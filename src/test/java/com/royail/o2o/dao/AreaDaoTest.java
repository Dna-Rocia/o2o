package com.royail.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.Area;

public class AreaDaoTest extends BaseTest {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void listArea() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>我的网站SSS");
		List<Area> areas =  areaDao.queryArea();
		 assertEquals(2,areas.size());
	}
	
	
	
	
	
}
