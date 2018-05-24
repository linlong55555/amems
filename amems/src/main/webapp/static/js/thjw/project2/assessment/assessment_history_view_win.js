
assessment_history_alert_Modal = {
	id : 'assessment_history_alert_Modal',
	getHistoryBbList : function(id,dprtcode){
		var this_ = this;
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath + "/project2/assessment/queryHistoryListById",
			type:"post",
			async : false,
			data:{
				id:id,
				dprtcode:dprtcode
			},
			dataType:"json",
			success:function(data){
				finishWait();
				if(data != null && data.length > 0){
					this_.appendContentHtml(data);
				}
			}
		});
		return $("#"+this_.id).html();
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			htmlContent = htmlContent + "<tr>";
			
			htmlContent += '<td title="'+StringUtil.escapeStr(row.pgdh)+'" class="text-left" style="padding-left:8px;">';
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"') >"+StringUtil.escapeStr(row.pgdh)+"</a>";
			htmlContent += '</td>';
			
			htmlContent += '<td title="'+StringUtil.escapeStr(row.bb)+'" class="text-center">';
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"') >R"+StringUtil.escapeStr(row.bb)+"</a>";
			htmlContent += '</td>';
			
			htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('technicalStatusEnum',row.zt)+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.pgsj)+"</td>";
			
			htmlContent += "</tr>"; 
		    
		});
		$("#history_bb_list", $("#"+this_.id)).empty();
		$("#history_bb_list", $("#"+this_.id)).html(htmlContent);
	},
	view : function(id){
		window.open(basePath+"/project2/assessment/view?id="+id);
	}
}