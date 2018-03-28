package com.phy.gsjlpt.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.UserMangerService;

/**   
 * @ClassName:  UserMangerController   
 * @Description:TODO
 * @author: 张晓峰 
 * @date:   2018年1月3日 上午9:44:19     
 */ 
@Controller
@RequestMapping(value="manger",produces="application/json; charset=utf-8")
/**
 * produces防止json数据传送给前台乱码
 */
public class UserMangerController {
	@Autowired
	private UserMangerService mangerservice;
	@RequestMapping(value="mindex")
	public String index(){
		System.out.println("进入manger首页");
		return "management";
	}
	@RequestMapping(value = "/upload", method ={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody	
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, ModelMap model) {
        System.out.println("开始");
        String fileName="";
        //String path = request.getSession().getServletContext().getRealPath("upload");
        String path="V:/upload";
        //得到文件名
        fileName = file.getOriginalFilename();
        //获得保存文件的路径
        String realpath=path+"/"+fileName;
        System.out.println("filename:"+fileName);
        // String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存
        try {
        	//将文件上传至本地服务器
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);
        //return "{\"result\":\""+fileName+"\"}";
        System.out.println("realpath:"+realpath);
       // return realpath;
        return "{\"result\":\""+realpath+"\"}";
    }
	@RequestMapping(value = "/file", method ={RequestMethod.GET,RequestMethod.POST})
	public void getFile(HttpServletRequest request){
		String filepath=request.getParameter("filename");
		System.out.println("执行了添加用户");
		System.out.println("文件保存filepath:"+filepath);
		mangerservice.getFile(filepath);
		//return "management";
	}
	@RequestMapping(value = "/getalluser", method ={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String getallUser(HttpServletRequest request){
		//String userlistjson=mangerservice.getallUser();
		//获得当前页数，如果为空的话，当前页数为1,其他则为前台的页数
		String currentpage=request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex");
		System.out.println("usercurrentpage"+currentpage);
		List<User> userlist=mangerservice.getUserlist(Integer.valueOf(currentpage));
		//获得总页数
		int totalPage=mangerservice.gettotalPage();
		//将list和totalPage放进map传给前台
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("userlist", userlist);
		 map.put("totalPage",totalPage);
		 map.put("currentpage",currentpage);
		 //将map转为
		// JsonConfig config = new JsonConfig();  
		 //config.setExcludes(new String[]{"muid","mruid"});
		 String json = JSONObject.fromObject(map).toString();
		 System.out.println("userlist的json:"+json);
		return json;
	}
	@RequestMapping(value = "/deleteuser", method ={RequestMethod.GET,RequestMethod.POST})
	public void deleteUser(HttpServletRequest request){
		//String userlistjson=mangerservice.getallUser();
		System.out.println("执行了删除用户");
		String uid=request.getParameter("uid");
		System.out.println("删除用户uid:"+uid);
		mangerservice.deleteUser(uid);
	}
	@RequestMapping(value = "/finduser", method ={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String findUser(HttpServletRequest request){
		//String userlistjson=mangerservice.getallUser();
		System.out.println("执行了模糊查找用户");
		String username=request.getParameter("username");
		//System.out.println("删除用户uid:"+uid);
		System.out.println("模糊查询的用户名:"+username);
		String userlist=mangerservice.findUser(username);
		System.out.println(userlist);
		return userlist;
	}
	@RequestMapping(value = "/adduser", method ={RequestMethod.GET,RequestMethod.POST})
	public void addNewuser(HttpServletRequest request){
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String dept=request.getParameter("dept");
		String realname=request.getParameter("realname");
		String role=request.getParameter("role");
		mangerservice.addOneuser(username, password, dept, realname, role);
	}
}
