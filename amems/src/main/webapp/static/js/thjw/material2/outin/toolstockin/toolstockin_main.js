    
$(function(){
	// 工具入库上架初始化
	materialStockIn.init();
	// 工具入库上架查询
	materialStockIn.search();
	
	//执行待办
    if(todo_ywid){
    	materialStockIn.putOnShelves(todo_ywid);
    	todo_ywid = null;
    }
	
});
    
/**
 * 工具入库上架-主页面
 */
var materialStockIn = {
		
	/**
	 * 初始化
	 */
	init : function(){
		
		var this_ = this;
		
		// 初始化导航
		this_.initNavigation();
		
		// 初始化控件
		this_.initWidget();
		
		// 初始化事件
		this_.initEvent();
		
		// 初始化数据字典和枚举
		this_.initDicAndEnum();
		
	},
	
	/**
	 * 初始化导航
	 */
	initNavigation : function(){
		Navigation(menuCode, "", "");
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		
		// 初始化日期选择控件
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		// 初始化日期范围控件
		$('.date-range-picker').daterangepicker().prev().on("click", function() {
			$(this).next().focus();
		});
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		this.buildStore();
	},
	
	/**
	 * 初始化事件
	 */
	initEvent : function(){
		//收缩效果
		$('#certificate_info').on('shown.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#certificate_info').on('hidden.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
		
		$('#additionalInfo').on('shown.bs.collapse', function () {
			$('#additionalInfo').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#additionalInfo').on('hidden.bs.collapse', function () {
			$('#additionalInfo').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
	},
	
	/**
	 * 显示更多
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
	 * 加载数据
	 */
	load : 	function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var obj = this.getParams();
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:20};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/material/outin/materialstockinlist",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				if(data.total >0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : "marterial_stockin_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[3,4,5,6,9,13], "#marterial_stockin_list tr td");
				} else {
					$("#marterial_stockin_list").empty();
					$("#marterial_stockin_pagination").empty();
					$("#marterial_stockin_list").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	
	/**
	 * 获取参数
	 */
	getParams : function(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.ckid = $.trim($("#store_search").val());
		 var keyword = $.trim($("#keyword_search").val());
		 var shrq = $.trim($("#shrq_search").val());
		 var hclx = $.trim($("#hclx_search").val());
		 if('' != keyword){
			 paramsMap.keyword = keyword;
		 }
		 if('' != shrq){
			 paramsMap.shrqBegin = shrq.substring(0,10);
			 paramsMap.shrqEnd = shrq.substring(13,23);
		 }
		 if('' != hclx){
			 paramsMap.hclxEj = hclx;
		 }
		 paramsMap.type = 'tool';
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	
	/**
	 * 拼接列表
	 */
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			htmlContent += "<tr>";
			htmlContent += "<td class='text-center'>"; 
			if(!row.inspection || (row.inspection.zt != -1 && row.inspection.zt != 1)){
				htmlContent += "<a href='javascript:;' onclick='materialStockIn.putOnShelves(\""+StringUtil.escapeStr(row.id)+"\")'>上架</a>";
			}
			htmlContent += "</td>";
			
			htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('materialToolSecondTypeEnum', row.hCMainData ? row.hCMainData.hclxEj : "")+"'>"+DicAndEnumUtil.getEnumName('materialToolSecondTypeEnum', row.hCMainData ? row.hCMainData.hclxEj : "")+"</td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.hCMainData ? row.hCMainData.ywms : "")+" "+StringUtil.escapeStr(row.hCMainData ? row.hCMainData.zwms : "")+"'>"+StringUtil.escapeStr(row.hCMainData ? row.hCMainData.ywms : "")+" "+StringUtil.escapeStr(row.hCMainData ? row.hCMainData.zwms : "")+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"'>"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</td>";  
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.receipt ? row.receipt.shdh : "")+"'><a href='javascript:void(0);' onclick='materialStockIn.viewReceipt(\""+(row.receipt ? row.receipt.id : "")+"\")'>"+StringUtil.escapeStr(row.receipt ? row.receipt.shdh : "")+"</a></td>";  
			htmlContent += "<td class='text-center' title='"+((row.receipt ? row.receipt.shrq : "") || "").substr(0, 10)+"'>"+((row.receipt ? row.receipt.shrq : "") || "").substr(0, 10)+"</td>";  
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.receipt ? (row.receipt.shr ? row.receipt.shr.displayName : "") : "")+"'>"+StringUtil.escapeStr(row.receipt ? (row.receipt.shr ? row.receipt.shr.displayName : "") : "")+"</td>";  
			htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('receiptTypeEnum', row.receipt ? row.receipt.shlx : "")+"'>"+DicAndEnumUtil.getEnumName('receiptTypeEnum', row.receipt ? row.receipt.shlx : "")+"</td>";  
			var zjztTitle = "";
			var zjztContent = "";
			if(!row.inspection){
				zjztTitle = "无需质检";
				zjztContent = "无需质检";
			}else if(row.inspection.zt == -1 || row.inspection.zt == 1){
				zjztTitle = "质检中";
				zjztContent = "质检中";
			}else{
				zjztTitle = StringUtil.escapeStr(row.inspection.jydh);
				zjztContent = "<a href='javascript:void(0);' onclick='materialStockIn.viewInspection(\""+row.inspection.id+"\")'>"+StringUtil.escapeStr(row.inspection.jydh)+"</a>";
			}
			htmlContent += "<td class='text-center' title='"+zjztTitle+"'>"+zjztContent+"</td>";  
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.inspection ? (row.inspection.jyr ? row.inspection.jyr.displayName : "") : "")+"'>"+StringUtil.escapeStr(row.inspection ? (row.inspection.jyr ? row.inspection.jyr.displayName : "") : "")+"</td>"; 
			htmlContent += "<td class='text-center' title='"+((row.inspection ? row.inspection.jyrq : "") || "").substr(0, 10)+"'>"+((row.inspection ? row.inspection.jyrq : "") || "").substr(0, 10)+"</td>";  
			
		   	htmlContent += "</tr>";
	   });
	   $("#marterial_stockin_list").html(htmlContent);
	},
	
	/**
	 * 生成仓库下拉框
	 */
	buildStore : function(){
		var this_ = this;
		var dprtcode = $("#dprtcode_search").val();
		var storeHtml = '<option value="" selected="true">显示全部All</option>';
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
	 			storeHtml += "<option value=\""+row.id+"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		})
		$("#store_search").html(storeHtml);
	},
	
	/**
	 * 列表搜索
	 */
	search: function(){
		this.load();
	},
	
	/**
	 * 重置搜索条件
	 */
	searchreset : function(){
		$("#shrq_search").val("");
		$("#hclx_search").val("");
		$("#keyword_search").val("");
		$("#store_search option:first").prop("selected", 'selected');  
		$("#dprtcode_search").val(userJgdm);
	},
	
	/**
	 * 上架
	 */
	putOnShelves : function(id){
		materialShelf.show({
			id : id,
		});
	},
	
	/**
	 * 查看收货单
	 */
	viewReceipt : function(id){
		window.open(basePath + "/material/outin/receipt?id=" + id + "&type=view");
	},
	
	/**
	 * 查看质检单
	 */
	viewInspection : function(id){
		window.open(basePath + "/material/inspection/find/" + id);
	},
};