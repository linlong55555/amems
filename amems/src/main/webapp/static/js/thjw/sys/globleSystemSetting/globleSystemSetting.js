var pzbmlist=['LOGIN_OUT','SESSION_LOSE','LOGIN_ERROR','ATT_ROOT_PATH','DEFAULT_PASSWORD','MAC_LIMIT','SUPER_AGENCY','PAGING_NUM','AGENCY_TYPE'];
var index=0;
var flag=true;
$(document).ready(function() {
	Navigation(menuCode);
	 if (syscode==null||syscode=='') {
//		 setstyle();
		AjaxGetDatasWithSearch(); 
	 }else{
		 decodePageParam();
	 }
	
});


function decodePageParam(){
	$("#sysCode").val(syscode);
//	setstyle();
	AjaxGetDatasWithSearch();
}

function AjaxGetDatasWithSearch() {
	var searchParam = {};
	searchParam.syscode = $.trim($("#sysCode").val());	
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/sys/settings/getBySyscode",
		type : "post",
		data : JSON.stringify({syscode:$.trim($("#sysCode").val())}),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.length > 0) {
				appendContentHtml(data);
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
			}else{
//				setstyle();
			}
		}
	});

}
function appendContentHtml(list) {
	var jglx=[];
	for(var i=0;i<list.length;i++){
		var id=(list[i].pzbm+"_id");		
		if($.inArray(list[i].pzbm||"", pzbmlist) != -1){			
			if(list[i].pzbm=="AGENCY_TYPE"){
				jglx.push(list[i]);
			}else{
			$("#"+id).show();
			$("#"+list[i].pzbm).val(list[i].value1);
			if(list[i].pzbm=="MAC_LIMIT"){				
				if(list[i].value1=="true"){
					$("#customDiv").html('<input id="MAC_LIMIT"  type="checkbox" checked="checked" data-on-text="ON" data-off-text="OFF" data-on-color="success" data-off-color="warning" />');					
				}else{
					$("#customDiv").html('<input id="MAC_LIMIT"  type="checkbox"     data-on-text="ON"  data-on-color="success" data-off-color="warning" />');
				}
				 $("#MAC_LIMIT").bootstrapSwitch();
			}
		 }
		}
	}
	if(jglx.length>0){
		for(var i=0;i<jglx.length;i++){
			if(i==0){
				$("#"+jglx[i].pzbm).val(jglx[i].value1);
				$("#"+jglx[i].pzbm+"_id").show();
			}else{
				appendJglx(jglx[i]);
				pzbmlist.push("AGENCY_TYPE_"+i+"");
			}
		}
	}
}
//切换系统描述
$("#sysCode").change(function(){
	window.location = basePath+"/sys/settings/main?syscode="+encodeURIComponent($(this).val());
	
})
//隐藏数组中div
function setstyle(){
	for(var i=0;i<pzbmlist.length;i++){
		var id=(pzbmlist[i]+"_id");
		$("#"+id).hide();
	}
}
//保存提交
function save(){
	var syscode=$.trim($("#sysCode").val());
	var param=[];
	for(var i=0;i<pzbmlist.length;i++){
		var obj=getObj(pzbmlist[i]);	
		if(obj.pzbm!=null){
		param.push(obj);
		}
	}
	if(flag){
		AjaxUtil.ajax({
			url : basePath + "/sys/settings/update",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				finishWait();
				AlertUtil.showMessage('保存成功!','/sys/settings/main?syscode='+encodeURIComponent($("#sysCode").val()));
			}
		});
	}else{
		AlertUtil.showErrorMessage("分页数请输入大于0额整数!");
		flag=true;
	}
	
	
	
}
//获取对象值
function getObj(obj){
	var info={};	
	if($("#"+obj+"_id").is(":visible")){
		info.syscode=$.trim($("#sysCode").val());
		info.pzbm=$.trim($("#"+obj+"_PZBM").val());
		if(obj=="MAC_LIMIT"){
			if($("#MAC_LIMIT").is(':checked')){
				info.value1="true";
			}else{
				info.value1="false";
			}
		}else{
			var value=$.trim($("#"+obj).val());
			if(obj=="PAGING_NUM"){
				validation(value);
			}			
			info.value1=value;
		}
	}
	return info;
}

//验证
function validation(obj){
	var reg=/^[1-9]\d*$/;
	if(reg.test(obj)==false){
		flag=false;
	}
}
//从数据库取机构类型数量大于1时候拼接页面
function appendJglx(obj){
	index+=1;
var html="";
	html+='<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="'+"AGENCY_TYPE_"+index+"_id"+'" >'+
	'<div class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">' +
	'<label class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">'+
	'<div class="font-size-12">机构类型</div></label>'+
	'</div>'+	
	'<div  	class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">'+
	'<input type="hidden" value="AGENCY_TYPE" id="'+"AGENCY_TYPE_"+index+"_PZBM"+'"/>'+
	'<input type="text"	id="'+"AGENCY_TYPE_"+index+'" value="'+formatUndefine(obj.value1)+'" name="AGENCY_TYPE" class="form-control " maxlength="160" /></div>'+		
	'<div class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">'+
	'<button class="line6 " onclick="removeJglx(this)">'+
	'<i class="icon-minus cursor-pointer color-blue cursor-pointer" ></i></button></div></div>'
	var len=pzbmlist.length;
	$("#"+pzbmlist[len-1]+"_id").after(html);
}
//移除机构类型
function removeJglx(obj){
	var id = $(obj).parent().parent().attr("id").replace("_id","");		
		AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){			
			remove(id);
			$(obj).parent().parent().remove();
		}
		});
}
//移除机构类型时移除pzbmlist数组中对应的值
function remove(id){
	var list=[];
	for(var i=0;i<pzbmlist.length;i++){
		var obj=pzbmlist[i];	
		if(id!=obj){
			list.push(obj);
		}
	}
	pzbmlist=list;
}

//点击添加按钮时拼接页面
function addJglx(){
	index+=1;
	var html="";
		html+='<div class="col-lg-12 col-sm-12 col-xs-12  padding-right-0 margin-top-10" id="'+"AGENCY_TYPE_"+index+"_id"+'" >'+
		'<div class="col-lg-2 col-sm-2 col-xs-2 margin-top-0 padding-left-0 padding-right-0">' +
		'<label class="pull-left col-xs-0 col-sm-0 text-right padding-left-0 padding-right-0 row-height">'+
		'<div class="font-size-12">机构类型</div></label>'+
		'</div>'+	
		'<div  	class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">'+
		'<input type="hidden" value="AGENCY_TYPE" id="'+"AGENCY_TYPE_"+index+"_PZBM"+'"/>'+
		'<input type="text"	id="'+"AGENCY_TYPE_"+index+'"  value="" name="AGENCY_TYPE" class="form-control " maxlength="160" /></div>'+		
		'<div class="col-lg-4 col-sm-4 col-xs-4 margin-top-0 padding-left-0 padding-right-0">'+
		'<button class="line6 " onclick="removeJglx(this)">'+
		'<i class="icon-minus cursor-pointer color-blue cursor-pointer" ></i></button></div></div>'		
		var len=pzbmlist.length;
		$("#"+pzbmlist[len-1]+"_id").after(html);
		pzbmlist.push("AGENCY_TYPE_"+index);
	}