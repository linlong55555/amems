var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
$(document).ready(function(){
		 //goPage(1,"auto","desc");//开始的加载默认的内容 
		if(tabId == 'main'){
			$("#tab_main").tab('show');
			loadMain();
		}else if(tabId == 'history'){
			$("#tab_history").tab('show');
			loadHistory();
		}else{
			id = "";
			pageParam = "";
			loadMain();// 加载采购数据
		}
	});
	var flagMain = false;
	function loadMain(){
		if(flagMain === false){
			decodePageParam();
			//flagMain = true;
		}
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
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:5};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.dprtcode=searchParam.dprtcode;
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/destruction/main",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.rows.length >0){
				   appendContentHtml(data.rows);
					// 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6]);
			   } else {
				  $("#list").empty();
				  $("#list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'lingliao'});
	      }
	    }); 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
		 searchParam.dprtcode = $.trim($("#zzjg").val());
		 return searchParam;
	 }

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\"  onclick='clickRow("+index+",this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\"  onclick='clickRow("+index+",this)' >";
		   }
		   
		   htmlContent = htmlContent + 
		   "<td class='fixed-column' class='text-right' style='vertical-align:middle' align='center'>"
		   + "<input type='checkbox' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" />"
		   + "<input type='hidden' name='destId' value='"+row.id+"'"
		   +"</td>";
		   htmlContent =htmlContent+ "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		   htmlContent = htmlContent + "<td class='fixed-column text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  		   		
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 		
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.kcsl)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.syts)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.spqx).substring(0,10)+"</td>";  
		  /* htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.sytss)+"</td>";  */
		   if(row.rkr_user != null){
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.rkr_user.displayName)+"'>"+StringUtil.escapeStr(row.rkr_user.displayName)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' ></td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-left' >"+formatUndefine(row.rksj)+"</td>";  
		   if(row.jg_dprt != null){
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	 
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
	 //信息检索
function searchRevision(){
	goPage(1,"auto","desc");
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
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
//销毁
function goEdit(){
	var stockids = [];
	$("#list").find("tr input:checked").each(function(){
			stockids.push($(this).next().val());
	});
	if(stockids.length>0){
		 window.location.href =basePath+"/aerialmaterial/destruction/add?ids="+stockids+"";
	}else{
		 AlertUtil.showErrorMessage("请选择需要销毁的数据");
	}
}
function clickRow(index,this_){
	var $checkbox1 = $("#list :checkbox[name='checkrow']:eq("+index+")");
	var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
	var checked = $checkbox1.is(":checked");
	$checkbox1.attr("checked", !checked);
	$checkbox2.attr("checked", !checked);
}

function checkRow(index,this_){
	var flag = $(this_).is(":checked");
	if(flag){
		$(this_).removeAttr("checked");
	}else{
		$(this_).attr("checked",true);
	}
}
//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}

