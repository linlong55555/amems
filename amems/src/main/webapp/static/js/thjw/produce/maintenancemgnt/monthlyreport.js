
$(function(){
	monthlyReport.init();
});
	
/**
 * 执管月报
 */
var monthlyReport = {
	
	/**
	 * 页面初始化
	 */
	init : function(){
		
		var this_ = this;
		
		// 初始化事件
		this_.initEvent();
		// 初始化导航栏
		this_.initNavigation();
		// 初始化飞机注册号
		this_.initAcReg();
		// 初始化控件
		this_.initWidget();
		// 初始化数据
		this_.initData();
		// 加载数据
		this_.loadData();
	},
	
	/**
	 * 初始化导航栏
	 */
	initNavigation : function(){
		Navigation(menuCode,"","");
	},
	
	/**
	 * 初始化事件
	 */
	initEvent : function(){
		
		var this_ = this;
		
		$('.panel-collapse').on('show.bs.collapse', function (e) {
			$("#page_body .panel-collapse.in").collapse("hide");
			this_.autoCollapseHeight($(this));
			$(this).siblings("div").find("input.selectCheckbox").prop("checked",true);
		}).on('hide.bs.collapse', function (e) { 
			$(this).siblings("div").find("input.selectCheckbox").prop("checked",false);
		});
		
		$("input[class='selectCheckbox']").click(function(e){
			e = e || window.event;  
			if(e.stopPropagation) { //W3C阻止冒泡方法  
			    e.stopPropagation();  
			} else {  
			    e.cancelBubble = true; //IE阻止冒泡方法  
			} 
	        if($(this).is(":checked")){  
	    	    $(this).parents(".panel-heading").eq(0).siblings(".panel-collapse").collapse('show');
	        }else{  
	        	$(this).parents(".panel-heading").eq(0).siblings(".panel-collapse").collapse('hide');
	        }  
		});
		
	},
	
	/**
	 * 计算折叠div高度
	 */
	autoCollapseHeight : function($active){
		
		var panelBody = $("#page_body").outerHeight()||0
		var worktimeInfo = $(".worktimeInfo").outerHeight()||0;
		var collapseHeight = 0;
		$("#page_body .panel-primary").each(function(){
			var $div = $(this);
			if(!$div.hasClass("in")){
				collapseHeight += ($div.find(".panel-heading").outerHeight()||0) + 1;
			}
		});
		
		if($active.length > 0){
			var activeHeader = $active.find(".panel-heading").outerHeight()||0;
			var activeCollapseHeight = panelBody - worktimeInfo - collapseHeight - 85 - activeHeader;
			if(navigator.userAgent.indexOf("Chrome") > -1){
				activeCollapseHeight -= 10;
	        }
		    $active.find(".panel-body").attr("style","height:"+activeCollapseHeight+"px;overflow:auto;padding-top:0px;padding-bottom:0px;");
		}
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		
		var this_ = this;
		
		// 初始化日期控件
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$('.date-range-picker').daterangepicker().prev().on("click", function() {
			$(this).next().focus();
		});
		
		$('#month').datepicker({
			 maxViewMode: 1,
	         minViewMode:1,
	         format: "yyyy-mm",//选择日期后，文本框显示的日期格式
	         language: "zh-CN", //汉化 
	         autoclose: true,  
	    });
	},
	
	/**
	 * 初始化飞机注册号
	 */
	initAcReg : function(){
		var this_ = this;
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:userJgdm});
	 	var planeRegOption = '';
	 	if(planeDatas != null && planeDatas.length >0){
	 		$.each(planeDatas, function(i, planeData){
 				planeRegOption 
 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' "
 					+ "fjjx='"+StringUtil.escapeStr(planeData.FJJX)+"'>"
 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
 					+ "</option>";
	 		});
	 	}else{
	 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
	 	}
	 	$("#fjzch").html(planeRegOption);
	},
	
	/**
	 * 获取当前月份
	 */
	getCurrentMonth : function(){
		var d = new Date;
		var year= d.getFullYear(); 
		var month = d.getMonth() + 1;
		month =month < 10 ? "0" + month : month;
		return year + "-" + month;
	},
	
	/**
	 * 刷新标题
	 */
	refreshTitle : function(){
		$("#fjzch_title").text($("#fjzch").val()+" ");
		
		var date = $("#month").datepicker("getDate");
		var year= date.getFullYear(); 
		var month = date.getMonth() + 1;
		$("#month_title").text(year+"年"+month+"月");
	},
	
	/**
	 * 初始化数据
	 */
	initData : function(){
		$("#month").val(this.getCurrentMonth());
		this.refreshTitle();
	},
	
	/**
	 * 获取查询参数
	 */
	gatherSearchParam : function(){
		var param = {};
		param.fjzch = $("#fjzch").val();
		param.fjjx = $("#fjzch option:selected").attr("fjjx");
		param.yf = $("#month").val();
		param.dprtcode = userJgdm;
		return param;
	},
	
	/**
	 * 加载数据
	 */
	loadData : function(){
		var this_ = this;
		var obj = this_.gatherSearchParam();
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/produce/maintenancemgnt/querymonthlyreport",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				this_.fillData(data);
			}
		});
	},
	
	/**
	 * 填充数据
	 */
	fillData : function(data){
		this.refreshTitle();
		$("#configId").val(data.config ? data.config.id : "");
		$("#configYf").val(data.config ? data.config.yf : "");
		this.showWorkingHourConfigText();
		// 机身及发动机小时数统计
		var temp = "";
		if(data.hoursStatistics && data.hoursStatistics.length > 0){
			$.each(data.hoursStatistics, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.WZ) + "'>"+ StringUtil.escapeStr(row.WZ)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.FJZCH) + "'>"+ StringUtil.escapeStr(row.FJZCH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.XLH) + "'>"+ StringUtil.escapeStr(row.XLH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.YDSJ) + "'>"+ StringUtil.escapeStr(row.YDSJ)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.YDXH) + "'>"+ StringUtil.escapeStr(row.YDXH)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.SJ) + "'>"+ StringUtil.escapeStr(row.SJ)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.XH) + "'>"+ StringUtil.escapeStr(row.XH)+ "</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>";
		}
		$("#hoursStatisticsList").html(temp);
		
		// 航线例行维修工作
		temp = "";
		if(data.routineWork && data.routineWork.length > 0){
			$.each(data.routineWork, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.FXRQ) + "'>"+ StringUtil.escapeStr(row.FXRQ)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.QFZ) + "'>"+ StringUtil.escapeStr(row.QFZ)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.ZLZ) + "'>"+ StringUtil.escapeStr(row.ZLZ)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GS) + "'>"+ StringUtil.escapeStr(row.GS)+ "</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>";
		}
		$("#routineWorkList").html(temp);
		
		// 航线非例行维修工作
		temp = "";
		if(data.nonRoutineWork && data.nonRoutineWork.length > 0){
			$.each(data.nonRoutineWork, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ (i + 1) + "'>"+ (i + 1)+ "</td>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.FXRQ) + "'>"+ StringUtil.escapeStr(row.FXRQ)+ "</td>";	
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.GDBH) + "'>"+ StringUtil.escapeStr(row.GDBH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GZNR) + "'>"+ StringUtil.escapeStr(row.GZNR)+ "</td>";	
				temp += "<td class='text-center' title='完成'>完成</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>";
		}
		$("#nonRoutineWorkList").html(temp);
		
		// EO/MAO执行情况
		temp = "";
		if(data.eoMaoImplementation && data.eoMaoImplementation.length > 0){
			$.each(data.eoMaoImplementation, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ (i + 1) + "'>"+ (i + 1)+ "</td>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.EOBH) + "'>"+ StringUtil.escapeStr(row.EOBH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.EOZT) + "'>"+ StringUtil.escapeStr(row.EOZT)+ "</td>";	
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.WCZT) + "'>"+ StringUtil.escapeStr(row.WCZT)+ "</td>";	
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>";
		}
		$("#eoMaoImplementationList").html(temp);
		
		// 适航指令,厂家服务通告等评估情况
		temp = "";
		if(data.evaluationSituations && data.evaluationSituations.length > 0){
			$.each(data.evaluationSituations, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.JSWJBH) + "'>"+ StringUtil.escapeStr(row.JSWJBH)+ "</td>";	
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.ZDSJ) + "'>"+ StringUtil.escapeStr(row.ZDSJ)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.PGJG) + "'>"+ StringUtil.escapeStr(row.PGJG)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.BZ) + "'>"+ StringUtil.escapeStr(row.BZ)+ "</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>";
		}
		$("#evaluationSituationsList").html(temp);
		
		// 飞机故障/缺陷监控
		temp = "";
		if(data.faultMonitor && data.faultMonitor.length > 0){
			$.each(data.faultMonitor, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.BLDH) + "'>"+ StringUtil.escapeStr(row.BLDH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GZMS) + "'>"+ StringUtil.escapeStr(row.GZMS)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.BLYY) + "'>"+ StringUtil.escapeStr(row.BLYY)+ "</td>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.BLRQ) + "'>"+ StringUtil.escapeStr(row.BLRQ)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.DQRQ) + "'>"+ StringUtil.escapeStr(row.DQRQ)+ "</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>";
		}
		$("#faultMonitorList").html(temp);
		
		// 非例行维修工作费用
		temp = "";
		if(data.nonRoutinePayment && data.nonRoutinePayment.length > 0){
			$.each(data.nonRoutinePayment, function(i, row){
				temp += "<tr>";
				temp += "<td class='text-center' title='"+ (i + 1) + "'>"+ (i + 1)+ "</td>";
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.FXRQ) + "'>"+ StringUtil.escapeStr(row.FXRQ)+ "</td>";	
				temp += "<td class='text-center' title='"+ StringUtil.escapeStr(row.GDBH) + "'>"+ StringUtil.escapeStr(row.GDBH)+ "</td>";	
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GZNR) + "'>"+ StringUtil.escapeStr(row.GZNR)+ "</td>";
				temp += "<td class='text-left' title='"+ StringUtil.escapeStr(row.SJGS) + "'>"+ StringUtil.escapeStr(row.SJGS)+ "</td>";
				temp += "</tr>";
			});
		}else{
			temp = "<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>";
		}
		$("#nonRoutinePaymentList").html(temp);
		$("#lxgs").text(data.lxgs);
		$("#flxgs").text(data.flxgs);
		$("#flxfy").text(data.flxfy);
		$("#cyhcfy").text(data.cyhcfy);
		$("#cyhcfywb").text(data.cyhcfywb);
	},
	
	/**
	 * 显示工时设置文本
	 */
	showWorkingHourConfigText : function(){
		var configId = $("#configId").val();
		var configYf = $("#configYf").val();
		if(configId && configYf){
			var date = new Date(configYf);
			var year= date.getFullYear(); 
			var month = date.getMonth() + 1;
			var text = "当前使用"+year+"年"+month+"月工时费用设置";
			if(checkBtnPermissions("produce:maintenancemgnt:monthlyreport:01")){
				$("#config_title").html('<a href="javascript:;" style="position:absolute;right:0px;top:10px;text-decoration:underline;" onclick="monthlyReport.setWorkHourConfig()" >'+text+'</a>');
			}else{
				$("#config_title").html('<a href="javascript:;" style="position:absolute;right:0px;top:10px;text-decoration:underline;" onclick="monthlyReport.viewWorkHourConfig()" >'+text+'</a>');
			}
		}else{
			var text = "当前未设置工时费用";
			if(checkBtnPermissions("produce:maintenancemgnt:monthlyreport:01")){
				$("#config_title").html('<a href="javascript:;" style="position:absolute;right:0px;top:10px;text-decoration:underline;color:red;" onclick="monthlyReport.setWorkHourConfig()" >'+text+'</a>');
			}else{
				$("#config_title").html('<span style="position:absolute;right:0px;top:10px;color:red;">'+text+'<span>');
			}
		}
	},
	
	/**
	 * 设置工时费用设置
	 */
	setWorkHourConfig : function(){
		work_hour_config_modal.show({
			id : $("#configId").val(),
			fjzch : $("#fjzch").val(),
			yf : $("#month").val(),
			type : "edit",
	 		dprtcode : userJgdm,//机构代码
	 		callback : function(data){
	 			monthlyReport.loadData();
	 		},
	 	});
	},
	
	/**
	 * 查看工时费用设置
	 */
	viewWorkHourConfig : function(){
		work_hour_config_modal.show({
			id : $("#configId").val(),
			fjzch : $("#fjzch").val(),
			yf : $("#month").val(),
			type : "view",
	 		dprtcode : userJgdm,//机构代码
	 		callback : function(data){
	 			monthlyReport.loadData();
	 		},
	 	});
	},
	
	/**
	 * 导出word
	 */
	exportWord : function(){
		var param = this.gatherSearchParam();
		window.open(basePath+"/produce/maintenancemgnt/exportWord?paramjson="+JSON.stringify(param));
	},
};

function customResizeHeight(bodyHeight, tableHeight){
	$("#page_body").removeAttr("style");
	var panelFooter = $("#page_body").siblings(".panel-footer").outerHeight()||0
	var panelBody = bodyHeight-panelFooter;
	$("#page_body").css({"height":(panelBody+10)+"px","overflow":"auto"});
	monthlyReport.autoCollapseHeight($("#hoursStatisticsCollapsed"));
}

