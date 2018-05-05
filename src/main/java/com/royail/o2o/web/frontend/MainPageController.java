package com.royail.o2o.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.royail.o2o.dto.ShopCategoryExecution;
import com.royail.o2o.entity.HeadLine;
import com.royail.o2o.enums.ProductCategoryStateEnum;
import com.royail.o2o.service.HeadLineService;
import com.royail.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private HeadLineService headLineService;

	/**
	 * 初始化前端展示系统的展示信息，包括获取一级店铺类别列表以及头条列表
	 */
	@RequestMapping(value = "/mainpage/info", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listMainPageInfo() {

		Map<String, Object> map = new HashMap<String, Object>();

		/**
		 * 一级店铺类别
		 */
		try {
			ShopCategoryExecution execution = shopCategoryService.listShopCategory(null);

			map.put("shopCategoryList", execution.getShopCategories());

		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", ProductCategoryStateEnum.INNER_ERROR);
			return map;
		}

		
		/**
		 * 头条
		 */
		List<HeadLine> headLines = new ArrayList<>();

		try {

			HeadLine headLine = new HeadLine();
			headLine.setEnableStatus(1);
			headLines = headLineService.listHeadLine(headLine);
			map.put("headLineList", headLines);

		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", ProductCategoryStateEnum.INNER_ERROR);
			return map;
		}

		map.put("success", true);

		return map;

	}

}
