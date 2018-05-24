var maxBb = 0.0;//最大版本
var gljb=["","无","批次号管理","序列号管理"];
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
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
	
	changeOrganization();
	//初始化领用日期,并查询数据
	 TimeUtil.getCurrentDate(function (date){
		 var lyrq = TimeUtil.dateOperator(date,30,TimeUtil.Operation.SUBTRACT);
		 $("#lyrq_search").val(lyrq+" ~ "+date);
		 goPage(1,"auto","desc");
		 
		 $('input[name=date-range-picker]').daterangepicker({cancelClass:'hide'}).prev().on("click",
				function() {
					$(this).next().focus();
				});
		 
	 });
	 
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode_search").val());
	var planeRegOption = '<option value="">显示全部All</option>';
	var data= acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	if(data.length >0){
		$.each(data,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
		});
	}
	/*if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}*/
	$("#fjzch_search").html(planeRegOption);
}

function onchangeSelect(){
	goPage(1,"auto","desc"); 
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 
		 var searchParam = gatherSearchParam();
		 searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		 startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/aerialmaterial/requisitioncost/queryAllPageList",
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
				 } else {
					 $("#list").empty();	
					 $("#pagination").empty();
					 $("#list").append("<tr><td colspan=\"29\" class='text-center'>暂无数据 No data.</td></tr>");
				 }
				 new Sticky({tableId:'requisitioncost_main_table'});
			 }
		 }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var fjzch = $.trim($("#fjzch_search").val());
		 var lyrq = $.trim($("#lyrq_search").val());
		 var hclx = $.trim($("#hclx_search").val());
		 var hclxej = $.trim($("#hclxej_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val());
		 
		 var ckrqBegin = lyrq.substring(0,10)+" 00:00:00";
		 var ckrqEnd = lyrq.substring(13,23)+" 23:59:59";
		 paramsMap.ckrqBegin = ckrqBegin;
		 paramsMap.ckrqEnd = ckrqEnd;
		 paramsMap.fjzch = fjzch;
		 paramsMap.dprtcode = dprtcode;
		 paramsMap.hclx = hclx;
		 paramsMap.hclxej = hclxej;
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var yxx = '';
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialSecondTypeEnum',row.HCLX_EJ)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left'>"+StringUtil.escapeStr(row.BJH)+"</td>";
			   htmlContent += "<td title='"+gljb[row.GLJB]+"' class='text-left'>"+gljb[row.GLJB]+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SN)+"' class='text-left'>"+StringUtil.escapeStr(row.SN)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.PCH)+"' class='text-left'>"+StringUtil.escapeStr(row.PCH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.YWMS)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ZWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.ZWMS)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.SYSL)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.JLDW)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.KCCB)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.LYCB)+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.CKRQ)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CKDH)+"' class='text-center'>"+StringUtil.escapeStr(row.CKDH)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.KCSL)+"</td>";
			   htmlContent += "<td class='text-right'>"+StringUtil.escapeStr(row.TKSL)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.BZ)+"' class='text-left'>"+StringUtil.escapeStr(row.BZ)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CJJH)+"' class='text-left'>"+StringUtil.escapeStr(row.CJJH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(formatUndefine(row.ZJH)+" "+StringUtil.escapeStr(row.ZZWMS))+"' class='text-left'>"+StringUtil.escapeStr(row.ZJH)+" "+StringUtil.escapeStr(row.ZZWMS)+"</td>";
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('materialPriceEnum',row.HCJZ)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.GG)+"' class='text-left'>"+StringUtil.escapeStr(row.GG)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.XINGH)+"' class='text-left'>"+StringUtil.escapeStr(row.XINGH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CKH)+"' class='text-left'>"+StringUtil.escapeStr(row.CKH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CKMC)+"' class='text-left'>"+StringUtil.escapeStr(row.CKMC)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.KWH)+"' class='text-left'>"+StringUtil.escapeStr(row.KWH)+"</td>";
//			   htmlContent += "<td title='"+StringUtil.escapeStr(row.JDMS)+"' class='text-left'>"+StringUtil.escapeStr(row.JDMS)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.XKZH)+"' class='text-left'>"+StringUtil.escapeStr(row.XKZH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SHZH)+"' class='text-left'>"+StringUtil.escapeStr(row.SHZH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SHZWZ)+"' class='text-left'>"+StringUtil.escapeStr(row.SHZWZ)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
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
		
		$("#hclx_search").val("");
		
		changeOrganization();
		
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
		changeOrganization();
		TimeUtil.getCurrentDate(function (date){
			 var lyrq = TimeUtil.dateOperator(date,30,TimeUtil.Operation.SUBTRACT);
			 $("#lyrq_search").val(lyrq+" ~ "+date);
		 });
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
		
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',

			includeSelectAllOption : true
		}); 
		 
		/**
		 * 导出excel
		 */
		function outRequisitioncostExcel(){
			var dprtcode=$("#dprtcode_search").val();
			var hclx=$("#hclx_search").val();
			var hclx_ej=$("#hclxej_search").val();
			var fjzch=$("#fjzch_search").val();
			var lyrq=$("#lyrq_search").val();
			window.open(basePath+"/aerialmaterial/requisitioncost/outRequisitioncostExcel?fjzch=" + encodeURIComponent(fjzch) + "&dprtcode="+encodeURIComponent(dprtcode)
		 		+"&hclx="+encodeURIComponent(hclx)+"&hclx_ej="+encodeURIComponent(hclx_ej)+"&lyrq="+encodeURIComponent(lyrq));
		}
