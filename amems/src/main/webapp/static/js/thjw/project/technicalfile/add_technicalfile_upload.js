//时间控件加载
/*$('.date-picker').datepicker({
	autoclose : true,
	 clearBtn:true
}).next().on("click", function() {
	$(this).prev().focus();
});*/

$(document).ready(function(){
	
	$('#sm').on('change', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('sm', 'NOT_VALIDATED',null)  
		.validateField('sm');   
	});
	
	$('#sxrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
         .updateStatus('sxrq', 'NOT_VALIDATED',null)  
         .validateField('sxrq');  
   });
	
	$('#pgqx').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('pgqx', 'NOT_VALIDATED',null)  
        .validateField('pgqx');  
  });
	
	var dprtcode=$("#jgdm").val();
	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	//导航
	Navigation(menuCode,"技术文件上传","Upload Technical Files");
	
	//上传
	$("#fileuploader").uploadFile({
		
		url:basePath+"/project/technicalfile/ajaxUploadFile",
		multiple:false,
		dragDrop:false,
		//maxFileCount:1,
		formData: {"fileType":"technicalfile"},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
		uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
		uploadButtonClass: "ajax-file-upload_ext",
//		statusBarWidth:150,
//		dragdropWidth:150,
		showStatusAfterSuccess : false,
		showStatusAfterError: false,
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
        {
			var sm=data.attachments[0].fileName;
			$("#fileName").val(sm);	
			sm=sm.substring(0,sm.lastIndexOf ('.'));
			$("#sm").val(sm);
			$("#newFileName").val(data.attachments[0].newFileName);
			$("#relativePath").val(data.attachments[0].relativePath);
			$("#wjdx").val(data.attachments[0].wjdx);
			$('#sm').change();
        }
		
	});
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	shzltgh: {
                validators: {
                	notEmpty: {
                        message: '文件编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,30}$/,
                        message: '不包含中文且长度不超过30个字符'
                    }
                }
            },
            bb: {
            	validators: {
            		notEmpty: {
            			message: '版本不能为空'
            		},
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
            	}
            },
                       
            pgrid: {
                validators: {
                    notEmpty: {
                        message: '机型工程师不能为空'
                    }
                }
            },
            sm: {
                validators: {
                	notEmpty: {
                        message: '原文件不能为空'
                    },
                	 stringLength: {
	                        max: 100,
	                        message: '长度最多不超过100个字符'
	                    },
	                	callback: {
	                        message: '不能输入特殊字符',
	                        callback: function(value, validator) {
	                            return !(/[<>\/\\|:"?*]/gi).test(value);
	                        }
	                    }
                }
            },
            sxrq: {
                validators: {
                	notEmpty: {
                        message: '生效日期不能为空'
                    }
                }
            },
            pgqx: {
                validators: {
                	notEmpty: {
                        message: '评估期限不能为空'
                    }
                }
            }
        }
    });
	
    var dprtcode = $.trim($("#jgdm").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if(planeDatas != null && planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }
	  /*if(planeRegOption == ''){
	  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
	  }*/
	  $("#jx").append(planeRegOption);
	  
	  jxgcs();
	
});


function btnSave() {
	
	
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }	
	  //验证文件编号是否唯一
	  if(validationWjbh($("#shzltgh").val(),"")==false){
		  AlertUtil.showErrorMessage("文件编号已存在，请重新输入");
		  return false;
	  }	
	  if($('#fileName').val()==null || $('#fileName').val()==""){
			AlertUtil.showErrorMessage("上传文件不能为空");
			return false;
		}
	  if($('#sm').val()==null || $('#sm').val()==""){
		  AlertUtil.showErrorMessage("文件说明不能为空"); 
		  return false;
	  }
	  
	  
	  
		startWait();//开始Loading
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/technicalfile/addTechnicalFileUpload",
			type:"post",
			data:{
				'ly' :  $.trim($('#ly').val()),// 来源
				'jx' : $.trim($('#jx').val()),// 机型
				'fl' :  $.trim($('#fl').val()),// 分类
				'wjlx' :  $.trim($('#wjlx').val()),// 分类
				'shzltgh' :  $.trim($('#shzltgh').val()),// 文件编号
				'bb' :  $.trim($('#bb').val()),// 版本
				'pgrid' : $.trim( $('#pgrid').val()),// 机型工程师
				'sxrq' : $.trim($('#sxrq').val()),// 生效日期
				'pgqx' : $.trim($('#pgqx').val()),//评估期限
				'bz' : $.trim($('#bz').val()),//评估期限
				'newFileName' : $.trim($('#newFileName').val()),//内部文件名
				'relativePath' : $.trim($('#relativePath').val()),//存放路径
				'wjdx' : $.trim($('#wjdx').val()),//文件大小
				'fileName' : $.trim($('#fileName').val()),//原始文件名
				'sm' : $.trim($('#sm').val()),//外部文件名
				'zjh' : $.trim($('#wczjh').val())//ATA章节号
			},
			dataType:"json",
			success:function(data){
				finishWait();//结束Loading
				if (data.state == "Success") {
					AlertUtil.showMessage('添加成功!','/project/technicalfile/mainupload?id='+data.id+'&pageParam='+pageParam);
				} else {
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});

}
function validationWjbh(shzltgh,oldshzltgh){
	var falot=true;
	var dprtcode=$("#jgdm").val();
	AjaxUtil.ajax({
	   url:basePath+"/project/technicalfile/validationWjbh",
	   type:"post",
		async: false,
		data:{
			'shzltgh' : shzltgh,
			'oldshzltgh':oldshzltgh,
			'dprtcode':dprtcode
		},
		dataType:"json",
	   success:function(data){
		   if(data.total>0){
			   falot = false;
			   return falot;
		   }
		   return falot;
    }
  }); 
	 return falot;
}

function openList(){
	var zjh = $.trim($("#wczjh").val());
	var dprtcode = $.trim($("#jgdm").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = formatUndefine(wczjh) + " " + formatUndefine(wczjhName);
			}
			$("#wczjh").val(formatUndefine(wczjh));
			$("#wczjhName").val(formatUndefine(wczjhName));
		}
	});
}
function back(){
	window.location.href =basePath+"/project/technicalfile/mainupload?pageParam="+pageParam;
}

//机型工程师的改变事件
function jxgcs(){
	var jx=$("#jx").val();
	if(jx==null || jx==""){
		return false
	}
	//清空机型工程师
	$("#pgrid").val("");
	var user={};
	user.jgdm=$("#jgdm").val();
	user.jx=jx;
	AjaxUtil.ajax({
		//url:"saveUser",
		url:basePath+"/sys/user/queryAllByjx",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(user),
		dataType:"json",
		success:function(data){
			finishWait();//结束Loading
			var htmlContent="";
			 $.each(data,function(index,row){
				 htmlContent+= "<option value='"+row.id+"' >"+row.displayName+"</option>";
			 });
			 $("#pgrid").empty();
			 $("#pgrid").append(htmlContent);
		}
	});
	
	
}
