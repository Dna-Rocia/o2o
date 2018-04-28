package com.royail.o2o.entity;

import java.util.Date;
import java.util.List;

public class Product {
	

	private Long 
					productId;
	
	//imgAddr: 简略图
	private String 
					productName, productDesc,imgAddr, normalPrice, promotionPrice;
	
	//enableStatus:0.下架     1.在前端展示系统展示
	private Integer 
					priority,enableStatus;
	
	private Date
					createTime, lastEditTime;
	

	private List<ProductImg> 
					productImgList;
	
	private ProductCategory 
					productCategory;
	private Shop 
					shop;
	
	
	
	public Product() {
		
	}	
	
	
	
	
	
	
	public Product(Long productId, String productName, String productDesc, String imgAddr, String normalPrice,
			String promotionPrice, Integer priority, Integer enableStatus,  ProductCategory productCategory, Shop shop) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.imgAddr = imgAddr;
		this.normalPrice = normalPrice;
		this.promotionPrice = promotionPrice;
		this.priority = priority;
		this.enableStatus = enableStatus;
		this.productCategory = productCategory;
		this.shop = shop;
	}






	//setter&&getter
	
	

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}
	public String getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
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
	public List<ProductImg> getProductImgList() {
		return productImgList;
	}
	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
	
	
	
	

}
