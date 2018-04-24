package com.royail.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royail.o2o.dao.AreaDao;
import com.royail.o2o.entity.Area;
import com.royail.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaDao areaDao;
	
	
	@Override
	public List<Area> queryArea() {
		List<Area> areas  = areaDao.queryArea();
		return areas;
	}

	
	
	
	
	
	
	
}
