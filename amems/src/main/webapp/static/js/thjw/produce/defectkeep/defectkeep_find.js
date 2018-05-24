Defectkeep_Find_Modal = {
	id : "Defectkeep_Open_Modal",
	open: function(param){
		var this_ = this;
		Navigation(menuCode, '', '', 'SC12-2 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		Defectkeep_Open_Modal.EmptiedData();  //清空数据
		this_.initInfo($("#viewid").val());//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".bldh").attr("disabled",true);							//保留单号不可编辑
		$(".div-hide").show();   			        				//隐藏标识
		Defectkeep_Open_Modal.validation(); 						//初始化验证
		$("#hcgj").show();   			        			    	//航材工具隐藏显示
		Defectkeep_Open_Modal.Equipment_list_edit(null,true); 		//初始化器材清单
		Defectkeep_Open_Modal.Tools_list_edit(null,true); 			//初始化工具设备
		this.initDate(param);										//加载故障保留数据
	},
	/**
	 * 加载故障保留数据
	 */
	initDate : function(param){
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
		$("#dprtcode").val(data.dprtcode);
		Defectkeep_Open_Modal.initFjzchOption(data.dprtcode); 	//初始化飞机注册号
		$("#bs145").val(data.bs145);				//145标识	
		$("#bldh").val(data.bldh);					//缺陷保留单号
		$("#"+this_.id+"_fjzch").val(data.fjzch);	//飞机注册号
		Defectkeep_Open_Modal.onchangeFindFjzch("#"+this_.id+"_fjzch");  //改变机型
		$("#djzt").val(data.zt);  					//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('failureKeepStatusEnum',data.zt));  //当前单据状态翻译
		
		$("#gdid").val(data.lygdid);								//工单id
		$("#gd").val(data.paramsMap?data.paramsMap.lygdbh:'');	//工单编号
		$("#sqrid").val(data.sqrid);  	//申请人id
		$("#sqrq").val(formatUndefine(data.sqrq).substring(0,10));  	//申请人日期
		$("#sqrq").datepicker("update");
		$("#sqr").val(data.paramsMap.sqr);  			//申请人描述
		$("#qxms").val(data.qxms);						//缺陷描述
		
		$("#wanchengxinxi_Model").hide();						
		$("#zhidingjieshu_Model").hide();	
		if(data.zt==10){
			
			$("#wanchengxinxi_Model").show();						
			$("#gbyy").val(data.gbyy);						//关闭原因
			$("#gzz").val(data.gzz);						//工作者
			$("#gzrq").val(formatUndefine(data.gzrq).substring(0,10));						//工作者日期
			$("#gbrg").val(data.paramsMap?data.paramsMap.gbr:'');						//指定结束人
			$("#gbsjg").val(data.gbsj);						//指定结束时间
		}else if(data.zt==9){
			$("#zhidingjieshu_Model").show();
			$("#gbyyy").val(data.gbyy);						//关闭原因
			$("#gbr").val(data.paramsMap?data.paramsMap.gbr:'');						//指定结束人
			$("#gbsj").val(data.gbsj);						//指定结束时间
		}
		
		approval_process_info.show({    
		 	status:5,			  				  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
			prepared:data.whr.id,	  	  		  //编写人
			preparedDate:data.whsj,	  			  //编写日期
			approvedOpinion:data.pzyj, 		  	  //批准意见
			yxxz:data.yxxz, 		  			  //批准意见
			approvedBy:data.paramsMap.scPzr,      //批准人
			approvedDate:formatUndefine(data.xfqx).substring(0,10),    		  //批准日期
		});
		
		Defectkeep_Open_Modal.Equipment_list_edit(data.id,false);	//加载器材清单
		Defectkeep_Open_Modal.Tools_list_edit(data.id,false);	 	//加载工具设备
		Defectkeep_Open_Modal.getAttachments(data.id,false,false);	//加载附件
		
		$(".noteditable").attr("disabled",true);	//标签不可编辑
		$(".required").hide();   			    	//隐藏必填标识*
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
	}
};
/**
 *初始化当前js
 */
$(function(){
	Defectkeep_Find_Modal.open();
	Navigation(menuCode, '查看缺陷保留', 'Select View', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
});