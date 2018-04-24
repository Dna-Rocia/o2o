/**
 * 
 */

$(function() {

	var insertUrl = "/o2o/shopadmin/productcategory/insert";
	var listUrl = "/o2o/shopadmin/productcategory/list";
	var deleteUrl = "/o2o/shopadmin/productcategory/delete";
	
	getlist();

	function getlist(e) {
		$('#product-category-wrap').empty();
		$.ajax({
			url : listUrl,
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.productCategoryList);
				}
			}
		});
	}

	function handleList(data) {

		var html = '';
		data
				.map(function(item, index) {
					html += '<div class="row row-product-category now"><div class="col-33 product-category-name">'
							+ item.productCategoryName
							+ '</div><div class="col-33">'
							+ item.priority
							+ '</div><div class="col-33">'
							+ goProductCategory(item.productCategoryId)
							+ '</div></div>';
				});

		$('#product-category-wrap').append(html);
	}

	/**
	 * /o2o/shopadmin/productcategory/delete?productCategoryId='
				+ id + '
	 * @param id
	 * @returns
	 */
	function goProductCategory(id) {
		return '<a href="#" class="button delete" data-id="'+id+'">删除</a>';
	}

	/**
	 * 每点击一次新增按钮就创建一个新的行
	 */
	$('#new')
			.click(
					function() {
						var tempHtml = '';

						tempHtml += '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="类别名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
								+ '</div>';
						$('#product-category-wrap').append(tempHtml);
					});

	/**
	 * 数据填充后进行提交后台
	 */

	$('#submit').click(function() {
		var tempArr = $('.temp');
		/** 通过temp进行识别* */
		var productCategoryList = [];

		tempArr.map(function(index, item) {
			var tempObj = {};
			
			tempObj.productCategoryName = $(item).find('.category').val();
			
			tempObj.priority = $(item).find('.priority').val();

			alert(tempObj.productCategoryName +"   "+tempObj.priority +"  "+ (tempObj.productCatedoryName!='' && tempObj.priority!='')); 
			
			if (tempObj.productCatedoryName!='' && tempObj.priority!='') {
				productCategoryList.push(tempObj);
			}

	
		});
		
		$.ajax({
			url:insertUrl,
			type:'POST',
			data:JSON.stringify(productCategoryList),
			contentType:'application/json',
			success:function(data){
				if(data.success){
					$.toast("提交成功！");
					getlist();
				}else{
					$.toast("提交失败！");
				}
			}
		});
	});
	
	
	
	
	/**
	 * 临时数据删除
	 */
	$('#product-category-wrap').on('click','.row-product-category.temp .delete',function(e){
		console.log($(this).parent().parent());
		$(this).parent().parent().remove();
	});
	
	
	/**
	 * 删除数据库数据
	 */
	$('#product-category-wrap').on('click','.row-product-category.now .delete',function(e){
		var target = e.currentTarget;
		$.confirm('确定么？',function(){
			alert(1111111111);
			$.ajax({
				url:deleteUrl,
				type:'POST',
				data:{
					productCategoryId:target.dataset.id
				},
				dataType:'json',
				success: function(data){
					if(data.success){
						$.toast('删除成功！');
						getlist();
					}else{
						$.toast('删除失败！');
					}
				}
			});
		});	
	});
	
	

});
