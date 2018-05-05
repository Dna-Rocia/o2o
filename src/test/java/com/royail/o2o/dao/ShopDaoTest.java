package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.Area;
import com.royail.o2o.entity.PersonInfo;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testListShop() {
		Shop shop = new Shop();
		PersonInfo info = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();

		info.setUserId(1l);
		shop.setOwner(info);

		shopCategory.setShopCategoryId(29l);
		shop.setShopCategory(shopCategory);
	
		List<Shop> shops = shopDao.listShop(shop, 0, 5);
		int shopCount = shopDao.listShopCount(shop);
		System.out.println("条件1：用户ID为1的\t\t\t当前查的店铺有： "+shops.size() +" 家 \t\t\t总共拥有店铺"+shopCount+" 家");
		
		
/*		List<Shop> shops1 = shopDao.listShop(shop, 0, 3);
		int shopCount1 = shopDao.listShopCount(shop);
		System.out.println("===================================\n条件2：分类id为29的\n当前查的店铺有： "+shops1.size() +" 家 \n总共拥有店铺"+shopCount1+" 家");
*/	
		
	}
	
	@Test
	public void testListShop2() {
		Shop shopCondition = new Shop();
		ShopCategory parentCategory = new ShopCategory();
		ShopCategory childCategory = new ShopCategory();
		
		parentCategory.setShopCategoryId(11l);
		childCategory.setParent(parentCategory);
		
		shopCondition.setShopCategory(childCategory);
		
	
		List<Shop> shops = shopDao.listShop(shopCondition, 0, 6);
		int shopCount = shopDao.listShopCount(shopCondition);
		System.out.println("店铺列表的大小： "+shops.size() +" 家 \t\t\t总共店铺"+shopCount+" 家");
		
	
		
	}
	
	
	
	
	
	@Test
	@Ignore
	public void  testFindShop() {
		long shopId = 31;
		Shop shop = shopDao.findShop(shopId);
		
		System.out.println(shop.toString());

	}
	
	
	
	@Test
	@Ignore
	public void testInsertShop() {
		
		PersonInfo info = new PersonInfo();
		Area area = new Area();
		ShopCategory category = new ShopCategory();
		
		
		info.setUserId(1l);
		area.setAreaId(2l);
		category.setShopCategoryId(1l);
		
		Shop shop = new Shop("insert",0);
		shop.setArea(area);
		shop.setOwner(info);
		shop.setShopCategory(category);
		
			
		int insertNum = shopDao.insertShop(shop);
		assertEquals(1,insertNum);
		
		
	}
	
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop("",19);
		
		int updateNum = shopDao.updateShop(shop);
		
		assertEquals(1,updateNum);
		
		
	}
	
	
	
}
