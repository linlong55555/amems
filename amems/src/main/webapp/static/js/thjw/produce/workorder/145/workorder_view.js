var viewObj = null; //对应的数据
var woJx = "";
var woZoneOption = "";
var workHourOptions = [];

$(function() {
	//查询数据
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/produce/workorder145/selectWOById",
		type:"post",
		data:{gdid:$("#gdid").val()},
		dataType:"json",
		success:function(data){
			viewObj = data;
		}
	});
	initWODicData();
	//显示数据
	showWODatas();
	//初始化组件
	initEOZujian();
});

/**初始化数据字典*/
function initWODicData(){
	var this_ = this;
	//工作类别
	$("#wo145add_gzlb").empty();
	DicAndEnumUtil.registerDic('17','wo145add_gzlb',this_.viewObj.dprtcode);
	
	//专业
	$("#wo145add_zy", $("#"+this_.id)).empty();
	DicAndEnumUtil.registerDic('4','wo145add_zy',this_.viewObj.dprtcode);
	
	//清空飞机站位/接近
	this_.clearWOFjzwAndJJ();
	
	//工序组
	this_.initWorkGroup();
}

/**初始化主工作组*/
function initWorkGroup(){
	var this_ = this;
	this_.workHourOptions = [];
	var obj={};
	obj.dprtcode = this_.viewObj.dprtcode;
	AjaxUtil.ajax({
		   url:basePath+"/sys/workgroup/loadWorkGroup",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   async: false,
		   data:JSON.stringify(obj),
		   success:function(data){
			   $("#wo145add_mainWorkcenter").empty();
			   if(data != null && data.wgList != null && data.wgList.length > 0){
				   var option = '';
				   $.each(data.wgList,function(i,list){
					   option += "<option value='"+list.id+"'>"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
					   this_.workHourOptions.push(list);
			 	   }); 
				   $("#wo145add_mainWorkcenter").html(option);
			   }
	       },
	 }); 
}

/**清空飞机站位/接近*/
function clearWOFjzwAndJJ(){
	var this_ = this;
	$("#jj_e").html("");
	$("#efjzw").html("");
}

/**显示主表数据**/
function showWODatas() {
	var this_ = this;
	if(this_.viewObj.gdlx == "3"){
		$("#qxmsDiv").hide();
		$("#gdbtName").html("缺陷描述");
		$("#gdbtEname").html("Defect Desc");
	}else{
		$("#qxmsDiv").show();
		$("#gdbtName").html("工单标题");
		$("#gdbtEname").html("Title");
	}
	$("#wo145add_zt").val(formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', this_.viewObj.zt)));//状态
	
	$("#wo145add_gdbh").val(this_.viewObj.gdbh);//工单编号
	$("#wo145add_dycs").html(this_.viewObj.paramsMap.printCount); 
	if(null != this_.viewObj.fixChapter){
		$("#wo145add_zjhid").val(this_.viewObj.fixChapter.zjh);//ATA章节号ID
		$("#wo145add_zjhName").val(this_.viewObj.fixChapter.displayName);//ATA章节号Name
	}
	
	if(this_.viewObj.bjbs != null && this_.viewObj.bjbs == 1){//必检RII
		$("#wo145add_bjbs").attr("checked","checked");
	}else{
		$("#wo145add_bjbs").removeAttr("checked");
	}
	$('input[name="wo145add_gdlx"][value="'+this_.viewObj.gdlx+'"]').prop("checked", "checked");//工单类型
	$("#wo145add_lyrwh").val(this_.viewObj.lyrwh);//来源任务号
	$("#wo145add_lyrwid").val(this_.viewObj.lyrwid);//来源任务号ID
	$("#wo145add_gkbh").val(this_.viewObj.gkbh);//来源工卡号
	$("#wo145add_gkid").val(this_.viewObj.gkid);//来源工卡ID
	$("#wo145add_gdbt").val(this_.viewObj.gdbt);//工单标题
	
	if(null != this_.viewObj.workpackage145){
		$("#wo145add_gbid").val(this_.viewObj.workpackage145.id);//工包ID
		$("#wo145add_gbbh").val(this_.viewObj.workpackage145.gbbh);//工包号
	}
	
	if(null != this_.viewObj.zdrq){
		$("#wo145add_kdrq").val(this_.viewObj.zdrq.substring(0, 10));//开单日期
	}
	if(null != this_.viewObj.jhKsrq){
		$("#wo145add_jh_ksrq").val(this_.viewObj.jhKsrq.substring(0, 10));//计划开始日期
	}
	if(null != this_.viewObj.jhJsrq){
		$("#wo145add_jh_jsrq").val(this_.viewObj.jhJsrq.substring(0, 10));//计划结束日期
	}
	$("#wo145add_bgrid").val(this_.viewObj.bgrid);//报告人ID
	$("#wo145add_bgr").val(this_.viewObj.bgr);//报告人
	$("#wo145add_zy").val(this_.viewObj.zy);//专业
	$("#wo145add_jh_zd").val(this_.viewObj.jhZd);//计划站点
	$("#wo145add_gzlb").val(this_.viewObj.gzlb);//工作类别
	$("#wo145add_jhgs_rs").val(this_.viewObj.jhgsRs);//人数
	$("#wo145add_jhgs_xss").val(this_.viewObj.jhgsXss);//小时
	this_.sumTotal();
	$("#wo145add_ybgs").val(this_.viewObj.ybgs);//一般概述
	$("#wo145add_qxms").val(this_.viewObj.qxms);//缺陷描述
	$("#wo145add_jyclfa").val(this_.viewObj.jyclfa);//建议处理方案
	$("#wo145add_mainWorkcenter").val(this_.viewObj.gzzid);//主工作组
	$("#wo145add_dq_gzzname").val(this_.viewObj.dqGzzid);//当前工作组
	$("#wo145add_dq_jdname").val(this_.viewObj.dqJdid);//当前阶段
	$("#wo145add_dq_zxzt").val(StringUtil.escapeStr(this_.viewObj.dqZxzt)+" "+StringUtil.escapeStr(this_.viewObj.dqZxzzt));//当前执行状态
	
	$("#wo145add_gbjx").val(this_.viewObj.fjjx);//机型
	$("#wo145add_gbfjzch").val(this_.viewObj.fjzch); //飞机注册号
	$("#wo145add_msn").val(this_.viewObj.fjxlh);//飞机序列号
	if(this_.viewObj.wgbs == 1 ){
		$("#wo145wgfkdiv").show(); 
		$("#wo145feedbacktab_sjJsrq").val(StringUtil.escapeStr(this_.viewObj.sjJsrq).substring(0,16)); //实际完成日期
//		$("#wo145feedbacktab_sjgzz").val(this_.viewObj.sjGzz);//实际工作者
		var gzzList = '';
		$.each(this_.viewObj.workers || [], function(i, row){
			gzzList += formatUndefine(row.gzz) + ",";
		});
		if(gzzList != ''){
			gzzList = gzzList.substring(0,gzzList.length - 1);
		}
		$("#wo145feedbacktab_sjgzz").val(gzzList);
		$("#wo145feedbacktab_sjjcz").val(this_.viewObj.sjJcz);//实际检查者
		$("#wo145feedbacktab_sjgs").val(this_.viewObj.sjGs);//实际工时
		$("#wo145feedbacktab_sjzd").val(this_.viewObj.sjZd);//工作站点
		$("#wo145feedbacktab_sjksrq").val(StringUtil.escapeStr(this_.viewObj.sjKsrq).substring(0, 10));//实际开始日期
		$("#wo145feedbacktab_gzxx").val(this_.viewObj.gzxx);//故障信息
		$("#wo145feedbacktab_clcs").val(this_.viewObj.clcs);//处理措施
		$("#wo145gbxqdiv").hide();//关闭原因
	}
	if(this_.viewObj.zt == 10){
		$("#wo145wgfkdiv").hide(); 
		$("#wo145wggbdiv").show();
		$("#wo145wgclose_gdlx").val("正常完工"); //工单类型
		$("#wo145wgclose_xfdw").val(StringUtil.escapeStr(this_.viewObj.xfrDepartment.dprtname)); //下发单位
		$("#wo145wgclose_jhksrq").val(StringUtil.escapeStr(this_.viewObj.jhKsrq).substring(0, 10)); //计划开始日期
		$("#wo145wgclose_jhjsrq").val(StringUtil.escapeStr(this_.viewObj.jhJsrq).substring(0, 10)); //计划结束日期
		$("#wo145wgclose_gbr").val(this_.viewObj.paramsMap.gbrname);
		$("#wo145wgclose_gbrq").val(StringUtil.escapeStr(this_.viewObj.gbrq).substring(0, 10));
		$("#wo145wgclose_sjJsrq").val(StringUtil.escapeStr(this_.viewObj.sjJsrq).substring(0,16)); //实际完成时间
//		$("#wo145wgclose_sjgzz").val(this_.viewObj.sjGzz);//实际工作者
		var gzzList = '';
		$.each(this_.viewObj.workers || [], function(i, row){
			gzzList += formatUndefine(row.gzz) + ",";
		});
		if(gzzList != ''){
			gzzList = gzzList.substring(0,gzzList.length - 1);
		}
		$("#wo145wgclose_sjgzz").val(gzzList);
		$("#wo145wgclose_sjjcz").val(this_.viewObj.sjJcz);//实际检查者
		$("#wo145wgclose_sjgs").val(this_.viewObj.sjGs);//实际工时
		$("#wo145wgclose_sjzd").val(this_.viewObj.sjZd);//工作站点
		$("#wo145wgclose_sjksrq").val(StringUtil.escapeStr(this_.viewObj.sjKsrq).substring(0, 10));//实际开始日期
		$("#wo145wgclose_gzxx").val(this_.viewObj.gzxx);//故障信息
		$("#wo145wgclose_clcs").val(this_.viewObj.clcs);//处理措施
		$("#wo145wgclose_gbyy").val(this_.viewObj.gbyy);//关闭原因
		$("#wo145gbxqdiv").show();//关闭原因
	}
	if(this_.viewObj.zt == 9){
		$("#wo145zdjsdiv").show();
		$("#wo145zdclose_gbr").val(this_.viewObj.paramsMap.gbrname);//关闭人
		$("#wo145zdclose_gbrq").val(StringUtil.escapeStr(this_.viewObj.gbrq).substring(0, 10));//关闭日期
		$("#wo145zdclose_gbyy").val(StringUtil.escapeStr(this_.viewObj.gbyy));//关闭原因
	}
}

/**初始化组件*/
function initEOZujian(){
	var this_ = this;
	
	//初始化工种工时
	work_hour_edit.init({
		djid:this_.viewObj.id,//关联单据id
		parentWinId : null,
		ywlx:21,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : false,
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//初始化工序列表
	WorkordeProcessUtil.init({
		djid:this_.viewObj.id,//关联单据id
		colOptionhead : false,
		workHourOptions : this_.workHourOptions,
		dprtcode: this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//区域、接近、飞机站位
	this_.loadWoCoverPlate(this_.viewObj.coverPlateList);
	
	//初始化参考文件
	ReferenceUtil.init({
		djid:this_.viewObj.id,//关联单据id
		ywlx:21,//业务类型：21:工单
		colOptionhead :false,//true:编辑,false:查看
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//初始化器材清单
	Equipment_list_edit.init({
		djid:this_.viewObj.id,//关联单据id
		parentWinId : null,
		ywlx:21,//业务类型：21:工单
		colOptionhead : false,//true:编辑,false:查看
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//初始化工具清单
	Tools_list_edit.init({
		djid:this_.viewObj.id,//关联单据id
		parentWinId :null,
		ywlx:21,//业务类型：21:工单
		colOptionhead : false,//true:编辑,false:查看
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//工作内容附件：上传按钮
	attachmentsSingleUtil.initAttachment(
			"work_content_attachments_single_edit"//主div的id
			,this_.viewObj.workContentAttachment//附件
			,'workorder145'//文件夹存放路径
			,false//true可编辑,false不可编辑
	);
	
	//初始化工作内容
	WorkContentUtil.init({
		djid:this_.viewObj.id,//关联单据id
		ywlx:21,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : false,
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//显示附件列表
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:this_.viewObj.id,
		fileHead : true,
		colOptionhead : false,
		fileType:"workorder145"
	});
	/**显示拆换件记录列表*/
	if(this_.viewObj.zt==10 || this_.viewObj.wgbs == 1){
		replacementRecord.init({
			back:this_.viewObj.woIORecordList,
			fdjsl:this_.viewObj.paramsMap.fdjsl,
			fjjx:this_.viewObj.paramsMap.fjjx,
			fjzch:this_.viewObj.fjzch,
			dprtcode:this_.viewObj.dprtcode,
			parent:"WOViewModal",
			isShowed:false,
			wbbs:this_.viewObj.workpackage145.wbbs,
		});
		$("#replacementRecord_wo145wgclose_div").css("display","block");
	}
	//Tab信息（航材工具、完工反馈、拆换件、执行历史）
//	Workorder145MainTab.loadTabInfo(this_.viewObj.id);
	$("#wo145materialToolBtn").css("display", "none");
	$("#woexeHistoryBtn").css("display", "none");
}

/**完成时限*/
function wxsxEOCover(limtStr){
	var showStr = "";
		if(null != limtStr && "" != limtStr){
			var limitArr = limtStr.split(',');
			MonitorUnitUtil.sortByStrList(limitArr);
			for (i=0;null != limitArr && i< limitArr.length;i++){
				var obj = limitArr[i].split('#_#');
				if(null != obj && obj.length == 2){
					if(i==limitArr.length-1){
						showStr += obj[1]+MonitorUnitUtil.getMonitorUnit(obj[0]);
					}else{
						showStr += obj[1]+MonitorUnitUtil.getMonitorUnit(obj[0])+",";
					}
				}
			}
		}
	return showStr;
}

//计算总数
function sumTotal(){
	var total = 0;
	var jhgsRs = $("#wo145add_jhgs_rs").val();
	var jhgsXss = $("#wo145add_jhgs_xss").val();
	total = jhgsRs*jhgsXss;
	if(total == ''){
		total = 0;
	}
	if(String(total).indexOf(".") >= 0){
		total = total.toFixed(2);
	}
	$("#wo145add_bzgs").html(total);
}

/**
 * 区域/接近/飞机站位回显
 */
function loadWoCoverPlate(coverPlateList){
	var this_ = this;
	var str = '';
	var stationStr = '';
	var qyStr = '';
	if(coverPlateList != null && coverPlateList.length > 0){
		$.each(coverPlateList,function(index,row){
			if(row.lx == 1){
				qyStr += StringUtil.escapeStr(row.gbh) + ",";
			}
			if(row.lx == 2){
				str += StringUtil.escapeStr(row.gbh)+StringUtil.escapeStr(row.coverPlateInformation==null?"":" "+row.coverPlateInformation.gbmc)+",";
			}
			if(row.lx == 3){
				stationStr += formatUndefine(row.gbh) + ",";
			}
		});
	}
	if(str != ''){
		str = str.substring(0,str.length - 1);
	}
	if(qyStr != ''){
		qyStr = qyStr.substring(0,qyStr.length - 1);
	}
	if(stationStr != ''){
		stationStr = stationStr.substring(0,stationStr.length - 1);
	}
	
	$('#eqy').html(qyStr);
	$("#jj_e").html(str);
	$("#efjzw").html(stationStr);
}

