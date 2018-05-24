
importModal = {
	param: {
		importUrl:null,
		downloadUrl:null,
		callback:function(){},
		beforeImport:function(){return true;}
	},
	show : function(){
		this.clearData();//清空数据
		$("#ImportModal").modal("show");
	},
	clearData : function(){
		$("#excelName").val('');
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		$("#excelImporter").uploadFile({
	    	url:basePath+param.importUrl,
	    	multiple:false,
	    	dragDrop:false,
	    	showStatusAfterSuccess: false,
	    	showFileSize: false,
	    	showDelete: false,
	    	showAbort: false,
	    	fileName: "excelFile",
	    	formData : param.formData,
	    	returnType: "json",
	    	removeAfterUpload:true,
	    	/*uploadStr:"<div class=\"font-size-12\">导入</div><div class=\"font-size-9\">Import</div>",
	    	uploadButtonClass: "ajax-file-upload_ext",*/
	        uploadStr:"<i class='fa fa-upload'></i>",
			uploadButtonClass:"btn btn-default btn-import-uploadnew",
	    	onSelect: function (files) {
	    		var fileName = files[0].name;
	    		var arr = fileName.split("\.");
	    	    var suffix = arr[arr.length-1];
	    	    if(suffix != 'xlsx' && suffix != 'xls'){
	    	    	AlertUtil.showErrorMessage("请选择一个excel文件");
	    	    	return false;
	    	    }
	    	    // 调用回调函数
	    		if(importModal.param.beforeImport && typeof(importModal.param.beforeImport) == "function"){	
	    			return importModal.param.beforeImport();
	    		}
	    	    return true;
	        },
	    	onSubmit: function (files, xhr) {
	    		startWait($("#ImportModal"));
	    		$("#excelName").val(files[0]);
	    	},
	    	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	    	{
	    		finishWait($("#ImportModal"));
	    		// 弹出操作结果信息
	    		if(data.message){
	    			AlertUtil.showErrorMessage(data.message);
	    		}
	    		// 调用回调函数
	    		if(importModal.param.callback && typeof(importModal.param.callback) == "function"){	
    				importModal.param.callback(data);
	    		}
	    	}
	    }); 
		
		if(this.param.downloadUrl){
			$("#downloadDiv").show();
			$("#downloadDiv a").off("click").click(function(){
				window.open(basePath + param.downloadUrl);
			});
		}else{
			$("#downloadDiv").hide();
		}
	}
}