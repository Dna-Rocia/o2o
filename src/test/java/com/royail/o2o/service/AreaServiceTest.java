package com.royail.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.royail.o2o.BaseTest;
import com.royail.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;
	
	
	@Test
	public void listAreas(){
		
		List<Area> lreas = areaService.queryArea();
		
		assertEquals("西苑",lreas.get(0).getAreaName());
		
		
		
	}
	
	
}
