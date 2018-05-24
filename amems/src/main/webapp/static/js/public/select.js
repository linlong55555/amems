
//全选或反选
function selectAll(selectAll,selectNode){
	
	//如果选中全选
	if(isSelectAll(selectAll)){
		$("#"+selectNode).find("tr input[type='checkbox']").each(function(){
			$(this).attr("checked",'true');//点击全选
		});
	}else{
		$("#"+selectNode).find("tr input[type='checkbox']:checked").each(function(){
			$(this).removeAttr("checked");//点击取消全选
		});
	}
}
//单选
function selectNode(selectAll,selectNode){
	
	var checkNum = $("#"+selectNode).find("tr input[type='checkbox']:checked").length;//选中个数
	var totalNum = $("#"+selectNode).find("tr").length;//总数
	
	if(checkNum == totalNum && totalNum != 0){
		checkSelectAll(selectAll);//全选
	}else{
		removeSelectAll(selectAll);//取消全选
	}
}

function isSelectAll(selectAll){
	var flag = $("#"+selectAll).is(":checked");
	return flag;
}

//选中全选
function checkSelectAll(selectAll){
	$("#"+selectAll).attr("checked",'true');//选中全选
}

//取消全选
function removeSelectAll(selectAll){
	$("#"+selectAll).removeAttr("checked");//取消全选
}

//获取选中的值,返回值1,2,3,4...用逗号隔开的字符串
function getSelectValue(selectNode){
	
	var ids = '';
	$("#"+selectNode).find("tr input[type='checkbox']:checked").each(function(){
		ids += $(this).val()+",";
	});
	ids = getstr(ids);
	return ids;
}

//获取原生选中的值,返回值1,2,3,4...用逗号隔开的字符串
function getYsSelectValue(selectNode){
	
	var strs = '';
	$("input[name='"+selectNode+"']:checked").each(function(){
		strs += $(this).val()+",";
	});
	strs = getstr(strs);
	return strs;
}

//去掉字符串中重复的内容1,2,3,4...用逗号隔开的字符串
function uniqueStr(str) {
	var result = str;
	if (str.indexOf(",") != -1) {//首先去掉多张工单中重复的工单号
		var arr = unique(str.split(","));//去重之后的结果
		result = arr.join(",");
	}
	return result;
}

//去掉数组中重复的内容
function unique(arr) {
	var result = [];
	for(var i = 0 ; i < arr.length ; i++){
		var str = $.trim(arr[i]);
		if(str != '' && result.indexOf(str) == -1){
			result.push(str);
		}
	}
	return result;
}

//去掉字符串后面的逗号
function getstr(objStr){
	var str = '';
	if(objStr != '' && objStr.length > 0){
		str = objStr.substring(0,objStr.length-1);
	}
	return str;
}