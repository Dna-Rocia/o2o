package com.royail.o2o.service;

import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ShopExecution;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.exception.ShopOperationException;

public interface ShopService {

	ShopExecution insertShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;
	
	Shop findShop(long shopId);
	
	ShopExecution updateShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;
	
	ShopExecution  listShop( Shop shop, int pageIndex,  int pageSize);
	
}
