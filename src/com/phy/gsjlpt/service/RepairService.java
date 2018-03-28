package com.phy.gsjlpt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phy.gsjlpt.dao.RepairDao;
import com.phy.gsjlpt.entity.Repair;
import com.phy.gsjlpt.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**   
 * @ClassName:  RepairService   
 * @Description:TODO(报修的逻辑处理)
 * @author: 张晓峰 
 * @date:   2018年1月7日 上午11:11:11     
 */  
@Service
@Transactional
public class RepairService {
	@Autowired
	private Repair repairentiy;
	@Autowired
	private RepairDao repairdao;
	public String addRepair(String repaircontent,User user){
		//设置回复内容
		repairentiy.setRcontent(repaircontent);
		//设置报修状态
		repairentiy.setRstate("新增");
		//添加报修用户的信息
		repairentiy.setRuid(user);
		//添加用户序号
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 String dateString = formatter.format(currentTime);
		 repairentiy.setRnumber(dateString);
		//将用户传递给dao
		repairdao.addRepair(repairentiy);
		return "sucess";
	}
	public String  showRepair(){
		//获得所有l报修
		List<Repair> repairlist=repairdao.showRepair();
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"muid","mruid"});
		System.out.println("list:"+repairlist.size());
		//转为jsons数据
		JSONArray jsonArr = JSONArray.fromObject(repairlist,config);
		String repairjson=jsonArr.toString();
		return repairjson;
	}
	public void replyRepair(String id, String rreply, User user){
		Integer rid = Integer.valueOf(id);
		System.out.println("回复rid:"+rid);
		//Repairdao.doReply(rid,mreply,user);
		repairentiy.setRid(rid);
		//修改报修状态
		repairentiy.setRstate("完成");
		repairentiy.setRreplay(rreply);
		//添加报修用户
		repairentiy.setRruid(user);
		repairdao.doReply(repairentiy);
	}
	public String getRepair(String id){
		Integer rid = Integer.valueOf(id);
		//根据rid获得报修
		Repair repair=repairdao.getRepair(rid);
		//去除外键约束，不然后台会出错
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"ruid","rruid"});
	   //将报修类转为json数据格式
		JSONObject json=JSONObject.fromObject(repair,config);
		System.out.println("jsontostring"+json.toString());
		return json.toString();
	}
	  /**
     * 获得当前页数
     */
	public int gettotalPage() {
		// TODO 自动生成的方法存根
		int totalRecord=repairdao.getRepairnumber();
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
	public List<Repair> getRepairlist(int currentpage){
		//感觉当前页数设置查询结果的起始位置
		int startIndex = (currentpage - 1) * 10;
		List<Repair> repairlist=repairdao.getRepairlist(startIndex);
		return repairlist;
	}
}
