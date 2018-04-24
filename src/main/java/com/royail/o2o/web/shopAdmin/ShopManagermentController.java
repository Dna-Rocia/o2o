package com.royail.o2o.web.shopAdmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ShopExecution;
import com.royail.o2o.entity.Area;
import com.royail.o2o.entity.PersonInfo;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.entity.ShopCategory;
import com.royail.o2o.enums.ShopStateEnum;
import com.royail.o2o.exception.ShopOperationException;
import com.royail.o2o.service.AreaService;
import com.royail.o2o.service.ShopCategoryService;
import com.royail.o2o.service.ShopService;
import com.royail.o2o.utils.CodeUtil;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagermentController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/**
	 * 初始化店铺信息
	 * @return
	 */
	@RequestMapping(value = "/shop/initInfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = (List<ShopCategory>) shopCategoryService.shopCategoryList(new ShopCategory(),0,10);
			areaList = areaService.queryArea();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	/**
	 * 查找店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shop/find", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> findShop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		if (shopId > -1) {
			try {

				Shop shop = shopService.findShop(shopId);
				List<Area> aList = areaService.queryArea();
				modelMap.put("shop", shop);
				modelMap.put("areaList", aList);
				modelMap.put("success", true);

			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	
	
	/**
	 * 注册店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shop/register", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();

		if (!CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "请输入正确的验证码");
			return map;
		}

		// 1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtils.getString(request, "shopStr");

		ObjectMapper mapper = new ObjectMapper();

		Shop shop = null;

		try {
			shop = mapper.readValue(shopStr, Shop.class);

		} catch (Exception e) {

			map.put("success", false);
			map.put("error", e.getMessage());
			return map;

		}
		CommonsMultipartFile shopImg = null;

		// 上传文件解析器

		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (resolver.isMultipart(request)) {

			MultipartHttpServletRequest mHttpServletRequest = (MultipartHttpServletRequest) request;

			shopImg = (CommonsMultipartFile) mHttpServletRequest.getFile("shopImg");

		} else {

			map.put("success", false);
			map.put("errMsg", "上传图片不能为空");
			return map;

		}

		// 2.注册店铺

		if (shop != null && shopImg != null) {
			// session 注册店铺的话就必须先登录
			PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(personInfo);
			ShopExecution shopExecution = null;
			try {
			
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				
				shopExecution = shopService.insertShop(shop, imageHolder);
				
				if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
					map.put("success", true);

					@SuppressWarnings("unchecked")
					List<Shop> shops = (List<Shop>) request.getSession().getAttribute("shopList");

					if (shops == null || shops.size() == 0) {
						shops = new ArrayList<Shop>();
					}
					shops.add(shopExecution.getShop());
					request.getSession().setAttribute("shopList", shops);

				} else {
					map.put("success", false);
					map.put("errMsg", shopExecution.getStateName());
				}

			} catch (ShopOperationException | IOException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				e.printStackTrace();
			}
			return map;
		} else {
			map.put("success", false);
			map.put("errMsg", "请输入店铺信息");
			return map;

		}
	}

	
	
	/**
	 * 更改店铺
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/shop/update", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateShop(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();

		if (!CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "请输入正确的验证码");
			return map;
		}

		// 1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtils.getString(request, "shopStr");

		ObjectMapper mapper = new ObjectMapper();

		Shop shop = null;

		try {
		
			
			shop = mapper.readValue(shopStr, Shop.class);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("error", e.getMessage());
			return map;

		}
		CommonsMultipartFile shopImg = null;

		// 上传文件解析器

		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (resolver.isMultipart(request)) {

			MultipartHttpServletRequest mHttpServletRequest = (MultipartHttpServletRequest) request;

			shopImg = (CommonsMultipartFile) mHttpServletRequest.getFile("shopImg");

		}

		// 2.修改店铺

		if (shop != null && shop.getShopId() != null) {
			ShopExecution shopExecution = null;
			try {
				if (shopImg == null) {
					shopExecution = shopService.updateShop(shop,  null);
				} else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
					shopExecution = shopService.updateShop(shop, imageHolder);
				}

				if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else {
					map.put("success", false);
					map.put("errMsg", shopExecution.getStateName());
				}

			} catch (ShopOperationException | IOException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				e.printStackTrace();
			}
			return map;
		} else {
			map.put("success", false);
			map.put("errMsg", "请输入店铺Id");
			return map;

		}
	}
	
	
	
	
	/**
	 * 店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shop/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String , Object> listShop(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo)request.getSession().getAttribute("user");

		try {
			Shop shop = new Shop();
			shop.setOwner(user);
			ShopExecution execution = shopService.listShop(shop, 0, 10);
			map.put("shopList", execution.getShops());
			map.put("user", user);
			map.put("shopCount", execution.getCount());
			map.put("success", true);
		
		
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
		}
	
		return map;	
	}
	
	
	/**
	 * 对于session中，店铺应有具体哪些操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shopmanager/info", method = RequestMethod.GET)
	@ResponseBody
	private Map<String , Object> listShopManager(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		long  shopId = HttpServletRequestUtils.getLong(request, "shopId");
		
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			
			if (currentShopObj == null) { 
				map.put("redirect", true);
				map.put("url", "/o2o/shopadmin/shoplist"); 
			}else {
				Shop currentShop = (Shop)currentShopObj;
				map.put("redirect", false);
				map.put("shopId", currentShop.getShopId());
			}
			
		}else {
			Shop currentShop  = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			map.put("redirect", false);
		}
		
		return map;
		
	}
	
	

	private static void inputStreamToFile(InputStream inputstream, File file) {

		OutputStream outputStream = null;

		try {

			outputStream = new FileOutputStream(file);

			int bytesRead = 0;

			byte[] buffer = new byte[1024];

			while ((bytesRead = inputstream.read(buffer)) != -1) {

				outputStream.write(buffer, 0, bytesRead);

			}

		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}

				if (inputstream != null) {
					inputstream.close();
				}

			} catch (Exception e2) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常: " + e2.getMessage());
			}
		}
	}

}
