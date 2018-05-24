
//申请单状态
SQDZT = ["","保存","提交","","","","","","作废","关闭","完成"];

/**
 * 加载航材领用历史记录
 */
var flagHistory = false;
function loadHistory(){
	if(flagHistory === false){
		initPlaneSelect();
		historyGoPage(1,"auto","desc");//开始的加载默认的内容 
		
		$("#dprtcode1").on("change", initPlaneSelect);
		flagHistory= true;
	}
}



function initPlaneSelect(){
	var html = "<option value=\"\">显示全部All</option>";
	html += "<option value=\"00000\">通用Currency</option>";
	var dprtcode = $("#dprtcode1").val();
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:formatUndefine(dprtcode),FJJX:null});
	$.each(planeData, function(index, row){
		if(dprtcode == row.DPRTCODE){
			html += "<option value=\""+StringUtil.escapeStr(row.FJZCH)+"\">"+StringUtil.escapeStr(row.FJZCH)+"</option>"
		}
	})
	$("#fjzch").html(html);
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function historyGoPage(pageNumber,sortType,sequence){
	historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}


var pagination1 = {};
//带条件搜索的翻页
function historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	pagination1 = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.pagination = pagination1
	obj.keyword = $.trim($("#his_keyword_search").val());
	obj.zt = $("#sqdzt").val();
	obj.fjzch = $("#fjzch").val();
	obj.dprtcode = $("#dprtcode1").val();
	
	if(id != ""){
		obj.id = id;
		id = "";
	}
	
	startWait();
	
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/list",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			if(data.rows  && data.rows.length > 0){
				historyAppendContentHtml(data.rows);
				new Pagination({
					renderTo : "historyPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage: function(a,b,c){
						historyAjaxGetDatasWithSearch(a,b,c);
					}
				});	  	   
				// 标记关键字
				signByKeyword($("#his_keyword_search").val(),[3],"#historyList td");
					   
			} else {
				$("#historyList").empty();
				$("#historyPagination").empty();
				$("#historyList").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'llls'});
			finishWait();
	    }
	}); 
}


/**
 * 拼接表格内容
 * @param list
 * @returns
 */
function historyAppendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
   		htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
		htmlContent += "<td  style='vertical-align: middle;' class='fixed-column text-center'><div>";
		if(userId == row.zdrUser.id && row.zt == "2"){
			htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:requisition:main:03' onClick=\"end('"+ row.id + "','"+ row.lysqdh + "')\" title='指定结束 end'></i>&nbsp;&nbsp;"
		}
		if(userId == row.zdrUser.id && row.zt == "1"){
			htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:requisition:main:02'  onClick=\"cancel('"+ row.id + "')\" title='作废  Invalid'></i>&nbsp;&nbsp;"
		}
		htmlContent += "</div></td>";
		
		htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='center'><a href='javascript:void(0);' onclick=\"showDetail('"+row.id+"');\">"+StringUtil.escapeStr(row.lysqdh)+"</a></td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left'>"+(StringUtil.escapeStr(row.fjzch)=="00000"?"通用Currency":StringUtil.escapeStr(row.fjzch))+"</td>";  
		
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.sqrUser.username)+" "+StringUtil.escapeStr(row.sqrUser.realname)+"'>"+StringUtil.escapeStr(row.sqrUser.username)+" "+StringUtil.escapeStr(row.sqrUser.realname)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(row.sqrq).substring(0,10)+"</td>";  
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.lyyy)+"'>"+StringUtil.escapeStr(row.lyyy)+"</td>"; 
		if(row.zt == 9){
		   htmlContent += "<td style='vertical-align: middle; ' align='center'><a href='javascript:void(0);' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' onclick=\"viewShutDown('"+row.lysqdh+"', '"+formatUndefine(row.zdjsUser.username)+' '+formatUndefine(row.zdjsUser.realname) +"','"+formatUndefine(row.zdjsrq)+"', this);\">指定结束</a></td>";
	    }else{
		   htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(SQDZT[row.zt])+"</td>";  
	    }
		htmlContent += "<td style='vertical-align: middle; ' align='left' title='"+StringUtil.escapeStr(row.zdrUser.username)+" "+StringUtil.escapeStr(row.zdrUser.realname)+"'>"+StringUtil.escapeStr(row.zdrUser.username)+" "+StringUtil.escapeStr(row.zdrUser.realname)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(row.zdsj)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode)))+"</td>"; 
		htmlContent += "</tr>";  
	});
	$("#historyList").empty();
	$("#historyList").html(htmlContent);
	refreshPermission();
}
	 
//字段排序
function historyOrderBy(obj, _obj) {
	$obj = $("#llls th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#llls .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#llls .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var orderType = "asc";
	if (orderStyle.indexOf ( "sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	var currentPage = $("#historyPagination li[class='active']").text();
	if(currentPage == ""){
		currentPage = 1;
	}
	historyGoPage(currentPage,obj,orderType);
}

	
	
//信息检索
function historySearch(){
	historyGoPage(1,"auto","desc");
}
	
		
//搜索条件重置
function historySearchreset(){
	$("#his_keyword_search").val("");
	$("#sqdzt").val("");
	$("#dprtcode1").val(userJgdm);
	$("#dprtcode1").trigger("change");
}
 
//搜索条件显示与隐藏 
function his_more_params() {
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

function cancel(sid){
	AlertUtil.showConfirmMessage("您确定要作废该领用申请吗？",{
		callback:function(){
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/requisition/cancel/"+sid,
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				success:function(data){
					historyAjaxGetDatasWithSearch(pagination1.page, pagination1.sort, pagination1.order)
//					historyGoPage(1,"auto","desc");//开始的加载默认的内容 
					finishWait();
			    }
			}); 
		}
	});
}

function end(id, sqdh){
	$("#showjsyy").show();
	$("#lysqid").val(id);
	$("#lysqdh").val(sqdh);
	$("#zdjsyy").val("");
	$("#zdjsyy").removeAttr("disabled");
	$("#shutdownConfirmBtn").show();
	$("#zdjsrDiv").hide();
	$("#zdjssjDiv").hide();
	$("#closeModal").modal("show");
}

function viewShutDown(sqdh, zdjsr, zdjssj, obj){
	$("#showjsyy").hide();
	$("#lysqdh").val(sqdh);
	$("#zdjsyy").val($(obj).attr("zdjsyy"));
	$("#zdjsr").val(zdjsr);
	$("#zdjssj").val(zdjssj);
	$("#zdjsyy").attr("disabled","disabled");
	$("#shutdownConfirmBtn").hide();
	$("#zdjsrDiv").show();
	$("#zdjssjDiv").show();
	$("#closeModal").modal("show");
}

function sbShutDown(){
	var zdjsyy = $("#zdjsyy").val();
	if(null == zdjsyy || '' == zdjsyy){
		AlertUtil.showMessage("输入指定结束原因!");
		return false;
	}
	startWait();
	var data = {id: $("#lysqid").val(), zdjsyy: zdjsyy};
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/end",
		type: "post",
		dataType:"json",
		data: data,
		success:function(data){
			AlertUtil.showMessage('指定结束成功!');
			$("#closeModal").modal("hide");
			id = $("#lysqid").val();
			historyGoPage(pagination1.page, pagination1.sort, pagination1.order);//开始的加载默认的内容 
			finishWait();
	    }
	});
}
	
function showDetail(id){
	window.open(basePath+"/aerialmaterial/requisition/view/"+id);
}

$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
	
	
