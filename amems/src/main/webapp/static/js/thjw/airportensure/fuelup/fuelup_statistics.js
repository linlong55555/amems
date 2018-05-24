 $(document).ready(function(){
	dprtcodeChange();
	
	var myDate = new Date();
	var month=myDate.getMonth()+1;
	//获取当前月1号到当前日的时间差
	setDefaultInDate();
	//默认加载
	$("#aaa").click();
});
 function setDefaultInDate(){
	 var curDate = new Date();
		var year = curDate.getFullYear();
		var month = (curDate.getMonth()+1)<10?"0"+(curDate.getMonth()+1):(curDate.getMonth()+1);
		var day = curDate.getDate()<10?"0"+curDate.getDate():curDate.getDate();
		$("#jyrq").data('daterangepicker').setStartDate(year+"-"+month+"-"+"01");
		$("#jyrq").data('daterangepicker').setEndDate(year+"-"+month+"-"+day);
		$("#jyrq").val(year+"-"+month+"-"+"01"+" ~ "+year+"-"+month+"-"+day);
	}
 
//获取查询条件
function gatherSearchParam(){
	 var searchParam = {};
	 var flightdate = $("#jyrq").val();
	 if(null != flightdate && "" != flightdate){
/*		 searchParam.jyDateBegin = flightdate.substring(0,10)+" 00:00:00";
		 searchParam.jyDateEnd = flightdate.substring(13,23)+" 23:59:59";*/
		 searchParam.jyDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
		 searchParam.jyDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
	 }
	 searchParam.dprtcode = $("#dprtcode").val();
	 searchParam.jd = $("#jd").val();
	 
	 return searchParam;
}
function selectFl(){
	var jyrq=$("#jyrq").val();
	if(jyrq=="" || jyrq==null){
		AlertUtil.showErrorMessage('请先选择日期!');
		return
	}
	
	var fl=$("#selectType").val();
	var viewType = $('input[name="viewType"]:checked ').val();
	/*if(fl==1 && viewType==1){
		searchRevisionFjDetailList(1,"auto","desc");
	}else if(fl==1 && viewType==2){
		searchRevisionFyrDetailList(1,"auto","desc");
	}else if(fl==2 && viewType==1){
		searchRevisionFjSummary(1,"auto","desc");
	}else if(fl==2 && viewType==2){
		searchRevisionFyrSummary(1,"auto","desc");
	}*/
	
	
	if(fl==1){
		searchRevisionDetailYf();
	}else if(fl==2 && viewType==1){
		searchRevisionFjSummary(1,"auto","desc");
	}else if(fl==2 && viewType==2){
		searchRevisionFyrSummary(1,"auto","desc");
	}
}


//根据选项调用方法(明细)
function selectDetailList(){
	var jyrq=$("#jyrq").val();
	if(jyrq=="" || jyrq==null){
		AlertUtil.showErrorMessage('请先选择日期!');
		return
	}
	var viewType=$("#viewType").val();
	var viewType = $('input[name="viewType"]:checked ').val();
	/*if(viewType==1){
		
		searchRevisionFjDetailList(1,"auto","desc");
	} else {
		
		searchRevisionFyrDetailList(1,"auto","desc");
	}*/
	searchRevisionDetailYf();
	
}
//根据选项调用方法(汇总)
function selectSummary(){
	var jyrq=$("#jyrq").val();
	if(jyrq=="" || jyrq==null){
		AlertUtil.showErrorMessage('请先选择日期!');
		return
	}
	var viewType=$("#viewType").val();
	var viewType = $('input[name="viewType"]:checked ').val();
	if(viewType==1){
		
		searchRevisionFjSummary(1,"auto","desc");
	} else {
		
		searchRevisionFyrSummary(1,"auto","desc");
	}
}
//加载汇总（飞机）
function searchRevisionFjSummary(pageNumber,sortType,sequence){
	$("#selectType").val("2");
	var obj ={};
	var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword = $.trim($("#keyword_search").val());
	obj.jyDateBegin = searchParam.jyDateBegin;
	obj.jyDateEnd = searchParam.jyDateEnd;
	obj.jd = searchParam.jd;
	obj.dprtcode = searchParam.dprtcode;
	startWait();
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/fuelup/fuelupSummaryList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			   /* if(data.total>0){*/
			    	appendContentHtmlSummary(data);
			    	
			    	/*new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								searchRevisionFjDetailList(a,b,c);
							}
						}); */
					// 标记关键字
					   //signByKeyword($("#keyword_search").val(),[2,3]);
					   
				 /*  } else {
					   $("#DetailTh").empty();
					   $("#DetailTd").empty();
					  $("#pagination").empty();
					  $("#DetailTh").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				   }*/
	      }
	    });
}

function appendContentHtmlSummary(list){
	   var htmlContentTh='';
	   var htmlContent = '';
	   htmlContentTh= htmlContentTh + "<tr bgcolor=\"#fefefe\">";
	   htmlContentTh= htmlContentTh +'<th style="width: 100px;padding: 0px;">';
	   htmlContentTh= htmlContentTh +'	<div class="out">';
	   htmlContentTh= htmlContentTh +'		<b>飞机</b>';
	   htmlContentTh= htmlContentTh +'		<em>日期</em>';
	   htmlContentTh= htmlContentTh +'	</div>';
	   htmlContentTh= htmlContentTh +'</th>';
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>地点</th>";
	   $.each(list.title,function(index,row){
		 htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>"+StringUtil.escapeStr(row)+"</th>";
	   });
	  /* htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>单价(元/吨)</th>";*/
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>总价(元)</th>";
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>月度耗油(吨)</th>";
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>月度金额(元)</th>";
	   htmlContentTh= htmlContentTh + "</tr>"
	   
	   var xjzl=0;
	   var xjje=0;
	   $.each(list.value,function(index,row){
		   var i=row.length;
		   var boole=true;
		   var ypzlValue=0;
		   var ydjeValue=0;
		   	$.each(row,function(index,ro){
		   		ypzlValue= ypzlValue+parseFloat(ro.ypzl);
		   		ydjeValue= ydjeValue+parseFloat(ro.ypfy);
		   	});
		   	ypzlValue=parseFloat(ypzlValue/1000000);
		   
			   $.each(row,function(index,ro){
				   var objBoole=true;
				   if (index % 2 == 0) {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
				   } else {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
				   }
				   if(boole){
					   htmlContent =htmlContent+ "<td style='min-width: 80px;vertical-align:middle;' class='text-center' rowspan="+i+">"+ro.date+"</td>";
					   var zj=0;
					   boole=false;
					   htmlContent= htmlContent + "<td class='text-left'>"+ro.jdms+"</td>";
					   
					   $.each(list.title,function(index,title){
						   
						   
						   
						   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
							   zj+=parseFloat(formatUndefine(ro[title])); 
							   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro[title]).toFixed(2)+"</td>";
					   		}else{
					   		 htmlContent= htmlContent + "<td class='text-right'>0.00</td>";
					   		}
					   	});
					   
					   $.each(list.jd,function(index,ful){
						   if(ful.JD==ro.code){
							   ful.zjia=parseFloat(ful.zjia)+ro.ypfy;
						   }
					   });
					   
					  
					   /*htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy)+"</td>";*/
					   htmlContent= htmlContent + "<td class='text-right'>"+/*zjValue.toFixed(2)*/parseFloat(ro.ypfy).toFixed(2)+"</td>";
					   htmlContent= htmlContent + "<td style='min-width: 80px;vertical-align:middle;' class='text-right' rowspan="+i+">"+ypzlValue.toFixed(2)+"</td>";
					   htmlContent= htmlContent + "<td style='min-width: 80px;vertical-align:middle;' class='text-right' rowspan="+i+">"+ydjeValue.toFixed(2)+"</td>";
					   xjzl=parseFloat(xjzl)+parseFloat(ypzlValue);
					   xjje=parseFloat(xjje)+parseFloat(ydjeValue);
					   htmlContent= htmlContent + "</tr>";
				   }else{
					   htmlContent= htmlContent + "<td class='text-left'>"+ro.jdms+"</td>";
					   var zj=0;
					   
					   $.each(list.title,function(index,title){
						   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
							   zj+=parseFloat(formatUndefine(ro[title])); 
							   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro[title]).toFixed(2)+"</td>";
						   		}else{
						   		 htmlContent= htmlContent + "<td class='text-right'>0.00</td>";	
						   		}
						   });
					   var zjValue=parseFloat(ro.ypfy);
					   $.each(list.jd,function(index,ful){
						   if(ful.JD==ro.code){
							   ful.zjia=parseFloat(ful.zjia)+zjValue;
						   }
					   });
					   
					   /*htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy)+"</td>";*/
					   htmlContent= htmlContent + "<td class='text-right'>"+/*zjValue.toFixed(2)*/parseFloat(ro.ypfy).toFixed(2)+"</td>";
					   
					   htmlContent= htmlContent + "</tr>";
				   }
				  
			   });
			   
	   });
	   
	   $.each(list.value,function(index,row){
		   $.each(row,function(index,ro){ 
			  
			   $.each(list.title,function(index,title){
				   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
					   
				   $.each(list.jd,function(index,ful){
					   if(ful.JD==ro.code){
						   ful[title]=parseFloat(ful[title])+parseFloat(formatUndefine(ro[title])); 
					   }
				   });
				   }
			   });
			   
		   });
		   
		  
	   });
	   
	   var boole=true;
	   var i=(list.jd).length;
	   $.each(list.jd,function(index,ful){
		  if (index % 2 == 0) {
			   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
		   } else {
			   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
		   }
		   if(boole){
			   htmlContent =htmlContent+ "<td style='min-width: 80px;vertical-align:middle;' class='text-center' rowspan="+i+">小计</td>";
			   htmlContent= htmlContent + "<td class='text-left'>"+ful.JDMS+"</td>";
			   $.each(list.title,function(index,title){
				   
				   if(formatUndefine(ful[title])!="" && formatUndefine(ful[title])!=undefined){
					   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ful[title]).toFixed(2)+"</td>";
				   }else{
					   htmlContent= htmlContent + "<td class='text-right'>0.00</td>";
				   }
			   		});
			   var zjvalue=parseFloat(ful.zjia);
			   htmlContent= htmlContent + "<td class='text-right'>"+zjvalue.toFixed(2)+"</td>";
			   htmlContent= htmlContent + "<td style='min-width: 80px;vertical-right:middle;' class='text-right' rowspan="+i+">"+xjzl.toFixed(2)+"</td>";
			   htmlContent= htmlContent + "<td style='min-width: 80px;vertical-right:middle;' class='text-right' rowspan="+i+">"+xjje.toFixed(2)+"</td>";
			   boole=false;
		   }else{
			   htmlContent= htmlContent + "<td class='text-left'>"+ful.JDMS+"</td>";
			   $.each(list.title,function(index,title){
				   htmlContent= htmlContent + "<td class='text-right'>"+formatUndefine(ful[title])+"</td>";
			   		});
			   var zjvalue=parseFloat(ful.zjia);
			   htmlContent= htmlContent + "<td class='text-right'>"+zjvalue.toFixed(2)+"</td>";
		   }
		   
		   
		   
		   
	   });
	   
	   if(htmlContent==""){
		   var a=list.title.length;
		   a=a+6;
		   htmlContent=htmlContent+"<tr><td class='text-center' colspan="+a+">暂无数据 No data.</td></tr>";
	   }
	   $("#SummaryTh").empty();
	   $("#SummaryTd").empty();
	   $("#SummaryTh").html(htmlContentTh);
	   $("#SummaryTd").html(htmlContent);
	   
	   refreshPermission();
}
//加载汇总（加油人）
function searchRevisionFyrSummary(pageNumber,sortType,sequence){
	$("#selectType").val("2");
	var obj ={};
	var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword = $.trim($("#keyword_search").val());
	obj.jyDateBegin = searchParam.jyDateBegin;
	obj.jyDateEnd = searchParam.jyDateEnd;
	obj.jd = searchParam.jd;
	obj.dprtcode = searchParam.dprtcode;
	startWait();
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/fuelup/fuelupFyrSummaryList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			   /* if(data.total>0){*/
			    	appendContentHtmlFyrSummary(data);
			    	
			    	/*new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								searchRevisionFjDetailList(a,b,c);
							}
						}); */
					// 标记关键字
					   //signByKeyword($("#keyword_search").val(),[2,3]);
					   
				 /*  } else {
					   $("#DetailTh").empty();
					   $("#DetailTd").empty();
					  $("#pagination").empty();
					  $("#DetailTh").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				   }*/
	      }
	    });
}

function appendContentHtmlFyrSummary(list){
	   var htmlContentTh='';
	   var htmlContent = '';
	   htmlContentTh= htmlContentTh + "<tr bgcolor=\"#fefefe\">";
	   htmlContentTh= htmlContentTh +'<th style="width: 100px;padding: 0px;">';
	   htmlContentTh= htmlContentTh +'	<div class="out">';
	   htmlContentTh= htmlContentTh +'		<b>加油人</b>';
	   htmlContentTh= htmlContentTh +'		<em>日期</em>';
	   htmlContentTh= htmlContentTh +'	</div>';
	   htmlContentTh= htmlContentTh +'</th>';
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>地点</th>";
	   $.each(list.title,function(index,row){
		 htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>"+StringUtil.escapeStr(row)+"</th>";
	   });
	   /*htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>单价(元/吨)</th>";*/
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>总价(元)</th>";
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>月度耗油(吨)</th>";
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>月度金额(元)</th>";
	   htmlContentTh= htmlContentTh + "</tr>"
	   var xjzl=0;
	   var xjje=0;
	   $.each(list.value,function(index,row){
		   
		   var i=row.length;
		   var boole=true;
		   var ypzlValue=0;
		   var ydjeValue=0;
		   	$.each(row,function(index,ro){
		   		ypzlValue= ypzlValue+parseFloat(ro.ypzl);
		   		ydjeValue= ydjeValue+parseFloat(ro.ypfy);
		   	});
		   	ypzlValue=parseFloat(ypzlValue/1000000);
			   $.each(row,function(index,ro){
				   if (index % 2 == 0) {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
				   } else {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
				   }
				   if(boole){
					   htmlContent =htmlContent+ "<td style='min-width: 80px;vertical-align:middle;' class='text-center' rowspan="+i+">"+ro.date+"</td>";
					   var zj=0;
					   boole=false;
					   htmlContent= htmlContent + "<td class='text-left'>"+ro.jdms+"</td>";
					   $.each(list.title,function(index,title){
						   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
							   zj+=parseFloat(formatUndefine(ro[title])); 
							   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(formatUndefine(ro[title])).toFixed(2)+"</td>";
						   		}else{
						   		 htmlContent= htmlContent + "<td class='text-right'>0.00</td>";
						   		}
					   		});
					 /*  var zjValue=parseFloat(ro.ypfy)*zj;
					   var ydzjValue=parseFloat(ro.ypzl)*parseFloat(ro.ypfy)*zj/1000000;*/
					 /*  xjzl=parseFloat(xjzl)+parseFloat(ro.ypzl);
					   xjje=parseFloat(xjje)+parseFloat(ydzjValue);*/
					   var zjValue=parseFloat(ro.ypfy);
					   $.each(list.jd,function(index,ful){
						   if(ful.JD==ro.code){
							   ful.zjia=parseFloat(ful.zjia)+zjValue;
						   }
					   });
					   
					  /* htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy)+"</td>";*/
					   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy).toFixed(2)+"</td>";
					   htmlContent= htmlContent + "<td class='text-right' rowspan="+i+">"+ypzlValue.toFixed(2)+"</td>";
					   htmlContent= htmlContent + "<td class='text-right' rowspan="+i+">"+ydjeValue.toFixed(2)+"</td>";
					   xjzl=parseFloat(xjzl)+parseFloat(ypzlValue);
					   xjje=parseFloat(xjje)+parseFloat(ydjeValue);
					   htmlContent= htmlContent + "</tr>";
				   }else{
					   htmlContent= htmlContent + "<td class='text-left'>"+ro.jdms+"</td>";
					   var zj=0;
					   $.each(list.title,function(index,title){
						   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
								   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(formatUndefine(ro[title])).toFixed(2)+"</td>";
								   zj+=parseFloat(formatUndefine(ro[title])); 
						   		}else{
						   			htmlContent= htmlContent + "<td class='text-right'>0.00</td>";
						   		}
						   });
					   var zjValue=parseFloat(ro.ypfy);
					   $.each(list.jd,function(index,ful){
						   if(ful.JD==ro.code){
							   ful.zjia=parseFloat(ful.zjia)+zjValue;
						   }
					   });
					   
					   /*htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy)+"</td>";*/
					   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro.ypfy).toFixed(2)+"</td>";
					   
					   htmlContent= htmlContent + "</tr>";
				   }
				  
			   });
			   
	   });
	   $.each(list.value,function(index,row){
		   $.each(row,function(index,ro){ 
			  
			   $.each(list.title,function(index,title){
				   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
					   
				   $.each(list.jd,function(index,ful){
					   if(ful.JD==ro.code){
						   ful[title]=parseFloat(ful[title])+parseFloat(formatUndefine(ro[title])); 
					   }
				   });
				   }
			   });
			   
		   });
	   });
	   
	   var boole=true;
	   var i=(list.jd).length;
	   $.each(list.jd,function(index,ful){
		  if (index % 2 == 0) {
			   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
		   } else {
			   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
		   }
		   if(boole){
			   htmlContent =htmlContent+ "<td style='min-width: 80px;vertical-align:middle;' class='text-center' rowspan="+i+">小计</td>";
			   htmlContent= htmlContent + "<td class='text-left'>"+ful.JDMS+"</td>";
			   $.each(list.title,function(index,title){
				   htmlContent= htmlContent + "<td class='text-left'>"+parseFloat(formatUndefine(ful[title])).toFixed(2)+"</td>";
			   		});
			   /*htmlContent= htmlContent + "<td class='text-right'></td>";*/
			   var zjvalue=parseFloat(ful.zjia);
			   htmlContent= htmlContent + "<td class='text-right'>"+zjvalue.toFixed(2)+"</td>";
			   htmlContent= htmlContent + "<td class='text-right' rowspan="+i+">"+xjzl.toFixed(2)+"</td>";
			   htmlContent= htmlContent + "<td class='text-right' rowspan="+i+">"+xjje.toFixed(2)+"</td>";
			   boole=false;
		   }else{
			   htmlContent= htmlContent + "<td class='text-left'>"+ful.JDMS+"</td>";
			   $.each(list.title,function(index,title){
				   htmlContent= htmlContent + "<td class='text-right'>"+formatUndefine(ful[title])+"</td>";
			   		});
			   /*htmlContent= htmlContent + "<td class='text-right'></td>";*/
			   var zjvalue=parseFloat(ful.zjia);
			   htmlContent= htmlContent + "<td class='text-right'>"+zjvalue.toFixed(2)+"</td>";
		   }
		   
		   
		   
		   
	   });
	   if(htmlContent==""){
		   var a=list.title.length;
		   a=a+6;
		   htmlContent=htmlContent+"<tr><td class='text-center' colspan="+a+">暂无数据 No data.</td></tr>";
	   }
	   
	   $("#SummaryTh").empty();
	   $("#SummaryTd").empty();
	   $("#SummaryTh").html(htmlContentTh);
	   $("#SummaryTd").html(htmlContent);
	   
	   refreshPermission();
}

//条件搜索
function searchRevision(){
	var jyrq=$("#jyrq").val();
	if(jyrq=="" || jyrq==null){
		AlertUtil.showErrorMessage('请先选择日期!');
		return
	}
	
	var fl=$("#selectType").val();
	var viewType = $('input[name="viewType"]:checked ').val();
	if(fl==1 ){
		searchRevisionDetailYf();
	}else if(fl==2 && viewType==1){
		searchRevisionFjSummary(1,"auto","desc");
	}else if(fl==2 && viewType==2){
		searchRevisionFyrSummary(1,"auto","desc");
	}
}
//加载加油人明细
function searchRevisionFyrDetailList(pageNumber,sortType,sequence){
	$("#selectType").val("1");
	var selectDate=$("#selectDate").val();
	var obj ={};
	var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword = $.trim($("#keyword_search").val());
	obj.jyDateBegin = searchParam.jyDateBegin;
	obj.jyDateEnd = searchParam.jyDateEnd;
	obj.jd = searchParam.jd;
	obj.dprtcode = searchParam.dprtcode;
	obj.selectDate=selectDate;
	
	startWait();
	
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/fuelup/fuelupFyrDetailList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   
			    finishWait();
			   /* if(data.total>0){*/
			    	appendContentHtmlFyr(data);
			    	/*new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								searchRevisionFyrDetailList(a,b,c);
							}
						}); */
					// 标记关键字
					   //signByKeyword($("#keyword_search").val(),[2,3]);
					   
				 /*  } else {
					   $("#DetailTh").empty();
					   $("#DetailTd").empty();
					  $("#pagination").empty();
					  $("#DetailTh").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				   }*/
	      }
	    });
}
//加载明细月份
function searchRevisionDetailYf(){
	var jyrq=$("#jyrq").val();
	if(jyrq=="" || jyrq==null){
		AlertUtil.showErrorMessage('请先选择日期!');
		return
	}
	
	var searchParam = gatherSearchParam();
	var dateArry=getDateArry(searchParam.jyDateBegin, searchParam.jyDateEnd);
	appendContentHtmlYf(dateArry);
	
	var viewType = $('input[name="viewType"]:checked ').val();
	if(viewType==1){
		searchRevisionFjDetailList(1,"auto","desc");
	}else if(viewType==2){
		searchRevisionFyrDetailList(1,"auto","desc");
	}
}

function appendContentHtmlYf(dateArry){
	var htmlContent = '';
	$.each(dateArry,function(index,date){ 
		var choId='cho'+date;
		 if (index == 0) {
			 	$("#selectDate").val(date);
				htmlContent= htmlContent + "<tr bgcolor=\"#fefefe\"style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=\"chooseDate('"+date+"','"+choId+"')\" class='sel' id="+choId+">";
		 }else{
			 htmlContent= htmlContent + "<tr bgcolor=\"#fefefe\" style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=\"chooseDate('"+date+"','"+choId+"')\" id="+choId+">";
		 }
		 htmlContent= htmlContent +"<td>";
		 htmlContent= htmlContent +date;
		 htmlContent= htmlContent +"</td>";
		 
		
	});
	$("#DetailDate").empty();
	$("#DetailDate").append(htmlContent);
}
var objDate="";
//查看当月数据
function chooseDate(date,e){
	objDate=e;
	$("#selectDate").val(date);
	
	var viewType = $('input[name="viewType"]:checked ').val();
	if(viewType==1){
		searchRevisionFjDetailList(1,"auto","desc");
	}else if(viewType==2){
		searchRevisionFyrDetailList(1,"auto","desc");
	}
	
	
	//行变色
	$("#"+objDate).addClass('sel').siblings().removeClass('sel');
	
}
function appendContentHtmlFyr(list){
	   var htmlContentTh='';
	   var htmlContent = '';
	   htmlContentTh= htmlContentTh + "<tr bgcolor=\"#fefefe\">";
	   htmlContentTh= htmlContentTh +'<th style="width: 100px;padding: 0px;">';
	   htmlContentTh= htmlContentTh +'	<div class="out">';
	   htmlContentTh= htmlContentTh +'		<b>加油人</b>';
	   htmlContentTh= htmlContentTh +'		<em>日期</em>';
	   htmlContentTh= htmlContentTh +'	</div>';
	   htmlContentTh= htmlContentTh +'</th>';
	   $.each(list.title,function(index,row){
		 htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>"+StringUtil.escapeStr(row)+"</th>";
	   });
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>地点</th></tr>";
	   
	   
	   $.each(list.value,function(index,row){
		   
			   $.each(row,function(index,ro){
				   if (index % 2 == 0) {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
				   } else {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
				   }
				   
				   htmlContent =htmlContent+ "<td class='text-center'>"+ro.date+"</td>";
				   $.each(list.title,function(index,title){
					   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
						   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro[title]).toFixed(2)+"</td>";
					   }else{
						   htmlContent= htmlContent + "<td class='text-right'></td>";
					   }
					   
							   
					   });
				   htmlContent= htmlContent + "<td>"+ro.jdms+"</td>";
				   htmlContent= htmlContent + "</tr>";
			   });
			   
	   });
	   
	   if(htmlContent==""){
		   var a=list.title.length;
		   a=a+2;
		   htmlContent=htmlContent+"<tr><td class='text-center' colspan="+a+">暂无数据 No data.</td></tr>";
	   }
	     // $("#DetailTb").style.display="min-width: 2600px !important"
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   /*$.each(list,function(index,row){
		   if(index ==0){
			   htmlContentTh= htmlContentTh + "<tr bgcolor=\"#fefefe\">";
			   for(var i=0;i<row.length;i++){
				   if(i==0){
					   htmlContentTh= htmlContentTh +'<th style="width: 100px;padding: 0px;">';
					   htmlContentTh= htmlContentTh +'	<div class="out">';
					   htmlContentTh= htmlContentTh +'		<b>发油人</b>';
					   htmlContentTh= htmlContentTh +'		<em>日期</em>';
					   htmlContentTh= htmlContentTh +'	</div>';
					   htmlContentTh= htmlContentTh +'</th>';
				   }else{
					   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>"+row[i]+"</th>";
				   }
				   
				   
			   }
			   htmlContentTh= htmlContentTh + "</tr>";
			   
		   }else{
			   if (index % 2 == 0) {
				   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
			   } else {
				   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
			   }
			   for(var i=0;i<row.length;i++){
				  if(i==0){
					   htmlContent= htmlContent + "<td  class='text-center'>"+row[i]+"</td>";
				   }else{
					   htmlContent= htmlContent + "<td  class='text-right'>"+row[i]+"</td>";
				   }
			   }
			   htmlContent= htmlContent + "</tr>";
		   }
	   });*/
	   $("#DetailTh").empty();
	   $("#DetailTd").empty();
	   $("#DetailTh").html(htmlContentTh);
	   $("#DetailTd").html(htmlContent);
	   
	   refreshPermission();
	 
}
//加载飞机明细
function searchRevisionFjDetailList(pageNumber,sortType,sequence){
	$("#selectType").val("1");
	var selectDate=$("#selectDate").val();
	var obj ={};
	var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.keyword = $.trim($("#keyword_search").val());
	obj.jyDateBegin = searchParam.jyDateBegin;
	obj.jyDateEnd = searchParam.jyDateEnd;
	obj.jd = searchParam.jd;
	obj.dprtcode = searchParam.dprtcode;
	obj.selectDate=selectDate;
	startWait();
	
	 AjaxUtil.ajax({
		   url:basePath+"/airportensure/fuelup/fuelupStatisticsDetailList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			   /* if(data.total>0){*/
			    	appendContentHtml(data);
			    	
			    	/*new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								searchRevisionFjDetailList(a,b,c);
							}
						}); */
					// 标记关键字
					   //signByKeyword($("#keyword_search").val(),[2,3]);
					   
				 /*  } else {
					   $("#DetailTh").empty();
					   $("#DetailTd").empty();
					  $("#pagination").empty();
					  $("#DetailTh").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				   }*/
	      }
	    });
	
}
function appendContentHtml(list){
	   var htmlContentTh='';
	   var htmlContent = '';
	   htmlContentTh= htmlContentTh + "<tr bgcolor=\"#fefefe\">";
	   htmlContentTh= htmlContentTh +'<th style="width: 100px;padding: 0px;">';
	   htmlContentTh= htmlContentTh +'	<div class="out">';
	   htmlContentTh= htmlContentTh +'		<b>飞机</b>';
	   htmlContentTh= htmlContentTh +'		<em>日期</em>';
	   htmlContentTh= htmlContentTh +'	</div>';
	   htmlContentTh= htmlContentTh +'</th>';
	   $.each(list.title,function(index,row){
		 htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>"+StringUtil.escapeStr(row)+"</th>";
	   });
	   htmlContentTh= htmlContentTh + "<th style='min-width: 80px;vertical-align:middle;'>地点</th></tr>";
	   
	   
	   $.each(list.value,function(index,row){
		   
			   $.each(row,function(index,ro){
				   if (index % 2 == 0) {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#fefefe\">";
				   } else {
					   htmlContent =htmlContent+ "<tr bgcolor=\"#f9f9f9\">";
				   }
				   
				   htmlContent =htmlContent+ "<td class='text-center'>"+ro.date+"</td>";
				   $.each(list.title,function(index,title){
					   
					   if(formatUndefine(ro[title])!="" && formatUndefine(ro[title])!=undefined){
						   htmlContent= htmlContent + "<td class='text-right'>"+parseFloat(ro[title]).toFixed(2)+"</td>";
					   }else{
						   htmlContent= htmlContent + "<td class='text-right'></td>";
					   }
				   });
				   htmlContent= htmlContent + "<td>"+ro.jdms+"</td>";
				   htmlContent= htmlContent + "</tr>";
			   });
			   
	   });
	   
	   if(htmlContent==""){
		   var a=list.title.length;
		   a=a+2;
		   htmlContent=htmlContent+"<tr><td class='text-center' colspan="+a+">暂无数据 No data.</td></tr>";
	   }
	   
	   $("#DetailTh").empty();
	   $("#DetailTd").empty();
	   $("#DetailTh").html(htmlContentTh);
	   $("#DetailTd").html(htmlContent);
	  refreshPermission();
	 
}




$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		});
 $('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',

	includeSelectAllOption : true
});
 
 
 
 
 
 
 
 function getDateArry(jyDateBegin,jyDateEnd) {
     var d1 = jyDateBegin;
     var d2 = jyDateEnd;
     var dateArry = new Array();
     var s1 = d1.split("-");
     var s2 = d2.split("-");
     var mCount = 0;
     if (parseInt(s1[0]) < parseInt(s2[0])) {
         mCount = (parseInt(s2[0]) - parseInt(s1[0])) * 12 + parseInt(s2[1]) - parseInt(s1[1])+1;
     } else {
         mCount = parseInt(s2[1]) - parseInt(s1[1])+1;
     }
     if (mCount > 0) {
         var startM = parseInt(s1[1]);
         var startY = parseInt(s1[0]);
         for (var i = 0; i < mCount; i++) {
             if (startM < 12) {
                 dateArry[i] = startY + "-" + (startM>9 ? startM : "0" + startM);
                 startM += 1;
             } else {
                 dateArry[i] = startY + "-" + (startM > 9 ? startM : "0" + startM);
                 startM = 1;
                 startY += 1;
             }
         }
     }
     console.log(dateArry);
     return dateArry
 } 
 
 function dprtcodeChange(){
		var dprtcode = $('#dprtcode').val();
		AjaxUtil.ajax({
			url : basePath + "/airportensure/fuelup/planeBaseChange",
			type : "post",
			async : false,
			data : {
				"dprtcode":dprtcode
			},
			dataType : "json",
			success : function(data) {
				finishWait();// 结束Loading
				$('#jd').empty();
				appendChangeContent(data.planeBaseList);

			}
		});
	}
	// 拼接机型工程师和制单人 列表内容
	function appendChangeContent(data) {
		var appendChangeContent = '';
		appendChangeContent = appendChangeContent
				+ "<option value=''>显示全部</option>";
		$.each(data, function(index, row) {
			appendChangeContent = appendChangeContent + "<option value='" + row.id
					+ "'>" + StringUtil.escapeStr(row.jdms) + "</option>";
		});
		$('#jd').html(appendChangeContent);

	}
 
 
 
 
 
 