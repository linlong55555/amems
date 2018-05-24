
$(document).ready(function(){
	Navigation(menuCode,"新增组织机构","Add Orgainazations");
	$('#saveAddDprt').click(function() {
		saveAddDprt() ;
	});
	initValidate();
	$('#yxqks').datepicker({
		autoclose : true,
		clearBtn : true
	});
	$('#yxqjs').datepicker({
		autoclose : true,
		clearBtn : true
	});
	 var superdprtcode=$("#supperdprtcode").val();
		var orgcode=$("#orgcode").val();
		if(superdprtcode===orgcode){
			$("#zczh_max").attr("disabled",false);
			$("#zcfj_max").attr("disabled",false);
			$("#yxqks").attr("disabled",false);
			$("#yxqjs").attr("disabled",false);
			$("#pxh").attr("disabled",false);
		}
});

function saveAddDprt(){
	  $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	 var obj={};
	obj.dprtcode = $.trim($("#dprtcode").val());
	obj.dprtname = $.trim($("#dprtname").val());
	obj.lxr1 = $.trim($("#lxr1").val());
	obj.lxdh1 = $.trim($("#lxdh1").val());
	obj.fax1 = $.trim($("#fax1").val());
	obj.email1 = $.trim($("#email1").val());
	obj.lxr2 = $.trim($("#lxr2").val());
	obj.lxdh2 = $.trim($("#lxdh2").val());
	obj.fax2 = $.trim($("#fax2").val());
	obj.email2 = $.trim($("#email2").val());
	obj.dz=$.trim($("#dz").val());
	obj.remark= $.trim($('#remark').val());
	obj.zczh_max=$.trim($('#zczh_max').val());
	obj.zcfj_max=$.trim($('#zcfj_max').val());
	obj.pxh=$.trim($('#pxh').val());
	obj.yxqks=$.trim($('#yxqks').val());
	obj.yxqjs=$.trim($('#yxqjs').val());
	if($.trim($('#yxqks').val())>$.trim($('#yxqjs').val())){
		AlertUtil.showErrorMessage("开始日期不能大于结束日期");
		return false
	}
	obj.deptType=$.trim($('#dprtType').val());
	obj.whbmid=$.trim($('#whbmid').val());
	obj.whrid=$.trim($('#whrid').val());
	var param={};
	param.dprtcode=$.trim($("#dprtcode").val());
	param.dprtname = $.trim($("#dprtname").val());
	param.dprttype=1;
	if(checkUpdMt(param)){
		startWait();//开始Loading
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/sys/department/addDepartment", 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success : function(data) {
				finishWait();//结束Loading
				if (data != '') {
					AlertUtil.showMessage("增加成功!",'/sys/department/main?id=' + data);
				
				} else {
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});
	}
			
}

//检查角色code是否重复
function checkUpdMt(param){
	var flag = false;
	AjaxUtil.ajax({
		url : basePath+'/sys/department/getDepartmentConut',                     //验证组织机构是否重复
		contentType:"application/json;charset=utf-8",
		data :JSON.stringify(param),
		type:"post",
		async: false,
		dataType : 'json',
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
function back(){
	window.location = basePath+"/sys/department/main/";
}
function initValidate(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {	                       
        	dprtcode: {
                validators: {
                	notEmpty: {
                        message: '组织机构代码不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            dprtname: {
                validators: {
                    notEmpty: {
                        message: '组织机构名称不为空'
                    }
                }
            },
            dprttype: {
                validators: {
                	 regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不能输入中文'
	                    }
                }
            },
            lxdh1:{
                validators: {
                	regexp: {
                		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,	     
		                 message: '不能输入中文'
                    }
                }
            },
            lxdh2:{
            	 validators: {
                 	regexp: {
                 		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,	     
 		                 message: '不能输入中文'
                     }
                 }
            },
            fax1:{
                validators: {
                	regexp: {
                		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                 message: '传真格式不正确'
                    }
                }
            },
            fax2:{
                validators: {
                	regexp: {
                		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                 message: '传真格式不正确'
                    }
                }
            },
            email1:{
                validators: {
                	regexp: {
                		 regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
		                 message: '邮箱格式不正确'
                    }
                }
            },
            email2:{
                validators: {
                	regexp: {
                		 regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
		                 message: '邮箱格式不正确'
                    }
                }
            },
            pxh:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            },
            zcfj_max:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            },
            zczh_max:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            }	
        }
    });
}