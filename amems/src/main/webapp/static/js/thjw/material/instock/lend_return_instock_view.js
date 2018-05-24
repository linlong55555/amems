$(document).ready(function(){
	Navigation(menuCode, "借出归还入库", "Lending return");
	initStoreSelect();
	lend_return_view.init();
	lend_return_view.load();//加载采购数据
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
	operateType: "edit",
	gljb:"",//航材管理级别
	sl:0,//数量
	dicts:[],//数据字典
	bjid:"",//部件ID
	bjh:"",//部件号
	zwms:"",//中文名称
	ywms:"",//英文名称
	cjjh:"",//厂家件号
	init: function(){
		this.gljb = $("#"+this.id+"_gljb").val();
		this.bjid = $("#"+this.id+"_bjid").val();
		this.bjh = $("#"+this.id+"_bjh").val();
		this.zwms = $("#"+this.id+"_zwms").val();
		this.ywms = $("#"+this.id+"_ywms").val();
		this.cjjh = $("#"+this.id+"_cjjh").val();
		TimeUtil.getCurrentDate("#"+this.id+"_rksj")
		$('.date-picker').datepicker({
			 autoclose : true
		}).next().on("click", function() {
			 $(this).prev().focus();
		});
		if(this.gljb==3){
			$("#"+this.id+"_add").css("display","none");
		}
		
		var this_ = this;
		$("#"+this.id+"_sl").on("change", function(){
			var sl = $.trim($(this).val());
			if(sl == "" || sl == "0"){
				AlertUtil.showMessage("归还数量不能为空");
				return false;
			}else{
				if(!this_.numberValidator.reg.test(sl)){  
					AlertUtil.showMessage("归还数量"+this_.numberValidator.message);
					return false;
				} 
			}
			if(parseFloat(sl) > parseFloat($("#"+this_.id+"_dghsl").val())){
				AlertUtil.showMessage("归还数量不能大于待归还数量");
				return false;
			}
			this_.sl = sl;	
			this_.loadData();
		});
		
		//加载默认库存价格
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/defaultCost",
			type: "post",
			dataType:"json",
			data: {bjid: this.bjid},
			success:function(data){
				$("#"+this_.id+"_kccb").val(formatUndefine(data));
		    }
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
//	            lend_return_view_kccb: {
//	            	validators: {
//	            		regexp: {
//				        	regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
//	            			message: '只能输入数值，保留两位小数'
//	            		}
//	            	}
//	            },
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
	                        max: 300,
	                        message: '长度最多不超过300个字符'
	                    }
	                }
	            }
	        }
	    });
	},
	//加载数据
	load : 	function(){
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/store",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			success:function(content){
				if(content){
					this_.dicts = content.dicts;
				}
				finishWait();
		    }
		}); 
	},
	//加载航材数据
	loadData: function(){
		if(this.sl == 0){
			$("#"+this.id+"_list").empty();
		}else{
			this.appendContentHtml();
		}
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		if(this.gljb == "3"){//序列号管理 按数量生成多条记录
			for (var int = 0; int < this.sl; int++) {
				htmlContent += "<tr style=\"cursor:pointer\"  ;  >";
				
				htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
				htmlContent += "<i class='icon-edit color-blue cursor-pointer'  onClick=\""+this.id+".change('', event)\" title='更换 Change'></i>";
				htmlContent += "<input type='hidden' name='bjid' value='"+this.bjid+"'/>";
				htmlContent += "</div></td>";
				
				htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.bjh)+"' align='left'>"+StringUtil.escapeStr(this.bjh)+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.ywms)+"' align='left'>"+StringUtil.escapeStr(this.ywms)+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.zwms)+"' align='left'>"+StringUtil.escapeStr(this.zwms)+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kwh' class='form-control'></select></td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn' onkeyup='onkeyup4Code(this);'/></td>";  
				htmlContent += "<td style='vertical-align: middle; ' align='right'>1</td>";  
				
				htmlContent += "</tr>";  
			}
		}else{//生成一条记录 带操作列
			htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
			htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
			htmlContent += "<i class='icon-edit color-blue cursor-pointer'  onClick=\""+this.id+".change('', event)\" title='更换 Change'></i>&nbsp;&nbsp;";
			htmlContent += "<i class='icon-trash color-blue cursor-pointer'  onClick=\""+this.id+".remove('', event)\" title='删除 Delete'></i>";
			htmlContent += "<input type='hidden' name='bjid' value='"+this.bjid+"'/>";
			htmlContent += "</div></td>";
			
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.bjh)+"' align='left'>"+StringUtil.escapeStr(this.bjh)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.ywms)+"' align='left'>"+StringUtil.escapeStr(this.ywms)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(this.zwms)+"' align='left'>"+StringUtil.escapeStr(this.zwms)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='ck'></select></td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='kwh'></select></td>";  
			if(this.gljb == "2"){
				htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' onkeyup='onkeyup4Code(this);'></td>";  
			}else{
				htmlContent += "<td style='vertical-align: middle; ' align='center'></td>";  
			}
			htmlContent += "<td style='vertical-align: middle; ' align='right'><input type='text' class='form-control' name='kcsl' value='"+$("#"+this.id+"_sl").val()+"' onkeyup='onkeyup4Num(this);'></td>";
			htmlContent += "</tr>";  
		}
		
		$("#"+this.id+"_list").html(htmlContent);
		this.renderSelect();
	},
	//渲染下拉单数据
	renderSelect:function(){
		var $select = $("select[name='ck']:empty");
		console.info($select);
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
	//历史航材合同采购价格
	showContractCost: function(){
		if(this.operateType == "view"){
			return;
		}
		var this_ = this;
		//调用用户选择弹窗
		open_win_contract_cost_his.show({
			bjid: this_.bjid,
			callback: function(data){//回调函数
				$("#"+this_.id+"_kccb").val(data.htClf);
			}
		})
	},
	//更换航材
	change: function(value,e){
		open_win_material_basic.show({
			callback: function(data){
				var $td = $(e.target).parent().parent();
				$td.find("input[name='bjid']").val(data.id);
				$td.next().text(StringUtil.escapeStr(data.bjh));
				$td.next().attr("title", StringUtil.escapeStr(data.bjh));
				$td.next().next().text(StringUtil.escapeStr(data.ywms));
				$td.next().next().attr("title", StringUtil.escapeStr(data.ywms));
				$td.next().next().next().text(StringUtil.escapeStr(data.zwms));
				$td.next().next().next().attr("title", StringUtil.escapeStr(data.zwms));
			}
		});
	},
	//保存
	save: function(isSubmit){
		
		
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			return false;
		}
		
		var data = {};
		data.bjid = $("#"+this.id+"_bjid").val();//待归还的bjid
		data.kccb = $.trim($("#"+this.id+"_kccb").val());//库存成本
		data.rkrid = $.trim($("#"+this.id+"_rkrid").val());//入库人ID
		data.rkbmid = $.trim($("#"+this.id+"_rkbmid").val());//入库部门ID
		data.rksj = $.trim($("#"+this.id+"_rksj").val());//入库日期
		data.bz = $.trim($("#"+this.id+"_bz").val());//备注
		data.sqrid = $.trim($("#"+this.id+"_sqrid").val());//归还人
		data.sqbmid = $.trim($("#"+this.id+"_sqbmid").val());//归还人部门
		data.sl = $.trim($("#"+this.id+"_sl").val());//归还数量
		data.jdfzr = $.trim($("#"+this.id+"_jdfzr").val());//归还人
		data.wpdxid = $.trim($("#"+this.id+"_wpdxid").val());//外派对象人
		
		var detailStock = []//入库库存详情
		var tempBjSn = {}
		var isValid = true;
		var this_ = this;
		var count = 0;//入库航材计数
		$("#"+this.id+"_list").find("tr").each(function(){
			var stock = {};
			stock.bjid = $(this).find("input[name='bjid']").val()

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
			detailStock.push(stock);
		});
		
		data.detailStock = detailStock;
		
		if(!isValid){
			return false;
		}
		
		var sl = $("#"+this.id+"_sl").val();
		if(count != sl){
			AlertUtil.showMessage("总数量与归还数量不相同");
			return false;
		}
		
		var	url = basePath+"/aerialmaterial/instock/submit/lend";
		AlertUtil.showConfirmMessage("确定要提交入库单吗？", {
			callback: function(){
				startWait();
				AjaxUtil.ajax({
					url:url,
					type: "post",
					contentType:'application/json;charset=utf-8',
					dataType:"json",
					data: JSON.stringify(data),
					success:function(data){
						finishWait();
						AlertUtil.showMessage("提交成功", "/aerialmaterial/instock/main?tabId=lendReturn&pageParam="+pageParam);
					}
				}); 
			}
		})
		
	},
	add: function(){
		var htmlContent = "";
		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
		htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
		if(this.gljb == "3"){
			htmlContent += "<i class='icon-edit color-blue cursor-pointer'  onClick=\""+this.id+".change('', event)\" title='更换 Change'></i>";
		}else{
			htmlContent += "<i class='icon-edit color-blue cursor-pointer'  onClick=\""+this.id+".change('', event)\" title='更换 Change'></i>";
			htmlContent += "<i class='icon-trash color-blue cursor-pointer'  onClick=\""+this.id+".remove('', event)\" title='删除 Delete'></i>";
		}
		htmlContent += "<input type='hidden' name='bjid' value='"+this.bjid+"'/>";

		htmlContent += "</div></td>";
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+StringUtil.escapeStr(this.bjh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+StringUtil.escapeStr(this.ywms)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+StringUtil.escapeStr(this.zwms)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='ck'></select></td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='kwh'></select></td>";  
		if(this.gljb == "2"){
			htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' onkeyup='onkeyup4Code(this);'></td>";  
		}else{
			htmlContent += "<td style='vertical-align: middle; ' align='center'></td>";  
		}
		htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' value='' onkeyup='onkeyup4Num(this);'></td>";
		htmlContent += "</tr>";  
		$("#"+this.id+"_list").append(htmlContent);
		this.renderSelect();
	},
	remove:function(id, e){
		if($("#"+this.id+"_list").find("tr").length = 1){
			AlertUtil.showMessage("至少要保留一条入库记录");
			return false;
		}
		if(id == ""){
			$(e.target).parent().parent().parent().remove();
			return;
		}
		if(confirm("您确定要删除该记录吗？")){
			this.delIds.push(id);
			$(e.target).parent().parent().parent().remove();
		}
	},
	//提交
	submit: function(){
		var this_ = this;
		this_.save(true);
	},
	back: function(){
		window.location = basePath+"/aerialmaterial/instock/main?tabId=lendReturn&pageParam="+pageParam;
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

