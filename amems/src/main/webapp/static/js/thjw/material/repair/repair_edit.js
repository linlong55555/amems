
var orderNumber = 1;
var flag = true;

$(function() {
	Navigation(menuCode,"编辑航材送修单","Edit");//初始化导航
	
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
                       
        	sqyy: {
                validators: {
                	notEmpty: {
                        message: '请填写送修原因'
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
			if(checkBtnPermissions('aerialmaterial:repair:manage:06') && !checkBtnPermissions('aerialmaterial:repair:manage:07')){
				$("#work_single_btn").show();
				this.workType = 135;
			}else if(!checkBtnPermissions('aerialmaterial:repair:manage:06') && checkBtnPermissions('aerialmaterial:repair:manage:07')){
				$("#work_single_btn").show();
				this.workType = 145;
			}else if(checkBtnPermissions('aerialmaterial:repair:manage:06') && checkBtnPermissions('aerialmaterial:repair:manage:07')){
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

//保存
function save(){
	var url = basePath+"/aerialmaterial/repair/editSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/repair/editSubmit";
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
			AlertUtil.showMessage(message,'/aerialmaterial/repair/manage?id='+$.trim($("#detailid").val())+'&pageParam='+pageParam);
		}
	});
}

/**
 * 选择送修航材
 * @returns
 */
function openMaterialWinAdd(){
	MaterialRepairModal.show({
		selected:"",//原值，在弹窗中默认勾选
		dprtcode:$("#dprtcode").val(),//
		callback: function(data){//回调函数
			if(data != null && data.id != null){
				$("#kcid").val(data.id);
				$("#bjh").val(data.hCMainData?data.hCMainData.bjh:"");
				$("#zwms").val(data.hCMainData?data.hCMainData.zwms:"");
				$("#ywms").val(data.hCMainData?data.hCMainData.ywms:"");
				$("#cjjh").val(data.hCMainData?data.hCMainData.cjjh:"");
				
				$("#sn").val(data.sn);
				
				var row = {};
				row.kcid = data.id;
				row.bjh = data.hCMainData?data.hCMainData.bjh:"";
				row.zwms = data.hCMainData?data.hCMainData.zwms:"";
				row.ywms = data.hCMainData?data.hCMainData.ywms:"";
				row.cjjh = data.hCMainData?data.hCMainData.cjjh:"";
				row.sn = data.sn;
				var zjhStr = '';
				if(data.hCMainData != null && data.hCMainData.fixChapter != null){
					zjhStr = (formatUndefine(data.hCMainData.fixChapter.zjh)+" "+formatUndefine(data.hCMainData.fixChapter.zwms));
				}
				row.zjhStr = zjhStr;
				row.hclx = DicAndEnumUtil.getEnumName('materialTypeEnum',data.hCMainData?data.hCMainData.hclx:"");
				row.jldw = data.hCMainData?data.hCMainData.jldw:"";
				addRow(row);
			}
		}
	});
}

//向table新增一行
function addRow(obj){
	
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>1</span>";
		tr += 	  "<input type='hidden' id='kcid' name='hiddenid' value='"+obj.kcid+"'/>";
		tr += "</td>";
		
		tr +=  "<td title='"+StringUtil.escapeStr(obj.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.cjjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cjjh)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.sn)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.sn)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zjhStr)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.hclx)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw)+"</td>";
		
		tr += "</tr>";
		
	$("#repairTable").empty();
	$("#repairTable").append(tr);
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
	 window.location.href =basePath+"/aerialmaterial/repair/manage?pageParam="+pageParam;
}
