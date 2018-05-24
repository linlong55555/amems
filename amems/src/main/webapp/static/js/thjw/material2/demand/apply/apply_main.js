
$(function(){
	Navigation(menuCode,"","");// 初始化导航
	$("input[name='date-picker']").datepicker({
		autoclose : true,
		clearBtn : true
	});
	$("#sqr").val(currentUser.displayName);
	// 初始化数据字典和枚举
	initDicAndEnum();
	// 刷新权限
	refreshPermission();
	// 初始化飞机注册号
	initAcReg();
	// 填充数据
	fillData();
	// 显示按钮
	showButton();
	// 初始化验证
	initValidation();
});
function customResizeHeight(bodyHeight, tableHeight){
	$("#demand_apply_body").removeAttr("style");
	var panelFooter=$("#demand_apply_body").siblings(".panel-footer").outerHeight()||0
	/*var fixedDiv=$("#fixedDiv").outerHeight()||0;*/
	var panelBody=bodyHeight-panelFooter;
	$("#demand_apply_body").css({"height":(panelBody+10)+"px","overflow":"auto"});
}

function removeTr(obj){
	$(obj).parent("td").parent("tr").remove();
}

/**
 * 初始化飞机注册号
 * @returns
 */
function initAcReg(){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtId").val()});
 	var planeRegOption = '<option value="">N/A</option>';
 	if(planeDatas != null && planeDatas.length >0){
 		$.each(planeDatas, function(i, planeData){
			planeRegOption 
				+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' " +
						"jx='"+StringUtil.escapeStr(planeData.FJJX)+"' "+
						"xlh='"+StringUtil.escapeStr(planeData.XLH)+"'>"
				+ StringUtil.escapeStr(planeData.FJZCH)
				+ "</option>";
 		});
 	}
	$("#fjzch").html(planeRegOption);
}

/**
 * 加载飞机数据
 */
function loadAcRegData(info){
	
	var fjzch = $("#fjzch").val();
	var xlh = $("#fjzch option:selected").attr("xlh") || "";
	$("#xlh").val(xlh);
	
	if(fjzch){
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/demand/acinfo",
			type:"post",
			data:JSON.stringify({
				fjzch : fjzch,
				dprtcode : $("#dprtId").val()
			}),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				finishWait();
				$("#fxsj").val("0");
				$("#fxxh").val("0");
				$.each(data || [], function(i, ac){
					var total = parseInt(ac.csz || 0) + parseInt(ac.ljz || 0);
					if(ac.jklbh == "2_10_FH"){
						$("#fxsj").val(TimeUtil.convertToHour(total, TimeUtil.Separator.COLON));
					}else if(ac.jklbh == "3_10_FC"){
						$("#fxxh").val(total);
					}
				});
			}
		});
	}else{
		$("#fxsj").val("");
		$("#fxxh").val("");
	}
	
	info = info || {};
	$("#lylx").val(info.lylx);
	$("#lyid").val(info.lyid);
	$("#lybh").val(info.lybh);
	$("#bs145").val(info.bs145);
}

/**
 * 初始化数据字典和枚举
 */
function initDicAndEnum(){
	DicAndEnumUtil.registerDic("84", "xqlb", $("#dprtId").val());
	DicAndEnumUtil.registerDic("1", "info_dw", $("#dprtId").val());
}

/**
 * 选择上级件号
 */
function chooseParentMaterial(){
	open_win_material_basic.show({
		multi:false,
		showStock:false,
		callback: function(data){//回调函数
			if(data){
				$("#sjbjh").val(data.bjh);
				$("#sjbjmc").val((data.ywms||"") + " " + (data.zwms||""));
			}else{
				$("#sjbjh").val("");
				$("#sjbjmc").val("");
			}
		},
		dprtcode:$("#dprtId").val()
	},true);
}

/**
 * 选择批准人
 */
function chooseApprover(){
	Personel_Tree_Multi_Modal.show({
		checkUserList:[{id:$("#pzrid").val(),displayName:$("#pzr").val()}],//原值，在弹窗中回显
		dprtcode:$("#dprtId").val(),
		multi:false,
		callback: function(data){//回调函数
			var user = $.isArray(data)?data[0]:{id:'',displayName:'',bmdm:''};
			$("#pzrid").val(user.id);
			$("#pzr").val(user.displayName);
			$("#pzbmid").val(user.bmdm);
		}
	});
	AlertUtil.hideModalFooterMessage();
}

/**
 * 选择来源-135
 */
function chooseSource135(){
	var this_ = this;
	open_win_demand_source.show({
		multi : false,
		type : "135",
		loaded : false,
		cleanData : true,
		selected : $("#lyid").val(),
		fjzch : $("#fjzch").val(),
		dprtcode : $("#dprtId").val(),
		callback: function(data){//回调函数
			$("#lylx").val(data.lylx);
			$("#lyid").val(data.lyid);
			$("#lybh").val(data.lybh);
			$("#bs145").val(data.bs145);
		}
	},true);
}

/**
 * 选择来源-145
 */
function chooseSource145(){
	var this_ = this;
	open_win_demand_source.show({
		multi : false,
		type : "145",
		loaded : false,
		cleanData : true,
		selected : $("#lyid").val(),
		fjzch : $("#fjzch").val(),
		dprtcode : $("#dprtId").val(),
		callback: function(data){//回调函数
			$("#lylx").val(data.lylx);
			$("#lyid").val(data.lyid);
			$("#lybh").val(data.lybh);
			$("#bs145").val(data.bs145);
		}
	},true);
}

/**
 * 获取数据
 */
function getData(){
	var obj = {};
	obj.id = $.trim($("#id").val());
	obj.jjcd = $('#jjcd').is(":checked") ? 9 : 1;
	obj.isYxfx = $('#isYxfx').is(":checked") ? 1 : 0;
	obj.isFjhtc = $('#isFjhtc').is(":checked") ? 1 : 0;
	obj.xqlb = $.trim($("#xqlb").val()) || null;
	obj.jhsyrq = $.trim($("#jhsyrq").val()) || null;
	obj.sjbjh = $.trim($("#sjbjh").val()) || null;
	obj.sjbjmc = $.trim($("#sjbjmc").val()) || null;
	obj.fjzch = $.trim($("#fjzch").val()) || null;
	obj.xlh = $.trim($("#xlh").val()) || null;
	obj.fxsj = TimeUtil.convertToMinute($("#fxsj").val()) || null;
	obj.fxxh = $.trim($("#fxxh").val()) || null;
	obj.xqyy = $.trim($("#xqyy").val()) || null;
	obj.gmjy = $.trim($("#gmjy").val()) || null;
	obj.dprtcode = $.trim($("#dprtId").val());
	obj.jhrid = $.trim($("#pzrid").val());
	obj.jhbmid = $.trim($("#pzbmid").val());
	
	obj.lylx = $.trim($("#lylx").val());
	obj.lyid = $.trim($("#lyid").val());
	obj.lybh = $.trim($("#lybh").val());
	obj.bs145 = $.trim($("#bs145").val());
	obj.details = MaterialInfo.getAll();
	return obj;
}

/**
 * 验证数据
 */
function validateData(){
	AlertUtil.hideModalFooterMessage();
	$('#demand_form').data('bootstrapValidator').resetForm(false);
	$('#demand_form').data('bootstrapValidator').validate();
	if(!$('#demand_form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		return false;
	}
	var details = MaterialInfo.getAll();
	if(!details || details.length == 0){
		AlertUtil.showModalFooterMessage("请至少添加一条物料信息！");
		return false;
	}
	return true;
}

/**
 * 保存数据
 */
function saveData(){
	if(!validateData()){
		return false;
	}
	var obj = getData();
	obj.zt = 1;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/material/demand/save",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			finishWait();
			$("#id").val(data.id);
			fillData();
			AlertUtil.showMessage("保存成功！");
		}
	});
}

/**
 * 提交数据
 */
function submitData(){
	if(!validateData()){
		return false;
	}
	var obj = getData();
	obj.zt = 2;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/material/demand/submit",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			finishWait();
			$("#id").val(data.id);
			fillData();
			AlertUtil.showMessage("提交成功！");
		}
	});
}

/**
 * 根据id填充数据
 * @param id
 */
function fillData(){
	var id = $("#id").val();
	if(id){
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/demand/detail",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(obj){
				finishWait();
				if(obj){
					obj.jjcd == 1 ?  $('#jjcd').removeAttr("checked") : $('#jjcd').attr("checked","checked");
					obj.isYxfx == 0 ?  $('#isYxfx').removeAttr("checked") : $('#isYxfx').attr("checked","checked");
					obj.isFjhtc == 0 ?  $('#isFjhtc').removeAttr("checked") : $('#isFjhtc').attr("checked","checked");
					$("#bh").val(obj.bh);
					$("#zt").val(obj.zt);
					$("#ztms").val(DicAndEnumUtil.getEnumName('demandStatusEnum', obj.zt));
					if(obj.zt == 6){
						$("#jhyj_label").show();
						$("#jhyj").text(obj.jhyj);
					}else{
						$("#jhyj_label").hide();
						$("#jhyj").text("");
					}
					$("#xqlb").val(obj.xqlb);
					$("#jhsyrq").val((obj.jhsyrq || "").substr(0, 10));
					$("#jhsyrq").datepicker("update");
					$("#sjbjh").val(obj.sjbjh);
					$("#sjbjmc").val(obj.sjbjmc);
					$("#fjzch").val(obj.fjzch);
					$("#xlh").val(obj.xlh);
					$("#fxsj").val(TimeUtil.convertToHour(obj.fxsj, TimeUtil.Separator.COLON));
					$("#fxxh").val(obj.fxxh);
					$("#xqyy").val(obj.xqyy);
					$("#gmjy").val(obj.gmjy);
					$("#dprtId").val(obj.dprtcode);
					
					if(obj.sqr){
						$("#sqr").val(obj.sqr.displayName);
					}
					$("#sqsj").val(obj.sqsj);
					$("#pzr").val(obj.jhr.displayName);
					$("#pzrid").val(obj.jhrid);
					$("#pzbmid").val(obj.jhbmid);
					$("#pzsj").val(obj.jhsj);
					
					$("#lylx").val(obj.lylx);
					$("#lyid").val(obj.lyid);
					$("#lybh").val(obj.lybh);
					$("#bs145").val(obj.bs145);
					
					$("#material_info_list").html('<tr class="no-data"><td class="text-center" colspan="12" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
					if(obj.details && obj.details.length > 0){
						MaterialInfo.set(obj.details);
						$.each(obj.details, function(i, detail){
							modifyMaterialInfoTable(detail);
						});
					}
					showButton();
				}else{
					AlertUtil.showErrorMessage("查询不到相关数据！");
				}
			}
		});
	}else if(jsonData){
		fillDataWithJson(jsonData);
	}
}

/**
 * 根据json填充页面
 */
function fillDataWithJson(json){
	
	var this_ = this;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/material/demand/builddetailbymaterialtool",
		type:"post",
		data:json,
		dataType:"json",
		contentType: "application/json;charset=utf-8",
		success:function(data){
			finishWait();
			
			$("#fjzch").val(data.fjzch == "-" ? null : data.fjzch);
			this_.loadAcRegData(data);
			
			MaterialInfo.set(data.details);
			data.details && $(data.details).each(function(i, detail){
				modifyMaterialInfoTable(detail);
			});
		}
	});
}

/**
 * 显示按钮
 */
function showButton(){
	$("#save_btn").hide()
	$("#submit_btn").hide()
	
	var zt = $("#zt").val();
	if(!zt || zt == 1){
		$("#save_btn").show();
	}
	if(!zt || zt == 1 || zt == 2 || zt == 4 || zt == 6){
		$("#submit_btn").show();
	}
}

/**
 * 链接至我的需求单
 */
function toMyDemand(){
	window.open(basePath + "/material/demand/tracking");
}

/**
 * 初始化验证
 */
function initValidation(){
	$('#demand_form').bootstrapValidator({
        message: '数据不合法',
        excluded: [':disabled'],
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xqlb: {
                validators: {
                    notEmpty: {
                        message: '需求类别不能为空'
                    },
                }
            },
            sjbjid: {
                validators: {
                	callback: {
                        message: '不能输入中文',
                        callback: function(value, validator) {
                        	if(!/^[\x00-\xff]*$/.test($("#sjbjh").val())){
                        		return false;
                        	}
                            return true;
                        }
                    }
                }
            },
            sjbjh: {
                validators: {
                    callback: {
                        message: '不能输入中文',
                        callback: function(value, validator) {
                        	$('#demand_form').data('bootstrapValidator')  
                    		.updateStatus('sjbjid', 'NOT_VALIDATED')  
                    	    .validateField('sjbjid'); 
                            return true;
                        }
                    }
                }
            },
            pzrid: {
                validators: {
                	notEmpty: {
                        message: '批准人不能为空'
                    }
                }
            },
            fxsj: {
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
                        message: '时间格式不正确'
                    }
                }
            },
            fxxh: {
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9]\d*)$/,
                        message: '循环格式不正确'
                    }
                }
            },
        }
    });
}

/**
 * 选择需求单
 */
function chooseDemand(){
	open_win_demand.show({
		multi : false,
		loaded : false,
		clearProject: true,
		dprtcode:$("#dprtId").val(),
		id : $("#id").val(),
		callback: function(data){//回调函数
			if(data.id){
				$("#id").val(data.id);
				fillData();
			}else{
				$("#id").val("");
				$('#jjcd').removeAttr("checked");
				$('#isYxfx').removeAttr("checked");
				$('#isFjhtc').removeAttr("checked");
				$("#bh").val("");
				$("#zt").val("");
				$("#ztms").val("");
				$("#jhyj_label").hide();
				$("#jhyj").text("");
				$("#xqlb option:first").prop("selected", "selected");
				$("#jhsyrq").val("");
				$("#sjbjh").val("");
				$("#sjbjmc").val("");
				$("#fjzch").val("");
				$("#xlh").val("");
				$("#fxsj").val("");
				$("#fxxh").val("");
				$("#xqyy").val("");
				$("#gmjy").val("");
				
				$("#sqr").val(currentUser.displayName);
				$("#sqsj").val("");
				$("#pzr").val("");
				$("#pzrid").val("");
				$("#pzbmid").val("");
				$("#pzsj").val("");
				
				$("#lylx").val("");
				$("#lyid").val("");
				$("#lybh").val("");
				$("#bs145").val("");
				
				showButton();
				MaterialInfo.set([]);
				$("#material_info_list").html('<tr class="no-data"><td class="text-center" colspan="12" title="暂无数据 No data.">暂无数据 No data.</td></tr>');	
			}
			$('#demand_form').data('bootstrapValidator').resetForm(false);
		}
	},true);
}