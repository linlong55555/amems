
$(function() {
	Navigation(menuCode,"新增维修方案","Add");
	initPlaneModel();
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	zwms: {
                validators: {
                    notEmpty: {
                        message: '维修方案名称不能为空'
                    },
                    /*regexp: {
                        regexp: /^([\u4E00-\u9FA5]+|[a-zA-Z]+)$/,
                        message: '只能输入中文和英文'
                    },*/
                    stringLength: {
                        max: 16,
                        message: '长度最多不超过16个字符'
                    }
                }
            },
            bb: {
                validators: {
                    notEmpty: {
                        message: '版本不能为空'
                    },
                    stringLength: {
                        max: 12,
                        message: '长度最多不超过12个字符'
                    }
                }
            },
            /*jhSxrq: {
                validators: {
                    notEmpty: {
                        message: '计划生效日期不能为空'
                    }
                }
            },*/
            jx: {
                validators: {
                    notEmpty: {
                        message: '请选择机型'
                    }
                }
            }          
        }
    });
});

/*$('#jhSxrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
    .updateStatus('jhSxrq', 'NOT_VALIDATED',null)  
    .validateField('jhSxrq');  
});*/

function initPlaneModel(){
	var data = acAndTypeUtil.getACTypeList({DPRTCODE:$("#dprtcode").val()});
 	var option = '<option value="">please choose</option>';
 	if(data.length >0){
		$.each(data,function(i,obj){
			option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
		});
  	 }
 	$("#jx").append(option);
}

function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	var zwms = $.trim($("#zwms").val());
	var bb = $.trim($("#bb").val());
	//var jhSxrq = $.trim($("#jhSxrq").val());
	var jx = $.trim($("#jx").val());
	var bz = $.trim($("#bz").val());
	
	if(zwms == null || zwms == ""){
		AlertUtil.showErrorMessage("维修方案名称不能为空！");
		return false;
    }
	
	if(bb == null || bb == ""){
		AlertUtil.showErrorMessage("版本不能为空！");
		return false;
    }
	var regu = /^[0-9]+\.?[0-9]{0,2}$/;
	if (!regu.test(bb)) {
        AlertUtil.showErrorMessage("版本号只能输入数字,并保留两位小数！");
        return false;
    }
	
	if(jx == null || jx == ""){
		AlertUtil.showErrorMessage("机型不能为空！");
		return false;
    }
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/maintenance/add",
		type:"post",
		data:{
			'zwms' : zwms,
			'bb' : bb,
			'jx' : jx,
			//'jhSxrq' : jhSxrq,
			'bz' : bz
		},
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功!', '/project/maintenance/main?id='+data+'&pageParam='+pageParam);
		}
	});

}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/maintenance/main?pageParam="+pageParam;
}

//只能输入数字和小数,保留两位
function clearNoNumTwo(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
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