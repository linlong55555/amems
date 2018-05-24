	$(function(){
		 maintenanceItemList.switchType();
	 });
	
	var maintenanceItemList={
			lastSelectId:null,
			lastSelectStyle:null,
			getDprtcode : function(){
				return $("#dprtId").val();
			},
			hideBottom:function(){
				if(this.lastSelectStyle!=null){
					$("#"+this.lastSelectId).removeClass(this.lastSelectStyle);
					this.lastSelectStyle=null;
					this.lastSelectId=null;
				}
				$(".displayContent").css("display","none");	
				App.resizeHeight();
			},
			// 初始化飞机注册号
			initAcReg : function(){
				var this_ = this;
				var wxfa = this_.getWxfa();
				var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this_.getDprtcode(), FJJX:wxfa.jx});
			 	var planeRegOption = '<option value="">全部</option>';
			 	if(planeDatas != null && planeDatas.length >0){
			 		$.each(planeDatas, function(i, planeData){
		 				planeRegOption 
		 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' " +
		 							"jx='"+StringUtil.escapeStr(planeData.FJJX)+"' "+
		 							"xlh='"+StringUtil.escapeStr(planeData.XLH)+"' "+
		 							"fdjsl='"+StringUtil.escapeStr(planeData.FDJSL)+"'>"
		 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
		 					+ "</option>";
			 		});
			 	}
		 		$("#fjzch").html(planeRegOption);
			},
			getWxfa : function(){
				var type = $("#pageType").val();
				if(type == 1){
					return maintenancePlan.wxfa;
				}else if(type == 2){
					return maintenancePlanAudit.wxfa;
				}else if(type == 3){
					return maintenancePlanApproval.wxfa;
				}else if(type == 4){
					return maintenancePlanEffect.wxfa;
				}else if(type == 5){
					return maintenancePlanView.wxfa; 
				}
				return {};
			},
			canEdit:function(){
				var this_ = this;
				var wxfa = this_.getWxfa();
				return wxfa.zt == 1 || wxfa.zt == 5 || wxfa.zt == 6;
			},
			switchType:function(){
				var type = $("#type").val();
				if(type != 1){
					$("#add_project_btn,#add_package_btn").remove();
				}
				if(type == 3){
					$("#export_btn").remove();
					$(".viewType").removeClass("hidden");
				}
			},
			switchBtn:function(){
				var this_ = this;
				if(this_.canEdit()){
					$("#add_project_btn,#add_package_btn").show();
				}else{
					$("#add_project_btn,#add_package_btn").hide();
				}
			},
			// 维修项目查看差异
			viewProjectDifference:function(id){
				window.open(basePath + "/project2/maintenanceproject/different?id=" + id);
			},
			// 维修方案查看差异
			viewSchemeDifference:function(id){
				window.open(basePath + "/project2/maintenancescheme/different?id=" + id);
			},
			// 维修项目查看
			viewProject:function(id){
				var wxfa = this.getWxfa();
				window.open(basePath + "/project2/maintenanceproject/view?id=" + id + "/" + wxfa.id);
			},
			// 维修方案查看
			viewScheme:function(id){
				window.open(basePath + "/project2/maintenancescheme/view?id=" + id);
			},
			// 技术评估单查看
			viewAssessment:function(id){
				window.open(basePath + "/project2/assessment/view?id=" + id);
			},
			// 工卡查看
			viewWorkCard:function(id){
				window.open(basePath + "/project2/workcard/view/" + id);
			},
			goPage:function(pageNumber,sortType,sequence){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
			},
			AjaxGetDatasWithSearch:function(){
				 var this_ = this;
				 var url = "";
				 var wxfa = this_.getWxfa();
				 // 按照ATA章节分组
				 if($("input[name='group_type']:radio:checked").val()==1){
					 url = "/project2/maintenanceproject/groupByATA";
				 }
				 // 按照目录分组
				 else{
					 url = "/project2/maintenanceproject/groupByCatalog";
				 }
				 startWait();
				 AjaxUtil.ajax({
					type: "post",
			 		url:basePath+url,
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify(this_.gatherSearchParam()),
			 		success:function(data){
			 			finishWait();
		 				this_.appendContentHtml(data);
		 				FormatUtil.removeNullOrUndefined();
		 				// 标记关键字
		 				var keyword = $("#item_keyword_search").val();
					    signByKeyword(keyword, [2,4,5,10]);
					    new Sticky({tableId:'maintenance_item_table'});
					    var offset = $(".left_first_body").scrollTop();
					    TableUtil.hideDisplayContent();
					    $(".left_first_body").scrollTop(offset);
					    
					    $("th[name='ope_td']").removeClass("upward").addClass("downward");
					    if(keyword){
					    	this_.expend();
					    }
			 		}
			     });
			},
			gatherSearchParam:function(){
				 var this_ = this;
				 var url = "";
				 var wxxmlxList = [];
				 var type = "";
				 var wxfa = this_.getWxfa();
				 var yxbs = $("#yxbs").val();
				 $("[name=wxxmlx]:checkbox:checked").each(function(){
					 wxxmlxList.push($(this).val());
				 });
				 // 按照ATA章节分组
				 if($("input[name='group_type']:radio:checked").val()==1){
					 url = "/project2/maintenanceproject/groupByATA";
					 type = 1;
				 }
				 // 按照目录分组
				 else{
					 url = "/project2/maintenanceproject/groupByCatalog";
					 type = 2;
				 }
				 var param = {
					jx : wxfa.jx,
		 			wxfabh : wxfa.wxfabh,
		 			dprtcode : this_.getDprtcode(),
		 			bb : wxfa.bb,
		 			zt : wxfa.zt,
		 			id : wxfa.id,
		 			yxbs : yxbs,
		 			paramsMap : {
		 				wxxmlxList : wxxmlxList,
		 				type : type,
		 				keyword : $("#item_keyword_search").val(),
		 				syx : $("#fjzch").val()
		 			}
				 };
				 return param;
			},
			reload:function(){
				this.goPage(1,"auto","desc");
			},
			// 重新加载行
			reloadRow:function(data, newData){
				
				var this_ = this;
				newData = newData || {};
				var parent = $("input[name='group_type']:radio:checked").val()==1 ? newData.fixChapter : newData.maintenanceClass;
				if(parent){
					parent.id = Math.uuid().toLowerCase();
				}
				
				// 对于新增时，如果后台转入改版逻辑，则刷新该行而非新增
				if(!data.id){
					var $oldrow = $("#maintenance_item_list tr[rwh='"+newData.rwh+"']");
					if($oldrow.length == 1){
						data.id = $oldrow.attr("id");
					}
				}
				
				if(data.id){	// 修改或改版或撤销
					if (StringUtil.escapeStr(newData.option) == "revoke") { // 撤销
						$resRow = $("#"+data.id);
						
						if (StringUtil.escapeStr(newData.removeRow) == "true") {	// 撤销后减去数量
							$resRow.replaceWith(this_.generateRow(newData, parent));
							this_.modifyRowCount(newData.id, -1,"revoke");
						}
						else{
							//删除当前行
							$resRow = $("#"+data.id);
							this_.modifyRowCount(data.id, -1);
							$resRow.remove();
							
							//新增当前行,到目标组
							var bh = parent.zjh || parent.dlbh;
							var $parentRow = $("tr[name='"+bh+"']"); //目标组
							$(this_.generateRow(newData, parent)).insertAfter($parentRow);
						}
					} else { //修改: 先删除再添加(应对转组)
						//修改前,行父ID
						var beforeRowParentId = $.trim($("#"+data.id).attr("class")); 
						//修改后,行父ID
						var bh = parent.zjh || parent.dlbh;
						var $parentRow = $("tr[name='"+bh+"']"); //目标组
						var afterRowParentId =  $.trim(StringUtil.escapeStr($parentRow.attr("id")));
						
						//新增
						if (afterRowParentId == "") {
							//删除当前行
							$resRow = $("#"+data.id);
							this_.modifyRowCount(data.id, -1);
							$resRow.remove();
							
							//新增当前行,到目标组
							parent.bh = bh;
							$("#maintenance_item_list").append(this_.generateParentRow(parent));
							$parentRow = $("tr[name='"+bh+"']");
							$(this_.generateRow(newData, parent)).insertAfter($parentRow);
						}
						//互换
						else{
							//原编号
							var old_bh = $("#"+beforeRowParentId).attr("name");
							//删除当前行
							$resRow = $("#"+data.id);
							//编号发生改变则更新父行数量
							if(bh != old_bh){
								this_.modifyRowCount(data.id, -1);
							}
							$resRow.remove();
							
							//新增当前行,到目标组
							$(this_.generateRow(newData, parent)).insertAfter($parentRow);
							//编号发生改变则更新父行数量
							if(bh != old_bh){
								this_.modifyRowCount(newData.id, 1);
							}
						}
					}
				}else{	// 新增
					
					$("#maintenance_item_list .no-data").remove();
					
					var bh = parent.zjh || parent.dlbh;
					var $parentRow = $("tr[name='"+bh+"']");
					
					if($parentRow.length == 0){
						parent.bh = bh;
						$("#maintenance_item_list").append(this_.generateParentRow(parent));
						$parentRow = $("tr[name='"+bh+"']");
						$(this_.generateRow(newData, parent)).insertAfter($parentRow);
					}else{
						$(this_.generateRow(newData, parent)).insertAfter($parentRow);
						this_.modifyRowCount(newData.id, 1);
					}
				}
				
				TableUtil.addTitle("#maintenance_item_list tr td");
				this_.initWebuiPopover();
			},
			// 拼接标题
			generateParentRow:function(row){
				var bh = row.bh;
				var groupName = StringUtil.escapeStr(row.bh+" "+(row.ywms||"")+" "+(row.zwms||""));
				var htmlContent = "";
				htmlContent +="<tr onclick='maintenanceItemList.showChildTR(this,\""+row.id+"\")' bgcolor='#f9f9f9' name='"+bh+"' id='"+row.id+"'>";
				htmlContent += "<td colspan='27' title='"+groupName+"'>";
				htmlContent += "&nbsp;&nbsp;&nbsp;&nbsp;<span><i rowid='"+row.id+"' class='fa fa-angle-down'></i></span>&nbsp;&nbsp;&nbsp;&nbsp;";
				htmlContent += "<span class='badge' style='background:#3598db;width:40px;' name='badgeCount'>1</span>";
				htmlContent += "<span style='margin-left:8px;'>"+groupName+"</span>";
				htmlContent += "</td>";
				htmlContent +="</tr>";
				
				return htmlContent;
			},
			// 修改标题数量
			modifyRowCount:function(childId, count,option){
				var parentId = $("#"+childId).attr("class");
				
				//撤销
				if("revoke" == option){ 
					parentId = childId;
				}
				
				//修改数量
				var $parentRow = $("#"+parentId);
				var $badge = $parentRow.find("[name='badgeCount']");
				$badge.text(parseInt($badge.text()) + count);
				
				//删除标题行
				if(parseInt($badge.text()) <= 0){
					$parentRow.remove(); 
				}
			},
			// 拼接行
			generateRow:function(row, parent){
				var this_ = this;
				var htmlContent = "";
				var wxfa = this_.getWxfa();
				var wxfazt = wxfa.zt;
				var wxfabb = wxfa.bb;
				if(row && parent){
					var bh = parent.zjh || parent.dlbh;
					var $parentRow = $("tr[name='"+bh+"']");
					var parentId = $parentRow.attr("id");
					var style = $parentRow.find("[rowid='"+parentId+"']").hasClass("fa fa-angle-down") ? "style='display:none;'" : "";
					htmlContent +="<tr name='detailRow' id='"+row.id+"' rwh='"+row.rwh+"' class='"+parentId+" "+(row.yxbs == 0?"delete-lineoragne":"")+"' "+style+" bgcolor='#fefefe' onclick='maintenanceItemList.showBottomDiv(event,this.id)'>";
					htmlContent +="<td class='text-center deleteline-none'>";
					if(this_.canEdit()){
						var typestr = "";
						// 判断打开维修项目或定检包页面
						if(row.wxxmlx != 4){
							typestr = "Project";
						}else{
							typestr = "Check";
						}
						if(parseFloat(row.bb) == parseFloat(wxfabb) && row.yxbs == 1){
							htmlContent +="<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:07' title='修改 Edit' onclick='open_win_modal.open"+typestr+"WinEdit(\""+row.id+"\")'></i>&nbsp;&nbsp;";
						}
						if(parseFloat(row.bb) == parseFloat(wxfabb)){
							if(row.fBbid){
								htmlContent +="<i class='fa fa-undo color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:09' title='撤销 Revoke' onclick='maintenanceItemList.revoke(\""+row.id+"\")'></i>&nbsp;&nbsp;";
							}else{
								htmlContent +="<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:09' title='删除 Delete' onclick='maintenanceItemList.revoke(\""+row.id+"\")'></i>&nbsp;&nbsp;";
							}
						}
						if(parseFloat(row.bb) < parseFloat(wxfabb)){
							htmlContent +="<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:08' title='改版 Modify' onclick='open_win_modal.open"+typestr+"WinRevision(\""+row.id+"\")'></i>&nbsp;&nbsp;";
						}
					}
					htmlContent +="</td>";
					htmlContent +="<td name='rwh'><a href='javascript:void(0);' onclick='maintenanceItemList.viewProject(\""+row.id+"\")'  name='content'>"+StringUtil.escapeStr(row.rwh)+"</a></td>";
					htmlContent +="<td style='text-align:left' class='deleteline-first' title='R"+row.bb+"'>R"+row.bb+this_.showRevisionMark(wxfa,row)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.ckh)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.rwms)+"</td>";
					htmlContent +="<td>"+this_.buildFjzch(row)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',row.wxxmlx))+"</td>";
					htmlContent +="<td colspan='4'>"+this_.buildMonitorItem((row.wxxmlx == 2 || row.wxxmlx == 3)?[]:row.projectMonitors)+"</td>";
					htmlContent +="<td name='gkbh'>"+StringUtil.escapeStr(row.gkbh)+"</td>";
					htmlContent +="<td>";
					htmlContent +='<span>'+StringUtil.escapeStr(row.ckwj)+'</span>';
					htmlContent +="</td>";
					htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
					if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) {
						htmlContent += '<i ckwjfjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					}
					htmlContent += "</td>";
					htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(17, row.gzlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
					htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(18, row.xmlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.xmlx)+"</td>";
					htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.isBj)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.qyStr)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.jjStr)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.fixChapter ? ((row.fixChapter.zjh || "")+"  "+ (row.fixChapter.ywms || "")) : "")+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.maintenanceClass.dlbh ? ((row.maintenanceClass.dlbh || "")+"  "+ (row.maintenanceClass.dlywms || "") + " " + (row.maintenanceClass.dlzwms || "")) : "")+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.fjzwStr)+"</td>";
					htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.ali)+"</td>";
					htmlContent +="<td>"+this_.buildWorkTimeContent(row)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.bz)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:"")+"</td>";
					htmlContent +="<td style='text-align:center' class='deleteline-last'>"+row.zdsj+"</td>";
					htmlContent +="<input type='hidden' name='wxxmlx' value='"+row.wxxmlx+"'>";
					htmlContent +="<input type='hidden' name='zt' value='"+row.zt+"'>";
					htmlContent +="<input type='hidden' name='yxbs' value='"+row.yxbs+"'>";
					htmlContent +="</tr>";
				}
				return htmlContent;
			},
			// 表格拼接
			appendContentHtml:function(list){
				var this_ = this;
				var htmlContent = '';
				var wxfa = this_.getWxfa();
				var wxfazt = wxfa.zt;
				var wxfabb = wxfa.bb;
				if(list.length > 0){
					$.each(list,function(index,row){
						var parent=row;
						var groupName = StringUtil.escapeStr(row.bh+" "+(row.ywmc||"")+" "+(row.zwmc||""));
						htmlContent +="<tr onclick='maintenanceItemList.showChildTR(this,\""+row.id+"\")' bgcolor='#f9f9f9' name='"+row.bh+"' id='"+row.id+"'>";
						htmlContent += "<td colspan='27' title='"+groupName+"'>";
						htmlContent += "&nbsp;&nbsp;&nbsp;&nbsp;<span><i rowid='"+row.id+"' class='fa fa-angle-down'></i></span>&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlContent += "<span class='badge' style='background:#3598db;width:40px;' name='badgeCount'>"+row.children.length+"</span>";
						htmlContent += "<span style='margin-left:8px;'>"+groupName+"</span>";
						htmlContent += "</td>";
						htmlContent +="</tr>";
						$.each(row.children,function(index,row){
							
							htmlContent +="<tr name='detailRow' id='"+row.id+"' rwh='"+row.rwh+"' class='"+parent.id+" "+(row.yxbs == 0?"delete-lineoragne":"")+"' bgcolor='#fefefe' style='display:none;text-decoration:' onclick='maintenanceItemList.showBottomDiv(event,this.id)'>";
							htmlContent +="<td class='text-center deleteline-none'>";
							if(this_.canEdit()){
								var typestr = "";
								// 判断打开维修项目或定检包页面
								if(row.wxxmlx != 4){
									typestr = "Project";
								}else{
									typestr = "Check";
								}
								if(parseFloat(row.bb) == parseFloat(wxfabb) && row.yxbs == 1){
									htmlContent +="<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:07' title='修改 Edit' onclick='open_win_modal.open"+typestr+"WinEdit(\""+row.id+"\")'></i>&nbsp;&nbsp;";
								}
								if(parseFloat(row.bb) == parseFloat(wxfabb)){
									if(row.fBbid){
										htmlContent +="<i class='fa fa-undo color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:09' title='撤销 Revoke' onclick='maintenanceItemList.revoke(\""+row.id+"\")'></i>&nbsp;&nbsp;";
									}else{
										htmlContent +="<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:09' title='删除 Delete' onclick='maintenanceItemList.revoke(\""+row.id+"\")'></i>&nbsp;&nbsp;";
									}
								}
								if(parseFloat(row.bb) < parseFloat(wxfabb)){
									htmlContent +="<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project2:maintenanceproject:main:08' title='改版 Modify' onclick='open_win_modal.open"+typestr+"WinRevision(\""+row.id+"\")'></i>&nbsp;&nbsp;";
								}
							}
							htmlContent +="</td>";
							htmlContent +="<td name='rwh'><a href='javascript:void(0);' onclick='maintenanceItemList.viewProject(\""+row.id+"\")'  name='content'>"+StringUtil.escapeStr(row.rwh)+"</a></td>";
							htmlContent +="<td style='text-align:left' class='deleteline-first' title='R"+row.bb+"'>R"+row.bb+this_.showRevisionMark(wxfa,row)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.ckh)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.rwms)+"</td>";
							htmlContent +="<td>"+this_.buildFjzch(row)+"</td>";
							/*htmlContent +="<td>"+ $(row.projectMaterialList).map(function(){
								return StringUtil.escapeStr(this.bjh)
							}).get().join(", ") +"</td>";*/
							htmlContent +="<td>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',row.wxxmlx))+"</td>";
							htmlContent +="<td colspan='4'>"+this_.buildMonitorItem((row.wxxmlx == 2 || row.wxxmlx == 3)?[]:row.projectMonitors)+"</td>";
							htmlContent +="<td name='gkbh'>"+StringUtil.escapeStr(row.gkbh)+"</td>";
							htmlContent +="<td>";
							htmlContent +='<span>'+StringUtil.escapeStr(row.ckwj)+'</span>';
							htmlContent +="</td>";
							htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
							if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) {
								htmlContent += '<i ckwjfjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
							}
							htmlContent += "</td>";
							htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(17, row.gzlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
							htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(18, row.xmlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.xmlx)+"</td>";
							htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.isBj)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.qyStr)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.jjStr)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.fixChapter ? ((row.fixChapter.zjh || "")+"  "+ (row.fixChapter.ywms || "")) : "")+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.maintenanceClass.dlbh ? ((row.maintenanceClass.dlbh || "")+"  "+ (row.maintenanceClass.dlywms || "") + " " + (row.maintenanceClass.dlzwms || "")) : "")+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.paramsMap.fjzwStr)+"</td>";
							htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.ali)+"</td>";
							htmlContent +="<td>"+this_.buildWorkTimeContent(row)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.bz)+"</td>";
							htmlContent +="<td>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:"")+"</td>";
							htmlContent +="<td style='text-align:center' class='deleteline-last'>"+row.zdsj+"</td>";
							htmlContent +="<input type='hidden' name='wxxmlx' value='"+row.wxxmlx+"'>";
							htmlContent +="<input type='hidden' name='zt' value='"+row.zt+"'>";
							htmlContent +="<input type='hidden' name='yxbs' value='"+row.yxbs+"'>";
							htmlContent +="</tr>";
						});
					});
				}else{
					htmlContent = "<tr class='no-data'><td class='text-center' colspan=\"27\">暂无数据 No data.</td></tr>";
				}
				$("#maintenance_item_list").html(htmlContent);
				TableUtil.addTitle("#maintenance_item_list tr td");
				this_.initWebuiPopover();
				refreshPermission();
			},
			// 显示修订标识
			showRevisionMark:function(wxfa,item){
				if(wxfa.bb == item.bb && (wxfa.zxbs == 1 || wxfa.zxbs == 2)){
					if(!item.fBbid){
						return "<span class='badge margin-left-10' title='新增' style='background-color:black;'>A</span>";
					}else if(item.yxbs == 0){
						return "<span class='badge margin-left-10' title='删除' style='background-color:black;'>D</span>";
					}else{
						return "<span class='badge margin-left-10' title='改版' style='background-color:black;'>R</span>";
					}
				}
				return "<span style='padding-left:13px;'></span>";
			},
			// 拼接工时
			buildWorkTimeContent:function(row){
				var result = "";
				if(row.jhgsRs!=null && row.jhgsRs!="" && row.jhgsXss!=null && row.jhgsXss!=""){
					var jhgsRs = row.jhgsRs || 0;
					var jhgsXss = row.jhgsXss || 0;
					var total = jhgsRs * jhgsXss;
					if(String(total).indexOf(".") >= 0){
						total = total.toFixed(2);
					}
					result = total + " 时";
				}
				return result;
			},
			// 拼接飞机适用性
			buildFjzch:function(row){
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
			},
			// 拼接区域和盖板
			buildCover:function(row, type){
				return $(row.coverPlateList).map(function(){
					if(this.lx == type){
						if(type == 2 && this.coverPlateInformation){
							return StringUtil.escapeStr(this.gbh + " " + (this.coverPlateInformation.gbmc || ""));
						}else{
							return StringUtil.escapeStr(this.gbh);
						}
					}
				}).get().join(", ");
			},
			//搜索条件显示与隐藏
			search:function(){
				if ($("#divSearch").css("display") == "none") {
					$("#divSearch").css("display", "block");
					$("#icon").removeClass("icon-caret-down");
					$("#icon").addClass("icon-caret-up");
				} else {
					$("#divSearch").css("display", "none");
					$("#icon").removeClass("icon-caret-up");
					$("#icon").addClass("icon-caret-down");
				}
			},
			showChildTR:function(obj,name){
				if($(obj).find("[rowid='"+name+"']").hasClass("fa fa-angle-down")){
					$('[rowid="'+name+'"]').attr("class","fa fa-angle-up cursor-pointer");
					$("."+name).css("display","table-row");
				}else{
					$('[rowid="'+name+'"]').attr("class","fa fa-angle-down cursor-pointer");
					$("."+name).css("display","none");
				}
				new Sticky({tableId:'maintenance_item_table'});
			},
			// 显示相关维修项目以及监控项目版本
			showBottomDiv:function(e,id){
				if(e.target.tagName == "I" || e.target.tagName == "A"){
					return;
				}
				if(lastBottomId !=''){
					if($("#"+lastBottomId).hasClass("bg_tr")){
						$("#"+lastBottomId).removeClass("bg_tr");
					}else{
						$("#"+lastBottomId).removeClass("deleteoragne-bg-tr");
					}
					
				}
				var isBottomShown = false;
				if($(".displayContent").is(":visible")){
					isBottomShown = true;
				}
				$(".displayContent").slideDown("500");
				App.resizeHeight();
				if(!isBottomShown){
					TableUtil.makeTargetRowVisible($("#"+id), $("#maintenance_item_table_top_div"));
				}
				if($("#"+id).hasClass("delete-lineoragne")){
					$("#"+id).addClass("deleteoragne-bg-tr");
					this.lastSelectStyle='deleteoragne-bg-tr';
				}else{
					$("#"+id).addClass("bg_tr");
					this.lastSelectStyle='bg_tr';
				}
				
				lastBottomId=id;
				this.lastSelectId=id;
				// 加载相关维修项目
				this.loadRelatedMaintenance();
				// 加载监控项目版本
				this.loadMonitorVersion();
				// 加载工卡
				this.loadWorkCard();
				new Sticky({tableId:'maintenance_item_table'});
			},
			// 加载相关维修项目
			loadRelatedMaintenance:function(sortType, sequence){
				 var this_ = this;
				 var wxfa = this_.getWxfa();
				 startWait();
				 AjaxUtil.ajax({
					type: "post",
			 		url:basePath+"/project2/maintenanceproject/queryRelatedMaintenanceProject",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			id : lastBottomId,
			 			scheme : {
			 				id : wxfa.id,
			 				zt : wxfa.zt
			 			},
			 			rwh : $("#"+lastBottomId).find("td[name='rwh']").text(),
			 			wxfabh : wxfa.wxfabh,
			 			dprtcode : this_.getDprtcode(),
			 			zt : $("#"+lastBottomId).find("input[name='zt']").val(),
			 			pagination : {
			 				sort:sortType,
			 				order:sequence
			 			}
			 		}),
			 		success:function(data){
			 			finishWait();
			 			var htmlContent = '';
						if(data.length > 0){
							$.each(data,function(index,row){
								htmlContent +="<tr class='"+(row.yxbs == 0?"delete-lineoragne":"")+"'>"
								htmlContent +="<td class='text-center deleteline-first'>"+(row.wxxmlx==4?"包":"项目")+"</td>"
								htmlContent +="<td><a href='javascript:void(0);' onclick='maintenanceItemList.viewProject(\""+row.id+"\")'  name='content'>"+StringUtil.escapeStr(row.rwh)+"</a></td>";
								htmlContent +="<td style='text-align:center'>R"+row.bb+"</td>"
								htmlContent +="<td>"+StringUtil.escapeStr(row.ckh)+"</td>"
								htmlContent +="<td>"+StringUtil.escapeStr(row.rwms)+"</td>"
								htmlContent +="<td>"+this_.buildFjzch(row)+"</td>"
								htmlContent +="<td>"+StringUtil.escapeStr(row.ckwj)+"</td>"
								htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(17, row.gzlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.gzlx)+"</td>"
								htmlContent +="<td title='"+DicAndEnumUtil.getDicItemDesc(18, row.xmlx,this_.getDprtcode())+"'>"+StringUtil.escapeStr(row.xmlx)+"</td>"
								htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.isBj)+"</td>"
								htmlContent +="<td>"+this_.buildCover(row, 1)+"</td>"
								htmlContent +="<td>"+this_.buildCover(row, 2)+"</td>"
								htmlContent +="<td>"+this_.buildCover(row, 3)+"</td>"
								htmlContent +="<td style='text-align:center;'>"+DicAndEnumUtil.getEnumName('whetherEnum', row.ali)+"</td>"
								htmlContent +="<td class='deleteline-last'>"+this_.buildWorkTimeContent(row)+"</td>"
								htmlContent +="</tr>"
							});
						}else{
							htmlContent = "<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>";
						}
						$("#maintenance_relate_list").html(htmlContent);
						TableUtil.addTitle("#maintenance_relate_list tr td");
		 				FormatUtil.removeNullOrUndefined();
			 		}
			     });
			},
			// 加载监控项目版本
			loadMonitorVersion:function(){
				var this_ = this;
				var wxxmlx = $("#"+lastBottomId).find("[name='wxxmlx']").val();
				var yxbs = $("#"+lastBottomId).find("[name='yxbs']").val();
				AjaxUtil.ajax({
					type: "post",
			 		url:basePath+"/project2/maintenanceproject/queryMonitorItemVersion",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			id : lastBottomId,
			 			wxxmlx : wxxmlx
			 		}),
			 		success:function(data){
			 			finishWait();
			 			var htmlContent = '';
			 			if(wxxmlx == 2 || wxxmlx == 3){
			 				$("#maintenance_version_bj").show();
			 				if(data.length > 0){
								$.each(data,function(index,row){
									$.each(row.projectMaterialList,function(j,mon){
										var bgcolor = ""
										if(row.scheme && row.scheme.zxbs == 2 && yxbs == 1){
											bgcolor = "effective-bg-color";
										}
										htmlContent +="<tr class='"+bgcolor+"'>"
										htmlContent +="<td style='text-align:center;'>"
										htmlContent +="<a href='javascript:void(0);' onclick='maintenanceItemList.viewProject(\""+row.id+"\")'>R"+row.bb+"</a>";
										htmlContent +="</td>";
										htmlContent +="<td style='text-align:left;'>"+mon.bjh+"</td>"
										htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(mon.projectMonitors, "jklbh"), true)+"</td>"
										htmlContent +="</tr>"
									});
								});
							}else{
								htmlContent = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							}
			 			}else{
			 				$("#maintenance_version_bj").hide();
			 				if(data.length > 0){
								$.each(data,function(index,row){
									var bgcolor = ""
									if(row.scheme && row.scheme.zxbs == 2 && yxbs == 1){
										bgcolor = "effective-bg-color";
									}
									htmlContent +="<tr class='"+bgcolor+"'>"
									htmlContent +="<td style='text-align:center;'>"
									htmlContent +="<a href='javascript:void(0);' onclick='maintenanceItemList.viewProject(\""+row.id+"\")'>R"+row.bb+"</a>";
									htmlContent +="</td>";
									htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(row.projectMonitors, "jklbh"), true)+"</td>"
									htmlContent +="</tr>"
								});
							}else{
								htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
							}
			 			}
						$("#maintenance_version_list").html(htmlContent);
						TableUtil.addTitle("#maintenance_version_list tr td");
						this_.resizeVersionWidth();
		 				FormatUtil.removeNullOrUndefined();
			 		}
			     });
			},
			// 加载工卡
			loadWorkCard:function(){
				var this_ = this;
				var wxfa = this_.getWxfa();
				AjaxUtil.ajax({
					type: "post",
			 		url:basePath+"/project2/workcard/versionlist",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			gkh : $("#"+lastBottomId).find("td[name='gkbh']").text(),
			 			jx : wxfa.jx,
			 			dprtcode : this_.getDprtcode()
			 		}),
			 		success:function(data){
			 			finishWait();
			 			var htmlContent = '';
		 				if(data.length > 0){
							$.each(data,function(index,row){
								var gzzstr = '';
								if(row.workGroup != null){
									gzzstr = StringUtil.escapeStr(row.workGroup.gzzdm) + " " + StringUtil.escapeStr(row.workGroup.gzzmc);
								}
								htmlContent +="<tr class='"+(row.zt == 8?"delete-lineoragne":"")+"'>"
								htmlContent +="<td style='text-align:left;' class='deleteline-first'>";
								htmlContent +="<a href='javascript:void(0);' onclick='maintenanceItemList.viewWorkCard(\""+row.id+"\")'>"+StringUtil.escapeStr(row.gkh)+"</a>";
								htmlContent +="</td>";
								htmlContent +="<td style='text-align:center;'>R";
								htmlContent +=row.bb;
								htmlContent +="</td>";
								htmlContent +="<td style='text-align:left;'>"+StringUtil.escapeStr(row.gzbt)+"</td>";
								htmlContent +="<td style='text-align:left;'>"+gzzstr+"</td>";
								htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;' class='deleteline-last'>";
								if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) 
									|| (row.gkfjid != null && row.gkfjid != "")
									|| (row.gznrfjid != null && row.gznrfjid != "")){
									htmlContent += '<i qtid="'+row.id+'" gkfjid="'+row.gkfjid+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
								}
								htmlContent += "</td>";
								htmlContent +="</tr>";
							});
						}else{
							htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
						}
						$("#maintenance_card_list").html(htmlContent);
						// 添加title
						TableUtil.addTitle("#maintenance_card_list tr td");
						this_.resizeVersionWidth();
		 				FormatUtil.removeNullOrUndefined();
		 				this_.initWorkCardWebuiPopover();
			 		}
			     });
			},
			// 初始化附件
			initWorkCardWebuiPopover : function(){
				var this_ = this;
				WebuiPopoverUtil.initWebuiPopover('attachment-view2','body',function(obj){
					return this_.getWorkCardHistoryAttachmentList(obj);
				});
			},
			// 获取历史附件列表
			getWorkCardHistoryAttachmentList : function(obj){
				var jsonData = [
			         {mainid : $(obj).attr('qtid'), type : '其它附件'}
			        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
			        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
			    ];
				return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
			},
			// 计算监控项目版本的宽度
			resizeVersionWidth:function(){
				$("#maintenance_version_list div[name='jkxm']").width($("#maintenance_version_jkxm").outerWidth());
				$("#maintenance_version_list div[name='sj']").width($("#maintenance_version_sj").outerWidth());
				$("#maintenance_version_list div[name='zq']").width($("#maintenance_version_zq").outerWidth());
				$("#maintenance_version_list div[name='rc']").width($("#maintenance_version_rc").outerWidth()-37);
			},
			buildMonitorItem:function(list, spread, name){
				var this_ = this;
				var htmlContent = '';
				name = name || 'monitor_rc_td';
				if(list!=null){
					$.each(list,function(i,row){
						 if (i == 1) {
							 htmlContent = htmlContent
										+ "　<i class='"+(spread?"icon-caret-up":"icon-caret-down")+" moreItem cursor-pointer' onclick=maintenanceItemList.vieworhidePgdh(this)></i><div class='"+name+"' "+(spread?"":"style='display:none;'")+">";
							}
						    var scz = (row.scz||"") + this_.convertUnit(row.jklbh, row.scz, row.dwScz);
						    var zqz = (row.zqz||"") + this_.convertUnit(row.jklbh, row.zqz, row.dwZqz);
						 	var rz = "-" + row.ydzQ + "/+" + row.ydzH + this_.convertUnit(row.jklbh, row.ydzQ, row.ydzQdw || row.ydzHdw);
						    htmlContent += "<div>";
						    htmlContent += "<div class='pull-left text-center' style='width:90px;min-height:10px;' name='jkxm'>"+MonitorUnitUtil.getMonitorName(row.jklbh)+"</div>"; 
							htmlContent += "<div class='pull-left text-center' style='width:90px;min-height:10px;' name='sj'>"+StringUtil.escapeStr(scz)+"</div>";
							htmlContent += "<div class='pull-left text-center' style='width:75px;min-height:10px;' name='zq'>"+StringUtil.escapeStr(zqz)+"</div>";
							htmlContent += "<div class='pull-left text-center' style='width:120px;min-height:10px;' name='rc'>"+StringUtil.escapeStr(rz)+"</div>";
							if (i != 0) {
								htmlContent += "<br>";

							}
							if (i != 0 && i == list.length - 1) {
								htmlContent += "</div>";
							}
					});
				}
				return htmlContent;
		   },
		   vieworhidePgdh : function(obj){
			    new Sticky({tableId:'maintenance_item_table'});
				var flag = $(obj).next().is(":hidden");
				if (flag) {
					$(obj).next().fadeIn(500);
					$(obj).removeClass("icon-caret-down");
					$(obj).addClass("icon-caret-up");
				} else {
					$(obj).next().hide();
					$(obj).removeClass("icon-caret-up");
					$(obj).addClass("icon-caret-down");
				}
			},
			vieworhideContentAll : function(type){
				new Sticky({tableId:'maintenance_item_table'});
				var obj = $("th[name='th_"+type+"']");
				var flag = obj.hasClass("downward");
				if(flag){
					obj.removeClass("downward").addClass("upward");
				}else{
					obj.removeClass("upward").addClass("downward");
				}
				if(flag){				
				 $("#"+type+"_table .moreItem").removeClass("icon-caret-down").addClass("icon-caret-up");
				 $("#"+type+"_table .moreItem").next().fadeIn(500);
				}else{
				 $("#"+type+"_table .moreItem").removeClass("icon-caret-up").addClass("icon-caret-down");
				 $("#"+type+"_table .moreItem").next().hide();
				}
			},
			// 监控类型单位转换
			convertUnit : function(jklbh, value, unit){
				if(value != null && value != "" && value != undefined){
					return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
				}else{
					return "";
				}
			},
			// 撤销
			revoke : function(id){
				var this_ = this;
				var wxfa = this_.getWxfa();
				var rwh = $("#"+id).find("td[name='rwh']").text();
				AlertUtil.showConfirmMessage("是否确认撤销任务号为"+rwh+"数据？",{callback:function(){
					startWait();
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenanceproject/revoke",
						type:"post",
						data:JSON.stringify({
							id : id,
							jx : wxfa.jx,
							scheme : {
				 				id : wxfa.id
				 			},
				 			dprtcode : this_.getDprtcode(),
						}),
						dataType:"json",
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait();
							AlertUtil.showMessage("撤销成功!");
							if (null == data) {
								data = {};
								data.id = $("#"+id).attr("class");
								data.removeRow ="true";
							}
							data.option = "revoke";
							this_.reloadRow({ id : id}, data);
						}
					});
				}});
			},
			// 字段排序
			orderBy : function(obj){
				var orderStyle = $("#" + obj + "_order").attr("class");
				$("th[id$=_order]").each(function() { //重置class
					$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
				});
				$("#" + obj + "_" + "order").removeClass("sorting");
				var orderType = "asc";
				if (orderStyle.indexOf("sorting_asc") >= 0) {
					$("#" + obj + "_" + "order").addClass("sorting_desc");
					orderType = "desc";
				} else {
					$("#" + obj + "_" + "order").addClass("sorting_asc");
					orderType = "asc";
				}
				this.loadRelatedMaintenance(obj, orderType);
			},
			// 展开/收缩所有行
			toggleAll : function(){
				var th = $("th[name='ope_td']");
				if(th.hasClass("downward")){
					this.expend();
				}else{
					this.collapse();
				}
				
				new Sticky({tableId:'maintenance_item_table'});
			},
			
			// 展开
			expend : function(){
				$("th[name='ope_td']").removeClass("downward").addClass("upward");
				$('#maintenance_item_list .fa.fa-angle-down').attr("class","fa fa-angle-up cursor-pointer");
				$('tr[name="detailRow"]').css("display","table-row");
			},
			
			// 收缩
			collapse : function(){
				$("th[name='ope_td']").removeClass("upward").addClass("downward");
				$('#maintenance_item_list .fa.fa-angle-up').attr("class","fa fa-angle-down cursor-pointer");
				$('tr[name="detailRow"]').css("display","none");
			},
			
			initWebuiPopover : function(){//初始化
				var this_ = this;
				WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
					return this_.getHistoryAttachmentList(obj);
				});
				$("#maintenance_item_table_top_div").scroll(function(){
					$('.attachment-view').webuiPopover('hide');
				});
			},
			getHistoryAttachmentList : function(obj){//获取历史附件列表
				var jsonData = [
			         {mainid : $(obj).attr('ckwjfjid'), type : '参考文件附件'}
			    ];
				return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
			},
			//导入维修项目清单：显示导入模态框
			showImportModal : function(){
				var this_ = this;
				var wxfa = this_.getWxfa(); //维修方案
				if(wxfa == null || wxfa.id == null){
					AlertUtil.showMessage("请选择维修项目！");
				}else{
					if(wxfa.zt != 1){
						AlertUtil.showMessage("保存状态的维修方案才可以进行导入！");
					}else{
						// 初始化导入
						importModal.init({
							importUrl:"/project2/maintenanceproject/excelImport?wxfaid="+wxfa.id,
							downloadUrl:"/common/templetDownfile/21", //维修项目清单
							callback: function(data){
								// 重新加载table数据
								this_.reload();
								$("#ImportModal").modal("hide");
							}
						});
						importModal.show();
					}
				}
			},
			
			//导出
			exportExcel : function(){
				window.open(basePath+"/project2/maintenanceproject/wxxm.xls?json=" + JSON.stringify(this.gatherSearchParam()));
			},
	}
	
	function customResizeHeight(bodyHeight, tableHeight){
		var panelHeadingHeight = $('.panel-heading').outerHeight();
		if(panelHeadingHeight<35){
			bodyHeight = bodyHeight + panelHeadingHeight - 35;
			tableHeight = tableHeight + panelHeadingHeight - 35;
			$(".page-content .panel-body:first").height(bodyHeight-3);
			var table_height=$(".table-height").outerHeight()||0;
			var bottom_height = tableHeight - table_height;
			$(".displayContent").css("height",bottom_height+"px");
        }
		
		bodyHeight = bodyHeight - 30;
		//隐藏的div
	    
	    var leftFirstBody=(bodyHeight)*0.4;
	    if(!$("#fjjx").is(":visible")){
	    	leftFirstBody = leftFirstBody - 4;
	    }
	    $("#maintenance_toggle_div").css("top",leftFirstBody+22+"px");
	    if($(".left_first_content").length == 0){
	    	leftFirstBody = -47;
	    }
	    $(".left_first_body").attr('style', 'height:' + leftFirstBody+ 'px !important;overflow-x: auto;');
	    var left_second_body=bodyHeight-leftFirstBody;
	    $(".left_second_body").attr('style', 'height:' + left_second_body+ 'px !important;overflow-x: auto;');
	    
	    $("#item_list_div_id").height(bodyHeight + 26);
        $("#item_list_div_id").css("overflow-y","auto");
	    
        //多列表高度设定
        if($(".displayContent").is(":hidden")){
        	$(".table-height").attr('style', 'height:' + tableHeight + 'px !important;overflow-x: auto;');
        }else{
        	$table = $(".table-height");
        	if($table.length > 0){
        		var cHeight = $table.attr("c-height");
        		var tempTableHight = tableHeight*0.45;
        		if(cHeight){
        			if(cHeight.indexOf("%") > 0){
        				cHeight = cHeight.replace("%","");
        				cHeight = cHeight/100;
        				tempTableHight = tableHeight * cHeight;
        			}else{
        				tempTableHight = cHeight;
        			}
        		}
        		
        		$table.attr('style', 'height:' + tempTableHight + 'px !important;overflow: auto;');
        		var table_height=$(".table-height").outerHeight()||0;
        		var bottom_hidden_tab = tableHeight - table_height -5;
        		
		    	$(".bottom_hidden_content").css("height",bottom_hidden_tab+"px");
		    	//隐藏div表头的高度
		    	var bottom_hidden_Ul=$(".bottom_hidden_Ul").outerHeight()||0
		    	//隐藏div内容的高度
		    	var bottom_hidden_table=bottom_hidden_tab-bottom_hidden_Ul-20;
		    	$(".bottom_hidden_table").css("height",bottom_hidden_table+"px");
        		
        	}
        }
        maintenanceItemList.resizeVersionWidth();
        open_win_maintenance_project_modal.resizeVersionWidth();
        // 浮动日志div高度
        $("#floatDiv").height($(".page-content:first>div").height()-13);
        try{
        	$("#logTableDiv").height($("#log_pagination").offset().top-$("#logTableDiv").offset().top - 5);
        }catch(e){}
	}

var lastBottomId='';
