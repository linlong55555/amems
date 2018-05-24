$(document).ready(function() {
	/*$('.page-sidebar').remove();
	$('.header').remove();
	$('.panel-primary').width('1200px');*/
	 
	
	hcStatisticsInstance = {
			param: {
				selected:null,
				callback:function(){}
			},
			init : function(param){
				if(param){
					$.extend(this.param, param);
				}
				this.goPage(1,"","desc");
			},
		 	goPage : function(pageNumber, sortType, sequence){
		 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
			},	
			AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
				startWait();
				var planTaskIds = this.param.planTaskIds;
				var listDom  = this.param.list;
				var defectStatisticalType = this.param.defectStatisticalType;
				
				var type = this.param.type;
				AjaxUtil.ajax({
					 url:basePath+"/productionplan/plantask/hcStatisticsData",
				   type: "post",
				   dataType:"json",
				   data:{
					   planTaskIds:planTaskIds
					   ,defectStatisticalType:defectStatisticalType
					   ,type:type
					   },
				   success:function(data){ 
					   finishWait();
					   if(data.total >0){
						   hcStatisticsInstance.appendContentHtml(data.rows);
						   $(listDom+" a[esWorkOrder='true']").click(function(){
								var id = $(this).attr('id');
								var gddlx = $(this).attr('gddlx');
								gddlx = formatUndefine(gddlx);
								var url = basePath+ "/project/workorder/Looked?id="+ id + "&gddlx=" + gddlx + "";
								window.open(url, '工单明细')
						  });
						  
					   } else {
						  $(listDom).empty();
						  $(listDom).append("<tr><td colspan=\"8\"  class='text-center'>暂无数据 No data.</td></tr>");
					   }
			     }
			   }); 
			},
			gatherSearchParam : function(){
				 var searchParam = {};
				 return searchParam;
			},
			appendContentHtml: function(list){
				
				var listDom  = this.param.list;
				var htmlContent = '';
				$.each(list,function(index,row){
					  if (index % 2 == 0) {
						   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  >";
					   } else {
						   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
					   }
					  
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',formatUndefine(row.hclx))+"</td>";  
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left' title=\""+formatUndefine(StringUtil.escapeStr(row.bjh))+"\" >"+formatUndefine(StringUtil.escapeStr(row.bjh))+"</td>";  
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle; \" class='text-left' title=\""+formatUndefine(StringUtil.escapeStr(row.zwms))+"\" >"+formatUndefine(StringUtil.escapeStr(row.zwms))+"</td>";  
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle; \" class='text-left' title=\""+formatUndefine(StringUtil.escapeStr(row.ywms))+"\"  >"+formatUndefine(StringUtil.escapeStr(row.ywms))+"</td>";
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle; \"  class='text-right'>"+formatUndefine(row.xqsl)+"</td>";
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle; \"  class='text-right'>"+formatUndefine(row.kcsl)+"</td>";
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle; \"  class='text-right'>"+formatUndefine(row.qjsl)+"</td>";
				   var htmlRwInfo = '';
				  
				   if(row.hcTaskInfos!=null){					 
					   var htmlHcOrders = ''
					   var hcTaskInfos = row.hcTaskInfos;
					   var len = hcTaskInfos.length;
					   htmlHcOrders = '';
					   $.each(hcTaskInfos,function(taskindex,hcTaskInfo){
						    
						   if(taskindex >0){
							   htmlRwInfo = htmlRwInfo + '<br>'+hcStatisticsInstance.getRwxx(hcTaskInfo);
						   }
						   else{
							   htmlRwInfo = htmlRwInfo + hcStatisticsInstance.getRwxx(hcTaskInfo);  
						   }
						   
					   });
					   htmlRwInfo = htmlRwInfo+htmlHcOrders;
				   }
				   htmlContent = htmlContent +"<td class='text-left'>"+htmlRwInfo+"</td>";
				   htmlContent = htmlContent + "</tr>";  
				    
			   });
			   $(listDom).empty();
			   $(listDom).html(htmlContent);
			}
			,search: function(){
				this.goPage(1,"","desc");
			} 
			,getRwxx:function(hcTaskInfo){
				return formatUndefine(hcTaskInfo!=null?hcTaskInfo.taskRelOrdersStr:'');
			}
		};
	
      var planTaskIds = [];
	  $("#planTaskIds option").each(function () {
          var planTaskId = $(this).val(); //获取单个value
          planTaskIds.push(planTaskId);
      });
	  hcStatisticsInstance.init({
		  'planTaskIds':planTaskIds
		  ,'list':'#hc_list'
		  ,defectStatisticalType:'HC_DEFECT'
		  ,type:$.trim($('input:radio:checked').val())  
      });
	  
	  $('#HC_DEFECT').on('click',function(){
		  hcStatisticsInstance.init({
			  'planTaskIds':planTaskIds
			  ,'list':'#hc_list'
			  ,defectStatisticalType:'HC_DEFECT'
			  ,type:$.trim($('input:radio:checked').val())  
	      });
	  });
	  
	  $('#TOOL_DEFECT').on('click',function(){
		  hcStatisticsInstance.init({
			  'planTaskIds':planTaskIds
			  ,'list':'#tool_list'
			  ,defectStatisticalType:'TOOL_DEFECT'
			  ,type:$.trim($('input:radio:checked').val())
	      });
	  });
	  
	 /* $('#refreshBtn').on('click',function(){
		  if($('#HC_DEFECT').hasClass('active')){
			  $('#HC_DEFECT').trigger("click");
		  }
		  else if($('#TOOL_DEFECT').hasClass('active')){
			  $('#TOOL_DEFECT').trigger("click");
		  }
	  });*/
	  
	  $('input[name=tjlx]').on('click',function(){
		  if($('#HC_DEFECT').hasClass('active')){
			  $('#HC_DEFECT').trigger("click");
		  }
		  else if($('#TOOL_DEFECT').hasClass('active')){
			  $('#TOOL_DEFECT').trigger("click");
		  }
	  });
	  
	  
});
 


