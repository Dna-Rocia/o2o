package com.royail.o2o.entity;

import java.util.Date;


public class Area {
	//ID
	private Long areaId;
	//權重
	private Integer priority;
	//名稱
	private String areaName;
	//時間(創建， 更改)
	private Date createTime;
	
	private Date lastEditTime;
	
	
	//setter&&getter
	
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	

	
	
	
	
	
	
	
	
	
}
