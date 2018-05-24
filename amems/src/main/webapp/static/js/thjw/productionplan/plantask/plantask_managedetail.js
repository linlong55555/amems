$(function(){
	ZyglTable.goPage(1,"auto","desc");
	 });
		
ZyglTable={
		goPage:function(pageNumber,sortType,sequence){
			ZyglTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
		
			 AjaxUtil.ajax({
		 		url:basePath+"/static/js/thjw/productionplan/plantask/data.json",
		 		/*type: "post",*/
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		/*data:JSON.stringify(obj),*/
		 		success:function(data){
		 			if(data.total >0){
		 				ZyglTable.appendContentHtml(data.rows);
						
						// 标记关键字
						signByKeyword($("#djkeyword_search").val(),[0,1,2,3],"#zygl_list tr td");
		 				}else{
			 			$("#zygl_list").empty();
						$("#paginationDj").empty();
						$("#zygl_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
		 			}
		 			/*new Sticky({tableId:'dj_list_table'});*/
		 		}
			 })
		    
		},
		appendContentHtml:function(list){
			var htmlContent = '';
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" id='"+row.id+"'  >";
				   
				} else {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" id='"+row.id+"'>";
				}
			
			    htmlContent += "<td class='text-center' title='"+row.lx+"'>"+row.lx+"</td>";  
			    htmlContent += "<td class='text-left' title='"+row.jh+"'>"+row.jh+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.mc+"'>"+row.mc+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.cjjh+"'>"+row.cjjh+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.xqsl+"'>"+row.xqsl+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.zksl+"'>"+row.zksl+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.kysl+"'>"+row.kysl+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.thjkysl+"'><a href='javascript:;' onclick='showThjkysl()'>"+row.thjkysl+"</a></td>";
			    htmlContent += "</tr>"
			    $("#zygl_list").empty();
			    $("#zygl_list").html(htmlContent);
				 
		})
		},
		splitStr:function(str){
			var arr=str.split(",");
			var obj='';
			for(var i=0;i<arr.length;i++){
				obj+=arr[i]+"<br/>";
			}
			return obj
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
		  	var currentPage = $("#paginationDj li[class='active']").text();
		  	ZyglTable.goPage(currentPage,obj,orderStyle.split("_")[1]);
		 }
	}
ThjxxTable={
		goPage:function(pageNumber,sortType,sequence){
			ThjxxTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
		
			 AjaxUtil.ajax({
		 		url:basePath+"/static/js/thjw/productionplan/plantask/thjxxdata.json",
		 		/*type: "post",*/
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		/*data:JSON.stringify(obj),*/
		 		success:function(data){
		 			if(data.total >0){
		 				ThjxxTable.appendContentHtml(data.rows);
						
						// 标记关键字
						signByKeyword($("#djkeyword_search").val(),[0,1,2,3],"#zygl_list tr td");
		 				}else{
			 			$("#Thjxx_list").empty();
						$("#paginationDj").empty();
						$("#Thjxx_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
		 			}
		 		}
			 })
		    
		},
		appendContentHtml:function(list){
			var htmlContent = '';
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" id='"+row.id+"'  >";
				   
				} else {
					htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" id='"+row.id+"'>";
				}
			    htmlContent += "<td class='text-center' title='"+row.thjh+"'>"+row.thjh+"</td>";  
			    htmlContent += "<td class='text-left' title='"+row.thjmc+"'>"+row.thjmc+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.syx+"'>"+row.syx+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.knx+"'>"+row.knx+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.zksl+"'>"+row.zksl+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.kysl+"'>"+row.kysl+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.fj+"'><a href='javascript:;'>"+row.fj+"</a></td>";
			    htmlContent += "</tr>"
			    $("#Thjxx_list").empty();
			    $("#Thjxx_list").html(htmlContent);
				 
		})
		},
		splitStr:function(str){
			var arr=str.split(",");
			var obj='';
			for(var i=0;i<arr.length;i++){
				obj+=arr[i]+"<br/>";
			}
			return obj
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
    function showThjkysl(){
    	ThjxxTable.goPage(1,"auto","desc");
    	$("#alertModalThjkysl").modal("show");
    }