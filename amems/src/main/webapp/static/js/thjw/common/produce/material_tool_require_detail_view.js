$(document).ready(function(){
	Navigation(menuCode, '航材工具需求清单', 'Tool Detail', 'GC-7-2', '刘兵', '2017-10-24', '刘兵', '2017-10-24');//加载导航栏
	material_tool_require_view.init();
});

/**
 * 航材工具需求清单
 */
var material_tool_require_view = {
	id : "material_tool_require_view",
	param: {
		fjzch : '',
		msn : '',
		idList : [],//用于查询任务信息
		lyidList : [],//用于查航材工具设备
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data : [],
	init : function(){
		var this_ = this;
		this_.initInfo();
	},
	initInfo : function(){//初始化信息
		try{
			//是否支持本地存储
			if(window.localStorage){
				var param = window.localStorage.getItem(type);
				var obj = JSON.parse(param);
				this.param.fjzch = obj.fjzch;
				this.param.dprtcode = obj.dprtcode;
				this.param.msn = obj.msn;
				this.param.idList = obj.idList;
				this.param.lyidList = obj.lyidList;
				window.localStorage.removeItem(type);
			}
			this.initShowOrHideTask();
		}catch(e){
			this.initShowOrHideTask();
		}
	},
	/**
	 * 初始化显示或隐藏任务信息,维修项目监控或EO监控
	 */
	initShowOrHideTask : function(){
		var this_ = this;
		if(type == 1){
			$("#m_task_info", $("#"+this.id)).show();
			material_tool_task_info.init({
				parentObj : this_,
				fjzch : this_.param.fjzch,
				msn : this_.param.msn,
				idList : this_.param.idList,
				setHeight : true,
				dprtcode : this_.param.dprtcode
			});
		}else if(type == 2 || type == 4 || type == 12){
			$("#m_task_workpackage_info", $("#"+this.id)).show();
			material_tool_task_info_workpackage.init({
				parentObj : this_,
				fjzch : this_.param.fjzch,
				msn : this_.param.msn,
				type : type,
				idList : this_.param.idList,
				setHeight : true,
				dprtcode : this_.param.dprtcode
			});
		}else if(type == 3){
			$("#m_task_workorder_info", $("#"+this.id)).show();
			material_tool_task_info_workorder.init({
				parentObj : this_,
				fjzch : this_.param.fjzch,
				msn : this_.param.msn,
				idList : this_.param.idList,
				setHeight : true,
				dprtcode : this_.param.dprtcode
			});
		}
		//初始化航材工具
		material_tool_detail_list.init({
			idList : this_.param.lyidList,
			type : type,
			fjzch : this_.param.fjzch,
			dprtcode : this_.param.dprtcode
		});
	}
}
