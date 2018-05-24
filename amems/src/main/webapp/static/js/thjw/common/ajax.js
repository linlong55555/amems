var AjaxUtil = {
			ajax:function(options){
				 var defaults = {
						error : function(data) {
							 finishWait(options.modal);
							 if ('timeout' == data.responseText) {
								// AlertUtil.showErrorMessage('登录超时','/');
								 window.location.href = basePath;
							 }
							 else if ('noauth' == data.responseText) {
								 AlertUtil.showErrorMessage('请求地址受限，请联系管理员。');
							 }
							 else{
								 if(!AlertUtil.showModalFooterMessage(data.responseText)){
									 AlertUtil.showErrorMessage(data.responseText);
								 }
							 }
						}
				};
				var settings = $.extend(defaults, options);
				$.ajax(settings); 
			}
	};