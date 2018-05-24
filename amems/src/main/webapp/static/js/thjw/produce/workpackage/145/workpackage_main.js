$(document).ready(
		function() {
			// 导航
			Navigation(menuCode, '', '', 'sc4_145', '岳彬彬', '2017-10-22', '岳彬彬',
					'2017-08-17');
			DicAndEnumUtil.registerDic('71','wxlx_search',$("#zzjgid").val());
			decodePageParam();
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			$(".date-range-picker").daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
			refreshPermission();
			// 初始化日志功能
			logModal.init({
				code : 'B_S2_010'
			});
		});

// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var obj = {};
	obj = gatherSearchParam();
	pagination = {
		page : pageNumber,
		sort : sortType,
		order : sequence,
		rows : 20
	};
	obj.pagination = pagination;
	
	if (id != "") {
		obj.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
				url : basePath + "/produce/workpackage145/getWorkpackage145List",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						appendContentHtml(data.rows);
						var page_ = new Pagination({
							renderTo : "pagination_list",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}

						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(), [ 3, 7,11,13 ],"#packaginglist tr td");
					} else {
						$("#packaginglist").empty();
						$("#pagination_list").empty();
						$("#packaginglist").append("<tr class='text-center'><td colspan=\"21\">暂无数据 No data.</td></tr>");
					}
					hideBottom();
					new Sticky({
						tableId : 'packing_list_table'
					});
				}
			});

}

// 将搜索条件封装
function gatherSearchParam() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
	searchParam.dprtcode = $.trim($("#zzjg").val());
	searchParam.fjzch = $.trim($("#fjzch_search").val());
	searchParam.wxlx = $.trim($("#wxlx_search").val());
	searchParam.zt = $.trim($("#zt_search").val());
	searchParam.wbbs = $.trim($("#xfdw_search_select").val());
	searchParam.zxdwid= $.trim($("#zxdw_search").val());
	searchParam.xfdwid= $.trim($("#xfdw_search").val());
	searchParam.xmid = $.trim($("#xmbhid_search").val());
	return searchParam;
}
function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.zzjg = $.trim($("#zzjg").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

function appendContentHtml(list) {
	var htmlContent = '';
	choseAllIds = [];
	$.each(list,function(index, row) {
		htmlContent += "<tr  id='"+row.id+"' dprtcode='"+row.dprtcode+"' fjzch='"+StringUtil.escapeStr(row.fjzch)+"' zt='"+StringUtil.escapeStr(row.zt)+"' gbbh='"+StringUtil.escapeStr(row.gbbh)+"'  onclick=\"showHiddenContent(this)\">";
		htmlContent += "<td class='text-center fixed-column'>";		
		htmlContent += "<input type='checkbox' dprtcode='"+row.dprtcode+"' checkid='"+row.id+"' name='checkrow' />";
		htmlContent += "</td>";
		htmlContent += "<td class='text-center fixed-column'>";	
		if(row.zt ==1 || row.zt == 2){
			htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:02' onClick=\"edit('"
				+ row.id + "',event)\" title='编辑 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:03' onClick=\"invalid('"
				+ row.id +"','" + row.zt + "',event)\" title='删除 Delete '></i>&nbsp;&nbsp;";
		} 
		if( row.zt == 2 ){
			htmlContent += "<i class='iconnew-issued color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:04' onClick=\"issued('"
				+row.id + "','" + row.zt + "',event)\" title='下发 Issued'></i>&nbsp;&nbsp;<i class='iconnew-end color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:07' onClick=\"endClose('"
				+ row.id + "',event)\" title='指定结束 End'></i>&nbsp;&nbsp;";
		}
		if(row.zt == 7){
			htmlContent += "<i class='iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:05' onClick=\"doWgfk('"
				+ row.id + "',event)\" title='完工反馈 Feedback'></i>&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:06' onClick=\"closeData('"
				+ row.id + "',event)\" title='完工关闭  Close'></i>&nbsp;&nbsp;<i class='iconnew-end color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage145:main:07' onClick=\"endClose('"
				+ row.id + "',event)\" title='指定结束 End'></i>&nbsp;&nbsp;";
		}
		if(row.zt == 9){ //状态为指定结束,可进行修订
			htmlContent += "<i class='fa fa-thumb-tack color-blue cursor-pointer '  onClick=\"revision9('"+ row.id + "',event)\"  title='修订'></i>&nbsp;&nbsp;";
		}
		if(row.zt == 10){//状态为完工关闭,可进行修订
			htmlContent += "<i class='fa fa-thumb-tack color-blue cursor-pointer '  onClick=\"revision10('"+ row.id + "',event)\"  title='修订'></i>&nbsp;&nbsp;";
		}
		htmlContent += "</td>";
		htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.gbbh)+"' ><a href='#' onClick=\"viewWorkPackage('"
				+ row.id + "',event)\">"+ StringUtil.escapeStr(formatUndefine(row.gbbh))+ "</a></td>";
		if(row.zt == 9 ){
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' ><a href='#' onclick=\"toShowEndClose('"+ row.id + "',event)\" >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</a></td>";
		}
		else if(row.zt==10){
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' ><a href='#' onclick=\"toShowCloseData('"+ row.id + "',event)\" >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</a></td>";
		}else{
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</td>";
		}		
		htmlContent += "<td  class='text-left ' title='"+ StringUtil.escapeStr(formatUndefine(row.fjzch))+ "'>"
				+ StringUtil.escapeStr(formatUndefine(row.fjzch))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.fjxlh) + "' >"+ StringUtil.escapeStr(row.fjxlh) + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.gbmc) + "'>"+ StringUtil.escapeStr(row.gbmc)+ "</td>";		
		htmlContent += "<td class='text-left'>"+ StringUtil.escapeStr(row.wxlx) + "</td>";
		htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "</td>";
		htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.project==null?"":row.project.xmbm+" "+row.project.xmmc) + "' >"+StringUtil.escapeStr(row.project==null?"":row.project.xmbm+" "+row.project.xmmc)  + "</td>";
		htmlContent += "<td class='text-left' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('sourceTypeEnum',row.wbbs)) + "' >"+formatUndefine(DicAndEnumUtil.getEnumName('sourceTypeEnum',row.wbbs))  + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.xfdw) + "' >"+StringUtil.escapeStr(row.xfdw)  + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xfsj==null?"":row.xfsj.substring(0,10))+"' >"+ StringUtil.escapeStr(row.xfsj==null?"":row.xfsj.substring(0,10))+ "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zxdw)+"' >"+ StringUtil.escapeStr(row.zxdw)+ "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gzyq)+"' >"+ StringUtil.escapeStr(row.gzyq)+ "</td>";
		if(row.wgbs==0){
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' >"+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</td>";
		}else{
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' ><a href='#' onclick=\"toShowFeedback('"+ row.id + "',event)\" > "+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</a></td>";
		}
		htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";		
		if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
			htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';		
		}
		htmlContent += "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.whr==null?"":row.whr.username)+" "+StringUtil.escapeStr(row.whr==null?"":row.whr.realname)+ "'>"+ StringUtil.escapeStr(row.whr==null?"":row.whr.username)+" "+StringUtil.escapeStr(row.whr==null?"":row.whr.realname)+ "</td>";
		htmlContent += "<td class='text-center'>"+ formatUndefine(row.whsj) + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' >"+ StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+ "</td>";
		htmlContent += "</tr>";
	});
	$("#packaginglist").empty();
	$("#packaginglist").html(htmlContent);
	initWebuiPopover();
	refreshPermission();
}

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#packing_list_table_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '附件'}
	   	    ];
	 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}

// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber, sortType, sequence) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
}
// 信息检索
function searchRevision() {
	TableUtil.resetTableSorting("packing_list_table");
	goPage(1, "auto", "desc");
}

$("#lylx_search").change(function(){
	searchRevision();
})

// 搜索条件重置
function searchreset() {
	var zzjgid = $('#zzjgid').val();
	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	});
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#zt_search").val("");
	$("#keyword_search").val("");
	var xfdw = document.getElementsByName('xfdw_search_radio');
	for (var i = 0; i < xfdw.length; i++) {
		if(xfdw[i].value=='0'){
			xfdw[i].checked = 'checked';
		}	
	}
	$("#zzjg").val(zzjgid);
	$("#fjzch_search").val("");
	$("#lylx_search").val("");
	changeZxdw($("#zzjg").val());//执行单位
	$("#xfdw_search_select").val("");//刷新客户种类
	$("#xfdw_search").empty();
	$("#xfdw_search").html("<option value=''>选择全部 All</option>");
	$("#xmbhid_search").val("");
	$("#xmbh_search").val("");
	$("#wxlx_search").empty();
	$("#wxlx_search").html("<option value=''>选择全部 All</option>");
	DicAndEnumUtil.registerDic('71','wxlx_search',$("#zzjg").val());//刷新维修类型
}

$("#zzjg").change(function(){
	var zt = document.getElementsByName('xfdw_search_radio');
	for (var i = 0; i < zt.length; i++) {
		if(zt[i].value=='0'){
			zt[i].checked='checked';
		}
	}
	changeZxdw($("#zzjg").val());//执行单位
	$("#xfdw_search_select").val("");//刷新客户种类
	$("#xfdw_search").empty();
	$("#xfdw_search").html("<option value=''>选择全部 All</option>");
	$("#xmbhid_search").val("");
	$("#xmbh_search").val("");
	$("#wxlx_search").empty();
	$("#wxlx_search").html("<option value=''>选择全部 All</option>");
	DicAndEnumUtil.registerDic('71','wxlx_search',$("#zzjg").val());//刷新维修类型
})

function khzlChange(){
	if($("#xfdw_search_select").val()=="0"){
		changeXfdw($("#zzjg").val());
	}else if($("#xfdw_search_select").val()=="1"){
		changeKh($("#zzjg").val());
	}else{
		$("#xfdw_search").empty();
		$("#xfdw_search").html("<option value=''>选择全部 All</option>");
	}
	searchRevision();//刷新页面
}

function xfdwChange(){
	searchRevision();//刷新页面
}

function changeZxdw(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/sys/department/getBmList",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#zxdw_search").empty();			   
			   var nbzxdwhtml =  "<option value=''>显示全部 All</option>";
				if(data != null && data.length >0){				
					$.each(data,function(i,department){
						nbzxdwhtml += "<option value='"+StringUtil.escapeStr(department.id)+"' >"+StringUtil.escapeStr(department.dprtname)+"</option>";
					});
				}
				$("#zxdw_search").html(nbzxdwhtml);
		}
	});	
}

function changeXfdw(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/sys/department/getChildrentList",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#xfdw_search").empty();			   
			   var nbzxdwhtml =  "<option value=''>显示全部 All</option>";
				if(data != null && data.length >0){				
					$.each(data,function(i,department){
						nbzxdwhtml += "<option value='"+StringUtil.escapeStr(department.id)+"' >"+StringUtil.escapeStr(department.dprtname)+"</option>";
					});
				}
				$("#xfdw_search").html(nbzxdwhtml);
		}
	});	
}

function changeKh(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/baseinfo/customer/getCustomers",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#xfdw_search").empty();			   
			   var khhtml = "<option value=''>显示全部 All</option>";
				if(data != null && data.length >0){				
					$.each(data,function(i,customer){
						khhtml += "<option value='"+StringUtil.escapeStr(customer.id)+"' >"+StringUtil.escapeStr(customer.khmc)+"</option>";
					});
				}
				$("#xfdw_search").html(khhtml);
		}
	});	
}

function changeXm(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/baseinfo/project/getProjects",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#xmbh_search").empty();			   
			   var xmbhhtml = "<option value=''>显示全部 All</option>";
				if(data != null && data.length >0){					
					$.each(data,function(i,project){
						xmbhhtml += "<option value='"+StringUtil.escapeStr(project.id)+"' >"+StringUtil.escapeStr(project.xmbm)+"</option>";
					});
				}
				$("#xmbh_search").html(xmbhhtml);
		}
	});	
}

// 搜索条件显示与隐藏
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

// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
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
	goPage(currentPage, obj, orderStyle.split("_")[1]);
}


// 新增
function add() {
	workpackage_modal.init({
		fjzch :$("#fjzch_search").val(),
		dprtcode:userJgdm,//组织机构
		workpackageId:'',//工包id
		operation:true,//操作类型
		modalName:'新增工包',//模态框中文名称
		modalEname:'Add Workpackage',//模态框英文名称
		callback:function(data){
			doRequest(data,"保存成功!",basePath + "/produce/workpackage145/add","workpackage_modal");
		}
	});
}

function edit(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"edit",function(obj){
		if(!(obj.zt == 1 || obj.zt == 2 )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		workpackage_modal.init({
			obj :obj,//工包对象
			fjzch :obj.fjzch,
			dprtcode:obj.dprtcode,//组织机构
			workpackageId:obj.id,//工包id
			operation:true,//操作类型
			modalName:'编辑工包',//模态框中文名称
			modalEname:'edit Workpackage',//模态框英文名称
			callback:function(data){
				doRequest(data,"修改成功!",basePath + "/produce/workpackage145/update","workpackage_modal");
			}
		});
	})
}
//指定结束
function endClose(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"end",function(obj){
		if(!(obj.zt == 7 || obj.zt == 2 )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		workpackage_end_modal.init({
			obj:obj,
			operation:true,
			dprtcode:obj.dprtcode,//组织机构
			workpackageId:obj.id,//工包id
			callback:function(data){
				doRequest(data,"指定结束成功!",basePath + "/produce/workpackage145/end","workpackage_end_modal");
			}
		});
	})
}

function decodePageParam() {
	try {
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#zzjg").val(params.zzjg);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.sort,
					pageParamJson.pagination.order);
		} else {
			goPage(1, "auto", "desc");
		}
	} catch (e) {
		goPage(1, "auto", "desc");
	}
}
// 刷新页面
function refreshPage() {
	goPage(pagination.page, pagination.sort, pagination.order);
}


function viewWorkPackage(id,e){
	e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }
	window.open(basePath+"/produce/workpackage145/view?id="+id);
}
//完工关闭
function closeData(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"close",function(obj){
		if(!(obj.zt == 7  )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		workpackage_close_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:true,		
			callback:function(data){
				doRequest(data,"完工关闭成功!",basePath + "/produce/workpackage145/close","workpackage_close_modal");
			}
		});
	})
}
//完工反馈
function doWgfk(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"feedback",function(obj){
		if(!(obj.zt == 7  )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		workpackage_feedback_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:true,		
			callback:function(data){
				doRequest(data,"工包完工反馈成功!",basePath + "/produce/workpackage145/feedback","workpackage_feedback_modal");
			}
		});
	})
}

function showHiddenContent(this_){
 	var id = $(this_).attr("id");
 	var fjzch = $(this_).attr("fjzch");
 	var gbbh = $(this_).attr("gbbh");
 	var dprtcode = $(this_).attr("dprtcode");
 	var zt = $(this_).attr("zt");
 	if($(".bottom_hidden_content").css("display")=='block'){
 		$(".bottom_hidden_content").slideUp(100);
 	}
 	$(".bottom_hidden_content").slideDown(100);
 	new Sticky({tableId:'assessmentTable'}); //初始化表头浮动
 	
 	
	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#pagination_list .fenye"));
	}
 	$("#tabDiv").css("display","none");
	$("#tabDiv").css("display","block");
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $("#packing_list_table_div"));
	}
	//工单明细
	workorder_detail.init({
		workpackageId:id,
		gbbh:gbbh,
		fjzch:fjzch,
		zt:zt,
		dprtcode:dprtcode,
		operationShow:true,
	});
 	 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
 	new Sticky({
		tableId : 'packing_list_table'
	});
 	 App.resizeHeight();
 	 
}	

function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}

function doRequest(data,message,url,modal){
	startWait($("#"+modal));
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		modal : $("#"+modal),
		success : function(data) {
			id = data;				
			pageParam=encodePageParam();
			AlertUtil.showMessage(message, decodePageParam());
			$("#"+modal).modal("hide");
			finishWait($("#"+modal));
			refreshPermission();
		}
	});
}
function getDataById(id,option,obj){
	var this_ = this;
	var param={};
	param.id=id;
	var paramsMap={};
	paramsMap = {
			"option" : option 
		};
	param.paramsMap=paramsMap;
	AjaxUtil.ajax({
		url : basePath + "/produce/workpackage145/getRecord",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data != null){
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}			
		}
	});
}

//删除
function invalid(id,zt,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	var param = {};
	param.id = id;
	param.zt = zt;
	AlertUtil.showConfirmMessage("确定要删除吗？", {
		callback : function() {
			AjaxUtil.ajax({
				url : basePath + "/produce/workpackage145/delete",
				type : "post",
				async : false,
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(param),
				dataType : "json",
				success : function(data) {
					pageParam=encodePageParam();
					AlertUtil.showMessage('删除成功!', decodePageParam());
					refreshPermission();
				}
			});
		}
	});
}

//查看指定结束
function toShowEndClose(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"end",function(obj){
		workpackage_end_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,//组织机构
			workpackageId:obj.id,//工包id
			operation:false,
		});
	})
}
//查看完工关闭
function toShowCloseData(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"close",function(obj){
		workpackage_close_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:false,
		});
	})
}
//查看完工反馈
function toShowFeedback(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"feedback",function(obj){
		workpackage_feedback_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:false,		
		});
	})
}
//下发
function issued(pid,zt,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	var param={};
	param.id=pid;
	AlertUtil.showConfirmMessage("确定要下发吗？", {
		callback : function() {
			AjaxUtil.ajax({
				url : basePath + "/produce/workpackage145/issued",
				type : "post",
				async : false,
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(param),
				dataType : "json",
				success : function(data) {
					id=data;
					pageParam=encodePageParam();
					AlertUtil.showMessage('下发成功', decodePageParam());
					refreshPermission();
				}
			});
		}
	});
}

function addProject(){
	window.open(basePath+"/baseinfo/project/main");
}

//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}

function ToHcList(){
	if($("#packaginglist").find("tr input:checked").length > 0){
		setParam();
		window.open(basePath+"/produce/maintenance/monitoring/material/tool/detail?type=4");
	}else{
		AlertUtil.showMessage("请选择数据!");
	}
}

function  setParam(){
	var idList = [];
	var dprtcode="";
	$("#packaginglist").find("tr input:checked").each(function(){
		var checkid = $(this).attr("checkid");
		dprtcode=$(this).attr("dprtcode");
		idList.push(checkid);
	});
	var params = {};
	params.dprtcode = dprtcode;
	params.idList = idList;
	params.lyidList = idList;
	pageParam.params = params;
	localStorage.setItem("4",JSON.stringify(params));
}

function revision9(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	getDataById(id,"revision",function(obj){
		if(!(obj.zt == 9 )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		workpackage_end_modal.init({
			obj:obj,
			operation:true,
			dprtcode:obj.dprtcode,//组织机构
			workpackageId:obj.id,//工包id
			callback:function(data){
				data.zt = obj.zt;
				doRequest(data,"修订成功!",basePath + "/produce/workpackage145/doRevision","workpackage_end_modal");
			}
		});
	})
}

function revision10(id,e){
	e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }
getDataById(id,"close",function(obj){
	if(!(obj.zt == 10)){
		AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
		return;
	}
	workpackage_close_modal.init({
		obj:obj,
		dprtcode:obj.dprtcode,
		workpackageId:obj.id,
		operation:true,		
		callback:function(data){
			data.zt=obj.zt;
			doRequest(data,"修订成功!",basePath + "/produce/workpackage145/doRevision","workpackage_close_modal");
		}
	});
})
}

function openWinXmbh(){
	var this_ = this;
	var wbbs = $("#xfdw_search_select").val();
	var khid = $("#xfdw_search").val();
	project_modal.init({
		dprtcode:$("#zzjg").val(),
		wbbs:wbbs,
		khid:khid,
		selected:$("#xmbhid_search").val(),
		callback:function(data){
			$("#xmbhid_search").val(StringUtil.escapeStr(data.id));
			$("#xmbh_search").val(StringUtil.escapeStr(data.xmbm)+" "+StringUtil.escapeStr(data.xmmc));
		}
	})
}

function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());// 关键字查询
	var dprtcode = $.trim($("#zzjg").val());
	var fjzch = $.trim($("#fjzch_search").val());
	var wxlx = $.trim($("#wxlx_search").val());
	var zt = $.trim($("#zt_search").val());
	var wbbs = $.trim($("#xfdw_search_select").val());
	var zxdwid= $.trim($("#zxdw_search").val());
	var xfdwid= $.trim($("#xfdw_search").val());
	var xmid = $.trim($("#xmbhid_search").val());
	window.open(basePath+"/produce/workpackage145/WorkPackage145.xls?dprtcode="
 				+ dprtcode 
 				+ "&fjzch="+encodeURIComponent(fjzch)+"&wxlx="+encodeURIComponent(wxlx)
 				+ "&zt="+zt
 				+ "&wbbs="+encodeURIComponent(wbbs)+"&zxdwid="+encodeURIComponent(zxdwid)
 				+ "&xfdwid="+encodeURIComponent(xfdwid)+"&xmid="+encodeURIComponent(xmid)
 				+"&keyword="+ encodeURIComponent(keyword));
	
}