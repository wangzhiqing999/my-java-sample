package cn.wzq.studyspringjpa.controller;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wzq.studyspringjpa.model.dto.UserDTO;
import cn.wzq.studyspringjpa.model.po.Person;
import cn.wzq.studyspringjpa.repository.PersonRepository;



@RestController
@RequestMapping("/user")
public class UserController {

	
	
    @Autowired
    private PersonRepository personRepository;
	
	
    
    // 根据 id 查找
    @GetMapping("/findById")
    public Optional<Person> findById(Long id)
    {
        return personRepository.findById(id);
    }
    
	// 根据 名称 查找
    @GetMapping("/findByName")
    public Optional<Person> findByName(String name)
    {
        return personRepository.findByName(name);
    }
	
    
    
    
    
    
    // 自定义 SQL 语句
    // Person 部分属性查询，避免 select *操作
    @GetMapping("/findPersonNameById")
    public String findPersonNameById(Long id) {
    	return personRepository.findPersonNameById(id);
    }
    
    
    // 自定义 SQL 语句
    // 根据 id 更新Person name：
    @GetMapping("/updatePersonNameById")
    public void updatePersonNameById(Long id, String name) {
    	personRepository.updatePersonNameById(name, id);
    }
    
    
    
    // 测试 连表查询
    @GetMapping("/getUserInformation")
    public Optional<UserDTO> getUserInformation(Long id){
    	return personRepository.getUserInformation(id);
    }
    
    
    // 测试翻页.
    @GetMapping("/getUserInformationList")
    public Page<UserDTO> getUserInformationList(int pageIndex, int pageSize) {
    	PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "age");
    	Page<UserDTO> userInformationList = personRepository.getUserInformationList(pageRequest);
    	return userInformationList;
    }
    
    
	
}
