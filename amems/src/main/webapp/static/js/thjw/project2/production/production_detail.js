	
$(function(){
	// 生产指令详情初始化
	production_detail_modal.init();
});

/**
 * 生产指令详情
 */
var production_detail_modal = {
		
	param: {
		type:"add",//add-新增，edit-修改，revision-改版，audit-审核，approve-审批，view-查看
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	/** 模态框id */
	id : "production_detail_modal",
	
	/**
	 * 初始化
	 */
	init : function(){
		
		var this_ = this;
		
		// 初始化控件
		this_.initWidget();
		
		// 章节号自动补全
		this_.initAutoComplete();
		
		// 初始化数据字典和枚举
		this_.initDicAndEnum();
		
		// 初始化监控项目
		this_.initMonitorItem();
		
	},
	
	
	show : function(param){
		var this_ = this;
		
		// 模态框显示
		if(param.type != "view"){
			$("#"+this_.id).modal("show");
		}
		// 设置参数
		if(param){
			$.extend(this.param, param);
		}
		// 初始化标题
		this_.initTitle();
		// 初始化验证
		this_.initValidation();
		// 根据页面类型调整页面
		this_.adjustPageByType();
		// 加载数据
		this_.loadData();
	},
	
	/**
	 * 初始化标题
	 */
	initTitle : function(){
		var type = this.param.type;
		if(type == "add"){
			$("#head_cn").text("新增生产指令");
			$("#head_eng").text("Add Production Order");
		}else if(type == "edit"){
			$("#head_cn").text("编辑生产指令");
			$("#head_eng").text("Edit Production Order");
		}else if(type == "audit"){
			$("#head_cn").text("审核生产指令");
			$("#head_eng").text("Review Production Order");
		}else if(type == "approve"){
			$("#head_cn").text("审批生产指令");
			$("#head_eng").text("Approve Production Order");
		}else if(type == "revision"){
			$("#head_cn").text("改版生产指令");
			$("#head_eng").text("Revision Production Order");
		}else if(type == "view"){
			$("#head_cn").text("查看生产指令");
			$("#head_eng").text("View Production Order");
		}
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		
		// 初始化适用性多选控件
		$('#syx').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    buttonContainer: '<div class="btn-group" style="width:100%;" />',
		    numberDisplayed:99,
		    maxHeight:300,
		    includeSelectAllOption: false,
		    onChange:function(element,checked){
		    	if(element.val() == "00000"){
		    		$('#syx>option').each(function(element) {
		    	    	$('#syx').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#syx').multiselect('select', '00000');
		    	}else if(element.val() == "null"){
		    	    $('#syx>option').each(function(element) {
	    	    		$('#syx').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#syx').multiselect('select', 'null');
		    	}else{
		    		if(checked){
		    			 $('#syx').multiselect('deselect', 'null');
		    			 $('#syx').multiselect('deselect', '00000');
		    		}
		    	}
	  	    }
	    });
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		
		// 枚举-计算公式
		$("#jsgs").empty();
		DicAndEnumUtil.registerEnum("computationalFormulaEnum", 'jsgs');
		
		// 初始化飞机机型
		var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : this.param.dprtcode});
		if(typeList.length > 0){
			for(var i = 0; i < typeList.length; i++){
				$("#fjjx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
			}
		}else{
			$("#fjjx").append("<option value=''>暂无飞机机型No data</option>");
		}
		
		// 初始化适用性-飞机注册号
		this.refreshFjzch();
	},
	
	/**
	 * 初始化监控项目
	 */
	initMonitorItem : function(){
		if(!this.monitorUtil){
			var monitorUtil = new MonitorUtil({});
			this.monitorUtil = monitorUtil;
		}
	},
	
	/**
	 * 获取监控工具
	 */
	getMonitorUtil : function(){
		if(!this.monitorUtil){
			this.initMonitorItem();
		}
		return this.monitorUtil;
	},
	
	/**
	 * 章节号自动补全
	 */
	initAutoComplete : function() {
		var this_ = this;
		 $("#zjhms").typeahead({
				displayText : function(item){
					return StringUtil.escapeStr(item.zjh)+" "+StringUtil.escapeStr(item.ywms);
				},
			    source: function (query, process) {
			    	AjaxUtil.ajax({
						url: basePath+"/project/fixchapter/limitTen",
						type: "post",
						contentType:"application/json;charset=utf-8",
						dataType:"json",
						data:JSON.stringify({
							zjh : query.toUpperCase(),
							dprtcode : this_.param.dprtcode
						}),
						success:function(data){
							if(data.length == 0){
								$("#zjh").val("");
							}
							process(data);
					    }
					}); 
			    },
		       updater: function (item) {
		    	  $("#zjh").val(StringUtil.escapeStr(item.zjh));
		    	  $("#production_detail_form").data('bootstrapValidator')
		    	  	.updateStatus("zjh", 'NOT_VALIDATED',null).validateField("zjh");
		    	  return item;
		       }
	     });
	},
	
	/**
	 * 改版飞机机型
	 */
	changeFjjx : function(){
		this.refreshFjzch();
		$("#gkid").val('');
		$("#gkbh").val('');
		$("#gkms").val('');
	},
	
	/**
	 * 刷新飞机注册号
	 */
	refreshFjzch : function(){
		var planeRegOption = '';
		planeRegOption += "<option value='00000'>ALL</option>";
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this.param.dprtcode,FJJX:$("#fjjx").val()});
		planeDatas && $.each(planeDatas, function(i,planeData){
			var tempOption = "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			planeRegOption += tempOption;
		});
		planeRegOption += "<option value='null'>N/A</option>";
		$("#syx").html(planeRegOption);
		$("#syx").multiselect('rebuild');
		$('#syx').multiselect('select', '00000');
	},
	
	/**
	 * 加载数据
	 */
	loadData : function(){
		
		var this_ = this;
		
		var id = this_.param.id;
		if(id){	// 按照id加载数据
			startWait();
			AjaxUtil.ajax({
				url: basePath+"/project2/production/detail",
				type: "post",
				dataType:"json",
				data:{
					id : id,
				},
				success:function(data){
					finishWait();
					this_.fillData(data);
			    }
			}); 
			production_process_record.loadData(id, $("#production_process_record_panel"));
		 	production_history_version.loadData(id, $("#production_history_version_panel"));
		}else{ // 重置数据
			this_.fillData({});
		}
	},
	
	/**
	 * 填充数据
	 */
	fillData : function(data){
		
		var this_ = this;
		
		AlertUtil.hideModalFooterMessage();
		// 填充主数据
		$("#id").val(data.id);
		$("#zt").val(data.zt);
		$("#zlbh").val(data.zlbh);
		$("#zjh").val(data.zjh);
		$("#zjhms").val(data.fixChapter ? data.fixChapter.displayName : "");
		if(data.jx){
			$("#fjjx").val(data.jx);
		}else{
			$("#fjjx option:first").prop("selected", 'selected');  
		}
		$("#zlms").val(data.zlms);
		$("#gkid").val(data.gkid);
		$("#gkbh").val(data.gkbh);
		$("#gkms").val(data.gkid ? (data.gkbh + " R" +data.gkbb + " " +data.gkbt) : "");
		
		$("#shyj").val("");
		$("#spyj").val("");
		
		var type = this_.param.type;
		if(type == "revision"){
			$("#bb").text(parseFloat(data.bb)+1);
		}else{
			$("#bb").text(data.bb || "1");
		}
		// 加载版本历史
		this_.initHistoryList(data.id);
		
		// 填充适用性
		this.refreshFjzch();
		if(data.id){
			var planes = data.planes;
			var planeStrList = [];
			if(data.syx == "00000"){
				planeStrList.push("00000");
			}else if(!data.syx){
				planeStrList.push("null");
			}else if(planes && planes.length > 0){
				planeStrList = $.map(planes, function(obj){
					return obj.fjzch;
				});
			}
			$('#syx').multiselect('deselect', '00000');
			$('#syx').multiselect('select', planeStrList);
		}else{
    	    $('#syx').multiselect('select', '00000');
		}
		$("#syx_input").val($("#syx").next().find("button").text());
		
		// 填充监控项
		this_.getMonitorUtil().loadData(data.monitors);
		if(data.jsgs){
			$("#jsgs").val(data.jsgs);
		}else{
			$("#jsgs option:first").prop("selected", 'selected');  
		}
		$("#isHdwz").removeAttr("checked");
		if(data.isHdwz == 1){
			$("#isHdwz").attr("checked",'true');
		}
		if(data.wz){
			$("#wz").val(data.wz);
		}else{
			$("#wz option:first").prop("selected", 'selected');  
		}
		$("#jgms").val(data.jgms);
		
		// 填充附件
		this_.loadAttachment(data.id);
		
		if(data.zt == 5 || data.zt == 6){
			$("#save_btn").hide();
		}
	},
	
	/**
	 * 加载版本历史
	 */
	initHistoryList : function(id){
		var this_ = this;
		var bb = $("#bb").text();
		$("span[name='lastBbSpan']").hide();
		id && productionHistory.getHistoryData(id, bb, function(list){
			WebuiPopoverUtil.initWebuiPopover('attachment-view3',"#"+this_.id,function(data){
				return productionHistory.buildHistoryHtml(list);
			});
			if(list && list.length > 0){
				$("#lastBbData").html('<a href="javascript:void(0);" onclick="'+this_.id+'.viewProductionOrder(\''+list[0].id+'\')"> R'+list[0].bb+'</a>');
				$("span[name='lastBbSpan']").show();
			}
		});
	},
	
	/**
	 * 加载附件
	 */
	loadAttachment : function(id){
		var type = this.param.type;
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('production_attachments_list');
		attachmentsObj.show({
			attachHead : true,
			djid:formatUndefine(id),
			fileType:"productionorder",
	 		colOptionhead : type != "audit" && type != "approve" && type != "view",
			fileHead : true,
			left_column : true,
		});
	},
	
	/**
	 * 根据页面类型调整页面
	 */
	adjustPageByType : function(){
		var this_ = this;
		var type = this_.param.type;
		
		// 新增页面不显示流程记录和历史数据
		$("#production_process_record_panel").hide();
		$("#production_history_version_panel").hide();
		if(type != "add"){
			$("#production_process_record_panel").show();
			$("#production_history_version_panel").show();
		}
		
		
		// 显示审核审批意见
		$("#production_process_info_panel,#production_process_info_shyj,#production_process_info_spyj").hide();
		if(type == "audit"){
			$("#production_process_info_panel").show();
			$("#production_process_info_shyj").show();
		}
		if(type == "approve"){
			$("#production_process_info_panel").show();
			$("#production_process_info_spyj").show();
		}
		
		// 审核、审批、查看页面，禁用页面元素
		if(type == "audit" || type == "approve" || type == "view"){
			$("#"+this_.id).find("input,select").attr("disabled", "disabled");
			$("#zlms,#jgms").attr("disabled", "disabled");
			$("#zjh_btn,#gk_btn").hide();
			$("#gkms").removeClass("readonly-style");
			$("#syx").next().hide();
			$("#syx_input").show();
			$(".identifying").hide();
			this_.getMonitorUtil().setDisable(true);
		}else{
			$("#"+this_.id).find("input,select").removeAttr("disabled");
			$("#zlms,#jgms").removeAttr("disabled");
			$("#zjh_btn,#gk_btn").show();
			$("#gkms").addClass("readonly-style");
			$("#syx").next().show();
			$("#syx_input").hide();
			$(".identifying").show();
			this_.getMonitorUtil().setDisable(false);
		}
		
		if(type == "edit" || type == "revision"){
			$("#zlbh").attr("disabled", "disabled");
		}
		
		// 显示按钮
		$("#save_btn,#submit_btn,#audit_pass_btn,#audit_reject_btn," +
		"#approve_pass_btn,#approve_reject_btn,#revision_save_btn,#revision_submit_btn").hide();
		if(type == "add"){
			$("#save_btn,#submit_btn").show();
		}else if(type == "edit"){
			$("#save_btn,#submit_btn").show();
		}else if(type == "audit"){
			$("#audit_pass_btn,#audit_reject_btn").show();
		}else if(type == "approve"){
			$("#approve_pass_btn,#approve_reject_btn").show();
		}else if(type == "revision"){
			$("#revision_save_btn,#revision_submit_btn").show();
		}
	},
	
	/**
	 * 获取页面数据
	 */
	getData : function(){
		var this_ = this;
		var obj = {};
		obj.dprtcode = this_.param.dprtcode;
		obj.id = $.trim($("#id").val());
		obj.bb = $.trim($("#bb").text());
		obj.zlbh = $.trim($("#zlbh").val());
		obj.zjh = $.trim($("#zjh").val());
		obj.jx = $.trim($("#fjjx").val());
		obj.zlms = $.trim($("#zlms").val());
		obj.gkbh = $.trim($("#gkbh").val());
		
		var syxStr = $.trim($("#syx").val());
		if(syxStr != null && syxStr.length > 0){
			var syx = '-';
			var planes = [];
			var syxArr = syxStr.split(",");
			for(var i = 0 ; i < syxArr.length ; i++){
				if("00000" == syxArr[i]){
					syx = '00000';
					break;
				}
				if("null" == syxArr[i]){
					syx = '';
					break;
				}
				if('multiselect-all' != syxArr[i]){
					var plane = {};
					plane.fjzch = syxArr[i];
					plane.xc = i + 1;
					planes.push(plane);
				}
			}
			if(syx == '-' && planes.length > 0){
				obj.planes = planes;
			}
			obj.syx = syx;
		}
		obj.monitors = this_.getMonitorUtil().getMonitorSettingParam();
		obj.wz = $.trim($("#wz:visible").val());
		obj.jsgs = $.trim($("#jsgs").val());
		obj.isHdwz = $("#isHdwz").is(":checked") ? 1 : 0;
		obj.jgms = $.trim($("#jgms").val());
		
		var type = this_.param.type;
		if(type == "audit"){
			obj.shyj = $.trim($("#shyj").val());
		}
		if(type == "approve"){
			obj.spyj = $.trim($("#spyj").val());
		}
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('production_attachments_list');
		obj.attachments = attachmentsObj.getAttachments();
		return obj;
	},
	
	/**
	 * 验证数据
	 */
	validateData : function(obj){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		
		// 验证章节号是否存在
		var zjh = $.trim($("#zjh").val());
		var zjhms = $.trim($("#zjhms").val());
		if(!zjh && zjhms){
			var fixChapter = null;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project/fixchapter/getFixChapterByZjh",
				type:"post",
				data:{zjh:zjhms,dprtcode:this_.param.dprtcode},
				dataType:"json",
				success:function(data){
					fixChapter = data;
				},
			});
			if(fixChapter == null){
				AlertUtil.showModalFooterMessage("章节号不存在！", this_.id);
				return false;
			}else{
				var wczjhName = formatUndefine(fixChapter.zjh) + " " +formatUndefine(fixChapter.ywms);
				$("#zjh").val(fixChapter.zjh);
				$("#zjhms").val(wczjhName);
				obj.zjh = fixChapter.zjh;
			}
		}
		
		$('#production_detail_form').data('bootstrapValidator').resetForm(false);
		$('#production_detail_form').data('bootstrapValidator').validate();
		if(!$('#production_detail_form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
			return false;
		}
		
		var msg = this.getMonitorUtil().validateMonitorItem();
		if(msg){
			AlertUtil.showModalFooterMessage(msg);
			return false;
		}
		
		return true;
	},
	
	/**
	 * 保存数据
	 */
	saveData : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!this_.validateData(obj)){
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/save",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("保存成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 提交数据
	 */
	submitData : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!this_.validateData(obj)){
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/submit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("提交成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 审核通过
	 */
	auditPass : function(){
		var this_ = this;
		var obj = this_.getData();
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/auditagree",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("审核成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 审核驳回
	 */
	auditReject : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!obj.shyj){
			AlertUtil.showModalFooterMessage("请填写审核意见！");
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/auditreject",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("审核成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 审批通过
	 */
	approvePass : function(){
		var this_ = this;
		var obj = this_.getData();
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/approveagree",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("审批成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 审批驳回
	 */
	approveReject : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!obj.spyj){
			AlertUtil.showModalFooterMessage("请填写审批意见！");
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/approvereject",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("审批成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 改版保存
	 */
	revisionSave : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!this_.validateData(obj)){
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/revisionsave",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("改版成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 改版提交
	 */
	revisionSubmit : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!this_.validateData(obj)){
			return false;
		}
		startWait($("#"+this_.id));
		AjaxUtil.ajax({
			url:basePath+"/project2/production/revisionsubmit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#"+this_.id),
			success:function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("改版成功！");
				$("#"+this_.id).modal("hide");
				production.reload();
			}
		});
	},
	
	/**
	 * 验证小数
	 */
	validateDecimal : function(obj){
		var value = $(obj).val();
		while(!(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 验证非中文
	 */
	validateNonChinese : function(obj){
		var value = $(obj).val();
		while(!(/^[\x00-\xff]*$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 打开章节号弹框
	 */
	openChapterWin : function(){
		var zjh = $.trim($("#zjh").val());
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#zjh").val(data ? data.zjh : "");
				$("#zjhms").val(data ? data.displayName : "");
				$("#production_detail_form").data('bootstrapValidator').resetForm(false); 
			}
		});
	},
	
	/**
	 * 打开工卡弹框
	 */
	openWorkCardWin : function(){
		var this_ = this;
		open_win_work_card.show({
			selected:$("#gkid").val(),//原值，在弹窗中默认勾选
			jx:$("#fjjx").val(),
			dprtcode:this_.param.dprtcode, //机构代码
			parentWinId:this_.id,
			clearData:true,
			callback: function(data){//回调函数
				if(data != null && data.id != null){
					var gkms = StringUtil.escapeStr(data.gkh) + " R" +StringUtil.escapeStr(data.bb) + " " +StringUtil.escapeStr(data.gzbt);
					$("#gkid").val(data.id);
					$("#gkbh").val(data.gkh);
					$("#gkms").val(gkms);
				}else{
					$("#gkid").val('');
					$("#gkbh").val('');
					$("#gkms").val('');
				}
			}
		}, true);
	},
	
	/**
	 * 查看生产指令
	 */
	viewProductionOrder : function(id){
		window.open(basePath + "/project2/production/view?id=" + id);
	},
	
	/**
	 * 初始化验证
	 */
	initValidation : function(){
		if($("#production_detail_form").data('bootstrapValidator')){
			$("#production_detail_form").data('bootstrapValidator').destroy();
			$('#production_detail_form').data('bootstrapValidator', null);
		}
		$('#production_detail_form').bootstrapValidator({
	        message: '数据不合法',
	        excluded: [':disabled'],
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	zlbh: {
	                validators: {
	                	regexp: {
	                        regexp: /^[\x00-\xff]*$/,
	                        message: '不能输入中文'
	                    }
	                }
	            },
	            zjh: {
	                validators: {
	                    notEmpty: {
	                        message: '章节号不能为空'
	                    },
	                }
	            },
	            fjjx: {
	                validators: {
	                	notEmpty: {
	                        message: '飞机机型不能为空'
	                    }
	                }
	            },
	            zlms: {
	                validators: {
	                	notEmpty: {
	                        message: '指令描述不能为空'
	                    }
	                }
	            },
	        }
	    });
	},
	
};


function customResizeHeight(bodyHeight, tableHeight){
	
}