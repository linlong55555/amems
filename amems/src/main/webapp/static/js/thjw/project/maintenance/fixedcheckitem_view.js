
var fjjx = '';

$(function() {
	//Navigation(menuCode,"批准定检项目","View");
	getFjjxByWxfabh();
	setMonitorItem();
	initFixedWorkContent();
	sumTotal();
	setReadonlyDisabled();
});

//设置只读或失效
function setReadonlyDisabled(){
	$("input").attr("readonly","readonly");
	$("select").attr("disabled","true");
	$("#isMel").attr("disabled","true");
	$("#bz").attr("readonly","readonly");
}

function setMonitorItem(){
	AjaxUtil.ajax({
		url:basePath+"/project/monitoritem/queryListByDjxmid",
		type:"post",
		async: false,
		data:{djxmid:$("#id").val()},
		dataType:"json",
		success:function(data){
			returnView(data);
			setDisabled();
		}
	});
}

function initFixedWorkContent(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixedcheckworkcontent/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:$("#id").val()},
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
		$("#rotatable").append("<tr style='height:35px;'><td colspan='18' class='text-align:center;vertical-align:middle;'>暂无数据 No data.</td></tr>");
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

//通过维修方案编号获取机型
function getFjjxByWxfabh(){
	var wxfabh = $.trim($("#wxfabh").val());
	AjaxUtil.ajax({
 		url:basePath+"/project/maintenance/getFjjxByWxfabh",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{wxfabh:wxfabh,dprtcode:$("#dprtcode").val()},
 		success:function(data){
	    	if(data != null && data != ''){
	    		fjjx = data;
	    	}
 		}
	}); 
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
	
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += obj.option;
		tr += "</td>";
		
		tr += "<td title='' style='text-align:center;vertical-align:middle;'>";
		tr += StringUtil.escapeStr(obj.nbxh);
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+obj.id+"'/>";
		tr += "<input type='hidden' class='form-control' name='nbxh' value='"+obj.nbxh+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='zjhStr'>"+StringUtil.escapeStr(obj.zjhStr)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.xmly)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += StringUtil.escapeStr(obj.xmly);
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.gzlxStr)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += StringUtil.escapeStr(obj.gzlxStr);
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.wz)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='wz'>"+StringUtil.escapeStr(obj.wz)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.scmsZw)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='scmsZw'>"+StringUtil.escapeStr(obj.scmsZw)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.scmsYw)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='scmsYw'>"+StringUtil.escapeStr(obj.scmsYw)+"</span>";
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
				if(s.fjzch != null && s.fjzch != '' && s.fjzch != 'null'){
					fjzchStr += s.fjzch+",";
				}
			});
			if(fjzchStr != ''){
				fjzchStr = StringUtil.escapeStr(fjzchStr);
				fjzchStr = fjzchStr.substring(0,fjzchStr.length - 1);
			}
	    }
		tr += "<td title='"+StringUtil.escapeStr(fjzchStr)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += fjzchStr;
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.gzzw)+"' style='text-align:right;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='gzzw'>"+StringUtil.escapeStr(obj.gzzw)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.cksc)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='cksc'>"+StringUtil.escapeStr(obj.cksc)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.ckgk)+"' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='ckgk'>"+StringUtil.escapeStr(obj.ckgk)+"</span>";
		tr += "</td>";
		
		tr += "<td style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += formatWorkCard(obj.djgkList,obj.zt);
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

function formatWorkCard(djgkList,zt){
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
	var display = "display:none";
	if(zt == 1){
		display = "";
	}
	card += "&nbsp;&nbsp;<i style='"+display+"' class='icon-plus color-blue cursor-pointer gk' onclick=addWorkCard(this) title='新增 Add' ></i>";
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

function addWorkCard(e){
	var id = $.trim($(e).parents("tr").find("input[name='hiddenid']").val());
	window.open(basePath+"/project/jobCard/intoAddJobCard?id="+id);
}

function viewWorkCard(id){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + $("#dprtcode").val()+"&t=" + new Date().getTime());
}
//审核通过
function agreedAudit(){
	var shyj = $.trim($("#shyj").val());
	var id = $.trim($("#id").val());
	
	var fixedCheckItem = {
			id : id,
			shyj : shyj
		};
	
	AlertUtil.showConfirmMessage("您确定要审核通过吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/fixedcheckitem/agreedAudit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('审核通过!','/project/fixedcheckitem/main?id='+id+'&wxfabh='+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam);
			}
		});
	
	}});
}
//审核驳回
function rejectedAudit(){
	var shyj = $.trim($("#shyj").val());
	var id = $.trim($("#id").val());
	if(shyj == "" || shyj == null){
		AlertUtil.showErrorMessage("审核意见不能为空!");
		return false;
	}
	var fixedCheckItem = {
			id : id,
			shyj : shyj
		};
	AlertUtil.showConfirmMessage("您确定要审核驳回吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/fixedcheckitem/rejectedAudit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('审核驳回!','/project/fixedcheckitem/main?id='+id+'&wxfabh='+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam);
			}
		});
	
	}});
}

//批准通过
function agreedApprove(){
	var pfyj = $.trim($("#pfyj").val());
	var id = $.trim($("#id").val());
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	
	AlertUtil.showConfirmMessage("您确定要批准通过吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/fixedcheckitem/agreedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('批准通过!','/project/fixedcheckitem/main?id='+id+'&wxfabh='+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam);
			}
		});
	
	}});
}

//批准中止
function stopApprove(){
	var pfyj = $.trim($("#pfyj").val());
	var id = $.trim($("#id").val());
	
	if(pfyj == "" || pfyj == null){
		AlertUtil.showErrorMessage("批准意见不能为空!");
		return false;
	}
	
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	
	AlertUtil.showConfirmMessage("您确定要批准中止吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/fixedcheckitem/stopApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('批准中止!','/project/fixedcheckitem/main?id='+id+'&wxfabh='+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam);
			}
		});
	
	}});
}

//批准驳回
function rejectedApprove(){
	var pfyj = $.trim($("#pfyj").val());
	var id = $.trim($("#id").val());
	if(pfyj == "" || pfyj == null){
		AlertUtil.showErrorMessage("批准意见不能为空!");
		return false;
	}
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	
	AlertUtil.showConfirmMessage("您确定要批准驳回吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/fixedcheckitem/rejectedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('批准驳回!','/project/fixedcheckitem/main?id='+id+'&wxfabh='+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam);
			}
		});
	
	}});
}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/fixedcheckitem/main?wxfabh="+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam;
}

