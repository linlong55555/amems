
var monitorList = [];
var workContentCount = 0;
var monitorCount = 0;
var alertFormId = 'alertDiffForm';
var pagination = {};

/** 存放数据 */
var xdnrMap = new Object();
xdnrMap['add'] = '工作内容新增';
xdnrMap['edit'] = '工作内容修改';
xdnrMap['del'] = '工作内容删除';

$(document).ready(function(){
	Navigation(menuCode,"维修方案差异信息","Maintenance Differ");
	initMonitor();
	getFixedCheckItemList();//开始的加载默认的内容 
});

function initMonitor(){

	AjaxUtil.ajax({
		url:basePath+"/component/monitorclass/queryMonitorList",
		type:"post",
		async: false,
		data:{},
		dataType:"json",
		success:function(data){
			monitorList = data;
		}
	});
}

function viewFixDetail(id){
	document.getElementById("fixDetail").src =basePath+"/iframe/fixIframe?id=" + id+"&t=" + new Date().getTime();
	$("#fixDetail").load(function () {
		var h = document.body.clientHeight;
		var w = document.body.clientWidth;
		document.getElementById("fixDetail").height = h + "px";
		document.getElementById("fixDetail").width = w + "px";
	});
}

function getFixedCheckItemList(){
	var wxfabh = $.trim($("#wxfabh").val());
	var wxfabb = $.trim($("#wxfabb").val());
	var wxfafbb = $.trim($("#wxfafbb").val());
	var dprtcode = $("#dprtcode").val();
	var obj = {};
	obj.wxfabh = wxfabh;
	obj.bb = wxfabb;
	obj.fbb = wxfafbb;
	obj.dprtcode = dprtcode;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckitem/queryByMap",
		type: "post",
		dataType:"json",
		data:obj,
		success:function(data){
			finishWait();
			$("#fixedCheckItem").empty();
			if(data.length >0){
				$.each(data,function(index,row){
					var content = ['<a href="#" id="'+row.id+'" fid="'+row.fBbid+'" isModify="'+row.isModify+'" class="list-group-item" onclick="loadFixDetail(this)" ',
					              ' style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 20px 5px 5px;">',
				                  '<font>',
				                  StringUtil.escapeStr(row.djbh) + " <span title='"+StringUtil.escapeStr(row.zwms)+"'>" + StringUtil.escapeStr(row.zwms)+"</span>",
				                  '</font>',
				                  (row.isModify ? '<i class="icon-circle pull-right" style="right:5px;position:absolute"></i>':''),
				                  '</a>'
				                  ].join('');
					$("#fixedCheckItem").append(content);
					
				});
				$("#fixedCheckItem a:first-child").click();
			}
		}
	}); 
}	

//加载定检项目
function loadFixDetail(obj){
	clearAll();
	// 高亮选中行
	$("#fixedCheckItem .list-group-item.active").removeClass("active");
	$(obj).addClass("active");
	
	var id = $(obj).attr('id');
	var fid = $(obj).attr('fid');
	var isModify = $(obj).attr('isModify');
	if(null == isModify || "" == isModify || "null" == isModify){
		$(".bzgsdiv").hide();
		AlertUtil.showErrorMessage("对不起,该定检项目没有差异");
		return false;
	}
	$(".bzgsdiv").show();
	buildFixForm(id,"1");
	setMonitorItem(id,"1");
	initFixedWorkContent(id,"1");
	buildFixForm(fid,"2");
	setMonitorItem(fid,"2");
	initFixedWorkContent(fid,"2");
}

function buildFixForm(id,bbType){
	AjaxUtil.ajax({
 		url:basePath+"/project/fixedcheckitem/selectById",
 		type:"post",
 		data:{'id' : id},
 		dataType:"json",
 		success:function(data){
 			if(null != data){
 				setFixForm(data,bbType);
 			}else{
 				$("#fixCode"+bbType).html("-");
 				$("#fixNameBb"+bbType).attr("title","");
 				$("#fixNameBb"+bbType).html("-");
 			}	
 		}
 	});
}

function setFixForm(obj,bbType){
	$("#fixId"+bbType).val(obj.id);
	$("#fixCode"+bbType).html(obj.djbh);
	$("#fixNameBb"+bbType).attr("title",(obj.zwms+" R"+obj.bb));
	$("#fixNameBb"+bbType).text((obj.zwms+" R"+obj.bb));
	$("#fixZyxs"+bbType).html(obj.zyxs);
	$("#fixYxx"+bbType).html(obj.yxx);
	$("#jhgsRs"+bbType).html(obj.jhgsRs);
	$("#jhgsXss"+bbType).html(obj.jhgsXss);
	$("#fixPxh"+bbType).html(obj.pxh);
	$("#fixBz"+bbType).attr("title",StringUtil.title(obj.bz,115));
	$("#fixBz"+bbType).text(StringUtil.subString(obj.bz,115));
	$("#fixZdr"+bbType).text(formatUndefine(obj.zdrusername)+" "+formatUndefine(obj.zdrrealname));
	$("#fixZdrq"+bbType).html(formatUndefine(obj.zdsj));
	sumTotal(bbType);
}

function setMonitorItem(id,bbType){
	AjaxUtil.ajax({
		url:basePath+"/project/monitoritem/queryListByDjxmid",
		type:"post",
		async: false,
		data:{djxmid:id},
		dataType:"json",
		success:function(data){
			loadMonitorItem(data,bbType);
		}
	});
}
function loadMonitorItem(fixMontList,bbType){
	
	$("#monitoring"+bbType).empty();
	
	if(JSON.stringify(fixMontList) == '[]'){

	}
	var str = '';
	$.each(fixMontList,function(i,f){
		str += '<div style="background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius: 4px;margin-bottom: 10px;min-height: 40px;padding: 10px 19px 0;"';
		str += '	<div class="row padding-top-10">';
		str += '		<div class="col-sm-6 padding-left-0 padding-right-0">';
		str += '			<font><strong>';
		str += 					i+1;
		str += '			</strong></font>';
		str += '			<font><strong>';
		str += 					'监控条件：'+getMonitorName(f.jklbh);
		str += '			</strong></font>';
		str += '		</div>';
		str += '		<div class="col-sm-6 padding-left-0 padding-right-0">';
		str += '			<font class="col-xs-3 text-right padding-left-0 padding-right-0">';
		str += '				<div class="font-size-12">周期值</div>';
		str += '			</font>';
		str += '			<div class="col-xs-7 padding-left-8 padding-right-0">';
		str += f.zqz+" "+DicAndEnumUtil.getEnumName('unitEnum',f.dw);
		str += '			</div>';
		str += '		</div>';
		str += '	</div>';
		str += '</div>';
	});
	$("#monitoring"+bbType).append(str);
	
	if(bbType == 1){
		monitorCount = fixMontList.length;
	}
	if(bbType == 2){
		var monitorCount2 = fixMontList.length;
		var num = 0;
		if(monitorCount < monitorCount2){
			num = monitorCount2 - monitorCount;
			addMonitorItem(num,1,monitorCount);
		}else{
			num = monitorCount - monitorCount2;
			addMonitorItem(num,2,monitorCount2);
		}
	}
}

function addMonitorItem(num,bbType,index){
	for(var i = 0 ; i < num ; i++){
		var str = '';
		str += '<div style="background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius: 4px;margin-bottom: 10px;min-height: 40px;padding: 10px 19px 0;"';
		str += '	<div class="row padding-top-10">';
		str += '		<div class="col-sm-6 padding-left-0 padding-right-0">';
		str += '			<font><strong>';
		str += 					index+i+1;
		str += '			</strong></font>';
		str += '			<font><strong>';
		str += 					'';
		str += '			</strong></font>';
		str += '		</div>';
		str += '		<div class="col-sm-6 padding-left-0 padding-right-0">';
		str += '			<font class="col-xs-3 text-right padding-left-0 padding-right-0">';
		str += '				<div class="font-size-12">&nbsp;</div>';
		str += '			</font>';
		str += '			<div class="col-xs-7 padding-left-8 padding-right-0">';
		str += '			</div>';
		str += '		</div>';
		str += '	</div>';
		str += '</div>';
		$("#monitoring"+bbType).append(str);
	}
}

function getMonitorName(jklbh){
	var monitorName = '';
	$.each(monitorList,function(i,m){
		$.each(m.itemList,function(i,item){
			if(jklbh == item.jklbh){
				monitorName = item.ms;
			}
		});
	});
	return monitorName;
}

function initFixedWorkContent(id,bbType){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixedcheckworkcontent/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:id},
		dataType:"json",
		success:function(data){
			initTableCol1(data,bbType);
		}
	});
}

//初始化表格
function initTableCol1(data,bbType){
	
	//先移除暂无数据一行
	$("#rotatable"+bbType).empty();
	
	if(JSON.stringify(data) == '[]'){
		$("#rotatable"+bbType).append("<tr><td  colspan='18' class='text-center'>暂无数据 No data.</td></tr>");
	}
	
	$.each(data,function(i,workObj){
		var display = "";
		var option = "";
		var zjhStr = "";
		//有效性，下拉框数据集
		var ztOption = "";
		if(workObj.zt == 1){
			ztOption +=  "有效";
			if(workObj.isUpd != null){
				option = '<i class="icon-circle pull-center"></i>';
			}
		}else{
			ztOption +=  "无效";
			//display = "display:none";
			option = "删除";
			if(bbType == 1 && workObj.normal != null){
				option = "";
			}
		}
		
		var fixChapter = workObj.fixChapter;
		if(null != fixChapter && '' != fixChapter){
			zjhStr = formatUndefine(fixChapter.zjh) + " " + formatUndefine(fixChapter.zwms);
		}
		
		var gzlxStr = '';
		if(workObj.gzlx != null && workObj.gzlx != ""){
			var gzlxArr = workObj.gzlx.split(",");
			for(var i = 0 ; i < gzlxArr.length ; i++){
				if('multiselect-all' != gzlxArr[i]){
					gzlxStr += gzlxArr[i]+",";
				}
			}
		}
		if(gzlxStr != ''){
			gzlxStr = gzlxStr.substring(0,gzlxStr.length-1);
		}
		
		workObj.display = display;
		workObj.option = option;
		workObj.ztOption = ztOption;
		workObj.zjhStr = zjhStr;
		workObj.gzlxStr = gzlxStr;
		addRow(workObj,bbType);
	});
	if(bbType == 1){
		workContentCount = data.length;
	}
	if(bbType == 2){
		var workNum = data.length;
		var num = workContentCount - workNum;
		if(workNum == 0 && num > 0){
			$("#rotatable"+bbType).empty();
		}
		for(var i = 0 ; i < num ; i++){
			var tr = "<tr style='height:30px;'>";
			tr += "<td></td>";
			tr += "<td style='text-align:center;vertical-align:middle;'>-</td>";
			tr += "<td colspan='16'></td>";
			tr += "</tr>";
			$("#rotatable"+bbType).append(tr);
		}
	}
	
	/*var div1 = document.getElementById("workTable1");
	var div2 = document.getElementById("workTable2");
    var height1=div1.clientHeight ;
    var height2=div2.clientHeight ;
	if(height2 < height1){
		div2.style.height=height1+'px';//设置高度
	}*/
}

//向table新增一行
function addRow(obj,bbType){
	
	var tr = "";
	
	tr += "<tr style='height:30px;'>";
	
	tr += "<td style='text-align:center;vertical-align:middle;'>";
	tr += obj.option;
	tr += "</td>";
	
	tr += "<td title="+StringUtil.escapeStr(obj.nbxh)+" style='text-align:center;vertical-align:middle;'>";
	tr += StringUtil.escapeStr(obj.nbxh);
	tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+obj.id+"'/>";
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.zjhStr);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.xmly)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.xmly);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.gzlxStr)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.gzlxStr);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.wz)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.wz);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.scmsZw)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.scmsZw);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.scmsYw)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.scmsYw);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.jclx)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.jclx);
	tr += "</td>";
	
	var fjzchStr = '';
	if('00000' == StringUtil.escapeStr(obj.fjzch)){
		fjzchStr = "通用Currency";
    }else{
    	var fixedCheckWorkPlaneList = obj.fixedCheckWorkPlaneList;
		$.each(fixedCheckWorkPlaneList,function(i,s){
			fjzchStr += s.fjzch+",";
		});
		if(fjzchStr != ''){
			fjzchStr = StringUtil.escapeStr(fjzchStr);
			fjzchStr = fjzchStr.substring(0,fjzchStr.length - 1);
		}
    }
	tr += "<td title='"+fjzchStr+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += fjzchStr;
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.gzzw)+"' style='text-align:right;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.gzzw);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.cksc)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.cksc);
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.ckgk)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.ckgk);
	tr += "</td>";
	
	tr += "<td style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += formatWorkCard(obj.djgkList);
	tr += "</td>";
	
	tr += "<td style='text-align:center;vertical-align:middle;"+obj.display+"'>";
	tr += obj.isBj == 1?"是":"否";
	tr += "</td>";
	
	tr += "<td style='text-align:center;vertical-align:middle;"+obj.display+"'>";
	tr += obj.isMi == 1?"是":"否";
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.bz)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
	tr += StringUtil.escapeStr(obj.bz);
	tr += "</td>";
	
	tr += "<td style='text-align:center;vertical-align:middle;"+obj.display+"'>";
	tr += obj.ztOption;
	tr += "</td>";
	
		
	
	$("#rotatable"+bbType).append(tr);
}

function formartStr(str){
	
	if(str == null || str == ''){
		return '';
	}
	if(str.length > 15){
		str = str.substring(0,15)+"...";
    }
	return str;
}

function formatWorkCard(djgkList){
	var card = '';
	if(null != djgkList && djgkList.length > 0){
		$.each(djgkList,function(i,s){
			if(i == 1){
				card += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=vieworhideWorkCard(this)></i><div style='display:none'>";
			}
			card += "<a href='#' onclick=viewWorkCard('"+s.id+"') >"+StringUtil.escapeStr(s.gdbh)+"</a>"+" "+DicAndEnumUtil.getEnumName('jobCardStatusEnum',s.zt);
			if(i != 0 && i != djgkList.length - 1){
				card += "<br/>";
			}
			if(i == djgkList.length - 1){
				card += "</div>";
			}
		});
	}
	return card;
}

function viewWorkCard(id){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + $("#dprtcode").val()+"&t=" + new Date().getTime());
}

function sumTotal(bbType){
	var totle = 0;
	var jhgsRs = $("#jhgsRs"+bbType).html();
	var jhgsXss = $("#jhgsXss"+bbType).html();
	totle = jhgsRs*jhgsXss;
	if(totle == ''){
		totle = 0;
	}
	totle = totle.toFixed(2);
	$("#bzgs"+bbType).html(totle);
}
//定检工卡显示或隐藏
function vieworhideWorkCard(obj) {
	var flag = $(obj).next().is(":hidden");
	if (flag) {
		$(obj).next().fadeIn(500);
		$(obj).removeClass("icon-caret-down");
		$(obj).addClass("icon-caret-up");
	} else {
		$(obj).next().hide();
		$(obj).removeClass("icon-caret-up");
		$(obj).addClass("icon-caret-down");
	}

}

//工作内容显示与隐藏 
function vieworhideWorkContent() {
	if ($("#workContent1").css("display") == "none") {
		$("#workContent1").css("display", "block");
		$("#iconWorkContent1").removeClass("icon-caret-down");
		$("#iconWorkContent1").addClass("icon-caret-up");
		$("#workContent2").css("display", "block");
		$("#iconWorkContent2").removeClass("icon-caret-down");
		$("#iconWorkContent2").addClass("icon-caret-up");
	} else {
		$("#workContent1").css("display", "none");
		$("#iconWorkContent1").removeClass("icon-caret-up");
		$("#iconWorkContent1").addClass("icon-caret-down");
		$("#workContent2").css("display", "none");
		$("#iconWorkContent2").removeClass("icon-caret-up");
		$("#iconWorkContent2").addClass("icon-caret-down");
	}
}

//监控项显示与隐藏 
function vieworhideMonitor() {
	if ($("#monitoring1").css("display") == "none") {
		$("#monitoring1").css("display", "block");
		$("#iconMonitor1").removeClass("icon-caret-down");
		$("#iconMonitor1").addClass("icon-caret-up");
		$("#monitoring2").css("display", "block");
		$("#iconMonitor2").removeClass("icon-caret-down");
		$("#iconMonitor2").addClass("icon-caret-up");
	} else {
		$("#monitoring1").css("display", "none");
		$("#iconMonitor1").removeClass("icon-caret-up");
		$("#iconMonitor1").addClass("icon-caret-down");
		$("#monitoring2").css("display", "none");
		$("#iconMonitor2").removeClass("icon-caret-up");
		$("#iconMonitor2").addClass("icon-caret-down");
	}
}

//定检基本信息显示与隐藏 
function vieworhideForm() {
	if ($("#fixForm1").css("display") == "none") {
		$("#fixForm1").css("display", "block");
		$("#iconForm1").removeClass("icon-caret-down");
		$("#iconForm1").addClass("icon-caret-up");
		$("#fixForm2").css("display", "block");
		$("#iconForm2").removeClass("icon-caret-down");
		$("#iconForm2").addClass("icon-caret-up");
	} else {
		$("#fixForm1").css("display", "none");
		$("#iconForm1").removeClass("icon-caret-up");
		$("#iconForm1").addClass("icon-caret-down");
		$("#fixForm2").css("display", "none");
		$("#iconForm2").removeClass("icon-caret-up");
		$("#iconForm2").addClass("icon-caret-down");
	}
}

function clearAll(){
	clearMonitor('1');
	clearFixedWorkContent('1');
	clearFixForm('1');
	clearMonitor('2');
	clearFixedWorkContent('2');
	clearFixForm('2');
}

function clearMonitor(bbType){
	$("#monitoring"+bbType).empty();
}

function clearFixedWorkContent(bbType){
	$("#rotatable"+bbType).empty();
	$("#rotatable"+bbType).append("<tr><td  colspan='18'>暂无数据 No data.</td></tr>");
}

function clearFixForm(bbType){
	$("#fixCode"+bbType).html('');
	$("#fixNameBb"+bbType).html('');
	$("#fixZyxs"+bbType).html('');
	$("#fixYxx"+bbType).html('');
	$("#jhgsRs"+bbType).html('');
	$("#jhgsXss"+bbType).html('');
	$("#fixPxh"+bbType).html('');
	$("#fixBz"+bbType).html('');
	$("#fixZdr"+bbType).html('');
	$("#fixZdrq"+bbType).html('');
	$("#bzgs"+bbType).html(0);
}

//打开差异表弹出框
function viewDifference(){
	loadFormInfo();//加载表单信息
	loadListInfo();//加载差异表数据
	$("#"+alertFormId).modal("show");
}

//加载差异表数据
function loadListInfo(){
	AjaxGetDatasWithSearch(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	searchParam.pagination = pagination;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckworkcontent/queryDiffPageList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(searchParam),
		success:function(data){
			finishWait();
			if(data.total >0){
			   	appendContentHtml(data.rows);
			   	new Pagination({
						renderTo : "diff_pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
		   } else {
			  $("#diffTable").empty();
			  $("#diff_pagination").empty();
			  $("#diffTable").append("<tr><td colspan=\"5\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   //new Sticky({tableId:'record_list_table'});
     }
   }); 
	
}

//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	var paramsMap = {};
	var wxfabh = $.trim($("#wxfabh").val());
	var wxfabb = $.trim($("#wxfabb").val());
	var wxfafbb = $.trim($("#wxfafbb").val());
	var dprtcode = $("#dprtcode").val();
	paramsMap.wxfabh = wxfabh;
	paramsMap.wxfabb = wxfabb;
	paramsMap.wxfafbb = wxfafbb;
	paramsMap.dprtcode = dprtcode;
	searchParam.paramsMap = paramsMap;
	return searchParam;
}

function appendContentHtml(list){
	
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent += "<tr bgcolor=\"#fefefe\">";
		}
		var xdnrx = '第'+(StringUtil.escapeStr(row.XDNRX) == ''?1:StringUtil.escapeStr(row.XDNRX))+'项';
		
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
		htmlContent += "<td title='"+StringUtil.escapeStr(row.HJXMBH)+"'  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.HJXMBH)+"</td>"; 
		htmlContent += "<td title='"+StringUtil.escapeStr(row.DJXMMC)+"'  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.DJXMMC)+"</td>"; 
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+xdnrx+"</td>"; 
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+formatUndefine(xdnrMap[StringUtil.escapeStr(row.XDNR)])+"</td>"; 
		htmlContent += "</tr>";  
	    
	});
	$("#diffTable").empty();
	$("#diffTable").html(htmlContent);
}


//加载表单信息
function loadFormInfo(){
	$("#vwxfabh").html($("#wxfabh").val());
	$("#vwxfazwms").html($("#wxfazwms").val());
	var bb = 'R'+$("#wxfafbb").val()+"&nbsp;&nbsp;<i class='icon-long-arrow-right color-black'></i>&nbsp;&nbsp;"+'R'+$("#wxfabb").val();
	$("#vwxfabb").html(bb);
}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/maintenance/main?pageParam="+pageParam;
}
//导出excel
function outMaintenance(){
	var wxfabh=$("#wxfabh").val();
	var wxfazwms=$("#wxfazwms").val();
	var bb =$("#wxfafbb").val();
	var wxfabb=$("#wxfabb").val();
	var dprtcode=$("#dprtcode").val();
	window.open(basePath+"/project/fixedcheckworkcontent/maintenanceDerictory?wxfabh="+encodeURIComponent(wxfabh)+"&wxfazwms="+encodeURIComponent(wxfazwms)+"&bb="+encodeURIComponent(bb)+"&wxfabb="+encodeURIComponent(wxfabb)
 	+"&dprtcode="+dprtcode);
}
