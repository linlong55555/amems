	$(function(){
		 Navigation(menuCode,'','','韩武','2017-08-01','韩武','2017-08-01');
		 $("#import_btn").remove();
		 // 加载维修方案版本
		 maintenancePlanEffect.reload();
		 // 初始化时间控件
		 maintenancePlanEffect.initDateWidget();
		 // 绑定事件
		 maintenancePlanEffect.addEvent();
		 
		//执行待办
	    if(todo_ywid){
	    	maintenancePlanEffect.showEffectModal(todo_ywid);
	    	todo_ywid = null;
	    }
		 
		
		 
	 });
	
	var maintenancePlanEffect={
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
			 		url:basePath+"/project2/maintenancescheme/effectlist",
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
						htmlContent +="<tr id='"+row.id+"' class='cursor-pointer' onclick='maintenancePlanEffect.showBottomRightContent(this,\""+row.bb+"\")'>"
						htmlContent += "<td>";
						htmlContent += "<a href='#' style='text-decoration:none;' class='checkPermission' permissioncode='project2:maintenanceproject:effect:main:01' onclick='maintenancePlanEffect.showEffectModal(\""+row.id+"\")' title='生效确认'><i class='iconnew-confirm'></i></a>&nbsp;";
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
			// 显示提交生效模态框
			showEffectModal:function(id){
				
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
						if(wxfa.zt != 7){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						TimeUtil.getCurrentTime("#qrsj");
						$("#sxrid").val(currentUser.id);
						$("#sxr").val(currentUser.displayName);
						$("#sxbmid").val(currentUser.dprt_bm?currentUser.dprt_bm.id:"");
						$("#effectModal").modal("show");
					}
				});
			},
			// 选择校验人
			chooseUser:function(){
				
				Personel_Tree_Multi_Modal.show({
					checkUserList:[{id:$("#sxrid").val(),displayName:$("#sxr").val()}],//原值，在弹窗中回显
					dprtcode:$("#dprtId").val(),
					clearUser:false,
					multi:false,
					callback: function(data){//回调函数
						var user = $.isArray(data)?data[0]:{id:'',displayName:''};
						$("#sxrid").val(user.id);
						$("#sxr").val(user.displayName);
						$("#sxbmid").val(user.bmdm);
					}
				});
				AlertUtil.hideModalFooterMessage();
			},
			// 绑定事件
			addEvent:function(){
				var this_ = this;
				$('#effectModal').on('shown.bs.modal', function() {
					AlertUtil.hideModalFooterMessage();
					var wxfa = this_.wxfa;
					$("#maintenance_schedule_modal_jx").val(wxfa.jx);
					$("#maintenance_schedule_modal_zwms").val(wxfa.zwms);
					$("#maintenance_schedule_modal_bb").val(wxfa.bb);
					$("#maintenance_schedule_modal_wxfabh").val(wxfa.wxfabh);
			    })
			},
			// 生效确认
			confirm:function(){
				var this_ = this;
				var wxfa = this_.wxfa;
				
				AlertUtil.showConfirmMessage("确定要生效吗？", {callback: function(){
					
					startWait($("#effectModal"));
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/confirmProduction",
						type:"post",
						data:JSON.stringify({
							id : wxfa.id,
							jx : wxfa.jx,
							sxrid : $("#sxrid").val(),
							sxbmid : $("#sxbmid").val(),
							dprtcode : wxfa.dprtcode
						}),
						dataType:"json",
						modal : $("#effectModal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#effectModal"));
							AlertUtil.showMessage("生效确认成功!");
							$("#effectModal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
			},
			
	}
	
