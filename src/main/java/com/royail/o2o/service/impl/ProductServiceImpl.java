package com.royail.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhlabs.image.ImageUtils;
import com.royail.o2o.dao.ProductDao;
import com.royail.o2o.dao.ProductImgDao;
import com.royail.o2o.dto.ImageHolder;
import com.royail.o2o.dto.ProductExecution;
import com.royail.o2o.entity.Product;
import com.royail.o2o.entity.ProductImg;
import com.royail.o2o.entity.Shop;
import com.royail.o2o.enums.ProductStateEnum;
import com.royail.o2o.exception.ProductOperationException;
import com.royail.o2o.service.ProductService;
import com.royail.o2o.utils.ImgUtils;
import com.royail.o2o.utils.PathUtils;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	
	
	
	@Override
	@Transactional
	public ProductExecution insertProduct(Product product, ImageHolder imageHolder, List<ImageHolder> productImgs)
			throws ProductOperationException {
		
		//1.处理缩略图，获取缩略图相对路径并赋值给product
		//2.往tb_product写入商品信息，获取productId
		//3.结合productId批量处理商品详情图
		//4.将商品详情图列表批量插入tb_product_img中
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(1);
			if (imageHolder != null) {
				addProductImg(product, imageHolder);
			}
			
			try {
				int num = productDao.insertProduct(product);
				
				if (num <= 0) {
					throw new ProductOperationException("创建商品失败！");
				}
				
			} catch (Exception e) {
				throw new ProductOperationException("创建商品 error:"+ e.getMessage());
			}
			
			if (productImgs != null && productImgs.size() > 0) {
				addProductImgList(product, productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
			
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	
	/**
	 * 处理文件流
	 * @param product
	 * @param imageHolder
	 */
	private void addProductImg(Product product, ImageHolder imageHolder) {

		String path = PathUtils.imgShopPath(product.getShop().getShopId());

		String imgAddr = ImgUtils.generateThumbnail(imageHolder, path);

		product.setImgAddr(imgAddr);
	}
	
	
	private void addProductImgList(Product product,List<ImageHolder> imageHolders) {
		//获取根路径
		String path = PathUtils.imgShopPath(product.getShop().getShopId());
		
		List<ProductImg> productImgs = new ArrayList<ProductImg>();
		//遍历图片流，并添加进productImg 实体类中
		for (ImageHolder imageHolder : imageHolders) {
			String addr = ImgUtils.generateThumbnail2(imageHolder, path);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(addr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgs.add(productImg);
		}
		//productImgs 确实是有图片，就执行批量添加
		if (productImgs.size() > 0) {
			try {
				int num  = productImgDao.insertProductImg(productImgs);
				if (num <= 0) {
					throw new ProductOperationException("创建商品详情图片失败！");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片 error:"+e.getMessage());
			}
		}
	}


	@Override
	public Product findProductById(long productId) {
		return productDao.findProduct(productId);
	}


	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws ProductOperationException {
		
		//1.若缩略图参数有值，则处理缩略图（若原先存在缩略图  则删除再添加新图，之后获取缩略图相对路径并赋值给product）
		//2.若商品详情图列表参数有值，多商品详情图片列表进行同样的操作
		//3.将tb_product_img下面的该商品原乡 的商品详情图记录全部清除
		//4.更新tb_product的信息
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null ) {
			
			product.setLastEditTime(new Date());
			
			//若商品缩略图不为空 并且原有缩略图不为空删除原有缩略图并添加
			if (thumbnail != null) {
				//先获取一遍原有信息，因为原来的信息里有原图片地址
				Product temp = productDao.findProduct(product.getProductId());
				if (temp .getImgAddr() != null ) {
					ImgUtils.deleteFileOrPath(temp.getImgAddr());
				}
				addProductImg(product, thumbnail);
			}
			//如果有新的存入的商品详情图， 则将原先的删除，并添加新的图片
			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgs);
			}
			
			try {
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("商品更新失败！");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
				
			} catch (Exception e) {
				throw new ProductOperationException("更新商品失败   error:"+e.getMessage());
			}
			
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}	
	}
	
	
	
	private void deleteProductImgList(long productId) {
		//根据productId获取原来的图片
		List<ProductImg> productImgs = productImgDao.listProductImg(productId);
		
		//删除掉原来的图片
		for (ProductImg productImg : productImgs) {
			ImgUtils.deleteFileOrPath(productImg.getImgAddr());
		}
		
		//删除数据库原有的图片的信息
		productImgDao.deleteImgByProductId(productId);
		
	}
	
	
	
	
	
}
