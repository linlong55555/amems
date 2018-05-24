
/**EO新增JS对象*/
eo_add_alert_Modal = {
	id : "EOAddModal",
	workContentAttachment : {},
	quyuDatas : [],
	bzIteam : [],
	editParam : {
		add : 1//新增
		,edit : 2//修改
		,audit : 3//审核
		,approve : 4//批准
		,modify : 5//改版
		,view : 0//查看
	},
	param: {
		editParam:1,//1:新增,2:修改,3:审核,4:批准,5:改版
		modalHeadCN : '',
		modalHeadENG : '',
		viewObj:{},
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){},
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		//滚动条位于最上方显示的语句
		$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			 $("#myTabContent").prop('scrollTop','0');
		});
		
		//新增:取当前人deptcode,编辑：取单据deptcode
		if(this_.param.editParam == this_.editParam.add){
			this_.param.dprtcode =  userJgdm; 
		}else{
			this_.param.dprtcode = this_.param.viewObj.dprtcode;
		}
		
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		//默认关闭Collapse
		closeCollapseSetting();
		//清空所有元素
		this.clearDatas();
		
		//初始化信息
		this.initInfo();
		//点击Collapse触发的事件
		collapseSetting();
	},
	close:function(){
		$("#"+this.id).modal("hide");
	},
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数!"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{0,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
	},
	/**初始化信息*/
	initInfo : function() {
		this.initModelHead();//标题信息
		this.initBaseInfo();//基础数据
		
		if(this.param.editParam == this.editParam.audit 
				|| this.param.editParam == this.editParam.approve
				|| this.param.editParam == this.editParam.view){
			this.initZujianInfo(false);//组件信息
		}else{
			this.initZujianInfo(true);//组件信息
		}
		this.initBody();//初始化页面中只读/失效/显示/隐藏
		
		//选中流程节点
		if(this.param.editParam == this.editParam.audit || this.param.editParam == this.editParam.approve){
			$("#fiveA", $("#"+this.id)).click();
			$(".control-important").css("display","none");
		}else{
			$("#oneA", $("#"+this.id)).click();
			$(".control-important").css("display","inline");
		}
	},
	
	/**初始化基本信息*/
	initBaseInfo : function() {
		var this_ = this;
		//机型
		this.initPlaneModelAdd();
		//工作类型
		$("#gzlx_add").empty();
		DicAndEnumUtil.registerDic('17', 'gzlx_add', this_.param.dprtcode);
		//EO类别
		$("#eolb_add").empty();
		DicAndEnumUtil.registerDic('25', 'eolb_add', this_.param.dprtcode);
		//EO分级
		$("#eofj_add").empty();
		DicAndEnumUtil.registerDic('26', 'eofj_add', this_.param.dprtcode);
		//币种
		$("#qcjgdw_add").empty();
		 this_.bzIteam = DicAndEnumUtil.getDict('3', this_.param.dprtcode);
		 for(i=0;null != this_.bzIteam && i<this_.bzIteam.length;i++){
			 if(i==0){
				 $("#btqcjgdw").empty();
				 $("#btqcjgdw").html(StringUtil.escapeStr(this_.bzIteam[i].name)+" <span class='caret caret-new' ></span>");
			 }
			 $('#qcjgdw_add').append("<li><a href='javascript:void(0)' value='"+StringUtil.escapeStr(this_.bzIteam[i].id)+"' onclick='eo_add_alert_Modal.changeUnit(this)'>"+StringUtil.escapeStr(this_.bzIteam[i].name)+"</a></li>");
		 }
		 $("#evaluation_list_important").css("display", "inline");
	},
	/**初始化对话框头部*/
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	/**机型*/
	initPlaneModelAdd:function(){
		var this_ = this;
		var data = acAndTypeUtil.getACTypeList({DPRTCODE: this_.param.dprtcode});
	 	var option = '';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	  	 }
	 	$("#jx_add").empty();
	 	$("#jx_add").append(option);
	},
	 selectsyx: function(){
		if($('input[name="sylb_public"]:checked').val() == '1'){
			applicable_settings.choosePlane();
		}else{
			applicable_settings.choosePN();
		}
	},	
	/**
	 * 初始化组件信息
	 */
	initZujianInfo : function(colOptionhead) {
		var this_ = this;
		
		//机型切换
		$("#jx_add").change(function(){
			applicable_settings.clearData(); //清空适用性列表
			$("#qbsyInput").removeAttr("checked");//取消全部适用勾选
			this_.initEOZoneInformation($("#jx_add").val(), this_.param.dprtcode);//初始化多选区域下拉框
			evaluation_modal.changeParam({
				parentId : "EOAddModal",// 第一层模态框id
				isShowed : colOptionhead,// 是否显示选择评估单的操作列
				zlxl : 6,// 指令类型
				dprtcode : this_.param.dprtcode,// 组织机构
				jx : $("#jx_add").val()//机型
			});
		});

		//初始化参考文件
		ReferenceUtil.init({
			djid:this.param.viewObj.id,//关联单据id
			ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode: this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化工种工时
		work_hour_edit.init({
			djid:this.param.viewObj.id,//关联单据id
			parentWinId : this.id,
			ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode: this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//受影响出版物
		PublicationUtil.init({
			djid:this.param.viewObj.id,//关联单据id
			colOptionhead : colOptionhead,
		});
		
		//工时/停场时间 
		StoppingUtil.init({
			djid:this.param.viewObj.id,//关联单据id
			colOptionhead : colOptionhead,
		});
		
		//初始EO附件列表
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:this.param.viewObj.id,
			colOptionhead : colOptionhead,
			fileHead : colOptionhead,
			attachHead : false,
			fileType:"eo"
		});
		
		//初始化器材清单
		Equipment_list_edit.init({
			djid:this.param.viewObj.id,//关联单据id
			parentWinId : "EOAddModal",
			ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode: this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化工具清单
		Tools_list_edit.init({
			djid:this.param.viewObj.id,//关联单据id
			parentWinId :"EOAddModal",
			ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode: this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//工作内容附件：上传按钮
		$("#gznrfjid").val(this.param.viewObj.gznrfjid);
		attachmentsSingleUtil.initAttachment(
				"work_content_attachments_single_edit"//主div的id
				,this.param.viewObj.workContentAttachment//附件
				,'eo'//文件夹存放路径
				,colOptionhead//true可编辑,false不可编辑
			);
		
		//初始化工作内容
		WorkContentUtil.init({
			djid:this.param.viewObj.id,//关联单据id
			ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode: this_.param.dprtcode//默认登录人当前机构代码
		});
		//流程信息
		if(this.param.editParam != this.editParam.add || this.param.editParam != this.editParam.view){
			var obj = this.param.viewObj;
			introduce_process_info_class.show({  
				status:obj.zt,//1,编写,2审核,3批准，4审核驳回,5批准驳回
				prepared:StringUtil.escapeStr(obj.zdr?obj.zdr.displayName:''),//编写人
				preparedDate:StringUtil.escapeStr(obj.whsj),//编写日期
				checkedOpinion:StringUtil.escapeStr(obj.shyj),//审核意见
				checkedby:StringUtil.escapeStr(obj.shr?obj.shr.displayName:''),//审核人
				checkedDate:StringUtil.escapeStr(obj.shsj),//审核日期
				checkedResult:obj.shjl,//审核结论
				approvedOpinion:StringUtil.escapeStr(obj.pfyj),//批准意见
				approvedBy:StringUtil.escapeStr(obj.pfr?obj.pfr.displayName:''),//批准人
				approvedDate:StringUtil.escapeStr(obj.pfsj),//批准日期
				approvedResult : obj.pfjl,// 批准结论
			});
		}
		
		if(this.param.editParam != this.editParam.add){
			var obj = this.param.viewObj;
			//技术评估单
			evaluation_modal.init({
				parentId : "EOAddModal",// 第一层模态框id
				isShowed : colOptionhead,// 是否显示选择评估单的操作列
				zlxl : 6,// 指令类型 6工程指令EO
				dprtcode :  this_.param.dprtcode,// 组织机构
				zlid : this.param.viewObj.id,
				jx :obj.jx//机型
			});
		}else{
			//技术评估单
			evaluation_modal.init({
				parentId : "EOAddModal",// 第一层模态框id
				isShowed : colOptionhead,// 是否显示选择评估单的操作列
				zlxl : 6,// 指令类型 6工程指令EO
				dprtcode :  this_.param.dprtcode,// 组织机构
				jx :$("#jx_add").val()//机型
			});
		}
		
		if (this.param.editParam == this.editParam.add) {
			this.initEOZoneInformation($("#jx_add").val(), this_.param.dprtcode); //区域
		} else {
			this.initEOZoneInformation(this.param.viewObj.jx, this_.param.dprtcode); //区域
		}
		
	    //适用性列表
		applicable_settings.init({
			operationShow:colOptionhead,
			dataList:this.param.viewObj.syxszList,
			sylb:this.param.viewObj.sylb,
			dprtcode:this_.param.dprtcode,// 组织机构
		});
		
		//工具设备:显示特殊工具和设备
		$("#is_tsgjsb_public_div").css("display", "block");
		
		//监控项设置 执行方式：1单次、2重复、3分段
		/*initJkxszWin(this.param.viewObj.zxfs,this.param.viewObj.id);*/
		
		//以后到为准
		if(this.param.viewObj.hdwz != null && this.param.viewObj.hdwz == 1){
			$("#hdwzradio").attr("checked","checked");
		}else{
			$("#hdwzradio").removeAttr("checked");
		}
		
		//终止条件标识
		if(this.param.viewObj.eoSub.zztjbs != null && this.param.viewObj.eoSub.zztjbs == 1){
			$("#zztjbsradio").attr("checked","true");
			$("#zztj").css("visibility","visible");
		}else{
			$("#zztjbsradio").removeAttr("checked");
		}
		
		//终止条件
		$('#zztj').val(this.param.viewObj.eoSub.zztj);
	},
	/**初始化页面中只读/失效/显示/隐藏*/
	initBody:function(){
		//隐藏或显示DIV
		this.initDivShowOrHide();
		//隐藏或显示按钮
		this.initBtnShowOrHide();
		//回显数据
		this.returnViewData();
		//如果适用性列表有数据,按钮不可编辑
		if(applicable_settings.getData() != null && applicable_settings.getData().length > 0){
			$("input[name='sylb_public']").not(":checked").attr('disabled','disabled');
		}
		if(this.param.editParam == this.editParam.add ){
			$('input[name="sylb_public"][value="1"]').prop("checked", "checked");//适用类别
		}
		//根据选择的使用类别,初始化监控项设置
		this.initJkxszWinBySylb();
	},
	/**根据选择的使用类别,初始化监控项设置*/
	initJkxszWinBySylb:function(){
		var chkSylb = $('input[name="sylb_public"]:checked').val();//选择的适用类别
		if(chkSylb == this.param.viewObj.sylb){ //如果使用类别和之前一样
			initJkxszWin(this.param.viewObj.zxfs,this.param.viewObj.id,chkSylb);
		}else{
			initJkxszWin(null,null,chkSylb);
		}
		
		if(chkSylb == 1){
			$('#qbsyLable').css("display", "inline");
		}else{
			$('#qbsyLable').css("display", "none");
		}
		
		//以后到为准
		if(this.param.viewObj.hdwz != null && this.param.viewObj.hdwz == 1){
			$("#hdwzradio").attr("checked","checked");
		}else{
			$("#hdwzradio").removeAttr("checked");
		}
		
		//终止条件标识
		if(this.param.viewObj.eoSub.zztjbs != null && this.param.viewObj.eoSub.zztjbs == 1){
			$("#zztjbsradio").attr("checked","true");
			$("#zztj").css("visibility","visible");
		}else{
			$("#zztjbsradio").removeAttr("checked");
		}
		
		//终止条件
		$('#zztj').val(this.param.viewObj.eoSub.zztj);
		
		if(this.param.editParam == this.editParam.audit 
				|| this.param.editParam == this.editParam.approve 
				|| this.param.editParam == this.editParam.view){
			this.setReadOnly4JkxFailure(); //设置只读
		}
		if(null != this.param.viewObj.jsgs){
			$('#jkxsz_frame_package_jsgs').val(this.param.viewObj.jsgs);
		}
		
		
		//全部适用就选中 并隐藏删除图标
		var fjzch = this.param.viewObj.fjzch;
		if(fjzch == "00000" && chkSylb == 1){
			$("#qbsyInput").prop('checked','checked'); 
			applicable_settings.loadAllFj();
		}else{
			$("#qbsyInput").removeAttr("checked");
		}
		applicable_settings.showHidePlaneMinus();
	},
	/**
	 * 日历单位改变
	 */
    changeUnit:function(obj){
		var btn = $(obj).parent().parent().prev();
		btn.attr("value", $(obj).attr("value"));
		btn.html($(obj).text()+'&nbsp;<span class="caret caret-new"></span>');
	},
	/**
	 * 打开ATA章节号对话框
	 */
	openChapterWin:function(){
		var this_ = this;
		var zjh = $.trim($("#zjhid_add").val());
		FixChapterModal.show({
			parentWinId : '',
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode,//机构代码
			callback: function(data){//回调函数	
				if(data != null){
					var zjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
					$("#zjhid_add").val(data.zjh);
					$("#zjhName_add").val(zjhName);
					this_.changeInput("#zjhName_add");
				}else{
					$("#zjhid_add").val('');
					$("#zjhName_add").val('');
				}	
			}
		});
	},
	/**
	 * 改变人数时触发
	 */
	changeRs : function(obj){
		this.clearNoNumber(obj);
		this.sumTotal();
	},
	/**
	 * 改变小时数触发
	 */
	changeXss : function(obj){
		this.clearNoNumTwo(obj);
		this.sumTotal();
	},
	/**
	 * 计算总数
	 */
	sumTotal : function(){
		var total = 0;
		var jhgsRs = $("#jhgsRs",$("#"+this.id)).val();
		var jhgsXss = $("#jhgsXss",$("#"+this.id)).val();
		total = jhgsRs*jhgsXss;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		if($("#jhgsRs",$("#"+this.id)).val() == "" || $("#jhgsXss",$("#"+this.id)).val() == ""){
			$("#bzgs",$("#"+this.id)).html(0);
		}else{
			$("#bzgs",$("#"+this.id)).html(total);
		}
	},
	/**
	 * 只能输入数字
	 */
	clearNoNumber : function(obj){
		 //先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	    this.changeInput(obj);
	},
	/**
	 * 只能输入数字和小数,保留两位
	 */
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
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	    this.changeInput(obj);
	},
	/**
	 * 分发
	 */
	openzrdw:function() {
		var this_ = this;
		var dprtname = $("#ff").val();
		var dprtcode = $("#ffid").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			type : true,// 勾选类型true为checkbox
			parentid : this.id,
			dprtcode : this_.param.dprtcode,// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#ff").val(data.dprtname);
				$("#ffid").val(data.dprtcode);
			}
		});
	},
	/**
	 * 附件显示或隐藏
	 */
	showOrHideAttach : function(box,single) {
		if($('#'+box, $("#"+this.id)).is(':checked')) {
			$('#'+single+' .singlefile', $("#"+this.id)).show();
		}else{
			$('#'+single+' .singlefile', $("#"+this.id)).hide();
		}
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		var eobh = $.trim($("#eobh_add",$("#"+this_.id)).val());
		var zt = $.trim($("#eozt_add",$("#"+this_.id)).val());
		var bb = $.trim($("#bb_add",$("#"+this_.id)).val());
		var zjh = $.trim($("#zjhid_add",$("#"+this_.id)).val());
		var zjhName = $.trim($("#zjhName_add",$("#"+this_.id)).val());
		var bfrq = $.trim($("#bfrq_add",$("#"+this_.id)).val());
		var qcjg = $.trim($("#qcjg_add",$("#"+this_.id)).val());
		var jhgsRs = $.trim($("#jhgsRs",$("#"+this_.id)).val());
		var jhgsXss = $.trim($("#jhgsXss",$("#"+this_.id)).val());
		
		var technicalList = evaluation_modal.getData(); //技术评估单数据
		var syxszList = applicable_settings.getData(); //适用性列表数据
		
		if(!this_.codeValidator.reg.test(eobh)){  
			$("#eobh_add",$("#"+this.id)).focus();
			$("#eobh_add",$("#"+this.id)).addClass("border-color-red");
			AlertUtil.showModalFooterMessage("EO编号"+this_.codeValidator.message);
			return false;
		}
		
		if(!this_.numberValidator.reg.test(bb)){  
			$("#bb_add",$("#"+this.id)).focus();
			$("#bb_add",$("#"+this.id)).addClass("border-color-red");
			AlertUtil.showModalFooterMessage("版本 "+this_.numberValidator.message);
			return false;
		}
		
		if(zt==""){  
			$("#eozt_add",$("#"+this.id)).focus();
			$("#eozt_add",$("#"+this.id)).addClass("border-color-red");
			AlertUtil.showModalFooterMessage("标题不能为空!");
			return false;
		}
		
		if(zjh=="" || zjhName == ""){  
			$("#oneA").click();
			$("#zjhid_add",$("#"+this.id)).focus();
			$("#zjhName_add",$("#"+this.id)).addClass("border-color-red");
			AlertUtil.showModalFooterMessage("ATA章节号不能为空!");
			return false;
		}
		
		if(bfrq==""){
			$("#oneA").click();
			$("#bfrq_add",$("#"+this.id)).focus();
			$("#bfrq_add",$("#"+this.id)).addClass("border-color-red");
			AlertUtil.showModalFooterMessage("颁发日期不能为空!");
			return false;
		}
		
		if(technicalList == null || technicalList.length <= 0){
			$("#oneA").click();
			AlertUtil.showModalFooterMessage("技术评估单不能为空!");
			return false;
		}
		
		if(syxszList == null || syxszList.length <= 0){
			$("#oneA").click();
			AlertUtil.showModalFooterMessage("适用列表不能为空!");
			return false;
		}
		
		if("" != jhgsRs && !this_.numberValidator.reg.test(jhgsRs)){
			$("#oneA").click();
			AlertUtil.showModalFooterMessage("影响手册中，参考工时(人数)，"+this_.numberValidator.message);
			return false;
		}
		
		if("" != jhgsXss && !this_.numberValidator.reg.test(jhgsXss)){
			$("#oneA").click();
			AlertUtil.showModalFooterMessage("影响手册中，参考工时(小时数)，"+this_.numberValidator.message);
			return false;
		}
		
		if("" != qcjg && !this_.numberValidator.reg.test(qcjg)){
			$("#oneA").click();
			AlertUtil.showModalFooterMessage("影响手册中，器材价格"+this_.numberValidator.message);
			return false;
		}
		
		return true;
	},
	/**
	 * 验证详情数据
	 */
	vilidateDetailData : function(){
		var this_ = this;
		
		//验证EO附件
		/*if(!attachmentsSingleUtil.isVaildMap['attachments_list_edit']){
			return false;
		}*/
		
		//验证工作内容
		if(!WorkContentUtil.isValid){
			$("#fourthA", $("#"+this_.id)).click();
			return false;
		}
		
		//验证工作内容附件
		if(!attachmentsSingleUtil.isVaildMap['work_content_attachments_single_edit']){
			$("#fourthA", $("#"+this_.id)).click();
			return false;
		}
		
		//验证工具设备
		if(!Tools_list_edit.isValid){
			$("#thiA", $("#"+this_.id)).click();
			return false;
		}
		
		//验证器材清单
		if(!Equipment_list_edit.isValid){
			$("#thiA", $("#"+this_.id)).click();
			return false;
		}
		
		//工时/停场时间
		if(!StoppingUtil.isValid){
			$("#secA", $("#"+this_.id)).click();
			return false;
		}
		
		//EO监控项设置
		if(!eoJkxSettingisValid){
			$("#secA", $("#"+this_.id)).click();
			return false;
		}
		
		//受影响的出版物
		if(!PublicationUtil.isValid){
			$("#oneA", $("#"+this_.id)).click();
			return false;
		}
		
		//验证参考文件
		if(!ReferenceUtil.isValid){
			$("#oneA", $("#"+this_.id)).click();
			return false;
		}
		
		return true;
	},
	/**保存数据 状态:1保存、2提交、3已审核、4已批准、5审核驳回、6审批驳回、7生效、9指定结束、10完成 */
	saveEO : function(operationType) {
		
		var this_ = this;
		
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		
		var reqData = this.getDatas(); 
		
		//验证详情数据
		if(!this.vilidateDetailData()){
			return false;
		}
		
		var paramsMap = {};
		
		//操作类型
		if(operationType != null && operationType != ''){
			paramsMap.operationType = operationType;
		}
		
		paramsMap.dprtcode =  this_.param.dprtcode; 
		
		/*Start:不需判空，要清空的字段 */
		if(null == reqData.jhgsRs || "" == reqData.jhgsRs){
			paramsMap.jhgsRs = "ND"; //计划工时_人数
		}
		if(null == reqData.jhgsXss || "" == reqData.jhgsXss){
			paramsMap.jhgsXss = "ND"; //计划工时_人数
		}
		/*End:不需判空，要清空的字段 */
		
		reqData.paramsMap = paramsMap;
		
		if ($("#qbsyInput").attr('checked')) {//获取适用性选飞机是否勾选全部适用
			reqData.fjzch = "00000"; //飞机注册号。适用类别=1，并且适用机型下所有飞机时写入00000，适用性表不写入数据；不是适用个别飞机时写入-
		} else {
			reqData.fjzch = "-"; 
		}
		
		var this_ = this;
		
		if(operationType != null && operationType == 2){
			AlertUtil.showConfirmMessage("您确定要提交吗？", {callback: function(){
				this_.param.callback(reqData);
			}});
		}else if(operationType != null && operationType == 1){
			this_.param.callback(reqData);
		}
	},
	/** 审核 */
	audit : function(audit) {
		var this_ = this;
		
		var shyj = introduce_process_info_class.getData().shenhe;
		var data = {};
		var message = "审核通过成功!";
		var tip = "您确定要审核通过吗？";
		
		if(audit == 3){
			/*if(shyj == null || shyj == '') {
				AlertUtil.showModalFooterMessage("审核意见不能为空!");
				return false;
			}*/
			data.shjl = 3;
		}else{
			/*if(shyj == null || shyj == '') {
				AlertUtil.showModalFooterMessage("审核驳回意见不能为空!");
				return false;
			}*/
			data.shjl = 5;
			message = "审核驳回成功!";
			tip = "您确定要审核驳回吗？";
		}
		
		data.shyj = shyj;
		data.dprtcode = this_.param.dprtcode;
		
		AlertUtil.showConfirmMessage(tip, {callback: function(){
			this_.param.callback(data,message);
		}});
	},
	/**
	 * 批准数据
	 * approve approve:批准通过
	 */
	approve: function(approve){
		var this_ = this;
		
		var pfyj = introduce_process_info_class.getData().shenpi;
		var data = {};
		var message = "批准通过成功!";
		var tip = "您确定要批准通过吗？";

		if(approve==4){
			/*if (pfyj == null || pfyj == '') {
				AlertUtil.showModalFooterMessage("批准意见不能为空!");
				return false;
			}*/
			data.pfjl = 4;
		}else{
			/*if (pfyj == null || pfyj == '') {
				AlertUtil.showModalFooterMessage("批准驳回意见不能为空!");
				return false;
			}*/
			data.pfjl = 6;
			message = "批准驳回成功!";
			tip = "您确定要批准驳回吗？";
		}
		
		data.pfyj = pfyj;
		data.dprtcode = this_.param.dprtcode;
		
		AlertUtil.showConfirmMessage(tip, {callback: function(){
				this_.param.callback(data,message);
		}});
	},
	/**
	 * 获取数据
	 */
	getDatas : function() {
		var this_ = this;
		var param = {};
		
		if(this.param.editParam != this.editParam.add){
			param.id = this.param.viewObj.id;
		}
		
		/*EO主表信息*/
		param.eobh = $("#eobh_add").val();//EO编号：必须
		param.bb = $("#bb_add").val();//版本：必须
		param.isXfsc = $('input[name="isXfsc_add"]:checked').val();//下发生产
		param.eozt = $('#eozt_add').val();//EO主题
		param.jx = $('#jx_add').val();//机型：必须
		param.zjh = $("#zjhid_add").val();//章节编号
		param.bfrq = $("#bfrq_add").val();//颁发日期
		param.jb = $('input[name="jb_add"]:checked').val();//级别
		param.gzlx = $('#gzlx_add').val();//工作类型
		param.sylb = $('input[name="sylb_public"]:checked').val();//适用类别
		param.eolb = $('#eolb_add').val();//EO类别
		param.eofj = $('#eofj_add').val();//EO级别
		param.jhgsRs = $('#jhgsRs').val();//计划工时_人数
		param.jhgsXss = $('#jhgsXss').val();//计划工时_小时数
		param.rii = $('#rii_add').attr('checked')?"1":"0";//RII
		param.zxfs = $('input[name="zxfsradio"]:checked').val();//执行方式
		param.hdwz = $('#hdwzradio').attr('checked')?"1":"0";//以后到为准
		param.jsgs = $('#jkxsz_frame_package_jsgs').val();//计算公式
		param.dprtcode =  this_.param.dprtcode;//部门
		
		/*EO从表信息*/
		param.eoSub = this.getEOSubDatas();
	
		/*EO关联表信息*/
		param = this.getEOGlbDatas(param);
		
		return param;
	},
	/**
	 * EO从表数据
	 */
	getEOSubDatas : function() {
		var eoSub = {};
		
		if(this.param.editParam != this.editParam.add){
			eoSub.id = this.param.viewObj.eoSub.id;
		}
		
		eoSub.scjszy = $('input[name="scjszy_add"]:checked').val();//首次技术支援
		eoSub.ywjsyfw =  $('#ywjsyfw_public').val();//源文件使用范围
		eoSub.gzgs =  $('#gzgs_add').val();//工作概述
		eoSub.fhx =  $('#fhx_add').val();//符合性
		eoSub.gbjlBc =  $('#gbjl_bc_public').val();//版次
		eoSub.gbjlYqbbgx =  $('#gbjl_yqbbgx_public').val();//与之前版本关系
		eoSub.gbjlGbyy =  $('#gbjl_gbyy_public').val();//改版原因
		eoSub.wxgzdxid =  $('#wxgzdxid_public').val();//维修改装对象 , 部件ID
		eoSub.wxgzdx =  $('#wxgzdx_public').val();//维修改装对象 , 部件号
		eoSub.wxgzbs = "";//标识
		$('input:checkbox[name="wxgzbs_public"]:checked').each(function(i){  
            if(0==i){  
            	eoSub.wxgzbs = $(this).val();  
            }else{  
            	eoSub.wxgzbs += $(this).val();  
            }  
        });  
		eoSub.wxgzbsQt = $('#wxgzbs_qt_public').val();//其他
		eoSub.isZzphbh = $('input[name="is_zzphbh_public"]:checked').val();//载重平衡变化
		eoSub.mtc = $('#mtc_public').val();//Mt Change
		eoSub.wtc = $('#wtc_public').val();//Wt Change
		eoSub.arm = $('#arm_public').val();//ARM
		eoSub.bhnr = $('#bhnr_public').val();//变化内容
		eoSub.yxsc = $('#yxsc_add').val();//影响手册
		eoSub.qcjg = $('#qcjg_add').val();//器材价格
		eoSub.qcjgdw = $('#btqcjgdw').val()==null?$('#btqcjgdw').html():$('#btqcjgdw').val(); //qcjgdw_add 币种
		eoSub.bfqczb = $('input[name="bfqczb_add"]:checked').val();//是否颁发器材准备
		eoSub.bfqczbtzd = $('#bfqczbtzd_add').val();//颁发器材准备通知单
		eoSub.isYxcbw = $('input[name="is_yxcbw_public"]:checked').val(); //是否影响出版物
		eoSub.sycjzrjqd = $('#sycjzrjqd_add').val(); //受影响的机载软件清单
		eoSub.isDqfzsj = $('input[name="is_dqfzsj_add"]:checked').val();//电气负载数据变化
		eoSub.dqfzsj = $('#dqfzsj_add').val();//电气负载数据
		eoSub.wxxmxgx = $('#wxxmxgx_add').val();//维修项目的相关性
		eoSub.fkyq = $('#fkyq_add').val();//反馈要求
		eoSub.hcxqsqd = $('#hcxqsqd_add').val();//航材需求申请单
		eoSub.yyljcl = $('#yyljcl_add').val();//原有零件处理
		eoSub.hhxx = $('#hhxx_add').val();//互换信息	
		eoSub.qt = $('#qt_add').val();//其他
		eoSub.zztjbs = $('#zztjbsradio').attr('checked')?"1":"0";//终止条件标识
		eoSub.zztj = $('#zztj').val();//终止条件
		eoSub.gcjy = $('#gcjy_add').val();//工程建议
		eoSub.scap = $('#scap_add').val();//生产安排
		eoSub.yyjms = $('#yyjms_add').val();//原因及描述
		eoSub.clcs = $('#clcs_add').val();//处理措施
		eoSub.isSpQc = $('#is_sp_qc_public').attr('checked')?"1":"0";//是否索赔器材
		eoSub.spQcsm = $('#sp_qcsm_public').val();//器材
		eoSub.spJgxx = $('#sp_jgxx_public').val();//索赔信息
		eoSub.isSpRg = $('#is_sp_rg_public').attr('checked')?"1":"0";//是否工人
		eoSub.spRgsm = $('#sp_rgsm_public').val();//人工
		eoSub.spqx = $('#spqx_public').val();//索赔期限
		eoSub.isTsgjsb = $('input[name="is_tsgjsb_public"]:checked').val();//特殊工具和设备
		
		return eoSub;
	},
	/**
	 * EO关联表数据
	 */
	getEOGlbDatas : function(param) {
		var technicalList = evaluation_modal.getData(); //技术评估单数据
		if (technicalList != null && technicalList != '') {
			var instructionsourceList = [];
			//组装下达指令数据
			$.each(technicalList, function(index, row) {
				var instructionsource = {};
				instructionsource.dprtcode = row.dprtcode;
				instructionsource.pgdid = row.id;
				instructionsource.pgdh = row.pgdh;
				instructionsource.bb = row.bb;
				instructionsourceList.push(instructionsource);
			});
			param.isList = instructionsourceList;
		}
		
		param.ckwjList = ReferenceUtil.getData(); //参考文件
		param.gzgsList = Work_Hour_Win_Modal.getData();//工种工时
		param.syxcbwList = PublicationUtil.getData();//受影响出版物
		
		//分发
		param.departmentIds = $("#ffid").val();
		
		//附件列表
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		param.attachments = attachmentsObj.getAttachments();
		
		//监控项信息
		param.jkxszList = getJzxszData();
		
		//工时停场时间 
		param.gstcshList = StoppingUtil.getData();
		
		//器材清单
		param.qcqdList = Equipment_list_edit.getData();
		
		//工具设备
		param.gjsbList = Tools_list_edit.getData();
		
		//设置工作内容附件
		var workContentAttachment = attachmentsSingleUtil.getAttachment('work_content_attachments_single_edit');
		if(workContentAttachment != null && workContentAttachment.wjdx != null && workContentAttachment.wjdx != ''){
			param.workContentAttachment = workContentAttachment;
		}
		
		param.gznrfjid = $.trim($("#gznrfjid").val());
		
		//工作内容
		param.gznrList = WorkContentUtil.getData();
		
		//区域和站位
		var qyzwList = [];
		/*var qyStr = $.trim($("#qy_public").val()); //区域
		if(qyStr != null && qyStr.length > 0){
			var qyArr = qyStr.split(",");
			for(var i = 0 ; i < qyArr.length ; i++){
				if('multiselect-all' != qyArr[i]){
					var coverPlate = {};
					coverPlate.gbid = qyArr[i];
					coverPlate.gbh = $("#qy_public").find("option[value='"+qyArr[i]+"']").text();
					coverPlate.lx = 1;
					coverPlate.xc = i;
					qyzwList.push(coverPlate);
				}
			}
		}*/
		var zone = {};
		zone.gbid = '';
		zone.gbh = $.trim($("#eqy_public").val());
		zone.xc = 1;
		zone.lx = 1;
		qyzwList.push(zone);
		
		//设置飞机站位
		var position = {};
		position.gbid = '';
		position.gbh = $.trim($("#efjzw_public").val());
		position.xc = 1;
		position.lx = 3;
		qyzwList.push(position);

		/*$.each(fjzwList,function(index,row){ //站位
			var coverPlate = {};
			coverPlate.gbid = row.id;
			coverPlate.gbh = row.sz;
			coverPlate.lx = 3;
			coverPlate.xc = index + 1;
			qyzwList.push(coverPlate);
		});*/
		
		param.qyzwList = qyzwList;
		
		//适用性
		var syxszList = applicable_settings.getData();
		console.info(syxszList);
		param.syxszList = syxszList;
		
		return param;
	},
	/**回显数据*/
	returnViewData : function(){
		if(this.param.editParam == this.editParam.add ){
			return;
		}
		this.setEODatas(); //回显主表数据
		this.setEOSubDatas();//回显从表数据
	},
	/**显示或隐藏div*/
	initDivShowOrHide : function(){
		$("#new_bbh_span", $("#"+this.id)).hide(); //最新版本号
		$("#old_bbhlist_span", $("#"+this.id)).hide();//历史版本号
		
		if(this.param.editParam == this.editParam.audit 
				|| this.param.editParam == this.editParam.approve 
				|| this.param.editParam == this.editParam.view){
			this.setReadOnlyFailure(); //设置只读
		}else{
			this.removeReadOnlyFailure(); //清除只读
		}
		
		if(this.param.editParam == this.editParam.audit || this.param.editParam == this.editParam.approve){
			$("#Five", $("#"+this.id)).addClass("active in"); //流程信息
			/*$("#fiveLi", $("#"+this.id)).css("display", "inline");*/
			$("#myTab").removeClass("fourStep");
			$("#myTab").addClass("fiveStep");
			if($("#fiveLi").length<1){
				var str=' <li id="fiveLi">';
					str+='<a id="fiveA" href="#Five" data-toggle="tab">';
					str+='<span class="step">5</span>' ;
					str+= '<span class="title">';
					str+= '<div class="font-size-14 margin-topnew-2">流程信息</div>' ;
					str+='<div class="font-size-12">Process</div>';
					str+='</span>';
					str+='</a>';
					str+='</li>';
				$("#myTab").append(str);
			}
			
			//查看图标：隐藏
			$('#efjzw_ico_public').css("display", "none");
			$('#eqy_public_btn').css("display", "none");
			$('#wxgzdxbt_public').css("display", "none");
			$('#ffbt').css("display", "none");
			$('#zjhbt_add').css("display", "none");
			//查看图标：灰色
			$('#zjhName_add').removeClass("readonly-style");
			$('#ff').removeClass("readonly-style");
			$('#wxgzdx_public').removeClass("readonly-style");
			$("#efjzw_public").attr("disabled","disabled");
			$("#efjzw_public").removeAttr('ondblclick');//解除绑定事件
			$("#eqy_public").attr("disabled","disabled");
			$("#eqy_public").removeAttr('ondblclick');//解除绑定事件
			
		}else{
			$("#Five", $("#"+this.id)).removeClass("active in");
			$("#fiveLi", $("#"+this.id)).remove();
			$("#myTab").removeClass("fiveStep");
			$("#myTab").addClass("fourStep");
			
			//查看图标 ： 显示
			$('#efjzw_ico_public').css("display", "table-cell");
			$('#eqy_public_btn').css("display", "table-cell");
			$('#wxgzdxbt_public').css("display", "table-cell");
			$('#ffbt').css("display", "table-cell");
			$('#zjhbt_add').css("display", "table-cell");
			
			//查看图标：灰色
			$('#zjhName_add').addClass("readonly-style");
			$('#ff').addClass("readonly-style");
			$('#wxgzdx_public').addClass("readonly-style");
			$("#efjzw_public").removeAttr("disabled","disabled");
			$("#efjzw_public").attr("ondblclick","openEOStationWin()"); //绑定事件
			$("#eqy_public").removeAttr("disabled","disabled");
			$("#eqy_public").attr("ondblclick","openEOZoneWin()"); //绑定事件
		}
		
		if(this.param.editParam == this.editParam.edit
				|| this.param.editParam == this.editParam.modify){
			$("#eobh_add", $("#"+this.id)).attr("disabled","true");
		}
		
		if(this.param.editParam == this.editParam.edit){
			$("#bb_add", $("#"+this.id)).attr("disabled","true");
		}
		
		if(this.param.editParam != this.editParam.add){
			if(this.param.editParam == this.editParam.modify){
				$("#new_bbh_span", $("#"+this.id)).show();
				$("#old_bbhlist_span", $("#"+this.id)).show();
				$("#new_bbh", $("#"+this.id)).html("");
				$("#old_bbh", $("#"+this.id)).html(this.param.viewObj.bb);
				this.initWebuiPopover(); //显示历史版本信息
			}else{
				if(this.param.viewObj.fBbid != null){
					$("#new_bbh_span", $("#"+this.id)).show();
					$("#old_bbhlist_span", $("#"+this.id)).show();
					$("#new_bbh", $("#"+this.id)).html("");
					$("#old_bbh", $("#"+this.id)).html(this.param.viewObj.fBbObj.bb);
				}
				this.initWebuiPopover(); //显示历史版本信息
			}
		}
	},
	/**显示或隐藏按钮*/
	initBtnShowOrHide : function(){
		$(".operation-btn ", $("#"+this.id)).hide(); //隐藏所有按钮
		if(this.param.editParam == this.editParam.add 
				|| this.param.editParam == this.editParam.edit
				|| this.param.editParam == this.editParam.modify){
			if(checkBtnPermissions("project2:eo:main:01") 
					|| checkBtnPermissions("project2:eo:main:02")  
					|| checkBtnPermissions("project2:eo:main:03")){
				$("#save_btn", $("#"+this.id)).show();
				$("#submit_btn", $("#"+this.id)).show();
			}
		}
		if(this.param.editParam == this.editParam.audit){
			if(checkBtnPermissions("project2:eo:main:04")){
				$("#audited_btn", $("#"+this.id)).show();
				$("#audit_reject_btn", $("#"+this.id)).show();
			}
		}
		if(this.param.editParam == this.editParam.approve){
			if(checkBtnPermissions("project2:eo:main:05")){
				$("#approved_btn", $("#"+this.id)).show();
				$("#approved_reject_btn", $("#"+this.id)).show();
			}
		}
	},
	/**
	 * 回显EO主表数据
	 */
	setEODatas : function() {
		var param = this.param.viewObj;
		
		/*EO主表信息*/
		$("#eobh_add").val(param.eobh);//EO编号：必须
		
		if(this.param.editParam == this.editParam.modify){
			$("#bb_add").val("");//版本：必须
		}else{
			$("#bb_add").val(param.bb);//版本：必须
		}
		
		$('input[name="isXfsc_add"][value="'+param.isXfsc+'"]').prop("checked", "checked");//下发生产
		$('#eozt_add').val(param.eozt);//EO主题
		$('#jx_add').val(param.jx);//机型：必须
		$('#jkxsz_frame_package_jsgs').val(param.jsgs);
		
		
		//章节号回显
		if(param.fixChapter != null){
			var wczjhName = StringUtil.escapeStr(param.fixChapter.zjh) + " " + StringUtil.escapeStr(param.fixChapter.ywms);
			$("#zjhid_add").val(param.fixChapter.zjh);
			$("#zjhName_add").val(wczjhName);
		}else{
			$("#zjhid_add").val('');
			$("#zjhName_add").val('');
		}
		
		if(param.bfrq != null){
			var bfrq = param.bfrq+"";
			$("#bfrq_add").val(bfrq.substring(0, 10));//颁发日期
		}else{
			$("#bfrq_add").val("");
		}
		$('input[name="jb_add"][value="'+param.jb+'"]').prop("checked", "checked");//级别
		
		$('#gzlx_add').val(param.gzlx);//工作类型
		
		$('input[name="sylb_public"][value="'+param.sylb+'"]').prop("checked", "checked");//适用类别
		
		$('#eolb_add').val(param.eolb);//EO类别
		$('#eofj_add').val(param.eofj);//EO级别
		$('#jhgsRs').val(param.jhgsRs);//计划工时_人数
		$('#jhgsXss').val(param.jhgsXss);//计划工时_小时数
		this.sumTotal();
		if(param.rii != null && param.rii == 1){//RII
			$("#rii_add", $("#"+this.id)).attr("checked","true");
		}else{
			$("#rii_add", $("#"+this.id)).removeAttr("checked");
		}
		
		$('input[name="zxfsradio"][value="'+param.zxfs+'"]').prop("checked", "checked");//执行方式
		
		//回显分发部门
		if(param.ffdeptList != null && param.ffdeptList.length > 0){
			var ffids = "";
			var ffnames = "";
			$.each(param.ffdeptList, function(i, ffObj) {
				if(i == param.ffdeptList.length-1){
					ffids += ffObj.department.id;
					ffnames += ffObj.department.dprtname;
				}else{
					ffids += ffObj.department.id+",";
					ffnames += ffObj.department.dprtname+",";
				}
			});
			
			$("#ff").val(ffnames);
			$("#ffid").val(ffids);
		}
	},
	/**
	 * 回显EO从表数据
	 */
	setEOSubDatas : function() {
		var this_ = this;
		var eoSub = this.param.viewObj.eoSub;
		
		$('input[name="scjszy_add"][value="'+eoSub.scjszy+'"]').prop("checked", "checked");//首次技术支援
		$('#ywjsyfw_public').val(eoSub.ywjsyfw);//源文件使用范围
		$('#gzgs_add').val(eoSub.gzgs);//工作概述
		$('#fhx_add').val(eoSub.fhx );//符合性
		$('#gbjl_bc_public').val(eoSub.gbjlBc);//版次
		$('#gbjl_yqbbgx_public').val(eoSub.gbjlYqbbgx);//与之前版本关系
		$('#gbjl_gbyy_public').val(eoSub.gbjlGbyy);//改版原因
		$('#wxgzdxid_public').val(eoSub.wxgzdxid);//维修改装对象 , 部件ID
		$('#wxgzdx_public').val(eoSub.wxgzdx);//维修改装对象 , 部件号
		if(null != eoSub.hcMainData){
			$('#zjh_public').val(eoSub.hcMainData.zjh); //部件关联的章节号
			$("#wxgzdxName_public").val(eoSub.hcMainData.ywms+" "+eoSub.hcMainData.zwms);
			if(eoSub.hcMainData.fixChapter != null){
				$("#zjhName_public").val(eoSub.hcMainData.fixChapter.displayName);
			}
		}
		
		//标识
		$('input:checkbox[name="wxgzbs_public"]').each(function(i){ 
			if(eoSub.wxgzbs != null && eoSub.wxgzbs.indexOf($(this).val()) > -1){
				$(this).prop("checked", "checked");
				if($(this).val() == "H"){
					$('#wxgzbs_qt_public').css("visibility","visible");
				}
			}else{
				$(this).removeAttr("checked");
			}
        });  
		
		$('#wxgzbs_qt_public').val(eoSub.wxgzbsQt);//其他
		$('input[name="is_zzphbh_public"][value="'+eoSub.isZzphbh+'"]').prop("checked", "checked");//载重平衡变化
		$('#mtc_public').val(eoSub.mtc);//Mt Change
		$('#wtc_public').val(eoSub.wtc);//Wt Change
		$('#arm_public').val(eoSub.arm);//ARM
		$('#bhnr_public').val(eoSub.bhnr);//变化内容
		$('#yxsc_add').val(eoSub.yxsc);//影响手册
		$('#qcjg_add').val(eoSub.qcjg);//器材价格
		
		if(null != eoSub.qcjgdw){
			$('#btqcjgdw').html(eoSub.qcjgdw+ '&nbsp;<span class="caret caret-new"></span>'); //qcjgdw_add 币种
		}else{
			if(null != this_.bzIteam && this_.bzIteam.length > 0){
				$('#btqcjgdw').html(StringUtil.escapeStr(this_.bzIteam[0].name) + '&nbsp;<span class="caret caret-new"></span>'); //qcjgdw_add 币种
			}
		}
		
		$('input[name="bfqczb_add"][value="'+eoSub.bfqczb+'"]').prop("checked", "checked");//是否颁发器材准备
		$('#bfqczbtzd_add').val(eoSub.bfqczbtzd);//颁发器材准备通知单
		$('input[name="is_yxcbw_public"][value="'+eoSub.isYxcbw+'"]').prop("checked", "checked"); //是否影响出版物
		$('#sycjzrjqd_add').val(eoSub.sycjzrjqd); //受影响的机载软件清单
		$('input[name="is_dqfzsj_add"][value="'+eoSub.isDqfzsj+'"]').prop("checked", "checked");//电气负载数据变化
		
		$('#dqfzsj_add').val(eoSub.dqfzsj);//电气负载数据
		$('#wxxmxgx_add').val(eoSub.wxxmxgx);//维修项目的相关性
		$('#fkyq_add').val(eoSub.fkyq);//反馈要求
		$('#hcxqsqd_add').val(eoSub.hcxqsqd);//航材需求申请单
		$('#yyljcl_add').val(eoSub.yyljcl);//原有零件处理
		$('#hhxx_add').val(eoSub.hhxx);//互换信息	
		$('#qt_add').val(eoSub.qt);//其他
		
		$('#gcjy_add').val(eoSub.gcjy);//工程建议
		$('#scap_add').val(eoSub.scap);//生产安排
		$('#yyjms_add').val(eoSub.yyjms);//原因及描述
		$('#clcs_add').val(eoSub.clcs);//处理措施
		
		if(eoSub.isSpQc != null && eoSub.isSpQc == 1){//是否索赔器材
			$("#is_sp_qc_public", $("#"+this.id)).attr("checked","true");
			$("#sp_qcsm_public").css("visibility","visible");
		}else{
			$("#is_sp_qc_public", $("#"+this.id)).removeAttr("checked");
		}
		
		$('#sp_qcsm_public').val(eoSub.spQcsm);//器材
		$('#sp_jgxx_public').val(eoSub.spJgxx);//索赔信息
		
		if(eoSub.isSpRg != null && eoSub.isSpRg == 1){//是否工人
			$("#is_sp_rg_public", $("#"+this.id)).attr("checked","true");
			$("#sp_rgsm_public").css("visibility","visible");
		}else{
			$("#is_sp_rg_public", $("#"+this.id)).removeAttr("checked");
		}
		
		$('#sp_rgsm_public').val(eoSub.spRgsm);//人工
		if(eoSub.spqx != null){
			$('#spqx_public').val((eoSub.spqx+"").substring(0, 10));//索赔期限
		}else{
			$('#spqx_public').val("");
		}
		
		$('input[name="is_tsgjsb_public"][value="'+eoSub.isTsgjsb+'"]').prop("checked", "checked");//特殊工具和设备
	},
	/**清除只读*/
	removeReadOnlyFailure : function(){
		/*EO主表信息*/
		$("#eobh_add", $("#"+this.id)).removeAttr("disabled"); //EO编号：必须
		$("#bb_add", $("#"+this.id)).removeAttr("disabled"); //版本：必须
		$('input[name="isXfsc_add"]').removeAttr("disabled");//下发生产
		$('#eozt_add', $("#"+this.id)).removeAttr("disabled"); //EO主题
		$('#jx_add', $("#"+this.id)).removeAttr("disabled"); //机型：必须
		$("#zjhid_add", $("#"+this.id)).removeAttr("disabled"); //章节编号
		$("#zjhName_add", $("#"+this.id)).removeAttr("disabled"); //章节编号 : 关联
		$("#zjhbt_add", $("#"+this.id)).removeAttr("disabled"); //章节编号 按钮
		$("#bfrq_add").removeAttr("disabled");//颁发日期
		$('input[name="jb_add"]').removeAttr("disabled");//级别
		$('#gzlx_add', $("#"+this.id)).removeAttr("disabled"); //工作类型
		$('input[name="sylb_public"]').removeAttr("disabled");//适用类别
		$('#eolb_add', $("#"+this.id)).removeAttr("disabled"); //EO类别
		$('#eofj_add', $("#"+this.id)).removeAttr("disabled"); //EO级别
		$('#jhgsRs', $("#"+this.id)).removeAttr("disabled"); //计划工时_人数
		$('#jhgsXss', $("#"+this.id)).removeAttr("disabled"); //计划工时_小时数
		$("#rii_add", $("#"+this.id)).removeAttr("disabled"); //RII
		$('input[name="zxfsradio"]').removeAttr("disabled");//执行方式
		$("#hdwzradio", $("#"+this.id)).removeAttr("disabled"); //以后到为准
		$('#jkxsz_frame_package_jsgs', $("#"+this.id)).removeAttr("disabled"); //计算公式
		
		/*EO从表信息*/
		$('input[name="scjszy_add"]', $("#"+this.id)).removeAttr("disabled");//首次技术支援
		$('#ywjsyfw_public', $("#"+this.id)).removeAttr("disabled");//源文件使用范围
		$('#gzgs_add', $("#"+this.id)).removeAttr("disabled");//工作概述
		$('#fhx_add', $("#"+this.id)).removeAttr("disabled");//符合性
		$('#gbjl_bc_public', $("#"+this.id)).removeAttr("disabled");//版次
		$('#gbjl_yqbbgx_public', $("#"+this.id)).removeAttr("disabled");//与之前版本关系
		$('#gbjl_gbyy_public', $("#"+this.id)).removeAttr("disabled");//改版原因
		$('#wxgzdxid_public', $("#"+this.id)).removeAttr("disabled");//维修改装对象 , 部件ID
		$('#wxgzdx_public', $("#"+this.id)).removeAttr("disabled");//维修改装对象 , 部件号
		$('input[name="wxgzbs_public"]', $("#"+this.id)).removeAttr("disabled");//标识
		$('#wxgzbs_qt_public', $("#"+this.id)).removeAttr("disabled");//其他
		$('input[name="is_zzphbh_public"]', $("#"+this.id)).removeAttr("disabled");//载重平衡变化
		$('#mtc_public', $("#"+this.id)).removeAttr("disabled");//Mt Change
		$('#wtc_public', $("#"+this.id)).removeAttr("disabled");//Wt Change
		$('#arm_public', $("#"+this.id)).removeAttr("disabled");//ARM
		$('#bhnr_public', $("#"+this.id)).removeAttr("disabled");//变化内容
		$('#yxsc_add', $("#"+this.id)).removeAttr("disabled");//影响手册
		$('#qcjg_add', $("#"+this.id)).removeAttr("disabled");//器材价格
		$('#btqcjgdw', $("#"+this.id)).removeAttr("disabled");//qcjgdw_add 币种
		$('input[name="bfqczb_add"]', $("#"+this.id)).removeAttr("disabled");//是否颁发器材准备
		$('#bfqczbtzd_add', $("#"+this.id)).removeAttr("disabled");//颁发器材准备通知单
		$('input[name="is_yxcbw_public"]', $("#"+this.id)).removeAttr("disabled");//是否影响出版物
		$('#sycjzrjqd_add', $("#"+this.id)).removeAttr("disabled");//受影响的机载软件清单
		$('input[name="is_dqfzsj_add"]', $("#"+this.id)).removeAttr("disabled");//电气负载数据变化
		$('#dqfzsj_add', $("#"+this.id)).removeAttr("disabled");//电气负载数据
		$('#wxxmxgx_add', $("#"+this.id)).removeAttr("disabled");//维修项目的相关性
		$('#fkyq_add', $("#"+this.id)).removeAttr("disabled");//反馈要求
		$('#hcxqsqd_add', $("#"+this.id)).removeAttr("disabled");//航材需求申请单
		$('#yyljcl_add', $("#"+this.id)).removeAttr("disabled");//原有零件处理
		$('#hhxx_add', $("#"+this.id)).removeAttr("disabled");//互换信息	
		$('#qt_add', $("#"+this.id)).removeAttr("disabled");//其他
		$("#zztjbsradio", $("#"+this.id)).removeAttr("disabled");//终止条件标识
		$('#zztj', $("#"+this.id)).removeAttr("disabled");//终止条件
		$('#gcjy_add', $("#"+this.id)).removeAttr("disabled");//工程建议
		$('#scap_add', $("#"+this.id)).removeAttr("disabled");//生产安排
		$('#yyjms_add', $("#"+this.id)).removeAttr("disabled");//原因及描述
		$('#clcs_add', $("#"+this.id)).removeAttr("disabled");//处理措施
		$("#is_sp_qc_public", $("#"+this.id)).removeAttr("disabled");//是否索赔器材
		$('#sp_qcsm_public', $("#"+this.id)).removeAttr("disabled");//器材
		$('#sp_jgxx_public', $("#"+this.id)).removeAttr("disabled");//索赔信息
		$("#is_sp_rg_public", $("#"+this.id)).removeAttr("disabled");//是否工人
		$('#sp_rgsm_public', $("#"+this.id)).removeAttr("disabled");//人工
		$('#spqx_public', $("#"+this.id)).removeAttr("disabled");//索赔期限
		$('input[name="is_tsgjsb_public"]', $("#"+this.id)).removeAttr("disabled");//特殊工具和设备
		$('#wxgzdxbt_public', $("#"+this.id)).removeAttr("disabled");//维修改装对象部件按钮
		$('#ffbt', $("#"+this.id)).removeAttr("disabled");//分发按钮
		$('#gzgsbt_public', $("#"+this.id)).removeAttr("disabled");//维修改装对象部件按钮
		
		//监控项设置
		$('input[name="zxfsradio"]').removeAttr("disabled");
		$('#hdwzradio').removeAttr("disabled");//后到为准
		$('#zztjbsradio').removeAttr("disabled");//终止条件
		$('#zztj').removeAttr("disabled");//终止条件
		$('input[name="jkxItems"]').removeAttr("disabled");
		$('input.monitorItem').removeAttr("disabled");
		$('input[name="sc"]').removeAttr("disabled");
		$('input[name="zq"]').removeAttr("disabled");
		$('input[name="rcq"]').removeAttr("disabled");
		$('input[name="rch"]').removeAttr("disabled");
		$('button[name="sc_dw"]').removeAttr("disabled");
		$('button[name="zq_dw"]').removeAttr("disabled");
		$('button[name="rc_dw"]').removeAttr("disabled");
		$('#addFdJkx').css("display", "table-cell");
		$('button[name="removeFdJkx"]').css("display", "table-cell");
		$('#jkxsz_frame_package_jsgs').removeAttr("disabled"); //计算公式
	},
	/**设置只读*/
	setReadOnlyFailure : function(){
		/*EO主表信息*/
		$("#eobh_add", $("#"+this.id)).attr("disabled","disabled");//EO编号：必须
		$("#bb_add", $("#"+this.id)).attr("disabled","disabled"); //版本：必须
		$('input[name="isXfsc_add"]').attr("disabled","disabled");//下发生产
		$('#eozt_add', $("#"+this.id)).attr("disabled","disabled"); //EO主题
		$('#jx_add', $("#"+this.id)).attr("disabled","disabled"); //机型：必须
		$("#zjhid_add", $("#"+this.id)).attr("disabled","disabled"); //章节编号
		$("#zjhbt_add", $("#"+this.id)).attr("disabled","disabled"); //章节编号按钮
		$("#zjhName_add", $("#"+this.id)).attr("disabled","disabled"); //章节编号 : 关联
		$("#bfrq_add").attr("disabled","disabled");//颁发日期
		$('input[name="jb_add"]').attr("disabled","disabled");//级别
		$('#gzlx_add', $("#"+this.id)).attr("disabled","disabled"); //工作类型
		$('input[name="sylb_public"]').attr("disabled","disabled");//适用类别
		$('#eolb_add', $("#"+this.id)).attr("disabled","disabled"); //EO类别
		$('#eofj_add', $("#"+this.id)).attr("disabled","disabled");//EO级别
		$('#jhgsRs', $("#"+this.id)).attr("disabled","disabled"); //计划工时_人数
		$('#jhgsXss', $("#"+this.id)).attr("disabled","disabled"); //计划工时_小时数
		$("#rii_add", $("#"+this.id)).attr("disabled","disabled"); //RII
		$('input[name="zxfsradio"]').attr("disabled","disabled");//执行方式
		$("#hdwzradio", $("#"+this.id)).attr("disabled","disabled"); //以后到为准
		$('#jkxsz_frame_package_jsgs', $("#"+this.id)).attr("disabled","disabled"); //计算公式
		
		/*EO从表信息*/
		$('input[name="scjszy_add"]', $("#"+this.id)).attr("disabled","disabled");//首次技术支援
		$('#ywjsyfw_public', $("#"+this.id)).attr("disabled","disabled");//源文件使用范围
		$('#gzgs_add', $("#"+this.id)).attr("disabled","disabled");//工作概述
		$('#fhx_add', $("#"+this.id)).attr("disabled","disabled");//符合性
		$('#gbjl_bc_public', $("#"+this.id)).attr("disabled","disabled");//版次
		$('#gbjl_yqbbgx_public', $("#"+this.id)).attr("disabled","disabled");//与之前版本关系
		$('#gbjl_gbyy_public', $("#"+this.id)).attr("disabled","disabled");//改版原因
		$('#wxgzdxid_public', $("#"+this.id)).attr("disabled","disabled");//维修改装对象 , 部件ID
		$('#wxgzdx_public', $("#"+this.id)).attr("disabled","disabled");//维修改装对象 , 部件号
		$('input[name="wxgzbs_public"]', $("#"+this.id)).attr("disabled","disabled");//标识
		$('#wxgzbs_qt_public', $("#"+this.id)).attr("disabled","disabled");//其他
		$('input[name="is_zzphbh_public"]', $("#"+this.id)).attr("disabled","disabled");//载重平衡变化
		$('#mtc_public', $("#"+this.id)).attr("disabled","disabled");//Mt Change
		$('#wtc_public', $("#"+this.id)).attr("disabled","disabled");//Wt Change
		$('#arm_public', $("#"+this.id)).attr("disabled","disabled");//ARM
		$('#bhnr_public', $("#"+this.id)).attr("disabled","disabled");//变化内容
		$('#yxsc_add', $("#"+this.id)).attr("disabled","disabled");//影响手册
		$('#qcjg_add', $("#"+this.id)).attr("disabled","disabled");//器材价格
		$('#btqcjgdw', $("#"+this.id)).attr("disabled","disabled");//qcjgdw_add 币种
		$('input[name="bfqczb_add"]', $("#"+this.id)).attr("disabled","disabled");//是否颁发器材准备
		$('#bfqczbtzd_add', $("#"+this.id)).attr("disabled","disabled");//颁发器材准备通知单
		$('input[name="is_yxcbw_public"]', $("#"+this.id)).attr("disabled","disabled");//是否影响出版物
		$('#sycjzrjqd_add', $("#"+this.id)).attr("disabled","disabled");//受影响的机载软件清单
		$('input[name="is_dqfzsj_add"]', $("#"+this.id)).attr("disabled","disabled");//电气负载数据变化
		$('#dqfzsj_add', $("#"+this.id)).attr("disabled","disabled");//电气负载数据
		$('#wxxmxgx_add', $("#"+this.id)).attr("disabled","disabled");//维修项目的相关性
		$('#fkyq_add', $("#"+this.id)).attr("disabled","disabled");//反馈要求
		$('#hcxqsqd_add', $("#"+this.id)).attr("disabled","disabled");//航材需求申请单
		$('#yyljcl_add', $("#"+this.id)).attr("disabled","disabled");//原有零件处理
		$('#hhxx_add', $("#"+this.id)).attr("disabled","disabled");//互换信息	
		$('#qt_add', $("#"+this.id)).attr("disabled","disabled");//其他
		$("#zztjbsradio", $("#"+this.id)).attr("disabled","disabled");//终止条件标识
		$('#zztj', $("#"+this.id)).attr("disabled","disabled");//终止条件
		$('#gcjy_add', $("#"+this.id)).attr("disabled","disabled");//工程建议
		$('#scap_add', $("#"+this.id)).attr("disabled","disabled");//生产安排
		$('#yyjms_add', $("#"+this.id)).attr("disabled","disabled");//原因及描述
		$('#clcs_add', $("#"+this.id)).attr("disabled","disabled");//处理措施
		$("#is_sp_qc_public", $("#"+this.id)).attr("disabled","disabled");//是否索赔器材
		$('#sp_qcsm_public', $("#"+this.id)).attr("disabled","disabled");//器材
		$('#sp_jgxx_public', $("#"+this.id)).attr("disabled","disabled");//索赔信息
		$("#is_sp_rg_public", $("#"+this.id)).attr("disabled","disabled");//是否工人
		$('#sp_rgsm_public', $("#"+this.id)).attr("disabled","disabled");//人工
		$('#spqx_public', $("#"+this.id)).attr("disabled","disabled");//索赔期限
		$('input[name="is_tsgjsb_public"]', $("#"+this.id)).attr("disabled","disabled");//特殊工具和设备
		$('#wxgzdxbt_public', $("#"+this.id)).attr("disabled","disabled");//维修改装对象部件按钮
		$('#ffbt', $("#"+this.id)).attr("disabled","disabled");//维修改装对象部件按钮
		$('#gzgsbt_public', $("#"+this.id)).attr("disabled","disabled");//维修改装对象部件按钮
		$('.multiselect').attr("disabled","disabled"); //区域
		$('#efjzw_ico_public', $("#"+this.id)).css("display", "none"); // 飞机站位
		$('#ff', $("#"+this.id)).attr("disabled","disabled");//分发

		this.setReadOnly4JkxFailure();//监控项设置
	},
	//监控项设置
	setReadOnly4JkxFailure : function(){
		//监控项设置
		$('input[name="zxfsradio"]').attr("disabled","disabled");
		$('#hdwzradio').attr("disabled","disabled");//后到为准
		$('#zztjbsradio').attr("disabled","disabled");//终止条件
		$('#zztj').attr("disabled","disabled");//终止条件
		$('input[name="jkxItems"]').attr("disabled","disabled");
		$('input.monitorItem').attr("disabled","disabled");
		$('input[name="sc"]').attr("disabled","disabled");
		$('input[name="zq"]').attr("disabled","disabled");
		$('input[name="rcq"]').attr("disabled","disabled");
		$('input[name="rch"]').attr("disabled","disabled");
		$('button[name="sc_dw"]').attr("disabled","disabled");
		$('button[name="zq_dw"]').attr("disabled","disabled");
		$('button[name="rc_dw"]').attr("disabled","disabled");
		$('#addFdJkx').css("display", "none");
		$('button[name="removeFdJkx"]').css("display", "none");
		$('#jkxsz_frame_package_jsgs').attr("disabled","disabled"); //计算公式
	},
	 //初始化区域下拉框
	 initEOZoneInformation : function(_jx, _dprtcode) {
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = _dprtcode;
		searchParam.jx = _jx;//机型
		searchParam.lx = 1;
		var zoneOption = '';
		AjaxUtil.ajax({
			url : basePath + "/basic/zone/zoneList",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(searchParam),
			success : function(data) {
				/*if(this_.param.editParam == this_.editParam.audit || this_.param.editParam == this_.editParam.approve) {
					$("#qy_publicDiv").empty();
					$("#qy_publicDiv").html("<input class='form-control' id='qy_public' readonly='readonly' >");
					if (data != null && data.length > 0) {
						this_.quyuDatas = data;
					}else{
						$("#qy_public").empty();
					}
				}else{
					$("#qy_publicDiv").empty();
					$("#qy_publicDiv").html("<select class='form-control' id='qy_public'  multiple='multiple' ></select>");
					//生成多选下拉框
					$('#qy_public').multiselect({
						buttonClass: 'btn btn-default',
						buttonWidth: '100%',
						buttonContainer: '<div class="btn-group" style="width:100%;" />',
						numberDisplayed:4,
						nonSelectedText:'显示全部 All',
						allSelectedText:'显示全部 All',
						includeSelectAllOption: true,
						selectAllText: '选择全部 Select All'
					});
					if (data != null && data.length > 0) {
						$.each(data, function(i, row) {
							var tempOption = "<option value='" + StringUtil.escapeStr(row.id) + "' >" + StringUtil.escapeStr(row.sz) + "</option>";
							zoneOption += tempOption;
						});
						$("#qy_public").html(zoneOption);
					}else{
						$("#qy_public").empty();
					}
					$('#qy_public').multiselect('rebuild');//重新加载
				}*/
				
				if(this_.param.editParam != this_.editParam.add ){
					this_.reviewQyDatas(); //回显区域和飞机站位数据
				}else{
					$("#efjzw_public").val("");
					fjzwList = []; //飞机站位数据集合
					positionPublicList = [];//飞机站位数据集合
					
					$("#eqy_public").val("");
				}
			}
		});
	},
	//显示历史版本信息
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#"+this_.id,function(obj){
			return this_.getHistoryBbList(obj);
		});
	},
	getHistoryBbList : function(obj){//获取历史版本列表
		return eo_history_view_alert_Modal.getHistoryBbList(this.param.viewObj.id);
	},
	//查看历史信息
	showHistoryView : function(){
		var this_ = this;
		if(this.param.editParam == this.editParam.modify){
			eo_main.openWinView(this.param.viewObj.id,1);
		}else{
			if(this.param.viewObj.fBbid != null){
				eo_main.openWinView(this.param.viewObj.fBbObj.id,1);
			}
		}
	},
	//清除数据
	clearDatas : function(){
		$("#EOAddModal input[type='checkbox']").removeAttr("checked");
		
		//勾了就显示的文本框
		if(this.param.editParam == this.editParam.add){
			$("#wxgzbs_qt_public").css("visibility","hidden");
			$("#zztj").css("visibility","hidden");
			$("#sp_qcsm_public").css("visibility","hidden");
			$("#sp_rgsm_public").css("visibility","hidden");
		}
		
		$("#EOAddModal input[type='text']").val("");
		$("#EOAddModal input[type='hidden']").val("");
		$("#EOAddModal textarea").val("");
		$("#EOAddModal input[type='text']").removeClass("border-color-red");
		$("#EOAddModal textarea").removeClass("border-color-red");
		
		TimeUtil.getCurrentDate("#bfrq_add"); //颁发日期,默认当前日期
		$("#rii_add", $("#"+this.id)).removeAttr("checked");
		$("#hdwzradio", $("#"+this.id)).removeAttr("checked");
		$('input[name="zxfsradio"][value="1"]').prop("checked", "checked");//执行方式
		//标识
		$('input:checkbox[name="wxgzbs_public"]').each(function(i){ 
			$(this).removeAttr("checked");
        });  
		$('input[name="is_zzphbh_public"][value="1"]').prop("checked", "checked");//载重平衡变化
		$('input[name="bfqczb_add"][value="1"]').prop("checked", "checked");//是否颁发器材准备
		$('input[name="is_yxcbw_public"][value="1"]').prop("checked", "checked"); //是否影响出版物
		showPulAffected(1);//是否影响出版物
		$('input[name="is_dqfzsj_add"][value="1"]').prop("checked", "checked");//电气负载数据变化
		$("#zztjbsradio", $("#"+this.id)).removeAttr("checked");
		$("#is_sp_rg_public", $("#"+this.id)).removeAttr("checked");
		$('input[name="is_tsgjsb_public"][value="1"]').prop("checked", "checked");//特殊工具和设备
		$('input[name="scjszy_add"][value="1"]').prop("checked", "checked");//首次技术支援
		$('input[name="isXfsc_add"][value="1"]').prop("checked", "checked");//是否下发
		$('input[name="jb_add"][value="9"]').prop("checked", "checked");//级别
		$("#efjzw_public").val("");//飞机站位数据集合
		fjzwList = []; //飞机站位数据集合
		positionPublicList = [];//飞机站位数据集合
	},
	//回显接近（盖板）/区域/站位
	reviewQyDatas : function(){
		var this_ = this;
		var obj = this_.param.viewObj;
		if(obj.qyzwList != null && obj.qyzwList.length > 0){
			var zonePublicList = [];
			var positionPublicViewList = [];
			var stationStr = "";
			var quyuStr = "";
			
			$.each(obj.qyzwList, function(i, cover){
				if(cover.lx == 1){
					/*if(this_.param.editParam == this_.editParam.audit || this_.param.editParam == this_.editParam.approve) {
						$.each(this_.quyuDatas, function(i, row) {
							if(cover.gbid == row.id){
								quyuStr += formatUndefine(row.sz) + ",";
							}
						});
					}else{
						zonePublicList.push(cover.gbid);
					}*/
					quyuStr += formatUndefine(cover.gbh) + ",";
				}
				if(cover.lx == 3){
					positionPublicViewList.push({
						id : cover.gbid,
						sz : cover.gbh
					});
					stationStr += formatUndefine(cover.gbh) + ",";
				}
			});
			
			// 回显区域
			/*if(this_.param.editParam == this_.editParam.audit || this_.param.editParam == this_.editParam.approve) {
				if(quyuStr != ''){
					quyuStr = quyuStr.substring(0,quyuStr.length - 1);
				}
				$('#qy_public').val(quyuStr);
			}else{
				$('#qy_public').multiselect('select', zonePublicList);
			}*/
			
			if(quyuStr != ''){
				quyuStr = quyuStr.substring(0,quyuStr.length - 1);
			}
			$('#eqy_public').val(quyuStr);
			
			// 回显飞机站位
			positionPublicList = positionPublicViewList;
			if(stationStr != ''){
				stationStr = stationStr.substring(0,stationStr.length - 1);
			}
			
			if(obj.jx == $("#jx_add").val()){
				$("#efjzw_public").val(stationStr);
			}else{
				$("#efjzw_public").val("");
				fjzwList = []; //飞机站位数据集合
				positionPublicList = [];//飞机站位数据集合
			}
		}else{
			$("#efjzw_public").val("");
			fjzwList = []; //飞机站位数据集合
			positionPublicList = [];//飞机站位数据集合
		}
	},
	changeInput : function(obj){
		$(obj).removeClass("border-color-red");
	},
	clearCodeValidator : function(obj) {
		var this_ = this;
		if(!this_.codeValidator.reg.test(obj.value)){  
			obj.value = "";
		}
		this_.changeInput(obj);
	}
};


