package com.royail.o2o.dto;

import java.util.List;

import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ShopStateEnum;

public class ShopExecution {
	//结果状态
	private int state;
	
	//状态标识
	private String stateName;
	
	//店铺数量
	private int count;
	
	private Shop shop;
	
	private List<Shop> shops;
	
	
	

	public ShopExecution() {
		
	}

	//店铺操作失败构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
	}
	
	
	//店铺操作成功构造器（单一店铺）
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.shop = shop;
	}
	
	//店铺操作成功构造器（多个店铺）
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shops) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.shops = shops;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
	
	
	
	
	
	
	
	
	

}
