/**
 * 等效替代
 */
open_win_equivalent_substitution = {
	id:'open_win_equivalent_substitution',
	param: {
		id : '',
		bjh : '',
		tdjh : '',
		isParamId : true,//参数是否为id
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.load();
	},
	//加载数据
	load : 	function(){
		var this_ = this;
		var url = basePath+"/basic/substitution/selectById";
		var param = {};
		if(this_.param.isParamId){
			param.id = this_.param.id;
		}else{
			url = basePath+"/basic/substitution/getSubByBjhAndTdjhAndDprt";
			param.bjh = this_.param.bjh;
			param.tdjh = this_.param.tdjh;
			param.dprtcode = this_.param.dprtcode;
		}
		startWait();
		AjaxUtil.ajax({
			url: url,
			type: "post",
			dataType:"json",
			data:param,
			success:function(data){
				finishWait();
				data = data?data:{};
				this_.returnData(data);
		    }
		}); 
	},	
	returnData: function(obj){
		var this_ = this;
		$("#subjh",$("#"+this_.id)).val(formatUndefine(obj.bjh));
		$("#sutdjh",$("#"+this_.id)).val(formatUndefine(obj.tdjh));
		$("#sums",$("#"+this_.id)).text(formatUndefine(obj.ms));
		$("#sutdjms",$("#"+this_.id)).text(formatUndefine(obj.tdjms));
		$("#suknxbs",$("#"+this_.id)).val(formatUndefine(obj.knxbs));
		$("input:radio[name='knxbs'][value='"+obj.knxbs+"']", $("#"+this_.id)).attr("checked",true);
		
		var jxbs = '';
		var gkbs = '';
		if(obj.applicabilityList != null){
			$.each(obj.applicabilityList,function(index,row){
				if(row.syxlx==1){
					jxbs += StringUtil.escapeStr(row.sydx) + ",";
				}else{
					gkbs += StringUtil.escapeStr(row.sydx) + ",";
				}
			});
			if(jxbs != ''){
				jxbs = jxbs.substring(0,jxbs.length - 1);
			}
			if(gkbs != ''){
				gkbs = gkbs.substring(0,gkbs.length - 1);
			}
		}
		if(obj.jxbs == '00000'){
			jxbs = '通用';
		}
		if(obj.gkbs == '00000'){
			gkbs = '通用';
		}
		$("#sujxbs",$("#"+this_.id)).val(jxbs);
		$("#sugkbs",$("#"+this_.id)).val(gkbs);
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents("attachments_list_view");
		attachmentsObj.show({
			djid:obj.id,
			fileHead : true,
			colOptionhead : false,
			fileType:"substitution",
			chinaHead:"等效替代评估单",
		    englishHead : 'Attachments'
		});//显示附件
		
	},
	close : function(){
		$("#"+this.id).modal("hide");
	}
};