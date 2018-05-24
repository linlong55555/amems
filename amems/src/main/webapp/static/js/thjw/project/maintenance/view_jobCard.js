var no1=0;
var pgdids=[];
var hcids=[];
var gdbhs=[];
var XggkGdbhs=[];
var hcxh=0;
var gznrxh=0;
var nox=0;
var oldXggkList=[];
var oldHchcList=[];
var oldGznrList=[];
var oldScwjList=[];
var workId=0;

$(function(){
	$('#pgdh-dialog').dialog('close');                              //评估单号的模态框
	$('#hcxx-dialog').dialog('close');                              //航材库的模态框
	//回显运算
	sumTotal();
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',$("#jobCardDprtcode").val());      //专业
	DicAndEnumUtil.registerDic('19','gzzw',$("#jobCardDprtcode").val());    //工作站位
	
	//回显相关工卡
	xggkHx();
	
	//回显航材耗材工具
	hchcgjHx();
	
	//回显工作内容
	gznrHx();
	//上传附件
	scwj();
	
	//专业回显
	var jobCardZy= $('#jobCardZy').val();
	$('#zy').val(jobCardZy);
	
	//工作站位
	var jobCardGzzw= $('#jobCardGzzw').val();
	$('#gzzw').val(jobCardGzzw);
	
	//
	
	$('#btnSave').click(function() {
		saveNonroutine();
    });
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	//addRotatableCol();
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xfgdyy: {
                validators: {
                	stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    }
                }
            },
            bz: {
                validators: {
                    stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    }
                }
            }
        }
    });
	
	
	if($('#djgznbid').val()==null || $('#djgznbid').val()==""){
		$('#djgznr').val("");
	}
	//初始化日志功能
	if($("#jg").val()=="1"){
		logModal.init({code:'B_G_013',id:$('#jobCardId').val()});
	}
	gklxSelect();
	loadWorkGroup();
	
	initRevisionNoticeBook();
	selectByRevisionNoticeBook();
	
});
function selectByRevisionNoticeBook(){
	AjaxUtil.ajax({
		   url:basePath+"/project/jobCardToBook/queryAllByMainid",
			 type:"post",
			 async: true,
			 data:{
				 'mainid' : $("#jobCardId").val()
			 },
			 dataType:"json",
			 success:function(data){
		    finishWait();
		    if(data.bookList != null){
		    	appendbook(data.bookList);
		    }
	      }
	    });
}
function appendbook(list){
	 $.each(list,function(index,row){
			 $('#xdtzsid').multiselect('select', row.xdtzsid);
	 });
}
function initRevisionNoticeBook(){
	var jx=$("#jx").val();
	if(jx==""){
		jx="00000";
	}
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/revisionNoticeBook/queryAllBook",
		type:"post",
		data:{jx:jx,zt:3,tzslx:3,dprtcode:$("#dprtcode").val()},
		dataType:"json",
		success:function(data){
			//生成多选下拉框(飞机注册号)
			$('#xdtzsDiv').empty();
			$('#xdtzsDiv').html("<select multiple='multiple' id='xdtzsid' disabled='disabled'></select>");
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
		        numberDisplayed:2,
			    includeSelectAllOption: true,
			    onChange:function(element,checked){
			    }
			});
		}
	});
}


function sumTotal(){
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").html((jhgsRs*jhgsXss).toFixed(2));
	}
}

var  zxfltype={
		 ZJJ:'装机件',
		FZJJ:'非装机件',
		 FJ:'机身',
};
var  wotype={
		 时控件工单:'1',
		 附件工单:'2',
		 排故工单:'3',
		 非例行工单:'4',
		 定检工单:'5',
		 EO工单:'6',
};
var  hctype={
		 危险品:'4',
		 工具:'5',
		 航材:'6',
		 设备:'7',
		 低值易耗品:'8',
};



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
     sumTotal();
}

function hchcgjHx(){
	var gdjcid=$('#gdjcid').val();
	var obj={
			mainid : gdjcid
	};
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedHcList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   //oldHchcList=data.rows;
			   appendSelecedHc(data.rows);
	   }
	 }); 
}
function appendSelecedHc(list){
	 var htmlContent = '';
	   var htmlContent = ""; 
	   $.each(list,function(index,row){
		   hcxh++;
		   oldHchcList.push(row.id);
			htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+row.bjid+"'>";
		/*	htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+row.bjid+"')><button><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a>" +
									"<input type='hidden' name='bjid' value='"+formatUndefine(row.bjid)+"'>" +
									"<input type='hidden' name='zwms' value="+formatUndefine(row.zwmc)+">" +
							   		"<input type='hidden' name='ywms' value="+formatUndefine(row.ywmc)+">" +
							   		"<input type='hidden' name='bjh' value="+formatUndefine(row.jh)+">" +
							   		"<input type='hidden' name='kycksl' value="+formatUndefine(row.sl)+">" +
							   		"<input type='hidden' name='hcxxId' value="+formatUndefine(row.id)+">" +
							   		"<input type='hidden' name='hclx' value="+formatUndefine(row.lx)+">" +
							   		"</td>";*/
			htmlContent = htmlContent + "<td  style='vertical-align:middle'>"+(index+1)+"</td>"; 
			htmlContent = htmlContent + "<td  style='vertical-align:middle'><input name='refJhly' type='text' class='form-control' maxlength='100' disabled='disabled' value='"+StringUtil.escapeStr(formatUndefine(row.refJhly))+"'></td>"; 
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"</td>"; 
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.jh))+"'>"+StringUtil.escapeStr(formatUndefine(row.jh))+"</td>";  
			htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'>"+formatUndefine(row.kykcsl)+"</td>";  
			htmlContent = htmlContent + "<td ><input   onkeyup='clearNoNum(this)'  name='sl' type='text' class='form-control' disabled='disabled' value='"+formatUndefine(row.sl)+"'></td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.lx)+"</td>";  
			htmlContent = htmlContent + "<td ><input name='bz' type='text' class='form-control' disabled='disabled' value='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'></td>";   
			htmlContent = htmlContent + "</tr>";
			hcids.push(row.bjid);
		 });
			//$("#Annunciatelist").empty();
		    $("#CKlist").append(htmlContent);
}
//回显工作内容
function gznrHx(){
	var gdjcid=$('#gdjcid').val();
	var obj={
			mainid : gdjcid
	};
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedGznrList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   appendSelecedGznr(data.rows);
	   }
	 });
}
//拼接工作内容

function appendSelecedGznr(list){
	var htmlContent = '';
	gznrxh++;
  var htmlContent = ""; 
  $.each(list,function(index,row){
	  oldGznrList.push(row.id);	
	  var check="";
	  if(row.isBj==1){
		  check="checked";
	  };
	   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
	  /* htmlContent = htmlContent + "<td><button  onclick='del_tr(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button></td>" ;*/
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><div>"+(index+1)+"</div></td>";  
	  /* htmlContent = htmlContent + "<td><input maxlength='300' class='form-control' type='text' name='gznr' disabled='disabled' value='"+formatUndefine(row.gznr)+"' /></td>"; */
	   htmlContent = htmlContent + "<td><textarea class='form-control' id='gznr' name='gznr' readonly='readonly' style='min-height:80px' >"+StringUtil.escapeStr(formatUndefine(row.gznr))+"</textarea></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='20'class='form-control' type='text' name='gzz' readonly='readonly' value='"+StringUtil.escapeStr(formatUndefine(row.gzz))+"' ></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='gznrId' type='hidden' value="+row.id+" />";  
	   if(row.isBj==1){
		   htmlContent = htmlContent + "<input name='isBj' type='checkbox' disabled='disabled' style='width: 20px;height: 20px;' checked="+check+" /></td>";  
	   }else{
		   htmlContent = htmlContent + "<input name='isBj' type='checkbox' disabled='disabled' style='width: 20px;height: 20px;' /></td>";  
		   
	   }
	   htmlContent = htmlContent + "</tr>";  
	   workId++;
	 });
	    $("#gznrList").append(htmlContent);
}
//回显相关工卡
function xggkHx(){
	var mainid=$('#gdjcid').val();
	var jx=$('#jx').val();
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedXggkList",
		   type: "post",
		   dataType:"json",
		   data:{
			   'mainid' : mainid,
			   'jx' : jx
		   },
		   success:function(data){
			   appendSelecedXggk(data.rows);
	   }
	 });
}
//拼接相关工卡
function appendSelecedXggk(list){
	//alert(JSON.stringify(list));
	
	   var htmlContent = ""; 
		 $.each(list,function(index,row){ 
			 oldXggkList.push(row.id);
			htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+row.xggdid+"'>";
			/*htmlContent = htmlContent + "<td><button  onclick=\"gdbhRemove('"+row.xggdid+"')\"><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button> </td>";  
*/			htmlContent = htmlContent + "<td class='text-center'>"+(index+1)+"</td>";

			htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='xggdid' value="+row.xggdid+"><input type='hidden' name='xggd_lx' value="+row.xggdLx+"><input type='hidden' name='xggd_zlx' value="+row.xggdZlx+"><input type='hidden' name='xgjcgdid' value="+row.xgjcgdid+"><input type='hidden' name='id' value="+row.id+">";
			if(row.xggdLx == 5){
				
				htmlContent = htmlContent +"<a href=\"javascript:viewMainJobCard('"+row.xggdid+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"</a>"; 
			}else{
				htmlContent = htmlContent + "<a href=\"javascript:Looked('"+ row.xggdid + "',"+row.xggdLx+")\">"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"</a>";  
			}
			
			htmlContent = htmlContent +"</td>";
			
			if(row.xggdLx==2){
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.xggdLx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.xggdZlx)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.xggdLx)+"</td>";  
		   }
			
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(formatUndefine(row.zhut))+"</td>";
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.zyName))+"</td>";  
			htmlContent = htmlContent + "</tr>";
			//pgdids.push(pgdId);
			XggkGdbhs.push(row.xggdid);
			  });
			$("#appendlistXggk").empty();
		    $("#appendlistXggk").append(htmlContent);
		    //判断是否已选择，
			   for(var i=0;i<XggkGdbhs.length;i++){
				   $("#XggkCheckbox"+XggkGdbhs[i]).attr("checked", "checked");
				   //alert($("#XggkCheckbox"+XggkGdbhs[i]).val());
			   };
	
}
//回显上传附件
function scwj(){
	var mainid=$('#gdjcid').val();
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedScwjList",
		   type: "post",
		   dataType:"json",
		   data:{
			   'mainid' : mainid
		   },
		   success:function(data){
			   //oldScwjList=data.rows;
			   appendSelecedScwj(data.rows);
	   }
	 });
}
//拼接上传附件
function appendSelecedScwj(list){
	//alert(JSON.stringify(list));
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 oldScwjList.push(row.id);
		 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
		/* htmlContent = htmlContent+'<td><div>';
//	 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+row.id+ '\')" title="删除附件">  ';
		 htmlContent = htmlContent+'</div></td>';*/
		/* htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
		 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';*/
		 htmlContent = htmlContent+"<td class='text-left'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"</td>";
		 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left'>"+formatUndefine(row.czr_user.displayName)+'</td>';
		 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
		/* htmlContent = htmlContent+'<td>'+row.cflj+'</td>';*/
		 	 htmlContent = htmlContent+'<input type="hidden" name="fjid" value=\''+row.id+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(row.nbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(row.yswjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(row.wjdx)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(row.wbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="cflj" value=\''+row.cflj+'\'/>';
		 
		 htmlContent = htmlContent+'</tr>';
	 });
	 $('#filelist').append(htmlContent);
	
}

//上传
var fileid=1;
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:false,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	
	maxFileCount:5,
	formData: {
		"fileType":"workorder"
		,"wbwjm" : function(){return $('#wbwjm').val();}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	uploadStr:"上传",
	statusBarWidth:150,
	dragdropWidth:150,
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	{
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
		 trHtml = trHtml+'<td><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除附件">  ';
		 trHtml = trHtml+'</div></td>';
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		 trHtml = trHtml+"<td class='text-left'>"+data.attachments[0].wjdx+'KB</td>';
		 trHtml = trHtml+"<td class='text-left'>"+data.attachments[0].user.username+' '+data.attachments[0].user.realname+'</td>';
		 trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj+'</td>';
		 
		 trHtml = trHtml+'<input type="hidden" name="fjid" value=\''+fileid+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+data.attachments[0].nbwjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+data.attachments[0].yswjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="wbwjm1" value=\''+data.attachments[0].wbwjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+data.attachments[0].cflj+'\'/>';
		 
		 trHtml = trHtml+'</tr>';	 
		 $('#filelist').append(trHtml);
		 fileid++;
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
		
	}
	$('#'+uuid).remove();
}
function lineRemovefile(obj){
	$(obj).parent().parent().remove();   
}

function chooesRow2(obj){
	chooesRow($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRow(obj);
}
//选中一行(单选)
function chooesRow3(objradio){
	$(objradio).find("input[type='radio']").attr("checked",true);
}

//行选
function chooesRow(obj){
	var flag = obj.is(":checked");
	if(flag){
		obj.removeAttr("checked");
	}else{
		obj.attr("checked",true);
	}
	
}


//同意
function agreedMain(){
	obj={
			'id':$('#jobCardId').val(),
			'shyj':$('#shyj').val(),
			'zt':2
	}
	AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback: function(){
		shenheSubmitMain(obj);
	}});
	
}
//驳回
function rejectedMain(){
	if($('#shyj').val()=="" || $('#shyj').val()==null){
		AlertUtil.showErrorMessage("审核意见不能为空");
		return 
	}
	obj={
			'id':$('#jobCardId').val(),
			'shyj':$('#shyj').val(),
			'zt':5
	}

	AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback: function(){
		shenheSubmitMain(obj);
	}});
}
//批复同意
function agreedMain1(){
	obj={
			'id':$('#jobCardId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':3
	}
	
	AlertUtil.showConfirmMessage("您确定要批准通过吗？",{callback: function(){
		pifuSubmitMain(obj);
	}});
}
//批复驳回
function rejectedMain1(){
	if($('#pfyj').val()=="" || $('#pfyj').val()==null){
		AlertUtil.showErrorMessage("批准意见不能为空");
		return 
	}
	obj={
			'id':$('#jobCardId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':6
	}
	
	AlertUtil.showConfirmMessage("您确定要批准驳回吗？",{callback: function(){
		pifuSubmitMain(obj);
	}});
}
//批复中止
function suspendMain(){
	if($('#pfyj').val()=="" || $('#pfyj').val()==null){
		AlertUtil.showErrorMessage("批准意见不能为空");
		return 
	}
	obj={
			'id':$('#jobCardId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':4
	}

	AlertUtil.showConfirmMessage("您确定要中止(关闭)吗？",{callback: function(){
		pifuSubmitMain(obj);
	}});
}
//审核提交
function shenheSubmitMain(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/jobCard/submitshenheMainJobCard",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==2){
				AlertUtil.showMessage('审核成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==5){
				AlertUtil.showMessage('驳回成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}
		}
	});

}
//批复提交
function pifuSubmitMain(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/jobCard/submitpifuMainJobCard",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==3){
				AlertUtil.showMessage('批准成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==6){
				AlertUtil.showMessage('驳回成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==4){
				AlertUtil.showMessage('中止成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
			}
		}
	});

}
//附件下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00603)
	//window.open(basePath + "/common/gdDownfile/" + id);
}

//查看工单
function Looked(id,gddlx) {                    
	 window.open(basePath+"/project/workorder/LookedWo?id=" + id+"&gddlx="+gddlx);
}

//工卡查看跳转
function viewMainJobCard(id,dprtcode){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode="+dprtcode);
}
function back(){
	window.location.href =basePath+"/project/jobCard/main?pageParam="+pageParam;
}
$(".panel-heading").not(":first").click(function(){         //隐藏和显示
	$(this).next().slideToggle("fast");
});
function gklxSelect(){
	var gklx=$("#gklx").val();
	if(gklx==1){
		$("#lxSelect").show();
		$("#lxSelectzjh").hide();
	}else if(gklx==2){
		$("#lxSelect").hide();
		$("#lxSelectzjh").show();
	}else{
		$("#jx").val("");
	}
	
}
//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=$("#jobCardDprtcode").val();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/workgroup/loadWorkGroup",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   if(data!=null){
				   var apps = '';
				   var list=data.wgList;
				   var gzzId=$("#gzzId").val();
				   $.each(list,function(i,list){
					   if(gzzId!=null ||gzzId!=""){
						   if(gzzId==list.id){
							   apps += "<option value='"+list.id+"' selected='selected'>"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
						   }else {
							   apps += "<option value='"+list.id+"' >"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
						   }
					   }else{
						   apps += "<option value='"+list.id+"' >"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
					   }
			 	   }); 
				   if(!data.wgMust){
					   $("#gzgroup").append("<option value=''></option>"+apps);
				   }else{
					   $("#gzgroup").append(apps);
				   }
			   }
	       },
	 }); 
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
     sumTotal();
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
     
     sumTotal();
}

//验证版本只能输入数字和小数,不能超过最大版本
function vilidateBb(obj){
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
    
    if(obj.value >= Number($("#rwxfabb").val())){
    	obj.value = $("#rwxfabb").val();
    }
     
}

function sumTotal(){
	var totle = 0;
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	totle = jhgsRs*jhgsXss;
	if(totle == ''){
		totle = 0;
	}
	totle = totle.toFixed(2);
	$("#bzgs").val(totle);
	
}