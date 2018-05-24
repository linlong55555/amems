/**
 * 合同编辑
 */
mgnt_add_alert_Modal = {
	id : "mgnt_add_alert_Modal",
	editParam : {
		add : 1//新增
		,edit : 2//修改
		,revise : 3//修订
	},
	detailObj : {},//明细对象
	isLoad:false,//是否加载
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	number2Validator : {
		reg : new RegExp("^[0-9]{1,10}$"),
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
	param: {
		id:'',
		htlx: 10,
		editParam:1,//1:新增,2:修改,3:审核,4:批准,5:改版
		modalHeadCN : '合同管理',
		modalHeadENG : 'Contract Mgnt',
		viewObj:{},
		xqqdIdList : [],//需求清单id集合
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
		this.initModelHead();
		this.initDate();
		this.initDic();
		this.initBody();
		this.initBtnShowOrHide();
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化日期
	 */
	initDate : function(){
		$('#htrq_e', $("#"+this.id)).datepicker({
			 autoclose : true,
			 clearBtn : false
		});
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(){
		$("#biz_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('3','biz_e',this.param.dprtcode);
		$("#jffs_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('81','jffs_e',this.param.dprtcode);
	},
	initFormValidator : function(){//初始化验证
		var this_ = this;
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#mgnt_form").data('bootstrapValidator').destroy();
		     $('#mgnt_form').data('bootstrapValidator', null);
		     this_.formValidator();
		});
	},
	/**
	 * 初始化操作按钮显示与隐藏
	 */
	initBtnShowOrHide : function(){
		$(".operation-btn ", $("#"+this.id)).hide();
		if(this.param.editParam == this.editParam.add || this.param.editParam == this.editParam.edit){
			$("#save_btn", $("#"+this.id)).show();
			$("#submit_btn", $("#"+this.id)).show();
		}
		if(this.param.editParam == this.editParam.revise){
			$("#submit_btn", $("#"+this.id)).show();
		}
		if(checkBtnPermissions('material:contract:mgnt:05')){
			if(this.param.editParam != this.editParam.add){
				if(this.param.htlx == 40){
					$("#exportSingle_btn", $("#"+this.id)).show();
				}
				if(this.param.htlx == 10 || this.param.htlx == 20){
					$("#export_btn", $("#"+this.id)).show();
					this.initWebuiPopover();
				}
			}
		}
	},
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('mgnt-export-type','#'+this_.id,function(obj){
			return this_.getContractTyleBtns(obj);
		}, 70);
	},
	getContractTyleBtns : function(obj){//获取合同类型按钮组
		var this_ = this;
		var str = "<div class='button-group-new' style='text-align:center;vertical-align:middle;'>";
		str += "<p class='margin-bottom-0'>";
		str += "<a href='javascript:void(0);' onclick="+this_.id+".exportWord(1) style='padding-left:0px;'>中文版</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a href='javascript:void(0);' onclick="+this_.id+".exportWord(2) style='padding-left:0px;'>英文版</a>";
		str += "</p>";
		str += "</div>";
		return str;
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(){
		this.returnViewData(this.param.viewObj);
		this.setReadOnlyFailure();
	},
	/**
	 * 设置只读/失效
	 */
	setReadOnlyFailure : function(){
		if(this.param.editParam == this.editParam.add){
			$("#hth_e", $("#"+this.id)).removeAttr("readonly");
		}
		if(this.param.editParam == this.editParam.edit || this.param.editParam == this.editParam.revise){
			$("#hth_e", $("#"+this.id)).attr("readonly","readonly");
		}
	},
	/**
	 * 设置相关方
	 */
	setXgf : function(htlx){
		if(htlx == 31 || htlx == 32 || htlx == 50){
			$("#xgfBtn", $("#"+this.id)).hide();
			$("#xgfms_e", $("#"+this.id)).removeClass("readonly-style");
		}else{
			$("#xgfBtn", $("#"+this.id)).show();
			$("#xgfms_e", $("#"+this.id)).addClass("readonly-style");
		}
	},
	returnViewData : function(obj){
		var this_ = this;
		this_.param.id = obj.id;
		this_.param.htlx = obj.htlx;
		$("#hth_e", $("#"+this_.id)).val(obj.hth);
		$("#htrq_e", $("#"+this_.id)).val(obj.htrq);
		$("#htlx_e", $("#"+this_.id)).val(DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx));
		$("#bzrstr_e", $("#"+this_.id)).val(obj.bzrstr);
		$("#xgfid_e", $("#"+this_.id)).val(obj.xgfid);
		$("#xgflx_e", $("#"+this_.id)).val(obj.xgflx);
		$("#xgfms_e", $("#"+this_.id)).val(obj.xgfms);
		$("#bz_e", $("#"+this_.id)).val(obj.bz);
		
		$("#cq_e",$("#"+this_.id)).val('');
		$("#cqid_e",$("#"+this_.id)).val('');
		
		if(obj.biz != null){
			$("#biz_e", $("#"+this_.id)).val(obj.biz);
		}
		
		if(obj.jffs != null){
			$("#jffs_e", $("#"+this_.id)).val(obj.jffs);
		}
		$("#xgfms_e", $("#"+this_.id)).removeAttr("readonly");
		if(obj.xgfid != null){
			$("#xgfms_e", $("#"+this_.id)).attr("readonly","readonly");
		}
		this_.setXgf(obj.htlx);
		this_.initInclude(obj);
	},
	/**
	 * 初始化引入数据
	 */
	initInclude : function(obj){
		var this_ = this;
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : true,
			colOptionhead : true,
			fileType:"contract"
		});//显示附件
		this_.loadDetail(obj);
	},
	/**
	 * 加载明细数据
	 */
	loadDetail : function(obj){
		var this_ = this;
		$("#purchase_include_edit", $("#"+this_.id)).hide();
		$("#repair_include_edit", $("#"+this_.id)).hide();
		$("#rent_include_edit", $("#"+this_.id)).hide();
		if(obj.htlx == 10 || obj.htlx == 40 || obj.htlx == 50){
			$("#purchase_include_edit", $("#"+this_.id)).show();
			this_.detailObj = purchaseAddData;
		}
		if(obj.htlx == 20){
			$("#repair_include_edit", $("#"+this_.id)).show();
			this_.detailObj = repairAddData;
		}
		if(obj.htlx == 31 || obj.htlx == 32){
			$("#rent_include_edit", $("#"+this_.id)).show();
			this_.detailObj = rentAddData;
		}
		//初始化明细
		this_.detailObj.init({
			djid:obj.id,//关联单据id
			cqid : $("#"+this_.id).val(),//默认产权id
			cqbh : $("#"+this_.id).val(),//默认产权名
			htlx : obj.htlx,//合同类型
			xqqdIdList : this_.param.xqqdIdList,//需求清单id集合
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
	},
	/**
	 * 打开相关方弹窗
	 */
	openXgfWin : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var gyslblist = [];
		var modalHeadChina = '';
		var modalHeadEnglish = '';
		if(this_.param.htlx == 10){
			gyslblist = [1];
			modalHeadChina = '航材供应商';
			modalHeadEnglish = 'Aerialmaterial Supplier';
		}
		if(this_.param.htlx == 20){
			gyslblist = [2];
			modalHeadChina = '外委供应商';
			modalHeadEnglish = 'Outsourcing Supplier';
		}
		if(this_.param.htlx == 40){
			gyslblist = [1,2];
			modalHeadChina = '供应商';
			modalHeadEnglish = 'Supplier';
		}
		open_win_firm.show({
			multi : false,
			gyslblist : gyslblist,
			modalHeadChina : modalHeadChina,
			modalHeadEnglish : modalHeadEnglish,
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				var xgfms = '';
				var xgflx = '';
				var xgfid = '';
				$("#xgfms_e", $("#"+this_.id)).removeAttr("readonly");
				if(data != null ){
					xgfms = data.GYSMC;
					xgflx = data.GYSLB;
					xgfid =  data.ID;
					$("#xgfms_e", $("#"+this_.id)).attr("readonly","readonly");
				}
				$("#xgfms_e",$("#"+this_.id)).val(xgfms);
				$("#xgflx_e",$("#"+this_.id)).val(xgflx);
				$("#xgfid_e",$("#"+this_.id)).val(xgfid);
			}
		},true);
	},
	/**
	 * 打开产权弹窗
	 */
	openCqWin : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var cqId = $("#cqid_e", $("#"+this_.id)).val();
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
				$("#cq_e",$("#"+this_.id)).val(cqText);
				$("#cqid_e",$("#"+this_.id)).val(cqid);
				this_.detailObj.setAllCq(cqid, cqText);
			}
		});
	},
	view : function(){
		window.open(basePath+"/project2/workcard/view/" + this.param.viewObj.id);
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		var hth = $.trim($("#hth_e",$("#"+this_.id)).val());
		var biz = $.trim($("#biz_e",$("#"+this_.id)).val());
		var htrq = $.trim($("#htrq_e",$("#"+this_.id)).val());
		
		if(hth != null && hth != ''){
			if(!this_.codeValidator.reg.test(hth)){  
				$("#hth_e", $("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("合同号"+this_.codeValidator.message);
				return false;
			}
		}
		
		if(biz == null || biz == ''){
			$("#biz_e", $("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("请选择币种!");
			return false;
		}
		
		if(htrq == null || htrq == ''){
			AlertUtil.showModalFooterMessage("合同日期不能为空!");
			return false;
		}
		
		return true;
	},
	/**
	 * 验证详情数据
	 */
	vilidateDetailData : function(){
		var this_ = this;
		
		//验证合同明细
		if(!this.detailObj.isValid){
			return false;
		}
		
		return true;
	},
	/**
	 * 保存数据
	 * operationType submit:提交
	 */
	setData: function(operationType){
		var this_ = this;
		$('#mgnt_form', $("#"+this.id)).data('bootstrapValidator').validate();
		if(!$('#mgnt_form', $("#"+this.id)).data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		
		var data = this.getContractData(operationType);
		
		//验证详情数据
		if(!this.vilidateDetailData()){
			return false;
		}
		var paramsMap = {};
		data.paramsMap = paramsMap;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if(operationType != null && operationType != ''){
				paramsMap.operationType = operationType;
				
				AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
				
					this_.param.callback(data);
				
				}});
				
			}else{
				this.param.callback(data);
			}
		}
	},
	getContractData : function(operationType){
		var this_ = this;
		var data = {};
		data.hth = $.trim($("#hth_e", $("#"+this_.id)).val());
		data.htrq = $.trim($("#htrq_e",$("#"+this_.id)).val());
		data.htlx = this_.param.htlx;
		data.biz = $.trim($("#biz_e",$("#"+this_.id)).val());
		data.jffs = $.trim($("#jffs_e",$("#"+this_.id)).val());
		data.xgfid = $.trim($("#xgfid_e",$("#"+this_.id)).val());
		data.xgflx = $.trim($("#xgflx_e",$("#"+this_.id)).val());
		data.xgfms = $.trim($("#xgfms_e",$("#"+this_.id)).val());
		data.bz = $.trim($("#bz_e",$("#"+this_.id)).val());
		data.dprtcode = this_.param.dprtcode;
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		data.attachmentList = attachmentsObj.getAttachments();
		
		data.contractInfoList = this_.detailObj.getData();
		
		return data;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	formValidator : function(){//加载验证
		validatorForm =  $('#mgnt_form', $("#"+this.id)).bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	htrq_e: {
	                validators: {
	                	notEmpty: {
	                        message: '合同日期不能为空'
	                    }
	                }
	            },
	            biz_e: {
	                validators: {
	                	notEmpty: {
	                        message: '币种不能为空'
	                    }
	                }
	            }
	        }
	    });	 
	},
	/**
	 * 导出
	 */
	exportWord : function(tyle){
		var param = {};
		var paramsMap = {};
		param.id = this.param.id;
		param.htlx = this.param.htlx;
		paramsMap.zywlx = tyle;//用于区分中英文
		param.paramsMap = paramsMap;
		$('.mgnt-export-type', $("#"+this.id)).webuiPopover('hide');
		window.open(basePath+"/material/contract/exportWord?paramjson="+JSON.stringify(param));
	}
}
