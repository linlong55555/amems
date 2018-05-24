$(document).ready(function(){
	goPage(1,"auto","desc");
});
//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("quality_check_list_table");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 paramsMap.keyword = $.trim($("#keyword_search").val());//关键字查询
	 paramsMap.kc = $.trim($("#kcKeyword").val());
	 paramsMap.ry = $.trim($("#ryKeyword").val());
	 paramsMap.gw = $.trim($("#gwKeyword").val());
	 /*var sjKsrq = $.trim($("#sjKsrq").val());
	 var xcpxrq = $.trim($("#xcpxrq").val());
	 if(null != sjKsrq && "" != sjKsrq){
		 var sjKsrqBegin = sjKsrq.substring(0,10)+" 00:00:00";
		 var sjKsrqEnd = sjKsrq.substring(13,23)+" 23:59:59";
		 paramsMap.sjKsrqBegin = sjKsrqBegin;
		 paramsMap.sjKsrqEnd = sjKsrqEnd;
	 }
	 if(null != xcpxrq && "" != xcpxrq){
		 var xcpxrqBegin = xcpxrq.substring(0,10)+" 00:00:00";
		 var xcpxrqEnd = xcpxrq.substring(13,23)+" 23:59:59";
		 paramsMap.xcpxrqBegin = xcpxrqBegin;
		 paramsMap.xcpxrqEnd = xcpxrqEnd;
	 }
	 paramsMap.is_ypx = $.trim($("#is_ypx").val());*/
	 searchParam.paramsMap=paramsMap;
	 searchParam.dprtcode=$("#zzjg").val();
	 
	 return searchParam;
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/training/posttracking/main",
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
			   signByKeyword($("#keyword_search").val(),[14,16]);
		   } else {
			  $("#posttrackingList").empty();
			  $("#pagination").empty();
			  $("#posttrackingList").append("<tr class='text-center'><td colspan=\"20\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'quality_check_list_table'});
     }
   }); 
	
}
//行变色
function showChildTR(obj){
	//行变色
	$(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
	new Sticky({tableId:'quality_check_list_table'});
}

function appendContentHtml(list){
	 var htmlContent='';
	 $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
		   }
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.dgbh)+"/"+StringUtil.escapeStr(row.paramsMap.dgmc)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.dgbh)+"/"+StringUtil.escapeStr(row.paramsMap.dgmc)+"</td>";
		   htmlContent += "<td class='text-center'>"+(row.zt==0?'申请中':'已授权')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcbh)+"' class='text-left'><a href=\"javascript:courseView('"+row.paramsMap.kcid+"')\">"+StringUtil.escapeStr(row.paramsMap.kcbh)+"</a></td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcmc)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.kcmc)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.lb)+"' class='text-left'>"+StringUtil.escapeStr(row.lb)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.rybh)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.rybh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.xm)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.xm)+"</td>";
		   htmlContent += "<td  class='text-center'>"+(row.paramsMap.is_fx==0?'否':'是')+"</td>";
		   htmlContent += "<td  class='text-center'>"+StringUtil.escapeStr(row.paramsMap.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',StringUtil.escapeStr(row.paramsMap.zqdw))+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.xcpxrq).substring(0,10)+"' class='text-center'>"+StringUtil.escapeStr(row.paramsMap.xcpxrq).substring(0,10)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.sj_ksrq).substring(0,10)+"' class='text-center'><a href=\"javascript:pxjhView('"+(row.paramsMap.scpxjhid)+"')\">"+StringUtil.escapeStr(row.paramsMap.sj_ksrq).substring(0,10)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.jhsj).substring(0,10)+"' class='text-center'><a href=\"javascript:pxjhView('"+(row.paramsMap.pxjhid)+"')\">"+StringUtil.escapeStr(row.paramsMap.jhsj?(row.paramsMap.jhsj).substring(0,10):'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.pxgh)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.pxgh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcdd)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.kcdd)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.jsxm)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.jsxm)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.cql)+"' class='text-right'>"+StringUtil.escapeStr(row.paramsMap.cql)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.cj)+"' class='text-right'>"+StringUtil.escapeStr(row.paramsMap.cj)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.zs)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.zs)+"</td>";
		   var istg = "";
		   if(row.paramsMap.khjg != null){
			   if(row.paramsMap.khjg==1){
				   istg ="通过";
			   }
			   if(row.paramsMap.khjg==0){
				   istg ="未通过";
			   }
			   
		   }
		   htmlContent += "<td title='"+istg+"' class='text-center'>"+istg+"</td>";
		   htmlContent += "</tr>";  
	 });
	 $("#posttrackingList").empty();
	 $("#posttrackingList").append(htmlContent);
}
function courseView(id){
	window.open(basePath+"/training/course/view?id=" + id);
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
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
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
	 $("#kcKeyword").val("");
	 $("#ryKeyword").val("");
	 $("#zzjg").val(zzjgid);
	 $("#yjjb").val(yjts);
	 changeContent();
}



function ryView(id){
	window.open(basePath+"/quality/maintenancepersonnel/view/" + id);
}
function pxjhView(id){
	if(id != null && id!=""){
		window.open(basePath+"/training/trainingnotice/find/" + id);
	}
}


function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());//关键字查询
	var kcKeyword = $.trim($("#kcKeyword").val());
	var ryKeyword = $.trim($("#ryKeyword").val());
	
	 var sjKsrq = $.trim($("#sjKsrq").val());
	 var xcpxrq = $.trim($("#xcpxrq").val());
	 
	 var sjKsrqBegin ="";
	 var sjKsrqEnd ="";
	 var xcpxrqBegin="";
	 var xcpxrqEnd="";
	 if(null != sjKsrq && "" != sjKsrq){
		  sjKsrqBegin = sjKsrq.substring(0,10)+" 00:00:00";
		  sjKsrqEnd = sjKsrq.substring(13,23)+" 23:59:59";
	
	 }
	 if(null != xcpxrq && "" != xcpxrq){
		  xcpxrqBegin = xcpxrq.substring(0,10)+" 00:00:00";
		  xcpxrqEnd = xcpxrq.substring(13,23)+" 23:59:59";
	 }
	 var dprtcode=$("#zzjg").val();
	 
	window.open(basePath+"/training/tracking/PersonnelTrainingTracking.xls?dprtcode="
 				+ dprtcode + "&kcKeyword="+encodeURIComponent(kcKeyword)
 				+"&ryKeyword="+encodeURIComponent(ryKeyword)
 				+"&sjKsrqBegin="+sjKsrqBegin
 				+"&sjKsrqEnd="+sjKsrqEnd
 				+"&xcpxrqBegin="+xcpxrqBegin
 				+"&xcpxrqEnd="+xcpxrqEnd
 				+"&keyword="+ encodeURIComponent(keyword));
}



function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());//关键字查询
	var kc = $.trim($("#kc").val());
	var ry = $.trim($("#ry").val());
	var gw = $.trim($("#gw").val());
	var dprtcode=$("#zzjg").val();
	 
	window.open(basePath+"/training/posttracking/posttracking.xls?dprtcode="
 				+ dprtcode + "&paramsMap[kc]="+encodeURIComponent(kc)
 				+"&paramsMap[ry]="+encodeURIComponent(ry)
 				+"&paramsMap[gw]="+encodeURIComponent(gw)
 				+"&paramsMap[keyword]="+ encodeURIComponent(keyword));
}