package com.royail.o2o.entity;

import java.util.Date;

public class ProductImg {
	private Long 
					productImgId,productId;
	
	private String 
					imgAddr,imgDesc;
	
	private Integer 
					priority;
	
	private Date 
					createTime;
	
	
	
	
	public ProductImg() {
		
	}


	public ProductImg(Long productId, String imgAddr, String imgDesc, Integer priority, Date createTime) {
		super();
		this.productId = productId;
		this.imgAddr = imgAddr;
		this.imgDesc = imgDesc;
		this.priority = priority;
		this.createTime = createTime;
	}
	//setter&&getter
	
	
	public Integer getPriority() {
		return priority;
	}
	public Long getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
	
	
}

