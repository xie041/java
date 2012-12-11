package net.lising.urls;

import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * 工程基础配置文件
 * 
 * @author xie041@126.com  QQ:190221242
 * @author xie041
 */
public class ProjectUtil {
	
	private static Logger log = Logger.getLogger(ProjectUtil.class);
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("core");
	
	/**
	 * 项目在线运行还是本地开发模式:在线true，开发false
	 */
	private static final String ONLINE = "online";
	
	/**
	 * Develop mode
	 */
	private static final String DEBUG = "debug";
	
	/**
	 * windows上传地址     D:/files/
	 */
	private static final String HFS_SYSTEM_WINDOWS = "hfs_system_windows";
	/**
	 * linux上传地址      /www/hfs/
	 */
	private static final String HFS_SYSTEM_LINUX = "hfs_system_linux";
	
	/**
	 * coreseek服务器IP
	 */
	private static final String CORESEEK_SERVER_IP = "coreseek_server_ip";
	/**
	 * 51book 代理码
	 */
	private static final String BOOK51_AGENCYCODE = "book51_agencyCode";
	/**
	 * 51book安全码
	 */
	private static final String BOOK51_SIGNCODE = "book51_signCode";
	
	/**
	 * 登陆标示，用于javascript弹出消息，读取使用
	 * 读取之后，让标示设置为false
	 */
	public static final String LOGINFLAG = "loginflag";
	
	/**
	 * 用户session存入的key
	 */
	public static final String CTX = "ctx";
	public static final String CITYID = "cityId";
	
	public static final String CORESEEK_DEFAULT_SEARCH_SIZE = "coreseek_default_search_size";
	
	/**
	 * 字符串分隔符  _1lvl1_
	 * split sembol  eg: look at this _1lvl1_ delimiter by string
	 */
	public static final String SPECIAL_DELIMITER="_1lvl1_";
	
	/**
	 * SSO不拦截的安全URL地址
	 */
	private static final String IGNOREURLS = "ignoreUrls";
	
	/**
	 * 文件上传,公司文档、营业执照、
	 */
	private static final String UPLOAD_COMPANY_DOCUMENTS = "upload_company_documents";
	private static final String UPLOAD_COMPANY_BUSINESS_LICENSE = "upload_company_business_license";
	private static final String UPLOAD_COMPANY_PHOTOS = "upload_company_photos";
	private static final String PATH_PLAN_IMG = "path_plan_img";
	/**分区广告图片地址*/
	private static final String PATH_POPULARIZE_AD_IMG = "path_popularize_ad_img";
	/**关键字广告图片地址*/
	private static final String PATH_KEYWORD_AD_IMG = "path_keyword_ad_img";
	private static final String PATH_PLAN_DOC = "path_plan_doc";
	private static final String PATH_PLAN_DOC_TEMPLATE = "path_plan_doc_template";
	private static final String PATH_STATISTICS_IMG = "path_statistics_img";
	private static final String UPLOAD_USER_PORTRAIT = "upload_user_portrait";
	
	/**旅游圈***/
	private static final String NEWS_STATIC_HTML = "news_static_html";
	private static final String NEWS_IMAGE = "news_image";
	private static final String SOCIAL_GROUPON = "social_groupon";
	
	/**个人简历word文档保存路径*/
	private static final String RESUME_DOC_PATH = "resume_doc";
	/**个人照片保存路径*/
	private static final String RESUME_PHOTO_PATH = "resume_photo";
	
	/**
	 * html静态化的时候，生成的ftl模版存储路径
	 */
	private static final String TEMPLATE = "template";
	
	/**frame框架控制*/
	/**框架控制：用于判断是否调入组件，默认true*/
	private static final String FRAME_ISLOADCOMPONENT = "isLoadComponent";
	/**框架控制： 用于判断是否打印框架信息，默认false*/
	private static final String FRAME_ISPRINT = "isPrint";
	/**框架控制： 用于设定忽略了IM的所有工程*/
	private static final String FRAME_IGNORE_IM_PROJECT = "ignoreIMProject";
	
	/**主题图片路径*/
	private static final String THEME_IMAGE_PATH = "upload_theme_img";
	
	static{
		if(getDevelopeMode()){
			StringBuffer bf = new StringBuffer("lising-core.jar使用须知：\n");
			bf.append("lisingsoft---->:本地开发，复制lising-common项目下的core.properties文件(config目录下),修改online=false,修改每个项目的真实地址\n");
			bf.append("lisingsoft---->:如上步骤，修改自己项目的ip地址，如：http://ip:port/project-name");
			log.info(bf.toString());
			log.debug(bf.toString()); 
		}
	}
	
	/**
	 * <pre>
	 * 工具操作系统，获取不同的文件上传地址
	 * hfs_system_windows=D:/files/
	 * hfs_system_linux=/www/hfs/
	 * </pre>
	 * @return
	 */
	public static String getFileUploadAddressInDisk(){
		if(System.getProperty("os.name").toLowerCase().contains("linux")){
			return getValueByKey(HFS_SYSTEM_LINUX);
		}else{
			return getValueByKey(HFS_SYSTEM_WINDOWS);
		}
		//Do not think of MAC OS
	}
	
	/**
	 * <pre>
	 * 公司文档
	 * 
	 * linux :   /www/hfs/cmp/docs/1/aaaa.doc
	 * windows:  d:/files/cmp/docs/1/aaaa.doc
	 * 
	 * 都应该添加ID，比如公司的，就需要添加公司id   
	 * 例如公司ID为5，则其上传的文档：D:/files/cmp/docs/5/bbbb.doc
	 * 
	 * 用户的，则应该添加用户的ID
	 * 用户ID为23，则头像位置应该为：D:/files/user/portrait/23/1212143434.jpg
	 * 
	 * 其他一次类推
	 * </pre>
	 * @return
	 */
	public static String getUploadOfCompanyDocuments(){
		return getValueByKey(UPLOAD_COMPANY_DOCUMENTS);
	}
	/**
	 * <pre>
	 * 公司相册
	 * 
	 * linux :   /www/hfs/cmp/docs/1/aaaa.doc
	 * windows:  d:/files/cmp/docs/1/aaaa.doc
	 * 
	 * 都应该添加ID，比如公司的，就需要添加公司id   
	 * 例如公司ID为5，则其上传的文档：D:/files/cmp/docs/5/bbbb.doc
	 * 
	 * 用户的，则应该添加用户的ID
	 * 用户ID为23，则头像位置应该为：D:/files/user/portrait/23/1212143434.jpg
	 * 
	 * 其他一次类推
	 * </pre>
	 * @return
	 */
	public static String getUploadOfCompanyPhotoes(){
		return getValueByKey(UPLOAD_COMPANY_PHOTOS);
	}
	
	/**
	 * <pre>
	 * 公司营业执照
	 * 
	 * linux :   /www/hfs/cmp/docs/1/aaaa.doc
	 * windows:  d:/files/cmp/docs/1/aaaa.doc
	 * 
	 * 都应该添加ID，比如公司的，就需要添加公司id   
	 * 例如公司ID为5，则其上传的文档：D:/files/cmp/docs/5/bbbb.doc
	 * 
	 * 用户的，则应该添加用户的ID
	 * 用户ID为23，则头像位置应该为：D:/files/user/portrait/23/1212143434.jpg
	 * 
	 * 其他一次类推
	 * </pre>
	 * @return
	 */
	public static String getUploadOfCompanyBusinessLicense(){
		return getValueByKey(UPLOAD_COMPANY_BUSINESS_LICENSE);
	}
	
	/**
	 * 计划图片目录：cmp/plan_img/
	 */
	public static String getPathOfPlanImages(){
		return getValueByKey(PATH_PLAN_IMG);
	}
	/**
	 * 分区广告图片目录：cmp/popularize_img/ad/
	 */
	public static String getPathOfPopularizeAdImages(){
		return getValueByKey(PATH_POPULARIZE_AD_IMG);
	}
	/**
	 * 分区广告图片目录：cmp/popularize_img/kwad/
	 */
	public static String getPathOfKeywordAdImages(){
		return getValueByKey(PATH_KEYWORD_AD_IMG);
	}
	
	/**
	 * 计划文档目录：cmp/plan_doc/
	 */
	public static String getPathOfPlanDoc() {
		return getValueByKey(PATH_PLAN_DOC);
	}
	
	/**
	 * 计划文档模版目录：cmp/plan_doc/template
	 */
	public static String getPathOfPlanDocTemplate() {
		return getValueByKey(PATH_PLAN_DOC_TEMPLATE);
	}

	/**
	 * 财务统计图目录：finance/statistics/
	 */
	public static String getPathOfStatisticsBar() {
		return getValueByKey(PATH_STATISTICS_IMG);
	}
	
	/**
	 * <pre>
	 * 用户头像
	 * 
	 * linux :   /www/hfs/cmp/docs/1/aaaa.doc
	 * windows:  d:/files/cmp/docs/1/aaaa.doc
	 * 
	 * 都应该添加ID，比如公司的，就需要添加公司id   
	 * 例如公司ID为5，则其上传的文档：D:/files/cmp/docs/5/bbbb.doc
	 * 
	 * 用户的，则应该添加用户的ID
	 * 用户ID为23，则头像位置应该为：D:/files/user/portrait/23/1212143434.jpg
	 * 
	 * 其他一次类推
	 * </pre>
	 * @return
	 */
	public static String getUploadOfUserPortrait(){
		return getValueByKey(UPLOAD_USER_PORTRAIT);
	}
	
	/**
	 * 返回上传主题的图片路径
	 * @return
	 */
	public static String getUploadThemeImagePath() {
		return getValueByKey(THEME_IMAGE_PATH);
	}
	/**
	 * 获取SSO服务器地址  http://192.168.11.16:8090/cas-server/
	 * @return
	 */
	@Deprecated
	public static String getSsoServerAddress(){
		return getValueByKey(ProjectEnum.sso_server.getProjectName());
	}
	public static String getSsoProjectAddress(){
		return getValueByKey(ProjectEnum.sso_server.getProjectName());
	}
	
	/**
	 * 登陆地址
	 * sso-client  ---》 org.jasig.cas.client.authentication.AuthenticationFilter中配置
	 * @see org.jasig.cas.client.authentication.AuthenticationFilter  #initInternal(final FilterConfig filterConfig) 
	 * http://192.168.11.16:8090/cas-server/login
	 * @return
	 */
	public static String getSsoServerAddressLoginAddress(){
		return getSsoProjectAddress()+"login";
	}
	
	/**
	 * sso退出地址
	 * @return
	 */
	public static String getSsoServerAddressLogoutAddress(){
		return getSsoProjectAddress()+"logout";
	}
	/**
	 * IM server服务器地址
	 * http://192.168.11.16:8090/lising-im/
	 * @return
	 */
	@Deprecated
	public static String getImChatServerAddress(){
		return getValueByKey(ProjectEnum.lising_im.getProjectName());
	}
	/**
	 * IM server服务器地址
	 * http://192.168.11.16:8090/lising-im/
	 * @return
	 */
	public static String getImProjectAddress(){
		return getValueByKey(ProjectEnum.lising_im.getProjectName());
	}
	/**
	 * IM server服务心跳检测
	 * http://192.168.11.16:8090/lising-im/pushlet.srv
	 * @return
	 */
	public static String getImHeartbeatCheckerAddress(){
		return getImProjectAddress()+"pushlet.srv";
	}
	
	/**
	 * 获取缓存服务器地址
	 * 192.168.11.17:11211
	 * @return
	 */
	public static String getMemcachedAddress(){
		return getValueByKey(ProjectEnum.mem_server.getProjectName());
	}
	
	/**
	 * * lising_org_rmt_hessian=http://192.168.11.16:8080/lising-org/rmt/orgHessianService
	 * lising_finance_rmt_hessian=http://192.168.11.16:8082/lising-finance/rmt/rmtFinanceService
	 * lising_order_rmt_hessian=http://192.168.11.16:8081/lising-order/rmt/orderService
	 * lising_plan_rmt_hessian=http://192.168.11.16:8081/lising-plan/rmt/planService
	 * lising_manage_rmt_hessian=http://192.168.11.16:8082/lising-manage/rmt/touristService
	 * lising_im_rmt_hessian=http://192.168.11.16:8090/lising-im/rmt/rmtMsgService
	 * @return
	 */
	public static String getRmtUrlOfFinance(){
		return getFinanceProjectAddress()+"rmt/rmtFinanceService";
	}
	
	/**
	 * * lising_org_rmt_hessian=http://192.168.11.16:8080/lising-org/rmt/orgHessianService
	 * lising_finance_rmt_hessian=http://192.168.11.16:8082/lising-finance/rmt/rmtFinanceService
	 * lising_order_rmt_hessian=http://192.168.11.16:8081/lising-order/rmt/orderService
	 * lising_plan_rmt_hessian=http://192.168.11.16:8081/lising-plan/rmt/planService
	 * lising_manage_rmt_hessian=http://192.168.11.16:8082/lising-manage/rmt/touristService
	 * lising_im_rmt_hessian=http://192.168.11.16:8090/lising-im/rmt/rmtMsgService
	 * @return
	 */
	public static String getRmtUrlOfIm(){
		return getImProjectAddress()+"rmt/rmtMsgService";
	}
	/**
	 * * lising_org_rmt_hessian=http://192.168.11.16:8080/lising-org/rmt/orgHessianService
	 * lising_finance_rmt_hessian=http://192.168.11.16:8082/lising-finance/rmt/rmtFinanceService
	 * lising_order_rmt_hessian=http://192.168.11.16:8081/lising-order/rmt/orderService
	 * lising_plan_rmt_hessian=http://192.168.11.16:8081/lising-plan/rmt/planService
	 * lising_manage_rmt_hessian=http://192.168.11.16:8082/lising-manage/rmt/touristService
	 * lising_im_rmt_hessian=http://192.168.11.16:8090/lising-im/rmt/rmtMsgService
	 * @return
	 */
	public static String getRmtUrlOfManage(){
		return getManageProjectAddress()+"rmt/manageService";
	}
	/**
	 * * lising_org_rmt_hessian=http://192.168.11.16:8080/lising-org/rmt/orgHessianService
	 * lising_finance_rmt_hessian=http://192.168.11.16:8082/lising-finance/rmt/rmtFinanceService
	 * lising_order_rmt_hessian=http://192.168.11.16:8081/lising-order/rmt/orderService
	 * lising_plan_rmt_hessian=http://192.168.11.16:8081/lising-plan/rmt/planService
	 * lising_manage_rmt_hessian=http://192.168.11.16:8082/lising-manage/rmt/touristService
	 * lising_im_rmt_hessian=http://192.168.11.16:8090/lising-im/rmt/rmtMsgService
	 * @return
	 */
	public static String getRmtUrlOfPlan(){
		return getPlanProjectAddress()+"rmt/planService";
	}
	
	/**
	 * 支付宝接受数据的地址
	 * http://192.168.11.16:8082/lising-finance/pages/notify_url.jsp
	 * @return
	 */
	public static String getAlipayServiceNotifyUrl(){
		return getFinanceProjectAddress() + "pages/alipay/notify_url.jsp";
	}
	/**
	 * 支付宝付款成功后，跳回财务项目的地址
	 * http://192.168.11.16:8082/lising-finance/alipay/receive.do
	 */
	public static String getAlipayReturnToAlipay(){
		return getFinanceProjectAddress() + "alipay/receive.do";
	}	
	
	/**
	 * 全搜索项目地址
	 * http://192.168.11.16:8081/lising-search/
	 * @return
	 */
	public static String getSearchProjectAddress(){
		return getValueByKey(ProjectEnum.lising_search.getProjectName());
	}
	
	/**
	 * 临时search地址
	 * http://192.168.11.16:8081/lising-search/search.do
	 * @return
	 */
	public static String getSearchActionAddress(){
		return getSearchProjectAddress() + "search.do";
	}
	
	/**
	 * 个人中心
	 * user_center=http://192.168.11.16:8080/lising-org/user/info.do
	 * @return
	 */
	public static String getUserCenter(){
		return getManageProjectAddress()+"special/user/info.do";
	}
	/**
	 * IM列表获取用户默认头像
	 * @return
	 */
	public static String getImUserDefaultIcon(){
		return getHfsAddress()+"statics/im/images/im-default.gif";
	}
	/**
	 * 用户登陆地址
	 * login_url=http://192.168.11.16:8082/lising-manage/
	 * @return
	 */
	public static String getLoginUrl(){
		return getManageProjectAddress();
	}
	/**
	 * 公司注册地址
	 * #regist as company role
	 * regist_as_company=http://192.168.11.16:8080/lising-org/reg/select.do
	 * #regist as customer role
	 * regist_as_personal=http://192.168.11.16:8080/lising-org/reg/personal.do
	 * @return
	 */
	public static String getRegisterAsCompanyUrl(){
		return getManageProjectAddress()+"special/reg/select.do";
	}
	/**
	 * 个人注册地址
	 * @return
	 */
	public static String getRegisterAsPersonalUrl(){
		return getManageProjectAddress()+"special/reg/personal.do";
	}
	
	/**
	 * lising-common的工程地址
	 * http://192.168.11.16:8080/lising-common/
	 * @return
	 */
	public static String getCommonProjectAddress(){
		return getValueByKey(ProjectEnum.lising_common.getProjectName());
	}
	
	/**
	 * 基础数据服务：省份、城市、ip、省份证等等
	 * http://192.168.11.16:8080/lising-common/rmi/base
	 * @return
	 */
	@Deprecated
	public static String getBaseDataService(){
		return getCommonProjectAddress()+"rmi/base";
	}
	/**
	 * 基础数据服务：省份、城市、ip、省份证等等
	 * http://192.168.11.16:8080/lising-common/rmi/base
	 * @return
	 */
	public static String getRmtUrlOfCommon(){
		return getCommonProjectAddress()+"rmi/base";
	}
	
	/**
	 * coreseek服务器Ip
	 * @return
	 */
	public static String getCoreseekServerIp(){
		return getValueByKey(CORESEEK_SERVER_IP);
	}
	/**
	 * 51book代理码
	 * @return
	 */
	public static String get51BookAgencyCode(){
		return getValueByKey(BOOK51_AGENCYCODE);
	}
	/**
	 * 51book安全码
	 * @return
	 */
	public static String get51BookSignCode(){
		return getValueByKey(BOOK51_SIGNCODE);
	}
	
	/**
	 * 通过工程名去获取IP地址
	 * @param projectName
	 * @return
	 */
	public static String getIpAddressByProjectName(String projectName){
		String temp = null;
		try {
			temp = getValueByKey(projectName);
		} catch (Exception e) {
			StringBuffer bf = new StringBuffer("----------------------------------------------------------\n");
			bf.append("异常原因：\n");
			bf.append("lising-common项目中->hfs-core.properties中缺少配置:").append(projectName+"\n");
			bf.append("----------------------------------------------------------");
			log.error(bf.toString());
		}
		return temp;
	}
	
	/**
	 * 获取当前工程的url地址和端口
	 * http://192.168.11.16:8082/
	 * @param request
	 * @return
	 */
	public static String getCurrentProjectUrl(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/";
	}
	
	/**
	 * 获取当前工程名称
	 * lising-plan
	 * @param request
	 * @return
	 */
	public static String getCurrentProjectName(HttpServletRequest request){
		String project = request.getContextPath();
		return project.substring(1, project.length());
	}
	
	/**
	 * http://localhost:8080/lising-demo/
	 * 获取当前工程action请求的绝对地址
	 * @param request
	 * @return
	 */
	public static String getCurrentProjectActionAddress(HttpServletRequest request){
		return getCurrentProjectUrl(request) + getCurrentProjectName(request) + "/";
	}
	
	/**
	 * 获取当前项目静态文件服务器地址
	 * @param request
	 * @return
	 */
	public static String getCurrentProjectStaticFilesAddress(HttpServletRequest request){
		if(getOnline()){
			//如果是在线运行模式，则应该访问文件服务器  http://192.168.11.17:8001/project-name/
			//文件服务器下的目录结构
			//-----/www/hfs/
			//-------------/project-name/
			return getHfsAddress() + getCurrentProjectName(request) + "/";
		}
		//开发模式，直接访问当前工程下的静态文件
		return getCurrentProjectActionAddress(request);
	}
	
	/**
	 * <pre>
	 * 静态文件服务器地址
	 * fileserver=http://localhost:8001/
	 * fileserver=http://192.168.11.16:8080/lising-common/
	 * </pre>
	 * @return
	 */
	public static String getHfsAddress(){
		return getValueByKey(ProjectEnum.hfs_server.getProjectName());
	}
	
	/**
	 * lising-org=http://192.168.11.16:8080/lising-plan/
	 * 原因是配置文件没有添加工程名称
	 * @return
	 */
	public static String getPlanProjectAddress(){
		return getValueByKey(ProjectEnum.lising_plan.getProjectName());
	}
	/**
	 * lising-org=http://192.168.11.16:8080/lising-manage/
	 * 原因是配置文件没有添加工程名称
	 * @return
	 */
	public static String getManageProjectAddress(){
		return getValueByKey(ProjectEnum.lising_manage.getProjectName());
	}
	/**
	 * lising-org=http://192.168.11.16:8080/lising-finance/
	 * 原因是配置文件没有添加工程名称
	 * @return
	 */
	public static String getFinanceProjectAddress(){
		return getValueByKey(ProjectEnum.lising_finance.getProjectName());
	}
	
	/**
	 * 获取默认工程
	 * @return
	 */
	public static String getDefaultProject(){
		return getSearchProjectAddress();
	}
	
	/**
	 * 判断开发模式，在线运行还是本地开发模式
	 * @return
	 */
	public static boolean getOnline(){
		if("true".equals(getValueByKey(ONLINE))){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取全搜索默认词
	 * @return
	 */
	public static String getDefaultSearchWords(){
		return "北京";
	}
	
	/**
	 * 获取全搜索条数限制
	 * @return
	 */
	public static int getCoreseekDefaultSearchSize(){
		int value;
		try {
			value = Integer.parseInt(getValueByKey(CORESEEK_DEFAULT_SEARCH_SIZE));
		} catch (NumberFormatException e) {
			value = 1000;
		}
		return value;
	}
	
	
	
	
	/**
	 * 是否调入框架组件,ture OR false
	 * @return
	 */
	public static boolean getFrameIsLoadComponent(){
		String val = getValueByKey(FRAME_ISLOADCOMPONENT);
		if("true".equals(val)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 是否打印框架信息,true OR false
	 * @return
	 */
	public static boolean getFrameIsPrint(){
		String val = getValueByKey(FRAME_ISPRINT);
		if("true".equals(val)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取所有忽略了调入IM的工程名称，包括:lising-news,lising-search
	 * @return
	 */
	public static String getIgnoreIMProject(){
		String val = getValueByKey(FRAME_IGNORE_IM_PROJECT);
			return val;
	}
	
	/**
	 * 是否打印框架读取的所有信息，包括权限等等,debug mode
	 * @return
	 */
	public static boolean getFrameIsPrintInfomationToConsole(){
		return getDevelopeMode();
	}
	
	/**
	 * 获取开发模式,debug=true or false
	 * @return
	 */
	public static boolean getDevelopeMode(){
		String val = getValueByKey(DEBUG);
		if("true".equals(val)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取SSO不拦截的URL地址
	 * @return
	 */
	public static String getIgnoreUrls(){
		return getValueByKey(IGNOREURLS);
	}
	
	/**
	 * 获取coreseek词典生成后保存路径
	 * @return
	 */
	public static String getCoreseekDictionarySavePath(){
		if(System.getProperty("os.name").toLowerCase().contains("linux")){
			return getValueByKey("coreseek_dictionary_linux");
		}else{
			return getValueByKey("coreseek_dictionary_windows");
		}
	}
	
	/**
	 * 旅游圈
	 * @return
	 */
	public static String getNewsProjectAddress(){
		return getValueByKey(ProjectEnum.lising_news.getProjectName());
	}
	/**
	 * 资讯静态文件存储路径
	 * @return
	 */
	public static String getNewsStaticHtmlPath(){
		return getValueByKey(NEWS_STATIC_HTML);
	}
	/**
	 * 资讯图片上传路径
	 * @return
	 */
	public static String getNewsImageUploadPath(){
		return getValueByKey(NEWS_IMAGE);
	}
	/**
	 * 团购/秒杀，图片上传路径
	 * @return
	 */
	public static String getGrouponImagePath(){
		return getValueByKey(SOCIAL_GROUPON);
	}
	
	/**
	 * 个人简历word文档上传路径
	 * @return
	 */
	public static String getResumeDocPath(){
		return getValueByKey(RESUME_DOC_PATH);
	}
	
	/**
	 * 个人照片上传路径
	 * @return
	 */
	public static String getResumePhotoPath(){
		return getValueByKey(RESUME_PHOTO_PATH);
	}
	
	
	/**
	 * @return
	 */
	public static String getNewsTemplatePath(){
		return getValueByKey(TEMPLATE);
	}
	
	/**
	 * 通过键获取
	 * @param key
	 * @return
	 */
	private static String getValueByKey(String key){
		String temp = "";
		try {
			temp = bundle.getString(key);
		} catch (Exception e) {
			StringBuffer bf = new StringBuffer("lising-core.jar can't find key : ");
			bf.append(key).append(" in hfs-core.properties  \n");
			bf.append("如果是本地测试项目，本地复制了lising-common项目的hfs-core.properties文件，请重新复制一个最新的hfs-core文件\n");
			bf.append("如果是在线项目，请检查hfs-core文件，是不是删除了\""+key+"\"\n");
			bf.append("如果仍然不能解决：please contact xie041@126.com");
			log.error(bf.toString());
		}
		return temp;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getHfsAddress());
		System.out.println(getMemcachedAddress());
		
		System.out.println("\n\n\n");
		
		System.out.println(getSsoProjectAddress());
		System.out.println(getImProjectAddress());
		System.out.println(getPlanProjectAddress());
		System.out.println(getManageProjectAddress());
		System.out.println(getSearchProjectAddress());
		System.out.println(getFinanceProjectAddress());
		System.out.println(getCommonProjectAddress());
		
		System.out.println("\n\n\n");
		
		System.out.println(getRmtUrlOfFinance());
		System.out.println(getRmtUrlOfIm());
		System.out.println(getRmtUrlOfManage());
		System.out.println(getRmtUrlOfPlan());
		System.out.println(getRmtUrlOfCommon());
		
		System.out.println("\n");
		System.out.println(getFileUploadAddressInDisk());
		System.out.println(getCoreseekDictionarySavePath());
	}
}