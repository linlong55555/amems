$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		check_main.init();
		refreshPermission();
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		 //证书信息切换
		$('#certificate_info').on('show.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#certificate_info').on('hide.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
	})
	
var check_main = {
	id:'check_main',
	type : 1,
	dprtcode: userJgdm,
	stockObj : {},
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	init : function(){
		this.type = $("#type", $("#"+this.id)).val();
		this.setMaterialData({});
	},
	openWinAdd : function(){//打开新增合同弹出框
		var this_ = this;
		check_open_alert.show({
			type : this_.type,
	 		modalHeadCN : '新建库存',
			modalHeadENG : 'Add',
			dprtcode:userJgdm,//机构代码
			callback : function(data) {//回调函数
				if (data != null) {
					var message = '确认成功!';
					var url = basePath+"/material/store/check/save";
					this_.performAction(url, data, message);
					$("#"+this_.id+" .modal-body").prop('scrollTop','0');
				}
			}
		});
	},
	performAction : function(url, param, message){//执行编辑合同
		var this_ = this;
		startWait($("#check_open_alert"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#check_open_alert"),
			success:function(data){
				finishWait($("#check_open_alert"));
				AlertUtil.showMessage(message);
				check_open_alert.close();
			}
		});
	},
	/**
	 * 打开库存分布详情对话框
	 */
	openStorageDetailWin : function(obj){
		var this_ = this;
		var bjh = $("#bjh_m", $("#"+this.id)).val();
		var ckidList = [];
		//打开库存分布详情对活框
		open_win_inventory_distribution_details.show({
			bjh : bjh,
			ckidList:ckidList,
			dprtcode:userJgdm
		});
	},
	/**
	 * 打开冻结对话框
	 */
	openDjWin : function(obj){
		var kcid = this.stockObj.id;
		//查看冻结履历
		frozenHistoryModal.show({
		    id:kcid       //库存id	
		})
	},
	openStockWin : function(){//打开库存弹出框
		var this_ = this;
		var type = this_.type;
		var hclxList = [0, 1, 4, 5, 6];
		if(this_.type == 2){
			hclxList = [2, 3];
		}
		stock_open_alert.show({
			type : this_.type,
			hclxList : hclxList,
	 		modalHeadCN : '选择库存',
			modalHeadENG : 'Stock',
			dprtcode:userJgdm,//机构代码
			callback : function(data) {//回调函数
				this_.stockObj = {};
				if(data){
					this_.stockObj = data;
					data.zywms = StringUtil.escapeStr(data.ywms) + " " + StringUtil.escapeStr(data.zwms);
					this_.setMaterialData(data);
				}else{
					this_.setMaterialData({});
				}
			}
		});
	},
	scanSearch : function(){//扫描回车
		AlertUtil.hideModalFooterMessage();
		var this_ = this;
		var scan = $("#scan_search", $("#check_main_body")).val();
		var param = {};
		var paramsMap = {};
		if(scan == null || scan == ''){
			AlertUtil.showModalFooterMessage("该条码对应的库存不存在!");
			return;
		}
		$("#scan_search", $("#check_main_body")).val('');
		if(this.isJSON(scan)){
			var obj = JSON.parse(scan);
			param.id = obj.ID;
			if(param.id == ''){
				param.id = obj.id;
			}
		}else{
			paramsMap.uuiddm = scan;
			param.paramsMap = paramsMap;
		}
		param.dprtcode = userJgdm;
		param.hclx = this_.type;
		var url = basePath+"/material/store/check/getByStock";
		startWait($("#check_main"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#check_main"),
			success:function(data){
				finishWait($("#check_main"));
				this_.stockObj = {};
				if(data){
					this_.stockObj = data;
					data.zywms = StringUtil.escapeStr(data.ywms) + " " + StringUtil.escapeStr(data.zwms);
					this_.setMaterialData(data);
				}else{
					this_.setMaterialData({});
				}
			}
		});
	},
	setMaterialData : function(obj){
		$("#bjh_m", $("#"+this.id)).val(obj.bjh);
		$("#zywms_m", $("#"+this.id)).val(obj.zywms);
		$("#kcxq_btn", $("#"+this.id)).show();
		if(StringUtil.escapeStr(obj.bjh) == ''){
			$("#kcxq_btn", $("#"+this.id)).hide();
		}
		$("#pch_m", $("#"+this.id)).val(obj.pch);
		$("#sn_m", $("#"+this.id)).val(obj.sn);
		$("#cqbh_m", $("#"+this.id)).val(formatUndefine(obj.paramsMap?obj.paramsMap.cqbh:''));
		$("#wz_m", $("#"+this.id)).val(formatUndefine(obj.ckh)+" "+formatUndefine(obj.ckmc)+" "+formatUndefine(obj.kwh));
		$("#kcsl_m", $("#"+this.id)).val(obj.kcsl);
		$("#djsl_dw", $("#"+this.id)).hide();
		$("#djsl_dw", $("#"+this.id)).html('');
		if(StringUtil.escapeStr(obj.kcsl) != ''){
			var djsl = formatUndefine(obj.djsl);
			$("#djsl_dw", $("#"+this.id)).show();
			var djstr = "冻结数量：0";
			if(djsl != ''){
				djstr = "<a href='javascript:;' onclick="+this.id+".openDjWin('"+obj.id+"')  id='djsl_m'>冻结数量："+djsl+"</a>";
			}
			djstr += "&nbsp;<span class='dw_m'>"+StringUtil.escapeStr(obj.jldw)+"</span>";
			$("#djsl_dw", $("#"+this.id)).html(djstr);
		}
		$(".dw_m", $("#"+this.id)).html(StringUtil.escapeStr(obj.jldw));
		$("#djbz_m", $("#"+this.id)).val('');
		$("#dw_btn", $("#"+this.id)).show();
		if(StringUtil.escapeStr(obj.bjh) == ''){
			$("#dw_btn", $("#"+this.id)).hide();
		}
		$("#scan_search", $("#check_main_body")).val('');
		if(obj.sn != null && obj.sn != ''){
			$("#sjsl_m", $("#"+this.id)).attr("readonly", "readonly");
			$("#sjsl_m", $("#"+this.id)).val(0);
		}else{
			$("#sjsl_m", $("#"+this.id)).removeAttr("readonly");
			$("#sjsl_m", $("#"+this.id)).val('');
		}
		
		AlertUtil.hideModalFooterMessage();
	},
	isJSON : function(str){
		if (typeof str == 'string') {
	        try {
	            var obj = JSON.parse(str);
	            if(typeof obj == 'object' && obj){
	                return true;
	            }else{
	                return false;
	            }
	        } catch(e) {
	            return false;
	        }
	    }
	},
	changeNum2 : function(obj){
		this.clearNoNumTwo(obj);
		$(obj).removeClass("border-color-red");
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		var bjh = $.trim($("#bjh_m", $("#"+this_.id)).val());
		var kcsl = $.trim($("#kcsl_m", $("#"+this_.id)).val());
		var sjsl = $.trim($("#sjsl_m", $("#"+this_.id)).val());
		var djsl = this_.stockObj.djsl;
		if(this_.stockObj.id == null){
			AlertUtil.showModalFooterMessage("请扫描或选择库存!");
			return false;
		}
		
		if(null == sjsl || "" == sjsl){
			var message = "请输入实际数量!";
			AlertUtil.showModalFooterMessage(message);
			$("#sjsl_m", $("#"+this_.id)).focus();
			$("#sjsl_m", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(Number(kcsl) == Number(sjsl)){
			AlertUtil.showModalFooterMessage("未发生盈亏的库存数据不用登记!");
			return false;
		}
		
		if(!this_.numberValidator.reg.test(sjsl)){  
			AlertUtil.showModalFooterMessage("实际数量"+this_.numberValidator.message);
			$("#sjsl_m", $("#"+this_.id)).focus();
			$("#sjsl_m", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		if(Number(djsl) > Number(sjsl)){
			AlertUtil.showModalFooterMessage("实际数量不能小于冻结数量!");
			return false;
		}
		
		return true;
	},
	save : function(){
		var this_ = this;
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		var param = this.getData();
		var message = '确认成功!';
		var url = basePath+"/material/store/check/update";
		startWait($("#check_main"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#check_main"),
			success:function(data){
				finishWait($("#check_main"));
				AlertUtil.showMessage(message);
				this_.setMaterialData({});
				this_.stockObj = {};
			}
		});
		
	},
	getData : function(){
		var this_ = this;
		var data = {};
		var stockObj = this_.stockObj;
		data.id = stockObj.id;
		data.hclx = stockObj.hclx;
		data.bz = $.trim($("#djbz_m",$("#"+this_.id)).val());
		data.kcsl = $.trim($("#sjsl_m",$("#"+this_.id)).val());
		return data;
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){//只能输入数字和小数,保留两位
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
}

	function customResizeHeight(bodyHeight, tableHeight){
		$("#check_main_body").removeAttr("style");
		var panelFooter=$("#check_main_body").siblings(".panel-footer").outerHeight()||0
		var scrollDiv=$("#scrollDiv").outerHeight()||0;
		var panelBody=bodyHeight-panelFooter-scrollDiv;
		$("#check_main_body").css("height",panelBody+"px");
	}
	