	
	$(function(){
		appendSpecialCondition();
		$('#timeMonitorModal').on('shown.bs.modal', function (e) {
			initTimeMonitorValidate();
			loadActuallyUsed($("#hidden_timeMonitor").val());
			fillTimeMonitorData();
			var isTsqk = $("#fjzch option:selected").attr("istsqk");
		    if(isTsqk && isTsqk == 1){
		    	$("#specialList_timeMonitor_div").show();
		    }else {
		    	$("#specialList_timeMonitor_div").hide();
		    }
		});
	});
	
	/**
	 * 添加特殊情况设置
	 */
	function appendSpecialCondition(){
		AjaxUtil.ajax({
			   url:basePath+"/productionplan/specialFlightCondition/findAll",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   success:function(data){
				   $("#specialList_timeMonitor").empty();
				   if(data.length >0){
					   $.each(data,function(index,row){
							$("#specialList_timeMonitor")
									  .append(["<tr id='"+(row.id)+"_specialList'>",
							                   "<td>"+(index+1)+"</td>",
							                   "<td>"+row.bh+"</td>",
							                   "<td>"+row.ms+"</td>",
							                   "<td style='padding:3px;'>",
							                   "<input name='xsz' type='text' class='form-control'/>",
							                   "</td>",
							                   "</tr>"
							                   ].join(""));
					   });
				   } else {
					  $("#specialList_timeMonitor").append("<tr><td colspan=\"4\">暂无数据 No data.</td></tr>");
				   }
				   if($("#forwordType").val() == 3){
				    	$("#specialList_timeMonitor input").attr("disabled","disabled");
				   }
		      }
		    }); 
	}
	
	//打开时控件模态框
	function showTimeMonitorModal(btn) {
		$("#hidden_timeMonitor").val($(btn).parent().parent().attr("id"));
		var on = totalData.getDismountRecord($("#hidden_timeMonitor").val()).on;
		if(!on.jh){
			AlertUtil.showErrorMessage("请先填写装上件信息！");
			return false;
		}
		if(!on.kzlx || on.kzlx ==3){
			AlertUtil.showErrorMessage("只有时控件/时寿件才能进行配置！");
			return false;
		}
		$("#timeMonitorModal").modal("show");
	}
	
	/**
	 * 回填时控件数据
	 */
	function fillTimeMonitorData(){
		// 清除上次的数据
		$("input[name='item_timeMonitor']").attr('checked', false);
		$("#items_timeMonitor input[name$='_timeMonitor']").val("");
		var timeArr = ['time_jsfx_well_timeMonitor','time_ssd_well_timeMonitor','time_jc_well_timeMonitor'];
		   $(timeArr).each(function(){
			   $("#"+this+" input[name='timeLimit_timeMonitor']").val(":");
			   $("#"+this+" input[name='nowUsed_timeMonitor']").val(":");
			   $("#"+this+" input[name='beforeUsed_timeMonitor']").val(":");
			   $("#"+this+" input[name='between_timeMonitor']").val(":");
		   });
		$("#items_timeMonitor font[name='remain_timeMonitor']").text("");
		$("#switchYearAndMonthBtn").html('日<span class="caret"></span>');
		$("#switchYearAndMonthBtn").attr("value",'day');
		$("#form_timeMonitor").find("div[id$='_well_timeMonitor']").hide();
		$("input[name='item_timeMonitor']").attr('disabled', false);
		showTimeMonitorWell(null);
		// 特殊情况设置
		$("#specialList_timeMonitor").find("input").val("");
		// 回显数据
		var id = $("#hidden_timeMonitor").val();
		var on = totalData.getDismountRecord(id).on;
		var timeMonitor = totalData.getDismountRecord(id).timeMonitor;
		var on = totalData.getDismountRecord(id).on;
		$("#jh_timeMonitor").val(on.jh);
		$("#xlh_timeMonitor").val(on.xlh);
		$("#zwmc_timeMonitor").val(on.zwmc);
		$("#ywmc_timeMonitor").val(on.ywmc);
		$("#kzlx_timeMonitor").val(on.kzlx);
		$("#wz_timeMonitor").val(on.wz);
		$("#bjlx_timeMonitor").val(1);
		fillInitData(on.wz);
		showCheckBox(on.wz);
		$("#tso_timeMonitor").val(timeMonitor.tso||on.tso);
		$("#tsn_timeMonitor").val(timeMonitor.tsn||on.tsn);
		var settings = timeMonitor.settings;
		if(settings && settings.length > 0){
			$.each(settings, function(i, obj){
				$("#form_timeMonitor").find("#"+obj.jklbh+"_label:visible").click();
				var parent = $("#form_timeMonitor").find("#"+obj.jklbh+"_div");
				parent.find("[name='timeLimit_timeMonitor']").val(obj.gdsx);
				parent.find("[name='nowUsed_timeMonitor']").val(obj.zjyy);
				parent.find("[name='beforeUsed_timeMonitor']").val(obj.bjyy);
				parent.find("[name='expectedRemoval_timeMonitor']").val(obj.bjyc);
				parent.find("[name='between_timeMonitor']").val(obj.zjhyy);
				parent.find("[name='between_timeMonitor']").attr("flag","1");
				calcRemainValue(parent);
				if(obj.jklbh == 'calendar'){
					if(obj.gdsxDw==13){
						$("#switchYearAndMonthBtn").html('年<span class="caret"></span>');
						$("#switchYearAndMonthBtn").attr("value",'year');
					}else if(obj.gdsxDw==12){
						$("#switchYearAndMonthBtn").html('月<span class="caret"></span>');
						$("#switchYearAndMonthBtn").attr("value",'month');
					}else if(obj.gdsxDw==11){
						$("#switchYearAndMonthBtn").html('日<span class="caret"></span>');
						$("#switchYearAndMonthBtn").attr("value",'day');
					}
				}
			});
		}
		$("#items_timeMonitor input[name='nowUsed_timeMonitor']").attr("disabled","disabled");
		$("#calendar_div [name='nowUsed_timeMonitor']").removeAttr("disabled");
		for(obj in actuallyUsed){
			// tb： 累计发生值为0时，允许tb修改
			if(!actuallyUsed[obj] || actuallyUsed[obj] == 0 
				|| actuallyUsed[obj] == '0.0' || actuallyUsed[obj] == '0:0'){
				$("#"+obj+"_div [name='nowUsed_timeMonitor']").removeAttr("disabled");
			}
			else{
				$("#"+obj+"_div [name='nowUsed_timeMonitor']").val(actuallyUsed[obj]||"0");
			}
		}
		// 判断tc和tv是否可改变
		fillTcIfEverDisassembled(on);
		// 根据定检件的tc和tv回填时控件的
		var fixedMonitor = totalData.getDismountRecord(id).fixedMonitor;
		if(fixedMonitor && fixedMonitor.length > 0){
			$.each(fixedMonitor, function(){
				$.each(this.monitorItemList, function(){
					var obj = this;
					var bjyy_input = $("#"+obj.jklbh+"_div").find("[name='beforeUsed_timeMonitor']");
					if(!bjyy_input.val()){
					   bjyy_input.val(obj.bjyy);
					}
					var zjhyy_input = $("#"+obj.jklbh+"_div").find("[name='between_timeMonitor']");
					if(!zjhyy_input.val()){
					   zjhyy_input.val(obj.zjhyy);
					}
					if(on.cj == 1){
					   var zjyy_input = $("#"+obj.jklbh+"_div").find("[name='nowUsed_timeMonitor']");
					   if(!zjyy_input.val()){
						   zjyy_input.val(obj.bjyy);
					   }
					}
				});
			});
		}
	    var conditions = timeMonitor.conditions;
	    if(conditions && conditions.length > 0){
	  	   $.each(conditions, function(i, obj){
	  		   $("#"+obj.tsfxpzid+"_specialList").find("input[name='xsz']").val(obj.xsz);
	  	   });
	    }
	    // 根据层级判断是否显示tc
  	    if(on.cj == 1){
  		   $("#form_timeMonitor div[name='tc_div']").hide();
  	    }else if(on.cj >= 1){
  		   $("#form_timeMonitor div[name='tc_div']").show();
  	    }
  	    // 修改tb描述
	    if(on.cj == 1 || on.cj == 2){
		   $("#items_timeMonitor>div>div[id$='_div']").each(function(){
			   var div = $(this);
			   var jlfdbh = div.attr("id").split("_")[0];
			   var tbDes = tbDescription[on.cj][jlfdbh];
			   tbDes && div.find("div[name='tbDescription']").html(tbDes);
			   var tyDes = tyDescription[on.cj][jlfdbh];
			   tyDes && div.find("div[name='tyDescription']").html(tyDes);
			   var tvDes = tvDescription[on.cj][jlfdbh];
			   tvDes && div.find("div[name='tvDescription']").html(tvDes);
		   });
	    }
	    if($("#forwordType").val() == 3){
	    	$("#form_timeMonitor input").attr("disabled","disabled");
	    }
	}
	
	/**
	 * 填充飞机初始化数据
	 */
	function fillInitData(wz){
		AjaxUtil.ajax({
			   url:basePath+"/productionplan/planeData/queryPlaneData",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   fjzch : $("#fjzch").val(),
				   dprtcode : $("#dprtcode").val()
			   }),
			   success:function(plane){
				   finishWait();
				   $("#calendar_div").find("[name='initDate_timeMonitor']").text(plane.init_date_rq);
				   $("#fuselage_flight_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_jsfx);
				   $("#search_light_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_ssd);
				   $("#winch_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_jc);
				   $("#landing_gear_cycle_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_qlj);
				   $("#winch_cycle_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_jc);
				   $("#ext_suspension_loop_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_wdg);
				   $("#special_first_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_ts1);
				   $("#special_second_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_ts2);
				   if(wz == 1 || wz == 2){
					   $("#N1_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n1 : plane.init_loop_r_n1);
					   $("#N2_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n2 : plane.init_loop_r_n2);
					   $("#N3_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n3 : plane.init_loop_r_n3);
					   $("#N4_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n4 : plane.init_loop_r_n4);
					   $("#N5_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n5 : plane.init_loop_r_n5);
					   $("#N6_div").find("[name='initDate_timeMonitor']").text(wz == 1 ? plane.init_loop_l_n6 : plane.init_loop_r_n6);
				   }
			   }
		});
	}
	
	/**
	 * 判断tc和tv是否可改变
	 */
	function fillTcIfEverDisassembled(ll){
		AjaxUtil.ajax({
			   url:basePath+"/flightdata/flightrecordsheet/loadTcIfEverDisassembled",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   jh : ll.jh,
				   xlh : ll.xlh,
				   fjdid : ll.fjdid,
				   fxjldid : $("#frsId").val(),
				   dprtcode : $("#dprtcode").val()
			   }),
			   success:function(usage){
				   finishWait();
				   if($("#forwordType").val() != 3){
					   $("input[name='beforeUsed_timeMonitor']").removeAttr("disabled");
				   }
				   if(usage){
					   for (var p in usage ){
						   if(ll.cj == 1){
							   $("#"+p+"_div").find("[name='between_timeMonitor']").attr("flag","1");
						   }else if(ll.cj == 2){
							   $("#"+p+"_div").find("[name='beforeUsed_timeMonitor']").val(usage[p]);
							   $("#"+p+"_div").find("[name='beforeUsed_timeMonitor']").attr("disabled","disabled");
							   $("#"+p+"_div").find("[name='between_timeMonitor']").attr("flag","1");
						   }
					   }
				   }
			   }
		});
	}
	
	
	/**
	 * 设置时控件信息
	 */
	function setTimeMonitor(){
		if ($("input[name='item_timeMonitor']:checked").length == 0) {
			AlertUtil.showErrorMessage("请至少选择一个监控项！");
			return false;
		}
		$('#form_timeMonitor').data('bootstrapValidator').validate();
		  if(!$('#form_timeMonitor').data('bootstrapValidator').isValid()){
			return false;
		}
		var id = $("#hidden_timeMonitor").val();
		var data = totalData.getDismountRecord(id);
		var timeMonitor = gatherTimeMonitorParam();
		$.each(timeMonitor.settings, function(i, obj){
			obj.fjzch = $("#fjzch").val();
			delete obj.zjqdid;
		});
		$.each(timeMonitor.conditions, function(i, obj){
			delete obj.zjqdid;
		});
		data.timeMonitor = timeMonitor;
		// 同步定检件的tc和tv
		var fixedMonitor = data.fixedMonitor;
		if(fixedMonitor && fixedMonitor.length > 0){
			$.each(timeMonitor.settings, function(){
				var setting = this;
				$.each(fixedMonitor, function(){
					$.each(this.monitorItemList, function(){
						var monitor = this;
						if(setting.jklbh == monitor.jklbh && setting.jkflbh == monitor.jkflbh){
							monitor.bjyy = setting.bjyy;
							monitor.zjhyy = setting.zjhyy;
						}
					});
				});
			});
		}
		$("#"+id+ " td:nth-child(16) > a").css("color","green").html("<u>已配置</u>");
		$("#timeMonitorModal").modal("hide");
	}
