
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	changeContractType();
	goPage(1,"auto","desc");//开始的加载默认的内容 
	refreshPermission();
});

//合同类型改变时,加载供应商信息
function changeContractType(){
	var searchParam = {};
	searchParam.gyslb = $("#htlx_search").val();
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryFirmList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   $("#gys_search").empty();
	   var supplierOption = '<option value="" selected="selected" >显示全部All</option>';
	   if(data.length >0){
		   $.each(data,function(index,row){
			   supplierOption += '<option value="'+row.id+'" >'+StringUtil.escapeStr(row.gysmc)+'</option>';
		   });
	   }
	   $("#gys_search").append(supplierOption);
     }
   }); 
}

//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetStatisticsWithSearch(pageNumber,sortType,sequence);
	AjaxGetDetailWithSearch(pageNumber,sortType,sequence);
}	

//带条件搜索的翻页,查询付款统计
function AjaxGetStatisticsWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	url = basePath+"/aerialmaterial/contract/queryPayStatisticsPageList";
	startWait();
	AjaxUtil.ajax({
	   url:url,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendPayStatisticsHtml(data.rows);
			   new Pagination({
					renderTo : "payStatisticsPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetStatisticsWithSearch(pageNumber,sortType,sequence);
					}
				}); 
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[3],"#payStatisticsList tr td");
		   } else {
			  $("#payStatisticsList").empty();
			  $("#payStatisticsPagination").empty();
			  $("#payStatisticsList").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'pay_main_table'});
      }
    }); 
}
//加载付款统计
function appendPayStatisticsHtml(list){
	 
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr bgcolor=\"#fefefe\" >";
		}
		htmlContent += "<td class='text-center'>"+(index+1)+"</td>";	
		htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('contractTypeEnum',row.HTLX) +"</td>"
		htmlContent += "<td title='"+StringUtil.escapeStr(row.HTH)+"' class='text-left'>";
		htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.ID+"')\">"+StringUtil.escapeStr(row.HTH)+"</a>";
		htmlContent += "</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.GYSMC)+"' class='text-left'>"+StringUtil.escapeStr(row.GYSMC)+"</td>";
		htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.HTQDRQ))+"</td>";
		htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('urgencyEnum',row.JJCD) +"</td>";
		htmlContent += "<td class='text-right'>"+formatUndefine(row.YF).toFixed(2)+"</td>";
		htmlContent += "<td class='text-right'>"+formatUndefine(row.WF).toFixed(2)+"</td>";
		
		htmlContent += "<td title='"+formatUndefine(formatUndefine(row.YFBL)+"%("+formatUndefine(row.FKCS)+")")+"' class='text-right'>";
		htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.ID+"')\">"+formatUndefine(row.YFBL)+"%("+formatUndefine(row.FKCS)+")"+"</a>";
		htmlContent += "</td>";
		
		htmlContent += "<td class='text-right'>"+formatUndefine(row.DHBL)+"%"+"</td>";
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
		htmlContent += "</tr>";  
	    
	});
	$("#payStatisticsList").empty();
	$("#payStatisticsList").html(htmlContent);
}

//带条件搜索的翻页,查询付款明细
function AjaxGetDetailWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	url = basePath+"/aerialmaterial/contract/queryPayDetailPageList";
	startWait();
	AjaxUtil.ajax({
	   url:url,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendPayDetailHtml(data.rows);
			   new Pagination({
					renderTo : "payDetailPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetDetailWithSearch(pageNumber,sortType,sequence);
					}
				}); 
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[3],"#payDetailList tr td");
		   } else {
			  $("#payDetailList").empty();
			  $("#payDetailPagination").empty();
			  $("#payDetailList").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'pay_detail_table'});
      }
    }); 
}

//加载付款明细
function appendPayDetailHtml(list){
	 
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr bgcolor=\"#fefefe\" >";
		}
		htmlContent += "<td class='text-center'>"+(index+1)+"</td>";	
		htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('contractTypeEnum',row.HTLX) +"</td>"
		htmlContent += "<td title='"+StringUtil.escapeStr(row.HTH)+"' class='text-left'>";
		htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.ID+"')\">"+StringUtil.escapeStr(row.HTH)+"</a>";
		htmlContent += "</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.GYSMC)+"' class='text-left'>"+StringUtil.escapeStr(row.GYSMC)+"</td>";
		htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.HTQDRQ))+"</td>";
		htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('urgencyEnum',row.JJCD) +"</td>";
		htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.FKRQ))+"</td>";
		htmlContent += "<td class='text-right'>"+formatUndefine(row.FKJE).toFixed(2)+"</td>";
		htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.FKFS)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.BZ)+"' class='text-left'>"+StringUtil.escapeStr(row.BZ)+"</td>";
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
		htmlContent += "</tr>";  
	});
	$("#payDetailList").empty();
	$("#payDetailList").html(htmlContent);
}

//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var keyword = $.trim($("#keyword_search").val());
	 var htlx = $.trim($("#htlx_search").val());
	 var jjcd = $.trim($("#jjcd_search").val());
	 var gysid = $.trim($("#gys_search").val());
	 var htqdrq = $.trim($("#htqdrq_search").val());
	 if('' != htlx){
		 paramsMap.htlx = htlx;
	 }
	 if('' != jjcd){
		 paramsMap.jjcd = jjcd;
	 }
	 if('' != gysid){
		 paramsMap.gysid = gysid;
	 }
	 if(null != htqdrq && "" != htqdrq){
		 var htqdrqBegin = htqdrq.substring(0,10)+" 00:00:00";
		 var htqdrqEnd = htqdrq.substring(13,23)+" 23:59:59";
		 paramsMap.htqdrqBegin = htqdrqBegin;
		 paramsMap.htqdrqEnd = htqdrqEnd;
	 }
	 paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
	 paramsMap.keyword = keyword;
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&t=" + new Date().getTime());
}
	  
//付款统计字段排序
function orderByStatistics(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#payStatisticsPagination li[class='active']").text();
	AjaxGetStatisticsWithSearch(currentPage,obj,orderStyle.split("_")[1]);
}

//付款明细字段排序
function orderByDetail(obj) {
	var orderStyle = $("#" + obj + "_dorder").attr("class");
	$("th[id$=_dorder]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "dorder").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "dorder").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "dorder").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_dorder").attr("class");
	var currentPage = $("#payDetailPagination li[class='active']").text();
	AjaxGetDetailWithSearch(currentPage,obj,orderStyle.split("_")[1]);
}

//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	
	$("#keyword_search").val("");
	
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	$("#dprtcode_search").val(dprtId);
		
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
		 
//导出Excel
function cotractOutExcel(){
	 var keyword = $.trim($("#keyword_search").val());
	 var htlx = $.trim($("#htlx_search").val());
	 var jjcd = $.trim($("#jjcd_search").val());
	 var gysid = $.trim($("#gys_search").val());
	 var htqdrq = $.trim($("#htqdrq_search").val());
	 var dprtcode=$.trim($("#dprtcode_search").val());
	 window.open(basePath+"/aerialmaterial/contract/cotractOutExcel?htlx=" + encodeURIComponent(htlx)+"&yjcd="+encodeURIComponent(jjcd)+"&gysbm="+encodeURIComponent(gysid)+"&sdqdrq="+htqdrq+"&dprtcode="+dprtcode+"&keyword="+encodeURIComponent(keyword));
 	}