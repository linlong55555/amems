	var ajaxCount = 0;
	var delAttachements = [];
	var planes;
	Navigation(menuCode);
	var hdMap = {
			1 : "航前",
			3 : "航间1",
			5 : "航间2",
			7 : "航间3",
			99 : "航后"
	}
	// 所有数据
	var totalData = {
			// 飞行数据
			flightData : [],
			// 完成工作
			finishedWork : [],
			// 根据id获取对应的完成工作
			getFinishedWork : function(rowId){
				var result = null;
				$.each(totalData.finishedWork, function(i, obj){
					if(rowId == obj.rowId){
						result = obj;
						return false;
					}
				});
				return result;
			},
			// 根据id设置完成工作
			setFinishedWork : function(rowId, finishedWork){
				for (var i = 0; i < totalData.finishedWork.length; i++) {
		            if (totalData.finishedWork[i].rowId == rowId) {
		            	totalData.finishedWork[i] = finishedWork;
		            	return false;
		            }
		        }
			},
			// 根据id获取对应的拆解记录
			getDismountRecord : function(subRowId){
				var rowId = subRowId.split("_")[0];
				var result = null;
				$.each(totalData.getFinishedWork(rowId).dismountRecord, function(i, obj){
					if(subRowId == obj.subRowId){
						result = obj;
						return false;
					}
				});
				return result;
			},
			// 根据id删除对应的拆解记录
			deleteDismountRecord : function(subRowId){
				var rowId = subRowId.split("_")[0];
				var dismountRecord = totalData.getFinishedWork(rowId).dismountRecord;
				for (var i = 0; i < dismountRecord.length; i++) {
		            if (dismountRecord[i].subRowId == subRowId) {
		            	dismountRecord.splice(i, 1);
		            }
		        }
			},
			// 删除完成工作
			deleteFinishedWork : function(rowId){
				for (var i = 0; i < totalData.finishedWork.length; i++) {
		            if (totalData.finishedWork[i].rowId == rowId) {
		            	totalData.finishedWork.splice(i, 1);
		            }
		        }
			},
			// 获取监控项目
			getMonitorItem : function(rowId, djbh){
				var result = null;
				var fixedMonitor = totalData.getDismountRecord(rowId).fixedMonitor || [];
				$.each(fixedMonitor, function(i, obj){
					if (obj.djbh == djbh) {
		            	result = obj;
		            	return false;
		            }
				});
				return result;
			},
			// 设置监控项目
			setMonitorItem : function(rowId, monitorItem){
				var fixedMonitor = totalData.getDismountRecord(rowId).fixedMonitor;
				var exist = false;
				for (var i = 0; i < fixedMonitor.length; i++) {
		            if (fixedMonitor[i].djbh == monitorItem.djbh) {
		            	fixedMonitor[i] = monitorItem;
		            	exist = true;
		            	return false;
		            }
		        }
				if(!exist){
					fixedMonitor.push(monitorItem);
				}
			},
			// 删除监控项目
			deleteFixedMonitor : function(rowId, djbh){
				var fixedMonitor = totalData.getDismountRecord(rowId).fixedMonitor;
				for (var i = 0; i < fixedMonitor.length; i++) {
		            if (fixedMonitor[i].djbh == djbh) {
		            	fixedMonitor.splice(i, 1);
		            }
		        }
			}
	};
	
	/**
	 * 一级部件对应的件号序列号
	 */
	var levelOneComponent = {
			1 : {
				jh : '',
				xlh : ''
			},
			2 : {
				jh : '',
				xlh : ''
			},
			3 : {
				jh : '',
				xlh : ''
			},
			4 : {
				jh : '',
				xlh : ''
			},
			5 : {
				jh : '',
				xlh : ''
			}
	}
	
	// 时间格式
	var timeArr = ['fxsj','f1sj','f2sj','jcsj','ssdsj'];
	var loopArr = ["qljxh","jcxh","f1n1","f1n2","f1n3","f1n4","f1n5","f1n6"
	                ,"f2n1","f2n2","f2n3","f2n4","f2n5","f2n6","dgxh","ts1","ts2"
	                ,'ylfxq','ylfxh','f1hy','f1wdyd','f1glyd','f2hy','f2wdyd','f2glyd'
	                ,'mgb','igb','tgb'];

	$(function(){
		initPlanes();
		initSubTable();
		initFlightrecordsheetValidate();
		// 切换页面显示类型
		switchType();
		// 初始化时间控件
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
		$('input.date-range-picker').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
		 });
		
		$("#fxrq").on('hide', function(e) {
			  $('#flightRecordSheetForm').data('bootstrapValidator')  
			     .updateStatus('fxrq', 'NOT_VALIDATED',null)  
			     .validateField('fxrq');  
			  refreshLegacyTrouble();
	    })
		
		//刷新飞行前数据
		refreshPreflightData();
		
		// 绑定输入框重新计算
		$("#flightDataList input").blur(function(){
			var cloumnName = $(this).parent().attr("name");
			if(cloumnName != "ylfxq" && cloumnName != "ylfxh" && cloumnName != "f1hy" && cloumnName != "f1wdyd"
				&& cloumnName != "f1glyd" && cloumnName != "f2hy" && cloumnName != "f2wdyd" && cloumnName != "f2glyd"
				&& cloumnName != "mgb" && cloumnName != "igb" && cloumnName != "tgb" && cloumnName != "avFxr"
				&& cloumnName != "avZzh" && cloumnName != "meFxr" && cloumnName != "meZzh" && cloumnName != "jzshrk"){
				recalcPreflightData(cloumnName);
			}
		});
		
		// 时间格式验证
		$(timeArr).each(function(){
			$("#flightDataList td[name='"+this+"']>input").keyup(function(){
				backspace($(this),/^[0-9]+((\:)?[0-5]?[0-9]?)?$/);
			});
			$("#flightDataList td[name='"+this+"']>input").blur(function(){
				backspace($(this),/^[0-9]+((\:)?[0-5]?[0-9]?)?$/);
			});
		});
		
		// 循环格式验证
		$(loopArr).each(function(){
			$("#flightDataTable td[name='"+this+"']>input,th[name='"+this+"']>input").keyup(function(){
				backspace($(this),/^[0-9]+(\.?[0-9]{0,2})?$/);
			});
			$("#flightDataTable td[name='"+this+"']>input,th[name='"+this+"']>input").blur(function(){
				backspace($(this),/^[0-9]+(\.?[0-9]{0,2})?$/);
			});
		});
		
		// 飞行数据自动补零
		var totalArr = timeArr.concat(loopArr);
		$(totalArr).each(function(){
			var jklbh = this+"";
			$("#flightDataTable td[name='"+this+"']>input,th[name='"+this+"']>input").blur(function(){
				var val = $(this).val();
				if(val){
					// 包含分隔符:或.
					if(val.indexOf(".") != -1 || val.indexOf(":") != -1){
						// 分隔符在最后，补两个零
						if(val.indexOf(".") == val.length - 1 || val.indexOf(":") == val.length - 1){
							$(this).val(val+"00");
							$(this).blur();
						}
						// 只有一个小于点后一位，补一个零
						else if(val.indexOf(".") == val.length - 2 || val.indexOf(":") == val.length - 2){
							if($.inArray(jklbh, loopArr) != -1){
								$(this).val(val+"0");
							}else if($.inArray(jklbh, timeArr) != -1){
								var hour = val.split(":")[0];
								var minute = val.split(":")[1];
								$(this).val(hour+":0"+minute);
							}
						}
					}
					// 不含分隔符，按照类型添加分隔符并补零
					else{
						if($.inArray(jklbh, loopArr) != -1){
							$(this).val(val+".00");
						}else if($.inArray(jklbh, timeArr) != -1){
							$(this).val(val+":00");
						}
					}
				}
			});
		});
		
		// 非中文字符验证
		var nonCnArr = ['avZzh','meZzh'];
		$(nonCnArr).each(function(){
			$("#flightDataTable td[name='"+this+"']>input,th[name='"+this+"']>input").keyup(function(){
				backspace($(this),/^[^\u4e00-\u9fa5]*$/);
			});
			$("#flightDataTable td[name='"+this+"']>input,th[name='"+this+"']>input").blur(function(){
				backspace($(this),/^[^\u4e00-\u9fa5]*$/);
			});
		});
		$("#finishedWorkModal_gzxmbldh,#disassemblyComponent_cxdh,#hbh,#finishedWorkModal_rwdh").live("keyup",function(){
			backspace($(this),/^[^\u4e00-\u9fa5]*$/);
	    });
		$("#finishedWorkModal_gzxmbldh,#disassemblyComponent_cxdh,#hbh,#finishedWorkModal_rwdh").live("blur",function(){
			backspace($(this),/^[^\u4e00-\u9fa5]*$/);
	    });
		
		// 当前列高亮
		$("#flightDataTable td,th").hover(
			function(){
				var columeName = $(this).attr("name");
				$("#flightDataTable td[name='"+columeName+"'],th[name='"+columeName+"'],th[children*='"+columeName+"']").addClass("warning");
     	   },function(){
     		  $("#flightDataTable td,th").removeClass("warning");
     	   });
		
		
		/**
		 * 完成工作 添加行选中事件
		 */
		$("#associateWorkModalList tr").live("click",function(event){
			 // 避免复选框重复选择
			 if($(event.target).attr("type") == "checkbox"){
				 return;
			 }
			 var checkbox = $(this).find(":checkbox");
             if(checkbox.attr("checked") == "checked"){
            	 checkbox.removeAttr("checked");
             } else {
            	 checkbox.attr("checked","checked");
             }
	    });
		
		/**
		 * 双击编辑完成工作
		 */
		$('#finishedWorkList>tr[name="finishedWork"]').live("dblclick", function(event){
			showFinishedWorkModal($(this), event);
		});
		
		/**
		 * 故障保留单展开/收缩拆装记录
		 */
		$("#DDF_title").click(function(){
			$("#DDF_div").toggle();
			$("#DDF_badge").html($("#DDF_badge").text() == "+" ? "&nbsp;-" : "+");
		});
		/**
		 * 附件上传展开/收缩拆装记录
		 */
		$("#fileUpload_title").click(function(){
			$("#FileUploadDiv").toggle();
			$("#fileUpload_badge").html($("#fileUpload_badge").text() == "+" ? "&nbsp;-" : "+");
		});
		// 刷新故障保留单
		refreshLegacyTrouble();
		// 初始化分页
		initPage();
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
				"fileType" : "flightrecordsheet",
				"wbwjm" : function() {
					return $('#wbwjm').val();
				}
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,
			uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
			uploadButtonClass: "ajax-file-upload_ext",
			onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
			{
				var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
				 trHtml = trHtml+'<td><div>';
				 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
				 trHtml = trHtml+'</div></td>';
				 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
				 
				 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
				 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
				 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
				 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
				 
				 trHtml = trHtml+'</tr>';	 
				 $('#filelist>.non-choice').remove();
				 $('#filelist').append(trHtml);
				 $('#fileUpload_count').text(parseInt($('#fileUpload_count').text())+1);
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
	
	
	/**
	* 删除附件
	* @param newFileName
	*/
	function delfile(uuid) {
		var key = $('#' + uuid).attr('key');
		$('#' + uuid).remove();
		$('#fileUpload_count').text(parseInt($('#fileUpload_count').text())-1);
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
		} else {
			delAttachements.push({
				id : key
			});
		}
		if($("#filelist>tr").length == 0){
			$("#filelist").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		}
	}
	
	/**
	 * 加载附件
	 * @param id
	 */
	function loadAttachments(id) {
		AjaxUtil.ajax({
					url : basePath + "/common/loadAttachments",
					type : "post",
					data : {
						mainid : id
					},
					success : function(data) {
						if (data.success) {
							var attachments = data.attachments;
							var len = data.attachments.length;
							if (len > 0) {
								$('#filelist>tr').remove();
								$('#fileUpload_count').text(len);
								var trHtml = '';
								for (var i = 0; i < len; i++) {
									trHtml = trHtml
											+ '<tr bgcolor="#f9f9f9" id=\''
											+ attachments[i].uuid + '\' key=\''
											+ attachments[i].id + '\' >';
									trHtml = trHtml + '<td><div>';
									trHtml = trHtml
											+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
											+ attachments[i].uuid
											+ '\')" title="删除 Delete">  ';
									trHtml = trHtml + '</div></td>';
									trHtml = trHtml
											+ '<td class="text-left"> <a class="cursor-pointer" onclick="downloadfile(\''
											+ attachments[i].id + '\')" >'
											+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a></td>';
									trHtml = trHtml + '<td class="text-left">'
											+ attachments[i].wjdxStr + ' </td>';
									trHtml = trHtml + '<td class="text-left">'
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
							if($("#forwordType").val() == 3){
								$("#FileUploadDiv .form-group").remove();
								$("#filelist").parent().find("tr th:nth-child(1)").remove();
								$("#filelist tr td:nth-child(1)").remove();
								$('#filelist>.non-choice').remove();
								if($("#filelist>tr").length == 0){
									$("#filelist").append('<tr class="non-choice"><td colspan="4">暂无数据 No data.</td></tr>');
								}
							}
						}
					}
				});
	}
	
	/**
	 * 下载附件
	 */
	function downloadfile(id) {
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
	
	/**
	 * 初始化时控件验证
	 */
	function initFlightrecordsheetValidate(){
		if($("#flightRecordSheetForm").data('bootstrapValidator')){
			$("#flightRecordSheetForm").data('bootstrapValidator').destroy();
			$('#flightRecordSheetForm').data('bootstrapValidator', null);
		}
		var validatorForm =  $('#flightRecordSheetForm').bootstrapValidator({
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
	                    }
	                }
	            },
	        	jlbym: {
	            	validators: {
		                notEmpty: {
	                        message: '记录本页码不能为空'
	                    }
	                }
	            },
	            fxrq: {
	            	validators: {
		                notEmpty: {
	                        message: '飞行日期不能为空'
	                    }
	                }
	            },
	        }
	    });
	}
	
	/**
	 * 刷新飞行前数据
	 */
	function refreshPreflightData(){
		var param = {
			fjzch : $("#fjzch").val() ,
			fxjldh : $("#fxjldh").html(),
			id : $("#frsId").val(),
			dprtcode : $("#dprtcode").val()
		};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/flightrecordsheet/loadPreflightData",
		   type: "post",
		   data:JSON.stringify(param),
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   success:function(data){
		    finishWait();
		    fillPreflightData(data.flightrecords);
	      }
	    }); 
	}
	
	/**
	 * 切换飞机
	 */
	function changePlane(){
		ajaxCount = 0;
		removePreflightData();
		refreshPreflightData();
		refreshLegacyTrouble();
	}
	
	/**
	 * 移除原有的飞行前数据
	 */
	function removePreflightData(){
		
		// 是否显示特殊情况
		var isTsqk = $("#fjzch option:selected").attr("istsqk");
	    if(isTsqk && isTsqk == 1){
	    	$("#flightDataTable [name='tsqk']").removeClass("hidden");
	    }else {
	    	$("#flightDataTable [name='tsqk']").addClass("hidden");
	    }
	    
	    // 移除原有的飞行前数据和输入框数据
	    $("#flightDataTable tbody td[name!='title'] small").html("&nbsp;");
	    $("#flightDataTable input").val("");
	    $("#flightDataTable select").val("");
	    $("#flightDataTable span").text("");
	    
	    $("#hc2").hide();
	    $("#hc3").hide();
	    $("#hc4").hide();
	    
	    // 删除已完成工作
		$("#finishedWorkList").empty();
		$("#finishedWorkList").append("<tr class='non-choice'> <td colspan='11'>请先选择完成工作.Please choose to finish work first.</td> </tr>");
		
		// 删除total数据
		totalData.flightData = [];
		totalData.finishedWork = [];
	}
	
	/**
	 * 填充飞行前数据
	 * @param data
	 */
	function fillPreflightData(data){
		$("#fjxlh").val($("#fjzch option:selected").attr("xlh"));
		
		$.each(data, function (i, rec){
			var rowNum = parseInt(rec.hc)/2-1;
			var tr = $("#flightDataList tr:eq("+rowNum+")");
			
			// 记录飞机的一级部件
			if(rec.hc == 2){
				recordLevelOneComponent(rec.preflightDatas);
			}
			
			// 随部件走的数据
			$.each(rec.preflightDatas, function (i2, preflightData){
				var td = tr.find("td[name='"+preflightData.item+"']");
				td.find("small[name='jh']").html(preflightData.jh||"&nbsp;");
				td.find("small[name='xlh']").html(preflightData.xlh||"无");
				var pre = preflightData.value;
				if(preflightData.item != "f1sj" && preflightData.item != "f2sj" 
					&& preflightData.item != "jcsj" && preflightData.item != "ssdsj"){
					pre = (parseFloat(pre||"0")).toFixed(2);
				}else{
					pre = pre||"0:00";
				}
				td.find("small[name='pre']").text(pre);
			});
			
			tr.find("td").each(function(){
				var td = $(this);
				var type = td.attr("name");
				if(type == "f1sj" || type == "f2sj" || type == "jcsj" || type == "ssdsj" || type == "dgxh" ){
					var xlh = td.find("small[name='xlh']");
					xlh.html(xlh.html().replace("&nbsp;","无"));
				}
				if(type == "f1sj" || type == "f2sj" || type == "jcsj" || type == "ssdsj" || type == "dgxh" 
					|| type == "fxsj" || type == "qljxh" || type == "jcxh" || type == "f1n1" || type == "f1n2"
					|| type == "f1n3" || type == "f1n4" || type == "f1n5" || type == "f1n6" || type == "f2n1"
					|| type == "f2n2" || type == "f2n3" || type == "f2n4" || type == "f2n5" || type == "f2n6"
					|| type == "ssdsj" || type == "dgxh"){
					var pre = td.find("small[name='pre']");
					pre.html(pre.html().replace("&nbsp;","0.00"));
				}
			});
			
			//随飞机走的数据
			tr.find("td[name='fxsj'] small[name='pre']").text(rec.fxsj||"0:00");
			tr.find("td[name='qljxh'] small[name='pre']").text(parseFloat(rec.qljxh||0).toFixed(2));
			tr.find("td[name='ts1'] small[name='pre']").text(parseFloat(rec.ts1||0).toFixed(2));
			tr.find("td[name='ts2'] small[name='pre']").text(parseFloat(rec.ts2||0).toFixed(2));
			
		});
		ajaxCount++;
		handleRecalc();
	}
	
	/**
	 * 记录飞机的一级部件
	 */
	function recordLevelOneComponent(preflightDatas){
		levelOneComponent = {
				1 : {
					jh : '',
					xlh : ''
				},
				2 : {
					jh : '',
					xlh : ''
				},
				3 : {
					jh : '',
					xlh : ''
				},
				4 : {
					jh : '',
					xlh : ''
				},
				5 : {
					jh : '',
					xlh : ''
				}
		};
		
		$.each(preflightDatas, function (i, preflightData){
			if(preflightData.item == 'f1sj'){
				levelOneComponent[1].jh = preflightData.jh||'';
				levelOneComponent[1].xlh = preflightData.xlh||'';
			}else if(preflightData.item == 'f2sj'){
				levelOneComponent[2].jh = preflightData.jh||'';
				levelOneComponent[2].xlh = preflightData.xlh||'';
			}else if(preflightData.item == 'jcsj'){
				levelOneComponent[3].jh = preflightData.jh||'';
				levelOneComponent[3].xlh = preflightData.xlh||'';
			}else if(preflightData.item == 'ssdsj'){
				levelOneComponent[4].jh = preflightData.jh||'';
				levelOneComponent[4].xlh = preflightData.xlh||'';
			}else if(preflightData.item == 'dgxh'){
				levelOneComponent[5].jh = preflightData.jh||'';
				levelOneComponent[5].xlh = preflightData.xlh||'';
			}
		});
	}
	
	/**
	 * 计算当前的一级部件
	 * @param wz
	 */
	function calcCurrentLevelOneComponent(wz){
		if(wz == 0){
			return false;
		}
		// 航次一的件号、序列号
		var jh = levelOneComponent[wz].jh;
		var xlh = levelOneComponent[wz].xlh;
		// 获取所有完成工作
		var finishedWork = $("#finishedWorkList>tr[name='finishedWork']");
		// 按照航段排序
		finishedWork.sort(function(a, b){
			var hd_a = $(a).find("select[name='hd']").val();
			var hd_b = $(b).find("select[name='hd']").val();
			return parseInt(hd_a) - parseInt(hd_b);
		});
		// 该飞行记录单无该位置的拆解记录，部件还原为初始值
		if(jh && xlh){
			replaceComponent(wz, 1, jh, xlh, "on");
		}else{
			replaceComponent(wz, 1, jh, xlh, "off");
		}
		finishedWork.each(function(i, work){
			var hd = $(work).find("select[name='hd']").val();
			// 循环拆解记录
			$("#finishedWorkList>tr>td>table>tbody>tr[id^='"+$(work).attr("id")+"']").each(function(i2, cjjl_){
				var cjjl = $(cjjl_);
				var dismountRecord = totalData.getDismountRecord(cjjl.attr("id"));
				var cxWz = dismountRecord.cxWz;
				var zsWz = dismountRecord.on.wz;
				var cj_cjjl = dismountRecord.cxCj || dismountRecord.on.cj || '';
				var wz_cjjl = cxWz || zsWz || '';
				if(cj_cjjl == 1 && wz_cjjl == wz){
					if(cxWz){
						jh = cjjl.find("td:eq(2)").html();
						xlh = cjjl.find("td:eq(4)").html();
						replaceComponent(wz, hd, jh, xlh, "off");
					}
					if(zsWz){
						jh = cjjl.find("td:eq(8)").html();
						xlh = cjjl.find("td:eq(10)").html();
						replaceComponent(wz, hd, jh, xlh, "on");
					}
				}
			});
		});
	}
	
	/**
	 * 计算所有位置的当前一级部件
	 */
	function calcCurrentLevelOneComponentAll(){
		calcCurrentLevelOneComponent("1");
		calcCurrentLevelOneComponent("2");
		calcCurrentLevelOneComponent("3");
		calcCurrentLevelOneComponent("4");
		calcCurrentLevelOneComponent("5");
	}
	
	/**
	 * 重新计算飞行前数据
	 * @param columnName 需要重新计算列的name
	 */
	function recalcPreflightData(columnName, isCalcAdjust){
		for(var i = 0; i < 4 ; i++){
			// 当前选中的td
			var td = $("#flightDataList tr:eq("+i+") td[name='"+columnName+"']");
			// 当前行的飞行前数据
			var pre = $.trim(td.find("small[name='pre']").text())||0;
			// 当前行的输入框值
			var val = td.find("input").val()||0;
			// 当前行的序列号
			var id = td.find("small[name='xlh']").text();
			
			// 下一行的对应td
			var nextTd = $("#flightDataList tr:eq("+(i+1)+") td[name='"+columnName+"']");
			// 下一行的序列号
			var nextId = nextTd.find("small[name='xlh']").text();
			
			// 件号和序列号没有发生改变
			if(id == nextId){
				// 刷新下一行的飞行前数据
				nextTd.find("small[name='pre']").text(add(pre, val, columnName));
			}
			if(i == 3){
				// 计算调整值
				if(isCalcAdjust){
					var adjust = $("#adjust"+columnName.toUpperCase( )).val() || "0";
					val = add(adjust, val, columnName);
				}
				// 累计总数
				var sumInput = $("#flightDataTable tfoot tr th[name='"+columnName+"'] input");
				sumInput.val(add(pre, val, columnName));
				var sumSpan = $("#flightDataTable tfoot tr th[name='"+columnName+"'] span");
				sumSpan.text(add(pre, val, columnName));
			}
		}
	}
	
	/**
	 * 重新计算所有的飞行前数据
	 */
	function recalcAllPreflightData(isCalcAdjust){
		var clacList = ["fxsj","f1sj","f2sj","qljxh","jcsj","jcxh","f1n1","f1n2","f1n3","f1n4","f1n5","f1n6"
		                ,"f2n1","f2n2","f2n3","f2n4","f2n5","f2n6","ssdsj","dgxh","ts1","ts2"];
		$.each(clacList, function(){
			recalcPreflightData(this, isCalcAdjust);
		});
	}
	
	/**
	 * 计算累计总数差异
	 */
	function calcDifference(columnName){
		// 最后一行的td
		var td =  $("#flightDataList tr:last td[name='"+columnName+"']");
		// 当前行的飞行前数据
		var pre = $.trim(td.find("small[name='pre']").text())||0;
		// 当前行的输入框值
		var val = td.find("input").val()||0;
		// 理论累计
		var theorySum = add(pre, val, columnName);
		// 实际累计
		var realSum = $("#flightDataTable tfoot tr th[name='"+columnName+"'] input").val()||0;
		// 返回差异
		return substract(realSum, theorySum, columnName);
	}
	
	/**
	 * 是否有差异
	 * @param columnName
	 */
	function hasDifference(columnName){
		var result = calcDifference(columnName);
		if(!result || result == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 加法
	 * @param param1 参数1
	 * @param param2 参数2
	 * @param columnName 列名
	 * @returns
	 */
	function add(param1, param2, columnName){
		// 时间计算
		if(columnName.indexOf("sj") != -1){
			return TimeUtil.operateTime(param1, param2, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
		} 
		// 循环计算
		else {
			var param1_float = parseFloat(param1);
			var param2_float = parseFloat(param2);
			param1_float = isNaN(param1_float) ? 0 : param1_float;
			param2_float = isNaN(param2_float) ? 0 : param2_float;
			return (param1_float + param2_float).toFixed(2);
		}
	}
	
	/**
	 * 减法
	 * @param param1
	 * @param param2
	 * @param columnName
	 */
	function substract(param1, param2, columnName){
		// 时间计算
		if(columnName.indexOf("sj") != -1){
			return TimeUtil.operateTime(param1, param2, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
		} 
		// 循环计算
		else {
			return (parseFloat(param1)-parseFloat(param2)).toFixed(2);
		}
	}
	
	/**
	 * 添加行
	 */
	function addRow(){
		var id = $("#flightDataList tr:visible:last").attr("id");
		if(id == "hc4"){
			// 最多4个航次
			AlertUtil.showErrorMessage("最多只能添加4个航次！");
		}else {
			var newRowNum = parseInt(id.substring(2))+1;
			$("#hc1 td:first").attr("rowspan",newRowNum);
			// 显示航次
			$("#hc" + newRowNum).show();
			// 动态显示检查人
			$("#hc" + (newRowNum-1) + "_jcr").show();
			// 修改航段
			$("select[name='hd']").each(function(){
				var select = $(this);
				var val = select.val();
				select.html(buildHdOption());
				// 保持所选择的航段不变
				select.val(val);
			});
		}
	}
	
	/**
	 * 删除行
	 */
	function deleteRow(){
		var id = $("#flightDataList tr:visible:last").attr("id");
		
		// 验证是否选择了该航次
		var deleteNum = parseInt(id.substring(2));
		var flag = false;
		$("select[name='hd']").each(function(){
			var val = parseInt($(this).val());
			if(deleteNum + 1 == val){
				flag = true;
				return false;
			}
		});
		if(flag){
			AlertUtil.showErrorMessage("已有完成工作选择该航段，无法删除！");
			return false;
		}
		
		if(id == "hc1"){
			// 最少1个航次
			AlertUtil.showErrorMessage("最少保留1个航次！");
		}else {
			$("#hc1 td:first").attr("rowspan",deleteNum - 1);
			// 隐藏航次
			$("#hc" + deleteNum).hide();
			$("#hc" + deleteNum + " >td>input:not([disabled])").val("");
			recalcAllPreflightData();
			// 隐藏检查人
			$("#hc" + (deleteNum - 1) + "_jcr").hide();
			$("#hc" + (deleteNum - 1) + "_jcr").val("");
			// 修改航段
			$("select[name='hd']").each(function(){
				var select = $(this);
				var val = select.val();
				select.html(buildHdOption());
				// 保持所选择的航段不变
				select.val(val);
			});
		}
	}
	
	/**
	 * 跳转至指定页数
	 * @param pageNumber	当前页码
	 * @param sortType	排序字段
	 * @param sequence	排序规则 
	 */
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}
	
	/**
	 * 显示关联工作modal
	 */
	function showAssociateWorkModal(){
		$("#associateWorkModal").modal("show");
		goPage(1,"rwdh","asc");
	}
	
	/**
	 * 更改完成工作任务类型
	 */
	function changeAssociateWorkType(){
		goPage(1,"rwdh","asc");
	}
	
	
	/**
	  * 带条件搜索的翻页
	  */
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		
		var searchParam = {
				fjzch : $("#fjzch").val(),
				zt : 2,
				dprtcode : $("#dprtcode").val(),
				isLoadedParts : $("#associateWorkType").val(),
				keyword : $("#associateWorkKeyword").val(),
				xszt : $("#associateWorkStatus").val()
			
		};
		var idNotEquals = [];
		$("#finishedWorkList>tr[id]>input[name='rwdid']").each(function(){
			idNotEquals.push($(this).val());
		});
		if(idNotEquals.length > 0){
			searchParam.idNotEquals = idNotEquals;
		}
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		//obj.id = '1'';//搜索条件
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/flightrecordsheet/queryAssociateWork",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   if(data.total >0){
				   appendContentHtml(data.rows, searchParam.keyword);
				   
				   new Pagination({
						renderTo : "mpagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   
				   FormatUtil.removeNullOrUndefined("#associateWorkModalList tr td");
				   signByKeyword($("#associateWorkKeyword").val(),[3,4],"#associateWorkModalList tr td");
			   } else {
				  $("#associateWorkModalList").empty();
				  $("#mpagination").empty();
				  $("#associateWorkModalList").append("<tr><td colspan=\"6\">暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }
	 

	 
	 
	 /**
	  * 拼接table数据
	  * @param list
	  */
	 function appendContentHtml(list, keyword){
		 $("#associateWorkModalList").empty();
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent += ["<tr style='cursor:pointer' bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
			                   "<td><input value='"+row.id+"' type='checkbox'></td>",
			                   "<td>"+(index+1)+"</td>",
			                   "<td style='text-align: left'>"+row.rwdh+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.rwxx)+"'>"+row.rwxx+"</td>",
			                   "<td style='text-align: left'>"+getTaskTypeDescription(row.rwlx, row.rwzlx)+"</td>",
			                   "<input type='hidden' name='rwlx' value='"+row.rwlx+"'>",
			                   "<input type='hidden' name='rwzlx' value='"+row.rwzlx+"'>",
			                   "<input type='hidden' name='xgdjid' value='"+row.xggdid+"'>",
			                   "</tr>"
			                   ].join("");
		   });
		   $("#associateWorkModalList").html(htmlContent);
		 
	 }
	 
	 /**
	  * 获取计划类型描述
	  * @param rwlx
	  * @param rwzlx
	  * @returns {String}
	  */
	 function getTaskTypeDescription(rwlx, rwzlx){
		 if(rwlx == 1){
			 return "定检执行任务";
		 }else if(rwlx == 2){
			 if(rwzlx == 1){
				 return "非例行-时控件工单";
			 }else if(rwzlx == 2){
				 return "非例行-附加工单";
			 }else if(rwzlx == 3){
				 return "非例行-排故工单";
			 }
		 }else if(rwlx == 3){
			 return "EO工单任务";
		 }
	 }
	 
	 
	//只能输入数字和小数
	 function clearNoNum(obj){
	      //先把非数字的都替换掉，除了数字和.
	      obj.value = obj.value.replace(/[^\d.]/g,"");
	      //必须保证第一个为数字而不是.
	      obj.value = obj.value.replace(/^\./g,"");
	      //保证只有出现一个.而没有多个.
	      obj.value = obj.value.replace(/\.{2,}/g,".");
	      //保证.只出现一次，而不能出现两次以上
	      obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	      //最多2位小数
	      if(/^\d+(\.\d{3,})$/.test(obj.value)){
	    	  obj.value = Math.floor(parseFloat(obj.value) * 100) / 100;
	      }
	      
	      var td = $(obj).parent();
	      var personNum = td.find("input:first").val();
	      var timeNum = td.find("input:last").val();
	      var result = (parseFloat(personNum||0)*parseFloat(timeNum||0)).toFixed(2);
	      td.find("span[name='totalTime']").text(result);
	 }
	 
	 /**
	  * 拼装航段
	  * @returns {String}
	  */
	 function buildHdOption(hcNum){
		 var rowNum = parseInt($("#flightDataList tr:visible:last").attr("id").substring(2))-1;
		 var hcOption = '<option value="1">航前</option>';
		 for(var i = 0; i < (hcNum || rowNum); i++){
			 hcOption += '<option value="'+(2*i+3)+'">航间'+(i+1)+'</option>';
		 }
		 hcOption += '<option value="99">航后</option>';
		 return hcOption;
	 }
	 
	 /**
	  * 添加modal的关联任务到完成工作中
	  */
	 function addAssociateWork(list){
		 var selects = $("#associateWorkModalList input:checked");
		 if(selects.length == 0){
			 AlertUtil.showErrorMessage("请至少选择一个完成工作！");
			 return false;
		 }
		 $("#finishedWorkList tr.non-choice").remove();
		 var num = $("#finishedWorkList tr").length;
		 $.each(selects, function(i, select){
			 var tr = $(select).parent().parent();
			 var id = $(select).val();
			 var rwdh = tr.find("td:eq(2)").html();
			 var rwxx = tr.find("td:eq(3)").html();
			 var rwlx = tr.find("td:eq(4)").html();
			 var no = num + i + 1;
			 
			 // 加入到数据集合中
			 totalData.finishedWork.push({
				 rowId : id,
				 dismountRecord : []
			 });
			 buildFinishedWork({
				 id : id,
				 rwlxms : rwlx,
				 rwdh : rwdh,
				 whnr : rwxx,
				 rwlx : tr.find("input[name='rwlx']").val(),
				 rwzlx : tr.find("input[name='rwzlx']").val(),
				 xgdjid : tr.find("input[name='xgdjid']").val()
			 });
		 });
		 
		 $("#associateWorkModal").modal("hide");
	 }
	 
	 function buildFinishedWork(data, dataAll){
		 $("#finishedWorkList").append([
                        '<tr id="'+data.id+'" style="cursor:pointer;-moz-user-select:none;" name="finishedWork" onselectstart="return false;">',
                        '<td style="vertical-align:middle;line-height:30px;">',
                        '<button type="button" class="btn btn-default btn-xs" onclick="toggleDismountRecord(this)" title="展开/收缩 Collapse/Expand">',
                        '<i class="icon-double-angle-down cursor-pointer color-default cursor-pointer"></i>',
                        '</button>&nbsp;&nbsp;',
						'<button type="button" class="btn btn-default btn-xs" onclick="deleteAssociateWork(\''+data.id+'\')" title="删除 Delete">',
						'<i class="icon-minus cursor-pointer color-default cursor-pointer"></i>',
						'</button>&nbsp;&nbsp;',
						'<button type="button" class="btn btn-default btn-xs notHide" onclick="showAttachment(\''+data.id+'\',\''+(data.rwdid||data.id)+'\')" title="附件 Attachment" style="width:23px;">',
						'<i class="icon-file cursor-pointer color-default cursor-pointer"></i>',
						'<b class="badge danger-bg bounceIn animated" name="attachmentCount">0</b>',
						'</button>',
                        '</td>',
                        '<td style="text-align:left;vertical-align:middle;" title="'+data.rwlxms+'" name="rwlxDes">'+data.rwlxms+'</td>',
                        '<td style="text-align:left;vertical-align:middle;" title="'+(data.rwdh||"")+'" name="rwdh">'+(data.rwdh||"")+'</td>',
                        '<td style="text-align:left;vertical-align:middle;" title="'+(data.whnr||'')+'" name="rwxx">'+(data.whnr||"")+'</td>',
                        '<td style="vertical-align:middle;" title="航前">',
                        '<select class="form-control input-sm" name="hd" style="display:none;">',
                        buildHdOption(dataAll ? dataAll.flightData.length-1 : null),
                        '</select>',
                        '<span name="hdDes">航前</span>',
                        '</td>',
                        '<td name="gzxx" style="text-align:left;vertical-align:middle;"></td>',
                        '<td name="clcs" style="text-align:left;vertical-align:middle;"></td>',
                        '<td style="vertical-align:middle;">',
                        '<input name="sjgsRs" type="hidden"/>',
                        '<input name="sjgsXss" type="hidden"/>',
                        '<span name="sjgsDes" style="float:left;"></span>',
                        '</td>',
                        '<td name="gzxmbldh" style="text-align:left;vertical-align:middle;"></td>',
                        '<td style="text-align:left;vertical-align:middle;">',
                        '<span name="zrr"></span>',
                        '<input type="hidden" class="form-control " name="zrrid" value=""/>',
                       /* '<a href="javascript:void(0);" onclick="showUserList(\''+data.id+'\')" class="btn btn-primary btn-sm padding-top-4 padding-bottom-4 pull-left" style="float: left; margin-left: 10px;" disabled="disabled">',
						'<i class="icon-search cursor-pointer" ></i>',
						'</a>',*/
						'</td>',
                        '<input type="hidden" name="rwdid" value="'+(data.rwdid||data.id)+'"/>',
                        '<input type="hidden" name="rwlx" value="'+data.rwlx+'"/>',
                        '<input type="hidden" name="rwzlx" value="'+data.rwzlx+'"/>',
                        '<input type="hidden" name="xgdjid" value="'+(data.xgdjid||"")+'"/>',
                        '<input type="hidden" name="fwid" value="'+(data.realId||"")+'"/>',
                        '</tr>'].join(""));
	 }
	 
	 /**
	  * 删除完成工作
	  * @param id
	  */
	 function deleteAssociateWork(rowId){
		 $("#"+rowId).remove();
		 $("#finishedWorkList>tr[parent='finishedWorkTable_"+rowId+"']").remove();
		 totalData.deleteFinishedWork(rowId);
		 if($("#finishedWorkList>tr").length == 0){
			 $("#finishedWorkList").append("<tr class='non-choice'> <td colspan='11'>请先选择完成工作.Please choose to finish work first.</td> </tr>");
		 }
		 calcCurrentLevelOneComponentAll();
	 }
	 
	 /**
	  * 选择责任人
	  * @param obj
	  */
	 function showUserList(){
		 //$("#hid_current_row").val(id);
		 var id = $("#editFinishedWorkModal_rowId").val();
		 var this_ = this;
			//调用用户选择弹窗
			userModal.show({
				selected:$("#"+id+" input[name='zrrid']").val(),//原值，在弹窗中默认勾选
				callback: function(data){//回调函数
					$("#finishedWorkModal_zrrid").val(data.id);
					$("#finishedWorkModal_zrr").val(data.displayName);
				},
				dprtcode:$("#dprtcode").val()
			})
	 }
	 
	 function initSubTable(){
		 var config = {
				 tableId : "finishedWorkTable",
				 title : "拆装记录",
				 dynamicAdd : true,
				 afterAddRow : afterAddRow,
				 afterDeleteRow : afterDeleteRow,
				 customColumnContent : buildCustomColumnContent(),
				 subColumeNames_cn : ["操作","件号","名称","序列号","安装位置","拆下单号","操作","件号","名称","序列号","安装位置","数量","父节点","时控件监控","定检监控"],
				 subColumeNames_eng : ["Operation","P/N",'Name',"S/N","Location",'Record No',"Operation","P/N","Name","S/N",'Location',"Quantity","Parent Node","Monitor","Monitor"],
				 rowOperationWidth : "5%",
				 subColumeWidth : ["4%","6%","6%","6%","6%","6%","4%","6%","6%","6%","6%","5%","6%","7%","6%"],
				 subColumeNames_secord_cn : ["拆下件信息","装上件信息"],
				 subColumeNames_secord_eng : ["Off","On"],
				 subColumeNames_secord_colspan : [6 , 9]
		 };
		 SubTable.setConfig(config);
	 }
	 
	 /**
	  * 构建自定义的子表单内容
	  * @returns
	  */
	 function buildCustomColumnContent(){
		 return	 ['<td>',
		       	  '<i class="icon-plus color-blue cursor-pointer" onclick="searchDisassemblyComponentModal(this)" title="拆下件放大镜 Choose Disassembly Part"></i>&nbsp;&nbsp;',
		       	  '<input type="hidden" name="hidden_disassemblyComponent_id">',
		       	  '<i class="icon-remove color-blue cursor-pointer" onclick="deleteDisassemblyComponent(this)" title="删除 Delete"></i>',
		       	  '</td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"><span name="cxjldh"></span></td>',
				  '<input type="hidden" name="loadingList_disassemblyComponent">',
				  '<td>',
				  '<i class="icon-plus color-blue cursor-pointer" onclick="searchMountComponentModal(this)" title="装上件放大镜 Choose Assembly Part"></i>&nbsp;&nbsp;',
				  '<i class="icon-remove color-blue cursor-pointer" onclick="deleteMountComponent(this)" title="删除 Delete"></i>',
				  '</td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:left"></td>',
				  '<td style="text-align:right"></td>',
				  '<td style="text-align:left"></td>',
				  '<td>',
				  '<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showTimeMonitorModal(this)"><u>待配置</u></a>',
				  '</td>',
				  '<td>',
				  '<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showFixedMonitorModal(this)"><u>待配置</u></a>',
				  '</td>',
				  '<input type="hidden" name="cjid">',
				  ].join('');
	 }
	 
	 function afterAddRow(rowId, subRowId){
		 totalData.getFinishedWork(rowId).dismountRecord.push({
			 subRowId : subRowId,
			 on : {},
			 cxZjqdid : "",
			 timeMonitor : {},
			 fixedMonitor : [],
			 fjzch : $("#fjzch").val()
		 });
	 }
	 
	 function afterDeleteRow(subRowId){
		 var record = totalData.getDismountRecord(subRowId);
		 var wz = record.cxWz || record.on.wz;
		 totalData.deleteDismountRecord(subRowId);
		 if(wz){
			 calcCurrentLevelOneComponent(wz);
		 }
	 }
	 
	 
	 /**
	  * 回填装机清单数据
	  */
	 function setLoadingList(){
		var select = $("#disassemblyComponentList").find("input:checked");
		// 验证是否选择拆下件
		if(select.length == 0){
			AlertUtil.showErrorMessage("请选择拆下件！");
			return false;
		}
		var datarow = select.parent().parent();
		var id = $("#hidden_disassemblyComponent").val();
		var targetRow = $("#"+id);
		// 验证拆下件是否在此次飞行记录单之后的航段有装上的二级件
		for(var i = 0; i < totalData.finishedWork.length; i++){
			var dismountRecord = totalData.finishedWork[i].dismountRecord;
			var hd_fjd = $("#"+id.split("_")[0]+">td>select[name='hd']").val()||"0";
			var hd_zjd = $("#"+totalData.finishedWork[i].rowId+">td>select[name='hd']").val()||"0";
			if(parseInt(hd_zjd) >= parseInt(hd_fjd)){
				for(var j = 0; j < dismountRecord.length; j++){
					var on = dismountRecord[j].on;
					if(on && on.fjdid == select.val()){
						AlertUtil.showErrorMessage("无法拆下该部件！如想继续，请先修改本飞行记录单下件号为："+on.jh+"序列号为："+(on.xlh||"")+"的父节点。");
						return false;
					}
				}
			}
		}
		
		// 图标
		targetRow.find("td:eq(1)>i.icon-plus").removeClass("icon-plus").addClass("icon-edit");
		// 件号
		targetRow.find("td:eq(2)").html(datarow.find("td:eq(5)").html());
		targetRow.find("td:eq(2)").attr("title",datarow.find("td:eq(5)").text());
		// 名称
		targetRow.find("td:eq(3)").html(datarow.find("td:eq(3)").html());
		targetRow.find("td:eq(3)").attr("title",datarow.find("td:eq(3)").text());
		// 序列号
		targetRow.find("td:eq(4)").html(datarow.find("td:eq(6)").html());
		targetRow.find("td:eq(4)").attr("title",datarow.find("td:eq(6)").text());
		// 安装位置
		targetRow.find("td:eq(5)").html(datarow.find("td:eq(11)").html());
		targetRow.find("td:eq(5)").attr("title",datarow.find("td:eq(11)").text());
		// 拆下记录单号
		targetRow.find("span[name='cxjldh']").text($("#disassemblyComponent_cxdh").val());
		targetRow.find("span[name='cxjldh']").attr("title",$("#disassemblyComponent_cxdh").val());
		// 加入到数据集合中
		var data = totalData.getDismountRecord(id);
		var beforeWz = data.cxWz;
		var beforeCj = data.cxCj;
		data.cxZjqdid = select.val();
		data.cxWz = datarow.find("input[name='wz']").val();
		data.cxFjdid = datarow.find("input[name='fjdid']").val();
		data.cxCj = datarow.find("input[name='cj']").val();
		data.cxjldh = $("#disassemblyComponent_cxdh").val();
		data.cxBz = $("#disassemblyComponent_cxbz").val();
		
		// 继承拆下件的子部件
		if(!data.on.children){
			var children = [];
			AjaxUtil.ajax({
				url:basePath+"/productionplan/loadingList/queryList",
				type:"post",
				data:JSON.stringify({
					fjdid : data.cxZjqdid || "-",
					fjzch : $("#fjzch").val()
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data2){
					$.each(data2, function(){
						children.push({
							zbjZjqdid : this.id
						});
					});
					data.on.children = children;
				}
			});
		}
		
		// 刷新飞行前数据
		if(data.cxCj == 1){
			calcCurrentLevelOneComponent(data.cxWz);
		}
		if(beforeCj == 1){
			calcCurrentLevelOneComponent(beforeWz);
		}
		$("#disassemblyComponentModal").modal("hide");
	}
	 
	/**
	 * 删除拆下的装机清单数据
	 * @param btn
	 */
	function deleteDisassemblyComponent(btn){
		
		var tr = $(btn).parent().parent();
		tr.find("td:gt(0)>i.icon-edit").removeClass("icon-edit").addClass("icon-plus");
		tr.find("td:gt(1):lt(4)").empty();
		tr.find("td:gt(1):lt(4)").attr("title","");
		tr.find("span[name='cxjldh']").text("");
		 // 加入到数据集合中
		var data = totalData.getDismountRecord(tr.attr("id"));
		var wz = data.cxWz;
		data.cxZjqdid = "";
		data.cxWz = "";
		data.cxFjdid = "";
		data.cxCj = "";
		data.cxjldh = "";
		data.cxBz = "";
		if(wz){
			calcCurrentLevelOneComponent(wz);
		}
	}
	
	/**
	 * 飞行记录单验证
	 */
	function validate(type){
		// 表单验证
		$('#flightRecordSheetForm').data('bootstrapValidator').validate();
		if(!$('#flightRecordSheetForm').data('bootstrapValidator').isValid()){
			return false;
		}
		// 航次验证
		if(!validateFlightData($("#flightDataList > tr:visible"))){
			AlertUtil.showErrorMessage("请以此添加航次！");
			return false;
		}
		
		// 验证时控件定检件是否配置
		for(var i = 0; i < totalData.finishedWork.length; i++){
			var dismountRecord = totalData.finishedWork[i].dismountRecord;
			for(var j = 0; j < dismountRecord.length; j++){
				var on = dismountRecord[j].on;
				var settings = dismountRecord[j].timeMonitor.settings;
				if((on.kzlx == 1 || on.kzlx == 2) && (typeof(settings) == "undefined" || settings.length == 0)){
					AlertUtil.showErrorMessage("部件"+on.jh+"的时控件设置没有填写！");
					return false;
				}
				if(on.isDj == 1 && dismountRecord[j].fixedMonitor.length == 0){
					AlertUtil.showErrorMessage("部件"+on.jh+"的定检件设置没有填写！");
					return false;
				}
			}
		}
		
		// 同一件号、序列号同一飞行记录单只能装上或拆下一次
		var arr = [];
		$("#finishedWorkList>tr>td>table>tbody>tr").each(function(){
			var tr = $(this);
			var cxJh = tr.find("td:eq(2)").html();
			var cxXlh = tr.find("td:eq(4)").html();
			if(cxJh && cxXlh){
				arr.push(cxJh+"_"+cxXlh);
			}
			var zsJh = tr.find("td:eq(8)").html();
			var zsXlh = tr.find("td:eq(10)").html();
			if(zsJh && zsXlh){
				arr.push(zsJh+"_"+zsXlh);
			}
		});
		if(isRepeat(arr)){
			AlertUtil.showErrorMessage("同一件号、序列号同一飞行记录单只能装上或拆下一次！");
			return false;
		}
		
		// 新增时验证
		var result = true;
		if(!$("#frsId").val()){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/flightdata/flightrecordsheet/validate",
				type:"post",
				data:JSON.stringify({
					fjzch : $("#fjzch").val(),
					dprtcode : $("#dprtcode").val(),
					jlbym : $("#jlbym").val(),
					fxrq : $("#fxrq").val(),
					paramsMap : {notId:$("#frsId").val()},
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data){
					if(!data.success) {
						if(type==1){
							$("#continueBtn").attr("onclick","saveTemp('1')");
						}else if(type==2){
							$("#continueBtn").attr("onclick","takeEffect('1')");
						}
						if(data.type == 1 || data.type == 2){
							$("#validateModal_weak").modal("show");
							$("#validateModalBody_weak").html(data.message);
						}else if (data.type == 3){
							$("#validateModal_strong").modal("show");
							$("#validateModalBody_strong").html(data.message);
						}
						result =  false;
					}
				}
			});
		}
		return result;
		
		/**
		 * 验证飞行前数据
		 */
		function validateFlightData(rows){
			var result = true;
			rows.each(function(i, row){
				row = $(row);
				if(!hasValue(row) && hasValue(row.next())){
					result = false;
					return false;
				}
			});
			return result;
		}
		
		/**
		 * 判断tr中input是否只是有一个值
		 */
		function hasValue(tr){
			var result = false;
			tr.find("input").each(function(){
				if($(this).val() != ""){
					result = true;
					return false;
				}
			});
			return result;
		}
	}
	
	/**
	 * 保存草稿
	 */
	function saveTemp(isSkip){
		// 验证
		if(isSkip != '1' && !validate(1)){
			return false;
		}
		startWait();
		// 添加飞行数据到total数据中
		gatherFlightData();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/flightdata/flightrecordsheet/save",
			type:"post",
			data:JSON.stringify(totalData),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				finishWait();
				$("#frsId").val(data.id);
				$("#previousPage").attr("pageid",data.previousPage||"");
				$("#nextPage").attr("pageid",data.nextPage||"");
				$("#forwordType").val(2);
				uploadObj.responses = [];
				uploadObj.selectedFiles = 0;
				changePlane();
				ajaxGetTotalData();
				initPage();
				AlertUtil.showMessage("保存飞行记录单成功!");
			}
		});
	}
	
	/**
	 * 添加飞行数据到total数据中
	 */
	function gatherFlightData(){
		var flightData = [];
		var rows = $("#flightDataList > tr:visible");
		if($("#frsId").val()){
			totalData.id = $("#frsId").val();
		}
		totalData.dprtcode = $("#dprtcode").val();
		totalData.fjzch = $("#fjzch").val();
		totalData.jlbym = $("#jlbym").val();
		totalData.fxrq = $("#fxrq").val();
		totalData.hbh = $("#hbh").val();
		// 添加航次数据
		$.each(rows, function(i, row){
			row = $(row);
			var fxsj = row.find("td[name='fxsj']>input").val();
			var f1sj = row.find("td[name='f1sj']>input").val();
			var f2sj = row.find("td[name='f2sj']>input").val();
			var jcsj = row.find("td[name='jcsj']>input").val();
			var ssdsj = row.find("td[name='ssdsj']>input").val();
			var data = {
					hc : row.attr("hc"),
					hcms : row.attr("hcms"),
					fxsjXs : TimeUtil.getHour(fxsj)||0,
					fxsjFz : TimeUtil.getMinute(fxsj)||0,
					f1sjXs : TimeUtil.getHour(f1sj)||0,
					f1sjFz : TimeUtil.getMinute(f1sj)||0,
					f2sjXs : TimeUtil.getHour(f2sj)||0,
					f2sjFz : TimeUtil.getMinute(f2sj)||0,
					qljxh : row.find("td[name='qljxh']>input").val()||0,
					jcsjXs : TimeUtil.getHour(jcsj)||0,
					jcsjFz : TimeUtil.getMinute(jcsj)||0,
					jcxh : row.find("td[name='jcxh']>input").val()||0,
					ylFxq : row.find("td[name='ylfxq']>input").val()||0,
					ylFxh : row.find("td[name='ylfxh']>input").val()||0,
					f1N1 : row.find("td[name='f1n1']>input").val()||0,
					f1N2 : row.find("td[name='f1n2']>input").val()||0,
					f1N3 : row.find("td[name='f1n3']>input").val()||0,
					f1N4 : row.find("td[name='f1n4']>input").val()||0,
					f1N5 : row.find("td[name='f1n5']>input").val()||0,
					f1N6 : row.find("td[name='f1n6']>input").val()||0,
					f1Hy : row.find("td[name='f1hy']>input").val()||0,
					f1Wdyd : row.find("td[name='f1wdyd']>input").val()||0,
					f1Glyd : row.find("td[name='f1glyd']>input").val()||0,
					f2N1 : row.find("td[name='f2n1']>input").val()||0,
					f2N2 : row.find("td[name='f2n2']>input").val()||0,
					f2N3 : row.find("td[name='f2n3']>input").val()||0,
					f2N4 : row.find("td[name='f2n4']>input").val()||0,
					f2N5 : row.find("td[name='f2n5']>input").val()||0,
					f2N6 : row.find("td[name='f2n6']>input").val()||0,
					f2Hy : row.find("td[name='f2hy']>input").val()||0,
					f2Wdyd : row.find("td[name='f2wdyd']>input").val()||0,
					f2Glyd : row.find("td[name='f2glyd']>input").val()||0,
					ssdsjXs : TimeUtil.getHour(ssdsj)||0,
					ssdsjFz : TimeUtil.getMinute(ssdsj)||0,
					dgxh : row.find("td[name='dgxh']>input").val()||0,
					ts1 : row.find("td[name='ts1']>input").val()||0,
					ts2 : row.find("td[name='ts2']>input").val()||0,
					mgb : row.find("td[name='mgb']>input").val()||0,
					igb : row.find("td[name='igb']>input").val()||0,
					tgb : row.find("td[name='tgb']>input").val()||0,
					tsfxpzid : row.find("td[name='tsqk']>select").val(),
					jhF1 : row.find("td[name='f1sj']>small[name='jh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					xlhF1 : row.find("td[name='f1sj']>small[name='xlh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					jhF2 : row.find("td[name='f2sj']>small[name='jh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					xlhF2 : row.find("td[name='f2sj']>small[name='xlh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					jhJc : row.find("td[name='jcsj']>small[name='jh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					xlhJc : row.find("td[name='jcsj']>small[name='xlh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					jhWdg : row.find("td[name='dgxh']>small[name='jh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					xlhWdg : row.find("td[name='dgxh']>small[name='xlh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					jhSsd : row.find("td[name='ssdsj']>small[name='jh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					xlhSsd : row.find("td[name='ssdsj']>small[name='xlh']").text().replace(/&nbsp;/g, "").replace(/无/g, ""),
					avFxr : row.find("td[name='avFxr']>input").val(),
					avZzh : row.find("td[name='avZzh']>input").val(),
					meFxr : row.find("td[name='meFxr']>input").val(),
					meZzh : row.find("td[name='meZzh']>input").val(),
					jzshrk : row.find("td[name='jzshrk']>input").val(),
					xdbs : 0
			}
			if(row.find("input[name='frid']").val()){
				data.id = row.find("input[name='frid']").val();
			}
			flightData.push(data);
		});
		totalData.flightData = flightData;
		
		// 累计总数调整
		if(hasDifference("f1n1")||hasDifference("f1n2")||hasDifference("f1n3")||
				hasDifference("f1n4")||hasDifference("f1n5")||hasDifference("f1n6")||
				hasDifference("f2n1")||hasDifference("f2n2")||hasDifference("f2n3")||
				hasDifference("f2n4")||hasDifference("f2n5")||hasDifference("f2n6")){
			var data = {
					f1N1 : calcDifference("f1n1"),
					f1N2 : calcDifference("f1n2"),
					f1N3 : calcDifference("f1n3"),
					f1N4 : calcDifference("f1n4"),
					f1N5 : calcDifference("f1n5"),
					f1N6 : calcDifference("f1n6"),
					f2N1 : calcDifference("f2n1"),
					f2N2 : calcDifference("f2n2"),
					f2N3 : calcDifference("f2n3"),
					f2N4 : calcDifference("f2n4"),
					f2N5 : calcDifference("f2n5"),
					f2N6 : calcDifference("f2n6"),
					xdbs : 1
					
			};
			totalData.flightData.push(data);
		}
		
		
		// 添加检查人数据
		var jcr = [];
		$.each($("div[name='jcr']:visible"), function(i, div){
			var data = {
					hd : $(div).attr("hc"),
					jcr : $(div).find("input[name='show']").val()
			}
			if($(div).find("input[name='jcrid']").val()){
				data.id = $(div).find("input[name='jcrid']").val();
			}
			jcr.push(data);
		});
		totalData.jcr = jcr;
		
		// 添加完成工作
		$("#finishedWorkList>tr[name='finishedWork']").each(function(i, row){
			row = $(row);
			var data = totalData.getFinishedWork(row.attr("id"));
			data.rwdid = row.find("input[name='rwdid']").val();
			data.hd = row.find("select[name='hd']").val();
			data.hdms = row.find("select[name='hd']>option:selected").text();
			data.whnr = row.find("td[name='rwxx']").text();
			data.gzxx = row.find("td[name='gzxx']").text();
			data.clcs = row.find("td[name='clcs']").text();
			data.zrrid = row.find("input[name='zrrid']").val();
			data.gzxmbldh = row.find("td[name='gzxmbldh']").text();
			data.sjgsRs = row.find("input[name='sjgsRs']").val();
			data.sjgsXss = row.find("input[name='sjgsXss']").val();
			data.rwlx = row.find("input[name='rwlx']").val();
			data.rwzlx = row.find("input[name='rwzlx']").val();
			data.rwdh = row.find("td[name='rwdh']").text();
			data.xgdjid = row.find("input[name='xgdjid']").val();
			if(row.find("input[name='fwid']").val()){
				data.id = row.find("input[name='fwid']").val();
			}
		});
		
		// 添加附件
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var attachments = [];
		if(len != undefined && len>0){
			for(var i =0;i<len;i++){
				attachments.push({
					'yswjm':responses[i].attachments[0].yswjm
					,'wbwjm':responses[i].attachments[0].wbwjm
					,'nbwjm':responses[i].attachments[0].nbwjm
					,'cflj':responses[i].attachments[0].cflj
					,'wjdx':responses[i].attachments[0].wjdx
					,'hzm':responses[i].attachments[0].nbwjm.split(".")[1]
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
		totalData.attachments = attachments;
	}
	
	/**
	 * 切换显示类型
	 */
	function switchType(){
		var type = $("#forwordType").val();
		var zt = $("#frsZt").val();
		// 新增
		if(type == 1){
			$("#cancelBtn").hide();
	 		//$("#dprtcode").val(params.dprtcode);
	 		$("#dprtcode").val(userJgdm);
	 		$("#dprtcode").attr("disabled","disabled");
	 		changeOrganization(true);
	 		// 带出主页面的飞机注册号，无该项，则默认选择第一条
	 		$("#fjzch").val($("#fjzch_add").val());
	 		if(!$("#fjzch").val()){
	 			$("#fjzch option:first").prop("selected", 'selected');
	 		}
		}
		// 修改
		else if(type == 2){
			if(zt != 1){
				$("#saveBtn").hide();
			}
			$("#pageTitle_cn").html("修改");
			$("#pageTitle_eng").html("Edit");
			$("#fxjldhDiv").show();
			$("#fjzch").attr("disabled","disabled");
			$("#dprtcode").attr("disabled","disabled");
//			$(".frs-head").css("width","20%");
			changeOrganization(true);
			ajaxGetTotalData();
		} 
		// 查看
		else if(type == 3){
			$("#pageTitle_cn").html("查看");
			$("#pageTitle_eng").html("View");
			$("#fxjldhDiv").show();
//			$(".frs-head").css("width","20%");
			changeOrganization(true);
			ajaxGetTotalData();
			disableAll();
			$("#cancelBtn").hide();
			$(".view").hide();
			$("#backBtn").show();
		}
	}
	
	/**
	 * 失效所有输入框
	 */
	function disableAll(){
		$("div.page-container input").attr("disabled","disabled");
		$("div.page-content textarea").attr("disabled","disabled");
		$("select").attr("disabled","disabled");
		$("div.page-container input:radio").attr("disabled","disabled");
		$("div.page-content button").hide();
		$("#hidden_disassemblyComponent").parent().hide();
		$("#keyword_search").hide();
		$("#component_mount_search").hide();
		$("#zjh_show_zs").parent().removeClass("input-group");
	}
	
	function ajaxGetTotalData(){
		AjaxUtil.ajax({
			   url:basePath+"/flightdata/flightrecordsheet/getTotalData",
			   type: "post",
			   data:JSON.stringify({
				   id : $("#frsId").val()
			   }),
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   success:function(data){
				   // 回填检查人数据
				   fillRummager(data.jcr);
				   // 回填完成工作
				   fillFinishedWork(data.finishedWork, data);
				   if($("#forwordType").val()==3){
					   $("#finishedWorkTable input").attr("disabled","disabled");
					   $("#finishedWorkTable textarea").attr("disabled","disabled");
					   $("#finishedWorkTable select").attr("disabled","disabled");
					   $("#finishedWorkTable button:not(.notHide)").addClass("disabled");
					   $("#finishedWorkTable a").addClass("disabled");
					   $("#finishedWorkTable i.icon-remove").hide();
					   $("#fixedMonitorList i.icon-remove").hide();
				   }
				   // 回填飞行数据
				   fillFlightData(data.flightData);
				   // 加载附件
				   loadAttachments($("#frsId").val());
		      }
		    });
	}
	
	// 回填完成工作
	function fillFinishedWork(list, data){
		if(list && list.length > 0){
			$("#finishedWorkList").empty();
			$.each(list, function(i, obj){
				// 加入到数据集合中
				totalData.finishedWork.push({
					 rowId : obj.id,
					 dismountRecord : []
				});
				obj.rwlxms = getTaskTypeDescription(obj.rwlx, obj.rwzlx);
				// 构建tr
				obj.realId = obj.id;
				buildFinishedWork(obj, data);
				$("#"+obj.id+" >td>select[name='hd']").val(obj.hd);
				$("#"+obj.id+" >td>span[name='hdDes']").text(hdMap[obj.hd]);
				$("#"+obj.id+" >td>span[name='hdDes']").parent().attr("title", hdMap[obj.hd]);
				$("#"+obj.id+" >td[name='gzxx']").text(obj.gzxx||"");
				$("#"+obj.id+" >td[name='gzxx']").attr("title", obj.gzxx||"");
				$("#"+obj.id+" >td[name='clcs']").text(obj.clcs||"");
				$("#"+obj.id+" >td[name='clcs']").attr("title", obj.clcs||"");
				$("#"+obj.id+" >td>input[name='sjgsRs']").val(obj.sjgsRs);
				$("#"+obj.id+" >td>input[name='sjgsXss']").val(obj.sjgsXss);
				$("#"+obj.id+" >td>span[name='sjgsDes']").text(getSjgsDes(obj.sjgsRs, obj.sjgsXss));
				$("#"+obj.id+" >td>span[name='sjgsDes']").parent().attr("title", getSjgsDes(obj.sjgsRs, obj.sjgsXss));
				$("#"+obj.id+" >td[name='gzxmbldh']").text(obj.gzxmbldh||"");
				$("#"+obj.id+" >td[name='gzxmbldh']").attr("title", obj.gzxmbldh||"");
				$("#"+obj.id+" >td input[name='zrrid']").val(obj.zrrid);
				if(obj.zrr){
					$("#"+obj.id+" >td span[name='zrr']").text((obj.zrr.username||"")+" "+(obj.zrr.realname||""));
					$("#"+obj.id+" >td span[name='zrr']").parent().attr("title", (obj.zrr.username||"")+" "+(obj.zrr.realname||""));
				}
				
				$('#'+obj.id).find("b[name='attachmentCount']").html((obj.attachments||[]).length);
				fillDisassemblyRecord(obj.id, obj.dismountRecord);
			});
		}
	}
	
	/**
	 * 回填拆解记录
	 */
	function fillDisassemblyRecord(rowId, list){
		if(list && list.length > 0 && list[0].id){
			SubTable.addSubTable(rowId);
			$.each(list, function(i, obj){
				SubTable.addSubRow(rowId);
				$("#"+rowId+" .icon-double-angle-down").removeClass("icon-double-angle-down").addClass("icon-double-angle-up");
				var rownum = SubTable.getMaxSubRowNum(rowId);
				var subRowId = rowId+"_"+(rownum-1);
				var targetRow = $("#"+subRowId);
				var data = totalData.getDismountRecord(subRowId);
				targetRow.find("input[name='cjid']").val(obj.id);
				data.id = obj.id;
				data.timeMonitor = {
						tsn : obj.timeMonitor.tsn,
						tso : obj.timeMonitor.tso,
						settings : [],
						conditions : []
				};
				// 拆下件
				if(obj.off){
					targetRow.find("td:eq(1)>i.icon-plus").removeClass("icon-plus").addClass("icon-edit");
					targetRow.find("td:eq(2)").html(StringUtil.escapeStr(obj.off.jh));
					targetRow.find("td:eq(2)").attr("title",obj.off.jh);
					targetRow.find("td:eq(3)").html(StringUtil.escapeStr(obj.off.ywmc));
					targetRow.find("td:eq(3)").attr("title",obj.off.ywmc);
					targetRow.find("td:eq(4)").html(StringUtil.escapeStr(obj.off.xlh));
					targetRow.find("td:eq(4)").attr("title",obj.off.xlh);
					targetRow.find("td:eq(5)").html(DicAndEnumUtil.getEnumName('planeComponentPositionEnum',obj.off.wz));
					targetRow.find("td:eq(5)").attr("title",DicAndEnumUtil.getEnumName('planeComponentPositionEnum',obj.off.wz));
					targetRow.find("span[name='cxjldh']").text(obj.cxjldh||"");
					targetRow.find("span[name='cxjldh']").attr("title", obj.cxjldh||"");
					// 加入到数据集合中
					data.cxZjqdid = obj.off.id;
					data.cxWz = obj.off.wz;
					data.cxFjdid = obj.off.fjdid;
					data.cxCj = obj.off.cj;
					data.cxjldh = obj.cxjldh||"";
					data.cxBz = obj.cxBz||"";
				}
				// 装上件
				if(obj.on){
					targetRow.find("td:eq(7)>i.icon-plus").removeClass("icon-plus").addClass("icon-edit");
					targetRow.find("td:eq(8)").html(StringUtil.escapeStr(obj.on.jh));
					targetRow.find("td:eq(8)").attr("title", obj.on.jh);
					targetRow.find("td:eq(9)").html(StringUtil.escapeStr(obj.on.ywmc));
					targetRow.find("td:eq(9)").attr("title", obj.on.ywmc);
					targetRow.find("td:eq(10)").html(StringUtil.escapeStr(obj.on.xlh));
					targetRow.find("td:eq(10)").attr("title", obj.on.xlh);
					targetRow.find("td:eq(11)").html(DicAndEnumUtil.getEnumName('planeComponentPositionEnum',obj.on.wz));
					targetRow.find("td:eq(11)").attr("title", DicAndEnumUtil.getEnumName('planeComponentPositionEnum',obj.on.wz));
					targetRow.find("td:eq(12)").html(obj.on.zjsl);
					targetRow.find("td:eq(12)").attr("title", obj.on.zjsl);
					data.on.fjzch = obj.on.fjzch;
					data.on.fjjx = $("#fjzch option:selected").attr("fjjx");
					data.on.xlh = obj.on.xlh;
					data.on.pch = obj.on.pch;
					data.on.jh = obj.on.jh;
					data.on.zjh = obj.on.zjh;
					if(obj.on.fixChapter){
						data.on.zjh_show = $.trim((obj.on.fixChapter.zjh||"")+"  "+ (obj.on.fixChapter.zwms||""));
					}
					data.on.cjjh = obj.on.cjjh;
					data.on.zwmc = obj.on.zwmc;
					data.on.ywmc = obj.on.ywmc;
					data.on.bz = obj.on.bz;
					data.on.bjgzjl = obj.on.bjgzjl;
					data.on.zjsl = obj.on.zjsl;
					data.on.zjslMax = obj.on.zjslMax;
					data.on.scrq = (obj.on.scrq||"").substr(0, 10);
					data.on.xkzh = obj.on.xkzh;
					data.on.shzh = obj.on.shzh;
					data.on.llkbh = obj.on.llkbh;
					data.on.llklx = obj.on.llklx;
					data.on.kzlx = obj.on.kzlx;
					data.on.isDj = obj.on.isDj;
					data.on.azrq = (obj.on.azrq||"").substr(0, 10);
					data.on.azjldh = obj.on.azjldh;
					data.on.ccrq = (obj.on.ccrq||"").substr(0, 10);
					data.on.ccjldh = obj.on.ccjldh;
					data.on.nbsbm = obj.on.nbsbm;
					data.on.wz = obj.on.wz;
					data.on.tso = obj.on.tso;
					data.on.tsn = obj.on.tsn;
					data.on.fjdid = obj.on.fjdid;
					data.on.parent = obj.on.parent;
					data.on.cj = obj.on.cj;
					data.zsIs = 1;
					data.zsWckcid = obj.zsWckcid;
					data.on.children = [];
					data.on.id = obj.on.id;
					data.on.zsZjqdid = obj.on.zsZjqdid;
					if(obj.on.parent){
						targetRow.find("td:eq(13)").html(StringUtil.escapeStr(obj.on.parent.displayName));
						targetRow.find("td:eq(13)").attr("title", obj.on.parent.displayName);
					}
					if(obj.on.children){
						$.each(obj.on.children, function(i, c){
							data.on.children.push({
								zbjZjqdid : c.zbjZjqdid,
								jh : c.jh||"",
								xlh : c.xlh||"",
								zwmc : c.zwmc||"",
								ywmc : c.ywmc||"",
								zjh_show : c.zjh_show||""
							});
						});
					}
				}
				// 时控件
				if(obj.timeMonitor){
					if(obj.timeMonitor.settings){
						$(obj.timeMonitor.settings).each(function(i, t){
							var setting = {};
							setting.fjzch = t.fjzch;
							setting.bjlx = t.bjlx;
							setting.jh = t.jh;
							setting.xlh = t.xlh;
							setting.jklbh = t.jklbh;
							setting.jkflbh = t.jkflbh;
							setting.pxh = t.pxh;
							setting.gdsx = t.gdsx;
							setting.zjyy = t.zjyy;
							setting.bjyy = t.bjyy;
							setting.bjyc = t.bjyc;
							setting.zjhyy = t.zjhyy;
							setting.gdsxDw = t.gdsxDw;
							setting.zjyyDw = t.zjyyDw;
							setting.bjyyDw = t.bjyyDw;
							setting.bjycDw = t.bjycDw;
							setting.zjhyyDw = t.zjhyyDw;
							data.timeMonitor.settings.push(setting);
						});
					}
					if(obj.timeMonitor.conditions){
						$(obj.timeMonitor.conditions).each(function(i, c){
							var condition = {
									tsfxpzid : c.tsfxpzid,
									xsz : c.xsz
							};
							data.timeMonitor.conditions.push(condition);
						});
					}
					if(obj.timeMonitor.settings.length > 0){
						$("#"+subRowId+ " td:nth-child(16) > a").css("color","green").html("<u>已配置</u>");
					}
					if($("#forwordType").val() == 3){
						if(obj.on.jh && obj.timeMonitor.settings.length == 0){
							$("#"+subRowId+ " td:nth-child(16)").html("无");
						}else{
							$("#"+subRowId+ " td:nth-child(16) > a").html("<u>查看</u>");
						}
					}
				}
				// 定检件
				if(obj.fixedMonitor){
					$(obj.fixedMonitor).each(function(i, f){
						var fixedMonitor = {
								id : f.id,
								fjzch : f.fjzch,
								bjlx : 1,
								jh : f.jh,
								xlh : f.xlh,
								nbsbm : f.nbsbm,
								djbh : f.djbh,
								zwms : f.zwms,
								bb : f.bb,
								monitorItemList : []
						};
						$(f.monitorItemList).each(function(i2,m){
							var monitorItem = {
									fjzch : m.fjzch,
									bjlx : 1,
									jh : m.jh,
									xlh : m.xlh,
									djbh : m.djbh,
									jkflbh : m.jkflbh,
									jklbh : m.jklbh,
									jkSz : m.jkSz,
									bjyy : m.bjyy,
									zjhyy : m.zjhyy,
									ms : m.zwms,
									jklms : m.zwms,
									zwms : m.zwms,
									zqz : m.zqz
							};
							monitorItem.jkDw = m.jkDw;
							monitorItem.bjyyDw = m.bjyyDw;
							monitorItem.zjhyyDw = m.zjhyyDw;
							fixedMonitor.monitorItemList.push(monitorItem);
						});
						data.fixedMonitor.push(fixedMonitor);
					});
					if(obj.fixedMonitor.length > 0){
						$("#"+subRowId+ " td:nth-child(17) > a").css("color","green").html("<u>已配置</u>");
					}
					if($("#forwordType").val() == 3){
						if(obj.on.jh && obj.fixedMonitor.length == 0){
							$("#"+subRowId+ " td:nth-child(17) ").html("无");
						}else{
							$("#"+subRowId+ " td:nth-child(17) > a").html("<u>查看</u>");
						}
					}
				}
			});
			if($("#forwordType").val() == 3){
				$(".icon-plus.color-blue").removeClass("icon-plus").addClass("icon-eye-open");
				$(".icon-edit.color-blue").removeClass("icon-edit").addClass("icon-eye-open");
				$("table[id^='subtable_finishedWorkTable_'] tbody tr").each(function(i, row){
					var cxjh = $(row).find("td:eq(2)").html();
					var zsjh = $(row).find("td:eq(8)").html();
					if(!cxjh){
						$(row).find("td:eq(1) i").remove();
					}
					if(!zsjh){
						$(row).find("td:eq(7) i").remove();
						$(row).find("td:eq(14) a,td:eq(15) a").remove();
					}
				});
			}
		}
	}
	
	/**
	 * 回填检查人
	 */
	function fillRummager(data){
		if(data && data.length > 0){
			$.each(data, function(i, obj){
				var div = $("div[name='jcr'][hc='"+obj.hd+"']");
				div.show();
				div.find("input[name='show']").val(obj.jcr);
				div.find("input[name='jcrid']").val(obj.id);
			});
		}
	}
	
	/**
	 * 回填飞行数据
	 * @param list
	 */
	function fillFlightData(list){
		if(list && list.length > 0){
			$("#hc1 td:first").attr("rowspan",list.length);
			$.each(list, function(i, obj){
				if(obj.hc){	// 四个航次
					var row = $("#flightDataList >tr[hc='"+obj.hc+"']");
					row.show();
					row.find("td[name='fxsj']>input").val(fillWithZero("fxsj", obj.fxsj));
					row.find("td[name='f1sj']>input").val(fillWithZero("f1sj", obj.f1SjN16Z));
					row.find("td[name='f2sj']>input").val(fillWithZero("f2sj", obj.f2SjN16Z));
					row.find("td[name='jcsj']>input").val(fillWithZero("jcsj", obj.jcsjZ));
					row.find("td[name='ssdsj']>input").val(fillWithZero("ssdsj", obj.ssdsj));
					row.find("td[name='qljxh']>input").val(fillWithZero("qljxh", obj.qljxh));
					row.find("td[name='jcxh']>input").val(fillWithZero("jcxh", obj.jcxh));
					row.find("td[name='ylfxq']>input").val(fillWithZero("ylfxq", obj.ylFxq));
					row.find("td[name='ylfxh']>input").val(fillWithZero("ylfxh", obj.ylFxh));
					row.find("td[name='f1n1']>input").val(fillWithZero("f1n1", obj.f1N1));
					row.find("td[name='f1n2']>input").val(fillWithZero("f1n2", obj.f1N2));
					row.find("td[name='f1n3']>input").val(fillWithZero("f1n3", obj.f1N3));
					row.find("td[name='f1n4']>input").val(fillWithZero("f1n4", obj.f1N4));
					row.find("td[name='f1n5']>input").val(fillWithZero("f1n5", obj.f1N5));
					row.find("td[name='f1n6']>input").val(fillWithZero("f1n6", obj.f1N6));
					row.find("td[name='f1hy']>input").val(fillWithZero("f1hy", obj.f1Hy));
					row.find("td[name='f1wdyd']>input").val(fillWithZero("f1wdyd", obj.f1Wdyd));
					row.find("td[name='f1glyd']>input").val(fillWithZero("f1glyd", obj.f1Glyd));
					row.find("td[name='f2n1']>input").val(fillWithZero("f2n1", obj.f2N1));
					row.find("td[name='f2n2']>input").val(fillWithZero("f2n2", obj.f2N2));
					row.find("td[name='f2n3']>input").val(fillWithZero("f2n3", obj.f2N3));
					row.find("td[name='f2n4']>input").val(fillWithZero("f2n4", obj.f2N4));
					row.find("td[name='f2n5']>input").val(fillWithZero("f2n5", obj.f2N5));
					row.find("td[name='f2n6']>input").val(fillWithZero("f2n6", obj.f2N6));
					row.find("td[name='f2hy']>input").val(fillWithZero("f2hy", obj.f2Hy));
					row.find("td[name='f2wdyd']>input").val(fillWithZero("f2wdyd", obj.f2Wdyd));
					row.find("td[name='f2glyd']>input").val(fillWithZero("f2glyd", obj.f2Glyd));
					row.find("td[name='dgxh']>input").val(fillWithZero("dgxh", obj.dgxh));
					row.find("td[name='ts1']>input").val(fillWithZero("ts1", obj.ts1));
					row.find("td[name='ts2']>input").val(fillWithZero("ts2", obj.ts2));
					row.find("td[name='mgb']>input").val(fillWithZero("mgb", obj.mgb));
					row.find("td[name='igb']>input").val(fillWithZero("igb", obj.igb));
					row.find("td[name='tgb']>input").val(fillWithZero("tgb", obj.tgb));
					row.find("td[name='tsqk']>select").val(obj.tsfxpzid);
					row.find("td[name='avFxr']>input").val(obj.avFxr);
					row.find("td[name='avZzh']>input").val(obj.avZzh);
					row.find("td[name='meFxr']>input").val(obj.meFxr);
					row.find("td[name='meZzh']>input").val(obj.meZzh);
					row.find("td[name='jzshrk']>input").val(obj.jzshrk);
					row.find("input[name='frid']").val(obj.id);
				}else{	// 调整值
					$("#adjustF1N1").val(obj.f1N1);
					$("#adjustF1N2").val(obj.f1N2);
					$("#adjustF1N3").val(obj.f1N3);
					$("#adjustF1N4").val(obj.f1N4);
					$("#adjustF1N5").val(obj.f1N5);
					$("#adjustF1N6").val(obj.f1N6);
					$("#adjustF2N1").val(obj.f2N1);
					$("#adjustF2N2").val(obj.f2N2);
					$("#adjustF2N3").val(obj.f2N3);
					$("#adjustF2N4").val(obj.f2N4);
					$("#adjustF2N5").val(obj.f2N5);
					$("#adjustF2N6").val(obj.f2N6);
				}
			});
			ajaxCount++;
			handleRecalc();
		}
	}
	
	/**
	 * 当航次1的飞行前数据和飞行数据都加载成功后，才会从新计算后面的飞行前数据
	 */
	function handleRecalc(){
		// 页面类型
		var type = $("#forwordType").val();
		if(ajaxCount == 2){
			// 重新计算飞行前数据
			recalcAllPreflightData(true);
			// 重新计算飞机的初始一级部件
			recalcLevelOneComponent();
		}
		if((type == '1' && ajaxCount == 1) 
				|| (type == '2' && ajaxCount == 2)){
			// 判断飞行数据是否可以输入
			judgeCanEntry();
		}
	}
	
	/**
	 * 判断飞行数据是否可以输入
	 */
	function judgeCanEntry(){
		if($("#forwordType").val() == '3'){
			return;
		}
		$("#flightDataList>tr>td>input").removeAttr("disabled");
		$("#flightDataList>tr").each(function(){
			var tr = $(this);
			var xlh_zf = tr.find("td[name='f1sj']>small[name='xlh']").html();
			var xlh_yf = tr.find("td[name='f2sj']>small[name='xlh']").html();
			var xlh_jc = tr.find("td[name='jcsj']>small[name='xlh']").html();
			var xlh_ssd = tr.find("td[name='ssdsj']>small[name='xlh']").html();
			var xlh_dg = tr.find("td[name='dgxh']>small[name='xlh']").html();
			if(xlh_zf == '无'){
				disabledJkx(tr, 'f1sj');
				disabledJkx(tr, 'f1n1');
				disabledJkx(tr, 'f1n2');
				disabledJkx(tr, 'f1n3');
				disabledJkx(tr, 'f1n4');
				disabledJkx(tr, 'f1n5');
				disabledJkx(tr, 'f1n6');
			}
			if(xlh_yf == '无'){
				disabledJkx(tr, 'f2sj');
				disabledJkx(tr, 'f2n1');
				disabledJkx(tr, 'f2n2');
				disabledJkx(tr, 'f2n3');
				disabledJkx(tr, 'f2n4');
				disabledJkx(tr, 'f2n5');
				disabledJkx(tr, 'f2n6');
			}
			if(xlh_jc == '无'){ 
				disabledJkx(tr, 'jcsj');
				disabledJkx(tr, 'jcxh');
			}
			if(xlh_ssd == '无'){
				disabledJkx(tr, 'ssdsj');
			}
			if(xlh_dg == '无'){
				disabledJkx(tr, 'dgxh');
			}
		});
	}
	
	function disabledJkx(tr, jkx){
		tr.find("td[name='"+jkx+"']>input").attr("disabled","disabled");
		tr.find("td[name='"+jkx+"']>input").val("0");
	}
	
	/**
	 * 重新计算飞机的初始一级部件
	 */
	function recalcLevelOneComponent(){
		// 修改飞行记录单
		if($("#forwordType").val() != '2'){
			return;
		}
		$("#finishedWorkList>tr[name='finishedWork']").each(function(){
			var finishedWork = $(this);
			var hd = $(this).find("select[name='hd']").val();
			if(hd == 1){
				// 循环拆解记录
				$("#finishedWorkList>tr>td>table>tbody>tr[id^='"+$(this).attr("id")+"']").each(function(){
					var cjjl = $(this);
					var dismountRecord = totalData.getDismountRecord(cjjl.attr("id"));
					var cxWz = dismountRecord.cxWz;
					var zsWz = dismountRecord.on.wz;
					var cj_cjjl = dismountRecord.cxCj || dismountRecord.on.cj || '';
					var wz_cjjl = cxWz || zsWz || '';
					if(cj_cjjl == 1){
						if(zsWz){
							levelOneComponent[wz_cjjl].jh = '';
							levelOneComponent[wz_cjjl].xlh = '';
						}
						if(cxWz){
							levelOneComponent[wz_cjjl].jh = cjjl.find("td:eq(2)").html();
							levelOneComponent[wz_cjjl].xlh = cjjl.find("td:eq(4)").html();
						}
					}
				});
			}
		});
	}
	
	/**
	 * 不满足正则则回退
	 * @param obj
	 * @param reg
	 */
	function backspace(obj, reg){
		var value = obj.val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(value);
	}
	
	/**
	 * 更换部件
	 * @param wz
	 * @param hd
	 * @param jh
	 * @param xlh
	 */
	function replaceComponent(wz, hd, jh, xlh, type){
		if(type == "off"){
			$("#flightDataList>tr").each(function(i, row){
				row = $(row);
				if(parseInt(row.attr("hc")) <= hd){
					return true;
				}
				if(wz == 1){
					setJhAndXlh(row, 'f1sj', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n1', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n2', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n3', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n4', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n5', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f1n6', jh, xlh, null, type, row.attr("hc"));
				}else if(wz == 2){
					setJhAndXlh(row, 'f2sj', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n1', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n2', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n3', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n4', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n5', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'f2n6', jh, xlh, null, type, row.attr("hc"));
				}else if(wz == 3){ 
					setJhAndXlh(row, 'jcsj', jh, xlh, null, type, row.attr("hc"));
					setJhAndXlh(row, 'jcxh', jh, xlh, null, type, row.attr("hc"));
				}else if(wz == 4){
					setJhAndXlh(row, 'ssdsj', jh, xlh, null, type, row.attr("hc"));
				}else if(wz == 5){
					setJhAndXlh(row, 'dgxh', jh, xlh, null, type, row.attr("hc"));
				}
			});
		}else if(type == "on"){
			startWait();
			AjaxUtil.ajax({
			   url:basePath+"/flightdata/flightrecordsheet/reloadPreflightData",
			   type: "post",
			   async: false,
			   data:JSON.stringify({
				   jh : jh,
				   xlh : xlh,
				   fjzch : $("#fjzch").val(),
				   fxjldid : $("#frsId").val(),
				   cj : 1,
				   dprtcode : $("#dprtcode").val()
			   }),
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   success:function(data){
				data = data ||{};
			    finishWait();
			    $("#flightDataList>tr").each(function(i, row){
			    	row = $(row);
					if(parseInt(row.attr("hc")) <= hd){
						return true;
					}
			    	if(wz == 1){
						setJhAndXlh(row, 'f1sj', jh, xlh, data.fsj, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n1', jh, xlh, data.fdjN1, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n2', jh, xlh, data.fdjN2, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n3', jh, xlh, data.fdjN3, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n4', jh, xlh, data.fdjN4, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n5', jh, xlh, data.fdjN5, type, row.attr("hc"));
						setJhAndXlh(row, 'f1n6', jh, xlh, data.fdjN6, type, row.attr("hc"));
					}else if(wz == 2){
						setJhAndXlh(row, 'f2sj', jh, xlh, data.fsj, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n1', jh, xlh, data.fdjN1, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n2', jh, xlh, data.fdjN2, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n3', jh, xlh, data.fdjN3, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n4', jh, xlh, data.fdjN4, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n5', jh, xlh, data.fdjN5, type, row.attr("hc"));
						setJhAndXlh(row, 'f2n6', jh, xlh, data.fdjN6, type, row.attr("hc"));
					}else if(wz == 3){ 
						var jcsj = TimeUtil.operateTime(data.jcsj||'0', '0', TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
						setJhAndXlh(row, 'jcsj', jh, xlh, jcsj, type, row.attr("hc"));
						setJhAndXlh(row, 'jcxh', jh, xlh, data.jcxh, type, row.attr("hc"));
					}else if(wz == 4){
						var ssdsj = TimeUtil.operateTime(data.ssdsj||'0', '0', TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
						setJhAndXlh(row, 'ssdsj', jh, xlh, ssdsj, type, row.attr("hc"));
					}else if(wz == 5){
						setJhAndXlh(row, 'dgxh', jh, xlh, data.dgxh, type, row.attr("hc"));
					}
			    });
		      }
		    });
		}
	}
	
	/**
	 * 设置件号、序列号、飞行前数据，并从新计算
	 * @param row
	 * @param column
	 * @param jh
	 * @param xlh
	 * @param pre
	 * @param type
	 */
	function setJhAndXlh(row, column, jh, xlh, pre, type, hc){
		if(type == "off"){
			var td = row.find("td[name='"+column+"']");
			var jh_old = td.find("small[name='jh']").html();
			var xlh_old = td.find("small[name='xlh']").html();
			//if(jh == jh_old && xlh == xlh_old){
				td.find("small[name='jh']").html("&nbsp;");
				td.find("small[name='xlh']").html("无");
				td.find("small[name='pre']").html("0.00");
				td.find("input").attr("disabled","disabled");
				td.find("input").val("0");
			//}
			if(hc == 8){
				recalcPreflightData(column);
			}
		}else if(type == "on"){
			var td = row.find("td[name='"+column+"']");
			td.find("small[name='jh']").html(jh||"&nbsp;");
			td.find("small[name='xlh']").html(xlh||"无");
			td.find("small[name='pre']").html(pre||"0.00");
			td.find("input").removeAttr("disabled");
			if(hc == 8){
				recalcPreflightData(column);
			}
		}
	}
	
	/**
	 * 提交生效
	 */
	function takeEffect(isSkip){
		// 表单验证
		if(isSkip != '1' && !validate(2)){
			return false;
		}
		// 添加飞行数据到total数据中
		gatherFlightData();
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/flightdata/flightrecordsheet/takeEffect",
			type:"post",
			data:JSON.stringify(totalData),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				finishWait();
				$("#frsId").val(data.id);
				$("#previousPage").attr("pageid",data.previousPage||"");
				$("#nextPage").attr("pageid",data.nextPage||"");
				$("#forwordType").val(2);
				uploadObj.responses = [];
				uploadObj.selectedFiles = 0;
				changePlane();
				ajaxGetTotalData();
				initPage();
				var nextPageId = $("#nextPage").attr("pageid");
				if(nextPageId){
					AlertUtil.showMessage("提交飞行记录单成功!请依次提交之后的飞行记录单。");
				}else{
					AlertUtil.showMessage("提交飞行记录单成功!");
				}
			}
		});
	}
	
	/**
	 * 显示撤销模态框
	 */
	function showCancelModal(){
		$("#cancelModal").modal("show");
	}
	
	/**
	 * 撤销
	 */
	function cancel(){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/flightdata/flightrecordsheet/cancel",
			type:"post",
			data:JSON.stringify({
				id : $("#frsId").val(),
				zdjsyy : $("#zdjsyy").val(),
				flightData : [],
				finishedWork : [],
				dprtcode : $("#dprtcode").val()}),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				finishWait();
				AlertUtil.showMessage("撤销飞行记录单成功!",'/flightdata/flightrecordsheet/main?pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 刷新故障保留单
	 */
	function refreshLegacyTrouble(){
		if(!$("#fxrq").val()){
			$("#DDFList").empty();
			$("#DDFList").append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
			return;
		}
		AjaxUtil.ajax({
			url:basePath+"/flightdata/flightrecordsheet/queryLegacyTrouble",
			type:"post",
			data:JSON.stringify({
				fjzch : $("#fjzch").val(),
				fxrq : $("#fxrq").val(),
				dprtcode : $("#dprtcode").val()
			}),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				$("#DDFList").empty();
				$("#DDF_count").text(data.length);
				if(data.length >0){
					   var htmlContent = '';
					   $.each(data,function(index,row){
						   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
						                   "<td style='text-align:left'><a href='javascript:void(0);' onclick='viewLegacyTrouble(\""+row.id+"\")'>"+StringUtil.escapeStr(row.gzbldh)+"</a></td>",
						                   "<td>"+row.mel+"</td>",
						                   "<td>"+(row.dqrq||"").substr(0, 10)+"</td>",
						                   "<td>"+(row.gbrq||"").substr(0, 10)+"</td>",
						                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.scNr)+"'>"+StringUtil.escapeStr(row.scNr)+"</td>",
						                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.zcNr)+"'>"+StringUtil.escapeStr(row.zcNr)+"</td>",
						                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.gzms)+"'>"+StringUtil.escapeStr(row.gzms)+"</td>",
						                   "</tr>"
						                   ].join("");
					   });
					   $("#DDFList").html(htmlContent);
					   FormatUtil.removeNullOrUndefined("#DDFList tr td");
			   } else {
				  $("#DDFList").append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
			   }
			}
		});
	}
	
	/**
	 * 按照指定长度缩写内容
	 * @param content
	 * @param length
	 * @returns {String}
	 */
	function abbreviate(content, length, style){
		if(!content || content.length <= length){
			return "<td "+(style||"")+">" + content+ "</td>";
		}
		return "<td title='"+content+"' "+(style||"")+">" + content.substr(0, length)+ "...</td>";
	}
	
	/**
	 * 返回主页面
	 */
	function goToMainPage(){
		window.location = basePath+"/flightdata/flightrecordsheet/main?pageParam="+pageParam;
	}
	
	/**
	 * 实际人数keyup
	 * @param obj
	 */
	function sjrsKeyup(obj){
		backspace($(obj),/^([0-9]|([1-9]\d+))$/);
		var td = $(obj).parent();
		var personNum = td.find("input:first").val();
		var timeNum = td.find("input:last").val();
		var result = (parseFloat(personNum||0)*parseFloat(timeNum||0));
		td.find("span[name='totalTime']").text(result);
	}
	
	/**
	  * 限制装机数量
	  */
	 function limitCount(){
		 var xlh = $.trim($("#xlh_zs").val());
		 if(xlh){
			 $("#zjsl_zs").attr("disabled","disabled");
			 $("#zjsl_zs").val(1);
		 }else{
			 $("#zjsl_zs").removeAttr("disabled");
		 }
	 }
	 
	 /**
	  * 判断数组中是否有重复值
	  * @param arr
	  * @returns
	  */
	 function isRepeat(arr){
		var result = false;
		var nary=arr.sort();  
		for(var i=0;i<nary.length;i++){  
			if (nary[i]==nary[i+1]){  
				result = true;
				break;
			}  
		}
		return result;
	 }
	 
	 /**
	  * 查看故障保留单
	  */
	 function viewLegacyTrouble(id){
		 window.open(basePath + "/flightdata/legacytrouble/view?id="+id);
	 }
	 
	 /**
	 * 加载实际使用值
	 */
	function loadActuallyUsed(id){
		var on = totalData.getDismountRecord(id).on;
		var param = {
				fjzch : on.fjzch,
				fxjldid : $("#frsId").val(),
				jh : on.jh,
				xlh : on.xlh,
				fjdid : on.fjdid,
				cj : on.cj,
				dprtcode : $("#dprtcode").val()
		}
		AjaxUtil.ajax({
			   url:basePath+"/flightdata/flightrecordsheet/loadActuallyUsed",
			   async: false,
			   type: "post",
			   data:JSON.stringify(param),
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   success:function(data){
				   if(data){
					   for(obj in actuallyUsed){
						   actuallyUsed[obj] = data[obj]||"0";
					   }
				   }
				   // 此次飞行记录单之前的实际值 + 此次飞行记录单该航段之前的飞行数据
				   var hd = $("#"+id.split("_")[0]+">td>select[name='hd']").val()||"0";
				   $("#flightDataList>tr:visible").each(function(){
					   var tr = $(this);
					   var hc = tr.attr("hc");
					   if(parseInt(hc) < parseInt(hd)){
						   actuallyUsed.fuselage_flight_time = add(actuallyUsed.fuselage_flight_time, tr.find("td[name='fxsj']>input").val()||"0", "sj");
						   actuallyUsed.landing_gear_cycle = add(actuallyUsed.landing_gear_cycle, tr.find("td[name='qljxh']>input").val()||"0", "");
						   actuallyUsed.special_first = add(actuallyUsed.special_first, tr.find("td[name='ts1']>input").val()||"0", "");
						   actuallyUsed.special_second = add(actuallyUsed.special_second, tr.find("td[name='ts2']>input").val()||"0", "");
						   if(on.wz == 1 && on.cj == 2){
							   actuallyUsed.N1 = add(actuallyUsed.N1, tr.find("td[name='f1n1']>input").val()||"0", "");
							   actuallyUsed.N2 = add(actuallyUsed.N2, tr.find("td[name='f1n2']>input").val()||"0", "");
							   actuallyUsed.N3 = add(actuallyUsed.N3, tr.find("td[name='f1n3']>input").val()||"0", "");
							   actuallyUsed.N4 = add(actuallyUsed.N4, tr.find("td[name='f1n4']>input").val()||"0", "");
							   actuallyUsed.N5 = add(actuallyUsed.N5, tr.find("td[name='f1n5']>input").val()||"0", "");
							   actuallyUsed.N6 = add(actuallyUsed.N6, tr.find("td[name='f1n6']>input").val()||"0", "");
						   }else if(on.wz == 2 && on.cj == 2){
							   actuallyUsed.N1 = add(actuallyUsed.N1, tr.find("td[name='f2n1']>input").val()||"0", "");
							   actuallyUsed.N2 = add(actuallyUsed.N2, tr.find("td[name='f2n2']>input").val()||"0", "");
							   actuallyUsed.N3 = add(actuallyUsed.N3, tr.find("td[name='f2n3']>input").val()||"0", "");
							   actuallyUsed.N4 = add(actuallyUsed.N4, tr.find("td[name='f2n4']>input").val()||"0", "");
							   actuallyUsed.N5 = add(actuallyUsed.N5, tr.find("td[name='f2n5']>input").val()||"0", "");
							   actuallyUsed.N6 = add(actuallyUsed.N6, tr.find("td[name='f2n6']>input").val()||"0", "");
						   }else if(on.wz == 3 && on.cj == 2){
							   actuallyUsed.winch_time = add(actuallyUsed.winch_time, tr.find("td[name='jcsj']>input").val()||"0", "sj");
							   actuallyUsed.winch_cycle = add(actuallyUsed.winch_cycle, tr.find("td[name='jcxh']>input").val()||"0", "");
						   }else if(on.wz == 4 && on.cj == 2){
							   actuallyUsed.search_light_time = add(actuallyUsed.search_light_time, tr.find("td[name='ssdsj']>input").val()||"0", "sj");
						   }else if(on.wz == 5 && on.cj == 2){
							   actuallyUsed.ext_suspension_loop = add(actuallyUsed.ext_suspension_loop, tr.find("td[name='dgxh']>input").val()||"0", "");
						   }
					   }
				   });
		      }
	    });
	}
	
	function showAttachment(id,rwdid){
		$("#editFinishedWorkModal_rowId").val(id);
		$("#attachment_rwdid").val(rwdid);
		loadAttachments_task(rwdid, "");
		$("#attachmentModal").modal("show");
	}
	
	//上传
	initPlanTaskUpload("");
	initPlanTaskUpload("_all");
	
	function initPlanTaskUpload(flag){
		$("#fileuploader_task"+flag).uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					maxFileCount : 999,
					formData : {
						"fileType" : "plantask",
						"wbwjm" : function() {
							return $('#wbwjm_task'+flag).val();
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass: "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\' name=\''+data.attachments[0].uuid+'\'>';
						 trHtml = trHtml+'<td><div>';
						 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile_task(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
						 trHtml = trHtml+'</div></td>';
						 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
						 
						 trHtml = trHtml+'<td class="text-left" title="'+data.attachments[0].wjdxStr+'">'+data.attachments[0].wjdxStr+' </td>';
						 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
						 trHtml = trHtml+'<td title="'+data.attachments[0].czsj+'">'+data.attachments[0].czsj+'</td>';
						 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
						 
						 trHtml = trHtml+'</tr>';	 
						 $('#filelist_task'+flag+'>.non-choice').remove();
						 $('#filelist_task'+flag).append(trHtml);
						 
						 var work = totalData.getFinishedWork($("#editFinishedWorkModal_rowId").val());
						 if(!work){
							 work = {
									 rowId : $("#editFinishedWorkModal_rowId").val(),
									 dismountRecord : []
							 };
							 totalData.finishedWork.push(work);
						 }
						 var attachments = work.attachments || [];
						 attachments.push({
							 	'yswjm':data.attachments[0].yswjm
								,'wbwjm':data.attachments[0].wbwjm
								,'nbwjm':data.attachments[0].nbwjm
								,'cflj':data.attachments[0].cflj
								,'wjdx':data.attachments[0].wjdx
								,'uuid':data.attachments[0].uuid
								,'hzm':data.attachments[0].nbwjm.split(".")[1]
						 		,'wjdxStr':data.attachments[0].wjdxStr
						 		,'czrname':data.attachments[0].user.username+" "+data.attachments[0].user.realname
						 		,'realname':data.attachments[0].czsj
						 });
						 work.attachments = attachments;
						 
						 var id = $("#editFinishedWorkModal_rowId").val();
						 var count = $('#'+id).find("b[name='attachmentCount']");
						 count.html(parseInt(count.html())+1);
					}
					
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm_task'+flag).val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	$('#wbwjm_task'+flag).focus();
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
	function delfile_task(uuid) {
		var key = $('#' + uuid).attr('key');
		$('tr[name="'+uuid+'"]').remove();
		var work = totalData.getFinishedWork($("#editFinishedWorkModal_rowId").val());
		var attachments = work.attachments || [];
		if (key == undefined || key == null || key == '') {
			var len = attachments.length;
			var fileArray = [];
			var waitDelArray = [];
			if (len > 0) {
				for (var i = 0; i < len; i++) {
					if (attachments[i].uuid != uuid) {
						fileArray.push(attachments[i]);
					}
				}
				work.attachments = fileArray;
			}
		} else {
			attachments.push({
				id : key,
				uuid : uuid,
				operate : 'DEL'
			});
			work.attachments = attachments;
		}
		if($("#filelist_task>tr").length == 0){
			$("#filelist_task").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		}
		if($("#filelist_task_all>tr").length == 0){
			$("#filelist_task_all").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		}
		var id = $("#editFinishedWorkModal_rowId").val();
		var count = $('#'+id).find("b[name='attachmentCount']");
		count.html(parseInt(count.html())-1);
	}
	
	/**
	 * 加载附件
	 * @param id
	 */
	function loadAttachments_task(id, flag) {
		$('#filelist_task'+flag+'>tr').remove();
		$("#filelist_task"+flag).append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		AjaxUtil.ajax({
					url : basePath + "/common/loadAttachments",
					type : "post",
					data : {
						mainid : id
					},
					success : function(data) {
						if (data.success) {
							var attachments = data.attachments;
							
							var work = totalData.getFinishedWork($("#editFinishedWorkModal_rowId").val());
							var attachments_this = work.attachments||[];
							for(var m = 0; m < attachments_this.length; m++){
								if(attachments_this[m].operate != 'DEL'){
									attachments.push(attachments_this[m]);
								}else{
									for(var j = 0; j < attachments.length; j++){
										if(attachments_this[m].uuid == attachments[j].uuid){
											attachments.splice(j, 1);
										}
									}
								}
							}
							if (attachments.length > 0) {
								$('#filelist_task'+flag+'>.non-choice').remove();
								var trHtml = '';
								for (var i = 0; i < attachments.length; i++) {
									if(attachments[i].czsj){
										trHtml = trHtml
										+ '<tr bgcolor="#f9f9f9" id=\''
										+ attachments[i].uuid+'\' name=\'' 
										+ attachments[i].uuid+'\' key=\''
										+ attachments[i].id + '\' >';
										trHtml = trHtml + '<td><div>';
										trHtml = trHtml
										+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile_task(\''
										+ attachments[i].uuid
										+ '\')" title="删除 Delete">  ';
										trHtml = trHtml + '</div></td>';
										trHtml = trHtml
										+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'"> <a class="cursor-pointer" onclick="downloadfile(\''
										+ attachments[i].id + '\')" >'
										+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a></td>';
										trHtml = trHtml + '<td class="text-left" title="'+attachments[i].wjdxStr+'">'
										+ attachments[i].wjdxStr + ' </td>';
										trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
										+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
										trHtml = trHtml + '<td title="'+attachments[i].czsj+'">' + attachments[i].czsj
										+ '</td>';
										trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
										trHtml = trHtml + '</tr>';
									}else{
										 trHtml = trHtml+'<tr bgcolor="#f9f9f9" id=\''+data.attachments[i].uuid+'\' name=\''+data.attachments[i].uuid+'\'>';
										 trHtml = trHtml+'<td><div>';
										 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile_task(\''+data.attachments[i].uuid+ '\')" title="删除 Delete">  ';
										 trHtml = trHtml+'</div></td>';
										 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[i].wbwjm)+'">'+StringUtil.escapeStr(data.attachments[i].wbwjm)+'</td>';
										 trHtml = trHtml+'<td class="text-left" title="'+data.attachments[i].wjdxStr+'">'+data.attachments[i].wjdxStr+' </td>';
										 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[i].czrname)+'">'+StringUtil.escapeStr(data.attachments[i].czrname)+'</td>';
										 trHtml = trHtml+'<td title="'+data.attachments[i].realname+'">'+data.attachments[i].realname+'</td>';
										 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[i].relativePath+'\'/>';
										 trHtml = trHtml+'</tr>';	 
									}
								}
								$('#filelist_task'+flag).append(trHtml);
							}
							if($("#forwordType").val() == 3){
								if($("#filelist_task"+flag).parent().find("tr>th").length == 5){
									$("#filelist_task"+flag).parent().find("tr th:nth-child(1)").remove();
								}
								$("#task_sp"+flag).css("width",flag?"150px":"300px");
								$("#attachmentModal .form-group").remove();
								$("#editFinishedWorkModal .form-group").remove();
								$("#filelist_task"+flag+" tr td:nth-child(1)").remove();
								$('#filelist_task'+flag+'>.non-choice').remove();
								if($("#filelist_task"+flag+">tr").length == 0){
									$("#filelist_task"+flag).append('<tr class="non-choice"><td colspan="4">暂无数据 No data.</td></tr>');
								}
							}
						}
					}
				});
	}
	
	//改变组织机构时改变飞机注册号
	 function changeOrganization(notRefresh){
	 	var dprtcode = $.trim($("#dprtcode").val());
	 	var planeRegOption = '';
	 	if(planes != null && planes.length >0){
	 		$.each(planes,function(i,planeData){
	 			if(dprtcode == planeData.dprtcode){
	 				planeRegOption += ("<option value='"+StringUtil.escapeStr(planeData.fjzch)+
	 					"' zt='"+planeData.zt+"' fjjx='"+StringUtil.escapeStr(planeData.fjjx)+
	 					"' isTsqk='"+planeData.isTsqk+"'>"+StringUtil.escapeStr(planeData.fjzch)+"</option>");
	 			}
	 		});
	 	}
	 	if(planeRegOption == ''){
	 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
	 	}
	 	$("#fjzch").html(planeRegOption);
	 	var currentPlane = $("#fjzch_hid").val();
	 	if(currentPlane){
	 		$("#fjzch").val(currentPlane);
	 	}
	 	if(!notRefresh){
	 		changePlane();
	 	}
	 }
	 
	 
	 function initPlanes(){
		 AjaxUtil.ajax({
			   async: false,
			   url:basePath+"/productionplan/planeData/findAllWithFjjx",
			   type: "post",
			   success:function(data){
				   planes = data;
		      }
	    }); 
	 }
	 
	 /**
	  * 双击展开/收缩拆装记录
	  * @param btn
	  */
	 function toggleDismountRecord(btn){
		 var i = $(btn).find("i");
		 var row = $(btn).parent().parent();
		 if(i.hasClass("icon-double-angle-down")){
			 i.removeClass("icon-double-angle-down").addClass("icon-double-angle-up");
		 }else{
			 i.removeClass("icon-double-angle-up").addClass("icon-double-angle-down");
		 }
		 var rowId = row.attr("id");
			if(rowId){
				SubTable.toggleSubTable(rowId);
			}
	 }
	 
	 /**
	  * 显示完成工作模态框
	  * @param event
	  */
	 function showFinishedWorkModal(tr, event){
		 if(event.target.nodeName != "BUTTON"){
			 
			if(tr.find("input[name='xgdjid']").val()){
				$("#finishedWorkModal_lylx").show();
				$("#finishedWorkModal_lylx_select").hide();
				$("#finishedWorkModal_rwdh").attr("disabled","disabled");
				$("#finishedWorkModal_rwxgxx").attr("disabled","disabled");
			}else{
				$("#finishedWorkModal_lylx").hide();
				$("#finishedWorkModal_lylx_select").show();
				$("#finishedWorkModal_lylx_select").val(tr.find("input[name='rwzlx']").val());
				$("#finishedWorkModal_rwdh").removeAttr("disabled");
				$("#finishedWorkModal_rwxgxx").removeAttr("disabled");
			}
			
			$("#editFinishedWorkModal_rowId").val(tr.attr("id"));
			$("#finishedWorkModal_lylx").val(tr.find("td[name='rwlxDes']").html());
			$("#finishedWorkModal_rwdh").val(tr.find("td[name='rwdh']").html());
			$("#finishedWorkModal_rwxgxx").val(tr.find("td[name='rwxx']").html());
			$("#finishedWorkModal_leg").html(tr.find("select[name='hd']").html());
			$("#finishedWorkModal_leg").val(tr.find("select[name='hd']").val());
			
			$("#finishedWorkModal_gzxmbldh").val(tr.find("td[name='gzxmbldh']").text());
			$("#finishedWorkModal_gzxx").val(tr.find("td[name='gzxx']").text());
			$("#finishedWorkModal_clcs").val(tr.find("td[name='clcs']").text());
			$("#finishedWorkModal_rx").val(tr.find("input[name='sjgsRs']").val());
			$("#finishedWorkModal_xss").val(tr.find("input[name='sjgsXss']").val());
			sjrsKeyup($("#finishedWorkModal_rx").get(0));
			$("#finishedWorkModal_zrr").val(tr.find("span[name='zrr']").text());
			$("#finishedWorkModal_zrrid").val(tr.find("input[name='zrrid']").val());
			$("#editFinishedWorkModal").modal("show");
			
			$("#attachment_rwdid").val(tr.find("input[name='rwdid']").val());
			loadAttachments_task(tr.find("input[name='rwdid']").val(), '_all');
		 }
	 }
	 
	 /**
	  * 手工添加完成工作
	  */
	 function showFinishedWorkByHandModal(){
		 
		 $("#finishedWorkModal_lylx").hide();
		 $("#finishedWorkModal_lylx_select").show();
		 $("#finishedWorkModal_rwdh").removeAttr("disabled");
		 $("#finishedWorkModal_rwxgxx").removeAttr("disabled");
		
		 $("#editFinishedWorkModal_rowId").val(Math.uuid().toLowerCase());
		 $("#finishedWorkModal_lylx").val(2);
		 $("#finishedWorkModal_rwdh").val("");
	     $("#finishedWorkModal_rwxgxx").val("");
	     $("#finishedWorkModal_leg").html(buildHdOption());
	     
	     $("#finishedWorkModal_gzxmbldh").val("");
	     $("#finishedWorkModal_gzxx").val("");
	     $("#finishedWorkModal_clcs").val("");
	     $("#finishedWorkModal_rx").val("");
	     $("#finishedWorkModal_xss").val("");
	     sjrsKeyup($("#finishedWorkModal_rx").get(0));
	     $("#finishedWorkModal_zrr").val("");
	     $("#finishedWorkModal_zrrid").val("");
	     $("#filelist_task_all>tr").remove();
	     $("#filelist_task_all").append("<tr class='non-choice'><td colspan='5'>暂无数据 No data.</td></tr>");
		 $("#editFinishedWorkModal").modal("show");
	 }
	 
	 /**
	  * 保存完成工作
	  */
	 function saveFinishedWork(){
		 var row = $("#"+$("#editFinishedWorkModal_rowId").val());
		 // 修改完成工作
		 if(row.length > 0){
			// 航段发生改变，重新计算一级部件
			 if($("#finishedWorkModal_leg").val() != row.find("select[name='hd']").val()){
				 calcCurrentLevelOneComponentAll();
			 }
			 row.find("select[name='hd']").val($("#finishedWorkModal_leg").val());
			 row.find("span[name='hdDes']").text(hdMap[$("#finishedWorkModal_leg").val()]);
			 row.find("span[name='hdDes']").parent().attr("title", hdMap[$("#finishedWorkModal_leg").val()]);
			 row.find("td[name='gzxx']").text($("#finishedWorkModal_gzxx").val()||"");
			 row.find("td[name='gzxx']").attr("title", $("#finishedWorkModal_gzxx").val()||"");
			 row.find("td[name='clcs']").text($("#finishedWorkModal_clcs").val()||"");
			 row.find("td[name='clcs']").attr("title", $("#finishedWorkModal_clcs").val()||"");
			 row.find("td[name='gzxmbldh']").text($("#finishedWorkModal_gzxmbldh").val()||"");
			 row.find("td[name='gzxmbldh']").attr("title", $("#finishedWorkModal_gzxmbldh").val()||"");
			 row.find("span[name='zrr']").text($("#finishedWorkModal_zrr").val()||"");
			 row.find("span[name='zrr']").parent().attr("title", $("#finishedWorkModal_zrr").val()||"");
			 row.find("input[name='zrrid']").val($("#finishedWorkModal_zrrid").val()||"");
			 
			 var rx = $("#finishedWorkModal_rx").val()||"";
			 var xss = $("#finishedWorkModal_xss").val()||"";
			 row.find("input[name='sjgsRs']").val(rx);
			 row.find("input[name='sjgsXss']").val(xss);
			 row.find("span[name='sjgsDes']").text(getSjgsDes(rx, xss));
			 row.find("span[name='sjgsDes']").parent().attr("title", getSjgsDes(rx, xss));
			 
			 if(!row.find("input[name='xgdjid']").val()){
				 row.find("input[name='rwzlx']").val($("#finishedWorkModal_lylx_select").val());
				 row.find("td[name='rwdh']").text($("#finishedWorkModal_rwdh").val()||"");
				 row.find("td[name='rwdh']").attr("title", $("#finishedWorkModal_rwdh").val()||"");
				 row.find("td[name='rwxx']").text($("#finishedWorkModal_rwxgxx").val()||"");
				 row.find("td[name='rwxx']").attr("title", $("#finishedWorkModal_rwxgxx").val()||"");
				 row.find("td[name='rwlxDes']").text(getTaskTypeDescription(2,$("#finishedWorkModal_lylx_select").val()));
				 row.find("td[name='rwlxDes']").attr("title", getTaskTypeDescription(2,$("#finishedWorkModal_lylx_select").val()));
			 }
		 }
		 // 手工输入完成工作
		 else{
			 $("#finishedWorkList tr.non-choice").remove();
			 
			 buildFinishedWork({
				 id : $("#editFinishedWorkModal_rowId").val(),
				 rwdh : $("#finishedWorkModal_rwdh").val(),
				 rwlx : 2,
				 rwlxms : getTaskTypeDescription(2,$("#finishedWorkModal_lylx_select").val()),
				 rwzlx : $("#finishedWorkModal_lylx_select").val()
			 });
			 
			 var id = $("#editFinishedWorkModal_rowId").val();
			 var work = totalData.getFinishedWork(id);
			 if(!work){
				 totalData.finishedWork.push({
					 rowId : id,
					 dismountRecord : []
				 });
			 }else{
				 $('#'+id).find("b[name='attachmentCount']").html(work.attachments.length);
			 }
			 
			 row = $("#"+$("#editFinishedWorkModal_rowId").val());
			 row.find("select[name='hd']").val($("#finishedWorkModal_leg").val());
			 row.find("span[name='hdDes']").text(hdMap[$("#finishedWorkModal_leg").val()]);
			 row.find("span[name='hdDes']").parent().attr("title", hdMap[$("#finishedWorkModal_leg").val()]);
			 row.find("td[name='rwxx']").text($("#finishedWorkModal_rwxgxx").val()||"");
			 row.find("td[name='rwxx']").attr("title", $("#finishedWorkModal_rwxgxx").val()||"");
			 row.find("td[name='gzxx']").text($("#finishedWorkModal_gzxx").val()||"");
			 row.find("td[name='gzxx']").attr("title", $("#finishedWorkModal_gzxx").val()||"");
			 row.find("td[name='clcs']").text($("#finishedWorkModal_clcs").val()||"");
			 row.find("td[name='clcs']").attr("title", $("#finishedWorkModal_clcs").val()||"");
			 row.find("td[name='gzxmbldh']").text($("#finishedWorkModal_gzxmbldh").val()||"");
			 row.find("td[name='gzxmbldh']").attr("title", $("#finishedWorkModal_gzxmbldh").val()||"");
			 row.find("span[name='zrr']").text($("#finishedWorkModal_zrr").val()||"");
			 row.find("span[name='zrr']").parent().attr("title", $("#finishedWorkModal_zrr").val()||"");
			 row.find("input[name='zrrid']").val($("#finishedWorkModal_zrrid").val()||"");
			 
			 var rx = $("#finishedWorkModal_rx").val()||"";
			 var xss = $("#finishedWorkModal_xss").val()||"";
			 row.find("input[name='sjgsRs']").val(rx);
			 row.find("input[name='sjgsXss']").val(xss);
			 row.find("span[name='sjgsDes']").text(getSjgsDes(rx, xss));
			 row.find("span[name='sjgsDes']").parent().attr("title", getSjgsDes(rx, xss));
		 }
	 }
	 
	 /**
	  * 获取实际工时描述
	  * @param rs
	  * @param xss
	  * @returns {String}
	  */
	 function getSjgsDes(rs, xss){
		 var total = (parseFloat(rs||0)*parseFloat(xss||0));
		 return (rs||"0")+" 人 x "+(xss||"0")+" 时 = "+total;
	 }
	 
	 /**
	  * 初始化分页
	  */
	 function initPage(){
		 var type = $("#forwordType").val();
		 var previousPage = $("#previousPage");
		 var previousPageId = $("#previousPage").attr("pageid");
		 
		 var nextPage = $("#nextPage");
		 var nextPageId = $("#nextPage").attr("pageid");
		 if(type == 1){
			 previousPage.parent().hide();
			 nextPage.parent().hide();
		 }else{
			 previousPage.parent().show();
			 nextPage.parent().show();
		 }
		 if(!previousPageId){
			 previousPage.parent().addClass("disabled");
			 previousPage.attr("title","没有上一页");
		 }
		 if(!nextPageId){
			 nextPage.parent().addClass("disabled");
			 nextPage.attr("title","没有下一页");
		 }
	 }
	 
	 /**
	  * 上一页
	  */
	 function goToPreviousPage(){
		 var type = $("#forwordType").val();
		 var previousPageId = $("#previousPage").attr("pageid");
		 if(type == 1){
			 
		 }else if(type == 2 && previousPageId){
			 window.location =basePath+"/flightdata/flightrecordsheet/edit/"+previousPageId+"?pageParam="+pageParam;
		 }else if(type == 3 && previousPageId){
			 window.location =basePath+"/flightdata/flightrecordsheet/view/"+previousPageId;
		 }
	 }
	 
	 /**
	  * 下一页
	  */
	 function goToNextPage(){
		 var type = $("#forwordType").val();
		 var nextPageId = $("#nextPage").attr("pageid");
		 if(type == 1){
			 
		 }else if(type == 2 && nextPageId){
			 window.location =basePath+"/flightdata/flightrecordsheet/edit/"+nextPageId+"?pageParam="+pageParam;
		 }else if(type == 2 && !nextPageId){
			 //window.location =basePath+"/flightdata/flightrecordsheet/add/?pageParam="+pageParam+"&fjzch="+encodeURIComponent($("#fjzch").val());
		 }else if(type == 3 && nextPageId){
			 window.location =basePath+"/flightdata/flightrecordsheet/view/"+nextPageId;
		 }
	 }
	
	 /**
	  * 跳转到添加页面
	  * @param obj
	  * @param rid
	  */
	 function goToAddPage(){
		 window.location =basePath+"/flightdata/flightrecordsheet/add/?pageParam="+pageParam+"&fjzch="+encodeURIComponent($("#fjzch").val());
	 }
	 
	 /**
	  * 飞行数据补零
	  * @param jklbh
	  * @param val
	  */
	 function fillWithZero(jklbh, val){
		 if($.inArray(jklbh, loopArr) != -1){
			 val = (parseFloat(val||"0")).toFixed(2);
		 }else if($.inArray(jklbh, timeArr) != -1){
			 if(val.indexOf(":") != -1){
				 var hour = val.split(":")[0];
				 var minute = val.split(":")[1];
				 minute = minute.length == 1 ? "0"+minute : minute;
				 val = hour + ":" + minute;
			 }else{
				 val += ":00";
			 }
		 }
		 return val;
	 }
