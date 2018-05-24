ShutDownModal = {
	id:'ShutDownModal',
	chinaHeadId:'chinaHead',
	englishHeadId:'englishHead',
	titleName:'titleName',
	titleEname:'titleEname',
	jsr:'vjsr',
	jssj:'vjssj',
	jsyy:'vjsyy',
	zt:2,
	confirmId:'saveData',
	param: {
		chinaHead:'指定单号',//单号中文名称
		englishHead:'Colse No.',//单号英文名称
		edit:false,//是否可编辑,可编辑：关闭操作;不可编辑：查看关闭详情
		djid:'',//单据id
		jsdh:'',//保留单号
		jsr:'',//工作者
		jssj:'',//工作日期
		jsyy:'',//纠正措施及说明
		callback:function(){}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initData();
		AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		
		
		$("#"+this.id).modal("show");
	},
	initData : function(){
		$("#ejsdh").val("");
		$("#ezdjsr").val("");
		$("#ezdjsrq").val("");
		$("#ezdjsyy").val("");
		$("#"+this.titleName,$("#"+this.id)).html(this.param.titleName);
		$("#"+this.titleEname,$("#"+this.id)).html(this.param.titleEname);
		$("#"+this.chinaHeadId,$("#"+this.id)).html(this.param.chinaHead);
		$("#"+this.englishHeadId,$("#"+this.id)).html(this.param.englishHead);
		this.showOrHideHead();//显示或隐藏结束人、结束时间
		this.setEffectorDisabled();
		this.loadData();//加载数据
	},
	showOrHideHead : function(){
		if(this.param.edit){
			$("#baocun").show();
			$("#"+this.jsr,$("#"+this.id)).hide();
			$("#"+this.jssj,$("#"+this.id)).hide();
			$("#"+this.jsr,$("#"+this.id)).hide();
			$("#"+this.jsyy,$("#"+this.id)).show();
			$("#"+this.confirmId,$("#"+this.id)).show();
		}else{
			$("#baocun").hide();	
			$("#"+this.jsr,$("#"+this.id)).show();
			$("#"+this.jssj,$("#"+this.id)).show();
			$("#"+this.jsyy,$("#"+this.id)).hide();
			$("#"+this.confirmId,$("#"+this.id)).hide();
		}
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit2');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	setEffectorDisabled : function(){
		if(this.param.edit){
			this.getAttachments(null,true,true);
			
			$(".disabledgb", $("#"+this.id)).removeAttr("disabled");
		}else{
			this.getAttachments(this.param.djid,false,false);
			$(".disabledgb", $("#"+this.id)).attr("disabled",'true');
		}
	},
	loadData : function(){
		$("#ejsdh",$("#"+this.id)).val(this.param.jsdh);
		$("#ezdjsr",$("#"+this.id)).val(this.param.jsr);
		$("#ezdjsrq",$("#"+this.id)).val(this.param.jssj);
		$("#ezdjsyy",$("#"+this.id)).val(this.param.jsyy);
	},
	vilidateData : function(){
		var zdjsyy = $.trim($("#ezdjsyy").val());
		if(null == zdjsyy || '' == zdjsyy){
			AlertUtil.showModalFooterMessage("请输入关闭保留缺陷措施!");
			return false;
		}
		return true;
	},
	setData: function(){
		if(!this.vilidateData()){
			return false;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data={};
			data.gzz=$.trim($("#ezdjsr").val());//工作者
			data.gzrq=$.trim($("#ezdjsrq").val());//工作日期
			data.gbyy=$.trim($("#ezdjsyy").val());//关闭原因
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit2');//获取附件数据
			data.attachmentList=attachmentsObj.getAttachments();//加载附件数据
			this.param.callback(data);
		}
	}
}
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});