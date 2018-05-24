Defectkeep_Open_Modal = {
	id : "Defectkeep_Open_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	planeDatas:[],
	/**
	 * 字段验证
	 */ 
	validation : function(){
		validatorForm = $('#defectkeepForm').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	  fjzch: {
			            validators: {
			            	notEmpty: {message: '飞机注册号不能为空'}
			            }
			      }, 
			      sqr: {
			            validators: {
			            	notEmpty: {message: '申请人不能为空'}
			           }
				  }, 
				  sqrq: {
			            validators: {
			            	notEmpty: {message: '申请日期不能为空'}
			           }
				  }, 
				  bldh: {
					  validators: {
						  regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '保留单编号不能输入中文!' 	
		                  }
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
		$("#hcgj").hide();   			        			      //航材工具隐藏显示
		$(".colse").addClass("readonly-style");  				  //去掉文本框为白的的样式
		
		$(".bldh").attr("disabled",false);						  //保留单号不可编辑
		
		$("#djzt").val(""); 									  //清空old状态
		$("#id").val(""); 									  	  //清空id
		$("#sqrid").val(""); 									  //清空申请人id
		$("#"+this.id+" input:text").val(""); 					  //清空所有文本框数据
		$("#"+this.id+" textarea").val("");	  					  //清空所有多行文本框数据
		
		$("#addupdatebs").val("1");   			        		  //默认新增标识为1为新增
		$("#bs145").val("");   			        		 	  //145标识
		this.getAttachments(null,true,true);	 				  //初始化附件
		
	},
	//按钮权限
	button: function(param){
		if(param==1){//新增
			 $("#sptg").hide();
			 $("#spbh").hide();
			 $("#baocuns").show();
			 $("#tijiao").show();
		}else if(param==4){//批准
			 $("#sptg").show();
			 $("#spbh").show();
			 $("#baocuns").hide();
			 $("#tijiao").hide();
		}
	},
	//初始化器材清单
	Equipment_list_edit: function(id,colOptionhead){
		var this_=this;
		Equipment_list_edit.init({
			djid:id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:33,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单、21工单、31故障保留、32项目保留、33缺陷保留
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});
	},
	//初始化工具设备
	Tools_list_edit: function(id,colOptionhead){
		var this_=this;
		Tools_list_edit.init({
			djid:id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:33,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单、21工单、31故障保留、32项目保留、33缺陷保留
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	//初始化飞机注册号
	initFjzchOption: function(jgdm){
		var this_ = this;
		$("#"+this_.id+"_fjzch").html("");
		var planeDatas = acAndTypeUtil.getACReg135145List({DPRTCODE : jgdm});
		this_.planeDatas=planeDatas;
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#"+this_.id+"_fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
			$("#jx").val(planeDatas[0].FJJX);
		}else{
			$("#"+this_.id+"_fjzch").html("<option value=''>N/A</option>");
		}
	},
	//初始化机型
	onchangeFjzch: function(e){
		var this_ = this;
		var fjzch=$(e).val();
		var planeDatas=this_.planeDatas;
		for(var i = 0; i < planeDatas.length; i++){
			if(fjzch==planeDatas[i].FJZCH){
				$("#jx").val(planeDatas[i].FJJX);
			}
		}
		$("#gdid").val('');  //清空工单id
		$("#gd").val('');    //清空工单编号
	},
	//初始化机型
	onchangeFindFjzch: function(e){
		var this_ = this;
		var fjzch=$(e).val();
		var planeDatas=this_.planeDatas;
		for(var i = 0; i < planeDatas.length; i++){
			if(fjzch==planeDatas[i].FJZCH){
				$("#jx").val(planeDatas[i].FJJX);
			}
		}
		$("#gdid").val('');  //清空工单id
		$("#gd").val('');    //清空工单编号
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
				
				$('#defectkeepForm').data('bootstrapValidator')  
	 		      .updateStatus(""+obj+"", 'NOT_VALIDATED',null)  
	 		      .validateField(""+obj+""); 
			}
		});
	},
	//初始化来源编号数据
	initSourceData: function(obj){
//		var obj="";
//		var code135="produce:reservation:defect:main:052";
//		var code145="produce:reservation:defect:main:053";
//		if($.inArray(code135.toLowerCase(), buttonPermissions) == -1){
//		}else{
//			obj="135";
//		}
//		if($.inArray(code145.toLowerCase(), buttonPermissions) == -1){
//		}else{
//			obj="145";
//		}
		var gdid = $.trim($("#gdid").val());
		var addupdatebs = $.trim($("#addupdatebs").val());//新增修改标识
		var dprtcode = $("#dprtcode").val();
		var fjzch = $("#"+this.id+"_fjzch").val(); //飞机注册号
			var bs=''; //145标识
			if(addupdatebs==1){//新增
				bs=obj;//取session里的deptType
			}else{
				var bs145 = $("#bs145").val(); 
			
				if(bs145 == ''){
					bs=obj;//取session里的deptType
				}else{
					if((bs145==1 && obj==135) ){
						AlertUtil.showModalFooterMessage("您没有145工单选择权限!");
						return false;
					}
					if( (bs145==0 && obj==145)){
						AlertUtil.showModalFooterMessage("您没有135工单选择权限!");
						return false;
					}
					bs=obj;//取session里的deptType
				}
			}
			
			SelectSourceorderModal.show({
				selected:gdid,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				fjzch:fjzch,
				zt:"2,7,10",
				type:bs, //145标识
				parentWinId : "SelectSourceorderModal",
				callback: function(data){//回调函数
					if(data != null){
						$("#gdid").val(data.id);
						$("#gd").val(data.gdbh);
						if(data.bs==145){
							$("#bs145").val(1); 
						}else{
							$("#bs145").val(0); 
						}
					}else{
						$("#gdid").val('');
						$("#gd").val('');
						$("#bs145").val('');
					}
				}
			});
		
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
};
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('#sqrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#defectkeepForm').data('bootstrapValidator')  
.updateStatus('sqrq', 'NOT_VALIDATED',null)  
.validateField('sqrq');  
});

