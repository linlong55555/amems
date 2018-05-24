
current_monitor_data_alert_Modal = {
	id : 'current_monitor_data_alert_Modal',
	getContextData : function(fjzch, dprtcode){
		var this_ = this;
		var searchParam={};
		if(fjzch != null && fjzch != ''){
			searchParam.fjzch = fjzch;
			searchParam.dprtcode = dprtcode;
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url:basePath+"/aircraftinfo/status/queryAll",
				type:"post",
				contentType:"application/json;charset=utf-8",
				async : false,
				data:JSON.stringify(searchParam),
				dataType:"json",
				success:function(data){
					finishWait();
					if(data.row){
						this_.appendStatus(data.row);
		 				if(data.rows.length > 0){
		 					this_.appendContentHtml(data.rows);
		 				}else{
		 					this_.setNoData();
		 				}
			 		}else{
			 			$("#jssj", $("#"+this.id)).val("0FH");
						$("#jsxh", $("#"+this.id)).val("0FC");
					}
				}
			});
		}else{
		 
		}
		
		return $("#"+this.id).html();
	},
	/**
	 * 拼接状态
	 */
	appendStatus:function(row){
		if(row.paramsMap.shzt == 0){
			$("#status", $("#"+this.id)).html("<i class='icon-fighter-jet font-size-20 color_green'></i>适航");
		}else{
			$("#status", $("#"+this.id)).html("<i class='icon-fighter-jet font-size-20 color_red'></i>不适航");
		}
	},
	/**
	 * 拼接表格
	 */
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			if(row.wz == 11){
				this_.formatLastJs(row.paramsMap.jkz);
			}else{
				htmlContent += "<tr >";
				htmlContent += "<td class='text-center colwidth-4' title='"+DicAndEnumUtil.getEnumName('installedPositionEnum',row.wz)+"' >"+DicAndEnumUtil.getEnumName('installedPositionEnum',row.wz)+"</td>";
				htmlContent += "<td class='text-left colwidth-10' title='P/N: "+row.jh+"\nS/N: "+row.xlh+"' >";
				htmlContent += "P/N: "+row.jh+"</br>S/N: "+row.xlh+"";
				htmlContent += "</td>";
				htmlContent +=this_.formatLastData(row.paramsMap.jkz);
				htmlContent += "</tr>" ;
			}
		});
		if(htmlContent != ''){
			$("#current_monitor_data_list", $("#"+this_.id)).empty();
			$("#current_monitor_data_list", $("#"+this_.id)).html(htmlContent);
		}else{
			this_.setNoData();
		}
	},
	setNoData : function(){
		$("#current_monitor_data_list", $("#"+this.id)).empty();
		$("#current_monitor_data_list", $("#"+this.id)).append("<tr><td class='text-center' rowspan='3'>暂无数据 No data.</td></tr>");
	},
	formatLastJs: function(row){
		var this_ = this;
		var list = row.split(",");
		$.each(list,function(index,row){
			var strList = row.split("#_#");
			if(strList[1] == "2_10_FH"){
				$("#jssj").html(this_.formatJkz(strList[1],strList[2]));
			}else{
				$("#jsxh").html(this_.formatJkz(strList[1],strList[2]));
			}
		});
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + " "+MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
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
	formatLastData : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td class='colwidth-5'></td>";
			return str;
		}
		var list = data.split(",");
		var jkz = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			jkz += this_.formatJkz(tdArr[1],tdArr[2]);
		});
		str += "<td title='"+jkz.replaceAll("</br>","\n")+"' style='text-align:center;' class='colwidth-8'>"+jkz+"</td>";
		return str;
	}
}