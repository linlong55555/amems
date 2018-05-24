setting_effect_alert_Modal = {
	id : "setting_effect_alert_Modal",
	sqlxArr : ["","初次","延期","增加"],
	jxs : 0,
	param: {
		id:'',
		callback:function(){}
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this_.param, param);
		}
		this_.vilidateOpenPri(function(obj){
			$("#"+this_.id).modal("show");
			AlertUtil.hideModalFooterMessage();
			this_.initInfo(obj);
		});
	},
	/**
	 * 验证弹窗是否可以打开
	 */
	vilidateOpenPri : function(callback){
		var this_ = this;
		var url = basePath + "/quality/post/application/getByPostApplicationId";
		this_.performAction(url, {id : this_.param.id}, false, '',function(obj){
			if(obj != null){
 				if(obj.zt == 4 && obj.pgjl == 1){
 					this_.jxs = obj.paramsMap.jxs;
 					callback(obj);
 				}else{
 					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 				}
			};
		});
	},
	initInfo : function(obj){
		this.initDate();
		this.returnViewData(obj);
	},
	/**
	 * 初始化日期控件
	 */
	initDate : function(){
		$("#sqksrq_edit", $("#"+this.id)).removeClass("border-color-red");
		$("#sqjzrq_edit", $("#"+this.id)).removeClass("border-color-red");
		$("#sqksrq_edit", $("#"+this.id)).datepicker({
			format:'yyyy-mm-dd',
			autoclose : true
		});
		
		$("#sqjzrq_edit", $("#"+this.id)).datepicker({
			format:'yyyy-mm-dd',
			autoclose : true
		});
		
		$(".sqrqwin", $("#"+this.id)).hide();
		$(".jslbwin", $("#"+this.id)).hide();
		if(this.jxs > 0){
			$(".jslbwin", $("#"+this.id)).show();
		}else{
			$(".sqrqwin", $("#"+this.id)).show();
		}
	
	},
	returnViewData : function(data){
		var sqgw = StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgmc:'');
		$("#sqsqdh_view", $("#"+this.id)).val(data.sqsqdh);//授权申请单号
		$("#sqr_view", $("#"+this.id)).val(StringUtil.escapeStr(data.whr?data.whr.username:'')+" "+StringUtil.escapeStr(data.whr?data.whr.realname:''));//申请人
		$("#sqlx_view", $("#"+this.id)).val(StringUtil.escapeStr(this.sqlxArr[data.sqlx]));//申请类型
		$("#sqgw_view", $("#"+this.id)).val(sqgw);//申请岗位
		$("#sqksrq_edit", $("#"+this.id)).val(indexOfTime(data.yxqKs));
		$("#sqjzrq_edit", $("#"+this.id)).val(indexOfTime(data.yxqJs));
		
		if(this.jxs > 0){
			//初始化机型
			 plane_model_list_edit.init({
				id : 'setting_plane_model',
				djid:data.id,//关联单据id
				colOptionhead : true,
				dprtcode:data.dprtcode//默认登录人当前机构代码
			});
			 
			$(".left_column", $("#setting_plane_model")).removeClass("col-lg-1").addClass("col-lg-2");
			$(".right_column", $("#setting_plane_model")).removeClass("col-lg-11").addClass("col-lg-10");
			 
		}
		
	},
	close : function(){
		$("#" + this.id).modal("hide");
	},
	changeDate : function(obj){
		$(obj).removeClass("border-color-red");
	},
	vilidateData : function(){
		var sqksrq = $.trim($("#sqksrq_edit", $("#"+this.id)).val());
		var sqjzrq = $.trim($("#sqjzrq_edit", $("#"+this.id)).val());
		
		if(sqksrq == null || sqksrq == ''){
			AlertUtil.showModalFooterMessage("授权开始日期不能为空!", this.id);
			$("#sqksrq_edit", $("#"+this.id)).addClass("border-color-red");
			return false;
		}
		
		if(sqjzrq == null || sqjzrq == ''){
			AlertUtil.showModalFooterMessage("授权截止日期不能为空!", this.id);
			$("#sqjzrq_edit", $("#"+this.id)).addClass("border-color-red");
			return false;
		}
		
		if(TimeUtil.compareDate(sqksrq +" " + "00:00:00",sqjzrq +" " + "00:00:00")){
			AlertUtil.showModalFooterMessage("授权开始日期不能大于授权截止日期!");
			$("#sqksrq_edit", $("#"+this.id)).addClass("border-color-red");
			return false;
		}
		
		return true;
	},
	setData: function(){
		var this_ = this;
		var data = {};
		data.yxqKs = $.trim($("#sqksrq_edit", $("#"+this.id)).val());
		data.yxqJs = $.trim($("#sqjzrq_edit", $("#"+this.id)).val());
		if(this_.jxs > 0){
			//设置机型
			data.postApplicationSQJXList = plane_model_list_edit.getData();
			//验证机型
			if(!plane_model_list_edit.isValid){
				return false;
			}
		}else{
			if(!this_.vilidateData()){
				return false;
			}
			var postApplicationSQJXList = [];
			var infos ={};
			infos.id = "";
			infos.fjjx = "";
			infos.lb = "";
			infos.yxqKs = $("#sqksrq_edit", $("#"+this.id)).val();
			infos.yxqJs = $("#sqjzrq_edit", $("#"+this.id)).val();
			postApplicationSQJXList.push(infos);
			data.postApplicationSQJXList = postApplicationSQJXList;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
	},
	/**
	 * 执行请求
	 * url 请求路径
	 * param 请求参数
	 * async true异步，false同步
	 * modal 遮罩,防重复提交
	 * callback 回调函数
	 */
	performAction : function(url, param, async, modal, callback){
		var this_ = this;
		startWait(modal);
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			async: async,
			modal:modal,
			success:function(data){
				finishWait(modal);
				if(typeof(callback)=="function"){
					callback(data); 
				}
			}
		});
	}
	
}
