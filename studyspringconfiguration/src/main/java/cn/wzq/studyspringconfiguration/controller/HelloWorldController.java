package cn.wzq.studyspringconfiguration.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studyspringconfiguration.data.TestConfigData;
import cn.wzq.studyspringconfiguration.service.EmailService;



@RestController
public class HelloWorldController {

	
    @Autowired
    private TestConfigData configData;	
	
	
    @Autowired
	private EmailService emailService;
    
    
    
	
    @GetMapping("/hello")
	public String index() {
    	return "Spring Boot Hello World";
	}
	
    
    
    
    @GetMapping("/configName")
    public String configName() {
    	return configData.getWebName();
    }
        
    @GetMapping("/configTitle")
    public String configTitle() {
    	return configData.getWebTitle();
    }
    
    @GetMapping("/configSummary")
    public String configSummary() {
    	
    	// 执行结果去控制台看 System.out.println 的结果。
    	emailService.send();
    	
    	return configData.getWebSummary();
    }
    

	
}
