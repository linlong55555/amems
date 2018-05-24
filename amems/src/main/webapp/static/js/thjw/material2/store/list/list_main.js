$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		list_main.init();
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
	})
	
var list_main = {
	id:'list_main',
	tableDivId : 'list_main_table_top_div',
	tableId : 'list_main_table',
	tbodyId : 'list_main_table_list',
	paginationId:'list_main_table_pagination',
	type : 1,
	pagination : {},
	ckidList : [],
	init : function(){
		this.type = $("#type", $("#"+this.id)).val();
		this.initInfo();
	},
	initInfo : function(){
		var this_ = this;
		this_.initStoreSelect();
		this_.load();
		refreshPermission();
		//回车事件控制
		document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#keyword_search", $("#"+this_.id)).is(":focus") 
						|| $("#keyword_search", $("#"+this_.id)).is(":focus")
				){
					this_.load();      
				}
			}
		};
	},
	initStoreSelect : function(){
		var this_ = this;
		this_.ckidList = [];
		$("#ck_search", $("#"+this_.id)).empty();
		var storeHtml = "<option value=''>显示全部All</option>";
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
				storeHtml += "<option value='"+row.id+"'>"+StringUtil.escapeStr(row.ckh)+" " + StringUtil.escapeStr(row.ckmc)+"</option>"
				this_.ckidList.push(row.id);
			}
		});
		$("#ck_search", $("#"+this_.id)).append(storeHtml);
	},
	/**
	 * 机构代码改变时执行
	 */
	changeDprt : function(){
		this.initStoreSelect();
		this.setCq('', '');
		this.search();
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType){
		var this_ = this;
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		if(this_.ckidList.length == 0){
			this_.setNoData();
			return;
		}
		
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/store/check/queryAllPageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.total >0){
					this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[6, 7, 9, 10, 11],"#"+this_.tbodyId+" tr td");
				} else {
					this_.setNoData();
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		var cqid = $.trim($("#cqid_search", $("#"+this.id)).val());
		var ckid = $.trim($("#ck_search", $("#"+this.id)).val());
		var whrname = $.trim($("#whr_search", $("#"+this.id)).val());
		var zdrq = $.trim($("#zdrq_search", $("#"+this.id)).val());
		searchParam.dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		paramsMap.keyword = keyword;
		if(null != cqid && "" != cqid){
			paramsMap.cqid = cqid;
		}
		if(null != ckid && "" != ckid){
			paramsMap.ckid = ckid;
		}
		if(null != whrname && "" != whrname){
			paramsMap.whrname = whrname;
		}
		if(this.ckidList != null && this.ckidList.length > 0){
			paramsMap.ckidList = this.ckidList;
		}
		if(null != zdrq && "" != zdrq){
			 var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
			 var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
			 paramsMap.zdrqBegin = zdrqBegin;
			 paramsMap.zdrqEnd = zdrqEnd;
		}
		if(this.type == 1){
			paramsMap.wllxList = [0, 1, 4, 5, 6];
		}else if(this.type == 2){
			paramsMap.wllxList = [2, 3];
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			htmlContent = htmlContent + "<tr id='"+row.id+"' >";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
			
			var yk = '';
			if(row.ykbs == 1){
				yk += "<i class='icon-plus-sign color-green' title='盘盈 Profit'></i>";
			}else if(row.ykbs == 2){
				yk += "<i class='icon-minus-sign color-red' title='盘亏 Loss'></i>";
			}
			yk += " "+Math.abs(row.yksl);
			
			htmlContent += "<td title='' style='text-align:left;vertical-align:middle;'>"+yk+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ykcsl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.ykcsl)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.kcsl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.kcsl)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.cqbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.cqbh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.bjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.zywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.zywms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.wz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.wz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xlh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.pch)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.pch)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.zdrstr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.zdrstr)+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.zdsj)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.dprtname)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
	},
	/**
	 * 打开产权弹窗
	 */
	openCqWin : function(){
		var this_ = this;
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
		var cqId = $("#cqid_search", $("#"+this_.id)).val();
		cqModal.show({
			selected : cqId,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				var cqText = '';
				var cqid = '';
				if(data != null ){
					cqText = data.cqbh;
					cqid = data.id;
				}
				this_.setCq(cqid, cqText);
			}
		});
	},
	setCq : function(cqid, cqbh){
		$("#cqbh_search", $("#"+this.id)).val(cqbh);
		$("#cqid_search", $("#"+this.id)).val(cqid);
	},
	more: function() {//查询条件更多 展开/收缩
		if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
			$("#divSearch", $("#"+this.id)).css("display", "block");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this.id)).css("display", "none");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this.id)).addClass("icon-caret-down");
		}
	},
	//搜索
	search: function(){
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load();
	},
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
		if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}
		this_.load(currentPage, sortColumn, orderType);
	},
	searchreset : function(){
		$("#keyword_search", $("#"+this.id)).val("");
		$("#divSearch", $("#"+this.id)).find("input").each(function() {
			$(this).attr("value", "");
		});
		$("#divSearch", $("#"+this.id)).find("textarea").each(function(){
			$(this).val("");
		});
		$("#divSearch", $("#"+this.id)).find("select").each(function() {
			$(this).attr("value", "");
		});
		$("#dprtcode_search", $("#"+this.id)).val(userJgdm);
		this.initStoreSelect();
		this.setCq('', '');
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		window.open(basePath+"/material/store/check/storecheck.xls?paramjson="+JSON.stringify(param));
	}
}
