package com.phy.gsjlpt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.phy.gsjlpt.entity.Repair;

/**   
 * @ClassName:  RepairDao   
 * @Description:TODO(报修的dao)
 * @author: 张晓峰 
 * @date:   2018年1月7日 上午10:58:45     
 */ 
@Repository
public class RepairDao {
	@Autowired 
    private SessionFactory sessionFactory;
	@Autowired 
	private Repair repair;
	public void addRepair(Repair repair){
		Session session=sessionFactory.getCurrentSession();
		session.save(repair);
	}
	public List<Repair> showRepair(){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Repair";
		Query query=session.createQuery(hql);
		//获得所有报修
			List<Repair> repairlist=query.list();
			return repairlist;
	}
	public void doReply(Repair repair){
		Session session=sessionFactory.getCurrentSession();
		String hql="update Repair b set b.rreplay =? ,b.rstate=?,b.rruid=? where b.rid = ?";
		Query query=session.createQuery(hql);
		query.setString(0, repair.getRreplay());
		query.setString(1, repair.getRstate());
		query.setEntity(2, repair.getRruid());
		query.setInteger(3, repair.getRid());
		query.executeUpdate();
	}
	public Repair getRepair(Integer rid){
		Session session=sessionFactory.getCurrentSession();
		String hql="from Repair m where m.rid=?";
		Query query=session.createQuery(hql);
		query.setInteger(0, rid);
		Repair repair=(Repair) query.uniqueResult();
		return repair;
	}
	  /**
     * 获得报修总数
     */
	public int getRepairnumber(){
    	String hql="select count(*) from Repair";
    	 Query query=sessionFactory.getCurrentSession().createQuery(hql);
    	 //将long类型转为int类型
		 int totalRecord = ((Long) query.iterate().next()).intValue();
		return totalRecord;
    } 
	 /**
     * 获得分页后的repairlist
     */
	 public List<Repair> getRepairlist(int startIndex) {
			// TODO 自动生成的方法存根
			//Page page = new Page(pageNum, totalRecord);
			String hql = "from Repair";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			//设置查询的起始位置
			query.setFirstResult(startIndex);
			//设置最大的查询数量
			query.setMaxResults(10);
			List<Repair> repairlist = query.list();
			//page.setList(list);
			return repairlist ;
		}
}
