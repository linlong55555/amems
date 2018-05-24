var cklx=["其它","领用出库","送修出库","归还出库","借出出库","报废出库","退货出库"];
var bs=["否","是"];
$(document).ready(function(){
	 changeOrganization();
	
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#zzjgid").val());
	var planeRegOption = '<option value="" >显示全部All</option><option value="00000" >通用Currency</option>';
	
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
	$("#fjzch_searchhistory").html(planeRegOption);
}
	
	//信息检索
	function searchhistory1(){
		historygoPage(1,"auto","desc");
	}

	 //带条件搜索的翻页-出库单信息
	 function historyAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParamhistory();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:5};
		obj.keyword =  $.trim($("#historykeyword_search").val());
		
		obj.fjzch = searchParam.fjzch;
	    obj.zt = searchParam.zt;
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
	
		obj.dprtcode = searchParam.dprtcode;
		obj.cklx = searchParam.cklx;
		
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
						   
				    	 	 new Pagination({
									renderTo : "historypagination",
									data: data,
									sortColumn :sequence,
									orderType : sortType,
									goPage:function(a,b,c){
										historyAjaxGetDatasWithSearch(a,c,b);
									}
								}); 
				    	   
						// 标记关键字
						   signByKeyword($("#historykeyword_search").val(),[3]);
							
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
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   if (index % 2 == 0) {
					htmlContent += "<tr id='"+row.id+"' bgcolor=\"#f9f9f9\" onClick=\"historyAjaxGetDatasSearch('"+ row.id + "','"+ index + "',this)\">";
				} else {
					htmlContent += "<tr id='"+row.id+"' bgcolor=\"#fefefe\" onClick=\"historyAjaxGetDatasSearch('"+ row.id + "','"+ index + "',this)\">";
				}
			   
//			htmlContent = htmlContent + "<td class='fixed-column' align='center'><input name='checkradio' ghid='"+row.id+"' bjh='"+StringUtil.escapeStr(row.bjh)+"'  dghsl='"+row.dghsl+"' type='checkbox' /></td>";
			  if(row.cklx==5||row.zt==11||row.cklx==6){
				  htmlContent = htmlContent + "<td class='fixed-column'></td>";
			  }else{
				   htmlContent = htmlContent + "<td class='fixed-column' align='center'><input type='hidden' value='1' name='"+ row.id + "'><i name='"+ row.id + "' class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:outstock:main:08' onClick=\"backout('"+ row.id + "')\" title='撤销  Backout'></i>"+"</td>"; 
			  }
			
			   htmlContent += "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left'><a class='cursor-pointer' href=\"javascript:openView('"+row.id+"')\">"+StringUtil.escapeStr(row.ckdh)+"</a></td>";  
			   htmlContent = htmlContent + "<td  class='text-left'><input type='hidden'  name='id' value='"+row.id+"'>"+StringUtil.escapeStr(cklx[row.cklx])+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.sqr)+"' ><input type='hidden'  name='sqrid' value='"+StringUtil.escapeStr(row.hth)+"'>"+StringUtil.escapeStr(row.sqr)+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+formatUndefine(row.sqsj).substring(0,10)+"</td>"; 
			  if(StringUtil.escapeStr(row.fjzch)=="00000"){
				  htmlContent = htmlContent + "<td class='text-left' >通用Currency</td>";  
			  }else{
				  htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.fjzch)+"</td>";  
			  }
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jddxms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jdfzr)+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+StringUtil.escapeStr(bs[row.sgbs])+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+StringUtil.escapeStr(ztls[row.zt])+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ckr)+"'>"+StringUtil.escapeStr(row.ckr)+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+formatUndefine(row.cksj).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdri)+"'>"+StringUtil.escapeStr(row.zdri)+"</td>";  
			   htmlContent = htmlContent + "<td align='center'>"+StringUtil.escapeStr(row.zdsj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#historylist").empty();
		   $("#historylist").html(htmlContent);
		   refreshPermission();
	 }
	 
	 /**
	  *查询出库历史
	  */
	 function openView(id){
	 	window.open(basePath+"/outboundhistory/history/view?id="+id);
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
	 function historyAjaxGetDatasSearch(id,index,objradio){
			$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
		 //$("#jkPanel :checkbox[name=id]:eq("+index+")").attr("checked", $(objradio).is(":checked"));
		// $(objradio).find("input[type='radio']").attr("checked",true);
		 $("input[name='checkradio']").attr("checked",false);
		 $(".sticky-col input[name='checkradio']:eq("+index+")").attr("checked",true);
		 $("#historylist input[name='checkradio']:eq("+index+")").attr("checked",true);
		 
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
						  $("#historyaviationlist").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
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
			   
			   if(row.ckzt==11 || row.cklx==5 || row.cklx==6){
					  htmlContent = htmlContent + "<td></td>";
			   }else{
				  htmlContent = htmlContent + "<td align='center'><input type='hidden' value='1' name='"+ row.id + "'><i name='"+ row.id + "' class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:outstock:main:09'  onClick=\"cancelling('"+ row.id + "','"+ row.kcsl + "','"+ row.tksl + "','"+ row.ckid + "')\" title='退库  Back Stock'></i>"+"</td>"; 
			   }
			   
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' >"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   
			  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>";  
				
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>";  
			
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
		 
