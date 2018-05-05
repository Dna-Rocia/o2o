package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	
	List<ProductCategory> listProductCategory(@Param("productCategoryCondition") ProductCategory productCategory);
	
	
	int listProductCategoryCount(@Param("productCategoryCondition") ProductCategory productCategory);
	
	
	int  insertProductCategorys(List<ProductCategory> productCategories);
	
	
	int  insertProductCategory(ProductCategory productCategorie);
	
	
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
}
