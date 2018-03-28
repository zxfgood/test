package com.phy.gsjlpt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phy.gsjlpt.entity.Complaint;
import com.phy.gsjlpt.entity.Complaint;

/**   
 * @ClassName:  ComplaintDao   
 * @Description:TODO(投诉的dao层)
 * @author: 张晓峰 
 * @date:   2018年1月7日 下午9:39:26     
 */ 
@Repository
public class ComplaintDao {
	@Autowired 
    private SessionFactory sessionFactory;
	@Autowired 
	private Complaint complaint;
	public void addComplaint(Complaint complaint){
		Session session=sessionFactory.getCurrentSession();
		session.save(complaint);
	}
	public List<Complaint> showComplaint(){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Complaint";
		Query query=session.createQuery(hql);
		//获得所有投诉
			List<Complaint> complaintlist=query.list();
			return complaintlist;
	}
	public void doReply(Complaint complaint){
		Session session=sessionFactory.getCurrentSession();
		String hql="update Complaint b set b.creplay =? ,b.Cstate=?,b.cruid=? where b.cid = ?";
		Query query=session.createQuery(hql);
		query.setString(0, complaint.getCreplay());
		query.setString(1, complaint.getCstate());
		query.setEntity(2, complaint.getCruid());
		query.setInteger(3, complaint.getCid());
		query.executeUpdate();
	}
	public Complaint getComplaint(Integer cid){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Complaint m where m.cid=?";
		Query query=session.createQuery(hql);
		query.setInteger(0, cid);
		Complaint complaint=(Complaint) query.uniqueResult();
		return complaint;
	}
	 /**
     * 获得投诉总数
     */
	public int getComplaintnumber(){
    	String hql="select count(*) from Complaint";
    	 Query query=sessionFactory.getCurrentSession().createQuery(hql);
    	 //将long类型转为int类型
		 int totalRecord = ((Long) query.iterate().next()).intValue();
		return totalRecord;
    } 
	 /**
     * 获得分页后的Complaintlist
     */
	 public List<Complaint> getComplaintlist(int startIndex) {
			// TODO 自动生成的方法存根
			//Page page = new Page(pageNum, totalRecord);
			String hql = "from Complaint";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			//设置查询的起始位置
			query.setFirstResult(startIndex);
			//设置最大的查询数量
			query.setMaxResults(10);
			List<Complaint> complaintlist = query.list();
			//page.setList(list);
			return complaintlist ;
		}
}
