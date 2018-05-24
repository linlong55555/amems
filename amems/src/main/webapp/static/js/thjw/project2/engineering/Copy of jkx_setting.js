
var JkxszUtil = function(param_){
	var param = {
		id:'jkxsz_frame_package',
		type : "div",
		isEdit : true,
		title_cn : "监控项设置",
		title_eng : "Monitor Setting",
		showFormula : true,
		showPlane : false,
		planes : [],
		showComponent : true,
		obj : [],
		global_data : [],
		chkValue : 1, //1单次 2重复 3分段
		countNum : 3, //计数器从3开始
		callback : function(){}
	};
	
	if(param_){
		$.extend(param, param_);
	}
	
	function show(param_){
		param.monitorId = param.id + "_monitor";
		param.tbodyId = param.id + "_monitor_body";
		param.modalId = param.id + "_modal";
		initMonitor(function(){
			if(!param.isEdit){
				clearCheck();
			}
		});
		if(param.type == "div"){
			$("#"+param.id).show();
		}else{
			$("#"+param.id).modal("show");
		}
	}
	
	function initMonitor(obj){
		AjaxUtil.ajax({
			url:basePath+"/component/monitorclass/queryOptionList",
			type:"post",
			async: false,
			data:{},
			dataType:"json",
			success:function(data){
				param.global_data = data;
				initMonitorFrame(); //初始,布局
				initZxfsHead(data); //初始,执行方式
				initMonitorHead(data);//初始,监控项
				initDCMonitorBody();//初始,内容
				initDateWidget(); //初始,日历控件
				initMonitorDic(); //初始化数据字典
				$("#addLabel").hide();
				bindEvent(); //绑定事件
			}
		});
	}
	
	/**
	 * 初始化监控设置框架
	 */
	function initMonitorFrame(){
		$("#"+param.id).html(['<div class="clearfix"></div>',
					'<div class="panel panel-primary">',
						'<div class="panel-heading bg-panel-heading" >',
							'<div class="font-size-12 ">'+param.title_cn+'</div>',
							'<div class="font-size-9">'+param.title_eng+'</div>',
						'</div>',
						'<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;">',
							'<div class="plane_div padding-bottom-5"></div>',
							'<div class="formula_div"></div>',
							'<div id="'+param.monitorId+'"></div>',
							'<div class="col-sm-12 padding-left-0 padding-right-0">',
								'<div id="'+param.tbodyId+'" class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0">',
								'</div>',
							'</div>	',
						'</div>',
					'</div>',
					'<div class="clearfix"></div>'].join(''));
	}
	
	/**
	 * 初始化执行方式
	 */
	function initZxfsHead(){
		$("#"+param.id+" .formula_div").html([
					'<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
						'<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">',
							'<label class="margin-right-5 label-input">执行方式</label>',
							'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" checked="checked" value="1" >&nbsp;单次</label>',
							'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" value="2" >&nbsp;重复</label>',
							'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" value="3" >&nbsp;分段</label>',
						'</div>',
					'</div>',
					
					'<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
					'<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">',
						'<label class="margin-right-5 label-input"><input id="hdwzradio" type="checkbox">&nbsp;后到为准</label>',
						'<label class="margin-right-5 label-input"><input id="zztjbsradio" type="checkbox">&nbsp;终止条件</label>',
						'<label class="margin-right-5 label-input"><input class="form-control" id="zztj" type="text"></label>',
					'</div>',
					'</div>'
		].join(""));
	}
	
	/**
	 * 初始化监控设置head
	 */
	function initMonitorHead(data){
		var monitorHtml = '<div class="monitor_setting_css">';
		monitorHtml += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="div'+param.countNum+'" >';
		monitorHtml += '<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">';
		
		monitorHtml += '<label style="margin-right:10px;" name="monitor" id="addLabel" >';
		monitorHtml += '	<button class="line6 line6-btn" id="addFdJkx" type="button">';
		monitorHtml += '		<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>';
		monitorHtml += '	</button>';
		monitorHtml += '</label>';
		
		$.each(data,function(index,row){
			monitorHtml += '<label style="margin-right:10px;" name="monitor">';
			monitorHtml += '<input class="monitorItem" name="jkxItems" type="checkbox" style="vertical-align: -4px;" appendToId="div'+param.countNum+'" jkflbh="'+row.jkflbh+'" jklbh="'+row.jklbh+'" jklms="'+row.ms+'">&nbsp;';
			monitorHtml += row.ms;
			monitorHtml += '</label>';
		});
		monitorHtml += '</div>';
		monitorHtml += '</div>';
		monitorHtml += '</div>';
		$("#"+param.monitorId,$("#"+param.id)).html(monitorHtml);
	}
	
	/**
	 * 添加监控设置head：分段
	 */
	function initFdMonitorHead(data){
		param.countNum++;
		
		var monitorHtml = '<div class="monitor_setting_css">';
		monitorHtml += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" id="div'+param.countNum+'" >';
		monitorHtml += '<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">';
		
		monitorHtml += '<label style="margin-right:10px;" name="monitor" id="addLabel">';
		monitorHtml += '	<button class="line6 line6-btn" name="removeFdJkx" type="button" frameid="div'+param.countNum+'">';
		monitorHtml += '		<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>';
		monitorHtml += '	</button>';
		monitorHtml += '</label>';
		
		$.each(data,function(index,row){
			monitorHtml += '<label style="margin-right:10px;" name="monitor">';
			monitorHtml += '<input class="monitorItem" name="fdjkxItems'+param.countNum+'" type="checkbox" style="vertical-align: -4px;" appendToId="div'+param.countNum+'" jkflbh="'+row.jkflbh+'" jklbh="'+row.jklbh+'" jklms="'+row.ms+'">&nbsp;';
			monitorHtml += row.ms;
			monitorHtml += '</label>';
		});
		monitorHtml += '</div>';
		monitorHtml += '</div>';
		monitorHtml += '</div>';
		$("#"+param.monitorId,$("#"+param.id)).append(monitorHtml);
	}
	
	/**
	 * 初始化监控设置body：单次
	 */
	function initDCMonitorBody(){		
		var monitorBody = '';

		monitorBody += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" style="display:block;">';
		monitorBody += '<div class="col-lg-8 padding-left-0 padding-right-0" id="dcDiv">';
		
		/*monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8" id="dcOneDiv" style="display:block;">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">'+row.ms+'</div>';
		monitorBody +='<div class="input-group padding-left-8">';
		monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq">';
		monitorBody +=buildUnit(row,'zq',1);
		monitorBody +='</div>';
		monitorBody += '</div>';*/
		
		monitorBody += '</div>';
		monitorBody += '</div>';
		
		$("#"+param.tbodyId,$("#"+param.id)).html(monitorBody);
	}
	
	/**
	 * 初始化监控设置body：重复
	 */
	function initCFMonitorBody(data){
		var monitorBody = '';
		$.each(data,function(index,row){
			monitorBody += '<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10" jkflbh="'+row.jkflbh+'" jklbh="'+row.jklbh+'" style="display:none;">';
			
			monitorBody += '<div class="col-lg-1 col-sm-1 col-xs-3 padding-left-0 padding-right-0">';
			monitorBody += '<label style="font-weight:normal;padding-top:8px;" class="col-lg-12 col-sm-12 col-xs-12 text-right padding-left-0 padding-right-0">';
			monitorBody += row.ms;
			monitorBody += '</label>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-8 padding-left-0 padding-right-0">';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">首次</div>';
			monitorBody +=buildUnit(row,'sc',2);
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">周期</div>';
			monitorBody +='<div class="input-group padding-left-8">';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq">';
			monitorBody +=buildUnit(row,'zq',2);
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-5 col-sm-5 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">容差</div>';
			monitorBody +='<div class="input-group padding-left-8">';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">-</span>';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rcq">';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">/</span>';
			monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">+</span>';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rch">';
			monitorBody +=buildUnit(row,"rc",2);
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '</div>';
			
			monitorBody += '</div>';
		});
		$("#"+param.tbodyId,$("#"+param.id)).html(monitorBody);
	}
	
	/**
	 * 初始化日期控件
	 */
	function initDateWidget(){
		$('.datepicker').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
	}
	
	/**
	 * 初始化监控设置数据字典
	 */
	function initMonitorDic(){
		DicAndEnumUtil.registerEnum("computationalFormulaEnum", param.id+'_jsgs');
	}
	
	/**
	 * 添加事件
	 */
	function bindEvent(){
		$("#"+param.id+" .monitorItem").click(function(){
			if(param.chkValue == 1 || param.chkValue == 2){
				showOrHideMonitor($(this));
				showOrHideDcDiv($(this));
			}else if(param.chkValue == 3){
				showOrHideFdDiv($(this));
			}
		});
		$("#"+param.id+" .calendarUnit").click(function(){
			changeUnit($(this));
		});
		$("#"+param.id+" #monitor_confirm").click(function(){
			if(param.callback && typeof(param.callback) == "function"){
				param.callback();
			}
		});
		$("input[name='zxfsradio']").change(function(e) {
			param.chkValue = $(this).val();
			changeWay(param.chkValue);
		});
		$("#addFdJkx").click(function() {
			addFdJkx();
		});
	}
	
	/**
	 * 改变执行方式
	 */
	function changeWay(chkVal){
		//清空监控项信息
		$("input[name='jkxItems']").attr("checked", false);
		$("input[name='jkxItems']").removeAttr("disabled"); 
		//清空body信息
		$("#"+param.tbodyId).empty();
		//隐藏添加监控项按钮
		$("#addLabel").hide();
		for(var i = 4; i <= param.countNum; i++) {
			$("#div"+i).remove();//删除存在的监控项
		}
		//删除fddiv3body
		$("div[name='fddiv3']").remove();
		
		param.countNum = 3;
		
		if(chkVal == 1) { //单次
			initDCMonitorBody();
		}else if(chkVal ==2) {//重复
			initCFMonitorBody(param.global_data);
		}else if(chkVal == 3) {//分段
			$("#addLabel").show(); //显示添加监控项按钮
		}
	}
	
	/**
	 * 显示影藏单次，选中的监控项
	 * @param chkObj
	 */
	function showOrHideDcDiv(chkObj){
		var chkJklbh = chkObj.attr("jklbh");//选择的监控项
		
		if($('#dcDivContent'+chkJklbh).length>0){
			$("#dcDivContent"+chkJklbh).remove();//删除存在的监控项
			return;
		}
		
		var row = {};
		$.each(param.global_data,function(index,item){
			if(item.jklbh == chkJklbh){
				row = item;
			}
		});
		
		var monitorBody = '';
		monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8" style="display:block;" id="dcDivContent'+chkJklbh+'">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">'+row.ms+'</div>';
		monitorBody +='<div class="input-group padding-left-8">';
		if(chkJklbh == "CAL"){
			monitorBody +=buildUnit(row,'sc',1);
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq">';
			monitorBody +=buildUnit(row,'zq',1);
		}
		monitorBody +='</div>';
		monitorBody += '</div>';
		
		$("#dcDiv").append(monitorBody); //添加监控项
		
		initDateWidget(); //初始化日期
	}
	
	
	/**
	 * 显示影藏分段，选中的监控项
	 * @param chkObj
	 */
	function showOrHideFdDiv(chkObj){
		
		var parentDiv = chkObj.attr("appendToId");
		var chkJklbh = chkObj.attr("jklbh");//选择的监控项
		
		$("input[appendToId='"+parentDiv+"']").attr("disabled","true"); //所有监控项,不可选
		if($("input[appendToId='"+parentDiv+"']:checked").length >= 3){
			$("input[appendToId='"+parentDiv+"']:checked").removeAttr("disabled");//选中的可选
		}else{
			$("input[appendToId='"+parentDiv+"']").removeAttr("disabled");//所有的可选
		}
		
		if($('#fd'+parentDiv+chkJklbh).length>0){
			$("#fd"+parentDiv+chkJklbh).remove();//删除存在的监控项
			return;
		}
		
		var row = {};
		$.each(param.global_data,function(index,item){
			if(item.jklbh == chkJklbh){
				row = item;
			}
		});
		
		var monitorBody = '';
		monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8" style="display:block;" name="fd'+parentDiv+'" id="fd'+parentDiv+chkJklbh+'">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">'+row.ms+'</div>';
		monitorBody +='<div class="input-group padding-left-8">';
		if(chkJklbh == "CAL"){
			monitorBody +=buildUnit(row,'sc',1);
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq">';
			monitorBody +=buildUnit(row,'zq',1);
		}
		monitorBody +='</div>';
		monitorBody += '</div>';
		
		$("#"+parentDiv).append(monitorBody); //添加监控项
		
		initDateWidget(); //初始化日期
	}
	
	/**
	 * 添加分段监控项
	 */
	function addFdJkx() {
		initFdMonitorHead(param.global_data);
		$("input[name='fdjkxItems"+param.countNum+"']").click(function(){
			showOrHideFdDiv($(this));
		});
		$("button[name='removeFdJkx']").click(function() {
			removeFdJkx($(this));
		});
	}
	
	/**
	 * 删除分段监控项
	 */
	function removeFdJkx(removeObj) {
		$("#"+removeObj.attr("frameid")).remove();//删除存在的监控项
	}
	
	/**
	 * 生成监控项单位
	 */
	function buildUnit(item, type,way){
		
		var jklbh = item.jklbh;
		
		var html = "";
		var dwMap = {
				CAL:"",
				FH : "HRS",
				FC : "CYC",
				EH : "HRS",
				EC : "CYC",
				APUH : "HRS",
				APUC : "CYC"
		}
		
		if(type == "sc"){
			if(jklbh == "CAL"){
				if(way == 2){
					html +='<div class="input-group padding-left-8">';
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100">';
					html +='<div class="input-group-btn" style="min-width:45px;">';
					html +='<button value="11" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:30px;padding:0 0 0 0;color: #555;">DAY<span class="caret"></span></button>';
					html +='<ul class="dropdown-menu dropdown-menu-right">';
					html +=' <li><a href="#" value="11" class="calendarUnit">DAY</a></li>';
					html +=' <li><a href="#" value="12" class="calendarUnit">MON</a></li>';
					html +='</ul>';
					html +='</div>';
					html +='</div>';
				}else{
					html +='<div class="padding-left-8" style="margin-left:24px;">';
					html +=' <input class="form-control datepicker input-sm" name="'+type+'" type="text" data-date-format="yyyy-mm-dd" maxlength="10">';
					html +='</div>';
				}
			}else{
				html +='<div class="input-group padding-left-8">';
				html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100">';
				html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;color: #555;font-size: 12px;padding: 0 0 0 0;">'+dwMap[jklbh]+'</span>';
				html +='</div>';
			}
		}else {
			if(jklbh == "CAL"){
				html +='<div class="input-group-btn" style="min-width:45px;">';
				html +='<button value="11" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:30px;padding:0 0 0 0;color: #555;">DAY<span class="caret"></span></button>';
				html +='<ul class="dropdown-menu dropdown-menu-right">';
				html +=' <li><a href="#" value="11" class="calendarUnit">DAY</a></li>';
				html +=' <li><a href="#" value="12" class="calendarUnit">MON</a></li>';
				html +='</ul>';
				html +='</div>';
			}else{
				html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">'+dwMap[jklbh]+'</span>';
			}
		}
		return html;
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
			$("#"+param.id+" div[jklbh='"+checkbox.attr("jklbh")+"']").show();
		}else{
			$("#"+param.id+" div[jklbh='"+checkbox.attr("jklbh")+"']").hide();
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
		return $("#"+param.id+' input.monitorItem:checkbox:checked');
	}
	
	/**
	 * 获取所有的监控项数目
	 */
	function getMonitorItems(){
		return $("#"+param.id+' input.monitorItem:checkbox');
	}
	
	/**
	 * 启用/禁用checkbox
	 */
	function setCheckDisabled(){
		getMonitorItems().attr("disabled","true");
		if(getSelectMonitorNum() >= 3){
			getSelectMonitorItems().removeAttr("disabled");
		}else{
			getMonitorItems().removeAttr("disabled");
		}
	}

	function clearCheck(){
		getMonitorItems().removeAttr("disabled");
		$("#"+param.tbodyId+">div").hide();
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
			var row = $("#"+param.tbodyId+">div[jklbh='"+jklbh+"'][jkflbh='"+jkflbh+"']");
			var obj = {
					jklbh : jklbh,
					jkflbh : jkflbh,
					monitorOptionItem : {
						ms : jklms
					},
					scz : row.find("input[name='sc']").val(),
					dwScz : getUnit(row, jklbh, "sc"),
					zqz : row.find("input[name='zq']").val(),
					dwZqz : getUnit(row, jklbh, "zq"),
					ydzQ :row.find("input[name='rcq']").val(),
					ydzQdw : getUnit(row, jklbh, "rc"),
					ydzH :row.find("input[name='rch']").val(),
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
		
		var dwMap = {
				CAL:"10",
				FH : "20",
				FC : "30",
				EH : "20",
				EC : "30",
				APUH : "20",
				APUC : "30"
		}
		
		if(jklbh == "CAL"){
			return row.find("button[name='"+type+"_dw']").attr("value")||dwMap[jklbh]||"";
		}else{
			return dwMap[jklbh]||"";
		}
	}
	
	/**
	 * 获取部件参数
	 */
	function getComponent(){
		var obj = {
				id : $("#"+param.id+" input[name='monitor_component_id']").val(),
				bjh : $("#"+param.id+" input[name='monitor_component_no']").val(),
				cj : $("#"+param.id+" input[name='monitor_component_manufacturer']").val(),
				projectMonitors : getMonitorSettingParam()
		};
		return obj;
	}
	
	return {
		show : show,
		getMonitorSettingParam : getMonitorSettingParam,
		getComponent : getComponent
	}
}


/***
 * 获取监控项信息
 */
function getJzxszData() {
	var param = [];
	
/*	var infos ={};
	
	var zxway = $('input[name="zxfsradio"]:checked').val(); //执行方式
	
	if(zxway == 1) {
		var parentId = "div"; //父div
		console.log(JkxszUtil.countNum);
		$(parentId +' input[type=checkbox]:checked').each(function(){
		    alert($(this).val());
		});
	}else if(zxway == 2) {
		infos.myid = 'lw2';
		param.push(infos);
	}else if(zxway == 3) {
		infos.myid = 'lw3';
		param.push(infos);
	}*/
	
	return param;
}

