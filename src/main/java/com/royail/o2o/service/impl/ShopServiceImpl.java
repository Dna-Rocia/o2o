package com.royail.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royail.o2o.dao.ShopDao;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ShopExecution;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ShopStateEnum;
import com.royail.o2o.exception.ShopOperationException;
import com.royail.o2o.service.ShopService;
import com.royail.o2o.utils.ImgUtils;
import com.royail.o2o.utils.PageCalculatorUtils;
import com.royail.o2o.utils.PathUtils;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution insertShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
		if (null == shop) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {

			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());

			int effctNum = shopDao.insertShop(shop);

			if (effctNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {

				if (null != imageHolder.getImage()) {
					try {

						addShopImg(shop, imageHolder);

					} catch (Exception e) {
						e.printStackTrace();
						throw new ShopOperationException("insertShopImg error:" + e.getMessage());

					}

					int updateNum = shopDao.updateShop(shop);

					if (updateNum <= 0) {
						throw new ShopOperationException("店铺图片地址更新失败");
					}
				}
			}

		} catch (Exception e) {
			throw new ShopOperationException("insertShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, ImageHolder imageHolder) {

		String path = PathUtils.imgShopPath(shop.getShopId());

		String shopImgAddr = ImgUtils.generateThumbnail(imageHolder, path);

		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop findShop(long shopId) {
		return shopDao.findShop(shopId);
	}

	@Override
	public ShopExecution updateShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {

			// 1.判断是否需要改变图片信息
			if (null != imageHolder) {
				if (imageHolder.getImage() != null && imageHolder.getImageName() != null
						&& !"".equals(imageHolder.getImageName())) {

					Shop tempShop = shopDao.findShop(shop.getShopId());

					if (tempShop.getShopImg() != null) {
						ImgUtils.deleteFileOrPath(tempShop.getShopImg());
					}

					addShopImg(shop, imageHolder);
				}
			}

			try {
				// 2.更新店铺信息
				shop.setLastEditTime(new Date());
				int updateNum = shopDao.updateShop(shop);
				if (updateNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.findShop(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}

			} catch (Exception e) {
				throw new ShopOperationException("updateShop error:" + e.getMessage());
			}

		}
	}

	@Override
	public ShopExecution listShop(Shop shop, int pageIndex, int pageSize) {

		int shopLength = shopDao.listShopCount(shop);
		ShopExecution execution = new ShopExecution();

		if (shopLength > 0) {
			int rowIndex = PageCalculatorUtils.calculatorRowIndex(pageIndex, pageSize);
			List<Shop> shops = shopDao.listShop(shop, rowIndex, pageSize);
			if (shops != null) {
				execution.setShops(shops);
			}
			execution.setCount(shopLength);
		} else {
			execution.setState(ShopStateEnum.INNER_ERROR.getState());
		}

		return execution;
	}

}
