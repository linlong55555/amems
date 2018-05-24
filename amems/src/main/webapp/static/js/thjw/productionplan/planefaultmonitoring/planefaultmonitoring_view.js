var delAttachements = [];
$(function(){
		 refreshPermission();
		 Navigation(menuCode,"查看飞机故障监控","Look AircraftFault");
		 var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = '';
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				});
			}
			$("#fjzch").html(planeRegOption);
			$("#fjzch").val($("#fjzchid").val());
			loadAttachments();
			loadInfo();
	 });

function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
function loadAttachments(){
	AjaxUtil
	.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		data : {
			mainid : $("#id").val()
		},
		success : function(data) {
			if (data.success) {
				var attachments = data.attachments;
				var len = data.attachments.length;
				if (len > 0) {
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml
								+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ attachments[i].uuid + '\' key=\''
								+ attachments[i].id + '\' >';
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm)
								+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left">'
								+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml = trHtml + '<td>'
								+ attachments[i].czsj + '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath
								+ '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#filelist').append(trHtml);
				}else{
					$('#filelist').append("<tr ><td  class='text-center'  colspan=\"4\">暂无数据 No data.</td></tr>");
				}
			}
		}
	});
}

function update(){
	var attachments = [];
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments.push({
				'yswjm' : responses[i].attachments[0].yswjm,
				'wbwjm' : responses[i].attachments[0].wbwjm,
				'nbwjm' : responses[i].attachments[0].nbwjm,
				'wjdx' : responses[i].attachments[0].wjdx,
				'cflj' : responses[i].attachments[0].cflj,
				'id' : responses[i].attachments[0].id,
				'operate' : responses[i].attachments[0].operate

			});
		}
	}
	var dellen = delAttachements.length;
	if (dellen != undefined && dellen > 0) {
		for (var i = 0; i < dellen; i++) {
			attachments.push({
				'id' : delAttachements[i].id,
				'operate' : 'DEL'

			});
		}
	}
	var param = {};
	param.attachments = attachments;
	param.fjzch=$("#fjzch").val();
	param.gzxx=$("#gzxx").val();
	param.bz=$("#bz").val();
	param.gbzt=0;
	param.id=$("#djid").val();
	param.dprtcode=$("#dprtcode").val();
	param.zddwid=$("#zddwid").val();
	param.zdrid=$("#zdrid").val();
	AjaxUtil.ajax({
		url : basePath
				+ "/produce/planefaultmonitoring/update",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			AlertUtil.showMessage('保存成功!',
					'/productionplan/planefaultmonitoring/main?id=' + data);
			finishWait();
			refreshPermission();
			uploadObj.responses = [];
		}
	});
}

function loadInfo(){
	var searchParam={};
	searchParam.mainid=$("#id").val();
	AjaxUtil.ajax({
		url : basePath + "/produce/planefaultmonitoring/getInfoList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtmlInfo(data.rows);
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
				signByKeyword($.trim($("#keyword").val()),
						[ 3 ],"#fjgzjk_list tr td")
			} else {
				$("#dcgzcl_list").empty();
				$("#dcgzcl_list").append(
						"<tr><td colspan=\"8\">暂无数据 No data.</td></tr>");
			}
		}
	});
}
var chosesId=[];
function appendContentHtmlInfo(list){
	var htmlContent = '';
	chosesId=[];
	$.each(list,function(index,row){
		htmlContent += "<tr >";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.hbh)+"'>"+StringUtil.escapeStr(row.hbh)+"</td>";  
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.hbrq.substring(0,10))+"'>"+StringUtil.escapeStr(row.hbrq.substring(0,10))+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pgsl)+"'>"+StringUtil.escapeStr(row.pgsl)+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.cljg)+"'>"+StringUtil.escapeStr(row.cljg)+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zlh)+"'>"+StringUtil.escapeStr(row.zlh)+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.cxjxx)+"'>"+StringUtil.escapeStr(row.cxjxx)+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zsjxx)+"'>"+StringUtil.escapeStr(row.zsjxx)+"</td>";
	    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+getAttacments(row.attachmentList)+"</td>";
	    htmlContent += "</tr>"
	    $("#dcgzcl_list").empty();
	    $("#dcgzcl_list").html(htmlContent);
		 
});
}

function getAttacments(attachments){
				var attachment='';
				if(attachments!=null){
				var len = attachments.length;
				if (len > 0) {
					for (var i = 0; i < len; i++) {
						chosesId.push(attachments[i].id);
						if (i == 1) {
							attachment = attachment
									+ "　<i class='icon-caret-down' id='"
									+ attachments[i].id
									+ "icon' onclick=switchfj('"
									+ attachments[i].id + "')></i><div id='"
									+ attachments[i].id
									+ "' style='display:none'>";
							}
						if(i==0){
							attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To7(StringUtil.escapeStr(attachments[i].wbwjm))+"</a>";
						}				
						if (i != 0) {
							attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To10(StringUtil.escapeStr(attachments[i].wbwjm))+"</a>";
							attachment += "<br>";

						}
						if (i != 0 && i == len - 1) {
							attachment += "</div>";
						}
					}
				}
				}
	return attachment;
}

function back(){
//	window.location =basePath+"/produce/planefaultmonitoring/main?id="+$("#id").val()+"&pageParam="+pageParam;
	window.history.back();
}

function vieworhideZjqkContent(){
	 new Sticky({tableId:'fjgz_record_sheet_table'});
	 var obj = $("th[name='fj']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	for(var i=0;i<chosesId.length;i++){
		if(flag){				
			$("#"+chosesId[i]).fadeIn(500);
			$("#"+chosesId[i]+'icon').removeClass("icon-caret-down");
			$("#"+chosesId[i]+'icon').addClass("icon-caret-up");
		}else{
			$("#"+chosesId[i]).hide();
			$("#"+chosesId[i]+'icon').removeClass("icon-caret-up");
			$("#"+chosesId[i]+'icon').addClass("icon-caret-down");
		}
	}
}
	
	function switchfj(id) {
		new Sticky({tableId:'fjgz_record_sheet_table'});
		if ($("#" + id).is(":hidden")) {
			$("#" + id).fadeIn(500);
			$("#" + id + 'icon').removeClass("icon-caret-down");
			$("#" + id + 'icon').addClass("icon-caret-up");
		} else {
			$("#" + id).hide();
			$("#" + id + 'icon').removeClass("icon-caret-up");
			$("#" + id + 'icon').addClass("icon-caret-down");
		}
	}
	
	function substring0To7(str){
		if(str!="" && str!=null ){
			if(str.length>7){
				return str.substring(0,7)+"...";
			}
			return str;
		}
		return str;
	}
 function substring0To10(str){
		if(str!="" && str!=null ){
			if(str.length>10){
				return str.substring(0,10)+"...";
			}
			return str;
		}
		return str;
	}