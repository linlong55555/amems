var uploadObjWireless = {};
var uploadObjWorthiness = {};
var uploadObj= {};
var delAttachements =[];
$(function(){
	uploader();
	//初始化时间控件
	loodingDate_picker();

});

var aircraftinfoModal = {
	id : "aircraftinfoModal",
	planeRegOption : '',
	attachmentSingle : {},
	isLoad:false,//是否加载
	param: {
		id:'',
		modalHeadChina : '飞机基础信息',
		modalHeadEnglish : 'A/C Type Message',
		viewObj:null,
		type:1,
		blOption:true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this.initInfo();
		ModalUtil.showModal(this.id);
//		$("#" + this.id).modal("show");
	},
	//初始化信息
	initInfo : function(){
		var this_ = this;
		//初始化表单验证
		if(!this_.isLoad){
			this_.initFormValidator();
			this_.isLoad = true;
		}
		this_.modalEmpty();
		this_.attachmentSingle = {};
		this_.initModelHead();
		this_.initPlaneModel();
		if(this_.param.viewObj.fjzch){
			this_.returnViewData(this_.param.viewObj);
		}else{
			loodingBase("");
		}
	},
	initFormValidator : function(){//初始化验证
		var this_ = this;
		validation();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#form").data('bootstrapValidator').destroy();
		     $('#form').data('bootstrapValidator', null);
		     validation();
		});
	},
	//标题
	initModelHead : function(){
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
		$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
	},
	//加载机型
	initPlaneModel : function(){
		var this_ = this;
		var data = acAndTypeUtil.getACTypeList({DPRTCODE:this_.param.dprtcode});
	 	var option = '';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	  	 }
	 	$("#fjjx", $("#"+this_.id)).empty();
	 	$("#fjjx", $("#"+this_.id)).append(option);
	},
	//清空
	modalEmpty : function(){
		/*uploadObjWireless = {};
		uploadObjWorthiness = {};
		uploadObj= {};*/
		validation();
		$("#fjjx").attr('disabled',false);	
		$("#fjzch").attr('disabled',false);	
		$("#xlh").attr('disabled',false);	
		$("#fdjsl").attr('disabled',false);	
		$('#aircraftinfoModal input').val("");
		$('#aircraftinfoModal textarea').val("");
		$("#fdjsl").val(2);
		$("#isSsbdw").attr("checked","checked");
		$("#gkSelect").hide();
		$("#attachments_list_tbody").empty();
		$("#aircraftinfo_dprtcode").val('');
		$("#attachments_list_tbody").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
		$("#bj_list_tbody").empty();
		$("#tableTr5").find("td").eq("3").html('');
		$("#tableTr5").find("td").eq("4").html('');
		$("#tableTr5").find("td").eq("5").html('');
		$("#jd").empty();
		uploader();
		appendBj(2);
		$(".modal-body").find("input").removeClass("border-color-red");
		$(".modal-body").find("span").removeClass("border-color-red");

	},
	returnViewData : function(obj){
		var this_ = this;
		$("#fjjx").val(obj.fjjx);
		$("#fjjx").attr('disabled',true);
		$("#fjzch").val(obj.fjzch);
		$("#fjzch").attr('disabled',true);	
		$("#oldFjzch").val(obj.fjzch);
		$("#xlh").val(obj.xlh);
		$("#xlh").attr('disabled',true);
		$("#oldXlh").val(obj.xlh);
		$("#bzm").val(obj.bzm);
		$("#gjdjzfjid").val(obj.gjdjzfjid);
		$("#shzfjid").val(obj.shzfjid);
		$("#wxdtzzfjid").val(obj.wxdtzzfjid);
		if(obj.isSsbdw ==1){
			$("#isSsbdw").attr("checked","checked");
		}else{ 
			$("#isSsbdw").attr("checked",false);
			$("#gkSelect").show();
			var text = (obj.customer?obj.customer.khbm:'') +" "+(obj.customer?obj.customer.khmc:'');
			$("#khText").val(text);
			$("#khid").val(obj.ssdwid);
		}
		$("#gmrq").val(obj.gmrq?obj.gmrq.substring(0,10):'');
		$("#scrq").val(obj.scrq?obj.scrq.substring(0,10):'');
		$("#ccrq").val(obj.ccrq?obj.ccrq.substring(0,10):'');
		$("#rhyzph").val(obj.rhyzph);
		$("#yyyph").val(obj.yyyph);
		$("#gjdjzh").val(obj.gjdjzh);
		$("#gjdjzjkrq").val(obj.gjdjzjkrq?obj.gjdjzjkrq.substring(0,10):'');
		$("#gjdjzyxq").val(obj.gjdjzyxq?obj.gjdjzyxq.substring(0,10):'');
		$("#shzh").val(obj.shzh);
		$("#shzjkrq").val(obj.shzjkrq?obj.shzjkrq.substring(0,10):'');
		$("#shzzjkrq").val(obj.shzzjkrq?obj.shzzjkrq.substring(0,10):'');
		$("#wxdtxkzh").val(obj.wxdtxkzh);
		$("#wxdtbfrq").val(obj.wxdtbfrq?obj.wxdtbfrq.substring(0,10):'');
		$("#dtzzjkrq").val(obj.dtzzjkrq?obj.dtzzjkrq.substring(0,10):'');
		$("#fdjsl").val(obj.fdjsl);
		$("#fdjsl").attr('disabled',true);	
		$("#jsgzjl").val(obj.jsgzjl);
		$("#bz").val(obj.bz);
		$("#aircraftinfo_dprtcode").val(obj.dprtcode);
		//加载基地
		loodingBase(obj.jd);
		appendBj(obj.fdjsl);
		//回显初始化信息
		var planInitList = obj.planInitList;
		$.each(planInitList,function(index,planInit){
			var str = (planInit.wz+"").substring(0,1);
			if(str ==1){
				this_.jshx(planInit);
			}else if(str ==2){
				this_.fdjhx(planInit);
			}else if(str ==3){
				this_.apuhx(planInit);
			}
			
		});
		//回显日使用量信息
		var planUsageList = obj.planUsageList;
		$.each(planUsageList,function(index,planUsage){
			this_.rsyhx(planUsage);
		});
		var fjIds = [];
		if(obj.gjdjzfjid != null && obj.gjdjzfjid !=''){
			fjIds.push(obj.gjdjzfjid);
		}
		if(obj.shzfjid != null && obj.shzfjid !=''){
			fjIds.push(obj.shzfjid);
		}
		if(obj.wxdtzzfjid != null && obj.wxdtzzfjid !=''){
			fjIds.push(obj.wxdtzzfjid);
		}
		if(fjIds.length >0){
			//回显附件
			loadDtzzfj([obj.gjdjzfjid, obj.shzfjid, obj.wxdtzzfjid]);
		}
		
 		
	},
	rsyhx: function(planUsage){
		if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_10_FH"){
			$("#rq_fj").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
			$("#rq_fj").next().val(planUsage.id);
		}
		if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_30_EH"){
			$("#rq_fdj").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
			$("#rq_fdj").next().val(planUsage.id);
		}
		if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_20_AH"){
			$("#rq_apu").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
			$("#rq_apu").next().val(planUsage.id);
		}
		if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_10_FC"){
			$("#xh_fj").val(planUsage.rsyl);
			$("#xh_fj").next().val(planUsage.id);
		}
		if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_30_EC"){
			$("#xh_fdj").val(planUsage.rsyl);
			$("#xh_fdj").next().val(planUsage.id);
		}
		if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_20_AC"){
			$("#xh_apu").val(planUsage.rsyl);
			$("#xh_apu").next().val(planUsage.id);
		}
	},
	jshx: function(planInit){
		if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
			$("#jscshrq").val(planInit.csz);
			$("#jscshrq").next().val(planInit.id);
		}
		if(planInit.jkflbh =="2T" && planInit.jklbh=="2_10_FH"){
			$("#jsfxxs").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
			$("#jsfxxs").next().val(planInit.id);
		}
		if(planInit.jkflbh =="3C" && planInit.jklbh=="3_10_FC"){
			$("#jsfxxh").val(planInit.csz);
			$("#jsfxxh").next().val(planInit.id);
		}
	},
	apuhx: function(planInit){
		$("#tableTr5").find("input[name='bj_jh']").val(planInit.jh);
		$("#tableTr5").find("input[name='bj_xlh']").val(planInit.xlh);
		$("#tableTr5").find("td").eq(3).html(planInit.hCMainData?planInit.hCMainData.xingh:'');
		$("#tableTr5").find("td").eq(4).html(planInit.hCMainData?planInit.hCMainData.ywms:'');
		$("#tableTr5").find("td").eq(5).html(planInit.hCMainData?planInit.hCMainData.zwms:'');
		if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
			$("#tableTr5").find("input[name='rq']").val(planInit.csz);
			$("#tableTr5").find("input[name='rq']").next().val(planInit.id);
		}
		if(planInit.jkflbh =="2T" && planInit.jklbh=="2_20_AH"){
			$("#tableTr5").find("input[name='xs']").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
			$("#tableTr5").find("input[name='xs']").next().val(planInit.id);
		}
		if(planInit.jkflbh =="3C" && planInit.jklbh=="3_20_AC"){
			$("#tableTr5").find("input[name='xh']").val(planInit.csz);
			$("#tableTr5").find("input[name='xh']").next().val(planInit.id);
		}
		
	},
	fdjhx: function(planInit){
		var i =(planInit.wz+"").substring(1,2);
		$("#tableTr"+i).find("input[name='bj_jh']").val(planInit.jh);
		$("#tableTr"+i).find("input[name='bj_xlh']").val(planInit.xlh);
		$("#tableTr"+i).find("td").eq(3).html(planInit.hCMainData?planInit.hCMainData.xingh:'');
		$("#tableTr"+i).find("td").eq(4).html(planInit.hCMainData?planInit.hCMainData.ywms:'');
		$("#tableTr"+i).find("td").eq(5).html(planInit.hCMainData?planInit.hCMainData.zwms:'');
		if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
			$("#tableTr"+i).find("input[name='rq']").val(planInit.csz);
			$("#tableTr"+i).find("input[name='rq']").next().val(planInit.id);
		}
		if(planInit.jkflbh =="2T" && planInit.jklbh=="2_30_EH"){
			$("#tableTr"+i).find("input[name='xs']").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
			$("#tableTr"+i).find("input[name='xs']").next().val(planInit.id);
		}
		if(planInit.jkflbh =="3C" && planInit.jklbh=="3_30_EC"){
			$("#tableTr"+i).find("input[name='xh']").val(planInit.csz);
			$("#tableTr"+i).find("input[name='xh']").next().val(planInit.id);
		}
	},
	showOrHideAttach : function(box,single){
		var this_ = this;
		if($('#'+box, $("#"+this_.id)).is(':checked')) {
			$('#'+single+' .singlefile', $("#"+this_.id)).show();
		}else{
			$('#'+single+' .singlefile', $("#"+this_.id)).hide();
		}
	},
	vilidateData : function(){
		var jx = $.trim($("#jx", $("#"+this_.id)).val());
		var bb = $.trim($("#bb", $("#"+this_.id)).val());
		var wcscmsYw = $.trim($("#wcscmsYw").val());
		if(null == jx || "" == jx){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		if(null == bb || "" == bb){
			AlertUtil.showErrorMessage("请输入版本!");
			return false;
		}
		return true;
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		var this_ = this;
		//表单验证
		$('#form').data('bootstrapValidator').validate();
		if(!$('#form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		//验证是否选择归属
		if($("#isSsbdw").attr("checked") !='checked'){
			var kh = $("#khid").val();
			if(kh == null || kh == ""){
				AlertUtil.showModalFooterMessage("不勾选本单位时，请选择客户!");
				return false;
			}
		}
		//获取基本信息
		var data = this.getAircraftinfoData();
		//获取附件信息
		data.attachmentList = this_.getAttachment();
		//获取初始化信息
		var planInitObj= this_.getPlaninit();
		if(planInitObj.folse){
			AlertUtil.showModalFooterMessage(planInitObj.message);
			return false;
		}else{
			data.planInitList = planInitObj.planinitList;
		}
		//判断是否件号和序列号有重复
		var b= this_.repeat(planInitObj.trObj);
		if(b){
			AlertUtil.showModalFooterMessage("关键部件初始信息件号和序列号出现重复!");
			return false;
		}
		var planUsageObj= this_.getPlanUsage();
		if(planUsageObj.folse){
			AlertUtil.showModalFooterMessage(planUsageObj.message);
			return false;
		}else{
			data.planUsageList = planUsageObj.planUsageList;
		}
		//删除附件ids
		data.delAttachements = delAttachements;
		//回调
		this_.param.callback(data);
	},
	repeat: function(list){
		var folse = false;
		for(var a = 0;a<list.length-1;a++){
			for(var b = a+1;b<list.length;b++){
				if(list[a].jh == list[b].jh && list[a].xlh == list[b].xlh){
					folse = true;
				}
			}
		}
		return folse;
	},
	/**
	 * 组装数据
	 */
	getAircraftinfoData: function(){
		var  this_= this;
		var data = {};
		var paramsMap = {};
		var oldFjzch = $("#oldFjzch").val();
		var oldXlh = $("#oldXlh").val();
		if(oldFjzch != null &&　oldFjzch　!=""){
			paramsMap.oldFjzch = oldFjzch;
		}
		if(oldXlh != null &&　oldXlh　!=""){
			paramsMap.oldXlh = oldXlh;
		}
		data.paramsMap = paramsMap;
		data.fjjx = $.trim($("#fjjx").val());
		data.fjzch = $.trim($("#fjzch").val());
		data.xlh = $.trim($("#xlh").val());
		data.bzm = $.trim($("#bzm").val());
		data.jd = $.trim($("#jd").val());
		if($("#isSsbdw").attr("checked")){
			data.isSsbdw = 1;
			data.ssdwid = "";
		}else{
			data.isSsbdw = 0;
			data.ssdwid = $("#khid").val();
		}
		data.gjdjzfjid = $.trim($("#gjdjzfjid").val());
		data.shzfjid = $.trim($("#shzfjid").val());
		data.wxdtzzfjid = $.trim($("#wxdtzzfjid").val());
		data.gmrq = $("#gmrq").val();
		data.scrq = $("#scrq").val();
		data.ccrq = $("#ccrq").val();
		data.rhyzph = $.trim($("#rhyzph").val());
		data.yyyph = $.trim($("#yyyph").val());
		data.gjdjzh = $.trim($("#gjdjzh").val());
		data.gjdjzjkrq = $("#gjdjzjkrq").val();
		data.shzh = $.trim($("#shzh").val());
		data.shzjkrq = $("#shzjkrq").val();
		data.shzzjkrq = $("#shzzjkrq").val();
		data.wxdtxkzh = $.trim($("#wxdtxkzh").val());
		data.wxdtbfrq = $("#wxdtbfrq").val();
		data.dtzzjkrq = $("#dtzzjkrq").val();
		data.fdjsl = $.trim($("#fdjsl").val());
		data.jsgzjl = $.trim($("#jsgzjl").val());
		data.bz = $.trim($("#bz").val());
		data.dprtcode = $("#aircraftinfo_dprtcode").val();
		data.gjdjzyxq=$("#gjdjzyxq").val();
		return data;
	},
	getPlanUsage: function(){
		var obj = {};
		obj.folse = false;
		obj.message = '';
		var planUsageList = [];
		planUsageList.push({
			'jklbh':"2_10_FH"
			,'jkflbh':"2T"
			,'rsyl':(TimeUtil.convertToMinute($("#rq_fj").val(), TimeUtil.Separator.COLON))==0?'':(TimeUtil.convertToMinute($("#rq_fj").val(), TimeUtil.Separator.COLON))
			,'id':$("#rq_fj").next().val()
		});
		if(!timeReg.test($("#rq_fj").val()) && $("#rq_fj").val()!= "" && $("#rq_fj").val()!= null){
			obj.folse = true;
			obj.message = '日使用量格式不正确';
			$("#rq_fj").addClass("border-color-red");
			$("#rq_fj").next().next().addClass("border-color-red");
			return obj
		}
		planUsageList.push({
			'jklbh':"2_30_EH"
			,'jkflbh':"2T"
			,'rsyl':TimeUtil.convertToMinute($("#rq_fdj").val(), TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToMinute($("#rq_fdj").val(), TimeUtil.Separator.COLON)
			,'id':$("#rq_fdj").next().val()
		});
		if(!timeReg.test($("#rq_fdj").val()) && $("#rq_fdj").val()!= "" && $("#rq_fdj").val()!= null){
			obj.folse = true;
			obj.message = '日使用量格式不正确';
			$("#rq_fdj").addClass("border-color-red");
			$("#rq_fdj").next().next().addClass("border-color-red");
			return obj
		}
		planUsageList.push({
			'jklbh':"2_20_AH"
			,'jkflbh':"2T"
			,'rsyl':(TimeUtil.convertToMinute($("#rq_apu").val(), TimeUtil.Separator.COLON))==0?'':(TimeUtil.convertToMinute($("#rq_apu").val(), TimeUtil.Separator.COLON))
			,'id':$("#rq_apu").next().val()
		});
		if(!timeReg.test($("#rq_apu").val()) && $("#rq_apu").val()!= "" && $("#rq_apu").val()!= null){
			obj.folse = true;
			obj.message = '日使用量格式不正确';
			$("#rq_apu").addClass("border-color-red");
			$("#rq_apu").next().next().addClass("border-color-red");
			return obj
		}
		planUsageList.push({
			'jklbh':"3_10_FC"
			,'jkflbh':"3C"
			,'rsyl':$("#xh_fj").val()
			,'id':$("#xh_fj").next().val()
		});
//		if(!cycleReg.test($("#xh_fj").val()) && $("#xh_fj").val()!= "" && $("#xh_fj").val()!= null){
//			obj.folse = true;
//			obj.message = '日使用量格式不正确';
//			$("#xh_fj").addClass("border-color-red");
//			$("#xh_fj").next().next().addClass("border-color-red");
//			return obj
//		}
		planUsageList.push({
			'jklbh':"3_30_EC"
			,'jkflbh':"3C"
			,'rsyl':$("#xh_fdj").val()
			,'id':$("#xh_fdj").next().val()
		});
//		if(!cycleReg.test($("#xh_fdj").val()) && $("#xh_fdj").val()!= "" && $("#xh_fdj").val()!= null){
//			obj.folse = true;
//			obj.message = '日使用量格式不正确';
//			$("#xh_fdj").addClass("border-color-red");
//			$("#xh_fdj").next().next().addClass("border-color-red");
//			return obj
//		}
		planUsageList.push({
			'jklbh':"3_20_AC"
			,'jkflbh':"3C"
			,'rsyl':$("#xh_apu").val()
			,'id':$("#xh_apu").next().val()
		});
//		if(!cycleReg.test($("#xh_apu").val()) && $("#xh_apu").val()!= "" && $("#xh_apu").val()!= null){
//			obj.folse = true;
//			obj.message = '日使用量格式不正确';
//			$("#xh_apu").addClass("border-color-red");
//			$("#xh_apu").next().next().addClass("border-color-red");
//			return obj
//		}
		obj.planUsageList =planUsageList;
		return obj;
	},
	getPlaninit: function(){
		var obj = {};
		obj.folse = false;
		obj.message = '';
		var planinitList = [];
		var trObj = [];
		//机身监控
		planinitList.push({
			'wz' : 11
			,'jklbh' : '1_10'
			,'jkflbh' : '1D'
			,'csz' : $("#jscshrq").val()
			,'id':$("#jscshrq").next().val()
		});
		planinitList.push({
			'wz' : 11
			,'jklbh' : '2_10_FH'
			,'jkflbh' : '2T'
			,'csz' : (TimeUtil.convertToMinute($("#jsfxxs").val(), TimeUtil.Separator.COLON))==0?'':(TimeUtil.convertToMinute($("#jsfxxs").val(), TimeUtil.Separator.COLON))
			,'id':$("#jsfxxs").next().val()
		});
		if(!timeReg.test($("#jsfxxs").val()) && $("#jsfxxs").val()!= "" && $("#jsfxxs").val()!= null){
			obj.folse = true;
			obj.message = '机身飞行小时格式不正确';
			$("#jsfxxs").addClass("border-color-red");
			$("#jsfxxs").next().next().addClass("border-color-red");
			return obj
		}
		planinitList.push({
			'wz' : 11
			,'jklbh' : '3_10_FC'
			,'jkflbh' : '3C'
			,'csz' : $("#jsfxxh").val()
			,'id':$("#jsfxxh").next().val()
		});
		//发动机监控
		$("#bj_list_tbody").find("tr").each(function(){
			var this_tr = this;
			var jh = $.trim($(this_tr).find("input[name='bj_jh']").val());
			var xlh = $.trim($(this_tr).find("input[name='bj_xlh']").val());
			var wz = 20 + $(this_tr).index()+1;
			var rq = $(this_tr).find("input[name='rq']").val();
			var rq_id = $(this_tr).find("input[name='rq']").next().val();
			var xs = $(this_tr).find("input[name='xs']").val();
			var xs_id = $(this_tr).find("input[name='xs']").next().val();
			var xh = $(this_tr).find("input[name='xh']").val();
			var xh_id = $(this_tr).find("input[name='xh']").next().val();
			trObj.push({
				"jh":jh,
				"xlh":xlh
			});
			if(jh == "" || jh == null){
				obj.folse = true;
				obj.message = '件号不能为空';
				$(this_tr).find("input[name='bj_jh']").addClass("border-color-red");
				return obj;
			}
			if(xlh == "" || xlh == null){
				obj.folse = true;
				obj.message = '序列号不能为空';
				$(this_tr).find("input[name='bj_xlh']").addClass("border-color-red");
				return obj;
			}
			if(rq == "" || rq == null){
				obj.folse = true;
				obj.message = '初始化日期不能为空';
				$(this_tr).find("input[name='rq']").addClass("border-color-red");
				return obj;
			}
			if(!nonchineseReg.test(xlh)){
				obj.folse = true;
				obj.message = '序列号不能输入中文';
				$(this_tr).find("input[name='bj_xlh']").addClass("border-color-red");
				return obj;
			}
			if(!timeReg.test(xs) && xs!= "" && xs!= null){
				obj.folse = true;
				obj.message = '初始化小时格式不正确';
				$(this_tr).find("input[name='xs']").addClass("border-color-red");
				$(this_tr).find("span[name='xs']").addClass("border-color-red");
				return obj;
			}
			if(!cycleReg.test(xh) && xh!= "" && xh!= null){
				obj.folse = true;
				obj.message = '初始化循环不正确';
				$(this_tr).find("input[name='xh']").addClass("border-color-red");
				$(this_tr).find("span[name='xh']").addClass("border-color-red");
				return obj;
			}
			
			planinitList.push({
				 'wz' : wz
				,'jklbh' : '1_10'
				,'jkflbh' : '1D'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : rq
				,'id' : rq_id
			});
			planinitList.push({
				 'wz' : wz
				,'jklbh' : '2_30_EH'
				,'jkflbh' : '2T'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : (TimeUtil.convertToMinute(xs, TimeUtil.Separator.COLON))==0?'':(TimeUtil.convertToMinute(xs, TimeUtil.Separator.COLON))
				,'id' : xs_id
			});
			planinitList.push({
				 'wz' : wz
				,'jklbh' : '3_30_EC'
				,'jkflbh' : '3C'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : xh
				,'id' : xh_id
			});
		});
		
		//APU监控
		var this_tr = $("#tableTr5");
		var jh = $.trim($(this_tr).find("input[name='bj_jh']").val());
		var xlh = $.trim($(this_tr).find("input[name='bj_xlh']").val());
		var rq = $(this_tr).find("input[name='rq']").val();
		var rq_id = $(this_tr).find("input[name='rq']").next().val();
		var xs = $(this_tr).find("input[name='xs']").val();
		var xs_id = $(this_tr).find("input[name='xs']").next().val();
		var xh = $(this_tr).find("input[name='xh']").val();
		var xh_id = $(this_tr).find("input[name='xh']").next().val();
		trObj.push({
			"jh":jh,
			"xlh":xlh
		});
		if(jh == "" || jh == null){
			obj.folse = true;
			obj.message = '件号不能为空';
			$(this_tr).find("input[name='bj_jh']").addClass("border-color-red");
			return obj;
		}
		if(xlh == "" || xlh == null){
			obj.folse = true;
			obj.message = '序列号不能为空';
			$(this_tr).find("input[name='bj_xlh']").addClass("border-color-red");
			return obj;
		}
		if(rq == "" || rq == null){
			obj.folse = true;
			obj.message = '初始化日期不能为空';
			$(this_tr).find("input[name='rq']").addClass("border-color-red");
			return obj;
		}
		if(!nonchineseReg.test(xlh)){
			obj.folse = true;
			obj.message = '序列号不能输入中文';
			$(this_tr).find("input[name='bj_xlh']").addClass("border-color-red");
			return obj;
		}
		if(!timeReg.test(xs) && xs!= "" && xs!= null){
			obj.folse = true;
			obj.message = '初始化小时格式不正确';
			$(this_tr).find("input[name='xs']").addClass("border-color-red");
			$(this_tr).find("span[name='xs']").addClass("border-color-red");
			return obj;
		}
		if(!cycleReg.test(xh) && xh!= "" && xh!= null){
			obj.folse = true;
			obj.message = '初始化循环不正确';
			$(this_tr).find("input[name='xh']").addClass("border-color-red");
			$(this_tr).find("span[name='xh']").addClass("border-color-red");
			return obj;
		}
			planinitList.push({
				 'wz' : 31
				,'jklbh' : '1_10'
				,'jkflbh' : '1D'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : rq
				,'id' : rq_id
			});
			planinitList.push({
				 'wz' : 31
				,'jklbh' : '2_20_AH'
				,'jkflbh' : '2T'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : (TimeUtil.convertToMinute(xs, TimeUtil.Separator.COLON))==0?'':TimeUtil.convertToMinute(xs, TimeUtil.Separator.COLON)
				,'id' : xs_id
			});
			planinitList.push({
				 'wz' : 31
				,'jklbh' : '3_20_AC'
				,'jkflbh' : '3C'
				,'jh' : jh
				,'xlh' : xlh
				,'csz' : xh
				,'id' : xh_id
			});
		
		obj.planinitList = planinitList;
		obj.trObj = trObj;
		return obj;
	},
	getAttachment: function(){
		
		var obj = [uploadObjWireless,uploadObjWorthiness,uploadObj];
		var attachmentList = [];
		for(var i = 0;i<obj.length;i++){
			if(obj[i].responses.length > 0){
				var responses = obj[i].responses;
				$.each(responses,function(index,row){
					attachmentList.push({
						'yswjm':row.attachments[0].yswjm
						,'wbwjm':row.attachments[0].wbwjm
						,'nbwjm':row.attachments[0].nbwjm
						,'cflj':row.attachments[0].cflj
						,'wjdx':row.attachments[0].wjdx
						,'hzm':row.attachments[0].hzm
						,'type':row.attachments[0].type
					});
					
				});
			}
		}
		return attachmentList;
	}
}

$("#ff").dblclick(function(){
	openzrdw();
})
//选择基地
/*function openzrdw() {
	var this_ = this;
	var dprtname = $("#ff").val();
	var dprtcode = $("#ffid").val();
	departmentModal.show({
		dprtnameList : dprtname,// 部门名称
		dprtcodeList : dprtcode,// 部门id
		type : false,// 勾选类型true为checkbox,false为radio
		parentid : "addModal",
		dprtcode : $("#dprt").val()==""?userJgdm:$("#dprt").val(),// 默认登录人当前机构代码
		callback : function(data) {// 回调函数
			$("#ff").val(data.dprtname);
			$("#ffid").val(data.dprtcode);
			var departmentList = data.departmentList;
			$.each(departmentList, function(index, row) {
				newbmidMap[row.id] = row;
				newbmidList.push(row.id);
			});
		}
	});
}*/
//选择客户
function openkhxx(){
	var khid = $("#khid").val();
	CustomerModal.show({
		selected : khid,// 客户id
		parentid : "aircraftinfoModal",
		dprtcode : $("#aircraftinfo_dprtcode").val()?$("#aircraftinfo_dprtcode").val():userJgdm,// 默认登录人当前机构代码
		callback : function(data) {// 回调函数
			if(data){
				$("#khid").val(data.id);
				$("#khText").val(data.khbm+" "+data.khmc);
			}
		}
	});
}

//关键部件初始化添加或删除一行
function bjUpdate(){
	var fdjsl = $("#fdjsl").val();
	if(fdjsl < 1){
		fdjsl = 1;
		$("#fdjsl").val(1);
	}
	if(fdjsl > 4){
		fdjsl = 4;
		$("#fdjsl").val(4);
	}
	appendBj(fdjsl);
}

//拼接部件
function appendBj(number){
	var appendHtml = '';
	if(number >= 1 && number <= 4){
		//获取当前数据条数
		var tableTr = $("#bj_list_tbody").find("tr").length;
		if(tableTr < number){
			var ten =  number - tableTr
			for(var i= 1;i <= ten;i++){
				var colTr = tableTr+i;
				var tableTrId = "tableTr"+colTr;
				appendHtml += "<tr id="+tableTrId+" >";
				appendHtml += "<td class='text-right'>"+colTr+"#发动机</td>";
				appendHtml += "<td class='text-left'>";
					appendHtml += "<div class='input-group ' style='min-width:100%'>";
					appendHtml += "<input type='text'  value='' name='bj_jh' class='form-control' placeholder='' onblur='selectbj(this)' maxlength='50'  id='ff'>";
						appendHtml += "<span class='input-group-btn' id='ffbmid' >";
							appendHtml += "<button type='button' class='btn btn-default' id='wxrybtn' data-toggle='modal' onclick='openBjxx("+colTr+")'>";
								appendHtml += "<i class='icon-search cursor-pointer'></i>";
							appendHtml += "</button>";
						appendHtml += "</span>";
					appendHtml += "</div>";
				appendHtml += "</td>";
				appendHtml += "<td>";
				appendHtml += "<input type='text' class='form-control' name='bj_xlh' maxlength='50' >";
				appendHtml += "</td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td><input class='form-control date-picker-tb' name='rq' data-date-format='yyyy-mm-dd' type='text' /><input class='form-control' type='hidden' /></td>";
				appendHtml += " <td><div class='input-group'><input class='form-control fxyz' name='xs' type='text' maxlength='12' /><input class='form-control' type='hidden' /><span name='xs' class='input-group-addon dw'  style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EH</span></div></td>";
				appendHtml += "<td><div class='input-group'><input class='form-control fxzsyz' name='xh' type='text' maxlength='12' /><input class='form-control' type='hidden' /><span name='xh' class='input-group-addon dw'  style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EC</span></div></td>";
				appendHtml += "</tr>";
			}
		}
		
		if(tableTr > number){
			var ten =  tableTr - number;
			for(var i= 1;i <= ten;i++){
				$("#bj_list_tbody").find("tr").eq(tableTr-i).remove();
			}
		}
		
	}
	$("#bj_list_tbody").append(appendHtml);
	loodingDate_picker();
	bindEvent();
	tablecs();
}

//回显型号和名称
function selectbj(obj){
	var bjh = $(obj).val();
	var objTr = $(obj).parent().parent().parent();
	$(objTr).find("td").eq(3).html('');
	$(objTr).find("td").eq(4).html('');
	$(objTr).find("td").eq(5).html('');
	if(!bjh){
		return false;
	}
	var dprtcode = userJgdm;
	AjaxUtil.ajax({
		url:basePath+"/material/material/selectByBjhAndDprcode",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify({
			'bjh':bjh,
			'dprtcode':dprtcode
		}),
		success:function(data){
			if(data.hCMainData){
				row = data.hCMainData;
				$(objTr).find("td").eq(3).html(row.xingh);
				$(objTr).find("td").eq(4).html(row.ywms);
				$(objTr).find("td").eq(5).html(row.zwms);
			}
			
			finishWait();
			
	    }
	}); 
	
}

//归属改变事件
function gsChange(){
	if($("#isSsbdw").attr("checked")){
		$("#gkSelect").hide();
	}else{
		$("#gkSelect").show();
	}
}

/***********************附件js*****start**************************************************/
//上传
function uploader(){
//上传
uploadObj = $("#fileuploader").uploadFile({
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 100,
			formData : {
				"fileType" : "fjzch"
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,
			showStatusAfterSuccess : false,
			showStatusAfterError: false,
			/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
			{    data.attachments[0].type=1;
				var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
				 trHtml = trHtml+'<td class="text-center"><div>';
				 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
				 trHtml = trHtml+'</div></td>';
				 trHtml = trHtml+'<td class="text-left">国籍登记证号<input type="hidden" name="type" value="1" /></td>';
				 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				 
				 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
				 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
				 trHtml = trHtml+'<td class="text-center">'+data.attachments[0].czsj+'</td>';
				 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
				 
				 trHtml = trHtml+'</tr>';	 
				 $('#attachments_list_tbody>.non-choice').remove();
				 $('#attachments_list_tbody').append(trHtml);
			}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#gjdjzh_fj').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});
//上传
uploadObjWorthiness = $("#fileuploaderairworthiness").uploadFile(
		{
			
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 100,
			formData : {
				"fileType" : "fjzch"
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,
			showStatusAfterSuccess : false,
			showStatusAfterError: false,
			/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
			{   data.attachments[0].type=2;
				var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
				 trHtml = trHtml+'<td class="text-center"><div>';
				 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
				 trHtml = trHtml+'</div></td>';
				 trHtml = trHtml+'<td class="text-left">适航证号</td>';
				 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				 
				 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
				 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
				 trHtml = trHtml+'<td class="text-center">'+data.attachments[0].czsj+'</td>';
				 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
				 
				 trHtml = trHtml+'</tr>';	 
				 $('#attachments_list_tbody>.non-choice').remove();
				 $('#attachments_list_tbody').append(trHtml);
			}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#shzh_fj').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});
//上传
uploadObjWireless = $("#fileuploaderwireless").uploadFile(
		{
			
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 100,
			formData : {
				"fileType" : "fjzch"
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,
			showStatusAfterSuccess : false,
			showStatusAfterError: false,
			/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
			uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass: "btn btn-default",
			onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
			{
				data.attachments[0].type=3;
				var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
				 trHtml = trHtml+'<td class="text-center"><div>';
				 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
				 trHtml = trHtml+'</div></td>';
				 trHtml = trHtml+'<td class="text-left">无线电台许可证号</td>';
				 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				 
				 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
				 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
				 trHtml = trHtml+'<td class="text-center">'+data.attachments[0].czsj+'</td>';
				 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
				 
				 trHtml = trHtml+'</tr>';	 
				 $('#attachments_list_tbody>.non-choice').remove();
				 $('#attachments_list_tbody').append(trHtml);
			}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wxdtxkzh_fj').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});
}
/**
* 删除附件
* @param newFileName
*/
function delfile(uuid) {
	var obj = [uploadObj,uploadObjWorthiness,uploadObjWireless];
	for(var i=0;i<obj.length;i++){
		var responses = obj[i].responses?obj[i].responses:[];
		var fileArray = [];
		$.each(responses,function(index,row){
			if(row.attachments[0].uuid != uuid){
				fileArray.push(row);
			}
		});
		 obj[i].responses = fileArray;
		 if(obj[i] != null  && obj[i].length != fileArray.length ){
			 obj[i].selectedFiles -= 1;
		 }
	}
	$('#'+uuid).remove();
	if($("#attachments_list_tbody>tr").length == 0){
		$("#attachments_list_tbody").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
	}
}

/**
 * 修改时删除附件
 */
function deletefile(uuid) {
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var obj = [uploadObj,uploadObjWorthiness,uploadObjWireless];
		var fileArray = [];
		for(var i=0;i<obj.length;i++){
			var responses = obj[i].responses?obj[i].responses:[];
			$.each(responses,function(index,row){
				if(row.attachments[0].uuid != uuid){
					fileArray.push(row);
				}
			});
			 obj[i].responses = fileArray;
			 if(obj[i] != null  &&obj[i].length != fileArray.length ){
				 obj[i].selectedFiles -= 1;
			 }
		}
		$('#' + uuid).remove();

	} else {
		$('#' + uuid).remove();
		delAttachements.push(key);
	}
	if($("#attachments_list_tbody > tr").length == 0){
		$("#attachments_list_tbody").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
	}
}
/***********************附件js******end*************************************************/
function loodingDate_picker(){
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn:true
	}).on('hide', function(e) {
		var name = $(this).attr("id");
		  $('#form').data('bootstrapValidator')  
	        .updateStatus(name, 'NOT_VALIDATED',null)  
	        .validateField(name);  
	  });
	
	$('.date-picker-tb').datepicker({
		autoclose : true,
		clearBtn:true
	});
	
}

function loodingBase(jd){
	var dprtcode = $("#aircraftinfo_dprtcode").val()?$("#aircraftinfo_dprtcode").val():userJgdm;
	AjaxUtil.ajax({
		   url:basePath+"/sys/department/queryDepartmentByJd", 
		   type: "post",
		   dataType:"json",
		   data:{
			   'id':dprtcode
		   },
		   success:function(data){
			   if(data != null && data.length > 0){
				   var htmlappend = '<option value="">请选择</option>';
				   $.each(data,function(index,row){
					   if(row.id == jd){
						   htmlappend += "<option selected value='"+row.id+"'>"+row.dprtname+"</option>";
					   }else{
						   htmlappend += "<option value='"+row.id+"'>"+row.dprtname+"</option>";
					   }
				   });
				   
				   $("#jd").html(htmlappend);
			   }
		   }
	 });
	
}

function openBjxx(i){
	var dprtcode = $("#aircraftinfo_dprtcode").val()?$("#aircraftinfo_dprtcode").val():userJgdm;
	var hclxlist = [1];
	var selected = $("#tableTr"+i).find("td").eq(1).find("input").eq(1).val();
	/*if(!selected){
		selected = "";
	}*/
	open_win_material_tools.show({
		multi : false,
		selected : selected,
		parentWinId : "aircraftinfoModal",
		existsIdList:null,//已经选择的集合,id
		existsBjhList:null,//已经选择的集合,部件号
		hclxlist:hclxlist,//已经选择的集合,航材类型
		modalHeadChina : '航材列表',
		modalHeadEnglish : 'Material List',
		dprtcode:dprtcode,
		callback: function(data){//回调函数
			if(data != null ){
				$("#tableTr"+i).find("td").eq(1).find("input").eq(0).val(data.bjh);
				$("#tableTr"+i).find("td").eq(1).find("input").eq(1).val(data.id);
				$("#tableTr"+i).find("td").eq(3).html(data.xingh);
				$("#tableTr"+i).find("td").eq(4).html(data.ywms);
				$("#tableTr"+i).find("td").eq(5).html(data.zwms);
				$("#tableTr"+i).find("td").eq(1).find("input").eq(0).removeClass("border-color-red");
			}
		}
	},true)
}
/**
 * 加载附件
 * @param id
 */
function loadDtzzfj(ids) {
	if(ids!=''){
		
	}
	var strs = ["国籍登记证号","适航证号","无线电台许可证号"];
	AjaxUtil.ajax({
				url : basePath + "/common/loadPlaneDataAttachmentsByMainIds",
				type : "post",
				data : {
					idList: ids
				},
				success : function(data) {
					if (data.success) {
						var attachments = data.attachments;
						var len = data.attachments.length;
						if (len > 0) {
							var trHtml = '';
							for (var i = 0; i < len; i++) {
								trHtml = trHtml
										+ '<tr  id=\''
										+ attachments[i].uuid + '\' key=\''
										+ attachments[i].id + '\' >';
								trHtml = trHtml + '<td><div>';
								trHtml = trHtml
										+ '<i class="icon-trash color-blue cursor-pointer" onclick="deletefile(\''
										+ attachments[i].uuid
										+ '\')" title="删除 Delete">  ';
								trHtml = trHtml + '</div></td>';
								trHtml = trHtml + '<td class="text-left">'+strs[ids.indexOf(attachments[i].mainid)]+'</td>';
								trHtml = trHtml
										+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(attachments[i].hzm)+'"> <a class="cursor-pointer" onclick="downfile(\''
										+ attachments[i].id + '\')" >'
										+ StringUtil.escapeStr(attachments[i].wbwjm) +"."+StringUtil.escapeStr(attachments[i].hzm) + '</a></td>';
								trHtml = trHtml + '<td class="text-left">'
										+ attachments[i].wjdxStr + ' </td>';
								trHtml = trHtml + '<td class="text-left"  title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
										+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
								trHtml = trHtml + '<td>' + attachments[i].czsj
										+ '</td>';
								trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
								trHtml = trHtml + '</tr>';
							}
							$('#attachments_list_tbody').empty();
							$('#attachments_list_tbody').append(trHtml);
						}
						if($("#attachments_list_tbody>tr").length == 0){
							$("#attachments_list_tbody").append('<tr class="non-choice"><td colspan="6">暂无数据 No data.</td></tr>');
						}
					}
				}
			});
}
