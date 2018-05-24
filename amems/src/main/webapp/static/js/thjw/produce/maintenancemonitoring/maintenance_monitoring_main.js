
$(document).ready(function(){
	Navigation(menuCode, '', '', 'SC3-1', '刘兵', '2017-10-09', '刘兵', '2017-10-09');//初始化导航
	maintenance_monitoring_main.init();
	refreshPermission();
});

var maintenance_monitoring_main = {
	id : 'maintenance_monitoring_main',
	tab_ui_Id : 'tab_child_div',
	tab_type : 1,//页签类型,1:飞机维修项目监控,2:EO监控,3:生产指令,4:NRC工单,5:已选择,6:预组包
	surplus : {},//剩余对象
	plan : {},//计划对象
	jx : '',
	msn : '',
	selectTR:null,
	init : function(){
		this.initInfo();
	},
	initInfo : function(){
		this.loadPlaneReg();
		this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
		this.msn = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("msn");
		this.buildSelect();
		this.multiselect();
		this.initCount();
		if(paramJgdm != null && paramJgdm != ''){
	    	$("#dprtcode_search").val(paramJgdm);
	    	paramJgdm = null;
		}
	    if(paramFjzch != null && paramFjzch != ''){
	    	var fjzch = decodeURIComponent(Base64.decode(paramFjzch));
	    	$("#fjzch_search").val(fjzch);
	    	paramFjzch = null;
		}
		this.loadMonitoringData();
		this.initWebuiPopover();
	},
	/**
	 * 加载飞机当前值
	 */
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('current-status','body',function(obj){
			return this_.getCurrentStatusList(obj);
		});
	},
	/**
	 * 获取当前状态
	 */
	getCurrentStatusList : function(obj){
		var html = '';
		var fjzch = $("#fjzch_search", $("#"+this.id)).val();
		var dprtcode = $("#dprtcode_search", $("#"+this.id)).val();
   		return current_monitor_data_alert_Modal.getContextData(fjzch, dprtcode);
	},
	/**
	 * 机构代码改变时执行
	 */
	changeDprt : function(){
		this.loadPlaneReg();
		this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
		this.msn = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("msn");
		this.buildSelect();
		this.loadMonitoringData();
		this.initCount();
	},
	/**
	 * 改变飞机注册号时触发
	 */
	changeModel : function(){
		if(this.jx != $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx")){
			this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
			this.msn = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("msn");
			this.buildSelect();
		}
		this.loadMonitoringData();
		this.initCount();
	},
	/**
	 * 加载维修项目大类
	 */
	buildSelect : function(){
		aircraft_maintenance_monitoring_main.buildSelect(this.jx, $("#dprtcode_search", $("#"+this.id)).val());
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		aircraft_maintenance_monitoring_main.multiselect();
	},
	/**
	 * 初始化已选中及预组包数量
	 */
	initCount : function(){
		var this_ = this;
		var fjzch = $("#fjzch_search", $("#"+this_.id)).val();
		var dprtcode = $("#dprtcode_search", $("#"+this_.id)).val();
		this_.getCheckCount(fjzch, dprtcode, function(count){
			this_.setCheckCount(count);
		});
		this_.getBurstificationCount(fjzch, dprtcode, function(count){
			this_.setBurstificationCount(count);
		});
		
		
	},
	/**
	 * 加载数据
	 */
	loadMonitoringData : function(){
		if(this.tab_type == 1){
			this.loadAircaraftMaintenanceMonitoring();
		}else if(this.tab_type == 2){
			this.loadEOMonitoring();
		}else if(this.tab_type == 3){
			this.loadPOMonitoring();
		}else if(this.tab_type == 4){
			this.loadNRCWorkOrder();
		}else if(this.tab_type == 5){
			this.loadCheckMonitoring();
		}else if(this.tab_type == 6){
			this.loadBurstification();
		}
	},
	/**
	 * 加载飞机维修项目
	 */
	loadAircaraftMaintenanceMonitoring : function(){
		var this_ = this;
		var warning = 'nochecked';
		if($("#warning_search_checkbox", $("#"+this_.id)).is(":checked")){
			warning = 'checked';
		}
		this.hideBottom();
		aircraft_maintenance_monitoring_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search", $("#"+this_.id)).val(),
			warning : warning,
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search", $("#"+this_.id)).val()
		});
		this.tab_type = 1;
		this.setChlidTabShow();
		$(".child_li_div", $("#"+this_.id)).removeClass("active in");
		$("#md_tab", $("#"+this_.id)).addClass("active in");
		$("#mdTab", $("#"+this_.id)).addClass("active in");
	},
	/**
	 * 加载EO
	 */
	loadEOMonitoring : function(){
		var this_ = this;
		var warning = 'nochecked';
		if($("#warning_search_checkbox", $("#"+this_.id)).is(":checked")){
			warning = 'checked';
		}
		this.hideBottom();
		eo_monitoring_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search").val(),
			warning : warning,
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search").val()
		});
		this.tab_type = 2;
		this.setMdHide();
		this.setToolShow();
		this.setHistoryShow();
		$(".child_li_div", $("#"+this_.id)).removeClass("active in");
		$("#tool_tab", $("#"+this_.id)).addClass("active in");
		$("#toolTab", $("#"+this_.id)).addClass("active in");
	},
	/**
	 * 加载PO
	 */
	loadPOMonitoring : function(){
		var this_ = this;
		var warning = 'nochecked';
		if($("#warning_search_checkbox", $("#"+this_.id)).is(":checked")){
			warning = 'checked';
		}
		this.hideBottom();
		po_monitoring_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search").val(),
			warning : warning,
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search").val()
		});
		this.tab_type = 3;
		this.setMdHide();
		this.setToolHide();
		this.setHistoryShow();
		$(".child_li_div", $("#"+this_.id)).removeClass("active in");
		$("#history_tab", $("#"+this_.id)).addClass("active in");
		$("#historyTab", $("#"+this_.id)).addClass("active in");
	},
	/**
	 * 加载NRC工单
	 */
	loadNRCWorkOrder : function(){
		var this_ = this;
		this.hideBottom();
		nrc_work_order_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search", $("#"+this_.id)).val(),
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search", $("#"+this_.id)).val()
		});
		this.tab_type = 4;
		this.setMdHide();
		this.setToolShow();
		this.setHistoryHide();
		$(".child_li_div", $("#"+this_.id)).removeClass("active in");
		$("#tool_tab", $("#"+this_.id)).addClass("active in");
		$("#toolTab", $("#"+this_.id)).addClass("active in");
	},
	/**
	 * 加载已选中
	 */
	loadCheckMonitoring : function(){
		var this_ = this;
		this.hideBottom();
		check_monitoring_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search", $("#"+this_.id)).val(),
			msn : this_.msn,
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search", $("#"+this_.id)).val()
		});
		this.tab_type = 5;
		this.setChlidTabHide();
	},
	/**
	 * 加载预组包
	 */
	loadBurstification : function(){
		var this_ = this;
		this.hideBottom();
		burstification_main.init({
			parentObj : this_,
			fjzch : $("#fjzch_search", $("#"+this_.id)).val(),
			surplus : this_.surplus,//剩余对象
			plan : this_.plan,//计划对象
			zjh : $("#zjh_search", $("#"+this_.id)).val(),//章节号
			dprtcode:$("#dprtcode_search", $("#"+this_.id)).val()
		});
		this.tab_type = 6;
		this.setChlidTabHide();
	},
	hideBottom:function(){
		$(this.selectTR).removeClass("bg_tr");
		this.selectTR = null;
		$(".displayTabContent").css("display","none");	
		App.resizeHeight();
	},
	/**
	 * 设置所有子类tab隐藏
	 */
	setChlidTabHide : function(){
		$("#md_tab", $("#"+this.tab_ui_Id)).hide();
		$("#tool_tab", $("#"+this.tab_ui_Id)).hide();
		$("#history_tab", $("#"+this.tab_ui_Id)).hide();
	},
	/**
	 * 设置所有子类tab显示
	 */
	setChlidTabShow : function(){
		this.setMdShow();
		this.setToolShow();
		this.setHistoryShow();
	},
	/**
	 * 设置维修清单显示
	 */
	setMdShow : function(){
		$("#md_tab", $("#"+this.tab_ui_Id)).show();
	},
	/**
	 * 设置航材工具显示
	 */
	setToolShow : function(){
		$("#tool_tab", $("#"+this.tab_ui_Id)).show();
	},
	/**
	 * 设置执行历史显示
	 */
	setHistoryShow : function(){
		$("#history_tab", $("#"+this.tab_ui_Id)).show();
	},
	/**
	 * 设置维修清单隐藏
	 */
	setMdHide : function(){
		$("#md_tab", $("#"+this.tab_ui_Id)).hide();
	},
	/**
	 * 设置航材工具隐藏
	 */
	setToolHide : function(){
		$("#tool_tab", $("#"+this.tab_ui_Id)).hide();
	},
	/**
	 * 设置执行历史隐藏
	 */
	setHistoryHide : function(){
		$("#history_tab", $("#"+this.tab_ui_Id)).hide();
	},
	/**
	 * 加载飞机注册号
	 */
	loadPlaneReg : function(){
		var dprtcode = $.trim($("#dprtcode_search").val());
		if(typeof(dprtcode) == "undefined"){
			dprtcode = userJgdm;
		}
		var data= acAndTypeUtil.getACRegList({DPRTCODE:dprtcode,FJJX:''});
		var option = '';
		if(data.length >0){
			$.each(data,function(i,planeData){
				option += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' msn='"+StringUtil.escapeStr(planeData.XLH)+"' jx='"+StringUtil.escapeStr(planeData.FJJX)+"'  >";
				option += StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH);
				option += "</option>";
			});
		}else{
			option = '<option value="">暂无飞机 No plane</option>';
	 	}
		$("#fjzch_search").empty();
	 	$("#fjzch_search").append(option);
	},
	/**
	 * 加载航材工具
	 */
	loadTool : function(id, type){
		material_tool_list.init({
			id : id,
			type : type,
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
	},
	/**
	 * 加载维修清单
	 */
	loadWxqd : function(id, type){
		var this_ = this;
		maintenance_detail_list.init({
			parentObj : this_,
			id : id,
			type : type,
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
	},
	/**
	 * 加载执行历史
	 */
	loadZxls : function(jksjid){
		var this_ = this;
		executionHistory.init({
			zt : 10,
			jksjid : jksjid,
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
	},
	/**
	 * 打开剩余对话框
	 */
	openSurplusWin : function(){
		var this_ = this;
		var sy = this_.surplus;
		SurplusWinModal.show({
			type:1,//1:剩余,2:计划
			viewObj:sy,//原值，在弹窗中回显
			modalHeadCN : '剩余',
			modalHeadENG : 'Remain',
			callback: function(data){//回调函数			
					var obj = "";
					this_.surplus = {};
					if(data != null && data.monitorStr != null && data.monitorStr != ""){
						obj = data.monitorStr;
						this_.surplus = data;
					}
					$("#sy_search", $("#"+this_.id)).val(obj);
					this_.loadMonitoringData();
			}
		});
	},
	/**
	 * 打开计划对话框
	 */
	openPlanWin : function(){
		var this_ = this;
		var plan = this_.plan;
		SurplusWinModal.show({
			type:2,//1:剩余,2:计划
			viewObj:plan,//原值，在弹窗中回显
			modalHeadCN : '计划',
			modalHeadENG : 'Plan',
			callback: function(data){//回调函数			
				var obj = "";
				this_.plan = {};
				if(data != null && data.monitorStr != null && data.monitorStr != ""){
					obj = data.monitorStr;
					this_.plan = data;
				}
				$("#plan_search", $("#"+this_.id)).val(obj);
				this_.loadMonitoringData();
			}
		});
	},
	/**
	 * 打开ATA章节号对话框
	 */
	openChapterWin : function(){
		var this_ = this;
		var zjh = $.trim($("#zjh_search", $("#"+this_.id)).val());
		FixChapterModal.show({
			parentWinId : '',
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:this_.dprtcode,//机构代码
			callback: function(data){//回调函数			
					var zjh=data.zjh==null?"":data.zjh;
					$("#zjh_search", $("#"+this_.id)).val(zjh);
					this_.loadMonitoringData();
			}
		});
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
		}else{
			value = " "+"</br>";
		}
		return value;
	},
	/**
	 * 分钟转小时
	 */
	convertToHour : function(jklbh, value){
		if(MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	/**
	 * 获取已选中数量
	 */
	getCheckCount : function(fjzch, dprtcode, obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/monitoring/getCheckCount",
			type:"post",
			data:{fjzch : fjzch, dprtcode : dprtcode},
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
	 * 设置已选中数量
	 */
	setCheckCount : function(count){
		count = count?count:0;
		$("#check_count", $("#" + this.id)).html(count);
	},
	/**
	 * 获取预组包数量
	 */
	getBurstificationCount : function(fjzch, dprtcode, obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/monitoring/getBurstificationCount",
			type:"post",
			data:{fjzch : fjzch, dprtcode : dprtcode},
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
	 * 设置预组包数量
	 */
	setBurstificationCount : function(count){
		count = count?count:0;
		$("#package_count", $("#" + this.id)).html(count);
	},
	/**
	 * 重置
	 */
	searchreset : function(){
		$("#dprtcode_search").val(userJgdm);
		this.loadPlaneReg();
		this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
		this.msn = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("msn");
		this.buildSelect();
		this.initCount();
		$("#keyword_search", $("#aircraft_maintenance_monitoring_main")).val('');
		$("#keyword_search", $("#eo_monitoring_main")).val('');
		$("#keyword_search", $("#nrc_work_order_main")).val('');
		$("#zjh_search").val('');
		$("#plan_search").val('');
		$("#sy_search").val('');
		this.plan = {};
		this.surplus = {};
		this.loadMonitoringData();
		
	},
	/**
	 * 查询条件更多 展开/收缩
	 */
	more: function() {
		if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
			$("#divSearch", $("#"+this.id)).css("display", "block");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this.id)).css("display", "none");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this.id)).addClass("icon-caret-down");
		}
	}
}	