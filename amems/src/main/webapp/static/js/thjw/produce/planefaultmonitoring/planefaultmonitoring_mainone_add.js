Fault_Handling_Record_Add_Modal = {
	id : "Fault_Handling_Record_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		 $("#modalNameFault").html("新增故障处理记录");
		 $("#modalEnameFault").html("Add Troubleshooting Record");
		 AlertUtil.hideModalFooterMessage();    	   		//初始化错误信息 
		 $("#"+this_.id+"").modal("show");
		 $("#hbbtn").removeAttr("disabled");
		 $("input[name='hbh']").removeAttr("disabled");
		 $("#hbrq").removeAttr("disabled");
		 Fault_Handling_Record_Open_Modal.validation(); 	//初始化验证
		 Fault_Handling_Record_Open_Modal.EmptiedData();	//清空数据
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var obj = {};
		
		$('#monitoringForm_bottom').data('bootstrapValidator').validate();
		if (!$('#monitoringForm_bottom').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		//paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		obj.mainid=$("#manidinfo").val();
		obj.fjzch=$("#fjzchid").val();
		obj.fxjldid=$("#fxjldid").val();
		obj.hbh=$("#hbh").val();
		obj.hbrq=$("#hbrq").val();
		obj.zlh=$("#zlh").val();
		obj.pgsl=$("#pgsl").val();
		obj.cljg=$("#cljg").val();
		obj.cxjxx=$("#cxjxx").val();
		obj.zsjxx=$("#zsjxx").val();
		obj.bz=$("#bz").val();
		obj.gdid=$("#zlhid").val();
		obj.dprtcode=$("#dprtId").val();
		obj.whdwid=$("#whdwid").val();
		obj.whrid=$("#gbrid").val();
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit_handing');//获取附件数据
		obj.attachments = attachmentsObj.getAttachments();
		
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
		var idnew=$("#infoId").val();
		var url="";
		if(idnew && idnew!=""){
			url="/produce/fault/monitoring/updateGzcl";	//修改故障处理记录
			obj.id=idnew;
		}else{
			url="/produce/fault/monitoring/addGzcl";	//新增故障处理记录
		}
		
		 startWait($("#Fault_Handling_Record_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Fault_Handling_Record_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Fault_Handling_Record_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Fault_Handling_Record_Modal").modal("hide");
	 			showInfo(1, "desc", "auto"); 			//刷新列表
	 			searchFjgzRecord();
	 		}
	   	 });
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Fault_Handling_Record_Modal").on("hidden.bs.modal", function() {
	$("#monitoringForm_bottom").data('bootstrapValidator').destroy();
	$('#monitoringForm_bottom').data('bootstrapValidator', null);
//	Fault_Handling_Record_Open_Modal.validation();
});

