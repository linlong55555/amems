$(document).ready(function(){
	Navigation(menuCode, "借入入库", "Lending InStock");
	initStoreSelect();
	borrow_view.init();
	changeOrganization();
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = userJgdm;
	var planeRegOption = '<option value="00000">通用Currency</option>';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:formatUndefine(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#borrow_view_fjzch").html(planeRegOption);
}

var storeHtml = "";
function initStoreSelect(){
	var cklbs=[0,4,5,6,7,8];
	var dprtcode = userJgdm;
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckh+" "+row.ckmc)+"</option>"
		}
	})
}

var borrow_view = {
	id:'borrow_view',
	instockId: null,
	borrowApplyId: null,
	dicts: {},
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符"
	},
	init: function(){
		$("#"+this.id+"_submit").show();
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		var this_ = this;
		//加载页面下拉单数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/init/lend",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			success:function(data){
				if(data.dicts){
					this_.dicts = data.dicts;
					var optionHtml = "";
					if(data.dicts.jddxlx){
						$.each(data.dicts.jddxlx, function(){
							optionHtml += "<option value='"+this.id+"'>"+StringUtil.escapeStr(this.name)+"</option>";
						});
					}else{
						optionHtml += "<option value=''>暂无数据 No Data</option>";
					}
					$("#"+this_.id+"_jddxlx").html(optionHtml);
					$("#"+this_.id+"_jddxlx").trigger("change");
				}
		    }
		});
		$("#"+this_.id+"_jddxlx").change(function(){
			var _value = $(this).val();
			if(_value == ""){
				$("#"+this_.id+"_jddx").html("<option value=''>暂无数据 No Data</option>");
				return;
			}
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/secondment/list/type",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data: JSON.stringify({jddxlx: _value, dprtcode : userJgdm}),
				success:function(data){
					var optionHtml = "";
					if(data && data.length > 0){
						$.each(data, function(){
							optionHtml += "<option value='"+this.id+"'>"+StringUtil.escapeStr(this.jddxms)+"</option>";
						});
					}else{
						optionHtml = "<option value=''>暂无数据 No Data</option>";
					}
					$("#"+this_.id+"_jddx").html(optionHtml);
			    }
			});
		});
		
		TimeUtil.getCurrentDate(function(date){
			$("#borrow_view_sqsj").val(date);
			$("#borrow_view_rksj").val(date);
		})
		
		$('#'+this.id+"_form").bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            borrow_view_jdfzr: {
	            	validators: {
	            		stringLength: {
	                        max: 10,
	                        message: '长度最多不超过10个字符'
	                    }
	            	}
	            },
	            borrow_view_bz: {
	                validators: {
	                    stringLength: {
	                        max: 300,
	                        message: '长度最多不超过300个字符'
	                    }
	                }
	            }
	        }
	    });
	},
	loadDefaultCost: function(){
		var bjidInputs = $("#"+this.id+"_list").find("input:hidden[name='bjid']");
		var bjids = [];
		$.each(bjidInputs, function(index, row){
			bjids.push($(this).val());
		});
		
		$.each(bjids, function(index, row){
			//加载默认库存价格
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/instock/defaultCost",
				type: "post",
				dataType:"json",
				data: {bjid: row},
				success:function(data){
					$.each(bjidInputs, function(i, r){
						var $input = $(this);
						if($input.val() == row){
							$input.parent().find("input[name='kccb']").val(formatUndefine(data));
						}
					});
				}
			});
		});
	},
	//枚举转化
	formatEnum: function(enumType, value){
		if(value == ""){
			return "";
		}
		if(this.dicts[enumType]){
			var text = value;
			$.each(this.dicts[enumType], function(){
				if(this.id == value){
					text = this.name;
					return false;
				}
			});
			return text;
		}else{
			return value;
		}
	},
	//渲染下拉单数据
	renderSelect:function(){
		var $select = $("select[name='ck']:empty");
		$select.html(storeHtml);
		$select.change(function(){
			var storeId = $(this).val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					var $selectKwh = $(this).parent().next().find("select[name='kw']");
					$selectKwh.html(kwOptionHtml);
				}
			}
		});
		$.each($select, function(){
			$(this).trigger("change");
		})
	},
	//用户选择按钮事件
	selectUser: function(){
		if(this.operateType == "view"){
			return;
		}
		var this_ = this;
		//调用用户选择弹窗
		userModal.show({
			selected:$("#"+this_.id+"_rkrid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#"+this_.id+"_rkrid").val(formatUndefine(data.id));
				$("#"+this_.id+"_rkrmc").val(formatUndefine(data.username)+ " " + formatUndefine(data.realname));
				$("#"+this_.id+"_rkbmid").val(formatUndefine(data.bmdm));
			}
		})
	},
	//用户选择按钮事件
	selectUser1: function(){
		if(this.operateType == "view"){
			return;
		}
		var this_ = this;
		//调用用户选择弹窗
		userModal.show({
			selected:$("#"+this_.id+"_sqrid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#"+this_.id+"_sqrid").val(formatUndefine(data.id));
				$("#"+this_.id+"_sqrmc").val(formatUndefine(data.username)+ " " + formatUndefine(data.realname));
				$("#"+this_.id+"_sqbmid").val(formatUndefine(data.bmdm));
			}
		})
	},
	submit: function(){
		startWait();
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			finishWait();
			return false;
		}
		var data = {};
		data.rukid = $("#borrow_view_rkrid").val();
		data.rkbmid = $("#borrow_view_rkbmid").val();
		data.sqrid = $("#borrow_view_sqrid").val();
		data.sqbmid = $("#borrow_view_sqbmid").val();
		data.sqsj2 = $("#borrow_view_sqsj").val();
		data.rksj = $("#borrow_view_rksj").val();
		data.jddx = $("#borrow_view_jddx").val();
		data.jdfzr = $("#borrow_view_jdfzr").val();
		data.fjzch = $("#borrow_view_fjzch").val();
		data.bz = $("#borrow_view_bz").val();
		
		if(data.jddx == ""){
			AlertUtil.showMessage("请选择借调对象");
			finishWait();
			return false;
		}
		
		
		var this_ = this;
		var tempBjSn = {};
		var detail = [];
		var isValid = true;
		$("#"+this.id+"_list").find("tr").each(function(){
			var stock = {};
			var ckid = $(this).find("select[name='ck']").val();
			if(ckid == null || ckid == ''){
				AlertUtil.showMessage("请选择仓库");
				isValid = false;
				return false;
			}
			stock.ckid = ckid;
			var kwid = $(this).find("select[name='kw']").val();
			if(kwid == null || kwid == ''){
				AlertUtil.showMessage("请选择库位");
				isValid = false;
				return false;
			}
			stock.kwid = kwid;
			
			stock.bjid = $(this).find("input:hidden[name='bjid']").val();

			var gljb = $(this).find("input[name='gljb']").val();
			
			if(gljb == "3"){//序列号件
				var sn = $.trim($(this).find("input:text[name='sn']").val());
				if(sn == ""){
					AlertUtil.showMessage("请填写序列号");
					isValid = false;
					return false;
				}else{
					if(!this_.codeValidator.reg.test(sn)){  
						AlertUtil.showMessage("序列号"+this_.codeValidator.message);
						isValid = false; 
						return false;
					} 
				}
				if(tempBjSn[stock.bjid]){
					if($.inArray(sn, tempBjSn[stock.bjid]) >= 0){
						AlertUtil.showMessage("序列号"+sn+"重复");
						isValid = false;
						return false;
					}
					tempBjSn[stock.bjid].push(sn);
				}else{
					var tempSnArray = [];
					tempSnArray.push(sn);
					tempBjSn[stock.bjid] = tempSnArray;
				}
				stock.sn = sn;
				stock.kcsl = '1';
			}else if(gljb == "2"){//批次号件
				var pch = $.trim($(this).find("input:text[name='sn']").val());
				if(pch == ""){
					AlertUtil.showMessage("请填写批次号");
					isValid = false;
					return false;
				}else{
					if(!this_.codeValidator.reg.test(pch)){  
						AlertUtil.showMessage("批次号"+this_.codeValidator.message);
						isValid = false; 
						return false;
					} 
				}
				stock.pch = pch;
				var sl = $.trim($(this).find("input:text[name='sl']").val());
				if(sl == ""){
					AlertUtil.showMessage("请填写入库数量");
					isValid = false;
					return false;
				}else{
					if(!this_.numberValidator.reg.test(sl)){  
						AlertUtil.showMessage("入库数量"+this_.numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
				stock.kcsl = sl;
			}else{
				var sl = $.trim($(this).find("input:text[name='sl']").val());
				if(sl == ""){
					AlertUtil.showMessage("请填写入库数量");
					isValid = false;
					return false;
				}else{
					if(!this_.numberValidator.reg.test(sl)){  
						AlertUtil.showMessage("入库数量"+this_.numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
				stock.kcsl = sl;
			}
			var kccb = $.trim($(this).find("input:text[name='kccb']").val());
			if(kccb == ""){
				AlertUtil.showMessage("请填写库存成本");
				isValid = false;
				return false;
			}else{
				if(!this_.numberValidator.reg.test(kccb)){  
					AlertUtil.showMessage("库存成本"+this_.numberValidator.message);
					isValid = false; 
					return false;
				} 
			}
			stock.kccb = kccb;
			
			detail.push(stock);
		});
		if(!isValid){
			finishWait();
			return false;
		}
		if(detail.length == 0){
			AlertUtil.showMessage("请添加借入航材信息");
			finishWait();
			return false;
		}
		
		data.detail = detail;
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/instock/submit/borrow",
			type: "post",
			contentType:'application/json;charset=utf-8',
			dataType:"json",
			data: JSON.stringify(data),
			success:function(data){
				finishWait();
				AlertUtil.showMessage("提交成功", "/aerialmaterial/instock/main?tabId=borrow&pageParam="+pageParam);
		    }
		}); 
		
	},
	add: function(){
		var this_ = this;
		open_win_material_basic.show({
			multi:true,
			callback: function(datas){//回调函数
				if(datas && datas.length > 0){
					$.each(datas, function(i, data){
						var htmlContent = "";
						htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
						htmlContent += "<input type='hidden' name='bjid' value='"+data.id+"'/>";
						htmlContent += "<input type='hidden' name='gljb' value='"+data.gljb+"'/>";
						htmlContent += "<td style='vertical-align: middle;' align='center'><button class=\"line6\" onclick=\""+this_.id+".remove(this);\"  title='删除 Delete'><i class=\"icon-minus cursor-pointer color-blue cursor-pointer\"></i></button></td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(data.bjh)+"' align='left'>"+StringUtil.escapeStr(data.bjh)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(data.ywms)+"' align='left'>"+StringUtil.escapeStr(data.ywms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(data.zwms)+"' align='left'>"+StringUtil.escapeStr(data.zwms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+this_.formatEnum("gljb", formatUndefine(data.gljb))+"</td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kw' class='form-control'></select></td>";  
						
						if(data.gljb == 1){
							htmlContent += "<td style='vertical-align: middle; ' align='center'>-</td>"; 
						}else{
							htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn' onkeyup='onkeyup4Code(this);'/></td>"; 
						}
						htmlContent += "<td style='vertical-align: middle;' align='center'><div class='input-group'><input type='text' class='form-control' name='kccb' onkeyup='onkeyup4Num(this);'/><span class='input-group-btn'><button class='btn btn-primary' onclick=\""+this_.id+".showContractCost('"+data.id+"',this);\"><i class='icon-list' ></i></button></span></div></td>";
						if(data.gljb == 3){
							htmlContent += "<td style='vertical-align: middle;' align='center'>1</td>";
						}else{
							htmlContent += "<td style='vertical-align: middle;' align='center'><input type='text' class='form-control' name='sl' onkeyup='onkeyup4Num(this);'/></td>";
						}
						htmlContent += "</tr>"; 
				
						var $htmlContent = $(htmlContent);
						//加载默认库存价格
						AjaxUtil.ajax({
							url:basePath+"/aerialmaterial/instock/defaultCost",
							type: "post",
							dataType:"json",
							data: {bjid: data.id},
							success:function(data){
								$htmlContent.find("input[name='kccb']").val(formatUndefine(data));
						    }
						});
						$("#"+this_.id+"_list").append($htmlContent);
					});
					this_.renderSelect();
				}
			}
		})
	},
	remove:function(obj){
		$(obj).parent().parent().remove();
	},
	//历史航材合同采购价格
	showContractCost: function(bjid, obj){
		var this_ = this;
		open_win_contract_cost_his.show({
			bjid: bjid,
			callback: function(data){//回调函数
				$(obj).parent().prev().val(data.htClf);
			}
		},true)
	},
	back: function(){
		window.location = basePath+"/aerialmaterial/instock/main?tabId=borrow&pageParam="+pageParam;
	}
};

//只能输入数字和小数,保留两位
function onkeyup4Num(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
     obj.value = validateMax(obj.value);
     function validateMax(_value){
    	 if(Number(_value) > 99999999.99){
    		return validateMax(_value.substr(0, _value.length-1));
    	 }
    	 return _value;
    }
}

function onkeyup4Code(obj){
	var reg = new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$");
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
