package cn.wzq.studycommomscodec.digest;

import org.apache.commons.codec.digest.DigestUtils;

public class TestDigestUtils {

	
	
	public static void main(String[] args) throws Exception {
		all();

	}

	
	
	
		
	public static void all() throws Exception {
		
		
		testUseMd5Hex();
		
		testUseSha1Hex();
		
		testUseSha256Hex();
		
	}
	
	
	
	
	
	
	public static void testUseMd5Hex() {
		
		System.out.println("----- md5Hex  -----");
		String result  = DigestUtils.md5Hex("测试1234567890");
        System.out.println(result );
        
		System.out.println();
	}

	
	

	
	public static void testUseSha1Hex() {
		System.out.println("----- sha1Hex -----");
		
		String result  = DigestUtils.sha1Hex("测试1234567890");
        System.out.println(result );
        
		System.out.println();
	}
	
	
	
	public static void testUseSha256Hex() {
		System.out.println("----- sha256Hex -----");
		
		String result  = DigestUtils.sha256Hex("测试1234567890");
        System.out.println(result );
        
		System.out.println();
	}
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
	

}
