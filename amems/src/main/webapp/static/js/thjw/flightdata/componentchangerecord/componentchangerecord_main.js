$(document).ready(function(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 //refreshPermission();
	});
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.dprtcode=searchParam.dprtcode;
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/componentchangerecord/list",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
						//controller: this_
					});
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[1,2,3,5,12]);
			   } else {
				  $("#Componentchangerecordlist").empty();
				  $("#pagination").empty();
				  $("#Componentchangerecordlist").append("<tr class='text-center'><td colspan=\"18\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
		 searchParam.zt = $.trim($("#zt").val());
		 searchParam.dprtcode = $.trim($("#zzjg").val());
		 return searchParam;
	 }

function appendContentHtml(list){
	   var htmlContent = '';
	    choseAllIds=[];
	   $.each(list,function(index,row){
		   choseAllIds.push(row.id);
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor='#f9f9f9' >";
		   } else {
			   htmlContent += "<tr bgcolor='#fefefe'  >";
		   }
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.jh))+"'>"+StringUtil.escapeStr(formatUndefine(row.jh))+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>"+StringUtil.escapeStr(formatUndefine(row.xlh))+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" >"+formatUndefine(row.aZxrq)+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+StringUtil.escapeStr(formatUndefine(row.azJld))+"'>"+StringUtil.escapeStr(formatUndefine(row.azJld))+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" >"+StringUtil.escapeStr(formatUndefine(row.azGzzxm))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.gdsx)+"'>"+convertLine(formatUndefine(row.gdsx))+"</td>";  
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.bjyy)+"'>"+convertLine(formatUndefine(row.bjyy))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.zssy)+"'>"+convertLine(formatUndefine(row.zssy))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.azBz)+"'>"+formatUndefine(row.azBz)+"</td>";
		   
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" >"+formatUndefine(row.cZxrq)+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+StringUtil.escapeStr(formatUndefine(row.cjJld))+"'>"+StringUtil.escapeStr(formatUndefine(row.cjJld))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" >"+StringUtil.escapeStr(formatUndefine(row.cjGzzxm))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.cxyy)+"'>"+convertLine(formatUndefine(row.cxyy))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.cxsy)+"' >"+convertLine(formatUndefine(row.cxsy))+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" >"+formatUndefine(row.cjZsjjh) + ' ' + formatUndefine(row.cjZsjxlh)+"</td>";
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(row.cjBz)+"'>"+formatUndefine(row.cjBz)+"</td>";
		   
		   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  title='"+formatUndefine(row.zjsy)+"'>"+convertLine(formatUndefine(row.zjsy))+"</td>";
		   htmlContent = htmlContent + "</tr>";   
	   });
	   $("#Componentchangerecordlist").empty();
	   $("#Componentchangerecordlist").html(htmlContent);
	   
	   //refreshPermission();
	 
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
	 var zzjgid=$('#zzjgid').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#zzjg").val(zzjgid);
	 $("#yjjb").val(yjts);
	 changeContent();
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
 //查看跳转
function viewMainAnnunciate(id,dprtcode){
	window.open(basePath+"/project/annunciate/intoViewMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam());
}
//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}

function convertLine(str) {
	var result = '';
	var arr = str.split(',');
	$.each(arr, function() {
		result += this + '<br>';
	})
	return result;
}

