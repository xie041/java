package net.lising.lib;

import static net.lising.lib.LogUtil.log;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * <pre>
 * 类名称：Common  
 * 类描述：  
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-8-10 上午09:58:29  
 * 修改人：xie041  
 * 修改时间：2011-8-10 上午09:58:29  
 * 修改备注：  
 * @version  
 * 获取绝对路径new File(PathUtil.class.getResource("/").getPath()).getParent()+"\\menu\\backMenuFirst.xml"
 * </pre>
 */
public class Common {
	
	/**
	 * hfs.properties文件名
	 */
	@Deprecated
	public static final String HFS = "hfs-core";
	
	/**
	 * 静态文件服务器地址 fileserver=http://192.168.11.16:8001/
	 */
	@Deprecated
	public static final String HFSFILESERVER = "fileserver";
	/**
	 * 静态文件服务器在windows下的存储地址：hfs_system_windows=D:/files/
	 */
	@Deprecated
	public static final String HFS_SYSTEM_WINDOWS = "hfs_system_windows";
	/**
	 * 静态文件服务器在linux下的存储地址：hfs_system_linux=/www/hfs/
	 */
	@Deprecated
	public static final String HFS_SYSTEM_LINUX = "hfs_system_linux";

	public static final String UTF_8 = "UTF-8";
	
	public static String ROOTPATH = null;
	
	static {
//		try {
//			ROOTPATH = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/").replace("\\", "/");
//		} catch (Exception e) {
//			logError("实例化Common.ROOTPATH异常", Common.class, e);
//		}
	}
	
	/**
	 * 经常用到一个小功能，判断是否为空
	 * 
	 * @param obj将要判断的参数值
	 * @return 有效返回true反之false
	 */
	public static boolean valid(Object obj) {
		if (null != obj && !"".equals(obj) && !"null".equals(obj))
			return true;
		else
			return false;
	}

	/**
	 * 验证ID
	 * @param str
	 * @return
	 */
	public static boolean validId(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	// 以下就是从session里面取东西喽.

	/**
	 * ajax response method<br/>
	 * 如果出现异常，则返回错误代码505
	 * 
	 * @param response
	 * @param text
	 *            ajax返回的内容
	 * @author xieyong
	 */
	public static void ajaxResponse(HttpServletResponse response, Object text) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(text.toString());
		} catch (IOException e) {
			log.error("ajaxResponse 失败："+e.getMessage());
		}
	}
	
	/**
	 * <pre>
	 * 注意：ajax请求必须携带：jsonnp请求callback
	 * 
	 * eg:
	 * $.getJSON(path + "ajax/news/comment.go?newsId=" + newsId + "&content=" +content+ "&callback=?", function(data) {
	 *   alert(data['val']);
	 * });
	 * 
	 * </pre>
	 * 
	 * @param response
	 * @param msg
	 */
	public static void jsonnpResponse(HttpServletResponse response,
			String callback, String msg) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(callback + "(" + msg + ")");
		} catch (IOException e) {

		}
	}

	/**
	 * 异常操作，需要浏览器地址返回上一步
	 * 
	 * @param response
	 * @param alertMsg
	 *            弹出提示信息
	 */
	public static void ajaxResponseRollBack(HttpServletResponse response,
			String msg) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write( "<script type='text/javascript'>alert('" + msg + "');window.history.back();</script>");
		} catch (IOException e) {
			log.error("ajaxResponseRollBack ->"+e.getMessage());
		}
	}
	
	public static void ajaxResponseAlert(HttpServletResponse response,
			String msg) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write( "<script type='text/javascript'>alert('" + msg + "');</script>");
		} catch (IOException e) {
			log.error("ajaxResponseRollBack ->"+e.getMessage());
		}
	}

	public static Integer StringToInteger(HttpServletRequest request,
			String name) {
		String obj = request.getParameter(name);
		Integer result = -1;
		if (obj != null) {
			result = Integer.parseInt(obj);
		}
		return result;
	}

	public static Long StringToLong(HttpServletRequest request, String name) {
		Long result = Long.parseLong(StringToInteger(request, name) + "");
		return result;
	}

	/**
	 * 获取工程物理路径.
	 * 
	 * @return E:/tomcat/tourie/
	 */
	@Deprecated
	public static String getServerPath() {
		return ROOTPATH;
	}

	/**
	 * 获取文件服务器路径
	 * 
	 * @return windows:E:/aaa/bbb/<br/>
	 *         linux:/user/file/
	 *         
	 *  避免使用这些方法，统一使用projectutil类提供的方法
	 */
	@Deprecated
	public static String getHttpFileServerPath() {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			//windows
			return PropertiesUtil.get(HFS,HFS_SYSTEM_WINDOWS);
		} else {
			//linux
			return PropertiesUtil.get(HFS,HFS_SYSTEM_LINUX);
		}
	}

	/**
	 * 为过滤计划行程文字颜色写的方法
	 * @param content
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getPitch(String content, String begin, String end) {
		String sb = "";
		if (content.contains(begin)) {
			int b = content.indexOf(begin);
			int e = b;
			if (b > 0 && content.charAt(b - 1) == '-') {// 不是背景色
				e = content.indexOf(end, b);
				return getPitch(content.substring(e), begin, end);
			} else {
				e = content.indexOf(end, b);
			}
			if (b != e) {
				sb = content.substring(b, e);
			}
		}
		return sb;
	}
	
	/**
	 * 属性拷贝
	 * 
	 * @param firstObj
	 * @param senecndObj
	 */
	public static void copyPropertes(Object firstObj, Object senecndObj) {
		try {
			BeanUtils.copyProperties(firstObj, senecndObj);
		} catch (Exception e) {
			firstObj = null;
		}
	}

	/**
	 * 获取项目路径名称
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	public static String getContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
	}

	public static String getBrowser(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		// String agent = request.getHeader("User-Agent");
		// "Mozilla/5.0 (Windows NT 6.1; rv:5.0) Gecko/20100101 Firefox/5.0";
		String userbrowser = "";
		boolean flag = true;
		StringTokenizer st = null;
		// IE
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			st = new StringTokenizer(agent, ";");
			st.nextToken();
			userbrowser += st.nextToken();
		} else {
			// 谷歌Chrome
			if (null != agent && -1 != agent.indexOf("Chrome"))
				userbrowser = "Chrome ";
			// 苹果Safari
			else if (null != agent && -1 != agent.indexOf("Safari"))
				userbrowser = "Safari ";
			// 火狐Firefox
			else if (null != agent && -1 != agent.indexOf("Firefox"))
				userbrowser = "Firefox ";
			// Opera
			else if (null != agent && -1 != agent.indexOf("Opera"))
				userbrowser = "Opera ";
			else {
				userbrowser = "未知的浏览器!";
				flag = false;
			}
			// 判断是否是未知浏览器
			if (flag) {
				// 分隔解析字符串
				st = new StringTokenizer(agent, "/");
				st.nextToken();
				st.nextToken();
				st.nextToken();
				userbrowser += st.nextToken();
				userbrowser = userbrowser.replaceAll("Safari", "");
			}
		}
		return userbrowser;
	}

	/**
	 * 获取class类名(第一个字母转换成小写)
	 * 
	 * @param clzz
	 * @return
	 */
	public static String getClassName(Class<?> clzz) {
		String className = clzz.getSimpleName();
		return className.substring(0, 1).toLowerCase()
				+ className.substring(1, className.length());
	}
	
	/**
	 * 第一个字母转换成大写
	 * 
	 * @param clzz
	 * @return
	 */
	public static String upperCaseFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}
	
	/**
	 * 第一个字母转换成小写
	 * 
	 * @param clzz
	 * @return
	 */
	public static String lowerCaseFirst(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
	}
	
	/**
	 * 日志
	 * @param descInfo
	 * @param clazz
	 * @param methodName
	 * @param errorMessage
	 */
	public static void logInfo(String descInfo, Class<?> clazz, String methodName){
	    log.error(descInfo +  "->   class：" + clazz +  " , method-> " + methodName);
	}
	
	public static void logInfo(String descInfo, Class<?> clazz, Exception e) {
	    log.info(descInfo +  "->   class：" + clazz +  " , 异常信息-> " + e.getMessage());
	}
	public static void logError(String descInfo, Class<?> clazz, Exception e) {
		log.error(descInfo +  "->  class：" + clazz +  " , 异常信息-> " + e.getMessage());
	}
	public static void logDebug(String descInfo, Class<?> clazz, Exception e) {
		log.debug(descInfo +  "->   class：" + clazz +  " , 异常信息-> " + e.getMessage());
	}
}