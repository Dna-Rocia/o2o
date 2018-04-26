package com.royail.o2o.web.shopAdmin;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ProductStateEnum;
import com.royail.o2o.service.ProductService;
import com.royail.o2o.utils.CodeUtil;
import com.royail.o2o.utils.HttpServletRequestUtils;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;

	private static final int IMAGEMAXCOUNT = 6;

	
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
		MultipartHttpServletRequest mHttpServletRequest = null; // 用于处理文件流的
		ImageHolder tImageHolder = null; // 用于处理缩略图的文件流
		List<ImageHolder> pImageHolders = new ArrayList<ImageHolder>(); // 用于保存商品详情图的文件流列表
																		// 以及对应的名字列表
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext()); // 从request中去获取文件流的
		try {
			// 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if (resolver.isMultipart(request)) {
				mHttpServletRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建ImagerHolder对象
				CommonsMultipartFile tMultipartFile = (CommonsMultipartFile) mHttpServletRequest.getFile("thumbnail");
				tImageHolder = new ImageHolder(tMultipartFile.getOriginalFilename(), tMultipartFile.getInputStream());
				
				// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productFile = (CommonsMultipartFile) mHttpServletRequest
							.getFile("productImg" + i);
					if (productFile != null) {
						// 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
						ImageHolder pHolder = new ImageHolder(productFile.getOriginalFilename(),productFile.getInputStream());
						pImageHolders.add(pHolder);
					} else {
						// 若取出的第i个详情图片文件流为空，则终止循环
						break;
					}
				}
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
		if (product != null && tImageHolder != null && pImageHolders.size() > 0) {

			try {

				// 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);

				// 执行添加操作
				ProductExecution execution = productService.insertProduct(product, tImageHolder, pImageHolders);
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
}
