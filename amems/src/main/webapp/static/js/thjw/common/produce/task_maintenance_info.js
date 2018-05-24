/**
 * 维修项目任务信息
 */
m_task_info_view = {
	id:'m_task_info_view',
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
			$("#m_last_history", $("#"+this.id)).show();
		}else{
			$("#m_last_history", $("#"+this.id)).hide();
		}
	},
	/**
	 * 加载数据
	 */
	load : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			if(obj.maintenanceProject != null){
				$("#rwdh", $("#" + this_.id)).val(obj.maintenanceProject.rwh + " R" + obj.maintenanceProject.bb);
				$("#ckh", $("#" + this_.id)).val(obj.maintenanceProject.ckh);
				$("#gkh", $("#" + this_.id)).val(obj.paramsMap.gkbh);
				$("#rwms", $("#" + this_.id)).val(obj.maintenanceProject.rwms);
				var zjh = StringUtil.escapeStr(obj.paramsMap.zjh) + " " + StringUtil.escapeStr(obj.paramsMap.zjhywms);
				$("#zjh", $("#" + this_.id)).val(zjh);
				var jsgs = 1;
				if(obj.maintenanceProject.jsgs != null){
					jsgs = obj.maintenanceProject.jsgs;
				}
				this_.jsgs = jsgs;
				this_.hdwz = obj.maintenanceProject.isHdwz;
			}
			$("#fjzch", $("#" + this_.id)).val(obj.fjzch);
			$("#bjh", $("#" + this_.id)).val(obj.bjh);
			$("#xlh", $("#" + this_.id)).val(obj.xlh);
			var attachment = obj.attachment;
			var t = "";
//			if(attachment != null){
//				 t += "<a title='"+(StringUtil.escapeStr(attachment.wbwjm)+"."+attachment.hzm)+"' href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+attachment.id+"')>"+(StringUtil.escapeStr(attachment.wbwjm)+"."+attachment.hzm)+"</a>";
//			}
			if((obj.paramsMap.attachCount != null && obj.paramsMap.attachCount > 0) 
					|| (obj.paramsMap.gkfjid != null && obj.paramsMap.gkfjid != "")
					|| (obj.paramsMap.gznrfjid != null && obj.paramsMap.gznrfjid != "")){
					t = '<i qtid="'+obj.paramsMap.gkid+'" gkfjid="'+obj.paramsMap.gkfjid+'" gznrfjid="'+obj.paramsMap.gznrfjid+'" class="attachment-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			$("#gkfj", $("#"+this_.id)).html(t);
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
			url:basePath+"/produce/maintenance/initialization/selectRelProjectById",
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