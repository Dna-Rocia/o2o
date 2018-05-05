package com.royail.o2o.service;

import java.io.IOException;
import java.util.List;

import com.royail.o2o.entity.HeadLine;

public interface HeadLineService {
	
	
	List<HeadLine> listHeadLine(HeadLine headLine)throws IOException;
	
}
