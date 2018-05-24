Assessment_Find_Modal = {
	id : "Assessment_Open_Modal",
	open: function(param){
		$("#glpgdid").html("<option value=''>暂无关联技术评估单No data</option>");
		$("#biaoshi").css("visibility","hidden");//隐藏标识
		ReferenceUtil.init({
			djid:'',//关联单据id
			ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : true,
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});//初始化参考文件
		 $("#historyList").hide();   			       //隐藏历史版本列表
		 $("#bbstate").hide();//隐藏版本标识
		 $("input[name='lylx']").attr("disabled",true);//来源radio按钮不可编辑，默认为其他
		 $("#lysm").show();							   //来源为其他时，显示来源说明
		 Assessment_Update_Modal.EmptiedData();    //清空数据
		 Assessment_Open_Modal.uploader(); 	    	//初始化结论附件
		 this.initInfo(param);						//初始化信息
	},
	/**
	 * 初始化数据
	 */
	initInfo : function(param){
		
		Assessment_Open_Modal.initFjjxOption();		    //初始化机型
		Assessment_Open_Modal.initwclx();      			//初始化完成类型
		Assessment_Open_Modal.inijjcd();      			//初始化紧急度
		Assessment_Open_Modal.onchangesyx();   			//改变本单位适用性
		Assessment_Open_Modal.initOrder();     			//加载下达指令
		this.initTechnical(param); 			  			//根据评估单id加载技术评估单数据
	},
	/**
	 * 加载下达指令数据
	 */
	initOrderData : function(param){
		
		var obj = {};
		obj.mainid = param;//技术评估单id
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
		 		url:basePath + "/project2/assessment/selectOrderList",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#Assessment_Open_Modal"),
		 		success:function(data){
		 			finishWait($("#Assessment_Open_Modal"));
		 			if(data.length > 0){
						for(var i = 0; i < data.length; i++){
							$('#orderDiv input:checkbox').each(function() {
								 var param = $(this).attr("name");
								 var zlxl=$("#"+param+"code").val(); //指令类型
								 if(zlxl==data[i].zlxl){
									 $("#Assessment_Open_Modal input[name='"+param+"']").get(0).checked=true;
									 if(param=="other"){
										 $("#"+param).val(data[i].sm);         //说明
									 }else{
										 $("#"+param+"id").val(data[i].zdryid);//userid
										 $("#"+param).val(data[i].paramsMap.displayName);//username
									 }
								 }
							});
						}
						Assessment_Open_Modal.initOrder();//重新刷新下达指令显示隐藏
					}
		 		}
		  });
		
	},
	/**
	 * 加载涉及部门
	 */
	initDepartmentList : function(param){
		var obj = {};
		obj.ywid = param;//技术评估单id
		
		startWait($("#Assessment_Open_Modal"));
	   	AjaxUtil.ajax({
		 		url:basePath + "/project2/assessment/selectDepartmentList",
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#Assessment_Open_Modal"),
		 		success:function(data){
		 			finishWait($("#Assessment_Open_Modal"));
		 			if(data.length > 0){
						var departmentList = data;// 分发部门
						if (departmentList != null && departmentList.length > 0) {
							var dprtcode = '';
							var dprtname = '';
							$.each(departmentList, function(index, row) {
								if(row.department!=null){
									dprtcode += row.department.id + ",";
									dprtname += row.department.dprtname + ",";
								}
							});
							dprtcode = dprtcode.substring(0, dprtcode.length - 1);
							dprtname = dprtname.substring(0, dprtname.length - 1);
							$("#sjbmms").html(dprtname);
							$("#sjbmid").val(dprtcode);
						}
						
					}
		 		}
		  });
	},
	/**
	 * 加载技术文件评估单数据
	 */
	initTechnical : function(param){
		var obj = {};
		obj.id = param;//技术文件评估单id
		$("#id").val(param);
		//根据技术文件评估单id查询技术文件评估单信息
		startWait($("#Assessment_Open_Modal"));
	   	AjaxUtil.ajax({
	 		url:basePath + "/project2/assessment/getByTechnicalId",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Assessment_Open_Modal"));
	 			if(data!=null){
	 				 //加载流程信息
	 				 introduce_process_info_class.show({    
	 					 	status:5,			  //1保存、2已评估、3已审核、4已批准、5审核驳回、6审批驳回、
	 						prepared:data.pgr.id,	  //编写人
	 						preparedDate:data.pgsj,	  //编写日期
	 						checkedOpinion:data.shyj, //审核意见
	 						checkedby:data.shr.id,    //审核人
	 						checkedDate:data.shsj,	  //审核日期
	 						checkedResult:data.shjl,  //审核结论
	 						approvedOpinion:data.pfyj,//批准意见
	 						approvedBy:data.pzr.id,   //批准人
	 						approvedDate:data.pfsj,   //批准日期
	 						approvedResult:data.pfjl  //批准结论
	 				 });
	 				$("#Assessment_Open_Modal_jx").val(data.jx);//机型
	 				$("#zt").val(DicAndEnumUtil.getEnumName('technicalStatusEnum',data.zt));//状态
	 				if(data.bb==null){
	 					$("#newbb").text(1); //当前版本
	 				}else{
	 					$("#newbb").text(data.bb); //当前版本
	 				}
	 				if(data.fBbid!=null){
	 					$("#bbstate").show();//显示版本标识
	 					$("#oldbb").text(data.paramsMap.oldbb); //前版本
	 					$("#oldbbid").val(data.fBbid); //前版本id
	 					$("#historyList").show();   			       		//隐藏历史版本列表
	 					Assessment_Open_Modal.initWebuiFindPopover();	   	//加载历史版本
	 				}
	 				 
	 				$("#djzt").val(data.zt);
	 				$("#dprtcode").val(data.dprtcode);
	 				$("#pgdh").val(data.pgdh);//评估单号
	 				var lylx=data.lylx;		  //来源类型
	 				$("input:radio[name='lylx'][value='"+lylx+"']").attr("checked",true); 
	 				$("#pgdh").attr("disabled",true);			  			//评估单号不可编辑
	 				$("#Assessment_Open_Modal_jx").attr("disabled",true);	//机型不可编辑
	 				$(".noteditable").attr("disabled",true);				//标签不可编辑
	 				$(".required").hide();   			    				//隐藏必填标识
	 				
	 			
	 				
	 				if(lylx==1){//档来源类型为1时，是从适航性资料来的数据
	 					$("#airworthinessDiv").show();   			  //隐藏适航性资料
	 					$("#lysm").css("visibility","hidden");	      //来源说明隐藏
	 					$("#wjbhbtn").attr("disabled",true);          //文件编号不可编辑
	 					$("#airworthinessDiv").show();   			  //隐藏适航性资料
	 					$("#evaluationDiv").show();   			      //隐藏关联技术评估单
	 				}else{
	 					$("#airworthinessDiv").hide();   			  //隐藏适航性资料
	 					$("#lysm").css("visibility","visible");	      //来源说明隐藏
	 					$("#lysm").val(formatUndefine(data.lysm));    //来源说明
	 					$("#airworthinessDiv").hide();   			  //隐藏适航性资料
	 					$("#evaluationDiv").hide();   			      //隐藏关联技术评估单
	 					$("#lysm").attr("disabled",true);  //机型可编辑
	 				}					
	 				
	 				//适航性资料数据
	 				$("#shxzlid").val(data.jswjid);   			  //技术文件id

	 				if(formatUndefine(data.paramsMap.glgljswjid)!=''){	
	 					$("#gljswjid").val(data.paramsMap.glgljswjid);     //关联技术文件id
	 				}
					$("#shxzljswjbh").val(formatUndefine(data.airworthiness.jswjbh));
					$("#shxzlbb").html(formatUndefine(data.airworthiness.bb));
					
					var url="";
					if(data.airworthinessAttachment?data.airworthinessAttachment.id:'' != ''){
						 url=formatUndefine(data.airworthinessAttachment?data.airworthinessAttachment.wbwjm:'')+"."+formatUndefine(data.airworthinessAttachment?data.airworthinessAttachment.hzm:'');
					}
					$("#ywjurl").html(url);
					$("#ywjid").val(data.airworthinessAttachment?data.airworthinessAttachment.id:'');
					
					$("#shxzlxzah").val(formatUndefine(data.airworthiness.xzah));
					$("#shxzljswjly").val(formatUndefine(data.airworthiness.jswjly));
					$("#shxzljswjlx").val(formatUndefine(data.airworthiness.jswjlx));
					$("#shxzlzdsj").val(formatUndefine(data.airworthiness.zdsj).substring(0,10));
					$("#shxzlzdsj").datepicker("update");
					$("#shxzljswjzt").val(formatUndefine(data.airworthiness.jswjzt));
					$("#shxzlbfrq").val(formatUndefine(data.airworthiness.bfrq).substring(0,10));
					$("#shxzlbfrq").datepicker("update");
					$("#shxzlsxrq").val(formatUndefine(data.airworthiness.sxrq).substring(0,10));
					$("#shxzlsxrq").datepicker("update");
					$("#shxzldqrq").val(formatUndefine(data.airworthiness.dqrq).substring(0,10));
					$("#shxzldqrq").datepicker("update");
					$("#gljswjid").val(formatUndefine(data.airworthiness.gljswjid));     //关联技术文件id
					//Assessment_Open_Modal.initGlpgdOption();//初始化关联技术评估单
					$("#glpgdid").val(data.glpgdid);//关联技术评估单id
 					if(data.glpgdid != null && data.airworthiness.gljswjid!=null ){
 						Assessment_Open_Modal.changeGljspgd(data.airworthiness.gljswjid,data.glpgdid);//修改关联评估单
 					}else{
 						Assessment_Open_Modal.changeGljspgd();//修改关联评估单
 					}
 					Assessment_Open_Modal.changeGljspgd();//修改关联评估单
//					if(data.glpgdid!=null && data.airworthiness.gljswjid!=null){ //当关联评估单id为null时
//						
//					}
					
						
					
					$("#zjh").val(data.zjh);				    //章节号
					$("#zjhms").val(data.fixChapter.zjh);       //章节号描述
					$("#pgqx").val(formatUndefine(data.pgqx).substring(0,10));//评估期限
					$("#pgqx").datepicker("update");
					$("#pgdzt").val(data.pgdzt);			    //技术评估单标题
					$("#bz").val(data.bz);//备注
					
					//初始化参考文件
					ReferenceUtil.init({
						djid:param,//关联单据id
						ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
						colOptionhead : false,
						dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
					});
					
					// 替换输入框、下拉框为文本
					StringUtil.replaceAsText("reference_list");
					$('#qbsyInput').attr("disabled","disabled");//全部适用
					$("input:radio[name='sylb_public']").attr("disabled","disabled");//全部适用
					
					//适用性列表
					applicable_settings.init({
						operationShow:false,
					});
					
					if(data.technicalAttached!=null){
						
					

					$("#technicalAttachedid").val(data.technicalAttached.id);//机型
					$("#dj").val(data.technicalAttached.dj);    //等级
					$("#jjcd").val(data.technicalAttached.jjcd);//紧急度
					$("#sjgz").val(data.technicalAttached.sjgz);//涉及改装
					$("#wjzy").val(data.technicalAttached.wjzy);//文件摘要
					$("#syfw_ywj").val(data.technicalAttached.syfwYwj);//源文件范围
					
					$("input:radio[name='syx'][value='"+data.technicalAttached.syx+"']").attr("checked",true); //本单位适用性
					if(data.technicalAttached.syx==0){//不适用
						$("#fsyyy").css("visibility","visible");//显示不适用原因
						$("#orderDiv").hide(); //隐藏下达指令
						$("#sylbDiv").hide();  //隐藏本单位适用类别
						$("#syfwDiv").hide();  //隐藏本单位适用范围
						$("#zlsjDiv").hide();  //隐藏指令时间
						$("#yjbfzlDiv").hide(); //隐藏指令时间
						$("#repeatid").hide(); //
						
						$("#fsyyy").val(data.technicalAttached.fsyyy);//不适用原因
					}else{
						$("#fsyyy").css("visibility","hidden");//隐藏不适用原因
						$("#orderDiv").show();//显示下达指令
						$("#sylbDiv").show(); //显示本单位适用类别
						$("#syfwDiv").show(); //显示本单位适用范围
						$("#zlsjDiv").show(); //显示指令时间
						$("#yjbfzlDiv").show(); //隐藏指令时间
						$("#repeatid").show(); //
						
						$("input:radio[name='sylb_public'][value='"+data.technicalAttached.sylb+"']").attr("checked",true); //本单位适用类别
						   
						//适用性列表
						applicable_settings.init({
							operationShow:false,
							dataList:data.syxszList,
							sylb:data.technicalAttached.sylb,
							dprtcode:$("#dprtcode").val(),// 组织机构
						});
						
						//全部适用就选中 并隐藏删除图标
						var fjzch = data.technicalAttached.fjzch;
						if(data.technicalAttached.sylb == 1){
							$('#qbsyLable').css("display", "inline");
						}else{
							$('#qbsyLable').css("display", "none");
						}
						$('#qbsyInput').attr("disabled","disabled");//全部适用
						$("input:radio[name='sylb_public']").attr("disabled","disabled");//全部适用
						if(fjzch == "00000" && data.technicalAttached.sylb == 1){
							$("#qbsyInput").prop('checked','checked'); 
						}else{
							$("#qbsyInput").removeAttr("checked");
						}
						
						
						$("#syfw_bdw").val(data.technicalAttached.syfwBdw);//本单位适用范围
						$("#yjbfzlsj").val(formatUndefine(data.yjbfzlsj).substring(0,10));//预计颁发指令时间
						$("#yjbfzlsj").datepicker("update");
						$("#yjbfzl").val(data.yjbfzl);									  //预计颁发指令
						//加载下达指令数据
						Assessment_Update_Modal.initOrderData(param);
					}
					
					$("#sxyq").val(data.sxyq);//时限要求
					$("#gbtj").val(data.technicalAttached.gbtj);//关闭条件
					
					$("input:radio[name='is_cfjc'][value='"+data.technicalAttached.isCfjc+"']").attr("checked",true); //重复检查
					$("input:radio[name='is_yxzlph'][value='"+data.technicalAttached.isYxzlph+"']").attr("checked",true); //影响重量平衡
					$("input:radio[name='is_mfhc'][value='"+data.technicalAttached.isMfhc+"']").attr("checked",true); //免费航材
					$("input:radio[name='is_zbhc'][value='"+data.technicalAttached.isZbhc+"']").attr("checked",true); //自备航材
					
					$("#ywjnr").val(data.technicalAttached.ywjnr);//源文件内容
					$("#bj").val(data.technicalAttached.bj);//背景
					$("#wjgxjx").val(data.technicalAttached.wjgxjx);//文件关系解析
					$("#xgwjzxztdc").val(data.technicalAttached.xgwjzxztdc);//相关文件执行状态调查
					$("#gcpgjl").val(data.technicalAttached.gcpgjl);//工程评估结论
					$("#wclx").val(formatUndefine(data.technicalAttached.wclx));
					//结论附件
					if(data.assessmentAttachment){
						$("#attachmentcheckbox").attr("checked", true);
						$("#scwjWbwjm").show();
						$("#attachmentcheckbox").attr("disabled",true);//结论附件不可编辑
						$("#scwjWbwjm").html("&nbsp;&nbsp;<a href=javascript:Assessment_Open_Modal.downfile('"+data.assessmentAttachment.id+"') >"+data.assessmentAttachment.wbwjm+"."+data.assessmentAttachment.hzm+"</a>&nbsp;</i>");
						$("#scfjId").val(data.assessmentAttachment.d);
						Assessment_Open_Modal.uploadObjjl.wbwjm=data.assessmentAttachment.wbwjm;
						Assessment_Open_Modal.uploadObjjl.hzm=data.assessmentAttachment.hzm;
					}
					}
					//涉及部门
					Assessment_Find_Modal.initDepartmentList(param);
					
					var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
					attachmentsObj.show({
						djid:param,
						fileHead : false,
						colOptionhead : false,
						fileType:"assessment"
					});//显示附件
				}
	 		}
		});
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#state").val("");//改版标识
		$("#id").val(""); //技术文件评估单id
		$("#technicalAttachedid").val(""); //技术文件评估单附表id
		$("#djzt").val(""); //状态
		
		$("#Assessment_Open_Modal input:text").val("");
		$("#Assessment_Open_Modal textarea").val("");
		$("#shxzlbb").text("");
		$("#glbb").text("");
		$("#Assessment_Open_Modal input[name='syx']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='sylb']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_cfjc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_yxzlph']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_mfhc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_zbhc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_tsgj']").get(0).checked=true;
		
		$('#orderDiv input:checkbox').each(function() {
			 var param = $(this).attr("name");
			 $("#Assessment_Open_Modal input[name='"+param+"']").get(0).checked=false;
			 if(param=="other"){
				 $("#"+param).val("");         //说明
			 }else{
				 $("#"+param+"id").val(userId);//userid
				 $("#"+param).val(displayName);//username
			 }
		});
	},
	/**
	 * 审核通过
	 */
	passed : function(){
		var url="/project2/assessment/audit/passed";//审核通过
		var str="审核通过";//审核通过
		Assessment_Find_Modal.subjectFrom(str,url);
	},
	/**
	 * 审核驳回
	 */
	turnDown : function(){
		var url="/project2/assessment/audit/turnDown";//审核驳回
		var str="审核驳回";
		Assessment_Find_Modal.subjectFrom(str,url);
	},
	/**
	 * 审核通过
	 */
	subjectFrom : function(str,url){
		var obj = {};
		
		var paramsMap = {};
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		var data=introduce_process_info_class.getData();//获取流程信息数据
		obj.id=$("#id").val();
		obj.shyj=data.shenhe;
		
		AlertUtil.showConfirmMessage("确定要"+str+"吗？", {callback: function(){
			 
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			finishWait($("#Assessment_Open_Modal"));
	 			AlertUtil.showMessage(""+str+"成功!");
	 			$("#Assessment_Open_Modal").modal("hide");
	 			hideBottom();
	 			decodePageParam();
	 		}
	   	  });
	   	 
	 }});
	   	 
	}
	
};

$(document).ready(function(){
	Navigation(menuCode, '查看技术评估单', 'Select Technical Assessment', 'GC-2-4 ', '林龙', '2017-08-01', '林龙', '2017-08-01');//加载导航栏
	
	$("#form input").attr("readonly",true);
	$("#form select").attr("readonly",true);
	$("#form textarea").attr("readonly",true);
	Assessment_Find_Modal.open($("#viewid").val());
	
});

