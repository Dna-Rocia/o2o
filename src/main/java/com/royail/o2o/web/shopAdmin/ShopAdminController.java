package com.royail.o2o.web.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin")
public class ShopAdminController {
	
	
	
	@RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
	public String  shopOperation() {
		
		return "shop/shopOperation";
		
	}

	
	
	
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String  shopList() {
		return "shop/shopList";
		
	}
	
	
	

	@RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
	public String  shopManagement() {
		return "shop/shopManagement";
		
	}
	
	
	
	@RequestMapping(value = "/productcategorylist", method = RequestMethod.GET)
	public String  productCategoryList() {
		return "shop/productCategory";
		
	}
	
	
	@RequestMapping(value = "/submit" , method = RequestMethod.POST)
	public String  productCategoryInsert() {
		return "shop/productCategory";
		
	}
	
	
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String  productCategoryDelete() {
		return "shop/productCategory";
		
	}
	
	
}
