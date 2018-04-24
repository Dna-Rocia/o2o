package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.Shop;


public class ProductDaoTest extends BaseTest{

	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void insertProductTest() {
		
		Product product = new Product();
		
		product.setProductName("book1");
		product.setProductDesc("这是一本XXX的书");
		product.setImgAddr("upload\\item\\shop\\31\\2018041709521443006.jpg");
		product.setNormalPrice("2000");
		product.setPromotionPrice("2000");
		product.setPriority(2);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		Shop shop = new Shop();
		shop.setShopId(31l);
		product.setShop(shop);
		
		
		int num = productDao.insertProduct(product);
		
		assertEquals(1,num);
		
		
	}
	
	
	
	
	
}
