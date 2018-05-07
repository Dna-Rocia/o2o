package com.royail.o2o.web.route;

public enum RequestRouteManagement {
	/**
	 * shop部分的地址
	 */

	SHOP_OPERATION("shopOperation","跳转至店铺注册/编辑页面"),
	
	SHOP_LIST("shopList","根据某个人转发至（TA）店铺列表页面"),
	
	SHOP_MANAGEMENT("shopManagement","转发至店铺管理页面"),
	
	SHOP_PRODUCT_CATEGORY("productCategory","转发至商品类别管理页面  "), //列表、新增、删除
	
	SHOP_PRODUCT("productOperation","转发至商品操作（新增、编辑）的页面"),
	
	SHOP_PRODUCT_LIST("productList","转发至商品列表的页面"),
	
	
	
	/**
	 * frontend部分的地址
	 */
	
	FRONTEND_INDEX("frontend", "index","转发至首页"),
	
	FRONTEND_SHOP_LIST("frontend", "shopList","转发至（全部）商品列表页面"),
	
	FRONTEND_SHOP_DETAIL("frontend","shopDetail","转发至店铺详情页"),
	
	FRONTEND_PRODUCT_DETAIL("frontend","productDetail","转发至商品详情页")

	
	;
	
	

	

	private String  baseUrl ;
	
	private String operationUrl;
	
	private String urlDesc;
	
	private String finalUrl;
	
	
	private RequestRouteManagement() {
		
	}

	private RequestRouteManagement(String operationUrl, String urlDesc) {
		this.baseUrl = "shop/";
		this.operationUrl = operationUrl;
		this.urlDesc = urlDesc;
		this.finalUrl = baseUrl+ operationUrl;
	}

	

	private RequestRouteManagement(String baseUrl, String operationUrl, String urlDesc) {
		this.baseUrl = baseUrl;
		this.operationUrl = operationUrl;
		this.urlDesc = urlDesc;
		this.finalUrl = baseUrl+"/"+ operationUrl;
	}


	public String getBaseUrl() {
		return baseUrl;
	}


	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


	public String getOperationUrl() {
		return operationUrl;
	}


	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}


	public String getUrlDesc() {
		return urlDesc;
	}


	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}


	public String getFinalUrl() {
		return finalUrl;
	}


	public void setFinalUrl(String finalUrl) {
		this.finalUrl = finalUrl;
	}
	
	
	
	
	
	
}
