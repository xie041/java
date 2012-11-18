package net.lising.lib;

/**
 * @author cheng.rong
 * <pre>
 * 类名称：StringUtil  
 * 类描述：  显示标题
 * 创建人：cheng.rong   Email:chengrong126@126.com  QQ:283720025 
 * 创建时间：2011-7-14 上午10:33:03  
 * 修改备注：  
 * @version
 * </pre>
 */ 
public class StringUtil {

	/**
	 * 返回指定长度的字符串(如果超过长度加上...)
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getString(String str, int length) {
		if(null != str ){
			if (str.length() > length) {
				str = str.substring(0, length) + "...";
			}
		}
		return str;
	}
}
