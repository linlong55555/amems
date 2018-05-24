$(document).ready(function(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 Navigation(menuCode,"查看机型","View");
		$(".panel-heading").not(":first").click(function(){         //隐藏和显示
			$(this).next().slideToggle("fast");
		});
		 //初始化日志功能
		var zbid=$('#dprtcode_parma').val()+","+$('#fjjx').val();
		logModal.init({code:'D_004',id:zbid});
}); 

function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

//带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
		var obj ={};
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:5};
		obj.fjjx= $.trim($('#fjjx').val());
		obj.dprtcode=$.trim($('#dprtcode_parma').val());
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/planemodeldata/upPlaneBJH",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
			   } else {
				  $("#list").empty();
				  $("#list").append("<tr><td colspan=\"5\">暂无数据 No data.</td></tr>");
				  $("#pagination").empty();
			   }
	      }
	    }); 
		
	 }

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ywmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.zwmc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	 
}
