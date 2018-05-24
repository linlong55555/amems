
var nrc_work_order_main = {
	id:'nrc_work_order_main',
	tableDivId : 'nrc_work_order_main_top_div',
	tableId : 'nrc_work_order_main_table',
	tbodyId : 'nrc_work_order_main_tbody',
	selectRowType : 1,
	mainData:[],
	pagination : {},
	param: {
		parentObj : {},
		fjzch : '',
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
	},
	load : function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.param.parentObj.hideBottom();
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
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		if(this_.param.fjzch == null || this_.param.fjzch == ''){
			this_.setNoData();
			return;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/workorder/queryNRCMapList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var count = 0;
				if(data != null && data.length > 0){
					count = data.length;
					this_.mainData = data;
					this_.appendContentHtml(data);
					// 标记关键字
					signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[2,3,8,13],"#"+this_.tbodyId+" tr td");
				} else {
					this_.setNoData();
				}
				$("#n_total_count", $("#"+this_.id)).html(count);
//				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},
	/**
	 * 将搜索条件封装 
	 */
	getParams : function(){
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
		paramsMap.gdlxList = [4,5];
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			
			var paramsMap = row.paramsMap?row.paramsMap:{};
			htmlContent += "<tr id='"+row.id+"' index="+index+" onclick='"+this_.id+".checkRow(this,1)'>";
			if(paramsMap.checkedId == null && row.jksjid == null && row.zt == 2 && row.gbid == null){
				htmlContent += "<td index="+index+" onclick='"+this_.id+".checkRow(this,2)' class='' style='text-align:center;vertical-align:middle;cursor:pointer;'><input index="+index+" type='checkbox' name='nrc_m_row' onclick='"+this_.id+".checkRow(this,2)' /></td>";
			}else{
				htmlContent += "<td index="+index+" onclick='"+this_.id+".checkRow(this,2)' class='' style='text-align:center;vertical-align:middle;cursor:pointer;'><input index="+index+" hide='hide' type='checkbox' name='nrc_m_row' style='display:none;' /></td>";
			}
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+StringUtil.escapeStr(row.id)+"')>"+StringUtil.escapeStr(row.gdbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
			htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', StringUtil.escapeStr(row.zt)))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', StringUtil.escapeStr(row.zt)))+"</td>";
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if((paramsMap.cardAttachCount != null && paramsMap.cardAttachCount > 0) 
				|| (paramsMap.cardFjid != null && paramsMap.cardFjid != "")
				|| (paramsMap.cardGznrfjid != null && paramsMap.cardGznrfjid != "")){
				htmlContent += '<i qtid="'+row.gkid+'" gkfjid="'+paramsMap.cardFjid+'" gznrfjid="'+paramsMap.cardGznrfjid+'" class="attachment-n-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			htmlContent += "<td title='"+indexOfTime(row.jhKsrq)+"' style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.jhKsrq)+"</td>";
			htmlContent += "<td title='"+indexOfTime(row.jhJsrq)+"' style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.jhJsrq)+"</td>";
			var zjh = StringUtil.escapeStr(row.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', row.gdlx))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', row.gdlx))+"</td>";
			htmlContent += "<td title='"+indexOfTime(row.whsj)+"' style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.whsj)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzlb)+"'>"+StringUtil.escapeStr(row.gzlb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bgr)+"'>"+StringUtil.escapeStr(row.bgr)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gkbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkCard('"+StringUtil.escapeStr(row.gkid)+"')>"+StringUtil.escapeStr(row.gkbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.jhgs)+"'>"+StringUtil.escapeStr(paramsMap.jhgs)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.yjZxdw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.yjZxdw)+"</td>";
			if((row.gdbh != null && row.paramsMap.woAttachCount != null && row.paramsMap.woAttachCount > 0) || (row.gznrfjid != null && row.gznrfjid != "")){
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += '<i qtid="'+row.id+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gbbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+StringUtil.escapeStr(row.gbid)+"')>"+StringUtil.escapeStr(paramsMap.gbbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initWebuiPopover();
		this_.initGkWebuiPopover();
		refreshPermission();
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"18\" class='text-center'>暂无数据 No data.</td></tr>");
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
	checkRow : function(this_,type){
		if(type == 1){
			this.showHiddenContent(this_);
		}else{
			this.selectRowType = 2;
			this.checkSingleRow(this_);
		}
	},
	checkSingleRow:function(this_){
		var index = $(this_).attr("index");
		SelectUtil.clickRow(index, this.tbodyId, 'nrc_m_row');
	},
	showHiddenContent:function(this_){
		if(this.selectRowType == 1){
			var id = $(this_).attr("id");
			this.param.parentObj.loadTool(id, 3);
			this.param.parentObj.selectTR = this_;
			$(this_).addClass('bg_tr').siblings().removeClass('bg_tr');
			var isBottomShown = false;
			if($(".displayTabContent").is(":visible")){
				isBottomShown = true;
			}
			$(".displayTabContent").css("display","block");
			App.resizeHeight();
			if(!isBottomShown){
			    $("#"+this.tableDivId, $("#"+this.id)).animate({scrollTop:this_.offsetTop-43},"slow");
//				TableUtil.makeTargetRowVisible($(this_), $("#"+this.tableDivId));
			}
		}
		this.selectRowType = 1;
	},
	/**
	 * 查看维修项目
	 */
	viewWxxm : function(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	},
	/**
	 * 查看装机清单
	 */
	viewZjqd : function(id){
		window.open(basePath + "/aircraftinfo/installationlist/view?id=" + id);
	},
	/**
	 * 查看工卡
	 */
	viewWorkCard : function(id){
		window.open(basePath+"/project2/workcard/view/" + id );
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
	 * 选中
	 */
	checked : function(){
		var this_ = this;
		var param = {};
		var monitoringWorkpackageList = [];
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var hide = $(this).attr("hide");
			if(hide == null || hide == ''){
				var info = {};
				var index = $(this).attr("index");
				var obj = this_.mainData[index];
				info.fjzch = this_.param.fjzch;
				info.jksjgdid = obj.id;
				info.dprtcode = this_.param.dprtcode;
				info.lx = 2;
				info.xsbs = 1;
				monitoringWorkpackageList.push(info);
			}
		});
		if(monitoringWorkpackageList.length > 0){
			param.fjzch = this_.param.fjzch;
			param.dprtcode = this_.param.dprtcode;
			param.monitoringWorkpackageList = monitoringWorkpackageList;
			var url = basePath+"/produce/maintenance/monitoring/saveChecked";
			var message = "选中成功!";
			this_.performAction(url, param, message);
		}else{
			AlertUtil.showMessage("请选择数据!");
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
				this_.param.parentObj.setCheckCount(data);
				finishWait();
				AlertUtil.showMessage(message);
				this_.search();
			}
		});
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
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
		window.open(basePath+"/produce/workorder/workorderMonitoring.xls?paramjson="+JSON.stringify(param));
	}
}	