var yjtsJb1 = '';	
var yjtsJb2 = '';
var yjtsJb3 = '';

/**
 *初始化当前js
 */
$(function(){
	Navigation(menuCode, '', '', 'SC11-1', '林龙', '2017-10-12', '林龙', '2017-10-14');//加载导航栏
    initFjzchOption();				//初始化飞机注册号
    loadMonitorsettings();			//初始化预警阀值
    
    if(paramJgdm != null && paramJgdm != ''){
    	$("#dprtcode").val(paramJgdm);
    	paramJgdm = null;
	}
    if(paramFjzch != null && paramFjzch != ''){
    	var fjzch = decodeURIComponent(Base64.decode(paramFjzch));
    	$("#fjzch").val(fjzch);
    	paramFjzch = null;
	}
    
    decodePageParam();				//解码页面查询条件和分页 并加载数据
    refreshPermission();			//权限初始化
	logModal.init({code:'B_S2_011'});//初始化日志功能
	
	if(gdId){
		Project_Add_Modal.open(gdId,gdLx); //直接打开新增界面。当工单id有值时
	}
});

/**
 * 初始化飞机注册号
 */
function initFjzchOption(){
	$("#fjzch").html("<option value='' >显示全部All</option>");
	var planeDatas = acAndTypeUtil.getACReg135145List({DPRTCODE : $("#dprtcode").val()});
	if(planeDatas.length > 0){
		for(var i = 0; i < planeDatas.length; i++){
			$("#fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
		}
	}else{
		$("#fjzch").html("<option value=''>显示全部All</option>");
	}
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	TableUtil.resetTableSorting("ProjectKeepTable");
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#dprtcode").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());     //组织机构
	params.keyword = $.trim($("#keyword_search").val());//关键字
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 */
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

/**
 * 执行预警阀值（机构代码）
 */
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/produce/reservation/item/getTechnicalThreshold",
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

/**
 * 切换组织机构
 */
function onchangeDprtcode(){
    initFjzchOption();				//初始化飞机注册号
    loadMonitorsettings();			//初始化预警阀值
}

function search(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("ProjectKeepTable");
}

/**
 * 查询条件
 */
function gatherSearchParam (){
	var searchParam={};
	var paramsMap={};
	searchParam.fjzch=$.trim($("#fjzch").val()); 			//飞机注册号
	searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
	searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
	
	var ztList="";//未关闭状态数据集合
	if($('input:checkbox[name=ztList]:checked').val()=="on"){
		ztList="1,2,4,6"; //仅显示1保存、2提交、4已批准、6审批驳回
	}
	var sqrq=$.trim($("#sqrq1").val()); //获取申请日期
    var sqBeginDate="";				 	//开始日期 
    var sqEndDate="";					//结束日期
	if(null != sqrq && "" != sqrq){
		sqBeginDate= sqrq.substring(0,4)+"-"+sqrq.substring(5,7)+"-"+sqrq.substring(8,10)+" 00:00:00";
		sqEndDate= sqrq.substring(12,17)+"-"+sqrq.substring(18,20)+"-"+sqrq.substring(21,23)+" 23:59:59";
	}
	
	var blrq=$.trim($("#blqx1").val()); //获取保留日期
	var blBeginDate="";				 	//开始日期 
	var blEndDate="";					//结束日期
	if(null != blrq && "" != blrq){
		blBeginDate= blrq.substring(0,4)+"-"+blrq.substring(5,7)+"-"+blrq.substring(8,10)+" 00:00:00";
		blEndDate= blrq.substring(12,17)+"-"+blrq.substring(18,20)+"-"+blrq.substring(21,23)+" 23:59:59";
	}
	
	//状态集合
	paramsMap.ztList=ztList;
	//申请日期
	paramsMap.sqBeginDate=sqBeginDate;
	paramsMap.sqEndDate=sqEndDate;
	//保留日期
	paramsMap.blBeginDate=blBeginDate;
	paramsMap.blEndDate=blEndDate;
	
	searchParam.paramsMap=paramsMap;
	return searchParam;
}

/**
 * 查询主单分页列表
 */
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if (id != "") {
		searchParam.id = id;
		id = "";
	}
	AjaxUtil.ajax({
	   url:basePath+"/produce/reservation/item/queryMonitorAllPageList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
 			if(data.total >0){
 			appendContentHtml(data.rows);
 			new Pagination({
				renderTo : "pagination",
				data: data,
				sortColumn : sortType,
				orderType : sequence,
				extParams:{},
				goPage: function(a,b,c){
					AjaxGetDatasWithSearch(a,b,c);
				}
			});
 			// 标记关键字
			signByKeyword($("#keyword_search").val(), [2]);
 			}else{
	 			$("#ProjectKeep_list").empty();
				$("#pagination").empty();
				$("#ProjectKeep_list").append("<tr><td class='text-center' colspan=\"20\">暂无数据 No data.</td></tr>");
 			}
 			new Sticky({tableId:'ProjectKeepTable'}); //初始化表头浮动
	   }
  }); 	
}

/**
 * 表格拼接
 */
function appendContentHtml(list){
	 var htmlContent = '';
	 $.each(list,function(index,row){
			htmlContent += "<tr>";
		    if( row.zt !==9 && row.zt !==10){ 
		    	htmlContent +=getWarningColor("#f9f9f9",parseInt(row.paramsMap?row.paramsMap.syts:''),row.zt,row.pgqx); 
		    }else{
		    	htmlContent += "<td class='fixed-column'></td>";
		    }
		    htmlContent += "<td class='fixed-column text-left' ><a href=\"javascript:findView('"+row.id+"')\">"+StringUtil.escapeStr(row.bldh)+"</a></td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.fjzch)+"</td>";
		    htmlContent += "<td class='text-center' >"+formatUndefine(row.sqrq).substring(0,10)+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.sqr:'')+"</td>";
		    htmlContent += "<td class='text-left' >";
		    if(row.paramsMap?row.paramsMap.gdbh:''!=''){
		    	htmlContent += "<a href=\"javascript:findOrderView('"+row.gdid+"','"+row.bs145+"')\"  >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gdbh:'')+"</a>【"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.paramsMap?row.paramsMap.gdzt:''))+"】";
		    }
		    htmlContent +="</td>"; 
		    
		    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.jhJsrq:'').substring(0,10)+"</td>";
		    
		    htmlContent += "<td class='text-left' >";
		    if(row.paramsMap?row.paramsMap.gkbh:''!=''){
		    	htmlContent += "<a href=\"javascript:findCardView('"+row.paramsMap.gkid+"')\"  >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gkbh:'')+"</a>";
		    }
		    htmlContent +="</td>"; 
		   
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gkbz:'')+"</td>"; 
		    htmlContent +="</td>"; 
		    htmlContent +=	"<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlContent += "<i onClick=\"filseDown('"+row.id+"')\" class='attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer '  style='font-size:15px'></i>";
			htmlContent +=	"</td>";
		    htmlContent += "<td class='text-center' >"+formatUndefine(row.blqx).substring(0,10)+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.shr1)+"</td>"; 
		    htmlContent += "<td class='text-center' >"+formatUndefine(row.shrq).substring(0,10)+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.pzr)+"</td>"; 
		    htmlContent += "<td class='text-center' >"+formatUndefine(row.pzrq).substring(0,10)+"</td>";
		    htmlContent += "<td class='text-left' >"; 
		    if(row.zt==9){
		    	htmlContent += "<a href='javascript:void(0);'  " 
		    		+ "bldh='"+StringUtil.escapeStr(row.bldh)+"'   zt='"+row.zt+"' gbyy='"+StringUtil.escapeStr(row.gbyy)+"'" 
		    		+ "gbczsj='"+StringUtil.escapeStr(row.gbsj)+"'   gbrid='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gbr:'')+"' " 
		    		+"onclick=\"zdjs('"+row.id+"', this , false)\">"+DicAndEnumUtil.getEnumName('failureKeepStatusEnum',row.zt)+"</a></td>";
		    }else if(row.zt==10){
		    	htmlContent += "<a href='javascript:void(0);'  " 
		    		+ "bldh='"+StringUtil.escapeStr(row.bldh)+"'   zt='"+row.zt+"' gbyy='"+StringUtil.escapeStr(row.gbyy)+"'" 
		    		+ "gzz='"+StringUtil.escapeStr(row.gzz)+"'   gzrq='"+StringUtil.escapeStr(row.gzrq).substring(0,10)+"' " 
		    		+ "jcz='"+StringUtil.escapeStr(row.jcz)+"'   jcrq='"+StringUtil.escapeStr(row.jcrq).substring(0,10)+"' " 
		    		+"onclick=\"shutDown('"+row.id+"', this , false)\">"+DicAndEnumUtil.getEnumName('failureKeepStatusEnum',row.zt)+"</a></td>";
		    }else{
		    	htmlContent += DicAndEnumUtil.getEnumName('failureKeepStatusEnum',row.zt); 
		    }
		    htmlContent += "</td>"; 
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gbyy)+"</td>"; 
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.id:'')+"</td>";
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"</td>"; 
		    htmlContent += "</tr>" ;
		    
		    $("#ProjectKeep_list").empty();
		    $("#ProjectKeep_list").html(htmlContent);
		    refreshPermission();			//权限初始化
		    TableUtil.addTitle("#ProjectKeep_list tr td"); //加载td title
	 });
}

/**
 * 查看工卡
 */
function findCardView(id){
	window.open(basePath+"/project2/workcard/view/"+id);
}
/**
 * 查看工单
 */
function findOrderView(id,bs145){
	if(bs145==1){
		window.open(basePath+"/produce/workorder145/woView?gdid="+id);
	}else{
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	}
}

/**
 * 搜索条件重置
 */
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
	 var SelectArr1 = $("#jxdiv select"); //机型重置为第一个
	 SelectArr1[0].options[0].selected = true;
	 $("input:checkbox[name='ztList']").attr("checked",true);
	 $("#keyword_search").val("");
	 $("#dprtcode").val(userJgdm);
     initFjzchOption();				//初始化飞机注册号
     loadMonitorsettings();			//初始化预警阀值
}

/**
 *删除
 */
function invalid(id,zt){
	var obj = {};
	var paramsMap = {};
	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/produce/reservation/item/delete",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage("删除成功!");
				decodePageParam();
			}
		});
	}});
}
//附件列表
function filseDown(id){
	FilesDownModal.show({
		 djid:id,//单据id
		 code:"produce:reservation:item:main:100",
		 callback: function(data){//回调函数
			 if(null != data && data != ''){
				 
				 data.id=id; //id
				 filesDownSub(data);//附件提交
			 }
		 }
	 });
}

//附件提交
function filesDownSub(obj){
	 startWait($("#FilesDownModal"));
   	 AjaxUtil.ajax({
 		url:basePath+"/produce/reservation/item/filesDownSub",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		modal:$("#FilesDownModal"),
 		success:function(data){
 			id = data;	
 			pageParam=encodePageParam();
 			finishWait($("#FilesDownModal"));
 			AlertUtil.showMessage('保存成功!');
 			$("#FilesDownModal").modal("hide");
 			decodePageParam();
 		}
   	  });
}

/**
 *重新执行
 */
function reExecute(id,zt){
	var obj = {};
	var paramsMap = {};
	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要重新执行吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/produce/reservation/item/updateReExecute",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage("重新执成功!");
				decodePageParam();
			}
		});
	}});
}

//完成
function shutDown(id,e,isEdit){
	var bldh=$(e).attr("bldh"); //保留单号
	var gbyy=$(e).attr("gbyy"); //关闭原因
	var gzz=$(e).attr("gzz"); 	//工作者id
	var gzrq=$(e).attr("gzrq");	//工作日期
	var jcz=$(e).attr("jcz");	//检查者
	var jcrq=$(e).attr("jcrq");	//检查日期
	var zt=$(e).attr("zt"); 	//状态
	ShutDownModal.show({
		 chinaHead:'保留单号',//单号中文名称
		 englishHead:'Evalua No.',//单号英文名称
		 edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		 djid:id,//单据id
		 jsdh:bldh,//指定结束单号
		 jsr:gzz,//工作者
		 jssj:gzrq,//工作日期
		 jsrz:jcz,//检查者
		 jssjz:jcrq,//检查日期
		 jsyy:gbyy,//纠正措施及说明
		 zt:zt,//状态
		 bbType:2,//1.故障保留，2.项目保留
		 callback: function(data){//回调函数
			 if(null != data && data != ''){
				 
				 var paramsMap = {};
				 paramsMap.currentZt = zt; //当前状态
				 data.paramsMap = paramsMap;
				 
				 data.id=id; //id
				 gbSubjectFrom(data);//指定结束提交
			 }
		 }
	 });
}
//完成
function gbSubjectFrom(obj){
	 startWait($("#ShutDownModal"));
   	 AjaxUtil.ajax({
 		url:basePath+"/produce/reservation/item/endModal",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		modal:$("#ShutDownModal"),
 		success:function(data){
 			finishWait($("#ShutDownModal"));
 			AlertUtil.showMessage('完成成功!');
 			$("#ShutDownModal").modal("hide");
 			decodePageParam();
 			
 		}
   	  });
}
//指定结束
function zdjs(id,e,isEdit){
	var bldh=$(e).attr("bldh"); //保留单号
	var gbyy=$(e).attr("gbyy"); //完成原因
	var jsr=$(e).attr("gbrid");	//完成人
	var jssj=$(e).attr("gbczsj");//完成日期
	var zt=$(e).attr("zt"); 	//状态
	ConfirmModal.show({
 		chinaHead:'保留单号',//单号中文名称
 		englishHead:'Evalua No.',//单号英文名称
 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
 		jsdh:bldh,//保留单号
 		jsr:jsr,//完成人
 		jssj:jssj,//完成时间
 		jsyy:gbyy,//完成原因
 		zt:zt,//状态
 		callback: function(data){//回调函数
 			if(null != data && data != ''){
 				
 				var paramsMap = {};
 				paramsMap.currentZt = zt; //当前状态
 				data.paramsMap = paramsMap;
 				
 				data.id=id; //id
 				gConfirm(data);//指定结束提交
 			}
 		}
 	});
}
//指定结束
function gConfirm(obj){
	 startWait($("#ConfirmModal"));
   	 AjaxUtil.ajax({
 		url:basePath+"/produce/reservation/item/gConfirm",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		modal:$("#ConfirmModal"),
 		success:function(data){
 			finishWait($("#ConfirmModal"));
 			AlertUtil.showMessage('指定结束成功!');
 			$("#ConfirmModal").modal("hide");
 			decodePageParam();
 		}
   	  });
}


/**
 *查询技术评估单
 */
function findView(id){
	window.open(basePath+"/produce/reservation/item/find/"+id);
}

/**
 * 点击附件展开附件列表
 */
function init(){
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#work_card_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
	
 }
/**
 *加载附件列表 
 */
function getHistoryAttachmentList(obj){
	var jsonData = [{mainid : $(obj).attr('qtid'), type : '其它附件'},
	   	         	{mainid : $(obj).attr('jlfjid'), type : '结论附件'}
		   	       ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}

/**
 *熏染预警颜色 
 */
function getWarningColor(bgcolor,syts){
	if (yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色		 
		return "<td  class='text-center fixed-column' title='"+yjtsJb1+"天&lt;剩余&lt;="+yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
	}
	if (Number(syts) <= yjtsJb1) {
		bgcolor = warningColor.level1;// 红色
		return "<td  class='text-center fixed-column' title='剩余&lt;="+yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
	}
	return "<td  class='text-center fixed-column'></td>";
}	

$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
	$(this).next().focus();
});
$('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',
	includeSelectAllOption : true
}); 

/**
 * 字段排序
 */
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

/**
 * 搜索条件显示与隐藏 
 */
function openOrHide(){
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
