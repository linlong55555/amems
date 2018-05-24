	
	$(function(){
		
	});
	
	//打开定检件模态框
	function showFixedMonitorModal(btn) {
		$("#hidden_fixedMonitor").val($(btn).parent().parent().attr("id"));
		var on = totalData.getDismountRecord($("#hidden_fixedMonitor").val()).on;
		if(!on.jh){
			AlertUtil.showErrorMessage("请先填写装上件信息！");
			return false;
		}
		if(!on.isDj || on.isDj == 0){
			AlertUtil.showErrorMessage("只有定检件才能进行配置！");
			return false;
		}
		if(!$("#fxrq").val()){
			AlertUtil.showErrorMessage("请先填写飞行日期！");
			return false;
		}
		$("#fixedMonitorModal").modal("show");
		fillFixedMonitorData();
		if($("#forwordType").val() == 3){
			$("#fixedMonitorList>tr>td>i").hide();
		}
		// 加载部件实际使用值
		loadActuallyUsed($("#hidden_fixedMonitor").val());
	}
	
	/**
	 * 显示新增修改定检项模态框
	 */
	function showfixedMonitorModalFlight(djxmid, djbh, type){
		if(djxmid){
			$("#jdxmid_hidden_fixedMonitor").val(djxmid);
			$("#save_btn_fixedMonitor").attr("flag","upd");
			$("#fixedMonitor_col_5").hide();
			$("#fixedMonitor_col_7").addClass("col-sm-12 padding-left-20").removeClass("col-sm-7 padding-left-0");
			$("#fixedMonitor_width").css("width","1000px");
		}else{
			$("#save_btn_fixedMonitor").attr("flag","ins");
			$("#fixedMonitor_col_5").show();
			$("#fixedMonitor_col_7").addClass("col-sm-7 padding-left-0").removeClass("col-sm-12 padding-left-20");
			$("#keyword_djxm_fixedMonitor").removeAttr("disabled");
			$("#fixedMonitor_width").css("width","1400px");
		}
		$('#fixedMonitor_remoteModal').modal('show');
		$("#fixedCheckedItem_fixedMonitor").empty();
		$("#monitorItem_fixedMonitor").empty();
		$("#modal_jh_fixedMonitor").val($("#jh_fixedMonitor").val());
		$("#modal_xlh_fixedMonitor").val($("#xlh_fixedMonitor").val());
		$("#modal_wz_fixedMonitor").val($("#wz_fixedMonitor").val());
		var on = totalData.getDismountRecord($("#hidden_fixedMonitor").val()).on;
		var searchParam = {
				fjzch : $("#fjzch").val(),
				jh : $("#jh_fixedMonitor").val(),
				xlh : $("#xlh_fixedMonitor").val(),
				fjdid : on.fjdid,
				fxjldid : $("#frsId").val(),
				dprtcode : $("#dprtcode").val()
				};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/flightrecordsheet/loadFixedMonitorItems",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   // 飞机主数据
			   var plane = data.plane;
			   if(plane){
				   $("#fjjx_fixedMonitor").val(plane.fjjx);
				   $("#fjzch_fixedMonitor").val(plane.fjzch);
			   }
			   // 维修方案
			   var maintenance = data.maintenance;
			   if(maintenance){
				   $("#wxfabh_fixedMonitor").val(maintenance.wxfabh);
				   $("#wxfamc_fixedMonitor").val(maintenance.zwms);
				   if(!djxmid) {
					   buildFixedCheckedContent(maintenance.fixedCheckedItems);
				   }
			   }
			   // 回显第一次监控时信息
			   var id = $("#hidden_fixedMonitor").val();
			   var items = totalData.getMonitorItem(id, djbh);
			   if(djxmid){
				   buildFixedCheckedContent([items]);
			   }
			   // 设置飞机当前值
			   setCurrentDatas();
			   // 根据时控件的tc和tv回填定检件的
			   var timeMonitor = totalData.getDismountRecord(id).timeMonitor;
			   setTcAndTv(timeMonitor.settings);
			   // 判断tc和tv是否可改变
			   var usage = data.usage;
			   if(usage){
				   for (var p in usage ){
					   $("td[name='"+p+"_bjyy_monitorItem']>input[name='bjyy_input']").val(usage[p]);
					   $("td[name='"+p+"_bjyy_monitorItem']>input[name='bjyy_input']").attr("disabled","disabled");
				   }
			   }
			   $("#monitorItem_fixedMonitor>tr>td[name='calendar_bjyy_monitorItem']>input").val($("#fxrq").val());
			   if(djxmid){
				   setTcAndTv(items.monitorItemList);
				   $("#monitorItem_fixedMonitor>tr>td[name='calendar_bjyy_monitorItem']>input").val($("#fxrq").val());
				   fillFirstMonitorInfoInputModal([items], djbh);
				   $("#fixedCheckedItem_fixedMonitor tr[name='"+djbh+"_fixedCheckedItem']").click();
			   }
			   if(type==3){
				   $("#fixedMonitor_remoteModal input").attr("disabled","disabled");
				   $("#save_btn_fixedMonitor").hide();
			   }else{
				   $("#save_btn_fixedMonitor").show();
			   }
	      }
	    }); 
	}
	
	/**
	 * 回显第一次监控时信息
	 * @param items
	 * @param id
	 */
	function fillFirstMonitorInfoInputModal(items, djbh){
		$.each(items, function(i, obj){
			if(obj.djbh == djbh){
				$.each(obj.monitorItemList, function(i1, item){
					$("#monitorItem_fixedMonitor [name='"+item.jklbh+"_jkz_monitorItem'] input").val(item.jkSz);
				});
				$("#monitorItem_fixedMonitor [name$='_jkz_monitorItem'] input").blur();
			}
		});
	}
	
	/**
	 * 保存定检监控设置
	 */
	function saveFixedMonitorFlight(){
		var tr = $("#fixedCheckedItem_fixedMonitor tr.selected");
		if(tr.length == 0){
			AlertUtil.showErrorMessage("请先选择定检项目!");
			return false;
		}
		
		if(!validateFixedMonitorForm() || !validateFixedMonitorItem()){
			return false;
		}
		
		// 定检项目参数
		var fixedMonitor = {
				//rownum : $("#fixedMonitorList>tr:not(.non-choice)").length,
				fjzch : $("#fjzch").val(),
				bjlx : 1,
				jh : $("#jh_fixedMonitor").val(),
				xlh : $("#xlh_fixedMonitor").val(),
				nbsbm : $("#nbsbm_fixedMonitor").val(),
				djbh : tr.find("td:nth-child(2)").text(),
				wxfabh : $("#wxfabh_fixedMonitor").val(),
				zwms : tr.find("td:nth-child(3)").text(),
				bb : tr.attr("bb"),
				monitorItemList : []
		};
		// 监控项目参数
		$("#monitorItem_fixedMonitor tr:visible").each(function(){
			var zqz = $(this).find("td:nth-child(2)").html();
			var monitorItem = {
					fjzch : fixedMonitor.fjzch,
					bjlx : fixedMonitor.bjlx,
					jh : fixedMonitor.jh,
					xlh : fixedMonitor.xlh,
					djbh : fixedMonitor.djbh,
					jkflbh : $(this).find("[name='jkflbh']").val(),
					jklbh : $(this).find("[name='jklbh']").val(),
					jkSz : fillTime($(this).find("[name$='_jkz_monitorItem'] input").val()),
					bjyy : fillTime($(this).find("[name$='_bjyy_monitorItem'] input").val()),
					zjhyy : fillTime($(this).find("[name$='_zjhyy_monitorItem'] input").val()),
					ms : $(this).find("td:nth-child(1)").html(),
					jklms : $(this).find("td:nth-child(1)").html(),
					zwms : $(this).find("td:nth-child(1)").html(),
					zqz : zqz.substr(0, zqz.length-1)
			};
			// 设置单位
			setUnit_fixedMonitor(monitorItem, monitorItem.jklbh, $(this).find("[name='dw']").val());
			fixedMonitor.monitorItemList.push(monitorItem);
		});
		// 添加到数据集合中
		var id = $("#hidden_fixedMonitor").val();
		totalData.setMonitorItem(id, fixedMonitor);
		// 刷新定检监控信息
		buildViewTableFlight(totalData.getDismountRecord(id).fixedMonitor);
		// 同步时控件的tc和tv
		var settings = totalData.getDismountRecord(id).timeMonitor.settings;
		if(settings && settings.length > 0){
			var on = totalData.getDismountRecord(id).on;
			$.each([fixedMonitor], function(){
				$.each(this.monitorItemList, function(){
					var monitor = this;
					$.each(settings, function(){
						var setting = this;
						if(setting.jklbh == monitor.jklbh && setting.jkflbh == monitor.jkflbh){
							setting.bjyy = monitor.bjyy;
							setting.zjhyy = monitor.zjhyy;
							// 重新计算预拆
							if(setting.jklbh != 'calendar'){
								setting.bjyc = calcTy(setting.gdsx,setting.zjyy,setting.bjyy,setting.jklbh);
							}
							// 同步tb    -----无需同步tb，飞行记录单tb为自动统计值 -----
							/*if(on.cj == 1 && setting.jklbh != 'fuselage_flight_time' 
								&& setting.jklbh != 'landing_gear_cycle' && setting.jklbh != 'special_first' 
								&& setting.jklbh != 'special_second'){
								setting.zjyy = monitor.bjyy;
							}*/
						}
					});
				});
			});
		}
		$("#"+id+ " td:nth-child(17) > a").css("color","green").html("<u>已配置</u>");
		$("#fixedMonitor_remoteModal").modal("hide");
	}
	
	
	/**
	 * 删除监控信息
	 */
	function deleteFixedMonitorFlight(){
		// 行号
		var rownum = $("#hidden_delete_fixedMonitor_rn").val();
		// 删除total数据
		var row = $("#fixedMonitorList tr:eq("+rownum+")");
		var djbh = row.find("td:nth-child(2)>a").html();
		var id = $("#hidden_fixedMonitor").val();
		totalData.deleteFixedMonitor(id, djbh);
		// 删除table元素 
		row.remove();
		if($("#fixedMonitorList tr").length == 0){
			$("#fixedMonitorList").append("<tr class='non-choice'><td colspan=\"5\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 回填定检件数据
	 */
	function fillFixedMonitorData(){
		// 回显数据
		var id = $("#hidden_fixedMonitor").val();
		var on = totalData.getDismountRecord(id).on;
		var fixedMonitor = totalData.getDismountRecord(id).fixedMonitor;
		
		var on = totalData.getDismountRecord(id).on;
		$("#jh_fixedMonitor").val(on.jh);
		$("#xlh_fixedMonitor").val(on.xlh);
		$("#zwmc_fixedMonitor").val(on.zwmc);
		$("#ywmc_fixedMonitor").val(on.ywmc);
		$("#wz_fixedMonitor").val(on.wz);
		$("#cj_fixedMonitor").val(on.cj);
		buildViewTableFlight(fixedMonitor);
	}
	
	/**
	 * 构造已保存的定检项目table
	 * @param items
	 */
	function buildViewTableFlight(items){
		$("#fixedMonitorList").empty();
		if(items && items.length > 0){
			$.each(items, function(i, row){
				$("#fixedMonitorList").append(["<tr bgcolor='"+(i % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
				                               "<td>",
				                               "<i class='icon-edit color-blue cursor-pointer' onClick='showfixedMonitorModalFlight(\""+row.id+"\",\""+row.djbh+"\")' title='修改定检项目关联 Edit Association'></i>&nbsp;&nbsp;",
				                               "<i class='icon-remove color-blue cursor-pointer' onClick='showDeleteFixedMonitorModalFlight(\""+row.id+"\",\""+row.djbh+"\",\""+i+"\")' title='删除定检项目关联 Delete Association'></i>&nbsp;&nbsp;",
				                               "</td>",
				                               "<td title='"+row.djbh+"'><a href='javascript:void(0);' onclick='showfixedMonitorModalFlight(\""+row.id+"\",\""+row.djbh+"\",\"3\")'>"+row.djbh+"</a></td>",
				                               "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left'>"+StringUtil.escapeStr(row.zwms)+"</td>",
				                               "<td title='"+row.bb+"'>"+row.bb+"</td>",
				                               "<td title='"+assembleFirstMonitorInfo(row.monitorItemList)+"' style='text-align:left'>"+assembleFirstMonitorInfo(row.monitorItemList)+"</td>",
				                               "</tr>"
				                               ].join(""));
			});
		}else {
			$("#fixedMonitorList").append("<tr class='non-choice'><td colspan=\"5\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 设置时控件信息
	 */
	function setFixedMonitor(){
		$("#fixedMonitorModal").modal("hide");
	}
	
	/**
	 * 显示未匹配的定检项目
	 */
	function showUnmatchModalFlight(){
		$("#fjzch_search").val($("#fjzch").val());
		showUnmatchModal();
	}
	
	/**
	 * 删除定检项目模态框
	 * @param id
	 */
	function showDeleteFixedMonitorModalFlight(id, djbh,rn){
		$("#deleteFixedMonitorModal_body").html("是否确认删除定检编号为："+djbh+"的监控信息？");
		$("#hidden_delete_fixedMonitor").val(id);
		$("#hidden_delete_fixedMonitor_rn").val(rn);
		$('#deleteFixedMonitorModal').modal('show');
	}
	
	/**
	 * 设置飞机当前值
	 */
	function setCurrentDatas(){
		for(obj in actuallyUsed){
			$("#monitorItem_fixedMonitor td[name='"+obj+"_init_monitorItem']").text(actuallyUsed[obj]||0);
		}
		$("#monitorItem_fixedMonitor td[name='calendar_init_monitorItem']").text($("#fxrq").val());
	}
