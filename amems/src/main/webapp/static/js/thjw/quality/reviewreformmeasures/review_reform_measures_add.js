
	var reviewReformMeasures = {
		
		id : "review_reform_measures_alert_Modal",
		
		// 页面初始化
		init : function(){
			
			var this_ = this;
			// 加载附件
			this_.loadAttachment();
			// 加载数据
			this_.loadData();
		},
		
		param : {
			data:{},
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			this.init();
			$("#"+this.id).modal("show");
		},
		// 下载文件
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		
		// 加载附件
		loadAttachment : function(){
			var this_ = this;
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				attachHead : true,
				djid:formatUndefine(this_.param.data.id),
				fileType:"noticeOfDiscovery",
		 		colOptionhead : true,
				fileHead : true,
				left_column : false,
			});//显示附件
		},
		
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;
			if(data.wtfkrid == userId){
				$("#assign_btn").show();
			}else{
				$("#assign_btn").hide();
			}
			preventive_measure.show({
				data:data,
				option:true,
				modal:this_.id,
			});
			measures_record.show({
				id:data.id,
				dprtcode:data.dprtcode,
				modal:this_.id
			});
			this_.fillData(data);
		},
		fillData : function(obj){
			$("#shwtbh").val(obj.shwtbh);
			$("#sjShrq").val(obj.auditDiscovery?(obj.auditDiscovery.sjShrq?obj.auditDiscovery.sjShrq.substring(0,10):""):"");
			$("#lx").val(obj.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverTypeEnum',obj.auditDiscovery.lx)));
			$("#shlb").val(obj.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',obj.auditDiscovery.shlb)));					
			$("#shdx").val(obj.auditDiscovery?(StringUtil.escapeStr(obj.auditDiscovery.shdxbh)+" "+StringUtil.escapeStr(obj.auditDiscovery.shdxmc)):"");			
			$("#zrr").val(obj.auditDiscovery?(StringUtil.escapeStr(obj.auditDiscovery.zrrbh)+" "+StringUtil.escapeStr(obj.auditDiscovery.zrrmc)):"");
			$("#wtdj").val(obj.wtdj);
			$("#wtfl").val(obj.wtfl);
			$("#yqfkrq").val(obj.auditDiscovery?(obj.auditDiscovery.yqfkrq?obj.auditDiscovery.yqfkrq.substring(0,10):""):"");
			$("#wtms").val(obj.wtms);
			$("#zgjy").val(obj.auditDiscovery?obj.auditDiscovery.zgjy:"");
			this.initWebuiPopover();
		},
		// 获取数据
		getData : function(){
			var obj = {};
			obj = preventive_measure.getData();
			obj.id = this.param.data.id;
			obj.attachments = attachmentsUtil.getAttachmentsComponents('attachments_list_edit').getAttachments();
			return obj;
		},
		initWebuiPopover : function(){
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#"+this_.id,function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
		},
		getHistoryAttachmentList:function(obj){//获取历史附件列表
			var id = this.param.data.auditDiscovery.id;
			var jsonData = [
			   	         {mainid : id, type : '附件'}
			   	    ];
			 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		// 保存
		saveData : function(){
			var this_ = this;
			var obj = this_.getData();
			obj.zt = this_.param.data.zt;
			var paramsMap={};
			paramsMap.editType="1";
			obj.paramsMap = paramsMap;
			if(!this_.valid(obj)){
				return false;
			}
			if(this.param.callback && typeof(this.param.callback) == "function"){
				this_.param.callback(obj);
			}
		},
		// 提交
		submitData : function(){
			var this_ = this;
			var obj = this_.getData();
			obj.zt = 2;
			var paramsMap={};
			paramsMap.editType="2";
			obj.paramsMap = paramsMap;
			if(!this_.valid(obj)){
				return false;
			}
			if(this.param.callback && typeof(this.param.callback) == "function"){
				this_.param.callback(obj);
			}
		},
		//指派
		assignExecutor : function(){
			var this_ = this;
			chooseUser(this_.param.data.dprtcode,function(obj){
//				var param = this_.getData();
				
				var param = {};
				param.yyfx = $.trim($("#"+this_.param.modal+ " #yyfx").val());				
				param.jzcs = $.trim($("#"+this_.param.modal+ " #jzcs").val());
				param.yfcs = $.trim($("#"+this_.param.modal+ " #yfcs").val());
				
				param.id = this_.param.data.id;
				param.attachments = attachmentsUtil.getAttachmentsComponents('attachments_list_edit').getAttachments();
				
				param.zxrbmid = obj.bmdm;
				param.zxrid = obj.id;
				var paramsMap = {};
				paramsMap.username = obj.username +" " +obj.realname;
				paramsMap.editType="3";
				param.paramsMap=paramsMap;				
				if(!this_.valid(param)){
					return false;
				}
				this_.param.callback(param);
			});
		},
		valid:function(data){
			if(!preventive_measure.isValid){
				return false;
			}
			return true;
		},
	};
	
	