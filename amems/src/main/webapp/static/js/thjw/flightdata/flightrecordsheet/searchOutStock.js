
var outStockItems = [];

//打开拆下件对话框
function searchOutStockModal(btn) {
	$("#outStockModal").modal("show");
	goPageOutStock(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageOutStock(pageNumber,sortType,sequence){
	AjaxOutStockSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchOutStockList(){
	goPageOutStock(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxOutStockSearch(pageNumber,sortType,sequence){
	var searchParam = getOutStockSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/outfieldstock/queryMountComponent",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendOutStockContentHtml(data.rows);
			   if($("#forwordType").val() != 3){
				   new Pagination({
						renderTo : "outStockPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxOutStockSearch(pageNumber,sortType,sequence);
						}
					}); 
			   }
			   FormatUtil.removeNullOrUndefined();
			   // 标记关键字
			   signByKeyword($("#out_stock_search").val(),[2,3,4,5,6],"#outStockList tr td");
		   } else {
			   $("#outStockList").empty();
			   $("#outStockPagination").empty();
			   $("#outStockList").append("<tr><td colspan=\"7\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
//将搜索条件封装 
function getOutStockSearchParam(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#out_stock_search").val());
	 searchParam.dprtcode = $("#dprtcode").val();
	 
	 var notIds = [];
	 for(var i = 0; i < totalData.finishedWork.length; i++){
		var dismountRecord = totalData.finishedWork[i].dismountRecord;
		for(var j = 0; j < dismountRecord.length; j++){
			if(dismountRecord[j].zsWckcid){
				notIds.push(dismountRecord[j].zsWckcid);
			}
		}
	 }
	 searchParam.notIds = notIds;
	 return searchParam;
}


	function appendOutStockContentHtml(list){
		var htmlContent = '';
		outStockItems = list;
		$.each(list,function(index,row){
			htmlContent += ["<tr style='cursor:pointer' bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
			                   "<td class='text-center'>",
			                   "<input name='outStock' type='radio' value='"+row.id+"'>",
			                   "</td>",
			                   "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>",
			                   "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>",
			                   "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>",
			                   "<td title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>",
			                   "<td title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>",
			                   "<td title='"+row.kcsl+"'>"+row.kcsl+"</td>",
			                   "</tr>"
			                   ].join("");
		   $("#outStockList").empty();
		   $("#outStockList").html(htmlContent);
	   });
	}
	
	/**
	 * 绑定行选择事件
	 */
	$("#outStockList > tr").live("click", function(){
		if($("#forwordType").val() != 3){
			   $(this).find("input[type='radio']").attr("checked",true);
		 }
	});
	
	 function moreOutStock() {
		if ($("#divSearchOutStock").css("display") == "none") {
			$("#divSearchOutStock").css("display", "block");
			$("#iconOutStock").removeClass("icon-caret-down");
			$("#iconOutStock").addClass("icon-caret-up");
		} else {

			$("#divSearchOutStock").css("display", "none");
			$("#iconOutStock").removeClass("icon-caret-up");
			$("#iconOutStock").addClass("icon-caret-down");
		}
	}
	 
	 //搜索条件重置
	function outStockSearchreset(){
		$("#divSearchOutStock").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearchOutStock").find("textarea").each(function(){
			$(this).val("");
		});
		 
		$("#divSearchOutStock").find("select").each(function(){
			$(this).val("");
		});
		$("#out_stock_search").val("");
	 }
	
	function setOutStock(){
		var select = $("#outStockList input:checked");
		// 验证是否选择
		if(select.length == 0){
			AlertUtil.showErrorMessage("请选择外场库存数据！");
			return false;
		}
		var id = select.val();
		console.log(id);
		console.log(outStockItems);
		for(var i = 0; i < outStockItems.length; i++){
			if(id == outStockItems[i].id){
				setMountComponentByOutStock(outStockItems[i]);
			}
		}
		$("#outStockModal").modal("hide");
	}

