package cn.wzq.springsample.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(tags = "HelloWorld")
@RequestMapping("/hello")
public class HelloWorldController {

	
    @GetMapping("/hello")
    @ApiOperation("hello")
	public String index() {
		return "Spring Boot Hello World";
	}
	
	
}
