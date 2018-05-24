$(document).ready(function(){
	$('.modal-new').on('show.bs.modal', function (e) {
		if($(this).is(":visible")){
			return;
		}
		$('.modal-new').removeClass("active");
		$(this).addClass("active");
		var $activeModal = $(".modal-new:visible");
		if($activeModal.length > 0){
			$(this).attr("deep", $activeModal.length + 1);
			$(this).css("padding-top",$activeModal.length * 55 + "px");
		}else{
			$(this).attr("deep", 1);
		}
	});
	
	$('.modal-new').on('shown.bs.modal', function (e) {
		var windowHeight = $(window).height()
		//modal-footer的高度
		var modalFooterHeight = $(this).find(".modal-footer")
				.outerHeight() || 0;
		//modal-header 的高度
		var modalHeaderHeight = $(this).find(".modal-header")
				.outerHeight() || 0;
		//modal-body 的设置
		var modalBodyHeight = windowHeight - modalFooterHeight
				- modalHeaderHeight - (($(this).attr("deep")-1) * 55* 2) -55 - 16;
		var $tableContent = $(this).find(".table-content");
		if($tableContent.length > 0){
			var modalSearch = $(".modalSearch", $(this)).outerHeight() || 0;
			var modalpagination = $(".page-height", $(this)).outerHeight() || 0;
			$tableContent.attr('style','max-height:' + (modalBodyHeight - modalSearch - modalpagination - 20) + 'px !important;overflow: auto;');
			$tableContent.prop('scrollTop','0');
		}else{
			//是否有表头固定信息EO页面
			if($(".fixedModalHeader").length>0){
				$modalBody = $(this).find(".modal-body");
				var len=$(".fixedModalHeader:visible").length;
				var fixedModalHeader=0
				for(var i=0;i<len;i++){
					fixedModalHeader+=$(".fixedModalHeader:visible").eq(i).outerHeight();
				}
			/*	var fixedModalHeader=$(".fixedModalHeader").outerHeight();*/
				
				var lenHeight= (len-1)*8
				
				var nonFixedBody=modalBodyHeight-fixedModalHeader-24-lenHeight;
				 // chrome浏览器
	            if(navigator.userAgent.indexOf("Chrome") > -1){
	            	nonFixedBody -= 10;
	            }
				$modalBody.attr('style' , "max-height:"+modalBodyHeight + 'px !important;overflow: auto;margin-top:0px;');
				$(this).find(".nonFixedContent").attr('style' , "max-height:"+nonFixedBody + 'px !important;overflow: auto;');
				$(this).find(".nonFixedContent").prop('scrollTop','0');
			}else{
				$modalBody = $(this).find(".modal-body");
				$modalBody.attr('style' , "max-height:"+modalBodyHeight + 'px !important;overflow: auto;margin-top:0px;');
				$modalBody.prop('scrollTop','0');
			}
			
		}
		//各页面自定义
        try{
        	customAlertResizeHeight(modalBodyHeight);
        }catch(e){}
	});
	
	$('.modal-new').on('hidden.bs.modal', function (e) {
		var $activeModal = $(".modal-new:visible");
		if($activeModal.length > 0){
			$activeModal.filter("[deep='"+($(this).attr("deep")-1)+"']").addClass("active");
		}
		//各页面自定义
        try{
        	hideAlertResizeHeight();
        }catch(e){}
	});
});


/**
 * 提示框工具类
 * 
 */
var AlertUtil = {
	hiddenComplete:false,
	hideComplete:false,

	/**
	 * 描述：弹出提示信息
	 * 参数 message提示信息,url提示完跳转地址
	 */
	showMessage:function(message,url){
		 
		$("#alertModalBody").html(message);
		$("#alertModal").modal("show");
		
		if(url != null){
			$('#alertModal').on('hidden.bs.modal', function (e) {
				if(url.indexOf("?") >= 0){
					window.location = basePath+url+"&t=" + new Date().getTime();
				}else{
					window.location = basePath+url+"?t=" + new Date().getTime();
				}
			});
			$('#alertModal').on('hide.bs.modal', function (e) {
				if(url.indexOf("?") >= 0){
					window.location = basePath+url+"&t=" + new Date().getTime();
				}else{
					window.location = basePath+url+"?t=" + new Date().getTime();
				}
			});
		}
	},	
	
	/**
	 * 描述：弹出错误提示信息
	 * 参数 message提示信息,url提示完跳转地址
	 */
	showErrorMessage:function(message,url){
		
		$("#alertErrorModalBody").html(message);
		$("#alertErrorModal").modal("show");
		
		if(url != null){
			$('#alertErrorModal').on('hidden.bs.modal', function (e) {
				if(url.indexOf("?") >= 0){
					window.location = basePath+url+"&t=" + new Date().getTime();
				}else{
					window.location = basePath+url+"?t=" + new Date().getTime();
				}
			});
		}
	},
	
	/**
	 * 描述：弹出错误提示信息
	 * 参数 message提示信息,回调函数refreshPage(),注意:调用此方法需要调用回调函数
	 */
	showAfterMessage:function(message){
		
		$("#alertAfterModalBody").html(message);
		$("#alertAfterModal").modal("show");
	
	}
	/**
	 * 描述：提示框(可回掉)
	 * 参数  
	 */
	,showMessageCallBack:function(param){ 
		$("#alertModalBody").html(param.message);
		$("#alertModal").modal("show");
		
		if(param.callback != undefined && param.callback !=null){
			 var hiddenComplete = false;
			 var hideComplete = false;
			$('#alertModal').on('hidden.bs.modal', function (e) {
				hideComplete = true
				if(!hiddenComplete){
					param.callback(param.option);
					hiddenComplete = true;
				}
			});
			
			$('#alertModal').on('hide.bs.modal', function (e) {
				hiddenComplete = true;
				if(!hideComplete){
					param.callback(param.option);
					hideComplete = true;
				}
			});
		 
		}
	}
	/**
	 * 描述：确认提示框(可回掉)
	 * 参数  
	 */
	,showConfirmMessage:function(message,calbackOption){
		$("#alertConfirmModalBody").html(message);
		$("#alertConfirmModal").modal("show");
		
		$('#confirmY').off('click').on('click',function(){
			$("#alertConfirmModal").modal("hide");
			if(calbackOption){
				calbackOption.callback(calbackOption.option);
			}
		});
		
		$('#confirmN').off('click').on('click',function(){
			calbackOption = null;
			$("#alertConfirmModal").modal("hide");
			finishWait();//@author xu.yong
		});
		
		
	}
	,calbackOption:{option:{},callback:function(){}}
	//显示模态框底部的信息
	,showModalFooterMessage:function(message, modalId){
		var $modalfootertip = null;
		if(typeof(modalId) == "undefined"){
			$modalfootertip = $(".modalfootertip:visible");
		}else{
			$modalfootertip = $(".modalfootertip", $("#"+modalId));
		}
		if($modalfootertip.length > 0){
			$modalfootertip.find("i").show();
			var $p = $modalfootertip.find("p");
			$p.attr("title", message);
			$p.text(message);
			return true;
		}
		return false;
		
	}
	//隐藏模态框底部信息
	,hideModalFooterMessage:function(modalId){
		
		var $modalfootertip = null;
		if(typeof(modalId) == "undefined"){
			$modalfootertip = $(".modalfootertip");
		}else{
			$modalfootertip = $(".modalfootertip", $("#"+modalId));
		}
	    $modalfootertip.find("i").hide();
		$modalfootertip.find("p").empty("");
	}
};



$(document).ready(function(){
//	$('#confirmY').off("click").on('click',function(){ 
//		$("#alertConfirmModal").modal("hide");
//		console.log("===================");
//		AlertUtil.calbackOption.callback(AlertUtil.calbackOption.option);
//	});
//
//	$('#confirmN').on('click',function(){
//		AlertUtil.isLoad = false;
//		$("#alertConfirmModal").modal("hide");
//		finishWait();//@author xu.yong
//	});
});
	

