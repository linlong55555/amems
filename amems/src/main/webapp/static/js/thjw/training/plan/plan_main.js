
var pagination = {};
var alertFormId = 'alertForm';
var headChinaId = 'modalName';
var headEnglishId = 'modalEname';
var dprtcode = userJgdm;
var jhlx = '';
var pxlb = '';
var pxxs = '';
var ksxs = '';
var pxysBz = '';
var jx = '';
var zy = '';
var course = {};
var isCheck = false;
var text_muted=0;
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	initPlaneModelSearch();
	initInfo();
	initDate();
	refreshPermission();
	formValidator();
	importExcel();
	
	//初始化日志功能
	logModal.init({code:'B_P_002'});
	
	//初始化年度
	TimeUtil.getCurrentTime(function (data){
		var year = 2017;
		if(data != null){
			year = data.substr(0,4);
		}
		$("#year_search").val(year);
		ndChange();
	});
	autoHeightResize();
	 $(window).resize(function() {
		 autoHeightResize();
	 });
	 
	 $('#pxjhExport a[data-toggle="tab"]').on('click', function (e) {
		 if(isCheck){
			 $("#fileDiv").css("display","block");
		 }
//		 new Sticky({tableId:'course_main_table'});
		 text_muted=0;
		 autoHeightResize();
	 });
	 
	 $('#ndstExport a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		 $("#fileDiv").css("display","none");
		 new Sticky({tableId:'yearTable'});
		 text_muted=$(".text-muted:visible").outerHeight()||0; 
    	
    		 
    		 if(text_muted==0){
            	 text_muted=16;
    		 }
         
		 autoHeightResize();
	 });
	 
});

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
                       
        	jhlx: {
                validators: {
                	notEmpty: {
                        message: '计划类型不能为空'
                    }
                }
            },
            kcbh: {
                validators: {
                	notEmpty: {
                        message: '课程代码不能为空'
                    }
                }
            },
            kcmc: {
                validators: {
                	notEmpty: {
                        message: '课程名称不能为空'
                    }
                }
            },
            pxlb: {
                validators: {
                    notEmpty: {
                        message: '培训类别不能为空'
                    }
                }
            },
            ks: {
                validators: {
                    notEmpty: {
                        message: '课时不能为空'
                    }
                }
            },
//            xt: {
//                validators: {
//                    notEmpty: {
//                        message: '学天不能为空'
//                    }
//                }
//            },
            jhKsrq: {
                validators: {
                    notEmpty: {
                        message: '计划开始日期不能为空'
                    }
                }
            },
            pxxs: {
                validators: {
                    notEmpty: {
                        message: '请选择培训形式'
                    }
                }
            },
            ksxs: {
                validators: {
                    notEmpty: {
                        message: '请选择考试形式'
                    }
                }
            }
//            zrr: {
//                validators: {
//                    notEmpty: {
//                        message: '主办不能为空'
//                    }
//                }
//            },
//            pxjg: {
//                validators: {
//                    notEmpty: {
//                        message: '培训机构不能为空'
//                    }
//                }
//            },
//            pxdx: {
//                validators: {
//                    notEmpty: {
//                        message: '培训对象不能为空'
//                    }
//                }
//            },
//            kcdd: {
//                validators: {
//                    notEmpty: {
//                        message: '培训地点不能为空'
//                    }
//                }
//            }
        }
    });	 
}

//隐藏Modal时验证销毁重构
$("#"+alertFormId).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     formValidator();
});

function initDate(){
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		}
	);
	
	$("#year_search").datepicker({
        language: "zh-CN",
        todayHighlight: true,
        format: 'yyyy',
        autoclose: true,
        startView: 'years',
        maxViewMode:'years',
        minViewMode:'years'
});
	
	$("#jhKsrq", $("#"+alertFormId)).datepicker({
		format:'yyyy-mm-dd',
		autoclose : true,
		clearBtn : true
	});
	$("#jhKssj", $("#"+alertFormId)).datetimepicker({
		lang:"ch", 
		format:'H:i'	,
		step:10,			 
		datepicker:false
	});
	
	$("#jhJsrq", $("#"+alertFormId)).datepicker({
		format:'yyyy-mm-dd',
		autoclose : true,
		clearBtn : true
	});
	$("#jhJssj", $("#"+alertFormId)).datetimepicker({
		lang:"ch", 
		format:'H:i'	,step:10,			 
		datepicker:false
	});
	
	$('#jhKsrq').datepicker({
		autoclose: true,
		clearBtn:true
		}).on('hide', function(e) {
		 $('#form').data('bootstrapValidator')  
		.updateStatus('jhKsrq', 'NOT_VALIDATED',null)  
		.validateField('jhKsrq');  
	});
}

function initInfo(){
	initDic();
	jhlx = $.trim($("#jhlx", $("#"+alertFormId)).val());
	pxlb = $.trim($("#pxlb", $("#"+alertFormId)).val());
	pxxs = $.trim($("#pxxs", $("#"+alertFormId)).val());
	ksxs = $.trim($("#ksxs", $("#"+alertFormId)).val());
	pxysBz = $.trim($("#pxysBz", $("#"+alertFormId)).val());
	jx = $.trim($("#jx", $("#"+alertFormId)).val());
	zy = $.trim($("#zy", $("#"+alertFormId)).val());
	DicAndEnumUtil.registerDic('34','pxlb_search',dprtcode);
}
//数据字典
function initDic(){
	$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
	$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	$("#pxlb", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('34','pxlb',dprtcode);
	$("#pxxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('32','pxxs',dprtcode);
	$("#ksxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('33','ksxs',dprtcode);
	$("#pxysBz", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('3','pxysBz',dprtcode);
	$("#zy", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('4','zy',dprtcode);
}

function initPlaneModelSearch(){
	selectFjjxByDprtcode($("#dprtcode_search").val(), function(data){
 	 	var option = '<option value="-" selected="true">显示全部All</option>';
 	 	if(data != null && data.length >0){
 			$.each(data,function(i,fjjx){
 				option += "<option value='"+StringUtil.escapeStr(fjjx)+"' >"+StringUtil.escapeStr(fjjx)+"</option>";
 			});
 	  	}
 	 	option += '<option value="">N/A</option>';
 	 	$("#fjjx_search").empty();
 	 	$("#fjjx_search").append(option);
	});
}

function initPlaneModel(dprtcode, obj){
	selectFjjxByDprtcode(dprtcode, function(data){
 	 	var option = '';
 	 	if(data != null && data.length >0){
 			$.each(data,function(i,fjjx){
 				option += "<option value='"+StringUtil.escapeStr(fjjx)+"' >"+StringUtil.escapeStr(fjjx)+"</option>";
 			});
 	  	}
 	 	option += '<option value="">N/A</option>';
 	 	$("#jx", $("#"+alertFormId)).empty();
 	 	$("#jx", $("#"+alertFormId)).append(option);
 	 	if(typeof(obj)=="function"){
			obj(); 
		}
	});
}

//通过id获取航材数据
function selectFjjxByDprtcode(dprtcode, obj){
	
	AjaxUtil.ajax({
	 	   url:basePath+"/project/planemodeldata/findAllType",
	 	   async: false,
	 	   type: "post",
	 	   dataType:"json",
	 	   data:{dprtcode:dprtcode},
	 	   success:function(data){
	 		  if(typeof(obj)=="function"){
					obj(data); 
	 		  }
	 	   }
	    }); 
}

//改变组织机构触发
function dpartmentChange(){
	dprtcode = $.trim($("#dprtcode_search").val());
	initPlaneModelSearch();
	$("#pxlb_search").empty();
	$("#pxlb_search").append('<option value="" selected="true">显示全部All</option>');
	DicAndEnumUtil.registerDic('34','pxlb_search',dprtcode);
	ndChange();
}

//年度改变时加载年度计划信息、列表信息
function ndChange(){
	if($.trim($("#year_search").val()) == null || $.trim($("#year_search").val()) == ''){
		AlertUtil.showErrorMessage("年度不能为空!");
		return false;
	}
	loadYearPlan();
	searchRevision();
}

//加载年度计划信息
function loadYearPlan(){
	var nd = $.trim($("#year_search").val());
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/yearplan/selectByNdAndDprt",
		type:"post",
		data:{nd:nd,dprtcode : dprtcode},
		dataType:"json",
		success:function(data){
			var ndId = '';
			var ndjhmc = nd;
			var fileCount = 0;
			if(data != null){
				ndId = data.id;
				ndjhmc = data.ndjhmc;
				fileCount = data.paramsMap.fjsl;
			}
			$("#ndId").val(ndId);
			$("#ndjhmcView").val(ndjhmc);
			$("#ndjhmcDiv").attr("title",StringUtil.title(ndjhmc,25));
    		$("#ndjhmcDiv").text(StringUtil.subString(ndjhmc,25));
    		$("#fileCount").text(fileCount);
		}
	});
}
//打开年度计划编辑页面
function openNdWinEdit(){
	if($.trim($("#year_search").val()) == null || $.trim($("#year_search").val()) == ''){
		AlertUtil.showErrorMessage("请选择年度!");
		return false;
	}
	$("#ndjhmc").val($("#ndjhmcView").val());
	$("#alertYearPlanModal").modal("show");
}
//保存年度计划
function saveYearPlan(){
	var ndjhmc = $.trim($("#ndjhmc", $("#alertYearPlanModal")).val());
	
	if(ndjhmc == "" || ndjhmc == null){
		AlertUtil.showErrorMessage("名称不能为空!");
		return false;
	}
	
	var param = {
			ndjhmc : ndjhmc,
			nd : $.trim($("#year_search").val()),
			dprtcode : dprtcode
		};
	
	
	var url = basePath+"/training/yearplan/addOrUpdate";
	
	startWait($("#alertYearPlanModal"));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		modal:$("#alertYearPlanModal"),
		success:function(data){
			finishWait($("#alertYearPlanModal"));
			AlertUtil.showMessage('保存成功!');
			$("#ndId").val(data);
			$("#ndjhmcView").val(ndjhmc);
			$("#ndjhmcDiv").attr("title",StringUtil.title(ndjhmc,15));
    		$("#ndjhmcDiv").text(StringUtil.subString(ndjhmc,15));
			$("#alertYearPlanModal").modal("hide");
		}
	});
}

//上传附件
function openAttachmentWinEdit(){
	if($.trim($("#year_search").val()) == null || $.trim($("#year_search").val()) == ''){
		AlertUtil.showErrorMessage("请选择年度!");
		return false;
	}
	open_win_attachments_list_edit.show({
		djid:$("#ndId").val(),
 		colOptionhead : true,
		fileHead : true,
		fileType:"year",
		callback: function(attachments){//回调函数
			var param = {
					ndjhmc : $("#ndjhmcView").val(),
					nd : $.trim($("#year_search").val()),
					dprtcode : dprtcode
				};
			param.attachments = attachments;
			// 提交数据
			AjaxUtil.ajax({
				url:basePath+"/training/yearplan/addOrUpdate",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					AlertUtil.showMessage('保存成功!');
					$("#ndId").val(data);
					$("#fileCount").text(open_win_attachments_list_edit.getAttachmentCount());
					$("#open_win_attachments_list_edit").modal("hide");
				}
			});
		}
	});//显示附件
}

 //带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	$("#fileDiv").css("display","none");
	/*showOrHideDetail();*/
	autoHeightResize();
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	url = basePath+"/training/plan/queryAllPageList?id="+id;
	
	startWait();
	AjaxUtil.ajax({
		 url:url,
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
			   signByKeyword($("#keyword_search").val(),[3,4,7,11,12,23],"#list tr td");
		   } else {
			  $("#list").empty();
			  $("#pagination").empty();
			  $("#list").append("<tr><td colspan=\"26\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'course_main_table'});
      }
    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 var jhlx = $.trim($("#jhlx_search").val());
		 var jhrq = $.trim($("#jhrq_search").val());
		 var pxlb = $.trim($("#pxlb_search").val());
		 var zt = $.trim($("#zt_search").val());
		 var nd = $.trim($("#year_search").val());
		 var fjjx = $.trim($("#fjjx_search").val());
		 if(fjjx != "-"){
			 paramsMap.qjx = "qjx";
			 searchParam.fjjx = fjjx;
		 }
		 if('' != jhlx){
			 searchParam.jhlx = jhlx;
		 }
		 if('' != pxlb){
			 searchParam.pxlb = pxlb;
		 }
		 if('' != zt){
			 searchParam.zt = zt;
		 }
		 var fxbsList = [];
		 $('input:checkbox[name=fxbs_search]:checked').each(function(){
			 fxbsList.push($(this).val());
			 
		 });
		 if(fxbsList.length == 0){
			 fxbsList.push(3);
		 }
		 
		 var jhrqBegin = nd + "-01-01 00:00:00";
		 var jhrqEnd = nd + "-12-31 23:59:59";
		 if(null != jhrq && "" != jhrq){
			 var jhrqBeginSearch = jhrq.substring(0,10) + " 00:00:00";
			 var jhrqEndSearch = jhrq.substring(13,23) + " 23:59:59";
			 if(TimeUtil.compareDate(jhrqBeginSearch,jhrqBegin)){
				 jhrqBegin = jhrqBeginSearch;
			 }
			 if(TimeUtil.compareDate(jhrqEnd,jhrqEndSearch)){
				 jhrqEnd = jhrqEndSearch;
			 }
		 }
		 
		 paramsMap.year = nd;
		 paramsMap.jhrqBegin = jhrqBegin;
		 paramsMap.jhrqEnd = jhrqEnd;
		 paramsMap.fxbsList = fxbsList;
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.id+"' kcid='"+(row.course?row.course.id:'')+"' dprtcode='"+row.dprtcode+"' kcbh='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectRow(this)>";
			   } else {
				   htmlContent += "<tr id='"+row.id+"' kcid='"+(row.course?row.course.id:'')+"' dprtcode='"+row.dprtcode+"' kcbh='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectRow(this)>";
			   }
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   if(row.zt == 0){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:02' onClick=\"openWinEdit('"+ row.id + "','" + row.dprtcode + "')\" title='修改 Edit'>&nbsp;&nbsp;</i>";
				   htmlContent += "<i class='icon-tumblr-sign color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:04' onClick=\"issued('"+ row.id + "')\" title='下发 Issued'>&nbsp;&nbsp;</i>";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:03' onClick=\"cancel('"+ row.id + "')\" title='作废 Invalid'>&nbsp;&nbsp;</i>";  
			   }
			   if(row.zt == 1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:02' onClick=\"openWinEdit('"+ row.id + "','" + row.dprtcode + "')\" title='修改 Edit'>&nbsp;&nbsp;</i>";
				   htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='training:plan:main:05' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' zdjsyy='' zdjsr='' zdjsrq='' onClick=\"shutDown(this,true)\" title='指定结束 End'>&nbsp;&nbsp;</i>";
			   }
			   htmlContent += "<i class='icon-eye-open color-blue cursor-pointer' onClick=\"findto('" + row.id + "')\" title='查看培训计划 View Training Plan'></i>";
			   htmlContent += "</td>";  
			   
			   htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx)+"' class='text-center'>";
			   htmlContent += DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx);
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"findCourse('" + (row.course?row.course.id:"") + "')\">"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.course?row.course.kcmc:"")+"' class='text-left'>"+StringUtil.escapeStr(row.course?row.course.kcmc:"")+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxlb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxlb)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zrr)+"' class='text-left'>"+StringUtil.escapeStr(row.zrr)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxdx)+"' class='text-left'>"+StringUtil.escapeStr(row.pxdx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxjg)+"' class='text-left'>"+StringUtil.escapeStr(row.pxjg)+"</td>";
			   var jhrq = indexOfTime(row.jhKsrq);
			   if(row.jhJsrq != null && indexOfTime(row.jhKsrq) != indexOfTime(row.jhJsrq)){
				   jhrq += " ~ "+indexOfTime(row.jhJsrq);
			   }
			   htmlContent += "<td title='"+jhrq+"' class='text-center'>"+jhrq+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.jsxm)+"' class='text-left'>"+StringUtil.escapeStr(row.jsxm)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcdd)+"' class='text-left'>"+StringUtil.escapeStr(row.kcdd)+"</td>";
			   htmlContent += "<td class='text-center'>"+(row.fxbs == 1?"初训":"复训")+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.ks)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.xt)+"</td>";
			   var fxjg = '';
			   if(row.course != null){
				   fxjg = formatUndefine(row.course.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',row.course.zqdw);
			   }
			   htmlContent += "<td title='"+fxjg+"' class='text-left'>"+fxjg+"</td>";
			   
			   var pxysValue = formatUndefine(row.pxys);
			   if(pxysValue != ''){
				   pxysValue += StringUtil.escapeStr(row.pxysBz);
			   }
			   htmlContent += "<td title='"+pxysValue+"' class='text-left'>"+pxysValue+"</td>";
			   var sjrq = indexOfTime(row.sjKsrq);
			   if(row.sjJsrq != null && indexOfTime(row.sjKsrq) != indexOfTime(row.sjJsrq)){
				   sjrq += " ~ "+indexOfTime(row.sjJsrq);
			   }
			   htmlContent += "<td title='"+sjrq+"' class='text-center'>"+sjrq+"</td>";
			   htmlContent += "<td class='text-center'>";
			   if(row.course != null && row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
				   htmlContent += "<i class='icon-file color-blue cursor-pointer' onClick=\"openCoursewareView('"+ row.course.id + "')\" title='课件 Courseware'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-left'>";
			   if(row.zt == 9){
				   var zdjsr = row.jsr?StringUtil.escapeStr(row.jsr.displayName):'';
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+formatUndefine(row.zdjsrq)+"' onclick=\"shutDown(this,false)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('trainingPlanStatusEnum',row.zt);
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   if(undefined != row.zdr){
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }
			   else{
				   htmlContent += "<td class='text-left'>"+"</td>";
			   }
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.paramsMap.dprtname)+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	//带条件搜索的翻页
	 function AjaxPlanViewSearch(pageNumber,sortType,sequence){
	 	var searchParam = gatherSearchParam();
	 	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	 	searchParam.pagination = pagination;
//	 	searchParam.paramsMap.ztList = [0,1,10];
	 	url = basePath+"/training/plan/queryYearPageList";
	 	startWait();
	 	AjaxUtil.ajax({
	 		 url:url,
	 	   type: "post",
	 	   contentType:"application/json;charset=utf-8",
	 	   dataType:"json",
	 	   data:JSON.stringify(searchParam),
	 	   success:function(data){
	 		   finishWait();
	 		   var thCount = loadTableHead(data.thStyleMap);
	 		   if(data.total >0){
	 			   appendYearContentHtml(data.rows,data.thStyleMap);
	 			   new Pagination({
	 					renderTo : "year_view_Pagination",
	 					data: data,
	 					sortColumn : sortType,
	 					orderType : sequence,
	 					goPage:function(pageNumber,sortType,sequence){
	 						AjaxPlanViewSearch(pageNumber,sortType,sequence);
	 					}
	 				}); 
	 			   // 标记关键字
	 			   signByKeyword($("#keyword_search").val(),[2,3,6,9,11,12],"#year_view_list tr td");
	 		   } else {
	 			  $("#year_view_list").empty();
	 			  $("#year_view_Pagination").empty();
	 			 $("#year_view_list").append("<tr><td colspan=\""+(14+thCount)+"\" class='text-center'>暂无数据 No data.</td></tr>");
	 		   }
	 		  new Sticky({tableId:'yearTable'});
	       }
	     }); 
	 		
	 	 }
	 
	 function appendYearContentHtml(list,thStyleMap){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   var bgcolor = "#fefefe";
			   if (index % 2 == 0) {
				   bgcolor = "#f9f9f9";
			   }
			   if(row.zt == 9){
				   bgcolor = "#e5e5e5";
//				   bgcolor = "#FFFFFF";
			   }
			   htmlContent += "<tr class='"+row.id+"' bgcolor='"+bgcolor+"' onclick=selectYearRow(this)>";
			   htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx)+"' class='fixed-column text-center'>";
			   htmlContent += DicAndEnumUtil.getEnumName('trainingPlanTypeEnum',row.jhlx);
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"' class='fixed-column text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"findCourse('" + (row.course?row.course.id:"") + "')\">"+StringUtil.escapeStr(row.course?row.course.kcbh:"")+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.course?row.course.kcmc:"")+"' class='fixed-column text-left'>"+StringUtil.escapeStr(row.course?row.course.kcmc:"")+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxlb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxlb)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zrr)+"' class='text-left'>"+StringUtil.escapeStr(row.zrr)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxdx)+"' class='text-left'>"+StringUtil.escapeStr(row.pxdx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.jsxm)+"' class='text-left'>"+StringUtil.escapeStr(row.jsxm)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   if(row.course != null && row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
				   htmlContent += "<i class='icon-file color-blue cursor-pointer' onClick=\"openCoursewareView('"+ row.course.id + "')\" title='课件 Courseware'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcdd)+"' class='text-left'>"+StringUtil.escapeStr(row.kcdd)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   htmlContent += "<td title='"+indexOfTime(row.jhKsrq)+"' class='text-center'>"+indexOfTime(row.jhKsrq)+"</td>";
			   htmlContent += "<td title='"+indexOfTime(row.sjKsrq)+"' class='text-center'>"+indexOfTime(row.sjKsrq)+"</td>";
			  
			   var jhrqTitle = "计划: "+indexOfTime(row.jhKsrq);
//			   jhrqTitle += " "+formatUndefine(row.jhKssj);
			   if(row.jhJsrq != null && indexOfTime(row.jhKsrq) != indexOfTime(row.jhJsrq)){
				   jhrqTitle += " ~ "+indexOfTime(row.jhJsrq);
//				   jhrqTitle += " "+formatUndefine(row.jhJssj);
			   }
			   
			   var sjrqTitle = "实际: "+indexOfTime(row.sjKsrq);
//			   sjrqTitle += " "+formatUndefine(row.sjKssj);
			   if(row.sjJsrq != null && indexOfTime(row.sjKsrq) != indexOfTime(row.sjJsrq)){
				   sjrqTitle += " ~ "+indexOfTime(row.sjJsrq);
//				   sjrqTitle += " "+formatUndefine(row.sjJssj);
			   }
			   
			   var jh = row.paramsMap.jh;
			   var sj = row.paramsMap.sj;
			   var jhMonth = 0;
			   var jsWeek = 0;
			   var sjMonth = 0;
			   var sjWeek = 0;
			   if(jh != null && jh != ''){
				   jhMonth = jh.month;
				   jsWeek = jh.week;
			   }
			   if(sj != null && sj != ''){
				   sjMonth = sj.month;
				   sjWeek = sj.week;
			   }
			   for(var i = 0 ; i< 12 ;i++){
					var zqs = thStyleMap[i];
					for(var j = 0 ; j < zqs ; j++){
						var h = '';
						htmlContent += "<td class='text-center'>";
						if(jhMonth == (i + 1) && jsWeek == (j + 1)){
							h = "<i class='icon-circle-blank color-black pull-center' title='"+jhrqTitle+"'></i>";
						}
						if(TimeUtil.compareDate($.trim($("#year_search").val()) + "-12-31",indexOfTime(row.sjKsrq))){ 
							if(sjMonth == (i + 1) && sjWeek == (j + 1)){
								h = "<i class='icon-circle color-green pull-center' title='"+sjrqTitle+"'></i>";
								if(sjMonth == jhMonth && sjWeek == jsWeek){
									var jhsjTitle = jhrqTitle+"&#13"+sjrqTitle;
									h = "<i class='icon-adjust color-green pull-center' title='"+jhsjTitle+"'></i>";
								}
							}
						}
						htmlContent += h;
						htmlContent += "</td>";
					}
				}
			   htmlContent += "</tr>";  
			    
		   });
		   $("#year_view_list").empty();
		   $("#year_view_list").html(htmlContent);
	 }
	 
function loadTableHead(thStyleMap){
	var thCount = 0;
	var tr = '';
	for(var i = 0 ; i<$('.month',$("#yearTable")).length;i++){
		var zqs = thStyleMap[i];
		$('.month',$("#yearTable")).eq(i).attr("colspan",zqs);
		for(var j = 0 ; j < zqs ; j++){
			tr += '<th class="colwidth-3"><div >'+(j+1)+'</div></th>';
		}
		thCount += zqs;
	}
	$("#trHead",$("#yearTable")).empty();
	$("#trHead",$("#yearTable")).html(tr);
	return thCount;
}

//打开新增弹出框
function openWinAdd(){
	dprtcode = userJgdm;
	course = {};
	checkPersonList = [];
	$("#"+headChinaId, $("#"+alertFormId)).html('新增');
 	$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
	initPlaneModel(dprtcode, function(){
	 	//数据字典
		initDic();
	 	var obj = {};
	 	obj.fxbs = 1;
	 	obj.jsBz = 1;
	 	obj.isJcff = 1;
	 	obj.jhlx = jhlx;
	 	obj.pxlb = pxlb;
	 	obj.pxxs = pxxs;
	 	obj.ksxs = ksxs;
	 	obj.pxysBz = pxysBz;
	 	obj.jx = jx;
	 	obj.zy = zy;
	 	obj.personName = '';
	 	obj.fjjx = "";
	 	setEdit();//设置可编辑
	 	setData(obj);//设置表单数据
	 	$("#"+alertFormId).modal("show");
	 	$("#kcbtn", $("#"+alertFormId)).removeAttr("disabled");
	});
}

//打开编辑课程弹出框
function openWinEdit(id,dprtcode_){
	dprtcode = dprtcode_;
	course = {};
	checkPersonList = [];
	$("#"+headChinaId, $("#"+alertFormId)).html('编辑');
	$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
	initPlaneModel(dprtcode, function(){
		selectById(id,function(obj){
			//数据字典
			initDic();
			setEdit();//设置只读/失效
			setData(obj);//设置表单数据
			disabledFx(obj.isFx);
			$("#"+alertFormId).modal("show");
			if(obj.zt == 1){
				$("#jhlx", $("#"+alertFormId)).attr("disabled","true");
				$("#kcbtn", $("#"+alertFormId)).attr("disabled","true");
			}else{
				$("#kcbtn", $("#"+alertFormId)).removeAttr("disabled");
			}
		});
	});
}

//打开查看课程弹出框
function openWinView(id,dprtcode_){
	dprtcode = dprtcode_;
	course = {};
	checkPersonList = [];
	$("#"+headChinaId, $("#"+alertFormId)).html('查看');
	$("#"+headEnglishId, $("#"+alertFormId)).html('View');
	initPlaneModel(dprtcode, function(){
		selectById(id,function(obj){
			//数据字典
			initDic();
			setData(obj);//设置表单数据
			$("#"+alertFormId).modal("show");
			setView();//设置只读/失效
			if(obj.fjjx == null || obj.fjjx == ''){
				$("#jx", $("#"+alertFormId)).text('');
			}
		});
	});
}

//打开讲师对话框
function openLecturerWin() {
	var sqksrq=$("#jhKsrq").val();
	var kcid=$("#kcid").val();
	if(sqksrq==''||kcid==''){
		AlertUtil.showErrorMessage("课程代码和计划开始日期不能为空!");
		return false;
	}
	var param = {};
	param.sqksrq=sqksrq;
	param.kcid=kcid;
	param.selected=$("#jsid").val();
	param.callback = function(data){
		$("#jsxm").val(data.xm);
		$("#jsid").val(data.id);
	},
	lecturer_user.show(param);
}


var checkPersonList = [];//储存选中的人员
//打开调整列表对话框
function openPersonelWin() {
	var this_ = this;
	Personel_Tree_Multi_Modal.show({
		checkUserList:checkPersonList,//原值，在弹窗中回显
		dprtcode:dprtcode,//
		callback: function(data){//回调函数
			checkPersonList = [];
			var personName = '';
			if(data != null && data != ""){
				checkPersonList = data;
				$.each(data, function(i, row){
					personName += formatUndefine(row.displayName) + ",";
				})
			}
			if(personName != ''){
				personName = personName.substring(0,personName.length - 1);
			}
			$("#personName").text(personName);
		}
	})
}

//打开课程列表对话框
function openCourseWin() {
	open_win_course_modal.show({
		selected : $("#kcid").val(),// 原值，在弹窗中默认勾选
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
			if (data != null && data.id != null) {
				course = data;
				$("#kcid", $("#"+alertFormId)).val(formatUndefine(data.id));
				$("#kcbh", $("#"+alertFormId)).val(formatUndefine(data.kcbh));
				$("#kcmc", $("#"+alertFormId)).val(formatUndefine(data.kcmc));
				$("#kcnr", $("#"+alertFormId)).val(formatUndefine(data.kcnr));
				$("#jx", $("#"+alertFormId)).val(formatUndefine(data.fjjx));
				$("input:radio[name='fxbs'][value='1']", $("#"+alertFormId)).attr("checked",true); 
				disabledFx(data.isFx);
				checkFxbs();
				$('#form').data('bootstrapValidator')  
	 		      .updateStatus('kcbh', 'NOT_VALIDATED',null)  
	 		      .validateField('kcbh');  
				$('#form').data('bootstrapValidator')  
				.updateStatus('kcmc', 'NOT_VALIDATED',null)  
				.validateField('kcmc');
				$('#form').data('bootstrapValidator')  
				.updateStatus('ks', 'NOT_VALIDATED',null)  
				.validateField('ks');
				$('#form').data('bootstrapValidator')  
				.updateStatus('pxxs', 'NOT_VALIDATED',null)  
				.validateField('pxxs');
				$('#form').data('bootstrapValidator')  
				.updateStatus('ksxs', 'NOT_VALIDATED',null)  
				.validateField('ksxs');
				
				$("#jsid").val('');
				$("#jsxm").val('');
			}
		}
	})
}

/**
 * 变更计划开始日期时清空讲师id和描述
 */
function onchangeDate(){
	$("#jsid").val('');
	$("#jsxm").val('');
}

/**
 * 讲师改变事件
 * @returns
 */
function onLecturerChanged(){
	$("#jsid").val("");
}

function openCoursewareView(id){
	open_win_attachments_list_edit.show({
		attachHead : true,
		chinaHead : '课件信息',
		englishHead : 'Courseware Information',
		chinaFileHead : '课件名',
		englishFileHead : 'Courseware',
		chinaColFileTitle : '课件名',
		englishColFileTitle : 'Courseware',
		djid:id,
 		colOptionhead : false,
		fileHead : false,
		fileType:"course",
		callback: function(attachments){//回调函数
		}
	});//显示附件
}

function loadAttachmentEdit(id,colOptionhead,fileHead){
	AttachmengsList.show({
		attachHead : true,
		chinaHead : '课件信息',
		englishHead : 'Courseware Information',
		chinaFileHead : '课件名',
		englishFileHead : 'Courseware',
		chinaColFileTitle : '课件名',
		englishColFileTitle : 'Courseware',
		djid:id,
 		colOptionhead : colOptionhead,
		fileHead : fileHead,
		fileType:"course"
	});//显示附件
}

//通过id获取培训计划数据
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/plan/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				var course = data.course;
				var planPersonList = data.planPersonList;
				if(planPersonList != null && planPersonList.length > 0){
					var personName = '';
					$.each(planPersonList, function(i, row){
						var planPerson = {
								id : row.wxrydaid,
								xm : row.xm
						};
						if(row.maintenancePersonnel != null){
							var displayName = StringUtil.escapeStr(row.maintenancePersonnel.rybh) + " " + StringUtil.escapeStr(row.maintenancePersonnel.xm);
							personName += displayName + ",";
							planPerson.displayName = displayName;
							checkPersonList.push(planPerson);
						}
					});
					
					if(personName != ''){
						personName = personName.substring(0,personName.length - 1);
					}
					data.personName = personName;
				}
				
				if(null != course){
					data.kcbh = course.kcbh;
					data.kcmc = course.kcmc;
					data.isFx = course.isFx;
				}
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
	$("#jhlx", $("#"+alertFormId)).val(obj.jhlx);
	$("#pxlb", $("#"+alertFormId)).val(obj.pxlb);
	$("#kcbh", $("#"+alertFormId)).val(obj.kcbh);
	$("#kcid", $("#"+alertFormId)).val(obj.kcid);
	$("#kcmc", $("#"+alertFormId)).val(obj.kcmc);
	$("input:radio[name='fxbs'][value='"+obj.fxbs+"']", $("#"+alertFormId)).attr("checked",true); 
	$("#ks", $("#"+alertFormId)).val(obj.ks);
	$("#pxxs", $("#"+alertFormId)).val(obj.pxxs);
	$("#ksxs", $("#"+alertFormId)).val(obj.ksxs);
	$("#jhKsrq", $("#"+alertFormId)).val(indexOfTime(obj.jhKsrq));
	$("#jhKssj", $("#"+alertFormId)).val(obj.jhKssj);
	$("#jhJsrq", $("#"+alertFormId)).val(indexOfTime(obj.jhJsrq));
	$("#jhJssj", $("#"+alertFormId)).val(obj.jhJssj);
	$("#xt", $("#"+alertFormId)).val(obj.xt);
	$("#zrr", $("#"+alertFormId)).val(obj.zrr);
	$("#pxjg", $("#"+alertFormId)).val(obj.pxjg);
	$("#pxdx", $("#"+alertFormId)).val(obj.pxdx);
	$("#pxys", $("#"+alertFormId)).val(obj.pxys);
	$("#pxysBz", $("#"+alertFormId)).val(obj.pxysBz);
	$("#kcdd", $("#"+alertFormId)).val(obj.kcdd);
	$("#jx", $("#"+alertFormId)).val(obj.fjjx);
	$("#zy", $("#"+alertFormId)).val(obj.zy);
	$("#jsxm", $("#"+alertFormId)).val(obj.jsxm);
	$("#jsid", $("#"+alertFormId)).val(obj.jsid);
	$("input:radio[name='jsBz'][value='"+obj.jsBz+"']", $("#"+alertFormId)).attr("checked",true);
	$("input:radio[name='isJcff'][value='"+obj.isJcff+"']", $("#"+alertFormId)).attr("checked",true);
	$("#personName", $("#"+alertFormId)).text(obj.personName);
	$("#kcnr", $("#"+alertFormId)).val(obj.kcnr);
	$("#bz", $("#"+alertFormId)).val(obj.bz);
}

//设置只读/失效/隐藏
function setView(){
	$("#ks", $("#"+alertFormId)).attr("readonly","readonly");
	$("#xt", $("#"+alertFormId)).attr("readonly","readonly");
	$("#zrr", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxjg", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxdx", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxys", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcdd", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcnr", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#jhlx", $("#"+alertFormId)).attr("disabled","true");
	$("#pxlb", $("#"+alertFormId)).attr("disabled","true");
	$("#pxxs", $("#"+alertFormId)).attr("disabled","true");
	$("#ksxs", $("#"+alertFormId)).attr("disabled","true");
	$("#pxysBz", $("#"+alertFormId)).attr("disabled","true");
	$("#jx", $("#"+alertFormId)).attr("disabled","true");
	$("#zy", $("#"+alertFormId)).attr("disabled","true");
	$("input:radio[name='fxbs']", $("#"+alertFormId)).attr("disabled","true");
	$("input:radio[name='jsBz']", $("#"+alertFormId)).attr("disabled","true");
	$("input:radio[name='isJcff']", $("#"+alertFormId)).attr("disabled","true");
	$("#jhKsrq", $("#"+alertFormId)).attr("disabled","true");
	$("#jhKssj", $("#"+alertFormId)).attr("disabled","true");
	$("#jhJsrq", $("#"+alertFormId)).attr("disabled","true");
	$("#jhJssj", $("#"+alertFormId)).attr("disabled","true");
	$("#kcbtn", $("#"+alertFormId)).hide();
	$("#jsbtn", $("#"+alertFormId)).hide();
	$("#personBtn", $("#"+alertFormId)).hide();
	$("#planSave", $("#"+alertFormId)).hide();
	$("#personDiv", $("#"+alertFormId)).removeClass("input-group");
	$(".identifying").hide();
}

//移除只读/失效/隐藏
function setEdit(){
	$("#ks", $("#"+alertFormId)).removeAttr("readonly");
	$("#xt", $("#"+alertFormId)).removeAttr("readonly");
	$("#zrr", $("#"+alertFormId)).removeAttr("readonly");
	$("#pxjg", $("#"+alertFormId)).removeAttr("readonly");
	$("#pxdx", $("#"+alertFormId)).removeAttr("readonly");
	$("#pxys", $("#"+alertFormId)).removeAttr("readonly");
	$("#kcdd", $("#"+alertFormId)).removeAttr("readonly");
	$("#kcnr", $("#"+alertFormId)).removeAttr("readonly");
	$("#bz", $("#"+alertFormId)).removeAttr("readonly");
	$("#jhlx", $("#"+alertFormId)).removeAttr("disabled");
	$("#pxlb", $("#"+alertFormId)).removeAttr("disabled");
	$("#pxxs", $("#"+alertFormId)).removeAttr("disabled");
	$("#ksxs", $("#"+alertFormId)).removeAttr("disabled");
	$("#pxysBz", $("#"+alertFormId)).removeAttr("disabled");
	$("#jx", $("#"+alertFormId)).removeAttr("disabled");
	$("#zy", $("#"+alertFormId)).removeAttr("disabled");
	$("input:radio[name='fxbs']", $("#"+alertFormId)).removeAttr("disabled");
	$("input:radio[name='jsBz']", $("#"+alertFormId)).removeAttr("disabled");
	$("input:radio[name='isJcff']", $("#"+alertFormId)).removeAttr("disabled");
	$("#jhKsrq", $("#"+alertFormId)).removeAttr("disabled");
	$("#jhKssj", $("#"+alertFormId)).removeAttr("disabled");
	$("#jhJsrq", $("#"+alertFormId)).removeAttr("disabled");
	$("#jhJssj", $("#"+alertFormId)).removeAttr("disabled");
	$("#kcbtn", $("#"+alertFormId)).show();
	$("#jsbtn", $("#"+alertFormId)).show();
	$("#personBtn", $("#"+alertFormId)).show();
	$("#planSave", $("#"+alertFormId)).show();
	$("#personDiv", $("#"+alertFormId)).addClass("input-group");
	$(".identifying").show();
}

//设置复训标识失效
function disabledFx(isFx){
	if(isFx == 1){
		$("input:radio[name='fxbs']", $("#"+alertFormId)).removeAttr("disabled");
	}else{
		$("input:radio[name='fxbs']", $("#"+alertFormId)).attr("disabled","true");
	}
}

//选中复训
function checkFxbs(){
	var type = $("input[name='fxbs']:checked",$("#"+alertFormId)).val();
	if(course != null && course.id != null){
		if(type == 2 && course.isFx == 1){
			$("#ks", $("#"+alertFormId)).val(course.fxks);
			$("#pxxs", $("#"+alertFormId)).val(course.fxpxxs);
			$("#ksxs", $("#"+alertFormId)).val(course.fxksxs);
		}else{
			$("#ks", $("#"+alertFormId)).val(course.ks);
			$("#pxxs", $("#"+alertFormId)).val(course.pxxs);
			$("#ksxs", $("#"+alertFormId)).val(course.ksxs);
		}
	}
}
//保存培训计划
function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		return false;
	}
	
	var jhlx = $.trim($("#jhlx", $("#"+alertFormId)).val());
	var kcid = $.trim($("#kcid", $("#"+alertFormId)).val());
	var kcbh = $.trim($("#kcbh", $("#"+alertFormId)).val());
	var kcmc = $.trim($("#kcmc", $("#"+alertFormId)).val());
	var pxlb = $.trim($("#pxlb", $("#"+alertFormId)).val());
	var ks = $.trim($("#ks", $("#"+alertFormId)).val());
	var pxxs = $.trim($("#pxxs", $("#"+alertFormId)).val());
	var ksxs = $.trim($("#ksxs", $("#"+alertFormId)).val());
	var jhKsrq = $.trim($("#jhKsrq", $("#"+alertFormId)).val());
	var jhKssj = $.trim($("#jhKssj", $("#"+alertFormId)).val());
	var jhJsrq = $.trim($("#jhJsrq", $("#"+alertFormId)).val());
	var jhJssj = $.trim($("#jhJssj", $("#"+alertFormId)).val());
	var xt = $.trim($("#xt", $("#"+alertFormId)).val());
	var zrr = $.trim($("#zrr", $("#"+alertFormId)).val());
	var pxjg = $.trim($("#pxjg", $("#"+alertFormId)).val());
	var pxdx = $.trim($("#pxdx", $("#"+alertFormId)).val());
	var pxys = $.trim($("#pxys", $("#"+alertFormId)).val());
	var pxysBz = $.trim($("#pxysBz", $("#"+alertFormId)).val());
	var kcdd = $.trim($("#kcdd", $("#"+alertFormId)).val());
	var jx = $.trim($("#jx", $("#"+alertFormId)).val());
	var zy = $.trim($("#zy", $("#"+alertFormId)).val());
	var jsxm = $.trim($("#jsxm", $("#"+alertFormId)).val());
	var jsid = $.trim($("#jsid", $("#"+alertFormId)).val());
	var kcnr = $.trim($("#kcnr", $("#"+alertFormId)).val());
	var bz = $.trim($("#bz", $("#"+alertFormId)).val());
	var fxbs = $("input:radio[name='fxbs']:checked", $("#"+alertFormId)).val(); 
	var jsBz = $("input:radio[name='jsBz']:checked", $("#"+alertFormId)).val(); 
	var isJcff = $("input:radio[name='isJcff']:checked", $("#"+alertFormId)).val(); 
	
	if(kcid == "" || kcid == null){
		AlertUtil.showErrorMessage("请选择课程!");
		return false;
	}
	
	var regu = /^[0-9]+$/;
	var regu2 = /^[0-9]+\.?[0-9]{0,2}$/;
	
	if(ks == "" || ks == null){
		AlertUtil.showErrorMessage("课时不能为空!");
		return false;
	}
	if (!regu2.test(ks)) {
        AlertUtil.showErrorMessage("课时只能输入数字,并保留两位小数！");
        return false;
    }
	
	if(jhKsrq == "" || jhKsrq == null){
		AlertUtil.showErrorMessage("计划开始日期不能为空!");
		return false;
	}
	
	if(jhJssj != "" && jhJssj != null && (jhJsrq == null || jhJsrq == "")){
		jhJssj = "";
		/*AlertUtil.showErrorMessage("计划结束日期不能为空!");
		return false;*/
	}
	
	if(jhJsrq != "" && jhJsrq != null){
		var tempJhkssj = (jhKssj == ""?"00:00":jhKssj);
		var tempJhJssj = (jhJssj == ""?"00:00":jhJssj);
		if(TimeUtil.compareDate(jhKsrq +" " + tempJhkssj + ":00",jhJsrq +" " + tempJhJssj + ":00")){
			AlertUtil.showErrorMessage("计划开始日期不能大于计划结束日期!");
			return false;
		}
	}
	
//	if(xt == "" || xt == null){
//		AlertUtil.showErrorMessage("学天不能为空!");
//		return false;
//	}
	if (xt&&!regu.test(xt)) {
        AlertUtil.showErrorMessage("学天只能输入数字！");
        return false;
    }
	if(pxys != null & pxys != ""){
		var regPxys = /^[0-9]+\.?[0-9]{0,2}$/;
		if (!regPxys.test(pxys)) {
	        AlertUtil.showErrorMessage("培训预算只能输入数字,并保留两位小数！");
	        return false;
	    }
    }
	
	var param = {
			id : $.trim($("#id").val()),
			jhlx : jhlx,
			kcid : kcid,
			pxlb : pxlb,
			ks : ks,
			pxxs : pxxs,
			ksxs : ksxs,
			jhKsrq : jhKsrq,
			jhKssj : jhKssj,
			jhJsrq : jhJsrq,
			jhJssj : jhJssj,
			xt : xt,
			zrr : zrr,
			pxjg : pxjg,
			pxdx : pxdx,
			pxys : pxys,
			pxysBz : pxysBz,
			kcdd : kcdd,
			fjjx : jx,
			zy : zy,
			jsxm : jsxm,
			jsid : jsid,
			kcnr : kcnr,
			bz : bz,
			fxbs : fxbs,
			jsBz : jsBz,
			isJcff : isJcff,
			dprtcode : dprtcode
		};
	var planPersonList = [];
	$.each(checkPersonList, function(i, row){
		var planPerson = {
				wxrydaid : row.id,
				xm : row.xm,
				gzdw : row.szdw,
				kcbm : kcbh,
				kcmc : kcmc
		};
		planPersonList.push(planPerson);
	});
//	if(planPersonList.length == 0){
//		AlertUtil.showErrorMessage("请选择人员！");
//        return false;
//	}
	param.planPersonList = planPersonList;
	param.ycrs = planPersonList.length;
	var url = basePath+"/training/plan/edit";
	if($.trim($("#id").val()) == null || $.trim($("#id").val()) == ''){
		url = basePath+"/training/plan/add";
	}
	startWait($("#"+alertFormId));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
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
function cancel(id) {
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/training/plan/cancel",
			type:"post",
			async: false,
			data:{
				id : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
		
	}
	});
}

//下发
function issued(id) {
	
	AlertUtil.showConfirmMessage("确定要下发吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/training/plan/updateIssued",
			type:"post",
			async: false,
			data:{
				id : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('下发成功!');
				refreshPage();
			}
		});
		
	}
	});
}

//指定结束
function shutDown(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	AssignEndModal.show({
		chinaHead:'课程编号',//单号中文名称
		englishHead:'Course No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:zdjsr,//指定结束人
		jssj:zdjsrq,//指定结束时间
		jsyy:zdjsyy,//指定结束原因
		callback: function(data){//回调函数
			if(null != data && data != '' && data.gbyy != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy : data.gbyy,
						zt : data.zt
				};
				sbShutDown(obj);
			}
		}
	});
}

//确认指定结束
function sbShutDown(plan) {
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/training/plan/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(plan),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('关闭成功!');
			$("#AssignEndModal").modal("hide");
			id = plan.id;
			refreshPage();
		}
	});
}

//设置高度
function autoHeightResize(){
    var headerHeight = $('.header').outerHeight();
    
    var headerDownHeight = $('.header-down').outerHeight();
    
    var windowHeight = $(window).height()
    
    var footerHeight = $('.footer').outerHeight()||0;
    
    var paginationHeight = $('.page-height:visible').outerHeight()||0;
    
    var rowHeight = $('.row-height:visible').outerHeight()||0;
    
    var searchHeight = $('.search-height:visible').outerHeight()||0;
    
    if(searchHeight){searchHeight = searchHeight += 10}
    
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    
    var theadHeight = $(".table-h thead").height();
    
    var adjustHeight = $("#adjustHeight").val()||0;
    
    var copyrightHeight = $("#copyright:visible").outerHeight()||0;
    
    var searchContent=$(".searchContent").outerHeight()||0
 
    //情景2:tab-tab 类型
    //修改1：在页面div class='page-content'上加class='page-content tab-tab-type'
    //修改2：在页面第一个ul的class='nav-tabs'的父div 上加class='tab-tab-type_parentdiv'
    //修改3：在第一个Tab的表格的父div 上添加class='tab-first-height'（有几个tab就得添加几次）
    //修改4：在第二个Tab的表格的父div 上添加class='tab-second-height'（有几个tab就得添加几次）
    if($(".tab-tab-type").length>0){
    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
     	//表格的高度
    	
         var tableHeight = bodyHeight - searchContent;
         //谷歌的兼容性问题
         if(navigator.userAgent.indexOf("Chrome") > -1){
         	tableHeight -= 10;
         }
         //first tab header
         var  tab_tab_type_parentdiv_header=$(".tab-tab-type_parentdiv .nav-tabs").outerHeight()||0
         
        
        
         //#fileDiv 不显示的情况下 first tab 的高度
         var tabheight=tableHeight-tab_tab_type_parentdiv_header;
        /* alert(bodyHeight+"=="+tableHeight+"=="+tabheight)*/
         //隐藏的div是否显示
        
         if($("#fileDiv").css("display")=='block'){
        	 //#fileDiv显示情况下first tab中table的高度
        	 $(".tab-first-height").attr('style', 'height:' + tabheight*0.5+ 'px !important;overflow-x: auto;');
        	 //提示信息的高度
        	 $(".tab-tab-type_parentdiv .tab-content").css("height","auto");
        	/* var parentDivHeight=$(".tab-tab-type_parentdiv ").outerHeight();*/
        	 var selectCourse=$("#selectCourse").outerHeight()||0;
        	 //第二个tab header 的高度
        	 var secondheader=$("#fileDiv .nav-tabs").outerHeight()||0;
        	 //第一个tab的高度
        	 var tab_tab_type_parentdiv=$(".tab-tab-type_parentdiv").outerHeight()||0;
        	 //第二个tab中table的高度
        	 var tab_second_height=tableHeight-(tab_tab_type_parentdiv)-secondheader-selectCourse-10;
        	 $("#fileDiv .tab-content").css({"height":tab_second_height+"px","overflow":"auto"});
        	//给第二个tab中table的高度赋值
        	/* $(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;') */
         }else{
        	
        	//#fileDiv 不显示情况下first tab中table的高度
        	 $(".tab-tab-type_parentdiv .tab-content").css("height",tabheight+"px");
        	 $(".tab-first-height").attr('style', 'height:' + (tabheight-paginationHeight-8-text_muted)+ 'px !important;overflow-x: auto;');
         }
    }   
}

//加载行信息
function selectRow(this_){
	// 下方div是否显示
	var isBottomShown = false;
	if($("#fileDiv").is(":visible")){
		isBottomShown = true;
	}
	isCheck = true;
	var kcbh = $.trim($(this_).attr("kcbh"));
	var id = $.trim($(this_).attr("id"));
	var kcid = $.trim($(this_).attr("kcid"));
	dprtcode = $.trim($(this_).attr("dprtcode"));
	var str = '';
	str = '已选择课程代码'+kcbh;
//	$("#selectCourse").text(str);
	loadPlanPerson(id);
	loadAttachment(kcid);
	$("#fileDiv").css("display","none");
	$("#fileDiv").css("display","block");
	/*showOrHideDetail();*/
	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
	new Sticky({tableId:'course_main_table'});
	autoHeightResize();
	// 选中行可见
	addIcon();
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $(".tab-first-height"));
	}
}
//添加小图标
function addIcon(){
	
	if( $("#hideIcon").length==0){
		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".tab-tab-type_parentdiv .fenye:visible"));
	}
}
//隐藏小图标
function hideBottom(){
	$("#hideIcon").remove();
	$("#fileDiv").css("display","none");
	autoHeightResize();
}
//加载行信息
function selectYearRow(this_){
	/*var clazz = $.trim($(this_).attr("class"));
	$("."+clazz).addClass('bg_tr').siblings().removeClass('bg_tr');*/
}

function loadPlanPerson(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/planperson/selectByPxjhid",
		type:"post",
		data:{pxjhid:id},
		dataType:"json",
		success:function(data){
			$("#person_body").empty();
			if(data != null && data.length > 0){
				var htmlContent = '';
				   $.each(data,function(index,row){
					   if (index % 2 == 0) {
						   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
					   } else {
						   htmlContent += "<tr bgcolor=\"#fefefe\">";
					   }
					   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
					   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.rybh)+"' class='text-left'>";
					   htmlContent += "<a href='javascript:void(0);' onclick=\"viewPersonnel('" + row.wxrydaid + "')\">"+StringUtil.escapeStr(row.paramsMap.rybh)+"</a>";
					   htmlContent += "</td>";
					   htmlContent += "<td title='"+StringUtil.escapeStr(row.xm)+"' class='text-left'>"+StringUtil.escapeStr(row.xm)+"</td>";
					   var gzdw = StringUtil.escapeStr(row.gzdw);
					   if(row.paramsMap.wbbs == 2 && gzdw == ''){
						   gzdw = "外部人员";
					   }
					   htmlContent += "<td title='"+gzdw+"' class='text-left'>"+gzdw+"</td>";
					   htmlContent += "</tr>";  
					    
				   });
				   $("#person_body").html(htmlContent);
			}else{
				$("#person_body").html("<tr><td colspan=\"4\" class='text-center'>暂无数据 No data.</td></tr>");
			}
		}
	});
}

//上传附件
function loadAttachment(id){
	attachments_list_crud.show({
		attachHead : false,
		chinaHead : '课件信息',
		englishHead : 'Courseware Information',
		chinaFileHead : '课件名',
		englishFileHead : 'Courseware',
		chinaColFileTitle : '课件名',
		englishColFileTitle : 'Courseware',
		djid:id,
 		colOptionhead : false,
		fileHead : false,
		fileType:"course",
		callback: function(){//回调函数
		}
	});//显示附件
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
	if(obj == 'username'){
		obj = "u.username";
	}
	if(obj == 'KS'){
		obj = "to_number("+obj+")";
	}
	goPage(currentPage,obj,orderStyle.split("_")[1]);
	autoHeightResize();
}
	
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	isCheck = false;
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	AjaxPlanViewSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevision(){
	clearData();
	TableUtil.resetTableSorting("course_list_table_div");
	goPage(1,"auto","desc");
}
	
//刷新页面
function refreshPage(){
	TableUtil.resetTableSorting("course_list_table_div");
	goPage(pagination.page, pagination.sort, pagination.order);
}

function changeOrganization(){
	searchRevision();
}

function clearData(){
	$("#attachments_list_crud").hide();
	$("#selectCourse").html('');
}

//查看人员详情
function viewPersonnel(id){
	window.open(basePath+"/quality/maintenancepersonnel/view/" + id);
}

//查看培训计划界面
function findto(id){
	window.open(basePath+"/training/trainingnotice/find/"+$.trim(id));
}

//查看课程界面
function findCourse(id){
	window.open(basePath+"/training/course/view?id="+$.trim(id));
}

//搜索条件显示与隐藏 
function search() {
	
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		autoHeightResize();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		autoHeightResize();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
//列表下方数据显示与隐藏
function showOrHideDetail(){
	if($("#fileDiv").css("display")=="block"){
		$("#course_list_table_div").css("height","auto");
	}else{
		App.resizeHeight();
	}
}	

//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	
	$("#keyword_search").val("");
	
	$("#divSearch").find("input [type='select']").each(function() {
		$(this).attr("value", "");
	});
	
	$("#jhrq_search").attr("value", "");
	
	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});

	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});
	/*var obj = document.getElementsByName('fxbs_search');
	 for(i=0;i<obj.length;i++){
		 obj[i].checked=true;
     };*/
    $("input:checkbox[name='fxbs_search']").attr("checked","checked");
	$("#dprtcode_search").val(dprtId);
	dpartmentChange();
}
		
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
		
//只能输入数字
function clearNoNumber(obj){
     //先把非数字的都替换掉，除了数字
     obj.value = obj.value.replace(/[^\d]/g,"");
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		obj.value = 0;
  	 }
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
}
//导出
function exportExcel(){
	var searchParam = gatherSearchParam();
	
	if($("#pxjhExport").attr("class")=="active"){
		window.open(basePath+"/training/plan/plan.xls?dprtcode="
				+ encodeURIComponent(searchParam.dprtcode)+ "&jhlx=" + encodeURIComponent(searchParam.jhlx?searchParam.jhlx:'') + "&keyword="
				+ encodeURIComponent(searchParam.keyword) + "&order=" + encodeURIComponent(pagination.order) + "&page="
				+ encodeURIComponent(pagination.page) + "&rows=" + encodeURIComponent(pagination.rows) + "&sort="
				+ encodeURIComponent(pagination.sort) + "&fxbsList=" + encodeURIComponent(searchParam.paramsMap.fxbsList?searchParam.paramsMap.fxbsList:'') + "&jhrqBegin="
				+ encodeURIComponent(searchParam.paramsMap.jhrqBegin?searchParam.paramsMap.jhrqBegin:'') + "&jhrqEnd=" + encodeURIComponent(searchParam.paramsMap.jhrqEnd?searchParam.paramsMap.jhrqEnd:'') + "&year="
				+ encodeURIComponent(searchParam.paramsMap.year?searchParam.paramsMap.year:'') + "&zt=" + encodeURIComponent(searchParam.zt?searchParam.zt:''));
	}else{
		searchParam.paramsMap.ztList = [0,1,10];
		window.open(basePath+"/training/plan/plan.xls?dprtcode="
				+ encodeURIComponent(searchParam.dprtcode)+ "&jhlx=" + encodeURIComponent(searchParam.jhlx?searchParam.jhlx:'') + "&keyword="
				+ encodeURIComponent(searchParam.keyword) + "&order=" + encodeURIComponent(pagination.order) + "&page="
				+ encodeURIComponent(pagination.page) + "&rows=" + encodeURIComponent(pagination.rows) + "&sort="
				+ encodeURIComponent(pagination.sort) + "&fxbsList=" + encodeURIComponent(searchParam.paramsMap.fxbsList?searchParam.paramsMap.fxbsList:'') + "&jhrqBegin="
				+ encodeURIComponent(searchParam.paramsMap.jhrqBegin?searchParam.paramsMap.jhrqBegin:'') + "&jhrqEnd=" + encodeURIComponent(searchParam.paramsMap.jhrqEnd?searchParam.paramsMap.jhrqEnd:'') + "&year="
				+ encodeURIComponent(searchParam.paramsMap.year?searchParam.paramsMap.year:'') + "&zt=" + encodeURIComponent(searchParam.zt?searchParam.zt:'')+"&ztList=" + encodeURIComponent(searchParam.paramsMap.ztList?searchParam.paramsMap.ztList:''));
	}
	
	
	/*var searchParam = gatherSearchParam();
	searchParam.pagination = pagination;
	startWait();
	AjaxUtil.ajax({
	   url : basePath+"/training/plan/planExcel",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
	    window.open(data);
	    
      }
    });*/
}	

//导入
function importExcel(){
 importModal.init({
	    importUrl:"/training/plan/excelImport",
	    downloadUrl:"/common/templetDownfile/22",
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