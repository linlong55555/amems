workpackage_modal = {
	id:"workpackage_modal",
	data:[],
	param: {
		obj:{},
		dprtcode:userJgdm,
		workpackageId:'',
		operation:true,
		modalName:'',
		modalEname:'',
		callback:function(){}
	},
	init:function(param){
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$(".clear").hide();
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" textarea").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id+" select").empty();
		DicAndEnumUtil.registerDic('71','workpackage_modal_wxlx',this.param.dprtcode);
		$("#"+this.id+"_gbbh").attr("disabled",false);
		$("input[name='"+this.id+"_xfdw_radio']").attr("disabled",false);
		$("#"+this.id+"_khxxbtn").show();
		$("#"+this.id+"_khxxb").attr("disabled",false);
		$("#"+this.id+"_fjzch").attr("disabled",false);
		$("#"+this.id+"_msn").attr("disabled",false);
		$("#"+this.id+"_fjjx").attr("disabled",false);
		$("#"+this.id+"_zdrq").attr("disabled",false);
		$("#"+this.id+"_khxx").addClass("readonly-style").removeClass("div-readonly-style");
		$("#"+this.id+"_name").html(this.param.modalName);
		$("#"+this.id+"_ename").html(this.param.modalEname);
		$("#"+this.id+"_ms").attr("disabled",false);
		$("input[name='"+this.id+"_xfdw_radio']").attr("disabled",false);
		$("#"+this.id+"_xfdw").attr("disabled",false);
		$("#"+this.id+"_xfdwbtn").show();
		$("#"+this.id+"_xfdw").addClass("readonly-style").removeClass("div-readonly-style");
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.workpackageId,
			fileHead : this.param.operation,
			colOptionhead : this.param.operation,
			fileType:"workpackage",
		});//显示附件
		this.initJxSelect();
		this.validation();
		if(this.param.workpackageId !=''){
			this.setData(this.param.obj);
		}else{
			$("#"+this.id+"_zdrq").datepicker({
				autoclose : true,
				clearBtn : false,
			});
			TimeUtil.getCurrentDate("#"+this.id+"_zdrq"); 
			$("#"+this.id+"_gbzt_div").hide();
			var deptType = deptInfo.deptType;
			var checkboxValue = deptType=="135"?"0":"1";
			var xfdw = document.getElementsByName(this.id+"_xfdw_radio");
			for(var i=0;i<xfdw.length;i++){
				if(xfdw[i].value == checkboxValue){
					xfdw[i].checked='checked';
				}
			}
			if(checkboxValue=="0"){
				$("#"+this.id+"_xfdw_bmdiv").show();
				$("#"+this.id+"_xfdw_khxxdiv").hide();			
			}else{
				$("#workpackage_modal_xfdw_bmdiv").hide();			
				$("#workpackage_modal_xfdw_khxxdiv").show();
			}
		}
		$('.date-picker').datepicker('update');
		$('input:radio[name="workpackage_modal_xfdw_radio"]').change( function(){
			var xfdw = $(this).val();
				if(xfdw=='0'){
					$("#workpackage_modal_xfdw_bmdiv").show();
					$("#workpackage_modal_xfdw_khxxdiv").hide();
					$("#workpackage_modal_khxx").val("")
					$("#workpackage_modal_khxxid").val("")
				}else{
					$("#workpackage_modal_xfdw_bmdiv").hide();					
					$("#workpackage_modal_xfdw_khxxdiv").show();
					$("#workpackage_modal_xfdw").val("")
					$("#workpackage_modal_xfdwid").val("")
				}
				$("#workpackage_modal_xmbhid").val("");
				$("#workpackage_modal_xmbh").val("");
				$("#workpackage_modal_fjzch").val("");
				$("#workpackage_modal_msn").val("");
		});
		$("#workpackage_modal").on("hidden.bs.modal", function() {
			$("#workpackage_modal_form").data('bootstrapValidator').destroy();
			$('#workpackage_modal_form').data('bootstrapValidator', null);
			workpackage_modal.validation();
		});
		$("#"+this.id).modal("show");
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息
	},
	checkRadio:function(obj){
		var this_=this;
		var xfdw = document.getElementsByName(this_.id+"_xfdw_radio");
		for(var i=0;i<xfdw.length;i++){
			if(xfdw[i].value==obj){
				xfdw[i].checked='checked';
			}
		}
	},
	setData:function(data){
		var this_ = this;
		if(data.paramsMap.exist == 1){
			$("#"+this_.id+"_ms").attr("disabled",true);
			$("#"+this_.id+"_fjjx").attr("disabled",true);
			$("#"+this_.id+"_msn").attr("disabled",true);
			$("#"+this_.id+"_fjzch").attr("disabled",true);
			$("input[name='"+this_.id+"_xfdw_radio']").attr("disabled",true);
			$("#"+this_.id+"_xfdw").attr("disabled",true);
			$("#"+this_.id+"_xfdwbtn").hide();
			$("#"+this_.id+"_xfdw").addClass("div-readonly-style").removeClass("readonly-style");
		}
		$("#"+this.id+"_gbzt_div").show();
		$("#"+this_.id+"_fjzch").val(data.fjzch);
		$("#"+this_.id+"_msn").val(data.fjxlh);
		$("#"+this_.id+"_fjjx").val(data.fjjx);
		$("#"+this_.id+"_gbbh").val(data.gbbh);
		$("#"+this_.id+"_gbbh").attr("disabled",true);
		$("#"+this_.id+"_zdrq").attr("disabled",true);
		$("#"+this_.id+"_ms").val(data.gbmc);
		$("#"+this_.id+"_zdrq").val(data.zdrq==null?"":data.zdrq.substring(0,10));
		$("#"+this_.id+"_jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		$("#"+this_.id+"_jhwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
		$("#"+this_.id+"_wxlx").val(data.wxlx);
		var wbbs=data.wbbs;
		this_.checkRadio(wbbs);
		if(wbbs==0){
			$("#"+this_.id+"_xfdwid").val(StringUtil.escapeStr(data.xfdwid));
			$("#"+this_.id+"_xfdw").val(StringUtil.escapeStr(data.xfdw));
			$("#"+this_.id+"_xfdw_bmdiv").show();
			$("#"+this_.id+"_xfdw_khxxdiv").hide();			
		}else{
			$("#"+this_.id+"_khxx").val(StringUtil.escapeStr(data.xfdw));
			$("#"+this_.id+"_khxxid").val(StringUtil.escapeStr(data.xfdwid));
			$("#workpackage_modal_xfdw_bmdiv").hide();			
			$("#workpackage_modal_xfdw_khxxdiv").show();
			if(data.zt==2){
				if(data.xfdwid !=null && data.xfdwid != ''){
					$("input[name='"+this_.id+"_xfdw_radio']").attr("disabled",true);
					$("#"+this_.id+"_khxxbtn").hide();
					$("#"+this_.id+"_khxx").attr("disabled",true);
					$("#"+this_.id+"_khxx").addClass("div-readonly-style").removeClass("readonly-style");
				}
				if(data.fjzch !=null && data.fjzch !=''){
					$("#"+this_.id+"_fjzch").attr("disabled",true);
				}
				if(data.fjxlh !=null && data.fjxlh !=''){
					$("#"+this_.id+"_msn").attr("disabled",true);
				}
				if(data.fjjx !=null && data.fjjx !=''){
					$("#"+this_.id+"_fjjx").attr("disabled",true);
				}
			}		
		}
		$("#"+this_.id+"_xmbhid").val(data.xmid);
		$("#"+this_.id+"_xmbh").val(data.project==null?"":data.project.xmbm+" "+data.project.xmmc);
		$("#"+this_.id+"_yjzxdwid").val(data.zxdwid);
		$("#"+this_.id+"_yjzxdw").val(data.zxdw);
		$("#"+this_.id+"_gbzt").val(DicAndEnumUtil.getEnumName('workpackageStatusEnum', data.zt));
		$("#"+this_.id+"_gzyq").val(data.gzyq);
	},
	initJxSelect:function(){
		var planeModelOption = '';
		var typeList = acAndTypeUtil.getACTypeList({
			DPRTCODE : this.param.dprtcode
		});
		if (typeList.length > 0) {
			for (var i = 0; i < typeList.length; i++) {
				planeModelOption += "<option value='"
						+ StringUtil.escapeStr(typeList[i].FJJX) + "'>"
						+ StringUtil.escapeStr(typeList[i].FJJX) + "</option>";
			}		
		} 
		planeModelOption += "<option value=''>N/A</option>";
		$("#"+this.id+"_fjjx").html(planeModelOption);
	},
	openzrdw : function(obj,jdbs){
		var this_ = this;
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:jdbs,//基地标识
			type : false,// 勾选类型true为checkbox,false为radio
			dprtcode : this_.param.dprtcode,// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+obj).val(data.dprtname);
				$("#"+this_.id+"_"+obj+"id").val(data.dprtcode);
				if(obj=="xfdw"){
					$("#"+this_.id+"_xmbhid").val("");
					$("#"+this_.id+"_xmbh").val("");
					$("#"+this_.id+"_fjzch").val("");
					$("#"+this_.id+"_msn").val("");
				}
			}
		})
	},
	openWinXmbh:function(){
		var this_ = this;
		var wbbs = $("input[name='"+this_.id+"_xfdw_radio']:checked").val();
		var khid = '';
		if(wbbs == 0){
			khid = $("#"+this_.id+"_xfdwid").val();
		}else{
			khid = $("#"+this_.id+"_khxxid").val();
		}
		var fjjx = $("#"+this_.id+"_fjjx").val();
		var fjzch = $("#"+this_.id+"_fjzch").val();
		var fjxlh = $("#"+this_.id+"_msn").val();
		project_modal.init({
			dprtcode:this_.param.dprtcode,
			wbbs:wbbs,
			fjjx:fjjx,
			fjzch:fjzch,
			fjxlh:fjxlh,
			khid:khid,
			selected:$("#"+this_.id+"_xmbhid").val(),
			callback:function(data){
				$("#"+this_.id+"_xmbhid").val(StringUtil.escapeStr(data.id));
				$("#"+this_.id+"_xmbh").val(StringUtil.escapeStr(data.xmbm)+" "+StringUtil.escapeStr(data.xmmc));
				$("#"+this_.id+"_fjzch").val(StringUtil.escapeStr(data.fjzch));
				$("#"+this_.id+"_msn").val(StringUtil.escapeStr(data.fjxlh));
				if($("#"+this_.id+"_khxxid").val()=='' && $("input[name='"+this_.id+"_xfdw_radio']:checked").val()==1){
					$("#"+this_.id+"_khxxid").val(StringUtil.escapeStr(data.khid));
					$("#"+this_.id+"_khxx").val(StringUtil.escapeStr(data.khbm)+" "+StringUtil.escapeStr(data.khmc));
				}
				if($("#"+this_.id+"_xfdwid").val()=='' && $("input[name='"+this_.id+"_xfdw_radio']:checked").val()==0){
					$("#"+this_.id+"_xfdwid").val(StringUtil.escapeStr(data.khid));
					$("#"+this_.id+"_xfdw").val(StringUtil.escapeStr(data.khmc));
				}
				$("#workpackage_modal_form").data('bootstrapValidator').destroy();
				$('#workpackage_modal_form').data('bootstrapValidator', null);
				workpackage_modal.validation();
			}
		})
	},
	openWinKhxx:function(){
		var this_ = this;
		customer_modal.init({
			dprtcode:this_.param.dprtcode,
			selected:$("#"+this_.id+"_khxxid").val(),
			callback:function(data){
				$("#"+this_.id+"_khxxid").val(StringUtil.escapeStr(data.id));
				$("#"+this_.id+"_khxx").val(StringUtil.escapeStr(data.khbm)+" "+StringUtil.escapeStr(data.khmc));
				$("#"+this_.id+"_xmbhid").val("");
				$("#"+this_.id+"_xmbh").val("");
				$("#"+this_.id+"_fjzch").val("");
				$("#"+this_.id+"_msn").val("");
			}
		})
	},
	getData:function(){
		var this_=this;
		var param={};
		if(this_.param.workpackageId!=''){
			param.id=this_.param.workpackageId;
			param.dprtcode = this_.param.dprtcode;
		}
		param.fjzch=$.trim($("#"+this_.id+"_fjzch").val());
		param.fjxlh=$.trim($("#"+this_.id+"_msn").val());
		param.fjjx=$.trim($("#"+this_.id+"_fjjx").val());
		param.gbbh=$.trim($("#"+this_.id+"_gbbh").val());
		param.gbmc=$.trim($("#"+this_.id+"_ms").val());
		param.zdrq=$.trim($("#"+this_.id+"_zdrq").val());
		param.jhKsrq=$.trim($("#"+this_.id+"_jhksrq").val());
		param.jhJsrq=$.trim($("#"+this_.id+"_jhwcrq").val());
		param.wxlx=$.trim($("#"+this_.id+"_wxlx").val());
		var wbbs=$.trim($("input[name='"+this_.id+"_xfdw_radio']:checked").val());
		param.wbbs=wbbs;
		if(wbbs==0){
			param.xfdwid=$.trim($("#"+this_.id+"_xfdwid").val());
			param.xfdw=$.trim($("#"+this_.id+"_xfdw").val());
		}else{
			param.xfdw=$.trim($("#"+this_.id+"_khxx").val());
			param.xfdwid=$.trim($("#"+this_.id+"_khxxid").val());
		}
		param.xmid=$.trim($("#"+this_.id+"_xmbhid").val());
		param.zxdw=$.trim($("#"+this_.id+"_yjzxdw").val());
		param.zxdwid=$.trim($("#"+this_.id+"_yjzxdwid").val());
		param.gzyq=$.trim($("#"+this_.id+"_gzyq").val());
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this_.id+'_attachments_list_edit');
		param.attachments = attachmentsObj.getAttachments();
		return param;
	},
	saveData:function(){
		$("#"+this.id+"_form").data('bootstrapValidator').validate();
		if (!$("#"+this.id+"_form").data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
			return false;
		}
		if($("#"+this.id+"_jhksrq").val() != '' && $("#"+this.id+"_jhwcrq").val() != '' && $("#"+this.id+"_jhksrq").val()>$("#"+this.id+"_jhwcrq").val()){
			AlertUtil.showModalFooterMessage("计划开始日期不能晚于计划完成日期!");
			return false;
		}
		if($("#"+this.id+"_zdrq").val() == ''){
			AlertUtil.showModalFooterMessage("制单日期不能为空!");
			return false;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var this_ = this;
			var data = this_.getData();
			if(data.gbbh==""){
				AlertUtil.showConfirmMessage("工包编号为空，是否需要自动生成并提交?", {callback: function(){
					this_.param.callback(data);
				}});
			}else{
				AlertUtil.showConfirmMessage("你确定要提交吗?", {callback: function(){
					this_.param.callback(data);
				}});
			}
		}
	},
	validation:function(){
		var this_=this;
		validatorForm = $("#"+this_.id+"_form").bootstrapValidator({
			message : '数据不合法',
			feedbackIcons : {
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				workpackage_modal_gbbh : {
					validators : {
						regexp : {
							regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,50}$/,
							message : '只能输入长度不超过50个字符的英文、数字、英文特殊字符!'
						}
					}
				},
				workpackage_modal_xmbh : {
					validators : {
						notEmpty : {
							message : '项目编号不能为空'
						},
					}
				},
				workpackage_modal_ms : {
					validators : {
						notEmpty : {
							message : '描述不能为空'
						},
					}
				},
				workpackage_modal_fjzch : {
					validators : {
						regexp : {
							regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,20}$/,
							message : '只能输入长度不超过20个字符的英文、数字、英文特殊字符!'
						}
					}
				},
				workpackage_modal_msn : {
					validators : {
						regexp : {
							regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,20}$/,
							message : '只能输入长度不超过20个字符的英文、数字、英文特殊字符!'
						}
					}
				}
			}
		})
	},
	changeFjjx:function(){
		$("#"+this.id+"_xmbh").val("");
		$("#"+this.id+"_xmbhid").val("");
		$("#"+this.id+"_xmbh").val("");
		$("#"+this.id+"_xmbh").val("");
		$("#"+this.id+"_fjzch").val("");
		$("#"+this.id+"_msn").val("");
	}
}
