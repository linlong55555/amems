
	var tableMap = {
			"1" : "教育经历",
			"2" : "外语水平",
			"3" : "聘任职称",
			"4" : "工作履历",
			"5" : "岗位/职务",
			"6" : "技术履历",
			"7" : "维修执照",
			"8" : "机型信息",
			"9" : "维修技术等级升级记录",
			"10" : "培训记录",
			"11" : "业务考核记录",
			"12" : "学术成就",
			"13" : "嘉奖记录",
			"14" : "事故征候情况",
			"15" : "负有责任的不安全事件",
			"16" : "不诚信行为",
			"17" : "基本信息"
	}

	$(function(){
		initAttachmentAttachment();
	});
	
	function initAttachmentAttachment(){
		$("#fileuploader").uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					maxFileCount : 100,
					formData : {
						"fileType" : "maintenancepersonnel",
						"wbwjm" : function() {
							return $('#wbwjm').val();
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
//					uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
//					uploadButtonClass: "ajax-file-upload_ext",
					uploadStr:"<i class='fa fa-upload'></i>",
					/*ajax-file-upload_ext*/
					uploadButtonClass:"btn btn-default btn-uploadnew",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
						 trHtml = trHtml+'<td><div>';
						 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delBasicfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
						 trHtml = trHtml+'</div></td>';
						 trHtml = trHtml+'<td class="text-left">基本信息</td>';
						 var wjmc = StringUtil.escapeStr(data.attachments[0].wbwjm+(data.attachments[0].hzm ? "."+data.attachments[0].hzm : ""));
						 trHtml = trHtml+'<td class="text-left" title="'+wjmc+'">'+wjmc+'</td>';
						 
						 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
						 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
						 trHtml = trHtml+'<td>'+(data.attachments[0].czsj||data.attachments[0].realname)+'</td>';
						 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(data.attachments[0].wjdx)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(data.attachments[0].uuid)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(data.attachments[0].hzm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(data.attachments[0].czsj)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="type" value="17"/>';
						 trHtml = trHtml+'</tr>';	 
						 $('#attachment_attachment_list>.non-choice').remove();
						 $('#attachment_attachment_list').append(trHtml);
						 personnel.attachment.updateBasicAttachments(getAttachments("17"));
					}
					
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
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
	}
	
	
	/**
	* 删除附件
	* @param newFileName
	*/
	function delBasicfile(uuid) {
		var key = $('#' + uuid).attr('key');
		$('#' + uuid).remove();
		personnel.attachment.updateBasicAttachments(getAttachments("17"));
		if(key){
			personnel.attachment.remove(key);
		}
		if($("[name='filelist']:visible>tr").length == 0){
			$("[name='filelist']:visible").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		}
	}
	
	/**
	 * 加载附件
	 * @param id
	 */
	function loadBasicAttachments() {
		AjaxUtil.ajax({
					url : basePath + "/common/loadAttachments",
					type : "post",
					data : {
						mainid : $("#personnelId").val()
					},
					success : function(data) {
						if (data.success) {
//							var attachments = data.attachments;
//							var obj = personnel.attachment.getAll();
//							attachments = attachments.concat(obj);
							var attachments = personnel.attachment.getAll();
							var delAttachments = personnel.attachment.getAllRemoveAttachments();
							for(var i = 0; i < attachments.length; i++){
								if($.inArray(attachments[i].uuid, delAttachments) != -1){
									attachments.splice(i, 1);
								}
							}
							$('#attachment_attachment_list>tr').remove();
							if (attachments.length > 0) {
								var trHtml = '';
								for (var i = 0; i < attachments.length; i++) {
									var wjmc = StringUtil.escapeStr(attachments[i].wbwjm+(attachments[i].hzm ? "."+attachments[i].hzm : ""));
									if(attachments[i].id){
										var czrname = attachments[i].user ? 
												(attachments[i].user.username+" "+data.attachments[i].user.realname) : attachments[i].czrname;
										trHtml = trHtml
										+ '<tr bgcolor="#f9f9f9" id=\''
										+ attachments[i].uuid + '\' key=\''
										+ attachments[i].id + '\' >';
										if($("#type").val() != '3'){
											trHtml = trHtml + '<td><div>';
											if(attachments[i].type == '17'){
												trHtml = trHtml
												+ '<i class="icon-trash color-blue cursor-pointer" onclick="delBasicfile(\''
												+ attachments[i].uuid
												+ '\')" title="删除 Delete">  ';
											}
											trHtml = trHtml + '</div></td>';
										}
										trHtml = trHtml+'<td class="text-left" title="'+tableMap[attachments[i].type]+'">'+tableMap[attachments[i].type]+'</td>';
										trHtml = trHtml
										+ '<td class="text-left" name="wbwjm" title="'+wjmc+'"> <a class="cursor-pointer" onclick="downloadfile(\''
										+ attachments[i].id + '\')" >'
										+ wjmc + '</a></td>';
										trHtml = trHtml + '<td class="text-left">'
										+ attachments[i].wjdxStr + ' </td>';
										trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
										+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
										trHtml = trHtml + '<td>' + (attachments[i].czsj||attachments[i].realname)
										+ '</td>';
										trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
										trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(attachments[i].yswjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(attachments[i].nbwjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(attachments[i].wbwjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(attachments[i].cflj)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(attachments[i].wjdx)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(attachments[i].uuid)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(attachments[i].hzm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(attachments[i].wjdxStr)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(czrname)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(attachments[i].czsj)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="type" value=\''+StringUtil.escapeStr(attachments[i].type)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="id" value=\''+StringUtil.escapeStr(attachments[i].id)+'\'/>';
										trHtml = trHtml + '</tr>';
									}else{
										 trHtml = trHtml+'<tr bgcolor="#f9f9f9" id=\''+attachments[i].uuid+'\'>';
										 trHtml = trHtml+'<td><div>';
										 if(attachments[i].type == '17'){
											 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delBasicfile(\''+attachments[i].uuid+ '\')" title="删除 Delete">  ';
										 }
										 trHtml = trHtml+'</div></td>';
										 trHtml = trHtml+'<td class="text-left">'+tableMap[attachments[i].type]+'</td>';
										 trHtml = trHtml+'<td class="text-left">'+wjmc+'</td>';
										 trHtml = trHtml+'<td class="text-left">'+attachments[i].wjdxStr+' </td>';
										 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(attachments[i].czrname)+'</td>';
										 trHtml = trHtml+'<td>'+(attachments[i].czsj||attachments[i].realname)+'</td>';
										 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+attachments[i].relativePath+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(attachments[i].yswjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(attachments[i].nbwjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(attachments[i].wbwjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(attachments[i].cflj)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(attachments[i].wjdx)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(attachments[i].uuid)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(attachments[i].hzm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(attachments[i].wjdxStr)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(attachments[i].czrname)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(attachments[i].czsj)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="type" value=\''+StringUtil.escapeStr(attachments[i].type)+'\'/>';
										 trHtml = trHtml+'</tr>';	 
									}
								}
								$('#attachment_attachment_list').append(trHtml);
							}else{
								if($("#type").val() == 1 || $("#type").val() == 2){
									$('#attachment_attachment_list').append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
								}else{
									$('#attachment_attachment_list').append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
								}
							}
							//附件
							if(!checkBtnPermissions("quality:maintenancepersonnel:13")){
								$("#fjDiv").hide();
								//负有责任的不安全事件
								$("#attachment_attachment_table").find("th").eq(0).hide();
								if($("#attachment_attachment_list").find("tr").eq(0).find("td").length <= 1){
									$("#attachment_attachment_list").find("tr").eq(0).find("td").eq(0).attr("colspan",5);
								}else{
									$("#attachment_attachment_list").find("tr").each(function () {
										$(this).find("td").eq(0).hide();
									});
								}
							}
							
						}
					}
				});
	}
