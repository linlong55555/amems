
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
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	decodePageParam();//开始的加载默认的内容 
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
	params.gljb = $.trim($("#gljb_search").val());
	params.sxkz = $.trim($("#sxkz_search").val());
	params.hclx = $.trim($("#hclx_search").val());
	params.whsj= $.trim($("#whrq_search").val());
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
		$("#gljb_search").val(params.gljb);
		$("#sxkz_search").val(params.keyword);
		$("#hclx_search").val(params.sxkz);
		$("#whrq_search").val(params.whsj);
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
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/aerialmaterial/cost/queryCostList",
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
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6,7,8]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"19\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'cost_list_table'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var gljb = $.trim($("#gljb_search").val());
		 var sxkz = $.trim($("#sxkz_search").val());
		 var hclx = $.trim($("#hclx_search").val());
		 var whsj= $.trim($("#whrq_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val()); 
		 paramsMap.keyword = keyword;
		 paramsMap.gljb = gljb;
		 paramsMap.sxkz = sxkz;
		 paramsMap.hclx = hclx;
		 paramsMap.dprtcode = dprtcode;
		 if(null != whsj && "" != whsj){
			paramsMap.dateBegin = whsj.substring(0,4)+"-"+whsj.substring(5,7)+"-"+whsj.substring(8,10);
			paramsMap.dateEnd = whsj.substring(12,17)+"-"+whsj.substring(18,20)+"-"+whsj.substring(21,23);
		}
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			      
			   htmlContent = htmlContent + "<td class='fixed-column text-center'>"+ "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:cost:main:01' onClick=\"edit('"
				+ row.id + "')\" title='修改 Edit'></i>"+"</td>";  
			   
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"' class='fixed-column text-left'>";
			   htmlContent = htmlContent + "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.bjh)+"</a>";
			   htmlContent = htmlContent + "</td>";
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.juescb)+"</td>"; 
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.jiescb)+"</td>"; 
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.gscb)+"</td>"; 
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"'  class='text-left'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>"; 
			   if('00000' == StringUtil.escapeStr(row.xh)){
				   htmlContent = htmlContent + "<td>通用Currency</td>";
			   }else{
				   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xh)+"' class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>";
			   }
			   
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb) +"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+DicAndEnumUtil.getEnumName('agingControlEnum',row.sxkz) +"</td>";
			   if(1 == row.isMel){
				   htmlContent = htmlContent + "<td class='text-center'>是</td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-center'>否</td>";
			   }
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.zjhms)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx) +"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw) +"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.cbwhsj)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();       //触发按钮权限监控
	 }
	 
//修改航材
function edit(id){
	window.location = basePath+"/aerialmaterial/cost/edit?id="+id+"&pageParam="+encodePageParam();
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/cost/view?id=" + id);
}
//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj,orderType);
}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	//信息检索
	function searchRevision(){
		goPage(1,"id","desc");
	}
		
	//搜索条件重置
	function searchreset(){
		var dprtId = $.trim($("#dprtId").val());
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
	
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});
		
		$("#dprtcode_search").val(dprtId);
		$("#keyword_search").val("");
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
		 
