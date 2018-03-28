package com.phy.gsjlpt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;

/**   
 * @ClassName:  MessageDao   
 * @Description:TODO
 * @author: 张晓峰 
 * @date:   2018年1月3日 上午10:00:28     
 */  
@Repository
public class MessageDao {
	@Autowired 
    private SessionFactory sessionFactory;
	@Autowired 
	private Message message;
	public void addMessage(Message message){
		Session session=sessionFactory.getCurrentSession();
		session.save(message);
	}
	public List<Message> showMessage(){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Message";
		Query query=session.createQuery(hql);
		//获得所有留言
			List<Message> messagelist=query.list();
			return messagelist;
	}
	public void doReply(Message message){
		Session session=sessionFactory.getCurrentSession();
		String hql="update Message b set b.mreplay =? ,b.mstate=?,b.mruid=? where b.mid = ?";
		Query query=session.createQuery(hql);
		query.setString(0, message.getMreplay());
		query.setString(1, message.getMstate());
		query.setEntity(2, message.getMruid());
		query.setInteger(3, message.getMid());
		query.executeUpdate();
	}
	public Message getMessage(Integer mid){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Message m where m.mid=?";
		Query query=session.createQuery(hql);
		query.setInteger(0, mid);
		Message message=(Message) query.uniqueResult();
		return message;
	}
	 /**
     * 获得留言总数
     */
	public int getMessagenumber(){
    	String hql="select count(*) from Message";
    	 Query query=sessionFactory.getCurrentSession().createQuery(hql);
    	 //将long类型转为int类型
		 int totalRecord = ((Long) query.iterate().next()).intValue();
		return totalRecord;
    } 
	 /**
     * 获得分页后的messagelist
     */
	 public List<Message> getMessagelist(int startIndex) {
			// TODO 自动生成的方法存根
			//Page page = new Page(pageNum, totalRecord);
			String hql = "from Message";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			//设置查询的起始位置
			query.setFirstResult(startIndex);
			//设置最大的查询数量
			query.setMaxResults(10);
			List<Message> mesagelist = query.list();
			//page.setList(list);
			return mesagelist ;
		}
	 public List<Message> findMessage(String date,String state,int startIndex){
			Session session=sessionFactory.getCurrentSession();
			//按照时间
			String hql="from Message a where a.mstate like:state and a.mnumber like:date";
			Query query=session.createQuery(hql);
			query.setString("state","%"+state+"%");
			query.setString("date", "%"+date+"%");
			//设置查询的起始位置
			query.setFirstResult(startIndex);
			//设置最大的查询数量
			query.setMaxResults(10);
			List<Message> messagelist=query.list();
			return messagelist;
		}
	 public int messageNumber(String date,String state){
	    	String hql="select count(*) from Message a where a.mstate like:state and a.mnumber like:date";
	    	 Query query=sessionFactory.getCurrentSession().createQuery(hql);
	    	 query.setString("state","%"+state+"%");
			 query.setString("date", "%"+date+"%");
	    	 //将long类型转为int类型
			 int totalRecord = ((Long) query.iterate().next()).intValue();
			return totalRecord;
	    } 
}
