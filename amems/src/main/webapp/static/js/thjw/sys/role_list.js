var oldStoreIds = '';
var roleId = '';
var pagination = {};

$(document).ready(function(){
	Navigation(menuCode);
	decodePageParam();
	refreshPermission();
	initNav();
});

/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	    if(href=="#FunctionalRoleList"){
	    	decodePageParam();
	    }else if(href=="#ModelRoleList"){
	    	gatherSearchParam1();
	    }else{
	    	gatherSearchParam2();
	    }
	}); 
}

	/**
	 * 编码页面查询条件和分页
	 * @returns
	 */
	function encodePageParam(){
		var pageParam = {};
		var params = {};
		params.dprtId = $.trim($("#dprtcode_search").val());
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
			$("#dprtcode_search").val(params.dprtId);
			$("#keyword_search").val(params.keyword);
			
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
				refreshPermission();
			}else{
				goPage(1,"auto","desc");
				refreshPermission();
			}
		}catch(e){
			goPage(1,"auto","desc");
			refreshPermission();
		}
	}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/role/queryRoleList",
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
				   signByKeyword($("#keyword_search").val(),[2,3]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }
	 
	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 goPage1(1,"auto","desc");//开始的加载默认的内容 
		 goPage2(1,"auto","desc");//开始的加载默认的内容 
	}
		
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.dprtId = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
			   }
			      
			   htmlContent += "<td class='text-center'>"
				   		   +  "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:02' onClick=\"edit('"
						   +  row.id + "')\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='icon-list color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:03' onClick=\"Assign('"
						   +  row.id + "')\" title='分配菜单/按钮 Distribution Menu/Button '></i>&nbsp;&nbsp;<i class='icon-user color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:04' onClick=\"AssignWork('"
						   +  row.id + "')\" title='分配组织机构 Distribution Organization'></i>&nbsp;&nbsp;<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='sys:role:main:05' onClick=\"Delete('"
						   +  row.id + "')\" title='删除 Delete'></i>"+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.roleCode)+"</td>";  
			   htmlContent += "<td>"+StringUtil.escapeStr(row.roleName)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.roleRemark)+"'>"+StringUtil.escapeStr(row.roleRemark)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.dprtId)+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	 //修改角色
	 function edit(id){
		 window.location =basePath+"/sys/role/initModifyRole/"+$.trim(id)+"";
	 }
	 
	 //分配菜单按钮
	 function Assign(id){
		 window.location =basePath+"/sys/role/intoRoleMenu/"+$.trim(id)+"?pageParam="+encodePageParam();
	 }
	 
	 //分配组织机构
	 function AssignWork(id){
		 $("#id").val(id);
		 var menus =''; 
		 AjaxUtil.ajax({
			   url:basePath+"/sys/role/queryRoleDprtTree?roleId="+ id, 
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:"",
			   success:function(data){
				   var setting = {view: {showIcon: false},check: {enable: true,
						chkStyle: "checkbox",
						chkboxType: { "Y": "", "N": "" }},data: {simpleData: {enable: true}}};
				   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
				   treeObj.expandAll(true); 

				   var nodes = treeObj.getCheckedNodes(true);
				   for (var i = 0; i < nodes.length; i++) {
						menus+=nodes[i].id+',';
					}
					if(menus != '')
					menus.substring(0,menus.length-1);
					$("#dprts").val(menus);
		      },
		    }); 
		 $("#alertModalDprt").modal("show");
	 }
	 
	//分配组织机构
	 function save(){
	 	  var list = '';  
	 	   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	 	   var nodes = treeObj.getCheckedNodes(true);
	 	   for (var i = 0; i < nodes.length; i++) {
	 		   list+=nodes[i].id+',';
	 		}
	 		if(list != '')
	 		list.substring(0,list.length-1);
	 		startWait($("#alertModalDprt"));//开始Loading
	 		// 提交数据
	 		AjaxUtil.ajax({
	 			type : 'post',
	 			cache : false,
	 			async: false,
	 			traditional: true,  
	 			url : basePath+"/sys/role/addRoleDprt",
	 			data : {
	 				'nodes' : list,// 跟新前的组织
	 				'dprts' : $('#dprts').val(),// 跟新后组织
	 				'roleId' : $('#id').val()//角色id
	 			},
	 			dataType : 'json',
	 			modal:$("#alertModalDprt"),
	 			success : function(data) {
	 				
	 				if (data.state == "Success") {
	 					finishWait($("#alertModalDprt"));//结束Loading
	 					goPage(1,"auto","desc");//开始的加载默认的内容 
	 					refreshPermission();
	 				} else {
	 					finishWait($("#alertModalDprt"));//结束Loading
	 					AlertUtil.showErrorMessage(data.state);
	 				}
	 			}
	 		});
	 }
	 
	
	function clickRow(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','storeList');
	}
	
	 //分配组织机构
	 function Delete(id){
		 AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
				startWait();//开始Loading
				AjaxUtil.ajax({
						type : "post",
						url : basePath+"/sys/role/removeRole/"+$.trim(id)+"",
						dataType : "json",
						success : function(data) {
							finishWait();//结束Loading 
						/*	if(data.state=="menu"){
								AlertUtil.showErrorMessage("该角色已分配菜单，不可删除！");
							}else if (data.state=="btn"){
								AlertUtil.showErrorMessage("该角色已分配按钮，不可删除！");
							}else */if (data.state=="userToRole"){
								AlertUtil.showErrorMessage("该角色已分配用户，不可删除！");
							}else if (data.state=="Success"){
								finishWait();
								AlertUtil.showMessage('删除成功!');
								searchRevision();
								refreshPermission();
							}
						}
					});
			}});
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
		
	//搜索条件重置
	function searchreset(){
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
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
	
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
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
			
			var selectTab = $("#djTab").find("li.active").index();
			
			if($("#workOrderNum").is(":focus")){
				$("#homeSearchWorkOrder").click();      
			}else if(selectTab == 0){
				$('#roleMainFunctionalSearch').trigger('click');
			}else if(selectTab == 1){
				$('#roleMainModelSearch').trigger('click');
			}else if(selectTab == 2){
				$('#roleMainWarehouseSearch').trigger('click');
			}
		}
	};
		 
