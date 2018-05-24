
PayModal = {
	id:'PayModal',
	isLoad:false,
	param: {
		viewObj:null,
		callback:function(){}
	},
	show : function(param){
		//初始化下拉框数据
		if(!this.isLoad){
			this.initDate();
			this.isLoad = true;
		}
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();//清空数据
		//回显数据
		if(this.param.viewObj){
			this.returnViewData(this.param.viewObj);
		}
		this.changePayMethod();
		$("#"+this.id).modal("show");
	},
	initDate : function(){
		$('#wfkrq').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
	},
	//只能输入数字和小数
	clearNoNum : function(obj){
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g,"");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g,"");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g,".");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){
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
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	clearData : function(){
		$("#wfkrq").val('');
		$("#wfkje").val('');
		$("#wfkfs").val(2);
		$("#wfkfssm").val('');
		$("#wbz").val('');
	},
	returnViewData : function(obj){
		$("#wfkrq").val(obj.fkrq);
		$("#wfkje").val(obj.fkje);
		$("#wfkfs").val(obj.fkfs);
		$("#wfkfssm").val(obj.fkfssm);
		$("#wbz").val(obj.bz);
	},
	changePayMethod : function(){
		var wfkfs = $.trim($("#wfkfs").val());
		if(0 == wfkfs){
			$("#wfkfssmspan").show();
		}else{
			$("#wfkfssmspan").hide();
		}
	},
	vilidateData : function(){
		var wfkrq = $.trim($("#wfkrq").val());
		var wfkje = $.trim($("#wfkje").val());
		var wfkfs = $.trim($("#wfkfs").val());
		var wfkfssm = $.trim($("#wfkfssm").val());
		if(null == wfkrq || "" == wfkrq){
			AlertUtil.showErrorMessage("请选择付款日期!");
			return false;
		}
		if(null == wfkje || "" == wfkje){
			AlertUtil.showMessageCallBack({
				message : '请输入付款金额!',
				option : 'wfkje',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		if(null == wfkfs || "" == wfkfs){
			AlertUtil.showMessageCallBack({
				message : '请选择付款方式!',
				option : 'wfkfs',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		
		if(0 == wfkfs && (null == wfkfssm || "" == wfkfssm)){
			AlertUtil.showMessageCallBack({
				message : '请输入付款方式说明!',
				option : 'wfkfssm',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}
		
		var data = {};
		data.fkrq = $.trim($("#wfkrq").val());
		data.fkje = $.trim($("#wfkje").val());
		data.fkfs = $.trim($("#wfkfs").val());
		data.fkfssm = $.trim($("#wfkfssm").val());
		data.bz = $.trim($("#wbz").val());
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	}
	
}