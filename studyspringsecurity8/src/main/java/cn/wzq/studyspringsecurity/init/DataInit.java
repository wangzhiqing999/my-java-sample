package cn.wzq.studyspringsecurity.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import cn.wzq.studyspringsecurity.bean.UserInfo;
import cn.wzq.studyspringsecurity.bean.Permission;
import cn.wzq.studyspringsecurity.bean.Role;
import cn.wzq.studyspringsecurity.repository.PermissionRepository;
import cn.wzq.studyspringsecurity.repository.RoleRepository;
import cn.wzq.studyspringsecurity.repository.UserInfoRepository;


@Service
public class DataInit {

	
    @Autowired 
    private UserInfoRepository userInfoRepository;
    
    @Autowired 
    private RoleRepository roleRepository;

    @Autowired 
    private PermissionRepository permissionRepository;
    
    
    
    @Autowired 
    private PasswordEncoder passwordEncoder;
    

    

    @PostConstruct
    public void dataInit() {
    	
    	
    	// 初始化角色相关数据.
        Role adminRole= new Role("admin","管理员");
        Role normalRole = new Role("normal","普通用户");
        roleRepository.save(adminRole);
        roleRepository.save(normalRole);
    	
    	
        
        
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(normalRole);
        
        
        List<Role> rolesAdmin = new ArrayList<>();
        rolesAdmin.add(adminRole);
        
        
        List<Role> rolesNormal = new ArrayList<>();
        rolesNormal.add(normalRole);

        
        
        
        //初始化权限.
		Permission permission =  new Permission();
		permission.setName("普通用户的url");
		permission.setDescription("允许普通用户和管理员访问");
		permission.setUrl("/helloUser");
		permission.setRoles(roles);
		permissionRepository.save(permission);
		
		
		Permission permission2 =  new Permission();
		permission2.setName("管理员的url");
		permission2.setDescription("允许管理员访问");
		permission2.setUrl("/helloAdmin");
		permission2.setRoles(rolesAdmin);
		permissionRepository.save(permission2);
        
        
        
        
    	
        // 初始化用户数据.

        UserInfo admin = new UserInfo();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRoles(roles);        
        userInfoRepository.save(admin);


        
        
        UserInfo user = new UserInfo();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123"));        
        user.setRoles(rolesNormal);                
        userInfoRepository.save(user);
    }
	
	
}
