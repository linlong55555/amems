/**
 * 外飞行队借入归还出库单
 */
var borrow_return_outstock = {
	id:'borrow_return_outstock',
	dprtcode:'',//默认组织机构
	pageSize: 20,
	dicts:{}, 
	dprts:[],
	dprts:[],
	pagination:{},
	encodePageParam:function(){
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		params.fxd = $("#"+this.id+"_fxd").val();
		pageParam.params = params;
		pageParam.pagination = this.pagination;
		return Base64.encode(JSON.stringify(pageParam));
	},
	init: function(){
		this.dprtcode = $("#"+this.id+"_dprtcode").val();
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#"+this.id+"_keyword_search").val(params.keyword);
			$("#"+this.id+"_fxd").val(params.fxd);
			if(pageParamJson.pagination){
				this.load(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				this.load();
			}
		}catch(e){
			this.load();
		}
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
			url:basePath+"/otheraerocade/outstock/list",
			type: "post",
			dataType: "json",
			data: obj,
			success:function(data){
				if(data.rows && data.rows.length > 0){
					this_.dicts = data.dicts;
					this_.dprts = data.ext?(data.ext.departments?data.ext.departments:[]):[];
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					});
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[4, 6], "#"+this_.id+"_list tr td");
						   
	 			} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
				}
				finishWait();
				new Sticky({tableId:'borrow_return_outstock_table'});
		    }
		}); 
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		paramsMap.fxdid = $("#"+this.id+"_fxd").val();
		params.paramsMap = paramsMap;
		return params;
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			htmlContent += "<tr>";
			htmlContent += "<td class='text-center fixed-column'><div>";
			var rkdid = row.instock?formatUndefine(row.instock.id):"";
			var rkdh = row.instock?formatUndefine(row.instock.rkdh):"";
			if(rkdid === ""){//没有入库单的显示入库按钮
				htmlContent += "<i class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='otheraerocade:outstock:main:01' onClick='"+this_.id+".instock(\""+row.id+"\");' title='入库 Stock in'></i>";
			}
			htmlContent += "</div></td>";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+(index+1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(this_.formatDept(formatUndefine(row.dprtcode)))+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'><a href='javascript:void(0);' onclick=\""+this_.id+".showDetail('"+row.id+"');\">"+StringUtil.escapeStr(row.ckdh)+"</a></td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(row.cksj).substr(0,10)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.jdfzr)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='center'><a href='javascript:void(0);' onclick=\""+this_.id+".viewDetail('"+rkdid+"');\">"+StringUtil.escapeStr(rkdh)+"</a></td>";  
			htmlContent += "</tr>";  
		});
		$("#"+this.id+"_list").empty();
		$("#"+this.id+"_list").html(htmlContent);
	},
	formatDept:function(dprtcode){
		var val = "";
		var this_ = this;
		$.each(this_.dprts, function(index, row){
			if(dprtcode == row.id){
				val = row.dprtname;
				return false;
			}
		});
		return val;
	},
	//列表头排序
	orderBy: function(sortColumn, _obj){
		$obj = $("#"+this.id+"_table th[name='column_"+sortColumn+"']");
		var orderStyle = $obj.attr("class");
		$("#"+this.id+"_table .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#"+this.id+"_table .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
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
	//查看外飞行队出库详情
	showDetail: function(id){
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/otheraerocade/outstock/detail/list/"+id,
			type: "post",
			dataType: "json",
			success:function(data){
				if(data.rows && data.rows.length > 0){
					var dicts = data.dicts;
//					this_.appendContentHtml(data.rows);
					var htmlContent = '';
					$.each(data.rows, function(index,row){
						htmlContent += "<tr >";
						htmlContent += "<td style='vertical-align: middle;' align='center'>"+(index+1)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left' title='"+StringUtil.escapeStr(row.materialHistory.bjh)+"'>"+StringUtil.escapeStr(row.materialHistory.bjh)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left' title='"+StringUtil.escapeStr(row.materialHistory.ywms)+"'>"+StringUtil.escapeStr(row.materialHistory.ywms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left' title='"+StringUtil.escapeStr(row.materialHistory.zwms)+"'>"+StringUtil.escapeStr(row.materialHistory.zwms)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left'>"+this_.formatEnum(dicts, "hclx", formatUndefine(row.hclx))+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left' title='"+(row.materialHistory.sn?StringUtil.escapeStr(row.materialHistory.sn):StringUtil.escapeStr(row.materialHistory.pch))+"'>"+(row.materialHistory.sn?StringUtil.escapeStr(row.materialHistory.sn):StringUtil.escapeStr(row.materialHistory.pch))+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='right'>"+formatUndefine(row.materialHistory.kcsl)+"</td>";  
						htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.materialHistory.jldw)+"</td>";  
						htmlContent += "</tr>";  
					});
					$("#"+this_.id+"_detail_list").html(htmlContent);
					$("#"+this_.id+"_detail_modal").modal("show");
	 			} else {
					$("#"+this_.id+"_detail_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				}
				finishWait();
		    }
		}); 
	},
	//枚举转化
	formatEnum: function(enums, enumType, value){
		if(value == ""){
			return "";
		}
		if(enums[enumType]){
			var text = value;
			$.each(enums[enumType], function(){
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
	//入库
	instock: function(id){
		startWait();
		window.location = basePath+"/otheraerocade/outstock/instock/"+id+"?pageParam="+this.encodePageParam();
	},
	viewDetail: function(id){
		window.open(basePath+"/aerialmaterial/instock/view/detail/" + id);
	}
};

$(document).ready(function(){
	Navigation(menuCode);
	

	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				borrow_return_outstock.load();//调用主列表页查询
			}
		}
	});
	
	borrow_return_outstock.init();
//	
//	var fxdItems = DicAndEnumUtil.getDicItems(21);
//	if(fxdItems){
//		 var len = fxdItems.length;
//		 var html = "";
//		 for(i=0;i<len;i++){
//			 if(fxdItems[i].id == userJgdm){
//				 continue;
//			 }
//			 html += "<option value="+fxdItems[i].id+" >"+fxdItems[i].name+"</option>";
//		 }
//		 $('#borrow_return_outstock_fxd').append(html);
//	}
	
});
