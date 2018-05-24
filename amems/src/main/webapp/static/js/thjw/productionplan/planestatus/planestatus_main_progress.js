	
	 WcsTable={
		goPage:function(pageNumber,sortType,sequence){
			WcsTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			var zhc='';
			for(var i=0;i<$("#list").find("tr").length;i++){
				if($("#list").find("tr").eq(i).attr("class")=='cursor-pointer active'){
					var zhc=$("#list").find("tr").eq(i).find("td").eq(0).text();
				}
				
			}
			var url='';
			if(zhc=='B-7136'){
				url=basePath+"/static/js/thjw/productionplan/planestatus/wcsdata.json";
				$("#progressbar").css("width","50%");
				$("#wcspan1").text("6/12");
				$("#wcspan2").text("50%");
			}else{
				url=basePath+"/static/js/thjw/productionplan/planestatus/wcsdata1.json";
				$("#wcspan1").text("3/7");
				$("#progressbar").css("width","43%");
				$("#wcspan2").text("43%");
			}
			 var obj ={};
				var searchParam = gatherSearchParam();
				obj.keyword=$.trim($('#keyword_search').val());
				obj.jgdm = searchParam.jgdm;
			 AjaxUtil.ajax({
		 		url:url,
		 		/*type: "post",*/
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		/*data:JSON.stringify(obj),*/
		 		success:function(data){
		 			if(data.total >0){
		 				WcsTable.appendContentHtml(data.rows);
						
		 				}else{
			 			$("#wgslist").empty();
						$("#paginationWcs").empty();
						$("#wgslist").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
		 			}
		 			/*new Sticky({tableId:'wcs_list_table'});*/
		 		}
			 })
		    
		},
		appendContentHtml:function(list){
			var htmlContent = '';
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" id='"+row.id+"' >";
				   
				} else {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" id='"+row.id+"'>";
				}
			
			    htmlContent += "<td class='text-center fixed-column' title='"+row.wg+"'>"+row.wg+"</td>";  
			    htmlContent += "<td class='text-left' title='"+row.rw+"'>"+row.rw+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.ly+"'>"+row.ly+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.bz+"'>"+row.bz+"</td>";
			    htmlContent += "</tr>"
			    $("#wgslist").empty();
			    $("#wgslist").html(htmlContent);
				 
		})
		   
		},
		orderBy:function(obj){
		 	var orderStyle = $("#" + obj + "_order").attr("class");
		  	$("th[id$=_order]").each(function() { //重置class
		  		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		  	});
		  	$("#" + obj + "_" + "order").removeClass("sorting");
		  	if (orderStyle.indexOf("sorting_asc")>=0) {
		  		$("#" + obj + "_" + "order").addClass("sorting_desc");
		  	} else {
		  		$("#" + obj + "_" + "order").addClass("sorting_asc");
		  	}
		  	orderStyle = $("#" + obj + "_order").attr("class");
		  	var currentPage = $("#paginationWcs li[class='active']").text();
		  	WcsTable.goPage(currentPage,obj,orderStyle.split("_")[1]);
		 }
	}
	
	
	//获取预警颜色
	 function getWarningColor(bgcolor,syts,zt){
	 	if(!(zt == 0 || zt==5 || zt==6)){
	 		return bgcolor;
	 	}
	 	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
	 		bgcolor = warningColor.level2;//黄色
	 	}
	 	if(Number(syts) <= yjtsJb1){
	 		bgcolor = warningColor.level1;//红色
	 	}
	 	return bgcolor;
	 }
	 