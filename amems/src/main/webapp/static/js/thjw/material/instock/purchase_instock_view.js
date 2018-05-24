$(document).ready(function(){
	Navigation(menuCode, "采购入库", "Purchase InStock");
	initStoreSelect();
	 purchase_view.init();
	 purchase_view.load();//加载采购数据
});

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
 * 采购入库
 */
var purchase_view = {
	id: "purchase_view",
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
	gljb:"",//航材管理级别
	sl:0,//数量
	dicts:[],//数据字典
	delIds:[],//删除的ID
	init: function(){
		this.operateType = $("#operateType").val();
		this.bizId = $("#id").val();
//		this.operateType="view";
		if(this.operateType == "edit"){
			$("#purchase_view_save").show();
			$("#purchase_view_submit").show();
			$("#"+this.id+"_list tr th:eq(0)").show();
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
//		        	purchase_view_kccb: {
//		            	validators: {
//		            		regexp: {
//					        	regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
//		            			message: '只能输入数值，保留两位小数'
//		            		}
//		            	}
//		            },
		            purchase_view_bz: {
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
	},
	//加载数据
	load : 	function(){
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/view/purchase/"+this.bizId,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			success:function(content){
				var data = null;
				if(content){
					data = content.item;
					this_.dicts = content.dicts;
				}
				if(data){
					if(data.instock){
						$("#purchase_view_rkdh").val(data.instock.rkdh);
						$("#purchase_view_rkbmid").val(data.instock.rkbmid);
						if(data.instock.rkrUser){
							$("#purchase_view_rkrid").val(data.instock.rkrUser.id);
							$("#purchase_view_rkrmc").val(data.instock.rkrUser.username +" "+ data.instock.rkrUser.realname);
						}
						$("#purchase_view_rksj").val(data.instock.rksj.substr(0,10));
						$("#purchase_view_bz").val(data.instock.bz);
					}
					if(data.hcMainData){
						this_.bjid = data.hcMainData.id;
						$("#purchase_view_bjh").val(data.hcMainData.bjh);
						$("#purchase_view_zwms").val(data.hcMainData.zwms);
						$("#purchase_view_ywms").val(data.hcMainData.ywms);
						$("#purchase_view_gljb").val(data.hcMainData.gljb);
						this_.gljb = data.hcMainData.gljb? data.hcMainData.gljb: '3';
						$("#purchase_view_sl").val(data.sl);
						this_.sl = data.sl?data.sl:0;
					}
					//索赔期限、库存成本取航材入库履历中信息
					if(data.detailStockList && data.detailStockList.length > 0){
						$("#purchase_view_spqx").val(formatUndefine(data.detailStockList[0].materialHistory.spqx).substr(0,10));
						$("#purchase_view_kccb").val(data.detailStockList[0].materialHistory.kccb);
					}else{
						//加载默认库存价格
						AjaxUtil.ajax({
							url:basePath+"/aerialmaterial/instock/defaultCost",
							type: "post",
							dataType:"json",
							data: {bjid: this_.bjid, instockDetailId: this_.bizId},
							success:function(data){
								$("#"+this_.id+"_kccb").val(formatUndefine(data));
						    }
						});
					}
					this_.appendContentHtml(data.detailStockList);
				}else{
					//加载默认库存价格
					AjaxUtil.ajax({
						url:basePath+"/aerialmaterial/instock/defaultCost",
						type: "post",
						dataType:"json",
						data: {bjid: this_.bjid, instockDetailId: thi_.bizId},
						success:function(data){
							$("#"+this_.id+"_kccb").val(formatUndefine(data));
					    }
					});
				}
				finishWait();
		    }
		}); 
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		if(this.gljb == "3"){//序列号管理的隐藏操作列
			$("#"+this.id+"_column_operate").hide();
		}
		if(list && list.length > 0){//当有数据是显示数据
			$.each(list,function(index,row){
				htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
				htmlContent += "<input type='hidden' name='stockId' value='"+row.id+"'/>"
				htmlContent += "<input type='hidden' name='kcllid' value='"+row.kcllid+"'/>"
				if(this_.operateType == "edit"){
					if(this_.gljb != "3"){//序列号管理不能生成操作列
						htmlContent += "<td style='vertical-align: middle;' class='text-center'><div>";
						htmlContent += "<button class='line6' onClick=\""+this_.id+".remove('"+ formatUndefine(row.id) + "', this)\"  title='删除 Delete'><i class='icon-minus  color-blue cursor-pointer'></i></button>";
						htmlContent += "</div></td>";
//						$("#"+this_.id+"_add").show();//显示新增按钮
					}
					htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control' oldValue='"+formatUndefine(row.materialHistory ?row.materialHistory.ckid:'')+"'></select></td>";  
					htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kwh' class='form-control' oldValue='"+StringUtil.escapeStr(row.materialHistory ?row.materialHistory.kwid:'')+"'></select></td>";  
					if(this_.gljb == "3"){
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn' onkeyup='onkeyup4Code(this)' value='"+StringUtil.escapeStr(row.materialHistory?(row.materialHistory.sn?row.materialHistory.sn:''):'')+"'/></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(row.materialHistory?row.materialHistory.kcsl:'')+"</td>";   
					}else if(this_.gljb == "2"){
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' onkeyup='onkeyup4Code(this)' value='"+StringUtil.escapeStr(row.materialHistory?(row.materialHistory.pch?row.materialHistory.pch:''):'')+"'/></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' value='"+formatUndefine(row.materialHistory?row.materialHistory.kcsl:'')+"' onkeyup='onkeyup4Num(this);' /></td>";   
					}else{
						htmlContent += "<td style='vertical-align: middle; ' align='center'></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' value='"+formatUndefine(row.materialHistory?row.materialHistory.kcsl:'')+"' onkeyup='onkeyup4Num(this);' /></td>";   
					}
				}else{
					htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(row.materialHistory ?row.materialHistory.ckid:'')+"</td>";  
					htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.materialHistory ?row.materialHistory.kwh:'')+"</td>";  
					htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.materialHistory?(row.materialHistory.sn?row.materialHistory.sn:(row.materialHistory.pch?row.materialHistory.pch:'')):'')+"</td>";  
					htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.materialHistory?row.materialHistory.kcsl:'')+"</td>";  
				}
				htmlContent += "</tr>";  
			});
		}else{//当没有数据时，显示
			if(this_.operateType == "edit"){
				if(this.gljb == "3"){//序列号管理 按数量生成多条记录
					for (var int = 0; int < this.sl; int++) {
						htmlContent += "<tr style=\"cursor:pointer\"  ;  >";
						
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kwh' class='form-control'></select></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn' onkeyup='onkeyup4Code(this)'/></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='right'>1</td>";  
						
						htmlContent += "</tr>";  
					}
				}else{//生成一条记录 带操作列
					htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
					htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
					htmlContent += "<button class='line6' onClick=\""+this_.id+".remove('', this)\"  title='删除 Delete'><i class='icon-minus  color-blue cursor-pointer'></i></button>";
					htmlContent += "</div></td>";
					htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='ck'></select></td>";  
					htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='kwh'></select></td>";  
					if(this.gljb == "2"){
						htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' onkeyup='onkeyup4Code(this)'></td>";  
					}else{
						htmlContent += "<td style='vertical-align: middle; ' align='center'></td>";  
					}
					htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' value='"+$("#"+this.id+"_sl").val()+"' onkeyup='onkeyup4Num(this);'></td>";
					htmlContent += "</tr>";  
//					$("#"+this.id+"_add").show();//显示新增按钮
				}
			}else{
				$("#"+this.id+"_column_operate").hide();
				htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
			}
		}
		
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").html(htmlContent);
		this.renderSelect(list && list.length > 0);
	},
	//渲染下拉单数据
	renderSelect:function(flag){
		
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
	//保存
	save: function(isSubmit){
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			return false;
		}
		
		var tempSnArray = [];
		
		var data = {};
		
		data.id = this.bizId;
		data.spqx = $("#"+this.id+"_spqx").val();//索赔期限
		data.kccb = $.trim($("#"+this.id+"_kccb").val());//库存成本
		data.rkrid = $.trim($("#"+this.id+"_rkrid").val());//入库人ID
		data.rkbmid = $.trim($("#"+this.id+"_rkbmid").val());//入库部门ID
		data.rksj = $.trim($("#"+this.id+"_rksj").val());//入库日期
		data.bz = $.trim($("#"+this.id+"_bz").val());//备注
		
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
			if(this_.gljb == "3"){//序列号件
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
				if($.inArray(sn, tempSnArray) >= 0){
					AlertUtil.showMessage("序列号"+sn+"重复");
					isValid = false;
					return false;
				}
				tempSnArray.push(sn);
				stock.sn = sn;
				stock.kcsl = '1';
				count += 1;
			}else if(this_.gljb == "2"){//批次号件
				var pch = $.trim($(this).find("input:text[name='pch']").val());
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
				
				var kcsl = $.trim($(this).find("input:text[name='kcsl']").val());
				if(kcsl == "" || kcsl == 0){
					AlertUtil.showMessage("请填写数量");
					isValid = false;
					return false;
				}else{
					if(!this_.numberValidator.reg.test(kcsl)){  
						AlertUtil.showMessage("数量"+this_.numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
				stock.kcsl = kcsl;
				count += Number(kcsl);
			}else{
				var kcsl = $.trim($(this).find("input:text[name='kcsl']").val());
				if(kcsl == "" || kcsl == 0){
					AlertUtil.showMessage("请填写数量");
					isValid = false;
					return false;
				}else{
					if(!this_.numberValidator.reg.test(kcsl)){  
						AlertUtil.showMessage("数量"+this_.numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
				stock.kcsl = kcsl;
				count += Number(kcsl);
			}
			stock.id = $.trim($(this).find("input:hidden[name='stockId']").val());
			stock.kcllid = $.trim($(this).find("input:hidden[name='kcllid']").val());
			detailStock.push(stock);
		});
		
		data.detailStock = detailStock;
		
		if(!isValid){
			return false;
		}
		
		var sl = $("#"+this.id+"_sl").val();
		if(count != sl){
			AlertUtil.showMessage("总数量与待入库数量不相同");
			return false;
		}
		
		data.delIds = this.delIds;
		
		startWait();
		var url = basePath+"/aerialmaterial/instock/save/purchase";
		if(isSubmit){
			url = basePath+"/aerialmaterial/instock/submit/purchase";
		}
		
		AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:'application/json;charset=utf-8',
			dataType:"json",
			data: JSON.stringify(data),
			success:function(data){
				finishWait();
				if(isSubmit){
					AlertUtil.showMessage("提交成功", "/aerialmaterial/instock/main?tabId=purchase&pageParam="+pageParam);
				}else{
					AlertUtil.showMessage("保存成功");
					this_.load();
				}
		    }
		}); 
		
	},
	add: function(){
		var htmlContent = "";
		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
		htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
		htmlContent += "<button class='line6' onClick=\""+this.id+".remove('', this)\"  title='删除 Delete'><i class='icon-minus  color-blue cursor-pointer'></i></button>";
		htmlContent += "</div></td>";
		htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='ck'></select></td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='kwh'></select></td>";  
		if(this.gljb == "2"){
			htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' onkeyup='onkeyup4Code(this)'></td>";  
		}else{
			htmlContent += "<td style='vertical-align: middle; ' align='center'></td>";  
		}
		htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' onkeyup='onkeyup4Num(this);' value=''></td>";
		htmlContent += "</tr>";  
		$("#"+this.id+"_list").append(htmlContent);
		this.renderSelect(false);
	},
	remove:function(id, obj){
		if($("#"+this.id+"_list").find("tr").length == 1){
			AlertUtil.showMessage("至少要保留一条入库记录");
			return false;
		}
		if(id == ""){
			$(obj).parent().parent().parent().remove();
			return;
		}
		if(confirm("您确定要删除该记录吗？")){
			this.delIds.push(id);
			$(e.target).parent().parent().parent().remove();
		}
	},
	//提交
	submit: function(){
		this.save(true);
	},
	back: function(){
		window.location = basePath+"/aerialmaterial/instock/main?tabId=purchase&pageParam="+pageParam;
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
