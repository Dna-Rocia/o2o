package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.ShopCategory;

public interface ShopCategoryDao {

	//List<ShopCategory> shopCategoryList(@Param("shopCategoryCondition") ShopCategory shopCategory,@Param("rowIndex") int rowIndex, @Param ("pageSize") int PageSize);
	List<ShopCategory> shopCategoryList(@Param("shopCategoryCondition") ShopCategory shopCategory);

	
	int listShopCategoryCount(@Param("shopCategoryCondition") ShopCategory shopCategory);
	
	
}
