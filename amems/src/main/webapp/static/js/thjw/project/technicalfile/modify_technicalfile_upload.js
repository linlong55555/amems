//时间控件加载
/*$('.date-picker').datepicker({
	autoclose : true
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
	
	
	 //菜单设置  自己设置
	 //MenuUtil.activeMenu("project:technicalfile:mainupload");
	//数据字典
	var dprtcode=$("#dprtcode").val();
	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	//导航
	Navigation(menuCode,"技术文件修改","Edit");
	
	
	for(var j=0;j<document.getElementById("ly").options.length;j++) 
	{ 
		if(document.getElementById("ly").options[j].value==$('#ly1').val()){
			document.getElementById("ly").options[j].selected=true;
		}
	} 
	for(var j=0;j<document.getElementById("fl").options.length;j++) 
	{ 
		if(document.getElementById("fl").options[j].value==$('#fl1').val()){
			document.getElementById("fl").options[j].selected=true;
		}
	} 
	for(var j=0;j<document.getElementById("wjlx").options.length;j++) 
	{ 
		if(document.getElementById("wjlx").options[j].value==$('#wjlx1').val()){
			document.getElementById("wjlx").options[j].selected=true;
		}
	} 
	
	//上传
	$("#fileuploader").uploadFile({
		url:basePath+"/project/technicalfile/ajaxUploadFile",
		multiple:false,
		dragDrop:false,
//		maxFileCount:1,
		formData: {"fileType":"technicalfile"},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
//		statusBarWidth:150,
//		dragdropWidth:150,
		showStatusAfterSuccess : false,
		showStatusAfterError: false,
		uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
		uploadButtonClass: "ajax-file-upload_ext",
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
        {
			var sm=data.attachments[0].fileName;
			$("#fileName").val(sm);	
			sm=sm.substring(0,sm.lastIndexOf ('.'));
			$("#sm").val(sm);
			$("#newFileName").val(data.attachments[0].newFileName);
			$("#relativePath").val(data.attachments[0].relativePath);
			$("#wjdx").val(data.attachments[0].wjdx);
			$("#uploadFile").hide();
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
	                        max: 65,
	                        message: '长度最多不超过65个字符'
	                    },
	                	callback: {
	                        message: '不能输入特殊字符',
	                        callback: function(value, validator) {
	                            return !(/[<>\/\\|:"?*]/gi).test(value);
	                        }
	                    }
                }
            },
            fileName: {
                validators: {
                	notEmpty: {
                        message: '上传文件不能为空'
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
	
	   var dprtcode = $.trim($("#dprtcode").val());
	    var jx=$("#technicalFileJx").val();
		 var planeRegOption = '';
		 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
		 if(planeDatas != null && planeDatas.length >0){
		  	$.each(planeDatas,function(i,planeData){
		  		if(jx==planeData.FJJX){
		  			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' selected='selected'>"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
		  		}else{
		  			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"'>"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
		  		}
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
	  if(validationWjbh($("#shzltgh").val(),$("#oldshzltgh").val())==false){
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
	/*  if($('#fileName').val()==null || $('#fileName').val()==""){
			AlertUtil.showErrorMessage("上传文件不能为空");
			return false;
		}*/
		var technicalfileId=$.trim( $('#id').val());
		startWait();//开始Loading
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/technicalfile/modifyModifyFileUpload",
			type:"post",
			data:{
				'id' : $.trim( $('#id').val()),//id
				'ly' :  $.trim($('#ly').val()),// 来源
				'jx' : $.trim($('#jx').val()),// 机型
				'fl' :  $.trim($('#fl').val()),// 分类
				'wjlx' :  $.trim($('#wjlx').val()),// 文件类型
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
				'zt' : 0,
				'zjh' :$.trim($('#wczjh').val()),
				'technicalUploadId' : $.trim($('#technicalUploadId').val())//外部文件名
				
			},
			dataType:"json",
			success:function(data){
				
				if (data.state == "Success") {
					finishWait();//结束Loading
					AlertUtil.showMessage('修改成功!','/project/technicalfile/mainupload?id='+technicalfileId+'&pageParam='+pageParam);
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});

}
function validationWjbh(shzltgh,oldshzltgh){
	var falot=true;
	var dprtcode=$("#dprtcode").val();
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
	var dprtcode = $.trim($("#dprtcode").val());
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
//附件下载
function downfile(id){
	//window.open(basePath + "/project/technicalfile/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00101)
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
	user.jgdm=$("#dprtcode").val();
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
			var pgrid=$("#technicalFilePgrid").val();
			$("#technicalFilePgrid").val("");
			 $.each(data,function(index,row){
				 if(pgrid==row.id){
					 htmlContent+= "<option value='"+row.id+"' selected='selected' >"+StringUtil.escapeStr(row.displayName)+"</option>";
				 }else {
					 htmlContent+= "<option value='"+row.id+"' >"+StringUtil.escapeStr(row.displayName)+"</option>";
				 }
			 });
			 $("#pgrid").empty();
			 $("#pgrid").append(htmlContent);
		}
	});
	
	
}


