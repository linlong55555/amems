$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn : true
	});
	var dprtcode=$("#dprtcode_search").val();
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	initfjzch(dprtcode);
	componentiohistoryMain.reload();
	
	initImport();
});

//切换组织机构
function dprtChange(){
	initfjzch($("#dprtcode_search").val());
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
	}else{
		planeRegOption += "<option value='' >"+"显示全部 All"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
}
var componentiohistoryMain={
		gatherSearchParam:function(){
			var searchParam={};
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 searchParam.fjzch=$("#fjzch").val();
			 searchParam.drbs=$("#drbs").val();
			 var paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
			 paramsMap.keyword=$("#keyword_search").val();
			 paramsMap.azsjBegin = $.trim($("#azsjBegin").val());
			 paramsMap.azsjEnd = $.trim($("#azsjEnd").val());
			 paramsMap.ccsjBegin = $.trim($("#ccsjBegin").val());
			 paramsMap.ccsjEnd = $.trim($("#ccsjEnd").val());
			 searchParam.paramsMap = paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = pagination;
			 startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/componentiohistory/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   finishWait();
			 			if(data.rows.length > 0){
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[2,3,5]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"24\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("main_list_table")
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				if(row.drbs == 1){
					htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:main:03' " +
	    			"onClick=\"invalid('"+row.paramsMap.zjqdid+"')\" title='删除 Delete'></i>&nbsp;&nbsp;";
				}
				"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>"; 
			    //htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			    htmlContent += "<td colspan='2' style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.paramsMap.bjywms)+" "+StringUtil.escapeStr(row.paramsMap.bjzwms)+"'>"+StringUtil.escapeStr(row.paramsMap.bjywms)+" "+StringUtil.escapeStr(row.paramsMap.bjzwms)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"'>"+StringUtil.escapeStr(row.paramsMap.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.fjzch)+"'><a href=\"javascript:viewFjzch('"+row.fjzch+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.fjzch)+"</a></td>";
			    htmlContent += componentiohistoryMain.formatLastData(row.paramsMap.zjyysj,2,row.ccsj);
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.azsj?row.azsj.substring(0,16):'')+"'>"+StringUtil.escapeStr(row.azsj?row.azsj.substring(0,16):'')+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.azjldh)+"'>";
			    if(row.azjldid){
			    	htmlContent += "<a href=\"javascript:viewWorkOrder('"+row.azjldid+"')\">"+StringUtil.escapeStr(row.azjldh)+"</a>";
			    }else{
			    	htmlContent += StringUtil.escapeStr(row.azjldh);
			    }
			    htmlContent += "</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.azr)+"'>"+StringUtil.escapeStr(row.azr)+"</td>";
			    if(row.zjsl){
			    	htmlContent += "<td style='text-align:left;vertical-align:middle;'' title='"+StringUtil.escapeStr(row.zjsl)+" "+StringUtil.escapeStr(row.jldw)+"'>"+StringUtil.escapeStr(row.zjsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>"; 
			    }else{
			    	htmlContent += "<td style='text-align:left;vertical-align:middle;'></td>"; 
			    }
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.fjzw)+"'>"+StringUtil.escapeStr(row.fjzw)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.zjjlx)+"'>"+StringUtil.escapeStr(row.zjjlx)+"</td>"; 
			    htmlContent +=	"<td title='装上附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			    if(row.paramsMap && (row.paramsMap.azfjCount && row.paramsMap.azfjCount > 0)){
			    	htmlContent += '<i zsid="'+row.azjldid+'" class="attachment-view-zs glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			    }
			    htmlContent +=	"</td>";
			    htmlContent += componentiohistoryMain.formatLastData(row.paramsMap.zjyysj,1,row.azsj); 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.azbz)+"'>"+StringUtil.escapeStr(row.azbz)+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.ccsj?row.ccsj.substring(0,16):'')+"'>"+StringUtil.escapeStr(row.ccsj?row.ccsj.substring(0,16):'')+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.ccjldh)+"'>";
			    if(row.ccjldid){
			    	htmlContent += "<a href=\"javascript:viewWorkOrder('"+row.ccjldid+"')\">"+StringUtil.escapeStr(row.ccjldh)+"</a>";
			    }else{
			    	htmlContent += StringUtil.escapeStr(row.ccjldh);
			    }
			    htmlContent += "</td>"; 
			    
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.ccr)+"'>"+StringUtil.escapeStr(row.ccr)+"</td>"; 
			    htmlContent +=	"<td title='拆下附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			    if(row.paramsMap && (row.paramsMap.ccfjCount && row.paramsMap.ccfjCount > 0)){
			    	htmlContent += '<i ccid="'+StringUtil.escapeStr(row.ccjldid)+'" class="attachment-view-cc glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			    }
			    htmlContent += componentiohistoryMain.formatLastData(row.paramsMap.zjyysj,3,row.ccsj); 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.cjZsjjh)+" "+StringUtil.escapeStr(row.cjZsjxlh)+"'>"+StringUtil.escapeStr(row.cjZsjjh)+" "+StringUtil.escapeStr(row.cjZsjxlh)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.ccbz)+"'>"+StringUtil.escapeStr(row.ccbz)+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		   initWebuiPopoverZs();
		   initWebuiPopoverCc();
		 });
		},
		formatLastData : function(data,i,rq){
			var str = "";
			if(rq == null || rq == ""){
				str += "<td></td>";
				return str;
			}
			if(data == null || data == ""){
				str += "<td></td>";
				return str;
			}
			var list = data.split(",");
			var value = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
				var dw = MonitorUnitUtil.getMonitorUnit(tdArr[0]);
				if(tdArr[0] == '1_10'){
					dw ='D';
				}
				if(dw == 'FH' || dw == 'EH' || dw == 'APUH'){
					value += (tdArr[i]?TimeUtil.convertToHour(tdArr[i], TimeUtil.Separator.COLON):0)+dw+"</br>";
				}else{
					if(dw =='D' && isNaN(tdArr[i])){
						value += (tdArr[i]?tdArr[i]:0)+"</br>";
					}else{
						value += (tdArr[i]?tdArr[i]:0)+dw+"</br>";
					}
				}
			});
			str += "<td title='"+value.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+value+"</td>";
			return str;
		}
};
function initWebuiPopoverZs(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view-zs','body',function(obj){
		return getHistoryAttachmentListZs(obj);
	});
	$("#structrucRepair_table").scroll(function(){
		$('.attachment-view-zs').webuiPopover('hide');
	});
}
function getHistoryAttachmentListZs(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('zsid'), type : '装上附件'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
function initWebuiPopoverCc(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view-cc','body',function(obj){
		return getHistoryAttachmentListCc(obj);
	});
	$("#structrucRepair_table").scroll(function(){
		$('.attachment-view-cc').webuiPopover('hide');
	});
}
function getHistoryAttachmentListCc(obj){//获取历史附件列表
	var jsonData = [
	                {mainid : $(obj).attr('ccid'), type : '拆下附件'}
	                ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
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
		componentiohistoryMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}

//跳转到飞机注册查看页面
function viewFjzch(fjzch,dprtcode){
	window.open(basePath+"/aircraftinfo/view?fjzch="+fjzch+"&dprtcode="+dprtcode);
}
//跳转到工单查看
function viewWorkOrder(gdid){
	window.open( basePath+"/produce/workorder/woView?gdid="+gdid);
}
//删除
function invalid(id){
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/componentiohistory/invalid",
				 type:"post",
				 async: false,
				 data:{
					 'id' : id
				 },
				 dataType:"json",
				 success:function(data){
					 finishWait();
					 AlertUtil.showErrorMessage("删除成功！");
					 componentiohistoryMain.reload();
				 }
			 });
			 
		 
	}});
}
/**
 * 初始化导入
 */
function initImport(){
	// 初始化导入
   importModal.init({
	    importUrl:"/produce/componentiohistory/excelImport",
	    downloadUrl:"/common/templetDownfile/11",
		callback: function(data){
			componentiohistoryMain.goPage(1,"auto","desc");
			$("#ImportModal").modal("hide");
		},
	})
}
/**
 * 显示导入模态框
 */
function showImportModal(){
	 importModal.show();
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
//搜索条件重置
function searchreset(){
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(userJgdm);
	 $("#drbs").val("");
	 $("#azsjBegin,#azsjEnd").val("");
	 $("#ccsjBegin,#ccsjEnd").val("");
	 initfjzch(userJgdm);
}
