/**
 * 下次计划起算点
 */
next_plan_starting_point_edit = {
	id:'next_plan_starting_point_edit',
	tbodyId : 'next_plan_starting_point_list',
	isEdit : true,//是否可编辑上次计划
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	isScExists : true,//判断上次计划和上次实际是否存在
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	numberValidator2 : {
		reg : new RegExp("^[0-9]+$"),
		message: "只能输入数值!"
	},
	numberValidator3 : {
		reg : new RegExp("^[-]?[0-9]+$"),
		message: "只能输入数值!"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
	},
	nonchineseReg : /^[\x00-\xff]*$/,
	cycleReg : /^(0|[1-9]\d*)$/,
	timeReg : /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/,
	cycleRegRelfs : /^(0|[-]?[1-9]?\d*)$/,
	timeRegRelfs : /^(0|[-]?[1-9]?\d*)(\:[0-5]?[0-9]?)?$/,
	validateDate : /^(\d{4})\-(\d{2})\-(\d{2})$/,
	param: {
		id : '',//监控对象id
		menuType:1,//菜单类型,1:维修计划初始化,2:飞机维修监控
		editType:1,//编辑类型,1:维修项目监控,2:EO监控
		monitorObj : {},//监控对象数据
		hdwz : '',//后到为准
		jsgs : 1,//计算公式
		zxfs : 1//执行方式
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this_.initInfo();
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(){//初始化信息
		var this_ = this;
		$("#isHdwz", $("#"+this.id)).removeAttr("checked");
		if(this_.param.hdwz == 1){
			$("#isHdwz", $("#"+this.id)).attr("checked", "true");
		}
		var jsgs = "计划与实际取小再加上周期";
		if(this_.param.jsgs == 2){
			jsgs = "实际加上周期";
		}
		if(this_.param.jsgs == 3){
			jsgs = "计划加上周期";
		}
		$("#jsgs", $("#"+this.id)).html(jsgs);
		var monitorObj = this_.param.monitorObj;
		if(monitorObj != null && monitorObj.fJksjid != null){
			this_.isEdit = false;
		}else{
			this_.isEdit = true;
		}
		this_.showOrHide();
		this_.load();
	},
	/**
	 * 显示或隐藏
	 */
	showOrHide : function(){
		//计算公式显示或隐藏
		if(this.param.editType == 2 && this.param.zxfs != 2){
			$("#jsgs_all", $("#"+this.id)).hide();
		}else{
			$("#jsgs_all", $("#"+this.id)).show();
		}
	},
	/**
	 * 加载数据
	 */
	load : function(){
		var this_ = this;
		this_.selectById(this_.param.id,function(obj){
			if(obj != null && obj.length > 0){
				if(this_.param.editType == 2 && this_.param.zxfs != 2){
					$("#exists_last_div", $("#"+this_.id)).hide();
					$("#not_exists_last_div", $("#"+this_.id)).show();
					this_.appendSingle(obj);
				}else{
					$("#exists_last_div", $("#"+this_.id)).show();
					$("#not_exists_last_div", $("#"+this_.id)).hide();
					this_.appendContentHtml(obj);
				}
			}else{
				$("#exists_last_div", $("#"+this_.id)).hide();
				$("#not_exists_last_div", $("#"+this_.id)).hide();
				this_.setNoData();
			}
		});
	},
	/**
	 * 加载单次或多次数据
	 */
	appendSingle: function(list){
		var this_ = this;
		var htmlContent = '';
		MonitorUnitUtil.sort(list, "jklbh");
		$.each(list,function(index,row){
			htmlContent += "<tr>";
			var jkx = StringUtil.escapeStr(MonitorUnitUtil.getMonitorName(row.jklbh));
			var jhz = formatUndefine(this_.convertToHour(row.jklbh, row.jhz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, "");
			var sjz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.sjz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, "");
			var syz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.syz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwZqz);
			if(row.jklbh == "1_10"){
				syz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.syz))+"D";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += jkx;
			htmlContent += "</td>";
			htmlContent += "<td title='"+jhz+"' name='jhz_v' style='text-align:center;vertical-align:middle;font-weight:bold'>"+jhz+"</td>";
			htmlContent += "<td title='"+sjz+"' style='text-align:center;vertical-align:middle;'>"+sjz+"</td>";
			htmlContent += "<td title='"+syz+"' name='syz_v' style='text-align:center;vertical-align:middle;'>"+syz+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#next_plan_list", $("#not_exists_last_div")).empty();
		$("#next_plan_list", $("#not_exists_last_div")).html(htmlContent);
	},
	appendContentHtml: function(list){
		var this_ = this;
		this_.isScExists = false;
		var htmlContent = '';
		MonitorUnitUtil.sort(list, "jklbh");
		$.each(list,function(index,row){
			htmlContent += "<tr>";
			var jkx = StringUtil.escapeStr(MonitorUnitUtil.getMonitorName(row.jklbh));
			
			if(row.monitoringLast == null){
				jkx = "<i class='glyphicon-asterisk color-blue'></i>" + jkx;
			}
			
//			var scz = '';
//			if(formatUndefine(row.scz) != ''){
//				scz = formatUndefine(this_.convertToHour(row.jklbh, row.scz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwScz);
//			}
//			var zqz = '';
//			if(formatUndefine(row.zqz) != ''){
//				zqz = formatUndefine(this_.convertToHour(row.jklbh, row.zqz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwZqz);
//			}
			var rc = "-" + formatUndefine(this_.convertToHour(row.jklbh, row.ydzQ))+ "/+" + formatUndefine(this_.convertToHour(row.jklbh, row.ydzH)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.ydzHdw);
			var jhz = '';
			if(formatUndefine(row.jhz) != ''){
				jhz = formatUndefine(this_.convertToHour(row.jklbh, row.jhz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, "");
			}
			
			var sjz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.sjz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, "");
			
			var syz = '';
//			if(formatUndefine(row.paramsMap.syz) != ''){
//			}
			syz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.syz)) + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwZqz);
			if(row.jklbh == "1_10"){
				syz = formatUndefine(this_.convertToHour(row.jklbh, row.paramsMap.syz))+"D";
			}
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += jkx;
			htmlContent += "<input type='hidden' name='hiddenid' value='"+row.id+"'/>";
			htmlContent += "<input type='hidden' name='jksjid' value='"+row.jksjid+"'/>";
//			htmlContent += "<input type='hidden' name='scz' value='"+formatUndefine(row.scz)+"'/>";
			htmlContent += "<input type='hidden' name='csz' value='"+formatUndefine(row.csz)+"'/>";
			htmlContent += "<input type='hidden' name='currcsz' value='"+formatUndefine(row.paramsMap.csz)+"'/>";
//			htmlContent += "<input type='hidden' name='zqz' value='"+formatUndefine(row.zqz)+"'/>";
			htmlContent += "<input type='hidden' name='jhz' value='"+formatUndefine(row.jhz)+"'/>";
			htmlContent += "<input type='hidden' name='sjz' value='"+formatUndefine(row.paramsMap.sjz)+"'/>";
			htmlContent += "<input type='hidden' name='jklbh' value='"+row.jklbh+"'/>";
			htmlContent += "<input type='hidden' name='jkflbh' value='"+row.jkflbh+"'/>";
			htmlContent += "<input type='hidden' name='dwScz' value='"+row.dwScz+"'/>";
			htmlContent += "<input type='hidden' name='dwZqz' value='"+row.dwZqz+"'/>";
			htmlContent += "<input type='hidden' name='wz' value='"+row.wz+"'/>";
			htmlContent += "<input type='hidden' name='scid' value='"+(row.monitoringLast?row.monitoringLast.id:"")+"'/>";
			htmlContent += "</td>";
			htmlContent += this_.formatLastData(row);
			htmlContent += this_.formatFirstCycleData(row);
//			htmlContent += "<td title='"+scz+"' style='text-align:center;vertical-align:middle;'>"+scz+"</td>";
//			htmlContent += "<td title='"+zqz+"' style='text-align:center;vertical-align:middle;'>"+zqz+"</td>";
			htmlContent += "<td title='"+rc+"' style='text-align:center;vertical-align:middle;'>"+rc+"</td>";
			htmlContent += "<td title='"+jhz+"' name='jhz_v' style='text-align:center;vertical-align:middle;font-weight:bold'>"+jhz+"</td>";
			htmlContent += "<td title='"+sjz+"' style='text-align:center;vertical-align:middle;'>"+sjz+"</td>";
			htmlContent += "<td title='"+syz+"' name='syz_v' style='text-align:center;vertical-align:middle;'>"+syz+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
		$('.datepicker', $("#"+this_.id)).datepicker({
			 autoclose: true,
			 clearBtn:true
		});
	},
	/**
	 * 格式化上次数据
	 */
	formatLastData : function(row){
		var this_ = this;
		var htmlContent = '';
		var scjh = '';
		var scsj = '';
		var jhqsz = formatUndefine(this_.convertToHour(row.jklbh, row.jhqsz));
		var dw = MonitorUnitUtil.getMonitorUnit(row.jklbh, "");
		if(row.monitoringLast != null){
			this_.isScExists = true;
			scjh = formatUndefine(this_.convertToHour(row.jklbh, row.monitoringLast.scjhz));
			scsj = formatUndefine(this_.convertToHour(row.jklbh, row.monitoringLast.scsjz));
		}
		if(row.jklbh == "1_10"){
			if(this_.isEdit && this_.param.menuType == 1 && (this_.param.editType == 1 || this_.param.editType == 3 || (this_.param.editType == 2 && this_.param.zxfs == 2))){
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<input onchange='"+this_.id+".setNextPlanPointDate(this)' value='"+scjh+"' class='form-control datepicker clear-x' name='scjh' type='text' data-date-format='yyyy-mm-dd' />";
				htmlContent += "</td>";
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<input onchange='"+this_.id+".setNextPlanPointDate(this)' value='"+scsj+"' class='form-control datepicker clear-x' name='scsj' type='text' data-date-format='yyyy-mm-dd' />";
				htmlContent += "</td>";
			}else{
				htmlContent += "<td title='"+scjh+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
				htmlContent += "<td title='"+scsj+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
			}
			if(this_.param.editType == 2 && this_.param.zxfs != 2){
				htmlContent += "<td title='"+jhqsz+"' style='text-align:center;vertical-align:middle;'>"+jhqsz+"</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<input onchange='"+this_.id+".onchangeNextPlanDate(this)' value='"+jhqsz+"' class='form-control datepicker clear-x' name='jhqsz' type='text' data-date-format='yyyy-mm-dd' />";
				htmlContent += "</td>";
			}
		}else{
			if(this_.isEdit && this_.param.menuType == 1 && (this_.param.editType == 1 || this_.param.editType == 3 || (this_.param.editType == 2 && this_.param.zxfs == 2))){
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<div class='input-group'>";
				htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".setNextPlanPoint(this)' value='"+scjh+"' class='form-control input-sm clear-x' maxlength='10' name='scjh' type='text' />";
				htmlContent += "<span class='input-group-addon dw' name='dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>"+dw+"</span>";
				htmlContent += "</div>";
				htmlContent += "</td>";
				
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<div class='input-group'>";
				htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".setNextPlanPoint(this)' value='"+scsj+"' class='form-control input-sm clear-x' maxlength='10' name='scsj' type='text' />";
				htmlContent += "<span class='input-group-addon dw' name='dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>"+dw+"</span>";
				htmlContent += "</div>";
				htmlContent += "</td>";
			}else{
				htmlContent += "<td title='"+(scjh?(scjh + dw):'')+"' style='text-align:center;vertical-align:middle;'>";
				htmlContent += (scjh?(scjh + dw):'');
				htmlContent += "</td>";
				
				htmlContent += "<td title='"+(scsj?(scsj + dw):'')+"' style='text-align:center;vertical-align:middle;'>";
				htmlContent += (scsj?(scsj + dw):'');
				htmlContent += "</td>";
			}
			
			if(this_.param.editType == 2 && this_.param.zxfs != 2){
				htmlContent += "<td title='"+(jhqsz?(jhqsz + dw):'')+"' style='text-align:center;vertical-align:middle;'>";
				htmlContent += (jhqsz?(jhqsz + dw):'');
				htmlContent += "</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<div class='input-group'>";
				htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".validNextPlanByQsd(this)' value='"+jhqsz+"' class='form-control input-sm clear-x' maxlength='10' name='jhqsz' type='text' />";
				htmlContent += "<span class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>"+dw+"</span>";
				htmlContent += "</div>";
				htmlContent += "</td>";
			}
		}
		return htmlContent;
	},
	/**
	 * 格式化首次周期数据
	 */
	formatFirstCycleData : function(row){
		var this_ = this;
		var htmlContent = '';
		var scz = '';
		var zqz = '';
		if(formatUndefine(row.scz) != ''){
			scz = formatUndefine(this_.convertToHour(row.jklbh, row.scz));
		}
		if(formatUndefine(row.zqz) != ''){
			zqz = formatUndefine(this_.convertToHour(row.jklbh, row.zqz));
		}
		
		if(row.jklbh == "1_10"){
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<div class='input-group'>";
			htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".onchangeFirstCycleDate(this)' value='"+scz+"' class='form-control input-sm clear-x' maxlength='10' style='margin-top:1px;' name='scz' type='text' />";
			htmlContent += "<span class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>";
			htmlContent += "<select onchange='"+this_.id+".onchangeDw(this)' name='dwScz' class='form-control'>";
			htmlContent += "<option value='11' "+(row.dwScz == 11?"selected='true'":"")+">D</option>";
			htmlContent += "<option value='12' "+(row.dwScz == 12?"selected='true'":"")+")>M</option>";
			htmlContent += "</select>";
			htmlContent += "</span>";
			htmlContent += "</div>";
			htmlContent += "</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<div class='input-group'>";
			htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".onchangeFirstCycleDate(this)' value='"+zqz+"' class='form-control input-sm clear-x' maxlength='10' style='margin-top:1px;' name='zqz' type='text' />";
			htmlContent += "<span class='input-group-addon dw' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>";
			htmlContent += "<select onchange='"+this_.id+".onchangeDw(this)' name='dwZqz' class='form-control'>";
			htmlContent += "<option value='11' "+(row.dwZqz == 11?"selected='true'":"")+">D</option>";
			htmlContent += "<option value='12' "+(row.dwZqz == 12?"selected='true'":"")+")>M</option>";
			htmlContent += "</select>";
			htmlContent += "</span>";
			htmlContent += "</div>";
			htmlContent += "</td>";
			
		}else{
			
			var dwScz = MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwScz);
			var dwZqz = MonitorUnitUtil.getMonitorUnit(row.jklbh, row.dwZqz);
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<div class='input-group'>";
			htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".validNextPlan(this)' value='"+scz+"' class='form-control input-sm clear-x' maxlength='10' name='scz' type='text' />";
			htmlContent += "<span class='input-group-addon dw' name='dwScz' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>"+dwScz+"</span>";
			htmlContent += "</div>";
			htmlContent += "</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<div class='input-group'>";
			htmlContent += "<input jklbh='"+row.jklbh+"' onkeyup='"+this_.id+".validNextPlan(this)' value='"+zqz+"' class='form-control input-sm clear-x' maxlength='10' name='zqz' type='text' />";
			htmlContent += "<span class='input-group-addon dw' name='dwZqz' style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>"+dwZqz+"</span>";
			htmlContent += "</div>";
			htmlContent += "</td>";
		}
		return htmlContent;
	},
	/**
	 * 切换单位
	 */
	onchangeDw : function(obj){
		var $obj = $(obj).parents("tr:first");
		$obj.find("input[name='"+$(obj).attr("name")+"']").val($(obj).val());
		var isExists = this.isExists();
		this.setNextPlanDate($obj, isExists);
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 通过id获取数据
	 */
	selectById : function(id,obj){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/maintenance/initialization/queryMonitoringPlanByJksjid",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	},
	/**
	 * 验证是否存在上次数据
	 */
	isExists : function(){
		var flag = false;
		var this_ = this;
		$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
			var tr_this = this;
			var scjh = $(tr_this).find("input[name='scjh']").val();
			var scsj = $(tr_this).find("input[name='scsj']").val();
			if((scjh != null && scjh != '') || (scsj != null && scsj != '')){
				flag = true;
				return flag;
			}
		});
		if(!this_.isEdit || (this_.param.menuType == 2 && this_.isScExists)){
			flag = true;
		}
		return flag;
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_ = this;
		this_.isValid = true;
		var param = [];
		var len = $("#"+this_.tbodyId,$("#"+this_.id)).find("tr").length;
		if (len == 1) {
			if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
				var tr_this = this;
				var infos = {};
				var monitoringLast = {};
				var hiddenid =$(tr_this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
				var jksjid = $(tr_this).find("input[name='jksjid']").val();
				var jhz = $(tr_this).find("input[name='jhz']").val();
				var jklbh = $(tr_this).find("input[name='jklbh']").val();
				var jkflbh = $(tr_this).find("input[name='jkflbh']").val();
//				var wz = $(tr_this).find("input[name='wz']").val();
				var scid = $(tr_this).find("input[name='scid']").val();
				var scjh = $(tr_this).find("input[name='scjh']").val();
				var scsj = $(tr_this).find("input[name='scsj']").val();
				var jhqsz = $(tr_this).find("input[name='jhqsz']").val();
				var scz = $(tr_this).find("input[name='scz']").val();
				var zqz = $(tr_this).find("input[name='zqz']").val();
				var dwScz = $(tr_this).find("input[name='dwScz']").val();
				var dwZqz = $(tr_this).find("input[name='dwZqz']").val();
				jhqsz = this_.convertToMinute(jklbh, jhqsz);
				scz = this_.convertToMinute(jklbh, scz);
				zqz = this_.convertToMinute(jklbh, zqz);
				
				if(this_.param.menuType == 1 && this_.isEdit){
					scjh = this_.convertToMinute(jklbh, scjh);
					scsj = this_.convertToMinute(jklbh, scsj);
					if(scjh != '' && jklbh != "1_10" && !this_.numberValidator2.reg.test(scjh)){  
						AlertUtil.showModalFooterMessage("上次计划"+this_.numberValidator2.message);
						$(tr_this).find("input[name='scjh']").focus();
						$(tr_this).find("input[name='scjh']").addClass("border-color-red");
						this_.isValid = false;
						return false;
					} 
					if(scsj != '' && jklbh != "1_10" && !this_.numberValidator2.reg.test(scsj)){  
						AlertUtil.showModalFooterMessage("上次实际"+this_.numberValidator2.message);
						$(tr_this).find("input[name='scsj']").focus();
						$(tr_this).find("input[name='scsj']").addClass("border-color-red");
						this_.isValid = false;
						return false;
					} 
					monitoringLast.scjhz = scjh;
					monitoringLast.scsjz = scsj;
				}
				
				if(jhqsz != '' && jklbh != "1_10" && !this_.numberValidator3.reg.test(jhqsz)){  
					AlertUtil.showModalFooterMessage("下次计划起算点"+this_.numberValidator3.message);
					$(tr_this).find("input[name='jhqsz']").focus();
					$(tr_this).find("input[name='jhqsz']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				}
				
				if(scz != '' && !this_.numberValidator2.reg.test(scz)){  
					AlertUtil.showModalFooterMessage("首次"+this_.numberValidator2.message);
					$(tr_this).find("input[name='scz']").focus();
					$(tr_this).find("input[name='scz']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				} 
				
				if(zqz != '' && !this_.numberValidator2.reg.test(zqz)){  
					AlertUtil.showModalFooterMessage("周期"+this_.numberValidator2.message);
					$(tr_this).find("input[name='zqz']").focus();
					$(tr_this).find("input[name='zqz']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				} 
				if(jhz != ''){
					if(jklbh == "1_10"){
						if(!this_.validateDate.test(jhz)){
							AlertUtil.showModalFooterMessage("下次计划值超出日期长度!");
							this_.isValid = false;
							return false;
						}
					}else{
						if(jhz < 0){
							AlertUtil.showModalFooterMessage("下次计划值不能为负数!");
							this_.isValid = false;
							return false;
						}
					}
				}
				
				infos.id = hiddenid;
				infos.jhz = jhz;
				infos.jhqsz = jhqsz;
				infos.scz = scz;
				infos.zqz = zqz;
				infos.dwScz = dwScz;
				infos.dwZqz = dwZqz;
				monitoringLast.id = scid;
				monitoringLast.jklbh = jklbh;
				monitoringLast.jkflbh = jkflbh;
//				monitoringLast.wz = wz;
				infos.monitoringLast = monitoringLast;
				param.push(infos);
			});
		}
		return param;
	},
	/**
	 * 设置日期格式的下次计划起算点
	 */
	setNextPlanPointDate : function(obj){
		var scjh = $(obj).parent().parent().find("input[name='scjh']").val();
		var scsj = $(obj).parent().parent().find("input[name='scsj']").val();
		var jhqsz = '';
		if(scjh != '' && scsj != ''){
			jhqsz = scjh;
			if(this.param.jsgs == 1){
				if(scjh != '' && scsj != ''){
					if(TimeUtil.compareDate(scjh, scsj)){
						jhqsz = scsj;
					}
				}else{
					jhqsz = '';
				}
			}
			if(this.param.jsgs == 2){
				jhqsz = scsj;
			}
		}else{
			jhqsz = scjh?scjh:(scsj?scsj:'');
		}
		$(obj).parent().parent().find("input[name='jhqsz']").val(jhqsz);
		$(obj).parent().parent().find("input[name='jhqsz']").datepicker("update");
		this.setAllNextPlan();
	},
	/**
	 * 改变下次计划起算点(日期格式)
	 */
	onchangeNextPlanDate : function(obj){
		var isExists = this.isExists();
		this.setNextPlanDate($(obj).parent().parent(), isExists);
	},
	/**
	 * 改变首次周期(日期格式)
	 */
	onchangeFirstCycleDate : function(obj){
		var isExists = this.isExists();
		var jklbh = $(obj).attr("jklbh");
		if(MonitorUnitUtil.isTime(jklbh)){
			this.onkeyup4NumRel($(obj), this.timeReg, true);
		}else{
			this.onkeyup4NumRel($(obj), this.cycleReg, false);
		}
		this.setNextPlanDate($(obj).parent().parent().parent(), isExists);
		$(obj).removeClass("border-color-red");
	},
	/**
	 * 设置所有的下次计划
	 */
	setAllNextPlan : function(){
		var this_ = this;
		var isExists = this_.isExists();
		$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
			var tr_this = this;
			var jklbh = $(tr_this).find("input[name='jklbh']").val();
			if(jklbh == "1_10"){
				this_.setNextPlanDate($(tr_this), isExists);
			}else{
				this_.setNextPlan($(tr_this), isExists);
			}
		});
	},
	/**
	 * 设置日期格式的下次计划
	 */
	setNextPlanDate : function($obj, isExists){
		var this_ = this;
		var jhqsz = $obj.find("input[name='jhqsz']").val();
		var scz = $obj.find("input[name='scz']").val();
		var currcsz = $obj.find("input[name='currcsz']").val();
		var zqz = $obj.find("input[name='zqz']").val();
		var sjz = $obj.find("input[name='sjz']").val();
		var jklbh = $obj.find("input[name='jklbh']").val();
		var dwZqz = $obj.find("input[name='dwZqz']").val();
		var dwScz = $obj.find("input[name='dwScz']").val();
		var jhz = '';
		var syz = '';
		TimeUtil.getCurrentDate(function (currentDate){
			jhqsz = jhqsz?jhqsz:currcsz;
			if(jhqsz != null && jhqsz != ''){
				if(isExists){
					if(zqz != null && zqz != ''){
						if(dwZqz == 12){
							jhz = TimeUtil.dateOperator4Month(jhqsz, zqz, TimeUtil.Operation.ADD);
						}else{
							jhz = TimeUtil.dateOperator(jhqsz, zqz, TimeUtil.Operation.ADD);
						}
					}
				}else{
					if(scz != null && scz != ''){
						if(dwScz == 12){
							jhz = TimeUtil.dateOperator4Month(jhqsz, scz, TimeUtil.Operation.ADD);
						}else{
							jhz = TimeUtil.dateOperator(jhqsz, scz, TimeUtil.Operation.ADD);
						}
					}
				}
			}else{
				jhz = '';
			}
			$obj.find("input[name='jhz']").val(jhz);
			$obj.find("td[name='jhz_v']").html(jhz);
			$obj.find("td[name='jhz_v']").attr("title", jhz);
			sjz = sjz?sjz:currentDate;
			if(jhz != ''){
				syz = TimeUtil.dateMinus(sjz, jhz) + "D";
			}
			$obj.find("td[name='syz_v']").html(syz);
			$obj.find("td[name='syz_v']").attr("title", syz);
		});
	},
	/**
	 * 设置下次计划起算点
	 */
	setNextPlanPoint : function(obj){
		var this_ = this;
		var jklbh = $(obj).attr("jklbh");
		if(MonitorUnitUtil.isTime(jklbh)){
			this_.onkeyup4NumRel($(obj), this_.timeReg, true);
		}else{
			this_.onkeyup4NumRel($(obj), this_.cycleReg, false);
		}
		$(obj).removeClass("border-color-red");
		var scjh = $(obj).parent().parent().parent().find("input[name='scjh']").val();
		var scsj = $(obj).parent().parent().parent().find("input[name='scsj']").val();
		scjh = scjh?this_.convertToMinute(jklbh, scjh):0;
		scsj = scsj?this_.convertToMinute(jklbh, scsj):0;
		var jhqsz = '';
		if(scjh != '' && scsj != ''){
			jhqsz = scjh;
			if(this.param.jsgs == 1){
				if(Number(scjh) > Number(scsj)){
					jhqsz = scsj;
				}
			}
			if(this.param.jsgs == 2){
				jhqsz = scsj;
			}
		}else{
			jhqsz = scjh?scjh:(scsj?scsj:'');
		}
		jhqsz = this_.convertToHour(jklbh, jhqsz);
		$(obj).parent().parent().parent().find("input[name='jhqsz']").val(jhqsz);
		this.setAllNextPlan();
	},
	/**
	 * 设置下次计划(根据起算点)
	 */
	validNextPlanByQsd : function(obj){
		var isExists = this.isExists();
		var jklbh = $(obj).attr("jklbh");
		if(MonitorUnitUtil.isTime(jklbh)){
			this.onkeyup4NumRelFs($(obj), this.timeRegRelfs, true);
		}else{
			this.onkeyup4NumRelFs($(obj), this.cycleRegRelfs, false);
		}
		this.setNextPlan($(obj).parent().parent().parent(), isExists);
		$(obj).removeClass("border-color-red");
	},
	/**
	 * 设置下次计划
	 */
	validNextPlan : function(obj){
		var isExists = this.isExists();
		var jklbh = $(obj).attr("jklbh");
		if(MonitorUnitUtil.isTime(jklbh)){
			this.onkeyup4NumRel($(obj), this.timeReg, true);
		}else{
			this.onkeyup4NumRel($(obj), this.cycleReg, false);
		}
		this.setNextPlan($(obj).parent().parent().parent(), isExists);
		$(obj).removeClass("border-color-red");
	},
	/**
	 * 设置下次计划
	 */
	setNextPlan : function($obj, isExists){
		var this_ = this;
		var jhqsz = $obj.find("input[name='jhqsz']").val();
		var scz = $obj.find("input[name='scz']").val();
		var zqz = $obj.find("input[name='zqz']").val();
		var sjz = $obj.find("input[name='sjz']").val();
		var jklbh = $obj.find("input[name='jklbh']").val();
		var dwZqz = $obj.find("input[name='dwZqz']").val();
		var dw = MonitorUnitUtil.getMonitorUnit(jklbh, "");
		var jhz = '';
		var syz = '';
		jhqsz = jhqsz?this_.convertToMinute(jklbh, jhqsz):0;
		scz = scz?this_.convertToMinute(jklbh, scz):0;
		zqz = zqz?this_.convertToMinute(jklbh, zqz):0;
		if(isExists){
			if(zqz != null && zqz != ''){
				jhz = Number(jhqsz) + Number(zqz);
			}
		}else{
			if(scz != null && scz != ''){
				jhz = Number(jhqsz) + Number(scz);
			}
		}
		$obj.find("input[name='jhz']").val(jhz);
		$obj.find("td[name='jhz_v']").html(jhz?(this_.convertToHour(jklbh, jhz)+dw):'');
		$obj.find("td[name='jhz_v']").attr("title", jhz?(this_.convertToHour(jklbh, jhz)+dw):'');
		sjz = sjz?sjz:0;
		if(jhz != ''){
			syz = this_.convertToHour(jklbh, (Number(jhz) - Number(sjz))) + dw;
		}
		$obj.find("td[name='syz_v']").html(syz);
		$obj.find("td[name='syz_v']").attr("title", syz);
	},
	/**
	 * 分钟转小时
	 */
	convertToHour : function(jklbh, value){
		if(MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	/**
	 * 小时转分钟
	 */
	convertToMinute : function(jklbh, value){
		if(value != '' && MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToMinute(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	//只能输入数字和冒号(可以输入负数)
	onkeyup4NumRelFs : function(obj, reg, replace){
		var value = obj.val();
		if(replace){
			value = value.replace(/：/g, ":");
		}
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(validateMax(value));
		function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//只能输入数字和冒号
	onkeyup4NumRel : function(obj, reg, replace){
		var value = obj.val();
		if(replace){
			value = value.replace(/：/g, ":");
		}
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(validateMax(value));
		function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//只能输入数字
	onkeyup4Num : function(obj){
		//先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){
		//先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     
	     var str = obj.value.split(".");
	     if(str.length > 1){
	    	 if(str[1].length > 2){
	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	}
};