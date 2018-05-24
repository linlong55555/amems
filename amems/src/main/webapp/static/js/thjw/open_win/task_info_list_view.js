/**
 * 任务信息
 */
var task_info_list_view = {
	id:'task_info_list_view',
	tableDivId : 'task_info_list_view_top_div',
	tableId : 'task_info_list_view_table',
	tbodyId : 'task_info_list_view_tbody',
	data:[],
	param: {
		type : 1,
		bjh : '',
		idList : [],
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.load();
	},
	//加载数据
	load : 	function(){
		var this_ = this;
		var obj ={};
		$.extend(obj, this.getParams());
		
		if(this_.param.bjh == '' || this_.param.idList.length == 0){
			this_.setNoData();
			return;
		}
		var url = basePath+"/project2/materialtool/queryTaskInfoList";
		if(this_.param.type == 1){
			url = basePath+"/project2/materialtool/queryTaskInfoList";
		}

		if(this_.param.type == 2 || this_.param.type == 12){
			url = basePath+"/project2/materialtool/queryTaskInfoList4Package";
		}
		if(this_.param.type == 3){
			url = basePath+"/project2/materialtool/queryTaskInfoList4WorkOrder";
		}
		if(this_.param.type == 4){
			url = basePath+"/project2/materialtool/queryTaskInfoList4WP145";
		}
		
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				if(data != null && data.length >0){
					this_.appendContentHtml(data);
				} else {
					this_.setNoData();
				}
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		var searchParam = {};
		var paramsMap = {};
		searchParam.jh = this.param.bjh;
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.idList = this.param.idList;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			
			var paramsMap = row.paramsMap?row.paramsMap:{};
			var type = '';
			var lylx = paramsMap.lylx;
			if(paramsMap.lylx == 1 && row.ywlx == 1){
				type = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.lyzlx);
			}else{
				type = DicAndEnumUtil.getEnumName('workorderTypeEnum',paramsMap.lylx);
			}
			if(row.ywlx == 2){
				lylx = 3;
			}
			if(paramsMap.lylx == 6){
				type = "生产指令";
			}
			
			htmlContent += "<tr>";
			htmlContent += "<td title='"+type+"' style='text-align:center;vertical-align:middle;'>"+formatUndefine(type)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.ywbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(row.ywid)+"',"+lylx+")>"+StringUtil.escapeStr(paramsMap.ywbh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gkbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkCard('"+StringUtil.escapeStr(paramsMap.gkid)+"')>"+StringUtil.escapeStr(paramsMap.gkbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.qcdh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.qcdh)+"</td>";
			htmlContent += "<td title='"+row.sl+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.sl)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bxx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bxx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			
			htmlContent += "</tr>";
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"7\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 查看工卡
	 */
	viewWorkCard : function(id){
		window.open(basePath+"/project2/workcard/view/" + id );
	},
	/**
	 * 查看任务
	 */
	viewTask : function(id, type){
		if(type == 1){
			window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
		}
		if(type == 2){
			window.open(basePath + "/project2/eo/view?id=" + id);
		}
		if(type == 3 || type == 4 || type == 5){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
		if(type == 6){
			window.open(basePath + "/project2/production/view?id=" + id);
		}
	},
	close : function(){
		$("#"+this.id).modal("hide");
	}
};