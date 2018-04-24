package com.royail.o2o.web.shopAdmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ShopCategoryController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	/**
	 * 店铺类别列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shopcategory/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String , Object> listShop(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			ShopCategory shopCategoryCondition = new ShopCategory();
			
			ShopCategoryExecution execution = shopCategoryService.shopCategoryList(shopCategoryCondition, 0, 10);
			map.put("shopCategoryList", execution.getShopCategories());
			map.put("shopCategoryCount", execution.getCount());
			map.put("success", true);
		
		
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
		}
	
		return map;	
	}
	
	
	
	
	
	
	
}
