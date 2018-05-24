
var monitorHtml = '';

$(function() {
	
	initMonitor();
});

function initMonitor(){

	AjaxUtil.ajax({
		url:basePath+"/component/monitorclass/queryMonitorList",
		type:"post",
		async: false,
		data:{},
		dataType:"json",
		success:function(data){
			initData(data);
		}
	});
}

function initData(data){
	monitorHtml += '<div class="monitor_css">';
	for(var i = 0 ; i < data.length ; i++){
		initMonitorClass(data[i]);
	}
	monitorHtml += '</div>';
	$("#monitor").html(monitorHtml);
}

function initMonitorClass(monitorClass){
	monitorHtml += '<div>';
	monitorHtml += '<label class="text-right" style="width:80px;">';
	monitorHtml += monitorClass.ms+"：";
	monitorHtml += "</label>";
	var itemList = monitorClass.itemList;
	for(var i = 0 ; i < itemList.length ; i++){
		initMonitorItem(monitorClass.jkflbh,itemList[i]);
	}
	monitorHtml += '</div>';
}

function initMonitorItem(jkflbh,monitorItem){
	monitorHtml += '<span name="monitor" class="checkbox-inline" style="margin-right:5px;" onclick=clickrow(this)>';
	monitorHtml += '<input class="monitorItem" type="checkbox" monitorClass="'+jkflbh+'" name="'+monitorItem.jklbh+'" id="'+monitorItem.jklbh+'" onclick=checkRow(this) />';
	monitorHtml += monitorItem.ms;
	monitorHtml += '</span>';
}

function returnView(data){

	for(var i = 0 ; i < data.length ; i++){
		var jklbh = data[i].jklbh;
		var zqz = data[i].zqz;
		var dw = data[i].dw;
		
		$('#'+jklbh).attr('checked','true');
		$("#"+jklbh+"_dlg").css("display","block");
		$("#"+jklbh+"_dlg input[name='zqz']").val(zqz);
		$("#"+jklbh+"_dlg select[name='dw']").val(dw);

	}
	if(3 == data.length){
		setCheckDisabled();
	}
}

function setDisabled(){
	$(".monitorItem").attr("disabled","true");
	$("input[name='zqz']").attr("disabled","true");
	$("select[name='dw']").attr("disabled","true");
	$("span[name='monitor']").each(function(){
		$(this).removeAttr("onclick");
	});
}
function setCheckDisabled(){
	var num = getSelectMonitorNum();
	if(num == 3){
		$('input:checkbox[class=monitorItem]').attr("disabled","true");
		var monitorItems = getSelectMonitorItems();
		for(var i = 0 ; i < monitorItems.length ; i++){
			$(monitorItems[i]).removeAttr("disabled");
		}
	}
}

function clearCheck(){
	var monitorItems = getSelectMonitorItems();
	for(var i = 0 ; i < monitorItems.length ; i++){
		var id = $(monitorItems[i]).attr('id');
		$("#"+id+"_dlg").css("display","none");
		$(monitorItems[i]).removeAttr('checked');
	}
}

function clickrow(this_){
	var id = $(this_).find(".monitorItem").attr("name");
	var checked = $(this_).find(".monitorItem").is(':checked');
	$(this_).find(".monitorItem").attr("checked", !checked);
	showOrHideMonitor(id);
}

function checkRow(this_){
	clickrow($(this_).parent());
}

function showOrHideMonitor(id){
	if($('#'+id).is(':checked')){
		var num = getSelectMonitorNum();
		if(num < 4){
			$("#"+id+"_dlg").css("display","block");
			setCheckDisabled();
		}else{
			AlertUtil.showErrorMessage("最多只能选择3个!");
			$('#'+id).removeAttr('checked');
		}
	}
	else{
		$("#"+id+"_dlg").css("display","none");
		$('input:checkbox[class=monitorItem]').removeAttr("disabled");
	}
}

function getSelectMonitorNum(){
	var monitorItems =  getSelectMonitorItems();
	return monitorItems.length;
}

function getSelectMonitorItems(){
	var monitorItems =  $('input:checkbox[class=monitorItem]:checked');
	return monitorItems;
}

function getMonitorItemParam(){
	
	var monitorItemList = [];
	
	var monitorItems = getSelectMonitorItems();
	
	for(var i = 0 ; i < monitorItems.length ; i++){
		obj = {};
		var jklbh = $(monitorItems[i]).attr('id');
		var jkflbh = $(monitorItems[i]).attr('monitorClass');
		var zqz = $("#"+jklbh+"_dlg input[name='zqz']").val();
		var dw = $("#"+jklbh+"_dlg select[name='dw']").val();
		
		var regu = /^[1-9]+[0-9]*$/;
		if (!regu.test(zqz)) {
			var message = "周期值只能输入数字,必须大于0!";
	        AlertUtil.showMessageCallBack({
				message : message,
				option : 'zqz',
				callback : function(option){
					$("#"+jklbh+"_dlg input[name='zqz']").focus();
				}
			});
			return false;
	    }
		
		obj.jklbh = jklbh;
		obj.jkflbh = jkflbh;
		obj.zqz = zqz;
		obj.dw = dw;

		monitorItemList.push(obj);
	}
	return monitorItemList;
}

//只能输入数字
function clearNoNumber(obj){
     //先把非数字的都替换掉，除了数字
     obj.value = obj.value.replace(/[^\d]/g,"");
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		obj.value = 0;
  	 }
}

//只能输入数字、小数、冒号,保留两位
function clearNoNumColonTwo(obj){
	
	//先把非数字的都替换掉，除了数字
    obj.value = obj.value.replace(/[^\d.:]/g,"");
	
	if(obj.value.indexOf(".") != -1){
		if(obj.value.indexOf(":") != -1){
			obj.value = obj.value.substring(0,obj.value.length -1);
		}else{
			clearNoNumTwoDate(obj);
		}
	}
	if(obj.value.indexOf(":") != -1){
		if(obj.value.indexOf(".") != -1){
			obj.value = obj.value.substring(0,obj.value.length -1);
		}else{
			clearNoNumTwoColon(obj);
		}
	}
	
	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "." && obj.value.substring(1,2) != ":"){
  			obj.value = 0;
  		 }
  	 }
}
//只能输入数字和小数,保留两位,小数后不能超过59
function clearNoNumTwoDate(obj){
	 //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g,"");
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g,"");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g,".");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    
    var str = obj.value.split(".");
    if(str.length > 1){
   	 var value = obj.value;
   	 if(str[1].length == 2){
   		 if(str[1] > 59){
       		 value = str[0] +".59";
       	 }
   	 }
   	 if(str[1].length > 2){
   		 value = str[0] +"." + str[1].substring(0,2);
   	 }
   	 obj.value = value;
    }
}

//只能输入数字和冒号,保留两位,冒号后不能超过59
function clearNoNumTwoColon(obj){
	 //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d:]/g,"");
    //必须保证第一个为数字而不是:
    obj.value = obj.value.replace(/^\:/g,"");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\:{2,}/g,":");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(":","$#$").replace(/\:/g,"").replace("$#$",":");
    
    var str = obj.value.split(":");
    if(str.length > 1){
   	 var value = obj.value;
   	 if(str[1].length == 2){
   		 if(str[1] > 59){
       		 value = str[0] +":59";
       	 }
   	 }
   	 if(str[1].length > 2){
   		 value = str[0] +":" + str[1].substring(0,2);
   	 }
   	 obj.value = value;
    }
}

//只能输入数字和小数,保留两位
function clearNoNumTwo(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
}