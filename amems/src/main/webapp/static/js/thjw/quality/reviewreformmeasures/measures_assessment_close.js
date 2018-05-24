
	var reviewReformMeasures = {
	
		// 页面初始化
		init : function(){
			
			var this_ = this;
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
		},
		
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;
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
			
		},
		// 获取数据
		getData : function(){
			var obj = {};
			obj = preventive_measure.getData();
			obj.id = this.param.data.id;
			obj.attachments = attachmentsUtil.getAttachmentsComponents('attachments_list_edit').getAttachments();
			return obj;
		},
		
		//指派执行人
		assignExecutor:function(){
			$("#assign_executor_alert_modal").modal("show");
		},
		
		// 保存
		saveData : function(){
			var this_ = this;
			var obj = this_.getData();
			obj.zt = this_.param.data.zt;
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
				var param = this_.getData();
				param.zt = 2;
				param.zxrbmid = obj.bmdm;
				param.zxrid = obj.id;
				var paramsMap = {};
				paramsMap.username = obj.realname+" "+obj.username;
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
	
	