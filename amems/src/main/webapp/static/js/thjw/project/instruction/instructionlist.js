var zt=[0,1,2,3,4,5,6,7,8,9,10];
var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
var choseAllIds=[];
var instructionitemData = [];
var pagination={};
$(document).ready(function(){
	 	 decodePageParam();
		 //菜单设置  自己设置
		 $("#revisionManagementPage").addClass("active");
		 $("#demo1").addClass("active");
		 refreshPermission();
		//初始化日志功能
			logModal.init({code:'B_G_004'});
	});
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		instructionitemData = [];
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
		obj.paramsMap=paramsMap;
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.zt=searchParam.zt;
		obj.jszt=searchParam.jszt;
		obj.dprtcode=searchParam.dprtcode;
		//obj.id = '1'';//搜索条件
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/project/instruction/queryInstructionList",
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
				   signByKeyword($("#keyword_search").val(),[3,4,5,6,10]);
			   } else {
				  $("#instructionlist").empty();
				  $("#pagination").empty();
				  $("#instructionlist").append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
 //将搜索条件封装 
 function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
	 searchParam.zt = $.trim($("#zt").val());
	 searchParam.jszt = $.trim($("#jszt").val());
	 searchParam.dprtcode = $.trim($("#zzjg").val());
	 searchParam.zdrid = $.trim($("#zdrid").val());
	 return searchParam;
 }
 function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());
	 params.zt=$.trim($("#zt").val());
	 params.zzjg=$.trim($("#zzjg").val());
	 params.jszt=$.trim($("#jszt").val());
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
		   if(row.zt==0|| row.zt==1 ||row.zt==2 || row.zt==3 || row.zt==5 || row.zt==6){
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:instruction:main:02' onClick=\"edit('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==0||row.zt==5 ||row.zt==6){
			   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project:instruction:main:03' onClick=\"invalid('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==3){
			   htmlContent = htmlContent +"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:instruction:main:04'  jszlh='"+row.jszlh+"' zdjsyy='' onClick=\"shutDown('"+row.id+"', this,'',true)\" title='指定结束 End'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==1){
			   htmlContent = htmlContent +"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:instruction:main:05' onClick=\"audit('"
			+ row.id + "',"+row.zt+",'"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==2){
			   htmlContent = htmlContent +"<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:instruction:main:06' onClick=\"reply('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
		   }
		   
		   htmlContent = htmlContent + "<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printInstruction('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;"
		   
		   htmlContent = htmlContent + "</td>";
		   
		  /* htmlContent = htmlContent + "<td class='text-center'>"+ "<div><i class='icon-edit color-blue cursor-pointer' onClick=\"edit('"
			+ row.id + "',"+row.zt+")\" title='修改 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer' onClick=\"invalid('"
			+ row.id + "',"+row.zt+")\" title='作废 Invalid '></i>&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer' onClick=\"shutDown('"
			+ row.id + "','"+row.jszlh+"',"+row.zt+")\" title='指定结束 End'></i>&nbsp;&nbsp;<i class='icon-foursquare color-blue cursor-pointer' onClick=\"audit('"
			+ row.id + "',"+row.zt+")\" title='审核 Review'></i>&nbsp;&nbsp;<i class='icon-check color-blue cursor-pointer' onClick=\"reply('"
			+ row.id + "',"+row.zt+")\" title='批准 Approve'></i>"+"</td>"; */
		   htmlContent = htmlContent + "<td class='text-center fixed-column'><a href=\"javascript:viewMainInstruction('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.jszlh))+"</a></td>";   
		   htmlContent = htmlContent + "<td class='text-left'>";
		   
		   if(row.pgjg!=null && row.pgjg!=""){
			   var strs= new Array(); //定义一数组
			   strs=row.pgjg.split(","); //字符分割
			   strs.sort();
			   for (i=0;i<strs.length ;i++ ){
				   var pgdh=strs[i].split("^")[0];
				   var pgdhid=strs[i].split("^")[1];
				   
				   if (i == 1) {
						htmlContent = htmlContent
								+ "　<i class='icon-caret-down' id='"
								+ row.id
								+ "icon' onclick=vieworhideWorkContent('"
								+ row.id + "')></i><div id='"
								+ row.id
								+ "' style='display:none'>";
					}
				   htmlContent = htmlContent +"<a href=\"javascript:selectPgdBypgdh('"+pgdhid+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(pgdh)+"</a>"; 
				   
				   if (i != 0) {
						htmlContent = htmlContent + "<br>";

					}
					if (i != 0 && i == strs.length - 1) {
						htmlContent = htmlContent + "</div>";
					}
			   } 
		   }
		  /* $.each(row.orderSourceList,function(index,p){
			   htmlContent = htmlContent +"<a href=\"javascript:selectPgdBypgdh('"+p.pgdid+"')\">"+formatUndefine(p.pgdh)+"</a><br/>";
		   });*/
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ckzl)+"' class='text-left'>"+StringUtil.escapeStr(row.ckzl)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zhut)+"' class='text-left'>"+StringUtil.escapeStr(row.zhut)+"</td>";
		   if(row.zt==9){
			   var zdjsr = row.zdjsr_user?row.zdjsr_user.displayName:'';
			   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
			   "jszlh='"+StringUtil.escapeStr(row.jszlh)+"' "+
			   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
			   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
			   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   }
		   if(row.zdr_user!=null && row.zdr_user!=""){
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jsr_user.displayName)+"'>"+StringUtil.escapeStr(row.jsr_user.displayName)+"</td>"; 
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>"; 
		   }
		   if(row.jszt==0){
			   htmlContent = htmlContent + "<td class='text-center'>未接收</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>已接收</td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr_user.displayName)+"'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";  
		   htmlContent = htmlContent + "</tr>";
		    
	   });
	   $("#instructionlist").empty();
	   $("#instructionlist").html(htmlContent);
	   
	   refreshPermission();
 }
 function vieworhideWorkContent(id) {
	 new Sticky({tableId:'quality_check_list_table'});
		var flag = $("#" + id).is(":hidden");
		if (flag) {
			$("#" + id).fadeIn(500);
			$("#" + id + 'icon').removeClass("icon-caret-down");
			$("#" + id + 'icon').addClass("icon-caret-up");
		} else {
			$("#" + id).hide();
			$("#" + id + 'icon').removeClass("icon-caret-up");
			$("#" + id + 'icon').addClass("icon-caret-down");
		}

	}


//查看指定结束界面
function viewreason(jszlh,reason,jsrq){
		$('#zlbh1').val(jszlh);
		$('#zdjsyy1').val(reason);
		$('#zdjsrq1').val(jsrq);
		$("#alertModalview").modal("show");
	 }

 
 //查看revision对应的工卡列表
 function goToLinkPage(obj,rid){
	 obj.stopPropagation(); //屏蔽父元素的click事件
	 window.location =basePath+"/main/work/listpage?rid="+rid;
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
	 changeContent();
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
	 
//修改
function edit(instructionId,zt,dprtcode) {
	if(zt==0 || zt==1 || zt==2 || zt==3 || zt==5 || zt==6){
		if(checkUpdMt(instructionId)){	
			window.location.href =basePath+"/project/instruction/intoEditMainInstruction?id="+instructionId+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
			}
	 }else{
		 AlertUtil.showErrorMessage('只有保存,提交,审核驳回,批准驳回下的状态才能进行操作!');
	 }
}
//验证是否能进行修改
function checkUpdMt(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/checkUpdIt",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			if (data.state == "success") {
				flag = true;
			} else {
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
	return flag;
}
//作废
function invalid(id,zt){
	 if(checkUpdMt(id)){
		 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
			 if(zt==0 || zt==5 || zt==6){
				 AjaxUtil.ajax({
					 url:basePath + "/project/instruction/deleteInstruction",
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 dataType:"json",
					 success:function(data){
						 if(data.state=="success"){
							 AlertUtil.showErrorMessage('作废成功!');
							 refreshPage();
						 }else{
							 AlertUtil.showErrorMessage(data.message);
						 }
					 }
				 });
			 }else{
				 AlertUtil.showErrorMessage("只能作废未提交状态的技术指令!");
				 
			 }
			 
		 }});
		 
	 }
	
}
/*function shutDown(id,zlbh,zt){
	 if(checkUpdMt(id)){
		 if(zt==3){
			 $("#zlbh").val(zlbh);
			 $("#zlid").val(id);
			 $("#alertModalZdjs").modal("show");
		 }else{
			 AlertUtil.showErrorMessage('该状态的技术指令不能指定结束!');
		 }
	 }
}*/
//指定结束
function shutDown(id,obj,zdjsrq,isEdit){
	var sqdh=$(obj).attr("jszlh");
	var zdjsyy=$(obj).attr("zdjsyy");
	var zdjsr=$(obj).attr("zdjsr");
	AssignEndModal.show({
		chinaHead:'技术指令编号',//单号中文名称
		englishHead:'T/O No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:zdjsr,//指定结束人
		jssj:zdjsrq,//指定结束时间
		jsyy:zdjsyy,//指定结束原因
		callback: function(data){//回调函数
			if(null != data && data != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy : data
				};
				zdjsOver(obj);
			}
		}
	});
}
//指定结束确认
function zdjsOver(obj){
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/offInstruction",
	    type: "post",
	    contentType:"application/json;charset=utf-8",
	    dataType:"json",
	    data:JSON.stringify(obj),
		success:function(data){
			if(data.state=="success"){
					$("#AssignEndModal").modal("hide");
					AlertUtil.showErrorMessage("指定结束成功!");
	 				id=data.id;
	 				refreshPage();
			}else{
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
	
}




//查看跳转
function viewMainInstruction(id,dprtcode){
	window.open(basePath+"/project/instruction/intoViewMainInstruction?id=" + id+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam());
}
//审核
function audit(id,zt,dprtcode){
		if(zt!=1){
			AlertUtil.showErrorMessage('只能对已提交状态的数据进行审核!');
			return
		}
		window.location.href =basePath+"/project/instruction/intoShenheMainInstruction?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
}
//批复
function reply(id,zt,dprtcode){
		if(zt!=2){
			AlertUtil.showErrorMessage('只能对已审核状态的数据进行批准!');
			return
		}
		window.location.href =basePath+"/project/instruction/intoPifuMainInstruction?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
}

//拼接机型工程师和制单人 列表内容
function appendChangeContent(data){
	 var appendChangeContent='';
	 appendChangeContent=appendChangeContent+"<option value=''>显示全部</option>";
	 $.each(data,function(index,row){
		 appendChangeContent=appendChangeContent+"<option value='"+row.id+"'>"+row.displayName+"</option>";
	 });
	 $('#zdrid').html(appendChangeContent);
	 $('#jxgcs').html(appendChangeContent);
	 
}
function selectPgdBypgdh(id,dprtcode){
	 window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	
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
		 //var flag = $("#"+choseAllIds[i]).is(":hidden");
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
/*function clickRow(index,this_){
	var $checkbox = $("#instructionlist :checkbox[name='checkrow']:eq("+index+")");
	$(".sticky-col").find(":checkbox[name='checkrow']:eq("+index+")").attr("checked", !$checkbox.is(":checked"));
	$checkbox.attr("checked", !$checkbox.is(":checked"));
}
function checkRow(index,this_){
	$("#instructionlist :checkbox[name=checkrow]:eq("+index+")").attr("checked", $(this_).is(":checked"));
}*/

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
	var zt = isApprovel?2:1;
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
	var url = basePath+"/project/instruction/updateBatchAudit";
	var message = '批量审核成功!';
	if(isApprovel){
		url = basePath+"/project/instruction/updateBatchApprove";
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
		$("#jszt").val(params.jszt);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
function add(){
	window.location = basePath+"/project/instruction/intoAddInstruction?pageParam="+encodePageParam();
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}

function printInstruction(id){
	/*var url = basePath+"/project/instruction/export/pdf/"+id;
	window.open(url);*/
	
	var url=basePath+"/project/instruction/export/pdf/"+id;
	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
			'PDF','width:50%;height:50%;top:100;left:100;');
}
//全选
function performSelectAll(){
	$(":checkbox[name=checkrow]").attr("checked", true);
}
//取消全选
function performSelectClear(){
	$(":checkbox[name=checkrow]").attr("checked", false);
}