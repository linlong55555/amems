
$(document).ready(function(){
	Navigation(menuCode, '', '', 'SC2-1', '刘兵', '2017-09-27', '刘兵', '2017-09-27');//初始化导航
	maintenance_initialization_main.init();
	refreshPermission();
});
/**
 * 维修计划初始化
 */
var maintenance_initialization_main = {
		id : 'maintenance_initialization_main',
	tab_type : 1,//页签类型,1:维修项目监控,2:EO监控
	jx : '',
	init : function(){
		this.initInfo();
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(){
		this.ininClick();
		this.loadPlaneReg();
		this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
		this.buildSelect();
		this.multiselect();
		this.loadAircaraftMaintenanceMonitoring();
	},
	/**
	 * 初始化点击事件
	 */
	ininClick : function(){
		var this_ = this;
		//点击EO
		$("#EOmonitoringTab").on('shown.bs.tab', function (e){
			this_.loadEOMonitoring();
		})
		//点击维修项目
		$("#aircraftMaintenanceTab").on('shown.bs.tab', function (e){
			this_.loadAircaraftMaintenanceMonitoring()
		})
		//点击维修项目
		$("#POmonitoringTab").on('shown.bs.tab', function (e){
			this_.loadPOMonitoring();
		})
	},
	/**
	 * 机构代码改变时执行
	 */
	changeDprt : function(){
		this.loadPlaneReg();
		this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
		this.buildSelect();
		this.loadMonitoringData();
	},
	/**
	 * 改变飞机注册号时触发
	 */
	changeModel : function(){
		if(this.jx != $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx")){
			this.jx = $("#fjzch_search ", $("#"+this.id)).find("option:selected").attr("jx");
			this.buildSelect();
		}
		this.loadMonitoringData();
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
	 * 加载维修项目或EO数据
	 */
	loadMonitoringData : function(){
		if(this.tab_type == 1){
			this.loadAircaraftMaintenanceMonitoring();
		}else if(this.tab_type == 2){
			this.loadEOMonitoring();
		}else if(this.tab_type == 3){
			this.loadPOMonitoring();
		}
	},
	/**
	 * 加载飞机维修项目
	 */
	loadAircaraftMaintenanceMonitoring : function(){
		aircraft_maintenance_monitoring_main.init({
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
		this.tab_type = 1;
	},
	/**
	 * 加载EO
	 */
	loadEOMonitoring : function(){
		
		eo_monitoring_main.init({
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
		this.tab_type = 2;
	},
	/**
	 * 加载生产指令
	 */
	loadPOMonitoring : function(){
		
		po_monitoring_main.init({
			fjzch : $("#fjzch_search").val(),
			dprtcode:$("#dprtcode_search").val()
		});
		this.tab_type = 3;
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
				option += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' jx='"+StringUtil.escapeStr(planeData.FJJX)+"'  >";
				option += StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH);
				option += "</option>";
			});
		}else{
			option = '<option value="">暂无飞机 No plane</option>';
	 	}
		$("#fjzch_search").empty();
	 	$("#fjzch_search").append(option);
	}
}	