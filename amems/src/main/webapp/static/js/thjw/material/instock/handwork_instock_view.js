$(document).ready(function(){
	initStoreSelect();
	handwork_view.init();
	changeOrganization();
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
	$("#handwork_view_fjzch").html(planeRegOption);
}

var handwork_view = {
	id:'handwork_view',
	operateType: 'view', //页面操作模式 add、edit、view
	instockId: null,//入库单ID
	dicts: {},//数据字典
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符"
	},
	init: function(){
		this.operateType = $("#operateType").val();
		this.instockId = $("#instockId").val();
		
		//当入库单ID为空 时为 add模式
		if(this.instockId == ""){
			this.operateType = "add";
		}
		var this_ = this;
		if(this.operateType == "add"){
			$("#"+this.id+"_submit").show();
			$("#"+this.id+"_back").hide();
			$("#"+this.id+"_operate").show();
			$("#"+this.id+"_rownum").hide();
			$('.date-picker').datepicker({
				autoclose : true
			}).next().on("click", function() {
				$(this).prev().focus();
			});
			
			$("#"+this.id+"_sqrid").val(currentUser.id);
			$("#"+this.id+"_sqrmc").val(currentUser.username + " "+currentUser.realname);
			$("#"+this.id+"_sqbmid").val(currentUser.bmdm);
			$("#"+this.id+"_rkrid").val(currentUser.id);
			$("#"+this.id+"_rkrmc").val(currentUser.username + " "+currentUser.realname);
			$("#"+this.id+"_rkbmid").val(currentUser.bmdm);
			
			TimeUtil.getCurrentDate(function(date){
				$("#"+this_.id+"_sqsj").val(date);
				$("#"+this_.id+"_rksj").val(date);
			});
			
		}else if(this.operateType == "edit"){
			$("#"+this.id+"_submit").show();
			$("#"+this.id+"_back").show();
			$("#"+this.id+"_operate").show();
			$("#"+this.id+"_rownum").hide();

			$('.date-picker').datepicker({
				autoclose : true
			}).next().on("click", function() {
				$(this).prev().focus();
			});
		}else{
			$("#"+this.id+"_submit").hide();
			$("#"+this.id+"_back").show();
			$("#"+this.id+"_operate").hide();
			$("#"+this.id+"_rownum").show();
		}
		if(this.operateType != "view"){
			$('#'+this.id+"_form").bootstrapValidator({
		        message: '数据不合法',
		        feedbackIcons: {
		            //valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		            handwork_view_bz: {
		                validators: {
		                    stringLength: {
		                        max: 300,
		                        message: '长度最多不超过300个字符'
		                    }
		                }
		            }
		        }
		    });
		}
		
		//加载数据字典
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/handwork/initdata",
			type: "post",
			dataType:"json",
			success:function(data){
				this_.dicts = data.dicts;
				/*if(this_.operateType == "add"){
					if(this_.dicts["planes"]){
						var optionHtml = "<option value='00000' selected>通用Currency</option>";
						for (var i = 0; i < this_.dicts["planes"].length; i++) {
							optionHtml += "<option value='"+this_.dicts['planes'][i].fjzch+"'>"+this_.dicts['planes'][i].fjzch+"</option>";
						}
						$("#"+this_.id+"_fjzch").html(optionHtml);
					}
				}*/
		    }
		});
		
		
		
		if(this.operateType != "add"){
			this.load();
		}
	},
	load: function(){
		var obj = {instockId: this.instockId}
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/view/borrow",
			type: "post",
			dataType: "json",
			data: obj,
			success:function(data){
				this_.dicts = data.dicts;
				this_.setFormValue(data.instock);
				//显示借入航材
				if(this_.operateType=="edit"){
					if(data.borrowApplyDetailList && data.borrowApplyDetailList.length > 0){
						this_.appendContentHtml(data.borrowApplyDetailList);
					}else {
						$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
					}
				}else{
					if(data.instockDetailStockList && data.instockDetailStockList.length > 0){
						this_.appendContentHtml(data.instockDetailStockList);
					}else {
						$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
					}
				}
				finishWait();
		    }
		}); 
	},
	//为表单设置值 
	setFormValue: function(data){
		$("#borrow_view_rkdh").val(formatUndefine(data.rkdh));//入库单号
		$("#borrow_view_bz").val(formatUndefine(data.bz));
		$("#borrow_view_jdfzr").val(formatUndefine(data.jdfzr));
		
		if(this.dicts["jddxlx"]){
			var optionHtml = "";
			for (var i = 0; i < this.dicts["jddxlx"].length; i++) {
				optionHtml += "<option value='"+this.dicts['jddxlx'][i].id+"'>"+StringUtil.escapeStr(this.dicts['jddxlx'][i].name)+"</option>";
			}
			$("#"+this.id+"_jddxlx").html(optionHtml);
		}
		
		if(this.operateType == "edit"){//编辑模式
			if(data.rukid){
				$("#borrow_view_rkrid").val(formatUndefine(data.rukid));
				$("#borrow_view_rkrmc").val(formatUndefine(data.rkrUser.username));
				$("#borrow_view_rkbmid").val(formatUndefine(data.rkbmid));
			}else{
				$("#borrow_view_rkrid").val(currentUser.id);
				$("#borrow_view_rkrmc").val(currentUser.username);
				$("#borrow_view_rkbmid").val(currentUser.bmdm);
			}
			if(data.sqrid){
				$("#borrow_view_sqrid").val(formatUndefine(data.sqrid));
				$("#borrow_view_sqrmc").val(formatUndefine(data.sqrUser.username));
				$("#borrow_view_sqbmid").val(formatUndefine(data.sqbmid));
			}else{
				$("#borrow_view_rkrid").val(currentUser.id);
				$("#borrow_view_rkrmc").val(currentUser.username);
				$("#borrow_view_rkbmid").val(currentUser.bmdm);
			}
			if(!data.sqsj2 || !data.rksj){
				TimeUtil.getCurrentDate(function(date){
					if(data.sqsj2){
						$("#borrow_view_sqsj").val(formatUndefine(data.sqsj2).substr(0,10));
					}else{
						$("#borrow_view_sqsj").val(date);
					}
					if(data.rksj){
						$("#borrow_view_rksj").val(formatUndefine(data.rksj).substr(0,10));
					}else{
						$("#borrow_view_rksj").val(date);
					}
				})
			}
			$("#borrow_view_fjzch").html("<option value='"+StringUtil.escapeStr(data.fjzch)+"' selected='selected'>"+(StringUtil.escapeStr(data.fjzch)=='00000'?'通用Currency':StringUtil.escapeStr(data.fjzch))+"</option>");
			$("#"+this.id+"_jddxlx").val(formatUndefine(data.secondment?data.secondment.jddxlx:''));
			$("#"+this.id+"_jddx").html("<option value='"+data.jddxid+"' selected='selected'>"+StringUtil.escapeStr(data.secondment?data.secondment.jddxms:'')+"</option>");

		}else{//查看模式
			$("#borrow_view_rkrid").val(formatUndefine(data.rukid));
			$("#borrow_view_rkrmc").val(formatUndefine(data.rkrUser.username));
			$("#borrow_view_rkbmid").val(formatUndefine(data.rkbmid));
			$("#borrow_view_sqrid").val(formatUndefine(data.sqrid));
			$("#borrow_view_sqrmc").val(formatUndefine(data.sqrUser.username));
			$("#borrow_view_sqbmid").val(formatUndefine(data.sqbmid));
			$("#borrow_view_sqsj").val(formatUndefine(data.sqsj2).substr(0,10));
			$("#borrow_view_rksj").val(formatUndefine(data.rksj).substr(0,10));
			$("#borrow_view_fjzch").html("<option value='"+StringUtil.escapeStr(data.fjzch)+"' selected='selected'>"+(StringUtil.escapeStr(data.fjzch)=='00000'?'通用Currency':StringUtil.escapeStr(data.fjzch))+"</option>");
			$("#"+this.id+"_jddxlx").val(formatUndefine(data.secondment?data.secondment.jddxlx:''));
			$("#"+this.id+"_jddx").html("<option value='' selected='selected'>"+StringUtil.escapeStr(data.jddxms)+"</option>");
		}
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		
		var this_ = this;
		var rowNo = 1;
		if(this_.operateType=="edit"){//编辑模式
			$.each(list, function(index,row){
				if(row.hcMainData){
					if(row.hcMainData.gljb == 3){//序列号件进行循环多条记录
						for(var i=1; i <= row.sl; i++){
							htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
							htmlContent += "<input type='hidden' name='jdsqmxid' value='"+row.id+"'/>";
							htmlContent += "<input type='hidden' name='jdsqmxsl' value='"+row.sl+"'/>";
							htmlContent += "<input type='hidden' name='bjid' value='"+row.bjid+"'/>";
							htmlContent += "<input type='hidden' name='gljb' value='"+row.hcMainData.gljb+"'/>";
							htmlContent += "<td style='vertical-align: middle;' align='center'>"+rowNo+"</td>";  
							htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"</td>";  
							htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"</td>";  
							htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"</td>";  
							htmlContent += "<td style='vertical-align: middle;' align='center'>"+this_.formatEnum("gljb",formatUndefine(row.hcMainData?row.hcMainData.gljb:''))+"</td>";  
							htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
							htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kw' class='form-control'></select></td>";  
							htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn'/></td>"; 
							htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kccb'/></td>"; 
							htmlContent += "<td style='vertical-align: middle;' align='center'>1</td>";  
							htmlContent += "</tr>";  
							rowNo ++;
						}
					}else{//批次件和无管理件一条记录
						htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
						htmlContent += "<input type='hidden' name='jdsqmxid' value='"+row.id+"'/>";
						htmlContent += "<input type='hidden' name='jdsqmxsl' value='"+row.sl+"'/>";
						htmlContent += "<input type='hidden' name='bjid' value='"+row.bjid+"'/>";
						htmlContent += "<input type='hidden' name='gljb' value='"+row.hcMainData.gljb+"'/>";
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+rowNo+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+this_.formatEnum("gljb",formatUndefine(row.hcMainData?row.hcMainData.gljb:''))+"</td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='ck' class='form-control'></select></td>";  
						htmlContent += "<td style='vertical-align: middle; ' align='center'><select name='kw' class='form-control'></select></td>";  
						
						if(row.hcMainData.gljb == 2){
							htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn'/></td>"; 
						}else{
							htmlContent += "<td style='vertical-align: middle; ' align='center'>-</td>"; 
						}
						htmlContent += "<td style='vertical-align: middle;' align='center'><input type='text' class='form-control' name='kccb'/></td>";
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(row.sl)+"</td>";
						htmlContent += "</tr>";  
						rowNo ++;
					}
				}
			});
		}else{//查看模式
			
		}
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").html(htmlContent);
		this.renderSelect();
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
						htmlContent += "<td style='vertical-align: middle;' align='left'>"+this_.formatEnum("gljb", formatUndefine(data.gljb))+"</td>";  
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
	submit: function(){
		startWait();
		$('#'+this.id+"_form").data('bootstrapValidator').validate();
		if(!$('#'+this.id+"_form").data('bootstrapValidator').isValid()){
			finishWait();
			return false;
		}
		var data = {};
		data.rukid = $('#'+this.id+"_rkrid").val();
		data.rkbmid = $('#'+this.id+"_rkbmid").val();
		data.sqrid = $('#'+this.id+"_sqrid").val();
		data.sqbmid = $('#'+this.id+"_sqbmid").val();
		data.sqsj2 = $('#'+this.id+"_sqsj").val();
		data.rksj = $('#'+this.id+"_rksj").val();
		data.fjzch = $('#'+this.id+"_fjzch").val();
		data.bz = $('#'+this.id+"_bz").val();
		
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
			
			stock.bjid = $(this).find("input[name='bjid']").val();

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
			AlertUtil.showMessage("请增加入库航材");
			finishWait();
			return false;
		}
		data.detail = detail;
		AlertUtil.showConfirmMessage("确定要提交入库单吗？", {
			callback: function(){
				AjaxUtil.ajax({
					url: basePath+"/aerialmaterial/instock/submit/handwork",
					type: "post",
					contentType:'application/json;charset=utf-8',
					dataType:"json",
					data: JSON.stringify(data),
					success:function(data){
						finishWait();
						AlertUtil.showMessage("提交成功", "/aerialmaterial/instock/main?tabId=handwork");
				    }
				}); 
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