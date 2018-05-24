function showInfo(pageNumber, sortType, sequence){
	var searchParam={};
	searchParam.mainid=$("#manidinfo").val();
	searchParam.dprtcode=$("#dprtId").val();
	pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20
		};
	AjaxUtil.ajax({
		url : basePath + "/produce/fault/monitoring/getInfoList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtmlInfo(data.rows);
				new Pagination({
					renderTo : "fjgzjkInfo_pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(a, b, c) {
						showInfo(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
			} else {
				$("#dcgzcl_list").empty();
				$("#fjgzjkInfo_pagination").empty();
				$("#dcgzcl_list").append("<tr ><td  class='text-center'  colspan=\"10\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId : 'flight_record_sheet_table'});
		}
	});
}

function appendContentHtmlInfo(list){
		var htmlContent = '';
		$.each(list,function(index,row){
		    htmlContent +="<tr>";
			htmlContent += "<td class='text-center'>";
			htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:fault:monitoring:main:05' title='修改 Edit' onclick=\"Fault_Handling_Record_Update_Modal.open('"+row.id+"')\" ></i>&nbsp;&nbsp;" ;
			htmlContent +="<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:fault:monitoring:main:06' title='删除  Delete' onClick='deleteInfo(\""+ row.id+ "\")' ></i>";
			htmlContent +="</td>";
		    htmlContent += "<td  class='text-left' >"+StringUtil.escapeStr(row.hbh)+"</td>";  
		    htmlContent += "<td  class='text-center' >"+(StringUtil.escapeStr(row.hbrq)!=""?StringUtil.escapeStr(row.hbrq).substring(0,10):"")+"</td>";
		    htmlContent += "<td  class='text-left' >"+(StringUtil.escapeStr(row.pgsl))+"</td>";
		    htmlContent += "<td  class='text-left' >"+(StringUtil.escapeStr(row.cljg))+"</td>";
		    htmlContent += "<td  class='text-left' >"+StringUtil.escapeStr(row.zlh)+"</td>";
		    htmlContent += "<td  class='text-left' >"+(StringUtil.escapeStr(row.cxjxx))+"</td>";
		    htmlContent += "<td  class='text-left' >"+(StringUtil.escapeStr(row.zsjxx))+"</td>";
		    htmlContent += "<td  class='text-left' >"+(StringUtil.escapeStr(row.bz))+"</td>";
		    htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) 
					|| (row.gkfjid != null && row.gkfjid != "")
					|| (row.gznrfjid != null && row.gznrfjid != "")){
					htmlContent += '<i qtid="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
			htmlContent += "</td>";
		    htmlContent += "</tr>";
		    $("#dcgzcl_list").empty();
		    $("#dcgzcl_list").html(htmlContent);
		    initWebuiPopover();
		    TableUtil.addTitle("#dcgzcl_list tr td");
	});
}
 

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#work_card_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}

function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('qtid'), type : '附件'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}


/**
 *删除
 */
function deleteInfo(id,zt){
	var obj = {};
	var paramsMap = {};
//	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	obj.mainid=$("#manidinfo").val();
	obj.whdwid=$("#whdwid").val();
	obj.whrid=$("#gbrid").val();
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
	 startWait();
   	 AjaxUtil.ajax({
   		url:basePath + "/produce/fault/monitoring/deleteInfo",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		success:function(data){
 			finishWait();
 			AlertUtil.showMessage("删除成功!");
 			decodePageParam();
 		}
   	  });
 }});
}
 	
 