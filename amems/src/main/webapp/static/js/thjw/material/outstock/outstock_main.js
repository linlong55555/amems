var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其它","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
var ztls=["","保存","提交","","","","","","作废","关闭","完成","撤销"];
$(document).ready(function(){
	Navigation(menuCode);
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var selectTab = $("#tablist").find("li.active").index();
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				if(selectTab == 0){
					$("#UseOutStock").click(); //调用主列表页查询
				}else if(selectTab == 1){
					$("#RepairOutStock").click(); //调用主列表页查询
				}else if(selectTab == 2){
					$("#OutStock").click(); //调用主列表页查询
				}else if(selectTab == 3){
					$("#OutStockhistory").click(); //调用主列表页查询
				}
			}
		}
	});
	
	
	initStoreSelect();
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 datepicker();
	 initNav();
	 refreshPermission();
	 $("#dprtcode").on("change",initStoreSelect);
		validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	username: {
		                validators: {
		                	notEmpty: {
		                        message: '出库人不能为空'
		                    }
		                }
		            },
		            ckrq: {
		                validators: {
		                	notEmpty: {
		                        message: '出库日期不能为空'
		                    }
		                }
		            },
		            bz: {
		                validators: {
		                	 stringLength: {
		                         max: 300,
		                         message: '长度最多不超过个300字符'
		                     }
		                }
		            }
	        	}
	    });
		
		   if($("#parameter").val()=="inventory"){
				$('#tablist li:eq(2) a').tab('show');
				inventorygoPage(1,"auto","desc");//开始的加载默认的内容 
		
			}
	 
});
/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	    
	    if(href=="#outstock"){
	   	 goPage(1,"auto","desc");//开始的加载默认的内容 
	    }else if(href=="#senda"){
	    	decodePageParam();//送修出库
	    }else if(href=="#inventory"){
	    	ids.splice(0,ids.length);
			 $("#changerepertoryid").val("");
	    	decodePageParam1();//库存出库
	    }else{
	    	historygoPage(1,"auto","desc");//开始的加载默认的内容 
	    	
	    }
	}); 
}

function initStoreSelect(){
	

	var storeHtml = "<option value=\"\">显示全部All</option>";
	var cklbs=[0,4,5,6,7,8];
	var dprtcode = $("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});

	$("#ckh").html(storeHtml);
}

function datepicker(){
	$('.date-picker').datepicker( 'setDate' , new Date());
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 
		var obj ={};
		var searchParam = gatherSearchParamout();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:5};
		obj.keyword =  $.trim($("#keyword_search_li").val());
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
		obj.ckh = searchParam.ckh;
		obj.sqrname = searchParam.sqrname;
		obj.dprtcode = searchParam.dprtcode;
		
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/outstock/stockList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
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
						signByKeyword($("#keyword_search_li").val(),[2]);
						   checkedAll();
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
						  
						  $("#list1").empty();
						  $("#list1").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
					   }
		      }
		    }); 
	 }
	 
	 //单行选择
	 function checkedAll() {
	
		  $("#list tr").on("click",function(){
			  
			  $('#list tr').css('background-color','');
				 var inputStr = $(this).find("td input").attr("id");
				 var index=$(this).index(); //当前行
				 
				 $(this).css('background-color','#EAEAEA');
				 var lysqdh =$("input[name='lysqdh']").eq(index).val(); //当前行，隐藏序列号的值
				 var sqrid =$("input[name='sqrid']").eq(index).val(); //当前行，隐藏序列号的值
				 var sqrq =$("input[name='sqrq']").eq(index).val(); //当前行，隐藏序列号的值
				 var xgdjid =$("input[name='id']").eq(index).val(); //当前行，隐藏序列号的值
				
				 $("#lysqdh").text(lysqdh);
				 $("#sqr").text(sqrid);
				 $("#sqrq").text(StringUtil.escapeStr(sqrq).substring(0,10));
				 $("#xgdjid").val(xgdjid);
				 
				 AjaxGetDatasWithSearch1(inputStr);//开始的加载默认的内容 
		  });
	 }
	 
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch1(id){
		var obj ={};
		obj.id =id;
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/outstock/aviationList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				 
				    if(data.rows !=""){
						   appendContentHtml1(data.rows);
					   } else {
						  $("#list1").empty();
						  $("#list1").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
					   }
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParamout(){
		 var searchParam = {};
		 
		 searchParam.sqrname = $("#sqrname").val();
		 searchParam.ckh = $("#ckh").val();
		 searchParam.dprtcode = $("#dprtcode").val();
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
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			   htmlContent += "<td style='vertical-align: middle;'  align='center'><input type='hidden' id='"+row.id+"'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-center'><input type='hidden'  name='id' value='"+row.id+"'><a href='javascript:void(0);' onclick=\"viewDetail111('"+row.id+"')\"><span name='keyword'>"+StringUtil.escapeStr(row.lysqdh)+"</span></a><input type='hidden'  name='lysqdh' value='"+StringUtil.escapeStr(row.lysqdh)+"'></td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.sqrid)+"' ><input type='hidden'  name='sqrid' value='"+row.sqrid+"'>"+StringUtil.escapeStr(row.sqrid)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center' ><input type='hidden'  name='sqrq' value='"+row.sqrq+"'>"+formatUndefine(row.sqrq).substring(0,10)+"</td>";  
			 if(row.fjzch=="00000"){
				 htmlContent = htmlContent + "<td class='text-left'>通用Currency</td>";  
			 }else{
				 htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.fjzch)+"</td>";   
			 }
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.lyyy)+"'>"+StringUtil.escapeStr(row.lyyy)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
		//查看领用申请单
	 function viewDetail111(id){
	 	window.open(basePath+"/aerialmaterial/requisition/view/"+id);
	 }
	 
	 function appendContentHtml1(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   if (index % 2 == 0) {
					htmlContent += "<tr id='"+row.id+"' bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr id='"+row.id+"' bgcolor=\"#fefefe\">";
				}
			   
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' align='center'>"+ "<i class='icon-exchange color-blue cursor-pointer' onClick=\"change('"+ StringUtil.escapeStr(row.bjh) + "','"+ row.id + "')\" title='更换 Change'></i>"+"</td>"; 
			   htmlContent += "<td   align='center' style='vertical-align: middle;'><input type='hidden' id='"+row.id+"'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'style='vertical-align: middle;' name='bjh'><input type='hidden' name='idout' value='"+row.id+"'><input type='hidden' name='bjh' value='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='grn' title='"+StringUtil.escapeStr(row.grn)+"'><input name='grn' type='hidden' value='"+StringUtil.escapeStr(row.grn)+"' >"+StringUtil.escapeStr(row.grn)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='ywms' title='"+StringUtil.escapeStr(row.ywms)+"'><input name='ywms' type='hidden' value='"+StringUtil.escapeStr(row.ywms)+"' >"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='zwms' title='"+StringUtil.escapeStr(row.zwms)+"'><input name='zwms' type='hidden' value='"+StringUtil.escapeStr(row.zwms)+"' >"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='cjjh'><input name='cjjh' type='hidden' value='"+row.cjjh+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='hclx'><input name='hclx' type='hidden' value='"+row.hclx+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   var kcsl = formatUndefine(row.kcsl);
			   if(kcsl == ""){
				   kcsl = "0";
			   }
			   
				   htmlContent += "<td class='text-left' style='vertical-align: middle;' name='sn1' title='"+StringUtil.escapeStr(row.pch)+"'> <input name='pch' type='hidden' value='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
				   htmlContent += "<td class='text-left' style='vertical-align: middle;' name='sn1' title='"+StringUtil.escapeStr(row.sn)+"'> <input name='sn' type='hidden' value='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
						
				
			   
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='shzh1'><input type='hidden' name='kcid' value='"+row.kcid+"'> <input type='hidden' name='cklb' value='"+row.cklb+"'><input name='shzh' type='hidden' value='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='ckh1'> <input name='ckh' type='hidden' value='"+StringUtil.escapeStr(row.ckh)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left' style='vertical-align: middle;' name='kwh1'> <input name='kwh' type='hidden' value='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   
			   htmlContent = htmlContent + "<td class='text-right'style='vertical-align: middle;' > <input name='sqlysl' type='hidden' value='"+formatUndefine(row.sqlysl)+"'>"+
			   formatUndefine(row.sqlysl)+"</td>";  
			   
			   
			 
			   htmlContent = htmlContent + "<td class='text-right' style='vertical-align: middle;' name='kcsl1'> <input type='hidden' name='kcsl' value='"+formatUndefine(kcsl)+"'>"+formatUndefine(kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' style='vertical-align: middle;'><input class='form-control' onkeyup='clearNoNum(this)' placeholder='长度最大为10'   maxlength='10' type='text' name='cksl'></td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list1").empty();
		   $("#list1").html(htmlContent);
		   refreshPermission();
	 }

	//信息检索
	function change(bjh,id){
			$("#alertModalMaterialWinAdd").modal("show");
			AjaxGetDatasWithSearch2(bjh,id);
	}
	
	//带条件搜索的翻页
	 function AjaxGetDatasWithSearch2(bjh,id){
		var obj ={};
		obj.bjh =bjh;
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/outstock/inventoryList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
						   appendContentHtml2(data.rows);
						   checkedAll1(id);
					   } else {
					
						  $("#list2").empty();
						  $("#list2").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
					   }
		      }
		    }); 
		 
		
	 }
	 //单行选择
	 function checkedAll1(id) {
		  $("#list2 tr").on("click",function(){
					
					var index=$(this).index(); //当前行
					var sn =$("input[name='sn1']").eq(index).val(); //当前行，隐藏序列号的值
					var cklb =$("input[name='cklb1']").eq(index).val(); //当前行，隐藏序列号的值
					var pch =$("input[name='pch1']").eq(index).val(); //当前行，隐藏批次号的值
					var shzh =$("input[name='shzh1']").eq(index).val(); //当前行，隐藏适航证号的值
					var kwh =$("input[name='kwh1']").eq(index).val(); //当前行，隐藏库位号的值
					var kcsl =$("input[name='kcsl1']").eq(index).val(); //当前行，隐藏库存数量的值
					var kcid =$("input[name='kcid1']").eq(index).val(); //当前行，隐藏库存数量的值
					var ckmc =$("input[name='ckmc']").eq(index).val(); //当前行，隐藏库存数量的值
					
				   if(StringUtil.escapeStr(sn) == ""){
						$("#"+id+" td[name='sn1']").html("<input name='pch' type='hidden' value='"+StringUtil.escapeStr(pch)+"' >"+StringUtil.escapeStr(pch));
				   }else{
					   $("#"+id+" td[name='sn1']").html("<input name='sn' type='hidden' value='"+StringUtil.escapeStr(sn)+"' >"+StringUtil.escapeStr(sn));
				   }
					$("#"+id+" td[name='shzh1']").html("<input name='kcid' type='hidden' value='"+formatUndefine(kcid)+"' /><input name='cklb' type='hidden' value='"+formatUndefine(cklb)+"' /><input name='shzh' type='hidden' value='"+StringUtil.escapeStr(shzh)+"' />"+StringUtil.escapeStr(shzh));
					$("#"+id+" td[name='ckh1']").html("<input name='ckh' type='hidden' value='"+StringUtil.escapeStr(ckmc)+"'>"+StringUtil.escapeStr(ckmc));
					$("#"+id+" td[name='kwh1']").html("<input name='kwh' type='hidden' value='"+StringUtil.escapeStr(kwh)+"'>"+StringUtil.escapeStr(kwh));
					$("#"+id+" td[name='kcsl1']").html("<input name='kcsl' type='hidden' value='"+formatUndefine(kcsl)+"'>"+formatUndefine(kcsl));
					$("#alertModalMaterialWinAdd").modal("hide");//隐藏
			});
		 
	 }
	 
	 function appendContentHtml2(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			   
			   htmlContent += "<td   align='center'><input type='hidden' id='"+formatUndefine(row.id)+"'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='kcid1' value='"+row.id+"'><input type='hidden' name='bjh' value='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'><input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'><input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='cjjh' value='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='hclx' value='"+formatUndefine(row.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   if(StringUtil.escapeStr(row.sn)== ""){
					htmlContent += "<td  class='text-left' style='vertical-align: middle; '><input type='hidden' name='pch1' value='"+StringUtil.escapeStr(row.pch)+"' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
				}else{
					htmlContent += "<td  class='text-left' style='vertical-align: middle; '><input type='hidden' name='sn1' value='"+StringUtil.escapeStr(row.sn)+"' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
				}
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='cklb1' value='"+formatUndefine(row.cklb)+"'><input type='hidden' name='shzh1' value='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='ckmc' value='"+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='kwh1' value='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'><input type='hidden' name='kcsl1' value='"+formatUndefine(row.kcsl)+"'>"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list2").empty();
		   $("#list2").html(htmlContent);
		   refreshPermission();
	 }
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}

	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage1(id,pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch1(id,pageNumber,sortType,sequence);
	}	
	
	
	
	//信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
	
		
	//搜索条件重置
	function searchreset(){
		$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search_li").val("");
	 $("#dprtcode").val(zzjgid);
	 $("#dprtcode").trigger("change");
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

		
	$('#ckrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
      .updateStatus('ckrq', 'NOT_VALIDATED',null)  
      .validateField('ckrq');  
  });
	
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
		}
	 /**
	  * 选择用户
	  * @returns
	  */
	 function selectUser(){
	 	userModal.show({
	 		selected:$("#userid").val(),//原值，在弹窗中默认勾选
	 		dprtcode:$("#dprtcode").val(),
	 		callback: function(data){//回调函数
	 			$("#userid").val(formatUndefine(data.id));
	 			$("#username").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
	 			$("#ckbmid").val(StringUtil.escapeStr(data.bmdm));
	 			
	 			  $('#form').data('bootstrapValidator')  
	 		      .updateStatus('username', 'NOT_VALIDATED',null)  
	 		      .validateField('username');  
	 		}
	 	})
	 }	
	 
	 /**
	  * 出库
	  * @returns
	  */
	 function putoutstorage(){
		 
		 $('#form').data('bootstrapValidator').validate();
		 
		  if(!$('#form').data('bootstrapValidator').isValid()){
			return false;
		  }	
		  
				
				var workContentParam = [];
				var len = $("#list1").find("tr").length;
				
				var fola=true;
				if (len > 0){
					
					$("#list1").find("tr").each(function(){
						var infos ={};
						var index=$(this).index(); //当前行
						
						var id =$("input[name='idout']").eq(index).val(); //当前行，隐藏id的值
						var bjh =$("input[name='bjh']").eq(index).val(); //当前行，隐藏件号的值
						var zwms =$("input[name='zwms']").eq(index).val(); //中文名称
						var ywms =$("input[name='ywms']").eq(index).val(); //英文名称
						var cjjh =$("input[name='cjjh']").eq(index).val(); //厂家件号
						var hclx =$("input[name='hclx']").eq(index).val(); //航材类型
						var cklb =$("input[name='cklb']").eq(index).val(); //航材类型
						
						var sn =$("input[name='sn']").eq(index).val(); //序列号
						var pch ="";
						if(sn==""){
							 pch =$("input[name='pch']").eq(index).val(); //批次号
						}	
						
						var kcid =$("input[name='kcid']").eq(index).val(); //库存id
						var shzh =$("input[name='shzh']").eq(index).val(); //适航证号
						var kwh =$("input[name='kwh']").eq(index).val(); //库位号
						var sqlysl =$("input[name='sqlysl']").eq(index).val(); //申请数量
						var kcsl =$("input[name='kcsl']").eq(index).val(); //库存数量
						var cksl =$("input[name='cksl']").eq(index).val(); //出库数量
						var folt=true;
						
						if(cksl==""||cksl==null){
							AlertUtil.showErrorMessage("出库数量不能为空");
							fola= false;
							folt=false;
							return false;
						}
						if(parseInt(cksl)>parseInt(kcsl)||parseInt(cksl)>parseInt(sqlysl)){
							AlertUtil.showErrorMessage("出库数不能大于申请数或者库存数");
							fola= false;
							folt=false;
							return false;
						}
						
						if(cklb==0||cklb==1||cklb==2||cklb==4||cklb==3){
							AlertUtil.showErrorMessage("仓库类别不正确,不能出库");
							fola= false;
							folt=false;
							return false;
						}
						
						if(folt==false){
							return false;
						}
						
						infos.kcid  = kcid;
						infos.cklb  = cklb;
						infos.id  = id;
						infos.bjh  = bjh;
						infos.zwms  = zwms;
						infos.ywms  = ywms;
						infos.cjjh  = cjjh;
						infos.sqlysl  = sqlysl;
						infos.hclx  = hclx;
						infos.sn  = sn;
						if(sn==""){
							infos.pch  = pch;
						}
						infos.shzh  = shzh;
						infos.kwh  = kwh;
						infos.kcsl  = kcsl;
						infos.cksl  = cksl;
						workContentParam.push(infos);
					});
					
				}else{
					AlertUtil.showErrorMessage("请选择需要出库的申请单");
					return false;
				}
				
				if(fola==false){
					return false;
				}
				
				var fixedCheckItem = {
						
						xgdjid :$.trim($('#xgdjid').val()),//相关单据id
						cukid :$.trim($('#userid').val()),//出库人id
						ckbmid :$.trim($('#ckbmid').val()),//出库人id
						cksj :$.trim($('#ckrq').val()),//出库日期
						bz :$.trim($('#outstockbz').val()),//备注
					};
				
			fixedCheckItem.materialRequisitionDetail = workContentParam;
			 AlertUtil.showConfirmMessage("你确定要出库吗？",{callback: function(){
						
				startWait();
			
				// 提交数据
				AjaxUtil.ajax({
					url : basePath+"/aerialmaterial/outstock/stockRemoval",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(fixedCheckItem),
					dataType:"json",
					success:function(data){
							finishWait();//结束Loading
						if (data.state == "success") {
							AlertUtil.showMessage('出库成功!','/aerialmaterial/outstock/main');
							refreshPermission();
						} else {
							AlertUtil.showErrorMessage(data.message);
						}
					}
				});
				}});
	 }	 

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
	      
			strs=obj.value.split("."); //字符分割
			if(strs.length>1){
				if(strs[1]>99){
					obj.value=strs[0]+'.'+strs[1].substr(0, 2);
				}
			}
	 }
