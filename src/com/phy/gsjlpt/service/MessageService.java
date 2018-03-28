package com.phy.gsjlpt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phy.gsjlpt.dao.MessageDao;
import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;



/**   
 * @ClassName:  MessageService   
 * @Description:TODO(处理留言的逻辑)
 * @author: 张晓峰 
 * @date:   2018年1月3日 上午9:51:12     
 */
@Service
@Transactional
public class MessageService {
	@Autowired
	private Message messageentiy;
	@Autowired
	private MessageDao messagedao;
	public String addMessage(String messagecontent,User user){
		//设置回复内容
		messageentiy.setMcontent(messagecontent);
		//设置留言状态
		messageentiy.setMstate("新增");
		//添加留言用户的信息
		messageentiy.setMuid(user);
		//添加用户序号
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 String dateString = formatter.format(currentTime);
		 messageentiy.setMnumber(dateString);
		//将用户传递给dao
		messagedao.addMessage(messageentiy);
		return "sucess";
	}
	public String  showMessage(){
		//获得所有l留言
		List<Message> messagelist=messagedao.showMessage();
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"muid","mruid"});
		System.out.println("list:"+messagelist.size());
		//转为jsons数据
		JSONArray jsonArr = JSONArray.fromObject(messagelist,config);
		String messagejson=jsonArr.toString();
		return messagejson;
	}
	public void replyMessage(String id, String mreply, User user){
		Integer mid = Integer.valueOf(id);
		System.out.println("回复mid:"+mid);
		//messagedao.doReply(mid,mreply,user);
		messageentiy.setMid(mid);
		messageentiy.setMstate("完成");
		messageentiy.setMreplay(mreply);
		messageentiy.setMruid(user);
		messagedao.doReply(messageentiy);
	}
	public String getMessage(String id){
		Integer mid = Integer.valueOf(id);
		//根据mid获得留言
		Message message=messagedao.getMessage(mid);
		//去除外键约束，不然后台会出错
		JsonConfig config = new JsonConfig();  
		 config.setExcludes(new String[]{"muid","mruid"});
	   //将留言类转为json数据格式
		JSONObject json=JSONObject.fromObject(message,config);
		System.out.println("jsontostring"+json.toString());
		return json.toString();
	}
	public int gettotalPage() {
		// TODO 自动生成的方法存根
		int totalRecord=messagedao.getMessagenumber();
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
	public List<Message> getMessagelist(int currentpage){
		//感觉当前页数设置查询结果的起始位置
		int startIndex = (currentpage - 1) * 10;
		List<Message> messagelist=messagedao.getMessagelist(startIndex);
		return messagelist;
	}
	public List<Message> findMessage(String date,String state,int currentpage){
		//获得模糊查询的留言记录
		int startIndex = (currentpage - 1) * 10;
		List<Message> messagelist=messagedao.findMessage(date,state, startIndex);
		return messagelist;
	}
	public int messageNmber(String date,String state){
		//获得留言数量
		int totalRecord=messagedao.messageNumber(date,state);
		return totalRecord;
	}
}
