
$(document).ready(function(){
	Navigation(menuCode,"确认工单工时","Confirm Working Hours");

	$('#tjrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('tjrq', 'NOT_VALIDATED',null)  
        .validateField('tjrq');  
  });
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dcbbh: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '不包含中文且长度不超过20个字符'
                    }
                }
            }
        }
    });
	
});


function btnSave() {
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }	
	  
		startWait();//开始Loading
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionmessage/workersimplex/modifyWorkersimplex",
			type:"post",
			data:{
				'id' : $.trim( $('#id').val()),// id
				'gsshDcbbh' : $.trim( $('#dcbbh').val()),// 调查表编号
				'gsshTjrid' :  $.trim($('#tjrid').val()),// 统计人
				'gsshTjrq' : $.trim($('#tjrq').val()),// 统计日期
				'gsshBz' :  $.trim($('#gsshBz').val()),// 描述
			},
			dataType:"json",
			success:function(data){
				finishWait();//结束Loading
				if (data.state == "Success") {
					
					$("#alertModalBody").html('保存成功!');
					window.location = basePath+'/productionmessage/workersimplex/manage?fjzch='+encodeURIComponent($('#fjzch').val())+'&id='+data.id+'&pageParam='+pageParam;
				} else {
					AlertUtil.showErrorMessage("保存失败");
				}
			}
		});

}

function btnSub() {
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }	
	  
		startWait();//开始Loading
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/productionmessage/workersimplex/subWorkersimplex",
			type:"post",
			data:{
				'id' : $.trim( $('#id').val()),// id
				'gsshDcbbh' : $.trim( $('#dcbbh').val()),// 调查表编号
				'gsshTjrid' :  $.trim($('#tjrid').val()),// 统计人
				'gsshTjrq' : $.trim($('#tjrq').val()),// 统计日期
				'gsshBz' :  $.trim($('#gsshBz').val()),// 描述
			},
			dataType:"json",
			success:function(data){
				finishWait();//结束Loading
			
				if (data.state == "Success") {
					$("#alertModalBody").html('确认成功!');
					window.location = basePath+'/productionmessage/workersimplex/manage?fjzch='+encodeURIComponent($('#fjzch').val())+'&id='+data.id+'&pageParam='+pageParam;
				} else {
					AlertUtil.showErrorMessage("确认失败");
				}
			}
		});

}

//返回
function back(){
	 window.location.href =basePath+"/productionmessage/workersimplex/manage?pageParam="+pageParam;
}