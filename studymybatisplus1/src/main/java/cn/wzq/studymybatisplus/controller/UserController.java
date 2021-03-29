package cn.wzq.studymybatisplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studymybatisplus.entity.User;
import cn.wzq.studymybatisplus.mapper.UserMapper;

@RestController
@RequestMapping("/users")
public class UserController {

	
	
    @Autowired
    private UserMapper userMapper;
	
	
	
    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    
    
    
    @GetMapping("/list")
    public List<User> list() {
        List<User> userList = userMapper.selectList(null);
        return userList;
    }
    

    @GetMapping("/get")
    public User get(long id) {
    	User user = userMapper.selectById(id);
    	return user;
    }
    
    
	
}
