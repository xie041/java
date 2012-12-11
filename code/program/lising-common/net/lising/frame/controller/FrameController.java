/**
 * 
 */
package net.lising.frame.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lising.frame.utils.FileOperator;
import net.lising.frame.utils.FrameConstant;
import net.lising.frame.utils.Logger;
import net.lising.frame.utils.RightBeanSet;
import net.lising.frame.utils.StringUtils;
import net.lising.frame.vo.ParameteVO;
import net.lising.lib.encrypt.SuperEncript;
import net.lising.manage.org.bean.RightBean;
import net.lising.manage.org.bean.auxiliary.RightAuxiliary;
import net.lising.manage.org.bean.context.UserContext;
import net.lising.urls.ProjectUtil;

import com.alibaba.fastjson.JSONArray;

/**
 * 框架Action类
 *  
 * @author 吕佳诚
 * @see net/lising/frame/utils/debug.txt
 */
@SuppressWarnings("serial")
public class FrameController extends HttpServlet {
	/**用于判断是否首页*/
//	private static final String IS_LOAD_COMPONENT = "index.jsp?isLoadComponent=true";

	/**
	 * 构造方法
	 */
	public FrameController() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//初始参数vo类---注释，每次运行，ParameteVO对象不为空。
		ParameteVO parameteVO = new ParameteVO();
		/**日志*/
		Logger logger = new Logger();
		
		logger.info("===============框架初始化【开始】===========");
		/**参数vo*/
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		RightBean rb =  null;
		
		//获取[静态文件服务器地址]
		parameteVO.setHFSServerURL( ProjectUtil.getHfsAddress() );
		
		//获取[是否生成组件区域]
		parameteVO.setIsLoadComponent(ProjectUtil.getFrameIsLoadComponent());

		//获取[im-chart-ip地址]
		parameteVO.setImChartServer( ProjectUtil.getImProjectAddress()  );
		
		//获取【当前项目IP地址】
		parameteVO.setProjectUrl( ProjectUtil.getCurrentProjectUrl(request) );
		
		//获取【当前项目名称】
		String currentProject = ProjectUtil.getCurrentProjectName(request);
		parameteVO.setProjectName( currentProject + "/" );
		
		//判断当前工程是否调入IM
		String ignoreIMProject = ProjectUtil.getIgnoreIMProject();
		
		if( ignoreIMProject == null || ignoreIMProject.indexOf( currentProject ) != -1) {
			parameteVO.setIsLoadIM( false );
		}else {
			parameteVO.setIsLoadIM( true );
		}
		
		//获取项目【本地地址】
		parameteVO.setProjectPath( StringUtils.getRealPath(request, "/")  );
		
		logger.info("\t\t【静态服务器地址】:"+parameteVO.getHFSServerURL());
		logger.info("\t\t【项目ip地址】:"+parameteVO.getProjectUrl());
		logger.info("\t\t【项目名称】:"+parameteVO.getProjectName());
		logger.info("\t\t【项目本地地址】:"+parameteVO.getProjectPath());
		logger.info("\t\t【即时通讯IM地址】:"+parameteVO.getImChartServer());
		logger.info("\t\t【配置是否加载组件区域】:"+parameteVO.isLoadComponent());
		logger.info("\t\t【忽略加载IM的项目】:"+ignoreIMProject);
		logger.info("\t\t【当前项目是否加载IM工程？】:"+parameteVO.isLoadIM());
		
		//======获取session数据并处理==开始========
		logger.info("\n\t===[session 数据获取内容开始]====");
		UserContext ctx = (UserContext)request.getSession().getAttribute(ProjectUtil.CTX);
		//======获取session数据并处理==结束========
		
		if( ctx == null  )
		{
			parameteVO.setErrorMessage("系统内部错误-用户未登录，请联系管理员！(UserContext=null)");
			//转到错误页面
			out.print(new FileOperator(logger).readErrorFile(parameteVO).toString());
			logger.info("\t\t 【获取session出错！！--用户未登录,UserContext为空】\n");
			logger.info("\t===[session 数据获取内容结束]====");
		}else {
			parameteVO.setUserId( StringUtils.convertNULL2Blank(  String.valueOf( ctx.getUser().getId() )   ) );
			parameteVO.setCompanyName(StringUtils.convertNULL2Blank(( ctx.getCmp().getName() )));
			parameteVO.setUserName(StringUtils.convertNULL2Blank(ctx.getUser().getName()));
			parameteVO.setUserPosition(StringUtils.convertNULL2Blank(ctx.getPos().getPositionName()));
			parameteVO.setCompanyId(StringUtils.convertNULL2Blank(String.valueOf( ctx.getCmp().getId() ) ));
			parameteVO.setEmployeeId(StringUtils.convertNULL2Blank(String.valueOf( ctx.getEmp().getId() ) ));
			
			
			//当前用户登录所在地
			if ( StringUtils.convertNULL2Blank( String.valueOf(ctx.getCmp().getCity()) ).length() > 0)
			{
				parameteVO.setCurrentLoginCity(  String.valueOf(ctx.getCmp().getCity()) );
			}else {
				logger.info("\t\t【默认当前用户登录所在地ID=1(北京)】");
				parameteVO.setCurrentSkin( FrameConstant.DEFALUT_CITY_NAME );
			}
			
			String userPhoto = "" ;
			String skin = "" ;
			
			if( ctx.getUau() != null  ) {
				userPhoto = ctx.getUau().getHeadImgPath();
				skin = ctx.getUau().getSkin() ;
				
				//设定用户头像
				if( StringUtils.convertNULL2Blank(userPhoto).length() > 0 )
				{
					parameteVO.setUserPhotoUrl(parameteVO.getHFSServerURL() + ProjectUtil.getUploadOfUserPortrait()+ parameteVO.getUserId() + "/" + userPhoto);
				}else {
					parameteVO.setUserPhotoUrl(parameteVO.getHFSServerURL() + FrameConstant.DEFAULT_USER_PHOTO );
				}
				//设定皮肤样式
				if ( StringUtils.convertNULL2Blank( skin ).length() > 0)
				{
					parameteVO.setCurrentSkin( skin );
				}else {
					logger.info("\t\t【皮肤样式】" + skin + "--设定为缺省skin1.css");
					parameteVO.setCurrentSkin( FrameConstant.DEFAULT_SKIN );
				}
				
			}else {
				parameteVO.setUserPhotoUrl(parameteVO.getHFSServerURL() + FrameConstant.DEFAULT_USER_PHOTO );
				parameteVO.setCurrentSkin( FrameConstant.DEFAULT_SKIN );
			}
			
			if( ctx.getCmp().getAuxiliary() != null ) {
				//公司logo
				if ( StringUtils.convertNULL2Blank( ctx.getCmp().getAuxiliary().getCompanyLogoURL() ).length() > 0)
				{
					parameteVO.setCompanyLogoUrl( parameteVO.getHFSServerURL() +  ctx.getCmp().getAuxiliary().getCompanyLogoURL() );
				}else {
					parameteVO.setCompanyLogoUrl( parameteVO.getHFSServerURL() +  FrameConstant.DEFAULT_COMPANY_LOGO );
					logger.info("\t\t【缺省公司logo】statics/images/header/logo.png");
				}
			}else {
				parameteVO.setCompanyLogoUrl( parameteVO.getHFSServerURL() +  FrameConstant.DEFAULT_COMPANY_LOGO );
			}
			logger.info("\t\t【用户ID】"+parameteVO.getUserId());
			logger.info("\t\t【用户名称】" + parameteVO.getUserName());
			logger.info("\t\t【职位名称】" + parameteVO.getUserPosition());
			logger.info("\t\t【用户头像地址】" + parameteVO.getUserPhotoUrl());
			logger.info("\t\t【公司LOGO地址】" + parameteVO.getCompanyLogoUrl());
			logger.info("\t\t【用户当前使用的皮肤样式名称】" + parameteVO.getCurrentSkin());
			logger.info("\t\t【公司ID】" + parameteVO.getCompanyId());
			logger.info("\t\t【公司名称】" + parameteVO.getCompanyName());
			logger.info("\t\t【员工ID】" + parameteVO.getEmployeeId());
			logger.info("\t\t【当前用户登录所在地ID】" + parameteVO.getCurrentLoginCity());
			
			
			List<RightBean> list = ctx.getRightList();
			if(list == null  ||  list.size() == 0)
			{
				parameteVO.setErrorMessage("该用户没有权限，请联系管理员！");
				//转到错误页面
				out.print(new FileOperator(logger).readErrorFile(parameteVO).toString());
				logger.info("\t\t 【获取权限列表RightBean出错！！】\n");
				logger.info("\t===[session 数据获取内容结束]====");
			}else {
				//解密参数
				Map<String,String> map = SuperEncript.getEncriptParams(request);
				/**用于保存当前用户选中工程的Id,通常为一级菜单的ID。一种为通过request获取；一种为数据库获取*/
				String serialId = map.get(FrameConstant.CURRENT_LEVEL_ONE_NAVIGATOR);
				/**二级菜单ID*/
				String serialId2 = map.get(FrameConstant.CURRENT_LEVEL_TWO_NAVIGATOR);
				/**判断是否调入组件*/
				String isLoadComponentString = map.get("isLoadComponent");
				
				//判断是否有组件body区域,如果解密或的isLoadComponentString参数不为空，则证明有body区域。
				if ( StringUtils.convertNULL2Blank(isLoadComponentString).length()> 0 )
				{
					parameteVO.setIsFirst( Boolean.valueOf(isLoadComponentString));
					logger.info("\t\t 【isLoadComponentString ！=null】parameteVO.getIsFirst=="+parameteVO.isFirst());
				}else {
					parameteVO.setIsFirst( false);
					logger.info("\t\t 【isLoadComponentString=null】parameteVO.getIsFirst=="+parameteVO.isFirst());
				}
				
				//判断解密后的serialId参数是否不为空且为数字，则设定一级活动导航ID(serialId)
				if(  StringUtils.isNumeric(serialId) ) {
					parameteVO.setSerialId(serialId);
					logger.info("\t\t 【serialId ！=null】parameteVO.setgerialId=="+parameteVO.getSerialId());
				}
				
				//判断解密后的serialId2参数是否不为空且为数字，则设定二级活动导航ID(serialId2)
				if(  StringUtils.isNumeric(serialId2) ) {
					parameteVO.setSerialId2(serialId2);
					logger.info("\t\t 【serialId2 ！=null】parameteVO.setSerialId2=="+parameteVO.getSerialId2());
				}
				
				logger.info("\t\t 【解密后:地址栏参数一级活动导航ID(sid)】: "+serialId);
				logger.info("\t\t 【解密后:地址栏参数二级活动导航ID(sid2)】: "+serialId2);
				logger.info("\t\t 【解密后:地址栏参数是否显示组件区域(isLoadComponent)】=="+isLoadComponentString);
				logger.info("\t\t 【当前用户获取权限列表个数】" + list.size());
				
				
			
				logger.info("\t===[session 数据获取内容结束]====\n");	
				
				if(ProjectUtil.getDevelopeMode()){
//					parameteVO.setSerialId("1");
					logger.info("\n\t===本地开发模式===");
					//加工参数列表
					rb =  new RightBeanSet(list,parameteVO,logger).getRightBean();
					out.print(	new FileOperator(logger).readFrameFile( rb, parameteVO ).toString()	);
				}else {
					//加工参数列表
					rb =  new RightBeanSet(list,parameteVO,logger).getRightBean();
					//判断当前链接地址是否具有展示权限-----开始
					
					//临时变量:用于判断当前工程是否具有权限
					boolean isRight = false;
					//变量：将跳转工程
					String redirectProject = null;
					//一级权限列表
					List<RightBean> level1List = rb.getLevelOneList();
					//二级权限列表
					List<RightBean> level2List = rb.getLevelTwoList();
					for (int i = 0 , len = level1List.size(); i < len; i++) {
						if( Long.valueOf(parameteVO.getSerialId()).longValue() ==  level1List.get(i).getId().longValue() ) {
							isRight = true;
						}
					}
					for (int i = 0 , len = level2List.size(); i < len; i++) {
						if( Long.valueOf(parameteVO.getSerialId2()).longValue() ==  level2List.get(i).getId().longValue() ) {
							isRight = true;
						}
					}
					
					if( !isRight )
					{
						redirectProject = level1List.get(0).getProjectKey();
						logger.info("\t\t--->该工程【"+parameteVO.getProjectName()+"】不具有权限，将跳转到:" + ProjectUtil.getIpAddressByProjectName(redirectProject));
						response.sendRedirect(ProjectUtil.getIpAddressByProjectName(redirectProject));
						logger.info("\t\t--->跳转成功！");
						return;
					}
					
					
					/**
					 * 不能直接使用ctx里面的getRightAuxiliaryList，因为会改变session中的值
					 * 所以复制了一个getRightAuxiliaryList对象
					 **/
					List<RightAuxiliary> rightAuxiliaryList = ctx.getRightAuxiliaryList();
					
					if( rightAuxiliaryList != null) {
						List<RightAuxiliary> listRightAuxiliary = new ArrayList<RightAuxiliary>();
						
						for (RightAuxiliary rightAuxiliary : rightAuxiliaryList) {
							RightAuxiliary newRightAuxiliary = new RightAuxiliary();
							
							newRightAuxiliary.setParentId(	rightAuxiliary.getParentId()	);
							//=====标题文字
							if(	StringUtils.convertNULL2Blank(rightAuxiliary.getTitleText()).length()>0 ) {
								//当前用户名称
								if(FrameConstant.CURRENT_USER_NAME.indexOf(rightAuxiliary.getTitleText())>-1) 
								{
									newRightAuxiliary.setTitleText(parameteVO.getUserName());
								}
								//当前公司名称
								else if( FrameConstant.CURRENT_COMPANY_NAME.indexOf(rightAuxiliary.getTitleText())>-1 )
								{
									newRightAuxiliary.setTitleText(parameteVO.getCompanyName());
								}
								//当前城市ID
								else if( FrameConstant.CURRENT_CITY_NAME.indexOf(rightAuxiliary.getTitleText())>-1  )
								{
									newRightAuxiliary.setTitleText(parameteVO.getCurrentLoginCity());
								}
							}
							//======图片地址
							if (	StringUtils.convertNULL2Blank( rightAuxiliary.getPictureURL() ).length()>0  ) {
								//用户头像
								if(  FrameConstant.USER_PHOTO_URL.indexOf(rightAuxiliary.getPictureURL())>-1  )
								{
									newRightAuxiliary.setPictureURL( parameteVO.getUserPhotoUrl() );
								}
								//公司LOGO
								else if( FrameConstant.COMPONY_LOGO_URL.indexOf(rightAuxiliary.getPictureURL())>-1  )
								{
									newRightAuxiliary.setPictureURL(parameteVO.getCompanyLogoUrl());
								}
								//缺省LOGO
								else if( FrameConstant.DEFAULT_LOGO_URL.indexOf( rightAuxiliary.getPictureURL() ) >-1  )
								{
									newRightAuxiliary.setPictureURL(parameteVO.getHFSServerURL()+FrameConstant.DEFAULT_COMPANY_LOGO);
								}
							}
							//===工程地址
							if( StringUtils.convertNULL2Blank( rightAuxiliary.getProjectURL()).length() > 0 && rightAuxiliary.getProjectURL().indexOf("http") == -1 )
							{
								String projectKey = rightAuxiliary.getProjectURL();
								String s1 = "1";
								String s2 = "5";
								if( rightAuxiliary.getProjectURL().indexOf("javascript") == -1 ) 
								{
									//获取sid 和 sid2
									for (int i = 0 , len = level1List.size(); i < len; i++) {
										if( level1List.get(i).getProjectKey().equals(projectKey.split(":")[0]) ) {
											s1 = String.valueOf(level1List.get(i).getId() );
											break;
										}
									}
									for (int i = 0 , len = level2List.size(); i < len; i++) {
										if( level2List.get(i).getUrlAddress().equals(projectKey.split(":")[1]) ) {
											s2 = String.valueOf(level2List.get(i).getId() );
											break;
										}
									}
									newRightAuxiliary.setProjectURL(ProjectUtil.getIpAddressByProjectName(projectKey.split(":")[0]) + projectKey.split(":")[1] +"?sid="+s1 + "&sid2="+ s2);
								}else {
									newRightAuxiliary.setProjectURL(projectKey );
								}
							}
							newRightAuxiliary.setParamateURL("");
							listRightAuxiliary.add( newRightAuxiliary );						
						}
						parameteVO.setAuxiliaryRightJSON( JSONArray.toJSONString(listRightAuxiliary).replaceAll("\\\\", "") );
						listRightAuxiliary = null;
						logger.info("\t\t【权限辅助表json字串】=="+parameteVO.getAuxiliaryRightJSON() );
					}
					
					//判断当前链接地址是否具有展示权限-----结束
					logger.info("\t\t 【否调入组件区域，parameteVO.isFirst()】=="+parameteVO.isFirst());
					logger.info("\t\t 【一级活动导航ID,parameteVO.getSerialId()】=="+parameteVO.getSerialId());
					logger.info("\t\t 【二级活动导航ID(parameteVO.getSerialId2())】=="+parameteVO.getSerialId2());
					
					out.print(	new FileOperator(logger).readFrameFile( rb, parameteVO ).toString()	);
				}
					// 设定组件js调入方法
					out.print("<script type=\'text/javascript\'>   \n");
					out.print("$(function(){   \n");
					
					if (rb.getLevelThreeList().size() > 0) {
						// 循环获取三级组件JS.load方法
						for (int i = 0, len = rb.getLevelThreeList().size(); i < len; i++) {
							logger.info("\t\t【将执行的js方法】"+rb.getLevelThreeList().get(i).getJsFunction());
							if (rb.getLevelThreeList().get(i).getJsFunction() != null ||
								StringUtils.convertNULL2Blank(rb.getLevelThreeList().get(i).getJsFunction()).length()>0	) {
								out.print("\t try{ \n");
								out.print("\t\t"+ rb.getLevelThreeList().get(i).getJsFunction() + "(); \n");
								out.print("\t }catch(err){}  \n");
							}
						}
					}//endif
					if ( parameteVO.isLoadIM() ) {
						out.print("im.load(); \n");
					}
					 out.print("FRAME.changeNavTitle ("+Integer.valueOf(parameteVO.getSerialId())+"); \n");
				     out.print("FrameSearch.getUrlParagram() \n");
				     out.print("FRAME.combineNavigator() \n");
					// out.print("FRAME.changeCompanyNameLength() \n");
					out.print("}); \n");
					out.print("</script> \n");
				}
			}
		
			logger.info("===============框架初始化【结束】===========");
			out.flush();
			out.close();	
	}
	
	
	public static void main(String[] args) {
		String aString= "?";
		String bString = "http://localhost:8080/lising-frame/index.jsp?sendType=1";
		System.err.println(bString.indexOf(aString));
		String[] abc = {"a","b","c"};
		System.err.println( abc.toString() );
		System.out.println(Arrays.toString(abc).replaceAll("[\\[\\], ]", ""));
	}
}
