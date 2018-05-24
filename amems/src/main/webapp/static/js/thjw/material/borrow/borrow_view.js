var gljb=["","无","批次号管理","序列号管理"];
var zts=["","保存","提交","核实","中止（关闭）","审核驳回","批准驳回","","作废","指定结束","完成"];
var orderNumber = 1;
var flag = true;

$(function() {
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				
			}
		}
	});
	
	//状态渲染
	var ztnum=$('#state').val();
	var zttext=zts[ztnum];
	$('#state').val(zttext);
	Navigation(menuCode,"查看借入申请","View Borrow Apply");
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
            }  ,
            fjzch: {
            	validators: {
            		notEmpty: {
            			message: '请选飞机注册号'
            		}
            	}
                }  
        }
    });
	changeSelected();
	
	initReserveDetail();
	changeOrganization();
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '<option value="00000" >通用Currency</option>';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch").html(planeRegOption);
	$("#fjzch").val($("#fjzchs").val());
}

//调用借调对象
function changeSelected(){
	var jddxms= $("#jddxms").val();
	var fixedCheckItem = {
			'jddxlx' :$("#jddxlx").val(),
			'dprtcode':$("#dprtcode").val()
		};
	if($("#jddxlx").val()!=""){
		AjaxUtil.ajax({
			url : basePath+"/aerialmaterial/secondment/list/type",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
					 $("#jddx").empty();
					   var htmlContent = '';
					   htmlContent +="<option value=''>请选择</option>";
					if(data!=null){
							for (var i = 0; i < data.length; i++) {  
								if(jddxms==data[i].jddxms){
									
									htmlContent +="	<option value='"+data[i].id+"' selected=true>"+StringUtil.escapeStr(data[i].jddxms)+"</option>";
								}else{
									
									htmlContent +="	<option value='"+data[i].id+"' >"+StringUtil.escapeStr(data[i].jddxms)+"</option>";
								}
							}
					}
					$("#jddx").html(htmlContent);
			}
		});
		
	}else{
		 var htmlContent1 = '';
		htmlContent1 +="<option value=''>请选择</option>";
		$("#jddx").html(htmlContent1);
	}
}

//初始化提订航材
function initReserveDetail(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/aerialmaterial/borrow/queryDetailListByMainId",
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
		row.sn = reserve.sn;
		row.sqsl = reserve.sqsl;
		row.yt = reserve.yt;
		row.detailId = reserve.id;
		row.hiddenid = reserve.bjid;
		addRow(row);
	});
	
}

//保存
function save(){
	var url = basePath+"/aerialmaterial/borrow/editSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/borrow/editSubmit";
	var message = "提交成功!";
	performAction(url,message);
}

function performAction(url,message){
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	}
	var id = $.trim($("#id").val());
	var sqsj = $.trim($("#sqsj").val());
	var fjzch = $.trim($("#fjzch").val());
	var jddxid = $.trim($("#jddx").val());
	var bz = $.trim($("#bz").val());
	
	var reserve = {
			id : id,
			sqsj : sqsj,
			fjzch : fjzch,
			jddxid : jddxid,
			bz : bz
		};

	reserve.reserveDetailList = getReserveDetailParam();
	
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
			AlertUtil.showMessage(message,'/aerialmaterial/borrow/main');
		}
	});
}

//返回
function back(){
	 window.location.href =basePath+"/aerialmaterial/borrow/main?pageParam="+pageParam;
}

//获取提订航材参数
function getReserveDetailParam(){

	flag = true;
	var reserveDetailParam = [];
	
	var len = $("#reserveTable").find("tr").length;
	if (len == 1) {
		if($("#reserveTable").find("td").length ==1){
			flag = false;
			AlertUtil.showErrorMessage("请选择借入航材!");
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
				$("input[name='sqsl']").eq(index).focus();
				AlertUtil.showErrorMessage("请输入借入数量!");
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




//向table新增一行
function addRow(obj){
	var tr = "";
		tr += "<tr>";
		
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.hiddenid+"'/>";
		tr += 	  "<input type='hidden' name='ids' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='detailId' value='"+obj.detailId+"'/>";
		tr += 	  "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(obj.bjh)+"'/>";
		tr += "</td>";
		
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.ywms)+"'>"+StringUtil.escapeStr(obj.ywms)+"</td>";
		tr +=  "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zwms)+"'>"+StringUtil.escapeStr(obj.zwms)+"</td>";
		
		tr +=  "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cjjh)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',obj.hclx) +"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(gljb[obj.gljb]) +"</td>";
		tr += "<td class='text-left' style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sn)+"'>"+StringUtil.escapeStr(obj.sn)+"</td>"; 
		tr += "<td class='text-left' style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pch)+"'>"+StringUtil.escapeStr(obj.pch)+"</td>";
		tr += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(obj.sqsl)+"</td>";
		tr += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.yt)+"'>"+StringUtil.escapeStr(obj.yt)+"</td>";
		tr += "</tr>";
	
	$("#reserveTable").append(tr);
}

//移除一行
function removeCol(e){
	$(e.target).parent().parent().remove();
	resXh();
	orderNumber--;
	var len = $("#reserveTable").find("tr").length;
	if(len < 1){
		$("#reserveTable").append("<tr><td  colspan='12'  class='text-center'>暂无数据 No data.</td></tr>");
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
  
	strs=obj.value.split("."); //字符分割
	if(strs.length>1){
		if(strs[1]>99){
			obj.value=strs[0]+'.'+strs[1].substr(0, 2);
		}
	}
     
}
