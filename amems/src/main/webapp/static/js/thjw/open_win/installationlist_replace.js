	
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
					this_.switchInitInput();
					this_.loadCertificate(true);
					this_.limitZjsl(true);
				}
			});
		},
		
		// 手工输入装上件
		manualInputInstalled : function(){
			var this_ = this;
			// 显示装上件信息
			this_.switchInitInput();
			
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
			// 初始化证书
			this_.initCertificate();
			// 初始化数据字典
			this_.initDicAndEnum();
			// 生成位置
			this_.buildWz();
			// 填充数据
			this_.fillData();
			// 初始化类型
			this_.initType();
		},
		
		// 初始化类型
		initType : function(){
			var this_ = this;
			var type = this_.param.ope_type;
			$container = $("#"+this_.formId);
			if(type == "3"){
				$(".identifying").hide();
				$container.find("input,select,textarea").attr("disabled","disabled");
				$container.find(".btn").addClass("hidden");
				$("#replace_cxbjh,#replace_zsbjh,#replace_fjd_name,#replace_zjh_display").removeClass("readonly-style");
				$("#replace_confirm_btn").hide();
			}else{
				$container.find("input:not(#replace_cxxlh,#replace_zjsl,#replace_zsbjh,#replace_zsxlh,#replace_fjd_name),select:not(#replace_wz),textarea").removeAttr("disabled");
				$container.find(".btn:not(#replace_zsbjh_btn,#replace_fjd_btn)").removeClass("hidden");
				$("#replace_cxbjh,#replace_zjh_display").addClass("readonly-style");
				$("#replace_confirm_btn").show();
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
			var name = $obj.attr("name");
			if(name){
				$('#'+this.formId).data('bootstrapValidator')  
				.updateStatus(name, 'NOT_VALIDATED',null)
				.validateField(name); 
			}
		},
		
		// 回退循环
		validateCycle : function ($obj){
			this.backspace($obj, /^(0|[1-9]\d*)$/);
			var name = $obj.attr("name");
			if(name){
				$('#'+this.formId).data('bootstrapValidator')  
				.updateStatus(name, 'NOT_VALIDATED',null)
				.validateField(name); 
			}
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
		            replace_xh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
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
		            replace_zjsl: {
		                validators: {
		                	callback: {
		                        message: '装机数不能为空,只能输入大于1的正整数',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val()){
		                        		return true;
		                        	}	
		                        	return /^([1-9]|([1-9]\d+))$/.test(value);
		                        }
		                	}
		                }
		            },
/*		            replace_zjh_display: {
		            	validators: {
		                	callback: {
		                        message: '章节号不能为空',
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
		            },      */
		            replace_fjd_name: {
		            	validators: {
		                	callback: {
		                        message: '上级部件不能为空',
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
		            replace_jldw: {
		            	validators: {
		                	callback: {
		                        message: '单位不能为空',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val()){
		                        		return true;
		                        	}
		                        	if(value){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                    }
		                }
		            },
		            replace_cal: {
		            	validators: {
		                	callback: {
		                        message: '初始化日期不能为空',
		                        callback: function(value, validator) {
		                        	if(!$('#replace_zsbjh').val() || !$('#replace_zsxlh').val()){
		                        		return true;
		                        	}
		                        	if(value){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                    }
		                }
		            },
		            replace_fh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            replace_fc: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            replace_eh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            replace_ec: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            replace_apuh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            replace_apuc: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            replace_tsn: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            replace_tso: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            replace_csn: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            replace_cso: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
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
			
			$('#replace_cal').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus("replace_cal", 'NOT_VALIDATED',null)
			     .validateField("replace_cal"); 
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
						this_.switchInitInput();
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
		
		// 计算cal
		computeCal : function(){
			$("#replace_ccsj").text(this.dateMinus($("#replace_chucrq").val()));
		},
		
		// 计算日期相减天数
		dateMinus : function(sDate){
		  if(sDate){
			  var sdate = new Date(sDate.replace(/-/g, "/"));
			  var now = new Date();
				now.setHours(0);
				now.setMinutes(0);
				now.setSeconds(0);
				now.setMilliseconds(0);
				var days = now.getTime() - sdate.getTime();
				var day = parseInt(days / (1000 * 60 * 60 * 24));
				return day;
		  }
		  return "";
		},
		
		// 切换初始化值输入框是否显示
		switchInitInput : function(){
			var xlh = $("#replace_zsxlh").val();
			var wz = $("#replace_wz").val();
			var zsbjh = $("#replace_zsbjh").val();
			if(zsbjh){
				$("#replace_detail_div").show("500");
			}else{
				$("#replace_detail_div").hide("500");
			}
			if(xlh){
				$("#replace_init_div").show("500");
				$("#replace_init_eh").hide();
				$("#replace_init_ec").hide();
				$("#replace_init_apuh").hide();
				$("#replace_init_apuc").hide();
				if(wz == 21 || wz == 22 || wz == 23 || wz == 24){
					$("#replace_init_eh").show();
					$("#replace_init_ec").show();
				}
				if(wz == 31){
					$("#replace_init_apuh").show();
					$("#replace_init_apuc").show();
				}
			}else{
				$("#replace_init_div").hide("500");
			}
		},
		
		// 序列号改变事件
		onblurXlh : function(){
			var this_ = this;
			// 切换初始化值输入框是否显示
			this_.switchInitInput();
			// 加载证书
			this_.loadCertificate(true);
			//限制数量输入
			this_.limitZjsl(true)
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
		
		// 初始化证书
		initCertificate : function (){
			var this_ = this;
			this.certificateUtil = new CertificateUtil({
				parentId : this_.id,
				dprtcode : this_.param.dprtcode,
				tbody : $("#replace_certificate_list"),
				container : "#"+this_.id,
				ope_type : this_.param.ope_type
			});
		},
		
		// 加载证书
		loadCertificate : function (reload){
			var this_ = this;
			if(!reload){
				this.certificateUtil.load({
					bjh : $("#replace_zsbjh").val(),
					xlh : $("#replace_zsxlh").val(),
					pch : $("#replace_zspch").val(),
					data : this_.param.data.zsj ? this_.param.data.zsj.certificates : [],
					dprtcode : this_.param.dprtcode,
				});
			}else{
				this.certificateUtil.load({
					bjh : $("#replace_zsbjh").val(),
					xlh : $("#replace_zsxlh").val(),
					pch : $("#replace_zspch").val(),
					dprtcode : this_.param.dprtcode,
				});
			}
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
			var cxjid = $.trim($("#replace_cxbjid").val());
			if(cxjid){
				var cxj = {};
				
				var czrq = $.trim($("#replace_cxrq").val());
				var czsj = $.trim($("#replace_cxsj").val());
				var czsjall = $.trim(czrq + " " + (czsj||"00:00") + ":00");
				
				obj.cxWckcid = $.trim($("#replace_cxWckcid").val());
				obj.cxKclvid = $.trim($("#replace_cxKclvid").val());
				cxj.id = cxjid;
				cxj.bjh = $.trim($("#replace_cxbjh").val());
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
				obj.cxZjqdid = cxjid;
				obj.cxBs = 1;
				obj.cxBz = czyy;
			}else{
				obj.cxBs = 0;
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
				zsj.ywmc = $.trim($("#replace_zsywmc").val());
				zsj.zwmc = $.trim($("#replace_zszwmc").val());
				zsj.pch = $.trim($("#replace_zspch").val());
				zsj.azsj = czsjall;
				zsj.wz = czwz;
				zsj.dprtcode = this_.param.dprtcode;
				zsj.fjzch = this_.param.fjzch;
				zsj.jx = this_.param.jx;
				// 父节点
				zsj.parent = {
					id : $.trim($("#replace_fjdid").val()),
					bjh : $.trim($("#replace_fjd_name").val()),
					wz : $.trim($("#replace_fjd_wz").val()),
					cj : $.trim($("#replace_fjd_cj").val()),
				};
				zsj.fjdid = zsj.parent.id;
				zsj.cj = parseInt(zsj.parent.cj) + 1;
				// 初始化值
				var initDatas = [];
				if(zsj.xlh){
					initDatas.push(generateInitData("replace_cal"));
					initDatas.push(generateInitData("replace_fh"));
					initDatas.push(generateInitData("replace_fc"));
					if(zsj.wz == 21 || zsj.wz == 22 || zsj.wz == 23 || zsj.wz == 24){
						initDatas.push(generateInitData("replace_eh"));
						initDatas.push(generateInitData("replace_ec"));
					}
					if(zsj.wz == 31){
						initDatas.push(generateInitData("replace_apuh"));
						initDatas.push(generateInitData("replace_apuc"));
					}
				}
				zsj.cjjh = $.trim($("#replace_cjjh").val());
				zsj.xh = $.trim($("#replace_xh").val());
				zsj.zjh = $.trim($("#replace_zjh_value").val());
				
				zsj.paramsMap = {
					zjhname : $.trim($("#replace_zjh_display").val())
				};
				
				zsj.jldw = $.trim($("#replace_jldw").val());
				zsj.zjsl = $.trim($("#replace_zjsl").val());
				zsj.llklx = $.trim($("#replace_llklx").val());
				zsj.llkbh = $.trim($("#replace_llkbh").val());
				zsj.azr = $.trim($("#replace_azr_display").val());
				zsj.azrid = $.trim($("#replace_azr_value").val());
				zsj.zjjlx = $.trim($("#replace_zjjlx").val());
				
				zsj.chucrq = $.trim($("#replace_chucrq").val());
				zsj.chucrq = zsj.chucrq ? zsj.chucrq + " 00:00:00" : "";
				zsj.tsn = $.trim($("#replace_tsn").val());
				zsj.tso = $.trim($("#replace_tso").val());
				zsj.csn = $.trim($("#replace_csn").val());
				zsj.cso = $.trim($("#replace_cso").val());
				zsj.bjgzjl = $.trim($("#replace_bjgzjl").val());
				zsj.initDatas = initDatas;
				zsj.certificates = this_.certificateUtil.getData();
				zsj.stations = this_.param.positionList;
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
			
			// 生成初始化数据
			function generateInitData(id){
				var row = $("#"+id);
				return {
					jklbh : $.trim(row.attr("jklbh")),
					jkflbh : $.trim(row.attr("jkflbh")),
					csz : $.trim(row.val()),
				};
			}
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
			if(obj.cxj.bjh){
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
			$("#replace_cxbjid").val(obj.cxj.id);
			$("#replace_cxbjh").val(obj.cxj.bjh);
			$("#replace_cxxlh").val(obj.cxj.xlh);
			$("#replace_cxpch").val(obj.cxj.pch);
			$("#replace_cxsl").val(obj.cxj.zjsl);
			$("#replace_cxywmc").val(obj.cxj.ywmc);
			$("#replace_cxzwmc").val(obj.cxj.zwmc);
			$("#replace_cxWckcid").val(obj.cxWckcid);
			$("#replace_cxKclvid").val(obj.cxKclvid);
			
			/* 装上件 */
			$("#replace_zszjqdid").val(obj.zsj.id);
			$("#replace_zsWckcid").val(obj.zsWckcid);
			$("#replace_zsKclvid").val(obj.zsKclvid);
			$("#replace_zsbjh").val(obj.zsj.bjh);
			$("#replace_zsxlh").val(obj.zsj.xlh);
			$("#replace_zsywmc").val(obj.zsj.ywmc);
			$("#replace_zszwmc").val(obj.zsj.zwmc);
			$("#replace_zspch").val(obj.zsj.pch);
			
			if(obj.zsj.paramsMap && obj.zsj.paramsMap.sxzt == 0){
				$("#replace_zsbjh").attr("disabled","disabled");
				$("#replace_zsxlh").attr("disabled","disabled");
				$("#replace_zsbjh_btn").addClass("hidden");
			}else{
				$("#replace_zsbjh").removeAttr("disabled");
				$("#replace_zsxlh").removeAttr("disabled");
				$("#replace_zsbjh_btn").removeClass("hidden");
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
			
			// 初始化值
			$("#replace_init_div").find("input[jklbh]").val("");
			if(obj.zsj.initDatas){
				$.each(obj.zsj.initDatas, function(i, init){
					var value = init.csz;
					$("#open_win_installationlist_replace input[jklbh='"+init.jklbh+"']").val(value);
				});
			}
			$("#replace_cjjh").val(obj.zsj.cjjh);
			$("#replace_xh").val(obj.zsj.xh);
			$("#replace_zjh_value").val(obj.zsj.zjh);
			$("#replace_zjh_display").val(obj.zsj.paramsMap?obj.zsj.paramsMap.zjhname:"");
			$("#replace_jldw").val(obj.zsj.jldw);
			$("#replace_zjsl").val(obj.zsj.zjsl);
			$("#replace_llklx").val(obj.zsj.llklx);
			$("#replace_llkbh").val(obj.zsj.llkbh);
			$("#replace_zjjlx").val(obj.zsj.bjh ? obj.zsj.zjjlx : "其他");
	
			obj.zsj.chucrq = (obj.zsj.chucrq || "").substr(0, 10);
			$("#replace_chucrq").val(obj.zsj.chucrq);
			obj.zsj.chucrq = obj.zsj.chucrq?obj.zsj.chucrq+" 00:00:00":"";
			$("#replace_chucrq").datepicker("update");
			$("#replace_tsn").val(obj.zsj.tsn|| "");		
			$("#replace_tso").val(obj.zsj.tso||"");
			$("#replace_csn").val(obj.zsj.csn);
			$("#replace_cso").val(obj.zsj.cso);
			$("#replace_bjgzjl").val(obj.zsj.bjgzjl);
			// 飞机站位
			$("#replace_fjzw").val(obj.zsj.fjzw);
			this_.param.certificates = obj.zsj.certificates;
			// 生成位置
			this_.buildWz();
			var wz = obj.cxj ? obj.cxj.wz == undefined ?(obj.zsj? (obj.zsj.wz == undefined ? "11":obj.zsj.wz):"11"):"11":"11";
			$("#replace_wz").val(wz);
			if(obj.cxj && obj.cxj.wz){
				$("#replace_wz").val(obj.cxj ? obj.cxj.wz:"11");
				$("#replace_wz").attr("disabled", "disabled");
			}
			
			// 新增下拉框默认第一个选中
			if(!obj.zsj.id){
				$("#replace_jldw").find("option:first").attr("selected", 'selected'); 
				$("#replace_wz").find("option:first").attr("selected", 'selected'); 
				$("#replace_llklx").find("option:first").attr("selected", 'selected'); 
			}
			
			// 切换初始化值输入框是否显示
			this_.switchInitInput();
			//限制数量输入
			this_.limitZjsl();
			// 计算CAL
			this_.computeCal();
			// 加载证书
			this_.loadCertificate(false);
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
		initDicAndEnum : function(){
			var this_ = this;
			$("#replace_llklx").empty();
			$("#replace_jldw").empty();
			$("#replace_zjjlx").empty();
			DicAndEnumUtil.registerEnum("careercardtypeenum", "replace_llklx");
			DicAndEnumUtil.registerDic("1", "replace_jldw",this.param.dprtcode);
			
			DicAndEnumUtil.registerDic("52", "replace_zjjlx",this.param.dprtcode);
			$("#replace_zjjlx").val("其他");
		},/**打开ATA章节号对话框**/
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
/*					$('#'+this_.formId).data('bootstrapValidator')  
				     .updateStatus('replace_zjh_display', 'NOT_VALIDATED',null)
				     .validateField('replace_zjh_display');   */
				}
			});
		},
		limitZjsl : function(validate){
			var this_ = this;
			var xlh = $("#replace_zsxlh").val();
			if(xlh){
				$("#replace_zjsl").val("1");
				if(validate){
					$('#'+this_.formId).data('bootstrapValidator')  
				     .updateStatus('replace_zjsl', 'NOT_VALIDATED',null)
				     .validateField('replace_zjsl');  
				}
				$("#replace_zjsl").attr("disabled","disabled");
			}else{
				$("#replace_zjsl").removeAttr("disabled");
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