/**
 * 
 */

$(function(){
	// 加载flag
	var loading = false;
	
	 /**
	  * 分页处理
	  */
    var maxItems = 20; // 最多可加载的条目，超过则禁止访问
    var pageNum = 1;	//页码
    var pageSize = 3 ;   //一页返回最大的条数
	
   /**
    * 定义搜索条件
    */
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';
    
    /**
     * url的管理
     */
    var searchDivUrl = '/o2o/frontend/shopdetail/pageinfo?shopId='+shopId;  //获取店铺商品列表的url
    var listUrl = '/o2o/frontend/productbyshop/list'; 	//获取商品列表的url
    
    //渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();
    
    //预先加载店铺信息
    addItems(pageSize,pageNum);
    
	
    /**
     * 加载数据进行填充渲染
     */
    function getSearchDivData(){
    	var url = searchDivUrl;
    	$.getJSON(url,function(data){
    		if(data.success){
    			var shop = data.shop;
    			$('#shop-cover-pic').attr('src',shop.shopImg);
    			$('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
    			$('#shop-name').html(shop.shopName);
    			$('#shop-desc').html(shop.shopDesc);
    			$('#shop-addr').html(shop.shopAddr);
    			$('#shop-phone').html(shop.phone);
    			var productCategoryList = data.productCategoryList;
    			var  html = '';
    			productCategoryList.map(function(item,index){
    				html += '<a href="#" class="button" data-product-search-id='+item.productCategoryId+'>'
    						+item.productCategoryName
    						+' </a>'
    			});
    			$('#shopdetail-botton-div').html(html);		
    		}    		
    	});
    }
    
    
    /**
     * 分页查询
     * @param pageSize 一页多少条
     * @param pageIndex 页码
     * @returns
     */
    function addItems(pageSize,pageIndex){
    	var url = listUrl + '?' 
    				+ 'pageIndex=' + pageIndex 
	    			+ '&pageSize='+ pageSize 
	    			+ '&productCategoryId=' + productCategoryId
					+ '&productName=' + productName 
					+ '&shopId=' + shopId;
    	loading = true;
    	
    	$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.productList.map(function(item, index) {
					html += '<div class="card" data-product-id='+ item.productId + '>'
							+ '<div class="card-header">' 
							+ item.productName
							+ '</div>' 
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' 
							+ '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' 
							+ '<img src="'+ item.imgAddr + '" width="44">' 
							+ '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' 
							+ item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' 
							+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				//获取你的卡片总数
				var total = $('.list-div .card').length;
				//若总数达到条件列出来的总数一致，则停止后台的加载
				if (total >= maxItems) {
					/**
					 *  加载完毕，则注销无限加载事件，以防不必要的加载 
					 *  存在bug:当取消掉这个事件，那么在当前页面再搜索商品超过pageSize的时候，就不能继续加载了
					 */
//					$.detachInfiniteScroll($('.infinite-scroll'));
					/**
					 * 加载提示符 
					 * 之前是删除，现在改隐藏
					 */ 
					$('.infinite-scroll-preloader').hide();
				}else{
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
    
    /**
     * 底部（50像素）的时候 自动进行加载下一页的内容 
     */
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
    
    
    
    /**
     * 选择新的商品类别之后，重置页码，清空原先商品列表，按照新的类别查询
     */
    $('#shopdetail-button-div').on( 'click', '.button', function(e) {
		productCategoryId = e.target.dataset.productSearchId; //获取商品类别Id
		if (productCategoryId) {
			//若之前已选定别的catgory，则移除其效果，改成选定新的category上
			if ($(e.target).hasClass('button-fill')) {
				$(e.target).removeClass('button-fill');
				productCategoryId = '';
			} else {
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			$('.list-div').empty();
			pageNum = 1;
			addItems(pageSize, pageNum);
		}
	});

    
    
    /**
     * 点击商品的卡片进入商品的详情页
     */
	$('.list-div').on('click','.card',function(e) {
		var productId = e.currentTarget.dataset.productId;
		window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
	});

    
    
    /**
     * 需要查询商品名字发生变化，重置页码，清空原先查询出来的列表信息，再根据新的条件进行查询
     */
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	
	
	/**
	 * 侧栏的时间
	 */
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	
	
	/**
	 * 初始化页面
	 */
	$.init();
	
	
	
	
});










