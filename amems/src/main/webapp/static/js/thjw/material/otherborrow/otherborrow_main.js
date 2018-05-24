var hclb=["其它","航材","设备","工具","危险品","低值易耗品"];
$(document).ready(function(){
	Navigation(menuCode);
	
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
	
	 //goPage(1,"auto","desc");//开始的加载默认的内容 
		//数据字典
	 refreshPermission();
	 decodePageParam();
});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#fxd").val());
	params.keyword = $.trim($("#keyword_search").val());
	
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
		$("#fxd").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
		
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
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword =$.trim( $("#keyword_search").val());
		obj.dprtcode = searchParam.dprtcode;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/otheraerocade/borrow/borrowApplyList",
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
				       signByKeyword($("#keyword_search").val(),[3]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'qtjrsq'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $("#fxd").val();
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
			   htmlContent = htmlContent + "<td class='text-center'>";
			   if(row.ckdh==null){
				   htmlContent += "<i class='icon-signout color-blue cursor-pointer checkPermission' permissioncode='otheraerocade:borrow:main:01' onClick=\"output('" + row.id + "')\" title='出库 Edit'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' sqdh='"+StringUtil.escapeStr(row.sqdh)+"' fxd='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' onclick=\"viewDetail('"+row.id+"',event)\">"+StringUtil.escapeStr(row.sqdh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent = htmlContent + "<td class='text-center '>"+formatUndefine(row.sqsj).substring(0,10)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.paramsMap.dprtname)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetailStockRemoval('"+row.id+"','"+StringUtil.escapeStr(row.ckdh)+"')\">"+StringUtil.escapeStr(row.ckdh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	 //查看申请单
	 function viewDetail(id,e){
		 var sqdh = $(e.target).attr("sqdh");
		 var fxd = $(e.target).attr("fxd");
		 $("#sqdh").val(sqdh);
		 $("#fxds").val(fxd);
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
			   htmlContent = htmlContent + "<td class='text-left ' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
			   if(!row.hcMainData){row.hcMainData = {}}
			   htmlContent = htmlContent + "<td class='text-left ' title='"+StringUtil.escapeStr(row.hcMainData.ywms)+"'>"+StringUtil.escapeStr(row.hcMainData.ywms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left ' title='"+StringUtil.escapeStr(row.hcMainData.zwms)+"'>"+StringUtil.escapeStr(row.hcMainData.zwms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left '>"+StringUtil.escapeStr(hclb[row.hcMainData.hclx])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left '>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-right '>"+formatUndefine(row.sqsl)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left '>"+StringUtil.escapeStr(row.hcMainData.jldw)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left ' title='"+StringUtil.escapeStr(row.yt)+"'>"+StringUtil.escapeStr(row.yt)+"</td>"; 
			
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
		 
	 		window.location = basePath+"/otheraerocade/borrow/otherborrowHistory?id=" + id+"&pageParam="+encodePageParam();
	 }		
	 
	//字段排序
	function orderBy(obj, _obj) {
		$obj = $("#qtjrsq th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#qtjrsq .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#qtjrsq .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
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
		 
