
MessageFormUtil = {
	id:'MessageFormUtil',
	checkUserList : [],
	clearData : function(){
		$("#majsrName").val('');
		$("#manbwjm").val('');
		this.checkUserList = [];
	},
	openUserModalWin : function(){
		var this_ = this;
		UserMultiModal.show({
			checkUserList:this_.checkUserList,//原值，在弹窗中回显
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
				$("#majsrName").val(mjsrName);
			}
		})
	},
	getData : function(){
		var message = {
			nbwjm : $.trim($("#manbwjm").val())
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
		return message;
	}
	
}