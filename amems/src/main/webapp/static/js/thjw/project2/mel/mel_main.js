
var pagination={};
var id = '';
var dprtcode = $("#dprtId").val();
var mainData = [];
$(document).ready(function(){
	
	Navigation(menuCode, '', '', 'GC-8-1', '刘兵', '2017-08-01', '刘兵', '2017-08-01');
	jxChange();
	goPage(1,"auto","desc");
	refreshPermission();
	//初始化日志功能
	logModal.init({code:'B_G_019'});	
	
    //执行待办
    if(todo_ywid){
    	if(todo_jd == 1 || todo_jd == 5 || todo_jd == 6){
    		openWinEdit(todo_ywid,todo_dprtcode);
    	}else if(todo_jd == 2){
    		audit(todo_ywid, todo_dprtcode);
    	}else if(todo_jd == 3){
    		reply(todo_ywid, todo_dprtcode);
    	}else{
    		add() ;
    	}
    	todo_ywid = null;
    }
    else if(todo_lyid) {
		add() ;
		//默认机型
		if(todo_fjjx) {
			todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
			if(todo_fjjx){
				$("#jx").val(todo_fjjx);
			}
			todo_fjjx = null;
		}
	}
	
});

function jxChange(){
	initPlaneModel($("#dprtcode_search").val());
}
function initPlaneModel(dprt){
	var this_ = this;
	var data = acAndTypeUtil.getACTypeList({DPRTCODE:dprt});
 	var option = '<option value="" selected="selected">显示全部All</option>';
 	if(data.length >0){
		$.each(data,function(i,obj){
			option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
		});
  	 }
 	$("#jx_search").empty();
 	$("#jx_search").append(option);
}

    /**
     * 查看评估单号操作
     */
	function viewPgdh(pgdid,dprtcode) {
		if(typeof(pgdid) == undefined || pgdid == ''){
			$.messager.alert("提示", "没有可查看的评估单号","error");
		}
		else{
			window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(pgdid)+"/"+$.trim(dprtcode)+"");
		}
	}
	
	function view(id,dprtcode) {
		window.open(basePath+"/project2/mel/view?id="+ id);
		
	}
	
	/**
	 * 作废
	 * @param id
	 */
	function deleteMel(id) {
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
			
				 AjaxUtil.ajax({
					 type:"post",
					 url:basePath+"/project2/mel/cancel",
					 dataType:"json",
					 data:{'id':id},
					 success:function(data) {
						AlertUtil.showMessage('删除成功!');
						refreshPage();
					 }
				 });
				 
		 }});
		
	}
	
	//指定结束
	function shutDown(id,this_,isEdit){
		var id = $(this_).attr("djid");
		var sqdh = $(this_).attr("sqdh");
		var zdjsyy = $(this_).attr("zdjsyy");
		var zdjsr = $(this_).attr("zdjsr");
		var zdjsrq = $(this_).attr("zdjsrq");
		var zt = $(this_).attr("zt");
		AssignEndModal.show({
			chinaHead:'文件编号',//单号中文名称
			englishHead:'File No.',//单号英文名称
			edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:sqdh,//指定结束单号
			jsr:zdjsr,//指定结束人
			jssj:zdjsrq,//指定结束时间
			jsyy:zdjsyy,//指定结束原因
			zt:zt,
			callback: function(data){//回调函数
				if(null != data && data != ''){
					var obj = {
							id : id,
							zdjsrid : $("#userId").val(),
							zdjsyy : data.gbyy,
							zt: data.zt
					};
					sbShutDown(obj);
				}
			}
		});
	}

	//确认指定结束
	function sbShutDown(plan) {
		
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/project2/mel/shutDown",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(plan),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('关闭成功!');
				$("#AssignEndModal").modal("hide");
				id = plan.id;
				refreshPage();
			}
		});
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
		startWait();
		AjaxUtil.ajax({
			 url:basePath+"/project2/mel/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   mainData = data.rows;
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
				   signByKeyword($("#keyword_search").val(),[3,6,13,14],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"19\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'main_list_table'});
	      }
	    }); 
		
	 }
    
//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	var paramsMap = {};
	var keyword = $.trim($("#keyword_search").val());
	var zt = $.trim($("#zt_search").val());
	searchParam.keyword = keyword;
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	searchParam.zt = zt;
	searchParam.jx = $("#jx_search").val();
	paramsMap.userId = userId;
	paramsMap.userType = userType;
	searchParam.paramsMap = paramsMap;
	return searchParam;
}
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent = htmlContent + "<tr onclick=SelectUtil.clickRow("+index+",'list','mel_row')>";
				
				htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input type='checkbox' name='mel_row' onclick=SelectUtil.clickRow("+index+",'list','mel_row') index="+index+" /></td>";
			   
			   htmlContent += "<td class='text-center fixed-column'>";
			      
			   if(row.zt==1 || row.zt==5 || row.zt==6 ){
				   htmlContent += "<i class='icon-edit  color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:02' onClick=\"openWinEdit('"+ row.id + "','"+row.dprtcode+"')\" title='修改 Edit'>" +
				   		"</i>&nbsp;&nbsp;";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:04' onClick=\"deleteMel('"+ row.id + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==2){
				   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:05' onClick=\"audit('"+ row.id + "','"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==3){
				   htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:06' onClick=\"reply('"+ row.id + "','"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt == 2||row.zt == 3||row.zt == 4){
				   htmlContent += "<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:07' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"' zt='"+row.zt+"' zdjsyy='' zdjsr='' zdjsrq='' " +
				   		"onClick=\"shutDown('"+row.id+"',this,true)\" title='关闭 Close'></i>&nbsp;&nbsp;";
			   }
			   
			   htmlContent += "</td>";
			   
			   htmlContent += "<td  class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"' ><a href=\"javascript:view('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"</td>";  
			   htmlContent += "<td title='"+AffectedUtil.formatTitle(row.orderSourceList)+"' class='text-left'>" + AffectedUtil.formatContent(row.orderSourceList) + "</td>";
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.jx)+"'>"+StringUtil.escapeStr(row.jx)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.xmh)+"'>"+StringUtil.escapeStr(row.xmh)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.ssbf)+"'>"+StringUtil.escapeStr(row.ssbf)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.sszj)+"'>"+StringUtil.escapeStr(row.sszj)+"</td>";
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(row.zy)+"</td>";  
			   var xgqBb = "";
			   var xghBb = "";
			   if(row.xgqBb){
				   xgqBb = "R"+row.xgqBb
			   }
			   if(row.xghBb){
				   xghBb = "R"+row.xghBb
			   }
			   
			   htmlContent += "<td  class='text-left' title='"+xgqBb+"'>"+xgqBb+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+xghBb+"'>"+xghBb+"</td>";  
			   var xgbj = '';
			   if(row.xgbj != null){
				   xgbj = row.xgbj.replace("A","新增").replace("R","修订").replace("D","删除");
			   }
			   htmlContent += "<td  class='text-left'>"+xgbj+"</td>";
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.xdnr)+"'>"+StringUtil.escapeStr(row.xdnr)+"</td>";
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.xgyy)+"'>"+StringUtil.escapeStr(row.xgyy)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.xdqx)+"'>"+StringUtil.escapeStr(row.xdqx)+"</td>";  
			   htmlContent += "<td class='text-left'>";
			   if(row.zt == 9 || row.zt == 10 ){
				   var zdjsr = row.jsr?StringUtil.escapeStr(row.jsr.displayName):'';
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' zt='"+row.zt+"' sqdh='"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+formatUndefine(row.zdjsrq)+"' onclick=\"shutDown('"+row.id+"',this,false)\">"+DicAndEnumUtil.getEnumName('melStatusEnum',row.zt)+"</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('melStatusEnum',row.zt);
			   }
			   htmlContent += "</td>";
			   
			   if(undefined != row.zdr){
				   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }
			   else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
				   
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			   
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.dprtname)+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();			//权限初始化
	 }
	 
//新增
function add(){
	dprtcode = userJgdm;
 	var obj = {
 	};
 	 
	mel_edit_alert_Modal.show({
 		modalHeadChina : '新增',
		modalHeadEnglish : 'Add',
		type:1,//1:新增,2:修改,3:审核,4:批准
		viewObj:obj,
		blOption:true,
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
		
		}
	});
}

//打开修改弹出框
function openWinEdit(id,dprtcode_){
	dprtcode = dprtcode_;
 	selectById(id,function(obj){
 		mel_edit_alert_Modal.show({
 	 		modalHeadChina : '编辑',
 			modalHeadEnglish : 'Edit',
 			type:2,//1:新增,2:修改,3:审核,4:批准
 			viewObj:obj,
 			blOption:true,
 			dprtcode:dprtcode,//机构代码
 			callback : function(data) {// 回调函数
 				
 			}
 		});
 	});
}

//打开审核弹出框
function audit(id,dprtcode_){
	dprtcode = dprtcode_;
 	selectById(id,function(obj){
 		if(obj.zt != 2){
 			 AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");
 			return false; 
 		}
 		
 		mel_edit_alert_Modal.show({
 	 		modalHeadChina : '审核',
 			modalHeadEnglish : 'Audit',
 			type:3,//1:新增,2:修改,3:审核,4:批准
 			viewObj:obj,
 			blOption:false,
 			dprtcode:dprtcode,//机构代码
 			callback : function(data) {// 回调函数
 				
 			}
 		});
 	});
 	
 	$("#save").hide();
 	$("#submit").hide();
 	$("#approve").hide();
 	$("#rejected").hide();
 	$("#audited").show();
 	$("#reject").show();
}

//打开批准弹出框
function reply(id,dprtcode_){
	dprtcode = dprtcode_;
 	selectById(id,function(obj){
		if(obj.zt != 3){
			 AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作！");	
 			return false; 
 		}
 		mel_edit_alert_Modal.show({
 	 		modalHeadChina : '批准',
 			modalHeadEnglish : 'Approve',
 			type:4,//1:新增,2:修改,3:审核,4:批准
 			viewObj:obj,
 			blOption:false,
 			dprtcode:dprtcode,//机构代码
 			callback : function(data) {// 回调函数
 				
 			}
 		});
 	});
 	
	$("#save").hide();
 	$("#submit").hide();
 	$("#approve").show();
 	$("#rejected").show();
 	$("#audited").hide();
 	$("#reject").hide();
}

//通过id获取数据
function selectById(id,obj){
 	AjaxUtil.ajax({
 		async: false,
 		url:basePath+"/project2/mel/selectById",
 		type:"post",
 		data:{id:id},
 		dataType:"json",
 		success:function(data){
 			if(data != null){
 				if(typeof(obj)=="function"){
					obj(data); 
				}
 			}
 		}
 	});
}

AffectedUtil = {
		flag : true,
		formatTitle : function(list){
			var htmlContent = '';
			if(list != null && list != "" && list.length > 0 ){
				$.each(list,function(i,obj){
					htmlContent += StringUtil.escapeStr(obj.pgdh);
					if(i != list.length - 1){
						htmlContent += ",";
					}
				});
			}
			return htmlContent;
		},
		formatContent : function(strs){
			var htmlContent = '';
			if(strs != null && strs.length > 0){
				$.each(strs,function(i,obj){
					if(i == 1){
						htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=CollapseOrExpandUtil.collapseOrExpand(this,'main_list_table')></i><div class='melDisplayFile' style='display:none'>";
					}
					htmlContent += "<a href='javascript:void(0);' onclick=AffectedUtil.view('"+obj.pgdid+"') >"+StringUtil.escapeStr(obj.pgdh)+" R"+StringUtil.escapeStr(obj.bb)+"</a>";
					if(i != 0 && i != strs.length - 1){
						htmlContent += "<br/>";
					}
					if(i == strs.length - 1){
						htmlContent += "</div>";
					}
				});
			}
			return htmlContent;
		},
		view : function(id){
			window.open(basePath+"/project2/assessment/view?id="+id);
		}
	}
	 
/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序规则 排序字段,
 * @param pageNumber
 * @param sortType
 * @param sortField
 */ 
function goPage(pageNumber,sortType,sortField){
	AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
}	
	 //信息检索
function searchRevision(){
	 TableUtil.resetTableSorting("main_list_table");
	 goPage(1,"auto","desc");
}
		
 //搜索条件重置
 function searchreset(){
	 
	 var drpt=$("#dprtId").val();
	 $("#keyword_search").val("");
	 
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 })
	 
	  $("#divSearch").find("select").each(function(){
		 $(this).val("");
	 })
	 $("#dprtcode_search").val(drpt);
	 jxChange();
 }
	 
//搜索条件显示与隐藏 
function more() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-chevron-down");
		$("#icon").addClass("icon-chevron-up");
	} else {

		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-chevron-up");
		$("#icon").addClass("icon-chevron-down");
	}
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
	var currentPage = $("#pagination li[class='active']").text()||1;
	goPage(currentPage,obj,orderStyle.split("_")[1]);
} 

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}

/**
 * 打开批量审核批准对话框
 */
function batchApproveWin(isApprovel){
	var idArr = [];
	var approvalArr = [];
	var approvalNotArr = [];
	
	var zt = isApprovel?3:2;
	$("#list").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var mel = mainData[index];
		if(mel.zt == zt){
			idArr.push(mel.id);
			approvalArr.push(mel.ggdbh);
   	 	}else{
   	 		approvalNotArr.push(mel.ggdbh);
   	 	}
	});
	if(approvalArr.length == 0 && approvalNotArr.length == 0){
		AlertUtil.showMessage("请选中数据后再进行操作！");
	}else{
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
}
/**
 * 执行批量审核批准
 */
function batchApproval(idList,data,isApprovel){
	var this_ = this;
	var url = basePath+"/project2/mel/updateBatchAudit";
	if(isApprovel){
		url = basePath+"/project2/mel/updateBatchApprove";
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
			this_.refreshPage();
		}
	});
}



