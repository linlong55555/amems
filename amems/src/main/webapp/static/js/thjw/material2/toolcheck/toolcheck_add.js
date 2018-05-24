toolcheck_add_modal = {
	id : "toolcheck_open_modal",
	main : "toolcheck",
	isValid : true,//验证是否通过,isValid:true表示验	证通过,isValid:false表示验证未通过
	open: function(param){
		var this_ = this; 
		 AlertUtil.hideModalFooterMessage();    //初始化错误信息 
		 $("#modalName").html("新增计量工具");
		 $("#modalEname").html("Add Evaluation");
		 $("#"+this_.id+"").modal("show");		//open窗口
		 toolcheck_open_modal.EmptiedData();   	//清空数据
		 this_.initInfo();						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(){
		var this_ = this; 
		$(".div-hide").hide();   			    //隐藏维护人
		$(".button").show();   			        //隐藏按钮
		toolcheck_open_modal.validation(); 		//初始化验证
		toolcheck_open_modal.initDic(); 		//初始化计量方式
		$("#zjldj").val($("#jldj").val());		//初始化计量等级
		toolcheck_open_modal.getAttachments(null,true,true); //加载附件
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_=this;
		var obj = {};
		$('#toolcheckForm').data('bootstrapValidator').validate();
		if (!$('#toolcheckForm').data('bootstrapValidator').isValid()) {
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
			return false;
		}
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); 	//old状态
		obj.paramsMap = paramsMap;
	
		var dbjxlh=$("#bjxlh").val();				//编号
		var dbjh=$("#bjh").val();					//部件号
		
		var flag=true;
		 
		 var len=$("#toolcheck_open_modal_list>div[name='zbjs']").length;
		 var measurementToolsDetailsList=[];
		 
		if(len == 0  && $('#toolcheck_open_modal_data_list').css('display')=='none'){
			AlertUtil.showModalFooterMessage("请添加一个子部件进行校验!");
			return false;
		}else{
				if( $("#bs").val() == 2){
				
				var measurementToolsDetails={};      //
				
				var zbjxlh =$("#zbjxlh").val();
				var zzwms =$("#zzwms").val();
				var zywms =$("#zywms").val();
				var zgg =$("#zgg").val();
				var zxh =$("#zxh").val();
				var zbjbs =1;
				var zbz =$("#zbz").val();
				var zjyScrq =$("#zjyScrq").val();
				var zjyXcrq =$("#zjyXcrq").val();
				var zjyZq =$("#zjyZq").val();
				var zjyZqdw =$("#zjyZqdw").val();
				var zjlfs =$("#jlfsSelect").val();
				var zjldj =$("#zjldj").val();
				
				if(zbjxlh == ""){
					$("#zbjxlh").focus();
					AlertUtil.showModalFooterMessage("编号不能为空!");
					flag=false;
					return false;
				}
				
				if(zjyZq == ""){
					$("#zjyZq").focus();
					AlertUtil.showModalFooterMessage("周期不能为空!");
					flag=false;
					return false;
				}
				
				if(zjyScrq == ""){
					$("#zjyScrq").focus();
					AlertUtil.showModalFooterMessage("校验日期不能为空!");
					flag=false;
					return false;
				}
				
				if(zjyXcrq == ""){
					$("#zjyXcrq").focus();
					AlertUtil.showModalFooterMessage("下次校验日期不能为空!");
					flag=false;
					return false;
				}
				var zjyScrqDate = new Date(zjyScrq); //校验日期
				var zjyXcrqDate = new Date(zjyXcrq); //下次校验日期
				if(zjyScrqDate.getTime() > zjyXcrqDate.getTime()){
					$("#zjyXcrq").focus();
					AlertUtil.showModalFooterMessage("下次校验日期必须大于校验日期!");
					flag=false;
					return false;
				}
				
				if(!flag){
					return false;
				}
				
				measurementToolsDetails.bjxlh=zbjxlh;
				measurementToolsDetails.bjh=dbjh;
				measurementToolsDetails.zwms=zzwms;
				measurementToolsDetails.ywms=zywms;
				measurementToolsDetails.gg=zgg;
				measurementToolsDetails.xh=zxh;
				measurementToolsDetails.bjbs=zbjbs;
				measurementToolsDetails.bz=zbz;
				measurementToolsDetails.jyScrq=zjyScrq;
				measurementToolsDetails.jyXcrq=zjyXcrq;
				measurementToolsDetails.jyZq=zjyZq;
				measurementToolsDetails.jyZqdw=zjyZqdw;
				measurementToolsDetails.jlfs=zjlfs;
				measurementToolsDetails.jldj=zjldj;
				
				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');//获取附件数据
				measurementToolsDetails.attachmentList=attachmentsObj.getAttachments();//加载附件数据
			
				measurementToolsDetailsList.push(measurementToolsDetails);
			
				}
			if(len>0){
					
				
		$("#toolcheck_open_modal_list>div[name='zbjs']").each(function(){
			var index=$(this).index(); 			 //当前行
			
			var measurementToolsDetails={};      //培训计划-培训课程人
			
			var bjxlh =$("input[name='bjxlh']").eq(index).val();
			var zwms =$("input[name='zwms']").eq(index).val();
			var ywms =$("input[name='ywms']").eq(index).val();
			var gg =$("input[name='gg']").eq(index).val();
			var xh =$("input[name='xh']").eq(index).val();
			var bjbs =$("input[name='bjbs']").eq(index).val();
			var bz =$("textarea[name='bz']").eq(index).val();
			var jyScrq =$("input[name='jyScrq']").eq(index).val();
			var jyXcrq =$("input[name='jyXcrq']").eq(index).val();
			var jyZq =$("input[name='jyZq']").eq(index).val();
			var jyZqdw =$("select[name='jyZqdw']").eq(index).val();
			var jlfs =$("select[name='jlfs']").eq(index).val();
			var jldj =$("input[name='jldj']").eq(index).val();
			if(bjxlh == ""){
				$("input[name='bjxlh']").eq(index).focus();
				AlertUtil.showModalFooterMessage("编号不能为空!");
				flag=false;
				return false;
			}
			if(jyZq == "" || jyZq == undefined){
				$("input[name='jyZq']").eq(index).focus();
				AlertUtil.showModalFooterMessage("周期不能为空!");
				flag=false;
				return false;
			}
			
			if(jyScrq == ""  || jyScrq == undefined){
				$("input[name='jyScrq']").eq(index).focus();
				AlertUtil.showModalFooterMessage("校验日期不能为空!");
				flag=false;
				return false;
			}
			
			if(jyXcrq == "" || jyXcrq == undefined){
				$("input[name='jyXcrq']").eq(index).focus();
				AlertUtil.showModalFooterMessage("下次校验日期不能为空!");
				flag=false;
				return false;
			}
			var jyScrqDate = new Date(jyScrq); //校验日期
			var jyXcrqDate = new Date(jyXcrq); //下次校验日期
			if(jyScrqDate.getTime() > jyXcrqDate.getTime()){
				$("input[name='jyXcrq']").eq(index).focus();
				AlertUtil.showModalFooterMessage("下次校验日期必须大于校验日期!");
				flag=false;
				return false;
			}
			
			if(!flag){
				return false;
			}
			
			measurementToolsDetails.bjxlh=bjxlh;
			measurementToolsDetails.bjh=dbjh;
			measurementToolsDetails.zwms=zwms;
			measurementToolsDetails.ywms=ywms;
			measurementToolsDetails.gg=gg;
			measurementToolsDetails.xh=xh;
			measurementToolsDetails.bjbs=bjbs;
			measurementToolsDetails.bz=bz;
			measurementToolsDetails.jyScrq=jyScrq;
			measurementToolsDetails.jyXcrq=jyXcrq;
			measurementToolsDetails.jyZq=jyZq;
			measurementToolsDetails.jyZqdw=jyZqdw;
			measurementToolsDetails.jlfs=jlfs;
			measurementToolsDetails.jldj=jldj;
			
			
			
			measurementToolsDetailsList.push(measurementToolsDetails);
		});
				}
		}
			
		if(!flag){
			return false;
		}
		
		
		var flagxlh= this_.isRepeat(measurementToolsDetailsList);
		if(flagxlh==true){
			AlertUtil.showModalFooterMessage("编号重复，请重新填写编号!");
			return false;
		}
		
		obj.measurementToolsDetailsList = measurementToolsDetailsList; //计量工具监控明细表list
		
		obj.bjxlh = dbjxlh; 
		obj.bjid = dbjh; 
		obj.dprtcode = $("#dprtcode").val(); //组织机构
	return obj;
	},
	/**
	 * 验证
	 */
	isRepeat : function(measurementToolsDetailsList){
		var hash = {};
		 
		for(var i in measurementToolsDetailsList) {
			if(hash[measurementToolsDetailsList[i].bjxlh])
				return true;
				hash[measurementToolsDetailsList[i].bjxlh] = true;
		}
		return false;
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
			url="/quality/toolcheck/save";//新增
		}else if(idnew!=""){
			url="/quality/toolcheck/update";//修改
			obj.id=idnew;
		}
		
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		startWait($("#toolcheck_open_modal"));
	   	AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#toolcheck_open_modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=toolcheck.encodePageParam();
	 			finishWait($("#toolcheck_open_modal"));
	 			AlertUtil.showMessage('提交成功!');
	 			$("#toolcheck_open_modal").modal("hide");
	 			toolcheck.decodePageParam();
	 		}
	   	  });
	  }});
	}
};

/**
 * 弹出窗验证销毁
 */
$("#toolcheck_open_modal").on("hidden.bs.modal", function() {
	$("#toolcheckForm").data('bootstrapValidator').destroy();
	$('#toolcheckForm').data('bootstrapValidator', null);
//	toolcheck_open_modal.validation();
});

