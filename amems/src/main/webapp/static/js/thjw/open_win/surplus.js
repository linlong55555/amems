/**
 * 剩余or计划
 */
SurplusWinModal = {
	id : 'SurplusWinModal',
	rldw : 11,//日历单位
	isLoad:false,
	numberValidator : {
		reg : new RegExp("^[0-9]+$"),
		message: "只能输入数值!"
	},
	param: {
		type:1,//1:剩余,2:计划
		viewObj:{},
		modalHeadCN : '剩余',
		modalHeadENG : 'Remain',
		callback:function(){}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
		$("#"+this.id).modal("show");
		AlertUtil.hideModalFooterMessage();
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(){
		this.initModelHead();
		this.initDate();
		this.showOrHide();
		this.returnViewData(this.param.viewObj);
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化日期
	 */
	initDate : function(){
		$("#rljh_e", $("#"+this.id)).datepicker({
			format:'yyyy-mm-dd',
			autoclose : true,
			clearBtn : true
		});
	},
	/**
	 * 显示剩余或计划
	 */
	showOrHide : function(){
		if(this.param.type == 1){
			$("#sy_e", $("#" + this.id)).show();
			$("#jh_e", $("#" + this.id)).hide();
		}else{
			$("#sy_e", $("#" + this.id)).hide();
			$("#jh_e", $("#" + this.id)).show();
		}
	},
	/**
	 * 回显数据
	 */
	returnViewData : function(obj){
		if(this.param.type == 1){
			if(obj.rldw == 12){
				this.rldw = obj.rldw;
				$("#rl_btn_v", $("#" + this.id)).html("M");
			}else{
				this.rldw = 11;
				$("#rl_btn_v", $("#" + this.id)).html("D");
			}
			this.returnSetData("rlsy_e", obj.rl, obj.rlhk);
		}else{
			this.returnSetData("rljh_e", obj.rl, obj.rlhk);
		}
		this.returnSetData("fxsj_e", TimeUtil.convertToHour(obj.fxsj, TimeUtil.Separator.COLON), obj.fxsjhk);
		this.returnSetData("fxxh_e", obj.fxxh, obj.fxxhhk);
		this.returnSetData("fdjsj_e", TimeUtil.convertToHour(obj.fdjsj, TimeUtil.Separator.COLON), obj.fdjsjhk);
		this.returnSetData("fdjxh_e", obj.fdjxh, obj.fdjxhhk);
		this.returnSetData("apusj_e", TimeUtil.convertToHour(obj.apusj, TimeUtil.Separator.COLON), obj.apusjhk);
		this.returnSetData("apuxh_e", obj.apuxh, obj.apuxhhk);
	},
	/**
	 * 回显数据
	 */
	returnSetData : function(id, value, hk){
		$("#" + id, $("#" + this.id)).val(value);
		$("#" + id + "_cb", $("#" + this.id)).removeAttr("checked");
		if(value == null || value == ''){
			$("#" + id + "_cb", $("#" + this.id)).attr("disabled", "true");
		}else{
			if(hk == 1){
				$("#" + id + "_cb", $("#" + this.id)).attr("checked",'true');//选中
			}
			$("#" + id + "_cb", $("#" + this.id)).removeAttr("disabled");
		}
	},
	/**
	 * 清空
	 */
	clearSurplus : function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback({});
		}
		this.close();
	},
	/**
	 * 关闭弹窗
	 */
	close : function(){
		$("#"+this.id).modal("hide");
	},
	/**
	 * 验证输入数据
	 */
	vilidateData : function(){
		var rlsy = $("#rlsy_e", $("#" + this.id)).val();
		var fxsj = $("#fxsj_e", $("#" + this.id)).val();
		var fxxh = $("#fxxh_e", $("#" + this.id)).val();
		var fdjsj = $("#fdjsj_e", $("#" + this.id)).val();
		var fdjxh = $("#fdjxh_e", $("#" + this.id)).val();
		var apusj = $("#apusj_e", $("#" + this.id)).val();
		var apuxh = $("#apuxh_e", $("#" + this.id)).val();
		
		if(this.param.type == 1 && rlsy != '' && !this.numberValidator.reg.test(rlsy)){
			AlertUtil.showModalFooterMessage("日历"+this.numberValidator.message);
			$("#rlsy_e", $("#" + this.id)).focus();
			$("#rlsy_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		}
		if(fxsj != '' && !this.numberValidator.reg.test(fxsj)){  
			AlertUtil.showModalFooterMessage("飞行时间"+this.numberValidator.message);
			$("#fxsj_e", $("#" + this.id)).focus();
			$("#fxsj_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		if(fxxh != '' && !this.numberValidator.reg.test(fxxh)){  
			AlertUtil.showModalFooterMessage("飞行循环"+this.numberValidator.message);
			$("#fxxh_e", $("#" + this.id)).focus();
			$("#fxxh_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		if(apusj != '' && !this.numberValidator.reg.test(apusj)){  
			AlertUtil.showModalFooterMessage("APU时间"+this.numberValidator.message);
			$("#apusj_e", $("#" + this.id)).focus();
			$("#apusj_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		if(apuxh != '' && !this.numberValidator.reg.test(apuxh)){  
			AlertUtil.showModalFooterMessage("APU循环"+this.numberValidator.message);
			$("#apuxh_e", $("#" + this.id)).focus();
			$("#apuxh_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		if(fdjsj != '' && !this.numberValidator.reg.test(fdjsj)){  
			AlertUtil.showModalFooterMessage("发动机时间"+this.numberValidator.message);
			$("#fdjsj_e", $("#" + this.id)).focus();
			$("#fdjsj_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		if(fdjxh != '' && !this.numberValidator.reg.test(fdjxh)){  
			AlertUtil.showModalFooterMessage("发动机循环"+this.numberValidator.message);
			$("#fdjxh_e", $("#" + this.id)).focus();
			$("#fdjxh_e", $("#" + this.id)).addClass("border-color-red");
			return false;
		} 
		return true;
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		if(!this.vilidateData()){
			return false;
		}
		var data = this.getData();
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		this.close();
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var data = {};
		var monitorStr = '';
		if(this.param.type == 1){
			var rl = $.trim($("#rlsy_e", $("#"+this.id)).val());
			if(rl != ''){
				data.rl = rl;
				data.rldw = this.rldw;
				data.rlhk = $("#rlsy_e_cb", $("#"+this.id)).is(":checked")?1:0;
				monitorStr += rl + $.trim($("#rl_btn_v", $("#"+this.id)).html());
				monitorStr += $("#rlsy_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
			}
		}else{
			var rl = $.trim($("#rljh_e", $("#"+this.id)).val());
			if(rl != ''){
				data.rl = rl;
				data.rlhk = $("#rljh_e_cb", $("#"+this.id)).is(":checked")?1:0;
				monitorStr += rl;
				monitorStr += $("#rljh_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
			}
		}
		var fxsj = $.trim($("#fxsj_e", $("#"+this.id)).val());
		var fxxh = $.trim($("#fxxh_e", $("#"+this.id)).val());
		var apusj = $.trim($("#apusj_e", $("#"+this.id)).val());
		var apuxh = $.trim($("#apuxh_e", $("#"+this.id)).val());
		var fdjsj = $.trim($("#fdjsj_e", $("#"+this.id)).val());
		var fdjxh = $.trim($("#fdjxh_e", $("#"+this.id)).val());
		
		if(fxsj != ''){
			data.fxsj = TimeUtil.convertToMinute(fxsj, TimeUtil.Separator.COLON)+"";
			data.fxsjhk = $("#fxsj_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += fxsj + $.trim($("#fxsj_e_dw", $("#"+this.id)).html());
			monitorStr += $("#fxsj_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		if(fxxh != ''){
			data.fxxh = fxxh;
			data.fxxhhk = $("#fxxh_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += fxxh + $.trim($("#fxxh_e_dw", $("#"+this.id)).html());
			monitorStr += $("#fxxh_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		if(apusj != ''){
			data.apusj = TimeUtil.convertToMinute(apusj, TimeUtil.Separator.COLON)+"";
			data.apusjhk = $("#apusj_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += apusj + $.trim($("#apusj_e_dw", $("#"+this.id)).html());
			monitorStr += $("#apusj_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		if(apuxh != ''){
			data.apuxh = apuxh;
			data.apuxhhk = $("#apuxh_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += apuxh + $.trim($("#apuxh_e_dw", $("#"+this.id)).html());
			monitorStr += $("#apuxh_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		if(fdjsj != ''){
			data.fdjsj = TimeUtil.convertToMinute(fdjsj, TimeUtil.Separator.COLON)+"";
			data.fdjsjhk = $("#fdjsj_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += fdjsj + $.trim($("#fdjsj_e_dw", $("#"+this.id)).html());
			monitorStr += $("#fdjsj_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		if(fdjxh != ''){
			data.fdjxh = fdjxh;
			data.fdjxhhk = $("#fdjxh_e_cb", $("#"+this.id)).is(":checked")?1:0;
			monitorStr += fdjxh + $.trim($("#fdjxh_e_dw", $("#"+this.id)).html());
			monitorStr += $("#fdjxh_e_cb", $("#"+this.id)).is(":checked")?" 含空; ":"; ";
		}
		data.monitorStr = monitorStr;
		return data;
	},
	/**
	 * 设置日历
	 */
	vilidateDate : function(obj){
		this.removeCheckedDisabled($(obj).attr("id") +"_cb", obj.value);
	},
	/**
	 * 设置日历单位
	 */
	checkedRl : function(obj){
		this.rldw = $(obj).attr("value");
		$("#rl_btn_v", $("#" + this.id)).html($(obj).html());
	},
	/**
	 * 输入监控数据时验证
	 */
	vilidateMonitorData : function(obj){
		this.onkeyup4Num(obj);
		this.removeCheckedDisabled($(obj).attr("id") +"_cb", obj.value);
		$(obj).removeClass("border-color-red");
	},
	/**
	 * 移除选中或失效
	 */
	removeCheckedDisabled : function(id, value){
		if(value == ''){
			$("#" + id, $("#" + this.id)).removeAttr("checked");
			$("#" + id, $("#" + this.id)).attr("disabled", "true");
		}else{
			$("#" + id, $("#" + this.id)).removeAttr("disabled");
		}
	},
	/**
	 * 只能输入数字
	 */
	onkeyup4Num : function(obj){
		//先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	}
	
}