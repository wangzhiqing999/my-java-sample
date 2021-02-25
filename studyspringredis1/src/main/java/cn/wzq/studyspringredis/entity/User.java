package cn.wzq.studyspringredis.entity;

import java.io.Serializable;


public class User implements Serializable {
	
	
    private static final long serialVersionUID = 2892248514883451461L;
    
    /**
     * 主键id
     */
    private Long id;
    
    /**
     * 姓名
     */
    private String name;

    
    
    public User() {
    	
    }

	public User(long l, String s) {
		this.id = l;
		this.name = s;
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
    
    
}
