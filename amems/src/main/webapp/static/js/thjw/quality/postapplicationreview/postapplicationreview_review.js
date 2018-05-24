Post_Update_Review_Modal = {
	id : "Post_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("审核授权");
		 $("#modalEname").html("Review Authorized");
		 Post_Open_Modal.EmptiedData();  		//清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".div-hide").show();   			    //隐藏标识
		Post_Open_Modal.validation(); 			//初始化验证
		this.initDate(param);					//加载故障保留数据
	},
	/**
	 * 加载故障保留数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait($("#Post_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/post/application/getByPostApplicationId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Post_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Post_Open_Modal"));
	 			if(data!=null){
	 				if(data.zt!=2){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					return false;
	 				}else{
	 					$("#"+this_.id+"").modal("show");//显示窗口
	 					this_.setDate(data);// 加载故障保留数据
	 				}
				};
	 		}
		});
	},
	/**
	 * 加载故障保留数据
	 */
	setDate : function(data){
		$("#id").val(data.id);
		$("#sqsqdh").val(data.sqsqdh);					//授权申请单号
		$("#djzt").val(data.zt);  						//当前单据状态
		$("#ztms").val(DicAndEnumUtil.getEnumName('postStatusEnum',data.zt));  //当前单据状态翻译
		
		$("#qtzz").val(data.qtzz);  										//其它执照
		$("#qtzzyxq").val(formatUndefine(data.qtzzyxq).substring(0,10)); 	//其他执照有效期
		$("input:radio[name='sqlx'][value='"+data.sqlx+"']").attr("checked",true); //申请类型
		$("#sqgwid").val(data.sqgwid);  				//其他执照有效期
		$("#sqgwms").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgbh:''));  //岗位编号
		$("#dgmc").val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgmc:''));  	//岗位名称
		$("#sqr").val(StringUtil.escapeStr(data.whr?data.whr.username:'')+" "+StringUtil.escapeStr(data.whr?data.whr.realname:''));  					//申请人
		$("#sqsj").val(formatUndefine(data.sqsj).substring(0,10));  				//申请时间	
		$("#shr").val(StringUtil.escapeStr(data.shr?data.shr.username:'')+" "+StringUtil.escapeStr(data.shr?data.shr.realname:'')); //审核人 				
		$("#sqbz").val(data.sqbz);  					//备注
		Post_Open_Modal.setData(data.sqrdaid);
		
		$(".noteditable").attr("disabled",true);	//标签不可编辑
		$(".required").hide();   			    	//隐藏必填标识*
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
		
		//初始化机型
		 plane_model_list_edit.init({
			djid:data.id,//关联单据id
			colOptionhead : false,
			dprtcode:data.dprtcode,//默认登录人当前机构代码
			callback: function(data1){//回调函数
				Post_Open_Modal_Trainingcourse.setData($("#wxrydaid").val(),$("#sqgwid").val(),data.dprtcode,data1);  //课程培训经历
			}
		});
	},
	/**
	 * 审核通过
	 */
	passed : function(){
		var url="/quality/post/review/passed";//审核通过
		var str="审核通过";//审核通过
		this.subjectFrom(str,url,1);
	},
	/**
	 * 审核驳回
	 */
	turnDown : function(){
		var url="/quality/post/review/turnDown";//审核驳回
		var str="审核驳回";
		this.subjectFrom(str,url,2);
	},
	/**
	 * 审核通过
	 */
	subjectFrom : function(str,url,state){
		var obj = {};
		
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
	
		obj.id=$("#id").val();
		obj.shyj=$.trim($("#shyj").val());	 	//审核意见
		obj.dprtcode = $("#dprtcode").val(); 	//组织机构
		obj.sqrdaid=$("#wxrydaid").val();       //申请人档案id
		obj.sqgwid=$("#sqgwid").val();          //申请岗位id
		obj.sqsqdh=$("#sqsqdh").val();          //授权单号
		if($.trim($("#shyj").val()) == ''&& state == 2){
			AlertUtil.showMessage("请填写审核意见!");
			return false;
		}
		AlertUtil.showConfirmMessage("确定要"+str+"吗？", {callback: function(){
			 
		 startWait($("#Post_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Post_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Post_Open_Modal"));
	 			AlertUtil.showMessage(""+str+"成功!");
	 			$("#Post_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	   	 
	 }});
	   	 
	}
};