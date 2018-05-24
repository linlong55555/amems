var delAttachements = [];
var jb1="";
var jb2="";
var jb3="";
var zrbmlist=[];
$(document).ready(function() {
	Navigation(menuCode);
	decodePageParam();
	gbValidation();
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			if ($("#keyword_search").is(":focus")) {
				searchRecord();
			}
		}
	});

	$("#zgxq_search").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	if($("#lx").val()==2){
		$("#add").hide();
	}
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
	params.zgxq = $.trim($("#zgxq_search").val());
	params.zt = $.trim($("#zt_search").val());
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
		$("#zgxq_search").val(params.zgxq);
		$("#zt_search").val(params.zt);
		$("#dprtcode_search").val(params.dprtcode);
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
	var paramsMap={};
	var zgxq = $.trim($("#zgxq_search").val());
	if (zgxq != '') {
		paramsMap.zgxqDateBegin = zgxq.substring(0,4)+"-"+zgxq.substring(5,7)+"-"+zgxq.substring(8,10);
		paramsMap.zgxqDateEnd = zgxq.substring(12,17)+"-"+zgxq.substring(18,20)+"-"+zgxq.substring(21,23);
	}
	paramsMap.lx=$("#lx").val();
	searchParam.paramsMap=paramsMap;
	var dprtcode = $.trim($("#dprtcode_search").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}

	var zt = $.trim($("#zt_search").val());
	if (zt != '') {
		searchParam.zt = zt;
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
		url : basePath + "/quality/selfquality/getListPage",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				 jb1 =data.monitorsettings.yjtsJb1;
				 jb2 =data.monitorsettings.yjtsJb2;
				 jb3 =data.monitorsettings.yjtsJb3;
				 zrbmlist=data.zrbmlist;
				 if(zrbmlist.length>0){
				 logModal.init({
						code : 'B_Z_002',extparam:{ZRBMLIST:zrbmlist}
					});
				 }else{
					 logModal.init({
							code : 'B_Z_002'
						}); 
				 }
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
						"<tr><td colspan=\"26\" class='text-center'>暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'selfquality_check_list_table'
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
						if (index % 2 == 0) {
							if(row.zt==1&&$("#lx").val()==1){
								content ="<tr bgcolor=\""+getWarningColor("#f9f9f9",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:02' onClick='update(\""
									+ row.id
									+ "\")' title='修改 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:03' onClick=\"Delete('"
									+ row.id
									+ "')\" title='作废  Invalid'></i></td>" 
							}
							if(row.zt==2&&$("#lx").val()==2){								
								content ="<tr bgcolor=\""+getWarningColor("#f9f9f9",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:rectification:01' onClick='update(\""
									+ row.id
									+ "\")' title='反馈 Feedback'></i></td>" 
							}
							if(row.zt==2&&$("#lx").val()==1){
								content ="<tr bgcolor=\""+getWarningColor("#f9f9f9",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:04' onClick=\"closeRecord('"+ row.id +"','"+row.dprtcode +"')\" title='关闭 Close'></i></td>" 
							}
							if(row.zt==3&&$("#lx").val()==1){
								content ="<tr bgcolor=\""+getWarningColor("#f9f9f9",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:04' onClick=\"closeRecord('"+ row.id +"','"+row.dprtcode +"')\" title='关闭 Close'></i>&nbsp;&nbsp;<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:05' onClick='update(\""
									+ row.id
									+ "\")' title='审核 Review'></i></td>" 
							}
							if(row.zt==10||row.zt==9||row.zt==8&&$("#lx").val()==1){
								content ="<tr bgcolor=\""+getWarningColor("#f9f9f9",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'></td>" 
							}  
						   } 
							else {
							   if(row.zt==1&&$("#lx").val()==1){
									content ="<tr bgcolor=\""+getWarningColor("#fefefe",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:02' onClick='update(\""
										+ row.id
										+ "\")' title='修改 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:03' onClick=\"Delete('"
										+ row.id
										+ "')\" title='作废  Invalid'></i></td>" 
								}
								if(row.zt==2&&$("#lx").val()==2){			
									content ="<tr bgcolor=\""+getWarningColor("#fefefe",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:rectification:01' onClick='update(\""
										+ row.id
										+ "\")' title='反馈 Feedback'></i></td>" 
								}
								if(row.zt==2&&$("#lx").val()==1){
									content ="<tr bgcolor=\""+getWarningColor("#fefefe",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:04' onClick=\"closeRecord('"+ row.id +"','"+row.dprtcode +"')\" title='关闭 Close'></i></td>" 
								}
								if(row.zt==3&&$("#lx").val()==1){
									content ="<tr bgcolor=\""+getWarningColor("#fefefe",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'><i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:04' onClick=\"closeRecord('"+ row.id +"','"+row.dprtcode +"')\" title='关闭 Close'></i>&nbsp;&nbsp;<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='quality:selfquality:main:05' onClick='update(\""
										+ row.id
										+ "\")' title='审核 Review'></i></td>" 
								}
								if(row.zt==10||row.zt==9||row.zt==8&&$("#lx").val()==1){
									content ="<tr bgcolor=\""+getWarningColor("#fefefe",row.zgxq,row.zt)+"\" ><td class='fixed-column text-center'></td>" ;
								}
						   }					
						if((row.zt==2&&$("#lx").val()==2)||$("#lx").val()==1){
						htmlContent += [
								content,							
								"<td  class='text-left'><a href='javascript:void(0);'onClick='look(\""
									+ row.id + "\")' >"+ row.dh+ "</a></td>",
								"<td class=' text-left' >" + row.zgxq.substring(0,10)+"</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.zgzt) + "'>"+ StringUtil.escapeStr(row.zgzt) + "</td>",
								"<td class='text-left' >"+ StringUtil.escapeStr(row.zrbmname) + "</td>",
								"<td class='text-left'>"+ zts[row.zt]+ "</td>",
								"<td class='text-left'>" + StringUtil.escapeStr(row.xfrname)+ "</td>",
								"<td class='text-center' >" + row.xfsj + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.xfyy) + "'>"+StringUtil.escapeStr(row.xfyy) + "</td>",
								"<td class='text-left' >"+getAttacments(row.xfattachments) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.fkrname) + "'>"+StringUtil.escapeStr(row.fkrname) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.fksj) + "'>"+StringUtil.escapeStr(row.fksj) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.zgsm) + "'>"+StringUtil.escapeStr(row.zgsm) + "</td>",
								"<td class='text-left'>"+getAttacments(row.zgattachments) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.shrname) + "'>"+StringUtil.escapeStr(row.shrname) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.shsm) + "'>"+StringUtil.escapeStr(row.shsm) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.shsj) + "'>"+StringUtil.escapeStr(row.shsj) + "</td>",
								"<td class='text-left' >"+getAttacments(row.shattachments) + "</td>",
								"<td class='text-left' >"+StringUtil.escapeStr(row.gbrname)+ "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.gbyy) + "'>"+StringUtil.escapeStr(row.gbyy) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.gbsj) + "'>"+StringUtil.escapeStr(row.gbsj) + "</td>",
								"<td class='text-left'>"+getAttacments(row.gbattachments) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whsj) + "'>"+StringUtil.escapeStr(row.whsj) + "</td>",
								"<td class='text-left'>"+StringUtil.escapeStr(row.whbmname) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whrname) + "'>"+StringUtil.escapeStr(row.whrname) + "</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode)) + "'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode)) + "</td>",
								, "</tr>" ].join("");
						}
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
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {

		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}

function add() {
	window.location =basePath+"/quality/selfquality/add?pageParam="+encodePageParam();
}





$("#dprtcode_search").change(function() {
	searchRecord();
});
$("#zt_search").change(function() {
	searchRecord();
});

var zts=["","保存","待处理","待审核","","","","","作废","关闭","完成"];

function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);

}
function update(id){
	window.location =basePath+"/quality/selfquality/edit?id="+id;
}

function look(id){
	window.location =basePath+"/quality/selfquality/look?id="+id;
}

function closeRecord(id,dprtcode){
	clearData();
	$("#djid").val(id);
	$("#djdprtcode").val(dprtcode);
	$("#closeModal").modal("show");
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
				"fileType" : "selfquality",
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
				trHtml = trHtml + '<td class="text-left">'
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

function closeData(){
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
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
	param.attachments = attachments;
	param.zt=9;
	param.id=$("#djid").val();
	param.gbyy=$("#gbyy").val();
	param.dprtcode=$("#djdprtcode").val();
	param.gbrid=$("#whrid").val();
	param.whrid=$("#whrid").val();
	param.whbmid=$("#whbmid").val();
	AjaxUtil.ajax({
		url : basePath
				+ "/quality/selfquality/updateRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			AlertUtil.showMessage('关闭成功!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
			clearData();
		}
	});
}

function Delete(id){
	var param = {};
	param.zt=8;
	param.id=id;
	param.whrid=$("#whrid").val();
	param.whbmid=$("#whbmid").val();
	AjaxUtil.ajax({
		url : basePath
				+ "/quality/selfquality/updateRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			AlertUtil.showMessage('作废成功!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}

function getAttacments(list){
	if(list!=null){
		var attachment='';
				if (list.length > 0) {
					for (var i = 0; i < list.length; i++) {
						attachment = attachment
								+ '<a href="#" title="'+StringUtil.escapeStr(list[i].wbwjm)+'"  onclick="downloadfile(\''
								+ list[i].id + '\')" >'
								+ StringUtil.escapeStr(list[i].wbwjm) + '</a><br>';
					}
				}

		return attachment;
	}else{
		return "";
	}
}

function gbValidation(){
	validatorForm = $("#form").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			gbyy : {
				validators : {
					notEmpty : {
						message : '关闭原因不能为空'
					}
				}
			}
		}
	})
}

function clearData(){
	$("#gbyy").val("");
	$('#filelist').html("");
	$("#wbwjm").val("");
	uploadObj.responses.length=0;
}

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

function searchreset() {
	$("#keyword_search").val("");
	$("#zgxq_search").val("");
	$("#zt_search").each(function() {
		$(this).val("");
	});
	$("#dprtcode_search").each(function() {
		$(this).val("");
	});
	var zzjg = $("#dprtcode").val();
	$("#dprtcode_search").val(zzjg);
	$("#zt_search").val("");
	searchRecord();
}

var currentDate= new Date().toLocaleDateString();
function getWarningColor(bgcolor,zgxq,zt){
	var zgxq1=zgxq;
	var day=zgxq1.substring(8,9)+zgxq1.substring(9,10);
	zgxq=zgxq.substring(0,4)+"/"+(zgxq.substring(5,6)=="0"?"":zgxq.substring(5,6))+zgxq.substring(6,7)+"/"+day;
	
	if(zt == 1 || zt==8 || zt==9||zt==10){
		return bgcolor;
	}
	
	if(compareJb2(zgxq,currentDate,jb1,jb2)){
		bgcolor = warningColor.level2;//黄色
	}
	if(compareJb1(zgxq,currentDate,jb1)){
		bgcolor = warningColor.level1;//红色
	}
	
	return bgcolor;
}

function compareJb1(zgxq, nowDate,jb1) {
    var arr = zgxq.split("/");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = nowDate.split("/");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    var idays=parseInt((starttimes - lktimes)/86400000)-jb1
   if(idays>0){
	   return false;
   }else{
	   return true;
   }
}

function compareJb2(zgxq, nowDate,jb1,jb2) {
    var arr = zgxq.split("/");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = nowDate.split("/");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    var iJb1=parseInt((starttimes - lktimes)/86400000)-jb1
    var iJb2=parseInt((starttimes - lktimes)/86400000)-jb2
   if(iJb1>0&&iJb2<0){
	   return true;
   }else{
	   return false;
   }
}