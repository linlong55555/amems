var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var StockAddData = [];
var stockbjh;
//打开库存列表对话框
function openStockAdd(obj) {
	stockbjh=obj;
	$("#keyword_otherStock_search").val(obj);
	searchStockAdd();
	//goPageStockAdd(1,"auto","desc");
	$("#alertModalStockAdd").modal("show");

}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageStockAdd(pageNumber,sortType,sequence){
	AjaxStockAddSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchStockAdd(){
	
	goPageStockAdd(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxStockAddSearch(pageNumber,sortType,sequence){
	
	var searchParam = getStockAddSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/otheraerocade/borrow/inventoryList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendStockAddContentHtml(data.rows);
			   
			 	 new Pagination({
						renderTo : "StockAddPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxStockAddSearch(a,b,c);
						}
					});  
			   
			   StockAddData = data.rows;
			   // 标记关键字
			   signByKeyword($("#keyword_otherStock_search").val(),[3,4,5]);
		   } else {
			   StockAddData = [];
			   $("#StockAdd").empty();
			   $("#StockAddPagination").empty();
			   $("#StockAdd").append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
//将搜索条件封装 
function getStockAddSearchParam(){
	 var searchParam = {};
	 
	 var hclx = $.trim($("#other_hclx_search").val());
	 var keyword = $.trim($("#keyword_otherStock_search").val());
	
	 if('' != keyword){
		 searchParam.keyword = keyword;
	 }
	 if('' != hclx){
		 searchParam.hclx = hclx;
	 }
	 return searchParam;
}

function appendStockAddContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  >";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
		   }
		   
		   htmlContent = htmlContent + "<td class='text-center'>"+ "<i class='color-blue icon-signout cursor-pointer' onClick=\"selectOtherStock('"
			+ row.id + "')\" title='添加航材'></i>"+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(index+1)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.bjh)+"</td>";
		   htmlContent = htmlContent + "<td title='"+formatUndefine(row.ywms)+"'>"+formatUndefine(row.ywms)+"</td>";
		   htmlContent = htmlContent + "<td title='"+formatUndefine(row.zwms)+"'>"+formatUndefine(row.zwms)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(gljb[row.gljb])+"</td>";  
		   
		   if(formatUndefine(row.sn) != ""){
				htmlContent += "<td class='text-left'>"+formatUndefine(row.sn)+"</td>"; 
			}else{
				htmlContent += "<td class='text-left'>"+formatUndefine(row.pch)+"</td>"; 
			}
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#StockAdd").empty();
	   $("#StockAdd").html(htmlContent);
	 
}

function getSelectRows(id){
	var rowData = {};
	$.each(StockAddData,function(index,row){
		if(id == row.id){
			rowData = row;
		}
	});
	return rowData;
}

