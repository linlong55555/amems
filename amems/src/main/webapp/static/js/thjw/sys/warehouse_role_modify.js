$(function() {
	Navigation(menuCode,"库房角色修改","Warehose Update");
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	roleName: {
	                validators: {
	                    notEmpty: {
	                        message: '库房角色名称不为空'
	                    },
	                    stringLength: {
	                        max: 30,
	                        message: '长度最多不超过30个字符'
	                    }
	                }
	            },
	            remark: {
	                validators: {
	                	 stringLength: {
		                        max: 65,
		                        message: '长度最多不超过65个字符'
		                    }
	                }
	            }
	        }
	    });
});

	//返回
	function go(){
		 window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#WarehouseRoleList";
	}
	
	function modify(){
		 $('#form').data('bootstrapValidator').validate();
		  if(!$('#form').data('bootstrapValidator').isValid()){
			return false;
		  }
		
		startWait();//开始Loading
			// 提交数据
		AjaxUtil.ajax({
				url:basePath+"/sys/role/modifywarehouseRole",
				type:"post",
				data:{
					'id' : $('#id').val(),// 库房角色id
					'roleName' : $('#roleName').val(),// 库房角色名称
					'roleRemark' : $('#roleRemark').val()// 库房角色备注
				},
				dataType:"json",
				success:function(data){
					if (data.state == "Success") {
						finishWait();//结束Loading
						$("#alertModal").modal("show");
						$('#alertModal').on('hidden.bs.modal', function (e) {
							window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#WarehouseRoleList";
						});
						$("#alertModalBody").html("修改成功!");
					} else {
						finishWait();//结束Loading
						AlertUtil.showErrorMessage(data.text);
					}
				}
				
			});
	}
