package cn.wzq.springsample.service;


import java.util.List;

import cn.wzq.springsample.pojo.User;





public interface UserService {
	
	int insert(User record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(User record);
	
	User selectByPrimaryKey(Integer id);
	
	
	List<User> selectAll();
	
}
