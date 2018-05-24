var del_ids=[];

$(function(){
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	Navigation(menuCode,"检验航材","Checking Material");
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
    });
	LoadAttachment();
	inithcly();
	 var SelectArr1 = $("#hclyDiv select"); 
	 if(SelectArr1[0].options[0] != undefined){
		 SelectArr1[0].options[0].selected = true;
	 }
	if($("#hclyone").val()!=''){
		$("#hcly").val($("#hclyone").val());
	}
	var jldwTransfor=$("#jldw").val();
	$("#jldw").val(jldwTransfor);
	
	if($.trim($("#jydh").val())==""){
		$("#jydh_div").hide();
		$("#div_2").addClass("clearfix"); 
		$("#div_4").addClass("clearfix"); 
	}else{
		$("#div_1").addClass("clearfix"); 
		$("#div_3").addClass("clearfix"); 
	}
	
	var checker=$.trim($("#checker").val());
	if(checker==null||checker==""){
		var ddd=$("#dqdlr").val();
		$("#checker").val(ddd);
	}
	if($("#jyrq").val()==null||$("#jyrq").val()==""){
		TimeUtil.getCurrentDate("#jyrq");                          //取得检验日期的当前时间
	}
	
	if($("#jyjg").val()==2||$("#jyjg").val()==3){
		$("#jgsm_div").show();
	}
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xkzh: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                }
            },
            shzh: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                }
            },
            shzwz: {
                validators: {
                }
            },
            tsn: {
                validators: {
                	regexp: {
                        regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
                        message: '输入的格式不正确'
                    },
                }
            }, 
            tso: {
                validators: {
                	regexp: {
                        regexp:/^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
                        message: '输入的格式不正确'
                    },
                }
            },
            cfyq: {
                validators: {
                }
            },
            bz: {
                validators: {
                }
            },
            jgsm: {
                validators: {
                	notEmpty: {
                        message: '检验结果说明不能为空！'
                    },
                }
            }
        }
    });
	
});

function inithcly(){
	$("#hcly").empty();
	DicAndEnumUtil.registerDic('85',"hcly", $("#dprtcode").val());
}
function checkResult(){
	var jyjg=$("#jyjg").val();
	if(jyjg==2||jyjg==3){
		$("#jgsm_div").show();
	}else{
		$("#jgsm_div").hide();
	}
}

//打开调整列表对话框
function openUserList() {
	 var dprtcode = $("#dprtcode").val();
	//调用用户选择弹窗
	userModal.show({
		selected:$("#jyrid").val(),//原值，在弹窗中默认勾选
		dprtcode:dprtcode,               //机构代码
		callback: function(data){//回调函数
			$("#jyrid").val(formatUndefine(data.id));
			$("#checker").val(formatUndefine(data.username) + " "+ formatUndefine(data.realname));
		},
    });
}

//检验人改变的时候 判断是选择的还是 填写的   填写的则清空id
function ChangeJyr(){
	if($("#checker").val()!=$("#checker2").val()){
		$("#jyrid").val("");
	}
}

//检验单保存提交
function submitSave(){
	var zt=2;
	validateInsert(zt);
}
function checkSave(){
	var zt=1;
	validateInsert(zt);
}
function validateInsert(zt){
    $('#form').data('bootstrapValidator').validate();
    if(!$('#form').data('bootstrapValidator').isValid()){
	  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
	return false;
    }
    var jydh=$.trim( $('#jydh').val()); 
    var id=$.trim( $('#id').val()); 
    var kcid=$.trim( $('#kcid').val()); 
	var hcly=$.trim( $('#hcly').val());  
	var xkzh=$.trim( $('#xkzh').val());  
	var shzh=$.trim( $('#shzh').val());  
	var shzwz=$.trim( $('#shzwz').val());  
	var tsn=$.trim( $('#tsn').val());  
	var tso=$.trim( $('#tso').val());  
	var cfyq=$.trim( $('#cfyq').val());  
	var bz=$.trim( $('#bz').val());  
	var jyr=$.trim( $('#checker3').val());  
	var jyrid=$.trim( $('#jyrid').val()); 
	var jyrq=$.trim( $('#jyrq').val());  
	var jyjg=$.trim( $('#jyjg').val());  
	var jgsm=$.trim( $('#jgsm').val());  
	var hjsm=$.trim( $('#hjsm').val());  
	
	var rkdid=$.trim( $('#rkdid').val());  
	var kcsl=$.trim( $('#kcsl').val());  
	
    if(zt==2){
    	if(jyrid==""||jyrid==null||jyrq==""||jyrq==null){
    		AlertUtil.showErrorMessage("检验人和检验日期不能为空！");
    		return false;
    	}
    	if(jyjg==0){
    		AlertUtil.showErrorMessage("请选择检验结果！");
    		return false;
    	}
    	if(jyjg!=1){
    		if(jgsm==""||jgsm==null){
    			AlertUtil.showErrorMessage("请填写结果说明！");
        		return false;
    		}
    	}
	} 
	if($('#gljb').val()==3){
		if(shzh==null||shzh==""){
			AlertUtil.showErrorMessage("序列号管理时，适航证号不能为空！");
    		return false;
		}
	}
	
	var qualitycheck={};
	
	qualitycheck.jydh=jydh;
	
	qualitycheck.id=id;
	qualitycheck.kcid=kcid;
	qualitycheck.hcly=hcly;
	qualitycheck.xkzh=xkzh;
	qualitycheck.shzh=shzh;
	qualitycheck.shzwz=shzwz;
	qualitycheck.tsn=tsn;
	qualitycheck.tso=tso;
	qualitycheck.cfyq=cfyq;
	qualitycheck.bz=bz;
	qualitycheck.jyr=jyr;
	qualitycheck.jyrid=jyrid;
	qualitycheck.jyrq=jyrq;
	qualitycheck.jyjg=jyjg;
	qualitycheck.jgsm=jgsm;
	qualitycheck.zt=zt;
	qualitycheck.hjsm=hjsm;
	
	qualitycheck.rkdid=rkdid;
	qualitycheck.kcsl=kcsl;
	qualitycheck.del_ids=del_ids;
	qualitycheck.attachmentList=getWoJobenclosureParenm();
	if(!shzh){
		AlertUtil.showConfirmMessage("适航证号为空，确定要继续吗？",{callback: function(){
			editCheckList();
		}});	
	}else{
		editCheckList();
	}
	
	function editCheckList(){
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/qualityCheck/Edit",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(qualitycheck),
			dataType:"json",
			success:function(data){
				if(data>0){
					if(zt==1){
						AlertUtil.showMessage('保存成功!','/aerialmaterial/qualityCheck/main?id='+id+'&pageParam='+pageParam);
					}else{
						AlertUtil.showMessage('提交成功!','/aerialmaterial/qualityCheck/main?id='+id+'&pageParam='+pageParam);
					}
				}else{
					finishWait();
					AlertUtil.showMessage('该航材状态已更新，请刷新后再进行操作!','/aerialmaterial/qualityCheck/main?id='+id+'&pageParam='+pageParam);
				}
			},
		});
	}
}

var id=$("#id").val();          
/**
 * 加载航材检验的附件
 */
function LoadAttachment(){
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/qualityCheck/LoadAttachment?id="+id,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   success:function(data){
	    finishWait();
		   if(data!=null){
			   appendContentHtml(data);
		   } else {
			  $("#filelist").empty();
			  $("#filelist").append("<tr><td colspan=\"4\">暂无数据 No data.</td></tr>");
		   }
        },
  }); 
}
function appendContentHtml(list){
	   var htmlContent = '';
	   if(list!=null){
		   for(var i=0;i<list.length;i++){
			   htmlContent = htmlContent +"<tr style='cursor: pointer' bgcolor='#f9f9f9' id='tr1_"+list[i].id+"'>";
			   htmlContent = htmlContent+'<td><input type="hidden" name="fjid" value=\''+list[i].id+'\'><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(list[i].wbwjm)+'\'><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(list[i].yswjm)+'\'>'+
			   '<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(list[i].nbwjm)+'\'><input type="hidden" name="cflj" value=\''+list[i].cflj+'\'><input type="hidden" name="wjdx" value=\''+list[i].wjdx+'\'><div>';
			   htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+list[i].id+ '\')" title="删除  Delete">  ';
			   htmlContent = htmlContent+'</div></td>';
			   htmlContent = htmlContent + "<td class='text-left'><a href=\"javascript:downloadfile('"+list[i].id+"')\">"+StringUtil.escapeStr(list[i].wbwjm)+"</a></td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(list[i].wjdxStr)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(list[i].czrname)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(list[i].czsj)+"</td></tr>";  
		   }
	   }
	   $("#filelist").empty();
	   $("#filelist").html(htmlContent);
}


function getWoJobenclosureParenm(){
	  var AttachmentList=[];
	  $("#filelist").find("tr").each(function(){
			var infos2 ={};
			var id =$(this).find("input[name='fjid']").val(); 
			var yswjm =$(this).find("input[name='yswjm']").val(); 
			var nbwjm =$(this).find("input[name='nbwjm']").val();
			var wbwjm =$(this).find("input[name='wbwjm']").val();
			var cflj =$(this).find("input[name='cflj']").val();
			var wjdx =$(this).find("input[name='wjdx']").val();
			
			infos2.id=id;
			infos2.yswjm = yswjm;
			infos2.nbwjm = nbwjm;
			infos2.wbwjm = wbwjm;
			infos2.cflj = cflj;
			infos2.wjdx = wjdx;
			infos2.hzm = nbwjm.split(".")[1];
			AttachmentList.push(infos2);
	  });
	 return AttachmentList;
}
//上传
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	maxFileCount:100,
	formData: {
		"fileType":"qualitycheck"
		,"wbwjm" : function(){return $('#wbwjm').val()}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
	uploadButtonClass: "ajax-file-upload_ext",
	onSuccess:function(files,data,xhr,pd)                     //上传成功事件，data为后台返回数据
	{
		var trHtml = "<tr style='cursor: pointer' bgcolor='#f9f9f9' id='tr1_"+data.attachments[0].uuid+"'>";
		 trHtml = trHtml+'<td><input type="hidden" name="fjid"><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'>'+
		   '<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'><input type="hidden" name="cflj" value=\''+data.attachments[0].cflj+'\'><input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'><div>';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除  Delete">  ';
		 trHtml = trHtml+'</div></td>';
    	 trHtml = trHtml+'<td  class="text-left">'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+'</td>';
		 trHtml = trHtml+'<td class="text-left">'+StringUtil.escapeStr(data.attachments[0].user.username)+"   "+StringUtil.escapeStr(data.attachments[0].user.realname)+'</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
		 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
		 trHtml = trHtml+'</tr>';	 
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
	del_ids.push(uuid);
	$('#tr1_'+uuid).remove();
}

/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function back(){
	 window.location.href =basePath+"/aerialmaterial/qualityCheck/main?pageParam="+pageParam;
}