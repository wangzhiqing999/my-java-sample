package cn.wzq.studycommonsio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;





//【copy】【最常用】拷贝流，支持多种数据间的拷贝
//【copyLarge】适合拷贝较大的数据流，比如2G以上
//【read】从一个流中读取内容
//【readFully】读取指定长度的流，如果读取的长度不够，就会抛出异常
//【readLines】【常用】readLines方法可以从流中读取内容，并转换为String的list
//【write】【常用】把数据写入到输出流中
//【writeLines】【常用】把string的List写入到输出流中
//【close】 关闭URL连接
//【closeQuietly】【常用】忽略nulls和异常，关闭某个流
//【lineIterator】读取流，返回迭代器
//【toBufferedInputStream】把流的全部内容放在另一个流中
//【toBufferedReader】返回输入流
//【toByteArray】返回字节数组
//【toCharArray】返回字符数组
//【toInputStream】返回输入流
//【toString】返回字符串
//【contentEquals】比较两个流是否相同
//【contentEqualsIgnoreEOL】比较两个流，忽略换行符
//【skip】跳过指定长度的流，
//【skipFully】类似skip，只是如果忽略的长度大于现有的长度，就会抛出异常

public class TestIOUtils {

	
	
	public static void main(String[] args) throws Exception  {
		all();

	}

	
	
	
	public static void all() throws Exception {
		
		
		testUseToString();
		
		
		testUseToInputStream();
		testUseToInputStream2();
	}
	
	
	
	
	
	
	
	
	
	
	public static void testUseToString() throws Exception  {
		System.out.println("----- toString -----");
		
		
		// 注意：  读取的流的  文本不大的情况下，  使用 toString 没啥问题。
		// 如果是读取 几百兆的文件，  也采取这种操作的话， 容易导致 内存不足而翻车。
		
		InputStream in = new URL( "https://www.baidu.com/" ).openStream();
		 try {
		    System.out.println(IOUtils.toString(in, "utf-8"));
		 } finally {
		    IOUtils.closeQuietly(in);
		 }
		
		
		System.out.println();
	}
	
	
	
	
	
	
	public static void testUseToInputStream() throws Exception  {
		System.out.println("----- toInputStream -----");
		
		
		InputStream is = IOUtils.toInputStream("This is a String","utf-8");		
		OutputStream os = new FileOutputStream("D:\\tmp\\java_test.txt");
		
		
		System.out.println("----- copy -----");
		int bytes=IOUtils.copy(is,os);
		
		
		System.out.println("File Written with "+bytes+" bytes");
		
		
		
		System.out.println("----- closeQuietly -----");
		
		// 【closeQuietly】【常用】忽略nulls和异常，关闭某个流
		IOUtils.closeQuietly(os);
		
		

		System.out.println();
	}
	
	
	
	
	
	
	
	public static void testUseToInputStream2 () throws Exception  {
		System.out.println("----- toInputStream -----");
		
		
		String testFileName = "D:\\tmp\\java_test2.txt";
		
		// 用Java7引入的 try-with-resources方式来关闭流
		
		try (InputStream is = IOUtils.toInputStream("This is a String \r\n12345678 \r\nABCDEFG \r\n", "utf-8");
		     OutputStream os = new FileOutputStream(testFileName)) {

			
			// 【copy】 【最常用】拷贝流，支持多种数据间的拷贝
			// 【copyLarge】适合拷贝较大的数据流，比如2G以上
		    int bytes = IOUtils.copy(is, os);
		    System.out.println("File Written with " + bytes + " bytes");
		     
		     
		     // 【write】【常用】把数据写入到输出流中
		    IOUtils.write("Test IOUtils.write!", os, "utf-8");
		     
		}

		System.out.println();
		
		
		
		System.out.println("----- toString -----");		
		try (FileReader fileReader = new FileReader(testFileName)) {
			System.out.println(IOUtils.toString(fileReader));
		}
		System.out.println();
	
		
		
		System.out.println("----- read -----");
		// 【read】从一个流中读取内容
		
	    try(FileInputStream fin=new FileInputStream(testFileName)){
	    	
	        byte[] buf= new byte[128];
	        
	        int len=IOUtils.read(fin,buf);
	        
	        System.out.println("The Length of Input Stream:"+len);
	        
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	    System.out.println();
	    
	    
	    
	    
	    
	    System.out.println("----- readLines -----");
	    
	    // 【readLines】【常用】readLines方法可以从流中读取内容，并转换为String的list
	    try(FileInputStream fin=new FileInputStream(testFileName)){
	    	List<String> ls = IOUtils.readLines(fin, "utf-8");
	        for (int i = 0; i < ls.size(); i++) {
	            System.out.println(ls.get(i));
	        }
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	    System.out.println();
	    
	    
	    System.out.println("----- lineIterator -----");
	    try(FileInputStream fin=new FileInputStream(testFileName)){
	        LineIterator lt=IOUtils.lineIterator(fin,"utf-8");
	        while (lt.hasNext()){
	            String line=lt.nextLine();
	            System.out.println(line);
	        }
	    }catch (IOException e){
	        e.printStackTrace();
	    }

	    
	    System.out.println();
	}
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
	

}
