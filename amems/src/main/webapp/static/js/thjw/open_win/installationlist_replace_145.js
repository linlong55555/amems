	
	$(function(){
		// 初始化控件
		open_win_installationlist_replace.initWidget();
		// 绑定事件
		open_win_installationlist_replace.bindEvent();
		// 弹出窗验证销毁
		$("#"+open_win_installationlist_replace.id).on("hidden.bs.modal", function() {
			$('#'+open_win_installationlist_replace.formId).data('bootstrapValidator').destroy();
			$('#'+open_win_installationlist_replace.formId).data('bootstrapValidator', null);
			AlertUtil.hideModalFooterMessage();
		});
	});

	/**
	 * 拆换记录弹窗
	 */
	var open_win_installationlist_replace = {
			
		id : 'open_win_installationlist_replace',
		
		formId : 'open_win_installationlist_replace_form',
		
		data : [],
		
		param : {
			fjzch:'', //飞机
			jx:'',//机型
			fdjsl:2,
			data:{},
			wbbs:1,//外部标识
			ope_type:1,//操作类型
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			this.init();
			$("#"+this.id).modal("show");
		},
		
		// 打开装上件弹窗
		openInstalledWin : function(){
			var this_ = this;
			open_win_installationlist_installed.show({
				//selected:$("#replace_zsbjid").val(),//原值，在弹窗中默认勾选
				dprtcode:this_.param.dprtcode, //机构代码
				callback: function(data){//回调函数
					if(data != null){
						//$("#replace_zsbjid").val(data.id);
						$("#replace_zsbjh").val(data.bjh);
						$("#replace_zsxlh").val(data.sn);
						$("#replace_zspch").val(data.pch);
						$("#replace_zssl").val(data.kcsl);
						$("#replace_zsywmc").val(data.ywms);
						$("#replace_zszwmc").val(data.zwms);
						$("#replace_jldw").val(data.jldw);
						$("#replace_xh").val(data.xh);
						$("#replace_zspch").val(data.pch);
						$("#replace_cjjh").val(data.hcMainData ? data.hcMainData.cjjh : "");
						$("#replace_zjh_display").val(data.hcMainData ? (data.hcMainData.fixChapter ? data.hcMainData.fixChapter.displayName : ""): "");
						$("#replace_zjh_value").val(data.hcMainData ? (data.hcMainData.fixChapter ? data.hcMainData.fixChapter.zjh : ""): "");
					}
					if(data && data.bjh){
						$("#replace_zsbjh").attr("readonly","").addClass("readonly-style");
					}else{
						$("#replace_zsbjh").removeAttr("readonly").removeClass("readonly-style");
					}
					this_.limitZjsl();
				}
			});
		},
		
		// 手工输入装上件
		manualInputInstalled : function(){
			var this_ = this;
		},
		
		// 手工输入完成装上件
		finishInputInstalled : function(){
			var this_ = this;
			// 加载航材数据
			this_.loadMaterialData();
		},
		
		// 加载航材数据
		loadMaterialData : function(){
			var this_ = this;
			var bjh = $.trim($("#replace_zsbjh").val());
			if(bjh){
				AjaxUtil.ajax({
					url:basePath+"/material/material/selectByBjhAndDprcode",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						bjh : bjh,
						dprtcode : this_.param.dprtcode
					}),
					success:function(data){
						finishWait();
						if(data.hCMainData){
							row = data.hCMainData;
							$("#replace_zsywmc").val(row.ywms||"");
							$("#replace_zszwmc").val(row.zwms||"");
							$("#replace_cjjh").val(row.cjjh);
							$("#replace_xh").val(row.xingh);
							$("#replace_jldw").val(row.jldw);
							
							$("#replace_zjh_display").val(row.fixChapter ? row.fixChapter.displayName : "");
							$("#replace_zjh_value").val(row.zjh);
						}
				    }
				}); 
			}
		},
		
		// 打开拆下件弹窗
		openRemovedWin : function(){
			var this_ = this;
			var czrq = $.trim($("#replace_cxrq").val());
			if(!czrq){
				AlertUtil.showErrorMessage("请先填写拆装时间！");
				return false;
			}
			var czsj = $.trim($("#replace_cxsj").val());
			var czsjall = $.trim(czrq + " " + (czsj||"00:00") + ":00");
			open_win_installationlist_removed.show({
				selected:$("#replace_cxbjid").val(),//原值，在弹窗中默认勾选
				dprtcode:this_.param.dprtcode, //机构代码
				fjzch:this_.param.fjzch,//飞机注册号
				wz:$("#replace_wz").val(),//位置
				jssj : czsjall,
				fjdid:$("#replace_fjdid").val(),//父节点
				callback: function(data){//回调函数
					if(data != null){
						$("#replace_cxbjid").val(data.id);
						$("#replace_cxbjh").val(data.bjh);
						$("#replace_cxxlh").val(data.xlh);
						$("#replace_cxpch").val(data.pch);
						$("#replace_cxsl").val(data.zjsl);
						$("#replace_cxywmc").val(data.ywmc);
						$("#replace_cxzwmc").val(data.zwmc);
						if(data.wz!=null){
							$("#replace_wz").val(data.wz);
							$("#replace_wz").attr("disabled",true);
							
							$("#replace_fjdid").val(data.fjdid);
							$("#replace_fjd_cj").val(data.parent ? data.parent.cj : "0");
							$("#replace_fjd_name").val(data.parent ? data.parent.bjh : "N/A");
							$("#replace_fjd_name").attr("disabled",true).removeClass("readonly-style");
							$("#replace_fjd_btn").addClass("hidden");
						}else{
							$("#replace_wz option:first").prop("selected", 'selected');
							$("#replace_wz").attr("disabled",false);
							
							$("#replace_fjdid").val("");
							$("#replace_fjd_cj").val("");
							$("#replace_fjd_name").val("");
							$("#replace_fjd_name").attr("disabled",false).addClass("readonly-style");
							$("#replace_fjd_btn").removeClass("hidden")
						}					
					}
				}
			});
		},
		
		// 初始化
		init : function(){
			var this_ = this;
			$("#replace_wz").attr("disabled",false);
			// 初始化验证
			this_.initValidation();
			// 初始化数据字典
			this_.initDicAndEnum();
			// 生成位置
			this_.buildWz();
			// 填充数据
			this_.fillData();
			// 初始化类型
			this_.initType();
			// 初始化外部标识
			this_.initWbbs();
		},
		
		// 初始化类型
		initType : function(){
			var this_ = this;
			var type = this_.param.ope_type;
			$container = $("#"+this_.formId);
			if(type == "3"){
				$container.find("input,select,textarea").attr("disabled","disabled");
				$container.find(".btn").addClass("hidden");
				$("#replace_cxbjh,#replace_zsbjh,#replace_fjd_name,#replace_zjh_display").removeClass("readonly-style");
				$("#replace_confirm_btn").hide();
			}else{
				$container.find("input:not(#replace_zssl,#replace_cxsl,#replace_fjd_name),select,textarea").removeAttr("disabled");
				$container.find(".btn:not(#replace_fjd_btn)").removeClass("hidden");
				$("#replace_confirm_btn").show();
			}
		},
		
		// 初始化外部标识
		initWbbs : function(){
			var this_ = this;
			var wbbs = this_.param.wbbs;
			if(wbbs == 0){	//内部
				$("#replace_cxbjh,#replace_cxxlh,#replace_cxpch,#replace_cxsl,#replace_cxywmc,#replace_cxzwmc").attr("readonly","readonly");
				$("#replace_parent_div,#replace_cxbjh_btn,#replace_zsbjh_btn").show();
				$("#replace_cxbjh").addClass("readonly-style");
			} else if (wbbs == 1){	// 外部
				$("#replace_cxbjh,#replace_cxxlh,#replace_cxpch,#replace_cxsl,#replace_cxywmc,#replace_cxzwmc").removeAttr("readonly");
				$("#replace_parent_div,#replace_cxbjh_btn,#replace_zsbjh_btn").hide();
				$("#replace_cxbjh").removeClass("readonly-style");
			}
		},
		
		// 绑定事件
		bindEvent : function(){
			var this_ = this;
			$("#"+this_.id+" .validate-time").on("keyup", function(){
				// 回退时间
				this_.validateTime($(this));
			});
			$("#"+this_.id+" .validate-cycle").on("keyup", function(){
				// 回退循环
				this_.validateCycle($(this));
			});
			
			$("#"+this_.id+" .time-masked").on("blur", function(){
				// 回退循环
				this_.validateDateTime($(this));
			});
		},
		
		// 回退时间
		validateTime : function ($obj){
			this.backspace($obj, /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/, true);
		},
		
		// 回退循环
		validateCycle : function ($obj){
			this.backspace($obj, /^(0|[1-9]\d*)$/);
		},
		
		// 验证日期时间
		validateDateTime : function($obj){
			var value = $obj.val();
			if(!/^(([01][0-9])|(2[0-3]))\:[0-5][0-9]$/.test(value)){
				$obj.val("");
			}
		},
		
		// 回退
		backspace : function(obj, reg, replace){
			var value = obj.val();
			if(replace){
				value = value.replace(/：/g, ":");
			}
			while(!(reg.test(value)) && value.length > 0){
				value = value.substr(0, value.length-1);
		    }
			obj.val(value);
			
		},
		
		// 生成位置
		buildWz : function(){
			var this_ = this;
			$("#replace_wz").empty();
			
			$("#replace_wz").append("<option value='11' >机身</option>");
			
			for(var i = 0; i < parseInt(this_.param.fdjsl); i++){
				$("#replace_wz").append("<option value='2"+(i+1)+"'>"+(i+1)+"#发</option>");
			}
			$("#replace_wz").append("<option value='31'>APU</option>");
			
			var wz = $("#replace_fjd_wz").val();
			if(wz){
				$("#replace_wz").find("option[value!='"+wz+"']").remove();
			}
		},
		
		// 初始化控件
		initWidget : function(){
			
			var this_ = this;
			
			// 初始化日期控件
			$('#'+this_.id+' .date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			
			// 初始化输入框内容限制控件
			$('#'+this_.id+' .time-masked').mask("99:99");
			
		},
		
		// 初始化验证
		initValidation : function(){
			var this_ = this;
			$('#'+this_.formId).bootstrapValidator({
		        message: '数据不合法',
		        excluded: [':disabled'],
		        feedbackIcons: {
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	cxrq: {
		                validators: {
		                	callback: {
		                        message: '拆下日期不能为空',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_cxbjh').val()){
		                        		return true;
		                        	}
		                        	if($.trim(value)){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                	}
		                }
		            },
		            zsrq: {
		                validators: {
		                	callback: {
		                        message: '装上日期不能为空',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val()){
		                        		return true;
		                        	}
		                        	if($.trim(value)){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                	}
		                }
		            },
		            replace_cxbjh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_cxxlh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_cxpch: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_cxsl: {
		                validators: {
		                	callback: {
		                        message: '拆下数量不能为空,只能输入大于1的正整数',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_cxbjh').val()){
		                        		return true;
		                        	}		                        	
		                        	return /^([1-9]|([1-9]\d+))$/.test(value);
		                        }
		                	}
		                }
		            },
		            replace_cxywmc: {
		                validators: {
		                    callback: {
		                        message: '拆下件的英文名称不能为空',
		                        callback: function(value, validator) {
		                        	if(this_.param.wbbs != 1){
		                        		return true;
		                        	}
		                        	if(!$('#replace_cxbjh').val()){
		                        		return true;
		                        	}
		                        	if($.trim(value)){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                	}
		                }
		            },
		            replace_zsbjh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_zsxlh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_zspch: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            replace_zssl: {
		                validators: {
		                	callback: {
		                        message: '装上数量不能为空,只能输入大于1的正整数',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val()){
		                        		return true;
		                        	}		                        	
		                        	return /^([1-9]|([1-9]\d+))$/.test(value);
		                        }
		                	}
		                }
		            },
		            replace_zsywmc: {
		                validators: {
		                    callback: {
		                        message: '装上件的英文名称不能为空',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val()){
		                        		return true;
		                        	}
		                        	if($.trim(value)){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                	}
		                }
		            },
		            replace_fjd_name: {
		            	validators: {
		                	callback: {
		                        message: '上级部件不能为空',
		                        callback: function(value, validator) {
		                        	if(this_.param.wbbs == 1){
		                        		return true;
		                        	}
		                        	if($.trim(value)){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                    }
		                }
		            },
		        }
		    });
			
			$('#replace_cxrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus("cxrq", 'NOT_VALIDATED',null)
			     .validateField("cxrq"); 
		    });
			
			$('#replace_zsrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus("zsrq", 'NOT_VALIDATED',null)
			     .validateField("zsrq"); 
		    });
			
		},
		
		// 打开上级部件弹窗
		openParentWin : function(){
			var this_ = this;
			var czrq = $.trim($("#replace_zsrq").val());
			if(!czrq){
				AlertUtil.showErrorMessage("请先填写装上时间！");
				return false;
			}
			var czsj = $.trim($("#replace_zssj").val());
			var czsjall = $.trim(czrq + " " + (czsj||"00:00") + ":00");
			installationlist_parent.show({
				selected : $("#replace_fjdid").val(),//原值，在弹窗中默认勾选
				dprtcode : this_.param.dprtcode,
				fjzch : this_.param.fjzch,
				wz : $("#replace_wz").val(),
				jssj : czsjall,
				//id : $("#replace_fjdid").val(),
				callback : function(data){//回调函数
					if(data != null){
						this_.setParent(data);
						var wz = $("#replace_wz").val();
						this_.buildWz();
						$("#replace_wz").val(wz);
						$('#'+this_.formId).data('bootstrapValidator')  
					     .updateStatus('replace_fjd_name', 'NOT_VALIDATED',null)
					     .validateField('replace_fjd_name'); 
					}
				}
			});
		},
		
		// 设置父节点
		setParent : function(data){
			$("#replace_fjdid").val(data.id||"");
			$("#replace_fjd_name").val(data.bjh||"");
			$("#replace_fjd_cj").val(data.cj||"");
			$("#replace_fjd_wz").val(data.wz||"");
		},
		
		// 序列号改变事件
		onblurXlh : function(){
			var this_ = this;
			//限制数量输入
			this_.limitZjsl()
		},
		
		// 打开飞机站位弹窗
		openStationWin : function (){
			var this_ = this;
			PositionModal.show({
				selected : this_.param.positionList,//原值，在弹窗中默认勾选
				dprtcode : this_.param.dprtcode,
				jx : this_.param.jx,
				callback : function(data){//回调函数
					var str = '';
					this_.param.positionList = [];
					if(data != null && data.length > 0){
						$.each(data,function(index,row){
							this_.param.positionList.push(row);
							str += formatUndefine(row.sz) + ",";
						});
					}
					if(str != ''){
						str = str.substring(0,str.length - 1);
					}
					$("#replace_fjzw").val(str);
				}
			});
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			var obj = {};
			
			/* 通用 */
			obj.id = $.trim($("#replace_id").val());
			obj.rowid = $.trim($("#replace_rowid").val());
			obj.gdczjlid = $.trim($("#replace_gdczjlid").val());
			if(this_.param.data.sgbs != 0){
				obj.sgbs = 1;
			}
			var czwz = $.trim($("#replace_wz").val());
			var czyy = $.trim($("#replace_czyy").val());
			obj.zrrid = $.trim($("#replace_azr_value").val());
			obj.zrr = $.trim($("#replace_azr_display").val());
			
			/* 拆下件 */
			var cxjbjh = $.trim($("#replace_cxbjh").val());
			if(cxjbjh){
				var cxj = {};
				
				var czrq = $.trim($("#replace_cxrq").val());
				var czsj = $.trim($("#replace_cxsj").val());
				var czsjall = $.trim(czrq + " " + (czsj||"00:00") + ":00");
				
				obj.cxWckcid = $.trim($("#replace_cxWckcid").val());
				obj.cxKclvid = $.trim($("#replace_cxKclvid").val());
				cxj.id = $.trim($("#replace_cxbjid").val());
				cxj.bjh = cxjbjh;
				cxj.xlh = $.trim($("#replace_cxxlh").val());
				cxj.pch = $.trim($("#replace_cxpch").val());
				cxj.zjsl = $.trim($("#replace_cxsl").val());
				cxj.ywmc = $.trim($("#replace_cxywmc").val());
				cxj.zwmc = $.trim($("#replace_cxzwmc").val());
				cxj.wz = czwz;
				cxj.parent = {
					bjh : $.trim($("#replace_fjd_name").val()),
					wz : $.trim($("#replace_fjd_wz").val()),
					cj : $.trim($("#replace_fjd_cj").val()),
				};
				cxj.cj = parseInt(cxj.parent.cj) + 1;
				obj.cxj = cxj;
				obj.cxSj = czsjall;
				obj.cxZjqdid = cxj.id;
				obj.cxBs = 1;
				obj.cxBz = czyy;
				obj.cxBjh = cxj.bjh;
				obj.cxXlh = cxj.xlh;
				obj.cxPch = cxj.pch;
				obj.cxCjsl = cxj.zjsl;
				obj.cxBjzwmc = cxj.zwmc;
				obj.cxBjywmc = cxj.ywmc;
				obj.cxWz = cxj.wz;
			}else{
				obj.cxBs = 0;
				obj.cxSj = '';
				obj.cxZjqdid = '';
				obj.cxBz = '';
				obj.cxBjh = '';
				obj.cxXlh = '';
				obj.cxPch = '';
				obj.cxCjsl = '';
				obj.cxBjzwmc = '';
				obj.cxBjywmc = '';
				obj.cxWz = '';
			}
			
			/* 装上件 */
			var zsjbjh = $.trim($("#replace_zsbjh").val());
			if(zsjbjh){
				var zsj = {};
				
				var czrq = $.trim($("#replace_zsrq").val());
				var czsj = $.trim($("#replace_zssj").val());
				var czsjall = $.trim(czrq + " " + (czsj||"00:00") + ":00");
				
				obj.zsWckcid = $.trim($("#replace_zsWckcid").val());
				obj.zsKclvid = $.trim($("#replace_zsKclvid").val());
				zsj.id = $.trim($("#replace_zszjqdid").val());
				zsj.bjh = zsjbjh;
				zsj.xlh = $.trim($("#replace_zsxlh").val());
				zsj.pch = $.trim($("#replace_zspch").val());
				zsj.zjsl = $.trim($("#replace_zssl").val());
				zsj.ywmc = $.trim($("#replace_zsywmc").val());
				zsj.zwmc = $.trim($("#replace_zszwmc").val());
				zsj.azr = $.trim($("#replace_azr_display").val());
				zsj.azrid = $.trim($("#replace_azr_value").val());
				zsj.azsj = czsjall;
				zsj.wz = czwz;
				zsj.dprtcode = this_.param.dprtcode;
				zsj.fjzch = this_.param.fjzch;
				zsj.jx = this_.param.jx;
				
				zsj.zjh = $.trim($("#replace_zjh_value").val());
				
				zsj.paramsMap = {
					zjhname : $.trim($("#replace_zjh_display").val())
				};
				
				zsj.jldw = $.trim($("#replace_jldw").val());
				
				
				// 父节点
				zsj.parent = {
					id : $.trim($("#replace_fjdid").val()),
					bjh : $.trim($("#replace_fjd_name").val()),
					wz : $.trim($("#replace_fjd_wz").val()),
					cj : $.trim($("#replace_fjd_cj").val()),
				};
				zsj.fjdid = zsj.parent.id;
				zsj.cj = parseInt(zsj.parent.cj) + 1;
				
				zsj.fjzw =$("#replace_fjzw").val();
				zsj.azbz = czyy;
				obj.zsj = zsj;
				obj.zsSj = czsjall;
				obj.zsBs = 1;
				obj.zsBz = czyy;
			}else{
				obj.zsBs = 0;
			}
			
			return obj;
			
		},
		
		// 验证数据
		validataData : function(){
			var this_ = this;
			AlertUtil.hideModalFooterMessage();
			$('#'+this_.formId).data('bootstrapValidator').resetForm(false);
			$('#'+this_.formId).data('bootstrapValidator').validate();
			if(!$('#'+this_.formId).data('bootstrapValidator').isValid()){
				AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！", this_.id);
				return false;
			}
			var cxbjh = $.trim($("#replace_cxbjh").val());
			var zsbjh = $.trim($("#replace_zsbjh").val());
			if(!cxbjh && !zsbjh){
				AlertUtil.showModalFooterMessage("请选择一个装上/拆下件！", this_.id);
				return false;
			}
			
			var cxrq = $.trim($("#replace_cxrq").val());
			var cxsj = $.trim($("#replace_cxsj").val());
			var cxrqall = $.trim(cxrq + " " + (cxsj||"00:00") + ":00");
			
			var zsrq = $.trim($("#replace_zsrq").val());
			var zssj = $.trim($("#replace_zssj").val());
			var zsrqall = $.trim(zsrq + " " + (zssj||"00:00") + ":00");
			if(cxbjh && zsbjh && TimeUtil.compareDate(cxrqall, zsrqall)){
				AlertUtil.showModalFooterMessage("拆下时间不能大于装上时间！", this_.id);
				return false;
			}
			return true;
		},
		
		// 填充数据
		fillData : function(){
			
			var this_ = this;
			var obj = this_.param.data;
			obj.cxj = obj.cxj || {};
			obj.zsj = obj.zsj || {};
			$("#replace_id").val(obj.id);
			$("#replace_rowid").val(obj.rowid);
			$("#replace_gdczjlid").val(obj.gdczjlid);
			
			if(obj.cxSj){
				$("#replace_cxrq").val(obj.cxSj.substr(0, 10));
				$("#replace_cxsj").val(obj.cxSj.substr(11, 5));
			}else if(this_.param.defaultCzrq){
				$("#replace_cxrq").val(this_.param.defaultCzrq.substr(0, 10));
				$("#replace_cxsj").val(this_.param.defaultCzrq.substr(11, 5));
			}else{
				$("#replace_cxrq").val(this_.getNowFormatDate());
			}
			
			if(obj.zsSj){
				$("#replace_zsrq").val(obj.zsSj.substr(0, 10));
				$("#replace_zssj").val(obj.zsSj.substr(11, 5));
			}else if(this_.param.defaultCzrq){
				$("#replace_zsrq").val(this_.param.defaultCzrq.substr(0, 10));
				$("#replace_zssj").val(this_.param.defaultCzrq.substr(11, 5));
			}else{
				$("#replace_zsrq").val(this_.getNowFormatDate());
			}
			
			$("#replace_cxrq,#replace_zsrq").datepicker("update");
			$("#replace_czyy").val(obj.cxBz || obj.zsBz || "");
			$("#replace_azr_value").val(obj.zrrid);
			$("#replace_azr_display").val(obj.zrr);
			
			/* 拆下件 */
			if(obj.cxBjh){
				$("#replace_fjdid").val(obj.fjdid);
				$("#replace_fjd_cj").val(obj.cxj.parent && obj.cxj.parent.cj ? obj.cxj.parent.cj : "0");
				$("#replace_fjd_wz").val(obj.cxj.parent && obj.cxj.parent.wz ? obj.cxj.parent.wz : "");
				$("#replace_fjd_name").val(obj.cxj.parent && obj.cxj.parent.bjh ? obj.cxj.parent.bjh : "N/A");
				$("#replace_fjd_name").attr("disabled",true).removeClass("readonly-style");
				$("#replace_fjd_btn").addClass("hidden");
			}else{
				$("#replace_fjdid").val("");
				$("#replace_fjd_cj").val("");
				$("#replace_fjd_wz").val("");
				$("#replace_fjd_name").val("");
				$("#replace_fjd_name").attr("disabled",false).addClass("readonly-style");
				$("#replace_fjd_btn").removeClass("hidden");
			}
			$("#replace_cxbjid").val(obj.cxZjqdid);
			$("#replace_cxbjh").val(obj.cxBjh);
			$("#replace_cxxlh").val(obj.cxXlh);
			$("#replace_cxpch").val(obj.cxPch);
			$("#replace_cxsl").val(obj.cxCjsl);
			$("#replace_cxywmc").val(obj.cxBjywmc);
			$("#replace_cxzwmc").val(obj.cxBjzwmc);
			$("#replace_cxWckcid").val(obj.cxWckcid);
			$("#replace_cxKclvid").val(obj.cxKclvid);
			
			/* 装上件 */
			$("#replace_zszjqdid").val(obj.zsj.id);
			$("#replace_zsWckcid").val(obj.zsWckcid);
			$("#replace_zsKclvid").val(obj.zsKclvid);
			$("#replace_zsbjh").val(obj.zsj.bjh);
			$("#replace_zsxlh").val(obj.zsj.xlh);
			$("#replace_zspch").val(obj.zsj.pch);
			$("#replace_zssl").val(obj.zsj.zjsl);
			$("#replace_zsywmc").val(obj.zsj.ywmc);
			$("#replace_zszwmc").val(obj.zsj.zwmc);
			$("#replace_zjh_value").val(obj.zsj.zjh);
			$("#replace_zjh_display").val(obj.zsj.paramsMap?obj.zsj.paramsMap.zjhname:"");
			$("#replace_jldw").val(obj.zsj.jldw);
			if(obj.zsWckcid){
				$("#replace_zsbjh").attr("readonly","").addClass("readonly-style");
			}else{
				$("#replace_zsbjh").removeAttr("readonly").removeClass("readonly-style");
			}
			// 父节点
			obj.zsj.parent = obj.zsj.parent || {};
			if(obj.zsj.fjdid == 0){
				obj.zsj.parent = {
					id : "0",
					bjh : "N/A",
					cj : "0",
					wz : obj.wz
				}
			}
			if(obj.zsj.bjh){
				$("#replace_fjdid").val(obj.zsj.parent.id);
				$("#replace_fjd_name").val(obj.zsj.parent.bjh);
				$("#replace_fjd_wz").val(obj.zsj.parent.wz);
				$("#replace_fjd_cj").val(obj.zsj.parent.cj);
			}
			
			// 飞机站位
			$("#replace_fjzw").val(obj.zsj.fjzw);
			// 生成位置
			this_.buildWz();
			var wz = obj.cx_wz == undefined ?(obj.zsj? (obj.zsj.wz == undefined ? "11":obj.zsj.wz):"11"):"11";
			$("#replace_wz").val(wz);
			if(obj.cxj && obj.cxj.wz){
				$("#replace_wz").val(obj.cxj ? obj.cxj.wz:"11");
				$("#replace_wz").attr("disabled", "disabled");
			}
			//限制数量输入
			this_.limitZjsl();
			this_.limitCxsl();
		},
		
		// 保存
		save: function(){
			var this_ = this;
			// 验证
			if(!this_.validataData()){
				return false;
			}
			if(this_.param.callback && typeof(this_.param.callback) == "function"){
				this_.param.callback(this_.getData());
			}
			$("#"+this_.id).modal("hide");
		},
		
		// 初始化数据字典和枚举
		initDicAndEnum : function(){
			var this_ = this;
			$("#replace_jldw").empty();
			DicAndEnumUtil.registerDic("1", "replace_jldw",this.param.dprtcode);
		},
		
		// 打开ATA章节号对话框
		openChapterWin : function(){
			var this_ = this;
			var zjh = $.trim($("#replace_zjh_value").val());
			FixChapterModal.show({
				parentWinId : '',
				selected:zjh,//原值，在弹窗中默认勾选
				dprtcode:this_.param.dprtcode,//机构代码
				callback: function(data){//回调函数	
					if(data != null){
						var zjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
						$("#replace_zjh_value").val(data.zjh);
						$("#replace_zjh_display").val(zjhName);
					}else{
						$("#replace_zjh_value").val('');
						$("#replace_zjh_display").val('');
					}
				}
			});
		},
		limitZjsl : function(){
			var this_ = this;
			var xlh = $("#replace_zsxlh").val();
			if(xlh){
				$("#replace_zssl").val("1");
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus('replace_zssl', 'NOT_VALIDATED',null)
			     .validateField('replace_zssl');  
				$("#replace_zssl").attr("disabled","disabled");
			}else{
				$("#replace_zssl").removeAttr("disabled");
			}
		},
		limitCxsl : function(){
			var this_ = this;
			var xlh = $("#replace_cxxlh").val();
			if(xlh){
				$("#replace_cxsl").val("1");
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus('replace_cxsl', 'NOT_VALIDATED',null)
			     .validateField('replace_cxsl');  
				$("#replace_cxsl").attr("disabled","disabled");
			}else{
				$("#replace_cxsl").removeAttr("disabled");
			}
		},
		/** 打开报告人*/
		openUser : function(){
			var this_ = this;
			var userList = [];
			var id = $("#replace_azr_value").val();
			var name = $("#replace_azr_display").val();		
			var p = {
				id : id,
				displayName : name
			};
			userList.push(p);			
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
					$("#replace_azr_display").val(displayName);
					$("#replace_azr_value").val(mjsrid);
				}
			});
			AlertUtil.hideModalFooterMessage();
		},
		
		getNowFormatDate : function () {
		    var date = new Date();
		    var seperator1 = "-";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		    return currentdate;
		},
	};