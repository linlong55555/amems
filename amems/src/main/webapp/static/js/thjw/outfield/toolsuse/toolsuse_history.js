
//申请单状态
SQDZT = ["","保存","提交","","","","","","作废","指定结束","完成"];

brrowZt = ["","申请","确认","归还","","","","","","指定结束","完成"];

/**
 * 加载航材领用历史记录
 */
function loadHistory(){
	historyGoPage(1,"auto","desc");//开始的加载默认的内容 
}

$('#sqdzt').on('change',function(){
	loadHistory()
});

$('#dprtcode1').on('change',function(){
	loadHistory()
});
 

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function historyGoPage(pageNumber,sortType,sequence){
	historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//带条件搜索的翻页
function historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	
	obj.keyword = $.trim($("#his_keyword_search").val());
	obj.zt = $("#sqdzt").val();
	obj.kcid = $("#kcid").val();
	obj.dprtcode = $("#dprtcode1").val();
	startWait();
	
	AjaxUtil.ajax({
		url:basePath+"/outfield/toolsuse/queryList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			if(data.rows !=""){
				historyAppendContentHtml(data.rows);
				new Pagination({
					renderTo : "historyPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage: function(a,b,c){
						historyAjaxGetDatasWithSearch(a,b,c);
					}
				});	  	   
				// 标记关键字
				signByKeyword($.trim(obj.keyword)
						 ,[1,2,3,4,5]);
				 new Sticky({tableId:'toolUseHistory'});
					   
			} else {
				$("#historyList").empty();
				$("#historyPagination").empty();
				$("#historyList").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
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
		 
   		htmlContent += "<tr   style=\"cursor:pointer\"  >";
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(StringUtil.escapeStr(row.jldh))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.bjh))+"' >"+formatUndefine(StringUtil.escapeStr(row.bjh))+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.ywms))+"' >"+formatUndefine(StringUtil.escapeStr(row.ywms) )+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.zwms))+"'>"+formatUndefine(StringUtil.escapeStr(row.zwms))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.bjxlh) )+"'>"+formatUndefine(StringUtil.escapeStr(row.bjxlh) )+"</td>";
		
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.xh))+"'>"+formatUndefine(StringUtil.escapeStr(row.xh))+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.gg) )+"'>"+formatUndefine(StringUtil.escapeStr(row.gg) )+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+brrowZt[formatUndefine(row.zt)]+"'>"+brrowZt[formatUndefine(row.zt)]+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.jyZrrmc) )+"'>"+formatUndefine(StringUtil.escapeStr(row.jyZrrmc) )+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+formatUndefine((row.jySj||''))+"'>"+formatUndefine((row.jySj||''))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='right' title='"+formatUndefine(row.jySl)+"'>"+formatUndefine(row.jySl)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.jyBz)   )+"'>"+formatUndefine(StringUtil.escapeStr(row.jyBz)   )+"</td>";
		
		var zrrName = (row.jczrr!=null?formatUndefine(StringUtil.escapeStr(row.jczrr.displayName) ):'');
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+zrrName+"'>"+zrrName+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(formatUndefine((row.jcSj||'')))+"'>"+formatUndefine(formatUndefine((row.jcSj||'')))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.jcBz)  )+"'>"+formatUndefine(StringUtil.escapeStr(row.jcBz))+"</td>";
		
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.ghZrrmc) )+"'>"+formatUndefine(StringUtil.escapeStr(row.ghZrrmc) )+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+formatUndefine((row.ghSj||''))+"'>"+formatUndefine((row.ghSj||''))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='right' title='"+formatUndefine(row.ghSl)+"'>"+formatUndefine(row.ghSl)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.ghBz)  )+"'>"+formatUndefine(StringUtil.escapeStr(row.ghBz)  )+"</td>";
		
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+formatUndefine((row.whsj||''))+"'>"+formatUndefine((row.whsj||''))+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.dprtname)   )+"'>"+formatUndefine(StringUtil.escapeStr(row.dprtname))+"</td>";
		
		htmlContent += "</tr>";  
	});
	$("#historyList").empty();
	$("#historyList").html(htmlContent);
	refreshPermission();
}
	 
//字段排序
function historyOrderBy(obj, _obj) {
	$obj = $("#toolUseHistory th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#toolUseHistory .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#toolUseHistory .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#historyPagination li[class='active']").text();
	historyGoPage(currentPage,obj, orderType);
}

//信息检索
function historySearch(){
	historyGoPage(1,"auto","desc");
}
		
//搜索条件重置
function historySearchreset(){
	$("#his_keyword_search").val("");
	$("#sqdzt").val("");
	$("#dprtcode1").val(userJgdm);
	$("#dprtcode1").trigger("change");
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
 
 
	
