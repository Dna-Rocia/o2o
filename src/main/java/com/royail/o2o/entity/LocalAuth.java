package com.royail.o2o.entity;

import java.util.Date;

public class LocalAuth {

	private Long 
					localAuthId;
	
	private String  
					username, password;
	
	private Date
					createTime , lastEditTime;
	
	private PersonInfo 
					personInfo;
	
	
	//setter&&getter
	
	
	
	public Long getLocalAuthId() {
		return localAuthId;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
