package com.royail.o2o.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.royail.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtils {
	
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random RANDOM = new Random();
	private static Logger logger =  LoggerFactory.getLogger(ImgUtils.class);
	
	
	
	
	public static File transferCommonFileToFile(CommonsMultipartFile commonsMultipartFile){
		
		File  file = new File(commonsMultipartFile.getOriginalFilename());
		
		try {
			commonsMultipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		
		
		return file;
		
	}
	
	/**
	 * 处理缩略图，并返回目标（新生成）图片的相对路径
	 */
	public static String generateThumbnail(ImageHolder imageHolder ,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		
		makeDirPath(targetAddr);
		
		String relativeAddr = targetAddr + realFileName + extension;
		
		logger.debug("current >>>>  relative is:"+relativeAddr);
		
		File file = new File(PathUtils.imgBasePath()+relativeAddr);
		
		logger.debug("current >>>>  complete addr is:"+relativeAddr);
		
		try{
			

			Thumbnails.of(imageHolder.getImage()).size(200, 200).watermark(Positions.BOTTOM_RIGHT, 
						ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.25f)
						.outputQuality(0.8f).toFile(file);
			
			
		}catch (IOException e) {
			
			logger.error(e.toString());
			e.printStackTrace();
		
		}
		
		return relativeAddr;
	}
	
	
	public static String generateThumbnail2(ImageHolder imageHolder ,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		
		makeDirPath(targetAddr);
		
		String relativeAddr = targetAddr + realFileName + extension;
		
		logger.debug("current >>>>  relative is:"+relativeAddr);
		
		File file = new File(PathUtils.imgBasePath()+relativeAddr);
		
		logger.debug("current >>>>  complete addr is:"+relativeAddr);
		
		try{
			

			Thumbnails.of(imageHolder.getImage()).size(337, 640).watermark(Positions.BOTTOM_RIGHT, 
						ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.25f)
						.outputQuality(0.9f).toFile(file);
			
			
		}catch (IOException e) {
			
			logger.error(e.toString());
			e.printStackTrace();
		
		}
		
		return relativeAddr;
	}
	
	
	/**
	 * 随机生成文件名(年月日+随机5个数)
	 * @return
	 */

	private static String getRandomFileName() {
		int ranNum = RANDOM.nextInt(89999)+10000;	
		
		String nowTimeStr = SIMPLE_DATE_FORMAT.format(new Date());
		
		return nowTimeStr+ranNum;
	}




	/**
	 * 拿到文件的扩展名
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}




	/**
	 * 创建目标路径所涉及到的目录
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realPPath = PathUtils.imgBasePath()+targetAddr;
		
		File dFile = new File(realPPath);
		
		if (!dFile.exists()) {
			
			dFile.mkdirs();
			
		}
		
	}

	/**
	 * storePath是文件/目录路径	
	 * @param storePath 若为文件就删文件，反之，是目录就直接删除目录下所有文件
	 */
	public static void  deleteFileOrPath(String storePath) {
		
		File fileOrPath = new File(PathUtils.imgBasePath()+storePath);
		
		if (fileOrPath.exists()) {
			
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					 files[i].delete();
				}
			}
			fileOrPath.delete();
		}	
	}
	
	
	
	
	
	

	public static void main(String[] args) throws IOException {
			
			Thumbnails.of(
					new File("D:/image/lufei.jpg")).size(200, 200).watermark(Positions.BOTTOM_RIGHT, 
							ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile("D:/image/lufeinew.jpg");
			
		

	}

}
