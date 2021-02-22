package cn.wzq.studyspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/demo/echo").permitAll() // 所有用户可访问
		        // 任何请求，访问的用户都需要经过认证
		        .anyRequest().authenticated()
		    .and()
		    // 设置 Form 表单登陆
		    .formLogin()
		        .permitAll() // 所有用户可访问
		    .and()
		    // 配置退出相关
		    .logout()
		        .permitAll(); // 所有用户可访问

	}
	
	
	@Bean //注入PsswordEncoder
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
