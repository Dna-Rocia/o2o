package com.royail.o2o.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.entity.ShopCategory;

public class ShopCategoryServiceTest extends BaseTest {

	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	
	@Test
	public void testListShopCategory() {
		ShopCategory parentCategory = new ShopCategory(), childCategory = new ShopCategory();
		parentCategory.setShopCategoryId(27l);
		childCategory.setParent(parentCategory);
		
//		 execution = shopCategoryService.listShopCategory(childCategory);

		ShopCategoryExecution execution = shopCategoryService.shopCategoryList(childCategory,0, 10);
		System.out.println("条件1：用户ID为1的\t\t\t当前查的店铺有： "+execution.getShopCategories().size() +" 家 \t\t\t总共拥有店铺"+execution.getCount()+" 家");
		
		
/*		List<Shop> shops1 = shopDao.listShop(shop, 0, 3);
		int shopCount1 = shopDao.listShopCount(shop);
		System.out.println("===================================\n条件2：分类id为29的\n当前查的店铺有： "+shops1.size() +" 家 \n总共拥有店铺"+shopCount1+" 家");
*/	
	}
	
	
	
	
	
}
