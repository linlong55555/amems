/**
 * 航材工具任务信息
 */
material_tool_task_info = {
	id:'material_tool_task_info',
	tableDivId : 'material_tool_task_info_top_div',
	tableId : 'material_tool_task_info_table',
	tbodyId : 'material_tool_task_info_list',
	mainData : [],
	param: {
		parentObj : {},
		fjzch : '',
		msn : '',
		idList : [],
		setHeight : false,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.setHeight){
			this.formatStyle();
		}
		this.load();
	},
	/**
	 * 设置固定高度
	 */
	formatStyle : function(){
		$("#"+this.tableDivId, $("#"+this.id)).css({"max-height":"250px", "margin-bottom":"8px"});
	},
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.mainData = [];
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		var searchParam = {};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		if(this_.param.fjzch == null || this_.param.dprtcode == null || this_.param.idList.length == 0){
			this_.setNoData();
			return;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/maintenance/monitoring/queryCheckedList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data != null && data.length > 0){
					this_.mainData = data;
					this_.appendContentHtml(data);
				} else {
					this_.setNoData();
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.idList = this.param.idList;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 拼接列表
	 */
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			var bb = '';
			if(paramsMap.bb != null){
				bb = StringUtil.escapeStr("R" + paramsMap.bb);
			}
			var hdwz = '';
			if(paramsMap.isHdwz != null){
				hdwz = (paramsMap.isHdwz==0?"否":"是");
			}
			
			var lylx = DicAndEnumUtil.getEnumName('workorderTypeEnum', paramsMap.jksjlx);
			if(paramsMap.jksjlx == 1){
				lylx = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.lyzlx);
			}
			if(paramsMap.jksjlx == 6){
				lylx = "生产指令";
			}
			
			htmlContent += "<tr>";
			htmlContent += "<td title='"+lylx+"' style='text-align:center;vertical-align:middle;'>"+lylx+"</td>";
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.rwh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(paramsMap.lyid)+"',"+paramsMap.jksjlx+")>"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+bb+"'>"+bb+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.rwms)+"'>"+StringUtil.escapeStr(paramsMap.rwms)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.gzlx)+"'>"+StringUtil.escapeStr(paramsMap.gzlx)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(this_.param.fjzch)+"'>"+StringUtil.escapeStr(this_.param.fjzch)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(this_.param.msn)+"'>"+StringUtil.escapeStr(this_.param.msn)+"</td>";
			htmlContent += this_.formatMonitorData(row);
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
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"9\" class='text-center'>请重新打开航材工具需求清单 Please Reset Open.</td></tr>");
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(row){
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var data = paramsMap.jhsjsj;
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var xcjhz = ''
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			xcjhz += (tdArr[1]?this_.convertToHour(tdArr[0], tdArr[1]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
		});
		str += "<td title='"+xcjhz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+xcjhz+"</td>";
		return str;
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
	 * 页面布局变化时触发
	 */
	customResizeHeight : function(){
		new Sticky({tableId:this.tableId});
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
	}
};

function customResizeHeight(bodyHeight, tableHeight){
	material_tool_task_info.customResizeHeight();
}