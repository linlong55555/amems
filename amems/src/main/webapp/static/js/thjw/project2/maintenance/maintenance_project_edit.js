
open_win_modal = {
	id : 'open_win_maintenance_project_modal',
	// 新增维修项目
	openProjectWinAdd : function(){
		var this_ = this;
		var param = open_win_maintenance_project_modal.getInitData(userJgdm);
		// 加载维修项目默认参数
		$.extend(param, this_.getProjectDefaultParam());
		var chinaHead = "新增维修项目";
		var englishHead = "Add Maintenance Item";
		open_win_maintenance_project_modal.show({
	 		chinaHead : chinaHead,
	 		englishHead : englishHead,
	 		win_type : 1,
	 		ope_type : 1,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
			editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					startWait($("#"+this_.id));
					var url = basePath+"/project2/maintenanceproject/save";
					this_.performAction(url,data,"保存成功!","add");
				}
			}
		});
	},
	// 修改维修项目
	openProjectWinEdit : function(id){
		var this_ = this;
		this_.wxxmid = id;
		var param = open_win_maintenance_project_modal.getInitData(userJgdm);
		// 加载维修项目默认参数
		$.extend(param, this_.getProjectDefaultParam());
		// 读取维修维修项目数据
		$.extend(param, this_.loadData(id));
		var chinaHead = "修改维修项目";
		var englishHead = "Edit Maintenance Item";
		open_win_maintenance_project_modal.show({
	 		chinaHead : chinaHead,
	 		englishHead : englishHead,
	 		win_type : 1,
	 		ope_type : 2,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
			editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					startWait($("#"+this_.id));
					var url = basePath+"/project2/maintenanceproject/save";
					this_.performAction(url,data,"保存成功!","edit");
				}
			}
		});
	},
	// 改版维修项目
	openProjectWinRevision : function(id){
		var this_ = this;
		this_.wxxmid = id;
		var param = open_win_maintenance_project_modal.getInitData(userJgdm);
		// 加载维修项目默认参数
		$.extend(param, this_.getProjectDefaultParam());
		// 读取维修维修项目数据
		$.extend(param, this_.loadData(id));
		var chinaHead = "改版维修项目";
		var englishHead = "Revision Maintenance Item";
		open_win_maintenance_project_modal.show({
	 		chinaHead : chinaHead,
	 		englishHead : englishHead,
	 		win_type : 1,
	 		ope_type : 3,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
			editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					AlertUtil.showConfirmMessage("确定要改版吗？", {callback: function(){
						startWait($("#alertConfirmModal"));
						var url = basePath+"/project2/maintenanceproject/revision";
						this_.performAction(url,data,"改版成功!","revision");
					}});
				}
				finishWait($("#"+this_.id));
			}
		});
	},
	// 新增定检包
	openCheckWinAdd : function(){
		var this_ = this;
		var jx = this.getJx();
		var param = $.extend({}, this_.getCheckDefaultParam());
		// 加载定检包默认参数
		open_win_maintenance_project_modal.show({
	 		chinaHead : "新增定检包",
	 		englishHead : "Add Scheduled Pack",
	 		win_type : 2,
	 		ope_type : 1,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
	 		editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					if (data != null) {
						startWait($("#"+this_.id));
						var url = basePath+"/project2/maintenanceproject/save";
						this_.performAction(url,data,"保存成功!","add");
					}
				}
			}
		});
	},
	// 修改定检包
	openCheckWinEdit : function(id){
		var this_ = this;
		var jx = this.getJx();
		var param = $.extend(param, this_.getCheckDefaultParam());
		// 读取维修维修项目数据
		$.extend(param, this_.loadData(id));
		// 加载定检包默认参数
		open_win_maintenance_project_modal.show({
	 		chinaHead : "修改定检包",
	 		englishHead : "Edit Scheduled Pack",
	 		win_type : 2,
	 		ope_type : 2,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
	 		editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					if (data != null) {
						startWait($("#"+this_.id));
						var url = basePath+"/project2/maintenanceproject/save";
						this_.performAction(url,data,"保存成功!","edit");
					}
				}
			}
		});
	},
	// 改版定检包
	openCheckWinRevision : function(id){
		var this_ = this;
		var jx = this.getJx();
		var param = $.extend(param, this_.getCheckDefaultParam());
		// 读取维修维修项目数据
		$.extend(param, this_.loadData(id));
		// 加载定检包默认参数
		open_win_maintenance_project_modal.show({
	 		chinaHead : "改版定检包",
	 		englishHead : "Revision Scheduled Pack",
	 		win_type : 2,
	 		ope_type : 3,
	 		jx : param.jx,
	 		wxfabh : param.wxfabh,
	 		editData : param,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					AlertUtil.showConfirmMessage("确定要改版吗？", {callback: function(){
						startWait($("#alertConfirmModal"));
						var url = basePath+"/project2/maintenanceproject/revision";
						this_.performAction(url,data,"改版成功!","revision");
					}});
				}
				finishWait($("#"+this_.id));
			}
		});
	},
	getJx : function(){
		return $("#fjjx").val();
	},
	getWxfaid : function(){
		return maintenancePlan.wxfa?maintenancePlan.wxfa.id:"";
	},
	getWxfabh : function(){
		return maintenancePlan.wxfa?maintenancePlan.wxfa.wxfabh:"";
	},
	getWxfaBb : function(){
		return maintenancePlan.wxfa?maintenancePlan.wxfa.bb:"";
	},
	getWxfaZt : function(){
		return maintenancePlan.wxfa?maintenancePlan.wxfa.zt:"";
	},
	// 加载维修项目默认参数
	getProjectDefaultParam : function(){
		var this_ = this;
		
		return {
			id : "",
			zt : 1,
			associateCnHead : "取代维修项目",
			associateEngHead : "Replace",
			associateColCnHead : "取代维修项目",
			associateColEngHead : "Replace",
			jx : this_.getJx(),
			wxfaid : this_.getWxfaid(),
			wxfabh : this_.getWxfabh(),
			syx : ["00000"],
			wxfaBb : this_.getWxfaBb(),
			isBj : 1,
			ali : 1,
			yxbs : 1,
			isHdwz : 0,
	 		jsgs : 1,
			wxxmlx : 1
		};
	},
	// 加载定检包默认参数
	getCheckDefaultParam : function(){
		var this_ = this;
		return {
			id : "",
			zt : 1,
			associateCnHead : "维修项目关联",
			associateEngHead : "Maintenance Item",
			associateColCnHead : "维修项目关联",
			associateColEngHead : "Maintenance Item",
			jx : this_.getJx(),
			wxfaid : this_.getWxfaid(),
			wxfabh : this_.getWxfabh(),
			wxfaBb : this_.getWxfaBb(),
			wxxmlx : 4,
			isHdwz : 0,
	 		jsgs : 1,
			yxbs : 1
		};
	},
	// 加载维修方案数据
	loadData : function(id){
		var this_ = this;
		var param = {};
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/maintenanceproject/detail",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify({
				id : id,
				zt : this_.getWxfaZt()
			}),
			dataType:"json",
			success:function(data){
				param = data;
			}
		});
		return param;
	},
	performAction : function(url,param,message,operation){
		var this_ = this;
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			modal : $("#"+this_.id),
			dataType:"json",
			success:function(data){
				finishWait($("#"+this_.id));
				finishWait($("#alertConfirmModal"));
				AlertUtil.showMessage(message);
				data.option = operation;
				maintenanceItemList.reloadRow(param, data);
				open_win_maintenance_project_modal.close();
			}
		});
	}
};
