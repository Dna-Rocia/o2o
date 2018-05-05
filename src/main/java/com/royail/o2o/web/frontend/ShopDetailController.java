package com.royail.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royail.o2o.dto.ProductCategoryExecution;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.service.ProductCategoryService;
import com.royail.o2o.service.ProductService;
import com.royail.o2o.service.ShopService;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping(value="/frontend")
public class ShopDetailController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 获取列表信息以及该店铺下面的商品类别列表
	 */
	@RequestMapping(value="/shopdetail/pageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//获取前端传过来的shopId
		long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		Shop shop = null;
		ProductCategoryExecution execution = null;
		
		if (shopId != -1) {
			//查询店铺信息
			shop = shopService.findShop(shopId);
			map.put("shop", shop);
			
			//获取店铺下的商品列表
			ProductCategory productCategory = new ProductCategory();
			productCategory.setShopId(shopId);
			execution = productCategoryService.listProductCategory(productCategory);
			map.put("productCategoryList", execution.getProductCategories());
			map.put("success", true);
			
		}else {
			map.put("success", false);
			map.put("errMsg", "empty shopId");
			
		}
	
		return map;		
	}
	
	
	
	/**
	 * 根据查询条件分页列出该店铺下的所有商品
	 */
	@RequestMapping(value="/productbyshop/list" , method = RequestMethod.GET)
	@ResponseBody
	private Map<String , Object> listProductByShop(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//获取页码
		int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
		//获取展示条数
		int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
		//获取店铺id
		long shopId  = HttpServletRequestUtils.getLong(request, "shopId");
		
		if (pageIndex > -1 && pageSize > -1 && shopId > -1) {
			//获取商品类别Id
			long productCategoryId = HttpServletRequestUtils.getLong(request, "productCategoryId");
			
			//获取商品名称
			String productName = HttpServletRequestUtils.getString(request, "productName");
			
			Product product = compactShopConditionSearch(shopId,productCategoryId,productName);
			
			ProductExecution execution = productService.listProduct(product, pageIndex, pageSize);
			
			map.put("productList", execution.getProducts());
			map.put("count", execution.getCount());
			map.put("success", true);
			
			
		}else {
			map.put("success", false);
			map.put("errMsg", "empty shopId or pageIndex or pageSize");
			
		}
		
		return map;
		
	}
	
	
	/**
	 *  整合查询商品的条件
	 * @param shopId  店铺的ID  【必填】
	 * @param productCategoryId 商品类别的Id
	 * @param productName  商品名称
	 * @return 商品对象
	 */
	private Product compactShopConditionSearch(long shopId,long productCategoryId,String productName) {
	
		Product product = new Product();
		
		product.setShop(new Shop(shopId));
		
		if (productCategoryId != -1l) {
			//查询某个商品类别下的商品列表
			product.setProductCategory(new ProductCategory(productCategoryId));
			
		}
		if (productName != null) {
			//查询某个商品名称的商品名称列表
			product.setProductName(productName);
		}
		//只允许选出上架的商品
		product.setEnableStatus(1);
		return product;
		
	}
	
	
	
	
}
