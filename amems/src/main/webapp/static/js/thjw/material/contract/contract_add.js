
var orderNumber = 1;
var flag = true;
var materialWinAddData = [];
var numberValidator = {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	};
$(function() {
	Navigation(menuCode,"新增合同","Add");//初始化导航
	
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
	
	$("#materialTable").append("<tr><td  colspan='15' class='text-center'>暂无数据 No data.</td></tr>");
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	htlx: {
                validators: {
                	notEmpty: {
                        message: '请选择合同类型!'
                    }
                }
            },
            hth: {
                validators: {
                	notEmpty: {
                        message: '合同编号不能为空!'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '不能输入中文且长度最多不超过20个字符'
                    }
                }
            },
            jjcd: {
                validators: {
                	notEmpty: {
                        message: '请选择紧急程度!'
                    }
                }
            },
            gys: {
                validators: {
                	notEmpty: {
                        message: '请选择供应商!'
                    }
                }
            },
            htqdrq: {
                validators: {
                	notEmpty: {
                        message: '请选择合同签订日期'
                    }
                }
            } 
        }
    });
	changeContractType();
	
	AttachmengsList.show({
		djid:null,
		fileType:"contract"
	});//显示附件
	refreshPermission();
});

$('#htqdrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
	    .updateStatus('htqdrq', 'NOT_VALIDATED',null)  
	    .validateField('htqdrq');  
	});

//合同类型改变时,加载供应商信息
function changeContractType(){
	var type = $("#htlx").val();
	var len = $("#materialTable").find("tr").length;
	if (len >= 1 && $("#materialTable").find("td").length !=1) {
		$("#htlx").val(type == 1?2:1);
		AlertUtil.showConfirmMessage("切换合同类型将清空航材列表，确定要切换吗？", {callback: function(){
			$("#htlx").val(type);
			changeType(type);
			
		}});
	}else{
		changeType(type);
	}
}
//改变类型
function changeType(type){
	showorhideColumn();
	$("#materialTable").empty();
	if($("#htlx").val() == 1){
		$("#materialTable").append("<tr><td  colspan='15' class='text-center'>暂无数据 No data.</td></tr>");
	}else{
		$("#materialTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
	}
	var searchParam = {};
	searchParam.gyslb = type;
	searchParam.dprtcode = $.trim($("#dprtId").val());
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryFirmList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   $("#gys").empty();
	   var supplierOption = '';
	   if(data.length >0){
		   $.each(data,function(index,row){
			   if(row.hzzk != 9){
				   supplierOption += '<option value="'+row.id+'" >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }
		   });
	   }
	   $("#gys").append(supplierOption);
	   $("#gys").selectpicker('refresh');
     }
   }); 
}

//保存
function save(){
	var url = basePath+"/aerialmaterial/contract/addSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/contract/addSubmit";
	var message = "提交成功!";
	performAction(url,message);
}

function performAction(url,messageStr){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	var htlx = $.trim($("#htlx").val());
	var hth = $.trim($("#hth").val());
	var jjcd = $.trim($("#jjcd").val());
	var gysid = $.trim($("#gys").val());
	var htqdrq = $.trim($("#htqdrq").val());
	var fjfy = $.trim($("#fjfy").val());
	var bz = $.trim($("#bz").val());
	if(null == gysid || '' == gysid){
		AlertUtil.showErrorMessage("请选择供应商!");
		return false;
	}
	
	if(fjfy != null && fjfy != '' && !numberValidator.reg.test(fjfy)){  
		AlertUtil.showMessageCallBack({
			message : "附加费用"+numberValidator.message,
			callback : function(option){
				$("#fjfy").focus();
			}
		});
		return false;
	} 
	
	var param = {
			htlx : htlx,
			hth : hth,
			jjcd : jjcd,
			gysid : gysid,
			htqdrq : htqdrq,
			fjfy : fjfy,
			bz : bz
		};

	param.contractDetailList = getContractDetailParam();
	var message = MessageFormUtil.getData();
	message.lx = htlx == 1?3:4;
	param.message = message;
	param.attachments = AttachmengsList.getAttachments();
	
	if(!flag){
		return false;
	}
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage(messageStr,'/aerialmaterial/contract/main?id='+data+'&pageParam='+pageParam);
		}
	});
}

//获取合同详情参数
function getContractDetailParam(){

	flag = true;
	var contractDetailParam = [];
	
	var len = $("#materialTable").find("tr").length;
	if (len == 1) {
		if($("#materialTable").find("td").length ==1){
			flag = false;
			AlertUtil.showErrorMessage("请选择航材!");
			return contractDetailParam;
		}
	}
	var type = $("#htlx").val();
	if (len > 0){
		var regu = /^[0-9]+\.?[0-9]{0,2}$/;
		$("#materialTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
			var tddid =$.trim($("input[name='tddid']").eq(index).val());
			var tddhcid =$.trim($("input[name='tddhcid']").eq(index).val());
			var bjClf =$.trim($("input[name='bjClf']").eq(index).val());
			var bjGsf =$.trim($("input[name='bjGsf']").eq(index).val());
			var bjQtf =$.trim($("input[name='bjQtf']").eq(index).val());
			var htSl = 1;
			var htDhrq =$.trim($("input[name='htDhrq']").eq(index).val());
			
			if(type == 1){
				htSl =$.trim($("input[name='htSl']").eq(index).val());
				var tdsl = $.trim($("input[name='tdsl']").eq(index).val());
				if(null == htSl || "" == htSl || htSl <= 0){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '请输入合同数量!',
						callback : function(option){
							$("input[name='htSl']").eq(index).focus();
						}
					});
					return false;
				}
				
				if(!numberValidator.reg.test(htSl)){  
					AlertUtil.showMessageCallBack({
						message : "合同数量"+numberValidator.message,
						callback : function(option){
							$("input[name='htSl']").eq(index).focus();
						}
					});
					flag = false;
					return false;
				} 
				
				if(Number(tdsl) < Number(htSl)){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '合同数量不能大于提订数量!',
						callback : function(option){
							$("input[name='htSl']").eq(index).focus();
						}
					});
					return false;
				}
				if(null == bjClf || "" == bjClf || bjClf <= 0){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '请输入单价!',
						callback : function(option){
							$("input[name='bjClf']").eq(index).focus();
						}
					});
					return false;
				}
				
				if(!numberValidator.reg.test(bjClf)){  
					AlertUtil.showMessageCallBack({
						message : "单价"+numberValidator.message,
						callback : function(option){
							$("input[name='bjClf']").eq(index).focus();
						}
					});
					flag = false;
					return false;
				} 
				bjGsf = 0;
				bjQtf = 0;
			}else{
				if(null == bjClf || "" == bjClf || bjClf <= 0){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '请输入材料费!',
						callback : function(option){
							$("input[name='bjClf']").eq(index).focus();
						}
					});
					return false;
				}
				
				if(!numberValidator.reg.test(bjClf)){  
					AlertUtil.showMessageCallBack({
						message : "材料费"+numberValidator.message,
						callback : function(option){
							$("input[name='bjClf']").eq(index).focus();
						}
					});
					flag = false;
					return false;
				} 
				if(null == bjGsf || "" == bjGsf || bjGsf <= 0){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '请输入工时费!',
						callback : function(option){
							$("input[name='bjGsf']").eq(index).focus();
						}
					});
					return false;
				}
				if(!numberValidator.reg.test(bjGsf)){  
					AlertUtil.showMessageCallBack({
						message : "工时费"+numberValidator.message,
						callback : function(option){
							$("input[name='bjGsf']").eq(index).focus();
						}
					});
					flag = false;
					return false;
				} 
				if(null == bjQtf || "" == bjQtf || bjQtf <= 0){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : '请输入其它费!',
						callback : function(option){
							$("input[name='bjQtf']").eq(index).focus();
						}
					});
					return false;
				}
				if(!numberValidator.reg.test(bjQtf)){  
					AlertUtil.showMessageCallBack({
						message : "其它费"+numberValidator.message,
						callback : function(option){
							$("input[name='bjQtf']").eq(index).focus();
						}
					});
					flag = false;
					return false;
				} 
			}
			if(null == htDhrq || "" == htDhrq){
				flag = false;
				AlertUtil.showErrorMessage("请输入预计到货日期!");
				return false;
			}
			infos.id = hiddenid;
			infos.tddid = tddid;
			infos.tddhcid = tddhcid;
			infos.htClf = bjClf;
			infos.htGsf = bjGsf;
			infos.htQtf = bjQtf;
			infos.htSl = htSl;
			infos.htDhrq = htDhrq;
			contractDetailParam.push(infos);
		});
	}
	return contractDetailParam;
}

/**
 * 打开航材列表对话框
 * @returns
 */
function openMaterialWinAdd(){
	
	var existsIdList = [];
	$("#materialTable").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var tddhcid =$.trim($("input[name='tddhcid']").eq(index).val());
		existsIdList.push(tddhcid);
	});
	
	MaterialContractModal.show({
		existsIdList : existsIdList,//
		htlx : $("#htlx").val(),
		callback: function(data){//回调函数
			if(data != null && data.length > 0){
				
				//先移除暂无数据一行
				var len = $("#materialTable").length;
				if (len == 1) {
					if($("#materialTable").find("td").length == 1){
						$("#materialTable").empty();
					}
				}
				
				$.each(data,function(index,row){
					var bjIdList = [];
					bjIdList.push(row.BJID);
					var procurementCatalogList = getProcurementCatalog(bjIdList);
					$.each(procurementCatalogList,function(index,procurementCatalog){
						if(procurementCatalog.bjid == row.BJID){
							row.BJ_CLF = procurementCatalog.bjClf;
							row.BJ_GSF = procurementCatalog.bjGsf;
							row.BJ_QTF = procurementCatalog.bjQtf;
						}
					});
					row.orderNumber = orderNumber++;
					row.TDDHCID = row.ID;
					row.ID = "";
					row.TDSL = row.SL - row.YCGSL;
					row.HTSL = '';
					addRow(row);
				});
				setHtfy();
			}
		}
	})
}


//向table新增一行
function addRow(obj){
	var type = $("#htlx").val();
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<button class='line6' onclick='removeCol(this)'>";
	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	    tr += "</button>";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.ID+"'/>";
		tr += 	  "<input type='hidden' name='tddid' value='"+obj.TDDID+"'/>";
		tr += 	  "<input type='hidden' name='tddhcid' value='"+obj.TDDHCID+"'/>";
		tr += 	  "<input type='hidden' name='bjid' value='"+obj.BJID+"'/>";
		tr += 	  "<input type='hidden' name='tdsl' value='"+formatUndefine(obj.TDSL)+"'/>";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SQDH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.BJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.BJH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.YWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.YWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.ZWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ZWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.CJJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.CJJH)+"</td>";
		
		var totalPrice = '';
		
		if(type == 1){
			tr += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(obj.TDSL)+"</td>";
			tr += "<td>";
			tr += "<input name='htSl' type='text' value='"+formatUndefine(obj.TDSL)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' maxlength='10' />";
			tr += "</td>";
			tr += "<td>";
			tr += "<input name='bjClf' type='text' value='"+formatUndefine(obj.BJ_CLF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			if(formatUndefine(obj.BJ_CLF) != '' && formatUndefine(obj.TDSL) != '' && obj.TDSL > 0){
				totalPrice = formatUndefine(obj.BJ_CLF) * obj.TDSL;
			}
		}else{
			tr += "<td style='text-align:center;vertical-align:middle;'>"+formatUndefine(obj.SN)+"</td>";
			tr += "<td>";
			tr += "<input name='bjClf' type='text' value='"+formatUndefine(obj.BJ_CLF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			tr += "<td>";
			tr += "<input name='bjGsf' type='text' value='"+formatUndefine(obj.BJ_GSF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			tr += "<td>";
			tr += "<input name='bjQtf' type='text' value='"+formatUndefine(obj.BJ_QTF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			totalPrice = formatUndefine(obj.BJ_CLF) + formatUndefine(obj.BJ_GSF) + formatUndefine(obj.BJ_QTF);
		}
		if(totalPrice == ''){
			totalPrice = 0;
		}
		totalPrice = totalPrice.toFixed(2);
		tr += "<td title='"+totalPrice+"' name='totalPrice' style='text-align:right;vertical-align:middle;'>"+totalPrice+"</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-0 padding-right-0'>";
		if(formatUndefine(obj.YJDHRQ) != ''){
			tr += "		<input type='text' class='form-control datepicker' name='htDhrq' value="+obj.YJDHRQ+" data-date-format='yyyy-mm-dd' placeholder=''  maxlength='10' />";
		}else{
			tr += "		<input type='text' class='form-control datepicker' name='htDhrq' data-date-format='yyyy-mm-dd' placeholder=''  maxlength='10' />";
		}
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td title='"+(StringUtil.escapeStr(obj.USERNAME)+" "+StringUtil.escapeStr(obj.REALNAME))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.USERNAME)+" "+StringUtil.escapeStr(obj.REALNAME)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>"+formatUndefine(obj.SQSJ)+"</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(formatUndefine(obj.YQQX))+"</td>";
		
		tr += "</tr>";
		
	$("#materialTable").append(tr);
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
}

//移除一行
function removeCol(obj){
	$(obj).parent().parent().remove();
	resXh();
	orderNumber--;
	setHtfy();//设置合同费用
	var len = $("#materialTable").find("tr").length;
	if(len < 1){
		if($("#htlx").val() == 1){
			$("#materialTable").append("<tr><td  colspan='15' class='text-center'>暂无数据 No data.</td></tr>");
		}else{
			$("#materialTable").append("<tr><td  colspan='16' class='text-center'>暂无数据 No data.</td></tr>");
		}
	}
}

function resXh(){
	
	var len = $("#materialTable").find("tr").length;
	if (len == 1) {
		if($("#materialTable").find("td").length ==1){
			return false;
		}
	}
	var orderNumber = 1;
	if (len > 0){
		$("#materialTable").find("tr").each(function(){
			var index=$(this).index(); //当前行
			$("span[name='orderNumber']").eq(index).html(orderNumber++);
		});
	}
}

function setTotalPrice(obj){
	var totalPrice = '';
	var type = $("#htlx").val();
	var bjClf = $(obj).parent().parent().find("input[name='bjClf']").val();
	if(type == 1){
		var sl = $(obj).parent().parent().find("input[name='htSl']").val();
		totalPrice = bjClf*sl;
	}else{
		var bjGsf = $(obj).parent().parent().find("input[name='bjGsf']").val();
		var bjQtf = $(obj).parent().parent().find("input[name='bjQtf']").val();
		totalPrice = bjClf*1 + bjGsf*1 + bjQtf*1;
	}
	if(totalPrice == ''){
		totalPrice = 0;
	}
	totalPrice = totalPrice.toFixed(2);
	$(obj).parent().parent().find("td[name='totalPrice']").html(totalPrice);
	$(obj).parent().parent().find("td[name='totalPrice']").attr("title",totalPrice);
}

//设置合同费用
function setHtfy(){
	var htfy = 0;
	$("#materialTable").find("tr").each(function(){
		htfy += Number($.trim($(this).find("td[name='totalPrice']").html()));
	});
	htfy += Number($.trim($("#fjfy").val()));
	htfy = htfy.toFixed(2);
	$("#htfy").val(htfy);
}

function changePrice(){
	var type = $("#htlx").val();
	var len = $("#materialTable").find("tr").length;
	if (len == 1) {
		if($("#materialTable").find("td").length ==1){
			return false;
		}
	}
	var bjIdList = [];
	if (len > 0){
		$("#materialTable").find("tr").each(function(){
			var index=$(this).index(); //当前行
			var bjid =$.trim($("input[name='bjid']").eq(index).val());
			bjIdList.push(bjid);
		});
	}
	var procurementCatalogList = getProcurementCatalog(bjIdList);
	
	$("#materialTable").find("tr").each(function(){
		var rowIndex=$(this).index(); //当前行
		var bjid =$.trim($("input[name='bjid']").eq(rowIndex).val());
		var sl =$.trim($("input[name='htSl']").eq(rowIndex).val());
		var totalPrice = 0;
		var bjClf = '';
		var bjGsf = '';
		var bjQtf = '';
		$.each(procurementCatalogList,function(index,procurementCatalog){
			if(procurementCatalog.bjid == bjid){
				bjClf = procurementCatalog.bjClf;
				bjGsf = procurementCatalog.bjGsf;
				bjQtf = procurementCatalog.bjQtf;
			}
		});
		$.trim($("input[name='bjClf']").eq(rowIndex).val(bjClf));
		if(type == 1){
			totalPrice = bjClf*sl;
		}else{
			$.trim($("input[name='bjGsf']").eq(rowIndex).val(bjGsf));
			$.trim($("input[name='bjQtf']").eq(rowIndex).val(bjQtf));
			totalPrice = bjClf*1 + bjGsf*1 + bjQtf*1;
		}
		totalPrice = totalPrice.toFixed(2);
		$.trim($("td[name='totalPrice']").eq(rowIndex).html(totalPrice));
	});
}

function getProcurementCatalog(bjIdList){
	var procurementCatalogList = [];
	var searchParam = {};
	searchParam.gysid = $.trim($("#gys").val());
	searchParam.dprtcode = $.trim($("#dprtId").val());
	searchParam.bjIdList = bjIdList;
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/enquiry/queryProcurementCatalogList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   async:false,
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   if(data != null && data.length >0){
		   procurementCatalogList = data;
	   }
     }
	}); 
 	return procurementCatalogList;
}

function showorhideColumn(){
	var type = $("#htlx").val();
	if(type == 1){
		$("#contractHead tr th:eq(2)").show();
		$("#contractHead tr th:eq(9)").show();
		$("#contractHead tr th:eq(10)").show();
		$("#contractHead tr th:eq(3)").hide();
		$("#contractHead tr th:eq(8)").hide();
		$("#contractHead tr th:eq(11)").show();
		$("#contractHead tr th:eq(12)").hide();
		$("#contractHead tr th:eq(13)").hide();
		$("#contractHead tr th:eq(14)").hide();
	}else{
		$("#contractHead tr th:eq(2)").hide();
		$("#contractHead tr th:eq(9)").hide();
		$("#contractHead tr th:eq(10)").hide();
		$("#contractHead tr th:eq(3)").show();
		$("#contractHead tr th:eq(8)").show();
		$("#contractHead tr th:eq(11)").hide();
		$("#contractHead tr th:eq(12)").show();
		$("#contractHead tr th:eq(13)").show();
		$("#contractHead tr th:eq(14)").show();
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
     obj.value = validateMax(obj.value);
     function validateMax(_value){
    	 if(Number(_value) > 9999999999.99){
    		return validateMax(_value.substr(0, _value.length-1));
    	 }
    	 return _value;
    }
     setTotalPrice(obj);
     setHtfy();
}

$('input[name=date-range-picker]').daterangepicker().prev().on("click",
	function() {
		$(this).next().focus();
	});

//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/contract/main?pageParam="+pageParam;
}
