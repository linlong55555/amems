var delAttachements = [];
$(function(){
		 refreshPermission();
		 Navigation(menuCode,"修改自我质量管理","Edit SelfQaulity Management");
		 if($("#xffjid").val()!=''){
			 loadAttachments($("#xffjid").val(),'filelist');
		 }
		 if($("#zgfjid").val()!=''){
			 loadAttachments($("#zgfjid").val(),'zgfilelist');
		 }
		 if($("#shfjid").val()!=''){
			 loadAttachments($("#shfjid").val(),'shfilelist'); 
		 }
		 $('#zgxq').datepicker({
				autoclose : true,
				clearBtn : true
			});
		if($("#zt").val()==1){
			$("#zgxx").hide();
			$("#shxx").hide();
			$("#gcjl").hide();
			$("#fk").hide();
			$("#shwc").hide();
			$("#shbh").hide();
		}
		if($("#zt").val()==2){
			zgValidation();
			$("#zgzt").attr("disabled", true);
			$("#zgxq").attr("disabled", true);
			$("#xfyy").attr("disabled", true);
			$("#zrbmbtn").attr("disabled", true);
			$("#fileuploader").attr("disabled", true);
			document.getElementById('xffjdiv').style.display = "none";
			$("#shxx").hide();
			$("#xf").hide();
			$("#save").hide();
			$("#shwc").hide();
			$("#shbh").hide();
			jlgc($("#id").val());
		}
		if($("#zt").val()==3){
			shValidation();
			$("#zgzt").attr("disabled", true);
			$("#zgxq").attr("disabled", true);
			$("#xfyy").attr("disabled", true);
			$("#zrbmbtn").attr("disabled", true);
			$("#zgsm").attr("disabled", true);
			$("#fileuploader").attr("disabled", true);
			document.getElementById('xffjdiv').style.display = "none";
			document.getElementById('zgfjdiv').style.display = "none";
			$("#xf").hide();
			$("#save").hide();
			$("#fk").hide();
			jlgc($("#id").val());
		}
	 });

function save(){
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
	param.zgzt=$("#zgzt").val();
	param.zgxq=$("#zgxq").val();
	param.zrbmid=$("#zrbmid").val();
	param.zt=1;
	param.id=$("#id").val();
	param.dprtcode=$("#dprtcode").val();
	param.zrbmid=$("#zrbmid").val();
	param.xfrid=$("#whrid").val();
	param.xfyy=$("#xfyy").val();
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
			AlertUtil.showMessage('保存成功!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
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
/**
* 添加时删除附件
* 
* @param uuid
*/
function delfile(uuid) {
var responses = uploadObj.responses;
var zgresponses = zguploadObj.responses;
var shresponses = shuploadObj.responses;
var len = uploadObj.responses.length;
var zglen=zguploadObj.responses.length;
var shlen= shuploadObj.responses.length;
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
if (zglen > 0) {
	for (var i = 0; i < zglen; i++) {
		if (zgresponses[i].attachments[0].uuid != uuid) {
			fileArray.push(zgresponses[i]);
		}
	}
	zguploadObj.responses = fileArray;
	zguploadObj.selectedFiles -= 1;
	$('#' + uuid).remove();
	}
if (shlen > 0) {
	for (var i = 0; i < shlen; i++) {
		if (shresponses[i].attachments[0].uuid != uuid) {
			fileArray.push(shresponses[i]);
		}
	}
	shuploadObj.responses = fileArray;
	shuploadObj.selectedFiles -= 1;
	$('#' + uuid).remove();
	}
}

function deletefile(uuid) {
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var zgresponses = zguploadObj.responses;
		var zglen = zguploadObj.responses.length;
		var shresponses = shuploadObj.responses;
		var shlen = shuploadObj.responses.length;
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
		if (zglen > 0) {
			for (var i = 0; i < zglen; i++) {
				if (zgresponses[i].attachments[0].uuid != uuid) {
					fileArray.push(zgresponses[i]);
				}
			}
			zguploadObj.responses = fileArray;
			zguploadObj.selectedFiles -= 1;
		}
		if (shlen > 0) {
			for (var i = 0; i < shlen; i++) {
				if (shresponses[i].attachments[0].uuid != uuid) {
					fileArray.push(shresponses[i]);
				}
			}
			shuploadObj.responses = fileArray;
			shuploadObj.selectedFiles -= 1;
		}
		$('#' + uuid).remove();

	} else {
		$('#' + uuid).remove();
		delAttachements.push({
			id : key
		});
	}
}
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function back(){
	if($("#zt").val()!=2){
		window.location =basePath+"/quality/selfquality/main?pageParam="+pageParam;
	}else{
		window.location =basePath+"/quality/selfquality/rectification?pageParam="+pageParam;
	}
}

function openZrbm(){
	var dprtcode =$("#dprtcode").val();
	departmentModal.show({
		selected : $("#zrbmid").val(),// 原值，在弹窗中默认勾选
		dprtcode : dprtcode,
		callback : function(data) {// 回调函数
			$("#zrbmid").val(formatUndefine(data.id));
			$("#zrbm").val(formatUndefine(data.dprtname));
		},
		
	})
}

function loadAttachments(id,obj){
	AjaxUtil
	.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		data : {
			mainid : id
		},
		success : function(data) {
			if (data.success) {
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
								+ '  ';
						trHtml = trHtml + '</div></td>';
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm)
								+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left">'
								+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml = trHtml + '<td>'
								+ attachments[i].czsj + '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath
								+ '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#'+obj).append(trHtml);
				}
			}
		}
	});
}

var zguploadObj = $("#zgfileuploader")
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
					return $('#zgwjm').val()
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
				$('#zgfilelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#zgwjm').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#zgwjm').focus();
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

var shuploadObj = $("#shfileuploader")
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
					return $('#shwjm').val()
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
				$('#shfilelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#zgwjm').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#zgwjm').focus();
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

function xf(){
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
	param.zgzt=$("#zgzt").val();
	param.zgxq=$("#zgxq").val();
	param.zrbmid=$("#zrbmid").val();
	param.zt=2;
	param.xffjid=$("#xffjid").val();
	param.id=$("#id").val();
	param.dprtcode=$("#dprtcode").val();
	param.zrbmid=$("#zrbmid").val();
	param.xfrid=$("#whrid").val();
	param.xfyy=$("#xfyy").val();
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
			AlertUtil.showMessage('下发成功!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}

function jlgc(id){
	var param={};
	param.mainid=id;
	AjaxUtil
	.ajax({
		url : basePath + "/quality/selfquality/getJlgc",
		type : "post",
		async : false,
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {			
				var len = data.length;
				if (len > 0) {
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml+ '<tr><td class="text-left">'+ data[i].czrname+'</td>';
						trHtml = trHtml+ '<td class="text-left">'+ data[i].czsj+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'+ data[i].hd + ' </td>';
						trHtml = trHtml + '<td class="text-left"  title="'+StringUtil.escapeStr(data[i].czsm)+'">'+ StringUtil.escapeStr(data[i].czsm) + '</td>';
						trHtml = trHtml + '<td>'+ getAttacment(data[i].fjid) + '</td>';
						trHtml = trHtml + '</tr>';
					}
					$('#gcjllist').append(trHtml);
				}
		}
	});
}

function getAttacment(id){
	if(id!=null&&id!=''){
	var attachment="";
	AjaxUtil
	.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		cache:false,
		data : {
			mainid : id
		},
		success : function(data) {
			if (data.success) {
				var attachments = data.attachments;
				var len = data.attachments.length;
				if (len > 0) {
					for (var i = 0; i < len; i++) {
						attachment = attachment
								+ '<a href="#" title="'+StringUtil.escapeStr(data.attachments[i].wbwjm)+'"  onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a><br>';
					}
				}
			}
		}
	});
	id='';
	return attachment;
	}else{
		id="";
		return "";
	}
}

function fk(){
	$('#form1').data('bootstrapValidator').validate();
	if (!$('#form1').data('bootstrapValidator').isValid()) {
		return false;
	}
	var attachments = [];
	var zgresponses = zguploadObj.responses;
	var zglen = zguploadObj.responses.length;
	if (zglen != undefined && zglen > 0) {
		for (var i = 0; i < zglen; i++) {
			attachments.push({
				'yswjm' : zgresponses[i].attachments[0].yswjm,
				'wbwjm' : zgresponses[i].attachments[0].wbwjm,
				'nbwjm' : zgresponses[i].attachments[0].nbwjm,
				'wjdx' : zgresponses[i].attachments[0].wjdx,
				'cflj' : zgresponses[i].attachments[0].cflj,
				'id' : zgresponses[i].attachments[0].id,
				'operate' : zgresponses[i].attachments[0].operate

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
	param.zgsm=$("#zgsm").val();
	param.zt=3;
	param.id=$("#id").val();
	param.dprtcode=$("#dprtcode").val();
	param.zgfjid=$("#zgfjid").val();
	param.fkrid=$("#whrid").val();
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
			AlertUtil.showMessage('反馈成功!',
					'/quality/selfquality/rectification?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}
function shwc(){
	$('#form2').data('bootstrapValidator').validate();
	if (!$('#form2').data('bootstrapValidator').isValid()) {
		return false;
	}
	var attachments = [];
	var shresponses = shuploadObj.responses;
	var shlen = shuploadObj.responses.length;
	if (shlen != undefined && shlen > 0) {
		for (var i = 0; i < shlen; i++) {
			attachments.push({
				'yswjm' : shresponses[i].attachments[0].yswjm,
				'wbwjm' : shresponses[i].attachments[0].wbwjm,
				'nbwjm' : shresponses[i].attachments[0].nbwjm,
				'wjdx' : shresponses[i].attachments[0].wjdx,
				'cflj' : shresponses[i].attachments[0].cflj,
				'id' : shresponses[i].attachments[0].id,
				'operate' : shresponses[i].attachments[0].operate

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
	param.shsm=$("#shsm").val();
	param.zt=10;
	param.id=$("#id").val();
	param.dprtcode=$("#dprtcode").val();
	param.shfjid=$("#shfjid").val();
	param.shrid=$("#whrid").val();
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
			AlertUtil.showMessage('审核通过!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}

function shbh(){
	$('#form2').data('bootstrapValidator').validate();
	if (!$('#form2').data('bootstrapValidator').isValid()) {
		return false;
	}
	var attachments = [];
	var shresponses = shuploadObj.responses;
	var shlen = shuploadObj.responses.length;
	if (shlen != undefined && shlen > 0) {
		for (var i = 0; i < shlen; i++) {
			attachments.push({
				'yswjm' : shresponses[i].attachments[0].yswjm,
				'wbwjm' : shresponses[i].attachments[0].wbwjm,
				'nbwjm' : shresponses[i].attachments[0].nbwjm,
				'wjdx' : shresponses[i].attachments[0].wjdx,
				'cflj' : shresponses[i].attachments[0].cflj,
				'id' : shresponses[i].attachments[0].id,
				'operate' : shresponses[i].attachments[0].operate

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
	param.shsm=$("#shsm").val();
	param.zt=2;
	param.id=$("#id").val();
	param.dprtcode=$("#dprtcode").val();
	param.shfjid=$("#shfjid").val();
	param.shrid=$("#whrid").val();
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
			AlertUtil.showMessage('审核驳回!',
					'/quality/selfquality/main?id=' + data+"&pageParam="+pageParam);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}

function zgValidation(){
	validatorForm = $("#form1").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			zgsm : {
				validators : {
					notEmpty : {
						message : '整改说明不能为空'
					}
				}
			}
		}
	})
}

function shValidation(){
	validatorForm = $("#form2").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			shsm : {
				validators : {
					notEmpty : {
						message : '整改说明不能为空'
					}
				}
			}
		}
	})
}