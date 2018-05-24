$(document).ready(function() {
	// 导航
	Navigation(menuCode, '', '', 'sc4_135', '岳彬彬', '2017-10-09', '岳彬彬',
			'2017-08-17');
	setDefaultDateRangePickerValue();
	refreshPermission();
	
	//判断审核项目单id是否为空
	var shxmdid = $("#shxmdid").val();
	if(shxmdid !=""){
		add(shxmdid);
	}

});

function add(shxmdid){
 	auditDiscoveryAdd.show({
		data:{},//原值，在弹窗中默认勾选
		ope_type : 1,//新增
		dprtcode:userJgdm, //机构代码
		shxmdid:shxmdid,//审核通知单id
		callback:function(data){
			doRequest(data,data.zt==0?"保存成功!":"提交成功!",basePath + "/quality/noticeofdiscovery/save","audit_discovery_alert_Modal");
		}
	});
 	
}

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
				url : basePath + "/quality/noticeofdiscovery/queryList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						appendContentHtml(data.rows);
						var page_ = new Pagination({
							renderTo : "auditdiscovery_main_table_pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}

						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(), [ 3, 8 ],"#auditdiscovery_main_table_list tr td");
					} else {
						$("#auditdiscovery_main_table_list").empty();
						$("#auditdiscovery_main_table_pagination").empty();
						$("#auditdiscovery_main_table_list").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
					}
					hideBottom();
					new Sticky({
						tableId : 'auditdiscovery_main_table'
					});
				}
			});

}

// 将搜索条件封装
function gatherSearchParam() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	searchParam.lx = $.trim($("#lx_search").val());
	var shdxbh = $.trim($("#shdxbh_search").val());
	var shdxmc = $.trim($("#shdxmc_search").val());
	if("" == shdxbh&&"" == shdxmc){
		searchParam.shdxbh = $.trim($("#shdx_search").val());
	}else{
		searchParam.shdxbh = shdxbh;
		searchParam.shdxmc = shdxmc;
	}
	searchParam.wtlx = $.trim($("#wtlx_search").val());
	searchParam.shlb=$.trim($("#shlb_search").val());
	var paramsMap={};
	var sjShrq = $.trim($("#sjShrq_search").val());
	if (null != sjShrq && "" != sjShrq) {
		var sjShrqBegin = sjShrq.substring(0, 10) + " 00:00:00";
		var sjShrqEnd = sjShrq.substring(13, 23) + " 23:59:59";
		paramsMap.sjShrqBegin = sjShrqBegin;
		paramsMap.sjShrqEnd = sjShrqEnd;
	}
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
	$.each(list,function(index, row) {
		htmlContent += "<tr  id='"+row.id+"' dprtcode='"+row.dprtcode+"' zt='"+row.zt+"'  onclick=\"showHiddenContent(this)\">";
		htmlContent += "<td class='text-center fixed-column'>";	
		if(row.zt ==0 ){
			htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:noticeofdiscovery:main:02' onClick=\"edit('"
					+ row.id + "',event)\" title='编辑 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:noticeofdiscovery:main:03' onClick=\"invalid('"
					+ row.id + "',event)\" title='删除 Delete '></i>&nbsp;&nbsp;";
		} 
		if( row.zt == 1 ){
			htmlContent += "<i class='iconnew-end color-blue cursor-pointer checkPermission' permissioncode='quality:noticeofdiscovery:main:04' onClick=\"endClose('"
					+ row.id + "',event)\" title='关闭 Close'></i>&nbsp;&nbsp;";
		}
		htmlContent += "</td>";
		htmlContent += "<td  class='text-left ' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('problemStatusEnum',row.zt))+ "'>"
		+ formatUndefine(DicAndEnumUtil.getEnumName('problemStatusEnum',row.zt))+ "</td>";
		htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.shwtdbh)+"' ><a href='#' onClick=\"view('"
				+ row.id + "',event)\">"+ StringUtil.escapeStr(formatUndefine(row.shwtdbh))+ "</a></td>";	
		htmlContent += "<td class='text-left' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverTypeEnum',row.lx)) + "' >"+ formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverTypeEnum',row.lx)) + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.shdxbh)+" "+StringUtil.escapeStr(row.shdxmc) + "' >"+ StringUtil.escapeStr(row.shdxbh)+" "+StringUtil.escapeStr(row.shdxmc) + "</td>";
		htmlContent += "<td class='text-left' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',row.shlb)) + "' >"+ formatUndefine(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',row.shlb)) + "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.sjShrq==null?"":(row.sjShrq).substring(0,10)) + "'>"+ StringUtil.escapeStr(row.sjShrq==null?"":(row.sjShrq).substring(0,10))+ "</td>";		
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jcnr)+"'>"+ StringUtil.escapeStr(row.jcnr) + "</td>";
		htmlContent += "<td class='text-left' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverProblemTypeEnum',row.wtlx))+ "'>" + formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverProblemTypeEnum',row.wtlx))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.zgjy)+ "'>" + StringUtil.escapeStr(row.zgjy)+ "</td>";
		htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.yqfkrq==null?"":(row.yqfkrq).substring(0,10))+ "'>" + StringUtil.escapeStr(row.yqfkrq==null?"":(row.yqfkrq).substring(0,10))+ "</td>";
		htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";		
		if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
			htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';		
		}
		htmlContent += "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.whr==null?"":row.whr.username)+" "+StringUtil.escapeStr(row.whr==null?"":row.whr.realname)+ "'>"+ StringUtil.escapeStr(row.whr==null?"":row.whr.username)+" "+StringUtil.escapeStr(row.whr==null?"":row.whr.realname)+ "</td>";
		htmlContent += "<td class='text-center'>"+ formatUndefine(row.whsj) + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.department.dprtname))+"' >"+ StringUtil.escapeStr(formatUndefine(row.department.dprtname))+ "</td>";
		htmlContent += "</tr>";
	});
	$("#auditdiscovery_main_table_list").empty();
	$("#auditdiscovery_main_table_list").html(htmlContent);
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
	TableUtil.resetTableSorting("auditdiscovery_main_table");
	goPage(1, "auto", "desc");
}


// 搜索条件重置
function searchreset() {
	var zzjgid = $('#zzjgid').val();
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#shdxbh_search").val("");
	$("#shdxid_search").val("");
	$("#shdxmc_search").val("");
	$("#shdx_search").val("");
	$("#lx_search").val("");
	$("#keyword_search").val("");
	$("#dprtcode_search").val(zzjgid);
	setDefaultDateRangePickerValue();
}

$("#dprtcode_search").change(function(){
	$("#shdxbh_search").val("");
	$("#shdxid_search").val("");
	$("#shdxmc_search").val("");
	$("#shdx_search").val("");
})


//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
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

/* 新增对话框 */
function openWinAdd(){
	auditDiscoveryAdd.show({
		data:{},//原值，在弹窗中默认勾选
		ope_type : 1,//新增
		dprtcode:userJgdm, //机构代码
		callback:function(data){
			doRequest(data,data.zt==0?"保存成功!":"提交成功!",basePath + "/quality/noticeofdiscovery/save","audit_discovery_alert_Modal");
		}
	});
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


function view(id,e){
	e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }
	window.open(basePath+"/quality/noticeofdiscovery/view?id="+id);
}


function showHiddenContent(this_){
 	var id = $(this_).attr("id");
 	var dprtcode = $(this_).attr("dprtcode");
 	if($(".bottom_hidden_content").css("display")=='block'){
 		$(".bottom_hidden_content").slideUp(100);
 	}
 	$(".bottom_hidden_content").slideDown(100);
 	new Sticky({tableId:'packing_list_table'}); //初始化表头浮动
 	
 	
	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#auditdiscovery_main_table_pagination .fenye"));
	}
 	$("#tabDiv").css("display","none");
	$("#tabDiv").css("display","block");
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $("#auditdiscovery_main_table_div"));
	}
	//工单明细
	problemTables.init({
		id:id,
		dprtcode:dprtcode,
	});
 	 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
 	new Sticky({tableId:'auditdiscovery_main_table'}); //初始化表头浮动
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
function getDataById(id,obj){
	var this_ = this;
	var param={};
	param.id=id;
	AjaxUtil.ajax({
		url : basePath + "/quality/noticeofdiscovery/getRecord",
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

function setDefaultDateRangePickerValue(){
	TableUtil.resetTableSorting("auditdiscovery_main_table");
	$("#sjShrq_search").daterangepicker().prev().on("click", function() {
		$(this).next().focus();
	});
	
	/*$(".cancelBtn").hide();//去掉时间控件取消按钮*/
	TimeUtil.getCurrentDate(function(date) {
		var startDate = date; 
		var endDate = addDays(addMonths(date, -6), 1);
		$("#sjShrq_search").data('daterangepicker').setStartDate(endDate);
		$("#sjShrq_search").data('daterangepicker').setEndDate(startDate);
		$("#sjShrq_search").val(endDate+" ~ "+startDate);
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

function openShdwWin(){
	var this_ = this;
	var dprtname = $("#shdxbh_search").val();
	var dprtcode = $("#shdxid_search").val();
	departmentModal.show({
		dprtnameList : dprtname,// 部门名称
		dprtcodeList : dprtcode,// 部门id
		type : false,// 勾选类型true为checkbox
		dprtcode : $("#zzjgid").val(),// 默认登录人当前机构代码
		callback : function(data) {// 回调函数
			$("#shdxid_search").val(data.dprtcode);
			$("#shdxbh_search").val(data.dprtbm);
			$("#shdxmc_search").val(data.dprtname);
			$("#shdx_search").val(data.dprtbm+" "+data.dprtname);
		}
	});
}

function changeShdx(){
	$("#shdxid_search").val("");
	$("#shdxbh_search").val("");
	$("#shdxmc_search").val("");
}

function edit(id,e){
	if(e != undefined){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	}	 
	getDataById(id,function(obj){
		if(!(obj.zt == 0)){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		auditDiscoveryAdd.show({
			data:obj,//原值，在弹窗中默认勾选
			ope_type : 2,//修改
			dprtcode:obj.dprtcode, //机构代码
			callback:function(data){
				doRequest(data,"修改成功!",basePath + "/quality/noticeofdiscovery/save","audit_discovery_alert_Modal");
			}
		});
	})
}

//删除
function invalid(id,e){
	e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }
    getDataById(id,function(obj){
		if(!(obj.zt == 0)){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		var param = {};
		param.id = id;
		AlertUtil.showConfirmMessage("确定要删除吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath + "/quality/noticeofdiscovery/delete",
					type : "post",
					async : false,
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify(param),
					dataType : "json",
					success : function(data) {
						pageParam=encodePageParam();
						AlertUtil.showMessage('删除成功！', decodePageParam());
						refreshPermission();
					}
				});
			}
		});
    });
}

function endClose(id,e){
	if(e != undefined){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	}
    getDataById(id,function(obj){
		if(!(obj.zt == 1)){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		var param = {};
		param.id = id;
		AlertUtil.showConfirmMessage("确定要关闭吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath + "/quality/noticeofdiscovery/close",
					type : "post",
					async : false,
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify(param),
					dataType : "json",
					success : function(data) {
						id=id;
						pageParam=encodePageParam();
						AlertUtil.showMessage('关闭成功！', decodePageParam());
						refreshPermission();
					}
				});
			}
		});
    });
}

function exportExcel(){
	var param = this.gatherSearchParam();
	param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
	window.open(basePath+"/quality/noticeofdiscovery/NoticeDiscovery.xls?paramjson="+JSON.stringify(param));
}