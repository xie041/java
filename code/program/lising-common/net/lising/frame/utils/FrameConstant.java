/**
 * 
 */
package net.lising.frame.utils;

/** 
 * @author : 吕佳诚
 * @Description : 框架常量类，用于针对一些字符串的描述。
 * @CreateDate : 2012-8-14 上午10:28:33 
 * @lastModified : 2012-8-14 上午10:28:33 
 * @version : 1.0  
 */
public class FrameConstant {
	/**2:组件区左*/
	public static final String COMPONENT_LEFT = "2";
	/**3:组件区右*/
	public static final String COMPONENT_RIGHT = "3";
	/**4:组件区平铺*/
	public static final String COMPONENT_FLAT = "4";
	/**frame.html文件*/
	public static final String FRAME_HTML_FILE = "/net/lising/frame/html/frame.html";
	/**body.html文件*/
	public static final String BODY_HTML_FILE = "/net/lising/frame/html/body.html";
	/**即时通讯im.html文件*/
	public static final String IM_HTML_FILE = "/net/lising/frame/html/im.html";
	/**搜索search.html文件*/
	public static final String SEARCH_HTML_FILE = "/net/lising/frame/html/search.html";
	/**搜索search.html文件*/
	public static final String ERROR_FILE_NAME = "/net/lising/frame/html/error.html";
	/**【缺省用户头像】statics/images/header/user.png*/
	public static final String DEFAULT_USER_PHOTO = "statics/images/header/user.png";
	/**【缺省公司logo】statics/images/header/logo.png*/
	public static final String DEFAULT_COMPANY_LOGO = "statics/images/header/logo.png";
	/**用于记录当前工程1级导航*/
	public static final String CURRENT_LEVEL_ONE_NAVIGATOR = "sid";
	/**用于记录当前工程2级导航*/
	public static final String CURRENT_LEVEL_TWO_NAVIGATOR = "sid2";
	/**【缺省样式】*/
	public static final String DEFAULT_SKIN = "skin1.css";
	/**当前选中皮肤样式开关*/
	public static final String SELECTED_SKIN = "skin-on";
	/**皮肤样式静态文件地址*/
	public static final String SKIN_STATIC_PATH = "statics/styles/skin/";
	/**sitemesh替换区域‘&ltsitemesh:write property='body'/&gt’*/
	public static final String SITEMESH_REPLACE_AREA = "<sitemesh:write property='body'/>";
	/**需清除session的工程的urls*/
	public static final String LOGOUT_URLS = "'{LOGOUT-URLS}'";
	/**当前皮肤样式*/
	public static final String CURRENT_SKIN_CSS = "'{CURRENT-SKIN-CSS}'" ;	
	/**网店地址*/
	public static final String LISING_NETSHOP_URL = "'{LISING-NETSHOP-URL}'" ;
	/**静态文件服务器地址*/
	public static final String HFS_SERVER_URL = "'{HFS-SERVER-URL}'" ;
	/**当前工程名称*/
	public static final String CURRENT_PROJECT_URL = "'{CURRENT-PROJECT-URL}'" ;
	/**公司名称*/
	public static final String CURRENT_COMPANY_NAME = "'{CURRENT-COMPANY-NAME}'" ;
	/**个人设置*/
	public static final String USER_CONFIG = "'{USER-CONFIG}'";
	/**【用户名称】*/
	public static final String CURRENT_USER_NAME = "'{CURRENT-USER-NAME}'";
	/**用户【头像】URL*/
	public static final String USER_PHOTO_URL = "'{USER-PHOTO-URL}'";
	/**公司【logo】URL*/
	public static final String COMPONY_LOGO_URL = "'{COMPONY-LOGO-URL}'";
	/**缺省【logo】URL*/
	public static final String DEFAULT_LOGO_URL = "'{DEFAULT-LOGO-URL}'";
	/**当前用户登录所在地【城市名称】*/
	public static final String CURRENT_CITY_NAME = "'{CURRENT-CITY-NAME}'";
	/**默认当前用户登录所在地【1:北京】*/
	public static final String DEFALUT_CITY_NAME = "1";
	/**用户【职位】*/
	public static final String CURRENT_USER_POSITION= "'{CURRENT-USER-POSITION}'";
	/**【个人名片】地址*/
	public static final String PERSONAL_CARD_URL = "'{PERSONAL-CARD-URL}'" ;	
	/**当前用户ID	*/
	public static final String CURRENT_USER_ID = "'{CURRENT-USER-ID}'"	;
	/**当前【员工ID】*/
	public static final String CURRENT_EMPLOYEE_ID = "'{CURRENT-EMPLOYEE-ID}'";	
	/**当前【公司ID】*/
	public static final String CURRENT_COMPANY_ID = "'{CURRENT-COMPANY-ID}'" ;
	/**【IM替换】区域*/
	public static final String IM_REPLACE_AREA = "'{IM-REPLACE-AREA}'" ;
	/**【搜索替换区域】*/
	public static final String SEARCH_REPLACE_AREA = "'{SEARCH-REPLACE-AREA}'";
	/**【搜索计划】action地址*/
	public static final String SEARCH_PLAN_ACTION = "'{SEARCH-PLAN-ACTION}'";
	/**【搜索文档】action地址*/
	public static final String SEARCH_DOC_ACTION = "'{SEARCH-DOC-ACTION}'";
	/**【搜索旅行社】action地址*/
	public static final String SEARCH_TRAVEL_ACTION = "'{SEARCH-TRAVEL-ACTION}'";
	/**【搜索首页】地址*/
	public static final String SEARCH_INDEX_ACTION = "'{SEARCH-INDEX-ACTION}'";
	/**【个人皮肤】地址*/
	public static final String PERSONAL_SKIN_URL = "'{PERSONAL-SKIN-URL}'";
	/**【一级导航条】替换区域*/
	public static final String LEVEL_ONE_NAV_REPLACE_AREA = "'{LEVEL-ONE-NAV-REPLACE-AREA}'";
	/**【二级导航条】替换区域*/
	public static final String LEVEL_TWO_NAV_REPLACE_AREA = "'{LEVEL-TWO-NAV-REPLACE-AREA}'";
	/**【左侧组件】替换区域*/
	public static final String LEFT_COMPONENT_REPLACE_AREA = "'{LEFT-COMPONENT-REPLACE-AREA}'";
	/**【右侧组件】替换区域*/
	public static final String RIGHT_COMPONENT_REPLACE_AREA = "'{RIGHT-COMPONENT-REPLACE-AREA}'";
	/**【平铺组件】替换区域*/
	public static final String FLAT_COMPONENT_REPLACE_AREA = "'{FLAT-COMPONENT-REPLACE-AREA}'";
	/**【IM服务器】地址*/
	public static final String IM_SERVER_URL = "'{IM-SERVER-URL}'";
	/**【System工程】地址*/
	public static final String LISING_SYSTEM_URL="'{LISING-SYSTEM-URL}'" ;
	/**【Order工程】地址*/
	public static final String LISING_ORDER_URL = "'{LISING-ORDER-URL}'" ;
	/**【manage工程】地址*/
	public static final String LISING_MANAGE_URL= "'{LISING-MANAGE-PROJECT-URL}'";
	/**【finance工程】地址*/
	public static final String LISING_FINANCE_URL= "'{LISING-FINANCE-PROJECT-URL}'";
	/**【plan工程】地址*/
	public static final String LISING_PLAN_URL= "'{LISING-PLAN-PROJECT-URL}'";
	/**【错误信息】*/
	public static final String ERROR_MESSAGE= "'{ERROR-MESSAGE}'";
	/**一级导航标识,对应数据库：menuPosition*/
	public static final String LEVEL_ONE_NAVIGATOR = "0";
	/**body区域左侧组件识别码*/
	public static final int LEFT_SIZE_NUMBER = 2;
	/**二级菜单识别码*/
	public static final int LEVEL_TWO_NUMBER = 1;
	/**权限辅助表json数据*/
	public static final String AUXILIARY_RIGHT_JSON = "'{AUXILIARY-RIGHT-JSON}'";
	/**当前当行区域*/
	public static final String CURRENT_NAVIGATOR_AREA = "'{CURRENT-NAVIGATOR-AREA}'";
	
	@Override
	public String toString() {
		return "框架常量类，用于针对一些字符串的描述。";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	/***/
//			public static final String = 
			
			
	}

}
