var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
$(document).ready(function(){
	
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 //删除当前飞行队
	 var jgdm=$('#zzjgid').val();
	 $("#fxd option[value='"+jgdm+"']").remove();
	 
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
	
});



//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword = $.trim($("#keyword_search").val());
	obj.hclx = searchParam.hclx;
	obj.ckh = searchParam.ckh;
	obj.dprtcode = searchParam.dprtcode;
	obj.oldDprtcode=$('#zzjgid').val();
	startWait();
	
	AjaxUtil.ajax({
		   url:basePath+"/otheraerocade/stock/otheraerocadeStockList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   
			    finishWait();
			    if(data.rows !=""){
					   
			    	appendContentHtml(data.rows,data.monitorsettings);
					   
			    	
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
					   signByKeyword($("#keyword_search").val(),[2,3,4,5,6]);
					   
				   } else {
				
					  $("#list").empty();
					  $("#pagination").empty();
					  $("#list").append("<tr><td class='text-center' colspan=\"18\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
	 
	
}

//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 
	 searchParam.hclx = $("#hclx").val();
	 searchParam.ckh = $("#ckh").val();
	 searchParam.dprtcode = $("#fxd").val();
	 
	 return searchParam;
}

function appendContentHtml(list,monitorsettings){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
		   if (index % 2 == 0) {
			   if(parseInt(row.syts)<=parseInt(monitorsettings.yjtsJb1)){
				   htmlContent = htmlContent + "<tr  bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.syts)>parseInt(monitorsettings.yjtsJb1) && parseInt(row.syts)<=parseInt(monitorsettings.yjtsJb2)){
				   htmlContent = htmlContent + "<tr     bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   htmlContent = htmlContent + "<tr     bgcolor=\"#f9f9f9\" >";
			   }
		   }else{
			   if(parseInt(row.syts)<=parseInt(monitorsettings.yjtsJb1)){
				   htmlContent = htmlContent + "<tr  bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.syts)>parseInt(monitorsettings.yjtsJb1) && parseInt(row.syts)<=parseInt(monitorsettings.yjtsJb2)){
				   htmlContent = htmlContent + "<tr     bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   htmlContent = htmlContent + "<tr     bgcolor=\"#fefefe\">";
			   }
		   }
		   htmlContent += "<td style='vertical-align: middle;'   class='text-center fixed-column' >"+formatUndefine(index+1)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.grn)+"'>"+StringUtil.escapeStr(row.grn)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
		   
		   if(StringUtil.escapeStr(row.sn) != ""){
				htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			}else{
				htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			}
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' >"+StringUtil.escapeStr(row.kcsl)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.syts)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(zt[row.zt])+"</td>";
		   if(row.jg_dprt==null || row.jg_dprt==""){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   }
		   
		   htmlContent = htmlContent + "</tr>";  
		    
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
	if (orderStyle.indexOf("sorting_asc")>=0) {
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

$("#divSearch").find("select").each(function(){
		$(this).val("");
	});
var zzjgid=$('#zzjgid').val();
$("#keyword_search").val("");
$("#dprtcode").val(zzjgid);
/*$("#fxd").trigger("change");*/
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
	 
