/**
 * 航材工具任务信息
 */
material_tool_task_info_workorder = {
	id:'material_tool_task_info_workorder',
	tableDivId : 'material_tool_task_info_workorder_top_div',
	tableId : 'material_tool_task_info_workorder_table',
	tbodyId : 'material_tool_task_info_workorder_list',
	jksjType : ["", "维修项目", "EO", "NRC"],
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
		if(this_.param.idList.length == 0){
			this_.setNoData();
			return;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/workorder/queryAllPageWorkOrderList",
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
		searchParam.dprtcode = this.param.dprtcode;
		searchParam.fjzch = this.param.fjzch;
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
			var zjh = StringUtil.escapeStr(row.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			var zxdx = '';
			var zxdxTitle = '';
			var yjwwbs = '';
			var yjzxdw = '';
			
			if(row.fjzch != null){
				zxdx += "<p class='tag-set margin-bottom-0'>";
				zxdx += "机号/MSN："+row.fjzch;
				zxdxTitle += "机号/MSN："+row.fjzch;
				if(paramsMap.msn != null){
					zxdx += "/" + paramsMap.msn;
					zxdxTitle += "/" + paramsMap.msn;
				}
				zxdx += "</p>";
				zxdxTitle += "\n";
			}
			
			if(paramsMap.bjh != null){
				zxdx += "<p class='tag-set margin-bottom-0'>";
				zxdx += "PN："+paramsMap.bjh;
				zxdxTitle += "PN："+paramsMap.bjh + "\n";
				zxdx += "</p>";
			}
			if(paramsMap.xlh != null){
				zxdx += "<p class='tag-set margin-bottom-0'>";
				zxdx += "SN："+paramsMap.xlh;
				zxdxTitle += "SN："+paramsMap.xlh;
				zxdx += "</p>";
			}
			yjwwbs = '内部';
			if(paramsMap.yjWwbs == 1){
				yjwwbs = '外委';
			}
			if(paramsMap.yjZxdw != null && paramsMap.yjZxdw != ''){
				yjzxdw = yjwwbs + " " + StringUtil.escapeStr(paramsMap.yjZxdw);
			}
			
			htmlContent += "<tr>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.gbbh)+"' >";
			htmlContent += "<a href=\"javascript:"+this_.id+".viewWorkPackage('"+ row.gbid + "')\">"+ StringUtil.escapeStr(formatUndefine(paramsMap.gbbh))+ "</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+(StringUtil.escapeStr(row.fjzch) +" " + StringUtil.escapeStr(paramsMap.msn))+"'>"+(StringUtil.escapeStr(row.fjzch) +" " + StringUtil.escapeStr(paramsMap.msn))+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+StringUtil.escapeStr(row.id)+"')>"+StringUtil.escapeStr(row.gdbh)+"</a>";
			htmlContent += "</td>";
			/*任务信息*/
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<p title='"+StringUtil.escapeStr(row.lyrwh)+"' class='tag-set margin-bottom-0'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(row.lyrwid)+"',"+row.gdlx+")>"+StringUtil.escapeStr(row.lyrwh)+"</a>";
			htmlContent += "</p>";
			htmlContent += "<p title='"+StringUtil.escapeStr(paramsMap.ckh)+"' class='tag-set margin-bottom-0'>"+StringUtil.escapeStr(paramsMap.ckh)+"</p>";
			htmlContent +="</td>";
			
			/*ATA章节号*/
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			/*工单标题*/
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
			/*状态*/
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', StringUtil.escapeStr(row.zt)))+"</td>";
			/*执行对象*/
			htmlContent += "<td title='"+zxdxTitle+"' style='text-align:left;vertical-align:middle;' >"+zxdx+"</td>";
			/*完成时限*/
			htmlContent += this_.formatLastData(paramsMap.jhsjsj);
			/*来源工卡*/
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<p title='"+StringUtil.escapeStr(row.gkbh)+"' class='tag-set margin-bottom-0'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkCard('"+StringUtil.escapeStr(row.gkid)+"')>"+StringUtil.escapeStr(row.gkbh)+"</a>";
			htmlContent += "</p>";
			htmlContent +="</td>";
			
			/*工卡附件*/
			if((paramsMap.cardAttachCount != null && paramsMap.cardAttachCount > 0) 
				|| (paramsMap.cardFjid != null && paramsMap.cardFjid != "")
				|| (paramsMap.cardGznrfjid != null && paramsMap.cardGznrfjid != "")){
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += '<i qtid="'+row.gkid+'" gkfjid="'+paramsMap.cardFjid+'" gznrfjid="'+paramsMap.cardGznrfjid+'" class="attachment-n-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			/*计划/实际工时*/
			htmlContent += "<td style='text-align:right;vertical-align:middle;'>";
			htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(paramsMap.jhgs)+"</p>";
			htmlContent +="</td>";
			/*预计执行单位*/
			htmlContent += "<td title='"+yjzxdw+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += yjzxdw;
			htmlContent +="</td>";
			/*工单附件*/
			if(row.paramsMap.woAttachCount != null && row.paramsMap.woAttachCount > 0 || (row.gznrfjid != null && row.gznrfjid != "")){
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += '<i qtid="'+row.id+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			
			htmlContent += "</tr>"; 
			
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initWebuiPopover();
		this_.initGkWebuiPopover();
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"14\" class='text-center'>请重新打开航材工具需求清单 Please Reset Open.</td></tr>");
	},
	initGkWebuiPopover : function(){//初始化工卡
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-n-gk-view',"body",function(obj){
			return this_.getGkHistoryAttachmentList(obj);
		});
		$("#"+this_.tableDivId).scroll(function(){
			$('.attachment-n-gk-view').webuiPopover('hide');
		});
	},
	getGkHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
   	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
   	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
   	    ];
   		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	initWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view',"body",function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#"+this_.tableDivId).scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	/**
	 * 查看工包
	 */
	viewWorkPackage : function(id){
		window.open(basePath+"/produce/workpackage/view?id="+id);
	},
	/**
	 * 查看工单
	 */
	viewWorkOrder : function(id){
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
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
		if(type == 3){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
	},
	/**
	 * 格式化上次数据
	 */
	formatLastData : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var scjh = '';
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			scjh += this_.formatJkz(tdArr[0], tdArr[1]);
		});
		str += "<td title='"+scjh.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
		return str;
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
		}else{
			value = "";
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
		if(type == 3){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
	}
};

function customResizeHeight(bodyHeight, tableHeight){
	material_tool_task_info_workorder.customResizeHeight();
}