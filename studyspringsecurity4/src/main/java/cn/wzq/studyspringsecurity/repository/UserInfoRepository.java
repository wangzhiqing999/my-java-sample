package cn.wzq.studyspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.wzq.studyspringsecurity.bean.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    public UserInfo findByUsername(String username);

}