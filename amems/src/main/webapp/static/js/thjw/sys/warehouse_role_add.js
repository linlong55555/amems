$(function() {
	Navigation(menuCode,"库房角色新增","Warehouse Add");
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	roleCode: {
	                validators: {
	                	notEmpty: {
	                        message: '库房角色代码不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不包含中文'
	                    },
	                    stringLength: {
	                        max: 30,
	                        message: '长度最多不超过30个字符'
	                    }
	                }
	            },
	                       
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

	function save(){
		 $('#form').data('bootstrapValidator').validate();
		  if(!$('#form').data('bootstrapValidator').isValid()){
			return false;
		  }
		
		startWait();//开始Loading
		//验证库房Code是否重复
		if(checkUpdMt($('#roleCode').val(),$('#dprtcode').val())){
			// 提交数据
			AjaxUtil.ajax({
				url:basePath+"/sys/role/addwarehouseRole",
				type:"post",
				data:{
					'roleCode' : $('#roleCode').val(),// 库房角色代码
					'roleName' : $('#roleName').val(),// 库房角色名称
					'roleRemark' : $('#roleRemark').val(),// 库房角色备注
					'dprtId' : $('#dprtcode').val()// 库房角色机构代码
				},
				dataType:"json",
				success:function(data){
					if (data.state == "Success") {
						finishWait();//结束Loading
						$("#alertModal").modal("show");
						$('#alertModal').on('hidden.bs.modal', function (e) {
							window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#WarehouseRoleList";
						});
						$("#alertModalBody").html("添加成功!");
					} else {
						finishWait();//结束Loading
						AlertUtil.showErrorMessage(data.text);
					}
				}
				
			});
		
		}
	
	}

	//检查库房角色code是否重复
	function checkUpdMt(roleCode,dprtcode){
		var flag = false;
		AjaxUtil.ajax({
			url:basePath+"/sys/role/warehousecheckUpdMt",
			type:"post",
			async: false,
			data:{
				'warehouseRoleCode' : roleCode,
				'dprtId' : dprtcode
			},
			dataType:"json",
			success:function(data){
				
				if (data.state == "success") {
					flag = true;
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.message);
				}
			}
		});
		return flag;
	}


