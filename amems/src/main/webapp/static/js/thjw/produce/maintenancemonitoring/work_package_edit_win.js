/**
 * 工包明细
 */
work_package_edit_Modal = {
	id : "work_package_edit_Modal",
	isLoad:false,//是否加载
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	param: {
		obj : {},//工包对象
		fjzch : '',
		dprtcode : userJgdm,//组织机构
		gbid:'',//工包id
		parent_win_id : '',
		scroll_body : '',
		callback:function(){}
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this_.initInfo();
	},
	initInfo : function(){//初始化信息
		var this_ = this;
		$("#"+this.id).show();
		work_package_detail.init({
			gbid : this_.param.gbid,
			obj : this_.param.obj,//工包对象
			parent_win_id : this_.param.parent_win_id,
			scroll_body : this_.param.scroll_body
		});
	},
	hide : function(){
		$("#"+this.id).hide();
	}
}

