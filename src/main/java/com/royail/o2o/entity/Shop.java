package com.royail.o2o.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.royail.o2o.enums.ShopStateEnum;


public class Shop {

	private Long 
					shopId;
	
	private String 
					shopName, shopDesc,shopAddr, phone, shopImg, advice;
	//advice:超级管理员给店家的提醒
	
	private Integer 
					priority,enableStatus;
	//enableStatus:-1.不可用 0.审核中 1.可用
	
	private Date 
					createTime, lastEditTime;

	private Area 
					area;
	
	private PersonInfo 
					owner;
	
	private ShopCategory 
					shopCategory;
	
	
	
	
	
	//constructor

//	public Shop() {
//		this.shopId = 1l;
//		this.shopName = "老大铺";
//		this.shopDesc = "AAA描述";
//		this.shopAddr = "广州市天河区";
//		this.lastEditTime = new Date();
//	}

	
	
	public Shop() {
		
	}

	
	
	
//测试增加的时候
	public Shop(String insert,long num) {
		if (insert.equals("insert")) {
			this.shopName = "我的测试铺";
			this.shopDesc = "ms";
			this.shopAddr = "dz";
			this.phone = "dh";
			this.advice = "审核中";
			this.enableStatus = ShopStateEnum.CHECK.getState();	
		}else {
			this.shopName = "老罗锅书铺";
			this.shopAddr = "同在地球村";
			this.shopId = num;
		}
	}

	
	
	
	
	
	
	//setter&&getter




	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", shopName=" + shopName + ", shopDesc=" + shopDesc + ", shopAddr=" + shopAddr
				+ ", phone=" + phone + ", shopImg=" + shopImg + ", advice=" + advice + ", priority=" + priority
				+ ", enableStatus=" + enableStatus + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
				+ ", area=" + area + ", owner=" + owner + ", shopCategory=" + shopCategory + "]";
	}



	
	

	public Long getShopId() {
		return shopId;
	}




	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public String getShopAddr() {
		return shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public PersonInfo getOwner() {
		return owner;
	}

	public void setOwner(PersonInfo owner) {
		this.owner = owner;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	
	
	
	
	
	
	
	
	
}
