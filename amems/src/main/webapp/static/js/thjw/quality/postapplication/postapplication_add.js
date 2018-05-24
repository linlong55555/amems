Post_Add_Modal = {
	id : "Post_Open_Modal",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("新增授权申请");
		 $("#modalEname").html("Add Authorized Application");
		 Post_Open_Modal.EmptiedData();  		//清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		
		
		$(".div-hide").hide();   			        		//隐藏标识
		
		$(".requiredgwsq").show();   			    	//岗位授权不可编辑
		$(".colsegwsq").attr("disabled",false);			//标签不可编辑
		$(".colsegwsq").addClass("readonly-style");  //岗位授权不可编辑
		
		Post_Open_Modal.button(1);							//初始化按钮权限
		Post_Open_Modal.validation(); 						//初始化验证
		$("#sqr").val(displayName);	
		TimeUtil.getCurrentDate("#sqsj");       			//取得当前时间
		this.initDate();									//加载授权申请数据
		
		//初始化机型
		 plane_model_list_edit.init({
			djid:"",//关联单据id
			colOptionhead : true,
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback: function(data){//回调函数
				Post_Open_Modal_Trainingcourse.setData($("#wxrydaid").val(),$("#sqgwid").val(),$("#dprtcode").val(),data);  //课程培训经历
			}
		});
	},
	/**
	 * 加载授权申请数据
	 */
	initDate : function(){
		var this_=this;
		var obj = {};
		obj.wxryid = userId;
		obj.dprtcode = $("#dprtcode").val(),//
		startWait($("#Post_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/maintenancepersonnel/findRyidExist",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Post_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Post_Open_Modal"));
 				if(data!=null){
 					$("#wxryid").val(userId);	
 					Post_Open_Modal.setData(data.id);
 				}
 				$("#"+this_.id+"").modal("show");//显示窗口
 				
	 		}
		});
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		var code="quality:post:application:main:06";
		if($.inArray(code.toLowerCase(), buttonPermissions) == -1){
			var xm=$("#xm").val();							//姓名
			if(xm==''){
				AlertUtil.showModalFooterMessage("当前登录人不是维修档案人员!");
				return false;
			}
		}
		
		$('#form1').data('bootstrapValidator').validate();
		if (!$('#form1').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 	//old状态
		obj.paramsMap = paramsMap;
		
		var wxrydaid=$("#wxrydaid").val();				//申请岗位id
		var sqlx = $("#"+this_.id+" input:radio[name='sqlx']:checked").val(); //申请类型
		var sqgwid=$("#sqgwid").val();					//申请岗位id
		var shrbmdm=$("#shrbmdm").val();				//审核部门
		var shrid=$("#shrid").val();					//审核人id
		var qtzz=$("#qtzz").val();						//其他执照
		var qtzzyxq=$("#qtzzyxq").val();				//其他执照有效期
		var sqbz=$("#sqbz").val();						//备注
		var sqsqdh=$("#sqsqdh").val();
		
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		obj.sqrdaid = wxrydaid;
		obj.sqlx = sqlx;
		obj.sqgwid = sqgwid;
		obj.shbmid = shrbmdm;
		obj.shrid = shrid;
		obj.qtzz = qtzz;
		obj.qtzzyxq =qtzzyxq;
		obj.sqbz =sqbz;
		obj.sqsqdh=sqsqdh;
		//设置机型
		obj.postApplicationSQJXList = plane_model_list_edit.getData();
		
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
		
		//验证机型
		if(!plane_model_list_edit.isValid){
			return false;
		}
		
		var idnew=$("#id").val();
		var url="";
		if(idnew==""){
			url="/quality/post/application/save";//新增
		}else if(idnew!=""){
			url="/quality/post/application/update";//修改
			obj.id=idnew;
		}
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
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Post_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Post_Open_Modal").modal("hide");
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
		
		//验证机型
		if(!plane_model_list_edit.isValid){
			return false;
		}
		
		var idnew=$("#id").val();
		var url="";
		if(idnew==""){
			url="/quality/post/application/saveSubmit";//提交新增
		}else if(idnew!=""){
			url="/quality/post/application/updateSubmit";//提交修改
			obj.id=idnew;
		}
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
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
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Post_Open_Modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#Post_Open_Modal").modal("hide");
	 			decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#Post_Open_Modal").on("hidden.bs.modal", function() {
	$("#form1").data('bootstrapValidator').destroy();
	$('#form1').data('bootstrapValidator', null);
//	Post_Open_Modal.validation();
});

