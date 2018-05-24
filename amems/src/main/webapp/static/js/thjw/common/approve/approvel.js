
ApprovelUtil = {
	id:'ApprovelUtil',
	param: {
		type:1,//1:新增,2:修改,3:审核,4:批准
		viewObj:{}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.init();
	},
	init : function(){
		$("#"+this.id).show();
		if(this.param.type == 1){
			$("#"+this.id).hide();
		}else if(this.param.type == 2){
			$("#shyj", $("#"+this.id)).attr("disabled","true");
			$("#pzyj", $("#"+this.id)).attr("disabled","true");
		}else if(this.param.type == 3){
			$("#shyj", $("#"+this.id)).removeAttr("disabled");
			$("#pzyj", $("#"+this.id)).attr("disabled","true");
		}else if(this.param.type == 4){
			$("#shyj", $("#"+this.id)).attr("disabled","true");
			$("#pzyj", $("#"+this.id)).removeAttr("disabled");
		}
		this.initDate(this.param.viewObj);
	},
	initDate : function(obj){
		$("#shyj").val(formatUndefine(obj.shyj));
		$("#shrname").text(formatUndefine(obj.shr?obj.shr.displayName:''));
		$("#shsj").text(formatUndefine(obj.shsj));
		
		$("#pzyj").val(formatUndefine(obj.pfyj));
		$("#pzrname").text(formatUndefine(obj.pyr?obj.pyr.displayName:''));
		$("#pzsj").text(formatUndefine(obj.pfsj));
	}
}
