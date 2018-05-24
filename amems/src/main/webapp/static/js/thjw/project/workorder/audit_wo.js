$(function() {
	
	Navigation(menuCode,"审核工单","Review W/O");
	
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").val(jhgsRs*jhgsXss);
	}
	var isXyjszy = $("#isXyjszy").val();
	isXyjszy==1?$("#isXyjszy").val("是"):$("#isXyjszy").val("否");
	 
	var diczt=DicAndEnumUtil.getEnumName('workOrderStateEnum',$("#gdzt").val());  //翻译工单状态
	$("#gdzt").val(diczt);
	
	var zhuanye=$("#zy-wo").val();
	$("#zy").val(zhuanye);
	
	var zxflvalue=$("#zxfl").val();
	if(zxflvalue=="ZJJ"){
		$("#zxfl").val("飞机部件");
	}else if(zxflvalue=="FZJJ"){
		$("#zxfl").val("非装机件");
	}else{
		$("#zxfl").val("飞机");
	}
	if($("#gkid").val()==""){
		$("#jobcard_demo").hide();
	}else{
		$("#jobcard_demo").show();
	}
	var dprtcode=$("#dprtcode_parma").val();
	var fjzch=$("#fjzch").val();
	AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
		type : 'post',
		url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+dprtcode,
		dataType : 'json',
		success : function(data) {
			if(data!=null){
				$("#fjxlh").val(data.fjXlh);
				$("#zfxh").val(data.zfXlh);
				$("#yfxh").val(data.yfXlh);
			}
		}
	});
	if($("#gddlx").val()==3){
		var gczlid=$("#gczlid").val();
		   AjaxUtil.ajax({                                                           //根据工程指令id取得机型
				type : 'post',
				url : basePath+"/project/workorder/getJx?gczlid="+gczlid,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						$("#fjjx").val(data.fjjx);
					}
				}
			});
	}else{
		var fjzch2=$("#zxdxfjzch").val();
		AjaxUtil.ajax({                                                           //根据飞机注册号取得机型
			type : 'post',
			url : basePath+"/productionplan/planeData/findJx?fjzch="+encodeURIComponent(fjzch2)+"&dprtcode="+dprtcode,
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					$("#fjjx").val(data.fjjx);
				}
			}
		});
	}
	
	var gzzwvalue=$("#gzzw-wo").val()==""?"-":$("#gzzw-wo").val();
	$("#gzzw").val(gzzwvalue);
	
	$("select").attr("disabled",true);
	$("input").attr("disabled",true);
	
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	
	//监控项目赋值
	var  jkzF=$("#old_jkzF").val();
	var  jkxmbhF=$("#old_jkxmbhF").val();
	if(jkzF!=null && jkzF!=""){
		if(jkxmbhF=="calendar"){
			$("#wcrq").val(jkzF);
		}
		if(jkxmbhF=="fuselage_flight_time"){
			$("#jssj").val(jkzF);
		}
		if(jkxmbhF=="landing_gear_cycle"){
			$("#qlxh").val(jkzF);
		}
		if(jkxmbhF=="winch_time"){
			$("#jcsj").val(jkzF);
		}
		if(jkxmbhF=="winch_cycle"){
			$("#jcxh").val(jkzF);
		}
	}
	
	 var  jkzS=$("#old_jkzS").val();
	 var  jkxmbhS=$("#old_jkxmbhS").val();	
	if(jkzS!=null && jkzS!=""){
		if(jkxmbhS=="calendar"){
			$("#wcrq").val(jkzS);
		}
		if(jkxmbhS=="fuselage_flight_time"){
			$("#jssj").val(jkzS);
		}
		if(jkxmbhS=="landing_gear_cycle"){
			$("#qlxh").val(jkzS);
		}
		if(jkxmbhS=="winch_time"){
			$("#jcsj").val(jkzS);
		}
		if(jkxmbhS=="winch_cycle"){
			$("#jcxh").val(jkzS);
		}
	}
	
	var   jkzT=$("#old_jkzT").val();
	 var  jkxmbhT=$("#old_jkxmbhT").val();	
	if(jkzT!=null && jkzT!=""){
		if(jkxmbhT=="calendar"){
			$("#wcrq").val(jkzT);
		}
		if(jkxmbhT=="fuselage_flight_time"){
			$("#jssj").val(jkzT);
		}
		if(jkxmbhT=="landing_gear_cycle"){
			$("#qlxh").val(jkzT);
		}
		if(jkxmbhT=="winch_time"){
			$("#jcsj").val(jkzT);
		}
		if(jkxmbhT=="winch_cycle"){
			$("#jcxh").val(jkzT);
		}
	}	
	if($("#gddlx").val()==2){
		if($("#gdlx").val()==1){
			$("#demo1").css("display", "none");  
			$("#demo2").css("display", "none");  
			$("#demo3").css("display", "none");  
			$("#zxdxtr select").attr("disabled",true);
			$("#zxdxtr input").attr("disabled",true);
			loadJkxm();
		}else{
			$("#demo1").css("display", "none");  
		}
		//$("#div_css1").addClass("clearfix"); 
	}else if($("#gddlx").val()==3){
		$("#demo2").css("display", "none");  
		$("#demo3").css("display", "none");  
		$("#demo5").css("display", "none"); 
		$("#demo6").css("display", "none");
	//	$("#div_css2").addClass("clearfix"); 
	}else{
		$("#demo5").css("display", "none"); 
		$("#demo1").css("display", "none"); 
		$("#demo3").css("display", "none");
		$("#demo2").css("display", "none"); 
		$("#demo6").css("display", "none");
	//	$("#div_css2").addClass("clearfix"); 
		loadJkxm();
	}
	
});

function ViewJobCard(){
	 var id= $("#temp_id").val();
	 var dprtcode= $("#temp_dprtcode").val();
	 window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode="+dprtcode);
}
//针对时控件和定检工单动态加载监控项
function loadJkxm(){
	var num=0;
    if($("#old_jkxmbhF").val()!=null&&$("#old_jkxmbhF").val()!=""){
    	num++;
    }
    if($("#old_jkxmbhS").val()!=null&&$("#old_jkxmbhS").val()!=""){
    	num++;
    }
    if($("#old_jkxmbhT").val()!=null&&$("#old_jkxmbhT").val()!=""){
    	num++;
    }
    if(num==3){
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
    	$("#div_1").attr("colspan", 3);
    	
    	$("#value_1").text($("#old_jkxmbhF").val());
    	$("#wcrq").val($("#old_jkzF").val());
    	
    	var zwmc1=$("#value_1").html();
    	$("#value_4").text(transferEnglish(zwmc1));
    	
    	$("#value_2").text($("#old_jkxmbhS").val());
    	$("#jssj").val($("#old_jkzS").val());
    	
    	var zwmc2=$("#value_2").html();
    	$("#value_5").text(transferEnglish(zwmc2));
    	
    	$("#value_3").text($("#old_jkxmbhT").val());
    	$("#qlxh").val($("#old_jkzT").val());
    	
    	var zwmc3=$("#value_3").html();
    	$("#value_6").text(transferEnglish(zwmc3));
    	
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    }else if(num==2){
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
		$("#th_7").css("display", "none");
		$("#th_5").css("display", "none");
		
    	$("#div_1").attr("colspan", 2);
    	if($("#old_jkxmbhF").val()==null||$("#old_jkxmbhF").val()==""){
    		$("#value_1").text($("#old_jkxmbhS").val());
    		$("#wcrq").val($("#old_jkzS").val());
    		
    		var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    		
    		$("#value_2").text($("#old_jkxmbhT").val());
    		$("#jssj").val($("#old_jkzT").val());
    		
    		var zwmc2=$("#value_2").html();
        	$("#value_5").text(transferEnglish(zwmc2));
    		
    	}else if($("#old_jkxmbhT").val()==null||$("#old_jkxmbhT").val()==""){
    		$("#value_1").text($("#old_jkxmbhF").val());
    		$("#wcrq").val($("#old_jkzF").val());
    		
    		var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
        	
    		$("#value_2").text($("#old_jkxmbhS").val());
    		$("#jssj").val($("#old_jkzS").val());
    		
    		var zwmc2=$("#value_2").html();
        	$("#value_5").text(transferEnglish(zwmc2));
        	
    	}else{
    		$("#value_1").text($("#old_jkxmbhF").val());
    		$("#wcrq").val($("#old_jkzF").val());
    		$("#value_2").text($("#old_jkxmbhT").val());
    		$("#jssj").val($("#old_jkzT").val());
    	}
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    }else{
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
		$("#th_7").css("display", "none");
		$("#th_5").css("display", "none");
		$("#th_8").css("display", "none");
		$("#th_6").css("display", "none");
		
    	$("#div_1").attr("colspan", 1);
    	
    	if($("#old_jkxmbhF").val()!=null&&$("#old_jkxmbhF").val()!=""){
    		$("#value_1").text($("#old_jkxmbhF").val());
        	$("#wcrq").val($("#old_jkzF").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}else if($("#old_jkxmbhT").val()!=null&&$("#old_jkxmbhT").val()!=""){
    		$("#value_1").text($("#old_jkxmbhT").val());
        	$("#wcrq").val($("#old_jkzT").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}else{
    		$("#value_1").text($("#old_jkxmbhS").val());
        	$("#wcrq").val($("#old_jkzS").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    }
}
function transferEnglish(zwmc){
	var ywmc1="";
	switch(zwmc){
		case "日历":
    	    ywmc1="Calendar";
    		break;
		case "机身飞行时间":
    	    ywmc1="Flight Time";
    		break;
		case "搜索灯时间":
    	    ywmc1="Search Light Time";
    		break;
		case "起落循环":
    	    ywmc1="Landing Gear Cycle";
    		break;
		case "绞车循环":
    	    ywmc1="Winch Cycle";
    		break;
		case "外吊挂循环":
			ywmc1="Ext Suspension Loop";
			break;
		case "搜索灯循环":
			ywmc1="Search Light Cycle";
			break;
		case "特殊监控1":
			ywmc1="TS1";
			break;
		case "特殊监控2":
			ywmc1="TS2";
			break;
		case "N1":
			ywmc1="N1";
			break;
		case "N2":
			ywmc1="N2";
			break;
		case "N3":
			ywmc1="N3";
			break;
		case "N4":
			ywmc1="N4";
			break;
		case "N5":
			ywmc1="N5";
			break;
		case "N6":
			ywmc1="N6";
			break;
	}
	return ywmc1;
}
function sumTotal(){
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	$("#bzgs").html(jhgsRs*jhgsXss);
}

/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00603);
}
//审核通过
function agreedAudit(){
	AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback: function(){
		var zt = 2;
		validateUpdate(zt);
	}});
}	
//审核驳回
function rejectedAudit(){
	var shyj=$("#shyj").val();
	if(shyj==null||shyj==""){
		AlertUtil.showErrorMessage("审核驳回意见不能为空！");
		return false;
	}else{
		AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback: function(){
			var zt=5;
			validateUpdate(zt);
		}});
	}
}

	
function validateUpdate(zt){	
	var shyj = $.trim($("#shyj").val());
	var id = $.trim($("#id").val());
	var gddlx= $.trim($("#gddlx").val());
	var obj = {
			id : id,
			shyj : shyj,
			zt:zt,
			gddlx:gddlx
		};
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/Audit",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(data>0){
				finishWait();
				if(zt==2){
					AlertUtil.showMessage('审核通过成功!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
				}else{
					AlertUtil.showMessage('审核驳回成功!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
				}
			}else{
				finishWait();
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
			}
		},
	});
}

//查看替代部件详情
function viewTdkc(this_){
	var bjh = $(this_).attr("bjh");
	Work_Material_View_Modal.show({
		bjh : bjh,//
		dprtcode : $.trim( $('#zzjgid').val())
	});
}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/workorder/main?pageParam="+pageParam;
}
