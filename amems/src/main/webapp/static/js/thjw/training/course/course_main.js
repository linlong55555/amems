
var pagination = {};
var alertFormId = 'alertForm';
var headChinaId = 'modalName';
var headEnglishId = 'modalEName';
var dprtcode = userJgdm;
var kclb = '';
var pxxs = '';
var zqdw = '';
var ksxs = '';
var fxpxxs = '';
var fxksxs = '';
var fjjx = '';
var isDoropDown=false;

$(document).ready(function(){
	initPlaneModelSearch();
	Navigation(menuCode,"","");//初始化导航
	goPage(1,"auto","desc");//开始的加载默认的内容 
	initInfo();
	refreshPermission();
	formValidator();
	//初始化日志功能
	logModal.init({code:'B_P_003'});
	
	authHeight();
	 $(window).resize(function() {
		 authHeight();
	 });
	 initImport();
});

//设置高度
function authHeight(){
    //头部高度
    var headerHeight = $('.header').outerHeight();
    var headerDownHeight=0;
    if(isDoropDown){
    	headerDownHeight = $('.header-down').outerHeight();	
    }else{
    	headerDownHeight=25;
    	 if(navigator.userAgent.indexOf("Chrome") > -1){
    		 headerDownHeight=15;
         }
    	
    }
    //window高度
    var windowHeight = $(window).height()
    //尾部的高度
    var footerHeight = $('.footer').outerHeight()||0;
    //分页的高度
    var paginationHeight = $('.page-height:visible').outerHeight()||0;
    //panelheader的高度
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    //调整高度
    var adjustHeight = $("#adjustHeight").val()||0;
    //搜索框的高度
    var searchContent=$(".searchContent").outerHeight()||0;
    //body的高度
   
    
   //情景1：table-tab
    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
    //修改2：给上方表格的父div 添加class='table-tab-type_table'
    //修改3：给tab中table的父div 添加class='tab-second-height'
    
    
    if($(".table-tab-type").length>0){
    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
    	//表格的高度
        var tableHeight = bodyHeight - searchContent - paginationHeight  - 20 - adjustHeight;
      //谷歌的兼容性问题
        if(navigator.userAgent.indexOf("Chrome") > -1){
        	tableHeight -= 10;
        }
        //隐藏的div是否显示
        if($("#fileDiv").css("display")=='block'){
        	//表格的高度
        	var actualTableHeight= tableHeight*0.5;
        	//表格的高度
        	$(".table-tab-type_table").attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
        	// tab header 的高度
        	var table_tab=$("#fileDiv .nav-tabs").outerHeight()||0;
        	//选中的提示信息
        	var selectCourse=$("#selectCourse").outerHeight()||0;
        	//表格的高度
        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
        	// tabcontent
        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectCourse;
        	//如果下方的tab是引用课件信息。
        	$("#fileDiv .tab-content").css({"height":tabcontent+"px","overflow":"auto"})
        
        	
        }else{
        	//给表格的父div的高度进行赋值
        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
        }
        
    }

    
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
                       
        	kcbh: {
                validators: {
                	notEmpty: {
                        message: '课程编号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            kcmc: {
                validators: {
                    notEmpty: {
                        message: '课程名称不能为空'
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
            },
            kclb: {
                validators: {
                    notEmpty: {
                        message: '请选择课程类别'
                    }
                }
            },
            ks: {
                validators: {
                    notEmpty: {
                        message: '初训学时不能为空'
                    }
                }
            },
            pxxs: {
                validators: {
                    notEmpty: {
                        message: '请选择初训培训形式'
                    }
                }
            },
            ksxs: {
                validators: {
                    notEmpty: {
                        message: '请选择初训考试形式'
                    }
                }
            },
            kcnr: {
                validators: {
                    notEmpty: {
                        message: '课程内容不能为空'
                    },
                    stringLength: {
                        max: 600,
                        message: '长度最多不超过600个字符'
                    }
                }
            },
            pxmb: {
                validators: {
                    notEmpty: {
                        message: '培训目标不能为空'
                    },
                    stringLength: {
                        max: 600,
                        message: '长度最多不超过600个字符'
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

function initInfo(){
	initDic();
	kclb = $.trim($("#kclb", $("#"+alertFormId)).val());
	pxxs = $.trim($("#pxxs", $("#"+alertFormId)).val());
	zqdw = $.trim($("#zqdw", $("#"+alertFormId)).val());
	ksxs = $.trim($("#ksxs", $("#"+alertFormId)).val());
	fxpxxs = $.trim($("#fxpxxs", $("#"+alertFormId)).val());
	fxksxs = $.trim($("#fxksxs", $("#"+alertFormId)).val());
	fjjx = $.trim($("#fjjx", $("#"+alertFormId)).val());
	
	
}
//数据字典
function initDic(){
	$("#kclb", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('31','kclb',dprtcode);
	$("#pxxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('32','pxxs',dprtcode);
	$("#ksxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('33','ksxs',dprtcode);
	$("#fxpxxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('32','fxpxxs',dprtcode);
	$("#fxksxs", $("#"+alertFormId)).empty();
	DicAndEnumUtil.registerDic('33','fxksxs',dprtcode);
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
 	 	$("#fjjx", $("#"+alertFormId)).empty();
 	 	$("#fjjx", $("#"+alertFormId)).append(option);
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

 //带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	$("#fileDiv").css("display","none");
	authHeight();
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	url = basePath+"/training/course/queryAllPageList";
	
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
			   signByKeyword($("#keyword_search").val(),[2,3,4,6,8,9,13,14,15],"#list tr td");
		   } else {
			  $("#list").empty();
			  $("#pagination").empty();
			  $("#list").append("<tr><td colspan=\"18\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'course_main_table'});
      }
    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var fjjx = $.trim($("#fjjx_search").val());
		 if(fjjx != "-"){
			 paramsMap.qjx = "qjx";
			 searchParam.fjjx = fjjx;
		 }
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = keyword;
		 var isFxList = [];
		 $('input[name="isFx_search"]').each(function(i){
			 if(this.checked){
				 isFxList.push($(this).val());
			 }
			});
		 if(isFxList.length == 0){
			 isFxList.push(2);
		 }
		 paramsMap.isFxList = isFxList;
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.id+"' dprtcode='"+row.dprtcode+"' kcbh='"+StringUtil.escapeStr(row.kcbh)+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectRow(this)>";
			   } else {
				   htmlContent += "<tr id='"+row.id+"' dprtcode='"+row.dprtcode+"' kcbh='"+StringUtil.escapeStr(row.kcbh)+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectRow(this)>";
			   }
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:course:main:02' onClick=\"openWinEdit('"+ row.id + "','" + row.dprtcode + "')\" title='维护课程信息 Edit'>&nbsp;&nbsp;</i>";
			   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='training:course:main:03' onClick=\"cancel('"+ row.id + "')\" title='作废 Invalid'></i>";  
			   htmlContent += "</td>";  
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kclb)+"' class='text-left'>"+StringUtil.escapeStr(row.kclb)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcbh)+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"view('" + row.id + "','" + row.dprtcode + "')\">"+StringUtil.escapeStr(row.kcbh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcmc)+"' class='text-left'>"+StringUtil.escapeStr(row.kcmc)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxmb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxmb)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ks)+"' class='text-right'>"+StringUtil.escapeStr(row.ks)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
			   htmlContent += "<td class='text-center'>"+(row.isFx == 1?"是":"否")+"</td>";
			   var fxjg = formatUndefine(row.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',row.zqdw);
			   htmlContent += "<td title='"+fxjg+"' class='text-left'>"+fxjg+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxks)+"' class='text-right'>"+StringUtil.escapeStr(row.fxks)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxpxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxpxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxksxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   if(undefined != row.zdr){
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }
			   else{
				   htmlContent += "<td class='text-left'>"+"</td>";
			   }
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }

//打开新增弹出框
function openWinAdd(){
	dprtcode = userJgdm;
 	$("#"+headChinaId, $("#"+alertFormId)).html('新增');
 	$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
	$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
	$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	initPlaneModel(dprtcode, function(){
		//数据字典
		initDic();
	 	var obj = {};
	 	obj.isFx = 1;
	 	obj.kclb = kclb;
	 	obj.pxxs = pxxs;
	 	obj.zqdw = zqdw;
	 	obj.ksxs = ksxs;
	 	obj.fxpxxs = fxpxxs;
	 	obj.fxksxs = fxksxs;
	 	obj.fjjx = "";
	 	setEdit();//设置可编辑
	 	$(".viewfix", $("#"+alertFormId)).hide();
	 	setData(obj);//设置表单数据
	 	checkFx();//设置复训
	 	$("#"+alertFormId).modal("show");
	 	loadAttachmentEdit('',true,true,'');//传入参数是否显示
	});
}

//打开编辑课程弹出框
function openWinEdit(id,dprtcode_){
	$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
	$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	dprtcode = dprtcode_;
	initPlaneModel(dprtcode, function(){
		selectById(id,function(obj){
			//数据字典
			initDic();
			$("#"+headChinaId, $("#"+alertFormId)).html('编辑课程');
			$("#"+headEnglishId, $("#"+alertFormId)).html('Edit');
			setEdit();//设置只读/失效
			$(".viewfix", $("#"+alertFormId)).hide();
			setData(obj);//设置航材表单数据
			checkFx();//设置复训
			$("#"+alertFormId).modal("show");
			loadAttachmentEdit(id,true,true,'');//传入参数是否显示
		});
	});
}

//打开查看课程弹出框
function openWinView(id,dprtcode_){
	dprtcode = dprtcode_;
	initPlaneModel(dprtcode, function(){
		selectById(id,function(obj){
			//数据字典
			initDic();
			$("#"+headChinaId, $("#"+alertFormId)).html('查看课程');
			$("#"+headEnglishId, $("#"+alertFormId)).html('View');
			setData(obj);//设置航材表单数据
			$("#"+alertFormId).modal("show");
			setView();//设置只读/失效
			checkFx();//设置复训
			$(".viewfix", $("#"+alertFormId)).show();
			$("#whr", $("#"+alertFormId)).val(obj.zdr?StringUtil.escapeStr(obj.zdr.displayName):'');
			$("#whsj", $("#"+alertFormId)).val(obj.whsj);
			loadAttachmentEdit(id,false,false,'');//传入参数是否显示
		});
	});
}

//查看课程界面
function view(id,dprtcode_){
	window.open(basePath+"/training/course/view?id="+$.trim(id));
}

function loadAttachmentEdit(id,colOptionhead,fileHead,divId){
	if(!divId){
		divId = '';
	}
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents(divId);
	attachmentsObj.show({
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
		fileType:"card"
	});//	
}

//通过id获取课程数据
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/course/selectById",
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
	$("#kcbh", $("#"+alertFormId)).val(obj.kcbh);
	$("#kcmc", $("#"+alertFormId)).val(obj.kcmc);
	$("#kclb", $("#"+alertFormId)).val(obj.kclb);
	$("#pxxs", $("#"+alertFormId)).val(obj.pxxs);
	$("#ks", $("#"+alertFormId)).val(obj.ks);
	$("input:radio[name='isFx'][value='"+obj.isFx+"']", $("#"+alertFormId)).attr("checked",true); 
	$("#fxks", $("#"+alertFormId)).val(obj.fxks);
	$("#zqz", $("#"+alertFormId)).val(obj.zqz);
	$("#zqdw", $("#"+alertFormId)).val(obj.zqdw);
	$("#ksxs", $("#"+alertFormId)).val(obj.ksxs);
	$("#fxpxxs", $("#"+alertFormId)).val(obj.fxpxxs);
	$("#fxksxs", $("#"+alertFormId)).val(obj.fxksxs);
	$("#fjjx", $("#"+alertFormId)).val(obj.fjjx);
	$("#jyyq", $("#"+alertFormId)).val(obj.jyyq);
	$("#pxjg", $("#"+alertFormId)).val(obj.pxjg);
	$("#ly", $("#"+alertFormId)).val(obj.ly);
	$("#jctg", $("#"+alertFormId)).val(obj.jctg);
	$("#kcnr", $("#"+alertFormId)).val(obj.kcnr);
	$("#pxmb", $("#"+alertFormId)).val(obj.pxmb);
	$("#bz", $("#"+alertFormId)).val(obj.bz);
}

//设置只读/失效/隐藏
function setView(){
	
	$("#kcbh", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcmc", $("#"+alertFormId)).attr("readonly","readonly");
	$("#ks", $("#"+alertFormId)).attr("readonly","readonly");
	$("#fxks", $("#"+alertFormId)).attr("readonly","readonly");
	$("#zqz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcnr", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxmb", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kclb", $("#"+alertFormId)).attr("disabled","true");
	$("#pxxs", $("#"+alertFormId)).attr("disabled","true");
	$("#zqdw", $("#"+alertFormId)).attr("disabled","true");
	$("#ksxs", $("#"+alertFormId)).attr("disabled","true");
	$("#fxpxxs", $("#"+alertFormId)).attr("disabled","true");
	$("#fxksxs", $("#"+alertFormId)).attr("disabled","true");
	$("input:radio[name='isFx']", $("#"+alertFormId)).attr("disabled","true");
	$("#courseSave", $("#"+alertFormId)).hide();
	$(".identifying", $("#"+alertFormId)).hide();
}

//移除只读/失效/隐藏
function setEdit(){
	$("#kcbh", $("#"+alertFormId)).removeAttr("readonly");
	$("#kcmc", $("#"+alertFormId)).removeAttr("readonly");
	$("#ks", $("#"+alertFormId)).removeAttr("readonly");
	$("#fxks", $("#"+alertFormId)).removeAttr("readonly");
	$("#zqz", $("#"+alertFormId)).removeAttr("readonly");
	$("#kcnr", $("#"+alertFormId)).removeAttr("readonly");
	$("#pxmb", $("#"+alertFormId)).removeAttr("readonly");
	$("#bz", $("#"+alertFormId)).removeAttr("readonly");
	$("#kclb", $("#"+alertFormId)).removeAttr("disabled");
	$("#pxxs", $("#"+alertFormId)).removeAttr("disabled");
	$("#zqdw", $("#"+alertFormId)).removeAttr("disabled");
	$("#ksxs", $("#"+alertFormId)).removeAttr("disabled");
	$("#fxpxxs", $("#"+alertFormId)).removeAttr("disabled");
	$("#fxksxs", $("#"+alertFormId)).removeAttr("disabled");
	$("input:radio[name='isFx']", $("#"+alertFormId)).removeAttr("disabled"); 
	$("#courseSave", $("#"+alertFormId)).show();
	$(".identifying", $("#"+alertFormId)).show();
}

//选中复训
function checkFx(){
	var type = $("input[name='isFx']:checked",$("#"+alertFormId)).val();
	if(type == 1){
		$("#fxksDiv", $("#"+alertFormId)).show();
		$("#fxpxxsDiv", $("#"+alertFormId)).show();
		$("#fxksxsDiv", $("#"+alertFormId)).show();
		$("#fxjgDiv", $("#"+alertFormId)).show();
	}else{
		$("#fxksDiv", $("#"+alertFormId)).hide();
		$("#fxpxxsDiv", $("#"+alertFormId)).hide();
		$("#fxksxsDiv", $("#"+alertFormId)).hide();
		$("#fxjgDiv", $("#"+alertFormId)).hide();
	}
}

function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		return false;
	}
	
	var kcbh = $.trim($("#kcbh", $("#"+alertFormId)).val());
	var kcmc = $.trim($("#kcmc", $("#"+alertFormId)).val());
	var kclb = $.trim($("#kclb", $("#"+alertFormId)).val());
	var pxxs = $.trim($("#pxxs", $("#"+alertFormId)).val());
	var ks = $.trim($("#ks", $("#"+alertFormId)).val());
	var isFx = $("input:radio[name='isFx']:checked", $("#"+alertFormId)).val(); 
	var fxks = $.trim($("#fxks", $("#"+alertFormId)).val());
	var zqz = $.trim($("#zqz", $("#"+alertFormId)).val());
	var zqdw = $.trim($("#zqdw", $("#"+alertFormId)).val());
	var ksxs = $.trim($("#ksxs", $("#"+alertFormId)).val());
	var fxpxxs = $.trim($("#fxpxxs", $("#"+alertFormId)).val());
	var fxksxs = $.trim($("#fxksxs", $("#"+alertFormId)).val());
	var fjjx = $.trim($("#fjjx", $("#"+alertFormId)).val());
	var kcnr = $.trim($("#kcnr", $("#"+alertFormId)).val());
	var pxmb = $.trim($("#pxmb", $("#"+alertFormId)).val());
	var bz = $.trim($("#bz", $("#"+alertFormId)).val());
	var jyyq = $.trim($("#jyyq", $("#"+alertFormId)).val());
	var pxjg = $.trim($("#pxjg", $("#"+alertFormId)).val());
	var ly = $.trim($("#ly", $("#"+alertFormId)).val());
	var jctg = $.trim($("#jctg", $("#"+alertFormId)).val());
	
	var regu = /^[0-9]+$/;
	var regu2 = /^[0-9]+\.?[0-9]{0,2}$/;
	
	if(ks == "" || ks == null){
		AlertUtil.showModalFooterMessage("初训学时不能为空!");
		return false;
	}
	
	if (!regu2.test(ks)) {
        AlertUtil.showModalFooterMessage("初训学时只能输入数字,并保留两位小数！");
        return false;
    }
	
	if(isFx == 1){
		
		if(fxks == "" || fxks == null){
			AlertUtil.showModalFooterMessage("复训学时不能为空!");
			return false;
		}
		
		if(fxpxxs == "" || fxpxxs == null){
			AlertUtil.showModalFooterMessage("复训培训形式不能为空!");
			return false;
		}
		if(fxksxs == "" || fxksxs == null){
			AlertUtil.showModalFooterMessage("复训考试形式不能为空!");
			return false;
		}
		
		if(zqz == "" || zqz == null){
			AlertUtil.showModalFooterMessage("复训间隔不能为空!");
			return false;
		}
		
		if(zqdw == "" || zqdw == null){
			AlertUtil.showModalFooterMessage("复训间隔单位不能为空!");
			return false;
		}
		
		if (!regu2.test(fxks)) {
	        AlertUtil.showModalFooterMessage("复训学时只能输入数字,并保留两位小数！");
	        return false;
	    }
		
		if (!regu.test(zqz)) {
	        AlertUtil.showModalFooterMessage("复训间隔只能输入数字！");
	        return false;
	    }
		
	}else{
		fxks = '';
		zqz = '';
		zqdw = '';
		fxpxxs = '';
		fxksxs = '';
	}
	
	var param = {
			id : $.trim($("#id").val()),
			kcbh : kcbh,
			kcmc : kcmc,
			kclb : kclb,
			pxxs : pxxs,
			ks : ks,
			isFx : isFx,
			fxks : fxks,
			zqz : zqz,
			zqdw : zqdw,
			ksxs : ksxs,
			fxpxxs : fxpxxs,
			fxksxs : fxksxs,
			fjjx : fjjx,
			kcnr : kcnr,
			pxmb : pxmb,
			bz : bz,
			jyyq : jyyq,
			pxjg : pxjg,
			ly : ly,
			jctg : jctg,
			dprtcode : dprtcode
		};
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents();
	param.attachments = attachmentsObj.getAttachments();
	
	var url = basePath+"/training/course/edit";
	if($.trim($("#id").val()) == null || $.trim($("#id").val()) == ''){
		url = basePath+"/training/course/add";
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
			url:basePath + "/training/course/cancel",
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

//加载行信息
function selectRow(this_){
	// 下方div是否显示
	var isBottomShown = false;
	if($("#fileDiv").is(":visible")){
		isBottomShown = true;
	}
	var kcbh = $.trim($(this_).attr("kcbh"));
	var id = $.trim($(this_).attr("id"));
	dprtcode = $.trim($(this_).attr("dprtcode"));
	var str = '';
	str = '已选择课程代码'+kcbh;
//	$("#selectCourse").text(str);
	loadAttachment({
		id : id,
		kcbh : kcbh,
		dprtcode : dprtcode
	});//显示附件
	post_list_view.init({
		djid : id,
		kcbh : kcbh,
		dprtcode : dprtcode,
		callback: function(){//回调函数
		}
	});//显示岗位
	$("#fileDiv").css("display","none");
	$("#fileDiv").css("display","block");
	authHeight();
	// 选中行可见
	//添加小图标
	addIcon();
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $(".table-tab-type_table"));
	}
}
//添加小图标
function addIcon(){
	if( $("#hideIcon").length==0){
		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#pagination .fenye"));
	}
}
//隐藏小图标
function hideBottom(){
	$("#hideIcon").remove();
	$("#fileDiv").css("display","none");
	authHeight();
}
//上传附件
function loadAttachment(param){
	$("#"+param.id).addClass('bg_tr').siblings().removeClass('bg_tr')
	attachments_list_crud.show({
		attachHead : false,
		chinaHead : '课件信息',
		englishHead : 'Courseware Information',
		chinaFileHead : '课件名',
		englishFileHead : 'Courseware',
		chinaColFileTitle : '课件名',
		englishColFileTitle : 'Courseware',
		djid:param.id,
 		colOptionhead : false,
		fileHead : false,
		fileType:"course",
		callback: function(attachments){//回调函数
			if(attachments != null){
				param.attachments = attachments;
				// 提交数据
				AjaxUtil.ajax({
					url:basePath+"/training/course/edit",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(param),
					dataType:"json",
					success:function(data){
						attachments_list_crud.loadAttachements();
					}
				});
			}
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
	if(obj == 'ks' || obj == 'fxks'){
		obj = "to_number("+obj+")";
	}
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}
	
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
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

function changeJx(){
	searchRevision();
}

function changeOrganization(){
	initPlaneModelSearch();
	searchRevision();
}

function clearData(){
	$("#attachments_list_crud_course").hide();
	$("#selectCourse").html('');
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
		
/**
 * 初始化导入
 */
function initImport(){
    importModal.init({
	    importUrl:"/training/course/excelImport",
	    downloadUrl:"/common/templetDownfile/19",
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
	var param = this.gatherSearchParam();
	param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
	param.keyword = encodeURIComponent(param.keyword);
	window.open(basePath+"/training/course/Course.xls?paramjson="+JSON.stringify(param));
}