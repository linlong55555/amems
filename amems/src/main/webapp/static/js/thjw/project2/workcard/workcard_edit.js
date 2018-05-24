/**
 * 工卡编辑
 */
work_card_edit_alert_Modal = {
	id : "work_card_edit_alert_Modal",
	planeRegOption : '',
	accessList : [],
	accessIdList : [],
	positionList : [],
	positionIdList : [],
	zoneOption : '',
	oldId : '',
	editParam : {
		add : 1//新增
		,edit : 2//修改
		,audit : 3//审核
		,approve : 4//批准
		,modify : 5//改版
	},
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
		editParam:1,//1:新增,2:修改,3:审核,4:批准,5:改版
		modalHeadCN : '工卡管理',
		modalHeadENG : 'Work Card',
		viewObj:{},
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
//		ModalUtil.showModal(this.id);
		$("#"+this.id).modal("show");
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).on("shown.bs.modal", function() {
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			this_.resizeVersionWidth();
		});
		//初始化信息
		this_.initInfo();
	},
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#"+this_.id,function(obj){
			return this_.getHistoryBbList(obj);
		});
	},
	getHistoryBbList : function(obj){//获取历史版本列表
	
		return work_card_history_alert_Modal.getHistoryBbList(this.param.viewObj.id);
	},
	resizeVersionWidth : function(){
		$("#maintenance_version_list div[name='jkxm']").width($("#maintenance_version_jkxm").outerWidth());
		$("#maintenance_version_list div[name='sj']").width($("#maintenance_version_sj").outerWidth());
		$("#maintenance_version_list div[name='zq']").width($("#maintenance_version_zq").outerWidth());
		$("#maintenance_version_list div[name='rc']").width($("#maintenance_version_rc").outerWidth()-10);
	},
	initInfo : function(){//初始化信息
		var this_ = this;
		//初始化表单验证
		if(!this.isLoad){
			this.initFormValidator();
			this.isLoad = true;
		}
		this.initModelHead();
		this.initPlaneModel();
		this.initWorkGroup();
		this.initDic();
		this.initBody();
		this.initWebuiPopover();
//		this.initAutoComplete();
		
	},
	initFormValidator : function(){//初始化验证
		var this_ = this;
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#work_card_form").data('bootstrapValidator').destroy();
		     $('#work_card_form').data('bootstrapValidator', null);
		     this_.formValidator();
		});
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**
	 * 初始化操作按钮显示与隐藏
	 */
	initBtnShowOrHide : function(){
		$(".operation-btn ", $("#"+this.id)).hide();
		if(this.param.editParam == this.editParam.add 
				|| this.param.editParam == this.editParam.edit
				|| this.param.editParam == this.editParam.modify){
			$("#save_btn", $("#"+this.id)).show();
			$("#submit_btn", $("#"+this.id)).show();
		}
		if(this.param.editParam == this.editParam.audit){
			$("#audited_btn", $("#"+this.id)).show();
			$("#audit_reject_btn", $("#"+this.id)).show();
		}
		if(this.param.editParam == this.editParam.approve){
			$("#approved_btn", $("#"+this.id)).show();
			$("#approved_reject_btn", $("#"+this.id)).show();
		}
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(){
		this.returnViewData(this.param.viewObj);
		if(this.param.editParam == this.editParam.modify){
			$("#bbViewHistoryDiv", $("#"+this.id)).show();
			$("#bbNoViewHistoryDiv", $("#"+this.id)).hide();
			$("#bbViewHistoryDiv_view", $("#"+this.id)).hide();
			$("#bbViewHistoryDiv_edit", $("#"+this.id)).hide();
			$("#new_bb_a", $("#"+this.id)).html(this.param.viewObj.bb);
			$("#new_bb", $("#"+this.id)).val('');
		}else{
			if(this.param.viewObj.fBbid != null && this.param.viewObj.fBbid != ''){
				this.oldId = this.param.viewObj.paramsMap.oldId;
				if(this.param.editParam == this.editParam.edit){
					$("#edit_bb", $("#"+this.id)).val(this.param.viewObj.bb);
					$("#edit_bb_a", $("#"+this.id)).html(this.param.viewObj.paramsMap.oldBb);
					$("#bbViewHistoryDiv_edit", $("#"+this.id)).show();
					$("#bbViewHistoryDiv_view", $("#"+this.id)).hide();
					$("#bbNoViewHistoryDiv", $("#"+this.id)).hide();
					$("#bbViewHistoryDiv", $("#"+this.id)).hide();
				}else{
					$("#bb_view", $("#"+this.id)).html(this.param.viewObj.bb);
					$("#old_bb_a", $("#"+this.id)).html(this.param.viewObj.paramsMap.oldBb);
					$("#bbViewHistoryDiv_view", $("#"+this.id)).show();
					$("#bbViewHistoryDiv_edit", $("#"+this.id)).hide();
					$("#bbNoViewHistoryDiv", $("#"+this.id)).hide();
					$("#bbViewHistoryDiv", $("#"+this.id)).hide();
				}
			}else{
				$("#bbNoViewHistoryDiv", $("#"+this.id)).show();
				$("#bbViewHistoryDiv_edit", $("#"+this.id)).hide();
				$("#bbViewHistoryDiv", $("#"+this.id)).hide();
				$("#bbViewHistoryDiv_view", $("#"+this.id)).hide();
			}
		}
		if(this.param.editParam == this.editParam.audit || this.param.editParam == this.editParam.approve){
			this.setReadOnlyFailure();
			this.initInclude(this.param.viewObj,false);
			$("#wxxm_search_btn", $("#"+this.id)).hide();
			$(".identifying", $("#"+this.id)).hide();
			$("#wxxm", $("#"+this.id)).attr("disabled","true");
			this.setFixChapterReadOnly();
		}else{
			$("#wxxm_search_btn", $("#"+this.id)).show();
			$(".identifying", $("#"+this.id)).show();
			$("#wxxm", $("#"+this.id)).removeAttr("disabled");
			this.removeFixChapterReadOnly();
			this.removeReadOnlyFailure();
			this.initInclude(this.param.viewObj,true);
			if(this.param.editParam != this.editParam.add){
				$("#gkh", $("#"+this.id)).attr("readonly","readonly");
				$("#jx", $("#"+this.id)).attr("disabled","true");
			}
		}
		this.initBtnShowOrHide();
	},
	/**
	 * 设置只读/失效
	 */
	setReadOnlyFailure : function(){
		//设置input只读
		$("#gkh", $("#"+this.id)).attr("readonly","readonly");
		$("#bb", $("#"+this.id)).attr("readonly","readonly");
		$("#rwdh", $("#"+this.id)).attr("readonly","readonly");
		$("#gzdh", $("#"+this.id)).attr("readonly","readonly");
		$("#kzh", $("#"+this.id)).attr("readonly","readonly");
		$("#cmph", $("#"+this.id)).attr("readonly","readonly");
		$("#gzbt", $("#"+this.id)).attr("readonly","readonly");
		$("#jhgsRs", $("#"+this.id)).attr("readonly","readonly");
		$("#jhgsXss", $("#"+this.id)).attr("readonly","readonly");
		$("#yjwj", $("#"+this.id)).attr("readonly","readonly");
		$("#tbsysm", $("#"+this.id)).attr("readonly","readonly");
		$("#gzgs", $("#"+this.id)).attr("readonly","readonly");
		$("#bz", $("#"+this.id)).attr("readonly","readonly");
		//设置select失效
		$("#jx", $("#"+this.id)).attr("disabled","true");
		$("#gzlx_e", $("#"+this.id)).attr("disabled","true");
		$("#gklx_e", $("#"+this.id)).attr("disabled","true");
		$("#zy_e", $("#"+this.id)).attr("disabled","true");
		$("#gzz_e", $("#"+this.id)).attr("disabled","true");
		//设置checkbox失效
		$("#isBj", $("#"+this.id)).attr("disabled","true");
		$("#worktype", $("#"+this.id)).attr("disabled","true");
		$("#workCardBox", $("#"+this.id)).attr("disabled","true");
		$("#workContentBox", $("#"+this.id)).attr("disabled","true");
	},
	/**
	 * 移除只读/失效
	 */
	removeReadOnlyFailure : function(){
		//移除input只读
		$("#gkh", $("#"+this.id)).removeAttr("readonly");
		$("#bb", $("#"+this.id)).removeAttr("readonly");
		$("#rwdh", $("#"+this.id)).removeAttr("readonly");
		$("#gzdh", $("#"+this.id)).removeAttr("readonly");
		$("#kzh", $("#"+this.id)).removeAttr("readonly");
		$("#cmph", $("#"+this.id)).removeAttr("readonly");
		$("#gzbt", $("#"+this.id)).removeAttr("readonly");
		$("#jhgsRs", $("#"+this.id)).removeAttr("readonly");
		$("#jhgsXss", $("#"+this.id)).removeAttr("readonly");
		$("#yjwj", $("#"+this.id)).removeAttr("readonly");
		$("#tbsysm", $("#"+this.id)).removeAttr("readonly");
		$("#gzgs", $("#"+this.id)).removeAttr("readonly");
		$("#bz", $("#"+this.id)).removeAttr("readonly");
		//移除select失效
		$("#jx", $("#"+this.id)).removeAttr("disabled");
		$("#gzlx_e", $("#"+this.id)).removeAttr("disabled");
		$("#gklx_e", $("#"+this.id)).removeAttr("disabled");
		$("#zy_e", $("#"+this.id)).removeAttr("disabled");
		$("#gzz_e", $("#"+this.id)).removeAttr("disabled");
		//移除checkbox失效
		$("#isBj", $("#"+this.id)).removeAttr("disabled");
		$("#worktype", $("#"+this.id)).removeAttr("disabled");
		$("#workCardBox", $("#"+this.id)).removeAttr("disabled");
		$("#workContentBox", $("#"+this.id)).removeAttr("disabled");
	},
	/**
	 * 清空章节号数据
	 */
	clearFixChapter : function(){
		this.setFixChapterData({});
		this.removeFixChapterReadOnly();
	},
	/**
	 * 设置章节号只读
	 */
	setFixChapterReadOnly : function(){
		$("#zjh_e_btn", $("#"+this.id)).hide();
		$("#zjh_name_e", $("#"+this.id)).removeClass("readonly-style");
	},
	/**
	 * 移除章节号只读
	 */
	removeFixChapterReadOnly : function(){
		$("#zjh_e_btn", $("#"+this.id)).show();
		$("#zjh_name_e", $("#"+this.id)).addClass("readonly-style");
	},
	/**
	 * 设置章节号数据
	 */
	setFixChapterData : function(obj){
		$("#zjh_e", $("#"+this.id)).val(obj.zjh);
		$("#zjh_name_e", $("#"+this.id)).val(obj.name);
	},
	/**
	 * 设置维修项目数据
	 */
	setMainProjectData : function(obj){
		$("#wxxmid", $("#"+this.id)).val(obj.wxxmid);
		$("#wxxmbh", $("#"+this.id)).val(obj.wxxmbh);
		$("#wxxm", $("#"+this.id)).val(obj.wxxm);
		$("#rwms", $("#"+this.id)).val(obj.rwms);
	},
	/**
	 * 初始化引入数据
	 */
	initInclude : function(obj,colOptionhead){
		var this_ = this;
		//维修项目
		var wxxmObj = {};
		wxxmObj.wxxmbh = obj.wxxmbh;
		wxxmObj.wxxm = obj.wxxmbh;
		if(obj.maintenanceProject != null){
			wxxmObj.wxxmbh = obj.maintenanceProject.rwh;
			wxxmObj.wxxm = formatUndefine(obj.maintenanceProject.rwh) + " R" +formatUndefine(obj.maintenanceProject.bb);
			wxxmObj.rwms = obj.maintenanceProject.rwms;
			wxxmObj.wxxmid = obj.maintenanceProject.id;
			this_.setFixChapterReadOnly();
		}
		
		this_.setMainProjectData(wxxmObj);
		this_.loadMaintenanceRel(wxxmObj.wxxmid);
		
		if(this_.param.editParam == this_.editParam.modify){
			obj.zt = 1;
		}
		//流程信息
		introduce_process_info_class.show({  
			status:obj.zt,//1,编写,2审核,3批准，4审核驳回,5批准驳回
			prepared:StringUtil.escapeStr(obj.zdr?obj.zdr.displayName:''),//编写人
			preparedDate:StringUtil.escapeStr(obj.whsj),//编写日期
			checkedOpinion:StringUtil.escapeStr(obj.shyj),//审核意见
			checkedby:StringUtil.escapeStr(obj.shr?obj.shr.displayName:''),//审核人
			checkedDate:StringUtil.escapeStr(obj.shsj),//审核日期
			checkedResult:obj.shjl,//审核结论
			approvedOpinion:StringUtil.escapeStr(obj.pfyj),//批准意见
			approvedBy:StringUtil.escapeStr(obj.pfr?obj.pfr.displayName:''),//批准人
			approvedDate:StringUtil.escapeStr(obj.pfsj),//批准日期
			approvedResult : obj.pfjl,// 批准结论
		});
		$("#gkfjid", $("#"+this_.id)).val(obj.gkfjid);
		$("#"+this_.id).on("shown.bs.modal", function() {
			//加载工卡附件
			attachmentsSingleUtil.initAttachment(
					"work_card_attachments_single_edit"//主div的id
					,obj.workCardAttachment//附件
					,'card'//文件夹存放路径
					,colOptionhead//true可编辑,false不可编辑
					,"请上传工卡附件"//未上传附件时提示信息
					);
			
			$("#gznrfjid", $("#"+this_.id)).val(obj.gznrfjid);
			//加载工作内容附件
			attachmentsSingleUtil.initAttachment(
					"work_content_attachments_single_edit"//主div的id
					,obj.workContentAttachment//附件
					,'card'//文件夹存放路径
					,colOptionhead//true可编辑,false不可编辑
					,"请上传工作内容附件"//未上传附件时提示信息
					);
		});
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : colOptionhead,
			colOptionhead : colOptionhead,
			fileType:"card"
		});//显示附件
		
		evaluation_modal.init({//初始化技术文件
			parentId : this_.id,// 第一层模态框id
			isShowed : colOptionhead,// 是否显示选择评估单的操作列
			zlxl : 8,//工卡
			dprtcode : this_.param.dprtcode,// 组织机构
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
			zlid : obj.id
		});
		//初始化参考文件
		ReferenceUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化相关工卡
		WorkCardUtil.init({
			djid:obj.id,//关联单据id
			gkh:obj.gkh,//关联单据编号
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
			parentWinId : this_.id,//父窗口id
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化器材清单
		Equipment_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工具设备
		Tools_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工作内容
		WorkContentUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工种工时
		work_hour_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
	},
	/**
	 * 初始化输入框自动完成控件
	 */
	initAutoComplete : function(){
		var this_ = this;
		 $("#wxxm", $("#"+this.id)).typeahead({
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
							dprtcode : this_.param.dprtcode
						}),
						success:function(data){
							process(data);
					    }
					}); 
			    },

	            highlighter: function (item) {
	                return "&nbsp;&nbsp;" + item + "&nbsp;&nbsp;";
	            },

	            updater: function (item) {
	                return item;
	            }
			});
	},
	/**
	 * 初始化工作组
	 */
	initWorkGroup : function(){
		var this_ = this;
		var obj={};
		obj.dprtcode = this_.param.dprtcode;
		obj.zt = 1;
		AjaxUtil.ajax({
			   url:basePath+"/sys/workgroup/loadWorkGroup",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   async: false,
			   data:JSON.stringify(obj),
			   success:function(data){
				   $("#gzz_e", $("#"+this_.id)).empty();
				   var option = "<option value='-' >"+"N/A"+"</option>";
				   if(data != null && data.wgList != null && data.wgList.length > 0){
					   $.each(data.wgList,function(i,list){
						   var str='';
						   if(list.mrbs==1){
							   str='selected="selected"';
						   }
						   option += "<option value='"+list.id+"' "+str+">"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
				 	   }); 
				   }
				   $("#gzz_e", $("#"+this_.id)).html(option);
		       },
		 }); 
	},
	initPlaneModel : function(){//初始化机型
		var this_ = this;
		var data = acAndTypeUtil.getACTypeList({DPRTCODE:this_.param.dprtcode});
	 	var option = '';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	  	 }
	 	$("#jx", $("#"+this_.id)).empty();
	 	$("#jx", $("#"+this_.id)).append(option);
	},
	/**
	 * 初始化区域下拉框
	 */
	initZoneInformation : function(obj){
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = this_.param.dprtcode;
		searchParam.jx = $("#jx", $("#"+this_.id)).val();
		searchParam.lx = 1;
		startWait();
		AjaxUtil.ajax({
			async: false,
			url: basePath+"/basic/zone/zoneList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var zoneOption = '';
				if(data != null && data.length > 0){
					$.each(data,function(i,row){
						var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.sz)+"</option>";
						zoneOption += tempOption;
					});
				}
				this_.zoneOption = zoneOption;
				if(typeof(obj)=="function"){
					obj(); 
				}
		    }
		}); 
	},
	/**
	 * 改变机型时触发
	 */
	changeModel : function(){
		var this_ = this;
		evaluation_modal.changeParam({
			parentId : this_.id,// 第一层模态框id
			isShowed : true,// 是否显示选择评估单的操作列
			zlxl : 8,// 指令类型
			dprtcode : this_.param.dprtcode,// 组织机构
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
			zlid : ''
		});
		
		//初始化相关工卡
		WorkCardUtil.changeParam({
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
		});
		
		/*this_.initZoneInformation(function(){
			this_.multiselect();
		});*/
		$("#eqy", $("#"+this_.id)).val("");
		
		if($("#wxxmid", $("#"+this_.id)).val() != null && $("#wxxmid", $("#"+this_.id)).val() != ''){
			this_.clearFixChapter();
			this_.setMainProjectData({});
		}
		
		$("#jj_e", $("#"+this_.id)).html("");
//		$("#efjzw", $("#"+this_.id)).html("");
		$("#efjzw", $("#"+this_.id)).val("");
		
		this_.accessIdList = [];
		this_.accessList = [];
		this_.positionIdList = [];
		this_.positionList = [];
		
		$("#maintenance_version_bj").show();
		$("#syx_div_view", $("#"+this_.id)).html('');
		$("#maintenance_version_list", $("#"+this_.id)).empty();
		$("#maintenance_version_list", $("#"+this_.id)).html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(){
		$("#gzlx_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','gzlx_e',this.param.dprtcode);
		$("#gklx_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('22','gklx_e',this.param.dprtcode);
		$("#zy_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('4','zy_e',this.param.dprtcode);
	},
	/**
	 * 初始化适用单位多选下拉框
	 */
	multiselectApplicableUnit : function(){
		
		//生成多选下拉框区域
		$('#esydwdiv',$("#"+this.id)).empty();
		$('#esydwdiv',$("#"+this.id)).html("<select class='form-control' multiple='multiple' id='esydw'></select>");
		DicAndEnumUtil.registerDic('24','esydw',this.param.dprtcode);
		
		//生成多选下拉框
		$('#esydw').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',//auto
		    buttonContainer: '<div class="btn-group" style="min-width:100%;" />',
		    numberDisplayed:16,
		    nonSelectedText:'请选择 Choose',
		    allSelectedText:'显示全部 All',
		    includeSelectAllOption: true,
		    selectAllText: '选择全部 Select All',
		    onChange:function(element,checked){
	  	    }
		});
		
	},
	multiselect : function(){
		
		//生成多选下拉框区域
		$('#eqydiv',$("#"+this.id)).empty();
		$('#eqydiv',$("#"+this.id)).html("<select class='form-control' multiple='multiple' id='eqy'></select>");
		$("#eqy").html(this.zoneOption);
		
		//生成多选下拉框
		$('#eqy').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',//auto
		    buttonContainer: '<div class="btn-group" style="min-width:100%;" />',
		    numberDisplayed:100,
		    nonSelectedText:'请选择 Choose',
		    allSelectedText:'显示全部 All',
		    includeSelectAllOption: true,
		    selectAllText: '选择全部 Select All',
		    onChange:function(element,checked){
	  	    }
		});
		
	},
	returnViewData : function(obj){
		var this_ = this;
		this_.param.id = obj.id;
		$("#gkh", $("#"+this_.id)).val(obj.gkh);
		$("#bb", $("#"+this_.id)).val(obj.bb);
		$("#rwdh", $("#"+this_.id)).val(obj.rwdh);
		$("#gzdh", $("#"+this_.id)).val(obj.gzdh);
		$("#kzh", $("#"+this_.id)).val(obj.kzh);
		$("#cmph", $("#"+this_.id)).val(obj.cmph);
		$("#gzbt", $("#"+this_.id)).val(obj.gzbt);
		$("#yjwj", $("#"+this_.id)).val(obj.yjwj);
		$("#tbsysm", $("#"+this_.id)).val(obj.tbsysm);
		$("#gzgs", $("#"+this_.id)).val(obj.gzgs);
		$("#bz", $("#"+this_.id)).val(obj.bz);
		$("#jhgsRs", $("#"+this_.id)).val(obj.jhgsRs);
		$("#jhgsXss", $("#"+this_.id)).val(obj.jhgsXss);
		
		if(obj.jx != null){
			$("#jx", $("#"+this_.id)).val(obj.jx);
		}
		
		if(obj.gzlx != null){
			$("#gzlx_e", $("#"+this_.id)).val(obj.gzlx);
		}
		
		if(obj.gklx != null){
			$("#gklx_e", $("#"+this_.id)).val(obj.gklx);
		}
		
		if(obj.zy != null){
			$("#zy_e", $("#"+this_.id)).val(obj.zy);
		}
		
		if(obj.gzzid != null){
			$("#gzz_e", $("#"+this_.id)).val(obj.gzzid);
		}
		
		if(obj.isBj == 1){
			$("#isBj", $("#"+this_.id)).attr("checked","true");
		}else{
			$("#isBj", $("#"+this_.id)).removeAttr("checked");
		}
		if(obj.worktype == 1){
			$("#worktype", $("#"+this_.id)).attr("checked","true");
		}else{
			$("#worktype", $("#"+this_.id)).removeAttr("checked");
		}
		//章节号回显
		var zjhObj = {};
		if(obj.fixChapter != null){
			zjhObj.zjh = obj.fixChapter.zjh;
			zjhObj.name = StringUtil.escapeStr(obj.fixChapter.zjh) + " " + StringUtil.escapeStr(obj.fixChapter.ywms);
		}
		this_.setFixChapterData(zjhObj);
		
		this_.loadCoverPlate(obj.coverPlateList);
		
		this_.loadApplicableUnit(obj.applicableUnitList);
		
//		$("#wxxm_search_btn").hide();
		//设置总工时
		this_.sumTotal();
	},
	/**
	 * 适用单位回显
	 */
	loadApplicableUnit : function(list){
		var this_ = this;
		var sydwArr = [];
		var sydwStr = '';
		if(list != null && list.length > 0){
			$.each(list,function(index,row){
				sydwArr.push(row.sydwid);
				sydwStr += StringUtil.escapeStr(row.sydw) + ",";
			});
		}
		if(sydwStr != ''){
			sydwStr = sydwStr.substring(0,sydwStr.length - 1);
		}
		if(this_.param.editParam == this_.editParam.audit || this_.param.editParam == this_.editParam.approve){
			$("#esydwdiv_edit", $("#"+this_.id)).hide();
			$("#esydwdiv_view", $("#"+this_.id)).show();
			$("#esydw_view", $("#"+this_.id)).html(sydwStr);
		}else{
			$("#esydwdiv_edit", $("#"+this_.id)).show();
			$("#esydwdiv_view", $("#"+this_.id)).hide();
			this_.multiselectApplicableUnit();
			if(sydwArr.length > 0){
				$('#esydw',$("#"+this_.id)).multiselect('select', sydwArr);
			}
		}
	},
	/**
	 * 区域/接近/飞机站位回显
	 */
	loadCoverPlate : function(coverPlateList){
		var this_ = this;
		var str = '';
		var stationStr = '';
		var qyArr = [];
		var qyStr = '';
		this_.accessIdList = [];
		this_.accessList = [];
		this_.positionIdList = [];
		this_.positionList = [];
		if(coverPlateList != null && coverPlateList.length > 0){
			$.each(coverPlateList,function(index,row){
				if(row.lx == 1){
					qyArr.push(row.gbid);
					qyStr += StringUtil.escapeStr(row.gbh) + ",";
				}
				if(row.lx == 2){
					this_.accessIdList.push(row.gbid);
					var access = {};
					access.id = row.gbid;
					access.gbbh = row.gbh;
					this_.accessList.push(access);
					str += StringUtil.escapeStr(row.coverPlateInformation?row.coverPlateInformation.gbbh:'') + " " +StringUtil.escapeStr(row.coverPlateInformation?row.coverPlateInformation.gbmc:'') + ",";
				}
				if(row.lx == 3){
					this_.positionList.push({
						id : row.gbid,
						sz : row.gbh
					});
					this_.positionIdList.push(row.gbid);
					stationStr += formatUndefine(row.gbh) + ",";
				}
			});
		}
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		if(qyStr != ''){
			qyStr = qyStr.substring(0,qyStr.length - 1);
		}
		if(stationStr != ''){
			stationStr = stationStr.substring(0,stationStr.length - 1);
		}
		if(this_.param.editParam == this_.editParam.audit || this_.param.editParam == this_.editParam.approve){
			$("#jj_div_edit", $("#"+this_.id)).hide();
			$("#jj_div_view", $("#"+this_.id)).show();
			$("#fjzw_div_edit", $("#"+this_.id)).hide();
			$("#fjzw_div_view", $("#"+this_.id)).show();
			$("#eqydiv_edit", $("#"+this_.id)).hide();
			$("#eqydiv_view", $("#"+this_.id)).show();
			$("#jj_div_view", $("#"+this_.id)).html(str);
//			$("#eqy_view", $("#"+this_.id)).html(qyStr);
			$("#eqy_view", $("#"+this_.id)).val(qyStr);
//			$("#fjzw_div_view", $("#"+this_.id)).html(stationStr);
			$("#fjzw_div_view", $("#"+this_.id)).val(stationStr);
		}else{
			$("#jj_div_edit", $("#"+this_.id)).show();
			$("#jj_div_view", $("#"+this_.id)).hide();
			$("#fjzw_div_edit", $("#"+this_.id)).show();
			$("#fjzw_div_view", $("#"+this_.id)).hide();
			$("#eqydiv_edit", $("#"+this_.id)).show();
			$("#eqydiv_view", $("#"+this_.id)).hide();
			/*this_.initZoneInformation(function(){
				this_.multiselect();
				if(qyArr.length > 0){
					$('#eqy',$("#"+this_.id)).multiselect('select', qyArr);
				}
			});*/
			$("#eqy", $("#"+this_.id)).val(qyStr);
			$("#jj_e", $("#"+this_.id)).html(str);
//			$("#efjzw", $("#"+this_.id)).html(stationStr);
			$("#efjzw", $("#"+this_.id)).val(stationStr);
		}
	},
	/**
	 * 加载维修项目相关信息
	 */
	loadMtRel : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var obj = {};
		obj.jx = $("#jx", $("#"+this_.id)).val();
		obj.gkbh = $("#gkh", $("#"+this_.id)).val();
		if(this_.editParam.add == this_.param.editParam){
			obj.gkbh = "";
		}
		var rwh = $("#wxxm", $("#"+this_.id)).val();
		obj.dprtcode = dprtcode;
		obj.rwh = rwh;
		if(null != rwh && '' != rwh){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/maintenanceproject/queryMonitorItemModelByWxxm",
				type:"post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					var wxxmObj = {};
					$("#maintenance_version_list", $("#"+this_.id)).empty();
					if(data != null){
						var zjhObj = {};
						this_.loadMonitorItemModel(data);
						this_.loadMonitor(data);
						wxxmObj.wxxmid = data.id;
						wxxmObj.wxxmbh = data.rwh;
						wxxmObj.wxxm = formatUndefine(data.rwh) + " R" +formatUndefine(data.bb);
						wxxmObj.rwms = data.rwms;
						if(null != data.fixChapter){
							zjhObj.zjh = data.fixChapter.zjh;
							zjhObj.name = formatUndefine(data.fixChapter.zjh) + " " +formatUndefine(data.fixChapter.ywms);
						}
						this_.setFixChapterData(zjhObj);
						this_.setFixChapterReadOnly();
						this_.loadMaintenanceRel(data.id);
					}else{
						wxxmObj.wxxmbh = rwh;
						wxxmObj.wxxm = rwh;
						this_.removeFixChapterReadOnly();
						this_.loadMaintenanceRel('');
					}
					
					this_.setMainProjectData(wxxmObj);
				}
			});
		}else{
			this_.setMainProjectData({});
			this_.clearFixChapter();
			this_.loadMaintenanceRel('');
		}
		this_.resizeVersionWidth();
	},
	/**
	 * 加载维修项目相关信息
	 */
	loadMaintenanceRel : function(id){
		var this_ = this;
		if(null != id && '' != id){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/maintenanceproject/queryMonitorItemModel",
				type:"post",
				data:{id:id},
				dataType:"json",
				success:function(data){
					$("#maintenance_version_list", $("#"+this_.id)).empty();
					if(data != null){
						this_.loadMonitorItemModel(data);
						this_.loadMonitor(data);
					}
				}
			});
		}else{
			$("#maintenance_version_bj").show();
			$("#syx_div_view", $("#"+this_.id)).html('');
			$("#maintenance_version_list", $("#"+this_.id)).empty();
			$("#maintenance_version_list", $("#"+this_.id)).html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
		}
		this_.resizeVersionWidth();
	},
	/**
	 * 加载维修项目相关信息
	 */
	loadMonitor:function(data){
		var this_ = this;
		var htmlContent = '';
		var wxxmlx = data.wxxmlx;
		if(wxxmlx == 2 || wxxmlx == 3){
			$("#maintenance_version_bj").show();
			if(data.projectMaterialList.length > 0){
				$.each(data.projectMaterialList,function(j,mon){
					htmlContent +="<tr>"
					htmlContent +="<td style='text-align:center;vertical-align:middle;' name='bj'>"+mon.bjh+"</td>"
					htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(mon.projectMonitors, "jklbh"))+"</td>"
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
			}
		}else{
			$("#maintenance_version_bj").hide();
			if(data.projectMonitors.length > 0){
				htmlContent +="<tr>"
				htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(data.projectMonitors, "jklbh"))+"</td>"
				htmlContent +="</tr>"
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
			}
		}
		$("#maintenance_version_list", $("#"+this_.id)).html(htmlContent);
	},
	buildMonitorItem:function(list){
		var this_ = this;
		var htmlContent = '';
		if(list!=null){
			$.each(list,function(i,row){
				 	if (i == 1) {
				 		htmlContent = htmlContent + "<div class='monitor_rc_td'>";
					}
				 	
				    var scz = (row.scz||"") + this_.convertUnit(row.jklbh, row.scz, row.dwScz);
				    var zqz = (row.zqz||"") + this_.convertUnit(row.jklbh, row.zqz, row.dwZqz);
				 	/*var rz = "-" + row.ydzQ + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.ydzQdw) + "/+" 
				 				+ row.ydzH + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.ydzHdw);*/
				 	var rz = "-" + row.ydzQ + "/+" + row.ydzH + this_.convertUnit(row.jklbh, row.ydzQ, row.ydzHdw);
				    htmlContent += "<div>";
				    htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='jkxm'>"+MonitorUnitUtil.getMonitorName(row.jklbh)+"</div>"; 
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='sj'>"+StringUtil.escapeStr(scz)+"</div>";
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='zq'>"+StringUtil.escapeStr(zqz)+"</div>";
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='rc'>"+StringUtil.escapeStr(rz)+"</div>";
					if (i != 0) {
						htmlContent += "<br>";

					}
					if (i != 0 && i == list.length - 1) {
						htmlContent += "</div>";
					}
			});
		}
		return htmlContent;
   },
	/**
	 * 加载监控项目适用性
	 */
	loadMonitorItemModel : function(project){
		var this_ = this;
		var syx = '';
		if(project.syx == "-" && null != project.projectModelList && project.projectModelList.length > 0){
			$.each(project.projectModelList,function(j,m){
				syx += m.fjzch + ",";
			});
			if(syx != ''){
				syx = syx.substring(0, syx.length - 1);
			}
		}
		if(project.syx == "00000"){
			syx = "ALL";
		}
		if(!project.syx || project.syx == "null"){
			syx = "N/A";
		}
		$("#syx_div_view", $("#"+this_.id)).html(syx);
	},
	/**
	 * 监控类型单位转换
	 * @param jklbh
	 * @param value
	 * @param unit
	 */
	convertUnit : function(jklbh, value, unit){
		if(value != null && value != "" && value != undefined){
			return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
		}else{
			return "";
		}
	},
	openChapterWin : function(){//打开章节号对话框
		var this_ = this;
		var zjh = $.trim($("#zjh_e").val());
		var dprtcode = this.param.dprtcode;
		
		var wxxmid = $("#wxxmid", $("#"+this_.id)).val();
		if(wxxmid != null && wxxmid != ''){
			return false;
		}
		if(this_.isView()){
			return false;
		}
		
		FixChapterModal.show({
			parentWinId : this_.id,
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode, //机构代码
			callback: function(data){//回调函数
				var zjhObj = {};
				if(data != null){
					zjhObj.zjh = data.zjh;
					zjhObj.name = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
				}
				this_.setFixChapterData(zjhObj);
			}
		});
	},
	/**
	 * 打开维修项目对话框
	 */
	openProjectWinAdd : function(){
		var this_ = this;
		if(this_.isView()){
			return false;
		}
		var dprtcode = this_.param.dprtcode;
		var obj = {};
		obj.jx = $("#jx", $("#"+this_.id)).val();
		obj.gkbh = $("#gkh", $("#"+this_.id)).val();
		if(this_.editParam.add == this_.param.editParam){
			obj.gkbh = "";
		}
		obj.dprtcode = dprtcode;
		open_win_maintenance_project.show({
			multi : false,
			clearProject: true,
			selected : $("#wxxmid", $("#"+this_.id)).val(),
			obj:obj,
			url:basePath+"/project2/maintenanceproject/queryWinAllPageList4WorkCard",
			dprtcode:dprtcode,
			parentWinId:this_.id,
			callback: function(data){//回调函数
				var wxxmObj = {};
				var zjhObj = {};
				if(data != null && data.id != null){
					wxxmObj.wxxmid = data.id;
					wxxmObj.wxxmbh = data.rwh;
					wxxmObj.wxxm = formatUndefine(data.rwh) + " R" +formatUndefine(data.bb);
					wxxmObj.rwms = data.rwms;
					if(null != data.fixChapter){
						zjhObj.zjh = data.fixChapter.zjh;
						zjhObj.name = formatUndefine(data.fixChapter.zjh) + " " +formatUndefine(data.fixChapter.ywms);
					}
					this_.setFixChapterData(zjhObj);
					this_.setFixChapterReadOnly();
					this_.loadMaintenanceRel(data.id);
				}else{
					if($("#wxxmid").val() != null && $("#wxxmid").val() != ''){
						this_.clearFixChapter();
					}
					this_.loadMaintenanceRel('');
				}
				this_.setMainProjectData(wxxmObj);
			}
		},true);
	},
	/**
	 * 打开区域弹窗
	 */
	openZoneWin : function(){
		var this_ = this;
		var jx = $("#jx", $("#"+this_.id)).val();
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		if(this_.isView()){
			return false;
		}
		PositionModal.show({
			selected:[],
			dprtcode:this_.param.dprtcode,
			modalHeadCN : '区域列表',
			modalHeadENG : 'Zone List',
			jx:jx,
			lx:1,
			callback: function(data){//回调函数
				var str = '';
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#eqy", $("#"+this_.id)).val(str);
			}
		});
	},
	/**
	 * 打开飞机站位弹窗
	 */
	openStationWin : function(){
		var this_ = this;
		var jx = $("#jx", $("#"+this_.id)).val();
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		if(this_.isView()){
			return false;
		}
		PositionModal.show({
//			selected:this_.positionList,//原值，在弹窗中默认勾选
			selected:[],
			dprtcode:this_.param.dprtcode,
			modalHeadCN : '飞机站位列表',
			modalHeadENG : 'A/C Type Position List',
			parentid:this_.id,
			jx:jx,
			lx:3,
			callback: function(data){//回调函数
				var str = '';
				this_.positionIdList = [];
				this_.positionList = [];
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						this_.positionIdList.push(row.id);
						this_.positionList.push(row);
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
//				$("#efjzw", $("#"+this_.id)).text(str);
				$("#efjzw", $("#"+this_.id)).val(str);
			}
		});
	},
	/**
	 * 打开接近对话框
	 */
	openAccessWin : function(){
		var this_ = this;
		var jx = $("#jx", $("#"+this_.id)).val();
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		if(this_.isView()){
			return false;
		}
		open_win_access.show({
			multi : true,
			parentWinId : this_.id,
			jx:jx,
			accessIdList : this_.accessIdList,//回显
			dprtcode:this_.param.dprtcode,
			callback: function(data){//回调函数
				var str = '';
				this_.accessIdList = [];
				this_.accessList = [];
				if(data != null && data.length > 0){
					this_.accessList = data;
					$.each(data,function(index,row){
						this_.accessIdList.push(row.id);
						str += StringUtil.escapeStr(row.gbbh) + " " +StringUtil.escapeStr(row.gbmc) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#jj_e", $("#"+this_.id)).html(str);
			}
		},true)
	},
	view : function(){
		window.open(basePath+"/project2/workcard/view/" + this.param.viewObj.id);
	},
	viewOld : function(){
		window.open(basePath+"/project2/workcard/view/" + this.oldId);
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		var gkh = $.trim($("#gkh",$("#"+this_.id)).val());
		var bb = $.trim($("#bb",$("#"+this_.id)).val());
		var jx = $.trim($("#jx",$("#"+this_.id)).val());
		var rwdh = $.trim($("#rwdh",$("#"+this_.id)).val());
		var gzdh = $.trim($("#gzdh",$("#"+this_.id)).val());
		var kzh = $.trim($("#kzh",$("#"+this_.id)).val());
		var cmph = $.trim($("#cmph",$("#"+this_.id)).val());
		var jhgsRs = $.trim($("#jhgsRs",$("#"+this_.id)).val());
		var jhgsXss = $.trim($("#jhgsXss",$("#"+this_.id)).val());
		var eqy = $.trim($("#eqy",$("#"+this_.id)).val());
		var efjzw = $.trim($("#efjzw",$("#"+this_.id)).val());
		
		if(this.param.editParam == this.editParam.modify){
			bb = $.trim($("#new_bb",$("#"+this_.id)).val());
		}
		
		if(!this_.codeValidator.reg.test(gkh)){  
			$("#gkh",$("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("工卡号"+this_.codeValidator.message);
			return false;
		}
		if(bb == null || bb == ''){
			$("#bb",$("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("请输入版本号!");
			return false;
		}
		
		if(!this_.numberValidator.reg.test(bb)){  
			$("#bb",$("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("版本"+this_.numberValidator.message);
			return false;
		}
		
		if(jx == null || jx == ''){
			$("#jx",$("#"+this.id)).focus();
			AlertUtil.showModalFooterMessage("请选择机型!");
			return false;
		}
		
		if(rwdh != null && rwdh != ''){
			if(!this_.codeValidator.reg.test(rwdh)){  
				$("#rwdh",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("任务单号"+this_.codeValidator.message);
				return false;
			}
		}
		
		if(gzdh != null && gzdh != ''){
			if(!this_.codeValidator.reg.test(gzdh)){  
				$("#gzdh",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("工作单号"+this_.codeValidator.message);
				return false;
			}
		}
		
		if(kzh != null && kzh != ''){
			if(!this_.codeValidator.reg.test(kzh)){  
				$("#kzh",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("控制号"+this_.codeValidator.message);
				return false;
			}
		}
		
		if(cmph != null && cmph != ''){
			if(!this_.codeValidator.reg.test(cmph)){  
				$("#cmph",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("CMP号"+this_.codeValidator.message);
				return false;
			}
		}
		
		if(jhgsRs != null && jhgsRs != ''){
			if(!this_.number2Validator.reg.test(jhgsRs)){  
				$("#jhgsRs",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("人数"+this_.number2Validator.message);
				return false;
			}
		}
		
		if(jhgsXss != null && jhgsXss != ''){
			if(!this_.numberValidator.reg.test(jhgsXss)){  
				$("#jhgsXss",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("小时数"+this_.numberValidator.message);
				return false;
			}
		}
		
		if(eqy != null && eqy != ''){
			if(!this_.codeValidatorQyZw.reg.test(eqy)){  
				$("#eqy",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("区域"+this_.codeValidatorQyZw.message);
				return false;
			}
		}
		
		if(efjzw != null && efjzw != ''){
			if(!this_.codeValidatorQyZw.reg.test(efjzw)){  
				$("#efjzw",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("飞机站位"+this_.codeValidatorQyZw.message);
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
		
		//验证工卡附件
		if(!attachmentsSingleUtil.isVaildMap['work_card_attachments_single_edit']){
			return false;
		}
		
		//验证参考文件
		if(!ReferenceUtil.isValid){
			return false;
		}
		
		//验证器材清单
		if(!Equipment_list_edit.isValid){
			return false;
		}
		
		//验证工具设备
		if(!Tools_list_edit.isValid){
			return false;
		}
		
		//验证工作内容附件
		if(!attachmentsSingleUtil.isVaildMap['work_content_attachments_single_edit']){
			return false;
		}
		
		//验证工作内容
		if(!WorkContentUtil.isValid){
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
		$('#work_card_form').data('bootstrapValidator').validate();
		if(!$('#work_card_form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		
		var data = this.getWorkCardData(operationType);
		
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
	/**
	 * 审核数据
	 * audit audit:审核通过
	 */
	audit: function(audit){
		var this_ = this;
		
		var shyj = introduce_process_info_class.getData().shenhe;
		var data = {};
		var message = "审核通过成功!";
		var tip = "您确定要审核通过吗？";
		var paramsMap = {};
		if(audit != null && audit != ''){
			paramsMap.audit = audit;
			data.shjl = 3;
		}else{
			/*if (shyj == null || shyj == '') {
				AlertUtil.showModalFooterMessage("审核驳回意见不能为空!");
				return false;
			}*/
			message = "审核驳回成功!";
			tip = "您确定要审核驳回吗？";
			data.shjl = 5;
		}
		
		data.paramsMap = paramsMap;
		data.shyj = shyj;
		data.jx = $.trim($("#jx",$("#"+this_.id)).val());
		data.dprtcode = this_.param.dprtcode;
		
		AlertUtil.showConfirmMessage(tip, {callback: function(){
			
			if(this_.param.callback && typeof(this_.param.callback) == "function"){
				this_.param.callback(data,message);
			}
		
		}});
		
	},
	/**
	 * 批准数据
	 * approve approve:批准通过
	 */
	approve: function(approve){
		var this_ = this;
		
		var pfyj = introduce_process_info_class.getData().shenpi;
		var data = {};
		var message = "批准通过成功!";
		var tip = "您确定要批准通过吗？";
		var paramsMap = {};
		if(approve != null && approve != ''){
			paramsMap.approve = approve;
			data.pfjl = 4;
		}else{
			/*if (pfyj == null || pfyj == '') {
				AlertUtil.showModalFooterMessage("批准驳回意见不能为空!");
				return false;
			}*/
			message = "批准驳回成功!";
			tip = "您确定要批准驳回吗？";
			data.pfjl = 6;
		}
		
		data.paramsMap = paramsMap;
		data.pfyj = pfyj;
		data.jx = $.trim($("#jx",$("#"+this_.id)).val());
		data.dprtcode = this_.param.dprtcode;
		
		AlertUtil.showConfirmMessage(tip, {callback: function(){
			
			if(this_.param.callback && typeof(this_.param.callback) == "function"){
				this_.param.callback(data,message);
			}
		
		}});
		
	},
	getWorkCardData : function(operationType){
		var this_ = this;
		var data = {};
		data.gkh = $.trim($("#gkh",$("#"+this_.id)).val());
		if(this.param.editParam == this.editParam.modify){
			data.bb = $.trim($("#new_bb",$("#"+this_.id)).val());
		}else{
			if(this.param.viewObj.fBbid != null && this.param.viewObj.fBbid != ''){
				data.bb = $.trim($("#edit_bb",$("#"+this_.id)).val());
			}else{
				data.bb = $.trim($("#bb",$("#"+this_.id)).val());
			}
		}
		data.jx = $.trim($("#jx",$("#"+this_.id)).val());
		data.rwdh = $.trim($("#rwdh",$("#"+this_.id)).val());
		data.gzdh = $.trim($("#gzdh",$("#"+this_.id)).val());
		data.kzh = $.trim($("#kzh",$("#"+this_.id)).val());
		data.zjh = $.trim($("#zjh_e",$("#"+this_.id)).val());
		data.wxxmbh = $.trim($("#wxxmbh",$("#"+this_.id)).val());
		data.cmph = $.trim($("#cmph",$("#"+this_.id)).val());
		data.gzbt = $.trim($("#gzbt",$("#"+this_.id)).val());
		data.gzlx = $.trim($("#gzlx_e",$("#"+this_.id)).val());
		data.gklx = $.trim($("#gklx_e",$("#"+this_.id)).val());
		data.zy = $.trim($("#zy_e",$("#"+this_.id)).val());
		data.gzzid = $.trim($("#gzz_e",$("#"+this_.id)).val());
		data.jhgsRs = $.trim($("#jhgsRs",$("#"+this_.id)).val());
		data.jhgsXss = $.trim($("#jhgsXss",$("#"+this_.id)).val());
		data.yjwj = $.trim($("#yjwj",$("#"+this_.id)).val());
		data.tbsysm = $.trim($("#tbsysm",$("#"+this_.id)).val());
		data.gzgs = $.trim($("#gzgs",$("#"+this_.id)).val());
		data.bz = $.trim($("#bz",$("#"+this_.id)).val());
		data.dprtcode = this_.param.dprtcode;
		
		data.isBj = $("#isBj",$("#"+this_.id)).is(":checked")?1:0;
		data.worktype = $("#worktype",$("#"+this_.id)).is(":checked")?1:0;
		
		data.gkfjid = $.trim($("#gkfjid",$("#"+this_.id)).val());
		//设置工卡附件
		var workCardAttachment = attachmentsSingleUtil.getAttachment('work_card_attachments_single_edit');
		if(workCardAttachment != null && workCardAttachment.wjdx != null && workCardAttachment.wjdx != ''){
			data.workCardAttachment = workCardAttachment;
		}
		
		data.gznrfjid = $.trim($("#gznrfjid",$("#"+this_.id)).val());
		//设置工作内容
		var workContentAttachment = attachmentsSingleUtil.getAttachment('work_content_attachments_single_edit');
		if(workContentAttachment != null && workContentAttachment.wjdx != null && workContentAttachment.wjdx != ''){
			data.workContentAttachment = workContentAttachment;
		}
		
		// 技术评估单数据
		var ywdjzt = 1;
		if(operationType != null && operationType != ''){
			ywdjzt = 2
			
		}
		var technicalList = evaluation_modal.getData();
		var instructionsourceList = [];
		if (technicalList != null && technicalList != '') {
			// 组装下达指令数据
			$.each(technicalList, function(index, row) {
				var instructionsource = {};
				instructionsource.dprtcode = row.dprtcode;
				instructionsource.pgdid = row.id;
				instructionsource.pgdh = row.pgdh;
				instructionsource.bb = row.bb;
				instructionsource.zlbh = data.gkh;
				instructionsource.zlbb = data.bb;
				instructionsource.ywzt = data.gzbt;
				instructionsource.ywdjzt = ywdjzt;
				instructionsourceList.push(instructionsource);
			});
		}
		data.instructionsourceList = instructionsourceList;
		
		//设置区域
		var coverPlateList = [];
		var zone = {};
		zone.gbid = '';
		zone.gbh = $.trim($("#eqy",$("#"+this_.id)).val());
		zone.xc = 1;
		zone.lx = 1;
		coverPlateList.push(zone);
		/*var qyStr = $.trim($("#eqy",$("#"+this_.id)).val());
		if(qyStr != null && qyStr.length > 0){
			var qyArr = qyStr.split(",");
			for(var i = 0 ; i < qyArr.length ; i++){
				if('multiselect-all' != qyArr[i]){
					var coverPlate = {};
					coverPlate.gbid = qyArr[i];
					coverPlate.gbh = $("#eqy",$("#"+this_.id)).find("option[value='"+qyArr[i]+"']").text();
					coverPlate.lx = 1;
					coverPlate.xc = i;
					coverPlateList.push(coverPlate);
				}
			}
		}*/
		
		//设置接近
		$.each(this_.accessList,function(index,row){
			var coverPlate = {};
			coverPlate.gbid = row.id;
			coverPlate.gbh = row.gbbh;
			coverPlate.lx = 2;
			coverPlate.xc = index + 1;
			coverPlateList.push(coverPlate);
		});
		//设置飞机站位
		var position = {};
		position.gbid = '';
		position.gbh = $.trim($("#efjzw",$("#"+this_.id)).val());
		position.xc = 1;
		position.lx = 3;
		coverPlateList.push(position);
		/*$(this_.positionList).each(function(i) {
			var coverPlate = {};
			coverPlate.gbid = this.id;;
			coverPlate.gbh = this.sz;
			coverPlate.xc = i + 1;
			coverPlate.lx = 3;
			coverPlateList.push(coverPlate);
		});*/
		
		data.coverPlateList = coverPlateList;
		
		//设置工卡-适用单位
		var sydwStr = $.trim($("#esydw",$("#"+this_.id)).val());
		var applicableUnitList = [];
		if(sydwStr != null && sydwStr.length > 0){
			var sydwArr = sydwStr.split(",");
			for(var i = 0 ; i < sydwArr.length ; i++){
				if('multiselect-all' != sydwArr[i]){
					var applicableUnit = {};
					applicableUnit.sydwid = sydwArr[i];
					applicableUnit.sydw = $("#esydw",$("#"+this_.id)).find("option[value='"+sydwArr[i]+"']").text();
					applicableUnitList.push(applicableUnit);
				}
			}
		}
		data.applicableUnitList = applicableUnitList;
		
		//设置工种工时
		data.workHourList = work_hour_edit.getWorkHourList();
		//设置参考文件
		data.referenceList = ReferenceUtil.getData();
		//设置工卡-关联工
		data.workCard2RelatedList = WorkCardUtil.getData();
		//设置工作内容
		data.workContentList = WorkContentUtil.getData(data.isBj);
		//设置器材/工具
		var materialToolList = [];
		//设置器材清单
		materialToolList = Equipment_list_edit.getData();
		//设置工具设备
		var toolArr = Tools_list_edit.getData();
		$.each(toolArr,function(index,row){
			var infos = {};
			infos.id = row.id;
			infos.bjid = row.bjid;
			infos.jh = row.jh;
			infos.bjlx = row.bjlx;
			infos.sl = row.sl;
			infos.bz = row.bz;
			infos.jhly = row.jhly;
			infos.qcdh = row.qcdh;
			infos.bxx = row.bxx;
			infos.xc = row.xc;
			materialToolList.push(infos);
		});
		data.materialToolList = materialToolList;
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		data.attachmentList = attachmentsObj.getAttachments();
		
		return data;
	},
	/**
	 * 判断是否查看
	 */
	isView : function(){
		if(this.param.editParam == this.editParam.audit || this.param.editParam == this.editParam.approve){
			return true;
		}
		return false;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	formValidator : function(){//加载验证
		validatorForm =  $('#work_card_form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	gkh: {
	                validators: {
	                	notEmpty: {
	                        message: '工卡号不能为空'
	                    },
	                    regexp: {
	                    	regexp: /^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$/,
	                        message: '只能输入长度不超过50个字符的英文、数字、英文特殊字符'
	                    }
	                }
	            },
	            bb: {
	                validators: {
	                	notEmpty: {
	                        message: '版本不能为空'
	                    }
	                }
	            },
	            jx: {
	                validators: {
	                	notEmpty: {
	                        message: '请选择机型'
	                    }
	                }
	            }
	        }
	    });	 
	},
	//改变人数时触发
	changeRs : function(obj){
		this.clearNoNumber(obj);
		this.sumTotal();
	},
	//改变小时数触发
	changeXss : function(obj){
		this.clearNoNumTwo(obj);
		this.sumTotal();
	},
	//改变版本时触发
	changeBb : function(obj){
		this.clearNoNumTwo(obj);
		/*$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	//计算总数
	sumTotal : function(){
		var total = 0;
		var jhgsRs = $("#jhgsRs",$("#"+this.id)).val();
		var jhgsXss = $("#jhgsXss",$("#"+this.id)).val();
		total = jhgsRs*jhgsXss;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#bzgs",$("#"+this.id)).html(total);
	},
	//只能输入数字
	clearNoNumber : function(obj){
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
}
function customResizeHeight(bodyHeight, tableHeight){
	work_card_edit_alert_Modal.resizeVersionWidth();
}
