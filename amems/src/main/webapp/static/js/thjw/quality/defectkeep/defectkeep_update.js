Defectkeep_Update_Modal = {
	id : "Defectkeep_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("修改缺陷保留单");
		 $("#modalEname").html("Update Defect Deferred Log");
		 Defectkeep_Open_Modal.EmptiedData();  //清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".bldh").attr("disabled",true);//保留单号不可编辑
		$(".div-hide").show();   			        			//隐藏标识
		$("#addupdatebs").val("0");   			        		//新增标识为0为修改
		Defectkeep_Open_Modal.button(1);						//初始化按钮权限
		Defectkeep_Open_Modal.validation(); 					//初始化验证
		
		Defectkeep_Open_Modal.getAttachments(null,true,true);  //加载附件
		this.initDate(param);//加载缺陷保留数据
	},
	/**
	 * 加载缺陷保留数据
	 */
	initDate : function(param){
		var falg=true;
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#Defectkeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/produce/reservation/defect/getByDefectKeepId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Defectkeep_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Defectkeep_Open_Modal"));
	 			if(data!=null){
	 				if(data.zt==2){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					falg=false;
	 					return false;
	 				}
	 			  	if(falg){
	 			   		$("#"+this_.id+"").modal("show");//显示窗口
	 			   	}
	 				this_.setDate(data);// 加载缺陷保留数据
				};
	 		}
		});
	},
	/**
	 * 加载缺陷保留数据
	 */
	setDate : function(data){
		
		var this_=this;
		$("#id").val(data.id);
		$("#bs145").val(data.bs145);				//145标识	
		$("#bldh").val(data.bldh);					//缺陷保留单号
		Defectkeep_Open_Modal.initFjzchOption(data.dprtcode); 				//初始化飞机注册号
		$("#"+this_.id+"_fjzch").val(data.fjzch);	//飞机注册号
		Defectkeep_Open_Modal.onchangeFjzch("#"+this_.id+"_fjzch");  //改变机型
		$("#djzt").val(data.zt);  					//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('failureKeepStatusEnum',data.zt));  //当前单据状态翻译
		
		$("#gdid").val(data.lygdid);								//工单id
		$("#gd").val(data.paramsMap?data.paramsMap.lygdbh:'');	//工单编号
		$("#sqrid").val(data.sqrid);  	//申请人id
		$("#sqrq").val(formatUndefine(data.sqrq).substring(0,10));  	//申请人日期
		$("#sqrq").datepicker("update");
		$("#sqr").val(data.paramsMap.sqr);  			//申请人描述
		$("#qxms").val(data.qxms);						//缺陷描述
		
		approval_process_info.show({    
		 	status:data.zt,			  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
		 	prepared:data.whr.id,	  	  		  //编写人
			preparedDate:data.whsj,	  			  //编写日期
			approvedOpinion:data.pzyj, 		  	  //批准意见
			yxxz:data.yxxz, 		  			  //运行限制
			approvedBy:data.paramsMap.scPzr,      //批准人
			approvedDate:formatUndefine(data.xfqx).substring(0,10),    		  //批准日期
		});
	}
};