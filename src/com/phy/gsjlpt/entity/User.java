package com.phy.gsjlpt.entity;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  User   
 * @Description:TODO(用户实体类)
 * @author: 张晓峰 
 * @date:   2018年1月3日 下午9:46:47     
 */  
@Entity
@Component
public class User{
	private Integer uid;
	private String username;
	private String password;
	private String dept;
	private String realname;
	private String role;
	 private String salt;
	 private Boolean locked = Boolean.FALSE;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
    public String getCredentialsSalt() {
        return username + salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
    }
