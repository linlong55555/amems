
var jdxd=["其它","飞行队","航空公司"];
var ztData = [];
ztData[1] = "保存";
ztData[2] = "提交";
ztData[8] = "作废";
ztData[9] = "完成";
ztData[11] = "撤销";

$(document).ready(function(){
	Navigation(menuCode);
	decodePageParam();
	refreshPermission();
	
	//回车事件控制
	$(document).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision(); //调用主列表页查询
			}
		}
	});
	
});


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
//	params.sqr = $.trim($("#sqr").val());
//	params.fjzch = $.trim($("#fjzch_search").val());
//	params.jddxlx = $.trim($("#jddxlx").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	params.keyword = $.trim($("#keyword_search").val());
//	params.flightdate = $("#flightDate_search").val();

	
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
//		$("#sqr").val(params.sqr);
//		$("#fjzch_search").val(params.fjzch);
//		$("#jddxlx").val(params.jddxlx);
		$("#dprtcode").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
//	    $("#flightDate_search").val(params.flightdate);
		
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
		obj.keyword = $.trim($("#keyword_search").val());
		obj.paramsMap ={ dateBegin:searchParam.dateBegin,
				dateEnd:searchParam.dateEnd
			   };
		obj.zt = searchParam.zt;
		obj.dprtcode = searchParam.dprtcode;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/returnedpurchase/returnedpurchaseList",
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
				       signByKeyword($("#keyword_search").val(),[3,4,5,6,9,10]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'jrsq'});
		      }
		    }); 
		 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.zt = $("#zt").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 var date = $("#flightDate_search").val();
		 if(null != date && "" != date){
			 searchParam.dateBegin = date.substring(0,4)+"-"+date.substring(5,7)+"-"+date.substring(8,10);
			 searchParam.dateEnd = date.substring(12,17)+"-"+date.substring(18,20)+"-"+date.substring(21,23);
		 }
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
			   if(row.zt==1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:returnedpurchase:manage:02' onClick=\"edit('" + row.id + "',"+row.zt+")\" title='修改 Edit'></i>";
				   htmlContent +="&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:returnedpurchase:manage:03' onClick=\"cancel('"+ row.id + "',"+row.zt+")\" title='作废  Invalid'></i>";
			   }
			   if(row.zt==2){
				   htmlContent += "<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:returnedpurchase:manage:04' onClick=\"revoked('" + row.id + "',"+row.zt+")\" title='撤销 Revoked'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";

			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.thdh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.outstock.ckdh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.fhdw)+"'>"+StringUtil.escapeStr(row.fhdw)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.thrid)+"'>"+StringUtil.escapeStr(row.thrid)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center '>"+formatUndefine(row.thrq).substring(0,10)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center '>"+ztData[row.zt]+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdrid)+"'>"+StringUtil.escapeStr(row.zdrid)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.zdsj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.dprt.dprtname)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }

	//查看详情
	 function viewDetail(id){
		 window.open( basePath+"/aerialmaterial/returnedpurchase/view?id=" + id+"&pageParam="+encodePageParam());
	 }
	 
	 //新增
	 function add(id){
		 window.location = basePath+"/aerialmaterial/returnedpurchase/add?&pageParam="+encodePageParam();
	 }
	 
	//编辑提订单
	 function edit(id,zt){
	 	if(zt != 1){
	 		AlertUtil.showErrorMessage("对不起,只有保存状态下的借入单才能修改!");
	 		return false;
	 	}
	 	window.location = basePath+"/aerialmaterial/returnedpurchase/edit?id=" + id+"&pageParam="+encodePageParam();
	 }		
	 
	//作废
	 function cancel(id,zt) {
		 
	 	if(zt != 1){
	 		AlertUtil.showMessage("对不起,只有保存状态下的退货单才能作废!");
	 		return false;
	 	}
		AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/aerialmaterial/returnedpurchase/cancel",
	 			type:"post",
	 			async: false,
	 			data:{
	 				id : id
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('作废成功!');
	 				searchRevision();
	 				refreshPermission();
	 			}
	 		});
	 	}});
	 }
	 
	//撤销
	 function revoked(id,zt) {
		 
	 	if(zt != 2){
	 		AlertUtil.showMessage("对不起,只有提交状态下的退货单才能撤销!");
	 		return false;
	 	}
		AlertUtil.showConfirmMessage("您确定撤销吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/aerialmaterial/returnedpurchase/revoked",
	 			type:"post",
	 			async: false,
	 			data:{
	 				id : id
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('撤销成功!');
	 				searchRevision();
	 				refreshPermission();
	 			}
	 		});
	 	}});
	 }
	
		//字段排序
		function orderBy(obj, _obj) {
			$obj = $("#jrsq th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#jrsq .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#jrsq .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
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
	
