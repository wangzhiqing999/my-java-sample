package cn.wzq.studycommonstext;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;



// http://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/StringSubstitutor.html
public class TestStringSubstitutor {

	public static void main(String[] args) throws Exception {
		
		
		all();

	}

	
	
	
		
	public static void all() throws Exception {
		
		testUseReplace();
		
		testUseReplaceWithDefaultValue();
		
		
		testUseReplaceSystemProperties();
	}

	
	
	
	
	
	
	
	
	public static void testUseReplace() {
		System.out.println("----- replace -----");
		
		Map<String, String> replaceValue = new HashMap<String, String>();
	    replaceValue.put("name", "张三");
	    replaceValue.put("age", "27");
		
		
	    StringSubstitutor strSubstitutor = new StringSubstitutor(replaceValue);
	    String template1 = "姓名：${name},  年龄： ${age}";
	    String resultText = strSubstitutor.replace(template1);
	    		
		System.out.println(resultText);
		
		System.out.println();
	}
	
	
	
	
	public static void testUseReplaceWithDefaultValue() {
		System.out.println("----- replace -----");
		
		Map<String, String> replaceValue = new HashMap<String, String>();
	    replaceValue.put("name", "张三");
	    replaceValue.put("age", "27");
		
		
	    StringSubstitutor strSubstitutor = new StringSubstitutor(replaceValue);
	    String template1 = "姓名：${name},  年龄： ${age},  所在地： ${area:-未指定}";
	    String resultText = strSubstitutor.replace(template1);
	    		
		System.out.println(resultText);
		
		
		
		
		replaceValue.put("area", "上海");
		resultText = strSubstitutor.replace(template1);
		
		System.out.println(resultText);
		
		
		
		System.out.println();
	}
	
	
	
	
	
	
	
	public static void testUseReplaceSystemProperties() {
		System.out.println("----- replaceSystemProperties -----");
		
		
		
		System.out.println(StringSubstitutor.replaceSystemProperties(
		          "You are running with java.version = ${java.version} and os.name = ${os.name}."));
		
		System.out.println();
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		
		
		System.out.println();
		System.out.println();
	}
	
	
	
}
