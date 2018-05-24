/**
 * 维修项目初始化
 */
var transit_main = {
	id:'transit_main',
	tableDivId : 'transit_main_top_div',
	tableId : 'transit_main_table',
	tbodyId : 'transit_main_tbody',
	paginationId:'transit_main_Pagination',
	operationId : '',
	pagination : {},
	currentDate : '',
	param: {
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	/**
	 * 加载数据
	 */
	load : function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.mainData = [];
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/contractinfo/queryAll",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				this_.planUsageList = [];
				finishWait();
				if(data.total >0){
					this_.mainData = data.rows;
					this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[2,3,4,10,11,12],"#"+this_.tbodyId+" tr td");
				} else {
					this_.setNoData();
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
			
	},	
	/**
	 * 将搜索条件封装 
	 */
	getParams : function(){
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this_.id)).val());
		var bj = $.trim($("#bj_search", $("#"+this_.id)).val());
		var cqid = $.trim($("#cqid_search", $("#"+this_.id)).val());
		var htbh = $.trim($("#htbh_search", $("#"+this_.id)).val());
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
		var shsj = '';
		if($("#shsj_search", $("#"+this_.id)).attr("checked")){
			shsj = 1;
		}
		var hclx = [];
		if($("#hclxType").val() ==1){
			hclx = [0,1,4,5,6];
		}else{
			hclx = [2,3];
		}
		paramsMap.hclx = hclx;
		paramsMap.keyword = keyword;
		paramsMap.bj = bj;
		searchParam.cqid = cqid;
		paramsMap.htbh = htbh;
		paramsMap.dprtcode = dprtcode;
		paramsMap.shsj = shsj;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 拼接列表
	 */
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			htmlContent = htmlContent + "<tr>";
			htmlContent = htmlContent + "<td class='fixed-column text-center'>"+(index+1)+"</td>";
			htmlContent += "<td class='fixed-column text-left' title='"+StringUtil.escapeStr(paramsMap.hth)+"'><a href=\"javascript:viewHt('"+paramsMap.htid+"')\">"+StringUtil.escapeStr(paramsMap.hth)+"</a></td>";
			htmlContent += "<td class='fixed-column text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.bjmc)+"'>"+StringUtil.escapeStr(paramsMap.bjmc)+"</td>";
			htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.sl)+" "+StringUtil.escapeStr(paramsMap.jldw)+"'>"+StringUtil.escapeStr(row.sl)+" "+StringUtil.escapeStr(paramsMap.jldw)+"</td>";
			htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(paramsMap.rks - paramsMap.cks)+" "+StringUtil.escapeStr(paramsMap.jldw)+"'>"+StringUtil.escapeStr(paramsMap.rks - paramsMap.cks)+" "+StringUtil.escapeStr(paramsMap.jldw)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.cqbh) +" "+ StringUtil.escapeStr(paramsMap.fjzch) +" "+ StringUtil.escapeStr(paramsMap.gsmc)+"'>"+StringUtil.escapeStr(paramsMap.cqbh)+"</td>";
			htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.paramsMap.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.paramsMap.hclx)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.xgfms)+"'>"+StringUtil.escapeStr(paramsMap.xgfms)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.cjjh)+"'>"+StringUtil.escapeStr(paramsMap.cjjh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.xingh)+"'>"+StringUtil.escapeStr(paramsMap.xingh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.gg)+"'>"+StringUtil.escapeStr(paramsMap.gg)+"</td>";
			htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('contractMgntTypeEnum',paramsMap.htlx)+"'>"+DicAndEnumUtil.getEnumName('contractMgntTypeEnum',paramsMap.htlx)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
	},
	//查看合同
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 查看维修历史记录
	 */
	viewHistory : function(id, dprtcode){
		window.open(basePath+"/produce/maintenance/initialization/project/history?id=" + id + "&fjzch=" + this.param.fjzch + "&dprtcode="+ dprtcode);
	},
	/**
	 * 排序
	 */
	orderBy : function(sortColumn, prefix){//字段排序
		var this_ = this;
		var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
		var orderStyle = $obj.attr("class");
		$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
		if(currentPage == ""){currentPage = 1;}
		/*if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}*/
		this_.load(currentPage, sortColumn, orderType);
	},
	/**
	 * 刷新页面
	 */
	refreshPage : function(){//刷新页面
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
	},
	/**
	 * 搜索
	 */
	search : function(){
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	openList : function(){
		var this_ = this;
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
		var cqId = $("#cqid_search", $("#"+this_.id)).val();
		cqModal.show({
			selected:cqId,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				var cqText = '';
				var cqid = '';
				if(data != null ){
					cqText = data.cqbh;
					cqid = data.id;
				}
				$("#cqText_search", $("#"+this_.id)).val(cqText);
				$("#cqid_search", $("#"+this_.id)).val(cqid);
			}
		});
	},
	moreSearch : function(){
		var this_ = this;
		if ($("#divSearch", $("#"+this_.id)).css("display") == "none") {
			$("#divSearch", $("#"+this_.id)).css("display", "block");
			App.resizeHeight();
			$("#icon", $("#"+this_.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this_.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this_.id)).css("display", "none");
			App.resizeHeight();
			$("#icon", $("#"+this_.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this_.id)).addClass("icon-caret-down");
		}
	},
	//搜索条件重置
	searchreset : function(){
		var this_ = this;
		 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
				$(this).val("");
			});
		 $("#keyword_search", $("#"+this_.id)).val("");
		 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
		 $("#wlly_search", $("#"+this_.id)).val("");
		 $("#cqText_search", $("#"+this_.id)).val("");
		 $("#cqid_search", $("#"+this_.id)).val("");
		 
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		//param.keyword = encodeURIComponent(param.keyword);
		window.open(basePath+"/material/stock/material/transit.xls?paramjson="+JSON.stringify(param));
	},
	//搜索条件重置
	 searchreset :function (){
		 var this_ = this;
		 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
				$(this).val("");
			});
		 $("#keyword_search", $("#"+this_.id)).val("");
		 $("#cqText_search", $("#"+this_.id)).val("");
		 $("#cqid_search", $("#"+this_.id)).val("");
		 $("#shsj_search", $("#"+this_.id)).attr("checked","checked");
		 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
	}
};

//合同查看
function viewHt(id){
	window.open(basePath+"/material/contract/view/"+id);
}