package cn.wzq.studyspringconfiguration.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 用于测试的 配置类。  数据被存储在 外部的 配置文件中。  
 */
@Component
@ConfigurationProperties(prefix="com.wzq.configdata")
public class TestConfigData {

	
	
	// 网站名称.
	private String webName;

   
	// 网站标题.
	private String webTitle;
	
	
	// 网站版本. (测试 使用随机数)
	private int webVersion; 
	
	
	// 网站摘要. (测试 参数间的引用)
	private String webSummary;
	
	
	
	
	public String getWebName() {
		return webName;
	}


	public void setWebName(String webName) {
		this.webName = webName;
	}


	public String getWebTitle() {
		return webTitle;
	}


	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}


	public int getWebVersion() {
		return webVersion;
	}


	public void setWebVersion(int webVersion) {
		this.webVersion = webVersion;
	}


	public String getWebSummary() {
		return webSummary;
	}


	public void setWebSummary(String webSummary) {
		this.webSummary = webSummary;
	}

	
	
	
}
