Annualplan_Edit_Modal = {
	id : "Annualplan_Edit_Modal",
	init : "annual_plan",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("维护附件");
		 $("#modalEname").html("Maintain Attachments");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 this_.EmptiedData();  					//清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		var this_=this;
		$("#"+this_.id+"_jhsm").val($("#ndjhsm").text());
		annual_plan_module.getAttachments($("#id").val(),true,true);	 				  //初始化附件
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		var this_=this;
		$("#"+this_.id+"_nd").val($("#year_search").val());
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};

		//var paramsMap = {};
		//obj.paramsMap = paramsMap;
	
		var nf=$("#"+this_.id+"_nd").val();						//年份
		var ndjhsm= $.trim($("#"+this_.id+"_jhsm").val());		//年度计划说明
		var bbh=$("#bbh").val();								//版本号
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
		
		if(bbh==''){
			bbh=1;
		}
		
		obj.nf=nf;
		obj.ndjhsm=ndjhsm;
		obj.bbh=bbh;
		obj.attachmentList=attachmentsObj.getAttachments();//加载附件数据
	return obj;
	},
	/**
	 * 提交
	 */
	submit : function(){
		var this_=this;
		var obj = {};
		var idnew=$("#id").val();
		var url="";
		if(idnew == ""){
			AlertUtil.showMessage('请先新增年度计划!');
			return false;
		}
		url="/quality/annualplan/plan/doSubmit";//提交
		obj.id=idnew;
		startWait($("#Annualplan_Edit_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Annualplan_Edit_Modal"),
	 		success:function(data){
	 			//id = data;	
	 			finishWait($("#Annualplan_Edit_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			annual_plan.init();
	 		}
	   	 });
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
		var idnew=$("#id").val();
		console.info(idnew);
		var url="";
		if(idnew==""){
			url="/quality/annualplan/plan/save";//新增
		}else if(idnew!=""){
			url="/quality/annualplan/plan/update";//修改
			obj.id=idnew;
		}
		startWait($("#Annualplan_Edit_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Annualplan_Edit_Modal"),
	 		success:function(data){
	 			//id = data;	
	 			finishWait($("#Annualplan_Edit_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Annualplan_Edit_Modal").modal("hide");
	 			annual_plan.init();
	 		}
	   	 });
	}
};

/**
 * 弹出窗验证销毁
 */
$("#FailureKeep_Open_Modal").on("hidden.bs.modal", function() {
	$("#failurekeepForm").data('bootstrapValidator').destroy();
	$('#failurekeepForm').data('bootstrapValidator', null);
//	FailureKeep_Open_Modal.validation();
});

