workorder_detail = {
		id:"workorder_detail",
		data:[],
		param: {
			workpackageId:null,
			gbbh:null,
			fjzch:null,
			zt:0,
			dprtcode:userJgdm,
			operationShow:true,
			searchDiv:false,
		},
		init:function(param){
			if(param){
				$.extend(this.param, param);
			}
			$('#workorder_detail_table_div .attachment-view').webuiPopover('hide');
			$("#"+this.id+"_list").empty();
			if(this.param.operationShow){
				$("#"+this.id+"_operation").show();
				$("#"+this.id+"_checkAll").hide();
			}else{
				$("#"+this.id+"_operation").hide();
				$("#"+this.id+"_checkAll").show();
			}
			if(this.param.searchDiv){
				$("#"+this.id+"_searchDiv").show();
				$("#"+this.id+"_operationDiv").hide();
			}else{
				$("#"+this.id+"_searchDiv").hide();
				$("#"+this.id+"_operationDiv").show();
			}
			this.goPage();			
		},
		goPage:function(){
			this.AjaxGetDatasWithSearch();
		},
		AjaxGetDatasWithSearch:function(){
			var _this=this;
			var param={};
			var paramsMap={};
			if(!_this.param.operationShow){
				var keyword=$("#"+_this.id+"_keyword_search").val();
				var lx=[];
				$("input[name='"+_this.id+"_cy']:checked").each(function() {
					lx.push(this.value);
				});
				paramsMap.keyword=keyword;
				paramsMap.lx=lx;
				if(paramsMap.lx.length==0){
					$("#"+_this.id+"_list").html("<tr ><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
					return false;
				}
			}
			paramsMap.gbid=_this.param.workpackageId;
			paramsMap.dprtcode=_this.param.dprtcode;
			param.paramsMap=paramsMap;
			 AjaxUtil.ajax({
			 		url:basePath+"/produce/workpackage/getWorpackageDetailList",
			 		type: "post",
					dataType:"json",
					async:false,
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify(param),
			 		success:function(data){
			 			if(data !=null ){
			 				var workorderList=data.workorderList
			 				var hcList=data.hcList;
			 				_this.data=workorderList;
			 				if(workorderList!=null&& workorderList.length>0){
			 					_this.appendContentHtml(workorderList);
			 					signByKeyword($("#workorder_detail_keyword_search").val(), [2, 3, 11,12 ],"#workorder_detail_list tr td");
			 				}else{
			 					$("#"+_this.id+"_list").html("<tr ><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
			 				}			 				
//		 					hc_list_view_modal.init({
//		 						obj:hcList,
//		 					})			 			
			 		    }
			 		}
			     }); 
		},
		appendContentHtml:function(list){
			var _this=this;
			var htmlContent = '';
			$.each(list,function(index,row){
				if(_this.param.operationShow){
					htmlContent += "<tr  id='"+row.GDID+"' >";
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
					if(row.ZT != 9 && row.ZT != 10){ //9、10以外状态,可编辑
						htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:02' onClick=workorder_detail.openWinEdit('"+ row.GDID+"','"+row.GDLX + "') title='修改 Edit'></i>&nbsp;&nbsp;";
					}
					if(row.ZT == 1){
						htmlContent += "<i class='iconnew-remove color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:01' onclick='"+_this.id+".workorderRemove(\""+ row.GDID+ "\");'   title='移除 Remove '></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:03' onclick='"+_this.id+".workorderInvalid(\""+ row.GDID+ "\");'   title='删除 Delete '></i>&nbsp;&nbsp;";
					}
					if(row.ZT == 2){
						htmlContent += "<i class='iconnew-remove color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:01' onclick='"+_this.id+".workorderRemove(\""+ row.GDID+ "\");'   title='移除 Remove '></i>&nbsp;&nbsp;";
					}
					if(row.ZT== 7){
						htmlContent += "<i class='iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:04' onclick='"+_this.id+".workorderFeedback(\""+ row.GDID+ "\");'  title='完工反馈 Feedback'></i>&nbsp;&nbsp;";
					}
					if(row.ZT == 7 && row.XMBLBS==0){
						htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:05' onclick='"+_this.id+".workorderClose(\""+ row.GDID+ "\");'   title='完工关闭 Close '></i>&nbsp;&nbsp;";
					}
					if(row.ZT==2||row.ZT==7){
						htmlContent += "<i class='iconnew-end color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:06'  onclick='"+_this.id+".workorderEnd(\""+ row.GDID+ "\");' title='指定结束 End '></i>&nbsp;&nbsp;";
					}
					if(row.GDLX == "3"){
						if(row.ZT==2 ){
							htmlContent += "<i class='iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:04' onclick='"+_this.id+".workorderFeedback(\""+ row.GDID+ "\");'  title='完工反馈 Feedback'></i>&nbsp;&nbsp;";
							if(row.XMBLBS==0){
								htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:05' onclick='"+_this.id+".workorderClose(\""+ row.GDID+ "\");'   title='完工关闭 Close '></i>&nbsp;&nbsp;";
							}
						}
					}
					if(row.ZT == 7 && (row.FJZCH !="" && row.FJZCH != null && row.FJZCH !="-" ) && row.XMBLBS==0){ //状态为生效,可进行,工作项目保留
						htmlContent += "<i class='iconnew-projectKeep color-blue cursor-pointer checkPermission' permissioncode='produce:reservation:item:main:01' onClick=workorder_detail.openWinView('"+ row.GDID + "',8)  title='工作项目保留'></i>&nbsp;&nbsp;";
					}
					if(row.ZT == 9 && row.GDLX != 9){ //状态为指定结束,可进行,修订
						htmlContent += "<i class='fa fa-thumb-tack color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:07' onClick=workorder_detail.openWinRevision9('"+ row.GDID+ "')  title='修订'></i>&nbsp;&nbsp;";
					}
					if(row.ZT == 10 && row.GDLX != 9){ //状态为指定结束,可进行,修订
						htmlContent += "<i class='fa fa-thumb-tack color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:07' onClick=workorder_detail.openWinRevision10('"+ row.GDID + "')  title='修订'></i>&nbsp;&nbsp;";
					}
					htmlContent += "</td>";
				}else{
					htmlContent += "<tr  bgcolor=\"#f9f9f9\" onclick='"+_this.id+".rowonclick(event);' >";
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 			
					htmlContent += "<input type='checkbox' name='"+_this.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+_this.id+"_list')\" >"; 					
					htmlContent += "</td>";	
				}				
				/*来源任务信息*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.LYBH)+"'><a href=javascript:workorder_detail.viewLyrw('"+ row.LYID+"','"+StringUtil.escapeStr(row.JKSJID)+"','"+row.GDLX + "') >"+StringUtil.escapeStr(row.LYBH)+"</a></td>";
				/*工单标题*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GDBT)+"'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
				/*工单状态*/
				if(row.ZT==9){
					htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.ZT))+"'><a href=\"javascript:workorder_detail.toShowZdjs('"+ row.GDID + "')\" > "+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.ZT))+"</a></td>";
				}else if(row.ZT==10){
					htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.ZT))+"'><a href=\"javascript:workorder_detail.toShowWggb('"+ row.GDID + "')\" > "+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.ZT))+"</a></td>";
				}else{
					htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.ZT))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.ZT))+"</td>";
				}	
				/*工卡附件*/
				htmlContent += "<td  style='text-align:center;vertical-align:middle;'>";		
				if((row.GKGZNRATTACHCOUNT!=null&& row.GKGZNRATTACHCOUNT > 0) ||(row.GKATTACHENT!=null && row.GKATTACHENT>0)||(row.GKFJATTACHENT!=null && row.GKFJATTACHENT>0) ){
					htmlContent += "<i qtid='"+row.GKID+"' gkfjid='"+row.GKFJID+"' gznrfjid='"+row.GKGZNRFJID+"'  class='attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer '  style='font-size:15px;margin-right:3px;'></i>";
				}
				/*类型*/
				if(row.GDZLX != null && row.GDZLX != ''){
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', row.GDZLX))+"' >"+formatUndefine(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', row.GDZLX))+"</td>";
				} else{
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', row.GDLX))+"' >"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', row.GDLX))+"</td>";
				}
				/*工单编号*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GDBH)+"'><a href=\"javascript:workorder_detail.viewWorkorder('"
				+ row.GDID + "')\">"+StringUtil.escapeStr(row.GDBH)+"</a></td>";
				/*参考号*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.CKH)+"'>"+StringUtil.escapeStr(row.CKH)+"</td>";
				/*工作类别*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GZLB)+"'>"+StringUtil.escapeStr(row.GZLB)+"</td>";
				/*反馈状态*/
				if(row.WGBS==0){
					htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.WGBS))+"' >"+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.WGBS))+ "</td>";
				}else{
					htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.WGBS))+"' ><a href=\"javascript:workorder_detail.toShowFeedback('"+ row.GDID + "')\" > "+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.WGBS))+ "</a></td>";
				}								
				/*部件号*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
				/*序列号*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.XLH)+"'>"+StringUtil.escapeStr(row.XLH)+"</td>";
				/*完成时限*/
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				var jh =row.JHSJSJ;
				var str = jh==null?"":jh.split(",");
				for (var i = 0; i < str.length; i++) {
					var m = '';
					var data=str[i].split("#_#");
					var lx = data[0];
					var value=data[1];
						if(MonitorUnitUtil.isTime(lx)){
							m = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit
						}else if(MonitorUnitUtil.isCal(lx)){
							m = value;
						}else if(MonitorUnitUtil.isLoop(lx)){
							m = value+MonitorUnitUtil.unitObj[lx].unit;
						}
						if(i==str.length-1){
							htmlContent += "<span title='"+m+"'>"+m+"</span>";	
						}else{
							htmlContent += "<span title='"+m+"'>"+m+"</span><br/>";	
						}															
				}
				htmlContent +="</td>";
				/*计划工时*/
				htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.JHGS)+"'>"+StringUtil.escapeStr(row.JHGS)+"</td>";
				/*实际工时*/
				htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.SJGS)+"'>"+StringUtil.escapeStr(row.SJGS)+"</td>";
				/*工卡编号*/
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GKBH)+(row.GKBB?(" R"+StringUtil.escapeStr(row.GKBB)):"")+"'><a href=javascript:workorder_detail.viewLyrw('"+ row.GKID+"','"+StringUtil.escapeStr(row.JKSJID)+"','"+8+ "') >"+StringUtil.escapeStr(row.GKBH)+(row.GKBB?(" R"+StringUtil.escapeStr(row.GKBB)):"")+"</a></td>";	
				htmlContent += "</td>";	
				/*工单附件*/
				htmlContent += "<td  style='text-align:center;vertical-align:middle;'>";		
				if(row.ATTACHCOUNT!=null&& row.ATTACHCOUNT>0){
					htmlContent += "<i qtid='"+row.GDID+"'  class='attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer '  style='font-size:15px;margin-right:3px;'></i>";
				}				
				if(_this.param.operationShow&& row.ZT!==9 && row.ZT!=10){
					htmlContent += "<i class='icon-upload-alt color-blue cursor-pointer ' title='上传 Upload' style='font-size:18px;margin-right:3px' onclick='"+_this.id+".addFile(\""+ row.GDID+ "\");'  ></i>";					
				}	
				htmlContent += "</td>";				
				htmlContent += "</tr>";
		 })	
		 $("#"+_this.id+"_list").empty();
		 $("#"+_this.id+"_list").html(htmlContent);
		 _this.initWebuiPopover();
		 refreshPermission(); //刷新权限
		},
		initWebuiPopover:function(){//初始化
			var this_=this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#workorder_detail_table_div").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		 getHistoryAttachmentList:function(obj){//获取历史附件列表
			var jsonData = [
			   	         {mainid : $(obj).attr('qtid'), type : '附件'},
			   	         {mainid : $(obj).attr('gkfjid'), type : '工卡附件'},
			   	         {mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'},
			   	    ];
			 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		getData:function(){
		
		},
		rowonclick: function(e){		
			SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");	
			this.getTotal();
		},
		addFile:function(id){
			var this_=this;
			open_win_attachments_list_edit.show({
				djid:id,
		 		colOptionhead : true,
				fileHead : true,
				fileType:"workorder",
				callback: function(attachments){//回调函数
					var param = {
							id : id,
						};
					param.attachments = attachments;
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/produce/workorder/doAttachment",
						contentType:"application/json;charset=utf-8",
						type:"post",
						data:JSON.stringify(param),
						dataType:"json",
						success:function(data){
							AlertUtil.showMessage('保存成功!',this_.goPage());
							$("#open_win_attachments_list_edit").modal("hide");
						}
					});
				}
			});//显示附件
		},
		addWorkorder:function(){
			var this_=this;
			getDataById(this.param.workpackageId,"edit",function(obj){
				if(obj.zt != 7 && obj.zt != 2 ){
					AlertUtil.showErrorMessage("只有在提交或下发状态的工包才能新增工单!");
					return;
				}else{
					window.open(basePath+"/produce/workorder/main?workpackageId="+encodeURIComponent(this_.param.workpackageId)+"&gbbh="+encodeURIComponent(this_.param.gbbh)+"&fjzch="+encodeURIComponent(this_.param.fjzch));
				}				
			})		
		},
		addNRC:function(){
			var this_=this;
			getDataById(this.param.workpackageId,"edit",function(obj){
				if(obj.zt != 7 && obj.zt != 2 ){
					AlertUtil.showErrorMessage("只有在提交或下发状态的工包才能新增工单!");
					return;
				}  else{
					window.open(basePath+"/produce/nrc135/main?workpackageId="+this_.param.workpackageId+"&gbbh="+encodeURIComponent(this_.param.gbbh)+"&fjzch="+encodeURIComponent(this_.param.fjzch));
				}				
			})		
		},
		chooseWorkorder:function(){
			var this_=this;
			getDataById(this.param.workpackageId,"edit",function(obj){
				if(obj.zt != 7 && obj.zt != 2 ){
					AlertUtil.showErrorMessage("只有在提交或下发状态的工包才能选择工单!");
					return;
				}
			open_win_workorder_modal.show({
				workpackageId:this_.param.workpackageId,
				fjzch:this_.param.fjzch,
				gdlx : [3,4,5],
				dprtcode:this_.param.dprtcode,
				callback:function(data){					
					this_.getIdList(data);
				}
			});
			})	
		},
		getIdList:function(data){
			var idList=[];
			$.each(data,function(index,row){
				idList.push(row.id);
			})
			var param = {};
			param.gbid=this.param.workpackageId;
			var paramsMap={};
			paramsMap.idList=idList;
			param.paramsMap=paramsMap;
			this.sendRequest(param);
		},
		sendRequest:function(param){
			var this_=this;
			AjaxUtil.ajax({
				url : basePath + "/produce/workorder/updateGbid",
				type : "post",
				data : JSON.stringify(param),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					pageParam=encodePageParam();
					$("#open_win_workorder_modal").modal("hide");
					AlertUtil.showMessage('添加成功!',this_.goPage() );
					refreshPermission();
				}
			});
		},
		searchWorkOder:function(){
			$("#"+this.id+"_jhgshj").css("display","none");
			this.AjaxGetDatasWithSearch();
		},workorderFeedback:function(id){
			var this_=this;
			workorder135_main.openWinFeedback(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});		
		},workorderRemove:function(id){
			var this_=this;
			workorder135_main.selectById(id,function(obj){
				if(!(obj.zt == 1 || obj.zt == 2)){
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');				
					return;
				}
				AlertUtil.showConfirmMessage("确定要移除吗？", {
					callback : function() {
					AjaxUtil.ajax({
						url : basePath + "/produce/workorder/update4RemoveWO",
						type : "post",
						data : {id:id},
						async : false,
						dataType:"json",
						success : function(data) {
							pageParam=encodePageParam();
							AlertUtil.showMessage('移除成功!',this_.goPage() );
							refreshPermission();
						}
					});
					}
				});
			});		
		},workorderClose:function(id){
			var this_=this;
			workorder135_main.openWinWGClose(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});		
		},workorderEnd:function(id){
			var this_=this;
			workorder135_main.openWinZDClose(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});				
		},workorderInvalid:function(id){
			var this_=this;
			workorder135_main.openWinDel(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});		
		},
		openWinRevision9:function(id){
			var this_=this;
			workorder135_main.openWinRevision9(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});	
		},
		openWinRevision10:function(id){
			var this_=this;
			workorder135_main.openWinRevision10(id,false,function(obj){
				if(obj){
					this_.goPage();
					refreshPermission();
				}
			});
		},
		openWinEdit:function(id,lx){
			var this_=this;
			if(lx == 3){
				window.open(basePath+"/produce/nrc135/main?gdid="+encodeURIComponent(id));
			}else{
				window.open(basePath+"/produce/workorder/main?gdid="+encodeURIComponent(id));
			}
		},
		viewWorkorder:function(id){
			window.open( basePath+"/produce/workorder/woView?gdid="+id);
		},//全选
		performSelectAll:function(){
			var this_=this;
			$(":checkbox[name='"+this_.id+"_list_radio']").attr("checked", true);
			this_.getTotal();
		}
		//取消全选
		,performSelectClear:function(){
			$(":checkbox[name='"+this.id+"_list_radio']").attr("checked", false);
			this.getTotal();
		}
		,getTotal:function(){
			var this_=this;
			var jhgshj=0;
			var total=0;
			$("#"+this_.id+"_list").find("tr input:checked").each(function(){
				var index = $(this).attr("value");
				total ++;
				jhgshj += (this_.data[index]).JHGS;
			});
			$("#"+this_.id+"_jhgshj").css("display","block");
			$("#"+this_.id+"_jhgshj").html("已选择："+total+",计划工时合计:"+jhgshj+"小时");
		},
		//查看工单完工反馈
		toShowFeedback:function(id){
			var this_=this;
			workorder135_main.openWin4ViewFeedback(id);
		},
		openWinView:function(id,type){
			workorder135_main.openWinView(id,type);
		},
		//查看工单指定结束
		toShowZdjs:function(id){
			var this_=this;
			workorder135_main.openWin4ViewZDClose(id);
		},
		//查看工单完工关闭
		toShowWggb:function(id){
			var this_=this;
			workorder135_main.openWin4ViewWGClose(id);
		},
		viewLyrw:function(id, jksjid, lx){
			if(lx == 1){
				window.open(basePath+"/project2/maintenanceproject/view?id="+id);
			}
			else if(lx == 2){
				window.open(basePath+"/project2/eo/view?id="+id);
			}
			else if(lx == 3 || lx == 4 || (lx == 5 && !jksjid)){
				window.open(basePath+"/produce/workorder/woView?gdid="+id);
			}else if(lx == 8){
				window.open(basePath+"/project2/workcard/view/"+id);
			}else if(lx == 5 && jksjid){
				window.open(basePath+"/project2/production/view?id="+id);
			}
		},
		exportExcel:function(){
			var _this = this;
			var param={};
			var paramsMap={};
			if(!_this.param.operationShow){
				var keyword=$("#"+_this.id+"_keyword_search").val();
				var lx=[];
				$("input[name='"+_this.id+"_cy']:checked").each(function() {
					lx.push(this.value);
				});
				paramsMap.keyword=keyword;
				paramsMap.lx=lx;
				if(paramsMap.lx.length==0){
					$("#"+_this.id+"_list").html("<tr ><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
					return false;
				}
			}
			paramsMap.gbid=_this.param.workpackageId;
			paramsMap.dprtcode=_this.param.dprtcode;
			param.paramsMap=paramsMap;
			window.open(basePath+"/produce/workpackage/workpackageDetail.xls?paramjson="+JSON.stringify(param));
		}
};