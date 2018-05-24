/**
 * 高度自适应
 * id为页面page-content的id
 */
authHeightUtil = {
		isDoropDown: 2,//1:版本1,2版本2
	authHeightOne: function(id){
		var this_ = this;
		//头部高度
	    var headerHeight = $('.header').outerHeight();
	    
	    var headerDownHeight=0;
	    if(this_.isDoropDown == 1){
	    	headerDownHeight = $('.header-down').outerHeight();	
	    }else{
	    	headerDownHeight=25;
	    	 if(navigator.userAgent.indexOf("Chrome") > -1){
	    		 headerDownHeight=15;
	         }
	    	
	    }
	    
//	    var headerDownHeight = $('.header-down').outerHeight();
	    //window高度
	    var windowHeight = $(window).height()
	    //尾部的高度
	    var footerHeight = $('.footer').outerHeight()||0;
	    //分页的高度
	    var paginationHeight = $('.page-height:visible').outerHeight()||0;
	    //panelheader的高度
	    var panelHeadingHeight = $('.panel-heading',$("#"+id)).outerHeight();
	    //调整高度
	    var adjustHeight = $("#adjustHeight").val()||0;
	    //搜索框的高度
	    var searchContent=$(".searchContent",$("#"+id)).outerHeight()||0;
	    //body的高度
	    
	   //情景1：table-tab
	    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
	    //修改2：给上方表格的父div 添加class='table-tab-type_table'
	    //修改3：给tab中table的父div 添加class='tab-second-height'
	    
	    if($("#"+id).length>0){
	    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
	    	//表格的高度
	        var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
	      //谷歌的兼容性问题
	        if(navigator.userAgent.indexOf("Chrome") > -1){
	        	tableHeight -= 10;
	        }
	        //隐藏的div是否显示
	        if($("#tabDiv",$("#"+id)).css("display")=='block'){
	        	//表格的高度
	        	var actualTableHeight= tableHeight*0.5;
	        	//表格的高度
	        	$(".table-tab-type_table",$("#"+id)).attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
	        	// tab header 的高度
	        	var table_tab=$("#tabDiv .nav-tabs",$("#"+id)).outerHeight()||0;
	        	//选中的提示信息
	        	var selectRowHeight=$("#selectRow",$("#"+id)).outerHeight()||0;
	        	//表格的高度
	        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
	        	// tabcontent
	        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectRowHeight-10;
	        	//如果下方的tab是引用信息。
	        	//下方tab中第一种表格，表格只有一个列表。
	        	if($(".tab-height-one",$("#"+id)).length>0){
	        		//下方tab的高度
	        		var tab_second_height =tabcontent-20;
	        		//给下方tab中table 的父div 的高度进行赋值
	        		$(".tab-height-one",$("#"+id)).attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
	        	}
	        	
	        	//下方tab中第二种表格，表格有一个列表，还有一个查询条件。
	        	if($(".tab-height-one",$("#"+id)).length>0){
	        		//表格上方是否存在查询高度
	        		var searcHeigth=$(".tab-height-search-one").outerHeight()||0;
	        		//下方tab的高度
	        		var tab_second_height =tabcontent-searcHeigth-20;
	        		//给下方tab中table 的父div 的高度进行赋值
	        		$(".tab-height-one",$("#"+id)).attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
	        	}
	        	
	        	if($(".tab-height-two").length>0){
	        		//是否存在课件上传
	        		var searchOneHeigth=$(".tab-height-search-one").outerHeight()||0;
	        		var lastheigth=$(".tab-height-search-last").outerHeight()||0;
	        		//下方tab的高度
	        		var tab_second_height =tabcontent-searchOneHeigth-lastheigth-20;
	        		
	        		//给下方tab中table 的父div 的高度进行赋值
	        		$(".tab-height-two").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
	        	}
	        	
	        }else{
	        	//给表格的父div的高度进行赋值
	        	 $(".table-tab-type_table",$("#"+id)).attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
	        }
	        
	    }
	}
};