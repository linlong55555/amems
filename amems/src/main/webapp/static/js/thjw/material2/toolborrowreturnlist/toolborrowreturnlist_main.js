
$(function(){
	borrowReturnRecord.init();
});

/**
 * 借用归还记录
 */
var borrowReturnRecord = {
		
	tableId : "record_table",
	
	tbodyId : "record_table_list",
	
	paginationId : "record_table_pagination",
		
	/**
	 * 页面初始化
	 */
	init : function(){
		
		
		var this_ = this;
		
		// 初始化控件
		this_.initWidget();
		// 初始化导航栏
		this_.initNavigation();
		// 加载数据
		this_.reload();
	},
	
	/**
	 * 初始化导航栏
	 */
	initNavigation : function(){
		Navigation(menuCode,"","");
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		
		var this_ = this;
		
		// 初始化日期控件
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$('.date-range-picker').daterangepicker({"opens":"left"}).prev().on("click", function() {
			$(this).next().focus();
		});
		
	},
	
	/**
	 * 切换更多搜索
	 */
	moreSearch : function(){
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
	},
	
	/**
	 * 重置搜索
	 */
	resetSearch : function(){
		var this_ = this;		
		$("#jyZrrmc_search").val("");
		$("#jySj_search").val("");		
		$("#keyword_search").val("");		
		$("#dprtcode_search").val(userJgdm);
		$("input[name='jllx']").attr("checked", "checked");
	},
	
	/**
	 * table重新加载
	 */
	reload:function(){
		// 重置排序图标
		TableUtil.resetTableSorting(this.tableId);
		this.goPage(1,"auto","desc");
	},
	
	/**
	 * table数据请求
	 */
	goPage:function(pageNumber, sortColumn, orderType){
		this.AjaxGetDatasWithSearch(pageNumber, sortColumn, orderType);
	},
	
	/**
	 * 获取table数据
	 */
	AjaxGetDatasWithSearch:function(pageNumber, sortColumn, orderType){
		
		 var this_ = this;
		 var param = this_.gatherSearchParam();
		 param.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		 startWait();
		 AjaxUtil.ajax({
			type: "post",
			url: basePath+"/material/toolborrowreturnlist/list",
	 		contentType:"application/json;charset=utf-8",
	 		dataType:"json",
	 		data:JSON.stringify(param),
	 		success:function(data){
	 			finishWait();
	 			// 拼接表格
	 			this_.appendContentHtml(data);
	 			// 拼接分页
	 			this_.appendPaginationHtml(data, pageNumber, sortColumn, orderType);
	 			// 表格添加title
	 			TableUtil.addTitle("#" + this_.tbodyId + " tr td");
				// 标记关键字
			    signByKeyword($.trim($("#keyword_search").val()), [4,5,6,7,9], "#" + this_.tbodyId + " tr td");
			    // 移除
 				FormatUtil.removeNullOrUndefined();
	 		}
	     });
	},
	
	/**
	 * 获取查询参数
	 */
	gatherSearchParam : function(){
		
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		searchParam.jyZrrmc = $.trim($("#jyZrrmc_search").val());
		
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#keyword_search").val());
		var jySj = $("#jySj_search").val();
		if(jySj != ''){
			paramsMap.jySjBegin = jySj.substring(0,10) + " 00:00:00";
			paramsMap.jySjEnd = jySj.substring(13,23) + " 23:59:59";
		}
		var jllxList = [];
		$("[name=jllx]:checkbox:checked").each(function(){
			jllxList.push($(this).val());
		});
		paramsMap.jllxList = jllxList;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	
	/**
	 * 拼接表格
	 */
	appendContentHtml:function(data){
		var this_ = this;
		var htmlContent = '';
		this_.messages = [];
		if(data.total > 0){
			$.each(data.rows,function(index,row){
				htmlContent +="<tr name='"+row.id+"'>";
				htmlContent +="<td class='text-center'>"+DicAndEnumUtil.getEnumName('toolBorrowReturnTypeEnum', row.jllx)+"</td>";
				
				htmlContent +="<td>"+StringUtil.escapeStr(row.jyZrrmc)+"</td>"
				htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.czsj)+"</td>"
				htmlContent +="<td>"+StringUtil.escapeStr(row.bjh)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.bjxlh)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.ywms) + " " + StringUtil.escapeStr(row.zwms)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.pch)+"</td>";
				htmlContent +="<td class='text-right'>"+StringUtil.escapeStr(row.jySl)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.jyBz)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.xingh)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.gg)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.ckh) + " " + StringUtil.escapeStr(row.ckmc)+"</td>";
				htmlContent +="<td>"+StringUtil.escapeStr(row.kwh)+"</td>";
				htmlContent +="</tr>"
			});
		}else{
			htmlContent = "<tr class='no-data'><td class='text-center' colspan=\"13\">暂无数据 No Data.</td></tr>";
		}
		$("#" + this_.tbodyId).html(htmlContent);
	},
	
	/**
	 * 拼接分页
	 */
	appendPaginationHtml : function(data, pageNumber, sortColumn, orderType){
		var this_ = this;
		if(data.total > 0){
			new Pagination({
				renderTo : this_.paginationId,
				data : data,
				sortColumn : sortColumn,
				orderType : orderType,
				extParams : {},
				goPage : function(a, b, c) {
					this_.AjaxGetDatasWithSearch(a, b, c);
				}
			});
		}else{
			$("#"+this_.paginationId).empty();
		}
	},
	
	/**
	 * 导出excel
	 */
	exportExcel : function (){
		window.open(basePath+"/material/toolborrowreturnlist/toolborrowreturnlist.xls?json=" + JSON.stringify(this.gatherSearchParam()));
	},
};