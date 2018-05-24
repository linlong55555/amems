var sex=[];
	sex[1]="男";
	sex[2]="女";
Post_Open_Modal = {
	id : "Post_Open_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	planeDatas:[],
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#form1').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	  xm: {
			            validators: {
			            	notEmpty: {message: '姓名不能为空'}
			            }
			      }, 
			      sqgwms: {
			            validators: {
			            	notEmpty: {message: '申请授权项目不能为空'}
			           }
				  }, 
				  shr: {
			            validators: {
			            	notEmpty: {message: '审核领导不能为空'}
			           }
				  }
		       }
		   });
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		
		$(".noteditable").attr("disabled",false);				  //标签不可编辑
		$(".required").show();   			    			      //隐藏必填标识*
		$(".colse").addClass("readonly-style");  				  //去掉文本框为白的的样式
		
		$("#djzt").val("");	  					  //
		$("#id").val("");	  					  //
		$("#sqgwid").val("");	  					  //
		$("#shrid").val("");	  					  //
		$("#"+this.id+" input[name='sqlx']").get(0).checked=true; 
		$("#"+this.id+" textarea").val("");	  					  //清空所有多行文本框数据
		$("#"+this.id+" input:text").val(""); 					  //清空所有文本框数据
		$("#smsm").val("本人在此申明以上内容属实");
		this.getAttachments(null,false,false);	 				  //初始化附件
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
	//按钮权限
	button: function(param){
		if(param==1){//新增
			 $("#sptg").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
			 $("#xgshr").hide();
		}else if(param==2){//批准
			 $("#sptg").hide();
			 $("#spbh").hide();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#xgshr").show();
		}else if(param==4){//批准
			 $("#sptg").show();
			 $("#spbh").show();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
			 $("#xgshr").hide();
		}
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
			userList = null;
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
				$("#"+obj+"bmdm").val(bmdm);
				
				$('#form1').data('bootstrapValidator')  
	 		      .updateStatus(""+obj+"", 'NOT_VALIDATED',null)  
	 		      .validateField(""+obj+""); 
			}
		});
	},
	//选择用户，根据所选用户返回用户信息，并反填
	openPersonelWin: function(){
		var this_=this;
		var param = {};
		param.dprtcode=$("#dprtcode").val();
		param.selected=$("#wxrydaid").val();
		param.callback = function(data){
			$("#wxryid").val(data.wxryid);
			this_.setData(data.id);
		},
		faculty_user.show(param);
		$("#userModal").modal("show");
	},
	//选择岗位
	openProjectWin: function(){
		var param = {};
		param.dprtcode=$("#dprtcode").val();
		param.selected=$("#sqgwid").val();
		param.callback = function(data){
			$("#sqgwid").val(data.id);
			$("#sqgwms").val(StringUtil.escapeStr(data.dgbh)+" "+StringUtil.escapeStr(data.dgmc));
			
			Post_Open_Modal_Trainingcourse.setData($("#wxrydaid").val(),$("#sqgwid").val());  //课程培训经历
			
			$('#form1').data('bootstrapValidator')  
		      .updateStatus("sqgwms", 'NOT_VALIDATED',null)  
		      .validateField("sqgwms"); 
		},
		post_list.show(param);
		$("#Post_Modal").modal("show");
	},
	//加载维修档案人员数据
	setData: function(id){
		var this_=this;
		startWait();
	   	 AjaxUtil.ajax({
		   	    url:basePath+"/quality/maintenancepersonnel/loaddetail",
		   	    type: "post",
		   	    contentType:"application/json;charset=utf-8",
		   	    dataType:"json",
		   	    data:JSON.stringify({
		   		    id : id
		   	    }),
		   		modal:$("#Post_Open_Modal"),
		   	    success:function(data){	   	    
		   	    	finishWait();
					// 回显表格数据
					this_.fillTableData(data);
		        }
	        }); 
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"17"
		});//显示附件
	},
	//初始化数据
	fillTableData: function(data){
		$("#xm").val(data.xm);
		$("#wxryid").val(data.wxryid);	
		$("#wxrydaid").val(data.id);
		$("#sex").val(sex[data.xb]);
		$("#rybh").val(data.rybh);
		$("#sr").val(formatUndefine(data.sr).substring(0,10));
		$("#cjgzrq").val(formatUndefine(data.cjgzrq).substring(0,10));
		$("#rzrq").val(formatUndefine(data.rzrq).substring(0,10));
		$("#szdw").val(data.szdw);
		$("#xl").val(data.xl);
		
		Post_Open_Modal_License.setHoldingData(data.maintenanceLicenses, this.id, "scroll_div");	//执照信息
		Post_Open_Modal_License.setActypeData(data.typeLicenses, this.id, "scroll_div"); 			//机型信息
		Post_Open_Modal_Educationorforeign.setData(data);  					//教育经历-外语水平
		Post_Open_Modal_Workexperience.setData(data.workExperiences);  		//工作经历
		
		Post_Open_Modal_Trainingcourse.setDataNofjjx(data.id,$("#sqgwid").val(),data.dprtcode,$("#id").val());  //课程培训经历
		Post_Open_Modal_MeTraining.setData(data.trainings);  				//全部培训经历
		this.getAttachments(data.id,false,false);	 				  		//初始化附件
		
		var  gwzw= '';
		//取最大值的现任职务
		if(data.posts != null && data.posts.length > 0){
			var index =data.posts;
			 var  maxks =index[0].ksrq;
			 var  maxjs =index[0].jsrq;
			 gwzw=index[0].gwzw;
			 for (var i = 0; i < index.length; i++) {
				 if(Date.parse(index[i].ksrq) > Date.parse(maxks) && Date.parse(index[i].jsrq) > Date.parse(maxjs)){
					 maxks = index[i].ksrq;
					 gwzw = index[i].gwzw;
				 }
			 }
		}
		 $("#gwzw").val(gwzw);
		
	}
};
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
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
