var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束"];
var ids='';
var selectPgdId = '';
var pagination={};
var monitorType = "";
$(document).ready(function(){
	monitorType = $("#monitorType").val();
	if(monitorType != ""){
		$("#wjlxdiv").hide();
		$(".viewCol").show();
	}else{
		$("#wjlxdiv").show();
		$(".viewCol").remove();
	}
	var dprtcode=$("#zzjg").val();
	goPage(1,"auto","desc");
 	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	//导航
	 
	Navigation(menuCode,"","");//初始化导航
	
	fjjxjz();
	$("#quality_check_list_table_div").css("height",panelContentHeight()+"px");
	$(window).resize(function(){
		showOrHideDetail();
	});
});

function showOrHideDetail(){
	if($("#jstreeMain").css("display")=="block"){
		$("#quality_check_list_table_div").css("height",(panelContentHeight()-bottomleftdivHeight()-4)+"px");
	}else{
		$("#quality_check_list_table_div").css("height",(panelContentHeight()-10)+"px");
	}
}


function panelContentHeight(){
	 // 头高度
   var headerHeight = $('.header').outerHeight();
   // 头部菜单高度
   var headerDownHeight = $('.header-down').outerHeight();
   // 屏幕高度
   var windowHeight = $(window).height();
   // 底部高度
   var footerHeight = $('.footer').outerHeight()||0;
   // 分页控件高度
   var paginationHeight = $('.page-height:visible').outerHeight()||0;
   // 关键字高度
   var rowHeight = $('.row-height:visible').outerHeight()||0;
   // 查询高度
   var searchHeight = $('.search-height:visible').outerHeight()||0;
   if(searchHeight){searchHeight = searchHeight += 10}
   // 面板标题高度
   var panelHeadingHeight = $('.panel-heading').outerHeight();
   var pagination = $('.pagination').outerHeight()|| 40;
  
   
   var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight-searchHeight-58-pagination ;
   return bodyHeight;
}
function fjjxjz(){
	var dprtcode = $.trim($("#zzjg").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if(planeDatas != null && planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }
	  /*if(planeRegOption == ''){
	  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
	  }*/
	 var htmlplan='<option value="">显示全部</option>';
	  $("#jx").empty();
	  $("#jx").append(htmlplan);
	  $("#jx").append(planeRegOption);
}

// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	$("#jstreeMain").css("display","none");
	showOrHideDetail();
	var obj ={};
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	var paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
	obj.paramsMap=paramsMap;
	obj.pagination = pagination;
	obj.keyword=$.trim($('#keyword_search').val());
	obj.jx = searchParam.jx;
	obj.fl = searchParam.fl;
	obj.wjlx = searchParam.wjlx;
	obj.ly = searchParam.ly;
	obj.zt = searchParam.zt;
	obj.pgrid=searchParam.pgrid;
	obj.zdrid=searchParam.zdrid;
	obj.dprtcode=searchParam.dprtcode;
	
	if(id != ""){
		obj.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/technicalfile/queryTechnicalFileListUpload",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
	    finishWait();
			if(data.total >0){
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
				});
				if(monitorType != ""){
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[3,8,9,18,20]);
				}else{
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[1,6,7,16,18]);
				}
			} else {
				$("#list").empty();
				$("#pagination").empty();
				if(monitorType != ""){
					$("#list").append("<tr><td class='text-center' colspan=\"22\">暂无数据 No data.</td></tr>");
				}else{
					$("#list").append("<tr><td class='text-center' colspan=\"20\">暂无数据 No data.</td></tr>");
				}
			}
			new Sticky({tableId:'quality_check_list_table'});
		}
    }); 
	
}
// 将搜索条件封装
function gatherSearchParam(){
	var searchParam = {};
	searchParam.jx = $("#jx").val();
	searchParam.fl = $("#fl").val();
	var wjlx = $("#wjlx").val();
	if(monitorType != ""){
		wjlx =  monitorType;
	}
	searchParam.wjlx = wjlx;
	searchParam.ly = $("#ly").val();
	searchParam.zt = $("#zt").val();
	searchParam.pgrid=$("#jxgcs").val();
	searchParam.dprtcode=$("#zzjg").val();
	return searchParam;
}
function appendContentHtml(list,monitorsettings){
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\" id='"+row.id+"' onclick='showjstreemain(this)'  style='cursor:pointer;'>";
		   
		} else {
			htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\" id='"+row.id+"' onclick='showjstreemain(this)' style='cursor:pointer;'>";
		}
		
		if(monitorType != ""){
		
			htmlContent += "<td class='text-center fixed-column'>";
			
			if(row.qualityClose == null){
				htmlContent += "<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='engineering:center:cad:01,engineering:center:sb:01' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.pgdh)+"' zdjsyy='' zdjsr='' zdjsrq='' dprtcode='"+row.dprtcode+"' onClick=\"qualityClose(this,true)\" title='关闭 Close'></i>";
			}
			
			htmlContent += "</td>";
			
			htmlContent += "<td class='text-center fixed-column'>";
			
			if(row.qualityClose != null){
				var zdjsr = StringUtil.escapeStr(row.qualityClose.whrusername) + " " + StringUtil.escapeStr(row.qualityClose.whrrealname);
				htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.pgdh)+"' zdjsyy='"+StringUtil.escapeStr(row.qualityClose.gbyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+formatUndefine(row.qualityClose.whsj)+"' onclick=\"qualityClose(this,false)\">"+formatUndefine(row.qualityClose.whsj)+"</a>";
			}
		
			htmlContent += "</td>";
		}
		
		htmlContent += "<td class='text-center fixed-column'><a href=\"javascript:find('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.pgdh)+"</a></td>";   
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.ly)+"</td>";  
		htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.fl)+"</td>";  
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.wjlx)+"</td>";  
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.shzltgh)+"'>"+StringUtil.escapeStr(row.shzltgh)+"</td>";  
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.wjzt)+"'>"+StringUtil.escapeStr(row.wjzt)+"</td>"; 
		htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.bb)+"</td>";  
		htmlContent += "<td class='text-center'>"+indexOfTime(row.sxrq)+"</td>";
		htmlContent += "<td class='text-center'>"+indexOfTime(row.pgqx)+"</td>";
		if(row.zt==3 || row.zt==4 || row.zt==9){
			htmlContent += "<td class='text-center'>√</td>";  
		}else{
			htmlContent += "<td class='text-center'>"+formatUndefine((row.syts))+"</td>";  
		}
		htmlContent += "<td class='text-left'  title='"+StringUtil.escapeStr(row.pgr_user.displayName)+"'>"+StringUtil.escapeStr(row.pgr_user.displayName)+"</td>"; 
	   	if(row.zt==9){
	   		var zdjsr = StringUtil.escapeStr(row.zdjs_user?StringUtil.escapeStr(row.zdjs_user.displayName):'');
	   		htmlContent += "<td class='text-left'><a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.pgdh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+row.zdjsrq+"' onclick=\"shutDown(this,false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
	   	}else{
	   		htmlContent += "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
	   	}
	   	htmlContent += "<td class='text-left'>"; 
	   	htmlContent += ResultUtil.formatContent(row.zlbh, row.dprtcode,row.id);
	   	htmlContent += "</td>"; 
	   
	   	var nbwjm=row.technicalUpload.nbwjm;
	   	var hzm="";
	   	if(nbwjm!=null && nbwjm!=""){
	   		var str= new Array(); // 定义一数组
	   		str=nbwjm.split("."); // 字符分割
	   		hzm=str[1];
	   	}
	  
		var wj=row.technicalUpload.wbwjm+"."+hzm;
		htmlContent += "<td  class='text-left'><a href=\"javascript:downfile('"+row.technicalUpload.id+"')\">"+ StringUtil.escapeStr(wj)+ "</a></td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+ "</td>";
		htmlContent += "<td title='"+AffectedUtil.formatTitle(row.affected)+"' class='text-left'>" + AffectedUtil.formatContent(row.affected,row.id) + "</td>";
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr_user.displayName)+"'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>";  
		htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.dprtname)+"</td>";  
	   
		htmlContent += "</tr>";  
	    
   });
   $("#list").empty();
   $("#list").html(htmlContent);
   refreshPermission();       //触发按钮权限监控
}
	 
ResultUtil = {
	flag : true,
	formatContent : function(zlbh,dprtcode,id){
		var htmlContent = '';
		if(zlbh != null && zlbh != ""){
			var strs= new Array(); //定义一数组
			strs = zlbh.split(",&sun1"); //字符分割
			$.each(strs,function(i,s){
				var bh = s.split("(")[0];
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer "+id+"' onclick=CollapseOrExpandUtil.collapseOrExpandRow('"+id+"','quality_check_list_table')></i><div class='affectedDisplayFile' style='display:none'>";
				}
				htmlContent += "<a zlbh='"+StringUtil.escapeStr(bh)+"' dprtcode='"+dprtcode+"' href=\"javascript:void(0);\" onclick=ResultUtil.selectByzlbh(this) >"+StringUtil.escapeStr(s)+"</a>";
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
	selectByzlbh : function(this_){
		var zlbh = $(this_).attr("zlbh");
		var dprtcode = $(this_).attr("dprtcode");
		window.open(basePath+"/project/technicalfile/selectByzlbh?zlbh="+encodeURIComponent(zlbh)+"&dprtcode="+$.trim(dprtcode)+"");
	}
}

AffectedUtil = {
	flag : true,
	formatTitle : function(list){
		var htmlContent = '';
		if(list != null && list != "" && list.length > 0 ){
			$.each(list,function(i,obj){
				htmlContent += StringUtil.escapeStr(obj.fjzch);
				if(obj.bjh != null && obj.bjh != ''){
					htmlContent += " P/N:" + StringUtil.escapeStr(obj.bjh);
				}
				if(obj.bjxlh != null && obj.bjxlh != ''){
					htmlContent += " SN:" + StringUtil.escapeStr(obj.bjxlh);
				}
				if(i != list.length - 1){
					htmlContent += ",";
				}
			});
		}
		return htmlContent;
	},
	formatContent : function(strs,id){
		var htmlContent = '';
		if(strs != null && strs.length > 0){
			$.each(strs,function(i,obj){
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer "+id+"' onclick=CollapseOrExpandUtil.collapseOrExpandRow('"+id+"','quality_check_list_table')></i><div class='affectedDisplayFile' style='display:none'>";
				}
				htmlContent += StringUtil.escapeStr(obj.fjzch);
				if(obj.bjh != null && obj.bjh != ''){
					htmlContent += " P/N:" + StringUtil.escapeStr(obj.bjh);
				}
				if(obj.bjxlh != null && obj.bjxlh != ''){
					htmlContent += " SN:" + StringUtil.escapeStr(obj.bjxlh);
				}
				if(i != 0 && i != strs.length - 1){
					htmlContent += "<br/>";
				}
				if(i == strs.length - 1){
					htmlContent += "</div>";
				}
			});
		}
		return htmlContent;
	}
}

//质量关闭
function qualityClose(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	var dprtcode = $(this_).attr("dprtcode");
	QualityCloseModal.show({
		chinaHead:'关闭单号',
		englishHead:'Close No.',
		edit:isEdit,
		jsdh:sqdh,
		jsr:zdjsr,
		jssj:zdjsrq,
		jsyy:zdjsyy,
		callback: function(data){//回调函数
			if(null != data && data != ''){
				var obj = {
						mainid : id,
						gbyy : data,
						dprtcode : dprtcode
				};
				sbQualityClose(obj);
			}
		}
	});
}

//确认关闭
function sbQualityClose(qualityClose) {
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/project/technicalfile/qualityClose",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(qualityClose),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('质量关闭成功!');
			$("#QualityCloseModal").modal("hide");
			refreshPage();
		}
	});
}

QualityCloseModal = {
	id:'QualityCloseModal',
	chinaHeadId:'chinaHead',
	englishHeadId:'englishHead',
	jsr:'vgbr',
	jssj:'vgbsj',
	jsyy:'vgbyy',
	confirmId:'ConfirmBtn',
	param: {
		chinaHead:'关闭单号',
		englishHead:'Close No.',
		edit:false,
		jsdh:'',
		jsr:'',
		jssj:'',
		jsyy:'',
		callback:function(){}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initData();
		$("#"+this.id).modal("show");
	},
	initData : function(){
		$("#"+this.chinaHeadId,$("#"+this.id)).html(this.param.chinaHead);
		$("#"+this.englishHeadId,$("#"+this.id)).html(this.param.englishHead);
		this.showOrHideHead();//显示或隐藏
		this.setEffectorDisabled();
		this.loadData();//加载数据
	},
	showOrHideHead : function(){
		if(this.param.edit){
			$("#"+this.jsr,$("#"+this.id)).hide();
			$("#"+this.jssj,$("#"+this.id)).hide();
			$("#"+this.jsyy,$("#"+this.id)).show();
			$("#"+this.confirmId,$("#"+this.id)).show();
		}else{
			$("#"+this.jsr,$("#"+this.id)).show();
			$("#"+this.jssj,$("#"+this.id)).show();
			$("#"+this.jsyy,$("#"+this.id)).hide();
			$("#"+this.confirmId,$("#"+this.id)).hide();
		}
	},
	setEffectorDisabled : function(){
		if(this.param.edit){
			$("#ezlgbyy", $("#"+this.id)).removeAttr("disabled");
		}else{
			$("#ezlgbyy", $("#"+this.id)).attr("disabled",'true');
		}
	},
	loadData : function(){
		$("#egbdh",$("#"+this.id)).val(this.param.jsdh);
		$("#ezlgbr",$("#"+this.id)).val(this.param.jsr);
		$("#ezlgbrq",$("#"+this.id)).val(this.param.jssj);
		$("#ezlgbyy",$("#"+this.id)).val(this.param.jsyy);
	},
	vilidateData : function(){
		var zdjsyy = $.trim($("#ezlgbyy").val());
		if(null == zdjsyy || '' == zdjsyy){
			AlertUtil.showMessageCallBack({
				message : '请输入质量关闭原因!',
				option : 'ezlgbyy',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback($.trim($("#ezlgbyy").val()));
		}
	}
}


function loadGiveOrder(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/engineering/center/queryByPgdId",
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
//	 
var This='';
var oldColor = '';
var preType='';
var preIndexnum='';
function showjstreemain(obj){
	
	new Sticky({tableId:'quality_check_list_table'});
	selectPgdId = $(obj).attr('id');
	preType='';
	preIndexnum='';
	$(This).attr('bgcolor',oldColor);
	oldColor = $(obj).attr('bgcolor');;
	$(obj).attr('bgcolor','#ececec');
	This=obj;
	$("#jstreeMain").css("display","none");
	$("#jstreeMain").css("display","block");
	showOrHideDetail();
	for(var i = 0 ; i<$('.displayFile').length;i++){
		$('.displayFile').eq(i).css("display","none");	
	}
	
	loadGiveOrder(selectPgdId,function(data){
		createJsTree(data); 	  
	});
	$("#quality_check_list_table_div").css("height",(panelContentHeight()-bottomleftdivHeight()-4)+"px");
	
}
function bottomleftdivHeight(){
	return $("#ibox-contentdiv").outerHeight()+$("#iboxdiv").outerHeight()+25;
}	 
function createJsTree(data){
	$('#using_json').jstree('destroy')
	$('#using_json').data('jstree', false).empty();
	$('#using_json').jstree({
            'core': {
            	 'data': data,
            	 "force_text": true
            }
            
	});
	
	
	$('#using_json').bind("select_node.jstree", function (e, data) {
		var type=data.node.li_attr.type;
        if(preType != type){
        	preType = '';
        	for(var i = 0 ; i<$('.displayFile').length;i++){
	    		$('.displayFile').eq(i).css("display","none");	
	    	}
        	if(type == 1){
	        		$('.displayFile').eq(0).css("display","block");
	        		TechnicalBulletin.show({
	        			pgdid : selectPgdId,
	        			yjtsJb1:data.node.li_attr.yjtsJb1,
	        			yjtsJb2:data.node.li_attr.yjtsJb2,
	        			yjtsJb3:data.node.li_attr.yjtsJb3
	        		});
	        	}else if(type == 2){
	        		$('.displayFile').eq(1).css("display","block");
	        		TechnicalOrder.show({
	        			pgdid : selectPgdId
	        		});
	        	}else if(type == 3 || type == 8 || type == 9){
	        		$('.displayFile').eq(2).css("display","block");
	        		var tzslx = (type == 3?1:(type == 8?2:3));
	        		var titleHead = '修订维修方案/Amendment Maintenance';
	        		if(type == 8){
	        			titleHead = '修订MEL/Amendment MEL';
	        		}
	        		if(type == 9){
	        			titleHead = '修订工卡/Amendment Card';
	        		}
	        		Amendmentnotice.show({
	        			pgdid : selectPgdId,
	        			tzslx : tzslx,
	        			titleHead : titleHead,
	        			yjtsJb1:data.node.li_attr.yjtsJb1,
	        			yjtsJb2:data.node.li_attr.yjtsJb2,
	        			yjtsJb3:data.node.li_attr.yjtsJb3
	        		});
	        	}else if(type == 6){
	        		if(data.node.li_attr.indexnum==0){
	            		$('.displayFile').eq(3).css("display","block");
	            		if(preIndexnum != type){
	            			Engineeringorder.show({
		            			pgdid : selectPgdId
		            		});
	            			preIndexnum = type;
	            		}
	        		}else if(data.node.li_attr.indexnum==1){
	            		$('.displayFile').eq(4).css("display","block");
            			var gczlid = '';
	            		if(data.node.parent == type){
	            			gczlid = data.node.id;
	            		}else{
	            			gczlid = data.node.parent;
	            		}
	            		if(preIndexnum != gczlid){
		            		WorkOrder.show({
		            			searchParam:{gczlid : gczlid} ,//查询参数
		            			url: basePath+"/project/workorder/queryByGczlId"
		            		});
		            		preIndexnum = gczlid;
	            		}
	        		}
	        	}else if(type == 4 || type == 5){
	        		$('.displayFile').eq(4).css("display","block");
	        		var gdlx = (type == 4?2:3);
	        		WorkOrder.show({
	        			searchParam:{pgdid : selectPgdId,gdlx : gdlx} ,//查询参数
	        			url: basePath+"/project/workorder/queryByPgdIdAndGdlx"
	        		});
	        	}
        	}
        	if(type != 6){
        		preType = type;
        	}
     });
}
	 
//指定结束
function shutDown(this_,isEdit){
	var id = $(this_).attr("djid");
	var sqdh = $(this_).attr("sqdh");
	var zdjsyy = $(this_).attr("zdjsyy");
	var zdjsr = $(this_).attr("zdjsr");
	var zdjsrq = $(this_).attr("zdjsrq");
	AssignEndModal.show({
		chinaHead:'评估单号',//单号中文名称
 		englishHead:'Assessment NO.',//单号英文名称
 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
 		jsdh:sqdh,//指定结束单号
 		jsr:zdjsr,//指定结束人
 		jssj:zdjsrq,//指定结束时间
 		jsyy:zdjsyy,//指定结束原因
 		callback: function(data){//回调函数
 		}
 	});
}
		
// 检查技术文件指定结束
function checkUpdMt(id){
	var flag = false;
	AjaxUtil.ajax({
 		url:basePath+"/project/technicalfile/checkUpdMt",
 		type:"post",
 		async: false,
 		data:{
 			'id' : id
 		},
 		dataType:"json",
 		success:function(data){
 			finishWait();// 结束Loading
 			if (data.state == "success") {
 				flag = true;
 			} else {
 				AlertUtil.showErrorMessage(data.message);
 			}
 		}
 	});
 	return flag;
}
	 
// 查看
function find(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findTechnicalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
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
	
// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
// 信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}
		
// 搜索条件重置
function searchreset(){
	var zzjgid=$('#zzjgid').val();
	var yjts=$('#yjts').val();
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
		 
// 回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#keyword_search").is(":focus")){
			searchRevision();      
		}
	}
}
		 
// 改变机型工程师和制单人 列表内容
function changeContent(){
	var dprtcode=$("#zzjg").val();
	$("#fl").empty();
	$("#fl").append("<option value='' >显示全部</option>");
	$("#wjlx").empty();
	$("#wjlx").append("<option value='' >显示全部</option>");
	$("#ly").empty();
	$("#ly").append("<option value='' >显示全部</option>");
	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
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
 			finishWait();// 结束Loading
 			$('#zdrid').empty();
 			$('#jxgcs').empty();
 			appendChangeContent(data.userToRole);
 		}
 	});
	fjjxjz();
}
// 拼接机型工程师和制单人 列表内容
function appendChangeContent(data){
	var appendChangeContent='';
	 appendChangeContent=appendChangeContent+"<option value=''>显示全部</option>";
	 $.each(data,function(index,row){
		 appendChangeContent=appendChangeContent+"<option value='"+row.id+"'>"+StringUtil.escapeStr(row.displayName)+"</option>";
	 });
	 $('#zdrid').html(appendChangeContent);
	 $('#jxgcs').html(appendChangeContent);
}
/**
* 技术指令上传文件导出
*/
function technicalFileUploadOutExcel() {
 window.location.href = "technicalFileOutExcel?jx=" + encodeURIComponent($("#jx").val()) + "&fl="
 			+ encodeURIComponent($("#fl").val())+"&wjlx="+$("#wjlx").val() + "&zt=" + $("#zt").val() + "&pgr="
 			+ $("#jxgcs").val() + "&ly=" + encodeURIComponent($("#ly").val()) + "&dprtcode="
 			+ $("#zzjg").val() + "&yjts=" + yjtsJb1 + "&keyword="
 			+ encodeURIComponent($("#keyword_search").val());
}

//附件下载
function downfile(id){
	//window.open(basePath + "/project/technicalfile/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00101)
}
//获取预警颜色
function getWarningColor(bgcolor,syts,zt,yjtsJb1,yjtsJb2){
	if(!(zt == 0 || zt==5 || zt==6)){
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

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}

/**
 * 打印
 * @param id
 */
function printTechnicalApproval(id){
	window.open(
			basePath+"/project/technicalfile/export/pdf/"+id,
			"_blank",
			"top=200,left=200,height=600,width=800,status=yes,toolbar=0,menubar=no,location=no,scrollbars=yes");
}