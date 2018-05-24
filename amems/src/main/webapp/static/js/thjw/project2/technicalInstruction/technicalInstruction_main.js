var choseAllIds=[];
var instructionitemData = [];
var pagination={};
$(document).ready(function(){
		 validation();
	 	 decodePageParam();
		 refreshPermission();
		 $('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
		//初始化日志功能
		logModal.init({code:'B_G_004_New'});
		
	    //执行待办
	    if(todo_ywid){
	    	if(todo_jd == 1 || todo_jd == 5 || todo_jd == 6){
	    		edit(todo_ywid, '', todo_dprtcode);
	    	}else if(todo_jd == 2){
	    		review(todo_ywid, 2);
	    	}else if(todo_jd == 3){
	    		approve(todo_ywid, 3);
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
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		instructionitemData = [];
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.zt=searchParam.zt;
		obj.jszt=searchParam.jszt;
		obj.dprtcode=searchParam.dprtcode;
		obj.paramsMap = {
			bb : $.trim($("#bb_search").val())
		}
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/project2/instruction/queryInstructionList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   instructionitemData=data.rows;
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
				   });
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,6,11]);
			   } else {
				  $("#instructionlist").empty();
				  $("#pagination").empty();
				  $("#instructionlist").append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
 //将搜索条件封装 
 function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
	 searchParam.zt = $.trim($("#zt_search").val());
	 searchParam.jszt = $.trim($("#jszt_search").val());
	 searchParam.dprtcode = $.trim($("#zzjg").val());
	 searchParam.zdrid = $.trim($("#zdrid").val());
	 return searchParam;
 }
 function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());
	 params.zt=$.trim($("#zt_search").val());
	 params.zzjg=$.trim($("#zzjg").val());
	 params.jszt=$.trim($("#jszt_search").val());
	 pageParam.params=params;
	 pageParam.pagination=pagination;
	 return Base64.encode(JSON.stringify(pageParam));
 }
 
 function appendContentHtml(list){
	   var htmlContent = '';
	   choseAllIds=[];
	   $.each(list,function(index,row){
		   choseAllIds.push(row.id);
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" onclick='clickRow("+index+",this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" onclick='clickRow("+index+",this)' >";
		   }
		   htmlContent += "<td class='text-center fixed-column'><input type='checkbox' index='"+index+"' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" /></td>";
		   
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		   if((row.zt==1 || row.zt==5 || row.zt==6) && !row.bBbid){
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:instruction:main:02' onClick=\"edit('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:instruction:main:08' onClick=\"invalid('"
					+ row.id + "','" + row.zt + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
		   }
		   if (row.zt == 2 && !row.bBbid) {
				htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project2:instruction:main:03' onClick=\"review('"
						+ row.id + "','" + row.zt + "')\" title='审核 Review'></i>&nbsp;&nbsp;";
		   }
		   if (row.zt == 3 && !row.bBbid) {
				htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project2:instruction:main:04' onClick=\"approve('"
						+ row.id + "','" + row.zt + "')\" title='批准 Approve'></i>&nbsp;&nbsp;";
		   }
		   if(row.zt == 7 && !row.bBbid) {
				htmlContent += "<i class='icon-pencil color-blue cursor-pointer '  onClick=\"revision('"
				+ row.id + "')\" title='改版 Revision'></i>&nbsp;&nbsp;";
		   }
		   if((row.zt == 2 || row.zt == 3 || row.zt == 4 || row.zt == 7) && !row.bBbid){
				htmlContent += "<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='project2:instruction:main:07'  jszlh='"+row.jszlh+"' zdjsyy=''  zt='"+row.zt+"'  onClick=\"shutDown('"+row.id+"', this , true)\" title='关闭 Close'></i>&nbsp;&nbsp;";
		   }
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td class='text-center fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.jszlh))+"'><a href=\"javascript:viewMainInstruction('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.jszlh))+"</a></td>";   
		   htmlContent = htmlContent + "<td class='text-center ' title='R"+StringUtil.escapeStr(formatUndefine(row.bb))+"'>R"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";   
		   htmlContent += "<td class='text-left'>"+ getPgds(row.isList, row.id) + "</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zhut)+"' class='text-left'>"+StringUtil.escapeStr(row.zhut)+"</td>";
		   if(row.zt==9 || row.zt ==10 ){
			   var zdjsr = row.gbr_user?row.gbr_user.displayName:'';
			   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
			   "jszlh='"+StringUtil.escapeStr(row.jszlh)+"' "+
			   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
			   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
			   "zt='"+StringUtil.escapeStr(row.zt)+"' "+
			   "zdjsrq='"+StringUtil.escapeStr(row.zdjsrq)+"' "+
			   "onclick=\"shutDown('"+row.id+"', this , false)\">"+formatUndefine(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum', row.zt))+"</a></td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum', row.zt))+"</td>";    
			   }
		   if(row.zdr_user!=null && row.zdr_user!=""){
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jsr_user.username)+" "+StringUtil.escapeStr(row.jsr_user.realname)+"'>"+StringUtil.escapeStr(row.jsr_user.username)+" "+StringUtil.escapeStr(row.jsr_user.realname)+"</td>"; 
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>"; 
		   }
		   if(row.jszt==0){
			   htmlContent = htmlContent + "<td class='text-center'>未接收</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>已接收</td>";  
		   }
		   htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
			if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
				htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr_user.username)+" "+StringUtil.escapeStr(row.zdr_user.realname)+"'>"+StringUtil.escapeStr(row.zdr_user.username)+" "+StringUtil.escapeStr(row.zdr_user.realname)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";  
		   htmlContent = htmlContent + "</tr>";
		    
	   });
	   $("#instructionlist").empty();
	   $("#instructionlist").html(htmlContent);
	   initWebuiPopover();
	   refreshPermission();
 }
 
 function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#bulletin_list_table_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	   	    ];
	 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
	
//拼接评估单
 function getPgds(list, id) {
 	var html = "";
 	if (list != null) {
 		for (var i = 0; i < list.length; i++) {
 			if (i == 1) {
 				html = html + "　<i class='icon-caret-down' id='" + id
 						+ "icon' onclick=switchPgd('" + id + "')></i><div id='"
 						+ id + "' style='display:none'>";
 			}
 			if (i == 0) {
 				html += "<a href='javascript:void(0);' title='"
 						+ StringUtil.escapeStr(list[i].pgdh)
 						+ "' onclick=\"toViewPgd('" + list[i].pgdid
 						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+StringUtil.escapeStr(list[i].bb)
 						+ "</a>";
 			}
 			if (i != 0) {
 				html += "<a href='javascript:void(0);' title='"
 						+ StringUtil.escapeStr(list[i].pgdh)
 						+ "' onclick=\"toViewPgd('" + list[i].pgdid
 						+ "')\" >" + (StringUtil.escapeStr(list[i].pgdh))+" R"+StringUtil.escapeStr(list[i].bb)
 						+ "</a>";
 				html += "<br>";

 			}
 			if (i != 0 && i == list.length - 1) {
 				html += "</div>";
 			}
 		}
 	}
 	return html;
 }
 // 单行展开评估单
 function switchPgd(id) {
 	if ($("#" + id).is(":hidden")) {
 		$("#" + id).fadeIn(500);
 		$("#" + id + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
 	} else {
 		$("#" + id).hide();
 		$("#" + id + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
 	}
 	new Sticky({
 		tableId : 'quality_check_list_table'
 	});
 }
 // 列展开评估单
 function vieworhidePgd() {
 	new Sticky({
 		tableId : 'quality_check_list_table'
 	});
 	var obj = $("th[name='pgd']");
 	var flag = obj.hasClass("downward");
 	if (flag) {
 		obj.removeClass("downward").addClass("upward");
 	} else {
 		obj.removeClass("upward").addClass("downward");
 	}
 	for (var i = 0; i < choseAllIds.length; i++) {
 		if (flag) {
 			$("#" + choseAllIds[i]).fadeIn(500);
 			$("#" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
 		} else {
 			$("#" + choseAllIds[i]).hide();
 			$("#" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
 		}
 	}
 }

 function toViewPgd(id){
		window.open(basePath+"/project2/assessment/view?id="+id);
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
	TableUtil.resetTableSorting("quality_check_list_table");
	goPage(1,"auto","desc");
}
	
 //搜索条件重置
 function searchreset(){
	 var zzjgid=$('#zzjgid').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#zzjg").val(zzjgid);
 }
 
 
 
 //查看工单详细信息
 function detailPage(obj,rid){
	 obj.stopPropagation(); 
	 window.location =basePath+"/main/revision/"+$.trim(rid)+"/revisionaction/detail?t="+new Date().getTime();
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
	 
//作废
function invalid(id,zt){
	var param = {};
	param.id = id;
	var paramsMap = {};
	paramsMap.currentZt = zt;
	param.paramsMap = paramsMap;
	AlertUtil.showConfirmMessage("确定要删除吗？", {
		callback : function() {
			AjaxUtil.ajax({
				url : basePath + "/project2/instruction/delete",
				type : "post",
				async : false,
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(param),
				dataType : "json",
				success : function(data) {
					pageParam=encodePageParam();
					AlertUtil.showMessage('删除成功!', decodePageParam());
					refreshPermission();
				}
			});
		}
	});
}

/**打开关闭弹出框*/
function shutDown(id,e,isEdit){
	var pgdh=$(e).attr("jszlh"); //评估单号
	var gbyy=$(e).attr("zdjsyy"); //关闭原因
	var gbrq=$(e).attr("zdjsrq"); //关闭日期
	var gbrid=$(e).attr("zdjsr");//关闭人
	var zt=$(e).attr("zt"); //状态
 	AssignEndModal.show({
 		chinaHead:'技术指令编号',//单号中文名称
 		englishHead:'TO No.',//单号英文名称
 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
 		jsdh:pgdh,//指定结束单号
 		jsr:gbrid,//指定结束人
 		jssj:gbrq,//指定结束时间
 		jsyy:gbyy,//指定结束原因
 		zt:zt,//指定结束原因
 		callback: function(data){//回调函数			
			var message = '关闭成功!';
			var param={};
			var paramsMap={};
			paramsMap.currentZt = zt;
			param.paramsMap=paramsMap;
			param.id = id;
			param.zt=data.zt;
			param.zdjsyy=data.gbyy;
			var url = basePath+"/project2/instruction/doClose";
			tip("确定要关闭吗？",param,url,message,'AssignEndModal');
		}
	});
}

//查看跳转
function viewMainInstruction(id){
	window.open(basePath+"/project2/instruction/view?id=" + id+"&pageParam="+encodePageParam());
}

function vieworhideWorkContentAll(){
	var obj = $("th[name='th_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<choseAllIds.length;i++){
		 if(flag){
			 $("#"+choseAllIds[i]).fadeIn(500);
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
		 }else{
			 $("#"+choseAllIds[i]).hide();
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
		 }
	 }
	 new Sticky({tableId:'quality_check_list_table'});
}

function clickRow(index,this_){
	var $checkbox1 = $("#instructionlist :checkbox[name='checkrow']:eq("+index+")");
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
//打开批量审核批准对话框
function batchApproveWin(isApprovel){
	var idArr = [];
	var approvalArr = [];
	var approvalNotArr = [];
	var zt = isApprovel?3:2;
	$("#instructionlist").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var instruction = instructionitemData[index];
		if(instruction.zt == zt ){
			idArr.push(instruction.id);
			approvalArr.push(instruction.jszlh);
   	 	}else{
   	 		approvalNotArr.push(instruction.jszlh);
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

function batchApproval(idList,data,isApprovel){
	var url = basePath+"/project2/instruction/updateBatchAudit";
	var message = '批量审核成功!';
	if(isApprovel){
		url = basePath+"/project2/instruction/updateBatchApprove";
		message = '批量批准成功!';
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
		$("#zt").val(params.zt);
		$("#zzjg").val(params.zzjg);
		$("#jszt_search").val(params.jszt);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
//新增
function add(){
	initModalData();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();	
	$("#revision").hide();
	if($("#fcrq").val()==null||$("#fcrq").val()==""){
		TimeUtil.getCurrentDate("#fcrq");                          //取得当前时间
	}
	initFjjxOption($("#zzjgid").val());// 加载飞机
	evaluation_modal.init({
		parentId : "addModal",// 第一层模态框id
		isShowed : true,// 是否显示选择评估单的操作列
		zlxl : 2,// 指令类型
		dprtcode : $("#zzjgid").val(),// 组织机构
		jx : $("#jx").val(),// 机型
	});
	introduce_process_info_class.show({ // 流程信息
		status : 1,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
	});
	WorkContentUtil.init({
		djid:'',//关联单据id
		ywlx:2,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead :true,
		dprtcode:$("#zzjgid").val()//默认登录人当前机构代码
	});
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"technicalInstruction"
	});//显示附件
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("新增技术指令");
	$("#addModal #titleEname").html("Add Technical Instruction");
	switchHistoryVersion();
	$("#addModal").modal("show");
}

//编辑
function edit(id,zt){
	initModalData();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#revision").hide();
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("修改技术指令");
	$("#addModal #titleEname").html("Edit Technical Instruction");
	fillData(param,0);
}

//审核
function review(id,zt) {
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#approve").hide();
	$("#rejected").hide();
	$("#revision").hide();
	$("#addModal #fcrname").removeClass("readonly-style");
	$("#addModal #jsrname").removeClass("readonly-style");
	$("#addModal #jsrbutton").hide();
	$("#addModal #fcrbutton").hide();
	$("#addModal #fcr_remark").hide();
	$("#addModal #fcrq_remark").hide();
	$("#addModal #jx_remark").hide();
	$("#addModal #jsr_remark").hide();
	$("#addModal #bb_remark").hide();
	$("#addModal #zhut_remark").hide();
	$("#addModal #zxsx_remark").hide();
	$("#addModal input[type='text']").attr("disabled", true);
	$("#addModal select").attr("disabled", true);
	$("#addModal textarea").attr("disabled", true);
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("审核技术指令");
	$("#addModal #titleEname").html("Review Technical Instruction");
	fillData(param,2);
}

//批准
function approve(id,zt) {
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#reject").hide();
	$("#audited").hide();
	$("#revision").hide();
	$("#addModal #fcrname").removeClass("readonly-style");
	$("#addModal #jsrname").removeClass("readonly-style");
	$("#addModal #jsrbutton").hide();
	$("#addModal #fcrbutton").hide();
	$("#addModal #fcr_remark").hide();
	$("#addModal #fcrq_remark").hide();
	$("#addModal #jx_remark").hide();
	$("#addModal #jsr_remark").hide();
	$("#addModal #bb_remark").hide();
	$("#addModal #zhut_remark").hide();
	$("#addModal #zxsx_remark").hide();
	$("#addModal input[type='text']").attr("disabled", true);
	$("#addModal select").attr("disabled", true);
	$("#addModal textarea").attr("disabled", true);
	var param = {};
	param.id = id;
	param.zt = zt;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("批准技术指令");
	$("#addModal #titleEname").html("Approve Technical Instruction");
	fillData(param,3);
}

//改版
function revision(id,zt){
	initModalData();
	$("#save").hide();
	$("#submit").hide();
	$("#audited").hide();
	$("#reject").hide();
	$("#approve").hide();
	$("#rejected").hide();
	var param = {};
	param.id = id;
	AlertUtil.hideModalFooterMessage();
	$("#addModal #titleName").html("改版技术指令");
	$("#addModal #titleEname").html("Revision Technical Instruction");
	fillData(param,7);
}

//回写数据
function fillData(param,obj) {
	AjaxUtil.ajax({
		url : basePath + "/project2/instruction/edit",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		async : false,
		success : function(data) {
			initFjjxOption(data.dprtcode);// 加载飞机
			$("#jx").val(data.jx);
			evaluation_modal.init({
				parentId : "addModal",
				isShowed : (data.zt==2||data.zt==3)?false:true,
				zlid : data.id,
				zlxl : 2,
				dprtcode :data.dprtcode,
				jx : $("#jx").val(),
			});
			$("#id").val(data.id);
			$("#dprt").val(data.dprtcode);
			$("#zt").val(data.zt);
			$("#fcrname").val(data.fcr_user.username +" " +data.fcr_user.realname);
			$("#jsrname").val(data.jsr_user.username +" " +data.jsr_user.realname);
			$("#jszlh").attr("disabled",true);
			document.getElementById('jszlhDiv').style.display="block";
			$("#jszlh").val(data.jszlh);
			$("#fcrq").val(data.fcrq.substring(0,10));
			$("#addModal #fcrq").datepicker("update");
			$("#fcrid").val(data.fcrid);
			$("#bb").val(data.bb);
			$("#current_bb").val(data.bb);
			$("#jsrid").val(data.jsrid);
			$("#fcrid").val(data.fcrid);
			$("#zhut").val(data.zhut);
			$("#lysm").val(data.lysm);
			$("#zxsx").val(data.zxsx);
			$("#bflyyj").val(data.bflyyj);
			$("#bz").val(data.bz);
			WorkContentUtil.init({
				djid:data.id,//关联单据id
				ywlx:2,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
				colOptionhead : (data.zt==2||data.zt==3)?false:true,
				dprtcode:data.dprtcode//默认登录人当前机构代码
			});
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				djid:data.id,
				fileHead : (data.zt==2||data.zt==3)?false:true,
				colOptionhead : (data.zt==2||data.zt==3)?false:true,
				fileType:"technicalInstruction"
			});//显示附件
			introduce_process_info_class.show({ // 流程信息
				status : data.zt,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
				prepared : data.zdr_user.username + " "+ data.zdr_user.realname,// 编写人
				preparedDate : data.zdsj,// 编写日期
				checkedOpinion : data.shyj,// 审核意见
				checkedby : data.shr_user != null ? data.shr_user.username+ " " + data.shr_user.realname : "",// 审核人
				checkedDate : data.shsj,// 审核日期
				checkedResult : data.shjl,// 审核结论
				approvedOpinion : data.pfyj,// 批准意见
				approvedBy : data.pfr_user != null ? data.pfr_user.username+ " " + data.pfr_user.realname : "",// 批准人
				approvedDate : data.pfsj,// 批准日期
				approvedResult : data.pfjl ,// 审批结论
			});
			if(obj==data.zt||(obj==0&&data.zt==1||data.zt==5||data.zt==6)){
				// 改版
				if(obj == 7){
					data.previous = {
						id : data.id,
						bb : data.bb
					};
					$("#current_bb").val("");
					$("#id").val("");
					$("#fBbid").val(data.id);
					$("#introduce_process_info_class_lcxx").hide();
				}
				switchHistoryVersion(data);
				$("#addModal").modal("show");
			}else{
				AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
			}	
		}
	});
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}

//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}

//初始化机型下拉框
function initFjjxOption(dprtcode) {
	var typeList = acAndTypeUtil.getACTypeList({
		DPRTCODE : dprtcode
	});
	if (typeList.length > 0) {
		for (var i = 0; i < typeList.length; i++) {
			$("#jx").append(
					"<option value='" + StringUtil.escapeStr(typeList[i].FJJX)
							+ "'>" + StringUtil.escapeStr(typeList[i].FJJX)
							+ "</option>");
		}
	} else {
		$("#jx").append("<option value=''>暂无飞机机型No data</option>");
	}
}

$("#jsrname").dblclick(function(){
	openWinUser('jsr');
})
$("#fcrname").dblclick(function(){
	openWinUser('fcr');
})

function openWinUser(obj){
	var userList = [];
	var a = $("#"+obj+"id").val();
	var b = $("#"+obj+"name").val();
	for (var i = 0; i < a.split(",").length; i++) {
		var p = {
			id : a.split(",")[i],
			displayName : b.split(",")[i]
		};
		userList.push(p);
	}
	if (a == "") {
		userList = null;
	}
	Personel_Tree_Multi_Modal.show({
		checkUserList:userList,//原值，在弹窗中回显
		dprtcode:$("#zzjgid").val(),//
		multi:false,
		clearUser:false,
		callback: function(data){//回调函数
			var displayName = '';
			var mjsrid = '';
			if(data != null && data != ""){
				$.each(data, function(i, row){
					displayName += formatUndefine(row.displayName) + ",";
					mjsrid += formatUndefine(row.id) + ",";
				});
			}
			if(displayName != ''){
				displayName = displayName.substring(0,displayName.length - 1);
			}
			if(mjsrid != ''){
				mjsrid = mjsrid.substring(0,mjsrid.length - 1);
			}
			$("#"+obj+"name").val(displayName);
			$("#"+obj+"id").val(mjsrid);			
			$("#form").data('bootstrapValidator').destroy();
			$('#form').data('bootstrapValidator', null);
			validation(); 
		}
	});
}



function getData(){
	var param={};
	if($("#dprt").val() != ''){
		param.dprtcode = $("#dprt").val();
	}else{
		param.dprtcode = $("#zzjgid").val();
	}
	param.jszlh=$("#jszlh").val();
	param.jx=$.trim($("#jx").val());
	param.lysm=$.trim($("#lysm").val());
	param.zhut=$.trim($("#zhut").val());
	param.jsrid=$.trim($("#jsrid").val());
	param.fcrid=$.trim($("#fcrid").val());
	param.fcrq=$.trim($("#fcrq").val());
	param.zxsx=$.trim($("#zxsx").val());
	param.bflyyj=$.trim($("#bflyyj").val());
	param.bb=$("#bb").is(":visible") ? $.trim($("#bb").val()) : $.trim($("#current_bb").val());
	param.bz=$.trim($("#bz").val());
	param.jszt=0;
	// 技术评估单数据
	var technicalList = evaluation_modal.getData();
	if (technicalList != null && technicalList != '') {
		var instructionsourceList = [];
		// 组装下达指令数据
		$.each(technicalList, function(index, row) {
			var instructionsource = {};
			instructionsource.dprtcode = row.dprtcode;
			instructionsource.pgdid = row.id;
			instructionsource.pgdh = row.pgdh;
			instructionsource.bb = row.bb;
			instructionsourceList.push(instructionsource);
		});
		param.isList = instructionsourceList;
	}
	//工作内容
	param.workContentList = WorkContentUtil.getData(0);
	//附件
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	param.attachments = attachmentsObj.getAttachments();
	var paramsMap = {};
	paramsMap.currentZt = $("#zt").val();
	param.paramsMap=paramsMap;
	return param;
}

function saveData(){
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	var param = {};
	param = getData();
	if(!WorkContentUtil.isValid){
		finishWait($("#addModal"));
		return false;
	}	
	if ($("#zt").val() != 5 && $("#zt").val() != 6) {
		param.zt = 1;
	} else {
		param.zt = $("#zt").val();
	}
	var id = $("#id").val();
	if (id != null && id != '') {
		param.id = id;
		url = basePath + "/project2/instruction/update";
		var message = "修改成功!";
	} else {
		url = basePath + "/project2/instruction/add";
		var message = "保存成功!";
	}
	sendDataRequest(param, url, message,"addModal");
}

//提交
function submitData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	var param = {};
	param = getData();
	param.zt = 2;
	var id = $("#id").val();
	if (id != null && id != '') {
		param.id = id;
		url = basePath + "/project2/instruction/update";
	} else {
		url = basePath + "/project2/instruction/add";
	}
	var message = "提交成功!";
	tip("确定要提交吗？",param,url,message,"addModal");
}

//审核
function auditedData(obj) {
	var shyj = introduce_process_info_class.getData().shenhe;
		var param = packingReview();
		param.shyj = shyj;
		var paramsMap = {};
		paramsMap.currentZt = $("#zt").val();
		param.paramsMap = paramsMap;
		var url = basePath + "/project2/instruction/review";
		var message = "";
		if (obj == '3') {
			param.zt = 3;
			param.shjl = 3;
			message = "审核通过成功!";
			tip("确定要审核通过吗？",param,url,message,"addModal");
		} else {
			param.zt = 5;
			param.shjl = 5;
			message = "审核驳回成功!";
			tip("确定要审核驳回吗？",param,url,message,"addModal");
		}
}
//批准
function approvedData(obj) {
	var pfyj = introduce_process_info_class.getData().shenpi;
		var param = packingApprove();
		param.pfyj = pfyj;
		var paramsMap = {};
		paramsMap.currentZt = $("#zt").val();
		param.paramsMap = paramsMap;
		var url = basePath + "/project2/instruction/review";
		var message = '';
		if (obj == '4') {
			param.zt = 4;
			param.pfjl = 4;
			message = "批准通过!";
			tip("确定要批准通过吗？",param,url,message,"addModal");
		} else {
			param.zt = 6;
			param.pfjl = 6;
			message = "批准驳回成功!";
			tip("确定要批准驳回吗？",param,url,message,"addModal");
		}
}

//组装审核内容
function packingReview() {
	var param = {}
	param.shrid = $("#userId").val();
	param.id = $("#id").val();
	param.shbmid = $("#zdbmid").val();
	return param;
}

//组装批复内容
function packingApprove() {
	var param = {}
	param.pfrid = $("#userId").val();
	param.id = $("#id").val();
	param.pfbmid = $("#zdbmid").val();
	return param;
}

//提示信息
function tip(tipmessage,param,url,message,modal){
	AlertUtil.showConfirmMessage(tipmessage, {
		callback : function() {
				sendDataRequest(param, url, message,modal);
		}
	});
	finishWait($("#"+modal));
}
//发送请求
function sendDataRequest(param, url, message,modal) {
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#addModal"),
		success : function(data) {
			id = data;				
			pageParam=encodePageParam();
			AlertUtil.showMessage(message, decodePageParam());		
			$("#"+modal).modal("hide");
			finishWait($("#"+modal));
			refreshPermission();
		}
	});
}

$("#jx").change(function() {
	evaluation_modal.changeParam({
		jx : $("#jx").val(),// 机型
	});
})

function initModalData(){
	$("#addModal input[type='text']").val("");
	$("#addModal select").empty();
	$("#addModal textarea").val("");
	$("#addModal #fcrq").datepicker("update");
	$("#addModal #id").val("");
	$("#addModal #fcrid").val("");
	$("#addModal #jsrid").val("");
	$("#addModal #fcrname").addClass("readonly-style");
	$("#addModal #jsrname").addClass("readonly-style");
	$("#addModal #zt").val("");
	$("#addModal #dprt").val("");
	$("#addModal #jsrbutton").show();	
	$("#addModal #fcrbutton").show();
	$("#addModal button[type='button']").show();
	$("#addModal input[type='text']").attr("disabled", false);
	$("#addModal input[type='checkbox']").attr("disabled", false);
	$("#addModal input[type='radio']").attr("disabled", false);
	$("#addModal select").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#addModal #fcr_remark").show();
	$("#addModal #fcrq_remark").show();
	$("#addModal #jx_remark").show();
	$("#addModal #jsr_remark").show();
	$("#addModal #bb_remark").show();
	$("#addModal #zhut_remark").show();
	$("#addModal #zxsx_remark").show();
	document.getElementById('jszlhDiv').style.display="none";
}

function validation(){
	validatorForm = $("#form").bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			fcrname : {
				validators : {
					notEmpty: {
                        message: '发出人不能为空'
                    }
				}
			},
			fcrq : {
				validators : {
					notEmpty: {
						message: '发出日期不能为空'
					}
				}
			},
			jx : {
				validators : {
					notEmpty : {
						message : '机型不能为空'
					}
				}
			},
			bb : {
				validators : {
					notEmpty: {
                        message: '版本不能为空'
                    },
                    regexp: {
                    	  regexp: /^[\x00-\xff]*$/,
 	                      message: '版本不能输入中文及中文符号'
	                }
				}
			},
			current_bb : {
				validators : {
					notEmpty : {
						message : '版本不能为空'
					},
					regexp : {
						regexp : /^\d+(\.\d{1,2})?$/,
						message : '请输入整数或小数点后2位小数'
					},
					callback: {
                        message: '版本必须大于上个版本',
                        callback: function(value, validator) {
                        	var previous = $("#previous_bb").text();
                        	if(value && previous && !isNaN(value) && parseFloat(value) <= parseFloat(previous)){
                        		return false;
                        	}
                        	return true;
                        }
                    }
				}
			},
			jsrname : {
				validators : {
					notEmpty : {
						message : '接收人不能为空'
					}
				}
			},
			zhut : {
				validators : {
					notEmpty : {
						message : '主题不能为空'
					}
				}
			},
			zxsx : {
				validators : {
					notEmpty : {
						message : '执行时限不能为空'
					}
				}
			}
		}
	})
}
$("#addModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
});

$('#fcrq').datepicker({
	autoclose : true,
	clearBtn : true
}).on('hide',function(e) {
		$('#form').data('bootstrapValidator').updateStatus('fcrq','NOT_VALIDATED', null).validateField('fcrq');
	});

//切换显示历史版本
function switchHistoryVersion(data){
	if(data && data.previous){
		$("#bbViewHistoryDiv").show();
		$("#bbNoViewHistoryDiv").hide();
		initHistoryBb(data.id);
		$("#previous_bb").text(data.previous.bb);
		$("#previous_id").val(data.previous.id)
	}else{
		$("#bbViewHistoryDiv").hide();
		$("#bbNoViewHistoryDiv").show();
	}
}

//查看前一版本的
function viewPrevious(){
	var id = $("#previous_id").val();
	window.open(basePath + "/project2/instruction/view?id=" + id);
}

//初始化历史版本
function initHistoryBb (id){
	WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#addModal",function(obj){
		return getHistoryBbList(id);
	});
}

//获取历史版本列表
function getHistoryBbList (id){
	return technical_order_history_alert_Modal.getHistoryBbList(id);
}

//改版
function revisionData() {
	startWait($("#addModal"));
	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
		finishWait($("#addModal"));
		return false;
	}
	var param = {};
	param = getData();
	param.zt = 1;
	param.fBbid = $("#fBbid").val();
	url = basePath + "/project2/instruction/revision";
	var message = "改版成功!";
	tip("确定要改版吗？",param,url,message,"addModal");
}