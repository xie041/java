package net.lising.lib;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 项目名称：lising-tourie  
 * 类名称：CookieUtil  
 * 类描述：  
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-7-21 下午05:22:04  
 * 修改人：xie041  
 * 修改时间：2011-7-21 下午05:22:04  
 * 修改备注：  
 * @version  
 * </pre> 
 */ 
public class CookieUtil {
	
	/**
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request,String key){
		Cookie cookies[] = request.getCookies();
		String value = null;
		for(Cookie cookie : cookies){
			if (cookie.getName().equals(key)) {
				try {
					value = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return null;
				}
			}
		}
		return value;
	}

	/**
	 * 伴随一次回话的cookie
	 * @param request
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String key,String value){
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		Cookie ck = new Cookie(key, value);
		ck.setMaxAge(-1);
		ck.setPath(request.getContextPath()+"/");
		response.addCookie(ck);
	}
	
	/**
	 * 设置失效时间的cookie
	 * @param request
	 * @param response
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response,String key,String value,int expiry){
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		Cookie ck = new Cookie(key, value);
		ck.setMaxAge(expiry);
		ck.setPath(request.getContextPath()+"/");
		response.addCookie(ck);
	}
	
	/**
	 * 删除cookie
	 * @param response
	 * @param key
	 */
	public static void removeCookie(HttpServletResponse response,String key){
		Cookie cookie = new Cookie(key, null); 
		cookie.setMaxAge(-1);
		response.addCookie(cookie); 
	}
}
