var choseAllIds=[];	
$(function(){
		 Navigation(menuCode, '', '', 'gc_5_1', '孙霁', '2018-4-02', '孙霁', '2018-4-02');
		var dprtcode=$("#dprtId").val();
		initMultiselect(dprtcode);
		searchRevision();
		refreshPermission(); 
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
			});
		initAcReg();
	 });

	//初始化飞机注册号
	function initAcReg(){
		var this_ = this;
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
	 	var planeRegOption = '<option value="">显示全部 All</option>';
	 	if(planeDatas != null && planeDatas.length >0){
	 		$.each(planeDatas, function(i, planeData){
					planeRegOption 
						+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"
						+ StringUtil.escapeStr(planeData.FJZCH)
						+ "</option>";
	 		});
	 	}
	 	$("#fjzch").html(planeRegOption);
	}
	
	// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 function goPage(pageNumber,sortType,sequence){
	 	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	 }	
	
	//查询条件
	 function gatherSearchParam (){
		 var searchParam={};
		 var paramsMap={};
		 searchParam.dprtcode=$("#dprtcode").val();	 
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
		 paramsMap.zt=$("#zt").val();
		 paramsMap.fjzch = $("#fjzch").val();
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
		  startWait();
		  AjaxUtil.ajax({
				url : basePath + "/project2/airworthiness/admonitoring/queryAllAd",
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
						signByKeyword($("#keyword_search").val(), [ 5, 6, 17 ]);
					} else {
						$("#airworthiness_list").empty();
						$("#pagination").empty();
						$("#airworthiness_list").append("<tr class='text-center'><td colspan=\"32\">暂无数据 No data.</td></tr>");
					}
					new Sticky({
						tableId : 'airworthiness_list_table'
					});
				}
			});
	 }
	 function show(obj){
		 $("#airworthiness_list .bg_tr").removeClass("bg_tr");
		 $(obj).addClass("bg_tr");
		
	 }
	/* 表格拼接*/
	 function appendContentHtml(list){
		 
			var htmlContent = '';
			$.each(list,function(index,row){
				choseAllIds.push(index);
				var paramsMap = {};
				if(row.paramsMap){
					paramsMap = row.paramsMap;
				}
				row.paramsMap;
				htmlContent += "<tr >";	
			   /* htmlContent += "<td class='text-center'>";
			    htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
			    "permissioncode='project2:airworthiness:main:02' onClick=\"closeMonitoring('"+row.id+"')\" title='关闭技术文件监控  Close'></i>&nbsp;&nbsp;";
			    if(paramsMap.fjbj){
			    	htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
			    	"permissioncode='project2:airworthiness:main:02' onClick=\"closeApplyMonitoring('"+row.id+"','"+paramsMap.fjbj+"')\" title='关闭适用性监控 Close'></i>&nbsp;&nbsp;";
			    }
			    htmlContent += "</td>";*/
			    
			    htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
			    
			    if(paramsMap.jswjjkzt == 9){
			    	htmlContent += "<td style='text-align:center;vertical-align:middle;' title='关闭'>关闭 "; 
			    	htmlContent += "<i class='fa fa-check-circle color-blue cursor-pointer checkPermission' " +
			    	"permissioncode='project2:airworthiness:admonitoring:main:09' onClick=\"closeMonitoring('"+row.id+"',0)\" title='开启 Start'></i>&nbsp;&nbsp;</td>";
			    }else{
			    	htmlContent += "<td style='text-align:center;vertical-align:middle;' title='开启'>开启 "; 
			    	htmlContent += "<i class='fa fa-times-circle color-blue cursor-pointer checkPermission' " +
			    	"permissioncode='project2:airworthiness:admonitoring:main:09' onClick=\"closeMonitoring('"+row.id+"',9)\" title='关闭 Close'></i>&nbsp;&nbsp;</td>";
			    }
			    if(paramsMap.syxjkzt == 9){
			    	if(paramsMap.fjbj !=" "){
			    		htmlContent += "<td style='text-align:center;vertical-align:middle;' title='关闭'>关闭 "; 
			    		htmlContent += "<i class='fa fa-check-circle color-blue cursor-pointer checkPermission' " +
				    	"permissioncode='project2:airworthiness:admonitoring:main:09' onClick=\"closeApplyMonitoring('"+(paramsMap.syxid?paramsMap.syxid:'')+"','"+row.id+"','"+(paramsMap.pgdid?paramsMap.pgdid:'')+"','"+(paramsMap.fjbj?paramsMap.fjbj:'')+"',0)\" title='开启 Start'></i>&nbsp;&nbsp;</td>";
			    	}else{
				    	htmlContent += "<td style='text-align:center;vertical-align:middle;'>-</td>"; 
				    }
			    }else{
			    	if(paramsMap.fjbj !=" "){
			    		htmlContent += "<td style='text-align:center;vertical-align:middle;' title='开启'>开启 "; 
			    		htmlContent += "<i class='fa fa-times-circle color-blue cursor-pointer checkPermission' " +
				    	"permissioncode='project2:airworthiness:admonitoring:main:09' onClick=\"closeApplyMonitoring('"+(paramsMap.syxid?paramsMap.syxid:'')+"','"+row.id+"','"+(paramsMap.pgdid?paramsMap.pgdid:'')+"','"+(paramsMap.fjbj?paramsMap.fjbj:'')+"',9)\" title='关闭 Close'></i>&nbsp;&nbsp;</td>"; 
			    	}else{
			    		htmlContent += "<td style='text-align:center;vertical-align:middle;'>-</td>"; 
			    	}
			    }
			    
			    
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.jswjlx)+"'>"+formatUndefine(row.jswjlx)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.xzah)+"'>"+formatUndefine(row.xzah)+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(row.jswjbh)+"'>"+formatUndefine(row.jswjbh)+"</td>"; 
			   
			    htmlContent += "<td style='text-align:left;vertical-align:middle;border-right:0px !important;padding-right:0px;' title='"+formatUndefine(row.jswjzt)+"'>"+formatUndefine(row.jswjzt)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;border-left:0px !important;'>";
			    if(paramsMap.scfjid){
			    	htmlContent += "<i class='fa color-blue fa-download cursor-pointer' style='font-size:11px;' onClick=\"downloadfile('"+paramsMap.scfjid+"')\"  ></i>";
			    }
			    htmlContent += "</td>"; 
			    
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(row.sxrq?row.sxrq.substring(0,10):'')+"'>"+formatUndefine(row.sxrq?row.sxrq.substring(0,10):'')+"</td>"; 
			    var gljswj = '';
			    if(row.paramsMap.gl_jswjid){
			    	gljswj += formatUndefine(paramsMap.gl_jswjlx)+formatUndefine(paramsMap.gl_jswjbh?"/"+paramsMap.gl_jswjbh:'')+formatUndefine(paramsMap.gl_xzah?"/"+paramsMap.gl_xzah:'')+formatUndefine("/"+paramsMap.gl_jswjzt?paramsMap.gl_jswjzt:'')+formatUndefine(paramsMap.gl_sxrq?"/"+paramsMap.gl_sxrq.substring(0,10):'');
			    }
			    htmlContent += "<td style='text-align:left;vertical-align:middle;border-right:0px !important;padding-right:0px;' title='"+formatUndefine(gljswj)+"'>"+formatUndefine(gljswj)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;border-left:0px !important;'>";
			    if(paramsMap.glscfjid){
			    	htmlContent += "<i class='fa color-blue fa-download cursor-pointer' style='font-size:11px;' onClick=\"downloadfile('"+paramsMap.glscfjid+"')\"  ></i>";
			    }
			    htmlContent += "</td>"; 
			    
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.bdwsyx)+"'>"+formatUndefine(paramsMap.bdwsyx)+"</td>"; 
			   
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.pgdh)+"'><a href=\"javascript:viewPgd('"+paramsMap.pgdid+"')\">"+formatUndefine(paramsMap.pgdh)+"</a></td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.jx)+"'>"+formatUndefine(paramsMap.jx)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.fjbj)+"'>"+formatUndefine(paramsMap.fjbj)+"</td>"; 
			    var zlhtml = '';
			    if(paramsMap.zllx){
			    	var list = paramsMap.zllx.split(",");
			    	$.each(list,function(index,zl){
			    		if(index == 0){
			    			zlhtml = DicAndEnumUtil.getEnumName('sendOrderEnum',zl) ;
			    		}else{
			    			zlhtml += "," + DicAndEnumUtil.getEnumName('sendOrderEnum',zl) ;
			    		}
			    	});
			    }
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+zlhtml+"'>"+zlhtml+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.zlbh)+"'><a href=\"javascript:viewEo('"+paramsMap.zlid+"')\">"+formatUndefine(paramsMap.zlbh)+"</a></td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.xfrq?paramsMap.xfrq.substring(0,10):'')+"'>"+formatUndefine(paramsMap.xfrq?paramsMap.xfrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(row.dqrq?row.dqrq.substring(0,10):'')+"'>"+formatUndefine(row.dqrq?row.dqrq.substring(0,10):'')+"</td>"; 
			    htmlContent += formatLastData(paramsMap.sczx);
			    htmlContent += formatLastData(paramsMap.xczx);
			    htmlContent += formatLastZxjl(paramsMap.zxjl,index);
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(paramsMap.syxjkbz)+"'>"+formatUndefine(paramsMap.syxjkbz)+"</td>"; 
			    htmlContent += "</tr>" ;	
		   $("#airworthiness_list").empty();
		   $("#airworthiness_list").html(htmlContent);
		   refreshPermission(); 
		 });
  }
	 function zlbhHideAndShow(){
		 if(row.zlbh!=null && row.zlbh!=""){
			   var strs= new Array(); // 定义一数组
			   strs=row.zlbh.split(",&sun1"); // 字符分割
			   for (i=0;i<strs.length ;i++ ){
				   var bh=strs[i].split("(")[0];
				   
				   if(i==1){
					   htmlContent = htmlContent +"　<i class='icon-caret-down' id='"+row.id+"icon' onclick=vieworhideWorkContent('"+row.id+"')></i><div id='"+row.id+"' style='display:none'>"; 
				   }
				   
				htmlContent = htmlContent +"<a href='javascript:void(0);' bh='"+StringUtil.escapeStr(bh)+"' dprtcode='"+row.dprtcode+"'onclick=\"selectByzlbh(this)\">"+StringUtil.escapeStr(strs[i])+"</a>";
				if(i!=0){
					htmlContent = htmlContent +"<br>";
					
				}
				if(i!=0 && i==strs.length-1)  {
					htmlContent = htmlContent +"</div>"; 
				}
					
				   
			   } 
		   }
	 }
	 
	 
	 
	//附件下载
	 function downloadfile(id){
	 	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	 }
	 function viewEo(eoid){
		 window.open(basePath+"/project2/eo/view?id="+eoid);
	 }
	 function viewPgd(pgdid){
		 window.open(basePath+"/project2/assessment/view?id="+pgdid);
	 }
	 function gdView(gdid){
			window.open(basePath+"/produce/workorder/woView?gdid="+gdid);
	 }
	 function formatLastZxjl(data,num){
		 var str = "";
		 if(data == null || data == ""){
				str += "<td></td>";
				return str;
			}
		var list = data.split(",");
		var value = '';
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			if(index == 0){
				value +="<a href=javascript:gdView('"+tdArr[0]+"')>"+tdArr[1]+"</a>";
				if(list.length > 1){
					value +="<i class='icon-caret-down' id='"+num+"icon' onclick=vieworhideWorkContent('"+num+"')></i><div id='"+num+"' style='display:none'>";
				}
			}else{
				value +="<a href=javascript:gdView('"+tdArr[0]+"')>"+tdArr[1]+"</a></br>";
			}
			
		});
		str += "<td style='text-align:center;vertical-align:middle;'>"+value+"</td>";
		return str;
	 }
	 function vieworhideWorkContent(id){
		 new Sticky({tableId:'airworthiness_list_table'});
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
		 
	 }
	 
	 function formatLastData(data){
			var str = "";
			if(data == null || data == ""){
				str += "<td></td>";
				return str;
			}
			var list = data.split(",");
			var value = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
					value += formatJkz(tdArr[0],tdArr[1]);
				
			});
			str += "<td title='"+value.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+value+"</td>";
			return str;
		}
		/**
		 * 格式化监控值
		 */
		function formatJkz(jklbh, value){
			if(value != null && value != ''){
				value = convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
			}else{
				value = " "+"</br>";
			}
			return value;
		}
		/**
		 * 分钟转小时
		 */
		function convertToHour(jklbh, value){
			if(MonitorUnitUtil.isTime(jklbh)){
				value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
			}
			return value;
		}
	 
	 
	 
	//关闭技术文件监控
	 function closeMonitoring(sid,zt){
		 var messageStater ='';
		 var messageEnd ='';
		 if(zt == 9){
			 messageStater = "确定要关闭技术文件监控吗？";
			 messageEnd = "关闭技术文件监控成功！";
		 }else{
			 messageStater = "确定要开启技术文件监控吗？";
			 messageEnd = "开启技术文件监控成功！";
		 }
	 	AlertUtil.showConfirmMessage(messageStater, {callback: function(){
	 		startWait();
	 			 AjaxUtil.ajax({
	 				 url:basePath+"/project2/airworthiness/closeMonitoring",
	 				 type:"post",
	 				 async: false,
	 				 data:{
	 					 'id' : sid,
	 					 'zt' : zt
	 				 },
	 				 dataType:"json",
	 				 success:function(data){
	 					 finishWait();
	 					 AlertUtil.showErrorMessage(messageEnd);
	 					goPage(1,"auto","desc");
	 				 }
	 			 });
	 			 
	 		 
	 	}});
	 }
	 //关闭适用性监控
	 function closeApplyMonitoring(id,gbjswjid,gbpgdid,fjbj,zt){
		 //隐藏出现异常的感叹号
		 AlertUtil.hideModalFooterMessage();
		 $("#adCloseModal").modal("show");
		 $("#gbid").val(id);
		 $("#gbjswjid").val(gbjswjid);
		 $("#gbpgdid").val(gbpgdid);
		 var list = fjbj.split(" ");
		 $("#gbbh").val(list[0]);
		 $("#gbxlh ").val(list[1]);
		 $("#gbzt").val(zt);
		 if(zt == 0){
			 $("#gbcName").html("开启");
			 $("#gbeName").html("Open");
			 //$("input:radio[name='gbzt'][value='9']").prop("checked", "checked");
		 }else{
			 $("#gbcName").html("关闭");
			 $("#gbeName").html("Close");
			 //$("input:radio[name='gbzt'][value='0']").prop("checked", "checked");
		 }
		 $("#gbbz").val('');
		
		 
	 }
	//关闭适用性监控(确认)
	 function adClose(){
		 var obj ={};
		 var paramsMap ={};
		 paramsMap.id= $("#gbjswjid").val();
		 paramsMap.pgdid= $("#gbpgdid").val();
		 obj.jkzt = $("#gbzt").val();
		 obj.id =$("#gbid").val();
		 obj.bh = $("#gbbh").val();
		 obj.xlh = $("#gbxlh").val();
		 obj.jkbz = $("#gbbz").val();
		 obj.paramsMap = paramsMap;
		  startWait();//开始Loading
		  AjaxUtil.ajax({
			  url:basePath+"/project2/airworthiness/closeApplyMonitoring",
				type: "post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					finishWait();//结束Loading
					AlertUtil.showErrorMessage("操作成功！");
					$("#adCloseModal").modal("hide");
					goPage(1,"auto","desc");
				}
		   });
	 }
	 
	 
	//附件下载
	 function downloadfile(id){
	 	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
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
	TableUtil.resetTableSorting("airworthiness_list_table");
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
}


$("#zt").change(function(){
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
			var url = basePath+"/project2/airworthiness/monitoring/doClose";
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

function exportExcel(){
	var searchParams=gatherSearchParam();
	window.open(basePath+"/project2/airworthiness/ADMonitor.xls?params="+JSON.stringify(searchParams));
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
	 new Sticky({tableId:'airworthiness_list_table'});
}