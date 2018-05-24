$(document).ready(function(){
	
});

var yjtsJb11 = '';
var yjtsJb21 = '';
var yjtsJb31 = '';
var isLoadMonitor1 = false;
//初始化系统阀值
function initMonitorsettings1(){
	isLoadMonitor1 = false;
}

//加载系统阀值
function loadMonitorsettings1() {
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode2").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb11 = data.monitorsettings.yjtsJb1;
				yjtsJb21 = data.monitorsettings.yjtsJb2;
				yjtsJb31 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor1 = true;
		}
	});
}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
		 if(!isLoadMonitor1){
				loadMonitorsettings1();
			}
		var obj ={};
		var searchParam = gatherSearchParam2();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search2").val());
		obj.hclx = searchParam.hclx;
		obj.zt = searchParam.zt;
		obj.dprtcode = searchParam.dprtcode;
		
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/stock/fieldmaterialstockList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   
				    finishWait();
				    if(data.rows !=""){
						   
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
						   signByKeyword($("#keyword_search2").val(),[2,3,4,5]);
						   
					   } else {
					
						  $("#list2").empty();
						  $("#pagination2").empty();
						  $("#list2").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'wchc'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam2(){
		 var searchParam = {};
		 searchParam.hclx = $("#hclx2").val();
		 searchParam.zt = $("#zt2").val();
		 searchParam.dprtcode = $("#dprtcode2").val();
		 return searchParam;
	 }
	 
	 function appendContentHtml2(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if(parseInt(row.syts)<=parseInt(row.yjtsJb11)){
				   htmlContent = htmlContent + "<tr   bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.syts)>parseInt(row.yjtsJb11) && parseInt(row.syts)<=parseInt(row.yjtsJb21)){
				   htmlContent = htmlContent + "<tr     bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   htmlContent = htmlContent + "<tr     >";
			   }
			   
			   htmlContent += "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='fixed-column text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
			   
			  
					htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			
					htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
/*			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.kwh)+"</td>";*/  
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.hjsm).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.syts)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(zt[row.zt])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list2").empty();
		   $("#list2").html(htmlContent);
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
		$("#divSearch2").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch2").find("textarea").each(function(){
		$(this).val("");
	});
	
	 $("#divSearch2").find("select").each(function(){
			$(this).val("");
		});
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search2").val("");
	 $("#dprtcode2").val(zzjgid);
	 $("#dprtcode2").trigger("change");
	}
 
	//搜索条件显示与隐藏 
	function search2() {
		if ($("#divSearch2").css("display") == "none") {
			$("#divSearch2").css("display", "block");
			$("#icon2").removeClass("icon-caret-down");
			$("#icon2").addClass("icon-caret-up");
		} else {
			$("#divSearch2").css("display", "none");
			$("#icon2").removeClass("icon-caret-up");
			$("#icon2").addClass("icon-caret-down");
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
		 
		//字段排序
		function orderBy2(obj, _obj) {
			$obj = $("#wchc th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#wchc .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#wchc .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#pagination2 li[class='active']").text();
			goPage2(currentPage,obj, orderType);
		}
	
