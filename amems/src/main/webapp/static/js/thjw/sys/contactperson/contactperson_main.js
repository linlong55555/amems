$(document).ready(
		function() {
			Navigation(menuCode);
			validation();
			
			decodePageParam();
			refreshPermission();
			logModal.init({code:'D_016'});
			initImport();
		});

 

//验证
function validation(){
	validatorForm =  $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	lxr: {
	                validators: {
	                	notEmpty: {
	                        message: '联系人不能为空'
	                    }
	                }
	            },
	            sj: {
	                validators: {
	                	regexp: {
	                		regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
	                        message: '手机格式不正确'
	                    }
	                }
	            },
	            zj: {
	                validators: {
			            regexp: {
			            	regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
		                    message: '座机不正确'
		                }
	                }
	            }
        	}
    });
}


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#dprtcode").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.order, pageParamJson.pagination.sort);
		}else{
			goPage(1, "desc", "auto");
		}
	}catch(e){
		goPage(1, "desc", "auto");
	}
}


function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}

function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());

	var dprtcode = $.trim($("#dprtcode").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}
	pagination = {page:pageNumber,sort:sequence,order:sortType,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/sys/contactperson/getListPage",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(pageNumber, sortType, sequence) {
						AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
				signByKeyword($.trim($("#keyword_search").val()),[ 2,4]);
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
			}
			refreshPermission();
		}
	});

}
function appendContentHtml(list) {
	var htmlContent = '';
	$
			.each(
					list,
					function(index, row) {
						htmlContent += [
								"<tr>",
								"<td class='text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:contactperson:manage:02'  onClick='update(\""
										+ row.id
										+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='sys:contactperson:manage:03'  onClick=\"Delete('"
										+ row.id + "')\" title='作废  Invalid'></i></td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.lxr)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.gysmc)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.zw)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.sj)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.zj)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.cz)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.yxdz)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.qq)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.wx)
										+ "</td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.bz)
										+ "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whr.username)+"'>" +StringUtil.escapeStr(row.whr.username)
										+ "</td>",
								"<td class='text-center'>" + StringUtil.escapeStr(row.whsj) + "</td>",
								"<td class='text-left'>"
										+ row.dprt.dprtname
										+ "</td>", "</tr>" ].join("");
					});
	$("#list").empty();
	$("#list").html(htmlContent);
	 refreshPermission();
}
function searchBaseMaintenance() {
	goPage(1, "desc", "auto");
}

function addBaseMaintenance() {
	 validation();
	 $("#account_untie_btn").hide();
	 $("#account_select_btn").show();
	 $("#gysmc").attr("disabled", false);
	$("#csid").val("");
	 $("#id").val("");
	  $("#BaseMaintenanceModal").find("input").each(function() {
			$(this).attr("value", "");
	 });
		 $("#BaseMaintenanceModal").find("textarea").each(function(){
			 $(this).val("");
	 });
		 
	$("#BaseMaintenanceModal").modal("show");
}

/**
 * 保存提交
 */
function saveBaseMaintenanceData() {
	 $('#form1').data('bootstrapValidator').validate();
	  if(!$('#form1').data('bootstrapValidator').isValid()){
		return false;
	  }		
	  var obj ={};
	  	var id = $("#id").val();
		var csid = $.trim($("#csid").val());
		var gysmc = $.trim($("#gysmc").val());
		var lxr = $.trim($("#lxr").val());
		var zw = $.trim($("#zw").val());
		var sj = $.trim($("#sj").val());
		var zj = $.trim($("#zj").val());
		var cz = $.trim($("#cz").val());
		var yxdz = $.trim($("#yxdz").val());
		var qq = $.trim($("#qq").val());
		var wx = $.trim($("#wx").val());
		var bz = $.trim($("#bz").val());

		 obj.csid = csid;
		 obj.gysmc = gysmc;
		 obj.lxr = lxr;
		 obj.zw = zw;
		 obj.sj = sj;
		 obj.zj = zj;
		 obj.cz = cz;
		 obj.yxdz = yxdz;
		 obj.qq = qq;
		 obj.wx = wx;
		 obj.bz = bz;
		 
		 var url ="";
		 if(id==""){
			 url = basePath+"/sys/contactperson/save";//新增
		 }else{
			 url = basePath+"/sys/contactperson/edit";//修改
			 obj.id = id;
		 }
	 
	 startWait($("#BaseMaintenanceModal"));
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(obj),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal:$("#BaseMaintenanceModal"),
		success : function(data) {
			finishWait($("#BaseMaintenanceModal"));
			decodePageParam();
			$("#BaseMaintenanceModal").modal("hide");
			AlertUtil.showMessage('保存成功!');
			refreshPermission();
		}
	});
}
/**
 * 作废
 * @param id
 */
function Delete(id) {
	var param = {};
	param.id = id;
	AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
		AjaxUtil.ajax({
			url : basePath + "/sys/contactperson/cancel",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				AlertUtil.showMessage('操作成功!','/sys/contactperson/manage?pageParam='+encodePageParam());
				refreshPermission();
			}
		});
	}});
}
/**
 * 获取对应基地信息
 * @param id
 */
function update(id) {
	 validation();
	 
	 
	 
	 
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/sys/contactperson/editInfo",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#id").val(data.id);
			$("#dprtcode").val(data.dprtcode);
			$("#csid").val(data.csid);
			$("#gysmc").val(data.gysmc);
			$("#lxr").val(data.lxr);
			$("#zw").val(data.zw);
			$("#sj").val(data.sj);
			$("#zj").val(data.zj);
			$("#cz").val(data.cz);
			$("#yxdz").val(data.yxdz);
			$("#qq").val(data.qq);
			$("#wx").val(data.wx);
			$("#bz").val(data.bz);
			
			
			if($("#csid").val() == ""){
				$("#account_untie_btn").hide();
				$("#account_select_btn").show();
			}else{
				$("#account_select_btn").hide();
				$("#account_untie_btn").show();
				$("#gysmc").attr("disabled", true);
			}
		}
	});
	$("#BaseMaintenanceModal").modal("show");
}


$("#dprtcode").change(function(){
	goPage(1, "desc", "auto");
	refreshPermission();
})
//隐藏Modal时验证销毁重构
	 $("#BaseMaintenanceModal").on("hidden.bs.modal", function() {
	 	 $("#form1").data('bootstrapValidator').destroy();
	      $('#form1').data('bootstrapValidator', null);
	      validation();
	 });

/**
 * 打开供应商选择界面
 * @returns
 */
function openAccountSelect(){
	open_win_account_select.show({callback:function(data){
		if(data){
			$("#csid").val(data.id);
			$("#gysmc").val(data.gysmc);
			validation();
		}
	}});
}

function accountuntie(){
	 AlertUtil.showConfirmMessage("确定要解除绑定吗？", {callback: function(){
		$("#gysmc").attr("disabled", false);
		$("#csid").val("");
		$("#account_select_btn").show();
		$("#account_untie_btn").hide();
	 }});
	}

//字段排序
function orderBy(obj, _obj) {
	$obj = $("#jrsq th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#jrsq .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#jrsq .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, orderType,obj);
}


/**
 * 初始化导入
 */
function initImport(){
    importModal.init({
	    importUrl:"/sys/contactperson/excelImport",
	    downloadUrl:"/common/templetDownfile/9",
		callback: function(data){
			goPage(1, "desc", "auto");
			$("#ImportModal").modal("hide");
		}
	});
}

/**
 * 显示导入模态框
 */
function showImportModal(){
	 importModal.show();
}

function exportExcel(){
	var keyword = $("#keyword_search").val();
	var dprtcode = $("#dprtcode").val();
	window.open(basePath+"/sys/contactperson/Contacts.xls?dprtcode="
 				+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
	
}

//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
		if(formatUndefine(winId) != ""){
			$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
		}else{
			searchBaseMaintenance(); //调用主列表页查询
		}
	}
};
