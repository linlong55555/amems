 $(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		refreshPermission();
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
			}
		);
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		$("#attach_div .panel-body").removeClass("padding-left-8 padding-right-8");
		$("#attach_div .panel-body").addClass("padding-left-0 padding-right-0");
		$("#left_column").attr("class","col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0");
		$("#left_column").siblings("div").attr("class","col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8");
		
       //加载审核日期	 
		setShrq();
		auditReportItemList.decodePageParam();
		 
	});
 
 function setShrq(){
	 TimeUtil.getCurrentDate(function(date) {
			 var startStr = date; 
			 startStr=startStr.replaceAll('/','-');
			 startStr=addMonth(startStr,-6);
			 var endStr = date;
			 $("#shrq_search").data('daterangepicker').setStartDate(startStr);
			 $("#shrq_search").data('daterangepicker').setEndDate(endStr);
			 $("#shrq_search").val(startStr + " ~ " + endStr);
		});
 } 
 function addMonth(date, months){ 
		var d=new Date(Date.parse(date.replace(
				/-/g, "/"))); 
		d.setMonth(d.getMonth()+months); 
		var month=d.getMonth()+1; 
		var day = d.getDate(); 
		if(month<10){ 
		month = "0"+month; 
		} 
		if(day<10){ 
		day = "0"+day; 
		} 
		var val = d.getFullYear()+"-"+month+"-"+day; 
		return val; 
	} 
 var auditReportItemList={
		 
	  id:'quality_audit_module',
		 
	  tableListId:'audit_report_main_table_list',
	  
	  tableTopId:'audit_report_main_table_top_div',
	  
	  ztList:['0','5','6'],
	  
	  saveStatus:['addSave','editSave','auditRejectSave','auditPassSave','approveRejectSave','approvePassSave'],
	  
	  decodePageParam:function(){
		  var this_=this;
			try {
				var decodeStr = Base64.decode(pageParam);
				var pageParamJson = JSON.parse(decodeStr);
				var params = pageParamJson.params;
				if (pageParamJson.pagination) {
					this_.goPage(pageParamJson.pagination.page,
							pageParamJson.pagination.sort,
							pageParamJson.pagination.order);
				} else {
					this_.goPage(1, "auto", "desc");
				}
			} catch (e) {
				this_.goPage(1, "auto", "desc");
			}
		  
		  
	  },
	  // 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	  goPage:function (pageNumber, sortType, sequence) {
		  this.loadMainData(pageNumber, sortType, sequence);
	  },
	  
	  loadMainData:function(pageNumber, sortType, sequence){
		var this_ = this;
		var searchParams = this_.gatherSearchParam();
		pagination = {
			page : pageNumber,
			sort : sortType,
			order : sequence,
			rows : 20
		};
		searchParams.pagination = pagination;
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/quality/qualityreviewreport/getAuditReportList",
			type : "post",
			data : JSON.stringify(searchParams),
			dataType : "json",
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					this_.appendHtml(data.rows);
					var page_ = new Pagination({
						renderTo : "auditdreport_main_table_pagination",
						data : data,
						sortColumn : sortType,
						orderType : sequence,
						extParams : {},
						goPage : function(a, b, c) {
							this_.loadMainData(a, b, c);
						}

					});
					// 标记关键字
					signByKeyword($("#keyword_search").val(), [ 3,4 ],"#audit_report_main_table_list tr td");
				} else {
					$("#audit_report_main_table_list").empty();
					$("#auditdreport_main_table_pagination").empty();
					$("#audit_report_main_table_list").append("<tr class='text-center'><td colspan=\"13\">暂无数据 No data.</td></tr>");
				}
				new Sticky({
					tableId : 'audit_report_main_table'
				});
			}
			}

		);
		  
	  },	
	  appendHtml:function(data){
		  var this_=this;		  
		  if(data&&data.length>0){
			  var html="";
			  $.each(data,function(index,row){
				  html+="<tr id="+row.id+"   onclick=\"auditReportItemList.showHiddenContent(this)\"><td class='text-center'>";
				  var zt="";
				  if("0"==row.zt){
					   zt="保存";
					  html+="<i class='fa fa-edit color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:02' title='编辑' id='"+row.id+"' zt='"+row.zt+"'  onclick=\"auditReportItemList.openAddView(this,'edits',event)\"></i>&nbsp;";
					  html+="<i class='fa fa-trash color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:03' title='删除' zt='"+row.zt+"' id='"+row.id+"' onclick=\"auditReportItemList.deleteAuditReport(this,event)\"></i>";
				  }else if("1"==row.zt){
					  zt="提交";
					  html+="<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:04' title='审核' zt='"+row.zt+"' id='"+row.id+"' onclick=\"auditReportItemList.openAddView(this,'audits',event)\"></i>";
				  }else if("2"==row.zt){
					  zt="已审核";
					  html+="<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:05' title='审批' zt='"+row.zt+"' id='"+row.id+"' onclick=\"auditReportItemList.openAddView(this,'approves',event)\"></i>";
				  }else if("3"==row.zt){
					  zt="已批准";
				  }else if("5"==row.zt){
					  zt="审核驳回";
					  html+="<i class='fa fa-edit color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:02' title='编辑' id='"+row.id+"' zt='"+row.zt+"'  onclick=\"auditReportItemList.openAddView(this,'edits',event)\"></i>&nbsp;";
					  html+="<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:qualityreviewreport:main:03' title='删除' zt='"+row.zt+"' id='"+row.id+"' onclick=\"auditReportItemList.deleteAuditReport(this,event)\"></i>";
				  }else if("6"==row.zt){
					  zt="审批驳回";
					  html+="<i class='fa fa-edit color-blue cursor-pointer checkPermission'  permissioncode='quality:qualityreviewreport:main:02' title='编辑' id='"+row.id+"' zt='"+row.zt+"'  onclick=\"auditReportItemList.openAddView(this,'edits',event)\"></i>&nbsp;";
					  html+="<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission'  permissioncode='quality:qualityreviewreport:main:03' title='删除' zt='"+row.zt+"' id='"+row.id+"' onclick=\"auditReportItemList.deleteAuditReport(this,event)\"></i>";
				  }else if("9"==row.zt){
					   zt="关闭"
					  
				  }
				  html+="</td>";
				  html+="<td title="+zt+">"+zt+"</td>";
				  html+="<td title="+row.zlshbgbh+"><a href='#' onClick=\"auditReportItemList.view('"+row.zlshbgbh + "',event)\">"+row.zlshbgbh+ "</a></td>";
				  if(row.shgy){
					  html+="<td title="+row.shgy+">"+row.shgy+"</td>";  
				  }else{
					  html+="<td ></td>";
				  }
				  
				  var lx="";
				  if("1"==row.lx){
					  lx="内部"
				  }else if("2"==row.lx){
					  lx="外部"
				  }
				  html+="<td title="+lx+">"+lx+"</td>";
				  html+="<td title='"+row.shdxmc+"'>"+row.shdxmc+"</td>";
				  var shlb="";
				  if("10"==row.shlb){
					  shlb="计划审核"
				  }else if("20"==row.shlb){
					  shlb="非计划审核"
				  }
				  html+="<td title="+shlb+">"+shlb+"</td>";
				  if(row.shrq){
					  html+="<td title="+row.shrq.substring(0, 10)+">"+row.shrq.substring(0, 10)+"</td>";  
				  }else{
					  html+="<td></td>"; 
				  }
				  
				  if(!row.auditPerson){
					  html+="<td></td>";  
				  }else{
					 var ars= row.auditPerson.shcy.split(",");
					 var shcyMcs="";
					 for(var i=0;i<ars.length;i++){
						 shcyMcs+=ars[i].split("#_#")[3]+",";
					 }
					 shcyMcs=shcyMcs.substr(0,shcyMcs.length-1);
					 html+="<td title="+shcyMcs+">"+shcyMcs+"</td>";  
				  }
				  
				  html+= "<td title='附件 Attachment' class='text-center'>";
				  if (row.attachments!= null && row.attachments.length > 0) {
					 html += '<i id="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';					

				  }
				  html += "</td>";  
				  html+="<td title="+row.user.displayName+">"+row.user.displayName+"</td>";
				  html+="<td title="+row.whsj.substring(0, 10)+">"+row.whsj.substring(0, 10)+"</td>";
				  html+="<td title="+AccessDepartmentUtil.getDpartName(row.dprtcode)+">"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>";
				  html+="</tr>";
			  });
			  $("#"+this.tableListId).empty();
			  $("#"+this.tableListId).append(html);
			  this_.initFile();
			  refreshPermission();
		  }		  		  
	  },
      gatherSearchParam:function(){
    	  var this_=this;
    	  var searchParams={};
    	  var paramsMap={};
    	  var shrqbegin=null;
    	  var shrqend=null;
    	  //状态集合
    	  var ztList=[];
    	  $(":checkbox[name='type_radio']:checked").each(function(){
    		  var value=$(this).val();
			 if (value && value.indexOf("&") > 0) {
				var valueList = value.split("&");
				for (var i = 0; i < valueList.length; i++) {
					ztList.push($.trim(valueList[i]));
				}
			} else {
				ztList.push($.trim(value));
			}
    	  });
    	// 审核对象名称
    	var shdxmc=$.trim($("#quality_audit_module_search_shdxmc").val());
    	//审核对象编号
    	var shdxbh=$.trim($("#quality_audit_module_search_shdxbh").val());
    	//审核对象
    	var shdx=$.trim($("#quality_audit_module_search_shdx").val());
    	
    	//关键字
    	var keyword=$.trim($("#keyword_search").val());
    	//审核日期
    	var shrq=$("#shrq_search").val();
    	
    	if(shrq){
    		 shrqbegin=shrq.substring(0, 10);
    		 shrqend=shrq.substring(13, 23);
    	 }
    	//类型
    	var lx=$("#lx_search").val();
    	//审核类别
    	var shlb=$("#shlb_search").val();
    	//组织机构
    	var dprtcode=$("#dprtcode").val();
    	
    	if(shdx==(shdxbh+" "+shdxmc)){
    		searchParams.shdxbh=shdxbh;
    	}else{
    		paramsMap.shrn=$.trim(shdx);
    	}
  	
    	paramsMap.ztList=ztList;
    	paramsMap.keyword=keyword;
    	paramsMap.shrqbegin=shrqbegin;
        paramsMap.shrqend=shrqend;
        searchParams.paramsMap=paramsMap;
        searchParams.lx=lx;
        searchParams.shlb=shlb;
        searchParams.dprtcode=dprtcode;
        
        return searchParams;
        
      },
      showHiddenContent:function(obj){
    	 	var id = $(obj).attr("id");
    	 	if($("#bottom_hidden_content").css("display")=='block'){
    	 		$("#bottom_hidden_content").slideUp(500);
    	 	}
    	 	$("#bottom_hidden_content").slideDown(100);
    	 	new Sticky({tableId:'audit_report_main_table'}); //初始化表头浮动
    	 	
    	 	
    		var isBottomShown = false;
    		if($(".displayContent").is(":visible")){
    			isBottomShown = true;
    		}
    	 	TableUtil.showDisplayContent();
    	 	if($("#hideIcon").length==0){
    	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#auditdreport_main_table_pagination .fenye"));
    		}
    	 	$("#tabDiv").css("display","none");
    		$("#tabDiv").css("display","block");
    		if(!isBottomShown){
    			TableUtil.makeTargetRowVisible($(obj), $("#audit_report_main_table_top_div"));
    		}
    		//流程记录
		    //初始化流程记录
			var data ={};
			data.mainid = id;
			data.dprtcode = $("#dprtcode").val();
			AjaxUtil.ajax({
				url : basePath + "/quality/processrecord/list",
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					if(data.length>0){
						var htmlContent = '';
				    	$.each(data,function(index, row) {
					    	htmlContent += "<tr>";		
							htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.czrmc)+"' >"+ StringUtil.escapeStr(formatUndefine(row.czrmc))+ "</a></td>";		
							htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.czsj)+"'>"+ StringUtil.escapeStr(row.czsj) + "</td>";
							htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.czsm)+ "'>" + StringUtil.escapeStr(row.czsm)+ "</td>";
							htmlContent += "</tr>";
				    	});
				    	$("#processRecord_list").empty();
				    	$("#processRecord_list").html(htmlContent);
					}else{
						$("#processRecord_list").empty();
						$("#processRecord_list").append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
					}
				}
			});    		  
    	 	new Sticky({tableId:'audit_report_main_table'}); //初始化表头浮动
    	 	App.resizeHeight();
    	 	 
    	 	 
    	},
      openAddView:function(obj,id,e){
    	  var this_=this;
    	  this_.prevent(e);
    	  var primaryKey=$(obj).attr("id");
    	  var zt=$(obj).attr("zt");
    	  var colOptionhead=false;
    	 if("edits"==id){
    		 colOptionhead=true;
    		 $("#modalHeadCN").html("编辑质量审核报告");
    		 $("#modalHeadENG").html("Edit AuditReport");
    		 $("#czsm_area").hide();
    	 }else if("audits"==id){
    		 $("#modalHeadCN").html("审核质量审核报告");
    		 $("#modalHeadENG").html("Audit AuditReport");	
    		 $("#czsm_area").show();
    		 $("#operationDescription").html("审核说明");
    	 }else if("approves"==id){
    		 $("#modalHeadCN").html("审批质量审核报告");
    		 $("#modalHeadENG").html("Approve AuditReport");
    		 $("#czsm_area").show();
    		 $("#operationDescription").html("审批说明");
    	 }else if("adds"==id){
    		 colOptionhead=true;
    		 $("#modalHeadCN").html("新增质量审核报告");
    		 $("#modalHeadENG").html("Add AuditReport");
    		 $("#czsm_area").hide();
    	 }    	  
    	  
    	 $("#audit_report_alert_Modal").modal("show");
    	 //隐藏相关按钮
    	 this_.buttonHide(id);
    	 $("#zt").hide();
         //清除历史数据
    	 this_.emptyData(obj);
    	//清除流程记录
    	 $("#processRecord_list").empty();
    	 $("#processRecord_list_add").empty();
        //初始化附件
       var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				djid:obj.id,
				fileHead : true,
				colOptionhead : colOptionhead,
			    englishHead : 'Attachments'
			});
	   //初始化审核成员
		var memberObj = memberUtil.getMemberComponents('audit_item_member');
		memberObj.shcyInit();
		var dataObj="";
		if(primaryKey){
			//初始化页面数据
			var obj={id:primaryKey};
			  startWait();
			  AjaxUtil.ajax({
					url:basePath+"/quality/qualityreviewreport/getAuditReportList",
					type:"post",
					data:JSON.stringify(obj),
					dataType:"json",
					contentType:"application/json;charset=utf-8",
					async:false,
					success:function(data){
						finishWait();
						dataObj=data.rows[0];
						this_.setData(data.rows[0]);
					}
				  
			  });
             
				//加载审核成员	
			  memberObj.shcyJz(primaryKey);
			    //初始化流程记录
				var data ={};
				data.mainid = primaryKey;
				data.dprtcode = $("#dprtcode").val();
				AjaxUtil.ajax({
					url : basePath + "/quality/processrecord/list",
					type : "post",
					data : JSON.stringify(data),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						if(data.length>0){
							var htmlContent = '';
					    	$.each(data,function(index, row) {
						    	htmlContent += "<tr>";		
								htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.czrmc)+"' >"+ StringUtil.escapeStr(formatUndefine(row.czrmc))+ "</a></td>";		
								htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.czsj)+"'>"+ StringUtil.escapeStr(row.czsj) + "</td>";
								htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.czsm)+ "'>" + StringUtil.escapeStr(row.czsm)+ "</td>";
								htmlContent += "</tr>";
					    	});
					    	$("#processRecord_list_add").empty();
					    	$("#processRecord_list_add").html(htmlContent);
						}else{
							$("#processRecord_list_add").empty();
							$("#processRecord_list_add").append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
						}
					}
				});
               
			  
		}
		
		if("approves"==id||"audits"==id){
			$("#audit_item_member button").hide();
			$("#audit_report_alert_Modal ").find("input,textarea").not("#czsm").attr("disabled", "disabled");
            //$("#audit_report_alert_Modal").find("input,textarea").not("#czsm").addClass("readonly-style");
            $("#button1").hide();
            $("#button2").hide();
		}
		if("adds"==id||"edits"==id){
			$("#audit_item_member button").show();
			$("#audit_report_alert_Modal").find("input,textarea").not("#czsm").removeAttr("disabled");
            //$("#audit_report_alert_Modal").find("input,textarea").not("#czsm").removeClass("readonly-style");
            $("#button1").show();
            $("#button2").show();
		}
		

		 if ((dataObj && dataObj.lx == '1')) {
			$("#quality_audit_module_add_shdx").attr("disabled", "disabled");
			//$("#quality_audit_module_add_shdx").addClass("readonly-style");
		} else if (dataObj && dataObj.lx == '2'
				&& (id == "adds" || id == "edits")) {
			$("#quality_audit_module_add_shdx").removeAttr("disabled");
			$("#button1").hide();
		} else {
			$("#quality_audit_module_add_shdx").removeAttr("disabled");
		}
			
      },
      selectByKey:function(id){
    	  var report={};
  		 var obj={id:id};
		  startWait();
		  AjaxUtil.ajax({
				url:basePath+"/quality/qualityreviewreport/selectByKey",
				type:"post",
				async:false,
				data:obj,
				dataType:"json",
				success:function(data){
					report=data;
				}
			  
		  });
    	  return report;
    	  
    	  
      },
      setData:function(data){
    	  $("#auditId").val(data.id);
    	  $("#shbgbh").val(data.zlshbgbh);
    	  $("#shrq").val(data.shrq.substring(0, 10));
    	  $("input[name='lx'][value='"+data.lx+"']").attr("checked",true);
    	  $("input[name='group_type'][value='"+data.shlb+"']").attr("checked",true);
    	  $("#quality_audit_module_add_shdxid").val(data.shdxid);
    	  $("#quality_audit_module_add_shdxbh").val(data.shdxbh);
    	  $("#quality_audit_module_add_shdxmc").val(data.shdxmc);
    	  $("#quality_audit_module_add_shdx").val(data.shdxmc);
    	  $("#quality_audit_module_add_shdx").val(data.shdxmc);
    	  //审核成员  
    	  
    	  $("#shmd").val(data.shmd);
    	  $("#shfw").val(data.shfw);
    	  $("#shyj").val(data.shyj);
    	  $("#shgs").val(data.shgy);
    	  $("#shjl").val(data.shjl);
    	  
    	  //分发部门
    	  var departIds=[];
    	  var departBhs=[];
    	  var departMcs=[];
    	  if(data.distributeDepartment){
        	  var distributeDepart=data.distributeDepartment.distributeDeparts;
        	 var departArrs=distributeDepart.split(",");
        	 for(var i=0;i<departArrs.length;i++){
        		   var ars=departArrs[i].split("#_#");
        		   departIds.push(ars[0]);
        		   departBhs.push(ars[1]);
        		   departMcs.push(ars[2]);
        	 }
        	  $("#quality_audit_module_selects_ffdxid").val(departIds.join(","));
        	  $("#quality_audit_module_selects_ffdxbh").val(departBhs.join(","));
        	  $("#quality_audit_module_selects_ffdxmc").val(departMcs.join(","));
        	  $("#quality_audit_module_selects_ffdx").val(departBhs.join(",")+" "+departMcs.join(","));
    	  }
    	  
    	  
    	  
      },
      emptyData:function(obj){
    	  $("#auditId").val("");
    	  $("#shbgbh").val("");
    	  $("#shrq").val("");
    	  $("input[name='lx'][value='1']").attr("checked",true);
    	  $("input[name='group_type'][value='10']").attr("checked",true);
    	  $("#quality_audit_module_add_shdxid").val("");
    	  $("#quality_audit_module_add_shdxbh").val("");
    	  $("#quality_audit_module_add_shdxmc").val("");
    	  $("#quality_audit_module_add_shdx").val("");
    	  $("#shmd").val("");
    	  $("#shfw").val("");
    	  $("#shyj").val("");
    	  $("#shgs").val("");
    	  $("#shjl").val("");
    	  $("#quality_audit_module_selects_ffdxid").val("");
    	  $("#quality_audit_module_selects_ffdxbh").val("");
    	  $("#quality_audit_module_selects_ffdxmc").val("");
    	  $("#quality_audit_module_selects_ffdx").val("");
    	  $(".alert-info-message").html("");
    	  $("#czsm").val("");
    	  AlertUtil.hideModalFooterMessage();
   	  
      },
      buttonHide:function(id){
    	  $("span[name='buttons'][id!='"+id+"']").hide();
    	  $("#"+id).show();
      },
      //打开审核对象窗口
      openzrdw:function(obj,jdbs,searchorupdate,isSelects){
  		var this_ = this;
  		var ffbm=false;
  		if(isSelects)ffbm=true;
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:jdbs,
			type : ffbm,// 勾选类型true为checkbox,false为radio
			dprtcode : $("#dprtcode").val(),//
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+searchorupdate+"_"+obj).val(data.dprtbm+" "+data.dprtname);
				$("#"+this_.id+"_"+searchorupdate+"_"+obj+"bh").val(data.dprtbm);
				$("#"+this_.id+"_"+searchorupdate+"_"+obj+"mc").val(data.dprtname);
				$("#"+this_.id+"_"+searchorupdate+"_"+obj+"id").val(data.dprtcode);
			}
		})    	      	  
      },
      resert:function(){
    	  $(":checkbox[name='type_radio']").attr("checked",true);
    	  $("#shdxbh").val("");
    	  $("#keyword").val("");
    	  $("#shrq_search").val("");
    	  $("#lx_search").find("option[value='']").attr("selected",true);
    	  $("#shlb").find("option[value='']").attr("selected",true);
    	  $("#dprtcode").children("option[value='"+userJgdm+"']").attr("selected",true);
    	  
    	
      	$("#quality_audit_module_search_shdxmc").val("");
      	$("#quality_audit_module_search_shdxbh").val("");
      	$("#quality_audit_module_search_shdxid").val("");
      	$("#quality_audit_module_search_shdx").val("");
      },
     
      save:function(status){
		var this_ = this;
		var paramsData = this_.getSubmitOrSaveData();
		paramsData.paramsMap.saveorsubmit = status;
		
		var shrq=$("#shrq").val();
		var shdx=$("#quality_audit_module_add_shdx").val();
		if(!shrq){
			AlertUtil.showModalFooterMessage("审核日期不能为空!");
			
			return ;
		}
		
		if(!shdx){
			AlertUtil.showModalFooterMessage("审核对象为空!");
			$("#quality_audit_module_add_shdx").focus();
			return ;
		}
		
		 var ars=paramsData.paramsMap.shcyArrays;	
		 var map={};
		 for(var i=0;i<ars.length;i++){
			 var auditMemberId=ars[i].cyid;
			 if(!map[auditMemberId]){
				 map[auditMemberId]='1';
			 }else{
				 AlertUtil.showModalFooterMessage("审核对象重复!"); 
				   return;
			 }
						
		 }
		 
		
		
		var isUnique = this_.validatShbhUnique(paramsData.zlshbgbh,paramsData.id);
		var report=null;
		if(paramsData.id){
		  report=this_.selectByKey(paramsData.id);
		}
		
		if (!isUnique) {
			AlertUtil.showMessage('质量审核编号已存在!');
			return;
		}
	
		if("auditRejectSave"==status||"auditPassSave"==status){
				if(report.zt!='1'){
					AlertUtil.showMessage('已提交的质量审核报告才能审核驳回或者审核通过');
					return;
				}
			
		}
			
	    if("approveRejectSave"==status||"approvePassSave"==status){
	    	if(report.zt!='2'){
	    		AlertUtil.showMessage('已审核的质量报告才能审批驳回或者通过');
			    return;
	    		
	    	}
			
	    }

		AjaxUtil.ajax({
			url : basePath + "/quality/qualityreviewreport/save",
			type : "post",
			async : true,
			data : JSON.stringify(paramsData),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				AlertUtil.showMessage('数据保存成功!');
				$("#audit_report_alert_Modal").modal("hide");
				this_.loadMainData();
			}
		});
    		 
    	 
    	
      },
      getSubmitOrSaveData:function(){
    	  var this_=this;
    	  var paramsMap={};
    	  // 审核成员
    	  paramsMap.shcyArrays=[];
    	  //分发部门
    	  paramsMap.ffbmArrays=[];
    	      	  
    	  //分发部门id
    	  var distrubuteDepartId=$("#quality_audit_module_selects_ffdxid").val();
    	  //分发部门名称
    	  var distrubuteDepartMc=$("#quality_audit_module_selects_ffdxmc").val();
    	  //分发部门编号
    	  var distrubuteDepartBh=$("#quality_audit_module_selects_ffdxbh").val();
    	  
    	  var distrubuteDepartIdArs=distrubuteDepartId.split(",");
    	  
    	  var distrubuteDepartMcArs=distrubuteDepartMc.split(",");
    	  
    	  var distrubuteDepartBhArs=distrubuteDepartBh.split(",")
    	    	 
    	  //处理分发部门
    	  for(var i=0;i<distrubuteDepartIdArs.length;i++){
    		  var obj={}; 
    		  obj.ywlx="15";//质量审核报告
    		  obj.bmid=distrubuteDepartIdArs[i];
    		  obj.bmbh=distrubuteDepartBhArs[i];
    		  obj.bmmc=distrubuteDepartMcArs[i];
    		  paramsMap.ffbmArrays.push(obj);
    	  }
    	  
    	  //处理审核成员  
    	  var auditMemberIds=this_.getAuditMemebers("userId");//成员id
    	  var auditMemberBhs=this_.getAuditMemebers("userName");//成员编号
    	  var auditMemeberNames=this_.getAuditMemebers("realName");//成员名称
    	  var zws=this_.getAuditMemebers("zw");//职位
    	  var auditDepartIds=this_.getAuditMemebers("dprtId");//部门id
    	  var auditDepartBhs=this_.getAuditMemebers("dprtBh");//部门编号
    	  var auditDepartMcs=this_.getAuditMemebers("dprtName");//部门名称
    	  
    	  for(var j=0;j<auditMemberIds.length;j++){
    		  var obj={};
    		  obj.ywid=$("#auditId").val();
    		  obj.ywlx="15";
    		  obj.cyid=auditMemberIds[j];
    		  if($("#headman_tr").find("input[value='"+auditMemberIds[j]+"']").size()==1){
    			  obj.js="1";  //组长
    		  }else{
    			  obj.js="2";  //成员
    		  } 		  
    		  obj.cybh=auditMemberBhs[j];
    		  obj.cymc=auditMemeberNames[j];
    		  obj.bmid=auditDepartIds[j];
    		  obj.bmbh=auditDepartBhs[j];
    		  obj.bmmc=auditDepartMcs[j];
    		  obj.zw=zws[j];
    		  paramsMap.shcyArrays.push(obj);
    	  }
   	  
    	  var qualityAuditReport={
    			//质量审核报告id
    			  id:$("#auditId").val(),
    			//质量审核报告编号
    			 zlshbgbh:$.trim($("#shbgbh").val()),  
    			 //审核日期
    			 shrq:$("#shrq").val(),
    			 //类型
    			 lx:$(":radio:checked").val(),
    			 //审核类别 
    			 shlb:$(":radio[name='group_type']:checked").val(),
    			 //审核对象id
    			 shdxid:($(":radio:checked").val()==2)?"":$("#quality_audit_module_add_shdxid").val(),
    			 //审核对象编号
    			 shdxbh:($(":radio:checked").val()==2)?"":$("#quality_audit_module_add_shdxbh").val(),
    			 //审核对象名称
    			 shdxmc:$("#quality_audit_module_add_shdx").val(),
    			 //状态
    			 zt:$("#zt").val(),
    			 //审核目的
    			 shmd:$("#shmd").val(),
    			 //审核范围
    			 shfw:$("#shfw").val(),
    			 //审核依据
    			 shyj:$("#shyj").val(),
    			 //审核概述
    			 shgy:$("#shgs").val(),
    			 //审核结论
    			 shjl:$("#shgs").val(),
    			 //附件
    			 attachments:attachmentsUtil.getAttachmentsComponents('attachments_list_edit').getAttachments(),
    			 
    			 paramsMap:paramsMap
    		
    	  }
    	    	  
    	   return qualityAuditReport;
      },
      getAuditMemebers:function(name){
    	  var ars=[];
    	  $("#dept_info_list").find("input[name='"+name+"']").each(function(){
    		  var displayNameValue=$(this).val().split(" ")[0];
    		  var value=$(this).val();
     		  if("displayName"==name){
     			  if(displayNameValue){
     				 ars.push(displayNameValue);  
     			  }    			   			  
     		  }else{
     			  if(value){
     				 ars.push(value); 
     			  }
     			 
     		  }
     	  });
    	  return ars;
      },
      deleteAuditReport:function(obj,e){
    	var this_=this;
  	    this_.prevent(e);
		var id = $(obj).attr("id");
		var zt = $(obj).attr("zt");
		if ($.inArray(zt,this_.ztList) == -1) {
			AlertUtil.showMessage('已提交,已审核,已批准,已关闭的数据不能删除');
		} else {
			AlertUtil.showConfirmMessage("确定要删除吗？", {
				callback : function() {
					AjaxUtil.ajax({
						url : basePath + "/quality/qualityreviewreport/delete",
						type : "post",
						data : {id:id},
						dataType : "json",
						success : function(data) {
								AlertUtil.showMessage('数据删除成功!');		
								this_.loadMainData();
						}
					});
				}
			});
		}
    	  
      },
      prevent:function(e){
    		 e = e || window.event;  
 		    if(e.stopPropagation) { //W3C阻止冒泡方法  
 		        e.stopPropagation();  
 		    } else {  
 		        e.cancelBubble = true; //IE阻止冒泡方法  
 		    }  	  
      },
       // 校验审核报告编号唯一性
       validatShbhUnique:function(zlshbh,id){
    	   var dprtcode=$("#dprtcode").val();
    	   var isUnique=false;
    	   AjaxUtil.ajax({
    		   url:basePath+"/quality/qualityreviewreport/validatShbhUnique",
    		   type:"post",
    		   async: false,
    		   data:{zlshbh:zlshbh,dprtcode:dprtcode,id:id},
    		   dataType:"json",
    		   success:function(data){
    			   if(data)
    		      isUnique=true;
    		   }
    	   });
    	    return isUnique;
      },
      
      
  	initFile: function(){
		var this_=this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#work_card_main_table_top_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	view:function(zlshbh,e){
		this.prevent(e);
		var dprtcode=$("#dprtcode").val();
		window.open(basePath+"/quality/qualityreviewreportquery/view?dprtcode="+dprtcode+"&zlshbh="+zlshbh);
	},
	/**
	 *  点击附件展开附件列表
	 */
	getHistoryAttachmentList: function(obj){
		var jsonData = [
			   	         {mainid : $(obj).attr('id'), type : '附件'}
			   	    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	getMore:function(){
		if ($("#divSearch", $("#panel")).css("display") == "none") {
			$("#divSearch", $("#panel")).css("display", "block");
			$("#icon", $("#panel")).removeClass("icon-caret-down");
			$("#icon", $("#panel")).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#panel")).css("display", "none");
			$("#icon", $("#panel")).removeClass("icon-caret-up");
			$("#icon", $("#panel")).addClass("icon-caret-down");
		}
		
	},
	orderBy:function(obj){
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
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		this.goPage(currentPage,obj,orderStyle.split("_")[1]);		
		
	},close:function(){
		
		$("#audit_report_alert_Modal").modal("hide");
		
	}
 }
function hideBottom(){
		$("#hideIcon").remove();
		TableUtil.hideDisplayContent();
	}
function openUserWin(b,isBut,isCy) {
 	var obj = {};
 	if(isBut){
 		obj = $(b).parent().parent();
 	}else{
 		obj = $(b).parent();
 	}
 	
 	
 	var userList = [];
 	var userId = $(obj).find($("input[name = 'userId']")).val();
 	var userDisplayName = $(obj).find($("input[name = 'displayName']")).val();
 	if(userId){
 		var user = {
 				id:userId,
 				displayName : userDisplayName
 		}
 		userList.push(user);
 	}
 	Personel_Tree_Multi_Modal.show({
 		checkUserList:userList,//原值，在弹窗中回显
 		dprtcode:$("#dprtcode").val(),//
 		multi:false,
 		callback: function(data){//回调函数
 			if(data.length > 0){
 				if(data[0]){
     				$(obj).find($("input[name = 'userId']")).val(data[0].id);
     				$(obj).find($("input[name = 'displayName']")).val(data[0].displayName);
     				$(obj).find($("input[name = 'userName']")).val(data[0].username);
     				$(obj).find($("input[name = 'realName']")).val(data[0].realname);
     				
     				var dprtObj = obj.parent().next();
     				$(dprtObj).find($("input[name = 'dprtName']")).val(data[0].dprtname);
     				$(dprtObj).find($("input[name = 'dprtId']")).val(data[0].bmdm);
     				$(dprtObj).find($("input[name = 'dprtBh']")).val(data[0].dprtbh);
     				$(dprtObj).find($("div[name = 'departmentName']")).html(data[0].dprtname);
     			}
 			}else{
 				$(obj).find($("input[name = 'userId']")).val('');
 				$(obj).find($("input[name = 'displayName']")).val('');
 				$(obj).find($("input[name = 'userName']")).val('');
 				$(obj).find($("input[name = 'realName']")).val('');
 				
 				var dprtObj = obj.parent().next();
 				$(dprtObj).find($("input[name = 'dprtName']")).val('');
 				$(dprtObj).find($("input[name = 'dprtId']")).val('');
 				$(dprtObj).find($("input[name = 'dprtBh']")).val('');
 				$(dprtObj).find($("div[name = 'departmentName']")).html('');
 			}
 			
 		}
 	});
 }  
function validation(){
	  $('#audit_report_alert_Modal_form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {   
	        	audit_report_alert_Modal_shrq: {
	                validators: {
	                    notEmpty: {
	                        message: '审核日期不能为空'
	                    }
	                }
	            },
	            audit_report_alert_Modal_shdx: {
	                validators: {
	                    notEmpty: {
	                        message: '审核对象不能为空'
	                    }
	                }
	            }
	        }
	    });
}
function setShdx(obj) {
	var value = $(obj).val();
	if ("1" == value) {
		$("#quality_audit_module_add_shdx").attr("disabled", "disabled");
		//$("#quality_audit_module_add_shdx").addClass("readonly-style");
		$("#button1").show();
	}
	if ("2" == value) {
		$("#quality_audit_module_add_shdx").removeAttr("disabled");
		//$("#quality_audit_module_add_shdx").removeClass("readonly-style");
		$("#button1").hide();
	}

}
