
	$(function(){
		Navigation(menuCode,'查看差异','View Difference','韩武','2017-08-01','韩武','2017-08-01');
		
		// 加载维修方案数据
		loadWxfa();
		
		// 滚动条同步移动
		synchronizeMovementAll();
		$(window).resize(function(){
			App.resizeHeight();
		})
		
	});
	
	/**
	 * 计算左面版高度
	 */
	function initHeight(){
		$("#left_panel").height($("#right_panel").height());
	}
	
	var current_wxfa;
	var previous_wxfa;
	var current_wxxm;
	var previous_wxxm;
	
	/**
	 * 加载维修方案数据
	 */
	function loadWxfa(){
		var id = $("#wxfaid").val();
		if(id){
			AjaxUtil.ajax({
				type: "post",
		 		url:basePath+"/project2/maintenancescheme/queryDifferenceData",
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		data:JSON.stringify({
		 			id : id
		 		}),
		 		success:function(data){
		 			var current = data.current;
		 			var previous = data.previous;
		 			current_wxfa = current;
		 			previous_wxfa = previous;
		 			
		 			$("#jx").text(current.jx);
		 			$("#wxfabh").text(current.wxfabh);
		 			$("#bbSpan").text("R"+current.bb);
		 			
		 			$.each(current.atalist, function(i, ata){
		 				var html = "";
		 				var wczjhName = formatUndefine(ata.bh) + " " +formatUndefine(ata.ywmc);
		 				html += '<div class="project_list col-xs-12 col-md-12 col-lg-12 padding-left-0 padding-right-0 padding-bottom-10 line1 margin-bottom-0" >';
		 				html += '<h1 style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;margin-right:25px;padding-left:20px;cursor:pointer;" onclick="toggleUl(this)">'+wczjhName+'</h1>';
		 				html += '<i class="fa fa-angle-up cursor-pointer" style="background:none;border:0px;color:#000;position:absolute; left: -4px;font-size:14px;" onclick="toggleUl(this)"></i>'
		 				html += '<i style="background:#3598db;position:absolute; right: 5px;">'+ata.children.length+'</i>';
		 				html += '<ul class="project_list_ul">';
		 				$.each(ata.children, function(i, child){
		 					html += '<li  style="padding-right:5px;padding-left:20px;" onclick="loadWxxm(\''+child.id+'\')" id="'+child.id+'">';
		 					html += StringUtil.escapeStr(child.rwh + " " + child.rwms);
		 					html += showRevisionMark(current, child);
		 					html += '</li>';
		 				});
		 				html += '</ul>';
		 				html += '</div>';
		 				$("#itemList").append(html);
		 			});
		 			initHistoryList(current.id, current.bb);
		 			$("#itemList li:first").click();
		 		}
		    });
		}
	}
	
	
	/**
	 * 加载维修项目
	 * @param id
	 */
	function loadWxxm(id, fBbid){
		
		$("#itemList li").removeClass("active");
		$("#"+id).addClass("active");
		
		var data = {
 			id : id,
 			fBbid : fBbid,
 			paramsMap : {}
 		};
		if($("#wxfaid").val()){
			data.paramsMap.wxfaid = $("#wxfaid").val();
		}
		startWait();
		//返回到顶部
		$("#right_panel .panel-body").eq(0).prop('scrollTop','0');
		AjaxUtil.ajax({
			type: "post",
	 		url:basePath+"/project2/maintenanceproject/queryDifferenceData",
	 		contentType:"application/json;charset=utf-8",
	 		dataType:"json",
	 		data:JSON.stringify(data),
	 		success:function(data){
	 			finishWait();
	 			if(data.current.bb == data.previous.bb){
	 				$("#previous_panel").hide();
	 				$("#current_panel").removeClass("col-lg-6").addClass("col-lg-12");
	 			}else{
	 				$("#previous_panel").show();
	 				$("#current_panel").removeClass("col-lg-12").addClass("col-lg-6");
	 			}
	 			
	 			$("div[name='monitor_panel_body']").css("height", "auto");
	 			$("div[name='relation_panel_body']").css("height", "auto");
	 			$("div[name='bz_panel_body']").css("height", "auto");
	 			$("div[name='gbyj_panel_body']").css("height", "auto");
	 			$("div[name='attachmentDiv'] .panel-body").css("height", "auto");
	 			
	 			fillWxxmData(data.current, "current");
	 			fillWxxmData(data.previous, "previous");
	 			/*initHeight();*/
	 			
	 			current_wxxm = data.current;
	 			previous_wxxm = data.previous;
	 			// 标记差异
	 			signDifference();
	 			
	 			// 同步高度
	 			synchronizeHeightAll();
	 		}
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
	 * 填充维修项目数据
	 * @param data
	 * @param type
	 */
	function fillWxxmData(data, type){
		
		if(data.wxxmlx == 4){
			$(".notPackage").hide();
			$("span[name='associateCnHead']").text("维修项目关联");
		}else{
			$(".notPackage").show();
			$("span[name='associateCnHead']").text("取代维修项目");
		}
		
		$("#"+type+"_bb").text("R"+data.bb);
		if(data.isBj == 1){
			$("#"+type+"_rii").attr("checked",true);
		}else{
			$("#"+type+"_rii").removeAttr("checked");
		}
		if(data.ali == 1){
			$("#"+type+"_ali").attr("checked",true);
		}else{
			$("#"+type+"_ali").removeAttr("checked");
		}
		if(data.isHdwz == 1){
			$("#"+type+"_isHdwz").attr("checked",true);
		}else{
			$("#"+type+"_isHdwz").removeAttr("checked");
		}
		$("#"+type+"_wxxmlx").text(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',data.wxxmlx));
		$("#"+type+"_yxbs").text(DicAndEnumUtil.getEnumName('effectiveEnum',data.yxbs));
		var wxxmdlName = data.maintenanceClass ? (formatUndefine(data.maintenanceClass.dlbh) + " " +formatUndefine(data.maintenanceClass.dlywms)+"  "+ (data.maintenanceClass.dlzwms||"")) : "";
		$("#"+type+"_wxxmdl").text(wxxmdlName);
		var wczjhName = data.fixChapter ? (formatUndefine(data.fixChapter.zjh) + " " +formatUndefine(data.fixChapter.ywms)) : "";
		$("#"+type+"_zjh").text(StringUtil.escapeStr(wczjhName));
		$("#"+type+"_rwh").text(StringUtil.escapeStr(data.rwh));
		$("#"+type+"_ckh").text(StringUtil.escapeStr(data.ckh));
		if(data.wz && data.wz == 21){
			$("#"+type+"_wz_div").show();
			$("#"+type+"_wz").text(StringUtil.escapeStr("1#发"));
		}else if(data.wz && data.wz == 22){
			$("#"+type+"_wz_div").show();
			$("#"+type+"_wz").text(StringUtil.escapeStr("2#发"));
		}else if(data.wz && data.wz == 23){
			$("#"+type+"_wz_div").show();
			$("#"+type+"_wz").text(StringUtil.escapeStr("3#发"));
		}else if(data.wz && data.wz == 24){
			$("#"+type+"_wz_div").show();
			$("#"+type+"_wz").text(StringUtil.escapeStr("4#发"));
		}else{
			$("#"+type+"_wz_div").hide();
		}
		$("#"+type+"_ckwj").text(StringUtil.escapeStr(data.ckwj));
		$("#"+type+"_cmph").text(StringUtil.escapeStr(data.cmph));
		$("#"+type+"_ssdjb").text(StringUtil.escapeStr(data.ssdjb));
		$("#"+type+"_rwms").text(StringUtil.escapeStr(data.rwms));
		$("#"+type+"_qy").text(buildCover(data, 1));
		$("#"+type+"_jj").text(buildCover(data, 2));
		$("#"+type+"_fjzw").text(buildCover(data, 3));
		$("#"+type+"_xmlx").text(StringUtil.escapeStr(data.xmlx));
		$("#"+type+"_gzlx").text(StringUtil.escapeStr(data.gzlx));
		$("#"+type+"_eo").text(StringUtil.escapeStr(data.eobh));
		$("#"+type+"_gk").text(StringUtil.escapeStr(data.gkbh));
		$("#"+type+"_jsgs").text(DicAndEnumUtil.getEnumName('computationalFormulaEnum',data.jsgs));
		$("#"+type+"_jgms").text(StringUtil.escapeStr(data.jgms));
		$("#"+type+"_gs").text(buildWorkTimeContent(data));
		$("#"+type+"_fjzch").text(buildFjzch(data));
		$("#"+type+"_bj").text($(data.projectMaterialList).map(function(){
			return StringUtil.escapeStr(this.bjh)
		}).get().join(", "));
		$("#"+type+"_bz").text(StringUtil.escapeStr(data.bz));
		
		// 加载相关维修项目
		loadRelationData(data.projectList, type);
		
		// 加载改版依据
		loadBasedOn(data.scheme, type);
		
		// 加载监控项
		loadMonitorItem(data, type);
		
		// 加载附件
		loadAttachment(data, type);
		
		TableUtil.addTitle();
		FormatUtil.removeNullOrUndefined();
	}
	
	/**
	 * 加载改版依据
	 */
	function loadBasedOn(wxfa, type){
		$("#"+type+"_gbyj").text(StringUtil.escapeStr(wxfa.gbyj));
		var html = "";
		if(wxfa.technicalList){
			$.each(wxfa.technicalList, function(i, obj){
				html += '<a href="javascript:void(0);" class="pull-left" title="'+StringUtil.escapeStr(obj.pgdzt)+'" onclick="viewAssessment(\''+obj.id+'\')">'+obj.pgdh+" R"+obj.bb+'</a>';
				html += '<br/>';
			});
		}
		$("#"+type+"_pgd").html(html||"");
	}
	
	/**
	 * 查看技术评估单
	 * @param id
	 */
	function viewAssessment(id){
		window.open(basePath + "/project2/assessment/view?id=" + id);
	}
	
	/**
	 * 显示修订标识
	 * @param wxfa
	 * @param item
	 * @returns
	 */
	function showRevisionMark(wxfa,item){
		if(wxfa.bb == item.bb && (wxfa.zxbs == 1 || wxfa.zxbs == 2)){
			if(!item.fBbid){
				return "<span class='badge pull-right' style='background-color:black;'>A</span>";
			}else if(item.yxbs == 0){
				return "<span class='badge pull-right' style='background-color:black;'>D</span>";
			}else{
				return "<span class='badge pull-right' style='background-color:black;'>R</span>";
			}
		}
		return "";
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
	 * 加载相关维修项目
	 * @param list
	 * @param type
	 */
	function loadRelationData(list, type){
		var tbody = $("#"+type+"_maintenance_project_list");
		tbody.empty();
		var count = 0;
		if(list != null && list.length > 0){
			$.each(list,function(index,row){
				if(row.wxxmlx == 4){
					$("#"+type+"_ssdjb").text(row.rwh + " R" + row.bb);
				}else{
					tbody.append(["<tr>",
						              "<td title='"+StringUtil.escapeStr("R"+row.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+row.bb)+"</td>",
						              "<td title='"+StringUtil.escapeStr(row.rwh)+"' style='text-align:left;vertical-align:middle;'><a href='javascript:void(0);' onclick='viewProject(\""+row.id+"\")'>"+StringUtil.escapeStr(row.rwh)+"</a></td>",  
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
	 * 加载附件
	 * @param wxxm
	 * @param type
	 */
	function loadAttachment(wxxm, type){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(type+'_attachments_list_edit');
		attachmentsObj.show({
			attachHead : true,
			djid:formatUndefine(wxxm.id),
			fileType:"reference",
			chinaHead:(type=="current"?"<i validate='attachment' class='icon-circle pull-left' style='display: none;'></i>":"")+"参考文件附件",
			englishHead:"Reference Attachment",
	 		colOptionhead : false,
			fileHead : false,
			left_column : false,
		});
	}
	
	/**
	 * 加载监控项
	 * @param data
	 * @param type
	 */
	function loadMonitorItem(data, type){
		var htmlContent = '';
		var wxxmlx = data.wxxmlx;
		if(wxxmlx == 2 || wxxmlx == 3){
			$("#"+type+"_maintenance_version_bj").show();
			if(data.projectMaterialList.length > 0){
				$.each(data.projectMaterialList,function(j,mon){
					htmlContent +="<tr>"
					htmlContent +="<td style='text-align:left;'>"+mon.bjh+"</td>"
					htmlContent +="<td colspan='4'>"+buildMonitorItem(mon.projectMonitors)+"</td>"
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
			}
		}else{
			$("#"+type+"_maintenance_version_bj").hide();
			if(data.projectMonitors.length > 0){
				htmlContent +="<tr>"
				htmlContent +="<td colspan='4'>"+buildMonitorItem(data.projectMonitors)+"</td>"
				htmlContent +="</tr>"
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
			}
		}
		$("#"+type+"_maintenance_version_list").html(htmlContent);
		resizeVersionWidth(type);
		
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
	//展开UL效果
	function toggleUl(obj){
		var project_list=$(obj).parent("div.project_list");
		var project_list_ul=project_list.find("ul.project_list_ul");
		var displayStyle=project_list_ul.css("display");
		if(displayStyle=="block"){
			project_list.find("i").eq(0).removeClass("fa-angle-up");
			project_list.find("i").eq(0).addClass("fa-angle-down")
			project_list_ul.hide();
		}else{
			project_list.find("i").eq(0).removeClass("fa-angle-down");
			project_list.find("i").eq(0).addClass("fa-angle-up")
			project_list_ul.show();
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
	function resizeVersionWidth(type){
		$("#"+type+"_maintenance_version_list div[name='jkxm']").width($("#"+type+"_maintenance_version_jkxm").outerWidth());
		$("#"+type+"_maintenance_version_list div[name='sj']").width($("#"+type+"_maintenance_version_sj").outerWidth());
		$("#"+type+"_maintenance_version_list div[name='zq']").width($("#"+type+"_maintenance_version_zq").outerWidth());
		$("#"+type+"_maintenance_version_list div[name='rc']").width($("#"+type+"_maintenance_version_rc").outerWidth()-10);
	}
	
	/**
	 * 页面添加左右滚动条同步移动
	 */
	function synchronizeMovementAll(){
		synchronizeMovement($("div[name='monitorItemDiv']"));
		synchronizeMovement($("div[name='relationDiv']"));
		synchronizeMovement($("div[name='attachmentDiv'] .panel-body>div>div"));
	}
	
	/**
	 * 左右滚动条同步移动
	 */
	function synchronizeMovement(obj){
		obj.scroll(function(){
			obj.scrollLeft($(this).scrollLeft());
		});
	}
	
	/**
	 * 页面高度同步
	 */
	function synchronizeHeightAll(){
		synchronizeHeight($("div[name='main_body']:visible"));
		synchronizeHeight($("div[name='monitor_panel_body']"));
		synchronizeHeight($("div[name='relation_panel_body']"));
		synchronizeHeight($("div[name='bz_panel_body']"));
		synchronizeHeight($("div[name='gbyj_panel_body']"));
		synchronizeHeight($("div[name='attachmentDiv'] .panel-body"));
	}
	
	/**
	 * 同步高度
	 * @param obj
	 */
	function synchronizeHeight(obj){
		if(obj.length == 2){
			var obj1 = obj.eq(0);
			var obj2 = obj.eq(1);
			obj1.height("auto");
			obj2.height("auto");
			if(obj1.height() > obj2.height()){
				obj2.height(obj1.height());
			}else if(obj1.height() < obj2.height()){
				obj1.height(obj2.height());
			}
		}else{
			var obj1 = $(obj[0]);
			obj1.height("auto");
		}
	}
	
	/**
	 * 初始化历史列表
	 * @param wxxmid
	 */
	function initHistoryList(wxxmid, bb){
		if(wxxmid){
			MaintenanceSchemeHistory.getHistoryData(wxxmid, function(list){
				WebuiPopoverUtil.initWebuiPopover('attachment-view2','#historyDiv',function(data){
					return MaintenanceSchemeHistory.buildHistoryHtml(list);
				});
				var wxfa;
				if(list && list.length > 0){
					$.each(list, function(i, obj){
						if(parseFloat(obj.bb) < parseFloat(bb)){
							wxfa = obj;
							return false;
						}
					});
				}
				if(wxfa){
					$("#lastBbData").html('<a href="javascript:void(0);" onclick="MaintenanceSchemeHistory.viewScheme(\''+wxfa.id+'\')"> R'+wxfa.bb+'</a>');
				}else{
					$("#lastBbSpan").hide();
				}
			});
		}
	}
	
	function customResizeHeight(bodyHeight, tableHeight){
		/*resizeVersionWidth("current");
		resizeVersionWidth("previous");*/
		//搜索的高度
		 var rowHeight = $('.row-height:visible').outerHeight()||0;
		 var panel_header=$("#left_panel .panel-heading").outerHeight()||0;
		 var panel_body=bodyHeight-panel_header-rowHeight-15;
		 $("#left_panel .panel-body").css({"height":panel_body+"px","overflow":"auto"});
		 $("#right_panel .panel-body").eq(0).css({"height":panel_body+"px","overflow":"auto"})
		
	}
	

	/**
	 * 查看差异表
	 */
	function viewDifference(){
		$("#modal_wxfabh").val(current_wxfa.wxfabh);
		$("#modal_wxfamc").val(current_wxfa.zwms);
		$("#modal_previous_bb").text("R"+previous_wxfa.bb);
		$("#modal_current_bb").text("R"+current_wxfa.bb);
		
		ModalUtil.showModal("alertDiffForm");
		
		var list = getDifferentList(current_wxxm, previous_wxxm);
		$("#modal_different").empty();
		var html = "";
		if(list.length > 0){
			$.each(list, function(i, obj){
				html += ['<tr>',
				         '<td style="text-align:center">'+(i+1)+'</td>',
				         '<td>'+StringUtil.escapeStr(obj.rwh)+'</td>',
				         '<td>'+StringUtil.escapeStr(obj.rwms)+'</td>',
				         '<td>'+StringUtil.escapeStr(obj.des)+'</td>',
				         '<td style="text-align:center">'+obj.type+'</td>',
				         '</tr>'].join("");
			});
		}else{
			html = '<tr><td colspan="5" class="text-center" title="暂无数据 No data.">暂无数据 No data.</td></tr>';
		}
		$("#modal_different").append(html);
		
	}
	
	/**
	 * 标记差异
	 */
	function signDifference(){
		var list = getDifferentList(current_wxxm, previous_wxxm);
		$("i[validate]").hide();
		$.each(list, function(i, obj){
			$("i[validate*='"+obj.name+"']").show();
		});
	}
	
	/**
	 * 获取差异集合
	 */
	function getDifferentList(current, previous){
		var diffList = [];
		getDiffer(current, current.isBj, previous.isBj, "rii", "RII", diffList);
		getDiffer(current, current.ali, previous.ali, "ali", "ALI", diffList);
		getDiffer(current, current.yxbs, previous.yxbs, "yxbs", "有效标识", diffList);
		getDiffer(current, current.ckh, previous.ckh, "ckh", "参考号", diffList);
		getDiffer(current, current.ckwj, previous.ckwj, "ckwj", "参考文件", diffList);
		getDiffer(current, current.cmph, previous.cmph, "cmph", "cmp号", diffList);
		getDiffer(current, current.rwms, previous.rwms, "rwms", "任务描述", diffList);
		getDiffer(current, current.xmlx, previous.xmlx, "xmlx", "项目类型", diffList);
		getDiffer(current, current.gzlx, previous.gzlx, "gzlx", "工作类型", diffList);
		getDiffer(current, current.jsgs, previous.jsgs, "jsgs", "计算公式", diffList);
		getDiffer(current, current.isHdwz, previous.isHdwz, "isHdwz", "后到为准", diffList);
		getDiffer(current, current.fixChapter.zjh, previous.fixChapter.zjh, "zjh", "ATA章节号", diffList);
		getDiffer(current, current.maintenanceClass.dlbh, previous.maintenanceClass.dlbh, "wxxmdl", "维修项目大类", diffList);
		getDiffer(current, current.gkbh, previous.gkbh, "gk", "工卡", diffList);
		getDiffer(current, current.eobh, previous.eobh, "eo", "取代EO", diffList);
		
		getDiffer(current, $("#current_ssdjb").text(), $("#previous_ssdjb").text(), "ssdjb", "所属定检包", diffList);
		getDiffer(current, $("#current_qy").text(), $("#previous_qy").text(), "qy", "区域", diffList);
		getDiffer(current, $("#current_jj").text(), $("#previous_jj").text(), "jj", "接近", diffList);
		getDiffer(current, $("#current_fjzw").text(), $("#previous_fjzw").text(), "fjzw", "飞机站位", diffList);
		getDiffer(current, $("#current_gs").text(), $("#previous_gs").text(), "gs", "工时", diffList);
		getDiffer(current, $("#current_fjzch").text(), $("#previous_fjzch").text(), "fjzch", "飞机注册号", diffList);
		getDiffer(current, $("#current_bj").text(), $("#previous_bj").text(), "bj", "部件", diffList);
		getDiffer(current, $("#current_maintenance_version_list").text(), $("#previous_maintenance_version_list").text(), "jkx", "监控项设置", diffList);
		getDiffer(current, $("#current_maintenance_project_list").text(), $("#previous_maintenance_project_list").text(), "glwxxm", "关联维修项目", diffList);
		getDiffer(current, $("#current_gbyj").text(), $("#previous_gbyj").text(), "gbyj", "改版依据", diffList);
		getDiffer(current, $("#current_pgd").text(), $("#previous_pgd").text(), "pgd", "技术评估单", diffList);
		getDiffer(current, $("#current_attachments_list_edit").text(), $("#previous_attachments_list_edit").text(), "attachment", "参考文件附件", diffList);
		
		return diffList;
		
		function getDiffer(current, text_current, text_previous, key, des, diffList){
			
			var result = {
					rwh : current.rwh,
					rwms : current.rwms,
					name : key,
					des : des
			};
			
			if(text_current && !text_previous){
				
				result.type = "新增";
				
			}else if(!text_current && text_previous){
				
				result.type = "删除";
				
			}else if(text_current != text_previous){
				
				result.type = "修改";
				
			}
			if(result.type){
				diffList.push(result);
			}
		}
	}
	
	function outMaintenance(){
		var param = {};
		param.wxfabh = current_wxfa.wxfabh;
		param.bb = current_wxfa.bb;
		param.dprtcode = current_wxfa.dprtcode;
		window.open(basePath+"/project2/maintenanceproject/difference.xls?paramjson="+JSON.stringify(param));
	}
	
