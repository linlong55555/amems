
var summatyList = [];
$(function(){
	loadSummatyList(1,"zdsj","desc");//开始的加载默认的内容 
	
});

//带条件搜索的翻页
function loadSummatyList(pageNumber,sortType,sequence){
	var searchParam = gatSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/production/maintenancerecord/queryLucenePageList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
	    summatyList = [];
		   if(data.total >0){
			   summatyList = data.rows;
			   loadSearchData(data.rows);
			 	 new Pagination({
						renderTo : "searchPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							loadSummatyList(pageNumber,sortType,sequence);
						}
					}); 
		   } else {
			  $("#searchList").empty();
			  $("#searchPagination").empty();
			  $("#searchList").append("暂无数据 No data.");
		   }
     }
   }); 
	
}

//将搜索条件封装 
function gatSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var keyword = $.trim($("#keyword_info_search").val());
	 if(keyword == null || keyword == ""){
	 }
	 paramsMap.keyword = keyword;
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
}

function loadSearchData(list){
	
	   var htmlContent = '';
	   $.each(list,function(index,row){
//		   var title = '';
//		   title += '故障描述：'+ '\n       '+row.gzmsTitle + '\n';
//		   title += '经验总结：'+ '\n       '+row.jyzjTitle + '';
		   var gzms = row.gzmsStr;
		   var jyzj = row.jyzjStr;
		   if($.trim($("#keyword_info_search").val()) != ''){
			   if(row.gzmsTitle != '' && row.gzmsTitle.length > 150 && gzms.indexOf('</span>') > 0 && gzms.indexOf("<span style='color:red;'>") > 0){
				   gzms += '...';
			   }
			   if(row.jyzjTitle != '' && row.jyzjTitle.length > 70 && jyzj.indexOf('</span>') > 0 && jyzj.indexOf("<span style='color:red;'>") > 0){
				   jyzj += '...';
			   }
		   }
		   htmlContent += '<div class="line"></div>';
		   htmlContent += '<div class="search_text col-lg-12 padding-left-0 padding-right-0">';
		   htmlContent += '<div class="col-lg-9 padding-left-0 padding-right-0">';
		   htmlContent += '<h1><a href="#" onclick="opensearchWin('+index+')" >';
		   htmlContent += DicAndEnumUtil.getEnumName('visibleRangeEnum',row.kjfw);
		   htmlContent += " ";
		   //htmlContent += StringUtil.escapeStr(row.zy);
		   htmlContent += formatNbsp(row.zystr);
		   htmlContent += " ";
		   htmlContent += formatNbsp(row.gjc);
		   htmlContent += '<a href="#" onclick="opensearchWin('+index+')" class="pull-right" style="margin-right:10px">';
		   htmlContent += '排故思路及经过';
		   htmlContent += '</a>';
		   htmlContent += '</h1>';
		   htmlContent += '<p onmouseover="move_over(this)" onmouseout="move_out(this)" jyzj="'+StringUtil.escapeStr(row.jyzjTitle)+'" gzms="'+StringUtil.escapeStr(row.gzmsTitle)+'" rowid="'+row.id+'" ><em>故障描述：</em>'+formatNbsp(gzms)+'</p>';
		   
		   htmlContent += '<p onmouseover="move_over(this)" onmouseout="move_out(this)" jyzj="'+StringUtil.escapeStr(row.jyzjTitle)+'" gzms="'+StringUtil.escapeStr(row.gzmsTitle)+'" rowid="'+row.id+'" ><em>经验总结：</em>'+formatNbsp(jyzj)+'</p>';
		   
//		   htmlContent += '<div id="'+row.id+'" class="tip" >';
//		   htmlContent += '</div>';
		   
		   htmlContent += '</div> ';
		   htmlContent += '<div class="col-lg-3 padding-right-0">';
		   htmlContent += '<p><em>所属信息</em>：'+formatUndefine(row.displayName)+" "+formatUndefine(row.zdsj)+'</p>';
		   htmlContent += '<p><em>机型</em>    ：'+formatNbsp(row.fjjx)+' <em>注册号</em>：'+formatNbsp(row.fjzch)+'</p>';
		   htmlContent += '<p><em>件号</em>：'+formatNbsp(row.bjh)+' <em>ATA章节号</em>：'+formatNbsp(row.zjhstr)+'</p>';
		   htmlContent += '<p><em>组织机构</em>：'+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+'  <em>基地</em>：'+formatNbsp(row.jdstr)+'</p>';
		   htmlContent += '</div>';
		   htmlContent += '</div>';
		   htmlContent += '<div class="clearfix"></div>';
	   });
	   $("#searchPagination").empty();
	   $("#searchList").html(htmlContent);
}

function searchInfo(){
	loadSummatyList(1,"zdsj","desc");//开始的加载默认的内容 
}

//打开新增故障总结弹出框
function opensearchWin(index){
	var row = summatyList[index];
	setSummaryViewData(row);//设置故障总结表单数据
	loadDetailViewData(row.detailList);//设置故障总结详情数据
	loadAttachementsView(row.attachmentList);//设置附件
	/*loadDetailData(obj.summaryDetailList,false);//设置故障总结详情数据
	$("#"+alertFormId).modal("show");
	attachNumber = 0;
	isEditFile = false;
	initUploadObj();
	loadAttachements(id);*/
	$("#alertSearchForm").modal("show");
}

//设置故障总结详情数据
function loadDetailViewData(list){
	var htmlContent = '';
	$("#detailTableView").empty();
	if(list == null || list.length == 0){
		$("#detailTableView").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
		return false;
	}
	var keyword = $.trim($("#keyword_info_search").val());
	$.each(list, function(index, obj) {
		htmlContent += '<div class="line"></div>';
		   htmlContent += '<div class="search_text col-lg-12 padding-left-0 padding-right-0">';
		   htmlContent += '<h1>';
		   htmlContent += (index+1);
		   htmlContent += " ";
		   htmlContent += formatNbsp(obj.clr);
		   htmlContent += " ";
		   htmlContent += formatUndefine(obj.clsj);
		   htmlContent += '</h1>';
		   htmlContent += '<p>排故经过：'+formatNbsp(obj.pgjg)+'</p>';
		   htmlContent += '</div>';
		   htmlContent += '<div class="clearfix"></div>';
	});
	$("#detailTableView").html(htmlContent);
}

/**
 * 标记关键字
 * @param keyword	关键字的值
 * @param columns	需要被标记的列（数组）
 */
function formatKeyword(keyword, value){
	 if(keyword != '' && value != ''){
		 var startIndex = value.indexOf(keyword);
		 if(startIndex != -1){
			 value = ([value.substr(0,startIndex),
		               "<font style='color:red'>",
		               value.substr(startIndex, keyword.length),
		               "</font>",
		               value.substr(parseInt(startIndex) + parseInt(keyword.length))
		               ].join(""))
 
		 }
		 alert(value);
		 return value;
	 }
}

/*//设置故障总结详情数据
function loadDetailViewData(list){
	var htmlContent = '';
	$("#detailTableView").empty();
	if(list == null || list.length == 0){
		$("#detailTableView").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
		return false;
	}
	$.each(list, function(index, obj) {
		if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\">";
		   }
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
		htmlContent +=  "<td title='"+formatUndefine(obj.clr)+"' style='text-align:left;vertical-align:middle;' >"+formatUndefine(obj.clr)+"</td>";
		htmlContent +=  "<td style='text-align:center;vertical-align:middle;'>"+formatUndefine(obj.clsj)+"</td>";
		htmlContent +=  "<td title='"+formatUndefine(obj.pgjg)+"' style='text-align:left;vertical-align:middle;'>"+formatUndefine(obj.pgjg)+"</td>";
		htmlContent += "</tr>";  
	});
	$("#detailTableView").html(htmlContent);
}*/

//设置附件
function loadAttachementsView(list){
	var htmlContent = '';
	$("#attachmentsListViewTbody").empty();
	if(list == null || list.length == 0){
		$("#attachmentsListViewTbody").append("<tr><td  colspan='5'  class='text-center'>暂无数据 No data.</td></tr>");
		return false;
	}
	$.each(list, function(index, obj) {
		
		 htmlContent += '<div class="col-lg-12 padding-left-0 padding-right-0">';
		 htmlContent += '<a href="#" onclick=downloadfileView("'+obj.id+'") >';
		 htmlContent += StringUtil.escapeStr(obj.wbwjm);
		 htmlContent += '</a>';
		 htmlContent += '</div> ';
	   htmlContent += '<div class="clearfix"></div>';
		
	});
	$("#attachmentsListViewTbody").html(htmlContent);
}

//设置故障总结表单数据
function setSummaryViewData(obj){
	$("#vbjh", $("#alertSearchForm")).html(formatNbsp(obj.bjh));
	$("#vjx", $("#alertSearchForm")).html(formatNbsp(obj.fjjx));
	$("#vfjzch", $("#alertSearchForm")).html(formatNbsp(obj.fjzch));
	$("#vzjhstr", $("#alertSearchForm")).html(formatNbsp(obj.zjhstr));
	$("#vgjc", $("#alertSearchForm")).html(formatNbsp(obj.gjc));
	$("#vkjfw", $("#alertSearchForm")).html(DicAndEnumUtil.getEnumName('visibleRangeEnum',obj.kjfw));
	$("#vjdstr", $("#alertSearchForm")).html(formatNbsp(obj.jdstr));
	$("#vzy", $("#alertSearchForm")).html(formatNbsp(obj.zy));
	$("#vgzms", $("#alertSearchForm")).html(formatNbsp(obj.gzms));
	$("#vjyzj", $("#alertSearchForm")).html(formatNbsp(obj.jyzj));
}

function downloadfileView(id){
//	/window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
//鼠标移动上去显示
function move_over(obj){
	var id = $(obj).attr("rowid");
	var gzms = $(obj).attr("gzms");
	var jyzj = $(obj).attr("jyzj");
	$("#gzmsDetail").text(gzms);
	$("#jyzjDetail").text(jyzj);
	 if((gzms != '' && gzms.length > 150) || (jyzj != '' && jyzj.length > 70)){
		 $("#viewDetailDiv").show();
	 }
	
	//$("#"+id).show();
}

//鼠标移出隐藏
function move_out(obj){
	//var id = $(obj).attr("rowid");
	//$("#"+id).hide();
	$("#viewDetailDiv").hide();
}

function formatNbsp(str){
	if(typeof(str) == 'undefined' || str == undefined || str == null){
		return "";
	}
	if (typeof(str) != 'string') {
		return str;
	}
	str = str.replaceAll("&", "&amp;");
	return str;
}

//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#keyword_info_search").is(":focus")){
			searchInfo();     
		}
	}
}



