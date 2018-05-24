
$(function() {
	Navigation(menuCode,"维修方案改版","Revision");
    initRevisionNoticeBook();
    
    validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	/*jhSxrq: {
                validators: {
                    notEmpty: {
                        message: '计划生效日期不能为空'
                    }
                }
            },   */
            newbb: {
                validators: {
                    notEmpty: {
                        message: '版本不能为空'
                    },
                    stringLength: {
                        max: 12,
                        message: '长度最多不超过12个字符'
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

function initRevisionNoticeBook(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/revisionNoticeBook/queryAllBook",
		type:"post",
		data:{jx:$("#jx").val(),zt:3,tzslx:1,dprtcode:$("#dprtcode").val()},
		dataType:"json",
		success:function(data){
			//修订通知书，下拉框数据集
			var bookOption = "";
			$.each(data,function(i,book){
				bookOption += "<option value='"+book.id+"'>" + StringUtil.escapeStr(book.text) +"</option>";
			});
			$("#xdtzsid").empty();
			$("#xdtzsid").append(bookOption);
			//生成多选下拉框动
			$('#xdtzsid').multiselect({
				buttonClass: 'btn btn-default',
		        buttonWidth: 'auto',
		        numberDisplayed:100,
			    includeSelectAllOption: true,
			    onChange:function(element,checked){
			    }
			});
		}
	});
}

//维修方案改版
function update(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	var id = $.trim($("#id").val());
	var wxfabh = $.trim($("#wxfabh").val());
	var zwms = $.trim($("#zwms").val());
	//var jhSxrq = $.trim($("#jhSxrq").val());
	var jx = $.trim($("#jx").val());
	var bb = $.trim($("#bb").val());
	var newbb = $.trim($("#newbb").val());
	var dprtcode = $.trim($("#dprtcode").val());
	var xdtzsid = getMultiSelectValue('xdtzsid');

	if(wxfabh == "" || wxfabh == null){
		AlertUtil.showErrorMessage("维修方案编号不能为空!");
		return false;
	}
	
	if(zwms == "" || zwms == null){
		AlertUtil.showErrorMessage("维修方案不能为空!");
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
	
	if(newbb == ""|| newbb == null){
		AlertUtil.showErrorMessage("新版本号不能为空!");
		return false;
	}
	
	var regu = /^[0-9]+\.?[0-9]{0,2}$/;
	if (!regu.test(newbb)) {
        AlertUtil.showErrorMessage("新版本号只能输入数字,并保留两位小数！");
        return false;
    }
	
	/*if(xdtzsid == ""|| xdtzsid == null){
		AlertUtil.showErrorMessage("请选择修订通知书!");
		return false;
	}*/
	
	if(Number(newbb) <= Number(bb)){
		AlertUtil.showErrorMessage("新版本必须大于老版本!");
		return false;
	}
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/modify",
		type:"post",
		data:{
			'id' : id,
			'wxfabh' : wxfabh,
			'zwms' : zwms,
			'jx' : jx,
			'bb' : newbb,
			'fBbid' : id,
			'dprtcode' : dprtcode,
			//'jhSxrq' : jhSxrq,
			'xdtzsidStr' : xdtzsid
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
