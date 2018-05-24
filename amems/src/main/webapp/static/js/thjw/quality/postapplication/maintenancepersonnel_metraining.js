Post_Open_Modal_MeTraining = {
	id : "Post_Open_Modal",
	/**
	 * 初始化培训经历
	 */
	setData : function(data){
		if(data.length>0){
			var htmlContent = '';
			$.each(data,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-center' >"+formatUndefine(row.sjKsrq).substring(0,10)+"</td>"; 
				htmlContent += "<td class='text-center' >"+formatUndefine(row.sjJsrq).substring(0,10)+"</td>"; 
				htmlContent += "<td title='"+formatUndefine(row.fjjx)+"' class='text-left' >"+formatUndefine(row.fjjx)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.kcbm)+" "+StringUtil.escapeStr(row.kcmc)+"</td>"; 
			
				htmlContent += "</td>";
				htmlContent += "</tr>" ;
				$("#maintenance_metraining_list").empty();
				$("#maintenance_metraining_list").html(htmlContent);
			});
		}else{
			$("#maintenance_metraining_list").empty();
			$("#maintenance_metraining_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
	}

};

