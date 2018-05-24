
var aircraft_performHisotry_status = {
	id:'aircraft_performHisotry_status_main',
	tableDivId : 'aircraft_performHisotry_status_top_div',
	tableId : 'aircraft_performHisotry_status_table',
	tbodyId : 'aircraft_performHisotry_status_tbody',
	paginationId:'aircraft_performHisotry_status_Pagination',
	selectTR:null,
	selectRowType : 1,
	pagination : {},
	currentDate : '',
	param: {
		fjzch : '',
		dprtcode:userJgdm,//默认登录人当前机构代码
		jkid:''
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
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:10};
		searchParam.pagination = this_.pagination;
		//$.extend(searchParam, this_.getParams());
		searchParam.fjzch = this_.param.fjzch;
		searchParam.dprtcode = this_.param.dprtcode;
		
		/*if(this_.param.jkid == null || this_.param.jkid == ''){
			this_.setNoData();
			return;
		}*/
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/taskhistory/queryAll",
			//url: basePath+"/produce/workorder/queryTaskhistoryByfjzch",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			//async: false,
			data:JSON.stringify(searchParam),
			success:function(data){
				//this_.planUsageList = [];
				finishWait();
				if(data.rows.length >0){
					this_.appendContentHtml(data.rows);
					//分页
		 			var page_ = new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						extParams:{},
						goPage: function(a,b,c){
							this_.load(a,b,c);
						}
					});
				} else {
					this_.setNoData();
				}
				
//					new Sticky({tableId:this_.tableId});
	      }
	    }); 
			
	},	
	getParams : function(){//将搜索条件封装 
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = $("#dprtcode_search").val();
		searchParam.fjzch = this_.param.fjzch;
		paramsMap.jksjid = this_.param.jkid;
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
			htmlContent += "<tr>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";
			 if(row.gdlx == 1){
			    	htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx)+"'>"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx)+"</td>"; 
			    }else{
			    	htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.gdlx)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.gdlx)+"</td>"; 
			    }
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.rwh)+"' style='text-align:left;vertical-align:middle;'>";
			if(row.gdlx == 1){
				htmlContent +="<a href=\"javascript:viewWxxm('"+row.paramsMap.rwid+"')\">"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			}else if(row.gdlx == 2){
				htmlContent +="<a href=\"javascript:viewEo('"+row.paramsMap.rwid+"')\">"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			}else if(row.gdlx == 3){
				htmlContent +="<a href=\"javascript:viewNrc('"+row.paramsMap.rwid+"')\">"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			}else if(row.gdlx == 5 && row.jksjid){
				htmlContent +="<a href=\"javascript:viewPo('"+row.paramsMap.rwid+"')\">"+StringUtil.escapeStr(paramsMap.rwh)+"</a>";
			}
			htmlContent +="</td>";
			if(paramsMap.rwbb !=null && paramsMap.rwbb !=''){
				htmlContent += "<td title='"+StringUtil.escapeStr("R"+paramsMap.rwbb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+paramsMap.rwbb)+"</td>";
			}else{
				htmlContent += "<td title='' style='text-align:left;vertical-align:middle;'></td>";
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.ckh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'><a href=\"javascript:viewWorkOrder('"+row.paramsMap.gdid+"')\">"+StringUtil.escapeStr(row.gdbh)+"</a></td>";
			/*工单附件*/
			if(paramsMap.gdfjcount != null && paramsMap.gdfjcount > 0){
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += '<i gdid="'+row.paramsMap.gdid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			htmlContent += this_.formatLastfjjlb(row.paramsMap.fxjlbxx);
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.bjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xingh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xingh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xlh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.sjJsrq?row.sjJsrq.substring(0,10):'')+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.sjJsrq?row.sjJsrq.substring(0,10):'')+"</td>";
			htmlContent += this_.formatLastData(row.paramsMap.jhsjsj,row.zt);
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzxx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzxx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.clcs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.clcs)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.sjGzz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sjGzz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.sjJcz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sjJcz)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
		this_.initWebuiPopover();
		this_.initWebuiPopover_flb();
	},
	initWebuiPopover:function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#aircraft_performHisotry_status_table").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList:function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('gdid'), type : '工单附件'},
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	initWebuiPopover_flb:function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view-fxjlb','body',function(obj){
			return this_.getHistoryAttachmentList_flb(obj);
		});
		$("#structrucRepair_table").scroll(function(){
			$('.attachment-view-fxjlb').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList_flb:function(obj){//获取历史附件列表
		var jsonData = [
		                {mainid : $(obj).attr('fxjlbid'), type : 'FLB附件'},
		                ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	formatLastfjjlb : function(data){
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var fjjlb = '';
		var fjjlbTitle = '';
		var jlbfj = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			fjjlb += "<a href=\"javascript:fjjlbView('"+tdArr[0]+"')\">"+tdArr[1]+"</a></br>";
			fjjlbTitle += tdArr[1]+"\n";
			if(tdArr[3] >0){
				jlbfj +='<i fxjlbid="'+tdArr[0]+'" class="attachment-view-fxjlb glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i></br>';
			}
		});
		str += "<td title='"+fjjlbTitle.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+fjjlb+"</td>";
		str += "<td title='飞行记录本附件' style='text-align:center;vertical-align:middle;'>"+jlbfj+"</td>";
		return str;
	},
	formatLastData : function(data,zt){
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
			scjh += this_.formatJkz(tdArr[0],tdArr[1]);
			scsj += this_.formatJkz(tdArr[0],tdArr[2]);
		});
		if(zt == 9){
			str += "<td style='text-align:center;vertical-align:middle;'></td>";
		}else{
			str += "<td title='"+scsj.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
		}
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
			value = " "+"</br>";
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
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"20\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	checkSingleRow:function(this_){
		var index = $(this_).attr("index");
		SelectUtil.clickRow(index, this.tbodyId, 'air_m_row');
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
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
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
		this.getdate();
		this.load(1,"auto","desc");
	},
	getdate : function(){
		var this_ = this;
		 this_.param.fjzch = $("#fjzch").val();
		 this_.param.dprtcode = $("#dprtcode_search").val();
		 //this_.param.jkid = jkid;
	}
	
}	
//跳转到工单查看
function viewWorkOrder(gdid){
	window.open( basePath+"/produce/workorder/woView?gdid="+gdid);
}
//跳转到维修项目
function viewWxxm (rwid){
	window.open(basePath+"/project2/maintenanceproject/view?id="+rwid);
}
//跳转到Eo
function viewEo (rwid){
	window.open(basePath+"/project2/eo/view?id="+rwid);
}
//跳转到生产指令
function viewPo (rwid){
	window.open(basePath+"/project2/production/view?id="+rwid);
}
//跳转到Nrc
function viewNrc (rwid){
	window.open(basePath+"/workorder/woView?gdid="+rwid);
}
//跳转到飞机记录本
function fjjlbView(id){
	window.open(basePath + "/frame/flb/view?id=" + id);
}