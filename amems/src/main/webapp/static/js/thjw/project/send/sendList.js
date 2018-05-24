$(document).ready(function(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 
	});
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		obj.keyword=searchParam.keyword;
		obj.zt=searchParam.zt;
		obj.zdrid=searchParam.zdrid;
		
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/send/querySendList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				  appendContentHtml(data.rows);
				  var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
				   });
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[1]);
			   } else {
				  $("#Annunciatelist").empty();
				  $("#mpagination").empty();
				  $("#Annunciatelist").append("<tr><td colspan=\"6\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
		 searchParam.zt = $.trim($("#zt").val());
		 searchParam.dprtcode = $.trim($("#zzjg").val());
		 searchParam.zdrid = $.trim($("#zdrid").val());
		 return searchParam;
	 }

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		  
		   htmlContent = htmlContent + "<td class='text-center'><a href=\"javascript:selectjstg('"+row.id+"','"+row.jstgid+"',"+row.zt+")\">"+formatUndefine(row.jstgh)+"</a></td>";  
		   if(row.zt==0){
			   htmlContent = htmlContent + "<td class='text-center'>未接收</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>已接收</td>";  
		   }
		   if(row.dyzt==0){
			   htmlContent = htmlContent + "<td class='text-center'>未打印</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>已打印</td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+formatUndefine(row.username)+" "+formatUndefine(row.realname)+"'>"+formatUndefine(row.username)+" "+formatUndefine(row.realname)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#Annunciatelist").empty();
	   $("#Annunciatelist").html(htmlContent);
	 
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
	 //信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}
function selectjstg(id,jstgid,zt){
		window.open(basePath+"/project/annunciate/intoViewMainAnnunciate?id="+jstgid+"");
}
//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("fa-chevron-down");
		$("#icon").addClass("fa-chevron-up");
	} else {

		$("#divSearch").css("display", "none");
		$("#icon").removeClass("fa-chevron-up");
		$("#icon").addClass("fa-chevron-down");
	}
}
	
	$('.date-picker').datepicker({
		autoclose : true
	}).next().on("click", function() {
		$(this).prev().focus();
	});
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
			});
	 $('#example27').multiselect({
		buttonClass : 'btn btn-default',
		buttonWidth : 'auto',

		includeSelectAllOption : true
	}); 
	//字段排序
	 function orderBy(obj) {
	 	var orderStyle = $("#" + obj + "_order").attr("class");
	 	$("th[id$=_order]").each(function() { //重置class
	 		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	 	});
	 	$("#" + obj + "_" + "order").removeClass("sorting");
	 	if (orderStyle.indexOf("sorting_asc")>=0) {
	 		$("#" + obj + "_" + "order").addClass("sorting_desc");
	 	} else {
	 		$("#" + obj + "_" + "order").addClass("sorting_asc");
	 	}
	 	orderStyle = $("#" + obj + "_order").attr("class");
	 	var currentPage = $("#pagination li[class='active']").text();
	 	goPage(currentPage,obj,orderStyle.split("_")[1]);
	 }
