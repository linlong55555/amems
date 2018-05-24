var delAttachements = [];

$(function(){
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	Navigation(menuCode,"查看航材","Checking Material");
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
    });
	var certificateUtil = {};
	loadAttachments();
	// 初始化证书
	initCertificate();
	// 加载证书
	loadCertificate();
	
	// 加载维修项目
	loadMaintenanceProject();
	
	if(kysl!=''&&$("#gljb").val()==3){
		var kysl=$("#kysl").val();
		var sl=$("#sl").val();
		if(sl.indexOf(".")){
			sl=sl.split(".")[0];
			$("#sl").val(sl);
		}
		if(kysl.indexOf(".")){
			kysl=kysl.split(".")[0];
			$("#kysl").val(kysl);
		}
	}
	
	var checker=$.trim($("#checker").val());
	if(checker==null||checker==""){
		var ddd=$("#dqdlr").val();
		$("#checker").val(ddd);
	}
	if($("#jyrq").val()==null||$("#jyrq").val()==""){
		TimeUtil.getCurrentDate("#jyrq");                          //取得检验日期的当前时间
	}
	
	if($("#jyjg").val()==2||$("#jyjg").val()==3){
		$("#jgsm_div").show();
	}
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xkzh: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                }
            },
            shzh: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                }
            },
            shzwz: {
                validators: {
                }
            },
            tsn: {
                validators: {
                	regexp: {
                        regexp: /^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
                        message: '输入的格式不正确'
                    },
                }
            }, 
            tso: {
                validators: {
                	regexp: {
                        regexp:/^[0-9]+((\:|\.)[0-5]?[0-9])?$/,
                        message: '输入的格式不正确'
                    },
                }
            },
            cfyq: {
                validators: {
                }
            },
            bz: {
                validators: {
                }
            },
            jgsm: {
                validators: {
                	notEmpty: {
                        message: '检验结果说明不能为空！'
                    },
                }
            }
        }
    });
	
});

//加载维修项目
function loadMaintenanceProject(){
	maintenance_project_view.init({
		bjh : $("#bjh").val(),
		dprtcode:$("#dprtcode").val()//当前机构代码
	});
}

//初始化证书消息框
function initCertificate(){
	certificateUtil = new CertificateUtil({
		parentId : "open_win_installationlist_replace",
		dprtcode : $("#dprtcode").val(),
		tbody : $("#replace_certificate_list"),
		container : "#open_win_installationlist_replace",
		ope_type : 3
	});
	 
	 
}

// 加载证书
function loadCertificate(){
	console.info($("#bjh").val(),$("#xlh").val(),$("#pch").val(),$("#dprtcode").val());
	certificateUtil.load({
		bjh : $("#bjh").val(),
		xlh : $("#xlh").val(),
		pch : $("#pch").val(),
		dprtcode : $("#dprtcode").val(),
	});
}
//打开调整列表对话框
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



//检验单保存提交
function submitSave(){
	var zt=2;
	validateInsert(zt);
}
function checkSave(){
	var zt=1;
	validateInsert(zt);
}
function validateInsert(zt){
    $('#form').data('bootstrapValidator').validate();
    if(!$('#form').data('bootstrapValidator').isValid()){
	  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
	return false;
    }
    var attachments = [];
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments.push({
				'yswjm' : responses[i].attachments[0].yswjm,
				'wbwjm' : responses[i].attachments[0].wbwjm,
				'hzm' : responses[i].attachments[0].hzm,
				'nbwjm' : responses[i].attachments[0].nbwjm,
				'wjdx' : responses[i].attachments[0].wjdx,
				'cflj' : responses[i].attachments[0].cflj,
				'id' : responses[i].attachments[0].id,
				'operate' : responses[i].attachments[0].operate

			});
		}
	}
	var dellen = delAttachements.length;
	if (dellen != undefined && dellen > 0) {
		for (var i = 0; i < dellen; i++) {
			attachments.push({
				'id' : delAttachements[i].id,
				'operate' : 'DEL'

			});
		}
	}
    
    var param={};
    param.attachments = attachments;
    param.id=$.trim($('#id').val()); 
    param.kysl=$.trim($('#kysl').val()); 
    param.hcly=$.trim($('#hcly').val());  
    param.cfyq=$.trim($('#cfyq').val());  
    param.xkzh=$.trim($('#xkzh').val());  
    param.shzh=$.trim($('#shzh').val());  
    param.shzwz=$.trim($('#shzwz').val());  
    param.tsn=$.trim($('#tsn').val());  
    param.tso=$.trim($('#tso').val());  
    param.bz=$.trim($('#bz').val());
    param.scrq=$.trim($("#scrq").val());
    param.zzcs=$.trim($('#zzcs').val()); 
    param.hjsm=$.trim($('#hjsm').val()); 
//    param.jyr=$.trim( $('#checker3').val());  
    param.jyrid=$.trim($('#jyrid').val()); 
    param.jyrq=$.trim($('#jyrq').val());  
    param.jyjg=$.trim($('#jyjg').val());  
    param.jgsm=$.trim($('#jgsm').val());
    param.zdrid=$("#zdrid").val();
    param.zdbmid=$("#zdbmid").val();
    param.dprtcode=$("#dprtcode").val();
    param.zt=1;
    if(zt==2&&$("#jyjg").val()!=2){
    	param.zt=2;
    	var materialHistory={};
    	materialHistory.kcid=$("#kcid").val();
    	materialHistory.dprtcode=$("#dprtcode").val();
    	materialHistory.bjid=$("#bjid").val();
    	materialHistory.bjh=$("#bjh").val();
    	materialHistory.pch=$("#pch").val();
    	materialHistory.sn=$("#xlh").val();
    	materialHistory.zwms=$("#zwms").val();
    	materialHistory.ywms=$("#ywms").val();
    	materialHistory.jldw=$("#jldw").val();
    	materialHistory.kcsl=$("#kysl").val();
    	materialHistory.djsl=0;
    	materialHistory.xkzh=$("#xkzh").val();
    	materialHistory.shzh=$("#shzh").val();
    	materialHistory.shzwz=$("#shzwz").val();
    	materialHistory.zt=2;
    	materialHistory.bz=$("#bz").val();
    	materialHistory.jydid=$("#id").val();
    	materialHistory.hjsm=$("#hjsm").val();
    	materialHistory.scrq=$("#scrq").val();
    	materialHistory.xh=$("#xh").val();
    	materialHistory.gg=$("#gg").val();
    	materialHistory.zzcs=$("#zzcs").val();
    	materialHistory.tsn=$("#tsn").val();
    	materialHistory.tso=$("#tso").val();
    	materialHistory.hcly=$("#hcly").val();
    	materialHistory.cfyq=$("#cfyq").val();
    	param.materialHistory=materialHistory;
    	var godownEntry={};
    	godownEntry.dprtcode=$("#dprtcode").val();
    	godownEntry.rklx=$("#shlx").val();
    	godownEntry.zt=zt;
    	godownEntry.bz=$("#bz").val();
    	godownEntry.xgdjid=$("#xgdjid").val();
    	godownEntry.jydid=$("#id").val();
    	godownEntry.shdid=$("#shdid").val();
    	godownEntry.shdmxid=$("#shdmxid").val();
    	godownEntry.zdbmid=$("#zdbmid").val();
    	godownEntry.zdrid=$("#zdrid").val();
    	param.godownEntry=godownEntry;   	
    }
	if(parseFloat($("#kysl").val())>parseFloat($("#sl").val())){
		AlertUtil.showErrorMessage("可用数量不能大于收货数量！");
		return false;
	}
    if(zt==2){
    	if(jyrid==""||jyrid==null||jyrq==""||jyrq==null){
    		AlertUtil.showErrorMessage("检验人和检验日期不能为空！");
    		return false;
    	}
    	if(jyjg==0){
    		AlertUtil.showErrorMessage("请选择检验结果！");
    		return false;
    	}
	} 
	if($('#gljb').val()==3){
		if(shzh==null||shzh==""){
			AlertUtil.showErrorMessage("序列号管理时，适航证号不能为空！");
    		return false;
		}			
		var reg=/^[1-9]\d*$/;
			if(reg.test($("#kysl").val())==false){
				AlertUtil.showErrorMessage("可用数请填写整数！");
				return false;
		}
	}
	
	if(!shzh){
		AlertUtil.showConfirmMessage("适航证号为空，确定要继续吗？",{callback: function(){
			editCheckList();
		}});	
	}else{
		editCheckList();
	}
	
	function editCheckList(){
		AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/inspection/edit",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				if(data>0){
					AlertUtil.showMessage('操作成功!','/aerialmaterial/inspection/main?id='+id+'&pageParam='+pageParam);
				}else{
					finishWait();
					AlertUtil.showMessage('该航材状态已更新，请刷新后再进行操作!','/aerialmaterial/inspection/main?id='+id+'&pageParam='+pageParam);
				}
			},
		});
	}
}

var id=$("#id").val();          
/**
 * 加载航材检验的附件
 */
function loadAttachments(){
	AjaxUtil
	.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		data : {
			mainid : $("#id").val()
		},
		success : function(data) {
			if (data.success) {
				var attachments = data.attachments;
				var len = data.attachments.length;
				if (len > 0) {
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml
								+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ attachments[i].uuid + '\' key=\''
								+ attachments[i].id + '\' >';
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)
								+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left">'
								+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml = trHtml + '<td>'
								+ attachments[i].czsj + '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath
								+ '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#filelist').append(trHtml);
				}else{
					$("#file-div").hide();
				}
			}
		}
	});
}
var uploadObj = $("#fileuploader")
.uploadFile(
		{
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 10,
			formData : {
				"fileType" : "planefaultmonitoring",
				"wbwjm" : function() {
					return $('#wbwjm').val()
				}
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,				
			showStatusAfterError: false,
			uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
			uploadButtonClass : "ajax-file-upload_ext",
			onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
			{
				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
						+ data.attachments[0].uuid + '\'>';
				trHtml = trHtml + '<td><div>';
				trHtml = trHtml
						+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
						+ data.attachments[0].uuid
						+ '\')" title="删除 Delete">  ';
				trHtml = trHtml + '</div></td>';
				trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+"."+StringUtil.escapeStr(data.attachments[0].hzm)+'" class="text-left">'
						+ StringUtil.escapeStr(data.attachments[0].wbwjm) + "."+StringUtil.escapeStr(data.attachments[0].hzm)+'</td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].wjdxStr + ' </td>';
				trHtml = trHtml + '<td class="text-left">'
						+ data.attachments[0].user.username + " "
						+ data.attachments[0].user.realname + '</td>';
				trHtml = trHtml + '<td>' + data.attachments[0].czsj
						+ '</td>';
				trHtml = trHtml
						+ '<input type="hidden" name="relativePath" value=\''
						+ data.attachments[0].relativePath + '\'/>';

				trHtml = trHtml + '</tr>';
				$('#filelist').append(trHtml);
			}
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjm').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}

		});
/**
* 添加时删除附件
* 
* @param uuid
*/
function delfile(uuid) {
var responses = uploadObj.responses;
var len = uploadObj.responses.length;
var fileArray = [];
var waitDelArray = [];
if (len > 0) {
for (var i = 0; i < len; i++) {
	if (responses[i].attachments[0].uuid != uuid) {
		fileArray.push(responses[i]);
	}
}
uploadObj.responses = fileArray;
uploadObj.selectedFiles -= 1;
$('#' + uuid).remove();
}
}

function deletefile(uuid) {
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		var waitDelArray = [];
		if (len > 0) {
			for (var i = 0; i < len; i++) {
				if (responses[i].attachments[0].uuid != uuid) {
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
		}
		$('#' + uuid).remove();

	} else {
		$('#' + uuid).remove();
		delAttachements.push({
			id : key
		});
	}
}
/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function back(){
	 window.location.href =basePath+"/aerialmaterial/inspection/main?pageParam="+pageParam;
}
