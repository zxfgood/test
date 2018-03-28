package com.phy.gsjlpt.entity;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  Repair   
 * @Description:TODO(报修实体类)
 * @author: 张晓峰 
 * @date:   2018年1月3日 下午9:46:31     
 */  
@Entity
@Component
public class Repair {
	private Integer rid;
	private String rnumber;
	private String rcontent;
	private String rstate;
	private String rreplay;
	private User ruid;
	private User rruid;
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getRnumber() {
		return rnumber;
	}
	public void setRnumber(String rnumber) {
		this.rnumber = rnumber;
	}
	public String getRcontent() {
		return rcontent;
	}
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	public String getRstate() {
		return rstate;
	}
	public void setRstate(String rstate) {
		this.rstate = rstate;
	}
	public String getRreplay() {
		return rreplay;
	}
	public void setRreplay(String rreplay) {
		this.rreplay = rreplay;
	}
	public User getRuid() {
		return ruid;
	}
	public void setRuid(User ruid) {
		this.ruid = ruid;
	}
	public User getRruid() {
		return rruid;
	}
	public void setRruid(User rruid) {
		this.rruid = rruid;
	}
}
