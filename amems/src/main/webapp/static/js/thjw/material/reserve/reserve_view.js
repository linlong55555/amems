var orderNumber = 1;
var flag = true;

$(function() {
	Navigation(menuCode,"查看航材提订单","View");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	$("#reserveTable").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
	initReserveDetail();
	initWorkOrderList();
	refreshPermission();
	MessageListUtil.show({
		djid:$("#id").val(),
		dprtcode:$("#dprtcode").val(),//
		lx:1
	});//显示留言
	
	AttachmengsListView.show({
		djid:$("#id").val(),
		fileType:"reserve"
	});//显示附件
});

//初始化提订航材
function initReserveDetail(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/reserve/queryReserveDetailListByMainId",
		type:"post",
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data.length > 0){
				initTableRow(data);
			}
		}
	});
}
//初始化提订航材列表
function initTableRow(data){
	
	//先移除暂无数据一行
	var len = $("#reserveTable").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length == 1){
			$("#reserveTable").empty();
		}
	}
	$.each(data,function(index,reserve){
		var row = reserve.hcMainData;
		row.orderNumber = orderNumber++;
		row.sqsl = reserve.sqsl;
		row.yt = reserve.yt;
		row.xjzt = reserve.xjzt;
		row.shsl = (reserve.shsl == 0?"":reserve.shsl);
		row.detailId = reserve.id;
		row.xjdh = reserve.xjdh;
		row.zjhStr = "";
		if(row.fixChapter != null){
			row.zjhStr = formatUndefine(row.fixChapter.zjh)+" "+formatUndefine(row.fixChapter.zwms);
		}
		addRow(row);
	});
	
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
		tr += "<tr id='"+obj.detailId+"'>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='detailId' value='"+obj.detailId+"'/>";
		tr += 	  "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(obj.bjh)+"'/>";
		tr += "</td>";
		
		tr +=  "<td title='"+StringUtil.escapeStr(obj.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.cjjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cjjh)+"</td>";
		
		if('00000' == StringUtil.escapeStr(obj.xh)){
			tr += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
	    }else{
	    	tr += "<td title='"+formatUndefine(obj.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.xh)+"</td>";
	    }
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zjhStr)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialSecondTypeEnum',obj.hclxEj) +"</td>";
		tr += "<td title='"+formatUndefine(obj.sqsl)+"' style='text-align:right;vertical-align:middle;' name='sqsl'>"+formatUndefine(obj.sqsl)+"</td>";
		tr += "<td title='"+formatUndefine(obj.shsl)+"' style='text-align:right;vertical-align:middle;' name='shsl' >"+formatUndefine(obj.shsl)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw) +"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.yt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.yt)+"</td>";	
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		if(obj.xjzt == 0){
			tr += "未询价";
		}
		if(obj.xjzt == 1){
			if($("#reserveStatus").val() == 9){
				tr += "指定结束";
			}else{
				tr += "询价中";
			}
		}
		if(obj.xjzt == 2){
			tr += "<a href='javascript:void(0);' onclick=\"openEnquiryWin('"+obj.detailId+"')\">"+StringUtil.escapeStr(obj.xjdh)+"</a>";
		}
		tr += "</td>";
		tr += "</tr>";
	
	$("#reserveTable").append(tr);
}

//打开询价对话框
function openEnquiryWin(id){
	var sqsl = $("#"+id).find("td[name='sqsl']").html();
	var materialSl = $("#"+id).find("td[name='shsl']").html();
	if(materialSl == '' || materialSl == 0){
		materialSl = Number(sqsl);
	}
	MaterialEnquiryModal.show({
		mainid:id,//在弹窗中作为条件mainid
		enquiryType:1,//询价类型
		materialSl:materialSl,//数量
		callback: function(data){//回调函数
		}
	})
}

//初始化相关工单
function initWorkOrderList(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/reserve/queryWorkOrderListByMainId",
		type:"post",
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data.length > 0){
				var workOrder = formatWorkCard(data);
				$("#workOrder").html(workOrder);
			}
		}
	});
}

function formatWorkCard(data){
	var card = '';
	$.each(data,function(i,s){
		card += "<a href='#' onclick='viewWorkCard(\""+s.gdid+"\")'>"+StringUtil.escapeStr(s.gdbh)+"</a>";
		if(i != data.length - 1){
			card += ",";
		}
	});
	return card;
}

function viewWorkCard(id){
	window.open(basePath+"/produce/workorder/woView?gdid="+id);
}
//转换工单类型
function transGdlx(gdlx){
	if(gdlx == 10){
		return 1
	}else if(gdlx == 20){
		return 3
	}else {
		return 2;
	}
}

//返回前页面
function back(){
	if(type == 'manage'){
		window.location.href =basePath+"/aerialmaterial/reserve/manage?pageParam="+pageParam;
	}
	if(type == 'approve'){
		window.location.href =basePath+"/aerialmaterial/reserve/approve?pageParam="+pageParam;
	}
	 
}
