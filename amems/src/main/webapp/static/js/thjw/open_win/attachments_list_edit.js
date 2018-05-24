/**
 * 附件弹窗列表
 */
open_win_attachments_list_edit = {
	id:'open_win_attachments_list_edit',
	tbodyId:'attachmentsListTbody',
	attachheadId : "attachHead",
	fileHeadId : "fileHead",
	colOptionheadId : "colOptionhead",
	saveBtnId : "saveBtn",
	param: {
		djid:null,//关联附件id
		attachHead : true,
		chinaHead : '附件',
		englishHead : 'Attachments',
		fileHead : true,
		chinaFileHead : '文件说明',
		englishFileHead : 'File Desc',
		colOptionhead : true,
		chinaColFileTitle : '文件说明',
		englishColFileTitle : 'File Desc',
		multiple : true,
		dragDrop:false,
		showStatusAfterSuccess:false,
		showStatusAfterError: false,
		showDelete:false,
		maxFileCount:100,//附件上传最大文件数
		fileType:"default",//存放文件夹名称
		fileName:"myfile",
		returnType:"json",
		removeAfterUpload:true,
		/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
		uploadStr:"<i class='fa fa-upload'></i>",
		uploadButtonClass: "btn btn-default",
		statusBarWidth:150,
		dragdropWidth:150,
		callback:function(){}//回调函数
	},
	uploadObj : null,
	delAttachements : [],
	orderNumber : 0,
	show : function(param){
		this.delAttachements = [];
		this.orderNumber = 0;
		if(param){
			$.extend(this.param, param);
		}
		this.initUploadObj();
		this.initViewOrHide();
		this.initTitle();
		$('#awbwjm', $("#"+this.id)).val('');
		//回显数据
		if(this.param.djid){
			this.loadAttachements();
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			if(this.param.colOptionhead){
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr style='height:35px;'><td colspan='6' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}else{
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr style='height:35px;'><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}
		}
		$("#"+this.id).modal("show");
	},
	initTitle : function(){
		$("#chinaHead", $("#"+this.id)).html(this.param.chinaHead);
		$("#englishHead", $("#"+this.id)).html(this.param.englishHead);
		$("#chinaFileHead", $("#"+this.id)).html(this.param.chinaFileHead);
		$("#englishFileHead", $("#"+this.id)).html(this.param.englishFileHead);
		$("#chinaColFileTitle", $("#"+this.id)).html(this.param.chinaColFileTitle);
		$("#englishColFileTitle", $("#"+this.id)).html(this.param.englishColFileTitle);
	},
	initViewOrHide : function(){
		//显示或隐藏头
		if(this.param.attachHead){
			$("#"+this.attachheadId, $("#"+this.id)).show();
		}else{
			$("#"+this.attachheadId, $("#"+this.id)).hide();
		}
		//显示或隐藏上传按钮
		if(this.param.fileHead){
			$("#"+this.fileHeadId, $("#"+this.id)).show();
		}else{
			$("#"+this.fileHeadId, $("#"+this.id)).hide();
		}
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("#"+this.colOptionheadId, $("#"+this.id)).show();
			$("#"+this.saveBtnId, $("#"+this.id)).show();
		}else{
			$("#"+this.colOptionheadId, $("#"+this.id)).hide();
			$("#"+this.saveBtnId, $("#"+this.id)).hide();
		}
	},
	initUploadObj : function(){
		var this_ = this;
		this.uploadObj = $("#fileuploader", $("#"+this_.id)).uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : this_.param.multiple,
					dragDrop : this_.param.dragDrop,
					showStatusAfterSuccess : this_.param.showStatusAfterSuccess,
					showDelete : this_.param.showDelete,
					maxFileCount : this_.param.maxFileCount,
					formData : {
						"fileType" : this_.param.fileType
					}, 
					fileName : this_.param.fileName,
					returnType : this_.param.returnType,
					removeAfterUpload : this_.param.removeAfterUpload,
					uploadStr : this_.param.uploadStr,
					uploadButtonClass: this_.param.uploadButtonClass,
					statusBarWidth : this_.param.statusBarWidth,
					dragdropWidth : this_.param.dragdropWidth,
					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
					{
						var len = $("#"+this_.tbodyId, $("#"+this_.id)).find("tr").length;
						if (len == 1) {
							if($("#"+this_.tbodyId, $("#"+this_.id)).find("td").length ==1){
								$("#"+this_.tbodyId, $("#"+this_.id)).empty();
							}
						}
						this_.orderNumber++;
						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+ data.attachments[0].uuid + '\'>';
						if(this_.param.colOptionhead){
							trHtml += '<td><div>';
							trHtml += '<i class="icon-trash color-blue cursor-pointer" onclick="'+this_.id+'.delfile(\''+ data.attachments[0].uuid + '\')" title="删除 Delete">  ';
							trHtml += '</div></td>';
						}
						trHtml += '<td style="text-align:center;vertical-align:middle;">';
						trHtml += 	  "<span name='orderNumber'>"+this_.orderNumber+"</span>";
						trHtml += '</td>';
						
						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">';
						//trHtml += "<a href='javascript:void(0);' onclick=\""+this_.id+".downloadfile('"+ data.attachments[0].uuid+"')\">"+data.attachments[0].yswjm+"</a>";
						trHtml += StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm);
						
						trHtml += '<td class="text-left">' + data.attachments[0].wjdxStr + ' </td>';
						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'" class="text-left">' + StringUtil.escapeStr(data.attachments[0].user.displayName) + '</td>';
						trHtml += '<td>' + data.attachments[0].czsj + '</td>';
						trHtml += '<input type="hidden" name="relativePath" value=\'' + data.attachments[0].relativePath + '\'/>';

						trHtml += '</tr>';
						$("#"+this_.tbodyId, $("#"+this_.id)).append(trHtml);
					}
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#awbwjm', $("#"+this_.id)).val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	AlertUtil.showMessageCallBack({
									message : "文件说明不能包含如下特殊字符 < > / \\ | : \" * ?",
									option : 'awbwjm',
									callback : function(option){
										$("#"+option).focus();
									}
								});
				            	$('.ajax-file-upload-container').html("");
				            	this_.uploadObj.selectedFiles -= 1;
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
	},
	loadAttachements : function(){
		var this_ = this;
		AjaxUtil.ajax({
			url : basePath + "/common/loadAttachments",
			type : "post",
			async : false,
			data : {
				mainid : this_.param.djid
			},
			success : function(data) {
				if (data.success) {
					var attachments = data.attachments;
					var len = data.attachments.length;
					$("#"+this_.tbodyId, $("#"+this_.id)).empty();
					if (len > 0) {
						var trHtml = '';
						for (var i = 0; i < len; i++) {
							this_.orderNumber++;
							trHtml += '<tr bgcolor="#f9f9f9" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
							if(this_.param.colOptionhead){
								trHtml += '<td><div>';
								trHtml += '<i class="icon-trash color-blue cursor-pointer" onclick="'+this_.id+'.delfile(\''+ attachments[i].uuid+ '\')" title="删除 Delete">  ';
								trHtml += '</div></td>';
							}
							trHtml += '<td style="text-align:center;vertical-align:middle;">';
							trHtml += 	  "<span name='orderNumber'>"+this_.orderNumber+"</span>";
							trHtml += '</td>';
							
							trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(attachments[i].hzm)+'" class="text-left">';
							trHtml += "<a href='javascript:void(0);' onclick=\""+this_.id+".downloadfile('"+ attachments[i].id+"')\">"+StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(attachments[i].hzm)+"</a>";
							trHtml += '</td>';
							
							trHtml += '<td class="text-left">' + attachments[i].wjdxStr + ' </td>';
							trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" class="text-left">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
							trHtml += '<td>' + attachments[i].czsj+ '</td>';
							trHtml += '<input type="hidden" name="relativePath" value=\''+ attachments[i].relativePath + '\'/>';
							trHtml += '</tr>';
						}
						$("#"+this_.tbodyId, $("#"+this_.id)).append(trHtml);
					}else{
						if(this_.param.colOptionhead){
							$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
						}else{
							$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr style='height:35px;'><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
						}
					}
				}
			}
		});
	},
	delfile : function(uuid){
		var this_ = this;
		var key = $('#' + uuid, $("#"+this_.id)).attr('key');
		if (key == undefined || key == null || key == '') {
			var responses = this.uploadObj.responses;
			var len = this.uploadObj.responses.length;
			var fileArray = [];
			var waitDelArray = [];
			if (len > 0) {
				for (var i = 0; i < len; i++) {
					if (responses[i].attachments[0].uuid != uuid) {
						fileArray.push(responses[i]);
					}
				}
				this.uploadObj.responses = fileArray;
				this.uploadObj.selectedFiles -= 1;
				$('#' + uuid, $("#"+this_.id)).remove();
			}

		} else {
			$('#' + uuid, $("#"+this_.id)).remove();
			this.delAttachements.push({
				id : key
			});
		}
		this.orderNumber--;
		this.resxh();
	},
	resxh : function(){
		var this_ = this;
		var len = $("#"+this_.tbodyId, $("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#"+this_.tbodyId, $("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	getAttachmentCount : function(){
		return this.orderNumber;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	save: function(){
		var this_ = this;
		var attachments = [];
		var responses = this.uploadObj.responses;
		var len = this.uploadObj.responses.length;
		if (len != undefined && len > 0) {
			for (var i = 0; i < len; i++) {
				attachments.push({
					'yswjm' : responses[i].attachments[0].yswjm,
					'wbwjm' : responses[i].attachments[0].wbwjm,
					'nbwjm' : responses[i].attachments[0].nbwjm,
					'wjdx' : responses[i].attachments[0].wjdx,
					'cflj' : responses[i].attachments[0].cflj,
					'hzm' : responses[i].attachments[0].hzm,
					'id' : responses[i].attachments[0].id,
					'operate' : responses[i].attachments[0].operate
				});
			}
		}

		var dellen = this_.delAttachements.length;
		if (dellen != undefined && dellen > 0) {
			for (var i = 0; i < dellen; i++) {
				attachments.push({
					'id' : this_.delAttachements[i].id,
					'operate' : 'DEL'
				});
			}
		}
		if(this_.param.callback && typeof(this_.param.callback) == "function"){
			this.param.callback(attachments);
		}
	}
}
