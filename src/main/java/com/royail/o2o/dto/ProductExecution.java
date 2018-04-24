package com.royail.o2o.dto;

import java.util.List;

import com.royail.o2o.entity.Product;
import com.royail.o2o.enums.ProductStateEnum;

public class ProductExecution extends BaseExecution {

	private Product product;
	
	private List<Product> products;
	

	public ProductExecution() {
	
	}	
	
	
	//产品操作失败构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
	}
	
	
	//产品操作成功构造器（单一）
	public ProductExecution(ProductStateEnum stateEnum,Product product) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.product = product;
	}
	
	//产品操作成功构造器（多个）
	public ProductExecution(ProductStateEnum stateEnum,List<Product> products) {
		this.state = stateEnum.getState();
		this.stateName = stateEnum.getStateName();
		this.products = products;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
	
	
	
	
	
}
