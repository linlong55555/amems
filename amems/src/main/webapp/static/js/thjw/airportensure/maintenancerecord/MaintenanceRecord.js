var delAttachements = [];
var div1 = document.getElementById('div-wxdbh');
var div2 = document.getElementById('fileuploader');
var div3 = document.getElementById('wj');
var div4 = document.getElementById('wjsm');
var div5 = document.getElementById('operat');
var div6 = document.getElementById('wxrybutton');
$(document).ready(function() {
	Navigation(menuCode);
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRecord();//调用主列表页查询
			}
		}
	});
	
	decodePageParam();
	validation();
	logModal.init({
		code : 'B_J_001'
	});

	$("#wxsj").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	$('#wxrq').datepicker({
		autoclose : true,
		clearBtn : true
	});
	refreshPermission();
});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * 
 * @returns
 */
function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	params.wxsj = $.trim($("#wxsj").val());
	params.wxfs = $.trim($("#wxfs").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * 
 * @returns
 */
function decodePageParam() {
	try {
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#wxsj").val(params.wxsj);
		$("#wxfs").val(params.wxfs);
		$("#dprtcode").val(params.dprtcode);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.order,
					pageParamJson.pagination.sort);
		} else {
			goPage(1, "desc", "auto");
		}
	} catch (e) {
		goPage(1, "desc", "auto");
	}
}

function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}

function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());

	var wxsj = $.trim($("#wxsj").val());
	if (wxsj != '') {
		searchParam.searchBeginDate = wxsj.substring(0, 10);
		searchParam.searchEndDate = wxsj.substring(13, 23);
	}

	var dprtcode = $.trim($("#dprtcode").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}

	var wxfs = $.trim($("#wxfs").val());
	if (wxfs != '') {
		searchParam.wxfs = wxfs;
	}
	pagination = {
		page : pageNumber,
		sort : sequence,
		order : sortType,
		rows : 20
	};
	searchParam.pagination = pagination;
	if (id != "") {
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/airportensure/maintenancerecord/getListPage",
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
					goPage : function(a, b, c) {
						AjaxGetDatasWithSearch(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
				signByKeyword($.trim($("#keyword_search").val()),
						[ 3,4, 5, 7, 8 ],"#list tr td")
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append(
						"<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'maintenanceRecord_check_list_table'
			});
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
								"<td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='airportensure:maintenancerecord:main:02' onClick='update(\""
										+ row.id
										+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='airportensure:maintenancerecord:main:03' onClick=\"Delete('"
										+ row.id
										+ "')\" title='作废  Invalid'></i></td>",
								"<td  class='text-center'>"
										+ row.wxsj.toString().substr(0, 10)
										+ "</td>",
								"<td class=' text-center' ><a href='javascript:void(0);'onClick='look(\""
										+ row.id + "\")' >" + row.wxdbh
										+ "</a></td>",
								"<td class='text-left' title='"
										+StringUtil.escapeStr(row.wxrymc) + "'>"
										+ StringUtil.escapeStr(row.wxrymc) + "</td>",
								"<td class='text-left' title='"
										+ StringUtil.escapeStr(row.wxdx) + "'>"
										+ StringUtil.escapeStr(row.wxdx) + "</td>",
								"<td class='text-center'>"
										+ (row.wxfs == 1 ? "自行维修" : "外包维修")
										+ "</td>",
								"<td class='text-left' title='"
										+ StringUtil.escapeStr(row.wxnr) + "'>"
										+ StringUtil.escapeStr(row.wxnr) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whr.displayName)+"'>" + StringUtil.escapeStr(row.whr.displayName)
										+ "</td>",
								"<td class='text-center'>" + row.whsj + "</td>",
								"<td class='text-left' title='"+(row.wh_department==null?"":StringUtil.escapeStr(row.wh_department.dprtname))+"'>"
										+(row.wh_department==null?"":StringUtil.escapeStr(row.wh_department.dprtname)) + "</td>",
								"<td class='text-left'>" + row.dprtname
										+ "</td>", "</tr>" ].join("");
					});
	$("#list").empty();
	$("#list").html(htmlContent);
}
function searchRecord() {
	goPage(1, "desc", "auto");
}

function more() {
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

function add() {
	$("#wxnrmark").show();
	$("#wxsbmark").show();
	$("#wxrqmark").show();
	div1.style.display = "none";
	div2.style.display = "block";
	div3.style.display = "block";
	div4.style.display = "block";
	$("#ycwxry").removeClass().addClass("input-group");
	document.getElementById('lookfile').style.display="block";
	// div5.style.display="inline";
	$("#operat").removeAttr("style");
	$("#wxrybutton").removeAttr("style");
	$("#div-wxdbh").val("");
	$("#lookfile")
			.removeClass()
			.addClass(
					"col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0");
	$("#div-wxrq")
			.removeClass()
			.addClass(
					"col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#div-wxfs")
			.removeClass()
			.addClass(
					"col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#div-wxry")
			.removeClass()
			.addClass(
					"col-lg-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#lab-wxrq")
			.removeClass()
			.addClass(
					"col-lg-3 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0");
	$("#lab-wxsb")
	.removeClass()
	.addClass(
			"col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0");
	$("#div-wxsb")
	.removeClass()
	.addClass(
			"col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0");
	validation();
	var zzjg = $("#zzjg").val();
	var orgcode=$("#orgcode").val();
	var dprtcode = $("#dprtcode").val();
	var supercode=$("#supercode").val();
		clearData();
		$("#addModal").modal("show");
		$("#addModal input").attr("disabled", false);
		$("#addModal textarea").attr("disabled", false);
		$("#addModal select").attr("disabled", false);
		$("#fileuploader").attr("disabled", false);
		$("#fileuploader").attr("disabled", false);
		// $("#addModal a").attr("onclick");
		$("#addModal i").attr("onclick");
		$("#wxrybtn").removeAttr("disabled");
		$("#save").show();
		$("#addModal").modal("show");	
}

function openWinwhrymc() {
	// var this_ = this;
	
	var dprtcode = '';
	if ($("#dpt").val() == '') {
		dprtcode = $("#zzjg").val();
	} else {
		dprtcode = $("#dpt").val();
	}
	userModal.show({
		clearUser: true,//是否显示清空按钮
		selected : $("#wxryid").val(),// 原值，在弹窗中默认勾选
		dprtcode : dprtcode,
		callback : function(data) {// 回调函数
			if(data!=null&&data!=undefined){
			$("#wxryid").val(formatUndefine(data.id));
			$("#wxrymc").val(formatUndefine(data.username)+ " " + formatUndefine(data.realname));
			$("#wxrymcs").val(formatUndefine(data.username)+ " " + formatUndefine(data.realname));
			$("#form").data('bootstrapValidator').destroy();
			$('#form').data('bootstrapValidator', null);
			validation();
			}
		},
		
	})

}
function closeUserMultiModal() {
	$("#userModal").modal("hide");
}

/**
 * 保存维修单信息(新增、修改)
 */
function saveMaintenanRecordData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		finishWait($("#addModal"));
		return false;
	}
	var attachments = [];
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments.push({
				'yswjm' : responses[i].attachments[0].yswjm,
				'wbwjm' : responses[i].attachments[0].wbwjm,
				'nbwjm' : responses[i].attachments[0].nbwjm,
				'wjdx' : responses[i].attachments[0].wjdx,
				'cflj' : responses[i].attachments[0].cflj,
				'id' : responses[i].attachments[0].id,
				'operate' : responses[i].attachments[0].operate

			});
		}
	}
	var dellen = delAttachements.length;
	if (dellen != undefined && dellen > 0) {
		for (var i = 0; i < dellen; i++) {
			attachments.push({
				'id' : delAttachements[i].id,
				'operate' : 'DEL'

			});
		}
	}
	var param = {};
	param.wxsj = $("#wxrq").val().substring(0, 10);
	if($.trim($("#wxrymc").val())==$.trim($("#wxrymcs").val())){
		param.wxrymc = $.trim($("#wxrymc").val());
		param.wxryid = $("#wxryid").val();
	}else{
		param.wxrymc = $.trim($("#wxrymc").val());
		param.wxryid = "";
	}
	param.wxfs = $("#wxfsid").val();
	param.wxdx = $("#wxdx").val();
	param.wxnr = $("#wxnr").val();
	param.whbmid = $("#whbmid").val();
	param.whrid = $("#whrid").val();
	param.id = $("#id").val();
	if ($("#id").val() == "") {
		param.dprtcode = $("#zzjg").val();
	} else {
		param.dprtcode = $("#dpt").val();
	}
	param.wxdbh = $("#wxdbh").val();
	param.attachments = attachments;
	AjaxUtil.ajax({
		url : basePath
				+ "/airportensure/maintenancerecord/addMaintenanceRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#addModal"),
		success : function(data) {
			AlertUtil.showMessage('保存成功!',
					'/airportensure/maintenancerecord/main?id=' + data
							+ '&pageParam=' + encodePageParam());
			$("#UserMultiModal").modal("hide");
			$("#addModal").modal("hide");
			finishWait();
			refreshPermission();
			// searchRecord();
			uploadObj.responses = [];
		}
	});
}

function Delete(id) {
	var zzjg = $("#zzjg").val();
	var orgcode=$("#orgcode").val();
	var dprtcode = $("#dprtcode").val();
	var supercode=$("#supercode").val();
		var param = {};
		param.id = id;
		AlertUtil.showConfirmMessage("您确定要作废吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath
							+ "/airportensure/maintenancerecord/deleteRecord",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						// searchRecord();
						AlertUtil.showMessage('操作成功!',
								'/airportensure/maintenancerecord/main?pageParam='
										+ encodePageParam());
						refreshPermission();
					}
				});
			}
		});
}
/**
 * 修改维修单
 * 
 * @param id
 */
function update(id) {
	var zzjg = $("#zzjg").val();
	var orgcode=$("#orgcode").val();
	var dprtcode = $("#dprtcode").val();
	var supercode=$("#supercode").val();
		clearData();
		var param = {};
		param.id = id;
		AjaxUtil.ajax({
			url : basePath + "/airportensure/maintenancerecord/edit",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(param),
			success : function(data) {
				$("#wxrq").val(data.wxsj.toString().substr(0, 10));
				$("#wxrq").datepicker("update");
				$("#wxrymc").val(data.wxrymc);
				$("#wxrymcs").val(data.wxrymc);
				$("#wxryid").val(data.wxryid);
				$("#wxfsid").val(data.wxfs);
				$("#wxdx").val(data.wxdx);
				$("#wxnr").val(data.wxnr);
				$("#id").val(data.id);
				$("#dpt").val(data.dprtcode);
				$("#wxdbh").val(data.wxdbh);
				$("#wxd").val(data.wxdbh);
				if(data.attachments!=null&&data.attachments.length>0){
					var attachments = data.attachments;
					var len = data.attachments.length;
					if (len > 0) {
						var trHtml = '';
						for (var i = 0; i < len; i++) {
							trHtml = trHtml
									+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
									+ attachments[i].uuid + '\' key=\''
									+ attachments[i].id + '\' >';
							trHtml = trHtml + '<td><div>';
							trHtml = trHtml
									+ '<i class="icon-trash color-blue cursor-pointer" onclick="deletefile(\''
									+ attachments[i].uuid
									+ '\')" title="删除 Delete">  ';
							trHtml = trHtml + '</div></td>';
							trHtml = trHtml
									+ '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" > <a onclick="downloadfile(\''
									+ attachments[i].id + '\')" >'
									+ StringUtil.escapeStr(attachments[i].wbwjm)
									+ '</a></td>';
							trHtml = trHtml + '<td class="text-left">'
									+ attachments[i].wjdxStr + ' </td>';
							trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
									+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
							trHtml = trHtml + '<td>'
									+ attachments[i].czsj + '</td>';
							trHtml = trHtml
									+ '<input type="hidden" name="relativePath" value=\''
									+ attachments[i].relativePath
									+ '\'/>';
							trHtml = trHtml + '</tr>';
						}
						$('#filelist').append(trHtml);
					}
				}
			}
		});
		$("#wxnrmark").show();
		$("#wxsbmark").show();
		$("#wxrqmark").show();
		$("#addModal input").attr("disabled", false);
		$("#addModal textarea").attr("disabled", false);
		$("#addModal select").attr("disabled", false);
		$("#fileuploader").attr("disabled", false);
		// $("#addModal a").attr("onclick");
		$("#addModal i").attr("onclick");
		$("#wxrybtn").removeAttr("disabled");
		$("#save").show();
		div1.style.display = "block";
		div2.style.display = "block";
		div3.style.display = "block";
		div4.style.display = "block";
		$("#ycwxry").removeClass().addClass("input-group");
		document.getElementById('lookfile').style.display="block";
		$("#operat").removeAttr("style");
		$("#wxrybutton").removeAttr("style");
		$("#lookfile")
				.removeClass()
				.addClass(
						"col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0");
		$("#div-wxrq")
				.removeClass()
				.addClass(
						"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
		$("#div-wxfs")
				.removeClass()
				.addClass(
						"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
		$("#div-wxry")
				.removeClass()
				.addClass(
						"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
		$("#lab-wxrq")
				.removeClass()
				.addClass(
						"col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0");
		$("#lab-wxsb")
		.removeClass()
		.addClass(
				"col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0");
		$("#div-wxsb")
		.removeClass()
		.addClass(
				"col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0");
		$("#addModal").modal("show");
}

var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					maxFileCount : 10,
					formData : {
						"fileType" : "maintenanceRecord",
						"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,				
					showStatusAfterError: false,
					uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass : "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ data.attachments[0].uuid + '\'>';
						trHtml = trHtml + '<td><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
								+ data.attachments[0].uuid
								+ '\')" title="删除 Delete">  ';
						trHtml = trHtml + '</div></td>';
						trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">'
								+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '</td>';
						trHtml = trHtml + '<td class="text-left">'
								+ data.attachments[0].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left" title="'+data.attachments[0].user.username + " "
						+ data.attachments[0].user.realname+'">'
								+ data.attachments[0].user.username + " "
								+ data.attachments[0].user.realname + '</td>';
						trHtml = trHtml + '<td>' + data.attachments[0].czsj
								+ '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ data.attachments[0].relativePath + '\'/>';

						trHtml = trHtml + '</tr>';
						$('#filelist').append(trHtml);
					}
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	$('#wbwjm').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
				            	
				            	$('.ajax-file-upload-container').html("");
								uploadObj.selectedFiles -= 1;
								return false;
				            } 
				            else{
				            	return true; 
				            }
						}else{
							return true;
						}
					}

				});
/**
 * 添加时删除附件
 * 
 * @param uuid
 */
function delfile(uuid) {
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if (len > 0) {
		for (var i = 0; i < len; i++) {
			if (responses[i].attachments[0].uuid != uuid) {
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		$('#' + uuid).remove();
	}
}
/**
 * 清空记录单modal数据
 */
function clearData() {
	$("#wxrq").val("");
	$("#wxrq").datepicker("update");
	$("#wxrymc").val("");
	$("#wxrymcs").val("");
	$("#wxryid").val("");
	$("#wxfsid").val(1);
	$("#wxdx").val("");
	$("#wxnr").val("");
	$("#id").val("");
	$("#dpt").val("");
	$("#wxdbh").val("");
	$('#filelist').html("");
	$("#wbwjm").val("");
	uploadObj.responses.length=0;
}	

/**
 * 修改时删除附件
 */
function deletefile(uuid) {
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		var waitDelArray = [];
		if (len > 0) {
			for (var i = 0; i < len; i++) {
				if (responses[i].attachments[0].uuid != uuid) {
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
		}
		$('#' + uuid).remove();

	} else {
		$('#' + uuid).remove();
		delAttachements.push({
			id : key
		});
	}
}
/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);

}
/**
 * 查看
 * 
 * @param id
 */
function look(id) {
	 var leng=0;
	clearData();	
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/airportensure/maintenancerecord/edit",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#wxrq").val(data.wxsj.toString().substr(0, 10));
			$("#wxrq").datepicker("update");
			$("#wxrymc").val(data.wxrymc);
			$("#wxryid").val(data.wxryid);
			$("#dprtcodeid").val(data.dprtcode);
			$("#wxfsid").val(data.wxfs);
			$("#wxdx").val(data.wxdx);
			$("#wxnr").val(data.wxnr);
			$("#id").val(data.id);
			$("#wxdbh").val(data.wxdbh);
			$("#wxd").val(data.wxdbh);		
			if(data.attachments!=null&&data.attachments.length>0){
				var attachments = data.attachments;
				var len = data.attachments.length;
				leng=len;
				if (len > 0) {
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml
								+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ attachments[i].uuid + '\' key=\''
								+ attachments[i].id + '\' >';
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left" title="'+attachments[i].czrname+'">'
								+ attachments[i].czrname + '</td>';
						trHtml = trHtml + '<td>' + attachments[i].czsj
								+ '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath + '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#filelist').append(trHtml);
				}
			}			
			if(leng==0){
				document.getElementById('lookfile').style.display="none";
			}else{
				document.getElementById('lookfile').style.display="block";
			}
		}
	});
	$("#wxnrmark").hide();
	$("#wxsbmark").hide();
	$("#wxrqmark").hide();
	$("#wjsm").hide();
	$("#addModal input").attr("disabled", true);
	$("#addModal textarea").attr("disabled", true);
	$("#addModal select").attr("disabled", true);
	$("#fileuploader").attr("disabled", true);
	// $("#addModal a").attr("onclick", false);
	$("#addModal i").attr("onclick", false);
	$("#wxrybtn").attr("disabled", "disabled");
	div1.style.display = "block";
	div2.style.display = "none";
	div3.style.display = "none";
	div4.style.display = "none";
	div5.style.display = "none";
	div6.style.display = "none";
	$("#ycwxry").removeClass().addClass("new_style");
	$("#lookfile")
			.removeClass()
			.addClass(
					"col-lg-12 col-sm-12 col-xs-12 margin-top-0 padding-left-0 padding-right-0");
	$("#div-wxrq")
			.removeClass()
			.addClass(
					"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#div-wxfs")
			.removeClass()
			.addClass(
					"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#div-wxry")
			.removeClass()
			.addClass(
					"col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group");
	$("#lab-wxrq")
			.removeClass()
			.addClass(
					"col-lg-4 col-sm-4 col-xs-4 text-right padding-left-8 padding-right-0");
	$("#lab-wxsb")
	.removeClass()
	.addClass(
			"col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0");
	$("#div-wxsb")
	.removeClass()
	.addClass(
			"col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0");
	$("#save").hide();
	$("#addModal").modal("show");
}
// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc")
						.addClass("sorting");
			});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, orderStyle.split("_")[1], obj);
}
// 重置
function searchreset() {
	$("#keyword_search").val("");
	$("#wxsj").val("");
	$("#wxfs").each(function() {
		$(this).val("");
	});
	$("#dprtcode").each(function() {
		$(this).val("");
	});
	var zzjg = $("#zzjg").val();
	$("#dprtcode").val(zzjg);
	$("#wxfs").val(0);
	searchRecord();
}

// 隐藏Modal时验证销毁重构
$("#addModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
});
function validation() {
	validatorForm = $("#form").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			wxrq : {
				validators : {
					notEmpty : {
						message : '维修日期不能为空'
					}
				}
			},
			wxdx : {
				validators : {
					notEmpty : {
						message : '维修设备不能为空'
					}
				}
			},
			wxnr : {
				validators : {
					notEmpty : {
						message : '维修内容不能为空'
					}
				}
			}
		}
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
		     .updateStatus('wxrq', 'NOT_VALIDATED',null)  
		     .validateField('wxrq');  
  });;
}

$("#dprtcode").change(function() {
	searchRecord();
});
$("#wxfs").change(function() {
	searchRecord();
});
