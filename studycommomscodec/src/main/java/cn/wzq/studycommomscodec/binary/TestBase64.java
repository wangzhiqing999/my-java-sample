package cn.wzq.studycommomscodec.binary;

import org.apache.commons.codec.binary.Base64;

public class TestBase64 {

	
	
    public static void main( String[] args )
    {
    	testUse();
    }
	
	
    
    
    
    
    
	
	public static void testUse() {
		
		
		Base64 base64 = new Base64();
        
		System.out.println("----- Base64.encodeToString  -----");
		String s = base64.encodeToString("测试1234567890".getBytes());
        System.out.println(s);
        
        
        System.out.println("----- Base64.decode  -----");
        String s1 = new String(base64.decode(s));
        System.out.println(s1);

        
		System.out.println();
	}
	
	
	
}
