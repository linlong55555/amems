/**
 * 完工反馈与拆换件记录
 */
feedback_replacement_tab_view = {
	id:'feedback_replacement_tab_view',
	param: {
		type : 1,
 		gdbh : '',
 		gdid : ''
	},
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.selectTab();
		this.loadData();
	},
	/**
	 * 选中tab标签
	 */
	selectTab : function(type){
		$(".child_li_div", $("#"+this.id)).removeClass("active in");
		if(this.param.type == 1){
			$("#feedback_win_tab", $("#"+this.id)).addClass("active in");
			$("#feedbackWinTab", $("#"+this.id)).addClass("active in");
		}
		if(this.param.type == 2){
			$("#replacement_win_tab", $("#"+this.id)).addClass("active in");
			$("#replacementWinTab", $("#"+this.id)).addClass("active in");
		}
		$("#feedbackWinTab #attach_div .panel-body").removeAttr("style");
		$("#feedbackWinTab #attach_div .panel-body").addClass("padding-top-0 padding-bottom-0");
	},
	/**
	 * 加载数据
	 */
	loadData : function(){
		//加载完工反馈和拆换件记录
	 	taskhistoryFeedbackMain.AjaxGetDatasWithSearch(this.param.gdid, this.id);
		taskhistoryRecordMain.AjaxGetDatasWithSearch(this.param.gdid, this.param.gdbh, this.id);
	},
	close : function(){
		$("#"+this.id).modal("hide");
	}
};

