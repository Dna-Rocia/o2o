package com.royail.o2o.utils;


/**
 * 提供两类路径： ①依据执行环境的不同提供所处位置的根路径 ②获取相对路径（店铺的存储路径）
 * 
 * @author Loyaill
 *
 */
public class PathUtils {

	private static String seperator = System.getProperty("file.separator");

	public static String imgBasePath() {

		String oString = System.getProperty("os.name");
		String basePath = "";

		if (oString.toLowerCase().startsWith("win")) {
			basePath = "D:/image/";
		} else {
			basePath = "/home/image/";
		}

		basePath = basePath.replace("/", seperator);

		return basePath;
	}

	public static String imgShopPath(long shopId) {

		String imgPath = "upload/item/shop/" + shopId+"/";
		
		imgPath = imgPath.replace("/", seperator);
		
		return imgPath ;
	}

	
	

}
