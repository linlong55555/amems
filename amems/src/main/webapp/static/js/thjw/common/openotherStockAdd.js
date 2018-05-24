var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var otherStockAddData = [];


var existsIdList = [];
//打开航材列表对话框
function openotherStockAdd() {
	existsIdList = [];
	$("#reserveTable").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var hiddenid =$.trim($("input[name='bjh']").eq(index).val());
		existsIdList.push(hiddenid);
	});
	
	
	$("#otherStockModel").html($("#jddx").find("option:selected").text()+"库存列表");
	$("#alertModalotherStockAdd").modal("show");
	console.info(1);
	goPageotherStockAdd(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageotherStockAdd(pageNumber,sortType,sequence){
	AjaxotherStockAddSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchotherStockAdd(){
	
	goPageotherStockAdd(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxotherStockAddSearch(pageNumber,sortType,sequence){
	var searchParam = getotherStockAddSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	
	 AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/stock/otherstockList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.rows!=""){
			   appendotherStockAddContentHtml(data.rows);
			   
				 new Pagination({
						renderTo : "otherStockAddPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					});  
			   
			   otherStockAddData = data.rows;
			   // 标记关键字
			   signByKeyword($("#keyword_otherStock_search").val(),[2,3,4,5]);
		   } else {
			   otherStockAddData = [];
			   $("#otherStockAdd").empty();
			   $("#otherStockAddPagination").empty();
			   $("#otherStockAdd").append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   
		   new Sticky({tableId:'otherStockAddTable'});
     }
   }); 
	
}
//将搜索条件封装 
function getotherStockAddSearchParam(){
	 var searchParam = {};
	 var paramsMap = {};
	 var jddxids = $.trim($("#jddx").val());
	 var dprtcode = (jddxids.split(","))[0];
	 var hclx = $.trim($("#other_hclx_search").val());
	 
	 var keyword = $.trim($("#keyword_otherStock_search").val());
	 if('' != hclx){
		 searchParam.hclx = hclx;
	 }
	 if('' != keyword){
		 searchParam.keyword = keyword;
	 }
	 if('' != hclx){
		 searchParam.hclx = hclx;
	 }
	 if('' != dprtcode){
		 searchParam.dprtcode = dprtcode;
	 }
	 
	 if(existsIdList != '' && existsIdList.length > 0){
		 paramsMap.idList = existsIdList;
	 }
	 searchParam.paramsMap = paramsMap;
	 return searchParam;
}

function appendotherStockAddContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
		   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\"    onClick=\"historyAjaxGetDatasSearch('"+ row.id + "','"+ index + "',this)\">";
		   htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input name='ids' type='checkbox' index='"+index+"'  /></td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(index+1)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.bjh)+"</td>";
		   htmlContent = htmlContent + "<td>"+formatUndefine(row.zwms)+"</td>";
		   htmlContent = htmlContent + "<td>"+formatUndefine(row.ywms)+"</td>";
		   htmlContent = htmlContent + "<td>"+formatUndefine(row.cjjh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(gljb[row.gljb])+"</td>";  
		   if(formatUndefine(row.sn) != ""){
				htmlContent += "<td class='text-left'>"+formatUndefine(row.sn)+"</td>"; 
			}else{
				htmlContent += "<td class='text-left'>"+formatUndefine(row.pch)+"</td>"; 
			}
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.kcsl)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#otherStockAdd").empty();
	   $("#otherStockAdd").html(htmlContent);
}

//带条件搜索的翻页-出库单信息
function historyAjaxGetDatasSearch(id,index,objradio){
	
	var $checkbox1 = $("#otherStockAdd input[name='ids']:eq("+index+")");
	var $checkbox2 = $(".sticky-col input[name='ids']:eq("+index+")");
	var checked = $checkbox1.is(":checked");
	$checkbox1.attr("checked", !checked);
	$checkbox2.attr("checked", !checked);
}
function getSelectRows(id){
	var rowData = {};
	$.each(otherStockAddData,function(index,row){
		if(id == row.bjid){
			rowData = row;
		}
	});
	return rowData;
}

//全选
function checkAll(){
		$("[name=ids]:checkbox").each(function() { 
			 $(this).attr("checked", 'checked'); 
		 });
		 
	}
//全不选
function notCheckAll(){
		 $("[name=ids]:checkbox").each(function() { 
			 $(this).removeAttr("checked"); 
		 });
	}