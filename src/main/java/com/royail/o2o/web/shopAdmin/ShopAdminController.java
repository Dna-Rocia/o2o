package com.royail.o2o.web.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin")
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
	public String  shopOperation() {
		return RequestRouteManagement.SHOP_OPERATION.getFinalUrl();
		
	}

	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String  shopList() {
		return RequestRouteManagement.SHOP_LIST.getFinalUrl();
	}
	
	
	@RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
	public String  shopManagement() {
		return RequestRouteManagement.SHOP_MANAGEMENT.getFinalUrl();
		
	}
	
	
	
	@RequestMapping(value = "/productcategorylist", method = RequestMethod.GET)
	public String  productCategoryList() {
		return RequestRouteManagement.SHOP_PRODUCT_CATEGORY_LIST.getFinalUrl();
		
	}
	
	
	@RequestMapping(value = "/submit" , method = RequestMethod.POST)
	public String  productCategoryInsert() {		
		return RequestRouteManagement.SHOP_PRODUCT_CATEGORY_SUBMIT.getFinalUrl();
		
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String  productCategoryDelete() {
		//转发商品类别的管理页面------删除
		return "shop/productCategory";
		
	}
	
	
}
