package com.royail.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royail.o2o.entity.Product;
import com.royail.o2o.service.ProductService;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping(value="/frontend")
public class ProductDetailController {

	@Autowired
	private ProductService productService;
	
	/**
	 * 根据商品ID 获取商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/productdetail/list" , method =RequestMethod.GET)
	@ResponseBody
	private Map<String , Object> listProductDetail(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取前台传来的productId
		long productId = HttpServletRequestUtils.getLong(request, "productId");
		Product product = null;
		
		if (productId != -1l) {
			
			//根据productId获取商品信息，里边包含了商品详情图列表
			product = productService.findProductById(productId);
			map.put("product", product);
			map.put("success", true);
			
		}else {
			map.put("success", false);
			map.put("errMsg", "empty productId");
			
		}
		return map;
		
	
	}
	
	
	
	
	
}
