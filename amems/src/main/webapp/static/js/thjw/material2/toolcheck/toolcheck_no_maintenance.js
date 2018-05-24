NoMaintenanceModal = {
	param: {
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#alertModalNoMaintenance").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#keyword_no_maintenance_search").val('');
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam={};
		searchParam.keyword=$.trim($("#keyword_no_maintenance_search").val());
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/material/stock/material/queryNoMaintenanceData",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),	   
		   success:function(data){
			   finishWait();
			   console.log(data);
			   if(data.total >0){
				   this_.data = data.rows;
				   NoMaintenanceModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'NoMaintenanceListPagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#keyword_no_maintenance_search").val()),[2,3,4,5,6,8,9],"#NoMaintenanceList tr td");
			   } else {
				  $("#NoMaintenanceList").empty();
				  $("#NoMaintenanceListPagination").empty();
				  $("#NoMaintenanceList").append("<tr><td colspan=\"11\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		console.log(list);
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='NoMaintenanceModal.check(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='NoMaintenanceModal.check(this)'>";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		   htmlContent += "<input type='radio' sn='"+(row.sn||"")+"' xh='"+(row.hCMainData?(row.hCMainData.xingh||""):"")+"'    gg='"+(row.hCMainData?(row.hCMainData.gg||""):"")+"' jldw='"+(row.hCMainData?(row.hCMainData.jldw||""):"")+"' kcsl='"+(row.kcsl||"")+"' rksj='"+(row.rksj||"")+"' kwh='"+(row.kwh||"")+"' ckmc='"+(row.ckmc||"")+"'  ckh='"+(row.ckh||"")+"'   bjh='"+(row.bjh||"")+"' pch='"+(row.pch||"")+"'  ywms='"+(row.hCMainData?row.hCMainData.ywms:"")+"' zwms='"+(row.hCMainData?row.hCMainData.zwms:"")+"'></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh||"")+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?(row.hCMainData.ywms||""):"")+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?(row.hCMainData.zwms||""):"")+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn||"")+"</td>"; 		   
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pch||"")+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.hclxEj:''))+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?(row.hCMainData.xingh||""):"")+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?(row.hCMainData.gg||""):"")+"</td>";  
		   htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.kcsl||"")+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?(row.hCMainData.jldw||""):"")+"</td>";  
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#NoMaintenanceList", $("#alertModalNoMaintenance")).empty();
	   $("#NoMaintenanceList", $("#alertModalNoMaintenance")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	check: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setData: function(){
		if($("input[type='radio']:checked").size()== 0){
			AlertUtil.showErrorMessage("请选择数据");
			return;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if($("#NoMaintenanceList input[type='radio']:checked").size()> 0){
				var $checkedObj=$("#NoMaintenanceList input[type='radio']:checked");
				var returnValue={};
				returnValue.sn=$checkedObj.attr("sn");//序列号
				returnValue.bjh=$checkedObj.attr("bjh");//部件号
				returnValue.pch=$checkedObj.attr("pch");//批次号
				returnValue.kwh=$checkedObj.attr("kwh");//库位号
				returnValue.ckmc=$checkedObj.attr("ckmc");//仓库名称
				returnValue.ckh=$checkedObj.attr("ckh");//仓库号
				returnValue.ywms=$checkedObj.attr("ywms");//中文描述
				returnValue.zwms=$checkedObj.attr("zwms");//英文描述
				returnValue.gg=$checkedObj.attr("gg");//规格
				returnValue.xh=$checkedObj.attr("xh");//型号
				returnValue.kcsl=$checkedObj.attr("kcsl");//库存数量
				returnValue.jldw=$checkedObj.attr("jldw");//计量单位
				returnValue.rksj=$checkedObj.attr("rksj");//入库时间
				this.param.callback(returnValue);
			}
		}
		   $("#alertModalNoMaintenance").modal("hide");
		   
	}	
}