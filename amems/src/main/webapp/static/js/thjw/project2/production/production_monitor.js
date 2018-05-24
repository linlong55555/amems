

var MonitorUtil = function(param_){
	
	var cycleReg = /^(0|[1-9]\d*)$/;
	
	var timeReg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
	
	var param = {
		itemId : 'production_monitor_item',
		bodyId : 'production_monitor_body',
		isEdit : true,
		obj : [],
		disabled : false,
		callback : function(){}
	};
	
	if(param_){
		$.extend(param, param_);
	}
	
	show();
	
	function show(){
		initMonitor();
		loadData(param.obj);
	}
	
	function initMonitor(obj){
		AjaxUtil.ajax({
			url:basePath+"/component/monitorclass/queryOptionList",
			type:"post",
			data:{},
			dataType:"json",
			success:function(data){
				initMonitorHead(data);
				initMonitorBody(data);
				bindEvent();
				if(typeof(obj)=="function"){
					obj(); 
				}
			}
		});
	}
	
	/**
	 * 初始化监控设置head
	 */
	function initMonitorHead(data){
		var monitorHtml = '';
		monitorHtml += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">';
		monitorHtml += '<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" style="padding-top:4px;">';
		monitorHtml += '监控项';
		monitorHtml += '</label>';
		monitorHtml += '<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">';
		$.each(data,function(index,row){
			monitorHtml += '<label style="margin-right:10px;" name="monitor">';
			monitorHtml += '<input class="monitorItem" type="checkbox" style="vertical-align: -4px;" jkflbh="'+row.jkflbh+'" jklbh="'+row.jklbh+'" jklms="'+row.ms+'">&nbsp;';
			monitorHtml += row.ms;
			monitorHtml += '</label>';
		});
		monitorHtml += '</div>';
		monitorHtml += '</div>';
		$("#"+param.itemId).html(monitorHtml);
	}
	
	/**
	 * 初始化监控设置body
	 */
	function initMonitorBody(data){
		var monitorBody = '';
		$.each(data,function(index,row){
			monitorBody += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" jkflbh="'+row.jkflbh+'" jklbh="'+row.jklbh+'" style="display:none;">';
			
			monitorBody += '<div class="col-lg-1 col-sm-1 col-xs-3 padding-left-0 padding-right-0">';
			monitorBody += '<label style="font-weight:normal;padding-top:8px;" class="col-lg-12 col-sm-12 col-xs-12 text-right padding-left-0 padding-right-0">';
			monitorBody += MonitorUnitUtil.getMonitorName(row.jklbh);
			monitorBody += '</label>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="padding-left-0 padding-right-0">';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">首次</div>';
			monitorBody +='<div class="input-group padding-left-8" style="width:85%;">';
			monitorBody +=buildScInput(row);
			monitorBody +=buildUnit(row,'sc');
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">周期</div>';
			monitorBody +='<div class="input-group padding-left-8" style="width:85%;">';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="8" name="zq">';
			monitorBody +=buildUnit(row,'zq');
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-5 col-sm-5 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">容差</div>';
			monitorBody +='<div class="input-group padding-left-8" style="width:85%;">';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">-</span>';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="8" name="rcq" value="0">';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">/</span>';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">+</span>';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="8" name="rch" value="0">';
			monitorBody +=buildUnit(row,"rc");
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '</div>';
			
			monitorBody += '</div>';
		});
		$("#"+param.bodyId).html(monitorBody);
	}
	
	/**
	 * 生成首次输入框
	 */
	function buildScInput(item){
		if(MonitorUnitUtil.isCal(item.jklbh)){
			return '<input class="form-control input-sm date-picker" data-date-format="yyyy-mm-dd" type="text" maxlength="10" name="sc">';
		}else{
			return '<input class="form-control input-sm" type="text" maxlength="8" name="sc">';
		}
	}
	
	/**
	 * 生成监控项单位
	 */
	function buildUnit(item, type){
		
		var jklbh = item.jklbh;
		var jklObj = MonitorUnitUtil.unitObj[jklbh]||{};
		var html = "";
		
		// 日历-周期、容差
		if(jklObj.subitem){
			if(type != "sc"){
				html +='<div class="input-group-btn" style="min-width:45px;">';
				if(type == "rc"){
					html +='<button value="11" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:34px;padding:0 0 0 0;color: #555!important;background-color:white;">D&nbsp;<span class="caret"></span></button>';
				}else{
					html +='<button value="12" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:34px;padding:0 0 0 0;color: #555!important;background-color:white;">M&nbsp;<span class="caret"></span></button>';
				}
				html +='<ul class="dropdown-menu dropdown-menu-right">';
				html +=' <li><a href="#" value="11" class="calendarUnit">D</a></li>';
				html +=' <li><a href="#" value="12" class="calendarUnit">M</a></li>';
				html +='</ul>';
				html +='</div>';
			}
		}else{
			html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">'+MonitorUnitUtil.getMonitorUnit(jklbh)+'</span>';
		}
		return html;
	}
	
	/**
	 * 添加事件
	 */
	function bindEvent(){
		$("#"+param.itemId+" .monitorItem").click(function(){
			showOrHideMonitor($(this));
		});
		$("#"+param.bodyId+" .calendarUnit").click(function(){
			changeUnit($(this));
		});
		$("#"+param.bodyId+">div").keyup(function(){
			validateInput($(this));
		});
		$("#"+param.bodyId+" .date-picker").datepicker({
			autoclose : true,
			clearBtn : true
		});
	}
	
	/**
	 * 输入框验证
	 */
	function validateInput(div){
		var jklbh = div.attr("jklbh");
		if(MonitorUnitUtil.isTime(jklbh)){
			backspace(div.find("input[name='sc']"), timeReg, true);
			backspace(div.find("input[name='zq']"), timeReg, true);
			backspace(div.find("input[name='rcq']"), timeReg, true);
			backspace(div.find("input[name='rch']"), timeReg, true);
		}else if(MonitorUnitUtil.isLoop(jklbh)){
			backspace(div.find("input[name='sc']"), cycleReg);
			backspace(div.find("input[name='zq']"), cycleReg);
			backspace(div.find("input[name='rcq']"), cycleReg);
			backspace(div.find("input[name='rch']"), cycleReg);
		}else{
			backspace(div.find("input[name='zq']"), cycleReg);
			backspace(div.find("input[name='rcq']"), cycleReg);
			backspace(div.find("input[name='rch']"), cycleReg);
		}
	}
	
	function backspace(obj, reg, replace){
		var value = obj.val();
		if(replace){
			value = value.replace(/：/g, ":");
		}
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(value);
	}
	
	/**
	 * 验证监控项必填
	 */
	function validateMonitorItem(){
		var msg = "";
		
		if(getSelectMonitorNum() == 0){
			return "请至少选择一个监控项！";
		}
		
		var containsScz = false;
		var hasRecord = false;
		$("#"+param.bodyId+">div:visible").each(function(){
			var row = $(this);
			var jklbh = row.attr("jklbh");
			hasRecord = true;
			var code = MonitorUnitUtil.getMonitorName(jklbh);
			
			var scz = row.find("input[name='sc']").val();
			if(!validateByType(scz, jklbh)){
				msg = code + "的首次值格式不正确！";
				return false;
			}
			
			var zqz = row.find("input[name='zq']").val();
			if(!validateByType(zqz, jklbh)){
				msg = code + "的周期值格式不正确！";
				return false;
			}
			
			var rcq = row.find("input[name='rcq']").val();
			if(!validateByType(rcq, jklbh)){
				msg = code + "的容差值格式不正确！";
				return false;
			}
			var rch = row.find("input[name='rch']").val();
			if(!validateByType(rch, jklbh)){
				msg = code + "的容差值格式不正确！";
				return false;
			}
			
			if(!scz && !zqz){
				msg = code + "的首次值/周期值至少填写一个！";
				return false;
			}
			if(scz){
				containsScz = true;
			}
		});
		if(hasRecord && !msg && !containsScz){
			msg = "监控项首次值至少填写一个！";
		}
		return msg;
		
		function validateByType(val, jklbh){
			if(MonitorUnitUtil.isTime(jklbh)){
				if(val && (!timeReg.test(val) || val.substr(val.length-1,1)== ":")){
					return false;
				}
			}else if(MonitorUnitUtil.isLoop(jklbh)){
				if(val && !cycleReg.test(val)){
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 日历单位改变
	 */
	function changeUnit(obj){
		var btn = obj.parent().parent().prev();
		btn.attr("value", obj.attr("value"));
		btn.html(obj.text()+'<span class="caret"></span>');
	}
	
	/**
	 * 显示/隐藏监控项
	 */
	function showOrHideMonitor(checkbox){
		setCheckDisabled();
		if(checkbox.is(':checked')){
			$("#"+param.bodyId+" div[jklbh='"+checkbox.attr("jklbh")+"']").show();
		}else{
			$("#"+param.bodyId+" div[jklbh='"+checkbox.attr("jklbh")+"']").hide();
		}
		var showEngine = false;
		$.each(getSelectMonitorItems(), function(i, item){
			var jklbh = $(item).attr("jklbh");
			if(jklbh == "2_30_EH" || jklbh == "3_30_EC"){
				showEngine = true;
			}
		});
		if(showEngine){
			$("#wz_div").show();
		}else{
			$("#wz_div").hide();
		}
	}
	
	/**
	 * 获取选中的监控项数目
	 */
	function getSelectMonitorNum(){
		return getSelectMonitorItems().length;
	}

	/**
	 * 获取选中的监控项
	 */
	function getSelectMonitorItems(){
		return $("#"+param.itemId+' input.monitorItem:checkbox:checked');
	}
	
	/**
	 * 获取所有的监控项数目
	 */
	function getMonitorItems(){
		return $("#"+param.itemId+' input.monitorItem:checkbox');
	}
	
	/**
	 * 启用/禁用checkbox
	 */
	function setCheckDisabled(){
		getMonitorItems().attr("disabled","true");
		if(!param.disabled){
			if(getSelectMonitorNum() >= 3){
				getSelectMonitorItems().removeAttr("disabled");
			}else{
				getMonitorItems().removeAttr("disabled");
			}
		}
		var showEngine = false;
		$.each(getSelectMonitorItems(), function(i, item){
			var jklbh = $(item).attr("jklbh");
			if(jklbh == "2_30_EH" || jklbh == "3_30_EC"){
				showEngine = true;
			}
		});
		if(showEngine){
			$("#wz_div").show();
		}else{
			$("#wz_div").hide();
		}
		
		if(param.disabled){
			$("#production_monitor_body button>span").hide();
			$("#production_monitor_body button").attr("disabled", "disabled").css("cssText", "min-width:45px;height:34px;padding:0 0 0 0;color: #000!important;background-color:white;");
			$("#production_monitor_body input").attr("disabled", "disabled");
		}else{
			$("#production_monitor_body button>span").show();
			$("#production_monitor_body button").removeAttr("disabled").css("cssText", "min-width:45px;height:34px;padding:0 0 0 0;color: #555!important;background-color:white;");
			$("#production_monitor_body input").removeAttr("disabled");
		}
	}

	/**
	 * 清除数据
	 */
	function clearData(){
		getMonitorItems().removeAttr("disabled");
		$("#"+param.itemId+" .monitorItem").removeAttr("checked");
		$("#"+param.bodyId+">div").hide();
		$("#"+param.bodyId).find("input[name='sc'],input[name='zq']").val("");
		$("#"+param.bodyId).find("input[name='rcq'],input[name='rch']").val("0");
		$("#"+param.bodyId+">[jklbh='1_10']").find("button[name='sc_dw'],button[name='zq_dw']")
			.attr("value", "12").html('M<span class="caret"></span>');
		$("#"+param.bodyId+">[jklbh='1_10']").find("button[name='rc_dw']")
		.attr("value", "11").html('D<span class="caret"></span>');
		
	}
	
	/**
	 * 获取监控项参数
	 */
	function getMonitorSettingParam(){
		var monitorItemList = [];
		
		var monitorItems = getSelectMonitorItems();
		
		for(var i = 0 ; i < monitorItems.length ; i++){
			var jklbh = $(monitorItems[i]).attr('jklbh');
			var jkflbh = $(monitorItems[i]).attr('jkflbh');
			var jklms = $(monitorItems[i]).attr('jklms');
			var row = $("#"+param.bodyId+">div[jklbh='"+jklbh+"'][jkflbh='"+jkflbh+"']");
			var obj = {
					jklbh : jklbh,
					jkflbh : jkflbh,
					scz : row.find("input[name='sc']").val(),
					dwScz : getUnit(row, jklbh, "sc"),
					zqz : row.find("input[name='zq']").val(),
					dwZqz : getUnit(row, jklbh, "zq"),
					ydzQ :row.find("input[name='rcq']").val()||0,
					ydzQdw : getUnit(row, jklbh, "rc"),
					ydzH :row.find("input[name='rch']").val()||0,
					ydzHdw : getUnit(row, jklbh, "rc"),
			};
			monitorItemList.push(obj);
		}
		return monitorItemList;
	}
	
	/**
	 * 获取监控类单位
	 */
	function getUnit(row, jklbh, type){
		
		return MonitorUnitUtil.getMonitorValue(jklbh, $.trim(row.find("button[name='"+type+"_dw']").text()));
		
	}
	
	/**
	 * 回显数据
	 */
	function loadData(list){
		
		clearData();
		list && $.each(list, function(i, row){
			$("#"+param.itemId+" .monitorItem[jklbh='"+row.jklbh+"']").attr("checked",true);
			$("#"+param.bodyId+" div[jklbh='"+row.jklbh+"']").show();
			var jkRow = $("#"+param.bodyId+">div[jklbh='"+row.jklbh+"']");
			
			jkRow.find("input[name='sc']").val(row.scz);
			jkRow.find("input[name='zq']").val(row.zqz);
			jkRow.find("input[name='rcq']").val(row.ydzQ||0);
			jkRow.find("input[name='rch']").val(row.ydzH||0);
			
			if(row.dwScz == 11){
				jkRow.find("button[name='sc_dw']").attr("value", 11).html("D<span class='caret'></span>");
			}else if(row.dwScz == 12){
				jkRow.find("button[name='sc_dw']").attr("value", 12).html("M<span class='caret'></span>");
			}
			
			if(row.dwZqz == 11){
				jkRow.find("button[name='zq_dw']").attr("value", 11).html("D<span class='caret'></span>");
			}else if(row.dwZqz == 12){
				jkRow.find("button[name='zq_dw']").attr("value", 12).html("M<span class='caret'></span>");
			}
			
			if(row.ydzQdw == 11){
				jkRow.find("button[name='rc_dw']").attr("value", 11).html("D<span class='caret'></span>");
			}else if(row.ydzQdw == 12){
				jkRow.find("button[name='rc_dw']").attr("value", 12).html("M<span class='caret'></span>");
			}
		});
			
		setCheckDisabled();
	}
	
	/**
	 * 获取部件参数
	 */
	function getComponent(){
		var obj = {
				id : $(".component_div input[name='monitor_component_id']").val(),
				rowid : param.obj.rowid || Math.uuid().toLowerCase(),
				bjh : $.trim($(".component_div input[name='monitor_component_no']").val()),
				cj : $.trim($(".component_div input[name='monitor_component_manufacturer']").val()),
				projectMonitors : getMonitorSettingParam()
		};
		return obj;
	}
	
	function setDisable(flag){
		param.disabled = flag;
	}
	
	return {
		show : show,
		getMonitorSettingParam : getMonitorSettingParam,
		getComponent : getComponent,
		validateMonitorItem : validateMonitorItem,
		loadData : loadData,
		setDisable : setDisable
	};
}

