/**
 * 
 */
package net.lising.frame.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.lising.frame.vo.ParameteVO;
import net.lising.lib.encrypt.SuperEncript;
import net.lising.manage.org.bean.RightBean;
import net.lising.urls.ProjectUtil;

/**
 * @author Administrator
 *
 */
public class FileOperator {
	private Logger logger = null ;
	private String style="widht:90px;";
	
	/**
	 * 构造方法
	 */
	public FileOperator(Logger logger) {
		this.logger = logger ;
	}
	/**
	 * 读取jar包（本项目）中的html文件
	 * @param rb 权限bean
	 * @param parameteVO 参数vo
	 * @return StringBuffer
	 */
	public  StringBuffer readFrameFile(RightBean rb,ParameteVO parameteVO)
	{
		logger.info("\n\t====【读取frame.html】文件开始====");
		
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer subsb = new StringBuffer();
		List<RightBean> level1List = rb.getLevelOneList() ;
		List<RightBean> level2List = rb.getLevelTwoList() ;
		try {
			is = this.getClass().getResourceAsStream(FrameConstant.FRAME_HTML_FILE);
			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			//读取文件行临时字符串变量
			String sLine = null;
			//临时字符串变量
			String temString  = null  ;
			//获取所有需要清除session的工程,并保存到parameteVO.logoutProjectURLs中。
			getLogoutProjectURls(parameteVO,rb) ;
			
			if(	parameteVO.isLoadComponent()	) {
				while ((sLine = br.readLine()) != null) {
					
					sLine = replace(sLine, parameteVO, subsb, level1List, level2List);
					
					//替换body区域
					if (sLine.indexOf(FrameConstant.SITEMESH_REPLACE_AREA) != -1) {
						temString =	StringUtils.convertNULL2Blank(	readBodyFile(FrameConstant.BODY_HTML_FILE,rb,parameteVO).toString()	)	;
						if (temString.length() > 0)
						{
							sLine = StringUtils.replace(sLine,FrameConstant.SITEMESH_REPLACE_AREA,temString);
						}
							
					}
					
					//替换需清除session的工程
					if (	sLine.indexOf(FrameConstant.LOGOUT_URLS) != -1	) {
						sLine = StringUtils.replace(sLine, FrameConstant.LOGOUT_URLS,parameteVO.getLogoutProjectURLs());
					}
					
					sb.append(sLine + "\n") ;
				}
			}else {
				while ((sLine = br.readLine()) != null) {
					sLine = replace(sLine, parameteVO, subsb, level1List, level2List);
					//替换需清除session的工程
					if (	sLine.indexOf(FrameConstant.LOGOUT_URLS) != -1	) {
						sLine = StringUtils.replace(sLine, FrameConstant.LOGOUT_URLS,parameteVO.getLogoutProjectURLs());
					}
					sb.append(sLine + "\n") ;
				}
			}
			
			//添加隐藏域，记录当前菜单状态sid&sid2
			sb.append("<input type=\"hidden\" id=\"frameMenuState\" value=\"sid="+ parameteVO.getSerialId() + "&sid2=" + parameteVO.getSerialId2() + "\"/>\n");
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("【读取frame.html】文件未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("\n\t====【读取frame.html】文件结束====");
		return sb;
	}
	
	
	/**
	 * 获取所有需要清除session的工程
	 * @param parameteVO
	 * @param rb
	 * @return
	 */
	private void getLogoutProjectURls(ParameteVO parameteVO,RightBean rb) throws Exception
	{
		StringBuffer sbURLs = new StringBuffer();
		//工程键值名称变量，用于存放工程名称
		String projectKey = "" ;
		//工程地址临时变量
		String address = "";

		
		List<RightBean> level1List = rb.getLevelOneList();
		
		for (int i = 0 , len = level1List.size(); i < len; i++) {
			projectKey = level1List.get(i).getProjectKey();
			
			if( StringUtils.convertNULL2Blank(projectKey).length() > 0 )
			{
				address = ProjectUtil.getIpAddressByProjectName(projectKey);
			}else {
				address = projectKey;
			}
			//添加工程地址并判断是否重复
			if ( sbURLs.indexOf(address) == -1)
			{
				sbURLs.append( address )
					  .append("logout?project=")
					  .append( projectKey )
					  .append( "," );
			}

		}
		
		//lising-plan 项目,一级菜单不包括plan项目，所以要添加上。
		if ( sbURLs.indexOf( ProjectUtil.getPlanProjectAddress() ) == -1)
		{
			sbURLs.append(ProjectUtil.getPlanProjectAddress())
				  .append("logout?project=")
				  .append(ProjectUtil.getPlanProjectAddress())
				  .append(",");
		}
		
		//lising-common 项目,要添加上。
		if ( sbURLs.indexOf( parameteVO.getProjectName() ) == -1)
		{
			sbURLs.append(parameteVO.getProjectUrl()+parameteVO.getProjectName())
				  .append("logout?project=")
				  .append(parameteVO.getProjectName())
				  .append(",");
		}
		List<RightBean> level2List = rb.getLevelTwoList();
		//工程url地址(跳转地址，如org/company/info.do)
		String urlAddres = "";
		for (int i = 0 , len = level2List.size(); i < len; i++) {
			//获取当前工程中数据库存储的URL地址,例如：pages/personal/index.jsp,用于给cas跳转时用。
			if( Long.valueOf(parameteVO.getSerialId2()).longValue() == level2List.get(i).getId().longValue() )
			{
				urlAddres = level2List.get(i).getUrlAddress();
			}
		}
		
		//当前工程地址+当前工程名+数据库存储URL = http://192.168.11.17:8081/lising-plan/pages/personal/index.jsp
		urlAddres  =  parameteVO.getProjectUrl()+parameteVO.getProjectName()+urlAddres;

//		//判断是否为lising-common项目,common项目的话用于调试，不加sid，不走index，走的是frame.go,主要是退出后执行的cas应用
//		//结果为：http://localhost:8080/lising-common/
		if( ProjectUtil.getCommonProjectAddress().indexOf(  parameteVO.getProjectName()  ) != -1 )
		{
			urlAddres  =  urlAddres + "frame.go";
		}else {
			//没有[?isLoadComponent]
			if( urlAddres.indexOf("?isLoadComponent") == -1) {
				//没有[？]
				if ( urlAddres.indexOf("?") == -1 ) {
					urlAddres  += "?isLoadComponent="+parameteVO.isLoadComponent();
				}else {//有[？]
					urlAddres  += "&isLoadComponent="+parameteVO.isLoadComponent();
				}
			}
			urlAddres  += "&sid="+parameteVO.getSerialId();
			urlAddres  += "&sid2="+parameteVO.getSerialId2();
		}
		
		urlAddres =  URLEncoder.encode(urlAddres,"UTF-8");
		
		//添加SSO服务器地址，用于执行退出
		sbURLs.append(ProjectUtil.getSsoServerAddressLogoutAddress())
				.append("?service=")
				.append( urlAddres )
				;
		
		logger.info("\t\t[获取所有需要清除SESSION的工程]"+sbURLs.toString());
		
		//设定需要清除session的工程
		parameteVO.setLogoutProjectURLs(sbURLs.toString());
		
//		return sbURLs.toString();
	}
	
	/**
	 * 根据不同标签替换相应的值
	 * @param sLine	但前读取到的行
	 * @param parameteVO 参数VO
	 * @param subsb StringBuffer对象，用于保存需替换的内容
	 * @param level1List List<RightBean>对象，一级菜单列表对象
	 * @param level1List List<RightBean>对象，二级菜单列表对象
	 * @return
	 * @throws Exception
	 */
	private String replace(String sLine,ParameteVO parameteVO,StringBuffer subsb,List<RightBean> level1List,List<RightBean> level2List) throws Exception
	{
		//设定当前样式字符串变量
		String current = "class=\"selected\""; //原来为：current
		//临时存放地址变量,
		String address = "";
		
		//临时存放菜单名称变量
		String mName = "";  
		long  id = 0;
		
		//此处替换【当前皮肤样式】
		if (sLine.indexOf(FrameConstant.CURRENT_SKIN_CSS) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_SKIN_CSS,
					parameteVO.getHFSServerURL() + 
					FrameConstant.SKIN_STATIC_PATH+
					parameteVO.getCurrentSkin()
			);
		}
		
		//此处替换【当前选中的皮肤样式】
		if (sLine.indexOf("id=\""+parameteVO.getCurrentSkin()+"\"") != -1) {
			sLine = StringUtils.replace(sLine, "class=\"\"","class=\"" + FrameConstant.SELECTED_SKIN + "\"");
		}
		
		//此处替换【查看网店】
		if (sLine.indexOf(FrameConstant.LISING_NETSHOP_URL) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.LISING_NETSHOP_URL,
						ProjectUtil.getManageProjectAddress()+"netshop/netshop.do?"+
						SuperEncript.encryptEveryThing( "cid="+parameteVO.getCompanyId() ) 
					);
		}
		
		//此处替换【静态文件服务器地址】
		if (sLine.indexOf(FrameConstant.HFS_SERVER_URL) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.HFS_SERVER_URL,parameteVO.getHFSServerURL());
		}
		
		//此处替换【当前工程URL】
		if (sLine.indexOf(FrameConstant.CURRENT_PROJECT_URL) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_PROJECT_URL,
					parameteVO.getProjectUrl()+parameteVO.getProjectName());
		}
		
		//公司名称
		if (sLine.indexOf(FrameConstant.CURRENT_COMPANY_NAME) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_COMPANY_NAME,parameteVO.getCompanyName());
		}
		//个人设置
		if (sLine.indexOf(FrameConstant.USER_CONFIG) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.USER_CONFIG,
					ProjectUtil.getUserCenter() + "?" +
					SuperEncript.encryptEveryThing("uid=" + parameteVO.getUserId())
			);
		}
		//用户名称
		if (sLine.indexOf(FrameConstant.CURRENT_USER_NAME) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.CURRENT_USER_NAME,parameteVO.getUserName());
		}
		
		//用户头像
		if (sLine.indexOf(FrameConstant.USER_PHOTO_URL) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.USER_PHOTO_URL,parameteVO.getUserPhotoUrl());
		}
		//用户职位
		if (sLine.indexOf(FrameConstant.CURRENT_USER_POSITION) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_USER_POSITION,parameteVO.getUserPosition());
		}
		
		//替换个人名片地址
		if (sLine.indexOf(FrameConstant.PERSONAL_CARD_URL) != -1) {;
			sLine = StringUtils.replace(sLine, FrameConstant.PERSONAL_CARD_URL,ProjectUtil.getManageProjectAddress());
		}
		//替换[当前用户ID]用于打开个人名片
		if (sLine.indexOf(FrameConstant.CURRENT_USER_ID) != -1) {;
			sLine = StringUtils.replace(sLine,FrameConstant.CURRENT_USER_ID,parameteVO.getUserId());
		}
		//替换[当前员工ID]用于打开个人名片
		if (sLine.indexOf(FrameConstant.CURRENT_EMPLOYEE_ID) != -1) {;
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_EMPLOYEE_ID,parameteVO.getEmployeeId());
		}
		//替换[当前公司ID]用于打开个人名片
		if (sLine.indexOf(FrameConstant.CURRENT_COMPANY_ID) != -1) {;
			sLine = StringUtils.replace(sLine,FrameConstant.CURRENT_COMPANY_ID,parameteVO.getCompanyId());
		}
		
		//替换[IM聊天区域]
		if ( sLine.indexOf(FrameConstant.IM_REPLACE_AREA) != -1) {
			if( parameteVO.isLoadIM() ) {
				sLine = StringUtils.replace(sLine,FrameConstant.IM_REPLACE_AREA, readImFile(FrameConstant.IM_HTML_FILE, parameteVO).toString());
			}else {
				sLine = StringUtils.replace(sLine,FrameConstant.IM_REPLACE_AREA, "");
			}
		}
		
		//搜索区域
		if (sLine.indexOf(FrameConstant.SEARCH_REPLACE_AREA) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.SEARCH_REPLACE_AREA,
					readSearchFile(FrameConstant.SEARCH_HTML_FILE, parameteVO).toString()
			);
		}
		
		//计划搜索action
		//添加logoutUrl参数，用于解决跳转到search项目中时，不能退出的bug。
		if (sLine.indexOf(FrameConstant.SEARCH_PLAN_ACTION) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.SEARCH_PLAN_ACTION,
						ProjectUtil.getSearchProjectAddress() +
						"plan.do?logoutUrl=" + 
						parameteVO.getLogoutProjectURLs()
					);
		}
		//文档搜索action
		//添加logoutUrl参数，用于解决跳转到search项目中时，不能退出的bug。
		if (sLine.indexOf(FrameConstant.SEARCH_DOC_ACTION) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.SEARCH_DOC_ACTION,
					ProjectUtil.getSearchProjectAddress() +
					"doc.do?logoutUrl=" + 
					parameteVO.getLogoutProjectURLs()
			);
		}
		//旅行社搜索action
		//添加logoutUrl参数，用于解决跳转到search项目中时，不能退出的bug。
		if (sLine.indexOf(FrameConstant.SEARCH_TRAVEL_ACTION) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.SEARCH_TRAVEL_ACTION,
					ProjectUtil.getSearchProjectAddress() +
					"travel.do?logoutUrl=" + 
					parameteVO.getLogoutProjectURLs()
			);
		}
		//搜索首页地址，添加logoutUrl参数，用于解决跳转到search项目中时，不能退出的bug。
		if (sLine.indexOf(FrameConstant.SEARCH_INDEX_ACTION) != -1) {
			sLine = StringUtils.replace(sLine,FrameConstant.SEARCH_INDEX_ACTION,
					ProjectUtil.getSearchProjectAddress() +
					"index.do?logoutUrl=" + parameteVO.getLogoutProjectURLs()
			);
		}
		
		//替换个人皮肤地址 //+ "ajax/org/user/skin.do?uId=" + parameteVO.getUserId()
		if (sLine.indexOf(FrameConstant.PERSONAL_SKIN_URL) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.PERSONAL_SKIN_URL,ProjectUtil.getManageProjectAddress() );
		}
		
		
		
		//用于存储一级导航所有工程的url及本项目的工程
		
		//替换【一级】导航条
		if (	sLine.indexOf(FrameConstant.LEVEL_ONE_NAV_REPLACE_AREA) != -1	) {//读取导航条
			logger.info("\n\t\t【一级导航替换开始】");
			//存储资源文件的key，一般为工程名称
			String projectKey = "";
			for (int i = 0 , len = level1List.size(); i < len; i++) {
				projectKey = level1List.get(i).getProjectKey();
				//如果存储的工程Key不为空，则读取资源文件，否则不读取资源文件
				if( StringUtils.convertNULL2Blank(projectKey).length() > 0 )
				{
					address = ProjectUtil.getIpAddressByProjectName(projectKey) +level1List.get(i).getUrlAddress();
				}else {
					address = "/"+level1List.get(i).getUrlAddress();
				}
				logger.info("\t\t[通过获取资源键得到一级菜单URL]"+address);
				mName = level1List.get(i).getMenuName() ;
				id = level1List.get(i).getId() ;
				
				//设定选中的高亮
				if( parameteVO.getSerialId().equalsIgnoreCase( String.valueOf(id ) ) )
				{
					current = "class=\"selected\"";
				}else {
					current = "";
				}
				
				if( address.indexOf("?isLoadComponent") == -1) {
					//没有[？]
					if ( address.indexOf("?") == -1 ) {
						address += "?isLoadComponent="+parameteVO.isLoadComponent();
					}else {//有[？]
						address += "&isLoadComponent="+parameteVO.isLoadComponent();
					}
				}
				address  += "&sid=" + id;
				
//				if(parameteVO.getProjectName() .indexOf( projectKey ) != -1 ){
//					address  += "&sid2=" + parameteVO.getSerialId2();
//				}
				
//				if( address.indexOf("?")>-1)
//				{
//					address = address+"&sid="+ id + "&isLoadComponent=true";
//				}else {
//					address = address+"?sid="+id + "&isLoadComponent=true";
//				}
				
//				address  += "&sid2=" + parameteVO.getSerialId2();
				
//				subsb.append("	<a "+ current +" name=\"s"+ id + "\" title=\""+ mName + "\" href=\""+address.split("\\?")[0] +"?"+ SuperEncript.encryptEveryThing(address.split("\\?")[1]) + "\" onclick=\"javascript:FRAME.changeNavTitle("+id+")\">"+mName+" </a>	\n");

				subsb.append("<li>	\n");
				subsb.append("\t\t\t\t\t\t");
				subsb.append("<a ");
				subsb.append(current);
				subsb.append(" name=\"s"+id+ "\"");
				subsb.append(" title=\""+ mName + "\"");
				subsb.append(" href=\""  );
				subsb.append( address  );
//				subsb.append(address.split("\\?")[0] +"?");//获取？之前的字符串，得到类似http://192.168.11.17:8082/lising-manage/index.jsp
//				subsb.append(SuperEncript.encryptEveryThing(address.split("\\?")[1]) );//将？之后的字串加密,即将isLoadComponent=true?isLoadComponent=true&sid=1之类的加密
				subsb.append("\"");
				subsb.append(" onclick=\"javascript:FRAME.changeNavTitle("+ id + ")\"");
				subsb.append("/>");
				subsb.append(mName);
				subsb.append("</a>	\n");
				
				subsb.append("\t\t\t\t\t");
				subsb.append("</li>	\n");
				
			}
			
			sLine = StringUtils.replace(sLine, FrameConstant.LEVEL_ONE_NAV_REPLACE_AREA,subsb.toString());
			subsb.setLength(0);
			logger.info("\t\t【一级导航替换完成】");
		}
		
		
		//替换中部导航条---开始
		if( StringUtils.contain(sLine, FrameConstant.CURRENT_NAVIGATOR_AREA) ) {
//			<li class="fl">
//		 		<a href="#">我的公司</a>
//		 		<div class="qq-trigon">
//		 			<span class="first"></span>
//		 			<span class="secend"></span>
//		 		</div>
//	 		</li>
			//存储资源文件的key，一般为工程名称
			String projectKey = "";
			for (int i = 0 , len = level1List.size(); i < len; i++) {
				if( StringUtils.toLong(parameteVO.getSerialId()) == level1List.get(i).getId().longValue()) {
					projectKey = level1List.get(i).getProjectKey();
					//如果存储的工程Key不为空，则读取资源文件，否则不读取资源文件
					if( StringUtils.convertNULL2Blank(projectKey).length() > 0 )
					{
						address = ProjectUtil.getIpAddressByProjectName(projectKey) +level1List.get(i).getUrlAddress();
					}else {
						address = "/"+level1List.get(i).getUrlAddress();
					}
					logger.info("\t\t[通过获取资源键得到一级菜单URL]"+address);
					mName = level1List.get(i).getMenuName() ;
					id = level1List.get(i).getId() ;
					
					if( address.indexOf("?isLoadComponent") == -1) {
						//没有[？]
						if ( address.indexOf("?") == -1 ) {
							address += "?isLoadComponent="+parameteVO.isLoadComponent();
						}else {//有[？]
							address += "&isLoadComponent="+parameteVO.isLoadComponent();
						}
					}
					address  += "&sid=" + id;
	
					subsb.append("<li class=\"fl\">	\n");
					subsb.append("\t\t\t\t\t\t");
					subsb.append("<a ");
					subsb.append(" name=\"s"+id+ "\"");
					subsb.append(" title=\""+ mName + "\"");
					subsb.append(" href=\""  );
					subsb.append( address  );
	//				subsb.append(address.split("\\?")[0] +"?");//获取？之前的字符串，得到类似http://192.168.11.17:8082/lising-manage/index.jsp
	//				subsb.append(SuperEncript.encryptEveryThing(address.split("\\?")[1]) );//将？之后的字串加密,即将isLoadComponent=true?isLoadComponent=true&sid=1之类的加密
					subsb.append("\"");
					subsb.append(" onclick=\"javascript:FRAME.changeNavTitle("+ id + ")\"");
					subsb.append("/>");
					subsb.append(mName);
					subsb.append("</a>	\n");
					
					subsb.append("\t<div class=\"qq-trigon\"> \n");
					subsb.append("\t\t <span class=\"first\"></span>\n");
					subsb.append("\t\t <span class=\"secend\"></span>\n");
					subsb.append("\t</div> \n");
					
					subsb.append("\t\t\t\t\t");
					subsb.append("</li>	\n");
				}	
			}
			
			for (int i = 0 , len = level2List.size(); i < len; i++) {
				if( StringUtils.toLong(parameteVO.getSerialId2()) == level2List.get(i).getId().longValue()) {
					projectKey = level2List.get(i).getProjectKey();
					//如果存储的工程Key不为空，则读取资源文件，否则不读取资源文件
					if( StringUtils.convertNULL2Blank(projectKey).length() > 0 )
					{
						address = ProjectUtil.getIpAddressByProjectName(projectKey) +level2List.get(i).getUrlAddress();
					}else {
						address = "/"+level2List.get(i).getUrlAddress();
					}
					logger.info("\t\t[通过获取资源键得到二级菜单URL]"+address);	
					//类似：组织架构
					mName = level2List.get(i).getMenuName() ;
					id = level2List.get(i).getId() ;
					
					subsb.append("\t\t\t\t\t");
					subsb.append(" <li class=\"fl ml10\">	\n");
					
					if( address.indexOf("?")>-1)
					{
						address = address + "&sid="+parameteVO.getSerialId()+"&sid2="+id ;
					}else {
						address = address + "?sid="+parameteVO.getSerialId()+"&sid2="+id ;
					}
					
					subsb.append("\t\t\t\t\t");
					subsb.append("<a");
					subsb.append( " name=\"s" + id + "\" ");
					subsb.append( " title=\""+ mName + "\""	);//"class=\"selected\""
					subsb.append( " href=\"");
					subsb.append( address );
//					subsb.append(address.split("\\?")[0] +"?");//获取？之前的字符串，得到类似http://192.168.11.17:8082/lising-manage/index.jsp
//					subsb.append(SuperEncript.encryptEveryThing(address.split("\\?")[1]) );//将？之后的字串加密,即将isLoadComponent=true?isLoadComponent=true&sid=1之类的加密
					subsb.append("\"");
					subsb.append("/>");
					subsb.append(mName);
					subsb.append("</a>	\n");
					
					subsb.append("\t<div class=\"qq-trigon\"> \n");
					subsb.append("\t\t <span class=\"first\"></span>\n");
					subsb.append("\t\t <span class=\"secend\"></span>\n");
					subsb.append("\t</div> \n");
					
					subsb.append("\t\t\t\t\t");
					subsb.append("</li>	\n");
				}
			}
			
			sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_NAVIGATOR_AREA,subsb.toString());
			subsb.setLength(0);
		}
		//替换中部导航条---结束
		
		
		//替换二级导航条
		if (	sLine.indexOf(FrameConstant.LEVEL_TWO_NAV_REPLACE_AREA) != -1	) {//读取导航子菜单
			logger.info("\t\t【二级导航替换开始】");
			//存储资源文件的key，一般为工程名称
			String projectKey = "";
			for (int i = 0 , len = level2List.size(); i < len; i++) {
				projectKey = level2List.get(i).getProjectKey();
				//如果存储的工程Key不为空，则读取资源文件，否则不读取资源文件
				if( StringUtils.convertNULL2Blank(projectKey).length() > 0 )
				{
					address = ProjectUtil.getIpAddressByProjectName(projectKey) +level2List.get(i).getUrlAddress();
				}else {
					address = "/"+level2List.get(i).getUrlAddress();
				}
				logger.info("\t\t[通过获取资源键得到二级菜单URL]"+address);	
				//类似：组织架构
				mName = level2List.get(i).getMenuName() ;
				id = level2List.get(i).getId() ;
				
				//判断哪个选中
				if( parameteVO.getSerialId2().equalsIgnoreCase( String.valueOf( id ) ) )
				{
					current = "class=\"selected\"";//style=\"color:#F97E27;font-weight:bold;\"
				}else {
					current = "";
				}
				subsb.append(" <li>	\n");
				
				if( address.indexOf("?")>-1)
				{
					address = address + "&sid="+parameteVO.getSerialId()+"&sid2="+id ;
				}else {
					address = address + "?sid="+parameteVO.getSerialId()+"&sid2="+id ;
				}
				
				if(mName.length()>4) {
					style = " style=\"width:90px;\"";
				}else {
					style = "" ;
				}
//				subsb.append("	<a "+ style + " name=\"s"+ id + "\" "+current+" title=\""+ mName + "\" href=\""+address.split("\\?")[0]+"?"+SuperEncript.encryptEveryThing(address.split("\\?")[1])+"\" >"+mName+"</a>	\n"); //id
				subsb.append("\t\t\t\t\t\t");
				subsb.append("<a");
				subsb.append( style );//if(mName.length()>4)style="width:90px;"
				subsb.append( " name=\"s" + id + "\" ");
				subsb.append( current );
				subsb.append( " title=\""+ mName + "\""	);//"class=\"selected\""
				subsb.append( " href=\"");
				subsb.append( address );
//				subsb.append(address.split("\\?")[0] +"?");//获取？之前的字符串，得到类似http://192.168.11.17:8082/lising-manage/index.jsp
//				subsb.append(SuperEncript.encryptEveryThing(address.split("\\?")[1]) );//将？之后的字串加密,即将isLoadComponent=true?isLoadComponent=true&sid=1之类的加密
				subsb.append("\"");
				subsb.append("/>");
				subsb.append(mName);
				subsb.append("</a>	\n");
				subsb.append("\t\t\t\t\t\t");
				subsb.append("</li>	\n");
			}
			sLine = StringUtils.replace(sLine, FrameConstant.LEVEL_TWO_NAV_REPLACE_AREA,subsb.toString());
			subsb.setLength(0);
			logger.info("\t\t【二级导航替换完成】");
		} 
		
		//设置权限辅助表json数据 
		if (sLine.indexOf(FrameConstant.AUXILIARY_RIGHT_JSON) != -1) {
			sLine = StringUtils.replace(sLine, FrameConstant.AUXILIARY_RIGHT_JSON,parameteVO.getAuxiliaryRightJSON() );
		}
		return sLine;
	}
		
	/**
	 * 读取jar包（本项目）中的body.html文件
	 * @param htmlFileName 框架body.html文件名及路径
	 * @param level3List 组件列表
	 * @param parameteVO parameteVO
	 * @return html片段
	 */
	public  StringBuffer readBodyFile(String htmlFileName,RightBean rb,ParameteVO parameteVO) 
	{
		logger.info("\n\t===【读取body区域】文件开始====");
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer subsb = new StringBuffer();
		List<RightBean> level3List = rb.getLevelThreeList();
		
		//权限列表中组件项为空
		if( level3List.size() == 0) {
			sb.append("");
			return sb;
		}
		
		
		try {
			is = this.getClass().getResourceAsStream(htmlFileName);
			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String sLine = null;
			boolean isMore = false;
			int countOfComponent = 0;
			
			while ((sLine = br.readLine()) != null) {
				//此处获取注册的HTML文件片段 【平铺】部分
				if (sLine.indexOf(FrameConstant.FLAT_COMPONENT_REPLACE_AREA) != -1) {
					logger.info("\t\t\t【body区域--平铺区域】开始");
					
					for (int i = 0 , len = level3List.size(); i < len; i++) {
						if( countOfComponent > 0 )
						{
							isMore = true ;
							countOfComponent = 0 ;
						}
						if( FrameConstant.COMPONENT_FLAT.equals( level3List.get(i).getMenuPosition()) )
						{
							countOfComponent += 1;
							subsb.append(readLocalFile(parameteVO.getProjectPath()+level3List.get(i).getUrlAddress() , isMore,parameteVO).toString() );
						}
					}
					
					//如果有平铺的组件且成功存储到subsb里面去了
					if( subsb.length()>0 )
					{	
						//替换
						sLine = StringUtils.replace(sLine, FrameConstant.FLAT_COMPONENT_REPLACE_AREA,subsb.toString());
					}else {
						//否则将此标识替换空白
						sLine = StringUtils.replace(sLine,FrameConstant.FLAT_COMPONENT_REPLACE_AREA,"");
					}
					subsb.setLength(0);
					isMore = false;
					countOfComponent = 0 ;
					logger.info("\t\t\t【body区域--平铺区域】结束");
				}
				
				
				
				//此处获取注册的HTML文件片段 【左边】部分
				if (sLine.indexOf(FrameConstant.LEFT_COMPONENT_REPLACE_AREA) != -1) {
					logger.info("\t\t\t【body区域--左区域】开始 "+countOfComponent);
					
					for (int i = 0 , len = level3List.size(); i < len; i++) {
						
						if( countOfComponent > 0 )
						{
							isMore = true ;
							countOfComponent = 0 ;
						}
						if( FrameConstant.COMPONENT_LEFT.equals( level3List.get(i).getMenuPosition()) )
						{
							countOfComponent+=1;
							subsb.append(readLocalFile(parameteVO.getProjectPath()+level3List.get(i).getUrlAddress() , isMore , parameteVO).toString());
						}
					}
					
					//如果有左区域的组件且成功存储到subsb里面去了
					if( subsb.length()>0 )
					{	
						//替换
						sLine = StringUtils.replace(sLine,FrameConstant.LEFT_COMPONENT_REPLACE_AREA,subsb.toString());
					}else {
						//否则将此标识替换空白
						sLine = StringUtils.replace(sLine,FrameConstant.LEFT_COMPONENT_REPLACE_AREA,"");
					}
					subsb.setLength(0);
					isMore = false;
					countOfComponent = 0 ;
					
					logger.info("\t\t\t【body区域--左区域】结束");
				}
				
				//此处获取注册的HTML文件片段 【右边】部分
				if (sLine.indexOf(FrameConstant.RIGHT_COMPONENT_REPLACE_AREA) != -1) {
					logger.info("\t\t\t【body区域--右区域】开始 " + countOfComponent);
//					logger.info("\t\tisMore==="+isMore);
					
					for (int i = 0 , len = level3List.size(); i < len; i++) {
						if( countOfComponent > 0 )
						{
							isMore = true ;
							countOfComponent = 0 ;
						}
						if( FrameConstant.COMPONENT_RIGHT.equals( level3List.get(i).getMenuPosition()) )
						{
							countOfComponent+=1;
							subsb.append(readLocalFile(parameteVO.getProjectPath()+level3List.get(i).getUrlAddress() , isMore , parameteVO).toString() );
						}
					}
					
					//如果有右区域的组件且成功存储到subsb里面去了
					if( subsb.length()>0 )
					{	
						//替换
						sLine = StringUtils.replace(sLine,FrameConstant.RIGHT_COMPONENT_REPLACE_AREA,subsb.toString());
					}else {
						//否则将此标识替换空白
						sLine = StringUtils.replace(sLine,FrameConstant.RIGHT_COMPONENT_REPLACE_AREA,"");
					}
					subsb.setLength(0);
					isMore = false;
					countOfComponent = 0 ;
					logger.info("\t\t\t【body区域--右区域】结束");
				}
				
				sb.append(sLine + "\n") ;
			}
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("\n\t===【读取body区域】文件结束===");
		
		return sb;
	}
	
	
	/**
	 * 读取jar包（本项目）中的im.html文件
	 * @param imFileName 框架im.html文件名及路径
	 * @param parameteVO parameteVO
	 * @return html片段
	 */
	private boolean isFirstLoadIM = true ;
	public  StringBuffer readImFile(String imFileName,ParameteVO parameteVO)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("");
		if( !isFirstLoadIM )  return sb;
		if( !parameteVO.isLoadIM() )  return sb;
		logger.info("\n\t===【读取im】文件开始===");
		
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = this.getClass().getResourceAsStream(imFileName);
			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String sLine = null;
			
			while ((sLine = br.readLine()) != null) {
				//IM用户ID
				if (sLine.indexOf(FrameConstant.CURRENT_USER_ID) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.CURRENT_USER_ID,parameteVO.getUserId());
				}
				
				//公司ID
				if (sLine.indexOf(FrameConstant.CURRENT_COMPANY_ID) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_COMPANY_ID,parameteVO.getCompanyId());
				}
				
				//IM用户名称
				if (sLine.indexOf(FrameConstant.CURRENT_USER_NAME) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.CURRENT_USER_NAME,parameteVO.getUserName());
				}
				
				//替换静态服务器地址
				if (sLine.indexOf(FrameConstant.HFS_SERVER_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.HFS_SERVER_URL,parameteVO.getHFSServerURL());
				}
				
				//此处替换IM服务器地址
				if (sLine.indexOf(FrameConstant.IM_SERVER_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.IM_SERVER_URL,parameteVO.getImChartServer());
				}
				//此处替系统工程服务器地址
				if (sLine.indexOf(FrameConstant.LISING_SYSTEM_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.LISING_SYSTEM_URL,ProjectUtil.getManageProjectAddress());
				}
				
				//订单工程IP
				if (sLine.indexOf(FrameConstant.LISING_ORDER_URL) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.LISING_ORDER_URL,ProjectUtil.getFinanceProjectAddress());
				}
				//manage工程IP
				if (sLine.indexOf(FrameConstant.LISING_MANAGE_URL) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.LISING_MANAGE_URL,ProjectUtil.getManageProjectAddress());
				}
				//此处替换【当前工程URL】
				if (sLine.indexOf(FrameConstant.CURRENT_PROJECT_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.CURRENT_PROJECT_URL,
							parameteVO.getProjectUrl()+parameteVO.getProjectName());
				}
				sb.append(sLine + "\n") ;
			}
			return sb;
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		isFirstLoadIM = false;
		logger.info("\n\t===【读取im】文件结束===");
		return sb;
	}
	
	/**
	 * 读取jar包（本项目）中的search.html文件
	 * @param searchFileName 框架search.html文件名及路径
	 * @param paramteVO 参数对象
	 * @return html片段
	 */
	public  StringBuffer readSearchFile(String searchFileName,ParameteVO parameteVO)
	{
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = this.getClass().getResourceAsStream(searchFileName);
			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String sLine = null;
			
			while ((sLine = br.readLine()) != null) {
//				if (sLine.indexOf(FrameConstant.SEARCH_REPLACE_AREA) != -1) {
//					sLine = StringUtils.replace(sLine, FrameConstant.SEARCH_REPLACE_AREA,parameteVO.getHFSServerURL());
//				}
				sb.append(sLine + "\n") ;
			}
			return sb;
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return sb;
	}
	/**
	 * 读取jar包（本项目）中的error.html文件
	 * @param imFileName 框架error.html文件名及路径
	 * @return html片段
	 */
	public  StringBuffer readErrorFile(ParameteVO parameteVO)
	{
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = this.getClass().getResourceAsStream(FrameConstant.ERROR_FILE_NAME);
			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String sLine = null;
			String projectURLs = parameteVO.getProjectUrl();
			
			//http://192.168.11.16:8082/lising-finance/logout?project=lising-finance,
			//http://192.168.11.16:8090/cas-server/logout?service=http://localhost:8080/lising-frame/
			
			projectURLs	+= parameteVO.getProjectName();
			projectURLs += "logout?project=" ;
			projectURLs += parameteVO.getProjectName();
			projectURLs += ",";
			projectURLs += ProjectUtil.getSsoServerAddressLogoutAddress();
			projectURLs += "?service=";
			projectURLs += parameteVO.getProjectUrl();
			projectURLs	+= parameteVO.getProjectName();
			projectURLs	+= "frame.go";
			
			
			
			while ((sLine = br.readLine()) != null) {
				
				//替换静态服务器地址
				if (sLine.indexOf(FrameConstant.HFS_SERVER_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.HFS_SERVER_URL,parameteVO.getHFSServerURL());
				}
				
				//替换错误消息
				if (sLine.indexOf(FrameConstant.ERROR_MESSAGE) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.ERROR_MESSAGE,parameteVO.getErrorMessage());
				}
				
				//搜索地址，用于返回到主页搜索页面
				if (sLine.indexOf(FrameConstant.SEARCH_INDEX_ACTION) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.SEARCH_INDEX_ACTION,ProjectUtil.getSearchProjectAddress());
				}
				
				//替换需清除session的工程
				if (	sLine.indexOf(FrameConstant.LOGOUT_URLS) != -1	) {
					sLine = StringUtils.replace(sLine, FrameConstant.LOGOUT_URLS, projectURLs );
				}
				
				sb.append(sLine + "\n") ;
			}
			return sb;
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return sb;
	}
	
	
	
	/**
	 * 读取本地文件HTML片段
	 * @param fileName
	 * @return
	 */
	public  StringBuffer readLocalFile(String fileName,boolean more,ParameteVO parameteVO)
	{
		logger.info("\t\t\t【读取本地项目组件文件】开始");
		logger.info("\t\t\t\t【文件名称】"+fileName);
		
//		logger.info("\t\t\t More==="+more);
//		System.err.println(fileName+" readLocalFile=== more:"+more);
		
		StringBuffer sb = new StringBuffer();
		InputStreamReader is = null;
//		InputStream is = null;
		BufferedReader br = null;
		
		String lising_manage_url = ProjectUtil.getIpAddressByProjectName("lising-manage");
//		String lising_order_url = ProjectUtil.getIpAddressByProjectName("lising-order");
		String lising_finance_url = ProjectUtil.getIpAddressByProjectName("lising-finance");
		String lising_plan_url = ProjectUtil.getIpAddressByProjectName("lising-plan");
		
		logger.info("\t\t\t\t获取【lising-manage项目】ajax调用地址=="+lising_manage_url);
//		logger.info("\t\t\t\t获取【lising_order项目】ajax调用地址=="+lising_order_url);
		logger.info("\t\t\t\t获取【lising_finance项目】ajax调用地址=="+lising_finance_url);
		logger.info("\t\t\t\t获取【lising_plan项目】ajax调用地址=="+lising_plan_url);
		
		
		try {
//			is = this.getClass().getResourceAsStream(fileName);
			is = new InputStreamReader(  new FileInputStream(fileName), "UTF-8");
//			br =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			br =  new BufferedReader(is);
			String sLine = null;
			
			
			if( more ) {
				sb.append( "<div style=\"margin-top:10px;\"></div> \n" );
			}
			
			while ((sLine = br.readLine()) != null) {
				
				//替换[lising-manage]项目URL地址，为了组件的AJAX调用
				if (sLine.indexOf(FrameConstant.LISING_MANAGE_URL) != -1) {
					sLine = StringUtils.replace(sLine, FrameConstant.LISING_MANAGE_URL,ProjectUtil.getManageProjectAddress());
				 }
				
				//替换[lising-order]项目URL地址，为了组件的AJAX调用
				if (sLine.indexOf(FrameConstant.LISING_ORDER_URL) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.LISING_ORDER_URL,ProjectUtil.getFinanceProjectAddress());
				}
				
				//替换[lising-finance]项目URL地址，为了组件的AJAX调用
				if (sLine.indexOf(FrameConstant.LISING_FINANCE_URL) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.LISING_FINANCE_URL,ProjectUtil.getFinanceProjectAddress());
				}
				
				//替换[lising-finance]项目URL地址，为了组件的AJAX调用
				if (sLine.indexOf(FrameConstant.LISING_PLAN_URL) != -1) {
					sLine = StringUtils.replace(sLine,FrameConstant.LISING_PLAN_URL,ProjectUtil.getPlanProjectAddress());
				}

				sb.append(sLine + "\n") ;
			}
			
//			System.err.println(sb.toString());
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.info("某个组件文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("读取或写入某个组件文件出错！");
			e.printStackTrace();
		}catch (Exception e) {
			logger.info("未知错误！");
			e.printStackTrace();
		}finally{
			try {
				if (br != null) {
					br.close();
				}
				
				if( is != null) {
					is.close();
				}
//				sb.setLength(0);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		logger.info("\t\t\t【读取本地项目html片段文件】结束");
		return sb;
	}
		
	 /**
     * 获取指定目录的绝对路径
     * @param requset HttpServletRequest
     * @param c 指定的目录
     * @return String 指定目录的绝对路径
     */
    public static final String getRealPath(HttpServletRequest requset, String pathName) {
    	return requset.getSession().getServletContext().getRealPath(pathName);
    }
    
	public static void main(String[] args) {
		String s1 = "lising-common/";
		String s2 = "lising-common/";
		
		System.err.println(s1.indexOf( s2 ));
		
	}
}
