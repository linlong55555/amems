Assessment_Update_Assessor_Modal = {
	id : "Assessment_Open_Modal",
	open: function(param){
//		$("#glpgdid").html("<option value=''>暂无关联技术评估单No data</option>");
		$("#biaoshi").css("visibility","hidden");//隐藏标识
		ReferenceUtilView.init({
			djid:'',//关联单据id
			ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : true,
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});//初始化参考文件
		ReferenceUtil.init({
			djid:'',//关联单据id
			ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : true,
			dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
		});//初始化参考文件
		 Assessment_Open_Modal.button(6);			   //技术评估单新增权限
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		 $("input[name='lylx']").attr("disabled",true);//来源radio按钮不可编辑，默认为其他
		 $("#lysm").show();							   //来源为其他时，显示来源说明
		 $("#historyList").hide();   			       //隐藏历史版本列表
		 $("#pgdh").attr("placeholder","");	
		 
		 
		 $("#ReferenceUtilView").show();	
		 $("#ReferenceUtil").hide();	
		 
		 $(".xgpgrid").hide();	
		 $("#xgpgrids").attr("style","width:60%;");
		 $("#xgpgrid1").attr("class","col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group");//来源radio按钮不可编辑，默认为其他
		 $("#xgpgrid2").attr("class","col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group");//来源radio按钮不可编辑，默认为其他
		 
		 
		 
		 
		 $("#bbstate").hide();//隐藏版本标识
		 $("#modalName").html("修改评估人");
		 $("#modalEname").html("Edit Assessor");
		 
		 Assessment_Open_Modal.validation(); 	//初始化验证
		 this.EmptiedData();    //清空数据
		 Assessment_Open_Modal.uploader(); 	    //初始化结论附件
		 this.initInfo(param);				    //初始化信息
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
		var this_ = this;
		var falg=true;
		var obj = {};
		$("#id").val(param);
		obj.id = param;//技术文件评估单id
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
	 				if(data.zt==2){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 					falg=false;
	 					return false;
	 				}
	 				$("#pgdh").val(data.pgdh);//评估单号
	 				$("#djzt").val(data.zt);
	 				$("#Assessment_Open_Modal_jx").val(data.jx);//机型
	 				if(data.bb==null){
	 					$("#newbb").text(1); //当前版本
	 				}else{
	 					$("#newbb").text(data.bb); //当前版本
	 				}
	 				if(data.fBbid!=null){
	 					$("#bbstate").show();//显示版本标识
	 					$("#oldbb").text(data.paramsMap.oldbb); //前版本
	 					$("#oldbbid").val(data.fBbid); //前版本id
	 					$("#historyList").show();   			       //显示历史版本列表
	 					Assessment_Open_Modal.initWebuiPopover();	   //加载历史版本
	 				}
	 				 //加载流程信息
	 				 introduce_process_info_class.show({    
	 						status:data.zt,			  //1保存、2已评估、3已审核、4已批准、5审核驳回、6审批驳回、
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
	 				
	 				//适航性资料数据
		 				$("#shxzlid").val(data.jswjid);   			 		 //技术文件id
		 				
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
	 				 
	 				var lylx=data.lylx;		  //来源类型
	 				$("input:radio[name='lylx'][value='"+lylx+"']").attr("checked",true); 
	 				if(lylx==1){//档来源类型为1时，是从适航性资料来的数据
	 					$("#lysm").css("visibility","hidden");	      //来源说明隐藏
	 					//$("#wjbhbtn").attr("disabled",true);          //文件编号不可编辑
	 					$("#airworthinessDiv").show();   			  //隐藏适航性资料
	 					$("#evaluationDiv").show();   			      //隐藏关联技术评估单
	 					$("#wxrybtn").hide();   			          //隐藏文件编号选择
	 					if($("#pgdh").val()==null||$("#pgdh").val()==""){ //当评估单号为null时可以编辑
	 						//$("#pgdh").attr("disabled",false);			  //评估单号可编辑
	 					}else{
	 						//$("#pgdh").attr("disabled",true);			  //评估单号可编辑
	 					}
	 					//$("#Assessment_Open_Modal_jx").attr("disabled",true);	//机型不可编辑
	 					$("#gljswjid").val(formatUndefine(data.airworthiness.gljswjid));     //关联技术文件id
	 					//Assessment_Open_Modal.initGlpgdOption();//初始化关联技术评估单
	 					$("#glpgdid").val(data.glpgdid);//关联技术评估单id
	 					Assessment_Open_Modal.changeGljspgd();//修改关联评估单
//						if(data.glpgdid!=null && data.airworthiness.gljswjid!=null){ //当关联评估单id为null时
//							
//						}
	 				}else{
	 					$("#lysm").css("visibility","visible");	      //来源说明隐藏
	 					$("#lysm").val(formatUndefine(data.lysm));    //来源说明
	 					$("#airworthinessDiv").hide();   			  //隐藏适航性资料
	 					$("#evaluationDiv").hide();   			      //隐藏关联技术评估单
	 					//$("#pgdh").attr("disabled",true);			  //评估单号不可编辑
	 					//$("#Assessment_Open_Modal_jx").attr("disabled",false);  //机型可编辑
	 				}					
				
					if(data.technicalAttached!=null){
						$("#technicalAttachedid").val(data.technicalAttached.id);//技术文件id
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
							
							$("#syfw_bdw").val(data.technicalAttached.syfwBdw);//本单位适用范围
							$("#yjbfzlsj").val(formatUndefine(data.yjbfzlsj).substring(0,10));//预计颁发指令时间
							$("#yjbfzlsj").datepicker("update");
							$("#yjbfzl").val(data.yjbfzl);									  //预计颁发指令
							//加载下达指令数据
							this_.initOrderData(param);
						}
						
					
						$("#gbtj").val(data.technicalAttached.gbtj);//关闭条件
						
						$("input:radio[name='is_cfjc'][value='"+data.technicalAttached.isCfjc+"']").attr("checked",true); //重复检查
						$("input:radio[name='is_mfhc'][value='"+data.technicalAttached.isMfhc+"']").attr("checked",true); //免费航材
						$("input:radio[name='is_zbhc'][value='"+data.technicalAttached.isZbhc+"']").attr("checked",true); //自备航材
						
						$("#ywjnr").val(data.technicalAttached.ywjnr);//源文件内容
						$("#bj").val(data.technicalAttached.bj);//背景
						$("#wjgxjx").val(data.technicalAttached.wjgxjx);//文件关系解析
						$("#xgwjzxztdc").val(data.technicalAttached.xgwjzxztdc);//相关文件执行状态调查
						$("#gcpgjl").val(data.technicalAttached.gcpgjl);//工程评估结论
						
						$("#wclx").val(formatUndefine(data.technicalAttached.wclx));
					}
					$("#sxyq").val(data.sxyq);//时限要求
				
					$("#zjh").val(data.zjh);				    //章节号
					$("#zjhms").val(data.fixChapter.zjh);       //章节号描述
					$("#pgqx").val(formatUndefine(data.pgqx).substring(0,10));//评估期限
					$("#pgqx").datepicker("update");
					$("#pgdzt").val(data.pgdzt);			    //技术评估单标题
					$("#bz").val(data.bz);//备注
					
					
//					//初始化参考文件
					ReferenceUtil.init({
						djid:param,//关联单据id
						ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
						colOptionhead : false,
						dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
					});
					//初始化参考文件
					ReferenceUtilView.init({
						djid:param,//关联单据id
						ywlx:9,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
						colOptionhead : false,
						dprtcode:$("#dprtcode").val()//默认登录人当前机构代码
					});
					//结论附件
					if(data.assessmentAttachment){
						$("#attachmentcheckbox").attr("checked", true);
						$("#scwjWbwjm").show();
						$("#attachmentcheckbox").attr("disabled",true);//结论附件不可编辑
						$("#scwjWbwjm").html("&nbsp;&nbsp;<a href=javascript:Assessment_Open_Modal.downfile('"+data.assessmentAttachment.id+"') >"+data.assessmentAttachment.wbwjm+"."+data.assessmentAttachment.hzm+"</a>&nbsp;");
						$("#scfjId").val(data.assessmentAttachment.d);
						Assessment_Open_Modal.uploadObjjl.wbwjm=data.assessmentAttachment.wbwjm;
						Assessment_Open_Modal.uploadObjjl.hzm=data.assessmentAttachment.hzm;
					}
					
					//涉及部门
					this_.initDepartmentList(param);
					
					var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
					attachmentsObj.show({
						djid:param,
						fileHead : false,
						colOptionhead : false,
						fileType:"assessment"
					});//显示附件
					
					$(".noteditable").attr("disabled",true);			//标签不可编辑
					$("#pgdh").attr("disabled",true);			  			//评估单号不可编辑
					$("#Assessment_Open_Modal_jx").attr("disabled",true);	//机型不可编辑
					$("#lysm").attr("disabled",true);  						//来源说明不可编辑
					
	 				$(".required").hide();   			    			//隐藏必填标识
	 				$('#Assessment_Open_Modal').on('shown.bs.modal', function () {
	 					// 替换输入框、下拉框为文本
 						StringUtil.replaceAsText("reference_list_view");
	 				});
	 				$(".colse").removeClass("readonly-style");  			//去掉文本框为白的的样式
	 				$(".colse-div").addClass("div-readonly-style");  		//去掉文本框为白的的样式
	 				$("#sjbmms").attr("ondblclick","");
	 				
	 				
	 				$(".pgclass").show();   			    			//隐藏必填标识
	 				
	 				
	 				$("#ypgr").val(data.pgr.id);//原评估人
//	 				$("#pgrid").val(data.pgrid);//备注
//	 				$("#pgrbmdm").val(data.pgbmid);//备注
				}
	 		}
		});
	   	if(falg){
	   		$("#"+this_.id+"").modal("show");//显示窗口
	   	}
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#glpgdid").val("");
		$("#state").val("");//改版标识
		$("#newbb").text(1);//当前id
		$("#oldbb").text("");//前版本版本
		$("#oldbbid").val("");//前版本id
		$("#id").val(""); //技术文件评估单id
		$("#pgrid").val(""); //技术文件评估单id
		$("#pgrbmdm").val(""); //技术文件评估单id
		$("#technicalAttachedid").val(""); //技术文件评估单附表id
		$("#djzt").val(""); //状态
		
		 $("#attachmentcheckbox").attr("checked",false);
	     $("#fileuploaderSingle").hide();
		 $("#scwjWbwjm").hide();
		 $("#attachmentcheckbox").attr("disabled",false);//结论附件不可编辑
		 Assessment_Open_Modal.uploadObjjl = {};
		
		$("#Assessment_Open_Modal input:text").val("");
		$("#Assessment_Open_Modal textarea").val("");
		$("#shxzlbb").text("");
		$("#glbb").text("");
		$("#Assessment_Open_Modal input[name='lylx']").get(1).checked=true;
		$("#lysm").css("visibility","visible");				//显示不适用原因
		$("#wjbhbtn").attr("disabled",false);			    //文件编号可以选择
		$("#Assessment_Open_Modal input[name='syx']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_cfjc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_mfhc']").get(0).checked=true;
		$("#Assessment_Open_Modal input[name='is_zbhc']").get(0).checked=true;
		
		$('#orderDiv input:checkbox').each(function() {
			 var param = $(this).attr("name");
			 $("#Assessment_Open_Modal input[name='"+param+"']").get(0).checked=false;
			 if(param=="other"){
				 $("#"+param).val("");         //说明
			 }else{
			 }
		});
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:null,
			fileHead : true,
			colOptionhead : true,
			fileType:"assessment"
		});//显示附件
	},
	/**
	 * 保存
	 */
	save : function(){
		var paramsMap = {};
		var obj = {};
		var idnew=$("#id").val();
		var pgrid=$("#pgrid").val();
		var pgbmid=$("#pgrbmdm").val();
		var jx=$("#Assessment_Open_Modal_jx").val();				//关联评估单id
		var shxzlid=$("#shxzlid").val();	//适航性资料id
		paramsMap.currentZt = $("#djzt").val(); //状态
		obj.paramsMap = paramsMap;
		
		obj.id=idnew;
		obj.pgrid=pgrid;
		obj.pgbmid=pgbmid;
		obj.jx=jx;
		obj.jswjid = shxzlid;
		obj.dprtcode = $("#dprtcode").val(); //组织机构
		if(pgrid==''){
			AlertUtil.showModalFooterMessage('评估人不能为空!');
			return false;
		}
		
		 startWait($("#Assessment_Open_Modal"));
	   	 AjaxUtil.ajax({
	 		url:basePath+"/project2/assessment/updateAssessor",//修改评估人
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#Assessment_Open_Modal"),
	 		success:function(data){
	 			id = data;	
	 			pageParam=encodePageParam();
	 			finishWait($("#Assessment_Open_Modal"));
	 			AlertUtil.showMessage('保存成功!');
	 			$("#Assessment_Open_Modal").modal("hide");
	 			hideBottom();
	 			decodePageParam();
	 		}
	   	  });
	},
};