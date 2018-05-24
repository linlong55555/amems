Post_Update_Modal = {
	id : "Post_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("修改授权申请");
		 $("#modalEname").html("Update Authorized Application");
		 Post_Open_Modal.EmptiedData();  		//清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".div-hide").show();   			        	//隐藏标识
		
		$(".requiredgwsq").hide();   			    	//岗位授权不可编辑
		$(".colsegwsq").attr("disabled",true);			//标签不可编辑
		$(".colsegwsq").removeClass("readonly-style");  //岗位授权不可编辑
		
		Post_Open_Modal.button(1);						//初始化按钮权限
		Post_Open_Modal.validation(); 					//初始化验证
		this.initDate(param);							//加载故障保留数据
		
	},
	/**
	 * 加载故障保留数据
	 */
	initDate : function(param){
		var this_=this;
		var obj = {};
		obj.id = param;
		//根据单据id查询信息
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/post/application/getByPostApplicationId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Post_Open_Modal"),
	 		success:function(data){
	 			finishWait();
	 			if(data!=null){
	 				if(data.zt!=1 && data.zt!=5){
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
		$("#shrid").val(data.shrid);  					//审核人id
		$("#shbmid").val(data.shbmid);  				//审核人部门id
		$("#shr").val(StringUtil.escapeStr(data.shr?data.shr.username:'')+" "+StringUtil.escapeStr(data.shr?data.shr.realname:'')); //审核人 				
		$("#sqbz").val(data.sqbz);  					//备注
		
		//初始化机型
		 plane_model_list_edit.init({
			djid:data.id,//关联单据id
			colOptionhead : true,
			dprtcode:data.dprtcode,//默认登录人当前机构代码
			callback: function(data1){//回调函数
				Post_Open_Modal_Trainingcourse.setData($("#wxrydaid").val(),$("#sqgwid").val(),data.dprtcode,data1);  //课程培训经历
			}
		});
		
		Post_Open_Modal.setData(data.sqrdaid);
	}
};