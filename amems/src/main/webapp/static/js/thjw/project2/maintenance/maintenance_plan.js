	$(function(){
		 Navigation(menuCode,'','','GC-6-3','韩武','2017-08-01','韩武','2017-08-01');
		 
		 // 加载飞机机型
		 maintenancePlan.loadFjjx();
		//执行待办
	    if(todo_ywid){
	    	if(todo_zt == 7){
	    		
	    	}
	    	todo_ywid = null;//默认机型
			if(todo_fjjx) {
				todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
				if(todo_fjjx){
					$("#fjjx").val(todo_fjjx);
				}
				todo_fjjx = null;
			}
	    }
		if(todo_lyid) {
			//默认机型
			if(todo_fjjx) {
				todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
				if(todo_fjjx){
					$("#fjjx").val(todo_fjjx);
				}
				todo_fjjx = null;
			}
		}
		 
		 // 加载维修方案版本
		 maintenancePlan.reload();
		 // 初始化验证
		 maintenancePlan.initValidation();
		 // 初始化时间控件
		 maintenancePlan.initDateWidget();
		 // 初始化技术评估单
		 maintenancePlan.initEvaluation();
		 // 初始化日志
		 maintenancePlan.initLog();
		 
	 });
	
	var maintenancePlan={
			data:[],
			wxfa:{},
			effectiveWxfa:{},
			setEffectiveWxfa:function(list){
				var result = {};
				if(list && list.length > 0){
					$.each(list, function(i, obj){
						if(obj.zxbs == 2){
							result = obj;
						}
					});
				}
				this.effectiveWxfa = result;
			},
			getEffectiveWxfa:function(){
				return this.effectiveWxfa;
			},
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
			 		url:basePath+"/project2/maintenancescheme/queryByFjjx",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			jx : $("#fjjx").val(),
			 			dprtcode : userJgdm
			 		}),
			 		success:function(data){
			 			this_.setEffectiveWxfa(data);
		 				this_.appendContentPlanHtml(data);
		 				this_.showBtn(data);
		 				this_.data = data;
		 				if(data.length > 0){
		 					$("#maintenance_plan_version>tr:first").click();
		 					//执行待办
		 				    if(todo_ywid){
		 				    	if(todo_zt == 1 || todo_zt == 5 || todo_zt == 6){
		 				    		maintenancePlan.showEditModal();
		 				    	}else if(todo_zt == 4){
		 				    		maintenancePlan.showSubmitProductionModal();
		 				    	}
		 				    	todo_ywid = null;
		 				    }
		 					
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
					 clearBtn:false
				}).on('hide', function(e) {
					$('#maintenance_scheme_form').data('bootstrapValidator')  
				     .updateStatus('jhSxrq', 'NOT_VALIDATED',null)  
				     .validateField('jhSxrq');  
			    });
			},
			showBtn:function(list){
				$("#add_btn,#revision_btn,#edit_btn,#submit_btn,#production_btn").addClass("hidden");
				if(list.length > 0){
					var newest = list[0];
					if(newest.zt == 10 && newest.zxbs != 0){
						$("#revision_btn").removeClass("hidden");
					}
					if(newest.zt == 1 || newest.zt == 5 || newest.zt == 6){
						$("#edit_btn").removeClass("hidden");
						$("#submit_btn").removeClass("hidden");
					}
					if(newest.zt == 4){
						$("#production_btn").removeClass("hidden");
					}
				}else{
					$("#add_btn").removeClass("hidden");
				}
			},
			//表格拼接
			appendContentPlanHtml:function(list){
				var this_ = this;
				var htmlContent = '';
				if(list.length > 0){
					$.each(list,function(index,row){
						var effective = this_.getEffectiveWxfa().bb == row.bb ? "effective-bg-color" : "";
						var newest = (!this_.getEffectiveWxfa().bb || this_.getEffectiveWxfa().bb < row.bb) ? true : false;
						htmlContent +="<tr id='"+row.id+"' class='cursor-pointer "+effective+"' onclick='maintenancePlan.showBottomRightContent(this,\""+row.bb+"\")'>"
						htmlContent += "<td class='text-center'>";
						if(row.fBbid){
							htmlContent += "<a href='javascript:void(0);' style='text-decoration:none;' title='查看差异' onclick='maintenanceItemList.viewSchemeDifference(\""+row.id+"\")'>";
							htmlContent += "<i class='icon-eye-open'></i>";
							htmlContent += "</a>&nbsp;&nbsp;";
						}
						htmlContent += "R"+row.bb+"";
						htmlContent += "</td>";
						htmlContent += "<td>"+(row.sjSxrq?row.sjSxrq.substr(0,10):"")+"</td>";
						if(effective){
							htmlContent += "<td><span class='badge effective-badge badge-lg' >当前生效</span></td>";
						}else if(newest){
							htmlContent += "<td>未生效</td>";
						}else{
							htmlContent += "<td><span class='badge badge-lg'>历史版本</span></td>";
						}
						
						htmlContent +="</tr>"
				   })
				}else{
					htmlContent = "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>"
				}
			    $("#maintenance_plan_version").html(htmlContent);
			},
			showBottomRightContent:function(tr, bb){
				var this_ = this;
				var wxfaid = $(tr).attr("id");
				$(this_.data).each(function(){
					if(wxfaid == this.id){
						this_.wxfa = this;
					}
				});
				$("#maintenance_plan_version .maintenance_bg_tr").removeClass("maintenance_bg_tr");
				$(tr).addClass("maintenance_bg_tr");
				maintenanceItemList.initAcReg();
				maintenanceItemList.reload();
				maintenanceInfo.load();
				maintenanceItemList.switchBtn();
			},
			// 加载飞机机型
			loadFjjx:function(){
				var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : userJgdm});
				$("#fjjx").html("");
				if(typeList.length > 0){
					for(var i = 0; i < typeList.length; i++){
						$("#fjjx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
					}
				}else{
					$("#fjjx").append("<option value=''>暂无飞机机型No data</option>");
				}
			},
			// 初始化验证
			initValidation:function(){
				var this_ = this;
				$('#maintenance_scheme_form').bootstrapValidator({
			        message: '数据不合法',
			        feedbackIcons: {
			            //valid: 'glyphicon glyphicon-ok',
			            invalid: 'glyphicon glyphicon-remove',
			            validating: 'glyphicon glyphicon-refresh'
			        },
			        fields: {
			                       
			        	zwms: {
			                validators: {
			                    notEmpty: {
			                        message: '维修方案描述不能为空'
			                    }
			                }
			            },
			            bb: {
			                validators: {
					            callback: {
			                        message: '版本不能为空',
			                        callback: function(value, validator) {
			                        	var bbNew = $("#maintenance_schedule_modal_bb");
			                        	if(bbNew.is(":visible") && !bbNew.is(":disabled")){
			                        		return value ? true : false;
			                        	}
			                            return true;
			                        }
			                    }
			                }
			            },
			            bbNew: {
			                validators: {
			                	callback: {
			                        message: '新版本不能为空',
			                        callback: function(value, validator) {
			                        	var bbNew = $("#maintenance_schedule_modal_bb_new");
			                        	if(bbNew.is(":visible") && !bbNew.is(":disabled")){
			                        		return value ? true : false;
			                        	}
			                            return true;
			                        }
			                    }
			                }
			            },
			            jhSxrq: {
			                validators: {
			                    notEmpty: {
			                        message: '计划生效日期不能为空'
			                    }
			                }
			            },
			        }
			    });
				$('#maintenance_schedule_modal').on('shown.bs.modal', function() {
					AlertUtil.hideModalFooterMessage();
					$("#maintenance_scheme_form").data('bootstrapValidator').resetForm(false);
			    })
			},
			// 新增维修方案modal
			showAddModal:function(){
				$("#maintenance_schedule_modal input").val("");
				$("#maintenance_schedule_modal textarea").val("");
				$("#maintenance_scheme_form [disabled='disabled']").removeAttr("disabled");
				$("#maintenance_schedule_modal_jx,#maintenance_schedule_modal_wxfabh").attr("disabled","disabled");
				$(".addView,.revisionView,.submitView,.editView1,.editView2").addClass("hidden");
				$(".addView").removeClass("hidden");
				$("#maintenance_schedule_modal_jx").val($("#fjjx").val());
				$("#maintenance_schedule_modal_type").val(1);
				
				ModalUtil.showModal("maintenance_schedule_modal");
				
				var newest = this.data[0]||{};
				var zlid = newest.id;
				if(!zlid){
					zlid = "-";
				}
				evaluation_modal.changeParam({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : true,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : zlid,
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
				evaluation_modal.load();
			},
			// 保存维修方案
			save:function(){
				var this_ = this;
				$('#maintenance_scheme_form').data('bootstrapValidator').validate();
				if(!$('#maintenance_scheme_form').data('bootstrapValidator').isValid()){
					AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
					return false;
				}
				var zwms = $("#maintenance_schedule_modal_zwms").val();
				var bb = $("#maintenance_schedule_modal_bb").val();
				var bz = $("#maintenance_schedule_modal_bz").val();
				var gbyj = $("#maintenance_schedule_modal_gbyj").val();
				var jx = $("#fjjx").val();
				var id = $("#maintenance_schedule_modal_id").val();
				var regu = /^[0-9]+\.?[0-9]{0,2}$/;
				if (!regu.test(bb)) {
					AlertUtil.showModalFooterMessage("版本号只能输入数字,并保留两位小数！");
					return false;
				}
				startWait($("#maintenance_schedule_modal"));
				// 提交数据
				AjaxUtil.ajax({
					url:basePath+"/project2/maintenancescheme/save",
					type:"post",
					data:JSON.stringify({
						id : id,
						zwms : zwms,
						bb : bb,
						jx : jx,
						bz : bz,
						gbyj : gbyj,
						instructionsourceList : this_.getTechnicalParam(),
						dprtcode : userJgdm
					}),
					dataType:"json",
					modal : $("#maintenance_schedule_modal"),
					contentType: "application/json;charset=utf-8",
					success:function(data){
						finishWait($("#maintenance_schedule_modal"));
						AlertUtil.showMessage("保存成功!");
						$("#maintenance_schedule_modal").modal("hide");
						this_.reload();
					}
				});
			},
			// 提交维修方案
			submit:function(){
				var this_ = this;
				$('#maintenance_scheme_form').data('bootstrapValidator').validate();
				if(!$('#maintenance_scheme_form').data('bootstrapValidator').isValid()){
					AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
					return false;
				}
				var zwms = $("#maintenance_schedule_modal_zwms").val();
				var bb = $("#maintenance_schedule_modal_bb").val();
				var bz = $("#maintenance_schedule_modal_bz").val();
				var gbyj = $("#maintenance_schedule_modal_gbyj").val();
				var jx = $("#fjjx").val();
				var id = $("#maintenance_schedule_modal_id").val();
				var regu = /^[0-9]+\.?[0-9]{0,2}$/;
				if (!regu.test(bb)) {
					AlertUtil.showModalFooterMessage("版本号只能输入数字,并保留两位小数！");
					return false;
				}
				
				AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
					startWait($("#maintenance_schedule_modal"));
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/submit",
						type:"post",
						data:JSON.stringify({
							id : id,
							zwms : zwms,
							bb : bb,
							jx : jx,
							bz : bz,
							gbyj : gbyj,
							instructionsourceList : this_.getTechnicalParam(),
							dprtcode : userJgdm
						}),
						dataType:"json",
						modal : $("#maintenance_schedule_modal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#maintenance_schedule_modal"));
							AlertUtil.showMessage("提交成功!");
							$("#maintenance_schedule_modal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
			},
			// 修改维修方案modal
			showEditModal:function(){
				var newest = this.data[0]||{};
				var id = newest.id;
				
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
						if(wxfa.zt != 1 && wxfa.zt != 5 && wxfa.zt != 6){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						$(".addView,.revisionView,.submitView,.editView1,.editView2").addClass("hidden");
						$(".editView1").removeClass("hidden");
						$("#maintenance_scheme_form [disabled='disabled']").removeAttr("disabled");
						$("#maintenance_schedule_modal_jx,#maintenance_schedule_modal_wxfabh").attr("disabled","disabled");
						$("#maintenance_schedule_modal_jx").val(wxfa.jx);
						$("#maintenance_schedule_modal_zwms").val(wxfa.zwms);
						$("#maintenance_schedule_modal_bb").val(wxfa.bb);
						$("#maintenance_schedule_modal_bz").val(wxfa.bz);
						$("#maintenance_schedule_modal_gbyj").val(wxfa.gbyj);
						$("#maintenance_schedule_modal_id").val(wxfa.id);
						$("#maintenance_schedule_modal_wxfabh").val(wxfa.wxfabh);
						$("#maintenance_schedule_modal_type").val(2);
						ModalUtil.showModal("maintenance_schedule_modal");
					}
				});
				
				if(!id){
					id = "-";
				}
				evaluation_modal.changeParam({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : true,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : id,
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
				evaluation_modal.load();
			},
			// 提交维修方案modal
			showSubmitModal:function(){
				var newest = this.data[0]||{};
				var id = newest.id;
				
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
						if(wxfa.zt != 1 && wxfa.zt != 5 && wxfa.zt != 6){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						$(".addView,.revisionView,.submitView,.editView1,.editView2").addClass("hidden");
						$(".editView2").removeClass("hidden");
						$("#maintenance_scheme_form [disabled='disabled']").removeAttr("disabled");
						$("#maintenance_schedule_modal_jx,#maintenance_schedule_modal_wxfabh").attr("disabled","disabled");
						$("#maintenance_schedule_modal_jx").val($("#fjjx").val());
						$("#maintenance_schedule_modal_zwms").val(newest.zwms);
						$("#maintenance_schedule_modal_bb").val(newest.bb);
						$("#maintenance_schedule_modal_bz").val(newest.bz);
						$("#maintenance_schedule_modal_gbyj").val(newest.gbyj);
						$("#maintenance_schedule_modal_id").val(newest.id);
						$("#maintenance_schedule_modal_wxfabh").val(newest.wxfabh);
						$("#maintenance_schedule_modal_type").val(2);
						ModalUtil.showModal("maintenance_schedule_modal");
					}
				});
				
				if(!id){
					id = "-";
				}
				evaluation_modal.changeParam({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : true,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : id,
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
				evaluation_modal.load();
			},
			// 改版维修方案modal
			showRevsionModal:function(){
				var newest = this.data[0];
				var id = newest.id;
				
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
						if(wxfa.bBbid){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						$(".addView,.revisionView,.submitView,.editView1,.editView2").addClass("hidden");
						$(".revisionView").removeClass("hidden");
						$("#maintenance_scheme_form [disabled='disabled']").removeAttr("disabled");
						$("#maintenance_schedule_modal_jx,#maintenance_schedule_modal_wxfabh").attr("disabled","disabled");
						$("#maintenance_schedule_modal_jx").val($("#fjjx").val());
						$("#maintenance_schedule_modal_zwms").val(wxfa.zwms);
						$("#maintenance_schedule_modal_bb").val(wxfa.bb);
						$("#maintenance_schedule_modal_bz").val("");
						$("#maintenance_schedule_modal_gbyj").val("");
						$("#maintenance_schedule_modal_bb_new").val("");
						$("#maintenance_schedule_modal_id").val(id);
						$("#maintenance_schedule_modal_wxfabh").val(wxfa.wxfabh);
						$("#maintenance_schedule_modal_bb").attr("disabled","disabled");
						$("#maintenance_schedule_modal_type").val(3);
						ModalUtil.showModal("maintenance_schedule_modal");
					}
				});
				
				evaluation_modal.changeParam({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : true,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : "-",
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
				evaluation_modal.load();
			},
			// 改版维修方案
			revision:function(){
				$('#maintenance_scheme_form').data('bootstrapValidator').validate();
				if(!$('#maintenance_scheme_form').data('bootstrapValidator').isValid()){
					AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
					return false;
				}
				var this_ = this;
				var zwms = $("#maintenance_schedule_modal_zwms").val();
				var bb_old = $("#maintenance_schedule_modal_bb").val();
				var bb = $("#maintenance_schedule_modal_bb_new").val();
				var bz = $("#maintenance_schedule_modal_bz").val();
				var gbyj = $("#maintenance_schedule_modal_gbyj").val();
				var jx = $("#fjjx").val();
				var id = $("#maintenance_schedule_modal_id").val();
				var wxfabh = $("#maintenance_schedule_modal_wxfabh").val();
				var regu = /^[0-9]+\.?[0-9]{0,2}$/;
				if (!regu.test(bb)) {
					AlertUtil.showModalFooterMessage("版本号只能输入数字,并保留两位小数！");
					return false;
				}
				if(parseFloat(bb) <= parseFloat(bb_old)){
					AlertUtil.showModalFooterMessage("新版本必须大于老版本！");
					return false;
				}
				$('#maintenance_scheme_form').data('bootstrapValidator').validate();
				
				AlertUtil.showConfirmMessage("确定要改版吗？", {callback: function(){
					
					startWait($("#alertConfirmModal"));
					// 提交数据
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/revision",
						type:"post",
						data:JSON.stringify({
							id : id,
							zwms : zwms,
							wxfabh : wxfabh,
							bb : bb,
							jx : jx,
							bz : bz,
							gbyj : gbyj,
							instructionsourceList : this_.getTechnicalParam(),
							dprtcode : userJgdm
						}),
						dataType:"json",
						modal : $("#alertConfirmModal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#alertConfirmModal"));
							AlertUtil.showMessage("改版成功!");
							$("#maintenance_schedule_modal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
			},
			// 直接提交
			doSubmit:function(){
				var this_ = this;
				var wxfa = this_.wxfa;
				AlertUtil.showConfirmMessage("是否确认提交？",{callback:function(){
					startWait();
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/submit",
						type:"post",
						data:JSON.stringify({
							id : $("#maintenance_schedule_modal_id").val(),
							dprtcode : wxfa.dprtcode
						}),
						dataType:"json",
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait();
							AlertUtil.showMessage("提交成功!");
							$("#maintenance_schedule_modal").modal("hide");
							this_.reload();
						}
					});
		  		}});
			},
			// 通知生产确认维修方案modal
			showSubmitProductionModal:function(){
				var newest = this.data[0]||{};
				var id = newest.id;
				
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
						if(wxfa.zt != 4){
							AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
							return false;
						}
						$(".addView,.revisionView,.submitView,.editView1,.editView2").addClass("hidden");
						$(".submitView").removeClass("hidden");
						$("#maintenance_scheme_form [disabled='disabled']").removeAttr("disabled");
						$("#maintenance_schedule_modal_jx,#maintenance_schedule_modal_wxfabh").attr("disabled","disabled");
						$("#maintenance_schedule_modal_jx").val(wxfa.jx);
						$("#maintenance_schedule_modal_zwms").val(wxfa.zwms);
						$("#maintenance_schedule_modal_bb").val(wxfa.bb);
						$("#maintenance_schedule_modal_bz").val(wxfa.bz);
						$("#maintenance_schedule_modal_gbyj").val(wxfa.gbyj);
						$("#maintenance_schedule_modal_id").val(wxfa.id);
						$("#maintenance_schedule_modal_wxfabh").val(wxfa.wxfabh);
						$("#maintenance_schedule_modal_jhSxrq").val("");
						$("#maintenance_schedule_modal_jhSxrq").datepicker("update");
						$("#maintenance_schedule_modal_bb," +
								"#maintenance_schedule_modal_zwms," +
								"#maintenance_schedule_modal_wxfabh," +
								"#maintenance_schedule_modal_gbyj," +
								"#maintenance_schedule_modal_bz").attr("disabled","disabled");
						$("#maintenance_schedule_modal_type").val(4);
						ModalUtil.showModal("maintenance_schedule_modal");
					}
				});
				
				if(!id){
					id = "-";
				}
				evaluation_modal.changeParam({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : false,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					zlid : id,
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
				evaluation_modal.load();
			},
			// 通知生产确认
			doSubmitProduction:function(){
				$('#maintenance_scheme_form').data('bootstrapValidator').validate();
				if(!$('#maintenance_scheme_form').data('bootstrapValidator').isValid()){
					AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
					return false;
				}
				var this_ = this;
				var newest = this.data[0];
				
				AlertUtil.showConfirmMessage("确定要通知生产确认吗？", {callback: function(){
					
					startWait($("#maintenance_schedule_modal"));
					AjaxUtil.ajax({
						url:basePath+"/project2/maintenancescheme/submitproduction",
						type:"post",
						data:JSON.stringify({
							id : newest.id,
							jx : $("#maintenance_schedule_modal_jx").val(),
							jhSxrq : $("#maintenance_schedule_modal_jhSxrq").val(),
							dprtcode : newest.dprtcode
						}),
						dataType:"json",
						modal : $("#maintenance_schedule_modal"),
						contentType: "application/json;charset=utf-8",
						success:function(data){
							finishWait($("#maintenance_schedule_modal"));
							AlertUtil.showMessage("通知生产确认成功!");
							$("#maintenance_schedule_modal").modal("hide");
							this_.reload();
						}
					});
				
				}});
				
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
				/*maintenanceItemList.authHeight();*/
				App.resizeHeight();
			},
			
			// 初始化技术评估单
			initEvaluation:function(){
				evaluation_modal.init({
					parentId : "maintenance_schedule_modal",// 第一层模态框id
					isShowed : true,// 是否显示选择评估单的操作列
					zlxl : 3,// 指令类型
					userId : $("#userId").val(),// 评估单制定人员id
					dprtcode : $("#dprtId").val(),// 组织机构
					jx : $("#fjjx").val(),// 机型
				});
			},
			
			// 获取技术评估单参数
			getTechnicalParam:function(){
				// 技术评估单数据
				var technicalList = evaluation_modal.getData();
				var instructionsourceList = [];
				if (technicalList != null && technicalList != '') {
					// 组装下达指令数据
					$.each(technicalList, function(index, row) {
						var instructionsource = {};
						instructionsource.dprtcode = row.dprtcode;
						instructionsource.pgdid = row.id;
						instructionsource.pgdh = row.pgdh;
						instructionsource.bb = row.bb;
						instructionsource.ywzt = "";
						instructionsourceList.push(instructionsource);
					});
				}
				return instructionsourceList;
			},
			
			//初始化日志
			initLog:function(){
				logModal.init({code:'B_G2_011'});
			}
	}
	
	//只能输入数字和小数,保留两位
	function clearNoNumTwo(obj){
	     //先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     
	     var str = obj.value.split(".");
	     if(str.length > 1){
	    	 if(str[1].length > 2){
	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	}

	//只能输入数字和小数
	function clearNoNum(obj){
	     //先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}
	
	
	
