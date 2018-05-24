ConfirmModal = {
	id:'ConfirmModal',
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
		chinaHead:'关闭单号',//单号中文名称
		englishHead:'Colse No.',//单号英文名称
		edit:false,//是否可编辑,可编辑：关闭操作;不可编辑：查看关闭详情
		jsdh:'',//保留单号
		jsr:'',//完成人
		jssj:'',//完成时间
		jsyy:'',//完成原因
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
		$("#ejsdh",$("#"+this.id)).val("");
		$("#ezdjsr",$("#"+this.id)).val("");
		$("#ezdjsrq",$("#"+this.id)).val("");
		$("#wcyy",$("#"+this.id)).val("");
		
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
			$("#baocun1").show();
			$("#"+this.jsr,$("#"+this.id)).hide();
			$("#"+this.jssj,$("#"+this.id)).hide();
			$("#"+this.jsyy,$("#"+this.id)).show();
			$("#"+this.confirmId,$("#"+this.id)).show();
		}else{
			$("#baocun1").hide();
			$("#"+this.jsr,$("#"+this.id)).show();
			$("#"+this.jssj,$("#"+this.id)).show();
			$("#"+this.jsyy,$("#"+this.id)).hide();
			$("#"+this.confirmId,$("#"+this.id)).hide();
		}
	},
	setEffectorDisabled : function(){
		if(this.param.edit){
			$("#wcyy", $("#"+this.id)).removeAttr("disabled");
		}else{
			$("#wcyy", $("#"+this.id)).attr("disabled",'true');
		}
	},
	loadData : function(){
		$("#ejsdh",$("#"+this.id)).val(this.param.jsdh);
		$("#ezdjsr",$("#"+this.id)).val(this.param.jsr);
		$("#ezdjsrq",$("#"+this.id)).val(this.param.jssj);
		$("#wcyy",$("#"+this.id)).val(this.param.jsyy);
	},
	vilidateData : function(){
		var zdjsyy = $.trim($("#wcyy").val());
		if(null == zdjsyy || '' == zdjsyy){
			AlertUtil.showModalFooterMessage("请输入指定结束原因!");
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
			data.gbyy=$.trim($("#wcyy").val());//关闭原因
			this.param.callback(data);
		}
	}
}