/**
 * 145工单，来源任务号，完成时限
 */
Workorder145WcsxWin = {
	id:'workorder145_wcsx_Modal', //窗口ID
	baseId:"workorder145_wcsx", //基础ID
	tbodyId:'', //列表ID
	colOptionheadClass : "colOptionhead",
	param: {
		modalHeadCN : '',//窗口中文名称
		modalHeadENG : '',//窗口英文名称
		parentWinId : '',//父窗口ID
		colOptionhead : true,
		gdid:null,//工单id
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){},//回调函数
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	/**显示窗口*/
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	/**初始数据*/
	initInfo : function(){
		//初始窗口标题信息
		this.initModelHead();
		
		//显示窗口
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		
		//加载数据
		initJkxszWin("1",null,99);
	},
	/**初始化对话框头部*/
	initModelHead : function(){
		if('' != this.param.modalHeadCN){
			$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
			$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
		}
	},
	/**获取数据*/
	getData : function(){
		
	},
};