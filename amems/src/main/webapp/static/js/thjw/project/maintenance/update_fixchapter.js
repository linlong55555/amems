$(document).ready(function(){
//	 goPage(1,"auto","desc");//开始的加载默认的内容 
	Navigation(menuCode,"修改ATA章节","Edit ATA");
	
	$('#btnSave').click(function() {
		saveUpdateChapter() ;
	});
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	zjh: {
                validators: {
                    notEmpty: {
                        message: '菜单编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '不能输入中文且长度最多不超过20个字符'
                    },
                }
            },
           /* zwms: {
                validators: {
		            regexp: {
		                regexp: /^[\u4e00-\u9fa5]{0,60}$/,
		                message: '只能输入中文'
		            }
                }
            },*/
            ywms: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,60}$/,
                        message: '不能输入中文'
                    },
                }
            },
            rJcsj: {
                validators: {
                	stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    }
                }
            }
        }
    });
});

function saveUpdateChapter(){
	
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	var obj={};
	obj.zjh= $('#zjh').val(),                  
	obj.zwms= $('#zwms').val(),                 
	obj.ywms= $('#ywms').val(),                                        
	obj.rJcsj=$('#rJcsj').val(); 
		// 提交数据
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/project/fixchapter/updateFixchapter", 
			contentType:"application/json;charset=utf-8",
			 dataType:"json",
			data:JSON.stringify(obj),		
			success : function(data) {
				if (data == 1) {
					AlertUtil.showMessage("修改成功！",'/project/fixchapter/main');
				} else {
					parent.showmsg(data.text);
				}
			}
		});
}
