
//申请单状态
SQDZT = ["","保存","提交","","","","","","作废","关闭","完成"];

brrowZt = ["","申请","确认","归还","","","","","","关闭","完成"];
 

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function outGoPage(pageNumber,sortType,sequence){
	historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//带条件搜索的翻页
function historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.paramsMap = {
			keyword:$.trim($("#out_keyword_search").val())
			,dprtcode:$.trim($("#dprtcodeOut").val())
	};
	 
	 var outDate = $.trim($("#outDate").val());
	 if(outDate != ''){
		 obj.paramsMap.dateBegin = outDate.substring(0,10);
		 obj.paramsMap.dateEnd = outDate.substring(13,23);
	 }
	 
	startWait();
	
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/stock/querylist4Out",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		async : false,
		data:JSON.stringify(obj),
		success:function(data){
			if(data.rows !=""){
				historyAppendContentHtml(data.rows);
				new Pagination({
					renderTo : "PaginationOut",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage: function(a,b,c){
						historyAjaxGetDatasWithSearch(a,b,c);
					}
				});	  	   
				// 标记关键字
				signByKeyword(obj.paramsMap.keyword,[1,2,3,4,6,7,8]);
				
				new Sticky({tableId : 'outstock'});
					   
			} else {
				$("#outstock #list").empty();
				$("#outstock #PaginationOut").empty();
				$("#outstock #list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			finishWait();
	    }
	}); 
}


/**
 * 拼接表格内容
 * @param list
 * @returns
 */
function historyAppendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		 
		if (index % 2 == 0) {
			htmlContent = htmlContent
					+ "<tr   bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent
					+ "<tr   bgcolor=\"#fefefe\">";
		}
   		
   		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+StringUtil.escapeStr(row.outstock.ckdh)+"'>"+StringUtil.escapeStr(row.outstock.ckdh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.bjh)+"'>"+StringUtil.escapeStr(row.materialHistory.bjh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.ywms)+"'>"+StringUtil.escapeStr(row.materialHistory.ywms)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.zwms)+"'>"+StringUtil.escapeStr(row.materialHistory.zwms)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.gljbName)+"'>"+StringUtil.escapeStr(row.materialHistory.gljbName)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.sn)+"'>"+StringUtil.escapeStr(row.materialHistory.sn)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.pch)+"'>"+StringUtil.escapeStr(row.materialHistory.pch)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.grn)+"'>"+StringUtil.escapeStr(row.materialHistory.grn)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='right' title='"+formatUndefine(row.materialHistory.kcsl)+"'>"+formatUndefine(row.materialHistory.kcsl)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+formatUndefine((row.outstock.cksj||'').substring(0,10))+"'>"+formatUndefine((row.outstock.cksj||'').substring(0,10)) +"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.outstock.department.dprtname)+"'>"+StringUtil.escapeStr(row.outstock.department.dprtname)+"</td>";   
		htmlContent += "</tr>";
		
		htmlContent += "</tr>";  
	});
	$("#outstock #list").empty();
	$("#outstock #list").html(htmlContent);
	refreshPermission();
}
	 
//字段排序
function orderByOut(obj, _obj) {
	
	$obj = $("#outstock th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#outstock .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#outstock .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#PaginationOut li[class='active']").text();
	outGoPage(currentPage,obj, orderType);
}

/*$("#dprtcodeOut").on("change",function(){
	window.outGoPage(1,"auto","desc")
});*/
	
//信息检索
function searchOut(){
	outGoPage(1,"auto","desc");
}
	
		
	
//搜索条件重置
function searchresetOut(){
	$("#divSearch1").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch1").find("select").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch1").find("textarea").each(function(){
		$(this).val("");
	});
	
	$('#divSearch1 #dprtcodeOut option:eq(0)').attr('selected','selected');
	setDefaultOutDate();
	window.outGoPage(1,"auto","desc")
}
	
 
//搜索条件显示与隐藏 
function his_more_params() {
	if ($("#divSearch1").css("display") == "none") {
		$("#divSearch1").css("display", "block");
		$("#icon1").removeClass("icon-caret-down");
		$("#icon1").addClass("icon-caret-up");
	} else {
		$("#divSearch1").css("display", "none");
		$("#icon1").removeClass("icon-caret-up");
		$("#icon1").addClass("icon-caret-down");
	}
}

$('#outDate').daterangepicker({
	 
	 format : 'YYYY-MM-DD HH:mm:ss',
	 autoUpdateInput: false,
	 autoApply: true,
	 locale : {  
       applyLabel : '确定',  
       cancelLabel : '清除',  
       fromLabel : '起始时间',  
       toLabel : '结束时间',  
       customRangeLabel : '自定义',  
       daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
       monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
               '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
       firstDay : 1  
   }  
}).prev().on("click", function() {
		$(this).next().focus();
});

$('#outDate').on('cancel.daterangepicker', function(ev, picker) {
   $(this).val('');
});
 
function setDefaultOutDate(){
	$("#outDate").data('daterangepicker').setStartDate(startDate);
	$("#outDate").data('daterangepicker').setEndDate(endDate);
	$("#outDate").val(startDate+" ~ "+endDate);
}

$(function(){
	setDefaultOutDate();
});
 
 
	
