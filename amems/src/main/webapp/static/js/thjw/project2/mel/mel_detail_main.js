
var id = "";
var pagination = {};

var alertFormId = 'mel_detail_edit_alert_Modal';
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
	logModal.init({code:'B_G_008'});
});

//form验证规则
function formValidator(){
	$('#form').bootstrapValidator({
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
	 searchParam.zt = zt;
	 paramsMap.userId = userId;
	 paramsMap.userType = userType;
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
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:meldetail:main:02' onClick=\"edit('"+ row.id + "','"+ row.dprtcode + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   }
		   
		   htmlContent += "</td>";  
		   
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";
		   var bb = "";
		   if(row.bb){
			   bb = "R"+row.bb; 
		   }
		   htmlContent += "<td title='"+bb+"' class='text-left'>"+bb+"</td>";
		   htmlContent += "<td class='text-left' title='"+(row.attachment?row.attachment.wbwjm+"."+row.attachment.hzm:'')+"' ><a href=\"javascript:downfile('"+(row.attachment?row.attachment.id:'')+"')\">"+(row.attachment?row.attachment.wbwjm+"."+row.attachment.hzm:'')+"</a></td>";
		   /*if(row.attachment != null){
			   htmlContent += "<i class='icon-download-alt color-blue cursor-pointer' onClick=\"downloadfile('"+ row.attachment.id + "')\" title='MEL清单  MEL'></i>";
		   }
		   htmlContent += "</td>";*/
		   
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
//附件下载
 function downfile(id){
 	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
 }
//打开新增供应商弹出框
function openWinAdd(){
	formValidator();
	dprtcode = userJgdm;
 	var obj = {
 	};
 	mel_detail_edit_alert_Modal.show({
 		modalHeadChina : '新增',
		modalHeadEnglish : 'Add',
		viewObj:obj,
		blOption:true,
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
			
		}
	});
 	
}

//打开修改供应商弹出框
function openWinEdit(id,dprtcode_){
	formValidator();
	dprtcode = dprtcode_;
 	selectById(id,function(obj){
 		$("#melqdfjid", $("#"+alertFormId)).val(formatUndefine(obj.melqdfjid));
 	 	mel_detail_edit_alert_Modal.show({
 	 		modalHeadChina : '编辑',
 			modalHeadEnglish : 'Edit',
 			viewObj:obj,
 			blOption:true,
 			dprtcode:dprtcode,//机构代码
 			callback : function(data) {// 回调函数

 			}
 		});
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
	//验证
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		return false;
	}
	
	var jx = $.trim($("#jx", $("#"+alertFormId)).val());
	var bb = $.trim($("#bb", $("#"+alertFormId)).val());
	
	var obj = {
			id : $.trim($("#melId").val()),
			jx : jx,
			bb : bb,
			melqdfjid : $("#melqdfjid", $("#"+alertFormId)).val(),
			dprtcode : dprtcode
		};
	
	  //设置工作内容
	  var workContentAttachment = attachmentsSingleUtil.getAttachment('mel_attachments_single_edit');
	  if(workContentAttachment != null && workContentAttachment.wjdx != null && workContentAttachment.wjdx != ''){
		  obj.attachment = workContentAttachment;
	  }
	  
	  //验证mel附件
	  if(!attachmentsSingleUtil.isVaildMap['mel_attachments_single_edit']){
		  return false;
	  }
	var url = basePath+"/engineering/mel/edit";
	if($.trim($("#melId").val()) == null || $.trim($("#melId").val()) == ''){
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
	TableUtil.resetTableSorting("main_list_table");
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

