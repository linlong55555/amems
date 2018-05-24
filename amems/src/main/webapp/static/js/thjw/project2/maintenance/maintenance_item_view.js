
	$(function(){
		Navigation(menuCode,'维修项目查看','View Item','韩武','2017-08-01','韩武','2017-08-01');
		
		// 加载维修项目
		loadWxxm();
		
		// 维修项目对比
		compareDifference();
	});
	
	/**
	 * 维修项目对比
	 */
	function compareDifference(){
		$("#compareBtn").click(function(){
			var id = $("#wxxmid").val();
			var wxxmid;
			var wxfaid;
			if(id.indexOf("/") != -1){
				wxxmid = id.split("/")[0];
				wxfaid = id.split("/")[1];
			}else{
				wxxmid = id;
			}
			window.open(basePath + "/project2/maintenanceproject/different?id=" + wxxmid);
		});
	}
	
	/**
	 * 维修项目查看
	 * @param id
	 */
	function viewProject(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	}
	
	/**
	 * EO查看
	 * @param id
	 */
	function viewEO(id){
		window.open(basePath + "/project2/eo/view?id=" + id);
	}
	

	/**
	 * 加载附件
	 * @param id
	 */
	function loadAttachment(id){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			attachHead : true,
			djid:formatUndefine(id),
			fileType:"reference",
			chinaHead:"参考文件附件",
			englishHead:"Reference Attachment",
	 		colOptionhead : false,
			fileHead : false,
		});
	}
	
	/**
	 * 加载相关维修项目
	 * @param list
	 * @param type
	 */
	function loadRelationData(list){
		var tbody = $("#maintenance_project_list");
		tbody.empty();
		var count = 0;
		if(list != null && list.length > 0){
			$.each(list,function(index,row){
				if(row.wxxmlx == 4){
					$("#ssdjb").val(row.rwh + " R" + row.bb);
				}else{
					tbody.append(["<tr>",
						              "<td title='"+StringUtil.escapeStr("R"+row.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+row.bb)+"</td>",
						              "<td title='"+StringUtil.escapeStr(row.rwh)+"' style='text-align:left;vertical-align:middle;'><a href='javascript:void(0);' onclick='MaintenanceProjectHistory.viewProject(\""+row.id+"\")'>"+StringUtil.escapeStr(row.rwh)+"</a></td>",  
						              "<td title='"+StringUtil.escapeStr(row.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ckh)+"</td>",  
						              "<td title='"+buildFjzch(row)+"' style='text-align:left;vertical-align:middle;'>"+buildFjzch(row)+"</td>",     
						              "<td title='"+StringUtil.escapeStr(row.ckwj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ckwj)+"</td>",
						              "<td title='"+buildWorkTimeContent(row)+"' style='text-align:left;vertical-align:middle;'>"+buildWorkTimeContent(row)+"</td>",  
					              "</tr>"].join(""));                                                                                                                                 
					count++;
				}
			});
		}  
		if(count == 0){
			tbody.append("<tr><td colspan='6' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 拼接工时
	 * @param row
	 * @returns {String}
	 */
	function buildWorkTimeContent(row){
		var result = "";
		if(row.jhgsRs!=null && row.jhgsRs!="" && row.jhgsXss!=null && row.jhgsXss!=""){
			var jhgsRs = row.jhgsRs || 0;
			var jhgsXss = row.jhgsXss || 0;
			var total = jhgsRs * jhgsXss;
			if(String(total).indexOf(".") >= 0){
				total = total.toFixed(2);
			}
			result = jhgsRs + " 人 x " + jhgsXss + " 时 = " + total + " 时";
		}
		return result;
	}
	
	/**
	 * 拼接飞机适用性
	 */
	function buildFjzch(row){
		if(row.syx == "00000"){
			return "ALL";
		}
		if(!row.syx || row.syx == "null"){
			return "N/A";
		}
		if(row.syx == "-"){
			return $(row.projectModelList).map(function(){
				return StringUtil.escapeStr(this.fjzch)
			}).get().join(", ")
		}
	}
	
	/**
	 * 加载维修项目
	 * @param id
	 */
	function loadWxxm(){
		var id = $("#wxxmid").val();
		var wxxmid;
		var wxfaid;
		if(id.indexOf("/") != -1){
			wxxmid = id.split("/")[0];
			wxfaid = id.split("/")[1];
		}else{
			wxxmid = id;
		}
		var data = {paramsMap : {}};
		data.id = wxxmid;
		if(wxfaid){
			data.paramsMap.wxfaid = wxfaid;
		}
		startWait();
		AjaxUtil.ajax({
			type: "post",
	 		url:basePath+"/project2/maintenanceproject/detail",
	 		contentType:"application/json;charset=utf-8",
	 		dataType:"json",
	 		data:JSON.stringify(data),
	 		success:function(data){
	 			finishWait();
	 			initHistoryList(wxxmid, data.bb);
	 			loadBasic(data);
	 			loadMonitorItem(data);
	 			loadRelationData(data.projectList);
	 			loadAttachment(wxxmid);
	 			resizeVersionWidth();
	 		}
	    });
	}
	
	/**
	 * 加载基础数据
	 * @param data
	 */
	function loadBasic(data){
		
		if(data.wxxmlx == 4){
			$(".wxxmDiv").remove();
			$("#wxxmCheckbox").remove();
			$("#ssdjbDiv").remove();
			$("#qdeo_div").remove();
		}else{
			$("#associateCnHead").text("取代维修项目");
			$("#associateCnTitle").text("取代维修项目");
			$("#associateEngTitle").text("Replace");
		}
		
		$("#bbSpan").text("R"+data.bb);
		if(data.isBj == 1){
			$("#rii").attr("checked",true);
		}else{
			$("#rii").removeAttr("checked");
		}
		if(data.ali == 1){
			$("#ali").attr("checked",true);
		}else{
			$("#ali").removeAttr("checked");
		}
		if(data.isHdwz == 1){
			$("#isHdwz").attr("checked",true);
		}else{
			$("#isHdwz").removeAttr("checked");
		}
		$("#wxxmlx").text(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',data.wxxmlx));
		$("#yxbs").text(DicAndEnumUtil.getEnumName('effectiveEnum',data.yxbs));
		open_win_maintenance_class.buildSelect("wxxmdl", data.jx, data.dprtcode, data.wxxmdlid);
		
		$("#wxfabh").val(StringUtil.escapeStr(data.wxfabh));
		var wczjhName = data.fixChapter ? (formatUndefine(data.fixChapter.zjh) + " " +formatUndefine(data.fixChapter.ywms)) : "";
		$("#zjh").val(StringUtil.escapeStr(wczjhName));
		if(!data.wz || data.wz == -1){
			$("#wz_div").remove();
		}
		$("#wz").val(StringUtil.escapeStr(data.wz));
		$("#cmph").val(StringUtil.escapeStr(data.cmph));
		$("#rwh").val(StringUtil.escapeStr(data.rwh));
		$("#ckh").val(StringUtil.escapeStr(data.ckh));
		$("#ckwj").val(StringUtil.escapeStr(data.ckwj));
		$("#rwms").text(StringUtil.escapeStr(data.rwms));
		$("#qy").val(buildCover(data, 1));
		$("#jj").val(buildCover(data, 2));
		$("#fjzw").val(buildCover(data, 3));
		DicAndEnumUtil.registerDic('17','gzlx',data.dprtcode);
		DicAndEnumUtil.registerDic('18','xmlx',data.dprtcode);
		$("#xmlx").val(StringUtil.escapeStr(data.xmlx));
		$("#gzlx").val(StringUtil.escapeStr(data.gzlx));
		$("#gkbh").val(StringUtil.escapeStr(data.gkbh));
		$("#eobh").val(StringUtil.escapeStr(data.eobh));
		if(data.eo){
			$("#eo_title_div").show();
			$("#eo_title").html("<a href='javascript:void(0);' onclick='viewEO(\""+data.eo.id+"\")'>"+data.eo.eozt+"</a>");
		}
		$("#jhgsRs").val(data.jhgsRs);
		$("#jhgsXss").val(data.jhgsXss);
		$("#bzgs").text((data.jhgsRs||0) * (data.jhgsXss||0));
		$("#fjzch").text(buildFjzch(data));
		DicAndEnumUtil.registerEnum('computationalFormulaEnum','jsgs',data.dprtcode);
		$("#jsgs").val(StringUtil.escapeStr(data.jsgs));
		$("#monitor_frame_component_jgms").val(StringUtil.escapeStr(data.jgms));
		$("#bj").val($(data.projectMaterialList).map(function(){
			return StringUtil.escapeStr(this.bjh)
		}).get().join(", "));
		$("#bz").text(StringUtil.escapeStr(data.bz));
	}
	
	/**
	 * 初始化历史列表
	 * @param wxxmid
	 */
	function initHistoryList(wxxmid, bb){
		if(wxxmid){
			MaintenanceProjectHistory.getHistoryData(wxxmid, 1, function(list){
				WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#historyDiv",function(data){
					return MaintenanceProjectHistory.buildHistoryHtml(list, 1);
				});
				
				var wxxm;
				if(list && list.length > 0){
					$.each(list, function(i, obj){
						if(parseFloat(obj.bb) < parseFloat(bb)){
							wxxm = obj;
							return false;
						}
					});
				}
				
				if(wxxm){
					$("#lastBbData").html('<a href="javascript:void(0);" onclick="MaintenanceProjectHistory.viewProject(\''+wxxm.id+'\')"> R'+wxxm.bb+'</a>');
				}else{
					$("#lastBbSpan").hide();
					$("#compareBtn").hide();
				}
			});
		}
	}
	
	/**
	 * 加载监控项
	 * @param data
	 * @param type
	 */
	function loadMonitorItem(data){
		var htmlContent = '';
		var wxxmlx = data.wxxmlx;
		if(wxxmlx == 2 || wxxmlx == 3){
			$("#maintenance_version_bj").show();
			if(data.projectMaterialList.length > 0){
				$.each(data.projectMaterialList,function(j,mon){
					htmlContent +="<tr>"
					htmlContent +="<td style='text-align:center;' name='bj'>"+mon.bjh+"</td>"
					htmlContent +="<td colspan='4'>"+buildMonitorItem(MonitorUnitUtil.sort(mon.projectMonitors, "jklbh"))+"</td>"
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
			}
		}else{
			$("#maintenance_version_bj").hide();
			if(data.projectMonitors.length > 0){
				htmlContent +="<tr>"
				htmlContent +="<td colspan='4'>"+buildMonitorItem(MonitorUnitUtil.sort(data.projectMonitors, "jklbh"))+"</td>"
				htmlContent +="</tr>"
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
			}
		}
		$("#maintenance_version_list").html(htmlContent);
		
		function buildMonitorItem(list){
			var htmlContent = '';
			if(list!=null){
				$.each(list,function(i,row){
					 	if (i == 1) {
					 		htmlContent = htmlContent + "<div class='monitor_rc_td'>";
						}
					 	
					 	var scz = (row.scz||"") + convertUnit(row.jklbh, row.scz, row.dwScz);
					    var zqz = (row.zqz||"") + convertUnit(row.jklbh, row.zqz, row.dwZqz);
					    var rz = "-" + row.ydzQ + "/+" + row.ydzH + convertUnit(row.jklbh, row.ydzQ, row.ydzHdw);
					    htmlContent += "<div>";
					    htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='jkxm'>"+MonitorUnitUtil.getMonitorName(row.jklbh)+"</div>"; 
						htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='sj'>"+StringUtil.escapeStr(scz)+"</div>";
						htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='zq'>"+StringUtil.escapeStr(zqz)+"</div>";
						htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='rc'>"+StringUtil.escapeStr(rz)+"</div>";
						if (i != 0) {
							htmlContent += "<br>";

						}
						if (i != 0 && i == list.length - 1) {
							htmlContent += "</div>";
						}
				});
			}
			return htmlContent;
		}
	}
	
	/**
	 * 监控类型单位转换
	 * @param jklbh
	 * @param value
	 * @param unit
	 */
	function convertUnit(jklbh, value, unit){
		if(value != null && value != "" && value != undefined){
			return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
		}else{
			return "";
		}
	}
	
	/**
	 * 计算监控项宽度
	 * @param type
	 */
	function resizeVersionWidth(){
		$("#maintenance_version_list div[name='jkxm']").width($("#maintenance_version_jkxm").outerWidth());
		$("#maintenance_version_list div[name='sj']").width($("#maintenance_version_sj").outerWidth());
		$("#maintenance_version_list div[name='zq']").width($("#maintenance_version_zq").outerWidth());
		$("#maintenance_version_list div[name='rc']").width($("#maintenance_version_rc").outerWidth()-10);
	}
	
	/**
	 * 拼接区域、盖板和飞机站位
	 * @param row
	 * @param type
	 * @returns
	 */
	function buildCover(row, type){
		return $(row.coverPlateList).map(function(){
			if(this.lx == type){
				if(type == 2 && this.coverPlateInformation){
					return StringUtil.escapeStr(this.gbh + " " + (this.coverPlateInformation.gbmc || ""));
				}else{
					return StringUtil.escapeStr(this.gbh);
				}
			}
		}).get().join(", ");
	}
	
	function customResizeHeight(bodyHeight, tableHeight){
		resizeVersionWidth();
	}
