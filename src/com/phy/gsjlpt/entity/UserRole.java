package com.phy.gsjlpt.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  UserRole   
 * @Description:TODO(用户-角色关系表)
 * @author: 张晓峰 
 * @date:   2018年1月29日 上午11:59:12     
 */  
@Entity
@Component
public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (roleId != null ? !roleId.equals(userRole.roleId) : userRole.roleId != null) return false;
        if (userId != null ? !userId.equals(userRole.userId) : userRole.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
