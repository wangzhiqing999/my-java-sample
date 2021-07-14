package cn.wzq.studycommonslang3.time;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class TestDateUtils {

	
	public static void main(String[] args) throws Exception {
		
		
		all();

	}

	
	
	
		
	public static void all() throws Exception {
		
		
		testUseAddMilliseconds();
		testUseAddSeconds();
		testUseAddMinutes();
		testUseAddHours();
		
		testUseAddDays();
		testUseAddWeeks();
		testUseAddMonths();
		testUseAddYears();
		
		
		
		testUseSetYears();
		
		
		testUseIsSameDay();
	
		testUseParseDate();
		
		testUseParseDateStrictly();
	}
	
	
	
	public static void testUseAddMilliseconds() {
		System.out.println("----- addMilliseconds -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addMilliseconds(date, 1000));
		System.out.println(DateUtils.addMilliseconds(date, -3000));
		System.out.println(DateUtils.addMilliseconds(date, 5000));
		
		System.out.println();
	}
	
	public static void testUseAddSeconds() {
		System.out.println("----- addSeconds -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addSeconds(date, 1));
		System.out.println(DateUtils.addSeconds(date, -3));
		System.out.println(DateUtils.addSeconds(date, 5));
		
		System.out.println();
	}
	
	public static void testUseAddMinutes() {
		System.out.println("----- addMinutes -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addMinutes(date, 1));
		System.out.println(DateUtils.addMinutes(date, -3));
		System.out.println(DateUtils.addMinutes(date, 5));
		
		System.out.println();
	}
	
	public static void testUseAddHours() {
		System.out.println("----- addHours -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addHours(date, 1));
		System.out.println(DateUtils.addHours(date, -3));
		System.out.println(DateUtils.addHours(date, 5));
		
		System.out.println();
	}
	
	
	
	public static void testUseAddDays() {
		System.out.println("----- addDays -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addDays(date, 1));
		System.out.println(DateUtils.addDays(date, -3));
		System.out.println(DateUtils.addDays(date, 5));
		
		System.out.println();
	}
	
	public static void testUseAddWeeks() {
		System.out.println("----- addWeeks -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addWeeks(date, 1));
		System.out.println(DateUtils.addWeeks(date, -3));
		System.out.println(DateUtils.addWeeks(date, 5));
		
		System.out.println();
	}
	
	public static void testUseAddMonths() {
		System.out.println("----- addMonths -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addMonths(date, 1));
		System.out.println(DateUtils.addMonths(date, -3));
		System.out.println(DateUtils.addMonths(date, 5));
		
		System.out.println();
	}
			
	
	
	public static void testUseAddYears() {
		System.out.println("----- addYears -----");
		
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtils.addYears(date, 1));
		System.out.println(DateUtils.addYears(date, -3));
		System.out.println(DateUtils.addYears(date, 5));
		
		System.out.println();
	}
		

	
	
	
	
	
	
	
	public static void testUseIsSameDay() throws Exception {
		System.out.println("----- isSameDay -----");
		
		//判断是否同一天
		Date date1 = DateUtils.parseDate("20171012 14:30:12", Locale.TRADITIONAL_CHINESE, "yyyyMMdd hh:mm:ss");
		Date date2 = DateUtils.parseDate("20171012 20:20:20", Locale.TRADITIONAL_CHINESE, "yyyyMMdd hh:mm:ss");
		
		System.out.println(DateUtils.isSameDay(date1, date2));
		System.out.println(DateUtils.isSameDay(date1, new Date()));
		
		System.out.println();
	}
	
	
	
	
	
	
	public static void testUseParseDate() throws Exception {
		System.out.println("----- parseDate -----");
	
		//字符串时间转换为Date
		// arg0 : 日期字符串 String
		// arg1 : 特定的地理，政治和文化地区.可以传null
		// arg3 : 日期格式.与arg0格式一致 String
		// 该方法对日期和时间的解释是宽松的
		// 宽松的解释日期（如 1996 年 2 月 42 日）将被视为等同于 1996 年 2 月 1 日后的第 41 天
		// 如果是严格的解释，此类日期就会引发异常
		Date date1 = DateUtils.parseDate("19960242 14:30:12", Locale.TRADITIONAL_CHINESE, "yyyyMMdd hh:mm:ss");
				
		System.out.println(date1);
		System.out.println();
	}
	
	
	
	public static void testUseParseDateStrictly() throws Exception  {
		System.out.println("----- parseDateStrictly -----");
		
		// String转换成Date 严格的
		// arg0 : 日期字符串 String
		// arg1 : 特定的地理，政治和文化地区.可以传null
		// arg3 : 日期格式.与arg0格式一致 String
		// 该方法对日期和时间的解释是严格的
		Date date3 = DateUtils.parseDateStrictly("20171012", Locale.TRADITIONAL_CHINESE, "yyyyMMdd");
		
		System.out.println(date3);
		System.out.println();
	}
	
	
	
	
	
	public static void testUseSetYears() {
		
		Date date = new Date();
		System.out.println(date);
		
		
		System.out.println("----- setYears -----");
		date = DateUtils.setYears(date, 2020);
		System.out.println(date);
		
		System.out.println("----- setMonths -----");
		date = DateUtils.setMonths(date, 1);
		System.out.println(date);

		System.out.println("----- setDays -----");
		date = DateUtils.setDays(date, 1);
		System.out.println(date);		
		
		System.out.println("----- setHours -----");
		date = DateUtils.setHours(date, 1);
		System.out.println(date);
		
		System.out.println("----- setMinutes -----");
		date = DateUtils.setMinutes(date, 1);
		System.out.println(date);
		
		System.out.println("----- setSeconds -----");
		date = DateUtils.setSeconds(date, 1);
		System.out.println(date);
		
		System.out.println();
	}
	
	
	
	
	

	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
}
