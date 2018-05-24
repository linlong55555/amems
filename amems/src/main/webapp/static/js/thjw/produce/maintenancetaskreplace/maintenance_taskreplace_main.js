$(document).ready(function(){
	Navigation(menuCode, '', '', 'SC12-2', '刘邓', '2017-12-11', '刘邓', '2017-12-11');//加载导航栏
	initFjjxOption();					//初始化机型
 	refreshPermission(); 				//加载按钮权限
	initDatas(); 				//初始化列表数据
});

/**
 * 初始化飞机机型
 */
function initFjjxOption(){
	$("#jx").html("<option value='' >显示全部All</option>");
	var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : $("#dprtcode").val()});
	if(typeList.length > 0){
		for(var i = 0; i < typeList.length; i++){
			$("#jx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
		}
	}else{
		$("#jx").html("<option value=''>显示全部All</option>");
	}
	
	$("#jx option:eq(1)").attr("selected","selected");  
}

/**
 * 初始化列表数据
 */
function initDatas(obj){
	if(obj)initFjjxOption();
	var wxxmlxList = [];
	$("input[name='wxxmlx']:checked").each(function() {
		wxxmlxList.push($(this).val());
	});
	var jx = $("#jx").val();
	var dprtcode = $("#dprtcode").val();
	var type = $(":radio[name='group_type']:checked").val();
	startWait();
	AjaxUtil.ajax({
		type : "post",
		url : basePath + "/produce/maintenance/taskreplace/query",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			jx : jx,
			dprtcode : dprtcode,
			paramsMap : {
				wxxmlxList : wxxmlxList,
				type : type,
				keyword : $.trim($("#keyword_search").val())
			}
		}),
		success : function(data) {
			finishWait();
			var list=classifyData(data,type);
            appendHtml(list);          
        	var keyword = $("#keyword_search").val();
		    signByKeyword(keyword, [1,3,4]);
            hideBottom();
		    new Sticky({tableId:'scjkyj_table'});
		}
	});
}

function initDatasByConditions(){
	initDatas();
}
function appendHtml(data){
	var htmlContent="";
	if(!data||data.length==0){
		htmlContent="<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>";
	}else{
		$.each(data,function(index,row){
			var groupName="";
			 if(row.type=='1'){
				var zjh_ywms=row.zjh_ywms;
				var zjh_zwms=row.zjh_zwms;
				if(!zjh_ywms)zjh_ywms="";
				if(!zjh_zwms)zjh_zwms="";
				groupName=StringUtil.escapeStr(row.zjh+" "+zjh_ywms+" "+zjh_zwms);
			 }else if(row.type=='2'){
				 var dlbh_ywms=row.dlbh_ywms;
				 var dlbh_zwms=row.dlbh_zwms;
				 if(!dlbh_ywms)dlbh_ywms="";
				 if(!dlbh_zwms)dlbh_zwms="";
				 groupName=StringUtil.escapeStr(row.dlbh+" "+dlbh_ywms+" "+dlbh_zwms);
			 }
			htmlContent +="<tr onclick='showChildTR(this,\""+row.zjh+"\")' bgcolor='#f9f9f9' name='"+row.zjh+"' id='"+row.zjh+"'>";
			htmlContent += "<td colspan='9' title='"+groupName+"'>";
			htmlContent += "&nbsp;&nbsp;&nbsp;&nbsp;<span><i rowid='"+row.zjh+"' class='fa fa-angle-down'></i></span>&nbsp;&nbsp;&nbsp;&nbsp;";
			htmlContent += "<span class='badge' style='background:#3598db;' name='badgeCount'>"+row.children.length+"</span>";
			htmlContent += "<span style='margin-left:8px;' title='"+groupName+"'>"+groupName+"</span>";
			htmlContent += "</td>";
			htmlContent +="</tr>";
			
			$.each(row.children,function(index,line){
				var wxxmlx=line.wxxmlx;
				if(wxxmlx=='4'){
					htmlContent +="<tr onclick='hideBottom(this)' name='detailRow' id='"+line.wxfaid+"' class='"+row.zjh+"' bgcolor='#fefefe' style='display:none;text-decoration:'>";
				}else{
					htmlContent +="<tr name='detailRow'  gkid='"+line.gkid+"'  id='"+line.wxfaid+"' class='"+row.zjh+"' wxxmid="+line.wxxmid+"  rwh="+line.rwh+"  bgcolor='#fefefe'  wxfaid="+line.wxfaid+"   jx="+line.jx+" dprtcode="+line.dprtcode+"  eobh="+line.eobh+" style='display:none;text-decoration:' onclick='showBottomDiv(event,this)'>";
				}
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(line.rwh)+"'><a href='javascript:void(0);' onclick='viewProject(this)'>"+StringUtil.escapeStr(line.rwh)+"</a></td>";
				htmlContent+="<td style='text-align:center' title='"+StringUtil.escapeStr(line.bb)+"'>"+"R"+StringUtil.escapeStr(line.bb)+"</td>";
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(line.ckh)+"'>"+StringUtil.escapeStr(line.ckh)+"</td>";
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(line.rwms)+"'>"+StringUtil.escapeStr(line.rwms)+"</td>";
				var syx=line.syx;
				if("00000"==syx){
					syx="ALL";
				}
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(syx)+"'>"+StringUtil.escapeStr(syx)+"</td>";		
				 if(wxxmlx=="1"){
					 wxxmlx="一般项目";
				 }else if(wxxmlx=="2"){
					 wxxmlx="时控项目";
				 }else if(wxxmlx=="3"){
					 wxxmlx="时寿项目";
				 }else if(wxxmlx=="4"){
					 wxxmlx="定检包";
				 }
				htmlContent+="<td style='text-align:center' title='"+StringUtil.escapeStr(wxxmlx)+"'>"+StringUtil.escapeStr(wxxmlx)+"</td>";
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(groupName)+"'>"+StringUtil.escapeStr(groupName)+"</td>";
				 var gkbh="";
				 if(line.gkbh){
					 gkbh=line.gkbh;
				 }
				htmlContent+="<td style='text-align:left' title='"+StringUtil.escapeStr(gkbh)+"'><a href='javascript:void(0);' onclick='viewWorkCard(this)'>"+StringUtil.escapeStr(gkbh)+"</a></td>";
				var dprtcode=$("#dprtcode").val();
				htmlContent+="<td style='text-align:left' title='"+AccessDepartmentUtil.getDpartName(dprtcode)+"'>"+AccessDepartmentUtil.getDpartName(dprtcode)+"</td>";
				htmlContent+="</tr>";
				
			});
			
		});
	};
	$("#scjkyj_list").empty();
    $("#scjkyj_list").append(htmlContent);
    
     //toRed($("#keyword_search").val());
    
}

//function toRed(content) {
//	if (content) {
//		var bodyHtml = $("#scjkyj_list").html();
//		var x = bodyHtml.replace(new RegExp(content, "gm"),
//				"<font color='red' >" + content + "</font>");
//		$("#scjkyj_list").html(x);
//
//	}
//
//};

function save(){
	var wxfaid=$("#wxfaid").val();
	var jx=$("#jx_con").val();
	var wxxmid=$("#wxxmid").val();
	var rwh=$("#rwh").val();
	var relativeMaintenanceProject=[];
	//var ar2=[];
	$("#maintenance_project_list").find("tr").each(function(){
		// var obj3={};
		// var obj2={};
		 var param={};
		 param.xgwxxmid=$(this).find("input[name='hiddenid']").val();
		 param.xgrwh=$(this).find("input[name='rwh']").val();
		 relativeMaintenanceProject.push(param);
//		 obj3.mainid=wxfaid;
//		 obj3.jx=jx;
//		 obj3.wxxmid=wxxmid;
//		 obj3.rwh=rwh;
//		 obj3.xgwxxmid=$(this).find("input[name='hiddenid']").val();
//		 obj3.xgrwh=$(this).find("input[name='rwh']").val();
//		 ar3.push(obj3);
//		 
//		 obj2.mainid=$(this).find("input[name='hiddenid']").val();
//		 obj2.jx=jx;
//		 obj2.wxxmbh=$(this).find("input[name='rwh']").val();
//		 ar2.push(obj2);
		 
	});	
	var obj={
			eobh:$("#eeobh").val(),	
			wxfaid:$("#wxfaid").val(),
			wxxmid:$("#wxxmid").val(),
			jx:jx,
			rwh:rwh,
			paramsMap:{
				
				relativeMaintenanceProject:relativeMaintenanceProject
			}
	};
	
	AjaxUtil.ajax({
		type : "post",
		url : basePath + "/produce/maintenance/taskreplace/save",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(obj),
		success : function(data) {
           AlertUtil.showMessage("保存成功 ！");
           $("#scjkyj_list .bg_tr").attr("eobh",$("#eeobh").val());
           showBottomDiv('',$("#scjkyj_list .bg_tr"));
		}
	});
	    
}

// 工卡查看
function viewWorkCard (obj){
	var gkid=$(obj).parent().parent().attr("gkid");
	window.open(basePath + "/project2/workcard/view/" + gkid);
};

// 维修项目查看
function viewProject(obj){
	var wxxmid=$(obj).parent().parent().attr("wxxmid");
	var wxfaid=$(obj).parent().parent().attr("wxfaid");
	window.open(basePath + "/project2/maintenanceproject/view?id=" + wxxmid + "/" + wxfaid);
}


// EO查看
function viewEO(obj){
	var eoid=$(obj).parent().parent().parent().prev().find("input[id='eeoid']").val();
	window.open(basePath + "/project2/eo/view?id=" + eoid);
}

function showChildTR(obj,name){
	if($(obj).find("[rowid='"+name+"']").hasClass("fa fa-angle-down")){
		$('[rowid="'+name+'"]').attr("class","fa fa-angle-up cursor-pointer");
		$("."+name).css("display","table-row");
	}else{
		$('[rowid="'+name+'"]').attr("class","fa fa-angle-down cursor-pointer");
		$("."+name).css("display","none");
	}
	new Sticky({tableId:'scjkyj_table'});
};

function openProjectWinAdd(){
	var existsIdList=[];
	var obj = {
		wxfaid : $("#wxfaid").val(),
		wxxmid : $("#wxxmid").val(),
		dprtcode : $("#dprtcode").val(),
		jx : $("#jx_con").val()
	};
	
	existsIdList.push($("#rwh").val());
	
	$("#maintenance_project_list").find("tr").each(function(){
		var rwh=$(this).find("input[name='rwh']").val();
		 if(rwh){
			 existsIdList.push(rwh); 
		 }
		
	});
	
	open_win_maintenance_project.show({
		multi : true,
		loaded : false,
		url : basePath + "/produce/maintenance/taskreplace/initProjectWindow",
		dprtcode : $("#dprtcode").val(),
		parentWinId : 'open_win_maintenance_project',
		existsIdList : existsIdList,// 过滤已经选择的
		obj : obj,
		callback : function(data) {// 回调函数
			var htmlContent="";
			if(!data||$.isEmptyObject(data)){
				$("#maintenance_project_list tr").remove();
				$("#maintenance_project_list").append("<tr id='nodata'><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			}
			if(data&&data.length==0){
				return;
			}
            if(data&&data.length>0){
              $("#maintenance_project_list").find("#nodata").remove();
              appendPlan(data,'1');
              //排序
          	var count=$("#maintenance_project_list tr").size();
        	for(var i=0;i<count;i++){
        		$("#maintenance_project_list").find("tr:eq("+i+")").find("span").html(i+1);
        	}
            }
		}
	}, true);	
}

function openEOWin(){
	var eoid = $("#eeoid").val();
	var dprtcode =$("#dprtcode").val();
	var jx=$("#jx_con").val();
	var wxxmid=$("#wxxmid").val();
	open_win_eo.show({
		id:eoid,
		selected:eoid,//原值，在弹窗中默认勾选
		jx:jx,
		wxxmid:wxxmid,
		dprtcode:dprtcode, //机构代码
		parentWinId:"open_win_eo",
		callback: function(data){//回调函数
			if(data != null && data.id != null){
				$("#eeoid").val(data.id);
				$("#eeobh").val(data.eobh);
				//$("#eo_title_div").show();
				$("#eo_title").html(data.eozt);
			}else{
				$("#eeoid").val('');
				$("#eeobh").val('');
				//$("#eo_title_div").hide();
				$("#eo_title").html('');
			}
		}
	});		
}


function showBottomDiv(e,obj){	
	$("#scjkyj_table .bg_tr").removeClass("bg_tr");
	$(obj).addClass("bg_tr");
	var id=$(obj).attr("id");
	var eobh=$(obj).attr("eobh");
	var jx=$(obj).attr("jx");
	var dprtcode=$(obj).attr("dprtcode");
	var wxxmid=$(obj).attr("wxxmid");
	var wxfai=$(obj).attr("wxfaid");
	var rwh=$(obj).find("td:eq(0)").find("a").html();;
	$("#wxxmid").val(wxxmid);
	$("#jx_con").val(jx);
	$("#wxfaid").val(wxfai);
    $("#rwh").val(rwh);
    startWait();
	//根据EO编号,机型,组织机构获取EO标题	
	AjaxUtil.ajax({
		type : "post",
		url : basePath + "/project2/eo/queryByEobh",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
			jx : jx,
			dprtcode : dprtcode,
		    eobh:eobh
		}),
		success : function(data) {
			if(data){
				 $("#eeobh").val(data.eobh);
				 $("#eeoid").val(data.id);
				 $("#eo_title").html(data.eozt);
			}else{
				 $("#eeobh").val("");
				 $("#eeoid").val("");
				 $("#eo_title").html("");

			}
			
		}
	});
	
	//加载维修项目	
	AjaxUtil.ajax({
		type : "post",
		url : basePath + "/produce/maintenance/taskreplace/initProjectByid",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify({
		      wxxmid:$("#wxxmid").val(),
		      wxfaid:$("#wxfaid").val()
		}),
		success : function(data) {
			if(data){
                appendPlan(data);
			}
			finishWait();
		}
	});
	
	 var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
	
	
	$(".displayContent").slideDown("500");
	App.resizeHeight();	
	 if(!isBottomShown){
		 $("#maintenance_item_table_top_div", $("#task_replace_main")).animate({scrollTop:obj.offsetTop-50},"slow");		 
	 }
	
};


function appendPlan(list,status){
	var htmlContent="";
	if(list&&list.length>0){
	for(var i=0;i<list.length;i++){
		var obj=list[i];
		htmlContent+="<tr>";
		htmlContent+="<td style='text-align:middle;vertical-align:middle;'><button class='line6 line6-btn' onclick='deleteRow(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";
		htmlContent+="<td style='text-align:middle;vertical-align:middle;'><span name='orderNumber'>"+(i+1)+"</span><input type='hidden' name='hiddenid' value='"+obj.wxxmid+"'><input type='hidden' name='rwh' value='"+obj.rwh+"'></td>";
		var bb="";
		 if(obj.bb)bb="R"+obj.bb;
		htmlContent+="<td title='"+StringUtil.escapeStr(bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(bb)+"</td>";
		htmlContent+="<td title='"+StringUtil.escapeStr(obj.rwh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.rwh)+"</td>";
		htmlContent+="<td title='"+StringUtil.escapeStr(obj.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ckh)+"</td>";
		var syx="";
		if(!obj.syx){
			syx="N/A";
		}else if(obj.syx=='00000'){
			syx="ALL";	
		}else if(obj.syx=="-"){
			syx=obj.fjzch;			
		} 
		htmlContent+="<td title='"+StringUtil.escapeStr(syx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(syx)+"</td>";
		htmlContent+="<td title='"+StringUtil.escapeStr(obj.ckwj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ckwj)+"</td>";
		
		if(obj.jhgsRs!=null && obj.jhgsRs!="" && obj.jhgsXss!=null && obj.jhgsXss!=""){
			var total = obj.jhgsRs*obj.jhgsXss;
			if(String(total).indexOf(".") >= 0){
				total = total.toFixed(2);
			}
			var jhvalue=obj.jhgsRs+"人x"+obj.jhgsXss+"时="+total+"时";
			htmlContent = htmlContent + "<td style='text-align:left;vertical-align:middle;' title='"+jhvalue+"'>"+jhvalue+"</td>";  
		}else{
			htmlContent = htmlContent + "<td style='text-align:left;vertical-align:middle;'></td>"; 
		}	
		htmlContent+="</tr>";
	} 
	}else{
		htmlContent+="<tr id='nodata'><td  class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>";
		
	}
	 if(!status){
		$("#maintenance_project_list").empty();
	 }
	$("#maintenance_project_list").append(htmlContent);
}

function deleteRow(obj){
	$(obj).parent().parent().remove();
	//重新排序
	var count=$("#maintenance_project_list tr").size();
	for(var i=0;i<count;i++){
		var num=i+1;
		$("#maintenance_project_list").find("tr:eq("+i+")").find("span").html(num);
	}
	
	if(count==0){
	  $("#maintenance_project_list").append("<tr id='nodata'><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
	}
	
}


function hideBottom (obj){
	$(".displayContent").css("display","none");	
	$(obj).addClass("bg_tr");
	$("#scjkyj_table tr[name='detailRow']").not(obj).each(function(){
		$(this).removeClass("bg_tr");
	});
	App.resizeHeight();
};



function toggleAll(){

	var th = $("th[name='ope_td']");
	if(th.hasClass("downward")){
		$("th[name='ope_td']").removeClass("downward").addClass("upward");
		$('#scjkyj_list .fa.fa-angle-down').attr("class","fa fa-angle-up cursor-pointer");
		$('tr[name="detailRow"]').css("display","table-row");
	}else{
		$("th[name='ope_td']").removeClass("upward").addClass("downward");
		$('#scjkyj_list .fa.fa-angle-up').attr("class","fa fa-angle-down cursor-pointer");
		$('tr[name="detailRow"]').css("display","none");
	}
	new Sticky({tableId:'scjkyj_table'});
	
}



//通过章节号或者维修项目大类对数据分组
//如果type=1按照章节号分组,如果type=2按照维修项目大类分组
function classifyData(data,type){
	var dist = [];
	var map = {};
	if (data && data instanceof Array) {
		for ( var i = 0; i < data.length; i++) {
			var ai = data[i];
			if(type=="1"){
				if (!map[ai.zjh + "+" + ai.zjh_zwms + "+" + ai.zjh_ywms]) {
					dist.push({
						zjh : ai.zjh,
						zjh_zwms : ai.zjh_zwms,
						zjh_ywms : ai.zjh_ywms,
						children : [ ai ],
						type:type
					});
					map[ai.zjh + "+" + ai.zjh_zwms + "+" + ai.zjh_ywms] = ai;
				} else {
					for ( var j = 0; j < dist.length; j++) {
						var dj = dist[j];
						if (ai.zjh == dj.zjh && ai.zjh_zwms == dj.zjh_zwms
								&& ai.zjh_ywms == dj.zjh_ywms) {
							dj.children.push(ai);
							break;
						};
					
					};
					
				};				
			}else if(type=="2"){
				if (!map[ai.dlbh + "+" + ai.dlbh_zwms + "+" + ai.dlbh_ywms]) {
					dist.push({
						dlbh : ai.dlbh,
						dlbh_zwms : ai.dlbh_zwms,
						dlbh_ywms : ai.dlbh_ywms,
						children : [ ai ],
						type:type
					});
					map[ai.dlbh + "+" + ai.dlbh_zwms + "+" + ai.dlbh_ywms] = ai;
				} else {
					for ( var j = 0; j < dist.length; j++) {
						var dj = dist[j];
						if (ai.dlbh == dj.dlbh && ai.dlbh_zwms == dj.dlbh_zwms
								&& ai.dlbh_ywms == dj.dlbh_ywms) {
							dj.children.push(ai);
							break;
						};
					
					};
					
				};							
			};			
		};
		dist.sort(function(a,b){
			if(type=='1')
			 return a.zjh-b.zjh;
			if(type=='2')
			return 	a.xc-b.xc;
		})
		return dist;
	};
	   return data;
};

function getMore(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
	App.resizeHeight();
}


function searchreset(){
	//$("#jx option:eq(1)").attr("selected","selected");  
	$("#keyword_search").val("");
	$(":radio[name='group_type'][value='1']").attr("checked","checked");
	$(":checkbox[name='wxxmlx']").attr("checked","checked");
	$("#dprtcode").children("option[value='"+userJgdm+"']").attr("selected","selected");
	 initFjjxOption();
}