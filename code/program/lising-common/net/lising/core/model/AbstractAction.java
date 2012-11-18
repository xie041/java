package net.lising.core.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lising.lib.ConstantLib;
import net.lising.lib.encrypt.SuperEncript;
import net.lising.manage.org.bean.CompanyBean;
import net.lising.manage.org.bean.DepartmentBean;
import net.lising.manage.org.bean.EmployeeBean;
import net.lising.manage.org.bean.UserBean;
import net.lising.manage.org.bean.context.UserContext;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author xie041 Email:xie041@126.com
 * @2011-3-25 上午11:20:36
 */
public class AbstractAction<T> extends ActionSupport implements SessionAware,
		ParameterAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 5463792560638350849L;

	protected List<T> list;
	protected DataPage<T> dataPage;
	protected Long id;//未加密的ID
	/**
	 *加密后的ID,获取之后,自动解密,并将值赋给ID
	 */
	protected String ssid ;
	protected int pageNo = 1;
	protected final int COUNT = 6; //列表中默认显示条数
	protected Map<String, String[]> params;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	protected String selected;// 选择全搜索目标
	
	protected UserBean findUser(){
		UserContext ctx = (UserContext)request.getSession().getAttribute(ConstantLib.USERCONTEXT);
		if(ctx == null) return null;
		return ctx.getUser();
	}
	protected EmployeeBean findEmp(){
		UserContext ctx = (UserContext)request.getSession().getAttribute(ConstantLib.USERCONTEXT);
		if(ctx == null) return null;
		return ctx.getEmp();
	}
	protected CompanyBean findCmp(){
		UserContext ctx = (UserContext)request.getSession().getAttribute(ConstantLib.USERCONTEXT);
		if(ctx == null) return null;
		return ctx.getCmp();
	}
	protected DepartmentBean findDept(){
		UserContext ctx = (UserContext)request.getSession().getAttribute(ConstantLib.USERCONTEXT);
		if(ctx == null) return null;
		return ctx.getDept();
	}
	
	/**
	 * @param param
	 * @return
	 */
	protected int toInt(String param){
		String v = request.getParameter(param);
		try {
			return Integer.parseInt(v);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * session中获取切换的城市ID
	 * @return
	 */
	protected int findSwitchCity(){
		String cityId = (String)request.getSession().getAttribute("cityId");
		return (cityId==null||cityId.equals(""))? 1 : Integer.parseInt(cityId);
	}
	
	/**
	 * 往session中存放切换的城市ID
	 * @param cityId
	 */
	protected void setSwitchCity(String cityId){
		request.getSession().setAttribute("cityId",cityId);
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setParameters(Map<String, String[]> params) {
		this.params = params;
	}

	public String[] getParamValue(String key) {
		return params.get(key);
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public DataPage<T> getDataPage() {
		return dataPage;
	}

	public void setDataPage(DataPage<T> dataPage) {
		this.dataPage = dataPage;
	}

	protected String toString(String param) {
		String result = request.getParameter(param);
		return null == result || "".equalsIgnoreCase(result.trim()) ? ""
				: result.trim();
	}

	protected String[] toStrings(String param) {
		return request.getParameterValues(param);
	}

	protected String getSelected() {
		return selected;
	}

	protected void setSelected(String selected) {
		this.selected = selected;
	}

	/**
	 * ssid和id是互通的，通过ssid可以给id赋值，如果有id，同样可以得到ssid的值
	 * @return
	 */
	public String getSsid() {
		if(ssid == null && id != 0 ){
			ssid = SuperEncript.encryptEveryThing(this.getId());
		}
		return ssid;
	}

	/**
	 * 获取解密ID,将解密值赋值给ID,让其不受任何影响
	 * 不可用protected，因为默认要调用
	 * @param ssid
	 */
	public void setSsid(String ssid) {
		String temp = SuperEncript.decryptEveryThing(ssid);
		//将值传递给ID
		try {
			this.id = Long.valueOf(temp);
			if(this.id == 0){
				//throw new CommonException("非法参数异常！");
			}
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath()+"/error.jsp");
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
		this.ssid = ssid;
	}
	
	/**
	 * 通过加密ID去获得Long值
	 * @param encrpyString
	 * @return
	 */
	public Long getSsidLong(String encrpyString){
		String temp = request.getParameter(encrpyString);
		if(temp == null || "".equals(temp)) return null;
		String s = SuperEncript.decryptEveryThing(temp);
		if("0".equals(s)) return null;
		else return Long.parseLong(s);
	}
	
	/**
	 * 通过long值加密ID
	 * @param longId
	 * @return
	 */
	public String getSsidString(Long longId){
		if(longId > 0) return SuperEncript.encryptEveryThing(longId);
		return null;
	}
	
	/**<pre>
	 * 作用：检测参数是否为空
	 * @param params
	 * 任何action方法，建议调用这个检测参数的方法(如果前端传递了参数)
	 * eg:ckeckParamsNull("id","orderNo");
	 * </pre>
	 */
	protected void ckeckParamsNull(String...params)throws Exception{
		if(params.length <= 0){
//			throw new CommonException("至少应该包含一个参数");
			//TODO
		}
		String tips = "";
		for(String p : params){
			String temp = request.getParameter(p);
			if(temp==null || temp.equals(""))
				tips += p + "、";//如果有空值，则添加
		}
		//归总参数为空
		if(!"".equals(tips)){
			tips = tips.substring(0, tips.length()-1);
//			throw new CommonException("参数"+tips+"的值为空,请检查");
			//TODO
		}
	}
}