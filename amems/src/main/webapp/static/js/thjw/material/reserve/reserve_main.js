
var type = $("#type").val();
var reserveDetailList = [];
var attachNumber = 0;
var styleFlag = true;//设置行样式
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;
var isDoropDown=false;

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	$("#reserveDetailList").append("<tr><td  colspan='22' class='text-center'>暂无数据 No data.</td></tr>");
	$("#messageListViewTable").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	
	if(bjh!=null&& bjh!=''){
		$("#sqrrealname_search").val("");
		$("#bjh").val(bjh);
		setDefaultDateRangePickerValue();
	}
	decodePageParam();//开始的加载默认的内容 
	refreshPermission();
	authHeight();
	 $(window).resize(function() {
		 authHeight();
	 });
});

/**
 * 初始化排班日期
 */
function setDefaultDateRangePickerValue(){
	
	$("#sqrq_search").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	$(".cancelBtn").hide();//去掉时间控件取消按钮
	TimeUtil.getCurrentDate(function(date) {
		var startDate = addMonths(date, -3); 
		var endDate = date ;
		$("#sqrq_search").data('daterangepicker').setStartDate(startDate);
		$("#sqrq_search").data('daterangepicker').setEndDate(endDate);
		$("#sqrq_search").val(startDate+" ~ "+endDate);
		decodePageParam(); //加载数据
	});
	
	function addMonths(date, months){ 
		var d=new Date(Date.parse(date.replace(/-/g, "/"))); 
		d.setMonth(d.getMonth()+months); 
		var month=d.getMonth()+1; 
		var day = d.getDate(); 
		if(month<10){ 
			month = "0"+month; 
		} 
		if(day<10){ 
			day = "0"+day; 
		} 
		var val = d.getFullYear()+"-"+month+"-"+day; 
		return val; 
	}
	
	function addDays(date, days){ 
		var d=new Date(date); 
		d.setDate(d.getDate()+days); 
		var month=d.getMonth()+1; 
		var day = d.getDate(); 
		if(month<10){ 
		month = "0"+month; 
		} 
		if(day<10){ 
		day = "0"+day; 
		} 
		var val = d.getFullYear()+"-"+month+"-"+day; 
		return val; 
	}
	  
}

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
    
//    var headerDownHeight = $('.header-down').outerHeight();
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
        var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
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
        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectCourse-10;
        	//如果下方的tab是引用课件信息。
        	if($(".tab-second-height").length>0){
        		//是否存在课件上传
        		var fileHeadheigth=$("#fileHead").outerHeight()||0;
        		//下方tab的高度
        		var tab_second_height =tabcontent-fileHeadheigth-20;
        		
        		//给下方tab中table 的父div 的高度进行赋值
        		$(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
        	}
        	
        	if($(".tab-table-search-height").length>0){
        		//是否存在课件上传
        		var fileHeadheigth=$(".tab-search-content").outerHeight()||0;
        		//下方tab的高度
        		var tab_second_height =tabcontent-fileHeadheigth-20;
        		
        		//给下方tab中table 的父div 的高度进行赋值
        		$(".tab-table-search-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
        	}
        	
        }else{
        	//给表格的父div的高度进行赋值
        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
        }
        
    }
  //情景2：table-table
    
  //情景3：tab-table
    

    //情景4:tab-tab 类型
    //修改1：在页面div class='page-content'上加class='page-content tab-tab-type'
    //修改2：在页面第一个ul的class='nav-tabs'的父div 上加class='tab-tab-type_parentdiv'
    //修改3：在第一个Tab的表格的父div 上添加class='tab-first-height'（有几个tab就得添加几次）
    //修改4：在第二个Tab的表格的父div 上添加class='tab-second-height'（有几个tab就得添加几次）
    if($(".tab-tab-type").length>0){
    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
     	//表格的高度
         var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
        //谷歌的兼容性问题
         if(navigator.userAgent.indexOf("Chrome") > -1){
         	tableHeight -= 10;
         }
         //first tab header
         var  tab_tab_type_parentdiv_header=$(".tab-tab-type_parentdiv .nav-tabs").outerHeight()||0
         //#fileDiv 不显示的情况下 first tab 的高度
         var tabheight=tableHeight-tab_tab_type_parentdiv_header-10;
         //隐藏的div是否显示
         if($("#fileDiv").css("display")=='block'){
        	 //#fileDiv显示情况下first tab中table的高度
        	 $(".tab-first-height").attr('style', 'height:' + tabheight*0.5+ 'px !important;overflow-x: auto;');
        	 //提示信息的高度
        	 var selectCourse=$("#selectCourse").outerHeight()||0;
        	 //第二个tab header 的高度
        	 var secondheader=$("#fileDiv .nav-tabs").outerHeight()||0;
        	 //第一个tab的高度
        	 var tab_tab_type_parentdiv=$(".tab-tab-type_parentdiv").outerHeight()||0;
        	 //第二个tab中table的高度
        	 var tab_second_height=tableHeight-(tab_tab_type_parentdiv-paginationHeight)-secondheader-selectCourse-10;
        	//给第二个tab中table的高度赋值
        	 $(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;') 
         }else{
        	//#fileDiv 不显示情况下first tab中table的高度
        	 $(".tab-first-height").attr('style', 'height:' + tabheight+ 'px !important;overflow-x: auto;');
         }
    }  
    
}

var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.sqrrealname = $.trim($("#sqrrealname_search").val());
	params.jjcd = $.trim($("#jjcd_search").val());
	params.zt = $.trim($("#zt_search").val());
	params.sqrq = $.trim($("#sqrq_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#sqrrealname_search").val(params.sqrrealname);
		$("#jjcd_search").val(params.jjcd);
		$("#zt_search").val(params.zt);
		$("#sqrq_search").val(params.sqrq);
		$("#dprtcode_search").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/getByKeyDprtcode",
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

	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		//先移除暂无数据一行
		$("#reserveDetailList").empty();
		$("#reserveDetailList").append("<tr><td  colspan='22' class='text-center'>暂无数据 No data.</td></tr>");
		reserveDetailList = [];
		$("#fileDiv").css("display","none");
		authHeight();
		if(!isLoadMonitor){
			loadMonitorsettings();
		}
		var searchParam = gatherSearchParam();
	
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		if(type == 'manage'){
			url = basePath+"/aerialmaterial/reserve/queryAllPageList";
		}
		if(type == 'approve'){
			url = basePath+"/aerialmaterial/reserve/queryApprovePageList";
		}
		
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
				   signByKeyword($("#keyword_search").val(),[4,5],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'reserve_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var sqrrealname = $.trim($("#sqrrealname_search").val());
		 var jjcd = $.trim($("#jjcd_search").val());
		 var zt = $.trim($("#zt_search").val());
		 var sqrq = $.trim($("#sqrq_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = keyword;
		 if('' != sqrrealname){
			 searchParam.sqrrealname = sqrrealname;
		 }
		 if('' != jjcd){
			 searchParam.jjcd = jjcd;
		 }
		 if('' != zt){
			 searchParam.zt = zt;
		 }
		 if(null != sqrq && "" != sqrq){
			 var sqrqBegin = sqrq.substring(0,10)+" 00:00:00";
			 var sqrqEnd = sqrq.substring(13,23)+" 23:59:59";
			 paramsMap.sqrqBegin = sqrqBegin;
			 paramsMap.sqrqEnd = sqrqEnd;
		 }
		 
		 paramsMap.bjh=$.trim($("#bjh").val());
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
			   htmlContent += "<td class='fixed-column text-center'><input type='checkbox' id='"+ row.id +"' sqdh='"+row.sqdh+"' name='checkReserve' onclick='loadMaterial("+index+",this)'  /></td>";
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   if(type == 'manage' && row.zt == 1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:manage:02' onClick=\"edit('" + row.id + "',"+row.zt+")\" title='修改 Edit'></i>";
				   htmlContent += "&nbsp;&nbsp;<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:manage:05' onClick=\"cancel('"+ row.id + "',"+row.zt+")\" title='作废 Invalid'></i>";
			   }
			   if(type == 'approve' && row.zt != 9){
				   if(row.zt == 2){
					   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:approve:01' onClick=\"audit('"+ row.id + "',"+row.zt+")\" title='审核 Review'></i>";
				   }
				   htmlContent += "&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:approve:02' djid='"+row.id+"' sqdh='"+row.sqdh+"' zdjsyy='' zdjsr='' zdjsrq='' onClick=\"shutDown(this,true)\" title='指定结束 End'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.sqdh)+"' class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.sqdh)+"</a>";
			   htmlContent += "</td>";
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.tdmc)+"' class='text-left'>"+StringUtil.escapeStr(row.tdmc)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('urgencyEnum',row.jjcd) +"</td>";
			   
			   htmlContent += "<td class='text-left'>";
			   if(row.zt == 9){
				   var zdjsr = row.jsr?StringUtil.escapeStr(row.jsr.displayName):'';
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.sqdh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+formatUndefine(row.zdjsrq)+"' onclick=\"shutDown(this,false)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('reserveStatusEnum',row.zt);
			   }
			   htmlContent += "</td>";
			    
			   htmlContent += "<td class='text-left'>"+formatWorkOrder(row.glgd)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.sqyy)+"' class='text-left'>"+StringUtil.escapeStr(row.sqyy)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.sqrusername)+" "+StringUtil.escapeStr(row.sqrrealname)+"' class='text-left'>"+StringUtil.escapeStr(row.sqrusername)+" "+StringUtil.escapeStr(row.sqrrealname)+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.sqsj)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.sprusername)+" "+StringUtil.escapeStr(row.sprrealname)+"' class='text-left'>"+StringUtil.escapeStr(row.sprusername)+" "+StringUtil.escapeStr(row.sprrealname)+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.spsj)+"</td>";
			   //htmlContent += "<td>"+formatUndefine(row.spyj)+"</td>";
			   var noRead = StringUtil.escapeStr(row.noRead);
			   var readAll = StringUtil.escapeStr(row.readAll);
			   if(noRead == ''){
				   noRead = 0;
			   }
			   if(readAll == ''){
				   readAll = 0;
			   }
			   
			   htmlContent += "<td class='text-center'>"; 
			   if(readAll != 0){
				   htmlContent += "<a href='javascript:void(0);' onclick=\"openMessageListWin(this,'"+ row.id+"')\"><span name='noRead'>"+noRead+"</span>/"+readAll+"</a>";
			   }else{
				   htmlContent += noRead+"/"+readAll;
			   }
			   htmlContent += "</td>"; 
			   
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	 
 /**
  * 打开留言列表
  * @returns
  */
 function openMessageListWin(obj,id){
	 $(obj).find("span[name=noRead]").html(0);
	 MessageListViewModal.show({
		viewObj:{id:id},//原值，在弹窗中默认勾选
 		callback: function(data){//回调函数
 			
 		}
 	})
 }	
//新增
 function add(){
 	window.location = basePath+"/aerialmaterial/reserve/add?pageParam="+encodePageParam();
 }
 	
//编辑提订单
function edit(id,zt){
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的提订单才能编辑!");
		return false;
	}
	if(checkEdit(id)){	
		window.location = basePath+"/aerialmaterial/reserve/edit?id=" + id+"&pageParam=" + encodePageParam();
	}
}

//审核提订单
function audit(id,zt){
	if(zt != 2){
		AlertUtil.showErrorMessage("对不起,只有提交状态下的提订单才能审核!");
		return false;
	}
	if(checkAudit(id)){	
		window.location = basePath+"/aerialmaterial/reserve/audit?id=" + id+"&pageParam=" + encodePageParam();
	}
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/reserve/view?id=" + id+"&type="+type+"&pageParam=" + encodePageParam());
}

//作废
function cancel(id,zt) {
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的提订单才能作废!");
		return false;
	}
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/reserve/cancel",
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
		
	}});
}

//指定结束
function shutDown(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	AssignEndModal.show({
		chinaHead:'提订单号',//单号中文名称
		englishHead:'Order No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:zdjsr,//指定结束人
		jssj:zdjsrq,//指定结束时间
		jsyy:zdjsyy,//指定结束原因
		showType : false,
		callback: function(data){//回调函数
			console.info(data);
			if(null != data && data != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy : data.gbyy
				};
				sbShutDown(obj);
			}
		}
	});
}

//确认指定结束
function sbShutDown(reserve) {
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(reserve),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('指定结束成功!');
			$("#AssignEndModal").modal("hide");
			id = reserve.id;
			refreshPage();
		}
	});
}

//检查提订单是否可以修改
function checkEdit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/aerialmaterial/reserve/checkEdit",
 		type:"post",
 		async: false,
 		data:{
 			'id' : id
 		},
 		dataType:"json",
 		success:function(data){	
 			flag = true;
 		}
 	});
 	return flag;
}

//检查提订单是否可以审核
function checkAudit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/aerialmaterial/reserve/checkAudit",
 		type:"post",
 		async: false,
 		data:{
 			'id' : id
 		},
 		dataType:"json",
 		success:function(data){	
 			flag = true;
 		}
 	});
 	return flag;
}

//搜索提订航材 
function searchRevision2(){
	 var keyword = $.trim($("#keyword2_search").val());
	 var materialProgress = $.trim($("#materialProgress_search").val());
	 var data = [];
	 $.each(reserveDetailList,function(index,row){
		 var jh = StringUtil.escapeStr(row.BJH);
		 var sqdh = StringUtil.escapeStr(row.SQDH);
		 var zwms = StringUtil.escapeStr(row.ZWMS);
		 var ywms = StringUtil.escapeStr(row.YWMS);
		 var jdzt = StringUtil.escapeStr(row.JDZT);
		 if(materialProgress == '' || materialProgress == jdzt){
			 if(keyword == ''){
				 data.push(row);
			 }else{
				 if(jh.indexOf(keyword) != -1 || sqdh.indexOf(keyword) != -1 || zwms.indexOf(keyword) != -1 || ywms.indexOf(keyword) != -1){
					 data.push(row);
				 }
			 }
		 }
	 });
	 initTableRow(data);
}

//加载航材信息
function loadMaterial(index,this_){
	// 下方div是否显示
	var isBottomShown = false;
	if($("#fileDiv").is(":visible")){
		isBottomShown = true;
	}
	$("#list :checkbox[name=checkReserve]:eq("+index+")").attr("checked", $(this_).is(":checked"));
	var id = $(this_).attr("id");
	var sqdh = $(this_).attr("sqdh");
	reserveDetailList = [];
	$("#reserveDetailList").empty();
	$("#reserveDetailList").append("<tr><td  colspan='22' class='text-center'>暂无数据 No data.</td></tr>");
	var mainidList = new Array();
	$("#list").find("input:checkbox[name=checkReserve]:checked").each(function() {
		mainidList.push($(this).attr('id'));
	});
	//留言
	updateStatusByDjIdUId(id);
	initMessageList(mainidList);
	initMaterial(mainidList);
	//附件
	if($(this_).is(":checked")){
		loadAttachements(id,sqdh);
	}else{
		removeAttach(id);
	}
	$("#fileDiv").css("display","none");
	$("#fileDiv").css("display","block");
	authHeight();
	// 选中行可见
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $(".table-tab-type_table"));
	}
}

//加载留言信息begin*************************************************************************************************************

function initMessageList(djidList){
	$("#messageListViewTable").empty();
	$("#messageListViewTable").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	if(djidList != null && djidList.length == 0 ){
		return false;
	}
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/message/queryMessageListByDjIdList",
		type:"post",
		async: false,
		data:{
			djidList:djidList
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.length > 0){
				appendMessageHtml(data);
		   } else {
			  $("#messageListViewTable").empty();
			  $("#messageListViewTable").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		   }
		}
	});
}

function appendMessageHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		var jsrnames = formatUndefine(row.jsrnames);
		var lynr = StringUtil.escapeStr(row.nbwjm);
		if(checkUser(row.messageRecipientsList)){
		   lynr = '<span style="color: red">' + lynr + "</span>";
		}
	   if (index % 2 == 0) {
		   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
		   
	   } else {
		   htmlContent += "<tr bgcolor=\"#fefefe\"  >";
	   }
	   
	   
	   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
	   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.sqdh:'')+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.wbwjm)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.wbwjm)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.nbwjm)+"' style='text-align:left;vertical-align:middle;' >"+lynr+"</td>";
	   htmlContent += "<td title='"+formartTiele(jsrnames)+"' style='text-align:left;vertical-align:middle;'>"+formartJsr(jsrnames)+"</td>"; 
	   htmlContent += "</tr>";  
		    
	});
	$("#messageListViewTable").empty();
	$("#messageListViewTable").html(htmlContent);
}

function checkUser(messageRecipientsList){
	var flag = false;
	if(messageRecipientsList != null && messageRecipientsList.length > 0){
		$.each(messageRecipientsList, function(i, obj){
			if($("#userId").val() == obj.jsrid){
				flag = true;
				return flag;
			}
		});
	}
	return flag;
}

//人员加上已读未读
function formartTiele(jsrnames){
	var jsrnameArr = jsrnames.split(",");
	var str = '';
	$.each(jsrnameArr, function(i, jname){
		jname = StringUtil.escapeStr(jname);
		var zt = jname.substring(0,1);
		var name = jname.substring(1,jname.length);
		if(zt == 0){
			str += name + "(未读),";
		}else{
			str += name + "(已读),";
		}
	});
	
	if(str != ''){
		str = str.substring(0,str.length - 1);
	}
	return str;
}

//未读人员显示红色
function formartJsr(jsrnames){
	var jsrnameArr = jsrnames.split(",");
	var str = '';
	$.each(jsrnameArr, function(i, jname){
		jname = StringUtil.escapeStr(jname);
		var zt = jname.substring(0,1);
		var name = jname.substring(1,jname.length);
		if(zt == 0){
			str += name + ",";
		}else{
			str += '<span style="color: blue">' + name + "</span>,";
		}
	});
	
	if(str != ''){
		str = str.substring(0,str.length - 1);
	}
	return str;
}

function updateStatusByDjIdUId(djid){
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/message/updateViewStatusByDjIdUId",
		type:"post",
		async: false,
		data:{
			djid : djid
		},
		dataType:"json",
		success:function(data){
			
		}
	});
}

//加载留言信息end*************************************************************************************************************

//加载附件信息begin*************************************************************************************************************
function loadAttachements(djid,sqdh){
	var this_ = this;
	AjaxUtil.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		data : {
			mainid : djid
		},
		success : function(data) {
			if (data.success) {
				var attachments = data.attachments;
				var len = data.attachments.length;
				if (len > 0) {
					var trHtml = '';
					
					if (1 == $("#profileList").find("tr").length && $("#profileList").find("td").length ==1) {
						$("#profileList").empty();
					}
					
					for (var i = 0; i < len; i++) {
						attachNumber++;
						trHtml += '<tr bgcolor="#f9f9f9" name="'+djid+'" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
						
						trHtml += '<td style="text-align:center;vertical-align:middle;">';
						trHtml += 	  "<span name='orderNumber'>"+attachNumber+"</span>";
						trHtml += '</td>';
						
						trHtml += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(sqdh)+"</td>";
						
						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].yswjm)+'" style="text-align:left;vertical-align:middle;">';
						trHtml += "<a href='javascript:void(0);' onclick=\"downloadfile('"+ attachments[i].id+"')\">"+StringUtil.escapeStr(attachments[i].yswjm)+"</a>";
						trHtml += '</td>';
						
						trHtml += '<td style="text-align:left;vertical-align:middle;">' + attachments[i].wjdxStr + ' </td>';
						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" style="text-align:left;vertical-align:middle;">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml += '<td style="text-align:center;vertical-align:middle;">' + attachments[i].czsj+ '</td>';
						trHtml += '<input type="hidden" name="relativePath" value=\''+ StringUtil.escapeStr(attachments[i].relativePath) + '\'/>';
						trHtml += '</tr>';
					}
					$("#profileList").append(trHtml);
				}
			}
		}
	});
}

function downloadfile(id){
	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function removeAttach(id){
	$("#profileList").find("tr[name='"+id+"']").each(function(index){
		attachNumber--;
		$(this).remove();
	});
	if($("#profileList").find("tr").length == 0){
		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len = $("#profileList").find("tr").length;
	if (len >= 1){
		$("#profileList").find("tr").each(function(index){
			$(this).find("span[name='orderNumber']").html(index+1);
		});
	}else{
		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
}



//加载附件信息end*************************************************************************************************************

function initMaterial(mainidList){
	if(mainidList.length > 0){
		startWait();
		AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/queryReserveDetailListByIds",
		type:"post",
		async: false,
		data:{
			mainidList:mainidList
		},
		dataType:"json",
		success:function(data){
			finishWait();
			if(data.length >0){
				reserveDetailList = data;
				searchRevision2(data);
		    }
		}
		});
	}
}

//初始化提订航材列表
function initTableRow(data){
	
	//先移除暂无数据一行
	$("#reserveDetailList").empty();
	if(JSON.stringify(data) == '[]'){
	 	$("#reserveDetailList").append("<tr><td  colspan='22' class='text-center'>暂无数据 No data.</td></tr>");
		return;
	}
	
	$.each(data,function(index,row){
		row.orderNumber = index+1;
		row.sqsl = row.sqsl;
		row.yt = row.yt;
		row.detailId = row.id;
		addRow(row);
	});
	// 标记关键字
	signByKeyword($("#keyword2_search").val(),[2,3,4,5],"#reserveDetailList tr td");
	new Sticky({tableId:'reserve-detail-list'});
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
		if (styleFlag) {
			tr += "<tr bgcolor=\""+getWarningColor("#f9f9f9",obj.SYTS,obj.JDZT)+"\" >";
			styleFlag = false;
	   } else {
		   	tr += "<tr bgcolor=\""+getWarningColor("#fefefe",obj.SYTS,obj.JDZT)+"\" >";
		   	styleFlag = true;
	   }
		tr += "<td style='text-align:center;vertical-align:middle;'>"+
		"&nbsp;&nbsp;<i style='text-align:right;' class='icon-eye-open color-blue cursor-pointer' bjh='" + StringUtil.escapeStr(obj.BJH) + "' onClick=\"findtoStock(this)\" title='查看库存 Check Stock'></i>" +
		"&nbsp;&nbsp;<i style='text-align:right;' class='icon-eye-open color-blue cursor-pointer'bjh='" + StringUtil.escapeStr(obj.BJH) + "'  onClick=\"findtoBooking(this)\" title='查看提订 Check Booking'></i></td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		tr +=  "<td title='"+StringUtil.escapeStr(obj.SQDH)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SQDH)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.BJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.BJH)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.YWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.YWMS)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.ZWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ZWMS)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.CJJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.CJJH)+"</td>";
		
		if('00000' == obj.XH){
			tr += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
	    }else{
	    	tr += "<td title='"+StringUtil.escapeStr(obj.XH)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(obj.XH)+"</td>";
	    }
		tr +=  "<td title='"+(StringUtil.escapeStr(obj.ZJH)+" "+StringUtil.escapeStr(obj.ZZWMS))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ZJH)+" "+StringUtil.escapeStr(obj.ZZWMS)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.HCLX) +"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.JDZT)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>" + indexOfTime(obj.YQQX) +"</td>";
		tr +=  "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SYTS)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>" + indexOfTime(obj.HTDHRQ) +"</td>";
		
		
		tr += "<td style='text-align:left;vertical-align:middle;'>";
		if(obj.XJZT == 2){
			tr += "<a href='#' onclick=\"openEnquiryWin('"+obj.ID+"','"+StringUtil.escapeStr(obj.SQSL)+"','"+StringUtil.escapeStr(obj.SHSL)+"')\">"+StringUtil.escapeStr(obj.XJDH)+"</a>";
		}else{
			if(obj.JDZT == '指定结束' && obj.XJZT == 1){
				tr += '指定结束';
			}else{
				tr += DicAndEnumUtil.getEnumName('enquiryStatusEnum',obj.XJZT);
			}
		}
		tr += "</td>";
		
		tr += "<td title='"+ContractUtil.formatTitle(obj.contractList)+"' style='text-align:left;vertical-align:middle;' >" + ContractUtil.formatContent(obj.contractList) + "</td>";  
		
		tr +=  "<td title='"+StringUtil.escapeStr(obj.SQSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SQSL)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.SHSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SHSL)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.SQDH)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.HTSL)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.DHSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.DHSL)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.RKSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.RKSL)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.JLDW)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.JLDW) +"</td>";
		tr += "</tr>";
	
	$("#reserveDetailList").append(tr);
}

/**
 * 跳转到查看库存页面
 * @param id
 */
function findtoStock(e){
	var bjh=$(e).attr("bjh"); //评估单号
	window.open(basePath + "/aerialmaterial/stock/main?bjh="+bjh+"&dprtcode="+$("#dprtcode_search").val());
	
}	
/**
 * 跳转到查看提订页面
 * @param id
 */
function findtoBooking(e){
	var bjh=$(e).attr("bjh"); //评估单号
	window.open(basePath + "/aerialmaterial/reserve/manage?bjh="+bjh+"&dprtcode="+$("#dprtcode_search").val());
	
}	

//获取预警颜色
function getWarningColor(bgcolor,syts,jdzt){
	if(jdzt == '指定结束' || jdzt == '全部入库'){
		return bgcolor;
	}
	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
		bgcolor = warningColor.level2;//黄色
	}
	if(Number(syts) <= yjtsJb1){
		bgcolor = warningColor.level1;//红色
	}
	return bgcolor;
}

//打开询价对话框
function openEnquiryWin(id,sqsl,shsl){
	var materialSl = sqsl;
	if(shsl != null && shsl != '' && shsl > 0){
		materialSl = shsl;
	}
	MaterialEnquiryModal.show({
		mainid:id,//在弹窗中作为条件mainid
		enquiryType:1,//询价类型
		materialSl:materialSl,//数量
		callback: function(data){//回调函数
		}
	})
}

//合同号
ContractUtil = {
	id : "ContractUtil",
	flag : true,
	formatTitle : function(list){
		var htmlContent = '';
		if(list != null && list != "" && list.length > 0 ){
			$.each(list,function(i,obj){
				htmlContent += StringUtil.escapeStr(obj.HTLSH);
				if(i != list.length - 1){
					htmlContent += ",";
				}
			});
		}
		return htmlContent;
	},
	formatContent : function(strs){
		var this_ = this;
		var htmlContent = '';
		if(strs != null && strs.length > 0){
			$.each(strs,function(i,obj){
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick="+this_.id+".vieworhideContent(this)></i><div class='contractDisplay' style='display:none'>";
				}
				htmlContent += "<a href='javascript:void(0);' onclick='"+this_.id+".viewContract(\""+obj.HTID+"\")'>"+StringUtil.escapeStr(obj.HTLSH)+"</a>";
				if(i != 0 && i != strs.length - 1){
					htmlContent += "<br/>";
				}
				if(i == strs.length - 1){
					htmlContent += "</div>";
				}
			});
		}
		return htmlContent;
	},
	vieworhideContentAll : function(obj){
		if (this.flag) {
			$(obj).removeClass("downward").addClass("upward");
			$(".contractDisplay").each(function(){
				$(this).fadeIn(500);
				$(this).prev().removeClass("icon-caret-down");
				$(this).prev().addClass("icon-caret-up");
			});
			this.flag = false;
		} else {
			$(obj).removeClass("upward").addClass("downward");
			$(".contractDisplay").each(function(){
				$(this).hide();
				$(this).prev().removeClass("icon-caret-up");
				$(this).prev().addClass("icon-caret-down");
			});
			this.flag = true;
		}
	},
	vieworhideContent : function(obj){
		var flag = $(obj).next().is(":hidden");
		if (flag) {
			$(obj).next().fadeIn(500);
			$(obj).removeClass("icon-caret-down");
			$(obj).addClass("icon-caret-up");
		} else {
			$(obj).next().hide();
			$(obj).removeClass("icon-caret-up");
			$(obj).addClass("icon-caret-down");
		}
	},
	viewContract : function(id){
		window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&pageParam=" + encodePageParam());
	}
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
		
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
		$("#dprtcode_search").val(dprtId);
		
}
	
//搜索条件重置
function searchreset2(){
	$("#divSearch2").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch2").find("select").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch2").find("textarea").each(function(){
		$(this).val("");
	});
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
	authHeight();
}
	
//航材搜索条件显示与隐藏 
function search2() {
	if ($("#divSearch2").css("display") == "none") {
		$("#divSearch2").css("display", "block");
		$("#icon2").removeClass("fa-chevron-down");
		$("#icon2").addClass("fa-chevron-up");
	} else {

		$("#divSearch2").css("display", "none");
		$("#icon2").removeClass("fa-chevron-up");
		$("#icon2").addClass("fa-chevron-down");
	}
}
		
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

function vieworhideContentAll(th_obj,tbody_obj,table){
	if(table != null && table != '' && typeof table != undefined){
		new Sticky({tableId:table});
	}
	if (this.flag) {
		$(th_obj).each(function(){
			$(this).removeClass("upward").addClass("downward");
		});
		$(tbody_obj).each(function(){
			$(this).hide();
			$(this).prev().removeClass("icon-caret-up");
			$(this).prev().addClass("icon-caret-down");
		});
		this.flag = false;
	} else {
		$(th_obj).each(function(){
			$(this).removeClass("downward").addClass("upward");
		});
		$(tbody_obj).each(function(){
			$(this).fadeIn(500);
			$(this).prev().removeClass("icon-caret-down");
			$(this).prev().addClass("icon-caret-up");
		});
		this.flag = true;
	}
}
	
//格式化工单
function formatWorkOrder(workStr){
	var card = '';
	if(workStr != null && workStr !=''){
		var arr = workStr.split(",");
		$.each(arr,function(i,s){
			if(i == 1){
				card += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=vieworhideWorkCard(this)></i><div class='reserveDisplayFile' style='display:none'>";
			}
			var gdbhArr = s.split("#");
			card += "<a href='#' onclick='viewWorkOrder(\""+gdbhArr[0]+"\")'>"+StringUtil.escapeStr(gdbhArr[1])+"</a>";
			if(i != 0 && i != arr.length - 1){
				card += "<br/>";
			}
			if(i == arr.length - 1){
				card += "</div>";
			}
		});
	}
	return card;
}

//工单显示或隐藏
function vieworhideWorkCard(obj) {
	new Sticky({tableId:'reserve_main_table'});
	var flag = $(obj).next().is(":hidden");
	if (flag) {
		$(obj).next().fadeIn(500);
		$(obj).removeClass("icon-caret-down");
		$(obj).addClass("icon-caret-up");
	} else {
		$(obj).next().hide();
		$(obj).removeClass("icon-caret-up");
		$(obj).addClass("icon-caret-down");
	}
}

function viewWorkOrder(id){
	window.open(basePath+"/produce/workorder/woView?gdid="+id);
}
//转换工单类型
function transGdlx(gdlx){
	if(gdlx == 10){
		return 1
	}else if(gdlx == 20){
		return 3
	}else {
		return 2;
	}
}
