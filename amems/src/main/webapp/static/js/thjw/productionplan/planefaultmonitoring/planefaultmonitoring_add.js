var delAttachements = [];
$(function(){
		 refreshPermission();
		 Navigation(menuCode,"新增飞机故障监控","Add AircraftFault");
		 var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = '';
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				});
			}
			$("#fjzch").html(planeRegOption); 
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
	param.fjzch=$("#fjzch").val();
	param.gzxx=$("#gzxx").val();
	param.bz=$("#bz").val();
	param.gbzt=0;
	param.dprtcode=$("#dprtcode").val();
	param.zddwid=$("#zddwid").val();
	param.zdrid=$("#zdrid").val();
	AjaxUtil.ajax({
		url : basePath
				+ "/productionplan/planefaultmonitoring/addPalneFaultMonitoring",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			AlertUtil.showMessage('保存成功!',
					'/productionplan/planefaultmonitoring/main?id=' + data+"&pageParam="+pageParam);
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
				"fileType" : "planefaultmonitoring",
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
				trHtml = trHtml + '<td class="text-left"title="'+data.attachments[0].user.username + " "
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
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function back(){
	window.location =basePath+"/productionplan/planefaultmonitoring/main?pageParam="+pageParam;
}