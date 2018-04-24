package com.royail.o2o.enums;

public enum ProductStateEnum {
	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY(-1003,"商品数据为空");
	
	
	private Integer state;
	private String stateName;
	
	//constructor
	private ProductStateEnum(int state,String stateName) {
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
