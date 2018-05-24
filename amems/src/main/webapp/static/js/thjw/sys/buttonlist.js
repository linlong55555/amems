
$(document).ready(function(){
	decodePageParam();
	Navigation(menuCode);
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
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
	var searchParam = gatherSearchParam();

	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/sys/button/queryButtonList",
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
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});
				// 标记关键字
				signByKeyword($("#keyword_search").val(),[2,3,4]);
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr class='text-center'><td colspan=\"6\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'button_table'});
		},
	}); 

}
//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());
	return searchParam;
}

function appendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		}

		htmlContent = htmlContent + "<td class='text-center'>"+ "<div><i class='icon-edit color-blue cursor-pointer' onClick=\"edit('"
		+ row.id + "')\" title='修改 Edit '></i>&nbsp;&nbsp;<i class='icon-remove  color-blue cursor-pointer' onClick=\"Delete('"
		+ row.id + "')\" title='删除 Delete'></i>"+"</td>"; 
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.buttonCode)+"</td>";  
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.buttonName)+"</td>";  
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.menuName)+"</td>";  
		htmlContent = htmlContent + "<td>"+formatUndefine(row.path)+"</td>";  
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.remark)+"</td>";  
		htmlContent = htmlContent + "</tr>";  

	});
	$("#list").empty();
	$("#list").html(htmlContent);

}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass().addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle == "sorting_asc") {
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

//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("fa-chevron-down");
		$("#icon").addClass("fa-chevron-up");
	} else {

		$("#divSearch").css("display", "none");
		$("#icon").removeClass("fa-chevron-up");
		$("#icon").addClass("fa-chevron-down");
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

//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#workOrderNum").is(":focus")){
			$("#homeSearchWorkOrder").click();      
		}
	}
};

//修改按钮
function edit(id){
	window.location =basePath+"/sys/button/" + id + "/initUpdateButton?pageParam=" + encodePageParam();
}
function add(){
	window.location = basePath+"/sys/button/intoAddButton?pageParam="+encodePageParam();
}
//删除按钮 
function Delete(id){
	if (id != null) {
		AjaxUtil.ajax({
			type : "post",
			url : basePath+"/sys/button/isRole_Button/"+$.trim(id)+"",
			dataType : "json",
			success : function(data) {
				if(data<1){
					AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
						AjaxUtil.ajax({
							type : "post",
							url : basePath+"/sys/button/deleteButton/"+$.trim(id)+"",
							dataType : "json",
							success : function(data) {
								finishWait();
								AlertUtil.showMessage('删除成功！');
								searchRevision();
							}
						});
					}});
				}else{
					AlertUtil.showErrorMessage('该按钮存在关联角色，不能删除！');
				}
			}
		});
	}
} 

//回车事件控制
document.onkeydown = function(event) {
	e = event ? event : (window.event ? window.event : null);
	if (e.keyCode == 13) {
		$('#buttonMainSearch').trigger('click');
	}
};