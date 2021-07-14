package cn.wzq.studycommonslang3;

import org.apache.commons.lang3.SystemUtils;



// https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/SystemUtils.html
public class TestSystemUtils {

	
	
	public static void main(String[] args) {
		all();
	}
	
	
	
	
	
	public static void all() {
		
		testUseStaticField();
				
		testUseGetEnvironmentVariable();
		
		testUseGetHostName();
		
		testUseGetJavaHome();
		
		testUseGetJavaIoTmpDir();
		
		testUseGetUserDir();
		
		testUseGetUserHome();
		
		testUseGetUserName();
	}
	

	
	

	public static void testUseStaticField() {
		System.out.println("----- static Field  -----");
		
		System.out.println("JAVA_HOME = " + SystemUtils.JAVA_HOME);		
		System.out.println("JAVA_VERSION = " + SystemUtils.JAVA_VERSION);		
		System.out.println("JAVA_VM_NAME = " + SystemUtils.JAVA_VM_NAME);
		
		
		System.out.println("OS_ARCH = " + SystemUtils.OS_ARCH);
		System.out.println("OS_NAME = " + SystemUtils.OS_NAME);
		System.out.println("OS_VERSION = " + SystemUtils.OS_VERSION);
		
		
		System.out.println("USER_DIR = " + SystemUtils.USER_DIR);
		System.out.println("USER_HOME = " + SystemUtils.USER_HOME);
		System.out.println("USER_LANGUAGE = " + SystemUtils.USER_LANGUAGE);
		
		System.out.println();
	}
	
	
	
	
	
	public static void testUseGetEnvironmentVariable() {
		System.out.println("----- getEnvironmentVariable -----");
		
		// 获取环境设置.
		// windows， 运行 cmd. 输入 set.  观察 环境变量得设置. 
		System.out.println("JAVA_HOME = " + SystemUtils.getEnvironmentVariable("JAVA_HOME", ""));
		System.out.println("OS = " + SystemUtils.getEnvironmentVariable("OS", ""));
	
		System.out.println();
	}
	
	
	
	
	public static void testUseGetHostName() {
		System.out.println("----- getHostName -----");
		
		System.out.println(SystemUtils.getHostName());
		System.out.println();
	}
	
	
	
	public static void testUseGetJavaHome() {
		System.out.println("----- getJavaHome -----");
		
		System.out.println(SystemUtils.getJavaHome());
		System.out.println();
	}
	
	
	
	
	public static void testUseGetJavaIoTmpDir() {
		System.out.println("----- getJavaIoTmpDir -----");
		
		System.out.println(SystemUtils.getJavaIoTmpDir());
		System.out.println();
	}
	
	
	public static void testUseGetUserDir() {
		System.out.println("----- getUserDir -----");
		
		System.out.println(SystemUtils.getUserDir());
		System.out.println();
	}
	
	public static void testUseGetUserHome() {
		System.out.println("----- getUserHome -----");
		
		System.out.println(SystemUtils.getUserHome());
		System.out.println();
	}	
	
	
	public static void testUseGetUserName() {
		System.out.println("----- getUserName -----");
		
		System.out.println(SystemUtils.getUserName());
		System.out.println();
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
}
