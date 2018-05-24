borrowHistoryModal = {
	param: {
        id:null       //库存id
	},
	show : function(param){
		$("#alertModalTool").modal("show");
		if(param){
			$.extend(this.param, param);
		}
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
		   url: basePath + "/material/stock/material/viewToolHistory",
		   type: "post",
		   dataType:"json",
		   contentType : "application/json;charset=utf-8",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'toolusepagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
			   } else {
				  $("#toolusehistory").empty();
				  $("#toolusepagination").empty();
				  $("#toolusehistory").append("<tr><td colspan=\"10\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){                                                
		   htmlContent += "<tr><td style='text-align:center;vertical-align:middle;'>"+row.bjh||''+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.bjxlh||''+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.jllx==10?'借用':'归还')+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.jy_zrrmc||''+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.jy_sj?row.jy_sj.substring(0,10):''+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.jy_sl||0+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.jy_bz||''+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.cqjybs==1?'是':'否')+"</td>";		
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.zdr?row.zdr.realname:'')+"</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+row.jg_dprt.dprtname||''+"</td>";  
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#toolusehistory", $("#alertModalTool")).empty();
	   $("#toolusehistory", $("#alertModalTool")).append(htmlContent);
	}
}