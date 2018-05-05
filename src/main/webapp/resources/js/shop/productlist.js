/**
 * 
 */
$(function() {
	// 获取商品列表
	var listUrl = '/o2o/shopadmin/product/list?pageIndex=1&pageSize=999';
	// 商品下架
	var statusUrl = '/o2o/shopadmin/product/update';
	
	getList();

	/**
	 * 获取当前店铺下的商品列表
	 * @returns
	 */
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';

				// 遍历商品
				productList.map(function(item, index) {

					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}

					// 拼接商品
					tempHtml += '<div class="row row-product"><div class="col-33">'
						+item.productName+'</div><div class="col-20">'
						+item.priority+'</div><div class="col-40">'
						+'<a href="#" class="edit" data-id="'+item.productId+'">编辑</a>'
						+'<a href="#" class="status" data-id="'+item.productId+'" data-status="'+contraryStatus+'">'+textOp+'</a>'
						+'<a href="#" class="preView" data-id="'+item.productId+'"  data-status="'+item.enableStatus+'">预览</a>'
						+'</div></div>';
				});
				$('#product-wrap').html(tempHtml);
			}

		});

	}
	
	
	
	/**
	 * 给a标签进行绑定事件
	 */
	$('#product-wrap').on('click','a',function(e){
		var target = $(e.currentTarget);
		
		//如果有class edit则点击就进入店铺信息编辑页面，并带有productId参数
		if(target.hasClass('edit')){
			window.location.href = '/o2o/shopadmin/productoperation?productId='+e.currentTarget.dataset.id;
		}else //如果有class status则调用后台功能上下架相关商品，并带有productId参数
			if(target.hasClass('status')){
			changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
		}else //如果有class preView 则去前台展示系统高商品详情页，并带有productId参数
			if(target.hasClass('preView')){
				window.location.href = '/o2o/frontend/productdetail?productId='+e.currentTarget.dataset.id;
			}	
	});
	
	
	
	function changeItemStatus(id,enableStatus){
		//定义product json对象并添加productId以及状态（上下架）
		
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		
		$.confirm('确定么？',function(){
			$.ajax({
				url:statusUrl,
				type:'POST',
				data:{
					productStr:JSON.stringify(product),
					statusChange:true  //让其跳过验证码的验证
				},
				dataType:'json',
				success:function(data){
					if(data.success){
						$.toast('操作成功！');
						getList();
					}else{
						$.toast('操作失败！');
					}
				}
			});
		});	
	}
	
	
	
	
	
	
	

});
