
	$(function(){
		Navigation(menuCode, '飞行记录本查看', 'View FLB', 'GC-8-1', '韩武', '2017-10-12', '韩武', '2017-10-12');//加载导航栏
		flb_view.init();
	});
	
	var flb_view = {
		
		// 页面初始化
		init : function(){
			var id = $.trim($("#fxjlbid").val());
			this.loadData(id);
			this.loadAttachment(id);
		},
		
		// 禁用所有输入框
		disableAll : function(){
			$(".panel-body input,textarea").attr("disabled","disabled");
			$(".panel-body select,:checkbox").attr("disabled","disabled");
			$(".panel-body .input-group-addon.btn.btn-default").remove();
			$("#installationlist_certificate_table th:first").remove();
			$("#flb_view .readonly-style").removeClass("readonly-style");
			$("[name='flb_anti_ice_table_list']>tr.no-data>td").each(function(){
				var col = parseInt($(this).attr("colspan"));
				$(this).attr("colspan", col - 1);
			});
			$(".notView").remove();
			$(".identifying").remove();
		},
		
		// 加载附件
		loadAttachment : function(id){
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_flb_edit');
			attachmentsObj.show({
				attachHead : true,
				djid:formatUndefine(id),
				fileType:"flb",
		 		colOptionhead : false,
				fileHead : false,
				left_column : false,
			});//显示附件
		},
		
		// 加载数据
		loadData : function(id){
			var this_ = this;
			startWait();
			AjaxUtil.ajax({
				url: basePath+"/produce/flb/detail",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					id : id,
				}),
				success:function(data){
					finishWait();
					this_.fillData(data);
					this_.disableAll();
			    }
			}); 
		},
		
		// 填充数据
		fillData : function(data){
			var this_ = this;
			
			this_.initAcReg("common_fjzch", data.dprtcode);
			
			this_.record = new FlbRecord({
				parentId : "flb_view",
				ope_type : 1,
				win_type : "detail",
				dprtcode : data.dprtcode,
				callback : function(){
					$("#flb_view [name='flb_detail_des']").remove();
					$("#flb_view input").attr("disabled","disabled");
					$("#flb_view button").remove();
					$("#flb_view [name='jz_btn']").attr("disabled","disabled");
					$("#flb_view th:first>div").removeClass("pull-left");
				}
			});
			
			$("#common_id").val(data.id);
			$("#common_fjzch").val(data.fjzch);
			$("#common_fxrq").val((data.fxrq || "").substr(0, 10));
			$("#common_jlbym").val(data.jlbym);
			$("#common_fxjlbh").text(data.fxjlbh);
			$("#dprtcode").val(data.dprtcode);
			
			if(data.zt == 2 || data.zt == 12){
				$("#common_fxrq").attr("disabled","disabled");
			}else{
				$("#common_fxrq").removeAttr("disabled");
			}
			
			if(data.fxjlbh){
				$("#common_fxjlbh_div").show();
			}else{
				$("#common_fxjlbh_div").hide();
			}
			
			// 填充航次数据
			this_.record.fillData(data.flightSheetVoyageList);
			
			// 填充航段数据
			$(data.legs).each(function(i, leg){
				$(leg.finishedWorks).each(function(j, work){
					$(work.disassemblies).each(function(k, rec){
						rec.sgbs = 0;
					});
				});
			});
			flb_work.init({"webuiPopoverParent":"body"});
			flb_work.fillData(data.legs);
			
			$("tbody[name='flb_work_table_list']>tr>td[name='gdbh']").each(function(){
				var $td = $(this);
				console.log($td.text());
				$td.html($td.text());
			});
			
			// 加载故障保留单
			flb_detail.loadFaultReservation();
		},
		
		// 初始化飞机注册号
		initAcReg : function(id, dprtcode){
			var this_ = this;
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
		 	var planeRegOption = '';
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
		 	}else{
		 		planeRegOption = '<option value="">暂无飞机 No plane</option>';
		 	}
		 	if(id){
		 		$("#"+id).html(planeRegOption);
		 	}else{
		 		$("#fjzch").html(planeRegOption);
		 	}
		},
		
	};
	
	