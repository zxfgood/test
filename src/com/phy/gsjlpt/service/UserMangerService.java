package com.phy.gsjlpt.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phy.gsjlpt.dao.UserDao;
import com.phy.gsjlpt.entity.Message;
import com.phy.gsjlpt.entity.User;
import com.phy.gsjlpt.sha.SHAUti;
import com.phy.gsjlpt.utils.PasswordHelper;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;

/**   
 * @ClassName:  UserMangerService   
 * @Description:TODO(进行用户管理)
 * @author: 张晓峰 
 * @date:   2018年1月4日 下午8:27:01     
 */ 
@Service
@Transactional
public class UserMangerService {
	@Autowired
	private UserDao userdao;
	@Autowired
    PasswordHelper passwordHelper;
	public void getFile(String path){
		//得到文件
		File file=new File(path);
		try {
			String username="";
			String password="";
			String dept="";
			String realname="";
			String role="";
			Workbook workbook=Workbook.getWorkbook(file);
			//获得sheet页
			Sheet sheet=workbook.getSheet(0);
			//遍历excel
			for(int row=1;row<sheet.getRows();row++){
				for(int col=0;col<sheet.getColumns();col++){
				   //获得单元格
					Cell cell=sheet.getCell(col,row);
					//获得单元格内容
					String mes=cell.getContents();
					if(col==0){
						//user.setUsername(mes);
						username=mes;
					}
					if(col==1){
						//user.setPassword(mes);
						//对密码进行加密
						password=SHAUti.shaEncode(mes);
						System.out.println("加密的密码："+password);
					}
					if(col==2){
						//user.setDept(mes);
						dept=mes;
					}
					if(col==3){
						//user.setRealname(mes);
						realname=mes;
					}
					if(col==4){
						//user.setRole(mes);
						role=mes;
					}
				}
				//通过名字来判断用户是否存在
				User user=userdao.finduserByusername(username);
				//不存在则添加用户，存在则更新用户
				if(user==null){
				userdao.addUser(username, password, dept, realname, role);
				}else{
					user.setDept(dept);
					user.setPassword(password);
					user.setRealname(realname);
					user.setRole(role);
					user.setUid(user.getUid());
					userdao.updateuser(user);
				}
			}
		} catch (BiffException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("文件打开失败");
		}
	}
	public String getallUser(){
		List<User> useralllist=userdao.getallUser();
		JSONArray jsonArr = JSONArray.fromObject(useralllist);
		String userjson=jsonArr.toString();
		return userjson;
		
		
		
	}
	public void deleteUser(String id){
		Integer uid = Integer.valueOf(id);
		userdao.deleteUser(uid);
	}
	public String findUser(String username){
		List<User> userlist=userdao.findUser(username);
		JSONArray jsonArr = JSONArray.fromObject(userlist);
		String userlistjson=jsonArr.toString();
		return userlistjson;
	}
	public int gettotalPage() {
		// TODO 自动生成的方法存根
		int totalRecord=userdao.getMessagenumber();
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
	public List<User> getUserlist(int currentpage){
		//感觉当前页数设置查询结果的起始位置
		int startIndex = (currentpage - 1) * 10;
		List<User> userlist=userdao.getUserlist(startIndex);
		return userlist;
	}
	public void addOneuser(String username,String password,String dept,String realname,String role){
		userdao.addOneuser(username, password, dept, realname, role);
	}
	public void addUser(User user){
		user.setSalt(passwordHelper.getRandomSalt());
		user.setPassword(passwordHelper.encryptPassword(user.getPassword(), user.getCredentialsSalt()));
		userdao.addUser(user);
	}
}
