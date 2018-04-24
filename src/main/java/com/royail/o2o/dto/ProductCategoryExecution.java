package com.royail.o2o.dto;

import java.util.List;

import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution extends BaseExecution{

	private ProductCategory productCategory;
	
	private List<ProductCategory> productCategories;
	

	public ProductCategoryExecution() {
	
	}	
	
	
	//产品分类操作失败构造器
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
	}
	
	
	//产品分类操作成功构造器（单一店铺类别）
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,ProductCategory shopCategory) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.productCategory = shopCategory;
	}
	
	//产品分类操作成功构造器（多个店铺类别）
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> shopCategories) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.productCategories = shopCategories;
	}


	public ProductCategory getProductCategory() {
		return productCategory;
	}


	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}


	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}


	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	

	
	
	
	
}
