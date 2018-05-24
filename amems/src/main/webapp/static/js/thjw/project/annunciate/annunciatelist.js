var zt=[0,1,2,3,4,5,6,7,8,9,10];
var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
var jstgh="";
/*var yjtsJb1 = $("#yjtsJb1").val();
var yjtsJb2 = $("#yjtsJb2").val();
var yjtsJb3 = $("#yjtsJb3").val();*/
var choseAllIds=[];
var annunciateitemData = [];
var pagination={};
$(document).ready(function(){
		 decodePageParam();
		 //goPage(1,"auto","desc");//开始的加载默认的内容 
		 
		 refreshPermission();
			//初始化日志功能
			logModal.init({code:'B_G_002'});
	});
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var paramsMap = {};
		annunciateitemData=[];
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.zt=searchParam.zt;
		obj.dprtcode=searchParam.dprtcode;
		var tgqx=searchParam.tgqx;
		
		 if(null != tgqx && "" != tgqx){
			 var tgqxBegin = tgqx.substring(0,10)+" 00:00:00";
			 var tgqxEnd = tgqx.substring(13,23)+" 23:59:59";
			 paramsMap.tgqxBegin = tgqxBegin;
			 paramsMap.tgqxEnd = tgqxEnd;
		 }
		 obj.paramsMap=paramsMap;
		obj.zdrid=searchParam.zdrid;
		if(id != ""){
			obj.id = id;
			id = "";
		}
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/annunciate/queryAnnunciateList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   annunciateitemData=data.rows;
				   appendContentHtml(data.rows,data.monitorsettings);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
						//controller: this_
					});
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,4,5,9,11]);
			   } else {
				  $("#Annunciatelist").empty();
				  $("#pagination").empty();
				  $("#Annunciatelist").append("<tr class='text-center'><td colspan=\"13\">暂无数据 No data.</td></tr>");
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
		 searchParam.tgqx = $.trim($("#tgqx").val());
		 
		 
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

function appendContentHtml(list,monitorsettings){
	   var htmlContent = '';
	    choseAllIds=[];
	   $.each(list,function(index,row){
		   choseAllIds.push(row.id);
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\" onclick='clickRow("+index+",this)' >";
			   
		   } else {
			   htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\" onclick='clickRow("+index+",this)' >";
		   }
		   htmlContent += "<td class='text-center fixed-column' ><input type='checkbox' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" /></td>";
		    
		   
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		      
		   if(row.zt==0|| row.zt==1 || row.zt==2 || row.zt==3 || row.zt==5 || row.zt==6){
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:02' onClick=\"edit('"
				+ row.id + "',"+row.zt+",'"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==0 ||row.zt==5 ||row.zt==6){
			   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:03' onClick=\"invalid('"
			+ row.id + "',"+row.zt+")\" title='作废 Invalid '></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==3){
			   htmlContent = htmlContent +"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:04' jstgh='"+row.jstgh+"' zdjsyy='' onClick=\"shutDown('"+row.id+"', this,'',true)\" title='指定结束 End '></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==1){
			   htmlContent = htmlContent +"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:05' onClick=\"audit('"
			+ row.id + "',"+row.zt+",'"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
		   }
		   if(row.zt==2){
			   htmlContent = htmlContent +"<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:06' onClick=\"reply('"
			+ row.id + "',"+row.zt+",'"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
		   }
		   
		   htmlContent = htmlContent + "<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printAnnunciate('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;"
		   
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td class='text-center fixed-column'><a href=\"javascript:viewMainAnnunciate('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.jstgh))+"</a></td>";   
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
		   htmlContent = htmlContent + "</td>";
		  
		   htmlContent = htmlContent + "<td class='text-left'title='"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"'>"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'><a href=\"javascript:selectSend('"+row.id+"')\">"+(row.tnum?row.tnum:0)+"<font color='black'>/"+row.snum+"</font></a></td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.tgqx).substring(0,10))+"</td>";
		   if(row.syts!==null && row.syts!==""){
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.syts)+"</td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-right'>√</td>";
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zhut))+"'>"+StringUtil.escapeStr(formatUndefine(row.zhut))+"</td>";  
		   
		   if(row.zt==9){
			   var zdjsr = row.jsr_user?row.jsr_user.displayName:'';
					   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
					   "jstgh='"+StringUtil.escapeStr(row.jstgh)+"' "+
					   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
					   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
					   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   }

		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
		  /* htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.bm_dprt.dprtname)+"</td>";*/
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#Annunciatelist").empty();
	   $("#Annunciatelist").html(htmlContent);
	   
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
	 $("#zzjg").val(zzjgid);
	 $("#yjjb").val(yjts);
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
 function edit(annunctionId,zt,dprtcode){
	 if(zt==0 || zt==1 || zt==2 || zt==3|| zt==5 || zt==6){
		 //if(checkUpdMt(annunctionId)){	
			 window.location.href =basePath+"/project/annunciate/intoEditMainAnnunciate?id="+annunctionId+"&dprtcode="+dprtcode+"&pageParam="+encodePageParam();
		 //}
	 }else{
		 AlertUtil.showErrorMessage('只有保存,提交,审核驳回,批准驳回下的状态才能进行操作!');
	 }
	 
	}
//验证是否能进行修改
 function checkUpdMt(id){
 	var flag = false;
 	AjaxUtil.ajax({
 		url:basePath + "/project/annunciate/checkUpdMt",
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
		var sqdh=$(obj).attr("jstgh");
		var zdjsyy=$(obj).attr("zdjsyy");
		var zdjsr=$(obj).attr("zdjsr");
 	AssignEndModal.show({
 		chinaHead:'维护提示编号',//单号中文名称
 		englishHead:'Technical Bulletin No.',//单号英文名称
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
 		url:basePath + "/project/annunciate/offAnnunciate",
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
	 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
			 if(zt==0 || zt==5 || zt==6){
				 //alert("只能关闭已提交状态的技术通告");
				 AjaxUtil.ajax({
					 url:basePath + "/project/annunciate/deleteAnnunciate",
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 dataType:"json",
					 success:function(data){
						 if(data.state=="success"){
							 AlertUtil.showErrorMessage(data.message);
							 refreshPage();
						 }else{
							 AlertUtil.showErrorMessage(data.message);
						 }
					 }
				 });
				 
			 }else{
				 AlertUtil.showErrorMessage('只能作废未提交,审核驳回,批准驳回状态的维护提示!');
			 }
			 
	 }});
 }
 //查看跳转
function viewMainAnnunciate(id,dprtcode){
	window.open(basePath+"/project/annunciate/intoViewMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam());
}
//审核
function audit(id,zt,dprtcode){
		if(zt!=1){
			AlertUtil.showErrorMessage('只能对已提交状态的数据进行审核!');
			return
		}
		window.location.href =basePath+"/project/annunciate/intoShenheMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
}
//批复
function reply(id,zt,dprtcode){
		if(zt!=2){
			AlertUtil.showErrorMessage('只能对已审核状态的数据进行批准!');
			return
		}
		window.location.href =basePath+"/project/annunciate/intoPifuMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
}
//查看圈阅情况
function selectSend(id){
	$("#alertModalSend").modal("show");
	goPage1(1,"auto","desc",id);//开始的加载默认的内容 
	
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage1(pageNumber,sortType,sequence,id){
	AjaxGetDatasWithSearch1(pageNumber,sortType,sequence,id);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch1(pageNumber,sortType,sequence,id){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	obj.mainid=id;
	
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/project/annunciate/selectSend",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml1(data.rows);
			   var page_ = new Pagination({
					renderTo : "pagination1",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch1(a,b,c,id);
					}//,
			   });
		   } else {
			  $("#SendList").empty();
			  $("#mpagination1").empty();
			  $("#SendList").append("<tr><td colspan=\"13\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function appendContentHtml1(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.use.displayName))+"' >"+StringUtil.escapeStr(formatUndefine(row.use.displayName))+"</td>"; 
		   if(row.zt==0){
			   htmlContent = htmlContent + "<td>未接收</td>";   			   
		   }else{
			   htmlContent = htmlContent + "<td>已接收</td>";  		   
			   
		   }
		   if(row.bm==null || row.bm==""){
			   htmlContent = htmlContent + "<td class='text-left'></td>"; 
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bm.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.bm.dprtname))+"</td>"; 
		   }
		   htmlContent = htmlContent + "<td>"+formatUndefine(row.jssj)+"</td>";   
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#SendList").empty();
	   $("#SendList").html(htmlContent);
	 
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

//拼接机型工程师和制单人 列表内容
function appendChangeContent(data){
	 var appendChangeContent='';
	 appendChangeContent=appendChangeContent+"<option value=''>显示全部</option>";
	 $.each(data,function(index,row){
		 appendChangeContent=appendChangeContent+"<option value='"+row.id+"'>"+StringUtil.escapeStr(row.displayName)+"</option>";
	 });
	 $('#zdrid').html(appendChangeContent);
	 $('#jxgcs').html(appendChangeContent);
	 
}

function selectPgdBypgdh(id,dprtcode){
	 window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	
}

//只能输入数字和小数
function clearNoNum(obj) {
	// 先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	// 必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	// 保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	// 保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
}
//获取预警颜色
function getWarningColor(bgcolor,syts,zt,yjtsJb1,yjtsJb2){
	if(!(zt == 0 || zt==5 || zt==6 || zt==1 || zt==2 || zt==3)){
		return bgcolor;
	}
	
	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
		bgcolor = warningColor.level2;//黄色
	}
	if(Number(syts) <= yjtsJb1){
		bgcolor = warningColor.level1;//红色
	}
	return bgcolor;
}

function clickRow(index,this_){
	var $checkbox1 = $("#Annunciatelist :checkbox[name='checkrow']:eq("+index+")");
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
	$("#Annunciatelist").find("tr input:checked").each(function(){
		var index = $(this).attr("index");
		var annunciate = annunciateitemData[index];
		if(annunciate.zt == zt ){
			idArr.push(annunciate.id);
			approvalArr.push(annunciate.jstgh);
   	 	}else{
   	 		approvalNotArr.push(annunciate.jstgh);
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
	var url = basePath+"/project/annunciate/updateBatchAudit";
	var message = '批量审核成功!';
	if(isApprovel){
		url = basePath+"/project/annunciate/updateBatchApprove";
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
	window.location = basePath+"/project/annunciate/intoAddAnnunciate?pageParam="+encodePageParam();
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
		$("#tgqx").val(params.tgqx);
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

//打印技术通告
function printAnnunciate(id){
	//window.open(basePath+"/project/annunciate/export/pdf/"+id);
	var url=basePath+"/project/annunciate/export/pdf/"+id;
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
