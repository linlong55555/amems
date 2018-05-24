
function validation(){
	  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	ggdbh: {
	                validators: {
	                	notEmpty: {
	                        message: '文件编号不能为空'
	                    },
	                    regexp: {
	                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	                        message: '不能输入中文且长度最多不超过50个字符'
	                    }
	                }
	            },    
	            jx: {
	                validators: {
	                    notEmpty: {
	                        message: '机型不能为空'
	                    }
	                }
	            },
	            xghBb: {
	                validators: {
	                    notEmpty: {
	                        message: '修改后版本不能为空'
	                    }
	                }
	            }
	        }
	    });
}



mel_edit_alert_Modal = {
	id : "mel_edit_alert_Modal",
	planeRegOption : '',
	attachmentSingle : {},
	attachmentsObj : {},
	param: {
		id:'',
		type:1,//1:新增,2:修改,3:审核,4:批准
		modalHeadChina : 'MEL更改单',
		modalHeadEnglish : 'MEL',
		viewObj:null,
		blOption:true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//初始化信息
		this.initInfo();
		ModalUtil.showModal(this.id);
//		$("#" + this.id).modal("show");
		//加载验证
		validation();
		
		
	},
	initInfo : function(){
		var this_ = this;
		$("#audited").hide();
		$("#reject").hide();
		$("#approve").hide();
		$("#rejected").hide();
		$("#save").show();
		$("#submit").show();
		evaluation_modal.init({
			parentId : "mel_edit_alert_Modal",// 第一层模态框id
			isShowed : true,// 是否显示选择评估单的操作列
			zlxl : 7,// 指令类型
			dprtcode : $("#dprtId").val(),// 组织机构
			jx : $("#jx").val(),// 机型
		});
		introduce_process_info_class.show({ // 流程信息
			status : 1,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
		});
		//隐藏出现异常的感叹号
		AlertUtil.hideModalFooterMessage();
		this_.attachmentSingle = {};
		this_.initModelHead();
		this_.initPlaneModel();
		this_.initDic();
		this_.initMelBasis();
		this_.initApprovel();
		this_.returnViewData(this_.param.viewObj,this_.param.blOption);
		if(!this_.param.blOption){
			this_.inputDisabled();
		}else{
			this_.inputShow();
		}
	},
	inputDisabled : function(){
		$('.input-group-border input').attr('disabled',true);
		$('.input-group-border textarea').attr('disabled',true);
		$('.input-group-border select').attr('disabled',true); 
	},
	inputShow : function(){
		$('.input-group-border input').attr('disabled',false);
		$('.input-group-border textarea').attr('disabled',false);
		$('.input-group-border select').attr('disabled',false); 
	},
	initApprovel : function(){
		var this_ = this;
		var data = this_.param.viewObj;
		if(data.zt){
			introduce_process_info_class.show({ // 流程信息
				status : data.zt,// 1,编写,2审核,3批准，4审核驳回,5批准驳回
				prepared : data.zdr?data.zdr.displayName:'',// 编写人
				preparedDate : data.zdsj,// 编写日期
				checkedOpinion : data.shyj,// 审核意见
				checkedby : data.shr? data.shr.displayName: '',// 审核人
				checkedDate : data.shsj,// 审核日期
				checkedResult : data.shjl,// 审核结论
				approvedOpinion : data.pfyj,// 批准意见
				approvedBy : data.pfr ? data.pfr.displayName : '',// 批准人
				approvedDate : data.pfsj,// 批准日期
				approvedResult : data.pfjl ,// 审批结论
			});
		}
	},
	initModelHead : function(){
		$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
		$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
	},
	initPlaneModel : function(){
		var this_ = this;
		var data = acAndTypeUtil.getACTypeList({DPRTCODE:this_.param.dprtcode});
	 	var option = '';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	  	 }
	 	$("#jx", $("#"+this_.id)).empty();
	 	$("#jx", $("#"+this_.id)).append(option);
	},
	initDic : function(){
		$("#gzlx_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','gzlx_e',this.param.dprtcode);
		$("#zy_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('4','zy_e',this.param.dprtcode);
		$("#gzz_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','gzz_e',this.param.dprtcode);
		$("#qy_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','qy_e',this.param.dprtcode);
		$("#jj_e", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','jj_e',this.param.dprtcode);
	},
	initMelBasis : function(){
		var this_ = this;
		$("#xgyjDiv", $("#"+this_.id)).empty();
		var str = '';
		var data = DicAndEnumUtil.getDicItems('41',this_.param.dprtcode);
		if(data != null && data.length >0){
			$.each(data,function(i,obj){
            	str += '<div class="input-group input-group-new form-group">';
            	str += '<label class="input-group-addon">';
            	str += '<input class="basisBox" value="'+StringUtil.escapeStr(obj.name)+'" type="checkbox" onclick='+this_.id+'.checkBasisRow(this) />';
            	str += ' '+StringUtil.escapeStr(obj.name);
            	str += '</label>';
            	str += '<input type="text" class="xgyjValue form-control" style="visibility:hidden;" maxlength="1300" >';
            	str += '</div>';
			});
	  	}
		$("#xgyjDiv", $("#"+this_.id)).html(str);
	},
	checkBasisRow : function(obj){
		if($(obj).is(':checked')){
			$(obj).parent().parent().find(".xgyjValue").css("visibility","visible");
		}else{
			$(obj).parent().parent().find(".xgyjValue").css("visibility","hidden");
		}
	},
	returnViewData : function(obj,blOption){
		var this_ = this;
		$("#ggdbh", $("#"+this_.id)).val(formatUndefine(obj.ggdbh));
		if(obj.jx){
			$("#jx", $("#"+this_.id)).val(formatUndefine(obj.jx));
		}
		$("#xmh", $("#"+this_.id)).val(formatUndefine(obj.xmh));
		$("#ssbf", $("#"+this_.id)).val(formatUndefine(obj.ssbf));
		$("#sszj", $("#"+this_.id)).val(formatUndefine(obj.sszj));
		$("#zy", $("#"+this_.id)).val(formatUndefine(obj.zy));
		$("#xgqBb", $("#"+this_.id)).val(formatUndefine(obj.xgqBb));
		$("#xghBb", $("#"+this_.id)).val(formatUndefine(obj.xghBb));
		$("#xdnr", $("#"+this_.id)).val(formatUndefine(obj.xdnr));
		$("#xgyy", $("#"+this_.id)).val(formatUndefine(obj.xgyy));
		$("#xdqx", $("#"+this_.id)).val(formatUndefine(obj.xdqx));
		$("#melqdfjid", $("#"+this_.id)).val(formatUndefine(obj.melqdfjid));
		$("#melDprtcode", $("#"+this_.id)).val(formatUndefine(obj.dprtcode));
		
		$("#melId", $("#"+this_.id)).val(formatUndefine(obj.id));
 		if(obj.xgbj != null){
 			var arr = obj.xgbj.split(",");
 			$.each(arr,function(index,v){
 				$("input:checkbox[name='xgbj'][value='"+v+"']", $("#"+this_.id)).attr("checked",true);
 			});
 			
 		}
 		
 		if(obj.melqdfjid != null && obj.melqdfjid != ''){
			$("#melqdfjBox", $("#"+this_.id)).attr("checked",'true');//选中
		}else{
			$("#melqdfjBox", $("#"+this_.id)).removeAttr("checked");//选中
		}
		var attachment = obj.attachment;
		//加载工卡附件
		attachmentsSingleUtil.initAttachment(
				"mel_attachments_single_edit"//主div的id
				,obj.attachment//附件
				,'card'//文件夹存放路径
				,blOption//true可编辑,false不可编辑
				,'请上传MEL清单'//提示信息
				);
 		this_.showOrHideAttach('melqdfjBox','mel_attachments_single_edit');
 		var melChangeSheetAndBasiList = obj.melChangeSheetAndBasiList;
 		if(melChangeSheetAndBasiList != null && melChangeSheetAndBasiList.length > 0){
 			$.each(melChangeSheetAndBasiList,function(index,o){
 				$("#xgyjDiv .basisBox[value='"+o.yjlx+"']", $("#"+this_.id)).attr("checked",true);
 				$("#xgyjDiv .basisBox[value='"+o.yjlx+"']", $("#"+this_.id)).parent().parent().find(".xgyjValue").css("visibility","visible");
 				$("#xgyjDiv .basisBox[value='"+o.yjlx+"']", $("#"+this_.id)).parent().parent().find(".xgyjValue").val(o.yjnr);
 			});
 		}
 		attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:formatUndefine(obj.id),
			fileType:"mel"
		});//显示附件
		evaluation_modal.init({
			parentId : "mel_edit_alert_Modal",
			isShowed : blOption,
			zlid : obj.id,
			zlxl : 7,
			dprtcode :obj.dprtcode,
			jx : $("#jx").val()
		});
		//显示附件
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : blOption,
			colOptionhead : blOption,
			fileType:"card"
		});
		
	},
	multiselect : function(){
		//生成多选下拉框(飞机注册号)
		$('#wcfjzchdiv').empty();
		$('#wcfjzchdiv').html("<select multiple='multiple' id='wcfjzch' ></select>");
		$("#wcfjzch").append(this.planeRegOption);
		$('#wcfjzch').multiselect({
		    buttonClass: 'btn btn-default',
		    buttonWidth: 'auto',
		    numberDisplayed:8,
		    includeSelectAllOption: true,
		    onChange:function(element,checked){
	  	    }
	    });
		
		//生成多选下拉框工作类型
		$('#wcgzlxdiv').empty();
		$('#wcgzlxdiv').html("<select multiple='multiple' id='wcgzlx' ></select>");
		DicAndEnumUtil.registerDic('17','wcgzlx',this.param.dprtcode);
	   $('#wcgzlx').multiselect({
		    buttonClass: 'btn btn-default',
		    buttonWidth: 'auto',
		    numberDisplayed:15,
		    includeSelectAllOption: true,
		    onChange:function(element,checked){
	  	    }
	   });
	},
	clearData : function(){
		$("#wcxmly").val('');
		$("#wczjh").val('');
		$("#wczjhName").val('');
		$("#wcscmsZw").val('');
		$("#wcscmsYw").val('');
		$("#wccksc").val('');
		$("#wcckgk").val('');
		$("#wwz").val('');
		$("#wcjclx").val('');
		$("#wcgzzw").val('-');
		$("#wczt").val(1);
		$("input:radio[name='wcisBj'][value=0]").attr("checked",true); 
		$("#wcisMi").removeAttr("checked");
		$("#wcbz").val('');
	},
	openChapterWin : function(){
		var zjh = $.trim($("#wczjh").val());
		var dprtcode = this.param.dprtcode;
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode, //机构代码
			callback: function(data){//回调函数
				if(data != null){
					var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.zwms);
					$("#wczjh").val(data.zjh);
					$("#wczjhName").val(wczjhName);
				}else{
					$("#wczjh").val('');
					$("#wczjhName").val('');
				}
			}
		});
	},
	showOrHideAttach : function(box,single){
		var this_ = this;
		if($('#'+box, $("#"+this_.id)).is(':checked')) {
			$('#'+single+' .singlefile', $("#"+this_.id)).show();
		}else{
			$('#'+single+' .singlefile', $("#"+this_.id)).hide();
		}
	},
	vilidateData : function(){
		var zjh = $.trim($("#wczjh").val());
		var fjzch = $.trim($("#wcfjzch").val());
		var wcscmsYw = $.trim($("#wcscmsYw").val());
		if(null != wcscmsYw && "" != wcscmsYw){
			var reg = /^[^\u4e00-\u9fa5]+$/;
			if(!reg.test(wcscmsYw)){
				AlertUtil.showMessageCallBack({
					message : '英文描述不能输入中文!',
					option : 'wcscmsYw',
					callback : function(option){
						$("#"+option).focus();
					}
				});
				return false;
			}
		}
		if(null == zjh || "" == zjh){
			AlertUtil.showErrorMessage("请选择ATA章节号!");
			return false;
		}
		if(null == fjzch || "" == fjzch){
			AlertUtil.showErrorMessage("请选择适用性!");
			return false;
		}else{
			if('multiselect-all' == fjzch){
				AlertUtil.showErrorMessage("请选择适用性!");
				return false;
			}
		}
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}
		
		var data = {};
		data.xmly = $.trim($("#wcxmly").val());
		data.zjh = $.trim($("#wczjh").val());
		data.zjhStr = $.trim($("#wczjhName").val());
		data.scmsZw = $.trim($("#wcscmsZw").val());
		data.scmsYw = $.trim($("#wcscmsYw").val());
		data.cksc = $.trim($("#wccksc").val());
		data.ckgk = $.trim($("#wcckgk").val());
		data.wz = $.trim($("#wwz").val());
		data.jclx = $.trim($("#wcjclx").val());
		data.gzzw = $.trim($("#wcgzzw").val());
		data.isBj = $("input:radio[name='wcisBj']:checked").val(); 
		data.isMi = $("#wcisMi").is(":checked")?1:0;
		data.bz = $.trim($("#wcbz").val());
		data.zt = $.trim($("#wczt").val());
		var gzlx = $.trim($("#wcgzlx").val());
		var fjzch = $.trim($("#wcfjzch").val());
		var fjzchStr = '';
		
		var gzlxStr = '';
		if(gzlx != null && gzlx.length > 0){
			var gzlxArr = gzlx.split(",");
			for(var i = 0 ; i < gzlxArr.length ; i++){
				if('multiselect-all' != gzlxArr[i]){
					gzlxStr += gzlxArr[i]+",";
				}
			}
			if('multiselect-all' == gzlxArr[0]){
				gzlx = gzlx.replace('multiselect-all',"").substring(1,gzlx.length); 
			}
		}
		if(gzlxStr != ''){
			gzlxStr = gzlxStr.substring(0,gzlxStr.length-1);
		}
		data.gzlx = gzlx;
		data.gzlxStr = gzlxStr;
		
		var fjzchValue = '';
		if(fjzch != null && fjzch.length > 0){
			var fjzchArr = fjzch.split(",");
			for(var i = 0 ; i < fjzchArr.length ; i++){
				if("00000" == fjzchArr[i]){
					fjzchValue = '00000';
					fjzchStr = '通用Currency';
					break;
				}
				if('multiselect-all' != fjzchArr[i]){
					fjzchValue += fjzchArr[i]+",";
				}
			}
		}
		if(fjzchValue != '' && fjzchValue != '00000'){
			fjzchValue = fjzchValue.substring(0,fjzchValue.length-1);
			fjzchStr = fjzchValue;
		}
		data.fjzch = fjzchValue;
		data.fjzchStr = fjzchStr;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#WorkContentModal").modal("hide");
	},
	save: function(){
		var melId = $("#melId").val();
		var url = '';
		var message = '';
		if(melId){
			url = basePath+"/project/mel/editSave";
			message = "修改成功!";
		}else{
			url = basePath+"/project/mel/addSave";
			message = "保存成功!";
		}
		this.performAction(url,message);
	},
	submit: function(){
		var melId = $("#melId").val();
		var url = '';
		var message = '';
		if(melId){
			url = basePath+"/project/mel/editSubmit";
			message = "修改成功!";
		}else{
			url = basePath+"/project/mel/addSubmit";
			message = "提交成功!";
		}
		this.performAction(url,message);
	},
	performAction: function(url,message) {
		var this_ = this;
		
		  $('#form').data('bootstrapValidator').validate();
		  if(!$('#form').data('bootstrapValidator').isValid()){
			  AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
			return false;
		  }
		  
		  var ggdbh = $.trim($("#ggdbh").val());
		  var jx = $.trim($("#jx").val());
		  var xmh = $.trim($("#xmh").val());
		  var ssbf = $.trim($('#ssbf').val());
		  var sszj = $.trim($('#sszj').val());
		  var zy = $.trim($('#zy').val());
		  var xgqBb = $.trim($('#xgqBb').val());
		  var xghBb = $.trim($('#xghBb').val());
		  var xdqx = $.trim($('#xdqx').val());
		  var xdnr = $.trim($('#xdnr').val());
		  var xgyy = $.trim($('#xgyy').val());
		  
		  var xgbj = '';
		  $('input:checkbox[name=xgbj]:checked').each(function(){
			  xgbj += $(this).val() + ",";
		  });
		  if(xgbj != ''){
			  xgbj = xgbj.substring(0,xgbj.length-1);
		  }
		  //参数组装
		  var obj = {
				  ggdbh:ggdbh,
				  jx:jx,
				  xmh:xmh,
				  ssbf:ssbf,
				  sszj:sszj,
				  zy:zy,
				  xgqBb:xgqBb,
				  xghBb:xghBb,
				  xdqx:xdqx,
				  xgbj:xgbj,
				  xdnr:xdnr,
				  xgyy:xgyy,
				  dprtcode:$("#melDprtcode").val()
		  };
			// 技术评估单数据
			var technicalList = evaluation_modal.getData();
			if (technicalList != null && technicalList != '') {
				var instructionsourceList = [];
				// 组装下达指令数据
				$.each(technicalList, function(index, row) {
					var instructionsource = {};
					instructionsource.dprtcode = row.dprtcode;
					instructionsource.pgdid = row.id;
					instructionsource.pgdh = row.pgdh;
					instructionsource.bb = row.bb;
					instructionsourceList.push(instructionsource);
				});
				 obj.isList = instructionsourceList;
			}
		  
			var melId = $("#melId").val();
			  if(melId){
				  obj.id = melId;
			  }
		  
		  obj.melChangeSheetAndBasiList = this_.getMelChangeSheetAndBasiList();
		  var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		  obj.attachmentList = attachmentsObj.getAttachments();
		  
		  //设置工作内容
		  var workContentAttachment = attachmentsSingleUtil.getAttachment('mel_attachments_single_edit');
		  if(workContentAttachment != null && workContentAttachment.wjdx != null && workContentAttachment.wjdx != ''){
			  obj.attachment = workContentAttachment;
		  }
		  
		  //验证mel附件
		  if(!attachmentsSingleUtil.isVaildMap['mel_attachments_single_edit']){
			  return false;
		  }
		  
		  
		  startWait();
		  AjaxUtil.ajax({
			  url:url,			//发送请求的地址（默认为当前页地址）
			  type:'post',										//请求方式（post或get）默认为get
			  data:JSON.stringify(obj),							//发送到服务器的数据
			  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
			  dataType:'json',									//预期服务器返回的数据类型
			  cache:false,	                                    //缓存（true有缓存，false无缓存）
			  async: false,
			  success:function(data) {
				  finishWait();//请求成功后调用的回调函数
				  $("#mel_edit_alert_Modal").modal("hide");
				  id = data;
				  AlertUtil.showErrorMessage(message);
				  goPage(1,"auto","desc");
			  }
		  });
		},
		//获取更改单-修改依据	
		getMelChangeSheetAndBasiList: function (){
			var melChangeSheetAndBasiList = [];
			$('#xgyjDiv .basisBox:checked').each(function(){
				  var info = {};
				  info.yjlx = $.trim($(this).val());
				  info.yjnr =$(this).parent().parent().find(".xgyjValue").val();
				  melChangeSheetAndBasiList.push(info);
			});
			return melChangeSheetAndBasiList;
		}
	
}

$("#jx").change(function() {
	evaluation_modal.changeParam({
		jx : $("#jx").val(),// 机型
	});
});

//隐藏Modal时验证销毁重构
$("#mel_edit_alert_Modal").on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});

//审核通过
function agreedAudit(){
	var shyj = introduce_process_info_class.getData().shenhe;
	var id = $.trim($("#melId").val());
	
	var fixedCheckItem = {
			id : id,
			shyj : shyj
		};
	
	AlertUtil.showConfirmMessage("您确定要审核通过吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project2/mel/agreedAudit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				 finishWait();//请求成功后调用的回调函数
				  $("#mel_edit_alert_Modal").modal("hide");
				  AlertUtil.showErrorMessage("审核通过！");
				  goPage(1,"auto","desc");
			}
		});
	
	}});
}
//审核驳回
function rejectedAudit(){
	var shyj = introduce_process_info_class.getData().shenhe;
	var id = $.trim($("#melId").val());
	var fixedCheckItem = {
			id : id,
			shyj : shyj
		};
	AlertUtil.showConfirmMessage("您确定要审核驳回吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project2/mel/rejectedAudit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				  finishWait();//请求成功后调用的回调函数
				  $("#mel_edit_alert_Modal").modal("hide");
				  AlertUtil.showErrorMessage("审核驳回！");
				  goPage(1,"auto","desc");
			}
		});
	
	}});
}
//批准通过
function agreedApprove(){
	var pfyj = introduce_process_info_class.getData().shenpi;
	var id = $.trim($("#melId").val());
	
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	
	AlertUtil.showConfirmMessage("您确定要批准通过吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project2/mel/agreedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				  finishWait();//请求成功后调用的回调函数
				  $("#mel_edit_alert_Modal").modal("hide");
				  AlertUtil.showErrorMessage("批准通过！");
				  goPage(1,"auto","desc");
			}
		});
	
	}});
}
//批准驳回
function rejectedApprove(){
	var pfyj = introduce_process_info_class.getData().shenpi;
	var id = $.trim($("#melId").val());
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	AlertUtil.showConfirmMessage("您确定要批准驳回吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project2/mel/rejectedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				 finishWait();//请求成功后调用的回调函数
				  $("#mel_edit_alert_Modal").modal("hide");
				  AlertUtil.showErrorMessage("批准驳回！");
				  goPage(1,"auto","desc");
			}
		});
	
	}});
}


