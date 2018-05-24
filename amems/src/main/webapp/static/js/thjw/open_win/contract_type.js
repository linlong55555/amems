/**
 * 合同明细弹窗
 */
contract_type = {
	id:'contract_type',
	contractWinId : 'contract_view_Modal',
	param: {
		selected : null, //已经选择的
		idList : [], //已经选择的
		dprtcode : userJgdm,
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		AlertUtil.hideModalFooterMessage();
		this.initParam();
	},
	//初始化参数
	initParam: function(){
		//初始化表单验证
		if(!this.isLoad){
			this.initWebuiPopover();
			this.isLoad = true;
		}
		this.loadBody();
		this.setDataList();
	},
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('edit-contract','#'+this_.id,function(obj){
			return this_.getDataList(obj);
		}, 180);
	},
	getDataList : function(obj){
		return $("#"+this.contractWinId).html();
	},
	setDataList : function(){
		var this_ = this;
		var htlx = $("input:radio[name='c_type']:checked", $("#"+this_.id)).val(); ;
		var searchParam ={};
		var paramsMap = {};
		searchParam.dprtcode = this_.param.dprtcode; 
		searchParam.htlx = htlx;
		paramsMap.ztList = [1, 2, 4, 12];
		searchParam.paramsMap = paramsMap;
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/contract/queryModelList",
			type: "post",
			async : false,
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				$("#exists_contract_div", $("#"+this_.contractWinId)).empty();
				$("#contract_li", $("#"+this_.contractWinId)).hide();
				$("#contract_confirm_single_btn", $("#"+this_.id)).hide();
				$("#contract_confirm_btn", $("#"+this_.id)).hide();
				finishWait();
				if(data.length >0){
					$("#contract_li", $("#"+this_.contractWinId)).show();
					this_.appendContentHtml(data);
					$("#contract_confirm_btn", $("#"+this_.id)).show();
				}else{
					$("#contract_confirm_single_btn", $("#"+this_.id)).show();
				}
			}
	    }); 
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<li style='background:white;'><a href='javascript:void(0);' onclick="+this_.id+".operationContract('"+row.id+"') >"+StringUtil.escapeStr(row.hth)+"</a></li>";
		});
		$("#exists_contract_div", $("#"+this_.contractWinId)).html(htmlContent);
	},
	loadBody : function(){
		var this_ = this;
		var items = DicAndEnumUtil.getEnum("contractMgntTypeEnum");
		var html = '';
		$.each(items, function(index, item){
			var check = '';
			html += '<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">';
			if(item.id == this_.param.selected){
				check = 'checked="checked"';
			}
			html += '<input value="'+item.id+'" onclick="'+this_.id+'.setDataList()" style="vertical-align:text-bottom;" '+check+' name="c_type" type="radio">&nbsp;'+StringUtil.escapeStr(item.name)+'&nbsp;&nbsp;';
			html += '</label>';
		});
		$("#htlx_radio_div", $("#"+this.id)).html(html);
//		$("input:radio[name='sxkz'][value='"+obj.sxkz+"']", $("#"+alertMaterialFormId)).attr("checked",true); 
	},
	operationContract : function(id){
		var this_ = this;
		if(id == ''){
			this_.openWinAdd();
		}else{
			this_.selectById(id, function(obj){
				obj.htrq = indexOfTime(obj.htrq);
				obj.bzrstr = obj.paramsMap.bzrstr;
				if(obj.zt == 1){
					this_.openWinEdit(obj);
				}else if(obj.zt == 2 || obj.zt == 4 || obj.zt == 12){
					this_.openWinRevise(obj);
				}else{
					AlertUtil.showErrorMessage("该合同已更新，请刷新后再进行操作!");
					return;
				}
			});
		}
		this_.close();
	},
	openWinAdd : function(){
		var this_ = this;
		var htlx = $("input:radio[name='c_type']:checked", $("#"+this_.id)).val();
		var obj = {};
		obj.id = '';
		obj.htlx = htlx;
		obj.bzrstr = displayName;
		//初始化合同日期
		TimeUtil.getCurrentDate(function (htrq){
			obj.htrq = htrq;
			var modalHeadCN = '新增'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', htlx)+'合同';
			mgnt_add_alert_Modal.show({
		 		modalHeadCN : modalHeadCN,
				modalHeadENG : 'Add',
				editParam:1,//新增
				viewObj:obj,
				xqqdIdList : this_.param.idList,//需求清单id集合
				dprtcode:this_.param.dprtcode,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						var message = '保存成功!';
						if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
							message = '提交成功!';
						}
						var url = basePath+"/material/contract/save";
						this_.performAction(url, data, message, true);
					}
				}
			});
		});
	},
	openWinRevise : function(obj){//打开修订合同弹出框
		var this_ = this;
		var modalHeadCN = '修订'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx)+'合同';
		mgnt_add_alert_Modal.show({
	 		modalHeadCN : modalHeadCN,
			modalHeadENG : 'Revision',
			editParam:3,//修订
			viewObj:obj,
			xqqdIdList : this_.param.idList,//需求清单id集合
			dprtcode:this_.param.dprtcode,//机构代码
			callback : function(data) {//回调函数
				if (data != null) {
					data.id = obj.id;
					var message = '提交成功!';
					var url = basePath+"/material/contract/updateRevise";
					this_.performAction(url, data, message, true);
				}
			}
		});
	},
	openWinEdit : function(obj){//打开修改合同弹出框
		var this_ = this;
		var modalHeadCN = '编辑'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx)+'合同';
		mgnt_add_alert_Modal.show({
	 		modalHeadCN : modalHeadCN,
			modalHeadENG : 'Edit',
			editParam:2,//编辑
			viewObj:obj,
			xqqdIdList : this_.param.idList,//需求清单id集合
			dprtcode:this_.param.dprtcode,//机构代码
			callback : function(data) {//回调函数
				if (data != null) {
					data.id = obj.id;
					var message = '保存成功!';
					if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
						message = '提交成功!';
					}
					var url = basePath+"/material/contract/update";
					this_.performAction(url, data, message, true);
				}
			}
		});
	},
	performAction : function(url, param, message, isScrollTop){//执行编辑合同
		var this_ = this;
		startWait($("#mgnt_add_alert_Modal"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#mgnt_add_alert_Modal"),
			success:function(data){
				finishWait($("#mgnt_add_alert_Modal"));
				AlertUtil.showMessage(message);
				mgnt_add_alert_Modal.close();
			}
		});
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
	close : function(){
		$("#"+this.id).modal("hide");
	}
};