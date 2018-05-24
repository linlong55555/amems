Defectkeep_Approval_Modal = {
	id : "Defectkeep_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("批准保留单");
		 $("#modalEname").html("Approval Defect Deferred Log");
		 Defectkeep_Open_Modal.EmptiedData();  //清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		
		$(".bldh").attr("disabled",true);		//保留单号不可编辑
		$(".div-hide").show();   			        			//隐藏标识
		Defectkeep_Open_Modal.button(4);						//初始化按钮权限
		Defectkeep_Open_Modal.validation(); 					//初始化验证
		
		$("#hcgj").show();   			        			    //航材工具隐藏显示
		Defectkeep_Open_Modal.Equipment_list_edit(null,true); 	//初始化器材清单
		Defectkeep_Open_Modal.Tools_list_edit(null,true); 		//初始化工具设备
		Defectkeep_Open_Modal.getAttachments(param,true,true);	//加载附件
		this.initDate(param);//加载故障保留数据
	},
	/**
	 * 加载故障保留数据
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
	 				if(data.zt!=2){
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
	 * 加载故障保留数据
	 */
	setDate : function(data){
		var this_=this;
		$("#id").val(data.id);
		Defectkeep_Open_Modal.initFjzchOption(data.dprtcode); 				//初始化飞机注册号
		$("#bs145").val(data.bs145);				//145标识	
		$("#bldh").val(data.bldh);					//缺陷保留单号
		$("#"+this_.id+"_fjzch").val(data.fjzch);	//飞机注册号
		Defectkeep_Open_Modal.onchangeFjzch("#"+this_.id+"_fjzch");  //改变机型
		$("#djzt").val(data.zt);  					//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('failureKeepStatusEnum',data.zt));  //当前单据状态翻译
		
		$("#gdid").val(data.lygdid);							//工单id
		$("#gd").val(data.paramsMap?data.paramsMap.lygdbh:'');	//工单编号
		$("#sqrid").val(data.sqrid);  	//申请人id
		$("#sqrq").val(formatUndefine(data.sqrq).substring(0,10));  	//申请人日期
		$("#sqrq").datepicker("update");
		$("#sqr").val(data.paramsMap.sqr);  			//申请人描述
		$("#qxms").val(data.qxms);						//缺陷描述
		
		approval_process_info.show({    
		 	status:data.zt,			  			  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
			prepared:data.whr.id,	  	  		  //编写人
			preparedDate:data.whsj,	  			  //编写日期
			approvedOpinion:data.pzyj, 		  	  //批准意见
			yxxz:data.yxxz, 		  			  //批准意见
			approvedBy:data.paramsMap.scPzr,      //批准人
			approvedDate:formatUndefine(data.scPzrq).substring(0,10),    		  //批准日期
		});
		
		$(".noteditable").attr("disabled",true);	//标签不可编辑
		$(".required").hide();   			    	//隐藏必填标识*
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
	},
	/**
	 * 批准通过
	 */
	passed : function(){
		var url="/produce/reservation/defect/passed";//批准通过
		var str="批准通过";//审核通过
		this.subjectFrom(str,url);
	},
	/**
	 * 批准驳回
	 */
	turnDown : function(){
		var url="/produce/reservation/defect/turnDown";//批准驳回
		var str="批准驳回";
		this.subjectFrom(str,url);
	},
	/**
	 * 审核通过
	 */
	subjectFrom : function(str,url){
		var obj = {};
		
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
		
		obj.materialToolList = materialToolList; //加载器材清单,工具设备
		
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		var data=approval_process_info.getData();//获取流程信息数据
		obj.id=$("#id").val();
		obj.pzyj=$.trim(data.shenpi); //批准意见
		obj.xfqx=$.trim(data.xfqx);   //修复期限
		obj.yxxz=$.trim(data.yxxz);   //运行限制
		obj.fjzch=$.trim($("#Defectkeep_Open_Modal_fjzch").val()); //飞机
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.bldh=$.trim($("#bldh").val());//保留单编号
		obj.sqrid=$("#sqrid").val();//申请人id
		AlertUtil.showConfirmMessage("确定要"+str+"吗？", {callback: function(){
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
	 			AlertUtil.showMessage(""+str+"成功!");
	 			$("#Defectkeep_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
		}});
	}
};