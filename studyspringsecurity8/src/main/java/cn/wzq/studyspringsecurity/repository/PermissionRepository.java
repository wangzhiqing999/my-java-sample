package cn.wzq.studyspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.wzq.studyspringsecurity.bean.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}