package cn.wzq.studycommonslang3;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;


public class TestEnumUtils {

	
	
	
	
	
	public static void main(String[] args) {
		all();
	}
	
	
	
	
	
	public static void all() {
				
		testUseGetEnum();
		
		testUseGetEnumIgnoreCase();

		testUseGetEnumList();
		
		testUseGetEnumMap();
		
		testUseIsValidEnum();
		
		testUseIsValidEnumIgnoreCase();
	}
	

	

   enum ImagesTypeEnum {
	   JPG, 
	   JPEG, 
	   PNG, 
	   GIF;
   }
	
	
	
	public static void testUseGetEnum() {
		System.out.println("----- getEnum -----");
		
		// 通过类返回一个枚举 可能返回空
		ImagesTypeEnum imagesTypeEnum = EnumUtils.getEnum(ImagesTypeEnum.class, "JPG");		
		System.out.println(imagesTypeEnum);
		
		
		imagesTypeEnum = EnumUtils.getEnum(ImagesTypeEnum.class, "jpg");		
		System.out.println(imagesTypeEnum);
		
		System.out.println();
	}
	
	
	public static void testUseGetEnumIgnoreCase() {
		System.out.println("----- getEnumIgnoreCase -----");
		
		// 通过类返回一个枚举 可能返回空 忽略大小写
		ImagesTypeEnum imagesTypeEnum = EnumUtils.getEnumIgnoreCase(ImagesTypeEnum.class, "JPG");		
		System.out.println(imagesTypeEnum);
		
		
		imagesTypeEnum = EnumUtils.getEnumIgnoreCase(ImagesTypeEnum.class, "jpg");		
		System.out.println(imagesTypeEnum);
		
		System.out.println();
	}
		
	
	
	
	
	public static void testUseGetEnumList() {
		System.out.println("----- getEnumList -----");
		
		// 过类返回一个枚举集合
		List<ImagesTypeEnum> imagesTypeEnumList = EnumUtils.getEnumList(ImagesTypeEnum.class);
		
		imagesTypeEnumList.stream().forEach(
	        imagesTypeEnum1 -> System.out.println("imagesTypeEnum1 = " + imagesTypeEnum1)
	    );
		 
		System.out.println();
	}
	
	
	
	public static void testUseGetEnumMap() {
		System.out.println("----- getEnumMap -----");
		
		// 通过类返回一个枚举map
		Map<String, ImagesTypeEnum> imagesTypeEnumMap = EnumUtils.getEnumMap(ImagesTypeEnum.class);
		 
		imagesTypeEnumMap.forEach((k, v) -> System.out.println("key：" + k + ",value：" + v));
		
		
		System.out.println();
	}
	
	
	
	
	public static void testUseIsValidEnum() {
		System.out.println("----- isValidEnum -----");
		
		// 验证enumName是否在枚举中
				
		boolean result = EnumUtils.isValidEnum(ImagesTypeEnum.class, "JPG");
		System.out.println("result = " + result); // result = true
		
		boolean result1 = EnumUtils.isValidEnum(ImagesTypeEnum.class, "jpg");
		System.out.println("result1 = " + result1); // result1 = false
		    
		boolean result2 = EnumUtils.isValidEnum(ImagesTypeEnum.class, null);
		System.out.println("result2 = " + result2); // result2 = false
		
		System.out.println();
	}
	
	
	
	
	public static void testUseIsValidEnumIgnoreCase() {
		System.out.println("----- isValidEnumIgnoreCase -----");
		
		// 验证enumName是否在枚举中，返回true false,enumName忽略大小写
				
		boolean result = EnumUtils.isValidEnumIgnoreCase(ImagesTypeEnum.class, "JPG");
		System.out.println("result = " + result); // result = true
		
		boolean result1 = EnumUtils.isValidEnumIgnoreCase(ImagesTypeEnum.class, "jpg");
		System.out.println("result1 = " + result1); // result1 = true
		    
		boolean result2 = EnumUtils.isValidEnumIgnoreCase(ImagesTypeEnum.class, null);
		System.out.println("result2 = " + result2); // result2 = false
		
		System.out.println();
	}
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
}
