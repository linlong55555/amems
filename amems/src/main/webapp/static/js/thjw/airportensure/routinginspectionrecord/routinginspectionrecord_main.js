$(document).ready(function(){
	
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
	 refreshPermission();
	 //初始化日志功能
	 logModal.init({code:'B_J_002'});
});

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.pagination = pagination;
	obj.keyword = $.trim($("#keyword_search").val());	
	obj.isYhxs = searchParam.isYhxs;
	obj.dprtcode = searchParam.dprtcode;
	if(id != ""){
		obj.id = id;
		id = "";
	}
	startWait();
	
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/routinginspectionrecord/routinginspectionrecordList",
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
					   signByKeyword($("#keyword_search").val(),[2,3]);
					   
				   } else {
					  $("#routinginspectionrecordList").empty();
					  $("#pagination").empty();
					  $("#routinginspectionrecordList").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
	 
	
}

//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.isYhxs = $("#isYhxs").val();
	 searchParam.dprtcode = $("#dprtcode").val();
	 
	 return searchParam;
}
function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());	
	 params.isYhxs=$.trim($("#isYhxs").val());
	 params.dprtcode=$.trim($("#dprtcode").val());
	 pageParam.params=params;
	 pageParam.pagination=pagination;
	 return Base64.encode(JSON.stringify(pageParam));
}
function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
		
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   
		   htmlContent =htmlContent+"<td class='text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='airportensure:routinginspectionrecord:main:02' onClick=\"edit('"
				+ row.id + "')\" title='修改 Edit '></i>&nbsp;&nbsp;";
		   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='airportensure:routinginspectionrecord:main:03' onClick=\"invalid('"
				+ row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;</td>";
		   htmlContent = htmlContent + "<td class='text-center'><a href=\"javascript:view('"+row.id+"')\">"+StringUtil.escapeStr(row.xjbh)+"</a></td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xsrmc)+"'>"+StringUtil.escapeStr(row.xsrmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.xsrq).substring(0,10))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.kssj)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jssj)+"</td>";  
		   
		   if(row.isYhxs==1){
			   htmlContent = htmlContent + "<td class='text-center'>是</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>否</td>";  
		   }
		   if(row.whr_user==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.whr_user.displayName)+"'>"+StringUtil.escapeStr(row.whr_user.displayName)+"</td>";  
		   }
		   if(row.whbm_dprt==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.whbm_dprt.dprtname)+"</td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-center' >"+formatUndefine(row.whsj)+"</td>";  
		   if(row.jg_dprt==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   }
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#routinginspectionrecordList").empty();
	   $("#routinginspectionrecordList").html(htmlContent);
	   refreshPermission();
}


 
///字段排序
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
function edit(id){
	window.location.href =basePath+"/airportensure/routinginspectionrecord/edit?id="+id+"&pageParam="+encodePageParam();
}
function view(id){
	//清空明细
	var xjxmbhList=["0101","0102","0103","0104","0105","0201","0301","0302","0401","0501","0601","0701","0801"];
	for(var i=0;i<xjxmbhList.length;i++){
		var inputName='#detailId'+xjxmbhList[i];
		var selectName='#detail'+xjxmbhList[i];
		var textareaName='#detailBz'+xjxmbhList[i];
		var detailPxfjName='#detailPxfj'+xjxmbhList[i];
		$(inputName).val("");
		$(selectName).val("1");
		$(textareaName).val("");
		$(detailPxfjName).empty();
	}
	//加载明细
	loadingDetail(id);
	$('#alertModalView').modal('show');
	
}

//加载明细
function loadingDetail(id){
	var routinginspectionrecordId=id;
	var obj={};
	obj.mainid=routinginspectionrecordId;
	AjaxUtil.ajax({
		url:basePath+"/airportensure/routinginspectionrecord/routinginspectionrecordDetailList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			appendContentHtmlDetail(data.routinginspectionrecordDetailList);
		}
	});
}
//回显明细
function appendContentHtmlDetail(list){
	 $.each(list,function(index,row){
		 var selectName='#detail'+row.xjxmbh;
		 var textareaName='#detailBz'+row.xjxmbh;
		 var detailPxfjName='#detailPxfj'+row.xjxmbh;
		 var detailIdName='#detailId'+row.xjxmbh;
		 $(selectName).val(row.xjxmbs);
		 $(textareaName).val(row.xjxmbz);
		 $(detailIdName).val(row.id);
		 //循环回显附件
		 $.each(row.orderAttachmentList,function(index,a){
			 
		 var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+a.id+'\'>';
		   trHtml = trHtml+"<td title='"+StringUtil.title(StringUtil.escapeStr(a.yswjm))+"'>"
		   +'<input type="hidden" name="attachmentId" value="" ><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(a.yswjm)+'\'><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(a.wbwjm)+'\'><input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(a.nbwjm)+'\'><input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(a.cflj)+'\'><input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(a.wjdx)+'\'>'
		   +"<a href=\"javascript:downloadfile('"+a.id+"')\">"+"　"+StringUtil.subString(StringUtil.escapeStr(a.yswjm))+'</a></td>';
		   trHtml = trHtml+'</tr>';
		   $(detailPxfjName).append(trHtml);
		 });
	 });
}
//附件下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
	//window.open(basePath + "/common/orderDownfile/" + id);
}
//作废
function invalid(id){
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		var obj={};
		obj.id=id;
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/airportensure/routinginspectionrecord/invalidRoutinginspectionrecord",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			$("#alertModalAdd").modal("hide");
	 			AlertUtil.showMessage('操作成功!');
	 			refreshPage();
	 		}
	 	});
	 }});
	
}
function add(){
	window.location = basePath+"/airportensure/routinginspectionrecord/add?pageParam="+encodePageParam();
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
		$("#isYhxs").val(params.isYhxs);
		$("#dprtcode").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}