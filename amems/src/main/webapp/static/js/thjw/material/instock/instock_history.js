
var instock_history = {
	id:'instock_history',
	dprtcode:'',//默认组织机构
	pageSize: 20,
	dicts:{},
	pagination:{},
	init: function(){
		this.dprtcode = $("#"+this.id+"_dprtcode").val();
		DicAndEnumUtil.registerEnum("instockTypeEnum", "instock_history_rklx");
		DicAndEnumUtil.registerEnum("instockStatusEnum", "instock_history_zt");
		var this_ = this;
		this.load();
	},
	load: function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){ pageNumber = 1; }
		if(typeof(sortColumn) == "undefined"){ sortColumn = "auto";} 
		if(typeof(orderType) == "undefined"){ orderType = "desc"; } 
		
		this.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:this.pageSize};

		var obj ={};
		obj["pagination.page"] = pageNumber;
		obj["pagination.sort"] = sortColumn;
		obj["pagination.order"] = orderType;
		obj["pagination.rows"] = this.pageSize;
		$.extend(obj, this.getParams());
		
		if(id != ""){
			obj.paramsMap.id = id;
			id = "";
		}
		
		startWait();
		
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/list",
			type: "post",
			dataType: "json",
			data: obj,
			success:function(data){
				finishWait();
				if(data.rows && data.rows.length > 0){
					this_.dicts = data.dicts;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					});
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[4], "#"+this_.id+"_list tr td");
						   
	 			} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").html("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId:'instock_history_table'});
		    }
		}); 
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		paramsMap.rklx = $("#"+this.id+"_rklx").val();
		paramsMap.zt = $("#"+this.id+"_zt").val();
		paramsMap.dprtcode = $("#"+this.id+"_dprtcode").val();
		var betweenDate = $("#"+this.id+"_between_date").val();
		if(null != betweenDate && "" != betweenDate){
			paramsMap.dateBegin = betweenDate.substring(0,4)+"-"+betweenDate.substring(5,7)+"-"+betweenDate.substring(8,10);
			paramsMap.dateEnd = betweenDate.substring(12,17)+"-"+betweenDate.substring(18,20)+"-"+betweenDate.substring(21,23);
		}
		params.paramsMap = paramsMap;
		return params;
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			htmlContent += "<tr style=\"cursor:pointer\"  ;  >";
			htmlContent += "<td class='text-center fixed-column'><div>";
			if(row.zt == 2){//提交状态显示撤消
				htmlContent += "<i class='icon-signout color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:instock:main:03' onClick='"+this_.id+".cancel(\""+row.id+"\");' title='撤销 Undo'></i>";
			}
			htmlContent += "</div></td>";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'>"+this_.formatEnum('rklx', formatUndefine(row.rklx))+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'><a href='javascript:void(0);' onclick=\""+this_.id+".viewDetail('"+row.id+"');\">"+StringUtil.escapeStr(row.rkdh)+"</a></td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'>"+this_.formatEnum('zt', formatUndefine(row.zt))+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.secondment?row.secondment.jddxms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.jdfzr)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+(StringUtil.escapeStr(row.fjzch)=='00000'?'通用Currency':StringUtil.escapeStr(row.fjzch))+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.sqrUser?row.sqrUser.username+' '+row.sqrUser.realname:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(row.sqsj2)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.rkrUser?row.rkrUser.username+' '+row.rkrUser.realname:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(row.rksj).substr(0,10)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.bz)+"' align='left'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode)))+"</td>";  
			htmlContent += "</tr>";  
		});
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
		var $column = $("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table"));
		var orderStyle = $column.attr("class");
		$(".sorting_desc", $("#"+this.id+"_table_div")).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this.id+"_table_div")).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table_div")).removeClass("sorting").addClass("sorting_desc");
			orderType = "desc"
		} else {
			$("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table_div")).removeClass("sorting").addClass("sorting_asc");
			orderType = "asc"
		}
		var currentPage = $("#"+this.id+"_pagination li[class='active']").text();
		if(currentPage == ""){currentPage = 1;}
		this.load(currentPage, sortColumn, orderType);
	},
	//重置搜索条件
	searchReset: function(){
		$("#"+this.id+"_keyword_search").val("");
		
		$("#"+this.id+"_divSearch").find("input").each(function() {
			$(this).val("");
		});

		$("#"+this.id+"_divSearch").find("select").each(function(){
			$(this).val("");
		});
		$("#"+this.id+"_dprtcode").val(this.dprtcode);
	},
	//查询条件更多 展开/收缩
	more: function() {
		if ($("#"+this.id+"_divSearch").css("display") == "none") {
			$("#"+this.id+"_divSearch").css("display", "block");
			$("#"+this.id+"_icon").removeClass("icon-angle-down");
			$("#"+this.id+"_icon").addClass("icon-angle-up");
		} else {
			$("#"+this.id+"_divSearch").css("display", "none");
			$("#"+this.id+"_icon").removeClass("icon-angle-up");
			$("#"+this.id+"_icon").addClass("icon-angle-down");
		}
	},
	cancel: function(sid){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要撤销该入库单吗？",{callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/instock/cancel",
				type: "post",
				dataType: "json",
				data: {id: sid},
				success:function(data){
					AlertUtil.showMessage("撤销成功");
					id = sid;
					this_.load(this_.pagination.page, this_.pagination.sort, this_.pagination.order);
					finishWait();
			    }
			}); 
			
		}})
	},
	viewDetail: function(id){
		window.open(basePath+"/aerialmaterial/instock/view/detail/" + id);
	}
};
$('.date-range-picker').daterangepicker().prev().on("click",
function() {
	$(this).next().focus();
});
