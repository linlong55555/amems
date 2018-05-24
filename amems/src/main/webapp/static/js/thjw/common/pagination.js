
var Pagination = function(params){
	var me = this;
	me.viewpagecount = 10;//每页显示12个分页便签
	
	me.renderTo = "";
	me.data = {};
	me.sortColumn = "auto";//排序字段
	me.orderType = "asc";//排序方式
	me.controller = null;
	me.extParams = null;
	me.ulAliasClass = "";//ul别名class
	me.pageAlignLeft = false;//分页页签是否居左
	me.goPage = function(){
		
	}
	me.currentPage = null;//当前页
	
	$.extend(me, params);
	
	generateHTML();
	
	//获取总数据量
	function getTotal(){
		return me.data.total;
	}
	
	//获取每页的数据
	function getPageSize(){
		return me.data.pageable.rows;
	}
	//获取总页数
	function getPageCount(){
		var total = getTotal();
		var size = getPageSize();
		return total % size==0? parseInt(total/size):parseInt(total/size)+1;//总的页数 
	}
	
	function getCurrentPage(){
		me.currentPage = me.data.pageable.page;
		return me.currentPage;
	}
	
	function generateHTML(){
		var currentPage = getCurrentPage();
		var viewpagecount = me.viewpagecount;
		var pageCount = getPageCount();
		var startpage = currentPage - (viewpagecount%2==0? parseInt(viewpagecount/2)-1: parseInt(viewpagecount/2));//起始页
		var endpage = currentPage + parseInt(viewpagecount/2); //结束页 
		if (startpage < 1) {
			startpage = 1;
			if (pageCount >= viewpagecount){
				endpage = viewpagecount;
			}else{
				endpage = pageCount;
			}
		}
		if (endpage > pageCount) {
			endpage = pageCount;
			if ((endpage - viewpagecount) > 0){
				startpage = endpage - viewpagecount + 1;
			}else{
				startpage = 1;
			}
		}
		
		var paginationContent = "";
		if (currentPage==1) { //当前为第一页时,不能向前翻页
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
		} else {
		   paginationContent = paginationContent 
		   + "<li pageNo='1'><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
		   + "<li pageNo='"+(currentPage-1)+"'><a href=\"javascript:void(0)\">&lt;</a></li>";
		}
	   
		for (var index = startpage;index <= endpage;index++){
			if (index==currentPage){
				paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
			}else {
				paginationContent = paginationContent + "<li pageNo='"+index+"'><a href=\"javascript:void(0)\">"+index+"</a></li>";
			}
		}
		if (currentPage ==pageCount){
			paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
			paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		}else {
			paginationContent = paginationContent + "<li pageNo='"+(currentPage+1)+"'><a href=\"javascript:void(0)\">&gt;</a></li>";
			paginationContent = paginationContent + "<li pageNo='"+pageCount+"'><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		}
		
		var pageAlign = "";
		if(me.pageAlignLeft){
			pageAlign = " pull-left ";
		}
		
		var $UL = $('<ul class="pagination '+pageAlign+' '+me.ulAliasClass+'" style="margin-top: 0px; margin-bottom: 0px;"></ul>');
		$UL.html(paginationContent);
		$("#"+me.renderTo).append($UL);
		var $Summary = $('<div class="fenye pull-right padding-right-0 text-center">');
		$Summary.append('<span >每页 <i>'+getPageSize()+'</i> 条</span>');
		$Summary.append('<span >共 <i>'+pageCount+'</i> 页  </span>');
		$Summary.append('<span >总数 <i>'+getTotal()+'</i> 条</span>');
		$("#"+me.renderTo).html($UL).append($Summary);
		
		$("li:not(.disabled,.active)", $UL).on("click", function(e){
			if(me.controller){
				me.controller.load($(e.target).parent().attr("pageNo"), me.sortColumn, me.orderType, me.extParams);
			}else{
				me.goPage($(e.target).parent().attr("pageNo"), me.sortColumn, me.orderType, me.extParams);
			}
		})
	}
	return me;
}