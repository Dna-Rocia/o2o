package com.royail.o2o.web.shopAdmin;

public enum RequestRouteManagement {
	

	SHOP_OPERATION("shopOperation","跳转至店铺注册/编辑页面"),
	
	SHOP_LIST("list","转发至店铺列表页面"),
	
	SHOP_MANAGEMENT("shopManagement","转发至店铺管理页面"),
	
	SHOP_PRODUCT_CATEGORY_LIST("productCategory","转发至商品类别管理页面  --列表"),
	
	SHOP_PRODUCT_CATEGORY_SUBMIT("productCategory","转发至商品类别管理页面 --新增")
	
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
		this.finalUrl = baseUrl+ operationUrl;
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
