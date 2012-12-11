package net.lising.tag;

import static net.lising.lib.LogUtil.log;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import net.lising.core.util.AbstractSerBean;

/**
 * 
 * <pre>
 * 分页标签
 * 操作方法：
 * jsp头部：<%@ taglib uri="/WEB-INF/tld/lising.tld" prefix="ls"%>
 * 需要分页的地方：<ls:page pageNo="${dataPage.currPage}" prefix="con" sercode="${dataPage.sercode}" url="${path}plan/personal/list_page.do" totalPage="${dataPage.totalPages}"></ls:page>
 * @author xie041
 * </pre>
 */
public class PageTag extends BaseTag {

	private static final long serialVersionUID = 4964814477495240581L;

	private Integer totalPage;// 总页

	private Integer firstPage = 1;// 首页

	private Integer lastPage;// 末页

	private Integer nextPage;// 下一页

	private Integer backPage;// 上一页

	private String url;// 请求地址

	private Integer pageNo;// 当前页

	private String sercode;// 查询参数序列化编码   name=zhangsan&pass=12345&sex=1
	
	private String prefix;//前缀
	
	private String pname;
	private String pvalue;
	private String[] pnames;
	private String[] pvalues;

	@Override
	protected void initTag() {
		if (pname != null && !"".equals(pvalue)) {
			pnames = pname.split(",");
			pvalues = pvalue.split(",");
		}
		// 末页
		lastPage = totalPage;
		// 下一页
		if (pageNo + 1 > totalPage)
			nextPage = totalPage;
		else
			nextPage = pageNo + 1;
		// 上一页
		if (pageNo - 1 < firstPage)
			backPage = pageNo;
		else
			backPage = pageNo - 1;
		if (!url.startsWith("http://"))
			this.setUrl(getPath() + url);
	}

	@Override
	public int doStartTag() throws JspException {
		initTag();
		JspWriter out = pageContext.getOut();
		StringBuffer showStr = new StringBuffer("");
		try {
			showStr.append("<div id = \"pageFormDiv\">");
			showStr.append("<form  name = \"pageFormName\" action = \"" + url
					+ "\" method = \"get\" id = \"pageFormId\">");
			showStr.append("<input type=\"hidden\" name= \"pageNo\"  id = \"pageNoId\" value = \"1\" />");
			
			if (null != pnames) {//简单条件搜索
				for (int i = 0; i < pnames.length; i++) {
					showStr.append("<input type=\"hidden\" name= \""
							+ pnames[i] + "\"  value = \"" + pvalues[i]
							+ "\" />");
				}
			}else{
				//多条件搜索
				if (null != sercode && !"".equals(sercode)) {
					String[] d = sercode.split(AbstractSerBean.split_2);
					for(String s:d){
						String[] dd = s.split(AbstractSerBean.split_1);
						String v = dd.length==1?"":dd[1];
						showStr.append("<input type=\"hidden\" name= \""+prefix+"."+dd[0]+"\"   value = \""+ v + "\" />");
					}
				}
			}
			showStr.append("<span>");
			showStr.append("<a href=\"javascript:submitPageForm('" + firstPage
					+ "')\">首页</a>");
			showStr.append("</span>");
			showStr.append("<span>");
			showStr.append("<a href=\"javascript:submitPageForm('" + backPage
					+ "')\">上一页</a>");
			showStr.append("</span>");
			showStr.append("<span>");
			showStr.append("" + pageNo + "/" + totalPage + "");
			showStr.append("</span>");
			showStr.append("<span>");
			showStr.append("<a href=\"javascript:submitPageForm('" + nextPage
					+ "')\">下一页</a>");
			showStr.append("</span>");
			showStr.append("<span>");
			showStr.append("<a href=\"javascript:submitPageForm('" + lastPage
					+ "')\">末页</a>");
			showStr.append("</span");
			showStr.append("</form>");

			showStr.append("<script type=\"text/javascript\">");
			showStr.append("function submitPageForm(p){");
			showStr.append(" document.getElementById(\"pageNoId\").value=p;");
			showStr.append(" document.forms['pageFormName'].submit();");
			showStr.append("}");
			showStr.append("</script>");
			showStr.append("</div>");
			out.print(showStr.toString());
		} catch (Exception e) {
			log.error("分页标签 ->doStartTag() 失败："+e.getMessage());
		}
		return super.doStartTag();
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getSercode() {
		return sercode;
	}

	public void setSercode(String sercode) {
		this.sercode = sercode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
