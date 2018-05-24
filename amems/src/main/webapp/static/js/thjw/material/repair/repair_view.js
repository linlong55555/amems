
$(function() {
	Navigation(menuCode,"查看航材送修单","View");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	MessageListUtil.show({
		djid:$("#id").val(),
		dprtcode:$("#dprtcode").val(),//
		lx:2
	});//显示留言
	
	AttachmengsListView.show({
		djid:$("#id").val(),
		fileType:"repair"
	});//显示附件
	
	initWorkOrderList();
	refreshPermission();
});

//初始化相关工单
function initWorkOrderList(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/reserve/queryWorkOrderListByMainId",
		type:"post",
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data.length > 0){
				var workOrder = formatWorkCard(data);
				$("#workOrder").html(workOrder);
			}
		}
	});
}

//打开询价对话框
function openEnquiryWin(id){
	MaterialEnquiryModal.show({
		mainid:id,//在弹窗中作为条件mainid
		enquiryType:2,//询价类型
		callback: function(data){//回调函数
		}
	})
}


function formatWorkCard(data){
	var card = '';
	$.each(data,function(i,s){
		card += "<a href='#' onclick='viewWorkCard(\""+s.gdid+"\")'>"+StringUtil.escapeStr(s.gdbh)+"</a>";
		if(i != data.length - 1){
			card += ",";
		}
	});
	return card;
}

function viewWorkCard(id){
	window.open(basePath+"/produce/workorder/woView?gdid="+id);
}
//转换工单类型
function transGdlx(gdlx){
	if(gdlx == 10){
		return 1
	}else if(gdlx == 20){
		return 3
	}else {
		return 2;
	}
}

//返回前页面
function back(){
	if(type == 'manage'){
		window.location.href =basePath+"/aerialmaterial/repair/manage?pageParam="+pageParam;
	}
	if(type == 'approve'){
		window.location.href =basePath+"/aerialmaterial/repair/approve?pageParam="+pageParam;
	}
	 
}
