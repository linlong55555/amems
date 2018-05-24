var itemData = [];
var cids=[];
var listgw=[];
var listgx=[];
var kcbhStr = '';
$(document).ready(function(){

		//加载岗位信息
		 goPagegw();
			//初始化日志功能
			//logModal.init({code:'B_G_002'});
	});
//切换组织机构
function changeDprtcode(){
	$("#gwid").val("");
	goPagegw();
}
function goPagegw(){
	var obj ={};
	obj.dprtcode=$("#dprtcode").val();
	
	startWait();
	AjaxUtil.ajax({
		   url:basePath+"/training/programme/main",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
		    if(data.rows.length>0){
		    	listgw=data.rows;
		    	appendgw(data.rows);
		    	queryAllpageList();
		    }else{
		    	$("#gwList").empty();
		    	$("#gwList").append("<li class='list-group-item' style='border-radius:0px;border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;background:#ececec;font-weight:bold;border-left:0px;height:30px;'>" +
					 		"<div class='text-left border-left-0 col-xs-5' >" +
					 		"岗位编号</div><div class='text-left col-xs-5'  >" +
					 		"岗位名称</div><div class='text-left col-xs-2' >" +
					 		"</div><div class='clearfix'></div></li><li class='list-group-item'>暂无数据</li>");
		    	
		    }
		    $("#ryList").empty();
	    	$("#ryList").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
	    	$("#kcList").empty();
	    	$("#kcList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
	    	
	    	 //加载课程信息和人员信息和岗位需求信息
			 queryAllpageList();
			 //获取课程编号
			 selectKcbh();
	     }
	   });
}

function appendgw(list){
	 var htmlContent = "<li class='list-group-item' style='border-radius:0px;border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;background:#ececec;font-weight:bold;border-left:0px;height:30px;'>" +
		"<div class='text-left border-left-0 col-xs-5' >" +
		"岗位编号</div><div class='text-left col-xs-5'  >" +
		"岗位名称</div><div class='text-left col-xs-2' >" +
		"</div><div class='clearfix'></div></li>";
	   $.each(list,function(index,row){
		   if(index==0 && $("#gwid").val()==""){
			   htmlContent += "<li class='list-group-item active' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;'>" +
			   "<div class='text-left border-left-0 col-xs-4' style='padding-left:3px;padding-right:3px;' title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
			   $("#gwid").val(row.id);  
		   }else if($("#gwid").val()== row.id){
			   htmlContent += "<li class='list-group-item active' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;'>" +
			   "<div class='text-left border-left-0 col-xs-4' style='padding-left:3px;padding-right:3px;'  title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
		   }else{
			   htmlContent += "<li class='list-group-item' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;'>" +
			   "<div class='text-left border-left-0 col-xs-4' style='padding-left:3px;padding-right:3px;'   title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
		   }
		   
		   htmlContent += StringUtil.escapeStr(substring026(formatUndefine(row.dgbh)));
		   htmlContent += "</div>";
		   htmlContent += "<div class='text-left col-xs-7'  style='padding-left:3px;padding-right:3px;'  title='"+StringUtil.escapeStr(formatUndefine(row.dgmc))+"' >";
		   htmlContent += StringUtil.escapeStr(formatUndefine(substring028(row.dgmc)));
		   htmlContent += "</div>";
		   htmlContent += "<div class='text-right col-xs-1' style='padding-left:3px;padding-right:3px;' title='"+row.paramsMap.kcs+"' >";
		   htmlContent += row.paramsMap.kcs;
		   htmlContent += "</div>";
		   htmlContent += "<div class='clearfix'></div></li>";
	   });
	   
	  $("#gwList").empty();
	   $("#gwList").append(htmlContent);
	   $("#gwList li:first-child").click();   //加载初始化
}

/*function appendgw(list){
	var htmlContent = "<li class='list-group-item' style='border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;background:#ececec;font-weight:bold;border-top-right-radius:5px;border-top-left-radius:5px;border-left:0px;height:30px;'>" +
	"<div class='text-left border-left-0 col-xs-4' >" +
	"岗位编号</div><div class='text-left col-xs-4'  >" +
	"岗位名称</div><div class='text-left col-xs-4' >" +
	"</div><div class='clearfix'></div></li>";
	$.each(list,function(index,row){
		if(index==0 && $("#gwid").val()==""){
			htmlContent += "<li class='list-group-item active' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;border:1px solid red;padding-left:0px;padding-right:0px;'>" +
			"<div class='text-left border-left-0 col-xs-4' style='margin-left:0px;height:30px;overflow:hidden;border:1px solid red;' title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
			$("#gwid").val(row.id);  
		}else if($("#gwid").val()== row.id){
			htmlContent += "<li class='list-group-item active' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;'>" +
			"<div class='text-left border-left-0 col-xs-4' style='height:30px;overflow:hidden;border:1px solid red;'  title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
		}else{
			htmlContent += "<li class='list-group-item' onclick='queryAllkcAndmx(this)' mcid='"+row.id+"' style='height:30px;'>" +
			"<div class='text-left border-left-0 col-xs-4' style='height:30px;overflow:hidden;border:1px solid red;'   title='"+StringUtil.escapeStr(formatUndefine(row.dgbh))+"' >";
		}
		
		htmlContent += StringUtil.escapeStr(substring026(formatUndefine(row.dgbh)));
		htmlContent += "</div>";
		htmlContent += "<div class='text-left col-xs-7' style='height:30px;overflow:hidden;border:1px solid red;'  title='"+StringUtil.escapeStr(formatUndefine(row.dgmc))+"' >";
		htmlContent += StringUtil.escapeStr(formatUndefine(substring028(row.dgmc)));
		htmlContent += "</div>";
		htmlContent += "<div class='text-right col-xs-1' style='height:30px;overflow:hidden;border:1px solid red;' title='"+row.paramsMap.kcs+"' >";
		htmlContent += row.paramsMap.kcs;
		htmlContent += "</div>";
		htmlContent += "<div class='clearfix'></div></li>";
	});
	
	$("#gwList").empty();
	$("#gwList").append(htmlContent);
	$("#gwList li:first-child").click();   //加载初始化
}
*/
function queryAllkcAndmx(obj){
	 $("#gwList li.active").removeClass("active");
	 $(obj).addClass("active");
	 var mcid=$(obj).attr("mcid");
	 $("#gwid").val(mcid);
	 queryAllpageList();
}

function queryAllpageList(){
	var obj = {
			'id' : $("#gwid").val(),
			 'dprtcode' : $("#dprtcode").val()
	};
	AjaxUtil.ajax({
		url:basePath+"/training/programme/list",
		 type:"post",
		 async: true,
		 data:JSON.stringify(obj),
		 contentType:"application/json;charset=utf-8",
		 dataType:"json",
		 success:function(data){
			 cids=[];  
			 if(data.kcList.length>0 && $("#gwid").val() !=''){
				 appendkc(data.kcList);
			 }else{
				 $("#kcList").empty();
			     $("#kcList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
			 }
			 if(data.ryList.length>0 && $("#gwid").val() !=''){
				 appendry(data.ryList);
			 }else{
				 $("#ryList").empty();
			     $("#ryList").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
			 }
			 if(data.gxList.length>0 && $("#gwid").val() !=''){
				 appendgx(data.gxList);
			 }else{
				 $("#gxList").empty();
			     $("#gxList").append("<tr class='text-center'><td colspan=\"2\">暂无数据 No data.</td></tr>");
			 }
		 }
	 });
}

function appendry(list){
	var htmlContent='';
	$.each(list,function(index,row){
		 if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		 
		htmlContent = htmlContent + "<td class='text-center'>";
		htmlContent = htmlContent +"<i class='icon-eye-open color-blue cursor-pointer' onClick=\"view('"
		  + row.wxrydaid + "')\" title='查看 View '></i>";
		htmlContent = htmlContent + "</td>";  
		htmlContent = htmlContent + "<td class='text-left'><a href=\"javascript:viewUser('"+row.wxrydaid+"')\">"+StringUtil.escapeStr(row.maintenancePersonnel.rybh)+"</a></td>";  
	    htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.maintenancePersonnel.xm)+"' >"+StringUtil.escapeStr(row.maintenancePersonnel.xm)+"</td>"; 
	    htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"' >"+StringUtil.escapeStr(row.fjjx)+"</td>"; 
	    htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.lb)+"' >"+StringUtil.escapeStr(row.lb)+"</td>"; 
	    htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(row.ksrq?row.ksrq.substring(0,10):'')+"' >"+StringUtil.escapeStr(row.ksrq?row.ksrq.substring(0,10):'')+"</td>"; 
	    htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(row.jzrq?row.jzrq.substring(0,10):'')+"' >"+StringUtil.escapeStr(row.jzrq?row.jzrq.substring(0,10):'')+"</td>"; 
	    
	    if(row.wbbs=="2"){
	    	
	    	htmlContent = htmlContent + "<td class='text-left' title='"+(StringUtil.escapeStr(row.maintenancePersonnel.szdw)?StringUtil.escapeStr(row.maintenancePersonnel.szdw):"外部人员")+"'>"+(StringUtil.escapeStr(row.maintenancePersonnel.szdw)?StringUtil.escapeStr(row.maintenancePersonnel.szdw):"外部人员")+"</td>";  
	    }else{
	    	htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.maintenancePersonnel.szdw)+"' >"+StringUtil.escapeStr(row.maintenancePersonnel.szdw)+"</td>";  
	    }
	    
	    
		 
	   });
	 $("#ryList").empty();
	 $("#ryList").html(htmlContent);
	 
}

function viewUser(id){
	window.open(basePath+"/quality/maintenancepersonnel/view/" + id);
}

function appendkc(list){
	var htmlContent='';
	$.each(list,function(index,row){
		 if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		htmlContent = htmlContent + "<td class='text-center'>";
		htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:programme:main:02' onclick=\"edit('"
					  		+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='training:programme:main:03' onClick=\"invalid('"
								  + row.id + "')\" title='删除 Delete '></i>&nbsp;&nbsp;";
		htmlContent = htmlContent + "</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.kcid)+"' ><a href=\"javascript:kcView('"+row.kcid+"')\">"+StringUtil.escapeStr(row.kcid)+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.kcmc)+"' >"+StringUtil.escapeStr(row.kcmc)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jyyq)+"' >"+StringUtil.escapeStr(row.jyyq)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.pxjg)+"' >"+StringUtil.escapeStr(row.pxjg)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ly)+"' >"+StringUtil.escapeStr(row.ly)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jctg)+"' >"+StringUtil.escapeStr(row.jctg)+"</td>";
		htmlContent += "</tr>"; 
		
		cids.push(row.id);
	   });
	 $("#kcList").empty();
	 $("#kcList").html(htmlContent);
	 refreshPermission(); 
}

function kcView(kcbh){
	$("#viewKcbh").val(kcbh);
	$("#ViewAlertModal").modal("show");
	goPage(1,"auto","desc");
}

function appendgx(list){
	var htmlContent='';
	$.each(list,function(index,row){
		 if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		htmlContent = htmlContent + "<td class='text-center'>";
		htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:programme:main:05' " +
					  "id='"+StringUtil.escapeStr(row.id)+"' mainid='"+StringUtil.escapeStr(row.mainid)+"' xc='"+StringUtil.escapeStr(row.xc)+"' " +
					  "gwyq='"+StringUtil.escapeStr(row.gwyq)+"' zt='"+StringUtil.escapeStr(row.zt)+"' whbmid='"+StringUtil.escapeStr(row.whbmid)+"' " +
					  "whrid='"+StringUtil.escapeStr(row.whrid)+"' whsj='" +row.whsj+"'"+
					  		"onClick=\"editWorkRequire(this)\" title='修改 Edit'></i>&nbsp;&nbsp;";
		htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='training:programme:main:06' onClick=\"invalidWork('"
								  + row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
		htmlContent = htmlContent + "</td>";  
		htmlContent += "<td title='"+StringUtil.escapeStr(row.gwyq)+"' class='text-left'>"+StringUtil.escapeStr(row.gwyq)+"</td>"; 		
		listgx.push(row.id);
	   });
	 $("#gxList").empty();
	 $("#gxList").html(htmlContent);
	 refreshPermission(); 
	
	
}

function add(){
	if($("#gwid").val()=="" || $("#gwid").val()==null ){
		AlertUtil.showErrorMessage('请先选择岗位');
		return false;
	}
	/* $("#EditalertModal").find("input").each(function() {
  		$(this).attr("value", "");
  	});*/
	/*goPage(1,"auto","desc");//开始的加载默认的内容 */
	
	/*$("#AddalertModal").modal("show");
	$("#courselist").empty();
	$("#courselist").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");*/
	$("#EditalertModal").modal("show");
	$("#gw_kc_chName",$("#EditalertModal")).html("新增");
	$("#gw_kc_enName",$("#EditalertModal")).html("Add");
	inintModal();
	$("#kcbh").attr("disabled",false);
	 
	
}

function inintModal(){
	//清空
	$("#kcbh").val("");
	$("#EditalertModal").find("input").each(function() {
		$(this).attr("value", "");
	});
}
function selectKcbh(){
	var obj ={};
	obj.dprtcode = $("#dprtcode").val();
	AjaxUtil.ajax({
		   url:basePath+"/training/programme/selectDistinctKcbh",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   var kcbhDiv ="<select class='form-control' id='kcbh' onchange='kcChange(this)' >";
			   kcbhDiv +="<option value=''>请选择</option>";
			   kcbhStr ="";
			   if(data.rows != null && data.rows.length > 0){
				   kcbhStr +="<select class='form-control' name='kcbh' >";
				   $.each(data.rows,function(index,row){
					   kcbhStr +="<option value='"+row.kcbh+"' >";
					   kcbhDiv +="<option value='"+row.kcbh+"'>";
					   kcbhStr +=row.kcbh;
					   kcbhDiv +=row.kcbh;
					   kcbhStr +="</option>";
					   kcbhDiv +="</option>";
				   });
				   kcbhStr +="</select>";
			   }
			   kcbhDiv +="</select>";
			   $("#kcbhDiv").html(kcbhDiv);
		    finishWait();
	    	
	     }
	   });
}

//课程的改变事件
function kcChange(obj){
	var kcbh = $(obj).val();
	if(kcbh =="" || kcbh == null){
		return false;
	}
	var obj ={};
		obj.kcbh = kcbh;
		obj.dprtcode = $("#dprtcode").val();
		
	AjaxUtil.ajax({
		   url:basePath+"/training/course/selectByKcbm",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			  if(data != null){
				  $("#kcbh").val(formatUndefine(data.kcbh));
				  $("#jyyq").val(formatUndefine(data.jyyq));
				  $("#pxjg").val(formatUndefine(data.pxjg));
				  $("#ly").val(formatUndefine(data.ly));
				  $("#jctg").val(formatUndefine(data.jctg));
			  }
			   
		    finishWait();
	    	
	     }
	   });
	
}

//添加岗位要求
function addWorkRequires(){
	var gwidValue=$("#gwid").val();
	  if(!gwidValue){
		  AlertUtil.showErrorMessage("请先选择岗位!");
		    return;
	  }
   goPage(1,"auto","desc");
   $("#AddalertWorkModal").find("input,textarea").val("");
   $("#gw_kc_chName").html("新增");
   $("gw_kc_egName").html("Add");
   $("#AddalertWorkModal").modal("show");
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
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 var paramsMap={};
	 if(cids.length > 0){
		 paramsMap.idList = cids;
	 }
	 var keyword = $.trim($("#keyword_Customer_search").val());
	 searchParam.dprtcode = $.trim($("#dprtcode").val());
	 searchParam.keyword =keyword;
	 searchParam.paramsMap =paramsMap;
	 return searchParam;
}
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		searchParam.kcbh =$("#viewKcbh").val();
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		url = basePath+"/training/course/queryAllPageList";
		
		startWait();
		AjaxUtil.ajax({
			 url:url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
		    
		    itemData=data.rows;
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
				   signByKeyword($("#keyword_Customer_search").val(),[1,2,3,5,7,8,12,13,14],"#course_list tr td");
			   } else {
				  $("#course_list").empty();
				  $("#pagination").empty();
				  $("#course_list").append("<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'course_main_table'});
	      }
	    }); 
			
		 }
	//信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
	 function appendContentHtml(list){
		 
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" >";
			   } else {
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
			   }
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kclb)+"' class='text-left'>"+StringUtil.escapeStr(row.kclb)+"</td>"; 
			   htmlContent += "<td class='text-left'><a href=\"javascript:openKcView('"+row.id+"')\">";
			   htmlContent += StringUtil.escapeStr(row.kcbh);
			   htmlContent += "</a></td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcmc)+"' class='text-left'>"+StringUtil.escapeStr(row.kcmc)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxmb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxmb)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ks)+"' class='text-right'>"+StringUtil.escapeStr(row.ks)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
			   htmlContent += "<td class='text-center'>"+(row.isFx == 1?"是":"否")+"</td>";
			   var fxjg = formatUndefine(row.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',row.zqdw);
			   htmlContent += "<td title='"+fxjg+"' class='text-left'>"+fxjg+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxks)+"' class='text-right'>"+StringUtil.escapeStr(row.fxks)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxpxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxpxxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxksxs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
//			   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcnr)+"' class='text-left'>"+StringUtil.escapeStr(row.kcnr)+"</td>";
//			   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxmb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxmb)+"</td>";
//			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
//			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#course_list").empty();
		   $("#course_list").html(htmlContent);
		   refreshPermission();
	 }
	//全选
	 function performSelectAll(){
	 	$(":checkbox[name=checkrow]").attr("checked", true);
	 }
	 //取消全选
	 function performSelectClear(){
	 	$(":checkbox[name=checkrow]").attr("checked", false);
	 }
	 function clickRow(index,this_){
			var $checkbox1 = $("#courselist :checkbox[name='checkrow']:eq("+index+")");
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
		
	function save(){
		var businessToCourseList =[];
		
		if($("#courselist").find("tr").eq(0).find("td").length <= 1){
			AlertUtil.showErrorMessage("请添加数据后再保存！");
			return false;
			
		}
		$("#courselist").find("tr").each(function(){
			var businessToCourse = {};
			var this_tr = this;
			businessToCourse.kcid = $(this_tr).find("select[name='kcbh']").val();
			businessToCourse.jyyq = $(this_tr).find("input[name='jyyq']").val();
			businessToCourse.pxjg = $(this_tr).find("input[name='pxjg']").val();
			businessToCourse.ly = $(this_tr).find("input[name='ly']").val();
			businessToCourse.jctg = $(this_tr).find("input[name='jctg']").val();
			businessToCourse.gwid = $("#gwid").val();
			businessToCourse.dprtcode = $("#dprtcode").val();
			businessToCourseList.push(businessToCourse);
		});
		
		
		if(businessToCourseList.length > 0){
			var obj = {};
			obj.businessToCourseList = businessToCourseList;
			AjaxUtil.ajax({
				url:basePath+"/training/programme/add",
				type: "post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					 goPagegw();
					 //加载课程信息和人员信息
					 //queryAllpageList();
				}
			});
		}
	}
	//修改
	function edit(id){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/training/programme/selectById",
			type:"post",
			data:{
				 'id' : id
			 },
			dataType:"json",
			success:function(data){
				var row = data.row;
				$("#kcbh").attr("disabled",true);
				$("#courseId").val(row.id);
				$("#kcbh").val(row.kcid);
				$("#jyyq").val(row.jyyq);
				$("#pxjg").val(row.pxjg);
				$("#ly").val(row.ly);
				$("#jctg").val(row.jctg);
				$("#EditalertModal").modal("show");
				$("#gw_kc_chName",$("#EditalertModal")).html("修改");
				$("#gw_kc_enName",$("#EditalertModal")).html("Edit");
			}
		});
	}
	
	//修改确定
	function modify(){
		var obj={};
		obj.id=$("#courseId").val();
		obj.jyyq=$("#jyyq").val();
		obj.pxjg=$("#pxjg").val();
		obj.ly=$("#ly").val();
		obj.jctg=$("#jctg").val();
		obj.kcid=$("#kcbh").val();
		obj.dprtcode=$("#dprtcode").val();
		obj.gwid = $("#gwid").val();
		var url = "";
		if(obj.id){
			url = basePath+"/training/programme/edit";
		}else{
			url = basePath+"/training/programme/add";
		}
		if(obj.kcid =="" || obj.kcid == null){
			 AlertUtil.showErrorMessage("请选择课程编号");
			 //AlertUtil.showModalFooterMessage("请选择课程编号!");
			 return false;
		}
		
		AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				 goPagegw();
				 //加载课程信息和人员信息
				 //queryAllpageList();
				 //关闭弹出
				 AlertUtil.showErrorMessage("操作成功！");
				 $("#EditalertModal").modal("hide");
			},error:function(data){
				AlertUtil.showErrorMessage(data.responseText);
			}
		});
		
		
	}
	//作废
	function invalid(id){
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
				 AjaxUtil.ajax({
					 url:basePath+"/training/programme/delete",
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 dataType:"json",
					 success:function(data){
						 goPagegw();
						 //加载课程信息和人员信息
						 //queryAllpageList();
					 }
				 });
	 }});
	}
	
	function query_gjz(){
		var keywork=$("#gwxx").val();
		if(listgw.length<=0){
			$("#gwList").empty();
	    	$("#gwList").append("<li class='list-group-item' style='border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;background:#ececec;font-weight:bold;border-top-right-radius:5px;border-top-left-radius:5px;border-left:0px;height:30px;'>" +
	    			"<div class='text-left border-left-0 pull-left'  style='width:80px;'>" +
	    			"岗位编号</div><div class='text-left pull-left'  style='width:120px;'>" +
	    			"岗位名称</div></li><li class='list-group-item'>暂无数据</li>");
	    	
	    	 $("#ryList").empty();
		    	$("#ryList").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
		    	$("#kcList").empty();
		    	$("#kcList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
		    	
	    	return false;
		}
		if(keywork!=null && keywork!=""){
			var list=[];
			   $.each(listgw,function(index,row){
				   if(row.dgbh.indexOf(keywork)!=-1 || row.dgmc.indexOf(keywork)!=-1){
					   list.push(row);
				   }
			   });
			   if(list.length>0){
				   appendgw(list);
				   queryAllpageList();
			   }else{
				   $("#gwList").empty();
			    	$("#gwList").append("<li class='list-group-item' style='border-radius:0px;border:1px solid #ddd;border-bottom:0px;margin-bottom:0px;background:#ececec;font-weight:bold;border-left:0px;height:30px;'>" +
					 		"<div class='text-left border-left-0 col-xs-5' >" +
					 		"岗位编号</div><div class='text-left col-xs-5'  >" +
					 		"岗位名称</div><div class='text-left col-xs-2' >" +
					 		"</div><div class='clearfix'></div></li><li class='list-group-item'>暂无数据</li>");
			    	
			    	 $("#ryList").empty();
				    	$("#ryList").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
				    	$("#kcList").empty();
				    	$("#kcList").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
			   }
		}else{
			appendgw(listgw);
	    	queryAllpageList();
		}
		
	}
	//查看详情
	function viewDetail(id){
		window.open(basePath+"/training/course/view?id=" + id);

	}
	//查看人员信息
	function view(id){
		
		$("#alertModalOpen").modal("show");
		AjaxUtil.ajax({
			url:basePath+"/training/programme/matlist",
			 type:"post",
			 async: true,
			 data:{
				 'wxrydaid' : id
			 },
			 dataType:"json",
			 success:function(data){
				 if(data.matlist!=null  && data.matlist.length>0){
					 appendMatList(data.matlist);
				 }else{
					 $("#matlist").empty();
				     $("#matlist").append("<tr class='text-center'><td colspan=\"20\">暂无数据 No data.</td></tr>"); 
				 }
			 }
		 });
	}
	function appendMatList(list){
		var htmlContent='';
		 $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
			   }
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.dgbh)+"/"+StringUtil.escapeStr(row.paramsMap.dgmc)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.dgbh)+"/"+StringUtil.escapeStr(row.paramsMap.dgmc)+"</td>";
			   htmlContent += "<td class='text-cneter'>"+(row.zt==0?'申请中':'已授权')+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcbh)+"' class='text-left'><a href=\"javascript:courseView('"+row.paramsMap.kcid+"')\">"+StringUtil.escapeStr(row.paramsMap.kcbh)+"</a></td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcmc)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.kcmc)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.lb)+"' class='text-left'>"+StringUtil.escapeStr(row.lb)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.rybh)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.rybh)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.xm)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.xm)+"</td>";
			   htmlContent += "<td  class='text-cneter'>"+(row.paramsMap.is_fx==0?'否':'是')+"</td>";
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.paramsMap.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',StringUtil.escapeStr(row.paramsMap.zqdw))+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.xcpxrq).substring(0,10)+"' class='text-center'>"+StringUtil.escapeStr(row.paramsMap.xcpxrq).substring(0,10)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.sj_ksrq).substring(0,10)+"' class='text-center'><a href=\"javascript:pxjhView('"+(row.paramsMap.scpxjhid)+"')\">"+StringUtil.escapeStr(row.paramsMap.sj_ksrq).substring(0,10)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.jhsj).substring(0,10)+"' class='text-center'><a href=\"javascript:pxjhView('"+(row.paramsMap.pxjhid)+"')\">"+StringUtil.escapeStr(row.paramsMap.jhsj?(row.paramsMap.jhsj).substring(0,10):'')+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.pxgh)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.pxgh)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.kcdd)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.kcdd)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.jsxm)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.jsxm)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.cql)+"' class='text-right'>"+StringUtil.escapeStr(row.paramsMap.cql)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.cj)+"' class='text-right'>"+StringUtil.escapeStr(row.paramsMap.cj)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.zs)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.zs)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.khjg==0?"未通过":"通过")+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.khjg==0?"未通过":"通过")+"</td>";
			   htmlContent += "</tr>";  
		 });
		 if(htmlContent==""){
			 htmlContent = "<tr class='text-center'><td colspan=\"20\">暂无数据 No data.</td></tr>";
		 }
		   $("#matlist").empty();
		   $("#matlist").html(htmlContent);
	}
	
	function substring026(str){
		if(str!=null && str.length>6){
			return str.substring(0,6)+"...";
		}
		return str;
	}
	function substring028(str){
		if(str!=null && str.length>13){
			return str.substring(0,13)+"...";
		}
		return str;
	}
	function courseView(id){
		window.open(basePath+"/training/course/view?id=" + id);
	}
	function pxjhView(id){
		if(id != null && id!=""){
			window.open(basePath+"/training/trainingnotice/find/" + id);
		}
	}
	
	//左右收缩的小图标
	function toggleIcon(obj){
		var i = $(obj);
		if(i.hasClass("icon-caret-left")){
			i.removeClass("icon-caret-left").addClass("icon-caret-right");
			$("#left_div").hide();
			$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
		}else{
			i.removeClass("icon-caret-right").addClass("icon-caret-left");
			$("#left_div").show();
			$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
		}
		App.resizeHeight();
	}
	
	function customResizeHeight(bodyHeight, tableHeight){
		 // 关键字高度
        var rowHeight = $('.row-height:visible').outerHeight()||0;
        var ulHeight=bodyHeight-rowHeight-16;
        $("#gwList").css({"height":ulHeight+"px","overflow":"auto"});
        var tabHeight=$("#right_div ul.nav-tabs").outerHeight()||0;
        var leftHeight=$("#left_div").outerHeight();
        var tabContentHeight=leftHeight-tabHeight-12;
        
        var tabRowHeight=$("#right_div .row-height:visible").outerHeight()||0;
        
        $("#right_div .tab-content").css("height",tabContentHeight+"px");
        var tableHeight=tabContentHeight-tabRowHeight-32;
        
        $("#right_div table").parent("div").css("height",tableHeight+"px");
			//多列表高度设定
		}
	
	
	 function saveWorkRequires(){
		 var id=$("#id_update").val();
		 var gwid=$("#gwid").val();
		 var xc=$("#xc_update").val();
		var gwxq=$("#gwxq_add").val();
		var isFx=$("input[name='isFx_add']:checked").val();
		var whr=$("#whr_add").val();
		var whbm=$("#whbm_add").val();
		var whsj=$("#whsj_add").val();
		var workRequire={};
		workRequire.id=id;
		workRequire.xc=xc;
		workRequire.mainid=gwid;
		workRequire.gwyq=gwxq;
		workRequire.zt=isFx;
		workRequire.whrid=whr;
		workRequire.whbmid=whbm;
		//workRequire.whsj=whsj;
		if(!$.trim(gwxq)){
			AlertUtil.showErrorMessage("岗位需求不能为空！");
			$("#gwxq_add").focus();
			   return ;
		}
		
		AjaxUtil.ajax({
			url:basePath+"/training/programme/addWorkRequires",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(workRequire),
			dataType:"json",
			success:function(data){
				$("#AddalertWorkModal").modal("hide");
				 //加载课程信息和人员信息和岗位需求信息
				 queryAllpageList();
				
			},error:function(data){
				  alert(JSON.stringify(data));
			}
					
		})		 
	 }
	 
	  function editWorkRequire(obj){
			var id=$(obj).attr("id");
			var mainid=$(obj).attr("mainid");
			var xc=$(obj).attr("xc");
			var gwyq=$(obj).attr("gwyq");
			var zt=$(obj).attr("zt");
			var whbmid=$(obj).attr("whbmid");
			var whrid=$(obj).attr("whrid");
			//var whsj=$(obj).attr("whsj");

			
			
			$("#gwyq_update").html(gwyq);
			$("#whr_update").val(whrid);
			$("#whbm_update").val(whbmid);
			//$("#whsj_update").val(whsj);
            $("#id_update").val(id);
            $("#mainid_update").val(mainid);
            $("#xc_update").val(xc);
			
            $("#EditalertWorkModal").modal("show");
	  
	  
	  }
	  
	  function editSaveWorkRequires(){
		  var workRequire={};
		    workRequire.id=$("#id_update").val();
		    workRequire.mainid=$("#mainid_update").val();
		    workRequire.xc=$("#xc_update").val();
			workRequire.gwyq=$("#gwyq_update").val();
			workRequire.zt=$("input[name='isFx_update']:checked").val();
			workRequire.whrid=$("#whr_update").val();
			workRequire.whbmid=$("#whbm_update").val();
//			workRequire.whsj=$("#whsj_update").val();
			
			
			if(!$.trim(workRequire.gwyq)){
				AlertUtil.showErrorMessage("岗位需求不能为空！");
				$("#gwyq_update").focus();
				   return ;			
			}
		  
			AjaxUtil.ajax({
				url:basePath+"/training/programme/updateSaveWorks",
				type: "post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(workRequire),
				dataType:"json",
				success:function(data){
					 //加载课程信息和人员信息和岗位需求信息
					 queryAllpageList();
					 //关闭弹出
					 AlertUtil.showErrorMessage("操作成功！");
					 $("#EditalertWorkModal").modal("hide");					
				},error:function(data){
					
					 alert(JSON.stringify(data));
				}						
			})			  
	  }
	  
	  function invalidWork(rowid){
			AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
				 AjaxUtil.ajax({
					 url:basePath+"/training/programme/deleteWorkRequire",
					 type:"post",
					 async: false,
					 data:{
						 'id' : rowid
					 },
					 dataType:"json",
					 success:function(data){
						 goPagegw();
						 //加载课程信息和人员信息
						 //queryAllpageList();
					 }
				 });
	 }});
		  
	  }
	  function exportExcel(){
			var keyword = $("#gwxx").val();
			var dprtcode = $("#dprtcode").val();
			var state = $("#state").val();
			window.open(basePath+"/training/programme/programme.xls?&dprtcode="
		 			+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
		}
	  function addKc(){
		  if(kcbhStr == ""){
			  AlertUtil.showErrorMessage("当前岗位没有课程，请先添加课程！");
			  return false;
		  }
		  if($("#courselist").find("tr").eq(0).find("td").length <= 1){
			  $("#courselist").empty();
		  }
		  var str = "";
		  str +="<tr>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +="<button class='line6' title='删除  Delete'  onclick='removeKc(this)'>";
		  str +="<i class='fa fa-minus color-blue cursor-pointer'></i>";
		  str +="</button>";
		  str +="</td>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +=kcbhStr;
		  str +="</td>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +="<input type='text' class='form-control' name='jyyq' >";
		  str +="</td>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +="<input type='text' class='form-control' name='pxjg' >";
		  str +="</td>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +="<input type='text' class='form-control' name='ly' >";
		  str +="</td>";
		  str +="<td style='text-align:center;vertical-align:middle'>";
		  str +="<input type='text' class='form-control' name='jctg' >";
		  str +="</td>";
		  str +="</tr>";
		  $("#courselist").append(str);
	  }
	  function removeKc (obj){
		  $(obj).parents("td").parent("tr").remove();
		  if($("#courselist").find("tr").length < 1){
			  $("#courselist").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
		  }
	  }
	  //查看课程
	  function openKcView(kcid){
		  window.open(basePath+"/training/course/view?id=" + kcid);
	  }
	  
	  