$(document).ready(function(){
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	//初始化最新检验日期
	TimeUtil.getCurrentDate(function (data){
		$("#xhrq").val(data);
	});
	$("#xhrq").datepicker('setStartDate','');
	//时间控件
	$('#xhrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('xhrq', 'NOT_VALIDATED',null)  
        .validateField('xhrq');  
  });
	
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	xhrid: {
	                validators: {
	                	notEmpty: {
	                        message: '销毁人不能为空'
	                    }
	                }
	            },
	            xhrq: {
	                validators: {
	                	notEmpty: {
	                        message: '销毁日期不能为空'
	                    }
	                }
	            }
	            	            
	        }
	    });
	
	
});

function del_tr(id){
	var len =$("#detailTBody").find("tr").length;
	if(len<=1){
		AlertUtil.showErrorMessage("请至少保留一条数据");
		return false;
	}
	$("#"+id).remove();  
}

function save(){
	var url=basePath+"/aerialmaterial/destruction/add";
	var zt=1;
	intoSave(url,zt);
}
function submit(){
	var url=basePath+"/aerialmaterial/destruction/submit";
	var zt=2;
	intoSave(url,zt);
}

function intoSave(url,zt){
	if($("#xhrid").val()==null || $("#xhrid").val()==""){
		AlertUtil.showErrorMessage("请选择销毁人");
		return false;
	}
	
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	  
	
	//基础信息
	var obj={};
	obj.xhrid=$("#xhrid").val();
	obj.xhrq=$("#xhrq").val();
	obj.bz=$("#bz").val();
	obj.zt=zt;
	obj.stockIds=gainStockIds();
	obj.orderAttachmentList=getOrderAttachment();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(data.state=="success"){
				AlertUtil.showMessage(data.message,'/aerialmaterial/destruction/main');
			}else{
				AlertUtil.showErrorMessage("操作失败");
			}
		}
	});
}

function gainStockIds(){
	var stockIds=[];
	$("#detailTBody").find("tr input[name='stockId']").each(function(){
		stockIds.push($(this).val());
	});
	return stockIds;
}

/**
 * 选择用户
 * @returns
 */
function selectUser(){
	userModal.show({
		selected:$("#xhrid").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			$("#xhrid").val(formatUndefine(data.id));
			$("#xhrmc").val(formatUndefine(data.username)+" "+formatUndefine(data.realname));
			$("#xhbmid").val(formatUndefine(data.bmdm));
		}
	})
}

//上传
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	
	formData: {
		"fileType":"order"
		,"wbwjm" : function(){return $('#wbwjm').val()}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	showStatusAfterSuccess : false,
	showStatusAfterError: false,
	uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
	uploadButtonClass: "ajax-file-upload_ext",
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	{
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
		 trHtml = trHtml+'<td><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="Delete 删除">  ';
		 trHtml = trHtml+'</div></td>';
		 //trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
		 //trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
		 trHtml = trHtml+"<td class='text-left' title='"+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+"'>"+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
		  
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'</td>';
		 trHtml = trHtml+"<td class='text-left' title='"+StringUtil.escapeStr(data.attachments[0].user.displayName)+"'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
		 trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj+'</td>';
		 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
		 
		 trHtml = trHtml+'</tr>';	
		 $("#filelist").empty();
		 $('#filelist').append(trHtml);
	}


		//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
		,onSubmit : function (files, xhr) {
			var wbwjm  = $.trim($('#wbwjm').val());
			if(wbwjm.length>0){
				if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
	            	$('#wbwjm').focus();
	            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
	            	
	            	$('.ajax-file-upload-container').html("");
					uploadObj.selectedFiles -= 1;
					return false;
	            } 
	            else{
	            	return true; 
	            }
			}else{
				return true;
			}
		}
		
	});
/**
* 删除附件
* @param newFileName
*/
function delfile(uuid) {
	var  responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if(len > 0 ) {
		for(var i =0;i<len;i++){
			if(responses[i].attachments[0].uuid != uuid){
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		$('#'+uuid).remove();
	}
}
//获取附件上传信息
function getOrderAttachment(){
	var orderAttachment=[];
	  var responses = uploadObj.responses;
	  var len = uploadObj.responses.length;
	  if(len != undefined && len>0){
		  for(var i =0;i<len;i++){
			  orderAttachment.push({
						'yswjm':responses[i].attachments[0].yswjm
						,'wbwjm':responses[i].attachments[0].wbwjm
						,'nbwjm':responses[i].attachments[0].nbwjm
						,'cflj':responses[i].attachments[0].cflj
						,'wjdx':responses[i].attachments[0].wjdx
						,'hzm':responses[i].attachments[0].hzm
					});
			}
	  }
	 return orderAttachment;
}
