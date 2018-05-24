mel_detail_edit_alert_Modal = {
	id : "mel_detail_edit_alert_Modal",
	planeRegOption : '',
	attachmentSingle : {},
	param: {
		id:'',
		modalHeadChina : 'MEL清单',
		modalHeadEnglish : 'MEL',
		viewObj:null,
		blOption:true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this.initInfo();
		ModalUtil.showModal(this.id);
//		$("#" + this.id).modal("show");
	},
	initInfo : function(){
		this.attachmentSingle = {};
		this.initModelHead();
		this.initPlaneModel();
		this.returnViewData(this.param.viewObj,this.param.blOption);
	},
	initModelHead : function(){
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
		$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
	},
	initPlaneModel : function(){
		var this_ = this;
		var data = acAndTypeUtil.getACTypeList({DPRTCODE:this_.param.dprtcode});
	 	var option = '';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	  	 }
	 	$("#jx", $("#"+this_.id)).empty();
	 	$("#jx", $("#"+this_.id)).append(option);
	},
	returnViewData : function(obj,blOption){
		var this_ = this;
		if(obj.jx){
			$("#jx", $("#"+this_.id)).val(obj.jx);
		}
		$("#bb", $("#"+this_.id)).val(obj.bb);
		$("#melqdfjid", $("#"+this_.id)).val(formatUndefine(obj.melqdfjid));
		$("#melqdfjName", $("#"+this_.id)).val(obj.melqdfjName);
		$("#melId", $("#"+this_.id)).val(obj.id);
		if(obj.melqdfjid != null && obj.melqdfjid != ''){
			$("#melqdfjBox", $("#"+this_.id)).attr("checked",'true');//选中
		}
		this_.param.dprtcode = obj.dprtcode;
		//加载工卡附件
		attachmentsSingleUtil.initAttachment(
				"mel_attachments_single_edit"//主div的id
				,obj.attachment//附件
				,'card'//文件夹存放路径
				,blOption//true可编辑,false不可编辑
				);
 		this_.showOrHideAttach('melqdfjBox','mel_attachments_single_edit');
 		
	},
	showOrHideAttach : function(box,single){
		var this_ = this;
		if($('#'+box, $("#"+this_.id)).is(':checked')) {
			$('#'+single+' .singlefile', $("#"+this_.id)).show();
		}else{
			$('#'+single+' .singlefile', $("#"+this_.id)).hide();
		}
	},
	vilidateData : function(){
		var jx = $.trim($("#jx", $("#"+this_.id)).val());
		var bb = $.trim($("#bb", $("#"+this_.id)).val());
		var wcscmsYw = $.trim($("#wcscmsYw").val());
		if(null == jx || "" == jx){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		if(null == bb || "" == bb){
			AlertUtil.showErrorMessage("请输入版本!");
			return false;
		}
		return true;
	},
	setData: function(){
		var this_ = this;
		if(!this_.vilidateData()){
			return false;
		}
		var data = {};
		data.jx = $.trim($("#jx", $("#"+this_.id)).val());
		data.bb = $.trim($("#bb", $("#"+this_.id)).val());
		data.melqdfjid = $.trim($("#melqdfjid", $("#"+this_.id)).val());
		data.dprtcode = this_.param.dprtcode;
		if(this_.attachmentSingle != null && this_.attachmentSingle.wjdx != null){
			data.attachment = this_.attachmentSingle;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this_.id).modal("hide");
	}
	
}
//隐藏Modal时验证销毁重构
$("#mel_detail_edit_alert_Modal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
    $('#form').data('bootstrapValidator', null);
    formValidator();
});