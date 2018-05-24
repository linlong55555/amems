Planefaultmonitoring_Add_Modal = {
	id : "Planefaultmonitoring_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		 var this_ = this;
		 $("#Planefaultmonitoring_Open_Modal").find("#modalName").html(" 新增飞机故障监控");
		 $("#Planefaultmonitoring_Open_Modal").find("#modalEname").html("Add AircraftFault");
		 
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		 $("#"+this_.id).modal("show");
		 Planefaultmonitoring_Open_Modal.validation(); 	//初始化验证
		 Planefaultmonitoring_Open_Modal.EmptiedData();	//清空数据
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var obj = {};
		$('#monitoringForm').data('bootstrapValidator').validate();
		if (!$('#monitoringForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		obj.paramsMap = paramsMap;
		obj.dprtcode = $("#dprtcode").val();
		obj.fjzch=$("#"+this.id+"_fjzch").val();
		obj.gzxx=$("#gzxx").val();
		obj.bz=$("#mbz").val();
		obj.zjh=$("#common_zjh_value").val();
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list');//获取附件数据
		obj.attachments=attachmentsObj.getAttachments();
	return obj;
	},
	/**
	 * 保存
	 */
	save : function(){
		var obj=this.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#djid").val();
		var url="";
		if(idnew==""){
			url="/produce/fault/monitoring/addPalneFaultMonitoring";		//新增飞机故障监控
		}else{
			url="/produce/fault/monitoring/updatePalneFaultMonitoring";	//修改飞机故障监控
			obj.id=idnew;
		}
		 startWait($("#Planefaultmonitoring_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Planefaultmonitoring_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Planefaultmonitoring_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Planefaultmonitoring_Open_Modal").modal("hide");
	 			hideBottom();
	 			decodePageParam();
	 		}
   	  });
	}

};

/**
 * 弹出窗验证销毁
 */
$("#Planefaultmonitoring_Open_Modal").on("hidden.bs.modal", function() {
	$("#monitoringForm").data('bootstrapValidator').destroy();
	$('#monitoringForm').data('bootstrapValidator', null);
//	Planefaultmonitoring_Open_Modal.validation();
});

