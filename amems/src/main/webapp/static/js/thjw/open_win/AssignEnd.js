AssignEndModal = {
	id:'AssignEndModal',
	chinaHeadId:'AssignEndModal_chinaHead',
	englishHeadId:'AssignEndModal_englishHead',
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
		titleName:'关闭',
		titleEname:'Close',
		jsdh:'',//关闭单号
		jsr:'',//关闭人
		zt:9,//状态
		jssj:'',//关闭时间
		jsyy:'',//关闭原因
		showType:true,//是否显示关闭类型 默认显示
		attachemtShow:false,//是否显示上传附件，默认不显示
		callback:function(){}
	},
	show : function(param){
		this.param.attachemtShow = false;//防止同一页面多个地方引用导致有的传参数有的没有传参数（如eo）
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
		$("#ezdjsyy",$("#"+this.id)).val("");
	
		
		$("#"+this.titleName,$("#"+this.id)).html(this.param.titleName);
		$("#"+this.titleEname,$("#"+this.id)).html(this.param.titleEname);
		$("#"+this.chinaHeadId,$("#"+this.id)).html(this.param.chinaHead);
		$("#"+this.englishHeadId,$("#"+this.id)).html(this.param.englishHead);
		this.loadData();//加载数据
		this.showOrHideHead();//显示或隐藏结束人、结束时间
		this.showType();//显示隐藏关闭类型
		this.showAttachment();//显示隐藏上传附件
		this.setEffectorDisabled();
	},
	showAttachment:function(){
		if(this.param.attachemtShow){
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
			attachmentsObj.show({
				djid:'',
				fileHead : this.param.operation,
				colOptionhead : this.param.operation,
				fileType:"eo",
			});//显示附件
			$("#"+this.id+"_attachments_list_edit").show();
		}else{
			$("#"+this.id+"_attachments_list_edit").hide();
		}
	},
	showType : function(){
		if(this.param.showType){
			$("#"+this.id+"_gblx").show();
		}else{
			$("#"+this.id+"_gblx").hide();
		}
	},
	showOrHideHead : function(){
		if(this.param.edit){
			$("#baocun").show();
			$("#"+this.jsr,$("#"+this.id)).hide();
			$("#"+this.jssj,$("#"+this.id)).hide();
			$("#"+this.jsyy,$("#"+this.id)).show();
			if(this.param.zt==2||this.param.zt==3||this.param.zt==10){//当状态为已评估，已审核时，状态不可编辑
				$(".closeModel").attr("disabled",true);
			}else{
				$(".closeModel").attr("disabled",false);
			}
			$("#"+this.confirmId,$("#"+this.id)).show();
		}else{
			$("#baocun").hide();
			$(".closeModel").attr("disabled",true);
			$("#"+this.jsr,$("#"+this.id)).show();
			$("#"+this.jssj,$("#"+this.id)).show();
			$("#"+this.jsyy,$("#"+this.id)).hide();
			$("#"+this.confirmId,$("#"+this.id)).hide();
			$("input:radio[name='zt'][value='"+this.param.zt+"']").attr("checked",true); 
		}
	},
	setEffectorDisabled : function(){
		if(this.param.edit){
			$("#ezdjsyy", $("#"+this.id)).removeAttr("disabled");
		}else{
			$("#ezdjsyy", $("#"+this.id)).attr("disabled",'true');
			$("input:radio[name='zt'][value='"+this.param.zt+"']").attr("checked",true);
		}
	},
	loadData : function(){
		$("#ejsdh",$("#"+this.id)).val(this.param.jsdh);
		$("#ezdjsr",$("#"+this.id)).val(this.param.jsr);
		$("#ezdjsrq",$("#"+this.id)).val(this.param.jssj);
		$("#ezdjsyy",$("#"+this.id)).val(this.param.jsyy);
		if(this.param.zt==2||this.param.zt==3||this.param.zt==10){//当状态为已评估，已审核时 ，状态默认为指定结束
			$("input:radio[name='zt'][value='9']").attr("checked",true); 
		}else{
			$("input:radio[name='zt'][value='10']").attr("checked",true); 
		}
	},
	vilidateData : function(){
		var zdjsyy = $.trim($("#ezdjsyy").val());
		if(null == zdjsyy || '' == zdjsyy){
			AlertUtil.showModalFooterMessage("请输入关闭原因!");
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
			data.gbyy=$.trim($("#ezdjsyy").val());//关闭原因
			data.zt=$("#"+this.id+" input:radio[name='zt']:checked").val(); //状态
			if(this.param.attachemtShow){
				var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
				data.attachments = attachmentsObj.getAttachments();				
			}
			this.param.callback(data);
		}
	}
}