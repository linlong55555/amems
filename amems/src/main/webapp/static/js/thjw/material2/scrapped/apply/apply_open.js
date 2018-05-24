apply_open = {
	id:"apply_open",
	data:[],
	param: {
		obj : {},
		fjzch:null,
		dprtcode:userJgdm,
		id:'',
		operation:true,
		view:false,
		modalName:'',
		modalEname:'',
		modalShow:true,
		callback:function(){}
	},
	init:function(param){
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : false
		});
		$("#"+this.id+"_modal input[type='text']").val("");
		$("#"+this.id+"_modal input[type='hidden']").val("");
		$("#"+this.id+"_modal textarea").val("");
		this.param.obj = {};
		this.param.id="";
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id+"_name").html(this.param.modalName);
		$("#"+this.id+"_ename").html(this.param.modalEname);
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this.id+'_attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.id,
			fileHead : this.param.operation,
			colOptionhead : this.param.operation,
			fileType:"apply",
		});//显示附件
		this.validation();
		apply_info.init({ //报废部件信息
			id:this.param.id
		});
		if(this.param.id !=''){
			this.setData(this.param.obj);
		}else{
			TimeUtil.getCurrentDate("#"+this.id+"_bfrq");
			$("#"+this.id+"_sqrid").val(currentUser.id);
			$("#"+this.id+"_sqr").val(currentUser.username+" "+currentUser.realname);
			$("#"+this.id+"_bfdh").attr("disabled",false);
			var str = "#apply_open_modal #processRecord_list";
			$(str).empty();
			$(str).append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
		}
		$('#apply_open_modal .date-picker').datepicker('update');
		$("#apply_open_modal").on("hidden.bs.modal", function() {
			$("#apply_open_form").data('bootstrapValidator').destroy();
			$('#apply_open_form').data('bootstrapValidator', null);
			apply_open.validation();
		});
		if(this.param.modalShow){
			AlertUtil.hideModalFooterMessage();
			$("#"+this.id+"_modal").modal("show");
		}	
	},
	openUser : function(){
		var this_ = this;
		var userList = [];
		var a = $("#apply_open_shrid").val();
		var b = $("#apply_open_shr").val();
		for (var i = 0; i < a.split(",").length; i++) {
			var p = {
				id : a.split(",")[i],
				displayName : b.split(",")[i]
			};
			userList.push(p);
		}
		if (a == "") {
			userList = "";
		}
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:this_.param.dprtcode,//组织编码
			multi:false, //单选
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
				$("#apply_open_shr").val(displayName);
				$("#apply_open_shrid").val(mjsrid);
			}
		});
	},
	setData:function(data){
		var this_ = this;
		$("#"+this_.id+"_bfdh").attr("disabled","disabled");
		$("#"+this_.id+"_bfdh").val(data.bfdh);
		$("#"+this_.id+"_bfrq").val(data.bfrq?data.bfrq.substring(0,10):"");
		$("#"+this_.id+"_sm").val(data.sm);
		$("#"+this_.id+"_sqrid").val(data.sqrid);
		$("#"+this_.id+"_sqr").val(data.sqr?(data.sqr.username+" "+data.sqr.realname):"");
		$("#"+this_.id+"_shrid").val(data.sprid);
		$("#"+this_.id+"_shr").val(data.shr?(data.shr.username+" "+data.shr.realname):"");
		if(data.zt == 5){
			var str = DicAndEnumUtil.getEnumName('applyStatusEnum', data.zt)+" ";
			str  += data.spyj;
			$("#"+this_.id+"_zt").val(str);
		}else{
			$("#"+this_.id+"_zt").val(DicAndEnumUtil.getEnumName('applyStatusEnum', data.zt));
		}
		measures_record.show({//流程信息
			id:data.id,
			dprtcode:data.dprtcode,
			modal:this_.id+"_modal"
		});
	},
	getData:function(){
		var this_=this;
		var param={};
		if(this_.param.id!=''){
			param.id=this_.param.id;
			param.dprtcode=this_.param.dprtcode;
			param.zt=this_.param.obj.zt;
		}
		param.bfdh=$.trim($("#"+this_.id+"_bfdh").val());
		param.bfrq=$.trim($("#"+this_.id+"_bfrq").val());
		param.sm = $.trim($("#"+this_.id+"_sm").val());
		param.sqbmid = $.trim($("#"+this_.id+"_sqbmid").val());
		param.sqrid = $.trim($("#"+this_.id+"_sqrid").val());
		param.sprid = $.trim($("#"+this_.id+"_shrid").val());
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this_.id+'_attachments_list_edit');
		param.attachments = attachmentsObj.getAttachments();
		var infoList = apply_info.getData();
		param.infoList = infoList;
		return param;
	},
	saveData:function(zt){
		$("#"+this.id+"_form").data('bootstrapValidator').validate();
		if (!$("#"+this.id+"_form").data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
			return false;
		}
		if($.trim($("#"+this.id+"_shr").val())== "" || $.trim($("#"+this.id+"_shr").val())== null){
			AlertUtil.showModalFooterMessage("审核人不能为空!");
			return false;
		}
		var data = this.getData();
		if(!apply_info.isValid){
			return false;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
				var this_ = this;
				if(this_.param.obj){
					data.zt = zt;
				}else{
					data.zt = this_.param.obj.zt;
				}
				if(data.bfdh==""){  
					AlertUtil.showConfirmMessage("报废单号为空，是否需要自动生成?", {callback: function(){
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
				apply_open_bfdh : {
					validators : {
						regexp : {
							regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,50}$/,
							message : '只能输入长度不超过50个字符的英文、数字、英文特殊字符!'
						}
					}
				},
				apply_open_bfrq : {
					validators : {
						notEmpty : {
							message : '报废日期不能为空'
						},
					}
				}
			}
		})
	},
}

