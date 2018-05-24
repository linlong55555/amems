var pagination2 = {};
$(document).ready(function(){
	decodePageParam2();
});
	/**
	 * 编码页面查询条件和分页
	 * @returns
	 */
	function encodePageParam2(){
		var pageParam = {};
		var params = {};
		params.dprtId = $.trim($("#dprtcode_search2").val());
		params.keyword = $.trim($("#keyword_search2").val());
		
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}

	/**
	 * 解码页面查询条件和分页 并加载数据
	 * @returns
	 */
	function decodePageParam2(){
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#dprtcode_search2").val(params.dprtId);
			$("#keyword_search2").val(params.keyword);
			
			if(pageParamJson.pagination){
				(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage2(1,"auto","desc");
			}
		}catch(e){
			goPage2(1,"auto","desc");
		}
	}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
		 
		var searchParam = gatherSearchParam2();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/sys/role/querywarehouseRoleList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml2(data.rows);
				   
				   	 new Pagination({
							renderTo : "pagination2",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								AjaxGetDatasWithSearch2(a,b,c);
							}
						}); 
				   
				// 标记关键字
				   signByKeyword($("#keyword_search2").val(),[2,3]);
			   } else {
				  $("#list2").empty();
				  $("#pagination2").empty();
				  $("#list2").append("<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam2(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search2").val());
		 searchParam.dprtId = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 function appendContentHtml2(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
			   }
			      
			   htmlContent += "<td class='text-center'>"+ "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:10' onClick=\"edit2('"
				+ row.id + "')\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='icon-book color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:11' onClick=\"AssignStore('"
				+ row.id + "')\" title='分配仓库  Distribution Warehouses'></i>&nbsp;&nbsp;<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:12' onClick=\"Delete2('"
				+ row.id + "')\" title='删除 Delete'></i>"+"</td>"; 
			   htmlContent += "<td class='text-left'>"+formatUndefine  (row.roleCode)+"</td>";  
			   htmlContent += "<td>"+StringUtil.escapeStr(row.roleName)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.roleRemark)+"'>"+StringUtil.escapeStr(row.roleRemark)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+formatUndefine  (row.dprtId)+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list2").empty();
		   $("#list2").html(htmlContent);
		   refreshPermission();
	 }
	 
	 //分配组织机构
	 function Delete2(id){
		 AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
				startWait();//开始Loading
				AjaxUtil.ajax({
						type : "post",
						url : basePath+"/sys/role/removeWarehouseRole/"+$.trim(id)+"",
						dataType : "json",
						success : function(data) {
							finishWait();//结束Loading
							if (data.state=="userToRole"){
								AlertUtil.showErrorMessage("该角色已分配用户，不可删除！");
							}else if (data.state=="Success"){
								finishWait();
								AlertUtil.showMessage('删除成功!');
								searchRevision2();
								refreshPermission();
							}
						}
					});
			}});
	 }
	 
	 //修改库房角色
	 function edit2(id){
		 window.location =basePath+"/sys/role/initModifyWarehouseRole/"+$.trim(id)+"";
	 }
	 
	//分配仓库
	function AssignStore(id){
		roleId = id;
		AjaxUtil.ajax({
			url:basePath+"/sys/role/findAllByRoleIdtive",
			type:"post",
			data:{id:id,dprtcode:$.trim($("#dprtcode_store_search").val())},
			dataType:"json",
			success:function(data){
				initStore2(data);
			}
		});
		$("#alertModalStore").modal("show");
		/*new Sticky({tableId:'open_win_store_table'});*/
	}
	
	function searchStore(){
		AssignStore(roleId);
	}
	
	function clickRow2(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','storeList');
	}
	
	function initStore2(dataList){
		oldStoreIds = '';
		var htmlContent = "";
		$.each(dataList,function(index,data){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" id='tr_"+data.id+"' onclick=clickRow(this)>";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr_"+data.id+"' onclick=clickRow(this)>";
			}
			if(data.selectId != null && data.selectId != ''){
				oldStoreIds += data.selectId + ',';
				htmlContent += "<td class='text-center'><input type='checkbox' checked value='"+data.id+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','storeList')\" /></td>"; 
			}else{
				htmlContent += "<td class='text-center'><input type='checkbox' value='"+data.id+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','storeList')\" /></td>"; 
			}
			htmlContent += "<td class='text-left'>"+formatUndefine(data.ckh)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(data.ckmc)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(data.dprtcode))+"</td>";
			htmlContent += "</tr>"; 
		});
		oldStoreIds = getstr(oldStoreIds);
		$("#storeList").empty();
		if(htmlContent == ''){
			htmlContent = "<tr><td colspan=\"4\" class='text-center'>暂无数据 No data.</td></tr>";
		}
		$("#storeList").html(htmlContent);
	}
	
	function saveRoleToStore(selectNode){
		
		var newStoreIds = getSelectValue(selectNode);//获取选中的卡
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/sys/role/saveRoleToStore",
			type:"post",
			data:{roleId:roleId,oldStoreIds:$.trim(oldStoreIds),newStoreIds:newStoreIds},
			dataType:"json",
			success:function(data){
				finishWait();
				searchRevision2();
				goPage2(1,"auto","desc");//开始的加载默认的内容 
			}
		});
	}
	  
	//字段排序
	function orderBy2(obj) {
		var orderStyle = $("#" + obj + "_order2").attr("class");
		$("th[id$=_order2]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		$("#" + obj + "_" + "order2").removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			$("#" + obj + "_" + "order2").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order2").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order2").attr("class");
		var currentPage = $("#pagination2 li[class='active']").text();
		goPage2(currentPage,obj,orderStyle.split("_")[1]);
	}
	
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage2(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
	}	
	
	//信息检索
	function searchRevision2(){
		goPage2(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset2(){
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
		$(this).val("");
		});
	}
 
	//搜索条件显示与隐藏 
	function search2() {
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
		
