package cn.wzq.studyspringnacos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {

	
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@GetMapping("/logger")
	public void logger() {
	    logger.debug("[logger][测试一下]");
	}
	
	
	@GetMapping("/info")
	public void info() {
	    logger.info("[info][测试一下]");
	}
	
}
