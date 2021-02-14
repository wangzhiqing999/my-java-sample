package cn.wzq.springsample.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.wzq.springsample.pojo.User;
import cn.wzq.springsample.service.UserService;

@RestController
@Api(tags = "User")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
    @GetMapping("/getuser")
    @ApiOperation("getuser")
	public User getUser(Integer id) {
    	return userService.selectByPrimaryKey(id);
	}
	
	
    
    @GetMapping("/getalluser")
    @ApiOperation("getalluser")
	public List<User> getAllUser() {
    	return userService.selectAll();
	}

}
