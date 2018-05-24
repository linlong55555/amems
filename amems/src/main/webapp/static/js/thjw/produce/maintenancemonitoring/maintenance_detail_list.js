/**
 * 维修清单
 */
maintenance_detail_list = {
	id:'maintenance_detail_list',
	tableDivId : 'maintenance_detail_list_top_div',
	tableId : 'maintenance_detail_list_table',
	tbodyId : 'maintenance_detail_list_tbody',
	planUsageList : [],
	currentDate : '',
	param: {
		parentObj : {},
		id : '',
		type : 1,
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(){
		this.load();
	},
	//加载数据
	load : function(){
		var this_ = this;
		if(this_.param.dprtcode == null || this_.param.dprtcode == '' || this_.param.id == '' || this_.param.type != 4){
			this_.setNoData();
			return;
		}
		var searchParam ={};
		this_.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		
		//加载数据前获取当前数据库时间
		TimeUtil.getCurrentDate(function (data){
			this_.currentDate = data;
		
			var url = basePath+"/produce/maintenance/monitoring/queryPackageMaintenanceDetailList";
			startWait();
			AjaxUtil.ajax({
				url : url,
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					this_.planUsageList = [];
					finishWait();
					if(data.total >0){
						this_.planUsageList = data.planUsageList;
						this_.appendContentHtml(data.rows);
					} else {
						this_.setNoData();
					}
		      }
		    }); 
		
		});
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.wxxmid = this.param.id;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml :function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			
			var mp = row.maintenanceProject?row.maintenanceProject:{};
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			htmlContent += "<tr>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',mp.wxxmlx)+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(mp.rwh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWxxm('"+mp.id+"')>"+StringUtil.escapeStr(mp.rwh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr("R"+mp.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+mp.bb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(mp.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(mp.ckh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(mp.rwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(mp.rwms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewZjqd('"+StringUtil.escapeStr(row.zjqdid)+"')>"+StringUtil.escapeStr(row.bjh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
//			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(mp.gzlx)+"</td>";
			htmlContent += this_.formatLastData(paramsMap.scsj);
			htmlContent += this_.formatMonitorData(row);
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xcjhrq)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xcjhrq)+"</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.hdwz==0?"否":"是")+"</td>";
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
			
			var zjh = StringUtil.escapeStr(paramsMap.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent +="<td title='"+StringUtil.escapeStr((paramsMap.dlbh||"")+"  "+ (paramsMap.dlywms||"")+"  "+ (paramsMap.dlzwms||""))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr((paramsMap.dlbh||"")+"  "+ (paramsMap.dlywms||"")+"  "+ (paramsMap.dlzwms||""))+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append(htmlContent);
		this_.initWebuiPopover();
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
		str += "<td title='"+scjh.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
		str += "<td title='"+scsj.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
		return str;
	},
	/**
	 * 格式化监控数据
	 */
	formatMonitorData : function(row){
		var mp = row.maintenanceProject?row.maintenanceProject:{};
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var data = paramsMap.jhsjsj;
		var isHdwz = row.hdwz;
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
		str += "<td title='"+xcjhqsd.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>";
		str += "<a href='javascript:void(0);' onclick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "')>"+xcjhqsd+"</a>";
		str += "</td>";
		str += "<td title='"+zqz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+zqz+"</td>";
		str += "<td title='"+rc.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+rc+"</td>";
		str += "<td title='"+xcjhz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+xcjhz+"</td>";
		str += "<td title='"+sjz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+sjz+"</td>";
		str += "<td title='"+syz.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+syz+"</td>";
//		str += "<td title='"+xcjhrq+"' style='text-align:center;vertical-align:middle;'>"+xcjhrq+"</td>";
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
	 * 清空数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.tableDivId)).empty();
		$("#"+this.tbodyId, $("#"+this.tableDivId)).append("<tr><td colspan=\"22\" class='text-center'>暂无数据 No data.</td></tr>");
	}
};