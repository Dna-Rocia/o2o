package com.royail.o2o.temp;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royail.o2o.entity.Shop;

public class tempTest {

	
	public static void main(String[] args) {
		
		String url = "{\"shopName\":\"宜家\",\"shopAddr\":\"dz1\",\"phone\":\"dh1\",\"shopDesc\":\"ms1\"},\"area\":{\"areaId\":3}}";
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			Shop value = mapper.readValue(url, Shop.class);
			
			System.out.println(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
