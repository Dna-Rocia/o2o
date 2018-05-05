package com.royail.o2o.service;

import java.util.List;

import com.royail.o2o.dto.ProductCategoryExecution;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.exception.ProductCategoryException;

public interface ProductCategoryService {

	
	ProductCategoryExecution listProductCategory(ProductCategory productCategory );
	
	ProductCategoryExecution insertProductCategory(List<ProductCategory> productCategories) throws ProductCategoryException;

	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryException;
	

	
}

