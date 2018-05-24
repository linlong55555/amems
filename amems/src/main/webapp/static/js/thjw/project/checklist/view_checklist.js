$(function(){
	
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	Navigation(menuCode,"查看定检任务单","View CheckList");
	
	var diczt=DicAndEnumUtil.getEnumName('workOrderStateEnum',$("#zt").val());  //翻译工单状态
	$("#zt").val(diczt);
	
	var jkxmbhF=jkxm[$("#jkxmbhF").text()];  //翻译监控项目
	$("#jkxmbhF").text(jkxmbhF);
	var jkxmbhS=jkxm[$("#jkxmbhS").text()];  //翻译监控项目
	$("#jkxmbhS").text(jkxmbhS);
	var jkxmbhT=jkxm[$("#jkxmbhT").text()];  //翻译监控项目
	$("#jkxmbhT").text(jkxmbhT);
	
	
	if($("#jkxmbhF").text()==null ||$("#jkxmbhF").text()==""  ||$("#jkzF").val()==null||$("#jkzF").val()=="" ){
		$("#calendar_dlg1").css("display", "none");
	}
	if($("#jkxmbhS").text()==null ||$("#jkxmbhS").text()=="" ||$("#jkzS").val()==null||$("#jkzS").val()=="" ){
		$("#calendar_dlg2").css("display", "none");
	}
	if($("#jkxmbhT").text()==null ||$("#jkxmbhT").text()=="" ||$("#jkzT").val()==null||$("#jkzT").val()=="" ){
		$("#calendar_dlg3").css("display", "none");
	}
	sumTotal();
	
	$("#close_open1").click(function(){
		 $("#display_div").slideToggle("fast");
	});
	$("#close_open2").click(function(){
		 $("#display_div2").slideToggle("fast");
	});
	$("#close_open3").click(function(){
		 $("#display_div3").slideToggle("fast");
	});

	
	var id=$("#id").val();
	 AjaxUtil.ajax({
	   url:basePath+"/project/checklist/queryCheckingContentList?id=" + id,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   success:function(data){
	    finishWait();
		   if(data!=null){
			   appendContentHtml(data.rows);
		   } else {
			  $("#CheckingContentList").empty();
			  $("#CheckingContentList").append("<tr class='text-center'><td colspan=\"18\">暂无数据 No data.</td></tr>");
		   }
      },
    }); 
});

function sumTotal(){
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#bzgs").text(jhgsRs*jhgsXss);
	}
}

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-center'>"+StringUtil.escapeStr(row.nbxh)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+StringUtil.escapeStr(row.zjh)+' '+StringUtil.escapeStr(row.zwms)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.xmly)+"'>"+StringUtil.escapeStr(row.xmly)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.gzlx)+"'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.wz)+"'>"+StringUtil.escapeStr(row.wz)+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.scmsZw)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.scmsZw)+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.scmsYw)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.scmsYw)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.jclx)+"'>"+StringUtil.escapeStr(row.jclx)+"</td>";
		   if(row.fjzch=='00000'){
			   htmlContent = htmlContent + "<td style='vertical-align:middle'>通用Currency</td>";
		   }else{
			   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
		   }
		   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-right'>"+formatUndefine(row.gzzw)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.cksc)+"'>"+StringUtil.escapeStr(row.cksc)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' title='"+StringUtil.escapeStr(row.ckgk)+"'>"+StringUtil.escapeStr(row.ckgk)+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>";
		   if(row.gdbh!=null&&row.gdbh!=""){
			   var strs= new Array(); //定义一数组
			   strs= row.gdbh.split(","); //字符分割
			   for (var i=0;i<strs.length ;i++ ){
				   var gdbh=strs[i].split("@")[0];
				   var gdid=strs[i].split("@")[1];
				   if(i==1){
					   htmlContent = htmlContent +"　<i class='icon-caret-down' id='"+row.id+"icon' onclick=vieworhideWorkContent('"+row.id+"')></i><div id='"+row.id+"' style='display:none'>"; 
				   }
				   htmlContent = htmlContent +"<a href=\"javascript:selectWoBygdbh('"+gdid+"')\">"+StringUtil.escapeStr(gdbh)+"</a>"; 
				   if(i!=0){
						htmlContent = htmlContent +"<br>";
						
					}
				   if(i!=0 && i==strs.length-1)  {
					   htmlContent = htmlContent +"</div>"; 
				   }
			   } 
		   }
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-center'>"+formatUndefine(row.isBj==1?"是":"否")+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-center'>"+formatUndefine(row.isMi==1?"是":"否")+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bz)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.bz)+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-center'>"+formatUndefine(row.zt==1?"有效":"失效")+"</td>";
		   htmlContent = htmlContent + "</tr style='vertical-align:middle'>";  
	   });
	   $("#CheckingContentList").empty();
	   $("#CheckingContentList").html(htmlContent);
}

function vieworhideWorkContent(id){
	 var flag = $("#"+id).is(":hidden");
	 if(flag){
		 $("#"+id).show();
		 $("#"+id+'icon').removeClass("icon-caret-down");
		 $("#"+id+'icon').addClass("icon-caret-up");
	 }else{
		 $("#"+id).hide();
		 $("#"+id+'icon').removeClass("icon-caret-up");
		 $("#"+id+'icon').addClass("icon-caret-down");
	 }
	 
}

function selectWoBygdbh (gdid){
	var lx=1;
	window.open(basePath+"/project/workorder/Looked?id="+gdid+"&gddlx="+lx);
}
var  jkxm={
		 calendar:"日历",
		 fuselage_flight_time:"机身飞行时间",
		 search_light_time:"搜索灯时间",
		 winch_time:"绞车时间",
		 landing_gear_cycle:"起落循环",
		 winch_cycle:"绞车循环",
		 ext_suspension_loop:"外吊挂循环",
		 search_light_cycle:"搜索灯循环",
		 special_first:"特殊监控1",
		 special_second:"特殊监控2",
		 N1:"N1",
		 N2:"N2",
		 N3:"N3",
		 N4:"N4",
		 N5:"N5",
		 N6:"N6",
	}; 

