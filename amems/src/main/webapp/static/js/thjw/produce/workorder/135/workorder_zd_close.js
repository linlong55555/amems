/**
 * 135工单完工关闭
 */
Workorder135ZDCloseWin = {
	id:'workorder135_zdclose_Modal', //窗口ID
	tbodyId:'wo135zdclose_list', //列表ID
	colOptionheadClass : "colOptionhead",
	param: {
		modalHeadCN : '',//窗口中文名称
		modalHeadENG : '',//窗口英文名称
		parentWinId : '',//父窗口ID
		colOptionhead : true,
		isShowReplacementRecord : true,//是否显示拆换件记录列表
		viewObj:{},
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){},//回调函数
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	/**显示窗口*/
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		$(".time-masked").mask("99:99");
		
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
		/**控制操作  */
		this.doOption();
		this.initInfo();
	},
	doOption:function(){
		var this_ = this;
		if(this_.param.colOptionhead){
			$("#wo135zdclose_gbyy").attr("disabled",false);
			$("#"+this.id+"_save").show();
			$("#"+this_.id+"_zdjsyymark").show();
		}else{
			$("#wo135zdclose_gbyy").attr("disabled",true);
			$("#"+this.id+"_save").hide();
			$("#"+this_.id+"_zdjsyymark").hide();
		}
	},
	/**初始数据*/
	initInfo : function(){
		var this_ = this;
		
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#wo135zdclose_from").data('bootstrapValidator').destroy();
		     $('#wo135zdclose_from').data('bootstrapValidator', null);
		     this_.formValidator();
		});
		
		//初始窗口标题信息
		this.initModelHead();
		
		//显示窗口
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		
//		//显示拆换件列表
//		if(this.param.isShowReplacementRecord){
//			this.showReplacementRecord();
//		}
		//显示附件列表
		/*this.showAttachments();*/
		//加载数据
		this.loadeBaseData();
	},
	/**加载基础数据*/
	loadeBaseData : function(){
		var this_ = this;
		var total = 0;
		var jhgsRs = this_.param.viewObj.jhgsRs;
		var jhgsXss = this_.param.viewObj.jhgsXss;
		total = jhgsRs*jhgsXss;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		
		var bjh = "";
		var xlh = "";
		if(null != this_.param.viewObj.monitoringObject){
			bjh = this_.param.viewObj.monitoringObject.bjh;//部件号
			xlh = this_.param.viewObj.monitoringObject.xlh;//序列号
		}
		
		var zjh = "";
		if(null != this_.param.viewObj.fixChapter){
			zjh = this_.param.viewObj.fixChapter.displayName;//ATA章节号Name
		}
		
		var htmlContent = "<tr class='text-center'>";
		    htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(this_.param.viewObj.gdlx)))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(this_.param.viewObj.gdlx)))+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(this_.param.viewObj.gdbh)+"'>"+StringUtil.escapeStr(this_.param.viewObj.gdbh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(zjh)+"'>"+StringUtil.escapeStr(zjh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(this_.param.viewObj.gdbt)+"'>"+StringUtil.escapeStr(this_.param.viewObj.gdbt)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(total)+"'>"+StringUtil.escapeStr(total)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(bjh)+"'>"+StringUtil.escapeStr(bjh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(xlh)+"'>"+StringUtil.escapeStr(xlh)+"</td>";
		    htmlContent += "</tr>";
		
		$("#wo135zdclose_list").html(htmlContent);
		$("#wo135zdclose_gdlx").val("指定结束");
//		$("#wo135zdclose_gdlx").val(formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(this_.param.viewObj.gdlx)))); //工单类型
		if(null != this_.param.viewObj.xfrDepartment){
			$("#wo135zdclose_xfdw").val(StringUtil.escapeStr(this_.param.viewObj.xfrDepartment.dprtname)); //下发单位
		}
		if(null != this_.param.viewObj.jhKsrq){
			$("#wo135zdclose_jhksrq").val(StringUtil.escapeStr(this_.param.viewObj.jhKsrq).substring(0, 10)); //计划开始日期
		}
		if(null != this_.param.viewObj.jhJsrq){
			$("#wo135zdclose_jhjsrq").val(StringUtil.escapeStr(this_.param.viewObj.jhJsrq).substring(0, 10)); //计划结束日期
		}
		if(null != $.trim(this_.param.viewObj.paramsMap.gbrname) &&"" != $.trim(this_.param.viewObj.paramsMap.gbrname)){
			$("#wo135zdclose_gbr").val(this_.param.viewObj.paramsMap.gbrname);
		}else{
			$("#wo135zdclose_gbr").val(StringUtil.escapeStr(currentUser.displayName)); //关闭人		
		}
		if(null != this_.param.viewObj.gbrq){
			$("#wo135zdclose_gbrq").val(StringUtil.escapeStr(this_.param.viewObj.gbrq).substring(0, 10));
		}else{
			TimeUtil.getCurrentDate("#wo135zdclose_gbrq");//关闭日期	
		}
		$("#wo135zdclose_gbyy").val(StringUtil.escapeStr(this_.param.viewObj.gbyy)); //关闭原因
		
	},
	/**初始化对话框头部*/
	initModelHead : function(){
		if('' != this.param.modalHeadCN){
			$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
			$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
		}
	},
	/**显示拆换件记录列表*/
	showReplacementRecord : function(){
		replacementRecord.init({
			back:this.param.viewObj.woIORecordList,
			fdjsl:this.param.viewObj.paramsMap.fdjsl,
			fjjx:this.param.viewObj.paramsMap.fjjx,
			fjzch:this.param.viewObj.fjzch,
			dprtcode:this.param.viewObj.dprtcode,
			parent:this.id,
			isShowed:this.param.colOptionhead,
		});
		$("#replacementRecord_wo135zdclose_div").css("display","block");
	},
	/**显示附件*/
	showAttachments : function(){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_workorderedit_wo135zdclose');
		attachmentsObj.show({
			djid:"",
			fileHead : true,
			colOptionhead : this.param.colOptionhead,
			fileType:"wo"
		});
	},
	/**获取数据*/
	getData : function(){
		var this_ = this;
		
		var param = {};
		param.id = this_.param.viewObj.id; //工单ID
		param.gbyy = $("#wo135zdclose_gbyy").val();//关闭详情
		param.zt = 9;//状态9指定结束
		var woIORecordList = replacementRecord.getData();
		param.woIORecordList=woIORecordList;
		return param;
	},
	/**工单关闭*/
	submitFeedback :  function(){
		var this_ = this;
		
		$('#wo135zdclose_from').data('bootstrapValidator').validate();
		if(!$('#wo135zdclose_from').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		
		//获取数据
		var reqData = this_.getData(); 
		
		AlertUtil.showConfirmMessage("您确定要提交吗？", {callback: function(){
			this_.param.callback(reqData);
		}});
	},
	formValidator : function(){//加载验证
		validatorForm =  $('#wo135zdclose_from').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	wo135zdclose_gbyy: {
	            	validators: {
	            		notEmpty: {
	            			message: '指定结束原因不能为空'
	            		}
	            	}
	            },
	        }
	    });	 
	},
};