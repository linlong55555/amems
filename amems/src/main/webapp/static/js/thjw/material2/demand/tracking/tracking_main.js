$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	$('input[name=date-range-picker]').daterangepicker({"opens":"left"}).prev().on("click",
			function() {
				$(this).next().focus();
			}
	);
})
function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
function moreSearchModal(){
	if ($("#divSearchModal").css("display") == "none") {
		$("#divSearchModal").css("display", "block");
		$("#iconModal").removeClass("icon-caret-down");
		$("#iconModal").addClass("icon-caret-up");
	} else {
		$("#divSearchModal").css("display", "none");
		$("#iconModal").removeClass("icon-caret-up");
		$("#iconModal").addClass("icon-caret-down");
	}
}
function customResizeHeight(bodyHeight, tableHeight){
	 if(navigator.userAgent.indexOf("Chrome") > -1){
		 bodyHeight -= 3;
     }
	  var rowHeight = $('.row-height:visible').outerHeight()||0;
	  
	  var mainHeight=(bodyHeight-rowHeight-10);
	  /* 左边样式设置 */
	  $("#leftDiv").css({"height":mainHeight+"px","overflow":"auto"})
	  var leftHeader=$("#leftDiv").find(".panel-heading").outerHeight()||0;
	  var leftFooter=$("#leftDiv").find(".panel-footer").outerHeight()||0;
	  var leftBody=mainHeight-leftHeader-leftFooter-4;
	  $("#leftDiv").find(".panel-body").css({"height":leftBody+"px","overflow":"auto"})
	  /* 右边样式设置  */
	  $("#rightDiv").css({"height":mainHeight+"px"})
	  var rightHeader=$("#rightDiv").find(".panel-heading").outerHeight()||0;
	  var rightBody=mainHeight-rightHeader-4;
	  $("#rightDiv").find(".panel-body").css({"height":rightBody+"px","overflow":"auto"})
	  var paginationHeight=$("#tracking_pagination").outerHeight()||0
	  var tableHeightOld=rightBody-20;
	  if($(".displayContent").is(":hidden")){
	    	$("#rightDiv .table-height").attr('style', 'height:' + (rightBody-20-paginationHeight) + 'px !important;overflow-x: auto;');
	    }else{
	    	$table = $("#rightDiv .table-height");
	    	if($table.length > 0){
	    		var cHeight = $table.attr("c-height");
	    		var tempTableHight = tableHeightOld*0.45;
	    		if(cHeight){
	    			if(cHeight.indexOf("%") > 0){
	    				cHeight = cHeight.replace("%","");
	    				cHeight = cHeight/100;
	    				tempTableHight = tableHeightOld * cHeight;
	    			}else{
	    				tempTableHight = cHeight;
	    			}
	    		}
	    	 $table.attr('style', 'height:' + tempTableHight + 'px !important;overflow: auto;');
	    	 
			 var tableHeightNew=$("#rightDiv .table-height:visible").outerHeight()||0;
			 
			 var rightRowHeight=$("#rightDiv .row-height").outerHeight()||0;
			 $(".displayContent").removeAttr("style");
			 var bottom_hidden_tab =tableHeightOld-tableHeightNew-rightRowHeight-paginationHeight;
			 $(".displayContent").find("table").parent("div").css({"height":bottom_hidden_tab+"px","overflow":"auto"});
	    	}
	    }
	
}
function view(){
	window.location.href = basePath+"/material/demand/tracking/view";
}
function closedDemand(){
	$("#tracking_open_alert").modal("show");
}
function showRightContent(obj,e){
	if(e.target.nodeName.toLowerCase() == 'input'){
		
	}else{
		alert("切换数据")
	}
}
function hideBottom(){
	$("#hideIcon").remove();
	$(".displayContent").css("display","none");	
	$("#tracking_table tbody").find("tr").removeClass("bg_tr");
	App.resizeHeight();
	
}
function showHiddenContent(obj){
	
}

function toggleIcon(obj){
	var i = $(obj);
	if(i.hasClass("icon-caret-left")){
		i.removeClass("icon-caret-left").addClass("icon-caret-right");
		$("#leftDiv").hide();
		$("#rightDiv").removeClass("col-lg-9 col-md-9 col-xs-9").addClass("col-sm-12 col-xs-12 padding-left-0 padding-right-0");	
	}else{
		i.removeClass("icon-caret-right").addClass("icon-caret-left");
		$("#leftDiv").show();
		$("#rightDiv").removeClass("col-sm-12 col-xs-12 padding-left-0 padding-right-0").addClass("col-lg-9 col-md-9 col-xs-9 padding-right-0");
	}
	App.resizeHeight();
	
}

