var certificateUtil = {};
testing_open_alert = {
	id : "testing_open_alert",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    // 初始化错误信息 
		 this_.EmptiedData();   				// 清空数据
		 this_.validation(); 					// 初始化验证
		 this_.inithcly();
		 this_.initDate(param);					// 初始化信息
		 this_.initCertificate();				// 初始化证书
		 this_.loadCertificate();				// 加载证书
		 this_.bindEvent();
	},
	//初始化航材来源
	inithcly: function(){
		var this_ = this;
		$("#"+this_.id+"_hcly").empty();
		DicAndEnumUtil.registerDic('85','testing_open_alert_hcly',$("#"+this_.id+"_dprtcode").val());
	},
	/**
	 * 跳转到收货单
	 */
	onclickshd : function(){
	var shdid=$("#"+this.id+"_shdid").val();
	window.open(basePath+"/material/outin/receipt?id="+shdid+"&type=view");

	},
	/**
	 * 初始化证书
	 */
	initCertificate : function(){
		var this_=this;
		certificateUtil = new CertificateUtil({
			parentId : this_.id,
			dprtcode : $("#"+this_.id+"_dprtcode").val(),
			tbody : $("#replace_certificate_list"),
			container : "#"+this_.id,
			ope_type : 1
		});
	},
	/**
	 * 加载证书
	 */
	loadCertificate : function(){
		var this_=this;
		certificateUtil.load({
			bjh : $("#"+this_.id+"_bjh").val(),
			xlh : $("#"+this_.id+"_sn").val(),
			pch : $("#"+this_.id+"_pch").val(),
			dprtcode : $("#"+this_.id+"_dprtcode").val(),
		});
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		var this_=this;
		$("#"+this_.id+"_id").val("");
		$("#"+this_.id+"_dprtcode").val("");
		$("#"+this_.id+"_jydh").val("");
		$("#"+this_.id+"_hclx").val("");
		$("#"+this_.id+"_bjh").val("");
		$("#"+this_.id+"_bjmc").val("");
		$("#"+this_.id+"_gljb").val("");
		$("#"+this_.id+"_gljbzt").val("");
		$("#"+this_.id+"_sn").val("");
		$("#"+this_.id+"_pch").val("");
		$("#"+this_.id+"_kysl").val("");
		$("#"+this_.id+"_shdid").val("");
		$("#"+this_.id+"_shdh").text("");
		$("#"+this_.id+"_lydw").val("");
		$("#"+this_.id+"_shrq").val("");
		$("#"+this_.id+"_wz").val("");
		$("#"+this_.id+"_grn").val("");
		$("#"+this_.id+"_scrq").val("");
		$("#"+this_.id+"_scrq").datepicker("update");
		$("#"+this_.id+"_hjsm").val("");
		$("#"+this_.id+"_hjsm").datepicker("update");
		$("#"+this_.id+"_tsn").val("");
		$("#"+this_.id+"_csn").val("");
		$("#"+this_.id+"_tso").val("");
		$("#"+this_.id+"_cso").val("");
		$("#"+this_.id+"_cfyq").val("");
		$("#"+this_.id+"_bz").val("");
		$("#"+this_.id+"_jyrq").val("");
		$("#"+this_.id+"_jyrq").datepicker("update");
		 var SelectArr1 = $("#jyjgDiv select"); //检验结果重置为第一个
		 SelectArr1[0].options[0].selected = true;
		$("#"+this_.id+"_jgsm").val("");
	},
	loadMaintenanceProject : function(param){//加载维修项目
		maintenance_project_view.init({
			bjh : param.paramsMap?param.paramsMap.bjh:'',
			dprtcode:param.dprtcode//当前机构代码
		});
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit1');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"inspection"
		});//显示附件
	},
	//验证t
	bindEvent: function(){
		var this_=this;
		$("#testing_open_alert .time-masked").on("keyup", function(){
			// 回退循环
			this_.validateDateTime($(this));
		});	
	},
	//验证t
	validateDateTime: function($obj){
		this.backspace($obj, /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/, true);
	},
	/**
	 * 字段验证
	 */ 
	validation : function(){
		validatorForm = $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            tsn: {
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/,
                        message: '输入的格式不正确!'
                    }
                }
            }, 
            tso: {
                validators: {
                	regexp: {
                        regexp:/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/,
                        message: '输入的格式不正确!'
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
            }, 
            jgsm: {
                validators: {
                	notEmpty: {
                        message: '结果说明不能为空!'
                    },
                }
            },
            hcly: {
            	validators: {
            		notEmpty: {
            			message: '部件来源不能为空!'
            		},
            	}
            },
            jyjg: {
            	validators: {
            		notEmpty: {
            			message: '检验结果不能为空!'
            		},
            	}
            },
            jyrq: {
                validators: {
                	notEmpty: {
                        message: '检验日期不能为空!'
                    }
                }
            }
        }
    });
	},
	/**
	 * 加载数据
	 */
	initDate : function(param){
		var falg=true;
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#"+this_.id));
	   	AjaxUtil.ajax({
	 		url:basePath + "/material/inspection/getByInspectionId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#"+this_.id),
	 		success:function(data){
	 			finishWait($("#"+this_.id));
	 			if(data!=null){
	 				if(data.zt == 2 ){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					falg=false;
	 					return false;
	 				}
	 			  	if(falg){
	 			   		$("#"+this_.id+"").modal("show");//显示窗口
	 			   	}
	 				this_.setDate(data);// 加载数据
				};
	 		}
		});
	},
	/**
	 * 加载数据
	 */
	setDate : function(data){
		var this_=this;
		$("#"+this_.id+"_id").val(data.id);
		$("#"+this_.id+"_jydh").val(data.jydh);
		$("#"+this_.id+"_dprtcode").val(data.dprtcode);
		$("#"+this_.id+"_jydh").val(data.jydh);
		$("#"+this_.id+"_hclx").val(DicAndEnumUtil.getEnumName('materialTypeEnum',data.paramsMap?data.paramsMap.hclx:''));
		$("#"+this_.id+"_bjh").val(data.paramsMap?data.paramsMap.bjh:'');
		$("#"+this_.id+"_bjmc").val(data.paramsMap?data.paramsMap.bjmc:'');
		$("#"+this_.id+"_gljb").val(DicAndEnumUtil.getEnumName('supervisoryLevelEnum', data.paramsMap?data.paramsMap.gljb:''));
		$("#"+this_.id+"_gljbzt").val(data.paramsMap?data.paramsMap.gljb:'');
		$("#"+this_.id+"_sn").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.sn:''));
		$("#"+this_.id+"_pch").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.pch:''));
		$("#"+this_.id+"_kysldw").val(StringUtil.escapeStr(data.kysl)+" "+StringUtil.escapeStr(data.hcMainData.jldw));
		$("#"+this_.id+"_kysl").val(StringUtil.escapeStr(data.kysl));
		$("#"+this_.id+"_shdid").val(data.shdid);
		$("#"+this_.id+"_shdh").text(data.paramsMap?data.paramsMap.shdh:'');
		$("#"+this_.id+"_lydw").val(data.paramsMap?data.paramsMap.lydw:'');
		$("#"+this_.id+"_shrq").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.shrq:'').substring(0,10));
		$("#"+this_.id+"_wz").val(data.paramsMap?data.paramsMap.wz:'');
		if(data.hcly==null || data.hcly==''){
//			 var SelectArr1 = $("#hclyDiv select"); //部件来源重置为第一个
//			 SelectArr1[0].options[0].selected = true;
		}else{
			$("#"+this_.id+"_hcly").val(data.hcly);
		}
		$("#"+this_.id+"_grn").val(data.grn);
		$("#"+this_.id+"_scrq").val(StringUtil.escapeStr(data.scrq).substring(0,10));
		$("#"+this_.id+"_scrq").datepicker("update");
		$("#"+this_.id+"_hjsm").val(StringUtil.escapeStr(data.hjsm).substring(0,10));
		$("#"+this_.id+"_hjsm").datepicker("update");
		$("#"+this_.id+"_tsn").val(TimeUtil.convertToHour(data.tsn, TimeUtil.Separator.COLON)||"");
		$("#"+this_.id+"_csn").val(data.csn);
		$("#"+this_.id+"_tso").val(TimeUtil.convertToHour(data.tso, TimeUtil.Separator.COLON)||"");
		$("#"+this_.id+"_cso").val(data.cso);
		$("#"+this_.id+"_cfyq").val(data.cfyq);
		$("#"+this_.id+"_bz").val(data.bz);
		$("#"+this_.id+"_jyrq").val(StringUtil.escapeStr(data.jyrq).substring(0,10));
		$("#"+this_.id+"_jyrq").datepicker("update");
		if(data.jyjg==null || data.jyjg==''){
			 var SelectArr1 = $("#jyjgDiv select"); //检验结果重置为第一个
			 SelectArr1[0].options[0].selected = true;
		}else{
			$("#"+this_.id+"_jyjg").val(data.jyjg);
		}
		$("#"+this_.id+"_jgsm").val(data.jgsm);

		this_.loadMaintenanceProject(data);
		this_.getAttachments(data.id,true,true);	//加载附件
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var param = {};
		$('#form1').data('bootstrapValidator').validate();
		if (!$('#form1').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#"+this_.id+"_djzt").val(); 	//old状态
		paramsMap.gljb = $("#"+this_.id+"_gljbzt").val(); 	//管理级别
		paramsMap.bjh = $("#"+this_.id+"_bjh").val(); 	//管理级别
		paramsMap.sn = $("#"+this_.id+"_sn").val(); 	//管理级别
		paramsMap.pch = $("#"+this_.id+"_pch").val(); 	//管理级别
		param.paramsMap = paramsMap;
		
		param.id=$("#"+this_.id+"_id").val();
		param.jydh=$("#"+this_.id+"_jydh").val();
	    param.hcly=$.trim($("#"+this_.id+"_hcly").val());  
	    param.grn=$.trim($("#"+this_.id+"_grn").val());  
	    param.scrq=$.trim($("#"+this_.id+"_scrq").val());
	    param.hjsm=$.trim($("#"+this_.id+"_hjsm").val());  
	    console.info($("#"+this_.id+"_tsn").val());
	    param.tsn=$.trim(TimeUtil.convertToMinute($("#"+this_.id+"_tsn").val()) || null );  
	    param.csn=$.trim($("#"+this_.id+"_csn").val());  
	    param.tso=$.trim(TimeUtil.convertToMinute($("#"+this_.id+"_tso").val()) || null ); 
	    param.cso=$.trim($("#"+this_.id+"_cso").val());  
	    param.cfyq=$.trim($("#"+this_.id+"_cfyq").val());  
	    param.bz=$.trim($("#"+this_.id+"_bz").val());
	    param.jyrq=$.trim($("#"+this_.id+"_jyrq").val());  
	    param.jyjg=$.trim($("#"+this_.id+"_jyjg").val());  
	    param.jgsm=$.trim($("#"+this_.id+"_jgsm").val());
	    param.dprtcode=$.trim($("#"+this_.id+"_dprtcode").val());
	    param.kysl=$.trim($("#"+this_.id+"_kysl").val());
	    
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents("attachments_list_edit1");//获取附件数据
		param.attachmentList=attachmentsObj.getAttachments();//加载附件数据
	    param.certificates = certificateUtil.getData(); 	 //获取证书信息
	return param;
	},
	/**
	 * 保存
	 */
	save : function(){
		var this_=this;
		var obj=this.getData();
	
		if(obj==false){
			return false;
		}
		obj.zt="1";
		startWait($("#"+this_.id));
	   	AjaxUtil.ajax({
	 		url:basePath+"/material/inspection/update",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#"+this_.id),
	 		success:function(data){
	 			id = data;	
	 			pageParam=testing.encodePageParam();
	 			finishWait($("#"+this_.id));
	 			AlertUtil.showMessage("保存成功!");
	 			$("#"+this_.id).modal("hide");
	 			testing.decodePageParam();
	 		}
	   	 });
	},
	/**
	 * 提交
	 */
	submit : function(){
		var this_=this;
		var obj=this_.getData();
		console.info(obj);
		if(obj==false){
			return false;
		}
		obj.zt="2";
//		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		startWait($("#"+this_.id));
	   	AjaxUtil.ajax({
	 		url:basePath+"/material/inspection/submit",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#"+this_.id),
	 		success:function(data){
	 			id = data;	
	 			pageParam=testing.encodePageParam();
	 			finishWait($("#"+this_.id));
	 			AlertUtil.showMessage("提交成功!");
	 			$("#"+this_.id).modal("hide");
	 			testing.decodePageParam();
	 		}
	   	 });
//	  }});
	}
};
$("#testing_open_alert_jyrq").datepicker({
	format:"yyyy-mm-dd",
	 autoclose: true,
}).on("hide", function(e) {
	  $("#form1").data("bootstrapValidator")  
.updateStatus("jyrq", "NOT_VALIDATED",null)  
.validateField("jyrq");  
});
/**
 * 弹出窗验证销毁
 */
$("#testing_open_alert").on("hidden.bs.modal", function() {
//	$('#form1').data('bootstrapValidator').resetForm(false);
	$("#form1").data('bootstrapValidator').destroy();
	$('#form1').data('bootstrapValidator', null);
//	Defectkeep_Open_Modal.validation();
});
