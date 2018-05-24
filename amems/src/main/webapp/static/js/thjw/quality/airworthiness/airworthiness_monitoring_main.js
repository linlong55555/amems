	 $(function(){
		 Navigation(menuCode, '', '', 'gc_5_1', '岳彬彬', '2017-08-01', '岳彬彬', '2017-08-01');
		var dprtcode=$("#dprtId").val();
		initMultiselect(dprtcode);
		initfjzch(dprtcode);
		searchRevision();
		refreshPermission(); 
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
			});
	 });
	
	// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 function goPage(pageNumber,sortType,sequence){
	 	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	 }	
	
	//查询条件
	 function gatherSearchParam (){
		 var searchParam={};
		 var paramsMap={};
		 paramsMap.dprtcode=$("#dprtcode").val();
		 paramsMap.zt=$("#zt").val();		 
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
		 paramsMap.keyword=$.trim($('#keyword_search').val());
		 paramsMap.fjzch=$("#fjzch").val();
		 paramsMap.zjh=$.trim($("#zjhMax").val());	
		 var sxrq=$("#sxrq").val();
		 if(null != sxrq && "" != sxrq){
			 var sxrqBegin = sxrq.substring(0,10)+" 00:00:00";
			 var sxrqEnd = sxrq.substring(13,23)+" 23:59:59";
			 paramsMap.sxrqBegin = sxrqBegin;
			 paramsMap.sxrqEnd = sxrqEnd;
		 }
		 searchParam.paramsMap=paramsMap;
		 return searchParam;
	 }
	 
	// 带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		
		  var searchParam = gatherSearchParam();
		  pagination = {
				  page : pageNumber,
				  sort : sortType,
				  order : sequence,
				  rows : 20
		  };
		  searchParam.pagination = pagination;
		  AjaxUtil.ajax({
				url : basePath + "/quality/airworthiness/monitoring/queryList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(searchParam),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						bulletinData = data.rows;
						appendContentHtml(data.rows, data.monitorsettings);
						var page_ = new Pagination({
							renderTo : "pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}

						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(), [ 4, 5,13 ]);
					} else {
						$("#airworthiness_list").empty();
						$("#pagination").empty();
						$("#airworthiness_list").append("<tr class='text-center'><td colspan=\"31\">暂无数据 No data.</td></tr>");
					}
					new Sticky({
						tableId : 'airworthiness_list_table'
					});
				}
			});
	 }
	 /* 表格拼接*/
	 function appendContentHtml(list){
		 
			var htmlContent = '';
			$.each(list,function(index,row){	
				htmlContent += "<tr >";
				 htmlContent += "<td class='text-center fixed-column'>";			  	    
				    if(row.JSWJJKZT==0){
				    	htmlContent += "<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='quality:airworthiness:monitoring:main:01'  bh='"+row.JSWJBH+"' zdjsyy=''  zt='"+row.JSWJJKZT+"'  onClick=\"shutDown('"+row.JSWJID+"', this , true)\" title='关闭 Close'></i>&nbsp;&nbsp;";
				    }			   
				    htmlContent += "</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.JSWJLX))+"'>"+StringUtil.escapeStr(formatUndefine(row.JSWJLX))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.JSWJLY))+"'>"+StringUtil.escapeStr(formatUndefine(row.JSWJLY))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.JSWJBH))+"'><a href=\"javascript:viewSh('"
				+ row.JSWJID + "')\">"+StringUtil.escapeStr(formatUndefine(row.JSWJBH))+"</a></td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.XZAH))+"'>"+StringUtil.escapeStr(formatUndefine(row.XZAH))+"</td>";
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.SXRQ==null?"":row.SXRQ.substring(0,10)))+"'>"+StringUtil.escapeStr(formatUndefine(row.SXRQ==null?"":row.SXRQ.substring(0,10)))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.JSWJZT))+"'>"+StringUtil.escapeStr(formatUndefine(row.JSWJZT))+"</td>"; 
			    if(row.SCFJID !=null&&row.SCFJID !=''){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.SCFJWBWJM)+"."+row.SCFJHZM)+"'><a href=\"javascript:downloadfile('"+row.SCFJID+"')\">"+StringUtil.escapeStr(formatUndefine(row.SCFJWBWJM)+"."+row.SCFJHZM)+"</a></td>"; 
			    }else{
			    	htmlContent += "<td class='text-left'></td>"; 
			    }
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.GLJSWJBH))+"'><a href=\"javascript:viewSh('"
				+ row.GLJSWJID + "')\">"+StringUtil.escapeStr(formatUndefine(row.GLJSWJBH))+"</a></td>"; 
			    
			    if(row.GLSCFJID !=null&&row.GLSCFJID !=''){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.GLSCFJWBWJM)+"."+row.GLSCFJHZM)+"'><a href=\"javascript:downloadfile('"+row.GLSCFJID+"')\">"+StringUtil.escapeStr(formatUndefine(row.GLSCFJWBWJM)+"."+row.GLSCFJHZM)+"</a></td>"; 
			    }else{
			    	htmlContent += "<td class='text-left'></td>"; 
			    }		    
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.YJBFZL)+"'>"+StringUtil.escapeStr(row.YJBFZL)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.ZLXL))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('sendOrderEnum',row.ZLXL))+"</td>"; 
			    if(row.ZLXL==1){//技术通告
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewJstg('"
			    	+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
			    	}else if(row.ZLXL==2){//技术指令
			    		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewJszl('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
				   }else if(row.ZLXL==3){//维修方案
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewWxfa('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
				   }else if(row.ZLXL==4){//下达nrc
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+"'><a href=\"javascript:viewNrc('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+"</a></td>"; 
				   }else if(row.ZLXL==6){//工程指令eo
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewGczl('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
				   }else if(row.ZLXL==7){//mel
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewMel('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
				   }else if(row.ZLXL==8){//工卡
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"'><a href=\"javascript:viewGk('"
						+ row.ZLID + "')\">"+StringUtil.escapeStr(row.ZLBH)+(StringUtil.escapeStr(row.ZLBB)?(" R"+StringUtil.escapeStr(row.ZLBB)):"")+"</a></td>"; 
				   }else{
					   htmlContent += "<td class='text-left' ></td>";  
				   }			
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.GZGS)+"'>"+StringUtil.escapeStr(row.GZGS)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.JX)+"'>"+StringUtil.escapeStr(row.JX)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.FJZCH)+"'><a href=\"javascript:viewFj('"+row.FJZCH+"','"+row.DPRTCODE+"')\" >"+StringUtil.escapeStr(row.FJZCH)+"</a></td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.FJXLH)+"'>"+StringUtil.escapeStr(row.FJXLH)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('compnentTypeEnum',row.SYLB))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('compnentTypeEnum',row.SYLB))+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.BJH))+"'>"+StringUtil.escapeStr(formatUndefine(row.BJH))+"</td>"; 
			    if(row.ZJQDID){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.BJXLH))+"'><a href=\"javascript:viewZjqd('"+row.ZJQDID+"')\">"+StringUtil.escapeStr(formatUndefine(row.BJXLH))+"</a></td>"; 			    
			    }else{
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.BJXLH))+"'>"+StringUtil.escapeStr(formatUndefine(row.BJXLH))+"</td>"; 			    
			    }			    
			    var sc="";
			    var zq="";
			    var sczxz="";
			    var xcjhz="";
			    if(row.SCSJ!=null){
					var str = row.SCSJ.split(",");
					for (var i = 0; i < str.length; i++) {
						var sc_html="";
						var lx =str[i].split("#_#")[0];
						var sc_value=str[i].split("#_#")[1];
						if(MonitorUnitUtil.isTime(lx)){
							sc_html = sc_value?TimeUtil.convertToHour(sc_value, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit:"";
						}else if(MonitorUnitUtil.isCal(lx)){
							sc_html = sc_value?sc_value:"";
						}else if(MonitorUnitUtil.isLoop(lx)){
							sc_html = sc_value?sc_value+MonitorUnitUtil.unitObj[lx].unit:"";
						}
						if(i==str.length-1){
							sczxz +=sc_html;
						}else{
							sczxz +=sc_html+"<br/>";
						}
				    }
				} else {
					
				}
			    var jhsjsj =row.JHSJSJ;
				if(jhsjsj==null ||jhsjsj ==undefined){
					
				}else{
					var str = jhsjsj.split(",");
					for (var i = 0; i < str.length; i++) {
						var sc_html="";
						var zq_html="";
						var xcjhz_html="";
						var lx =str[i].split("#_#")[0];
						var sc_value=str[i].split("#_#")[2];
						var zq_value=str[i].split("#_#")[4];
						var xcjhz_value=str[i].split("#_#")[1];
						var sc_dw =str[i].split("#_#")[3];
						var zq_dw =str[i].split("#_#")[5];
						if(MonitorUnitUtil.isTime(lx)){
							sc_html = sc_value?TimeUtil.convertToHour(sc_value, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit:"";
							zq_html = zq_value?TimeUtil.convertToHour(zq_value, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit:"";
							xcjhz_html = xcjhz_value?TimeUtil.convertToHour(xcjhz_value, TimeUtil.Separator.COLON)+MonitorUnitUtil.unitObj[lx].unit:"";
						}else if(MonitorUnitUtil.isCal(lx)){
							sc_html = sc_value?sc_value+converJkldw(lx,sc_dw):"";
							zq_html = zq_value?zq_value+converJkldw(lx,zq_dw):"";
							xcjhz_html = xcjhz_value;
						}else if(MonitorUnitUtil.isLoop(lx)){
							sc_html = sc_value?sc_value+MonitorUnitUtil.unitObj[lx].unit:"";
							zq_html = zq_value?zq_value+MonitorUnitUtil.unitObj[lx].unit:"";
							xcjhz_html = xcjhz_value?xcjhz_value+MonitorUnitUtil.unitObj[lx].unit:"";
						}
						if(i==str.length-1){
							sc +=sc_html;
							zq +=zq_html;
							xcjhz += xcjhz_html;
						}else{
							sc +=sc_html+"<br/>";
							zq +=zq_html+"<br/>";
							xcjhz += xcjhz_html+"<br/>";
						}
					}		    
				}
				htmlContent += "<td class='text-left' >"+sc+"</td>"; 											
				htmlContent += "<td class='text-left' >"+zq+"</td>";				
			    htmlContent += "<td class='text-left' >"+sczxz+"</td>"; 			   
			    htmlContent += "<td class='text-left' >"+xcjhz+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ZZTJ))+"'>"+StringUtil.escapeStr(formatUndefine(row.ZZTJ))+"</td>"; 	
			    htmlContent += "<td class='text-left' title='"+doZlph(row.IS_ZZPHBH,row.MTC,row.WTC,row.ARM)+"' >"+doZlph(row.IS_ZZPHBH,row.MTC,row.WTC,row.ARM)+"</td>"; 
			    if(row.ZLXL==1){//技术通告
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('bulletinStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('bulletinStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==2){//技术指令
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('technicalInstructionStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==3){//维修方案
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceSchemeStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('maintenanceSchemeStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==4){//下达nrc
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==6){//工程指令eo
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('engineeringOrderStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('engineeringOrderStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==7){//mel
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('melStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('melStatusEnum',row.YWDJZT))+"</td>";
			   }else if(row.ZLXL==8){//工卡
				   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workCardStatusEnum',row.YWDJZT))+"'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workCardStatusEnum',row.YWDJZT))+"</td>";
			   }else{
				   htmlContent += "<td class='text-left' ></td>";  
			   }
			    
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.FSYYY)+"'>"+StringUtil.escapeStr(row.FSYYY)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ZJHYWMS)+" "+StringUtil.escapeStr(row.ZJHZWMS)+"'>"+StringUtil.escapeStr(row.ZJHYWMS)+" "+StringUtil.escapeStr(row.ZJHZWMS)+"</td>"; 
			    if(row.JSWJJKZT==0){
			    	htmlContent += "<td class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('airworthinessMonitorStatusEnum', row.JSWJJKZT))+"' >"+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('airworthinessMonitorStatusEnum', row.JSWJJKZT))+ "</td>";
			    }else{
			    	var zdjsr = row.DISPLAYNAME;
					   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
					   "bh='"+StringUtil.escapeStr(row.JSWJBH)+"' "+
					   "zdjsyy='"+StringUtil.escapeStr(row.GBYY)+"' "+
					   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
					   "zdjsrq='"+StringUtil.escapeStr(row.GBRQ)+"' "+
					   "onclick=\"shutDown('"+row.JSWJID+"', this , false)\">"+formatUndefine(DicAndEnumUtil.getEnumName('airworthinessMonitorStatusEnum', row.JSWJJKZT))+"</a>"+"</td>";
			    }
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>"; 
			    htmlContent += "</tr>" ;	
		   $("#airworthiness_list").empty();
		   $("#airworthiness_list").html(htmlContent);
		   refreshPermission(); 
		 })
  }
	//附件下载
	 function downloadfile(id){
	 	//window.open(basePath + "/common/orderDownfile/" + id);
	 	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
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
function search() {
	
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
	
function searchRevision(){
	 goPage(1,"auto","desc");
}

//搜索条件重置
function searchreset(){
	 var zzjgid=$('#dprtId').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	$("#zt").val("");
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#dprtcode").val(zzjgid);
	 var dprtcode=$("#dprtcode").val();
	initMultiselect(dprtcode);
	initfjzch(dprtcode);
}

$("#dprtcode").change(function(){
	initMultiselect($("#dprtcode").val());
	initfjzch($("#dprtcode").val());
})

$("#zt").change(function(){
	searchRevision();
})

$("#fjzch").change(function(){
	searchRevision();
})

function initMultiselect(dprtcode) {
	$("#wjlxHtml").empty();
	$("#wjlxHtml").html("<select class='form-control' id='wjlx'  multiple='multiple' ></select>");
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	//生成多选下拉框
	$('#wjlx').multiselect({
		buttonClass: 'btn btn-default',
	    buttonWidth: '100%',
	    numberDisplayed:100,
	    buttonContainer: '<div class="btn-group" style="width:100%;" />',
	    nonSelectedText:'显示全部 All',
	    allSelectedText:'显示全部 All',
	    includeSelectAllOption: true,
	    selectAllText: '选择全部 Select All'
	});
	$('#wjlx').multiselect('select', ['CAD']);
}

//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "<option value='' >"+"选择全部"+"</option>";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"/"+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
	}else{
		planeRegOption = "<option value='' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
}

function initFixChapter(){
	var zjh = $.trim($("#zjhMax").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode: $("#dprtcode").val(),
		callback: function(data){//回调函数
			if(data != null){
				var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
				$("#zjhMax").val(data.zjh);
				$("#zjhmsMax").val(wczjhName);
			}else{
				$("#zjhMax").val('');
				$("#zjhmsMax").val('');
			}
		}
	});
}

function shutDown(id,e,isEdit){
	var pgdh=$(e).attr("bh"); //评估单号
	var gbyy=$(e).attr("zdjsyy"); //关闭原因
	var gbrq=$(e).attr("zdjsrq"); //关闭日期
	var gbrid=$(e).attr("zdjsr");//关闭人
	var zt=$(e).attr("zt"); //状态
	AssignEndModal.show({
		chinaHead:'适航性资料编号',//单号中文名称
		englishHead:'Doc. No.',//单号英文名称
		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:pgdh,//指定结束单号
		jsr:gbrid,//指定结束人
		jssj:gbrq,//指定结束时间
		jsyy:gbyy,//指定结束原因
		zt:zt,//状态
		showType:false,
		callback: function(data){//回调函数			
			var message = '关闭成功!';
			var param={};
			param.id = id;
			param.gbyy=data.gbyy;
			var url = basePath+"/quality/airworthiness/monitoring/doClose";
			tip("确定要关闭吗？",param,url,message,'AssignEndModal');
		}
	});
}

//提示信息
function tip(tipmessage,param,url,message,modal){
	AlertUtil.showConfirmMessage(tipmessage, {
		callback : function() {
				sendDataRequest(param, url, message,modal);
		}
	});
}

function sendDataRequest(param, url, message,modal) {
	AjaxUtil.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal : $("#addModal"),
		success : function(data) {		
			AlertUtil.showMessage(message);		
			$("#"+modal).modal("hide");
			searchRevision();
			refreshPermission();
		}
	});
}

function viewSh(id){
	window.open(basePath + "/project2/airworthiness/view?id="+ id );
}

function viewJstg(id){
	window.open(basePath+"/project2/bulletin/view?id="+id);
}

function viewJszl(id){
	window.open(basePath+"/project2/maintenancescheme/view?id="+id);
}
function viewWxfa(id){
	window.open(basePath+"/project2/maintenancescheme/view?id="+id);
}
function viewGczl(id){
	window.open(basePath+"/project2/eo/view?id="+id);
}
function viewMel(id){
	window.open(basePath+"/project2/mel/view?id="+id);
}
function viewGk(id){
	window.open(basePath+"/project2/workcard/view/"+id);
}
function viewNrc(id){
	window.open(basePath+"/produce/workorder/woView?gdid="+id);
}

function viewFj(fjzch,dprtcode){
	window.open(basePath+"/aircraftinfo/view?fjzch="+fjzch+"&dprtcode="+dprtcode);
}
function viewZjqd(id){
	window.open(basePath+"/aircraftinfo/installationlist/view?id="+id);
}

function converJkldw(jklbh,value){
	var dw=''
	var list = MonitorUnitUtil.unitObj[jklbh].subitem;
	$.each(list,function(i,data){
		if(data.value==value){
			dw = data.unit;
		}
	})
	return dw;
}

function doZlph(is_zzphbh,mtc,wtc,arm,bhnr){
	var zlph = "";
	zlph += formatUndefine(DicAndEnumUtil.getEnumName("whetherEnum",is_zzphbh));
	zlph += mtc?("/"+StringUtil.escapeStr(mtc)):"";
	zlph += wtc?("/"+StringUtil.escapeStr(wtc)):"";
	zlph += arm?("/"+StringUtil.escapeStr(arm)):"";
	zlph += bhnr?("/"+StringUtil.escapeStr(bhnr)):"";
	return zlph;
}