
var type = $("#type").val();
var reserveDetailList = [];
var pageId = "repair_main";
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
	
	$("#reserveDetailList").append("<tr><td  colspan='20' class='text-center'>暂无数据 No data.</td></tr>");
	MessageListViewUtil.show({messageHead : false});//显示留言
	AttachmengsListView.show({attachHead : false});//显示附件
	decodePageParam();//开始的加载默认的内容 
	refreshPermission();
	authHeightUtil.authHeightOne(pageId);
	$(window).resize(function() {
		authHeightUtil.authHeightOne(pageId);
	});
});

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

	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		MessageListViewUtil.clear();
		AttachmengsListView.clear();	
		authHeightUtil.authHeightOne(pageId);
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		if(type == 'manage'){
			url = basePath+"/aerialmaterial/materialrepair/queryRepairPageList";
		}
		if(type == 'approve'){
			url = basePath+"/aerialmaterial/materialrepair/queryApproveRepairPageList";
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
				   signByKeyword($("#keyword_search").val(),[3,4,5,6,7,8,12],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"20\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'repair_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var paramsMap = {};
		 var sqrrealname = $.trim($("#sqrrealname_search").val());
		 var jjcd = $.trim($("#jjcd_search").val());
		 var zt = $.trim($("#zt_search").val());
		 var sqrq = $.trim($("#sqrq_search").val());
		 paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = keyword;
		 if('' != sqrrealname){
			 paramsMap.sqrrealname = sqrrealname;
		 }
		 if('' != jjcd){
			 paramsMap.jjcd = jjcd;
		 }
		 if('' != zt){
			 paramsMap.zt = zt;
		 }
		 if(null != sqrq && "" != sqrq){
			 var sqrqBegin = sqrq.substring(0,10)+" 00:00:00";
			 var sqrqEnd = sqrq.substring(13,23)+" 23:59:59";
			 paramsMap.sqrqBegin = sqrqBegin;
			 paramsMap.sqrqEnd = sqrqEnd;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.reserveMain.id+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectRepair('"+row.reserveMain.id+"','"+formatUndefine(row.reserveMain?row.reserveMain.sqdh:'')+"')>";
			   } else {
				   htmlContent += "<tr id='"+row.reserveMain.id+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectRepair('"+row.reserveMain.id+"','"+formatUndefine(row.reserveMain?row.reserveMain.sqdh:'')+"')>";
			   }
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   if(type == 'manage' && row.reserveMain.zt == 1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:repair:manage:02' onClick=\"edit('" + row.reserveMain.id + "',"+row.reserveMain.zt+")\" title='修改 Edit'></i>";
				   htmlContent += "&nbsp;&nbsp;<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:repair:manage:05' onClick=\"cancel('"+ row.reserveMain.id + "',"+row.reserveMain.zt+")\" title='作废 Invalid'></i>";
			   }
			   if(type == 'approve' && row.reserveMain.zt != 9){
				   if(row.reserveMain.zt == 2){
					   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:repair:approve:01' onClick=\"audit('"+ row.reserveMain.id + "',"+row.reserveMain.zt+")\" title='审核 Review'></i>";
				   }
				   htmlContent += "&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:approve:02' djid='"+row.reserveMain.id+"' sqdh='"+StringUtil.escapeStr(row.reserveMain.sqdh)+"' zdjsyy='' zdjsr='' zdjsrq='' deteilid='"+row.id+"' onClick=\"shutDown(this,true)\" title='指定结束 End'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.reserveMain.id+"')\">"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.sqdh:'')+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.bjh:'')+"' class='text-left'>"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.bjh:'')+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.ywms:'')+"' class='text-left'>"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.ywms:'')+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.zwms:'')+"' class='text-left'>"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.zwms:'')+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.materialHistory?(row.materialHistory.hcMainData?row.materialHistory.hcMainData.cjjh:''):'')+"' class='text-left'>"+StringUtil.escapeStr(row.materialHistory?(row.materialHistory.hcMainData?row.materialHistory.hcMainData.cjjh:''):'')+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.sn:'')+"' class='text-left'>"+StringUtil.escapeStr(row.materialHistory?row.materialHistory.sn:'')+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('urgencyEnum',row.reserveMain?row.reserveMain.jjcd:'') +"</td>";
			   
			   htmlContent += "<td  class='text-left'>";
			   if(row.reserveMain.zt == 9){
				   var zdjsr = row.reserveMain.jsr?row.reserveMain.jsr.displayName:'';
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.reserveMain.id+"' sqdh='"+StringUtil.escapeStr(row.reserveMain.sqdh)+"' zdjsyy='"+StringUtil.escapeStr(row.reserveMain.zdjsyy)+"' zdjsr='"+StringUtil.escapeStr(zdjsr)+"' zdjsrq='"+row.reserveMain.zdjsrq+"' deteilid='' onclick=\"shutDown(this,false)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('reserveStatusEnum',row.reserveMain?row.reserveMain.zt:'');
			   }
			   htmlContent += "</td>";
			   
			   htmlContent += "<td class='text-left'>"+formatWorkOrder(row.glgd)+"</td>";
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.sqyy:'')+"' class='text-left'>"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.sqyy:'')+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.reserveMain?(row.reserveMain.sqr?row.reserveMain.sqr.displayName:''):'')+"' class='text-left'>"+StringUtil.escapeStr(row.reserveMain?(row.reserveMain.sqr?row.reserveMain.sqr.displayName:''):'')+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.sqsj:'')+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.reserveMain?(row.reserveMain.spr?row.reserveMain.spr.displayName:''):'')+"' class='text-left'>"+StringUtil.escapeStr(row.reserveMain?(row.reserveMain.spr?row.reserveMain.spr.displayName:''):'')+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.reserveMain?row.reserveMain.spsj:'')+"</td>"; 
			   //htmlContent += "<td class='text-center'>"+formatUndefine(row.reserveMain?row.reserveMain.spyj:'')+"</td>"; 
			   
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
				   htmlContent += "<a href='javascript:void(0);' onclick=\"openMessageListWin(this,'"+ row.reserveMain.id+"')\"><span name='noRead'>"+noRead+"</span>/"+readAll+"</a>";
			   }else{
				   htmlContent += noRead+"/"+readAll;
			   }
			   htmlContent += "</td>"; 
			   
			   htmlContent += "<td class='text-left'>";
			   
			   if(row.xjzt == 2){
				   htmlContent += "<a href='#' onclick=\"openEnquiryWin('"+row.id+"')\">"+StringUtil.escapeStr(row.xjdh)+"</a>";
			   }else{
				   if(row.reserveMain.zt == 9 && row.xjzt == 1){
					   htmlContent += '指定结束';
				   }else{
					   htmlContent += DicAndEnumUtil.getEnumName('enquiryStatusEnum',row.xjzt);
				   }
			   }
			   
			   htmlContent += "</td>";
				   
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewContract('"+row.htid+"')\">"+StringUtil.escapeStr(row.htlsh)+"</a>";
			   htmlContent += "</td>";
			   
			   htmlContent += "<td>"+AccessDepartmentUtil.getDpartName(row.reserveMain?row.reserveMain.dprtcode:'')+"</td>";
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

//打开询价对话框
function openEnquiryWin(id){
	MaterialEnquiryModal.show({
		mainid:id,//在弹窗中作为条件mainid
		enquiryType:2,//询价类型
		callback: function(data){//回调函数
		}
	})
}

//新增送修单
function add(){
	window.location = basePath+"/aerialmaterial/repair/add?pageParam="+encodePageParam();
}
	 
//编辑送修单
function edit(id,zt){
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的送修单才能编辑!");
		return false;
	}
	if(checkEdit(id)){	
		window.location = basePath+"/aerialmaterial/repair/edit?id=" + id+"&pageParam="+encodePageParam();
	}
}

//审核送修单
function audit(id,zt){
	if(zt != 2){
		AlertUtil.showErrorMessage("对不起,只有提交状态下的送修单才能审核!");
		return false;
	}
	if(checkAudit(id)){	
		window.location = basePath+"/aerialmaterial/repair/audit?id=" + id+"&pageParam="+encodePageParam();
	}
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/repair/view?id=" + id+"&type="+type+"&pageParam="+encodePageParam());
}

//查看合同
function viewContract(id){
	window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&pageParam="+encodePageParam());
}

//作废
function cancel(id,zt) {
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的送修单才能作废!");
		return false;
	}
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/repair/cancel",
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

//指定结束
function shutDown(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	var deteilid = $(this_).attr("deteilid");
	AssignEndModal.show({
		chinaHead:'送修单号',//单号中文名称
		englishHead:'Repair No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:zdjsr,//指定结束人
		jssj:zdjsrq,//指定结束时间
		jsyy:zdjsyy,//指定结束原因
		showType : false,
		callback: function(data){//回调函数
			if(null != data && data != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy :  data.gbyy
				};
				sbShutDown(obj,deteilid);
			}
		}
	});
}

//确认指定结束
function sbShutDown(repair,deteilid) {
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/repair/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(repair),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('指定结束成功!');
			$("#AssignEndModal").modal("hide");
			id = deteilid;
			refreshPage();
		}
	});
}

//检查送修单是否可以修改
function checkEdit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/aerialmaterial/repair/checkEdit",
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

//检查送修单是否可以审核
function checkAudit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/aerialmaterial/repair/checkAudit",
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

//加载航材信息
function selectRepair(id,sqdh){
	// 下方div是否显示
	var isBottomShown = false;
	if($("#tabDiv").is(":visible")){
		isBottomShown = true;
	}
	var str = '';
	str = '已选择送修单'+sqdh;
	$("#selectRow").html(str);
	MessageListViewUtil.show({
		messageHead : false,
		djid:id,
		lx:2
	});//加载留言
	AttachmengsListView.show({
		attachHead : false,
		djid:id,
		fileType:"repair"
	});//加载附件
	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
	$("#tabDiv").css("display","none");
	$("#tabDiv").css("display","block");
	authHeightUtil.authHeightOne(pageId);
	// 选中行可见
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($("#"+id), $(".table-tab-type_table"));
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
		authHeightUtil.authHeightOne(pageId);
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
		
		function vieworhideContentAll(){
			new Sticky({tableId:'repair_main_table'});
			if (this.flag) {
				$(".repairDisplayFile").each(function(){
					$(this).fadeIn(500);
					$(this).prev().removeClass("icon-caret-down");
					$(this).prev().addClass("icon-caret-up");
				});
				this.flag = false;
			} else {
				$(".repairDisplayFile").each(function(){
					$(this).hide();
					$(this).prev().removeClass("icon-caret-up");
					$(this).prev().addClass("icon-caret-down");
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
						card += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=vieworhideWorkCard(this)></i><div class='repairDisplayFile' style='display:none'>";
					}
					var gdbhArr = s.split("#");
					card += "<a href='#' onclick='viewWorkOrder(\""+gdbhArr[0]+"\")'>"+gdbhArr[1]+"</a>";
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
			new Sticky({tableId:'repair_main_table'});
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
		 
