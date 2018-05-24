
var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
$(document).ready(function(){
	Navigation(menuCode);
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			var selectTab = $("#tablist").find("li.active").index();
			
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				if(selectTab == 0){
					$('#borrowgivebackSearch').trigger('click');
				}else if(selectTab == 1){
					$('#loangivebackSearch').trigger('click');
				}
			}
		}
	});
		
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 
	 initNav();
	 
});
/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	}); 
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.hclx = searchParam.hclx;
		obj.dprtcode = searchParam.dprtcode;
		obj.jddxlx = searchParam.jddxlx;
		
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/secondment/expatriateList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   
				    finishWait();
				    if(data.rows !=""){
						   
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
						  $("#list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
					   }
				    //new Sticky({tableId:'jrwgh'});
		      }
		    }); 
		 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 
		 searchParam.hclx = $("#hclx").val();
		 searchParam.jddxlx = $("#jddxlx").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 
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
			   
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jddxms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
				
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
			 
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.dghsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }

		
		//字段排序
		function orderBy(obj, _obj) {
			$obj = $("#jrwgh th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#jrwgh .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#jrwgh .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
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
		 
	
	
