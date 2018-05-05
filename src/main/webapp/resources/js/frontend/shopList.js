/**
 * 
 */

$(function(){
	// 加载flag
	var loading = false;
	
	 // 最多可加载的条目，超过则禁止访问
    var maxItems = 999;
    
    //一页返回最大的条数
    var pageSize = 3 ;
	
	//获取店铺列表的url
    var listUrl = '/o2o/frontend/shops/list';
	
    //获取店铺类别列表以及区域列表的url
    var searchDivUrl = '/o2o/frontend/shopspage/listinfo';
	
	//页码
    var pageNum = 1;
	
    //从地址栏url里获取 shop parent category id
    var parentId = getQueryString('parentId');
    
    var areaId = '';
    
    var shopCategoryId = '';
    
    var shopName = '';
    
    //渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();
    
    //预先加载10条店铺信息
    addItems(pageSize,pageNum);
    
    
    
    
    
    /**
     * 根据以上的条件搜寻出 店铺类别列表以及区域列表信息
     * @returns
     */
    function getSearchDivData(){
    	//如果传入parentID，则取出此一级类别下的所有二级类别
    	var url = searchDivUrl+'?parentId='+parentId;
    	$.getJSON(url,function(data){
    		if(data.success){
    			//获取后台返回的店铺类别列表
    			var shopCategoryList = data.shopCategoryList;
    			var html = '';
    			html += '<a href="#" class="button" data-category-id="">全部类别</a>'
    			
    			//遍历店铺类别列表，拼接a标签集
    			shopCategoryList.map(function(item,index){
    				html += '<a href="#" class="button" data-category-id='+item.shopCategoryId+'>'
    						+item.shopCategoryName
    						+'</a>';
    			});
    			
    			//将拼接好的语句填充控件中    				
    			$('#shoplist-search-div').html(html);
    			var selectOptions = '<option value="">全部街道</option>';
    			
    			var areaList = data.areaList;
    			
    			//遍历区域信息列表，拼接出option标签集
    			areaList.map(function(item,index){
    				selectOptions += '<option value="'+item.areaId+'">'
				    				+item.areaName
				    				+'</option>';
    			});
    			//将标签集添加进area列表里
    			$('#area-search').html(selectOptions);
    		}
    	});
    }
    
	
	function addItems(pageSize,pageIndex){
		//拼接出查询的url,赋空值就默认去掉这个条件的限制，有值就代表要按照这个条件查询
		
		var url = listUrl+'?'+'pageIndex='+pageIndex
					+'&pageSize='+pageSize
					+'&parentId='+parentId
					+'&areaId='+areaId
					+'&shopCategoryId='+shopCategoryId
					+'&shopName='+shopName;
		
		//设定加载符，若还在后台获取数据则不能再次访问，避免多次重复加载
		loading = true;
		
		
		//访问后台获取相应的店铺列表
		$.getJSON(url,function(data){
			if(data.success){
				//获取当前查询的店铺总数
				maxItems = data.count;
				var html ='';
				
				//遍历店铺列表，拼接出卡片集合
				
				var shopList = data.shopList; 
				if(shopList == null){
					$.toast('找不到店铺了！');
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').remove();
				}else{
				
				shopList.map(function(item,index){
					html +='<div class="card" data-shop-id="'+ item.shopId + '">' 
					+ '<div class="card-header">'+ item.shopName 
					+ '</div>'
					+ '<div class="card-content">'
					+ '<div class="list-block media-list">' 
					+ '<ul>'
					+ '<li class="item-content">'
					+ '<div class="item-media">' 
					+ '<img src="'+ item.shopImg + '" width="44">' 
					+ '</div>'
					+ '<div class="item-inner">'
					+ '<div class="item-subtitle">' + item.shopDesc
					+ '</div>' + '</div>' + '</li>' + '</ul>'
					+ '</div>' + '</div>' 
					+ '<div class="card-footer">'
					+ '<p class="color-gray">'+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
					+ '更新</p>' 
					+ '<span>点击查看</span>' 
					+ '</div>'
					+ '</div>';
					
				});
				//将数据追加进组件中
				$('.list-div').append(html);
				//获取目前为止以显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				//若总数达到跟按照此查询条件列出来的总数一致，则停止后台加载
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').remove();
				}
				//否则页码加1，继续load
				pageNum += 1;
				//加载结束后
				loading = false;
				//刷新页面显示加载的数据
				$.refreshScroller();	
				}
			}
		});
		
	}
	
	// 预先加载20条
	//addItems(pageSize, pageNum);

	//下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	//点击店铺卡片进入该店铺的详情页
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});
	
	//选择新的店铺类别之后，重置页码，清空原先的店铺列表，再进行新的条件查询
	$('#shoplist-search-div').on('click','.button',function(e) {
				if (parentId) {
					// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					//若之前已选定别的category，则移除其选定的效果，改成选定新的
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					//清空原来的数据列表
					$('.list-div').empty();
					//重置页码
					pageNum = 1;
					
					addItems(pageSize, pageNum);
				} else {
					// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					//清空原来的数据列表
					$('.list-div').empty();
					//重置页码
					pageNum = 1;
					addItems(pageSize, pageNum);
					parentId = '';
				}

			});

	$('#search').on('input', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-righ-demo');
	});

	//初始化页面
	$.init();
	
	

	
	
});















