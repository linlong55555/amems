
var production_process_record = {
		
	id : "production_process_record",
	
	/**
	 * 查询主单列表
	 */
	loadData : function(id, $parent){
	 	
		var this_=this;
		var searchParam={};
		searchParam.mainid=id;
		searchParam.dprtcode=$("#dprtcode").val();
		AjaxUtil.ajax({
     	   url:basePath+"/quality/processrecord/list",
     	   type: "post",
     	   contentType:"application/json;charset=utf-8",
     	   dataType:"json",
     	   data:JSON.stringify(searchParam),
     	   success:function(data){
     		   if(data.length >0){
      				this_.appendContentHtml(data, $parent);
      		   }else{
      			    $parent.find("#"+this_.id+"_list").empty();
      			    $parent.find("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
      		   }
		   }
		}); 	
		
		App.resizeHeight();
	},
	
	
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list, $parent){
		var this_=this;
		 var htmlContent = '';
		 $.each(list,function(index,row){
				htmlContent += "<tr >";
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.czsj)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.czrmc)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bmmc)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.czsm)+"</td>";
			    htmlContent += "</tr>" ;
		 });
		 $parent.find("#"+this_.id+"_list").empty();
		 $parent.find("#"+this_.id+"_list").html(htmlContent);
		 TableUtil.addTitle($parent.selector+" #"+this_.id+"_list tr td"); //加载td title
	}
};


