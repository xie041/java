package net.lising.lib;

import org.apache.log4j.Logger;

/**
 * 
 * 日志工具
 * 使用方法：
 * import static net.lising.lib.LogUtil.log;
 * 
 * log.debug("debug");
 * log.info("debug");
 * log.error("debug");
 * 
 * LogUtil log = new LogUtil(Demo.class);
 * 
 * log.debug("");
 * 
 * @author xie041
 *
 */
public class LogUtil{
	
	private LogUtil() { }
	/**
	 * 这个log不去记录具体的类路径
	 */
	public static Logger log = Logger.getRootLogger();
}