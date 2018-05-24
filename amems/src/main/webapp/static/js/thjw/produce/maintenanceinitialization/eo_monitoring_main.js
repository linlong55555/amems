/**
 * EO初始化
 */
var eo_monitoring_main = {
	id:'eo_monitoring_main',
	tableDivId : 'eo_monitoring_main_top_div',
	tableId : 'eo_monitoring_main_table',
	tbodyId : 'eo_monitoring_main_tbody',
	paginationId:'eo_monitoring_main_Pagination',
	operationId : '',
	pagination : {},
	planUsageList : [],
	currentDate : '',
	param: {
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	/**
	 * 加载数据
	 */
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
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		if(this_.operationId != ""){
			searchParam.id = this_.operationId;
			this_.operationId = "";
		}
		if(this_.param.fjzch == null || this_.param.fjzch == ''){
			this_.setNoData();
			new Sticky({tableId:this_.tableId});
			return;
		}
		
		//加载数据前获取当前数据库时间
		TimeUtil.getCurrentDate(function (data){
			this_.currentDate = data;
			
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/produce/maintenance/initialization/queryAllPageEOList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					finishWait();
					if(data.total >0){
						this_.mainData = data.rows;
						this_.planUsageList = data.planUsageList;
						this_.appendContentHtml(data.rows);
					   new Pagination({
							renderTo : this_.paginationId,
							data: data,
							sortColumn : sortColumn,
							orderType : orderType,
							controller: this_
						}); 
						// 标记关键字
						signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[2,4,5,6,7,20],"#"+this_.tbodyId+" tr td");
					} else {
						this_.setNoData();
					}
					new Sticky({tableId:this_.tableId});
		      }
		    }); 
			
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
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 拼接数据
	 */
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
			htmlContent += "<tr>";
		   
			htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'>";
			
			htmlContent += "<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:maintenance:initialization:main:02' onClick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "') title='修改 Edit'></i>";
			
			if(paramsMap.jhsjsj != null && paramsMap.jhsjsj != ''){
				htmlContent += "<i class='spacing icon-eye-open color-blue cursor-pointer' onClick="+this_.id+".viewHistory('"+ row.id + "','" +row.dprtcode + "') title='维修历史 Maintenance History'></i>";  
			}
			htmlContent += "</td>";  
		   
			htmlContent += "<td class='fixed-column' title='"+StringUtil.escapeStr(paramsMap.eobh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewEo('"+paramsMap.id+"')>"+StringUtil.escapeStr(paramsMap.eobh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr("R"+paramsMap.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+paramsMap.bb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.eozt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.eozt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			htmlContent += "<td title='"+fjzw+"' style='text-align:left;vertical-align:middle;'>"+fjzw+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+zxfs+"</td>";
			htmlContent += this_.formatLastData(paramsMap.scsj);
			htmlContent += this_.formatMonitorData(paramsMap.jhsjsj, paramsMap.hdwz, paramsMap.zxfs);
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(paramsMap.hdwz==0?"否":"是")+"</td>";
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			var zdr = StringUtil.escapeStr(paramsMap.zdrzwms) + " " + StringUtil.escapeStr(paramsMap.zdrywms);
			htmlContent += "<td title='"+zdr+"' style='text-align:left;vertical-align:middle;'>"+zdr+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.whsj)+"</td>";
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
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"23\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 查看EO信息
	 */
	viewEo : function(id){
		window.open(basePath + "/project2/eo/view?id=" + id);
	},
	/**
	 * 维修历史
	 */
	viewHistory : function(id, dprtcode){
		window.open(basePath+"/produce/maintenance/initialization/eo/history?id=" + id + "&fjzch=" + this.param.fjzch + "&dprtcode="+ dprtcode);
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
			menuType:1,//菜单类型,1:维修计划初始化,2:飞机维修监控
			editType:2,//编辑类型,1:维修项目监控,2:EO监控,3:生产指令监控
			dprtcode:dprtcode_,//机构代码
			callback : function(data) {//回调函数
				this_.operationId = id;
				this_.refreshPage();
			}
		});
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
			scjh += this_.formatJkz(tdArr[0], tdArr[1]);
			scsj += this_.formatJkz(tdArr[0], tdArr[2]);
		});
		str += "<td title='"+this_.formatTitle(scjh)+"' style='text-align:center;'>"+scjh+"</td>";
		str += "<td title='"+this_.formatTitle(scsj)+"' style='text-align:center;'>"+scsj+"</td>";
		return str;
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
		}else{
			value = " "+"</br>";
		}
		return value;
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(data, isHdwz, zxfs){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
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
		$.each(list,function(index, row){
			var tdArr = row.split("#_#");
			xcjhqsd += this_.formatJkz(tdArr[0], tdArr[1]);
			
			if(formatUndefine(tdArr[5]) != ''){
				zqz += (tdArr[5]?this_.convertToHour(tdArr[0], tdArr[5]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[6])+"</br>";
			}else{
				zqz += " "+"</br>";
			}
			
			rc += "-" + (tdArr[7]?this_.convertToHour(tdArr[0], tdArr[7]):0)+ "/+" + (tdArr[9]?this_.convertToHour(tdArr[0], tdArr[9]):0)+ MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[8])+"</br>";
			xcjhz += (tdArr[2]?this_.convertToHour(tdArr[0], tdArr[2]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
			sjz += (tdArr[3]?this_.convertToHour(tdArr[0], tdArr[3]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], "")+"</br>";
			if(tdArr[0] == "1_10"){
				syz += (tdArr[4]?this_.convertToHour(tdArr[0], tdArr[4]):0)+"D"+"</br>";
			}else{
				syz += (tdArr[4]?this_.convertToHour(tdArr[0], tdArr[4]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[6])+"</br>";
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
		str += "<td title='"+this_.formatTitle(xcjhqsd)+"' style='text-align:center;'>"+xcjhqsd+"</td>";
		if(zxfs != 2){
			str += "<td></td>";
			str += "<td></td>";
		}else{
			str += "<td title='"+this_.formatTitle(zqz)+"' style='text-align:center;'>"+zqz+"</td>";
			str += "<td title='"+this_.formatTitle(rc)+"' style='text-align:center;'>"+rc+"</td>";
			
		}
		str += "<td title='"+this_.formatTitle(xcjhz)+"' style='text-align:center;'>"+xcjhz+"</td>";
		str += "<td title='"+this_.formatTitle(sjz)+"' style='text-align:center;'>"+sjz+"</td>";
		str += "<td title='"+this_.formatTitle(syz)+"' style='text-align:center;'>"+syz+"</td>";
		str += "<td title='"+xcjhrq+"' style='text-align:center;vertical-align:middle;'>"+xcjhrq+"</td>";
		return str;
	},
	/**
	 * 格式化title
	 */
	formatTitle : function(title){
		var v = '';
		var temp = $.trim(title.replaceAll("</br>",""));
		if(temp != ''){
			temp = title.replaceAll("</br>",",");
			temp = temp.substring(0, temp.length - 1);
			v = temp.replaceAll(",","\n");
		}
		return v;
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
	 * 刷新页面
	 */
	refreshPage : function(){//刷新页面
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
	},
	/**
	 * 搜索
	 */
	search : function(){
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	}
}	