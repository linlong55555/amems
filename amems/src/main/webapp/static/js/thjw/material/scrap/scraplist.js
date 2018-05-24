
var bflx=["","库内报废","外场报废"];
var pagination = {};
$(document).ready(function(){
	 decodePageParam();//开始的加载默认的内容 
	 Navigation(menuCode);
	 //菜单设置  自己设置
});


/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.zt = $.trim($("#zt_search").val());
	params.bflx = $.trim($("#bflx_search").val());
	params.bfsj = $.trim($("#bfsj_search").val());
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
		params.keyword = $.trim($("#keyword_search").val());
		params.dprtcode = $.trim($("#dprtcode_search").val());
		params.zt = $.trim($("#zt_search").val());
		params.bflx = $.trim($("#bflx_search").val());
		params.bfsj = $.trim($("#bfsj_search").val());
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
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/scrap/queryScrapList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
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
				   signByKeyword($("#keyword_search").val(),[2,3,4]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
			   }
		        new Sticky({tableId:'scrap_list_table'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		    var paramsMap = {};
			paramsMap.keyword = $.trim($("#keyword_search").val());
			paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
			paramsMap.zt = $.trim($("#zt_search").val());
			paramsMap.bflx = $.trim($("#bflx_search").val());
			
			var bfsj = $.trim($("#bfsj_search").val());
			if(null != bfsj && "" != bfsj){
				paramsMap.dateBegin = bfsj.substring(0,4)+"-"+bfsj.substring(5,7)+"-"+bfsj.substring(8,10);
				paramsMap.dateEnd = bfsj.substring(12,17)+"-"+bfsj.substring(18,20)+"-"+bfsj.substring(21,23);
			}
			searchParam.paramsMap = paramsMap;
		    return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr  bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr  bgcolor=\"#fefefe\">";
			   }
			      
			   if(row.zt==1||row.zt==2){                 //保存或提交时可以审核
				   htmlContent = htmlContent + 
				   "<td class='text-center fixed-column' >"
				   + "<i class='icon-foursquare color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:scrap:main:01' onClick=\"audit('"+ row.id + "')\" title='审核 Review'></i>&nbsp;&nbsp;"
				   +"</td>";
			   }else if(row.zt==10){
				   htmlContent = htmlContent + 
				   "<td class='text-center fixed-column'>"
				   + "<i class='icon-reply-all color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:scrap:main:02' onClick=\"Revoke('"+ row.id +"','"+row.bfdh + "','"+row.bflx+"')\" title='撤销 Revoke'></i>&nbsp;&nbsp;"
				   +"</td>"; 
			   }else{
				   htmlContent = htmlContent + 
				   "<td class='text-center fixed-column'>"
				   /*+ "<i class='icon-foursquare color-blue cursor-pointer checkPermission'  permissioncode='aerialmaterial:scrap:main:01' onClick=\"audit('"+ row.id + "')\" title='审核 Review'></i>&nbsp;&nbsp;"*/
				   +"</td>"; 
			   }  
			   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
			   htmlContent = htmlContent + "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.bfdh)+"</a>";
			   htmlContent = htmlContent + "</td>"; 
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bfyy)+"'>"+StringUtil.escapeStr(row.bfyy)+"</td>";  
			   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('contractStatusEnum',row.zt)+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.sprname)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.spsj).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.bfrname)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.bfsj).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(bflx[row.bflx])+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.zdrname)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
			   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.dprtname)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();       //触发按钮权限监控
	 }
	 
	//审核报废
	 function audit(id){
		 window.location =basePath+"/aerialmaterial/scrap/intoAudit?id="+id+"&pageParam="+encodePageParam();
	 }
	//查看报废
	 function viewDetail(id){
	 	window.open(basePath+"/aerialmaterial/scrap/view?id=" + id);
	 } 
	 //撤销报废
		function Revoke(id,bfdh,bflx){
			var param={}
			param.id=id;
			param.bflx=bflx;
			param.bfdh=bfdh;
			AlertUtil.showConfirmMessage("您确定要撤销吗？",{callback: function(){
				 AjaxUtil.ajax({
						url:basePath+"/aerialmaterial/scrap/Revoke",
						type: "post",
						contentType:"application/json;charset=utf-8",
						data:JSON.stringify(param),
						dataType:"json",
						success:function(data){
							finishWait();
						    AlertUtil.showMessage('撤销成功!','/aerialmaterial/scrap/main?id='+id+'&pageParam='+pageParam);
						},
					});
			}});
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
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		 $("#bflx_search").val("");
		 $("#zt_search").val("");
			
		 var zzjgid=$('#zzjgid').val();
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
