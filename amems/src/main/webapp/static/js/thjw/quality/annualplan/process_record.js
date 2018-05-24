

process_record = {
	/**
	 * 加载流程记录
	 */
	load : function(mainid){
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/quality/processrecord/list",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				dprtcode : $.trim($("#dprtcode").val()),
				mainid : mainid,
			}),
			success:function(data){
				this_.appendProcessRecordHtml(data);
		    }
		}); 
	},
	
	/**
	 * 拼接流程记录
	 */
	appendProcessRecordHtml : function(list){
		var html = "";
		if(list.length > 0){
			$(list).each(function(i, obj){
				html += "<tr>";
				html += "<td style='text-align:left;'>"+StringUtil.escapeStr(obj.czrmc)+"</td>";
				html += "<td style='text-align:center;'>"+StringUtil.escapeStr(obj.czsj)+"</td>";
				html += "<td style='text-align:left;'>"+StringUtil.escapeStr(obj.czsm)+"</td>";
				html += "</tr>";
			});
		}else{
			html += "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>";
		}
		$("#process_record_list").html(html);
	},
};


