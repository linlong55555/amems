
$(document).ready(function(){
	Navigation(menuCode,"","");
	decodePageParam();
	$("#setUser").click(function(){
		$("#sxjyrid").val($("#userId").val());
		$("#sxjyr").val($("#realname").val());
	});
	//初始化日志功能
	logModal.init({code:'B_G_011'});
});

var id = "";
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
			 url:basePath+"/project/confirmationofrevision/queryAllPageList",
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
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,9]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'effect_main_table'});
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
		 var zxbs = $.trim($("#zxbs_search").val());
		 var zdrrealname = $.trim($("#zdrrealname_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val());
		 paramsMap.userId = $.trim($("#userLoginId").val());
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

		 if(null != zxbs && "" != zxbs){
			 searchParam.zxbs = zxbs;
		 }
		 if(null != zdrrealname && "" != zdrrealname){
			 searchParam.zdrrealname = zdrrealname;
		 }
		 if('' != dprtcode){
			 searchParam.dprtcode = dprtcode;
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
			   htmlContent = htmlContent + "<td  class='text-center'>";
			   
			   if(row.zt == 2){
				   htmlContent = htmlContent + "<i class='icon-ok color-blue cursor-pointer' onClick=\"effective('" + row.id + "',"+row.zt+ ")\" title='确认生效 Effective'></i>"; 
			   }
			   //htmlContent = htmlContent + "&nbsp;&nbsp;<i class='icon-eye-open color-blue cursor-pointer' onClick=\"viewDetail('" + row.id + "')\" title='查看详情'></i>"; 
			   
			   htmlContent = htmlContent + "</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.jx)+"' class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>";
			   htmlContent = htmlContent + "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.wxfabh)+"</a>";
			   htmlContent = htmlContent + "</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bb)+"' class='text-right'>"+StringUtil.escapeStr(row.bb)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('latestLogoTwoEnum',row.zxbs) +"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+indexOfTime(row.jhSxrq)+"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+indexOfTime(row.sjSxrq)+"</td>";
			   /*if(3 == row.zt){
				   htmlContent = htmlContent + "<td>已确认</td>";
			   }else{
				   htmlContent = htmlContent + "<td>未确认</td>";
			   }*/
			   htmlContent += "<td title='"+(StringUtil.escapeStr(row.user?row.user.displayName:''))+"' class='text-left'>"+StringUtil.escapeStr(row.user?row.user.displayName:'')+"</td>";
			   htmlContent += "<td title='"+(StringUtil.escapeStr(row.jyr?row.jyr.displayName:''))+"' class='text-left'>"+StringUtil.escapeStr(row.jyr?row.jyr.displayName:'')+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }
	 
//打开用户列表对话框
function openUserList() {
	var dprtcode = $.trim($("#dprtcode_search").val());
 	userModal.show({
 		selected:$("#sxjyrid").val(),//原值，在弹窗中默认勾选
 		dprtcode:dprtcode,
 		callback: function(data){//回调函数
 			if(data != null){
 				var str = StringUtil.escapeStr(data.username) + " " + StringUtil.escapeStr(data.realname);
 				$("#sxjyrid").val(formatUndefine(data.id));
 				$("#sxjyr").val(str);
 			}
 		}
 	})
 }	 

	 
//维修方案确认生效
function effective(id,zt){

	if(2 != zt){
		AlertUtil.showErrorMessage("对不起,只有待生效的维修方案才能确认生效!");
		return false;
	}
	
	if(checkEffective(id)){
		$('#wxfaId').val(id);
		$("#alertModalForm").modal("show");
	}

}

//查看详情
function viewDetail(id){
	window.open(basePath+"/project/confirmationofrevision/view?id=" + id+"&pageParam=" + encodePageParam());
}

//确认生效
function sbEffective() {
	
	var wxfaId = $("#wxfaId").val();
	if(null == wxfaId || '' == wxfaId){
		AlertUtil.showErrorMessage("维修方案编号不能为空!");
		return false;
	}
	
	var sxjyrid = $("#sxjyrid").val();
	var sxjyr = $("#sxjyr").val();
	if(null == sxjyrid || '' == sxjyrid || null == sxjyr || '' == sxjyr){
		AlertUtil.showMessageCallBack({
			message : '请选择校验人',
			option : 'sxjyr',
			callback : function(option){
				$("#"+option).focus();
			}
		});
		return false;
	}
	
	startWait($("#alertModalForm"));
	AjaxUtil.ajax({
		url:basePath + "/project/confirmationofrevision/sbEffective",
		type:"post",
		async: false,
		data:{
			'wxfaId' : wxfaId,
			'sxjyrid' : sxjyrid
		},
		dataType:"json",
		modal:$("#alertModalForm"),
		success:function(data){
			finishWait($("#alertModalForm"));
			id = wxfaId;
			AlertUtil.showMessage('生效成功!');
			$("#alertModalForm").modal("hide");
			refreshPage();
		}
	});
}
	
//检查维修方案是否可以生效
function checkEffective(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/confirmationofrevision/checkEffective",
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
		$("#zxbs_search").val(0);
		
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
		};
		 
