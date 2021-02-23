package cn.wzq.studyspringsecurity.service;



import java.util.Collection;
import java.util.Map;


import org.springframework.security.access.ConfigAttribute;


public interface PermissionService {

	public Map<String, Collection<ConfigAttribute>> getPermissionMap();	
	
}
