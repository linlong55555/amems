
maintenance_project_view = {
	id:'maintenance_project_view',
	param: {
		bjh : "",
		xlh : "",
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.initDataList();
	},
	initDataList : function(){
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/project2/maintenanceproject/queryApplyListByBjh",
			type: "post",
			dataType:"json",
			data:{
				bjh : this_.param.bjh,
				dprtcode : this_.param.dprtcode
			},
			success:function(data){
				finishWait();
				this_.appendHtml(data);
		    }
		}); 
	},
	appendHtml : function(list){
		var this_ = this;
		var html = "";
		var skbs = 0;
		var ssbs = 0;
		if(list.length > 0){
			$(list).each(function(i, obj){
				if(obj.wxxmlx == 2){
					skbs = 1;
				}
				if(obj.wxxmlx == 3){
					ssbs = 1;
				}
				var monitorCount = obj.projectMonitors.length;
				$(obj.projectMonitors).each(function(i2, monitor){
					
					var scz = (monitor.scz||"") + convertUnit(monitor.jklbh, monitor.scz, monitor.dwScz);
					var zqz = (monitor.zqz||"") + convertUnit(monitor.jklbh, monitor.zqz, monitor.dwZqz);
					var rc = "-" + monitor.ydzQ + "/+" + monitor.ydzH + convertUnit(monitor.jklbh, monitor.ydzQ, monitor.ydzHdw);
					 	
					html += "<tr>";
					if(i2 == 0){
						html += "<td rowspan='"+monitorCount+"' style='text-align:center;vertical-align: middle;'>";
						html += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+obj.id+"') >"+StringUtil.escapeStr(obj.rwh)+"</a>";
						html += "</td>";
						html += "<td rowspan='"+monitorCount+"' style='text-align:center;vertical-align: middle;'>";
						html += "R" + obj.bb;
						html += "</td>";
						html += "<td rowspan='"+monitorCount+"' style='text-align:left;vertical-align: middle;' title='"+StringUtil.escapeStr(obj.rwms)+"'>";
						html += StringUtil.escapeStr(obj.rwms);
						html += "</td>";
						html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+MonitorUnitUtil.getMonitorName(monitor.jklbh)+"</td>";
						html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+scz+"</td>";
						html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+zqz+"</td>";
						html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+rc+"</td>";
					}else{
						html += "<td style='text-align:center;border: none;'>"+MonitorUnitUtil.getMonitorName(monitor.jklbh)+"</td>";
						html += "<td style='text-align:center;border: none;'>"+scz+"</td>";
						html += "<td style='text-align:center;border: none;'>"+zqz+"</td>";
						html += "<td style='text-align:center;border: none;'>"+rc+"</td>";
					}
					
					html += "</tr>";
				});
			});
		}else{
			html += "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>";
		}
		
		$("#common_maintenance_list", $("#"+this_.id)).html(html);
		$("#common_skbs", $("#"+this_.id)).val(skbs);
		$("#common_ssbs", $("#"+this_.id)).val(ssbs);
		
		// 单位转换
		function convertUnit(jklbh, value, unit){
			if(value != null && value != "" && value != undefined){
				return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
			}else{
				return "";
			}
		}
	},
	view : function(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	}
}
