$(function(){

});
var certificateUtil = {};
var CertificateModal = {
	id : "CertificateModal",
	planeRegOption : '',
	attachmentSingle : {},
	isLoad:false,//是否加载
	param: {
		id:'',
		modalHeadChina : '编辑证书',
		modalHeadEnglish : 'Edit',
		viewObj:null,
		type:1,
		blOption:true,
		callback:function(){},
		bjh:'',
		xlh:'',
		pch:'',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this.initInfo();
		$("#" + this.id).modal("show");
	},
	//初始化信息
	initInfo : function(){
		var this_ = this;
		this_.modalEmpty();
		this_.initModelHead();
		this_.returnViewData(this_.param.viewObj);
		this_.initCertificate(this_.param.viewObj.dprtcode);
		this_.loadCertificate();
	},
	//标题
	initModelHead : function(){
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
		$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
	},
	//清空
	modalEmpty : function(){
		$("#bjh").val('');	
		$("#xingh").val('');	
		$("#xlh").val('');	
		$("#pch").val('');	
		$("#mc").val('');	
		$("#cjjh").val('');	
		$("#jhly").val('');	
		$("#zjh").val('');	
		$("#installationlist_certificate_list").empty('');	
		$("#installationlist_certificate_list").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");

	},
	returnViewData : function(obj){
		var this_ = this;
		$("#bjh").val(StringUtil.escapeStr(obj.bjh));	
		$("#xingh").val(StringUtil.escapeStr(obj.xingh));	
		$("#xlh").val(obj.paramsMap?obj.paramsMap.xlh:'');	
		$("#pch").val(obj.paramsMap?obj.paramsMap.pch:'');	
		$("#mc").val(StringUtil.escapeStr(displayName));	
		$("#cjjh").val(StringUtil.escapeStr(obj.cjjh));	
		$("#jhly").val(StringUtil.escapeStr(obj.jhly));	
		$("#zjh").val(StringUtil.escapeStr(obj.zjh)+" "+StringUtil.escapeStr(obj.paramsMap.zjhywms));	
		$("#dprtcode").val(StringUtil.escapeStr(obj.dprtcode));	
		this_.param.bjh = StringUtil.escapeStr(obj.bjh);
		this_.param.xlh = StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.xlh:'');
		this_.param.pch = StringUtil.escapeStr(obj.pch);
		this_.param.dprtcode = StringUtil.escapeStr(obj.dprtcode);
		this_.param.certificates = obj.certificates;
	},
	// 初始化证书
	initCertificate:function(dprtcode){
		certificateUtil = new CertificateUtil({
			parentId : 'CertificateModal',
			dprtcode : dprtcode,
			tbody : $("#installationlist_certificate_list"),
			container : '#CertificateModal',
			ope_type : 1
		});
	},
	// 加载证书
	loadCertificate:function(){
		var this_ = this;
		certificateUtil.load({
			bjh : this_.param.bjh,
			xlh : this_.param.xlh,
			pch : this_.param.pch,
			/*data :this_.param.certificates,*/
			dprtcode :this_.param.dprtcode
		});
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		var this_ = this;
		var obj = {};
		obj.bjh = this_.param.bjh;
		obj.xlh = this_.param.xlh;
		obj.pch = this_.param.pch;
		obj.dprtcode = this_.param.dprtcode;
		obj.certificates = certificateUtil.getData();
		//回调
		this_.param.callback(obj);
	},
	repeat: function(list){
		var folse = false;
		for(var a = 0;a<list.length-1;a++){
			for(var b = a+1;b<list.length;b++){
				if(list[a].jh == list[b].jh && list[a].xlh == list[b].xlh){
					folse = true;
				}
			}
		}
		return folse;
	},
	/**
	 * 组装数据
	 */
	getAircraftinfoData: function(){
		var  this_= this;
		var data = {};
		return data;
	}
}

