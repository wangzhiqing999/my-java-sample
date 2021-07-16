package cn.wzq.studycommonstext;

import org.apache.commons.text.StringTokenizer;

public class TestStringTokenizer {

	
	
	public static void main(String[] args) throws Exception {
		
		
		all();

	}

	
	
	
		
	public static void all() throws Exception {

		testUseNextToken();
		
		testUseDiff();
	}

	
	
	
	
	
	
	public static void testUseNextToken() {
		System.out.println("----- nextToken -----");
		
		String aString="AB-CD-EF-GH-IJ-KL-MN-OP-QR-ST-UV-WX-YZ";
		StringTokenizer strTokenizer=new StringTokenizer(aString, "-");
		
		while(strTokenizer.hasNext()) {
			System.out.println(strTokenizer.nextToken());	
		}
		
		
		System.out.println();
	}
	
	
	
	
	
	public static void testUseDiff() {
		
		
		
		String aString="AB-CD--EF---GH----";
		
		System.out.println("----- nextToken -----");
		StringTokenizer strTokenizer=new StringTokenizer(aString, "-");		
		while(strTokenizer.hasNext()) {
			System.out.println(strTokenizer.nextToken());	
		}
		
		
		System.out.println("----- String.split -----");
		String[] strArray = aString.split("-");
		for(String element: strArray)
		{
		    System.out.println(element);
		}		
		System.out.println();
		
		
		
		System.out.println();
		System.out.println();
	}
	
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		
		
		System.out.println();
		System.out.println();
	}
	
	
	
}
