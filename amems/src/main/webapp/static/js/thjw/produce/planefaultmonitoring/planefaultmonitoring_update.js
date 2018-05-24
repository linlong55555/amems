Planefaultmonitoring_Update_Modal = {
	id : "Planefaultmonitoring_Open_Modal",
	open: function(param){
		var this_ = this;
		 $("#"+this_.id).find("#modalName").html("修改飞机故障监控");
		 $("#"+this_.id).find("#modalEname").html("Edit AircraftFault");
		 AlertUtil.hideModalFooterMessage();    	   	//初始化错误信息 
		 $("#"+this_.id+"").modal("show");
		 Planefaultmonitoring_Open_Modal.validation(); 	//初始化验证
		 Planefaultmonitoring_Open_Modal.EmptiedData();	//清空数据
		 this.initInfo(param);				    		//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(id){
		var this_=this;
		var param={};
 		param.id=id;
 		AjaxUtil.ajax({
  			url : basePath+ "/produce/fault/monitoring/edit",
  			type : "post",
  			data : JSON.stringify(param),
  			contentType : "application/json;charset=utf-8",
  			dataType : "json",
  			success : function(data){
  				$("#djid").val(data.id);
  				$("#"+this_.id+"_fjzch").val(data.fjzch);
  				$("#"+this_.id+"_fjzch").attr("disabled",true);//飞机注册号不可编辑
  				$("#gzxx").val(data.gzxx);
  				$("#gzxx").val(data.gzxx);
  				$("#mbz").val(data.bz);
  				$("#common_zjh_value").val(data.zjh);
  				$("#common_zjh_display").val(data.paramsMap.zjhms); 
  				//var chapter=getZjhDescription(data.zjh);
  				 /* if(chapter){
  					$("#common_zjh_display").val(chapter.zjh+" "+StringUtil.escapeStr(chapter.ywms)); 
  				  }else{
  					$("#common_zjh_display").val("");
  				  }*/
  				
  				$("#dprtcode1").val(data.dprtcode);
  				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list');
				attachmentsObj.show({
					djid:id,
					fileHead : true,
					colOptionhead : true,
					fileType:"Monitoring"
				});//显示附件
  		 		
  			}
		});
	}
};