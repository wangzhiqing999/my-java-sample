package cn.wzq.studycommonsio;


import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


public class StudyFileUtils {

	public static void main(String[] args) throws Exception  {
		all();

	}

	
	
	
	
	
	
	
	
		
	public static void all() throws Exception {
		
		
		testUseListFiles();
		
		
		 testUseMkdir();
	}

	
	
	
	private static String TEMP_FILE_PATH = "D:\\tmp";
	
	
	
	
	
	
	public static void testUseListFiles() {
		
		System.out.println("----- listFiles  -----");
		
		File directory = new File(TEMP_FILE_PATH);
		
		// 列出目录下全部。
		Collection<File> files =  FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, null);		
		for(File element: files)
		{
		    System.out.println(element);
		}			
		System.out.println();

		
		// 只列出 .txt
		files =  FileUtils.listFiles(directory, FileFilterUtils.suffixFileFilter("txt"), null);		
		for(File element: files)
		{
		    System.out.println(element);
		}
		System.out.println();
	}
	
	
	
	
	
	
	
	public static void testUseMkdir()  throws Exception {
		System.out.println("----- forceMkdir -----");
		
		String tmpPath = TEMP_FILE_PATH + "\\abc\\123\\test_java_io";		
		File directory = new File(tmpPath);
		
		String tmpFile = tmpPath + "\\test.txt";
		File file = new File(tmpFile);
		
		
		// forceMkdir 创建陌路
		// 注意：创建的时候，是连带的， 依次判断   abc, 123, test_java_io， 如果不存在，则创建的操作。 
		FileUtils.forceMkdir(directory);
		System.out.println("directory.exists() = " + directory.exists());
		
		
		
		System.out.println("----- isEmptyDirectory -----");
		// isEmptyDirectory 是否是空目录.
		System.out.println("isEmptyDirectory = " + FileUtils.isEmptyDirectory(directory));
		
		
		
		System.out.println("----- writeStringToFile -----");
		
		// 写入文本.
		FileUtils.writeStringToFile(file, "1234567890\r\nABCDEFGHIJKLMN", "utf8");
		System.out.println(file);
		
		System.out.println("isEmptyDirectory = " + FileUtils.isEmptyDirectory(directory));
		
		
		
		
		System.out.println("----- readLines -----");
		
		// 读取文本.
		List<String> lines = FileUtils.readLines(file, "utf8");
		for(String line: lines)
		{
		    System.out.println(line);
		}
		
		
		
		System.out.println("----- sizeOf -----");
		
		// 文件大小
		System.out.println(FileUtils.sizeOf(file));
		
		
		
		System.out.println("----- copyFile -----");
		
		// 复制文件.
		File copyFile = new File(tmpPath + "\\test_copy.txt");
		FileUtils.copyFile(file, copyFile);
		
		Collection<File> files =  FileUtils.listFiles(directory, FileFilterUtils.suffixFileFilter("txt"), null);		
		for(File element: files)
		{
		    System.out.println(element);
		}
		
		
		
		System.out.println("----- contentEquals -----");
		
		// 比较两个文件内容是否相同
		System.out.println(FileUtils.contentEquals(file, copyFile));
		
		
		
		
		
		System.out.println("----- delete -----");
		
		// 删除文件.
		FileUtils.delete(copyFile);
		
		files =  FileUtils.listFiles(directory, FileFilterUtils.suffixFileFilter("txt"), null);		
		for(File element: files)
		{
		    System.out.println(element);
		}
		
		System.out.println("isEmptyDirectory = " + FileUtils.isEmptyDirectory(directory));
		
		
		
		System.out.println("----- cleanDirectory -----");
		
		// 清空目录， 但是不删除这个目录.
		FileUtils.cleanDirectory(directory);
		
		System.out.println("isEmptyDirectory = " + FileUtils.isEmptyDirectory(directory));
		
		
		
		
		System.out.println("----- deleteDirectory -----");
		
		// 删除目录.
		FileUtils.deleteDirectory(directory);
		
		
		
		System.out.println("directory.exists() = " + directory.exists());
		
		
		System.out.println();
	}	
	
	
	
	
	
	
	
	public static void testUse() {
		System.out.println("-----  -----");
		
		System.out.println();
		System.out.println();
	}
	
	
}
