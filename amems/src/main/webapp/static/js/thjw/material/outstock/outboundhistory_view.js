var cklx=["其它","领用出库","送修出库","归还出库","借出出库","报废出库","退货出库"];
var bs=["否","是"];
var ztls=["","保存","提交","","","","","","作废","关闭","完成","撤销"];
$(document).ready(function(){
	Navigation(menuCode,"查看出库单","Select Outstock");
	historygoPage(1,"auto","desc");
});

	 //带条件搜索的翻页-出库单信息
	 function historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParamhistory();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:5};
	
		obj.dprtcode = searchParam.dprtcode;
		obj.id = $("#viewid").val();
		console.info($("#viewid").val());
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/outboundhistory/history/historyList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
				    	   historyappendContentHtml(data.rows);
					   } else {
						  $("#historylist").empty();
						  $("#historypagination").empty();
						  $("#historylist").append("<tr><td class='text-center' colspan=\"18\">暂无数据 No data.</td></tr>");
						  $("#historyaviationlist").empty();
					   	  $("#historyaviationlist").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'ckls'});
		      }
		    }); 
	 }
	 
	 function historyappendContentHtml(list){
		 $("#ckdh").val(list[0].ckdh);
		 $("#cklx").val(cklx[list[0].cklx]);
		 $("#sqr").val(list[0].sqr);
		 $("#sqsj").val(formatUndefine(list[0].sqsj).substring(0,10));
		 if(StringUtil.escapeStr(list[0].fjzch)=="00000"){
			  $("#fjzch").val("通用Currency");
		  }else{
			  $("#fjzch").val(StringUtil.escapeStr(list[0].fjzch));
		  }
		 $("#jddxms").val(list[0].jddxms);
		 $("#jdfzr").val(list[0].jdfzr);
		 $("#sgbs").val(bs[list[0].sgbs]);
		 $("#zt").val(ztls[list[0].zt]);
		 $("#ckr").val(StringUtil.escapeStr(list[0].ckr));
		 $("#cksj").val(formatUndefine(list[0].cksj).substring(0,10));
		 $("#zdri").val(StringUtil.escapeStr(list[0].zdri));
		 $("#zdsj").val(StringUtil.escapeStr(list[0].zdsj));
		 $("#bz").val(StringUtil.escapeStr(list[0].bz));
		 historyAjaxGetDatasSearch(list[0].id);
	 }
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function historygoPage(pageNumber,sortType,sequence){
		historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
 
	 //将搜索条件封装 
	 function gatherSearchParamhistory(){
		 var searchParam = {};
		 
		 searchParam.zt = $("#zthistory").val();
		 searchParam.cklx = $("#cklx").val();
		 searchParam.fjzch = $("#fjzch_searchhistory").val();
		 searchParam.dprtcode = $("#dprtcode3").val();
		 var flightdate = $("#flightDate_searchhistory").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.fxDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.fxDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
		 }
		 
		 return searchParam;
	 }
	
	//搜索条件显示与隐藏 
	function searchhistory() {
		if ($("#divSearchhistory").css("display") == "none") {
			$("#divSearchhistory").css("display", "block");
			$("#iconhistory").removeClass("icon-caret-down");
			$("#iconhistory").addClass("icon-caret-up");
		} else {
			$("#divSearchhistory").css("display", "none");
			$("#iconhistory").removeClass("icon-caret-up");
			$("#iconhistory").addClass("icon-caret-down");
		}
	
	}

	 //撤销
	function backout(id){
		//验证是否可以撤销
		 if(checkUpdMt(id)){
			 AlertUtil.showConfirmMessage("你确定要撤销吗？",{callback: function(){
			 
				// 提交数据
				AjaxUtil.ajax({
					url : basePath+"/outboundhistory/history/backout/"+id,
					dataType:"json",
					success:function(data){
							finishWait();//结束Loading
						if (data.state == "success") {
							$("#alertModal").modal("show");
								$('#alertModal').on('hidden.bs.modal', function (e) {
									window.location = basePath+"/aerialmaterial/outstock/main?t=" + new Date().getTime()+"#history";
								});
							$("#alertModalBody").html("撤销成功!");
							refreshPermission();
						} else {
							AlertUtil.showErrorMessage(data.message);
						}
					}
				});
		
				}});
				}
	}	

	//验证是否可以撤销
	 function checkUpdMt(ids){
	 	var flag = false;
	 	AjaxUtil.ajax({
	 		url:basePath+"/outboundhistory/history/checkUpdMt",
	 		type:"post",
	 		async: false,
	 		data:{
	 			'ids' : ids
	 		},
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();//结束Loading
	 			if (data.state == "success") {
	 				flag = true;
	 			} else {
	 				AlertUtil.showErrorMessage(data.message);
	 			}
	 		}
	 	});
	 	return flag;
	 }
	 
	 
	//带条件搜索的翻页-出库单信息
	 function historyAjaxGetDatasSearch(id){
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/outboundhistory/history/aviationList/"+id,
			   dataType:"json",
			   cache:false,
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
				    	historyappendHtml(data.rows);
					   } else {
						  $("#historyaviationlist").empty();
						  $("#historyaviationlist").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					   }
		      }
		    }); 
	 }
	 
	 function historyappendHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			   
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>";  
				
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>";  
			
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.tksl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>"; 
			   htmlContent = htmlContent + "<td align='center' title='"+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#historyaviationlist").empty();
		   $("#historyaviationlist").html(htmlContent);
		   refreshPermission();
	 }
	 
		//搜索条件重置
		function searchresethistory(){
			$("#divSearchhistory").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearchhistory").find("textarea").each(function(){
			$(this).val("");
		});
		
		 $("#divSearchhistory").find("select").each(function(){
				$(this).val("");
			});
		
		 var zzjgid=$('#zzjgid').val();
		 $("#historykeyword_search").val("");
		 $("#dprtcode3").val(zzjgid);
		 
		 var SelectArr1 = $("#fjzch_search_zt select");
		  SelectArr1[0].options[0].selected = true; 
		 
		 var SelectArr = $("#fjzch_search_history select");
		  SelectArr[0].options[0].selected = true; 
		}
		
		//退库弹窗
		function cancelling(id,kcsl,tksl,ckid){
			$("#detailid").val(id);
			$("#ckid").val(ckid);
			$("#tksl").val((parseFloat(kcsl*100)-parseFloat(tksl*100))/100);
			$("#updtaeuploading").modal("show");
		}
		
		//航材退库
		function cancellingStock(){
			var fixedCheckItem = {
					id :$.trim($('#detailid').val()),//出库明细表id
					tksl :$.trim($('#tksl').val()),//退库数量
				};
			if($('#tksl').val()==""||$('#tksl').val()==0){
				 AlertUtil.showErrorMessage("退库数量不能为空!");
				 return;
			}
			
		//验证是否可以退库
		 if(checkUpd($('#detailid').val(),$('#tksl').val())){
			 AlertUtil.showConfirmMessage("你确定要退库吗？",{callback: function(){
			
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url : basePath+"/outboundhistory/history/cancellingStock",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(fixedCheckItem),
				dataType:"json",
				success:function(data){
						finishWait();//结束Loading
					if (data.state == "success") {
						$("#alertModal").modal("show");
						$('#alertModal').on('hidden.bs.modal', function (e) {
							window.location = basePath+"/aerialmaterial/outstock/main?t=" + new Date().getTime()+"#history";
						});
						$("#alertModalBody").html("退库成功!");
						refreshPermission();
					} else {
						AlertUtil.showErrorMessage(data.message);
					}
				}
			});
				}});
				
		 }
			 
		}
		
		//验证是否可以退库
		 function checkUpd(ids,tksl){
		 	var flag = false;
		 	AjaxUtil.ajax({
		 		url:basePath+"/outboundhistory/history/checkUpd",
		 		type:"post",
		 		async: false,
		 		data:{
		 			'ids' : ids,
		 			'tksl' :tksl
		 		},
		 		dataType:"json",
		 		success:function(data){
		 			finishWait();//结束Loading
		 			if (data.state == "success") {
		 				flag = true;
		 			} else {
		 				AlertUtil.showErrorMessage(data.message);
		 			}
		 		}
		 	});
		 	return flag;
		 }
		
		//只能输入数字和小数
		 function clearNoNum1(obj){
			//先把非数字的都替换掉，除了数字和.
				obj.value = obj.value.replace(/[^\d.]/g,"");
				//必须保证第一个为数字而不是.
				obj.value = obj.value.replace(/^\./g,"");
				//保证只有出现一个.而没有多个.
				obj.value = obj.value.replace(/\.{2,}/g,".");
				//保证.只出现一次，而不能出现两次以上
				obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		      
				strs=obj.value.split("."); //字符分割
				if(strs.length>1){
					if(strs[1]>99){
						obj.value=strs[0]+'.'+strs[1].substr(0, 2);
					}
				}
		 }
		 
			//字段排序
			function orderByhistory(obj, _obj) {
				$obj = $("#ckls th[name='column_"+obj+"']");
				var orderStyle = $obj.attr("class");
				$("#ckls .sorting_desc").removeClass("sorting_desc").addClass("sorting");
				$("#ckls .sorting_asc").removeClass("sorting_asc").addClass("sorting");
				
				var orderType = "asc";
				if (orderStyle.indexOf ( "sorting_asc")>=0) {
					$obj.removeClass("sorting").addClass("sorting_desc");
					orderType = "desc";
				} else {
					$obj.removeClass("sorting").addClass("sorting_asc");
					orderType = "asc";
				}
				var currentPage = $("#historypagination li[class='active']").text();
				historygoPage(currentPage,obj, orderType);
			}
		 
