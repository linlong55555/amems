
var eo_status_main = {
	id:'eo_status_main',
	tableDivId : 'eo_status_main_top_div',
	tableId : 'eo_status_main_table',
	tbodyId : 'eo_status_main_tbody',
	paginationId:'eo_status_main_Pagination',
	selectTR:null,
	selectRowType : 1,
	pagination : {},
	planUsageList : [],
	currentDate : '',
	mainData:[],
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
		this.load(1,"auto","desc");
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
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		
		if(this_.param.fjzch == null || this_.param.fjzch == ''){
			this_.setNoData();
			return;
		}
		
		//加载数据前获取当前数据库时间
		TimeUtil.getCurrentDate(function (data){
			this_.currentDate = data;
			
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/produce/maintenance/monitoring/queryAllPageEOList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					this_.planUsageList = [];
					finishWait();
					if(data.total >0){
						this_.mainData = data.rows;
						this_.planUsageList = data.planUsageList;
						this_.appendContentHtml(data.rows);
//						new Pagination({
//							renderTo : this_.paginationId,
//							data: data,
//							sortColumn : sortColumn,
//							orderType : orderType,
//							controller: this_
//						}); 
						// 标记关键字
						//signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[3,5,7,8,9],"#"+this_.tbodyId+" tr td");
					} else {
						this_.setNoData();
					}
//					new Sticky({tableId:this_.tableId});
		      }
		    }); 
			
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
		if(this.param.surplus && JSON.stringify(this.param.surplus) != "{}"){
			paramsMap.surplus = this.param.surplus;
		}
		if(this.param.plan && this.param.plan.length != null){
			paramsMap.plan = this.param.plan;
		}
		paramsMap.zjh = this.param.zjh;
		paramsMap.lylx = 2;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			var fjzw = "";
			if(paramsMap.fjzw != null && paramsMap.fjzw !=''){
				var strs= new Array(); //定义一数组
				strs = paramsMap.fjzw.split(","); //字符分割
				strs.sort();
				fjzw = strs.join(",");
			}
			
			var zxfs = DicAndEnumUtil.getEnumName('executionEnum',paramsMap.zxfs);
			if(paramsMap.zxfs == 3 && row.eoxc != null){
				zxfs = "分段：第" +row.eoxc+ "次";
			}
			htmlContent += "<tr >";
			htmlContent += this_.getWarningColor(row);
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.eozt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.eozt)+"</td>";
			htmlContent += this_.formatMonitorData(row);
			htmlContent += this_.formatLastData(paramsMap.scsj);
			
			/*
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.eobh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewEo('"+paramsMap.id+"')>"+StringUtil.escapeStr(paramsMap.eobh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr("R"+paramsMap.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+paramsMap.bb)+"</td>";
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewZjqd('"+StringUtil.escapeStr(row.zjqdid)+"')>"+StringUtil.escapeStr(row.bjh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.gzlx)+"</td>";
			
			htmlContent += "<td title='"+fjzw+"' style='text-align:left;vertical-align:middle;'>"+fjzw+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+zxfs+"</td>";
			
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(paramsMap.hdwz==0?"否":"是")+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gbbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+StringUtil.escapeStr(paramsMap.gbid)+"')>"+StringUtil.escapeStr(paramsMap.gbbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gdbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+StringUtil.escapeStr(paramsMap.gdid)+"')>"+StringUtil.escapeStr(paramsMap.gdbh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";*/
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
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	checkRow : function(this_,type){
		var jkid = $(this_).attr("jkid");
		this.selectTR = this_;
		$(this_).addClass('bg_tr').siblings().removeClass('bg_tr');
		$("#status_main_rightSecondDiv").css("display","block");
		App.resizeHeight();
		aircraftstatus.loadperformHistory(jkid);
		
		
		
		/*if(type == 1){
			this.showHiddenContent(this_);
		}else{
			this.selectRowType = 2;
			this.checkSingleRow(this_);
		}*/
	},
	checkSingleRow:function(this_){
		var index = $(this_).attr("index");
		SelectUtil.clickRow(index, this.tbodyId, 'eo_m_row');
	},
	showHiddenContent:function(this_){
		if(this.selectRowType == 1){
			this.selectTR = this_;
			$(this_).addClass('bg_tr').siblings().removeClass('bg_tr');
			$(".displayTabContent").css("display","block");
			App.resizeHeight();
		}
		this.selectRowType = 1;
	},
	/**
	 * 查看EO
	 */
	viewEo : function(id){
		window.open(basePath + "/project2/eo/view?id=" + id);
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
	 * 获取预警颜色
	 */
	getWarningColor : function(yjbs){
		var this_ = this;
		var htmlappend = "<td class='fixed-column' style='text-align:center;vertical-align:middle;'></td>";
		var bgcolor = warningColor.level2;//黄色
		htmlappend = "<td class='fixed-column' style='text-align:center;vertical-align:middle;' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
		if(yjbs == 1){
			bgcolor = warningColor.level1;//红色
			htmlappend =  "<td class='fixed-column' style='text-align:center;vertical-align:middle;' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
		}
		return htmlappend;
	},
	/**
	 * 格式化上次数据
	 */
	formatLastData : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var scjh = '';
		var scsj = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			scjh += this_.param.parentObj.formatJkz(tdArr[0], tdArr[1]);
			scsj += this_.param.parentObj.formatJkz(tdArr[0], tdArr[2]);
		});
		str += "<td title='"+scjh.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
		str += "<td title='"+scsj.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
		return str;
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(row){
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var data = paramsMap.jhsjsj;
		var isHdwz = paramsMap.hdwz;
		var this_ = this;
		var parentObj = this_.param.parentObj;
		var str = "";
		if(data == null || data == ""){
			//str += "<td></td>";
			str += "<td></td>";
			str += "<td></td>";
			str += "<td></td>";
			str += "<td></td>";
			str += "<td></td>";
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var xcjhqsd = '';
		var zqz = '';
		var rc = '';
		var xcjhz = ''
		var sjz = '';
		var syz = '';
		var syts = 0;
		var xcjhrq = this_.currentDate;
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			xcjhqsd += parentObj.formatJkz(tdArr[0], tdArr[1]);
			zqz += (tdArr[5]?parentObj.convertToHour(tdArr[0], tdArr[5]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[6])+"</br>";
			rc += "-" + (tdArr[7]?parentObj.convertToHour(tdArr[0], tdArr[7]):0)+ "/+" + (tdArr[9]?parentObj.convertToHour(tdArr[0], tdArr[9]):0)+ MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[8])+"</br>";
			xcjhz += (tdArr[2]?parentObj.convertToHour(tdArr[0], tdArr[2]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
			sjz += (tdArr[3]?parentObj.convertToHour(tdArr[0], tdArr[3]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
			
			if(tdArr[0] == "1_10"){
				syz += (tdArr[4]?parentObj.convertToHour(tdArr[0], tdArr[4]):0)+"D"+"</br>";
			}else{
				syz += (tdArr[4]?parentObj.convertToHour(tdArr[0], tdArr[4]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[6])+"</br>";
			}
			
			var temp = this_.formatSyts(tdArr[0], tdArr[4]);
			if(index == 0){
				syts = temp;
			}
			if((isHdwz == 1 && Number(syts) < Number(temp)) || (isHdwz == 0 && Number(syts) > Number(temp))){
				syts = temp;
			}
		});
		//获取下次计划日期
		if(syts != null && syts != 0){
			xcjhrq = TimeUtil.dateOperator(this_.currentDate, syts, TimeUtil.Operation.ADD);
		}
		if(xcjhrq == 'NaN-NaN-NaN'){
			xcjhrq = "NaN-NaN-NaN";
		}
		zqz = zqz != '0</br>'?zqz:'';
		syz = syz != '0</br>'?syz:'';
		/*str += "<td title='"+xcjhqsd.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>";
		str += "<a href='javascript:void(0);' onclick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "')>"+xcjhqsd+"</a>";
		str += "</td>";*/
		if(paramsMap.zxfs != 2){
			str += "<td></td>";
			str += "<td></td>";
		}else{
			str += "<td title='"+zqz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+zqz+"</td>";
			str += "<td title='"+rc.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+rc+"</td>";
		}
		str += "<td title='"+xcjhz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+xcjhz+"</td>";
		str += "<td title='"+sjz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+sjz+"</td>";
		str += "<td title='"+syz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+syz+"</td>";
		str += "<td title='"+xcjhrq+"' style='text-align:center;vertical-align:middle;'>"+xcjhrq+"</td>";
		return str;
	},
	/**
	 * 格式化剩余天数
	 */
	formatSyts : function(jklbh, syts){
		var flag = false;
		if(MonitorUnitUtil.isTime(jklbh)){
			flag = true;
		}
		$.each(this.planUsageList,function(index, row){
			if(jklbh == row.jklbh){
				if(row.rsyl != null && row.rsyl > 0){
					syts = syts/row.rsyl;
				}else{
					if(flag){
						syts = syts/60;
					}
				}
			}
		});
		syts = Math.floor(syts);
		return syts;
	},
	/**
	 * 打开EO监控弹出框
	 */
	openWinEdit : function(id,dprtcode_){
		var this_ = this;
		initialization_monitoring_edit_alert_Modal.show({
			id : id,
	 		modalHeadCN : 'EO监控',
			modalHeadENG : 'EO Monitoring',
			menuType:2,//菜单类型,1:维修计划初始化,2:飞机维修监控
			editType:2,//编辑类型,1:维修项目监控,2:EO监控
			dprtcode:dprtcode_,//机构代码
			callback : function(data) {//回调函数
				this_.search();
			}
		});
	},
	/**
	 * 选中
	 */
	checked : function(){
		var this_ = this;
		var param = {};
		var monitoringWorkpackageList = [];
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var info = {};
			var hide = $(this).attr("hide");
			if(hide == null || hide == ''){
				var index = $(this).attr("index");
				var obj = this_.mainData[index];
				info.fjzch = this_.param.fjzch;
				info.jksjgdid = obj.id;
				info.dprtcode = this_.param.dprtcode;
				info.lx = 1;
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
		this.load(1,"auto","desc");
	},
	/**
	 * 获取预警颜色
	 */
	getWarningColor : function(row){
		var this_ = this;
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var yjbs = paramsMap.isWarning;
		var data = paramsMap.jhsjsj;
		var htmlappend = "<td class='fixed-column' style='text-align:center;vertical-align:middle;'></td>";
//		var bgcolor = warningColor.level2;//黄色
//		htmlappend = "<td class='fixed-column' style='text-align:center;vertical-align:middle;' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
		if(yjbs == 1){
			var title = '';
			if(data != null && data != ""){
				var list = data.split(",");
				$.each(list,function(index,row){
					title += this_.formatMonitorTitle(row);
				});
			}
			bgcolor = warningColor.level2;//黄色
			htmlappend =  "<td title='"+title+"' class='fixed-column' style='text-align:center;vertical-align:middle;' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
		}
		if(yjbs == 2){
			bgcolor = warningColor.level1;//红色
			htmlappend =  "<td title='超期' class='fixed-column' style='text-align:center;vertical-align:middle;' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
		}
		return htmlappend;
	}
}	