package com.phy.gsjlpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phy.gsjlpt.dao.UserDao;
import com.phy.gsjlpt.entity.User;

/**   
 * @ClassName:  LoginService   
 * @Description:TODO(处理登录的逻辑)
 * @author: 张晓峰 
 * @date:   2018年1月1日 上午9:59:02     
 */  
@Service
@Transactional
public class LoginService {
	@Autowired
	private UserDao userdao;
	public User doLogin(String username,String password){
		//得到用户的id
		User user=userdao.doLoginuser(username, password);
		//String msg="";
		//通过判断用户id是否存在来判断用户是否存在
		//if(user!=null){
			//msg="sucess";
		//}else {
			//msg="fail";
		//}
		return user;
	}
	public void updatePwd(User user){
		userdao.updatePwd(user);
	}
}
