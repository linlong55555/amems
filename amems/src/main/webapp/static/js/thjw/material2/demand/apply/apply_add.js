
// 物料信息
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

$(function(){
	// 章节号自动补全
	initAutoComplete();
	// 初始化验证
	initDetailValidation();
	// 重置表单验证
	$("#demand_apply_add_alert").on('shown.bs.modal', function (e) {
		$('#demand_detail_form').data('bootstrapValidator').resetForm(false); 
	});
});

/**
 * 选择件号
 */
function chooseChildMaterial(){
	var existsIdList = [];
	$.each(MaterialInfo.getAll(), function(i, detail){
		if(detail.bjid){
			existsIdList.push(detail.bjid);
		}
	});
	open_win_material_basic.show({
		multi:false,
		showStock:false,
		selected : $("#info_bjid").val(),
		search_hclx : $("#info_wllb").val(),
		//existsIdList : existsIdList,
		callback: function(data){//回调函数
			if(data){
				$("#info_wllb").val(data.hclx);
				$("#info_bjh").val(data.bjh);
				$("#info_bjid").val(data.id);
				$("#info_bjmc").val(formatUndefine(data.ywms) + " " + formatUndefine(data.zwms));
				$("#info_xingh").val(data.xingh);
				$("#info_gg").val(data.gg);
				$("#info_jhly").val(data.jhly);
				$("#info_zjh").val(data.zjh);
				$("#info_zjhms").val(data.fixChapter ? data.fixChapter.displayName : "");
				$("#info_bzjh").val(data.bzjh);
				$("#info_dw").val(data.jldw);
			}else{
				$("#info_wllb").val("1");
				$("#info_bjh").val("");
				$("#info_bjid").val("");
				$("#info_bjmc").val("");
				$("#info_xingh").val("");
				$("#info_gg").val("");
				$("#info_jhly").val("");
				$("#info_zjh").val("");
				$("#info_zjhms").val("");
				$("#info_bzjh").val("");
				$("#info_dw option:first").prop("selected", 'selected');
			}
			// 判断物料信息是否可以输入
			disableChildMaterial();
		},
		dprtcode:$("#dprtId").val()
	},true);
}

//加载航材数据
function loadMaterialData(){
	AjaxUtil.ajax({
		url:basePath+"/material/material/selectByBjhAndDprcode",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify({
			bjh : $("#info_bjh").val(),
			dprtcode : $("#dprtId").val()
		}),
		success:function(data){
			finishWait();
			if(data.hCMainData){
				data = data.hCMainData;
				$("#info_wllb").val(data.hclx);
				$("#info_bjh").val(data.bjh);
				$("#info_bjid").val(data.id);
				$("#info_bjmc").val(formatUndefine(data.ywms) + " " + formatUndefine(data.zwms));
				$("#info_xingh").val(data.xingh);
				$("#info_gg").val(data.gg);
				$("#info_jhly").val(data.jhly);
				$("#info_zjh").val(data.zjh);
				$("#info_zjhms").val(data.fixChapter ? data.fixChapter.displayName : "");
				$("#info_bzjh").val(data.bzjh);
			}
			// 判断物料信息是否可以输入
			disableChildMaterial();
	    }
	}); 
}

/**
 * 判断物料信息是否可以输入
 */
function disableChildMaterial(){
	var bjid = $("#info_bjid").val();
	if(bjid){
		$("#info_bjh").addClass("readonly-style").attr("readonly","readonly");
		$("#info_wllb").attr("disabled", "disabled");
		$("#info_bjmc").attr("disabled", "disabled");
		$("#info_xingh").attr("disabled", "disabled");
		$("#info_gg").attr("disabled", "disabled");
		$("#info_jhly").attr("disabled", "disabled");
		$("#info_zjhms").attr("disabled", "disabled");
		$("#info_dw").attr("disabled", "disabled");
		$("#info_bzjh").attr("disabled", "disabled");
		$("#info_zjh_btn").hide();
	}else{
		$("#info_bjh").removeClass("readonly-style").removeAttr("readonly");
		$("#info_wllb").removeAttr("disabled");
		$("#info_bjmc").removeAttr("disabled");
		$("#info_xingh").removeAttr("disabled");
		$("#info_gg").removeAttr("disabled");
		$("#info_jhly").removeAttr("disabled");
		$("#info_zjhms").removeAttr("disabled");
		$("#info_dw").removeAttr("disabled");
		$("#info_bzjh").removeAttr("disabled");
		$("#info_zjh_btn").show();
	}
	
	limitXqsl();
}

/**
 * 选择章节号
 */
function chooseFixChapter(){
	var zjh = $.trim($("#info_zjh").val());
	var dprtcode = $("#dprtId").val();
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		parentWinId : "demand_apply_add_alert",
		callback: function(data){//回调函数
			if(data.zjh){
				var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
				$("#info_zjh").val(data.zjh);
				$("#info_zjhms").val(wczjhName);
				$("#info_zjhms").addClass("readonly-style").attr("readonly","readonly");
			}else{
				$("#info_zjh").val('');
				$("#info_zjhms").val('');
				$("#info_zjhms").removeClass("readonly-style").removeAttr("readonly");
			}
		}
	});
}

/**
 * 新增物料信息
 */
function addMaterialInfo(){
	// 清空物料信息数据
	fillMaterialInfo({});
	$("#demand_apply_add_alert").modal("show");
}

/**
 * 修改物料信息
 */
function editMaterialInfo(rowid){
	var obj = MaterialInfo.get(rowid);
	fillMaterialInfo(obj);
	$("#demand_apply_add_alert").modal("show");
}

/**
 * 删除物料信息
 */
function deleteMaterialInfo(rowid){
	// 列表移除该列
	$("#material_info_list>tr[rowid='"+rowid+"']").remove();
	// 如果列表无数据了，则添加暂无数据
	if($("#material_info_list>tr").length == 0){
		$("#material_info_list").append('<tr class="no-data"><td class="text-center" colspan="12" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
	}
	// 删除页面数据
	MaterialInfo.remove(rowid);
}

/**
 * 填充物料信息数据
 * @param data
 */
function fillMaterialInfo(obj){
	$("#info_id").val(obj.id);
	$("#info_rowid").val(obj.rowid);
	$("#info_wllb").val(obj.wllb || "1");
	$("#info_bjid").val(obj.bjid);
	$("#info_bjh").val(obj.bjh);
	$("#info_bjmc").val(obj.bjmc);
	$("#info_xingh").val(obj.xingh);
	$("#info_gg").val(obj.gg);
	$("#info_jhly").val(obj.jhly);
	$("#info_zjh").val(obj.zjh);
	$("#info_zjhms").val(obj.zjhms);
	$("#info_bzjh").val(obj.bzjh);
	$("#info_xqsl").val(obj.xqsl);
	$("#info_xlh").val(obj.xlh);
	$("#info_thj").val(obj.thj);
	if(obj.bjh){
		$("#info_dw").val(obj.dw);
	}else{
		$("#info_dw option:first").prop("selected", "selected");
	}
	// 判断物料信息是否可以输入
	disableChildMaterial();
	AlertUtil.hideModalFooterMessage("demand_apply_add_alert");
}

/**
 * 获取物料信息
 */
function getMaterialInfo(){
	var obj = {};
	obj.id = $.trim($("#info_id").val());
	obj.rowid = $.trim($("#info_rowid").val());
	obj.wllb = $.trim($("#info_wllb").val()) || null;
	obj.bjid = $.trim($("#info_bjid").val()) || null;
	obj.bjh = $.trim($("#info_bjh").val()) || null;
	obj.bjmc = $.trim($("#info_bjmc").val()) || null;
	obj.xingh = $.trim($("#info_xingh").val()) || null;
	obj.gg = $.trim($("#info_gg").val()) || null;
	obj.jhly = $.trim($("#info_jhly").val()) || null;
	obj.zjh = $.trim($("#info_zjh").val()) || null;
	obj.zjhms = $.trim($("#info_zjhms").val()) || null;
	obj.bzjh = $.trim($("#info_bzjh").val()) || null;
	obj.xqsl = $.trim($("#info_xqsl").val()) || null;
	obj.dw = $.trim($("#info_dw").val()) || null;
	obj.xlh = $.trim($("#info_xlh").val()) || null;
	obj.thj = $.trim($("#info_thj").val()) || null;
	obj.xqbs = 1;
	return obj;
}

/**
 * 更新物料信息列表
 * @param obj
 */
function modifyMaterialInfoTable(obj){
	if(!obj.rowid){	// 新增
		obj.rowid = Math.uuid().toLowerCase();
		$("#material_info_list>.no-data").remove();
		$("#material_info_list").append(["<tr rowid='"+obj.rowid+"'>",
		       "<td class='text-center'>",
			       "<button class='line6 line6-btn' onclick='editMaterialInfo(\""+obj.rowid+"\")' type='button'>",
				   		"<i class='icon-pencil cursor-pointer color-blue cursor-pointer'></i>",	   
			       "</button>&nbsp;",
				   "<button class='line6 line6-btn' onclick='deleteMaterialInfo(\""+obj.rowid+"\")' type='button'>",
				   		"<i class='icon-minus cursor-pointer color-blue cursor-pointer'></i>",	   
			       "</button>",
		       "</td>",
		       "<td title='"+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb)+"' name='wllb'>"+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.wllb)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bjh)+"' name='bjh'>"+StringUtil.escapeStr(obj.bjh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bjmc)+"' name='bjmc'>"+StringUtil.escapeStr(obj.bjmc)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xingh)+"' name='xingh'>"+StringUtil.escapeStr(obj.xingh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.gg)+"' name='gg'>"+StringUtil.escapeStr(obj.gg)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xlh)+"' name='xlh'>"+StringUtil.escapeStr(obj.xlh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.xqsl)+"' name='xqsl'>"+StringUtil.escapeStr(obj.xqsl) + " " + StringUtil.escapeStr(obj.dw)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.jhly)+"' name='jhly'>"+StringUtil.escapeStr(obj.jhly)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.zjhms)+"' name='zjhms'>"+StringUtil.escapeStr(obj.zjhms)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.bzjh)+"' name='bzjh'>"+StringUtil.escapeStr(obj.bzjh)+"</td>",
		       "<td title='"+StringUtil.escapeStr(obj.thj)+"' name='thj'>"+StringUtil.escapeStr(obj.thj)+"</td>",
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
		row.find("[name='xqsl']").text(StringUtil.escapeStr(obj.xqsl) + " " + StringUtil.escapeStr(obj.dw));
		row.find("[name='xqsl']").attr("title",StringUtil.escapeStr(obj.xqsl) + " " + StringUtil.escapeStr(obj.dw));
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
	}
}

/**
 * 保存物料信息
 */
function saveMaterialInfo(){
	// 验证物料详情数据
	if(!validateDetailData()){
		return false;
	}
	// 获取物料信息
	var obj = getMaterialInfo();
	// 更新页面对象
	MaterialInfo.merge(obj);
	// 更新物料信息列表
	modifyMaterialInfoTable(obj);
	// 模态框关闭
	$("#demand_apply_add_alert").modal("hide");
}

/**
 * 章节号自动补全
 */
function initAutoComplete () {
	 $("#info_zjhms").typeahead({
			displayText : function(item){
				return StringUtil.escapeStr(item.zjh)+" "+StringUtil.escapeStr(item.ywms);
			},
		    source: function (query, process) {
		    	AjaxUtil.ajax({
					url: basePath+"/project/fixchapter/limitTen",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						zjh : query.toUpperCase(),
						dprtcode : $("#dprtId").val()
					}),
					success:function(data){
						if(data.length == 0){
							$("#info_zjh").val("");
						}
						process(data);
				    }
				}); 
		    },
	       updater: function (item) {
	    	  $("#info_zjh").val(StringUtil.escapeStr(item.zjh));
	    	  return item;
	       }
     });
}

/**
 * 编写下一条
 */
function writeNext(){
	// 验证物料详情数据
	if(!validateDetailData()){
		return false;
	}
	// 获取物料信息
	var obj = getMaterialInfo();
	// 更新页面对象
	MaterialInfo.merge(obj);
	// 更新物料信息列表
	modifyMaterialInfoTable(obj);
	// 清空物料信息数据
	fillMaterialInfo({});
	// 重置表单验证
	$('#demand_detail_form').data('bootstrapValidator').resetForm(false);
}

/**
 * 初始化验证
 */
function initDetailValidation(){
	$('#demand_detail_form').bootstrapValidator({
        message: '数据不合法',
        excluded: [],
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	info_wllb: {
                validators: {
                    notEmpty: {
                        message: '物料类别不能为空'
                    },
                }
            },
            info_bjh: {
                validators: {
                	notEmpty: {
                        message: '件号不能为空'
                    },
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
                    }
                }
            },
            info_xingh: {
                validators: {
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
                    }
                }
            },
            info_gg: {
                validators: {
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
                    }
                }
            },
            info_bzjh: {
                validators: {
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
                    }
                }
            },
            info_xqsl: {
                validators: {
                    notEmpty: {
                        message: '需求数量不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]|([1-9]\d+))$/,
                        message: '只能输入正整数'
                    }
                }
            },
            info_xlh: {
                validators: {
                	regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能输入中文'
                    }
                }
            },
        }
    });
}

/**
 * 验证物料详情数据
 */
function validateDetailData(){
	AlertUtil.hideModalFooterMessage("demand_apply_add_alert");
	$('#demand_detail_form').data('bootstrapValidator').resetForm(false);
	$('#demand_detail_form').data('bootstrapValidator').validate();
	if(!$('#demand_detail_form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！", "demand_apply_add_alert");
		return false;
	}
	
	// 验证章节号是否存在
	var zjh = $.trim($("#info_zjh").val());
	var zjhms = $.trim($("#info_zjhms").val());
	if(!zjh && zjhms){
		var fixChapter = null;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project/fixchapter/getFixChapterByZjh",
			type:"post",
			data:{zjh:zjhms,dprtcode:$("#dprtId").val()},
			dataType:"json",
			success:function(data){
				fixChapter = data;
			},
		});
		if(fixChapter == null){
			AlertUtil.showModalFooterMessage("章节号不存在！", "demand_apply_add_alert");
			return false;
		}else{
			var wczjhName = formatUndefine(fixChapter.zjh) + " " +formatUndefine(fixChapter.ywms);
			$("#info_zjh").val(fixChapter.zjh);
			$("#info_zjhms").val(wczjhName);
		}
	}
	return true;
}

/**
 * 限制需求数量
 */
function limitXqsl(){
	var xlh = $("#info_xlh").val();
	if(xlh){
		$("#info_xqsl").attr("disabled","disabled").val("1");
		$('#demand_detail_form').data('bootstrapValidator')  
	     .updateStatus("info_xqsl", 'NOT_VALIDATED',null)
	     .validateField("info_xqsl"); 
	}else{
		$("#info_xqsl").removeAttr("disabled");
	}
}
