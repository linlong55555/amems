$(document).ready(function(){
	Navigation(menuCode, '查看培训计划', 'Training Plan');//加载导航栏

	into($("#id").val());//初始化数据
	
	$("#form input").attr("disabled",true);
	$("#form select").attr("disabled",true);
	$("#form textarea").attr("disabled",true);
	
});

//打开查看课程弹出框
function into(id){
	selectById(id,function(obj){
		setEdit();//设置只读/失效
		setData(obj);//设置表单数据
		 loadAttachmentEdit(id,true,true);//传入参数是否显示
		 initReserveDetail(id);//初始化修改数据
	});
}

//初始化修改数据
function initReserveDetail(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/course/selectByPrimaryKey",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			console.info(data);
			if(data !=null){
			     initTableRow(data.planPersonList);//人员
			}
		}
	});
}


//初始化人员
function initTableRow(data){
	 $("#messageListTable").empty();
	 if(data.length>0){
		//先移除暂无数据一行
		 	var len = $("#messageListTable").length;
		 	if (len == 1) {
		 		if($("#messageListTable").find("td").length == 1){
		 			$("#messageListTable").empty();
		 		}
		 	}
		 	orderNumber=1;
		 	$.each(data,function(index,reserve){
		 		var row = reserve;
		 		row.orderNumber = orderNumber++;
		 		addRow(row,index);
		 	});
	 }else{
		 $("#messageListTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
	 }
}

//向table新增一行
function addRow(obj,index){
	
	var tr = "";
		tr += "<tr>";
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += "</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.maintenancePersonnel.rybh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+obj.maintenancePersonnel.id+"\")'  name='content'>"+StringUtil.escapeStr(obj.maintenancePersonnel.rybh)+"</a></td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.xm)+"'>"+StringUtil.escapeStr(obj.xm)+"</td>";
	    if(obj.wbbs==2){
	    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='外部人员'>外部人员</td>";  
	    }else{
	    	tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gzdw)+"'>"+StringUtil.escapeStr(obj.gzdw)+"</td>";
	    }
		tr +=  "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.isYc == 1?"是":"否")+"'>"+StringUtil.escapeStr(obj.isYc == 1?"是":"否")+"</td>";
		var rr=obj.isSc;
		if(obj.isYc == 0){
			rr=1;
		}
		var isscchen="";
		
		if(rr==1){
			isscchen="checked='checked'";
		}else if(obj.isSc==0){
			isscchen="";
			 			
		}
		tr +=  "<td style='text-align:center;vertical-align:middle;' ><input name='isSc"+obj.orderNumber+"' onchange='onchliksc()' type='checkbox'  style='width: 20px;height: 20px;' "+isscchen+" /></td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cql)+"'><input type='text' class='form-control' name='cql'  value='"+StringUtil.escapeStr(obj.cql)+"' maxlength='12'  /></td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cj)+"'><input type='text' class='form-control' name='cj'  value='"+StringUtil.escapeStr(obj.cj)+"' maxlength='12'  /></td>";
		var checked="";
		var checked1="";
		if(obj.khjg==1||obj.khjg==null){
			checked="checked='checked'";
			checked1="";
		}else if(obj.khjg==0){
			checked="";
			checked1="checked='checked'";
		}
		tr +=  "<td style='text-align:left;vertical-align:middle;' ><label style='margin-right: 0px;font-weight: normal' ><input name='khjg"+obj.orderNumber+"' type='radio' value='1' "+checked+"  />通过 </label><label style='font-weight: normal' ><input name='khjg"+obj.orderNumber+"' type='radio' value='0' "+checked1+" />未通过</label> </td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zs)+"'><input type='text' class='form-control' name='zs'  value='"+StringUtil.escapeStr(obj.zs)+"' maxlength='100' /></td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(obj.xcpxrq).substring(0,10)+"'><input type='text' class='xiacipx form-control datetimepicker'  value='"+formatUndefine(obj.xcpxrq).substring(0,10)+"' name='xcpxrq' maxlength='100'  /></td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"'><input class='form-control'  name='bz' maxlength='300' value='"+StringUtil.escapeStr(obj.bz)+"'	  ></td>";
		tr += "<td style='vertical-align:middle' name='pxfj' colspan='2' width='40%'><table style='width:170px;'>"; 
		if(obj.attachments!=null){
			$.each(obj.attachments, function(i, row){
				tr +='<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+formatUndefine(row.id)+'\'>';
				tr += "<td class=\"text-left\" title='"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"'><a href=\"javascript:downloadfile('"+formatUndefine(row.id)+"')\"><input type=\"hidden\" name=\"operate\" value='EDIT'><input type=\"hidden\" name=\"fjid\" value='"+formatUndefine(row.id)+"'><input type=\"hidden\" name=\"yswjm\" value='"+StringUtil.escapeStr(row.yswjm)+"'><input type=\"hidden\" name=\"wbwjm\" value='"+StringUtil.escapeStr(row.wbwjm)+"'><input type=\"hidden\" name=\"nbwjm\" value='"+StringUtil.escapeStr(row.nbwjm)+"'><input type=\"hidden\" name=\"cflj\" value='"+StringUtil.escapeStr(row.cflj)+"'><input type=\"hidden\" name=\"wjdx\" value='"+StringUtil.escapeStr(row.wjdx)+"'>"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"</a></td>";
				tr += '</tr>';	
			});
	   }
	  tr += "</table></td>";  
	  tr += "</tr>";
	
		$("#messageListTable").append(tr);
		
}


/**
 * 跳转到查看页面
 * @param id
 */
function goToViewPage(id){
	window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
}

/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
} 
function loadAttachmentEdit(id,colOptionhead,fileHead){
	AttachmengsList.show({
		attachHead : false,
		chinaHead : '附件',
		englishHead : 'Courseware Information',
		chinaFileHead : '文件说明',
		englishFileHead : 'Courseware',
		djid:id,
 		colOptionhead : false,
		fileHead : false,
		fileType:"course"
	});//显示附件
}

//设置表单数据
function setData(obj){
	var jhlx="";
	 if(obj.jhlx=='1'){
		 jhlx="年度培训计划";
	 }else if(obj.jhlx=='2'){
		 jhlx="临时培训计划";
	 }
	 $("#jhlx").val(jhlx);
	$("#pxlb").val(obj.pxlb);
	$("#kcbh").html(obj.kcbh);
	$("#kcid").val(obj.kcid);
	
	$("#kcmc").val(obj.kcmc);
	$("input:radio[name='fxbs'][value='"+obj.fxbs+"']").attr("checked",true); 
	$("#ks").val(obj.ks);
	$("#pxxs").val(obj.pxxs);
	$("#ksxs").val(obj.ksxs);
	$("#jhKsrq").val(indexOfTime(obj.jhKsrq));
	$("#jhKssj").val(obj.jhKssj);
	$("#jhJsrq").val(indexOfTime(obj.jhJsrq));
	$("#jhJssj").val(obj.jhJssj);
	
	$("#sjKsrq").val(indexOfTime(obj.sjKsrq));
	$("#sjKssj").val(obj.sjKssj);
	$("#sjJsrq").val(indexOfTime(obj.sjJsrq));
	$("#sjJssj").val(obj.sjJssj);
	$("#sjks").val(obj.sjks);
	$("#xt").val(obj.xt);
	$("#zrr").val(obj.zrr);
	$("#pxjg").val(obj.pxjg);
	$("#pxdx").val(obj.pxdx);
	$("#pxys").val(obj.pxys);
	$("#pxysBz").val(obj.pxysBz);
	$("#kcdd").val(obj.kcdd);
	$("#jx").val(obj.fjjx);
	$("#zy").val(obj.zy);
	$("#jsxm").val(obj.jsxm);
	$("#jsid").val(obj.jsid);
	$("input:radio[name='jsBz'][value='"+obj.jsBz+"']").attr("checked",true); 
	$("input:radio[name='isJcff'][value='"+obj.isJcff+"']").attr("checked",true); 
	$("#personName").val(obj.personName);
	$("#kcnr").val(obj.kcnr);
	$("#bz").val(obj.bz);
	$("#zt").val(DicAndEnumUtil.getEnumName('trainingPlanStatusEnum',obj.zt));
	$("#whr").val(obj.zdr.displayName);
	$("#whsj").val(obj.whsj);
}

//移除只读/失效/隐藏
function setEdit(){
	$("#form input").attr("disabled",true);
	$("#form select").attr("disabled",true);
	$("#form textarea").attr("disabled",true);
}

//查看课程界面
function findCourse(){
	var id =$("#kcid").val();
	window.open(basePath+"/training/course/view?id="+$.trim(id));
}

//通过id获取培训计划数据
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/training/plan/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				var course = data.course;
				var planPersonList = data.planPersonList;
				if(planPersonList != null && planPersonList.length > 0){
					var personName = '';
					$.each(planPersonList, function(i, row){
				
						if(row.maintenancePersonnel != null){
							var displayName = StringUtil.escapeStr(row.maintenancePersonnel.rybh) + " " + StringUtil.escapeStr(row.maintenancePersonnel.xm);
							personName += displayName + ",";
						}
					});
					
					if(personName != ''){
						personName = personName.substring(0,personName.length - 1);
					}
					data.personName = personName;
				}
				
				if(null != course){
					data.kcbh = course.kcbh;
					data.kcmc = course.kcmc;
				}
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		}
	});
}