/**
 * 送修入库
 */
var repair = {
	id: "repair",
	pageSize: 20,
	dicts:{},
	pagination:{},
	encodePageParam:function(){
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		params.betweenDate = $("#"+this.id+"_between_date").val();
		params.dprtcode = $("#"+this.id+"_dprtcode").val();
		pageParam.params = params;
		pageParam.pagination = this.pagination;
		return Base64.encode(JSON.stringify(pageParam));
	},
	init: function(){
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#"+this.id+"_keyword_search").val(params.keyword);
			$("#"+this.id+"_dprtcode").val(params.dprtcode);
			$("#"+this.id+"_between_date").val(params.betweenDate);
			if(pageParamJson.pagination){
				this.load(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				this.load();
			}
		}catch(e){
			this.load();
		}
		this.load();
	},
	//加载数据
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
		
		this.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:this.pageSize};
		var obj ={};
		obj["pagination.page"] = pageNumber;
		obj["pagination.sort"] = sortColumn;
		obj["pagination.order"] = orderType;
		obj["pagination.rows"] = this.pageSize;
		$.extend(obj, this.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/list/repair",
			type: "post",
			dataType:"json",
			data:obj,
			success:function(data){
				if(data.dicts){
					this.page.dicts = data.dicts;
				}
				if(data.rows && data.rows.length > 0){
					this.page.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this.page.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this.page
					});
					// 标记关键字
					signByKeyword($("#"+this.page.id+"_keyword_search").val(),[3,4,6,7,8], "#"+this.page.id+"_list tr td");
				} else {
					$("#"+this.page.id+"_list").empty();
					$("#"+this.page.id+"_pagination").empty();
					$("#"+this.page.id+"_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId:'xsrk'});
				finishWait();
		    },
			page: this
		}); 
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		var betweenDate = $("#"+this.id+"_between_date").val();
		if(null != betweenDate && "" != betweenDate){
			paramsMap.dateBegin = betweenDate.substring(0,4)+"-"+betweenDate.substring(5,7)+"-"+betweenDate.substring(8,10);
			paramsMap.dateEnd = betweenDate.substring(12,17)+"-"+betweenDate.substring(18,20)+"-"+betweenDate.substring(21,23);
		}
		paramsMap.dprtcode = $("#"+this.id+"_dprtcode").val();
		params.paramsMap = paramsMap;
		
		return params;
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		
		var page = this;
		$.each(list,function(index,row){
			htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
			htmlContent += "<td class='fixed-column' style='vertical-align: middle;' class='text-center'><div>";
			htmlContent += "<i class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:instock:main:02' onClick=\""+page.id+".instock('"+ row.id + "')\" title='入库 Stock in'></i>&nbsp;&nbsp;"
			htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:instock:main:01'  onClick=\""+page.id+".cancel('"+ row.id + "')\" title='作废 Invalid'></i>&nbsp;&nbsp;"
			htmlContent += "</div></td>";
			
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'><a href='javascript:void(0);' onclick=\""+page.id+".viewContract('"+formatUndefine((row.contractDetail && row.contractDetail.contract)?row.contractDetail.contract.id:'')+"');\">"+StringUtil.escapeStr((row.contractDetail && row.contractDetail.contract)?row.contractDetail.contract.htlsh:'')+"</a></td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.expatriate?row.expatriate.sn:'')+"' align='left'>"+StringUtil.escapeStr(row.expatriate?row.expatriate.sn:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+page.formatEnum("hclx", formatUndefine(row.hcMainData?row.hcMainData.hclx:''))+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(row.instock?row.instock.sqsj2:'').substring(0,10)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(row.instock?row.instock.department.dprtname:'')+"</td>";  
			htmlContent += "</tr>";  
		});
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").html(htmlContent);
		refreshPermission();
	},
	//枚举转化
	formatEnum: function(enumType, value){
		if(value === ""){
			return "";
		}
		if(this.dicts[enumType]){
			var text = value;
			$.each(this.dicts[enumType], function(){
				if(this.id == value){
					text = this.name;
					return false;
				}
			});
			return text;
		}else{
			return value;
		}
	},
	//列表头排序
	orderBy: function(sortColumn, _obj){
		$obj = $("#xsrk th[name='column_"+sortColumn+"']");
		var orderStyle = $obj.attr("class");
		$("#xsrk .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#xsrk .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
		var orderType = "asc";
		if (orderStyle.indexOf ( "sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this.id+"_pagination li[class='active']").text();
		if(currentPage == ""){currentPage = 1;}
		this.load(currentPage, sortColumn, orderType);
	},
	//重置搜索条件
	searchReset: function(){
		$("#"+this.id+"_keyword_search").val("");
		$("#"+this.id+"_dprtcode").val(userJgdm);
		
		$("#"+this.id+"_divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#"+this.id+"_divSearch").find("textarea").each(function(){
			$(this).val("");
		});
	},
	//查询条件更多 展开/收缩
	more: function() {
		if ($("#"+this.id+"_divSearch").css("display") == "none") {
			$("#"+this.id+"_divSearch").css("display", "block");
			$("#"+this.id+"_icon").removeClass("icon-caret-down");
			$("#"+this.id+"_icon").addClass("icon-caret-up");
		} else {
			$("#"+this.id+"_divSearch").css("display", "none");
			$("#"+this.id+"_icon").removeClass("icon-caret-up");
			$("#"+this.id+"_icon").addClass("icon-caret-down");
		}
	},
	instock: function(id){
		window.location = basePath+"/aerialmaterial/instock/view/repair/edit/"+id+"?pageParam="+this.encodePageParam();
	},
	cancel: function(id){
		var this_ = this;
		AlertUtil.showConfirmMessage("您确定要作废该记录吗",{
			callback: function(){
				AjaxUtil.ajax({
					url:basePath+"/aerialmaterial/instock/discard/repair",
					type: "post",
//					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:{instockDetailId: id},
					success:function(data){
						AlertUtil.showMessage("作废成功");
						this_.load(this_.pagination.page, this_.pagination.sort, this_.pagination.order);
						finishWait();
					}
				}); 
			}
		});
	},
	//查看合同
	viewContract: function(id){
		window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&t=" + new Date().getTime());
	}
}
$('.date-range-picker').daterangepicker().prev().on("click",
function() {
	$(this).next().focus();
});
