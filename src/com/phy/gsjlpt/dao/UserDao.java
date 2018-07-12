package com.phy.gsjlpt.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;

/**   
 * @ClassName:  UserDao   
 * @Description:TODO
 * @author: 张晓峰 
 * @date:   2018年1月1日 上午10:04:02     
 */  
@Repository
public class UserDao {
	@Autowired 
    private SessionFactory sessionFactory;
	@Autowired
	private User user;
	public User doLoginuser(String username,String password){
		Session session=sessionFactory.getCurrentSession();
		String hql="from User a where a.username=? and a.password=?";
		Query query=session.createQuery(hql);
		query.setString(0, username);
		query.setString(1, password);
		//获得user
		User user=(User) query.uniqueResult();
		return user;	
	}
	public void addUser(String username,String password,String dept,String realname,String role){
		Session session=sessionFactory.getCurrentSession();
		user.setUsername(username);
		user.setPassword(password);
		user.setDept(dept);
		user.setRealname(realname);
		user.setRole(role);
		session.save(user);
		//清除session中的缓存数据（不管缓存与数据库的同步）
		session.clear();
	}
	public List<User> getallUser(){
		Session session=sessionFactory.getCurrentSession();
		String hql="from User";
		Query query=session.createQuery(hql);
		List<User> userlist=query.list();
		return userlist;
	}
	public void deleteUser(Integer uid){
		Session session=sessionFactory.getCurrentSession();
		user.setUid(uid);
		session.delete(user);
	}
	public List<User> findUser(String username){
		Session session=sessionFactory.getCurrentSession();
		String hql="from User a where a.username like:name";
		Query query=session.createQuery(hql);
		query.setString("name", "%"+username+"%");
		List<User> userlist=query.list();
		return userlist;
	}
	public int getMessagenumber(){
    	String hql="select count(*) from User";
    	 Query query=sessionFactory.getCurrentSession().createQuery(hql);
    	 //将long类型转为int类型
		 int totalRecord = ((Long) query.iterate().next()).intValue();
		return totalRecord;
    } 
	 /**
     * 获得分页后的userlist
     */
	 public List<User> getUserlist(int startIndex) {
			// TODO 自动生成的方法存根
			//Page page = new Page(pageNum, totalRecord);
			String hql = "from User";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			//设置查询的起始位置
			query.setFirstResult(startIndex);
			//设置最大的查询数量
			query.setMaxResults(10);
			List<User> userlist = query.list();
			//page.setList(list);
			return userlist;
		}
	 public void updatePwd(User user){
		 Session session=sessionFactory.getCurrentSession();
		 session.update(user);
	 }
	 public void addOneuser(String username,String password,String dept,String realname,String role){
		 Session session=sessionFactory.getCurrentSession();
		 user.setUsername(username);
		 user.setPassword(password);
		 user.setRealname(realname);
		 user.setRole(role);
		 user.setDept(dept);
		 session.save(user);
	 }
	 public User finduserByusername(String username){
		 Session session=sessionFactory.getCurrentSession();
		 //通过用户名查找用户
		 String hql="from User a where a.username=?"; 
		 Query query=session.createQuery(hql);
		 query.setString(0, username);
		 User user=(User) query.uniqueResult();
		 //session.close();
		return user; 
	 }
	 
	 public void updateuser(User user){
		 System.out.println("进入了更新用户");
		 Session session=sessionFactory.getCurrentSession();
		 session.update(user);
		 //清除缓存和数据库同步
		 session.flush();
	 }
	 public Set<String> findUserRole(String username){
		 Session session=sessionFactory.getCurrentSession();
		 String hql=" select r.role from User u, Role r,UserRole ur where u.username=? and u.uid=ur.userId and r.id=ur.roleId"; 
		 Query query=session.createQuery(hql);
		 query.setString(0, username);
		 List<String> role=query.list();
		 Set<String> roleset = new HashSet(role);
		 return roleset;
	 }
	 public Set<String> findUserPermission(String username){
		 Session session=sessionFactory.getCurrentSession();
		 String hql= "select p.permission" 
		 +" from User u, Role r, Permission p, UserRole ur, RolePermission rp" 
		 +" where u.username=? and u.uid=ur.userId" 
		 +" and r.id=ur.roleId" 
		 +" and r.id=rp.roleId" 
		 +" and p.id=rp.permissionId";
		 Query query=session.createQuery(hql);
		 query.setString(0, username);
		 List<String> permission=query.list();
		 Set<String> permissionset = new HashSet(permission);
		 return permissionset;
	 }
	 
	 public void addUser(User user){
		 Session session=sessionFactory.getCurrentSession();
		 session.save(user);
		//清除缓存和数据库同步
		 session.flush();
	 }
}
