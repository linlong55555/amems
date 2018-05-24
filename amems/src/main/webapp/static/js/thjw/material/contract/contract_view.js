
var orderNumber = 1;
var flag = true;
var materialWinAddData = [];
var oldDetailArr = [];//已拥有的详情id数组

$(function() {
	Navigation(menuCode,"查看合同","View");//初始化导航
	$("#materialTable").append("<tr><td  colspan='19' class='text-center'>暂无数据 No data.</td></tr>");
	changeContractType();
	initContractDetail();
	goContractPay(1,"auto","desc");//开始的加载默认的内容 
	MessageListUtil.show({
		dprtcode:$("#dprtcode").val(),//
		djid:$("#id").val(),
		lx:$("#htlx").val() == 1?3:4
	});//显示留言
	
	if($("#htzt").val()==9){
		$("#zt").append("<option selected='selected'>指定结束</option>");
	}
	AttachmengsListView.show({
		djid:$("#id").val(),
		fileType:"contract"
	});//显示附件
	refreshPermission();
	initToFixed("fjfy");
	initToFixed("htfy");
});

function initToFixed(id){
	var v = 0;
	if($("#"+id).val() != null && $("#"+id).val() != ''){
		v = Number($("#"+id).val());
	}
	v = v.toFixed(2);
	$("#"+id).val(v);
}

//合同类型改变时,加载供应商信息
function changeContractType(){
	var type = $("#htlx").val();
	showorhideColumn();
	$("#materialTable").empty();
	if(type == 1){
		$("#materialTable").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
	}else{
		$("#materialTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
	}
	var searchParam = {};
	searchParam.gyslb = type;
	
	if($.trim($("#contractDprtcode").val()) == ""){
		searchParam.dprtcode = $.trim($("#dprtId").val());
	}else{
		searchParam.dprtcode = $.trim($("#contractDprtcode").val());
	}
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryFirmList",
	   type: "post",
	   async:false,
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   $("#gys").empty();
	   var gysid = $.trim($("#gysid").val());
	   var supplierOption = '';
	   if(data.length >0){
		   $.each(data,function(index,row){
			   if(gysid == row.id){
				   supplierOption += '<option value="'+row.id+'" selected=true >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }else{
				   supplierOption += '<option value="'+row.id+'" >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }
		   });
	   }
	   $("#gys").append(supplierOption);
     }
   }); 
}

//初始化合同详情
function initContractDetail(){
	var type = $("#htlx").val();
	var url = basePath+"/aerialmaterial/contract/queryReserveContractDetailList";
	if(type == 2){
		url = basePath+"/aerialmaterial/contract/queryRepairContractMaterialList";
	}
	AjaxUtil.ajax({
		async: false,
		url:url,
		type:"post",
		async:false,
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data != null && data.length > 0){
				initTableRow(data);
			}
		}
	});
}
//初始化合同详情列表
function initTableRow(data){
	
	//先移除暂无数据一行
	var len = $("#materialTable").length;
	if (len == 1) {
		if($("#materialTable").find("td").length == 1){
			$("#materialTable").empty();
		}
	}
	$.each(data,function(index,row){
		row.orderNumber = orderNumber++;
		oldDetailArr.push(row.ID);
		addRow(row);
	});
	
}

//向table新增一行
function addRow(obj){
	var type = $("#htlx").val();
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.ID+"'/>";
		tr += 	  "<input type='hidden' name='tddid' value='"+obj.TDDID+"'/>";
		tr += 	  "<input type='hidden' name='tddhcid' value='"+obj.TDDHCID+"'/>";
		tr += 	  "<input type='hidden' name='bjid' value='"+obj.BJID+"'/>";
		tr += 	  "<input type='hidden' name='tdsl' value='"+formatUndefine(obj.TDSL)+"'/>";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SQDH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.BJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.BJH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.YWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.YWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.ZWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ZWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.CJJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.CJJH)+"</td>";
		
		var totalPrice = 0;
		
		if(type == 1){
			tr += "<td title='"+formatUndefine(obj.HTSL)+"' style='text-align:right;vertical-align:middle;'>";
			tr += formatUndefine(obj.HTSL);
			tr += "</td>";
			tr += "<td title='"+(formatUndefine(obj.BJ_CLF).toFixed(2))+"' style='text-align:right;vertical-align:middle;'>";
			tr += formatUndefine(obj.BJ_CLF).toFixed(2);
			tr += "</td>";
			if(formatUndefine(obj.BJ_CLF) != ''){
				totalPrice = formatUndefine(obj.BJ_CLF) * formatUndefine(obj.HTSL);
			}
		}else{
			tr += "<td title='"+(formatUndefine(obj.BJ_CLF).toFixed(2))+"' style='text-align:center;vertical-align:middle;'>"+formatUndefine(obj.SN)+"</td>";
			tr += "<td style='text-align:right;vertical-align:middle;'>";
			tr += formatUndefine(obj.BJ_CLF).toFixed(2);
			tr += "</td>";
			tr += "<td title='"+(formatUndefine(obj.BJ_GSF).toFixed(2))+"' style='text-align:right;vertical-align:middle;'>";
			tr += formatUndefine(obj.BJ_GSF).toFixed(2);
			tr += "</td>";
			tr += "<td title='"+(formatUndefine(obj.BJ_QTF).toFixed(2))+"' style='text-align:right;vertical-align:middle;'>";
			tr += formatUndefine(obj.BJ_QTF).toFixed(2);
			tr += "</td>";
			totalPrice = formatUndefine(obj.BJ_CLF) + formatUndefine(obj.BJ_GSF) + formatUndefine(obj.BJ_QTF);
		}
		totalPrice = totalPrice.toFixed(2);
		tr += "<td title='"+totalPrice+"' name='totalPrice' style='text-align:right;vertical-align:middle;'>"+totalPrice+"</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "	<div class='col-xs-12 padding-left-0 padding-right-0'>";
		if(formatUndefine(obj.YJDHRQ) != ''){
			tr += indexOfTime(formatUndefine(obj.YJDHRQ));
		}
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td title='"+(StringUtil.escapeStr(obj.USERNAME)+" "+StringUtil.escapeStr(obj.REALNAME))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.USERNAME)+" "+StringUtil.escapeStr(obj.REALNAME)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>"+formatUndefine(obj.SQSJ)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(formatUndefine(obj.YQQX))+"</td>";
		
		tr += "</tr>";
		
	$("#materialTable").append(tr);

}

function getProcurementCatalog(bjIdList){
	var procurementCatalogList = [];
	var searchParam = {};
	searchParam.gysid = $.trim($("#gys").val());
	searchParam.dprtcode = $.trim($("#dprtId").val());
	searchParam.bjIdList = bjIdList;
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/enquiry/queryProcurementCatalogList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   async:false,
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   if(data != null && data.length >0){
		   procurementCatalogList = data;
	   }
     }
	}); 
 	return procurementCatalogList;
}

function showorhideColumn(){
	var type = $("#htlx").val();
	if(type == 1){
		$("#contractHead tr th:eq(1)").show();
		$("#contractHead tr th:eq(8)").show();
		$("#contractHead tr th:eq(2)").hide();
		$("#contractHead tr th:eq(7)").hide();
		$("#contractHead tr th:eq(9)").show();
		$("#contractHead tr th:eq(10)").hide();
		$("#contractHead tr th:eq(11)").hide();
		$("#contractHead tr th:eq(12)").hide();
	}else{
		$("#contractHead tr th:eq(1)").hide();
		$("#contractHead tr th:eq(8)").hide();
		$("#contractHead tr th:eq(2)").show();
		$("#contractHead tr th:eq(7)").show();
		$("#contractHead tr th:eq(9)").hide();
		$("#contractHead tr th:eq(10)").show();
		$("#contractHead tr th:eq(11)").show();
		$("#contractHead tr th:eq(12)").show();
	}
}

//初始化合同付款详情
function goContractPay(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:100};
	startWait();
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/contract/queryContractPayPageList",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async:false,
		data:JSON.stringify(searchParam),
		dataType:"json",
		success:function(data){
			finishWait();
			if(data.total >0){
			   appendContentHtml(data.rows);
			   new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						goContractPay(pageNumber,sortType,sequence);
					}
				}); 
		   } else {
			  $("#contractPayTable").empty();
			  $("#pagination").empty();
			  $("#contractPayTable").append("<tr style='height:35px;'><td colspan='8'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		   }
		}
	});
}

//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.mainid = $.trim($("#id").val());
	 return searchParam;
}

function appendContentHtml(list){
	var userId = $("#userId").val();
	var htmlContent = '';
	$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
		   } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\" >";
		   }
		  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+(index+1)+"</td>";	
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+indexOfTime(formatUndefine(row.fkrq))+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+DicAndEnumUtil.getEnumName('payTypeEnum',row.fkfs) +"</td>";
		   htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+formatUndefine(row.fkje).toFixed(2)+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.fkfssm)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' <td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
		   htmlContent += "<td title='"+(StringUtil.escapeStr(row.zdr?row.zdr.displayName:''))+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.whsj)+"</td>";
		   htmlContent += "</tr>";  
		    
	});
	$("#contractPayTable").empty();
	$("#contractPayTable").html(htmlContent);

}
//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/contract/main?pageParam="+pageParam;
}