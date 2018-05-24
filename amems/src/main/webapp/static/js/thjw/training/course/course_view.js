
var alertFormId = "courseForm";//序号

$(function() {
	Navigation(menuCode,"查看课程信息","View");//初始化导航
	setView();
	checkFx();
	AttachmengsList.show({
		attachHead : true,
		chinaHead : '课件信息',
		englishHead : 'Courseware Information',
		chinaFileHead : '课件名',
		englishFileHead : 'Courseware',
		chinaColFileTitle : '课件名',
		englishColFileTitle : 'Courseware',
		djid:$("#id").val(),
 		colOptionhead : false,
		fileHead : false,
		fileType:"course"
	});//显示附件
	post_list_view.init({
		djid : $("#id").val(),
		kcbh : $("#kcbh").val(),
		dprtcode : $("#dprtcode").val(),
		callback: function(){//回调函数
		}
	});//显示岗位
});

//设置只读/失效/隐藏
function setView(){
	$("#kcbh", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcmc", $("#"+alertFormId)).attr("readonly","readonly");
	$("#ks", $("#"+alertFormId)).attr("readonly","readonly");
	$("#fxks", $("#"+alertFormId)).attr("readonly","readonly");
	$("#zqz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kcnr", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxmb", $("#"+alertFormId)).attr("readonly","readonly");
	$("#fjjx", $("#"+alertFormId)).attr("readonly","readonly");
	$("#jyyq", $("#"+alertFormId)).attr("readonly","readonly");
	$("#pxjg", $("#"+alertFormId)).attr("readonly","readonly");
	$("#ly", $("#"+alertFormId)).attr("readonly","readonly");
	$("#jctg", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bz", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kclb", $("#"+alertFormId)).attr("disabled","true");
	$("#pxxs", $("#"+alertFormId)).attr("disabled","true");
	$("#zqdw", $("#"+alertFormId)).attr("disabled","true");
	$("#ksxs", $("#"+alertFormId)).attr("disabled","true");
	$("#fxpxxs", $("#"+alertFormId)).attr("disabled","true");
	$("#fxksxs", $("#"+alertFormId)).attr("disabled","true");
	$("input:radio[name='isFx']", $("#"+alertFormId)).attr("disabled","true");
}

//选中复训
function checkFx(){
	var type = $("input[name='isFx']:checked",$("#"+alertFormId)).val();
	if(type == 1){
		$(".isfx", $("#"+alertFormId)).show();
	}else{
		$(".isfx", $("#"+alertFormId)).hide();
	}
}