package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest {

	
	@Autowired
	private HeadLineDao headLineDao;
	
	
	@Test
	public void AlistHeadLine(){
		List<HeadLine> headLines = headLineDao.listHeadLine(new HeadLine());
		
		assertEquals(4,headLines.size());
		
	}
	
	
	
	
}
