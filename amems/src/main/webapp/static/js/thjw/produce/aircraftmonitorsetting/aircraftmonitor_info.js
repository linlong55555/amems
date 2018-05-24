/**
 * 监控设置
 */
bottom_hidden_content = {
	id:'bottom_hidden_content',
	/**
	 * 查询监控设置信息
	 */
	setting:function(){
		var this_=this;
		$(".displayContent input[name='classification1']").eq(0).attr("checked","checked");
		classificationType("percentage");
		var obj={};
		obj.fjzch=$("#fjzch").val();
		obj.dprtcode=$("#dprtcode").val();
		AjaxUtil.ajax({
		  url:basePath+"/produce/monitorsetting/queryMonitorSettingByfjzch",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   $("#bottom_hidden_content_input input").val("");//清空数据
			   if(data.length>0){
				   this_.setDate(data);//加载数据
			   }
		   }
		}); 	
	}, 
	/**
	 * 加载监控设置数据
	 */
	setDate:function(list){
		$("input:radio[name='classification1'][value='"+list[0].lx+"']").attr("checked",true); 
		if(list[0].lx == 1){
			classificationType("percentage");
		}else{
			classificationType();
		}
		 $.each(list,function(index,row){
			 if(row.lx == 2){
				 if(StringUtil.escapeStr(row.jklbh)=='2_10_FH'||StringUtil.escapeStr(row.jklbh)=='2_20_AH'||StringUtil.escapeStr(row.jklbh)=='2_30_EH' ){
					 $("#"+StringUtil.escapeStr(row.jklbh)).val(StringUtil.escapeStr( TimeUtil.convertToHour(row.fz, TimeUtil.Separator.COLON)));
				 }else{
					 $("#"+StringUtil.escapeStr(row.jklbh)).val(StringUtil.escapeStr(row.fz));
				 }
			 }else{
				 $("#"+StringUtil.escapeStr(row.jklbh)).val(StringUtil.escapeStr(row.fz));
			 }

		 });
	},
	/**
	 * 保存
	 */
	save:function(){
		var this_=this;
		var paramsMap = {};
		var obj = {};
		obj.fjzch = $.trim($("#fjzch").val()); 		//飞机注册号
		obj.dprtcode =$.trim($("#dprtcode").val()); //组织机构
		var lx  = $("input:radio[name='classification1']:checked").val(); 	
		
		obj.lx=lx;
		var CAL = $.trim($("#1_10").val()); //日历
		var FH = $.trim($("#2_10_FH").val()); //飞行小时
		var EH = $.trim($("#2_30_EH").val()); //发动机小时
		var APUH = $.trim($("#2_20_AH").val()); //APU小时
		var FC = $.trim($("#3_10_FC").val()); //飞行循环
		var EC = $.trim($("#3_30_EC").val()); //发动机循环
		var APUC = $.trim($("#3_20_AC").val()); //APU循环
		var reg = /^[0-9]*$/;
		var reg1 = /^100$|^(\d|[1-9]\d)$/;
		if(lx==2){
			if(reg.test(CAL)==false){
				AlertUtil.showMessage("日历输入不正确，请输入0和正整数!");
				return false;
			}
			if(reg.test(FC)==false){
				AlertUtil.showMessage("飞行循环输入不正确，请输入0和正整数!");
				return false;
			}
			if(reg.test(APUC)==false){
				AlertUtil.showMessage("APU循环输入不正确，请输入0和正整数!");
				return false;
			}
			if(reg.test(EC)==false){
				AlertUtil.showMessage("发动机循环输入不正确，请输入0和正整数!");
				return false;
			}
			if($.trim(CAL)!=null){
				paramsMap.CAL = $.trim(CAL); //日历 
			}
			if($.trim(FH)!=null&&TimeUtil.convertToMinute(FH, TimeUtil.Separator.COLON)!=0){
				paramsMap.FH = $.trim(TimeUtil.convertToMinute(FH, TimeUtil.Separator.COLON)); //飞行小时
			}
			if($.trim(EH)!=null&&TimeUtil.convertToMinute(EH, TimeUtil.Separator.COLON)!=0){
				paramsMap.EH = $.trim(TimeUtil.convertToMinute(EH, TimeUtil.Separator.COLON)); //发动机小时
			}
			if($.trim(APUH)!=null&&TimeUtil.convertToMinute(APUH, TimeUtil.Separator.COLON)!=0){
				paramsMap.APUH = $.trim(TimeUtil.convertToMinute(APUH, TimeUtil.Separator.COLON)); //APU小时
			}
			if($.trim(FC)!=null){
				paramsMap.FC = $.trim(FC); //飞行循环
			}
			if($.trim(EC)!=null){
				paramsMap.EC = $.trim(EC); //发动机循环
			}
			if($.trim(APUC)!=null){
				paramsMap.APUC = $.trim(APUC); //APU循环
			}
		}else{
			if($.trim(CAL)!='' && reg1.test(CAL)==false){
				AlertUtil.showMessage("日历输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(FH)!='' && reg1.test(FH)==false){
				AlertUtil.showMessage("飞行时间输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(FC)!='' && reg1.test(FC)==false){
				AlertUtil.showMessage("飞行循环输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(APUH)!='' && reg1.test(APUH)==false){
				AlertUtil.showMessage("APU时间输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(APUC)!='' && reg1.test(APUC)==false){
				AlertUtil.showMessage("APU循环输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(EH)!='' && reg1.test(EH)==false){
				AlertUtil.showMessage("发动机时间输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(EC)!='' && reg1.test(EC)==false){
				AlertUtil.showMessage("发动机循环输入不正确，请输入0-100的整数!");
				return false;
			}
			if($.trim(CAL)!=null){
				paramsMap.CAL = $.trim(CAL); //日历 
			}
			if($.trim(FH)!=null){
				paramsMap.FH = $.trim(FH); //飞行时间
			}
			if($.trim(FC)!=null){
				paramsMap.FC = $.trim(FC); //飞行循环
			}
			if($.trim(EH)!=null){
				paramsMap.EH = $.trim(EH); //发动机时间
			}
			if($.trim(EC)!=null){
				paramsMap.EC = $.trim(EC); //发动机循环
			}
			if($.trim(APUC)!=null){
				paramsMap.APUC = $.trim(APUC); //APU循环
			}
			if($.trim(APUH)!=null){
				paramsMap.APUH = $.trim(APUH); //APU时间
			}
		}
	
		obj.paramsMap = paramsMap;
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath+"/produce/monitorsetting/save",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();
	 			AlertUtil.showMessage("保存成功!");
	 			$("#Assessment_Open_Modal").modal("hide");
	 			this_.setting();
	 		}
	   	});
	},
	/**
	 *只能输入数字
	 */
	clearNoNum:function(obj){
    	//先把非数字的都替换掉，除了数字
		obj.value = obj.value.replace(/[^\d]/g,"");
        if(obj.value > 8){//长度不能超过8位
        	obj.value =obj.value.substring(0,8);
	     }
	},
	/**
	 * 只能输入数字和冒号
	 */
	onkeyup4Num : function(obj){
		var reg=/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
		var value = $(obj).val();
			value = value.replace(/：/g, ":");
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(validateMax(value));
		function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	/**
	 * 分钟转小时
	 */
	convertToHour : function(jklbh, value){
		if(MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	/**
	 * 小时转分钟
	 */
	convertToMinute : function(jklbh, value){
		if(value != '' && MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToMinute(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
};

var unitName=[];
for(var i=0;i<$("#monitor_group_border span[name='zq_dw']").length;i++){
	unitName.push($("#monitor_group_border span[name='zq_dw']").eq(i).text());
}
function classificationType(type){
	if(type=="percentage"){
		for(var i=0;i<$("span[name='zq_dw']").length;i++){
			$("#monitor_group_border span[name='zq_dw']").eq(i).text("%");
		}
	}else{
		for(var i=0;i<$("span[name='zq_dw']").length;i++){
			$("#monitor_group_border span[name='zq_dw']").eq(i).text(unitName[i]);
		}
	}
}
