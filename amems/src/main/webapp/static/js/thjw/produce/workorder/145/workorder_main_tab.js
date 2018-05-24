/**
 * 145工单,tab信息
 */
Workorder145MainTab = {
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
			/*this_.loadGDZxhistoryInfo();*/
			
		},
		/**根据GDID查找数据*/
		selectById : function(id){//通过id获取数据 
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/produce/workorder145/selectWOById",
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
				$("#wo145feedbacktab_sjZxdw").val(this_.woInfo.workpackage145.zxdw); 
				$("#wo145feedbacktab_sjJsrq").val(StringUtil.escapeStr(this_.woInfo.sjJsrq).substring(0,16)); //实际完成日期
				//实际工作者
				var gzzList = '';
				$.each(this_.woInfo.workers || [], function(i, row){
					gzzList += formatUndefine(row.gzz) + ",";
				});
				if(gzzList != ''){
					gzzList = gzzList.substring(0,gzzList.length - 1);
				}
				$("#wo145feedbacktab_sjgzz").val(gzzList);
				
				$("#wo145feedbacktab_sjjcz").val(this_.woInfo.sjJcz);//实际检查者
				$("#wo145feedbacktab_sjgs").val(this_.woInfo.sjGs);//实际工时
				$("#wo145feedbacktab_sjzd").val(this_.woInfo.sjZd);//工作站点
				$("#wo145feedbacktab_sjksrq").val(StringUtil.escapeStr(this_.woInfo.sjKsrq).substring(0, 10));//实际开始日期
				$("#wo145feedbacktab_gzxx").val(this_.woInfo.gzxx);//故障信息
				$("#wo145feedbacktab_clcs").val(this_.woInfo.clcs);//处理措施
				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('wo145feedbacktab_attachments_list_edit');
				attachmentsObj.show({
					djid:this_.woInfo.id,
					fileHead : true,
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
				   	$("#workorder145chjtab_list").empty();
					$("#workorder145chjtab_list").html(htmlContent);
				});
			}else{
				$("#workorder145chjtab_list").html("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
		},
		/**航材工具数据*/
		loadHCInfo : function(){
			var this_ = this;
			var param = {};
			param.id = this_.woInfo.id;
			param.dprtcode = this_.woInfo.dprtcode;
			AjaxUtil.ajax({
				url:basePath+"/produce/workorder145/getGDHCToolDetail",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					if(data.hcList.length>0){
						this_.appendHCContentHtml(data);
					}else{
						$("#workorder145tabmataerialtool_list").empty();
						$("#workorder145tabmataerialtool_list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
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
						htmlContent += "<a style='"+warningStyle+"' href=javascript:Workorder145MainTab.openStorageDetailWin('"+StringUtil.escapeStr(row.JH)+"','"+row.DPRTCODE+"')>"+StringUtil.escapeStr(row.KCSL)+"</a>";
						htmlContent += "</td>";
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
							var tdjtTitle = StringUtil.escapeStr(bjh) + " : " + sl + "," + StringUtil.escapeStr(ms);
							var tdj = "<a bjh='"+StringUtil.escapeStr(row.JH)+"' dprtcode='"+row.DPRTCODE+"' tdjh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick=Workorder145MainTab.openSubstitutionWin(this)>"+StringUtil.escapeStr(bjh)+"</a>";
							tdj += " : ";
							tdj += "<a jh='"+StringUtil.escapeStr(bjh)+"'  href=javascript:Workorder145MainTab.openStorageDetailWin('"+StringUtil.escapeStr(bjh)+"','"+row.DPRTCODE+"')>"+sl+"</a>";
							tdj += " , " + StringUtil.escapeStr(ms);
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
			$("#workorder145tabmataerialtool_list").empty();
			$("#workorder145tabmataerialtool_list").append(htmlContent);
		},
		/**执行历史数据*/
		loadGDZxhistoryInfo : function(){
			var this_ = this;
			var param = {};
			param.fjzch = this_.woInfo.fjzch; //飞机注册号
			param.jksjid = this_.woInfo.jksjid; //监控数据ID
			param.dprtcode = this_.woInfo.dprtcode; //组织机构
			AjaxUtil.ajax({
				url:basePath+"/produce/workorder/getGDZxhistoryInfo",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					if(data.exeList.length>0){
						this_.appendZXhistoryContentHtml(data);
					}else{
						$("#workorder145tabexehistory_list").empty();
						$("#workorder145tabexehistory_list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
					}
				}
			});
		},
		appendZXhistoryContentHtml:function(list){
			var htmlContent="";			
			var this_ = this;
			if(list!=null){
				$.each(list,function(index,exeList){
					$.each(exeList,function(j, row){
						htmlContent +="<tr>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.ZJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ZJH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GDZLX)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GDZLX)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.RWH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.RWH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.RWBB)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.RWBB)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.CKH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.CKH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GDBH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GDBH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(this_.woInfo.gdbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(this_.woInfo.gdbh)+"</td>";
						
						
						htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
						if((this_.woInfo.paramsMap.woAttachCount != null && this_.woInfo.paramsMap.woAttachCount > 0) || (this_.woInfo.gznrfjid != null && this_.woInfo.gznrfjid != "")){
							htmlContent += '<i qtid="'+this_.woInfo.id+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
						}
						htmlContent += "</td>";
						
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GDBT)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr("缺")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr("缺")+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr("缺")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr("缺")+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr("缺")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr("缺")+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.XLH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.XLH)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr("缺")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr("缺")+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.SJGS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.SJGS)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.JHGS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JHGS)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.GZXX)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GZXX)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.CLCS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.CLCS)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.SJ_GZZ)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.SJ_GZZ)+"</td>";
						htmlContent += "<td title='"+StringUtil.escapeStr(row.SJ_JCZ)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.SJ_JCZ)+"</td>";
						htmlContent += "</tr>";
					});
				});
			}
			$("#workorder145tabexehistory_list").empty();
			$("#workorder145tabexehistory_list").append(htmlContent);
			this_.initWebuiPopover(); //显示附件
		},
		initWebuiPopover : function(){//初始化
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#wo145exeHistoryDiv").scroll(function(){
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