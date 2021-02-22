package cn.wzq.studyspringsecurity.bean;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class UserInfo {

    public enum Role{
        admin,normal
    }


    @Id @GeneratedValue
    private long uid;//主键.

    private String username;//用户名.
    private String password;//密码.

    @Enumerated(EnumType.STRING)
    private Role role;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


} 