$(document).ready(
		function() {
			// 导航
			Navigation(menuCode, '查看工包', 'View WorkPackage', 'gc_4_1', '岳彬彬', '2017-08-01', '岳彬彬',
					'2017-08-17');
			refreshPermission();
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('workpackage_view_attachments_list_edit');
			attachmentsObj.show({
				djid:$("#workpackageId").val(),
				fileHead : false,
				colOptionhead : false,
				fileType:"workpackage"
			});//显示附件
			//工单明细
			workorder_detail.init({
				workpackageId:$("#workpackageId").val(),
				operationShow:false,
				searchDiv:true,
			});
			sendRequest();
		});

function sendRequest(){
	var param={};
	param.id=$("#workpackageId").val();
	AjaxUtil.ajax({
		url : basePath + "/produce/workpackage145/getRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data != null){
				setData(data);
			}			
		}
	});
}

function setData(data){
	$("#fjzch").val(data.fjzch);
	$("#msn").val(data.fjxlh);
	$("#jx").val(data.fjjx);
	$("#gbbh").val(data.gbbh);
	$("#ms").val(data.gbmc);
	$("#zdrq").val(data.zdrq==null?"":data.zdrq.substring(0,10));
	$("#jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
	$("#jhjsrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
	$("#wxlx").val(data.wxlx);
	$("#yjzxdw").val(data.zxdw);
	var wbbs=data.wbbs;
	var xfdw = document.getElementsByName("xfdw_radio");
	for(var i=0;i<xfdw.length;i++){
		if(xfdw[i].value==wbbs){
			xfdw[i].checked='checked';
		}
	}
	if(wbbs==0){
		$("#xfdw").val(data.xfdw);
		$("#xfdw_bmdiv").show();
		$("#xfdw_khxxdiv").hide();			
	}else{
		$("#gzworkpackage_modal_khxx").val(data.xfdw);
		$("#xfdw_khxxdiv").show();
		$("#xfdw_bmdiv").hide();
	}
	$("#xmbh").val(data.project==null?"":data.project.xmbm+" "+data.project.xmmc);
	$("#gzyq").val(data.gzyq);
	$("#gbzt").val(DicAndEnumUtil.getEnumName('workpackageStatusEnum', data.zt));
	
	if(data.wgbs==1){
		$("#wp145wgfkdiv").show();
		$("#workpackage_feedback_sjwcrq").val(data.sjJsrq==null?"":data.sjJsrq.substring(0,16));
		$("#workpackage_feedback_sjksrq").val(data.sjKsrq==null?"":data.sjKsrq.substring(0,10));
		$("#workpackage_feedback_bm").val(data.sjZxdw);
		$("#workpackage_feedback_gzzd").val(data.sjZd);
		$("#workpackage_feedback_gzzname").val(data.sjGzz);
		$("#workpackage_feedback_jczname").val(data.sjJcz);
		$("#gbrdiv").hide();
		$("#gbrqdiv").hide();
		$("#gbyydiv").hide();
	}
	if(data.zt==10){
		$("#cName").html("完工关闭");
		$("#eName").html("Close")
		$("#gbrdiv").show();
		$("#gbrqdiv").show();
		$("#gbyydiv").show();
		$("#workpackage_feedback_sjwcrq").val(data.sjJsrq==null?"":data.sjJsrq.substring(0,16));
		$("#workpackage_feedback_sjksrq").val(data.sjKsrq==null?"":data.sjKsrq.substring(0,10));
		$("#workpackage_feedback_bm").val(data.sjZxdw);
		$("#workpackage_feedback_gzzd").val(data.sjZd);
		$("#workpackage_feedback_gzzname").val(data.sjGzz);
		$("#workpackage_feedback_jczname").val(data.sjJcz);
		$("#workpackage_close_gbr").val(data.gbr.username+" "+data.gbr.realname);
		$("#workpackage_close_gbrq").val(data.gbrq==null?"":data.gbrq.substring(0,10));
		$("#workpackage_close_gbyy").val(data.gbyy);
	}
	if(data.zt==9){
		$("#wp145zdjsdiv").show();
		$("#workpackage_end_gbr").val(data.gbr.username+" "+data.gbr.realname);
		$("#workpackage_end_gbrq").val(data.gbrq==null?"":data.gbrq.substring(0,10));
		$("#workpackage_end_gbyy").val(data.gbyy);
	}
}