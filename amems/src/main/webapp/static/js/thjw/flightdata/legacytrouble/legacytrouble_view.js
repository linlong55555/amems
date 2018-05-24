$(document)
		.ready(
				function() {
					
					Navigation(menuCode,'查看故障保留单','See Fault')
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

							zcBlrid : {
								validators : {
									notEmpty : {
										message : '保留人不能为空'
									}
								}
							},
							zcPzrid : {
								validators : {
									notEmpty : {
										message : '批准人不能为空'
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
															+ attachments[i].uuid
															+ '\'>';
												 
													/*trHtml = trHtml
															+ '<td>'
															+ attachments[i].wbwjm
															+ '</td>';

													trHtml = trHtml
															+ '<td>'
															+ attachments[i].wjdxStr
															+ ' </td>';
													trHtml = trHtml
															+ '<td>'
															+ attachments[i].czrname
															+ '</td>';
													trHtml = trHtml
															+ '<td>'
															+ attachments[i].czsj
															+ '</td>';
													trHtml = trHtml
															+ '<input type="hidden" name="relativePath" value=\''
															+ attachments[i].relativePath
															+ '\'/>';
													trHtml = trHtml + '</tr>';*/
													
													trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\">'
													+'<a href="#" onclick="downloadfile(\''
													+ attachments[i].id
													+ '\')">' + StringUtil.escapeStr(attachments[i].wbwjm) 
													+ '</a></td>';
													
													trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
													+ attachments[i].wjdxStr + '</td>';
													trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
															+ StringUtil.escapeStr(attachments[i].czrname)   + '</td>';
													trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-13\">' + attachments[i].czsj
															+ '</td>';
													trHtml = trHtml
															+ '<input type="hidden" name="relativePath" value=\''
															+ attachments[i].relativePath + '\'/>';
												}

												$('#filelist').append(trHtml);
											}
										}
									}
								});
					}

					$('#revert').on('click',function(){
						window.location.href =  basePath+'/flightdata/legacytrouble/manage?&pageParam='+pageParam;
					});
					
					//初始化日志功能
					logModal.init({code:'B_S_013',id:$('#id').val()});
				});

/**
 * 下载附件
 * @param newFileName
 */
function downloadfile(id) {
	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

 
