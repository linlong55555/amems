
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];

$(document).ready(function(){
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		var selectTab = $("#tablist").find("li.active").index();
		
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				if(selectTab == 0){
					$('#InStockList').trigger('click');
				}else if(selectTab == 1){
					$('#OutStockList').trigger('click');
				}
			}
		}
	});
	
	
	setDefaultInDate();
	Navigation(menuCode);
	refreshPermission();
	/*$("#dprtcode1").on("change",function(){
		window.goPage(1,"auto","desc")
	});*/
	
	goPage(1,"auto","desc");//开始的加载默认的内容 
});

search
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	//obj.paramsMap = {status : $("input:radio[name='brrowStatus']").filter(':checked').val()};
	
	obj.paramsMap = {
			keyword:$.trim($("#keyword_search").val())
			,dprtcode:$.trim($("#ilist #dprtcode1").val())
			 
	};
	
	 var inDate = $.trim($("#inDate").val());
	 if(inDate != ''){
		 obj.paramsMap.dateBegin = inDate.substring(0,10);
		 obj.paramsMap.dateEnd = inDate.substring(13,23);
	 }
	 
	startWait();
	
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/stock/querylist4In",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		async : false,
		data:JSON.stringify(obj),
		success:function(data){
			if(data.rows !=""){
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "instock_pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});	   
					// 标记关键字
				signByKeyword(obj.paramsMap.keyword,[1,2,3,4,6,7,8]);
				
				new Sticky({tableId : 'instock'});
					   
			} else {
				
				$("#instock #list").empty();
				$("#instock #instock_pagination").empty();
				$("#instock #list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			finishWait();
	    }
	}); 
	
	  refreshPermission();
}

/**
 * 拼接表格内容
 * @param list
 * @returns
 */
function appendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		
		if (index % 2 == 0) {
			htmlContent = htmlContent
					+ "<tr   bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent
					+ "<tr   bgcolor=\"#fefefe\">";
		}
   		
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+StringUtil.escapeStr(row.instockDetail.instock.rkdh)+"' >"+StringUtil.escapeStr(row.instockDetail.instock.rkdh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.bjh)+"' >"+StringUtil.escapeStr(row.materialHistory.bjh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.ywms)+"' >"+StringUtil.escapeStr(row.materialHistory.ywms)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.zwms)+"' >"+StringUtil.escapeStr(row.materialHistory.zwms)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.gljbName)+"' >"+StringUtil.escapeStr(row.materialHistory.gljbName)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.sn)+"' >"+StringUtil.escapeStr(row.materialHistory.sn)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.pch)+"' >"+StringUtil.escapeStr(row.materialHistory.pch)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.materialHistory.grn)+"' >"+StringUtil.escapeStr(row.materialHistory.grn)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='right' title='"+formatUndefine(row.materialHistory.kcsl)+"' >"+formatUndefine(row.materialHistory.kcsl)+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='center' title='"+formatUndefine((row.instockDetail.instock.rksj||'').substring(0,10))+"' >"+
		formatUndefine((row.instockDetail.instock.rksj||'').substring(0,10))
		+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.instockDetail.instock.department.dprtname)+"' >"+StringUtil.escapeStr(row.instockDetail.instock.department.dprtname)+"</td>";   
		htmlContent += "</tr>";  
	});
	$("#instock #list").empty();
	$("#instock #list").html(htmlContent);
	refreshPermission();
}

	 
//字段排序
function orderBy(obj, _obj) {
	
	$obj = $("#instock th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#instock .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#instock .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#instock_pagination li[class='active']").text();
	goPage(currentPage,obj, orderType);
	 
}
	
	
	
//信息检索
function search(){
	goPage(1,"auto","desc");
}
	
		
//搜索条件显示与隐藏 
function more_params() {
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



/**
 * 借用申请
 * @returns
 */
function borrowApply(kcid){
	
    $('#applyModal #jyBz').val('');
	$('#applyModal #jyZrrid').val('');
	$('#applyModal #jyZrrmc').val('');
	
	$('#applyModal').modal('show');
	$('#applyModal #kcid').val(kcid);
}
/**
 * 借用申请
 * @returns
 */
function doApply(){
	
	$('#applyform').data('bootstrapValidator').validate();
	if (!$('#applyform').data('bootstrapValidator').isValid()) {
		return false;
	}
	else{
		//提交数据：借用人id，借用人名称  借用备注,库存ID
		var kcid = $('#applyModal #kcid').val();
		var jyBz = $.trim($('#applyModal #jyBz').val());
		var jyZrrid = $('#applyModal #jyZrrid').val();
		var jyZrrmc = $.trim($('#applyModal #jyZrrmc').val());
		
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/apply",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				kcid:kcid
				,jyBz:jyBz
				,jyZrrid:jyZrrid
				,jyZrrmc:jyZrrmc
				 
				}),
			success:function(data){
				
				search();
				$("#requisiton_message").html("借用申请成功").fadeIn().fadeOut(3000);
		    }
		}); 
		
		
	}
	
}

/**
 * 借出确认
 * @param id
 */
function borrowConfirmation(brrowId){
	
	$('#confirmModal #jcBz').val('');
	$('#confirmModal #id').val(brrowId);
	$('#confirmModal').modal('show');
	
}
/**
 * 借出确认
 * @param id
 */
function doBorrowConfirmation(){
	
	$('#confirmfrom').data('bootstrapValidator').validate();
	if (!$('#confirmfrom').data('bootstrapValidator').isValid()) {
		return false;
	}
	else{
		//提交数据：借用人id，借用人名称  借用备注,库存ID
		var id = $('#confirmModal #id').val();
		var jcBz = $.trim($('#confirmModal #jcBz').val());
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/confirm",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				id:id
				,jcBz:jcBz
				}),
			success:function(data){
				search();
				$("#requisiton_message").html("借出确认成功").fadeIn().fadeOut(3000);
		    }
		}); 
		
	}
}


/**
 * 快速借出
 * @param id
 */
function borrowConfirmationFast(kcid){
	
	$('#confirmFastModal #jcBz').val('');
	$('#confirmFastModal #jyZrrid').val('');
	$('#confirmFastModal #jyZrrmc').val('');
	$('#confirmFastModal #kcid').val(kcid);
	$('#confirmFastModal').modal('show');
	
}
/**
 * 快速借出
 * @param id
 */
function doConfirmationFast(){
	
	$('#confirmfastform').data('bootstrapValidator').validate();
	if (!$('#confirmfastform').data('bootstrapValidator').isValid()) {
		return false;
	}
	else{
		//提交数据：借用人id，借用人名称  借用备注,库存ID
		var kcid = $('#confirmFastModal #kcid').val();
		var jyBz = $.trim($('#confirmFastModal #jyBz').val());
		var jyZrrid = $('#confirmFastModal #jyZrrid').val();
		var jyZrrmc = $('#confirmFastModal #jyZrrmc').val();
		 
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/confirmFast",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				  kcid:kcid
				,jyBz:jyBz
				,jyZrrid:jyZrrid
				,jyZrrmc:jyZrrmc
				}),
			success:function(data){
				search();
				$("#requisiton_message").html("借出确认成功").fadeIn().fadeOut(3000);
		    }
		}); 
		
	}
}





function revert(brrowId){
	
	$('#revertModal #id').val(brrowId);
	$('#revertModal #ghBz').val('');
	$('#revertModal #ghZrrid').val('');
	$('#revertModal #ghZrrmc').val('');
	$('#revertModal').modal('show');
	
}

/**
 * 归还
 * @param id
 */
function doRevert(){
	
	$('#revertform').data('bootstrapValidator').validate();
	if (!$('#revertform').data('bootstrapValidator').isValid()) {
		return false;
	}
	else{
		//提交数据：借用人id，借用人名称  借用备注,库存ID
		var id = $('#revertModal #id').val();
		var ghBz = $.trim($('#revertModal #ghBz').val());
		var ghZrrid = $('#revertModal #ghZrrid').val();
		var ghZrrmc = $.trim($('#revertModal #ghZrrmc').val());
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/revert",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				id:id
				,ghBz:ghBz
				,ghZrrid:ghZrrid
				,ghZrrmc:ghZrrmc
				}),
			success:function(data){
				search();
				$("#requisiton_message").html("归还成功").fadeIn().fadeOut(3000);
		    }
		}); 
		
	}
}
 
 
/**
 * 指定结束
 * @param id
 */
function end(id){
	//提交数据：借用人id，借用人名称  借用备注,库存ID
	AjaxUtil.ajax({
		url:basePath+"/outfield/toolsuse/end",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify({
			id:id
			}),
		success:function(data){
			search();
			$("#requisiton_message").html("指定结束成功").fadeIn().fadeOut(3000);
	    }
	}); 
	
}

//搜索条件重置
function searchreset(){
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	
	$('#divSearch #dprtcode1 option:eq(0)').attr('selected','selected');
	setDefaultInDate(); 
	window.goPage(1,"auto","desc")
}

$('#inDate').daterangepicker({
	 
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

$('#inDate').on('cancel.daterangepicker', function(ev, picker) {
    $(this).val('');
});
  
function setDefaultInDate(){
	$("#inDate").data('daterangepicker').setStartDate(startDate);
	$("#inDate").data('daterangepicker').setEndDate(endDate);
	$("#inDate").val(startDate+" ~ "+endDate);
}

function goHistory(kcid) {
	 
	$('#kcid').val(kcid);
	$('#loadhis').trigger('click');
}
 
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",
	function() {
		$(this).next().focus();
	}
);
$('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',
	includeSelectAllOption : true
}); 
	
	
