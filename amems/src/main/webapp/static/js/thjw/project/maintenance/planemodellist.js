$(document).ready(function(){
	decodePageParam();//开始的加载默认的内容 
    Navigation(menuCode);
    
    //初始化日志功能
	logModal.init({code:'D_004'});
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
	params.dprtcode = $.trim($("#dprtcode").val());
	params.gsDjjh = $.trim($("#gs_djjh").val());
	params.isTsqk = $.trim($("#is_tsqk").val());
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
		$("#dprtcode").val(params.dprtcode);
		$("#gs_djjh").val(params.gsDjjh);
		$("#is_tsqk").val(params.isTsqk);
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
		var fjjx = $("#fjjx").val();
		if(fjjx != ""){
			searchParam.fjjx = fjjx;
			$("#fjjx").val("");
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/project/planemodeldata/selectPlaneModelList",
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
				   signByKeyword($("#keyword_search").val(),[2,20]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"22\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'ckTb'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode").val());
		 searchParam.gsDjjh = $.trim($("#gs_djjh").val());
		 searchParam.isTsqk = $.trim($("#is_tsqk").val());
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
			   var gsdjjh="";
			   if(row.gsDjjh==1){
				   gsdjjh="计划与实际取小+周期";
			   }else if (row.gsDjjh==2){
				   gsdjjh="实际+周期";
			   }else{
				   gsdjjh="计划+周期";
			   }
			      
			   htmlContent = htmlContent + "<td class='text-center fixed-column'>"+ "<div><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:planemodeldata:main:02' fjjx='"+StringUtil.escapeStr(row.fjjx)+"' onClick=\"edit(this,'"+row.dprtcode+"')\"  title='修改 Edit'></i></td>"; 
			   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.fjjx)+"'>";
			   htmlContent = htmlContent + "<a href='javascript:void(0);' fjjx='"+StringUtil.escapeStr(row.fjjx)+"' onclick=\"looked(this,'"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.fjjx)+"</a>";
			   htmlContent = htmlContent + "</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rJsfxsj)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rSsdsj)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rJcsj)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rQljxh)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rJcxh)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rWdgxh)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN1)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN2)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN3)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN4)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN5)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rN6)+"</td>";  
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rTsjk1)+"</td>"; 
			   htmlContent = htmlContent + "<td>"+formatUndefine(row.rTsjk2)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+gsdjjh+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+(row.isTsqk==1?'有':'无')+"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+(row.zt==1?'有效':'无效')+"</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.displayname)+"'>"+StringUtil.escapeStr(row.displayname)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.cjsj)+"</td>";
			  // htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.departmentName)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.dprtname)+"</td>"; 
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
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
		 $("#divSearch").find("select").each(function(){
				$(this).val("");
	     });
		 $("#dprtcode").val(zzjgid);
		 $("#keyword_search").val("");
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
		 
		//修改机型数据
		 function edit(obj,dprtcode){
			 window.location =basePath+"/project/planemodeldata/upPlaneData?fjjx="+encodeURIComponent( $(obj).attr("fjjx"))+"&dprtcode="+encodeURIComponent(dprtcode)+"&pageParam=" + encodePageParam();
		 }
		
		 //查看机型数据
		 function looked(obj,dprtcode){
			 window.open(basePath+"/project/planemodeldata/lookPlaneData?fjjx="+encodeURIComponent($(obj).attr("fjjx"))+"&dprtcode="+encodeURIComponent(dprtcode));
		 } 
		 
		 function add(){
			window.location = basePath+"/project/planemodeldata/intoPlaneData?pageParam="+encodePageParam();
		 }
		