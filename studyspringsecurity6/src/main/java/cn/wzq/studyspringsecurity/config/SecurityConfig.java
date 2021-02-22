package cn.wzq.studyspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.wzq.studyspringsecurity.filter.*;


@Configuration
@EnableWebSecurity //启用Spring Security.
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
        .antMatchers("/login").permitAll()// 设置所有人都可以访问登录页面
        .antMatchers("/","/index").permitAll()// 设置所有人都可以访问首页
        .anyRequest().authenticated()  // 任何请求,登录后可以访问
        .and()
        .formLogin().loginPage("/login")
        ;
        
       

        
        // 这里是简单测试 Filter 的执行顺序。
        // 也就是3个方法的追加的顺序：
        // addFilterBefore / addFilterAt / addFilterAfter
        http.addFilterBefore(new Test1Filter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new Test2Filter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new Test3Filter(), UsernamePasswordAuthenticationFilter.class);
        
	}
	
	
	
	@Bean //注入PsswordEncoder
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
