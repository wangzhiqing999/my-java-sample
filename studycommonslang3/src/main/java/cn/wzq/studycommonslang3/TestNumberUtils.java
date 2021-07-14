package cn.wzq.studycommonslang3;

import org.apache.commons.lang3.math.NumberUtils;



// https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/math/NumberUtils.html
public class TestNumberUtils {

	
	public static void main(String[] args) {
		all();
	}
	
	
	
	
	
	public static void all() {
		
	
		testUseMax();
		
		testUseIsDigits();
		
		testUseIsParsable();
		
		testUseIsCreatable();
	}
	

	
	
	
	public static void testUseMax() {
		System.out.println("----- max / min -----");
		
		int[] source = new int[] { 1, 2, 3, 4, 5, 6, 7 };
		
		
		//从数组中选出最大值
		System.out.println(NumberUtils.max(source));
		
		//从数组中选出最小值
		System.out.println(NumberUtils.min(source));
		
		System.out.println();
	}
	
	
	
	
	public static void testUseIsDigits() {
		System.out.println("----- isDigits -----");
		
		// 判断字符串是不是  整数.
		System.out.println(NumberUtils.isDigits("153.4"));
		
		System.out.println(NumberUtils.isDigits("12345"));		
		System.out.println(NumberUtils.isDigits("-12345"));
		
		
		System.out.println(NumberUtils.isDigits("0012345"));	
		
		
		System.out.println(NumberUtils.isDigits("12345678901234567890123456789012345678901234567890"));
		
		System.out.println();
	}
	
	
	
	
	
	public static void testUseIsCreatable() {
		System.out.println("----- isCreatable -----");
		
		// 判断字符串是否是有效数字
		// isNumber(String str) Deprecated.
		// This feature will be removed in Lang 4.0, use isCreatable(String) instead
		System.out.println(NumberUtils.isCreatable("153.4"));
		
		System.out.println(NumberUtils.isCreatable("12345"));		
		System.out.println(NumberUtils.isCreatable("-12345"));
		
		
		System.out.println(NumberUtils.isCreatable("0012345"));	
		
		
		System.out.println(NumberUtils.isCreatable("12345678901234567890123456789012345678901234567890"));
		
		System.out.println();
	}
	
	
	
	
	
	public static void testUseIsParsable() {
		System.out.println("----- isParsable -----");
		
		// 
		System.out.println(NumberUtils.isParsable("153.4"));
		
		System.out.println(NumberUtils.isParsable("12345"));		
		System.out.println(NumberUtils.isParsable("-12345"));
		
		
		System.out.println(NumberUtils.isParsable("0012345"));	
		
		
		System.out.println(NumberUtils.isParsable("12345678901234567890123456789012345678901234567890"));
		
		System.out.println();
	}
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
}
