package cn.wzq.studyspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.wzq.studyspringsecurity.bean.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {

}
