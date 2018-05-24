
var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
$(document).ready(function(){
	Navigation(menuCode);
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	initStoreSelect();
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 $("#dprtcode").on("change",initStoreSelect);
	 initNav();
    // 初始化导入
    importModal.init({
	    importUrl:"/aerialmaterial/stock/excelImport",
	    downloadUrl:"/common/templetDownfile/8",
		callback: function(data){
			// 刷新库存信息
			 goPage(1,"auto","desc");//开始的加载默认的内容 
			 $("#ImportModal").modal("hide");
		}
	});
    
    //初始化日志功能
    logModal.init({code:'B_H_001',extparam:{'REC_YJLX':7}});
    
    
});

/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	    if(href=="#stock"){
	    	 goPage(1,"auto","desc");//开始的加载默认的内容 
	    }else if(href=="#fieldmaterial"){
	    	 goPage2(1,"auto","desc");//开始的加载默认的内容 
	    }else{
	    	 goPage1(1,"auto","desc");//开始的加载默认的内容 
	    }
	}); 
}

function initStoreSelect(){
	var storeHtml = "<option value=\"\">显示全部All</option>";
	var dprtcode = $("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if( dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	storeHtml += "<option value=\""+"50"+"\">"+"外场"+"</option>";
	$("#ckh").html(storeHtml);
}

var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;
//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/stock/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 if(!isLoadMonitor){
			loadMonitorsettings();
		}
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search").val());
		obj.hclx = searchParam.hclx;
		obj.ckh = searchParam.ckh;
		obj.zt = searchParam.zt;
		obj.dprtcode = searchParam.dprtcode;
		
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/stock/editList",
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
						   signByKeyword($("#keyword_search").val(),[2,3,4,5,6]);
						   refreshPermission();
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"23\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'zkhc'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 
		 searchParam.hclx = $("#hclx").val();
		 searchParam.zt = $("#zt").val();
		 searchParam.ckh = $("#ckh").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if(parseInt(row.syts)<=parseInt(yjtsJb1)){
				   htmlContent = htmlContent + "<tr   bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.syts)>parseInt(yjtsJb1) && parseInt(row.syts)<=parseInt(yjtsJb2)){
				   htmlContent = htmlContent + "<tr     bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   if (index % 2 == 0) {
						  htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
					   } else {
						   htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\">";;
					   }
			   }
			   
			   //checkPermission
			   htmlContent = htmlContent + 
			   "<td class='fixed-column' class='text-center' style='vertical-align:middle'>"
			   /*+ "<input type='checkbox' onchange=\"checkbox_change('"+index+"',this);\" name='box' value='"+row.id+"'/>"*/
			   htmlContent = htmlContent + "<i class='icon-edit  color-blue cursor-pointer  checkPermission' permissioncode='aerialmaterial:stock:editmain:01' onClick=\"edit('"+ row.id + "','"+row.kcly+"')\" title='编辑 Edit'></i>&nbsp;&nbsp;"
			   +"</td>";
			   htmlContent =htmlContent+ "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='fixed-column text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
				htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
				htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.syts)+"</td>";  
			   console.info(row.sytss)
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.spqx).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.sytss)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(zt[row.zt])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+formatUndefine(row.rkrid)+"'>"+formatUndefine(row.rkrid)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' >"+formatUndefine(row.rksj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
	 }
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc")>=0) {
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
	 $("#keyword_search").val("");
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
		 
		 function stockOutPDF(){
			 obj = document.getElementsByName("box");
			    check_val = [];
			    for(var i=0;i<obj.length;i++){
			        if(obj[i].checked){
			            check_val.push(obj[i].value);			  
			        }
			    }
			    if(check_val.length!=0){
			    	var url=basePath+"/aerialmaterial/stock/stockOutPDF?id=" + encodeURIComponent(check_val)+"&dprtcode="+encodeURIComponent($("#dprtcode").val());
			    	window.open(basePath+'/static/lib/pdf.js/web/pdfViewer.html?file='+encodeURIComponent(url),
							'PDF','width:50%;height:50%;top:100;left:100;');
		 }	else{
			 AlertUtil.showErrorMessage("请选择一个航材");
		 }
		 }
		//全选
		 function checkAll(){
				$("[name=box]:checkbox").each(function() { 
					 $(this).attr("checked", 'checked'); 
				 });
				 
			}
		//全不选
		 function notCheckAll(){
				 $("[name=box]:checkbox").each(function() { 
					 $(this).removeAttr("checked"); 
				 });
			}
		 
		 /**
	  	  * 显示导入模态框
	  	  */
	  	 function showImportModal(){
	  		 importModal.show();
	  	 }
	 	function checkbox_change(index, _this){
			$("#zkhc :checkbox[name=box]:eq("+index+")").attr("checked", $(_this).is(":checked"));
			
		}
	 	
	 	function edit(id,kcly){
	 		stockEditComp.show({id:id,kcly:kcly});
	 	}
	 	
	 	
