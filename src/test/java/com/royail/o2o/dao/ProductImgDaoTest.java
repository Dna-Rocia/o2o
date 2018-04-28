package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
	
	@Autowired
	private ProductImgDao productImgDao;
	
	
	@Test
	public void AinsertProductImgs()throws Exception {
		
		
		List<ProductImg> productImgs = new ArrayList<ProductImg>();
		
		productImgs.add(new ProductImg(1l, "图1", "第一张图", 1, new Date()));
		productImgs.add(new ProductImg(1l, "图2", "第二张图", 2, new Date()));
		productImgs.add(new ProductImg(1l, "图3", "第三张图", 3, new Date()));
		
		
		int num = productImgDao.insertProductImg(productImgs);
		
	
		assertEquals(3,num);
	}
	
	
	@Test
	public void BlistProductImgs() throws Exception{
		List<ProductImg> productImgs = productImgDao.listProductImg(1l);
		System.out.println(productImgs.toString());
	}
	
	
	
	@Test
	public void CdeleteProductImgs() throws Exception{
		//删除以上新增的测试数据
		long productId = 1;
		int effectNum = productImgDao.deleteImgByProductId(productId);
		assertEquals(3, effectNum);
		
	}
	
	
	
	
}
