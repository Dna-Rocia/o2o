package com.royail.o2o.enums;

public enum ProductCategoryStateEnum {
	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"操作失败"),
	NULL_SHOPID(-1002,"店铺ID为空"),
	EMPTY_LIST(-1003,"添加数少于1");
	
	
	private Integer state;
	private String stateName;
	
	//constructor
	private ProductCategoryStateEnum(int state,String stateName) {
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
