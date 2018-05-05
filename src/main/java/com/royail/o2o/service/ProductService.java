package com.royail.o2o.service;

import java.util.List;

import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.exception.ProductOperationException;

public interface ProductService {

	ProductExecution insertProduct(Product product,ImageHolder imageHolder,List<ImageHolder> productImgs) throws ProductOperationException;
	
	
	Product findProductById(long productId);
	
	
	ProductExecution updateProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgs)throws ProductOperationException;
	
	
	ProductExecution listProduct(Product product, int pageIndex,  int pageSize);
	
	
	
	
}
		