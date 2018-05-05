package com.royail.o2o.web.shopAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ProductCategoryExecution;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.ProductCategory;
import com.royail.o2o.entity.ProductImg;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ProductStateEnum;
import com.royail.o2o.exception.ProductCategoryException;
import com.royail.o2o.service.ProductCategoryService;
import com.royail.o2o.service.ProductService;
import com.royail.o2o.utils.CodeUtil;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;

	private static final int IMAGEMAXCOUNT = 6;


	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/product/insert", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> insertProduct(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 验证码检验
		if (!CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "输入了错误的验证码");
			return map;
		}
		
		// 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtils.getString(request, "productStr");
	/*	MultipartHttpServletRequest mHttpServletRequest = null; */// 用于处理文件流的
		ImageHolder thumbnail = null; // 用于处理缩略图的文件流
		List<ImageHolder> ImageHolders = new ArrayList<ImageHolder>();  // 用于保存商品详情图的文件流列表  以及对应的名字列表
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext()); // 从request中去获取文件流的
		
		try {
			// 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if (resolver.isMultipart(request)) {
				handleImage(request, thumbnail, ImageHolders);
			} else {
				map.put("success", false);
				map.put("errMsg", "上传图片不能为空");
				return map;
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}

		try {
			// 尝试获取前端传过来的表单string流并将其转换成product实体类
			product = mapper.readValue(productStr, Product.class);

		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}

		// 若product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
		if (product != null && thumbnail != null && ImageHolders.size() > 0) {

			try {
				// 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);

				// 执行添加操作
				ProductExecution execution = productService.insertProduct(product, thumbnail, ImageHolders);
				if (execution.getState() == ProductStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else {
					map.put("success", false);
					map.put("errMsg", execution.getStateName());
					return map;
				}
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}

		} else {
			map.put("success", false);
			map.put("errMsg", "请输入商品信息");
		}

		return map;

	}
	

	@SuppressWarnings("unused")
	@RequestMapping(value = "/product/update", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateProduct(HttpServletRequest request) {
			
		Map<String, Object> map = new HashMap<String, Object>();
	
		/**
		 * 商品的编辑有两种方法： ①上下架 （无需输入验证码）  ②直接编辑  （需要输入验证码）  
		 */
		boolean statusChange = HttpServletRequestUtils.getBoolean(request, "statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "输入了错误的验证码");
		}
		
		//接收前端传的参数变量的初始化   例：商品、缩略图、详情图
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> ImageHolders = new ArrayList<>();
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		
		//若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
		try {
			if (resolver.isMultipart(request)) {
				
				handleImage(request,thumbnail, ImageHolders);
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
	
		try {
			String productStr = HttpServletRequestUtils.getString(request, "productStr");
			//尝试获取前端传过来的表单String流并将其转化为product实体类
			product = mapper.readValue(productStr, Product.class);
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		
		//非空判断
		if (product != null) {
			try {
				//从session中获取当前店铺的id 赋值给product，减少对前端数据的依赖
				Shop shop = (Shop)request.getSession().getAttribute("currentShop");
				product.setShop(shop);
				//进行product数据的变更
				ProductExecution execution = productService.updateProduct(product, thumbnail, ImageHolders);
				if (execution.getState() == ProductStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				}else {
					map.put("success", false);
					map.put("errMsg", execution.getStateName());
				}
				
			} catch (RuntimeException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}else {
			map.put("success", false);
			map.put("errMsg", "请输入商品的信息...");
			
		}
		return map;
	}


	private void handleImage(HttpServletRequest request,ImageHolder thumbnail, List<ImageHolder> ImageHolders) throws IOException {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		
		//取出缩略图并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartHttpServletRequest.getFile("thumbnail");
		
		if (thumbnailFile != null ) {
			thumbnail =	new ImageHolder(thumbnailFile.getName(), thumbnailFile.getInputStream());
		}
		

		// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
		for (int i = 0; i < IMAGEMAXCOUNT; i++) {
			CommonsMultipartFile productFile = (CommonsMultipartFile) multipartHttpServletRequest .getFile("productImg" + i);
			if (productFile != null) {
				// 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
				ImageHolder pHolder = new ImageHolder(productFile.getOriginalFilename(),productFile.getInputStream());
				ImageHolders.add(pHolder);
			} else {
				// 若取出的第i个详情图片文件流为空，则终止循环
				break;
			}
		}
	}


	@RequestMapping(value = "/product/find", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> findProduct(@RequestParam Long productId) {
			
		Map<String, Object> map = new HashMap<String, Object>();

		if (productId > -1) {
			
			//获取商品信息
			Product product = productService.findProductById(productId);
			
			ProductCategory category = new ProductCategory(product.getShop().getShopId());
			
			category.setShopId(product.getShop().getShopId());
			//获取店铺下的类别信息
			ProductCategoryExecution pcExecution = productCategoryService.listProductCategory(category);
			
			map.put("product", product);
			map.put("productCategoryList", pcExecution.getProductCategories());
			map.put("success", true);
			
		}else {
			map.put("success", false);
			map.put("errMsg", "empty ProductId");
		}
		return map;
	}
	
	
	private Product compactProduct(long shopId,long productCategoryId,String productName) {
		Product product = new Product();
		product.setShop(new Shop(shopId));
		if (productCategoryId != -1l) {
			product.setProductCategory(new ProductCategory(productCategoryId));
		}
		
		if (productName != null) {
			product.setProductName(productName);
		}
		return product;
	}
	
	

	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProduct(HttpServletRequest request) {
			
		Map<String, Object> map = new HashMap<String, Object>();
		//当前页码
		int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
		//页码的上限
		int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
		//当前店铺
		Shop shop = (Shop)request.getSession().getAttribute("currentShop");
		
		if (shop != null && shop.getShopId() != null && pageIndex > -1 && pageSize > -1) {
			
			Long productCategoryId = HttpServletRequestUtils.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtils.getString(request, "productName");
			Product product = compactProduct(shop.getShopId(), productCategoryId, productName);
			
			ProductExecution execution = productService.listProduct(product, pageIndex, pageSize);
			map.put("productList", execution.getProducts());
			map.put("count", execution.getCount());
			map.put("success", true);
			
		}else {
			map.put("success", false);
			map.put("errMsg", "empty shop or pageIndex or pageSize");
		}
		
		return map;
	}
	
	
	
	
	
}