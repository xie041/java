/**
 * StringUtils.java 2010-12-31 下午05:06:29 
 * Copyright (c) 2005 ChinaSoft International, Ltd. All rights reserved.
 */
package net.lising.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jiachenglu
 * 
 */
public class StringUtils {

	/**
	 * 如果对象为空则返回"0"否则返回字符串型对象的值
	 * 
	 * @param obj 对象 通常为字符串对象
	 * @return
	 */
	public static String convertNULL2Zero(Object obj) {
		if (obj == null) {
			return "0";
		} else {
//			return cn.gov.cma.cimiss.gds.dataservices.utils.StringUtils
//			.trimFullSpace(((String) obj));
			return toSemiangle((String) obj).trim();
		}
	}

	/**
	 * 如果对象为空则返回""否则返回字符串型对象的值
	 * 
	 * @param obj 对象 通常为字符串对象
	 * @return
	 */
	public static String convertNULL2Blank(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return toSemiangle((String) obj).trim();
		}
	}
	

	/**
	 * 返回当前日期
	 * 
	 * @param format 返回格式 例如:"yyyy-MM-dd hh:mm:ss" <br>
	 * yyyy-MM-dd kk:mm:ss为24小时制;
	 * @return 当前日期
	 */
	public static String today(String format) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		c.setTimeInMillis(System.currentTimeMillis());
		return df.format(c.getTime());
	}
	
	
	/**
	 * <h3>装换全角字符到半角字符，如"，。、"等</h3>
	 *全角空格为12288，半角空格为32<br/>   
     *其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248 
	 * @param string 字符串
	 * @return 转换后的字符串
	 */
	public static String toSemiangle(String string) {
		char[] c = string.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] == 12289) {
				c[i] = (char) 47;
				continue;
			}
			if (c[i] == 12290) {
				c[i] = (char) 46;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/**
	 * （特殊应用）<br>
	 * 获取"@"和"@"之间的文字并返回
	 * @param sourceString
	 * @return
	 */
	public static String getBetweenString(String sourceString) {
		if(sourceString.indexOf("@") !=-1 && 
				sourceString.lastIndexOf("@")!=-1 &&
				sourceString.indexOf("@") != sourceString.lastIndexOf("@") 
				) {
			StringBuffer sb = new StringBuffer(100);
			sb.append("(");
			sb.append(sourceString.substring(sourceString.indexOf("@") + 1, 
					sourceString.lastIndexOf("@")));
			sb.append(")");
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 替换指定字串中指定的值<br/>
	 * 例如：将字串'aac'中所有的'a'替换成's',结果如下：'ssc'
	 * @param sourceString 将替换字串
	 * @param oldString 被替换值
	 * @param newString 替换值
	 * @return
	 */
	public static String replace(String sourceString,String oldString,
			String newString)
	{
		newString = convertNULL2Blank(newString).length()>0?convertNULL2Blank(newString): "";
		if (sourceString.indexOf(oldString) != -1) {
			sourceString = sourceString.replace(oldString,newString);
		}
		return sourceString;
	}
	
	/**
	 * 将数值转换成相应精度的字符串
	 * @param number 数值，如：230.0
	 * @param digital 精度
	 * @return
	 */
	public static String getDigital (Object number,int digital)
	{
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance();  
		  formater.setMaximumFractionDigits(digital);  
		  //formater.setMinimumFractionDigits(2);  
		  //System.out.println(formater.format(3.1415927));
//		  System.out.println(formater.format(number));
		  return formater.format(number);
	}
	
	/**
     * 获取指定目录的绝对路径
     * @param requset HttpServletRequest
     * @param c 指定的目录
     * @return String 指定目录的绝对路径
     */
    public static final String getRealPath(HttpServletRequest 
    		request, String c) {
    	return request.getSession().getServletContext().getRealPath(c);
    }
    
    /**
     * 截取相应长度的字串并返回
     * @param sourceString
     * @param length
     * @return
     */
	public static String trimString(String sourceString,int length) {
    	if( convertNULL2Blank(sourceString).length() == 0 ) {return "";}
    	
    	String tem = sourceString.trim();
    	if( tem.length() < length) {return tem;}
    	
    	return tem.substring(0, length-3)+"...";
    }
	
	/**
	 * 将字符串数组转换成字符串<br>
	 * 如：["a","b","c"]--> abc; 
	 * @param stringArray 字符串数组
	 * @return
	 */
	public static String convertStringArrayToString(String[] stringArray) {
		if(stringArray == null || stringArray.length == 0) return "";
		return Arrays.toString(stringArray).replaceAll("[\\[\\], ]", "") ;
	}
	
	/**
	 * 判断是否为数字
	 * @param str字符串
	 * @return true|false
	 */
	public static boolean isNumeric(String str) {
		str = convertNULL2Blank(str);
		if( str.length() == 0) return false;
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str 字符串
	 * @return true|false
	 */
	public static boolean isNull(String str) {
		if( str == null ) {
			return true;
		}else {
			return convertNULL2Blank(str).length() == 0 ;
		}
	}
	
	/**
	 * 判断[子串]是否存在[父串]中
	 * @param parentString 父串
	 * @param childString 子串
	 * @return 是否穿在 true|false
	 */
	public static boolean contain(String parentString, String childString) {
		if(parentString == null || childString == null) return false ;
		return parentString.indexOf( childString) != -1 ;
	}
	
	/**
	 * 将字符串转换为基本long型
	 * @param str 字串
	 * @return 基本long型
	 */
	public static long toLong(String str) {
		if(isNull(str) || !isNumeric(str)) return 0L;
		return Long.valueOf(str).longValue();
	}
	
	/**
	 * 将字符串转换为基本int型
	 * @param str 字串
	 * @return 基本int型
	 */
	public static int toInt(String str) {
		if(isNull(str) || !isNumeric(str)) return 0;
		return Integer.valueOf(str).intValue();
	}
	
	/**
	 * （特殊应用）<br>
	 * 从字串中去除指定字串
	 * @param string 字串
	 * @return 去除类似(;jsessionid=2C809F656D20BBADF4FB525D741D4507)后的字串
	 */
	public static String substring(String string) {
		if(string == null ||string.length() == 0) return "";
		String defaultString = ";jsessionid=";
		String temString = string.substring(0,string.indexOf(defaultString));
		//;jsessionid=2C809F656D20BBADF4FB525D741D4507 长度为44
		temString = temString + string.substring(temString.length()+44,string.length());
		return temString;
	}
	
	/**
	 * 获取请求的地址
	 * @param request
	 * @return 类似：http://192.168.11.17:8082/lising-manage/sosid/li/list.do?sid=1&sid2=2
	 */
	public static String getRequestURL(HttpServletRequest request) {
		if(request == null ) return "";
		String  casReturnProjectURL= request.getScheme()+"://";  
				casReturnProjectURL+=request.getHeader("host");  
				casReturnProjectURL+=request.getRequestURI();  
		if(request.getQueryString()!=null) { 
			casReturnProjectURL+="?"+request.getQueryString();
		}
		if( casReturnProjectURL.indexOf(";jsessionid=") !=-1 ) {
			casReturnProjectURL = substring(casReturnProjectURL);
		}
		return casReturnProjectURL;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String[] abc = {"a","b","c"};
//		System.err.println( convertStringArrayToString(abc));
		
		System.out.println( isNumeric (" "));
	}

	

}
