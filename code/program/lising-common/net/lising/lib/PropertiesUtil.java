package net.lising.lib;

import java.util.ResourceBundle;

/**
 * Properties文件工具类
 * 
 * @author Jay.Lee
 * @createDate 2011-1-15 上午11:52:40
 */
public class PropertiesUtil {

	/**
	 * 获取Properties配置文件值
	 * 
	 * @param fileName
	 *            Properties文件名称
	 * @param key
	 *            属性key
	 * @return
	 */
	public static String get(String fileName, String key) {
		return ResourceBundle.getBundle(fileName).getString(key);
	}

}
