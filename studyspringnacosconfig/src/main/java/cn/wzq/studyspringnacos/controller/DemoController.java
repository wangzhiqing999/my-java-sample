package cn.wzq.studyspringnacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studyspringnacos.pojo.OrderProperties;

@RestController
public class DemoController {

	
    @Autowired
    private OrderProperties configData;	
	
	
    @GetMapping("/demo")
    public OrderProperties demo() {
        return configData;
    }
	
}
