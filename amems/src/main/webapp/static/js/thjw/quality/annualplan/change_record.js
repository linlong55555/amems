

change_record = {
		
	/**
	 * 加载变更记录
	 */
	load : function(nf){
		if(nf == null || nf ==""){
			nf = $.trim($("#year_search").val());
		}
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/quality/annualplan/changerecord",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				nf : nf,
				dprtcode : $.trim($("#dprtcode").val()),
			}),
			success:function(data){
				this_.appendHtml(data);
		    }
		}); 
	},
	
	/**
	 * 拼接变更记录
	 */
	appendHtml : function(list){
		var html = "";
		if(list.length > 0){
			$(list).each(function(i, obj){
				html += "<tr>";
				html += "<td style='text-align:center;'>"+StringUtil.escapeStr("R"+obj.bbh)+"</td>";
				html += "<td style='text-align:left;'>"+StringUtil.escapeStr(obj.whr ? obj.whr.displayName : "")+"</td>";
				html += "<td style='text-align:center;'>"+StringUtil.escapeStr(obj.whsj)+"</td>";
				html += "<td style='text-align:left;'>"+StringUtil.escapeStr(obj.ndjhsm)+"</td>";
				html += "</tr>";
			});
		}else{
			html += "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
		}
		$("#change_record_list").html(html);
	},
};


