	$(function(){
		 Navigation(menuCode,'','','韩武','2017-08-01','韩武','2017-08-01');
		 $("#import_btn").remove();
		 // 加载维修方案版本
		 maintenancePlanAudit.reload();
		 // 初始化时间控件
		 maintenancePlanAudit.initDateWidget();
		 // 初始化技术评估单
		 maintenancePlanAudit.initEvaluation();
		 // 绑定事件
		 maintenancePlanAudit.addEvent();
		 
		//执行待办
		if(todo_ywid) {
			maintenancePlanAudit.showAuditAgreeModal(todo_ywid);
			todo_ywid = null;
		}
		 
	 });
	
	var maintenancePlanAudit={
			data:[],
			wxfa:{},
			// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
			goPage:function(pageNumber,sortType,sequence){
				this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
			},
			// 带条件搜索的翻页
			AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
				 var this_ = this;
				 var obj ={};
				 AjaxUtil.ajax({
					type: "post",
			 		url:basePath+"/project2/maintenancescheme/auditlist",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			dprtcode : userJgdm
			 		}),
			 		success:function(data){
		 				this_.appendContentPlanHtml(data);
		 				this_.data = data;
		 				if(data.length > 0){
		 					$("#maintenance_plan_version>tr:first").click();
		 				}else{
		 					this_.wxfa = {};
		 					this_.showBottomRightContent();
		 				}
			 		}
			     }); 
			},
			reload:function(){
				this.goPage(1,"auto","desc");
			},
			initDateWidget:function(){
				$('.datepicker').datepicker({
					 autoclose: true,
					 clearBtn:true
				}).on('hide', function(e) {
			    });
			},
			//表格拼接
			appendContentPlanHtml:function(list){
				var this_ = this;
				var htmlContent = '';
				if(list.length > 0){
					$.each(list,function(index,row){
						htmlContent +="<tr id='"+row.id+"' class='cursor-pointer' onclick='maintenancePlanAudit.showBottomRightContent(this,\""+row.bb+"\")'>"
						htmlContent += "<td>";
						htmlContent += "<a href='#' style='text-decoration:none;' class='checkPermission' permissioncode='project2:maintenanceproject:audit:main:01' onclick='maintenancePlanAudit.showAuditAgreeModal(\""+row.id+"\")' title='审核 Audit'><i class='icon-foursquare'></i></a>&nbsp;";
						if(row.fBbid){
							htmlContent += "<a href='javascript:void(0);' style='text-decoration:none;' title='查看差异' onclick='maintenanceItemList.viewSchemeDifference(\""+row.id+"\")'>";
							htmlContent += "<i class='icon-eye-open'></i>";
							htmlContent += "</a>&nbsp;&nbsp;";
						}
						htmlContent += "R"+row.bb;
						htmlContent += "</td>";
						htmlContent += "<td>"+(StringUtil.escapeStr(row.jx))+"</td>";
						htmlContent +="</tr>"
				   });
				}else{
					htmlContent = "<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>"
				}
			    $("#maintenance_plan_version").html(htmlContent);
			    refreshPermission();
			},
			showBottomRightContent:function(tr, bb){
				var this_ = this;
				var wxfaid = $(tr).attr("id");
				$(this_.data).each(function(){
					if(wxfaid == this.id){
						this_.wxfa = this;
					}
				});
				$("#maintenance_plan_version .bg_tr").removeClass("bg_tr");
				$(tr).addClass("bg_tr");
				maintenanceItemList.initAcReg();
				maintenanceItemList.reload();
				maintenanceInfo.load();
				maintenanceItemList.switchBtn();
			},
			// 显示/隐藏维修方案
			toggleMaintenance:function(obj){
				var i = $(obj);
				if(i.hasClass("icon-caret-left")){
					i.removeClass("icon-caret-left").addClass("icon-caret-right");
					$("#left_div").hide();
					$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
				}else{
					i.removeClass("icon-caret-right").addClass("icon-caret-left");
					$("#left_div").show();
					$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
				}
				maintenanceItemList.authHeight();
			},
			// 显示审核通过模态框
			showAuditAgreeModal:function(id){
				startWait();
				// 提交数据
				AjaxUtil.ajax({
					url:basePath+"/project2/maintenancescheme/detail",
					type:"post",
					data:JSON.stringify({
						id : id
					}),
					dataType:"json",
					contentType: "application/json;charset=utf-8",
					success:function(wxfa){
						finishWait();
						if(wxfa.zt != 2){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						$("#maintenance_schedule_modal_jx").val(wxfa.jx);
						$("#maintenance_schedule_modal_zwms").val(wxfa.zwms);
						$("#maintenance_schedule_modal_bb").val(wxfa.bb);
						$("#maintenance_schedule_modal_wxfabh").val(wxfa.wxfabh);
						$("#maintenance_schedule_modal_bz").val(wxfa.bz);
						$("#maintenance_schedule_modal_gbyj").val(wxfa.gbyj);
						$("#shyj").val("");
						ModalUtil.showModal("auditModal");
					}
				});
				evaluation_modal.changeParam({
					isShowed : false,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : id,
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#maintenance_schedule_modal_jx").val(),// 机型
				});
				evaluation_modal.load();
			},
			
			// 初始化技术评估单
			initEvaluation:function(){
				evaluation_modal.init({
					isShowed : false,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#maintenance_schedule_modal_jx").val(),// 机型
				});
			},
			
			// 绑定事件
			addEvent:function(){
				var this_ = this;
				$('#auditModal').on('shown.bs.modal', function() {
					AlertUtil.hideModalFooterMessage();
					var wxfa = this_.wxfa;
					
					//流程信息
					introduce_process_info_class.show({  
						status:wxfa.zt,//1,编写,2审核,3批准，4审核驳回,5批准驳回
						prepared:StringUtil.escapeStr(wxfa.zdr?wxfa.zdr.displayName:''),//编写人
						preparedDate:StringUtil.escapeStr(wxfa.zdsj),//编写日期
						checkedOpinion:StringUtil.escapeStr(wxfa.shyj),//审核意见
						checkedby:StringUtil.escapeStr(wxfa.shr?wxfa.shr.displayName:''),//审核人
						checkedDate:StringUtil.escapeStr(wxfa.shsj),//审核日期
						checkedResult:wxfa.shjl,//审核结论
						approvedOpinion:StringUtil.escapeStr(wxfa.pfyj),//批准意见
						approvedBy:StringUtil.escapeStr(wxfa.pfr?wxfa.pfr.displayName:''),//批准人
						approvedDate:StringUtil.escapeStr(wxfa.pfsj),//批准日期
						approvedResult : wxfa.pfjl,// 批准结论
					});
			    })
			},
			
			// 审核通过
			agree:function(){
				var this_ = this;
				var wxfa = this_.wxfa;
				
				AlertUtil.showConfirmMessage("确定要审核通过吗？", {callback: function(){
					
					startWait($("#auditModal"));
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/auditagree",
						type:"post",
						data:JSON.stringify({
							id : wxfa.id,
							jx : wxfa.jx,
							shyj : introduce_process_info_class.getData().shenhe,
							dprtcode : wxfa.dprtcode
						}),
						dataType:"json",
						modal : $("#auditModal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#auditModal"));
							AlertUtil.showMessage("审核通过成功!");
							$("#auditModal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
			},
			

			// 审核拒绝
			reject:function(){
				var this_ = this;
				var wxfa = this_.wxfa;
				
				AlertUtil.showConfirmMessage("确定要审核驳回吗？", {callback: function(){
					
					startWait($("#auditModal"));
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/auditreject",
						type:"post",
						data:JSON.stringify({
							id : wxfa.id,
							jx : wxfa.jx,
							shyj : introduce_process_info_class.getData().shenhe,
							dprtcode : wxfa.dprtcode
						}),
						dataType:"json",
						modal : $("#auditModal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#auditModal"));
							AlertUtil.showMessage("审核驳回成功!");
							$("#auditModal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
			}
	}
	
