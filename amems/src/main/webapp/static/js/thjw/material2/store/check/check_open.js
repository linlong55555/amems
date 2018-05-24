/**
 * 库存
 */
check_open_alert = {
	id : "check_open_alert",
	materialObj : {},
	isLoad:false,//是否加载
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	certificateUtil : {},
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	number2Validator : {
		reg : new RegExp("^[0-9]{1,10}$"),
		message: "只能输入数值!"
	},
	numberTimeValidator : {
		reg : new RegExp("^([0-9]{1,9})(\:[0-5]?[0-9]?)?$"),
		message: "输入格式不正确!"
	},
	numberValidator2 : {
		reg : new RegExp("^[0-9]+$"),
		message: "只能输入数值!"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
	},
	codeValidatorQyZw : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,4000}$"),
		message: "只能输入长度不超过4000个字符的英文、数字、英文特殊字符!"
	},
	cycleReg : /^(0|[1-9]\d*)$/,
	timeReg : /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/,
	param: {
		type : 1,//1航材，2工具设备
		modalHeadCN : '新建库存',
		modalHeadENG : 'Add',
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).on("shown.bs.modal", function() {
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
		});
		//初始化信息
		this_.initInfo();
	},
	initInfo : function(){//初始化信息
		var this_ = this;
		//初始化表单验证
		if(!this.isLoad){
			this.initFormValidator();
			this.isLoad = true;
		}
		this.materialObj = {};
		this.initModelHead();
		this.initDic();
		this.inithcly();
		this.initqczt();
		this_.initStoreSelect();
		this_.initBody();
		$(".border-color-red", $("#"+this.id)).removeClass("border-color-red");
	},
	//初始化航材来源
	inithcly: function(){
		var this_ = this;
		$("#hcly_o").empty();
		DicAndEnumUtil.registerDic('85','hcly_o',this_.param.dprtcode);
	},
	//初始化器材状态
	initqczt: function(){
		var this_ = this;
		$("#qczt_o").empty();
		DicAndEnumUtil.registerDic('86','qczt_o',this_.param.dprtcode);
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(){
		$("#biz_o", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('3','biz_o', this.param.dprtcode);
		$("#jzbz_o", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('3','jzbz_o', this.param.dprtcode);
	},
	initFormValidator : function(){//初始化验证
		var this_ = this;
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#check_open_form", $("#"+this_.id)).data('bootstrapValidator').destroy();
		     $('#check_open_form', $("#"+this_.id)).data('bootstrapValidator', null);
		     this_.formValidator();
		});
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(){
		this.returnViewData();
	},
	returnViewData : function(){
		var this_ = this;
		this_.setMaterialData({});
		$("#pch_o", $("#"+this_.id)).val('');
		$("#xlh_o", $("#"+this_.id)).val('');
		$("#kcsl_o", $("#"+this_.id)).val('');
		$("#kcsl_o", $("#"+this_.id)).removeAttr("readonly");
		$("#jz_o", $("#"+this_.id)).val('');
		$("#kccb_o", $("#"+this_.id)).val('');
		$("#hjsm_o", $("#"+this_.id)).val('');
		$("#syts_o", $("#"+this_.id)).html('');
		$("#syts_o", $("#"+this.id)).hide();
		$("#hcly_o", $("#"+this_.id)).val(1);
		$("#grn_o", $("#"+this_.id)).val('');
		$("#tsn_o", $("#"+this_.id)).val('');
		$("#tso_o", $("#"+this_.id)).val('');
		$("#csn_o", $("#"+this_.id)).val('');
		$("#cso_o", $("#"+this_.id)).val('');
		$("#scrq_o", $("#"+this_.id)).val('');
		$("#cfyq_o", $("#"+this_.id)).val('');
		$("#bz_o", $("#"+this_.id)).val('');
		$("#cqbh_o", $("#"+this_.id)).val('');
		$("#cqid_o", $("#"+this_.id)).val('');
		this_.initInclude();
	},
	initStoreSelect : function(){
		var this_ = this;
		$("#ck_o", $("#"+this_.id)).empty();
		var storeHtml = "";
		var dprtcode = this_.param.dprtcode;
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
				storeHtml += "<option ckh='"+StringUtil.escapeStr(row.ckh)+"' ckmc='"+StringUtil.escapeStr(row.ckmc)+"' cklb='"+StringUtil.escapeStr(row.cklb)+"' value='"+row.id+"'>"+StringUtil.escapeStr(row.ckh)+" " + StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		});
		$("#ck_o", $("#"+this_.id)).append(storeHtml);
		this_.changeStore();
	},
	changeStore : function(){
		var this_ = this;
		var ckid = $.trim($("#ck_o", $("#"+this_.id)).val());
	 	AjaxUtil.ajax({
			async: false,
			url:basePath+"/material/store/queryStorageListByStoreId",
			type:"post",
			data:{storeId : ckid},
			dataType:"json",
			success:function(data){
				this_.buildStorage(data);	
			}
		});
	},
	buildStorage : function(storageList){
		var this_ = this;
		$("#kw_o", $("#"+this_.id)).empty();
	 	var option = '<option value=""></option>';
	 	option = '';
	 	for(var i = 0 ; i < storageList.length ; i++){
	 		var storage = storageList[i];
	 		option += '<option value="'+storage.id+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
	 	}
	 	$("#kw_o", $("#"+this_.id)).append(option);
		$('#kw_o').selectpicker('refresh');
	 	
	},
	/**
	 * 打开产权弹窗
	 */
	openCqWin : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var cqId = $("#cqid_o", $("#"+this_.id)).val();
		cqModal.show({
			selected : cqId,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				var cqText = '';
				var cqid = '';
				if(data != null ){
					cqText = data.cqbh;
					cqid = data.id;
				}
				$("#cqbh_o", $("#"+this_.id)).val(cqText);
				$("#cqid_o", $("#"+this_.id)).val(cqid);
			}
		});
	},
	/**
	 * 打开部件弹窗
	 */
	openMaterialWin : function(){
		var this_ = this;
		var type = this_.param.type;
		var hclxList = [0, 1, 4, 5, 6];
		if(this_.param.type == 2){
			hclxList = [2, 3];
		}
		open_win_material_basic.show({
			multi:false,
			showStock:false,
			type : type,
			hclxList : hclxList,
			dprtcode : this_.param.dprtcode,
			callback: function(data){//回调函数
				if(data){
					this_.materialObj = data;
					data.zywms = StringUtil.escapeStr(data.ywms) + " " + StringUtil.escapeStr(data.zwms);
					this_.setMaterialData(data);
				}else{
					this_.materialObj = {};
					this_.setMaterialData({});
				}
			}
		},true);
	},
	changeNum2 : function(obj){
		this.clearNoNumTwo(obj);
		$(obj).removeClass("border-color-red");
	},
	changeNumRel : function(obj){
		this.onkeyup4NumRel($(obj), this.timeReg, true);
		$(obj).removeClass("border-color-red");
	},
	changeNum : function(obj){
		this.onkeyup4NumRel($(obj), this.cycleReg, false);
		$(obj).removeClass("border-color-red");
	},
	changeBjh : function(obj){
		this.onkeyup4Code(obj);
		$(obj).removeClass("border-color-red");
	},
	changeXlh : function(obj){
		this.onkeyup4Code(obj);
		$(obj).removeClass("border-color-red");
	},
	changeKcsl : function(obj){
		if(obj.value != null && obj.value != ''){
			$("#kcsl_o", $("#"+this.id)).val(1);
			$("#kcsl_o", $("#"+this.id)).attr("disabled", "disabled");
		}else{
			$("#kcsl_o", $("#"+this.id)).removeAttr("disabled");
		}
	},
	changeHjsm : function(){
		var this_ = this;
		var hjsm = $("#hjsm_o", $("#"+this.id)).val();
		$("#syts_o", $("#"+this_.id)).val('');
		$("#syts_o", $("#"+this_.id)).hide();
		if(hjsm != null && hjsm != ''){
			//初始化合同日期
			TimeUtil.getCurrentDate(function (currentDate){
				var syts = TimeUtil.dateMinus(currentDate, hjsm);
				$("#syts_o", $("#"+this_.id)).html(syts);
				$("#syts_o", $("#"+this_.id)).show();
			});
		}
	},
	/**
	 * 加载部件相关信息
	 */
	loadMaterialRel : function(obj){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var bjh = obj.value;
		AjaxUtil.ajax({
			url : basePath+"/material/material/selectByBjhAndDprcode",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				'bjh':bjh,
				'dprtcode':this_.param.dprtcode
			}),
			success:function(data){
				if(data.hCMainData != null){
					var hCMainData = data.hCMainData;
					this_.materialObj = data.hCMainData;
					hCMainData.zywms = StringUtil.escapeStr(hCMainData.ywms) + " " + StringUtil.escapeStr(hCMainData.zwms);
					this_.setMaterialData(hCMainData);
				}else{
					this_.materialObj = {};
					this_.setMaterialData({});
					obj.value = bjh;
				}
		    }
		}); 
	},
	setMaterialData : function(obj){
		$("#bjid_o", $("#"+this.id)).val(obj.id);
		$("#bjh_o", $("#"+this.id)).val(obj.bjh);
		$("#zywms_o", $("#"+this.id)).val(obj.zywms);
		
		$("#jldw_o", $("#"+this.id)).show();
		if(StringUtil.escapeStr(obj.jldw) == ''){
			$("#jldw_o", $("#"+this.id)).hide();
		}
		$("#jldw_o", $("#"+this.id)).html(StringUtil.escapeStr(obj.jldw));
		var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', obj.hclx);
		if(obj.hclxEj != null && obj.hclxEj != ''){
			if(obj.hclx == 1){
				hclx += " " + DicAndEnumUtil.getEnumName('materialSecondTypeEnum', obj.hclxEj);
			}else if(obj.hclx == 2){
				hclx += " " + DicAndEnumUtil.getEnumName('materialToolSecondTypeEnum', obj.hclxEj);
			}
		}
		$("#hclx_o", $("#"+this.id)).val(hclx);
		$("#cjjh_o", $("#"+this.id)).val(obj.cjjh);
		$("#gg_o", $("#"+this.id)).val(obj.gg);
		$("#xingh_o", $("#"+this.id)).val(obj.xingh);
		$("#gljb_o", $("#"+this.id)).val(DicAndEnumUtil.getEnumName('supervisoryLevelEnum', obj.gljb));
		$("#zzcs_o", $("#"+this.id)).val(obj.zzcs);
		$("#maxKcl_o", $("#"+this.id)).val(obj.maxKcl);
		$("#minKcl_o", $("#"+this.id)).val(obj.minKcl);
	},
	/**
	 * 初始化引入数据
	 */
	initInclude : function(){
		var this_ = this;
		this_.certificateUtil = new CertificateUtil({
				parentId : this_.id,
				dprtcode : this_.param.dprtcode,
				tbody : $("#check_open_certificate_list"),
				container : "#"+this_.id,
				ope_type : 1
			});
			
			var obj={
				bjh:'',	
				pch:'',	
				xlh:'',
				dprtcode : this_.param.dprtcode
			};

			this_.certificateUtil.load(obj);
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		var bjid = $.trim($("#bjid_o", $("#"+this_.id)).val());
		var bjh = $.trim($("#bjh_o", $("#"+this_.id)).val());
		var pch = $.trim($("#pch_o", $("#"+this_.id)).val());
		var xlh = $.trim($("#xlh_o", $("#"+this_.id)).val());
		var kcsl = $.trim($("#kcsl_o", $("#"+this_.id)).val());
		var kccb = $.trim($("#kccb_o", $("#"+this_.id)).val());
		var jz = $.trim($("#jz_o", $("#"+this_.id)).val());
		var tsn = $.trim($("#tsn_o", $("#"+this_.id)).val());
		var tso = $.trim($("#tso_o", $("#"+this_.id)).val());
		var csn = $.trim($("#csn_o", $("#"+this_.id)).val());
		var cso = $.trim($("#cso_o", $("#"+this_.id)).val());
		
		if(bjh == null || bjh == ''){
			$("#bjh_o", $("#"+this_.id)).focus();
			AlertUtil.showModalFooterMessage("部件号不能为空!", this_.id);
			$("#bjh_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(!this_.codeValidator.reg.test(bjh)){  
			$("#bjh_o", $("#"+this_.id)).focus();
			AlertUtil.showModalFooterMessage("部件号"+this_.codeValidator.message, this_.id);
			$("#bjh_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(null == bjid || "" == bjid){
			AlertUtil.showModalFooterMessage("部件号不存在!", this_.id);
			$("#bjh_o", $("#"+this_.id)).focus();
			$("#bjh_o", $("#"+this_.id)).addClass("border-color-red");
			this_.isValid = false; 
			return false;
		}
		
		if(pch != null && pch != ''){
			if(!this_.codeValidator.reg.test(pch)){  
				$("#pch_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("批次号"+this_.codeValidator.message, this_.id);
				$("#pch_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(this_.materialObj.gljb == 3 && (xlh == null || xlh == '')){
			$("#xlh_o", $("#"+this_.id)).focus();
			AlertUtil.showModalFooterMessage("序列号管理的航材序列号不能为空!", this_.id);
			$("#xlh_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(xlh != null && xlh != ''){
			if(!this_.codeValidator.reg.test(xlh)){  
				$("#xlh_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("序列号"+this_.codeValidator.message, this_.id);
				$("#xlh_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(null == kcsl || "" == kcsl || kcsl <= 0){
			var message = "请输入库存数量!";
			if(kcsl <= 0){
				message = "库存数量必须大于0!";
			}
			AlertUtil.showModalFooterMessage(message, this_.id);
			$("#kcsl_o", $("#"+this_.id)).focus();
			$("#kcsl_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(!this_.numberValidator.reg.test(kcsl)){  
			AlertUtil.showModalFooterMessage("库存数量"+this_.numberValidator.message, this_.id);
			$("#kcsl_o", $("#"+this_.id)).focus();
			$("#kcsl_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(xlh != null && xlh != '' && kcsl != 1){
			AlertUtil.showModalFooterMessage("填写序列号时，数量必须等于1!", this_.id);
			$("#xlh_o", $("#"+this_.id)).focus();
			$("#xlh_o", $("#"+this_.id)).addClass("border-color-red");
			return false;
		}
		
		if(kccb != null && kccb != ''){
			if(!this_.numberValidator.reg.test(kccb)){  
				$("#kccb_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("库存成本"+this_.numberValidator.message, this_.id);
				$("#kccb_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(jz != null && jz != ''){
			if(!this_.numberValidator.reg.test(jz)){  
				$("#jz_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("价值"+this_.numberValidator.message, this_.id);
				$("#jz_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(tsn != null && tsn != ''){
			if(!this_.numberTimeValidator.reg.test(tsn)){  
				$("#tsn_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("TSN"+this_.numberTimeValidator.message, this_.id);
				$("#tsn_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(tso != null && tso != ''){
			if(!this_.numberTimeValidator.reg.test(tso)){  
				tso = TimeUtil.convertToMinute(tso, TimeUtil.Separator.COLON);
				$("#tso_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("TSO"+this_.numberTimeValidator.message, this_.id);
				$("#tso_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(csn != null && csn != ''){
			if(!this_.numberValidator2.reg.test(csn)){  
				$("#csn_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("CSN"+this_.numberValidator2.message, this_.id);
				$("#csn_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		if(cso != null && cso != ''){
			if(!this_.numberValidator2.reg.test(cso)){  
				$("#cso_o", $("#"+this_.id)).focus();
				AlertUtil.showModalFooterMessage("CSO"+this_.numberValidator2.message, this_.id);
				$("#cso_o", $("#"+this_.id)).addClass("border-color-red");
				return false;
			}
		}
		
		return true;
	},
	/**
	 * 验证详情数据
	 */
	vilidateDetailData : function(){
		var this_ = this;
		
		//验证合同明细
//		if(!this.detailObj.isValid){
//			return false;
//		}
		
		return true;
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		var this_ = this;
		$('#check_open_form', $("#"+this.id)).data('bootstrapValidator').validate();
		if(!$('#check_open_form', $("#"+this.id)).data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!", this_.id);
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		
		var data = this.getData();
		
		//验证详情数据
//		if(!this.vilidateDetailData()){
//			return false;
//		}
		data.componentCertificateList = this_.certificateUtil.getData();
		var paramsMap = {};
		data.paramsMap = paramsMap;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this_.param.callback(data);
		}
	},
	getData : function(){
		var this_ = this;
		var data = {};
		var materialObj = this_.materialObj;
		var bjid = $.trim($("#bjid_o",$("#"+this_.id)).val());
		var bjh = $.trim($("#bjh_o",$("#"+this_.id)).val());
		var pch = $.trim($("#pch_o",$("#"+this_.id)).val());
		var xlh = $.trim($("#xlh_o",$("#"+this_.id)).val());
		var ckid = $.trim($("#ck_o",$("#"+this_.id)).val()); 
		var cklb = $.trim($("#ck_o ", $("#"+this_.id)).find("option:selected").attr("cklb"));
		var ckh = $.trim($("#ck_o ", $("#"+this_.id)).find("option:selected").attr("ckh"));
		var ckmc = $.trim($("#ck_o ", $("#"+this_.id)).find("option:selected").attr("ckmc"));
		var kwid = $.trim($("#kw_o",$("#"+this_.id)).val()); 
		var kwh = $.trim($("#kw_o ", $("#"+this_.id)).find("option:selected").text());
		var kcsl = $.trim($("#kcsl_o",$("#"+this_.id)).val());
		var kccb = $.trim($("#kccb_o",$("#"+this_.id)).val());
		var biz = $.trim($("#biz_o",$("#"+this_.id)).val());
		var jz = $.trim($("#jz_o",$("#"+this_.id)).val());
		var jzbz = $.trim($("#jzbz_o",$("#"+this_.id)).val());
		var cqid = $.trim($("#cqid_o",$("#"+this_.id)).val());
		var hjsm = $.trim($("#hjsm_o",$("#"+this_.id)).val());
		var hcly = $.trim($("#hcly_o",$("#"+this_.id)).val());
		var grn = $.trim($("#grn_o",$("#"+this_.id)).val());
		var tsn = $.trim($("#tsn_o",$("#"+this_.id)).val());
		var tso = $.trim($("#tso_o",$("#"+this_.id)).val());
		var qczt = $.trim($("#qczt_o",$("#"+this_.id)).val());
		if(tsn != null && tsn != ''){
			tsn = TimeUtil.convertToMinute(tsn, TimeUtil.Separator.COLON);
		}
		if(tso != null && tso != ''){
			tso = TimeUtil.convertToMinute(tso, TimeUtil.Separator.COLON);
		}
		var csn = $.trim($("#csn_o",$("#"+this_.id)).val());
		var cso = $.trim($("#cso_o",$("#"+this_.id)).val());
		var scrq = $.trim($("#scrq_o",$("#"+this_.id)).val());
		var cfyq = $.trim($("#cfyq_o",$("#"+this_.id)).val());
		var bz = $.trim($("#bz_o",$("#"+this_.id)).val());
		
		data.bjid = bjid;
		data.bjh = bjh;
		data.hclx = materialObj.hclx;
		data.zwms = materialObj.zwms;
		data.ywms = materialObj.ywms;
		data.jldw = materialObj.jldw;
		data.xh = materialObj.xingh;
		data.gg = materialObj.gg;
		data.zzcs = materialObj.zzcs;
		data.pch = pch;
		data.sn = xlh;
		data.ckid = ckid;
		data.cklb = cklb;
		data.ckh = ckh;
		data.ckmc = ckmc;
		data.ckid = ckid;
		data.kwid = kwid;
		data.kwh = kwh;
		data.kcsl = kcsl;
		data.kccb = kccb;
		data.biz = biz;
		data.jz = jz;
		data.jzbz = jzbz;
		data.cqid = cqid;
		data.hjsm = hjsm;
		data.hcly = hcly;
		data.grn = grn;
		data.tsn = tsn;
		data.tso = tso;
		data.csn = csn;
		data.cso = cso;
		data.scrq = scrq;
		data.cfyq = cfyq;
		data.bz = bz;
		data.qczt = qczt;
		data.gljb = materialObj.gljb;
		
		return data;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	formValidator : function(){//加载验证
		validatorForm =  $('#check_open_form', $("#"+this.id)).bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	bjh_o: {
	                validators: {
	                	notEmpty: {
	                        message: '部件号不能为空'
	                    }
	                }
	            },
	            kcsl_o: {
	                validators: {
	                	notEmpty: {
	                        message: '库存数量不能为空'
	                    }
	                }
	            }
	        }
	    });	 
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
	clearNoNumTwo : function(obj){//只能输入数字和小数,保留两位
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
	    	 if(Number(_value) > 99999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	onkeyup4Code : function(obj){
		obj.value = obj.value.replaceAll("−","-");
		var reg = new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$");
		
	     obj.value = validate(obj.value);
	     function validate(_value){
	    	 if(_value.length == 0){
	    		 return "";
	    	 }
	    	 if(!reg.test(_value)){
	    		return validate(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	}
}
