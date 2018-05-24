/**
 * 执行历史
 */
executionHistory = {
	id:'executionHistory',
	tableDivId : 'executionHistory_top_div',
	tableId : 'executionHistory_table',
	tbodyId : 'executionHistory_tbody',
	param: {
		zt : '',
		gdid : '',
		jksjid : '',
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
		if(this_.param.dprtcode == null || this_.param.dprtcode == '' || (this_.param.jksjid == '' && this_.param.gdid == '')){
			this_.setNoData();
			return;
		}
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		
		var url = basePath+"/produce/workorder/queryAllTaskhistory";
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data != null && data.length >0){
					this_.appendContentHtml(data);
				} else {
					this_.setNoData();
				}
	      }
	    }); 
	},	
	/**
	 * 将搜索条件封装 
	 */
	getParams : function(){
		var searchParam = {};
		var paramsMap = {};
		paramsMap.jksjid = this.param.jksjid?this.param.jksjid:"";
		paramsMap.gdid = this.param.gdid?this.param.gdid:"";
		paramsMap.zt = this.param.zt;
		searchParam.dprtcode = this.param.dprtcode;
		searchParam.fjzch = this.param.fjzch;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml :function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			htmlContent += "<tr>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
			
			if(paramsMap.byxjksjid != null && paramsMap.byxjksjid != ''){
				//加入取代小图片
				if(index%2===0){
					htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;background:url("+basePath+"/static/images/replace.png) #f9f9f9 no-repeat 100% 50%'>";
				}else{
					htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;background:url("+basePath+"/static/images/replace.png) no-repeat 100% 50%'>";
				}
			}else{
				htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle'>";
			}
			
			htmlContent += "<a href='javascript:void(0);'  onclick="+this_.id+".viewWorkOrder('"+paramsMap.gdid+"')>"+StringUtil.escapeStr(row.gdbh)+"</a>";
			/*htmlContent += "<img src='"+basePath+"/static/images/replace.png'/>"*/
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
			
			/*工单附件*/
			if((paramsMap.gdfjcount != null && paramsMap.gdfjcount > 0) || (row.gznrfjid != null && row.gznrfjid != "")){
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += '<i qtid="'+paramsMap.gdid+'" gznrfjid="'+row.gznrfjid+'" class="attachment-gd-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			htmlContent += this_.formatLastfjjlb(paramsMap.fxjlbxx);
			
			htmlContent += "<td title='"+indexOfTime(row.paramsMap.jssj)+"' style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.paramsMap.jssj)+"</td>";
			htmlContent += this_.formatLastData(paramsMap.jhsjsj);
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			if(row.wgbs == 1){
				htmlContent += "<a type=1 gdbh='"+StringUtil.escapeStr(row.gdbh)+"' gdid='"+paramsMap.gdid+"' href='javascript:void(0);' onclick="+this_.id+".openWinDetail(this)>已反馈</a>";
			}else{
				htmlContent += "未反馈";
			}
			htmlContent += "</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<a type=2 gdbh='"+StringUtil.escapeStr(row.gdbh)+"' gdid='"+paramsMap.gdid+"' href='javascript:void(0);' onclick="+this_.id+".openWinDetail(this)>查看详情</a>";
			htmlContent += "</td>";
			
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initGdWebuiPopover();
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	initGdWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-gd-view',"body",function(obj){
			return this_.getGdAttachmentList(obj);
		});
		$("#"+this_.tableDivId, $("#"+this_.id)).scroll(function(){
			$('.attachment-gd-view', $("#"+this_.id)).webuiPopover('hide');
		});
	},
	getGdAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	formatLastfjjlb : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			str += "<td></td>";
			//str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var fjjlb = '';
		var fjjlbTitle = '';
		var jlbym = '';
		//var jlbfj = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			fjjlb += "<a href=\"javascript:"+this_.id+".fjjlbView('"+tdArr[0]+"')\">"+tdArr[1]+"</a></br>";
			fjjlbTitle += tdArr[1]+"\n";
			jlbym += tdArr[2]+"</br>";
			//jlbfj +='<i fxjlbid="'+tdArr[0]+'" class="attachment-view-fxjlb glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i></br>';
		});
		str += "<td title='"+fjjlbTitle.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+fjjlb+"</td>";
		str += "<td title='"+jlbym.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+jlbym+"</td>";
		//str += "<td title='飞行记录本附件' style='text-align:center;vertical-align:middle;'>"+jlbfj+"</td>";
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
			scjh += this_.formatJkz(tdArr[0], tdArr[1]);
			scsj += this_.formatJkz(tdArr[0], tdArr[2]);
		});
		str += "<td title='"+scsj.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
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
	 * 查看反馈或拆换件详情
	 */
	openWinDetail : function(this_){
	 	var gdid = $(this_).attr("gdid");
	 	var gdbh = $(this_).attr("gdbh");
	 	var type = $(this_).attr("type");
	 	feedback_replacement_tab_view.show({
	 		type : type,
	 		gdbh : gdbh,
	 		gdid : gdid
		});
	},
	/**
	 * 查看飞行记录本
	 */
	fjjlbView : function(id){
		window.open(basePath+"/produce/flb/view?id="+id);
	},
	/**
	 * 查看工单
	 */
	viewWorkOrder : function(id){
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
};