var certificateUtil = {};
testing_open_alert = {
	id : "testing_open_alert",
	open: function(param){
		Navigation(menuCode, '查看航材工具', 'Select Quality Testing', 'SC12-2 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		var this_ = this;
		 this_.initDate($("#viewid").val());					// 初始化信息
		 this_.initCertificate();								// 初始化证书
		 this_.loadCertificate();								// 加载证书
	},
	/**
	 * 跳转到收货单
	 */
	onclickshd : function(){
	var shdid=$("#"+this.id+"_shdid").val();
	window.open(basePath+"/material/outin/receipt?id="+shdid+"&type=view");

	},
	loadMaintenanceProject : function(param){//加载维修项目
		maintenance_project_view.init({
			bjh : param.paramsMap?param.paramsMap.bjh:'',
			dprtcode:param.dprtcode//当前机构代码
		});
	},
	/**
	 * 初始化证书
	 */
	initCertificate : function(){
		var this_=this;
		certificateUtil = new CertificateUtil({
			parentId : this_.id,
			dprtcode : $("#"+this_.id+"_dprtcode").val(),
			tbody : $("#replace_certificate_list"),
			container : "body",
			ope_type : 3
		});
	},
	/**
	 * 加载证书
	 */
	loadCertificate : function(){
		var this_=this;
		certificateUtil.load({
			bjh : $("#"+this_.id+"_bjh").val(),
			xlh : $("#"+this_.id+"_sn").val(),
			pch : $("#"+this_.id+"_pch").val(),
			dprtcode : $("#"+this_.id+"_dprtcode").val(),
		});
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit1');
		attachmentsObj.show({
			djid:id,
			fileHead : false,
			colOptionhead : false,
			fileType:"inspection"
		});//显示附件
	},
	/**
	 * 加载数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath + "/material/inspection/getByInspectionId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
 				this_.setDate(data);// 加载数据
	 		}
		});
	},
	/**
	 * 加载数据
	 */
	setDate : function(data){
		var this_=this;
		$("#"+this_.id+"_id").val(data.id);
		$("#"+this_.id+"_dprtcode").val(data.dprtcode);
		$("#"+this_.id+"_jydh").val(data.jydh);
		$("#"+this_.id+"_hclx").val(DicAndEnumUtil.getEnumName('materialTypeEnum',data.paramsMap?data.paramsMap.hclx:''));
		$("#"+this_.id+"_bjh").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.bjh:''));
		$("#"+this_.id+"_bjmc").val(data.paramsMap?data.paramsMap.bjmc:'');
		$("#"+this_.id+"_gljb").val(DicAndEnumUtil.getEnumName('supervisoryLevelEnum', data.paramsMap?data.paramsMap.gljb:''));
		$("#"+this_.id+"_gljbzt").val(data.paramsMap?data.paramsMap.gljb:'');
		$("#"+this_.id+"_sn").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.sn:''));
		$("#"+this_.id+"_pch").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.pch:''));
		$("#"+this_.id+"_kysl").val(StringUtil.escapeStr(data.kysl)+" "+StringUtil.escapeStr(data.hcMainData.jldw));
		$("#"+this_.id+"_shdid").val(data.shdid);
		$("#"+this_.id+"_shdh").text(data.paramsMap?data.paramsMap.shdh:'');
		$("#"+this_.id+"_lydw").val(data.paramsMap?data.paramsMap.lydw:'');
		$("#"+this_.id+"_shrq").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.shrq:'').substring(0,10));
		$("#"+this_.id+"_wz").val(data.paramsMap?data.paramsMap.wz:'');
		if(data.hcly==null || data.hcly==''){
			 var SelectArr1 = $("#hclyDiv select"); //部件来源重置为第一个
			 SelectArr1[0].options[0].selected = true;
		}else{
			$("#"+this_.id+"_hcly").val(data.hcly);
		}
		$("#"+this_.id+"_grn").val(data.grn);
		$("#"+this_.id+"_scrq").val(StringUtil.escapeStr(data.scrq).substring(0,10));
		$("#"+this_.id+"_hjsm").val(StringUtil.escapeStr(data.hjsm).substring(0,10));
		$("#"+this_.id+"_tsn").val(TimeUtil.convertToHour(data.tsn, TimeUtil.Separator.COLON)||"");
		$("#"+this_.id+"_csn").val(data.csn);
		$("#"+this_.id+"_tso").val(TimeUtil.convertToHour(data.tso, TimeUtil.Separator.COLON)||"");
		$("#"+this_.id+"_cso").val(data.cso);
		$("#"+this_.id+"_cfyq").val(data.cfyq);
		$("#"+this_.id+"_bz").val(data.bz);
		$("#"+this_.id+"_jyrq").val(StringUtil.escapeStr(data.jyrq).substring(0,10));
		if(data.jyjg==null || data.jyjg==''){
			 var SelectArr1 = $("#jyjgDiv select"); //检验结果重置为第一个
			 SelectArr1[0].options[0].selected = true;
		}else{
			$("#"+this_.id+"_jyjg").val(data.jyjg);
		}
		$("#"+this_.id+"_jgsm").val(data.jgsm);
		this_.loadMaintenanceProject(data);
		this_.getAttachments(data.id,true,true);	//加载附件
	}
};

//下载
installationlist = {
		 downloadfile:function(id) {
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		}
}
/**
 *初始化当前js
 */
$(function(){
	testing_open_alert.open();
});