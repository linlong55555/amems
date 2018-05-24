
Contact_Person_Win_Modal = {
	id:'Contact_Person_Win_Modal',
	param: {
		viewObj:null,
		callback:function(){}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();//清空数据
		//回显数据
		if(this.param.viewObj){
			this.returnViewData(this.param.viewObj);
		}
		$("#"+this.id).modal("show");
	},
	clearData : function(){
		$("#lxr", $("#"+this.id)).val('');
		$("#zw", $("#"+this.id)).val('');
		$("#sj", $("#"+this.id)).val('');
		$("#zj", $("#"+this.id)).val('');
		$("#cz", $("#"+this.id)).val('');
		$("#yxdz", $("#"+this.id)).val('');
		$("#qq", $("#"+this.id)).val('');
		$("#wx", $("#"+this.id)).val('');
		$("#bz", $("#"+this.id)).val('');
	},
	returnViewData : function(obj){
		$("#lxr", $("#"+this.id)).val(obj.lxr);
		$("#zw", $("#"+this.id)).val(obj.zw);
		$("#sj", $("#"+this.id)).val(obj.sj);
		$("#zj", $("#"+this.id)).val(obj.zj);
		$("#cz", $("#"+this.id)).val(obj.cz);
		$("#yxdz", $("#"+this.id)).val(obj.yxdz);
		$("#qq", $("#"+this.id)).val(obj.qq);
		$("#wx", $("#"+this.id)).val(obj.wx);
		$("#bz", $("#"+this.id)).val(obj.bz);
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	vilidateData : function(){
		var this_ = this;
		var lxr = $.trim($("#lxr", $("#"+this_.id)).val());
		if(null == lxr || "" == lxr){
			AlertUtil.showMessageCallBack({
				message : '联系人不能为空!',
				option : 'lxr',
				callback : function(option){
					$("#"+option, $("#"+this_.id)).focus();
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
		data.lxr = $.trim($("#lxr", $("#"+this.id)).val());
		data.zw = $.trim($("#zw", $("#"+this.id)).val());
		data.sj = $.trim($("#sj", $("#"+this.id)).val());
		data.zj = $.trim($("#zj", $("#"+this.id)).val());
		data.cz = $.trim($("#cz", $("#"+this.id)).val());
		data.yxdz = $.trim($("#yxdz", $("#"+this.id)).val());
		data.qq = $.trim($("#qq", $("#"+this.id)).val());
		data.wx = $.trim($("#wx", $("#"+this.id)).val());
		data.bz = $.trim($("#bz", $("#"+this.id)).val());
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	}
	
}