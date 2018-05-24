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

/**
 * 借出归还
 */
var lend_return_view = {
	id: "lend_return_view",
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符"
	},
	stores:[],//仓库
	init: function(){
		var this_ = this;
		if(operateType == "edit"){
			//过滤仓库
			$.each(userStoreList, function(i,row){
				if(row.dprtcode == userJgdm){
					this_.stores.push(row);
				}
			})
//			var fxdid = $("#lend_return_view_jddxid").val();
//			var fxd = AccessDepartmentUtil.getDpartName(fxdid);
//			$("#"+this.id+"_jddx").val(fxd);
//			$("#"+this.id+"_jddxid").val(fxdid);
			TimeUtil.getCurrentDate("#"+this.id+"_rksj")
			$('.date-picker').datepicker({
				 autoclose : true
			}).next().on("click", function() {
				 $(this).prev().focus();
			});
			
			$('#'+this.id+"_form").bootstrapValidator({
		        message: '数据不合法',
		        feedbackIcons: {
		            //valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	lend_return_view_sl: {
		                validators: {
		                	notEmpty: {
		                        message: '归还数量不能为空'
		                    },
					        regexp: {
					        	regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
					        	message: '只能输入数值，保留两位小数'
					        }
		                }
		            },
		            lend_return_view_kccb: {
		            	validators: {
		            		regexp: {
					        	regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
		            			message: '只能输入数值，保留两位小数'
		            		}
		            	}
		            },
		            lend_return_view_jdfzr: {
		            	validators: {
		            		stringLength: {
		                        max: 10,
		                        message: '长度最多不超过10个字符'
		                    }
		            	}
		            },
		            lend_return_view_bz: {
		                validators: {
		                    stringLength: {
		                        max: 150,
		                        message: '长度最多不超过150个字符'
		                    }
		                }
		            }
		        }
		    });
			
			AjaxUtil.ajax({
				url:basePath+"/otheraerocade/outstock/instock/detail/"+$("#id").val(),
				type: "post",
				dataType: "json",
				success:function(data){
					if(data.rows && data.rows.length > 0){
						this_.appendContentHtml(data.rows);
		 			}
			    }
			}); 
			
			
			
		}
	},
	//拼接列表内容
	appendContentHtml: function(rows){
		var this_ = this;
		var htmlContent = "";
		if(rows){
			$.each(rows, function(index, row){
				htmlContent += "<tr >";
				htmlContent += "<input type='hidden' name='bjh' value=\""+StringUtil.escapeStr(row.materialHistory.bjh)+"\"/>";
				htmlContent += "<input type='hidden' name='sn' value=\""+StringUtil.escapeStr(row.materialHistory.sn)+"\"/>";
				htmlContent += "<input type='hidden' name='pch' value=\""+StringUtil.escapeStr(row.materialHistory.pch)+"\"/>";
				htmlContent += "<input type='hidden' name='kcsl' value=\""+formatUndefine(row.materialHistory.kcsl)+"\"/>";
				htmlContent += "<td style='vertical-align: middle;' align='center'>"+(index+1)+"</td>";  
				htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.materialHistory.bjh)+"' align='left'>"+StringUtil.escapeStr(row.materialHistory.bjh)+"</td>";  
				htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.materialHistory.ywms)+"' align='left'>"+StringUtil.escapeStr(row.materialHistory.ywms)+"</td>";  
				htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.materialHistory.zwms)+"' align='left'>"+StringUtil.escapeStr(row.materialHistory.zwms)+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kwh' class='form-control'></select></td>";
				htmlContent += "<td style='vertical-align: middle;' title='"+(row.materialHistory.sn?StringUtil.escapeStr(row.materialHistory.sn):StringUtil.escapeStr(row.materialHistory.pch))+"' align='left'>"+(row.materialHistory.sn?StringUtil.escapeStr(row.materialHistory.sn):StringUtil.escapeStr(row.materialHistory.pch))+"</td>";  
				htmlContent += "<td style='vertical-align: middle;' align='right'>"+formatUndefine(row.materialHistory.kcsl)+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><div class='input-group'><input name='kccb' class='form-control'onkeyup='onkeyup4Num(this);'/><span class='input-group-btn'><button class='btn btn-primary' onclick=\""+this_.id+".showContractCost(this);\"><i class='icon-list' ></i></button></span></div></td>";
				htmlContent += "<td style='vertical-align: middle;' align='left'><div class='input-group'><input type='hidden' name='bjid'/><input type='text' name='bj' class='form-control' readonly/><span class='input-group-btn'><button class='btn btn-primary' onclick=\""+this_.id+".showHcMainData(this);\"><i class='icon-search' ></i></button></span></div></td>";  
				htmlContent += "</tr>";  
			});
		}
		$("#"+this.id+"_list").html(htmlContent);
		this.renderSelect();
		this.loadDefaultCost();
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
					var $selectKwh = $(this).parent().next().find("select[name='kwh']");
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
				$("#"+this_.id+"_rkrmc").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
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
				$("#"+this_.id+"_sqrmc").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
				$("#"+this_.id+"_sqbmid").val(formatUndefine(data.bmdm));
			}
		})
	},
	loadDefaultCost: function(){
		var bjInputs = $("#"+this.id+"_list").find("input[name='bjh']");
		var bjs = [];
		$.each(bjInputs, function(index, row){
			bjs.push($(this).val());
		});
		
		$.each(bjs, function(index, row){
			//加载默认库存价格
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/instock/defaultCost",
				type: "post",
				dataType:"json",
				data: {bjh: row},
				success:function(data){
					$.each(bjInputs, function(i, r){
						var $input = $(this);
						if($input.val() == row){
							$input.parent().find("input[name='kccb']").val(formatUndefine(data));
						}
					});
				}
			});
		});
	},
	//历史航材合同采购价格
	showContractCost: function(obj){
		var this_ = this;
		
		var $tr = $(obj).parent().parent().parent().parent();
		var bjid = $tr.find("input[name='bjid']").val();
		var bjh = $tr.find("input[name='bjh']").val();
		if(bjid != ""){
			bjh = "";
		}
		//调用用户选择弹窗
		open_win_contract_cost_his.show({
			bjid: bjid,
			bjh: bjh,
			callback: function(data){//回调函数
				$tr.find("input[name='kccb']").val(data.htClf);
			}
		},true)
	},
	showHcMainData: function(obj){
		open_win_material_basic.show({
			callback: function(data){//回调函数
				if(data){
					$(obj).parent().prev().val(data.bjh);
					$(obj).parent().prev().prev().val(data.id);
					
					//加载默认库存价格
					AjaxUtil.ajax({
						url:basePath+"/aerialmaterial/instock/defaultCost",
						type: "post",
						dataType:"json",
						data: {bjid: data.id},
						success:function(data){
							$(obj).parent().parent().parent().parent().find("input[name='kccb']").val(formatUndefine(data));
					    }
					});
					
				}else{
					$(obj).parent().prev().val("");
					$(obj).parent().prev().prev().val("");
					//加载默认库存价格
					AjaxUtil.ajax({
						url:basePath+"/aerialmaterial/instock/defaultCost",
						type: "post",
						dataType:"json",
						data: {bjh: $(obj).parent().parent().parent().parent().find("input[name='bjh']").val()},
						success:function(data){
							$(obj).parent().parent().parent().parent().find("input[name='kccb']").val(formatUndefine(data));
					    }
					});
				}
			}
		})
	},
	//分配：根据借调对象进行分配
	distribution: function(){
		var data = {};
		var rows = [];
		$("#"+this.id+"_list").find("tr").each(function(){
			var row = {}; 
			var bjid = $.trim($(this).find("input[name='bjid']").val());
			if(bjid==""){
				var bjh = $(this).find("input:hidden[name='bjh']").val()
				row.bjh = bjh;
			}else{
				row.bjid = bjid;
			}
			row.kcsl = $(this).find("input:hidden[name='kcsl']").val()
			rows.push(row);
		});
		data.fxdid = $("#"+this.id+"_jddxid").val();
		data.bjs = rows;
		var this_ = this;
		AjaxUtil.ajax({
			url : basePath+"/otheraerocade/outstock/instock/distribution",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data: JSON.stringify(data),
			dataType:"json",
			success:function(_data){
				if(_data && _data.rows && _data.rows.length > 0){
					var htmlContent = "";
					$.each(_data.rows, function(index, row){
						htmlContent += "<tr >";
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+(index+1)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.hcMainData.bjh)+"' align='left'>"+StringUtil.escapeStr(row.hcMainData.bjh)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.hcMainData.zwms)+"' align='left'>"+StringUtil.escapeStr(row.hcMainData.zwms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.hcMainData.ywms)+"' align='left'>"+StringUtil.escapeStr(row.hcMainData.ywms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='right'>"+formatUndefine(row.dghsl)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(row.wpsj).substr(0,10)+"</td>";  
						htmlContent += "</tr>";  
					});
					$("#"+this_.id+"_lend_list").html(htmlContent);
					$("#"+this_.id+"_lend_div").show();
					$("#"+this_.id+"_lend_table").show();
					$("#"+this_.id+"_submit").removeAttr("disabled");
				}
			}
		});
	},
	//保存
	submit: function(){
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			return false;
		}
		
		var data = {};
		data.outstockId = $("#id").val();
		data.rkrid = $.trim($("#"+this.id+"_rkrid").val());//入库人ID
		data.rkbmid = $.trim($("#"+this.id+"_rkbmid").val());//入库部门ID
		data.rksj = $.trim($("#"+this.id+"_rksj").val());//入库日期
		data.bz = $.trim($("#"+this.id+"_bz").val());//备注
		data.sqrid = $.trim($("#"+this.id+"_sqrid").val());
		data.sqbmid = $.trim($("#"+this.id+"_sqbmid").val());
		data.jdfzr = $.trim($("#"+this.id+"_jdfzr").val());//归还人
		data.jddxid = $.trim($("#"+this.id+"_jddxid").val());//外派对象
		
		var detailStock = []//入库库存详情
		
		var isValid = true;
		var this_ = this;
		var count = 0;//入库航材计数
		$("#"+this.id+"_list").find("tr").each(function(){
			var stock = {};
			var ckid = $(this).find("select[name='ck']").val();
			if(ckid == null || ckid == ''){
				AlertUtil.showMessage("请选择仓库");
				isValid = false;
				return false;
			}
			stock.ckid = ckid;
			var kwid = $(this).find("select[name='kwh']").val();
			if(kwid == null || kwid == ''){
				AlertUtil.showMessage("请选择库位");
				isValid = false;
				return false;
			}
			stock.kwid = kwid;
			var bjid = $(this).find("input[name='bjid']").val();
			if(bjid == ""){
				stock.bjh = $(this).find("input[name='bjh']").val();
			}else{
				stock.bjid = bjid;
			}
			
			var kccb = $.trim($(this).find("input[name='kccb']").val());
			if(kccb != "" && !this_.numberValidator.reg.test(kccb)){  
				AlertUtil.showMessage("库存成本"+this_.numberValidator.message);
				isValid = false; 
				return false;
			} 
			stock.kccb = kccb
			
			stock.sn = $(this).find("input[name='sn']").val()
			stock.pch = $(this).find("input[name='pch']").val()
			stock.kcsl = $(this).find("input[name='kcsl']").val()
			
			detailStock.push(stock);
		});
		
		data.detailStock = detailStock;
		
		if(!isValid){
			return false;
		}
		
		startWait();
		var	url = basePath+"/otheraerocade/instock/submit";
		AlertUtil.showConfirmMessage("确定要提交入库单吗？", {
			callback: function(){
				AjaxUtil.ajax({
					url:url,
					type: "post",
					contentType:'application/json;charset=utf-8',
					dataType:"json",
					data: JSON.stringify(data),
					success:function(data){
						finishWait();
						AlertUtil.showMessage("提交成功", "/otheraerocade/outstock/main?id="+$("#id").val()+"&pageParam="+pageParam);
				    }
				}); 
			}
		})
		
	},
	back: function(){
		window.location = basePath+"/otheraerocade/outstock/main?pageParam="+pageParam;
	}
}

$(document).ready(function(){
	Navigation(menuCode,"借出归还入库","Lending return");
	initStoreSelect();
	lend_return_view.init();
});


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
