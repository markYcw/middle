package keda.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 提供时间类型操作的相关方法 
 * @author TaoPeng
 */
public class TimeUtil {
	public static final String TIME_SQL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static Timestamp getCurTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	// public static Date getCurDate() {
	// return new Date(System.currentTimeMillis());
	// }

	public static Calendar getYesterday() {
		Calendar c = getChCalendar();
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c;
	}
	
	public static Calendar getNextday() {
		Calendar c = getChCalendar();
		c.add(Calendar.DAY_OF_MONTH, +1);
		return c;
	}

	public static String getDate(Timestamp ts) {
		if (ts == null)
			return "";

		SimpleDateFormat f = getChSimpleDateFormat("yyyy年MM月dd日");

		return f.format(ts);
	}

	public static String getDate(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat f = getChSimpleDateFormat("yyyy年MM月dd日");

		return f.format(date);
	}

	public static String getTimestamp(java.util.Date ts, String format) {
		if (ts == null || format == null || "".equals(format))
			return "";
		SimpleDateFormat f = getChSimpleDateFormat(format);
		return f.format(ts);
	}

	public static String getTimestamp(Timestamp ts) {
		if (ts == null)
			return "";

		SimpleDateFormat f = getChSimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		return f.format(ts);
	}
	/**
	 * 获取mysql格式的时间字符串
	 * @param ts	时间
	 * @return
	 */
	public static String getSQLTimestamp(Timestamp ts) {
		return getTimestamp(ts, TIME_SQL_PATTERN);
	}
	/**
	 * 按格试要求格式化时间字符串
	 * @param ts 时间
	 * @param format 格式
	 * @return
	 */
//	public static String getTimestamp(Timestamp ts, String format) {
//		if (ts == null || format == null || "".equals(format))
//			return "";
//		SimpleDateFormat f = getChSimpleDateFormat(format);
//		return f.format(ts);
//	}
	public static String getSQLDate(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat f = getChSimpleDateFormat("yyyy-MM-dd");

		return f.format(date);
	}

	// public static String getCurTimeString() {
	// java.util.Date date = new java.util.Date();
	// SimpleDateFormat f = getChSimpleDateFormat("yyyyMMddHHmmss");
	//
	// return f.format(date);
	// }

	public static Timestamp createTimestamp(int year, int month, int day,
			int hour, int minute, int second) {
		Calendar c = getChCalendar();
		c.set(year, month - 1, day, hour, minute, second);

		return new Timestamp(c.getTimeInMillis());
	}

	public static Calendar createDate(int year, int month, int day) {
		Calendar c = getChCalendar();
		c.set(year, month - 1, day);

		return c;
	}

	public static String getJavaScriptTimeString(Timestamp ts) {
		if (ts == null)
			return "";
		SimpleDateFormat f = getChSimpleDateFormat("new Date(yyyy,MM,dd,HH,mm,ss)");
		return f.format(ts);
	}

	/**
	 * 计算时间差以yyyy年MM月dd日 HH时mm分ss秒格式返回字符串
	 * 
	 * @param startTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 返回时间差, null : 起始时间大小结束时间
	 */
	public static String getDiffTime(long startTime, long endTime) {
		if (endTime < startTime) {
			return null;
		}
		long diffTime = endTime - startTime;
		long diffHours = diffTime / 3600000;
		long diffMinutes = diffTime / 60000 - diffHours * 60;
		long diffSecond = diffTime / 1000 - diffMinutes * 60 - diffHours * 3600;

		return diffHours + "时" + diffMinutes + "分" + diffSecond + "秒";
	}

	/**
	 * 获取两个时间相差的天数。
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 如果endTime比startTime更后，则返回正数，否则返回负数。如果时间差不足1天，则按1天计算。
	 */
	public static int getDiffDay(long startTime, long endTime){

		long diffTime = endTime - startTime;
		long dt = 24 * 3600 * 1000;
		double d = (double)diffTime / dt;
		d = Math.ceil(d);
		
		return (int)d;
	}
	/**
	 * 获取两个时间相差的天数。
	 * @param startTime
	 * @param endTime
	 * @see #getDiffDay(long, long)
	 * @return
	 */
	public static int getDiffDay(Calendar startTime, Calendar endTime){
		return getDiffDay(startTime.getTimeInMillis(), endTime.getTimeInMillis());
		
	}
	
	/**
	 * 将字符串转换为时间类
	 * 
	 * @param strTimestamp
	 *            时间字符串
	 * @return 时间类
	 */
	public static Timestamp valueOfTimestamp(String strTimestamp) {
		if (strTimestamp == null || "".equals(strTimestamp)){
			return null;
		}
		return Timestamp.valueOf(strTimestamp);
	}
	
	public static Timestamp valueOfTimestamp(String strTimestamp, String pattern)   {
		try {
			if (strTimestamp == null || "".equals(strTimestamp))
				return null;
			SimpleDateFormat sdf = getChSimpleDateFormat(pattern);
			java.util.Date date = sdf.parse(strTimestamp);
			
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取中国时区
	 * 
	 * @return
	 */
	public static Calendar getChCalendar() {
		Calendar ca = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Shanghai"));
		return ca;
	}

	/**
	 * 获取中国时区的时间格式化类
	 * 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getChSimpleDateFormat(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf;
	}
	
	/**
	 * 获取一月的开始时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getMonthStart(Calendar calendar){
		calendar = copy(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 获取一月的结束时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getMonthEnd(Calendar calendar){
		calendar = getMonthStart(calendar);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar;
	}
	
	/**
	 * 获取一天的开始时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getDayStart(Calendar calendar){
		calendar = copy(calendar);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	/**
	 * 获取一天的开始时刻
	 * @param date
	 * @return
	 */
	public static java.util.Date getDayStart(java.util.Date date){
		Calendar c = getCalendar(date.getTime());
		c = getDayStart(c);
		return c.getTime();
	}
	/**
	 * 获取一天的结束时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getDayEnd(Calendar calendar){
		calendar = getDayStart(calendar);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar;
	}
	/**
	 * 获取一天的结束时刻
	 * @param date
	 * @return
	 */
	public static java.util.Date getDayEnd(java.util.Date date){
		Calendar c = getCalendar(date.getTime());
		c = getDayEnd(c);
		return c.getTime();
	}

	/**
	 * 获取今天的开始时刻
	 * @return
	 */
	public static Calendar getTodayStart(){
		return getDayStart(Calendar.getInstance());
	}
	
	/**
	 * 获取今天的结束时刻
	 * @return
	 */
	public static Calendar getTodayEnd(){
		return getDayEnd(Calendar.getInstance());
	}
	
	/**
	 * 获取一个小时的开始时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getHourStart(Calendar calendar){
		calendar = copy(calendar);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	/**
	 * 获取一个小时的结束时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getHourEnd(Calendar calendar){
		calendar = getHourStart(calendar);
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar;
	}
	
	/**
	 * 获取一分钟的开始时刻
	 * @param calendar
	 * @return 
	 */
	public static Calendar getMinuteStart(Calendar calendar){
		calendar = copy(calendar);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	/**
	 * 获取一分钟的结束时刻
	 * @param calendar
	 * @return
	 */
	public static Calendar getMinuteEnd(Calendar calendar){
		calendar = getMinuteStart(calendar);
		calendar.add(Calendar.MINUTE, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar;
	}
	
	/**
	 * 返回srcCalendar的副本
	 * @param srcCalendar
	 * @return
	 */
	public static Calendar copy(Calendar srcCalendar){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(srcCalendar.getTimeInMillis());
		return c;
	}
	

	public static String formatTime(Calendar calendar,String pattern){
		return formatTime(calendar.getTime(), pattern);
	}
	public static String formatTime(Timestamp timestamp,String pattern){
		Date d  = new Date(timestamp.getTime());
		return formatTime(d, pattern);
	}
	public static String formatTime(java.util.Date date,String pattern){
		SimpleDateFormat sdf = getChSimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @param calendar
	 * @param pattern
	 * @return 返回解析后的时间，如果解析出现异常，则返回null
	 * @throws ParseException 
	 */
	public static Calendar parse(String calendar,String pattern) throws ParseException{

		if(calendar == null){
			return null;
		}
		SimpleDateFormat sdf = getChSimpleDateFormat(pattern);
		java.util.Date d;
		d = sdf.parse(calendar);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		return c;
	}
	
	private static ArrayList<String> datePtterns = new ArrayList<String>();
	static{
		datePtterns.add("yyyy-MM-dd HH:mm:ss.SSS");
		datePtterns.add("yyyy-MM-dd HH:mm:ss");
		datePtterns.add("yyyy-MM-dd HH:mm");
		datePtterns.add("yyyy-MM-dd HH");
		datePtterns.add("yyyy-MM-dd");
		datePtterns.add("yyyy-MM");

		datePtterns.add("MM-dd HH:mm:ss.SSS");
		datePtterns.add("MM-dd HH:mm:ss");
		datePtterns.add("MM-dd HH:mm");
		datePtterns.add("MM-dd HH:mm");
		datePtterns.add("MM-dd HH");
		datePtterns.add("MM-dd");

		datePtterns.add("HH:mm:ss.SSS");
		datePtterns.add("HH:mm:ss");
		datePtterns.add("HH:mm");
	}
	
	/**
	 * 解析指定的日期字符串
	 * @param dateString 字符串的格式可以是yyyy-MM-dd HH:mm:ss.SSS中的全部或者部分， 
	 * 最完整的格式为yyyy-MM-dd HH:mm:ss.SSS， 最简单的格式为 HH:mm
	 * @return
	 * @throws ParseException 如果指定的字符串不是日期，则抛出异常
	 */
	public static java.util.Date parserDateString(String dateString) throws ParseException{
		
		ParseException pe = null;
		for(String pattern : datePtterns){
			SimpleDateFormat sdf = getChSimpleDateFormat(pattern);
			try {
				return sdf.parse(dateString);
			} catch (ParseException e) {
				pe = e;
			}
		}
		
		throw pe;
	}
	
	
	public static Calendar convert(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	
	/**
	 * 创建一个Calendar
	 * @param time 时间值,单位：ms
	 * @return
	 */
	public static Calendar getCalendar(long time){
		Calendar c = getChCalendar();
		c.setTimeInMillis(time);
		return c;
	}
	
	/**
	 * 判断time是否在start与end之间
	 * @param time
	 * @param start
	 * @param end
	 * @return 当{@code time >= start && time < end}时返回true,否则返回false
	 */
	public static boolean isBetween(Calendar time, Calendar start, Calendar end){
		long t = time.getTimeInMillis();
		return t>= start.getTimeInMillis() && t < end.getTimeInMillis();
	}
	/**
	 * 返回time1与time2是否表示相同的时间值
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameTimeValue(Calendar time1, Calendar time2){
		return time1.getTimeInMillis() - time2.getTimeInMillis() == 0;
	}
	/**
	 * 返回time1与time2与表示的时间是否是同一个小时
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameHour(Calendar time1 , Calendar time2){
		time1 = getHourStart(time1);
		time2 = getHourStart(time2);
		return isSameTimeValue(time1, time2);
	}

	/**
	 * 返回time1与time2与表示的时间是否是同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(Calendar time1 , Calendar time2){
		time1 = getDayStart(time1);
		time2 = getDayStart(time2);
		return isSameTimeValue(time1, time2);
	}

	/**
	 * 返回time1与time2与表示的时间是否是同一月
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameMonth(Calendar time1 , Calendar time2){
		time1 = getMonthStart(time1);
		time2 = getMonthStart(time2);
		return isSameTimeValue(time1, time2);
	}
	/**
	 * 将一个时间段按小时分组，返回每个小时的起始时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static List<Calendar> groupByHour(Calendar startTime, Calendar endTime){
		Calendar st = getHourStart(startTime);
		Calendar et = getHourEnd(endTime);
		List<Calendar> times = new ArrayList<Calendar>(24);
		times.add(st);
		Calendar next = st;
		while(true){
			next = copy(next);
			next.add(Calendar.HOUR_OF_DAY, 1);
			if(next.getTimeInMillis() >= et.getTimeInMillis()){
				break;
			}else{
				times.add(next);
			}
		}
		
		return times;
	}
	
	/**
	 * 将一个时间段按天分组，返回每天的起始时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static List<Calendar> groupByDay(Calendar startTime, Calendar endTime){
		Calendar st = getDayStart(startTime);
		Calendar et = getDayEnd(endTime);
		List<Calendar> times = new ArrayList<Calendar>(31);
		times.add(st);
		Calendar next = st;
		while(true){
			next = copy(next);
			next.add(Calendar.DAY_OF_MONTH, 1);
			if(next.getTimeInMillis() >= et.getTimeInMillis()){
				break;
			}else{
				times.add(next);
			}
		}
		
		return times;
		
		/*
		 * 
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		Calendar st = TimeTool.getMonthStart(s);
		Calendar et = TimeTool.getMonthEnd(e);
		System.out.println(formatTime(st, "yyyy-MM-dd HH"));
		System.out.println(formatTime(et, "yyyy-MM-dd HH"));
		List<Calendar> cs = groupByDay(st, et);
		for(Calendar c : cs){
			System.out.println(formatTime(c, "yyyy-MM-dd HH:mm:ss:ms"));
		}
		 */
	}
	public static List<Calendar> groupByMonth(Calendar startTime, Calendar endTime){
		Calendar st = getMonthStart(startTime);
		Calendar et = getMonthEnd(endTime);
		List<Calendar> times = new ArrayList<Calendar>(31);
		times.add(st);
		Calendar next = st;
		while(true){
			next = copy(next);
			next.add(Calendar.MONTH, 1);
			if(next.getTimeInMillis() >= et.getTimeInMillis()){
				break;
			}else{
				times.add(next);
			}
		}
		return times;
		/*
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		s.set(Calendar.MONTH, 1);
		e.set(Calendar.MONTH, 11);
		List<Calendar> cs = groupByMonth(s, e);
		for(Calendar c : cs){
			System.out.println(formatTime(c, "yyyy-MM-dd HH:mm:ss:ms"));
		}
		 */
	}
	//毫秒
	public static short DIFF_TYPE_MILLISECOND	= 1;
	//秒
	public static short DIFF_TYPE_SECOND	= 2;
	//分
	public static short DIFF_TYPE_MINUTE	= 3;
	//小时
	public static short DIFF_TYPE_HOUR	= 4;
	//天
	public static short DIFF_TYPE_DAY	= 5;
	
	public static Long getDiff(Timestamp start, Timestamp end, short type){
		if(start != null && end != null){
			long diff = end.getTime() - start.getTime();
			if(diff > 0){
				double result = -1.0;
				switch(type){
					case 1 :
						result = diff;
						break;
					case 2 :
						result = (double)(diff/1000);
						break;
					case 3 :
						result = (double)(diff/1000/60);
						break;
					case 4 :
						result = (double)(diff/1000/60/60);
						break;
					case 5 :
						result = (double)(diff/1000/60/60/24);
						break;
					default:
						break;
				}
				return (long) Math.ceil(result);
			}
		}
		return null;
	}
	/**
	 * 获取该时间，在当天的分钟
	 * @param time
	 * @return
	 */
	public static long getMinuteOfCurDay(Timestamp time){
		if(time == null)
			return 0;
		return (time.getTime()%24)/1000;
	}
	public static void main(String args[]){
		Calendar s = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(s.getTime()));
//		4014-11-23 01:54:04
//		2014-11-23 01:54:04
//
//
//
//		2800-11-22 10:04:24  	107062 
//		2014-11-22 10:04:24
//		2216-08-24 04:11:10 	80166 
//		2014-11-22 00:11:10
		long time1 = TimeUtil.valueOfTimestamp("4014-11-23 01:54:04").getTime();
		long time2 = TimeUtil.valueOfTimestamp("2014-11-23 01:54:04").getTime();
		System.out.println("4014-11-23 01:54:04   : " + time1);
		System.out.println("2014-11-23 01:54:04   : " + time2);
		System.out.println(time1 - time2);
		time1 = TimeUtil.valueOfTimestamp("2800-11-22 10:04:24").getTime();
		time2 = TimeUtil.valueOfTimestamp("2014-11-22 10:04:24").getTime();
		System.out.println("2800-11-22 10:04:24   : " + time1);
		System.out.println("2014-11-22 10:04:24   : " + time2);
		System.out.println(time1 - time2);
		time1 = TimeUtil.valueOfTimestamp("2216-08-24 04:11:10").getTime();
		time2 = TimeUtil.valueOfTimestamp("2014-11-22 00:11:10").getTime();
		System.out.println("2216-08-24 04:11:10   : " + time1);
		System.out.println("2014-11-22 00:11:10   : " + time2);
		System.out.println(time1 - time2);
	}
}
