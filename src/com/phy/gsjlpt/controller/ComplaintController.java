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

import com.phy.gsjlpt.entity.Complaint;
import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.ComplaintService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @ClassName:  ComplaintController   
 * @Description:TODO(投诉的controller处理)
 * @author: 张晓峰 
 * @date:   2018年1月7日 下午9:44:15     
 */
@Controller
@RequestMapping(value="complaint",produces="application/json; charset=utf-8")
public class ComplaintController {
	@Autowired
	private ComplaintService complaintservice;
	@RequestMapping(value="addcomplaint",method=RequestMethod.POST)
	@ResponseBody
	public String addComplaint(HttpServletRequest request) throws UnsupportedEncodingException{
		System.out.println("添加投诉");
		/////////////改了的标记///////////////////////
		//获得话题
		String ccontent=request.getParameter("complaintcontent");
		System.out.println("cconteng1:"+ccontent);
		//处理乱码
		//String mcontent= new String(mcontent1.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(ccontent);
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String result=complaintservice.addComplaint(ccontent, user);
		//返回处理结果
		return "{\"result\":\""+result+"\"}";
	}
	@RequestMapping(value="replycomplaint",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void replyComplaint(HttpServletRequest request){
		//获得投诉ID
		String cid=request.getParameter("cid");
		//获得回复内容
		String creply=request.getParameter("creply");
		System.out.println("回复信息mreply:"+creply);
		HttpSession session=request.getSession();
		//获得回复用户
		User user=(User) session.getAttribute("user");
		complaintservice.replyComplaint(cid,creply,user);
	}
	@RequestMapping(value="show")
	@ResponseBody
	public String showComplaint(HttpServletRequest request){
		//获得当前页数，如果为空的话，当前页数为1,其他则为前台的页数
		String currentpage=request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex");
		//String Complaint=Complaintservice.showComplaint();
		//System.out.println(Complaint);
		//将currentpage转为整型后进行查询
		List<Complaint> complaintlist=complaintservice.getComplaintlist(Integer.valueOf(currentpage));
		//获得总页数
		int totalPage=complaintservice.gettotalPage();
		//将list和totalPage放进map传给前台
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("complaintlist", complaintlist);
		 map.put("totalPage",totalPage);
		 map.put("currentpage",currentpage);
		 //将map转为
		 JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"cuid","cruid"});
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
	@RequestMapping(value="complaintdetail",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String complaintDetail(HttpServletRequest request){
		//获得mid
		String cid=request.getParameter("cid");
		System.out.println("用户细节的mid："+cid);
		//获得Complaint信息的json数据
		String complaintjson=complaintservice.getComplaint(cid);
		return complaintjson;
	}
	@RequestMapping(value="complaintindex",method={RequestMethod.POST,RequestMethod.GET})
		public String goComplaintindex(){
			return "complant";
		}
}
