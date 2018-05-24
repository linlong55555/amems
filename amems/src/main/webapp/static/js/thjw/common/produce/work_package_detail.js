work_package_detail = {
		id:"work_package_detail",
		tableDivId : 'workorder_detail_top_div',
		tableId : 'workorder_detail_table',
		tbodyId : 'workorder_detail_list',
		mainData:[],
		param: {
			gbid : "",
			obj : {},//工包对象
			parent_win_id : '',
			scroll_body : ''
		},
		init:function(param){
			if(param){
				$.extend(this.param, param);
			}
			this.initInfo();
		},
		initInfo : function(){
			$("#keyword_search", $("#"+this.id)).val("");
			$('input:checkbox[name=gdzlx]', $("#"+this.id)).attr("checked", "true");
			$('input:checkbox[name=gdlx]', $("#"+this.id)).attr("checked", "true");
			$('#keyword_search', $("#"+this.id)).removeAttr("disabled");
			this.load();
		},
		//加载数据
		load : function(){
			var this_ = this;
			this_.mainData = [];
			if(!this_.isChecked()){
				this_.setNoData();
				return;
			}
			var searchParam ={};
			$.extend(searchParam, this_.getParams());
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/produce/workorder/queryPackageDetailMapList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					finishWait();
					if(data != null && data.length >0){
						this_.mainData = data;
						this_.appendContentHtml(data);
						// 标记关键字
						signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[4,7],"#"+this_.tbodyId+" tr td");
					} else {
						this_.setNoData();
					}
		      }
		    }); 
		},	
		getParams : function(){//将搜索条件封装 
			var searchParam = {};
			var paramsMap = {};
			var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
			searchParam.dprtcode = this.param.dprtcode;
			searchParam.gbid = this.param.gbid;
			paramsMap.keyword = keyword;
			paramsMap.userId = userId;
			paramsMap.userType = userType;
			var gdzlxList = [];
			var gdlxList = [];
			var isGdArr = [];
			$('input:checkbox[name=gdzlx]:checked', $("#"+this.id)).each(function(){
				gdzlxList.push($(this).val());
			});
			$('input:checkbox[name=gdlx]:checked', $("#"+this.id)).each(function(){
				var isGd = $(this).attr("isGd");
				if(isGd){
					isGdArr.push(isGd);
				}
				gdlxList.push($(this).val());
			});
			if(isGdArr.length == 1){
				paramsMap.isGd = isGdArr[0];
			}
			if(gdzlxList.length > 0){
				paramsMap.gdzlxList = gdzlxList;
			}
			if(gdlxList.length > 0){
				paramsMap.gdlxList = gdlxList;
			}
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		appendContentHtml: function(list){
			var this_ = this;
			var htmlContent = '';
			var gb = this_.param.obj;
			$.each(list,function(index,row){
				
				var paramsMap = row.paramsMap?row.paramsMap:{};
				var zxdx = '';
				var zxdxTitle = '';
				if(paramsMap.bjh != null){
					zxdx += "PN："+paramsMap.bjh + "</br>";
					zxdxTitle += "PN："+paramsMap.bjh + "\n";
				}
				if(paramsMap.xlh != null){
					zxdx += "SN："+paramsMap.xlh;
					zxdxTitle += "SN："+paramsMap.xlh;
				}
				
				var lyfl = DicAndEnumUtil.getEnumName('workorderTypeEnum', row.gdlx);
				if(row.gdlx == 1){
					lyfl = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx); 
				}
				if(row.gdlx == 6 && paramsMap.isGd == 0){
					lyfl = "生产指令";
				}
				
				htmlContent += "<tr onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_p_row')>";
				if((row.gdbh == null || row.gbid == null) && row.djbgdid == null){
					htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_p_row') name='check_p_row' index="+index+" /></td>";
				}else{
					htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' hide='hide' onclick=SelectUtil.clickRow("+index+",'"+this_.tbodyId+"','check_p_row') name='check_p_row' index="+index+" style='display:none;' /></td>";
				}
				
				htmlContent += "<td title='"+lyfl+"' style='text-align:center;vertical-align:middle;'>"+lyfl+"</td>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'>";
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+StringUtil.escapeStr(paramsMap.gdid)+"')>"+StringUtil.escapeStr(row.gdbh)+"</a>";
				htmlContent += "</td>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.lyrwh)+"' style='text-align:left;vertical-align:middle;'>";
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(row.lyrwid)+"',"+row.gdlx+")>"+StringUtil.escapeStr(row.lyrwh)+"</a>";
				htmlContent += "</td>";
				
				htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+(row.zt==9?"已关闭":"未关闭")+"</td>";
				htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+(row.wgbs==1?"已反馈":"未反馈")+"</td>";
				
				htmlContent += "<td title='"+zxdxTitle+"' style='text-align:left;vertical-align:middle;' >"+zxdx+"</td>";
				
				htmlContent += this_.formatLastData(paramsMap.jhsjsj);
				
				htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.jhgs)+"'>"+StringUtil.escapeStr(paramsMap.jhgs)+"</td>";
				htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(paramsMap.sjgs)+"'>"+StringUtil.escapeStr(paramsMap.sjgs)+"</td>";
				
				if(row.gdlx == 1){
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
				}else{
					if((row.gdbh != null && row.paramsMap.woAttachCount != null && row.paramsMap.woAttachCount > 0) || (row.gznrfjid != null && row.gznrfjid != "")){
						htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
						htmlContent += '<i qtid="'+paramsMap.gdid+'" gznrfjid="'+row.gznrfjid+'" class="attachment-win-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						htmlContent += "</td>";
					}else{
						htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
					}
				}
				
				htmlContent += "</tr>";
			    
			});
			$("#"+this_.tbodyId, $("#"+this_.id)).empty();
			$("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
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
			$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
		},
		initGkWebuiPopover : function(){//初始化工卡
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-n-gk-view',"#"+this_.param.parent_win_id,function(obj){
				return this_.getGkHistoryAttachmentList(obj);
			});
			$("#"+this_.param.scroll_body).scroll(function(){
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
			WebuiPopoverUtil.initWebuiPopover('attachment-win-view',"#"+this_.param.parent_win_id,function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#"+this_.param.scroll_body).scroll(function(){
				$('.attachment-win-view').webuiPopover('hide');
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
		 * 移除选中
		 */
		removeChecked : function(){
			var this_ = this;
			var param = {};
			var monitoringWorkpackageList = this_.getCheckedData();
			if(monitoringWorkpackageList.length > 0){
				param.fjzch = this_.param.fjzch;
				param.dprtcode = this_.param.dprtcode;
				param.id = this.param.gbid;
				param.monitoringWorkpackageList = monitoringWorkpackageList;
				var url = basePath+"/produce/maintenance/monitoring/deleteWorkOrder4WorkPackage";
				var message = "移除成功!";
				this_.performAction(url, param, message);
			}else{
				AlertUtil.showMessage("请选择一行!");
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
//					this_.param.parentObj.setCheckCount(data);
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
			var monitoringWorkpackageList = [];
			$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
				var info = {};
				var index = $(this).attr("index");
				var obj = this_.mainData[index];
				var paramsMap = obj.paramsMap?obj.paramsMap:{};
				var hide = $(this).attr("hide");
				if(hide == null || hide == ''){
					info.fjzch = obj.fjzch;
					info.jksjgdid = paramsMap.gdid;
					if(obj.gdlx == 1 && paramsMap.gdzlx == 4){
						info.paramsMap = paramsMap;
					}
					monitoringWorkpackageList.push(info);
				}
			});
			return monitoringWorkpackageList;
		},
		/**
		 * 判断是否选中
		 */
		isChecked : function(){
			var lx = [];
			$('input:checkbox[name=gdzlx]:checked', $("#"+this.id)).each(function(){
				lx.push($(this).val());
			});
			$('input:checkbox[name=gdlx]:checked', $("#"+this.id)).each(function(){
				lx.push($(this).val());
			});
			return lx.length > 0;
		},
		/**
		 * 查看工单
		 */
		viewWorkOrder : function(id){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
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
				window.open(basePath+"/project2/production/view?id="+id);
			}
		},
		search : function(){
			this.load();
		},
		/**
		 * 导出
		 */
		exportExcel : function(){
			if(!this.isChecked()){
				AlertUtil.showMessage("请选择查询类型!");
				return;
			}
			var param = this.getParams();
			param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
			window.open(basePath+"/produce/workorder/workorderBurstification.xls?paramjson="+JSON.stringify(param));
		}
		
};