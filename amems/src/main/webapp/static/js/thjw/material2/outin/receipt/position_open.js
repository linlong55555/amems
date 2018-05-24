
/**
 * 临时存放位置
 */
var position = {
		
	id : "position_open_win",
	
	param: {
		detail:null,//收货明细
		callback:function(){},//回调函数
		dprtcode:userJgdm,//默认登录人当前机构代码
	},
	
	show : function(param){
		AlertUtil.hideModalFooterMessage(this.id);
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		// 初始化数据字典和枚举
		this.initDicAndEnum();
		// 加载数据
		this.load();
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		// 生成仓库下拉框
		this.buildStore();
		// 生成库位下拉框
		this.buildStorageLocation();
	},
	
	/**
	 * 加载数据
	 */
	load : function(){
		var this_ = this;
		var detail = this_.param.detail;
		$("#position_lscfwz").val(detail.lscfwz);
	},
	
	/**
	 * 生成仓库下拉框
	 */
	buildStore : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var storeHtml = "";
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
	 			storeHtml += "<option value=\""+row.id+"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		})
		$("#position_ck").html(storeHtml);
		var detail = this_.param.detail;
		if(detail.lsckid){
			$("#position_ck").val(detail.lsckid);
		}else{
			$("#position_ck").val($("#shck").val());
		}
	},
	
	/**
	 * 生成库位下拉框
	 */
	buildStorageLocation : function(){
		var this_ = this;
		var option = "<option value=''></option>";
		var ckid = $("#position_ck").val();
		$.each(userStoreList, function(index, row){
			if(row.id == ckid){
				for(var i = 0 ; i < row.storageList.length ; i++){
			 		var storage = row.storageList[i];
		 			option += '<option value="'+storage.id+'" kwh="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
			 	}
			}
		});
		$("#position_kw").html(option);
		var detail = this_.param.detail;
		if(detail.lskwid){
			$("#position_kw").val(detail.lskwid);
		}
		$('#position_kw').selectpicker('refresh');
	},
	
	
	/**
	 * 保存
	 */
	save : function(){
		
		var this_ = this;
		
		// 获取数据
		var detail = this_.param.detail;
		
		detail.lsckid = $.trim($("#position_ck").val());
		detail.lskwid = $.trim($("#position_kw").val());
		detail.lscfwz = $.trim($("#position_lscfwz").val());
		
		// 运行回调方法
		if(this_.param.callback && typeof(this_.param.callback) == "function"){
			this_.param.callback(detail);
		}
		
		// 隐藏模态框
		$("#"+this_.id).modal("hide");
	},
}