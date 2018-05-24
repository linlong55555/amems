var zt=[0,1,2,3,4,5,6,7,8,9,10];
var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
var engineeringitemData = [];
var pagination={};
$(document).ready(function(){
			decodePageParam();
		 //goPage(1,"auto","desc");//开始的加载默认的内容 
		 refreshPermission();
		 $("#CheckAll").click(function() { 
				 /*$("[name=subBoxshenhe]:checkbox").each(function() { 
					 $(this).attr("checked", flag); 
				 }); 
				 $("[name=subBoxpifu]:checkbox").each(function() { 
					 $(this).attr("checked", flag); 
				 }); */
				 $("[name=subBox]:checkbox").each(function() { 
					 $(this).attr("checked", 'checked'); 
				 });
				 $("#CancelAll").removeAttr("checked"); 
		 }); 
		 
		 $("#CancelAll").click(function() { 
				 /*$("[name=subBoxshenhe]:checkbox").each(function() { 
					 $(this).attr("checked", flag); 
				 }); 
				 $("[name=subBoxpifu]:checkbox").each(function() { 
					 $(this).attr("checked", flag); 
				 }); */
				 $("[name=subBox]:checkbox").each(function() { 
					 $(this).removeAttr("checked"); 
				 });
				 $("#CheckAll").removeAttr("checked"); 
		 }); 
			//初始化日志功能
			logModal.init({code:'B_G_009'});
	});
 //带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	engineeringitemData=[];
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
	obj.paramsMap=paramsMap;
	obj.pagination = pagination;
	obj.keyword=searchParam.keyword;
	obj.zt=searchParam.zt;
	obj.dprtcode=searchParam.dprtcode;
	obj.zdrid=searchParam.zdrid;
	//obj.id = '1'';//搜索条件
	if(id != ""){
		
		obj.id = id;
		id = "";
	}
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/engineering/queryEngineeringList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   engineeringitemData=data.rows;
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
			   signByKeyword($("#keyword_search").val(),[3,4,5,6,9,13]);
		   } else {
			  $("#engineeringlist").empty();
			  $("#pagination").empty();
			  $("#engineeringlist").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
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
	 searchParam.dprtcode = $.trim($("#zzjg").val());
	 return searchParam;
 }
 function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());
	 params.zt=$.trim($("#zt").val());
	 params.zzjg=$.trim($("#zzjg").val());
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
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\"  onclick='clickRow("+index+",this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\"  onclick='clickRow("+index+",this)' >";
		   }
		   htmlContent += "<td class='text-center fixed-column' ><input type='checkbox' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" /></td>";		
		   	
		   htmlContent = htmlContent + "<td class='text-center fixed-column' >";
		      
		   if(row.zt==0|| row.zt==1 || row.zt==2 || row.zt==3 || row.zt==5 || row.zt==6){
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:02' onClick=\"edit('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='修改 Edit '></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==0 ||row.zt==5 ||row.zt==6){
			   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:03' onClick=\"invalid('"
			+ row.id + "',"+row.zt+")\" title='作废 Invalid'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==3){
			   htmlContent = htmlContent +"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:04' gczlbh='"+row.gczlbh+"'  zdjsyy='' onClick=\"shutDown('"+row.id+"', this,'',true)\"title='指定结束 End'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==1){
			   htmlContent = htmlContent +"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:05' onClick=\"audit('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==2){
			   htmlContent = htmlContent +"<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:06' onClick=\"reply('"
			+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==3){
			   htmlContent = htmlContent +"<i class='icon-print color-blue cursor-pointer checkPermission' permissioncode='project:engineering:main:07' onClick=\"printEngineering('"
			   + row.id + "','"+row.zt+"')\" title='打印  Print'></i>&nbsp;&nbsp;";
		   }
		   
		   
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.gczlbh))+"' ><a href=\"javascript:viewMainEngineering('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.gczlbh))+"</a></td>";   
		   htmlContent = htmlContent + "<td class='text-left'>";
			
		   var strs= new Array(); //定义一数组
		   if(row.pgjg!=null && row.pgjg!=""){
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
				   
				   htmlContent = htmlContent +"<a href=\"javascript:selectPgdBypgdh('"+pgdhid+"','"+row.dprtcode+"')\">"+pgdh+"</a>"; 
				   
				   if (i != 0) {
						htmlContent = htmlContent + "<br>";

					}
					if (i != 0 && i == strs.length - 1) {
						htmlContent = htmlContent + "</div>";
					}
			   } 
		   }
		   
		  /* htmlContent = htmlContent +"<a href=\"javascript:selectPgdBypgdh('"+row.pgjg+"')\">"+formatUndefine(row.pgjg)+"</a><br>"; */
		   
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.zhut))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.zhut))+"</td>";
		   htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.xggzh))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.xggzh))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.txyj))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.txyj))+"</td>";
		   if(row.isCfzxsx==1){
			   htmlContent = htmlContent + "<td class='text-center'>是</td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>否</td>";
			   
		   }
		   if(row.isZlphyx==1){
			   htmlContent = htmlContent + "<td class='text-center'>是</td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>否</td>";
			   
		   }
		   
		   if(row.zt==9){
			   		var zdjsr = row.zdjsr_user?row.zdjsr_user.displayName:'';
					   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
					   "gczlbh='"+StringUtil.escapeStr(row.gczlbh)+"' "+
					   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
					   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
					   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   }
		   /*htmlContent = htmlContent + "<td class='text-left'>"+zts[row.zt]+"</td>";*/  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.username))+" "+StringUtil.escapeStr(formatUndefine(row.realname))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>";  
		   htmlContent = htmlContent + "</tr>";
		    
	   });
	   $("#engineeringlist").empty();
	   $("#engineeringlist").html(htmlContent);
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
	 })
 }
 
//作废
 function invalid(id,zt){
	 if(checkUpdMt(id)){
		 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
			 if(zt==0 || zt== 5 || zt==6){
				 AjaxUtil.ajax({
					 url:basePath + "/project/engineering/deleteEngineering",
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 dataType:"json",
					 success:function(data){
						 if(data.state=="success"){
							 AlertUtil.showErrorMessage("作废成功");
							 refreshPage();
						 }else{
							 AlertUtil.showErrorMessage(data.message);
						 }
					 }
				 });
			 }else{
				 AlertUtil.showErrorMessage('只能作废未提交状态的工程指令!');
				 
			 }
			 
		 }});
		 
	 }
 }
//指定结束
 function shutDown(id,obj,zdjsrq,isEdit){
		var sqdh=$(obj).attr("gczlbh");
		var zdjsyy=$(obj).attr("zdjsyy");
		var zdjsr=$(obj).attr("zdjsr");
	 //查询该工程指令下的eo工单是否都已关闭
	 if(selectbyEoId(id)){
		 AlertUtil.showErrorMessage('该工程指令不能指定结束!');
		 return;
	 }
	 
 	AssignEndModal.show({
 		chinaHead:'EO指令编号',//单号中文名称
 		englishHead:'Engineering Order No.',//单号英文名称
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
 //指令结束查看工卡是否满足操作
 function selectbyEoId(id){
	 AjaxUtil.ajax({
	 		url:basePath + "/project/engineering/selectbyEoId",
		    type: "post",
		    dataType:"json",
		    data:{
		    	'id':id
		    },
	 		success:function(data){
	 			if(data.total>0){
	 				return true;
	 			}else{
	 				return false;
	 			}
	 		}
	 	});
 }
 
//指定结束确认
 function zdjsOver(obj){
	AjaxUtil.ajax({
 		url:basePath + "/project/engineering/offEngineering",
	    type: "post",
	    contentType:"application/json;charset=utf-8",
	    dataType:"json",
	    data:JSON.stringify(obj),
 		success:function(data){
 			if(data.state=="success"){
 				$("#AssignEndModal").modal("hide");
 				AlertUtil.showErrorMessage(data.text);
 				id=obj.id;
 				refreshPage();
 			}else{
 				AlertUtil.showErrorMessage(data.text);
 			}
 		}
 	});
	
 }
 //修改
 function edit(engineeringId,zt,dprtcode){
	 if(zt==0||zt==1 ||zt==2 ||zt==3 || zt==5 || zt==6){
		 if(checkUpdMt(engineeringId)){	
			 window.location.href =basePath+"/project/engineering/intoEditMainEngineering?id="+engineeringId+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
		 	}
		 
	 	}else{
	 		AlertUtil.showErrorMessage('该状态不能进行修改!');
	 	}
	}
//审核
 function audit(id,zt,dprtcode){
 		if(zt!=1){
 			AlertUtil.showErrorMessage('只能对已提交状态的数据进行审核!');
 			return
 		}
 		window.location.href =basePath+"/project/engineering/intoShenheMainEngineering?id=" + id+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
 }
 //批复
 function reply(id,zt,dprtcode){
 		if(zt!=2){
 			AlertUtil.showErrorMessage('只能对已审核状态的数据进行批准!');
 			return
 		}
 		window.location.href =basePath+"/project/engineering/intoPifuMainEngineering?id=" + id+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
 }
 //打印
 function printEngineering(id,zt){
	 if(zt==3){
		 //window.open( basePath+"/project/engineering/export/pdf/" + id)
		 var url=basePath+"/project/engineering/export/pdf/"+id;
			window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');
	 }
 }
//验证是否能进行修改
 function checkUpdMt(id){
 	var flag = false;
 	
 	AjaxUtil.ajax({
 		url:basePath + "/project/engineering/checkUpdMt",
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
 function selectPgdBypgdh(id,dprtcode){
	 window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	
}
 function viewMainEngineering(id,dprtcode){
	 window.open(basePath+"/project/engineering/intoViewMainEngineering?id=" + id+"&dprtcode="+dprtcode);
 }
//改变组织机构时变换的制单人
 function changeContent(){
 	var obj={};
 	 obj.jgdm=$('#zzjg').val();
 	 AjaxUtil.ajax({
 	 		url:basePath+"/project/technicalfile/changeContent",
 	 		type:"post",
 	 		async: false,
 	 		contentType:"application/json;charset=utf-8",
 	 		data:JSON.stringify(obj),
 	 		dataType:"json",
 	 		success:function(data){
 	 			finishWait();//结束Loading
 	 			$('#zdrid').empty();
 	 			 $('#jxgcs').empty();
 	 			appendChangeContent(data.userToRole);
 	 			
 	 		}
 	 	});
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
 
//打开批量审核批准对话框
 function batchApproveWin(isApprovel){
 	var idArr = [];
 	var approvalArr = [];
 	var approvalNotArr = [];
 	var zt = isApprovel?2:1;
 	$("#engineeringlist").find("tr input:checked").each(function(){
 		var index = $(this).attr("index");
 		var engineering = engineeringitemData[index];
 		if(engineering.zt == zt ){
 			idArr.push(engineering.id);
 			approvalArr.push(engineering.gczlbh);
    	 	}else{
    	 		approvalNotArr.push(engineering.gczlbh);
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
 	var url = basePath+"/project/engineering/batchReview";
 	var message = '批量审核成功!';
 	if(isApprovel){
 		url = basePath+"/project/engineering/batchApprove";
 		message = '批量批准成功!';
 	}
 	startWait();
 	 //提交数据
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
 function clickRow(index,this_){
		var $checkbox1 = $("#engineeringlist :checkbox[name='checkrow']:eq("+index+")");
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
 
 
/* function clickRow(index,this_){
		
		var $checkbox = $("#engineeringlist :checkbox[name='checkrow']:eq("+index+")");
		$(".sticky-col").find(":checkbox[name='checkrow']:eq("+index+")").attr("checked", !$checkbox.is(":checked"));
		$checkbox.attr("checked", !$checkbox.is(":checked"));
	}
 
 
 
	function checkRow(index,this_){
		$("#engineeringlist :checkbox[name=checkrow]:eq("+index+")").attr("checked", $(this_).is(":checked"));
	}*/
	
	
	
	
	
	function add(){
		window.location = basePath+"/project/engineering/intoAddEngineering?pageParam="+encodePageParam();
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
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage(1,"auto","desc");
			}
		}catch(e){
			goPage(1,"auto","desc");
		}
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