package com.phy.gsjlpt.entity;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  Complaint   
 * @Description:TODO(投诉实体类)
 * @author: 张晓峰 
 * @date:   2018年1月3日 下午9:45:56     
 */  
@Entity
@Component
public class Complaint {
	private Integer cid;
	private String cnumber;
	private String ccontent;
	private String cstate;
	private String creplay;
	private User cuid;
	private User cruid;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public String getCcontent() {
		return ccontent;
	}
	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
	public String getCstate() {
		return cstate;
	}
	public void setCstate(String cstate) {
		this.cstate = cstate;
	}
	public String getCreplay() {
		return creplay;
	}
	public void setCreplay(String creplay) {
		this.creplay = creplay;
	}
	public User getCuid() {
		return cuid;
	}
	public void setCuid(User cuid) {
		this.cuid = cuid;
	}
	public User getCruid() {
		return cruid;
	}
	public void setCruid(User cruid) {
		this.cruid = cruid;
	}
}
