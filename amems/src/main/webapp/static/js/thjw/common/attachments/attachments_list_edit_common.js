
attachmentsUtil = {
		attachment : {},
		getAttachmentsComponents : function(id){
			if(id == null || id == ''){
				id = 'attachments_list_edit';
			}
			if(this.attachment[id] != null && this.attachment[id] != ''){
				return this.attachment[id];
			}
			var attachmentsComponents = {
					id:id,
					tbodyId:'attachments_list_tbody',
					attachDivId : "attach_div",
					attachheadId : "attachHead",
					fileHeadId : "fileHead",
					colOptionheadId : "colOptionhead",
					param: {
						djid:null,//关联附件id
						attachDatas:[],//附件数据
						attachHead : true,//模块头是否显示
						chinaHead : '附件',//模块中文头
						englishHead : 'Attachments',//模块英文头
						fileHead : true,//文件头是否显示
						chinaFileHead : '文件说明',//文件中文头
						englishFileHead : 'File Desc',//文件英文头
						colOptionhead : true,//是否可编辑
						left_column : true,//左边列是否显示
						chinaColFileTitle : '文件说明',//字段列中文
						englishColFileTitle : 'File Desc',//字段列英文
						multiple : true,//是否多选
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
						/*ajax-file-upload_ext*/
						uploadButtonClass:"btn btn-default btn-uploadnew",
						statusBarWidth:150,
						dragdropWidth:150
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
						}else if(this.param.attachDatas && this.param.attachDatas.length > 0){
							this.loadAttachementsWithData();
						}else{
							$("#"+this.tbodyId, $("#"+this.id)).empty();
							if(this.param.colOptionhead){
								$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='6' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
							}else{
								$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
							}
						}
						$("#"+this.id).show();
					},
					initTitle : function(){
						$("#chinaHead", $("#"+this.id)).html(this.param.chinaHead);
						$("#englishHead", $("#"+this.id)).html(this.param.englishHead);
						$("#chinaFileHead", $("#"+this.id)).html(this.param.chinaFileHead);
						$("#englishFileHead", $("#"+this.id)).html(this.param.englishFileHead);
						$("#chinaColFileTitle", $("#"+this.id)).html(this.param.chinaColFileTitle);
						$("#englishColFileTitle", $("#"+this.id)).html(this.param.englishColFileTitle);
					},
					initDetele : function(){
						var this_ = this;
						$(".deleterow").on("click",function(){
							var id = $(this).attr("fjid");
							this_.delfile(id);
						});
					},
					initDownload : function(){
						var this_ = this;
						$(".downloadfile").on("click",function(){
							var id = $(this).attr("fjid");
							this_.downloadfile(id);
						});
					},
					initViewOrHide : function(){
						//显示或隐藏头
						if(this.param.attachHead){
							$("#"+this.attachheadId, $("#"+this.id)).show();
							$("#"+this.attachDivId, $("#"+this.id)).addClass("panel panel-primary");
						}else{
							$("#"+this.attachheadId, $("#"+this.id)).hide();
							$("#"+this.attachDivId, $("#"+this.id)).removeClass("panel panel-primary");
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
						}else{
							$("#"+this.colOptionheadId, $("#"+this.id)).hide();
						}
						//显示或隐藏字段列
						if(this.param.left_column){
							$("#left_column", $("#"+this.id)).next().removeClass("col-lg-12 col-md-12 col-sm-12 col-xs-12").addClass("col-lg-11 col-md-11 col-sm-10 col-xs-9");
							$("#left_column", $("#"+this.id)).show();
						}else{
							$("#left_column", $("#"+this.id)).next().removeClass("col-lg-11 col-md-11 col-sm-10 col-xs-9").addClass("col-lg-12 col-md-12 col-sm-12 col-xs-12");
							$("#left_column", $("#"+this.id)).hide();
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
										"fileType" : this_.param.fileType,
										"wbwjm" : function() {
											return '';
										}
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
											trHtml += '<i class="deleterow icon-trash color-blue cursor-pointer" fjid="'+data.attachments[0].uuid+'"  title="删除 Delete">  ';
											trHtml += '</div></td>';
										}
										trHtml += '<td style="text-align:center;vertical-align:middle;">';
										trHtml += 	  "<span name='orderNumber'>"+this_.orderNumber+"</span>";
										trHtml += '</td>';
										
										trHtml += '<td title="'+(StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm))+'" class="text-left">';
										//trHtml += "<a href='javascript:void(0);' onclick=\""+this_.id+".downloadfile('"+ data.attachments[0].uuid+"')\">"+data.attachments[0].yswjm+"</a>";
										
										trHtml += StringUtil.escapeStr(data.attachments[0].wbwjm)+ (data.attachments[0].hzm ? "."+StringUtil.escapeStr(data.attachments[0].hzm) : "");
										
										trHtml += '<td class="text-left">' + data.attachments[0].wjdxStr + ' </td>';
										trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'" class="text-left">' + StringUtil.escapeStr(data.attachments[0].user.displayName) + '</td>';
										trHtml += '<td>' + data.attachments[0].czsj + '</td>';
										trHtml += '<input type="hidden" name="relativePath" value=\'' + data.attachments[0].relativePath + '\'/>';
										trHtml += '</tr>';
										$("#"+this_.tbodyId, $("#"+this_.id)).append(trHtml);
										this_.initDetele();
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
														$("#"+option, $("#"+this_.id)).focus();
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
											trHtml += '<tr bgcolor="#f9f9f9" id=\''+ attachments[i].id + '\' key=\''+ attachments[i].id + '\' >';
											if(this_.param.colOptionhead){
												trHtml += '<td><div>';
												//trHtml += '<i class="icon-download-alt color-blue cursor-pointer" onclick="'+this_.id+'.downloadfile(\''+ attachments[i].id+ '\')" title="下载附件 "></i>&nbsp;&nbsp;';
												trHtml += '<i class="deleterow icon-trash color-blue cursor-pointer" fjid="'+attachments[i].id+'" title="删除 Delete">  ';
												trHtml += '</div></td>';
											}
											trHtml += '<td style="text-align:center;vertical-align:middle;">';
											trHtml += 	  "<span name='orderNumber'>"+this_.orderNumber+"</span>";
											trHtml += '</td>';
											
											var wjmc = StringUtil.escapeStr(attachments[i].wbwjm)+ (attachments[i].hzm ? "."+StringUtil.escapeStr(attachments[i].hzm) : "");
											trHtml += '<td title="'+wjmc+'" class="text-left">';
											trHtml += "<a href='javascript:void(0);' class='downloadfile' fjid='"+attachments[i].id+"' >"+wjmc+"</a>";
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
											$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
										}else{
											$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
										}
									}
									this_.initDetele();
									this_.initDownload();
								}
							}
						});
					},
					loadAttachementsWithData : function(){
						var this_ = this;
						var attachments = this_.removeDeleteAttachment(this_.param.attachDatas);
						var len = attachments.length;
						$("#"+this_.tbodyId, $("#"+this_.id)).empty();
						if (len > 0) {
							var trHtml = '';
							for (var i = 0; i < len; i++) {
								this_.orderNumber++;
								trHtml += '<tr bgcolor="#f9f9f9" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
								if(this_.param.colOptionhead){
									trHtml += '<td><div>';
									trHtml += '<i class="deleterow icon-trash color-blue cursor-pointer" fjid="'+attachments[i].uuid+'" title="删除 Delete">  ';
									trHtml += '</div></td>';
								}
								trHtml += '<td style="text-align:center;vertical-align:middle;">';
								trHtml += 	  "<span name='orderNumber'>"+this_.orderNumber+"</span>";
								trHtml += '</td>';
								
								var wjmc = StringUtil.escapeStr(attachments[i].wbwjm)+ (attachments[i].hzm ? "."+StringUtil.escapeStr(attachments[i].hzm) : "");
								trHtml += '<td title="'+wjmc+'" class="text-left">';
								if(attachments[i].id){
									trHtml += "<a href='javascript:void(0);' class='downloadfile' fjid='"+attachments[i].id+"' >"+wjmc+"</a>";
								}else{
									trHtml += wjmc;
								}
								trHtml += '</td>';
								
								trHtml += '<td class="text-left">' + attachments[i].wjdxStr + ' </td>';
								trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" class="text-left">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
								trHtml += '<td>' + attachments[i].czsj+ '</td>';
								trHtml += '<input type="hidden" name="relativePath" value=\''+ attachments[i].relativePath + '\'/>';
								trHtml += '</tr>';
								
								var responses = this.uploadObj.responses;
								responses.push({
									attachments : [{
										'yswjm' : attachments[i].yswjm,
										'wbwjm' : attachments[i].wbwjm,
										'nbwjm' : attachments[i].nbwjm,
										'wjdx' : attachments[i].wjdx,
										'wjdxStr' : attachments[i].wjdxStr,
										'cflj' : attachments[i].cflj,
										'hzm' : attachments[i].hzm,
										'id' : attachments[i].id,
										'uuid' : attachments[i].uuid || attachments[i].id,
										'czrname' : attachments[i].czrname,
										'czsj' : attachments[i].czsj,
										'operate' : attachments[i].operate
									}]
								});
							}
							$("#"+this_.tbodyId, $("#"+this_.id)).append(trHtml);
						}else{
							if(this_.param.colOptionhead){
								$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
							}else{
								$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
							}
						}
						this_.initDetele();
						this_.initDownload();
					},
					removeDeleteAttachment : function(list){
						var addList = [];
						var delList = [];
						var attachments = [];
						$.each(list || [], function(i, obj){
							if(obj.operate != "DEL"){
								addList.push(obj);
							}else{
								delList.push(obj);
							}
						});
						for(var i = 0; i < addList.length; i++){
							var isdelete = false;
							for(var j = 0; j < delList.length; j++){
								if(addList[i].id == delList[j].id){
									isdelete = true;
								}
							}
							if(!isdelete){
								attachments.push(addList[i]);
							}
						}
						return attachments;
					},
					delfile : function(uuid){
						var this_ = this;
						var key = $('#' + uuid, $("#"+this_.id)).attr('key');
						if (key == undefined || key == null || key == '' || key == 'undefined') {
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
							$("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
						}
					},
					downloadfile : function(id){
						//window.open(basePath + "/common/downfile/" + id);
						PreViewUtil.handle(id, PreViewUtil.Table.D_011);
					},
					getAttachments : function(){
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
									'wjdxStr' : responses[i].attachments[0].wjdxStr,
									'czrname' : responses[i].attachments[0].user ? responses[i].attachments[0].user.displayName : responses[i].attachments[0].czrname,
									'cflj' : responses[i].attachments[0].cflj,
									'hzm' : responses[i].attachments[0].hzm,
									'id' : responses[i].attachments[0].id,
									'uuid' : responses[i].attachments[0].uuid,
									'czsj' : responses[i].attachments[0].czsj,
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
						return attachments;
					}
				}
			this.attachment[id] = attachmentsComponents;
			return attachmentsComponents;
		}
}



