package cn.wzq.studycommonslang3;

import org.apache.commons.lang3.StringUtils;




// https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html
public class TestStringUtils {

	
	
    public static void main( String[] args )
    {
        all();
    }	
	
	

	public static void all() {
		
		testUseAbbreviate();	
		testUseAppendIfMissing();
		testUseCapitalize();
		testUseCenter();
		testUseChomp();
		testUseContains();
		testUseCountMatches();
		testUseDeleteWhitespace();
		testUseDifference();
		testUseEndsWith();
		testUseStartsWith();
		testUseEquals();
		testUseGetCommonPrefix();
		testUseIndexOf();
		testUseLastIndexOf();
		testUseIsAllUpperCase();
		testUseIsBlank();		
		testUseIsEmpty();
		testUseIsNumeric();
		testUseJoin();
		testUseUpperCase();
		testUseReplace();
		testUseRepeat();
		testUseReverse();
		testUseRemove();
		testUseSplit();
		testUseStrip();
		testUseSubstring();
		testUseLeft();
	}
	
	
	
	
	public static void testUseAbbreviate() {
		System.out.println("----- abbreviate -----");

		// 缩短到某长度,用...结尾.其实就是(substring(str, 0, max-3) + "...")		
		String source = "我是一段很长的文本，1234567890. ABCDEFG. ";		
		String result = StringUtils.abbreviate(source, 6);				
		System.out.println(source);
		System.out.println(result);
		System.out.println();

	}
	

	public static void testUseAppendIfMissing() {
		System.out.println("----- appendIfMissing -----");
		
		//字符串结尾的后缀是否与你要结尾的后缀匹配，若不匹配则添加后缀		
		System.out.println(StringUtils.appendIfMissing("1234567890", "finish"));		
		System.out.println(StringUtils.appendIfMissingIgnoreCase("1234567890 FINISH", "finish"));		
		System.out.println();
	}
	
	
	public static void testUseCapitalize() {
		System.out.println("----- capitalize / uncapitalize  -----");
		
		//首字母大小写转换
		System.out.println(StringUtils.capitalize("cat"));
		System.out.println(StringUtils.uncapitalize("Cat"));
		System.out.println();
	}
	
	
	public static void testUseCenter() {
		System.out.println("----- center -----");
		
		//字符串扩充至指定大小且居中（若扩充大小少于原字符大小则返回原字符，若扩充大小为 负数则为0计算 ）
		System.out.println(StringUtils.center("abcd", 2));
		System.out.println(StringUtils.center("ab", -1));
		System.out.println(StringUtils.center("ab", 4));
		System.out.println(StringUtils.center("a", 4, "yz"));
		System.out.println(StringUtils.center("abc", 7, ""));
		System.out.println();
	}
	
	
	public static void testUseChomp() {
		System.out.println("----- center -----");
		
		//去除字符串中的"\n", "\r", or "\r\n"
		System.out.println("Hello \n World \r !\r\n!");
		System.out.println();
	}
	
	
	
	public static void testUseContains() {
		System.out.println("----- contains -----");
		
		// 判断一字符串是否包含另一字符串。
		System.out.println(StringUtils.contains("abcdefg", "z"));
		System.out.println(StringUtils.containsIgnoreCase("abcdefg", "A"));
		System.out.println();
	}
	

	
	public static void testUseCountMatches() {
		System.out.println("----- countMatches -----");
		
		//统计一字符串在另一字符串中出现次数		
		System.out.println(StringUtils.countMatches("统计一字符串在另一字符串中出现次数", "字符"));
		System.out.println();
	}
	

	
	public static void testUseDeleteWhitespace() {
		System.out.println("----- deleteWhitespace -----");
		
		//删除字符串中的梭有空格
		System.out.println(StringUtils.deleteWhitespace("   ab  c  d   efg"));
		System.out.println();
	}	
	
	
	
	public static void testUseDifference() {
		System.out.println("----- difference -----");
		
		//比较两字符串 返回不同之处 确切的说是返回第二个参数中与第一个参数所不同的字符串
		System.out.println(StringUtils.difference("abcde", "abxyz"));
		System.out.println(StringUtils.difference("abcde", "abc"));
		System.out.println(StringUtils.difference("abcde", "abcdef"));
		System.out.println();
	}
	
	

	
	public static void testUseEndsWith() {
		System.out.println("----- endsWith -----");

		// 检查字符串结尾后缀是否匹配
		System.out.println(StringUtils.endsWith("abcdef", "def"));
		System.out.println(StringUtils.endsWithIgnoreCase("ABCDEF", "def"));
		System.out.println(StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}));
		
		System.out.println();
	}	
	
	
	public static void testUseStartsWith() {
		System.out.println("----- startsWith -----");
		
		// 检查起始字符串是否匹配
		System.out.println(StringUtils.startsWith("abcdef", "abc"));
		System.out.println(StringUtils.startsWithIgnoreCase("ABCDEF", "ABC"));
		System.out.println(StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}));
		System.out.println();
	}
		
	
	
	
	public static void testUseEquals() {
		System.out.println("----- equals -----");
		
		// 判断两字符串是否相同
		System.out.println(StringUtils.equals("abc", "abc"));
		System.out.println(StringUtils.equalsIgnoreCase("abc", "ABC"));
		System.out.println();
	}
	
	
	
	public static void testUseGetCommonPrefix() {
		System.out.println("----- getCommonPrefix -----");
		
		// 比较字符串数组内的所有元素的字符序列，起始一致则返回一致的字符串，若无则返回""
		System.out.println(StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}));
		System.out.println();
	}
	
	
	
	public static void testUseIndexOf() {
		System.out.println("----- indexOf -----");
		
		// 正向查找字符在字符串中第一次出现的位置
		System.out.println(StringUtils.indexOf("aabaabaa", "b"));
		
		// 从第三个字符后开始查找
		System.out.println(StringUtils.indexOf("aabaabaa", "b", 3));
		
		// 查找第三次出现的位置.
		System.out.println(StringUtils.ordinalIndexOf("aabaabaa", "a", 3));
		System.out.println();
	}
	
	
	public static void testUseLastIndexOf() {
		System.out.println("----- lastIndexOf -----");
		
		// 反向查找字符串第一次出现的位置
		System.out.println(StringUtils.lastIndexOf("aabaabaa", "b"));
		
		// 反向 从第三个字符后开始查找
		System.out.println(StringUtils.lastIndexOf("aabaabaa", "b", 3));
		
		// 反向 查找第三次出现的位置.
		System.out.println(StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 3));
		System.out.println();
	}
		
	
	
	
	public static void testUseIsAllUpperCase() {
		System.out.println("----- isAllUpperCase / isAllLowerCase -----");
		
		// 判断字符串大写/小写
		System.out.println(StringUtils.isAllUpperCase("ABC"));
		System.out.println(StringUtils.isAllLowerCase("abC"));
	}
	
	
		
	public static void testUseIsBlank() {
		System.out.println("----- isBlank -----");
		
		// 判断是否为空。
		System.out.println(StringUtils.isBlank(null));
		System.out.println(StringUtils.isBlank(""));
		System.out.println(StringUtils.isBlank("   "));
		
		System.out.println("----- isNoneBlank-----");		
		System.out.println(StringUtils.isNoneBlank(" ", "abc"));

		System.out.println();
	}
	
	
	
	public static void testUseIsEmpty() {
		System.out.println("----- isEmpty -----");
		
		//  判断是否为空。
		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isEmpty(""));
		System.out.println(StringUtils.isEmpty("   "));
		
		System.out.println("----- isNoneEmpty-----");		
		System.out.println(StringUtils.isNoneEmpty(" ", "abc"));
 
		System.out.println();
	}
	
	
	
	public static void testUseIsNumeric() {
		System.out.println("----- isNumeric -----");
		
		// 判断字符串数字
		System.out.println(StringUtils.isNumeric("123"));
		System.out.println(StringUtils.isNumeric("12 3"));
		System.out.println(StringUtils.isNumeric("12.3"));
		
		
		System.out.println("----- isNumericSpace -----");
		System.out.println(StringUtils.isNumericSpace("1234 5678 9000"));
		
		System.out.println();
	}
		
	
	
	public static void testUseJoin() {
		System.out.println("----- join -----");
		int [] source = new int[] {1, 2, 3, 4, 5};
		
		System.out.println(StringUtils.join(source, ';'));
		System.out.println();
	}
	
	
	
	public static void testUseUpperCase() {
		System.out.println("----- upperCase -----");
		
		// 大小写转换
		System.out.println(StringUtils.upperCase("aBc"));
		System.out.println(StringUtils.lowerCase("aBc"));
		System.out.println(StringUtils.swapCase("The dog has a BONE"));
		System.out.println();
	}	
	
	
	
	public static void testUseReplace() {
		System.out.println("----- replace / overlay / replaceEach  -----");

		// 替换字符串内容
		System.out.println(StringUtils.replace("abcdefgabcdefg", "a", "z"));
		// 指定区域
		System.out.println(StringUtils.overlay("abcdefgabcdefg", "zz", 2, 4));
		// 多组指定替换  ab-->w  d-->t
		System.out.println(StringUtils.replaceEach("abcdefgabcdefg", new String[]{"ab", "d"},
                new String[]{"W", "T"}));
		System.out.println();
	}	
	
	
	
	
	public static void testUseRepeat() {
		System.out.println("----- repeat -----");
		
		// 重复字符
		System.out.println(StringUtils.repeat('A', 5));
		
		System.out.println();
	}
	
	
	public static void testUseReverse() {
		System.out.println("----- reverse -----");
		
		// 反转字符串
		System.out.println(StringUtils.reverse("ABCDEFG"));
		
		System.out.println();
	}
	
	
	
	
	public static void testUseRemove() {
		System.out.println("----- remove -----");
		
		
		//删除某字符
		System.out.println(StringUtils.remove("abcdefgabcdefg", 'd'));
		
		System.out.println();
	}
	
	
	
	public static void testUseSplit() {
		System.out.println("----- split -----");
		
		String[] result1 = StringUtils.split("a..b.c", '.');
		for(String element: result1)
		{
		    System.out.println(element);
		}		
		System.out.println();
		
		
		String[] result2 = StringUtils.split("ab:cd:ef", ":", 2);
		for(String element: result2)
		{
		    System.out.println(element);
		}		
		System.out.println();
		
		
		String[] result3 = StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2);
		for(String element: result3)
		{
		    System.out.println(element);
		}		
		System.out.println();
		
		
		String[] result4 = StringUtils.splitByWholeSeparatorPreserveAllTokens("ab::cd:ef", ":");
		for(String element: result4)
		{
		    System.out.println(element);
		}
		System.out.println();
		
	}
	
	
	
	
	
	public static void testUseStrip() {
		System.out.println("----- strip -----");
		
		// 去除首尾空格，类似trim……（stripStart、stripEnd、stripAll、stripAccents）
		System.out.println(StringUtils.strip("  ab cd ef   "));
		
		System.out.println(StringUtils.stripToNull("  ab cd ef   "));
		System.out.println(StringUtils.stripToNull(null));
		
		System.out.println(StringUtils.stripToEmpty("  ab cd ef   "));
		System.out.println(StringUtils.stripToEmpty(null));
		
		System.out.println();
	}
	
	
	
	public static void testUseSubstring() {
		System.out.println("----- substring -----");

		// 截取字符串
		System.out.println(StringUtils.substring("abcdefg", 2));
		
		// 注意这个 与  mid 的区别.
		// 这里的第二个参数是结束位置而不是长度
		System.out.println(StringUtils.substring("abcdefg", 2, 4));

		
		// 指定字符之前的
		System.out.println(StringUtils.substringBefore("abcba", "b"));
		// 从后向前指定字符之前的
		System.out.println(StringUtils.substringBeforeLast("abcba", "b"));
		
		// 指定字符之后的
		System.out.println(StringUtils.substringAfter("abcba", "b"));
		// 从后向前指定字符之后的
		System.out.println(StringUtils.substringAfterLast("abcba", "b"));
		
		// 指定字符之间的.
		System.out.println(StringUtils.substringBetween("tagabctag", "tag"));
		System.out.println(StringUtils.substringBetween("<span>abcdefg</span>", "<span>", "</span>"));
		
		System.out.println();
	}
	
	
	
	public static void testUseLeft() {
		System.out.println("----- left / right / mid -----");
		
		// left、right从左(右)开始截取n位字符
		System.out.println(StringUtils.left("abcdefg", 2));
		System.out.println(StringUtils.right("abcdefg", 2));
		
		// mid 从第n位开始截取m位字符
		System.out.println(StringUtils.mid("abcdefg", 2, 4));
	}
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
}
