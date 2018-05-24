 
   function showDj(){
    	 $("#searchTabBtn").html("");
    	 var str='<div class=" pull-left padding-left-0 padding-right-0" style="width: 150px;">'
    		 str+='<input type="text" placeholder="件号/控制/剩余/备注" id="djkeyword_search" class="form-control" style="border-top-right-radius:0px;border-bottom-right-radius:0px;">'
    		 str+='</div>'
    		 str +='<div class="pull-right padding-right-0">'
    		 str+='<button type="button" class=" btn btn-primary" style="padding-top:4px;padding-bottom:4px;padding-left:8px;padding-right:8px;border-top-left-radius:0px;border-bottom-left-radius:0px;" onclick="searchDj();">'
    		 str+='<div class="font-size-12">搜索</div>'
    		 str+='<div class="font-size-9">Search</div>'
    		 str+='</button>'
    		 str+='</div>'
    	 $("#searchTabBtn").html(str);	
    	 DjTable.goPage(1,"auto","desc");
    	 
     }
 
	DjTable={
		goPage:function(pageNumber,sortType,sequence){
			DjTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
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
				url=basePath+"/static/js/thjw/productionplan/planestatus/djdata.json";
				$("#djspan").text("5");
				$("#ssspan").text("3")
				$("#qtgdspan").text("4")
			}else{
				url=basePath+"/static/js/thjw/productionplan/planestatus/djdata1.json";
				$("#djspan").text("3");
				$("#ssspan").text("2")
				$("#qtgdspan").text("2")
			}
			 var obj ={};
				var searchParam = gatherSearchParam();
				obj.keyword=$.trim($('#djkeyword_search').val());
				obj.jgdm = searchParam.jgdm;
				
				
			 AjaxUtil.ajax({
		 		url:url,
		 		/*type: "post",*/
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		/*data:JSON.stringify(obj),*/
		 		success:function(data){
		 			if(data.total >0){
		 				DjTable.appendContentHtml(data.rows);
						
						// 标记关键字
						signByKeyword($("#djkeyword_search").val(),[0,1,2,3],"#djlist tr td");
		 				}else{
			 			$("#djlist").empty();
						$("#paginationDj").empty();
						$("#djlist").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
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
			
			    htmlContent += "<td class='text-center' title='"+row.djxm+"'>"+row.djxm+"</td>";  
			    htmlContent += "<td class='text-left' title='"+row.jh+"'>"+row.jh+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.sy+"'>"+DjTable.splitStr(row.sy)+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.bz+"'>"+row.bz+"</td>";
			    htmlContent += "</tr>"
			    $("#djlist").empty();
			    $("#djlist").html(htmlContent);
				 
		})
		leftBottomTableHeight("dj_list_tableDiv")
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
		  	DjTable.goPage(currentPage,obj,orderStyle.split("_")[1]);
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
     function showSk(){
    	 $("#searchTabBtn").html("");
    	 var str='<div class=" pull-left padding-left-0 padding-right-0" style="width: 150px;">'
    		 str+='<input type="text" placeholder="件号/控制/剩余/备注" id="skkeyword_search" class="form-control" style="border-top-right-radius:0px;border-bottom-right-radius:0px;">'
    		 str+='</div>'
    		 str +='<div class="pull-right padding-right-0">'
    		 str+='<button type="button" class=" btn btn-primary" style="padding-top:4px;padding-bottom:4px;padding-left:8px;padding-right:8px;border-top-left-radius:0px;border-bottom-left-radius:0px;" onclick="searchSk();">'
    		 str+='<div class="font-size-12">搜索</div>'
    		 str+='<div class="font-size-9">Search</div>'
    		 str+='</button>'
    		 str+='</div>'
    	 $("#searchTabBtn").html(str);	
		 SKTable.goPage(1,"auto","desc");
    	 
     }
 	SKTable={
 			goPage:function(pageNumber,sortType,sequence){
 				SKTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
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
 					url=basePath+"/static/js/thjw/productionplan/planestatus/skdata.json"
 				}else{
 					url=basePath+"/static/js/thjw/productionplan/planestatus/skdata1.json"
 				}
 				    var obj ={};
 					var searchParam = gatherSearchParam();
 					obj.keyword=$.trim($('#skkeyword_search').val());
 					obj.jgdm = searchParam.jgdm;
 				 AjaxUtil.ajax({
 			 		url:url,
 			 		/*type: "post",*/
 			 		contentType:"application/json;charset=utf-8",
 			 		dataType:"json",
 			 		/*data:JSON.stringify(obj),*/
 			 		success:function(data){
 			 			if(data.total >0){
 			 				SKTable.appendContentHtml(data.rows);
 							// 标记关键字
 							signByKeyword($("#skkeyword_search").val(),[1,2,3,4],"#sklist tr td");
 			 				}else{
 				 			$("#sklist").empty();
 							$("#paginationSk").empty();
 							$("#sklist").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
 			 			}
 			 			/*new Sticky({tableId:'sk_list_table'});*/
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
 				
 				    htmlContent += "<td class='text-center' title='"+row.jh+"'>"+row.jh+"</td>";  
 				    htmlContent += "<td class='text-left' title='"+row.kz+"'>"+row.kz+"</td>";
 				    htmlContent += "<td class='text-left' title='"+row.sy+"'>"+SKTable.splitStr(row.sy)+"</td>";
 				    htmlContent += "<td class='text-left' title='"+row.bz+"'>"+row.bz+"</td>";
 				    htmlContent += "</tr>"
 				    $("#sklist").empty();
 				    $("#sklist").html(htmlContent);
 					 
 			})
 			leftBottomTableHeight("sk_list_tableDiv");
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
 			  	var currentPage = $("#paginationSk li[class='active']").text();
 			  	SKTable.goPage(currentPage,obj,orderStyle.split("_")[1]);
 			 }
 		}
 	 function showQygd(){
 		 $("#searchTabBtn").html("");
    	 var str='<div class=" pull-left padding-left-0 padding-right-0" style="width: 150px;">'
    		 str+='<input type="text" placeholder="件号/控制/剩余/备注" id="qtgdkeyword_search" class="form-control" style="border-top-right-radius:0px;border-bottom-right-radius:0px;">'
    		 str+='</div>'
    		 str +='<div class="pull-right padding-right-0">'
    		 str+='<button type="button" class=" btn btn-primary" style="padding-top:4px;padding-bottom:4px;padding-left:8px;padding-right:8px;border-top-left-radius:0px;border-bottom-left-radius:0px;" onclick="searchQtgd();">'
    		 str+='<div class="font-size-12">搜索</div>'
    		 str+='<div class="font-size-9">Search</div>'
    		 str+='</button>'
    		 str+='</div>'
    	 $("#searchTabBtn").html(str);
 		QtgdTable.goPage(1,"auto","desc");
    }
 	QtgdTable={
			goPage:function(pageNumber,sortType,sequence){
				QtgdTable.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
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
					url=basePath+"/static/js/thjw/productionplan/planestatus/qtgddata.json";
				}else{
					url=basePath+"/static/js/thjw/productionplan/planestatus/qtgddata1.json";
				}
				 var obj ={};
					var searchParam = gatherSearchParam();
					obj.keyword=$.trim($('#qtgdkeyword_search').val());
					obj.jgdm = searchParam.jgdm;
				 AjaxUtil.ajax({
			 		url:url,
			 		/*type: "post",*/
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		/*data:JSON.stringify(obj),*/
			 		success:function(data){
			 			if(data.total >0){
			 				QtgdTable.appendContentHtml(data.rows);
							// 标记关键字
							signByKeyword($("#qtgdkeyword_search").val(),[1,2,3,4],"#qtgdlist tr td");
			 				}else{
				 			$("#qtgdlist").empty();
							$("#paginationQtgd").empty();
							$("#qtgdlist").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			 			}
			 			/*new Sticky({tableId:'qtgd_list_table'});*/
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
				
				    htmlContent += "<td class='text-center' title='"+row.dh+"'><a href=\"javascript:find('"+row.id+"')\">"+row.dh+"</a></td>";  
				    htmlContent += "<td class='text-left' title='"+row.lx+"'>"+row.lx+"</td>";
				    htmlContent += "<td class='text-left' title='"+row.sy+"'>"+QtgdTable.splitStr(row.sy)+"</td>";
				    htmlContent += "<td class='text-left' title='"+row.xfyy+"'>"+row.xfyy+"</td>";
				    htmlContent += "</tr>"
				    $("#qtgdlist").empty();
				    $("#qtgdlist").html(htmlContent);
					 
			})
			leftBottomTableHeight("qtgd_list_tableDiv");
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
			  	var currentPage = $("#paginationQtgd li[class='active']").text();
			  	QtgdTable.goPage(currentPage,obj,orderStyle.split("_")[1]);
			 }
		}
 	    function searchDj(){
 		   DjTable.goPage(1,"auto","desc");
 	    }
 	    function searchSk(){
 	      SKTable.goPage(1,"auto","desc");
  	    }
 	   function searchQtgd(){
 		  QtgdTable.goPage(1,"auto","desc");
 	   }
 	    