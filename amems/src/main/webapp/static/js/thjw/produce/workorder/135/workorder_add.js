/**
 * 135工单新增
 */
Workorder135AddWin = {
	id:'workorder135_add_Modal', //窗口ID
	tbodyId:'', //列表ID
	colOptionheadClass : "colOptionhead",
	zoneOption : '',
	jx:'', //机型
	accessList : [],
	accessIdList : [],
	positionList : [],
	positionIdList : [],
	gdbt:null,
	gkbt:null,
	isLoad:false,//是否加载
	param: {
		modalHeadCN : '',//窗口中文名称
		modalHeadENG : '',//窗口英文名称
		parentWinId : 'workorder135_add_Modal',//父窗口ID
		colOptionhead : true,
		editParam:1,//1:新增,2:修改,3:审核,4:批准,5:改版
		gdid:null,//工单id
		viewObj:{},
		dprtcode:userJgdm,//默认登录人当前机构代码
		blLx:"", //保留类型 1:故障保留  2:缺陷保留
		blId:"", //保留类型ID
		gbId:"", //工包ID
		gbBh:"", //工包编号
		callback:function(){},//回调函数
	},
	editParam : {
		 add : 1//新增
		,edit : 2//修改
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	/**显示窗口*/
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this_.param, param);
		}
		/*新增:取当前人deptcode,编辑：取单据deptcode*/
		if(this_.param.editParam == this_.editParam.add){
			this_.param.dprtcode =  userJgdm; 
		}else{
			this_.param.dprtcode = this_.param.viewObj.dprtcode;
		}
		
		/*清空所有元素*/
		this.clearDatas();
		
		/*初始数据*/
		this.initInfo();
	},
	/**初始数据*/
	clearDatas : function(){
		var this_ = this;
		$("#"+this.id+" input[type='text']").val("");
		$("#"+this.id+" input[type='hidden']").val("");
		$("#"+this.id+" textarea").val("");
		
	    //清除只读
		$("#wo135add_bjbs").removeAttr("checked");//必检RII
		$("#wo135add_gdbh").removeAttr("disabled");//工单编号
		$("#wo135add_fjzch").removeAttr("disabled");//飞机注册号
	    $("#wo135add_lyrwh").removeAttr("disabled");//来源任务号
	    $("#wo135add_gbbh").removeAttr("disabled");//工包编号	    
	    $('#wo135add_lyrwh').addClass("readonly-style");//灰色
		$('#wo135add_lyrwbtn').css("display", "table-cell");//放大镜隐藏
		$('input[name="wo135add_gdlx"]').removeAttr("disabled"); //来源
		$("#wo135add_gkbh").removeAttr("disabled");//来源工卡号
		$('#wo135add_gkbh').addClass("readonly-style");//灰色
		$('#wo135add_gkbtn').css("display", "table-cell");//放大镜隐藏
		$("#wo135add_zjhName").removeAttr("disabled");//ATA章节号
		$('#wo135add_zjhName').addClass("readonly-style");//灰色
		$('#wo135add_gbbh').addClass("readonly-style");//灰色
		$('#wo135add_zjhbtn').css("display", "table-cell");//放大镜隐藏
		$("#wo135add_lyrwh_div").show();
		$("#wo135add_wcsx_div").show();
		//显示所有按钮
		$(".operation-btn ", $("#"+this_.id)).show();
	},
	/**初始数据*/
	initInfo : function(){
		var this_ = this;
		
		//初始窗口标题信息
		this.initModelHead();
		
		//显示窗口
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		
		//初始化表单验证
		if(!this.isLoad){
			this.initFormValidator();
			this.isLoad = true;
		}
		
		//初始化数据字典
		this.initDicData();
		
		//加载基础数据
		if(this_.param.editParam != this_.editParam.add){
			this.loadeBaseData();
		}else{
			$("#wo135add_wcsx_div").hide();
			$("#wo135add_lyrwh_div").hide();
			TimeUtil.getCurrentDate("#wo135add_kdrq");
			if(this_.param.blLx != "" && this_.param.blId !=""){
				this_.getBL(this_.param.blId,this_.param.blLx);
			}
		}
		
		//初始化公共组件 并 显示数据
		this.initZujian();
		
		//显示隐藏
		this.showOrHideElement();
	},
	/**初始化验证*/
	initFormValidator : function(){
		var this_ = this;
		this_.formValidator();
		//隐藏Modal时验证销毁重构
		$("#"+this_.id).on("hidden.bs.modal", function() {
			 $("#workorder135_form").data('bootstrapValidator').destroy();
		     $('#workorder135_form').data('bootstrapValidator', null);
		     this_.formValidator();
		});
		
		//计划开始日期
		$('#wo135add_jh_ksrq').datepicker({
			autoclose : true,
			clearBtn : true
		}).on('hide',function(e) {
			$('#workorder135_form').data('bootstrapValidator').updateStatus('wo135add_jh_ksrq','NOT_VALIDATED', null).validateField('wo135add_jh_ksrq');
		});
		
		//计划结束日期
		$('#wo135add_jh_jsrq').datepicker({
			autoclose : true,
			clearBtn : true
		}).on('hide',function(e) {
			$('#workorder135_form').data('bootstrapValidator').updateStatus('wo135add_jh_jsrq','NOT_VALIDATED', null).validateField('wo135add_jh_jsrq');
		});

	},
	formValidator : function(){//加载验证
		validatorForm =  $('#workorder135_form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	wo135add_gdbh: {
	                validators: {
	                    regexp: {
	                    	regexp: /^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$/,
	                        message: '只能输入长度不超过50个字符的英文、数字、英文特殊字符'
	                    }
	                }
	            },	            
	            wo135add_gdbt: {
	            	validators: {
	            		notEmpty: {
	            			message: '工单标题不能为空'
	            		}
	            	}
	            },
	            eqy: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[\x00-\xff]*$/,
	                        message: '不能输入中文'
	                    }
	            	}
	            },
	            efjzw: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[\x00-\xff]*$/,
	                        message: '不能输入中文'
	                    }
	            	}
	            }
	        }
	    });	 
	},
	/**初始化对话框头部*/
	initModelHead : function(){
		if('' != this.param.modalHeadCN){
			$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
			$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
		}
	},
	/**初始化数据字典*/
	initDicData : function(){
		var this_ = this;
		var fjjx = '';
		if(todo_fjjx){
			todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
			fjjx = todo_fjjx;
			todo_fjjx = null;
		}
		//飞机注册号
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this_.param.dprtcode,FJJX:fjjx});
		var planeRegOption = "";
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' fjjx='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
			});
		}
		planeRegOption += "<option value='' fjjx='' >"+"N/A"+"</option>";
		$("#wo135add_fjzch", $("#"+this_.id)).html(planeRegOption); 
		if(this_.param.editParam != this_.editParam.add){
			var fjzch = this_.param.viewObj.fjzch;
			if(fjzch == "-"){
				fjzch= "";
			}
			this_.param.viewObj.fjzch = fjzch;
			$("#wo135add_fjzch").val(fjzch);//飞机注册号
		}
		
		//工作类别
		$("#wo135add_gzlb", $("#"+this.id)).empty();
		/*$("#wo135add_gzlb", $("#"+this.id)).append('<option value="" selected="selected">显示全部All</option>');*/
		DicAndEnumUtil.registerDic('17','wo135add_gzlb',this_.param.dprtcode);
		
		//专业
		$("#wo135add_zy", $("#"+this_.id)).empty();
		DicAndEnumUtil.registerDic('4','wo135add_zy',this_.param.dprtcode);
		
		//根据飞机机型加载数据
		this_.loadDatasByJX();
	},
	/**加载基础数据*/
	loadeBaseData : function(){
		var this_ = this;
		$("#wo135add_zt").val(formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', this_.param.viewObj.zt)));//状态
		
		$("#wo135add_gdbh").val(this_.param.viewObj.gdbh);//工单编号
		$("#wo135add_fjzch").val(this_.param.viewObj.fjzch);//飞机注册号
		
		if(null != this_.param.viewObj.fixChapter){
			$("#wo135add_zjhid").val(this_.param.viewObj.fixChapter.zjh);//ATA章节号ID
			$("#wo135add_zjhName").val(this_.param.viewObj.fixChapter.displayName);//ATA章节号Name
		}
		
		if(this_.param.viewObj.bjbs != null && this_.param.viewObj.bjbs == 1){//必检RII
			$("#wo135add_bjbs").attr("checked","checked");
		}else{
			$("#wo135add_bjbs").removeAttr("checked");
		}
		if(this_.param.editParam == this_.editParam.add){
			$('input[name="wo135add_gdlx"][value="'+this_.param.viewObj.gdlx+'"]').prop("checked", "checked");//工单类型
		}else{
			$("#edit_gdlx_id").val(this_.param.viewObj.gdlx);
			$('#edit_gdlx_name').val(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(this_.param.viewObj.gdlx)));//工单类型
		}
		if(this_.param.viewObj.gdlx==4||this_.param.viewObj.gdlx==5){
			$("#wo135add_lyrwh_div").hide();
			$("#wo135add_wcsx_div").hide();
		}
		$("#wo135add_lyrwh").val(this_.param.viewObj.lyrwh);//来源任务号
		$("#wo135add_lyrwid").val(this_.param.viewObj.lyrwid);//来源任务号ID
		$("#wo135add_gkbh").val(this_.param.viewObj.gkbh);//来源工卡号
		$("#wo135add_gkid").val(this_.param.viewObj.gkid);//来源工卡ID
		$("#wo135add_gdbt").val(this_.param.viewObj.gdbt);//工单标题
		$("#wo135add_gbid").val(this_.param.viewObj.gbid);//工包ID
		$("#wo135add_gbbh").val(this_.param.viewObj.paramsMap.gbbh);//工包号
		if(null != this_.param.viewObj.kdrq){
			$("#wo135add_kdrq").val(this_.param.viewObj.kdrq.substring(0, 10));//开单日期
		}else{
			TimeUtil.getCurrentDate("#wo135add_kdrq");
		}
		if(null != this_.param.viewObj.jhKsrq){
			$("#wo135add_jh_ksrq").val(this_.param.viewObj.jhKsrq.substring(0, 10));//计划开始日期
		}
		if(null != this_.param.viewObj.jhJsrq){
			$("#wo135add_jh_jsrq").val(this_.param.viewObj.jhJsrq.substring(0, 10));//计划结束日期
		}
		$("#wo135add_bgrid").val(this_.param.viewObj.bgrid);//报告人ID
		$("#wo135add_bgr").val(this_.param.viewObj.bgr);//报告人
		$("#wo135add_zy").val(this_.param.viewObj.zy);//专业
		$("#wo135add_jh_zd").val(this_.param.viewObj.jhZd);//计划站点
		$("#wo135add_gzlb").val(this_.param.viewObj.gzlb);//工作类别
		$("#wo135add_jhgs_rs").val(this_.param.viewObj.jhgsRs);//人数
		$("#wo135add_jhgs_xss").val(this_.param.viewObj.jhgsXss);//小时
		this_.sumTotal();
		$("#wo135add_ybgs").val(this_.param.viewObj.ybgs);//一般概述
		$("#wo135add_qxms").val(this_.param.viewObj.qxms);//缺陷描述
		$("#wo135add_jyclfa").val(this_.param.viewObj.jyclfa);//建议处理方案
		if(null != this_.param.viewObj.monitoringPlan && null != this_.param.viewObj.monitoringPlan.paramsMap){
			var limtStr = this_.param.viewObj.monitoringPlan.paramsMap.jhsjsj;
			$("#wo135add_wcsx").val(this_.wxsxCover(limtStr));//完成时限
		}
		
		if(null != this_.param.viewObj.monitoringObject){
			$("#wo135add_bjh").val(this_.param.viewObj.monitoringObject.bjh);//部件号
			$("#wo135add_xlh").val(this_.param.viewObj.monitoringObject.xlh);//序列号
		}
		this_.calRwmsCount();
	},
	/**完成时限*/
	wxsxCover : function (limtStr){
		var showStr = "";
		/*if(null != this_.param.viewObj.monitoringPlan && null != this_.param.viewObj.monitoringPlan.paramsMap){
			var limtStr = this_.param.viewObj.monitoringPlan.paramsMap.jhsjsj;*/
			
			if(null != limtStr && "" != limtStr){
				var limitArr = limtStr.split(',');
				MonitorUnitUtil.sortByStrList(limitArr);
				for (i=0;null != limitArr && i< limitArr.length;i++){
					var obj = limitArr[i].split('#_#');
					if(null != obj && obj.length == 2){
						var jklbh = StringUtil.escapeStr(obj[0]); //监控类型
						var unit = MonitorUnitUtil.getMonitorUnit(jklbh); //单位
						var val = StringUtil.escapeStr(obj[1]); //实际值
						if(MonitorUnitUtil.isTime(jklbh)){
							val = TimeUtil.convertToHour(val, TimeUtil.Separator.COLON);
						}
						if(i==limitArr.length-1){
							showStr += val+unit;
						}else{
							showStr += val+unit+",";
						}
					}
				}
			}
		/*}*/
		return showStr;
	},
	/**初始化组件*/
	initZujian : function(){
		var this_ = this;
		if(this_.param.viewObj.gdlx ==1||this_.param.viewObj.gdlx ==2||this_.param.viewObj.gdlx ==9){
			$("#wo135add_jspgddiv").css("display","none");
		}else{
			$("#wo135add_jspgddiv").css("display","block");
			//选择评估单
			evaluation_modal.init({
				isShowed : (this_.param.viewObj.zt==7)?false:true,
				zlid : this_.param.viewObj.id,
				zlxl : 4,
				dprtcode :this_.param.dprtcode,
				jx : this_.jx,
			});	
		}
		//初始化工种工时
		work_hour_edit.init({
			djid:this_.param.viewObj.id,//关联单据id
			parentWinId : this_.param.parentWinId,
			ywlx:21,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : this_.param.colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});

		//区域、接近、飞机站位
		this_.loadCoverPlate(this_.param.viewObj.coverPlateList);
		
		//初始化参考文件
		ReferenceUtil.init({
			djid:this_.param.viewObj.id,//关联单据id
			ywlx:21,//业务类型：21:工单
			colOptionhead : this_.param.colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化器材清单
		Equipment_list_edit.init({
			djid:this_.param.viewObj.id,//关联单据id
			parentWinId : this_.param.parentWinId,
			ywlx:21,//业务类型：21:工单
			colOptionhead : this_.param.colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化工具清单
		Tools_list_edit.init({
			djid:this_.param.viewObj.id,//关联单据id
			parentWinId :this_.param.parentWinId,
			ywlx:21,//业务类型：21:工单
			colOptionhead : this_.param.colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//工作内容附件：上传按钮
		$("#gznrfjid").val(this_.param.viewObj.gznrfjid);
		attachmentsSingleUtil.initAttachment(
				"work_content_attachments_single_edit"//主div的id
				,this_.param.viewObj.workContentAttachment//附件
				,'workorder135'//文件夹存放路径
				,this_.param.colOptionhead//true可编辑,false不可编辑
		);
		
		//初始化工作内容
		WorkContentUtil.init({
			djid:this_.param.viewObj.id,//关联单据id
			ywlx:21,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单 21工单
			colOptionhead : this_.param.colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//显示附件列表
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('workorder135_add_attachments_list_edit');
		attachmentsObj.show({
			djid:this_.param.viewObj.id,
			fileHead : true,
			colOptionhead : this_.param.colOptionhead,
			fileType:"workorder135"
		});
		
	},
	/**显示或隐藏页面元素*/
	showOrHideElement : function(){
		var this_ = this;
		if(this_.param.editParam == this_.editParam.add){//新增
//			$('input[name="wo135add_gdlx"]').attr("disabled","disabled"); //来源不可编辑
			$('input[name="wo135add_gdlx"][value="4"]').prop("checked", "checked");//来源默认为NRC
			$("#wo135add_zt_div").hide();
			$('#wo135add_gbbtn').show();
			$("#wo135add_bgrdiv").show();
			$("#wo135add_qxmsdiv").show();
			$("#wo135add_jyclfadiv").show();
			$("#add_order_lx").show();
			$("#edit_order_lx").hide();
		}else if (this_.param.editParam == this_.editParam.edit){//编辑
			$("#add_order_lx").hide();
			$("#edit_order_lx").show();
			
			$("#wo135add_zt_div").show();
			$('#wo135add_gbbtn').hide();
			$("#wo135add_gbbh").attr("disabled","disabled");//工包编号
			$('#wo135add_gbbh').removeClass("readonly-style");//工包编号
			if(this_.param.viewObj.gdlx != 4 && this_.param.viewObj.gdlx != 5){
				$("#wo135add_bgrdiv").hide();
				$("#wo135add_qxmsdiv").hide();
				$("#wo135add_jyclfadiv").hide();
			}else{
				$("#wo135add_bgrdiv").show();
				$("#wo135add_qxmsdiv").show();
				$("#wo135add_jyclfadiv").show();
			}
			if(this_.param.viewObj.zt == 1){ //保存状态
				$("#wo135add_gdbh").attr("disabled","disabled");//工单编号
				$("#wo135add_fjzch").attr("disabled","disabled");//飞机注册号
//				$('input[name="wo135add_gdlx"]').attr("disabled","disabled"); //来源
			}else if(this_.param.viewObj.zt == 2){ //提交状态
				$("#wo135add_gdbh").attr("disabled","disabled");//工单编号
				$("#wo135add_fjzch").attr("disabled","disabled");//飞机注册号
			    $("#wo135add_lyrwh").attr("disabled","disabled");//来源任务号
			    $('#wo135add_lyrwh').removeClass("readonly-style");//灰色
				$('#wo135add_lyrwbtn').css("display", "none");//放大镜隐藏
//				$('input[name="wo135add_gdlx"]').attr("disabled","disabled"); //来源
				$("#wo135add_gkbh").attr("disabled","disabled");//来源工卡号
				$('#wo135add_gkbh').removeClass("readonly-style");//灰色
				$('#wo135add_gkbtn').css("display", "none");//放大镜隐藏
				$("#wo135add_gbbh").attr("disabled","disabled");//工包号
				$("#wo135add_zjhName").attr("disabled","disabled");//ATA章节号
				$('#wo135add_zjhName').removeClass("readonly-style");//灰色
				$('#wo135add_zjhbtn').css("display", "none");//放大镜隐藏
				$("#save_btn", $("#"+this_.id)).hide();
			}else if(this_.param.viewObj.zt == 7){ //下发
				$("#wo135add_gdbh").attr("disabled","disabled");//工单编号
				$("#wo135add_fjzch").attr("disabled","disabled");//飞机注册号
			    $("#wo135add_lyrwh").attr("disabled","disabled");//来源任务号
			    $('#wo135add_lyrwh').removeClass("readonly-style");//灰色
				$('#wo135add_lyrwbtn').css("display", "none");//放大镜隐藏
//				$('input[name="wo135add_gdlx"]').attr("disabled","disabled"); //来源
				$("#wo135add_gkbh").attr("disabled","disabled");//来源工卡号
				$('#wo135add_gkbh').removeClass("readonly-style");//灰色
				$('#wo135add_gkbtn').css("display", "none");//放大镜隐藏
				$("#wo135add_gbbh").attr("disabled","disabled");//工包号
				$("#wo135add_zjhName").attr("disabled","disabled");//ATA章节号
				$('#wo135add_zjhName').removeClass("readonly-style");//灰色
				$('#wo135add_zjhbtn').css("display", "none");//放大镜隐藏
				$("#save_btn", $("#"+this_.id)).hide();
			}
		}
		$('.date-picker').datepicker('update');
	},
	/**获取数据*/
	getData : function(type){
		var this_ = this;
		var param = {};
		
		if(this_.param.editParam != this_.editParam.add){
			param.id = this_.param.viewObj.id;
		}else{
			param.blLx = this_.param.blLx; //保留类型 1:故障保留  2:缺陷保留
			param.blId = this_.param.blId;  //保留类型ID
		}
		
		//工单135基本数据
		param.gdbh = $("#wo135add_gdbh").val();//工单编号
		param.fjzch = $("#wo135add_fjzch").val();//飞机注册号
		param.jx = $("#wo135add_fjzch").find("option:selected").attr("fjjx"); //飞机注册号对应的飞机机型
		param.zjh = $("#wo135add_zjhid").val();//ATA章节号
		param.bjbs = $('#wo135add_bjbs').attr('checked')?"1":"0";//必检RII
		
		if (this_.param.editParam == this_.editParam.add){
			param.gdlx = $('input[name="wo135add_gdlx"]:checked').val();//工单类型
		}else{
			param.gdlx = $('#edit_gdlx_id').val();//工单类型
		}
		param.lyrwh = $("#wo135add_lyrwh").val();//来源任务号
		param.lyrwid = $("#wo135add_lyrwid").val();//来源任务号ID
		param.gkbh = $("#wo135add_gkbh").val();//来源工卡号
		param.gkid = $("#wo135add_gkid").val();//来源工卡ID
		param.gdbt = $("#wo135add_gdbt").val();//工单标题
		param.gbid = $("#wo135add_gbid").val();//工包号
		param.kdrq = $("#wo135add_kdrq").val();//开单日期
		param.jhKsrq = $("#wo135add_jh_ksrq").val();//计划开始日期
		param.jhJsrq = $("#wo135add_jh_jsrq").val();//计划结束日期
		param.bgrid = $("#wo135add_bgrid").val();//报告人ID
		param.bgr = $("#wo135add_bgr").val();//报告人
		param.zy = $("#wo135add_zy").val();//专业
		param.jhZd = $("#wo135add_jh_zd").val();//计划站点
		param.gzlb = $("#wo135add_gzlb").val();//工作类别
		param.jhgsRs = $.trim($("#wo135add_jhgs_rs").val());//人数
		param.jhgsXss = $.trim($("#wo135add_jhgs_xss").val());//小时
		param.ybgs = $.trim($("#wo135add_ybgs").val());//一般概述
		param.qxms = $.trim($("#wo135add_qxms").val());//缺陷描述
		param.jyclfa = $.trim($("#wo135add_jyclfa").val());//建议处理方案
		param.dprtcode =  this_.param.dprtcode;//部门
		
		var ywdjzt =1;
		if(type != null && type != ''){
			if(this_.param.viewObj.zt==7){
				ywdjzt = this_.param.viewObj.zt
			}else{
				ywdjzt = type;
			}		
		}
		// 技术评估单数据
		var technicalList = evaluation_modal.getData();
		if (technicalList != null && technicalList != '') {
			var instructionsourceList = [];
			// 组装下达指令数据
			$.each(technicalList, function(index, row) {
				var instructionsource = {};
				instructionsource.dprtcode = row.dprtcode;
				instructionsource.pgdid = row.id;
				instructionsource.pgdh = row.pgdh;
				instructionsource.bb = row.bb;
				instructionsource.zlbh = $.trim($("#wo135add_gdbh").val());
				instructionsource.ywzt = $.trim($("#wo135add_gdbt").val());
				instructionsource.ywdjzt = ywdjzt;
				instructionsourceList.push(instructionsource);
			});
			param.isList = instructionsourceList;
		}
		var coverPlateList = [];			
		//设置区域
		var coverPlate = {};
		coverPlate.gbid = '';
		coverPlate.gbh = $.trim($("#eqy",$("#"+this_.id)).val());
		coverPlate.lx = 1;
		coverPlate.xc = i;
		coverPlateList.push(coverPlate);		

		//设置接近
		$.each(this_.accessList,function(index,row){
			var coverPlate = {};
			coverPlate.gbid = row.id;
			coverPlate.gbh = row.gbbh;
			coverPlate.lx = 2;
			coverPlate.xc = index + 1;
			coverPlateList.push(coverPlate);
		});
		//设置飞机站位
		var coverPlate = {};
		coverPlate.gbid = '';
		coverPlate.gbh = $.trim($("#efjzw",$("#"+this_.id)).val());
		coverPlate.xc = 1;
		coverPlate.lx = 3;
		coverPlateList.push(coverPlate);
		param.coverPlateList = coverPlateList;
		param.workHourList = work_hour_edit.getWorkHourList();//工种工时
		param.referenceList = ReferenceUtil.getData();//设置参考文件
		//设置器材/工具
		var materialToolList = [];
		//设置器材清单
		materialToolList = Equipment_list_edit.getData();
		//设置工具设备
		var toolArr = Tools_list_edit.getData();
		$.each(toolArr,function(index,row){
			var infos = {};
			infos.id = row.id;
			infos.bjid = row.bjid;
			infos.jh = row.jh;
			infos.bjlx = row.bjlx;
			infos.sl = row.sl;
			infos.bz = row.bz;
//			infos.hhxx = row.hhxx;
			infos.qcdh = row.qcdh;
			infos.bxx = row.bxx;
			infos.xc = row.xc;
			materialToolList.push(infos);
		});
		param.materialToolList = materialToolList;
		param.workContentList = WorkContentUtil.getData();//工作内容
		//设置工作内容附件
		var workContentAttachment = attachmentsSingleUtil.getAttachment('work_content_attachments_single_edit');
		if(workContentAttachment != null && workContentAttachment.wjdx != null && workContentAttachment.wjdx != ''){
			param.workContentAttachment = workContentAttachment;
		}
		//附件列表
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('workorder135_add_attachments_list_edit');
		param.attachments = attachmentsObj.getAttachments();
		
		return param;
	},
	/**
	 * 验证详情数据
	 */
	vilidateDetailData : function(data){
		var this_ = this;
		
		//验证工作内容
		if(!WorkContentUtil.isValid){
			return false;
		}
		
		//验证工作内容附件
		if(!attachmentsSingleUtil.isVaildMap['work_content_attachments_single_edit']){
			return false;
		}
		
		//验证工具设备
		if(!Tools_list_edit.isValid){
			return false;
		}
		
		//验证器材清单
		if(!Equipment_list_edit.isValid){
			return false;
		}
		
		//验证参考文件
		if(!ReferenceUtil.isValid){
			return false;
		}
		//验证工种工时
		var rs = data.jhgsRs //人数
		var xs = data.jhgsXss //小时
		var reg = /^[0-9]*$/;
		var reg1 = /^[0-9]{1,10}(\.[0-9]{0,2})?$/;
		if(reg.test(rs)==false){
			AlertUtil.showModalFooterMessage("计划工时输入不正确，请输入整数!");
			return false;
		}
		if(xs != ''&&reg1.test(xs)==false){
			AlertUtil.showModalFooterMessage("计划工时小时数输入不正确，只能输入数值，精确到两位小数!");
			return false;
		}
		//验证标题长度
		var rwmsCount = this_.calRwmsCount();
		if(rwmsCount == 0){
			AlertUtil.showModalFooterMessage("工单标题不能为空！");
			return false;
		}
		if(rwmsCount > 4000){
			AlertUtil.showModalFooterMessage("工单标题的最大长度为4000！");
			return false;
		}
		return true;
	},
	/** 保存数据 状态：1保存、2提交*/
	setData: function(operationType){
		var this_ = this;
		$('#workorder135_form').data('bootstrapValidator').validate();
		if(!$('#workorder135_form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			$("#"+this_.id+" .modal-body").prop('scrollTop','0');
			return false;
		}
		
		//获取数据
		var reqData = this_.getData(operationType); 
		
		//验证详情数据
		if(!this_.vilidateDetailData(reqData)){
			return false;
		}
		
		var paramsMap = {};
		//操作类型
		if(operationType != null && operationType != ''){
			if(this_.param.viewObj.zt==7){
				paramsMap.operationType = this_.param.viewObj.zt
				reqData.gbid = this_.param.viewObj.gbid;
				reqData.gdsbid = this_.param.viewObj.gdsbid;
				reqData.gznrfjid = this_.param.viewObj.gznrfjid;
			}else{
				paramsMap.operationType = operationType;
			}		
		}
		paramsMap.dprtcode =  this_.param.dprtcode; 
		
		/*Start:不需判空，要清空的字段 */
		if(null == reqData.jhgsRs || "" == reqData.jhgsRs){
			paramsMap.jhgsRs = "ND"; //计划工时_人数
		}
		if(null == reqData.jhgsXss || "" == reqData.jhgsXss){
			paramsMap.jhgsXss = "ND"; //计划工时_人数
		}
		if(null == reqData.kdrq || "" == reqData.kdrq){
			paramsMap.kdrq = "ND"; //开单日期
		}
		if(null == reqData.jhKsrq || "" == reqData.jhKsrq){
			paramsMap.jhKsrq = "ND"; //计划开始日期
		}
		if(null == reqData.jhJsrq || "" == reqData.jhJsrq){
			paramsMap.jhJsrq = "ND"; //计划结束日期
		}
		/*End:不需判空，要清空的字段 */
		
		reqData.paramsMap = paramsMap;
		
		if(reqData.gdbh=="" && operationType != null && operationType == 2){
			if($("#wo135add_gdbh").val()!=null){
				AlertUtil.showConfirmMessage("是否需要提交?", {callback: function(){
					this_.param.callback(reqData);
				}});
			}else{
				AlertUtil.showConfirmMessage("工单编号为空，是否需要自动生成并提交?", {callback: function(){
					this_.param.callback(reqData);
				}});
			}
			
		}else{
			if($("#wo135add_gdbh").val()!=null){
				this_.param.callback(reqData);
			}else{
				AlertUtil.showConfirmMessage("工单编号为空，是否需要自动生成?", {callback: function(){
					this_.param.callback(reqData);
				}});
			}
		}
		
		
		/*if(){
			AlertUtil.showConfirmMessage("您确定要提交吗？", {callback: function(){
				
				this_.param.callback(reqData);
			}});
		}else if(operationType != null && operationType == 1){
			this_.param.callback(reqData);
		}*/
		
	},
	/**打开来源任务号*/
	openWinLyrwh : function(){
		var this_ = this;
		var chkLy = '';
		if(this_.param.editParam == this_.editParam.add){
			chkLy = $('input[name="wo135add_gdlx"]:checked').val(); //来源:   1RTN（维修项目）、2EO、3NRC、9FLB
		}else{
			chkLy = $('#edit_gdlx_id').val();
		}
		Workorder135NrcWin.show({
	 		modalHeadCN : '来源任务',
			modalHeadENG : 'Originating Task',
			multi : false,
			selected : $("#wo135add_lyrwid").val(),
			/*135工单，类型为1维修项目/2EO，状态（2提交/7生效/10完成）+机构代码*/
			dprtcode:this_.param.dprtcode,//机构代码
			gdlx:[chkLy],//工单类型
			gdzt:[2,7,10],//状态
			fjzch:$("#wo135add_fjzch").val(),//飞机注册号
			callback : function(data) {//回调函数
				if(null != data && null != data.paramsMap && null != data.paramsMap.gdid){
					$("#wo135add_lyrwid").val(data.paramsMap.gdid); //工单ID
					$("#wo135add_lyrwh").val(data.gdbh);//工单编号
					$("#wo135add_gdbt").val(data.gdbt);
					this_.calRwmsCount();
					this_.gdbt=data.gdbt;
				}else{
					$("#wo135add_lyrwid").val("");
					$("#wo135add_lyrwh").val("");
					$("#wo135add_gdbt").val("");
				}
				$("#wo135add_gkbh").val("");//来源工卡号
				$("#wo135add_gkid").val("");//来源工卡id
			}
		});
		
		/*if(chkLy == "3"){
			Workorder135NrcWin.show({
		 		modalHeadCN : '来源任务-NRC',
				modalHeadENG : 'Originating Task',
				multi : false,
				selected : $("#wo135add_lyrwid").val(),
				135工单，类型为1维修项目/2EO，状态（2提交/7生效/10完成）+机构代码
				dprtcode:this_.param.dprtcode,//机构代码
				gdlx:[1,2],//工单类型
				gdzt:[2,7,10],//状态
				callback : function(data) {//回调函数
					if(null != data && null != data.paramsMap && null != data.paramsMap.gdid){
						$("#wo135add_lyrwid").val(data.paramsMap.gdid); //工单ID
						$("#wo135add_lyrwh").val(data.gdbh);//工单编号
					}else{
						$("#wo135add_lyrwid").val("");
						$("#wo135add_lyrwh").val("");
					}
				}
			});
		}else if(chkLy == "2"){
			Workorder135EOWin.show({
		 		modalHeadCN : 'EO',
				modalHeadENG : 'Add',
				dprtcode:this_.param.dprtcode,//机构代码
				callback : function(data) {//回调函数
					
				}
			});
		}else if(chkLy == "1"){
			Workorder135WxxmWin.show({
		 		modalHeadCN : '维修项目',
				modalHeadENG : 'Add',
				dprtcode:this_.param.dprtcode,//机构代码
				callback : function(data) {//回调函数
					
				}
			});
		}*/
	},
	/**打开来源工卡弹框*/
	openLygkWin : function(){
		var this_ = this;
		Workorder135LycardWin.show({
	 		modalHeadCN : '工卡列表',
			modalHeadENG : 'WorkCard List',
			dprtcode:this_.param.dprtcode,//机构代码
			multi : false,
			selected : $("#wo135add_gkid").val(),
			callback : function(data) {//回调函数
				if(null != data){
					this_.selectById(data.id);
				}else{
					this_.selectById(0);//清空
					$("#wo135add_gdbt").val(this_.gdbt);
				}
				$('#workorder135_form').data('bootstrapValidator').updateStatus('wo135add_gdbt','NOT_VALIDATED', null).validateField('wo135add_gdbt');
			}
		});
	},
	selectById : function(id){//通过id获取工卡关联的数据
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/workcard/selectById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(obj){
				if(obj != null){
					this_.reviewCardRefDatas(obj);//回显来源工卡关联的数据
				}else{
					var obj = {};
					obj.id = 0;
					this_.reviewCardRefDatas(obj);//清空
					
				}
			}
		});
	},
	/**打开完成时限弹框*/
	openWinWcsx : function(){
		Workorder135WcsxWin.show({
	 		modalHeadCN : '完成时限',
			modalHeadENG : 'Add',
			dprtcode:userJgdm,//机构代码
			callback : function(data) {//回调函数
				
			}
		});
	},
	/**打开ATA章节号对话框**/
	openChapterWin : function(){
		var this_ = this;
		var zjh = $.trim($("#wo135add_zjhid").val());
		FixChapterModal.show({
			parentWinId : '',
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode,//机构代码
			callback: function(data){//回调函数	
				if(data != null){
					var zjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
					$("#wo135add_zjhid").val(data.zjh);
					$("#wo135add_zjhName").val(zjhName);
				}else{
					$("#wo135add_zjhid").val('');
					$("#wo135add_zjhName").val('');
				}	
			}
		});
	},
	/**根据机型加载数据**/
	loadDatasByJX : function(){
		var this_ = this;
		
		//飞机机型
		this_.jx = $("#wo135add_fjzch").find("option:selected").attr("fjjx"); 
		evaluation_modal.changeParam({
			jx : this_.jx,// 机型
		});
		//重新加载区域
		this_.initZoneInformation(function(){
//			thisct();
		});
		
		//清空飞机站位/接近
		this_.clearFjzwAndJJ();
	},
	/**根据机型加载数据**/
	changeLoadDatasByJX : function(){
		var this_ = this;
		this_.loadDatasByJX();
		//清空之前选择工卡的关联数据
		var obj = {};
		obj.id = 0;
		$("#wo135add_lyrwid").val("");
		$("#wo135add_lyrwh").val("");
		$("#wo135add_gdbt").val("")
	    $("#wo135add_gkbh").val("");//来源工卡号
	    $("#wo135add_gkid").val("");//来源工卡id
	    this_.gdbt = "";
		$("#wo135add_gbid").val("");
		$("#wo135add_gbbh").val("");
		$("#wo135add_jh_ksrq").val("");//计划开始日期
		$("#wo135add_jh_jsrq").val("");//计划结束日期
		this_.reviewCardRefDatas(obj);
	},
	/**初始化区域下拉框**/
	initZoneInformation : function(obj){
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = this_.param.dprtcode;
		searchParam.jx = this_.jx;
		searchParam.lx = 1;
		startWait();
		AjaxUtil.ajax({
			async: false,
			url: basePath+"/basic/zone/zoneList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var zoneOption = '';
				if(data != null && data.length > 0){
					$.each(data,function(i,row){
						var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.sz)+"</option>";
						zoneOption += tempOption;
					});
				}
				this_.zoneOption = zoneOption;
				if(typeof(obj)=="function"){
					obj(); 
				}
		    }
		}); 
	},
	zoneMultiselect : function(){
		var this_ = this;
		//生成多选下拉框区域
		$('#eqydiv',$("#"+this_.id)).empty();
		$('#eqydiv',$("#"+this_.id)).html("<select class='form-control' multiple='multiple' id='eqy'></select>");
		$("#eqy").html(this_.zoneOption);
		
		//生成多选下拉框
		$('#eqy').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',//auto
		    buttonContainer: '<div class="btn-group" style="min-width:100%;" />',
		    numberDisplayed:100,
		    nonSelectedText:'请选择 Choose',
		    allSelectedText:'显示全部 All',
		    includeSelectAllOption: true,
		    selectAllText: '选择全部 Select All',
		    onChange:function(element,checked){
	  	    }
		});
	},
	/** 打开飞机站位弹窗*/
	openStationWin : function(){
		var this_ = this;
		var jx = this_.jx;
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		/*if(this_.isView()){
			return false;
		}*/
		PositionModal.show({
//			selected:this_.positionList,//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode,
			parentid:this_.id,
			jx:jx,
			lx:3,
			callback: function(data){//回调函数
				var str = '';
				this_.positionIdList = [];
				this_.positionList = [];
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						this_.positionIdList.push(row.id);
						this_.positionList.push(row);
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#efjzw", $("#"+this_.id)).val(str);
			}
		});
	},
	/** 打开区域*/
	openZoneWin : function(){
		var this_ = this;
		var jx = this_.jx;;
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		PositionModal.show({
			selected:[],
			dprtcode:this_.param.dprtcode,
			modalHeadCN : '区域列表',
			modalHeadENG : 'Zone List',
			jx:jx,
			lx:1,
			callback: function(data){//回调函数
				var str = '';
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#eqy", $("#"+this_.id)).val(str);
			}
		});
	},
	/**打开接近对话框*/
	openAccessWin : function(){
		var this_ = this;
		var jx = this_.jx;
		if(jx == null || jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		/*if(this_.isView()){
			return false;
		}*/
		open_win_access.show({
			multi : true,
			parentWinId : this_.id,
			jx:jx,
			accessIdList : this_.accessIdList,//回显
			dprtcode:this_.param.dprtcode,
			callback: function(data){//回调函数
				var str = '';
				this_.accessIdList = [];
				this_.accessList = [];
				if(data != null && data.length > 0){
					this_.accessList = data;
					$.each(data,function(index,row){
						this_.accessIdList.push(row.id);
						str += StringUtil.escapeStr(row.gbbh) + " " +StringUtil.escapeStr(row.gbmc) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#jj_e", $("#"+this_.id)).html(str);
			}
		},true);
	},
	/**清空飞机站位/接近*/
	clearFjzwAndJJ : function(){
		var this_ = this;
		this_.accessList = [];
		this_.accessIdList = [];
		this_.positionList = [];
		this_.positionIdList = [];
		$("#jj_e", $("#"+this_.id)).html("");
		$("#efjzw", $("#"+this_.id)).html("");
	},
	/** 打开报告人*/
	openUser : function(obj){
		var this_ = this;
		var userList = [];
		var a = $("#"+obj+"id").val();
		var b = $("#"+obj).val();
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
				$("#"+obj).val(displayName);
				$("#"+obj+"id").val(mjsrid);
			}
		});
	},
	//改变人数时触发
	changeRs : function(obj){
		this.clearNoNumber(obj);
		this.sumTotal();
	},
	//改变小时数触发
	changeXss : function(obj){
		this.clearNoNumTwo(obj);
		this.sumTotal();
	},
	//计算总数
	sumTotal : function(){
		var total = 0;
		var jhgsRs = $("#wo135add_jhgs_rs",$("#"+this.id)).val();
		var jhgsXss = $("#wo135add_jhgs_xss",$("#"+this.id)).val();
		total = jhgsRs*jhgsXss;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#wo135add_bzgs",$("#"+this.id)).html(total);
	},
	//只能输入数字
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
	},
	//只能输入数字和小数,保留两位
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
	},
	/*回显来源工卡关联的数据*/
	reviewCardRefDatas : function(obj){
		var this_ = this;
		
		//工卡编号、工卡ID
		$("#wo135add_gkid").val(obj.id);
		$("#wo135add_gkbh").val(obj.gkh);
		
		//ATA章节号、RII、专业、工作类别、标准工时（人/小时）
		if(null != obj.fixChapter){
			$("#wo135add_zjhid").val(obj.fixChapter.zjh);
			$("#wo135add_zjhName").val(obj.fixChapter.displayName);
		}else{
			$("#wo135add_zjhid").val("");
			$("#wo135add_zjhName").val("");
		}
		
		$("#wo135add_gdbt").val(obj.gzbt);
		this_.gkbt=obj.gzbt;
		this_.calRwmsCount();
		//RII
		if(obj.isBj == "1"){
			$("#wo135add_bjbs").attr("checked","checked");
		}else{
			$("#wo135add_bjbs").removeAttr("checked");
		}
		
		//专业
		$("#wo135add_zy option").each(function (){  
			var text = $(this).val();
			if(text == obj.zy){
				$(this).attr("selected", "selected"); 
				return false;
			}else{
				$("#wo135add_zy option:first").prop("selected", 'selected');
			}
		});
		
		//工作类别
		$("#wo135add_gzlb option").each(function (){  
			var text = $(this).val();
			if(text == obj.gzlx){
				$(this).attr("selected", "selected"); 
				return false;
			}else{
				$("#wo135add_gzlb option:first").prop("selected", 'selected');
			}
		});
		
		//标准工时（人/小时）
		if(null != obj.jhgsRs && null != obj.jhgsXss){
			$("#wo135add_jhgs_rs").val(obj.jhgsRs);
			$("#wo135add_jhgs_xss").val(obj.jhgsXss);
		}else{
			$("#wo135add_jhgs_rs").val('');
			$("#wo135add_jhgs_xss").val('');
		}
		this_.sumTotal();
		
		//初始化工种工时
		work_hour_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.param.parentWinId,
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : this_.param.colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//区域、接近、飞机站位
		this_.loadCoverPlate(obj.coverPlateList);
		
		//初始化参考文件
		ReferenceUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : this_.param.colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化器材清单
		Equipment_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.param.parentWinId,
			ywlx:8,//业务类型：8:工卡
			colOptionhead : this_.param.colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化工具清单
		Tools_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId :this_.param.parentWinId,
			ywlx:8,//业务类型：8:工卡
			colOptionhead : this_.param.colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//工作内容附件：上传按钮
		$("#gznrfjid").val(obj.gznrfjid);
		attachmentsSingleUtil.initAttachment(
				"work_content_attachments_single_edit"//主div的id
				,obj.workContentAttachment//附件
				,'workorder135'//文件夹存放路径
				,this_.param.colOptionhead//true可编辑,false不可编辑
		);
		
		//初始化工作内容
		WorkContentUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : this_.param.colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//一般概述
		$("#wo135add_ybgs").val(obj.gzgs);
		//显示附件列表
		/*var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : true,
			colOptionhead : this_.param.colOptionhead,
			fileType:"workorder135"
		});*/
	},
	/**
	 * 区域/接近/飞机站位回显
	 */
	loadCoverPlate : function(coverPlateList){
		var this_ = this;
		var str = '';
		var stationStr = '';
		var qyArr = [];
		var qyStr = '';
		this_.accessIdList = [];
		this_.accessList = [];
		this_.positionIdList = [];
		this_.positionList = [];
		if(coverPlateList != null && coverPlateList.length > 0){
			$.each(coverPlateList,function(index,row){
				if(row.lx == 1){
					qyArr.push(row.gbid);
					qyStr += StringUtil.escapeStr(row.gbh) + ",";
				}
				if(row.lx == 2){
					this_.accessIdList.push(row.gbid);
					var access = {};
					access.id = row.gbid;
					access.gbbh = row.gbh;
					this_.accessList.push(access);
					str += StringUtil.escapeStr(row.gbh)+(row.coverPlateInformation==null?"":" "+StringUtil.escapeStr(row.coverPlateInformation.gbmc))+",";
				}
				if(row.lx == 3){
					this_.positionList.push({
						id : row.gbid,
						sz : row.gbh
					});
					this_.positionIdList.push(row.gbid);
					stationStr += formatUndefine(row.gbh) + ",";
				}
			});
		}
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		if(qyStr != ''){
			qyStr = qyStr.substring(0,qyStr.length - 1);
		}
		if(stationStr != ''){
			stationStr = stationStr.substring(0,stationStr.length - 1);
		}
		
//		this_.initZoneInformation(function(){
//			this_.zoneMultiselect();
//			if(qyArr.length > 0){
//				$('#eqy',$("#"+this_.id)).multiselect('select', qyArr);
//			}
//		});
		$("#eqy", $("#"+this_.id)).val(qyStr);
		$("#jj_e", $("#"+this_.id)).html(str);
		$("#efjzw", $("#"+this_.id)).val(stationStr);
	},
	// 计算任务描述长度
	calRwmsCount:function(){
		var rwms = $("#wo135add_gdbt",$("#"+this.id)).val();
		var count = rwms.replace(/[^\x00-\xff]/g,"012").length;
		$("#rwms_count",$("#"+this.id)).text(count);
		
		if(count > 4000){
			$("#rwms_des",$("#"+this.id)).addClass("text-danger");
		}else{
			$("#rwms_des",$("#"+this.id)).removeClass("text-danger");
		}
		return count;
	},
	/**打开135工包对话框**/
	openWin135Package : function(){
		var this_ = this;
		Workpackage145Win.show({
	 		modalHeadCN : '135工包',
			modalHeadENG : '135 package',
			gdlx:"135",
			fjzch:$("#wo135add_fjzch").val(),
			multi : false,
			selected : $("#wo135add_gbid").val(),//工包ID
			dprtcode:this_.param.dprtcode,//机构代码
			gbzt:[2,7],//状态
			callback : function(data) {//回调函数
				if(null != data){
					$("#wo135add_gbid").val(data.id); //工包ID
					$("#wo135add_gbbh").val(data.gbbh);//工包编号
					$("#wo135add_jh_ksrq").val(StringUtil.escapeStr(data.jhKsrq).substring(0, 10));//计划开始日期
					$("#wo135add_jh_jsrq").val(StringUtil.escapeStr(data.jhJsrq).substring(0, 10));//计划结束日期
				}else{
					$("#wo135add_gbid").val("");
					$("#wo135add_gbbh").val("");
					$("#wo135add_jh_ksrq").val("");//计划开始日期
					$("#wo135add_jh_jsrq").val("");//计划结束日期
				}
			}
		});
	},
	getBL : function(id,bllx){
		var this_=this;
		var obj = {};
		var url = basePath + "/produce/reservation/fault/getByFailureKeepId";
		if(bllx ==  2){
			url = basePath + "/produce/reservation/defect/getByDefectKeepId";
		}
		obj.id = id;
	   	AjaxUtil.ajax({
	 		url:url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		success:function(data){
	 			if(data!=null){
	 				$("#wo135add_fjzch").val(data.fjzch);
	 				$("#wo135add_fjzch").attr("disabled",true);
				};
	 		}
		});
	}
};