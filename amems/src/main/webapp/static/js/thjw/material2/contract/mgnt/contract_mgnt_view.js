$(document).ready(function(){
	Navigation(menuCode, '查看合同', 'View', '');//加载导航栏
	contract_view.init();
});

/**
 * 合同查看
 */
var contract_view = {
	id : "contract_view",
	init : function(){
		var this_ = this;
		var id = $("#id").val();
		this_.selectById(id,function(obj){
			//初始化信息
			this_.initInfo(obj);
		});
	},
	initInfo : function(obj){//初始化信息
		this.initBody(obj);
	},
	selectById : function(id,obj){//通过id获取数据
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/material/contract/selectById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(obj){
		this.returnViewData(obj);
	},
	returnViewData : function(obj){
		var this_ = this;
		$("#hth_v", $("#"+this_.id)).val(obj.hth);
		$("#htrq_v", $("#"+this_.id)).val(indexOfTime(obj.htrq));
		$("#htlx_v", $("#"+this_.id)).val(DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx));
		$("#bzrstr_v", $("#"+this_.id)).val(obj.paramsMap.bzrstr);
		$("#xgfms_v", $("#"+this_.id)).val(obj.xgfms);
		$("#bz", $("#"+this_.id)).val(obj.bz);
		$("#biz_v", $("#"+this_.id)).val(obj.biz);
		$("#jffs_v", $("#"+this_.id)).val(obj.jffs);
		$("#zt", $("#"+this_.id)).val(DicAndEnumUtil.getEnumName('contractMgntStatusEnum',obj.zt));
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : true,
			colOptionhead : false,
			fileType:"contract"
		});//显示附件
		
		this.loadDetail(obj);

	},
	loadDetail : function(obj){
		contract_main_detail.init(obj);
		mgnt_payment_main.init({
			mainid : obj.id,
			biz: obj.biz,
			zt : obj.zt,
			isView : true,
			callback: function(){}//回调函数
		});
	}
}
