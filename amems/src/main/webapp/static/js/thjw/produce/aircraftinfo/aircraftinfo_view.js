$(function(){
	Navigation(menuCode, '查看飞机基本信息', 'View', 'SC-1-2 ', '孙霁', '2017-09-25', '孙霁', '2017-09-25');//加载导航栏
	var aircraftinfo = {};
	aircraftinfo.fjzch = $("#aircraftinfoFjzch").val();
	aircraftinfo.dprtcode = $("#aircraftinfoDprtcode").val();
	selectById(aircraftinfo);
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	$('.page-content select').attr('disabled',true); 
});

function selectById(aircraftinfo){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aircraftinfo/selectById",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(aircraftinfo),
		dataType:"json",
		success:function(data){
			if(data != null){
				viewObj(data.aircraftinfo);
			}
		}
	});
}

function viewObj(obj){
	$("#fjjx").val(obj.fjjx);
	$("#fjzch").val(obj.fjzch);
	$("#xlh").val(obj.xlh);
	$("#bzm").val(obj.bzm);
	$("#jd").val(obj.base?obj.base.dprtname:'');
	$("#gjdjzfjid").val(obj.gjdjzfjid);
	$("#shzfjid").val(obj.shzfjid);
	$("#wxdtzzfjid").val(obj.wxdtzzfjid);
	if(obj.isSsbdw ==1){
		$("#isSsbdw").attr("checked","checked");
		$("#gkSelect").hide();
	}else{ 
		$("#isSsbdw").attr("checked",false);
		$("#gkSelect").show();
		var text = (obj.customer?obj.customer.khbm:'') +" "+(obj.customer?obj.customer.khmc:'');
		$("#khText").val(text);
	}
	$("#gmrq").val(obj.gmrq?obj.gmrq.substring(0,10):'');
	$("#scrq").val(obj.scrq?obj.scrq.substring(0,10):'');
	$("#ccrq").val(obj.ccrq?obj.ccrq.substring(0,10):'');
	$("#rhyzph").val(obj.rhyzph);
	$("#yyyph").val(obj.yyyph);
	$("#gjdjzh").val(obj.gjdjzh);
	$("#gjdjzjkrq").val(obj.gjdjzjkrq?obj.gjdjzjkrq.substring(0,10):'');
	$("#shzh").val(obj.shzh);
	$("#shzjkrq").val(obj.shzjkrq?obj.shzjkrq.substring(0,10):'');
	$("#shzzjkrq").val(obj.shzzjkrq?obj.shzzjkrq.substring(0,10):'');
	$("#wxdtxkzh").val(obj.wxdtxkzh);
	$("#wxdtbfrq").val(obj.wxdtbfrq?obj.wxdtbfrq.substring(0,10):'');
	$("#dtzzjkrq").val(obj.dtzzjkrq?obj.dtzzjkrq.substring(0,10):'');
	$("#gjdjzyxq").val(obj.gjdjzyxq?obj.gjdjzyxq.substring(0,10):'');
	$("#fdjsl").val(obj.fdjsl);
	$("#fdjsl").attr('disabled',true);	
	$("#jsgzjl").val(obj.jsgzjl);
	$("#bz").val(obj.bz);
	appendBj(obj.fdjsl);
	//回显初始化信息
	var planInitList = obj.planInitList;
	$.each(planInitList,function(index,planInit){
		var str = (planInit.wz+"").substring(0,1);
		if(str ==1){
			jshx(planInit);
		}else if(str ==2){
			fdjhx(planInit);
		}else if(str ==3){
			apuhx(planInit);
		}
		
	});
	//回显日使用量信息
	var planUsageList = obj.planUsageList;
	$.each(planUsageList,function(index,planUsage){
		rsyhx(planUsage);
	});
	var fjIds = [];
	if(obj.gjdjzfjid != null && obj.gjdjzfjid !=''){
		fjIds.push(obj.gjdjzfjid);
	}
	if(obj.shzfjid != null && obj.shzfjid !=''){
		fjIds.push(obj.shzfjid);
	}
	if(obj.wxdtzzfjid != null && obj.wxdtzzfjid !=''){
		fjIds.push(obj.wxdtzzfjid);
	}
	if(fjIds.length >0){
		//回显附件
		loadDtzzfj([obj.gjdjzfjid, obj.shzfjid, obj.wxdtzzfjid]);
	}
}

function rsyhx(planUsage){
	if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_10_FH"){
		$("#rq_fj").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
		$("#rq_fj").next().val(planUsage.id);
	}
	if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_30_EH"){
		$("#rq_fdj").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
		$("#rq_fdj").next().val(planUsage.id);
	}
	if(planUsage.jkflbh =="2T" && planUsage.jklbh=="2_20_AH"){
		$("#rq_apu").val(TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON)==0?'':TimeUtil.convertToHour(planUsage.rsyl, TimeUtil.Separator.COLON));
		$("#rq_apu").next().val(planUsage.id);
	}
	if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_10_FC"){
		$("#xh_fj").val(planUsage.rsyl);
		$("#xh_fj").next().val(planUsage.id);
	}
	if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_30_EC"){
		$("#xh_fdj").val(planUsage.rsyl);
		$("#xh_fdj").next().val(planUsage.id);
	}
	if(planUsage.jkflbh =="3C" && planUsage.jklbh=="3_20_AC"){
		$("#xh_apu").val(planUsage.rsyl);
		$("#xh_apu").next().val(planUsage.id);
	}
}

function jshx(planInit){
	if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
		$("#jscshrq").val(planInit.csz);
		$("#jscshrq").next().val(planInit.id);
	}
	if(planInit.jkflbh =="2T" && planInit.jklbh=="2_10_FH"){
		$("#jsfxxs").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
		$("#jsfxxs").next().val(planInit.id);
	}
	if(planInit.jkflbh =="3C" && planInit.jklbh=="3_10_FC"){
		$("#jsfxxh").val(planInit.csz);
		$("#jsfxxh").next().val(planInit.id);
	}
}

function apuhx(planInit){
	$("#tableTr5").find("input[name='bj_jh']").val(planInit.jh);
	$("#tableTr5").find("input[name='bj_xlh']").val(planInit.xlh);
	$("#tableTr5").find("td").eq(3).html(planInit.hCMainData?planInit.hCMainData.xingh:'');
	$("#tableTr5").find("td").eq(4).html(planInit.hCMainData?planInit.hCMainData.ywms:'');
	$("#tableTr5").find("td").eq(5).html(planInit.hCMainData?planInit.hCMainData.zwms:'');
	if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
		$("#tableTr5").find("input[name='rq']").val(planInit.csz);
		$("#tableTr5").find("input[name='rq']").next().val(planInit.id);
	}
	if(planInit.jkflbh =="2T" && planInit.jklbh=="2_20_AH"){
		$("#tableTr5").find("input[name='xs']").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
		$("#tableTr5").find("input[name='xs']").next().val(planInit.id);
	}
	if(planInit.jkflbh =="3C" && planInit.jklbh=="3_20_AC"){
		$("#tableTr5").find("input[name='xh']").val(planInit.csz);
		$("#tableTr5").find("input[name='xh']").next().val(planInit.id);
	}
}

function fdjhx(planInit){
	var i =(planInit.wz+"").substring(1,2);
	$("#tableTr"+i).find("input[name='bj_jh']").val(planInit.jh);
	$("#tableTr"+i).find("input[name='bj_xlh']").val(planInit.xlh);
	$("#tableTr"+i).find("td").eq(3).html(planInit.hCMainData?planInit.hCMainData.xingh:'');
	$("#tableTr"+i).find("td").eq(4).html(planInit.hCMainData?planInit.hCMainData.ywms:'');
	$("#tableTr"+i).find("td").eq(5).html(planInit.hCMainData?planInit.hCMainData.zwms:'');
	if(planInit.jkflbh =="1D" && planInit.jklbh=="1_10"){
		$("#tableTr"+i).find("input[name='rq']").val(planInit.csz);
		$("#tableTr"+i).find("input[name='rq']").next().val(planInit.id);
	}
	if(planInit.jkflbh =="2T" && planInit.jklbh=="2_30_EH"){
		$("#tableTr"+i).find("input[name='xs']").val(TimeUtil.convertToHour(planInit.csz, TimeUtil.Separator.COLON));
		$("#tableTr"+i).find("input[name='xs']").next().val(planInit.id);
	}
	if(planInit.jkflbh =="3C" && planInit.jklbh=="3_30_EC"){
		$("#tableTr"+i).find("input[name='xh']").val(planInit.csz);
		$("#tableTr"+i).find("input[name='xh']").next().val(planInit.id);
	}
}

//拼接部件
function appendBj(number){
	var appendHtml = '';
		//获取当前数据条数
		var tableTr = $("#bj_list_tbody").find("tr").length;
		if(tableTr < number){
			var ten =  number - tableTr
			for(var i= 1;i <= ten;i++){
				var colTr = tableTr+i;
				var tableTrId = "tableTr"+colTr;
				appendHtml += "<tr id="+tableTrId+" >";
				appendHtml += "<td class='text-right'>"+colTr+"#发动机</td>";
				appendHtml += "<td class='text-left'>";
					appendHtml += "<div class='input-group ' style='min-width:100%'>";
					appendHtml += "<input type='text'  value='' name='bj_jh' class='form-control' maxlength='100' id='ff'>";
					appendHtml += "</div>";
				appendHtml += "</td>";
				appendHtml += "<td>";
				appendHtml += "<input type='text' class='form-control' name='bj_xlh' maxlength='50' >";
				appendHtml += "</td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td class='text-center'></td>";
				appendHtml += "<td><input class='form-control date-picker-tb' name='rq' data-date-format='yyyy-mm-dd' type='text' /><input class='form-control' type='hidden' /></td>";
				appendHtml += " <td><div class='input-group'><input class='form-control fxyz' name='xs' type='text' maxlength='20' /><input class='form-control' type='hidden' /><span name='xs' class='input-group-addon dw'  style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EH</span></div></td>";
				appendHtml += "<td><div class='input-group'><input class='form-control fxzsyz' name='xh' type='text' maxlength='20' /><input class='form-control' type='hidden' /><span name='xh' class='input-group-addon dw'  style='min-width:45px;background-color:white;font-size: 12px;padding: 0 0 0 0;'>EC</span></div></td>";
				appendHtml += "</tr>";
				 
				
				 
			}
		}
	$("#bj_list_tbody").append(appendHtml);
}
/**
 * 加载附件
 * @param id
 */
function loadDtzzfj(ids) {
	if(ids!=''){
		
	}
	var strs = ["国籍登记证号","适航证号","无线电台许可证号"];
	AjaxUtil.ajax({
				url : basePath + "/common/loadPlaneDataAttachmentsByMainIds",
				type : "post",
				data : {
					idList: ids
				},
				success : function(data) {
					if (data.success) {
						var attachments = data.attachments;
						var len = data.attachments.length;
						if (len > 0) {
							var trHtml = '';
							for (var i = 0; i < len; i++) {
								trHtml = trHtml
										+ '<tr  id=\''
										+ attachments[i].uuid + '\' key=\''
										+ attachments[i].id + '\' >';
								trHtml = trHtml + '<td class="text-left">'+strs[ids.indexOf(attachments[i].mainid)]+'</td>';
								trHtml = trHtml
										+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(attachments[i].hzm)+'"> <a class="cursor-pointer" onclick="downfile(\''
										+ attachments[i].id + '\')" >'
										+ StringUtil.escapeStr(attachments[i].wbwjm) +"."+StringUtil.escapeStr(attachments[i].hzm) + '</a></td>';
								trHtml = trHtml + '<td class="text-left">'
										+ attachments[i].wjdxStr + ' </td>';
								trHtml = trHtml + '<td class="text-left"  title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
										+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
								trHtml = trHtml + '<td>' + attachments[i].czsj
										+ '</td>';
								trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
								trHtml = trHtml + '</tr>';
							}
							$('#attachments_list_tbody').empty();
							$('#attachments_list_tbody').append(trHtml);
						}
						if($("#attachments_list_tbody>tr").length == 0){
							$("#attachments_list_tbody").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
						}
					}
				}
			});
}
//附件下载
function downfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}