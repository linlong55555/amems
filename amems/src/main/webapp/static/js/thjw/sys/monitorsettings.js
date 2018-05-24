$(document).ready(function() {
	var zzjgid=$('#zzjgid').val();
	$("#dprtcode").val(zzjgid);
	Navigation(menuCode);
	refreshPermission();
	//获取当前组织机构的监控设置	
	gopage();
});

function gopage(){
	var dprtcode=$("#dprtcode").val();
	AjaxUtil.ajax({
		url:basePath+"/sys/monitorsettings/monitorsettingsList",
		type: "post",
		dataType:"json",
		data:{
			'dprtcode':dprtcode 
		},
		success:function(data){
			monitorsettingsFill(data.rows);
		}
	});
	
	//加载适航性资料子类型
	loadShxSubType();
}

//填充
function monitorsettingsFill(list){
	
	$.each(list,function(index,mts){
		if(mts.key=="JSWJ"){
			//适航性资料
			$("#JSWJfirst").val(mts.yjtsJb1);
			$("#JSWJfirstcopy").html(mts.yjtsJb1);
			$("#JSWJsecond").val(mts.yjtsJb2);
		}else if(mts.key=="TGZL"){
			//技术通告
			$("#TGZLfirst").val(mts.yjtsJb1);
			$("#TGZLfirstcopy").html(mts.yjtsJb1);
			$("#TGZLsecond").val(mts.yjtsJb2);
		}else if(mts.key=="GZBLD"){
			//保留控制
			$("#GZBLDfirst").val(mts.yjtsJb1);
			$("#GZBLDfirstcopy").html(mts.yjtsJb1);
			$("#GZBLDsecond").val(mts.yjtsJb2);
		}else if(mts.key=="HCSM"){
			//货架寿命
			$("#HCSMfirst").val(mts.yjtsJb1);
			$("#HCSMfirstcopy").html(mts.yjtsJb1);
			$("#HCSMsecond").val(mts.yjtsJb2);
		}else if(mts.key=="DTZZ"){
			//飞机三证
			$("#DTZZfirst").val(mts.yjtsJb1);
			$("#DTZZfirstcopy").html(mts.yjtsJb1);
			$("#DTZZsecond").val(mts.yjtsJb2);
		}else if(mts.key=="SQZZ"){
			//维修执照
			$("#SQZZfirst").val(mts.yjtsJb1);
			$("#SQZZfirstcopy").html(mts.yjtsJb1);
			$("#SQZZsecond").val(mts.yjtsJb2);
		}else if(mts.key=="JXDQJK"){
			//机型执照监控
			$("#JXDQJKfirst").val(mts.yjtsJb1);
			$("#JXDQJKfirstcopy").html(mts.yjtsJb1);
			$("#JXDQJKsecond").val(mts.yjtsJb2);
		}else if(mts.key=="TDYQ"){
			//航材提订要求
			$("#TDYQfirst").val(mts.yjtsJb1);
			$("#TDYQfirstcopy").html(mts.yjtsJb1);
			$("#TDYQsecond").val(mts.yjtsJb2);
		}else if(mts.key=="GJJK"){
			//工具/设备监控
			$("#GJJKfirst").val(mts.yjtsJb1);
			$("#GJJKfirstcopy").html(mts.yjtsJb1);
			$("#GJJKsecond").val(mts.yjtsJb2);
		}else if(mts.key=="GYSYXQ"){
			//供应商授权有效期
			$("#GYSYXQfirst").val(mts.yjtsJb1);
			$("#GYSYXQfirstcopy").html(mts.yjtsJb1);
			$("#GYSYXQsecond").val(mts.yjtsJb2);
		}else if(mts.key=="ZWZLSCQX"){
			//自我质量审查期限
			$("#ZWZLSCQXfirst").val(mts.yjtsJb1);
			$("#ZWZLSCQXfirstcopy").html(mts.yjtsJb1);
			$("#ZWZLSCQXsecond").val(mts.yjtsJb2);
		}else if(mts.key=="RYKCPX"){
			//课程培训监控
			$("#RYKCPXfirst").val(mts.yjtsJb1);
			$("#RYKCPXfirstcopy").html(mts.yjtsJb1);
			$("#RYKCPXsecond").val(mts.yjtsJb2);
		}else if(mts.key=="GWDQJK"){
			//岗位到期监控
			$("#GWDQJKfirst").val(mts.yjtsJb1);
			$("#GWDQJKfirstcopy").html(mts.yjtsJb1);
			$("#GWDQJKsecond").val(mts.yjtsJb2);
		}else if(mts.key=="PXTX"){
			//培训提醒设置
			$("#pxts").val(mts.yjtsJb2);
			$("#txlx").val(mts.yjtsJb3);
			if(mts.yjtsJb1==1){
				 $("#txsz").prop("checked", true);
				$("#pxts").attr("readonly", false);
			}else{
				$("#pxts").attr("readonly", true);
				 $("#txsz").prop("checked", false);
				 $("#pxts").val("");
			}
		}else if(mts.key=="ZLWTFK"){
			//质量问题反馈
			$("#ZLWTFKfirst").val(mts.yjtsJb1);
			$("#ZLWTFKfirstcopy").html(mts.yjtsJb1);
			$("#ZLWTFKsecond").val(mts.yjtsJb2);
		}
				
	});
}
//封装对象
function encapsulationDetai(){
	var obj={};
	var monitorsettingsList=[];
	var fol= false;
	var message="";
	var monitorsettingsKey=["JSWJ","TGZL","GZBLD","HCSM","DTZZ","SQZZ","TDYQ","GJJK","PXTX","GYSYXQ","ZWZLSCQX","RYKCPX","GWDQJK","JXDQJK","ZLWTFK"];
	
	for(var i=0;i<monitorsettingsKey.length;i++){
		var monitorsettings={};
		if("PXTX"==monitorsettingsKey[i]){
			var b=$("input[type='checkbox']").is(':checked');
			if(b){
				monitorsettings.yjtsJb1=1;
			}else{
				monitorsettings.yjtsJb1=0;
			}
			monitorsettings.yjtsJb2=$("#pxts").val();
			monitorsettings.yjtsJb3=$("#txlx").val();
			monitorsettings.key=monitorsettingsKey[i];
			monitorsettings.dprtcode=$("#dprtcode").val();
			monitorsettingsList.push(monitorsettings);
			if(monitorsettings.yjtsJb1==1 || monitorsettings.yjtsJb3==""){
				if(monitorsettings.yjtsJb2==""){
					fol =true;
					message="提醒设置,监控项不合法,请再次填写";
					obj.fol=fol;
					obj.message=message;
					return obj;
				}
			}
		} else {
			var first='#'+monitorsettingsKey[i]+'first';
			var second='#'+monitorsettingsKey[i]+'second';
			monitorsettings.yjtsJb1=$(first).val();
			monitorsettings.yjtsJb2=$(second).val();
			if(monitorsettings.yjtsJb1=="" || monitorsettings.yjtsJb2==""){
				fol =true;
				message = $("#"+monitorsettingsKey[i]+"Div").html() + ",监控项不合法,请再次填写";
				obj.fol=fol;
				obj.message=message;
				return obj;
			}
			
			monitorsettings.key=monitorsettingsKey[i];
			monitorsettings.dprtcode=$("#dprtcode").val();
			monitorsettingsList.push(monitorsettings);
		}
		
	}
	obj.monitorsettingsList=monitorsettingsList;
	obj.fol=fol;
	obj.message=message;
	return obj;
}

//保存修改操作
function update(){
	var monitorsettings={};
	
	var obj=encapsulationDetai();
	monitorsettings.monitorsettingsList=obj.monitorsettingsList;
	if(obj.fol){
		AlertUtil.showErrorMessage(obj.message);
		return false;
	}
	
	var subObj = validateSubType();
	monitorsettings.subTypeList = subObj.subTypeList;
	if(subObj.fol){
		AlertUtil.showErrorMessage(subObj.message);
		return false;
	}
	
	monitorsettings.dprtcode=$("#dprtcode").val();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/sys/monitorsettings/updateMonitorsettings",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(monitorsettings),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showErrorMessage("保存成功");
		}
	});
	
}


$(function() {
	$("#JSWJfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#JSWJfirst").val("");
			$("#JSWJfirstcopy").html("");
		} else {
			$("#JSWJfirstcopy").html($("#JSWJfirst").val());
			if(parseInt($("#JSWJsecond").val())<parseInt($("#JSWJfirst").val())){
				$("#JSWJsecond").val("");
			}
		}
	});
	$("#JSWJsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#JSWJfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#TGZLfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#TGZLfirst").val("");
			$("#TGZLfirstcopy").html("");
		} else {
			$("#TGZLfirstcopy").html($("#TGZLfirst").val());
			if(parseInt($("#TGZLsecond").val())<parseInt($("#TGZLfirst").val())){
				$("#TGZLsecond").val("");
			}
		}
	});
	$("#TGZLsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#TGZLfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#GZBLDfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#GZBLDfirst").val("");
			$("#GZBLDfirstcopy").html("");
		} else {
			$("#GZBLDfirstcopy").html($("#GZBLDfirst").val());
			if(parseInt($("#GZBLDsecond").val())<parseInt($("#GZBLDfirst").val())){
				$("#GZBLDsecond").val("");
			}
		}
	});
	$("#GZBLDsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#GZBLDfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#HCSMfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#HCSMfirst").val("");
			$("#HCSMfirstcopy").html("");
		} else {
			$("#HCSMfirstcopy").html($("#HCSMfirst").val());
			if(parseInt($("#HCSMsecond").val())<parseInt($("#HCSMfirst").val())){
				$("#HCSMsecond").val("");
			}
		}
	});
	$("#HCSMsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#HCSMfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#SQZZfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#SQZZfirst").val("");
			$("#SQZZfirstcopy").html("");
		} else {
			$("#SQZZfirstcopy").html($("#SQZZfirst").val());
			if(parseInt($("#SQZZsecond").val())<parseInt($("#SQZZfirst").val())){
				$("#SQZZsecond").val("");
			}
		}
	});
	$("#SQZZsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#SQZZfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#JXDQJKfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#JXDQJKfirst").val("");
			$("#JXDQJKfirstcopy").html("");
		} else {
			$("#JXDQJKfirstcopy").html($("#JXDQJKfirst").val());
			if(parseInt($("#JXDQJKsecond").val())<parseInt($("#JXDQJKfirst").val())){
				$("#JXDQJKsecond").val("");
			}
		}
	});
	$("#JXDQJKsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#JXDQJKfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#DTZZfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#DTZZfirst").val("");
			$("#DTZZfirstcopy").html("");
		} else {
			$("#DTZZfirstcopy").html($("#DTZZfirst").val());
			if(parseInt($("#DTZZsecond").val())<parseInt($("#DTZZfirst").val())){
				$("#DTZZsecond").val("");
			}
		}
	});
	$("#DTZZsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#DTZZfirst").val())) {
					$(this).val("");
				}
			}
		}
	});

	$("#TDYQfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#TDYQfirst").val("");
			$("#TDYQfirstcopy").html("");
		} else {
			$("#TDYQfirstcopy").html($("#TDYQfirst").val());
			if(parseInt($("#TDYQsecond").val())<parseInt($("#TDYQfirst").val())){
				$("#TDYQsecond").val("");
			}
		}
	});
	$("#TDYQsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#TDYQfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#GJJKfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#GJJKfirst").val("");
			$("#GJJKfirstcopy").html("");
		} else {
			$("#GJJKfirstcopy").html($("#GJJKfirst").val());
			if(parseInt($("#GJJKsecond").val())<parseInt($("#GJJKfirst").val())){
				$("#GJJKsecond").val("");
			}
		}
	});
	$("#GYSYXQfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#GYSYXQfirst").val("");
			$("#GYSYXQfirstcopy").html("");
		} else {
			$("#GYSYXQfirstcopy").html($("#GYSYXQfirst").val());
			if(parseInt($("#GYSYXQsecond").val())<parseInt($("#GYSYXQfirst").val())){
				$("#GYSYXQsecond").val("");
			}
		}
	});
	$("#ZWZLSCQXfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#ZWZLSCQXfirst").val("");
			$("#ZWZLSCQXfirstcopy").html("");
		} else {
			$("#ZWZLSCQXfirstcopy").html($("#ZWZLSCQXfirst").val());
			if(parseInt($("#ZWZLSCQXsecond").val())<parseInt($("#ZWZLSCQXfirst").val())){
				$("#ZWZLSCQXsecond").val("");
			}
		}
	});
	$("#ZWZLSCQXsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#ZWZLSCQXfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#RYKCPXfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#RYKCPXfirst").val("");
			$("#RYKCPXfirstcopy").html("");
		} else {
			$("#RYKCPXfirstcopy").html($("#RYKCPXfirst").val());
			if(parseInt($("#RYKCPXsecond").val())<parseInt($("#RYKCPXfirst").val())){
				$("#RYKCPXsecond").val("");
			}
		}
	});
	$("#RYKCPXsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#RYKCPXfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#GWDQJKfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#GWDQJKfirst").val("");
			$("#GWDQJKfirstcopy").html("");
		} else {
			$("#GWDQJKfirstcopy").html($("#GWDQJKfirst").val());
			if(parseInt($("#GWDQJKsecond").val())<parseInt($("#GWDQJKfirst").val())){
				$("#GWDQJKQsecond").val("");
			}
		}
	});
	$("#GWDQJKsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#GWDQJKfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	
    $("#ZLWTFKfirst").blur(function() {
		if (regvalue($(this).val()) == false) {
			$("#ZLWTFKfirst").val("");
			$("#ZLWTFKfirstcopy").html("");
		} else {
			$("#ZLWTFKfirstcopy").html($("#ZLWTFKfirst").val());
			if(parseInt($("#ZLWTFKsecond").val())<parseInt($("#ZLWTFKfirst").val())){
				$("#ZLWTFKsecond").val("");
			}
		}
	});
	$("#ZLWTFKsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#ZLWTFKfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
		
	$("#GJJKsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#GJJKfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#GYSYXQsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#GYSYXQfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	$("#ZWZLSCQXsecond").blur(function() {
		var second = $(this).val();
		if (regvalue(second) == false) {
			$(this).val("");
		} else {
			if (second != "") {
				if (parseInt(second) < parseInt($("#ZWZLSCQXfirst").val())) {
					$(this).val("");
				}
			}
		}
	});
	
	$("#txsz").click(function() {
		if ($("#txsz").prop("checked") == false) {
			$("#pxts").val("");
			$("#pxts").attr("readonly", true);
		} else { 
			$("#pxts").attr("readonly", false);
		}
	});
	
	$("#pxts").blur(function() {
		if (regvalue($(this).val()) == false) {
			$(this).val("");
		}
	});
});

$("#oil").blur(function() {
	if (regoil($(this).val()) == false) {
		$(this).val("");
	}
});

function regvalue(obj) {
	var reg = /^(0|[1-9][0-9]*)$/;
	return reg.test(obj);
}
function regoil(obj) {
	var r = /^([1-9]\d*|0)(\.\d*[1-9])?$/;
	return r.test(obj);
}

/**收缩隐藏,适航性资料**/
function showHideSubType() {
	if ($("#divSubType").css("display") == "none") {
		$("#divSubType").css("display", "block");
		$("#moreImge").attr('src',basePath+'/static/images/up.png'); 
	} else {
		$("#divSubType").css("display", "none");
		$("#moreImge").attr('src',basePath+'/static/images/down.png'); 
	}
}

/**加载适航性资料子类型**/
function loadShxSubType() {
	//选中的组织机构
	var dprtcode = $("#dprtcode").val();
	
	//子类型键值: 有值:展开   无值:收拢
	var keyMap = getKeyMap(dprtcode); 
	
	//创建子类型Html
	createSubTypeHtml(keyMap);
	
	//注册事件
	bindBlurEvent();
}

/**获取子类型键值,有值:展开   无值:收拢**/
function getKeyMap(dprtcode){
	var keyMap = new ErayMap();  
	AjaxUtil.ajax({
		url:basePath+"/sys/monitorsettings/getShxzlSubTypeList",
		type: "post",
		dataType:"json",
		async : false, //设置成功同步，success中才能访问到keyMap
		data:{
			'dprtcode':dprtcode 
		},
		success:function(data){
			if(data != null && data.subTypes != null && data.subTypes.length > 0){
				$.each(data.subTypes,function(index,subObj){
					keyMap.put(StringUtil.escapeStr(subObj.key),subObj); 
				});
				$("#divSubType").css("display", "block");
				$("#moreImge").attr('src',basePath+'/static/images/up.png'); 
			}else{
				$("#divSubType").css("display", "none");
				$("#moreImge").attr('src',basePath+'/static/images/down.png');
			}
		}
	});
	
	return keyMap;
}

/**创建子类型Html**/
function createSubTypeHtml(keyMap){
	//数据字典子类型
	var subTypes = DicAndEnumUtil.getDicItems('16',$("#dprtcode").val());
	var len = subTypes.length;
	var subTypeHtml = "";
	for(i=0;i<len;i++){
		//获取key对应的值
		var valObj = keyMap.get("JSWJ_"+StringUtil.escapeStr(subTypes[i].id));
		if (undefined == valObj) {
			valObj = {};
		}
		
		if(i==0){
			subTypeHtml += "<div class='col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-35 padding-right-0'>";
		}else{
			subTypeHtml += "<div class='clearfix'></div>";
			subTypeHtml += "<div class='col-lg-4 col-sm-4 col-xs-4 margin-top-10 padding-left-35 padding-right-0'>";
		}
		subTypeHtml += "	<label class='pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height'>";
		subTypeHtml += "    	<div class='font-size-12'>"+StringUtil.escapeStr(subTypes[i].name)+"</div>";
		subTypeHtml += "    </label>&nbsp;&nbsp;&nbsp;&nbsp;";
		subTypeHtml += "    <span>剩余评估期限&le;</span>";
		subTypeHtml += "    <span>";
		subTypeHtml += "    	<input type='text' style='width: 30px' value='"+StringUtil.escapeStr(valObj.yjtsJb1)+"' id='JSWJ_"+StringUtil.escapeStr(subTypes[i].id)+"_first' name='"+StringUtil.escapeStr(subTypes[i].name)+"'>";
		subTypeHtml += "    </span>";
		subTypeHtml += "    <span>天</span>";
		subTypeHtml += "    <span>"; 
		subTypeHtml += "    	<input class='ys_club' disabled='disabled';type='text'>";
		subTypeHtml += "    </span> ";
		subTypeHtml += "    <span>标注</span> ";
		subTypeHtml += " </div> ";
		if(i==0){
			subTypeHtml += " <div class='col-lg-8 col-sm-8 col-xs-8 margin-top-0 padding-left-0 padding-right-0'>";
		}else{
			subTypeHtml += " <div class='col-lg-8 col-sm-8 col-xs-8 margin-top-10 padding-left-0 padding-right-0'>";
		}
		
		subTypeHtml += " 	<span id='first'></span> ";
		subTypeHtml += " 	<span id='JSWJ_"+StringUtil.escapeStr(subTypes[i].id)+"_firstCopy' >"+StringUtil.escapeStr(valObj.yjtsJb1)+"</span>";
		subTypeHtml += "    <span>天&lt;剩余评估期限&le;</span>";
		subTypeHtml += " 	<input  value='"+StringUtil.escapeStr(valObj.yjtsJb2)+"' id='JSWJ_"+StringUtil.escapeStr(subTypes[i].id)+"_second'  name='"+StringUtil.escapeStr(subTypes[i].name)+"' type='text' style='width: 25px'> ";
		subTypeHtml += "    <span>天</span>";
		subTypeHtml += " 	<span>";
		subTypeHtml += " 		<input type='text' class='ye_club' disabled='disabled'>";
		subTypeHtml += " 	</span> ";
		subTypeHtml += " 	<span>标注</span>";
		subTypeHtml += " </div>";
	}
	$("#divSubType").html(subTypeHtml);
}

/**注册事件**/
function bindBlurEvent(){
	//数据字典子类型
	var subTypes = DicAndEnumUtil.getDicItems('16',$("#dprtcode").val());
	var len = subTypes.length;
	var subTypeHtml = "";
	for(i=0;i<len;i++){
		var firstTempId = "JSWJ_"+StringUtil.escapeStr(subTypes[i].id)+"_first";
		
		$("#"+firstTempId).blur(function() {
			if (regvalue($(this).val()) == false) {
				var firstId = $(this).attr('id');
				var firstCopyId = $(this).attr('id').replace("_first","_firstCopy");
				
				$("#"+firstId).val("");
				$("#"+firstCopyId).html("");
			} else {
				var firstId = $(this).attr('id');
				var firstCopyId = $(this).attr('id').replace("_first","_firstCopy");
				var secondId =  $(this).attr('id').replace("_first","_second");
				
				$("#"+firstCopyId).html($("#"+firstId).val());
				if(parseInt($("#"+secondId).val())<parseInt($("#"+firstId).val())){
					$("#"+secondId).val("");
				}
			}
		});
	}
}

//校验子类型，并封装对象
function validateSubType(){
	var obj={};
	
	var subTypeList=[];
	var fol= false;
	var message="";
	
	
	var subTypes = DicAndEnumUtil.getDicItems('16',$("#dprtcode").val());
	var len = subTypes.length;
	for(i=0;i<len;i++){
		var subType ={};
		
		var key = "JSWJ_"+StringUtil.escapeStr(subTypes[i].id);
		var firstVal = StringUtil.escapeStr($("#"+key+"_first").val());
		var secondVal = StringUtil.escapeStr($("#"+key+"_second").val());
		
		if((firstVal == "" && secondVal != "") || (parseInt(secondVal)<parseInt(firstVal))){
			fol =true;
			message= StringUtil.escapeStr(subTypes[i].id) + ",监控项不合法,请再次填写";
			obj.fol=fol;
			obj.message=message;
			return obj;
		}
		
		subType.key=key;
		subType.yjtsJb1=firstVal;
		subType.yjtsJb2=secondVal;
		subType.dprtcode=$("#dprtcode").val();
		subTypeList.push(subType);
	}
	
	obj.subTypeList=subTypeList;
	obj.fol=fol;
	obj.message=message;
	
	return obj;
}
