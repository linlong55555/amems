var delAttachements = [];

$(document)
		.ready(
				function() {
					Navigation(menuCode,'再次故障保留','Again Retain')
					loadAttachements();
					$('.datepicker').datepicker({
						autoclose : true,
						clearBtn : true
					}).next().on("click", function() {
						$(this).prev().focus();
					});

					$('#fjzch').change(function() {
						loadFxjldh();
					});
					$("#fjzch").trigger("change");

					$('#fxjldh').change(
							function() {
								var fxjldhText = $("#fxjldh").find(
										"option:selected").text()
								$('#jlbym').val(fxjldhText.split('/')[0])
							});
					$("#fxjldh").trigger("change");

					$('#zcSqrq').datepicker({
						autoclose : true,
						clearBtn : true
					}).on(
							'hide',
							function(e) {
								$('#form').data('bootstrapValidator')
										.updateStatus('zcSqrq',
												'NOT_VALIDATED', null)
										.validateField('zcSqrq');
							});

					$('#zcDqrq').datepicker({
						autoclose : true,
						clearBtn : true
					}).on(
							'hide',
							function(e) {
								$('#form').data('bootstrapValidator')
										.updateStatus('zcDqrq',
												'NOT_VALIDATED', null)
										.validateField('zcDqrq');
							});

					$('#form').bootstrapValidator({
						message : '数据不合法',
						feedbackIcons : {
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							zcNr : {
								validators : {
									notEmpty : {
										message : '保留内容不能为空'
									},
									stringLength : {
										max : 300,
										message : '保留内容不能超出300个字符'
									}
								}
							},
							zcZzh : {
								validators : {
									notEmpty : {
										message : '执照号不能为空'
									},
									regexp : {
										regexp : /^[^\u4e00-\u9fa5]{0,20}$/,
										message : '执照号不包含中文且长度不超过20个字符'
									}
								}
							},

							zcBlrmc : {
								validators : {
									notEmpty : {
										message : '再次保留人不能为空'
									}
								}
							},
							zcPzrmc : {
								validators : {
									notEmpty : {
										message : '再次批准人不能为空'
									}
								}
							},

							zcSqrq : {
								validators : {
									notEmpty : {
										message : '申请日期不能为空'
									}
								}
							},
							zcDqrq : {
								validators : {
									notEmpty : {
										message : '到期日期不能为空'
									}
								}
							}
						}
					});

					function loadFxjldh() {

						$("#fxjldh").empty();
						AjaxUtil
								.ajax({
									url : basePath
											+ "/flightdata/flightrecordsheet/queryList",
									type : "post",
									async : false,
									data : JSON.stringify({
										fjzch : $('#fjzch').val()
										,dprtcode:$('#dprtcode').val() 
										,valid:'1'
									}),
									contentType : "application/json;charset=utf-8",
									success : function(data) {
										var len = data.length
										if (len > 0) {
											for (var i = 0; i < len; i++) {
												$("#fxjldh")
														.append(
																"<option value='"
																		+ data[i].fxjldh
																		+ "'>"
																		+ data[i].jlbym
																		+ '/'
																		+ data[i].fxjldh
																		+ "</option>");
											}

										}

									}
								});
					}
					
					$('#revert').on('click',function(){
						var id = $.trim($('#id').val());
						window.location.href =  basePath+'/flightdata/legacytrouble/manage?id='+id+'&pageParam='+pageParam;
					});
					
				});

// 上传
var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					maxFileCount : 10,
					formData : {
						"fileType" : "legacytrouble"
						,"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					/*uploadStr : "上传",
					statusBarWidth : 150,
					dragdropWidth : 150,*/
					showStatusAfterSuccess : false,
					showStatusAfterError: false,
					uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass: "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr,pd) 
					{
						 
						
						var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
							+ data.attachments[0].uuid + '\'>';
						trHtml = trHtml + '<td><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
								+ data.attachments[0].uuid
								+ '\')" title="删除附件 Delete attachment">  ';
						trHtml = trHtml + '</div></td>';

						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\">'
								 + StringUtil.escapeStr(data.attachments[0].wbwjm)   
								 + '</td>';

						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
								+ data.attachments[0].wjdxStr + '</td>';
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
								+  StringUtil.escapeStr(data.attachments[0].user.displayName)     + '</td>';
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-13\">'
						+ data.attachments[0].czsj
								+ '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ data.attachments[0].relativePath + '\'/>';
						

						trHtml = trHtml + '</tr>';
						$('#filelist').append(trHtml);
						
					}
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
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
 * 保存
 */
function save() {

	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showErrorMessage("请根据提示输入正确的信息");
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
	var id = $.trim($('#id').val())
	var data = {
		'id' : id,// 故障保留单号
		'zcNr' : $.trim($('#zcNr').val()),// 内容
		'zcZzh' : $.trim($('#zcZzh').val()),// 执照号
		'zcBlrid' : $.trim($('#zcBlrid').val()),// 保留人
		'zcPzrid' : $.trim($('#zcPzrid').val()),// 批准人
		'zcSqrq' : $.trim($('#zcSqrq').val()),// 首次申请日期
		'dqrq' : $.trim($('#zcDqrq').val()),// 内容
		'zcDqrq' : $.trim($('#zcDqrq').val()),// 首次到期日期
		'attachments' : attachments
	}

	var body = this;
	// 提交数据
	AjaxUtil.ajax({
		url : basePath + "/flightdata/legacytrouble/resave",
		type : "post",
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			AlertUtil.showMessageCallBack({
				message : "故障保留单再次保留成功",
				callback : function() {
					window.location.href =  basePath+'/flightdata/legacytrouble/manage?id='+id+'&pageParam='+pageParam;
				}
			});
		}
	});
};
 

/**
 * 加载附件
 */
function loadAttachements() {
	AjaxUtil
			.ajax({
				url : basePath + "/common/loadAttachments",
				type : "post",
				async : false,
				data : {
					mainid : $('#id').val()
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
								
								trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-7\"><div>';

						        trHtml = trHtml
								+ ' <i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
								+ attachments[i].uuid
								+ '\')" title="删除附件 Delete attachment">  ';
								trHtml = trHtml + '</div></td>';
								
								trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\">'
										+'<a href="#" onclick="downloadfile(\''
										+ attachments[i].id
										+ '\')">' + StringUtil.escapeStr(attachments[i].wbwjm)
										+ '</a></td>';

								trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
										+ attachments[i].wjdxStr + '</td>';
								trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
										+ StringUtil.escapeStr(attachments[i].czrname)  + '</td>';
								trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-13\">' + attachments[i].czsj
										+ '</td>';
								trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
								trHtml = trHtml + '</tr>';
							}

							$('#filelist').append(trHtml);
						}
					}
				}
			});
}


/**
 * 删除附件
 */
function delfile(uuid) {
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
 * @param newFileName
 */
function downloadfile(id) {
	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	var dprtcode  = $.trim($("#dprtcode").val()); 
	FixChapterModal.show({
		dprtcode:dprtcode,
		selected:zjh,//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = wczjh + " " + wczjhName;
			}
			$("#zjh").val(wczjh);
			$("#zjhandname").val(wczjhName);
		}
	})
}


zcBlr = {
		selectUser1:function (){
			var this_ = this;
			//调用用户选择弹窗
			userModal.show({
				dprtcode:$('#dprtcode').val(),
				selected:$("#form #"+"zcBlrid").val(),//原值，在弹窗中默认勾选
				callback: function(data){//回调函数
					 
					$("#form #zcBlrid").val(data.id);
					$("#form #zcBlrmc").val(data.displayName);
					
					$('#form').data('bootstrapValidator')
					.updateStatus('zcBlrmc',
							'NOT_VALIDATED', null)
					.validateField('zcBlrmc');
				}
			}) 
		}
}

zcPzr = {
		selectUser1:function (){
			var this_ = this;
			//调用用户选择弹窗
			userModal.show({
				dprtcode:$('#dprtcode').val(),
				selected:$("#form #"+"zcPzrid").val(),//原值，在弹窗中默认勾选
				callback: function(data){//回调函数
					
					$("#form #zcPzrid").val(data.id);
					$("#form #zcPzrmc").val(data.displayName);
					
					$('#form').data('bootstrapValidator')
					.updateStatus('zcPzrmc',
							'NOT_VALIDATED', null)
					.validateField('zcPzrmc');
				}
			}) 
		}
}