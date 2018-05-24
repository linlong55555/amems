
	/**
	 * 弹出窗验证销毁
	 */
	$(function(){
		
		flb_manual.initWidget();
		
		$("#"+flb_manual.id).on("hidden.bs.modal", function() {
			$('#'+flb_manual.formId).data('bootstrapValidator').destroy();
			$('#'+flb_manual.formId).data('bootstrapValidator', null);
			AlertUtil.hideModalFooterMessage();
		});
	});
	
	var flb_manual = {
			
		id : "open_win_flightlogbook_manual_modal",
		
		formId : "open_win_flightlogbook_manual_modal_form",
		
		param : {
			fjzch:"",
			dprtcode:userJgdm,
			data:{}
		},
		
		// 页面初始化
		show : function(param){
			$("#"+this.id).modal("show");
			if(param){
				$.extend(this.param, param);
			}
			this.init();
		},
		
		// 页面初始化
		init : function(){
			var this_ = this;
			this_.adjustPage();
			this_.initValidation();
			this_.fillData(this_.param.data);
			this_.loadAttachment(this_.param.data.attachments);
		},
		
		// 初始化控件
		initWidget : function(){
			
			var this_ = this;
			// 初始化输入框内容限制控件
			$('#'+this_.id+' .time-masked').mask("99:99");
			
//			$("#"+this_.id+" .time-masked").on("blur", function(){
//				// 回退循环
//				this_.validateDateTime($(this));
//			});
		},
		
		// 验证日期时间
		validateDateTime : function($obj){
			var value = $obj.val();
			if(!/^(([01][0-9])|(2[0-3]))\:[0-5][0-9]$/.test(value)){
				$obj.val("");
			}
		},
		
		// 根据类型调整字段布局
		adjustPage : function(){
			var this_ = this;
			var gdlx = this_.param.data.gdlx || (this_.param.data.workorder ? this_.param.data.workorder.gdlx : "9");
			// 手工新增
			if(gdlx == 9){
				$("#manual_modal_gdbt_div").hide();
			}
			// 工单带出
			else{
				$("#manual_modal_gdbt_div").show();
			}
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
		        	wcrq: {
		                validators: {
		                    notEmpty: {
		                        message: '完成日期不能为空'
		                    }
		                }
		            },
		        }
		    });
			
			$('#manual_modal_wcrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				$('#'+this_.formId).data('bootstrapValidator')  
			     .updateStatus("wcrq", 'NOT_VALIDATED',null)
			     .validateField("wcrq"); 
		    });
			
		},
		
		// 填充数据
		fillData : function(data){
			
			var gdlx = data.gdlx||(data.workorder?data.workorder.gdlx:"9");
			var gdlxContent = "";
			if(gdlx == 1){
				gdlxContent = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', data.paramsMap.wxxmlx);
			}else{
				gdlxContent = DicAndEnumUtil.getEnumName('workorderTypeEnum', gdlx);
			}
			$("#manual_modal_gdlx").val(gdlxContent);
			$("#manual_modal_gdbh").val(data.gdbh||"");
			
			$("#manual_modal_wcrq").val((data.wcrq||"").substr(0, 10));
			$("#manual_modal_wcrq").datepicker("update");
			$("#manual_modal_wcsj").val((data.wcrq||"").substr(11, 5));
//			$("#manual_modal_gzzid").val(data.gzzid||"");
//			$("#manual_modal_gzz").val(data.gzz||"");
			
			var gzzList = '';
			var gzzidList = '';
			$.each(data.workers || [], function(i, row){
				gzzList += formatUndefine(row.gzz) + ",";
				gzzidList += formatUndefine(row.gzzid) + ",";
			});
			if(gzzList != ''){
				gzzList = gzzList.substring(0,gzzList.length - 1);
			}
			if(gzzidList != ''){
				gzzidList = gzzidList.substring(0,gzzidList.length - 1);
			}
			$("#manual_modal_gzz").val(gzzList);
			$("#manual_modal_gzzid").val(gzzidList);
			
			$("#manual_modal_jczid").val(data.zrrid||"");
			$("#manual_modal_jcz").val(data.zrr||"");
			$("#manual_modal_sjgs").val(data.sjgs||"");
			//$("#manual_modal_sjrs").val(data.sjrs||"");
			this.sumTotal();
			$("#manual_modal_gzbg").val(data.gzbg||"");
			$("#manual_modal_gzxx").val(data.gzxx||"");
			$("#manual_modal_clcs").val(data.clcs||"");
			$("#manual_modal_sjZd").val(data.sjZd||"");
			$("#manual_modal_gdbt").val(data.rwxx||"");
			
			$("#manual_modal_hsgs").removeAttr("checked");
			if(data.hsgs == 1){
				$("#manual_modal_hsgs").attr("checked",'true');//选中
			}
			
			if(data.gdbh){
				$("#manual_modal_gdbh_div").show();
			}else{
				$("#manual_modal_gdbh_div").hide();
			}
			this.disableUser("manual_modal_jcz");
			$("#manual_modal_wcrq").datepicker("update");
		},
		
		// 加载附件
		loadAttachment : function(attachments){
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('task_list_edit');
			attachmentsObj.show({
				attachHead : true,
				attachDatas:attachments,
				fileType:"task",
		 		colOptionhead : true,
				fileHead : true,
			});//显示附件
		},
		
		// 打开工作者弹窗
		openGzzWin : function(){
			var this_ = this;
			var userList = [];
			var gzzidList = $("#manual_modal_gzzid").val();
			var gzzList = $("#manual_modal_gzz").val();
			for (var i = 0; i < gzzidList.split(",").length; i++) {
				var p = {
					id : gzzidList.split(",")[i],
					displayName : gzzList.split(",")[i]
				};
				userList.push(p);
			}
			if (gzzidList == "") {
				userList = [];
			}
			Personel_Tree_Multi_Modal.show({
				checkUserList:userList,//原值，在弹窗中回显
				dprtcode:flb_detail.getDprtcode(),
				multi:true,
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
					$("#manual_modal_gzz").val(displayName);
					$("#manual_modal_gzzid").val(mjsrid);
				}
			});
			AlertUtil.hideModalFooterMessage();
		},
		
		// 打开检查者弹窗
		openJczWin : function(){
			var this_ = this;
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:$("#manual_modal_jczid").val(),displayName:$("#manual_modal_jcz").val()}],//原值，在弹窗中回显
				dprtcode:flb_detail.getDprtcode(),
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					$("#manual_modal_jczid").val(user.id);
					$("#manual_modal_jcz").val(user.displayName);
					this_.disableUser("manual_modal_jcz");
				}
			});
			AlertUtil.hideModalFooterMessage();
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			var obj = this_.param.data || {};
			obj.gdlx = this_.param.data.gdlx || (this_.param.data.workorder ? this_.param.data.workorder.gdlx : "9");
			if(this_.param.data.sgbs != 0){
				obj.sgbs = 1;
			}
			obj.hd = this_.param.hd;
			obj.hdms = this_.param.hdms;
			obj.gdbh = $.trim($("#manual_modal_gdbh").val());
			
			var wcrq = $.trim($("#manual_modal_wcrq").val());
			var wcsj = $.trim($("#manual_modal_wcsj").val());
			obj.wcrq = wcrq + " " + (wcsj||"00:00") + ":00";
			obj.zrrid = $.trim($("#manual_modal_jczid").val());
			obj.zrr = $.trim($("#manual_modal_jcz").val());
			obj.sjgs = $.trim($("#manual_modal_sjgs").val());
			obj.sjrs = $.trim($("#manual_modal_sjrs").val());
			obj.sjrgs = $.trim($("#manual_modal_total").text());
			obj.gzbg = $.trim($("#manual_modal_gzbg").val());
			obj.gzxx = $.trim($("#manual_modal_gzxx").val());
			obj.clcs = $.trim($("#manual_modal_clcs").val());
			obj.rwxx = $.trim($("#manual_modal_gdbt").val());
			obj.sjZd = $.trim($("#manual_modal_sjZd").val());
			obj.hsgs = $("#manual_modal_hsgs").is(":checked") ? 1 : 0;
//			obj.gzzid = $.trim($("#manual_modal_gzzid").val());
//			obj.gzz = $.trim($("#manual_modal_gzz").val());
			var userList = [];
			var gzzidList = $("#manual_modal_gzzid").val();
			var gzzList = $("#manual_modal_gzz").val();
			for (var i = 0; i < gzzidList.split(",").length; i++) {
				var p = {
					gzzid : gzzidList.split(",")[i],
					gzz : gzzList.split(",")[i]
				};
				userList.push(p);
			}
			if (gzzidList == "") {
				userList = [];
			}
			obj.workers = userList;
			
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('task_list_edit');
			obj.attachments = attachmentsObj.removeDeleteAttachment(attachmentsObj.getAttachments());
			return obj;
		},
		
		// 保存
		save : function(){
			var this_ = this;
			if(!this_.validataData()){
				return false;
			}
			if(this_.param.callback && typeof(this_.param.callback) == "function"){
				var data = this_.getData();
				this_.param.callback(data);
			}
			$("#"+this_.id).modal("hide");
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
			var xss = $.trim($("#manual_modal_sjgs").val());
			var rs = $.trim($("#manual_modal_sjrs").val());
			var wcsj = $.trim($("#manual_modal_wcsj").val())||"00:00";
			if(rs && !/^[0-9]+$/.test(rs)){
				AlertUtil.showModalFooterMessage("实际工时的人数应为正整数！", this_.id);
				return false;
			}
			if(xss && !/^[0-9]+((\.)[0-9]?[0-9]?)?$/.test(xss)){
				AlertUtil.showModalFooterMessage("实际工时应为正数，最多两位小数！", this_.id);
				return false;
			}
			if(!/^(([01][0-9])|(2[0-3]))\:[0-5][0-9]$/.test(wcsj)){
				AlertUtil.showModalFooterMessage("完成时间格式不正确！",this_.id);
				return false;
			}
			return true;
		},
		
		// 验证整数
		clearNoNumber : function(obj){
			 //先把非数字的都替换掉，除了数字
		     obj.value = obj.value.replace(/[^\d]/g,"");
		     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		  		obj.value = 0;
		  	 }
		    this.sumTotal();
		},
		
		// 验证小数点后2位小数
		clearNoNumTwo : function(obj){
			//先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d.]/g,"");
		     //必须保证第一个为数字而不是.
		     obj.value = obj.value.replace(/^\./g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\.{2,}/g,".");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		     
		     var str = obj.value.split(".");
		     if(str.length > 1){
		    	 if(str[1].length > 2){
		    		 obj.value = str[0] +"." + str[1].substring(0,2);
		    	 }
		     }
		     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		  		 if(obj.value.substring(1,2) != "."){
		  			obj.value = 0;
		  		 }
		  	 }
		    this.sumTotal();
		},
		
		// 计算总工时
		sumTotal : function(){
			var total = 0;
			var sjgs = $("#manual_modal_sjgs").val()||"0";
			var sjrs = $("#manual_modal_sjrs").val()||"0";
			total = parseFloat(sjgs) * parseFloat(sjrs);
			if(String(total).indexOf(".") >= 0){
				total = parseFloat(total.toFixed(2));
			}
			$("#manual_modal_total").text(total);
		},
		
		// 禁止输入用户
		disableUser : function(id){
			var user = $("#"+id+"id").val();
			if(user){
				$("#"+id).attr("readonly","readonly").addClass("readonly-style");
			}else{
				$("#"+id).removeAttr("readonly").removeClass("readonly-style");
			}
		},
	};
	
	