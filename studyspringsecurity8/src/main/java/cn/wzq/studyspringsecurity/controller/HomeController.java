package cn.wzq.studyspringsecurity.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	
	
	@GetMapping({"","/","/index"})
	public String index(Model model) {
		
		Object pricipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//未登录的情况下，返回的是一个字符串：anonymousUser
		//登录的情况下，返回的是在CustomUserDetailService的loadUserByUsername的方法中返回的User对象。
		if("anonymousUser".equals(pricipal)) {
			model.addAttribute("name", "anonymous user");
		}else {
			User user = (User) pricipal;
			model.addAttribute("name",user.getUsername());
		}
		
		return "/index";
	}
	
	
	
	
	
	
	// @PreAuthorize("hasRole('admin')")
	@GetMapping("/helloAdmin")
	public String helloAdmin() {
		return "/helloAdmin";
	}
	
	
	
	
	// @PreAuthorize("hasAnyRole('admin','normal')")
	@GetMapping("/helloUser")
	public String helloUser() {
		return "/helloUser";
	}
	
	
	
	// 这个是没有定义在 权限表中的路径。
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "/helloWorld";
	}
	
	
}
