
var orderNumber = 1;
var flag = true;

$(function() {
	Navigation(menuCode,"合同收货","Come Good");//初始化导航
	$("#materialTable").append("<tr><td  colspan='19' class='text-center'>暂无数据 No data.</td></tr>");
	changeContractType();
	initContractDetail();
	refreshPermission();
});

//合同类型改变时,加载供应商信息
function changeContractType(){
	var type = $("#htlx").val();
	showorhideColumn();
	var searchParam = {};
	searchParam.gyslb = type;
	searchParam.dprtcode = $.trim($("#dprtId").val());
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryFirmList",
	   type: "post",
	   async:false,
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	   finishWait();
	   $("#gys").empty();
	   var gysid = $.trim($("#gysid").val());
	   var supplierOption = '';
	   if(data.length >0){
		   $.each(data,function(index,row){
			   if(gysid == row.id){
				   supplierOption += '<option value="'+row.id+'" selected=true >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }else{
				   supplierOption += '<option value="'+row.id+'" >'+StringUtil.escapeStr(row.gysmc)+'</option>';
			   }
		   });
	   }
	   $("#gys").append(supplierOption);
     }
   }); 
}

//初始化合同详情
function initContractDetail(){
	var type = $("#htlx").val();
	var url = basePath+"/aerialmaterial/contract/queryReserveContractDetailList";
	if(type == 2){
		url = basePath+"/aerialmaterial/contract/queryRepairContractMaterialList";
	}
	AjaxUtil.ajax({
		async: false,
		url:url,
		type:"post",
		async:false,
		data:{mainid:$.trim($("#id").val())},
		dataType:"json",
		success:function(data){
			if(data != null && data.length > 0){
				initTableRow(data);
			}
		}
	});
}
//初始化合同详情列表
function initTableRow(data){
	
	//先移除暂无数据一行
	var len = $("#materialTable").length;
	if (len == 1) {
		if($("#materialTable").find("td").length == 1){
			$("#materialTable").empty();
		}
	}
	$.each(data,function(index,row){
		row.orderNumber = orderNumber++;
		addRow(row);
	});
	
}

//提交
function submit(){
	var url = basePath+"/aerialmaterial/contract/comeGood";
	var message = "提交成功!";
	performAction(url,message);
}

function performAction(url,message){
	
	var id = $.trim($("#id").val());
	
	var param = {};
	param.id = id;
	param.contractDetailList = getContractDetailParam();
	
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
			AlertUtil.showMessage(message,'/aerialmaterial/contract/main');
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
		$("#materialTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
			var bjid =$.trim($("input[name='bjid']").eq(index).val());
			var bjh =$.trim($("input[name='bjh']").eq(index).val());
			var message = '';
			
			if(type == 1){
				var bcdhs =$.trim($("input[name='bcdhs']").eq(index).val());
				var wdhs =$.trim($("input[name='wdhs']").eq(index).val());

				if(null === bcdhs || "" === bcdhs){
					message = '请输入本次到货数,如果未到货请输入0!'
				}
				if(Number(bcdhs) > Number(wdhs)){
					message = '本次到货数必须小于等于未到货数!'
				}
				if(message != ''){
					flag = false;
					AlertUtil.showMessageCallBack({
						message : message,
						callback : function(option){
							$("input[name='bcdhs']").eq(index).focus();
						}
					});
					return false;
				}
				if(bcdhs != 0){
					infos.id = hiddenid;
					infos.bcdhs = bcdhs;
					infos.bjid = bjid;
					infos.bjh = bjh;
					contractDetailParam.push(infos);
				}
			}else{
				var isdh =$("input[name='isdh']").eq(index).is(":checked"); //当前行，必检
				if(isdh){
					infos.id = hiddenid;
					infos.bjid = bjid;
					infos.bjh = bjh;
					infos.bcdhs = 1;
					contractDetailParam.push(infos);
				}
				
			}
		});
	}
	if(contractDetailParam.length == 0 && flag){
		if(type == 1){
			AlertUtil.showErrorMessage('请输入本次到货数量!');
		}else{
			AlertUtil.showErrorMessage('请选择到货航材!');
		}
		flag = false;
		return false;
	}
	return contractDetailParam;
}

//向table新增一行
function addRow(obj){
	
	var type = $("#htlx").val();
	var sl = formatUndefine(obj.SL);
	var dhsl = formatUndefine(obj.DHSL);
	wdhs = sl - dhsl;
	if(wdhs == 0){
		return false;
	}
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.ID+"'/>";
		tr += 	  "<input type='hidden' name='bjid' value='"+formatUndefine(obj.BJID)+"'/>";
		tr += 	  "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(obj.BJH)+"'/>";
		tr += 	  "<input type='hidden' name='wdhs' value='"+wdhs+"'/>";
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SQDH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.BJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.BJH)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.YWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.YWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.ZWMS)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ZWMS)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.CJJH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.CJJH)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.HCLX)+"</td>";
		if(type == 1){
			tr += "<td style='text-align:right;vertical-align:middle;'>"+sl+"</td>";
		}else{
			tr += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(obj.SN)+"</td>";
		}
		tr += "<td style='text-align:right;vertical-align:middle;'>"+dhsl+"</td>";
		tr += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(obj.RKSL)+"</td>";
		tr += "<td style='text-align:right;vertical-align:middle;'>"+wdhs+"</td>";
		
		tr += "<td>";
		if(type == 1){
			tr += "<input name='bcdhs' type='text' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' maxlength='12' />";
		}else{
			tr += "<input name='isdh' type='checkbox' style='width: 20px;height: 20px;' />";
		}
		tr += "</td>";
		
		tr += "</tr>";
		
	$("#materialTable").append(tr);
	
}

function showorhideColumn(){
	var type = $("#htlx").val();
	if(type == 1){
		$("#contractHead tr th:eq(1)").show();
		$("#contractHead tr th:eq(9)").show();
		$("#contractHead tr th:eq(2)").hide();
		$("#contractHead tr th:eq(8)").hide();
		$("#contractHead tr th:eq(13)").show();
		$("#contractHead tr th:eq(14)").hide();
	}else{
		$("#contractHead tr th:eq(1)").hide();
		$("#contractHead tr th:eq(9)").hide();
		$("#contractHead tr th:eq(2)").show();
		$("#contractHead tr th:eq(8)").show();
		$("#contractHead tr th:eq(13)").hide();
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
	 window.location.href =basePath+"/aerialmaterial/contract/main?pageParam="+pageParam;
}
