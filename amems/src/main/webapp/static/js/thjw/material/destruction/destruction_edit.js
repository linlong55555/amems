var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var deleteScwjId=[];
var stockIds=[];
$(document).ready(function(){
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
	
	
	//加载已选数据
	selectDestructionDetail();
	//加载上传附件
	selectChooseFj();
	
});

function selectDestructionDetail(){
	var id=$("#destructionId").val();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/destruction/queryChoStock",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			//拼接内容
			appendSelectChooseStock(data.rows);
		}
	});
	
}

function appendSelectChooseStock(list){
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 htmlContent = htmlContent+'<tr style="cursor: pointer"  id=\''+row.id+'\'>';
		 htmlContent = htmlContent+"<td class='text-center'><div>";
		 htmlContent = htmlContent+"<i class='icon-trash color-blue cursor-pointer' onclick=\"del_tr('"+row.id+"')\" title='Delete 删除'>";
		 htmlContent = htmlContent+'</div></td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ywms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ywms))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwms))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hcMainData.cjjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.hcMainData.cjjh))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(hclb[row.hcMainData.hclx]))+"'>"+StringUtil.escapeStr(formatUndefine(hclb[row.hcMainData.hclx]))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(gljb[row.hcMainData.gljb]))+"'>"+StringUtil.escapeStr(formatUndefine(gljb[row.hcMainData.gljb]))+'</td>';
		 htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' align='left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
		 htmlContent += "<td title='"+StringUtil.escapeStr(row.pch)+"' align='left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzh))+'</td>';
		 htmlContent = htmlContent+"<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.kcsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.kcsl))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jldw))+"'>"+StringUtil.escapeStr(formatUndefine(row.jldw))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ckmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.ckmc))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.kwh))+"'>"+StringUtil.escapeStr(formatUndefine(row.kwh))+'</td>';
		 htmlContent = htmlContent+'</tr>';
	 });
	 $('#detailTBody').append(htmlContent);
}

//加载已上传的附件
function selectChooseFj(){
	var mainid=$('#destructionId').val();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/destruction/selectedScwjList",
	   type: "post",
	   dataType:"json",
	   data:{
		   'mainid' : mainid
	   },
	   success:function(data){
		   appendSelecedScwj(data.rows);
   }
 });
	
	
}
//拼接上传附件
function appendSelecedScwj(list){
	//alert(JSON.stringify(list));
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
		 htmlContent = htmlContent+'<td><div>';
//	 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 htmlContent = htmlContent+"<i class='icon-trash color-blue cursor-pointer' onclick=\"delfile('"+row.id+"' ,'"+row.id+"' )\" title='Delete 删除'>";
		 htmlContent = htmlContent+'</div></td>';
		/* htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
		 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';*/
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"."+StringUtil.escapeStr(row.hzm)+"'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"."+StringUtil.escapeStr(row.hzm)+"</a></td>";
		 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+'</td>';
		 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
		/* htmlContent = htmlContent+'<td>'+row.cflj+'</td>';*/
		 	 htmlContent = htmlContent+'<input type="hidden" name="fjid" value=\''+row.id+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(row.nbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(row.yswjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(row.wjdx)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(row.wbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="cflj" value=\''+row.cflj+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="hzm" value=\''+row.hzm+'\'/>';
		 
		 htmlContent = htmlContent+'</tr>';
	 });
	 if(htmlContent==''){
		 htmlContent='<tr><td colspan=\"5\">暂无数据 No data.</td></tr>';
	 }
	 $('#filelist').append(htmlContent);
	
}

var uploadObj = $("#fileuploader")
.uploadFile(
		{
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 10,
			formData : {
				"fileType" : "maintenanceRecord",
				"wbwjm" : function() {
					return $('#wbwjm').val()
				}
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,				
			showStatusAfterError: false,
			uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
			uploadButtonClass : "ajax-file-upload_ext",
			onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
			{
				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
						+ data.attachments[0].uuid + '\'>';
				trHtml = trHtml + '<td><div>';
				trHtml = trHtml+ "<i class='icon-trash color-blue cursor-pointer' onclick=\"delfile('"+data.attachments[0].uuid+"' ,'"+1+"' )\" title='Delete 删除'>";
				
				trHtml = trHtml + '</div></td>';
				trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">'
						+ StringUtil.escapeStr(data.attachments[0].wbwjm) +"."+StringUtil.escapeStr(data.attachments[0].hzm)+ '</td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].wjdxStr + ' </td>';
				trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username) + " "
				+ StringUtil.escapeStr(data.attachments[0].user.realname)+'">'
						+ StringUtil.escapeStr(data.attachments[0].user.username) + " "
						+ StringUtil.escapeStr(data.attachments[0].user.realname) + '</td>';
				trHtml = trHtml + '<td>' + data.attachments[0].czsj
						+ '</td>';
				trHtml = trHtml
						+ '<input type="hidden" name="relativePath" value=\''
						+ data.attachments[0].relativePath + '\'/>';

				trHtml = trHtml + '</tr>';
				$('#filelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjm').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
		            	
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
function delfile(uuid,id) {
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if (len > 0) {
		for (var i = 0; i < len; i++) {
			if (responses[i].attachments[0].uuid != uuid) {
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
	}
	$('#' + uuid).remove();
	if(id!=1){
		deleteScwjId.push(id);
	}
	
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
//删除明细数据
function del_tr(id){
	var len =$("#detailTBody").find("tr").length;
	if(len<=1){
		AlertUtil.showErrorMessage("请至少保留一条数据");
		return false;
	}
	$("#"+id).remove();  
	stockIds.push(id);
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
//保存
function save(){
	var url=basePath+"/aerialmaterial/destruction/edit";
	infoSave(url);
}
//提交
function submit(){
	var url=basePath+"/aerialmaterial/destruction/update";
	infoSave(url);
}
function infoSave(url){
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
	obj.id=$("#destructionId").val();
	obj.xhrid=$("#xhrid").val();
	obj.xhrq=$("#xhrq").val();
	obj.bz=$("#bz").val();
	obj.stockIds=stockIds;
	obj.orderAttachmentList=getOrderAttachment();
	obj.deleteScwjId=deleteScwjId;
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(data.state=="success"){
				AlertUtil.showErrorMessage(data.message);
				 window.location.href =basePath+"/aerialmaterial/destruction/main";
			}else{
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
}