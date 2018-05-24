
/**
 * 工时费用设置
 */
var work_hour_config_modal = {
	id:'work_hour_config_modal',
	param: {
		id:null,
		dprtcode:userJgdm,//默认登录人当前机构代码
		type:"edit",
		callback:function(){}//回调函数
	},
	//显示弹窗
	show : function(param){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.disabledInput();
		this.loadData();
	},
	// 初始化输入框是否可编辑
	disabledInput : function(){
		var this_ = this;
		AlertUtil.hideModalFooterMessage(this_.id);
		$("#detail_sqckjg,#detail_sqksrq,#detail_sqjsrq,#detail_shyj").removeAttr("disabled");
		$(".column-require").show();
		if(this_.param.type == "view"){
			$("#config_hqgs,#config_gzgs,#config_hhgs,#config_gsdj,#config_cyhcfybl").attr("disabled","disabled");
			$("#save_btn,.column-require").hide();
			$("#modalHeadCN").text("查看工时费用");
			$("#modalHeadENG").text("View Work Hour Payment");
		}else{
			$("#config_hqgs,#config_gzgs,#config_hhgs,#config_gsdj,#config_cyhcfybl").removeAttr("disabled");
			$("#save_btn,.column-require").show();
			$("#modalHeadCN").text("设置工时费用");
			$("#modalHeadENG").text("Set Work Hour Payment");
		}
	},
	/**
	 * 验证小数
	 */
	validateDecimal : function(obj){
		var value = $(obj).val();
		while(!(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	//加载数据
	loadData : 	function(){
		var this_ = this;
		AjaxUtil.ajax({
			url : basePath + "/produce/maintenancemgnt/detail",
			type : "post",
			contentType:"application/json;charset=utf-8",
		    dataType:"json",
		    data:JSON.stringify({
		    	fjzch : this_.param.fjzch,
		    	yf : this_.param.yf,
		    	dprtcode : this_.param.dprtcode,
		    }),
			success : function(data) {
				if(data){
					this_.fillData(data);
				}else{
					this_.fillData({
						fjzch : this_.param.fjzch,
						yf : this_.param.yf,
					});
				}
			}
		});
	},	
	fillData: function(obj){
		
		var this_ = this;
		
		$("#config_id").val(obj.id);
		$("#config_fjzch").val(obj.fjzch);
		$("#config_yf").val(this_.param.yf);
		$("#config_hqgs").val(obj.hqgs);
		$("#config_gzgs").val(obj.gzgs);
		$("#config_hhgs").val(obj.hhgs);
		$("#config_gsdj").val(obj.lxgsdj);
		$("#config_cyhcfybl").val(obj.cyhcfybl);
	},
	getData : function(){
		
		var data = {};
		
		data.id = $("#config_id").val();
		data.fjzch = $("#config_fjzch").val();
		data.yf = $("#config_yf").val();
		data.hqgs = $("#config_hqgs").val();
		data.gzgs = $("#config_gzgs").val();
		data.hhgs = $("#config_hhgs").val();
		data.lxgsdj = $("#config_gsdj").val();
		data.flxgsdj = $("#config_gsdj").val();
		data.cyhcfybl = $("#config_cyhcfybl").val();
		data.dprtcode = this.param.dprtcode;
		
		return data;
	},
	
	validateData : function(data){
		
		if(data.hqgs && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(data.hqgs)){
			AlertUtil.showErrorMessage("航前工时格式不正确！");
			return false;
		}
		if(data.gzgs && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(data.gzgs)){
			AlertUtil.showErrorMessage("过站工时格式不正确！");
			return false;
		}
		if(data.hhgs && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(data.hhgs)){
			AlertUtil.showErrorMessage("航后工时格式不正确！");
			return false;
		}
		if(data.lxgsdj && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(data.lxgsdj)){
			AlertUtil.showErrorMessage("工时费用格式不正确！");
			return false;
		}
		if(data.cyhcfybl && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(data.cyhcfybl)){
			AlertUtil.showErrorMessage("常用耗材费用比例格式不正确！");
			return false;
		}
		
		return true;
	},
	saveData: function(){
		var this_ = this;
		var data = this_.getData();
		if(!this_.validateData(data)){
			return false;
		}
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/produce/maintenancemgnt/save",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(data),
		   success:function(data){
			   finishWait();
			   AlertUtil.showMessage("保存成功！");
			   $("#"+this_.id).modal("hide");
			   if(this_.param.callback && typeof(this_.param.callback) == "function"){			
				   this_.param.callback(data);
			   }
	       }
	    }); 
	},
};