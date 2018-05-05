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
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.entity.ProductImg;
import com.royail.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{

	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	@Ignore
	public void AinsertProductTest() {
		
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
	
	
	
	@Test
	@Ignore
	public void BfindProductById() throws Exception{
		
		long productId = 12;
		List<ProductImg> productImgs = new ArrayList<>();
		productImgs.add(new ProductImg(productId, "图1", "第一张图", 1, new Date()));
		productImgs.add(new ProductImg(productId, "图2", "第二张图", 2, new Date()));
		
		
		int num = productImgDao.insertProductImg(productImgs);
		assertEquals(2, num);
		
		
		Product product = productDao.findProduct(productId);
		assertEquals(10, product.getProductImgList().size());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+product.getProductImgList().get(0).toString());
		
		num = productImgDao.deleteImgByProductId(productId);
		assertEquals(10, num);
	}
	
	
	@Test
	@Ignore
	public void CupdateProduct()throws Exception{
		Product product = new Product(5l, "jnCe测试商品1", "测试商品的描述",null, null, null, 5, null,  new ProductCategory(9l), new Shop(1l));
		
		int num = productDao.updateProduct(product);
			
		assertEquals(1, num);
	}
	
	
	
	@Test
	@Ignore
	public void DlistProductCategory() throws Exception{
		
		Product product = new Product();
		List<Product> list = productDao.listProduct(product, 0, 10);
		assertEquals(10, list.size());
		
		
		int count = productDao.listProductCount(product);
		assertEquals(13, count); 
		
		
		product.setProductName("测试");
		product.setShop(new Shop(1l));
		 list = productDao.listProduct(product, 0, 10);
		
		assertEquals(6, list.size());
		
		
		int num = productDao.listProductCount(product);
		assertEquals(6, num);
		
	}

	@Test
	public void EupdateProductCategoryToNull(){
		//将productCategoryId为28的商品类别下面的商品的商品类别置为空
		
		int num = productDao.updatePcToNull(9l);
		
		assertEquals(5, num);
		
	}
	
	
	
}
