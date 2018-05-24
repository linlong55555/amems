$(document).ready(function(){
	Navigation(menuCode, '查看MEL更改单', 'View', 'GC-3-2 ', '孙霁', '2017-08-5', '孙霁', '2017-08-18');//加载导航栏
	//所有文本框不可修改
	textDisabled();
	//回显修改标记
	markvalue();
	//回显从表信息
	objListvalue();
	//状态回显
	var ztText = DicAndEnumUtil.getEnumName('melStatusEnum',$("#zt").val());
	$("#ztText").val(ztText);
	
});

function markvalue(){
	var xgbj = $("#xgbj").val();
	if(xgbj){
		var arr = xgbj.split(",");
		$.each(arr,function(index,v){
			$("input:checkbox[name='xgbj'][value='"+v+"']").attr("checked",true);
		});
		
	}
}

function textDisabled(){
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	$('.page-content select').attr('disabled',true); 
}

//下载
function openddownfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function objListvalue (){
	var id = $("#melId").val();
	var jx = $("#jx").val();
	var dprtcode = $("#melDprtcode").val();
	//显示附件
	evaluation_modal.init({
		isShowed : false,
		zlid : id,
		zlxl : 7,
		dprtcode :dprtcode,
		jx : $("#jx").val()
	});
	//显示附件
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:id,
		fileHead : false,
		colOptionhead : false,
		fileType:"card"
	});
	
	var zt = $("#zt").val();
	var zdrName = $("#zdrName").val();
	var zdsj = $("#zdsj").val();
	var shyj = $("#shyj").val();
	var shrName = $("#shrName").val();
	var shsj = $("#shsj").val();
	var shjl = $("#shjl").val();
	var pfyj = $("#pfyj").val();
	var pfrName = $("#pfrName").val();
	var pfsj = $("#pfsj").val();
	var pfjl = $("#pfjl").val();
	introduce_process_info_class.show({ // 流程信息
		status : 5,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
		prepared : zdrName?zdrName:'',// 编写人
		preparedDate : zdsj,// 编写日期
		checkedOpinion : shyj,// 审核意见
		checkedby : shrName? shrName: '',// 审核人
		checkedDate : shsj,// 审核日期
		checkedResult : shjl,// 审核结论
		approvedOpinion : pfyj,// 批准意见
		approvedBy : pfrName ? pfrName : '',// 批准人
		approvedDate : pfsj,// 批准日期
		approvedResult : pfjl ,// 审批结论
	});
	
}