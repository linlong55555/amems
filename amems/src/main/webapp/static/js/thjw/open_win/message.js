
MessageModal = {
	id:'MessageModal',
	param: {
		viewObj:null,
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}
	},
	checkUserList : null,
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();//清空数据
		//回显数据
		if(this.param.viewObj){
			this.returnViewData(this.param.viewObj);
		}
		$("#"+this.id).modal("show");
	},
	clearData : function(){
		$("#mjsrName").val('');
		$("#mnbwjm").val('');
		this.checkUserList = [];
	},
	returnViewData : function(obj){
		$("#mjsrName").val(obj.jsrName);
		$("#mnbwjm").val(obj.nbwjm);
	},
	openUserModalWin : function(){
		var this_ = this;
		var userId = $.trim($("#mjsrid").val());
		var dprtcode = this.param.dprtcode;
		UserMultiModal.show({
			checkUserList:this_.checkUserList,//原值，在弹窗中回显
			dprtcode:dprtcode,//
			callback: function(data){//回调函数
				this_.checkUserList = [];
				var mjsrName = '';
				if(data != null && data != ""){
					this_.checkUserList = data;
					$.each(data, function(i, row){
						mjsrName += row.displayName + ",";
					})
				}
				if(mjsrName != ''){
					mjsrName = mjsrName.substring(0,mjsrName.length - 1);
				}
				$("#mjsrName").val(mjsrName);
			}
		})
	},
	vilidateData : function(){

		var mjsrName = $.trim($("#mjsrName").val());
		var mnbwjm = $.trim($("#mnbwjm").val());
		if(null == mjsrName || "" == mjsrName){
			AlertUtil.showMessageCallBack({
				message : '请选择提醒人!',
				option : 'mjsrName',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		if(null == mnbwjm || "" == mnbwjm){
			AlertUtil.showMessageCallBack({
				message : '请输入留言内容!',
				option : 'mnbwjm',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}

		var message = {
			nbwjm : $.trim($("#mnbwjm").val())
		};
		
		var messageRecipientsList = [];
		$.each(this.checkUserList, function(i, row){
			var messageRecipients = {
					jsrid : row.id,
					jsbmid : row.bmdm
				};
			messageRecipientsList.push(messageRecipients);
		});
		message.messageRecipientsList = messageRecipientsList;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(message);
		}
		$("#"+this.id).modal("hide");
	}
	
}