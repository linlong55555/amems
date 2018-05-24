
AttachmengsListView = {
	id:'AttachmengsListView',
	tbodyId:'attachmentsListViewTbody',
	attachheadId : "attachHeadView",
	param: {
		djid:null,//关联附件id
		attachHead : true,
		multiple:true,
		dragDrop:false,
		showStatusAfterSuccess:false,
		showStatusAfterError: false,
		showDelete:false,
		maxFileCount:100,
		fileType:"default",//存放文件夹名称
		fileName:"myfile",
		returnType:"json",
		removeAfterUpload:true,
		uploadStr:"上传",
		statusBarWidth:150,
		dragdropWidth:150
	},
	uploadObj : null,
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initUploadObj();
		//显示或隐藏头
		if(this.param.attachHead){
			$("#"+this.attachheadId, $("#"+this.id)).show();
		}else{
			$("#"+this.attachheadId, $("#"+this.id)).hide();
		}
		//回显数据
		if(this.param.djid){
			this.loadAttachements();
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
		$("#"+this.id).show();
	},
	initUploadObj : function(){
		var this_ = this;
		this.uploadObj = $("#fileuploader").uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : this_.param.multiple,
					dragDrop : this_.param.dragDrop,
					showStatusAfterSuccess : this_.param.showStatusAfterSuccess,
					showDelete : this_.param.showDelete,
					maxFileCount : this_.param.maxFileCount,
					formData : {
						"fileType" : this_.param.fileType,
						"wbwjm" : function() {
							return $('#awbwjm').val()
						}
					}, 
					fileName : this_.param.fileName,
					returnType : this_.param.returnType,
					removeAfterUpload : this_.param.removeAfterUpload,
					uploadStr : this_.param.uploadStr,
					statusBarWidth : this_.param.statusBarWidth,
					dragdropWidth : this_.param.dragdropWidth,
					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
					{
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
							trHtml += '<tr bgcolor="#f9f9f9" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
							
							trHtml += '<td style="text-align:center;vertical-align:middle;">';
							trHtml += 	  "<span name='orderNumber'>"+(i+1)+"</span>";
							trHtml += '</td>';
							
							trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'" class="text-left">';
							trHtml += "<a href='javascript:void(0);' onclick=\""+this_.id+".downloadfile('"+ attachments[i].id+"')\">"+StringUtil.escapeStr(attachments[i].wbwjm)+"</a>";
							trHtml += '</td>';
							
							trHtml += '<td class="text-left">' + attachments[i].wjdxStr + ' </td>';
							trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" class="text-left">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
							trHtml += '<td>' + attachments[i].czsj+ '</td>';
							trHtml += '<input type="hidden" name="relativePath" value=\''+ attachments[i].relativePath + '\'/>';
							trHtml += '</tr>';
						}
						$("#"+this_.tbodyId, $("#"+this_.id)).append(trHtml);
					}else{
						$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
					}
				}
			}
		});
	},
	clear : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	},
	downloadfile : function(id){
		//window.open(basePath + "/common/downfile/" + id);
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
}

