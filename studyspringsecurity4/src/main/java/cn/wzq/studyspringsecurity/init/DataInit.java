package cn.wzq.studyspringsecurity.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.wzq.studyspringsecurity.bean.UserInfo;
import cn.wzq.studyspringsecurity.bean.UserInfo.Role;
import cn.wzq.studyspringsecurity.repository.UserInfoRepository;


@Service
public class DataInit {

	
    @Autowired UserInfoRepository userInfoRepository;


    @PostConstruct
    public void dataInit() {

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword("123");
        admin.setRole(Role.admin);
        userInfoRepository.save(admin);


        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword("123");
        user.setRole(Role.normal);
        userInfoRepository.save(user);
    }
	
	
}
