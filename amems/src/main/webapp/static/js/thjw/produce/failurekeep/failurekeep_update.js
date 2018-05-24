FailureKeep_Update_Modal = {
	id : "FailureKeep_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("修改故障保留单");
		 $("#modalEname").html("Update Defect Deferred Log");
		 FailureKeep_Open_Modal.EmptiedData();  //清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".bldh").attr("disabled",true);//保留单号不可编辑
		$(".div-hide").show();   			        			//隐藏标识
		$("#addupdatebs").val("0");   			        		//新增标识为0为修改
		FailureKeep_Open_Modal.button(1);						//初始化按钮权限
		FailureKeep_Open_Modal.validation(); 					//初始化验证
		FailureKeep_Open_Modal.onchangeblly(); 					//初始化来源类型
		
		FailureKeep_Open_Modal.Equipment_list_edit(param,true); //初始化器材清单
		FailureKeep_Open_Modal.Tools_list_edit(param,true); 	//初始化工具设备
		FailureKeep_Open_Modal.getAttachments(param,true,true); //加载附件
		this.initDate(param);//加载故障保留数据
	},
	/**
	 * 加载故障保留数据
	 */
	initDate : function(param){
		var falg=true;
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#FailureKeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/produce/reservation/fault/getByFailureKeepId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#FailureKeep_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#FailureKeep_Open_Modal"));
	 			if(data!=null){
	 				if(data.zt==2){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					falg=false;
	 					return false;
	 				}
	 			  	if(falg){
	 			   		$("#"+this_.id+"").modal("show");//显示窗口
	 			   	}
	 				this_.setDate(data);// 加载故障保留数据
				};
	 		}
		});
	},
	/**
	 * 加载故障保留数据
	 */
	setDate : function(data){
		
		var this_=this;
		$("#id").val(data.id);
		FailureKeep_Open_Modal.initFjzchOption(data.dprtcode); 				//初始化飞机注册号
		$("#bs145").val(data.bs145);				//145标识	
		$("#bldh").val(data.bldh);					//故障保留单号
		$("#"+this_.id+"_fjzch").val(data.fjzch);	//飞机注册号
		FailureKeep_Open_Modal.onchangeFjzch("#"+this_.id+"_fjzch");  //改变机型
		$("#djzt").val(data.zt);  					//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('failureKeepStatusEnum',data.zt));  //当前单据状态翻译
		$("#zjh").val(data.zjh);  					//当前单据状态
		$("#zjhms").val(data.fixChapter.zjh);       //章节号描述
		$("#hz").val(data.hz);  					//航站
		$("input:radio[name='blly'][value='"+data.blly+"']").attr("checked",true); //来源类型	
		if(data.blly!=9){//飞行记录本-nrc
			$("#bllyid").val(data.bllyid); 			//保留来源id
			$("#blly").val(data.paramsMap?data.paramsMap.bllyName:'');//来源编号
		}
		var blly=$("input:radio[name='blly']:checked").val();
		if(blly==9){ //来源编号
			$("#lybhdiv").css("visibility","hidden");//隐藏
		}else {
			$("#lybhdiv").css("visibility","visible");//显示
		}     
		$("input:radio[name='bllx'][value='"+data.bllx+"']").attr("checked",true); //保留类型	
		if(data.bllx==9){//当保留类型为9时
			$("#bllxsm").val(data.bllxsm); 			//保留类型说明
		}
		FailureKeep_Open_Modal.onchangebllx();		//改变来保留类型
		$("#scSqrid").val(data.scSqrid);  	//申请人id
		$("#scSqrq").val(formatUndefine(data.scSqrq).substring(0,10));  			//申请人日期
		$("#scSqrq").datepicker("update");
		$("#scSqr").val(data.paramsMap.scSqr);  			//申请人描述
		$("#scSqrbmid").val(data.scSqrbmid);  		//申请人部门
		$("#scBlqx").val(formatUndefine(data.scBlqx).substring(0,10));  			//保留期限
		$("#scBlqx").datepicker("update");
		$("#scSqrzzh").val(data.scSqrzzh);  		//申请人执照号
		$("#sxgsRs").val(data.sxgsRs);  			//所需工时-人数
		$("#sxgsXs").val(data.sxgsXs);  			//所需工时-小时
		FailureKeep_Open_Modal.sumTotal();			//计算总数
		$("#blyj").val(data.blyj);  				//保留依据
		$("#gzms").val(data.gzms);  				//故障描述
		$("#blyy").val(data.blyy);  				//保留原因
		$("#lscs").val(data.lscs);  				//临时措施
		
		if(data.mbs==1){//执行M程序标识：0否、1是
			$("#mbs").attr("checked", true);
			$("#msm").val(data.msm);  				//执行M程序说明
			FailureKeep_Open_Modal.onchangembs("m");
		}
		if(data.obs==1){//执行O程序标识：0否、1是
			$("#obs").attr("checked", true);
			FailureKeep_Open_Modal.onchangembs("o");
			$("#osm").val(data.osm);  			//执行O程序说明
		}
		if(data.yxxzbs==1){//运行限制标识：0否、1是
			$("#yxxzbs").attr("checked", true);
			FailureKeep_Open_Modal.onchangembs("yxxz");
			$("#yxxzsm").val(data.yxxzsm);  		//运行限制说明
		}
		
		approval_process_info.show({    
		 	status:data.zt,			  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
		 	prepared:data.whr.id,	  	  //编写人
			preparedDate:data.whsj,	  			  //编写日期
			approvedOpinion:data.scPzyj, 		  //批准意见
			approvedBy:data.paramsMap.scPzr,      		 		  //批准人
			approvedDate:formatUndefine(data.scPzrq).substring(0,10),    		  //批准日期
		});
		
		$("#gzblBjh").val(data.bjh); //部件号
		$("#gzblXlh").val(data.xlh); //序列号
		if(data.fxsj != null && data.fxsj != ""){
			$("#fhInput").val(TimeUtil.convertToHour(data.fxsj, TimeUtil.Separator.COLON)); //飞行时间
		}
		$("#fcInput").val(data.fxxh); //飞行循环
		
		if(data.blfxsj != null && data.blfxsj != ""){
			$("#blfhInput").val(TimeUtil.convertToHour(data.blfxsj, TimeUtil.Separator.COLON)); //保留飞行时间
		}
		$("#blfcInput").val(data.blfxxh); //保留飞行循环
		
		//勾选代码
		if(data.yxfwdmList != null){
			for (var i = 0; i < data.yxfwdmList.length; i++) {
				$("input[type='checkbox'][name='yxfwdm'][value='"+ data.yxfwdmList[i]+"']").attr("checked",true); //受影响服务代码
			}
		}
		if(data.sjbmdmList != null){
			for (var i = 0; i < data.yxfwdmList.length; i++) {
				$("input[type='checkbox'][name='sjbmdm'][value='"+ data.sjbmdmList[i]+"']").attr("checked",true); //涉及部门代码
			}
		}
		if(data.yxxzdmList != null){
			for (var i = 0; i < data.yxxzdmList.length; i++) {
				$("input[type='checkbox'][name='yxxzdm'][value='"+ data.yxxzdmList[i]+"']").attr("checked",true); //运行限制代码
			}
		}
		if(data.blyydmList != null){
			for (var i = 0; i < data.blyydmList.length; i++) {
				$("input[type='checkbox'][name='blyydm'][value='"+ data.blyydmList[i]+"']").attr("checked",true); //保留原因代码
			}
		}
	}
};