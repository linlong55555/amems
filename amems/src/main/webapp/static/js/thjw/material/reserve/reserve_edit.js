
var orderNumber = 1;
var oldDetailArr = [];//已拥有的详情id数组
var flag = true;

$(function() {
	Navigation(menuCode,"编辑航材提订单","Edit");//初始化导航
	
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
	
	$("#reserveTable").append("<tr><td  colspan='14' class='text-center'>暂无数据 No data.</td></tr>");
	initReserveDetail();
	work_order_util.init();
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
});

$('#yqqx').datepicker({
	 autoclose: true,
	 clearBtn:true
});

/**
 * 工单
 */
var work_order_util = {
		id : 'work_order_util',
		workType : 135,//135工单，145工单
		init : function(){
			this.initBtnShowOrHide();
			this.initTagsinput();
			this.initWorkOrderList();
		},
		/**
		 * 初始化Tagsinput
		 */
		initTagsinput : function(){
			$("#workOrder").tagsinput({
				  itemValue: 'gdid',
				  itemText: 'gdbh'
				});
			$(".bootstrap-tagsinput input").attr("readonly", true);
		},
		initWorkOrderList : function(){
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/aerialmaterial/reserve/queryWorkOrderListByMainId",
				type:"post",
				data:{mainid:$.trim($("#id").val())},
				dataType:"json",
				success:function(data){
					if(data.length > 0){
						$.each(data, function(i, row){
							this_.workType = row.gdlx;
							$("#workOrder").tagsinput('add', row);
						});
					}
				}
			});
		},
		/**
		 * 清空Tagsinput
		 */
		clearWorkOrder : function(){
			$("#workOrder").tagsinput('removeAll');
		},
		/**
		 * 初始化操作按钮显示与隐藏
		 */
		initBtnShowOrHide : function(){
			$("#work_btn").hide();
			$("#work_single_btn").hide();
			if(checkBtnPermissions('aerialmaterial:reserve:manage:06') && !checkBtnPermissions('aerialmaterial:reserve:manage:07')){
				$("#work_single_btn").show();
				this.workType = 135;
			}else if(!checkBtnPermissions('aerialmaterial:reserve:manage:06') && checkBtnPermissions('aerialmaterial:reserve:manage:07')){
				$("#work_single_btn").show();
				this.workType = 145;
			}else if(checkBtnPermissions('aerialmaterial:reserve:manage:06') && checkBtnPermissions('aerialmaterial:reserve:manage:07')){
				$("#work_btn").show();
				this.initWebuiPopover();
			}
		},
		initWebuiPopover : function(){
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('operation-work-btn','body',function(obj){
				return this_.getWorkTyleBtns(obj);
			}, 50);
		},
		getWorkTyleBtns : function(obj){//获取工单类型按钮组
			var this_ = this;
			var str = "<div class='button-group-new' style='text-align:center;vertical-align:middle;'>";
			str += "<p style='background:white;' class='margin-bottom-0'>";
			str += "<a href='javascript:void(0);' onclick="+this_.id+".selectWorkOrder(135) style='padding-left:0px;'>135</a>";
			str += "</p>";
			str += "<p style='background:white;' class='margin-bottom-0'>";
			str += "<a href='javascript:void(0);' onclick="+this_.id+".selectWorkOrder(145) style='padding-left:0px;'>145</a>";
			str += "</p>";
			str += "</div>";
			return str;
		},
		openWorkOrder : function(){
			this.selectWorkOrder(this.workType);
		},
		selectWorkOrder : function(type){
			var this_ = this;
			open_win_work_order.show({
				dprtcode:userJgdm,
				gdzt:[2,7],
				type : type,//工单类型
				callback: function(data){
					if(data != null && data.length > 0){
						if(this_.workType == type){
							$.each(data,function(index,row){
								$("#workOrder").tagsinput('add', row);
							});
						}else{
							this_.clearWorkOrder();//清空
							this_.workType = type;
							$.each(data,function(index,row){
								$("#workOrder").tagsinput('add', row);
							});
						}
					}
				}
			}, true);
		},
		getWorkOrderParam : function(){//获取关联工单参数
			var this_ = this;
			var data = $("#workOrder").tagsinput('items');
			var workOrderParam = [];
			if(data != null && data != ''){
				$.each(data, function(i, row){
					infos = {};
					infos.gdlx = this_.workType;
					infos.gdbh = row.gdbh;
					infos.gdid = row.gdid;
					workOrderParam.push(infos);
				});
			}
			return workOrderParam;
		}
}

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
		row.detailId = reserve.id;
		row.zjhStr = "";
		if(row.fixChapter != null){
			row.zjhStr = formatUndefine(row.fixChapter.zjh)+" "+formatUndefine(row.fixChapter.zwms);
		}
		oldDetailArr.push(reserve.id);
		addRow(row);
	});
	
}

//保存
function save(){
	var url = basePath+"/aerialmaterial/reserve/editSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/reserve/editSubmit";
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
	var tdmc = $.trim($("#tdmc").val());
	
	var reserve = {
			id : id,
			jjcd : jjcd,
			yqqx : yqqx,
			sqyy : sqyy,
			tdmc : tdmc
		};
	reserve.reserveDetailIdList = oldDetailArr;
	reserve.reserveDetailList = getReserveDetailParam();
	reserve.reserveWorkOrderList = work_order_util.getWorkOrderParam();
	
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
			AlertUtil.showMessage(message,'/aerialmaterial/reserve/manage?id='+id+'&pageParam='+pageParam);
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
			return reserveDetailParam;
		}
	}
	if (len > 0){
		$("#reserveTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var detailId =$.trim($("input[name='detailId']").eq(index).val());
			var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
			var bjh =$.trim($("input[name='bjh']").eq(index).val());
			var sqsl =$.trim($("input[name='sqsl']").eq(index).val());
			var yt =$.trim($("input[name='yt']").eq(index).val());
			
			if(null == sqsl || "" == sqsl || sqsl <= 0){
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '请输入提订数量!',
					callback : function(option){
						$("input[name='sqsl']").eq(index).focus()
					}
				});
				return false;
			}
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			if (!regu.test(sqsl)) {
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '提订数量只能输入数字,并保留两位小数！',
					callback : function(option){
						$("input[name='sqsl']").eq(index).focus()
					}
				});
		        return false;
		    }
			infos.id = detailId;
			infos.bjid = hiddenid;
			infos.bjh = bjh;
			infos.sqsl = sqsl;
			infos.yt = yt;
			reserveDetailParam.push(infos);
		});
	}
	return reserveDetailParam;
}

/**
 * 打开航材列表对话框
 * @returns
 */
function openMaterialWinAdd(){
	
	var existsIdList = [];
	$("#reserveTable").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
		existsIdList.push(hiddenid);
	});
	
	MaterialReserveModal.show({
		existsIdList : existsIdList,//
		dprtcode:$("#dprtcode").val(),//
		callback: function(data){//回调函数
			if(data != null && data.length > 0){
				
				//先移除暂无数据一行
				var len = $("#reserveTable").length;
				if (len == 1) {
					if($("#reserveTable").find("td").length == 1){
						$("#reserveTable").empty();
					}
				}
				
				$.each(data,function(index,row){
					row.orderNumber = orderNumber++;
					row.detailId = "";
					row.zjhStr = "";
					if(row.fixChapter != null){
						row.zjhStr = StringUtil.escapeStr(row.fixChapter.zjh)+" "+StringUtil.escapeStr(row.fixChapter.zwms);
					}
					addRow(row);
				});
			}
		}
	})
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<button class='line6' onclick='removeCol(this)'>";
	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	    tr += "</button>";
		tr += "</td>";
		
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
		
		if('00000' == formatUndefine(obj.xh)){
			tr += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
	    }else{
	    	tr += "<td title='"+StringUtil.escapeStr(obj.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.xh)+"</td>";
	    }
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zjhStr)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialSecondTypeEnum',obj.hclxEj) +"</td>";
		tr += "<td><input class='form-control' name='sqsl' type='text' value='"+StringUtil.escapeStr(obj.sqsl)+"' placeholder='' onkeyup='clearNoNumTwo(this)' maxlength='10' /></td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw) +"</td>";
		tr += "<td><input class='form-control' name='yt' type='text' value='"+StringUtil.escapeStr(obj.yt)+"' maxlength='16' /></td>";
		tr += "</tr>";
	
	$("#reserveTable").append(tr);
}

//移除一行
function removeCol(obj){
	$(obj).parent().parent().remove();
	resXh();
	orderNumber--;
	var len = $("#reserveTable").find("tr").length;
	if(len < 1){
		$("#reserveTable").append("<tr><td  colspan='14'  class='text-center'>暂无数据 No data.</td></tr>");
	}
}

function resXh(){
	
	var len = $("#reserveTable").find("tr").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length ==1){
			return false;
		}
	}
	var orderNumber = 1;
	if (len > 0){
		$("#reserveTable").find("tr").each(function(){
			var index=$(this).index(); //当前行
			$("span[name='orderNumber']").eq(index).html(orderNumber++);
		});
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
	 window.location.href =basePath+"/aerialmaterial/reserve/manage?pageParam="+pageParam;
}
