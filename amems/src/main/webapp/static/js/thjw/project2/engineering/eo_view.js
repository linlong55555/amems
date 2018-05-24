var viewObj = null; //对应的数据
var eo_status = {
	"1":{name:"保存"},
	"2":{name:"提交"},
	"3":{name:"已审核"},
	"4":{name:"已批准"},
	"5":{name:"审核驳回"},
	"6":{name:"审批驳回"},
	"7":{name:"生效"},
	"9":{name:"指定结束"},
	"10":{name:"完成"}
};

$(function() {
	//查询数据
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project2/eo/selectById",
		type:"post",
		data:{eoId:$("#id").val(),viewFlag:"1"},
		dataType:"json",
		success:function(data){
			viewObj = data;
		}
	});
	collapseSetting();
	//显示数据
	
	showDatas();
});
function collapseSetting(){
	/* 适用性设置 */
	$('#appSettingCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#appSettingCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 适用性设置 */
	
	/* 工作概述 */
	$('#summaryCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#summaryCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 工作概述 */
	
	/* 改版记录 */
	$('#revRecordCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#revRecordCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 改版记录*/
	
	/* 维修改装对象 */
	$('#objectCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#objectCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 维修改装对象*/
	
	/* 重量与平衡 */
	$('#weightCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#weightCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 重量与平衡*/
	
	/* 参考文件 */
	$('#referenceCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#referenceCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 参考文件*/
	
    /* 影响手册  */
	$('#manualsCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#manualsCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 影响手册 */
	
	/* 受影响的出版物 */
	$('#pulicationCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#pulicationCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 受影响的出版物*/
	
	/* 受影响的机载软件清单  */
	$('#softwareCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#softwareCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 受影响的机载软件清单 */
	
	/* 电气负载数据  */
	$('#electricalCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#electricalCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 电气负载数据 */
	
	/* 原有零件处理  */
	$('#disposalCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#disposalCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 原有零件处理 */
	
	/* 分发  */
	$('#distributionCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#distributionCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 分发 */
	
	/* 附件信息  */
	$('#attachmentCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#attachmentCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 附件信息 */
	
	/* 计划 */
	$('#planningCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#planningCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 计划 */
	
	/* 工时/停场时间 */
	$('#manpowerCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#manpowerCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 工时/停场时间 */
	/* 索赔 */
	$('#warrantyCollapsed').on('shown.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","checked")
	})
	$('#warrantyCollapsed').on('hidden.bs.collapse', function () {
		$(this).siblings(".panel-heading").find("input.selectCheckbox").prop("checked","")
	})
	/* 索赔 */
}

/**显示主表数据**/
function showDatas() {
	if(viewObj == null){return;}
	
	/*EO主表信息*/
	$("#eobh_add").val(viewObj.eobh);//EO编号：必须
	$("#bb_add").val(viewObj.bb);//版本：必须
	$('input[name="isXfsc_add"][value="'+viewObj.isXfsc+'"]').prop("checked", "checked");//下发生产
	$("#zt_add").val(getEOStatusName(viewObj.zt));//状态
	if(viewObj.zt == 9 || viewObj.zt == 10){
		$("#EOViewModal_gbyy_div").show();
		$("#ezdjsyy").val(viewObj.gbyy);
	}else{
		$("#EOViewModal_gbyy_div").hide();
	}
	$('#eozt_add').val(viewObj.eozt);//EO主题
	$('#jx_add').val(viewObj.jx);//机型：必须
	//章节号回显
	if(viewObj.fixChapter != null){
		var wczjhName = StringUtil.escapeStr(viewObj.fixChapter.zjh) + " " + StringUtil.escapeStr(viewObj.fixChapter.ywms);
		$("#zjhid_add").val(viewObj.fixChapter.zjh);
		$("#zjhName_add").val(wczjhName);
	}else{
		$("#zjhid_add").val('');
		$("#zjhName_add").val('');
	}
	if(viewObj.bfrq != null){
		var bfrq = viewObj.bfrq+"";
		$("#bfrq_add").val(bfrq.substring(0, 10));//颁发日期
	}else{
		$("#bfrq_add").val("");
	}
	$('input[name="jb_add"][value="'+viewObj.jb+'"]').prop("checked", "checked");//级别
	$('#gzlx_add').val(viewObj.gzlx);//工作类型
	$('input[name="sylb_public"][value="'+viewObj.sylb+'"]').prop("checked", "checked");//适用类别
	
	if(viewObj.sylb == 1){
		$('#qbsyLable').css("display", "inline");
	}else{
		$('#qbsyLable').css("display", "none");
	}
	
	if(viewObj.fjzch == "00000"){
		$('#qbsyInput').prop("checked", "checked");//全部适用
	}
	$('#eoLB_add').val(viewObj.eolb);//EO类别
	$('#eofj_add').val(viewObj.eofj);//EO级别
	$('#jhgsRs').val(viewObj.jhgsRs);//计划工时_人数
	$('#jhgsXss').val(viewObj.jhgsXss);//计划工时_小时数
	this.sumTotal();
	
	if(viewObj.rii == 1){//RII
		$("#rii_add").attr("checked","true");
	}else{
		$("#rii_add").removeAttr("checked");
	}
	$('input[name="zxfsradio"][value="'+viewObj.zxfs+'"]').prop("checked", "checked");//执行方式
	if(viewObj.hdwz == 1){//以后到为准
		$("#hdwzradio").attr("checked","true");
	}else{
		$("#hdwzradio").removeAttr("checked");
	}
	
	/*EO从表信息*/
	var eoSub = viewObj.eoSub;
	
	if(eoSub == undefined || null == eoSub){return;}
	
	$('input[name="scjszy_add"][value="'+eoSub.scjszy+'"]').prop("checked", "checked");//首次技术支援
	$('#ywjsyfw_public').val(eoSub.ywjsyfw);//源文件使用范围
	$('#gzgs_add').val(eoSub.gzgs);//工作概述
	$('#fhx_add').val(eoSub.fhx );//符合性
	$('#gbjl_bc_public').val(eoSub.gbjlBc);//版次
	$('#gbjl_yqbbgx_public').val(eoSub.gbjlYqbbgx);//与之前版本关系
	$('#gbjl_gbyy_public').val(eoSub.gbjlGbyy);//改版原因
	$('#wxgzdxid_public').val(eoSub.wxgzdxid);//维修改装对象 , 部件ID
	$('#wxgzdx_public').val(eoSub.wxgzdx);//维修改装对象 , 部件号
	
	if(eoSub.hcMainData != null){
		$('#zjh_public').html(eoSub.hcMainData.zjh); //部件关联的章节号
		$("#wxgzdxName_public").val(eoSub.hcMainData.ywms+" "+eoSub.hcMainData.zwms);
		if(eoSub.hcMainData.fixChapter != null){
			$("#zjhName_public").val(eoSub.hcMainData.fixChapter.displayName);
		}
	}
	
	//标识
	$('input:checkbox[name="wxgzbs_public"]').each(function(i){ 
		if(eoSub.wxgzbs != null && eoSub.wxgzbs.indexOf($(this).val()) > -1){
			$(this).prop("checked", "checked");
			if($(this).val() == "H"){
				$('#wxgzbs_qt_public').css("visibility","visible");
			}
		}
    }); 
	$('#wxgzbs_qt_public').val(eoSub.wxgzbsQt);//其他
	$('input[name="is_zzphbh_public"][value="'+eoSub.isZzphbh+'"]').prop("checked", "checked");//载重平衡变化
	$('#mtc_public').val(eoSub.mtc);//Mt Change
	$('#wtc_public').val(eoSub.wtc);//Wt Change
	$('#arm_public').val(eoSub.arm);//ARM
	$('#bhnr_public').val(eoSub.bhnr);//变化内容
	$('#yxsc_add').val(eoSub.yxsc);//影响手册
	$('#qcjg_add').val(eoSub.qcjg);//器材价格
	
	//币种
	$("#qcjgdw_add").empty();
	 var bzIteam = DicAndEnumUtil.getDict('3',viewObj.dprtcode);
	 for(i=0;null != bzIteam && i<bzIteam.length;i++){
		 if(i==0){
			 $("#btqcjgdw").empty();
			 $("#btqcjgdw").html(StringUtil.escapeStr(bzIteam[i].name)+" <span class='caret caret-new' ></span>");
		 }
		 $('#qcjgdw_add').append("<li><a href='#' value='"+StringUtil.escapeStr(bzIteam[i].id)+"' >"+StringUtil.escapeStr(bzIteam[i].name)+"</a></li>");
	 }
	 
	if(null != eoSub.qcjgdw){
		$('#btqcjgdw').html(eoSub.qcjgdw); 
	}else{
		if(null != bzIteam && bzIteam.length > 0){
			$('#btqcjgdw').html(StringUtil.escapeStr(bzIteam[0].name)); //qcjgdw_add 币种
		}
	}
	
	$('#btqcjgdw').html($('#btqcjgdw').html()); //qcjgdw_add 币种
	$('input[name="bfqczb_add"][value="'+eoSub.bfqczb+'"]').prop("checked", "checked");//是否颁发器材准备
	$('#bfqczbtzd_add').val(eoSub.bfqczbtzd);//颁发器材准备通知单
	$('input[name="is_yxcbw_public"][value="'+eoSub.isYxcbw+'"]').prop("checked", "checked"); //是否影响出版物
	$('#sycjzrjqd_add').val(eoSub.sycjzrjqd); //受影响的机载软件清单
	$('input[name="is_dqfzsj_add"][value="'+eoSub.isDqfzsj+'"]').prop("checked", "checked");//电气负载数据变化
	$('#dqfzsj_add').val(eoSub.dqfzsj);//电气负载数据
	$('#wxxmxgx_add').val(eoSub.wxxmxgx);//维修项目的相关性
	$('#fkyq_add').val(eoSub.fkyq);//反馈要求
	$('#hcxqsqd_add').val(eoSub.hcxqsqd);//航材需求申请单
	$('#yyljcl_add').val(eoSub.yyljcl);//原有零件处理
	$('#hhxx_add').val(eoSub.hhxx);//互换信息	
	$('#qt_add').val(eoSub.qt);//其他
	if(eoSub.zztjbs == 1){//终止条件标识
		$("#zztjbsradio").attr("checked","true");
	}else{
		$("#zztjbsradio").removeAttr("checked");
	}
	$('#zztj').val(eoSub.zztj);//终止条件
	$('#gcjy_add').val(eoSub.gcjy);//工程建议
	$('#scap_add').val(eoSub.scap);//生产安排
	$('#yyjms_add').val(eoSub.yyjms);//原因及描述
	$('#clcs_add').val(eoSub.clcs);//处理措施
	
	if(eoSub.isSpQc == 1){//是否索赔器材
		$("#is_sp_qc_public").attr("checked","true");
		$("#sp_qcsm_public").css("visibility","visible");
	}else{
		$("#is_sp_qc_public").removeAttr("checked");
	}
	$('#sp_qcsm_public').val(eoSub.spQcsm);//器材
	$('#sp_jgxx_public').val(eoSub.spJgxx);//索赔信息
	if(eoSub.isSpRg == 1){//是否工人
		$("#is_sp_rg_public").attr("checked","true");
		$("#sp_rgsm_public").css("visibility","visible");
	}else{
		$("#is_sp_rg_public").removeAttr("checked");
	}
	$('#sp_rgsm_public').val(eoSub.spRgsm);//人工
	if(eoSub.spqx != null){
		$('#spqx_public').val((eoSub.spqx+"").substring(0, 10));//索赔期限
	}else{
		$('#spqx_public').val("");
	}
	$('input[name="is_tsgjsb_public"][value="'+eoSub.isTsgjsb+'"]').prop("checked", "checked");//特殊工具和设备
	
	//显示组件
	initZujianInfo(false);
	
	//设置只读
	setReadOnlyFailure();
	
	if(viewObj.fBbid != null){
		$("#new_bbh_span").show();
		$("#old_bbhlist_span").show();
		$("#new_bbh").html("");
		$("#old_bbh").html(viewObj.fBbObj.bb);
		initWoviewWebuiPopover(); //显示历史版本信息
	}
}

//显示历史版本信息
function initWoviewWebuiPopover(){
	WebuiPopoverUtil.initWebuiPopover('attachment-view2',"body",function(obj){
		return getEoviewHistoryBbList(obj);
	});
}

function getEoviewHistoryBbList(obj){//获取历史版本列表
	return eo_history_view_alert_Modal.getHistoryBbList(viewObj.id);
}
//查看历史信息
function showHistoryView(flag){
	openWinView(viewObj.fBbObj.id);
}

/**打开查看弹出框*/
function openWinView(id,dprtcode_){
	window.open(basePath+"/project2/eo/view?id="+id);
}

/**
 * 初始化组件信息
 */
function initZujianInfo(colOptionhead) {
	//技术评估单
	evaluation_modal.init({
		isShowed : colOptionhead,// 是否显示选择评估单的操作列
		zlid : this.viewObj.id,
	});

	//初始化参考文件
	ReferenceUtil.init({
		djid:this.viewObj.id,//关联单据id
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : colOptionhead,//true:编辑,false:查看
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//初始化工种工时
	/*work_hour_edit.init({
		djid:eoId,//关联单据id
		parentWinId : "EOViewModal",
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : colOptionhead,
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});*/
	
	//受影响出版物
	PublicationUtil.init({
		parentId:"EOViewModal",
		djid:this.viewObj.id,//关联单据id
		colOptionhead : colOptionhead,
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//工时/停场时间 
	StoppingUtil.init({
		djid:this.viewObj.id,//关联单据id
		colOptionhead : colOptionhead,
	});
	
	//初始EO附件列表
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:this.viewObj.id,
		colOptionhead : colOptionhead,
		fileHead : colOptionhead,
		attachHead : false,
		fileType:"eo"
	});
	
	//初始化器材清单
	Equipment_list_edit.init({
		djid:this.viewObj.id,//关联单据id
		parentWinId : "EOViewModal",
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : colOptionhead,//true:编辑,false:查看
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//初始化工具清单
	Tools_list_edit.init({
		djid:this.viewObj.id,//关联单据id
		parentWinId :"EOViewModal",
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : colOptionhead,//true:编辑,false:查看
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//上传按钮
	attachmentsSingleUtil.initAttachment(
			"work_content_attachments_single_edit"//主div的id
			,this.viewObj.workContentAttachment//附件
			,'eo'//文件夹存放路径
			,colOptionhead//true可编辑,false不可编辑
		);
	
	//初始化工作内容
	WorkContentUtil.init({
		djid:this.viewObj.id,//关联单据id
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : colOptionhead,
		dprtcode:this.viewObj.dprtcode//默认登录人当前机构代码
	});
	
	//监控项设置 执行方式：1单次、2重复、3分段
	initJkxszWin(this.viewObj.zxfs,this.viewObj.id,this.viewObj.sylb);
	if(null != this.viewObj.jsgs){
		$('#jkxsz_frame_package_jsgs').val(this.viewObj.jsgs);
	}
	
	//以后到为准
	if(this.viewObj.hdwz != null && this.viewObj.hdwz == 1){
		$("#hdwzradio").attr("checked","checked");
	}else{
		$("#hdwzradio").removeAttr("checked");
	}
	
	//终止条件标识
	if(this.viewObj.eoSub.zztjbs != null && this.viewObj.eoSub.zztjbs == 1){
		$("#zztjbsradio").attr("checked","true");
		$("#zztj").css("visibility","visible");
	}else{
		$("#zztjbsradio").removeAttr("checked");
	}

	//终止条件
	$('#zztj').val(this.viewObj.eoSub.zztj);
	
	var obj = this.viewObj;
	//流程信息
	introduce_process_info_class.show({  
		status:4,//1,编写,2审核,3批准，4审核驳回,5批准驳回
		prepared:StringUtil.escapeStr(obj.zdr?obj.zdr.displayName:''),//编写人
		preparedDate:StringUtil.escapeStr(obj.whsj),//编写日期
		checkedOpinion:StringUtil.escapeStr(obj.shyj),//审核意见
		checkedby:StringUtil.escapeStr(obj.shr?obj.shr.displayName:''),//审核人
		checkedDate:StringUtil.escapeStr(obj.shsj),//审核日期
		checkedResult:obj.shjl,//审核结论
		approvedOpinion:StringUtil.escapeStr(obj.pfyj),//批准意见
		approvedBy:StringUtil.escapeStr(obj.pfr?obj.pfr.displayName:''),//批准人
		approvedDate:StringUtil.escapeStr(obj.pfsj),//批准日期
		approvedResult : obj.pfjl,// 批准结论
	});
	
	this.initEOZoneInformation(this.viewObj.jx,this.viewObj.dprtcode); //区域
	
    //适用性列表
	applicable_settings.init({
		operationShow:false,
		dataList:this.viewObj.syxszList,
		sylb:this.viewObj.sylb,
	});
	//全部适用就选中 并隐藏删除图标
	var fjzch = this.viewObj.fjzch;
	var chkSylb = this.viewObj.sylb;
	if(fjzch == "00000" && chkSylb == 1){
		$("#qbsyInput").prop('checked','checked'); 
		applicable_settings.loadAllFj();
	}else{
		$("#qbsyInput").removeAttr("checked");
	}
	applicable_settings.showHidePlaneMinus();
	//工具设备:显示特殊工具和设备
	$("#is_tsgjsb_public_div").css("display", "block");
	
	var this_ = this;
	//回显分发部门
	if(this_.viewObj.ffdeptList != null && this_.viewObj.ffdeptList.length > 0){
		var ffnames = "";
		$.each(this_.viewObj.ffdeptList, function(i, ffObj) {
			if(i == this_.viewObj.ffdeptList.length-1){
				ffnames += ffObj.department.dprtname;
			}else{
				ffnames += ffObj.department.dprtname+",";
			}
		});
		$("#ff").val(ffnames);
	}
}

var quyuDatas = [];
//初始化区域下拉框
function initEOZoneInformation(_jx, _dprtcode) {
	var this_ = this;
	var searchParam = {};
	searchParam.dprtcode = _dprtcode;
	searchParam.jx = _jx;//机型
	searchParam.lx = 1;
	AjaxUtil.ajax({
		url : basePath + "/basic/zone/zoneList",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(searchParam),
		success : function(data) {
			/*$("#qy_publicDiv").empty();
			$("#qy_publicDiv").html("<input class='form-control' id='qy_public' disabled='disabled' >");
			if (data != null && data.length > 0) {
				this_.quyuDatas = data;
			}else{
				$("#qy_public").empty();
			}*/
			this_.reviewQyDatas();
		}
	});
}

//回显接近（盖板）/区域/站位
function reviewQyDatas(){
	var this_ = this;
	var obj = this.viewObj;
	if(obj.qyzwList != null && obj.qyzwList.length > 0){
		var zonePublicList = [];
		var quyuStr = "";
		var stationStr = "";
		
		$.each(obj.qyzwList, function(i, cover){
			if(cover.lx == 1){
				/*$.each(this_.quyuDatas, function(i, row) {
					if(cover.gbid == row.id){
						quyuStr += formatUndefine(row.sz) + ",";
					}
				});*/
				quyuStr += formatUndefine(cover.gbh) + ",";
			}
			if(cover.lx == 3){
				positionPublicList.push({
					id : cover.gbid,
					sz : cover.gbh
				});
				stationStr += formatUndefine(cover.gbh) + ",";
			}
		});
		
		// 回显区域
		if(quyuStr != ''){
			quyuStr = quyuStr.substring(0,quyuStr.length - 1);
		}
		$('#eqy_public').val(quyuStr);
		
		// 回显飞机站位
		if(stationStr != ''){
			stationStr = stationStr.substring(0,stationStr.length - 1);
		}
		$("#efjzw_public").val(stationStr);
	}
}

function back(){
	window.location.href = basePath+"/project2/eo/main";
}

/**设置只读*/
function setReadOnlyFailure(){
	
	$('#ywjsyfw_public').attr("disabled","disabled");//源文件使用范围
	$('#gbjl_bc_public').attr("disabled","disabled");//版次
	$('#gbjl_yqbbgx_public').attr("disabled","disabled");//与之前版本关系
	$('#gbjl_gbyy_public').attr("disabled","disabled");//改版原因
	//标识
	$('input:checkbox[name="wxgzbs_public"]').each(function(i){ 
		$(this).attr("disabled", "disabled");
    });  
	$('#wxgzbs_qt_public').attr("disabled", "disabled");//其他
	$('input[name="is_zzphbh_public"]').attr("disabled","disabled");//载重平衡变化
	$('#mtc_public').attr("disabled","disabled");//Mt Change
	$('#wtc_public').attr("disabled","disabled");//Wt Change
	$('#arm_public').attr("disabled","disabled");//ARM
	$('#bhnr_public').attr("disabled","disabled");//变化内容
	$('input[name="is_yxcbw_public"]').attr("disabled","disabled");//是否影响出版物
	$('#btqcjgdw').attr("disabled","disabled");//qcjgdw_add 币种
	$('#efjzw_ico_public').css("display", "none");
	$('#eqy_public_btn').css("display", "none");
	
	$("#wxgzdx_public").removeAttr("readonly");
	$("#wxgzdx_public").attr("disabled","disabled");
	
	$("#zjhName_public").removeAttr("readonly");
	$("#zjhName_public").attr("disabled","disabled");
	
	$("#efjzw_public").removeAttr("readonly");
	$("#efjzw_public").attr("disabled","disabled");
	
	$("#eqy_public").removeAttr("readonly");
	$("#eqy_public").attr("disabled","disabled");
	
	
	$('#wxgzdxbt_public').css("display", "none");
	
	$('#zjhName_add').removeClass("readonly-style");
	$('#ff').removeClass("readonly-style");
	$('#wxgzdx_public').removeClass("readonly-style");
	$('#efjzw_public').addClass("div-readonly-style");
	
	
	$('#gcjy_add').attr("disabled","disabled");//工程建议
	$('#scap_add').attr("disabled","disabled");//生产安排
	$('#yyjms_add').attr("disabled","disabled");//原因及描述
	$('#clcs_add').attr("disabled","disabled");//处理措施
	$("#is_sp_qc_public").attr("disabled","disabled");//是否索赔器材
	$('#sp_qcsm_public').attr("disabled","disabled");//器材
	$('#sp_jgxx_public').attr("disabled","disabled");//索赔信息
	$("#is_sp_rg_public").attr("disabled","disabled");//是否工人
	$('#sp_rgsm_public').attr("disabled","disabled");//人工
	$('#spqx_public').attr("disabled","disabled");//索赔期限
	$('input[name="is_tsgjsb_public"]').attr("disabled","disabled");//特殊工具和设备
	
	//监控项设置
	$('input[name="zxfsradio"]').attr("disabled","disabled");
	$('#hdwzradio').attr("disabled","disabled");//后到为准
	$('#zztjbsradio').attr("disabled","disabled");//终止条件
	$('#zztj').attr("disabled","disabled");//终止条件
	$('#addFdJkx').css("display", "none");
	$('button[name="removeFdJkx"]').css("display", "none");
	$('input[name="jkxItems"]').attr("disabled","disabled");
	$('input.monitorItem').attr("disabled","disabled");
	$('input[name="sc"]').attr("disabled","disabled");
	$('input[name="zq"]').attr("disabled","disabled");
	$('input[name="rcq"]').attr("disabled","disabled");
	$('input[name="rch"]').attr("disabled","disabled");
	$('button[name="sc_dw"]').attr("disabled","disabled");
	$('button[name="zq_dw"]').attr("disabled","disabled");
	$('button[name="rc_dw"]').attr("disabled","disabled");
	$('#jkxsz_frame_package_jsgs').attr("disabled","disabled"); //计算公式
	
	$('input[name="jb_add"]').attr("disabled","disabled");//级别
	$('input[name="sylb_public"]').attr("disabled","disabled");//适用类别
	$('#qbsyInput').attr("disabled","disabled");//全部适用
	
	
	$('.multiselect').attr("disabled","disabled"); //区域
	$('#syxlbredspan').css("display", "none");
}

/**
 * 计算总数
 */
function sumTotal(){
	var total = 0;
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	total = jhgsRs*jhgsXss;
	
	if(total == ''){
		total = 0;
	}
	if(String(total).indexOf(".") >= 0){
		total = total.toFixed(2);
	}
	
	if($("#jhgsRs").val() == "" || $("#jhgsXss").val() == ""){
		$("#bzgs").html(0);
	}else{
		$("#bzgs").html(total);
	}
}

/**
 * 状态
 * @param status
 * @returns
 */
function getEOStatusName(status){
	var obj = this.eo_status[status] || {};
	return StringUtil.escapeStr(obj.name);
}

function printEO(){
	var url=basePath+"/project2/eo/eo?id=" + encodeURIComponent($("#id").val());
	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
			'PDF','width:50%;height:50%;top:100;left:100;');	
}