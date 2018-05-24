Post_Open_Modal_Educationorforeign = {
	id : "Post_Open_Modal",
	/**
	 * 初始化教育经历/外语水平
	 */
	setData : function(data){
		var this_=this;
		if(data.languages.length > 0 || data.educations.length > 0){
			$("#maintenance_educationorforeign_list").empty();
			var htmlContent = '';
				htmlContent+=this_.seteeducationsData(data.educations);	//初始化教育经历
				htmlContent+=this_.setlanguagesData(data.languages);	//初始化外语水平
			$("#maintenance_educationorforeign_list").html(htmlContent);
		}else{
			$("#maintenance_educationorforeign_list").empty();
			$("#maintenance_educationorforeign_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 初始化教育经历
	 */
	seteeducationsData : function(data){
			var htmlContent = '';
			if(data.length>0){
				$.each(data,function(index,row){
						htmlContent += "<tr>";
						htmlContent += "<td class='text-center' >"+formatUndefine(row.ksrq).substring(0,10)+"</td>"; 
						htmlContent += "<td class='text-center' >"+formatUndefine(row.jsrq).substring(0,10)+"</td>"; 
						htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.byxx)+" "+StringUtil.escapeStr(row.jyzy)+"</td>"; 
						htmlContent += "</tr>" ;
				});
			}
			return htmlContent;
	},
	/**
	 * 初始化外语水平
	 */
	setlanguagesData : function(data){
			var htmlContent = '';
			if(data.length>0){
				$.each(data,function(index,row){
					htmlContent += "<tr>";
					htmlContent += "<td class='text-center' ></td>"; 
					htmlContent += "<td class='text-center' ></td>"; 
					htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.yz)+" "+StringUtil.escapeStr(row.wysp)+"</td>"; 
					htmlContent += "</tr>" ;
				});
			}
			return htmlContent;
	}
};

