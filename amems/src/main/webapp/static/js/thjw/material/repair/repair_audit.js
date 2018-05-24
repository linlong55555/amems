
var orderNumber = 1;
var flag = true;

$(function() {
	Navigation(menuCode,"审核航材送修单","Audit");//初始化导航
	
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
	
	$("#workOrder").tagsinput({
		  itemValue: 'gdid',
		  itemText: 'gdbh'
	});
	$(".bootstrap-tagsinput input").attr("readonly", true);
	initWorkOrderList();
	refreshPermission();
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	sqyy: {
                validators: {
                	notEmpty: {
                        message: '请填写送修原因'
                    }
                }
            }  
        }
    });
	
	MessageListUtil.show({
		djid:$("#id").val(),
		dprtcode:$("#dprtcode").val(),//
		lx:2
	});//显示留言
	
	AttachmengsList.show({
		djid:$("#id").val(),
		fileType:"repair"
	});//显示附件
});

$('#yqqx').datepicker({
	 autoclose: true,
	 clearBtn:true
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
				$.each(data, function(i, row){
					$("#workOrder").tagsinput('add', row);
				});
			}
		}
	});
}

//保存
function save(){
	var url = basePath+"/aerialmaterial/repair/auditSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/repair/auditSubmit";
	var message = "提交成功!";
	performAction(url,message);
}

function performAction(url,message){
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	var id = $.trim($("#id").val());
	var jjcd = $.trim($("#jjcd").val());
	var yqqx = $.trim($("#yqqx").val());
	var sqyy = $.trim($("#sqyy").val());
	var kcllid = $.trim($("#kcllid").val());
	var kcidOld = $.trim($("#kcidOld").val());
	var kcid = $.trim($("#kcid").val());
	
	if(kcid == null || kcid == ''){
		AlertUtil.showErrorMessage('请选择送修航材!');
		return false;
	}
	
	var reserve = {
			id : id,
			jjcd : jjcd,
			yqqx : yqqx,
			sqyy : sqyy
		};
	
	reserve.kcid = kcid;
	reserve.kcllid = kcllid;
	reserve.kcidOld = kcidOld;
	reserve.reserveWorkOrderList = getWorkOrderParam();
	reserve.attachments = AttachmengsList.getAttachments();
	
	if(!flag){
		return false;
	}
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(reserve),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage(message,'/aerialmaterial/repair/approve?id='+$.trim($("#detailid").val())+'&pageParam='+pageParam);
		}
	});
}

//获取关联工单参数
function getWorkOrderParam(){

	var data = $("#workOrder").tagsinput('items');
	var workOrderParam = [];
	if(data != null && data != ''){
		$.each(data, function(i, row){
			infos = {};
			infos.gdlx = row.gdlx;
			infos.gdbh = row.gdbh;
			infos.gdid = row.gdid;
			workOrderParam.push(infos);
		});
	}
	return workOrderParam;
}

//审核通过,修改询价状态为待询价
function updateEquiryStatus(detailId) {
	var param = {};
	param.id = detailId;
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/repair/updateEquiryStatus",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('审核通过!');
			$("#enquiry").html("询价中");
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

//指定结束
function shutDown(){
	var id = $.trim($('#id').val());
	var sqdh = $.trim($('#repairCode').val());
	AssignEndModal.show({
		chinaHead:'送修单号',//单号中文名称
		englishHead:'Repair No.',//单号英文名称
		edit:true,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		jsdh:sqdh,//指定结束单号
		jsr:'',//指定结束人
		jssj:'',//指定结束时间
		jsyy:'',//指定结束原因
		showType : false,
		callback: function(data){//回调函数
			if(null != data && data != ''){
				var obj = {
						id : id,
						zdjsrid : $("#userId").val(),
						zdjsyy :  data.gbyy
				};
				sbShutDown(obj);
			}
		}
	});
}

//确认指定结束
function sbShutDown(repair) {
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/repair/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(repair),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('指定结束成功!','/aerialmaterial/repair/approve?id='+$.trim($("#detailid").val())+'&pageParam='+pageParam);
			$("#AssignEndModal").modal("hide");
		}
	});
}

function selectWorkOrder(obj){
	open_win_work_order.show({
		dprtcode:$("#dprtcode").val(),//
		gdzt:[2,7],
		callback: function(data){
			if(data != null && data.length > 0){
				$.each(data,function(index,row){
					$("#workOrder").tagsinput('add', row);
				});
			}
		}
	}, true);
}

//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
}

//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/repair/approve?pageParam="+pageParam;
}