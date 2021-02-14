package cn.wzq.springsample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wzq.springsample.mapper.UserMapper;
import cn.wzq.springsample.pojo.User;
import cn.wzq.springsample.pojo.UserExample;
import cn.wzq.springsample.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	
	@Autowired
    private UserMapper userMapper;
	

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> selectAll() {
		UserExample example = new UserExample();
		List<User> list = userMapper.selectByExample(example);
		return list;
	}

}
