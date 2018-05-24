frozenHistoryModal = {
	param: {
        id:null       //库存id
	},
	show : function(param){
		$("#alertModalFrozen").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.emptyData();
		this.goPage(1,"auto","desc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_ = this;
		var id=this_.param['id'];
		var searchParam ={};
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.id=id;
		startWait();
		AjaxUtil.ajax({
		   url: basePath + "/material/stock/material/queryFrozenHistoryBykcid",
		   type: "post",
		   dataType:"json",
		   contentType : "application/json;charset=utf-8",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'frozenpagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
			   } else {
				  $("#frozenlist").empty();
				  $("#frozenpagination").empty();
				  $("#frozenlist").append("<tr><td colspan=\"4\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		var obj=list[0];
		var htmlContent = '';
		$.each(list,function(index,row){                                                
		   htmlContent += "<tr><td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName("freezeBusinessTypeEnum",StringUtil.escapeStr(row.ywlx))+"</td>";
		   if(row.ywlx==9){
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.ywbh)+"'><a href='javascript:void(0);' onclick=\"frozenHistoryModal.viewBf('"+row.paramsMap.bfid + "')\">"+StringUtil.escapeStr(row.ywbh)+"</a></td>";	
		   }else{
			   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywbh)+"</td>";  

		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.djsl)+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr((row.czsj||"").substr(0,10))+"</td>";    
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#frozenlist", $("#alertModalFrozen")).empty();
	   $("#frozenlist", $("#alertModalFrozen")).append(htmlContent);
	   $("#bjh_modal", $("#alertModalFrozen")).val(obj.stock.bjh||"");
	   $("#mc_modal", $("#alertModalFrozen")).val(((obj.stock.hCMainData?(obj.stock.hCMainData.ywms||""):"")+" "+(obj.stock.hCMainData?(obj.stock.hCMainData.zwms||""):"")));
	   $("#pch_modal", $("#alertModalFrozen")).val(obj.stock.pch||"");
	   $("#sequence_modal", $("#alertModalFrozen")).val(obj.stock.sn||"");
	   $("#num_modal", $("#alertModalFrozen")).val((obj.stock.kcsl-obj.stock.djsl)+"/"+obj.stock.kcsl+" "+(obj.stock.jldw||""));	   
	},
	viewBf:function(id){
		window.open(basePath + "/material/scrapped/apply/view?id=" + id);
		
	},
	emptyData:function(){
		$("#frozenlist").empty();
		$("#alertModalFrozen input").val("");		
	}
}