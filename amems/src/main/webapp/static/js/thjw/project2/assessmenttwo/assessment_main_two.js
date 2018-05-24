
var choseAllIds=[];    //下达指令集合
var assessmentList=''; //技术评估单List
var ly=[]; //来源
	ly[1]="适航性资料";
	ly[9]="其他";
var yjtsJb1 = '';	
var yjtsJb2 = '';
var yjtsJb3 = '';

/**
 *加载js
 */
$(function(){
	Navigation(menuCode, '', '', 'GC-2-1 ', '林龙', '2017-08-01', '林龙', '2017-08-01');//加载导航栏
    initFjjxOption();				//初始化机型
    initAutoComplete();
    //执行待办
	if(todo_ywid) {
		Assessment_Update_Modal.open(todo_ywid);
		todo_ywid = null;
	}
	
    loadMonitorsettings();			//初始化预警阀值
    wjlxMultiselect();				//初始化生成多选下拉框-文件类型
    decodePageParam();				//解码页面查询条件和分页 并加载数据
    refreshPermission();			//权限初始化

	logModal.init({code:'B_G2_001'});//初始化日志功能
});


/**
 * 初始化成多选下拉框-文件类型
 */
function wjlxMultiselect(){
	$("#wjlxHtml").empty();
	$("#wjlxHtml").html("<select class='form-control' id='wjlx'  multiple='multiple' ></select>");
	DicAndEnumUtil.registerDic('16','wjlx',$("#dprtcode").val());
	//生成多选下拉框
	$('#wjlx').multiselect({
		buttonClass : 'btn btn-default ',
		buttonWidth: '100%',
		numberDisplayed:100,
		buttonContainer: '<div class="btn-group" style="width:100%;" />',
		includeSelectAllOption : true,
		nonSelectedText:'显示全部 All',
		allSelectedText:'显示全部 All',
		selectAllText: '选择全部 Select All',
		onChange : function(element, checked) {
		}
	});
	
}

/**
 * 初始化机型
 */
function initFjjxOption(){
	$("#jx").html("<option value='' >显示全部All</option>");
	var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : $("#dprtcode").val()});
	if(typeList.length > 0){
		for(var i = 0; i < typeList.length; i++){
			$("#jx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
		}
	}else{
		$("#jx").html("<option value=''>暂无飞机机型No data</option>");
	}
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	TableUtil.resetTableSorting("assessmentTable");
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#dprtcode").val(params.dprtcode);
		
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());     //组织机构
	params.keyword = $.trim($("#keyword_search").val());//关键字
	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 */
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

/**
 * 执行技术文件预警阀值（机构代码）
 */
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/project2/assessment/getTechnicalThreshold",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			
	/*		$("#pgrid").html("<option value='' >显示全部All</option>");
			if(data.userList.length > 0){
				for(var i = 0; i < data.userList.length; i++){
					if(data.userList[i].id==userId){
						$("#pgrid").append("<option value='"+StringUtil.escapeStr(data.userList[i].id)+"' selected='selected'>"+StringUtil.escapeStr(data.userList[i].displayName)+"</option>");
					}else{
						$("#pgrid").append("<option value='"+StringUtil.escapeStr(data.userList[i].id)+"'>"+StringUtil.escapeStr(data.userList[i].displayName)+"</option>");
					}
				}
			}else{
				$("#pgrid").html("<option value=''>暂无评估工程师No data</option>");
			}*/
		
			isLoadMonitor = true;
		}
	});
}

/**
 * 切换组织机构
 */
function onchangeDprtcode(){
    initFjjxOption();				//初始化机型
    loadMonitorsettings();			//初始化预警阀值
    wjlxMultiselect();				//初始化生成多选下拉框-文件类型
}

function search(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("assessmentTable");
}

/**
 * 查询条件
 */
function gatherSearchParam (){
	var searchParam={};
	var paramsMap={};
	searchParam.jx=$.trim($("#jx").val()); 						//机型
	searchParam.zt=$.trim($("#zt").val());						//状态
	searchParam.lylx=$.trim($("#lylx").val());					//来源
	searchParam.pgrid=$.trim($("#pgrids").val());				//评估工程师
	searchParam.dprtcode=$.trim($("#dprtcode").val());			//组织机构
	searchParam.keyword=$.trim($('#keyword_search').val());//关键字
	var ztList="";//待处理状态集合
	if($('input:checkbox[name=ztList]:checked').val()=="on"){
		ztList="-1,1,5,6";
	}
	if($("#bbList").prop("checked")==true){
		searchParam.bBbid="bBbid";     //该版本的后版本id
	}
	var wjlx = $("#wjlx").val();
	var wjlxList=[];
	 if(wjlx != null){
			for(var i = 0 ; i < wjlx.length ; i++){
				if('multiselect-all' != wjlx[i]){
					wjlxList.push(wjlx[i]);
				}
			}
		}
	 if(wjlxList != null && wjlxList.length > 0){
		 paramsMap.wjlxList=wjlxList;
	 }
	 
	var sylb=$.trim($("#sylb").val());		//适用类别
	var pgrq=$.trim($("#pgrq").val()); 		//获取评估日期
    var pgBeginDate="";				//评估开始日期 
    var pgEndDate="";				//评估结束日期
	if(null != pgrq && "" != pgrq){
		 pgBeginDate= pgrq.substring(0,4)+"-"+pgrq.substring(5,7)+"-"+pgrq.substring(8,10)+" 00:00:00";
		 pgEndDate= pgrq.substring(12,17)+"-"+pgrq.substring(18,20)+"-"+pgrq.substring(21,23)+" 23:59:59";
	}
	paramsMap.ztList=ztList;
	paramsMap.pgBeginDate=pgBeginDate;
	paramsMap.pgEndDate=pgEndDate;
	paramsMap.sylb=sylb;
	searchParam.paramsMap=paramsMap;
	
	return searchParam;
}

/**
 * 查询主单分页列表
 */
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	
	var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		
		AjaxUtil.ajax({
		   url:basePath+"/project2/assessment/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
	 			if(data.total >0){
	 				assessmentList=data.rows;
	 			appendContentHtml(data.rows);
	 			var page_ = new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}
				});
	 			// 标记关键字
				signByKeyword($("#keyword_search").val(), [3,6, 16,17, 19]);
	 			}else{
		 			$("#airworthiness_list").empty();
					$("#pagination").empty();
					$("#airworthiness_list").append("<tr><td class='text-center' colspan=\"24\">暂无数据 No data.</td></tr>");
	 			}
	 			hideBottom();
	 			new Sticky({tableId:'assessmentTable'}); //初始化表头浮动
	 			
	    }
  }); 	
}

/**
 * 表格拼接
 */
function appendContentHtml(list){
		 var htmlContent = '';
		 choseAllIds=[];
		 $.each(list,function(index,row){
			  choseAllIds.push(index);
				htmlContent += "<tr  id='"+row.id+"' onclick=\"showHiddenContent(this)\">";
				
				htmlContent += "<td class='fixed-column text-center'>";
				
			    if( row.zt == -1 || row.zt == 1 || row.zt == 5 || row.zt == 6){ //状态为初始，保存，审核驳回，批准驳回可以修改
				   if(row.pgrid==userId){ //当前评估人为
					   htmlContent += "<i class=' icon-edit color-blue cursor-pointer checkPermission' " 
					   + " permissioncode='project2:assessment:main:02' onClick=\"Assessment_Update_Modal.open('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";//修改
					   htmlContent += "<i class='iconnew-auditor color-blue cursor-pointer checkPermission' " 
						   + " permissioncode='project2:assessment:main:06' onClick=\"Assessment_Update_Assessor_Modal.open('"+row.id+"')\" title='修改评估人 Edit Assessor'></i>&nbsp;&nbsp;";//修改
				   }
			    }
			    
			    if(row.zt == 2||row.zt == 3||row.zt == 4 ||row.zt == 10){ //状态为提交后，审核后和批准,完成后才可以关闭
			    	htmlContent += "<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='project2:assessment:main:05' "
		    		+ "pgdh='"+row.pgdh+"' gbyy=''  zt='"+row.zt+"' " 
		    		+ "onclick=\"shutDown('"+row.id+"', this , true)\" title='关闭 Close'></i>&nbsp;&nbsp;";//关闭
			    }
			    
			    if(row.zt == 1||row.zt == 5||row.zt == 6){ //状态为保存并且来源类型为其他才可以删除
			    	if(row.lylx==9 && row.pgrid==userId){
			    		htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:assessment:main:03' " 
		    			+ " onClick=\"invalid('"+ row.id + "','"+ row.zt +"')\" title='删除 Delete'></i>&nbsp;&nbsp;"; 
			    	}
			    }
			    
			    if( row.zt ==9||row.zt ==10){ //状态为指定结束-完成才可以改版
			    	 if((row.bBbid==null&&row.fbbid!=null) || (row.bBbid==null&&row.fbbid==null)){ //评估单人为当前人，后版本id为null，前版本id不为null
				    	htmlContent += "<i class='icon-pencil  color-blue cursor-pointer checkPermission' permissioncode='project2:assessment:main:04' " 
				    	+ " onclick=\"Assessment_Revision_Modal.open('"+row.id+"')\" title='改版 Revision'></i>&nbsp;&nbsp;";
			    	 }
			    }
			     
			    htmlContent += "</td>";
			    
			    if( row.zt !==9 && row.zt !==10 && row.zt != 4){ //状态为指定结束-完成才可以改版
			    	htmlContent +=getWarningColor("#f9f9f9",parseInt(row.paramsMap.syts),row.zt,row.pgqx); 
			    }else{
			    	htmlContent += "<td></td>";
			    }
			    
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pgdh)+"' ><a href=\"javascript:findAssessmentView('"+row.id+"')\">"+StringUtil.escapeStr(row.pgdh)+"</a></td>";
			  
			    if(StringUtil.escapeStr(row.bb)!=''){
			    	htmlContent += "<td class='text-center' title='R"+StringUtil.escapeStr(row.bb)+"'>R"+StringUtil.escapeStr(row.bb)+"</td>";
			    }else{
			    	htmlContent += "<td></td>";
			    }
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jx)+"'>"+StringUtil.escapeStr(row.jx)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pgdzt)+"'>"+StringUtil.escapeStr(row.pgdzt)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pgr.id)+"'>"+StringUtil.escapeStr(row.pgr.id)+"</td>";
			    
			    if(StringUtil.escapeStr(row.paramsMap.glpgdh)!=null&&StringUtil.escapeStr(row.paramsMap.glpgdh)!=''){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.glpgdh)+" "+StringUtil.escapeStr(row.paramsMap.glpgdbb)+"'><a href=\"javascript:findAssessmentView('"+row.glpgdid+"')\">"+StringUtil.escapeStr(row.paramsMap.glpgdh)+" R"+StringUtil.escapeStr(row.paramsMap.glpgdbb)+"</a></td>";
			    }else{
			    	htmlContent += "<td></td>";
			    }
			    
			    
			    htmlContent += "<td class='text-left' > "+giveInstruction(row.giveInstructionList,index)+"</td>"; //加载下达指令信息
			    
			    htmlContent += "<td class='text-left' >"; 
			    if(row.zt==9||row.zt==10){
			    	htmlContent += "<a href='javascript:void(0);'  " 
			    	+ "pgdh='"+StringUtil.escapeStr(row.pgdh)+"'   zt='"+row.zt+"' gbrid='"+row.gbr.id+"' " 
			    	+ "gbrq='"+row.gbrq+"'   gbyy='"+StringUtil.escapeStr(row.gbyy)+"' " 
			    	+"onclick=\"shutDown('"+row.id+"', this , false)\">"+DicAndEnumUtil.getEnumName('technicalStatusEnum',row.zt)+"</a></td>";
			    }{
			    	htmlContent += DicAndEnumUtil.getEnumName('technicalStatusEnum',row.zt); 
			    }
			    htmlContent += "</td>"; 
			    
			    htmlContent += "<td class='text-center' title='"+formatUndefine(row.pgqx).substring(0,10)+"'>"+formatUndefine(row.pgqx).substring(0,10)+"</td>";
			    if(formatUndefine(row.pgqx)==''){
			    	htmlContent += "<td class='text-center'></td>";
			    }else{
			    	if(row.zt==9||row.zt==10 || row.zt == 4){
			    		htmlContent += "<td class='text-center'>-</td>";
			    	}else{
			    		htmlContent += "<td class='text-center' title='"+formatUndefine(row.paramsMap.syts?parseInt(row.paramsMap.syts):0)+"'>"+formatUndefine(row.paramsMap.syts?parseInt(row.paramsMap.syts):0)+"</td>";
			    	}
			    }
			    
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(ly[row.lylx])+"'>"+StringUtil.escapeStr(ly[row.lylx])+"</td>";
			   
			    if(row.technicalAttached!=null||row.technicalAttached!=''){
			    	htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('compnentTypeEnum',StringUtil.escapeStr(row.technicalAttached).sylb)+"'>"+DicAndEnumUtil.getEnumName('compnentTypeEnum',StringUtil.escapeStr(row.technicalAttached).sylb)+"</td>";
			    }else{
			    	htmlContent += "<td class='text-left' ></td>";
			    }
			 	if(StringUtil.escapeStr(row.airworthiness.jswjlx)!=''&&StringUtil.escapeStr(row.airworthiness.jswjlx)!=null){
		    		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.airworthiness.jswjlx)+"'>"+StringUtil.escapeStr(row.airworthiness.jswjlx)+"</td>"; 
		    	}else{
		    		htmlContent += "<td class='text-left' ></td>";
		    	}
			    if(StringUtil.escapeStr(row.airworthiness.jswjbh)!=null&&StringUtil.escapeStr(row.airworthiness.jswjbh)!=''){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.airworthiness.jswjbh)+" R"+StringUtil.escapeStr(row.airworthiness.bb)+"'><a href=\"javascript:airworthinessView('"+row.jswjid+"')\">"+StringUtil.escapeStr(row.airworthiness.jswjbh)+" R"+StringUtil.escapeStr(row.airworthiness.bb)+"</a></td>"; 
			    }else{
			    	htmlContent += "<td class='text-left' ></td>";
			    }
		    	if(row.airworthinessAttachment != null){
	    			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.airworthinessAttachment.wbwjm)+"."+StringUtil.escapeStr(row.airworthinessAttachment.hzm)+"'><a href=javascript:Assessment_Open_Modal.downfile('"+row.airworthinessAttachment.id+"') >"+StringUtil.escapeStr(row.airworthinessAttachment.wbwjm)+"."+StringUtil.escapeStr(row.airworthinessAttachment.hzm)+"</a></td>"; 
		    	}else{
		    		htmlContent += "<td class='text-left' ></td>";
		    	}
		    	if(StringUtil.escapeStr(row.paramsMap.gljswjbh)!=''&&StringUtil.escapeStr(row.paramsMap.gljswjbh)!=null){
		    		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.gljswjbh)+" R"+StringUtil.escapeStr(row.paramsMap.gljswjbb)+"'><a class='cursor-pointer' href=\"javascript:relatedAirworthinessView('"+row.paramsMap.gljswjid+"')\">"+StringUtil.escapeStr(row.paramsMap.gljswjbh)+" R"+StringUtil.escapeStr(row.paramsMap.gljswjbb)+"</a></td>"; 
		    	}else{
		    		htmlContent += "<td class='text-left' ></td>";
		    	}
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fixChapter.zjh)+"'>"+StringUtil.escapeStr(row.fixChapter.zjh)+"</td>"; 
				
			    htmlContent += "<td title='附件 Attachment' class='text-center'>";
				if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) || (row.technicalAttached != null && row.technicalAttached != "")){
					if(row.technicalAttached.jlfjid!=null||(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0)){
						htmlContent += '<i qtid="'+row.id+'" jlfjid="'+row.technicalAttached.jlfjid+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					}
				}
				htmlContent += "</td>";
				htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.airworthiness.sxrq).substring(0,10)+"'>"+StringUtil.escapeStr(row.airworthiness.sxrq).substring(0,10)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.pgsj)+"'>"+StringUtil.escapeStr(row.pgsj)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#airworthiness_list").empty();
		   $("#airworthiness_list").html(htmlContent);
		   init();
		   refreshPermission();			//权限初始化
		 });
}

/**
 *查询技术评估单
 */
function findAssessmentView(id){
	window.open(basePath+"/project2/assessment/view?id="+id);
}

/**
 *查询适航性资料 
 */
function airworthinessView(id){
	window.open(basePath+"/project2/airworthiness/view?id="+id);
}

/**
 *查询关联适航性资料 
 */
function relatedAirworthinessView(id){
	window.open(basePath+"/project2/airworthiness/view?id="+id);
}

/**
 *加载下达指令 
 */
function giveInstruction(orderList,index){
		var htmlContent='';
	if(orderList!=null){
		var falg=true;
		   for (var i = 0; i < orderList.length; i++) {
			   if(orderList[i].zlxl!=''){
				
				   htmlContent +=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',orderList[i].zlxl));
				   
				   if(falg==true){
					   if(orderList[i+1]!=null){
						   htmlContent += "<i class='icon-caret-down cursor-pointer' " 
							   	+ " id='"+ index+ "icon' onclick=vieworhideWorkContent('"+ index + "')></i>" 
						   		+ "<div id='"+ index + "content' style='display:none;white-space:nowrap;'>";
					   }
					
					   falg=false;
				   }else{
					   htmlContent +="<br>";
				   }
				
				   if (i != 0 && i == orderList.length - 1) {
						htmlContent += "</div>";
						
				   }
			   }
		   }
	} 
	  

  return htmlContent;
}
	
/**
 * 点击表头展开表格
 */
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
			 $("#"+choseAllIds[i]+'content').fadeIn(500);
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
		 }else{
			 $("#"+choseAllIds[i]+'content').hide();
			 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
			 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
		 }
	 }
 new Sticky({tableId:'assessmentTable'});
}

function vieworhideWorkContent(i){
	 var flag = $("#"+choseAllIds[i]+'content').is(":hidden");
	 if(flag){
		 $("#"+choseAllIds[i]+'content').fadeIn(500);
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
	 }else{
		 $("#"+choseAllIds[i]+'content').hide();
		 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
		 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
	 }
	 new Sticky({tableId:'assessmentTable'});
}

/**
 * 点击附件展开附件列表
 */
function init(){
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#work_card_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
	
 }

function getHistoryAttachmentList(obj){
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	   	        ,{mainid : $(obj).attr('jlfjid'), type : '结论附件'}
	   	    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}

//关闭
function shutDown(id,e,isEdit){
	var pgdh=$(e).attr("pgdh"); //评估单号
	var gbyy=$(e).attr("gbyy"); //关闭原因
	var gbrq=$(e).attr("gbrq"); //关闭日期
	var gbrid=$(e).attr("gbrid");//关闭人
	var zt=$(e).attr("zt"); //状态
 	AssignEndModal.show({
 		chinaHead:'评估单号',//单号中文名称
 		englishHead:'Evaluation No.',//单号英文名称
 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
 		jsdh:pgdh,//指定结束单号
 		jsr:gbrid,//指定结束人
 		jssj:gbrq,//指定结束时间
 		jsyy:gbyy,//指定结束原因
 		zt:zt,//指定结束原因
 		callback: function(data){//回调函数
 			if(null != data && data != ''){
 				
 				var paramsMap = {};
 				paramsMap.currentZt = zt; //当前状态
 				data.paramsMap = paramsMap;
 				
 				data.id=id; //评估单id
 				gbSubjectFrom(data);//指定结束提交
 			}
 		}
 	});
}

//关闭提交
function gbSubjectFrom(obj){
	 startWait($("#AssignEndModal"));
   	 AjaxUtil.ajax({
 		url:basePath+"/project2/assessment/endModal",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		modal:$("#AssignEndModal"),
 		success:function(data){
 			finishWait($("#AssignEndModal"));
 			AlertUtil.showMessage('关闭成功!');
 			$("#AssignEndModal").modal("hide");
 			decodePageParam();
 			hideBottom();
 		}
   	  });
}


/**
 *删除
 */
function invalid(id,zt){
	var obj = {};
	
	var paramsMap = {};
	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 
	 startWait();
   	 AjaxUtil.ajax({
   		url:basePath + "/project2/assessment/delete",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		success:function(data){
 			finishWait();
 			AlertUtil.showMessage("删除成功!");
 			decodePageParam();
 		}
   	  });
   	 
 }});
}


//弹出查看评估单	
function findto(id){
	window.open(basePath+"/project2/assessment/find/"+$.trim(id));
}

/**
 *点击行选择 
 */
function showHiddenContent(this_){
 	var id = $(this_).attr("id");
 	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
 	if($(".bottom_hidden_content").css("display")=='block'){
 		$(".bottom_hidden_content").slideUp(100);
 	}
 	$(".bottom_hidden_content").slideDown(100);
 	new Sticky({tableId:'assessmentTable'}); //初始化表头浮动
 	
	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
	}
 	bottom_hidden_content.anOrder(id);//加载下达指令数据
	App.resizeHeight();
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(this_), $("#tableId"));
	}
 	
 	 
}	
	
/**
 *熏染预警颜色 
 */
function getWarningColor(bgcolor,syts,zt,pgqx){
	
	if (pgqx == "") {
		return "<td  class='text-center fixed-column'></td>";
	}
	if (yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2) {
		bgcolor = warningColor.level2;// 黄色		 
		return "<td  class='text-center fixed-column' title='"+yjtsJb1+"天&lt;剩余评估期限&lt;="+yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
	}
	if (Number(syts) <= yjtsJb1) {
		bgcolor = warningColor.level1;// 红色
		return "<td  class='text-center fixed-column' title='剩余评估期限&lt;="+yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
	}
	return "<td  class='text-center fixed-column'></td>";
}	

$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
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
 
//搜索条件重置
function searchreset(){
	
	
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	 });
	 
	 //$("#pgrid").val(displayName);//评估工程师 为当前登录人
	 
	 var SelectArr1 = $("#jxdiv select"); //机型重置为第一个
	 if(SelectArr1[0].options[0] != undefined){
		 
	 SelectArr1[0].options[0].selected = true;
	 }
	 
	 $("input:checkbox[name='ztList']").attr("checked",true);
	 
	 $("#keyword_search").val("");
	 $("#dprtcode").val(userJgdm);
	 $("#pgrids").val(userName);
	 $("input:checkbox[name='bbList']").attr("checked",false);
	 wjlxMultiselect();
     initFjjxOption();				//初始化机型
     loadMonitorsettings();			//初始化预警阀值
     wjlxMultiselect();				//初始化生成多选下拉框-文件类型
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

//搜索条件显示与隐藏 
function openOrHide() {
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

function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}

function exportExcel(){
	var jx=$.trim($("#jx").val()); 						//机型
	var zt=$.trim($("#zt").val());						//状态
	var lylx=$.trim($("#lylx").val());					//来源
	var pgrid=$.trim($("#pgrids").val());				//评估工程师
	var dprtcode=$.trim($("#dprtcode").val());			//组织机构
	var keyword=$.trim($('#keyword_search').val());		//关键字
	
	var ztList="";//待处理状态集合
	if($('input:checkbox[name=ztList]:checked').val()=="on"){
		ztList="-1,1,5,6";
	}
	
	var wjlx = $("#wjlx").val();
	var wjlxList=[];
	 if(wjlx != null){
			for(var i = 0 ; i < wjlx.length ; i++){
				if('multiselect-all' != wjlx[i]){
					wjlxList.push(wjlx[i]);
				}
			}
		}
	 if(wjlxList != null && wjlxList.length > 0){
		 paramsMap.wjlxList=wjlxList;
	 }
	 
	var sylb=$.trim($("#sylb").val());		//适用类别
	var pgrq=$.trim($("#pgrq").val()); 		//获取评估日期
    var pgBeginDate="";				//评估开始日期 
    var pgEndDate="";				//评估结束日期
	if(null != pgrq && "" != pgrq){
		 pgBeginDate= pgrq.substring(0,4)+"-"+pgrq.substring(5,7)+"-"+pgrq.substring(8,10)+" 00:00:00";
		 pgEndDate= pgrq.substring(12,17)+"-"+pgrq.substring(18,20)+"-"+pgrq.substring(21,23)+" 23:59:59";
	}

	window.open(basePath+"/project2/assessment/TechnicalAssessment.xls?dprtcode="
 				+ dprtcode 
 				+ "&jx="+jx+"&zt="+zt
 				+ "&lylx="+lylx+"&pgrid="+encodeURIComponent(pgrid)
 				+ "&ztList="+encodeURIComponent(ztList)+"&pgBeginDate="+encodeURIComponent(pgBeginDate)
 				+ "&pgEndDate="+encodeURIComponent(pgEndDate)+"&sylb="+encodeURIComponent(sylb)
 				+ "&keyword="+ encodeURIComponent(keyword));
	
}