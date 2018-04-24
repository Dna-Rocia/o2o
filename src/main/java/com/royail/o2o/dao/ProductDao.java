package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.Product;

public interface ProductDao {

	int insertProduct(Product product);
	
	
	int deleteProduct(@Param("productId") long productId, @Param("productCategoryId") long productCategoryId, @Param("shopId")long shopId);
	
	
	//int updateProduct(Product product);
	
	
	//Product findProduct(Product product);
	
	
	//List<Product> listProduct(@Param("product")Product product,@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
	
	
	//int listProductCount(@Param("product")Product product);
	
	
}
