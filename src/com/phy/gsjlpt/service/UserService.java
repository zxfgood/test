package com.phy.gsjlpt.service;


import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.phy.gsjlpt.dao.UserDao;
import com.phy.gsjlpt.entity.User;
/**   
 * @ClassName:  Permission   
 * @Description:TODO(处理用户权限角色)
 * @author: 张晓峰 
 * @date:   2018年3月7日 下午7:00:30     
 */
@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userdao;
	public User findByUsername(String username){
		return userdao.finduserByusername(username);
	}
	public Set<String> getRole(String username){
		return userdao.findUserRole(username);
	}
	public Set<String> getPermission(String username){
		return userdao.findUserPermission(username);
	}
}
