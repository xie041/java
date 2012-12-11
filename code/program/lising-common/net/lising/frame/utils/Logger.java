/**
 * 
 */
package net.lising.frame.utils;

import java.text.DateFormat;
import java.util.Date;

import net.lising.urls.ProjectUtil;

/**
 * @author : 吕佳诚
 * @Description : 日志打印
 * @CreateDate : 2012-4-16 下午04:49:34
 * @lastModified : 2012-4-16 下午04:49:34
 * @version : 1.0
 */
public class Logger {
	
	/** 是否打印 */
	private boolean isPrint;

	/**
	 * 构造器
	 */
	public Logger() {
		this.isPrint = ProjectUtil.getFrameIsPrint();
	}

	/**
	 * 打印调试信息
	 * 
	 * @param message
	 */
	public void info(String message) {
		if (isPrint) {
			System.out.println( message );
		}
	}

	/**
	 * 打印时间及调试信息
	 * 
	 * @param message
	 */
	public void logger(String message) {
		if (isPrint) {
			System.out.println( getNow() + "消息:"+message );
		}
	}

	/**
	 * 打印调试信息及异常信息
	 * 
	 * @param message
	 */
	public void debug(String message, Exception e) {
		if (isPrint) {
			System.out.println( message + "/n" + e);
		}
	}

	/**
	 * 获取时间。
	 * 
	 * @return 时间字串
	 */
	private String getNow() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); // 显示日期，时间（精确到分）
		return df.format(now);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
