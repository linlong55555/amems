annual_plan_alert_Add = {
	id : "annual_plan_alert_Modal",
	module : "annual_plan_module",
	init : "annual_plan",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		if(annual_plan.zt == 3 && annual_plan.bbh == annual_plan.maxbbh){
			var message = "因为该年度计划已经通过审批，所以您的此次操作系统会自动对该年度计划进行升级版本，升级版本后不会对您之前的数据造成影响，您是否继续？";
			AlertUtil.showConfirmMessage(message, {callback: function(){
				
				this_.initOpen();
				
			}});
		}else{
			this_.initOpen();
		}
		 
	},
	initOpen : function(){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName1").html("新增年度审核计划");
		 $("#modalEname1").html("New Annual Audit Plan");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 this_.validation(); 					//初始化验证
		 this_.EmptiedData();  					//清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 字段验证
	 */
	validation : function(){
		var this_=this;
		validatorForm = $('#'+this_.id+"_from").bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	   annual_plan_alert_Modal_yf: {
			            validators: {
			            	notEmpty: {message: '月份不能为空!'}
			            }
			      }/*,
			      annual_plan_module_shdx: {
			    	  validators: {
			    		  notEmpty: {message: '审核对象不能为空!'}
			    	  }
			      },*/
		       }
		   });
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		var this_=this;
		$("#"+this_.id+"_yf").val("1");
		$("#"+this.id+" input[name='"+this_.id+"_lx']").get(0).checked=true; //类型默认为内部
		$("#"+this_.id+"_bz").val("");
		$("#"+this_.id+"_id").val("");
		
		$("#"+this_.module+"_shdxid").val("");
		$("#"+this_.module+"_shdxbh").val("");
		$("#"+this_.module+"_shdxmc").val("");
		$("#"+this_.module+"_shdxid").val("");
		$("#"+this_.module+"_shdx").val("");
		$("#"+this_.module+"_shzrrid").val("");
		$("#"+this_.module+"_shzrr").val("");
		annual_plan_module.changeType();
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		var this_=this;
		$("#"+this_.id+"_nd").val($("#year_search").val());
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_plan_edit');
		attachmentsObj.show({
			djid:"",
			fileHead : true,
			colOptionhead : true,
			fileType:"plan"
		});//显示附件
		
		$("#left_column", $("#attachments_list_plan_edit")).removeClass("col-lg-1").addClass("col-lg-2");
		$("#right_column", $("#attachments_list_plan_edit")).removeClass("col-lg-11").addClass("col-lg-10");
	},
	/**
	 * 获取责任审核人数据
	 */
	getAuditMembersList : function(){
		var this_=this;
		var shzrrid=$("#"+this_.module+"_shzrrid").val();	
		var auditMembersList=[];
		var dataList=[];
		dataList = shzrrid.split(",");// 在每个逗号(,)处进行分解。
		for (var i = 0; i < dataList.length; i++) {
			var auditMembers={};
			auditMembers.cyid=dataList[i];
			auditMembersList.push(auditMembers);
		}
		return auditMembersList;
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		
		$('#'+this_.id+"_from").data('bootstrapValidator').validate();
		if (!$('#'+this_.id+"_from").data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}

		var ndjhid=$("#id").val();								//年度计划id
		var bbh=$("#bbh").val();								//版本号
		var nf=$("#"+this_.id+"_nd").val();						//年份
		var yf= $.trim($("#"+this_.id+"_yf").val());			//月份
		var lx = $("#"+this_.id+" input:radio[name='"+this_.id+"_lx']:checked").val(); 	//类型		
		var shdxid= $.trim($("#"+this_.module+"_shdxid").val());	//审核对象id
		var shdxbh= $.trim($("#"+this_.module+"_shdxbh").val());	//审核对象编号
		var shdxmc= $.trim($("#"+this_.module+"_shdxmc").val());	//审核对象名称
		if(lx == 2){
			shdxmc= $.trim($("#"+this_.module+"_shdx").val());
		}
		
		if(shdxmc == null || shdxmc == ''){
			AlertUtil.showModalFooterMessage("审核对象不能为空!");
			return false;
		}
		
		var bz= $.trim($("#"+this_.id+"_bz").val());			//备注
		
		obj.auditMembersList = this.getAuditMembersList();	//获取责任审核人数据
		
		obj.nf=nf;
		obj.bbh=bbh;
		obj.ndjhid=ndjhid;
		obj.yf=yf;
		obj.lx=lx;
		obj.shdxid=shdxid;
		obj.shdxbh=shdxbh;
		obj.shdxmc=shdxmc;
		obj.bz=bz;
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_plan_edit');
		obj.attachments = attachmentsObj.getAttachments();
		
	return obj;
	},
	/**
	 * 保存
	 */
	save : function(){
		var this_=this;
		var obj=this.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#"+this_.id+"_id").val();
		console.info(idnew);
		var url="";
		if(idnew==""){
			url="/quality/annualplan/audit/save";//新增
		}else if(idnew!=""){
			url="/quality/annualplan/audit/update";//修改
			obj.id=idnew;
		}
		startWait($("#annual_plan_alert_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#annual_plan_alert_Modal"),
	 		success:function(data){
	 			finishWait($("#annual_plan_alert_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#annual_plan_alert_Modal").modal("hide");
	 			annual_plan.init();
	 		}
	   	 });
	}
};

/**
 * 弹出窗验证销毁
 */
$("#annual_plan_alert_Modal").on("hidden.bs.modal", function() {
	$("#annual_plan_alert_Modal_from").data('bootstrapValidator').destroy();
	$('#annual_plan_alert_Modal_from').data('bootstrapValidator', null);
});

