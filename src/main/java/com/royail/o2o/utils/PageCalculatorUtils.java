package com.royail.o2o.utils;

public class PageCalculatorUtils {

	public static int calculatorRowIndex(int pageIndex,int pageSize) {
		
		return (pageIndex > 0)?(pageIndex -1) * pageSize : 0;
		
		
		
	}
	
	
	
	
	
	
	
	
}
