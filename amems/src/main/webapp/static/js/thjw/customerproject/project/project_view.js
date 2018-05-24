$(function(){
	Navigation(menuCode, '查看项目', 'View', ' ', '甘清', '2017-10-13', '甘清', '2017-10-13');//加载导航栏
	loadProjectInfo();
});

//ajax加载项目详细信息
function loadProjectInfo() {
	showProjectextend();
	var id = $("#projectid").val();
	var object={};
		 object.id = id;
	 startWait();
	 AjaxUtil.ajax({
			url: basePath+"/customerproject/project/getprojectbyid",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(object),
			dataType:"json",
			//modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait();
				//赋值操作
				reproduceDatas(massge);
			}
		});
}

//编辑项目赋值方法
function reproduceDatas(massge) {
	$("#project_id").val(massge.project.id);
	$("#xmbm").val(massge.project.xmbm);
	$("#xmmc").val(massge.project.xmmc);
	$("#xmzl").val(massge.project.xmzl);//find("option[value='"+massge.project.xmzl+"']").attr("selected",true);
	if (massge.project.wbbs == 1) {
		//客户信息 
		$("#cust_no").val(massge.project.khbm);
		$("#cust_name").val(massge.project.khmc);
		$("#cust_id").val(massge.project.khid);
		
		$("input[type='radio'][value='1'][name='pro_fl']").attr('checked',true);
		$("#customer_No_1").css("display","block");
		$("#customer_Name_1").css("display","block");
		$("#deptment_Name_1").css("display","none");
		$("#deptment_No_1").css("display","none");
	} else { //部门编号
		$("#dept_no").val(massge.project.khbm);
		$("#dept_name").val(massge.project.khmc);
		$("#dept_id").val(massge.project.khid);
		
		$("input[type='radio'][value='0'][name='pro_fl']").attr('checked',true);
		
		$("#customer_No_1").css("display","none");
		$("#customer_Name_1").css("display","none");
		$("#deptment_Name_1").css("display","block");
		$("#deptment_No_1").css("display","block");
	}
	
	$("#fjzch").val(massge.project.fjzch);
	$("#fjxlh").val(massge.project.fjxlh);
	$("#fjjx").val(massge.project.fjjx); //飞机机型
	$("#fjbzm").val(massge.project.fjbzm); //飞机备注名
	$("#fjxlh").val(massge.project.fjxlh); //飞机序列号
	$("#hbh").val(massge.project.hbh); //飞机航班号
	if(massge.project.fjjx){
		$("#view_fjjxInfo_div").show();
	}else{
		$("#view_fjjxInfo_div").hide();
	}
	
	//$("#fjxlh").val(massge.project.fjxlh);
	$("#jh_ksrq").val(massge.project.jhksrqstr);
	$("#jh_jsrq").val(massge.project.jhjsrqstr);
	$("#sj_ksrq").val(massge.project.sjksrqstr);
	$("#sj_jsrq").val(massge.project.sjjsrqstr);
	
	//飞机飞行小时
	$("#fxsj").val(massge.project.fxsjstr);
	
	$("#fxxh").val(massge.project.fxxh);
	$("#ipcyxxh").val(massge.project.ipcyxxh);
	$("#rhyzph").val(massge.project.rhyzph);
	$("#yyyph").val(massge.project.yyyph);
	$("#yyyph").val(massge.project.yyyph);
	//$("#fjzt").find("option[value='"+massge.project.fjzt+"']").attr("selected",true); //飞机状态
	$("#fjzt").val(massge.project.fjzt); //飞机状态
	
	// engine & apu
	if (massge.project.projectMainAreas != null) {
		$.each(massge.project.projectMainAreas, function(i, obj) {
			switch(obj.wz) {
			case 31:   //APU
				$("#apu_jh").val(obj.jh);
				$("#apu_xh").val(obj.xh);
				$("#apu_xlh").val(obj.xlh);
				if (obj.jklbh=="2_20_AH" && obj.jkflbh=="2T") {
					$("#apu_id_h").val(obj.id);
					$("#apu_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_20_AC" && obj.jkflbh=="3C") {
					$("#apu_id_c").val(obj.id);
					$("#apu_cv").val(obj.sjz);
				}
				break;
			case 21:  //1#
				$("#e1_jh").val(obj.jh);
				$("#e1_xh").val(obj.xh);
				$("#e1_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e1_id_h").val(obj.id);
					$("#e1_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e1_id_c").val(obj.id);
					$("#e1_hc").val(obj.sjz);
				}
				break;
			case 22:  //2#
				$("#e2_jh").val(obj.jh);
				$("#e2_xh").val(obj.xh);
				$("#e2_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e2_id_h").val(obj.id);
					$("#e2_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e2_id_c").val(obj.id);
					$("#e2_hc").val(obj.sjz);
				}
				break;
			case 23:  //3#
				$("#e3_jh").val(obj.jh);
				$("#e3_xh").val(obj.xh);
				$("#e3_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e3_id_h").val(obj.id);
					$("#e3_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e3_id_c").val(obj.id);
					$("#e3_hc").val(obj.sjz);
				}
				break;
			case 24:  //4#
				$("#e4_jh").val(obj.jh);
				$("#e4_xh").val(obj.xh);
				$("#e4_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e4_id_h").val(obj.id);
					$("#e4_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e4_id_c").val(obj.id);
					$("#e4_hc").val(obj.sjz);
				}
				break;
			}
		});
	}
	
	$("#xsddh").val(massge.project.xsddh);
	$("#fstk").val(massge.project.fstk);
	$("#xmjl").val(massge.project.xmjl);
	$("#jhzk").val(massge.project.jhzk);
	$("#kzs").val(massge.project.kzs);
	$("#qsgs").val(massge.project.qsgs);
	$("#kzsdh").val(massge.project.kzsdh);
	//获得附件信息
	if (massge.project.attachments != null) {
		var trHtml = "";
		$.each(massge.project.attachments,function(i, att){
			trHtml = trHtml + '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\'tr_'+att.id+'\'>';
	//		trHtml = trHtml+'<td class="text-center"><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
	//		trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+att.id+ '\')" title="Delete 删除"> ';
	//		trHtml = trHtml+'</div></td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
			trHtml = trHtml+"<td class='text-left'><a onclick=\"downloadfile('"+att.id+"')\">"+StringUtil.escapeStr(att.wbwjm)+"</a><input type='hidden' value='"+att.nbwjm+"'></td>";
			
			trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(att.wjdxStr)+"<input type='hidden' value='"+att.wjdx+"'></td>";
			//trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
			trHtml = trHtml+"<td class='text-left'>"+att.czrname+"</td>";
			trHtml = trHtml+"<td class='text-center'>"+att.czsj
			+'<input type="hidden" name="relativePath" value=\''+att.cflj+'\'/>'
			+'<input type="hidden" name="hzm" value=\''+att.hzm+'\'/>'
			+'<input type="hidden" name="bs" value="11"/></td>'; //11，标识原始数据
			// trHtml = trHtml+"<td class='text-left'>"+att.cflj+"</td>";
			
			trHtml = trHtml+'</tr>';
		});
		$("#noAttachment_x").remove(); //移除暂无合同提示
		$("#filelist").append(trHtml);
	} else {
		if ($("#filelist").find("tr").length < 1) {
			 $("#filelist").append('<tr id="noAttachment_x"><td colspan="4" class="text-center">暂无数据 No data.</td></tr>');
		 }
	}
	//格式化循环值文本框
	//formatCycleInput();
}

//格式化循环文本输入框
function formatCycleInput() {
	TimeUtil.addTimeValidate("input[name='fxsj']");
	TimeUtil.addTimeValidate("input[name='init_time_apuh']");
	TimeUtil.addTimeValidate("input[name='init_time_1eh']");
	TimeUtil.addTimeValidate("input[name='init_time_2eh']");
	TimeUtil.addTimeValidate("input[name='init_time_3eh']");
	TimeUtil.addTimeValidate("input[name='init_time_4eh']");
}

//所有input置灰
function showProjectextend(){
	$("#projectDetail").find("input[type='text']").attr("disabled","disabled"); 
	$("#projectDetail").find("input[type='radio']").attr("disabled","disabled"); 
	$("#projectDetail").find("textarea").attr("disabled","disabled"); 
	//$("#AddEditAlertModal").find("select").attr("disabled","disabled"); 
	//$("#materialSave").hide();  //按钮隐藏
	//对上传的合同附件单独处理
	if ($("#noAttachment_x").length > 0) {
		$("#filelist").empty();
		$("#filelist").append('<tr id="noAttachment_x"><td colspan="4" class="text-center">暂无数据 No data.</td></tr>');
	} 
}

//附件下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}