package com.royail.o2o.service;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ShopExecution;
import com.royail.o2o.entity.Area;
import com.royail.o2o.entity.PersonInfo;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.enums.ShopStateEnum;
import com.royail.o2o.exception.ShopOperationException;

public class ShopServiceTest extends BaseTest{

	@Autowired
	private ShopService shopService;
	
	
	@Test
	public void testListShop() {
		Shop shop = new Shop();
		PersonInfo info = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		
		info.setUserId(1l);
		shop.setOwner(info);

		shopCategory.setShopCategoryId(29l);
		shop.setShopCategory(shopCategory);
	
		ShopExecution execution = shopService.listShop(shop, 2, 3);
		System.out.println("条件1：用户ID为1的\t\t\t当前查的店铺有： "+execution.getShops().size() +" 家 \t\t\t总共拥有店铺"+execution.getCount()+" 家");
		
		
/*		List<Shop> shops1 = shopDao.listShop(shop, 0, 3);
		int shopCount1 = shopDao.listShopCount(shop);
		System.out.println("===================================\n条件2：分类id为29的\n当前查的店铺有： "+shops1.size() +" 家 \n总共拥有店铺"+shopCount1+" 家");
*/	
	}
	
	
	
	
	
	@Test
	@Ignore
	public void  testUpdateShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(31l);
		shop.setShopName("宜家");
		File file = new File("D:/image/lufei.jpg");
		InputStream inputStream = new FileInputStream(file);
		ImageHolder imageHolder = new ImageHolder("lufei.jpg", inputStream);
		
		ShopExecution execution = shopService.updateShop(shop,imageHolder);
		
		System.out.println("新的图片地址："+execution.getShop().getShopImg());
		
		
	}
	
	
	
	
	
	@Test
	@Ignore
	public void testInsertShop() throws FileNotFoundException {
		
		PersonInfo info = new PersonInfo();
		Area area = new Area();
		ShopCategory category = new ShopCategory();
		
		
		info.setUserId(1l);
		area.setAreaId(2);
		category.setShopCategoryId(18l);
		
		Shop shop = new Shop("insert",0);
		shop.setArea(area);
		shop.setOwner(info);
		shop.setShopCategory(category);
		
		File shopimg = new File("D:/image/lufei.jpg");
	
		InputStream is = new FileInputStream(shopimg);
		
		ImageHolder imageHolder = new ImageHolder(shopimg.getName(), is);
		
		ShopExecution execution = shopService.insertShop(shop, imageHolder );
		
		assertEquals(ShopStateEnum.CHECK.getState(),execution.getState());
		
		
	}
	
	
	
	
}
