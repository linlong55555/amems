$(document).ready(function(){
//	 goPage(1,"auto","desc");//开始的加载默认的内容 
	
	Navigation(menuCode,"新增ATA章节","Add ATA");
	$('#btnSave').click(function() {
		saveFixChapter() ;
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
                        message: 'ATA章节号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                    stringLength: {
                        max: 18,
                        message: '长度最多不超过18个字符'
                    }
                }
            },
            ywms: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                    stringLength: {
		                max: 60,
		                message: '长度最多不超过60个字符'
		            }
                }
            },
            rjcsj: {
                validators: {
                	stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    },
                }
            }
        }
    });
});

function saveFixChapter() {
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	
	// 保存ATA章节号
	var zjh = $.trim($("#zjh").val());
	
	 var obj={};
	 obj.zjh=$('#zjh').val();                   
	 obj.ywms =$('#ywms').val();                
	 obj.zwms= $('#zwms').val();                                            
	 obj.rJcsj= $('#rJcsj').val();     
		AjaxUtil.ajax({
			type : 'post',
			url : basePath+'/project/fixchapter/getfixchapterConut/'+$.trim(zjh)+"",                                                             //验证ATA章节号是否重复
			contentType:"application/json;charset=utf-8",
			data : "",
			dataType : 'json',
			success : function(data) {
				if (data != 1) {                                           //机型不存在则可以执行增加操作
					AjaxUtil.ajax({
						type : 'post',
						url : basePath+'/project/fixchapter/addFixChapter',                                                             //验证ATA章节号是否重复
						contentType:"application/json;charset=utf-8",
						data:JSON.stringify(obj),
						dataType : 'json',
						success : function(data) {
							if (data == 1) {
								AlertUtil.showMessage("增加成功",'/project/fixchapter/main');
							} else {
								parent.showmsg(data.text);
							}
						}
					});
				} else {
					AlertUtil.showErrorMessage("ATA章节号已经存在，请修改...");
				}
			}
		});
	}