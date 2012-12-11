package net.lising.lib;

import static net.lising.lib.LogUtil.log;

import java.io.UnsupportedEncodingException;


public class NetUtil {
	
	private static final String char_encode = "UTF-8";
	/**
	 * UTF-8解码
	 * @return
	 */
	public static String decode(String str) {
		String result = null;
		if(str==null||str.equals(""))return "";
		try {
			result = java.net.URLDecoder.decode(str, char_encode);
		} catch (UnsupportedEncodingException e) {
			log.error("UTF-8解码 ->decode() 失败："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * UTF-8编码
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		String result = null;
		if(str==null||str.equals(""))return "";
		try {
			result = java.net.URLEncoder.encode(str, char_encode);
		} catch (UnsupportedEncodingException e) {
			log.error("UTF-8编码 ->encode() 失败："+e.getMessage());
		}
		return result;
	}

}
