var monitorList = [];
$(document).ready(function(){
	Navigation(menuCode,"查看详情","View");
	getFixedCheckItemList(1,"auto","asc");//开始的加载默认的内容 
	setReadonlyDisabled();
	initMonitor();
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
		},
		error:function(data){
			AlertUtil.showErrorMessage(data.responseText);
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

//设置只读或失效
function setReadonlyDisabled(){
	$("input").attr("readonly","readonly");
	$("select").attr("disabled","true");
	$("#isMel").attr("disabled","true");
	$("#fixBz").attr("readonly","readonly");
}

//获取定检项目列表:三个参数依次为:当前页码,排序字段,排序规则 
function getFixedCheckItemList(pageNumber,sortType,sequence){
	var searchParam = {};
	var wxfabh = $.trim($("#wxfabh").val());
	var bb = $.trim($("#bb").val());
	var dprtcode = $.trim($("#dprtcode").val());
	searchParam.wxfabh = wxfabh;
	searchParam.bb = bb;
	searchParam.dprtcode = dprtcode;
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:100};
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckitem/queryAllPageList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(searchParam),
		success:function(data){
			finishWait();
			$("#fixedCheckItem").empty();
			if(data.rows.length >0){
				$.each(data.rows,function(index,row){
					var content = ['<a href="#" id="'+row.id+'" class="list-group-item" onclick="loadFixDetail(this)" ',
					              ' style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding: 5px 20px 5px 5px;">',
				                  '<font>',
				                  " <span title='"+StringUtil.escapeStr(row.zwms)+"'>" + StringUtil.escapeStr(row.zwms)+"</span>",
				                  '</font>',
				                  ('<span style="right:5px;position:absolute"> V'+row.bb+'</span>'),
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
	// 高亮选中行
	$("#fixedCheckItem .list-group-item.active").removeClass("active");
	$(obj).addClass("active");
	
	var id = $(obj).attr('id');
	buildFixForm(id);
	setMonitorItem(id);
	initFixedWorkContent(id);
}

function buildFixForm(id){
	AjaxUtil.ajax({
 		url:basePath+"/project/fixedcheckitem/selectById",
 		type:"post",
 		data:{'id' : id},
 		dataType:"json",
 		success:function(data){
 			if(null != data){
 				$("#fixId").val(data.id);
 				$("#fixCode").val(data.djbh);
 				$("#fixName").val(data.zwms);
 				$("#fixBb").val(data.bb);
 				$("#fixZyxs").val(data.zyxs);
 				$("#fixYxx").val(data.yxx);
 				$("#jhgsRs").val(data.jhgsRs);
 				$("#jhgsXss").val(data.jhgsXss);
 				$("#fixPxh").val(data.pxh);
 				$("#fixBz").text(data.bz);
 				sumTotal();
 			}	
 		}
 	});
}

function setMonitorItem(id){
	AjaxUtil.ajax({
		url:basePath+"/project/monitoritem/queryListByDjxmid",
		type:"post",
		async: false,
		data:{djxmid:id},
		dataType:"json",
		success:function(data){
			loadMonitorItem(data);
		}
	});
}

function loadMonitorItem(fixMontList){
	
	$("#monitoring").empty();
	
	if(JSON.stringify(fixMontList) == '[]'){
		return false;
	}
	var str = '';
	$.each(fixMontList,function(i,f){
		str += '<div style="background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius: 4px;margin-bottom: 10px;min-height: 20px;padding: 10px 19px 0;"';
		str += '	<div class="row padding-top-10">';
		str += '		<div class="col-sm-4 padding-left-0 padding-right-0">';
		str += '			<font><strong>';
		str += 					'监控条件：'+getMonitorName(f.jklbh);
		str += '			</strong></font>';
		str += '		</div>';
		str += '		<div class="col-sm-8 padding-left-0 padding-right-0">';
		str += '			<font class="col-xs-2 text-right padding-left-0 padding-right-0">';
		str += '				<div class="font-size-12 line-height-36">周期值</div>';
		str += '				<div class="font-size-9 line-height-18">Value</div>';
		str += '			</font>';
		str += '			<div class="col-xs-7 padding-left-8 padding-right-0" style="padding-top:6px;" >';
		str += f.zqz+" "+DicAndEnumUtil.getEnumName('unitEnum',f.dw);
		str += '			</div>';
		str += '		</div>';
		str += '	</div>';
		str += '</div>';
	});
	$("#monitoring").append(str);
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

function initFixedWorkContent(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixedcheckworkcontent/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:id},
		dataType:"json",
		success:function(data){
			initTableCol(data);
		}
	});
}

//初始化表格
function initTableCol(data){
	
	//先移除暂无数据一行
	$("#rotatable").empty();
	
	if(JSON.stringify(data) == '[]'){
		$("#rotatable").append("<tr><td  colspan='18' class='text-center'>暂无数据 No data.</td></tr>");
		return;
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
		addRow(workObj);
	});
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
	
	tr += "<tr>";
	
	tr += "<td style='text-align:center;vertical-align:middle;'>";
	tr += obj.option;
	tr += "</td>";
	
	tr += "<td title='"+StringUtil.escapeStr(obj.nbxh)+"' style='text-align:center;vertical-align:middle;'>";
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
	
	tr += "</tr>";
		
	$("#rotatable").append(tr);
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

function viewWorkCard(id){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + $("#dprtcode").val()+"&t=" + new Date().getTime());
}

function sumTotal(){
	var totle = 0;
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	totle = jhgsRs*jhgsXss;
	if(totle == ''){
		totle = 0;
	}
	totle = totle.toFixed(2);
	$("#bzgs").val(totle);
}

//定检项目信息显示与隐藏 
function vieworhide() {
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

//返回前页面
function back(){
	 window.location.href =basePath+"/project/confirmationofrevision/main?pageParam="+pageParam;
}