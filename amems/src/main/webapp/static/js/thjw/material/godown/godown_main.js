var gljb=["","无","批次号管理","序列号管理"];
var jy=["","合格","不合格","让步接收"];
var rklx=["","采购","送修","借入","借出归还","其它"];
var ztls=["","保存","提交","","","","","","作废","关闭","完成","撤销"];
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
	
	refreshPermission();
	decodePageParam();
	
});


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());
	params.keyword = $.trim($("#keyword_search").val());
	params.rkrq = $("#rkrq").val();
	params.jyrq = $("#jyrq").val();
	params.zt = $("#zt").val();
	params.rklx = $("#rklx").val();
	params.jyjg = $("#jyjg").val();
	
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
		$("#dprtcode").val(params.dprtcode);
		$("#keyword_search").val(params.keyword);
	    $("#rkrq").val(params.rkrq);
	    $("#jyrq").val(params.jyrq);
	    $("#rklx").val(params.rklx);
	    $("#zt").val(params.zt);
	    $("#jyjg").val(params.jyjg);
		
	    
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
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword = $.trim($("#keyword_search").val());
		
		obj.paramsMap ={ rukDateBegin:searchParam.rukDateBegin,
						 rukDateEnd:searchParam.rukDateEnd,
						 jyDateBegin:searchParam.jyDateBegin,
						 jyDateEnd:searchParam.jyDateEnd,
						 jyjg:searchParam.jyjg
					   };
		obj.dprtcode = $.trim(searchParam.dprtcode);
		obj.rklx = $.trim(searchParam.rklx);
		obj.zt = $.trim(searchParam.zt);
		
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/godown/godownEntryList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
					
				    finishWait();
				    if(data.total>0){
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
				       signByKeyword($("#keyword_search").val(),[3,5,6,7,8,12]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"22\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'jrsq'});
		      }
		    }); 
		 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.dprtcode = $.trim($("#dprtcode").val());
		 searchParam.rklx = $.trim($("#rklx").val());
		 searchParam.zt = $.trim($("#zt").val());
		 searchParam.jyjg = $.trim($("#jyjg").val());
		 var rkrq = $.trim($("#rkrq").val());
		 var jyrq = $.trim($("#jyrq").val());
			
		 if(null != rkrq && "" != rkrq){
			 searchParam.rukDateBegin = rkrq.substring(0,4)+"-"+rkrq.substring(5,7)+"-"+rkrq.substring(8,10);
			 searchParam.rukDateEnd = rkrq.substring(12,17)+"-"+rkrq.substring(18,20)+"-"+rkrq.substring(21,23);
		 }
		 if(null != jyrq && "" != jyrq){
			 searchParam.jyDateBegin = jyrq.substring(0,4)+"-"+jyrq.substring(5,7)+"-"+jyrq.substring(8,10);
			 searchParam.jyDateEnd = jyrq.substring(12,17)+"-"+jyrq.substring(18,20)+"-"+jyrq.substring(21,23);
		 }
		 
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent = htmlContent + "<tr>";
			   htmlContent = htmlContent + "<td class='fixed-column text-center'>";
			   if(row.zt==1){
				   htmlContent += "<i class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:godown:main:01' onClick=\"stoctIn('" + row.id + "',"+row.zt+")\" title='入库 Warehousing'></i>";
			   }
			   if(row.zt==2){
				   htmlContent +="&nbsp;&nbsp;<i class='icon-share-alt color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:godown:main:02' onClick=\"cancel('"+ row.id + "',"+row.zt+")\" title='撤销 Revoked'></i>";
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.rkdh)+"</a>";
			   htmlContent += "</td>";
			   var rklxs=0;
			   if(StringUtil.escapeStr(row.rklx)==99){
				   rklxs=5;
			   }else{
				   rklxs=row.rklx;
			   }
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(rklx[rklxs])+"</td>";  
			   htmlContent += "<td class='text-left '>"+StringUtil.escapeStr(row.hcmainData.bjh)+"</td>"; 
			   htmlContent += "<td class='text-left '>"+StringUtil.escapeStr(row.grn)+"</td>"; 
			   htmlContent += "<td class='text-left '  title='"+StringUtil.escapeStr(row.hcmainData.ywms)+"'>"+StringUtil.escapeStr(row.hcmainData.ywms)+"</td>"; 
			   htmlContent += "<td class='text-left '  title='"+StringUtil.escapeStr(row.hcmainData.zwms)+"'>"+StringUtil.escapeStr(row.hcmainData.zwms)+"</td>"; 
			   htmlContent += "<td class='text-left '>"+StringUtil.escapeStr(gljb[row.hcmainData.gljb])+"</td>"; 
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.receiptSheetDetail.sl)+"</td>"; 
			   htmlContent += "<td class='text-right '>"+formatUndefine(row.rksl)+"</td>"; 
			   if(row.inspection.jydh==null){
				   htmlContent += "<td class='text-left'>收货单号：<a href='javascript:void(0);' onclick=\"viewDetailsh('"+row.receiptSheet.id+"')\">"+row.receiptSheet.shdh+"</a></td>";  
			   }else{
				  htmlContent += "<td class='text-left' >"+"检验单号：<a href='javascript:void(0);' onclick=\"viewDetailjy('"+row.inspection.id+"')\">"+row.inspection.jydh+"</a> - 收货单号：<a href='javascript:void(0);' onclick=\"viewDetailsh('"+row.receiptSheet.id+"')\">"+row.receiptSheet.shdh+"</a></td>";  
			   }
			   htmlContent += "<td class='text-left'>"+formatUndefine(row.fhdw)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(ztls[row.zt])+"</td>";  
			   htmlContent += "<td class='text-left'>"+formatUndefine(row.rukid)+"</td>";  
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.rkrq).substring(0,10)+"</td>";  
			   htmlContent += "<td class='text-left'>"+formatUndefine(jy[row.inspection.jyjg])+"</td>";  
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.inspection.jyrq).substring(0,10)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zdrid)+"'>"+StringUtil.escapeStr(row.zdrid)+"</td>";  
			   htmlContent +="<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent += "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }

	//查看入库单详情
	 function viewDetail(id){
		 window.open( basePath+"/aerialmaterial/godown/view?id=" + id+"&pageParam="+encodePageParam());
	 }
	 
	 //查看检验单详情
	 function viewDetailjy(id){
		 window.open( basePath+"/aerialmaterial/inspection/Looked?id=" + id);
	 }
	 
	 //查看收货单详情
	 function viewDetailsh(id){
		 window.open( basePath+"/aerialmaterial/receipt/view/"+id);
	 }
	 
	//入库
	 function stoctIn(id,zt){
	 	if(zt != 1){
	 		AlertUtil.showErrorMessage("对不起,只有保存状态下的入库单才能操作!");
	 		return false;
	 	}
	 	//验证状态
//	 	if(checkEdit(id)){	
	 		window.location = basePath+"/aerialmaterial/godown/stoctIn?id=" + id+"&pageParam="+encodePageParam();
//	 	}
	 }		
	 
	//作废
	 function cancel(id,zt) {
		 
	 	if(zt != 2){
	 		AlertUtil.showMessage("对不起,只有提交状态下的入库单才能撤销!");
	 		return false;
	 	}
		AlertUtil.showConfirmMessage("您确定要撤销吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/aerialmaterial/godown/cancel",
	 			type:"post",
	 			async: false,
	 			data:{
	 				id : id
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('撤销成功!');
	 				searchRevision();
	 				refreshPermission();
	 			}
	 		});
	 	}});
	 }
	
		//字段排序
		function orderBy(obj, _obj) {
			$obj = $("#jrsq th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#jrsq .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#jrsq .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
			var orderType = "asc";
			if (orderStyle.indexOf ( "sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#pagination li[class='active']").text();
			goPage(currentPage,obj, orderType);
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
		 
