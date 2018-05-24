$(document).ready(
		function() {
			edit($("#id").val());
		});

	var auditDiscoveryView = {
		
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
			ope_type:3,//操作类型
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
			$("#"+this.id).modal("show");
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
		 		colOptionhead : false,
				fileHead : false,
				left_column : false,
			});//显示附件
		},
		
		// 加载数据
		loadData : function(){
			var this_ = this;
			var data = this_.param.data;
			this_.fillData(data);
			
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
	};
	
	function edit(id){
		getDataById(id,function(obj){
			auditDiscoveryView.show({
				data:obj,//原值，在弹窗中默认勾选
				ope_type : 3,//查看
				dprtcode:obj.dprtcode, //机构代码
			});
		})
	}
	
	function getDataById(id,obj){
		var this_ = this;
		var param={};
		param.id=id;
		AjaxUtil.ajax({
			url : basePath + "/quality/noticeofdiscovery/getRecord",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}			
			}
		});
	}
