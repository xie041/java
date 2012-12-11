package net.lising.lib.cal;

import java.util.Calendar;
import java.util.Date;

import net.lising.lib.DateUtil;

public class CalendarUtil {
	
	
	/**
	 * 日历月份标题
	 * <pre>
	 *	<tr>
	 *	<td colspan="7">
	 *	<table
	 *		style="font-weight: bold; width: 100%; border-collapse: collapse;" border="1" cellspacing="0">
	 *			<tr>
	 *				<td style="width: 15%; cursor: auto;">
	 *				   <span style="color: rgb(204, 204, 204);" title="转到上一个月">◄6月</span>
	 *				</td>
	 *				<td style="width: 70%; cursor: auto;" align="center">2010年7月</td>
	 *				<td style="width: 15%; cursor: auto;" align="right"></td>
	 *			</tr>
	 *	</table>
	 *	</td>
	 * </tr>
	 * </pre>
	 * @param date ◄上月   ►下月
	 * @param month
	 * @return
	 */
	public static String getCalendarTitle(String date,CalendarType type){
		String nextMonth = DateUtil.getNextMonth(date, 2);
		String preMonth = DateUtil.getNextMonth(date, -1);
		Calendar ca = Calendar.getInstance();
		ca.setTime(DateUtil.getDateByString(date, "yyyy-MM-dd"));
		int month = ca.get(Calendar.MONTH);//当前月份前一月
		int currentMonth = month + 1;
		int year = ca.get(Calendar.YEAR);//年
		date = year + "年" + currentMonth + "月";
		StringBuffer buf = new StringBuffer("<tr class=\"date_tab_tit ac\">");
		buf.append("<td colspan=\"7\">");
		buf.append("<h3 class=\"date-div-title pos-rel\">");
		if(type.equals(CalendarType.PREVIOUS)){//向左日历
			if(month<1)
				month = 12;
			buf.append("<span tip=\"转到上一个月\" class=\"pos-abs date-up\"><a href=\"javascript:getCal('"+preMonth+"');\">◄"+month+"月</a></span>&nbsp;&nbsp;&nbsp;");
			buf.append(date);
		}else if(type.equals(CalendarType.MIDDLE)){
//			buf.append("<td style=\"width: 18%;\"></td>");
//			buf.append("<td>"+date+"</td>");
//			buf.append("<td style=\"width: 18%;\" align=\"right\"></td>");
		}else if(type.equals(CalendarType.NEXT)){
			month += 2;
			if(month > 12) month = 1;
			buf.append(date);
			buf.append("&nbsp;&nbsp;&nbsp;<span tip=\"转到下一个月\" class=\"pos-abs date-down\"><a href=\"javascript:getCal('"+nextMonth+"');\">"+month+"月►</a></span>");
		}
		buf.append("</h3>");
		buf.append("</td>");
		buf.append("</tr>");
		return buf.toString();
	}
	
	/**
	 * 获得星期标题
	 * <tr>
     *		<th abbr="星期日" scope="col" align="center">日</th>
     *		<th abbr="星期一" scope="col" align="center">一</th>
     *		<th abbr="星期二" scope="col" align="center">二</th>
	 *		<th abbr="星期三" scope="col" align="center">三</th>
	 *		<th abbr="星期四" scope="col" align="center">四</th>
	 *		<th abbr="星期五" scope="col" align="center">五</th>
	 *		<th abbr="星期六" scope="col" align="center">六</th>
	 * </tr>
	 * @return
	 */
	public static String getWeekTitle(){
		StringBuffer buf = new StringBuffer("<tr class=\"date-table-week ac\">");
		buf.append("<td title=\"星期日\"><strong class=\"bin-red\">日</strong></td>");
		buf.append("<td title=\"星期一\"><strong>一</strong></td>");
		buf.append("<td title=\"星期二\"><strong>二</strong></td>");
		buf.append("<td title=\"星期三\"><strong>三</strong></td>");
		buf.append("<td title=\"星期四\"><strong>四</strong></td>");
		buf.append("<td title=\"星期五\"><strong>五</strong></td>");
		buf.append("<td title=\"星期六\"><strong class=\"bin-red\">六</strong></td>");
		buf.append("</tr>");
		return buf.toString();
	}
	
	/**
	 * 获得日期
	 * @param date
	 * @return
	 */
	public static String getDayPanel(String date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDateByString(date, "yyyy-MM-dd"));
	
		int beginDay=cal.getActualMinimum(Calendar.DAY_OF_MONTH);//月第一天
		int endDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//月最后一天
		cal.set(Calendar.DAY_OF_MONTH, beginDay);
		int beginWeek = cal.get(Calendar.DAY_OF_WEEK)-1;//月第一天星期几
		cal.set(Calendar.DAY_OF_MONTH, endDay);
		int endWeek = cal.get(Calendar.DAY_OF_WEEK)-1;//月最后一天星期几
		
		StringBuffer buf = new StringBuffer("<tr class=\"date_tdmargin\">");
		
		//第一次循环，目的是让星期与title星期重合,目的是添加前置空格
		for (int i = 0; i < beginWeek; i++) {
			buf.append("<td>&nbsp;</td>");
		}
		
		//第二次循环日期
		for (int i = beginDay ; i <= endDay; i++) {
			String loopDay = DateUtil.getDateByDay(date, i);
			if(checkDateIsAvailble(date,i))//日期可以发团
			{
				if(checkDateIsToday(date,i))//今天
					buf.append("<td class=\"today_color\" title=\""+GregorianCalendarUtil.getLunerDate(loopDay)+"\" onclick=\"alert('今天不能发团!');\">"+i+"</td>");
				else{
					if(checkDayIsWeekend(loopDay))//能发团，是周末
						buf.append("<td class=\"weekend_color\" onclick=\"showDateItems('"+loopDay+"')\" title=\""+GregorianCalendarUtil.getLunerDate(loopDay)+"\" onclick=\"alert('今天不能发团!');\">"+i+"</td>");
					else//能发团，不是周末
						buf.append("<td class=\"date_seldate\" onclick=\"showDateItems('"+loopDay+"')\" title=\""+GregorianCalendarUtil.getLunerDate(loopDay)+"\" onclick=\"alert('今天不能发团!');\">"+i+"</td>");
				}
			}else{//日期不能发团
				if(checkDayIsWeekend(loopDay))//不能发团，是周末
					buf.append("<td class=\"weekend_color\" onclick=\"alert('今天以前的日期不能发团')\" title=\""+GregorianCalendarUtil.getLunerDate(loopDay)+"\">"+i+"</td>");
				else//不能发团，不是周末
					buf.append("<td class=\"overbg\" onclick=\"alert('今天以前的日期不能发团')\" title=\""+GregorianCalendarUtil.getLunerDate(loopDay)+"\">"+i+"</td>");
				
			}
				
			if((i+beginWeek)%7==0&&i!=0) //System.out.println("换行");
				buf.append("</tr><tr class=\"date_tdmargin\">");
		}
		
		//第三次循环，在最后日期添加空格，保证表格饱满
		for (int i = 0; i < 6-endWeek; i++) {
			buf.append("<td>&nbsp;</td>");
		}
		
		buf.append("</tr>");
		return buf.toString();
	}
	
	/**
	 * 获得公司信息中心日程安排
	 * @param date
	 * @return
	 */
	public static String getDayPanelForInfoCenter(String date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDateByString(date, "yyyy-MM-dd"));
		
		int beginDay=cal.getActualMinimum(Calendar.DAY_OF_MONTH);//月第一天
		int endDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//月最后一天
		cal.set(Calendar.DAY_OF_MONTH, beginDay);
		int beginWeek = cal.get(Calendar.DAY_OF_WEEK)-1;//月第一天星期几
		cal.set(Calendar.DAY_OF_MONTH, endDay);
		int endWeek = cal.get(Calendar.DAY_OF_WEEK)-1;//月最后一天星期几
		
		StringBuffer buf = new StringBuffer("<tr>");
		
		//第一次循环，目的是让星期与title星期重合,目的是添加前置空格
		for (int i = 0; i < beginWeek; i++) {
			buf.append("<td>&nbsp;</td>");
		}
		//第二次循环日期
		for (int i = beginDay ; i <= endDay; i++) {
			String loopDay = DateUtil.getDateByDay(date, i);
			String date_title = GregorianCalendarUtil.getLunerDate(loopDay);//title
			String g_cal = GregorianCalendarUtil.getLunerDateForInfoCenter(loopDay);//廿三
			if(checkDateIsToday(date,i)){//今天
				//<td  title="2010-09-01 星期三&nbsp;&nbsp;&#10;农历庚寅(虎)年七月廿三">1<br/>廿三</td>
				buf.append("<td class=\"today\" tip=\""+date_title+"\" id=\"cal_"+loopDay.replace("-", "")+"\"  >"+i+"<br/>"+g_cal+"</td>");
			}else{
				if(checkDayIsWeekend(loopDay))//周末
					buf.append("<td class=\"liu\" tip=\""+date_title+"\"  id=\"cal_"+loopDay.replace("-", "")+"\" >"+i+"<br/>"+g_cal+"</td>");
				else//不是周末
					buf.append("<td tip=\""+date_title+"\" id=\"cal_"+loopDay.replace("-", "")+"\" >"+i+"<br/>"+g_cal+"</td>");
			}
			if((i+beginWeek)%7==0&&i!=0) //System.out.println("换行");
				buf.append("</tr><tr>");
		}
		
		//第三次循环，在最后日期添加空格，保证表格饱满
		for (int i = 0; i < 6-endWeek; i++) {
			buf.append("<td>&nbsp;</td>");
		}
		buf.append("</tr>");
		return buf.toString();
	}
	
	/**
	 * 检查日期的月份是不是有效,也就是是不是今天以前的日期,如果是，就不能发团
	 * @return
	 */
	private static boolean checkDateIsAvailble(String date,int day){
		String tempDay = null;
		if(day<10)
			tempDay = "0" + day;
		else
			tempDay = day + "";
		String c_day = date.substring(0,date.lastIndexOf("-"))+ "-" + tempDay;
		Date d1 = DateUtil.getDateByString(c_day, "yyyy-MM-dd");
		Date d2 = DateUtil.getDateByString(DateUtil.getCurrentDate(), "yyyy-MM-dd");//今天
		return d1.getTime()>=d2.getTime();
	}
	
	/**
	 * 检查是否是今天
	 * @param date
	 * @param day
	 * @return
	 */
	private static boolean checkDateIsToday(String date,int day){
		String tempDay = null;
		if(day<10)
			tempDay = "0" + day;
		else
			tempDay = day + "";
		String currentDay = DateUtil.getCurrentDate();//当前日期
		String c_day = date.substring(0,date.lastIndexOf("-"))+ "-" + tempDay;
		return c_day.equals(currentDay);
	}
	
	/**
	 * 检查日期是否是周六周日
	 * @param day
	 * @return
	 */
	private static boolean checkDayIsWeekend(String day){
		String week = DateUtil.getDateStringByPattern(DateUtil.getDateByString(day, "yyyy-MM-dd"), "E");
		if(week.equals("星期六")||week.equals("星期日"))
			return true;
		else 
			return false;
	}
}
