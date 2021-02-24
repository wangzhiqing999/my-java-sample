package cn.wzq.studyspringlogback.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
public class HelloWorldController {

	
	// 日志.
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	
	
	
    @GetMapping("/hello")
	public String index() {
    	return "Spring Boot Hello World";
	}
	
    
    
    @GetMapping("/testDebug")
	public String testDebug() {
    	if(logger.isDebugEnabled()) {    		
    		logger.debug("/testDebug Start...");    		
    	}    	
    	return "Spring Boot Test logger.debug";
	}
	
    

    
    @GetMapping("/testInfo")
	public String testInfo() {
    	if(logger.isInfoEnabled()) {    		
    		logger.info("/testInfo Start...");    		
    	}    	
    	return "Spring Boot Test logger.info";
	}
	
    
    
    
    @GetMapping("/testWarn")
	public String testWarn() {
    	if(logger.isWarnEnabled()) {    		
    		logger.warn("/testWarn Start...");    		
    	}    	
    	return "Spring Boot Test logger.warn";
	}

	
    
    @GetMapping("/testError")
	public String testError() {
    	if(logger.isErrorEnabled()) {    		
    		logger.error("/testError Start...");    		
    	}    	
    	return "Spring Boot Test logger.error";
	}

    
	
}
