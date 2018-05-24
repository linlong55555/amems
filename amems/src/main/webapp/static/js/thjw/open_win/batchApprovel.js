
BatchApprovelModal = {
	id:'BatchApprovelModal',
	modelHeadChinaId:'modelHeadChina',
	modelHeadEnglishId:'modelHeadEnglish',
	personChinaId:'personChina',
	personEnglishlishId:'personEnglish',
	timeChinaId:'timeChina',
	timeEnglishId:'timeEnglish',
	approvalId:'approve_list',
	approvalNotId:'approve_not_list',
	param: {
		approvalArr:null,//审核或批准可操作的数据
		approvalNotArr:null,//审核或批准不可操作的数据
		isApprovel:false,//判断审核还是批准
		callback:function(){}
	},
	checkUserList : null,
	show : function(param){
		var this_=this;
		if(param){
			$.extend(this.param, param);
		}
		this.initData();
		$("#"+this.id).modal("show");
	},
	initData : function(){
		this.initInfo();//初始化审核或批准
		this.loadApproveData();//加载可操作的数据
		this.loadApproveNotData();//加载不可操作的数据
	},
	initInfo : function(){
		var this_ = this;
		if(this.param.isApprovel){
			$("#"+this.modelHeadChinaId,$("#"+this.id)).html("批量批准");
			$("#"+this.modelHeadEnglishId,$("#"+this.id)).html("Approval");
			$("#"+this.personChinaId,$("#"+this.id)).html("批准人");
			$("#"+this.personEnglishlishId,$("#"+this.id)).html("Approval By");
			$("#"+this.timeChinaId,$("#"+this.id)).html("批准时间");
			$("#"+this.timeEnglishId,$("#"+this.id)).html("Date");
			$("#isApprovel_li",$("#"+this.id)).html("下列单据状态为已审核状态，可以进行批准：");
			$("#isNotApprovel_li",$("#"+this.id)).html("下列单据状态不是已审核状态，不可进行批准：");
		}else{
			$("#"+this.modelHeadChinaId,$("#"+this.id)).html("批量审核");
			$("#"+this.modelHeadEnglishId,$("#"+this.id)).html("Checked");
			$("#"+this.personChinaId,$("#"+this.id)).html("审核人");
			$("#"+this.personEnglishlishId,$("#"+this.id)).html("Checked by");
			$("#"+this.timeChinaId,$("#"+this.id)).html("审核时间");
			$("#"+this.timeEnglishId,$("#"+this.id)).html("Date");
			$("#isApprovel_li",$("#"+this.id)).html("下列单据状态为提交状态，可以进行审核：");
			$("#isNotApprovel_li",$("#"+this.id)).html("下列单据状态不是提交状态，不可进行审核：");
		}
		
		//设置当前时间
		TimeUtil.getCurrentTime(function (data){
			$("#optionTime",$("#"+this_.id)).html(data);
		});
		$("#yj",$("#"+this_.id)).val('');
	},
	loadApproveData : function(){
		var approveStr = '<li>暂无数据</li>';
		if(this.param.approvalArr && this.param.approvalArr.length > 0){
			approveStr = '';
			$.each(this.param.approvalArr,function(index,bh){
				approveStr += "<li>" + StringUtil.escapeStr(bh) + "</li>";
			});
		}
		$("#"+this.approvalId,$("#"+this.id)).html(approveStr);
	},
	loadApproveNotData : function(){
		var approveNotStr = '<li>暂无数据</li>';
		if(this.param.approvalNotArr && this.param.approvalNotArr.length > 0){
			approveNotStr = '';
			$.each(this.param.approvalNotArr,function(index,bh){
				approveNotStr += "<li>" + StringUtil.escapeStr(bh) + "</li>";
			});
		}
		$("#"+this.approvalNotId,$("#"+this.id)).html(approveNotStr);
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	setData: function(){
		
		var this_ = this;
		var message = '将对可审核的单据进行操作，是否继续？';
		if(this_.param.isApprovel){
			message = '将对可批准的单据进行操作，是否继续？';
		}
		
		AlertUtil.showConfirmMessage(message, {callback: function(){
		
			var data = {};
			data.yj = $.trim($("#yj",$("#"+this_.id)).val());
			if(this_.param.callback && typeof(this_.param.callback) == "function"){
				this_.param.callback(data);
			}
			this_.close();
		
		}});
	},
	modalBodyHeight:function(id){
		//window高度
		var windowHeight = $(window).height()
	    //modal-footer的高度
		var modalFooterHeight=$("#"+id+" .modal-footer").outerHeight()||0;
		//modal-header 的高度
	    var modalHeaderHeight=$("#"+id+" .modal-header").outerHeight()||0;
		//modal-dialog的margin-top值
	    var modalDialogMargin=parseInt($("#"+id+" .modal-dialog").css("margin-top"))||0
	    //modal-body 的设置
		var modalBodyHeight=windowHeight-modalFooterHeight-modalHeaderHeight-modalDialogMargin*2-8;
	    $("#"+id+" .modal-body").attr('style', 'max-height:' + modalBodyHeight+ 'px !important;overflow: auto;margin-top:0px;');
	}
}
