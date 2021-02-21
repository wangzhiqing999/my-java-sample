package cn.wzq.studyspringsecurity.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.wzq.studyspringsecurity.bean.UserInfo;
import cn.wzq.studyspringsecurity.repository.UserInfoRepository;
import cn.wzq.studyspringsecurity.service.UserInfoService;


@Service
public class UserInfoServiceImpl  implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }
	
}
