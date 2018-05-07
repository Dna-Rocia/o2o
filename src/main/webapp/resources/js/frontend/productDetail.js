/**
 * 
 */

$(function() {
	var productId = getQueryString('productId');
	var productUrl = '/o2o/frontend/productdetail/list?productId='+ productId;

	$.getJSON(productUrl,function(data) {
		if (data.success) {
			var product = data.product;
			//商品缩略图
			$('#product-img').attr('src', product.imgAddr);
			$('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
//			if (product.point != undefined) {
//				$('#product-point').text('购买可得' + product.point + '积分');
//			}
			$('#product-name').text(product.productName);
			$('#product-desc').text(product.productDesc);
			//商品价格展示逻辑，主要判断原价现价是否为空，所有都为空则不显示价格栏目
			if (product.normalPrice != undefined && product.promotionPirce != undefined) {
				//如果现价和原价都不为空则都展示，并且给原价加个删除符号
				$('#price').show();
				$('#normalPrice').text('<del>' + '￥' + product.normalPrice+ '</del>');
				$('#promotionPrice').text('￥' + product.promotionPirce);
			} else if (product.normalPrice != undefined && product.promotionPirce == undefined) {
				//如果原价不为空而现价为空，只展示原价
				$('#price').show();
				$('#promotionPrice').text('￥' + product.normalPrice);
			} else if (product.normalPrice == undefined && product.promotionPirce != undefined) {
				//如果现价不为空而原价为空，只展示现价
				$('#promotionPrice').text( '￥' + product.promotionPrice);
			}
			var imgListHtml = '';
			//遍历商品详情图列表，并生成批量的img标签
			product.productImgList.map(function(item, index) {
				imgListHtml += '<div> <img src="'
						+ item.imgAddr
						+ '" width="100%" /></div>';
			});
			
//			if (data.needQRCode) {
//				// 生成购买商品的二维码供商家扫描
//				imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
//						+ product.productId
//						+ '" width="100%"/></div>';
//			}
			$('#imgList').html(imgListHtml);
		}
	});
	
	//打开侧栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});

