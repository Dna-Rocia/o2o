package com.royail.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royail.o2o.dao.ShopCategoryDao;
import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.enums.ShopCategoryStateEnum;
import com.royail.o2o.service.ShopCategoryService;
import com.royail.o2o.utils.PageCalculatorUtils;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Override
	public ShopCategoryExecution shopCategoryList(ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
		
		int categoryLength = shopCategoryDao.listShopCategoryCount(shopCategoryCondition);
		
		ShopCategoryExecution execution = new ShopCategoryExecution();
		
		if (categoryLength > 0) {
			//int rowIndex = PageCalculatorUtils.calculatorRowIndex(pageIndex, pageSize);
			List<ShopCategory>  shopCategories = shopCategoryDao.shopCategoryList(shopCategoryCondition);
			if (shopCategories != null) {
				execution.setShopCategories(shopCategories);
			}
			execution.setCount(categoryLength);
		}else {
			execution.setState(ShopCategoryStateEnum.INNER_ERROR.getState());
		}
		
		return execution;
	}
	
	
	@Override
	public ShopCategoryExecution listShopCategory(ShopCategory shopCategoryCondition) {
		int categoryLength = shopCategoryDao.listShopCategoryCount(shopCategoryCondition);
		
		ShopCategoryExecution execution = new ShopCategoryExecution();
		
		if (categoryLength > 0) {
			List<ShopCategory>  shopCategories = shopCategoryDao.shopCategoryList(shopCategoryCondition);
			if (shopCategories != null) {
				execution.setShopCategories(shopCategories);
			}
			execution.setCount(categoryLength);
		}else {
			execution.setState(ShopCategoryStateEnum.INNER_ERROR.getState());
		}
		
		return execution;
	}

	
	
	
	
	
	
}
