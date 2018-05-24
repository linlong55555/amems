Assessment_Add_Modal = {
	id : "Assessment_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		$(".noteditable").attr("disabled",false);				//标签不可编辑
		$("#pgdh").attr("disabled",false);			  			//评估单号不可编辑
		$("#Assessment_Open_Modal_jx").attr("disabled",false);	//机型不可编辑
		$("#lysm").attr("disabled",false);  					//来源说明不可编辑
		$(".required").show();   			    				//隐藏必填标识
		$(".colse").addClass("readonly-style");  			//去掉文本框为白的的样式
		$(".colse-div").addClass("div-readonly-style");  	//去掉文本框为白的的样式
		$("#sjbmms").attr("ondblclick","Assessment_Open_Modal.openzrdw()");
		$(".pgclass").hide();   			    			//隐藏必填标识
		 $("#pgdh").attr("placeholder","不填写则自动生成");
		 $("#ReferenceUtilView").hide();	
		 $("#ReferenceUtil").show();	
		
		 $(".xgpgrid").show();	
		 $("#xgpgrids").attr("style","width:90%;");
		 $("#xgpgrid1").attr("class","col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group");
		 $("#xgpgrid2").attr("class","col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group");
		 
		 Assessment_Open_Modal.button(1);			   //技术评估单新增权限
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		 $("#pgdh").attr("disabled",false);			   //评估单号可编辑
		 $("#banben").show();						   //版本从1开始-显示
		 $("input[name='lylx']").attr("disabled",true);//来源radio按钮不可编辑，默认为其他
		 $("#lysm").show();							   //来源为其他时，显示来源说明
		 $("#historyList").hide();   			       //隐藏历史版本列表
		 $("#airworthinessDiv").hide();   			   //隐藏适航性资料
		 $("#evaluationDiv").hide();   			       //隐藏关联技术评估单
		 $("#Assessment_Open_Modal_jx").attr("disabled",false);  //机型可编辑
		 //加载流程信息
		 introduce_process_info_class.show({    
				status:1,			 //1保存、2已评估、3已审核、4已批准、5审核驳回、6审批驳回、
				prepared:null,		 //编写人
				preparedDate:null,	 //编写日期
				checkedOpinion:null, //审核意见
				checkedby:null,	     //审核人
				checkedDate:null,	 //审核日期
				checkedResult:null,  //审核结论
				approvedOpinion:null,//批准意见
				approvedBy:null,	 //批准人
				approvedDate:null,   //批准日期
		 });
		
		 $("#modalName").html("新增技术评估单");
		 $("#modalEname").html("Add Evaluation");
		 $("#bbstate").hide();//隐藏版本标识
		 
		 $("#"+this_.id+"").modal("show");
		 Assessment_Open_Modal.validation(); 	//初始化验证
		 this.EmptiedData();				    //清空数据
		 Assessment_Open_Modal.uploader(); 	    //初始化结论附件
		 this.initInfo();						//初始化信息
		 $("#qbsyInput").removeAttr("checked");
		 var obj = {};
			var eoSub = {};
			obj.eoSub = eoSub;
			obj.id = "0";
			obj.syxszList = null; //适用性列表
			obj.sylb = null;
			Assessment_Open_Modal.viewObj=obj;
			
		    //适用性列表
			applicable_settings.init({
				operationShow:true,
				dataList:null,
				sylb:null,
			});
			
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		$(".colse-div").removeClass("div-readonly-style");  //去掉文本框为白的的样式
		Assessment_Open_Modal.initFjjxOption();//初始化机型
		Assessment_Open_Modal.initwclx();      //初始化完成类型
		Assessment_Open_Modal.inijjcd();       //初始化紧急度
		Assessment_Open_Modal.onchangesyx();   //改变本单位适用性
		Assessment_Open_Modal.initOrder();     //加载下达指令
	   
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#glpgdid").val("");
		 $("#attachmentcheckbox").attr("checked",false);
	     $("#fileuploaderSingle").hide();
		 $("#scwjWbwjm").hide();
		 $("#attachmentcheckbox").attr("disabled",false);//结论附件不可编辑
		 Assessment_Open_Modal.uploadObjjl = {};
		
		$("#state").val("");//改版标识
		$("#newbb").text(1);//当前id
		$("#oldbb").text("");//前版本版本
		$("#oldbbid").val("");//前版本id
		$("#id").val(""); //技术文件评估单id
		$("#gljswjid").val(""); //技术文件评估单id
		$("#shxzlid").val(""); //技术文件评估单id
		$("#technicalAttachedid").val(""); //技术文件评估单附表id
		$("#djzt").val(""); //状态
		
		$("#Assessment_Open_Modal input:text").val("");
		$("#Assessment_Open_Modal textarea").val("");
		$("#shxzlbb").text("");
		$("#glbb").text("");
		$("#Assessment_Open_Modal input[name='lylx']").get(1).checked=true;
		$("#lysm").css("visibility","visible");				//显示不适用原因
		$("#wjbhbtn").attr("disabled",false);			    //文件编号可以选择
		$("#Assessment_Open_Modal input[name='syx']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='sylb_public']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_cfjc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_yxzlph']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_mfhc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_zbhc']").get(0).checked=true;
		
		$('#orderDiv input:checkbox').each(function() {
			 var param = $(this).attr("name");
			 $("#Assessment_Open_Modal input[name='"+param+"']").get(0).checked=false;
			 if(param=="other"){
				 $("#"+param).val("");         //说明
			 }else{
//				 $("#"+param+"id").val(userId);//userid
//				 $("#"+param).val(displayName);//username
			 }
		});
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:null,
			fileHead : true,
			colOptionhead : true,
			fileType:"assessment"
		});//显示附件
		
		$("#Assessment_Open_Modal input[name='sylb_public']").get(0).checked=true;
		$("input[name='sylb_public']").attr("disabled",false);
		//根据选择的使用类别,初始化监控项设置
		Assessment_Open_Modal.initJkxszWinBySylb();
	},
	/**
	 * 获取下达指令集合
	 */
	getGiveInstructionListData : function(){
		var this_ = this;
		this_.isValid = true;
		//下达指令
		var giveInstructionList = []; // 技术评估单附加信息
		
		$('#orderDiv input:checkbox').each(function() {
			 var infos ={};
			 var param = $(this).attr("name");
			 var order=$("input:checkbox[name='"+param+"']:checked").val();
			 if(order=="on"){
				 infos.zlxl=$("#"+param+"code").val(); //指令类型
				 if(param=="other"){
					 infos.sm=$("#"+param).val();      //说明
				 }else{
					 var zdryid=$("#"+param+"id").val();//zdryid
					 if(zdryid==null||zdryid==''){
							AlertUtil.showModalFooterMessage("下达指令人员不能为空!"); 
							this_.isValid = false; 
							return false;
					 }else{
						 infos.zdryid=zdryid;//zdryid
					 }
				 }
				 
				 giveInstructionList.push(infos);
			 }
		});
		
		return giveInstructionList;
	},
	/**
	 * 获取涉及部门数据List
	 */
	getcomparBmidListData : function(){
		var sjbmid=$("#sjbmid").val();		//涉及部门id集合
		var distributionDepartmentList=[];
		var dataList=[];
		dataList = sjbmid.split(",");// 在每个逗号(,)处进行分解。
		
		for (var i = 0; i < dataList.length; i++) {
			var distributionDepartment={};
			distributionDepartment.dxid=dataList[i];
			distributionDepartmentList.push(distributionDepartment);
		}
		return distributionDepartmentList;
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#assessmentForm').data('bootstrapValidator').validate();
		if (!$('#assessmentForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		var pgdh=$("#pgdh").val();			//评估单单号
	
		var lylx = $("#"+this_.id+" input:radio[name='lylx']:checked").val(); 	//来源类型
		var shxzlid=$("#shxzlid").val();	//适航性资料id
		var glpgdid=$("#glpgdid").val();	//关联评估单id
		var jx=$("#Assessment_Open_Modal_jx").val();				//关联评估单id
		var zjh=$("#zjh").val();			//章节号
		var pgqx=$("#pgqx").val();			//评估期限
		var wclx=$("#wclx").val();			//完成类型
		var dj=$.trim($("#dj").val());		//等级
		var jjcd=$.trim($("#jjcd").val());	//紧急程度
		var pgdzt=$.trim($("#pgdzt").val());//评估单主题
		var sjgz=$.trim($("#sjgz").val());	//涉及改装
		var wjzy=$.trim($("#wjzy").val());	//文件摘要
		var bz=$.trim($("#bz").val());		//备注
		
		obj.referenceList=ReferenceUtil.getData();//加载参考文件
		//验证参考文件
		if(!ReferenceUtil.isValid){
			return false;
		}
		var syfw_ywj=$.trim($("#syfw_ywj").val());//源文件适用范围
		var sxyq=$.trim($("#sxyq").val());		  //时限要求
		var gbtj=$.trim($("#gbtj").val());		  //关闭条件
		var is_cfjc = $("#"+this_.id+" input:radio[name='is_cfjc']:checked").val(); 	//重复检查
		var is_zzcs = $("#"+this_.id+" input:radio[name='is_zzcs']:checked").val(); 	//最终措施
		var is_yxzlph = $("#"+this_.id+" input:radio[name='is_yxzlph']:checked").val(); //影响重量平衡
		var is_mfhc = $("#"+this_.id+" input:radio[name='is_mfhc']:checked").val(); 	//免费航材
		var is_zbhc = $("#"+this_.id+" input:radio[name='is_zbhc']:checked").val(); 	//自备航材
		var is_tsgj = $("#"+this_.id+" input:radio[name='is_tsgj']:checked").val(); 	//需特殊工具
		var ywjnr=$.trim($("#ywjnr").val());		//源文件内容
		var bj=$.trim($("#bj").val());				//背景
		var wjgxjx=$.trim($("#wjgxjx").val());		//文件关系解析
		var xgwjzxztdc=$.trim($("#xgwjzxztdc").val());//相关文件执行状态调查
		var gcpgjl=$.trim($("#gcpgjl").val());		//工程评估结论
											//结论附件
		
		obj.distributionDepartmentList = this.getcomparBmidListData();	//涉及部门
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
		obj.attachmentList=attachmentsObj.getAttachments();
		
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.pgdh = pgdh;
	
		obj.lylx = lylx;
		if(lylx==9){
			var lysm=$("#lysm").val();	     //来源说明
			obj.lysm = lysm;
		}
		obj.jswjid = shxzlid;
		obj.glpgdid = glpgdid;
		obj.jx = jx;
		obj.pgqx = pgqx;
		obj.zjh = zjh;
		obj.pgdzt = pgdzt;
		obj.sxyq = sxyq;
		obj.bz = bz;
		//最新标识，前版本id，后版本id，状态
		
		var technicalAttachedid=$("#technicalAttachedid").val();//技术文件评估单附表id
		var technicalAttached = {}; // 技术评估单附加信息
		technicalAttached.id = technicalAttachedid; //技术评估单附加信息
		technicalAttached.wclx = wclx;
		technicalAttached.dj = dj;
		technicalAttached.jjcd = jjcd;
		technicalAttached.sjgz = sjgz;
		technicalAttached.wjzy = wjzy;
		technicalAttached.syfwYwj = syfw_ywj;
		var syx = $("#"+this.id+" input:radio[name='syx']:checked").val(); 	//适用性
		if(syx==1){

			obj.giveInstructionList=this.getGiveInstructionListData(); //把技术评估单-下达指令集合加载到技术文件评估单中
			//验证下达指令
			if(!this.isValid){
				return false;
			}
			var sylb = $("#"+this_.id+" input:radio[name='sylb_public']:checked").val(); 	//本适用类别
			technicalAttached.sylb = sylb;
			//适用性
			var syxszList = applicable_settings.getData();
			console.info(syxszList);
			obj.syxszList = syxszList;
			
			if(sylb != 1){
				technicalAttached.fjzch="-";
			}else {
				var checked = $("#qbsyInput").is(":checked");
				if(checked){
					technicalAttached.fjzch="00000";
				}else{
					technicalAttached.fjzch="";
				}
			}
			
			var syfwBdw=$.trim($("#syfw_bdw").val());	//本单位适用范围
			technicalAttached.syfwBdw = syfwBdw;
			
			var yjbfzlsj=$("#yjbfzlsj").val();	//预计颁发时间
			var yjbfzl=$("#yjbfzl").val();		//预计颁发指令
			obj.yjbfzlsj = yjbfzlsj;
			obj.yjbfzl = yjbfzl;
		}else{
			var fsyyy=$.trim($("#fsyyy").val());		//不适用原因
			technicalAttached.fsyyy = fsyyy;
		}
		technicalAttached.syx = syx;
		technicalAttached.gbtj = gbtj;
		technicalAttached.isCfjc = is_cfjc;
		technicalAttached.isZzcs = is_zzcs;
		technicalAttached.isYxzlph = is_yxzlph;
		technicalAttached.isMfhc = is_mfhc;
		technicalAttached.isZbhc = is_zbhc;
		technicalAttached.isTsgj = is_tsgj;
		technicalAttached.ywjnr = ywjnr;
		technicalAttached.bj = bj;
		technicalAttached.wjgxjx = wjgxjx;
		technicalAttached.xgwjzxztdc = xgwjzxztdc;
		technicalAttached.gcpgjl = gcpgjl;
		
		obj.technicalAttached=technicalAttached; //把技术文件评估单-附加信息加载到技术文件评估单中
		
		//结论附件
		var checked = $("#attachmentcheckbox").is(":checked");
		
		if(checked){
			if(Assessment_Open_Modal.uploadObjjl.wbwjm){
				obj.assessmentAttachment=Assessment_Open_Modal.uploadObjjl;
			}else {
				AlertUtil.showModalFooterMessage("选择源文件后，请上传附件");
				return false;
			}
		}else{
			obj.deleteUploadId = $("#scfjId").val();
		}
		
		if(Assessment_Open_Modal.deleteUploadId){
			obj.deleteUploadId = Assessment_Open_Modal.deleteUploadId;
		}
	return obj;
	},
	/**
	 * 保存
	 */
	save : function(){
		var obj=this.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#id").val();
		obj.bb=$("#newbb").text(); //新版本
		var url="";
		if(idnew==""&&$("#state").val()!="on"){
			url="/project2/assessment/save";//新增技术评估单
		}else if(idnew!=""&&$("#state").val()!="on"){
			url="/project2/assessment/update";//修改技术评估单
			obj.id=idnew;
		}else{
			url="/project2/assessment/revisionSave";//改版保存技术评估单
			obj.id=idnew;
		}
		
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Assessment_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Assessment_Open_Modal").modal("hide");
	 			hideBottom();
	 			decodePageParam();
	 		}
	   	  });
	},
	/**
	 * 提交
	 */
	submit : function(){
		var this_=this;
		
		var obj=this_.getData();
		if(obj==false){
			return false;
		}
		var idnew=$("#id").val();
		obj.bb=$("#newbb").text(); //新版本
		var url="";
		
		if(idnew==""&&$("#state").val()!="on"){
			url="/project2/assessment/saveSubmit";//新增提交技术评估单
		}else if(idnew!=""&&$("#state").val()!="on"){
			url="/project2/assessment/updateSubmit";//修改提交技术评估单
			obj.id=idnew;
		}else{
			url="/project2/assessment/updateRevisionSubmit";//改版提交技术评估单
			obj.id=idnew;
		}
		
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Assessment_Open_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#Assessment_Open_Modal").modal("hide");
	 			hideBottom();
	 			decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Assessment_Open_Modal").on("hidden.bs.modal", function() {

	$("#assessmentForm").data('bootstrapValidator').destroy();
	$('#assessmentForm').data('bootstrapValidator', null);
	//Assessment_Open_Modal.validation();
});

