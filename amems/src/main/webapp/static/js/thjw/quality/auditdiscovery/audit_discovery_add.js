
	var auditDiscoveryAdd = {
		
		id : "audit_discovery_alert_Modal",
		
		formId : 'auditDiscoveryAdd_form',
		// 页面初始化
		init : function(){
			
			var this_ = this;
			// 初始化控件
			this_.initWidget();
			// 初始化数据字典和枚举
			this_.initDicAndEnum();
			// 加载附件
			this_.loadAttachment();
			// 加载数据
			this_.loadData();
			//加载验证
			this_.validation();
			$("#audit_discovery_alert_Modal").on("hidden.bs.modal", function() {
				$("#auditDiscoveryAdd_form").data('bootstrapValidator').destroy();
				$("#auditDiscoveryAdd_form").data('bootstrapValidator', null);
				auditDiscoveryAdd.validation();
			});
			$('#audit_discovery_alert_Modal .date-picker').datepicker('update');
			//实际审核日期
			$('#sjShrq').datepicker({
				autoclose : true,
				clearBtn : true
			}).on('hide',function(e) {
				$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('sjShrq','NOT_VALIDATED', null).validateField('sjShrq');
			});
			//要求反馈日期
			$('#yqfkrq').datepicker({
				autoclose : true,
				clearBtn : true
			}).on('hide',function(e) {
				$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('yqfkrq','NOT_VALIDATED', null).validateField('yqfkrq');
			});
			$('input:radio[name="lx"]').change( function(){
				var lx = $(this).val();
					if(lx == 2){
						$("#shdxbtn").hide();
						$("#shdx").removeClass("readonly-style");
						$("#shdx").removeAttr("readonly");
						$("#zrrmark").hide();
					}else{						
						$("#shdxbtn").show();
						$("#shdx").addClass("readonly-style");
						$("#shdx").attr("readonly","readonly");
						$("#zrrmark").show();
					}
					$("#shdxid").val("");
					$("#shdxbh").val("")
					$("#shdxmc").val("");
					$("#shdx").val("");
					$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('shdx','NOT_VALIDATED', null).validateField('shdx');
					$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('zrr','NOT_VALIDATED', null).validateField('zrr');
			});
		},
		
		param : {
			data:{},
			ope_type:1,//操作类型
			dprtcode:userJgdm,//默认登录人当前机构代码
			shxmdid:null,//审核项目单id
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			if(this.param.ope_type == 1){
				$("#shwtdbh").attr("disabled",false)
			}
			if(this.param.ope_type == 2){
				$("#shwtdbh").attr("disabled",true)
			}
			this.init();
			if(this.param.shxmdid != null && this.param.shxmdid != ""){
				//加载基本信息
				this.getNdsh(this.param.shxmdid);
			}
			$("#"+this.id).modal("show");
		},
		getNdsh: function(shxmdid){
			var this_ = this;
			startWait($("#"+this_.id));
		   	AjaxUtil.ajax({
		 		url:basePath + "/quality/audititemlist/selectById",
		 		type:"post",
		 		async: false,
		 		data:{
		 			"id" : shxmdid
		 		},
		 		dataType:"json",
		 		modal:$("#"+this_.id),
		 		success:function(data){
		 			var row = data.audititem;
		 			finishWait($("#"+this_.id));
		 			if(data!=null){
		 				$("input:radio[name='lx'][value="+row.lx+"]",$("#"+this_.id)).attr('checked', 'checked');
		 				$("input:radio[name='shlb'][value="+row.shlb+"]",$("#"+this_.id)).attr('checked', 'checked');
		 				$("input:radio[name='lx'][value="+row.lx+"]",$("#"+this_.id)).attr('checked', 'checked');
		 				$("input:radio[name='shlb'][value="+row.shlb+"]",$("#"+this_.id)).attr('checked', 'checked');
	 					$("#shdx",$("#"+this_.id)).val(formatUndefine(row.shdxbh) +" "+formatUndefine(row.shdxmc));
	 					$("#shdxmc",$("#"+this_.id)).val(formatUndefine(row.shdxmc));
	 					$("#shdxbh",$("#"+this_.id)).val(formatUndefine(row.shdxbh));
	 					$("#shdxid",$("#"+this_.id)).val(formatUndefine(row.shdxid));
					};
		 		}
			});
		},
		// 初始化控件
		initWidget : function(){
			// 初始化日期控件
			$('#' + this.id + ' .date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
		},
		
		// 初始化数据字典和枚举
		initDicAndEnum : function(){
			var this_ = this;
			
			
		},
		// 选择部门
		chooseDepartment : function() {
			var this_ = this;
			var dprtname = $("#shdxmc").val();
			var dprtcode = $("#shdxid").val();
			departmentModal.show({
				dprtnameList : dprtname,// 部门名称
				dprtcodeList : dprtcode,// 部门id
				type : false,// 勾选类型true为checkbox
				parentid : this_.id,
				dprtcode : this_.param.dprtcode,// 默认登录人当前机构代码
				callback : function(data) {// 回调函数
					$("#shdxmc").val(data.dprtname);
					$("#shdxid").val(data.dprtcode);
					$("#shdxbh").val(data.dprtbm);
					$("#shdx").val(data.dprtbm+" "+data.dprtname);
					$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('shdx','NOT_VALIDATED', null).validateField('shdx');
				}
			});
		},
		
		// 选择校验人
		chooseUser:function(){
			var this_ = this;
			var bmdm = $("#shdxid").val();
			var lx =  $.trim($("[name=lx]:radio:checked").val());
			if(!bmdm && lx ==1){
				AlertUtil.showErrorMessage("请先选择审核对象！");
				return false;
			}
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:$("#zrrid").val(),displayName:$("#zrr").val()}],//原值，在弹窗中回显
				dprtcode:this_.param.dprtcode,
				bmdm:bmdm,
				clearUser:false,
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					$("#zrrid").val(user.id);
					$("#zrr").val(user.displayName);
					$("#zrrbmid").val(user.bmdm);
					$("#zrrbh").val(user.username);
					$("#zrrmc").val(user.realname);
					$('#auditDiscoveryAdd_form').data('bootstrapValidator').updateStatus('zrr','NOT_VALIDATED', null).validateField('zrr');
				}
			});
			AlertUtil.hideModalFooterMessage();
		},		
		// 下载文件
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		
		// 加载附件
		loadAttachment : function(){
			var this_ = this;
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				attachHead : true,
				djid:formatUndefine(this_.param.data.id),
				fileType:"noticeOfDiscovery",
		 		colOptionhead : true,
				fileHead : true,
				left_column : false,
			});//显示附件
		},
		
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;			
			//设置默认值
			AlertUtil.hideModalFooterMessage(this_.id);
			$("input[name='lx'][value='1']").attr('checked',true);
			$("input[name='shlb'][value='11']").attr('checked',true);
			$("input[name='wtlx'][value='1']").attr('checked',true);
			$("#shdxbtn").show();
			$("#shdx").addClass("readonly-style");
			$("#shdx").attr("readonly","readonly");
			$("#zrrmark").show();
			// 刷新时间控件
			$("#"+this_.id+" .date-picker").datepicker("update");
			$("#"+this_.id+" input[type='text']").val("");
			$("#"+this_.id+" input[type='hidden']").val("");
			$("#"+this_.id+" textarea").val("");
			if(this_.param.ope_type == 2){//修改
				this_.fillData(data);
			}
			//加载审核发现问题
			
			auditDiscoveryAddProblems.init({
				data:data.details,//原值
				ope_type : this_.param.ope_type,
				dprtcode:data.dprtcode, //机构代码
			});
		},
		fillData : function(obj){
			$("#shwtdbh").val(obj.shwtdbh);
			$("#sjShrq").val(obj.sjShrq?obj.sjShrq.substring(0,10):"");
			this.checkRadio("lx",obj.lx);
			if(obj.lx == 2){
				$("#shdxbtn").hide();
				$("#shdx").removeClass("readonly-style");
				$("#shdx").removeAttr("readonly");
				$("#zrrmark").hide();
			}else{						
				$("#shdxbtn").show();
				$("#shdx").addClass("readonly-style");
				$("#shdx").attr("readonly","readonly");
				$("#zrrmark").show();
			}
			this.checkRadio("shlb",obj.shlb);			
			$("#shdxid").val(obj.shdxid);
			$("#shdxbh").val(obj.shdxbh);
			$("#shdxmc").val(obj.shdxmc);			
			$("#shdx").val(StringUtil.escapeStr(obj.shdxbh)+" "+StringUtil.escapeStr(obj.shdxmc));			
			$("#zrrbmid").val(obj.zrrbmid);
			$("#zrrid").val(obj.zrrid);
			$("#zrrbh").val(obj.zrrbh);
			$("#zrrmc").val(obj.zrrmc);
			$("#zrr").val(obj.zrrbh+" "+obj.zrrmc);
			$("#jcnr").val(obj.jcnr);
			this.checkRadio("wtlx",obj.wtlx);
			$("#yqfkrq").val(obj.yqfkrq?obj.yqfkrq.substring(0,10):"");
			$("#zgjy").val(obj.zgjy);
			
		},
		checkRadio : function(str,value){
			var lx = document.getElementsByName(str);
			for(var i=0;i<lx.length;i++){
				if(lx[i].value == value){
					lx[i].checked='checked';
				}
			}
		},
		// 获取数据
		getData : function(){
			var obj = {};
			obj.id = this.param.data.id;
			obj.dprtcode = this.param.dprtcode;
			obj.shwtdbh = $.trim($("#shwtdbh").val());
			obj.sjShrq = $.trim($("#sjShrq").val());
			obj.lx = $.trim($("[name=lx]:radio:checked").val());
			obj.shlb = $.trim($("[name=shlb]:radio:checked").val());
			if(obj.lx==1){
				obj.shdxid = $.trim($("#shdxid").val());
				obj.shdxbh = $.trim($("#shdxbh").val());
				obj.shdxmc = $.trim($("#shdxmc").val());			
			}
			if(obj.lx==2){
				obj.shdxid ="";
				obj.shdxbh = "";
				obj.shdxmc = $.trim($("#shdx").val());
			}
			obj.zrrbmid = $.trim($("#zrrbmid").val());
			obj.zrrid = $.trim($("#zrrid").val());
			obj.zrrbh = $.trim($("#zrrbh").val());
			obj.zrrmc = $.trim($("#zrrmc").val());
			obj.jcnr = $.trim($("#jcnr").val());
			obj.wtlx = $.trim($("[name=wtlx]:radio:checked").val());
			obj.yqfkrq = $.trim($("#yqfkrq").val());
			obj.zgjy = $.trim($("#zgjy").val());
			obj.attachments = attachmentsUtil.getAttachmentsComponents('attachments_list_edit').getAttachments();
		    obj.details = auditDiscoveryAddProblems.getData();
		    if(this.param.shxmdid){
		    	 var paramsMap = {};
		    	 paramsMap.shxmdid = this.param.shxmdid;
		    	 obj.paramsMap = paramsMap;
			}
		   
			return obj;
		},
		
		// 保存
		saveData : function(type){
			var this_ = this;
			var obj = this_.getData();
			obj.zt = type;
			if(!this_.valid(obj)){
				return false;
			}
			if(this.param.callback && typeof(this.param.callback) == "function"){
				this_.param.callback(obj);
		}
		},
		valid:function(data){
			$("#auditDiscoveryAdd_form").data('bootstrapValidator').validate();
			if (!$("#auditDiscoveryAdd_form").data('bootstrapValidator').isValid()) {
				AlertUtil.showModalFooterMessage("请根据提示输入正确信息!");
				return false;
			}
			//审核发现问题
			if(!auditDiscoveryAddProblems.isValid){
				return false;
			}
			if(!data.yqfkrq){
				AlertUtil.showModalFooterMessage("要求反馈日期不能为空!");
				return false;
			}
			if(data.sjShrq > data.yqfkrq){
				AlertUtil.showModalFooterMessage("要求反馈日期不能早于实际审核日期!");
				return false;
			}
			return true;
		},
		validation:function(){
			var this_=this;
			validatorForm = $("#auditDiscoveryAdd_form").bootstrapValidator({
				message : '数据不合法',
				feedbackIcons : {
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					shwtdbh : {
						validators : {
							regexp : {
								regexp : /^[a-zA-Z0-9-_\x21-\x7e]{1,50}$/,
								message : '只能输入长度不超过50个字符的英文、数字、英文特殊字符!'
							}
						}
					},
					sjShrq : {
						validators : {
							notEmpty : {
								message : '时间审核日期不能为空'
							}
						}
					},
					shdx : {
						validators : {
							notEmpty : {
								message : '审核对象不能为空'
							},
						}
					},
					zrr : {
						validators : {
							callback: {
		                        message: '责任人不能为空',
		                        callback: function(value, validator) {
		                        	var lx = $.trim($("[name=lx]:radio:checked").val());	 	      
		                			if(lx == 1){
		                				if("" == $.trim(value) || null == $.trim(value)){
		                					return false;
		                				}
		                			}
		                            return true;
		                        }
		                    }
						}
					},
					yqfkrq : {
						validators : {
							notEmpty : {
								message : '要求反馈日期不能为空'
							},
						}
					},
				}
			})
		}
	};
	
	