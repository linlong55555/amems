tracking_log = {
	id : "tracking_log",
	idList : [],
	/**
	 * 查询主单列表
	 */
	showHiddenContent: function(id,obj){
		
		if($("#bottom_hidden_content").css("display")=='block'){
	 		$("#bottom_hidden_content").slideUp(100);
	 	}
	 	$("#bottom_hidden_content").slideDown(100);
	 	$(obj).siblings("tr").removeClass("bg_tr");
	 	$(obj).addClass("bg_tr");
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#toolcheck_pagination .fenye"));
		}
	 	App.resizeHeight();
		
		var this_=this;
		var searchParam={};
		var paramsMap={};
		paramsMap.mainid=id;
		searchParam.paramsMap=paramsMap;
		
		AjaxUtil.ajax({
		   url:basePath+"/quality/toolcheck/queryAllLogList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   if(data.length >0){
	 				this_.appendContentHtml(data);
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"13\">暂无数据 No data.</td></tr>");
	 			}
	 		
		   }
	  }); 	
	},
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list){
		var this_=this;
		 var htmlContent = '';
		 $.each(list,function(index,row){
			 htmlContent += "<tr  >";
		
			    htmlContent += "<td class='fixed-column text-left' >"+StringUtil.escapeStr(row.bjxlh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gg)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jlfs)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jldj)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jyZq)+StringUtil.escapeStr((row.jyZqdw == 11) ? '天':'月' )+"</td>";
			    htmlContent += "<td class='text-center' >"+formatUndefine(row.jyScrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-center' >"+formatUndefine(row.jyXcrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.paramsMap.syts)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bz)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.id:'')+"</td>";
			    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	}
};

function hideBottom(){
	$("#hideIcon").remove();
	$(".displayContent").css("display","none");	
	$("#tracking_table tbody").find("tr").removeClass("bg_tr");
	App.resizeHeight();
	
}
