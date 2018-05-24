var pgdids=[];
var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
$(function() {
	$('#zhut').on('change', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('zhut', 'NOT_VALIDATED',null)  
		.validateField('zhut');   
	});
	//时间控件
	$('#tgqx').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('tgqx', 'NOT_VALIDATED',null)  
        .validateField('tgqx');  
  });
	//时间控件
	$('#sxrq').datepicker({
		autoclose: true,
		clearBtn:true
	}).on('hide', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('sxrq', 'NOT_VALIDATED',null)  
		.validateField('sxrq');  
	});
	
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	            zhut: {
	                validators: {
	                	notEmpty: {
	                        message: '主题不能为空'
	                    }
	                }
	            },
	            userids: {
	                validators: {
	                    notEmpty: {
	                        message: '圈阅人员不能为空'
	                    }
	                }
	            },
	            tgqx: {
	                validators: {
	                	notEmpty: {
	                        message: '通告期限不能为空'
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
	            }
	            	            
	        }
	    });
	
	

//生成多选下拉框动
$('#userids').multiselect({
	buttonClass: 'btn btn-default',
    buttonWidth: 'auto',
    numberDisplayed:20,
    includeSelectAllOption: true,
    onChange:function(element,checked){
    }
});


});
//打开评估单列表对话框
function openPgd() {
	$("#keyword_search").val("");
	 goPage(1,"auto","desc");
	 $("#alertModalPgd").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var zlid=$('#zlid').val();
	var keyword=$.trim($('#keyword_search').val());
	if (zlid != null && zlid != "") {
		obj.zlid = zlid;					//指令id
	   	}
	var pgdsId="";
	for(var i=0;i<pgdids.length;i++){
   		//alert(pgdids[i]);
   		pgdsId+= pgdids[i]+",";
   	}
	pgdsId=pgdsId.substring(0,pgdsId.length-1);
	obj.pgdsId=pgdsId;//已选择的评估单id
	obj.isJstg=1;	//技术指令复选为1
	obj.dprtcode=$("#jgdm").val();
	obj.keyword=keyword;
	//var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/technicalfile/selectPgdList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml(data.rows);
			   var page_ = new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}//,
					//controller: this_
				});
			// 标记关键字
				signByKeyword($("#keyword_search").val(), [ 2, 3, 8 ]);
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr><td colspan=\"12\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
}

function chooesRow2(obj){
	chooesRow($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRow(obj);
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

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		      
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd' onclick='chooesRow2(this)' ><input type='hidden' value="+formatUndefine(row.id)+">" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ly))+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.sxrq)+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.pgqx)+"'>" +
																		   		"<input type='hidden' value="+formatUndefine(row.zt)+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.username)+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.realname)+"></td>";
											   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"' >"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
												htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
												htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
												htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
												htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
												htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
												htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>";
												htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
												htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.pgr_user.displayName))+"</td>";  
												htmlContent = htmlContent + "<td class='text-left'>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
												htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td></tr>";  
		    
	   });
	   //$("#Pgdlist").empty();
	   $("#Pgdlist").html(htmlContent);
	 
}

//保存评估单
function appendPgd(){
	var dprtcode=$("#jgdm").val();
	var htmlContent = ""; 
	$('input[name=pgd]:checked').each(function(){   
		var pgdId=$(this).next().val();
		var pgdh=$(this).next().next().val();
		var ckzl=$(this).next().next().next().val();
		var wjzt=$(this).next().next().next().next().val();
		var ly=$(this).next().next().next().next().next().val();
		var sxrq=$(this).next().next().next().next().next().next().val();
		var pgqx=$(this).next().next().next().next().next().next().next().val();
		var pgzt=$(this).next().next().next().next().next().next().next().next().val();
		var username=$(this).next().next().next().next().next().next().next().next().next().val();
		var realname=$(this).next().next().next().next().next().next().next().next().next().next().val();
		//alert(wjzt);
		htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+pgdId+"'>";
		htmlContent = htmlContent + "<td ><button class='line6' onclick=\"lineRemove('"+pgdId+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'><input type='hidden' name='PgdIdAndPgdh' value="+pgdId+","+StringUtil.escapeStr(pgdh)+"><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(ckzl)+"'><a href=\"javascript:viewMainTechnicalfile('"+pgdId+"','"+dprtcode+"')\">"+StringUtil.escapeStr(pgdh)+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(ly)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(ckzl)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(sxrq.substring(0,10))+"</td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(username)+" "+StringUtil.escapeStr(realname)+"</td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
			/*$('#zhut').on('change', function(e) {
				$('#form').data('bootstrapValidator')  
				.updateStatus('zhut', 'NOT_VALIDATED',null)  
				.validateField('zhut');  
			});*/
			$('#zhut').change();
		}
		  });
		//$("#Annunciatelist").empty();
	    $("#Annunciatelist").append(htmlContent);
}

function viewMainTechnicalfile(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
}

//删除行
function lineRemove(pgdid){
	pgdids.remove(pgdid);
	//alert(pgdids);
	$('#tr1_'+pgdid).remove();
}
//扩展数组方法：查找指定元素的下标  
Array.prototype.indexOf = function(val) {  
for (var i = 0; i < this.length; i++) {  
    if (this[i] == val) return i;  
}  
return -1;  
};  

//扩展数组方法:删除指定元素  
Array.prototype.remove = function(val) {  
var index = this.indexOf(val);  
while(index>-1){  
    this.splice(index, 1);  
    index = this.indexOf(val);  
}  
}; 
/*//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.rid = $.trim($("#rid_search").val());
	 searchParam.aircrafttailnumber = $.trim($("#aircrafttailnumber_search").val());
	 searchParam.msn = $.trim($("#msn_search").val());
	 searchParam.aircrafttype = $.trim($("#aircrafttype_search").val());
	 searchParam.description = $.trim($("#description_search").val());
	 searchParam.plannedstartdate = $.trim($("#plannedstartdate_search").val());
	 searchParam.plannedfinishdate = $.trim($("#plannedfinishdate_search").val());
	 searchParam.actualstartdate = $.trim($("#actualstartdate_search").val());
	 return searchParam;
}*/
//获取选择后的所有评估单id
function getPgdIdAndPgdh(){
	var pgdIdAndPgdh=[];
	$("#Annunciatelist").find("tr input[name='PgdIdAndPgdh']").each(function(){
	var ordersource={};
		//alert($(this).val());
		//alert("2");
		ordersource.pgdid=$(this).val().split(',')[0]; 
		ordersource.pgdh=$(this).val().split(',')[1]; 
		pgdIdAndPgdh.push(ordersource);
	});
	return pgdIdAndPgdh;
}
//获取选择的角色id
function getUsersIdName(){
	var obj = document.getElementById('oListboxTo');
	var options = obj.options;
	var usersId = new Array();
	for(var i=0,len=options.length;i<len;i++){
	    var opt = options[i];
	    usersId.push(opt.value);
	}
	return usersId;
}
//保存
function save(){
	//评估单id
	var orderSourceList=getPgdIdAndPgdh();
	
	//基本信息
	var zhut=$.trim($("#zhut").val());
	var nr=$.trim($("#nr").val());
	var sendList = [];
	var userids = $('#userids').val();
	//alert(userids);
	if(userids != null){
		for(var i = 0 ; i < userids.length ; i++){
			if('multiselect-all' != userids[i]){
				var send={};
				send.jsrid=userids[i].split(',')[0];
				send.jsbmid=userids[i].split(',')[1];
				sendList.push(send);
			}
		}
	}
	var zt=0;
	var tgqx=$('#tgqx').val();
	var sxrq=$('#sxrq').val();
	//alert(tgqx);
	var obj={
			"orderSourceList":orderSourceList,
			"zhut":zhut,
			"nr":nr,
			"zt":zt,
			"ckzl":getShzlh(),
			"sendList":sendList,//用户ID
			"tgqx":tgqx,
			"sxrq":sxrq,
			"bb":$('#bb').val(),
			"orderAttachmentList":getOrderAttachment()
	};
	//alert(JSON.stringify(obj));
	saveMainAnnunciate(obj);
}
//获取所有的适航指令号并拼接
function getShzlh(){
	var ckzl="";
	$("#Annunciatelist").find("tr input[name='Ckzl']").each(function(){
		ckzl+=$(this).val()+","; 
	});
	ckzl=ckzl.substring(0,ckzl.length-1);
	
	return ckzl;
}
//提交
function save1(){
	//评估单id
	var orderSourceList=getPgdIdAndPgdh();
	
	var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	
	
	
	//基本信息
	var zhut=$.trim($("#zhut").val());
	var nr=$.trim($("#nr").val());
	var sendList = [];
	var userids = $('#userids').val();
	//alert(userids);
	if(userids != null){
		for(var i = 0 ; i < userids.length ; i++){
			if('multiselect-all' != userids[i]){
				var send={};
				send.jsrid=userids[i].split(',')[0];
				send.jsbmid=userids[i].split(',')[0];
				sendList.push(send);
			}
		}
	}
	var zt=1;
	var tgqx=$('#tgqx').val();
	var sxrq=$('#sxrq').val();
	var obj={
			"orderSourceList":orderSourceList,
			"zhut":zhut,
			"nr":nr,
			"zt":zt,
			"ckzl":getShzlh(),
			"sendList":sendList,//用户ID
			"tgqx":tgqx,
			"sxrq":sxrq,
			"bb":$('#bb').val(),
			"orderAttachmentList":getOrderAttachment()
	};
	//alert(JSON.stringify(obj));
	saveMainAnnunciate(obj);
}
//提交后台
function saveMainAnnunciate(obj){
	if(obj.sendList<=0){
		AlertUtil.showErrorMessage("请选择需要圈阅的人员");
		return false
	}
	var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/annunciate/saveMainAnnunciate",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==0){
				AlertUtil.showMessage('保存成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==1){
				AlertUtil.showMessage('提交成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
				
			}
		}
	});
}

//选择评估单
function searchRevision(){
	
	goPage(1,"auto","desc");
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
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		  
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'</td>';
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
		 trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj+'</td>';
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
					});
			}
	  }
	 return orderAttachment;
}
function back(){
	window.location.href =basePath+"/project/annunciate/main?pageParam="+pageParam;
}