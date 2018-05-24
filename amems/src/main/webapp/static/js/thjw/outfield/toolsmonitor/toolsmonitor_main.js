
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	loadMonitorsettings();
	goPage(1,"auto","desc");//开始的加载默认的内容 
	//初始化日志功能
	logModal.init({code:'B_W_001'});
});

//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/outfield/toolsmonitor/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode_search").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
}

 //带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	if(!isLoadMonitor){
		loadMonitorsettings();
	}
	var searchParam = gatherSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	var url = basePath+"/outfield/toolsmonitor/queryAllPageList";
	startWait();
	AjaxUtil.ajax({
		url:url,
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
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
					}
				}); 
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[3,4,5,7,8],"#list tr td");
		   } else {
			  $("#list").empty();
			  $("#pagination").empty();
			  $("#list").append("<tr><td colspan=\"16\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'tool_main_table'});
      }
    }); 
	
}
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var keyword = $.trim($("#keyword_search").val());
	 var hclx = $.trim($("#hclx_search").val());
	 var jyXcrq = $.trim($("#jyXcrq_search").val());
	 paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
	 paramsMap.keyword = keyword;
	 if(null != jyXcrq && "" != jyXcrq){
		 var jyXcrqBegin = jyXcrq.substring(0,10)+" 00:00:00";
		 var jyXcrqEnd = jyXcrq.substring(13,23)+" 23:59:59";
		 paramsMap.jyXcrqBegin = jyXcrqBegin;
		 paramsMap.jyXcrqEnd = jyXcrqEnd;
	 }
	 if('' != hclx){
		 paramsMap.hclx = hclx;
	 }
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
}
 
function appendContentHtml(list){
	 
   var htmlContent = '';
   $.each(list,function(index,row){
	   if (index % 2 == 0) {
		   htmlContent += "<tr id='"+row.ID+"' bgcolor=\""+getWarningColor("#f9f9f9",row.SYTS)+"\" >";
	   } else {
		   htmlContent += "<tr id='"+row.ID+"' bgcolor=\""+getWarningColor("#fefefe",row.SYTS)+"\" >";
	   }
	   
	   htmlContent += "<td class='fixed-column text-center'>";
	   
	   htmlContent += "<i class='icon-cogs color-blue cursor-pointer' mbjxlh='"+StringUtil.escapeStr(row.MBJXLH)+"' dprtcode='"+formatUndefine(row.DPRTCODE)+"' onClick=\"set(this)\" title='设置 Set'></i>&nbsp;&nbsp;";
	   
	   htmlContent += "</td>";
	   
	   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.MBJXLH)+"' class='text-left' >"+StringUtil.escapeStr(row.MBJXLH)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left' >"+StringUtil.escapeStr(row.BJH)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.HCYWMS)+" "+StringUtil.escapeStr(row.HCZWMS)+"' class='text-left'>";
	   htmlContent += StringUtil.escapeStr(row.HCYWMS)+" "+StringUtil.escapeStr(row.HCZWMS);
	   htmlContent += "</td>";
	   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX) +"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.BJXLH)+"' class='text-left' >"+StringUtil.escapeStr(row.BJXLH)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' class='text-left'>";
	   htmlContent += StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS);
	   htmlContent += "</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.GG)+"' class='text-left'>"+StringUtil.escapeStr(row.GG)+"</td>";
	   htmlContent += "<td title='"+StringUtil.escapeStr(row.XH)+"' class='text-left'>"+StringUtil.escapeStr(row.XH)+"</td>";
	   htmlContent += "<td class='text-right'>"+formatUndefine(row.JY_ZQ)+"</td>";
	   htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.JY_SCRQ))+"</td>";
	   htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.JY_XCRQ))+"</td>";
	   htmlContent += "<td class='text-right'>"+formatUndefine(row.SYTS)+"</td>";
	   htmlContent += "<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
	   htmlContent += "</tr>";  
	    
   });
   $("#list").empty();
   $("#list").html(htmlContent);
	 
}
//获取预警颜色
function getWarningColor(bgcolor,syts){
	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
		bgcolor = warningColor.level2;//黄色
	}
	if(Number(syts) <= yjtsJb1){
		bgcolor = warningColor.level1;//红色
	}
	return bgcolor;
}

//跳转至设备工具监控设置
function set(obj){
	var bjxlh = $(obj).attr("mbjxlh");
	var dprtcode = $(obj).attr("dprtcode");
	window.location = basePath+"/outfield/toolsmonitor/setting?bjxlh=" + encodeURIComponent(bjxlh)+"&dprtcode="+dprtcode+"&t=" + new Date().getTime();
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
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
	var dprtId = $.trim($("#dprtId").val());
	
	$("#keyword_search").val("");
	
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	$("#dprtcode_search").val(dprtId);
		
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
		if($("#keyword_search").is(":focus") 
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
		){
			searchRevision();      
		}
	}
};
		 