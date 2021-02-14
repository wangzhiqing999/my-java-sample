package cn.wzq.mybatissample.mapper;

import java.util.List;

import cn.wzq.mybatissample.model.User;;

public interface UserMapper {

	
	User getUserById(Integer id);
	


    Integer addUser(User user);


    Integer deleteUserById(Integer id);

    Integer updateUser(User user);

    
	User getUserByName(String username);
	
    
    List<User> getAllUser();
	
}
