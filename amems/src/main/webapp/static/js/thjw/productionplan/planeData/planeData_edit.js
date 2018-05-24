//service for workdetail.jsp
	var delAttachements = [];

	$(function(){
		// 初始化导航栏
		var type = $("#operationType").val();
		if(type == 'view'){
			Navigation(menuCode,'查看','View');
		}else{
			Navigation(menuCode,'修改','Edit');
			TimeUtil.addTimeValidate("input[name='init_time_jsfx']");
			TimeUtil.addTimeValidate("input[name='init_time_ssd']");
			TimeUtil.addTimeValidate("input[name='init_time_jc']");
		}
		// 初始化时间控件
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			 $('#planeForm').data('bootstrapValidator')  
		     .updateStatus('gjdjzh_yxq', 'NOT_VALIDATED',null)  
		     .validateField('gjdjzh_yxq');
		    $('#planeForm').data('bootstrapValidator')  
		     .updateStatus('shzh_yxq', 'NOT_VALIDATED',null)  
		     .validateField('shzh_yxq');  
		   $('#planeForm').data('bootstrapValidator')  
		     .updateStatus('wxdtxkzh_yxq', 'NOT_VALIDATED',null)  
		     .validateField('wxdtxkzh_yxq');  
	     });
		// 加载电台执照附件
		if($("#gjdjzfjidinput").val()!=''){
			
		}
		loadDtzzfj([$("#gjdjzfjidinput").val(), $("#shzfjidinput").val(),$("#wxdtzzfjidinput").val()]);
//		loadDtzzfj($("#shzfjidinput").val(),2);
//		loadDtzzfj($("#wxdtzzfjidinput").val(),3);
		var validatorForm =  $('#planeForm').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	fjzch: {
	                validators: {
	                    notEmpty: {
	                        message: '飞机注册号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: '飞机注册号不能含有中文'
	                    }
	                }
	            },
	            fjjx: {
	                validators: {
	                    notEmpty: {
	                        message: '飞机机型不能为空'
	                    }
	                }
	            },
	            xlh: {
	                validators: {
	                    notEmpty: {
	                        message: '序列号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: '序列号不能含有中文'
	                    }
	                }
	            },
	            jd: {
	                validators: {
	                    notEmpty: {
	                        message: '基地不能为空'
	                    }
	                }
	            },
	            gjdjzh: {
		            validators: {
	                	regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: '国籍登记证号不能含有中文'
	                    },
	                	callback: {
	                        message: '必须和国籍登记日期同时填写',
	                        callback: function(value, validator) {
	                        	validator.revalidateField($("#gjdjzh_yxq"));
	                			var wxdtxkzh = $("#gjdjzh").val();
	                			var dtzzjkrq = $("#gjdjzh_yxq").val();
	                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
	                				validator.updateStatus('gjdjzh_yxq', 'INVALID', 'callback');  
	                				return false;
	                			}
	                			validator.updateStatus('gjdjzh_yxq', 'VALID', 'callback');  
	                            return true;
	                        }
	                    }
	                }
	            },
	            shzh: {
		            validators: {
	                	regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]*$/,
	                        message: '适航证号不能含有中文'
	                    },
	                	callback: {
	                        message: '必须和适航证号日期同时填写',
	                        callback: function(value, validator) {
	                        	validator.revalidateField($("#shzh_yxq"));
	                			var wxdtxkzh = $("#shzh").val();
	                			var dtzzjkrq = $("#shzh_yxq").val();
	                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
	                				validator.updateStatus('shzh_yxq', 'INVALID', 'callback');  
	                				return false;
	                			}
	                			validator.updateStatus('shzh_yxq', 'VALID', 'callback');  
	                            return true;
	                        }
	                    }
	                }
	            },
	            wxdtxkzh: {
	            	 validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]*$/,
		                        message: '无线电台许可证号不能含有中文'
		                    },
		                	callback: {
		                        message: '必须和电台执照监控日期同时填写',
		                        callback: function(value, validator) {
		                        	validator.revalidateField($("#wxdtxkzh_yxq"));
		                			var wxdtxkzh = $("#wxdtxkzh").val();
		                			var dtzzjkrq = $("#wxdtxkzh_yxq").val();
		                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
		                				validator.updateStatus('wxdtxkzh_yxq', 'INVALID', 'callback');  
		                				return false;
		                			}
		                			validator.updateStatus('wxdtxkzh_yxq', 'VALID', 'callback');  
		                            return true;
		                        }
		                    }
		                }
	            },
	            gjdjzh_yxq: {
	            	  validators: {
		                	callback: {
		                        message: '必须和国籍登记日期同时填写',
		                        callback: function(value, validator) {
		                			var wxdtxkzh = $("#gjdjzh").val();
		                			var dtzzjkrq = $("#gjdjzh_yxq").val();
		                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
		                				validator.updateStatus('gjdjzh', 'INVALID', 'callback');  
		                				return false;
		                			}
		                			validator.updateStatus('gjdjzh', 'VALID', 'callback');  
		                            return true;
		                        }
		                    }
		                }
	            
	            },
	            shzh_yxq: {
	            	 validators: {
		                	callback: {
		                        message: '必须和适航证号日期同时填写',
		                        callback: function(value, validator) {
		                			var wxdtxkzh = $("#shzh").val();
		                			var dtzzjkrq = $("#shzh_yxq").val();
		                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
		                				validator.updateStatus('shzh', 'INVALID', 'callback');  
		                				return false;
		                			}
		                			validator.updateStatus('shzh', 'VALID', 'callback');  
		                            return true;
		                        }
		                    }
		                }
	            },
	            wxdtxkzh_yxq: {
	            	 validators: {
		                	callback: {
		                        message: '必须和电台执照监控日期同时填写',
		                        callback: function(value, validator) {
		                			var wxdtxkzh = $("#wxdtxkzh").val();
		                			var dtzzjkrq = $("#wxdtxkzh_yxq").val();
		                			if((wxdtxkzh && !dtzzjkrq || (!wxdtxkzh && dtzzjkrq))){
		                				validator.updateStatus('wxdtxkzh', 'INVALID', 'callback');  
		                				return false;
		                			}
		                			validator.updateStatus('wxdtxkzh', 'VALID', 'callback');  
		                            return true;
		                        }
		                    }
		                }
	            },
	            init_loop_qlj: {
	                validators: {
		                regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '起落架循环格式不正确'
		                }
	                }
	            },
	            init_loop_jc: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '绞车循环格式不正确'
		                }
	                }
	            },
	            init_loop_wdg: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '外吊挂循环格式不正确'
		                }
	                }
	            },
/*	            init_loop_ssd: {
	                validators: {
	                	integer: {
		                    message: '搜索灯循环必须为整数'
		                },
		                greaterThan:{
		                	value:0,
		                	message: '搜索灯循环必须大于0'
		                }
	                }
	            },*/
	            init_loop_l_n1: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N1循环格式不正确'
		                }
	                }
	            },
	            init_loop_l_n2: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N2循环格式不正确'
		                }
	                }
	            },
	            init_loop_l_n3: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N3循环格式不正确'
		                }
	                }
	            },
	            init_loop_l_n4: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N4循环格式不正确'
		                }
	                }
	            },
	            init_loop_l_n5: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N5循环格式不正确'
		                }
	                }
	            },
	            init_loop_l_n6: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机左发N6循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n1: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N1循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n2: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N2循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n3: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N3循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n4: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N4循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n5: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N5循环格式不正确'
		                }
	                }
	            },
	            init_loop_r_n6: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: '发动机右发N6循环格式不正确'
		                }
	                }
	            },
	            init_loop_ts1: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: 'TS1格式不正确'
		                }
	                }
	            },
	            init_loop_ts2: {
	                validators: {
	                	regexp: {
		                    regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
		                    message: 'TS2格式不正确'
		                }
	                }
	            }
	        }
	    });
	});
	
	//上传
	var uploadObj = $("#fileuploader").uploadFile(
			{
				url : basePath + "/common/ajaxUploadFile",
				multiple : true,
				dragDrop : false,
				showStatusAfterSuccess : false,
				showDelete : false,
				maxFileCount : 100,
				formData : {
					"fileType" : "fjzch",
					"wbwjm" : function() {
						return $('#gjdjzh_fj').val();
					}
				},
				fileName : "myfile",
				returnType : "json",
				removeAfterUpload : true,
				uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
				uploadButtonClass: "ajax-file-upload_ext",
				onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
				{    data.attachments[0].type=1;
					var trHtml = '<tr  id=\''+data.attachments[0].uuid+'\'>';
					 trHtml = trHtml+'<td><div>';
					 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
					 trHtml = trHtml+'</div></td>';
					 trHtml = trHtml+'<td class="text-left">国籍登记证号<input type="hidden" name="type" value="1" /></td>';
					 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
					 
					 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
					 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
					 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
					 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
					 
					 trHtml = trHtml+'</tr>';	 
					 $('#filelist>.non-choice').remove();
					 $('#filelist').append(trHtml);
				}
				//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
				,onSubmit : function (files, xhr) {
					var wbwjm  = $.trim($('#gjdjzh_fj').val());
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
	//上传
	var uploadObjWorthiness = $("#fileuploaderairworthiness").uploadFile(
			{
				
				url : basePath + "/common/ajaxUploadFile",
				multiple : true,
				dragDrop : false,
				showStatusAfterSuccess : false,
				showDelete : false,
				maxFileCount : 100,
				formData : {
					"fileType" : "fjzch",
					"wbwjm" : function() {
						return $('#shzh_fj').val();
					}
				},
				fileName : "myfile",
				returnType : "json",
				removeAfterUpload : true,
				uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
				uploadButtonClass: "ajax-file-upload_ext",
				onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
				{   data.attachments[0].type=2;
					var trHtml = '<tr id=\''+data.attachments[0].uuid+'\'>';
					 trHtml = trHtml+'<td><div>';
					 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
					 trHtml = trHtml+'</div></td>';
					 trHtml = trHtml+'<td class="text-left">适航证号</td>';
					 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
					 
					 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
					 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
					 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
					 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
					 
					 trHtml = trHtml+'</tr>';	 
					 $('#filelist>.non-choice').remove();
					 $('#filelist').append(trHtml);
				}
				//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
				,onSubmit : function (files, xhr) {
					var wbwjm  = $.trim($('#shzh_fj').val());
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
	//上传
	var uploadObjWireless = $("#fileuploaderwireless").uploadFile(
			{
				
				url : basePath + "/common/ajaxUploadFile",
				multiple : true,
				dragDrop : false,
				showStatusAfterSuccess : false,
				showDelete : false,
				maxFileCount : 100,
				formData : {
					"fileType" : "fjzch",
					"wbwjm" : function() {
						return $('#wxdtxkzh_fj').val();
					}
				},
				fileName : "myfile",
				returnType : "json",
				removeAfterUpload : true,
				uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
				uploadButtonClass: "ajax-file-upload_ext",
				onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
				{
					data.attachments[0].type=3;
					var trHtml = '<tr  id=\''+data.attachments[0].uuid+'\'>';
					 trHtml = trHtml+'<td><div>';
					 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
					 trHtml = trHtml+'</div></td>';
					 trHtml = trHtml+'<td class="text-left">无线电台许可证号</td>';
					 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
					 
					 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
					 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
					 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
					 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
					 
					 trHtml = trHtml+'</tr>';	 
					 $('#filelist>.non-choice').remove();
					 $('#filelist').append(trHtml);
				}
				//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
				,onSubmit : function (files, xhr) {
					var wbwjm  = $.trim($('#wxdtxkzh_fj').val());
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
	 * 加载电台执照附件
	 * @param id
	 */
	function loadDtzzfj(ids) {
		var strs = ["国籍登记证号","适航证号","无线电台许可证号"];
		AjaxUtil.ajax({
					url : basePath + "/common/loadPlaneDataAttachmentsByMainIds",
					type : "post",
					data : {
						idList: ids
					},
					success : function(data) {
						if (data.success) {
							var attachments = data.attachments;
							var len = data.attachments.length;
							if (len > 0) {
								var trHtml = '';
								for (var i = 0; i < len; i++) {
									trHtml = trHtml
											+ '<tr  id=\''
											+ attachments[i].uuid + '\' key=\''
											+ attachments[i].id + '\' >';
									trHtml = trHtml + '<td><div>';
									trHtml = trHtml
											+ '<i class="icon-trash color-blue cursor-pointer" onclick="deletefile(\''
											+ attachments[i].uuid
											+ '\')" title="删除 Delete">  ';
									trHtml = trHtml + '</div></td>';
									trHtml = trHtml + '<td class="text-left">'+strs[ids.indexOf(attachments[i].mainid)]+'</td>';
									trHtml = trHtml
											+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'"> <a class="cursor-pointer" onclick="downloadfile(\''
											+ attachments[i].id + '\')" >'
											+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a></td>';
									trHtml = trHtml + '<td class="text-left">'
											+ attachments[i].wjdxStr + ' </td>';
									trHtml = trHtml + '<td class="text-left"  title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
											+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
									trHtml = trHtml + '<td>' + attachments[i].czsj
											+ '</td>';
									trHtml = trHtml
											+ '<input type="hidden" name="relativePath" value=\''
											+ attachments[i].relativePath + '\'/>';
									trHtml = trHtml + '</tr>';
								}
								$('#filelist').append(trHtml);
							}
							if($("#operationType").val() == "view"){
								$("table tr th:nth-child(1)").remove();
								$("table tr td:nth-child(1)").remove();
								if($("#filelist>tr").length == 0 ){
									$("#filelist").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
								}
							}else{
								if($("#filelist>tr").length == 0){
									$("#filelist").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
								}
							}
						}
					}
				});
	}
	
	/**
	* 删除附件
	* @param newFileName
	*/
	function delfile(uuid) {
		var  responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		var waitDelArray = [];
		if(len > 0 ) {
			for(var i =0;i<len;i++){
				if(responses[i].attachments[0].uuid != uuid){
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
			$('#'+uuid).remove();
			if($("#filelist>tr").length == 0){
				$("#filelist").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
			}
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
		if($("#filelist>tr").length == 0){
			$("#filelist").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
		}
	}
	
	function save(){
		$('#planeForm').data('bootstrapValidator').validate();
		  if(!$('#planeForm').data('bootstrapValidator').isValid()){
			return false;
		  }
		var params = assemblePlaneInitData();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/planeData/edit",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(fjzch){
				
				AlertUtil.showMessage("提交成功!",'/productionplan/planeData/main?fjzch='+encodeURIComponent(fjzch)+'&dprtcode='+$("#dprtcode").val()+'&pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 拼接飞机初始化的表单数据
	 * @param formParam
	 */
	function assemblePlaneInitData(){
		var params = {
				dprtcode:$.trim($("#dprtcode").val()),
				fjzch : $.trim($("#fjzch").val()),
				fjjx : $.trim($("#fjjx").val()),
				xlh : $.trim($("#xlh").val()),
				bzm : $.trim($("#bzm").val()),
				ccrq : $.trim($("#ccrq").val()),
				jd : $.trim($("#jd").val()),
				jsgzjl : $.trim($("#jsgzjl").val()),
				bz : $.trim($("#bz").val()),
				gjdjzh:$.trim($("#gjdjzh").val()),
				gjdjzjkrq:$.trim($("#gjdjzh_yxq").val()),
				shzh:$.trim($("#shzh").val()),
				shzjkrq:$.trim($("#shzh_yxq").val()),
				wxdtxkzh:$.trim($("#wxdtxkzh").val()),
				dtzzjkrq:$.trim($("#wxdtxkzh_yxq").val()),
				gjdjzfjid:$.trim($("#gjdjzfjidinput").val()),
				shzfjid:$.trim($("#shzfjidinput").val()),
				wxdtzzfjid:$.trim($("#wxdtzzfjidinput").val()),
				/*gjdjzh : $.trim($("#gjdjzh").val()),*/
				/*shzh : $.trim($("#shzh").val()),*/
				/*wxdtxkzh : $.trim($("#wxdtxkzh").val()),*/
				/*dtzzjkrq : $.trim($("#dtzzjkrq").val()),*/
				initDatas : [],
				dtzzfjs : []
		};
		$.each($("[name^='init_']"), function(i, obj){
			var initData = {
					fjzch : params.fjzch,
					initXmbh : obj.name,
					initValue : obj.value,
					jsbs : 0
			};
			if(initData.initValue != "" && initData.initValue != ":"){
				params.initDatas.push(initData);
			}
		 });
		
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var responsesWorthiness = uploadObjWorthiness.responses;
		var lenWorthiness = uploadObjWorthiness.responses.length;
		var responsesWireless = uploadObjWireless.responses;
		var lenWireless = uploadObjWireless.responses.length;
		if(len != undefined && len>0){
			for(var i =0;i<len;i++){
				params.dtzzfjs.push({
							'yswjm':responses[i].attachments[0].yswjm
							,'wbwjm':responses[i].attachments[0].wbwjm
							,'nbwjm':responses[i].attachments[0].nbwjm
							,'cflj':responses[i].attachments[0].cflj
							,'wjdx':responses[i].attachments[0].wjdx
							,'hzm':responses[i].attachments[0].nbwjm.split(".")[1]
				            ,'type':responses[i].attachments[0].type
						});
			}
		}
		if(lenWorthiness != undefined && lenWorthiness>0){
			for(var i =0;i<lenWorthiness;i++){
				
				params.dtzzfjs.push({
							'yswjm':responsesWorthiness[i].attachments[0].yswjm
							,'wbwjm':responsesWorthiness[i].attachments[0].wbwjm
							,'nbwjm':responsesWorthiness[i].attachments[0].nbwjm
							,'cflj':responsesWorthiness[i].attachments[0].cflj
							,'wjdx':responsesWorthiness[i].attachments[0].wjdx
							,'hzm':responsesWorthiness[i].attachments[0].nbwjm.split(".")[1]
				            ,'type':responsesWorthiness[i].attachments[0].type
						});
			}
		}
		if(lenWireless != undefined && lenWireless>0){
			for(var i =0;i<lenWireless;i++){
				params.dtzzfjs.push({
							'yswjm':responsesWireless[i].attachments[0].yswjm
							,'wbwjm':responsesWireless[i].attachments[0].wbwjm
							,'nbwjm':responsesWireless[i].attachments[0].nbwjm
							,'cflj':responsesWireless[i].attachments[0].cflj
							,'wjdx':responsesWireless[i].attachments[0].wjdx
							,'hzm':responsesWireless[i].attachments[0].nbwjm.split(".")[1]
							,'type':responsesWireless[i].attachments[0].type
						});
			}
		}
		var dellen = delAttachements.length;
		if (dellen != undefined && dellen > 0) {
			for (var i = 0; i < dellen; i++) {
				params.dtzzfjs.push({
					'id' : delAttachements[i].id,
					'operate' : 'DEL'
				});
			}
		}
		return params;
	}
	
	/**
	 * 跳转到主页面
	 */
	function goToMainPage(){
		window.location = basePath+"/productionplan/planeData/main?pageParam="+pageParam;
	}
	
	/**
	 * 下载附件
	 */
	function downloadfile(id) {
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
	
 
