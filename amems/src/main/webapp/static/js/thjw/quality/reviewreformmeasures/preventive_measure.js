
	var preventive_measure = {
			
		formId : 'preventive_measure_form',
		
		isValid:true,
		// 页面初始化
		init : function(){
			
			var this_ = this;
			// 加载数据
			this_.loadData();
			//加载验证
			this_.validation();
			
			$("#"+this_.param.modal +" #review_reform_measures_alert_Modal").on("hidden.bs.modal", function() {
				$("#"+this_.param.modal+ " #preventive_measure_form").data('bootstrapValidator').destroy();
				$("#"+this_.param.modal+ " #preventive_measure_form").data('bootstrapValidator', null);
				preventive_measure.validation();
			});
		},
		
		param : {
			data:{},
			option:true,//可编辑
			modal:null,//嵌套的modal
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			this.init();
		},		
		
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;			
			AlertUtil.hideModalFooterMessage(this_.id);
			$("#"+this_.param.modal+ " #yyfx").val(data.yyfx);				
			$("#"+this_.param.modal+ " #jzcs").val(data.jzcs);
			$("#"+this_.param.modal+ " #yfcs").val(data.yfcs);
			$("#"+this_.param.modal+ " #wtfkr").val(data.fkr?(data.fkr.username+" "+data.fkr.realname):"");
			$("#"+this_.param.modal+ " #wtfksj").val(data.wtfksj);
			if(this_.param.option){
				$("#"+this_.param.modal+ " #wtfkr_div").hide();
				$("#"+this_.param.modal+ " #wtfksj_div").hide();
				$("#"+this_.param.modal+ " #yyfx").attr("disabled",false);				
				$("#"+this_.param.modal+ " #jzcs").attr("disabled",false);
				$("#"+this_.param.modal+ " #yfcs").attr("disabled",false);
			}else{
				$("#"+this_.param.modal+ " #wtfkr_div").show();
				$("#"+this_.param.modal+ " #wtfksj_div").show();
				$("#"+this_.param.modal+ " #yyfx").attr("disabled",true);				
				$("#"+this_.param.modal+ " #jzcs").attr("disabled",true);
				$("#"+this_.param.modal+ " #yfcs").attr("disabled",true);
			}
		},
		// 获取数据
		getData : function(){
			var this_= this;
			$("#"+this_.param.modal+ " #preventive_measure_form").data('bootstrapValidator').validate();
			if (!$("#"+this_.param.modal+ " #preventive_measure_form").data('bootstrapValidator').isValid()) {
				AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
				this_.isValid = false;
				return;
			}else{
				this_.isValid = true;
			}
			var obj = {};
			obj.yyfx = $.trim($("#"+this_.param.modal+ " #yyfx").val());				
			obj.jzcs = $.trim($("#"+this_.param.modal+ " #jzcs").val());
			obj.yfcs = $.trim($("#"+this_.param.modal+ " #yfcs").val());
			return obj;
		},
		validation:function(){
			var this_=this;
			validatorForm = $("#"+this_.param.modal+ " #preventive_measure_form").bootstrapValidator({
				message : '数据不合法',
				feedbackIcons : {
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					yyfx : {
						validators : {
							notEmpty : {
								message : '根本原因分析不能为空'
							}
						}
					},
					jzcs : {
						validators : {
							notEmpty : {
								message : '纠正措施及完成计划不能为空'
							},
						}
					},
					yfcs : {
						validators : {
							notEmpty : {
								message : '预防措施不能为空'
							},
						}
					},
				}
			})
		}
	};
	
	