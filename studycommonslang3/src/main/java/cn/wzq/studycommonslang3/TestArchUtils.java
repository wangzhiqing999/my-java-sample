package cn.wzq.studycommonslang3;

import org.apache.commons.lang3.ArchUtils;
import org.apache.commons.lang3.arch.Processor;

public class TestArchUtils {

	public static void main(String[] args) {


		testUseGetProcessor();

	}

	
	
	
	
	
	
	
	
	public static void testUseGetProcessor() {
		System.out.println("----- getProcessor -----");
		
		
		// 获取处理器的信息.
		Processor p = ArchUtils.getProcessor();
		
		
		System.out.println("Arch:" + p.getArch());
		System.out.println("Type:" + p.getType());
		
		System.out.println("is32Bit:" + p.is32Bit());
		System.out.println("is64Bit:" + p.is64Bit());
		System.out.println("isIA64:" + p.isIA64());
		System.out.println("isPPC:" + p.isPPC());
		System.out.println("isX86:" + p.isX86());
		
		
		System.out.println();
	}
	
	
}
