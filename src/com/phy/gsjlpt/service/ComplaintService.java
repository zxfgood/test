package com.phy.gsjlpt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phy.gsjlpt.dao.ComplaintDao;

import com.phy.gsjlpt.entity.Complaint;
import com.phy.gsjlpt.entity.Complaint;
import com.phy.gsjlpt.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @ClassName:  ComplaintService   
 * @Description:TODO(投诉的业务逻辑)
 * @author: 张晓峰 
 * @date:   2018年1月7日 下午9:46:14     
 */
@Service
@Transactional
public class ComplaintService {
	@Autowired
	private Complaint complaintentiy;
	@Autowired
	private ComplaintDao complaintdao;
	public String addComplaint(String complaintcontent,User user){
		//设置回复内容
		complaintentiy.setCcontent(complaintcontent);
		//设置投诉状态
		complaintentiy.setCstate("新增");
		//添加投诉用户的信息
		complaintentiy.setCuid(user);
		//添加用户序号
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 String dateString = formatter.format(currentTime);
		 complaintentiy.setCnumber(dateString);
		//将用户传递给dao
		complaintdao.addComplaint(complaintentiy);
		return "sucess";
	}
	public String  showComplaint(){
		//获得所有l投诉
		List<Complaint> complaintlist=complaintdao.showComplaint();
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"cuid","cruid"});
		System.out.println("list:"+complaintlist.size());
		//转为jsons数据
		JSONArray jsonArr = JSONArray.fromObject(complaintlist,config);
		String complaintjson=jsonArr.toString();
		return complaintjson;
	}
	public void replyComplaint(String id, String creply, User user){
		Integer cid = Integer.valueOf(id);
		System.out.println("回复cid:"+cid);
		//Complaintdao.doReply(mid,mreply,user);
		complaintentiy.setCid(cid);
		complaintentiy.setCstate("完成");
		complaintentiy.setCreplay(creply);
		complaintentiy.setCruid(user);
		complaintdao.doReply(complaintentiy);
	}
	public String getComplaint(String id){
		Integer cid = Integer.valueOf(id);
		//根据cid获得投诉
		Complaint complaint=complaintdao.getComplaint(cid);
		//去除外键约束，不然后台会出错
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"cuid","cruid"});
	   //将投诉类转为json数据格式
		JSONObject json=JSONObject.fromObject(complaint,config);
		System.out.println("jsontostring"+json.toString());
		return json.toString();
	}
	/**
     * 获得当前总页数
     */
	public int gettotalPage() {
		// TODO 自动生成的方法存根
		int totalRecord=complaintdao.getComplaintnumber();
		//设置每页显示几条数据
		int pageSize=10;
		int totalPage;
		//计算有几页
		if (totalRecord % pageSize == 0) {
			totalPage = totalRecord / pageSize;
		} else {
			totalPage = totalRecord / pageSize + 1;
		}
		return totalRecord;
	}
	public List<Complaint> getComplaintlist(int currentpage){
		//感觉当前页数设置查询结果的起始位置
		int startIndex = (currentpage - 1) * 10;
		List<Complaint> complaintlist=complaintdao.getComplaintlist(startIndex);
		return complaintlist;
	}
}
