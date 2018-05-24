var maxBb = 0.0;//最大版本
var fixedcheckitemData = [];
var wxfalist = [];

$(document).ready(function(){
	Navigation(menuCode,"","");
	$("#list").append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
	initMatenanceList();
	decodePageParam();
	refreshPermission();
	//初始化日志功能
	logModal.init({code:'B_G_012'});
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
	params.wxfabh = $.trim($("#wxfabh_search").val());
	params.bb = $.trim($("#bb_search").val());
	params.pxh = $.trim($("#pxh_search").val());
	params.sjSxrq = $.trim($("#sjSxrq_search").val());
	params.zdrq = $.trim($("#zdrq_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.spzt = $.trim($("#spzt_search").val());
	params.yxx = $.trim($("#yxx_search").val());
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
		$("#pxh_search").val(params.pxh);
		$("#sjSxrq_search").val(params.sjSxrq);
		$("#zdrq_search").val(params.zdrq);
		$("#dprtcode_search").val(params.dprtcode);
		setOrganization();
		$("#wxfabh_search").val(params.wxfabh);
		changeBh();
		$("#bb_search").val(params.bb);
		$("#spzt_search").val(params.spzt);
		$("#yxx_search").val(params.yxx);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		changeOrganization();
		goPage(1,"auto","desc");
	}
}


	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 fixedcheckitemData = [];
		 var searchParam = gatherSearchParam();
		 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		 searchParam.pagination = pagination;
		 if(id != ""){
			 searchParam.id = id;
			 id = "";
		 }
		 if($.trim($("#wxfabh_search").val()) == null || $.trim($("#wxfabh_search").val()) == ''){
			 AlertUtil.showMessage('请先添加维修方案!');
			 $("#list").empty();
			 $("#list").append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
			 return false;
		 }
		 startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/project/fixedcheckitem/queryAllPageList",
			 type: "post",
			 contentType:"application/json;charset=utf-8",
			 dataType:"json",
			 data:JSON.stringify(searchParam),
			 success:function(data){
				 finishWait();
				 if(data.total >0){
					 fixedcheckitemData = data.rows;
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
					 signByKeyword($("#keyword_search").val(),[4,5,6,9,12],"#list tr td");
				 } else {
					$("#list").empty();	
				  	$("#pagination").empty();
				  	$("#list").append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
				 }
				 new Sticky({tableId:'fix_main_table'});
			 }
		 }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.wxfabh = $.trim($("#wxfabh_search").val());
		 searchParam.bb = $.trim($("#bb_search").val());
		 searchParam.pxh = $.trim($("#pxh_search").val());
		 var sjSxrq = $.trim($("#sjSxrq_search").val());
		 var zdrq = $.trim($("#zdrq_search").val());
		 var zdrrealname = $.trim($("#zdr_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val());
		 var spzt = $.trim($("#spzt_search").val());
		 var yxx = $.trim($("#yxx_search").val());
		 if(null != sjSxrq && "" != sjSxrq){
			 var sjSxrqBegin = sjSxrq.substring(0,10)+" 00:00:00";
			 var sjSxrqEnd = sjSxrq.substring(13,23)+" 23:59:59";
			 paramsMap.sjSxrqBegin = sjSxrqBegin;
			 paramsMap.sjSxrqEnd = sjSxrqEnd;
		 }
		 if(null != zdrq && "" != zdrq){
			 var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
			 var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
			 paramsMap.zdrqBegin = zdrqBegin;
			 paramsMap.zdrqEnd = zdrqEnd;
		 }
		 if('' != dprtcode){
			 searchParam.dprtcode = dprtcode;
		 }
		 if('' != zdrrealname){
			 searchParam.zdrrealname = zdrrealname;
		 }
		 if('' != spzt){
			 searchParam.spzt = spzt;
		 }
		 if('' != yxx){
			 searchParam.yxx = yxx;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var yxx = '';
		   var htmlContent = '';
		   var bb = $.trim($("#bb_search").val());
		   $("#batchReview").hide();
		   $("#batchApprove").hide();
		   if(maxBb == bb){
			   if(checkBtnPermissions('project:fixedcheckitem:main:05')){
				   $("#batchReview").show();
			   }
			   if(checkBtnPermissions('project:fixedcheckitem:main:06')){
				   $("#batchApprove").show();
			   }
		   }
		   $.each(list,function(index,row){
			   
			   
			   if(maxBb != bb){
				   if (index % 2 == 0) {
					   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
				   } else {
					   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" >";
				   }
				   $(".viewCol").removeClass("fixed-column");
				   htmlContent += "<td></td>";
				   htmlContent += "<td></td>";
			   }else{
				   if (index % 2 == 0) {
					   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='clickRow("+index+",this)'>";
				   } else {
					   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='clickRow("+index+",this)'>";
				   }
				   $(".viewCol").addClass("fixed-column");
				   htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input type='checkbox' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" /></td>";
				   
				   htmlContent += "<td class='fixed-column text-center'>";
				
				   if(row.zt == 1){
					   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:fixedcheckitem:main:02' onClick=\"edit('"+ row.id + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
				   }
				   if(row.zt == 3 && row.bb < maxBb){
					   htmlContent += "<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project:fixedcheckitem:main:03' onClick=\"modify('"+ row.id + "')\" title='改版 Modify'></i>&nbsp;&nbsp;";
				   }
				   
				   if(row.zt == 1 && row.spzt == 1){
					   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:fixedcheckitem:main:05' onClick=\"audit('"+ row.id + "')\" title='审核 Review'></i>&nbsp;&nbsp;";
				   }
				   
				   if(row.zt == 1 && row.spzt == 2){
					   htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:fixedcheckitem:main:06' onClick=\"approve('"+ row.id + "')\" title='批准 Approved'></i>&nbsp;&nbsp;";
				   }
				   
				   if(row.zt == 1){
					   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:fixedcheckitem:main:04' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>&nbsp;&nbsp;";
				   }
				   htmlContent += "</td>";
			   }
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxh)+"' class='text-center'>"+StringUtil.escapeStr(row.pxh)+"</td>";
			   htmlContent += "<td class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.djbh)+"</a>";
			   htmlContent += "</td>";  
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
			   htmlContent += "<td>"+DicAndEnumUtil.getEnumName('approveStatusEnum',row.spzt)+"</td>";
			   
			   if(row.yxx == 1){
				   yxx = '有效';	
			   }else{
				   yxx = '无效';
			   }
			   htmlContent += "<td class='text-center'>"+yxx+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bb)+"' class='text-right'>"+StringUtil.escapeStr(row.bb)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.jktj)+"' class='text-left'>"+StringUtil.escapeStr(row.jktj)+"</td>";
			   htmlContent += "<td class='text-center'>"+indexOfTime(row.sxsj)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.user.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.user.username)+" "+StringUtil.escapeStr(row.user.realname)+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.zdsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
function clickRow(index,this_){
	var $checkbox1 = $("#list :checkbox[name='checkrow']:eq("+index+")");
	var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
	var checked = $checkbox1.is(":checked");
	$checkbox1.attr("checked", !checked);
	$checkbox2.attr("checked", !checked);
}

function checkRow(index,this_){
	var flag = $(this_).is(":checked");
	if(flag){
		$(this_).removeAttr("checked");
	}else{
		$(this_).attr("checked",true);
	}
}

//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}

//改变组织机构时改变维修方案
function changeOrganization(){
	setOrganization();
	changeBh(function(){
		goPage(1,"auto","desc");
	});
}
//初始化维修方案下拉框
function initMatenanceList(){
	// 提交数据
 	AjaxUtil.ajax({
 		url:basePath+"/project/maintenance/selectByDprtcodeList",
 		type:"post",
 		async:false,
 		data:{},
 		dataType:"json",
 		success:function(data){
 			if(data != null && data.length > 0){
 				wxfalist = data;
 			}
 		}
 	});
}

//维修方案编号改变时,重新加载版本信息
function changeBhLoad(){
	changeBh(function(){
		goPage(1,"auto","desc");
	});
}

//改变组织机构时改变维修方案
function setOrganization(){
	var dprtcode = $.trim($("#dprtcode_search").val());
	var wxfabhOption = '';
	if(wxfalist != null && wxfalist.length >0){
		$.each(wxfalist,function(i,wxfa){
			if(dprtcode == wxfa.dprtcode){
				wxfabhOption += "<option value='"+StringUtil.escapeStr(wxfa.wxfabh)+"' >"+StringUtil.escapeStr(wxfa.zwms)+"</option>";
			}
		});
	}
	$("#wxfabh_search").html(wxfabhOption);
}
	 
//维修方案编号改变时,重新加载版本信息
function changeBh(obj){
 	var wxfabh = $.trim($("#wxfabh_search").val());
 	var dprtcode = $.trim($("#dprtcode_search").val());
 	if(null != rwxfabh && '' != rwxfabh){
 		wxfabh = rwxfabh;
 		rwxfabh = "";
 		$("#wxfabh_search").val(wxfabh);
 	}
 	// 提交数据
 	AjaxUtil.ajax({
 		url:basePath+"/project/maintenance/queryBbListByWxfabh",
 		type:"post",
 		async:false,
 		data:{'wxfabh' : wxfabh,'dprtcode' : dprtcode},
 		dataType:"json",
 		success:function(data){
 			buildBb(data);	
 			if(typeof(obj)=="function"){
				obj(); 
			}
 		}
 	});
}
//加载版本信息
function buildBb(bbList){

	maxBb = 0.0;
 	$("#bb_search").empty();
 	
 	var option = '';
 	for(var i = 0 ; i < bbList.length ; i++){
 		var bb = bbList[i];
 		option += '<option value="'+bb+'">'+bb+'</option>';
 		if(maxBb < bb){
 			maxBb = bb;
 		}
 	}
 	$("#bb_search").append(option);
 	$("#bb_search").val(maxBb);
}

//打开调整列表对话框
function order() {
	initOrder();
	$("#alertModalOrder").modal("show");
}

function initOrder(){
	var wxfabh = $.trim($("#wxfabh_search").val());
 	var dprtcode = $.trim($("#dprtcode_search").val());
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckitem/queryOrderList",
		type:"post",
		data:{'wxfabh' : wxfabh,'dprtcode' : dprtcode},
		dataType:"json",
		success:function(data){
			initTableCol(data);
		}
	});
}

//初始化表格
function initTableCol(data){
	
	//先移除暂无数据一行
	$("#rotatable").empty();
	
	if(JSON.stringify(data) == '[]'){
		$("#rotatable").append("<tr><td  colspan='12'>暂无数据 No data.</td></tr>");
		return;
	}
	$.each(data,function(i,fixObj){
		addRow(fixObj);
	});
}

function saveOrder(){
	
	var fixedParam = [];
	var len = $("#rotatable").find("tr").length;
	if (len == 1) {
		if($("#rotatable").find("td").length ==1){
			AlertUtil.showMessage('请添加数据!');
			searchRevision();
			return false;
		}
	}

	if (len > 0){
		var flag = true;
		var regu = /^[0-9]+$/;
		$("#rotatable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var hiddenid =$("input[name='hiddenid']").eq(index).val();
			var pxh =$("input[name='pxh']").eq(index).val();
			if(pxh == null || pxh == ''){
				obj.pxh = 0;
			}
			
			if (!regu.test(pxh)) {
		        AlertUtil.showErrorMessage("排序号只能输入数字！");
		        flag = false;
		        return false;
		    }
			infos.id  = hiddenid;
			infos.pxh  = pxh;
			fixedParam.push(infos);
		});
		if(!flag){
			return false;
		}
	}
	
	startWait($("#alertModalOrder"));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckitem/order",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedParam),
		dataType:"json",
		modal:$("#alertModalOrder"),
		success:function(data){
			finishWait($("#alertModalOrder"));
			AlertUtil.showMessage('调整成功!');
			$("#alertModalOrder").modal("hide");
			searchRevision();
		}
	});

}

//打开批量审核批准对话框
function batchApproveWin(isApprovel){
	var idArr = [];
	var approvalArr = [];
	var approvalNotArr = [];
	
	var spzt = isApprovel?2:1;
	$("#list").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var fixedcheckitem = fixedcheckitemData[index];
		if(fixedcheckitem.zt == 1 && fixedcheckitem.spzt == spzt){
			idArr.push(fixedcheckitem.id);
			approvalArr.push(fixedcheckitem.djbh);
   	 	}else{
   	 		approvalNotArr.push(fixedcheckitem.djbh);
   	 	}
	});
	
	BatchApprovelModal.show({
		approvalArr:approvalArr,//审核可操作的数据
		approvalNotArr:approvalNotArr,//审核不可操作的数据
		isApprovel:isApprovel,//判断审核还是批准
		callback: function(data){//回调函数
			if(idArr.length > 0){
				batchApproval(idArr,data,isApprovel);
			}
		}
	});
}

function batchApproval(idList,data,isApprovel){
	var url = basePath+"/project/fixedcheckitem/updateBatchAudit";
	if(isApprovel){
		url = basePath+"/project/fixedcheckitem/updateBatchApprove";
	}
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		type:"post",
		data:{
			idList:idList,yj:data.yj
		},
		dataType:"json",
		success:function(message){
			finishWait();
			AlertUtil.showMessage(message);
			refreshPage();
		}
	});
}

//显示定检项目新增页面
function add(){
	
	var wxfabh = $.trim($("#wxfabh_search").val());
	var bb = $.trim($("#bb_search").val());
	var wxfamc = $.trim($("#wxfabh_search").find("option:selected").text());
	if(null == wxfabh || '' == wxfabh){
		AlertUtil.showErrorMessage('请先新增维修方案信息!');
		return false;
	}
	if(maxBb != bb){
		AlertUtil.showErrorMessage('请选择维修方案的最新版本!');
		return false;
	}
	
	var dprtId = $.trim($("#dprtId").val());
	var dprtcode = $.trim($("#dprtcode_search").val());
	
	if(dprtId != dprtcode){
		AlertUtil.showErrorMessage('请选择本组织机构维修方案!');
		return false;
	}
	
	if(checkEffectiveState(wxfabh,bb)){
		window.location = basePath+"/project/fixedcheckitem/add?wxfabh="+encodeURIComponent(wxfabh)+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam();
	}
}
	 
	 
//定检项目修改
function edit(id){
	if(checkEdit(id)){	
		var bb = $.trim($("#bb_search").val());
		window.location = basePath+"/project/fixedcheckitem/edit?id=" + id+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam();
	}
}

//定检项目改版
function modify(id){

	if(checkModify(id)){
		var bb = $.trim($("#bb_search").val());
		window.location = basePath+"/project/fixedcheckitem/modify?id=" + id+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam();
	}

}

//定检项目审核
function audit(id){

	if(checkAudit(id)){
		var bb = $.trim($("#bb_search").val());
		window.location = basePath+"/project/fixedcheckitem/audit?id=" + id+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam();
	}

}

//定检项目批准
function approve(id){

	if(checkApprove(id)){
		var bb = $.trim($("#bb_search").val());
		window.location = basePath+"/project/fixedcheckitem/approve?id=" + id+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam();
	}

}

//查看详情
function viewDetail(id){
	var bb = $.trim($("#bb_search").val());
	window.open(basePath+"/project/fixedcheckitem/view?id=" + id+"&bb="+encodeURIComponent(bb)+"&pageParam=" + encodePageParam());
}

//作废
function del(id) {
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
	
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/project/fixedcheckitem/cancel",
			type:"post",
			async: false,
			data:{
				'id' : id
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

//检查维修方案是否生效
function checkEffectiveState(wxfabh,bb){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/maintenance/checkEffectiveState",
		type:"post",
		async: false,
		data:{
			'wxfabh' : wxfabh,
				'bb' : bb,
		  'dprtcode' : $.trim($("#dprtcode_search").val())
		},
		dataType:"json",
		success:function(data){
			flag = true;
		}
	});
	return flag;
}

//检查定检项目是否可以修改
function checkEdit(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/fixedcheckitem/checkEdit",
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
	
//检查定检项目是否可以改版
function checkModify(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/fixedcheckitem/checkModify",
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

//检查定检项目是否可以审核
function checkAudit(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/fixedcheckitem/checkAudit",
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

//检查定检项目是否可以批准
function checkApprove(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/fixedcheckitem/checkApprove",
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
//向table新增一行
function addRow(obj){
	
	var tr = "";
		
		tr += "<tr>";
		
		tr += "<td>";
		tr += "<input name='pxh' type='text' value='"+StringUtil.escapeStr(obj.pxh)+"' class='form-control' placeholder='' onkeyup='clearNoNumber(this)' />";
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='djbh' type='text' value='"+StringUtil.escapeStr(obj.djbh)+"' class='form-control' readonly />";
		tr += "</td>";
		
		tr += "<td>";
		tr += "<input name='zwms' type='text' value='"+StringUtil.escapeStr(obj.zwms)+"' class='form-control' readonly />";
		tr += "</td>";
		
		tr += "</tr>";
	
	$("#rotatable").append(tr);
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

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
	
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});
		
		$("#dprtcode_search").val(dprtId);
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
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
//只能输入数字和小数
 function clearNoNum(obj)
 {
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
 }
 
//只能输入数字
function clearNoNumber(obj){
	//先把非数字的都替换掉，除了数字
	obj.value = obj.value.replace(/[^\d]/g,"");
	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		obj.value = 0;
	}
}
		 
