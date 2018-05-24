var zt=[0,1,2,3,4,5,6,7,8,9,10];
var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
var jobCarditemData = [];
var annunciateitemData = [];
var pagination={};
$(document).ready(function(){
	var dprtcode=$("#zzjg").val();
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',dprtcode);      //专业
	 	 decodePageParam();
		 refreshPermission();
		//初始化日志功能
		logModal.init({code:'B_G_013'});
		//获取工作组
		loadWorkGroup();
	});
//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=$("#zzjg").val();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/workgroup/loadWorkGroup",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   $("#gzzid").empty();
			   if(data!=null){
				   var apps = '';
				   var list=data.wgList;
				   $.each(list,function(i,list){
						   apps += "<option value='"+list.id+"' >"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
			 	   }); 
				   if(!data.wgMust){
					   $("#gzzid").append("<option value=''>显示全部</option>"+apps);
				   }else{
					   $("#gzzid").append(apps);
				   }
			   }else{
				   $("#gzzid").append("<option value=''>显示全部</option>");
			   }
	       },
	 }); 
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		jobCarditemData=[];
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
		obj.paramsMap=paramsMap;
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.zt=searchParam.zt;
		obj.dprtcode=searchParam.dprtcode;
		obj.zy=searchParam.zy;
		obj.yxx=searchParam.yxx;
		obj.gzzId=searchParam.gzzid;
		obj.gklx=searchParam.gklx;
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/project/jobCard/queryJobCardList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   jobCarditemData=data.rows;
				   appendContentHtml(data.rows);
				   signByKeyword($("#keyword_search").val(),[3,4,9,10,12,14]);
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
			   } else {
				  $("#JobCardlistlist").empty();
				  $("#pagination").empty();
				  $("#JobCardlistlist").append("<tr><td colspan=\"16\">暂无数据 No data.</td></tr>");
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
		 searchParam.zy = $.trim($("#zy").val());
		 searchParam.yxx = $.trim($("#yxx").val());
		 searchParam.gklx = $.trim($("#gklx").val());
		 searchParam.gzzid = $.trim($("#gzzid").val());
		 return searchParam;
	 }
	 function encodePageParam(){
		 var pageParam={};
		 var params={};
		 params.keyword=$.trim($("#keyword_search").val());
		 params.zt=$.trim($("#zt").val());
		 params.zzjg=$.trim($("#zzjg").val());
		 params.yxx=$.trim($("#yxx").val());
		 params.zy=$.trim($("#zy").val());
		 pageParam.params=params;
		 pageParam.pagination=pagination;
		 return Base64.encode(JSON.stringify(pageParam));
	 }

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" onclick='clickRow("+index+",this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" onclick='clickRow("+index+",this)' >";
		   } 
		   htmlContent += "<td class='text-center fixed-column' ><input type='checkbox' index='"+index+"' name='checkrow' index='"+index+"'  /></td>";
		   
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		   if(row.zt!=4 && row.zt!=8 && row.zt!=9 && row.zt!=10){
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:jobCard:main:02' onClick=\"edit('"
			   				+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='修改 Edit '></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==0 ||row.zt==5 ||row.zt==6){
			   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project:jobCard:main:03' onClick=\"invalid('"
			+ row.id + "',"+row.zt+")\" title='作废 Invalid '></i>&nbsp;&nbsp;";
			   
		   }
		   if(row.zt==3){
			   htmlContent = htmlContent +"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:jobCard:main:04' gdbh='"+row.gdbh+"'  zdjsyy=''  onClick=\"shutDown('"+row.id+"', this,'',true)\"  title='指定结束 End '></i>&nbsp;&nbsp;";
			     
		   }
		   if(row.zt==1){
			   htmlContent = htmlContent +"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:jobCard:main:05' onClick=\"audit('"
				+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='审核 Review '></i>&nbsp;&nbsp;";
				   
		   }
		   if(row.zt==2 ){
			   htmlContent = htmlContent +"<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:jobCard:main:06' onClick=\"reply('"
				+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='批准 Approve '></i>&nbsp;&nbsp;";
		   }
		   
		   if(row.zt==3 ){
			   htmlContent = htmlContent +"<i class='icon-reorder color-blue cursor-pointer checkPermission'  onClick=\"generateGD('"
				+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='生成工单 Add '></i>&nbsp;&nbsp;";
		   }
		   
		   
		   htmlContent = htmlContent + "</td>";
		   
		   
		   htmlContent = htmlContent +"<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"'><a href=\"javascript:viewMainJobCard('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"</a></td>"; 
		   htmlContent = htmlContent + "<td class='text-center' title='"+formatUndefine(row.djbh)+"' ><a href=\"javascript:selectByZxbsAndBh('"+row.djbh+"','"+row.dprtcode+"')\">"+formatUndefine(row.djbh)+"</a></td>";
		  if(row.gklx==1){
			  htmlContent = htmlContent + "<td class='text-left'>定检工卡</td>"; 
		  }else if(row.gklx==2){
			  htmlContent = htmlContent + "<td class='text-left'>非定检工卡</td>"; 
		  }else{
			  htmlContent = htmlContent + "<td class='text-left'></td>"; 
		  }
		  if(row.gzz==null){
			  htmlContent = htmlContent + "<td class='text-left'></td>"; 
		  }else{
			  htmlContent = htmlContent + "<td class='text-left'  title='"+StringUtil.escapeStr(row.gzz.gzzmc)+"'>"+StringUtil.escapeStr(formatUndefine(row.gzz.gzzmc))+"</td>"; 
		  }
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(formatUndefine(row.zy))+"</td>"; 
		   if(row.jhgsRs!=null && row.jhgsRs!="" && row.jhgsXss!=null && row.jhgsXss!=""){
			   var jhvalue=row.jhgsRs+"人x"+row.jhgsXss+"时="+(row.jhgsRs*row.jhgsXss).toFixed(2)+"时";
			   
			   htmlContent = htmlContent + "<td class='text-left' title='"+jhvalue+"'>"+jhvalue+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>"; 
		   }
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.gzzt)+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.gzzt))+"</td>";
		   if(row.yxx==1){
			   htmlContent = htmlContent + "<td class='text-center'>有效</td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>无效</td>";
		   }
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";
		   
		   if(row.zt==9){
			   var zdjsr = row.zdjs_user?row.zdjs_user.displayName:'';
			   
			   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
			   "gdbh='"+StringUtil.escapeStr(row.gdbh)+"' "+
			   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
			   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
			   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   }
		   /*htmlContent = htmlContent + "<td class='text-left'>"+zts[row.zt]+"</td>";*/
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr_user.displayName)+"'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#JobCardlistlist").empty();
	   $("#JobCardlistlist").html(htmlContent);
	   refreshPermission();
}
//查看指定结束界面
function viewreason(jstgh,reason,jssj){
		$('#zlbh1').val(jstgh);
		$('#zdjsyy1').val(reason);
		$('#zdjssj1').val(jssj);
		$("#alertModalview").modal("show");
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
	 $("#zzjg").val(userJgdm);
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
	};
 //修改
 function edit(jobCarId,zt,dprtcode){
	 if(zt!=4 && zt!=8 && zt!=9 && zt!=10){
		 if(checkUpdMt(jobCarId)){	
			 window.location.href =basePath+"/project/jobCard/intoEditMainJobCard?id="+jobCarId+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
		 }
	 }else{
		 AlertUtil.showErrorMessage('只有保存,提交,审核驳回,批准驳回下的状态才能进行操作!');
	 }
	 
	}
//验证是否能进行修改
 function checkUpdMt(id){
 	var flag = false;
 	AjaxUtil.ajax({
 		url:basePath + "/project/jobCard/checkUpdMt",
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
//指定结束
 function shutDown(id,obj,zdjsrq,isEdit){
		var sqdh=$(obj).attr("gdbh");
		var zdjsyy=$(obj).attr("zdjsyy");
		var zdjsr=$(obj).attr("zdjsr");
 	AssignEndModal.show({
 		chinaHead:'工卡编号',//单号中文名称
 		englishHead:'W/O No.',//单号英文名称
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
 		url:basePath + "/project/jobCard/offJobCard",
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
 
 
 //作废
 function invalid(id,zt){
	 if(checkUpdMt(id)){
		 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
			 if(zt!=0){
				 //alert("只能关闭已提交状态的技术通告");
				 AlertUtil.showErrorMessage('只能作废未提交状态的定检工卡!');
				 
			 }else{
				 AjaxUtil.ajax({
					 url:basePath + "/project/jobCard/deleteJobCard",
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
			 }
			 
		 }});
	 }
 }
 //查看跳转
function viewMainJobCard(id,dprtCode){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + dprtCode+"&pageParam="+encodePageParam());
}
//查看定检项目
function selectByZxbsAndBh(djbh,dprtCode){
	AjaxUtil.ajax({
		 url:basePath + "/project/fixedcheckitem/djxmCount",
		 type:"post",
		 async: false,
		 data:{
			 'djbh' : encodeURIComponent(djbh),
			 'dprtCode' : dprtCode
		 },
		 dataType:"json",
		 success:function(data){
			if(data.state=="error"){
				AlertUtil.showErrorMessage('该定检项目已作废,请重新关联!');
			}else{
				window.open(basePath+"/project/fixedcheckitem/selectByZxbsAndBh?djbh=" + encodeURIComponent(djbh)+"&dprtCode=" + dprtCode);
			}
		 }
	 });
}
//审核
function audit(id,zt,dprtcode){
		if(zt!=1){
			AlertUtil.showErrorMessage('只能对已提交状态的数据进行审核!');
			return
		}
		window.location.href =basePath+"/project/jobCard/intoShenheMainJobCard?id=" + id+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
}
//批复
function reply(id,zt,dprtcode){
		if(zt!=2){
			AlertUtil.showErrorMessage('只能对已审核状态的数据进行批复!');
			return
		}
		window.location.href =basePath+"/project/jobCard/intoPifuMainJobCard?id=" + id+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
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
//改变组织机构时变换的制单人
function changeContent(){
	
	var dprtcode=$("#zzjg").val();
	$("#zy").empty();
	$("#zy").append("<option value='' >显示全部</option>");
	DicAndEnumUtil.registerDic('4','zy',dprtcode);
	loadWorkGroup();
	var obj={};
	 obj.jgdm=dprtcode;
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
	 			
	 		}
	 	});
}

function clickRow(index,this_){
	var $checkbox1 = $("#JobCardlistlist :checkbox[name='checkrow']:eq("+index+")");
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
	$("#JobCardlistlist").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var jobCarditem = jobCarditemData[index];
		if(jobCarditem.zt == zt ){
			idArr.push(jobCarditem.id);
			approvalArr.push(jobCarditem.gdbh);
   	 	}else{
   	 		approvalNotArr.push(jobCarditem.gdbh);
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
	var url = basePath+"/project/jobCard/updateBatchAudit";
	var message = '批量审核成功!';
	if(isApprovel){
		url = basePath+"/project/jobCard/updateBatchApprove";
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

function add(){
	window.location = basePath+"/project/jobCard/intoAddJobCard?pageParam="+encodePageParam();
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
		changeContent();
		$("#yxx").val(params.yxx);
		$("#zy").val(params.zy);
		$("#gklx").val(params.gklx);
		$("#gzzid").val(params.gzzid);
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
//生成工单a
function generateGD(id){
	window.location = basePath+"/project/workorder/intoNonRoutine?id="+id;
}