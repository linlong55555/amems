
	
	var installationlist_table = {
		
		tableId : "installation_list_main_table",
		
		tbodyId : "installation_list_main_table_list",
		
		paginationId : "installation_list_main_table_pagination",
		
		// table重新加载
		reload:function(){
			this.goPage(1,"auto","desc");
		},
		
		// table跳转页面
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		
		// 获取table数据
		AjaxGetDatasWithSearch:function(pageNumber, sortType, sequence){
			 var this_ = this;
			 var url = basePath+"/aircraftinfo/installationlist/editpagelist";
			 if($("#zt").val() == "history"){
				 url = basePath+"/aircraftinfo/installationlist/effectpagelist";
			 }
			 
			 var param = this_.gatherSearchParam();
			 param.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 startWait();
			 AjaxUtil.ajax({
				type: "post",
				url: url,
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		data:JSON.stringify(param),
		 		success:function(data){
		 			finishWait();
		 			// 拼接表格
		 			this_.appendContentHtml(data);
		 			// 拼接分页
		 			this_.appendPaginationHtml(data, pageNumber, sortType, sequence);
		 			// 初始化附件控件
		 			this_.initWebuiPopover();
		 			// 表格添加title
		 			TableUtil.addTitle("#" + this_.tbodyId + " tr td");
					// 标记关键字
				    signByKeyword(installationlist.getKeyword(), [6,7,8,9,28], "#" + this_.tbodyId + " tr td");
				    // 移除
	 				FormatUtil.removeNullOrUndefined();
	 				// 刷新权限
	 				refreshPermission();
	 				// 浮动表头
				    new Sticky({tableId:this_.tableId});
		 		}
		     });
		},
		
		// 获取查询参数
		gatherSearchParam : function(){
			
			var searchParam = {};
			searchParam.fjzch = installationlist.getFjzch();
			searchParam.dprtcode = installationlist.getDprtcode();
			searchParam.wz = $("#wz").val();
			
			if($("#zt").val() == "current" && !$("#containsIneffective").is(":checked")){
				searchParam.tbbs = 0;
			}
			var paramsMap = {};
			paramsMap.keyword = installationlist.getKeyword();
			paramsMap.historyStatus = $("#zt").val();
			var kzlx = $("#kzlx").val();
			var kzlxList=[];
			if(kzlx != null){
				for(var i = 0 ; i < kzlx.length ; i++){
					if('multiselect-all' != kzlx[i]){
						kzlxList.push(kzlx[i]);
					}
				}
			}
			if(kzlxList != null && kzlxList.length > 0){
				paramsMap.kzlxList = kzlxList;
			}
			
			if($("#zjjlx").val() != "其他"){
				searchParam.zjjlx = $("#zjjlx").val();
			}else{
				var items = DicAndEnumUtil.getDicItems("52", installationlist.getDprtcode());
				var otherZjjlx = []; 
				if(items != null && items.length >0){
					$.each(items,function(i,obj){
						if(obj.name != "其他"){
							otherZjjlx.push(StringUtil.escapeStr(obj.name));
						}
					});
			  	}
				paramsMap.otherZjjlx = otherZjjlx;
			}
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		
		// 拼接表格
		appendContentHtml:function(data){
			var this_ = this;
			var htmlContent = '';
			if(data.total > 0){
				var isCurr = $("#zt").val() == "current";
				$.each(data.rows,function(index,row){
					htmlContent +="<tr>"
					htmlContent +="<td class='text-center fixed-column'>";
					if(row.zjzt == 1 || row.tbbs == 1){
						if($("#zt").val() != "history"){
							htmlContent +="<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:installationlist:main:02' onclick='installationlist.modify(\""+row.id+"\")' title='修改 Edit'></i>";
						}
					}
					if(!row.effective && row.tbbs == 1){
						if($("#zt").val() != "history"){
							htmlContent +="&nbsp;&nbsp;<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:installationlist:main:03' onclick='installationlist.del(\""+row.id+"\")' title='删除 Delete'></i>";
						}
					}
					htmlContent +="</td>";
					
					htmlContent +="<td style='text-align:center'>";
					if(row.initDatas && row.initDatas.length > 0){
						var initFlag = false;
						$(row.initDatas).each(function(i, obj){
							if($("#zt").val() == "current" && obj.csz != null){
								initFlag = true;
							}
							if($("#zt").val() == "history" && obj.zssyy != null){
								initFlag = true;
							}
						});
						if(initFlag){
							htmlContent +="<i class='icon-ok color-green' title='初始化信息已维护'></i>";
						}else{
							htmlContent +="<i class='icon-exclamation color-red' title='初始化信息未维护'></i>";
						}
					}
					htmlContent +="</td>";
					
					htmlContent +="<td class='text-center'>"+DicAndEnumUtil.getEnumName('installedStatusEnum', row.zjzt)+"</td>";
					htmlContent +="<td class='text-center'>"+(row.tbbs == 1 ? "N" : "Y")+"</td>";
					
					if(row.cj <= 1){
						htmlContent +="<td class='text-center' title='一级件'>*</td>";
					}else if(isCurr && (!row.parent || !row.parent.id)  ){
						htmlContent +="<td class='text-center' title='上级部件缺失'>N/A</td>";
					}else{
						htmlContent +="<td class='text-center'></td>";
					}
					
					htmlContent +="<td class='text-left'>"+StringUtil.escapeStr(row.fixChapter?row.fixChapter.displayName:"")+"</td>";
					
					var zt = $("#zt").val();
					if(zt == "current"){
						htmlContent +="<td><a href='javascript:void(0);' onclick='installationlist.viewCurrent(\""+row.id+"\")'>"+StringUtil.escapeStr(row.bjh)+"</a></td>";
					}else{
						htmlContent +="<td><a href='javascript:void(0);' onclick='installationlist.viewHistory(\""+row.id+"\")'>"+StringUtil.escapeStr(row.bjh)+"</a></td>";
					}
					htmlContent +="<td>"+StringUtil.escapeStr(row.displayName)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.xlh)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.xh)+"</td>";
					htmlContent +="<td class='text-center'>"+this_.buildMonitorType(row)+"</td>";
					htmlContent +="<td class='text-center'>"+DicAndEnumUtil.getEnumName('installedPositionEnum', row.wz)+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.zjjlx)+"</td>";
					htmlContent +="<td class='text-center'>"+this_.getInitValueByJklbh(row, "CAL")+"</td>";
					htmlContent +="<td>"+this_.getInitValueByJklbh(row, "TIME")+"</td>";
					htmlContent +="<td>"+this_.getInitValueByJklbh(row, "LOOP")+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.pch)+"</td>";
					htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.zjsl)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.jldw)+"</td>";
					htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
					if(row.paramsMap.certificateCount != null && row.paramsMap.certificateCount > 0) {
						htmlContent += '<i bjh="'+StringUtil.escapeStr(row.bjh)+'" xlh="'+StringUtil.escapeStr(row.xlh)+'" pch="'+StringUtil.escapeStr(row.pch)+'" dprtcode="'+StringUtil.escapeStr(row.dprtcode)+'" class="certificate-view fa fa-id-card color-blue cursor-pointer "  style="font-size:15px"></i>';
					}
					htmlContent += "</td>";
					htmlContent +="<td class='text-center'>"+(row.chucrq||"").substr(0, 10)+"</td>";
					htmlContent +="<td class='text-right'>"+ this_.computeCal(row.chucrq) + "</td>";
					htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.tsn)+"</td>";
					htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.tso)+"</td>";
					htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.csn)+"</td>";
					htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.cso)+"</td>";
					htmlContent +="<td class='text-center'>"+(row.azsj||"").substr(0, 16)+"</td>";
					if(row.azjldid){
						htmlContent +='<td><a href="javascript:void(0);" onclick="installationlist_table.viewWorkOrder(\''+row.azjldid+'\')">'+StringUtil.escapeStr(row.azjldh)+'</a></td>';
					}else{
						htmlContent +="<td>"+StringUtil.escapeStr(row.azjldh)+"</td>";
					}
					htmlContent +="<td class='text-center'>"+(row.ccsj||"").substr(0, 16)+"</td>";
					if(row.ccjldid){
						htmlContent +='<td><a href="javascript:void(0);" onclick="installationlist_table.viewWorkOrder(\''+row.ccjldid+'\')">'+StringUtil.escapeStr(row.ccjldh)+'</a></td>';
					}else{
						htmlContent +="<td>"+StringUtil.escapeStr(row.ccjldh)+"</td>";
					}
					htmlContent +="<td>"+StringUtil.escapeStr(row.bjgzjl)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.bz)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.whr?row.whr.displayName:'')+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"35\">暂无数据 No data.</td></tr>";
			}
			$("#" + this_.tbodyId).html(htmlContent);
		},
		
		// 生成监控类型
		buildMonitorType : function(obj){
			var str = "";
			if(obj.skbs == 1 && obj.ssbs == 0){
				str = "时控件";
			}
			if(obj.skbs == 0 && obj.ssbs == 1){
				str = "时寿件";
			}
			if(obj.skbs == 1 && obj.ssbs == 1){
				str = "时控件/时寿件";
			}
			if(str == ""){
				str = "非控制件";
			}
			return str;
		},
		
		// 根据监控类编号获取初始化值
		getInitValueByJklbh : function(row, type){
			var result = "";
			$(row.initDatas||[]).each(function(i, obj){
				if(type == "CAL" && MonitorUnitUtil.isCal(obj.jklbh)){
					result += (obj.csz || obj.zssyy);
				}
				if((obj.csz || obj.zssyy) 
						&& ((type == "TIME" && MonitorUnitUtil.isTime(obj.jklbh))
						|| (type == "LOOP" && MonitorUnitUtil.isLoop(obj.jklbh)))){
					result +=  (obj.csz || obj.zssyy) + MonitorUnitUtil.getMonitorUnit(obj.jklbh) + " ";
				}
			});
			return result;
		},
		
		// 拼接分页
		appendPaginationHtml : function(data,pageNumber, sortType, sequence){
			var this_ = this;
			if(data.total > 0){
				new Pagination({
					renderTo : this_.paginationId,
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					extParams : {},
					goPage : function(a, b, c) {
						this_.AjaxGetDatasWithSearch(a, b, c);
					}
				});
			}else{
				$("#"+this_.paginationId).empty();
			}
		},
		
		// 初始化附件控件
		initWebuiPopover : function(){
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('certificate-view','body',function(obj){
				return this_.getHistoryCertificateList(obj);
			},600);
			$("#structrucRepair_table").scroll(function(){
				$('.certificate-view').webuiPopover('hide');
			});
		},
		
		// 获取历史附件列表
		getHistoryCertificateList : function(obj){
			var xlh = $(obj).attr('xlh');
			var pch = $(obj).attr('pch');
			if(xlh){
				pch = "-";
			}else{
				xlh = "-";
				pch = pch || "-";
			}
			var jsonData ={jh : $(obj).attr('bjh'),xlh : xlh,pch : pch,dprtcode : $(obj).attr('dprtcode') };
		    
			return history_certificate_alert_Modal.getHistoryCertificateList(jsonData);
		},
		
		// 计算cal
		computeCal : function(chucrq){
			return this.dateMinus(chucrq);
		},
		
		// 计算日期相减天数
		dateMinus : function(sDate){
		  if(sDate){
			  var sdate = new Date(sDate.replace(/-/g, "/"));
			　　var now = new Date();
			　　var days = now.getTime() - sdate.getTime();
			　　var day = parseInt(days / (1000 * 60 * 60 * 24));
			　　return day;
		  }
		　　return "";
		},
		
		// 查看工单
		viewWorkOrder : function(id){
			window.open(basePath + "/produce/workorder/woView?gdid=" + id);
		},
		
		//导出
		exportExcel : function(){
			window.open(basePath+"/aircraftinfo/installationlist/zjqd.xls?json=" + JSON.stringify(this.gatherSearchParam()));
		},
	};
	
	