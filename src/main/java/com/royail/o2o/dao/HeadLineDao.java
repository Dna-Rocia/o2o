package com.royail.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.royail.o2o.entity.HeadLine;

public interface HeadLineDao {

	
	List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
	
	
	
	
}
