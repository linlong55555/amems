/**
 * 借出归还入库
 */
var lend_return = {
	id: "lend_return",
	dprtcode:"",
	dicts:[],
	pageSize: 20,
	pagination:{},
	encodePageParam:function(){
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		params.jddxlx = $("#"+this.id+"_jddxlx").val();
		params.jddxid = $("#"+this.id+"_jddx").val();
		params.dprtcode = $("#"+this.id+"_dprtcode").val();
		pageParam.params = params;
		pageParam.pagination = this.pagination;
		return Base64.encode(JSON.stringify(pageParam));
	},
	//初始化
	init:function(){
		var this_ = this;
		this.dprtcode = $("#"+this.id+"_dprtcode").val();
		//加载页面下拉单数据
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/init/lend",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			success:function(data){
				if(data.dicts){
					this_.dicts = data.dicts;
					if(data.dicts.jddxlx){
						var optionHtml = "<option value=''>显示全部All</option>";
						$.each(data.dicts.jddxlx, function(){
							optionHtml += "<option value='"+this.id+"'>"+StringUtil.escapeStr(this.name)+"</option>";
						});
						$("#"+this_.id+"_jddxlx").html(optionHtml);
					}
				}
				this_.initComplete();
		    }
		});
		$("#"+this_.id+"_jddxlx").change(function(e, callback){
			var _value = $(this).val();
			if(_value == ""){
				$("#"+this_.id+"_jddx").html("<option value=''>显示全部All</option>");
				if(callback){callback();}
				return;
			}
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/secondment/list/type",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data: JSON.stringify({jddxlx: _value, dprtcode : $("#"+this_.id+"_dprtcode").val()}),
				success:function(data){
					var optionHtml = "<option value=''>显示全部All</option>";
					if(data){
						$.each(data, function(){
							optionHtml += "<option value='"+this.id+"'>"+StringUtil.escapeStr(this.jddxms)+"</option>";
						});
					}
					$("#"+this_.id+"_jddx").html(optionHtml);
					if(callback){callback();}
			    }
			});
		});
		$("#"+this_.id+"_dprtcode").on("change",function(){
			$("#"+this_.id+"_jddxlx").trigger("change");
		});
		//加载数据
	},
	initComplete:function(){
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#"+this.id+"_keyword_search").val(params.keyword);
			$("#"+this.id+"_dprtcode").val(params.dprtcode);
			$("#"+this.id+"_jddxlx").val(params.jddxlx);
			var this_ = this;
			$("#"+this.id+"_jddxlx").trigger("change", function(){
				$("#"+this_.id+"_jddx").val(params.jddxid);
				if(pageParamJson.pagination){
					this_.load(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
				}else{
					this_.load();
				}
			});
		}catch(e){
			this.load();
		}
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
		
		var obj ={};
		this.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:this.pageSize};
		obj.pagination = this.pagination;
		$.extend(obj, this.getParams());
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/instock/list/lend",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				if(data.rows && data.rows.length >0){
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					});
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[4,5,6], "#"+this_.id+"_list tr td");
						   
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				}
				finishWait();
				new Sticky({tableId:'jcghrk'});
		    }
		}); 
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		params.paramsMap = paramsMap;
		
		var secondment = {};
		secondment.jddxlx = $("#"+this.id+"_jddxlx").val();
		secondment.id = $("#"+this.id+"_jddx").val();
		params.secondment = secondment;
		
		params.dprtcode = $("#"+this.id+"_dprtcode").val();
		return params;
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		
		var page = this;
		$.each(list,function(index,row){
			htmlContent += "<tr   style=\"cursor:pointer\"  ;  >";
			htmlContent += "<td class='fixed-column' class='text-center'><div>";
			htmlContent += "<i class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:instock:main:02' onClick=\""+page.id+".instock('"+row.dprtcode+"', '"+ row.secondment.id + "', '"+formatUndefine(row.hcMainData?row.hcMainData.id:'')+"')\" title='入库 Stock in'></i>&nbsp;&nbsp;"
			htmlContent += "</div></td>";
			
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.secondment ? row.secondment.jddxms:'')+"' align='left'>"+StringUtil.escapeStr(row.secondment ? row.secondment.jddxms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.bjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.ywms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.zwms:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.cjjh:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+page.formatEnum("hclx",formatUndefine(row.hcMainData?row.hcMainData.hclx:''))+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+page.formatEnum("gljb",formatUndefine(row.hcMainData?row.hcMainData.gljb:''))+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.dghsl)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.hcMainData?row.hcMainData.jldw:'')+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode)))+"</td>";  
			htmlContent += "</tr>";  
		});
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").html(htmlContent);
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
		$obj = $("#jcghrk th[name='column_"+sortColumn+"']");
		var orderStyle = $obj.attr("class");
		$("#jcghrk .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#jcghrk .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
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
		
		$("#"+this.id+"_divSearch").find("input").each(function() {
			$(this).val("");
		});

		$("#"+this.id+"_divSearch").find("select").each(function(){
			$(this).val("");
		});
		$("#"+this.id+"_dprtcode").val(this.dprtcode);
		$("#"+this.id+"_jddxlx").trigger("change");
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
	/**
	 * 入库
	 * @param dprtcode 组织机构
	 * @param secondmentId 借调对象ID
	 * @param bjId 部件ID
	 */
	instock: function(dprtcode, secondmentId, bjId){
		window.location = basePath+"/aerialmaterial/instock/view/lend/edit/"+dprtcode+"||"+secondmentId+"||"+bjId+"?pageParam="+this.encodePageParam();
	}
}
$('.date-range-picker').daterangepicker().prev().on("click",
function() {
	$(this).next().focus();
});
