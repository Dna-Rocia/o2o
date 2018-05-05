package com.royail.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royail.o2o.dao.ProductCategoryDao;
import com.royail.o2o.dao.ProductDao;
import com.royail.o2o.dto.ProductCategoryExecution;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.enums.ProductCategoryStateEnum;
import com.royail.o2o.exception.ProductCategoryException;
import com.royail.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;
	
	
	@Override
	public ProductCategoryExecution listProductCategory(ProductCategory productCategory) {
		
		int categoryLength = productCategoryDao.listProductCategoryCount(productCategory);
		
		ProductCategoryExecution execution = new ProductCategoryExecution();
		
		if (categoryLength > 0) {
		
			List<ProductCategory> productCategories = productCategoryDao.listProductCategory(productCategory);
			if (productCategories != null) {
				execution.setProductCategories(productCategories);
			}
			execution.setCount(categoryLength);
		}else {
			execution.setState(ProductCategoryStateEnum.INNER_ERROR.getState());
		}
		
		return execution;	
	}


	@Override
	public ProductCategoryExecution insertProductCategory(List<ProductCategory> productCategories)
			throws ProductCategoryException {
		
		
		if (productCategories != null && productCategories.size() > 0) {
			
			try {
				int num = productCategoryDao.insertProductCategorys(productCategories);
				
				
				if (num <= 0) {
					throw new ProductCategoryException("商品分类创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
				
				
			} catch (Exception e) {
				throw new ProductCategoryException("批量创建商品类别运行错误："+e.getMessage());
			}
		
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
		
	}


	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryException {
		//接触tb_product 里的商品  与该productCategoryId的关联   ======= 将此商品类别下的商品 的    类别ID 置为空
		try {
			int num = productDao.updatePcToNull(productCategoryId);
			if (num < 0) {
				throw new ProductCategoryException("商品类别删除失败");
			}
		} catch (Exception e) {
			throw new ProductCategoryException("deleteProductCategory error:"+e.getMessage());
		}
		
		//删除该productCategory
		try {
			int  num = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (num <= 0) {
				throw new ProductCategoryException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
			
		} catch (Exception e) {
			throw new ProductCategoryException("商品类别删除  error:"+e.getMessage());
		}
	}

	
	
	
	
	
	
	
}
