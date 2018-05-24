FailureKeep_Add_Modal = {
	id : "FailureKeep_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		 var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("新增故障保留单");
		 $("#modalEname").html("Add Evaluation");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 FailureKeep_Open_Modal.EmptiedData();  //清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		$(".div-hide").hide();   			        			//隐藏标识
		FailureKeep_Open_Modal.button(1);						//初始化按钮权限
		FailureKeep_Open_Modal.validation(); 					//初始化验证
		FailureKeep_Open_Modal.onchangeblly(); 					//初始化来源类型
		FailureKeep_Open_Modal.initFjzchOption(userJgdm); 				//初始化飞机注册号
		FailureKeep_Open_Modal.Equipment_list_edit(null,true); 	//初始化器材清单
		FailureKeep_Open_Modal.Tools_list_edit(null,true); 		//初始化工具设备
		FailureKeep_Open_Modal.onchangebllx(1); //新增，默认选中第一个，保留类别
		
		approval_process_info.show({    
		 	status:1,			  //：1保存、2提交、4已批准、6审批驳回、9关闭、10完成
			prepared:null,	  	  //编写人
			preparedDate:null,	  //编写日期
			approvedOpinion:null, //批准意见
			approvedBy:null,      //批准人
			approvedDate:null,    //批准日期
		});
		
		$("#scSqrid").val(userId);  	//申请人id
		$("#scSqrbmdm").val(userBmdm);  	//申请人部门id
		
		$("#scSqr").val(displayName);  			//申请人描述
		TimeUtil.getCurrentDate("#scSqrq");                          //取得当前时间
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#failurekeepForm').data('bootstrapValidator').validate();
		if (!$('#failurekeepForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 	//old状态
		obj.paramsMap = paramsMap;
	
		var bldh=$("#bldh").val();					//故障保留单号
		var fjzch=$("#"+this_.id+"_fjzch").val();	//飞机注册号
		var zjh=$("#zjh").val();					//章节号
		var hz=$("#hz").val();						//航站
		var blly = $("#"+this_.id+" input:radio[name='blly']:checked").val(); 	//来源类型				
		var scSqrid=$("#scSqrid").val();			//申请人id
		var scSqrbmdm=$("#scSqrbmdm").val();		//部门id
		var scSqrq=$("#scSqrq").val();				//申请人日期
		var scBlqx=$("#scBlqx").val();				//保留期限
		var scSqrzzh=$("#scSqrzzh").val();			//申请人执照号
		var bllx = $("#"+this_.id+" input:radio[name='bllx']:checked").val(); 	//保留类型				
		var sxgsRs=$("#sxgsRs").val();				//所需工时-人数
		var sxgsXs=$("#sxgsXs").val();				//所需工时-小时
		var blyj=$("#blyj").val();					//保留依据
		var gzms=$("#gzms").val();					//故障描述
		var blyy=$("#blyy").val();					//保留原因
		var lscs=$("#lscs").val();					//临时措施
		var mbschecked = $("#mbs").is(":checked");  //执行M程序
		var obschecked = $("#obs").is(":checked");  //执行O程序
		var yxxzbschecked = $("#yxxzbs").is(":checked");  //运行限制
		
		var bjh=$("#gzblBjh").val();					//部件号
		var xlh=$("#gzblXlh").val();					//序列号
		var fxsj=$("#fhInput").val();					//飞行时间
		var fxxh=$("#fcInput").val();					//飞行循环
		
		var materialToolList = Equipment_list_edit.getData(); //获取器材清单数据
		var toolArr = Tools_list_edit.getData(); 			  //获取工具设备数据
		
		//验证器材清单
		if(!Equipment_list_edit.isValid){
			return false;
		}
		
		//验证工具设备
		if(!Tools_list_edit.isValid){
			return false;
		}
		
		/**
		 * 加载器材清单,工具设备
		 */
		$.each(toolArr,function(index,row){
			var infos = {};
			infos.id = row.id;
			infos.bjid = row.bjid;
			infos.jh = row.jh;
			infos.bjlx = row.bjlx;
			infos.sl = row.sl;
			infos.bz = row.bz;
			infos.hhxx = row.hhxx;
			infos.xc = row.xc;
			materialToolList.push(infos);
		});
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
		
		/* var oDate1 = new Date(scSqrq); //申请日期
	 	 var oDate2 = new Date(scBlqx); //保留期限
 		 if(oDate1.getTime()>=oDate2.getTime()){
 			AlertUtil.showModalFooterMessage('保留期限必须大于  报告日期!');
			return false;
 	     }*/
		
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.bs145 = $("#bs145").val(); 		 //145标识
		obj.bldh = bldh;
		obj.fjzch = fjzch;
		obj.zjh = zjh;
		if(zjh == ""){
			obj.zjh = $("#zjhms").val();
		}
		obj.hz = hz;
		obj.blly = blly;
		if(blly==1){ //飞行记录本
			obj.bllyid = $("#bllyid").val(); //保留来源id
		}else if(blly==2){//NRC
			obj.bllyid = $("#bllyid").val(); //保留来源id
		}
		obj.bllx = bllx;
		if(bllx==9){
			obj.bllxsm = $("#bllxsm").val(); //保留类型为9  保留类型描述
		}
		obj.sxgsRs = sxgsRs;
		obj.sxgsXs = sxgsXs;
		obj.blyj = blyj;
		obj.gzms = gzms;
		obj.blyy = blyy;
		obj.lscs = lscs;
		if(mbschecked){
			obj.mbs = 1;
			obj.msm = $("#msm").val(); //执行M程序说明
		}else{
			obj.mbs = 0;
		}
		if(obschecked){
			obj.obs = 1;
			obj.osm = $("#osm").val(); //执行O程序说明
		}else{
			obj.obs = 0;
		}
		if(yxxzbschecked){
			obj.yxxzbs = 1;
			obj.yxxzsm = $("#yxxzsm").val(); //运行限制说明
		}else{
			obj.yxxzbs = 0;
		}
		obj.scSqrbmid = scSqrbmdm;
		obj.scSqrid = scSqrid;
		obj.scSqrq = scSqrq;
		obj.scSqrzzh = scSqrzzh;
		obj.scBlqx = scBlqx;
		
		obj.materialToolList = materialToolList; //加载器材清单,工具设备
		obj.attachmentList=attachmentsObj.getAttachments();//加载附件数据
		
		obj.bjh= bjh;					//部件号
		obj.xlh= xlh;					//序列号
		obj.fxsj= fxsj;					//飞行时间
		obj.fxxh= fxxh;					//飞行循环
		
		obj.blfxsj= $("#blfhInput").val();;				//保留飞行时间
		obj.blfxxh= $("#blfcInput").val();;				//保留飞行循环
		
		if(scBlqx=="" && obj.blfxsj=="" && obj.blfxxh==""){
			$("#scBlqx").addClass("border-color-red");
			$("#blfhInput").addClass("border-color-red");
			$("#blfcInput").addClass("border-color-red");
			AlertUtil.showModalFooterMessage('保留期限，至少填写一个!');
			return false;
		}
		
		//影响服务代码
		var yxfwdmList = [];
		$("input[name='yxfwdm']:checked").each(function() {  
			yxfwdmList.push($(this).val());  
		}); 
		obj.yxfwdmList = yxfwdmList;
		
		//涉及部门代码集合
		var sjbmdmList = [];
		$("input[name='sjbmdm']:checked").each(function() {  
			sjbmdmList.push($(this).val());  
		});  
		obj.sjbmdmList = sjbmdmList;
		
		//运行限制代码集合
		var yxxzdmList = [];
		$("input[name='yxxzdm']:checked").each(function() {  
			yxxzdmList.push($(this).val());  
		});  
		obj.yxxzdmList = yxxzdmList;
		
		//保留原因代码集合
		var blyydmList = [];
		$("input[name='blyydm']:checked").each(function() {  
			blyydmList.push($(this).val());  
		});  
		obj.blyydmList = blyydmList;
		
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
		var url="";
		if(idnew==""){
			url="/produce/reservation/fault/save";//新增故障保留
		}else if(idnew!=""){
			url="/produce/reservation/fault/update";//修改故障保留
			obj.id=idnew;
		}
		startWait($("#FailureKeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#FailureKeep_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#FailureKeep_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#FailureKeep_Open_Modal").modal("hide");
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
		var url="";
		if(idnew==""){
			url="/produce/reservation/fault/saveSubmit";//提交新增故障保留
		}else if(idnew!=""){
			url="/produce/reservation/fault/updateSubmit";//提交修改故障保留
			obj.id=idnew;
		}
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		startWait($("#FailureKeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#FailureKeep_Open_Modal"),
	 		success:function(data){
	 			id = data;
	 			pageParam=encodePageParam();
	 			finishWait($("#FailureKeep_Open_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#FailureKeep_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#FailureKeep_Open_Modal").on("hidden.bs.modal", function() {
	$("#failurekeepForm").data('bootstrapValidator').destroy();
	$('#failurekeepForm').data('bootstrapValidator', null);
//	FailureKeep_Open_Modal.validation();
});

