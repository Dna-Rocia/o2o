package com.royail.o2o.web.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendRouteController {

	
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	private String  index() {
		return RequestRouteManagement.FRONTEND_INDEX.getFinalUrl();
	}
	
	
	
	@RequestMapping(value="/shoplist",method=RequestMethod.GET)
	private String  shopslist() {
		return RequestRouteManagement.FRONTEND_SHOP_LIST.getFinalUrl();
	}
	
	
	
	
	
	
	
}
