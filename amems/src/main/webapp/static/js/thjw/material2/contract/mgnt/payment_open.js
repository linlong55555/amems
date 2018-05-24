/**
 * 收付款编辑
 */
payment_open_alert = {
	id : "payment_open_alert",
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	number2Validator : {
		reg : new RegExp("^[0-9]{1,10}$"),
		message: "只能输入数值!"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
	},
	codeValidatorQyZw : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,4000}$"),
		message: "只能输入长度不超过4000个字符的英文、数字、英文特殊字符!"
	},
	param: {
		id:'',
		htid: '',
		modalHeadCN : '新增收付款',
		modalHeadENG : 'Add',
		htObj:{},
		viewObj:{},
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).on("shown.bs.modal", function() {
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
		});
		//初始化信息
		this_.initInfo();
	},
	initInfo : function(){//初始化信息
		var this_ = this;
		this.initModelHead();
		this.initDic();
		this.initBody();
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(){
		$("#p_sfkfs", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('83','p_sfkfs',this.param.dprtcode);
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(){
		this.returnViewData();
	},
	/**
	 * 设置只读/失效
	 */
	setReadOnlyFailure : function(htlx){
		$("input:radio[name='paymentType']", $("#"+this.id)).attr("disabled","true"); 
		if(htlx == 40){
			$("input:radio[name='paymentType']", $("#"+this.id)).removeAttr("disabled"); 
		}
	},
	returnViewData : function(){
		var this_ = this;
		var htObj = this_.param.htObj;
		var obj = this_.param.viewObj;
		var htrq = indexOfTime(htObj.htrq);
		this_.param.htid = htObj.id;
		$("#p_hth", $("#"+this_.id)).val(htObj.hth);
		$("#p_htrq", $("#"+this_.id)).val(htrq);
		$("#p_htlx", $("#"+this_.id)).val(DicAndEnumUtil.getEnumName('contractMgntTypeEnum', htObj.htlx));
		$("#p_bzr", $("#"+this_.id)).val(htObj.paramsMap.bzrstr);
		$("#p_biz", $("#"+this_.id)).val(htObj.biz);
		$("#p_jffs", $("#"+this_.id)).val(htObj.jffs);
		$("#p_xgfms", $("#"+this_.id)).val(htObj.xgfms);
		
		$("input:radio[name='paymentType'][value='"+obj.lx+"']", $("#"+this_.id)).attr("checked",true); 
		$("#p_htmxid", $("#"+this_.id)).val(obj.htmxid);
		$("#p_htmxName", $("#"+this_.id)).val(obj.htmxName);
		
		var je = formatUndefine(obj.je);
		if(je != ''){
			je = je.toFixed(2);
		}
		
		$("#p_je", $("#"+this_.id)).val(je);
		if(obj.sfkfs != null){
			$("#p_sfkfs", $("#"+this_.id)).val(obj.sfkfs);
		}
		$("#p_fphm", $("#"+this_.id)).val(obj.fphm);
		$("#p_bz", $("#"+this_.id)).val(obj.bz);
		this.setReadOnlyFailure(htObj.htlx);
	},
	changeJe : function(obj){
		this.onkeyup4Num(obj);
		$(obj).removeClass("border-color-red");
	},
	/**
	 * 打开合同明细弹窗
	 */
	openContractInfoWin : function(){
		var this_ = this;
		var htObj = this_.param.htObj;
		var mainid = this_.param.htid;
		var id = $("#p_htmxid",$("#"+this_.id)).val();
		open_win_contract_info.show({
			multi : false,
			selected : id, //已经选择的
			mainid : mainid,
			biz : htObj.biz,
			modalHeadChina : '合同明细',
			modalHeadEnglish : 'Contract Detail',
			callback: function(data){//回调函数
				var htmxName = '';
				var htmxid = '';
				if(data != null ){
					htmxName = data.bjh;
					htmxid =  data.id;
				}
				$("#p_htmxName",$("#"+this_.id)).val(htmxName);
				$("#p_htmxid",$("#"+this_.id)).val(htmxid);
			}
		},true);
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		if(!$("input:radio[name='paymentType']", $("#"+this.id)).is(":checked")){
			AlertUtil.showModalFooterMessage("请选择类型!");
			return false;
		}
		var je = $.trim($("#p_je", $("#"+this_.id)).val());
		var sfkfs = $.trim($("#p_sfkfs", $("#"+this_.id)).val());
		if(null == je || "" == je){
			var message = "请输入金额!";
			AlertUtil.showModalFooterMessage(message);
			$("#p_je", $("#"+this_.id)).focus();
			$("#p_je", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(!this_.numberValidator.reg.test(je)){  
			AlertUtil.showModalFooterMessage("金额"+this_.numberValidator.message);
			$("#p_je", $("#"+this_.id)).focus();
			$("#p_je", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(je <= 0){
			var message = "金额必须大于0!";
			AlertUtil.showModalFooterMessage(message);
			$("#p_je", $("#"+this_.id)).focus();
			$("#p_je", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(sfkfs == null || sfkfs == ''){
			$("#p_sfkfs", $("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("请选择支付方式!");
			return false;
		}
		return true;
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		var this_ = this;
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		var data = this.getPaymentData();
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this_.param.callback(data);
		}
	},
	getPaymentData : function(){
		var this_ = this;
		var data = {};
		data.mainid = this_.param.htid;
		data.lx = $("input:radio[name='paymentType']:checked", $("#"+this_.id)).val(); 
		data.sfkfs = $.trim($("#p_sfkfs", $("#"+this_.id)).val());
		data.je = $.trim($("#p_je",$("#"+this_.id)).val());
		data.htmxid = $.trim($("#p_htmxid",$("#"+this_.id)).val());
		data.fphm = $.trim($("#p_fphm",$("#"+this_.id)).val());
		data.bz = $.trim($("#p_bz",$("#"+this_.id)).val());
		return data;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	//只能输入数字和小数,保留两位
	onkeyup4Num : function(obj){//只能输入数字和小数,保留两位
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
