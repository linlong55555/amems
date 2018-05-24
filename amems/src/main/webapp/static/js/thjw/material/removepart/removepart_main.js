var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
$(document).ready(function(){
	Navigation(menuCode);
	// goPage(1,"auto","desc");//开始的加载默认的内容 
	 datepicker();
	 initStoreSelect1();
	 initStoreSelect();
	 initStoreSelect2();
	 refreshPermission();
	 decodePageParam();
});

function initStoreSelect(){
	var html = "";
	var dprtcode = $("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if(row.cklb==3 && dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckh").html(html);
}

function initStoreSelect1(){
	var cklbs=[0,4,5,6,7,8];
	var html = "";
	var dprtcode = $("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckid").html(html);
}

function initStoreSelect2(){
	var cklbs=[2];
	var html = "";
	var dprtcode = $("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#bfck").html(html);
}

function datepicker(){
	$('.date-picker').datepicker( 'setDate' , new Date());
}

var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());
	params.keyword = $.trim($("#keyword_search").val());
	params.hclx = $("#hclx").val();
	
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
		$("#dprtcode").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
	    $("#hclx").val(params.hclx);
		
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
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.hclx = searchParam.hclx;
		obj.dprtcode = searchParam.dprtcode;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/removepart/removepartList",
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
				       signByKeyword($("#keyword_search").val(),[2,3,4,5,6,7,8]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'cxj'});
		      }
		    }); 
		 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.hclx = $("#hclx").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   var kysl = (row.kcsl||0)-(row.djsl||0);
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
			   htmlContent += "<i class='icon-wrench color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:removepart:main:01' onClick=\"send('" + row.id + "',"+kysl+")\" title='送待修库 Edit'></i>";
			   htmlContent +="&nbsp;&nbsp;<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:removepart:main:02' onClick=\"scrap('"+ row.id + "',"+kysl+")\" title='报废  Invalid'></i>";
			   htmlContent +="&nbsp;&nbsp;<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:removepart:main:03' onClick=\"storage('"+ row.id + "',"+kysl+")\" title='入库 Stock In'></i>";
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center fixed-column'>"+(index+1)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left fixed-column'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 			
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 		
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+kysl+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   TableUtil.addTitle("#list tr td");
		   refreshPermission();
	 }

	//送修
	 function send(id,kcsl) {
			$('#storageid1').val(id);
		 	$('#storagekcsl1').val(kcsl);
		 	$('#rukusl').val(kcsl);
		 	$("#alertModal1").modal("show");
		 	 onchange1();
	 }
	 //调用库位号
	 function onchange1(){
		if($("#ckh").val()!=""){
			var storeId = $("#ckh").val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					$("#kwh").html(kwOptionHtml);
				}
			}
		}else{
			var htmlContent1 = '';
			htmlContent1 +="";
			$("#kwh").html(htmlContent1);
		}
	 }
	//确认送修
	 function sbStorage1() {
	 	var id = $("#storageid1").val();
	 	var ckid = $("#ckh").val();
	 	var kwid = $("#kwh").val();
	 	var kcsl = $("#storagekcsl1").val();
	 	var rksj = $("#rksj1").val();
		if(null == ckid || '' == ckid){
	 		AlertUtil.showMessage('请选择仓库号!');
	 		return false;
	 	}
		if(null == kwid || '' == kwid){
	 		AlertUtil.showMessage('请选择库位号!');
	 		return false;
	 	}
		 if(null == kcsl || '' == kcsl||parseInt(kcsl)<=0){
			 AlertUtil.showErrorMessage("请输入入库数量!");
			 return false;
		 }
		 
		 if(parseInt(kcsl)>parseInt($("#rukusl").val())){
			 AlertUtil.showErrorMessage("入库数量不能大于库存数量!");
			 return false;
		 }
	 
	 	var reserve = {
	 			id : id,
	 			ckid : ckid,
	 			kwid : kwid,
	 			kcsl : kcsl,
	 			rksj : rksj
	 		};
	 	startWait($("#alertModal1"));
	 	AjaxUtil.ajax({
	 		url:basePath + "/aerialmaterial/removepart/senda",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(reserve),
	 		dataType:"json",
	 		modal:$("#alertModal1"),
	 		success:function(data){
	 			finishWait($("#alertModal1"));
	 			AlertUtil.showMessage('入库成功!');
	 			$("#alertModal1").modal("hide");
	 			decodePageParam();
	 			refreshPermission();
	 		}
	 	});
	 }
	 
	 
	 
		//检查是否可以送修
		function checkEdit(id){
			var flag = false;
			AjaxUtil.ajax({
		 		url:basePath + "/aerialmaterial/removepart/checkEdit",
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
	 
	//报废
	 function scrap(id,kcsl){
	 	$('#scrapid').val(id);
	 	$('#baofsl').val(kcsl);
	 	$('#scrapkcsl').val(kcsl);
	 	$("#alertModalScrap").modal("show");
	 	 onchange3();
	 }
	 //调用库位号
	 function onchange3(){
		if($("#bfck").val()!=""){
			var storeId = $("#bfck").val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					$("#bfkw").html(kwOptionHtml);
				}
			}
		}else{
			var htmlContent1 = '';
			htmlContent1 +="";
			$("#bfkw").html(htmlContent1);
		}
	 }
	 //确认报废
	 function sbScrap() {
		 var id = $("#scrapid").val();
		 var kcsl = $("#scrapkcsl").val();
		 var bfyy = $("#bfyy").val();
		 var bfck = $("#bfck").val();
		 var bfkw = $("#bfkw").val();
		 
		if(null == bfck || '' == bfck){
	 		AlertUtil.showMessage('请选择仓库!');
	 		return false;
	 	}
		if(null == bfkw || '' == bfkw){
	 		AlertUtil.showMessage('请选择库位号!');
	 		return false;
	 	}
		 
		 if(null == kcsl || '' == kcsl||parseInt(kcsl)<=0){
			 AlertUtil.showErrorMessage("报废数量不能输入0或者为空!");
			 return false;
		 }
		 if(parseInt(kcsl)>parseInt($("#baofsl").val())){
			 AlertUtil.showErrorMessage("报废数量不能大于拆下件数量!");
			 return false;
		 }
		 
		 if(null == bfyy || '' == bfyy){
			 AlertUtil.showErrorMessage("请输入报废原因!");
			 return false;
		 }
		 var reserve = {
				 id : id,
				 bfyy : bfyy,
				 kcsl : kcsl,
				 ckid : bfck,
	 			 kwid : bfkw
		 };
		 startWait($("#alertModalScrap"));
		 AjaxUtil.ajax({
			 url:basePath + "/aerialmaterial/removepart/scrap",
			 contentType:"application/json;charset=utf-8",
			 type:"post",
			 async: false,
			 data:JSON.stringify(reserve),
			 dataType:"json",
			 modal:$("#alertModalScrap"),
			 success:function(data){
				 finishWait($("#alertModalScrap"));
				 AlertUtil.showMessage('报废成功!');
				 $("#alertModalScrap").modal("hide");
				 decodePageParam();
				 refreshPermission();
			 }
		 });
	 }
	 
	//入库
	 function storage(id,kcsl){
	 	$('#storageid').val(id);
	 	$('#storagekcsl').val(kcsl);
	 	$('#rukusl').val(kcsl);
	 	$("#alertModalStorage").modal("show");
	 	 onchange2();
	 }
	 
	 //调用库位号
	 function onchange2(){
		if($("#ckid").val()!=""){
			var storeId = $("#ckid").val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
						
						kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					$("#kwid").html(kwOptionHtml);
				}
			}
		}else{
			var htmlContent1 = '';
			htmlContent1 +="";
			$("#kwid").html(htmlContent1);
		}
	 }
	 
	//确认入库
	 function sbStorage() {
	 	var id = $("#storageid").val();
	 	var ckid = $("#ckid").val();
	 	var kwid = $("#kwid").val();
	 	var kcsl = $("#storagekcsl").val();
	 	var rksj = $("#rksj").val();
		if(null == ckid || '' == ckid){
	 		AlertUtil.showMessage('请选择仓库号!');
	 		return false;
	 	}
		if(null == kwid || '' == kwid){
	 		AlertUtil.showMessage('请选择库位号!');
	 		return false;
	 	}
	 	if(null == kcsl || '' == kcsl||parseInt(kcsl)<=0){
	 		AlertUtil.showMessage('请输入入库数量!');
	 		return false;
	 	}
	 	if(parseFloat(kcsl) > parseFloat($("#rukusl").val())){
	 		AlertUtil.showMessage('入库数量不可大于可用数量!');
	 		return false;
	 	}
	 	var reserve = {
	 			id : id,
	 			ckid : ckid,
	 			kwid : kwid,
	 			kcsl : kcsl,
	 			rksj : rksj
	 		};
	
	 	startWait($("#alertModalStorage"));
	 	AjaxUtil.ajax({
	 		url:basePath + "/aerialmaterial/removepart/storage",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(reserve),
	 		dataType:"json",
	 		modal:$("#alertModalStorage"),
	 		success:function(data){
	 			finishWait($("#alertModalStorage"));
	 			AlertUtil.showMessage('入库成功!');
	 			$("#alertModalStorage").modal("hide");
	 			decodePageParam();
	 			refreshPermission();
	 		}
	 	});
	 }
	 


	 
	//字段排序
	function orderBy(obj, _obj) {
		$obj = $("#cxj th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#cxj .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#cxj .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
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
		 
		 
			//只能输入数字和小数
		 function clearNoNum(obj){
		    	//先把非数字的都替换掉，除了数字
		        obj.value = obj.value.replace(/[^\d.:]/g,"");
		    	
		    	if(obj.value.indexOf(".") != -1){
		    		if(obj.value.indexOf(":") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoDate(obj);
		    		}
		    	}
		    	if(obj.value.indexOf(":") != -1){
		    		if(obj.value.indexOf(".") != -1){
		    			obj.value = obj.value.substring(0,obj.value.length -1);
		    		}else{
		    			clearNoNumTwoColon(obj);
		    		}
		    	}
		    	
		    	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		      		 if(obj.value.substring(1,2) != "." && obj.value.substring(1,2) != ":"){
		      			obj.value = 0;
		      		 }
		      	 }
		 }
		 
		//只能输入数字和小数,保留两位,小数后不能超过59
		 function clearNoNumTwoDate(obj){
		 	 //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d.]/g,"");
		     //必须保证第一个为数字而不是.
		     obj.value = obj.value.replace(/^\./g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\.{2,}/g,".");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		     
		     var str = obj.value.split(".");
		     if(str.length > 1){
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 59){
		        		 value = str[0] +".59";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +"." + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
		 }
		 
		//只能输入数字和冒号,保留两位,冒号后不能超过59
		 function clearNoNumTwoColon(obj){
		 	 //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d:]/g,"");
		     //必须保证第一个为数字而不是:
		     obj.value = obj.value.replace(/^\:/g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\:{2,}/g,":");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(":","$#$").replace(/\:/g,"").replace("$#$",":");
		     
		     var str = obj.value.split(":");
		     if(str.length > 1){
		    	 var value = obj.value;
		    	 if(str[1].length == 2){
		    		 if(str[1] > 59){
		        		 value = str[0] +":59";
		        	 }
		    	 }
		    	 if(str[1].length > 2){
		    		 value = str[0] +":" + str[1].substring(0,2);
		    	 }
		    	 obj.value = value;
		     }
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
