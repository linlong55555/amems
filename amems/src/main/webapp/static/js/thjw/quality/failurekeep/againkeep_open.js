again_keep_open = {
	id : "Againkeep_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#"+this_.id+"_modalName").html("再次保留故障保留单");
		 $("#"+this_.id+"_modalEname").html("Delay Again Defect Deferred Log");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 this_.EmptiedData();  					//清空数据
		 this_.validation(); 					//初始化验证
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化验证
	 */
	validation : function(){
		validatorForm = $('#againfailurekeepForm').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	   zcSqr: {
			            validators: {
			            	notEmpty: {message: '办理人不能为空'}
			            }
			      }, 
			      zcSqrq: {
			            validators: {
			            	notEmpty: {message: '办理日期不能为空'}
			           }
				  }, 
				 /* zcBlqx: {
			            validators: {
			            	notEmpty: {message: '再次保留期限不能为空'}
			           }
				  }, */
				  zcBlyy: {
			            validators: {
			            	notEmpty: {message: '再次保留原因不能为空'}
			           }
				  }
		       }
		   });
	},
	//选择人员
	openUser: function(obj){
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
			dprtcode:$("#dprtcode").val(),//
			multi:false,
			callback: function(data){//回调函数
				var bmdm = '';
				var displayName = '';
				var mjsrid = '';
				if(data != null && data != ""){
					$.each(data, function(i, row){
						displayName += formatUndefine(row.displayName) + ",";
						mjsrid += formatUndefine(row.id) + ",";
						bmdm += formatUndefine(row.bmdm) + ",";
					});
				}
				if(bmdm != ''){
					bmdm = bmdm.substring(0,bmdm.length - 1);
				}
				if(displayName != ''){
					displayName = displayName.substring(0,displayName.length - 1);
				}
				if(mjsrid != ''){
					mjsrid = mjsrid.substring(0,mjsrid.length - 1);
				}
				$("#"+obj).val(displayName);
				$("#"+obj+"id").val(mjsrid);
				$("#"+obj+"bmid").val(bmdm);
				
				if(obj!='zcPzr'){
					$('#againfailurekeepForm').data('bootstrapValidator')  
					.updateStatus(""+obj+"", 'NOT_VALIDATED',null)  
					.validateField(""+obj+""); 
				}
			}
		});
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		
		$(".noteditable").attr("disabled",false);				  //标签可编辑
		$(".required").show();   			    			      //隐藏必填标识*
		$(".colse").addClass("readonly-style");  				  //去掉文本框为白的的样式
		
		$("#"+this.id+" input[type='text']").val(""); //清空所有文本框数据
		$("#"+this.id+" textarea").val("");	 //清空所有多行文本框数据
		$("#"+this.id+" input[type='hidden']").val(""); 
		
		$("#"+this.id+" input[name='bllx2']").get(0).checked=true; //保留类型默认选择第一个
		$("#"+this.id+" input[name='bllx2']").attr("disabled",true);//保留类别不可编辑
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
	
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#Againkeep_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/produce/reservation/fault/getByFailureKeepId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Againkeep_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Againkeep_Open_Modal"));
	 			if(data!=null){
	 				this_.setDate(data);
				};
	 		}
		});
	},
	//改变保留类别
	onchangebllx : function(typeFlag){
		
		var this_=this;
		var bllx=$("input[name='bllx2']:checked").val();
		
		/**清空*/
		if(typeFlag==1){
			$("#zcBlqx").val("");//保留期限
			$("#zcFhInput").val("");//飞行时间
			$("#zcFcInput").val("");//飞行循环
		}
		
		/**文本框显示和可编辑*/
		if (bllx==2 || bllx==3 || bllx==4) {
			$("#zcBlqx").attr("disabled",true);//保留期限不可编辑
			$("#fhDiv2").css("visibility","hidden");//飞行时间隐藏
			$("#fcDiv2").css("visibility","hidden");//飞行循环隐藏
		}else{
			$("#zcBlqx").attr("disabled",false);//保留期限可编辑
			$("#fhDiv2").css("visibility","visible");//飞行时间显示
			$("#fcDiv2").css("visibility","visible");//飞行循环显示
		}

		/**保留期限赋值*/
		if (bllx==2 || bllx==3 || bllx==4) {
			var addDay = 0;
			if (bllx==2) {
				addDay = 3;
			} else if (bllx==3) {
				addDay = 10;
			} else if (bllx==4) {
				addDay = 120;
			}
			var scSqrq=$("#zcSqrq").val(); //申请人日期
			if(scSqrq !=''){
				var num=new Date(FailureKeep_Open_Modal.dateAdd(scSqrq,"d",addDay));
				if(num!=""){
					this_.CalculationNum(num);
				}
			}
		}
		
	},
	//回显再次保留期限
	CalculationNum: function(num){
		$(".icon-zcblqx").hide();   			    //隐藏保留单号星
		$("#zcBlqx").attr("disabled",true);		//不可编辑
		
		
		var rDate = new Date(num);  
	    var year = rDate.getFullYear();  
	    var month = rDate.getMonth() + 1;  
	    if (month < 10) month = "0" + month;  
	    var date = rDate.getDate();
	    if (date < 10) date = "0" + date;  
		$("#zcBlqx").val(year + "-" + month + "-" + date);//保留期限
		$("#zcBlqx").datepicker("update");
	},
	/**
	 * 加载故障保留数据
	 */
	setDate : function(data){
		$("#zcSqrid").val(userId);  		 //办理人id				
		$("#zcSqrbmid").val(userJgdm); 	 	 //办理人部门			
		$("#zcSqr").val(displayName); 	 	 //办理人姓名描述	
		
		FailureKeep_Open_Modal.getAttachments3(data.id,true,true);
		var this_=this;
		$("#id").val(data.id);
		$("#djzt").val(data.zt); 							    //状态
		$("#"+this_.id+"_bldh").val(data.bldh);					//故障保留单号
		$("#"+this_.id+"_blqx").val(formatUndefine(data.scBlqx).substring(0,10));					//故障保留期限
		$("#zcSqrq").val(TimeUtil.getCurrentDate("#zcSqrq"));	//办理日期
		
		$("input:radio[name='bllx2'][value='"+data.bllx+"']").attr("checked",true); //保留类型	
		$("input:radio[name='bllx2'][value='"+data.bllx+"']").trigger('click');
		
		var scBlqx=formatUndefine(data.scBlqx).substring(0,10); //保留期限
		if(data.bllx==2){
			this_.CalculationNum(new Date(FailureKeep_Open_Modal.dateAdd(scBlqx,"d",3)));
		}else if(data.bllx==3){
			this_.CalculationNum(new Date(FailureKeep_Open_Modal.dateAdd(scBlqx,"d",10)));
		}else if(data.bllx==4){
			this_.CalculationNum(new Date(FailureKeep_Open_Modal.dateAdd(scBlqx,"d",120)));
		}else{
			$("#zcBlqx").attr("disabled",false);//可编辑
		}
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#againfailurekeepForm').data('bootstrapValidator').validate();
		if (!$('#againfailurekeepForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		var id=$("#id").val();					//单据id
		var dprtcode=$("#dprtcode").val();		//组织机构
		var zcSqrbmid=$("#zcSqrbmid").val();	//再次申请人部门id
		var zcSqrid=$("#zcSqrid").val();		//再次申请人id
		var zcSqrq=$("#zcSqrq").val();			//再次申请日期
		var zcBlqx=$("#zcBlqx").val();			//再次保留期限
		var zcBlyy=$("#zcBlyy").val();			//再次保留原因
		var zcPzrbmid=$("#zcPzrbmid").val();	//再次批准人部门id
		var zcPzrid=$("#zcPzrid").val();		//再次批准人id
		var zcPzrq=$("#zcPzrq").val();			//再次批准日期
		var jfpzyj=$("#jfpzyj").val();			//局方批准意见
		var jfpzr=$("#jfpzr").val();			//局方批准人
		var jfpzrq=$("#jfpzrq").val();			//局方批准日期
		
/*		 var oDate1 = new Date($("#"+this_.id+"_blqx").val()); //保留日期
	 	 var oDate2 = new Date(zcBlqx); //再次保留期限
		 if(oDate1.getTime()>=oDate2.getTime()){
			AlertUtil.showModalFooterMessage('再次保留期限必须大于首次保留期限!');
			return false;
	     }*/
		 var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit3');//获取附件数据
		obj.attachmentList=attachmentsObj.getAttachments();//加载附件数据
		obj.id=id;
		obj.dprtcode=dprtcode;
		obj.zcSqrbmid=zcSqrbmid;
		obj.zcSqrid=zcSqrid;
		obj.zcSqrq=zcSqrq;
		obj.zcBlqx=zcBlqx;
		obj.zcBlyy=zcBlyy;
		obj.zcPzrbmid=zcPzrbmid;
		obj.zcPzrid=zcPzrid;
		obj.zcPzrq=zcPzrq;
		obj.jfpzyj=jfpzyj;
		obj.jfpzr=jfpzr;
		obj.jfpzrq=jfpzrq;
		
		obj.blfxsj= $("#zcFhInput").val();//飞行时间
		obj.blfxxh= $("#zcFcInput").val();//飞行循环
		
		if(obj.zcBlqx=="" && obj.blfxsj=="" && obj.blfxxh==""){
			$("#zcBlqx").addClass("border-color-red");
			$("#zcFhInput").addClass("border-color-red");
			$("#zcFcInput").addClass("border-color-red");
			AlertUtil.showModalFooterMessage('再次保留期限，至少填写一个!');
			return false;
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
		 startWait($("#Againkeep_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+"/quality/faultmonitoring/saveagainkeep",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Againkeep_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Againkeep_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Againkeep_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Againkeep_Open_Modal").on("hidden.bs.modal", function() {
	$("#againfailurekeepForm").data('bootstrapValidator').destroy();
	$('#againfailurekeepForm').data('bootstrapValidator', null);
//	again_keep_open.validation();
});

$('#zcSqrq').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#againfailurekeepForm').data('bootstrapValidator')  
	.updateStatus('zcSqrq', 'NOT_VALIDATED',null)  
	.validateField('zcSqrq');  
});
/*$('#zcBlqx').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#againfailurekeepForm').data('bootstrapValidator')  
	.updateStatus('zcBlqx', 'NOT_VALIDATED',null)  
	.validateField('zcBlqx');  
});*/
