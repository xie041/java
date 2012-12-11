/**
 * 
 */
package net.lising.frame.vo;

/** 
 * @Description : 框架参数vo,
 * 包括：<br>
 *	<table border=1>
 *	<thead><th>参数类型</th><th>参数名称</th><th>参数描述</th><th>参数类型</th><th>参数名称</th><th>参数描述</th></thead>
 *	<tr><td>boolean</td><td><strong>isFirst</strong></td><td>是否第一次调入框架</td><td>String</td><td><strong>userId</strong></td><td>用户ID</td></tr>
 *	<tr><td>String</td><td><strong>HFSServerIP</strong></td><td>静态服务器地址</td><td>String</td><td><strong>userName</strong></td><td>用户名称</td></tr>
 *	<tr><td>boolean</td><td><strong>isLoadComponent</strong></td><td>判断是否生成【组件】区域</td><td>String</td><td><strong>userPhotoUrl</strong></td><td>用户头像地址</td></tr>
 *	<tr><td>String</td><td><strong>projectPath</strong></td><td>工程文件路径</td><td>String</td><td><strong>userPosition</strong></td><td>用户职务</td></tr>
 *	<tr><td>String</td><td><strong>projectUrl</strong></td><td>工程IP地址</td><td>String</td><td><strong>companyName</strong></td><td>公司名称</td></tr>
 *	<tr><td>String</td><td><strong>projectName</strong></td><td>工程名称</td><td>String</td><td><strong>employeeId</strong></td><td>员工ID</td></tr>
 *	<tr><td>String</td><td><strong>serialId</strong></td><td>一级菜单序列号</td><td>String</td><td><strong>projectURLs</strong></td><td>需要清除session的工程</td></tr>
 *	<tr><td>String</td><td><strong>serialId2</strong></td><td>二级菜单序列号</td><td>String</td><td><strong>currentSkin</strong></td><td>当前用户使用的皮肤样式</td></tr>
 *	<tr><td>String</td><td><strong>imChartServer</strong></td><td>IM聊天服务器地址</td><td>String</td><td><strong>errorMessage</strong></td><td>错误消息</td></tr>
 *	</table>
 * @author : 吕佳诚
 * @CreateDate : 2012-4-11 下午02:42:36 
 * @lastModified : 2012/6/5 10:42
 * @version : 1.0  
 */
public class ParameteVO {
	
	/**是否第一次调入框架，用于判断是否添加组件列表，默认false*/
	private boolean isFirst = false ;
	/**静态服务器地址*/
	private String HFSServerURL;
	
	/**判断是否生成【组件】区域，默认false*/
	private boolean isLoadComponent = false ;
	
	/**判断是否调入IM工程，默认true*/
	private boolean isLoadIM = true ;
	
	/** 工程本地路径,例如：D:\Project\tomcat\Tomcat 6.0.20\webapps\lising-common\   */
	private String projectPath;

	/** 工程IP地址,例如：http://localhost:8080/  */
	private String projectUrl;
	
	/**  工程名称,例如：lising-common/   */
	private String projectName;

	/**当前选中工程的ID,如：我的工作*/
	private String serialId;
	
	private String serialId2;
	
	/**IM聊天服务器地址*/
	private String imChartServer;
	
	/**用户ID*/
	private String userId;
	/**用户名称*/
	private String userName;
	/**用户头像地址*/
	private String userPhotoUrl;
	/**用户职务*/
	private String userPosition;
	/**公司名称*/
	private String companyName;
	/**公司品牌*/
	private String companyBrandname;
	/**公司ID*/
	private String companyId;
	/**员工ID*/
	private String employeeId;
	
	/**需要清除session的工程 **/
	private String logoutProjectURLs;
	
	/**当前用户使用的皮肤样式**/
	private String currentSkin;
	
	/**错误信息*/
	private String errorMessage = "404";
	
	/**
	 * 权限辅助表json数据
	 */
	private String auxiliaryRightJSON;

	/**公司logoURL*/
	private String companyLogoUrl;
	
	/**当前登录城市*/
	private String currentLoginCity;
	
	/**  
	 * <strong>获取</strong>  是否第一次调入框架，用于判断是否添加组件列表，默认false<br>
	 * @return isFirst	是否第一次调入框架，用于判断是否添加组件列表   ，默认false
	 */
	public boolean isFirst() {
		return isFirst;
	}

	/**  
	 * <strong>设置</strong> 是否第一次调入框架，用于判断是否添加组件列表，默认false
	 * @param      isFirst	是否第一次调入框架，用于判断是否添加组件列表，默认false
	 */
	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	/**  
	 * <strong>获取</strong>  是否调入IM聊天项目，默认true<br>
	 * @return isFirst	是否IM聊天项目  ，默认true
	 */
	public boolean isLoadIM() {
		return isLoadIM;
	}
	/**  
	 * <strong>设置</strong> 是否调入IM聊天项目，默认true
	 * @param      isFirst	是否调入IM聊天项目，默认true
	 */
	public void setIsLoadIM(boolean isLoadIM) {
		this.isLoadIM = isLoadIM;
	}

	/**  
	 * <strong>获取</strong>  hFSServerURL<br>
	 * @return hFSServerURL	hFSServerURL   
	 */
	public String getHFSServerURL() {
		return HFSServerURL;
	}

	/**  
	 * <strong>设置</strong> hFSServerURL
	 * @param      hFSServerURL	hFSServerURL
	 */
	public void setHFSServerURL(String hFSServerURL) {
		HFSServerURL = hFSServerURL;
	}

	/**  
	 * <strong>获取</strong>  判断是否生成【组件】区域，默认false<br>
	 * @return isBody	判断是否生成【组件】区域  ，默认false 
	 */
	public boolean isLoadComponent() {
		return isLoadComponent;
	}

	/**  
	 * <strong>设置</strong> 判断是否生成【组件】区域，默认false
	 * @param      isBody	判断是否生成【组件】区域，默认false
	 */
	public void setIsLoadComponent(boolean isLoadComponent) {
		this.isLoadComponent = isLoadComponent;
	}
	/**  
	 * <strong>获取</strong>  工程本地路径,例如：D:\Project\tomcat\Tomcat 6.0.20\webapps\lising-common\ <br>
	 * @return projectPath	工程路径   
	 */
	public String getProjectPath() {
		return projectPath;
	}

	/**  
	 * <strong>设置</strong> 工程本地路径,例如：D:\Project\tomcat\Tomcat 6.0.20\webapps\lising-common\ 
	 * @param      projectPath	工程路径
	 */
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	/**  
	 * <strong>获取</strong>  工程名称,例如：lising-common/ <br>
	 * @return projectName	工程名称   
	 */
	public String getProjectName() {
		return projectName;
	}

	/**  
	 * <strong>设置</strong> 工程名称,例如：lising-common/ 
	 * @param      projectName	工程名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**  
	 * <strong>获取</strong>  工程IP地址,例如：http://localhost:8080/<br>
	 * @return projectUrl	工程IP地址   
	 */
	public String getProjectUrl() {
		return projectUrl;
	}

	/**  
	 * <strong>设置</strong> 工程IP地址,例如：http://localhost:8080/
	 * @param      projectUrl	工程IP地址
	 */
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	
	/**  
	 * <strong>获取</strong>  当前选中工程的ID<br>
	 * @return serialId	当前选中工程的ID   
	 */
	public String getSerialId() {
		return serialId;
	}

	/**  
	 * <strong>设置</strong> 当前选中工程的ID
	 * @param      serialId	当前选中工程的ID
	 */
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}


	/**  
	 * <strong>获取</strong>  IM聊天服务器地址<br>
	 * @return imChartServer	IM聊天服务器地址   
	 */
	public String getImChartServer() {
		return imChartServer;
	}

	/**  
	 * <strong>设置</strong> IM聊天服务器地址
	 * @param      imChartServer	IM聊天服务器地址
	 */
	public void setImChartServer(String imChartServer) {
		this.imChartServer = imChartServer;
	}

	/**  
	 * <strong>获取</strong>  用户名称<br>
	 * @return userName	用户名称   
	 */
	public String getUserName() {
		return userName;
	}

	/**  
	 * <strong>设置</strong> 用户名称
	 * @param      userName	用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**  
	 * <strong>获取</strong>  用户职务<br>
	 * @return userPosition	用户职务   
	 */
	public String getUserPosition() {
		return userPosition;
	}

	/**  
	 * <strong>设置</strong> 用户职务
	 * @param      userPosition	用户职务
	 */
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	/**  
	 * <strong>获取</strong>  用户职务<br>
	 * @return companyName	用户职务   
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**  
	 * <strong>设置</strong> 用户职务
	 * @param      companyName	用户职务
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**  
	 * <strong>获取</strong>  companyBrandname<br>
	 * @return companyBrandname	companyBrandname   
	 */
	public String getCompanyBrandname() {
		return companyBrandname;
	}

	/**  
	 * <strong>设置</strong> companyBrandname
	 * @param      companyBrandname	companyBrandname
	 */
	public void setCompanyBrandname(String companyBrandname) {
		this.companyBrandname = companyBrandname;
	}

	/**  
	 * <strong>获取</strong>  serialId2<br>
	 * @return serialId2	serialId2   
	 */
	public String getSerialId2() {
		return serialId2;
	}

	/**  
	 * <strong>设置</strong> serialId2
	 * @param      serialId2	serialId2
	 */
	public void setSerialId2(String serialId2) {
		this.serialId2 = serialId2;
	}

	/**  
	 * <strong>获取</strong>  用户ID<br>
	 * @return userId	用户ID   
	 */
	public String getUserId() {
		return userId;
	}

	/**  
	 * <strong>设置</strong> 用户ID
	 * @param      userId	用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**  
	 * <strong>获取</strong>  用户头像地址<br>
	 * @return userPhotoUrl	用户头像地址   
	 */
	public String getUserPhotoUrl() {
		return userPhotoUrl;
	}

	/**  
	 * <strong>设置</strong> 用户头像地址
	 * @param      userPhotoUrl	用户头像地址
	 */
	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

	/**  
	 * <strong>获取</strong>  公司ID<br>
	 * @return companyId	公司ID   
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**  
	 * <strong>设置</strong> 公司ID
	 * @param      companyId	公司ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	/**  
	 * <strong>获取</strong>  需要清除session的工程<br>
	 * @return logoutProjectURLs	需要清除session的工程   
	 */
	public String getLogoutProjectURLs() {
		return logoutProjectURLs;
	}

	/**  
	 * <strong>设置</strong> 需要清除session的工程
	 * @param      logoutProjectURLs	需要清除session的工程
	 */
	public void setLogoutProjectURLs(String logoutProjectURLs) {
		this.logoutProjectURLs = logoutProjectURLs;
	}

	/**  
	 * <strong>获取</strong>  currentSkin<br>
	 * @return currentSkin	currentSkin   
	 */
	public String getCurrentSkin() {
		return currentSkin;
	}

	/**  
	 * <strong>设置</strong> currentSkin
	 * @param      currentSkin	currentSkin
	 */
	public void setCurrentSkin(String currentSkin) {
		this.currentSkin = currentSkin;
	}

	/**  
	 * <strong>获取</strong>  employeeId<br>
	 * @return employeeId	employeeId   
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**  
	 * <strong>设置</strong> employeeId
	 * @param      employeeId	employeeId
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**  
	 * <strong>获取</strong>  errorMessage<br>
	 * @return errorMessage	errorMessage   
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**  
	 * <strong>设置</strong> errorMessage
	 * @param      errorMessage	errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 获取权限辅助表json数据
	 * @return
	 */
	public String getAuxiliaryRightJSON() {
		return auxiliaryRightJSON;
	}
	
	/**
	 * 设置权限辅助表json数据 
	 * @param auxiliaryRightJSON
	 */
	public void setAuxiliaryRightJSON(String auxiliaryRightJSON) {
		this.auxiliaryRightJSON = auxiliaryRightJSON;
	}
	
	/**
	 * 获取 公司logoURL
	 * @return
	 */
	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}

	/**
	 * 设置 公司logoURL
	 * @param companyLogoUrl
	 */
	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}

	/**
	 * 获取 当前登录城市
	 * @return
	 */
	public String getCurrentLoginCity() {
		return currentLoginCity;
	}

	/**
	 * 设置当前登录城市
	 * @param currentLoginCity
	 */
	public void setCurrentLoginCity(String currentLoginCity) {
		this.currentLoginCity = currentLoginCity;
	}

	
	
	
}
