Defectkeep_Add_Modal = {
	id : "Defectkeep_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this; 
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("新增缺陷保留单");
		 $("#modalEname").html("Add Evaluation");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 Defectkeep_Open_Modal.EmptiedData();   //清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		$(".div-hide").hide();   			        			//隐藏标识
		var bs=deptInfo.deptType;								//取session里的deptType
		if(bs==145){
			$("#bs145").val(1); 								//145为1				
		}
		Defectkeep_Open_Modal.button(1);						//初始化按钮权限
		Defectkeep_Open_Modal.validation(); 					//初始化验证
		Defectkeep_Open_Modal.initFjzchOption(userJgdm); 				//初始化飞机注册号
		
		approval_process_info.show({    
		 	status:1			  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
		});
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#defectkeepForm').data('bootstrapValidator').validate();
		if (!$('#defectkeepForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 	//old状态
		obj.paramsMap = paramsMap;
	
		var bldh=$("#bldh").val();					//故障保留单号
		var fjzch=$("#"+this_.id+"_fjzch").val();	//飞机注册号
		var sqrid=$("#sqrid").val();				//申请人id
		var sqrbmdm=$("#sqrbmdm").val();			//部门id
		var sqrq=$("#sqrq").val();					//申请人日期
		var gdid=$("#gdid").val();					//来源工单id
		var qxms=$("#qxms").val();					//缺陷描述
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
		
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.bs145 = $("#bs145").val(); 		 //145标识
		obj.bldh = bldh;
		obj.fjzch = fjzch;
		obj.sqrbmdm = sqrbmdm;
		obj.sqrid = sqrid;
		obj.sqrq = sqrq;
		obj.lygdid = gdid;
		obj.qxms = qxms;
		obj.attachmentList=attachmentsObj.getAttachments();//加载附件数据
		
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
		var idnew=$("#id").val();
		var url="";
		if(idnew==""){
			url="/produce/reservation/defect/save";//新增故障保留
		}else if(idnew!=""){
			url="/produce/reservation/defect/update";//修改故障保留
			obj.id=idnew;
		}
		startWait($("#Defectkeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Defectkeep_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Defectkeep_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Defectkeep_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	 });
	},
	/**
	 * 提交
	 */
	submit : function(){
		var this_=this;
		var obj=this_.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#id").val();
		var url="";
		if(idnew==""){
			url="/produce/reservation/defect/saveSubmit";//提交新增故障保留
		}else if(idnew!=""){
			url="/produce/reservation/defect/updateSubmit";//提交修改故障保留
			obj.id=idnew;
		}
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		startWait($("#Defectkeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Defectkeep_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Defectkeep_Open_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#Defectkeep_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Defectkeep_Open_Modal").on("hidden.bs.modal", function() {
	$("#defectkeepForm").data('bootstrapValidator').destroy();
	$('#defectkeepForm').data('bootstrapValidator', null);
//	Defectkeep_Open_Modal.validation();
});

