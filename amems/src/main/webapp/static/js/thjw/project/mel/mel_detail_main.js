
var id = "";
var pagination = {};

var alertFormId = 'alertForm';
var headChinaId = 'headChina';
var headEnglishId = 'headEnglish';
var dprtcode = '';
var attachmentSingle = {};

$(document).ready(function(){
	Navigation(menuCode, '', '', 'GC-8-2-1', '刘兵', '2017-08-01', '刘兵', '2017-08-01');//初始化导航
	goPage(1,"auto","desc");//开始的加载默认的内容 
	refreshPermission();
	formValidator();
	
	//初始化日志功能
	logModal.init({code:'B_G_019'});
});


function initPlaneModel(){
	var data = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
 	var option = '';
 	if(data.length >0){
		$.each(data,function(i,obj){
			option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
		});
  	 }
 	$("#jx", $("#"+alertFormId)).empty();
 	$("#jx", $("#"+alertFormId)).append(option);
}

//form验证规则
function formValidator(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	jx: {
                validators: {
                	notEmpty: {
                        message: '机型不能为空'
                    }
                }
            },
            bb: {
                validators: {
                    notEmpty: {
                        message: '版本不能为空'
                    }
                }
            }   
        }
    });	 
}

//隐藏Modal时验证销毁重构
$("#"+alertFormId).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     formValidator();
});

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/engineering/mel/queryAllPageList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(searchParam),
		success:function(data){
			finishWait();
			if(data.total >0){
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
					}
				}); 
				// 标记关键字
				signByKeyword($("#keyword_search").val(),[2,3],"#list tr td");
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'supplier_main_table'});
      }
    }); 
	
}
 //将搜索条件封装 
 function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var keyword = $.trim($("#keyword_search").val());
	 var zt = $.trim($("#zt_search").val()); 
	 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	 searchParam.keyword = keyword;
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
 }
 
 function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\">";
		   }
		      
		   htmlContent += "<td class='fixed-column text-center'>";
		   
		   if(row.zt == 1 && row.melbgdid == null){
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:01' onClick=\"edit('"+ row.id + "','"+ row.dprtcode + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   }
		   
		   htmlContent += "</td>";  
		   
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bb	)+"' class='text-left'>"+StringUtil.escapeStr(row.bb)+"</td>";
		  
		   htmlContent += "<td class='text-center'>";
		   if(row.attachment != null){
			   htmlContent += "<i class='icon-download-alt color-blue cursor-pointer' onClick=\"downloadfile('"+ row.attachment.id + "')\" title='MEL清单  MEL'></i>";
		   }
		   htmlContent += "</td>";
		   
		   htmlContent += "<td class='text-center'>"+(row.zt == 1?"有效":"无效")+"</td>";
		   
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.shr?row.shr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.shr?row.shr.displayName:'')+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.melChangeSheet?row.melChangeSheet.shsj:'')+"' class='text-left'>"+StringUtil.escapeStr(row.melChangeSheet?row.melChangeSheet.shsj:'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.pyr?row.pyr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.pyr?row.pyr.displayName:'')+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.melChangeSheet?row.melChangeSheet.pfsj:'')+"' class='text-left'>"+StringUtil.escapeStr(row.melChangeSheet?row.melChangeSheet.pfsj:'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.whsj)+"' class='text-left'>"+StringUtil.escapeStr(row.whsj)+"</td>";
		   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";
		   htmlContent += "</tr>";  
		    
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	   refreshPermission();
 }
 
//打开新增供应商弹出框
function openWinAdd(){
	dprtcode = userJgdm;
	initPlaneModel();
 	$("#"+headChinaId, $("#"+alertFormId)).html('新增');
 	$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
 	$("#bb", $("#"+alertFormId)).val('');
 	$("#id", $("#"+alertFormId)).val('');
 	$("#melqdfjid", $("#"+alertFormId)).val('');
 	$("#wbwjmSingle").val('');
 	$("#melqdDiv", $("#"+alertFormId)).html('');
 	attachmentSingle = {};
 	$("#"+alertFormId).modal("show");
}

//打开修改供应商弹出框
function openWinEdit(id,dprtcode_){
	dprtcode = dprtcode_;
	initPlaneModel();
 	selectById(id,function(obj){
 		$("#"+headChinaId, $("#"+alertFormId)).html('修改');
 		$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
 		$("#jx", $("#"+alertFormId)).val(obj.jx);
 		$("#id", $("#"+alertFormId)).val(obj.id);
 		$("#bb", $("#"+alertFormId)).val(obj.bb);
 		$("#melqdfjid", $("#"+alertFormId)).val(formatUndefine(obj.melqdfjid));
 		var attachment = obj.attachment;
 		if(attachment != null){
 			 attachmentSingle.id = attachment.id;
 			 attachmentSingle.yswjm = attachment.yswjm;
 			 attachmentSingle.wbwjm = attachment.wbwjm;
 			 attachmentSingle.nbwjm = attachment.nbwjm;
 			 attachmentSingle.cflj = attachment.cflj;
 			 attachmentSingle.wjdx = attachment.wjdx;
 			 $("#wbwjmSingle", $("#"+alertFormId)).val(attachment.wbwjm);
 			 $("#melqdDiv", $("#"+alertFormId)).html('');
 			 var t = '';
 			 t += '<a class="pull-left" style="color: blue;" href="javascript:downloadfile(\''+attachment.id+'\')">';
 			 t += '<i class="icon-download-alt pull-left color-blue" style="font-size:25px" title="附件下载 download"></i>';
 			 t += '</a>';
 			 t += '&nbsp;<i class="icon-remove-sign cursor-pointer color-blue cursor-pointer" style="font-size:20px" title="删除 Delete" onclick=removeFile(this)></i>';
 			 $("#melqdDiv", $("#"+alertFormId)).html(t);
 		}
 		$("#"+alertFormId).modal("show");
 	});
}

function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function removeFile(){
	$("#wbwjmSingle").val("");
	$("#melqdDiv").empty();
	attachmentSingle = {};
}

//通过id获取供应商数据
function selectById(id,obj){
 	AjaxUtil.ajax({
 		async: false,
 		url:basePath+"/engineering/mel/selectById",
 		type:"post",
 		data:{id:id},
 		dataType:"json",
 		success:function(data){
 			if(data != null){
 				if(typeof(obj)=="function"){
					obj(data); 
				}
 			}
 		}
 	});
}

//修改供应商
function edit(id,dprtcode){
	openWinEdit(id,dprtcode);
}

function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	var jx = $.trim($("#jx", $("#"+alertFormId)).val());
	var bb = $.trim($("#bb", $("#"+alertFormId)).val());
	
	var obj = {
			id : $.trim($("#id").val()),
			jx : jx,
			bb : bb,
			melqdfjid : $("#melqdfjid", $("#"+alertFormId)).val(),
			dprtcode : dprtcode
		};
	
	if(attachmentSingle != null && attachmentSingle.wjdx != null){
		  obj.attachment = attachmentSingle;
	  }
	
	var url = basePath+"/engineering/mel/edit";
	if($.trim($("#id").val()) == null || $.trim($("#id").val()) == ''){
		url = basePath+"/engineering/mel/save";
	}
	startWait($("#"+alertFormId));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(obj),
		dataType:"json",
		modal:$("#"+alertFormId),
		success:function(data){
			finishWait($("#"+alertFormId));
			AlertUtil.showMessage('保存成功!');
			id = data;
			$("#"+alertFormId).modal("hide");
			refreshPage();
		}
	});

}

//作废
function del(id){
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/aerialmaterialfirm/cancel",
			type:"post",
			async: false,
			data:{
				'id' : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
		
	}});
}

//上传
var uploadObjSingle = $("#fileuploaderSingle").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	
	formData: {
		"fileType":"order"
		,"wbwjmSingle" : function(){return $('#wbwjmSingle').val()}
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
		 attachmentSingle.yswjm = data.attachments[0].yswjm;
		 attachmentSingle.wbwjm = data.attachments[0].wbwjm;
		 attachmentSingle.nbwjm = data.attachments[0].nbwjm;
		 attachmentSingle.cflj = data.attachments[0].cflj;
		 attachmentSingle.wjdx = data.attachments[0].wjdx;
		 $("#wbwjmSingle", $("#"+alertFormId)).val(data.attachments[0].wbwjm);
		 $("#melqdDiv", $("#"+alertFormId)).html('');
	}
		//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
		,onSubmit : function (files, xhr) {
			var wbwjm  = $.trim($('#wbwjmSingle').val());
			if(wbwjm.length>0){
				if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
	            	$('#wbwjmSingle').focus();
	            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
	            	
	            	$('.ajax-file-upload-container').html("");
					uploadObjSingle.selectedFiles -= 1;
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

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}

	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
	
//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	
	$("#keyword_search").val("");
	
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});

	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});
	
	$("#dprtcode_search").val(dprtId);
}
 
//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}

$('#sqkssj', $("#"+alertFormId)).datepicker({
	 autoclose: true,
	 clearBtn:true
});

$('#sqjssj', $("#"+alertFormId)).datepicker({
	 autoclose: true,
	 clearBtn:true
});
		
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		});
 $('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',

	includeSelectAllOption : true
}); 
 
 //回车事件控制
 document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#keyword_search").is(":focus") 
				|| $("#keyword_search").is(":focus")
		){
			searchRevision();      
		}
	}
};

