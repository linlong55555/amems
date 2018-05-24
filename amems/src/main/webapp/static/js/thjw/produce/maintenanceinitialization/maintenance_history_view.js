$(document).ready(function(){
	Navigation(menuCode, '维修历史清单', 'Maintenance History', 'SC2-2', '刘兵', '2017-09-28', '刘兵', '2017-09-28');//加载导航栏
	m_history_view.init();
});

/**
 * 维修历史清单
 */
var m_history_view = {
	id : "m_history_view",
	tbodyId : 'wxls_list',
	param: {
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(){
		this.initInfo();
	},
	initInfo : function(obj){//初始化信息
		this.initShowOrHideTask(obj);
		this.loadHistory();
	},
	/**
	 * 初始化显示或隐藏任务信息,维修项目监控或EO监控
	 */
	initShowOrHideTask : function(){
		$("#m_task_info", $("#"+this.id)).hide();
		$("#eo_task_info", $("#"+this.id)).hide();
		$("#po_task_info", $("#"+this.id)).hide();
		if($("#historyType").val() == 1){
			$("#m_task_info", $("#"+this.id)).show();
			m_task_info_view.show({
				parentObj : 'body',//父对象
				id : $("#id").val()//监控对象id
			});
		}else if($("#historyType").val() == 2){
			$("#eo_task_info", $("#"+this.id)).show();
			eo_task_info_view.show({
				id : $("#id").val()//监控对象id
			});
		}else{
			$("#po_task_info", $("#"+this.id)).show();
			po_task_info_view.show({
				parentObj : 'body',//父对象
				id : $("#id").val()//监控对象id
			});
		}
	},
	/**
	 * 加载执行历史数据
	 */
	loadHistory : function(){
		var this_ = this;
		executionHistory.init({
			parentObj : this_,
			zt : 10,
			jksjid : $("#id").val(),
			fjzch : $("#fjzch").val(),
			dprtcode:$("#dprtcode").val()
		});
	}
}
