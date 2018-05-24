var delAttachements = [];
var div2 = document.getElementById('fileuploader');
var div3 = document.getElementById('wj');
var div4 = document.getElementById('wjsm');
var div5 = document.getElementById('operat');
var div6 = document.getElementById('wxrybtn');
$(document).ready(
		function() {
			Navigation(menuCode);
			decodePageParam();
			validation();
			refreshPermission();
			// goPage(1, "desc", "auto");// 开始的加载默认的内容
			$(document).keydown(function(event) {
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
					if(formatUndefine(winId) != ""){
						$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
					}else{
						searchRecord(); //调用主列表页查询
					}
				}
			});

			$("#fbsj").daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
			$('#yxqKs').datepicker({
				autoclose : true,
				clearBtn : true
			});
			$('#yxqJs').datepicker({
				autoclose : true,
				clearBtn : true
			});
			
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
	params.fbsj = $.trim($("#fbsj").val());
	params.jjd = $.trim($("#jjd").val());
	params.zt = $.trim($("#zt").val());
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
		$("#fbsj").val(params.fbsj);
		$("#jjd").val(params.jjd);
		$("#dprtcode").val(params.dprtcode);
		$("#zt").val(params.zt);
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

	var fbsj = $.trim($("#fbsj").val());
	if (fbsj != '') {
		searchParam.searchBeginDate = fbsj.substring(0, 10);
		searchParam.searchEndDate = fbsj.substring(13, 23);
	}

	var dprtcode = $.trim($("#dprtcode").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}
	var zt = $.trim($("#zt").val());
	if (zt != '') {
		searchParam.zt = zt;
	}
	var jjd = $.trim($("#jjd").val());
	if (jjd != '') {
		searchParam.jjd = jjd;
	}
	pagination = {page:pageNumber,sort:sequence,order:sortType,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/sys/message/getListPage",
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
						[ 2,3,10],"#list tr td")
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append(
						"<tr><td colspan=\"13\">暂无数据 No data.</td></tr>");
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
						var content="";
						if(row.zt==0){
							content ="<tr><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:message:main:02'  onClick='update(\""
								+ row.id
								+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='sys:message:main:03'  onClick='reply(\""
								+ row.id
								+ "\")' title='发布 Release'></i></i>&nbsp;&nbsp;<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='sys:message:main:04' onClick=\"Delete('"
								+ row.id
								+ "')\" title='关闭 Close'></i></td>" 
						}
						if(row.zt==1){							
							content="<tr><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission'permissioncode='sys:message:main:02'  onClick='update(\""
									+ row.id
									+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='sys:message:main:04' onClick=\"Delete('"
									+ row.id
									+ "')\" title='关闭 Close'></i></td>"
						}
						if(row.zt==9){						
							content="<tr><td class='fixed-column text-center'></td>"
						}
						htmlContent += [
						          content,
								"<td  class='text-left' title='"+ StringUtil.escapeStr(row.bt) +"' >" +
										"<a href='javascript:void(0);'onClick='look(\""
										+ row.id + "\")' >"
										+  StringUtil.escapeStr(row.bt)
										+ "</td>",
								"<td class=' text-left' title='"+ StringUtil.escapeStr(row.nr) +"'  >"
										+ StringUtil.escapeStr(row.nr)
										+ "</td>",
								"<td class='text-left' title='"+ StringUtil.escapeStr(row.zrdw) +"'>"
										+ StringUtil.escapeStr(row.zrdw)+"</td>",
								"<td class='text-center' >"+(row.fbsj==null?"":row.fbsj)+ "</td>",
								"<td class='text-center'>"+(row.yxqKs==null?"":row.yxqKs.toString().substr(0, 10)) + "</td>",
								"<td class='text-center'>"+ (row.yxqJs==null?"":row.yxqJs.toString().substr(0, 10)) + "</td>",
								"<td class='text-left'>" + (row.jjd=="1"?"普通":row.jjd=="9"?"紧急":"") + "</td>",
								"<td class='text-left'>" + (row.zt=="0"?"保存":row.zt=="1"?"发布":row.zt=="9"?"关闭":"") + "</td>",
								"<td class='text-left'title='"+StringUtil.escapeStr(row.whr.displayName)+"'>" + StringUtil.escapeStr(row.whr.displayName) + "</td>",
								"<td class='text-center'>" + formatUndefine(row.whsj) + "</td>",
								"<td class='text-left'>"+ StringUtil.escapeStr(row.wh_department==null?"":row.wh_department.dprtname) + "</td>",
								"<td class='text-left'>"+StringUtil.escapeStr(row.dprtname)+ "</td>", 
								"</tr>" ].join("");
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
	clearData();
	validation();
	div2.style.display = "block";
	div3.style.display = "block";
	div4.style.display = "block";
	$("#addModal span").show();
	document.getElementById('lookfile').style.display="block";
	$("#wxrybtn").removeAttr("style");
	$("#operat").removeAttr("style");
	$("#wxrybutton").removeAttr("style");
	$("#addModal input").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#addModal select").attr("disabled", false);
	$("#fileuploader").attr("disabled", false);
	$("#fileuploader").attr("disabled", false);
	$("#addModal i").attr("onclick");
	$("#lookfile")
	.removeClass()
	.addClass("col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0");
	$("#addModal").modal("show");
	$("#save").show();
	$("#fb").show();
}


/**
 * 保存信息(新增、修改)
 */
function saveMessage(obj) {
	startWait($("#addModal"));
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
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
	if(obj=="save"){
		param.zt =0;
	}
	if(obj=="fb"){
		param.zt =1;
	}
	param.bt =$.trim($("#bt").val());
	param.nr =$.trim($("#nr").val());
	param.yxqKs = $.trim($("#yxqKs").val());
	param.yxqJs = $.trim($("#yxqJs").val());
	if($.trim($("#yxqKs").val())>$.trim($("#yxqJs").val())){
		AlertUtil.showErrorMessage("开始日期不能大于结束日期");
		finishWait($("#addModal"));
		return false
	}
	param.jjd = $.trim($("#jjdid").val());
	param.zrdw = $.trim($("#zrdw").val());
	param.whbmid = $("#whbmid").val();
	param.whrid = $("#whrid").val();
	param.id = $("#id").val();
	if($("#id").val()==""){
		param.dprtcode=$("#zzjg").val();
	}else{
		param.dprtcode = $("#dpt").val();	
	}
	param.attachments = attachments;
		AjaxUtil.ajax({
			url : basePath
					+ "/sys/message/addMessage",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			modal:$("#addModal"),
			success : function(data) {
				$("#addModal").modal("hide");
				 AlertUtil.showMessage('操作成功!','/sys/message/main?id='+data+'&pageParam='+encodePageParam());
				clearData();
				finishWait();
				refreshPermission();
//				searchRecord();
				uploadObj.responses = [];
			}
		});
	} 


function Delete(id) {
	var param = {};
	param.id = id;
	param.whbmid = $("#whbmid").val();
 	param.whrid = $("#whrid").val();
	AlertUtil.showConfirmMessage("您确定要关闭吗？",{callback: function(){
		AjaxUtil.ajax({
			url : basePath + "/sys/message/deleteMessage",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				AlertUtil.showMessage('操作成功!','/sys/message/main?id='+data+'&pageParam='+encodePageParam());
				refreshPermission();
			}
		});
	}});
}
/**
 * 修改
 * 
 * @param id
 */
function update(id) {
	clearData();
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/sys/message/edit",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#bt").val(data.bt);
			$("#nr").val(data.nr);
			if(data.yxqKs==null){
				$("#yxqKs").val("");
			}else{
			$("#yxqKs").val(data.yxqKs.toString().substr(0, 10));
			$("#yxqKs").datepicker("update");
			}
			if(data.yxqJs==null){
				$("#yxqJs").val("");
			}else{
			$("#yxqJs").val(data.yxqJs.toString().substr(0, 10));
			$("#yxqJs").datepicker("update");
			}
			$("#jjdid").val(data.jjd);
			$("#zrdw").val(data.zrdw);
			$("#id").val(data.id);
			$("#dpt").val(data.dprtcode);
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
			if(data.zt==1){
				$("#save").hide();
			}
			if(data.zt==0){
				$("#save").show();
			}
			$("#addModal").modal("show");
		}
	});
	$("#addModal span").show();
	div2.style.display = "block";
	div3.style.display = "block";
	div4.style.display = "block";
	document.getElementById('lookfile').style.display="block";
	$("#wxrybtn").removeAttr("style");
	$("#operat").removeAttr("style");
	$("#wxrybutton").removeAttr("style");
	$("#addModal input").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#addModal select").attr("disabled", false);
	$("#fileuploader").attr("disabled", false);
	$("#fileuploader").attr("disabled", false);
	$("#addModal i").attr("onclick");
	$("#lookfile")
	.removeClass()
	.addClass("col-lg-12 col-sm-12 col-xs-12 margin-top-10 padding-left-0 padding-right-0");
	$("#fb").show();
}

var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showStatusAfterError: false,
					showDelete : false,
					maxFileCount : 100,
					formData : {
						"fileType" : "message",
						"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
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
						trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" >'
								+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '</td>';
						trHtml = trHtml + '<td class="text-left">'
								+ data.attachments[0].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left" title="'+data.attachments[0].user.username + " "
						+ data.attachments[0].user.realname +'">'
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
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
				            	
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
//重置
function searchreset(){
	$("#keyword_search").val("");
	$("#fbsj").val("");
	$("#jjd").each(function(){
		$(this).val("");
	});
	$("#dprtcode").each(function(){
		$(this).val("");
	});
	var zzjg=$("#zzjg").val();
	$("#dprtcode").val(zzjg);
	$("#jjd").val(0);
	$("#zt").val("");
	searchRecord();
}


	//隐藏Modal时验证销毁重构
	 $("#addModal").on("hidden.bs.modal", function() {
	 	 $("#form").data('bootstrapValidator').destroy();
	      $('#form').data('bootstrapValidator', null);
	      validation();
	 });
	 function validation(){
	 validatorForm = $("#form").bootstrapValidator({
			message : '数据不合法',
			feedbackIcons : {
				// valid: 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				bt : {
					validators : {
						notEmpty : {
							message : '标题不能为空'
						}
					}
				},
				nr : {
					validators : {
						notEmpty : {
							message : '内容不能为空'
						}
					}
				}
			}
		});
	 }

	 $("#dprtcode").change(function(){
		 searchRecord();
	 });
	 $("#jjd").change(function(){
		 searchRecord();
	 });
	 $("#zt").change(function(){
		 searchRecord();
	 });
	 //清空数据
	 function clearData(){
		 $("#bt").val("");
		 $("#nr").val("");
		 $("#yxqKs").val("");
		 $("#yxqKs").datepicker("update");
		 $("#yxqJs").val("");
		 $("#yxqJs").datepicker("update");
		 $("#jjdid").val(1);
		 $("#zrdw").val("");
		 $("#id").val("");
		 $("#dpt").val("");
		 $('#filelist').html("");
		 $("#wbwjm").val("");
		 uploadObj.responses.length=0;
	 }
	 
	 function reply(id){
		 	var param = {};
		 	if(id != ""){
		 		param.id = id;
				id = "";
			}
		 	param.whbmid = $("#whbmid").val();
		 	param.whrid = $("#whrid").val();
		 	startWait();
			AjaxUtil.ajax({
				url : basePath + "/sys/message/replyMessage",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(param),
				success : function(data) {
					finishWait();
					 AlertUtil.showMessage('发布成功!','/sys/message/main?id='+data+'&pageParam='+encodePageParam());
					 refreshPermission();
				}
			}); 
	 }
	 
	 function openzrdw() {
			var this_ = this;
			var userList = [];
			var b = $("#zrdw").val();
			for (var i = 0; i < b.split(",").length; i++) {
				var p = {
					dprtname : b.split(",")[i],
					dprtname : b.split(",")[i]
				};
				userList.push(p);
			}
			if (b == "") {
				userList = "";
			}
			dprtmentMultiModal.show({
				checkUserList : userList,// 原值，在弹窗中回显
				callback : function(data) {// 回调函数
					var mjsrName = '';
					if (data != null && data != "") {
						$.each(data, function(i, row) {						
							mjsrName += row.dprtname + ",";	
						});
					}
					if (mjsrName != '') {
						mjsrName = mjsrName.substring(0, mjsrName.length - 1);
					}
					$("#zrdw").val(mjsrName);
				}
			});
			userList = [];
		}
	 
	 function closemodal(){
		 $("#dprtmentMultiModal").modal("hide");
	 }
	
	 function look(id){
		 var leng=0;
		 clearData();
			var param = {};
			param.id = id;
			AjaxUtil.ajax({
				url : basePath + "/sys/message/edit",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(param),
				success : function(data) {
					$("#bt").val(data.bt);
					$("#nr").val(data.nr);
					if(data.yxqKs==null){
						$("#yxqKs").val("");
					}else{
					$("#yxqKs").val(data.yxqKs.toString().substr(0, 10));
					}
					if(data.yxqJs==null){
						$("#yxqJs").val("");
					}else{
					$("#yxqJs").val(data.yxqJs.toString().substr(0, 10));
					}
					$("#jjdid").val(data.jjd);
					$("#zrdw").val(data.zrdw);
					$("#id").val(data.id);
					$("#dpt").val(data.dprtcode);
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
					$("#addModal").modal("show");
				}
			});
			$("#addModal input").attr("disabled", true);
			$("#addModal textarea").attr("disabled", true);
			$("#addModal select").attr("disabled", true);
			$("#fileuploader").attr("disabled", true);
			$("#addModal i").attr("onclick", false);
			div2.style.display = "none";
			div3.style.display = "none";
			div4.style.display = "none";
			div5.style.display = "none";
			div6.style.display = "none";
			$("#addModal span").hide();
			$("#lookfile")
			.removeClass()
			.addClass("col-lg-12 col-sm-12 col-xs-12 margin-top-0 padding-left-0 padding-right-0");
			$("#save").hide();
			$("#fb").hide();
			if(leng==0){
				document.getElementById('lookfile').style.display="none";
			}else{
				document.getElementById('lookfile').style.display="block";
			}
	 }
