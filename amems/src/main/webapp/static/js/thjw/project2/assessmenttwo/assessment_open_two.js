Assessment_Open_Modal = {
	id : "Assessment_Open_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	param: {
		viewObj:{},
		callback:function(){},
	},
	//显示弹出窗
	show: function(param){
		//$("#glpgdid").html("<option value=''>暂无关联技术评估单No data</option>");
		$("#biaoshi").css("visibility","hidden");//隐藏标识
		ReferenceUtil.init({
			djid:'',//关联单据id
			ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : true,
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});//初始化参考文件
		if(param==1){//新增
			Assessment_Add_Modal.open();
		}else if(param==2){//修改
			Assessment_Update_Modal.open();
		}else if(param==3){//审核
			Assessment_Audit_Modal.open();
		}else if(param==4){//批准
			Assessment_Approval_Modal.open();
		}else if(param==5){//改版
			Assessment_Revision_Modal.open();
			
		}
	},
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#assessmentForm').bootstrapValidator({
		       message: '数据不合法',
		       excluded:[":disabled"],  
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	  jx: {
		            validators: {
		            	notEmpty: {message: '机型不能为空'}
		            }
			      }, 
			      zjhms: {
		            validators: {
		            	notEmpty: {message: 'ATA章节号不能为空'}
		           }
				  }, 
				  pgdzt: {
		            validators: {
		            	notEmpty: {message: '标题不能为空'}
		           }
				  }, 
				  zjh: {
					  trigger:"change", //关键配置  
					  validators: {
						  notEmpty: {message: 'ATA章节号不存在'}
					  }
				  }, 
				  pgdh: {
					  validators: {
						  regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '技术评估单编号不能输入中文!' 	
		                  }
					  }
				  }
		       }
		   });
	},
	//历史版本
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#"+this_.id,function(obj){
			return this_.getHistoryBbList(obj);
		});
	},
	//历史版本
	initWebuiFindPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2',"body",function(obj){
			return this_.getHistoryBbList(obj);
		});
	},
	getHistoryBbList : function(obj){//获取历史版本列表
		return assessment_history_alert_Modal.getHistoryBbList($("#id").val(),$("#dprtcode").val());
	},
	//查询上一版本的技术评估单
	findAssessmentView : function(){
		var id=$("#oldbbid").val();
		window.open(basePath+"/project2/assessment/view?id="+id);
	},
	//查询关联的技术评估单
	findGlAssessment : function(){
		var id=$("#glpgdid").val();
		window.open(basePath+"/project2/assessment/view?id="+id);
	},
	//按钮权限
	button: function(param){
		if(param==1){//新增
			 $("#shtg").hide();
			 $("#sptg").hide();
			 $("#shbh").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
			 $("#xiugaipgr").hide();
		}else if(param==2){//修改
			 $("#shtg").hide();
			 $("#sptg").hide();
			 $("#shbh").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
			 $("#xiugaipgr").hide();
		}else if(param==3){//审核
			 $("#shtg").show();
			 $("#sptg").hide();
			 $("#shbh").show();
			 $("#spbh").hide();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#xiugaipgr").hide();
		}else if(param==4){//批准
			 $("#shtg").hide();
			 $("#sptg").show();
			 $("#shbh").hide();
			 $("#spbh").show();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#xiugaipgr").hide();
		}else if(param==5){//改版
			 $("#shtg").hide();
			 $("#sptg").hide();
			 $("#shbh").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
			 $("#xiugaipgr").hide();
		}else if(param==6){
			 $("#shtg").hide();
			 $("#sptg").hide();
			 $("#shbh").hide();
			 $("#spbh").hide();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#xiugaipgr").show();
		}
	},
	//初始化适航性资料弹窗
	openTech: function(param){
			var dprtcode = $.trim($("#dprtcode").val());
			var shxzlid = $.trim($("#shxzlid").val());
			TechnicalfileModal.show({
				selected:shxzlid,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				id:null,
				parentid:'Assessment_Open_Modal',
				callback: function(data){//回调函数
					
					if(data!=null){
						$("#shxzlid").val(formatUndefine(data.id));
						$("#shxzljswjbh").val(formatUndefine(data.jswjbh));
						$("#shxzlbb").html(formatUndefine(data.bb));
						$("#shxzlxzah").val(formatUndefine(data.xzah));
						$("#shxzljswjly").val(formatUndefine(data.jswjly));
						$("#shxzljswjlx").val(formatUndefine(data.jswjlx));
						$("#shxzlzdsj").val(formatUndefine(data.zdsj).substring(0,10));
						$("#shxzljswjzt").val(formatUndefine(data.jswjzt));
						$("#shxzlbfrq").val(formatUndefine(data.bfrq).substring(0,10));
						$("#shxzlsxrq").val(formatUndefine(data.sxrq).substring(0,10));
						$("#shxzldqrq").val(formatUndefine(data.dqrq).substring(0,10));
						$("#gljswjid").val(formatUndefine(data.gljswjid));
						Assessment_Open_Modal.initGlpgdOption();//初始化关联技术评估单
					}
				}
			});
	},
	//初始化关联技术评估单（关联适航性资料id为条件）
	updateGlpgdOption: function(){
		open_win_evaluation_modal.show({
			dprtcode :$("#pgd_dprtcode").val(),
			/*parentid:this_.param.parentId,
			userId:this_.param.userId,
			zlxl:this_.param.zlxl,
			jx:this_.param.jx,
			existsIdList:this_.existsIdList,*/
			selected : $("#glpgdid").val(),
			multi:false,
			//dprtcode:this_.param.dprtcode,
			callback : function(data) {// 回调函数
				if(data != null){
					 $("#glbb").html("");
					 $("#glshxzl").val("");		
					$("#glpgdid").val(data.id);
					 $("#glpgd").val(data.pgdh);
					 $("#glbb").html(data.bb);
						 $("#glpgsj").val(data.pgsj);
						 $("#glpgdzt").val(data.pgdzt);
						 if(data.airworthiness!=null){
							 $("#glshxzl").val(""+data.airworthiness.jswjbh+" R"+data.airworthiness.bb+" "+StringUtil.escapeStr(data.airworthiness.jswjzt));
						 }
				}
				
				//this_.appendHtml(data);
			}
		});
		
	},
	//初始化关联技术评估单（关联适航性资料id为条件）
	initGlpgdOption: function(){
		
		/*open_win_evaluation_modal.show({
			parentid:this_.param.parentId,
			userId:this_.param.userId,
			zlxl:this_.param.zlxl,
			jx:this_.param.jx,
			existsIdList:this_.existsIdList,
			selected : $("#glpgdid").val(),
			multi:false,
			//dprtcode:this_.param.dprtcode,
			callback : function(data) {// 回调函数
				if(data != null){
					
					$("#glpgdid").val(data.id);
					 $("#glpgd").val(data.pgdh);
					 $("#glbb").html(data.bb);
						 $("#glpgsj").val(data.pgsj);
						 $("#glpgdzt").val(data.pgdzt);
						 if(data.airworthiness!=null){
							 $("#glshxzl").val(""+data.airworthiness.jswjbh+" R"+data.airworthiness.bb+" "+StringUtil.escapeStr(data.airworthiness.jswjzt));
						 }
				}
				
				//this_.appendHtml(data);
			}
		});
		*/
/*		open_win_evaluation_modal.show({
			parentid:this_.param.parentId,
			userId:this_.param.userId,
			zlxl:this_.param.zlxl,
			jx:this_.param.jx,
			existsIdList:this_.existsIdList,
			multi:true,
			//dprtcode:this_.param.dprtcode,
			callback : function(data) {// 回调函数
				console.info(data);
				this_.appendHtml(data);
			}
		});*/
		
		
		
	/*	$("#glpgdid").html("");
		var obj = {};
		obj.jswjid = formatUndefine($("#gljswjid").val());//关联技术文件id
		obj.jx = formatUndefine($("#Assessment_Open_Modal_jx").val());//机型
		obj.dprtcode = $("#dprtcode").val();//组织机构
		 startWait($("#Assessment_Open_Modal"));
//		 if(formatUndefine($("#gljswjid").val())!=''){
	   	 AjaxUtil.ajax({
		 		url:basePath + "/project2/assessment/getgljspgdList",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#Assessment_Open_Modal"),
		 		success:function(data){
		 			console.info(data);
		 			finishWait($("#Assessment_Open_Modal"));
		 			if(data.length > 0){
						for(var i = 0; i < data.length; i++){
							var pgdh="";
							if(StringUtil.escapeStr(data[i].pgdh)==""){
								pgdh="";
							}else{
								pgdh=StringUtil.escapeStr(data[i].pgdh);
							}
							$("#glpgdid").append("<option value='"+StringUtil.escapeStr(data[i].id)+"'>"+pgdh+"</option>");
						}
						 Assessment_Open_Modal.changeGljspgd();
					}else{
						$("#glpgdid").append("<option value=''>暂无关联技术评估单No data</option>");
					}
		 		}
		  });*/
//		 }else{
//			 $("#glpgdid").append("<option value=''>暂无关联技术评估单No data</option>");
//			 
//		 }
		
	},
	//修改关联技术评估单
	changeGljspgd: function(jswjid,glpgdidold){
	
		var obj = {};
		var glpgdid = $("#glpgdid").val();
		if(jswjid != null){
			obj.jswjid = jswjid;
		}
		obj.jx = $("#Assessment_Open_Modal_jx").val();
		obj.dprtcode = $("#dprtcode").val();//组织机构
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
		 		url:basePath + "/project2/assessment/getgljspgdListoldwu",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#Assessment_Open_Modal"),
		 		success:function(obj){
		 			finishWait($("#Assessment_Open_Modal"));
		 			if(obj != null){
		 				if(jswjid != null){
		 					$.each(obj,function(index,data){
		 						if(glpgdidold!=null){
		 							if(data.id==glpgdidold){
		 								$("#glpgdid").val(data.id);
		 								$("#glpgd").val(data.pgdh);
		 								$("#glbb").html(data.bb);
		 								$("#glpgsj").val(data.pgsj);
		 								$("#glpgdzt").val(data.pgdzt);
		 								if(data.airworthiness!=null){
		 									$("#glshxzl").val(""+data.airworthiness.jswjbh+" R"+data.airworthiness.bb+" "+StringUtil.escapeStr(data.airworthiness.jswjzt));
		 								}
		 							}
		 							
		 						}else{
		 							$("#glpgdid").val(obj[0].id);
		 							$("#glpgd").val(obj[0].pgdh);
		 							$("#glbb").html(obj[0].bb);
		 							$("#glpgsj").val(obj[0].pgsj);
		 							$("#glpgdzt").val(obj[0].pgdzt);
		 							if(obj[0].airworthiness!=null){
		 								$("#glshxzl").val(""+obj[0].airworthiness.jswjbh+" R"+obj[0].airworthiness.bb+" "+StringUtil.escapeStr(obj[0].airworthiness.jswjzt));
		 							}
		 						}
		 					});
		 					
		 				}else{
		 					$.each(obj,function(index,data){
		 						if(data.id==glpgdid){
		 							$("#glpgdid").val(data.id);
		 							$("#glpgd").val(data.pgdh);
		 							$("#glbb").html(data.bb);
		 							$("#glpgsj").val(data.pgsj);
		 							$("#glpgdzt").val(data.pgdzt);
		 							if(data.airworthiness!=null){
		 								$("#glshxzl").val(""+data.airworthiness.jswjbh+" R"+data.airworthiness.bb+" "+StringUtil.escapeStr(data.airworthiness.jswjzt));
		 							}
		 						}
		 					});
		 				}
					}
		 		}
		  });
	},
	//初始化机型
	initFjjxOption: function(){
		var this_ = this;
		$("#"+this_.id+"_jx").empty();
		var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : $("#dprtcode").val()});
		if(typeList.length > 0){
			for(var i = 0; i < typeList.length; i++){
				$("#"+this_.id+"_jx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
			}
		}else{
			$("#"+this_.id+"_jx").append("<option value=''>暂无飞机机型No data</option>");
		}
	},
	//初始化章节号
	initFixChapter: function(){
		var zjh = $.trim($("#zjh").val());
		var dprtcode = $("#dprtcode").val();
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			parentWinId : "Assessment_Open_Modal",
			callback: function(data){//回调函数
				if(data != null){
					var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
					$("#zjh").val(data.zjh);
					$("#zjhms").val(wczjhName);
					
					$("input[name='zjh']").val(data.zjh).change();  
				}else{
					$("#zjh").val('');
					$("#zjhms").val('');
				}
				
				  $('#assessmentForm').data('bootstrapValidator')  
	 		      .updateStatus('zjhms', 'NOT_VALIDATED',null)  
	 		      .validateField('zjhms');  
			
			}
		});
	},
	//初始化完成类型
	initwclx: function(){
		$("#wclx").empty();
		DicAndEnumUtil.registerDic('21','wclx',$("#dprtcode").val());
	},
	//初始化紧急度
	inijjcd: function(){
		$("#jjcd").empty();
		DicAndEnumUtil.registerDic('79','jjcd',$("#dprtcode").val());
	},
	//改变本单位适用性
	onchangesyx : function(){
		var syx=$("input:radio[name='syx']:checked").val();//本单位适用性
		if(syx==1){
			$("#fsyyy").css("visibility","hidden");//隐藏不适用原因
			$("#orderDiv").show();//显示下达指令
			$("#sylbDiv").show(); //显示本单位适用类别
			$("#syfwDiv").show(); //显示本单位适用范围
			$("#zlsjDiv").show(); //显示指令时间
			$("#yjbfzlDiv").show(); //显示预计颁发指令
			$("#repeatid").show(); //
		}else{
			$("#fsyyy").css("visibility","visible");//显示不适用原因
			$("#orderDiv").hide(); //隐藏下达指令
			$("#sylbDiv").hide();  //隐藏本单位适用类别
			$("#syfwDiv").hide();  //隐藏本单位适用范围
			$("#zlsjDiv").hide();  //隐藏指令时间
			$("#yjbfzlDiv").hide();  //隐藏预计颁发指令
			$("#repeatid").hide(); //
		}
	},
	//改变结论附件
	onchangeAttachment : function(obj){
		var checked = $(obj).is(":checked");
		if(checked){
			if(!this.uploadObjjl.wbwjm){
				$("#fileuploaderSingle").show();
			}else{
				$("#scwjWbwjm").show();
			}
		}else{
				$("#fileuploaderSingle").hide();
				$("#scwjWbwjm").hide();
		}
		
	},
	//改变下达指令
	onchangeOrder: function(param){
		var order=$("input:checkbox[name='"+param+"']:checked").val();
		if(order=="on"){
			
			$("#"+param+"").css("visibility","visible");
			$("#"+param+"r").css("visibility","visible");
		}else{
			$("#"+param+"").css("visibility","hidden");
			$("#"+param+"r").css("visibility","hidden");
		}
	},
	//初始化下达指令
	initOrder: function(){
		$('#orderDiv input:checkbox').each(function() {
			 var param = $(this).attr("name");
			 var order=$("input:checkbox[name='"+param+"']:checked").val();
			 if(order=="on"){
				
				$("#"+param+"").css("visibility","visible");
				$("#"+param+"r").css("visibility","visible");
			 }else{
				$("#"+param+"id").val("");
				$("#"+param).val();
				$("#"+param+"").css("visibility","hidden");
				$("#"+param+"r").css("visibility","hidden");
			 }
		});
	},
	//初始化涉及部门
	openzrdw: function(){
		var dprtname = $("#sjbmms").val();
		var dprtcode = $("#sjbmid").val();
		departmentModal.show({
			dprtnameList : dprtname,// 部门名称
			dprtcodeList : dprtcode,// 部门id
			type : true,// 勾选类型true为checkbox
			parentid : "Assessment_Open_Modal",
			dprtcode : $("#dprtcode").val(),// 默认登录人当前机构代码
			callback : function(data) {// 回调函数
				$("#sjbmms", $("#Assessment_Open_Modal")).html(data.dprtname);
				$("#sjbmid").val(data.dprtcode);
			}
		});
	},
	//初始化选择人员
	openUserWin: function(obj){
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
			userList = null;
		}
		
		Personel_Tree_Multi_Modal.show({
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:$("#dprtcode").val(),//
			multi:false,
			parentWinId : "Assessment_Open_Modal",
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
				/*$('#projectForm').data('bootstrapValidator')  
	 		      .updateStatus(""+obj+"", 'NOT_VALIDATED',null)  
	 		      .validateField(""+obj+"");*/ 
			}
		});
		
		
		
		/*var userId = $("#"+e+"id").val();
		var userDisplayName = $("#"+e).val();
		var userList = [];
		if(userId){
			var user = {
					id  : userId,
					displayName : userDisplayName
			};
			userList.push(user);
		}
		User_Tree_Multi_Modal.show({
			clearUser:false,
			checkUserList:userList,//原值，在弹窗中回显
			dprtcode:$("#dprtcode").val(),//
			parentWinId : "Assessment_Open_Modal",
			callback: function(data){//回调函数
				if(data != null && data != ""){
					$("#"+e+"id").val(data[0].id);
					$("#"+e).val(data[0].displayName);
				}
			}
		});*/
	},
	//跳转到适航性资料查询界面
	findAirworthiness: function(){
		var id =$("#shxzlid").val();
		window.open(basePath+"/project2/airworthiness/view?id="+id);
	},
	//跳转到评估单查询界面
	findAssessment: function(){
		var id =$("#oldbbid").val();
		window.open(basePath+"/project2/assessment/view?id="+id);
	},
	//源文件下载
	onclikfile : function(){
		var id=$("#ywjid").val();
		this.downfile(id);
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
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
		
		/*//终止条件标识
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
		}*/
		
		
		
		applicable_settings.showHidePlaneMinus();
	},
	selectsyx: function(){
			if($('input[name="sylb_public"]:checked').val() == '1'){
				applicable_settings.choosePlane();
			}else{
				applicable_settings.choosePN();
			}
	},	
	loadAllDatas : function(dprtcode){
		var this_ = this;
		this_.data = [];
		this_.param.fjjx = $("#Assessment_Open_Modal_jx").val();//机型
		this_.param.dprtcode=dprtcode;
		this_.param.existList = []; 
		this_.sendRequest();
		return this_.data;
	},
	sendRequest:function(){
		var this_=this;
		var param ={};
		param.fjjx=this_.param.fjjx;
		param.dprtcode=this_.param.dprtcode;
		AjaxUtil.ajax({
			url : basePath + "/aircraftinfo/selectByFjjx",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success:function(data){
				this_.data = data;
		    }
		});
	},
	//删除结论附件
	removeFile : function (id) {
		var this_=this;
		 AlertUtil.showConfirmMessage("确定要删除附件吗？", {callback: function(){
			if(id){
				this_.deleteUploadId= id;
				this_.uploadObjjl.wbwjm= "";
			}
			this_.uploadObjjl.biaoshi="delete";
			$("#scwjWbwjm").hide();
			$("#fileuploaderSingle").show();
			$("#attachmentcheckbox").attr("disabled",false);//结论附件可编辑
		 }});
	},
	//初始化結論附件
	uploader: function(){
		var uploadObjjl =this.uploadObjjl;
		var uploadObjSingle =this.uploadObjSingle;
		//上传
		 uploadObjSingle = $("#fileuploaderSingle").uploadFile({
			url:basePath+"/common/ajaxUploadFile",
			multiple:true,
			dragDrop:false,
			showStatusAfterSuccess: false,
			showDelete: false,
			formData: {
				"fileType":"order"
				,"wbwjmSingle" : function(){}
				},//参考FileType枚举（技术文件类型）	
			fileName: "myfile",
			returnType: "json",
			removeAfterUpload:true,
			showStatusAfterSuccess : false,
			showStatusAfterError: false,
			/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/			
			uploadStr:"<i class='fa fa-upload'></i>",
			/*ajax-file-upload_ext*/
			uploadButtonClass: "btn btn-default btn-uploadnew",
			onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
			{
				uploadObjjl.yswjm = data.attachments[0].yswjm;
				uploadObjjl.wbwjm = data.attachments[0].wbwjm;
				uploadObjjl.nbwjm = data.attachments[0].nbwjm;
				uploadObjjl.cflj  = data.attachments[0].cflj;
				uploadObjjl.wjdx  = data.attachments[0].wjdx;
				uploadObjjl.hzm   = data.attachments[0].hzm;
				uploadObjjl.biaoshi="edit";
				//alert(JSON.stringify(data.attachments[0]));
				//$("#wbwjm").val(data.attachments[0].wbwjm);
				$("#scwjWbwjm").html("&nbsp;&nbsp;"+data.attachments[0].wbwjm+"."+data.attachments[0].hzm+"&nbsp;<i class='icon-remove-sign cursor-pointer color-blue cursor-pointer' style='font-size:15px' title='删除 Delete' onclick='Assessment_Open_Modal.removeFile()'></i>");
				$("#fileuploaderSingle").hide();
				$("#scwjWbwjm").show();
				$("#attachmentcheckbox").attr("disabled",true);//结论附件不可编辑
			},
		
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjmSingle').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjmSingle').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObjSingle.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}
			
		});
	}
	
};

$(document).ready(function(){
	 $(window).resize(function(){
	    	modalBodyHeight("AddalertModal");
	    	modalBodyHeight("myModalSecond");
	    });
	uploader();
	
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:'',
		fileType:"card"
	});//显示附件
	
});



function modalBodyHeight(id){
	//window高度
	var windowHeight = $(window).height();
    //modal-footer的高度
	var modalFooterHeight=$("#"+id+" .modal-footer").outerHeight()||0;
	//modal-header 的高度
    var modalHeaderHeight=$("#"+id+" .modal-header").outerHeight()||0;
	//modal-dialog的margin-top值
    var modalDialogMargin=parseInt($("#"+id+" .modal-dialog").css("margin-top"))||0;
    //modal-body 的设置
	var modalBodyHeight=windowHeight-modalFooterHeight-modalHeaderHeight-modalDialogMargin*2-8;
    $("#"+id+" .modal-body").attr('style', 'max-height:' + modalBodyHeight+ 'px !important;overflow: auto;margin-top:0px;');
}
//上传

function uploader(){
		uploadObj =$("#fileuploader").uploadFile({
		
		url:basePath+"/common/ajaxUploadFile",
		multiple:false,
		dragDrop:false,
		//maxFileCount:1,
		formData: {"fileType":"technicalfile","wbwjm" : function(){return $('#sm').val();}},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
		uploadStr:"上传",
		uploadButtonClass: "ajax-file-upload_ext",
//		statusBarWidth:150,
//		dragdropWidth:150,
		showStatusAfterSuccess : false,
		showStatusAfterError: false,
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	    {
			var sm=data.attachments[0].wbwjm;
			sm=sm.substring(0,sm.lastIndexOf ('.'));
			$("#sm").val(sm);
			$("#uploadFile").hide();
	    }
		
	});
}

 function openHistoryWin(){
	$(".historyBb-dialog").css("display","block");
    $(document).click(function(e){
	    if(	$(".historyBb-dialog").css("display")=="block"){
		$(".historyBb-dialog").css("display","none");
		}
	}); 
	
	work_card_history_alert_Modal.show({
		id:'',
		callback : function(data) {// 回调函数
			
		}
	});
}
 $('.date-picker').datepicker({
		format:'yyyy-mm-dd',
		autoclose : true
	}).next().on("click", function() {
		$(this).prev().focus();
	});

 
 $("#assessmentForm input[name='zjhms']").keyup(function(){
	  $('#assessmentForm').data('bootstrapValidator')  
	      .updateStatus('zjh', 'NOT_VALIDATED',null)  
	      .validateField('zjh');  
});


function initAutoComplete(){
   $("#assessmentForm input[name='zjhms']").typeahead({
		displayText : function(item){
			return StringUtil.escapeStr(item.zjh)+" "+StringUtil.escapeStr(item.ywms);
		},
	    source: function (query, process) {
	    	AjaxUtil.ajax({
				url: basePath+"/project/fixchapter/limitTen",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					zjh : query.toUpperCase(),
					dprtcode : $("#dprtcode").val()
				}),
				success:function(data){
					if(data.length>0){
//						$("#zjh").val(StringUtil.escapeStr(data[0].zjh));
//						$("#zjhms").val(StringUtil.escapeStr(data[0].zjh)+" "+StringUtil.escapeStr(data[0].ywms));
					}
					process(data);
			    }
			}); 
	    },

       highlighter: function (item) {	
       	$("#zjh").val("");
           return item;
       },

       updater: function (item) {
       	$("#zjh").val(StringUtil.escapeStr(item.zjh));
       	
       	$("input[name='zjh']").val(item.zjh).change();  
       	  $('#assessmentForm').data('bootstrapValidator')  
		      .updateStatus('zjh', 'NOT_VALIDATED',null)  
		      .validateField('zjh');  
           return item;
       }
	});
}

//机型切换
$("#Assessment_Open_Modal_jx").change(function(){
	applicable_settings.clearData(); //清空适用性列表
	$("#qbsyInput").removeAttr("checked");//取消全部适用勾选
	this_.initEOZoneInformation($("#Assessment_Open_Modal_jx").val(), this_.param.dprtcode);//初始化多选区域下拉框
	evaluation_modal.changeParam({
		parentId : "EOAddModal",// 第一层模态框id
		isShowed : colOptionhead,// 是否显示选择评估单的操作列
		zlxl : 6,// 指令类型
		dprtcode : this_.param.dprtcode,// 组织机构
		jx : $("#Assessment_Open_Modal_jx").val()//机型
	});
});