$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	 var d=new Date;
	var year=d.getFullYear(); 
	var month=d.getMonth()+1;
	month =(month<10 ? "0"+month:month); 
	$('#year').datepicker({
		 maxViewMode: 1,
         minViewMode:1,
         format: "yyyy-mm",//选择日期后，文本框显示的日期格式
         language: "zh-CN", //汉化 
         autoclose: true,  
    });
	$("#year").val(year+"-"+month);
	 //收缩效果
	
	/*人员补贴汇总*/
	$('#summaryCollapsed').on('show.bs.collapse', function (e) {
		classIn();
		collapseFun(0) 
		$('#summaryCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",true);
	});
	$('#summaryCollapsed').on('hide.bs.collapse', function (e) { 
		$('#summaryCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",false);
	});
	
	/*维修工时明细*/
	
	$('#maintenanceCollapsed').on('show.bs.collapse', function () {
		classIn();
		collapseFun(1);
		$('#maintenanceCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",true);
	});
	$('#maintenanceCollapsed').on('hide.bs.collapse', function () {
		$('#maintenanceCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",false);
		
	});
	
	/*清洁工时明细*/
	
	$('#cleaningCollapsed').on('show.bs.collapse', function () {
		classIn();
		collapseFun(2)
		$('#cleaningCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",true);
	});
	$('#cleaningCollapsed').on('hide.bs.collapse', function () {
		$('#cleaningCollapsed').siblings("div").find("input.selectCheckbox").prop("checked",false);
	});
	$("input[class='selectCheckbox']").click(function(e){
		var _this=this;
		 e = e || window.event;  
			if(e.stopPropagation) { //W3C阻止冒泡方法  
			    e.stopPropagation();  
			 } else {  
			     e.cancelBubble = true; //IE阻止冒泡方法  
			 } 
	      if($(this).is(":checked")){  
	    	  $(_this).parents(".panel-heading").eq(0).siblings(".panel-collapse").collapse('show');
	        }else{  
	        	$(_this).parents(".panel-heading").eq(0).siblings(".panel-collapse").collapse('hide');
	        }  
	})
	loadDatail();
})
function moreSearch(){
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
}

/*删除一行*/
function removeTr(obj){
	$(obj).parent().parent().remove();
}

function showHourTime(){
	getDataById(function(obj){
		settingtimeprice_open.init({
			obj:obj,
			callback:function(data){
				doRequest(data,"保存成功!",basePath + "/produce/worktimesubsidy/add","settingtimeprice_open_alert");
			}
		})
	})
}

function getDataById(obj){
	AjaxUtil.ajax({
		url : basePath + "/produce/worktimesubsidy/get",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			if(typeof(obj)=="function"){
				obj(data); 
			}			
		}
	});
}

function doRequest(data,message,url,modal){
	startWait($("#"+modal));
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		modal : $("#"+modal),
		success : function(data) {
			id = data;				
			AlertUtil.showMessage(message, loadDatail());
			$("#"+modal).modal("hide");
			finishWait($("#"+modal));
			refreshPermission();
		}
	});
}

function customResizeHeight(bodyHeight, tableHeight){
	$("#demand_apply_body").removeAttr("style");
	var panelFooter=$("#demand_apply_body").siblings(".panel-footer").outerHeight()||0
	var panelBody=bodyHeight-panelFooter;
	$("#demand_apply_body").css({"height":(panelBody+10)+"px","overflow":"auto"});
	collapseFun();
}

function classIn(){
	var activeCollapse=null;
	for(var i=0;i<$("#demand_apply_body .panel-primary").length;i++){
		if($("#demand_apply_body .panel-primary").eq(i).find(".panel-collapse").hasClass("in")){
			activeCollapse=i;
			break;
		}
	}
	$("#demand_apply_body .panel-primary").eq(activeCollapse).find(".panel-collapse").collapse("hide");
}
function collapseFun(index){
	var panelBody=$("#demand_apply_body").outerHeight()||0
	var worktimeInfo=$(".worktimeInfo").outerHeight()||0;
	var collapseHeight=0;
	var activeCollapse=null;
	if(index==null){
		for(var i=0;i<$("#demand_apply_body .panel-primary").length;i++){
			if($("#demand_apply_body .panel-primary").eq(i).find(".panel-collapse").hasClass("in")){
				activeCollapse=i;
			}
			break;
		}
	}else{
		activeCollapse=index;
	}
	for(var i=0;i<$("#demand_apply_body .panel-primary").length;i++){
		if(i!=activeCollapse){
			collapseHeight+=($("#demand_apply_body .panel-primary").eq(i).find(".panel-heading").outerHeight()||0)+1	
		}
		
	}
	if(activeCollapse!=null){
		var activeHeader=$("#demand_apply_body .panel-primary").eq(activeCollapse).find(".panel-heading").outerHeight()||0;
		var activeCollapseHeight=panelBody-worktimeInfo-collapseHeight-48-activeHeader;
		  if(navigator.userAgent.indexOf("Chrome") > -1){
			  activeCollapseHeight -= 10;
          }
		$("#demand_apply_body .panel-primary").eq(activeCollapse).find(".panel-body").attr("style","height:"+activeCollapseHeight+"px;overflow:auto;padding-top:0px;padding-bottom:0px;");
		
	}
}

	function loadDatail(){
		var obj = {};
		obj = gatherSearchParam();
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/produce/worktimesubsidy/getAll",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				if (data.totalList != null && data.totalList.length>0) {
					appendTotalContentHtml(data.totalList);
				} else {
					$("#totalList").empty();
					$("#totalList").append("<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>");
				}
				$("#wx_zgs").html(data.wxgsZgs);
				$("#wx_zje").html(data.wxgsZje);
				$("#wx_dj").html(data.wxgsdj);
				$("#wx_biz").html(data.biz);
				$("#qj_biz").html(data.biz);
				$("#total_biz").html(data.biz);
				if (data.wxgsList != null && data.wxgsList.length>0) {
					appendGsContentHtml(data.wxgsList,"wxgsList",data.wxgsdj);
				} else {
					$("#wxgsList").empty();
					$("#wxgsList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
				}
				$("#qj_zgs").html(data.qjgsZgs);
				$("#qj_zje").html(data.qjgsZje);
				$("#qj_dj").html(data.qjgsdj);
				if (data.qjgsList != null && data.qjgsList.length>0) {
					appendGsContentHtml(data.qjgsList,"qjgsList",data.qjgsdj);
				} else {
					$("#qjgsList").empty();
					$("#qjgsList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
				}
			}
		});
	}
	
	function gatherSearchParam() {
		var searchParam = {};
		searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
		searchParam.dprtcode = userJgdm;
		var paramsMap={};
		var sjjsrq = $("#year").val();
		paramsMap.sjjsrq = sjjsrq;
		searchParam.paramsMap=paramsMap;
		return searchParam;
	}
	
	function appendTotalContentHtml(list){
		var htmlContent = "";
		$.each(list,function(index, row) {
			htmlContent += "<tr >";
			htmlContent += "<td class='text-left' >"+ (index+1)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.zh) + "'>"+ StringUtil.escapeStr(row.zh)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.name) + "'>"+ StringUtil.escapeStr(row.name)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.zje) + "'>"+ StringUtil.escapeStr(row.zje)+ "</td>";	
			htmlContent += "</tr>";
		});
		$("#totalList").empty();
		$("#totalList").html(htmlContent);
		refreshPermission();
	}
	
	function appendGsContentHtml(list,str,obj){
		var htmlContent = '';
		$.each(list,function(index, row) {
			htmlContent += "<tr >";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GZRQ) + "'>"+ StringUtil.escapeStr(row.GZRQ)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.USERNO) + "'>"+ StringUtil.escapeStr(row.USERNO)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.USERNAME) + "'>"+ StringUtil.escapeStr(row.USERNAME)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GS) + "'>"+ StringUtil.escapeStr(row.GS)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+(obj=="-"?"-":StringUtil.escapeStr(row.GSFY)) + "'>"+ (obj=="-"?"-":StringUtil.escapeStr(row.GSFY))+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GZMS) + "'>"+ StringUtil.escapeStr(row.GZMS)+ "</td>";	
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.GKBH) + "'>"+ StringUtil.escapeStr(row.GKBH)+ "</td>";	
			htmlContent += "</tr>";
		});
		$("#"+str).empty();
		$("#"+str).html(htmlContent);
		refreshPermission();
	}
	
	function exportPdf(){
		var obj = {};
		obj = gatherSearchParam();
		var url=basePath+"/produce/worktimesubsidy/workTime?paramjson="+JSON.stringify(obj);
    	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
				'PDF','width:50%;height:50%;top:100;left:100;');
	}
	
	function exportExcel(){
		var obj = {};
		obj = gatherSearchParam();
		window.open(basePath+"/produce/worktimesubsidy/workTime.xls?paramjson="+JSON.stringify(obj));
	}