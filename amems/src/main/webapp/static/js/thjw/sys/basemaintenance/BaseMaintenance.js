$(document).ready(
		function() {
			decodePageParam();
			refreshPermission();
//			goPage(1, "desc", "auto");// 开始的加载默认的内容
			Navigation(menuCode);
			logModal.init({code:'D_012'});
//			$(document).keydown(function(event) {
//				if (event.keyCode == 13) {
//					if ($("#keyword_search").is(":focus")) {
//						searchRecord();
//					}
//				}
//			});

		});

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
		url : basePath + "/sys/base/getListPage",
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
				signByKeyword($.trim($("#keyword_search").val()),
						[ 2,3 ])
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append(
						"<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>");
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
								"<td class='text-center'><i class='icon-edit color-blue cursor-pointer'  onClick='update(\""
										+ row.id
										+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer'  onClick=\"Delete('"
										+ row.id + "')\" title='作废  Invalid'></i></td>",
								"<td class='text-left'>"
										+ StringUtil.escapeStr(row.jdms)
										+ "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whr==null?"":row.whr.username+" "+row.whr.realname)+"'>" +StringUtil.escapeStr(row.whr==null?"":row.whr.username+" "+row.whr.realname)
										+ "</td>",
								"<td class='text-center'>" + (row.whsj==null?"":row.whsj.toString()) + "</td>",
								"<td class='text-left'>"
										+ row.dprtname
										+ "</td>", "</tr>" ].join("");
					});
	$("#list").empty();
	$("#list").html(htmlContent);
}
function searchBaseMaintenance() {
	goPage(1, "desc", "auto");
}

function addBaseMaintenance() {
	clearData();
	$("#BaseMaintenanceModal").modal("show");
}

/**
 * 保存提交
 */
function saveBaseMaintenanceData() {

	var searchParam = {};
	var jdms = $.trim($("#jdms").val());
	searchParam.jdms=jdms;
	searchParam.whrid=$("#whrid").val();
	searchParam.id=$("#id").val();
	if($("#id").val()==''){
		searchParam.dprtcode = $("#zzjg").val();
	}else{
		searchParam.dprtcode = $("#dpt").val();
	}
	if(jdms!=''){
	AjaxUtil.ajax({
		url : basePath
				+ "/sys/base/addBaseMaintenance",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal:$("#BaseMaintenanceModal"),
		success : function(data) {
//			searchBaseMaintenance();
			if(data!=""){
			AlertUtil.showMessage('保存成功!','/sys/base/main?id='+data+'&pageParam='+encodePageParam());
			$("#BaseMaintenanceModal").modal("hide");
			clearData();
			finishWait();
			refreshPermission();
			}else{
			AlertUtil.showMessage("该基地描述已存在！");
			}
		}
	});
}else{
	AlertUtil.showErrorMessage("基地描述不能为空！");
}
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
			url : basePath + "/sys/base/deleteBaseMaintenance",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
//				searchBaseMaintenance();
				AlertUtil.showMessage('操作成功!','/sys/base/main?pageParam='+encodePageParam());
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
	clearData();
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/sys/base/edit",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#jdms").val(data.jdms);
			$("#id").val(data.id);
			$("#dpt").val(data.dprtcode);
		}
	});
	$("#BaseMaintenanceModal").modal("show");
}

/**
 * 清空modal数据
 */
function clearData() {
	$("#jdms").val("");
	$("#id").val("");
	$("#dpt").val("");
}

$("#dprtcode").change(function(){
	goPage(1, "desc", "auto");
	refreshPermission();
});


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
