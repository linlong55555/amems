

var MonitorUtil = function(param_){
	
	var nonchineseReg = /^[\x00-\xff]*$/;
	
	var cycleReg = /^(0|[1-9]\d*)$/;
	
	var timeReg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
	
	var param = {
		id:'MonitorUtil',
		type : "div",
		isEdit : true,
		title_cn : "监控项设置",
		title_eng : "Monitor Setting",
		showFormula : true,
		showPlane : false,
		planes : [],
		showComponent : false,
		validateCount : false,
		obj : [],
		width : "col-lg-8",
		callback : function(){}
	};
	
	if(param_){
		$.extend(param, param_);
	}
	
	function show(){
		param.monitorId = param.id + "_monitor";
		param.tbodyId = param.id + "_monitor_body";
		initMonitor(function(){
			if(!param.isEdit){
				clearCheck();
			}
		});
		loadData(param.obj);
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
				if(param.type=="div"){
					initMonitorFrame();
				}else{
					initMonitorModal();
				}
				initMonitorHead(data);
				initMonitorBody(data);
				initDateWidget();
				if(param.showComponent){
					initComponent();
				}
				if(param.showFormula){
					initFormula();
				}
				if(param.showPlane){
					initPlane();
				}
				initMonitorDic();
				bindEvent();
				if(typeof(obj)=="function"){
					obj(); 
				}
			}
		});
	}
	
	function initDateWidget(){
		$('#'+param.id+' .datepicker').datepicker({
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
							'<div id="'+param.monitorId+'"></div>',
							'<div class="col-sm-12 padding-left-0 padding-right-0">',
								'<div id="'+param.tbodyId+'" class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0">',
								'</div>',
							'</div>	',
							'<div class="clearfix"></div>',
							'<div class="formula_div"><div>',
						'</div>',
						'</div>',
					'</div>',
					'<div class="clearfix"></div>'].join(''));
	}
	
	/**
	 * 初始化监控设置弹框
	 */
	function initMonitorModal(){
		$("#"+param.id).html([
				                  	'<div class="modal-dialog" style="width:60%;">',
				            		'<div class="modal-content">',
				            			'<div class="modal-header modal-header-new">',
				                     		'<h4 class="modal-title">',
				                         		'<div class="pull-left">',
				                             		'<div class="font-size-12 ">监控项设置</div>',
				            						'<div class="font-size-9">Monitor Setting</div>',
				            					'</div>',
				            	 				'<div class="pull-right">',
				            						'<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>',
				            					'</div>',
				            					'<div class="clearfix"></div>',
				                           	'</h4>',
				                      	'</div>',
				            		
				            			'<div class="modal-body padding-bottom-0">',
				            			  	'<div class="panel panel-primary margin-top-10" '+(param.showComponent?"style='min-height:220px;'":"")+'>',
				            					'<div class="panel-body padding-top-0 padding-bottom-0">',
				            						'<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">',
					            						'<div class="component_div"></div>',
					            						'<div class="col-sm-12 padding-left-0 padding-right-0">',
						            						'<div id="'+param.monitorId+'"></div>',
						        							'<div class="col-sm-12 padding-left-0 padding-right-0">',
						        								'<div id="'+param.tbodyId+'" class="panel-body padding-bottom-0 padding-left-0 padding-right-0 padding-top-0">',
						        								'</div>',
						        							'</div>	',
					            						'</div>',
					            						'<div class="clearfix"></div>',
					            						'<div class="formula_div"></div>',
				            						'</div>',
				            						'<div class="clearfix"></div>',
				            					'</div>',
				            				'</div>',
				            			'</div>',
				            			
				            			'<div class="modal-footer">',
				            	           	'<div class="col-xs-12 padding-leftright-8">',
				            					'<div class="input-group">',
				            					'<span class="input-group-addon modalfootertip">',
				            						'<i class="glyphicon glyphicon-info-sign alert-info" style="display:none;"></i><p class="alert-info-message"></p>',
				            					'</span>',
				            	                   '<span class="input-group-addon modalfooterbtn">',
				            	                        '<button type="button" id="monitor_confirm" class="btn btn-primary padding-top-1 padding-bottom-1">',
				            								'<div class="font-size-12">确定</div>',
				            								'<div class="font-size-9">Confirm</div>',
				            							'</button>',
				            							'<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">',
				            								'<div class="font-size-12">关闭</div>',
				            								'<div class="font-size-9">Close</div>',
				            							'</button>',
				            	                    '</span>',
				            	               	'</div>',
				            				'</div>',
				            			'</div>',
				            			'<div class="clearfix"></div>',
				            		'</div>',
				            	'</div>'].join(''));
	}
	
	/**
	 * 初始化选择部件
	 */
	function initComponent(){
		$("#"+param.id+" .component_div").html(['<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 form-group">',
		            								'<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">',
		            									'<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>部件</div>',
		            									'<div class="font-size-9">Component</div>',
		            								'</label>',
		            								'<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">',
		            									'<input class="form-control" type="text" name="monitor_component_no" maxlength="50">',
		            								'</div>',
		            							'</div>',
		            							
		            							'<input type="hidden" name="monitor_component_id">',
		            							
		            			            	'<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 form-group">',
		            								'<label class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">',
		            									'<div class="font-size-12 margin-topnew-2">厂家/制造商</div>',
		            									'<div class="font-size-9">Manufacturer</div>',
		            								'</label>',
		            								'<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">',
		            									'<input class="form-control" type="text" name="monitor_component_manufacturer" maxlength="100">',
		            								'</div>',
		            							'</div>',
		            							'<div class="clearfix"></div>',].join(""));
		initAutoComplete();
		$("#"+param.id+" input[name='monitor_component_no']").keyup(function(){
			backspace($(this), nonchineseReg);
		});
	}
	
	/**
	 * 初始化飞机
	 */
	function initPlane(){
		$("#"+param.id+" .plane_div").html(['<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">',
			            							'<div class="font-size-12 margin-topnew-2">飞机注册号</div>',
			            							'<div class="font-size-9">A/C REG</div>',
			            						'</label>',
			            						'<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-8">',
			            							'<div id="esyxdiv_plane" class="col-lg-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0">',
			            							'</div>',
			            						'</div>',
			            						'<div class="clearfix"></div>'].join(""));
	}
	
	/**
	 * 初始化计算公式
	 */
	function initFormula(){
		$("#"+param.id+" .formula_div").html([
		            '<div class="col-sm-12 padding-left-0 padding-right-0">',
						'<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
							'<label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">',
								'<div class="font-size-12 margin-topnew-2">计算公式</div>',
								'<div class="font-size-9">Formula</div>',
							'</label>',
							'<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 padding-leftright-8">',
								'<select class="form-control" id="'+param.id+'_jsgs">',
								'</select>',
							'</div>',
						'</div>',
					
						'<div class="col-lg-1 col-md-2 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">',
								'<label class="label-input"><input name="isHdwz" type="checkbox">&nbsp;后到为准</label>',
						'</div>',
						
						'<div class="col-lg-4 col-md-6 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style="display:none" id="'+param.id+'_wz_div">',
							'<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">',
								'<div class="font-size-12 margin-topnew-2">发动机分类</div>',
								'<div class="font-size-9">Engine</div>',
							'</label>',
							'<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">',
								'<select class="form-control" id="'+param.id+'_wz">',
									'<option value="21" selected="selected">1#发</option>',
									'<option value="22">2#发</option>',
									'<option value="23">3#发</option>',
									'<option value="24">4#发</option>',
								'</select>',
							'</div>',
						'</div>',
						
						'<div class="clearfix"></div><div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">',
							'<label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">',
								'<div class="font-size-12 margin-topnew-2">间隔描述</div>',
								'<div class="font-size-9">Remark</div>',
							'</label>',
							'<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 padding-leftright-8">',
							'<textarea style="height: 75px;" class="form-control"  id="'+param.id+'_jgms" maxlength="300"></textarea>',
							'</div>',
						'</div>',
						
					'</div>'].join(""));
	}
	
	/**
	 * 初始化监控设置head
	 */
	function initMonitorHead(data){
		var monitorHtml = '<div class="monitor_setting_css">';
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
		monitorHtml += '</div>';
		$("#"+param.monitorId,$("#"+param.id)).html(monitorHtml);
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
			
			monitorBody += '<div class="'+param.width+' padding-left-0 padding-right-0">';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">首次</div>';
			monitorBody +='<div class="input-group padding-left-8">';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="8" name="sc">';
			monitorBody +=buildUnit(row,'sc');
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-3 col-sm-3 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">周期</div>';
			monitorBody +='<div class="input-group padding-left-8">';
			monitorBody +=' <input class="form-control input-sm" type="text" maxlength="8" name="zq">';
			monitorBody +=buildUnit(row,'zq');
			monitorBody +='</div>';
			monitorBody += '</div>';
			
			monitorBody += '<div class="col-lg-5 col-sm-5 col-xs-12 padding-leftright-8">';
			monitorBody +='<div class="pull-left" style="font-weight:normal;padding-top:8px;">容差</div>';
			monitorBody +='<div class="input-group padding-left-8">';
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
		$("#"+param.tbodyId,$("#"+param.id)).html(monitorBody);
	}
	
	/**
	 * 生成监控项单位
	 */
	function buildUnit(item, type){
		
		var jklbh = item.jklbh;
		var jklObj = MonitorUnitUtil.unitObj[jklbh]||{};
		var html = "";
		
		if(jklObj.subitem){
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
		}else{
			html +='<span class="input-group-addon dw" name="'+type+'_dw" style="min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;">'+MonitorUnitUtil.getMonitorUnit(jklbh)+'</span>';
		}
		return html;
	}
	
	/**
	 * 添加事件
	 */
	function bindEvent(){
		$("#"+param.id+" .monitorItem").click(function(){
			showOrHideMonitor($(this));
		});
		$("#"+param.id+" .calendarUnit").click(function(){
			changeUnit($(this));
		});
		$("#"+param.tbodyId+">div").keyup(function(){
			validateInput($(this));
		});
		
		$("#"+param.id+" #monitor_confirm").click(function(){
			if(param.callback && typeof(param.callback) == "function"){
				var msg = validateMonitorItem();
				if(msg){
					AlertUtil.showModalFooterMessage(msg, param.id);
				}else{
					param.callback();
					$("#"+param.id).modal("hide");
				}
			}
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
		}else{
			backspace(div.find("input[name='sc']"), cycleReg);
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
		
		var bjh = $.trim($("#"+param.id+" input[name='monitor_component_no']").val());
		if(param.showComponent){
			if(!bjh){
				return "请输入部件号！";
			}else{
				var repeat = false;
				$("#component_monitor_list tr").each(function(i, row){
					var rowid = $(row).attr("rowid");
					var rowbjh = $(row).find("td[name='bjh']").text();
					if(param.obj.rowid != rowid && bjh == rowbjh){
						repeat = true;
						return false;
					}
				});
				if(repeat){
					return "部件号重复！";
				}
			}
			if(!nonchineseReg.test(bjh)){
				return "部件号不能包含中文！";
			}
		}
		
		if(param.validateCount && getSelectMonitorNum() == 0){
			return "请至少选择一个监控项！";
		}
		
		var containsScz = false;
		var hasRecord = false;
		$("#"+param.tbodyId+">div:visible").each(function(){
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
			}else{
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
		var showEngine = false;
		$.each(getSelectMonitorItems(), function(i, item){
			var jklbh = $(item).attr("jklbh");
			if(jklbh == "2_30_EH" || jklbh == "3_30_EC"){
				showEngine = true;
			}
		});
		if(showEngine){
			$("#"+param.id+"_wz_div").show();
		}else{
			$("#"+param.id+"_wz_div").hide();
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
	function loadData(obj){
		var list;
		if(param.type == "div"){
			list = obj;
		}else{
			list = obj.projectMonitors;
			$("#"+param.id+" input[name='monitor_component_id']").val(obj.id);
			$("#"+param.id+" input[name='monitor_component_no']").val(obj.bjh);
			$("#"+param.id+" input[name='monitor_component_manufacturer']").val(obj.cj);
		}
		if(list){
			$.each(list, function(i, row){
				$("#"+param.id+" .monitorItem[jklbh='"+row.jklbh+"']").attr("checked",true);
				$("#"+param.id+" div[jklbh='"+row.jklbh+"']").show();
				var jkRow = $("#"+param.tbodyId+">div[jklbh='"+row.jklbh+"']");
				
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
		}
		setCheckDisabled();
	}
	
	/**
	 * 初始化输入框自动完成控件
	 */
	function initAutoComplete(){
	    $("#"+param.id+" input[name='monitor_component_no']").typeahead({
			displayText : function(item){
				return item.bjh;
			},
		    source: function (query, process) {
		    	AjaxUtil.ajax({
					url: basePath+"/material/material/limitTen",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						bjh : query.toUpperCase(),
						dprtcode : param.dprtcode
					}),
					success:function(data){
						process(data);
				    }
				}); 
		    },

            highlighter: function (item) {
                return item;
            },

            updater: function (item) {
                return item;
            }
		});
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
	
	return {
		show : show,
		getMonitorSettingParam : getMonitorSettingParam,
		getComponent : getComponent,
		validateMonitorItem : validateMonitorItem
	};
}

