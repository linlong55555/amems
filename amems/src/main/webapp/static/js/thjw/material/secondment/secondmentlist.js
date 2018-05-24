$(document).ready(function(){
	
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
	 Navigation(menuCode);
	 //菜单设置  自己设置
	 refreshPermission();       //触发按钮权限监控
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
	params.jddxlx = $.trim($("#jddxlx_search").val());
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
		$("#jddxlx_search").val(params.jddxlx);
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
		   url:basePath+"/aerialmaterial/secondment/querySecondmentList",
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
						sortColumn : sequence,
						orderType : sortType,
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,c,b);
						}
					});	

				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[3]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
			   };
			   new Sticky({tableId:'secondment_list_table'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.jddxlx = $.trim($("#jddxlx_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
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
			      
			   htmlContent = htmlContent + "<td class='fixed-column text-center'>"+ "<div><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:secondment:main:02' onClick=edit('"+ row.id + "')  title='修改 Edit'></i>&nbsp;&nbsp;" +
			   		"<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:secondment:main:03' onClick=Delete('"+ row.id + "')  title='删除 Delete '></i>"+"</td>"; 

			   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('secondmentTypeEnum',row.jddxlx)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.jddxms)+"'>"+StringUtil.escapeStr(row.jddxms)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.whsj)+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.dprtname)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();       //触发按钮权限监控
	 }
	 
	//修改借调对象
	 function edit(id){
		 window.location =basePath+"/aerialmaterial/secondment/intoEdit?id="+id+"&pageParam="+encodePageParam();
	 }
	 function add(){
		window.location = basePath+"/aerialmaterial/secondment/intoSeconded?pageParam="+encodePageParam();
	}
	 //逻辑删除借调对象
	 function Delete(id){
		AlertUtil.showConfirmMessage("你确定要删除吗？",{callback: function(){
		   AjaxUtil.ajax({
				type : "post",
				url : basePath+"/aerialmaterial/secondment/Delete?id="+id,
				dataType : "json",
				success : function(data) {
					if(data!=0){
						finishWait();
						AlertUtil.showMessage('删除成功！');
						searchRevision();
					}
				}
		   });
		}});
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
		goPage(1,"auto","desc");
	}
		
	 //搜索条件重置
	 function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		 $("#jddxlx_search").val("");
			
		 var zzjgid=$('#zzjgid').val();
		 $("#keyword_search").val("");
		 $("#dprtcode_search").val(zzjgid);
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
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		};
