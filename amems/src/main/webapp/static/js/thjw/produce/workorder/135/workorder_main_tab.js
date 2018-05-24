/**
 * 135工单,tab信息
 */
Workorder135MainTab = {
		woInfo : {}, //工单信息
		gdbh :null,//工单编号
		/**加载Tab信息*/
		loadTabInfo:function(gdid){
			var this_ = this;

			/*根据工单ID,查找工单对象*/
			woInfo = this_.selectById(gdid);

			/*航材工具Tab信息*/
			this_.loadHCInfo();

			/*完工反馈Tab信息*/
			this_.loadFeedbackInfo();

			/*拆换件记录Tab信息*/
			this_.loadChjInfo();

			/*执行历史Tab信息*/
			this_.loadGDZxhistoryInfo();
			
		},
		/**根据GDID查找数据*/
		selectById : function(id){//通过id获取数据 
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/produce/workorder/selectWOById",
				type:"post",
				data:{gdid:id},
				dataType:"json",
				success:function(data){
					if(data != null){
						this_.woInfo = data;
						this_.gdbh=data.gdbh;
					}else{
						this_.woInfo = {};
					}
				}
			});
		},
		/**完工反馈数据*/
		loadFeedbackInfo : function(){
			var this_ = this;
				if(this_.woInfo.paramsMap.sjwwbs==1){
					$("#wo135feedbacktab_sjZxdw").val("外委   "+StringUtil.escapeStr(this_.woInfo.paramsMap.sjzxdw));
				}else if(this_.woInfo.paramsMap.sjwwbs==0){
					$("#wo135feedbacktab_sjZxdw").val("内部   "+StringUtil.escapeStr(this_.woInfo.paramsMap.sjzxdw));
				}else{
					$("#wo135feedbacktab_sjZxdw").val("");
				}
				$("#wo135feedbacktab_sjJsrq").val(StringUtil.escapeStr(this_.woInfo.sjJsrq).substring(0,16)); //实际完成日期
				//实际工作者
				var gzzList = '';
				$.each(this_.woInfo.workers || [], function(i, row){
					gzzList += formatUndefine(row.gzz) + ",";
				});
				if(gzzList != ''){
					gzzList = gzzList.substring(0,gzzList.length - 1);
				}
				$("#wo135feedbacktab_sjgzz").val(gzzList);
				
				$("#wo135feedbacktab_sjjcz").val(this_.woInfo.sjJcz);//实际检查者
				$("#wo135feedbacktab_sjgs").val(this_.woInfo.sjGs);//实际工时
				//核算工时
				$("#wo135feedbacktab_hsgs").removeAttr("checked");
				if(this_.woInfo.hsgs == 1){
					$("#wo135feedbacktab_hsgs").attr("checked",'true');//选中
				}
				$("#wo135feedbacktab_sjzd").val(this_.woInfo.sjZd);//工作站点
				$("#wo135feedbacktab_sjksrq").val(StringUtil.escapeStr(this_.woInfo.sjKsrq).substring(0, 10));//实际开始日期
				$("#wo135feedbacktab_gzxx").val(this_.woInfo.gzxx);//故障信息
				$("#wo135feedbacktab_clcs").val(this_.woInfo.clcs);//处理措施
				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('wo135feedbacktab_attachments_list_edit');
				attachmentsObj.show({
					djid:this_.woInfo.id,
					fileHead : false,
					colOptionhead : false,
					attachHead:false,
					fileType:""
				});
		},
		/**拆换件数据*/
		loadChjInfo : function(){
			var this_=this;
			var list=this_.woInfo.woIORecordList;
			if(list.length>0){
				var htmlContent="";
				$.each(list,function(index,data){
					if(data.id!=null &&data.id!=''){
						htmlContent +="<tr>";				
						htmlContent +="<td>"+(index+1)+"</td>";									
						htmlContent += "<td name='gdbh' title='"+this_.gdbh+"' style='text-align:left;vertical-align:middle;'><a href=\"javascript:Workorder135MainTab.viewWO('"+ this_.woInfo.id + "')\" >"+this_.gdbh+"</a></td>";
						htmlContent += "<td name='zsbjh' title='"+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+"</td>";
						htmlContent += "<td name='zsxlh' title='"+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+"</a></td>";
						htmlContent += "<td name='zsmc' title='"+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+"</td>";
						htmlContent += '<td name="zssj" title="'+(data.zsSj||"").substr(0, 16)+'" style="text-align:center;vertical-align:middle;">'+(data.zsSj||"").substr(0, 16)+'</td>';	   
						htmlContent += "<td name='cxbjh' title='"+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+"</td>";
					    htmlContent += "<td name='cxxlh' title='"+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+"</a></td>";
					    htmlContent += "<td name='cxmc' title='"+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+"</td>";
					   
					    htmlContent += '<td name="cxsj" title="'+(data.cxSj||"").substr(0, 16)+'" style="text-align:center;vertical-align:middle;">'+(data.cxSj||"").substr(0, 16)+'</td>';
					    htmlContent += "<td name='czyy' title='"+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+"</td>";	      
					   	htmlContent += "</tr>";
					}
					
				});
				if(htmlContent==""){
					$("#workorder135chjtab_list").html("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
				}else{
					$("#workorder135chjtab_list").empty();
					$("#workorder135chjtab_list").html(htmlContent);					
				}
			}else{
				$("#workorder135chjtab_list").html("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			
		},
		/**航材工具数据*/
		loadHCInfo : function(){
			var this_ = this;
			var param = {};
			param.id = this_.woInfo.id;
			param.dprtcode = this_.woInfo.dprtcode;
			AjaxUtil.ajax({
				url:basePath+"/produce/workorder/getGDHCToolDetail",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					if(data.hcList.length>0){
						this_.appendHCContentHtml(data);
					}else{
						$("#workorder135tabmataerialtool_list").empty();
						$("#workorder135tabmataerialtool_list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
					}
				}
			});
		},
		appendHCContentHtml:function(list){
			var htmlContent="";			
			var this_ = this;
			if(list!=null){
				$.each(list,function(index,hcList){
					$.each(hcList,function(j, row){
						var isWarning = (row.IS_WARNING == 1?"是":"否");
						var warningStyle = (row.IS_WARNING == 1?"color: red":"");
						var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', row.HCLX);
						if(row.HCLX == 1 && row.HCLX_EJ != null && row.HCLX_EJ != '' && DicAndEnumUtil.getEnumName('materialSecondTypeEnum', row.HCLX_EJ) != ''){
							hclx = DicAndEnumUtil.getEnumName('materialSecondTypeEnum', row.HCLX_EJ);
						}
						htmlContent +="<tr>";
						htmlContent += "<td title='"+hclx+"' style='text-align:left;vertical-align:middle;'>"+hclx+"</td>"; 
						htmlContent += "<td title='"+StringUtil.escapeStr(row.JH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.XINGH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.XINGH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+"" +StringUtil.escapeStr(row.ZWMS) +"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.YWMS)+"" +StringUtil.escapeStr(row.ZWMS)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.JHLY)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JHLY)+"</td>";	   
						htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.XQSL)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.XQSL)+"</td>";					
						htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.KCSL)+"' style='text-align:left;vertical-align:middle;'>";
						htmlContent += "<a style='"+warningStyle+"' href=javascript:Workorder135MainTab.openStorageDetailWin('"+StringUtil.escapeStr(row.JH)+"','"+row.DPRTCODE+"')>"+StringUtil.escapeStr(row.KCSL)+"</a>";
						htmlContent += "</td>"
						htmlContent += "<td title='"+StringUtil.escapeStr(row.JLDW)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JLDW)+"</a></td>";
						htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('whetherEnum',row.IS_WARNING))+"' style='text-align:left;vertical-align:middle;'>"+formatUndefine(DicAndEnumUtil.getEnumName('whetherEnum',row.IS_WARNING))+"</td>";				   				 
						htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
						var tdjxx =row.TDJXX;
						var str = tdjxx==null?"":tdjxx.split(",");
						for (var i = 0; i < str.length; i++) {
							var dataArr = str[i].split("#_#")
							var bjh = dataArr[0];
							var sl = dataArr[1]?dataArr[1]:0;
							var ms = dataArr[3];
							var tdjtTitle = StringUtil.escapeStr(bjh) + " : " + sl;
							var tdj = "<a bjh='"+StringUtil.escapeStr(row.JH)+"' dprtcode='"+row.DPRTCODE+"' tdjh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick=Workorder135MainTab.openSubstitutionWin(this)>"+StringUtil.escapeStr(bjh)+"</a>";
							tdj += " : ";
							tdj += "<a jh='"+StringUtil.escapeStr(bjh)+"' href=javascript:Workorder135MainTab.openStorageDetailWin('"+StringUtil.escapeStr(bjh)+"','"+row.DPRTCODE+"')>"+sl+"</a>";
							if (typeof(ms) != "undefined" && $.trim(ms) != "") {
								tdjtTitle = tdjtTitle  + "," + StringUtil.escapeStr(ms);
								tdj += " , " + StringUtil.escapeStr(ms);
							}
							if(i==str.length-1){
								htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>";
							}else{
								htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>"+"<br>";	
							}							
						}				
						htmlContent += "</td>";  
						htmlContent += "</tr>";
					});
				});
			}
			$("#workorder135tabmataerialtool_list").empty();
			$("#workorder135tabmataerialtool_list").append(htmlContent);
		},
		/**执行历史数据*/
		loadGDZxhistoryInfo : function(){
			var this_ = this;
			var param = {};
			var fjzch = this_.woInfo.fjzch; //飞机注册号
			var jksjid = this_.woInfo.jksjid||""; //监控数据ID
			var dprtcode = this_.woInfo.dprtcode; //组织机构
			var gdid = this_.woInfo.id||""; //工单id
			var this_ = this;
			executionHistory.init({
				zt : 10,
				jksjid : jksjid,
				fjzch : fjzch,
				dprtcode : dprtcode,
				gdid :gdid
			});
			
			
			/*AjaxUtil.ajax({
				url:basePath+"/produce/workorder/getGDZxhistoryInfo",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					if(data.exeList.length>0){
						this_.appendZXhistoryContentHtml(data);
					}else{
						$("#workorder135tabexehistory_list").empty();
						$("#workorder135tabexehistory_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
					}
				}
			});*/
		},
		appendZXhistoryContentHtml:function(list){
			var htmlContent="";			
			var this_ = this;
			if(list!=null){
				$.each(list,function(index,exeList){
					$.each(exeList,function(j, row){
						htmlContent +="<tr>";
						htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(j+1)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GDBH)+"' style='text-align:left;vertical-align:middle;'>"+"<a href=\"javascript:Workorder135MainTab.viewWO('"+ row.GDID + "')\" >"+StringUtil.escapeStr(row.GDBH)+"</a></td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GDBT)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GDBT)+"</td>";				
						htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
						if((this_.woInfo.paramsMap.woAttachCount != null && this_.woInfo.paramsMap.woAttachCount > 0) || (this_.woInfo.gznrfjid != null && this_.woInfo.gznrfjid != "")){
							htmlContent += '<i qtid="'+this_.woInfo.id+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						}
						htmlContent += "</td>";
						var fxjlbxx = row.FXJLBXX;
						var fxjlbhtml = '';
						var jlbymhtml='';
						var fxjlbStr = fxjlbxx==null?"":fxjlbxx.split(",");
						for (var i = 0; i < fxjlbStr.length; i++) {						
							var data=fxjlbStr[i].split("#_#");
							var id = data[0];
							var fxjlbh = data[1];
							var jlbym = data[2];
								if(i==fxjlbStr.length-1){
									fxjlbhtml += "<span title='"+fxjlbh+"'>"+"<a href='javascript:Workorder135MainTab.viewFLB('"+id+"')'>"+fxjlbh+"</a></span>";	
									jlbymhtml += "<span title='"+jlbym+"'>"+jlbym+"</span>";	
								}else{
									fxjlbhtml += "<span title='"+fxjlbh+"'>"+"<a href='javascript:Workorder135MainTab.viewFLB('"+id+"')'>"+fxjlbh+"</a></span><br/>";	
									jlbymhtml += "<span title='"+jlbym+"'>"+jlbym+"</span><br/>";	
								}	
						}
						htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+fxjlbhtml+"</td>";
						htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+jlbymhtml+"</td>";
						htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.SJ_JSRQ==null?"":row.SJ_JSRQ.substring(0,10))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.SJ_JSRQ==null?"":row.SJ_JSRQ.substring(0,10))+"</td>";
						var jhsjsj = row.JHSJSJ;
						var jhhtmlContent='';
						var sjhtmlContent='';
						var str = jhsjsj==null?"":jhsjsj.split(",");
						for (var i = 0; i < str.length; i++) {
							var jhhtml = '';
							var sjhtml='';
							var data=str[i].split("#_#");
							var lx = data[0];
							var jh = data[1];
							var sj = data[2];
								if(MonitorUnitUtil.isTime(lx)){
									jhhtml = TimeUtil.convertToHour(jh, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit
									sjhtml = TimeUtil.convertToHour(sj, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit
								}else if(MonitorUnitUtil.isCal(lx)){
									jhhtml = jh;
									sjhtml = sj;
								}else if(MonitorUnitUtil.isLoop(lx)){
									jhhtml = jh+MonitorUnitUtil.unitObj[lx].unit;
									sjhtml = sj+MonitorUnitUtil.unitObj[lx].unit;
								}
								if(i==str.length-1){
									jhhtmlContent += "<span title='"+jhhtml+"'>"+jhhtml+"</span>";	
									sjhtmlContent += "<span title='"+sjhtml+"'>"+sjhtml+"</span>";	
								}else{
									jhhtmlContent += "<span title='"+jhhtml+"'>"+jhhtml+"</span><br/>";	
									sjhtmlContent += "<span title='"+sjhtml+"'>"+sjhtml+"</span><br/>";	
								}	
						}
						htmlContent +="<td style='text-align:center;vertical-align:middle;'>"+sjhtmlContent+"</td>";			
						htmlContent +="<td style='text-align:center;vertical-align:middle;'>"+jhhtmlContent+"</td>";
						htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
						if(row.WGBS == 1){
							htmlContent += "<a type=1 gdbh='"+StringUtil.escapeStr(row.GDBH)+"' gdid='"+row.GDID+"' href=javascript:Workorder135MainTab.openWinDetail(1,'"+row.GDBH+"','"+row.GDID+"')>已反馈</a>";
						}else{
							htmlContent += "未反馈";
						}
						htmlContent += "</td>";
						htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
						htmlContent += "<a type=2 gdbh='"+StringUtil.escapeStr(row.GDBH)+"' gdid='"+row.GDID+"' href=javascript:Workorder135MainTab.openWinDetail(2,'"+row.GDBH+"','"+row.GDID+"')>查看详情</a>";
						htmlContent += "</td>";
						htmlContent += "</tr>";
					});
				});
			}
			$("#workorder135tabexehistory_list").empty();
			$("#workorder135tabexehistory_list").append(htmlContent);
			this_.initWebuiPopover(); //显示附件
		},
		initWebuiPopover : function(){//初始化
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#wo135exeHistoryDiv").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		getHistoryAttachmentList : function(obj){//获取历史附件列表
			var jsonData = [
		         {mainid : $(obj).attr('qtid'), type : '其它附件'}
		        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
		    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		viewWO:function(id){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		},
		/**
		 * 查看反馈或拆换件详情
		 */
		openWinDetail : function(type,gdbh,gdid){
		 	feedback_replacement_tab_view.show({
		 		type : type,
		 		gdbh : gdbh,
		 		gdid : gdid
			});
		},
		/**
		 * 打开库存分布详情对话框
		 */
		openStorageDetailWin : function(bjh,dprtcode){
			var this_ = this;
			var ckidList = [];
			//打开库存分布详情对活框
			open_win_inventory_distribution_details.show({
				bjh : bjh,
				ckidList:ckidList,
				dprtcode:dprtcode
			});
		},openSubstitutionWin : function(obj){
			var bjh = $(obj).attr("bjh");
			var tdjh = $(obj).attr("tdjh");
			var dprtcode = $(obj).attr("dprtcode");
			open_win_equivalent_substitution.show({
				bjh : bjh,
				tdjh : tdjh,
				isParamId : false,
				dprtcode:dprtcode
			});
		}
};