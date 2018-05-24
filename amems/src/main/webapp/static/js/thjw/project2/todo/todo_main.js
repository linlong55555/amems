
var currzt = 1;

$(function(){
	Navigation(menuCode, '', '', 'GC-2-5 ', '林龙', '2017-08-02', '李高升', '2017-08-29');// 加载导航栏

	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				search();//调用主列表页查询
			}
		}
	});
	
	initPage();
	goPage(1,"auto","desc");
	
	//执行待办
    if(param_todoId){
    	param_fjjx = decodeURIComponent(Base64.decode(param_fjjx));
    	param_lybh = decodeURIComponent(Base64.decode(param_lybh));
		modalEmpty();
		if(formatUndefine(param_lybh) != 'undefined'){
			$("#lybh2").val(formatUndefine(param_lybh));
		}
		if(formatUndefine(param_fjjx) != 'undefined'){
			$("#fjjx2").val(formatUndefine(param_fjjx));
		}
		$("#toDoId").val(formatUndefine(param_todoId));
		$("#dbgzlxName2").val(DicAndEnumUtil.getEnumName('todoEnum',param_dbgzlx));
		$("#FeedBackModal").modal("show");
		param_todoId = null;
    }
});

var sortParam = {};
var choseAllIds=[];   
// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

// 查询条件
function gatherSearchParam (){
	var searchParam={};
	if($("#zt1").is(":checked")){
		searchParam.zt=1;
	}
	if($("#zt2").is(":checked")){
		searchParam.zt=2;
	}
	searchParam.dbgzlx=$("#daibangongzuo").val();
	return searchParam;
}

// initPage,将枚举映射为下拉列表
function initPage(){
	 $("#daibangongzuo").empty();
	 $("#daibangongzuo").append("<option value='' selected='selected' >显示全部All</option>")	
	 DicAndEnumUtil.registerEnum("todoEnum","daibangongzuo");	
	 
}
// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		  var searchParam = gatherSearchParam();		  	  
			pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		    searchParam.pagination = pagination;
		    searchParam.keyword=$.trim($('#keyword_search').val());
		    
		    $(".fksmMainTable").hide();
		    $("#cz_main_table").hide();
		    if(currzt == 1){
		    	$("#cz_main_table").show();
		    	$("#fksj_main_ch").html("接收时间");
		    }else{
		    	$(".fksmMainTable").show();
		    	$("#fksj_main_ch").html("反馈时间");
		    }
		    
			/* obj.jgdm = searchParam.jgdm; */
		 AjaxUtil.ajax({
			   url:basePath+"/project2/todo/getToDOList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
	 			if(data.total >0){
	 			appendContentHtml(data.rows);
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
	 			signByKeyword($.trim($("#keyword_search").val()),[2],"#to_do_list tr td");
	 			}else{
		 			$("#to_do_list").empty();
					$("#pagination").empty();
					$("#to_do_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:'quality_check_list_table'});
	 		}
	     }); 	
}
/* 表格拼接 */
function appendContentHtml(list){		 
			var htmlContent = ''; 
			choseAllIds = [];
			$.each(list,function(index,row){
				choseAllIds.push(index);
				 
				var ttDate = StringUtil.escapeStr(formatUndefine(row.blqx));  				 
				ttDate = ttDate.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg, '$1-$2-$3'); 
				if (index % 2 == 0) {
					htmlContent += "<tr  bgcolor=\"#f9f9f9\" >";
				   
				} else {
					htmlContent += "<tr  bgcolor=\"#fefefe\" >";
				}
				
				if(currzt == 1){
					htmlContent += "<td class='text-center'>";
				    //未处理-显示反馈
				    if(row.zt == '1'){
				    	htmlContent += "<i class='fa fa-send color-blue cursor-pointer checkPermission' permissioncode='project2:todo:main:edit' " +
	    				" onclick=\"feedback('"+ row.id + "',this)\" title='反馈 Feedback'" +
	    				"  lybh='"+StringUtil.escapeStr(formatUndefine(row.lybh))+"'"+
	    				"  fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"+
	    				"  dbgzlxName='"+StringUtil.escapeStr(formatUndefine(row.dbgzlxName))+"'"+
	    				"  dbgzlx='"+StringUtil.escapeStr(formatUndefine(row.dbgzlx))+"'"+
	    				"  zt='"+StringUtil.escapeStr(formatUndefine(row.zt))+"'"+
	    						"></i>&nbsp;&nbsp;";
				    }
				    htmlContent += "</td>";
				}
			    
			    var lydh = StringUtil.escapeStr(formatUndefine(row.lybh));
			    if(StringUtil.escapeStr(formatUndefine(row.bb)) != ''){
			    	lydh += " R"+ StringUtil.escapeStr(formatUndefine(row.bb));
			    }
			    
			    htmlContent += "<td class='text-left' >";
			    
			    if(StringUtil.escapeStr(formatUndefine(row.dbgzlx)) != 43){
			    	htmlContent +=  "<a href='javascript:void(0);' title='"+lydh+"'";
			    	htmlContent +=  " lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'";
			    	htmlContent +=  " dbywid='"+StringUtil.escapeStr(formatUndefine(row.dbywid))+"'";
			    	htmlContent +=  " dbgzlx='"+StringUtil.escapeStr(formatUndefine(row.dbgzlx))+"'";
			    	htmlContent += " onclick=\"viewSource(this)\" >";  
			    		htmlContent +=  lydh;
			    		htmlContent +=  "</a>";
			    }else{
			    	htmlContent +=  lydh;
			    }
			   
				htmlContent += "</td>";
			    
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dbgzlxName))+"'>"+StringUtil.escapeStr(formatUndefine(row.dbgzlxName))+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.sm))+"'>"+StringUtil.escapeStr(formatUndefine(row.sm))+"</td>"; 
			    /*htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.blqx))+"'>"+StringUtil.escapeStr(formatUndefine(row.blqx))+"</td>";*/ 
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(ttDate)+"'>"+StringUtil.escapeStr(ttDate)+"</td>";
			    
			    /*htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.ztName))+"'>"+StringUtil.escapeStr(formatUndefine(row.ztName))+"</td>";*/
			    htmlContent += "<td class='text-left'>"+ getTreatmentResult(row,index) + "</td>";
			    
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.fksj))+"'>"+StringUtil.escapeStr(formatUndefine(row.fksj))+"</td>";
			    
			    if(currzt == 2){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fkyj))+"'>"+StringUtil.escapeStr(formatUndefine(row.fkyj))+"</td>";
			    }
			    
			    htmlContent += "</tr>" ;
		 })
		       $("#to_do_list").empty();
			   $("#to_do_list").html(htmlContent);
}

// 弹出反馈界面
function feedback(id,obj){
	getTodoById(id,function(data){
		if(data.zt != 1){
			AlertUtil.showErrorMessage("该工作已反馈过，请刷新后再进行操作!");
			return;
		}
		modalEmpty();
		$("#lybh2").val($(obj).attr("lybh"));
		$("#fjjx2").val($(obj).attr("fjjx"));
		$("#toDoId").val(id);
		$("#dbgzlxName2").val($(obj).attr("dbgzlxName"));
		$("#FeedBackModal").modal("show");
	});
}
// 清空弹出框
function modalEmpty(){
	$("#lybh").val("");
	$("#fjjx").val("");
	$("#dbgzlxName").val("");
	$("#dbgzlx").val("");
	$("#fkyj2").val("");
	AlertUtil.hideModalFooterMessage();
}
// 反馈操作
function saveFeedBack(){
	var feedbackObject={};
	feedbackObject.fkyj=$("#fkyj2").val();
	feedbackObject.id=$("#toDoId").val();
	submit(feedbackObject);
}

// 提交
function submit(obj){
	 startWait($("#FeedBackModal"));
	 AjaxUtil.ajax({
			url:basePath+"/project2/todo/doFeedBack",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			modal:$("#FeedBackModal"),
			dataType:"json",
			success:function(massge){
				finishWait($("#FeedBackModal"));
					$("#FeedBackModal").modal("hide");
					AlertUtil.showMessage("反馈成功！");				
					goPage(1,"auto","asc");			 
			}
		});
}

function getTodoById(id, obj){
	AjaxUtil.ajax({
			async : false,
			url : basePath+"/project2/todo/selectById",
			type : "post",
			data : {id:id},
			dataType : "json",
			success : function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
}

// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { // 重置class
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
	sortParam.field = obj;
	sortParam.sort = orderStyle.split("_")[1];
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}

// 搜索条件显示与隐藏
function search() {
	TableUtil.resetTableSorting("quality_check_list_table");
	if($("#zt1").is(":checked")){
		currzt = 1;
		if(sortParam.field && sortParam.sort ){
			goPage(1,sortParam.field,sortParam.sort);
		}
		else {
			goPage(1,"auto","asc");
		}
	}
	if($("#zt2").is(":checked")){
		currzt = 2;
		if(sortParam.field && sortParam.sort ){
			goPage(1,sortParam.field,sortParam.sort);
		}
		else {
			goPage(1,"FKSJ","DESC");
		}
	}
	
}

/** 获取处理结果-已办*/
function getTreatmentResult4Processed(row,index){
	var html = "";
	if (row.paramsMap.dbstr) {
		var str = row.paramsMap.dbstr.split(",");
		var title = '';
		var bb = '';
		for (var i = 0; i < str.length; i++) {
			var arr = str[i].split("#_#");
			bb = StringUtil.escapeStr(arr[2]) ? (" R" + StringUtil.escapeStr(arr[2])) : '';
			title = (StringUtil.escapeStr(arr[1]))+bb
			if (i == 1) {
				html = html + "　<i class='icon-caret-down' id='" + index
						+ "icon' onclick=switchTreatmentResult('" + index + "')></i><div id='"+ index + "content' style='display:none'>";
			}
			if (i == 0) {
				html += "<a href='javascript:void(0);' " 
					 +" todoId='"+ StringUtil.escapeStr(row.id)+ "'"
					 +" dbgzlx='"+ StringUtil.escapeStr(row.dbgzlx)+ "'"
					 +" ywid='"+StringUtil.escapeStr(formatUndefine(arr[0]))+"'"
					 +" lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'"
					 +" fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"
					 +" jd='"+StringUtil.escapeStr(formatUndefine(row.jd))+"'"
					 
					 +" title='"+ title+ "'" 
					 +" onclick=\"toViewTodo(this)\" >" + title
						+ "</a>";
			}
			if (i != 0) {
				html += "<a href='javascript:void(0);' " 
					 +" todoId='"+ StringUtil.escapeStr(row.id)+ "'"
					 +" dbgzlx='"+ StringUtil.escapeStr(row.dbgzlx)+ "'"
					 +" ywid='"+StringUtil.escapeStr(formatUndefine(arr[0]))+"'"
					 +" lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'"
					 +" fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"
					 +" jd='"+StringUtil.escapeStr(formatUndefine(row.jd))+"'"
					 
					 +" title='"+ title+ "'" 
					 +" onclick=\"toViewTodo(this)\" >" + title
						+ "</a>";
				html += "<br>";

			}
			if (i != 0 && i == str.length - 1) {
				html += "</div>";
			}
		}
	}
	return html;
}

 
/** 处理结果*/
function getTreatmentResult(row,index){ 
	//非评估单
	if(row.zt == 1){
		//评估单-有办理记录
		if(row.paramsMap.dbstr && row.dbgzlx == '9' && row.jd == 1){
			var str = row.paramsMap.dbstr.split(",");
			var arr = str[0].split("#_#");
			//未处理
			if(arr[1]) {
				return getTreatmentResult4Processed(row,index);//处理结果
			}else{
				return getTreatmentResult4Pgd(row, index);//代办
			}
		}
		return getTreatmentResult4Todo(row,index);
	}
	if(row.zt == 2){
		return getTreatmentResult4Processed(row,index);
	}
	return '';
}

/** 获取评估单-待办*/
function getTreatmentResult4Pgd(row, index){
	var html = "";
	//评估单使用
	html += "<a href='javascript:void(0);' " 
		 +" todoId='"+ StringUtil.escapeStr(row.id)+ "'"
		 +" dbgzlx='"+ StringUtil.escapeStr(row.dbgzlx)+ "'"
		 +" lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'"
		 +" fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"
		 +" ywid='"+StringUtil.escapeStr(formatUndefine(row.dbywid))+"'"
		 +" jd='"+StringUtil.escapeStr(formatUndefine(row.jd))+"'"
		 
		 +"title='"
		 + '办理'
		 + "' onclick=\"toHandle(this)\" >" + '办理'
		 + "</a>";
	
	return html;
}


/** 获取处理结果-待办*/
function getTreatmentResult4Todo(row,index){
	var html = "";
	html += "<a href='javascript:void(0);' " 
		 +" todoId='"+ StringUtil.escapeStr(row.id)+ "'"
		 +" dbgzlx='"+ StringUtil.escapeStr(row.dbgzlx)+ "'"
		 +" lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'"
		 +" fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"
		 +" ywid='"+StringUtil.escapeStr(formatUndefine(row.dbywid))+"'"
		 +" jd='"+StringUtil.escapeStr(formatUndefine(row.jd))+"'"
		 
		 +"title='"
		 + '办理'
		 + "' onclick=\"toHandle(this)\" >" + '办理'
		 + "</a>";
	
	if (row.paramsMap.dbstr) {
		var str = row.paramsMap.dbstr.split(",");
		var title = '';
		var bb = '';
		for (var i = 0; i < str.length; i++) {
			var arr = str[i].split("#_#");
			bb = StringUtil.escapeStr(arr[2]) ? (" R" + StringUtil.escapeStr(arr[2])) : '';
			title = (StringUtil.escapeStr(arr[1]))+bb;

			if (i == 0) {
				html = html + "　<i class='icon-caret-down' id='" + index
						+ "icon' onclick=switchTreatmentResult('" + index + "')></i><div id='"+ index + "content' style='display:none'>";
			}
			 
			html += "<a href='javascript:void(0);' " 
				    +" todoId='"+ StringUtil.escapeStr(row.id)+ "'"
					+" dbgzlx='"+ StringUtil.escapeStr(row.dbgzlx)+ "'"
					+" ywid='"+StringUtil.escapeStr(formatUndefine(arr[0]))+"'"
					+" fjjx='"+StringUtil.escapeStr(formatUndefine(row.fjjx))+"'"
					+" lyid='"+StringUtil.escapeStr(formatUndefine(row.lyid))+"'"
					+" jd='"+StringUtil.escapeStr(formatUndefine(row.jd))+"'"
					
				    +" title='"+ title+ "'" 
					+" onclick=\"toViewTodo(this)\" >" + title
					+ "</a>";
			html += "<br>";
		 
			if (i != 0 && i == str.length - 1) {
				html += "</div>";
			}
		}
	}
	return html;
}

/** 单行展开处理结果*/
function switchTreatmentResult(i) {
	var flag = $("#"+choseAllIds[i]+'content').is(":hidden");
	console.info(flag);
	 if(flag){
		 $("#"+choseAllIds[i]+'content').fadeIn(500);
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
	 }else{
		 $("#"+choseAllIds[i]+'content').hide();
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
	 }
	 new Sticky({tableId:'quality_check_list_table'});
}

/** 办理 */
function toHandle(param) {
	var obj = getParam(param);
	handleCfg.toHandle(obj);
}

/** 查看办理结果 */
function toViewTodo(param) {
	var obj = getParam(param);
	handleCfg.toViewTodo(obj);
}

function getParam(param){
	var todoId = $(param).attr('todoId');//待办id
	var dbgzlx = $(param).attr('dbgzlx');//待办类型
	var id = $(param).attr('ywid');
	var lyid = $(param).attr('lyid');
	var fjjx = Base64.encode(encodeURIComponent($(param).attr('fjjx')));
	var jd = $(param).attr('jd');
	var obj = {todoId:todoId,dbgzlx:dbgzlx,lyid:lyid,fjjx:fjjx,id:id,jd:jd};
	return obj;
}

 
/** 查看来源*/
function viewSource(param){
	var $this = $(param);
	var lyid = $this.attr('lyid');
	var dbywid = $this.attr('dbywid');
	var dbgzlx = $this.attr('dbgzlx');
	handleCfg.viewSource({dbgzlx : dbgzlx, id : lyid, dbywid : dbywid});
}

/**
 * 点击表头展开表格
 */
function vieworhideWorkContentAll(){
	var obj = $("th[name='th_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<choseAllIds.length;i++){
		 if(flag){				
			 $("#"+choseAllIds[i]+'content').fadeIn(500);
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
		 }else{
			 $("#"+choseAllIds[i]+'content').hide();
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
		 }
	 }
 new Sticky({tableId:'quality_check_list_table'});
}


function vieworhideWorkContent(i){
	 var flag = $("#"+choseAllIds[i]+'content').is(":hidden");
	 if(flag){
		 $("#"+choseAllIds[i]+'content').fadeIn(500);
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
	 }else{
		 $("#"+choseAllIds[i]+'content').hide();
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
	 }
	 new Sticky({tableId:'quality_check_list_table'});
}