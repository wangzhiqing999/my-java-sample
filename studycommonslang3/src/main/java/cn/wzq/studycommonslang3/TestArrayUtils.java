package cn.wzq.studycommonslang3;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class TestArrayUtils {

	public static void main(String[] args) {
		
		
		all();

	}

	
	
	
	
	
	
	public static void all() {
		
		testUseToArray();
		
		testUseIsEquals();
		
		testUseContains();
		
		testUseToMap();
		
		
		testUseReverse();
		
		testUseShift();
		
		
		testUseToString();
		
		testUseSubarray();
		
		

		testUseToObject();
		
		testUseToPrimitive();
		
		testUseToStringArray();
	}
	
	
	
	
	
	
	public static void testUseToArray() {
		System.out.println("----- toArray -----");
		
		String[] result = ArrayUtils.toArray("1", "2", "3", "4", "5");
		for(String element: result)
		{
		    System.out.println(element);
		}		
		
		System.out.println();
	}
	
	
	

	public static void testUseIsEquals() {
		System.out.println("----- isEquals -----");
		
		
		String[] arr1 = ArrayUtils.toArray("1", "2", "3", "4", "5");
		String[] arr2 = ArrayUtils.toArray("1", "2", "3", "4", "5");
		String[] arr3 = ArrayUtils.toArray("2", "3", "4", "5", "1");
		
		
		
		// isEquals(Object array1, Object array2)
		// Deprecated. 
		// this method has been replaced by java.util.Objects.deepEquals(Object, Object) and will be removed from future releases.
		
		
		System.out.println(java.util.Objects.deepEquals(arr1,arr2));
		System.out.println(java.util.Objects.deepEquals(arr2,arr3));
		

		System.out.println();
	}
	
	
	
	public static void testUseContains() {
		System.out.println("----- contains -----");
		
		String[] arr1 = ArrayUtils.toArray("1", "2", "3", "4", "5");
		
		System.out.println( ArrayUtils.contains(arr1, "3"));
		System.out.println( ArrayUtils.contains(arr1, "0"));
		
		System.out.println();
	}
	
	
	
	
	public static void testUseToMap() {
		System.out.println("----- toMap -----");
		
		String[][] source = new String[][] { 
            { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } };
            
        Map map = ArrayUtils.toMap(source);
            
		System.out.println(map.get("RED"));
		System.out.println(map.get("GREEN"));
		System.out.println(map.get("BLUE"));
		
		System.out.println();
	}
	
	
	
	
	
	public static void testUseReverse() {
		System.out.println("----- reverse -----");
		// 反转数组
		String[] result = ArrayUtils.toArray("1", "2", "3", "4", "5");
		ArrayUtils.reverse(result);		
		for(String element: result)
		{
		    System.out.println(element);
		}		
		
		System.out.println();
	}
	
	
	
	public static void testUseShift() {
		System.out.println("----- shift -----");

		// 打乱数组
		String[] result = ArrayUtils.toArray("1", "2", "3", "4", "5");
		ArrayUtils.shift(result, 3);
		for(String element: result)
		{
		    System.out.println(element);
		}		

		System.out.println();
	}
	
	
	
	
	public static void testUseToString() {
		System.out.println("----- toString -----");

		String[] result = ArrayUtils.toArray("1", "2", "3", "4", "5");
		
		// 将一个数组转换成String 用于输出处理。
		System.out.println(ArrayUtils.toString(result));
		
		System.out.println();
	}
	
	
	
	
	public static void testUseSubarray() {
		System.out.println("----- subarray  -----");
		// 截取 子数组
				
		String[] source = ArrayUtils.toArray("1", "2", "3", "4", "5");
		
		String[] result1 = ArrayUtils.subarray(source, 0, 1);		
		System.out.println(ArrayUtils.toString(result1));
		
		String[] result2 = ArrayUtils.subarray(source, 1, 3);		
		System.out.println(ArrayUtils.toString(result2));
		
		System.out.println();
	}
	
	
	
	
	
	
	
	
	public static void testUseToObject() {
		System.out.println("----- toObject -----");
		
		
		// 可以用来完成 int[] 向  Integer[] 的转换.
		
		int[] source = new int[] {1, 2, 3, 4, 5};
		
		Integer[] integers = ArrayUtils.toObject(source);
		
		System.out.println(ArrayUtils.toString(integers));
		System.out.println();
	}
	
	
	public static void testUseToPrimitive() {
		System.out.println("----- toPrimitive -----");
		
		// 可以用来完成 Integer[] 向 int [] 的转换.
		
		Integer[] source = new Integer[] {1, 2, 3, 4, 5};
		
		int[] ints = ArrayUtils.toPrimitive(source);
		
		System.out.println(ArrayUtils.toString(ints));
		System.out.println();
	}
	
	
	
	
	public static void testUseToStringArray() {
		System.out.println("----- toStringArray -----");
		
		// 可以用来完成 Integer[] 向  String [] 的转换.
		
		Integer[] source = new Integer[] {1, 2, 3, 4, 5};		
		String[] strings  = ArrayUtils.toStringArray(source);		
		System.out.println(ArrayUtils.toString(strings ));
		
		
		// 如果源数组中包含NULL 
		Integer[] source2 = new Integer[] {1, 2, 3, 4, 5, null, 7, 8};		
		String[] strings2  = ArrayUtils.toStringArray(source2, "");		
		System.out.println(ArrayUtils.toString(strings2 ));
		
		
		System.out.println();
	}
	
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
	
}
