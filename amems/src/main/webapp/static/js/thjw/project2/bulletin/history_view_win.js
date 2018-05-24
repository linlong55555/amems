
bulletin_history_alert_Modal = {
	id : 'bulletin_history_alert_Modal',
	getHistoryBbList : function(id){
		var this_ = this;
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath + "/project2/bulletin/historylist",
			type:"post",
			async : false,
			data:{
				id:id
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
			
			htmlContent += '<td title="'+StringUtil.escapeStr(row.jstgh)+'" class="text-left" style="padding-left:8px;">';
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"') >"+StringUtil.escapeStr(row.jstgh)+"</a>";
			htmlContent += '</td>';
			
			htmlContent += '<td title="'+StringUtil.escapeStr("R"+row.bb)+'" class="text-center">';
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"') >"+StringUtil.escapeStr("R"+row.bb)+"</a>";
			htmlContent += '</td>';
			
			htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('bulletinStatusEnum',row.zt)+"</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.pfsj)+"</td>";
			
			
			htmlContent += "</tr>"; 
		    
		});
		$("#history_bb_list", $("#"+this_.id)).empty();
		$("#history_bb_list", $("#"+this_.id)).html(htmlContent);
	},
	view : function(id){
		window.open(basePath + "/project2/bulletin/view?id=" + id);
	}
}