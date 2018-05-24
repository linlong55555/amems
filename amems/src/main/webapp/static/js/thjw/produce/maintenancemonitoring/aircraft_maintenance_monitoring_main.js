
var aircraft_maintenance_monitoring_main = {
	id:'aircraft_maintenance_monitoring_main',
	tableDivId : 'aircraft_maintenance_monitoring_main_top_div',
	tableId : 'aircraft_maintenance_monitoring_main_table',
	tbodyId : 'aircraft_maintenance_monitoring_main_tbody',
	paginationId:'aircraft_maintenance_monitoring_main_Pagination',
	selectRowType : 1,
	operationId : '',
	pagination : {},
	planUsageList : [],
	thresholdAirList : [],
	currentDate : '',
	mainData:[],
	param: {
		parentObj : {},
		fjzch : '',
		warning : '',
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
		this.load(1,"auto","desc");
	},
	//加载数据
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
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		if(this_.operationId != ""){
			searchParam.id = this_.operationId;
			this_.operationId = "";
		}
		if(this_.param.fjzch == null || this_.param.fjzch == ''){
			this_.setNoData();
//			new Sticky({tableId:this_.tableId});
			return;
		}
		var atime = new Date();
		var btime = new Date();
		//加载数据前获取当前数据库时间
		TimeUtil.getCurrentDate(function (data){
			this_.currentDate = data;
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/produce/maintenance/monitoring/queryAllPageMaintenanceList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					btime = new Date();
//					console.info("请求所需时间："+(btime.getTime() - atime.getTime()));
					this_.planUsageList = [];
					this_.thresholdAirList = [];
					finishWait();
					if(data.total >0){
						this_.mainData = data.rows;
						this_.planUsageList = data.planUsageList;
						this_.thresholdAirList = data.thresholdAirList;
						this_.appendContentHtml(data.rows);
						// 标记关键字
						signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[5,7,8,10,11,12],"#"+this_.tbodyId+" tr td");
					} else {
						this_.setNoData();
					}
					var ctime = new Date();
//					console.info("加载数据所需时间："+(ctime.getTime() - btime.getTime()));
					$("#m_total_count", $("#"+this_.id)).html(data.total);
//					new Sticky({tableId:this_.tableId});
					var dtime = new Date();
//					console.info("浮动所需时间："+(dtime.getTime() - ctime.getTime()));
		      }
		    }); 
			
		});
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		var dlid = $.trim($("#dlbh_search", $("#"+this.id)).val());
		var wxxmlx = $.trim($("#xmlx_search", $("#"+this.id)).val());
		if(wxxmlx != null && wxxmlx.length > 0){
			var wxxmlxArr = wxxmlx.split(",");
			var wxxmlxList = [];
			for(var i = 0 ; i < wxxmlxArr.length ; i++){
				if('multiselect-all' != wxxmlxArr[i]){
					wxxmlxList.push(wxxmlxArr[i]);
				}
			}
			if(wxxmlxList.length > 0){
				paramsMap.wxxmlxList = wxxmlxList;
			}
		}
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.keyword = keyword;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		if(this.param.surplus && JSON.stringify(this.param.surplus) != "{}"){
			paramsMap.surplus = this.param.surplus;
		}
		if(this.param.plan && JSON.stringify(this.param.plan) != "{}"){
			paramsMap.plan = this.param.plan;
		}
		if(this.param.warning == 'checked'){
			paramsMap.warning = this.param.warning;
		}
		paramsMap.zjh = this.param.zjh;
		paramsMap.dlid = dlid;
		paramsMap.lylx = 1;
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
			
			htmlContent += "<tr id='"+paramsMap.id+"' jksjid='"+row.id+"' wxxmlx="+paramsMap.wxxmlx+" index="+index+" onclick='"+this_.id+".checkRow(this,1)'>";
			
			if(paramsMap.checkedId == null && paramsMap.gdgbstr == null){
				htmlContent += "<td index="+index+" onclick='"+this_.id+".checkRow(this,2)' class='' style='text-align:center;vertical-align:middle;cursor:pointer;'><input index="+index+" type='checkbox' name='air_m_row' onclick='"+this_.id+".checkRow(this,2)' /></td>";
			}else{
				htmlContent += "<td index="+index+" onclick='"+this_.id+".checkRow(this,2)' class='' style='text-align:center;vertical-align:middle;cursor:pointer;'><input index="+index+" hide='hide' type='checkbox' name='air_m_row' style='display:none;' /></td>";
			}
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index + 1)+"</td>";
			
			var yj = this_.getWarningColor(row);
			htmlContent += yj; 
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.wxxmlx)+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.rwh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWxxm('"+paramsMap.id+"')>"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr("R"+paramsMap.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+paramsMap.bb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.ckh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.rwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.rwms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.bz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewZjqd('"+StringUtil.escapeStr(row.zjqdid)+"')>"+StringUtil.escapeStr(row.bjh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.gzlx)+"</td>";
			htmlContent += this_.formatLastData(paramsMap.scsj);
			htmlContent += this_.formatMonitorData(row);
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xcjhrq)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xcjhrq)+"</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.hdwz==1?"是":"否")+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gkbh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkCard('"+StringUtil.escapeStr(paramsMap.gkid)+"')>"+StringUtil.escapeStr(paramsMap.gkbh)+"</a>";
			htmlContent += "</td>";
			
			
//			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gkfj)+"' style='text-align:left;vertical-align:middle;'>";
//			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+StringUtil.escapeStr(paramsMap.gkfjid)+"')>"+StringUtil.escapeStr(paramsMap.gkfj)+"</a>";
//			htmlContent += "</td>";
			
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if((paramsMap.attachCount != null && paramsMap.attachCount > 0) 
				|| (paramsMap.gkfjid != null && paramsMap.gkfjid != "")
				|| (paramsMap.gznrfjid != null && paramsMap.gznrfjid != "")){
				htmlContent += '<i qtid="'+paramsMap.gkid+'" gkfjid="'+paramsMap.gkfjid+'" gznrfjid="'+paramsMap.gznrfjid+'" class="attachment-m-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			
//			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.gkfj)+"' style='text-align:left;vertical-align:middle;'>";
//			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+StringUtil.escapeStr(paramsMap.gkfjid)+"')>"+StringUtil.escapeStr(paramsMap.gkfj)+"</a>";
//			htmlContent += "</td>";
			
			htmlContent += this_.formatWorkOrderPackage(paramsMap.gdgbstr);
			
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent +="<td title='"+StringUtil.escapeStr((paramsMap.dlbh||"")+"  "+ (paramsMap.dlywms||"")+"  "+ (paramsMap.dlzwms||""))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr((paramsMap.dlbh||"")+"  "+ (paramsMap.dlywms||"")+"  "+ (paramsMap.dlzwms||""))+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initWebuiPopover();
		refreshPermission();
	},
	initWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-m-gk-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#"+this_.tableDivId).scroll(function(){
			$('.attachment-m-gk-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"30\" class='text-center'>暂无数据 No data.</td></tr>");
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
		SelectUtil.clickRow(index, this.tbodyId, 'air_m_row');
	},
	showHiddenContent:function(this_){
		
		if(this.selectRowType == 1){
			var id = $(this_).attr("id");
			var jksjid = $(this_).attr("jksjid");
			var wxxmlx = $(this_).attr("wxxmlx");
			this.param.parentObj.loadTool(id, 1);
			this.param.parentObj.loadWxqd(id, wxxmlx);
			this.param.parentObj.loadZxls(jksjid);
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
	},
	/**
	 * 格式化工单工包
	 */
	formatWorkOrderPackage : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var gd = '';
		var gb = '';
		var gdTitle = '';
		var gbTitle = '';
		var gbStr = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			gd += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+tdArr[0]+"')>"+StringUtil.escapeStr(tdArr[1])+"</a></br>";
			gdTitle += StringUtil.escapeStr(tdArr[1])+"\n";
			if(gbStr.indexOf(tdArr[2]) == -1){
				gb += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+tdArr[2]+"')>"+StringUtil.escapeStr(tdArr[3])+"</a></br>";
				gbTitle += StringUtil.escapeStr(tdArr[3])+"\n";
			}
			gbStr += tdArr[2];
		});
		
		str += "<td title='"+gbTitle+"' style='text-align:left;vertical-align:middle;'>"+gb+"</td>";
		str += "<td title='"+gdTitle+"' style='text-align:left;vertical-align:middle;'>"+gd+"</td>";
		return str;
	},
	/**
	 * 格式化标题
	 */
	formatMonitorTitle : function(row){
		var this_ = this;
		var tdArr = row.split("#_#");
		var jklbh = tdArr[0];
		var syz = tdArr[4];
		var lx = tdArr[13];
		var jzz = tdArr[14];
		var fz = tdArr[15];
		var jzzdw = tdArr[16];
		var dwscz = tdArr[17];
		var jkname = StringUtil.escapeStr(MonitorUnitUtil.getMonitorName(jklbh));
		var str = '';
		if(lx == 2){
			if(Number(syz) <= Number(fz)){
				var dw = MonitorUnitUtil.getMonitorUnit(jklbh, '');
				var parentObj = this_.param.parentObj;
				if(jklbh == "1_10"){
					dw = "D";
				}
				str += jkname + "剩余值小于等于" + parentObj.convertToHour(jklbh, fz) + dw + "\n";
			}
		}else{
			if(dwscz != 10){
				var bzz = jzz * fz / 100;
				if(jzzdw == 12){
					bzz = bzz * 30;
				}
				bzz = Math.ceil(bzz);
				if(Number(syz) <= Number(bzz)){
					str += jkname + "剩余值小于等于" + fz + "%" + "\n";
				}
			}
		}
		return str;
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
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			scjh += this_.param.parentObj.formatJkz(tdArr[0], tdArr[1]);
			scsj += this_.param.parentObj.formatJkz(tdArr[0], tdArr[2]);
		});
		str += "<td title='"+this_.formatTitle(scjh)+"' style='text-align:center;'>"+scjh+"</td>";
		str += "<td title='"+this_.formatTitle(scsj)+"' style='text-align:center;'>"+scsj+"</td>";
		return str;
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(row){
		var mp = row.maintenanceProject?row.maintenanceProject:{};
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var data = paramsMap.jhsjsj;
		var isHdwz = mp.isHdwz;
		var this_ = this;
		var parentObj = this_.param.parentObj;
		var str = "";
		if(data == null || data == ""){
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
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			xcjhqsd += parentObj.formatJkz(tdArr[0], tdArr[1]);
			
			if(formatUndefine(tdArr[5]) != ''){
				zqz += (tdArr[5]?parentObj.convertToHour(tdArr[0], tdArr[5]):0)+MonitorUnitUtil.getMonitorUnit(tdArr[0], tdArr[6])+"</br>";
			}else{
				zqz += " "+"</br>";
			}
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
		str += "<td title='"+this_.formatTitle(xcjhqsd)+"' style='text-align:center;'>";
		str += "<a href='javascript:void(0);' onclick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "')>"+xcjhqsd+"</a>";
		str += "</td>";
		str += "<td title='"+this_.formatTitle(zqz)+"' style='text-align:center;'>"+zqz+"</td>";
		str += "<td title='"+this_.formatTitle(rc)+"' style='text-align:center;'>"+rc+"</td>";
		str += "<td title='"+this_.formatTitle(xcjhz)+"' style='text-align:center;'>"+xcjhz+"</td>";
		str += "<td title='"+this_.formatTitle(sjz)+"' style='text-align:center;'>"+sjz+"</td>";
		str += "<td title='"+this_.formatTitle(syz)+"' style='text-align:center;'>"+syz+"</td>";
//		str += "<td title='"+xcjhrq+"' style='text-align:center;vertical-align:middle;'>"+xcjhrq+"</td>";
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
	 * 打开维修项目初始化弹窗
	 */
	openWinEdit : function(id, dprtcode_){
		var this_ = this;
		this.selectRowType = 2;
		initialization_monitoring_edit_alert_Modal.show({
			id : id,
	 		modalHeadCN : '飞机维修项目监控',
			modalHeadENG : 'Aircraft Maintenance Monitoring',
			menuType:2,//菜单类型,1:维修计划初始化,2:飞机维修监控
			editType:1,//编辑类型,1:维修项目监控,2:EO监控
			dprtcode:dprtcode_,//机构代码
			callback : function(data) {//回调函数
				this_.operationId = id;
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
			var hide = $(this).attr("hide");
			if(hide == null || hide == ''){
				var info = {};
				var paramsMap = {};
				var index = $(this).attr("index");
				var obj = this_.mainData[index];
				var mp = obj.maintenanceProject?obj.maintenanceProject:{};
				info.fjzch = this_.param.fjzch;
				info.jksjgdid = obj.id;
				info.dprtcode = this_.param.dprtcode;
				info.lx = 1;
				info.xsbs = 1;
				if(mp.wxxmlx == 4){
					paramsMap.wxxmlx = mp.wxxmlx;
					info.paramsMap = paramsMap;
				}
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
	 * 加载维修项目大类
	 */
	buildSelect : function(jx, dprtcode){
		var this_ = this;
		var html = "<option value='' selected='selected'>显示全部All</option>";
		if(jx != null && jx != ''){
			AjaxUtil.ajax({
				url: basePath+"/project/maintenanceclass/querySelectByFjjx",
				type: "post",
				async : false,
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					jx : jx,
					dprtcode : dprtcode
				}),
				success:function(data){
					if(data.length >0){
						$(data).each(function(){
							var str = StringUtil.escapeStr((this.dlbh||"")+"  "+ (this.dlywms||"")+"  "+ (this.dlzwms||""));
							html += "<option value='"+this.id+"'>"+str+"</option>";
						});
					} else {
						html += "<option value=''>暂无数据</option>";
					}
					$("#dlbh_search", $("#"+this_.id)).html(html);
			    }
			}); 
		}else{
			$("#dlbh_search", $("#"+this_.id)).html(html);
		}
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		var this_ = this;
		//生成多选来源分类
		$("#xmlx_search", $("#"+this.id)).multiselect({
			buttonClass: 'btn btn-default',
			buttonWidth:'100%' ,
			buttonHeight: '34px',
			buttonContainer: '<div class="btn-group base-data-syjx" style="min-width:100%;" />',
			numberDisplayed:100,
			includeSelectAllOption: true,
			nonSelectedText:'显示全部 All',
		    allSelectedText:'显示全部 All',
			selectAllText: '选择全部 Select All',
			onChange : function(element, checked) {
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
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		if(this.param.fjzch == null || this.param.fjzch == ''){
			AlertUtil.showMessage("请选择飞机注册号!");
			return;
		}
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		window.open(basePath+"/produce/maintenance/monitoring/maintenanceMonitoring.xls?paramjson="+JSON.stringify(param));
	}
}	