
var production_history_version = {
		
	id : "production_history_version",
	
	/**
	 * 查询主单列表
	 */
	loadData : function(id, $parent){
	 	
		var this_=this;
		var searchParam={};
		searchParam.id=id;
		AjaxUtil.ajax({
     	   url:basePath+"/project2/production/historylist",
     	   type: "post",
     	   contentType:"application/json;charset=utf-8",
     	   dataType:"json",
     	   data:JSON.stringify(searchParam),
     	   success:function(data){
     		   if(data.length >0){
      				this_.appendContentHtml(data, $parent);
      		   }else{
      			    $parent.find("#"+this_.id+"_list").empty();
      			    $parent.find("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
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
				htmlContent += "<td class='text-left'><a href='javascript:void(0);' onclick='production_history_version.viewProductionOrder(\""+row.id+"\")'>R"+StringUtil.escapeStr(row.bb)+"</a></td>";
			    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";
			    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.syx)+"</td>";
			    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zlms)+"</td>";
			    htmlContent += "<td class='text-center'><a href='javascript:void(0);' onclick='production_history_version.viewWorkCard(\""+row.gkid+"\")'>"+StringUtil.escapeStr(row.gkbh)+"</a></td>";
			    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fixChapter ? row.fixChapter.displayName : '')+"</td>";
			    htmlContent += "<td class='text-center'><a href='javascript:void(0);' onclick='production_history_version.viewProductionOrder(\""+row.id+"\")'>"+StringUtil.escapeStr(row.zlbh)+"</a></td>";
			    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.sxsj)+"</td>";
			    htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('productionOrderStatusEnum', row.zt)+"</td>";
			    htmlContent += "</tr>" ;
		 });
		 $parent.find("#"+this_.id+"_list").html(htmlContent);
		 TableUtil.addTitle($parent.selector+" #"+this_.id+"_list tr td"); //加载td title
	},
	
	
	/**
	 * 查看生产指令
	 */
	viewProductionOrder : function(id){
		window.open(basePath + "/project2/production/view?id=" + id);
	},

	/**
	 * 查看工卡
	 */
	viewWorkCard : function(id){
		window.open(basePath + "/project2/workcard/view/" + id);
	},
};


