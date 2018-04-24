package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	
	@Test
	@Ignore
	public void  listTest() {
		
		ProductCategory productCategory = new ProductCategory();
		productCategory.setShopId(1l);
		
		int length = productCategoryDao.listProductCategoryCount(productCategory);
		
		System.out.println("长度： "+length);
		
		List<ProductCategory> productCategories = productCategoryDao.listProductCategory(productCategory);
		
		for (ProductCategory productCategory2 : productCategories) {
			System.out.println(productCategory2.toString());
		}
	
	}

	
	
	
	@Test
	@Ignore
	public void insertsTest(){
		
		List<ProductCategory> pList = new ArrayList<>();
		
		pList.add(new ProductCategory(31l, "直发", 30, new Date()));
		
		
		pList.add(new ProductCategory(31l, "离子烫", 29, new Date()));
		
		
		pList.add(new ProductCategory(31l, "染发", 27, new Date()));
		
		
		int num = productCategoryDao.insertProductCategorys(pList);
		
		
		assertEquals(3, num);
	
		
	}
	
	@Test
	public void deleteTest(){
		
		long shopId = 1l;
		long  productCategoryId = 33l;
		
		int num = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
		
		
		assertEquals(1, num);
	
		
	}
	
	
	
}
