var reviewData = [];
$(document).ready(
		function() {
			// 导航
			Navigation(menuCode, '', '', 'sc4_135', '岳彬彬', '2017-10-09', '岳彬彬',
					'2017-08-17');
			setDefaultDateRangePickerValue();
			refreshPermission();
			
			//执行待办
		    if(todo_ywid){
		    	if(todo_jd == 21 || todo_jd == 22){
		    		openEditWin(todo_ywid);
		    	}
		    	todo_ywid = null;
		    }
			
		});

// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	reviewData = [];
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
				url : basePath + "/quality/correctivemeasures/queryList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						reviewData = data.rows;
						appendContentHtml(data.rows, data.monitorsettings);
						var page_ = new Pagination({
							renderTo : "pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}

						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(), [ 5 ],"#table_list tr td");
					} else {
						$("#table_list").empty();
						$("#pagination").empty();
						$("#table_list").append("<tr class='text-center'><td colspan=\"17\">暂无数据 No data.</td></tr>");
					}
					hideBottom();
					new Sticky({
						tableId : 'review_table'
					});
				}
			});

}

// 将搜索条件封装
function gatherSearchParam() {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	var paramsMap={};
	paramsMap.wtlx = $.trim($("#wtlx_search").val());
	paramsMap.shlb=$.trim($("#shlb_search").val());
	paramsMap.lx=$.trim($("#lx_search").val());
	var ztList = [];
	var zt = document.getElementsByName("zt_radio");
	for (var i = 0; i < zt.length; i++) {
		if(zt[i].checked){
			ztList.push(zt[i].value);
		}
	}
	if(ztList.length>0){
		paramsMap.ztList = ztList;
	}else{
		paramsMap.ztList = null;
	}
	var sjShrq = $.trim($("#sjShrq_search").val());
	if (null != sjShrq && "" != sjShrq) {
		var sjShrqBegin = sjShrq.substring(0, 10) + " 00:00:00";
		var sjShrqEnd = sjShrq.substring(13, 23) + " 23:59:59";
		paramsMap.sjShrqBegin = sjShrqBegin;
		paramsMap.sjShrqEnd = sjShrqEnd;
	}
	if($("#issued_radio").attr("checked")=="checked"){
		paramsMap.isIssued = 0;
	}else{
		paramsMap.isIssued = 1;
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

function appendContentHtml(list,monitorsettings) {
	var htmlContent = '';
	$.each(list,function(index, row) {
		htmlContent += "<tr  id='"+row.id+"' dprtcode='"+row.dprtcode+"' zt='"+row.zt+"'  onclick=\"showHiddenContent(this)\">";
		htmlContent += "<td class='text-center fixed-column'>";		
		htmlContent += "<input type='checkbox' index='"+index+"' dprtcode='"+row.dprtcode+"' checkid='"+row.id+"' name='checkrow' />";
		htmlContent += "</td>";
		htmlContent += "<td class='text-center fixed-column'>";	
		if(row.zt == 1 || row.zt == 2 || row.zt ==6 ){
			htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:correctivemeasures:main:01' onClick=\"edit('"
					+ row.id + "',event)\" title='编辑 Edit'></i>&nbsp;&nbsp";
		}
		if((row.zt == 1 || row.zt == 2 || row.zt ==6) && userId == row.wtfkrid){
			htmlContent += "<i class='fa fa-hand-o-up color-blue cursor-pointer checkPermission' permissioncode='quality:correctivemeasures:main:02' onClick=\"assign('"
				+ row.id + "',event)\" title='指派 Assign'></i>&nbsp;&nbsp";
		}
		htmlContent += "</td>";
		htmlContent +=  getWarningColor("#fefefe", row.paramsMap.syts,row.zt, monitorsettings.yjtsJb1,monitorsettings.yjtsJb2) ;
		htmlContent += "<td  class='text-left ' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('problemStatusEnum',row.zt))+ "'>"
		+ formatUndefine(DicAndEnumUtil.getEnumName('problemStatusEnum',row.zt))+ "</td>";
		htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.shwtbh)+"' ><a href='#' onClick=\"view('"
				+ row.id + "',event)\">"+ StringUtil.escapeStr(formatUndefine(row.shwtbh))+ "</a></td>";	
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverTypeEnum',row.auditDiscovery.lx))) + "' >"+ (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverTypeEnum',row.auditDiscovery.lx))) + "</td>";
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.shdxbh)+" "+StringUtil.escapeStr(row.auditDiscovery.shdxmc)) + "' >"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.shdxbh)+" "+StringUtil.escapeStr(row.auditDiscovery.shdxmc)) + "</td>";
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',row.auditDiscovery.shlb))) + "' >"+ (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',row.auditDiscovery.shlb))) + "</td>";
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.sjShrq==null?"":(row.auditDiscovery.sjShrq).substring(0,10))) + "'>"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.sjShrq==null?"":(row.auditDiscovery.sjShrq).substring(0,10)))+ "</td>";		
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.jcnr))+"'>"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.jcnr)) + "</td>";
		htmlContent += "<td class='text-center' title='"+ (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverProblemTypeEnum',row.auditDiscovery.wtlx)))+ "'>" + (row.auditDiscovery==null?"":formatUndefine(DicAndEnumUtil.getEnumName('auditDiscoverProblemTypeEnum',row.auditDiscovery.wtlx)))+ "</td>";
		htmlContent += "<td class='text-left' title='"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.zgjy))+ "'>" + (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.zgjy))+ "</td>";
		htmlContent += "<td class='text-center' title='"+ (row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.yqfkrq==null?"":(row.auditDiscovery.yqfkrq).substring(0,10)))+ "'>" +( row.auditDiscovery==null?"":StringUtil.escapeStr(row.auditDiscovery.yqfkrq==null?"":(row.auditDiscovery.yqfkrq).substring(0,10)))+ "</td>";
		htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";		
		if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
			htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';		
		}
		htmlContent += "</td>";
		htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.detailWhr==null?"":row.detailWhr.username)+" "+StringUtil.escapeStr(row.detailWhr==null?"":row.detailWhr.realname)+ "'>"+ StringUtil.escapeStr(row.detailWhr==null?"":row.detailWhr.username)+" "+StringUtil.escapeStr(row.detailWhr==null?"":row.detailWhr.realname)+ "</td>";
		htmlContent += "<td class='text-center'>"+ formatUndefine(row.whsj) + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.department.dprtname))+"' >"+ StringUtil.escapeStr(formatUndefine(row.department.dprtname))+ "</td>";
		htmlContent += "</tr>";
	});
	$("#table_list").empty();
	$("#table_list").html(htmlContent);
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
	TableUtil.resetTableSorting("review_table");
	goPage(1, "auto", "desc");
}


// 搜索条件重置
function searchreset() {
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#shdxbh_search").val("");
	$("#shdxid_search").val("");
	$("#shdxmc_search").val("");
	$("#shdx_search").val("");
	$("#lx_search").val("");
	$("#keyword_search").val("");
	$("#dprtcode_search").val(userJgdm);
	$("input[name='issued_radio']").attr("checked",false); 
	$("input[name='type_radio']").attr("checked",false); 
	$("input[name='zt_radio']").attr("checked",false); 
	$("#issued_radio").attr("checked",true); 
	$("#ztYtx").attr("checked",true); 
	
	
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
	window.open(basePath+"/quality/correctivemeasures/view?id="+id);
}


function showHiddenContent(this_){
 	var id = $(this_).attr("id");
 	var dprtcode = $(this_).attr("dprtcode");
 	if($(".bottom_hidden_content").css("display")=='block'){
 		$(".bottom_hidden_content").slideUp(100);
 	}
 	$(".bottom_hidden_content").slideDown(100);
 	new Sticky({tableId:'review_table'}); //初始化表头浮动
 	
 	
	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#pagination .fenye"));
	}
 	$("#tabDiv").css("display","none");
	$("#tabDiv").css("display","block");
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $("#review_table_div"));
	}

	measures_record.show({
		id:id,
		dprtcode:dprtcode,
		modal:null,
	});
 	 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
 	new Sticky({tableId:'review_table'}); //初始化表头浮动
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
		url : basePath + "/quality/correctivemeasures/getRecord",
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
	TableUtil.resetTableSorting("review_table");
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

function edit(id,e){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    openEditWin(id);
}

function openEditWin(id){
	getDataById(id,function(obj){
		if(!(obj.zt == 1 | obj.zt == 2 || obj.zt == 6)){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		reviewReformMeasures.show({
			data:obj,//原值，在弹窗中默认勾选
			callback:function(data){
				var tip = data.paramsMap.editType =="1"?"保存成功！":data.paramsMap.editType == "2" ?"提交成功!":data.paramsMap.editType == "3" ?"指派成功!":"";
				doRequest(data,tip,basePath + "/quality/correctivemeasures/save","review_reform_measures_alert_Modal");
			}
		});
	})
}


function assign(id,e){
	e = e || window.event;  
	if(e.stopPropagation) { //W3C阻止冒泡方法  
		e.stopPropagation();  
	} else {  
		e.cancelBubble = true; //IE阻止冒泡方法  
	}
	getDataById(id,function(obj){
		if(!(obj.zt == 1 || obj.zt == 2 || obj.zt == 6)){
			AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
			return;
		}
		if(obj.wtfkrid != userId){
			AlertUtil.showErrorMessage("只有问题反馈人才能指派!");
			return;
		}
		chooseUser(obj.dprtcode,function(data){
			var param = {};
			param.id = id;
			param.zxrbmid = data.bmdm;
			param.zxrid = data.id;
			var paramsMap = {};
			paramsMap.username = data.realname+" "+data.username;
			paramsMap.editType="3";
			param.paramsMap=paramsMap;
			var tip = "指派成功!";
			doRequestNoModal(param,tip,basePath + "/quality/correctivemeasures/save");
		});
	})
}

//选择校验人
function chooseUser(dprtcode,obj){
	var bmdm = "";
	Personel_Tree_Multi_Modal.show({
		checkUserList:[{id:"",displayName:""}],//原值，在弹窗中回显
		dprtcode:dprtcode,
		clearUser:false,
		multi:false,
		callback: function(data){//回调函数
			var user = $.isArray(data)?data[0]:{id:'',displayName:''};
			if(user != null){
				if(typeof(obj)=="function"){
					obj(user); 
				}
			}else{
				AlertUtil.showMessage("请选择执行人!");
			}	
		}
	});
	AlertUtil.hideModalFooterMessage();
}

//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}

//批量反馈
function batchApproveWin(){
	var idArr = [];
	var approvalArr = [];
	var zt = [1,2,6];
	var len = 0;
	$("#table_list").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var data = reviewData[index];
		if($.inArray(data.zt, zt) != -1  && data.wtfkrid == userId){
			idArr.push(data.id);
		}else{
			len ++;
		}
	});
	if(len > 0){
		AlertUtil.showMessage("存在无反馈权限的问题单！请重新选择！");
		return ;
	}
	if(idArr.length == 0){
		AlertUtil.showMessage("请选中数据后再进行操作！");
	}else{
		var param = {};
		var paramsMap = {};
		paramsMap.idList = idArr;
		param.paramsMap = paramsMap;
		doRequestNoModal(param,"反馈成功!",basePath + "/quality/correctivemeasures/feedback");
	}
}

function doRequestNoModal(data,message,url){
	startWait();
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
			finishWait();
			refreshPermission();
		}
	});
}

//获取预警颜色
function getWarningColor(bgcolor, syts, zt, yjtsJb1, yjtsJb2) {
	if (zt !=0 && zt != 1 &&zt != 2 && zt !=6) {
		return "<td  class='text-center fixed-column'></td>";
	}
	if (yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色		 
		return "<td  class='text-center fixed-column' title='"+yjtsJb1+"&lt;剩余反馈期限&lt;="+yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	if (Number(syts) <= yjtsJb1) {
		bgcolor = warningColor.level1;// 红色
		return "<td  class='text-center fixed-column' title='剩余反馈期限&lt;="+yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' ></i>" + "</td>";
	}
	return "<td  class='text-center fixed-column'></td>";
}

function exportExcel(){
	var param = this.gatherSearchParam();
	param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
	window.open(basePath+"/quality/rectifymeasuresfollow/export/2/CorrectiveMeasures.xls?paramjson="+JSON.stringify(param));
}