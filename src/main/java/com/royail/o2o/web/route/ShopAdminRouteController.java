package com.royail.o2o.web.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin")
public class ShopAdminRouteController {

	/**
	 * shop的路由（转发）管理
	 */
	
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
	
	
	
	/**
	 * productCategory 的路由（转发）管理
	 */
	
	@RequestMapping(value = "/productcategorylist", method = RequestMethod.GET)
	public String  productCategoryList() {
		return RequestRouteManagement.SHOP_PRODUCT_CATEGORY.getFinalUrl();
		
	}
	
	
	@RequestMapping(value = "/categorysubmit" , method = RequestMethod.POST)
	public String  productCategoryInsert() {		
		return RequestRouteManagement.SHOP_PRODUCT_CATEGORY.getFinalUrl();
		
	}
	
	
	@RequestMapping(value = "/categorydelete", method = RequestMethod.POST)
	public String  productCategoryDelete() {
		return RequestRouteManagement.SHOP_PRODUCT_CATEGORY.getFinalUrl();
		
	}
	
	
	
	/**
	 * product 的路由（转发）管理
	 */
	@RequestMapping(value = "/productoperation", method = RequestMethod.GET)
	public String  productOperation() {
		return RequestRouteManagement.SHOP_PRODUCT.getFinalUrl();
		
	}
	
	@RequestMapping(value = "/productmanagement", method = RequestMethod.GET)
	public String  productList() {
		return RequestRouteManagement.SHOP_PRODUCT_LIST.getFinalUrl();
		
	}
	
	
	
	
}
