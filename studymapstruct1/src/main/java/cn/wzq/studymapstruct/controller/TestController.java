package cn.wzq.studymapstruct.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studymapstruct.enums.UserTypeEnum;
import cn.wzq.studymapstruct.model.dto.UserVO1;
import cn.wzq.studymapstruct.model.dto.UserVO2;
import cn.wzq.studymapstruct.model.dto.UserVO3;
import cn.wzq.studymapstruct.model.dto.UserVO4;
import cn.wzq.studymapstruct.model.dto.UserVO5;
import cn.wzq.studymapstruct.model.po.User;
import cn.wzq.studymapstruct.model.po.UserEnum;
import cn.wzq.studymapstruct.service.UserCovertBasic;

@RestController
public class TestController {


	
	
	
	// 获取测试数据.
	@GetMapping("source")
	public List<User> getTestUserList() {
		// 构建测试需要用到的数据
        List<User> userList = new ArrayList<>();
        User user = User.builder().id(1).name("张三")
                .createTime("2020-04-01 11:05:07").updateTime(LocalDateTime.now())
                .build();
        userList.add(user);
        User user2 = User.builder().id(2).name("李四")
                .createTime("2020-04-01 11:05:07").updateTime(LocalDateTime.now())
                .build();
        userList.add(user2);
        
        return userList;
	}
	
	
	// 获取测试数据.
	@GetMapping("sourceenum")
	public List<UserEnum> getTestUserEnumList() {
		// 构建测试需要用到的数据
        List<UserEnum> userList = new ArrayList<>();
        UserEnum user = UserEnum.builder().id(1).name("张三")
        		.userTypeEnum(UserTypeEnum.Java)
                .build();
        userList.add(user);
        UserEnum user2 = UserEnum.builder().id(2).name("李四")
        		.userTypeEnum(UserTypeEnum.LINUX)
                .build();
        userList.add(user2);
        
        return userList;
	}
	
	

	
	
	
	
    @GetMapping("convert01")
    public List<UserVO1> convertEntity01() {
        
    	List<User> userList = this.getTestUserList(); 

        List<UserVO1> resultList = new ArrayList<UserVO1>();
        
        for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);
			
			UserVO1 userVO1 = UserCovertBasic.INSTANCE.toConvertVO1(user);
			
			resultList.add(userVO1);
        }
        
        return resultList;
    }
    
	@GetMapping("convert01b")
    public List<UserVO1> convertEntity01ByBeanUtils() {
	
		List<User> userList = this.getTestUserList(); 
				
		List<UserVO1> resultList = new ArrayList<UserVO1>();
		
		for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);			
			UserVO1 userVO1 = new UserVO1();
	        BeanUtils.copyProperties(user, userVO1);
	        
	        resultList.add(userVO1);
		}
		
		return resultList;
	}  

    
    
    @GetMapping("convert02")
    public List<UserVO2> convertEntity02() {
        
    	List<User> userList = this.getTestUserList(); 

        List<UserVO2> resultList = new ArrayList<UserVO2>();
        
        for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);
			
			UserVO2 userVO2 = UserCovertBasic.INSTANCE.toConvertVO2(user);
			
			resultList.add(userVO2);
        }
        
        return resultList;
    }
    
    
	@GetMapping("convert02b")
    public List<UserVO2> convertEntity02ByBeanUtils() {
	
		List<User> userList = this.getTestUserList(); 
				
		List<UserVO2> resultList = new ArrayList<UserVO2>();
		
		for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);			
			UserVO2 userVO2 = new UserVO2();
	        BeanUtils.copyProperties(user, userVO2);
	        
	        resultList.add(userVO2);
		}
		
		return resultList;
	}    
    
    
  
	
	
	
    @GetMapping("convert03")
    public List<UserVO3> convertEntity03() {
        
    	List<User> userList = this.getTestUserList(); 

        List<UserVO3> resultList = new ArrayList<UserVO3>();
        
        for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);
			
			UserVO3 userVO3 = UserCovertBasic.INSTANCE.toConvertVO3(user);
			
			resultList.add(userVO3);
        }
        
        return resultList;
    }	
    
	@GetMapping("convert03b")
    public List<UserVO3> convertEntity03ByBeanUtils() {
	
		List<User> userList = this.getTestUserList(); 
				
		List<UserVO3> resultList = new ArrayList<UserVO3>();
		
		for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);			
			UserVO3 userVO3 = new UserVO3();
	        BeanUtils.copyProperties(user, userVO3);
	        
	        resultList.add(userVO3);
		}
		
		return resultList;
	}    
        
    
	
	
	
    
    @GetMapping("convert04")
    public List<UserVO4> convertEntity04() {
        
    	List<User> userList = this.getTestUserList(); 

        List<UserVO4> resultList = new ArrayList<UserVO4>();
        
        for(int i = 0; i < userList.size(); i ++) {			
			User user = userList.get(i);
			
			UserVO4 userVO4 = UserCovertBasic.INSTANCE.toConvertVO4(user);
			
			resultList.add(userVO4);
        }
        
        return resultList;
    }	
	    
    
    
    

    
    @GetMapping("convert05")
    public List<UserVO5> convertEntity05() {
        
    	List<UserEnum> userList = this.getTestUserEnumList(); 

        List<UserVO5> resultList = new ArrayList<UserVO5>();
        
        for(int i = 0; i < userList.size(); i ++) {			
        	UserEnum user = userList.get(i);
			
        	UserVO5 userVO5 = UserCovertBasic.INSTANCE.toConvertVO5(user);
			
			resultList.add(userVO5);
        }
        
        return resultList;
    }	
    

	
}
