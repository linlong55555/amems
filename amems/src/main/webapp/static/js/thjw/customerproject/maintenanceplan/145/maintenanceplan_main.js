$(document).ready(
		function() {
			// 导航
			Navigation(menuCode, '', '', 'gc_4_1', '林龙', '2017-08-01', '林龙',
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
				url : basePath + "/customerproject/maintenanceplan145/getWorkpackage145List",
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
						signByKeyword($("#keyword_search").val(), [ 2, 6,10 ],"#packaginglist tr td");
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
	searchParam.zxdw= $.trim($("#zxdw_search").val());
	searchParam.wbbs= $.trim($("#xfdw_search").val());
	var paramsMap={};
	paramsMap.kh= $.trim($("#kh_search").val());
	paramsMap.xmbh= $.trim($("#xmbh_search").val());
	searchParam.paramsMap=paramsMap;
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
		htmlContent += "<tr  id='"+row.id+"' dprtcode='"+row.dprtcode+"' fjzch='"+row.fjzch+"' zt='"+row.zt+"' gbbh='"+row.gbbh+"'  onclick=\"showHiddenContent("+index+",this)\">";
		htmlContent += "<td class='text-center fixed-column'>";		
		htmlContent += "<input type='checkbox' dprtcode='"+row.dprtcode+"' checkid='"+row.id+"' name='checkrow' index='"+index+"' onclick=\"showHiddenContent("+index+",this)\" />";
		htmlContent += "</td>";
	
		htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.gbbh)+"' ><a href=\"javascript:viewWorkPackage('"
				+ row.id + "')\">"+ StringUtil.escapeStr(formatUndefine(row.gbbh))+ "</a></td>";
		if(row.zt == 9 ){
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' ><a href='#' onclick=\"toShowEndClose('"+ row.id + "')\" >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</a></td>";
		}
		else if(row.zt==10){
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' ><a href='#' onclick=\"toShowCloseData('"+ row.id + "')\" >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</a></td>";
		}else{
			htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('workpackageStatusEnum', row.zt))+ "</td>";
		}		
		htmlContent += "<td  class='text-left ' title='"+ StringUtil.escapeStr(formatUndefine(row.fjzch))+ "'>"
				+ StringUtil.escapeStr(formatUndefine(row.fjzch))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.fjxlh) + "' >"+ StringUtil.escapeStr(row.fjxlh) + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.gbmc) + "'>"+ StringUtil.escapeStr(row.gbmc)+ "</td>";		
		htmlContent += "<td class='text-left'>"+ StringUtil.escapeStr(row.wxlx) + "</td>";
		htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhKsrq==null?"":(row.jhKsrq).substring(0,10))+ "</td>";
		htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.jhJsrq==null?"":(row.jhJsrq).substring(0,10))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.project==null?"":row.project.xmbm+" "+row.project.xmmc) + "' >"+StringUtil.escapeStr(row.project==null?"":row.project.xmbm+" "+row.project.xmmc)  + "</td>";
		htmlContent += "<td class='text-center' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('sourceTypeEnum',row.wbbs)) + "' >"+formatUndefine(DicAndEnumUtil.getEnumName('sourceTypeEnum',row.wbbs))  + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.xfdw) + "' >"+StringUtil.escapeStr(row.xfdw)  + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xfsj==null?"":row.xfsj.substring(0,10))+"' >"+ StringUtil.escapeStr(row.xfsj==null?"":row.xfsj.substring(0,10))+ "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zxdw)+"' >"+ StringUtil.escapeStr(row.zxdw)+ "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gzyq)+"' >"+ StringUtil.escapeStr(row.gzyq)+ "</td>";
		if(row.wgbs==0){
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' >"+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</td>";
		}else{
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' ><a href='#' onclick=\"toShowFeedback('"+ row.id + "')\" > "+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</a></td>";
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
	goPage(1, "auto", "desc");
}

$("#xfdw_search").change(function(){
	searchRevision();
})

// 搜索条件重置
function searchreset() {
	var zzjgid = $('#zzjgid').val();
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	});
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#zt_search").val("");
	$("#keyword_search").val("");
	$("#nbzxdw_search").val("");
	var zt = document.getElementsByName('zt_search');
	for (var i = 0; i < zt.length; i++) {
		zt[i].checked = 'checked';
	}
	$("#zzjg").val(zzjgid);
	$("#fjzch_search").val("");
	$("#xfdw_search").val("");
	changeZxdw($("#zzjg").val());//执行单位
	changeKh($("#zzjg").val());//刷新客户
	changeXm($("#zzjg").val());//刷新项目编号
	$("#wxlx_search").empty();
	$("#wxlx_search").html("<option value=''>选择全部 All</option>");
	DicAndEnumUtil.registerDic('71','wxlx_search',$("#zzjg").val());//刷新维修类型
}

$("#zzjg").change(function(){
	changeZxdw($("#zzjg").val());//执行单位
	changeKh($("#zzjg").val());//刷新客户
	changeXm($("#zzjg").val());//刷新项目编号
	DicAndEnumUtil.registerDic('71','wxlx_search',$("#zzjg").val());//刷新维修类型
})

function changeZxdw(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/sys/department/getBmList",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#nbzxdw_search").empty();			   
			   var nbzxdwhtml =  "<option value=''>选择全部 All</option>";
				if(data != null && data.length >0){				
					$.each(data,function(i,department){
						nbzxdwhtml += "<option value='"+StringUtil.escapeStr(department.id)+"' >"+StringUtil.escapeStr(department.dprtname)+"</option>";
					});
				}
				$("#nbzxdw_search").html(nbzxdwhtml);
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
			 $("#kh_search").empty();			   
			   var khhtml = "<option value=''>选择全部 All</option>";
				if(data != null && data.length >0){				
					$.each(data,function(i,customer){
						khhtml += "<option value='"+StringUtil.escapeStr(customer.id)+"' >"+StringUtil.escapeStr(customer.khmc)+"</option>";
					});
				}
				$("#kh_search").html(khhtml);
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
			   var xmbhhtml = "<option value=''>选择全部 All</option>";
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

//指定结束
function endClose(id,zt){
	getDataById(id,function(obj){
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
				doRequest(data,"指定结束成功!",basePath + "/customerproject/workpackage145/end","workpackage_end_modal");
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


function viewWorkPackage(id){
	window.open(basePath+"/customerproject/workpackage145/view?id="+id);
}
//完工关闭
function closeData(id){
	getDataById(id,function(obj){
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
				doRequest(data,"完工关闭成功!",basePath + "/customerproject/workpackage145/close","workpackage_close_modal");
			}
		});
	})
}
//完工反馈
function doWgfk(id,zt){
	getDataById(id,function(obj){
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
				doRequest(data,"工包完工反馈成功!",basePath + "/customerproject/workpackage145/feedback","workpackage_feedback_modal");
			}
		});
	})
}
//修订
function openWinXDClose(id,zt){
	getDataById(id,function(obj){
		if(!(obj.zt == 9 || obj.zt == 10  )){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		if(obj.zt==9){
			workpackage_end_modal.init({
				obj:obj,
				operation:true,
				dprtcode:obj.dprtcode,//组织机构
				workpackageId:obj.id,//工包id
				callback:function(data){
					data.zt=obj.zt;
					doRequest(data,"保存成功!",basePath + "/customerproject/maintenanceplan145/doXDClose","workpackage_end_modal");
				}
			});
		}else{
			workpackage_close_modal.init({
				obj:obj,
				dprtcode:obj.dprtcode,
				workpackageId:obj.id,
				operation:true,		
				callback:function(data){
					data.zt=obj.zt;
					doRequest(data,"保存成功!",basePath + "/customerproject/maintenanceplan145/doXDClose","workpackage_close_modal");
				}
			});
		}
	})
}

function showHiddenContent(index,this_){
 	var id = $(this_).attr("id");
 	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
 	if($(".bottom_hidden_content").css("display")=='block'){
 		$(".bottom_hidden_content").slideUp(100);
 	}
 	$(".bottom_hidden_content").slideDown(100);
 	new Sticky({tableId:'packing_list_table'}); //初始化表头浮动
 	
// 	var $checkbox1 = $("#packing_list_table :checkbox[name='checkrow']:eq("+index+")");
//	var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
//	var checked = $checkbox1.is(":checked");
//	$checkbox1.attr("checked", !checked);
//	$checkbox2.attr("checked", !checked);
 	
 	
	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
	}
// 	$("#tabDiv").css("display","none");
//	$("#tabDiv").css("display","block");
	engine_health_monitor.init(id);
	maintenanceplan_gannt.init(id);
	$("#hcgjid").val(id);
	App.resizeHeight();
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $("#packing_list_table_div"));
	}
 	 
}	

function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}
function customResizeHeight(bodyHeight, tableHeight){
	if($(".displayContent").css("display")=="block"){
		var panelBody=parseInt($(".displayContent .panel-body").css("height"));
		$(".displayContent .panel-body").removeAttr("style");
		$(".displayContent .panel-body").css({"height":(panelBody-5)+"px","padding":"0px"})
		var panelBodyHeight=0;
		if(navigator.userAgent.indexOf("Chrome") > -1){
			 panelBodyHeight=panelBody-5;
           }else{
        	 panelBodyHeight=panelBody-5;
           }
		
	    if(panelBodyHeight<150){
			$(".displayContent .panel-body div").css({"height":"150px","overflow":"auto"});
		}else{
			$(".displayContent .panel-body div").css({"height":panelBodyHeight+"px","overflow":"auto"});	
		}
		
	}
	/*alert($(".displayContent .panel-body").css("height"));*/
}
function doRequest(data,message,url,modal){
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			id = data;				
			pageParam=encodePageParam();
			AlertUtil.showMessage(message, decodePageParam());
			$("#"+modal).modal("hide");
			refreshPermission();
		}
	});
}
function getDataById(id,obj){
	var this_ = this;
	var param={};
	param.id=id;
	AjaxUtil.ajax({
		url : basePath + "/customerproject/workpackage145/getRecord",
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

//查看指定结束
function toShowEndClose(id){
	getDataById(id,function(obj){
		workpackage_end_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,//组织机构
			workpackageId:obj.id,//工包id
			operation:false,
		});
	})
}
//查看完工关闭
function toShowCloseData(id){
	getDataById(id,function(obj){
		workpackage_close_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:false,
		});
	})
}
//查看完工反馈
function toShowFeedback(id){
	getDataById(id,function(obj){
		workpackage_feedback_modal.init({
			obj:obj,
			dprtcode:obj.dprtcode,
			workpackageId:obj.id,
			operation:false,		
		});
	})
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
		window.open(basePath+"/produce/maintenance/monitoring/material/tool/detail?type=4&pageParam=" + encodeHcPageParam());
	}else{
		AlertUtil.showMessage("请选择数据!");
	}
}

function  encodeHcPageParam(){
	var idList = [];
	var dprtcode="";
	$("#packaginglist").find("tr input:checked").each(function(){
		var checkid = $(this).attr("checkid");
		dprtcode=$(this).attr("dprtcode");
		idList.push(checkid);
	});
	var pageParam = {};
	var params = {};
	params.dprtcode = dprtcode;
	params.idList = idList;
	params.lyidList = idList;
	pageParam.params = params;
	console.info(idList);
	localStorage.setItem("4",JSON.stringify(params));
	return Base64.encode(JSON.stringify(pageParam));
}