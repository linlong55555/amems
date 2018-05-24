AddalertModal = {
	id : "AddalertModal",
	/**
	 * 初始化新增
	 */
	inItAdd: function(param){
		var this_ = this;
		$("#alertInfo").hide();
		$("#fjjx").attr("disabled",false);
		$("#fjjx").val("");//飞机机型
		$("#bz").val("");//备注
		$("#mark").val("1");//新增标识
		$("#"+this_.id+"_modalName").html("新增机型");
		$("#"+this_.id+"_modalEname").html("Add A/C Type");
		$("#"+this_.id+"").modal("show");
		AddalertModal.validation(); 					//初始化验证
		AlertUtil.hideModalFooterMessage();
	},
	/**
	 * 初始化修改
	 */
	inItUpdate: function(e){
		var this_ = this;
		$("#alertInfo").hide();
		var fjjx = $(e.target).attr("fjjx");
		var bz = $(e.target).attr("bz");
		$("#fjjx").val(fjjx);//飞机机型
		$("#fjjx").attr("disabled",true);
		$("#bz").val(bz);//备注
		$("#mark").val("2");//修改标识
		$("#"+this_.id+"_modalName").html("修改机型");
		$("#"+this_.id+"_modalEname").html("Edit A/C Type");
		$("#"+this_.id+"").modal("show");
		AddalertModal.validation(); 					//初始化验证
		AlertUtil.hideModalFooterMessage();
	},
	/**
	 * 保存
	 */
	save : function(){
		$('#form').data('bootstrapValidator').validate();
		if (!$('#form').data('bootstrapValidator').isValid()) {
			return false;
		}
		var fjjx=$.trim($("#fjjx").val());//飞机机型
		var bz=$.trim($("#bz").val());//备注
		var mark=$("#mark").val();//新增修改标识
		
		var obj = {};
		obj.bz = bz;//备注
		obj.fjjx = fjjx;//飞机机型
		obj.dprtcode = $("#dprtcode").val();
		var url="";
		if(mark==1){
			url="/project2/actype/save";//新增
		}else{
			url="/project2/actype/update";//修改
		}
		AddalertModal.subjectFrom(obj,url);
	 	
	},
	/**
	 * 提交数据
	 */
	subjectFrom : function(obj,url){
		 startWait($("#AddalertModal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#AddalertModal"),
	 		success:function(data){
	 			finishWait($("#AddalertModal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#AddalertModal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	},
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#form').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		          fjjx: {
		            validators: {
		            	notEmpty: {
		                    message: '机型不能为空!'
		                },regexp: {
	                        regexp: /^[\x00-\xff]*$/,
	                        message: '机型只能输入英文、数字、英文特殊字符!' 	
	                    }
		            }
			      }
		       }
		   });
	}
};

/**
 * 弹出窗验证销毁
 */
$("#AddalertModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
//	AddalertModal.validation();
});

