$(function() {
	// 从URL获取productId参数的值
	var productId = getQueryString('productId');

	var baseUrl = "/o2o/shopadmin";

	var proUrl = "/product";

	var catUrl = "/productcategory";

	// 通过productId获取商品信息的URL
	var infoUrl = baseUrl + proUrl + "/find?productId=" + productId;

	// 获取当前店铺设定的商品类别列表的Url
	var categoryUrl = baseUrl + catUrl + "/list";

	// 更新商品信息的Url
	var productPostUrl = baseUrl + proUrl + "/update";

	/**
	 * 由于新增和更新使用同一个页面 必须定义一个标识符进行区分
	 */
	var isEdit = false;
	if (productId) {
		// 若有值的话，默认编辑
		getInfo(productId);
		isEdit = true;

	} else {
		getCategory();
		productPostUrl = baseUrl + proUrl + '/insert';

	}

	/**
	 * 获取到信息后进行展示并更新
	 * 
	 * @param id
	 * @returns
	 */
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								var product = data.product;
								$('#product_name').val(product.productName);
								$('#product_desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#normal_price').val(product.normalPrice);
								$('#promotion_price').val(
										product.promotionPrice);

								// 获取原本的商品类别以及该店铺的所有商品类别列表
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;

								// 生成前端的Html商品类别列表，并默认选择前的商品类别
								optionArr
										.map(function(item, index) {

											var isSelect = (optionSelected == item.productCategoryId) ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '" '
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';

										});

								$('#category').html(optionHtml);
							}
						});
	}

	/**
	 * 为商品添加操作提供该店铺下所有商品类别列表
	 * 
	 * @returns
	 */
	function getCategory() {

		$.getJSON(categoryUrl, function(data) {
			if (data.success) {

				var productCategoryList = data.productCategoryList;
				var optionHtml = '';

				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	/**
	 * 针对商品详情图空间组，若该空间组的最后一个元素发生变化（即上传了图片），且控件总数未达到6个，则生成新的文件上传控件
	 */
	$('.detail-img-div').on(
			'change',
			'.detail-img:last-child',
			function() {
				if ($('.detail-img').length < 6) {
					$('#detail_img').append(
							'<input type="file"  class="detail-img"/>');
				}
			});

	/**
	 * 提交按钮的事件响应，分别对新增以及编辑做出不同的响应
	 */
	$('#submit').click(
			function() {
				/**
				 * 相同部分：首先创建json对象，然后从表单中获取值
				 */
				var product = {};
				product.productName = $('#product_name').val();
				product.productDesc = $('#product_desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal_price').val();
				product.promotionPrice = $('#promotion_price').val();
				// 获取选定的商品类别值
				product.productCategory = {
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};

				product.productId = productId;
				// 获取缩略图文件流
				var thumbnail = $('#small_img')[0].files[0];
				// 生成表单对象，用于接收参数并传递给后台
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				// 遍历商品详情图控件，获取里面的文件流
				$('.detail-img').map(
						function(index, item) {
							// 判断该控件是否已选择了文件
							if ($('.detail-img')[index].files.length > 0) {
								// 将第文件流赋值给key为productImg的表单键值对里
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// 将product json对象转成字符流保存至表单对象 key为productStr的键值对里
				formData.append('productStr', JSON.stringify(product));

				// 获取表单里输入的验证码
				var verityCodeActual = $('#j_kaptcha').val();
				if (!verityCodeActual) {
					$.toast('请输入验证码！');
					return;
				}

				formData.append('verifyCode', verityCodeActual);

				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							$('#kaptcha_img').click();
						} else {
							$.toast('提交失败！');
							$('#kaptcha_img').click();
						}
					}
				});

			});

});
