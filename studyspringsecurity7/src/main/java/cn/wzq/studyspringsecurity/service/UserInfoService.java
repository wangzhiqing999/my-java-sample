package cn.wzq.studyspringsecurity.service;

import cn.wzq.studyspringsecurity.bean.UserInfo;


public interface UserInfoService {

    public UserInfo findByUsername(String username);	
	
}
