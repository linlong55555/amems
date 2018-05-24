var hcjz=["","一般","中等","高额"]
$(document).ready(function(){
	goPage(1,"auto","desc");
});
//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
	 paramsMap.zt = $.trim($("#zt").val());
	 paramsMap.hclx = $.trim($("#hclx").val());
	 paramsMap.dprtcode = $.trim($("#zzjg").val());
	 var xhrq = $.trim($("#xhrq").val());
	 if(null != xhrq && "" != xhrq){
		 var xhrqBegin = xhrq.substring(0,10)+" 00:00:00";
		 var xhrqEnd = xhrq.substring(13,23)+" 23:59:59";
		 paramsMap.xhrqBegin = xhrqBegin;
		 paramsMap.xhrqEnd = xhrqEnd;
	 }
	 searchParam.paramsMap=paramsMap;
	 
	 return searchParam;
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var paramsMap = {};
	annunciateitemData=[];
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/destructionlist/main",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
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
					}
				});
			// 标记关键字
			   signByKeyword($("#keyword_search").val(),[2,7,8,11]);
		   } else {
			  $("#destructionDetaillist").empty();
			  $("#pagination").empty();
			  $("#destructionDetaillist").append("<tr class='text-center'><td colspan=\"17\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'quality_check_list_table'});
     }
   }); 
	
}

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" >";
		   } 
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>"+(index+1)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.hCMainData.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.hCMainData.bjh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hCMainData.ywms))+"'>"+StringUtil.escapeStr(formatUndefine(row.hCMainData.ywms))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hCMainData.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.hCMainData.zwms))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.materialHistory.kcsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.materialHistory.kcsl))+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hCMainData.gljb)+"'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hCMainData.gljb)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.materialHistory.sn))+"'>"+StringUtil.escapeStr(formatUndefine(row.materialHistory.sn))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.materialHistory.pch))+"'>"+StringUtil.escapeStr(formatUndefine(row.materialHistory.pch))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(hcjz[row.hCMainData.hcjz]))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.materialHistory.kccb))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.destruction.xhdh))+"'><a href=\"javascript:selectDestruction('"+row.mainid+"')\">"+StringUtil.escapeStr(formatUndefine(row.destruction.xhdh))+"</a></td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+row.destruction.ztText+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.destruction.xhrq).substring(0,10))+"'>"+StringUtil.escapeStr(formatUndefine(row.destruction.xhrq).substring(0,10))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hCMainData.cjjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.hCMainData.cjjh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hCMainData.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hCMainData.hclx)+"</td>";  	    
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hCMainData.jldw))+"'>"+StringUtil.escapeStr(formatUndefine(row.hCMainData.jldw))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#destructionDetaillist").empty();
	   $("#destructionDetaillist").html(htmlContent);
	   
	   refreshPermission();
	 
}
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
//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
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
}
//搜索条件重置
function searchreset(){
	 var zzjgid=$('#zzjgid').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#zzjg").val(zzjgid);
	 $("#yjjb").val(yjts);
	 changeContent();
}

/**
 * 导出Excel
 */
function exportExcel() {
	 var keyword = $.trim($("#keyword_search").val());//关键字查询
	 var zt = $.trim($("#zt").val());
	 var hclx = $.trim($("#hclx").val());
	 var dprtcode = $.trim($("#zzjg").val());
	 var xhrq = $.trim($("#xhrq").val());
	 var xhrqBegin='';
	 var xhrqEnd='';
	 if(null != xhrq && "" != xhrq){
		 var xhrqBegin = xhrq.substring(0,10)+" 00:00:00";
		 var xhrqEnd = xhrq.substring(13,23)+" 23:59:59";
	 }
	window.open(basePath+"/aerialmaterial/destructionlist/DestructionList.xls?keyword="
			+ encodeURIComponent(keyword) + "&zt=" + encodeURIComponent(zt) + "&hclx="
			+ encodeURIComponent(hclx) + "&dprtcode=" + encodeURIComponent(dprtcode) + "&xhrqBegin="
			+ encodeURIComponent(xhrqBegin) + "&xhrqEnd=" + encodeURIComponent(xhrqEnd));
	
}

function selectDestruction(id,dprtcode){
	window.open(basePath+"/aerialmaterial/destruction/view?id="+id+"");
}