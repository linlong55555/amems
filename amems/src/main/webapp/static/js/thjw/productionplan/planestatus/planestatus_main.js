	var fjzt_list_tableDiv_actualheight="";
	
	 $(function(){
		 Navigation(menuCode);
		 var blankHeight = $(window).height() - $('.header').outerHeight() - 13-$("#NavigationBar").outerHeight()-$("#fjztSearchDiv").outerHeight()-$("#pagination").outerHeight();
		 var tableMaxHeight=(parseInt(blankHeight*0.5));
		 $("#fjzt_list_tableDiv").css("max-height",tableMaxHeight);
		 goPage(1,"auto","desc");
	 });
	
	// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 function goPage(pageNumber,sortType,sequence){
	 	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	 }	
	// 信息检索
	 function searchFjzt(){
	 	goPage(1,"auto","desc");
	 	
	 }
	// 带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 var obj ={};
			var searchParam = gatherSearchParam();
			pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			obj.pagination = pagination;
			obj.keyword=$.trim($('#fjzt_keyword_search').val());
			obj.jgdm = searchParam.jgdm;
			
			
		 AjaxUtil.ajax({
	 		url:basePath+"/static/js/thjw/productionplan/planestatus/data.json",
	 		/*type: "post",*/
	 		contentType:"application/json;charset=utf-8",
	 		dataType:"json",
	 		/*data:JSON.stringify(obj),*/
	 		success:function(data){
	 			if(data.total >0){
					appendContentHtml(data.rows);
					var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
					});
					// 标记关键字
					signByKeyword($("#fjzt_keyword_search").val(),[1,2,4],"#list tr td");
	 		    }else{
		 			$("#list").empty();
					$("#pagination").empty();
					$("#list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:'fjzt_list_table'});
	 		}
	     }); 	
	 }
	 function appendContentHtml(list){
			var htmlContent = '';
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					htmlContent += "<tr class='cursor-pointer' bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" id='"+row.id+"' onclick='getFjzt(this.id)' >";
				   
				} else {
					htmlContent += "<tr class='cursor-pointer' bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" id='"+row.id+"' onclick='getFjzt(this.id)'>";
				}
			
			    htmlContent += "<td class='text-center fixed-column'><a href=\"javascript:find('"+row.id+"')\" title='"+row.zch+"'>"+row.zch+"</a></td>";  
			    htmlContent += "<td class='text-left fixed-column' title='"+row.xlh+"'>"+row.xlh+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.shzt+"'>"+row.shzt+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.jx+"'>"+row.jx+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.fxbrq+"'>"+row.fxbrq+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.fxsj+"'>"+row.fxsj+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+row.qlxh+"'>"+row.qlxh+"</td>";
			    htmlContent += "<td class='text-left' title='"+row.fxjl+"'>"+row.fxjl+"</td>"; 
			    htmlContent += "</tr>"
		   $("#list").empty();
		   $("#list").html(htmlContent);
		  
				 
		})
		 $("#fjzt_list_tableDiv").attr("actualHeight",$("#fjzt_list_tableDiv").outerHeight());
		fjzt_list_tableDiv_actualheight=$("#fjzt_list_tableDiv").outerHeight();
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
	 
	//字段排序                  
	 function orderBy(obj) {
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
	  	var currentPage = $("#pagination li[class='active']").text();
	  	goPage(currentPage,obj,orderStyle.split("_")[1]);
	 }
	// 将搜索条件封装
	 function gatherSearchParam(){
	 	var searchParam = {};
	 	searchParam.jx = $("#jgdm").val();
	 	return searchParam;
	 }
	 var lastID="";
	 
	 function getFjzt(obj){
	  
	 if(lastID!=""){
		 $("#"+lastID).css("background",""); 
		 $("#"+lastID).removeClass("active"); 
	 }	 
	 $("#"+obj).css("background","#ececec"); 
	 $("#"+obj).addClass("active");
	 lastID=obj;
	 if( $("#fxzt_bottom").css("display")=="block"){
		  $("#fxzt_bottom").slideUp(500);
		 }
		 $("#fxzt_bottom").slideDown(500);
		 
		 DjTable.goPage(1,"auto","desc");
		 WcsTable.goPage(1,"auto","desc");
		 wcsTableHeight();
	 }
	 
	 function wcsTableHeight(){
		    var $wcsTab=$("#wcsTab").outerHeight();
		    var $window=$(window).height();
		    var $header=$('.header').outerHeight();
		    var $NavgationBar=$("#NavigationBar").outerHeight();
		    var $pagination=$("#pagination").outerHeight();
		    var $fjztSearch=$("#fjztSearchDiv").outerHeight();
		    var $fjzt_list_tableDiv=$("#fjzt_list_tableDiv").outerHeight();
		    var $wcsTabProgress=$("#wcsTabProgress").outerHeight();
		    var height=$window-$header-$NavgationBar-$pagination-$fjztSearch-$fjzt_list_tableDiv-$wcsTabProgress-$wcsTab-98;
			$("#wcs_list_tableDiv").css("height",height);
	 }
	 function leftBottomTableHeight(obj){
		    var $djTab=$("#djTab").outerHeight();
		    var $window=$(window).height();
		    var $header=$('.header').outerHeight();
		    var $NavgationBar=$("#NavigationBar").outerHeight();
		    var $pagination=$("#pagination").outerHeight();
		    var $fjztSearch=$("#fjztSearchDiv").outerHeight();
		    var $fjzt_list_tableDiv=$("#fjzt_list_tableDiv").outerHeight();
		    var height=$window-$header-$NavgationBar-$pagination-$fjztSearch-$fjzt_list_tableDiv-$djTab-80;
			$("#"+obj).css("height",height);
	 }
	