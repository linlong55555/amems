
var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
$(document).ready(function(){
	Navigation(menuCode);
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				search();//调用主列表页查询
			}
		}
	});
	
	refreshPermission();
	initStoreSelect();
	
	if(tabId == 'main'){
		$("#tab_main").tab('show');
		loadMain();
	}else if(tabId == 'history'){
		$("#tab_history").tab('show');
		loadHistory();
	}else{
		id = "";
		pageParam = "";
		loadMain();// 加载采购数据
	}
});

var flagMain = false;
function loadMain(){
	if(flagMain === false){
		decodePageParam();
		flagMain = true;
	}
}

var pagination={};
function encodePageParam(){
	var pageParam = {};
	var params = {};
	
	params.keyword = $.trim($("#keyword_search").val());
	params.hclx = $("#hclx").val();
	params.ckid = $("#ckh").val();
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
};

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
		$("#hclx").val(params.hclx);
		$("#ckh").val(params.ckid);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
function initStoreSelect(){
	var cklbs=[0, 4, 6, 8];//航材/低值易耗品
	var html = "<option value=\"\">显示全部All</option>";
	var dprtcode = userJgdm;
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckh").html(html);
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var paramsMap={};
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};;
	obj.pagination = pagination;
	obj.keyword = $.trim($("#keyword_search").val());
	obj.dprtcode = userJgdm;
	if($("#hclx").val() == ''){
		paramsMap.hclxs = [0, 4, 5, 6, 1];
	}else{
		obj.hclx = $("#hclx").val();
	}
	obj.ckid = $("#ckh").val();
	obj.paramsMap = paramsMap;
	startWait();
	
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/stock/requisitionStockList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			if(data.rows && data.rows.length > 0){
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});	   
					// 标记关键字
				signByKeyword($("#keyword_search").val(),[3,4,5,6,7,10,11],"#lingliao td");
					   
			} else {
				
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr><td class='text-center' colspan=\"18\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'lingliao'});
			finishWait();
	    }
	}); 
}

/**
 * 拼接表格内容
 * @param list
 * @returns
 */
function appendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		if(parseInt(row.syts)<=parseInt($("#fazhi").val())){
			htmlContent += "<tr  style=\"cursor:pointer\"   ;  bgcolor=\"#ccc\">";
	   	}else{
	   		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
	   	}
		
		htmlContent += "<td class='fixed-column' class='text-center'><div>";
		htmlContent += "<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:requisition:main:01' onClick=\"getMaterial('"+ row.id + "','"+ (index+1) + "', this);\" title='领用 Requisition'></i>&nbsp;&nbsp;";
		htmlContent += "</div></td>";
		
		htmlContent += "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		htmlContent += "<td class='fixed-column' title='"+StringUtil.escapeStr(row.bjh)+"' align='left'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
		htmlContent += "<td class='fixed-column' title='"+StringUtil.escapeStr(row.grn)+"' align='left'>"+StringUtil.escapeStr(row.grn)+"</td>";  
		htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' align='left'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' align='left'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' align='left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; '>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; '>"+formatUndefine(gljb[row.gljb])+"</td>"; 
		
		htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' align='left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
		htmlContent += "<td title='"+StringUtil.escapeStr(row.pch)+"' align='left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
		
		
		
		htmlContent += "<td title='"+StringUtil.escapeStr(row.shzh)+"' align='left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.kcsl)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; '>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.syts)+"</td>";  
		htmlContent += "</tr>";  
	});
	$("#list").empty();
	$("#list").html(htmlContent);
	refreshPermission();
}

	 
//字段排序
function orderBy(obj, _obj) {
	$obj = $("#lingliao th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#lingliao .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#lingliao .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#pagination li[class='active']").text();
	if(currentPage == ""){
		currentPage = 1;
	}
	goPage(currentPage,obj, orderType);
}
	
	
	
//信息检索
function search(){
	goPage(1,"auto","desc");
}
	
		
//搜索条件重置
function searchreset(){
	
	$("#keyword_search").val("");
	$("#hclx").val("");
	$("#ckh").val("");
//	$("#dprtcode").val(userJgdm);
//	$("#dprtcode").trigger("change");
}
 
//搜索条件显示与隐藏 
function more_params() {
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

/**
 * 领用航材
 * @returns
 */
function getMaterial(id, index, obj){
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/requisition/"+id,
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		success:function(data){
			$("#requisiton_message").html("序号【"+index+"】航材领用成功，请在领料申请中查看").fadeIn().fadeOut(3000);
	    }
	}); 
}

/**
 * 跳转到领料申请单时验证用户是否存在领料申请单
 * @returns
 */
function goEdit(){
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/allowEdit",
		type: "get",
		cache:false,
		dataType:"json",
		success:function(data){
			if(data === false){
				AlertUtil.showMessage("请先领用航材!");
			}else{
				window.location = basePath+"/aerialmaterial/requisition/edit?pageParam="+encodePageParam();
			}
	    }
	}); 
}

		
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",
	function() {
		$(this).next().focus();
	}
);
$('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',
	includeSelectAllOption : true
}); 
	
	
