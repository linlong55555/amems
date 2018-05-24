
var zts=["","","提交","","","","","","","","","作废","修订"];
$(document).ready(function(){
	Navigation(menuCode);
	 changeOrganization();
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 changeSelectedPlane();
	});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch_search").html(planeRegOption);
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.fjzch = searchParam.fjzch;
	
		obj.dprtcode = searchParam.dprtcode;
		obj.zt = searchParam.zt;
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
		obj.keyword = $.trim($("#keyword_search").val());
		
		startWait();
		
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/flightrecord/flightrecordList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			    if(data.rows !=""){
					   appendContentHtml(data.rows);
					   	 new Pagination({
								renderTo : "pagination1",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								goPage:function(a,b,c){
									AjaxGetDatasWithSearch(a,b,c);
								}
							}); 
					   
					// 标记关键字
					   signByKeyword($("#keyword_search").val(),[3,9,10,11,12,28,41]);
					   
				   } else {
				
					  $("#planelist").empty();
					  $("#pagination1").empty();
					  $("#planelist").append("<tr><td class='text-center' colspan=\"50\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'indexList'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.fjzch = $("#fjzch_search").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 searchParam.zt = $("#zt").val();
		 
		 var flightdate = $("#flightDate_search").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.fxDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.fxDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
		 }
		 return searchParam;
	 }
	 

	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr style='cursor:default' bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr style='cursor:default' bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.fxrq).substring(0,10)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.fxjldh)+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-center'>"+StringUtil.escapeStr(row.jlbym)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.hcms)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(zts[row.zt])+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+TimeUtil.operateTime(StringUtil.escapeStr(row.fxsj||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"</td>"; 
			   if(StringUtil.escapeStr(formatUndefine(row.fxsjQljxhZ).split("`")[0])==":"){
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'></td>";  
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+TimeUtil.operateTime((StringUtil.escapeStr(formatUndefine(row.fxsjQljxhZ).split("`")[0]))||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"</td>";  
			   }
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.yl)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.qljxh)+"/"+(StringUtil.escapeStr(formatUndefine(row.fxsjQljxhZ).split("`")[1])!=""?StringUtil.escapeStr(formatUndefine(row.fxsjQljxhZ).split("`")[1]):'-')+"</td>";  
			   if(StringUtil.escapeStr(row.ssdsjZ)==":"){
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+TimeUtil.operateTime(StringUtil.escapeStr(row.ssdsj||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"/0:00"+"("+(StringUtil.escapeStr(row.xlhSsd)!=""?StringUtil.escapeStr(row.xlhSsd):'-')+")"+"</td>";  
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+TimeUtil.operateTime(StringUtil.escapeStr(row.ssdsj||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"/"+(TimeUtil.operateTime(StringUtil.escapeStr(row.ssdsjZ||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)!=""?TimeUtil.operateTime(StringUtil.escapeStr(row.ssdsjZ||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD):'-')+"("+(StringUtil.escapeStr(row.xlhSsd)!=""?StringUtil.escapeStr(row.xlhSsd):'-')+")"+"</td>";  
			   }
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.dgxh)+"/"+(StringUtil.escapeStr(row.dgxhZ)!=""?StringUtil.escapeStr(row.dgxhZ):'-')+"("+(StringUtil.escapeStr(row.xlhWdg)!=""?StringUtil.escapeStr(row.xlhWdg):'-')+")"+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.jcxh)+"/"+(StringUtil.escapeStr(row.jcxhZ)!=""?StringUtil.escapeStr(row.jcxhZ):'-')+"("+(StringUtil.escapeStr(row.xlhJc)!=""?StringUtil.escapeStr(row.xlhJc):'-')+")"+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(TimeUtil.operateTime(row.jcsj||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD))!=""?TimeUtil.operateTime(StringUtil.escapeStr(row.jcsj||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD):'-')+"/"+(StringUtil.escapeStr(row.jcsjZ)!=""?StringUtil.escapeStr(row.jcsjZ):'-')+"("+(StringUtil.escapeStr(row.xlhJc)!=""?StringUtil.escapeStr(row.xlhJc):'-')+")"+"</td>";  
			 
			   if(StringUtil.escapeStr(formatUndefine(row.f1SjN16Z).split("`")[0])==":"){
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' title='"+(StringUtil.escapeStr(row.xlhF1)!=""?StringUtil.escapeStr(row.xlhF1):'-')+")"+"' class='text-right'>-("+(StringUtil.escapeStr(row.xlhF1)!=""?StringUtil.escapeStr(row.xlhF1):'-')+")"+"</td>";  
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right' title='"+(TimeUtil.operateTime((StringUtil.escapeStr(formatUndefine(row.f1SjN16Z).split("`")[0]))||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)!=""?TimeUtil.operateTime((StringUtil.escapeStr(formatUndefine(row.f1SjN16Z).split("`")[0]))||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD):'-')+"("+(StringUtil.escapeStr(row.xlhF1)!=""?StringUtil.escapeStr(row.xlhF1):'-')+")"+"'>"+StringUtil.subString(TimeUtil.operateTime((StringUtil.escapeStr(formatUndefine(row.f1SjN16Z).split("`")[0]))||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)!=""?TimeUtil.operateTime((StringUtil.escapeStr(row.f1SjN16Z).split("`")[0])||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD):'-')+"("+(StringUtil.escapeStr(row.xlhF1)!=""?StringUtil.escapeStr(row.xlhF1):'-')+")"+"</td>";  
			   }
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1Hy)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1Wdyd)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1Glyd)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N1)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N2)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N3)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N4)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N5)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f1N6)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[1]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[2]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[3]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[4]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[5]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f1SjN16Z)!=""?StringUtil.escapeStr(row.f1SjN16Z).split("`")[6]:'')+"</td>";  
			   
			   if(StringUtil.escapeStr(row.f2SjN16Z).split("`")[0]==":"){
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right' title='"+StringUtil.escapeStr(row.xlhF2)+"'>-("+StringUtil.subString(row.xlhF2)+")"+"</td>";  
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right' title='"+TimeUtil.operateTime((StringUtil.escapeStr(row.f2SjN16Z).split("`")[0])||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"("+StringUtil.escapeStr(row.xlhF2)+")"+"'>"+StringUtil.subString(TimeUtil.operateTime((StringUtil.escapeStr(row.f2SjN16Z).split("`")[0])||0, 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD)+"("+StringUtil.escapeStr(row.xlhF2)+")")+"</td>";  
			   }
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2Hy)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2Wdyd)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2Glyd)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N1)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N2)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N3)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N4)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N5)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.f2N6)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[1]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[2]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[3]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[4]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[5]:'')+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+(StringUtil.escapeStr(row.f2SjN16Z)!=""?StringUtil.escapeStr(row.f2SjN16Z).split("`")[6]:'')+"</td>";  
			   
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.ts1)+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-right'>"+StringUtil.escapeStr(row.ts2)+"</td>"; 
				if(row.tsqk==null){
					htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>无</td>"; 
				}else{
					htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.tsqk)+"</td>"; 
				}
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+StringUtil.escapeStr(row.zdjsyy)+"'>"+StringUtil.subString(row.zdjsyy)+"</td>"; 
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#planelist").empty();
		   $("#planelist").html(htmlContent);
		 
	 }
	
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf ( "sorting_asc")>=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination1 li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}
	
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	 //信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
		
	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}
	
	
	 //搜索条件重置
	 function searchreset(){
		 $("#test11").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#test11").find("textarea").each(function(){
			 $(this).val("");
		 });
		 
		 $("#test11").find("select").each(function(){
				$(this).val("");
		});
		 
		 var zzjgid=$('#zzjgid').val();
		 $("#keyword_search").val("");
		 $("#dprtcode").val(zzjgid);
		 
		 var SelectArr = $("#fjzch_search_sel select");
		  SelectArr[0].options[0].selected = true; 
		  changeOrganization();
	 }
	 
	//搜索条件显示与隐藏 
	function search() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}

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
		 
	
		 
		//只能输入数字和小数
		 function clearNoNum(obj){
		      //先把非数字的都替换掉，除了数字和.
		      obj.value = obj.value.replace(/[^\d.]/g,"");
		      //必须保证第一个为数字而不是.
		      obj.value = obj.value.replace(/^\./g,"");
		      //保证只有出现一个.而没有多个.
		      obj.value = obj.value.replace(/\.{2,}/g,".");
		      //保证.只出现一次，而不能出现两次以上
		      obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		 }
		 
		 function outexcel(){
			 var flightDate=$("#flightDate_search").val();
			 var fxDateBegin='';
			 var fxDateEnd='';
			 if(flightDate!=''){
				 fxDateBegin=flightDate.substring(0,10);
				 fxDateEnd=flightDate.substring(13,23);
			 }
			 window.open(basePath+"/flightdata/flightrecord/FlightRecordList.xls?fjzch="+encodeURIComponent($("#fjzch_search").val())+"&fxDateBegin="+fxDateBegin+"&fxDateEnd="+fxDateEnd
			 +"&zt="+$("#zt").val()+"&dprtcode="+$("#dprtcode").val());
		 }
