$(function() {
	Navigation(menuCode,"角色新增","Role Update");
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
	                        message: '角色名称不为空'
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
		 window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#FunctionalRoleList";
	}

	function modify(){
		 $('#form').data('bootstrapValidator').validate();
		  if(!$('#form').data('bootstrapValidator').isValid()){
			  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
			return false;
		  }
	
		  startWait();//开始Loading
		// 提交数据
		  AjaxUtil.ajax({
			url:basePath+"/sys/role/modifyRole",
			type:"post",
			data:{
				'id' : $('#id').val(),// 角色id
				'roleName' : $('#roleName').val(),// 角色名称
				'roleRemark' : $('#roleRemark').val()// 角色备注
			},
			dataType:"json",
			success:function(data){
				
				if (data.state == "Success") {
					finishWait();//结束Loading
					AlertUtil.showMessage('修改成功!','/sys/role/main');
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.text);
				}
			}
			
		});
	}
