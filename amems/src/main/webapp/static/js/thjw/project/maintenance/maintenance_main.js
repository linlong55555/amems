
$(document).ready(function(){
	Navigation(menuCode,"","");
	decodePageParam();
	refreshPermission();
	//初始化日志功能
	logModal.init({code:'B_G_011'});
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
	params.jhSxrq = $.trim($("#jhSxrq_search").val());
	params.sjSxrq = $.trim($("#sjSxrq_search").val());
	params.zdrq = $.trim($("#zdrq_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.zxbs = $.trim($("#zxbs_search").val());
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
		$("#jhSxrq_search").val(params.jhSxrq);
		$("#sjSxrq_search").val(params.sjSxrq);
		$("#zdrq_search").val(params.zdrq);
		$("#dprtcode_search").val(params.dprtcode);
		$("#zxbs_search").val(params.zxbs);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

//改变状态时查询维修方案
function changeStatus(){
	goPage(1,"auto","desc");
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
		startWait();
		AjaxUtil.ajax({
			 url:basePath+"/project/maintenance/queryAllPageList",
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
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,9,10],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'maintenance_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 searchParam.keyword = keyword;
		 var jhSxrq = $.trim($("#jhSxrq_search").val());
		 var sjSxrq = $.trim($("#sjSxrq_search").val());
		 var zdrq = $.trim($("#zdrq_search").val());
		 var zdrrealname = $.trim($("#zdr_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 var zxbs = $.trim($("#zxbs_search").val());
		 paramsMap.userId = $.trim($("#userId").val());
		 paramsMap.userType = $.trim($("#userType").val());
		 if(null != jhSxrq && "" != jhSxrq){
			 var jhSxrqBegin = jhSxrq.substring(0,10)+" 00:00:00";
			 var jhSxrqEnd = jhSxrq.substring(13,23)+" 23:59:59";
			 paramsMap.jhSxrqBegin = jhSxrqBegin;
			 paramsMap.jhSxrqEnd = jhSxrqEnd;
		 }
		 if(null != sjSxrq && "" != sjSxrq){
			 var sjSxrqBegin = sjSxrq.substring(0,10)+" 00:00:00";
			 var sjSxrqEnd = sjSxrq.substring(13,23)+" 23:59:59";
			 paramsMap.sjSxrqBegin = sjSxrqBegin;
			 paramsMap.sjSxrqEnd = sjSxrqEnd;
		 }
		 if(null != zdrq && "" != zdrq){
			 var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
			 var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
			 paramsMap.zdrqBegin = zdrqBegin;
			 paramsMap.zdrqEnd = zdrqEnd;
		 }
		 if('' != zdrrealname){
			 searchParam.zdrrealname = zdrrealname;
		 }
		 if('' != zxbs){
			 searchParam.zxbs = zxbs;
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
			   htmlContent += "<td class='fixed-column text-center'>";
			   
			   if(row.zt == 1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:maintenance:main:02' onClick=\"edit('"+ row.id + "')\" title='编辑 Edit'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt == 3 && row.zxbs == 1 && row.bb == row.maxbb){
				   htmlContent += "<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project:maintenance:main:04' onClick=\"modify('"+ row.id + "')\" title='改版 Modify'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt == 1){
				   htmlContent += "<i class='icon-tumblr-sign color-blue cursor-pointer checkPermission' permissioncode='project:maintenance:main:03' onClick=\"sbProd('"+ row.id + "')\" title='提交生产 Submit'></i>&nbsp;&nbsp;";
			   }
			   if(row.fBbid != null && row.fBbid != ''){
				   htmlContent += "<i class='icon-eye-open color-blue cursor-pointer'  onClick=\"viewDiff('"+ row.id + "')\" title='查看差异 Diff'></i>&nbsp;&nbsp;";
			   }
			   
			   if(row.zt == 1){
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:maintenance:main:05' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>&nbsp;&nbsp;";
			   }
			   
			   htmlContent += "</td>";
			   
			   htmlContent = htmlContent + "<td class='text-center'>";
			   htmlContent = htmlContent + "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.wxfabh)+"</a>";
			   htmlContent = htmlContent + "</td>";  
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bb)+"' class='text-right'>"+StringUtil.escapeStr(row.bb)+"</td>"; 

			   htmlContent += "<td class='text-center'>"+indexOfTime(row.jhSxrq)+"</td>";
			   htmlContent += "<td class='text-center'>"+indexOfTime(row.sjSxrq)+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('latestLogoTwoEnum',row.zxbs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.user?row.user.displayName:'')+"' class='text-left'>"+StringUtil.escapeStr(row.user?row.user.displayName:'')+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.zdsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
//维修方案新增
function add(){
	window.location = basePath+"/project/maintenance/add?pageParam="+encodePageParam();
}
	 
//维修方案修改
function edit(id){
	if(checkEdit(id)){	
		window.location = basePath+"/project/maintenance/" + id + "/edit?pageParam=" + encodePageParam();
	}
}

//维修方案改版
function modify(id){
	if(checkModify(id)){
		window.location = basePath+"/project/maintenance/" + id + "/modify?pageParam=" + encodePageParam();
	}
}

//维修方案提交生产
function sbProd(id){
	if(checkSbProd(id)){
		window.location = basePath+"/project/maintenance/" + id + "/sbProd?pageParam=" + encodePageParam();
	}
}

//维修方案查看差异
function viewDiff(id){
	window.open(basePath+"/project/maintenance/view?id=" + id+"&pageParam=" + encodePageParam());
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/project/maintenance/viewDetail?id=" + id+"&pageParam=" + encodePageParam());
}

//作废
function del(id) {
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/project/maintenance/delete",
			type:"post",
			async: false,
			data:{
				'id' : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
		
	}});
	
}
	
//检查维修方案是否可以修改
function checkEdit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/project/maintenance/checkEdit",
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

//检查维修方案是否可以改版
function checkModify(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/checkModify",
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
	
//检查维修方案是否可以提交生产
function checkSbProd(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/checkSbProd",
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

//检查维修方案下是否存在同版本的定检项目
/*function checkFixedExists(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/checkFixedExists",
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
}*/

function formartStr(str){
	
	if(str == null || str == ''){
		return '';
	}
	if(str.length > 15){
		str = str.substring(0,15)+"...";
    }
	return str;
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
		$("#zxbs_search").val("");
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
	
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
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
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
		 
