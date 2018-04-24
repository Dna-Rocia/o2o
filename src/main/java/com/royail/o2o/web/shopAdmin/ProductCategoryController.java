package com.royail.o2o.web.shopAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royail.o2o.dto.ProductCategoryExecution;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ProductCategoryStateEnum;
import com.royail.o2o.exception.ProductCategoryException;
import com.royail.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 商品类别列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productcategory/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductCategory(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

		if (currentShop != null && currentShop.getShopId() > 0) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setShopId(currentShop.getShopId());
			ProductCategoryExecution execution = productCategoryService.listProductCategory(productCategory);
			map.put("productCategoryList", execution.getProductCategories());
			map.put("productCategoryCount", execution.getCount());
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("errMsg", ProductCategoryStateEnum.INNER_ERROR);
		}
		return map;
	}

	
	
	
	
	/**
	 * 商品类别新增
	 * @param productCategories
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productcategory/insert", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> insertProductCategorys(@RequestBody List<ProductCategory> productCategories,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

		for (ProductCategory productCategory : productCategories) {
			productCategory.setShopId(currentShop.getShopId());
		}

		if (productCategories != null && productCategories.size() > 0) {

			try {

				ProductCategoryExecution pExecution = productCategoryService.insertProductCategory(productCategories);
				if (pExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else {
					map.put("success", false);
					map.put("errMsg", pExecution.getStateName());
				}

			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}

		} else {
			map.put("success", false);
			map.put("errMsg", "请至少输入一个商品类别");
		}
		return map;
	}
	
	
	/**
	 * 商品类别删除
	 * @param productCategories
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productcategory/delete", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> deleteProductCategorys(Long productCategoryId,HttpServletRequest request) {
		Map<String, Object> map = null;
		if (productCategoryId != null && productCategoryId > 0) {
			map = new HashMap<String, Object>();
			
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

				try {
					ProductCategoryExecution pExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
					
					if (pExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
						map.put("success", true);
					} else {
						map.put("success", false);
						map.put("errMsg", pExecution.getStateName());
					}

				} catch (ProductCategoryException e) {
					map.put("success", false);
					map.put("errMsg", e.getMessage());
					return map;
				}

			
		} else {
			map.put("success", false);
			map.put("errMsg", "请至少输入一个商品类别");
		}
		
		return map;
	}
	
	
	
	
	
	
	

}
