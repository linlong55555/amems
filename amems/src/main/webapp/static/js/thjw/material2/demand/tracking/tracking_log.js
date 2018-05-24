tracking_log = {
	id : "tracking_log",
	idList : [],
	/**
	 * 查询主单列表
	 */
	showHiddenContent: function(id,obj){
		
		var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
		
//		if($("#bottom_hidden_content").css("display")=='block'){
//	 		$("#bottom_hidden_content").slideUp(100);
//	 	}
//	 	$("#bottom_hidden_content").slideDown(100);
	 	$(obj).siblings("tr").removeClass("bg_tr");
	 	$(obj).addClass("bg_tr");
	 	
	
	 	
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#tracking_pagination .fenye"));
		}
	 
	 	TableUtil.showDisplayContent();
	 	
		var this_=this;
		var searchParam={};
		searchParam.mainid=id;
		searchParam.dprtcode=$("#dprtcode").val();
		AjaxUtil.ajax({
		   url:basePath+"/material/demand/list",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   if(data.length >0){
	 				this_.appendContentHtml(data);
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
	 			}
	 		
		   }
	  }); 	
		
		App.resizeHeight();
		if(!isBottomShown){
			TableUtil.makeTargetRowVisible($(obj), $("#tableId"));
		}
	 	
	},
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list){
		var this_=this;
		 var htmlContent = '';
		 $.each(list,function(index,row){
				htmlContent += "<tr >";
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.czsj)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.czrmc)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bmmc)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.czsm)+"</td>";
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	}
};


