package cn.wzq.studyspringsecurity.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import cn.wzq.studyspringsecurity.bean.Permission;
import cn.wzq.studyspringsecurity.bean.Role;
import cn.wzq.studyspringsecurity.repository.PermissionRepository;
import cn.wzq.studyspringsecurity.service.PermissionService;



@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	//key = url, value = 权限集合.
	private Map<String,Collection<ConfigAttribute>> permissionMap = null;
	
	
	@PostConstruct
	public void initPermissions() {
		
		/*
		 * 从数据库中获取到所有权限信息，然后进行遍历，储存到permissionMap集合中。
		 * 
		 */
		permissionMap = new HashMap<>();
		List<Permission> permissions = permissionRepository.findAll();
		for(Permission p:permissions) {
			Collection<ConfigAttribute> collection = new ArrayList<ConfigAttribute>();
			for(Role role:p.getRoles()) {
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_"+role.getName());
				collection.add(configAttribute);
			}
			permissionMap.put(p.getUrl(), collection);
		}
		System.out.println(permissionMap);
	}
	
	
	
	@Override
	public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
		if(permissionMap == null || permissionMap.size() == 0) initPermissions(); 
		return permissionMap;
	}
	
}
