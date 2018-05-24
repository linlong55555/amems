Post_View_Modal = {
	id : "Post_Open_Modal",
	open: function(param){
		var this_ = this;
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 Post_Open_Modal.EmptiedData();  		//清空数据
		 this_.initInfo(param);					//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		$(".div-hide").show();   			        	//隐藏标识
		
		Post_Open_Modal.button(2);						//初始化按钮权限
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
 					this_.setDate(data);// 加载故障保留数据
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
		$("#shyj").val(data.shyj);  				//审核意见
		$("#sqbz").val(data.sqbz);  					//备注
	
		//初始化机型
		 plane_model_list_edit.init({
			djid:data.id,//关联单据id
			colOptionhead : false,
			dprtcode:data.dprtcode,//默认登录人当前机构代码
			callback: function(data1){//回调函数
				Post_Open_Modal_Trainingcourse.setData($("#wxrydaid").val(),$("#sqgwid").val(),data.dprtcode,data1);  //课程培训经历
			}
		});
		 
		Post_Open_Modal.setDatatojx(data.sqrdaid);
		

		$(".noteditable").attr("disabled",true);	//标签不可编辑
		$(".required").hide();   			    	//隐藏必填标识*
		$(".colse").removeClass("readonly-style");  //去掉文本框为白的的样式
		
	},
	/**
	 * 获取数据
	 */
	getData : function(){
		var obj = {};
		$('#form1').data('bootstrapValidator').validate();
		if (!$('#form1').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 		//old状态
		obj.paramsMap = paramsMap;
		
		var shrbmdm=$("#shrbmdm").val();				//审核部门
		var shrid=$("#shrid").val();					//审核人id
		var idnew=$("#id").val();
		
		obj.dprtcode = $("#dprtcode").val(); 			//组织机构
		obj.shbmid = shrbmdm;
		obj.shrid = shrid;
		obj.id=idnew;
	return obj;
	},
	/**
	 * 保存审核人
	 */
	save : function(){
		var obj=this.getData();
		if(obj==false){
			return false;
		}
		
		var url="/quality/post/application/updateShr";//修改审核人
		
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
};

$(document).ready(function(){
	Navigation(menuCode, '查看授权', 'Select Post', 'GC-2-4 ', '林龙', '2017-08-01', '林龙', '2017-08-01');//加载导航栏
	$("#form input").attr("disabled",true);
	$("#form select").attr("disabled",true);
	$("#form textarea").attr("disabled",true);
	Post_View_Modal.open($("#viewid").val());
	
});
function showTab(){
	$("#trainingCourse").addClass("collapse in");
	$("#trainingCourse").css("height","auto");
}
function collapseHide(){
	if(!$("#myTabContent").hasClass("active")){
		$('#myTab a:first').tab('show');
	}
	if($("#trainingCourse").hasClass("collapse in")){
		$("#myTab li").removeClass("active");
		$("#myTabContent .tab-pane").removeClass("active");
	};
}