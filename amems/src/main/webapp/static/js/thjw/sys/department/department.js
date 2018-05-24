var type=[,"组织机构","部门机构"];
$(document).ready(function(){
	     var confgvalue="";
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 //菜单设置  自己设置
		 Navigation(menuCode);       //菜单选中高亮显示
		 $("#revisionManagementPage").addClass("active");
		 $("#DepartmentPage").addClass("active");
		 refreshPermission();
});

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
		
		 var searchParam = gatherSearchParam();
		 searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		startWait();
	 
		AjaxUtil.ajax({
			   url:basePath+"/sys/department/queryDepartmentList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
			    finishWait();
				   if(data.total >0){
					   confgvalue=data.configValue;
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
					   signByKeyword($.trim($("#keyword_search").val()),[2,3],"#list tr td");
				   } else {
					  $("#list").empty();
					  $("#pagination").empty();
					  $("#list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				   }
		      },
		      error:function(){
				     finishWait();
		    	     AlertUtil.showErrorMessage("system error.");
		      }
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
			      
			   htmlContent = htmlContent + "<td class='text-center'>";
			   htmlContent = htmlContent + "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:department:main:02' onClick=edit('"+ row.id + "') title='修改 Edit'></i>&nbsp;&nbsp;";
			   
			   if($("#dprtcode").val()!=StringUtil.escapeStr(row.dprtcode)){
				   htmlContent = htmlContent + "<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='sys:department:main:03' onClick=deleteDepartment('"+ row.id +"','" + row.dprttype + "') title='删除 Delete'></i>";
			   }
			 
			   htmlContent = htmlContent + "</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+ StringUtil.escapeStr(row.dprtcode) +"'>"+StringUtil.escapeStr(row.dprtcode)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+ StringUtil.escapeStr(row.dprtname) +"' >"+StringUtil.escapeStr(row.dprtname)+"</td>";  
			
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.deptInfo?row.deptInfo.zczh_max:'')+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.deptInfo?row.deptInfo.zcfj_max:'')+"</td>";
			   var yxqks='';
			   var yxqjs='';
			   if(formatUndefine(row.deptInfo?row.deptInfo.yxqks:'')!=""){
				   yxqks=(row.deptInfo?row.deptInfo.yxqks:'').substring(0,10);
			   }
			   if(formatUndefine(row.deptInfo?row.deptInfo.yxqjs:'')!=""){
				   yxqjs=(row.deptInfo?row.deptInfo.yxqjs:'').substring(0,10);
			   }
			   htmlContent = htmlContent + "<td class='text-center' title='"+yxqks+"'>"+formatUndefine(yxqks)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center' title='"+yxqjs+"' >"+formatUndefine(yxqjs)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.remark)+"'>"+StringUtil.escapeStr(row.remark)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	//作废
	 function deleteDepartment(id,dprttype) {
	 	
	 	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
	 		
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/sys/department/deleteDepartment",
	 			type:"post",
	 			async: false,
	 			data:{
	 				'id' : id,
	 				'dprttype':dprttype
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('删除成功!');
	 				searchRevision();
	 				refreshPermission();
	 			}
	 		});
	 		
	 	}});
	 	
	 }
	 
	 
	 //查看revision对应的工卡列表
	 function goToLinkPage(obj,rid){
		 obj.stopPropagation(); //屏蔽父元素的click事件
		 window.location =basePath+"/main/work/listpage?rid="+rid;
	 }
	 
	//字段排序
		function orderBy(obj) {
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(
					function() { // 重置class
						$(this).removeClass("sorting_desc").removeClass("sorting_asc")
								.addClass("sorting");
					});
			$("#" + obj + "_" + "order").removeClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
				orderType = "asc";
			} else {
				orderType = "desc";
				$("#" + obj + "_" + "order").addClass("sorting_asc");
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			goPage(currentPage,obj, orderStyle.split("_")[1]);
		}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
		 //信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
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
		 
		//新增组织机构页面
		function add(){
			window.location = basePath+"/sys/department/intoAddDepartment/";
		}
		
		//修改组织机构
		 function edit(id){
			 if(id==confgvalue){
				 AlertUtil.showErrorMessage("顶级组织机构不能修改！");
			 }else{
				 window.location =basePath+"/sys/department/intoModifyDepartment/"+$.trim(id)+"";
			 }
		 }

		 
		//回车事件控制
		 document.onkeydown = function(event) {
		 	e = event ? event : (window.event ? window.event : null);
		 	if (e.keyCode == 13) {
		 		$('#departmentMainSearch').trigger('click');
		 	}
		 };