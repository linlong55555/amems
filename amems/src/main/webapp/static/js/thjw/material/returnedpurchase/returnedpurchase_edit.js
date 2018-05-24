var orderNumber = 1;
var flag = true;
var ztData = [];
ztData[1] = "保存";
ztData[2] = "提交";
ztData[8] = "作废";
ztData[9] = "完成";
ztData[11] = "撤销";
$(function() {
	Navigation(menuCode,"修改退货","Update Borrow Apply");
	datepicker();
	//状态渲染
	$('#state').val(ztData[$('#state').val()]);
	
	$("#reserveTable").append("<tr><td  colspan='13' class='text-center'>暂无数据 No data.</td></tr>");
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	jddxlx: {
                validators: {
                	notEmpty: {
                        message: '请选择借调对象类型'
                    }
                }
            }  ,
            jddx: {
        	validators: {
        		notEmpty: {
        			message: '请选择借调对象'
        		}
        	}
            } 
        }
    });
	
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
	
	initReserveDetail();
	
});

function datepicker(){
	$('.date-picker').datepicker( 'setDate' , new Date());
}

$('#sqsj').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
 .updateStatus('sqsj', 'NOT_VALIDATED',null)  
 .validateField('sqsj');  
});
var state;

/**
 * 选择用户
 * @returns
 */
function selectUser(){
	userModal.show({
		selected:$("#thrid").val(),//原值，在弹窗中默认勾选
		dprtcode:$("#dprtcode").val(),
		callback: function(data){//回调函数
			$("#thrid").val(formatUndefine(data.id));
			$("#username").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
			$("#thbmid").val(StringUtil.escapeStr(data.bmdm));
			
			  $('#form1').data('bootstrapValidator')  
		      .updateStatus('username', 'NOT_VALIDATED',null)  
		      .validateField('username');  
		}
	})
}	

//保存
function save(){
	var url = basePath+"/aerialmaterial/returnedpurchase/updatesave";
	var message = "保存成功!";
	performAction(url,message);
}

//提交
function submit(){
	var url = basePath+"/aerialmaterial/returnedpurchase/updatesubmit";
	var message = "提交成功!";
	performAction(url,message);
}

function performAction(url,message){
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	}
	
	var fhdw = $.trim($("#fhdw").val());
	var id = $.trim($("#id").val());
	var bz = $.trim($("#bz").val());
	var thrq = $.trim($("#thrq").val());
	var thrid = $.trim($("#thrid").val());
	var thbmid = $.trim($("#thbmid").val());
	
	var reserve = {
		id:id,
		fhdw : fhdw,
		bz : bz,
		thrq : thrq,
		thrid : thrid,
		thbmid : thbmid
		};

	reserve.returnedPurchaseDetailList = getreturnedPurchaseDetailListParam();
	if(!flag){
		return false;
	}
	
	console.info(reserve);
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
			AlertUtil.showMessage(message,'/aerialmaterial/returnedpurchase/manage?id='+data+'&pageParam='+pageParam);
		}
	});
}

//返回
function goreturn(){
	 window.location.href =basePath+"/aerialmaterial/returnedpurchase/manage?pageParam="+pageParam;
}

//获取库存信息
function getreturnedPurchaseDetailListParam(){

	flag = true;
	var reserveDetailParam = [];
	
	var len = $("#reserveTable").find("tr").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length ==1){
			flag = false;
			AlertUtil.showErrorMessage("请选择退货库存!");
			return materialParam;
		}
	}
	if (len > 0){
		$("#reserveTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var kcid =$.trim($("input[name='kcid']").eq(index).val());
			var id =$.trim($("input[name='id']").eq(index).val());
			var sl =$.trim($("input[name='sl']").eq(index).val());
			var sn =$.trim($("input[name='sn']").eq(index).val());
			
			if(null == sl || "" == sl || sl <= 0){
				flag = false;
				$("input[name='sl']").eq(index).focus();
				AlertUtil.showErrorMessage("请输入退货数量!");
				return false;
			}
			
			if(null != sn&&"" !=sn ){
				if(sl>1){
					$("input[name='sn']").eq(index).focus();
					AlertUtil.showErrorMessage("当前件号为序列号管理，退货数量不能大于1!");
					flag = false;
					return false;
				}
			}else{
				var kcsl = $.trim($("input[name='kcsl']").eq(index).val());
				if(kcsl && parseFloat(sl) > parseFloat(kcsl)){
					AlertUtil.showErrorMessage("退货数量不可大于库存数量!");
					flag = false;
					return false;
				}
			}
			var materialHistory ={};
		
			materialHistory.id = id;
			infos.materialHistory=materialHistory;
			infos.sl = sl;
			infos.id = id;
			
			
			reserveDetailParam.push(infos);
		});
	}
	return reserveDetailParam;
}


//初始化提订航材
function initReserveDetail(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/returnedpurchase/queryDetailListByMainId",
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
		
		reserve.thid = reserve.id;
		reserve.id=reserve.materialHistory.kcid;
		reserve.ywms=reserve.materialHistory.ywms;
		reserve.zwms=reserve.materialHistory.zwms;
		reserve.jldw=reserve.materialHistory.jldw;
		reserve.sn=reserve.materialHistory.sn;
		reserve.pch=reserve.materialHistory.pch;
		reserve.bjh=reserve.materialHistory.bjh;
		reserve.cklb=reserve.materialHistory.cklb;
		reserve.kcsl=reserve.materialHistory.kcsl;
		reserve.cjjh=reserve.hcMainData.cjjh;
		reserve.hclx=reserve.hcMainData.hclx;
		reserve.gljb=reserve.hcMainData.gljb;
		reserve.orderNumber = orderNumber++;
		addRow(reserve);
	});
	
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
		tr += 	  "<input type='hidden' name='kcid' value='"+formatUndefine(obj.id)+"'/>";
		tr += 	  "<input type='hidden' name='id' value='"+formatUndefine(obj.thid)+"'/>";
		tr += 	  "<input type='hidden' name='sn' value='"+StringUtil.escapeStr(obj.sn)+"'/>";
		tr += 	  "<input type='hidden' name='kcsl' value='"+StringUtil.escapeStr(obj.kcsl)+"'/>";
		tr += "</td>";
		
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.ywms)+"'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zwms)+"'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cjjh)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb) +"</td>";
		tr += "<td class='text-left' style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sn)+"'>"+StringUtil.escapeStr(obj.sn)+"</td>"; 
		tr += "<td class='text-left' style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pch)+"'>"+StringUtil.escapeStr(obj.pch)+"</td>"; 
		tr += "<td style='text-align:right;vertical-align:middle;'>"+(obj.kcsl||0)+"</td>";
		tr += "<td><input class='form-control' name='sl' type='text' value='"+formatUndefine(obj.sl)+"' onkeyup='clearNoNum(this)' maxlength='10' /></td>";
		
	    if(StringUtil.escapeStr(obj.sn) != ""||StringUtil.escapeStr(obj.pch) != ""){
		  tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw)+"</td>";
	    }else{
	 	  tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw)+"</td>";
	    }
		tr += "</tr>";
	
	$("#reserveTable").append(tr);
}

//移除一行
function removeCol(e){
	$(e).parent().parent().remove();
	resXh();
	orderNumber--;
	var len = $("#reserveTable").find("tr").length;
	if(len < 1){
		$("#reserveTable").append("<tr><td  colspan='13'  class='text-center'>暂无数据 No data.</td></tr>");
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

/**
 * 打开库存列表对话框
 * @returns
 */
function openMaterialWinAdd(){
	
	var existsIdList = [];
	$("#reserveTable").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
		existsIdList.push(hiddenid);
	});
	
	open_win_material_basic.show({
		multi:true,
		existsIdList : existsIdList,
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
					row.zjhStr = "";
					if(row.fixChapter != null){
						row.zjhStr = row.fixChapter.zjh+" "+row.fixChapter.zwms;
					}
					addRow(row);
				});
			}
		}
	},true);
}


//只能输入数字和小数
function clearNoNum(obj){
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