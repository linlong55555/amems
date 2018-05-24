var viewObj = null; //对应的数据
var woJx = "";
var woZoneOption = "";
$(function() {
	//查询数据
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/produce/workorder/selectWOById",
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
	//飞机注册号
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this_.viewObj.dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' fjjx='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
		planeRegOption += "<option value='-' fjjx='-' >"+"N/A"+"</option>";
	}else{
		planeRegOption += "<option value=''  fjjx=''  >"+"暂无飞机"+"</option>";
	}
	$("#wo135add_fjzch").html(planeRegOption); 
	
	//工作类别
	$("#wo135add_gzlb").empty();
	DicAndEnumUtil.registerDic('17','wo135add_gzlb',this_.viewObj.dprtcode);
	
	//专业
	$("#wo135add_zy", $("#"+this_.id)).empty();
	DicAndEnumUtil.registerDic('4','wo135add_zy',this_.viewObj.dprtcode);
	
	//清空飞机站位/接近
	this_.clearWOFjzwAndJJ();
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
	$("#wo135add_zt").val(formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', this_.viewObj.zt)));//状态
	$("#wo135add_gdbh").val(this_.viewObj.gdbh);//工单编号
	$("#wo135add_fjzch").val(this_.viewObj.fjzch);//飞机注册号
	$("#wo135add_dycs").html(this_.viewObj.paramsMap.printCount); 
	if(null != this_.viewObj.fixChapter){
		$("#wo135add_zjhid").val(this_.viewObj.fixChapter.zjh);//ATA章节号ID
		$("#wo135add_zjhName").val(this_.viewObj.fixChapter.displayName);//ATA章节号Name
	}
	
	if(this_.viewObj.bjbs != null && this_.viewObj.bjbs == 1){//必检RII
		$("#wo135add_bjbs").attr("checked","checked");
	}else{
		$("#wo135add_bjbs").removeAttr("checked");
	}
	$('#wo135add_gdlx').val(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(this_.viewObj.gdlx)));//工单类型
	$("#wo135add_lyrwh").val(this_.viewObj.lyrwh);//来源任务号
	$("#wo135add_lyrwid").val(this_.viewObj.lyrwid);//来源任务号ID
	$("#wo135add_gkbh").val(this_.viewObj.gkbh);//来源工卡号
	$("#wo135add_gkid").val(this_.viewObj.gkid);//来源工卡ID
	$("#wo135add_gdbt").val(this_.viewObj.gdbt);//工单标题
	$("#wo135add_gbid").val(this_.viewObj.gbid);//工包ID
	$("#wo135add_gbbh").val(this_.viewObj.paramsMap.gbbh);//工包号
	if(null != this_.viewObj.kdrq){
		$("#wo135add_kdrq").val(this_.viewObj.kdrq.substring(0, 10));//开单日期
	}
	if(null != this_.viewObj.jhKsrq){
		$("#wo135add_jh_ksrq").val(this_.viewObj.jhKsrq.substring(0, 10));//计划开始日期
	}
	if(null != this_.viewObj.jhJsrq){
		$("#wo135add_jh_jsrq").val(this_.viewObj.jhJsrq.substring(0, 10));//计划结束日期
	}
	$("#wo135add_bgrid").val(this_.viewObj.bgrid);//报告人ID
	$("#wo135add_bgr").val(this_.viewObj.bgr);//报告人
	$("#wo135add_zy").val(this_.viewObj.zy);//专业
	$("#wo135add_jh_zd").val(this_.viewObj.jhZd);//计划站点
	$("#wo135add_gzlb").val(this_.viewObj.gzlb);//工作类别
	$("#wo135add_jhgs_rs").val(this_.viewObj.jhgsRs);//人数
	$("#wo135add_jhgs_xss").val(this_.viewObj.jhgsXss);//小时
	this_.sumTotal();
	$("#wo135add_ybgs").val(this_.viewObj.ybgs);//一般概述
	$("#wo135add_qxms").val(this_.viewObj.qxms);//缺陷描述
	$("#wo135add_jyclfa").val(this_.viewObj.jyclfa);//建议处理方案
	if(null != this_.viewObj.monitoringPlan && null != this_.viewObj.monitoringPlan.paramsMap){
		var limtStr = this_.viewObj.monitoringPlan.paramsMap.jhsjsj;
		$("#wo135add_wcsx").val(this_.wxsxEOCover(limtStr));//完成时限
	}
	if(null != this_.viewObj.monitoringObject){
		$("#wo135add_bjh").val(this_.viewObj.monitoringObject.bjh);//部件号
		$("#wo135add_xlh").val(this_.viewObj.monitoringObject.xlh);//序列号
	}
	if(this_.viewObj.wgbs == 1 ){
		$("#wo135wgfkdiv").show();
		if(this_.viewObj.paramsMap.sjwwbs==1){
			$("#wo135feedbacktab_sjZxdw").val("外委   "+StringUtil.escapeStr(this_.viewObj.paramsMap.sjzxdw));
		}else if(this_.viewObj.paramsMap.sjwwbs==0){
			$("#wo135feedbacktab_sjZxdw").val("内部   "+StringUtil.escapeStr(this_.viewObj.paramsMap.sjzxdw));
		}else{
			$("#wo135feedbacktab_sjZxdw").val("");
		}
		$("#wo135feedbacktab_sjJsrq").val(StringUtil.escapeStr(this_.viewObj.sjJsrq).substring(0,16)); //实际完成日期
//		$("#wo135feedbacktab_sjgzz").val(this_.viewObj.sjGzz);//实际工作者
		var gzzList = '';
		$.each(this_.viewObj.workers || [], function(i, row){
			gzzList += formatUndefine(row.gzz) + ",";
		});
		if(gzzList != ''){
			gzzList = gzzList.substring(0,gzzList.length - 1);
		}
		$("#wo135feedbacktab_sjgzz").val(gzzList);
		if(this_.viewObj.hsgs == 1){
			$("#wo135feedbacktab_hsgs").attr("checked",'true');//选中
		}
		$("#wo135feedbacktab_sjjcz").val(this_.viewObj.sjJcz);//实际检查者
		$("#wo135feedbacktab_sjgs").val(this_.viewObj.sjGs);//实际工时
		$("#wo135feedbacktab_sjzd").val(this_.viewObj.sjZd);//工作站点
		$("#wo135feedbacktab_sjksrq").val(StringUtil.escapeStr(this_.viewObj.sjKsrq).substring(0, 10));//实际开始日期
		$("#wo135feedbacktab_gzxx").val(this_.viewObj.gzxx);//故障信息
		$("#wo135feedbacktab_clcs").val(this_.viewObj.clcs);//处理措施
		$("#wo135gbxqdiv").hide();//关闭原因
	}
	if(this_.viewObj.zt == 10){
		$("#wo135wgfkdiv").hide();
		$("#wo135wggbdiv").show();
		$("#wo135wgclose_gdlx").val("正常完工"); //工单类型
		$("#wo135wgclose_xfdw").val(StringUtil.escapeStr(this_.viewObj.xfrDepartment==null?"":this_.viewObj.xfrDepartment.dprtname)); //下发单位
		$("#wo135wgclose_jhksrq").val(StringUtil.escapeStr(this_.viewObj.jhKsrq).substring(0, 10)); //计划开始日期
		$("#wo135wgclose_jhjsrq").val(StringUtil.escapeStr(this_.viewObj.jhJsrq).substring(0, 10)); //计划结束日期
		$("#wo135wgclose_sjJsrq").val(StringUtil.escapeStr(this_.viewObj.sjJsrq).substring(0,16)); //实际完成时间
		$("#wo135wgclose_gbr").val(this_.viewObj.paramsMap.gbrname);
		$("#wo135wgclose_gbrq").val(StringUtil.escapeStr(this_.viewObj.gbrq).substring(0, 10));
//		$("#wo135wgclose_sjgzz").val(this_.viewObj.sjGzz);//实际工作者
		var gzzList = '';
		$.each(this_.viewObj.workers || [], function(i, row){
			gzzList += formatUndefine(row.gzz) + ",";
		});
		if(gzzList != ''){
			gzzList = gzzList.substring(0,gzzList.length - 1);
		}
		$("#wo135wgclose_sjgzz").val(gzzList);
		if(this_.viewObj.hsgs == 1){
			$("#wo135wgclose_hsgs").attr("checked",'true');//选中
		}
		$("#wo135wgclose_sjjcz").val(this_.viewObj.sjJcz);//实际检查者
		$("#wo135wgclose_sjgs").val(this_.viewObj.sjGs);//实际工时
		$("#wo135wgclose_sjzd").val(this_.viewObj.sjZd);//工作站点
		$("#wo135wgclose_sjksrq").val(StringUtil.escapeStr(this_.viewObj.sjKsrq).substring(0, 10));//实际开始日期
		$("#wo135wgclose_gzxx").val(this_.viewObj.gzxx);//故障信息
		$("#wo135wgclose_clcs").val(this_.viewObj.clcs);//处理措施
		$("#wo135wgclose_gbxq").val(this_.viewObj.gbyy);//关闭原因
		$("#wo135gbxqdiv").show();//关闭原因
	}
	if(this_.viewObj.zt == 9){
		$("#wo135zdjsdiv").show();
		$("#wo135zdclose_gbr").val(this_.viewObj.paramsMap.gbrname);//关闭人
		$("#wo135zdclose_gbrq").val(StringUtil.escapeStr(this_.viewObj.gbrq).substring(0, 10));//关闭日期
		$("#wo135zdclose_gbyy").val(StringUtil.escapeStr(this_.viewObj.gbyy));//关闭原因
	}
}

/**初始化组件*/
function initEOZujian(){
	var this_ = this;
	if(viewObj.gdlx ==1||viewObj.gdlx ==2||viewObj.gdlx ==9){
		$("#wo135add_jspgddiv").css("display","none");
	}else{
		$("#wo135add_jspgddiv").css("display","block");
		//选择评估单
		evaluation_modal.init({
			isShowed : false,
			zlid : viewObj.id,
			zlxl : 4,
			dprtcode :viewObj.dprtcode,
			jx : this_.jx,
		});	
	}
	//初始化工种工时
	work_hour_edit.init({
		djid:this_.viewObj.id,//关联单据id
		parentWinId : null,
		ywlx:21,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : false,
		dprtcode:this_.viewObj.dprtcode//默认登录人当前机构代码
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
			,'workorder135'//文件夹存放路径
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
		fileType:"workorder135"
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
		});
		$("#replacementRecord_wo135wgclose_div").css("display","block");
	}
	//Tab信息（航材工具、完工反馈、拆换件、执行历史）
//	Workorder135MainTab.loadTabInfo(this_.viewObj.id);
	$("#wo135materialToolBtn").css("display", "none");
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
						if(MonitorUnitUtil.isTime(obj[0])){
							showStr += TimeUtil.convertToHour(obj[1], TimeUtil.Separator.COLON)+MonitorUnitUtil.getMonitorUnit(obj[0])
						}else if(MonitorUnitUtil.isCal(obj[0])){
							showStr += obj[1];
						}else if(MonitorUnitUtil.isLoop(obj[0])){
							showStr += obj[1]+MonitorUnitUtil.getMonitorUnit(obj[0]);
						}
					}else{
						if(MonitorUnitUtil.isTime(obj[0])){
							showStr += TimeUtil.convertToHour(obj[1], TimeUtil.Separator.COLON)+MonitorUnitUtil.getMonitorUnit(obj[0])+","
						}else if(MonitorUnitUtil.isCal(obj[0])){
							showStr += obj[1]+",";
						}else if(MonitorUnitUtil.isLoop(obj[0])){
							showStr += obj[1]+MonitorUnitUtil.getMonitorUnit(obj[0])+",";
						}
					}
				}
			}
		}
	return showStr;
}

//计算总数
function sumTotal(){
	var total = 0;
	var jhgsRs = $("#wo135add_jhgs_rs").val();
	var jhgsXss = $("#wo135add_jhgs_xss").val();
	total = jhgsRs*jhgsXss;
	if(total == ''){
		total = 0;
	}
	if(String(total).indexOf(".") >= 0){
		total = total.toFixed(2);
	}
	$("#wo135add_bzgs").html(total);
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

