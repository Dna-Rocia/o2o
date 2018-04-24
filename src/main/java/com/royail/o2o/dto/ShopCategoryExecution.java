package com.royail.o2o.dto;

import java.util.List;


import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.enums.ShopCategoryStateEnum;

public class ShopCategoryExecution extends BaseExecution {
	
	private ShopCategory shopCategory;
	
	private List<ShopCategory> shopCategories;
	
	
	
	public ShopCategoryExecution(){
		
	}
	
	
	//店铺分类操作失败构造器
	public ShopCategoryExecution(ShopCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
	}
	
	
	//店铺分类操作成功构造器（单一店铺类别）
	public ShopCategoryExecution(ShopCategoryStateEnum stateEnum,ShopCategory shopCategory) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.shopCategory = shopCategory;
	}
	
	//店铺分类操作成功构造器（多个店铺类别）
	public ShopCategoryExecution(ShopCategoryStateEnum stateEnum,List<ShopCategory> shopCategories) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.shopCategories = shopCategories;
	}

	

	
	
	

	//=====================setter&&getter
	



	public ShopCategory getShopCategory() {
		return shopCategory;
	}


	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}


	public List<ShopCategory> getShopCategories() {
		return shopCategories;
	}


	public void setShopCategories(List<ShopCategory> shopCategories) {
		this.shopCategories = shopCategories;
	}

	

	
	
	
	
	
	
	
	
}
