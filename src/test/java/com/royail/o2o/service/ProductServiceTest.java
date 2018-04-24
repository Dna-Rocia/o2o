package com.royail.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ProductStateEnum;
import com.royail.o2o.exception.ShopOperationException;

public class ProductServiceTest extends BaseTest{

	@Autowired
	private ProductService productService;
	
	@Test
	public void  insertProduct() throws ShopOperationException,FileNotFoundException {
		
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(19l);
		ProductCategory productCategory = new ProductCategory();
		
		productCategory.setProductCategoryId(28l);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setProductName("商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(3);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		
		//创建缩略图文件流
		File file = new File("D://image//watermark.jpg");
		FileInputStream inputStream = new FileInputStream(file);
		ImageHolder imageHolder = new ImageHolder(file.getName(),inputStream);
		
		//创建商品详情图片流
		File file1 = new File("D://image//lufei.jpg");
		FileInputStream inputStream1 = new FileInputStream(file1);
		File file2 = new File("D://image//face.jpg");
		FileInputStream inputStream2 = new FileInputStream(file2);
		
		List<ImageHolder> list = new ArrayList<ImageHolder>();
		list.add(new ImageHolder(file1.getName(), inputStream1));		
		list.add(new ImageHolder(file2.getName(), inputStream2));	
		
		ProductExecution execution = productService.insertProduct(product, imageHolder, list);
		
		assertEquals(ProductStateEnum.SUCCESS.getState(),execution.getState());
		
	}
	
	
	
	
	
	
}
