package com.phy.gsjlpt.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.LoginService;
import com.phy.gsjlpt.sha.SHAUti;

/**   
 * @ClassName:  LoginController   
 * @Description:TODO(处理登录)
 * @author: 张晓峰 
 * @date:   2018年1月1日 上午9:48:00     
 */ 
@Controller
@RequestMapping(value="user",produces="application/json; charset=utf-8")
public class LoginController {
	@Autowired
	private LoginService loginservice;
	@RequestMapping(value="login2",method=RequestMethod.POST)
	@ResponseBody
	public String doLogin(HttpServletRequest request){
		//test
		System.out.println("456456");
		//获得用户名
		String username=request.getParameter("username");
		//获得密码
		String realpassword=request.getParameter("password");
		String password="";
		try {
			//将原密码进行加密进行比对
			password = SHAUti.shaEncode(realpassword);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		//获得用户
		User user=loginservice.doLogin(username, password);
		HttpSession session=request.getSession();
		//System.out.println("sessionID"+session.getId());
		//将用户信息存入session
		session.setAttribute("user", user);
		String msg="";
		if(user!=null){
		System.out.println("username:"+user.getRealname());
		msg="sucess";
	}else {
		msg="fail";
	}
		return "{\"result\":\""+msg+"\"}";
	}
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public String doLogin2(HttpServletRequest request){
		System.out.println("456456");
		String msg="sucess";
		//获得用户名
		String username=request.getParameter("username");
		//获得密码
		String realpassword=request.getParameter("password");
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(username, realpassword);
		try {
			subject.login(token);
		} catch (Exception e) {
			// TODO: handle exception
			msg="fail";
		}
		return "{\"result\":\""+msg+"\"}";
	}
	@RequestMapping(value="scucessdo",method={RequestMethod.POST,RequestMethod.GET})
	public String doScucess(){
		System.out.println("这是scucssdo");
		//返回index页面
		return "index";
	}
	@RequestMapping(value="faildo",method={RequestMethod.POST,RequestMethod.GET})
	public String doFail(){
		return "fail";
	}
	@RequestMapping(value="logout",method={RequestMethod.POST,RequestMethod.GET})
	public void logout(HttpServletRequest request){
		HttpSession session=request.getSession();
		//移除session的user
		session.removeAttribute("user");
		//销毁session
		session.invalidate();
		//return "fail";
	}
	@RequestMapping(value="updatepwd",method={RequestMethod.POST,RequestMethod.GET})
	public void updatePssword(HttpServletRequest request) throws UnsupportedEncodingException{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String updatepwd=request.getParameter("updatepwd");
		System.out.println("更新的密码:"+updatepwd);
		String updatepwd1=SHAUti.shaEncode(updatepwd);
		user.setPassword(updatepwd1);
		loginservice.updatePwd(user);
	}
}
