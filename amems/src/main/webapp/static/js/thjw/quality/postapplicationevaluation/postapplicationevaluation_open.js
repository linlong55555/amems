/**
 * 评估编辑
 */
var Post_Open_Modal = {
	id : "Post_Open_Modal",
	sex : ["","男","女"],
	jxs : 0,
	obj : {},
	param: {
		id:'',
		editParam:3,//3:评估,4:修改评估
		modalHeadCN : '岗位评估',
		modalHeadENG : 'Post',
		callback:function(){}
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		this.vilidateOpenPri(function(obj){
			$("#"+this_.id).modal("show");
			AlertUtil.hideModalFooterMessage();
			this_.initInfo(obj);
		});
	},
	/**
	 * 验证弹窗是否可以打开
	 */
	vilidateOpenPri : function(callback){
		var this_ = this;
		this_.obj = {};
		var url = basePath + "/quality/post/application/getByPostApplicationId";
		this_.performAction(url, {id : this_.param.id}, false, '',function(obj){
			if(obj != null){
 				if(obj.zt != this_.param.editParam){
 					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 				}else{
 					this_.obj = obj;
 					this_.jxs = obj.paramsMap.jxs;
 					callback(obj);
 				}
			};
		});
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(obj){
		this.initModelHead();
		this.initDate();
		this.returnViewData(obj);
		this.initDisabled();
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	initDisabled : function(){
		if(this.param.editParam == 3){
			$(".pgjl", $("#"+this.id)).removeAttr("disabled");//标签不可编辑
		}else{
			$(".pgjl", $("#"+this.id)).attr("disabled",true);//标签不可编辑
		}
	},
	/**
	 * 初始化日期控件
	 */
	initDate : function(){
		$("#sqksrq", $("#"+this.id)).datepicker({
			format:'yyyy-mm-dd',
			autoclose : true,
			clearBtn : false
		});
		
		$("#sqjzrq", $("#"+this.id)).datepicker({
			format:'yyyy-mm-dd',
			autoclose : true,
			clearBtn : false
		});
	},
	/**
	 * 回显数据
	 */
	returnViewData : function(data){
		console.info(data.id);
		$("#sqsqdh", $("#"+this.id)).val(data.sqsqdh);//授权申请单号
		$("#post_main_panel_id").val(data.id);//授权申请id
		$("#ztms", $("#"+this.id)).val(DicAndEnumUtil.getEnumName('postStatusEnum',data.zt));//当前单据状态翻译
		$("#qtzz", $("#"+this.id)).val(data.qtzz);  										//其它执照
		$("#qtzzyxq", $("#"+this.id)).val(formatUndefine(data.qtzzyxq).substring(0,10)); 	//其他执照有效期
		$("input:radio[name='sqlx'][value='"+data.sqlx+"']", $("#"+this.id)).attr("checked",true); //申请类型
		$("#sqgwid", $("#"+this.id)).val(data.sqgwid);  				//其他执照有效期
		$("#sqgwms", $("#"+this.id)).val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgbh:''));  //其他执照有效期
		$("#dgmc", $("#"+this.id)).val(StringUtil.escapeStr(data.paramsMap?data.paramsMap.dgmc:''));  //其他执照有效期
		$("#sqr", $("#"+this.id)).val(StringUtil.escapeStr(data.whr?data.whr.username:'')+" "+StringUtil.escapeStr(data.whr?data.whr.realname:''));  					//申请人
		$("#sqsj", $("#"+this.id)).val(formatUndefine(data.sqsj).substring(0,10));  				//申请时间	
		$("#smsm", $("#"+this.id)).val("本人在此申明以上内容属实");
		$("#shr", $("#"+this.id)).val(StringUtil.escapeStr(data.shr?data.shr.username:'')+" "+StringUtil.escapeStr(data.shr?data.shr.realname:'')); //审核人 
		$("#shrq", $("#"+this.id)).val(indexOfTime(data.shsj)); //审核日期
		$("#shyj", $("#"+this.id)).html(StringUtil.escapeStr(data.shyj)); //审核意见
		
		$("#pgr", $("#"+this.id)).val(StringUtil.escapeStr(data.pgr?data.shr.username:'')+" "+StringUtil.escapeStr(data.pgr?data.shr.realname:'')); //评估人 
		$("#pgrq", $("#"+this.id)).val(indexOfTime(data.pgsj)); //评估日期
		
		$("#sqbz", $("#"+this.id)).val(data.sqbz);//备注
		
		//评估结论
		var pgjl = 1;
		if(data.pgjl != null){
			pgjl = data.pgjl;
		}
		$("input:radio[name='pgjl'][value='"+pgjl+"']", $("#"+this.id)).attr("checked",true);
		this.checkedPgjl();
		$("#sqksrq", $("#"+this.id)).val(indexOfTime(data.yxqKs));
		$("#sqjzrq", $("#"+this.id)).val(indexOfTime(data.yxqJs));
		//偏离评估
		var plpgjg = data.plpgjg?data.plpgjg:9;
		$("input:radio[name='plpgjg'][value='"+plpgjg+"']", $("#"+this.id)).attr("checked",true);
		$("#plkhcj", $("#"+this.id)).val(data.plkhcj);//偏离考核成绩
		
		$(".noteditable", $("#"+this.id)).attr("disabled",true);	//标签不可编辑
		
		this.initAttachments(data.sqsfjid, false, true, 'attachments_list_authorization_eidt');//授权书附件
		this.returnViewOther(data.sqrdaid);
		
		//初始化培训评估
		qualifcation_edit.init({
			wxrydaid : data.sqrdaid,//维修人员档案id
			id : data.id,
			gwid : data.sqgwid,
			dprtcode : data.dprtcode
		});
		
		//初始化人员资质评估
		training_evaluation_edit.init({
			wxrydaid : data.sqrdaid,//维修人员档案id
			id : data.id,
			gwid : data.sqgwid,
			dprtcode : data.dprtcode
		});
		
		if(this.jxs > 0){
			//初始化机型
			 plane_model_list_edit.init({
				id : 'evaluat_plane_model',
				djid:data.id,//关联单据id
				colOptionhead : true,
				dprtcode:data.dprtcode//默认登录人当前机构代码
			});
		}
		
	},
	/**
	 * 回显其它数据
	 */
	returnViewOther : function(id){
		var this_ = this;
		var url = basePath+"/quality/maintenancepersonnel/loaddetail";
		this_.performAction(url, {id : id}, false, '',function(data){
			this_.returnViewPersonnel(data);
			this_.initInclude(data);
		});
	},
	/**
	 * 回显人员数据
	 */
	returnViewPersonnel : function(data){
		$("#xm", $("#"+this.id)).val(data.xm);
		$("#wxryid", $("#"+this.id)).val(data.wxryid);	
		$("#wxrydaid", $("#"+this.id)).val(data.id);
		$("#sex", $("#"+this.id)).val(this.sex[data.xb]);
		$("#rybh", $("#"+this.id)).val(data.rybh);
		$("#sr", $("#"+this.id)).val(formatUndefine(data.sr).substring(0,10));
		$("#cjgzrq", $("#"+this.id)).val(formatUndefine(data.cjgzrq).substring(0,10));
		$("#rzrq", $("#"+this.id)).val(formatUndefine(data.rzrq).substring(0,10));
		$("#szdw", $("#"+this.id)).val(data.szdw);
		$("#xl", $("#"+this.id)).val(data.xl);
		
		var gwzw = '';
		//取最大值的现任职务
		if(data.posts != null && data.posts.length > 0){
			var index =data.posts;
			var maxks =index[0].ksrq;
			var maxjs =index[0].jsrq;
			gwzw = index[0].gwzw;
			for(var i = 0; i < index.length; i++) {
				if(Date.parse(index[i].ksrq) > Date.parse(maxks) && Date.parse(index[i].jsrq) > Date.parse(maxjs)){
					maxks = index[i].ksrq;
					gwzw = index[i].gwzw;
				}
			}
		}
		$("#gwzw", $("#"+this.id)).val(gwzw);
	},
	/**
	 * 初始化引入数据
	 */
	initInclude : function(data){
		Post_Open_Modal_License.setHoldingData(data.maintenanceLicenses, this.id, "scroll_div");	//执照信息
		Post_Open_Modal_License.setActypeData(data.typeLicenses, this.id, "scroll_div"); 			//机型信息
		Post_Open_Modal_Educationorforeign.setData(data);  					//教育经历-外语水平
		Post_Open_Modal_Workexperience.setData(data.workExperiences);  		//工作经历
	
		Post_Open_Modal_Trainingcourse.setDataNofjjx(data.id,$("#sqgwid").val(),data.dprtcode,$("#post_main_panel_id").val());  //课程培训经历
		Post_Open_Modal_MeTraining.setData(data.trainings);  				//全部培训经历
		this.initAttachments(data.id, false, false, 'attachments_list_cert_view');//证书附件信息
	},
	/**
	 * 初始化附件
	 */
	initAttachments: function(id, fileHead, colOptionhead, attachmentTopDiv){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents(attachmentTopDiv);
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"17"
		});
	},
	/**
	 * 选中合格与不合格
	 */
	checkedPgjl : function(){
		var pgjl = $("input[name='pgjl']:checked", $("#"+this.id)).val();
		$(".sqrq", $("#"+this.id)).hide();
		$(".jslb", $("#"+this.id)).hide();
		if(pgjl == 1){
			if(this.jxs > 0){
				$(".jslb", $("#"+this.id)).show();
			}else{
				$(".sqrq", $("#"+this.id)).show();
			}
		}
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	vilidateData : function(){//验证表单
		var this_ = this;
		//评估结论
		var pgjl = $("input[name='pgjl']:checked", $("#"+this.id)).val();
		if(pgjl == 1){
			var yxqKs = $("#sqksrq", $("#"+this.id)).val();
			var yxqJs = $("#sqjzrq", $("#"+this.id)).val();
			
			if(yxqKs != "" && yxqJs != null){
				if(TimeUtil.compareDate(yxqKs +" " + "00:00:00",yxqJs +" " + "00:00:00")){
					AlertUtil.showModalFooterMessage("授权开始日期不能大于授权截止日期!");
					return false;
				}
			}
			
		}
		return true;
	},
	/**
	 * 验证详情数据
	 */
	vilidateDetailData : function(){
		var this_ = this;
		
		if(this_.jxs > 0){
			//验证机型
			if(!plane_model_list_edit.isValid){
				return false;
			}
		}
		
		return true;
	},
	/**
	 * 保存数据
	 */
	setData: function(){
		var this_ = this;
		
		//验证主表数据
		if(!this.vilidateData()){
			return false;
		}
		
		var data = this.getDetailData();
		
		//验证详情数据
		if(!this.vilidateDetailData()){
			return false;
		}
		
		var paramsMap = {};
		paramsMap.operationType = this_.param.editParam;
		data.paramsMap = paramsMap;
		
		AlertUtil.showConfirmMessage("确定要提交吗？", {callback: function(){
		
			var url = basePath + "/quality/post/evaluation/updateEvaluation";
			this_.performAction(url, data, false, $("#"+this_.id),function(obj){
				if(this_.param.callback && typeof(this_.param.callback) == "function"){
					this_.param.callback(obj);
				}
			});
		
		}});
	},
	
	getDetailData : function(operationType){
		var this_ = this;
		var data = {};
		data.id = this_.param.id;
		data.sqsfjid = this_.obj.sqsfjid?this_.obj.sqsfjid:'';
		
		//评估结论
		data.pgjl = $("input[name='pgjl']:checked", $("#"+this.id)).val();
		if(data.pgjl == 1){
			data.yxqKs = $("#sqksrq", $("#"+this.id)).val();
			data.yxqJs = $("#sqjzrq", $("#"+this.id)).val();
			if(this_.jxs > 0){
				//设置机型
				data.postApplicationSQJXList = plane_model_list_edit.getData();
			}else{
				var postApplicationSQJXList = [];
				var infos ={};
				infos.id = "";
				infos.fjjx = "";
				infos.lb = "";
				infos.yxqKs = $("#sqksrq", $("#"+this.id)).val();
				infos.yxqJs = $("#sqjzrq", $("#"+this.id)).val();
				postApplicationSQJXList.push(infos);
				data.postApplicationSQJXList = postApplicationSQJXList;
			}
		}
		//偏离评估
		data.plpgjg = $("input[name='plpgjg']:checked", $("#"+this.id)).val();
		data.plkhcj = $("#plkhcj", $("#"+this.id)).val();
		//培训评估
		data.applicationPXPGList = qualifcation_edit.getData();
		//人员资质评估
		data.postApplicationRYZZPGList = training_evaluation_edit.getData();
		//获取授权书附件
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_authorization_eidt');
		data.attachmentList = attachmentsObj.getAttachments();
		
		return data;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	/**
	 * 展开或收缩人员信息
	 */
	toggleRyinfo : function(obj){
		if($(obj).find("i").hasClass("fa-angle-down")){
			$(".evaluationRyInfo ").hide(); 
			$(obj).empty();
			$(obj).html("展开人员信息<i class='fa fa-angle-up margin-left-5'></i>");
		}else{
			$(".evaluationRyInfo ").show(); 
			$(obj).empty();
			$(obj).html("收起人员信息<i class='fa fa-angle-down margin-left-5'></i>"); 
		}
	},
	/**
	 * 展开或收缩
	 */
	toggleBaseinfo : function(obj){
		if($(obj).find("i").hasClass("fa-angle-down")){
			$(".evaluationBaseInfo").hide(); 
			$(obj).empty();
			$(obj).html("展开审核信息<i class='fa fa-angle-up margin-left-5'></i>");
		}else{
			$(".evaluationBaseInfo").show(); 
			$(obj).empty();
			$(obj).html("收起审核信息<i class='fa fa-angle-down margin-left-5'></i>"); 
		}
	},
	/**
	 * 执行请求
	 * url 请求路径
	 * param 请求参数
	 * async true异步，false同步
	 * modal 遮罩,防重复提交
	 * callback 回调函数
	 */
	performAction : function(url, param, async, modal, callback){
		var this_ = this;
		startWait(modal);
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			async: async,
			modal:modal,
			success:function(data){
				finishWait(modal);
				if(typeof(callback)=="function"){
					callback(data); 
				}
			}
		});
	},
	//只能输入数字
	clearNoNumber : function(obj){
		//先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){
		//先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     
	     var str = obj.value.split(".");
	     if(str.length > 1){
	    	 if(str[1].length > 2){
	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	}
}
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