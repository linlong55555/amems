
EOCloseModal = {
	id:'EOCloseModal',
	param: {
		zt:9,//9指定完成  10正常结束
		callback:function(){}
	},
	show : function(param){
		var this_=this;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		$("#"+this.id).on('shown.bs.modal', function () {
			this_.modalBodyHeight(this.id)
		});
		
		this.initHideShow();
	},
	initHideShow : function(){
		var ckFlag = false;
		
		$("#eoclose9div").css("display","none");
		$("#eoclose10div").css("display","none");
		$("#eoclose9").removeAttr("checked");
		$("#eoclose10").removeAttr("checked");
		
		if(this.param.zt == 2 || this.param.zt == 3 || this.param.zt == 7){
			$("#eoclose9div").css("display","block");
			if(!ckFlag){
				$("#eoclose9").attr("checked","true");
				ckFlag = true;
			}
		}
		
		if(this.param.zt == 7){
			$("#eoclose10div").css("display","block");
			if(!ckFlag){
				$("#eoclose10").attr("checked","true");
				ckFlag = true;
			}
		}
		
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
	},
	doClose : function(){
		var reqData = {};
		reqData.zt = $('input[name="eoclose"]:checked').val();
		this.param.callback(reqData);
	}
}
