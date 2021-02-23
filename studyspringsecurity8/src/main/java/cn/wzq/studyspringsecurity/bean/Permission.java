package cn.wzq.studyspringsecurity.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Permission {

	
	
	@Id @GeneratedValue
	private long id;
	
	private String name;//权限名称.
	private String description;//描述.
	private String url;//地址.
	private long pid;//父id.
	
	//角色和权限的关系  多对多.
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="role_permission",joinColumns= {@JoinColumn(name="permission_id")},
	inverseJoinColumns= {@JoinColumn(name="role_id")})
	private List<Role> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
