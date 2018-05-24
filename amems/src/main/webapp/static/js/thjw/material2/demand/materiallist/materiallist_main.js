$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
			}
	);
	var dprtcode=$("#dprtcode_search").val();
	$("#xqlb_search").html("<option value=''>显示全部All</option>");
	DicAndEnumUtil.registerDic('84','xqlb_search',dprtcode);
	//飞机注册号
	initfjzch(dprtcode); 
})
//切换组织机构
function dprtChange(){
	initfjzch($("#dprtcode_search").val());
	$("#xqlb_search").empty();
	$("#xqlb_search").html("<option value=''>显示全部All</option>");
	DicAndEnumUtil.registerDic('84','xqlb_search',dprtcode);
}
//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		planeRegOption += "<option value='' >"+"显示全部 All"+"</option>";
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
		planeRegOption += "<option value='-' >N/A</option>";
	}else{
		planeRegOption += "<option value='-1' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch_search").html(planeRegOption); 
	materiallistMain.reload();
}

var attrStr=[];
function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
function customResizeHeight(bodyHeight, tableHeight){
	 //多列表高度设定
    if($(".displayContent").is(":hidden")){
    	$(".table-height").attr('style', 'height:' + tableHeight + 'px !important;overflow-x: auto;');
    }else{
    	$table = $(".table-height");
    	if($table.length > 0){
    		var cHeight = $table.attr("c-height");
    		var tempTableHight = tableHeight*0.45;
    		if(cHeight){
    			if(cHeight.indexOf("%") > 0){
    				cHeight = cHeight.replace("%","");
    				cHeight = cHeight/100;
    				tempTableHight = tableHeight * cHeight;
    			}else{
    				tempTableHight = cHeight;
    			}
    		}
    	 $table.attr('style', 'height:' + tempTableHight + 'px !important;overflow: auto;');
		 var tableHeightNew=$(".table-height:visible").outerHeight()||0;
		 var rowHeight=$(".row-height").outerHeight()||0;
		 var paginationHeight = $('.page-height:visible').outerHeight()||0;
		 var bottom_hidden_tab = bodyHeight - tableHeightNew-rowHeight-paginationHeight -5;
		 var tabHeader=$(".displayContent ul#myChildTab").eq(0).outerHeight()||0;
		 var tabBodyHeight=bottom_hidden_tab-tabHeader-5;
		 $(".displayContent .tab-pane").css({"height":tabBodyHeight+"px","overflow":"auto"});
    	}
    }
		
   
}
function view(){
	window.location.href = basePath+"/material/demand/materiallist/view";
}


//需求统计分析
var xutjIdList = [];
function analysisView(){
	if(xutjIdList.length <= 0){
		AlertUtil.showMessage("请先选择数据！");
	}else{
		analysis_open_alert.show({
			idList : xutjIdList, //已经选择的
			dprtcode : userJgdm,
			callback: function(){//回调函数
				
			}
		});
	}
}
//清除选中的数据
function clearSelect(){
	xutjIdList = [];
}
//全选
function selectAllBtn(){
	SelectUtil.performSelectAll('work_card_main_table_top_div');
	$("#materiallist_list").find("tr").each(function(){
		var mid = $(this).attr("id");
		if(mid != null && xutjIdList.indexOf(mid) == -1){
			xutjIdList.push(mid);
		}
	});
}
//取消全选时处理
function clearSelectAllBtn(){
	SelectUtil.performSelectClear('work_card_main_table_top_div');
	$("#materiallist_list").find("tr").each(function(){
		var mid = $(this).attr("id");
		xutjIdList.remove(mid);
	});
}
//选中或取消选中
function selectOrUnselect(flag, id){
	if(flag){
		xutjIdList.push(id);
	}else{
		xutjIdList.remove(id);
	}
}



function processModal(){
	var obj = checkedbox(true);
	if(obj.sjsl <= 0){
		AlertUtil.showMessage("请先选择数据！");
		
	}else{
		$("#bzbz_process").val("");
		$("#sjsl").html(obj.sjsl);
		$("#fjzchsl").html(obj.fjzchsl);
		$("#bjsl").html(obj.bjsl);
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		$("#process_modal").modal("show");
	}
}
//编制合同
function editContract(){
	if(xutjIdList.length <= 0){
		AlertUtil.showMessage("请先选择数据！");
	}else{
		contract_type.show({
			selected : 10, //合同类型
			idList : xutjIdList, //已经选择的
			dprtcode : userJgdm,
			callback: function(){//回调函数
				
			}
		});
	}
}

//变更确认
function changeConfirm(){
	var obj = checkedbox(true);
	if(obj.sjsl <= 0){
		AlertUtil.showMessage("请先选择数据！");
	}else{
		AlertUtil.showConfirmMessage("确定要把已选数据进行更变确认吗？", {callback: function(){
			var obj = {};
			var idList = checkedbox(false);
			obj.idList = idList;
			obj.qrbs = 1;
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url:basePath+"/material/demand/updateBatch",
			    type: "post",
			    contentType:"application/json;charset=utf-8",
			    dataType:"json",
			    data:JSON.stringify(obj),
				success:function(message){
					finishWait();
					AlertUtil.showMessage("操作成功！");
					$("#process_modal").modal("hide");
					materiallistMain.reload();
				}
			});
				 
			 
		}});
	}
	
}

function processSave(){
	var obj = {};
	var idList = checkedbox(false);
	obj.idList = idList;
	obj.bzzt = $("input:radio[name='process_type']:checked").val();
	obj.bzbz =$("#bzbz_process").val();
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/material/demand/updateBatch",
	    type: "post",
	    contentType:"application/json;charset=utf-8",
	    dataType:"json",
	    data:JSON.stringify(obj),
		success:function(message){
			finishWait();
			AlertUtil.showMessage("操作成功！");
			$("#process_modal").modal("hide");
			materiallistMain.reload();
		}
	});
}

//获取选中的数据
function checkedbox(fl){
	if(fl){
		var obj = {};
		var idList = [];
		var bjhList = [];
		var fjzchList = [];
		$("#materiallist_list").find("tr input:checked").each(function(){
			var bjh = $(this).attr("bjh");
			var mid = $(this).attr("mid");
			var fjzch = $(this).attr("fjzch");
			if(bjh != null){
				bjhList.push(bjh);
			}
			if(mid != null){
				idList.push(mid);
			}
			if(fjzch != null){
				fjzchList.push(fjzch);
			}
		});
		if(bjhList.length >0){
			bjhList = listQc(bjhList);
		}
		if(fjzchList.length >0){
			fjzchList = listQc(fjzchList);
		}
		obj.sjsl=idList.length;
		var fjzchsl = 0;
		$.each(fjzchList,function(index,fj){
			if(fj){
				fjzchsl += 1;
			}
		});
		obj.fjzchsl=fjzchsl;
		obj.bjsl=bjhList.length;
		return obj;
	}else{
		var idList = [];
		$("#materiallist_list").find("tr input:checked").each(function(){
			var mid = $(this).attr("mid");
			if(mid != null){
				idList.push(mid);
			}
		});
		return idList;
	}

	
	
	
}

	function listQc(xmList){
 		var list = [];
 		$.each(xmList,function(index,oldList){
 			var fl = true;
 			$.each(list,function(index,newList){
 				if(newList == oldList){
 					fl = false;
 				}
 			});
 			if(fl){
 				list.push(oldList);
 			}
 		});
 		return list;
 	}

function hideBottom(){
	$(".displayContent").css("display","none");	
	$("#meteriallist_table tbody").find("tr").removeClass("bg_tr");
	App.resizeHeight();
}
function showHiddenContent(index,this_){
	var mainid = $(this_).attr("mainid");
	var id = $(this_).attr("id");
	lodingDetail(id,mainid);
	var $checkbox1 = $("#materiallist_list :checkbox[name='checkrow']:eq("+index+")");
	var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
	var checked = $checkbox1.is(":checked");
	$checkbox1.attr("checked", !checked);
	$checkbox2.attr("checked", !checked);
	
	if($("#bottom_hidden_content").css("display")=='block'){
 		$("#bottom_hidden_content").slideUp(100);
 	}
 	$("#bottom_hidden_content").slideDown(100);
	$(this_).siblings("tr").removeClass("bg_tr");
 	$(this_).addClass("bg_tr");
 	App.resizeHeight();
}

//分页后依旧选中复选框，表格中的每一行需要加一个ID
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};	

function selectTr(obj,e){
	 e = e || window.event;  
	if(e.stopPropagation) { //W3C阻止冒泡方法  
	    e.stopPropagation();  
	 } else {  
	     e.cancelBubble = true; //IE阻止冒泡方法  
	     
	 }
	if($(obj).attr("checked")){
		attrStr.push($(obj).parent("td").parent("tr").attr("id"));
	}else{
	attrStr.remove($(obj).parent("td").parent("tr").attr("id"));
	}
}



var materiallistMain={
		pagination : {},
		gatherSearchParam:function(){
			var searchParam={};
			 var paramsMap ={};
			if (id != "") {
				searchParam.id = id;
				id = "";
			}
			 paramsMap.fjzch=$("#fjzch_search").val();
			 paramsMap.dprtcode=$("#dprtcode_search").val();
			 paramsMap.xqlb=$("#xqlb_search").val();
			 paramsMap.bj=$("#bj_search").val();
			 paramsMap.tbr=$("#tbr_search").val();
			 paramsMap.xlh=$("#xlh_search").val();
			 paramsMap.isYxfx=$("#isYxfx_search").val();
			 paramsMap.isFjhtc=$("#isFjhtc_search").val();
			 paramsMap.jjcd=$("#jjcd_search").val();
			 paramsMap.keyword=$.trim($('#keyword_search').val());
			 searchParam.qrbs = 1;
			
			 var jhsyrq=$("#jhsyrq_search").val();
			 //计划使用日期
			 if(null != jhsyrq && "" != jhsyrq){
				 var jhsyrqBegin = jhsyrq.substring(0,10)+" 00:00:00";
				 var jhsyrqEnd = jhsyrq.substring(13,23)+" 23:59:59";
				 paramsMap.jhsyrqBegin = jhsyrqBegin;
				 paramsMap.jhsyrqEnd = jhsyrqEnd;
			 }else{
				 paramsMap.jhsyrqBegin = "";
				 paramsMap.jhsyrqEnd = "";
			 }
			  var bzztList=[];
			 if($("#bzzt_search1").attr("checked")){
				 bzztList.push(1);
			}
			 if($("#bzzt_search2").attr("checked")){
				 bzztList.push(2);
			 }
			 if($("#bzzt_search3").attr("checked")){
				 bzztList.push(3);
			 }
			 if($("#qrbs_search").attr("checked")){
				 searchParam.qrbs = 0;
			 }else{
				 searchParam.qrbs = 1;
			 }
			 if(bzztList.length>0){
				 paramsMap.bzztList=bzztList;
			 }
			 var wllbList = [0,1,4,5,6];
			 
			 paramsMap.wllbList=wllbList;
			 
			 searchParam.paramsMap=paramsMap;
			 console.info(searchParam)
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 _this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = _this.pagination;
			 
			 if(!searchParam.paramsMap ||searchParam.paramsMap.bzztList == null || searchParam.paramsMap.bzztList.length <= 0){
				 $("#materiallist_list").empty();
				 $("#materiallist_pagination").empty();
				 $("#materiallist_list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
				 return false;
			 }
			 if(id != ""){
				 	searchParam.id = id;
					id = "";
				}
			 AjaxUtil.ajax({
				   url:basePath+"/material/demand/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
			 			if(data.rows.length > 0){
						 objData=data.rows;
			 				
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "materiallist_pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[11,18,19]);
			 		
			 		}else{
			 			$("#materiallist_list").empty();
						$("#materiallist_pagination").empty();
						$("#materiallist_list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'meteriallist_table'});
			 			hideBottom();
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("meteriallist_table");
			hideBottom();
			clearSelect();//清除选中的数据
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent = htmlContent + "<tr onclick=showHiddenContent("+index+",this) id='"+row.id+"' mainid='"+row.mainid+"' >";
				
				var check = "";
				if(xutjIdList.indexOf(row.id) != -1){
					check = "checked='checked'";
				}
				
				htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input fjzch='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.fjzch))+"'" +
						"mid='"+StringUtil.escapeStr(formatUndefine(row.id))+"' bjh='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"' type='checkbox' name='checkrow' index="+index+" "+check+" onclick=\"SelectUtil.checkRow(this,'selectAllId')\" /></td>";
				
				
			    htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('demandSafeguardStatusEnum',row.xqbs)+"'>"+DicAndEnumUtil.getEnumName('demandSafeguardStatusEnum',row.xqbs)+"</td>";
			    var bzztText = '';
			    if(row.bzzt){
			    	bzztText = "("+DicAndEnumUtil.getEnumName('securityStatusEnum',row.bzzt)+")";
			    }
			    if(row.qrbs == 1){
			    	htmlContent += "<td class='text-center' title='已确认 "+bzztText+"'>已确认 "+bzztText+"</td>";
			    }else{
			    	htmlContent += "<td class='text-center' title='未确认 "+bzztText+"'>未确认 "+bzztText+"</td>";
			    }
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.xqlb))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.xqlb))+"</td>";
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.fjzch))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.fjzch))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";
			    htmlContent += "<td class='text-left' title='"+formatUndefine(row.paramsMap.Dywms)+formatUndefine(row.paramsMap.Dzwms)+"'>"+formatUndefine(row.paramsMap.Dywms)+formatUndefine(row.paramsMap.Dzwms)+"</td>";
			    htmlContent += "<td class='text-center' title='"+(row.paramsMap.jhsyrq?row.paramsMap.jhsyrq.substring(0,10):'')+"'>"+(row.paramsMap.jhsyrq?row.paramsMap.jhsyrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.xqsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.xqsl))+"</td>";
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcsl))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bzbz))+"'>"+StringUtil.escapeStr(formatUndefine(row.bzbz))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>"+StringUtil.escapeStr(formatUndefine(row.xlh))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.gmjy))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.gmjy))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.thj))+"'>"+StringUtil.escapeStr(formatUndefine(row.thj))+"</td>";
		    	if(row.paramsMap.jjcd == 1 ){
		    		htmlContent += "<td class='text-center' title='一般'>一般</td>";
		    	}else if(row.paramsMap.jjcd == 9){
		    		htmlContent += "<td class='text-center' title='紧急'>紧急</td>";
		    	}else{
		    		htmlContent += "<td class='text-center' title=''></td>";
		    	}
		    	if(row.paramsMap.is_yxfx == 0){
		    		htmlContent += "<td class='text-center' title='否'>否</td>";
		    	}else{
		    		htmlContent += "<td class='text-center' title='是'>是</td>";
		    	}
		    	if(row.paramsMap.is_fjhtc == 0){
		    		htmlContent += "<td class='text-center' title='否'>否</td>";
		    	}else{
		    		htmlContent += "<td class='text-center' title='是'>是</td>";
		    	}
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.bh))+"'><a href=\"javascript:view('"+row.mainid+"')\">"+StringUtil.escapeStr(formatUndefine(row.paramsMap.bh))+"</a></td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.xqyy))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.xqyy))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.lybh))+"'><a href=\"javascript:viewSource('"+row.paramsMap.lyid+"','"+row.paramsMap.lylx+"','"+row.paramsMap.bs145+"')\">"+StringUtil.escapeStr(formatUndefine(row.paramsMap.lybh))+"</a></td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.sqr?row.sqr.displayName:''))+"'>"+StringUtil.escapeStr(formatUndefine(row.sqr?row.sqr.displayName:''))+"</td>";
			    htmlContent += "</tr>" ;
		   $("#materiallist_list").empty();
		   $("#materiallist_list").html(htmlContent);
		   refreshPermission(); 
		 });
		}
}

function selectworkcard(e,index,id,name,obj){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
				+ index + ")");
		var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
				+ index + ")");
	    if(!$(obj).parents("table").hasClass("sticky-col")){
	    	var checked = $checkbox1.is(":checked");
	 		$checkbox1.attr("checked", checked);
	 		$checkbox2.attr("checked", checked);	
	 		selectOrUnselect($(obj).is(":checked"), $(obj).attr("mid"));//选中或取消选中
	    }else{
	    	var checked = $checkbox2.is(":checked");
	 		$checkbox1.attr("checked", checked);
	 		$checkbox2.attr("checked", checked);
	 		selectOrUnselect(checked, checked.attr("mid"));//选中或取消选中
	    }
}
function lodingDetail(id,mainid){
	//清空需求详情和流程信息
	emptyDetail();
	 AjaxUtil.ajax({
		 url:basePath+"/material/demand/selectDetailAndProcess",
		 type:"post",
		 async: false,
		 data:{
			 'id' : id,
			 'mainid' : mainid
		 },
		 dataType:"json",
		 success:function(data){
			 finishWait();
			 if(data.demandSafeguard){
				 appendDetail(data.demandSafeguard);
			 }
			 if(data.processRecordList){
				 appendProcess(data.processRecordList);
			 }else{
				 $("#process_list").empty();
				 $("#process_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
			 }
			 
			 
		 }
	 });
}
function emptyDetail(){
	$("#plan_feedback_replace_tab").find('input').val('');
	$("#plan_feedback_replace_tab").find('textarea').val('');
	$("#detail_list").empty();
	$("#detail_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
	$("#process_list").empty();
	$("#process_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
}
function appendDetail(obj){
	$("#bh").val(obj.bh);
	$("#zt").val(obj.paramsMap.ztText);
	$("#xqlb").val(obj.xqlb);
	$("#jhsyrq").val(obj.jhsyrq?obj.jhsyrq.substring(0,10):'');
	$("#sjbjh").val(obj.sjbjh);
	$("#sjbjmc").val(obj.sjbjmc);
	$("#fjzch").val(obj.fjzch);
	$("#xlh").val(obj.xlh);
	$("#fxxh").val(obj.fxxh);
	$("#fxsj").val(TimeUtil.convertToHour(obj.fxsj, TimeUtil.Separator.COLON));
	$("#xqyy").val(obj.xqyy);
	$("#gmjy").val(obj.gmjy);
	$("#sqr").val(obj.sqr?obj.sqr.displayName:'');
	$("#sqsj").val(obj.sqsj);
	$("#jhr").val(obj.jhr?obj.jhr.displayName:'');
	$("#jhsj").val(obj.jhsj);
	
	if(obj.jjcd == 9 ){
		$("#jjcd").attr("checked","checked");
	}else{
		$("#jjcd").attr("checked",false);
	}
	if(obj.isYxfx == 0){
		$("#isYxfx").attr("checked",false);
	}else{
		$("#isYxfx").attr("checked","checked");
	}
	if(obj.isFjhtc == 0){
		$("#isFjhtc").attr("checked",false);
	}else{
		$("#isFjhtc").attr("checked","checked");
	}
	
	
	var htmlAppend = "";
	if(obj.demandSafeguardDetailList != null && obj.demandSafeguardDetailList.length > 0 ){
		$.each(obj.demandSafeguardDetailList,function(index,row){
			htmlAppend += "<tr>";
			htmlAppend += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.wllb)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.wllb)+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjmc))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xingh))+"'>"+StringUtil.escapeStr(formatUndefine(row.xingh))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.gg))+"'>"+StringUtil.escapeStr(formatUndefine(row.gg))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>"+StringUtil.escapeStr(formatUndefine(row.xlh))+"</td>";
			htmlAppend += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.xqsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.xqsl))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jhly))+"'>"+StringUtil.escapeStr(formatUndefine(row.jhly))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.zjh))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bzjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bzjh))+"</td>";
			htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.thj))+"'>"+StringUtil.escapeStr(formatUndefine(row.thj))+"</td>";
			htmlAppend += "</tr>";
		});
		$("#detail_list").empty();
		$("#detail_list").append(htmlAppend);
	}else{
		$("#detail_list").empty();
		$("#detail_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
	}
}

function appendProcess(list){
	var htmlAppend = "";
	$.each(list,function(index,row){
		htmlAppend += "<tr>";
		htmlAppend += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.czsj))+"'>"+StringUtil.escapeStr(formatUndefine(row.czsj))+"</td>";
		htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.czrmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.czrmc))+"</td>";
		htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bmmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.bmmc))+"</td>";
		htmlAppend += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.czsm))+"'>"+StringUtil.escapeStr(formatUndefine(row.czsm))+"</td>";
	});
	 $("#process_list").empty();
	 $("#process_list").append(htmlAppend);
}
function orderBy(obj){
	// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		clearSelect();//清除选中的数据
		materiallistMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}

//导出
function exportExcel(){
	var searchParam = materiallistMain.gatherSearchParam();
	if(!searchParam.paramsMap.xpgbsList){
		searchParam.paramsMap.xpgbsList = "";
	}
		window.open(basePath+"/material/demand/materiallist.xls?paramsMap[dprtcode]="
				+ encodeURIComponent(searchParam.paramsMap.dprtcode)+ "&paramsMap[fjzch]="+encodeURIComponent(searchParam.paramsMap.fjzch)+"&paramsMap[keyword]="
				+ encodeURIComponent(searchParam.paramsMap.keyword) + "&paramsMap[xqlb]=" + encodeURIComponent(searchParam.paramsMap.xqlb)+"&paramsMap[jhsyrqBegin]="
				+ encodeURIComponent(searchParam.paramsMap.jhsyrqBegin) + "&paramsMap[jhsyrqEnd]=" + encodeURIComponent(searchParam.paramsMap.jhsyrqEnd)+"&paramsMap[bj]="
				+ encodeURIComponent(searchParam.paramsMap.bj) + "&paramsMap[tbr]=" + encodeURIComponent(searchParam.paramsMap.tbr)+"&paramsMap[isFjhtc]="
				+ encodeURIComponent(searchParam.paramsMap.isFjhtc) + "&paramsMap[jjcd]=" + encodeURIComponent(searchParam.paramsMap.jjcd)+"&bzztList="
				+ encodeURIComponent(searchParam.paramsMap.bzztList) + "&qrbs=" + encodeURIComponent(searchParam.qrbs)+"&wllbList="
				+ encodeURIComponent(searchParam.paramsMap.wllbList)+"&paramsMap[xlh]="
				+ encodeURIComponent(searchParam.paramsMap.xlh) + "&paramsMap[isYxfx]=" + encodeURIComponent(searchParam.paramsMap.isYxfx));
}

function view(id){
		window.open(basePath+"/material/demand/find?id="+id);
		
}

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
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(userJgdm);
	 $("#fjzch_search").val("");
	 $("#bzzt_search1").attr("checked","checked");
	 $("#bzzt_search2").attr("checked","checked");
	 $("#bzzt_search3").attr("checked",false);
	 $("#qrbs_search").attr("checked","checked");
	 initfjzch(userJgdm);
	 $("#xqlb_search").html("<option value=''>显示全部All</option>");
	 DicAndEnumUtil.registerDic('84','xqlb_search',userJgdm);
}

/**
 * 查看来源
 * @param lyid
 * @param lylx
 * @param bs145
 */
function viewSource(lyid, lylx, bs145){
	if(lylx == 1){
		if(bs145 == 0){
			window.open(basePath + "/produce/workpackage/view?id=" + lyid);
		}else{
			window.open(basePath + "/produce/workpackage145/view?id=" + lyid);
		}
	}
	
	if(lylx == 2){
		if(bs145 == 0){
			window.open(basePath + "/produce/workorder/woView?gdid=" + lyid);
		}else{
			window.open(basePath + "/produce/workorder145/woView?gdid=" + lyid);
		}
	}
}


