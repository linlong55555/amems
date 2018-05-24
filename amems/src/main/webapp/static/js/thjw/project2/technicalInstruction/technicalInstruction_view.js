$(document).ready(function(){
	Navigation(menuCode,"查看技术指令","View Technical Instruction");

	var param = {};
	var paramsMap = {};
	paramsMap = {
		"view" : "1" //查看页面对当前技术指令做标识 
	};
	param.paramsMap = paramsMap;
	param.id = $("#instructionId").val();		
	fillData(param);//填充页面内容
});

//回写数据
function fillData(param) {
	AjaxUtil.ajax({
		url : basePath + "/project2/instruction/edit",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			$("#jx").val(data.jx);
			evaluation_modal.init({
				isShowed : false,
				zlid : data.id,		
				changeCss:true,
				isShowAll:false,
			});
			$("#id").val(data.id);
			$("#dprt").val(data.dprtcode);
			$("#zt").val(data.zt);
			$("#fcrname").val(data.fcr_user.username +" " +data.fcr_user.realname);
			$("#jsrname").val(data.jsr_user.username +" " +data.jsr_user.realname);
			$("#jszlh").attr("disabled",true);
			$("#jszlh").val(data.jszlh);
			$("#fcrq").val(data.fcrq.substring(0,10));
			$("#bb").val(data.bb);
			$("#current_bb").val(data.bb);
			$("#fcrid").val(data.fcrid);
			$("#zhut").val(data.zhut);
			$("#lysm").val(data.lysm);
			$("#zxsx").val(data.zxsx);
			$("#bflyyj").val(data.bflyyj);
			$("#zt_input").val(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum', data.zt));
			$("#bz").val(data.bz);
			WorkContentUtil.init({
				djid:data.id,//关联单据id
				ywlx:2,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
				colOptionhead : false,
				dprtcode:data.dprtcode//默认登录人当前机构代码
			});
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				djid:data.id,
				fileHead : false,
				colOptionhead :false,
				fileType:"technicalInstruction"
			});//显示附件
			introduce_process_info_class.show({ // 流程信息
				status : 5,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
				prepared : data.zdr_user.username + " "+ data.zdr_user.realname,// 编写人
				preparedDate : data.zdsj,// 编写日期
				checkedOpinion : data.shyj,// 审核意见
				checkedby : data.shr_user != null ? data.shr_user.username+ " " + data.shr_user.realname : "",// 审核人
				checkedDate : data.shsj,// 审核日期
				checkedResult : data.shjl,// 审核结论
				approvedOpinion : data.pfyj,// 批准意见
				approvedBy : data.pfr_user != null ? data.pfr_user.username+ " " + data.pfr_user.realname : "",// 批准人
				approvedDate : data.pfsj,// 批准日期
				approvedResult : data.pfjl ,// 审批结论
			});
			switchHistoryVersion(data);
		}
	});
}

//切换显示历史版本
function switchHistoryVersion(data){
	if(data && data.previous){
		$("#bbViewHistoryDiv").show();
		$("#bbNoViewHistoryDiv").hide();
		initHistoryBb(data.id);
		$("#previous_bb").text(data.previous.bb);
		$("#previous_id").val(data.previous.id)
	}else{
		$("#bbViewHistoryDiv").hide();
		$("#bbNoViewHistoryDiv").show();
	}
}

//查看前一版本的
function viewPrevious(){
	var id = $("#previous_id").val();
	window.open(basePath + "/project2/instruction/view?id=" + id);
}

//初始化历史版本
function initHistoryBb (id){
	WebuiPopoverUtil.initWebuiPopover('attachment-view2',"body",function(obj){
		return getHistoryBbList(id);
	});
}

//获取历史版本列表
function getHistoryBbList (id){
	return technical_order_history_alert_Modal.getHistoryBbList(id);
}

