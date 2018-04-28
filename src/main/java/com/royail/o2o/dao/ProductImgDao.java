package com.royail.o2o.dao;

import java.util.List;

import com.royail.o2o.entity.ProductImg;

public interface ProductImgDao {

	
	int insertProductImg(List<ProductImg> productImgs);
	
	
	int deleteImgByProductId(long productId);
	
	
	List<ProductImg> listProductImg(long productId);
	
	
	
}
