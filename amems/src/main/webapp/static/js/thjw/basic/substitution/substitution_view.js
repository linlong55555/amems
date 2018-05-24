
function initInformation(){
	AjaxUtil.ajax({
		   url:basePath+"/basic/substitution/selectById",
			 type:"post",
			 async: true,
			 data:{
				 'id' : id
			 },
			 dataType:"json",
			 success:function(data){
                 if(data){
                	 appendHtml(data);
                 }else{
                	 AlertUtil.showMessage('未查到相关数据');
                 }
	      }
	    });	
}

function appendHtml(row){
	// 件号
	var bjh = row.bjh;
	// 替代件号
	var tdjh = row.tdjh;
	// 描述
	var ms = row.ms;
	// 替代描述
	var tdjms = row.tdjms;

	// 机器适用性
	var jxbs = [];
	// 工卡适用性
	var gkbs = [];

	var applicabilityList = row.applicabilityList;
	if (applicabilityList instanceof Array) {
		if (applicabilityList && applicabilityList.length >= 2) {
			for ( var i = 0; i < applicabilityList.length; i++) {
				if (applicabilityList[i].syxlx == "1") {
					jxbs.push(applicabilityList[i].sydx);
				} else if (applicabilityList[i].syxlx == "2") {
					gkbs.push(applicabilityList[i].sydx);
				}
				if (jxbs.length == applicabilityList.length) {
					gkbs.push("通用currency");
				}
				if (gkbs.length == applicabilityList.length) {
					jxbs.push("通用currency");
				}
			}

		} else if (applicabilityList && applicabilityList.length == "1") {
			var applicability = applicabilityList[0];
			if (applicability.syxlx == "1") {
				jxbs.push(applicability.sydx);
				gkbs.push("通用currency");
			} else if (applicability.syxlx == "2") {
				gkbs.push(applicability.sydx);
				jxbs.push("通用currency");
			}
		} else {
			jxbs.push("通用currency");
			gkbs.push("通用currency");
		}
	}

	// 是否可逆
	var knxbs = row.knxbs;
	if (knxbs == "2") {
		knxbs = "是";
	} else if (knxbs == "1") {
		knxbs = "否";
	}

	$("#bjh").val(bjh);
	$("#tdjh").val(tdjh);
	$("#ms").val(ms);
	$("#tdjms").val(tdjms);
	$("#jxbs").val(jxbs.join(","));
	$("#gkbs").val(gkbs.join(","));
	$("#knxbs").val(knxbs);

	var attachmentsObj = attachmentsUtil
			.getAttachmentsComponents("dxtd_modal_attachments_list_edit");
	attachmentsObj.show({
		djid : id,
		fileHead : false,
		colOptionhead : false,
		fileType : "substitution"
	});//显示附件
	$("#alertModalShow").modal("show");
}