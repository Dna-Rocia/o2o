/**
 * ① 获取后端的店铺分类 以及 商品信息，填充控件中
 * 
 * ②数据提交转发至后台
 */
$(function() {
	var shopId = getQueryString('shopId');
	
	var isEdit  = shopId?true:false;
	
	var initUrl = '/o2o/shopadmin/shop/initInfo';

	var registerShopUrl = '/o2o/shopadmin/shop/register';
	
	var shopInfoUrl="/o2o/shopadmin/shop/find?shopId="+shopId;
	
	var editShopUrl = "/o2o/shopadmin/shop/update";
	
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	
	/**
	 * 编辑店铺，店铺的类别是不可以改变的
	 * @param shopId
	 * @returns
	 */
	function getShopInfo(shopId) {
		
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop  = data.shop;
				$('#shop_name').val(shop.shopName);
				$('#shop_addr').val(shop.shopAddr);
				$('#shop_phone').val(shop.phone);
				$('#shop_desc').val(shop.shopDesc);
				
				var shopcategory = '<option data-id=' + shop.shopCategory.shopCategoryId + ' selected>'+ shop.shopCategory.shopCategoryName + '</option>';
				$('#shop_category').html(shopcategory);
				$('#shop_category').attr('disabled','disabled');
				
				var tempAreaHtml='';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id=' + item.areaId + '>'+ item.areaName + '</option>'
				})
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
			}
		});
	}
	
	
	
	
	
	/**
	 * 获取初始化数据
	 * 
	 * @returns
	 */
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';

				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id=' + item.shopCategoryId + '">'
							+ item.shopCategoryName + '</option>';
				})

				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id=' + item.areaId + '">'
							+ item.areaName + '</option>'
				})

				$('#shop_category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	
	
	
	
	
	$('#submit').click(function(){
		var shop = {};
		if(isEdit){
			shop.shopId = shopId;
		
		}
		shop.shopName = $('#shop_name').val();
		shop.shopAddr = $('#shop_addr').val();
		shop.phone = $('#shop_phone').val();
		shop.shopDesc = $('#shop_desc').val();
		shop.shopCategory = {
				shopCategoryId: $('#shop_category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		shop.area = {
				areaId: $('#area').find('option').not(function() {
					return !this.selected;
				}).data('id')
		};
		alert(shop.area.areaId);
		var shopImg = $('#shop_img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		
		var verifyCode = $('#j_kaptcha').val();
		if(!verifyCode){
			$.toast("请输入验证码 ！");
			return;
		}
		
		formData.append('verifyCode',verifyCode);
		
		$.ajax({
			url:(isEdit?editShopUrl:registerShopUrl),
			type:'POST',
			data :formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if (data.success) {
					$.toast('提交成功！');
				} else {
					$.toast('提交失败！' + data.errMsg);
				}
				$('#kaptcha_img').click();
			}
			
		});	
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

})
