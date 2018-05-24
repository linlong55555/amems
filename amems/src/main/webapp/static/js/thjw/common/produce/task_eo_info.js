/**
 * EO任务信息
 */
eo_task_info_view = {
	id:'eo_task_info_view',
	param: {
		id : '',//监控对象id
		isShow : false//是否显示上版本执行历史
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initShowOrHide(this.param.isShow);
		this.load(this.param.id);
	},
	/**
	 * 初始化显示或隐藏上版本执行历史
	 */
	initShowOrHide : function(isShow){
		if(isShow){
			$("#eo_last_history", $("#"+this.id)).show();
		}else{
			$("#eo_last_history", $("#"+this.id)).hide();
		}
	},
	/**
	 * 加载数据
	 */
	load : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			if(obj.engineeringOrder != null){
				$("#eobh", $("#" + this_.id)).val(obj.engineeringOrder.eobh);
				$("#bb", $("#" + this_.id)).val(obj.engineeringOrder.bb);
				$("#eozt", $("#" + this_.id)).val(obj.engineeringOrder.eozt);
				$("#eobh", $("#" + this_.id)).val(obj.engineeringOrder.eobh);
				
				var zxfs = DicAndEnumUtil.getEnumName('executionEnum',obj.engineeringOrder.zxfs);
				if(obj.engineeringOrder.zxfs == 3 && obj.eoxc != null){
					zxfs = "分段：第" +obj.eoxc + "次";
				}
				$("#zxfs", $("#" + this_.id)).val(zxfs);
				
				var zjh = StringUtil.escapeStr(obj.paramsMap.zjh) + " " + StringUtil.escapeStr(obj.paramsMap.zjhywms);
				$("#zjh", $("#" + this_.id)).val(zjh);
				var jsgs = 1;
				if(obj.engineeringOrder.jsgs != null){
					jsgs = obj.engineeringOrder.jsgs;
				}
				this_.jsgs = jsgs;
				this_.hdwz = obj.engineeringOrder.hdwz;
				this_.zxfs = obj.engineeringOrder.zxfs;
			}
			var xh = "";
			if(obj.paramsMap != null){
				xh = obj.paramsMap.xh;
			}
			$("#xh", $("#" + this_.id)).val(xh);
			$("#fjzch", $("#" + this_.id)).val(obj.fjzch);
			$("#bjh", $("#" + this_.id)).val(obj.bjh);
			$("#xlh", $("#" + this_.id)).val(obj.xlh);
			if(this_.zxfs != 2){
				$("#eo_last_history", $("#"+this_.id)).hide();
			}
		});
	},
	/**
	 * 获取后到为准
	 */
	getHdwz : function(){
		return this.hdwz;
	},
	/**
	 * 获取计算公式
	 */
	getJsgs : function(){
		return this.jsgs;
	},
	/**
	 * 获取执行方式
	 */
	getZxfs : function(){
		return this.zxfs;
	},
	/**
	 * 通过id获取数据
	 */
	selectById : function(id,obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/initialization/selectRelEOById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	}
};