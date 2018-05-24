var eoJkxSettingisValid = true; //验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过

var fjHeadDatas = ["1_10","2_10_FH","3_10_FC"]; //如果选择的飞机，那么监控项目只能从日历日、飞行时间、飞行循环中选择
var fdjHeadDatas = ["1_10","2_10_FH","3_10_FC","2_30_EH","3_30_EC"]; //如果选择的发动机，那么监控项目只能从日历日、飞行时间、飞行循环、发动机时间、发动机循环中选择
var apuHeadDatas = ["1_10","2_10_FH","3_10_FC","2_20_AH","3_20_AC"]; //如果选择的APU，那么监控项目只能从日历日、飞行时间、飞行循环、APU时间、APU循环中选择
var otherHeadDatas = ["1_10","2_10_FH","3_10_FC","2_30_EH","3_30_EC","2_20_AH","3_20_AC"]; //如果选择的其他部件，那么监控项目只能从日历日、飞行时间、飞行循环、发动机时间、发动机循环、APU时间、APU循环中选择

var param = {
	id:'workorder_jkxsz_frame_package',
	type : "div",
	title_cn : "监控项设置",
	title_eng : "Monitor Setting",
	global_data : [],
	oldWay : "", //原始方式
	oldData : {},//原始数据
	chkValue : 1, //1单次 2重复 3分段
	countNum : 3, //计数器从3开始
	sylb : "", //1：飞机 2：发动机 3：cpu 99：其他部件 根据适用性类别加载不同监控项
};

/**
 * @param way 监控项设置 执行方式：1单次、2重复、3分段，默认为1
 * @param mainId EOID当eoId不为空时,加载数据
 * @param syxlb 使用性类别，1：飞机 2：发动机 3：cpu 99：其他部件 根据适用性类别加载不同监控项
 * 
 *  如果选择的飞机，那么监控项目只能从日历日、飞行时间、飞行循环中选择
	如果选择的发动机，那么监控项目只能从日历日、飞行时间、飞行循环、发动机时间、发动机循环中选择
	如果选择的APU，那么监控项目只能从日历日、飞行时间、飞行循环、APU时间、APU循环中选择
	如果选择的其他部件，那么监控项目只能从日历日、飞行时间、飞行循环、发动机时间、发动机循环、APU时间、APU循环中选择
 */
function initJkxszWin(way,mainId,syxlb) {
	param.monitorId = param.id + "_monitor";
	param.tbodyId = param.id + "_monitor_body";
	param.modalId = param.id + "_modal";
	param.countNum = 3;
	param.global_data = [];
	param.oldWay = "";
	param.sylb = syxlb;
	param.oldData = {};
	
	//初始监控项
	initMonitor();
	
	//加载已保存数据
	if(null != mainId){
		initMonitorDatas(way,mainId);
	}
	
	$("#"+param.id).show();
}

//初始化监控项信息
function initMonitor(){ 
	var this_ = this;
	AjaxUtil.ajax({
		url:basePath+"/component/monitorclass/queryOptionList",
		type:"post",
		async: false,
		data:{},
		dataType:"json",
		success:function(data){
			/**Start:根据适用性类别，加载监控项设置*/
			this_.param.global_data = [];
			$.each(data,function(index,row){
				if(this_.param.sylb == 1){ //飞机
					if(this_.fjHeadDatas.indexOf(row.jklbh) > -1){
						this_.param.global_data.push(row);
					}
				}else if(this_.param.sylb == 2){//发动机
					if(this_.fdjHeadDatas.indexOf(row.jklbh) > -1){
						this_.param.global_data.push(row);
					}
				}else if(this_.param.sylb == 3){//APU
					if(this_.apuHeadDatas.indexOf(row.jklbh) > -1){
						this_.param.global_data.push(row);
					}
				}else if(this_.param.sylb == 99){//其他部件
					if(this_.otherHeadDatas.indexOf(row.jklbh) > -1){
						this_.param.global_data.push(row);
					}
				}
			});
			/**End:根据适用性类别，加载监控项设置*/
			initMonitorFrame(); //初始,布局
			//initZxfsHead(this_.param.global_data); //初始,执行方式
			initMonitorHead(this_.param.global_data);//初始,监控项
			initDCMonitorBody();//初始,内容
			initDateWidget(); //初始,日历控件
			initMonitorDic(); //初始化数据字典
			$("#addLabel").hide();
			bindEvent(); //绑定事件
		}
	});
}

//加载监控项数据   执行方式way：1单次、2重复、3分段
function initMonitorDatas(way,mainId){
	var searchParam = {};
	searchParam.mainid = mainId;
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project2/eomonitoriemset/queryAllList",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(searchParam),
		dataType:"json",
		success:function(data){
			param.oldWay = way;
			param.oldData = data.concat(0); //防止地址引用，返回新的数组对象
			reviewDatas(way,data);
		}
	});
}

/***
 * 回显数据
 */
function reviewDatas(way,data){
	if(data.length <= 0){return;}
	
	if(way == 1) { //单次
		initDCMonitorBody();
	}else if(way ==2) {//重复
		initCFMonitorBody(param.global_data);
	}else if(way == 3) {//分段
		param.chkValue = 3;
		$("#addLabel").show(); //显示添加监控项按钮
	}
	
	var preXC = 3;//前一段项次号：默认从三开始
	
	$.each(data,function(index,row){
		var scz = row.scz;
		var zqz = row.zqz;
		var ydzQ = row.ydzQ;
		var ydzH = row.ydzH;
		
		if(MonitorUnitUtil.isTime(row.jklbh)){
			scz = TimeUtil.convertToHour(row.scz, TimeUtil.Separator.COLON);
			zqz = TimeUtil.convertToHour(row.zqz, TimeUtil.Separator.COLON);
			ydzQ = TimeUtil.convertToHour(row.ydzQ, TimeUtil.Separator.COLON);
			ydzH = TimeUtil.convertToHour(row.ydzH, TimeUtil.Separator.COLON);
		}
		
		
		if(way == 1){
			$('input[jklbh="'+row.jklbh+'"]').click();
			$("#dcDivContent"+row.jklbh+" input[type=text]").attr("value",scz);//首次值
		}else if(way == 2){
			$('input[name="zxfsradio"]').eq(1).prop('checked',true);
			$('input[jklbh="'+row.jklbh+'"]').click();
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("input[name='sc']").attr("value",scz);//首次值
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='sc_dw']").attr("value",row.dwScz);//首次值单位
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='sc_dw']").html(MonitorUnitUtil.getMonitorUnit(row.jklbh,row.dwScz)+'<span class="caret"></span>');
			
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("input[name='zq']").attr("value",zqz);//周期值
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='zq_dw']").attr("value",row.dwZqz);//首次值单位
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='zq_dw']").html(MonitorUnitUtil.getMonitorUnit(row.jklbh,row.dwZqz)+'<span class="caret"></span>');
			
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("input[name='rcq']").attr("value",ydzQ);//前裕度值
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("input[name='rch']").attr("value",ydzH);//后裕度值
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='rc_dw']").attr("value",row.ydzHdw);//首次值单位
			$("#"+param.id+" div[jklbh='"+row.jklbh+"']").find("button[name='rc_dw']").html(MonitorUnitUtil.getMonitorUnit(row.jklbh,row.ydzHdw)+'<span class="caret"></span>');
		}else if(way == 3){
			$('input[name="zxfsradio"]').eq(2).prop('checked',true);
			//从第2条数据开始分段(因为初始已经存在了一条)
			if(index > 0 && row.xc != preXC){
				addFdJkx();
			}
			preXC = row.xc;
		}
	});
	
	//自动勾选
	if(way == 3){
		$.each(data,function(index,row){
			var scz = row.scz;
			if(MonitorUnitUtil.isTime(row.jklbh)){
				scz = TimeUtil.convertToHour(row.scz, TimeUtil.Separator.COLON);
			}
			$("#div"+row.xc+' input[jklbh="'+row.jklbh+'"]').click();
			$("#fddiv"+row.xc+row.jklbh).find("input[type=text]").attr("value",scz);//首次值
		});
	}
	
}

/**
 * 初始化监控设置框架
 */
function initMonitorFrame(){
	$("#"+param.id).html(['<div class="clearfix"></div>',
				'<div class="panel panel-primary">',
					/*'<div class="panel-heading bg-panel-heading" >',
						'<div class="font-size-12 ">'+param.title_cn+'</div>',
						'<div class="font-size-9">'+param.title_eng+'</div>',
					'</div>',*/
					'<div class="panel-body padding-left-0 padding-right-0" style="padding-bottom:0px;">',
						/*'<div class="plane_div padding-bottom-5"></div>',*/
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
				'<div class="pull-left padding-left-0 padding-right-0 form-group" style="width:296px;">',
					'<div class="col-xs-12 padding-leftright-8 label-input-div" >',
						'<label class="margin-right-5 label-input">执行方式</label>',
						'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" checked="checked" value="1" >&nbsp;单次</label>',
						'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" value="2" >&nbsp;重复</label>',
						'<label class="margin-right-5 label-input"><input name="zxfsradio" type="radio" value="3" >&nbsp;分段</label>',
					'</div>',
				'</div>',
				
				'<div class="col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
				'<div class="col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">',
				'<div class="input-group input-group-new">',
				'<div class="input-group-addon"><label style="color:#333;"><input id="hdwzradio" type="checkbox" style="vertical-align: -4px;">&nbsp;后到为准&nbsp;</label></div>',
				'<div class="input-group-addon"><label class="margin-right-5 " style="color:#333;"><input id="zztjbsradio" type="checkbox" style="vertical-align: -4px;" onchange=eo_main.setRedonly("zztj")  maxlength="100" >&nbsp;终止条件&nbsp;</label></div>',
				'<input class="form-control" id="zztj" type="text" style="visibility:hidden;">',
				'</div>',
					/*'<label class="margin-right-5 label-input"><input id="hdwzradio" type="checkbox">&nbsp;后到为准</label>',
					'<label class="margin-right-5 label-input"><input id="zztjbsradio" type="checkbox" onchange=eo_main.setRedonly("zztj")  maxlength="100" >&nbsp;终止条件</label>',
					'<label class="margin-right-5 label-input"><input class="form-control" id="zztj" type="text" readonly="readonly"></label>',*/
				'</div>',
				'</div>'
	].join(""));
}

/**
 * 初始化监控设置head
 */
function initMonitorHead(data){
	var monitorHtml = '<div class="monitor_setting_css">';
	monitorHtml += '<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-0 padding-right-0 form-group" id="div'+param.countNum+'" >';
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
	monitorHtml += '<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-0 padding-right-0 form-group" id="div'+param.countNum+'" >';
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
		monitorBody += '<label style="font-weight:normal;padding-top:5px;" class="col-lg-12 col-sm-12 col-xs-12 text-right padding-left-0 padding-right-0">';
		monitorBody += row.ms;
		monitorBody += '</label>';
		monitorBody += '</div>';
		
		monitorBody += '<div class="col-lg-8 padding-left-0 padding-right-0">';
		
		monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:5px;">首次</div>';
		monitorBody +=buildUnit(row,'sc',2);
		monitorBody += '</div>';
		
		monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:5px;">周期</div>';
		monitorBody +='<div class="input-group padding-left-8">';
		if(MonitorUnitUtil.isTime(row.jklbh)){
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" jklbhrow="'+row.jklbh+'_row"  onkeyup="validateInputTime2(this)">';
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" jklbhrow="'+row.jklbh+'_row"  onkeyup="validateInputNumber2(this)">';
		}
		monitorBody +=buildUnit(row,'zq',2);
		monitorBody +='</div>';
		monitorBody += '</div>';
		
		monitorBody += '<div class="col-lg-5 col-sm-5 col-xs-12 padding-leftright-8">';
		monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:5px;">容差</div>';
		monitorBody +='<div class="input-group padding-left-8">';
		monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">-</span>';
		if(MonitorUnitUtil.isTime(row.jklbh)){
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rcq" onkeyup="validateInputTime(this)" value="0" >';
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rcq" onkeyup="validateInputNumber(this)" value="0" >';
		}
		monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">/</span>';
		monitorBody +='<span class="input-group-addon padding-left-5 padding-right-3" style="border:none;background-color:white;font-size: 12px;">+</span>';
		if(MonitorUnitUtil.isTime(row.jklbh)){
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rch" onkeyup="validateInputTime(this)" value="0" >';
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="rch" onkeyup="validateInputNumber(this)" value="0" >';
		}
		monitorBody +=buildUnit(row,"rc",2);
		monitorBody +='</div>';
		monitorBody += '</div>';
		
		monitorBody += '</div>';
		
		monitorBody += '</div>';
	});
	$("#"+param.tbodyId,$("#"+param.id)).html(monitorBody);
	initFormula(); //计算公式
	initMonitorDic();//计算公式值
}

/**
 * 初始化计算公式
 */
function initFormula(){
	$("#workorder_jkxsz_frame_package_monitor_body",$("#"+param.id)).append(['<div class="col-sm-12 padding-left-0 padding-right-0">',
					'<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
					'<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">',
						'<div class="font-size-12 margin-topnew-2">计算公式</div>',
						'<div class="font-size-9">Formula</div>',
					'</label>',
					'<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">',
						'<select class="form-control" id="'+param.id+'_jsgs">',
						'</select>',
					'</div>',
				'</div>',
				].join(""));
}

/**
 * 初始化监控设置数据字典
 */
function initMonitorDic(){
	DicAndEnumUtil.registerEnum("computationalFormulaEnum", param.id+'_jsgs');
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
	
	//如果切换后,执行方式和原来一致，加载出初始数据
	if(chkVal == param.oldWay){
		reviewDatas(param.oldWay,param.oldData);
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
	monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:5px;">'+row.ms+'</div>';
	monitorBody +='<div class="input-group padding-left-8">';
	if(chkJklbh == "1_10"){
		monitorBody +=buildUnit(row,'sc',1);
	}else{
		if(MonitorUnitUtil.isTime(chkJklbh)){
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" onkeyup="validateInputTime(this)" >'; //加监听(时间格式)
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" onkeyup="validateInputNumber(this)" >';//加监听(数字)
		}
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
	monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8 margin-top-8" style="display:block;" name="fd'+parentDiv+'" id="fd'+parentDiv+chkJklbh+'">';
	monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:5px;width:65px;text-align:right;">'+row.ms+'</div>';
	monitorBody +='<div class="input-group padding-left-8">';
	if(chkJklbh == "1_10"){
		monitorBody +=buildUnit(row,'sc',1);
	}else{
		if(MonitorUnitUtil.isTime(chkJklbh)){
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" onkeyup="validateInputTime(this)" >'; //加监听(时间格式)
		}else{
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="100" name="zq" onkeyup="validateInputNumber(this)" >';//加监听(数字)
		}
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
	
	if(type == "sc"){
		if(jklbh == "1_10"){
			if(way == 2){
				html +='<div class="input-group padding-left-8">';
				if(MonitorUnitUtil.isTime(jklbh)){
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100" jklbhrow="'+jklbh+'_row"  onkeyup="validateInputTime2(this)">';
				}else{
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100" jklbhrow="'+jklbh+'_row"  onkeyup="validateInputNumber2(this)">';
				}
				html +='<div class="input-group-btn" style="min-width:45px;">';
				html +='<button value="11" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:34px;padding:0 0 0 0;color: #555 !important;background:white;">'+MonitorUnitUtil.getMonitorUnit(jklbh,"12")+'&nbsp;<span class="caret"></span></button>';
				html +='<ul class="dropdown-menu dropdown-menu-right">';
				html +=' <li><a href="javascript:void(0)" value="11"  onclick="changeUnit(this)"  class="calendarUnit">'+MonitorUnitUtil.getMonitorUnit(jklbh,"11")+'</a></li>';
				html +=' <li><a href="javascript:void(0)" value="12"  onclick="changeUnit(this)"  class="calendarUnit">'+MonitorUnitUtil.getMonitorUnit(jklbh,"12")+'</a></li>';
				html +='</ul>';
				html +='</div>';
				html +='</div>';
			}else{
				html +=' <input class="form-control input-sm datepicker" readonly="readonly" name="'+type+'" type="text" data-date-format="yyyy-mm-dd" maxlength="10"  onchange="changeInput(this)">';
				/*html +='<span class="input-group-addon dw btn btn-default"  style="min-width:45px;font-size: 12px;padding: 0 0 0 0;"><i class="fa fa-calendar"></i></span>';*/
			}
		}else{
			html +='<div class="input-group padding-left-8">';
			
			if(way == 2){
				if(MonitorUnitUtil.isTime(jklbh)){
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100" jklbhrow="'+jklbh+'_row"  onkeyup="validateInputTime2(this)">';
				}else{
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100" jklbhrow="'+jklbh+'_row"  onkeyup="validateInputNumber2(this)">';
				}
			}else{
				if(MonitorUnitUtil.isTime(jklbh)){
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100"  onkeyup="validateInputTime(this)">';
				}else{
					html +=' <input class="form-control input-sm" name="'+type+'" type="text" maxlength="100"  onkeyup="validateInputNumber(this)">';
				}
			}
			
			html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;color: #555;font-size: 12px;padding: 0 0 0 0;">'+MonitorUnitUtil.getMonitorUnit(jklbh,"")+'</span>';
			html +='</div>';
		}
	}else {
		if(jklbh == "1_10"){
			html +='<div class="input-group-btn" style="min-width:45px;">';
			html +='<button value="11" name="'+type+'_dw" type="button" class="btn btn-default" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="min-width:45px;height:34px;padding:0 0 0 0;color: #555 !important;background:white;">'+MonitorUnitUtil.getMonitorUnit(jklbh,"12")+'&nbsp;<span class="caret"></span></button>';
			html +='<ul class="dropdown-menu dropdown-menu-right">';
			html +=' <li><a href="javascript:void(0)" value="11" onclick="changeUnit(this)" class="calendarUnit">'+MonitorUnitUtil.getMonitorUnit(jklbh,"11")+'</a></li>';
			html +=' <li><a href="javascript:void(0)" value="12" onclick="changeUnit(this)" class="calendarUnit">'+MonitorUnitUtil.getMonitorUnit(jklbh,"12")+'</a></li>';
			html +='</ul>';
			html +='</div>';
		}else{
			html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">'+MonitorUnitUtil.getMonitorUnit(jklbh,"")+'</span>';
		}
	}
	return html;
}

/**
 * 日历单位改变
 */
function changeUnit(obj){
	var btn = $(obj).parent().parent().prev();
	btn.attr("value", $(obj).attr("value"));
	btn.html($(obj).text()+'<span class="caret"></span>');
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
 * 获取部件参数
 */
function getComponent(){
	var obj = {
			id : $("#"+param.id+" input[name='monitor_component_id']").val(),
			bjh : $("#"+param.id+" input[name='monitor_component_no']").val(),
			cj : $("#"+param.id+" input[name='monitor_component_manufacturer']").val(),
	};
	return obj;
}

/***
 * 获取监控项信息
 */
function getJzxszData() {
	var this_ = this;
	this_.eoJkxSettingisValid = true;
	
	var paramData = [];
	
	var zxway = $('input[name="zxfsradio"]:checked').val(); //执行方式
	
	if(zxway == 1) {
		var parentId = "div"+param.countNum; //父div
		$("#"+parentId).find("input[type=checkbox]:checked").each(function(index,chkObj){//div下选中的监控项input
			var info ={};
			info.xc = index; //项次
			info.zt = "1"; //状态
			info.jklbh = $(chkObj).attr("jklbh");//监控项编号
			info.jkflbh = $(chkObj).attr("jkflbh");//监控项分类
			
			var jklms = $(chkObj).attr("jklms");//监控项描述
			var inputObj = $($("#dcDivContent"+info.jklbh).find("input[type=text]")[0]);
			info.scz = inputObj.attr("value");//首次值
			if(null == info.scz || "" == info.scz){
				AlertUtil.showModalFooterMessage("请输入"+jklms+"!");
				inputObj.focus();
				inputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;
			}
			
			info.dwScz = MonitorUnitUtil.getMonitorValue($(chkObj).attr("jklbh")); //首次值单位
			paramData.push(info);
		});
	}else if(zxway == 2) {
		var parentId = "div"+param.countNum; //父div
		$("#"+parentId).find("input[type=checkbox]:checked").each(function(index,chkObj){//div下选中的监控项input
		    var jkxbhDiv = $("#"+param.id+" div[jklbh='"+$(chkObj).attr("jklbh")+"']"); //根据项编号,找到对应内容Div
		    
			var info ={};
			info.xc = index; //项次
			info.zt = "1"; //状态
			info.jklbh = $(chkObj).attr("jklbh");//监控项编号
			info.jkflbh = $(chkObj).attr("jkflbh");//监控项分类
			
			var jklms = $(chkObj).attr("jklms");//监控项描述
			var sczInputObj = $(jkxbhDiv).find('input[name="sc"]');
			info.scz = sczInputObj.attr("value");//首次值
			/*if(null == info.scz || "" == info.scz){
				AlertUtil.showModalFooterMessage("请输入"+jklms+"首次值!");
				sczInputObj.focus();
				sczInputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;
			}*/
			
			if(info.jklbh == "1_10"){
				info.dwScz = $(jkxbhDiv).find('button[name="sc_dw"]').attr("value");//首次值单位
			}else{
				info.dwScz =  MonitorUnitUtil.getMonitorValue(info.jklbh); //首次值单位
			}
			
			var zqzInputObj = $(jkxbhDiv).find('input[name="zq"]');
			info.zqz = zqzInputObj.attr("value");//周期值
			/*if(null == info.zqz || "" == info.zqz){
				AlertUtil.showModalFooterMessage("请输入"+jklms+"周期值!");
				zqzInputObj.focus();
				zqzInputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;
			}*/
			
			if((null == info.scz || "" == info.scz) && (null == info.zqz || "" == info.zqz)){
				AlertUtil.showModalFooterMessage("请输入"+jklms+"首次值或周期值!");
				sczInputObj.focus();
				sczInputObj.addClass("border-color-red");
				zqzInputObj.focus();
				zqzInputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;
			}
			
			if(info.jklbh == "1_10"){
				info.dwZqz = $(jkxbhDiv).find('button[name="zq_dw"]').attr("value");; //周期值单位
			}else{
				info.dwZqz =  MonitorUnitUtil.getMonitorValue(info.jklbh); //周期值单位
			}
			
			var ydzqInputObj = $(jkxbhDiv).find('input[name="rcq"]');
			info.ydzQ = ydzqInputObj.attr("value");//前裕度值
			if(null == info.ydzQ || "" == info.ydzQ){
				/*AlertUtil.showModalFooterMessage("请输入"+jklms+"前裕度值!");
				ydzqInputObj.focus();
				ydzqInputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;*/
				info.ydzQ = "0";
			}
			
			if(info.jklbh == "1_10"){
				info.ydzQdw = $(jkxbhDiv).find('button[name="rc_dw"]').attr("value");; //前裕度值单位
			}else{
				info.ydzQdw =  MonitorUnitUtil.getMonitorValue(info.jklbh); //前裕度值单位
			}
			
			var ydzHInputObj = $(jkxbhDiv).find('input[name="rch"]');
			info.ydzH = ydzHInputObj.attr("value");//后裕度值
			if(null == info.ydzH || "" == info.ydzH){
				/*AlertUtil.showModalFooterMessage("请输入"+jklms+"后裕度值!");
				ydzHInputObj.focus();
				ydzHInputObj.addClass("border-color-red");
				this_.eoJkxSettingisValid = false; 
				return false;*/
				info.ydzH = "0";
			}
			
			if(info.jklbh == "1_10"){
				info.ydzHdw = $(jkxbhDiv).find('button[name="rc_dw"]').attr("value");; //后裕度值单位
			}else{
				info.ydzHdw =MonitorUnitUtil.getMonitorValue(info.jklbh); //后裕度值单位
			}
			
			paramData.push(info);
		});
	}else if(zxway == 3) {
		for (var c = 3; c <= param.countNum; c++) {
			if($("#div"+c).length > 0) { //若果div存在
				var parentId = "div"+c; //父div
				$("#"+parentId).find("input[type=checkbox]:checked").each(function(index,chkObj){//div下选中的监控项input
					var info ={};
					info.xc = c; //项次
					info.zt = "1"; //状态
					info.jklbh = $(chkObj).attr("jklbh");//监控项编号
					info.jkflbh = $(chkObj).attr("jkflbh");//监控项分类
					
					var jklms = $(chkObj).attr("jklms");//监控项描述
					var inputObj = $($("#fd"+parentId+info.jklbh).find("input[type=text]")[0]);
					info.scz = inputObj.attr("value");//首次值
					if(null == info.scz || "" == info.scz){
						AlertUtil.showModalFooterMessage("请输入"+jklms+"!");
						inputObj.focus();
						inputObj.addClass("border-color-red");
						this_.eoJkxSettingisValid = false; 
						return false;
					}
					
					info.dwScz = MonitorUnitUtil.getMonitorValue($(chkObj).attr("jklbh")); //首次值单位
					paramData.push(info);
				});
			}
		}
	}
	
	//重复：至少输入一个首次值
	if(zxway == 2 && paramData != null && paramData.length > 0) {
		var sczNums = 0;
		for(var i = 0; i < paramData.length; i++) {
			if(null != paramData[i].scz && "" != paramData[i].scz){
				sczNums++;
			}
		}
		if(sczNums == 0){
			AlertUtil.showModalFooterMessage("请至少输入一个首次值!");
			this_.eoJkxSettingisValid = false; 
			return false;
		}
	}
	
	return paramData;
}


/**
 * 输入框验证
 */
function validateInputTime(obj){
	var this_ = this;
	var reg = /^[0-9]+((\:)?[0-5]?[0-9]?)?$/;
	var value = $(obj).val();
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
	this_.changeInput(obj);
}

/**
 * 输入框验证
 */
function validateInputNumber(obj){
	var this_ = this;
	var reg = /^[0-9]+(\.?[0-9]{0,2})?$/;
	var value = $(obj).val();
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
	this_.changeInput(obj);
}

/**
 * 输入框验证
 */
function validateInputTime2(obj){
	var this_ = this;
	var reg = /^[0-9]+((\:)?[0-5]?[0-9]?)?$/;
	var value = $(obj).val();
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
	var jklbhrow = $(obj).attr("jklbhrow"); //重复添加的监控项
	$('input[jklbhrow="'+jklbhrow+'"]').removeClass("border-color-red");
}

/**
 * 输入框验证
 */
function validateInputNumber2(obj){
	var this_ = this;
	var reg = /^[0-9]+(\.?[0-9]{0,2})?$/;
	var value = $(obj).val();
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	$(obj).val(value);
	var jklbhrow = $(obj).attr("jklbhrow"); //重复添加的监控项
	$('input[jklbhrow="'+jklbhrow+'"]').removeClass("border-color-red");
}

/**
 * @param obj
 */
function changeInput(obj){
	$(obj).removeClass("border-color-red");
}
