package com.royail.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royail.o2o.dao.HeadLineDao;
import com.royail.o2o.entity.HeadLine;
import com.royail.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{

	@Autowired
	private HeadLineDao headLineDao;
	
	
	@Override
	public List<HeadLine> listHeadLine(HeadLine headLine) throws IOException {
		return headLineDao.listHeadLine(headLine);
	}
	
	
}
