package com.phy.gsjlpt.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.MessageService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @ClassName:  MessageController   
 * @Description:TODO
 * @author: 张晓峰 
 * @date:   2018年1月3日 上午9:46:58     
 */  
@Controller
@RequestMapping(value="message",produces="application/json; charset=utf-8")
public class MessageController {
	@Autowired
	private MessageService messageservice;
	@RequestMapping(value="addmessage",method=RequestMethod.POST)
	@ResponseBody
	public String addMessage(HttpServletRequest request) throws UnsupportedEncodingException{
		System.out.println("添加留言");
		//获得话题
		String mcontent=request.getParameter("messagecontent");
		System.out.println("mconteng1:"+mcontent);
		//处理乱码
		//String mcontent= new String(mcontent1.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(mcontent);
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String result=messageservice.addMessage(mcontent, user);
		//返回处理结果
		return "{\"result\":\""+result+"\"}";
	}
	@RequestMapping(value="replymessage",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void replyMessage(HttpServletRequest request){
		//获得留言ID
		String mid=request.getParameter("mid");
		//获得回复内容
		String mreply=request.getParameter("mreply");
		System.out.println("回复信息mreply:"+mreply);
		HttpSession session=request.getSession();
		//获得回复用户
		User user=(User) session.getAttribute("user");
		messageservice.replyMessage(mid,mreply,user);
	}
	@RequestMapping(value="show")
	@ResponseBody
	public String showMessage(HttpServletRequest request){
		//获得当前页数，如果为空的话，当前页数为1,其他则为前台的页数
		String currentpage=request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex");
		//String message=messageservice.showMessage();
		//System.out.println(message);
		//将currentpage转为整型后进行查询
		System.out.println("//////////////////////////////////");
		System.out.println("currentpage"+currentpage);
		System.out.println("//////////////////////////////////");
		List<Message> messagelist=messageservice.getMessagelist(Integer.valueOf(currentpage));
		//获得总条数
		int totalPage=messageservice.gettotalPage();
		//将list和totalPage放进map传给前台
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("messagelist", messagelist);
		 map.put("totalPage",totalPage);
		 map.put("currentpage",currentpage);
		 //将map转为
		 JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"muid","mruid"});
		 String json = JSONObject.fromObject(map,config).toString();
		return json;
	}
	@RequestMapping(value="getuser",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String getUser(HttpServletRequest request){
		HttpSession session=request.getSession();
		//获得当前用户
		User user=(User) session.getAttribute("user");
		//把user信息转为json格式
		JSONObject json=JSONObject.fromObject(user);
		System.out.println("jsontostring"+json.toString());
		return json.toString();
	}
	@RequestMapping(value="messagedetail",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String messageDetail(HttpServletRequest request){
		//获得mid
		String mid=request.getParameter("mid");
		System.out.println("用户细节的mid："+mid);
		//获得message信息的json数据
		String messagejson=messageservice.getMessage(mid);
		return messagejson;
	}
	@RequestMapping(value="messageindex",method={RequestMethod.POST,RequestMethod.GET})
		public String goMessageindex(){
			return "index";
		}
	@RequestMapping(value="findmessage",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String getMessagenmber(HttpServletRequest request){
		//获得当前页数，如果为空的话，当前页数为1,其他则为前台的页数
		String currentpage=request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex");
		String state=request.getParameter("state");
		String date=request.getParameter("date");
		if(state==null){
			state="";
		}
		if(date==null){
			date="";
		}
		int totalPage=messageservice.messageNmber(date,state);
		List<Message> messagelist=messageservice.findMessage(date,state,Integer.valueOf(currentpage));
		//将list和totalPage放进map传给前台
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("messagelist", messagelist);
		 map.put("totalPage",totalPage);
		 map.put("currentpage",currentpage);
		 //将map转为
		 JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"muid","mruid"});
		 String json = JSONObject.fromObject(map,config).toString();
		return json;
		//return totalRecord;
	}
}
