package cn.wzq.studyspringsecurity.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import cn.wzq.studyspringsecurity.bean.UserInfo;
import cn.wzq.studyspringsecurity.bean.Role;
import cn.wzq.studyspringsecurity.repository.RoleRepository;
import cn.wzq.studyspringsecurity.repository.UserInfoRepository;


@Service
public class DataInit {

	
    @Autowired 
    private UserInfoRepository userInfoRepository;
    
    @Autowired 
    private RoleRepository roleRepository;


    @Autowired 
    private PasswordEncoder passwordEncoder;
    

    

    @PostConstruct
    public void dataInit() {
    	
    	
    	// 初始化角色相关数据.
        Role adminRole= new Role("admin","管理员");
        Role normalRole = new Role("normal","普通用户");
        roleRepository.save(adminRole);
        roleRepository.save(normalRole);
    	
    	
    	
        // 初始化用户数据.

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(normalRole);
        admin.setRoles(roles);
        
        userInfoRepository.save(admin);


        
        
        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123"));
        
        roles = new ArrayList<>();
        roles.add(normalRole);
        user.setRoles(roles);
                
        userInfoRepository.save(user);
    }
	
	
}
