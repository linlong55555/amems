var choseAllIds=[];
var chk_value_id=[];
var workorderData=[];
$(document).ready(function(){
	decodePageParam();//开始的加载默认的内容 
	Navigation(menuCode);
	DicAndEnumUtil.registerDic('4','zhuanye_search',$("#dprtcode_search").val());      //专业
	
	var dprtcode_search=$("#dprtcode_search").val();
	loadingBase(dprtcode_search);
	$("#CheckAll").click(function() { 
		 $("[name=subBox]:checkbox").each(function() { 
			 $(this).attr("checked", 'checked'); 
		 });
		 $("#CancelAll").removeAttr("checked"); 
	}); 
	
	$("#CancelAll").click(function() { 
		 $("[name=subBox]:checkbox").each(function() { 
			 $(this).removeAttr("checked"); 
		 });
		 $("#CheckAll").removeAttr("checked"); 
	}); 
	refreshPermission();       //触发按钮权限监控
	//初始化日志功能
	logModal.init({code:'WorkOrder'});
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
	params.gddlx = $.trim($("#gdlx_search").val());
	params.zxfl = $.trim($("#zxfl_search").val());
	params.zhuanye = $.trim($("#zhuanye_search").val());
	params.jd = $.trim($("#jd_search").val());
	params.zt = $.trim($("#gdzt_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.zdrq = $.trim($("#zdrq_search").val());
	
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
		$("#gdlx_search").val(params.gddlx);
		$("#zxfl_search").val(params.zxfl);
		$("#zhuanye_search").val(params.zhuanye);
		$("#jd_search").val(params.jd);
		$("#gdzt_search").val(params.zt);
		$("#dprtcode_search").val(params.dprtcode );
		$("#zdrq_search").val(params.zdrq );
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

function loadingBase(dprtcode_search){
	var obj={};
	obj.dprtcode=dprtcode_search;
	AjaxUtil.ajax({
		 url:basePath+"/productionmessage/schedule/findJd",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   $("#jd_search").empty();
			   var htmls="";
			   if(data && data.length > 0){
				   for(var i = 0; i < data.length; i++){
					   htmls +="<option value='"+data[i].id+"'>"+StringUtil.escapeStr(data[i].jdms)+"</option>";
				   }
				   $("#jd_search").append("<option value=''>查看全部</option>"+htmls);
			   }else{
				   $("#jd_search").append('<option value="">暂无基地</option>');
			   }
	      }
    });
}
function changeOrg(){
	var dprtcode_search=$("#dprtcode_search").val();
	$("#zhuanye_search").html("");
	$("#zhuanye_search").append("<option value='all' selected='true' >查看全部</option><option value=''>无</option>");
	DicAndEnumUtil.registerDic('4','zhuanye_search',dprtcode_search);      //专业
	loadingBase(dprtcode_search);
}
function  changeType(){
	var gddlx = $("#gddlx_search").val();
	if(gddlx == 1 || gddlx == 3){
		$("#gdlx_search").val("");
		$("#gdlx_search").attr("disabled","true");
	}else{
		$("#gdlx_search").removeAttr("disabled");
	}
	goPage(1,"auto","desc");
}
 //带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	workorderData = [];
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	 AjaxUtil.ajax({
		 url:basePath+"/project/workorder/queryWorkOrderList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   workorderData=data.rows;
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
			   signByKeyword($("#keyword_search").val(),[4,5,6,7,9,10,11,13,17]);
		   } else {
			  $("#list").empty();
			  $("#pagination").empty();
			  $("#list").append("<tr class='text-center'><td colspan=\"19\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'workorder_list_table'});
      },
    }); 
	
 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 
		 var keyword = $.trim($("#keyword_search").val());
		 
		 var gddlx = $.trim($("#gddlx_search").val());
		 var gdlx = $.trim($("#gdlx_search").val());
		 var zxfl = $.trim($("#zxfl_search").val());
		 var zhuanye = $.trim($("#zhuanye_search").val());
		 var jd=$.trim($("#jd_search").val());
		 var zt = $.trim($("#gdzt_search").val());
		 var dprtcode = $.trim($("#dprtcode_search").val());
		 var zdrq = $.trim($("#zdrq_search").val());
		 if(null != zdrq && "" != zdrq){
				paramsMap.dateBegin = zdrq.substring(0,4)+"-"+zdrq.substring(5,7)+"-"+zdrq.substring(8,10);
				paramsMap.dateEnd = zdrq.substring(12,17)+"-"+zdrq.substring(18,20)+"-"+zdrq.substring(21,23);
		 }
		 
		 paramsMap.keyword = keyword;
		 paramsMap.gddlx = gddlx;
		 paramsMap.gdlx = gdlx;
		 paramsMap.zxfl = zxfl;
		 if(zhuanye!="all"){
			 paramsMap.zhuanye = zhuanye;
		 }
		 paramsMap.jd = jd;
		 paramsMap.zt = zt;
		 paramsMap.dprtcode=dprtcode;
		 
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
 function appendContentHtml(list){
   choseAllIds=[];
   var htmlContent = '';
   var jgdm= $("#zzjgid").val();
   $.each(list,function(index,row){
	   choseAllIds.push(row.id);
	   htmlContent = htmlContent + "<tr  onclick=\"chooesRow1('"+index+"')\" >";
	   htmlContent = htmlContent + "<td class='text-center fixed-column' >";
	   htmlContent = htmlContent +"<input type='checkbox' value='"+row.id+"' name='subBox' id='subBox' index='"+index+"' onclick='chooesRow2(this)'/>" +
							   		"<input type='hidden' value='"+row.zt+"'  />"+
							   		"<input type='hidden' value='"+row.gdbh+"'  />";
	   htmlContent = htmlContent + "</td>";
	   
	   if(row.zt==1||row.zt==2||row.zt==3){
		   if(row.gddlx==2&&row.gdlx!=1){
			   htmlContent = htmlContent + "<td class='text-center fixed-column'>"
			   + "<i class='icon-edit  color-blue cursor-pointer checkPermission' permissioncode='project:workorder:main:02,project:workorder:main1:02' onClick=\"edit('"+ row.id + "','"+row.gddlx+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
			   if(row.zt==1){
				   htmlContent = htmlContent + "<i class='icon-foursquare color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:05,project:workorder:main1:05' onClick=\"audit('"+ row.id + "','"+row.gddlx+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
			   }else if(row.zt==2){
				   htmlContent = htmlContent + "<i class='icon-check color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:06,project:workorder:main1:06'  onClick=\"reply('"+ row.id + "','"+row.gddlx+"')\" title='批准  Approve'></i>&nbsp;&nbsp;";
			   }else{
				   htmlContent = htmlContent + "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:workorder:main:04,project:workorder:main1:04' username='"+StringUtil.escapeStr(row.username)+"' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' onClick=\"end('"+ row.id + "',"+row.gddlx+",this,"+row.gdlx+")\" title='指定结束 End'></i>&nbsp;&nbsp;"; 
			   }
			   if(jgdm==row.dprtcode){
				   htmlContent = htmlContent+"<i class='icon-copy color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:09,project:workorder:main1:09' onClick=\"copy('"+ row.id + "',"+row.gddlx+")\" title='复制 Copy'></i>&nbsp;&nbsp;";
			   }
		   }else{
			   htmlContent = htmlContent +  "<td class='text-center fixed-column'>";
			   if(row.zt==1){
				   htmlContent = htmlContent + "<i class='icon-foursquare color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:05,project:workorder:main1:05' onClick=\"audit('"+ row.id + "','"+row.gddlx+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
			   }else if(row.zt==2){
				   htmlContent = htmlContent + "<i class='icon-check color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:06,project:workorder:main1:06'  onClick=\"reply('"+ row.id + "','"+row.gddlx+"')\" title='批准  Approve'></i>&nbsp;&nbsp;";
			   }else{
				   htmlContent = htmlContent + "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:workorder:main:04,project:workorder:main1:04' username='"+StringUtil.escapeStr(row.username)+"' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' onClick=\"end('"+ row.id + "',"+row.gddlx+",this,"+row.gdlx+")\" title='指定结束 End'></i>&nbsp;&nbsp;"; 
			   }
			   if(jgdm==row.dprtcode){
				   htmlContent = htmlContent+"<i class='icon-copy color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:09,project:workorder:main1:09' onClick=\"copy('"+ row.id + "',"+row.gddlx+")\" title='复制 Copy'></i>&nbsp;&nbsp;";
			   }
		   }
		   if(row.gddlx==2){
		    	htmlContent = htmlContent+"<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printNonroutine('"+ row.id + "')\" title='打印非例行工单  Print Non-Routine W/O'></i>&nbsp;&nbsp;";
		   }else{
		    	htmlContent = htmlContent+ "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:07,project:workorder:main1:07' onClick=\"print('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
		   }
		   +"</td>"; 
	   }
	   if(row.zt==0||row.zt==5||row.zt==6){
		   htmlContent = htmlContent + 
		   "<td class='text-center fixed-column'>"
		    + "<i class='icon-edit  color-blue cursor-pointer checkPermission' permissioncode='project:workorder:main:02,project:workorder:main1:02' onClick=\"edit('"+ row.id + "','"+row.gddlx+"')\" title='修改 Edit'></i>&nbsp;&nbsp;"
		    + "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:workorder:main:03,project:workorder:main1:03' onClick=\"cancel('"+ row.id + "','"+row.gddlx+"')\" title='作废 Invalid'></i>&nbsp;&nbsp;";
		    if(jgdm==row.dprtcode){
				   htmlContent = htmlContent+"<i class='icon-copy color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:09,project:workorder:main1:09' onClick=\"copy('"+ row.id + "',"+row.gddlx+")\" title='复制 Copy'></i>&nbsp;&nbsp;";
			}
		    if(row.gddlx==2){
		    	htmlContent = htmlContent+"<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printNonroutine('"+ row.id + "')\" title='打印非例行工单  Print Non-Routine W/O'></i>&nbsp;&nbsp;";
		    }else{
		    	htmlContent = htmlContent+ "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:07,project:workorder:main1:07' onClick=\"print('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
		    }
		    +"</td>";
	   }
	   if(row.zt==4){					//批准中止关闭
		   htmlContent = htmlContent +  "<td class='text-center fixed-column'>";
		    if(jgdm==row.dprtcode){
				   htmlContent = htmlContent+"<i class='icon-copy color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:09,project:workorder:main1:09' onClick=\"copy('"+ row.id + "',"+row.gddlx+")\" title='复制 Copy'></i>&nbsp;&nbsp;";
			}
		    if(row.gddlx==2){
		    	htmlContent = htmlContent+"<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printNonroutine('"+ row.id + "')\" title='打印非例行工单  Print Non-Routine W/O'></i>&nbsp;&nbsp;";
		    }else{
		    	htmlContent = htmlContent+ "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:07,project:workorder:main1:07' onClick=\"print('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
		    }
		    +"</td>"; 
	   }
	   if(row.zt==9||row.zt==10){              //完成
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		   if(jgdm==row.dprtcode){
			   htmlContent = htmlContent+"<i class='icon-copy color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:09,project:workorder:main1:09' onClick=\"copy('"+ row.id + "',"+row.gddlx+")\" title='复制 Copy'></i>&nbsp;&nbsp;";
		   }
		   if(row.gddlx==2){
		       htmlContent = htmlContent+"<i class='icon-print color-blue cursor-pointer '  permissioncode='aerialmaterial:qualityCheck:main:02' onClick=\"printNonroutine('"+ row.id + "')\" title='打印非例行工单  Print Non-Routine W/O'></i>&nbsp;&nbsp;";
		       +"</td>"; 
		   }else{
		       htmlContent = htmlContent+ "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='project:workorder:main:07,project:workorder:main1:07' onClick=\"print('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
		       +"</td>"; 
		   } 
	   }
	   if(row.gddlx==2){
		   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.gdlx)+"</td>";  
	   }else{
		   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+"</td>";  
	   }  
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.gdbh)+"'><a href=\"javascript:Looked('"+ row.id + "',"+row.gddlx+")\">"+StringUtil.escapeStr(row.gdbh)+"</a></td>";  
	   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";
	   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xfgdyy)+"'>"+StringUtil.escapeStr(row.xfgdyy)+"</td>";
	   htmlContent = htmlContent + "<td class='text-left'>";
	   if(row.pgdid!=null&&row.pgdid!=""){
		   var strs= new Array(); //定义一数组
		   strs= row.pgdid.split(","); //字符分割
		   for (var i=0;i<strs.length ;i++ ){
			   var pgdh=strs[i].split("@")[0];
			   var pgdid=strs[i].split("@")[1];
			   if(i==1){
				   htmlContent = htmlContent +"　<i class='icon-caret-down' id='"+row.id+"icon' onclick=vieworhideWorkContent('"+row.id+"')></i><div id='"+row.id+"' style='display:none'>"; 
			   }
			   htmlContent = htmlContent +"<a href=\"javascript:selectPgdBypgdh('"+pgdid+"','"+row.dprtcode+"')\">"+pgdh+"</a>";
			   if(i!=0){
				   htmlContent = htmlContent +"<br>";
			   }
			   if(i!=0 && i==strs.length-1)  {
				   htmlContent = htmlContent +"</div>"; 
			   }
		   } 
	   }
	   
	   htmlContent = htmlContent + "</td>";
	   if(row.zxfl=="FZJJ"){
		   htmlContent = htmlContent + "<td>非装机件</td>";  
	   }else if(row.zxfl=="ZJJ"){
		   htmlContent = htmlContent + "<td>飞机部件</td>"; 
	   }else{
		   htmlContent = htmlContent + "<td>机身</td>";  
	   }
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjxlh)+"'>"+StringUtil.escapeStr(row.bjxlh)+"</td>";
	   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.zhuanye)+"</td>";
	   if(StringUtil.escapeStr(row.zjh)!="" && StringUtil.escapeStr(row.zwms) !="" ){
		   htmlContent = htmlContent + "<td title='"+formatUndefine(StringUtil.escapeStr(row.zjh)+' '+StringUtil.escapeStr(row.zwms))+"'>"+StringUtil.escapeStr(row.zjh)+' '+StringUtil.escapeStr(row.zwms)+"</td>";
	   }else{
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.zjh)+' '+StringUtil.escapeStr(row.zwms)+"</td>";
	   }
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.jdms)+"'>"+StringUtil.escapeStr(row.jdms)+"</td>";
	   if(row.zt!=9){
		   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('workOrderStateEnum',row.zt)+"</td>";
	   }else{
		   htmlContent = htmlContent + "<td class='text-left' >";
		   htmlContent = htmlContent +"<a href='javascript:void(0);' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' displayname='"+StringUtil.escapeStr(row.displayname)+"' onclick=\"javascript:ZdjsReson(this,'"+row.zdjsrq+"')\">指定结束</a><br>"; 
		   htmlContent = htmlContent + "</td>";
	   }
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.displaygzz)+"'>"+StringUtil.escapeStr(row.displaygzz)+"</td>";
	   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";
	   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
	   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.dprtname)+"</td>";
	   htmlContent = htmlContent + "</tr>";  
   });
   $("#list").empty();
   $("#list").html(htmlContent);
   refreshPermission();       //触发按钮权限监控
 }
     //查看评估单
	 function selectPgdBypgdh(id,dprtcode){
	 	 window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	 }
	 
	 //查看指定结束原因
	 function ZdjsReson(obj,zdjsrq){
		 $('#alertModalZdjsReson').modal('show');                
		 $("#gdbh2").val($(obj).attr("gdbh"));
	     $("#zdjsyy2").val($(obj).attr("zdjsyy"));
	     $("#zdjsrq").val(zdjsrq);
	     $("#zdjsr").val($(obj).attr("displayname"));
	 }
	 function addNon(){
		window.location = basePath+"/project/workorder/intoNonRoutine?pageParam="+encodePageParam();
	 }
	 function addEo(){
		 window.location = basePath+"/project/workorder/intoEO?pageParam="+encodePageParam();
	 }
	 //修改工单
	function edit(id,gddlx) {                     //id,类型，制单人，工单基础id，工单编号，状态
	    window.location.href =basePath+"/project/workorder/intoEdit?id=" + id+"&gddlx="+gddlx +"&pageParam="+encodePageParam();
	}
	//复制工单
	function copy(id,gddlx) {
	    window.location.href =basePath+"/project/workorder/intoCopy?id=" + id+"&gddlx="+gddlx +"&pageParam="+encodePageParam();
	}	
	 //查看工单
	function Looked(id,gddlx) {                    
		 window.open(basePath+"/project/workorder/LookedWo?id=" + id+"&gddlx="+gddlx);
	}
	//审核
	 function audit(id,gddlx){
	     window.location.href =basePath+"/project/workorder/auditWo?id=" + id+"&gddlx="+gddlx +"&pageParam="+encodePageParam();
	 }
	 
	 function checkRow(index,this_){
		$("#list :checkbox[name=subBox]:eq("+index+")").attr("checked", $(this_).is(":checked"));
      }
	 
	 //批准
	 function reply(id,gddlx){
 		window.location.href =basePath+"/project/workorder/replyWo?id=" + id+"&gddlx="+gddlx +"&pageParam="+encodePageParam();
	 }
	 //指定结束工单
	function end(id,gddlx,obj,gdlx) {
		AjaxUtil.ajax({
			  url:basePath+'/productionplan/plantask/alreadyCompleted',
			  type : "post",
			  data : {id:id,idSource:gddlx},
			  dataType : "json",
			  async: false,
			  cache:false,
			  success:function(data) {
				  if(data.alreadyCompleted){
					  AlertUtil.showConfirmMessage("该任务已完工，您确定指定结束工单吗？",{callback: function(){
							$("#id").val(id);                                                                        //将工单参数赋值
							$("#gdbh").val($(obj).attr("gdbh"));
							$("#gddlx").val(gddlx);
							$("#gdlx").val(gdlx);
							$("#zdjsyy").val("");
							$("#alertModalZdjs").modal("show");
					  }});
				  }	else{
					    $("#id").val(id);                                                                        //将工单参数赋值
						$("#gdbh").val($(obj).attr("gdbh"));
						$("#gddlx").val(gddlx);
						$("#gdlx").val(gdlx);
						$("#zdjsyy").val("");
						$("#alertModalZdjs").modal("show");
				  }
			  }
	    }); 	  
	}
	function zdjsOver(){
		 //参数组装
		  var obj = {};
		  obj.id = $.trim($("#id").val());				                                  //主键id
	      obj.gddlx = $.trim($("#gddlx").val());	
	      obj.gdlx = $.trim($("#gdlx").val());	
	      obj.zdjsyy =  $.trim($("#zdjsyy").val());						  
	      obj.zt=9;
		      
	  	  //判断指定结束原因
	  	  var zdjsyy = $.trim($("#zdjsyy").val());
	  	  if (zdjsyy == "") {
	  		  $('#alertErrorModalBody').html("指定结束原因不能为空");
			      $('#alertErrorModal').modal('show');
	  		  return false;
	  	  }
	  	startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/workorder/Over",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				if(data!=-1){
					finishWait();
					AlertUtil.showMessage('指定结束成功!');
					id=obj.id;
					refreshPage();
				}else{
					finishWait();
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
				}
			},
		});
	}
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}
	 //作废工单
	function cancel(id,gddlx) {
		$("#id").val(id);                                                                        //将工单参数赋值
		$("#gddlx").val(gddlx);
		 //参数组装
		  var obj = {};
		  obj.id = $.trim($("#id").val());				                                  //主键id
	      obj.gddlx = $.trim($("#gddlx").val());		                   
	      obj.zt=8;
		  startWait();
			// 提交数据
		  AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
			AjaxUtil.ajax({
				url:basePath+"/project/workorder/Cancel",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					if(data!=-1){
						finishWait();
						AlertUtil.showMessage('作废成功!');
						searchRevision();
					}else{
						finishWait();
						AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					}
				},
			});
		  }});
	}
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "asc";
		} else {
			orderType = "desc";
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderType);
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
		$("#gdlx_search").selected="selected";
	});
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	});
	 $("#zilei").find("select").each(function(){
			$(this).val("");
	});
	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
	 $("#keyword_search").val("");
	 $("#zhuanye_search").val("all");
	 $("#dprtcode_search").val(zzjgid);
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
		 
		 function vieworhideWorkContent(id){
			 var flag = $("#"+id).is(":hidden");
			 if(flag){
				 $("#"+id).fadeIn(500);
				 $("#"+id+'icon').removeClass("icon-caret-down");
				 $("#"+id+'icon').addClass("icon-caret-up");
			 }else{
				 $("#"+id).hide();
				 $("#"+id+'icon').removeClass("icon-caret-up");
				 $("#"+id+'icon').addClass("icon-caret-down");
			 }
			 new Sticky({tableId:'workorder_list_table'});
			 
		 }
		 function vieworhideWorkContentAll(){
			 var obj = $("th[name='glpgdh']");
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
			 new Sticky({tableId:'workorder_list_table'});
		 }
		//打开批量审核批准对话框
		 function batchApproveWin(isApprovel){
		 	var idArr = [];
		 	var approvalArr = [];
		 	var approvalNotArr = [];
		 	
		 	var zt = isApprovel?2:1;
		 	$("#list").find("tr input:checked").each(function(){
		 		var index = $(this).attr("index");
		 		var workorder = workorderData[index];
		 		if(workorder.zt == zt){
		 			idArr.push(workorder.id);
		 			approvalArr.push(workorder.gdbh);
		    	 	}else{
		    	 		approvalNotArr.push(workorder.gdbh);
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
			var obj={};
		 	var url = basePath+"/project/workorder/batchReview";
		 	obj.shyj = data.yj;
		 	if(isApprovel){
		 		obj.shyj = "";
		 		obj.pfyj = data.yj;
		 		url = basePath+"/project/workorder/batchApprove";
		 	}
		 	obj.ids=idList;
		 	startWait();
		 	// 提交数据
		 	AjaxUtil.ajax({
		 		url:url,
		 		type:"post",
		 		contentType:"application/json;charset=utf-8",
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		success:function(message){
		 			finishWait();
		 			AlertUtil.showMessage(message);
		 			searchRevision();
		 		}
		 	});
		 }
		 function chooesRow2(obj){
			 chooesRowPGD($(obj));
		 }
		 function chooesRow1(index){
			var $checkbox1 = $("#workorder_list_table :checkbox[name='subBox']:eq("+index+")");
			var $checkbox2 = $(".sticky-col :checkbox[name='subBox']:eq("+index+")");
			var checked = $checkbox1.is(":checked");
			$checkbox1.attr("checked", !checked);
			$checkbox2.attr("checked", !checked);
			
		 }

		// 行选
		function chooesRowPGD(obj){
			var flag = obj.is(":checked");
			if(flag){
				obj.removeAttr("checked");
			}else{
				obj.attr("checked",true);
			}
			
		}
		function checkAll(){
			$("[name=subBox]:checkbox").each(function() { 
				 $(this).attr("checked", 'checked'); 
			 });
			 
		}
		function notCheckAll(){
			 $("[name=subBox]:checkbox").each(function() { 
				 $(this).removeAttr("checked"); 
			 });
		}
 
		//打印非例行工卡
		function printNonroutine(id){
			//window.open( basePath+"/project/workorder/nonroutine/export/pdf/"+id);
			
			var url=basePath+"/project/workorder/nonroutine/export/pdf/"+id;
			window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');
			
			
		}