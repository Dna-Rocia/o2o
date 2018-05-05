package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.Product;

public interface ProductDao {

	int insertProduct(Product product);
	
	
	int deleteProduct(@Param("productId") long productId, @Param("productCategoryId") long productCategoryId, @Param("shopId")long shopId);
	
	
	int updateProduct(Product product);
	
	/**
	 * 删除商品之前，将商品的类别置为空
	 * @param productCategoryId
	 * @return
	 */
	int updatePcToNull(long productCategoryId);
	
	
	Product findProduct(Long productId);
	
	
	List<Product> listProduct(@Param("product")Product product,@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	
	int listProductCount(@Param("product")Product product);
	
	
}
