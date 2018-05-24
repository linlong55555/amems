workpackage_modal = {
	id:"workpackage_modal",
	data:[],
	param: {
		obj : {},
		fjzch:null,
		dprtcode:userJgdm,
		workpackageId:'',
		operation:true,
		view:false,
		mark:true,
		modalName:'',
		modalEname:'',
		modalShow:true,
		callback:function(){}
	},
	init:function(param){
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
		$("#"+this.id+" select").empty();
		DicAndEnumUtil.registerDic('71','workpackage_modal_wxlx',this.param.dprtcode);
		var yjzxdw = document.getElementsByName(this.id+"_yjzxdw_radio");
		for(var i=0;i<yjzxdw.length;i++){
			if(yjzxdw[i].value=='0'){
				yjzxdw[i].checked='checked';
				$("#"+this.id+"_yjzxdw_gysdiv").hide();
			}
		}
		this.param.view=false;
		this.param.mark=true;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id+"_name").html(this.param.modalName);
		$("#"+this.id+"_ename").html(this.param.modalEname);
		$("#"+this.id+"_zdrq").attr("disabled",false);
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.workpackageId,
			fileHead : this.param.operation,
			colOptionhead : this.param.operation,
			fileType:"workpackage",
		});//显示附件
		if(this.param.view){
			$("#"+this.id+" input[type='text']").attr("disabled",true);
			$("#"+this.id+" select").attr("disabled",true);
			$("#"+this.id+" input[type='radio']").attr("disabled",true);
			$("#"+this.id+"_xfdwbtn").attr("disabled",true);
			$("#"+this.id+"_xfdwbtn").hide();
			$("#"+this.id+"_nbzxdwbtn").hide();
			$("#"+this.id+"_wbzxdwbtn").hide();
			$("#"+this.id+"_gbbhmark").hide();
			$("#"+this.id+"_msmark").hide();
			$("#"+this.id+"_bcbtn").hide();
			$("#"+this.id+"_qrtjbtn").show();
			$("#"+this.id+" textarea").attr("disabled",true);
			$("#"+this.id+"_xfdw").addClass("div-readonly-style").removeClass("readonly-style");
			$("#"+this.id+"_gys").addClass("div-readonly-style").removeClass("readonly-style");
		}else{
			$("#"+this.id+" input[type='text']").attr("disabled",false);
			$("#"+this.id+" select").attr("disabled",false);
			$("#"+this.id+" button").attr("disabled",false);
			$("#"+this.id+" textarea").attr("disabled",false);
			$("#"+this.id+" input[type='radio']").attr("disabled",false);
			$("#"+this.id+"_msn").attr("disabled",true);
			$("#"+this.id+"_jx").attr("disabled",true);
			$("#"+this.id+"_gbzt").attr("disabled",true);
			$("#"+this.id+"_xfdwbtn").show();
			$("#"+this.id+"_nbzxdwbtn").show();
			$("#"+this.id+"_wbzxdwbtn").show();
			$("#"+this.id+"_bcbtn").show();
			$("#"+this.id+"_gbbhmark").show();
			$("#"+this.id+"_msmark").show();
			$("#"+this.id+"_qrtjbtn").hide();
			$("#"+this.id+"_xfdw").addClass("readonly-style").removeClass("div-readonly-style");
			$("#"+this.id+"_gys").addClass("readonly-style").removeClass("div-readonly-style");
		}
		this.initfjzchSelect();
		this.validation();
		if(this.param.workpackageId !=''){
			this.setData(this.param.obj);
		}else{
			TimeUtil.getCurrentDate("#"+this.id+"_zdrq");
			$("#"+this.id+"_gbzt_div").hide();
			$("#"+this.id+"_gbbh").attr("disabled",false);
			$("#"+this.id+"_xfdwid").val(StringUtil.escapeStr(currentUser.department==null?"":currentUser.department.id));
			$("#"+this.id+"_xfdw").val(StringUtil.escapeStr(currentUser.department==null?"":currentUser.department.dprtname));
			this.checkRadio(0);
			$("#workpackage_modal_yjzxdw_bmdiv").show();
			$("#workpackage_modal_yjzxdw_gysdiv").hide();
			this.getFjjx();
		}
		$('.date-picker').datepicker('update');
		$('input:radio[name="workpackage_modal_yjzxdw_radio"]').click( function(){
			yjzxdw = $(this).val();
				if(yjzxdw==0){
					$("#workpackage_modal_yjzxdw_bmdiv").show();
					$("#workpackage_modal_yjzxdw_gysdiv").hide();
					$("#workpackage_modal_gysid").val("");
					$("#workpackage_modal_gys").val("");
				}else{
					$("#workpackage_modal_yjzxdw_bmdiv").hide();
					$("#workpackage_modal_yjzxdw_gysdiv").show();
					$("#workpackage_modal_yjzxdw").val("");
					$("#workpackage_modal_yjzxdwid").val("");
				}
		});
		$("#workpackage_modal").on("hidden.bs.modal", function() {
			$("#workpackage_modal_form").data('bootstrapValidator').destroy();
			$('#workpackage_modal_form').data('bootstrapValidator', null);
			workpackage_modal.validation();
		});
		if(this.param.mark && this.param.workpackageId ==''){
			$("#"+this.id+"_fjzch").attr("disabled",false);
		}else{
			$("#"+this.id+"_fjzch").attr("disabled",true);
		}
		if(this.param.modalShow){
			AlertUtil.hideModalFooterMessage();
			$("#"+this.id).modal("show");
		}	
	},
	getFjjx:function(){
		var this_ = this;
		var param = {};
		param.fjzch=$("#"+this_.id+"_fjzch").val();
		param.dprtcode=this.param.dprtcode;
		AjaxUtil.ajax({
			   url:basePath+"/aircraftinfo/getFj",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(param),
			   success:function(data){
				   if(null == data){
					   $("#"+this_.id+"_msn").val("");
					   $("#"+this_.id+"_jx").val("");  
				   }else{
					   $("#"+this_.id+"_fjzch").val(data.fjzch);				  
					   $("#"+this_.id+"_msn").val(data.xlh);
					   $("#"+this_.id+"_jx").val(data.fjjx);  
				   }
		      }
		}); 
	},
	changeFj :function(){
		this.getFjjx();
	},
	checkRadio:function(obj){
		var this_=this;
		var xfdw = document.getElementsByName(this_.id+"_yjzxdw_radio");
		for(var i=0;i<xfdw.length;i++){
			if(xfdw[i].value==obj){
				xfdw[i].checked='checked';
			}
		}
	},
	setData:function(data){
		var this_ = this;
		$("#"+this_.id+"_fjzch").attr("disabled","disabled");
		$("#"+this_.id+"_gbbh").attr("disabled","disabled");
		$("#"+this.id+"_gbzt_div").show();
		$("#"+this_.id+"_fjzch").val(data.fjzch);
		$("#"+this_.id+"_msn").val(data.aircrafInfo==null?"":data.aircrafInfo.xlh);
		$("#"+this_.id+"_jx").val(data.aircrafInfo==null?"":data.aircrafInfo.fjjx);
		$("#"+this_.id+"_gbbh").val(data.gbbh);
		$("#"+this_.id+"_ms").val(data.gbmc);
		$("#"+this_.id+"_zdrq").val(data.zdrq==null?"":data.zdrq.substring(0,10));
		$("#"+this_.id+"_zdrq").attr("disabled","disabled");
		$("#"+this_.id+"_jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		$("#"+this_.id+"_jhwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
		$("#"+this_.id+"_wxlx").val(data.wxlx);
		var yjzxdw=data.jhWwbs;
		this_.checkRadio(yjzxdw)
		if(yjzxdw==0){
			$("#workpackage_modal_yjzxdw_bmdiv").show();
			$("#workpackage_modal_yjzxdw_gysdiv").hide();
			$("#"+this_.id+"_yjzxdwid").val(data.jhZxdwid);
			$("#"+this_.id+"_yjzxdw").val(data.jhZxdw);
			$("#"+this_.id+"_yjzxdw_do").val(data.jhZxdw);
		}else{
			$("#workpackage_modal_yjzxdw_bmdiv").hide();
			$("#workpackage_modal_yjzxdw_gysdiv").show();
			$("#"+this_.id+"_gysid").val(data.jhZxdwid);
			$("#"+this_.id+"_gys").val(data.jhZxdw);
		}
		$("#"+this_.id+"_xfdwid").val(StringUtil.escapeStr(data.gbxfdwid));
		$("#"+this_.id+"_xfdw").val(StringUtil.escapeStr(data.xfdwDepartment==null?"":data.xfdwDepartment.dprtname));
		$("#"+this_.id+"_gbzt").val(DicAndEnumUtil.getEnumName('workpackageStatusEnum', data.zt));
		$("#"+this_.id+"_gzyq").val(data.gzyq);
	},
	//飞机搜索框
	initfjzchSelect:function(){
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this.param.dprtcode});
		var planeRegOption = '';
		var value="";
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				if(i==0){
					value=StringUtil.escapeStr(planeData.FJZCH);
				}
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		planeRegOption += "<option value='' >"+"N/A"+"</option>";
		$("#"+this.id+"_fjzch").html(planeRegOption);
		if(this.param.fjzch==null||this.param.fjzch==""){
			$("#"+this.id+"_fjzch").val(value);
		}else{
			$("#"+this.id+"_fjzch").val(this.param.fjzch);			
		}
	},
	openzrdw : function(obj,jdbs){
		var this_ = this;
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:jdbs,
			type : false,// 勾选类型true为checkbox,false为radio
			dprtcode : this_.param.dprtcode,// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+obj).val(data.dprtname);
				$("#"+this_.id+"_"+obj+"id").val(data.dprtcode);
				if(jdbs=='1'){
					$("#"+this_.id+"_"+obj+"_do").val(data.dprtname);
				}
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
		if(this_.param.workpackageId!=''){
			param.id=this_.param.workpackageId;
			param.dprtcode=this_.param.dprtcode;
			param.zt=this_.param.obj.zt;
		}
		param.fjzch=$.trim($("#"+this_.id+"_fjzch").val());
		param.gbbh=$.trim($("#"+this_.id+"_gbbh").val());
		
		
		param.gbmc=$.trim($("#"+this_.id+"_ms").val());
		param.gbxfdwid=$.trim($("#"+this_.id+"_xfdwid").val());
		param.wxlx=$.trim($("#"+this_.id+"_wxlx").val());
		param.zdrq=$.trim($("#"+this_.id+"_zdrq").val());
		var jhWwbs=$.trim($("input[name='"+this_.id+"_yjzxdw_radio']:checked").val());
		param.jhWwbs=jhWwbs;
		if(jhWwbs==0){
			if($.trim($("#"+this_.id+"_yjzxdw").val())==$.trim($("#"+this_.id+"_yjzxdw_do").val())){
				param.jhZxdw =$.trim($("#"+this_.id+"_yjzxdw").val());
				param.jhZxdwid=$.trim($("#"+this_.id+"_yjzxdwid").val());
			}else{
				param.jhZxdw =$.trim($("#"+this_.id+"_yjzxdw").val());
			}
			
		}else{
			param.jhZxdwid=$.trim($("#"+this_.id+"_gysid").val());
			param.jhZxdw =$.trim($("#"+this_.id+"_gys").val());
		}				
		param.jhKsrq=$.trim($("#"+this_.id+"_jhksrq").val());
		param.jhJsrq=$.trim($("#"+this_.id+"_jhwcrq").val());
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
					AlertUtil.showConfirmMessage("工包编号为空，是否需要自动生成?", {callback: function(){
						setTimeout(function(){
							this_.param.callback(data);
						},500);
					}});
				}else{
					this_.param.callback(data);
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
				workpackage_modal_ms : {
					validators : {
						notEmpty : {
							message : '描述不能为空'
						},
					}
				}
			}
		})
	}
}

