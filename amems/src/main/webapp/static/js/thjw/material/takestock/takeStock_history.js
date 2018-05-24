

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
	
	initStoreSelect();
	decodePageParam();//开始的加载默认的内容 
});

var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.ckid = $.trim($("#ck_search").val());
	params.zt = $.trim($("#zt_search").val());
	params.ksrq = $.trim($("#ksrq_search").val());
	params.pdrname = $.trim($("#pdrname_search").val());
	params.zdrname = $.trim($("#zdrname_search").val());
	params.zdrq = $.trim($("#zdrq_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
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
		$("#keyword_search").val(params.keyword);
		$("#ck_search").val(params.ckid);
		$("#zt_search").val(params.zt);
		$("#ksrq_search").val(params.ksrq);
		$("#pdrname_search").val(params.pdrname);
		$("#zdrname_search").val(params.zdrname);
		$("#zdrq_search").val(params.zdrq);
		$("#dprtcode_search").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

function initStoreSelect(){
	$("#ck_search").empty();
	var storeHtml = "<option value=''>显示全部All</option>";
	var dprtcode = $("#dprtcode_search").val();
	$.each(userStoreList, function(index, row){
		if(dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>"
		}
	});
	$("#ck_search").append(storeHtml);
}


	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		url = basePath+"/aerialmaterial/takestock/queryAllPageList";
		
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
				   signByKeyword($("#keyword_search").val(),[3,4,5,7],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'take_history_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var paramsMap = {};
		 paramsMap.userId = $.trim($("#userId").val());
		 paramsMap.userType = $.trim($("#userType").val());
		 var ckid = $.trim($("#ck_search").val());
		 var zt = $.trim($("#zt_search").val());
		 var ksrq = $.trim($("#ksrq_search").val());
		 var zdrq = $.trim($("#zdrq_search").val());
		 searchParam.keyword = keyword;
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 if('' != ckid){
			 searchParam.ckid = ckid;
		 }
		 if('' != zt){
			 searchParam.zt = zt;
		 }
		 if(null != ksrq && "" != ksrq){
			 var ksrqBegin = ksrq.substring(0,10)+" 00:00:00";
			 var ksrqEnd = ksrq.substring(13,23)+" 23:59:59";
			 paramsMap.ksrqBegin = ksrqBegin;
			 paramsMap.ksrqEnd = ksrqEnd;
		 }
		 if(null != zdrq && "" != zdrq){
			 var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
			 var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
			 paramsMap.zdrqBegin = zdrqBegin;
			 paramsMap.zdrqEnd = zdrqEnd;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			   } else {
				   htmlContent += "<tr bgcolor=\"#fefefe\" >";
			   }
			   htmlContent += "<td class='text-center'>";
			   if(row.zt == 2){
				   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:takestock:history:01' onClick=\"audit('"+ row.id + "',"+row.zt+")\" title='审核 Review'></i>";
			   }
			   if(row.zt == 3){
				   htmlContent += "<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:takestock:history:02' onClick=\"recall('"+ row.id + "',"+row.zt+")\" title='撤销 recall'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.pddh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pdr?row.pdr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.pdr?row.pdr.displayName:'')+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ckmc)+"' class='text-left'>"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</td>";
			   htmlContent += "<td class='text-center'>"+indexOfTime(row.ksrq)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>"; 
			   htmlContent += "<td class='text-center'>"+row.zdsj+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('takeStockStatusEnum',row.zt) +"</td>";
			   htmlContent += "<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 

//审核盘点
function audit(id,zt){
	window.location = basePath+"/aerialmaterial/takestock/audit?id=" + id+"&pageParam=" + encodePageParam();
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/takestock/view?id=" + id+"&pageParam=" + encodePageParam());
}

//撤销
function recall(id,zt){
	
	startWait();
	AlertUtil.showConfirmMessage("确定撤销吗？", {callback: function(){

			var url = basePath+"/aerialmaterial/takestock/recall";
			var param = {};
			param.id = id;
			// 提交数据
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				async:false,
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					finishWait();
					//AlertUtil.showMessage('撤销成功!');
					refreshPage();//刷新页面
				}
			});
		}
	});
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
	
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
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
		 
