package com.royail.o2o.enums;

public enum ShopCategoryStateEnum {

	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"内部错误"),
	NULL_CATEGORYID(-1002,"categoryId为空"),
	NULL_CATEGORY(-1003,"category信息为空");
	
	
	
	private int state;
	private String stateName;
	
	//constructor
	 private ShopCategoryStateEnum(int state,String stateName) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
