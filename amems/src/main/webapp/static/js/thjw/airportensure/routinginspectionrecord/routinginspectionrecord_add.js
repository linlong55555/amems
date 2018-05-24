$(document).ready(function(){

	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				return false;//调用主列表页查询
			}
		}
	});
	
	//加载上传按钮
	loadingButton();
	//时间控件
	$('#xsrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('xsrq', 'NOT_VALIDATED',null)  
        .validateField('xsrq');  
  });
	
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	xsrq: {
                validators: {
                	notEmpty: {
                        message: '巡检日期不能为空'
                    }
                }
            },
            kssj: {
                validators: {
                    notEmpty: {
                        message: '开始时间不能为空'
                    }
                }
            },
            jssj: {
                validators: {
                	notEmpty: {
                        message: '结束时间不能为空'
                    }
                }
            }
        }
    });
	
});

function loadingButton(){
	$.each($("#detailList").find(".uploaderDiv"), function(){
		   var $this = $(this);
		   $this.uploadFile({
			   url:basePath+"/common/ajaxUploadFile",
			   multiple:true,
			   dragDrop:false,
			   showStatusAfterSuccess: false,
			   showDelete: false,
			   
			   maxFileCount:100,
			   formData: {
				   "fileType":"trainingprogramme"
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
				   trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick=\"delfile(\''+data.attachments[0].uuid+ '\')"\ title="删除附件">  ';
				   trHtml = trHtml+'</div></td>';
				   trHtml = trHtml+"<td title='"+StringUtil.escapeStr(data.attachments[0].yswjm)+"'>"
				   +'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'><input type="hidden" name="wbwjm" value=\''+data.attachments[0].wbwjm+'\'><input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'><input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'><input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(data.attachments[0].wjdx)+'\'>'
				   +StringUtil.subString(StringUtil.escapeStr(data.attachments[0].yswjm))+'</td>';
				   trHtml = trHtml+'</tr>';	 
				   $this.parent().prev().append(trHtml);
			   }
		   });
	   });
}
/**
 * 删除附件
 * @param newFileName
 */
function delfile(uuid) {
	 $('#'+uuid).remove(); 
}
//打开用户列表
function openUserList(){
	$("#alertModalUser").modal("show");
	$('#keyword_user_search').val("");
	AjaxGetDatasWithSearch();
}
function AjaxGetDatasWithSearch(){
	var obj={};
	obj.jgdm=$('#zzjgid').val();
	obj.keyword=$.trim($('#keyword_user_search').val());
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/routinginspectionrecord/queryUserAll",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   
			    finishWait();
			    if(data.userList !=""){
			    	appendContentHtml(data.userList);
			    	// 标记关键字
					signByKeyword($("#keyword_user_search").val(),[2]);
				   } else {
					  $("#userList").empty();
					  $("#userList").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				   }
	      }
	    });
}
function appendContentHtml(list){
	var xsrid=$('#xsrid').val();
	 var htmlContent = '';
	   $.each(list,function(index,row){
	if (index % 2 == 0) {
		   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)' >";
	   } else {
		   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" onclick='chooesRow3(this)' >";
	   }
	   if(xsrid==row.id){
		   htmlContent =htmlContent+"<td class='text-center'>" +
		   "<input type='radio' name='opUser' id='opUser' checked='true' value="+row.id +" >" +
		   "<input type='hidden' value="+StringUtil.escapeStr(row.realname) +"></td>";  
	   }else{
		   htmlContent =htmlContent+"<td class='text-center'>" +
		   "<input type='radio' name='opUser' id='opUser' value="+row.id +" >" +
		   "<input type='hidden' value="+StringUtil.escapeStr(row.realname) +"></td>";  
		   
	   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.displayName)+"' >"+StringUtil.escapeStr(row.displayName)+"</td>";  
		   if(StringUtil.escapeStr(row.department)==null || StringUtil.escapeStr(row.department)==""){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.department.dprtname)+"</td>";  
		   }
		   htmlContent = htmlContent + "</tr>";  
		    
	});
	$("#userList").empty();
	$("#userList").html(htmlContent);
}
//选中一行(单选)
function chooesRow3(objradio){
	$(objradio).find("input[type='radio']").attr("checked",true);
}
//用户搜索
function searchUser (){
	AjaxGetDatasWithSearch();
}
//选择用户后回显
function appendUser(){
	var userId=$('#opUser:checked').val();
	var userName=$('#opUser:checked').next().val();
	$('#xsrid').val(userId);
	$('#xsrmc').val(userName);
	$('#xsrmcOld').val(userName);
}
//清空用户
function clearUser(){
	$('#xsrid').val('');
	$('#xsrmc').val('');
	$('#xsrmcOld').val('');
	$("#alertModalUser").modal("hide");
}
//手动输入用户名时，清空userid
function usernameChange(){
	var xsrmcOld=$('#xsrmcOld').val();
	var xsrmc=$('#xsrmc').val();
	if(xsrmc!=xsrmcOld){
		$('#xsrid').val("");
	}
}
//保存
function submit(){
	
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	var obj={};
	var xsrmc=$('#xsrmc').val();
	var xsrid=$('#xsrid').val();
	var xsrq=$('#xsrq').val();
	var kssj=$('#kssj').val();
	var jssj=$('#jssj').val();
	var isYhxs=$('#isYhxs').val();
	startWait();
	obj.xsrmc=xsrmc;
	obj.xsrid=xsrid;
	obj.xsrq=xsrq;
	obj.kssj=kssj;
	obj.jssj=jssj;
	obj.isYhxs=isYhxs;
	obj.detailList=encapsulationDetai();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/airportensure/routinginspectionrecord/save",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功!','/airportensure/routinginspectionrecord/main?id='+data+'&pageParam='+pageParam);
		}
	});
	
}

//封装明细
function encapsulationDetai(){
var detailList=[];
var xjxmbhList=["0101","0102","0103","0104","0105","0201","0301","0302","0401","0501","0601","0701","0801"];
	for(var i=0;i<xjxmbhList.length;i++){
		var detail={};
		var selectName='#detail'+xjxmbhList[i];
		var textareaName='#detailBz'+xjxmbhList[i];
		detail.xjxmbh=xjxmbhList[i];
		detail.xjxmbs=$(selectName).val();
		detail.xjxmbz=$(textareaName).val();
		detail.orderAttachmentList=encapsulationAttachment(xjxmbhList[i]);
		detailList.push(detail);
	}

	
	
	return detailList;
}
//封装附件表
function encapsulationAttachment(xhbh){
	var na='detailPxfj'+xhbh
	var attachmentlist=[];
	$('#detailList').find("td[name='"+na+"']>tr").each(function(){
		var infos ={};
		var yswjm =$(this).find("input[name='yswjm']").val(); 
		var nbwjm =$(this).find("input[name='nbwjm']").val();
		var cflj =$(this).find("input[name='cflj']").val();
		var wjdx =$(this).find("input[name='wjdx']").val();
		
		infos.yswjm = yswjm;
		infos.nbwjm = nbwjm;
		infos.wbwjm = yswjm;
		infos.cflj = cflj;
		infos.wjdx = wjdx;
		infos.hzm = yswjm.split(".")[1];
		attachmentlist.push(infos);
	});
	return attachmentlist;
}

function back(){
	window.location.href =basePath+"/airportensure/routinginspectionrecord/main?pageParam="+pageParam;
}










