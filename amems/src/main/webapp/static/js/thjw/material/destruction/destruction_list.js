var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
		/**
		 * 加载航材领用历史记录
		 */
		var flagHistory = false;
		function loadHistory(){
			if(flagHistory === false){
				destructionGoPage(1,"auto","desc");//开始的加载默认的内容 
			}
		}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearchDestruction(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParamDestruction();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.dprtcode=searchParam.dprtcode;
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/destruction/list",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtmlDestruction(data.rows);
				   var page_ = new Pagination({
						renderTo : "historyPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearchDestruction(a,b,c);
						}//,
						//controller: this_
					});
					// 标记关键字
				   signByKeyword($("#keyword_search_destruction").val(),[2,4,5,7]);
			   } else {
				  $("#destructionList").empty();
				  $("#destructionList").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'llls'});
	      }
	    }); 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParamDestruction(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search_destruction").val());//关键字查询
		 searchParam.dprtcode = $.trim($("#destzzjg").val());
		 return searchParam;
	 }

function appendContentHtmlDestruction(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\"  onclick='clickRow("+index+",this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\"  onclick='clickRow("+index+",this)' >";
		   }
		   
		   htmlContent = htmlContent + 
		   "<td class='fixed-column' class='text-right' style='vertical-align:middle' align='center'>";
		   if(row.zt==1){
			   htmlContent = htmlContent +  "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:destruction:main:02' onClick=\"edit('"+ row.id + "');\" title='修改 Edit'></i>&nbsp;&nbsp;"
		   }
		   if(row.zt==2){
			   htmlContent = htmlContent + "<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:destruction:main:03' onClick=\"undo('"+ row.id + "','"+ row.zt + "');\" title='撤销  Undo'></i>&nbsp;&nbsp;"
		   }
		   if(row.zt==1){
			   htmlContent = htmlContent +  "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:destruction:main:04' onClick=\"invalid('"+ row.id + "','"+ row.zt + "');\" title='作废 Delete'></i>"
		   }
		   htmlContent = htmlContent + "</td>";
		   htmlContent =htmlContent+ "<td class='fixed-column' style='vertical-align: middle;'  align='center'><a href=\"javascript:selectDestruction('"+row.id+"')\">"+formatUndefine(row.xhdh)+"</a></td>";  
		   htmlContent = htmlContent + "<td class=' text-center' >"+StringUtil.escapeStr(row.xhrq).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.xhr_user.displayName)+"'>"+StringUtil.escapeStr(row.xhr_user.displayName)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' >"+StringUtil.escapeStr(row.ztText)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr_user.displayName)+"'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.bm_dprt.dprtname)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#destructionList").empty();
	   $("#destructionList").html(htmlContent);
	 
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function destructionGoPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchDestruction(pageNumber,sortType,sequence);
}	
	 //信息检索
function searchRevisionDestruction(){
	destructionGoPage(1,"auto","desc");
}
	
 //回车事件控制
 document.onkeydown = function(event){
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			if($("#workOrderNum").is(":focus")){
				$("#homeSearchWorkOrder").click();      
			}
		}
	};
 //查看跳转
function selectDestruction(id,dprtcode){
	window.open(basePath+"/aerialmaterial/destruction/view?id="+id+"");
}
//字段排序
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
	destructionGoPage(currentPage,obj,orderStyle.split("_")[1]);
}
//刷新页面
function refreshPage(){
	destructionGoPage(pagination.page, pagination.sort, pagination.order);
}

//修改
function edit(id){
	window.location.href =basePath+"/aerialmaterial/destruction/edit?id="+id+"";
}
//撤销
function undo(id,zt){
	 AlertUtil.showConfirmMessage("确定要撤销吗？", {callback: function(){
		 if(zt==2){
			 //alert("只能关闭已提交状态的技术通告");
			 AjaxUtil.ajax({
				 url:basePath + "/aerialmaterial/destruction/undoDestruction",
				 type:"post",
				 async: false,
				 data:{
					 'id' : id
				 },
				 dataType:"json",
				 success:function(data){
					 if(data.state=="success"){
						 AlertUtil.showErrorMessage(data.message);
						 loadHistory();
					 }else{
						 AlertUtil.showErrorMessage(data.message);
					 }
				 }
			 });
			 
		 }else{
			 AlertUtil.showErrorMessage('只能撤销提交状态的销毁单!');
		 }
		 
 }});
}
//作废
function invalid(id,zt){
	 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		 if(zt==1){
			 //alert("只能关闭已提交状态的技术通告");
			 AjaxUtil.ajax({
				 url:basePath + "/aerialmaterial/destruction/deleteDestruction",
				 type:"post",
				 async: false,
				 data:{
					 'id' : id
				 },
				 dataType:"json",
				 success:function(data){
					 if(data.state=="success"){
						 AlertUtil.showErrorMessage(data.message);
						 loadHistory();
					 }else{
						 AlertUtil.showErrorMessage(data.message);
					 }
				 }
			 });
			 
		 }else{
			 AlertUtil.showErrorMessage('只能作废保存状态的销毁单!');
		 }
		 
 }});
}

//字段排序
function destructionOrderBy(obj) {
	var orderStyle = $("#" + obj + "_destructionOrder").attr("class");
	$("th[id$=_destructionOrder]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "destructionOrder").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc")>=0) {
		$("#" + obj + "_" + "destructionOrder").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "destructionOrder").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_destructionOrder").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	destructionGoPage(currentPage,obj,orderStyle.split("_")[1]);
}
