package cn.wzq.studyspringjob.controller;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studyspringjob.service.DemoAsyncService;
import cn.wzq.studyspringjob.service.DemoService;



@RestController
public class HelloWorldController {

	
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	
    @Autowired
	private DemoService demoService;	
	
    
    
    @Autowired
	private DemoAsyncService demoAsyncService;
    
    
	
	
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    
    
    @GetMapping("/demo")    
    public String demo() {
    	
    	long now = System.currentTimeMillis();
        logger.info("[demo][开始执行]");

        demoService.execute01();
        demoService.execute02();

        logger.info("[demo][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
      	
    	
    	return "demo";
    }
    
    
    
    @GetMapping("/demoasync")    
    public String demoasync() {
    	
    	long now = System.currentTimeMillis();
        logger.info("[demoasync][开始执行]");

        demoAsyncService.execute01Async();
        demoAsyncService.execute02Async();

        logger.info("[demoasync][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
      	
    	
    	return "demoasync";
    }
        
    
    
    @GetMapping("/demoAsyncWithFuture")    
    public String demoAsyncWithFuture() throws InterruptedException, ExecutionException {
    	
    	long now = System.currentTimeMillis();
        logger.info("[demoAsyncWithFuture][开始执行]");

        // <1> 执行任务
        Future<Integer> execute01Result = demoAsyncService.execute01AsyncWithFuture();
        Future<Integer> execute02Result = demoAsyncService.execute02AsyncWithFuture();
        // <2> 阻塞等待结果
        execute01Result.get();
        execute02Result.get();

        logger.info("[demoAsyncWithFuture][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
        
        return "demoAsyncWithFuture";
    }

    
    
    

}