Fault_Handling_Record_Update_Modal = {
	id : "Fault_Handling_Record_Modal",
	open: function(param){
		var this_ = this;
		 $("#modalNameFault").html("修改故障处理记录");
		 $("#modalEnameFault").html("edit Fault Handling Record");
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		 $("#"+this_.id+"").modal("show");
		 Fault_Handling_Record_Open_Modal.validation(); 	//初始化验证
		 Fault_Handling_Record_Open_Modal.EmptiedData();	//清空数据
		 $("#hbbtn").attr("disabled",true);
		 $("input[name='hbh']").attr("disabled",true);
		 $("#hbrq").attr("disabled",true);
		 this.initInfo(param);				    			//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(id){
		var param={};
 		param.id=id;
 		AjaxUtil.ajax({
  			url : basePath+ "/produce/fault/monitoring/editInfo",
  			type : "post",
  			data : JSON.stringify(param),
  			contentType : "application/json;charset=utf-8",
  			dataType : "json",
  			success : function(data) {
  				$("#hbh").val(data.hbh);
  		 		$("#fxjldid").val(data.fxjldid);
  		 		$("#hbrq").val(data.hbrq.substring(0,10));
  		 		$("#zlh").val(data.zlh);
  		 		$("#zlhid").val(data.gdid);
  		 		$("#cxjxx").val(data.cxjxx);
  		 		$("#zsjxx").val(data.zsjxx);
  		 		$("#pgsl").val(data.pgsl);
  		 		$("#cljg").val(data.cljg);
  		 		$("#bz").val(data.bz);
  		 		$("#infoId").val(data.id);
  		 		
  				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit_handing');
				attachmentsObj.show({
					djid:id,
					fileHead : true,
					colOptionhead : true,
					fileType:"assessment"
				});//显示附件
  		 		
  			}
		});
	}
};