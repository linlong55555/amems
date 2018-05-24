var zts=[];
zts[1]="待处理";
zts[2]="处理中";
zts[3]="完成";
$(function(){
	Navigation(menuCode,'查看需求', 'Select View');// 初始化导航
	$("#sqr").val(currentUser.displayName);
	// 初始化数据字典和枚举
//	initDicAndEnum();
	// 初始化飞机注册号
	//initAcReg();
	// 填充数据
	fillData();
	// 显示按钮
//	showButton();
	// 初始化验证
//	initValidation();
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
function loadAcRegData(){
	
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
					$("#xqlb").val(obj.xqlb);
					$("#jhsyrq").val((obj.jhsyrq || "").substr(0, 10));
					$("#sjbjh").val(obj.sjbjh);
					$("#sjbjmc").val(obj.sjbjmc);
				
					var fjfc='';
					if(obj.fjzch == null){
						fjfc='N/A';					
					}else{
						fjfc=obj.fjzch;
					}
					$("#fjzch").val(fjfc);
					$("#xlh").val(obj.xlh);
					$("#fxsj").val(TimeUtil.convertToHour(obj.fxsj, TimeUtil.Separator.COLON));
					$("#fxxh").val(obj.fxxh);
					$("#xqyy").val(obj.xqyy);
					$("#gmjy").val(obj.gmjy);
					$("#dprtId").val(obj.dprtcode);
					$("#lybh").val(obj.lybh);
					
					if(obj.sqr){
						$("#sqr").val(obj.sqr.displayName);
					}
					$("#sqsj").val((obj.sqsj || "").substr(0, 10));
					$("#pzr").val(obj.jhr.displayName);
					$("#pzrid").val(obj.jhrid);
					$("#pzbmid").val(obj.jhbmid);
					$("#pzsj").val((obj.jhsj || "").substr(0, 10));
					
					$("#material_info_list").html('<tr class="no-data"><td class="text-center" colspan="13" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
					if(obj.details && obj.details.length > 0){
						MaterialInfo.set(obj.details);
						$.each(obj.details, function(i, detail){
							modifyMaterialInfoTable(detail);
						});
					}
				}else{
					AlertUtil.showErrorMessage("查询不到相关数据！");
				}
			}
		});
	}
}

//物料信息
var MaterialInfo = {
	data : [],
	merge : function(obj){
		var insert = true;
		var list = this.data;
		for(var i = 0; i < list.length; i++){
			if(list[i].rowid == obj.rowid){
				list[i] = obj;
				insert = false;
			}
		}
		if(insert){
			list.push(obj);
		}
	},
	remove : function(rowid){
		var list = this.data;
		for (var i = 0; i < list.length; i++) {
            if (list[i].rowid == rowid) {
            	list.splice(i, 1);
            }
        }
	} ,
	set : function(list){
		this.data = list;
	},
	get : function(rowid){
		var result;
		var list = this.data;
		for (var i = 0; i < list.length; i++) {
            if (list[i].rowid == rowid) {
            	result = list[i];
            }
        }
		return result;
	},
	getAll : function(){
		return this.data;
	},
};

/**
 * 更新物料信息列表
 * @param obj
 */
function modifyMaterialInfoTable(obj){
	if(!obj.rowid){	// 新增
		obj.rowid = Math.uuid().toLowerCase();
		$("#material_info_list>.no-data").remove();
		$("#material_info_list").append(["<tr rowid='"+obj.rowid+"'>",
		       "<td title='"+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb)+"' name='wllb'>"+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bjh)+"' name='bjh'>"+StringUtil.escapeStr(obj.bjh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bjmc)+"' name='bjmc'>"+StringUtil.escapeStr(obj.bjmc)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xingh)+"' name='xingh'>"+StringUtil.escapeStr(obj.xingh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.gg)+"' name='gg'>"+StringUtil.escapeStr(obj.gg)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xlh)+"' name='xlh'>"+StringUtil.escapeStr(obj.xlh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xqsl)+"' name='xqsl'>"+StringUtil.escapeStr(obj.xqsl)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.jhly)+"' name='jhly'>"+StringUtil.escapeStr(obj.jhly)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.zjhms)+"' name='zjhms'>"+StringUtil.escapeStr(obj.zjhms)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bzjh)+"' name='bzjh'>"+StringUtil.escapeStr(obj.bzjh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.thj)+"' name='thj'>"+StringUtil.escapeStr(obj.thj)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bzzt)+"' name='bzzt'>"+StringUtil.escapeStr(zts[obj.paramsMap?obj.paramsMap.bzzt:''])+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bzbz)+"' name='bzbz'>"+StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.bzbz:'')+"</td>",
		       "<input type='hidden' name='id' value='"+StringUtil.escapeStr(obj.id)+"'>",
		       "<input type='hidden' name='bjid' value='"+StringUtil.escapeStr(obj.bjid)+"'>",
		       "<input type='hidden' name='zjh' value='"+StringUtil.escapeStr(obj.zjh)+"'>",
		       "</tr>"
		       ].join(""));
	}else{	// 修改
		var row = $("tr[rowid='"+obj.rowid+"']");
		row.find("[name='wllb']").text(DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb));
		row.find("[name='wllb']").attr("title",DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb));
		row.find("[name='bjh']").text(obj.bjh || "");
		row.find("[name='bjh']").attr("title",obj.bjh || "");
		row.find("[name='bjmc']").text(obj.bjmc || "");
		row.find("[name='bjmc']").attr("title",obj.bjmc || "");
		row.find("[name='xingh']").text(obj.xingh || "");
		row.find("[name='xingh']").attr("title",obj.xingh || "");
		row.find("[name='gg']").text(obj.gg || "");
		row.find("[name='gg']").attr("title",obj.gg || "");
		row.find("[name='xlh']").text(obj.xlh || "");
		row.find("[name='xlh']").attr("title",obj.xlh || "");
		row.find("[name='xqsl']").text(obj.xqsl || "");
		row.find("[name='xqsl']").attr("title",obj.xqsl || "");
		row.find("[name='jhly']").text(obj.jhly || "");
		row.find("[name='jhly']").attr("title",obj.jhly || "");
		row.find("[name='zjhms']").text(obj.zjhms || "");
		row.find("[name='zjhms']").attr("title",obj.zjhms || "");
		row.find("[name='bzjh']").text(obj.bzjh || "");
		row.find("[name='bzjh']").attr("title",obj.bzjh || "");
		row.find("[name='thj']").text(obj.thj || "");
		row.find("[name='thj']").attr("title",obj.thj || "");
		
		row.find("[name='bjid']").val(obj.bjid||"");
		row.find("[name='zjh']").val(obj.zjh||"");
		row.find("[name='bzzt']").val(obj.bzzt||"");
		row.find("[name='bzbz']").val(obj.bzbz||"");
	}
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
            sjbjh: {
                validators: {
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
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
				$('#jjcd').removeAttr("checked");
				$('#isYxfx').removeAttr("checked");
				$('#isFjhtc').removeAttr("checked");
				$("#bh").val("");
				$("#zt").val("");
				$("#ztms").val("");
				$("#xqlb").val("");
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
				
				MaterialInfo.set([]);
				$("#material_info_list").html('<tr class="no-data"><td class="text-center" colspan="13" title="暂无数据 No data.">暂无数据 No data.</td></tr>');	
			}
			$('#demand_form').data('bootstrapValidator').resetForm(false);
			showButton();
		}
	},true);
}