$(document).ready(function(){
	 goPage1(1,"auto","desc");//开始的加载默认的内容 
});


 //带条件搜索的翻页
 function AjaxGetDatasWithSearch1(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam1();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	 AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/transfer/queryTransferList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			    finishWait();
			    if(data.rows !=""){
			    	appendContentHtml1(data.rows);
			    	 new Pagination({
							renderTo : "pagination1",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								AjaxGetDatasWithSearch1(a,b,c);
							}
						}); 
					// 标记关键字
					   signByKeyword($("#keyword_search1").val(),[2,3,6,7],'#list1 tr td');
					   
				   } else {
					  $("#list1").empty();
					  $("#pagination1").empty();
					  $("#list1").append("<tr><td class='text-center' colspan=\"23\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'transferhistory_table'});
	      },
	    }); 
 }
 
 //将搜索条件封装 
 function gatherSearchParam1(){
    var searchParam = {};
    var paramsMap = {};
	paramsMap.keyword = $.trim($("#keyword_search1").val());
	paramsMap.dprtcode = $.trim($("#dprtcode1").val());
	
	var ykrq = $.trim($("#ykrq_search").val());
	if(null != ykrq && "" != ykrq){
		paramsMap.dateBegin = ykrq.substring(0,4)+"-"+ykrq.substring(5,7)+"-"+ykrq.substring(8,10);
		paramsMap.dateEnd = ykrq.substring(12,17)+"-"+ykrq.substring(18,20)+"-"+ykrq.substring(21,23);
	}
	searchParam.paramsMap = paramsMap;
	return searchParam;
 }
 function appendContentHtml1(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" >";
		   }
		   
		   htmlContent = htmlContent + "<td class='text-center fixed-column' style='vertical-align:middle'>";
		   if(row.zt!=11){
			   htmlContent = htmlContent+ "<i class='icon-reply-all color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:transfer:main:02' onClick=\"Revoke('"+ row.id + "')\" title='撤销 Revoke'></i>&nbsp;&nbsp;";
		   }
		   htmlContent = htmlContent +"</td>";
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		   htmlContent = htmlContent + "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+formatUndefine(row.ykdh)+"</a>";
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";  
		   htmlContent = htmlContent + "<td>"+formatUndefine(hclb[row.ysCklb])+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ysCkh)+"'>"+StringUtil.escapeStr(row.ysCkh)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ysCkmc)+"'>"+StringUtil.escapeStr(row.ysCkmc)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ysKwh)+"'>"+StringUtil.escapeStr(row.ysKwh)+"</td>";  
		   htmlContent = htmlContent + "<td>"+formatUndefine(hclb[row.mbCklb])+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.mbCkh)+"'>"+StringUtil.escapeStr(row.mbCkh)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.mbCkmc)+"'>"+StringUtil.escapeStr(row.mbCkmc)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.mbKwh)+"'>"+StringUtil.escapeStr(row.mbKwh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.mbSl)+"</td>";  
		   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('workOrderStateEnum',row.zt)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.username)+' '+StringUtil.escapeStr(row.realname)+"'>"+StringUtil.escapeStr(row.username)+' '+StringUtil.escapeStr(row.realname)+"</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.ykrq).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ykyy)+"'>"+StringUtil.escapeStr(row.ykyy)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.displayname)+"'>"+StringUtil.escapeStr(row.displayname)+"</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.dprtname)+"</td>"; 
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list1").empty();
	   $("#list1").html(htmlContent);
	   refreshPermission();       //触发按钮权限监控
 }
 
//查看详情
 function viewDetail(id){
 	window.open(basePath+"/aerialmaterial/transfer/view?id=" + encodeURIComponent(id));
 } 
 
 //撤销移库操作
function Revoke(id){
	AlertUtil.showConfirmMessage("您确定要撤销吗？",{callback: function(){
	   AjaxUtil.ajax({
			type : "post",
			url : basePath+"/aerialmaterial/transfer/Revoke?id="+id,
			dataType : "json",
			success : function(data) {
				if(data>0){
					finishWait();
					AlertUtil.showMessage('撤销成功！');
					searchRevision1();
				}else if(data==-1){
					 AlertUtil.showErrorMessage('移库数量已经改变，不可撤销！');
				}else{
					finishWait();
					AlertUtil.showMessage('该移库单已更新，请刷新后再进行操作!');
				}
			}
	   });
	}});
}
//字段排序
function orderBy1(obj, _obj) {
	$obj = $("#transferhistory_table th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#transferhistory_table .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#transferhistory_table .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#pagination1 li[class='active']").text();
	goPage1(currentPage,obj, orderType);
}


	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage1(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch1(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevision1(){
	goPage1(1,"auto","desc");
}
	
//搜索条件重置
function searchreset1(){
	$("#divSearch1").find("input").each(function() {
	$(this).attr("value", "");
});

$("#divSearch1").find("textarea").each(function(){
	$(this).val("");
});

 $("#divSearch1").find("select").each(function(){
		$(this).val("");
	});
 var zzjgid=$('#zzjgid').val();
 $("#keyword_search1").val("");
 $("#dprtcode1").val(zzjgid);
	}
 
	//搜索条件显示与隐藏 
function search1() {
	if ($("#divSearch1").css("display") == "none") {
		$("#divSearch1").css("display", "block");
		$("#icon1").removeClass("icon-caret-down");
		$("#icon1").addClass("icon-caret-up");
	} else {
		$("#divSearch1").css("display", "none");
		$("#icon1").removeClass("icon-caret-up");
		$("#icon1").addClass("icon-caret-down");
	}
}
		
		
		 
	
	
