/**
 * 135工单完工反馈
 */
Workorder135FeedbackWin = {
	id:'workorder135_feedback_Modal', //窗口ID
	tbodyId:'wo135feedback_list', //列表ID
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
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$(".time-masked").mask("99:99");
		
		/**清空所有元素*/
		this.clearDatas();
		/**控制操作  */
		this.doOption();
		/**初始数据*/
		this.initInfo();
	},
	/**清空所有元素*/
	clearDatas : function(){
		var this_ = this;
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
	},
	/** 控制操作  */
	doOption:function(){
		var this_ = this;
		if(this_.param.colOptionhead){
			$("#"+this.id+" input[type='text']").attr("disabled",false);
			$("#"+this.id+" input:checkbox").attr("disabled",false);
			$("#"+this.id+" textarea").attr("disabled",false);
			$("#"+this.id+"_save").show();
			$("#"+this_.id+"_sjwcrq_remark").show();
			$("#wo135feedback_sjgzz_btn").show();
			$("#wo135feedback_sjjcz_btn").show();
			$("#wo135feedback_sjgzz").addClass("readonly-style");
		}else{
			$("#"+this.id+" input[type='text']").attr("disabled",true);
			$("#"+this.id+" input:checkbox").attr("disabled",true);
			$("#"+this.id+" textarea").attr("disabled",true);
			$("#"+this.id+"_save").hide();
			$("#"+this_.id+"_sjwcrq_remark").hide();
			$("#wo135feedback_sjgzz_btn").hide();
			$("#wo135feedback_sjjcz_btn").hide();
			$("#wo135feedback_sjgzz").removeClass("readonly-style");
		}
	},
	/**初始数据*/
	initInfo : function(){
		var this_ = this;
		
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#wo135feedback_from").data('bootstrapValidator').destroy();
		     $('#wo135feedback_from').data('bootstrapValidator', null);
		     this_.formValidator();
		});
		//实际完成日期
		$('#wo135feedback_sjJsrq').datepicker({
			autoclose : true,
			clearBtn : true
		}).on('hide',function(e) {
			$('#wo135feedback_from').data('bootstrapValidator').updateStatus('wo135feedback_sjJsrq','NOT_VALIDATED', null).validateField('wo135feedback_sjJsrq');
		});
		
		//初始窗口标题信息
		this.initModelHead();
		
		//显示窗口
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		
		//显示拆换件列表
		if(!this.param.viewObj.fjzch || this.param.viewObj.fjzch == "-"){
			this.param.isShowReplacementRecord = false;
		}
		if(this.param.isShowReplacementRecord){
			$("#wo135feedback_replacementRecord_div").show();
			this.showReplacementRecord();
		}else{
			$("#wo135feedback_replacementRecord_div").hide();
		}
		//显示附件列表
		this.showAttachments();
		
		//加载数据
		this.loadeBaseData();
		
		$('#workorder135_feedback_Modal .date-picker').datepicker('update');
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
		
		$("#wo135feedback_list").html(htmlContent);
		
		$("#wo135feedback_sjJsrq").val(StringUtil.escapeStr(this_.param.viewObj.sjJsrq).substring(0, 10)); //实际完成日期
		$("#wo135feedback_sjJssj").val(StringUtil.escapeStr(this_.param.viewObj.sjJsrq).substring(11, 16));//实际完成时间
//		$("#wo135feedback_sjgzz").val(this_.param.viewObj.sjGzz);//实际工作者
//		$("#wo135feedback_sjjcz").val(this_.param.viewObj.sjJcz);//实际检查者
		
		var gzzList = '';
		var gzzidList = '';
		$.each(this_.param.viewObj.workers || [], function(i, row){
			gzzList += formatUndefine(row.gzz) + ",";
			gzzidList += formatUndefine(row.gzzid) + ",";
		});
		if(gzzList != ''){
			gzzList = gzzList.substring(0,gzzList.length - 1);
		}
		if(gzzidList != ''){
			gzzidList = gzzidList.substring(0,gzzidList.length - 1);
		}
		$("#wo135feedback_sjgzz").val(gzzList);
		$("#wo135feedback_sjgzzid").val(gzzidList);
		$("#wo135feedback_sjjcz").val(this_.param.viewObj.sjJcz);
		$("#wo135feedback_sjjczid").val(this_.param.viewObj.sjJczid);
		$("#wo135feedback_hsgs").removeAttr("checked");
		if(this_.param.viewObj.hsgs == 1){
			$("#wo135feedback_hsgs").attr("checked",'true');//选中
		}
		this_.disableUser("wo135feedback_sjjcz");
		
		$("#wo135feedback_sjgs").val(this_.param.viewObj.sjGs);//实际工时
		$("#wo135feedback_sjzd").val(this_.param.viewObj.sjZd);//工作站点
		$("#wo135feedback_sjksrq").val(StringUtil.escapeStr(this_.param.viewObj.sjKsrq).substring(0, 10));//实际开始日期
		$("#wo135feedback_gzxx").val(this_.param.viewObj.gzxx);//故障信息
		$("#wo135feedback_clcs").val(this_.param.viewObj.clcs);//处理措施
		//拆换件暂缺......
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
			defaultCzrq:function(){
				var sjjsrq = $.trim($("#wo135feedback_sjJsrq").val());
				var sjjssj = $.trim($("#wo135feedback_sjJssj").val());
				var czrq = "";
				if(sjjsrq){
					czrq = sjjsrq + " " + (sjjssj||"00:00") + ":00";
				}
				return czrq;
			}
		});
	},
	/**显示附件*/
	showAttachments : function(){
		var this_ = this;
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('wo135feedback_attachments_list_workorderedit');
		attachmentsObj.show({
			djid:this_.param.viewObj.id,
			fileHead : true,
			colOptionhead : this_.param.colOptionhead,
			fileType:"WO"
		});
	},
	/**获取数据*/
	getData : function(){
		var this_ = this;
		
		var param = {};
		param.id = this_.param.viewObj.id; //工单ID
		var sjJsrq = $("#wo135feedback_sjJsrq").val() + " "+$("#wo135feedback_sjJssj").val();
		param.sjJsrq = new Date(Date.parse(sjJsrq.replace(/-/g, "/"))); ;//实际完成日期
//		param.sjGzz = $("#wo135feedback_sjgzz").val();//实际工作者
//		param.sjJcz = $("#wo135feedback_sjjcz").val();//实际检查者
		
		param.hsgs = $("#wo135feedback_hsgs").is(":checked") ? 1 : 0;
		var userList = [];
		var gzzidList = $("#wo135feedback_sjgzzid").val();
		var gzzList = $("#wo135feedback_sjgzz").val();
		for (var i = 0; i < gzzidList.split(",").length; i++) {
			var p = {
				gzzid : gzzidList.split(",")[i],
				gzz : gzzList.split(",")[i]
			};
			userList.push(p);
		}
		if (gzzidList == "") {
			userList = [];
		}
		param.workers = userList;
		param.sjJcz = $("#wo135feedback_sjjcz").val();
		param.sjJczid = $("#wo135feedback_sjjczid").val();
		
		param.sjGs = $("#wo135feedback_sjgs").val();//实际工时
		param.sjZd = $("#wo135feedback_sjzd").val();//工作站点
		param.sjKsrq = $("#wo135feedback_sjksrq").val();//实际开始日期
		param.gzxx = $("#wo135feedback_gzxx").val();//故障信息
		param.clcs = $("#wo135feedback_clcs").val();//处理措施
		param.wgbs = 1;//完工标识=1已完工
		//拆换件
		var woIORecordList = replacementRecord.getData();
		if(woIORecordList != null &&woIORecordList.length>0){
			param.woIORecordList=woIORecordList;			
		}
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('wo135feedback_attachments_list_workorderedit');
		param.attachments = attachmentsObj.getAttachments();//附件列表
		
		return param;
	},
	/**完工反馈*/
	submitFeedback :  function(){
		var this_ = this;
		
		$('#wo135feedback_from').data('bootstrapValidator').validate();
		if(!$('#wo135feedback_from').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		var sjgs = $("#wo135feedback_sjgs").val();//实际工时
		var reg = /^[0-9]{1,10}(\.[0-9]{0,2})?$/;
		if(sjgs !=='' && reg.test(sjgs)==false){
			AlertUtil.showModalFooterMessage("实际工时输入不正确，只能输入数值，精确到两位小数!");
			return false;
		}
		
		var sjjssj=$("#wo135feedback_sjJssj").val();
		if(sjjssj != ''){
			if(sjjssj.split(":")[0]>=24){
				AlertUtil.showModalFooterMessage("实际完成时间格式不正确!");
				return false;
			}
			if(sjjssj.split(":")[1]>=60){
				AlertUtil.showModalFooterMessage("实际完成时间格式不正确!");
				return false;
			}			
		}
		
		if($("#wo135feedback_sjksrq").val() != '' && $("#wo135feedback_sjJsrq").val() != '' && $("#wo135feedback_sjksrq").val()>$("#wo135feedback_sjJsrq").val()){
			AlertUtil.showModalFooterMessage("实际开始日期不能晚于实际完成日期!");
			return false;
		}
		//获取数据
		var reqData = this_.getData(); 
		
		AlertUtil.showConfirmMessage("您确定要提交吗？", {callback: function(){
			this_.param.callback(reqData);
		}});
	},
	formValidator : function(){//加载验证
		validatorForm =  $('#wo135feedback_from').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	wo135feedback_sjJsrq: {
	            	validators: {
	            		notEmpty: {
	            			message: '实际完成日期不能为空'
	            		}
	            	}
	            },
	           /* wo135feedback_sjJssj: {
	            	validators: {
	            		notEmpty: {
	            			message: '实际完成时间不能为空'
	            		}
	            	}
	            },*/
	        }
	    });	 
	},
	
	// 打开工作者弹窗
	openGzzWin : function(){
		var this_ = this;
		var userList = [];
		var gzzidList = $("#wo135feedback_sjgzzid").val();
		var gzzList = $("#wo135feedback_sjgzz").val();
		for (var i = 0; i < gzzidList.split(",").length; i++) {
			var p = {
				id : gzzidList.split(",")[i],
				displayName : gzzList.split(",")[i]
			};
			userList.push(p);
		}
		if (gzzidList == "") {
			userList = [];
		}
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:this_.param.dprtcode,
			multi:true,
			callback: function(data){//回调函数
				var displayName = '';
				var mjsrid = '';
				if(data != null && data != ""){
					$.each(data, function(i, row){
						displayName += formatUndefine(row.displayName) + ",";
						mjsrid += formatUndefine(row.id) + ",";
					});
				}
				if(displayName != ''){
					displayName = displayName.substring(0,displayName.length - 1);
				}
				if(mjsrid != ''){
					mjsrid = mjsrid.substring(0,mjsrid.length - 1);
				}
				$("#wo135feedback_sjgzz").val(displayName);
				$("#wo135feedback_sjgzzid").val(mjsrid);
			}
		});
		AlertUtil.hideModalFooterMessage();
	},
	
	// 打开检查者弹窗
	openJczWin : function(){
		var this_ = this;
		Personel_Tree_Multi_Modal.show({
			checkUserList:[{id:$("#wo135feedback_sjjczid").val(),displayName:$("#wo135feedback_sjjcz").val()}],//原值，在弹窗中回显
			dprtcode:this_.param.dprtcode,
			multi:false,
			callback: function(data){//回调函数
				var user = $.isArray(data)?data[0]:{id:'',displayName:''};
				$("#wo135feedback_sjjczid").val(user.id);
				$("#wo135feedback_sjjcz").val(user.displayName);
				this_.disableUser("wo135feedback_sjjcz");
			}
		});
		AlertUtil.hideModalFooterMessage();
	},
	
	// 禁止输入用户
	disableUser : function(id){
		var user = $("#"+id+"id").val();
		if(user){
			$("#"+id).attr("readonly","readonly").addClass("readonly-style");
		}else{
			$("#"+id).removeAttr("readonly").removeClass("readonly-style");
		}
	},
};