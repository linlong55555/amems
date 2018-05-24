
	$(function(){
		// 初始化导航栏
		initNavigation();
		// 初始化时间控件
		initDateWidget();
		// 初始化验证
		initValidate();
		
		// 加载收货单数据
		loadDetail();
	});
	
	/**
	 * 状态枚举
	 */
	var ztMap = {
			1 : "保存",
			2 : "提交",
			8 : "作废",
			9 : "关闭",
			10 : "完成",
			11 : "撤销"
	}
	
	var jddxList = [];
	
	/**
	 * 初始化导航栏
	 */
	function initNavigation(){
		
		$("#shlx").val(1);
		var type = $("#type").val();
		if(type == 1){
			Navigation(menuCode,'新增','Add');
		}else if(type == 2){
			Navigation(menuCode,'修改','Edit');
		}else if(type == 3){
			Navigation(menuCode,'查看','View');
		}
	}
	
	/**
	 * 初始化时间控件
	 */
	function initDateWidget(){
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#receiptForm').data('bootstrapValidator')  
			     .updateStatus('shrq', 'NOT_VALIDATED',null)  
			     .validateField('shrq');  
	    });
	}
	
	/**
	 * 选择收货人
	 */
	function chooseShr(){
		//调用用户选择弹窗
		userModal.show({
			selected:$("#shrid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#shrid").val(data.id);
				$("#shr").val(data.displayName);
				$("#shbmid").val(data.dprt_bm?data.dprt_bm.id:"");
				$("#receiptForm").data('bootstrapValidator').resetForm(false); 
			},
			dprtcode:$("#dprtcode").val()
		})
	}
	
	/**
	 * 选择合同
	 */
	function chooseContract(){
		//调用合同选择弹窗
		contractModal.show({
			selected:$("#htid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#htid").val(data.id);
				$("#hth").val(data.htlsh);
				$("#fhdw").val(data.gysmc);
				$("#receiptForm").data('bootstrapValidator').resetForm(false); 
				deleteReceiptDetail();
			},
			dprtcode:$("#dprtcode").val(),
			type:$("#shlx").val()
		})
	}
	
	/**
	 * 选择借入申请
	 */
	function chooseBorrowApply(){
		//调用借入申请选择弹窗
		borrowApplyModal.show({
			selected:$("#jrsqid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#jrsqid").val(data.id);
				$("#jrsqbh").val(data.sqdh);
				deleteReceiptDetail();
			},
			dprtcode:$("#dprtcode").val()
		})
	}
	
	/**
	 * 选择未归还统计数据
	 */
	function chooseLendReturn(){
		//调用未归还统计数据弹窗
		lendReturnModal.show({
			selected:$("#bjid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				for(var i = 0; i < data.length; i++){
					addReceiptDetail({
						bjid : data[i].bjid,
						hcMainData : {
							bjh : data[i].hcMainData?data[i].hcMainData.bjh:"",
							zwms : data[i].hcMainData?data[i].hcMainData.zwms:"",
							ywms : data[i].hcMainData?data[i].hcMainData.ywms:"",
							gljb : data[i].hcMainData?data[i].hcMainData.gljb:""
						},
						yjid : data[i].bjid,
						yjMainData : {
							bjh : data[i].hcMainData?data[i].hcMainData.bjh:"",
							zwms : data[i].hcMainData?data[i].hcMainData.zwms:"",
							ywms : data[i].hcMainData?data[i].hcMainData.ywms:"",
							gljb : data[i].hcMainData?data[i].hcMainData.gljb:"",
							sjjh : data[i].hcMainData?data[i].hcMainData.bjh:""
						},
						sl : data[i].dghsl,
						zdsl : data[i].dghsl,
						isZj : 1,
						notOriginal : true
					});
					$("#onekey_btn").show();
				}
			},
			dprtcode:$("#dprtcode").val()
		})
	}
	
	/**
	 * 选择件号-航材主数据
	 */
	function chooseJh(){
		open_win_material_basic.show({
			multi:true,
			showStock:false,
			callback: function(data){//回调函数
				for(var i = 0; i < data.length; i++){
					addReceiptDetail({
						bjid : data[i].id,
						hcMainData : {
							bjh : data[i].bjh,
							zwms : data[i].zwms,
							ywms : data[i].ywms,
							gljb : data[i].gljb
						},
						sl : 1,
						isZj : 1,
						notOriginal : false
					});
				}
				$("#onekey_btn").show();
			},
			dprtcode:$("#dprtcode").val()
		},true);
	}
	
	/**
	 * 选择实际件号-航次主数据
	 */
	function chooseSjjh(btn){
		var tr = $(btn).parents("tr");
		open_win_material_basic.show({
			multi:false,
			selected:tr.find("[name='bjid']").val(),
			callback: function(data){//回调函数
				tr.find("[name='yjid']").val(tr.find("[name='bjid']").val());
				tr.find("[name='bjid']").val(data.id);
				tr.find("[name='sjjh']").val(data.bjh);
			},
			dprtcode:$("#dprtcode").val()
		},true);
	}
	
	/**
	 * 选择部件-合同/借入申请
	 */
	function chooseJhByDocument(){
		// 验证合同/借入申请是否选择
		var shlx = $("#shlx").val();
		if((shlx == 1 || shlx == 2) && !$("#htid").val()){
			AlertUtil.showErrorMessage("请先选择合同！");
			return false;
		}
		if(shlx == 3 && !$("#jrsqid").val()){
			AlertUtil.showErrorMessage("请先选择借入申请单！");
			return false;
		}
		componentModal.show({
			selected:$("#bjid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				for(var i = 0; i < data.length; i++){
					var param = {
							bjid : data[i].BJID,
							hcMainData : {
								bjh : data[i].BJH,
								zwms : data[i].ZWMS,
								ywms : data[i].YWMS,
								gljb : data[i].GLJB
							},
							sl : data[i].ZDSL,
							zdsl : data[i].ZDSL,
							xgdjid : data[i].ID,
							isZj : 1,
							notOriginal : shlx == 2
						};
					if(shlx == 2){
						param.initSn = data[i].SN||"";
						param.yjid = data[i].BJID;
						param.yjMainData = {
							bjh : data[i].BJH,
							zwms : data[i].ZWMS,
							ywms : data[i].YWMS,
							gljb : data[i].GLJB,
							sjjh : data[i].BJH
						};
					}
					addReceiptDetail(param);
					$("#onekey_btn").show();
				}
			},
			dprtcode:$("#dprtcode").val()
		})
	}
	
	/**
	 * 选择部件
	 */
	function chooseComponent(){
		var shlx = $("#shlx").val();
		// 采购合同/送修合同
		if(shlx == 1 || shlx == 2){
			chooseJhByDocument();
		}
		// 借入
		if(shlx == 3){
			var jrsqid = $("#jrsqid").val();
			// 选择了借入申请，带出件号
			if(jrsqid){
				chooseJhByDocument();
			}
			// 未选择借入申请，选择部件主数据
			else{
				chooseJh();
			}
		}
		// 借出归还
		if(shlx == 4){
			// 选择未归还统计数据
			chooseLendReturn();
		}
		// 其他
		if(shlx == 99){
			// 选择部件主数据
			chooseJh();
		}
	}
	
	/**
	 * 添加收货单详细
	 */
	function addReceiptDetail(obj){
		var bjid,yjid,jh,xlh,zwms,ywmc,gljb,zdsl,kwid,isZj,shsl,detailId,xgdjid,notOriginal,ckid,sjjh;
		
		yjid = obj.yjid;
		bjid = obj.bjid;
		if(obj.yjMainData && obj.yjMainData.bjh){
			jh = obj.yjMainData.bjh;
			zwms = obj.yjMainData.zwms;
			ywms = obj.yjMainData.ywms;
			gljb = obj.yjMainData.gljb;
			sjjh = obj.hcMainData.bjh;
		}else{
			jh = obj.hcMainData.bjh;
			zwms = obj.hcMainData.zwms;
			ywms = obj.hcMainData.ywms;
			gljb = obj.hcMainData.gljb;
		}
		kwid = obj.kwid;
		isZj = obj.isZj;
		shsl = parseInt(obj.sl);
		zdsl = parseInt(obj.zdsl);
		xlh = obj.sn||obj.pch||obj.initSn;
		detailId = obj.id;
		xgdjid = obj.xgdjid;
		notOriginal = obj.notOriginal;
		ckid = obj.ckid;
		
		$("#list>tr.non-choice").remove();
		addRow(shsl);
		
		var shlx = $("#shlx").val();
		
		function addRow(num){
			$("#list").append(["<tr>",
			                   ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
	                		   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteReceiptDetail(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			                   ($("#type").val()!=3)?"</td>":"",
			                   "<td style='text-align: left;vertical-align:middle;' name='jh' title='"+StringUtil.escapeStr(jh)+"'>"+StringUtil.escapeStr(jh)+"</td>",
			                   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(ywms)+"'>"+StringUtil.escapeStr(ywms)+"</td>",
			                   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(zwms)+"'>"+StringUtil.escapeStr(zwms)+"</td>",
			                   "<td style='text-align: center;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',gljb)+"'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',gljb)+"</td>",
			                   notOriginal?"<td style='text-align: center;vertical-align:middle;'>":"",
		                		   notOriginal?"<div class='input-group'><input class='form-control' name='sjjh' disabled='disabled' type='text' value='"+(sjjh||"")+"'>":"",
		            				   notOriginal?"<span class='input-group-btn'>":"",
			    						   notOriginal?"<button class='btn btn-primary form-control' type='button' onclick='chooseSjjh(this)'>":"",
										   notOriginal?"<i class='icon-search'></i>":"",
										   notOriginal?"</button>":"",
									   notOriginal?"</span>":"",
								   notOriginal?"</div>":"",
							   notOriginal? "</td>":"",
			                   "<td style='text-align: left;vertical-align:middle;'>",
			                   ((gljb==2||gljb==3)?"<input type='text' class='form-control input-sm' name='xlh' onkeyup='onkeyup4Code(this)' value='"+StringUtil.escapeStr(xlh)+"'>":"-"),
			                   "</td>",
			                   "<td style='text-align: left;vertical-align:middle;'>",
			                   		"<select class='form-control input-sm' name='ck' onchange='changeStore(this)'>"+buildStore(ckid)+"</select>",
			                   "</td>",
			                   "<td style='text-align: left;vertical-align:middle;'>",
			                   		"<select class='form-control input-sm' name='kwh'>"+buildStorage(ckid, kwid)+"</select>",
			                   "</td>",
			                   "<td style='text-align: right;vertical-align:middle;'>",
			                   		"<input type='text' class='form-control input-sm' name='shsl' "+(shlx==2||obj.sn?"disabled='disabled'":"")+" maxlength='8' value='"+(num||"")+"'>",
			                   "</td>",
			                   "<td style='text-align: center;vertical-align:middle;'>",
			                   		"<input type='checkbox' name='isZj' "+((isZj==1)?"checked='checked'":"")+" style='width:20px;height:20px;'>",
			                   "</td>",
			                   "<input type='hidden' name='gljb' value='"+gljb+"'>",
			                   "<input type='hidden' name='zdsl' value='"+(zdsl||"")+"'>",
			                   "<input type='hidden' name='bjid' value='"+bjid+"'>",
			                   "<input type='hidden' name='yjid' value='"+(yjid||"")+"'>",
			                   "<input type='hidden' name='detailId' value='"+(detailId||"")+"'>",
			                   "<input type='hidden' name='xgdjid' value='"+(xgdjid||"")+"'>",
			                   "</tr>"
			                   ].join(""));
		}
	}
	
	/**
	 * 删除收货单详细
	 * @param obj
	 */
	function deleteReceiptDetail(obj){
		if(obj){
			$(obj).parent().parent().remove();
		}else{
			$("#list>tr").remove();
		}
		if($("#list>tr").length == 0){
			var ths = $("#receipt_detail_table>thead>tr>th:visible").length;
			$("#list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 切换收货类型
	 */
	function switchType(){
		$("#hth").val("");
		$("#htid").val("");
		$("#fhdw").val("");
		var shlx = $("#shlx").val();
		$("#contract_div").hide();
		$("#borrowApply_div").hide();
		$("#shjh_th").hide();
		$("#jddx_div").hide();
		$("#fhdw_div").hide();
		$("#fhdw").attr("disabled","disabled");
		
		$("#receiptForm").data('bootstrapValidator').resetForm(false); 
		
		if(shlx == 1){
			$("#contract_div").show();
			$("#fhdw_div").show();
		}else if(shlx == 2){
			$("#contract_div").show();
			$("#shjh_th").show();
			$("#fhdw_div").show();
		}else if(shlx == 3){
			$("#borrowApply_div").show();
			$("#jddx_div").show();
			changeJddx();
		}else if(shlx == 4){
			$("#shjh_th").show();
			$("#jddx_div").show();
			changeJddx();
		}else if(shlx == 99){
			$("#fhdw_div").show();
			$("#fhdw").removeAttr("disabled");
		}
		deleteReceiptDetail();
	}
	
	/**
	 * 加载仓库下拉框
	 */
	function buildStore(ckid){
		var cklbs=[1];
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
		var cklbs=[1];
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
	 * 初始化借调对象
	 */
	function initJddx(){
		AjaxUtil.ajax({
			async: false,
			url : basePath+"/aerialmaterial/secondment/list/all",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify({
				dprtcode : $("#dprtcode").val()
			}),
			dataType:"json",
			success:function(data){
				jddxList = data;
				changeJddxlx();
			}
		});
	}
	
	/**
	 * 更改借调对象类型
	 */
	function changeJddxlx(){
		$("#jddx").empty();
		var htmlContent = '';
		var jddxlx = $("#jddxlx").val();
		for (var i = 0; i < jddxList.length; i++) {
			if(!jddxlx || jddxList[i].jddxlx == jddxlx){
				htmlContent +="	<option value='"+jddxList[i].id+"' >"+StringUtil.escapeStr(jddxList[i].jddxms)+"</option>";
			}
		}
		$("#jddx").html(htmlContent);
		$('#receiptForm').data('bootstrapValidator')  
	     .updateStatus('jddx', 'NOT_VALIDATED',null)  
	     .validateField('jddx');  
	}
	
	/**
	 * 初始化验证
	 */
	function initValidate(){
		$("#list input[name='shsl']").live("keyup", function(){
			backspace($(this),/^([1-9][0-9]*)$/);
		});
		
		$('#receiptForm').bootstrapValidator({
	        message: '数据不合法',
	        excluded: [':disabled'],
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	htid: {
	                validators: {
	                	callback: {
	                        message: '合同不能为空',
	                        callback: function(value, validator) {
	                			var shlx = $("#shlx").val();
	                			var htid = $("#htid").val();
	                			if(shlx == 1 || shlx == 2){
	                				if(htid){
	                					return true;
	                				}else{
	                					return false;
	                				}
	                			}
	                            return true;
	                        }
	                    }
	                }
	            },
	            shrid: {
	                validators: {
	                    notEmpty: {
	                        message: '收货人不能为空'
	                    }
	                }
	            },
	            shrq: {
	                validators: {
	                    notEmpty: {
	                        message: '收货日期不能为空'
	                    }
	                }
	            },
	            jddxlx: {
	                validators: {
	                    callback: {
	                        message: '借调对象类型不能为空',
	                        callback: function(value, validator) {
	                			var shlx = $("#shlx").val();
	                			var jddxlx = $("#jddxlx").val();
	                			if(shlx == 3 || shlx == 4){
	                				if(jddxlx){
	                					return true;
	                				}else{
	                					return false;
	                				}
	                			}
	                            return true;
	                        }
	                    }
	                }
	            },
	            jddx: {
	                validators: {
	                    callback: {
	                        message: '借调对象不能为空',
	                        callback: function(value, validator) {
	                			var shlx = $("#shlx").val();
	                			var jddx = $("#jddx").val();
	                			if(shlx == 3 || shlx == 4){
	                				if(jddx){
	                					return true;
	                				}else{
	                					return false;
	                				}
	                			}
	                            return true;
	                        }
	                    }
	                }
	            },
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
	 * 更改仓库
	 */
	function changeStore(select){
		$(select).parents("tr").find("select[name='kwh']").html(buildStorage($(select).val()));
	}
	
	/**
	 * 更改仓库
	 */
	function changeStoreByOnekey(){
		$("#kw").html(buildStorage($("#ck").val()));
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
	 * 保存
	 */
	function save(){
		$('#receiptForm').data('bootstrapValidator').validate();
		if(!$('#receiptForm').data('bootstrapValidator').isValid()){
			return false;
		}
		if(!validateParam()){
			return false;
		}
		var params = gatherParam();
		if(!params.paramsMap.success){
			return false;
		}
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/receipt/save",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(id){
				AlertUtil.showMessage("收货单保存成功!",'/aerialmaterial/receipt/main?id='+id+'&pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 提交
	 */
	function takeEffect(){
		$('#receiptForm').data('bootstrapValidator').validate();
		if(!$('#receiptForm').data('bootstrapValidator').isValid()){
			return false;
		}
		if(!validateParam()){
			return false;
		}
		var params = gatherParam();
		if(!params.paramsMap.success){
			return false;
		}
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/receipt/submit",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(id){
				AlertUtil.showMessage("收货单提交成功!",'/aerialmaterial/receipt/main?id='+id+'&pageParam='+pageParam);
			}
		});
	}
	
	/**
	 * 验证参数
	 */
	function validateParam(){
		if($("#list>tr:not(.non-choice)").length == 0){
			AlertUtil.showErrorMessage("请至少选择一个收货部件！");
			return false;
		}
		var result = true;
		var keyList = [];
		var slList = [];
		$("#list>tr").each(function(){
			var tr = $(this);
			// 收货数量非空验证
			if(!tr.find("input[name='shsl']").val()){
				result = false;
				AlertUtil.showErrorMessage("请填写件号为"+tr.find("[name='jh']").text()+"的收货数量");
				return false
			}
			// 序列号/批次号非空验证
			if(tr.find("input[name='gljb']").val() != 1 && !tr.find("td>input[name='xlh']").val()){
				result = false;
				AlertUtil.showErrorMessage("请填写件号为"+tr.find("[name='jh']").text()+"的序列号/批次号");
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
			
			// 统计收货数量
			var isContain = false;
			for(var i = 0; i < slList.length; i++){
				var bjid = slList[i].split("_")[0];
				var sl = parseInt(slList[i].split("_")[1]);
				var zdsl = parseInt(slList[i].split("_")[2]);
				if(bjid == tr.find("input[name='bjid']").val()){
					sl += parseInt(tr.find("input[name='shsl']").val());
					slList[i] = bjid + "_" + sl + "_" + zdsl;
					isContain = true;
				}
			}
			if(!isContain){
				slList.push(tr.find("input[name='bjid']").val() + "_" 
						+ tr.find("input[name='shsl']").val() + "_" 
						+ tr.find("input[name='zdsl']").val());
			}
			
			// 统计件号序列号数量
			if(tr.find("input[name='gljb']").val() == 3){
				var xlh = tr.find("td>input[name='xlh']").val();
				if(xlh.indexOf(",") == -1){
					keyList.push(tr.find("[name='jh']").text()+"_"+xlh);
				}else{
					var xlhList = xlh.split(",");
					for(var i = 0; i < xlhList.length; i++){
						keyList.push(tr.find("[name='jh']").text()+"_"+xlhList[i]);
					}
				}
			}
		});
		if(!result){
			return false;
		}
		
		// 验证序列号唯一
		if(isRepeat(keyList)){
			AlertUtil.showErrorMessage("收货单中包含重复的序列号");
			return false
		}
		
		// 收货数量不能大于单据数量验证
		for(var i = 0; i < slList.length; i++){
			var bjid = slList[i].split("_")[0];
			var sl = parseInt(slList[i].split("_")[1]);
			var zdsl = parseInt(slList[i].split("_")[2]);
			if(sl > zdsl){
				var jh = $("#list input[name='bjid'][value='"+bjid+"']:first").parent().find("[name='jh']");
				var jhstr = jh.text();
				AlertUtil.showErrorMessage("件号为"+jhstr+"的收货数量为"+sl+"不能大于单据数量"+zdsl);
				result = false;
			}
		}
		if(!result){
			return false;
		}
		
		return result;
		
	}
	
	/**
	 * 获取提交参数
	 */
	function gatherParam(){
		var shlx = $("#shlx").val();
		// 收货单主单
		var receipt = {
				id : $("#receiptId").val(),
				dprtcode : $("#dprtcode").val(),
				shlx : shlx,
				shrid : $("#shrid").val(),
				shbmid : $("#shbmid").val(),
				shrq : $("#shrq").val(),
				fhdw : $("#fhdw").val(),
				bz : $("#bz").val(),
				paramsMap : {
					success : true
				}
		};
		// 借调对象
		if($("#jddx").is(":visible")){
			receipt.jddxid = $("#jddx").val();
			receipt.jdfzr = $("#jdfzr").val();
		}
		// 相关单据
		if(shlx == 1 || shlx == 2){
			receipt.xgdjid = $("#htid").val();
		}else if(shlx == 3){
			receipt.xgdjid = $("#jrsqid").val();
		}
		// 收货单详情
		var details = [];
		$("#list>tr:not(.non-choice)").each(function(){
			var row = $(this);
			var obj = {
					id : row.find("[name='detailId']").val(),
					bjid : row.find("[name='bjid']").val(),
					kwid : row.find("[name='kwh']").val(),
					sl : row.find("[name='shsl']").val(),
					isZj : row.find("[name='isZj']").is(':checked')?1:0,
					yjid : row.find("[name='yjid']").val(),
					xgdjid : row.find("[name='xgdjid']").val()
			};
			// 批次号管理
			if(row.find("[name='gljb']").val() == "2"){
				obj.pch = row.find("[name='xlh']").val();
			}
			// 序列号管理
			else if(row.find("[name='gljb']").val() == "3"){
				obj.sn = row.find("[name='xlh']").val();
			}
			
			// 拆分序列号管理件的数量
			if(row.find("[name='gljb']").val() == "3"){
				var snList = obj.sn.split(",");
				var sl = obj.sl;
				if(snList.length != obj.sl){
					AlertUtil.showErrorMessage("件号为"+row.find("[name='jh']").text()+"的序列号数量和收货数量不一致");
					receipt.paramsMap.success = false;
					return receipt;
				}
				for(var i = 0; i < sl; i++){
					var cloneObj = JSON.parse(JSON.stringify(obj));
					cloneObj.sl = 1;
					cloneObj.sn = snList[i];
					if(cloneObj.sn.length > 50){
						AlertUtil.showErrorMessage("件号为"+row.find("[name='jh']").text()+"的序列号长度过长（最大50）");
						receipt.paramsMap.success = false;
					}
					details.push(cloneObj);
				}
			}else{
				if(obj.pch && obj.pch.length > 50){
					AlertUtil.showErrorMessage("件号为"+row.find("[name='jh']").text()+"的批次号长度过长（最大50）");
					receipt.paramsMap.success = false;
				}
				details.push(obj);
			}
		});
		receipt.details = details;
		return receipt;
	}
	
	/**
	 * 加载收货单数据
	 */
	function loadDetail(){
		// 默认收货人
		$("#shr").val(currentUser.displayName);
		$("#shrid").val(currentUser.id);
		$("#shbmid").val(currentUser.dprt_bm?currentUser.dprt_bm.id:"");
		
		// 默认收货日期
		$("#shrq").val(getNowFormatDate());
		
		// 加载借调对象
		initJddx();
		
		var receiptId = $("#receiptId").val();
		if(receiptId){
			AjaxUtil.ajax({
				url : basePath+"/aerialmaterial/receipt/loadDetail",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify({
					id : receiptId
				}),
				dataType:"json",
				success:function(data){
					$("#shlx").val(data.shlx);
					// 切换收货类型
					switchType();
					if(data.shlx == 1 || data.shlx == 2){
						$("#htid").val(data.xgdjid);
						$("#hth").val(data.xgdjbh);
					}else if(data.shlx == 3){
						$("#jrsqid").val(data.xgdjid);
						$("#jrsqbh").val(data.xgdjbh);
					}
					$("#shr").val(data.shr.displayName);
					$("#shrid").val(data.shrid);
					$("#shbmid").val(data.shbmid);
					$("#shrq").val((data.shrq||"").substr(0,10));
					$("#fhdw").val(data.fhdw);
					$("#bz").val(data.bz);
					$("#jdfzr").val(data.jdfzr);
					$("#jddxlx").val(data.secondment?data.secondment.jddxlx:"");
					$("#jddx").val(data.jddxid);
					if(data.details.length > 0){
						$(data.details).each(function(){
							if(data.shlx == 2 || data.shlx == 4){
								this.notOriginal = true;
							}
							addReceiptDetail(this);
						});
						$("#onekey_btn").show();
					}
					if($("#type").val() == 2){
						$("#shlx").attr("disabled","disabled");
					}
					
					if($("#type").val() == 3){
						// 隐藏查看不必要内容
						$("#receiptForm input").attr("disabled","disabled");
						$("#receiptForm select").attr("disabled","disabled");
						$("#receiptForm textarea").attr("disabled","disabled");
						$("#receiptForm .input-group button").attr("disabled","disabled");
						$("#saveBtn").hide();
						$("#submitBtn").hide();
						$("#confirmBtn").hide();
						$(".notView").remove();
						$("#shr_div").html('<input class="form-control" disabled="disabled" type="text" value="'+(data.shr.displayName||"")+'">');
						$("#ht_div").html('<input class="form-control" disabled="disabled" type="text" value="'+(data.xgdjbh||"")+'">');
						$("#jrsq_div").html('<input class="form-control" disabled="disabled" type="text" value="'+(data.xgdjbh||"")+'">');
						
						// 替换输入框、下拉框为文本
						$("#list input:visible").each(function(){
							var content;
							if(this.type == "text"){
								content = $(this).val();
							}else if(this.type == "checkbox"){
								content = this.checked ? "是" : "否";
							}
							$(this).parent().attr("title",content);
							$(this).parent().text(content);
						});
						$("#list select:visible").each(function(){
							var content = $(this).find("option:selected").text();
							$(this).parent().attr("title",content);
							$(this).parent().text(content);
						});
					}
					
					if($("#type").val() == 2 || $("#type").val() == 3){
						$("#shdh").val(data.shdh);
						$("#zt").val(ztMap[data.zt]);
						$("#zdr").val(data.zdr.displayName);
						$("#zdsj").val(data.zdsj);
						$("#shdh_div").show();
						$("#zt_div").show();
						$("#zdr_div").show();
						$("#zdsj_div").show();
					}
				}
			});
		}
		
		function getNowFormatDate() {
		    var date = new Date();
		    var seperator = "-";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator + month + seperator + strDate
		    return currentdate;
		} 
	}
	
	/**
	 * 返回主页面
	 */
	function goToMainPage(){
		window.location = basePath+'/aerialmaterial/receipt/main?pageParam='+pageParam;
	}
	
	/**
	 * 序列号输入验证
	 * @param obj
	 */
	function onkeyup4Code(obj){
		var reg = new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,999}$");
	     obj.value = validate(obj.value);
	     function validate(_value){
	    	 if(_value.length == 0){
	    		 return "";
	    	 }
	    	 if(!reg.test(_value)){
	    		return validate(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	}
	
	/**
	 * 判断数组是否重复
	 * @param arr
	 * @returns {Boolean}
	 */
	function isRepeat(arr){
		var hash = {};
		for(var i in arr) {
			if(hash[arr[i]])
				return true;
			hash[arr[i]] = true;
		}
		return false;
	} 
	
	/**
	 * 显示一键填写仓库窗口
	 */
	function showAutoFillStoreModal(){
		$("#ck").html(buildStore());
		$("#kw").html(buildStorage());
		$("#oneKeyModal").modal("show");
	}
	
	/**
	 * 一键填写仓库
	 */
	function autoFillStore(){
		$("#list select[name='ck']").val($("#ck").val());
		$("#list select[name='ck']").trigger("change");
		$("#list select[name='kwh']").val($("#kw").val());
	}
	
	/**
	 * 借调对象改变事件
	 */
	function changeJddx(){
		var jddx = $("#jddx").val();
		if(jddx != ""){
			$("#fhdw").val($("#jddx").find("option:selected").text());
		}else{
			$("#fhdw").val("");
		}
	}
	
	//回车事件控制
	document.onkeydown = function(event){
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				 //调用主列表页查询
			}
		}
	};