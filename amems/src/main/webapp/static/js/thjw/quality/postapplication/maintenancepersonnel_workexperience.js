Post_Open_Modal_Workexperience = {
	id : "Post_Open_Modal",
	/**
	 * 初始化工作经历
	 */
	setData : function(data){
		if(data.length>0){
			var htmlContent = '';
			$.each(data,function(index,row){
				var timestr = (formatUndefine(row.jsrq).substring(0,10)||"至今");
				htmlContent += "<tr>";
				
				htmlContent += "<td class='text-center' >"+formatUndefine(row.ksrq).substring(0,10)+"</td>"; 
				htmlContent += "<td class='text-center' >"+timestr+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gznr)+"</td>"; 
			
				htmlContent += "</td>";
				htmlContent += "</tr>" ;
				$("#maintenance_workexperience_list").empty();
				$("#maintenance_workexperience_list").html(htmlContent);
			});
		}else{
			$("#maintenance_workexperience_list").empty();
			$("#maintenance_workexperience_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		}
	}

};

