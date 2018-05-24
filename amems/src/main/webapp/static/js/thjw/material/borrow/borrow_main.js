
var jdxd=["其它","飞行队","航空公司"];
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
	
	decodePageParam();
	changeOrganization();
	// goPage(1,"auto","desc");//开始的加载默认的内容 
	 refreshPermission();
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '<option value="" >显示全部All</option><option value="00000" >通用Currency</option>';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch_search").html(planeRegOption);
}


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.fjzch = $.trim($("#fjzch_search").val());
	params.jddxlx = $.trim($("#jddxlx").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	params.keyword = $.trim($("#keyword_search").val());
	params.flightdate = $("#flightDate_search").val();

	
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
		$("#fjzch_search").val(params.fjzch);
		$("#jddxlx").val(params.jddxlx);
		$("#dprtcode").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
	    $("#flightDate_search").val(params.flightdate);
		
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
		obj.jddxlx = searchParam.jddxlx;
		obj.fjzch = searchParam.fjzch;
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
		obj.dprtcode = searchParam.dprtcode;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/borrow/borrowApplyList",
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
				       signByKeyword($("#keyword_search").val(),[3,4]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"13\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'jrsq'});
		      }
		    }); 
		 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.fjzch = $("#fjzch_search").val();
		 searchParam.jddxlx = $("#jddxlx").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 var flightdate = $("#flightDate_search").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.fxDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.fxDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
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
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:borrow:main:02' onClick=\"edit('" + row.id + "',"+row.zt+")\" title='修改 Edit'></i>";
				   htmlContent +="&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:borrow:main:03' onClick=\"cancel('"+ row.id + "',"+row.zt+")\" title='作废  Invalid'></i>";
			   }
				if(row.zt==2||row.zt==3){
					  htmlContent +="&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:borrow:main:04' onClick=\"shutDown('"+ row.id + "','"+row.sqdh+"',"+row.zt+")\" title='指定结束 Close'></i>";
				}
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";

			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.sqdh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.sqr)+"'>"+StringUtil.escapeStr(row.sqr)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center '>"+formatUndefine(row.sqsj).substring(0,10)+"</td>"; 
			   if(row.fjzch=="00000"){
				   htmlContent = htmlContent + "<td class='text-left'>通用Currency</td>";  
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
			   }
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(jdxd[row.jddxlx])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jddxms)+"</td>";  
	
			   htmlContent += "<td  class='text-left'>";
			   if(row.zt==9){
				   htmlContent += "<a href='javascript:void(0);' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+StringUtil.escapeStr(row.zdjsr)+"' onclick=\"viewShutDown('"+ StringUtil.escapeStr(row.sqdh)+"','"+StringUtil.escapeStr(row.zdjsrq)+"',event)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('reserveStatusEnum',row.zt);
			   }
			 
			   htmlContent +="</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdrs)+"'>"+StringUtil.escapeStr(row.zdrs)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.zdsj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }

	//查看详情
	 function viewDetail(id){
		 window.open( basePath+"/aerialmaterial/borrow/view?id=" + id+"&pageParam="+encodePageParam());
	 }
	 
	 //新增
	 function addBorrow(id){
		 window.location = basePath+"/aerialmaterial/borrow/add?&pageParam="+encodePageParam();
	 }
	 
	//编辑提订单
	 function edit(id,zt){
	 	if(zt != 1){
	 		AlertUtil.showErrorMessage("对不起,只有保存状态下的借入单才能修改!");
	 		return false;
	 	}
	 	if(checkEdit(id)){	
	 		window.location = basePath+"/aerialmaterial/borrow/edit?id=" + id+"&pageParam="+encodePageParam();
	 	}
	 }		
	 
	//作废
	 function cancel(id,zt) {
		 
	 	if(zt != 1){
	 		AlertUtil.showMessage("对不起,只有保存状态下的借入单才能作废!");
	 		return false;
	 	}
		AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/aerialmaterial/borrow/cancel",
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
	 
	 
	//指定结束
	 function shutDown(id,sqdh,zt){

	 	if(9 == zt){
	 		AlertUtil.showErrorMessage("对不起,该借入单已经指定结束!");
	 		return false;
	 	}
	 	$('#reserveId').val(id);
	 	$('#sqdh').val(sqdh);
	 	$('#zdjsyy').val("");
	 	$("#alertModalShutDown").modal("show");
	 }
	 
	//指定结束
	 function viewShutDown(sqdh,zdjsrq,e){
		 
		 var zdjsyy = $(e.target).attr("zdjsyy");
		 var zdjsr = $(e.target).attr("zdjsr");
		 
		 
	 	$('#zdjsyyview').val(zdjsyy);
	 	$('#sqdhview').val(sqdh);
	 	$('#zdjsr').val(zdjsr);
	 	$('#zdjsrq').val(zdjsrq);
	 	$("#alertModalview").modal("show");
	 }
	 
	//确认指定结束
	 function sbShutDown() {
	 	var id = $("#reserveId").val();
	 	var zdjsrid = $("#zdjsrid").val();
	 	var zdjsyy = $("#zdjsyy").val();
	 	if(null == zdjsyy || '' == zdjsyy){
	 		AlertUtil.showErrorMessage("输入指定结束原因!");
	 		return false;
	 	}
	 	var reserve = {
	 			id : id,
	 			zdjsrid : zdjsrid,
	 			zdjsyy : zdjsyy
	 		};
	 	startWait();
	 	AjaxUtil.ajax({
	 		url:basePath + "/aerialmaterial/borrow/shutDown",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(reserve),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			AlertUtil.showMessage('指定结束成功!');
	 			$("#alertModalShutDown").modal("hide");
	 			searchRevision();
	 			refreshPermission();
	 		}
	 	});
	 }
	 

	//检查提订单是否可以修改
	function checkEdit(id){
		var flag = false;
		AjaxUtil.ajax({
	 		url:basePath + "/aerialmaterial/borrow/checkEdit",
	 		type:"post",
	 		async: false,
	 		data:{
	 			'id' : id
	 		},
	 		dataType:"json",
	 		success:function(data){	
	 			flag = true;
	 		}
	 	});
	 	return flag;
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
		 
