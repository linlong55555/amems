/**
 * 初始化
 */ 
$(document).ready(function(){
	Navigation(menuCode, '', '', 'SC12-1', '林龙', '2017-09-12', '林龙', '2017-09-12');//加载导航栏
	initFjjxOption();					//初始化机型
 	refreshPermission(); 				//加载按钮权限
	decodePageParam(); 					//初始化列表数据
});
/**
 * 初始化飞机机型
 */
function initFjjxOption(){
	$("#jx").html("<option value='' >显示全部All</option>");
	var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : $("#dprtcode").val()});
	if(typeList.length > 0){
		for(var i = 0; i < typeList.length; i++){
			$("#jx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
		}
	}else{
		$("#jx").html("<option value=''>显示全部All</option>");
	}
}
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());     //组织机构
	params.keyword = $.trim($("#keyword_search").val());//关键字
	
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
		$("#dprtcode").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
		
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}			
/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 */
function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}
/**
 * 查询条件
 */
function gatherSearchParam (){
	var searchParam={};
	 var paramsMap={};
	 paramsMap.userId = userId;
	 paramsMap.userType = userType;
	 searchParam.paramsMap=paramsMap;
	searchParam.fjjx=$.trim($("#jx").val()); 						//机型
	searchParam.dprtcode=$.trim($("#dprtcode").val());				//组织机构
	searchParam.keyword=$.trim($('#keyword_search').val());			//关键字
	return searchParam;
}
/**
 * 查询主单分页列表
 */
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	
	if (fjzch != "") {
		searchParam.fjzch = fjzch;
		fjzch = "";
	}
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/produce/monitorsetting/queryAllPageList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
 			if(data.total >0){
 				appendContentHtml(data.rows);
	 			new Pagination({
					renderTo : "scjkyj_pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});
	 			// 标记关键字
				signByKeyword($("#keyword_search").val(), [1,3]);
 			}else{
	 			$("#scjkyj_list").empty();
				$("#scjkyj_pagination").empty();
				$("#scjkyj_list").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
 			}
 			hideBottom();
 			new Sticky({tableId:'scjkyj_table'}); //初始化表头浮动
		}
	});
}
 	
/**
 * 加载数据
 * @param list
 */
function appendContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent +="<tr  fjzch='"+row.fjzch+"' onclick='showDcgzcl(this)'  >";
			htmlContent += "<td class='text-left ' >"+StringUtil.escapeStr(row.fjzch)+"</td>";  
		    htmlContent += "<td class='text-left ' >"+StringUtil.escapeStr(row.fjjx)+"</td>";  
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xlh)+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bzm)+"</td>";
	    	htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.base?row.base.dprtname:'')+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";
		    htmlContent += "</tr>";
		    $("#scjkyj_list").empty();
		    $("#scjkyj_list").html(htmlContent);
		    TableUtil.addTitle("#scjkyj_list tr td");
	});
}
     
/**
 * 点击行选择
 */
function showDcgzcl(this_){
    	 var fjzch = $(this_).attr("fjzch");
		 $("#fjzch").val(fjzch);
		 
	 	if($(".bottom_hidden_content").css("display")=='block'){
	 		$(".bottom_hidden_content").slideUp(100);
	 	}
	 	$(".bottom_hidden_content").slideDown(100);
	 	
	 	new Sticky({tableId:'scjkyj_table'}); //初始化表头浮动
	 	
	 	var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
	 	TableUtil.showDisplayContent();
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
		}
	 	
		if(!isBottomShown){
			TableUtil.makeTargetRowVisible($(this_), $("#scjkyj_table"));
		}
		bottom_hidden_content.setting();//加载下达指令数据
	 	 $(this_).addClass('bg_tr').siblings().removeClass('bg_tr');
}
     

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}

function searchFjgzRecord(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("scjkyj_table");
}
function onchangeFjgzRecord(){
	initFjjxOption();
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("scjkyj_table");
}
//重置
function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}