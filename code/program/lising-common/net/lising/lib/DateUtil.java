package net.lising.lib;

import java.text.ParseException;
import static net.lising.lib.LogUtil.log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期操作工具类
 * 
 * @author Jay.Lee
 * 
 * @createDate 2010-12-20 下午05:03:27
 */
public final class DateUtil {

	private static String getFormatDateTime(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return format.format(date);
	}

	/**
	 * 获取当前年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * 获取当前时分秒
	 * 
	 * @return HH:mm:ss
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		return getFormatDateTime(date, "HH:mm:ss");
	}

	public static String getOrderNum() {
		StringBuffer sb = new StringBuffer();
		sb.append(getCurrentDate());
		String[] temps = getCurrentTime().split(":");
		for (String temp : temps) {
			sb.append("-");
			sb.append(temp);
		}
		return sb.toString();
	}

	/**
	 * 比较两个日期
	 * 
	 * @param begin
	 * @param end
	 * @return begin是否在end之前
	 */
	public static boolean compare(Date begin, Date end) {
		return begin.before(end);
	}

	/**
	 * 比较两个日期,转换成yyyy-MM-dd HH:mm:ss格式进行比较
	 * 
	 * @param begin
	 * @param end
	 * @return begin是否在end之前
	 */
	public static boolean compare(String begin, String end) {
		Date d1 = getDateByString(begin, "yyyy-MM-dd HH:mm:ss");
		Date d2 = getDateByString(end, "yyyy-MM-dd HH:mm:ss");
		return d1.before(d2);
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		Date date = new Date();
		return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据传入的时间格式获取相应的时间字符串
	 * 
	 * @param date
	 *            时间
	 * @return 默认为yyyy-MM-dd HH:mm:ss格式日期
	 */
	public static String getDateStringByPattern(Date date) {
		return getDateStringByPattern(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据传入的时间格式获取相应的时间字符串
	 * 
	 * @param date
	 *            时间
	 * @param pattern
	 *            时间格式
	 * @return
	 */
	public static String getDateStringByPattern(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String str = sf.format(date);
		return str;
	}

	/**
	 * 日期格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getDateStringByPattern(String pattern) {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sf.format(date);
	}

	/**
	 * 将字符串转换成日期
	 * 
	 * @param dateStr
	 *            要转换的日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date getDateByString(String dateStr, String pattern) {
		if (null != dateStr && !"".equals(dateStr)) {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			try {
				return sf.parse(dateStr);
			} catch (ParseException e) {
				log.error("将字符串转换成日期 ->getDateByString() 失败："+e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 根据日期得到下一个月日期
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static String getNextMonth(String date, int num) {
		Date d = getDateByString(date, "yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);
		int month = ca.get(Calendar.MONTH);
		month = month + num;
		ca.set(Calendar.MONTH, month);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		return getDateStringByPattern(ca.getTime(), "yyyy-MM-dd");
	}

	/**
	 * @param date
	 *            2010-09-01
	 * @param num
	 *            1 or 0 or -1
	 * @param pattern
	 *            "yyyy-MM-dd"
	 * @return
	 */
	public static String getNextMonth(String date, int num, String pattern) {
		Date d = getDateByString(date, "yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);
		int month = ca.get(Calendar.MONTH);
		month = month + num;
		ca.set(Calendar.MONTH, month);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		return getDateStringByPattern(ca.getTime(), pattern);
	}

	/**
	 * 得到某一日期后的任意日期
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static String getNextDay(String date, int num) {
		Date d = getDateByString(date, "yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);
		ca.add(Calendar.DAY_OF_MONTH, num);
		return getDateStringByPattern(ca.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 给定一个日期，得到这一月任意一天日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String getDateByDay(String date, int day) {
		Date d = getDateByString(date, "yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);
		ca.set(Calendar.DAY_OF_MONTH, day);
		return getDateStringByPattern(ca.getTime(), "yyyy-MM-dd");
	}

	/**
	 * <pre>
	 * 一日游：当天发团、当天返回    因此需要减1
	 * 根据日期和天数 算出该日期往后的日期
	 * </pre>
	 * 
	 * @param date
	 *            "2010-07-21"
	 * @param FutureDay
	 *            正数：往后 负数：往前
	 * @return
	 */
	public static String getFutureDays(String date, int FutureDay) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			now.setTime(sdf.parse(date));
		} catch (ParseException e) {
			log.error("根据日期和天数 算出该日期往后的日期 ->getFutureDays() 失败："+e.getMessage());
		}
		now.set(Calendar.DATE, now.get(Calendar.DATE) + FutureDay - 1);
		return sdf.format(now.getTime());
	}

	/**
	 * sub是文件名前面识别文字 filename 文件的名称 上传的文件重命名
	 */
	public static String generateFileName(String sub, String fileName) {
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return sub + String.valueOf(System.currentTimeMillis()) + extension;
	}

	public static String longToStr(long mi, String pattern) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(mi);
		return getDateStringByPattern(c.getTime(), pattern);
	}

	/**
	 * 
	 * @param date
	 *            such as '2010-09-09'
	 * @return
	 */
	public static Long StringDateToLong(String date) {
		Long result = null;
		result = java.sql.Date.valueOf(date).getTime();
		return result;
	}
	
	/**
	 * <pre>
	 * 得到时间间隔
	 * 大于1天  显示几天前
	 * 大于1小时 显示几小时前
	 * 大于1分钟，显示几分钟前
	 * </pre>
	 * @param time
	 * @return
	 */
	public static String getTimeInterval(Date time){
		long interval = new Date().getTime() - time.getTime();
		long day=interval/(24*60*60*1000);
	    if(day > 0){
	    	return day + "天前";
	    }
	    long hour=(interval/(60*60*1000)-day*24);
	    if(hour >0 ){
	    	return hour + "小时前";
	    }
	    long min=((interval/(60*1000))-day*24*60-hour*60);
	    if(min > 0){
	    	return min + "分钟前";
	    }
		return "1分钟前";
	}
	
	/**
	 * 获取当前的时间
	 * @return
	 */
	public static long getDayTimeString() {
		return System.currentTimeMillis();
	}

	public static void main(String[] s) {
		@SuppressWarnings("unused")
		String d = "2010-01-21";
//		Date d2 = DateUtil.getDateByString(d, "yyyy-MM-dd");
//		System.out.println(DateUtil.getDateStringByPattern(d2, "yyyy年MM月"));
//		System.out.println(getFutureDays("2011-01-01", 20));
//		System.out.println(getNextDay("2011-01-01", 20));
		System.out.println(generateFileName("","aaa.jpg"));
	}

}
