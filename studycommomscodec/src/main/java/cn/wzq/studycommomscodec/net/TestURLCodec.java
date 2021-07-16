package cn.wzq.studycommomscodec.net;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;


public class TestURLCodec {

	public static void main(String[] args) throws Exception {

		
		testUse();

	}

	
	
	
	
	
	public static void testUse() throws EncoderException, DecoderException {
		
		String url = "https://www.oschina.net/?name=你好";
        URLCodec codec = new URLCodec();
        
        
        String encode = codec.encode(url);        
        System.out.println(encode);
        
        
        String decode = codec.decode(encode);
        System.out.println(decode);
        
	}
	
	
	
}
