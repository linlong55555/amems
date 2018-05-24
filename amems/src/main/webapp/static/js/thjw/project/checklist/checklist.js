$(document).ready(function(){
	Navigation(menuCode);
	decodePageParam();//开始的加载默认的内容 
});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#dprtcode_search").val(params.dprtcode );
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		 AjaxUtil.ajax({
			 url:basePath+"/project/checklist/queryCheckList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					});
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,6,7,8,9]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'scheduled_table'});
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var jkxm = $.trim($("#jkxm_search").val());
		 var zt = $.trim($("#zt_search").val());
		 var zdrid = $.trim($("#zdrid_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val());
		 
		 searchParam.keyword = keyword;
         searchParam.jkxm = jkxm;
         searchParam.zt = zt;
         searchParam.zdrid = zdrid;
         searchParam.dprtcode = dprtcode;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   var num=0;
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#fefefe\">";
			   }
			   if(row.zt!=9&&row.zt!=10){
				   htmlContent = htmlContent + 
				   "<td  class='text-center fixed-column' style='vertical-align:middle'>"
				   + "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:end' username='"+StringUtil.escapeStr(row.username)+"'  djrwbh='"+StringUtil.escapeStr(row.djrwbh)+"' onClick=\"end('"+ row.id + "',this)\" title='指定结束 End'></i>&nbsp;&nbsp;"
				   + "<i class='icon-print color-blue cursor-pointer '  permissioncode='project:revisionnoticebook:print' onClick=\"print('"+ row.id + "')\" title='Print/打印'></i>&nbsp;&nbsp;"
				   +"</td>";
			   }else{
				   htmlContent = htmlContent + 
				   "<td class='text-center fixed-column' style='vertical-align:middle'>"
				   + "<i class='icon-print color-blue cursor-pointer '  permissioncode='project:revisionnoticebook:print' onClick=\"print('"+ row.id + "')\" title='Print/打印'></i>&nbsp;&nbsp;"
				   +"</td>";
			   }
			   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align:middle'><a href=\"javascript:Looked('"+ row.id + "')\">"+StringUtil.escapeStr(row.djrwbh)+"</a></td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.djbh)+"' style='vertical-align:middle'><a href=\"javascript:LookedCheckItem('"+StringUtil.escapeStr(row.djbh) + "','"+ row.dprtcode + "')\">"+StringUtil.escapeStr(row.djbh)+"</a></td>"; 
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-right'>"+StringUtil.escapeStr(row.bb)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.wxfabh)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.wxfabh)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.fjzch)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			   if(row.jhgsRs*row.jhgsXss==0||row.jhgsRs*row.jhgsXss==""){
				   htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' class='text-right'>0</td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' class='text-right'>"+formatUndefine(row.jhgsRs)+"人X"+formatUndefine(row.jhgsXss)+"时="+row.jhgsRs*row.jhgsXss+"时"+"</td>";
			   }
               
			   if(row.jkxmbhF!=null&&row.jkxmbhF!=""){
				   num++;
			   }
			   if(row.jkxmbhS!=null&&row.jkxmbhS!=""){
				   num++;
			   }
			   if(row.jkxmbhT!=null&&row.jkxmbhT!=""){
				   num++;
			   }
			   if(num==3){
				   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhF])+':'+formatUndefine(row.jkzF)+'<br>'+formatUndefine(jkxm[row.jkxmbhS])+':'+formatUndefine(row.jkzS)+'<br>'+formatUndefine(jkxm[row.jkxmbhT])+':'+formatUndefine(row.jkzT)+"</td>";
			   }else if(num==2){
				   if(row.jkxmbhF==null||row.jkxmbhF==""){
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhS])+':'+formatUndefine(row.jkzS)+'<br>'+formatUndefine(jkxm[row.jkxmbhT])+':'+formatUndefine(row.jkzT)+"</td>";
				   }else if(row.jkxmbhS==null||row.jkxmbhS==""){
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhF])+':'+formatUndefine(row.jkzF)+'<br>'+formatUndefine(jkxm[row.jkxmbhT])+':'+formatUndefine(row.jkzT)+"</td>";
				   }else{
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhF])+':'+formatUndefine(row.jkzF)+'<br>'+formatUndefine(jkxm[row.jkxmbhS])+':'+formatUndefine(row.jkzS)+"</td>";
				   };
			   }else if(num==1){
				   if(row.jkxmbhF!=null&&row.jkxmbhF!=""){
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhF])+':'+formatUndefine(row.jkzF)+"</td>";
				   }else if(row.jkxmbhS!=null&&row.jkxmbhS!=""){
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhS])+':'+formatUndefine(row.jkzS)+"</td>";
				   }else{
					   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhT])+':'+formatUndefine(row.jkzT)+"</td>";
				   };
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+formatUndefine(jkxm[row.jkxmbhT])+"</td>";
			   }
			   if(row.zt==9){
				   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'>";
				   htmlContent = htmlContent +"<a href='javascript:void(0);' djrwbh='"+StringUtil.escapeStr(row.djrwbh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' displayname='"+StringUtil.escapeStr(row.displayname)+"' onclick=\"javascript:LookedZdjs(this,'"+row.zdjsrq+"')\">指定结束</a><br>"; 
				   htmlContent = htmlContent + "</td>";
			   }else{
				   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'>"+DicAndEnumUtil.getEnumName('workOrderStateEnum',row.zt)+"</td>";
			   }
			   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+StringUtil.escapeStr(row.username)+' '+StringUtil.escapeStr(row.realname)+"</td>";
			   htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			   htmlContent = htmlContent + "<td style='vertical-align:middle'>"+StringUtil.escapeStr(row.dprtname)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
	 }
	 
	   //查看定检任务单
		function Looked(id) {                    
			 window.open(basePath+"/project/checklist/Looked?id=" + id);
		}
		 //查看定检项目
		 function LookedCheckItem(djbh,dprtCode){
			 window.open(basePath+"/project/fixedcheckitem/selectByZxbsAndBh?djbh="+ djbh+"&dprtCode="+dprtCode);
		 }

		function LookedZdjs(obj,zdjsrq){
			$("#alertModalZdjsReson").modal("show");
			$("#djrwbh2").val($(obj).attr("djrwbh"));
			$("#zdjsyy2").val($(obj).attr("zdjsyy") );
			$("#zdjsrq").val(zdjsrq);
			$("#zdjsr").val($(obj).attr("displayname"));
		}
		
		//指定结束定检任务单
		function end(id,obj) {
			AjaxUtil.ajax({
				  url:basePath+'/productionplan/plantask/alreadyCompleted',
				  type : "post",
				  data : {id:id,idSource:4},
				  dataType : "json",
				  async: false,
				  cache:false,
				  success:function(data) {
					  if(data.alreadyCompleted){
						  AlertUtil.showConfirmMessage("该任务已完工，您确定指定结束定检任务单吗？",{callback: function(){
						    $("#alertModalZdjs").modal("show");
						    $("#id").val(id);                                                                        //将工单参数赋值
							$("#djrwbh").val($(obj).attr("djrwbh"));
							$("#zdjsyy").val("");
						  }});
					  }	else{
						    $("#alertModalZdjs").modal("show");
						    $("#id").val(id);                                                                        //将工单参数赋值
							$("#djrwbh").val($(obj).attr("djrwbh"));
							$("#zdjsyy").val("");
					  }
				  }
		    }); 	  
		}
		function zdjsOver(){
		   //参数组装
		  var obj = {};
		  obj.id = $.trim($("#id").val());				                                  //主键id
	      obj.zdjsyy =  $.trim($("#zdjsyy").val());						      //指定结束原因
	      obj.zt=9;
		      
	  	  //判断指定结束原因
	  	  var zdjsyy = $.trim($("#zdjsyy").val());
	  	  if (zdjsyy == "") {
	  		  $('#alertErrorModalBody').html("指定结束原因不能为空");
			  $('#alertErrorModal').modal('show');
	  		  return false;
	  	  }
	  	startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/checklist/Over",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('指定结束成功!');
				id=obj.id;
 				refreshPage();
			},
		});
	}
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "asc";
		} else {
			orderType = "desc";
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderType);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	//信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}
		
	//搜索条件重置
	function searchreset(){
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
		$("#gdlx_search").selected="selected";
	});
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	});
	 $("#zilei").find("select").each(function(){
			$(this).val("");
	});
	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(zzjgid);
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
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		};
		
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
		
		 
