package com.phy.gsjlpt.entity;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  Message   
 * @Description:TODO(message实体类)
 * @author: 张晓峰 
 * @date:   2018年1月3日 下午9:45:21     
 */  
@Entity
@Component
public class Message {
	private Integer mid;
	private String mnumber;
	private String mcontent;
	private String mstate;
	private String mreplay;
	private User muid;
	private User mruid;
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMnumber() {
		return mnumber;
	}
	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}
	public String getMcontent() {
		return mcontent;
	}
	public void setMcontent(String mcontent) {
		this.mcontent = mcontent;
	}
	public String getMstate() {
		return mstate;
	}
	public void setMstate(String mstate) {
		this.mstate = mstate;
	}
	public String getMreplay() {
		return mreplay;
	}
	public void setMreplay(String mreplay) {
		this.mreplay = mreplay;
	}
	public User getMuid() {
		return muid;
	}
	public void setMuid(User muid) {
		this.muid = muid;
	}
	public User getMruid() {
		return mruid;
	}
	public void setMruid(User mruid) {
		this.mruid = mruid;
	}
}
