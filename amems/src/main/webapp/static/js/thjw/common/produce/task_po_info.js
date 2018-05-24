/**
 * 维修项目任务信息
 */
po_task_info_view = {
	id:'po_task_info_view',
	param: {
		id : '',//监控对象id
		parentObj : '',//父对象
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
			$("#po_last_history", $("#"+this.id)).show();
		}else{
			$("#po_last_history", $("#"+this.id)).hide();
		}
	},
	/**
	 * 加载数据
	 */
	load : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			var paramsMap = obj.paramsMap?obj.paramsMap:{};
			$("#zlbh_po", $("#" + this_.id)).val(paramsMap.zlbh + " R" + paramsMap.bb);
			$("#gkh_po", $("#" + this_.id)).val(paramsMap.gkbh);
			$("#zlms_po", $("#" + this_.id)).val(paramsMap.zlms);
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			$("#zjh_po", $("#" + this_.id)).val(zjh);
			var jsgs = 1;
			if(paramsMap.jsgs != null){
				jsgs = paramsMap.jsgs;
			}
			this_.jsgs = jsgs;
			this_.hdwz = paramsMap.hdwz;
			$("#fjzch_po", $("#" + this_.id)).val(obj.fjzch);
			var attachment = obj.attachment;
			var t = "";
			if((obj.paramsMap.attachCount != null && obj.paramsMap.attachCount > 0) 
					|| (obj.paramsMap.gkfjid != null && obj.paramsMap.gkfjid != "")
					|| (obj.paramsMap.gznrfjid != null && obj.paramsMap.gznrfjid != "")){
					t = '<i qtid="'+obj.paramsMap.gkid+'" gkfjid="'+obj.paramsMap.gkfjid+'" gznrfjid="'+obj.paramsMap.gznrfjid+'" class="attachment-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			$("#gkfj_po", $("#"+this_.id)).html(t);
			this_.initWebuiPopover();
		});
	},
	initWebuiPopover : function(){
		var this_ = this;
		var parentObj = this_.param.parentObj;
		WebuiPopoverUtil.initWebuiPopover('attachment-gk-view', parentObj,function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
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
	 * 通过id获取数据
	 */
	selectById : function(id,obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/initialization/selectRelPoById",
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