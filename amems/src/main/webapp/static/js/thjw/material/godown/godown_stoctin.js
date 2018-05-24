var orderNumber = 1;
var zts=["","保存","提交","核实","中止（关闭）","审核驳回","批准驳回","","作废","指定结束","完成"];
var gljbs=["","无","批次号管理","序列号管理"];
var rklx=["","采购","送修","借入","借出归还","其它"];
var hclxs=["其他","航材","设备","工具","危险品","低值易耗品"];
var certificateUtil = {};

$(function() {
	Navigation(menuCode,"入库","Warehousing");
	
	//入库类型
	$('#rklx').val(rklx[$('#rklx').val()]);
	//航材类型
	$('#hclx').val(hclxs[$('#hclx').val()]);
	
	//管理级别
	$('#gljb').val(gljbs[$('#gljb').val()]);
	
	//状态渲染
	$('#state').val(zts[$('#state').val()]);
	
	datepicker();
	inithcly();
	$("#hcly").val($("#hclyone").val());
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
           	tsn: {
        		validators: {
		            regexp: {
	                    regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
	                    message: 'tsn格式不正确'
	                }
                }
            }, 	
            tso: {
        		validators: {
		            regexp: {
	                    regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
	                    message: 'tsn格式不正确'
	                }
                }
            }, 
            csn: {
            	validators: {
            		regexp: {
            			regexp: /^[0-9]*$/,
            			message: '只能为正整数!'
            		}
            	}
            }, 
            cso: {
            	validators: {
            		regexp: {
            			regexp:/^[0-9]*$/,
            			message: '只能为正整数!'
            		}
            	}
            }
        }
    });
	initStoreSelect();
	if($("#ckhs").val()!=''){
		$("#ckh").val($("#ckhs").val());
	}
	initChecking();
	onchage();
});

function inithcly(){
	$("#hcly").empty();
//	$("#hcly").html("<option value=''>显示全部</option>");
	DicAndEnumUtil.registerDic('85',"hcly",$("#dprtcode").val());
}
/**
 * 初始化证书
 */
function initCertificate(type){
	var this_=this;
	certificateUtil = new CertificateUtil({
		parentId : "zsDiv",
		dprtcode : $("#dprtcode").val(),
		tbody : $("#replace_certificate_list"),
		container : "#zsDiv",
		ope_type : type
	});
}
/**
 * 加载证书
 */
function loadCertificate(){
	var this_=this;
	certificateUtil.load({
		bjh : $("#bjh").val(),
		xlh : $("#xlh").val(),
		pch : $("#pch").val(),
		dprtcode : $("#dprtcode").val(),
	});
}

/**
 * 是否航材检验
 * @returns
 */
function initChecking(){
	if($("#jydid").val()==""){
		$("#checkingId input").attr("disabled",false);
		$("#checkingId textarea").attr("disabled",false);
		$("#checkingId select").attr("disabled",false);
		 //初始化证书
	    initCertificate(1);
	}else{
		$("#checkingId input").attr("disabled",true);
		$("#checkingId textarea").attr("disabled",true);
		$("#checkingId select").attr("disabled",true);
		 //初始化证书
	    initCertificate(3);
	}
	//加载证书
	loadCertificate();
}	

/**
 * 航材成本
 * @returns
 */
function showContractCost(){
	var this_ = this;
	open_win_contract_cost_his.show({
		bjid: $("#bjid").val(),
		dprtcode:$("#dprtcode").val(),
		callback: function(data){//回调函数
			if(this_.operateType == "view"){
				return;
			}
			$("#kccb").val(data.htClf);
		}
	})
}	

/**
 * 选择用户
 * @returns
 */
function selectUser(){
	userModal.show({
		selected:$("#userid").val(),//原值，在弹窗中默认勾选
		dprtcode:$("#dprtcode").val(),
		callback: function(data){//回调函数
			$("#userid").val(formatUndefine(data.id));
			$("#username").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
			$("#rkbmid").val(StringUtil.escapeStr(data.bmdm));
			
			  $('#form').data('bootstrapValidator')  
		      .updateStatus('username', 'NOT_VALIDATED',null)  
		      .validateField('username');  
		}
	})
}	

//检验人
function openUserList() {
	 var dprtcode = $("#dprtcode").val();
	//调用用户选择弹窗
	userModal.show({
		selected:$("#jyrid").val(),//原值，在弹窗中默认勾选
		dprtcode:dprtcode,               //机构代码
		callback: function(data){//回调函数
			$("#jyrid").val(formatUndefine(data.id));
			$("#checker").val(formatUndefine(data.username) + " "+ formatUndefine(data.realname));
		},
    });
}


var storeHtml = "";
function initStoreSelect(){
	var cklbs=[0,4,5,6,7,8];
	var dprtcode =$("#dprtcode").val();
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckh+" "+row.ckmc)+"</option>";
		}
	});
	$("#ckh").html(storeHtml);
	
}


//渲染下拉单数据-库位
function renderSelect(){
	
		/*var storeId = $("#ckh").val();
		for (var i = 0; i < userStoreList.length; i++) {
			if(userStoreList[i].id == storeId){
				var kwOptionHtml = "";
				var storageList = userStoreList[i].storageList;
				for (var j = 0; j < storageList.length; j++) {
					
					$("#reserveTable").find("tr").each(function(){
						var index=$(this).index(); //当前行
						var kwh =$("input[name='kwhs']").eq(index).val();
						if(kwh==storageList[j].id){
							kwOptionHtml += "<option selected='selected' value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
						}else{
							kwOptionHtml += "<option  value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
						}
					});
				}
				$("select[name='kwh']").html(kwOptionHtml);
			}
		}*/
	
		/*
		 * bug:3186  
		 * author:hanwu
		 * date:20170624
		 */
		var ckid = $("#ckh").val();
		$("#reserveTable>tr").each(function(){
			var row = $(this); //当前行
			var kwid = row.find("input[name='kwhs']").val();
			var kwOptionHtml = "";
			$.each(userStoreList, function(index, row){
				if(row.id == ckid){
					for(var i = 0 ; i < row.storageList.length ; i++){
				 		var storage = row.storageList[i];
				 		if(kwid && storage.id == kwid){
				 			kwOptionHtml += '<option value="'+storage.id+'" selected="selected">'+StringUtil.escapeStr(storage.kwh)+'</option>';
				 		}else{
				 			kwOptionHtml += '<option value="'+storage.id+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
				 		}
				 	}
				}
			})
			row.find("select[name='kwh']").html(kwOptionHtml);
		});
}

//渲染下拉单数据-库位
function onchage(){
	renderSelect();
}


function add(){
	var htmlContent = "";
	htmlContent += "<tr   style=\"cursor:pointer\"  >";
	htmlContent += "<td style='vertical-align: middle; ' class='text-center'><div>";
	htmlContent += "<i class='icon-trash color-blue cursor-pointer'  onClick=\"remove('', event)\" title='删除 Delete'></i>";
	htmlContent += "<input type='hidden' name='bjid' value='"+this.bjid+"'/>";
	htmlContent += "</div></td>";
	htmlContent += "<td style='vertical-align: middle; ' align='center'><select class='form-control' name='kwh'></select></td>";  
	if($("#gljbbase").val() == "2"){
		htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='pch' value='"+$("#pchs").val()+"' readonly></td>";  
	}else{
		htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='sn' value='' readonly></td>";  
	}
	htmlContent += "<td style='vertical-align: middle; ' align='center'><input type='text' class='form-control' name='kcsl' value='' onkeyup='clearNoNum(this);'></td>";
	htmlContent += "</tr>";  
	$("#reserveTable").append(htmlContent);
	renderSelect();
}

function remove(id, e){
	if($("#reserveTable").find("tr").length == 1){
		AlertUtil.showMessage("至少要保留一条入库记录");
		return false;
	}
	if(id == ""){
		$(e.target).parent().parent().parent().remove();
		return;
	}
}


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

$('#hjsm').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#form').data('bootstrapValidator')  
	.updateStatus('hjsm', 'NOT_VALIDATED',null)  
	.validateField('hjsm');  
});

$('#scrq').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#form').data('bootstrapValidator')  
	.updateStatus('scrq', 'NOT_VALIDATED',null)  
	.validateField('scrq');  
});

$('#jyrq').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#form').data('bootstrapValidator')  
	.updateStatus('jyrq', 'NOT_VALIDATED',null)  
	.validateField('jyrq');  
});

//保存
function save(){
	var url = basePath+"/aerialmaterial/godown/editSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submit(){
	var url = basePath+"/aerialmaterial/godown/editSubmit";
	var message = "提交成功!";
	performAction(url,message);
}

var num=0;
//获取入库数据
function getgodownEntryDetailListParam(){
	var kwhList=[];
	flag = true;
	num=0;
	var reserveDetailParam = [];
		$("#reserveTable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var kwh =$("select[name='kwh']").eq(index).val();
			if($.inArray(kwh, kwhList)!=-1){
				flag = false;
				AlertUtil.showErrorMessage("入库库位不能相同!");
			}else{
				kwhList.push(kwh);
			}
			var kcllid = $("input[name='kcllid']").eq(index).val();
			var ckh = $.trim($("#ckh").val());
			
			var godownEntryDetailsId =$("input[name='godownEntryDetailsId']").eq(index).val();
			var kcsl =$("input[name='kcsl']").eq(index).val();
			
			if(null == ckh || "" == ckh){
				flag = false;
				AlertUtil.showErrorMessage("请选择仓库！");
			    return false;
			}
			
			if(null == kwh || "" == kwh){
				flag = false;
				AlertUtil.showErrorMessage("请选择库位！");
			    return false;
			}
			
			if(null == kcsl || "" == kcsl || kcsl <= 0){
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '请输入入库数量!',
					callback : function(option){
						$("input[name='kcsl']").eq(index).focus();
					}
				});
				return false;
			}
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			if (!regu.test(kcsl)) {
				flag = false;
				AlertUtil.showMessageCallBack({
					message : '入库数量只能输入数字,并保留两位小数！',
					callback : function(option){
						$("input[name='kcsl']").eq(index).focus();
					}
				});
		        return false;
		    }
			var infos1 ={};
			infos.id = godownEntryDetailsId;
			if($("#gljbbase").val()==3){
				var sn =$("input[name='sn']").eq(index).val();
				infos1.sn = sn;
			}
			else if($("#gljbbase").val()==2){
				var pch =$("input[name='pch']").eq(index).val();
				infos1.pch = pch;
			}
			
			var kccb = $.trim($("#kccb").val());
			
			var bjh = $.trim($("#bjh").val());
			var hcly = $.trim($("#hcly").val());
			var grn = $.trim($("#grn").val());
			var scrq = $.trim($("#scrq").val());
			var hjsm = $.trim($("#hjsm").val());
			var tsn = $.trim($("#tsn").val());
			var csn = $.trim($("#csn").val());
			var tso = $.trim($("#tso").val());
			var cso = $.trim($("#cso").val());
			var cfyq = $.trim($("#cfyq").val());
			var jybz = $.trim($("#jybz").val());
			infos1.kccb = kccb;
			infos1.id = kcllid;
			
			infos1.hcly = hcly;
			if($("#jydid").val()!=""){
				infos1.grn = "#_#";
			}else{
				infos1.grn = grn;
			}
			infos1.bjh = bjh;
			infos1.dprtcode = $.trim($("#dprtcode").val());
			infos1.scrq = scrq;
			infos1.hjsm = hjsm;
			infos1.tsn = tsn;
			infos1.csn = csn;
			infos1.tso = tso;
			infos1.cso = cso;
			infos1.cfyq = cfyq;
			infos1.bz = jybz;
			infos1.ckid = ckh;
			infos1.kwid = kwh;
			infos1.kcsl = kcsl;
			infos1.certificates = certificateUtil.getData(); 	 //获取证书信息
			infos.materialHistory = infos1;
			num=parseInt(num)+parseInt(kcsl);
			reserveDetailParam.push(infos);
			
			
		});
		
	
	return reserveDetailParam;
}

//获取库存数据
function materialHistoryParam(){

	

	return reserveDetailParam;
}

function performAction(url,message){
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	}
	
	/*if($("#gljb").val()==3){
		if($("#shzh").val()==""){
			AlertUtil.showErrorMessage("当航材管理级别为序列号时适航证号不能为空!");
			return false;
		}
	}*/
	
	var id = $.trim($("#id").val());
	
	var dprtcode = $.trim($("#dprtcode").val());
	var cklb = $.trim($("#cklb").val());
	var bjid = $.trim($("#bjid").val());
	var bjh = $.trim($("#bjh").val());
	
	var zwms = $.trim($("#zwms").val());
	var ywms = $.trim($("#ywms").val());
	var jldw = $.trim($("#jldw").val());
	
	var jydid = $.trim($("#jydid").val());
	var gg = $.trim($("#gg").val());
	var xh = $.trim($("#xh").val());
	
	var rukid = $.trim($("#userid").val());
	var rkbmid = $.trim($("#rkbmid").val());
	var rkrq = $.trim($("#rkrq").val());
	var bz = $.trim($("#bz").val());
	var fhdw = $.trim($("#fhdw").val());
	
	var reserve = {
					id : id,
					fhdw : fhdw,
					rukid : rukid,
					rkbmid : rkbmid,
					rkrq : rkrq,
					bz : bz,
					dprtcode : dprtcode
					};

	reserve.paramsMap ={ 
						cklb:cklb,
						 bjid:bjid,
						 bjh:bjh,
						 zwms:zwms,
						 ywms:ywms,
						 jldw:jldw,
						 jydid:jydid,
						 gg:gg,
						 xh:xh
		   				};
	
	reserve.godownEntryDetailList = getgodownEntryDetailListParam();
	
	if(!flag){
		return false;
	}
	
	if(parseFloat(num)>parseFloat($("#rksl").val())||parseFloat(num)<parseFloat($("#rksl").val())){
		AlertUtil.showErrorMessage("输入的数量之和必须等于入库数量!");
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
			AlertUtil.showMessage(message,'/aerialmaterial/godown/main');
		}
	});
}

//返回
function goreturn(){
	 window.location.href =basePath+"/aerialmaterial/godown/main?pageParam="+pageParam;
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
   	
   	if(obj.value.indexOf(":") == -1 && obj.value.indexOf(".") == -1 && obj.value.length > 8){
   		obj.value = obj.value.substring(0,8);
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
   	 if(str[0].length > 8){
   		value = str[0].substring(0,8) +"." + str[1];
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

