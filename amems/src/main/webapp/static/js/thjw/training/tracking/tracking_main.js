	var yjtsJb1 = '';
	var yjtsJb2 = '';
	var yjtsJb3 = '';
	var ispx = [];
	 ispx[0] = "否";
	 ispx[1] = "是";
	var choseAllIds=[];
$(document).ready(function(){
	goPage(1,"auto","desc");
});
//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("quality_check_list_table");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
	 paramsMap.kcKeyword = $.trim($("#kcKeyword").val());
	 paramsMap.ryKeyword = $.trim($("#ryKeyword").val());
	 var sjKsrq = $.trim($("#sjKsrq").val());
	 var xcpxrq = $.trim($("#xcpxrq").val());
	 if(null != sjKsrq && "" != sjKsrq){
		 var sjKsrqBegin = sjKsrq.substring(0,10)+" 00:00:00";
		 var sjKsrqEnd = sjKsrq.substring(13,23)+" 23:59:59";
		 paramsMap.sjKsrqBegin = sjKsrqBegin;
		 paramsMap.sjKsrqEnd = sjKsrqEnd;
	 }
	 if(null != xcpxrq && "" != xcpxrq){
		 var xcpxrqBegin = xcpxrq.substring(0,10)+" 00:00:00";
		 var xcpxrqEnd = xcpxrq.substring(13,23)+" 23:59:59";
		 paramsMap.xcpxrqBegin = xcpxrqBegin;
		 paramsMap.xcpxrqEnd = xcpxrqEnd;
	 }
	 paramsMap.is_ypx = $.trim($("#is_ypx").val());
	 searchParam.paramsMap=paramsMap;
	 searchParam.dprtcode=$("#zzjg").val();
	 
	 return searchParam;
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/training/tracking/main",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			    yjtsJb1 =data.monitorsettings.yjtsJb1;
				yjtsJb2 =data.monitorsettings.yjtsJb2;
				yjtsJb3 =data.monitorsettings.yjtsJb3;
			   appendContentHtml(data.rows,data.monitorsettings);
			   var page_ = new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});
			// 标记关键字
			   signByKeyword($("#keyword_search").val(),[17,18,19,20]);
		   } else {
			  $("#planPersonlist").empty();
			  $("#pagination").empty();
			  $("#planPersonlist").append("<tr class='text-center'><td colspan=\"25\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'quality_check_list_table'});
     }
   }); 
	
}
//获取预警颜色
function getWarningColor(syts,zt){
	if(syts == null || syts==""){
		return "";
	}
	if(yjtsJb1 < parseInt(Number(syts)) && parseInt(Number(syts)) <= yjtsJb2){
		//黄色
		/*return "<i class='icon-circle' style='font-size:16px;color:#ffce43;' />";*/
		return "<i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + warningColor.level2+ "' />";
	}
	if(parseInt(Number(syts)) <= yjtsJb1){
		//红色
		/*return "<i class='icon-circle' style='font-size:16px;color:#cf2a27;'/>";*/
		return "<i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + warningColor.level1+ "' />";
	}
	return "";
}
//行变色
function showChildTR(obj){
	//行变色
	$(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
	new Sticky({tableId:'quality_check_list_table'});
}

//岗位
/*function getgw(sjgw,zdid){
	var htmlContent='';
	if (sjgw != null && sjgw != "") {
		var strs = new Array(); // 定义一数组
		strs = sjgw.split(","); // 字符分割
		for (i = 0; i < strs.length; i++) {
			var gwid = strs[i].split("#_#")[0];
			var gwName = strs[i].split("#_#")[1];
			
			if (i == 1) {
				htmlContent = htmlContent
				+ "　<i class='icon-caret-down' id='"
				+ zdid
				+ "icon' onclick=vieworhideWorkContent('"
				+ zdid + "')></i><div id='"
				+ zdid
				+ "' style='display:none'>";
			}

			htmlContent = htmlContent +"<a href='javascript:void(0);' onclick=\"selectByzlbh('"+gwid+"')\">"+StringUtil.escapeStr(gwName)+"</a>";
			if (i != 0) {
				htmlContent = htmlContent + "<br>";

			}
			if (i != 0 && i == strs.length - 1) {
				htmlContent = htmlContent + "</div>";
			}

		}
	}
	return htmlContent;
}
function vieworhideWorkContent(id) {
	 new Sticky({tableId:'quality_check_list_table'});
	var flag = $("#" + id).is(":hidden");
	if (flag) {
		$("#" + id).fadeIn(500);
		$("#" + id + 'icon').removeClass("icon-caret-down");
		$("#" + id + 'icon').addClass("icon-caret-up");
	} else {
		$("#" + id).hide();
		$("#" + id + 'icon').removeClass("icon-caret-up");
		$("#" + id + 'icon').addClass("icon-caret-down");
	}

}
function vieworhideWorkContentAll(){
	var obj = $("th[name='th_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<choseAllIds.length;i++){
		 //var flag = $("#"+choseAllIds[i]).is(":hidden");
		 if(flag){
			 $("#"+choseAllIds[i]).fadeIn(500);
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
		 }else{
			 $("#"+choseAllIds[i]).hide();
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
		 }
	 }
	 new Sticky({tableId:'quality_check_list_table'});
}*/
function appendContentHtml(list,monitorsettings){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		  
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\" onclick='showChildTR(this)'>";
		   } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\" onclick='showChildTR(this)'>";
		   }
		   htmlContent += "<td class='text-center fixed-column' title='当剩余天数小于等于"+yjtsJb1+"时，红色标识显示。当剩余天数小于"+yjtsJb2+"大于"+yjtsJb1+"时，黄色标识显示。当剩余天数为空时，不显示任何标识。' >"+getWarningColor(row.paramsMap.sy,row.zt)+"</td>"; 
		   
		   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.rybh))+"'><a href=\"javascript:ryView('"+row.paramsMap.mid+"')\">"+StringUtil.escapeStr(formatUndefine(row.paramsMap.rybh))+"</a></td>";  
		   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.mxm))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.mxm))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcbh))+"'><a href=\"javascript:courseView('"+row.paramsMap.cid+"')\">"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcbh))+"</a></td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcmc))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"</td>";  
		   
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(ispx[row.paramsMap.is_ypx]))+"'>"+StringUtil.escapeStr(formatUndefine(ispx[row.paramsMap.is_ypx]))+"</td>";  
		   //htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.sjgw))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.sjgw))+"</td>";
		   var sjgw="";
		   if(row.paramsMap.sjgw){
			   var gw = row.paramsMap.sjgw.split(",");
			   $.each(gw,function(index,row){
					var tdArr = row.split("#_#");
					sjgw +=  tdArr[1] + "</br>";
				});
		   }
		   htmlContent = htmlContent + "<td class='text-center' title='"+sjgw.replaceAll("</br>","\n")+"'>"+sjgw+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.sjKsrq).substring(0,10))+"'><a href=\"javascript:pxjhView('"+(row.paramsMap.scpxjhid?row.paramsMap.scpxjhid:'')+"')\">"+StringUtil.escapeStr(formatUndefine(row.sjKsrq).substring(0,10))+"</a></td>";  
		   var str='';
		   if(row.paramsMap.zqdw==1){
			   str=row.paramsMap.zqz+"天"
		   }else if(row.paramsMap.zqdw==2){
			   str=row.paramsMap.zqz+"月"
		   }else if(row.paramsMap.zqdw==3){
			   str=row.paramsMap.zqz+"年"
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+str+"'>"+str+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center' title='"+(row.paramsMap.fxqx?StringUtil.escapeStr(formatUndefine(row.paramsMap.fxqx).substring(0,10)):'')+"'>"+(row.paramsMap.fxqx?StringUtil.escapeStr(formatUndefine(row.paramsMap.fxqx).substring(0,10)):'')+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.xcpxrq).substring(0,10))+"'>"+StringUtil.escapeStr(formatUndefine(row.xcpxrq).substring(0,10))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' title='"+(row.paramsMap.sy!=null?parseInt(row.paramsMap.sy):'')+"'>"+(row.paramsMap.sy!=null? parseInt(row.paramsMap.sy) :'')+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.pxjh==null?"":row.paramsMap.pxjh))+"'><a href=\"javascript:pxjhView('"+(row.paramsMap.pxjhid?row.paramsMap.pxjhid:'')+"')\">"+StringUtil.escapeStr(formatUndefine(row.paramsMap.pxjh==null?"":row.paramsMap.pxjh))+"</a></td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.pxlb))+"'>"+StringUtil.escapeStr(formatUndefine(row.pxlb))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.sjks))+"'>"+StringUtil.escapeStr(formatUndefine(row.sjks))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.pxxs))+"'>"+StringUtil.escapeStr(formatUndefine(row.pxxs))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ksxs))+"'>"+StringUtil.escapeStr(formatUndefine(row.ksxs))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jsxm))+"'>"+StringUtil.escapeStr(formatUndefine(row.jsxm))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.pxgh))+"'>"+StringUtil.escapeStr(formatUndefine(row.pxgh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.cql))+"'>"+StringUtil.escapeStr(formatUndefine(row.cql))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.cj))+"'>"+StringUtil.escapeStr(formatUndefine(row.cj))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zs))+"'>"+StringUtil.escapeStr(formatUndefine(row.zs))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.khjg==null?"":row.khjg==0?"未通过":"通过")+"'>"+StringUtil.escapeStr(row.khjg==null?"":row.khjg==0?"未通过":"通过")+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+(row.paramsMap?StringUtil.escapeStr(formatUndefine(row.paramsMap.dprtname)):'')+"'>"+(row.paramsMap?StringUtil.escapeStr(formatUndefine(row.paramsMap.dprtname)):'')+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#planPersonlist").empty();
	   $("#planPersonlist").html(htmlContent);
	   
	   refreshPermission();
	 
}

function courseView(id){
	window.open(basePath+"/training/course/view?id=" + id);
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
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
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
}
//搜索条件重置
function searchreset(){
	 var zzjgid=$('#zzjgid').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#kcKeyword").val("");
	 $("#ryKeyword").val("");
	 $("#zzjg").val(zzjgid);
	 $("#yjjb").val(yjts);
	 changeContent();
}

/**
 * 导出Excel
 */
/*function exportExcel() {
	 var keyword = $.trim($("#keyword_search").val());//关键字查询
	 var zt = $.trim($("#zt").val());
	 var hclx = $.trim($("#hclx").val());
	 var dprtcode = $.trim($("#zzjg").val());
	 var xhrq = $.trim($("#xhrq").val());
	 var xhrqBegin='';
	 var xhrqEnd='';
	 if(null != xhrq && "" != xhrq){
		 var xhrqBegin = xhrq.substring(0,10)+" 00:00:00";
		 var xhrqEnd = xhrq.substring(13,23)+" 23:59:59";
	 }
	window.open(basePath+"/aerialmaterial/destructionlist/DestructionListExcel.xls?keyword="
			+ encodeURIComponent(keyword) + "&zt=" + encodeURIComponent(zt) + "&hclx="
			+ encodeURIComponent(hclx) + "&dprtcode=" + encodeURIComponent(dprtcode) + "&xhrqBegin="
			+ encodeURIComponent(xhrqBegin) + "&xhrqEnd=" + encodeURIComponent(xhrqEnd));
	
}*/


function ryView(id){
	window.open(basePath+"/quality/maintenancepersonnel/view/" + id);
}
function pxjhView(id){
	if(id != null && id!=""){
		window.open(basePath+"/training/trainingnotice/find/" + id);
	}
}


function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());//关键字查询
	var kcKeyword = $.trim($("#kcKeyword").val());
	var ryKeyword = $.trim($("#ryKeyword").val());
	
	 var sjKsrq = $.trim($("#sjKsrq").val());
	 var xcpxrq = $.trim($("#xcpxrq").val());
	 
	 var sjKsrqBegin ="";
	 var sjKsrqEnd ="";
	 var xcpxrqBegin="";
	 var xcpxrqEnd="";
	 if(null != sjKsrq && "" != sjKsrq){
		  sjKsrqBegin = sjKsrq.substring(0,10)+" 00:00:00";
		  sjKsrqEnd = sjKsrq.substring(13,23)+" 23:59:59";
	
	 }
	 if(null != xcpxrq && "" != xcpxrq){
		  xcpxrqBegin = xcpxrq.substring(0,10)+" 00:00:00";
		  xcpxrqEnd = xcpxrq.substring(13,23)+" 23:59:59";
	 }
	 var dprtcode=$("#zzjg").val();
	 
	window.open(basePath+"/training/tracking/PersonnelTrainingTracking.xls?dprtcode="
 				+ dprtcode + "&kcKeyword="+encodeURIComponent(kcKeyword)
 				+"&ryKeyword="+encodeURIComponent(ryKeyword)
 				+"&sjKsrqBegin="+sjKsrqBegin
 				+"&sjKsrqEnd="+sjKsrqEnd
 				+"&xcpxrqBegin="+xcpxrqBegin
 				+"&xcpxrqEnd="+xcpxrqEnd
 				+"&keyword="+ encodeURIComponent(keyword));
}