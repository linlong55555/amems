	var delAttachements = [];
	
	$(function(){
		
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					//调用主列表页查询
				}
			}
		});
		
		// 初始化导航栏
		initNavigation();
		// 初始化验证
		initValidate();
		// 加载报废单数据
		loadDetail();
	});

	/**
	 * 选择申请人
	 */
	function chooseSqr(){
		//调用用户选择弹窗
		userModal.show({
			selected:$("#bfrid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#bfrid").val(data.id);
				$("#bfr").val(data.displayName);
				$("#bfbmid").val(data.dprt_bm?data.dprt_bm.id:"");
				$("#scrap_form").data('bootstrapValidator').resetForm(false); 
			},
			dprtcode:$("#dprtcode").val()
		});
	}
	
	/**
	 * 选择件号-航材主数据
	 */
	function chooseJh(){
		open_win_scrap_stock.show({
			multi:true,
			showStock:false,
			existsIdList:getExistsIdList(),
			callback: function(data){//回调函数
				for(var i = 0; i < data.length; i++){
					var kcid = data[i].id;
					data[i].id = "";
					data[i].kcid = kcid;
					data[i].kysl = (data[i].kcsl||0) - (data[i].djsl);
					addScrapDetail(data[i]);
				}
			},
			dprtcode:$("#dprtcode").val()
		},true);
		
		function getExistsIdList(){
			var ids = [];
			$("#list>tr input[name='kcid']").each(function(){
				ids.push($(this).val());
			});
			return ids;
		}
	}
	
	/**
	 * 新增部件报废清单
	 * @param obj
	 */
	function addScrapDetail(row){
		$("#list>.non-choice").remove();
		$("#list").append(["<tr>",
		                   ($("#type").val()!=3 && $("#type").val()!=4)?"<td style='text-align: center;vertical-align:middle;'>":"",
                		   ($("#type").val()!=3 && $("#type").val()!=4)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteScrapDetail(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
        				   ($("#type").val()!=3 && $("#type").val()!=4)?"</td>":"",
		                   "<td style='text-align: left' name='jh' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>",
		                   "<td style='text-align: right;vertical-align:middle;'>",
		                   		"<input type='text' class='form-control input-sm' name='bfsl' maxlength='8' value='"+(row.bfsl||row.kcsl||0)+"'>",
		                   "</td>",
		                   "<td style='text-align: left;vertical-align:middle;'>",
	                   			"<select class='form-control input-sm' name='ck' onchange='changeStore(this)'>"+buildStore(row.bfckid)+"</select>",
		                   "</td>",
		                   "<td style='text-align: left;vertical-align:middle;'>",
		                   		"<select class='form-control input-sm' name='kwh'>"+buildStorage(row.bfckid, row.bfkwid)+"</select>",
		                   "</td>",
		                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('materialPriceEnum',row.hCMainData.hcjz)+"'>"+DicAndEnumUtil.getEnumName('materialPriceEnum',row.hCMainData.hcjz)+"</td>",
		                   "<td style='text-align: right' title='"+StringUtil.escapeStr(row.kccb)+"'>"+StringUtil.escapeStr(row.kccb)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>",
		                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb)+"'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>",
		                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>",
		                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>",		             
		                   "<td style='text-align: right' name='kcsl' title='"+(row.kcsl||0)+"'>"+(row.kcsl||0)+"</td>",
		                   "<td style='text-align: right' title='"+((row.kysl||0))+"'>"+((row.kysl||0))+"</td>",
		                   "<input type='hidden' name='kcid' value='"+(row.kcid||"")+"'>",
		                   "<input type='hidden' name='kysl' value='"+((row.kysl||0))+"'>",
		                   "<input type='hidden' name='detailId' value='"+(row.id||"")+"'>",
		                   "</tr>"
		                   ].join(""));
	}
	
	/**
	 * 删除收货单详细
	 * @param obj
	 */
	function deleteScrapDetail(obj){
		if(obj){
			$(obj).parent().parent().remove();
		}else{
			$("#list>tr").remove();
		}
		if($("#list>tr").length == 0){
			var ths = $("#scrap_detail_table>thead>tr>th:visible").length;
			$("#list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 加载仓库下拉框
	 */
	function buildStore(ckid){
		var cklbs=[2];
		var dprtcode = $("#dprtcode").val();
		var storeHtml = "";
		$.each(userStoreList, function(index, row){
			if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
		 		if(ckid && row.id == ckid){
		 			storeHtml += "<option value=\""+row.id+"\" selected='selected'>"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
		 		}else{
		 			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
		 		}
			}
		})
		return storeHtml;
	}
	
	/**
	 * 获取默认的第一个仓库id
	 * @returns {String}
	 */
	function getDefaultSotreId(){
		var cklbs=[2];
		var dprtcode = $("#dprtcode").val();
		var ckid = "";
		$.each(userStoreList, function(index, row){
			if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode && !ckid){
				ckid = row.id;
			}
		})
		return ckid;
	}
	
	/**
	 * 加载库位信息
	 */
	function buildStorage(ckid_, kwid){
		var option;
		var ckid = ckid_ || getDefaultSotreId();
		$.each(userStoreList, function(index, row){
			if(row.id == ckid){
				for(var i = 0 ; i < row.storageList.length ; i++){
			 		var storage = row.storageList[i];
			 		if(kwid && storage.id == kwid){
			 			option += '<option value="'+storage.id+'" selected="selected">'+StringUtil.escapeStr(storage.kwh)+'</option>';
			 		}else{
			 			option += '<option value="'+storage.id+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
			 		}
			 	}
			}
		})
	 	return option;
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
	 * 初始化导航栏
	 */
	function initNavigation(){
		var type = $("#type").val();
		if(type == 1){
			Navigation(menuCode,'新增','Add');
			$("#bfr").val($("#dlrname").val());
			$("#bfrid").val($("#dlrid").val());
			$("#bfbmid").val($("#dlrbm").val());
		}else if(type == 2){
			Navigation(menuCode,'修改','Edit');
		}else if(type == 3){
			Navigation(menuCode,'审核','Audit');
			$(".view").hide();
		}else if(type == 4){
			Navigation(menuCode,'查看','View');
			$(".view").hide();
		}
		if(type != 1){
			$("#bfdh_div").show();
			$("#zt_div").show();
			$("#zdr_div").show();
			$("#zdsj_div").show();
		}
	}
	
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
				"fileType" : "scrap",
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
				 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				 
				 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
				 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
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
											+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(attachments[i].hzm)+'"> <a class="cursor-pointer" onclick="downloadfile(\''
											+ attachments[i].id + '\')" >'
											+ StringUtil.escapeStr(attachments[i].wbwjm) +"."+StringUtil.escapeStr(attachments[i].hzm)+ '</a></td>';
									trHtml = trHtml + '<td class="text-left">'
											+ attachments[i].wjdxStr + ' </td>';
									trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
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
							if($("#type").val() == 3 || $("#type").val() == 4){
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
	 * 返回到主页面
	 */
	function goToMainPage(){
		if($("#type").val()==1||$("#type").val()==2){
			window.location = basePath+"/aerialmaterial/scrap/apply?pageParam="+pageParam;
		}else{
			window.location = basePath+"/aerialmaterial/scrap/audit?pageParam="+pageParam;
		}
	}
	
	/**
	 * 保存
	 */
	function save(){
		$('#scrap_form').data('bootstrapValidator').validate();
		if(!$('#scrap_form').data('bootstrapValidator').isValid()){
			return false;
		}
		if(!validateParam()){
			return false;
		}
		var params = gatherParam();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/scrap/save",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(id){
				AlertUtil.showMessage("报废单保存成功!",'/aerialmaterial/scrap/apply?id='+id+'&pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 提交
	 */
	function doSubmit(){
		$('#scrap_form').data('bootstrapValidator').validate();
		if(!$('#scrap_form').data('bootstrapValidator').isValid()){
			return false;
		}
		if(!validateParam()){
			return false;
		}
		var params = gatherParam();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/scrap/submit",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(id){
				AlertUtil.showMessage("报废单提交成功!",'/aerialmaterial/scrap/apply?id='+id+'&pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 验证参数
	 */
	function validateParam(){
		if($("#list>tr:not(.non-choice)").length == 0){
			AlertUtil.showErrorMessage("请至少选择一个报废部件！");
			return false;
		}
		var result = true;
		$("#list>tr").each(function(){
			var tr = $(this);
			// 报废数量非空验证
			if(!tr.find("input[name='bfsl']").val()){
				result = false;
				AlertUtil.showErrorMessage("请填写件号为"+tr.find("[name='jh']").text()+"的报废数量");
				return false
			}
			// 仓库非空验证
			if(!tr.find("select[name='ck']").val()){
				result = false;
				AlertUtil.showErrorMessage("请选择仓库");
				return false
			}
			// 库位非空验证
			if(!tr.find("select[name='kwh']").val()){
				result = false;
				AlertUtil.showErrorMessage("请选择库位");
				return false
			}
			// 报废数量不可大于可用数量
			var bfsl = tr.find("input[name='bfsl']").val();
			var kysl = tr.find("input[name='kysl']").val();
			var kcsl = tr.find("[name='kcsl']").text();
			var scrapZt = $("#scrapZt").val();
			if(scrapZt != 5){
				if(parseFloat(bfsl) > parseFloat(kysl)){
					result = false;
					AlertUtil.showErrorMessage("件号为"+tr.find("[name='jh']").text()+"的报废数量不可大于可用数量");
					return false
				}
			}else{
				if(parseFloat(bfsl) + parseFloat(kysl)> parseFloat(kcsl)){
					result = false;
					AlertUtil.showErrorMessage("件号为"+tr.find("[name='jh']").text()+"的报废数量+可用数量不可大于库存数量");
					return false
				}
			}
		});
		if(!result){
			return false;
		}
		return result;
		
	}
	
	/**
	 * 初始化验证
	 */
	function initValidate(){
		$("#list input[name='bfsl']").live("keyup", function(){
			backspace($(this),/^([1-9][0-9]*)$/);
		});
		
		$('#scrap_form').bootstrapValidator({
	        message: '数据不合法',
	        excluded: [':disabled'],
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            bfrid: {
	                validators: {
	                    notEmpty: {
	                        message: '申请人不能为空'
	                    }
	                }
	            },
	        }
	    });
	}
	
	/**
	 * 获取提交参数
	 */
	function gatherParam(){
		// 报废单
		var scrap = {
				id : $("#scrapId").val(),
				dprtcode : $("#dprtcode").val(),
				bfrid : $("#bfrid").val(),
				bfbmid : $("#bfbmid").val(),
				bz : $("#bz").val(),
				bfyy : $("#bfyy").val()
		};
		// 报废单详情
		var details = [];
		$("#list>tr:not(.non-choice)").each(function(){
			var row = $(this);
			var obj = {
					id : row.find("[name='detailId']").val(),
					kcid : row.find("[name='kcid']").val(),
					bfsl : row.find("[name='bfsl']").val(),
					ckid : row.find("[name='ck']").val(),
					kwid : row.find("[name='kwh']").val()
			};
			details.push(obj);
		});
		scrap.hasScrappedList = details;
		
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
					,'hzm':responses[i].attachments[0].hzm
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
		scrap.attachments = attachments;
		return scrap;
	}
	
	/**
	 * 加载报废单数据
	 */
	function loadDetail(){
		var scrapId = $("#scrapId").val();
		if(scrapId){
			AjaxUtil.ajax({
				url : basePath+"/aerialmaterial/scrap/queryscrapdetail",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify({
					id : scrapId
				}),
				dataType:"json",
				success:function(list){
					if(list.length > 0){
						$(list).each(function(){
							var obj = this;
							obj.bjh = obj.hCMainData.bjh;
							obj.zwms = obj.hCMainData.zwms;
							obj.ywms = obj.hCMainData.ywms;
							obj.hclx = obj.hCMainData.hclx;
							obj.gljb = obj.hCMainData.gljb;
							obj.ckmc = obj.materialHistory.ckmc;
							obj.kwh = obj.yskw;
							obj.bfkcid = obj.kcid;
							obj.bfkwid = obj.kwid;
							obj.djsl = obj.bfsl;
							addScrapDetail(obj);
						});
					}
					$("#zt").val(DicAndEnumUtil.getEnumName('scrapStatusEnum',$("#scrapZt").val()));
					// 审核页面禁用输入框
					if($("#type").val() == 3 || $("#type").val() == 4){
						$("#sqr_div").html('<input class="form-control" disabled="disabled" type="text" value="'+($("#bfr").val()||"")+'">');
						$("#bfyy,#bz").attr("disabled","disabled");
						$(".notView").remove();
						$(".view").show();
						// 替换输入框、下拉框为文本
						StringUtil.replaceAsText("list");
						if($("#type").val() == 4){
							$("#spyj").attr("disabled","disabled");
							$(".view").remove();
						}
					}
				}
			});
			
			// 加载附件
			loadAttachments(scrapId);
		}
	}
	
	/**
	 * 审核通过
	 */
	function approve(){
		if(!$("#spyj").val()){
			AlertUtil.showErrorMessage("审核意见不能为空！");
			return false;
		}
		AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback:function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/scrap/auditApprove",
				type:"post",
				data:JSON.stringify({
						id : $("#scrapId").val(),
						spyj : $("#spyj").val()
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(id){
					finishWait();
					AlertUtil.showMessage("审核成功!",'/aerialmaterial/scrap/audit?id='+id+'&pageParam='+pageParam);
				}
			});
		}});
	}
	
	/**
	 * 审核驳回
	 */
	function reject(){
		if(!$("#spyj").val()){
			AlertUtil.showErrorMessage("审核意见不能为空！");
			return false;
		}
		AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback:function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/scrap/auditReject",
				type:"post",
				data:JSON.stringify({
					id : $("#scrapId").val(),
					spyj : $("#spyj").val()
			}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(id){
					finishWait();
					AlertUtil.showMessage("驳回成功!",'/aerialmaterial/scrap/audit?id='+id+'&pageParam='+pageParam);
				}
			});
		}});
	}
	
