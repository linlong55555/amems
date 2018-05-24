$(document).ready(function(){
	Navigation(menuCode);
	decodePageParam();
	//goPage(1,"auto","desc");//开始的加载默认的内容 
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
	params.hclx = $.trim($("#hclx_search").val());
	params.gljb = $.trim($("#gljb_search").val());
	params.hcly = $.trim($("#hcly_search").val());
	params.jyjg = $.trim($("#jyjg_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.jyrq = $.trim($("#jyrq_search").val());
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
		$("#hclx_search").val(params.hclx);
		$("#gljb_search").val(params.gljb);
		$("#hcly_search").val(params.hcly);
		$("#jyjg_search").val(params.jyjg);
		$("#dprtcode_search").val(params.dprtcode);
		$("#jyrq_search").val(params.jyrq );
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
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		searchParam.hclx = $.trim($("#hclx_search").val());
		searchParam.gljb = $.trim($("#gljb_search").val());
		searchParam.hcly = $.trim($("#hcly_search").val());
		searchParam.jyjg = $.trim($("#jyjg_search").val());
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/qualityCheck/queryQualityCheckList",
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
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6,8,9]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"24\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#keyword_search").val());
		
		
		paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
		 
		var jyrq = $.trim($("#jyrq_search").val());
		if(null != jyrq && "" != jyrq){
			paramsMap.dateBegin = jyrq.substring(0,4)+"-"+jyrq.substring(5,7)+"-"+jyrq.substring(8,10);
			paramsMap.dateEnd = jyrq.substring(12,17)+"-"+jyrq.substring(18,20)+"-"+jyrq.substring(21,23);
		}
		
		 searchParam.paramsMap = paramsMap;
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
			   if(row.zt==2){
				   htmlContent = htmlContent + 
				   "<td class='fixed-column text-center' style='vertical-align:middle'>"
				   + "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printQualityBarcode('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;"
				   +"</td>";
			   }
			   if(row.zt==1){
				   if(row.jydh==null){
					   htmlContent = htmlContent + 
					   "<td class='fixed-column text-center' style='vertical-align:middle'>"
					   + "<i class='icon-random color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:qualityCheck:main:01' onClick=\"Checking('"+ row.id + "')\" title='检验 Check'></i>&nbsp;&nbsp;"
					   +"</td>";
				   }else{
					   htmlContent = htmlContent + 
					   "<td class='fixed-column text-center' style='vertical-align:middle'>"
					   + "<i class='icon-random color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:qualityCheck:main:01' onClick=\"Checking('"+ row.id + "')\" title='检验 Check'></i>&nbsp;&nbsp;"
					   + "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printQualityBarcode('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;"
					   +"</td>";
				   }
			   }
			   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align:middle'><a href=\"javascript:Looked('"+ row.id + "')\">"+StringUtil.escapeStr(row.jydh)+"</a></td>"; 
			   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align:middle'><a href=\"javascript:View('"+ row.rkdid + "')\">"+StringUtil.escapeStr(row.rkdh)+"</a></td>"; 
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.sn)+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.sn)+StringUtil.escapeStr(row.pch)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb)+"</td>";
			   htmlContent = htmlContent + "<td  class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";
			   htmlContent = htmlContent + "<td  class='text-right'>"+formatUndefine(row.kcsl)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.hcly)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xkzh)+"'>"+StringUtil.escapeStr(row.xkzh)+"</td>";
			   if(row.jyjg==1){
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(DicAndEnumUtil.getEnumName('materialCheckResultEnum',row.jyjg))+"</td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>";
				   htmlContent = htmlContent +"<a href=\"javascript:CheckResult('"+ row.jgsm + "','"+ row.jydh + "')\">"+formatUndefine(DicAndEnumUtil.getEnumName('materialCheckResultEnum',row.jyjg))+"</a><br>"; 
				   htmlContent = htmlContent + "</td>";
			   }
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.jyrq).substring(0,10)+"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zt==1?"保存":"提交")+"</td>";
			   htmlContent = htmlContent + "<td >"+StringUtil.escapeStr(row.username)+' '+StringUtil.escapeStr(row.realname)+"</td>";
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.dprtname)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();       //触发按钮权限监控
	 }
	   //查看检验单
		function Looked(id) {                    
			 window.open(basePath+"/aerialmaterial/qualityCheck/Looked?id=" + id);
		}
		//查看入库单
		function View(rkdid) {    
			window.open(basePath+"/aerialmaterial/instock/view/detail/" + rkdid);
		}
		
		 //检验
		function Checking(id) {                    
			 window.location.href =basePath+"/aerialmaterial/qualityCheck/Checking?id=" + id +"&pageParam="+encodePageParam();
		}
		
		function CheckResult(jgsm,jydh){
			$("#alertModalResult").modal("show");
			$("#jydh").val(jydh);
			$("#jgsm").val(jgsm);
		}
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "asc";
		} else {
			orderType = "desc";
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderType);
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
		var zzjgid=$('#zzjgid').val();
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
		$("#gdlx_search").selected="selected";
	});
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	});
	 $("#zilei").find("select").each(function(){
			$(this).val("");
	});
	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(zzjgid);
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
		};
		function printQualityBarcode(id){
			var url=basePath+"/aerialmaterial/qualityCheck/qualityCheckOutPDF?id=" + encodeURIComponent(id)
 			+ "&dprtcode=" + encodeURIComponent($("#dprtcode_search").val());
			
			window.open(basePath+'/static/lib/pdf.js/web/pdfViewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');		
		}