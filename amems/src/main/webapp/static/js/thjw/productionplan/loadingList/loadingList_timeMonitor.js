


	$(function(){
		//回车搜索
		document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#keyword_search_timeMonitor").is(":focus")){
					getTimeMonitorList();      
				}else if($("#keyword_search_fixedMonitor").is(":focus")){
					getfixedMonitorList();      
				}else if($("#keyword_djxm_fixedMonitor").is(":focus")){
					searchDjxm();      
				}
			}
		}
		// 计算预拆和剩余
		$("#items_timeMonitor").find(".well").each(function(){
			// 修改输入项，重新计算预拆和剩余
			$(this).find("input[name!='expectedRemoval_timeMonitor'][name!='between_timeMonitor']").blur(function(){
				calcRemovalAndRemain($(this).parents().filter("div.well"));
			});
			// 修改预拆，重新计算剩余
			$(this).find("input[name='expectedRemoval_timeMonitor']").blur(function(){
				calcRemainValue($(this).parents().filter("div.well"));
			});
			// 修改装机后-->进入系统前，重新计算剩余
			$(this).find("[name='between_timeMonitor']").blur(function(){
				calcRemainValue($(this).parents().filter("div.well"));
			});
			// 修改该部件装机时,装机后-->进入系统前，验证是否等于飞机初始值
			$(this).find("[name='nowUsed_timeMonitor'],[name='between_timeMonitor']").blur(function(){
				if($("#tablist").length > 0){
					isEqualInitData($(this).parents().filter("div.well"));
				}
			});
		});
		
		$('#calendar_nowUsed_timeMonitor').on('changeDate',function(ev){
			calcRemovalAndRemain($("#calendar_div"));
		});
		
		$('#calendar_expectedRemoval_timeMonitor').on('changeDate',function(ev){
			calcRemainValue($("#calendar_div"));
			$('#form_timeMonitor').data('bootstrapValidator')  
	        .updateStatus($("#calendar_div [name='expectedRemoval_timeMonitor']"), 'NOT_VALIDATED',null)  
	        .validateField($("#calendar_div [name='expectedRemoval_timeMonitor']"));
		});
		
		// 刷新时间控件的验证状态
		$('#calendar_nowUsed_timeMonitor').on('hide', function(e) {
			  $('#form_timeMonitor').data('bootstrapValidator')  
	       .updateStatus('nowUsed_timeMonitor', 'NOT_VALIDATED',null)  
	       .validateField('nowUsed_timeMonitor');  
	    });
		
		// 日历格式验证
		var calendarArr = ['date_rq_well_timeMonitor'];
		$(calendarArr).each(function(){
			$("#"+this+" input[name='timeLimit_timeMonitor']").keyup(function(){
				backspace($(this),/^[0-9]*[1-9][0-9]*$/);
			});
		});
		
		// 时间格式验证
		var timeArr = ['time_jsfx_well_timeMonitor','time_ssd_well_timeMonitor','time_jc_well_timeMonitor'];
		$(timeArr).each(function(){
			TimeUtil.addTimeValidate("#"+this+" input[name='timeLimit_timeMonitor']");
			TimeUtil.addTimeValidate("#"+this+" input[name='nowUsed_timeMonitor']");
			TimeUtil.addTimeValidate("#"+this+" input[name='beforeUsed_timeMonitor']");
			TimeUtil.addTimeValidate("#"+this+" input[name='between_timeMonitor']");
			TimeUtil.addTimeValidate("#"+this+" input[name='expectedRemoval_timeMonitor']");
		});
		
		// 循环格式验证
		var loopArr = ["loop_qlj_well_timeMonitor","loop_jc_well_timeMonitor","loop_wdg_well_timeMonitor"
		                ,"loop_N1_well_timeMonitor","loop_N2_well_timeMonitor","loop_N3_well_timeMonitor"
		                ,'loop_N4_well_timeMonitor','loop_N5_well_timeMonitor','loop_N6_well_timeMonitor'
		                ,'loop_ts1_well_timeMonitor','loop_ts2_well_timeMonitor'];
		$(loopArr).each(function(){
			$("#"+this+" input[name='timeLimit_timeMonitor'],#"+this+" input[name='nowUsed_timeMonitor']," +
					"#"+this+" input[name='beforeUsed_timeMonitor'],#"+this+" input[name='between_timeMonitor']," +
					"#"+this+" input[name='expectedRemoval_timeMonitor']").keyup(function(){
				backspace($(this),/^[0-9]+(\.?[0-9]{0,2})?$/);
			});
		});
		initTimeMonitorValidate();
	    $("#items_timeMonitor input[name='expectedRemoval_timeMonitor']").attr("disabled","disabled");
	    
	    $("#component_timeMonitor>p").on("click",function(){
	    	var childrenDiv = $("#"+$(this).attr("id")+"_part");
	    	if(childrenDiv.find("a").length > 0){
	    		childrenDiv.slideToggle();
	    		$(this).find("i").toggleClass("icon-angle-left").toggleClass("icon-angle-down");
	    	}
	    });
	});
	
	/**
	 * tb描述
	 */
	var tbDescription = {
			1 : {
				search_light_time : "该搜索灯装机时，该搜索灯已用时间",
				winch_time : "该绞车装机时，该绞车已用时间",
				winch_cycle : "该绞车装机时，该绞车已用循环",
				ext_suspension_loop : "该外吊挂装机时，该外吊挂已用循环",
				N1 : "该发动机装机时，该发动机N1已用循环",
				N2 : "该发动机装机时，该发动机N2已用循环",
				N3 : "该发动机装机时，该发动机N3已用循环",
				N4 : "该发动机装机时，该发动机N4已用循环",
				N5 : "该发动机装机时，该发动机N5已用循环",
				N6 : "该发动机装机时，该发动机N6已用循环"
			},
			2 : {
				search_light_time : "该部件装机时，搜索灯时间",
				winch_time : "该部件装机时，绞车时间",
				winch_cycle : "该部件装机时，绞车循环",
				ext_suspension_loop : "该部件装机时，外吊挂循环",
				N1 : "该部件装上发动机时，发动机N1循环",
				N2 : "该部件装上发动机时，发动机N2循环",
				N3 : "该部件装上发动机时，发动机N3循环",
				N4 : "该部件装上发动机时，发动机N4循环",
				N5 : "该部件装上发动机时，发动机N5循环",
				N6 : "该部件装上发动机时，发动机N6循环",
			}
	};
	
	/**
	 * ty描述
	 */
	var tyDescription = {
			1 : {
				search_light_time : "预拆该搜索灯时，搜索灯时间",
				winch_time : "预拆该绞车时，绞车时间",
				winch_cycle : "预拆该绞车时，绞车循环",
				ext_suspension_loop : "预拆该外吊挂时，外吊挂循环",
				N1 : "预拆该发动机装机时，发动机N1循环",
				N2 : "预拆该发动机装机时，发动机N2循环",
				N3 : "预拆该发动机装机时，发动机N3循环",
				N4 : "预拆该发动机装机时，发动机N4循环",
				N5 : "预拆该发动机装机时，发动机N5循环",
				N6 : "预拆该发动机装机时，发动机N6循环"
			},
			2 : {
				search_light_time : "预拆该部件时，搜索灯时间",
				winch_time : "预拆该部件时，绞车时间",
				winch_cycle : "预拆该部件时，绞车循环",
				ext_suspension_loop : "预拆该部件时，外吊挂循环",
				N1 : "预拆该部件时，发动机N1循环",
				N2 : "预拆该部件时，发动机N2循环",
				N3 : "预拆该部件时，发动机N3循环",
				N4 : "预拆该部件时，发动机N4循环",
				N5 : "预拆该部件时，发动机N5循环",
				N6 : "预拆该部件时，发动机N6循环",
			}
	};
	
	/**
	 * tv描述
	 */
	var tvDescription = {
			1 : {
				search_light_time : "该搜索灯从装机后->进入系统前，已用搜索灯时间",
				winch_time : "该绞车从装机后->进入系统前，已用绞车时间",
				winch_cycle : "该绞车从装机后->进入系统前，已用绞车循环",
				ext_suspension_loop : "该外吊挂从装机后->进入系统前，已用外吊挂循环",
				N1 : "该发动机从装机后->进入系统前，已用N1循环",
				N2 : "该发动机从装机后->进入系统前，已用N2循环",
				N3 : "该发动机从装机后->进入系统前，已用N3循环",
				N4 : "该发动机从装机后->进入系统前，已用N4循环",
				N5 : "该发动机从装机后->进入系统前，已用N5循环",
				N6 : "该发动机从装机后->进入系统前，已用N6循环"
			},
			2 : {
				search_light_time : "该部件从装机后->进入系统前，已用搜索灯时间",
				winch_time : "该部件从装机后->进入系统前，已用绞车时间",
				winch_cycle : "该部件从装机后->进入系统前，已用绞车循环",
				ext_suspension_loop : "该部件从装机后->进入系统前，已用外吊挂循环",
				N1 : "该部件从装机后->进入系统前，已用N1循环",
				N2 : "该部件从装机后->进入系统前，已用N2循环",
				N3 : "该部件从装机后->进入系统前，已用N3循环",
				N4 : "该部件从装机后->进入系统前，已用N4循环",
				N5 : "该部件从装机后->进入系统前，已用N5循环",
				N6 : "该部件从装机后->进入系统前，已用N6循环",
			}
	};
	
	/**
	 * 部件实际使用量
	 */
	var actuallyUsed = {
			winch_time : "0",
			fuselage_flight_time : "0",
			search_light_time : "0",
			landing_gear_cycle : "0",
			winch_cycle : "0",
			ext_suspension_loop : "0",
			special_first : "0",
			special_second : "0",
			N1 : "0",
			N2 : "0",
			N3 : "0",
			N4 : "0",
			N5 : "0",
			N6 : "0"
	};
	
	/**
	 * 初始化时控件验证
	 */
	function initTimeMonitorValidate(){
		if($("#form_timeMonitor").data('bootstrapValidator')){
			$("#form_timeMonitor").data('bootstrapValidator').destroy();
			$('#form_timeMonitor').data('bootstrapValidator', null);
		}
		var validatorForm =  $('#form_timeMonitor').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	tsn_timeMonitor: {
	        		validators: {
			            regexp: {
		                    regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
		                    message: 'tsn格式不正确'
		                }
	                }
	            },
	            tso_timeMonitor: {
	            	validators: {
			            regexp: {
		                    regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
		                    message: 'tso格式不正确'
		                }
	                }
	            },
	            expectedRemoval_timeMonitor: {
	            	validators: {
	                	callback: {
	                        message: '该项必须大于0',
	                        callback: function(value, validator) {
	                        	if(value == "" || value.indexOf("-")> 0 ){
	                        		return true;
	                        	}else if(value.indexOf(":")!= -1){
	                        		var left = parseInt(value.split(":")[0]);
	                        		return left >= 0;
	                        	}else if(parseFloat(value) >= 0){
	                        		return true;
	                        	}
	                        	return false;
	                        }
	                    }
	                }
	            },
	            between_timeMonitor: {
	            	validators: {
	            		callback: {
	                        message: '该项必须大于0',
	                        callback: function(value, validator) {
	                        	if(value == ""){
	                        		return true;
	                        	}else if(value.indexOf(":")!= -1){
	                        		return value.indexOf("-") == -1;
	                        	}else if(parseFloat(value) >= 0){
	                        		return true;
	                        	}
	                        	return false;
	                        }
	                    }
	                }
	            },
	            remain_row: {
	            	validators: {
	            		callback: {
	                        message: '该部件装机时数据+装机后-->进入系统前数据应等于飞机初始化值',
	                        callback: function(value, validator) {
	                        	return true;
	                        }
	                    }
	                }
	            },
	        }
	    });
	}
	
	/**
	 * 计算预拆和剩余
	 */
	function calcRemovalAndRemain(parentWell){
		var ta = fillTime(parentWell.find("[name='timeLimit_timeMonitor']").val());
		var tb = fillTime(parentWell.find("[name='nowUsed_timeMonitor']").val());
		var tc = fillTime(parentWell.find("[name='beforeUsed_timeMonitor']").val()||tb);
		var ty = calcTy(ta, tb, tc, parentWell.attr("id"));
		parentWell.find("[name='expectedRemoval_timeMonitor']").val(ty);
		$('#form_timeMonitor').data('bootstrapValidator')  
        .updateStatus(parentWell.find("[name='expectedRemoval_timeMonitor']"), 'NOT_VALIDATED',null)  
        .validateField(parentWell.find("[name='expectedRemoval_timeMonitor']"));
		var td = parentWell.find("[name='initDate_timeMonitor']").text()||0;
		
		var tv = calcTv(tb, td, parentWell.attr("id"));
		// 飞行记录单不计算tv的值
		if($("#forwordType").length == 0){
			parentWell.find("[name='between_timeMonitor']").val(tv);
		}else{
			parentWell.find("[name='between_timeMonitor']").val(0);
		}
		if(parentWell.find("[name='between_timeMonitor']").length > 0){
			$('#form_timeMonitor').data('bootstrapValidator')  
			.updateStatus(parentWell.find("[name='between_timeMonitor']"), 'NOT_VALIDATED',null)  
			.validateField(parentWell.find("[name='between_timeMonitor']"));
		}
		
		var remain = calcRemain(td, ty, parentWell.attr("id"));
		parentWell.find("[name='remain_timeMonitor']").text(remain);
	}
	
	/**
	 * 计算剩余
	 */
	function calcRemainValue(parentWell){
		var tc = fillTime(parentWell.find("[name='beforeUsed_timeMonitor']").val()||parentWell.find("[name='nowUsed_timeMonitor']").val());
		var tv = fillTime(parentWell.find("[name='between_timeMonitor']").val());
		var ty = fillTime(parentWell.find("[name='expectedRemoval_timeMonitor']").val());
		var td = fillTime(parentWell.find("[name='initDate_timeMonitor']").text());
		var remain = calcRemain(td, ty, parentWell.attr("id"));
		parentWell.find("[name='remain_timeMonitor']").text(remain);
	}
	
	/**
	 * 判断该部件装机时+装机后-->进入系统前是否等于飞机初始值
	 */
	function isEqualInitData(parentWell){
		var tb = fillTime(parentWell.find("[name='nowUsed_timeMonitor']").val());
		var tv = fillTime(parentWell.find("[name='between_timeMonitor']").val());
		var td = fillTime(parentWell.find("[name='initDate_timeMonitor']").text());
		var remain = parentWell.find("[name='remain_row']");
		if(parentWell.attr("id").indexOf("calendar") != -1){
			
		}else if(parentWell.attr("id").indexOf("time") != -1){
			if(TimeUtil.operateTime(tb, tv, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD) ==
				TimeUtil.operateTime(td, "0", TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)){
				$('#form_timeMonitor').data('bootstrapValidator').updateStatus(remain, 'VALID',null)
			}else{
				$('#form_timeMonitor').data('bootstrapValidator').updateStatus(remain, 'INVALID',null)
			}
			
		}else{
			if(parseFloat(tb) + parseFloat(tv) == parseFloat(td)){
				$('#form_timeMonitor').data('bootstrapValidator').updateStatus(remain, 'VALID',null)
			}else{
				$('#form_timeMonitor').data('bootstrapValidator').updateStatus(remain, 'INVALID',null)
			}
		}
	}
	
	
	/**
	 * 时间补零
	 * @param value
	 * @returns {String}
	 */
	function fillTime(value){
		if(value && value.indexOf(":") != -1){
			var hour = value.split(":")[0]||"0";
			var minute = value.split(":")[1]||"0";
			// 分钟补零
			if(parseInt(minute) < 10){
				minute = "0" + parseInt(minute);
			}
			value = hour + ":" + minute;
		}
		return value||"0";
	}
	
	
	/**
	 * 计算预拆
	 * @param ta
	 * @param tb
	 * @param tc
	 * @returns {Number}
	 */
	function calcTy(ta, tb, tc, id){
		//日历计算
		if(id.indexOf("calendar") != -1){
			if(!tb || tb == "0"){
				return "";
			}
			var monthOrYear = $("#switchYearAndMonthBtn").attr("value");
			if(monthOrYear == 'year'){
				return addYears(tb, parseInt(ta));
			}else if(monthOrYear == 'month'){
				return addMonths(tb, parseInt(ta));
			}else if(monthOrYear == 'day'){
				return addDays(tb, parseInt(ta));
			}
		} 
		// 时间计算
		else if(id.indexOf("time") != -1){
			var ty = TimeUtil.operateTime(ta, tb, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
			return TimeUtil.operateTime(ty, tc, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
		}
		// 循环计算
		else {
			return (parseFloat(ta)+parseFloat(tb)-parseFloat(tc)).toFixed(2);
		}
	}
	
	/**
	 * 计算剩余
	 * @param ty
	 * @param td
	 * @returns {Number}
	 */
	function calcRemain(td, ty, id){
		id = id.replace(/_div/, "");
		var used = actuallyUsed[id]||'0';
		//日历计算=ty-new Date()
		if(id.indexOf("calendar") != -1){
			if(!ty){
				return 0;
			}
			return dateSubtract(ty, formatDate(new Date()));
		} 
		//时间计算
		else if(id.indexOf("time") != -1){
			// 飞行记录单剩余 = ty - tb
			if($("#forwordType").length > 0){
				return TimeUtil.operateTime(ty, used, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
			}
			// 装机清单剩余 = ty - td
			else{
				return TimeUtil.operateTime(ty, td, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
			}
		}
		//循环计算
		else {
			if($("#forwordType").length > 0){
				return (parseFloat(ty)-parseFloat(used)).toFixed(2);
			}else{
				return (parseFloat(ty)-parseFloat(td)).toFixed(2);
			}
		}
	}
	
	/**
	 * 日期格式转换
	 * @param date
	 * @returns
	 */
	function formatDate(date){
		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	}
	
	/**
	 * 计算tv
	 * @param tc
	 * @param td
	 * @param id
	 * @returns {Number}
	 */
	function calcTv(tb, td, id){
		//日历计算
		if(id.indexOf("time") != -1){
			return TimeUtil.operateTime(td, tb, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
		}
		//循环计算
		else {
			return (parseFloat(td)-parseFloat(tb)).toFixed(2);
		}
		return null;
	}
	
	/**
	 * 获取特殊情况列表
	 */
	function getSpecialSettingList(){
		var searchParam = {zt : 1, fjjx : $("#fjzch_search option:selected").attr("fjjx")};
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/specialFlightCondition/findAll",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
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
						                   "<input name='xsz' type='text' class='form-control input-sm' onkeyup='clearNoNum(this)'/>",
						                   "</td>",
						                   "</tr>"
						                   ].join(""));
				   });
			   } else {
				  $("#specialList_timeMonitor").append("<tr><td colspan=\"4\">暂无数据 No data.</td></tr>");
			   }
			   getTimeMonitorList();
	      }
	    }); 
	}
	
	/**
	 * 获取时控件列表
	 */
	function getTimeMonitorList(id){
		var searchParam = {
				zt : 1, 
				fjzch : $("#fjzch_search").val(),
				dprtcode : $("#dprtcode_head").val(),
				keyword : $("#keyword_search_timeMonitor").val(),
				kzlx : -1
		};
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/queryEditableTimeMonitor",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   $("#component_timeMonitor>div>a").remove();
			   var js_count = 0;
			   var zf_count = 0;
			   var yf_count = 0;
			   var ssd_count = 0;
			   var wdg_count = 0;
			   var jc_count = 0;
			   if(data.length >0){
				   $.each(data,function(index,row){
					   var content = ['<a href="#" id="'+row.id+'_timeMonitor" class="list-group-item" wz="'+row.wz+'" ',
					                  'onclick="loadTimeMonitorDetail(this)" title="'+StringUtil.escapeStr(row.displayName)+'" ',
					                  'style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 25px 5px 15px;">',
					                  '<font>',
					                  StringUtil.escapeStr(row.displayName),
					                  '</font>',
					                  (row.timeMonitorFlag ? '<i class="icon-ok" style="right:10px;position:absolute"></i>':''),
					                  '</a>'
					                  ].join('');
					   $("#component_timeMonitor_"+row.wz+"_part").append(content);
					   if(row.wz == 0){
						   js_count++;
						   $("#component_timeMonitor_0 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 1){
						   zf_count++;
						   $("#component_timeMonitor_1 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 2){
						   yf_count++;
						   $("#component_timeMonitor_2 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 3){
						   jc_count++;
						   $("#component_timeMonitor_3 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 4){
						   ssd_count++;
						   $("#component_timeMonitor_4 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }else if(row.wz == 5){
						   wdg_count++;
						   $("#component_timeMonitor_5 i").removeClass("icon-angle-left").addClass("icon-angle-down");
					   }
				   });
				   signListBykeyword(searchParam.keyword);
				   if(id){
					   $("#"+id+"_timeMonitor").click();
				   }else {
					   $("#component_timeMonitor>div>a:first").click();
				   }
			   }else{
				   $("#items_timeMonitor>div").hide();
				   $("input:checkbox").removeAttr("checked");
			   }
			   $("#timeMonitor_js_count").text(js_count);
			   $("#timeMonitor_zf_count").text(zf_count);
			   $("#timeMonitor_yf_count").text(yf_count);
			   $("#timeMonitor_jc_count").text(jc_count);
			   $("#timeMonitor_ssd_count").text(ssd_count);
			   $("#timeMonitor_wdg_count").text(wdg_count);
	      }
	    }); 
	}
	
	/**
	 * list关键字标红
	 */
	function signListBykeyword(keyword){
		if(keyword){
			$(".list-group-item font").each(function(){
				var value = $(this).text();
				var startIndex = value.indexOf(keyword);
				if(startIndex != -1){
					$(this).html([value.substr(0,startIndex),
					              "<font style='color:red'>",
					              value.substr(startIndex, keyword.length),
					              "</font>",
					              value.substr(parseInt(startIndex) + parseInt(keyword.length))
					              ].join(""));
				}
			});
		}
	}
	
	var kzlxMap = {
			1 : "时控件",
			2 : "时寿件",
			3 : "非控制件"
	};
	
	/**
	 * 加载时控件数据
	 */
	function loadTimeMonitorDetail(obj){
		// 高亮选中行
		$("#component_timeMonitor .list-group-item.active").removeClass("active");
		$(obj).addClass("active");
		var searchParam = {id : $(obj).attr("id").split("_")[0]};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/loadingList/loadTimeMonitorDetail",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   // 装机清单数据
			   var ll = data.loadingList;
			   if(ll){
				   $("#jh_timeMonitor").val(ll.jh);
				   $("#xlh_timeMonitor").val(ll.xlh);
				   $("#zwmc_timeMonitor").val(ll.zwmc);
				   $("#ywmc_timeMonitor").val(ll.ywmc);
				   $("#kzlx_timeMonitor").val(kzlxMap[ll.kzlx]);
				   $("#tsn_timeMonitor").val(ll.tsn);
				   $("#tso_timeMonitor").val(ll.tso);
				   $("#zjqdid_timeMonitor").val(ll.id);
				   $("#bjlx_timeMonitor").val(ll.bjlx);
				   $("#wz_timeMonitor").val(DicAndEnumUtil.getEnumName('planeComponentPositionEnum',ll.wz));
				   // 是否可以选择监控项
				   showCheckBox(ll.wz);
			   }
			   // 飞机初始化数据
			   var plane = data.plane;
			   $("#items_timeMonitor font[name='initDate_timeMonitor']").text("");
			   if(plane){
				   $("#calendar_div").find("[name='initDate_timeMonitor']").text(plane.init_date_rq);
				   $("#fuselage_flight_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_jsfx);
				   $("#search_light_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_ssd);
				   $("#winch_time_div").find("[name='initDate_timeMonitor']").text(plane.init_time_jc);
				   $("#landing_gear_cycle_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_qlj);
				   $("#winch_cycle_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_jc);
				   $("#ext_suspension_loop_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_wdg);
				   $("#special_first_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_ts1);
				   $("#special_second_div").find("[name='initDate_timeMonitor']").text(plane.init_loop_ts2);
				   if(ll.wz == 1 || ll.wz == 2){
					   $("#N1_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n1 : plane.init_loop_r_n1);
					   $("#N2_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n2 : plane.init_loop_r_n2);
					   $("#N3_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n3 : plane.init_loop_r_n3);
					   $("#N4_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n4 : plane.init_loop_r_n4);
					   $("#N5_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n5 : plane.init_loop_r_n5);
					   $("#N6_div").find("[name='initDate_timeMonitor']").text(ll.wz == 1 ? plane.init_loop_l_n6 : plane.init_loop_r_n6);
				   }
			   }
			   // 时控件设置
			   $.each($("#form_timeMonitor").find("div[id$='_well_timeMonitor']"), function(i, obj){
				   $(obj).hide();
			   });
			   $("input[name='between_timeMonitor']").removeAttr("flag");
			   $("input[name='item_timeMonitor']").attr('checked', false);
			   // 重置输入框的值
			   $("#items_timeMonitor input[name$='_timeMonitor']").val("");
			   var timeArr = ['time_jsfx_well_timeMonitor','time_ssd_well_timeMonitor','time_jc_well_timeMonitor'];
			   $(timeArr).each(function(){
				   $("#"+this+" input[name='timeLimit_timeMonitor']").val(":");
				   $("#"+this+" input[name='nowUsed_timeMonitor']").val(":");
				   $("#"+this+" input[name='beforeUsed_timeMonitor']").val(":");
			   });
			   $("#items_timeMonitor font[name='remain_timeMonitor']").text("");
			   var settings = data.settings;
			   $("#switchYearAndMonthBtn").html('日<span class="caret"></span>');
			   $("#switchYearAndMonthBtn").attr("value",'day');
			   showTimeMonitorWell(null);
			   if(settings && settings.length > 0){
				   $.each(settings, function(i, obj){
					   $("input[name='item_timeMonitor']").attr('disabled', false);
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
						   $("#calendar_nowUsed_timeMonitor").datepicker("update");
					   }
				   });
			   }
			   // 判断tc和tv是否可改变
			   var usage = data.usage;
			   $("input[name='nowUsed_timeMonitor']").removeAttr("disabled");
			   $("input[name='beforeUsed_timeMonitor']").removeAttr("disabled");
			   if(usage){
				   for (var p in usage ){
					   if(ll.cj == 1){
						   $("#"+p+"_div").find("[name='nowUsed_timeMonitor']").val(usage[p]);
						   $("#"+p+"_div").find("[name='nowUsed_timeMonitor']").attr("disabled","disabled");
						   $("#"+p+"_div").find("[name='between_timeMonitor']").attr("flag","1");
					   }else if(ll.cj == 2){
						   $("#"+p+"_div").find("[name='beforeUsed_timeMonitor']").val(usage[p]);
						   $("#"+p+"_div").find("[name='beforeUsed_timeMonitor']").attr("disabled","disabled");
						   $("#"+p+"_div").find("[name='between_timeMonitor']").attr("flag","1");
					   }
				   }
			   }
			   // 部件已使用和装机后已用
			   var tcAndTv = data.tcAndTv;
			   if(tcAndTv){
				   $.each(tcAndTv, function(i, obj){
					   var bjyy_input = $("#"+obj.jklbh+"_div").find("[name='beforeUsed_timeMonitor']");
					   if(!bjyy_input.val()){
						   bjyy_input.val(obj.bjyy);
					   }
					   var zjhyy_input = $("#"+obj.jklbh+"_div").find("[name='between_timeMonitor']");
					   if(!zjhyy_input.val()){
						   zjhyy_input.val(obj.zjhyy);
					   }
					   if(ll.cj == 1){
						   var zjyy_input = $("#"+obj.jklbh+"_div").find("[name='nowUsed_timeMonitor']");
						   if(!zjyy_input.val()){
							   zjyy_input.val(obj.bjyy);
						   }
					   }
				   });
			   }
			   // 特殊情况设置
			   $("#specialList_timeMonitor").find("input").each(function(){
				   $(this).val("");
			   });
			   var conditions = data.conditions;
			   if(conditions && conditions.length > 0){
				   $.each(conditions, function(i, obj){
					   $("#"+obj.tsfxpzid+"_specialList").find("input[name='xsz']").val(obj.xsz);
				   });
			   }
			   
			   if(ll){
				   // 根据层级判断是否显示tc
				   if(ll.cj == 1){
					   $("#form_timeMonitor div[name='tc_div']").hide();
				   }else if(ll.cj >= 1){
					   $("#form_timeMonitor div[name='tc_div']").show();
				   }
				   // 修改tb描述
				   if(ll.cj == 1 || ll.cj == 2){
					   $("#items_timeMonitor>div>div[id$='_div']").each(function(){
						   var div = $(this);
						   var jlfdbh = div.attr("id").replace(/_div/, "");
						   var tbDes = tbDescription[ll.cj][jlfdbh];
						   tbDes && div.find("div[name='tbDescription']").html(tbDes);
						   var tyDes = tyDescription[ll.cj][jlfdbh];
						   tyDes && div.find("div[name='tyDescription']").html(tyDes);
						   var tvDes = tvDescription[ll.cj][jlfdbh];
						   tvDes && div.find("div[name='tvDescription']").html(tvDes);
					   });
				   }
			   }
			   initTimeMonitorValidate();
	      }
	    }); 
	}
	
	/**
	 * 显示checkbox
	 * @param wz
	 */
	function showCheckBox(wz){
		$.each($("label.checkbox-inline"), function(i, obj){
			$(obj).hide();
		});
		if(wz==1||wz==2){
		   $.each($("label.checkbox-inline[id^='N']"), function(i, obj){
			   $(obj).show();
		   });
	    }else if(wz==3){
	       $.each($("label.checkbox-inline[id^='winch']"), function(i, obj){
			   $(obj).show();
		   });
	    }else if(wz==4){
    	   $.each($("label.checkbox-inline[id^='search']"), function(i, obj){
			   $(obj).show();
		   });
	    }else if(wz==5){
    	   $.each($("label.checkbox-inline[id^='ext']"), function(i, obj){
			   $(obj).show();
		   });
	    }
		$("#calendar_label").show();
		$("#fuselage_flight_time_label").show();
		$("#landing_gear_cycle_label").show();
		$("#special_first_label").show();
		$("#special_second_label").show();
	}
	
	/**
	 * 显示监控项well
	 * @param id
	 */
	function showTimeMonitorWell(id){
		$("input[name='item_timeMonitor']").attr('disabled', true);
        if ($("input[name='item_timeMonitor']:checked").length >= 3) {
            $("input[name='item_timeMonitor']:checked").attr('disabled', false);
        } else {
            $("input[name='item_timeMonitor']").attr('disabled', false);
        }
		$("#"+id).toggle();
	}
	
	/**
	 * 切换日历中的年和月
	 * @param type
	 */
	function switchYearAndMonth(type){
		if(type=='year'){
			$("#switchYearAndMonthBtn").html('年<span class="caret"></span>');
		}else if(type=='month'){
			$("#switchYearAndMonthBtn").html('月<span class="caret"></span>');
		}else if(type=='day'){
			$("#switchYearAndMonthBtn").html('日<span class="caret"></span>');
		}
		$("#switchYearAndMonthBtn").attr("value",type);
		calcRemovalAndRemain($("#calendar_div"));
	}
	
	/**
	 * 新增或更新时控件监控
	 */
	function insertOrUpdateTimeMonitor(){
		validatePlaneZt();
		if ($("input[name='item_timeMonitor']:checked").length == 0) {
			AlertUtil.showErrorMessage("请至少选择一个监控项！");
			return false;
		}
		$('#form_timeMonitor').data('bootstrapValidator').validate();
		if(!$('#form_timeMonitor').data('bootstrapValidator').isValid()){
			return false;
		}
		var param = gatherTimeMonitorParam();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/modifyTimeMonitorEditable",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(data){
				getTimeMonitorList(param.id);
				AlertUtil.showMessage("时控件设置成功!");
			}
		});
	}
	
	/**
	 * 获取时控件参数
	 */
	function gatherTimeMonitorParam(){
		
		var param = {
				id : $("#zjqdid_timeMonitor").val(),
				tsn : $("#tsn_timeMonitor").val(),
				tso : $("#tso_timeMonitor").val(),
				fjzch : $("#fjzch_search").val(),
				settings : [],
				conditions : []
		};
		$("input[name='item_timeMonitor']:checked").each(function(){
			var label_id = $(this).parent().attr("id");
			var div_id = label_id.replace(/_label/, "_div");
			var zjyyValue = fillTime($("#"+div_id).find("[name='nowUsed_timeMonitor']").val());
			if(div_id.indexOf("calendar") != -1 && zjyyValue == 0){
				zjyyValue = getNowFormatDate();
				$("#"+div_id).find("[name='nowUsed_timeMonitor']").val(zjyyValue)
				$("#calendar_nowUsed_timeMonitor").blur();
			}
			var setting = {
					zjqdid : $("#zjqdid_timeMonitor").val(),
					fjzch : $("#fjzch_search").val(),
					bjlx : $("#bjlx_timeMonitor").val(),
					jh : $("#jh_timeMonitor").val(),
					xlh : $("#xlh_timeMonitor").val(),
					jklbh : $("#"+div_id).find("[name='jklbh']").val(),
					jkflbh : $("#"+div_id).find("[name='jkflbh']").val(),
					pxh : $("#"+div_id).find("[name='pxh']").val(),
					gdsx : fillTime($("#"+div_id).find("[name='timeLimit_timeMonitor']").val()),
					zjyy : zjyyValue,
					bjyy : fillTime($("#"+div_id).find("[name='beforeUsed_timeMonitor']").val()),
					bjyc : fillTime($("#"+div_id).find("[name='expectedRemoval_timeMonitor']").val()),
					zjhyy : fillTime($("#"+div_id).find("[name='between_timeMonitor']").val())
			};
			// 一级部件的tc=tb
			if(!$("#"+div_id).find("[name='beforeUsed_timeMonitor']").is(':visible')){
				setting.bjyy=setting.zjyy;
			}
			setUnit(setting, div_id);
			param.settings.push(setting);
		});
		$("#specialList_timeMonitor").children().each(function(){
			var tr = $(this);
			var tsfxpzid = tr.attr("id");
			tsfxpzid = tsfxpzid.replace(/_specialList/, "");
			var condition = {
					zjqdid : $("#zjqdid_timeMonitor").val(),
					tsfxpzid : tsfxpzid,
					xsz : tr.find("input[name='xsz']").val(),
			};
			if(condition.xsz){
				param.conditions.push(condition);
			}
		});
		return param;
	}
	
	/**
	 * 设置单位
	 */
	function setUnit(setting, id){
		if(id.indexOf("time") != -1){
			setting.gdsxDw = 20;
			setting.zjyyDw = 20;
			setting.bjyyDw = 20;
			setting.bjycDw = 20;
			setting.zjhyyDw = 20;
		}else if(id.indexOf("calendar") != -1){
			var monthOrYear = $("#switchYearAndMonthBtn").attr("value");
			if(monthOrYear == 'year'){
				setting.gdsxDw = 13;
			}else if(monthOrYear == 'month'){
				setting.gdsxDw = 12;
			}else if(monthOrYear == 'day'){
				setting.gdsxDw = 11;
			}
			setting.zjyyDw = 10;
			setting.bjyyDw = 10;
			setting.bjycDw = 10;
			setting.zjhyyDw = 10;
		}else{
			setting.gdsxDw = 30;
			setting.zjyyDw = 30;
			setting.bjyyDw = 30;
			setting.bjycDw = 30;
			setting.zjhyyDw = 30;
		}
	}
	
	/**
	 * 刷新整个时控件监控页面
	 */
	function refreshTimeMonitor(){
		var isTsqk = $("#fjzch_search option:selected").attr("istsqk");
	    if(isTsqk && isTsqk == 1){
	    	$("#specialList_timeMonitor_div").show();
	    }else {
	    	$("#specialList_timeMonitor_div").hide();
	    }
	    getSpecialSettingList();
	}
	
	
	function addDays(date, days){ 
		var d=new Date(date); 
		d.setDate(d.getDate()+days); 
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
	
	function addMonths(date, months){ 
		var d=new Date(date); 
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
	
	function addYears(date, years){ 
		var d=new Date(date); 
		d.setFullYear(d.getFullYear()+years); 
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
	
	function dateSubtract(d1, d2){
		if(d1 && d2){
			var array1 = d1.split("-");
			var date1 = new Date(parseInt(array1[0]), parseInt(array1[1]) - 1, parseInt(array1[2]));
			
			var array2 = d2.split("-");
			var date2 = new Date(parseInt(array2[0]), parseInt(array2[1]) - 1, parseInt(array2[2]));
			return (date1.getTime() - date2.getTime())/(24 * 60 * 60 * 1000);
		}
		return NaN;
	}
	
	/**
	 * 不满足正则则回退
	 * @param obj
	 * @param reg
	 */
	function backspace(obj, reg){
		var value = obj.val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(value);
	}


