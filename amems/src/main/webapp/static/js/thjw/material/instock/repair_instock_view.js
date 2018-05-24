$(document).ready(function(){
	Navigation(menuCode, "送修入库", "Repair InStock");
	repair_view.init();
});

/**
 * 采购入库
 */
var repair_view = {
	id: "repair_view",
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符"
	},
	operateType: "view",
	bizId: "",//业务ID，此处是instockDetail id
	bjid:"",
	dicts:[],//数据字典
	storeHtml:"",//仓库下拉option
	init: function(){
		var this_ = this;
		this.operateType = $("#operateType").val();
		this.bizId = $("#id").val();
//		this.operateType="view";
		if(this.operateType == "edit"){
			$("#"+this.id+"_save").show();
			$("#"+this.id+"_submit").show();
			$("#"+this.id+"_list tr th:eq(0)").show();
			$('.date-picker').datepicker({
				 autoclose : true
			}).next().on("click", function() {
				 $(this).prev().focus();
			});
			
			var cklbs=[0,4,5,6,7,8];
			$.each(userStoreList, function(index, row){
				if($.inArray(row.cklb, cklbs)>=0 && userJgdm == row.dprtcode){
					this_.storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckh+" "+row.ckmc)+"</option>"
				}
			})
			
			$('#'+this.id+"_form").bootstrapValidator({
		        message: '数据不合法',
		        feedbackIcons: {
		            //valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
//		        	repair_view_kccb: {
//		            	validators: {
//		            		regexp: {
//					        	regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
//		            			message: '只能输入数值，保留两位小数'
//		            		}
//		            	}
//		            },
		            repair_view_bz: {
		                validators: {
		                    stringLength: {
		                        max: 300,
		                        message: '长度最多不超过300个字符'
		                    }
		                }
		            }
		        }
		    });
			
		}else{
			$('.date-picker').removeClass("date-picker");
			$("#"+this.id+"_list tr th:eq(0)").hide();
			$("input").attr("readonly","readonly");
			$("textarea").attr("readonly","readonly");
		}
		this.load();
	},
	//加载数据
	load : 	function(){
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/view/repair",
			type: "post",
			dataType:"json",
			data:{instockDetailId: this_.bizId},
			success:function(content){
				var data = null;
				if(content){
					data = content.item;
					this_.dicts = content.dicts;
				}
				if(data){
					if(data.instock){
						$("#"+this_.id+"_rkdh").val(data.instock.rkdh);
						if(data.instock.rkrUser){
							$("#"+this_.id+"_rkbmid").val(data.instock.rkbmid);
							$("#"+this_.id+"_rkrid").val(data.instock.rkrUser.id);
							$("#"+this_.id+"_rkrmc").val(data.instock.rkrUser.realname);
							$("#"+this_.id+"_rksj").val(data.instock.rksj.substr(0,10));
						}else{
							$("#"+this_.id+"_rkrid").val(currentUser.id);
							$("#"+this_.id+"_rkrmc").val(currentUser.realname);
							$("#"+this_.id+"_rkbmid").val(currentUser.bmdm)
							TimeUtil.getCurrentDate("#"+this_.id+"_rksj");
						}
						$("#"+this_.id+"_bz").val(data.instock.bz);
					}
					if(data.expatriate){
						$("#"+this_.id+"_original_sn").val(data.expatriate.sn);
						if(this_.operateType == "edit"){
							$("#"+this_.id+"_sn").val(data.expatriate.sn);
						}
					}
					if(data.hcMainData){
						this_.bjid = data.hcMainData.id;
						$("#"+this_.id+"_original_bjh").val(data.hcMainData.bjh);
						$("#"+this_.id+"_zwms").val(data.hcMainData.zwms);
						$("#"+this_.id+"_ywms").val(data.hcMainData.ywms);
						if(this_.operateType == "edit"){
							$("#"+this_.id+"_bjh").val(data.hcMainData.bjh);
							$("#"+this_.id+"_bjid").val(data.hcMainData.id);
						}
					}
					if(data.detailStockList && data.detailStockList.length > 0){
						$("#"+this_.id+"_kccb").val(data.detailStockList[0].materialHistory.kccb);
						$("#"+this_.id+"_bjh").val(data.detailStockList[0].materialHistory.bjh);
						$("#"+this_.id+"_bjid").val(data.detailStockList[0].materialHistory.bjid);
						$("#"+this_.id+"_sn").val(data.detailStockList[0].materialHistory.sn);
						$("#"+this_.id+"_ck").attr("oldValue",data.detailStockList[0].materialHistory.ckid);
						$("#"+this_.id+"_kw").attr("oldValue",data.detailStockList[0].materialHistory.kwid);
					}else{
						//加载默认库存价格
						AjaxUtil.ajax({
							url:basePath+"/aerialmaterial/instock/defaultCost",
							type: "post",
							dataType:"json",
							data: {bjid: this_.bjid/*, instockDetailId: this_.bizId*/},
							success:function(data){
								$("#"+this_.id+"_kccb").val(formatUndefine(data));
						    }
						});
					}
					this_.renderSelect(data.detailStockList && data.detailStockList.length > 0);
				}else{
					AlertUtil.showMessage("加载送修入库数据出现错误");
				}
				finishWait();
		    }
		}); 
	},
	//渲染下拉单数据
	renderSelect:function(flag){

		var $select = $("#repair_view_ck");
		$select.html(this.storeHtml);
		$select.change(function(){
			var storeId = $(this).val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					$("#repair_view_kw").html(kwOptionHtml);
				}
			}
		});
		if(flag){//有数据
			$.each($select, function(){
				$(this).val($(this).attr("oldValue"));
				$(this).trigger("change");
			})
		}else{//无数据
			$.each($select, function(){
				$(this).trigger("change");
			})
		}
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
	//历史航材合同采购价格
	showContractCost: function(){
		
		var this_ = this;
		open_win_contract_cost_his.show({
			bjid: this_.bjid,
			callback: function(data){//回调函数
				if(this_.operateType == "view"){
					return;
				}
				$("#"+this_.id+"_kccb").val(data.htClf);
			}
		})
	},
	showMaterial: function(){
		var this_ = this;
		open_win_material_basic.show({
//			bjid: this_.bjid,
			callback: function(data){//回调函数
				if(this_.operateType == "view"){
					return;
				}
				$("#"+this_.id+"_bjid").val(data.id);
				$("#"+this_.id+"_bjh").val(data.bjh);
			}
		})
	},
	//保存
	save: function(isSubmit){
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			return false;
		}
		var data = {};
		
		data.instockDetailId = this.bizId;
		data.kccb = $.trim($("#"+this.id+"_kccb").val());//库存成本
		data.rkrid = $.trim($("#"+this.id+"_rkrid").val());//入库人ID
		data.rkbmid = $.trim($("#"+this.id+"_rkbmid").val());//入库部门ID
		data.rksj = $.trim($("#"+this.id+"_rksj").val());//入库日期
		data.bz = $.trim($("#"+this.id+"_bz").val());//备注
		data.bjid = $.trim($("#"+this.id+"_bjid").val());//部件ID
		data.sn = $.trim($("#"+this.id+"_sn").val());//序列号
		data.kwid = $.trim($("#"+this.id+"_kw").val());//库位ID
		
		var this_ = this;
		startWait();
		var url = basePath+"/aerialmaterial/instock/save/repair";
		if(isSubmit){
			url = basePath+"/aerialmaterial/instock/submit/repair";
		}
		
		AjaxUtil.ajax({
			url:url,
			type: "post",
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			data: JSON.stringify(data),
			success:function(data){
				finishWait();
				if(isSubmit){
					AlertUtil.showMessage("提交成功", "/aerialmaterial/instock/main?tabId=repair&pageParam="+pageParam);
				}else{
					AlertUtil.showMessage("保存成功");
					this_.load();
				}
		    }
		}); 
		
	},
	//提交
	submit: function(){
		this.save(true);
	},
	back: function(){
		window.location = basePath+"/aerialmaterial/instock/main?tabId=repair&pageParam="+pageParam;
	}
}

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


