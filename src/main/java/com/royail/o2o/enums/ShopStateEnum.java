package com.royail.o2o.enums;

public enum ShopStateEnum {
	
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部错误"),
	NULL_SHOPID(-1002,"shopId为空"),NULL_SHOP(-1003,"shop信息为空");
	
	
	
	
	
	
	
	
	
	
	
	private int state;
	private String stateName;
	
	//constructor
	private ShopStateEnum(int state,String stateName) {
		this.state = state;
		this.stateName = stateName;
	}
	
	
	//start ================== getter
	
	public int getState() {
		return state;
	}


	public String getStateName() {
		return stateName;
	}



	//end =============================getter
	
	
	
	public static ShopStateEnum stateOf(int state) {
		
		for (ShopStateEnum  stateEnum : values()) {
			
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
			
		}
		return null;
		
	}


	
	
	
	
	
	
	

}
