var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其它","航材","设备","工具","危险品","低值易耗品"];
$(document).ready(function(){
	Navigation(menuCode);
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 $('#zuigao').css('background',warningColor.level2); 
	 
});


/**
 * 更改选中的飞机
 */
function changeSelectedPlane(){
	 goPage(1,"auto","desc");//开始的加载默认的内容 
}
var max=2;
var min=2;
/**
 * 最低
 */
function onclick1(){
	 if($("#zuidi").is(':checked')) {
		 min=1;
		 max=2;
		 $("#zuigao").attr("checked", false);
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}else{
		 min=2;
		 max=2;
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}
}
/**
 * 最高
 */
function changeSelectedPlane2(){
	 if($("#zuigao").is(':checked')) {
		 min=2;
		 max=1;
		 $("#zuidi").attr("checked", false);
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}else{
		 min=2;
		 max=2;
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}
}

 //带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.dprtcode = searchParam.dprtcode;
		obj.hclx = searchParam.hclx;
		obj.minKcl = min;
		obj.maxKcl = max;
		
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/stockwarning/stockwarningList",
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
				       signByKeyword($("#keyword_search").val(),[2,3,4,7,9,10,11,17]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'sqzz'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $("#dprtcode").val();
		 searchParam.hclx = $("#hclx").val();
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			  
			   if(parseInt(row.kcsl)<=parseInt(row.minKcl)){
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\"   ;  bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.kcsl)>=parseInt(row.maxKcl)){
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\"  ;   bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   if (index % 2 == 0) {
						  htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
					   } else {
						   htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\">";;
					   }
				   
			   }
			   
			   htmlContent += "<td  class='text-center'>"+(index+1)+"</td>";
			   htmlContent = htmlContent + "<td class=' text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+ StringUtil.escapeStr(row.ywms) +"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left ' title='"+ StringUtil.escapeStr(row.zwms) +"' >"+StringUtil.escapeStr(row.zwms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.minKcl)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.maxKcl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
	 }
	 
	 //查看申请单
	 function viewDetail(id){
			startWait();
			AjaxUtil.ajax({
			   url:basePath+"/otheraerocade/borrow/queryByMaterials?id="+id,
			   type: "post",
			   success:function(data){
				   finishWait();
				   if(data.rows !=""){
				      appendContentHtml2(data.rows);
					   
				      $("#alertModalview").modal("show");
				   } else {
					  $("#list2").empty();
					  $("#pagination").empty();
					  $("#list2").append("<tr><td colspan=\"16\">暂无数据 No data.</td></tr>");
				   }
		     }
		   }); 
	 }

	 
	 
	 function appendContentHtml2(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.hcMainData.zwms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.hcMainData.ywms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(hclb[row.hcMainData.hclx])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+formatUndefine(row.sl)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.hcMainData.jldw)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+StringUtil.escapeStr(row.yt)+"</td>"; 
			
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list2").empty();
		   $("#list2").html(htmlContent);
	 }
	 
	 //查看出库单
	 function viewDetailStockRemoval(id,ckdh){
	 	window.location = basePath+"/otheraerocade/borrow/view?id=" + id+"&ckdh=" + encodeURIComponent(ckdh)+"&t=" + new Date().getTime();
	 }
	 //其它飞行队出库
	 function output(id){
	 		window.location = basePath+"/otheraerocade/borrow/otherborrowHistory?id=" + id+"&t=" + new Date().getTime();
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
		 
		function stockWaringOutExcel(){
			 var dprtcode = $("#dprtcode").val();
			 var hclx = $("#hclx").val();
			 var minKcl = min;
			 var maxKcl = max;
			 var keyword = $.trim($("#keyword_search").val());
			 window.open(basePath+"/aerialmaterial/stockwarning/stockWaringOut.xls?hclx=" + encodeURIComponent(hclx) + "&dprtcode="
			 			+ dprtcode + "&keyword="+ encodeURIComponent(keyword)+"&minKcl="+minKcl+"&maxKcl="+maxKcl);
		} 
	
		//回车事件控制
		document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision(); //调用主列表页查询
				}
			}
		};