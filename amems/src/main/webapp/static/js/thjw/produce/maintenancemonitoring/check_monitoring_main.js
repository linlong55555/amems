
var check_monitoring_main = {
	id:'check_monitoring_main',
	tableDivId : 'check_monitoring_main_top_div',
	tableId : 'check_monitoring_main_table',
	tbodyId : 'check_monitoring_main_tbody',
	paginationId:'check_monitoring_main_Pagination',
	selectTR:null,
	selectRowType : 1,
	pagination : {},
	mainData:[],
	gdbhArr : [],//可新增的工单
	gdbhNotArr : [],//不可新增的工单
	isLoad : false,//是否加载
	param: {
		parentObj : {},
		fjzch : '',
		msn : '',
		surplus : {},//剩余对象
		plan : {},//计划对象
		zjh : '',//章节号
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load();
		if(!this.isLoad){
			this.initWebuiPopover();
			this.isLoad = true;
		}
		
	},
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('burstification-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		}, 180);
	},
	getHistoryAttachmentList : function(obj){//获取历史版本列表
		if(this.isChecked()){
			return burstification_view_Modal.getHistoryBbList(this, this.param.fjzch, this.param.dprtcode);
		}else{
			$('.burstification-view').webuiPopover('hide');
			AlertUtil.showMessage("请选择未组包的数据!");
		}
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
		if(this_.param.fjzch == null || this_.param.fjzch == ''){
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
				var count = 0;
				if(data != null && data.length > 0){
					this_.mainData = data;
					count = data.length;
					this_.appendContentHtml(data);
					// 标记关键字
					signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[3,5,6,7,8,13],"#"+this_.tbodyId+" tr td");
				} else {
					this_.param.parentObj.setCheckCount(0);
					this_.setNoData();
				}
				this_.param.parentObj.setCheckCount(count);
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.keyword = keyword;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		paramsMap.surplus = this.param.surplus;
		paramsMap.plan = this.param.plan;
		paramsMap.zjh = this.param.zjh;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
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
			
			htmlContent += "<tr onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_m_row')>";
			if(row.gbid == null && paramsMap.gdid == null){
				htmlContent += "<td class='' style='text-align:center;vertical-align:middle;'><input type='checkbox' onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_m_row') name='check_m_row' index="+index+" /></td>";
			}else{
				htmlContent += "<td class='' style='text-align:center;vertical-align:middle;'><input type='checkbox' hide='hide' onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_m_row') name='check_m_row' index="+index+" /></td>";
			}
			
			htmlContent += "<td title='"+lylx+"' style='text-align:center;vertical-align:middle;'>"+lylx+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.rwh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(paramsMap.lyid)+"',"+paramsMap.jksjlx+")>"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+bb+"'>"+bb+"</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.rwms)+"'>"+StringUtil.escapeStr(paramsMap.rwms)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.bjh)+"'>"+StringUtil.escapeStr(paramsMap.bjh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.xlh)+"'>"+StringUtil.escapeStr(paramsMap.xlh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.xh)+"'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.gzlx)+"'>"+StringUtil.escapeStr(paramsMap.gzlx)+"</td>";
			htmlContent += this_.formatMonitorData(row);
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+hdwz+"</td>";
			
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gbbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+StringUtil.escapeStr(row.gbid)+"')>"+StringUtil.escapeStr(paramsMap.gbbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			htmlContent += "</tr>"; 
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>");
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
	/**
	 * 查看装机清单
	 */
	viewZjqd : function(id){
		window.open(basePath + "/aircraftinfo/installationlist/view?id=" + id);
	},
	/**
	 * 查看工包
	 */
	viewWorkpackage : function(id){
		window.open(basePath+"/produce/workpackage/view?id="+id);
	},
	/**
	 * 查看工单
	 */
	viewWorkOrder : function(id){
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	},
	/**
	 * 航材工具需求清单
	 */
	openMaterialToolDetail : function(){
		var this_ = this;
		if($("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").length > 0){
			this_.setParam();
			window.open(basePath+"/produce/maintenance/monitoring/material/tool/detail?type=1");
		}else{
			AlertUtil.showMessage("请选择数据!");
		}
	},
	/**
	 * 设置参数
	 */
	setParam : function(){
		var this_ = this;
		var idList = [];
		var lyidList = [];
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var index = $(this).attr("index");
			var obj = this_.mainData[index];
			var paramsMap = obj.paramsMap?obj.paramsMap:{};
			idList.push(obj.id);
			lyidList.push(paramsMap.lyid);
		});
		var param = {};
		param.fjzch = this_.param.fjzch;
		param.msn = this_.param.msn;
		param.dprtcode = this_.param.dprtcode;
		param.idList = idList;
		param.lyidList = lyidList;
		localStorage.setItem("1",JSON.stringify(param));
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(row){
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var data = paramsMap.jhsjsj;
		var this_ = this;
		var parentObj = this_.param.parentObj;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var rc = '';
		var xcjhz = ''
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			xcjhz += (tdArr[1]?parentObj.convertToHour(tdArr[0], tdArr[1]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
			rc += "-" + (tdArr[2]?parentObj.convertToHour(tdArr[0], tdArr[2]):0)+ "/+" + (tdArr[4]?parentObj.convertToHour(tdArr[0], tdArr[4]):0)+ MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[3])+"</br>";
		});
		str += "<td title='"+xcjhz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+xcjhz+"</td>";
		str += "<td title='"+rc.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+rc+"</td>";
		return str;
	},
	/**
	 * 新建工包
	 */
	addWorkPackage : function(data, modal){
		console.info(1);
		var this_ = this;
		var url = basePath + "/produce/maintenance/monitoring/addWorkpackage";
		data.monitoringWorkpackageList = this_.getCheckedData();
		data.fjzch = this_.param.fjzch;
		data.dprtcode = this_.param.dprtcode;
		var message = '1,将创建新工包：'+data.gbbh+'</br>';
		if(this_.gdbhArr.length > 0){
			message += '2,此工包下加入如下工单：';
			$.each(this_.gdbhArr,function(index,gdbh){
				message += "["+gdbh+"]";
			});
			message += "</br>是否继续新增此工包？";
		}else{
			message += '2,此工包下无工单,是否继续新增此工包？';
		}
		AlertUtil.showConfirmMessage(message, {callback: function(){
			AjaxUtil.ajax({
				url : url,
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					this_.param.parentObj.setBurstificationCount(data);
					$("#"+modal).modal("hide");
					AlertUtil.showMessage("保存成功!");
					this_.search();
				}
			});
			 
		}});
	},
	/**
	 * 添加到已有工包
	 */
	add2WorkPackage : function(data, modal){
		var this_ = this;
		var url = basePath + "/produce/maintenance/monitoring/add2WorkPackage";
		data.monitoringWorkpackageList = this_.getCheckedData();
		data.fjzch = this_.param.fjzch;
		data.dprtcode = this_.param.dprtcode;
		var message = '';
		if(this_.gdbhArr.length > 0){
			message = '将在现有工包['+data.gbbh+']下添加如下工单：';
			$.each(this_.gdbhArr,function(index,gdbh){
				message += "["+gdbh+"]";
			});
			message += ",是否继续？";
		}else{
			message += '现有工包['+data.gbbh+']此次未添加工单,是否继续？';
		}
		AlertUtil.showConfirmMessage(message, {callback: function(){
			
			if(this_.gdbhArr.length > 0){
				AjaxUtil.ajax({
					url : url,
					type : "post",
					data : JSON.stringify(data),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					async : false,
					success : function(data) {
						this_.param.parentObj.setBurstificationCount(data);
						$("#"+modal).modal("hide");
						AlertUtil.showMessage("保存成功!");
						this_.search();
					}
				});
			}else{
				$("#"+modal).modal("hide");
			}
			 
		}});
	},
	/**
	 * 验证是否选中
	 */
	isChecked : function(){
		var this_ = this;
		var count = 0;
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var hide = $(this).attr("hide");
			if(hide == null || hide == ''){
				count++;
			}
		});
		return count != 0;
	},
	/**
	 * 移除选中
	 */
	removeChecked : function(){
		var this_ = this;
		var param = {};
		var monitoringWorkpackageList = this_.getCheckedData();
		if(monitoringWorkpackageList.length > 0){
			param.fjzch = this_.param.fjzch;
			param.dprtcode = this_.param.dprtcode;
			param.monitoringWorkpackageList = monitoringWorkpackageList;
			var url = basePath+"/produce/maintenance/monitoring/deleteChecked";
			var message = "移除成功!";
			this_.performAction(url, param, message);
		}else{
			AlertUtil.showMessage("请选择未组包的数据!");
		}
	},
	performAction : function(url, param, message){//执行编辑工卡
		var this_ = this;
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
//				this_.param.parentObj.setCheckCount(data);
				finishWait();
				AlertUtil.showMessage(message);
				this_.search();
			}
		});
	},
	/**
	 * 获取选中的数据
	 */
	getCheckedData : function(){
		var this_ = this;
		this_.gdbhArr = [];
		this_.gdbhNotArr = [];
		var monitoringWorkpackageList = [];
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var hide = $(this).attr("hide");
			var index = $(this).attr("index");
			var obj = this_.mainData[index];
			var paramsMap = obj.paramsMap?obj.paramsMap:{};
			if(hide == null || hide == ''){
				var info = {};
				info.id = obj.id;
				info.dprtcode = obj.dprtcode;
				info.fjzch = obj.fjzch;
				info.jksjgdid = obj.jksjgdid;
				this_.gdbhArr.push(paramsMap.rwh);
				monitoringWorkpackageList.push(info);
			}else{
				this_.gdbhNotArr.push(paramsMap.rwh);
			}
		});
		return monitoringWorkpackageList;
	},
	/**
	 * 排序
	 */
	orderBy : function(sortColumn, prefix){//字段排序
		var this_ = this;
		var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
		var orderStyle = $obj.attr("class");
		$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
		if(currentPage == ""){currentPage = 1;}
		if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}
		this_.load(currentPage, sortColumn, orderType);
	},
	/**
	 * 搜索
	 */
	search : function(){
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load();
	}
}	