var rzlx=["","正确","错误"];
$(document).ready(function(){
	Navigation(menuCode);
	 goPage(1,"auto","desc");//开始的加载默认的内容 
});

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
}

 //带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search").val());
		
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/sys/log/loginlogList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.total>0){
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
				       signByKeyword($("#keyword_search").val(),[2,3,4,5]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'sqzz'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $("#dprtcode").val();
		 
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			   htmlContent += "<td class='' class='text-center'>"+(index+1)+"</td>";
			   var username=StringUtil.escapeStr(row.username);
			   if(username==" "){
				   username="";
			   }
			   
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(username)+"'>"+StringUtil.escapeStr(username)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.czip)+"' >"+StringUtil.escapeStr(row.czip)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left'>"+StringUtil.escapeStr(row.czmc)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.czsj)+"'>"+StringUtil.escapeStr(row.czsj)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-center' >"+StringUtil.escapeStr(rzlx[row.rzlx])+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.ycnr)+"'>"+StringUtil.escapeStr(row.ycnr)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-center' >"+formatUndefine(row.casj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
	 }
	 
	//字段排序
	function orderBy(obj, _obj) {
		$obj = $("#sqzz th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#sqzz .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#sqzz .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
		var orderType = "asc";
		if (orderStyle.indexOf ( "sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj, orderType);
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
		
		 $("#divSearch").find("select").each(function(){
				$(this).val("");
			});
		 
		 var zzjgid=$('#zzjgid').val();
		 $("#keyword_search").val("");
		 $("#dprtcode").val(zzjgid);
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
		
	//回车事件控制
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$('#loginlogMainSearch').trigger('click');
		}
	};	 
	
	
