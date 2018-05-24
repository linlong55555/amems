annual_plan_module = {
	id:"annual_plan_module",
	modelId : "annual_plan_alert_Modal",
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
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
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
				   $("#"+this_.id+"_fjzch").val(data.fjzch);
				   $("#"+this_.id+"_msn").val(data.xlh);
				   $("#"+this_.id+"_jx").val(data.fjjx);  
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
			planeRegOption += "<option value='-' >"+"N/A"+"</option>";
		}else{
			planeRegOption += "<option value='' >"+"暂无飞机"+"</option>";
		}
		$("#"+this.id+"_fjzch").html(planeRegOption);
		if(this.param.fjzch==null||this.param.fjzch==""){
			$("#"+this.id+"_fjzch").val(value);
		}else{
			$("#"+this.id+"_fjzch").val(this.param.fjzch);			
		}
	},
	/**
	 * 切换类型
	 */
	changeType : function(){
		var lx = $("#"+this.modelId+" input:radio[name='"+this.modelId+"_lx']:checked").val(); 	//类型
		if(lx == 1){
			$("#shdxBtn", $("#"+this.modelId)).show();
			$("#annual_plan_module_shdx", $("#"+this.modelId)).attr("readonly", "true");
			$("#annual_plan_module_shdx", $("#"+this.modelId)).addClass("readonly-style");
		}else{
			$("#shdxBtn", $("#"+this.modelId)).hide();
			$("#annual_plan_module_shdx", $("#"+this.modelId)).removeAttr("readonly");
			$("#annual_plan_module_shdx", $("#"+this.modelId)).removeClass("readonly-style");
		}
		$("#"+this.id+"_shdxid").val("");
		$("#"+this.id+"_shdxbh").val("");
		$("#"+this.id+"_shdxmc").val("");
		$("#"+this.id+"_shdx").val("");
	},
	openzrdw : function(obj,jdbs){
		var this_ = this;
		var lx = $("#"+this.modelId+" input:radio[name='"+this.modelId+"_lx']:checked").val(); 	//类型
		if(lx == 2){
			return false;
		}
		var dprtname = $("#"+this_.id+"_"+obj).val();
		var dprtcode = $("#"+this_.id+"_"+obj+"id").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			jdbs:jdbs,
			type : false,// 勾选类型true为checkbox,false为radio
			dprtcode : $("#dprtcode").val(),//
			callback : function(data) {// 回调函数
				$("#"+this_.id+"_"+obj).val(data.dprtbm+" "+data.dprtname);
				$("#"+this_.id+"_"+obj+"bh").val(data.dprtbm);
				$("#"+this_.id+"_"+obj+"mc").val(data.dprtname);
				$("#"+this_.id+"_"+obj+"id").val(data.dprtcode);
				if(obj != 'shdx_search'){
					$('#annual_plan_alert_Modal_from').data('bootstrapValidator')  
		 		      .updateStatus(""+this_.id+"_"+obj+"", 'NOT_VALIDATED',null)  
		 		      .validateField(""+this_.id+"_"+obj+""); 
				}
			}
		})
	},
	openUser : function(obj){
		var this_=this;
		var userList = [];
		var a = $.trim($("#"+this_.id+"_"+obj+"id").val());
		var b = $.trim($("#"+this_.id+"_"+obj).val());
		for (var i = 0; i < a.split(",").length; i++) {
			var p = {
				id : a.split(",")[i],
				displayName : b.split(",")[i]
			};
			userList.push(p);
		}
		if (a == "") {
			userList = [];
		}
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:$("#dprtcode").val(),//
			parentWinId : "xlhExistModal",
			callback: function(data){//回调函数
				var displayName = '';
				var mjsrid = '';
				if(data != null && data != ""){
					$.each(data, function(i, row){
						displayName += formatUndefine(row.displayName) + ",";
						mjsrid += formatUndefine(row.id) + ",";
					});
				}
				if(displayName != ''){
					displayName = displayName.substring(0,displayName.length - 1);
				}
				if(mjsrid != ''){
					mjsrid = mjsrid.substring(0,mjsrid.length - 1);
				}
				$("#"+this_.id+"_"+obj).val(displayName);
				$("#"+this_.id+"_"+obj+"id").val(mjsrid);
			}
		});
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
				this_.param.callback(data);
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
						notEmpty : {
							message : '工包编号不能为空'
						},
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

