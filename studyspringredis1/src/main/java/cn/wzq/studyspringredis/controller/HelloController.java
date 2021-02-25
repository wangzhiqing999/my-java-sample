package cn.wzq.studyspringredis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studyspringredis.entity.User;
import cn.wzq.studyspringredis.service.UserService;



@RestController
public class HelloController {

	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
		
    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    @GetMapping("/testSet")
    public String testSet(String key, String value) {    	
    	stringRedisTemplate.opsForValue().set(key, value);    	
    	return "ok";
    }
    
    @GetMapping("/testGet")
    public String testGet(String key) {    
    	String k1 = stringRedisTemplate.opsForValue().get(key);    	
    	return k1;
    }
    
    
    
    
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/getUser")
    public User getUser(Long id) {
    	User user1 = userService.get(id);
    	return user1;
    }
    
    
    @GetMapping("/saveOrUpdate")
    public User saveOrUpdate(Long id, String name) {
    	User user2 = new User(id, name);
    	return userService.saveOrUpdate(user2);
    }
    
    
    @GetMapping("/deleteUser")
    public String delete(Long id) {
    	userService.delete(id);
    	return "ok";
    }
	
	
}
