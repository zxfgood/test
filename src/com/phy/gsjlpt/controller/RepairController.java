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

import com.phy.gsjlpt.entity.Repair;
import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.service.RepairService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @ClassName:  RepairController   
 * @Description:TODO(报修的controller)
 * @author: 张晓峰 
 * @date:   2018年1月7日 上午11:50:58     
 */
@Controller
@RequestMapping(value="repair",produces="application/json; charset=utf-8")
public class RepairController {
	@Autowired
	private RepairService repairservice;
	@RequestMapping(value="addrepair",method=RequestMethod.POST)
	@ResponseBody
	public String addRepair(HttpServletRequest request) throws UnsupportedEncodingException{
		System.out.println("添加报修");
		//获得话题
		String rcontent=request.getParameter("repaircontent");
		System.out.println("rconteng1:"+rcontent);
		//处理乱码
		//String mcontent= new String(mcontent1.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(rcontent);
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String result=repairservice.addRepair(rcontent, user);
		//返回处理结果
		return "{\"result\":\""+result+"\"}";
	}
	@RequestMapping(value="replyrepair",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void replyRepair(HttpServletRequest request){
		//获得报修ID
		String rid=request.getParameter("rid");
		//获得回复内容
		String rreply=request.getParameter("rreply");
		System.out.println("回复信息rreply:"+rreply);
		HttpSession session=request.getSession();
		//获得回复用户
		User user=(User) session.getAttribute("user");
		repairservice.replyRepair(rid,rreply,user);
	}
	@RequestMapping(value="show")
	@ResponseBody
	public String showRepair(HttpServletRequest request){
		//获得当前页数，如果为空的话，当前页数为1,其他则为前台的页数
		String currentpage=request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex");
		//String Repair=Repairservice.showRepair();
		//System.out.println(Repair);
		//将currentpage转为整型后进行查询
		List<Repair> repairlist=repairservice.getRepairlist(Integer.valueOf(currentpage));
		//获得总页数
		int totalPage=repairservice.gettotalPage();
		//将list和totalPage放进map传给前台
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("repairlist", repairlist);
		 map.put("totalPage",totalPage);
		 map.put("currentpage",currentpage);
		 //将map转为json数据格式
		 JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"ruid","rruid"});
		 String json = JSONObject.fromObject(map,config).toString();
		return json;
	}
	@RequestMapping(value="repairdetail",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String repairDetail(HttpServletRequest request){
		//获得mid
		String rid=request.getParameter("rid");
		System.out.println("报修细节的rid："+rid);
		//获得Repair信息的json数据
		String repairjson=repairservice.getRepair(rid);
		return repairjson;
	}
	@RequestMapping(value="repairindex",method={RequestMethod.POST,RequestMethod.GET})
		public String goRepairindex(){
		//要改的
			return "repairment";
		}
}
