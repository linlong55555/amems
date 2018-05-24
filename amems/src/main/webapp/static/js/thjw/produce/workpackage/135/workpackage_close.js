workpackage_close_modal = {
	id:"workpackage_close",
	data:[],
	param: {
		obj:{},
		dprtcode:userJgdm,
		workpackageId:null,
		operation:true,		
		callback:function(){}
	},
	init:function(param){
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$(".time-masked").mask("99:99");
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+"_bm").val("");
		$("#"+this.id+"_bmid").val("");
		$("#"+this.id+"_gys").val("");
		$("#"+this.id+"_gysid").val("");
		$("#"+this.id+"_sjwcsj").val("");
		$("#"+this.id+" textarea").val("");
		var zxdw = document.getElementsByName(this.id+"_zxdw_radio");
		for(var i=0;i<zxdw.length;i++){
			if(zxdw[i].value=='0'){
				zxdw[i].checked='checked';
				$("#"+this.id+"_zxdw_gysdiv").hide();
			}
		}
		if(param){
			$.extend(this.param, param);
		}		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.workpackageId,
			fileHead : this.param.operation,
			colOptionhead : this.param.operation,
			fileType:"workpackage",
		});//显示附件
		this.validation();
		if(this.param.operation){
			$("#"+this.id+"_closeInfo_div  input[type='text']").attr("disabled",false);
			$("#"+this.id+"_closeInfo_div  textarea").attr("disabled",false);
			$("#"+this.id+"_closeInfo_div  input[type='radio']").attr("disabled",false);
			$("#"+this.id+"_gys").addClass("readonly-style").removeClass("div-readonly-style");
			$("#"+this.id+"_bm").addClass("readonly-style").removeClass("div-readonly-style");
			$("#"+this.id+"_sjwcrq_remark").show();
			$("#"+this.id+"_gysbutton").show();
			$("#"+this.id+"_bmbutton").show();
			$("#"+this.id+"_savebutton").show();
		}else{
			$("#"+this.id+"_closeInfo_div  input[type='text']").attr("disabled",true);
			$("#"+this.id+"_closeInfo_div  input[type='radio']").attr("disabled",true);
			$("#"+this.id+"_closeInfo_div  textarea").attr("disabled",true);
			$("#"+this.id+"_gys").addClass("div-readonly-style").removeClass("readonly-style");
			$("#"+this.id+"_bm").addClass("div-readonly-style").removeClass("readonly-style");
			$("#"+this.id+"_sjwcrq_remark").hide();
			$("#"+this.id+"_gysbutton").hide();
			$("#"+this.id+"_bmbutton").hide();
			$("#"+this.id+"_savebutton").hide();
		}
		if(this.param.workpackageId !=''){
			this.fillData(this.param.obj);
		}else{
			this.checkRadio(0)
			$("#workpackage_close_zxdw_bmdiv").show();
			$("#workpackage_close_zxdw_gysdiv").hide();
		}
		$('.date-picker').datepicker('update');
		$('input:radio[name="workpackage_close_zxdw_radio"]').click( function(){
			zxdw = $(this).val();
				if(zxdw==0){
					$("#workpackage_close_zxdw_bmdiv").show();
					$("#workpackage_close_zxdw_gysdiv").hide();
					$("#workpackage_close_gys").val("");
					$("#workpackage_close_gysid").val("");
					
				}else{
					$("#workpackage_close_zxdw_bmdiv").hide();
					$("#workpackage_close_zxdw_gysdiv").show();
					$("#workpackage_close_bm").val("");
					$("#workpackage_close_bmid").val("");
				}
		});
		$("#workpackage_close_modal").on("hidden.bs.modal", function() {
		$("#workpackage_close_form").data('bootstrapValidator').destroy();
		$('#workpackage_close_form').data('bootstrapValidator', null);
		workpackage_close_modal.validation();
		});
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id+"_modal").modal("show");
	},
	checkRadio:function(obj){
		var this_=this;
		var xfdw = document.getElementsByName(this_.id+"_zxdw_radio");
		for(var i=0;i<xfdw.length;i++){
			if(xfdw[i].value==obj){
				xfdw[i].checked='checked';
			}
		}
	},
	fillData:function(data){
		var this_ = this;
		$("#"+this_.id+"_fjzch").val(data.fjzch);
		$("#"+this_.id+"_msn").val(data.aircrafInfo==null?"":data.aircrafInfo.xlh);
		$("#"+this_.id+"_jx").val(data.aircrafInfo==null?"":data.aircrafInfo.fjjx);
		$("#"+this_.id+"_gbbh").val(data.gbbh);
		$("#"+this_.id+"_ms").val(data.gbmc);
		$("#"+this_.id+"_xfdw").val(data.xfdwDepartment==null?"":data.xfdwDepartment.dprtname);
		$("#"+this_.id+"_jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		$("#"+this_.id+"_jhwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
		if(data.gbr!=null && data.gbr.username!=null||data.realname!=null){
			$("#"+this_.id+"_gbr").val(data.gbr.username+" "+data.gbr.realname);
		}else{
			$("#"+this_.id+"_gbr").val(displayName);
		}
		if(data.gbrq != null){
			$("#"+this_.id+"_gbrq").val(data.gbrq.substring(0,10));
		}else{
			TimeUtil.getCurrentDate("#"+this_.id+"_gbrq");
		}
		
		var wwbs=data.sjWwbs;
		if(wwbs!=null || wwbs != undefined){
			this_.checkRadio(wwbs);
			if(wwbs==0){				
				$("#"+this_.id+"_bm").val(data.sjZxdw);
				$("#"+this_.id+"_bmid").val(data.sjZxdwid);
				$("#workpackage_close_zxdw_bmdiv").show();
				$("#workpackage_close_zxdw_gysdiv").hide();
			}else{
				$("#"+this_.id+"_gys").val(data.sjZxdw);
				$("#"+this_.id+"_gysid").val(data.sjZxdwid);
				$("#workpackage_close_zxdw_bmdiv").hide();
				$("#workpackage_close_zxdw_gysdiv").show();
			}
		}else{
			wwbs=data.jhWwbs;
			this_.checkRadio(wwbs);
			if(wwbs==0){				
				$("#"+this_.id+"_bm").val(data.jhZxdw);
				$("#"+this_.id+"_bmid").val(data.jhZxdwid);
				$("#workpackage_close_zxdw_bmdiv").show();
				$("#workpackage_close_zxdw_gysdiv").hide();
			}else{
				$("#"+this_.id+"_gys").val(data.jhZxdw);
				$("#"+this_.id+"_gysid").val(data.jhZxdwid);
				$("#workpackage_close_zxdw_bmdiv").hide();
				$("#workpackage_close_zxdw_gysdiv").show();
			}
		}
		if(data.sjJsrq != null){
			$("#"+this_.id+"_sjwcrq").val(data.sjJsrq==null?"":data.sjJsrq.substring(0,10));
			$("#"+this_.id+"_sjwcsj").val(data.sjJsrq==null?"":data.sjJsrq.substring(11,16));
		}else{
			$("#"+this_.id+"_sjwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
			$("#"+this_.id+"_sjwcsj").val("00:00");
		}
		if(data.sjKsrq !=null){
			$("#"+this_.id+"_sjksrq").val(data.sjKsrq==null?"":data.sjKsrq.substring(0,10));
		}else{
			$("#"+this_.id+"_sjksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		}
		$("#"+this_.id+"_gzzd").val(data.sjZd);
		$("#"+this_.id+"_gzzname").val(data.sjGzz);
		$("#"+this_.id+"_jczname").val(data.sjJcz);
		$("#"+this_.id+"_gbxq").val(data.gbyy);
	},
	openzrdw : function(obj){
		var this_ = this;
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:1,
			type : false,// 勾选类型true为checkbox,false为radio
			dprtcode : $("#zzjgid").val()==""?userJgdm:$("#zzjgid").val(),// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+obj).val(data.dprtname);
				$("#"+this_.id+"_"+obj+"id").val(data.dprtcode);
			}
		})
	},
	openGys:function(){
		var this_=this;
		  ManufacturerModal.show({
				selected : $("#"+this_.id+"_gysid").val(),// 对象ID
				parentid : "workpackage_modal",
				callback : function(data) {// 回调函数
					if(data){
						$('#'+this_.id +'_gysid').val(data.id);
						$('#'+this_.id +'_gys').val(data.gysbm+" "+data.gysmc);
					}
				}
			}); 
	},
	getData:function(){
		var this_=this;
		var param={};
		param.id=this_.param.workpackageId;
		param.sjKsrq=$.trim($("#"+this_.id+"_jhksrq").val());
		param.sjJsrq=$.trim($("#"+this_.id+"_sjwcrq").val());
		var paramsMap={};
		paramsMap.sjJssj=$.trim($("#"+this_.id+"_sjwcsj").val());
		param.paramsMap=paramsMap;
		param.sjZd=$.trim($("#"+this_.id+"_gzzd").val());
		var sjWwbs = $.trim($("input[name='"+this_.id+"_zxdw_radio']:checked").val());
		param.sjWwbs = sjWwbs;
		if(sjWwbs == 0){
			param.sjZxdwid=$.trim($("#"+this_.id+"_bmid").val());
			param.sjZxdw=$.trim($("#"+this_.id+"_bm").val());
		}else{
			param.sjZxdwid=$.trim($("#"+this_.id+"_gysid").val());
			param.sjZxdw=$.trim($("#"+this_.id+"_gys").val());
		}
		param.sjGzz=$.trim($("#"+this_.id+"_gzzname").val());
		param.sjJcz=$.trim($("#"+this_.id+"_jczname").val());
		param.gbyy=$.trim($("#"+this_.id+"_gbxq").val());
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
		if($("#"+this.id+"_sjksrq").val() != '' && $("#"+this.id+"_sjwcrq").val() != '' && $("#"+this.id+"_sjksrq").val()>$("#"+this.id+"_sjwcrq").val()){
			AlertUtil.showModalFooterMessage("实际开始日期不能晚于实际完成日期!");
			return false;
		}
		var sjwcsj = $("#"+this.id+"_sjwcsj").val();
		if(sjwcsj != ''){
			if(sjwcsj.split(":")[0]>=24){
				AlertUtil.showModalFooterMessage("实际完成时间格式不对!");
				return false;
			}
			if(sjwcsj.split(":")[1]>=60){
				AlertUtil.showModalFooterMessage("实际完成时间格式不对!");
				return false;
			}			
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
				var this_ = this;
				AlertUtil.showConfirmMessage("您确定要提交吗？", {callback: function(){
					var data = this_.getData();
					this_.param.callback(data);
				}});
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
				workpackage_close_sjwcrq : {
					validators : {
						notEmpty : {
							message : '实际完成时间不能为空'
						}
					}
				}
			}
		})
	}
}
$('#workpackage_close_sjwcrq').datepicker({
	autoclose : true,
	clearBtn : true
}).on('hide',function(e) {
	$('#workpackage_close_form').data('bootstrapValidator').updateStatus('workpackage_close_sjwcrq',
				'NOT_VALIDATED', null).validateField('workpackage_close_sjwcrq');
	
});