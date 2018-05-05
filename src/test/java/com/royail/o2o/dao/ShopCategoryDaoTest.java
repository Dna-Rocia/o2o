package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	@Ignore
	public void AtestQueryShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.shopCategoryList(new ShopCategory());
		assertEquals(12, shopCategoryList.size());
		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(10l);
		testCategory.setParent(parentCategory);
		
		shopCategoryList = shopCategoryDao.shopCategoryList(testCategory);
		assertEquals(2, shopCategoryList.size());
		System.out.println(shopCategoryList.get(0).getShopCategoryName());
	}
	
	
	@Test
	public void BtestQueryShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.shopCategoryList(null);
		System.out.println(shopCategoryList.size());
	}
}
