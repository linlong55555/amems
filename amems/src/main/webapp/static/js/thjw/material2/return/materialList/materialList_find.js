materialList_find_modal = {
	id : "materialList_find_modal",
	open: function(param){
		var this_ = this;
		 var viewid=$("#viewid").val();
		 this_.initDate(viewid);					//初始化信息
	},

	/**
	 * 加载数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath + "/material/return/getById",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			if(data!=null){
 			   		this_.setDate(data);// 加载数据
				};
	 		}
		});
	},
	/**
	 * 加载数据
	 */
	setDate : function(data){
		$("#tlrq").val(StringUtil.escapeStr(data.tlrq).substring(0,10));
		$("#tlr").val(StringUtil.escapeStr(data.whr?data.whr.id:''));
		$("#bjh").val(data.bjh);
		$("#bjmc").val(data.bjmc);
		$("#pch").val(data.pch);
		$("#xlh").val(data.xlh);
		$("#tlsl").val(data.tlsl);
		$("#sfky").val(StringUtil.escapeStr((data.sfky == 1) ? '可用':'不可用'));
		$("#bjly").val(StringUtil.escapeStr((data.bjly == 1) ? '库房':'飞机'));
		$("#wllb").val(DicAndEnumUtil.getEnumName('materialTypeEnum',data.wllb));
		$("#sm").val(data.sm);
		
		var htmlContent="";
		  $.each(data.receivingRelationshiplist,function(index,row1){
		    	if(row1.paramsMap?row1.paramsMap.sjkw:'' != ''){
		    		htmlContent += StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sjkw:'');//
		    	}else{
		    		htmlContent += StringUtil.escapeStr(data.paramsMap?data.paramsMap.ckh:'')+" "+StringUtil.escapeStr(data.paramsMap?data.paramsMap.kwh:'')+" "+StringUtil.escapeStr(data.paramsMap?data.paramsMap.lscfwz:'');//
		    	}
		    	
		    	htmlContent +="; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sl:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shr:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shrq:'').substring(0,10);
		    	htmlContent +="\n";
		  });
		  $("#sjhw").val(htmlContent);//
		
	}
};

/**
 *初始化当前js
 */
$(function(){
	materialList_find_modal.open();
	Navigation(menuCode, '查看航材退料清单', 'Select View', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
});