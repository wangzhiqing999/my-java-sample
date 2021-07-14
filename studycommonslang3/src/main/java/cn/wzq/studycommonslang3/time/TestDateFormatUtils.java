package cn.wzq.studycommonslang3.time;


import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;


// https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/time/DateFormatUtils.html
public class TestDateFormatUtils {

	
	
	public static void main(String[] args) throws Exception {
		
		
		all();

	}

	
	
	
		
	public static void all() throws Exception {
		
		
		testUseFormat();
		
		testUseFormatUTC();
	}
	
	
	
	
	// y	years
	// M	months
	// d	days
	// H	hours
	// m	minutes
	// s	seconds
	// S	milliseconds
	// 'text'	arbitrary text content

	
	
	
	public static void testUseFormat() {
		System.out.println("----- format -----");
		
		
		Date date = new Date();
		
		System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
		System.out.println();
	}
	
	
	
	
	public static void testUseFormatUTC() {
		System.out.println("----- formatUTC -----");
		
		
		Date date = new Date();
		
		System.out.println(DateFormatUtils.formatUTC(date, "yyyy-MM-dd HH:mm:ss"));
		System.out.println();
	}
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
}
