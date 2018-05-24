workpackage_end_modal = {
	id:"workpackage_end",
	data:[],
	param: {
		obj:{},
		dprtcode:userJgdm,
		workpackageId:null,
		operation:true,		
		callback:function(){}
	},
	init:function(param){
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+"_zdjsyy").val("");
		if(param){
			$.extend(this.param, param);
		};		
		this.validation();
		this.fillData(this.param.obj);
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id+"_modal").modal("show");
		$("#"+this.id+"_modal").on("hidden.bs.modal", function() {
			$("#workpackage_end_form").data('bootstrapValidator').destroy();
			$("#workpackage_end_form").data('bootstrapValidator', null);
			workpackage_end_modal.validation();
		});
	},
	fillData:function(data){
		var this_ = this;
		$("#"+this_.id+"_fjzch").val(data.fjzch);
		$("#"+this_.id+"_msn").val(data.aircrafInfo==null?"":data.aircrafInfo.xlh);
		$("#"+this_.id+"_jx").val(data.aircrafInfo==null?"":data.aircrafInfo.fjjx);
		$("#"+this_.id+"_gbbh").val(data.gbbh);
		$("#"+this_.id+"_ms").val(data.gbmc);
		$("#"+this_.id+"_xfdw").val(StringUtil.escapeStr(data.xfdwDepartment==null?"":data.xfdwDepartment.dprtname));
		$("#"+this_.id+"_jhksrq").val(data.jhKsrq==null?"":data.jhKsrq.substring(0,10));
		$("#"+this_.id+"_jhwcrq").val(data.jhJsrq==null?"":data.jhJsrq.substring(0,10));
		if(this_.param.operation){
			$("#"+this_.id+"_zdjsyy").attr("disabled",false);
			$("#"+this_.id+"_zdjsyy_remark").show();
			$("#"+this_.id+"_savebutton").show();
			$("#"+this_.id+"_gbr").val(displayName);
			if(data.gbrq){
				$("#"+this_.id+"_gbrq").val(data.gbrq);
			}else{
				TimeUtil.getCurrentTime("#"+this_.id+"_gbrq");
			}
			$("#"+this_.id+"_zdjsyy").val(data.gbyy);
		}else{
			$("#"+this_.id+"_zdjsyy").attr("disabled",true);
			$("#"+this_.id+"_zdjsyy_remark").hide();
			$("#"+this_.id+"_savebutton").hide();
			$("#"+this_.id+"_gbr").val(data.gbr.realname+" "+data.gbr.username);
			$("#"+this_.id+"_gbrq").val(data.gbrq);
			$("#"+this_.id+"_zdjsyy").val(data.gbyy);
		}				
	},
	getData:function(){
		var this_=this;
		var param={};
		param.id=this_.param.workpackageId;
		param.gbyy=$.trim($("#"+this_.id+"_zdjsyy").val());
		return param;
	},
	saveData:function(){
		$("#"+this.id+"_form").data('bootstrapValidator').validate();
		if (!$("#"+this.id+"_form").data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
			return false;
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
				workpackage_end_zdjsyy : {
					validators : {
						notEmpty : {
							message : '指定结束原因不能为空'
						}
					}
				}
			}
		})
	}
}

