package cn.wzq.studyspringsecurity.config;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cn.wzq.studyspringsecurity.service.PermissionService;



public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    @Autowired
    private PermissionService permissionService;

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法。
     * object-->FilterInvocation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        System.out.println("MyFilterInvocationSecurityMetadataSource.getAttributes()");

        Map<String, Collection<ConfigAttribute>> map = permissionService.getPermissionMap();
        FilterInvocation filterInvocation = (FilterInvocation) object;
        
        
        System.out.println(filterInvocation.getFullRequestUrl());

        
        if (isMatcherAllowedRequest(filterInvocation)) return null ; //return null 表示允许访问，不做拦截

        
        
        HttpServletRequest request = filterInvocation.getHttpRequest();
        String resUrl;
        
        
        // URL规则匹配.
        AntPathRequestMatcher matcher;
        
        // 这里是 遍历  配置在数据库中， 所有的 URL路径， 与 当前访问的路径，进行匹配。 
        for(Iterator<String> it  = map.keySet().iterator();it.hasNext();) {
            resUrl = it.next();
                        
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
            	
            	// 这里是  检测到， 当前的请求， 与 数据库中存储的路径， 发生匹配的情况。
            	
            	System.out.println(resUrl);
                System.out.println(map.get(resUrl));
                
                return map.get(resUrl);
            }
        }
        
        
        
        System.out.println("Permission URL Not Found, Use Default Setting...");
        
        // 如果能够执行到这里， 说明。
        // 这个路径， 不是默认允许请求的路径 （这个定义在下面的  allowedRequest() 方法里面  ）        
        // 这个路径，没有定义在 权限列表里面。
        

        // 方式一：没有匹配到,直接是白名单了.不登录也是可以访问的。
        // 也就是奔放一点， 新加的页面， 可以随意访问了。
        // return null;

        
        // 方式二：配有匹配到，需要指定相应的角色：
        // 也就是严格一点， 新加的页面， 需要管理员才能访问
        return SecurityConfig.createList("ROLE_admin");
    }




    /**
     * 判断当前请求是否在允许请求的范围内
     * @param fi 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(FilterInvocation fi){
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(requestMatcher -> requestMatcher.matches(fi.getHttpRequest()))
                .toArray().length > 0;
    }

    /**
     * @return 定义允许请求的列表
     */
    private List<String> allowedRequest(){
        return Arrays.asList("/login","/",  "/index");
    }

    
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}