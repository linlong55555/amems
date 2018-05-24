
updateLogAlert={
		init:function(){
			var this_=this;
			this_.loadFields();
			
		},
		/**
		 * 加载数据
		 */
		loadFields:function(){
			var this_=this;
			var searchParam ={};
			startWait();
			AjaxUtil.ajax({
			   url:basePath+"/sys/updatelog/queryByAll",
			   type: "post",
			   async : false,
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
				   finishWait();
				   if(data.length >0){
					   $("#updateLog").html(this_.logContent(data));
				   } else {
					   $("#updateLog").html("");
				   }
		     } 
		   }); 
		},
		logContent:function(data){
			var this_=this;
			var htmlContent = "";
			htmlContent += "<div class='log-container'>";
			htmlContent += "<span class='log-header'>";
			htmlContent += "<span class='log-header-icon'><i class='fa fa-clock-o' style=''></i></span>";
			/*htmlContent += "<i class='fa fa-clock-o' style=''></i>";*/
			htmlContent += "<label class='log-header-info'>AMEMS 更新日志</label>";
			htmlContent += "</span>";
			htmlContent += this_.yearContent(data);
			htmlContent += "</div>";
			return htmlContent;
		},
		yearContent:function(data){
			var this_=this;
			var htmlContent = "";
			var date = new Date();
			var year = date.getFullYear();
			var yearCount = 0;
			var yearRowArray = [];
			var yearDataArray = [];
			$.each(data,function(index,row){
				while(year != row.pubdate.substr(0,4)){
					var yearDataJson = {}; 
					yearDataJson.year = year;
					yearDataJson.count = yearCount;
					yearDataJson.datas = yearRowArray;
					yearDataArray.push(yearDataJson);
					year -= 1;
					yearCount = 0;
					yearRowArray = [];
				}
				year = row.pubdate.substr(0,4);
				yearCount += 1;
				yearRowArray.push(row);		
				if(index == data.length -1){
					var yearDataJson = {}; 
					yearDataJson.year = year;
					yearDataJson.count = yearCount;
					yearDataJson.datas = yearRowArray;
					yearDataArray.push(yearDataJson);
				}
			});	
			
			$.each(yearDataArray,function(index,row){		
				htmlContent += "<div class='log-items "+(index==0?'active':'')+"'>"; 
				htmlContent += "<div class='log-year' >";
				htmlContent += "<div class='log-year-info'>";
				htmlContent += "<span class='log-year-date' style='' onclick=updateLogAlert.showYearContent(this,'log-year-info')>"+row.year+"</span>";
				/*htmlContent += "<span class='showyearicon' style=''></span>"*/
				htmlContent += "<i class='fa "+(index==0?'fa-caret-down':'fa-caret-right')+" cursor-pointer' id='downUpIcon' onclick=updateLogAlert.showYearContent(this)></i>";
				htmlContent += "</div>";
				  
			   htmlContent += "<div class='log-year-box'>";
			   htmlContent += "<span onclick=updateLogAlert.showYearContent(this,'log-year-box')>更新<strong>"+row.count+"</strong>次</span>";
			   htmlContent += "</div>";
			   htmlContent += "<div class='clearfix'></div>";
			   htmlContent += "</div>";
               htmlContent += this_.dayContent(row.datas);
			   htmlContent += "</div>";
				
			});
			return htmlContent;   
			
		},
		dayContent:function(datas){
			if(!datas){
				return;
			}
			var this_=this;
			var htmlContent = "";
			$.each(datas, function(index, row){
				 htmlContent += "<div class='log-body'>";
				   htmlContent += "<div class='log-body-item'>";
					   htmlContent += "<div class='log-body-info'>";
						   htmlContent += "<span class='log-body-date'>"+row.pubdate.substr(5,5)+"</span>";
						   htmlContent += "<span class='log-body-icon'>";
						   	/*htmlContent += "<i class='fa fa-dot-circle-o cursor-pointer'></i>";*/
						   htmlContent += "</span>";
					   htmlContent += "</div>";
					   htmlContent += "<div class='log-body-box'>";
					   	   htmlContent += "<h2>【"+row.version+"】 "+formatUndefine(row.title)+"</h2>";
					   	   htmlContent += "<p>"+row.describe+"</p>";
						  /* htmlContent += "<div class='panel panel-primary'>";
							   htmlContent += "<div class='panel-heading' style='padding-bottom:5px;'>";
							   	htmlContent += "<div class='font-size-12'>【"+row.version+"】 "+formatUndefine(row.title)+"</div>";
							   htmlContent += "</div>";
							   htmlContent += "<div class='panel-body'>";
							   htmlContent += row.describe;
							   htmlContent += "</div>";
						   htmlContent += "</div>";*/
					   htmlContent += "</div>";   
				   htmlContent += "</div>";
				   htmlContent += "</div>";
			})
			return htmlContent;
		},
		showYearContent:function(obj,className){
			var iconObj="";
			var parentDiv="";
			if(className=="log-year-box"){
				iconObj=$(obj).parent().siblings(".log-year-info").find("i#downUpIcon");
				parentDiv=$(obj).parents(".log-items");
			}else if(className=="log-year-info"){
				iconObj=$(obj).find("i#downUpIcon");
				parentDiv=$(obj).parents(".log-items");
			}else{
				iconObj=$(obj);
				parentDiv=$(obj).parents(".log-items");
			}
			
			if(parentDiv.hasClass("active")){
				parentDiv.toggleClass("active");
				iconObj.removeClass("fa-caret-down")
				iconObj.addClass("fa-caret-right")
				
			}else{
				parentDiv.toggleClass("active");
				iconObj.removeClass("fa-caret-right")
				iconObj.addClass("fa-caret-down")
				
			}
		}
}
