
var orderNumber = 1;

$(function() {
	Navigation(menuCode,"合同付款","Payment");//初始化导航
	changeContractType();
	goContractPay(1,"auto","desc");//开始的加载默认的内容 
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
	var searchParam = {};
	searchParam.gyslb = type;
	searchParam.dprtcode = $.trim($("#dprtId").val());
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
			  $("#contractPayTable").append("<tr style='height:35px;'><td colspan='9'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
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
			   if(userId == row.whrid){ 
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" bz='"+StringUtil.escapeStr(row.bz)+"' fkfssm='"+StringUtil.escapeStr(row.fkfssm)+"' ondblclick=\"openPayWinEdit('" + row.id +"','"+indexOfTime(row.fkrq) +"','"+row.fkfs +"','"+row.fkje + "',this)\" >";
			   }else{
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" >";
			   }
		   } else {
			   if(userId == row.whrid){
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" bz='"+StringUtil.escapeStr(row.bz)+"' fkfssm='"+StringUtil.escapeStr(row.fkfssm)+"' ondblclick=\"openPayWinEdit('" + row.id +"','"+indexOfTime(formatUndefine(row.fkrq)) +"','"+row.fkfs +"','"+row.fkje + "',this)\" >";
			   }else{
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
			   }
		   }
		   
		   htmlContent += "<td class='text-center'>";
		   if(userId == row.whrid){
			   htmlContent += "<button class='line6' onclick=removePay('" + row.id + "')>";
			   htmlContent += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
			   htmlContent += "</button>";
		   }
		   htmlContent += "</td>";
		  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+(index+1)+"</td>";	
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+indexOfTime(formatUndefine(row.fkrq))+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+DicAndEnumUtil.getEnumName('payTypeEnum',row.fkfs) +"</td>";
		   htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+formatUndefine(row.fkje).toFixed(2)+"</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.fkfssm)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
		   htmlContent += "<td title='"+(StringUtil.escapeStr(row.zdr?row.zdr.displayName:''))+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.whsj)+"</td>";
		   htmlContent += "</tr>";  
		    
	});
	$("#contractPayTable").empty();
	$("#contractPayTable").html(htmlContent);

}

/**
 * 打开修改付款窗口
 * @returns
 */
function openPayWinEdit(id,fkrq,fkfs,fkje,this_){
	var bz = $(this_).attr("bz");
	var fkfssm = $(this_).attr("fkfssm");
	var obj = {};
	obj.fkrq = fkrq;
	obj.fkfs = fkfs;
	obj.fkje = fkje;
	obj.fkfssm = fkfssm;
	obj.bz = bz;
	PayModal.show({
		viewObj:obj,//对象，在弹窗中回显
		callback: function(data){//回调函数
			if(data != null){
				var url = basePath+"/aerialmaterial/contract/editPay";
				var mainid = $.trim($("#id").val());
				data.mainid = mainid;
				data.id = id;
				performAction(url,data);
			}
		}
	})
}

/**
 * 打开新增付款窗口
 * @returns
 */
function openPayWinAdd(){
	PayModal.show({
		viewObj:null,//对象，在弹窗中回显
		callback: function(data){//回调函数
			if(data != null){
				var url = basePath+"/aerialmaterial/contract/addPay";
				var mainid = $.trim($("#id").val());
				data.mainid = mainid;
				performAction(url,data);
			}
		}
	})
}

/**
 * 删除付款信息
 * @returns
 */
function removePay(id){
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
	
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/contract/deletePay",
			type:"post",
			async: false,
			data:{
				id : id,
				mainId : $.trim($("#id").val())
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('删除成功!');
				goContractPay(1,"auto","desc");//开始的加载默认的内容 
			}
		});
		
	}});
	
}

function performAction(url,param){
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			goContractPay(1,"auto","desc");//开始的加载默认的内容 
		}
	});
}
//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/contract/main?pageParam="+pageParam;
}