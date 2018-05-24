Project_Find_Modal = {
	id : "Project_Open_Modal",
	open: function(param){
		var this_ = this;
		Navigation(menuCode, '', '', 'SC12-2 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		 Project_Open_Modal.EmptiedData();  //清空数据
		 this_.initInfo($("#viewid").val());//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".bldh").attr("disabled",true);						//保留单号不可编辑
		$(".icon-bldh").hide();   			        			//隐藏保留单号星
		$(".div-hide").show();   			        			//隐藏标识
		this.initDate(param);//加载故障保留数据
	},
	/**
	 * 加载故障保留数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#Project_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/produce/reservation/item/getByProjectKeeId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Project_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Project_Open_Modal"));
	 			if(data!=null){
	 				this_.setDate(data);
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
		Project_Open_Modal.initFjzchOption(data.dprtcode); 					//初始化飞机注册号
		$("#bs145").val(data.bs145);				//145标识	
		$("#bldh").val(data.bldh);					//故障保留单号
		$("#"+this_.id+"_fjzch").val(data.fjzch);	//飞机注册号
		Project_Open_Modal.onchangeFjzch("#"+this_.id+"_fjzch");  //改变机型
		$("#djzt").val(data.zt);  					//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('failureKeepStatusEnum',data.zt));  //当前单据状态翻译
		
		$("#sqrid").val(data.sqrid);				//申请人
		$("#sqr").val(data.paramsMap?data.paramsMap.sqr:'');					//申请人描述
		$("#sqrbm").val(data.paramsMap?data.paramsMap.sqrbm:'');					//申请部门描述
		$("#sqrbmid").val(data.sqrbmid);			//部门id
		$("#sqrq").val(formatUndefine(data.sqrq).substring(0,10));						//申请人日期
		$("#sqrq").datepicker("update");
		$("#gdid").val(data.gdid);								//工单id
		$("#gd").val(data.paramsMap?data.paramsMap.gdbh:'');		//工单编号
		$("#jhJsrq").val(formatUndefine(data.paramsMap?data.paramsMap.jhJsrq:'').substring(0,10));//计划完成日期
		$("#jhJsrq").datepicker("update");
		$("#gkbh").val(data.paramsMap?data.paramsMap.gkbh:'');	//工卡编号
		$("#gkbz").val(data.paramsMap?data.paramsMap.gkbz:'');	//间隔
		
		$("input:radio[name='fjbs1'][value='"+data.fjbs1+"']").attr("checked",true); //附加1标识		
		$("input:radio[name='fjbs2'][value='"+data.fjbs2+"']").attr("checked",true); //附加2标识	
		$("input:radio[name='fjbs3'][value='"+data.fjbs3+"']").attr("checked",true); //附加3标识	
		
		$("#dxgxtyq").val(data.dxgxtyq);			//MEL/CDL 对相关系统要求
		$("#blqjcqcs").val(data.blqjcqcs);			//保留期间采取措施
		if(data.fxsj==0){
			$("#fxsj").val("");
		}else{
			$("#fxsj").val(TimeUtil.convertToHour(data.fxsj, TimeUtil.Separator.COLON));//飞行时间：分钟转小时
		}
		$("#fxxh").val(data.fxxh);					//飞行循环
		$("input:radio[name='fjbs4'][value='"+data.fjbs4+"']").attr("checked",true); //附加4标识		
		$("input:radio[name='fjbs5'][value='"+data.fjbs5+"']").attr("checked",true); //附加5标识	
		$("input:radio[name='fjbs6'][value='"+data.fjbs6+"']").attr("checked",true); //附加6标识
		$("#blqx").val(formatUndefine(data.blqx).substring(0,10));					//保留期限
		$("#blqx").datepicker("update");
		$("#gzblxz").val(data.gzblxz);				//规章保留限制
		$("#jhwcdd").val(data.jhwcdd);				//计划完成地点
		$("#pgr").val(data.pgr);					//生产计划评估人
		$("#pgrq").val(formatUndefine(data.pgrq).substring(0,10));					//生产计划评估日期
		$("#pgrq").datepicker("update");
		$("input:radio[name='fjbs7'][value='"+data.fjbs7+"']").attr("checked",true); //附加7标识		
		$("input:radio[name='fjbs8'][value='"+data.fjbs8+"']").attr("checked",true); //附加8标识	
		$("input:radio[name='fjbs9'][value='"+data.fjbs9+"']").attr("checked",true); //附加9标识
		$("#jspgr").val(data.jspgr);					//技术评估人
		$("#jspgrq").val(formatUndefine(data.jspgrq).substring(0,10));					//技术评估日期
		$("#jspgrq").datepicker("update");
		$("#shr").val(data.shr);						//审核人
		$("#shrq").val(formatUndefine(data.shrq).substring(0,10));						//审核日期
		$("#shrq").datepicker("update");
		$("#shyj").val(data.shyj);						//审核意见
		$("#pzr").val(data.pzr);						//批准人
		$("#pzrq").val(formatUndefine(data.pzrq).substring(0,10));						//批准日期
		$("#pzrq").datepicker("update");
		$("#pzyj").val(data.pzyj);						//批准意见
		
		Project_Open_Modal.Equipment_list_edit(data.id,false);	//加载器材清单
		Project_Open_Modal.Tools_list_edit(data.id,false);	 	//加载工具设备
		Project_Open_Modal.getAttachments(data.id,false,false);	//加载附件
		
		$(".noteditable").attr("disabled",true);	//标签不可编辑
		$(".required").hide();   			    	//隐藏必填标识*
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
	}
};
/**
 *初始化当前js
 */
$(function(){
	Project_Find_Modal.open();
});