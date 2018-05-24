$(document).ready(function(){
	decodePageParam1();
});
var pagination1 = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam1(){
	var pageParam = {};
	var params = {};
	params.dprtId = $.trim($("#dprtcode_search1").val());
	params.keyword = $.trim($("#keyword_search1").val());
	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam1(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#dprtcode_search1").val(params.dprtId);
		$("#keyword_search1").val(params.keyword);
		
		if(pageParamJson.pagination){
			(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage1(1,"auto","desc");
		}
	}catch(e){
		goPage1(1,"auto","desc");
	}
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch1(pageNumber,sortType,sequence){
		 
		var searchParam = gatherSearchParam1();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/sys/role/queryModelRoleList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml1(data.rows);
				   
				   	 new Pagination({
							renderTo : "pagination1",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								AjaxGetDatasWithSearch1(a,b,c);
							}
						}); 
				   
				// 标记关键字
				   signByKeyword($("#keyword_search1").val(),[2,3]);
			   } else {
				  $("#list1").empty();
				  $("#pagination1").empty();
				  $("#list1").append("<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }

	 //将搜索条件封装 
	 function gatherSearchParam1(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search1").val());
		 searchParam.dprtId = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 function appendContentHtml1(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
			   }
			      
			   htmlContent = htmlContent + "<td class='text-center'>"+ "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:07' onClick=\"edit1('"
				+ row.id + "')\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:08' onClick=\"Delete1('"
				+ row.id + "')\" title='删除 Delete'></i>"+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.roleCode)+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.roleName)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.roleRemark)+"'>"+StringUtil.escapeStr(row.roleRemark)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.dprtId)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list1").empty();
		   $("#list1").html(htmlContent);
		   refreshPermission();
	 }
	 
	 //修改角色
	 function edit1(id){
		 window.location =basePath+"/sys/role/initModifyModelRole/"+$.trim(id)+"";
	 }
	 
	 //分配组织机构
	 function Delete1(id){
		 AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
				startWait();//开始Loading
					AjaxUtil.ajax({
								type : "post",
								url : basePath+"/sys/role/removeModelRole/"+$.trim(id)+"",
								dataType : "json",
								success : function(data) {
									finishWait();//结束Loading
									if (data.state=="userToRole"){
										AlertUtil.showErrorMessage("该角色已分配用户，不可删除！");
									}else if (data.state=="Success"){
										finishWait();
										AlertUtil.showMessage('删除成功!');
										searchRevision1();
										refreshPermission();
									}
								}
							});
			}});
	 }
	 
	function clickRow1(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','storeList');
	}
	  
	//字段排序
	function orderBy1(obj) {
		var orderStyle = $("#" + obj + "_order1").attr("class");
		$("th[id$=_order1]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		$("#" + obj + "_" + "order1").removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			$("#" + obj + "_" + "order1").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order1").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order1").attr("class");
		var currentPage = $("#pagination1 li[class='active']").text();
		goPage1(currentPage,obj,orderStyle.split("_")[1]);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage1(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch1(pageNumber,sortType,sequence);
	}	
	//信息检索
	function searchRevision1(){
		goPage1(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset1(){
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
}
 
	//搜索条件显示与隐藏 
	function search1() {
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
		
