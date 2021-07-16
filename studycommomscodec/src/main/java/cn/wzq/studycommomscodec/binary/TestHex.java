package cn.wzq.studycommomscodec.binary;

import org.apache.commons.codec.binary.Hex;

public class TestHex {

	
	
    public static void main( String[] args ) throws Exception
    {
    	testUse();
    }
	
	
    
    
    
    
    
	
	public static void testUse() throws Exception {
		
		
		System.out.println("----- Hex.encodeHexString  -----");
		String s = Hex.encodeHexString("测试1234567890".getBytes("UTF-8"));
        System.out.println(s);
        
        
        
        
        System.out.println("----- Hex.encodeHex  -----");
        byte[] decodeHex = Hex.decodeHex(s);
        String s1 = new String(decodeHex);
        System.out.println(s1);

        
		System.out.println();
	}
	
	
	
	
}
