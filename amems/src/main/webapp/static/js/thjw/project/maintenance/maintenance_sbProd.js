
$(function() {
	Navigation(menuCode,"维修方案提交生产","Submit");
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	jhSxrq: {
	                validators: {
	                    notEmpty: {
	                        message: '计划生效日期不能为空'
	                    }
	                }
	            }
	        }
	    });
});

$('#jhSxrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
     .updateStatus('jhSxrq', 'NOT_VALIDATED',null)  
     .validateField('jhSxrq');  
});

//维修方案提交生产
function update(){

	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	var id = $.trim($("#id").val());
	var wxfabh = $.trim($("#wxfabh").val());
	var zwms = $.trim($("#zwms").val());
	var jx = $.trim($("#jx").val());
	var bb = $.trim($("#bb").val());
	var jhSxrq = $.trim($("#jhSxrq").val());

	if(wxfabh == "" || wxfabh == null){
		AlertUtil.showErrorMessage("维修方案编号不能为空!");
		return false;
	}
	
	if(zwms == "" || zwms == null){
		AlertUtil.showErrorMessage("维修方案名称不能为空!");
		return false;
	}
	
	if(jx == "" || jx == null){
		AlertUtil.showErrorMessage("机型不能为空!");
		return false;
	}
	
	if(bb == ""|| bb == null){
		AlertUtil.showErrorMessage("版本号不能为空!");
		return false;
	}
	
	if(jhSxrq == ""|| jhSxrq == null){
		AlertUtil.showErrorMessage("计划生效日期不能为空!");
		return false;
	}
	jhSxrq += " 00:00:00";
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/sbProd",
		type:"post",
		data:{
			'id' : id,
			'wxfabh' : wxfabh,
			'zwms' : zwms,
			'jx' : jx,
			'bb' : bb,
			'jhSxrq' : jhSxrq
		},
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功!', '/project/maintenance/main?id='+id+'&pageParam='+pageParam);
		}
	});

}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/maintenance/main?pageParam="+pageParam;
}

//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
