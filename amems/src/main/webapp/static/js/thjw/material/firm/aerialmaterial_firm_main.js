
var id = "";
var pagination = {};

var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;

var alertFormId = 'alertForm';
var headChinaId = 'headChina';
var headEnglishId = 'headEnglish';
var dprtcode = '';

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	loadMonitorsettings();
	goPage(1,"auto","desc");//开始的加载默认的内容 
	refreshPermission();
	formValidator();
	
	AlertUtil.hideModalFooterMessage();
	
	//初始化日志功能
	logModal.init({code:'D_01501',extparam:{GYSTYPE:'1'}});
	initImport();
});

//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/aerialmaterialfirm/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode_search").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
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
                       
        	gysbm: {
                validators: {
                	notEmpty: {
                        message: '供应商编号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            dz: {
                validators: {
                	  stringLength: {
                          max: 1000,
                          message: '长度最多不超过1000个字符'
                      }
                }
            },
            gysmc: {
                validators: {
                    notEmpty: {
                        message: '供应商名称不能为空'
                    },
                    /*regexp: {
                        regexp: /^([\u4E00-\u9FA5]+|[a-zA-Z]+)$/,
                        message: '只能输入中文和英文'
                    },*/
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
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
		url:basePath+"/aerialmaterial/aerialmaterialfirm/queryAllPageList",
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
				signByKeyword($("#keyword_search").val(),[2,3,4,5,6,7,8,12,13],"#list tr td");
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'supplier_main_table'});
      }
    }); 
	
}
 //将搜索条件封装 
 function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var gyslb = $.trim($("#gyslb").val()); 
	 var keyword = $.trim($("#keyword_search").val());
	 var sqksrq = $.trim($("#sqksrq_search").val()); 
	 var sqjsrq = $.trim($("#sqjsrq_search").val()); 
	 var whrq = $.trim($("#whrq_search").val()); 
	 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	 searchParam.keyword = keyword;
	 searchParam.gyslb = gyslb;
	 if(null != sqksrq && "" != sqksrq){
		 var sqksrqBegin = sqksrq.substring(0,10)+" 00:00:00";
		 var sqksrqEnd = sqksrq.substring(13,23)+" 23:59:59";
		 paramsMap.sqksrqBegin = sqksrqBegin;
		 paramsMap.sqksrqEnd = sqksrqEnd;
	 }
	 if(null != sqjsrq && "" != sqjsrq){
		 var sqjsrqBegin = sqjsrq.substring(0,10)+" 00:00:00";
		 var sqjsrqEnd = sqjsrq.substring(13,23)+" 23:59:59";
		 paramsMap.sqjsrqBegin = sqjsrqBegin;
		 paramsMap.sqjsrqEnd = sqjsrqEnd;
	 }
	 if(null != whrq && "" != whrq){
		 var whrqBegin = whrq.substring(0,10)+" 00:00:00";
		 var whrqEnd = whrq.substring(13,23)+" 23:59:59";
		 paramsMap.whrqBegin = whrqBegin;
		 paramsMap.whrqEnd = whrqEnd;
	 }
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
 }
 
 function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
		   if (index % 2 == 0) {
			   htmlContent += "<tr id='"+row.id+"' bgcolor=\""+getWarningColor("#f9f9f9",row.paramsMap.syts)+"\" >";
		   } else {
			   htmlContent += "<tr id='"+row.id+"' bgcolor=\""+getWarningColor("#fefefe",row.paramsMap.syts)+"\" >";
		   }
		      
		   htmlContent += "<td class='fixed-column text-center'>";
		   
		   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:aerialmaterialfirm:main:02' onClick=\"edit('"+ row.id + "','"+ row.dprtcode + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:aerialmaterialfirm:main:03' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
		   
		   htmlContent += "</td>";  
		   
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysbm)+"' class='text-left'>";
		   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.gysbm)+"</a>";
		   htmlContent += "</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysmc)+"' class='text-left'>"+StringUtil.escapeStr(row.gysmc)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysjc)+"' class='text-left'>"+StringUtil.escapeStr(row.gysjc)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.pzh)+"' class='text-left'>"+StringUtil.escapeStr(row.pzh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.dz)+"' class='text-left'>"+StringUtil.escapeStr(row.dz)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.sqfw)+"' class='text-left'>"+StringUtil.escapeStr(row.sqfw)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.zssm)+"' class='text-left'>"+StringUtil.escapeStr(row.zssm)+"</td>";
		   htmlContent += "<td class='text-center'>"+indexOfTime(row.sqkssj)+"</td>";
		   htmlContent += "<td class='text-center'>"+indexOfTime(row.sqjssj)+"</td>";
		   htmlContent += "<td class='text-right'>"+formatUndefine(row.paramsMap.syts)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		   if(null != row.zdr){
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
		   }else{
			   htmlContent += "<td class='text-left'></td>";
		   }
		   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.cjsj)+"</td>";
		   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
		   htmlContent += "</tr>";  
		    
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	   refreshPermission();
 }
 
//获取预警颜色
 function getWarningColor(bgcolor,syts){
 	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
 		bgcolor = warningColor.level2;//黄色
 	}
 	if(Number(syts) <= yjtsJb1){
 		bgcolor = warningColor.level1;//红色
 	}
 	return bgcolor;
 }
 
//打开新增供应商弹出框
function openWinAdd(){
	AlertUtil.hideModalFooterMessage();
	$("#gysbmmark").show();
	$("#gysmcmark").show();
 	setEdit();//设置可编辑
 	$("#"+headChinaId, $("#"+alertFormId)).html('新增');
 	$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
 	var obj = {};
 	setData(obj);//设置表单数据
 	$("#"+alertFormId).modal("show");
 	Contact_person_list_edit.show({
 		djid:null,//关联单据id
		personHead : true,
		colOptionhead : true
	});//显示联系人
 	
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:'',
		fileHead : true,
		colOptionhead : true,
		fileType:"firm"
	});//显示附件

}

//打开修改供应商弹出框
function openWinEdit(id,dprtcode_){
	AlertUtil.hideModalFooterMessage();
	dprtcode = dprtcode_;
 	selectById(id,function(obj){
 		setEdit();//设置可编辑
 		$("#"+headChinaId, $("#"+alertFormId)).html('修改');
 		$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
 		$("#gysbm", $("#"+alertFormId)).attr("readonly","readonly");
 		setData(obj);//设置表单数据
 		$("#sqkssj", $("#"+alertFormId)).datepicker('setStartDate','');
 		$("#sqjssj", $("#"+alertFormId)).datepicker('setStartDate','');
 		$("#"+alertFormId).modal("show");
 		Contact_person_list_edit.show({
 	 		djid:id,//关联单据id
 			personHead : true,
 			colOptionhead : true
 		});//显示联系人
 		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
 		attachmentsObj.show({
 			djid:id,
 			fileHead : true,
 			colOptionhead : true,
 			fileType:"firm"
 		});//显示附件
 	});
}

//打开查看供应商弹出框
function openWinView(id){
 	selectById(id,function(obj){
 		$("#"+headChinaId, $("#"+alertFormId)).html('查看');
 		$("#"+headEnglishId, $("#"+alertFormId)).html('View');
 		setData(obj);//设置表单数据
 		setView();//设置只读/失效
 		$("#"+alertFormId).modal("show");
 		Contact_person_list_edit.show({
 	 		djid:id,//关联单据id
 			personHead : true,
 			colOptionhead : false
 		});//显示联系人
 		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
 		attachmentsObj.show({
 			djid:id,
 			fileHead : true,
 			colOptionhead : false,
 			fileType:"firm"
 		});//显示附件
 	});
}

//通过id获取供应商数据
function selectById(id,obj){
 	AjaxUtil.ajax({
 		async: false,
 		url:basePath+"/aerialmaterial/aerialmaterialfirm/selectByPrimaryKey",
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

//设置表单数据
function setData(obj){
	$("#id", $("#"+alertFormId)).val(obj.id);
	$("#gysbm", $("#"+alertFormId)).val(obj.gysbm);
	$("#gysmc", $("#"+alertFormId)).val(obj.gysmc);
	$("#gysjc", $("#"+alertFormId)).val(obj.gysjc);
	$("#pzh", $("#"+alertFormId)).val(obj.pzh);
	$("#sqkssj", $("#"+alertFormId)).val(indexOfTime(obj.sqkssj));
	$("#sqjssj", $("#"+alertFormId)).val(indexOfTime(obj.sqjssj));
	$("#zssm", $("#"+alertFormId)).val(obj.zssm);
	$("#bz", $("#"+alertFormId)).val(obj.bz);
	$("#bqydm", $("#"+alertFormId)).val(obj.bqydm);
	$("#dz", $("#"+alertFormId)).val(obj.dz);
	$("#sqfw", $("#"+alertFormId)).val(obj.sqfw);
}

//设置只读/失效/隐藏
function setView(){
	$("#gysbm", $("#"+alertFormId)).attr("readonly","readonly");
	$("#gysmc", $("#"+alertFormId)).attr("readonly","readonly");
	$("#gysjc", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pzh", $("#"+alertFormId)).attr("readonly","readonly");
	$("#sqkssj", $("#"+alertFormId)).attr("disabled","true");
	$("#sqjssj", $("#"+alertFormId)).attr("disabled","true");
	$("#zssm", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bqydm", $("#"+alertFormId)).attr("readonly","readonly");
	$("#dz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#sqfw", $("#"+alertFormId)).attr("readonly","readonly");
	$("#supplierSave", $("#"+alertFormId)).hide();
}

//移除只读/失效/隐藏
function setEdit(){
	$("#gysbm", $("#"+alertFormId)).removeAttr("readonly");
	$("#gysmc", $("#"+alertFormId)).removeAttr("readonly");
	$("#gysjc", $("#"+alertFormId)).removeAttr("readonly");
	$("#pzh", $("#"+alertFormId)).removeAttr("readonly");
	$("#sqkssj", $("#"+alertFormId)).removeAttr("disabled");
	$("#sqjssj", $("#"+alertFormId)).removeAttr("disabled");
	$("#zssm", $("#"+alertFormId)).removeAttr("readonly");
	$("#bz", $("#"+alertFormId)).removeAttr("readonly");
	$("#bqydm", $("#"+alertFormId)).removeAttr("readonly");
	$("#sqfw", $("#"+alertFormId)).removeAttr("readonly");
	$("#dz", $("#"+alertFormId)).removeAttr("readonly");
	$("#supplierSave", $("#"+alertFormId)).show();
}
	 
//修改供应商
function edit(id,dprtcode){
	$("#gysbmmark").show();
	$("#gysmcmark").show();
	openWinEdit(id,dprtcode);
}

//查看详情
function viewDetail(id){
	$("#gysbmmark").hide();
	$("#gysmcmark").hide();
	openWinView(id);
}

function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	var gysbm = $.trim($("#gysbm", $("#"+alertFormId)).val());
	var gysmc = $.trim($("#gysmc", $("#"+alertFormId)).val());
	var gysjc = $.trim($("#gysjc", $("#"+alertFormId)).val());
	var pzh = $.trim($("#pzh", $("#"+alertFormId)).val());
	var sqkssj = $.trim($("#sqkssj", $("#"+alertFormId)).val());
	var sqjssj = $.trim($("#sqjssj", $("#"+alertFormId)).val());
	var zssm = $.trim($("#zssm", $("#"+alertFormId)).val());
	var bz = $.trim($("#bz", $("#"+alertFormId)).val());
	var bqydm = $.trim($("#bqydm", $("#"+alertFormId)).val());
	var dz=$.trim($("#dz", $("#"+alertFormId)).val());
	var sqfw=$.trim($("#sqfw", $("#"+alertFormId)).val());
	if(sqkssj != '' && sqjssj != '' && TimeUtil.compareDate(sqkssj,sqjssj)){
		AlertUtil.showErrorMessage("批准日期不能大于有效期!");
		return false;
	}
	
	var supplier = {
			id : $.trim($("#id").val()),
			gyslb : $.trim($("#gyslb").val()),
			gysbm : gysbm,
			gysmc : gysmc,
			gysjc : gysjc,
			pzh : pzh,
			sqkssj : sqkssj,
			sqjssj : sqjssj,
			zssm : zssm,
			bqydm :bqydm,
			bz : bz,
			dz : dz,
			dprtcode : dprtcode,
			sqfw:sqfw
		};
	
	supplier.contactPersonList = Contact_person_list_edit.getData();
	supplier.delIds = Contact_person_list_edit.delIds;
	 
	supplier.attachments = attachmentsUtil.getAttachmentsComponents("attachments_list_edit").getAttachments();
	
	var url = basePath+"/aerialmaterial/aerialmaterialfirm/edit";
	if($.trim($("#id").val()) == null || $.trim($("#id").val()) == ''){
		url = basePath+"/aerialmaterial/aerialmaterialfirm/add";
	}
	
	startWait($("#"+alertFormId));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(supplier),
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

/**
 * 初始化导入
 */
function initImport(){
    importModal.init({
	    importUrl:"/aerialmaterial/aerialmaterialfirm/excelImport",
	    downloadUrl:"/common/templetDownfile/3",
		callback: function(data){
			goPage(1,"auto","desc");
			$("#ImportModal").modal("hide");
		}
	});
}

/**
 * 显示导入模态框
 */
function showImportModal(){
	 importModal.show();
}

function exportExcel(){
	 var gyslb = $.trim($("#gyslb").val()); 
	 var keyword = $.trim($("#keyword_search").val());
	 var sqksrq = $.trim($("#sqksrq_search").val()); 
	 var sqjsrq = $.trim($("#sqjsrq_search").val()); 
	 var whrq = $.trim($("#whrq_search").val()); 
	 var dprtcode = $.trim($("#dprtcode_search").val());
	 var sqksrqBegin="";
	 var sqksrqEnd="";
	 var sqjsrqBegin="";
	 var whrqBegin="";
	 var sqjsrqEnd="";
	 var whrqEnd="";
	 if(null != sqksrq && "" != sqksrq){
		  sqksrqBegin = sqksrq.substring(0,10)+" 00:00:00";
		  sqksrqEnd = sqksrq.substring(13,23)+" 23:59:59";
	 }
	 if(null != sqjsrq && "" != sqjsrq){
		  sqjsrqBegin = sqjsrq.substring(0,10)+" 00:00:00";
		  sqjsrqEnd = sqjsrq.substring(13,23)+" 23:59:59";
	 }
	 if(null != whrq && "" != whrq){
		  whrqBegin = whrq.substring(0,10)+" 00:00:00";
		  whrqEnd = whrq.substring(13,23)+" 23:59:59";
	 }
	window.open(basePath+"/aerialmaterial/aerialmaterialfirm/AirmaterialSupplier.xls?dprtcode="
 				+ dprtcode 
 				+ "&gyslb="+gyslb
 				+ "&sqksrqBegin="+encodeURIComponent(sqksrqBegin)
 				+ "&sqksrqEnd="+encodeURIComponent(sqksrqEnd)
 				+ "&sqjsrqBegin="+encodeURIComponent(sqjsrqBegin)
 				+ "&whrqBegin="+encodeURIComponent(whrqBegin)
 				+ "&sqjsrqEnd="+encodeURIComponent(sqjsrqEnd)
 				+ "&whrqEnd="+encodeURIComponent(whrqEnd)
 				+"&keyword="+ encodeURIComponent(keyword));
	
}

