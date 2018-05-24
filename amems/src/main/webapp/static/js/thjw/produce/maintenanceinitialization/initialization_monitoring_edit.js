/**
 * 初始化监控编辑
 */
initialization_monitoring_edit_alert_Modal = {
	id : "initialization_monitoring_edit_alert_Modal",
	hdwz : '',//后到为准
	jsgs : 1,//计算公式
	zxfs : 1,//执行方式
	taskId : 'm_task_info_view',//任务id
	isLoad:false,//是否加载
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	param: {
		id:'',
		modalHeadCN : '飞机维修项目监控',
		modalHeadENG : 'Aircraft Maintenance Monitoring',
		menuType:1,//菜单类型,1:维修计划初始化,2:飞机维修监控
		editType:1,//编辑类型,1:维修项目监控,2:EO监控,3:生产指令监控
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
		this.initShowOrHideTask();
		this.selectById(this_.param.id,function(obj){
			if(formatUndefine(obj.fJksjid) == ''){
				m_task_info_view.initShowOrHide(false);
				eo_task_info_view.initShowOrHide(false);
				po_task_info_view.initShowOrHide(false);
			}
			last_plan_view.load(obj, this_.taskId);
			next_plan_starting_point_edit.show({
				id : this_.param.id,//监控对象id
				menuType:this_.param.menuType,//菜单类型,1:维修计划初始化,2:飞机维修监控
				editType:this_.param.editType,//编辑类型,1:维修项目监控,2:EO监控,3:生产指令监控
				monitorObj : obj,//监控对象数据
				hdwz : this_.hdwz,//后到为准
				jsgs : this_.jsgs,//计算公式
				zxfs : this_.zxfs//执行方式
			});
		});
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化显示或隐藏任务信息,维修项目监控或EO监控
	 */
	initShowOrHideTask : function(){
		var this_ = this;
		$("#save_btn", $("#"+this.id)).show();
		$("#eo_task_info", $("#"+this.id)).hide();
		$("#m_task_info", $("#"+this.id)).hide();
		$("#po_task_info", $("#"+this.id)).hide();
		if(this.param.editType == 1){
			$("#m_task_info", $("#"+this.id)).show();
			this_.taskId = "m_task_info_view";
			m_task_info_view.show({
				id : this_.param.id,//监控对象id
				parentObj : "#"+this_.id,//父对象
				isShow : true//是否显示上版本执行历史
			});
			this.hdwz = m_task_info_view.getHdwz();
			this.jsgs = m_task_info_view.getJsgs();
			
		}else if(this.param.editType == 2){
			$("#eo_task_info", $("#"+this.id)).show();
			this_.taskId = "eo_task_info_view";
			eo_task_info_view.show({
				id : this_.param.id,//监控对象id
				isShow : true//是否显示上版本执行历史
			});
			this.hdwz = eo_task_info_view.getHdwz();
			this.jsgs = eo_task_info_view.getJsgs();
			this.zxfs = eo_task_info_view.getZxfs();
			if(this.zxfs != 2){
				$("#save_btn", $("#"+this.id)).hide();
			}
		}else{
			$("#po_task_info", $("#"+this.id)).show();
			this_.taskId = "po_task_info_view";
			po_task_info_view.show({
				id : this_.param.id,//监控对象id
				parentObj : "#"+this_.id,//父对象
				isShow : true//是否显示上版本执行历史
			});
			this.hdwz = po_task_info_view.getHdwz();
			this.jsgs = po_task_info_view.getJsgs();
		}
	},
	/**
	 * 通过id获取数据
	 */
	selectById : function(id,obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/initialization/getMonitoringObjectById",
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
	},
	/**
	 * 保存数据
	 * operationType submit:提交
	 */
	setData: function(operationType){
		var this_ = this;
		var param = {};
		param.id = this_.param.id;
		var monitoringPlanList = next_plan_starting_point_edit.getData();
		param.monitoringPlanList = monitoringPlanList;
		
		//验证上次计划
		if(!next_plan_starting_point_edit.isValid){
			return false;
		}
		
		var this_ = this;
		startWait($("#initialization_monitoring_edit_alert_Modal"));
		var url = basePath+"/produce/maintenance/initialization/save";
		if(this_.param.menuType == 2){
			url = basePath+"/produce/maintenance/monitoring/save";
		}
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#initialization_monitoring_edit_alert_Modal"),
			success:function(data){
				finishWait($("#initialization_monitoring_edit_alert_Modal"));
				AlertUtil.showMessage("保存成功！");
				this_.close();
				if(this_.param.callback && typeof(this_.param.callback) == "function"){
					this_.param.callback(data);
				}
			}
		});
	},
	/**
	 * 关闭弹窗
	 */
	close : function(){
		$("#"+this.id).modal("hide");
	}
}

