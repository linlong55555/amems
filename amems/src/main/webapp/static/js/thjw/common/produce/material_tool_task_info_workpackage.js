/**
 * 航材工具任务信息
 */
material_tool_task_info_workpackage = {
	id:'material_tool_task_info_workpackage',
	tableDivId : 'material_tool_task_info_workpackage_top_div',
	tableId : 'material_tool_task_info_workpackage_table',
	tbodyId : 'material_tool_task_info_workpackage_list',
	jksjType : ["", "维修项目", "EO", "NRC"],
	mainData : [],
	param: {
		parentObj : {},
		fjzch : '',
		msn : '',
		type : 2,//2表示135,4表示145
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
		if(this_.param.idList.length == 0){
			this_.setNoData();
			return;
		}
		var url =basePath+"/produce/workpackage/queryMToolDetailList";
		if(this_.param.type == 4){
			url = basePath+"/produce/workpackage145/queryMToolDetail145List";
		}
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.total >0){
					this_.mainData = data.rows;
					this_.appendContentHtml(data.rows);
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
		if(this.param.dprtcode != null && this.param.dprtcode != ''){
			searchParam.dprtcode = this.param.dprtcode;
		}
		if(this.param.fjzch != null && this.param.fjzch != ''){
			searchParam.fjzch = this.param.fjzch;
		}
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
			
			var fjzchMsn = StringUtil.escapeStr(formatUndefine(row.fjzch));
			
			if(row.xlh!=null){
				fjzchMsn += " "+StringUtil.escapeStr(row.xlh);
			}
			
			htmlContent += "<tr>"; 
			
			htmlContent += "<td class='text-left ' title='"+StringUtil.escapeStr(row.gbbh)+"' >";
			htmlContent += "<a href=\"javascript:"+this_.id+".viewWorkPackage('"+ row.id + "')\">"+ StringUtil.escapeStr(formatUndefine(row.gbbh))+ "</a>";
			htmlContent += "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.gbmc) + "'>"+ StringUtil.escapeStr(row.gbmc)+ "</td>";		
			htmlContent += "<td class='text-left'>"+ StringUtil.escapeStr(row.wxlx) + "</td>";
			htmlContent += "<td class='text-left ' title='"+fjzchMsn+ "'>" + fjzchMsn+ "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.zdrq==null?"":(row.zdrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.zdrq==null?"":(row.zdrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.xfdwDprtname) + "' >"+StringUtil.escapeStr(row.xfdwDprtname)  + "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gzyq)+"' >"+ StringUtil.escapeStr(row.gzyq)+ "</td>";
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
	 * 页面布局变化时触发
	 */
	customResizeHeight : function(){
		new Sticky({tableId:this.tableId});
	},
	/**
	 * 查看工包
	 */
	viewWorkPackage : function(id){
		window.open(basePath+"/produce/workpackage/view?id="+id);
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
		if(type == 3){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
	}
};

function customResizeHeight(bodyHeight, tableHeight){
	material_tool_task_info_workpackage.customResizeHeight();
}