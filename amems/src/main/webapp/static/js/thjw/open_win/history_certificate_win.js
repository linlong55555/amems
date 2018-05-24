
history_certificate_alert_Modal = {
	id : 'history_certificate_alert_Modal',
	attachMap : {},
	getHistoryCertificateList : function(jsonData){
		var this_ = this;
		var searchParam = jsonData;
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/produce/componenthistory/queryCertificateList",
			type:"post",
			async : false,
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				finishWait();
				if(data.rows.length > 0){
					this_.appendContentHtml(data.rows);
				}else{
					
					$("#certigicate_List", $("#"+this_.id)).html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
				}
			}
		});
		return $("#"+this.id).html();
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
				if(row.attachments !=''){
					$.each(row.attachments,function(index,attachment){
						htmlContent += "<tr onclick='history_certificate_alert_Modal.showChildTR(this,\""+row.id+"\")' bgcolor='#f9f9f9'>";
						htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"'>"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"</td>";
						htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"</td>";
						htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"'>"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"</td>";
						htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"'>"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"</td>";
						htmlContent += "<td  class='text-center' title='附件 Attach'><a href=\"javascript:history_certificate_alert_Modal.downloadfile('"+StringUtil.escapeStr(attachment.id)+"')\">"+StringUtil.escapeStr(attachment.wbwjm)+"."+StringUtil.escapeStr(attachment.hzm)+"</a></td>"
						htmlContent += "</tr>";
					});
				}else{
					htmlContent += "<tr onclick='history_certificate_alert_Modal.showChildTR(this,\""+row.id+"\")' bgcolor='#f9f9f9'>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"'>"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"</td>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"</td>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"'>"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"</td>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"'>"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"</td>";
					htmlContent += "<td  class='text-center' title='附件 Attach'></td>";
					htmlContent += "</tr>";
				}
			    	
		});
		$("#certigicate_List", $("#"+this_.id)).html(htmlContent);
	},
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
}