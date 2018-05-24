
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];

var pagination = {};

$(document).ready(function(){
	Navigation(menuCode);
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			var selectTab = $("#tablist").find("li.active").index();
			
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				if(selectTab == 0){
					$('#requisitionSearch').trigger('click');
				}else if(selectTab == 1){
					$('#requisitionHistorySearch').trigger('click');
				}
			}
		}
	});
	
	
	 $('#dprtcode').on('change',function(){
		 goPage(1,"auto","desc");
	 });
	 
	 $('#dprtcode').trigger("change");
	 
	goPage(1,"auto","desc");//开始的加载默认的内容 
	validationApply();
	validationConfirmFast();
	validationConfirm();
	validationRevert();
	
	 
	refreshPermission();
	 
	 
	
});

function validationConfirm(){
	$('#confirmfrom').bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			 
			jcBz : {
				validators : {
					notEmpty : {
						message : '确认备注不能为空'
					},
					stringLength : {
						max : 300,
						message : '确认备注不能超出300个字符'
					}
				}
			}
			
		}
	});
}

function validationConfirmFast(){
	$('#confirmfastform').bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			jyZrrmc : {
				validators : {
					notEmpty : {
						message : '借用人不能为空'
					}
					 
				}
			},
		 
			jyBz : {
				validators : {
					notEmpty : {
						message : '借用备注不能为空'
					},
					stringLength : {
						max : 300,
						message : '借用备注不能超出300个字符'
					}
				}
			}
			
		}
	});
}

function validationApply(){
	$('#applyform').bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			jyZrrmc : {
				validators : {
					notEmpty : {
						message : '借用人不能为空'
					}
					 
				}
			},
		 
			jyBz : {
				validators : {
					notEmpty : {
						message : '借用备注不能为空'
					},
					stringLength : {
						max : 300,
						message : '借用备注不能超出300个字符'
					}
				}
			}
			
		}
	});
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sortField){
	var param ={};
	param.pagination = {page:pageNumber,sort:sortType,order:sortField,rows:20};
	param.keyword = $.trim($("#keyword_search").val());
	param.paramsMap = {
			status : $("input:radio[name='brrowStatus']").filter(':checked').val()
			,dprtcode:$('#dprtcode').val()
			};
	
	
	
	startWait();
	pagination = param.pagination;
	AjaxUtil.ajax({
		url:basePath+"/outfield/toolsuse/queryList4Tool",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(param),
		success:function(data){
			if(data.rows !=""){
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sortField,
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});	   
					// 标记关键字
				signByKeyword(param.keyword,[2,3,4,5]);
					   
			} else {
				
				$("#toolUse #list").empty();
				$("#toolUse #pagination").empty();
				$("#toolUse #list").append("<tr><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
			}
			finishWait();
	    }
	}); 
	  refreshPermission();
	  
	  new Sticky({tableId:'toolUse'});
}

/**
 * 拼接表格内容
 * @param list
 * @returns
 */
function appendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
   		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
		htmlContent += "<td class='text-center'><div>";
		if(row.borrowStatus =='3' ){
			htmlContent += "<i class='icon-circle-arrow-left color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsuse:main:01' onClick=\"borrowApply('"+ row.id+"');\" title='借用申请  Borrow'></i>&nbsp;&nbsp;";
			htmlContent += "<i class='icon-thumbs-up-alt color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsuse:main:04' onClick=\"borrowConfirmationFast('"+ row.id+"');\" title='快速借用确认 Quick Borrow'></i>&nbsp;&nbsp;";
		}
		
		if(row.borrowStatus =='1' ){
			 
			htmlContent += "<i class='icon-ok color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsuse:main:02' onClick=\"borrowConfirmation('"+ row.brrowId + "','"+ row.id+"');\" title='借用确认 Borrow Confirmation'></i>&nbsp;&nbsp;";
		}
		
		if(row.borrowStatus =='2' ){
			htmlContent += "<i class='icon-circle-arrow-right color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsuse:main:03' onClick=\"revert('"+ row.brrowId + "','"+ row.id+"');\" title='归还 Revert'></i>&nbsp;&nbsp;";
		}
		
		if(row.brrowId !=undefined && row.brrowId!=null  && row.brrowId!='' ){
			htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsuse:main:05' onClick=\"end('"+ row.brrowId + "','"+ row.id+"');\" title='指定结束 End'></i>&nbsp;&nbsp;";
		}
		
		htmlContent += "</div></td>";
		
		
//		htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.bjh))+"'><a href='#' onClick=\"goHistory('"+ row.id + "');\" >"+formatUndefine(StringUtil.escapeStr(row.bjh))+"</a></td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.ywms))+"'>"+formatUndefine(StringUtil.escapeStr(row.ywms) )+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.zwms))+"'>"+formatUndefine(StringUtil.escapeStr(row.zwms) )+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.sn))+"'>"+formatUndefine(StringUtil.escapeStr(row.sn) )+"</td>";
		
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.xh))+"'>"+formatUndefine(StringUtil.escapeStr(row.xh) )+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.gg) )+"' >"+formatUndefine(StringUtil.escapeStr(row.gg) )+"</td>";
		htmlContent += "<td style='vertical-align: middle; ' align='right' title='"+formatUndefine(row.kcsl)+"'>"+formatUndefine(row.kcsl)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(row.borrowStatusText)+"' >"+formatUndefine(row.borrowStatusText)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.position))+"'>"+formatUndefine(StringUtil.escapeStr(row.position) )+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+formatUndefine(StringUtil.escapeStr(row.dprtname))+"'>"+formatUndefine(StringUtil.escapeStr(row.dprtname) )+"</td>";   
		htmlContent += "</tr>";  
	});
	$("#toolUse #list").empty();
	$("#toolUse #list").html(htmlContent);
	refreshPermission();
}

	 
//字段排序
function orderBy(obj, _obj) {
	
	$obj = $("#toolUse th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#toolUse .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#toolUse .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj, orderType);
}
	
//信息检索
function search(){
	goPage(1,"auto","desc");
}
	
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
		
//搜索条件重置
function searchreset(){
	
	$("#keyword_search").val("");
	$("#hclx").val("");
	$("#ckh").val("");
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

//隐藏Modal时验证销毁重构
$("#applyModal").on("hidden.bs.modal", function() {
	 $("#applyform").data('bootstrapValidator').destroy();
     $('#applyform').data('bootstrapValidator', null);
     validationApply();
});

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
				,dprtcode:$('#dprtcode').val()
				,jyBz:jyBz
				,jyZrrid:jyZrrid
				,jyZrrmc:jyZrrmc
				 
				}),
			success:function(data){
				refreshPage();
				if(data.isLegitimate){
					$("#requisiton_message").removeClass("alert-danger");
					$("#requisiton_message").addClass("alert-success");
					$("#requisiton_message").html("借用申请成功").fadeIn().fadeOut(3000);
				}
				else{
					 
					$("#requisiton_message").removeClass("alert-success");
					$("#requisiton_message").addClass("alert-danger");
					$("#requisiton_message").html(data.message).fadeIn().fadeOut(3000);
				}
		    }
		}); 
		
		
	}
	
}


//隐藏Modal时验证销毁重构
$("#confirmModal").on("hidden.bs.modal", function() {
   $("#confirmfrom").data('bootstrapValidator').destroy();
   $('#confirmfrom').data('bootstrapValidator', null);
   validationConfirm();
});

/**
 * 借出确认
 * @param id
 */
function borrowConfirmation(brrowId,kcid){
	
	$('#confirmModal #jcBz').val('');
	$('#confirmModal #id').val(brrowId);
	$('#confirmModal #kcid').val(kcid);
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
		var kcid = $('#confirmModal #kcid').val();
		var jcBz = $.trim($('#confirmModal #jcBz').val());
		var dprtcode = $.trim($('#dprtcode').val());
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/confirm",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				id:id
				,kcid:kcid
				,jcBz:jcBz
				,dprtcode:dprtcode
				}),
			success:function(data){
				refreshPage();
				//$("#requisiton_message").html("借出确认成功").fadeIn().fadeOut(3000);
				
				if(data.isLegitimate){
					$("#requisiton_message").removeClass("alert-danger");
					$("#requisiton_message").addClass("alert-success");
					$("#requisiton_message").html("借出确认成功").fadeIn().fadeOut(3000);
				}
				else{
					 
					$("#requisiton_message").removeClass("alert-success");
					$("#requisiton_message").addClass("alert-danger");
					$("#requisiton_message").html(data.message).fadeIn().fadeOut(3000);
				}
				
		    }
		}); 
		
	}
}


$('#borrowConfirmationBtn').on('click',doBorrowConfirmation)

/**
 * 快速借出
 * @param id
 */
function borrowConfirmationFast(kcid){ 
	$('#confirmFastModal #jyBz').val('');
	$('#confirmFastModal #jyZrrid').val('');
	$('#confirmFastModal #jyZrrmc').val('');
	$('#confirmFastModal #kcid').val(kcid);
	$('#confirmFastModal').modal('show');
	
}


//隐藏Modal时验证销毁重构
$("#confirmFastModal").on("hidden.bs.modal", function() {
   $("#confirmfastform").data('bootstrapValidator').destroy();
   $('#confirmfastform').data('bootstrapValidator', null);
   validationConfirmFast();
});

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
			    ,dprtcode:$('#dprtcode').val()
				,jyBz:jyBz
				,jyZrrid:jyZrrid
				,jyZrrmc:jyZrrmc
				}),
			success:function(data){
				refreshPage();
				if(data.isLegitimate){
					$("#requisiton_message").removeClass("alert-danger");
					$("#requisiton_message").addClass("alert-success");
					$("#requisiton_message").html("借出确认成功").fadeIn().fadeOut(3000);
				}
				else{
					 
					$("#requisiton_message").removeClass("alert-success");
					$("#requisiton_message").addClass("alert-danger");
					$("#requisiton_message").html(data.message).fadeIn().fadeOut(3000);
				}
				
		    }
		}); 
		
	}
}



//隐藏Modal时验证销毁重构
$("#revertModal").on("hidden.bs.modal", function() {
	 $("#revertform").data('bootstrapValidator').destroy();
     $('#revertform').data('bootstrapValidator', null);
     validationRevert();
});

function validationRevert(){
	$('#revertform').bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			ghZrrmc : {
				validators : {
					notEmpty : {
						message : '归还人不能为空'
					}
					 
				}
			},
		 
			ghBz : {
				validators : {
					notEmpty : {
						message : '归还备注不能为空'
					},
					stringLength : {
						max : 300,
						message : '归还备注不能超出300个字符'
					}
				}
			}
			
		}
	});
}



function revert(brrowId,kcid){
	$('#revertModal #kcid').val(kcid);
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
		
		var kcid = $('#revertModal #kcid').val();
		var id = $('#revertModal #id').val();
		var ghBz = $.trim($('#revertModal #ghBz').val());
		var ghZrrid = $('#revertModal #ghZrrid').val();
		var ghZrrmc = $.trim($('#revertModal #ghZrrmc').val());
		var dprtcode = $.trim($('#dprtcode').val());
		AjaxUtil.ajax({
			url:basePath+"/outfield/toolsuse/revert",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				id:id
				,dprtcode:dprtcode
				,kcid:kcid
				,ghBz:ghBz
				,ghZrrid:ghZrrid
				,ghZrrmc:ghZrrmc
				}),
			success:function(data){
				refreshPage();
				//$("#requisiton_message").html("归还成功").fadeIn().fadeOut(3000);
				
				if(data.isLegitimate){
					$("#requisiton_message").removeClass("alert-danger");
					$("#requisiton_message").addClass("alert-success");
					$("#requisiton_message").html("归还成功").fadeIn().fadeOut(3000);
				}
				else{
					 
					$("#requisiton_message").removeClass("alert-success");
					$("#requisiton_message").addClass("alert-danger");
					$("#requisiton_message").html(data.message).fadeIn().fadeOut(3000);
				}
		    }
		}); 
		
	}
}
 
 
/**
 * 指定结束
 * @param id
 */
function end(id,kcid){
	var dprtcode = $.trim($('#dprtcode').val());
	//提交数据：借用人id，借用人名称  借用备注,库存ID
	AjaxUtil.ajax({
		url:basePath+"/outfield/toolsuse/end",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify({
			id:id
			,kcid:kcid
			,dprtcode:dprtcode
			}),
		success:function(data){
			refreshPage();
			//$("#requisiton_message").html("指定结束成功").fadeIn().fadeOut(3000);
			
			if(data.isLegitimate){
				$("#requisiton_message").removeClass("alert-danger");
				$("#requisiton_message").addClass("alert-success");
				$("#requisiton_message").html("指定结束成功").fadeIn().fadeOut(3000);
			}
			else{
				 
				$("#requisiton_message").removeClass("alert-success");
				$("#requisiton_message").addClass("alert-danger");
				$("#requisiton_message").html(data.message).fadeIn().fadeOut(3000);
			}
	    }
	}); 
	
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
	

$('#confirmationFastBtn').on('click',doConfirmationFast);

$('#applyBtn').on('click',doApply);

$('#revertBtn').on('click',doRevert);




