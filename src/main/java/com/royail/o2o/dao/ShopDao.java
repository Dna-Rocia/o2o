package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.Shop;

public interface ShopDao {
	
	int insertShop(Shop shop );
	
	int updateShop(Shop shop);
	
	Shop findShop(long shopId);
	
	/**
	 * 分页查询，可搜索的查询条件：店铺名称（模糊搜索）、店铺状态、店铺类别、区域id、用户owner
	 * @param rowIndex 从第几行开始取值
	 * @param PageSize 返回的条数
	 * @return
	 */
	List<Shop> listShop(@Param("shopCondition") Shop shop,@Param("rowIndex") int rowIndex, @Param ("pageSize") int PageSize);
	
	int listShopCount(@Param("shopCondition") Shop shop);
	
}
