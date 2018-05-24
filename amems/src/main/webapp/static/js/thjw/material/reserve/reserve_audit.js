var orderNumber = 1;
var flag = true;

$(function() {
	Navigation(menuCode,"审核航材提订单","Audit");//初始化导航
	
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
	
	/*$("#workOrder").tagsinput({
		  itemValue: 'gdid',
		  itemText: 'gdbh'
	});*/
	$(".bootstrap-tagsinput input").attr("readonly", true);
	$("#reserveTable").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
	initReserveDetail();
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
        	tdmc: {
                validators: {
                	notEmpty: {
                        message: '提订名称不能为空'
                    },
                    stringLength: {
                        max: 150,
                        message: '长度最多不超过150个字符'
                    }
                }
            },         
        	sqyy: {
                validators: {
                	notEmpty: {
                        message: '请填写提订原因'
                    }
                }
            }  
        }
    });
	
	MessageListUtil.show({
		djid:$("#id").val(),
		dprtcode:$("#dprtcode").val(),//
		lx:1
	});//显示留言
	
	AttachmengsList.show({
		djid:$("#id").val(),
		fileType:"reserve"
	});//显示附件
	
});

$('#yqqx').datepicker({
	 autoclose: true,
	 clearBtn:true
});
//初始化提订航材
function initReserveDetail(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/reserve/queryReserveDetailListByMainId",
		type:"post",
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data.length > 0){
				initTableRow(data);
			}
		}
	});
}
//初始化提订航材列表
function initTableRow(data){
	
	//先移除暂无数据一行
	var len = $("#reserveTable").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length == 1){
			$("#reserveTable").empty();
		}
	}
	$.each(data,function(index,reserve){
		var row = reserve.hcMainData;
		row.orderNumber = orderNumber++;
		row.sqsl = reserve.sqsl;
		row.yt = reserve.yt;
		row.xjzt = reserve.xjzt;
		row.shsl = (reserve.shsl == 0?"":reserve.shsl);
		row.detailId = reserve.id;
		row.xjdh = reserve.xjdh;
		row.zjhStr = "";
		if(row.fixChapter != null){
			row.zjhStr = formatUndefine(row.fixChapter.zjh)+" "+formatUndefine(row.fixChapter.zwms);
		}
		addRow(row);
	});
	
}

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

//保存
function save(){
	var url = basePath+"/aerialmaterial/reserve/auditSave";
	var message = "保存成功!";
	performAction(url,message);
}
//确认
function submit(){
	var url = basePath+"/aerialmaterial/reserve/auditSubmit";
	var message = "确认成功!";
	performAction(url,message);
}

function performAction(url,message){
	/*$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}*/
	var id = $.trim($("#id").val());
	/*var jjcd = $.trim($("#jjcd").val());
	var yqqx = $.trim($("#yqqx").val());
	var sqyy = $.trim($("#sqyy").val());
	var tdmc = $.trim($("#tdmc").val());*/
	
	var reserve = {
			id : id/*,
			jjcd : jjcd,
			yqqx : yqqx,
			sqyy : sqyy,
			tdmc : tdmc*/
		};
	reserve.reserveDetailList = getReserveDetailParam();
//	reserve.reserveWorkOrderList = getWorkOrderParam();
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
			AlertUtil.showMessage(message,'/aerialmaterial/reserve/approve?id='+id+'&pageParam='+pageParam);
		}
	});
}

//获取提订航材参数
function getReserveDetailParam(){

	flag = true;
	var reserveDetailParam = [];
	
	var len = $("#reserveTable").find("tr").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length ==1){
			flag = false;
			AlertUtil.showErrorMessage("请选择提订航材!");
			return materialParam;
		}
	}
	if (len > 0){
		$("#reserveTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var detailId =$.trim($("input[name='detailId']").eq(index).val());
			var shsl =$.trim($("input[name='shsl']").eq(index).val());
			
			if(null == shsl || "" == shsl || shsl <= 0){
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '请输入审核数量!',
					callback : function(option){
						$("input[name='shsl']").eq(index).focus()
					}
				});
				return false;
			}
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			if (!regu.test(shsl)) {
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '审核数量只能输入数字,并保留两位小数！',
					callback : function(option){
						$("input[name='shsl']").eq(index).focus()
					}
				});
		        return false;
		    }
			infos.id = detailId;
			infos.shsl = shsl;
			reserveDetailParam.push(infos);
		});
	}
	return reserveDetailParam;
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

//向table新增一行
function addRow(obj){
	
	var tr = "";
		tr += "<tr id='"+obj.detailId+"'>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='detailId' value='"+obj.detailId+"'/>";
		tr += 	  "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(obj.bjh)+"'/>";
		tr += "</td>";
		
		tr +=  "<td title='"+StringUtil.escapeStr(obj.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.cjjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cjjh)+"</td>";
		
		if('00000' == StringUtil.escapeStr(obj.xh)){
			tr += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
	    }else{
	    	tr += "<td title='"+StringUtil.escapeStr(obj.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.xh)+"</td>";
	    }
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zjhStr)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialSecondTypeEnum',obj.hclxEj) +"</td>";
		tr += "<td style='text-align:right;vertical-align:middle;' name='sqsl'>"+formatUndefine(obj.sqsl)+"</td>";
		tr += "<td><input class='form-control' name='shsl' type='text' value='"+formatUndefine(obj.shsl)+"' placeholder='' onkeyup='clearNoNumTwo(this)' maxlength='10' /></td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw) +"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.yt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.yt)+"</td>";	
		tr += "<td style='text-align:center;vertical-align:middle;' name='xjzt' >";
		if(obj.xjzt == 0){
			tr += "<i class='icon-foursquare color-blue cursor-pointer' onClick=updateEquiryStatus('"+ obj.detailId + "') title='审核通过 Review'></i>";
		}
		if(obj.xjzt == 1){
			tr += "询价中";
		}
		if(obj.xjzt == 2){
			tr += "<a href='#' onclick=\"openEnquiryWin('"+obj.detailId+"')\">"+StringUtil.escapeStr(obj.xjdh)+"</a>";
		}
		tr += "</td>";
		tr += "</tr>";
	
	$("#reserveTable").append(tr);
}

//审核通过,修改询价状态为待询价
function updateEquiryStatus(detailId) {
	var param = {};
	param.id = detailId;
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/updateEquiryStatus",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('审核通过!');
			$("#"+detailId).find("td[name='xjzt']").html("询价中");
		}
	});
}

//打开询价对话框
function openEnquiryWin(id){
	var sqsl = $("#"+id).find("td[name='sqsl']").html();
	var materialSl = $("#"+id).find("input[name='shsl']").val();
	if(materialSl == '' || materialSl == 0){
		materialSl = Number(sqsl);
	}
	MaterialEnquiryModal.show({
		mainid:id,//在弹窗中作为条件mainid
		enquiryType:1,//询价类型
		materialSl:materialSl,//数量
		callback: function(data){//回调函数
		}
	})
}

//指定结束
function shutDown(){
	var id = $.trim($('#id').val());
	var sqdh = $.trim($('#reserveCode').val());
	AssignEndModal.show({
		chinaHead:'提订单号',//单号中文名称
		englishHead:'Order No.',//单号英文名称
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
						zdjsyy : data.gbyy
				};
				sbShutDown(obj);
			}
		}
	});
}

//确认指定结束
function sbShutDown(reserve) {	
	
	startWait();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/reserve/shutDown",
		contentType:"application/json;charset=utf-8",
		type:"post",
		async: false,
		data:JSON.stringify(reserve),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('指定结束成功!','/aerialmaterial/reserve/approve?id='+reserve.id+'&pageParam='+pageParam);
			$("#AssignEndModal").modal("hide");
		}
	});
}

/*function selectWorkOrder(obj){
	open_win_work_order.show({
		dprtcode:$("#dprtcode").val(),//
		callback: function(data){
			if(data != null && data.length > 0){
				$.each(data,function(index,row){
					$("#workOrder").tagsinput('add', row);
				});
			}
		}
	});
}*/

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
	 window.location.href =basePath+"/aerialmaterial/reserve/approve?pageParam="+pageParam;
}