$(document).ready(function(){
	
});

//带条件搜索的翻页
function AjaxGetDatasWithSearch1(pageNumber,sortType,sequence){
	var obj ={};
	var searchParam = gatherSearchParam1();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword =  $.trim($("#keyword_search1").val());
	obj.hclx = searchParam.hclx;
	obj.lx = searchParam.lx;
	obj.dprtcode = searchParam.dprtcode;
	
	startWait();
	
	AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/stock/contractdetaillist",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			    if(data.rows !=""){
					   appendContentHtml1(data.rows);
					   
					 	 new Pagination({
								renderTo : "pagination1",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								goPage1:function(a,b,c){
									AjaxGetDatasWithSearch1(a,b,c);
								}
							}); 
					   
					// 标记关键字
					   signByKeyword($("#keyword_search1").val(),[2,3,4,5]);
					   
				   } else {
				
					  $("#list1").empty();
					  $("#pagination1").empty();
					  $("#list1").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'zthc'});
	      }
	    }); 
	 
	
}



//将搜索条件封装 
function gatherSearchParam1(){
	 var searchParam = {};
	 
	 searchParam.hclx = $("#hclx1").val();
	 searchParam.lx = $("#lx").val();
	 searchParam.dprtcode = $("#dprtcode1").val();
	 
	 return searchParam;
}

function appendContentHtml1(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   htmlContent += "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		   htmlContent = htmlContent + "<td class='fixed-column text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.cjjh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.jldw)+"</td>";  
		   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.ztsl)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.hth)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.gysmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+formatUndefine(row.htDhrq).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' >"+AccessDepartmentUtil.getDpartName(StringUtil.escapeStr(row.dprtcode))+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list1").empty();
	   $("#list1").html(htmlContent);
	 
}

//字段排序
function orderBy1(obj, _obj) {
	$obj = $("#zthc th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#zthc .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#zthc .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#pagination1 li[class='active']").text();
	goPage1(currentPage,obj, orderType);
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage1(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch1(pageNumber,sortType,sequence);
}

//信息检索
function searchRevision1(){
	goPage1(1,"auto","desc");
}

//搜索条件显示与隐藏 
function search1() {
	if ($("#divSearch1").css("display") == "none") {
		$("#divSearch1").css("display", "block");
		$("#icon1").removeClass("icon-caret-down");
		$("#icon1").addClass("icon-caret-up");
	} else {
		$("#divSearch1").css("display", "none");
		$("#icon1").removeClass("icon-caret-up");
		$("#icon1").addClass("icon-caret-down");
	}
	
	
}

	//搜索条件重置
	function searchreset1(){
		$("#divSearch1").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch1").find("textarea").each(function(){
		$(this).val("");
	});
	
	 $("#divSearch1").find("select").each(function(){
			$(this).val("");
		});
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search1").val("");
	 $("#dprtcode1").val(zzjgid);
	}