

	$(function(){
		
		/**
		 * 定检项目绑定点击事件
		 */
		$("#fixedCheckedItem_fixedMonitor tr:not(.non-choice)").live("click",function(){
			 $("#fixedCheckedItem_fixedMonitor tr.selected").removeClass("selected");
			 // 行选中
             $(this).addClass("selected").find(":radio").attr("checked","checked");
             
             // 显示对应监控项目
             $("#monitorItem_fixedMonitor").find("tr").hide();
             var id = $(this).attr("id").split("_")[0];
             $("#monitorItem_fixedMonitor").find("tr[name='"+id+"_monitorItem']").show();
             
             // 回填定检编号、定检名称
             $("#djbh_fixedMonitor").val($(this).find("td:eq(1)").attr("title"));
             $("#djmc_fixedMonitor").val($(this).find("td:eq(2)").attr("title"));
	    });
		
		/**
		 * 输入监控值后计算剩余
		 */
		$("#monitorItem_fixedMonitor input").live("blur",function(){
			var brother = $(this).parent().siblings("td[name$='_init_monitorItem']");
			var initData = fillTime(brother.text());
			var current = fillTime($(this).parent().parent().find("input[name='jkz_input']").val());
			var bjyy = fillTime($(this).parent().parent().find("input[name='bjyy_input']").val());
			var zjhyy = fillTime($(this).parent().parent().find("input[name='zjhyy_input']").val());
			var remain = calcRemain_fixMonitor(current, bjyy, zjhyy, initData, $(this).parent().parent().find("td[name$='_jkz_monitorItem']").attr("name"));
			$(this).parent().siblings("td[name='remain_monitorItem']").text(remain);
		});
		
		$("#component_fixedMonitor>p").on("click",function(){
	    	var childrenDiv = $("#"+$(this).attr("id")+"_part");
	    	if(childrenDiv.find("a").length > 0){
	    		childrenDiv.slideToggle();
	    		$(this).find("i").toggleClass("icon-angle-left").toggleClass("icon-angle-down");
	    	}
	    });
		
	});
	
	/**
	 * 计算剩余
	 * @param current
	 * @param init
	 * @param id
	 * @returns
	 */
	function calcRemain_fixMonitor(current, bjyy, zjhyy, init, id){
		id = id.replace(/_jkz_monitorItem/, "");
		$("#monitorItem_fixedMonitor td[name='"+id+"_init_monitorItem']>i").remove();
		// 装机清单页面 且 部件层级为2
		var isCalcWithInitValue = ($("#cj_fixedMonitor").val() == 2 && $("#tablist").length > 0);
		//日历计算
		if(id.indexOf("calendar") != -1){
			if(current != "0"){
				return dateSubtract(current, init||bjyy);
			}
		} 
		// 时间计算
		else if(id.indexOf("time") != -1){
			// 随飞机走的
			if(id == "fuselage_flight_time" || isCalcWithInitValue){
				return TimeUtil.operateTime(current, init, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
			}
			// 随部件走的
			else{
				if(TimeUtil.operateTime(bjyy, zjhyy, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD) != 
					TimeUtil.operateTime(init, "0", TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)){
					$("#monitorItem_fixedMonitor td[name='"+id+"_init_monitorItem']")
							.append('<i class="icon-exclamation text-danger cursor-pointer padding-left-5" title="部件已用与装机后->进入系统前的值和与飞机初始值不一致"></i>');
				}
				var temp = TimeUtil.operateTime(current, bjyy, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
				return TimeUtil.operateTime(temp, zjhyy, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
			}
		}
		// 循环计算
		else {
			// 随飞机走的
			if(id == "landing_gear_cycle" || id == "special_first" || id == "special_second" || isCalcWithInitValue){
				return (parseFloat(current)-parseFloat(init)).toFixed(2);
			}
			// 随部件走的
			else{
				if(parseFloat(bjyy) + parseFloat(zjhyy) != parseFloat(init)){
					$("#monitorItem_fixedMonitor td[name='"+id+"_init_monitorItem']")
							.append('<i class="icon-exclamation text-danger cursor-pointer padding-left-5" title="部件已用与装机后->进入系统前的值和与飞机初始值不一致"></i>');
				}
				return (parseFloat(current)-parseFloat(bjyy)-parseFloat(zjhyy)).toFixed(2);
			}
		}
		return "";
	}
	
	/**
	 * 单位
	 */
	var unitMap = {
			10 : "日历",
			11 : "日",
			12 : "月",
			13 : "年",
			20 : "小时",
			21 : "分钟",
			30 : "循环单位"
	};
	
	var unitMap_r = {
			10 : "M",
			20 : "H",
			30 : "C",
			11 : "D",
			12 : "M",
			13 : "Y"
	};
	
	/**
	 * 获取定检件列表
	 */
	function getfixedMonitorList(id){
		if($("#component_fixedMonitor").length == 0){
			return false;
		}
		var searchParam = {
				zt : 1, 
				fjzch : $("#fjzch_search").val(),
				dprtcode : $("#dprtcode_head").val(),
				keyword : $("#keyword_search_fixedMonitor").val(),
				isDj : 1
		};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/queryEditableFixedMonitor",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   $("#component_fixedMonitor>div>a").remove();
			   var js_count = 0;
			   var zf_count = 0;
			   var yf_count = 0;
			   var ssd_count = 0;
			   var wdg_count = 0;
			   var jc_count = 0;
			   if(data.length >0){
				   $.each(data,function(index,row){
					   var content = ['<a href="#" id="'+row.id+'_fixedMonitor" class="list-group-item" ',
					                  'onclick="loadfixedMonitorDetail(this)" title="'+StringUtil.escapeStr(row.displayName)+'" ',
					                  'style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 25px 5px 15px;">',
					                  '<font>',
					                  StringUtil.escapeStr(row.displayName),
					                  '</font>',
					                  (row.fixMonitorFlag ? '<i class="icon-ok" style="right:10px;position:absolute"></i>':''),
					                  '</a>'
					                  ].join('');
					   $("#component_fixedMonitor_"+row.wz+"_part").append(content);
					   if(row.wz == 0){
						   js_count++;
						   $("#component_fixedMonitor_0 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 1){
						   zf_count++;
						   $("#component_fixedMonitor_1 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 2){
						   yf_count++;
						   $("#component_fixedMonitor_2 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 3){
						   jc_count++;
						   $("#component_fixedMonitor_3 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 4){
						   ssd_count++;
						   $("#component_fixedMonitor_4 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 5){
						   wdg_count++;
						   $("#component_fixedMonitor_5 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }
				   });
				   signListBykeyword(searchParam.keyword);
				   if(id){
					   $("#"+id+"_fixedMonitor").click();
				   }else {
					   loadfixedMonitorDetail($("#component_fixedMonitor>div>a:first"));
				   }
			   }
			   $("#fixedMonitor_js_count").text(js_count);
			   $("#fixedMonitor_zf_count").text(zf_count);
			   $("#fixedMonitor_yf_count").text(yf_count);
			   $("#fixedMonitor_jc_count").text(jc_count);
			   $("#fixedMonitor_ssd_count").text(ssd_count);
			   $("#fixedMonitor_wdg_count").text(wdg_count);
	      }
	    }); 
	}
	
	
	/**
	 * 加载定检件数据
	 */
	function loadfixedMonitorDetail(obj){
		if(!$(obj).attr("id")){
			// 清空原有定检数据
			$("#fixedMonitorList").html("<tr class='non-choice'><td colspan=\"5\">暂无数据 No data.</td></tr>");
			$("#jh_fixedMonitor").val("");
			$("#xlh_fixedMonitor").val("");
			$("#zwmc_fixedMonitor").val("");
			$("#ywmc_fixedMonitor").val("");
			return false;
		}
		// 高亮选中行
		$("#component_fixedMonitor .list-group-item.active").removeClass("active");
		$(obj).addClass("active");
		var searchParam = {id : $(obj).attr("id").split("_")[0]};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/loadFixedMonitorDetail",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   // 装机清单数据
			   var ll = data.loadingList;
			   if(ll){
				   $("#jh_fixedMonitor").val(ll.jh);
				   $("#modal_jh_fixedMonitor").val(ll.jh);
				   $("#xlh_fixedMonitor").val(ll.xlh);
				   $("#modal_xlh_fixedMonitor").val(ll.xlh);
				   $("#zwmc_fixedMonitor").val(ll.zwmc);
				   $("#ywmc_fixedMonitor").val(ll.ywmc);
				   $("#zjqdid_fixedMonitor").val(ll.id);
				   $("#wz_fixedMonitor").val(ll.wz);
				   $("#bjlx_fixedMonitor").val(ll.bjlx);
				   $("#nbsbm_fixedMonitor").val(ll.nbsbm);
				   $("#modal_wz_fixedMonitor").val(ll.wz);
				   $("#cj_fixedMonitor").val(ll.cj);
				   $("#wz_fixedMonitor_show").val(DicAndEnumUtil.getEnumName('planeComponentPositionEnum',ll.wz));
			   }
			   // 定检监控
			   var items = data.items;
			   buildViewTable(items);
	      }
	    }); 
	}
	
	/**
	 * 显示新增修改定检项模态框
	 */
	function showfixedMonitorModal(djxmid, djbh, type){
		if(djxmid){
			$("#jdxmid_hidden_fixedMonitor").val(djxmid);
			$("#save_btn_fixedMonitor").attr("flag","upd");
			$("#fixedMonitor_col_5").hide();
			$("#fixedMonitor_col_7").addClass("col-sm-12 padding-left-20").removeClass("col-sm-7 padding-left-0");
		}else{
			$("#save_btn_fixedMonitor").attr("flag","ins");
			$("#fixedMonitor_col_5").show();
			$("#fixedMonitor_col_7").addClass("col-sm-7 padding-left-0").removeClass("col-sm-12 padding-left-20");
			$("#keyword_djxm_fixedMonitor").removeAttr("disabled");
		}
		$('#fixedMonitor_remoteModal').modal('show');
		$("#fixedCheckedItem_fixedMonitor").empty();
		$("#monitorItem_fixedMonitor").empty();
		var id = $("#component_fixedMonitor .list-group-item.active").attr("id").split("_")[0];
		var fjzch = $("#fjzch_search").val();
		var searchParam = {
				id : id,
				fjzch : fjzch
				};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/loadFixedMonitorItems",
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
			   var items = data.items;
			   if(djxmid){
				   buildFixedCheckedContent(items);
			   }
			   // 设置飞机初始化数据
			   setInitDatas(plane, $("#wz_fixedMonitor").val());
			   // 设置部件已用和装机后已用
			   var tcAndTv = data.tcAndTv;
			   if(tcAndTv){
				   setTcAndTv(tcAndTv);
			   }
			   // 判断tc和tv是否可改变
			   var usage = data.usage;
			   if(usage){
				   for (var p in usage ){
					   $("td[name='"+p+"_bjyy_monitorItem']>input[name='bjyy_input']").val(usage[p]);
					   $("td[name='"+p+"_bjyy_monitorItem']>input[name='bjyy_input']").attr("disabled","disabled");
				   }
			   }
			   if(djxmid){
				   fillFirstMonitorInfoInput(items, djxmid);
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
	function fillFirstMonitorInfoInput(items, id){
		$.each(items, function(i, obj){
			if(obj.id == id){
				$.each(obj.monitorItemList, function(i1, item){
					$("#monitorItem_fixedMonitor [name='"+item.jklbh+"_jkz_monitorItem'] input").val(item.jkSz);
				});
				$("#monitorItem_fixedMonitor [name$='_jkz_monitorItem'] input").blur();
			}
		});
		$("#monitorItem_fixedMonitor td[name='calendar_jkz_monitorItem']>input").datepicker("update");
	}
	
	/**
	 * 构造定检项目table
	 * @param fixedCheckedItems
	 */
	function buildFixedCheckedContent(fixedCheckedItems){
		$.each(fixedCheckedItems, function(i, row){
		    // 维修方案下无可用的定检项目
			if(!row.djbh){
				$("#djbh_fixedMonitor").val("");
				$("#djmc_fixedMonitor").val("");
				$("#fixedCheckedItem_fixedMonitor").append("<tr class='non-choice'><td colspan=\"4\">暂无数据 No data.</td></tr>");
				$("#monitorItem_fixedMonitor").append("<tr class='non-choice'><td colspan=\"7\">暂无数据 No data.</td></tr>");
				return false;
			}
			var content = ["<tr id='"+row.id+"_fixedCheckedItem' name='"+row.djbh+"_fixedCheckedItem' bb='"+row.bb+"'>",
		                   "<td><input type='radio' name='item_fixedMonitor'/></td>",
		                   "<td title='"+row.djbh+"'>"+row.djbh+"</td>",
		                   "<td style='text-align:left;' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>",
		                   "<td style='text-align:left;' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>",
		                   "</tr>"
		                   ].join('');
		    $("#fixedCheckedItem_fixedMonitor").append(content);
		    buildMonitorItemContent(row.monitorItemList, row.id);
		});
		FormatUtil.removeNullOrUndefined();
		$("#fixedCheckedItem_fixedMonitor tr:not(.non-choice):first-child").click();
	}
	
	/**
	 * 构造监控项目table
	 * @param monitorItemList
	 */
	function buildMonitorItemContent(monitorItemList, djxmid){
		$.each(monitorItemList, function(i, row){
			var content = ["<tr name='"+djxmid+"_monitorItem' style='display:none;'>",
		                   "<td>"+row.jklms+"</td>",
		                   "<td>"+row.zqz+(unitMap_r[row.dw]||unitMap_r[row.jkDw]||"")+"</td>",
		                   "<td name='"+row.jklbh+"_jkz_monitorItem'>"+assembleInput(row.jklbh, "jkz")+"</td>",
		                   "<td name='"+row.jklbh+"_bjyy_monitorItem'>"+assembleInput(row.jklbh, "bjyy")+"</td>",
		                   "<td name='"+row.jklbh+"_zjhyy_monitorItem'>"+assembleInput(row.jklbh, "zjhyy")+"</td>",
		                   "<td name='"+row.jklbh+"_init_monitorItem'></td>",
		                   "<td name='remain_monitorItem'></td>",
		                   "<input name='dw' type='hidden' value='"+(row.dw||"")+"'/>",
		                   "<input name='jklbh' type='hidden' value='"+row.jklbh+"'/>",
		                   "<input name='jkflbh' type='hidden'  value='"+row.jkflbh+"'/>",
		                   "</tr>"
		                   ].join('');
		    $("#monitorItem_fixedMonitor").append(content);
		    if(row.jklbh.indexOf("time") != -1 &&
		    		$("#monitorItem_fixedMonitor>tr[name='"+djxmid
			    			+"_monitorItem']>td[name='"+row.jklbh+"_bjyy_monitorItem']>input").attr("disabled") != "disabled"){
		    	TimeUtil.addTimeValidate("#monitorItem_fixedMonitor>tr[name='"+djxmid
		    			+"_monitorItem']>td[name='"+row.jklbh+"_jkz_monitorItem']>input");
		    	TimeUtil.addTimeValidate("#monitorItem_fixedMonitor>tr[name='"+djxmid
		    			+"_monitorItem']>td[name='"+row.jklbh+"_bjyy_monitorItem']>input");
		    	TimeUtil.addTimeValidate("#monitorItem_fixedMonitor>tr[name='"+djxmid
		    			+"_monitorItem']>td[name='"+row.jklbh+"_zjhyy_monitorItem']>input");
		    }
		});
		// 新生成的时间控件要重新初始化操作
		$('input[name$="_input"].date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
		
		// 添加change事件
		$('input[name$="_input"].date-picker').on('changeDate',function(ev){
			var brother = $(this).parent().siblings("td[name$='_init_monitorItem']");
			var initData = brother.text();
			var current = $(this).parent().parent().find("input[name='jkz_input']").val()||0;
			var bjyy = $(this).parent().parent().find("input[name='bjyy_input']").val()||0;
			var zjhyy = $(this).parent().parent().find("input[name='zjhyy_input']").val()||0;
			var remain = calcRemain_fixMonitor(current, bjyy, zjhyy, initData, $(this).parent().parent().find("td[name$='_jkz_monitorItem']").attr("name"));
			$(this).parent().siblings("td[name='remain_monitorItem']").text(remain);
		});
	}
	
	function assembleInput(id, type){
		if(id=="calendar"){
			if(type=="jkz"){
				return "<input type='text' class='form-control input-sm date-picker' style='height:25px;' data-date-format='yyyy-mm-dd' name='"+type+"_input'  maxlength='20'/>";
			}else {
				return "<input type='text' class='form-control input-sm' style='height:25px;' name='"+type+"_input' disabled='disabled'  maxlength='20'/>";
			}
		}else{
			var cj = $("#cj_fixedMonitor").val();
			if(type!="jkz" && cj == 0){
				return "<input type='text' class='form-control input-sm' style='height:25px;' name='"+type+"_input' disabled='disabled' maxlength='20'/>";
			}else {
				return "<input type='text' class='form-control input-sm' style='height:25px;' name='"+type+"_input' maxlength='20'/>";
			}
		}
	}
	
	/**
	 * 设置飞机初始化数据
	 * @param plane
	 * @param wz
	 */
	function setInitDatas(plane, wz){
		if(wz == 1){
			$("#monitorItem_fixedMonitor td[name='N1_init_monitorItem']").text(plane.init_loop_l_n1||0);
			$("#monitorItem_fixedMonitor td[name='N2_init_monitorItem']").text(plane.init_loop_l_n2||0);
			$("#monitorItem_fixedMonitor td[name='N3_init_monitorItem']").text(plane.init_loop_l_n3||0);
			$("#monitorItem_fixedMonitor td[name='N4_init_monitorItem']").text(plane.init_loop_l_n4||0);
			$("#monitorItem_fixedMonitor td[name='N5_init_monitorItem']").text(plane.init_loop_l_n5||0);
			$("#monitorItem_fixedMonitor td[name='N6_init_monitorItem']").text(plane.init_loop_l_n6||0);
		}else if(wz == 2){
			$("#monitorItem_fixedMonitor td[name='N1_init_monitorItem']").text(plane.init_loop_r_n1||0);
			$("#monitorItem_fixedMonitor td[name='N2_init_monitorItem']").text(plane.init_loop_r_n2||0);
			$("#monitorItem_fixedMonitor td[name='N3_init_monitorItem']").text(plane.init_loop_r_n3||0);
			$("#monitorItem_fixedMonitor td[name='N4_init_monitorItem']").text(plane.init_loop_r_n4||0);
			$("#monitorItem_fixedMonitor td[name='N5_init_monitorItem']").text(plane.init_loop_r_n5||0);
			$("#monitorItem_fixedMonitor td[name='N6_init_monitorItem']").text(plane.init_loop_r_n6||0);
		}else {
			$("#monitorItem_fixedMonitor td[name='N1_init_monitorItem']").text(0);
			$("#monitorItem_fixedMonitor td[name='N2_init_monitorItem']").text(0);
			$("#monitorItem_fixedMonitor td[name='N3_init_monitorItem']").text(0);
			$("#monitorItem_fixedMonitor td[name='N4_init_monitorItem']").text(0);
			$("#monitorItem_fixedMonitor td[name='N5_init_monitorItem']").text(0);
			$("#monitorItem_fixedMonitor td[name='N6_init_monitorItem']").text(0);
		}
		$("#monitorItem_fixedMonitor td[name='calendar_init_monitorItem']").text(plane.init_date_rq||getNowFormatDate());
		$("#monitorItem_fixedMonitor td[name='fuselage_flight_time_init_monitorItem']").text(plane.init_time_jsfx||0);
		$("#monitorItem_fixedMonitor td[name='search_light_time_init_monitorItem']").text(plane.init_time_ssd||0);
		$("#monitorItem_fixedMonitor td[name='winch_time_init_monitorItem']").text(plane.init_time_jc||0);
		$("#monitorItem_fixedMonitor td[name='landing_gear_cycle_init_monitorItem']").text(plane.init_loop_qlj||0);
		$("#monitorItem_fixedMonitor td[name='winch_cycle_init_monitorItem']").text(plane.init_loop_jc||0);
		$("#monitorItem_fixedMonitor td[name='ext_suspension_loop_init_monitorItem']").text(plane.init_loop_wdg||0);
		$("#monitorItem_fixedMonitor td[name='search_light_cycle_init_monitorItem']").text(plane.init_loop_ssd||0);
		$("#monitorItem_fixedMonitor td[name='special_first_init_monitorItem']").text(plane.init_loop_ts1||0);
		$("#monitorItem_fixedMonitor td[name='special_second_init_monitorItem']").text(plane.init_loop_ts2||0);
	}
	
	/**
	 * 设置部件已用和装机后已用
	 * @param list
	 */
	function setTcAndTv(list){
		if(list){
			$.each(list, function(i, obj){
				$("#monitorItem_fixedMonitor td[name='"+obj.jklbh+"_bjyy_monitorItem'] input").val(obj.bjyy);
				$("#monitorItem_fixedMonitor td[name='"+obj.jklbh+"_zjhyy_monitorItem'] input").val(obj.zjhyy);
			});
		}
	}
	
	/**
	 * 保存定检项目
	 */
	function saveFixedMonitor(){
		startWait($("#fixedMonitor_remoteModal"));
		var tr = $("#fixedCheckedItem_fixedMonitor tr.selected");
		if(tr.length == 0){
			finishWait($("#fixedMonitor_remoteModal"));
			AlertUtil.showErrorMessage("请先选择定检项目!");
			return false;
		}
		if(!validateFixedMonitorForm() || !validateFixedMonitorItem()){
			finishWait($("#fixedMonitor_remoteModal"));
			return false;
		}
		
		var result = true;
		if($("#cj_fixedMonitor").val() != 0){
			$("#monitorItem_fixedMonitor>tr:visible").each(function(){
				var row = $(this);
				var id = row.find("td[name$='_jkz_monitorItem']").attr("name");
				var init = row.find("td[name$='_init_monitorItem']").text();
				var bjyy = row.find("input[name='bjyy_input']").val()||0;
				var zjhyy = row.find("input[name='zjhyy_input']").val()||0;
				// 日历
				if(id.indexOf("calendar") != -1){
				} 
				// 时间
				else if(id.indexOf("time") != -1){
					if(TimeUtil.operateTime(bjyy, zjhyy, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD) != 
						TimeUtil.operateTime(init, "0", TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)){
						result = false;
					}
				}
				// 循环
				else {
					if(parseFloat(bjyy) + parseFloat(zjhyy) != parseFloat(init)){
						result = false;
					}
				}
			});
		}
		
		if(!result){
			finishWait($("#fixedMonitor_remoteModal"));
			AlertUtil.showErrorMessage("部件已用与装机后->进入系统前的值和与飞机初始值不一致!");
			return false;
		}
		
		// 定检项目参数
		var param = {
				zjqdid : $("#zjqdid_fixedMonitor").val(),
				fjzch : $("#fjzch_search").val(),
				bjlx : $("#bjlx_fixedMonitor").val(),
				jh : $("#jh_fixedMonitor").val(),
				xlh : $("#xlh_fixedMonitor").val(),
				nbsbm : $("#nbsbm_fixedMonitor").val(),
				wxfabh : $("#wxfabh_fixedMonitor").val(),
				djbh : tr.find("td:nth-child(2)").text(),
				zwms : tr.find("td:nth-child(3)").text(),
				monitorItemList : []
		};
		var djxmid = $("#jdxmid_hidden_fixedMonitor").val();
		if(djxmid){
			param.id = djxmid;
		}
		
		// 监控项目参数
		$("#monitorItem_fixedMonitor tr:visible").each(function(){
			var monitorItem = {
					zjqdid : $("#zjqdid_fixedMonitor").val(),
					fjzch : param.fjzch,
					bjlx : param.bjlx,
					jh : param.jh,
					xlh : param.xlh,
					djbh : param.djbh,
					zwms : param.zwms,
					jkflbh : $(this).find("[name='jkflbh']").val(),
					jklbh : $(this).find("[name='jklbh']").val(),
					jkSz : fillTime($(this).find("[name$='_jkz_monitorItem'] input").val()),
					bjyy : fillTime($(this).find("[name$='_bjyy_monitorItem'] input").val()),
					zjhyy : fillTime($(this).find("[name$='_zjhyy_monitorItem'] input").val())
			};
			// 设置单位
			setUnit_fixedMonitor(monitorItem, monitorItem.jklbh, $(this).find("[name='dw']").val());
			param.monitorItemList.push(monitorItem);
		});
		// 判断url为新增或修改
		
		var url_foot = $("#save_btn_fixedMonitor").attr("flag") == "ins" ? "saveFixMonitorEditable" : "updateFixMonitorEditable";
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/" + url_foot,
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#fixedMonitor_remoteModal"),
			contentType: "application/json;charset=utf-8",
			success:function(data){
				$('#fixedMonitor_remoteModal').modal('hide');
				finishWait($("#fixedMonitor_remoteModal"));
				getfixedMonitorList(param.zjqdid);
				AlertUtil.showMessage("定检监控维护成功!");
			}
		});
	}
	
	/**
	 * table验证
	 */
	function validateFixedMonitorForm(){
		var result = true;
		$("#monitorItem_fixedMonitor small").remove();
		$("#monitorItem_fixedMonitor tr:visible").each(function(){
			var tr = $(this);
			var jklbh = tr.find("input[name='jklbh']").val();
			var jkz = tr.find("input[name='jkz_input']").val();
			var bjyy = tr.find("input[name='bjyy_input']").val();
			var zjhyy = tr.find("input[name='zjhyy_input']").val();
			var ms = tr.children().first().text();
			if(jklbh.indexOf("calendar") != -1){
				if(!jkz){
					tr.find("input[name='jkz_input']").val(getNowFormatDate());
				} else if(!isCalendar(jkz)){
					result = false;
					addErrorMsg(tr, "jkz_input", "该项的格式不正确");
				} 
			}else if(jklbh.indexOf("time") != -1){
				/*if(!jkz){
				}else if(!isTime(jkz)){
					result = false;
					addErrorMsg(tr, "jkz_input", "该项的格式不正确");
				}
				if(tr.find("input[name='bjyy_input']:disabled").length == 0){
					if(!bjyy){
					}else if(!isTime(bjyy)){
						result = false;
						addErrorMsg(tr, "bjyy_input", "该项的格式不正确");
					}
					if(!zjhyy){
					}else if(!isTime(zjhyy)){
						result = false;
						addErrorMsg(tr, "zjhyy_input", "该项的格式不正确");
					}
				}*/
			}else {
				if(!jkz){
				}else if(!isLoop(jkz)){
					result = false;
					addErrorMsg(tr, "jkz_input",  "该项的格式不正确");
				}
				if(tr.find("input[name='bjyy_input']:disabled").length == 0){
					if(!bjyy){
					}else if(!isLoop(bjyy)){
						result = false;
						addErrorMsg(tr, "bjyy_input", "该项的格式不正确");
					}
					if(!zjhyy){
					}else if(!isLoop(zjhyy)){
						result = false;
						addErrorMsg(tr, "zjhyy_input", "该项的格式不正确");
					}
				}
			}
		});
		return result;
	}
	
	/**
	 * 根据位置验证监控项目
	 */
	function validateFixedMonitorItem(){
		var result = true;
		var wz = $("#wz_fixedMonitor").val();
		var wzToPartMap = {
			0 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second'],
			1 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second','N1','N2','N3','N4','N5','N6'],
			2 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second','N1','N2','N3','N4','N5','N6'],
			3 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second','winch_time','winch_cycle'],
			4 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second','search_light_time','search_light_cycle'],
			5 : ['calendar','fuselage_flight_time','landing_gear_cycle','special_first','special_second','ext_suspension_loop']
 		};
		var allowedJklbhArr = wzToPartMap[wz];
		$("#monitorItem_fixedMonitor tr:visible input[name='jklbh']").each(function(){
			if($.inArray($(this).val(), allowedJklbhArr) == -1){
				addErrorMsg($(this).parent(), "jkz_input", 
						"部件的位置无法设置该监控项");
				result = false;
			}
		});
		return result;
	}
	
	function addErrorMsg(tr, id, msg){
		tr.find("input[name='"+id+"']")
			.after('<small class="help-block text-left" style="color:#a94442;margin-bottom:0;margin-top:0">'+msg+'</small>');
	}
	
	function isTime(str){
		var reg = /^[0-9]+((\:|\.)[0-5]?[0-9])?$/;
		return reg.test(str);
	}
	
	function isLoop(str){
		var reg = /^[0-9]+(\.[0-9]{1,2})?$/;
		return reg.test(str);
	}
	
	function isCalendar(str){
		var reg =  /^(\d{4})-(0\d{1}|1[0-2])-(0\d{1}|[12]\d{1}|3[01])$/;
		return reg.test(str);
	}
	
	/**
	 * 设置单位
	 */
	function setUnit_fixedMonitor(setting, id, dw){
		if(id.indexOf("time") != -1){
			setting.jkDw = 20;
			setting.bjyyDw = 20;
			setting.zjhyyDw = 20;
		}else if(id.indexOf("calendar") != -1){
			setting.jkDw = dw;
			setting.bjyyDw = 10;
			setting.zjhyyDw = 10;
		}else{
			setting.jkDw = 30;
			setting.bjyyDw = 30;
			setting.zjhyyDw = 30;
		}
	}
	
	/**
	 * 构造已保存的定检项目table
	 * @param items
	 */
	function buildViewTable(items){
		$("#fixedMonitorList").empty();
		if(items && items.length > 0){
			$.each(items, function(i, row){
				var firstMonitorInfo = assembleFirstMonitorInfo(row.monitorItemList);
				$("#fixedMonitorList").append(["<tr bgcolor='"+(i % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
				                               "<td>",
				                               "<i class='icon-edit color-blue cursor-pointer' onClick='showfixedMonitorModal(\""+row.id+"\",\""+row.djbh+"\")' title='修改定检项目关联 Edit Association'></i>&nbsp;&nbsp;",
				                               "<i class='icon-remove color-blue cursor-pointer' onClick='showDeleteFixedMonitorModal(\""+row.id+"\",\""+row.djbh+"\")' title='删除定检项目关联 Delete Association'></i>&nbsp;&nbsp;",
				                               "</td>",
				                               "<td title='"+row.djbh+"'><a href='javascript:void(0);' onclick='showfixedMonitorModal(\""+row.id+"\",\""+row.djbh+"\",\"3\")'>"+row.djbh+"</a></td>",
				                               "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left'>"+StringUtil.escapeStr(row.zwms)+"</td>",
				                               "<td title='"+row.bb+"'>"+row.bb+"</td>",
				                               "<td title='"+firstMonitorInfo+"' style='text-align:left'>"+firstMonitorInfo+"</td>",
				                               "</tr>"
				                               ].join(""));
			});
		}else {
			$("#fixedMonitorList").append("<tr class='non-choice'><td colspan=\"5\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 拼接第一次定检时监控信息
	 * @param items
	 */
	function assembleFirstMonitorInfo(items){
		var result = "";
		$.each(items, function(i, obj){
			if(obj.jkSz){
				result += (obj.ms + "：" + obj.jkSz + "/" + obj.zqz + unitMap_r[obj.jkDw] + ( i==items.length-1?"。":"； "));
			}else {
				result += (obj.ms + "：待配置" + ( i==items.length-1?"。":"； "));
			}
		});
		return result;
	}
	
	/**
	 * 删除定检项目模态框
	 * @param id
	 */
	function showDeleteFixedMonitorModal(id, djbh){
		$("#deleteFixedMonitorModal_body").html("是否确认删除定检编号为："+djbh+"的监控信息？");
		$("#hidden_delete_fixedMonitor").val(id);
		$('#deleteFixedMonitorModal').modal('show');
	}
	
	/**
	 * 删除定检项目
	 */
	function deleteFixedMonitor(){
		var param = {
				id : $("#hidden_delete_fixedMonitor").val(), 
				fjzch : $("#fjzch_search").val(),
				zjqdid : $("#zjqdid_fixedMonitor").val()
				};
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/deleteFixMonitorEditable",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				getfixedMonitorList();
				AlertUtil.showMessage("删除定检监控信息成功!");
			}
		});
	}
	
	/**
	 * 显示未匹配的定检项目
	 */
	function showUnmatchModal(){
		$('#unmathchModal').modal('show');
		var searchParam = {
				fjzch : $("#fjzch_search").val(), 
				dprtcode : $("#dprtcode_head").val()||$("#dprtcode").val()
				};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/findUnmatchItem",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
		    $("#unmatchList").empty();;
		    var maintenance = data.maintenance;
		    if(maintenance){
		   	   $.each(maintenance.fixedCheckedItems, function(i, row){
		   			$("#unmatchList").append(["<tr bgcolor='"+(i % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
		   			                          	   "<td>"+(i+1)+"</td>",
		   			                               "<td title='"+row.djbh+"'>"+row.djbh+"</td>",
		   			                               "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;'>"+StringUtil.escapeStr(row.zwms)+"</td>",
		   			                               "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;'>" + StringUtil.escapeStr(row.bz)+ "</td>",
		   			                               "</tr>"
		   			                               ].join(""));
		   		});
		   	   FormatUtil.removeNullOrUndefined();
		    } else {
		   	  $("#unmatchList").append("<tr><td colspan=\"4\">暂无数据 No data.</td></tr>");
		    }
	      }
	    }); 
	}
	
	/**
	 * 搜索定检项目
	 */
	function searchDjxm(){
		$("#fixedCheckedItem_fixedMonitor tr td font").replaceWith(function(){
			return $(this).text();
		});
		$("#fixedCheckedItem_fixedMonitor tr").hide();
		var keyword = $.trim($("#keyword_djxm_fixedMonitor").val());
		$("#fixedCheckedItem_fixedMonitor tr td").each(function(){
			var td = $(this);
			if(td.text().indexOf(keyword) != -1){
				td.parent().show();
			}
		});
		signByKeyword(keyword, [2,3,4], "#fixedCheckedItem_fixedMonitor>tr>td");
		var firstTr = $("#fixedCheckedItem_fixedMonitor tr:visible");
		if(firstTr.length > 0){
			$("#fixedCheckedItem_fixedMonitor .non-choice").remove();
			$("#monitorItem_fixedMonitor .non-choice").remove();
			firstTr[0].click();
		}else {
			$("#monitorItem_fixedMonitor").find("tr").hide();
			$("#fixedCheckedItem_fixedMonitor").append('<tr class="non-choice"><td colspan="4">暂无数据 No data.</td></tr>');
			$("#monitorItem_fixedMonitor").append('<tr class="non-choice"><td colspan="7">暂无数据 No data.</td></tr>');
		}
	}
	
	/**
	 * 获取当前日期 格式yyyy-MM-dd
	 * @returns {String}
	 */
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	}
	
	
