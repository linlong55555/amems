audit_modal = {
	id:'audit_modal',
	param: {
		id:'',//id
		edit:true,//是否可编辑,可编辑：关闭操作;不可编辑：查看关闭详情
		titleName:"审核通过",
		titleEname:"Pass",
		bfdh:'',//关闭单号
		type:true,//审核驳回 审批通过 默认通过
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
		$("#audit_modal_bfdh",$("#"+this.id)).val("");
		$("#audit_modal_spyj",$("#"+this.id)).val("");
		$("#audit_modal_titleName").html(this.param.titleName);
		$("#audit_modal_titleEname").html(this.param.titleEname);
		if(this.param.type){
			$("#audit_modal_spyjRemark").hide();
		}else{
			$("#audit_modal_spyjRemark").show();
		}
		this.loadData();//加载数据
	},
	
	loadData : function(){
		$("#audit_modal_bfdh",$("#"+this.id)).val(this.param.bfdh);
	},
	
	setData: function(){
		var spyj =$.trim($("#"+this.id+"_spyj").val());
		if(!this.param.type){
			if(spyj == "" ){
				AlertUtil.showModalFooterMessage("请填写审批意见!");
				return false
			}
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data={};
			data.spyj=spyj;//审批意见
			data.id = this.param.id;
			data.bfdh=this.param.bfdh;
			this.param.callback(data);
		}
	}
}