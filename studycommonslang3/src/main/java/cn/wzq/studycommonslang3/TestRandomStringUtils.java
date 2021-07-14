package cn.wzq.studycommonslang3;

import org.apache.commons.lang3.RandomStringUtils;



// https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/RandomStringUtils.html
public class TestRandomStringUtils {

	
	
	public static void main(String[] args) {
		all();
	}
	
	
	
	
	
	public static void all() {
		
	
		testUseRandomNumeric();
		testUseRandom();
	}
	
	
	
	
	public static void testUseRandomNumeric() {
		System.out.println("----- randomNumeric -----");
		
		// 随机生成几位数字 ( 可以用于生成  数字的 验证码 )
		System.out.println(RandomStringUtils.randomNumeric(4));
		
		System.out.println(RandomStringUtils.randomNumeric(6));
		
		System.out.println(RandomStringUtils.randomNumeric(8));
		
		System.out.println();
	}
	
	
	
	public static void testUseRandom() {
		System.out.println("----- random -----");
		
		// 在指定字符串中生成几位字符长度的随机字符串 ( 可以用于生成  字母与数字的 验证码 )
		System.out.println(RandomStringUtils.random(4, "123456789ABCDEFG"));
		
		System.out.println(RandomStringUtils.random(6, "123456789ABCDEFG"));
		
		System.out.println(RandomStringUtils.random(8, "123456789ABCDEFG"));
		
		System.out.println();
	}
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}

}
