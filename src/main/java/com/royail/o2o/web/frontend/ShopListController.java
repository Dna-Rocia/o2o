package com.royail.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.dto.ShopExecution;
import com.royail.o2o.entity.Area;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.service.AreaService;
import com.royail.o2o.service.ShopCategoryService;
import com.royail.o2o.service.ShopService;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping(value="/frontend")
public class ShopListController {
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;
	
	
	
	/**
	 * 返回商品列表的一级或者二级category，以及区域信息列表
	 */
	@RequestMapping(value="/shopspage/listinfo" , method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		//从前端获取请求中获取parentId
		long parentId = HttpServletRequestUtils.getLong(request,"parentId");
		
		ShopCategoryExecution execution = null ;
		
		if (parentId != -1) {
			//如果parentId存在的话，则取出该一级shopCategory下的二级shopCategory列表       也说明不是最大的分类
			try {
				
			ShopCategory parentCategory = new ShopCategory(), childCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			 execution = shopCategoryService.listShopCategory(childCategory);
			
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
			}
		}else {
			
			try {
				//如果parentId不存在，则取出所有一级shopCategory（用户在首页选择的是全部商店列表）
				execution  =  shopCategoryService.listShopCategory(null);
			} catch (Exception e) {
				
				map.put("success", false);
				map.put("errMsg", e.getMessage());
			}

		
		}
		
		map.put("shopCategoryList", execution.getShopCategories());
		
		List<Area> areaList = null;
		
		try {
			areaList = areaService.queryArea();
			map.put("areaList", areaList);
			map.put("success", true);
			return map;
			
		} catch (Exception e) {
			
			map.put("success", false);
			map.put("errMsg", e.getMessage());
		}
		
		return map;
		
		
	}
	
	
	/**
	 * 获取指定查询条件下的店铺列表
	 */
	@RequestMapping(value="/shops/list" , method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取页码
		int pageIndex =  HttpServletRequestUtils.getInt(request, "pageIndex");
		//获取一页需要多少条数据
		int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
		//进行一个非空判断
		if ((pageIndex > -1) && (pageSize > -1)) {
			
			//获取一级类别的Id
			long parentId = HttpServletRequestUtils.getLong(request, "parentId");
			//获取二级类别的id
			long shopCategoryId = HttpServletRequestUtils.getLong(request, "shopCategoryId");
			//试着获取区域ID
			long  areaId = HttpServletRequestUtils.getLong(request, "areaId");
			//获取店铺的name进行模糊查询
			String shopName = HttpServletRequestUtils.getString(request,"shopName");
			//将上述的条件进行一个整合
			Shop shop = compactShopConditionSearch(parentId,shopCategoryId,areaId,shopName);
			
			ShopExecution execution = shopService.listShop(shop, pageIndex, pageSize);
			
			map.put("shopList", execution.getShops());
			map.put("count", execution.getCount());
			map.put("success", true);
			
		}else {

			map.put("success", false);
			map.put("errMsg","empty pageIndex or pageSize");
		}
		
		return  map;
	}
	
	
	/**
	 * 辅助方法
	 */
	private Shop compactShopConditionSearch(long parentId ,long shopCategoryId , long areaId ,String shopName) {
		Shop shop = new Shop();
		
		//查询某个一级shopCategory下面的所有二级shopCategory里面的店铺列表
		if (parentId != -1l) {
			ShopCategory parentCategory = new ShopCategory(), childCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shop.setShopCategory(childCategory);
			
		}
		
		//查询某个二级shopCategory下面的列表
		if (shopCategoryId != -1l) {
			ShopCategory category = new ShopCategory();
			category.setShopCategoryId(shopCategoryId);
			shop.setShopCategory(category);
			
		}
		
		
		if (areaId != -1l) {
			Area area = new Area();
			area.setAreaId(areaId);
			shop.setArea(area);
		}
		
		
		if (shopName != null &&!shopName.equals("")) {
			shop.setShopName(shopName);
		}
		
		//只能操作可操作(审核通过)的店铺
		shop.setEnableStatus(1);
		
		return shop;
		
	}
	
	
	
	
	
	
	
	

}
