
	$(function(){
		aircraftfailure.init();
	});
	
	var aircraftfailure = {
		
		tableId : "aircraftfailure_table",
		
		tbodyId : "aircraftfailure_table_list",
		
		paginationId : "aircraftfailure_table_pagination",
		
		// 页面初始化
		init : function(){
			
			
			var this_ = this;
			
			// 加载导航栏
			this_.initNavigation();
			// 初始化飞机注册号
			this_.initAcReg();
			// 初始化控件
			this_.initWidget();
			// 加载数据
			this_.reload();
		},
		
		// 加载导航栏
		initNavigation : function(){
			Navigation(menuCode, '', '');
		},
		
		// 初始化控件
		initWidget : function(){
			
			var this_ = this;
			
			// 初始化日期控件
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			$('.date-range-picker').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
			
		},
		
		// 初始化飞机注册号
		initAcReg : function(){
			var this_ = this;
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this_.getDprtcode()});
		 	var planeRegOption = '<option value="">显示全部All</option>';
		 	if(planeDatas != null && planeDatas.length >0){
		 		$.each(planeDatas, function(i, planeData){
	 				planeRegOption 
	 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"
	 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
	 					+ "</option>";
		 		});
		 	}
	 		$("#fjzch").html(planeRegOption);
		},
		
		// 改变组织机构
		changeDprtcode : function(){
			this.initAcReg();
			this.reload();
		},
		
		// 获取组织机构
		getDprtcode : function(){
			return $.trim($("#dprtcode").val());
		},
		
		// 获取飞机注册号
		getFjzch : function(){
			return $.trim($("#fjzch").val());
		},
		
		// 获取关键字
		getKeyword : function(){
			return $.trim($("#keyword").val());
		},
		
		// table重新加载
		reload:function(){
			// 重置排序图标
			TableUtil.resetTableSorting(this.tableId);
			this.goPage(1,"auto","desc");
		},
		
		// table跳转页面
		goPage:function(pageNumber, sortColumn, orderType){
			this.AjaxGetDatasWithSearch(pageNumber, sortColumn, orderType);
		},
		
		// 获取table数据
		AjaxGetDatasWithSearch:function(pageNumber, sortColumn, orderType){
			
			 var this_ = this;
			 var param = this_.gatherSearchParam();
			 param.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
			 startWait();
			 AjaxUtil.ajax({
				type: "post",
				url: basePath+"/produce/aircraftfailure/pageablelist",
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
				    signByKeyword(this_.getKeyword(), [5, 6, 9], "#" + this_.tbodyId + " tr td");
				    // 移除
	 				FormatUtil.removeNullOrUndefined();
		 		}
		     });
		},
		
		// 获取查询参数
		gatherSearchParam : function(){
			
			var this_ = this;
			var searchParam = {};
			searchParam.fjzch = this_.getFjzch();
			searchParam.dprtcode = this_.getDprtcode();
			searchParam.gzbg = $("#gzbg").val();
			
			var paramsMap = {};
			paramsMap.keyword = this_.getKeyword();
			var fxrq = $("#fxrq").val();
			if(fxrq != ''){
				paramsMap.fxrqBegin = fxrq.substring(0,10);
				paramsMap.fxrqEnd = fxrq.substring(13,23);
			}
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		
		// 拼接表格
		appendContentHtml:function(data){
			var this_ = this;
			var htmlContent = '';
			if(data.total > 0){
				$.each(data.rows,function(index,row){
					htmlContent +="<tr>";
					htmlContent +="<td class='text-center'>"+(index + 1)+"</td>"
					htmlContent +="<td>"+StringUtil.escapeStr(row.fjzch)+"</td>"
					htmlContent +="<td class='text-center'>"+(row.fxrq||"").substr(0, 10)+"</td>"
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.gzbg)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.gzxx)+"</td>"
					htmlContent +="<td>"+StringUtil.escapeStr(row.clcs)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.gzz)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.zrr)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.sjZd)+"</td>";
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr class='no-data'><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>";
			}
			$("#" + this_.tbodyId).html(htmlContent);
		},
		
		// 拼接分页
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
		
		// 查询更多
		more : function(){
			if ($("#divSearch").css("display") == "none") {
				$("#divSearch").css("display", "block");
				$("#icon").removeClass("icon-caret-down");
				$("#icon").addClass("icon-caret-up");
			} else {
				$("#divSearch").css("display", "none");
				$("#icon").removeClass("icon-caret-up");
				$("#icon").addClass("icon-caret-down");
			}
		},
		
		// 查看飞行记录单
		view : function(id){
			window.open(basePath + "/produce/aircraftfailure/view?id=" + id);
		},
		
		searchreset:function(){
			var this_ = this;		
			$("#keyword").val("");
			$("#fxrq").val("");		
			$("#gzbg").val("");		
			$("#dprtcode").val(userJgdm);
			this_.initAcReg();
			TableUtil.resetTableSorting(this_.tableId);
		},
		
		// 字段排序
		orderBy : function(sortColumn, prefix){
			var this_ = this;
			var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableId));
			var orderStyle = $obj.attr("class");
			$(".sorting_desc", $("#"+this_.tableId)).removeClass("sorting_desc").addClass("sorting");
			$(".sorting_asc", $("#"+this_.tableId)).removeClass("sorting_asc").addClass("sorting");
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
			this_.goPage(currentPage, sortColumn, orderType);
		},
		
		/**
		 * 导出
		 */
		exportExcel: function(){
			var param = this.gatherSearchParam();
			window.open(basePath+"/produce/aircraftfailure/aircraftfailure.xls?json="+JSON.stringify(param));
		},
	};
	
	// 自定义页面高度
	function customResizeHeight(bodyHeight, tableHeight){
		
	}
	
	