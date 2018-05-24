toolcheck_update_modal = {
	id : "toolcheck_open_modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("修改计量工具");
		 $("#modalEname").html("Update Defect Deferred Log");
		 toolcheck_open_modal.EmptiedData();  				//清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		var this_ = this;
		$(".bldh").attr("disabled",true);						//保留单号不可编辑
		$(".div-hide").show();   			        			//隐藏标识
		$(".button").hide();   			        				//隐藏按钮
		toolcheck_open_modal.validation(); 									//初始化验证
		toolcheck_open_modal.initDic(); 		//初始化计量方式
		$("#zjldj").val($("#jldj").val());		//初始化计量等级
		toolcheck_open_modal.getAttachments(param,true,true);				//加载附件
		this_.initDate(param);//加载数据
	},
	/**
	 * 加载数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#toolcheck_open_modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/toolcheck/getByDefectKeepId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#toolcheck_open_modal"),
	 		success:function(data){
	 			finishWait($("#toolcheck_open_modal"));
	 			if(data!=null){
 			   		this_.setDate(data);// 加载数据
 			   		$("#"+this_.id+"").modal("show");//显示窗口
				};
	 		}
		});
	},
	/**
	 * 加载数据
	 */
	setDate : function(data){
		
		$("#id").val(data.id);
		$("#bjxlh").val(data.mes.bjxlh);		//部件名称
		$("#bjh").val(data.mes.bjid);			//工具编号
		$("#bjmc").val(formatUndefine(data.paramsMap.zywms)+" "+formatUndefine(data.paramsMap.zzwms));			//部件号
		$("#whr").val(data.whr?data.whr.id:'');		//维护人
		if(StringUtil.escapeStr(data.paramsMap.ckh)==''){
			$("#wz").val("不在库");
		}else{
			$("#wz").val(StringUtil.escapeStr(data.paramsMap.ckh)+" "+StringUtil.escapeStr(data.paramsMap.ckmc)+" "+StringUtil.escapeStr(data.paramsMap.kwh));		//位置
		}
		$("#rksj").val(formatUndefine(data.paramsMap.rksj).substring(0,10));		//入库日期
		$("#pch").val(data.paramsMap.pch);		//批次号
		if(formatUndefine(data.paramsMap.kcsl)!=''){
			$("#kcsl").val(formatUndefine(data.paramsMap.kcsl)+" "+formatUndefine(data.paramsMap.jldw));		//数量
		}
		
		$("#zbjxlh").val(data.bjxlh);			//编号
		$("#zzwms").val(data.zwms);				//中文描述
		$("#zywms").val(data.ywms);				//英文描述
		$("#zxh").val(data.xh);					//型号
		$("#zgg").val(data.gg);					//规格
		$("#zjyZq").val(data.jyZq);				//周期
		$("#zjyZqdw").val(data.jyZqdw);			//周期单位
		$("#zjyScrq").val(formatUndefine(data.jyScrq).substring(0,10));			//上次校验日期
		$("#zjyScrq").datepicker("update");
		$("#zjyXcrq").val(formatUndefine(data.jyXcrq).substring(0,10));			//下次校验日期
		$("#zjyXcrq").datepicker("update");
		$("#jlfsSelect").val(data.jlfs);				//计量方式
		$("#zjldj").val(data.jldj);				//计量等级
		$("#zbz").val(data.bz);					//备注
		$("#djzt").val(data.zt);
		$(".required").hide();   			    //隐藏必填标识
		$(".noteditable").attr("disabled",true);//标签不可编辑
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
	}
};