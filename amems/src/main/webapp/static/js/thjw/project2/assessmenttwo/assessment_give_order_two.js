var zt=[];
	zt[1]='未反馈';
	zt[2]='已反馈';
var OutputAllIds=[];    //下达指令集合
/**
 * 下达指令
 */
bottom_hidden_content = {
	id:'bottom_hidden_content',
	/**
	 * 查询下达指令列表
	 */
	anOrder:function(id){
		var obj={};
		obj.lyid=id;
		AjaxUtil.ajax({
		   url:basePath+"/project2/assessment/queryAllOrderList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
	 			if(data.length>0){
	 				bottom_hidden_content.appendContentOrderHtml(data);
	 			}else{
		 			$("#give_order_list").empty();
					$("#give_order_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
	 			}
		   }
		}); 	
	},
	/**
	 * 表格拼接
	 */
	appendContentOrderHtml:function(list){
		 var htmlContent = '';
		 OutputAllIds=[];
		 $.each(list,function(index,row){
			 OutputAllIds.push(index);
			 htmlContent += "<tr>";
			 htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.lybh)+" R"+StringUtil.escapeStr(row.bb)+"' ><a href=\"javascript:findAssessmentView('"+row.lyid+"')\">"+StringUtil.escapeStr(row.lybh)+" R"+StringUtil.escapeStr(row.bb)+"</a></td>";
		     htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.dbgzlx))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.dbgzlx))+"</td>"; 
		     if(row.zpr!=null){
		    	 htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zpr.id)+"'>"+StringUtil.escapeStr(row.zpr.id)+"</td>"; 
		     }else{
		    	 htmlContent += "<td></td>"; 
		     }
	    	 htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.blqx).substring(0,10)+"'>"+StringUtil.escapeStr(row.blqx).substring(0,10)+"</td>"; 
	    	 if(row.zt==1){
	    		 htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(zt[row.zt])+"'>"+StringUtil.escapeStr(zt[row.zt])+"</td>"; 
	    	 }else{
	    		 htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(zt[row.zt])+"'><a " +
		    		 "  lybh='"+StringUtil.escapeStr(row.lybh)+"'"+
	 				"  fjjx='"+StringUtil.escapeStr(row.fjjx)+"'"+
	 				"  dbgzlx='"+StringUtil.escapeStr(row.dbgzlx)+"'"+
	 				"  blrid='"+StringUtil.escapeStr(row.zpr.id)+"'"+
	 				"  fksj='"+StringUtil.escapeStr(row.fksj)+"'"+
	 				"  fkyj='"+StringUtil.escapeStr(row.fkyj)+"'"+
	    		 		"onclick=\"OpenTodoView('"+row.id+"',this)\">"+StringUtil.escapeStr(zt[row.zt])+"</a></td>"; 
	    	 }
		     htmlContent += "<td class='text-left' >"+outPut(row.instructionsourceList,index,row.dbgzlx)+"</td>"; 
		     htmlContent += "<td class='text-left' >"+outPutSm(row.instructionsourceList,index)+"</td>"; 
		     htmlContent += "</tr>" ;
		     $("#give_order_list").empty();
		     $("#give_order_list").html(htmlContent);
		 });	
	}
};

/**
 *查询技术评估单
 */
function findAssessmentView(id){
	window.open(basePath+"/project2/assessment/view?id="+id);
}
/**
 *弹出待办事宜窗口
 */
function OpenTodoView(id,obj){
	
	modalEmpty();
	$("#lybh").val($(obj).attr("lybh"));
	$("#fjjx").val($(obj).attr("fjjx"));
	$("#dbgzlx").val(DicAndEnumUtil.getEnumName('sendOrderEnum',$(obj).attr("dbgzlx")));
	$("#blrid").val($(obj).attr("blrid"));
	$("#fksj").val($(obj).attr("fksj"));
	$("#fkyj").val($(obj).attr("fkyj"));
	
	$("#FeedBackModal").modal("show");
}

//清空弹出框
function modalEmpty(){
	$("#lybh").val("");
	$("#fjjx").val("");
	$("#dbgzlx").val("");
	$("#blrid").val("");
	$("#fksj").val("");
	$("#fkyj").val("");
	AlertUtil.hideModalFooterMessage();
}

/**
 *根据指令类型跳转不同的页面 
 */
function findOutputView(id,gzlx){
	if(gzlx==1){//技术通告
		window.open(basePath+"/project2/bulletin/view?id="+id);
	}else if(gzlx==2){//技术指令
		window.open(basePath+"/project2/instruction/view?id="+id);
	}else if(gzlx==3){//维修方案
		window.open(basePath+"/project2/maintenancescheme/view?id="+id);
	}else if(gzlx==4){//非例行工单
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	}else if(gzlx==6){//工程指令EO
		window.open(basePath+"/project2/eo/view?id="+id);
	}else if(gzlx==7){//MEL
		window.open(basePath+"/project2/mel/view?id="+id);
	}else if(gzlx==8){//工卡
		window.open(basePath+"/project2/workcard/view/"+id);
	}
}


/**
 *加载下达指令 
 */
function outPut(data,index,gzlx){
	  var htmlContent='';
	  if(data!=null){
		   var falg=true;
 		   for (var i = 0; i < data.length; i++) {
				   var zt="";
				   if(gzlx==1){//技术通告
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('bulletinStatusEnum',data[i].ywdjzt));
				   }else if(gzlx==2){//技术指令
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum',data[i].ywdjzt));
				   }else if(gzlx==3){//维修方案
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceSchemeStatusEnum',data[i].ywdjzt));
				   }else if(gzlx==4){//下达nrc
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',data[i].ywdjzt));
					   htmlContent +="<a title='"+StringUtil.escapeStr(data[i].zlbh)+"' href=\"javascript:findOutputView('"+data[i].zlid+"',"+gzlx+")\">"+StringUtil.escapeStr(data[i].zlbh)+"</a>【"+zt+"】";
				   }else if(gzlx==6){//工程指令eo
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('engineeringOrderStatusEnum',data[i].ywdjzt));
				   }else if(gzlx==7){//mel
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('melStatusEnum',data[i].ywdjzt));
					   htmlContent +="<a title='"+StringUtil.escapeStr(data[i].zlbh)+"' href=\"javascript:findOutputView('"+data[i].zlid+"',"+gzlx+")\">"+StringUtil.escapeStr(data[i].zlbh)+"</a>【"+zt+"】 ";
				   }else if(gzlx==8){//工卡
					   zt=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workCardStatusEnum',data[i].ywdjzt));
				   }
				   if(gzlx!=4 && gzlx!=7){
					   htmlContent +="<a title='"+StringUtil.escapeStr(data[i].zlbh)+" R"+StringUtil.escapeStr(data[i].zlbb)+"【"+zt+"】' href=\"javascript:findOutputView('"+data[i].zlid+"',"+gzlx+")\">"+StringUtil.escapeStr(data[i].zlbh)+" R"+StringUtil.escapeStr(data[i].zlbb)+"</a>【"+zt+"】 ";
				   }
 				  
 				   if(falg==true){
 					   if(data[i+1]!=null){
 						   htmlContent += "<i class='icon-caret-down cursor-pointer' id='"+ index+ "icon1' onclick=vieworhideOutput('"
 						   + index + "')></i><div name='"+ index+ "content1' style='display:none;white-space:nowrap;'>";
 					   }
 					   falg=false;
 				   }else{
 					   htmlContent +="<br>";
 				   }
 				
 				   if (i != 0 && i == data.length - 1) {
 						htmlContent += "</div>";
 				   }
		   }
	  }
  return htmlContent;
}

/**
 * 点击表头展开表格
 */
function vieworhideOutputAll(){
	var obj = $("#bottom_hidden_content th[name='output_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<OutputAllIds.length;i++){
		 if(flag){				
			 $("[name="+OutputAllIds[i]+"content1]").fadeIn(500);
			 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-down");
			 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-up");
		 }else{
			 $("[name="+OutputAllIds[i]+"content1]").hide();
			 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-up");
			 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-down");
		 }
	 }
}

function vieworhideOutput(i){
	 var flag = $("[name="+OutputAllIds[i]+"content1]").is(":hidden");
	 if(flag){
		 $("[name="+OutputAllIds[i]+"content1]").fadeIn(500);
		 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-down");
		 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-up");
	 }else{
		 $("[name="+OutputAllIds[i]+"content1]").hide();
		 $("#"+OutputAllIds[i]+"icon1").removeClass("icon-caret-up");
		 $("#"+OutputAllIds[i]+"icon1").addClass("icon-caret-down");
	 }
}


/**
 *加载主题
 */
function outPutSm(data,index){
	  var htmlContent='';
	  if(data!=null){
		   var falg=true;
 		   for (var i = 0; i < data.length; i++) {
						if(StringUtil.escapeStr(data[i].ywzt)!=''){
							 htmlContent +="<i title='"+StringUtil.escapeStr(data[i].ywzt)+"'>"+StringUtil.escapeStr(data[i].ywzt)+"</i>";
						}else{
							 htmlContent +="";
						}
				   if(falg==true){
					   if(data[i+1]!=null){
						   htmlContent += "<i  name='"+ index+ "icon1' onclick=vieworhideOutput1('"
						   + index + "')></i><div name='"+ index+ "content1' style='display:none;white-space:nowrap;'>";
					   }
					   falg=false;
				   }else{
					   htmlContent +="<br>";
				   }
				
				   if (i != 0 && i == data.length - 1) {
						htmlContent += "</div>";
				   }
		
				  
		   }

	  }

  return htmlContent;
}
