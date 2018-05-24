
$(document)
		.ready(
				function() {
					
					Navigation(menuCode,'新增故障保留单','Add Retain');
					loadFjzch();
					$('.datepicker').datepicker({
						autoclose : true,
						clearBtn : true
					}).next().on("click", function() {
						$(this).prev().focus();
					});

					$('#fjzch').change(function() {
						loadFxjldh();
						initValidate();
						$('#form').data('bootstrapValidator')
						.updateStatus('fxjldh',
								'NOT_VALIDATED', null)
						.validateField('fxjldh');
					});
					$("#fjzch").trigger("change");
					
					/*$('#fxjldh').change(
						function() {
							var jlbym = $("#fxjldh").find("option:selected").attr('jlbym');
							$('#jlbym').val(jlbym);
					});*/
					
				 

					$("#fxjldh").trigger("change");

					$('#scSqrq').datepicker({
						autoclose : true,
						clearBtn : true
					}).on('hide',function(e) {
								$('#form').data('bootstrapValidator')
										.updateStatus('scSqrq',
												'NOT_VALIDATED', null)
										.validateField('scSqrq');
					});

					$('#scDqrq').datepicker({
						autoclose : true,
						clearBtn : true
					}).on('hide',function(e) {
								$('#form').data('bootstrapValidator')
										.updateStatus('scDqrq',
												'NOT_VALIDATED', null)
										.validateField('scDqrq');
					});

					
					function initValidate(){
						$('#form').bootstrapValidator({
							message : '数据不合法',
							feedbackIcons : {
								invalid : 'glyphicon glyphicon-remove',
								validating : 'glyphicon glyphicon-refresh'
							},
							fields : {
								gzbldh : {
									validators : {
										notEmpty : {
											message : '故障保留单号不能为空'
										},
										regexp : {
											regexp : /^[^\u4e00-\u9fa5]{0,20}$/,
											message : '不包含中文且长度不超过20个字符'
										}
									}
								},
								zt : {
									validators : {
										notEmpty : {
											message : '状态不能为空'
										}
									}
								},

								fjzch : {
									validators : {
										notEmpty : {
											message : '飞机注册号不能为空'
										}
									}
								},
								fxjldh : {
									validators : {
										notEmpty : {
											message : '飞行记录单不能为空'
										}
									}
								},
								

								zjhandname : {
									validators : {
										notEmpty : {
											message : 'ATA章节号不能为空'
										}
									}
								},
								gzms : {
									validators : {
										notEmpty : {
											message : '故障描述/措施采取不能为空'
										},
										stringLength : {
											max : 300,
											message : '故障描述/措施采取不能超出300个字符'
										}
									}
								},
								scNr : {
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
								blyj : {
									validators : {
										notEmpty : {
											message : '保留依据不能为空'
										},
										stringLength : {
											max : 300,
											message : '保留依据不能超出300个字符'
										}
									}
								},
								
								scZzh : {
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

								/*scBlrmc : {
									validators : {
										notEmpty : {
											message : '保留人不能为空'
										}
									}
								},*/
								
								/*scPzrmc : {
									validators : {
										notEmpty : {
											message : '批准人不能为空'
										}
									}
								},*/

								scSqrq : {
									validators : {
										notEmpty : {
											message : '申请日期不能为空'
										}
									}
								},
								scDqrq : {
									validators : {
										notEmpty : {
											message : '到期日期不能为空'
										}
									}
								}
								
								
							}
						});
					}

					function loadFxjldh() {
						$("#fxjldh").empty();
						AjaxUtil
								.ajax({
									url : basePath
											+ "/flightdata/flightrecordsheet/queryList",
									type : "post",
									dataType : "json",
									async : false,
									data : JSON.stringify({
										fjzch : $('#fjzch').val()
										,dprtcode:userJgdm
										,valid:'1'
									}),
									contentType : "application/json;charset=utf-8",
									success : function(data) {
										var len = data.length
										if (len > 0) {
											for (var i = 0; i < len; i++) {
												$("#fxjldh").append(
														"<option value='"
																+ StringUtil.escapeStr(data[i].fxjldh) 
																+ "'" 
																+ " jlbym='"+StringUtil.escapeStr(data[i].jlbym)+"'"
																+">"
																+ StringUtil.escapeStr(data[i].fxjldh) 
																+ '/'
																+ StringUtil.escapeStr(data[i].jlbym) 
																+ "</option>");
											}
										}
									}
								});
					}
					
					$('#revert').on('click',function(){
						window.location.href =  basePath+'/flightdata/legacytrouble/manage?pageParam='+pageParam;
					});

				});

/**
 * 实例化附件上传组件
 */ 
var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showStatusAfterError: false,
					showDelete : false,
					maxFileCount : 10,
					formData : {
						"fileType" : "legacytrouble",
						"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					/*statusBarWidth : 150,
					dragdropWidth : 150,*/
					uploadStr:"<div >上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass: "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ data.attachments[0].uuid + '\'>';
						trHtml = trHtml + '<td><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
								+ data.attachments[0].uuid
								+ '\')" title="删除附件">  ';
						trHtml = trHtml + '</div></td>';
						
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\">'
						 + StringUtil.escapeStr(data.attachments[0].wbwjm) 
						 + '</td>';

						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
								+ data.attachments[0].wjdxStr + '</td>';
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
								+ StringUtil.escapeStr(data.attachments[0].user.displayName)  + '</td>';
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
 * 删除附件
 * @param newFileName
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

function committed() {

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
 
	var jlbym = $("#fxjldh").find("option:selected").attr('jlbym');
	$('#jlbym').val(jlbym);
	var data = {
		'blyj':$.trim($('#blyj').val()),
		'dprtcode':userJgdm,
		'gzbldh' : $.trim($('#gzbldh').val()),// 故障保留单号
		'zt' : $.trim($('#zt').val()),// 状态
		'fjzch' : $.trim($('#fjzch').val()),// 飞机注册号					
		'fxjldh' : $.trim($('#fxjldh').val()),// 飞行记录单
		'jlbym' : $.trim($('#jlbym').val()),// 飞行记录单
		'zjh' : $.trim($('#zjh').val()),// ATA章节号

		'isM' : $.trim($("#isM")[0].checked == true ? 1 : 0),// 执行M程序
		'mel' : $.trim($('input:radio:checked').val()),// mel类型
		'gzms' : $.trim($('#gzms').val()),//故障描述/措施采取
		'scNr' : $.trim($('#scNr').val()),//内容
		'scZzh' : $.trim($('#scZzh').val()),//内容

		'scBlrid' : $.trim($('#scBlrid').val()),//保留人
		'scPzrid' : $.trim($('#scPzrid').val()),//批准人
		'scSqrq' : $.trim($('#scSqrq').val()),//首次申请日期
		'dqrq' : $.trim($('#scDqrq').val()),//内容
		'scDqrq' : $.trim($('#scDqrq').val()),//首次到期日期
		'attachments' : attachments
	}
	var body = this;
	// 提交数据
	AjaxUtil.ajax({
		url : basePath + "/flightdata/legacytrouble/addCommitted",
		type : "post",
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			
			if(data.isLegitimate){
				AlertUtil.showMessageCallBack({
					message : "故障保留单提交成功",
					callback : function() {
						//goHistory(body)
						window.location.href =  basePath+'/flightdata/legacytrouble/manage?pageParam='+pageParam;
					}
				});
			}
			else{
				AlertUtil.showErrorMessage("故障保留号已存在，请重新指定。");
				finishWait();
				return false;
			}
			
		}
	});
};

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

	var jlbym = $("#fxjldh").find("option:selected").attr('jlbym');
	$('#jlbym').val(jlbym);
	var data = {
		'blyj':$.trim($('#blyj').val()),
		'dprtcode':userJgdm,
		'gzbldh' : $.trim($('#gzbldh').val()),// 故障保留单号
//		'zt' : $.trim($('#zt').val()),// 状态
		'fjzch' : $.trim($('#fjzch').val()),// 飞机注册号					
		'fxjldh' : $.trim($('#fxjldh').val()),// 飞行记录单
		'jlbym' : $.trim($('#jlbym').val()),// 飞行记录单
		'zjh' : $.trim($('#zjh').val()),// ATA章节号

		'isM' : $.trim($("#isM")[0].checked == true ? 1 : 0),// 执行M程序
		'mel' : $.trim($('input:radio:checked').val()),// mel类型
		'gzms' : $.trim($('#gzms').val()),//故障描述/措施采取
		'scNr' : $.trim($('#scNr').val()),//内容
		'scZzh' : $.trim($('#scZzh').val()),//内容

		'scBlrid' : $.trim($('#scBlrid').val()),//保留人
		'scPzrid' : $.trim($('#scPzrid').val()),//批准人
		'scSqrq' : $.trim($('#scSqrq').val()),//首次申请日期
		'dqrq' : $.trim($('#scDqrq').val()),//到期日期
		'scDqrq' : $.trim($('#scDqrq').val()),//首次到期日期
		'attachments' : attachments
	}

	var body = this;
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url : basePath + "/flightdata/legacytrouble/add",
		type : "post",
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			
			if(data.isLegitimate){
				AlertUtil.showMessageCallBack({
					message : "故障保留单保存成功",
					callback : function() {
						//goHistory(body)
						window.location.href =  basePath+'/flightdata/legacytrouble/manage?pageParam='+pageParam;
					}
				});
			}
			else{
				AlertUtil.showErrorMessage("故障保留号已存在，请重新指定。");
				finishWait();
				return false;
			}
			
		}
	});
};


//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	FixChapterModal.show({
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
			
			$('#form').data('bootstrapValidator')
			.updateStatus('zjhandname',
					'NOT_VALIDATED', null)
			.validateField('zjhandname');
		}
	})
}

scBlr = {
		selectUser1:function (){
			var this_ = this;
			//调用用户选择弹窗
			userModal.show({
				selected:$("#form #"+"scBlrid").val(),//原值，在弹窗中默认勾选
				callback: function(data){//回调函数
					$("#form #"+"scBlrid").val(data.id);
					$("#form #"+"scBlrmc").val(data.displayName);
				}
			}) 
		}
}

scPzr = {
		selectUser1:function (){
			var this_ = this;
			//调用用户选择弹窗
			userModal.show({
				selected:$("#form #"+"scPzrid").val(),//原值，在弹窗中默认勾选
				callback: function(data){//回调函数
					$("#form #"+"scPzrid").val(data.id);
					$("#form #"+"scPzrmc").val(data.displayName);
				}
			}) 
		}
}

function loadFjzch() {
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:userJgdm});
 	var planeRegOption = '';
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH) +"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
		});
	}
	$("#fjzch").html(planeRegOption); 
}

