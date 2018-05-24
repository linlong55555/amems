
var selectIndex = -1;
var tempgysid = "";

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
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
	
	//changeContractType();
	$("#contractDetailList").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
	$("#payList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
	MessageListViewUtil.show({messageHead : false});//显示留言
	AttachmengsListView.show({attachHead : false});//显示附件
	decodePageParam(1,"auto","desc");//开始的加载默认的内容 
	showorhideEnquiry(1);
	refreshPermission();
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
	params.htlx = $.trim($("#htlx_search").val());
	params.dhzt = $.trim($("#dhzt_search").val());
	params.jjcd = $.trim($("#jjcd_search").val());
	params.gysid = $.trim($("#gys_search").val());
	params.zdrname = $.trim($("#zdr_search").val());
	params.zdrq = $.trim($("#zdrq_search").val());
	params.htqdrq = $.trim($("#htqdrq_search").val());
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
		$("#htlx_search").val(params.htlx);
		$("#dhzt_search").val(params.dhzt);
		$("#jjcd_search").val(params.jjcd);
		$("#zdr_search").val(params.zdrname);
		$("#zdrq_search").val(params.zdrq);
		$("#htqdrq_search").val(params.htqdrq);
		$("#dprtcode_search").val(params.dprtcode);
		$("#gys_search").val(params.gysid);
		tempgysid = params.gysid;
		changeContractType();
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		tempgysid = "";
		changeContractType();
		goPage(1,"auto","desc");
	}
}

//合同类型改变时,加载供应商信息
function changeContractType(){
	var searchParam = {};
	searchParam.gyslb = $("#htlx_search").val();
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryFirmList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   async:false,
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   $("#gys_search").empty();
	   var supplierOption = '<option value="" >显示全部All</option>';
	   if(data.length >0){
		   $.each(data,function(index,row){
			   
			   if(tempgysid == row.id){
				   supplierOption += '<option value="'+row.id+'" selected=true >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }else{
				   supplierOption += '<option value="'+row.id+'" >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }
			   
		   });
	   }
	   $("#gys_search").append(supplierOption);
	   $("#gys_search").selectpicker('refresh');
	   tempgysid = "";
     }
   }); 
}

	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		
		clear();
		
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		url = basePath+"/aerialmaterial/contract/queryAllPageList";
		
		startWait();
		AjaxUtil.ajax({
		   url:url,
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
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,4,7,8,13],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"16	\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'contract_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var htlx = $.trim($("#htlx_search").val());
		 var dhzt = $.trim($("#dhzt_search").val());
		 var jjcd = $.trim($("#jjcd_search").val());
		 var gysid = $.trim($("#gys_search").val());
		 var zdrname = $.trim($("#zdr_search").val());
		 var zdrq = $.trim($("#zdrq_search").val());
		 var htqdrq = $.trim($("#htqdrq_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = keyword;
		 if('' != htlx){
			 searchParam.htlx = htlx;
		 }
		 if('' != jjcd){
			 searchParam.jjcd = jjcd;
		 }
		 if('' != dhzt){
			 searchParam.dhzt = dhzt;
		 }
		 if('' != gysid){
			 searchParam.gysid = gysid;
		 }
		 if('' != zdrname){
			 paramsMap.zdrname = zdrname;
		 }
		 if(null != zdrq && "" != zdrq){
			 var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
			 var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
			 paramsMap.zdrqBegin = zdrqBegin;
			 paramsMap.zdrqEnd = zdrqEnd;
		 }
		 if(null != htqdrq && "" != htqdrq){
			 var htqdrqBegin = htqdrq.substring(0,10)+" 00:00:00";
			 var htqdrqEnd = htqdrq.substring(13,23)+" 23:59:59";
			 paramsMap.htqdrqBegin = htqdrqBegin;
			 paramsMap.htqdrqEnd = htqdrqEnd;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.id+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectContract('"+row.id+"','"+row.htlx+"','"+row.htlsh+"',"+index+")>";
			   } else {
				   htmlContent += "<tr id='"+row.id+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectContract('"+row.id+"','"+row.htlx+"','"+row.htlsh+"',"+index+")>";
			   }
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   if(row.zt == 1){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:contract:main:02' onClick=\"edit('" + row.id + "',"+row.zt+")\" title='修改 Edit'>&nbsp;&nbsp;</i>";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:contract:main:07' onClick=\"cancel('"+ row.id + "',"+row.zt+")\" title='作废 Invalid'>&nbsp;&nbsp;</i>";
			   }
			   /*if(row.zt == 2){
				   htmlContent += "<i class='icon-signin color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:contract:main:08' onClick=\"comeGood('"+ row.id + "','"+row.htlsh+"',"+row.zt+")\" title='收货 come'>&nbsp;&nbsp;</i>";
			   }*/
			   if(row.zt == 2 || row.zt == 10){
				   htmlContent += "<i class='icon-yen color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:contract:main:09' onClick=\"pay('"+ row.id + "','"+row.htlsh+"',"+row.zt+")\" title='付款 pay'>&nbsp;&nbsp;</i>";
			   }
			   if(row.zt != 1 && row.zt != 8 && row.zt != 9){
				   htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:reserve:approve:02' djid='"+row.id+"' sqdh='"+row.htlsh+"' zdjsyy='' zdjsr='' zdjsrq='' onClick=\"shutDown(this,true)\" title='指定结束 End'></i>";
			   }
			  
			   htmlContent += "</td>";
			  
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";	
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.htlsh)+"' class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.htlsh)+"</a>";
			   
			   htmlContent += "</td>";
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.hth)+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.hth)+"</a>";
			   htmlContent += "</td>";
			   
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('contractTypeEnum',row.htlx) +"</td>"
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('urgencyEnum',row.jjcd) +"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysbm)+"' class='text-left'>"+StringUtil.escapeStr(row.gysbm)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysmc)+"' class='text-left'>"+StringUtil.escapeStr(row.gysmc)+"</td>";
			   
			   htmlContent += "<td  class='text-left'>";
			   if(row.zt == 9){
				   var zdjsr = StringUtil.escapeStr(row.zdjsr?row.zdjsr.displayName:'');
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.htlsh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+row.zdjsrq+"' onclick=\"shutDown(this,false)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('contractStatusEnum',row.zt);
			   }
			   htmlContent += "</td>";

			   htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.htqdrq))+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('contractDeliveryStatusEnum',row.dhzt) +"</td>";
			   htmlContent += "<td title='"+(formatUndefine(row.htfy).toFixed(2))+"' class='text-right'>"+formatUndefine(row.htfy).toFixed(2)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   htmlContent += "<td title='"+(StringUtil.escapeStr(row.zdr?row.zdr.displayName:''))+"' class='text-left'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }

function selectContract(id,htlx,htlsh,index){
	selectIndex = index;
	var str = '';
	str = '已选择合同号'+htlsh;
	$("#selectContract").html(str);
	showorhideEnquiry(htlx);
	loadContractDetail(id,htlx);//加载合同详情
	loadContractPay(id);//加载合同付款
	
	MessageListViewUtil.show({
		messageHead : false,
		djid:id,
		lx:htlx == 1?3:4
	});//加载留言
	AttachmengsListView.show({
		attachHead : false,
		djid:id,
		fileType:"repair"
	});//加载附件
	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
}

function showorhideEnquiry(type){
	if(type == 1){
		$("#contractDetailHead tr th:eq(1)").show();
		$("#contractDetailHead tr th:eq(10)").show();
		$("#contractDetailHead tr th:eq(12)").show();
		$("#contractDetailHead tr th:eq(2)").hide();
		$("#contractDetailHead tr th:eq(9)").hide();
		$("#contractDetailHead tr th:eq(13)").hide();
		$("#contractDetailHead tr th:eq(14)").hide();
		$("#contractDetailHead tr th:eq(15)").hide();
	}else{
		$("#contractDetailHead tr th:eq(1)").hide();
		$("#contractDetailHead tr th:eq(10)").hide();
		$("#contractDetailHead tr th:eq(12)").hide();
		$("#contractDetailHead tr th:eq(2)").show();
		$("#contractDetailHead tr th:eq(9)").show();
		$("#contractDetailHead tr th:eq(13)").show();
		$("#contractDetailHead tr th:eq(14)").show();
		$("#contractDetailHead tr th:eq(15)").show();
	}
}

//加载合同详情
function loadContractDetail(id,type){
	var url = basePath+"/aerialmaterial/contract/queryReserveContractDetailList";
	if(type == 2){
		url = basePath+"/aerialmaterial/contract/queryRepairContractMaterialList";
	}
	AjaxUtil.ajax({
		async: false,
		url:url,
		type:"post",
		async:false,
		data:{mainid:id},
		dataType:"json",
		success:function(data){
			if(data != null && data.length > 0){
				appendDetailContentHtml(data,type);
			}else{
				$("#contractDetailList").empty();
			    $("#contractDetailList").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
			}
		}
	});
}

//加载合同详情列表
function appendDetailContentHtml(list,type){
	 
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			} else {
			   htmlContent += "<tr bgcolor=\"#fefefe\" >";
			}
	
			htmlContent += "<td class='text-center'>"+(index+1)+"</td>";	
			htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.SQDH)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left'>"+StringUtil.escapeStr(row.BJH)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.YWMS)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ZWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.ZWMS)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.CJJH)+"' class='text-left'>"+StringUtil.escapeStr(row.CJJH)+"</td>";
			htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.GLJB) +"</td>"
			htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX) +"</td>"

			if(type == 1){
				htmlContent += "<td title='"+formatUndefine(row.HTSL)+"' class='text-right'>"+formatUndefine(row.HTSL)+"</td>";
			}else{
				htmlContent += "<td title='"+formatUndefine(row.SN)+"' class='text-right'>"+formatUndefine(row.SN)+"</td>";
			}
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.JLDW) +"</td>";
			var totalPrice = 0;
			if(type == 1){
				htmlContent += "<td title='"+(formatUndefine(row.BJ_CLF).toFixed(2))+"' class='text-right'>"+formatUndefine(row.BJ_CLF).toFixed(2)+"</td>";
				if(formatUndefine(row.BJ_CLF) != '' && formatUndefine(row.HTSL) != '' && row.HTSL > 0){
					totalPrice = formatUndefine(row.BJ_CLF) * row.HTSL;
				}
			}else{
				htmlContent += "<td title='"+(formatUndefine(row.BJ_CLF).toFixed(2))+"' class='text-right'>"+formatUndefine(row.BJ_CLF).toFixed(2)+"</td>";
				htmlContent += "<td title='"+(formatUndefine(row.BJ_GSF).toFixed(2))+"' class='text-right'>"+formatUndefine(row.BJ_GSF).toFixed(2)+"</td>";
				htmlContent += "<td title='"+(formatUndefine(row.BJ_QTF).toFixed(2))+"' class='text-right'>"+formatUndefine(row.BJ_QTF).toFixed(2)+"</td>";
				totalPrice = formatUndefine(row.BJ_CLF) + formatUndefine(row.BJ_GSF) + formatUndefine(row.BJ_QTF);
			}
			htmlContent += "<td title='"+totalPrice.toFixed(2)+"' class='text-right'>"+totalPrice.toFixed(2)+"</td>";
			htmlContent += "<td title='"+formatUndefine(row.DHSL)+"' class='text-right'>"+formatUndefine(row.DHSL)+"</td>";
			htmlContent += "<td title='"+formatUndefine(row.RKSL)+"' class='text-right'>"+formatUndefine(row.RKSL)+"</td>";

			htmlContent += "</tr>";  
		    
	   });
	   $("#contractDetailList").empty();
	   $("#contractDetailList").html(htmlContent);
	 
}

//加载合同付款
function loadContractPay(id){
	var searchParam = {};
	searchParam.mainid = id;
	searchParam.pagination = {page:1,sort:'auto',order:'desc',rows:100};
	var url = basePath+"/aerialmaterial/contract/queryContractPayPageList";
	AjaxUtil.ajax({
		async: false,
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		async:false,
		data:JSON.stringify(searchParam),
		dataType:"json",
		success:function(data){
			if(data.total >0){
				appendPayContentHtml(data.rows);
			}else{
				$("#payList").empty();
			    $("#payList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
			}
		}
	});
}

//加载合同付款列表
function appendPayContentHtml(list){
	
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
		} else {
		   htmlContent += "<tr bgcolor=\"#fefefe\" >";
		}
	
		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+(index+1)+"</td>";	
		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+indexOfTime(formatUndefine(row.fkrq))+"</td>";
		htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+DicAndEnumUtil.getEnumName('payTypeEnum',row.fkfs) +"</td>";
		htmlContent += "<td title='"+formatUndefine(row.fkje)+"' style='text-align:right;vertical-align:middle;' >"+formatUndefine(row.fkje)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.fkfssm)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.fkfssm)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.whsj)+"</td>";

	   	htmlContent += "</tr>";  
		    
	});
	$("#payList").empty();
	$("#payList").html(htmlContent);
	 
}

//维修方案新增
function add(){
	window.location = basePath+"/aerialmaterial/contract/add?pageParam="+encodePageParam();
}
	 
//修改合同
function edit(id,zt){
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的合同才能修改!");
		return false;
	}
	if(checkEdit(id)){	
		window.location = basePath+"/aerialmaterial/contract/edit?id=" + id+"&pageParam=" + encodePageParam();
		
	}
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&pageParam=" + encodePageParam());
}

//作废
function cancel(id,zt) {
	if(zt != 1){
		AlertUtil.showErrorMessage("对不起,只有保存状态下的合同才能作废!");
		return false;
	}
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/contract/cancel",
			type:"post",
			async: false,
			data:{
				id : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
	}});
}

//收货
function comeGood(id,shutdh,zt){
	if(zt != 2){
		AlertUtil.showErrorMessage("对不起,只有提交后才能收货!");
		return false;
	}
	window.location = basePath+"/aerialmaterial/contract/comeGood?id=" + id+"&pageParam="+encodePageParam();
}

//付款
function pay(id,shutdh,zt){
	if(zt != 2 && zt != 10){
		AlertUtil.showErrorMessage("对不起,只有提交、完成后才能付款!");
		return false;
	}
	window.location = basePath+"/aerialmaterial/contract/pay?id=" + id+"&pageParam="+encodePageParam();
}

//指定结束
function shutDown(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	AssignEndModal.show({
		chinaHead:'合同号',//单号中文名称
		englishHead:'Contract No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:zdjsr,//指定结束人
		jssj:zdjsrq,//指定结束时间
		jsyy:zdjsyy,//指定结束原因
		showType : false,
		callback: function(data){//回调函数
			if(null != data && data != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy : data.gbyy
				};
				sbShutDown(obj);
			}
		}
	});
}

//确认指定结束
function sbShutDown(param) {
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/contract/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('指定结束成功!');
			$("#AssignEndModal").modal("hide");
			id = param.id;
			refreshPage();
		}
	});
}

//检查合同是否可以修改
function checkEdit(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath + "/aerialmaterial/contract/checkEdit",
 		type:"post",
 		async: false,
 		data:{
 			'id' : id
 		},
 		dataType:"json",
 		success:function(data){	
 			flag = true;
 		}
 	});
 	return flag;
}

function clear(){
	$("#contractDetailList").empty();
    $("#contractDetailList").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
    MessageListViewUtil.clear();
    AttachmengsListView.clear();	
    $("#payList").empty();
    $("#payList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
}
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc") >= 0) {
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
	
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}
		
	//搜索条件重置
	function searchreset(){
		var dprtId = $.trim($("#dprtId").val());
		
		$("#keyword_search").val("");
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
		$("#dprtcode_search").val(dprtId);
		
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
