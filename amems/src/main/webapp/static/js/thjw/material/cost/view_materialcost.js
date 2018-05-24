$(document).ready(function(){
	
	Navigation(menuCode,"查看航材成本维护","AirMaterial View");
	//数据字典
	DicAndEnumUtil.registerDic('1','jldw',$("#dprtcode").val());
	initChapter();
	
	//生成多选下拉框动
	$('#jx').multiselect({
		buttonClass: 'btn btn-default',
        buttonWidth: 'auto',
        numberDisplayed:20,
	    includeSelectAllOption: true,
	    onChange:function(element,checked){
	    }
	});
	$("#jldw").val($("#jldw-hx").val());
	
	changeMaterialType();
	setReadonlyDisabled();
	
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	goPage(1,"id","desc");//开始的加载默认的内容 
	getFjjxData($("#id").val());
});

function changeMaterialType(){
	var type = $("#hclx").val();
	if(1 == type){
		$("#materialType").show();
	}else{
		$("#materialType").hide();
		$("#hclxEj").val("");
	}
}
function getFjjxData(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/material/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				var jx = '';
				var fjjx = [];
				if(data.xh == '00000'){
					jx = '通用Currency';
				}else{
					if(data.xh == null || data.xh == ''){
						if(data.materialToPlaneModelList != null && data.materialToPlaneModelList.length > 0){
							$.each(data.materialToPlaneModelList,function(index,row){
								fjjx.push(row.fjjx);
							});
							jx = fjjx.join(",");
						}
					}
					
				}
				$("#jxdiv").val(jx);
			}
		}
	});
}
//设置只读或失效
function setReadonlyDisabled(){
	$("input").attr("readonly","readonly");
	$("select").attr("disabled","true");
	$("#isMel").attr("disabled","true");
	$("#bz").attr("readonly","readonly");
}

//初始化ATA章节号信息
function initChapter(){
 	var zjh = $.trim($("#zjh").val());
 	
 	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixchapter/getFixChapterByZjh",
		type:"post",
		data:{zjh:zjh,dprtcode:$("#dprtcode").val()},
		dataType:"json",
		success:function(data){
			if(null != data){
				$("#zjhName").val(data.zjh+" "+data.zwms);
			}
		},
		error:function(data){
			AlertUtil.showErrorMessage(data.responseText);
		}
	});
}


function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/aerialmaterial/cost/queryCostListByPage",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
			   } else {
				  $("#list").empty();
				  $("#list").append("<tr><td colspan=\"16\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
	      },
	      error:function(){
			     finishWait();
	    	     alert("system error.");
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var mainid=$("#id").val();
		 searchParam.mainid = mainid;
		 return searchParam;
	 }
	 
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.juescb)+"</td>"; 
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.jiescb)+"</td>"; 
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.gscb)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>"; 
			   htmlContent += "<td>"+formatUndefine(row.whsj)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }