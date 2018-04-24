package com.royail.o2o.service;

import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.entity.ShopCategory;

public interface ShopCategoryService {

	ShopCategoryExecution shopCategoryList( ShopCategory shopCategoryCondition, int pageIndex, int PageSize);
	
	
}
