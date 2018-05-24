Project_Add_Modal = {
	id : "Project_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(gdId,gdLx){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("新增项目保留");
		 $("#modalEname").html("Add Work Items Delay");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 Project_Open_Modal.EmptiedData();  	//清空数据
		 this_.initInfo(gdId,gdLx);						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(gdId,gdLx){
		var this_=this;
		$(".div-hide").hide();   			        		//隐藏标识
		Project_Open_Modal.validation(); 					//初始化验证
		Project_Open_Modal.bindEvent(); 					//初始化格式验证
		Project_Open_Modal.initFjzchOption(userJgdm); 		//初始化飞机注册号
		Project_Open_Modal.Equipment_list_edit(null,true); 	//初始化器材清单
		Project_Open_Modal.Tools_list_edit(null,true); 		//初始化工具设备
		
		if(gdId){
			this_.setGdid(gdId,gdLx);//当工单id不为空时，加载工单数据
		}
		
	},
	/**
	 * 加载工单信息
	 */
	setGdid : function(gdId,gdLx){
		var this_=this;
		var obj = {};
		var paramsMap={};
		obj.id = gdId;
		paramsMap.gdLx = gdLx;
		obj.paramsMap = paramsMap;
		//根据单据id查询信息
		startWait($("#Project_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/produce/reservation/item/getBygdId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Project_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Project_Open_Modal"));
	 			if(data!=null){
	 				$("#gdid").val(gdId);									//工单id
	 				$("#"+this_.id+"_fjzch").val(data.fjzch);				//飞机注册号
	 				$("#gd").val(data.paramsMap?data.paramsMap.gdbh:'');	//工单编号
	 				$("#jhJsrq").val(formatUndefine(data.paramsMap?data.paramsMap.jhJsrq:'').substring(0,10));//计划完成日期
	 				$("#jhJsrq").datepicker("update");
	 				$("#gkbh").val(data.paramsMap?data.paramsMap.gkbh:'');	//工卡编号
	 				$("#gkbz").val(data.paramsMap?data.paramsMap.gkbz:'');	//间隔
	 				$("#sqrid").val(userId);  				//申请人id
	 				$("#sqrbmid").val(currentUser.department?currentUser.department.id:'');  			//申请人id
	 				$("#sqrbm").val(currentUser.department?currentUser.department.dprtname:'');					//部门描述
	 				$("#sqr").val(displayName);  			//申请人描述
	 				TimeUtil.getCurrentDate("#sqrq");       //取得当前时间
	 				Project_Open_Modal.Equipment_list_edit1(gdId,true,21); 	//初始化器材清单
	 				Project_Open_Modal.Tools_list_edit1(gdId,true,21); 		//初始化工具设备
	 				
	 				if(gdLx==145){
	 					$("#bs145").val(1); 								//145为1				
	 				}
	 				$("#addupdatebs").val("0");   			        		//新增标识为0为修改
	 				
				};
	 		}
		});
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#projectForm').data('bootstrapValidator').validate();
		if (!$('#projectForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 	//old状态
		obj.paramsMap = paramsMap;
	
	
		var bldh=$("#bldh").val();					//故障保留单号
		var fjzch=$("#"+this_.id+"_fjzch").val();	//飞机注册号
		var sqrid=$("#sqrid").val();				//申请人id
		var sqrbmid=$("#sqrbmid").val();			//部门id
		var sqrq=$("#sqrq").val();					//申请人日期
		var gdid=$("#gdid").val();					//工单id
		var fjbs1 = $("#"+this_.id+" input:radio[name='fjbs1']:checked").val(); 	//附加1标识	
		var fjbs2 = $("#"+this_.id+" input:radio[name='fjbs2']:checked").val(); 	//附加2标识
		var fjbs3 = $("#"+this_.id+" input:radio[name='fjbs3']:checked").val(); 	//附加3标识
		var dxgxtyq=$("#dxgxtyq").val();			//MEL/CDL 对相关系统要求
		var blqjcqcs=$("#blqjcqcs").val();			//保留期间采取措施
		var fxsj=TimeUtil.convertToMinute($("#fxsj").val(), TimeUtil.Separator.COLON);//飞行时间：小时转分钟
		var fxxh=$("#fxxh").val();					//飞行循环
		var fjbs4 = $("#"+this_.id+" input:radio[name='fjbs4']:checked").val(); 	//附加4标识
		var fjbs5 = $("#"+this_.id+" input:radio[name='fjbs5']:checked").val(); 	//附加5标识
		var fjbs6 = $("#"+this_.id+" input:radio[name='fjbs6']:checked").val(); 	//附加6标识
		var blqx=$("#blqx").val();					//保留期限
		var gzblxz=$("#gzblxz").val();				//规章保留限制
		var jhwcdd=$("#jhwcdd").val();				//计划完成地点
		var pgr=$("#pgr").val();					//生产计划评估人
		var pgrq=$("#pgrq").val();					//生产计划评估日期
		var fjbs7 = $("#"+this_.id+" input:radio[name='fjbs7']:checked").val(); 	//附加7标识
		var fjbs8 = $("#"+this_.id+" input:radio[name='fjbs8']:checked").val(); 	//附加8标识
		var fjbs9 = $("#"+this_.id+" input:radio[name='fjbs9']:checked").val(); 	//附加9标识
		var jspgr=$("#jspgr").val();					//技术评估人
		var jspgrq=$("#jspgrq").val();					//技术评估日期
		var shr=$("#shr").val();						//审核人
		var shrq=$("#shrq").val();						//审核日期
		var shyj=$("#shyj").val();						//审核意见
		var pzr=$("#pzr").val();						//批准人
		var pzrq=$("#pzrq").val();						//批准日期
		var pzyj=$("#pzyj").val();						//批准意见
		
		 var oDate1 = new Date(sqrq);					//申请日期
	 	 var oDate2 = new Date(pgrq); 					//生产计划评估日期
	 	 var oDate3 = new Date(jspgrq); 				//技术评估日期
	 	 var oDate4 = new Date(shrq); 					//审核日期
	 	 var oDate5 = new Date(pzrq); 					//批准日期
 		 if(oDate1!=''&&oDate2!=''&&oDate1.getTime()>oDate2.getTime()){
 			AlertUtil.showModalFooterMessage('生产计划评估日期必须大于等于申请日期!');
 			$("#pgrq").focus();
			$("#pgrq").addClass("border-color-red");
			return false;
 	     }
 		 if(oDate1!=''&&oDate3!=''&&oDate1.getTime()>oDate3.getTime()){
 			 AlertUtil.showModalFooterMessage('技术评估日期必须大于等于申请日期!');
 			$("#jspgrq").focus();
			$("#jspgrq").addClass("border-color-red");
 			 return false;
 		 }
 		 if(oDate4!=''&&oDate2!=''&&oDate2.getTime()>oDate4.getTime()){
 			 AlertUtil.showModalFooterMessage('审核日期必须大于等于生产计划评估日期!');
 			$("#shrq").focus();
			$("#shrq").addClass("border-color-red");
 			 return false;
 		 }
 		 if(oDate4!=''&&oDate3!=''&&oDate3.getTime()>oDate4.getTime()){
 			 AlertUtil.showModalFooterMessage('审核日期必须大于等于技术评估日期!');
 			$("#shrq").focus();
			$("#shrq").addClass("border-color-red");
 			 return false;
 		 }
 		 if(oDate4!=''&&oDate5!=''&&oDate4.getTime()>oDate5.getTime()){
 			 AlertUtil.showModalFooterMessage('批准日期必须大于等于审核日期!');
 			$("#pzrq").focus();
			$("#pzrq").addClass("border-color-red");
 			 return false;
 		 }
 		//$("#pzrq").removeClass("border-color-red");
 		 
		var materialToolList = Equipment_list_edit.getData(); //获取器材清单数据
		var toolArr = Tools_list_edit.getData(); 			  //获取工具设备数据
		/**
		 * 加载器材清单,工具设备
		 */
		$.each(toolArr,function(index,row){
			var infos = {};
			infos.id = row.id;
			infos.bjid = row.bjid;
			infos.jh = row.jh;
			infos.bjlx = row.bjlx;
			infos.sl = row.sl;
			infos.bz = row.bz;
			infos.hhxx = row.hhxx;
			infos.xc = row.xc;
			materialToolList.push(infos);
		});
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
		
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.bs145 = $("#bs145").val(); 		 //145标识
		obj.bldh = bldh;
		obj.fjzch = fjzch;
		obj.sqrbmid = sqrbmid;
		obj.sqrid = sqrid;
		obj.sqrq = sqrq;
		obj.gdid = gdid;
		obj.fjbs1 = fjbs1;
		obj.fjbs2 = fjbs2;
		obj.fjbs3 = fjbs3;
		obj.dxgxtyq = dxgxtyq;
		obj.blqjcqcs = blqjcqcs;
		obj.fxsj = fxsj;
		obj.fxxh = fxxh;
		obj.fjbs4 = fjbs4;
		obj.fjbs5 = fjbs5;
		obj.fjbs6 = fjbs6;
		obj.blqx = blqx;
		obj.gzblxz = gzblxz;
		obj.jhwcdd = jhwcdd;
		obj.pgr = pgr;
		obj.pgrq = pgrq;
		obj.fjbs7 = fjbs7;
		obj.fjbs8 = fjbs8;
		obj.fjbs9 = fjbs9;
		obj.jspgr = jspgr;
		obj.jspgrq = jspgrq;
		obj.shr = shr;
		obj.shrq = shrq;
		obj.shyj = shyj;
		obj.pzr = pzr;
		obj.pzrq = pzrq;
		obj.pzyj = pzyj;
		
		obj.materialToolList = materialToolList; //加载器材清单,工具设备
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
			url="/produce/reservation/item/save";//新增
		}else if(idnew!=""){
			url="/produce/reservation/item/update";//修改
			obj.id=idnew;
		}
		startWait($("#Project_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Project_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Project_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Project_Open_Modal").modal("hide");
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
			url="/produce/reservation/item/saveSubmit";//提交新增
		}else if(idnew!=""){
			url="/produce/reservation/item/updateSubmit";//提交修改
			obj.id=idnew;
		}
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		startWait($("#Project_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Project_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Project_Open_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#Project_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Project_Open_Modal").on("hidden.bs.modal", function() {
	$("#projectForm").data('bootstrapValidator').destroy();
	$('#projectForm').data('bootstrapValidator', null);
//	Project_Open_Modal.validation();
});

