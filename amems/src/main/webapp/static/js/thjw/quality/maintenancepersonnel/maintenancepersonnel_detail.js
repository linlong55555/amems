	

	$(function(){
		// 初始化导航栏
		initNavigation();
		// 初始化事件
		initEvent();
		// 初始化时间控件
		initDateWidget();
		// 显示表格新增删除按钮
		showTableEditButton();
		// 初始化验证
		initValidate();
		// 加载维修人员档案数据
		loadDetail();
		// 权限控制
		//maintenanPrivilege();
		
	});
	/**
	 * 权限控制
	 */
	function maintenanPrivilege(){
		//0:基本信息,1:岗位信息,2:执照信息,3:维修等级,4:培训记录,5:个人奖惩,6:诚信记录,7:附件
		var qxList = ["quality:maintenancepersonnel:06","quality:maintenancepersonnel:07",
		              "quality:maintenancepersonnel:08","quality:maintenancepersonnel:09",
		              "quality:maintenancepersonnel:10","quality:maintenancepersonnel:11",
		              "quality:maintenancepersonnel:12"];
		//基本信息权限控制
		if(!checkBtnPermissions(qxList[0])){ 
			$("#archive_basic").find("input").attr("readonly",true);
			$("#archive_basic").find("input[type='radio']").attr("disabled",true);
			$("#archive_basic").find("select").attr("disabled",true);
			$("#archive_basic").find("textarea").attr("readonly",true);
			$("#sr").attr("disabled",true);
			$("#archive_basic").find("input").removeClass ("readonly-style");
			$("#archive_basic").find("input").unbind("click");
			$("#xmButt ").attr("onclick",false);
			$("#zpButt ").attr("onclick",false);
			$("#xm ").attr("ondblclick",false);
			$("#cjgzqk ").find("input").attr("disabled",true);
			$("#radioChange1").attr("onclick",false);
			$("#radioChange2").attr("onclick",false);
			//教育经历
			$("#basic_education_table").find("th").eq(0).hide();
			if($("#basic_education_list").find("tr").eq(0).find("td").length <= 1){
				$("#basic_education_list").find("tr").eq(0).find("td").eq(0).attr("colspan",5);
			}else{
				$("#basic_education_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//外语水平
			$("#basic_foreign_language_table").find("th").eq(0).hide();
			if($("#basic_foreign_language_list").find("tr").eq(0).find("td").length <= 1){
				$("#basic_foreign_language_list").find("tr").eq(0).find("td").eq(0).attr("colspan",3);
			}else{
				$("#basic_foreign_language_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//聘任职称
			$("#basic_title_table").find("th").eq(0).hide();
			if($("#basic_title_list").find("tr").eq(0).find("td").length <= 1){
				$("#basic_title_list").find("tr").eq(0).find("td").eq(0).attr("colspan",6);
			}else{
				$("#basic_title_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//工作履历
			$("#basic_work_experience_table").find("th").eq(0).hide();
			if($("#basic_work_experience_list").find("tr").eq(0).find("td").length <= 1){
				$("#basic_work_experience_list").find("tr").eq(0).find("td").eq(0).attr("colspan",5);
			}else{
				$("#basic_work_experience_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
		}
		//岗位信息权限控制
		if(!checkBtnPermissions(qxList[1])){
			//岗位/职务
			$("#post_post_table").find("th").eq(0).hide();
			if($("#post_post_list").find("tr").eq(0).find("td").length <= 1){
				$("#post_post_list").find("tr").eq(0).find("td").eq(0).attr("colspan",6);
			}else{
				$("#post_post_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//技术履历
			$("#post_technical_table").find("th").eq(0).hide();
			if($("#post_technical_list").find("tr").eq(0).find("td").length <= 1){
				$("#post_technical_list").find("tr").eq(0).find("td").eq(0).attr("colspan",7);
			}else{
				$("#post_technical_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			
		}
		//执照信息权限控制
		if(!checkBtnPermissions(qxList[2])){
			//维修执照
			$("#maintenance_license_table").find("th").eq(0).hide();
			if($("#maintenance_license_list").find("tr").eq(0).find("td").length <= 1){
				$("#maintenance_license_list").find("tr").eq(0).find("td").eq(0).attr("colspan",6);
			}else{
				$("#maintenance_license_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//机型信息
			$("#type_license_table").find("th").eq(0).hide();
			if($("#type_license_list").find("tr").eq(0).find("td").length <= 1){
				$("#type_license_list").find("tr").eq(0).find("td").eq(0).attr("colspan",7);
			}else{
				$("#type_license_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			
		}
		//维修等级权限控制
		if(!checkBtnPermissions(qxList[3])){
			//维修技术等级升级记录
			$("#maintenance_level_table").find("th").eq(0).hide();
			if($("#maintenance_level_list").find("tr").eq(0).find("td").length <= 1){
				$("#maintenance_level_list").find("tr").eq(0).find("td").eq(0).attr("colspan",8);
			}else{
				$("#maintenance_level_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			
		}
		//培训记录权限控制
		if(!checkBtnPermissions(qxList[4])){
			//培训记录
			$("#training_records_table").find("th").eq(0).hide();
			if($("#training_records_list").find("tr").eq(0).find("td").length <= 1){
				$("#training_records_list").find("tr").eq(0).find("td").eq(0).attr("colspan",15);
			}else{
				$("#training_records_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			
		}
		//个人奖惩权限控制
		if(!checkBtnPermissions(qxList[5])){
			//业务考核记录
			$("#award_business_table").find("th").eq(0).hide();
			if($("#award_business_list").find("tr").eq(0).find("td").length <= 1){
				$("#award_business_list").find("tr").eq(0).find("td").eq(0).attr("colspan",7);
			}else{
				$("#award_business_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//学术成就
			$("#award_scholarship_table").find("th").eq(0).hide();
			if($("#award_scholarship_list").find("tr").eq(0).find("td").length <= 1){
				$("#award_scholarship_list").find("tr").eq(0).find("td").eq(0).attr("colspan",4);
			}else{
				$("#award_scholarship_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//嘉奖记录
			$("#award_citation_table").find("th").eq(0).hide();
			if($("#award_citation_list").find("tr").eq(0).find("td").length <= 1){
				$("#award_citation_list").find("tr").eq(0).find("td").eq(0).attr("colspan",4);
			}else{
				$("#award_citation_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//事故征候情况
			$("#award_incident_table").find("th").eq(0).hide();
			if($("#award_incident_list").find("tr").eq(0).find("td").length <= 1){
				$("#award_incident_list").find("tr").eq(0).find("td").eq(0).attr("colspan",6);
			}else{
				$("#award_incident_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
		}
		//诚信记录权限控制
		if(!checkBtnPermissions(qxList[6])){
			//负有责任的不安全事件
			$("#reputation_unsafe_table").find("th").eq(0).hide();
			if($("#reputation_unsafe_list").find("tr").eq(0).find("td").length <= 1){
				$("#reputation_unsafe_list").find("tr").eq(0).find("td").eq(0).attr("colspan",9);
			}else{
				$("#reputation_unsafe_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
			//不诚信行为
			$("#reputation_dishonest_table").find("th").eq(0).hide();
			if($("#reputation_dishonest_list").find("tr").eq(0).find("td").length <= 1){
				$("#reputation_dishonest_list").find("tr").eq(0).find("td").eq(0).attr("colspan",9);
			}else{
				$("#reputation_dishonest_list").find("tr").each(function () {
					$(this).find("td").eq(0).hide();
				});
			}
		}
	}
	
	/**
	 * 初始化事件
	 */
	function initEvent(){
		$("#detail_div>ul>li>a").click(function(){
			$("[name='rybh_feedback']").text($("#rybh").val());
			$("[name='xm_feedback']").text($("#xm").val());
			$("[name='szdw_feedback']").text($("#szdw").val());
		});
	}
	
	
	/**
	 * 初始化验证
	 */
	function initValidate(){
		$('#basic_form').bootstrapValidator({
	        message: '数据不合法',
	        excluded: [':disabled'],
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	rybh: {
	                validators: {
	                    notEmpty: {
	                        message: '人员编号不能为空'
	                    }
	                }
	            },
	            xm: {
	                validators: {
	                    notEmpty: {
	                        message: '姓名不能为空'
	                    }
	                }
	            },
//	            cjgzrq: {
//	                validators: {
//	                    notEmpty: {
//	                        message: '参加工作日期不能为空'
//	                    }
//	                }
//	            },
//	            rzrq: {
//	                validators: {
//	                    notEmpty: {
//	                        message: '入职日期不能为空'
//	                    },
//	                    callback: {
//	                        message: '入职日期不可小于参加工作日期',
//	                        callback: function(value, validator) {
//	                        	var rzrq = $("#rzrq").val();
//	                    		var cjgzrq = $("#cjgzrq").val();
//	                    		if(compareDate(cjgzrq, rzrq) > 0){
//	                    			return false;
//	                    		}
//                                return true;
//	                        }
//	                    }
//	                }
//	            },
	            lzrq: {
	            	validators: {
	                	callback: {
	                        message: '离职日期不能为空',
	                        callback: function(value, validator) {
	                            if($("[name=zzzt]:radio:checked").val() == "0" && !value){
	                                return false;
	                            }
                                return true;
	                        }
	                    },
	                    callback: {
	                        message: '离职日期不可小于入职日期',
	                        callback: function(value, validator) {
	                        	var rzrq = $("#rzrq").val();
	                    		var lzrq = $("#lzrq").val();
	                    		if(lzrq && compareDate(rzrq, lzrq) > 0){
	                    			return false;
	                    		}
                                return true;
	                        }
	                    }
	                }
	            },
	            sfz: {
	                validators: {
	                	regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '身份证输入不合法'
	                    }
	                }
	            },
	            yxdz: {
	                validators: {
	                	regexp: {
	                        regexp: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ,
	                        message: '邮箱输入不合法'
	                    }
	                }
	            },
	            yzbm: {
	                validators: {
	                	regexp: {
	                        regexp: /^[1-9][0-9]{5}$/,
	                        message: '邮政编码输入不合法'
	                    }
	                }
	            },
	            lxdh: {
	                validators: {
	                	callback: {
	                        message: '联系电话输入不合法',
	                        callback: function(value, validator) {
	                        	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	                            var isMob=/^1[34578]\d{9}$/;
	                            if(!value||isMob.test(value)||isPhone.test(value)){
	                                return true;
	                            }
	                            else{
	                                return false;
	                            }
	                        }
	                    }
	                }
	            },
	        }
	    });
	}
	
	/**
	 * 初始化导航栏
	 */
	function initNavigation(){
		
		var type = $("#type").val();
		if(type == 1){
			Navigation(menuCode,'新增','Add');
		}else if(type == 2){
			Navigation(menuCode,'修改','Edit');
		}else if(type == 3){
			Navigation(menuCode,'查看','View');
		}
	}
	
	/**
	 * 初始化时间控件
	 */
	function initDateWidget(){
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#basic_form').data('bootstrapValidator')  
			     .updateStatus('cjgzrq', 'NOT_VALIDATED',null)  
			     .updateStatus('rzrq', 'NOT_VALIDATED',null)
			  	 .updateStatus('lzrq', 'NOT_VALIDATED',null);  
	    });
	}
	
	/**
	 * 返回主页面
	 */
	function goToMainPage(){
		window.location = basePath+'/quality/maintenancepersonnel/manage?pageParam='+pageParam;
	}
	
	/**
	 *  显示表格新增删除按钮
	 */
	function showTableEditButton(){
		if($("#type").val() == "1" || $("#type").val() == "2"){
			$(".editTable").show();
			$(".non-choice>td").each(function(){
				var colspan = parseInt($(this).attr("colspan"));			
				$(this).attr("colspan", colspan + 1);
			});
		}else{
			$("#xm_div").html('<input class="form-control" disabled="disabled" type="text" id="xm" value="'+($("#xm").val()||"")+'">');
			$("#basic_div input").attr("real","disabled");
			$("#basic_div select").attr("disabled","disabled");
			$("#basic_div textarea").attr("disabled","disabled");
			$("#rybhmark").hide();
			$("#xmmark").hide();
			$("#cjgzrqmark").hide();
			$("#rzrqmark").hide();
			$("#lzrqmark").hide();
		}
	}
	
	/**
	 * 保存
	 */
	function save(){
		
		if(!checkBtnPermissions('quality:maintenancepersonnel:06')){ 
			AlertUtil.showErrorMessage("无基本信息权限，请联系管理员添加权限后再操作！");
			finishWait();
			return false;
		}
		
		$('#basic_form').data('bootstrapValidator').validate();
		if(!$('#basic_form').data('bootstrapValidator').isValid()){
			$("#tab_archive_basic").click();
			AlertUtil.showErrorMessage("请根据提示输入正确的信息!");
			finishWait();
			return false;
		}
		var params = gatherParam();
		// 提交数据
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/quality/maintenancepersonnel/save",
			type:"post",
			data:JSON.stringify(params),
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			success:function(id){
				finishWait();
				$("#personnelId").val(id);
				loadDetail();
				// 权限控制
				maintenanPrivilege();
				AlertUtil.showMessage("维修人员档案保存成功!");
			}
		});
	}
	
	/**
	 * 获取提交参数
	 */
	function gatherParam(){
		var obj = {
			id : $("#personnelId").val(),
			dprtcode : $("#dprtcode").val(),
			rybh : $("#rybh").val(),
			wbbs : $("input[name='wbbs']:checked").val(),
			wxryid : $("#wxryid").val(),
			xm : $("#xm").val(),
			szdw : $("#szdw").val(),
			xb : $("[name=xb]:radio:checked").val(),
			sr : $("#sr").val(),
			jg : $("#jg").val(),
			zzmm : $("#zzmm").val(),
			xl : $("#xl").val(),
			sfz : $("#sfz").val(),
			yzbm : $("#yzbm").val(),
			lxdh : $("#lxdh").val(),
			yxdz : $("#yxdz").val(),
			dz : $("#dz").val(),
			isJh : $("[name=is_jh]:radio:checked").val(),
			jtcy : $("#jtcy").val(),
			cjgzrq : $("#cjgzrq").val(),
			rzrq : $("#rzrq").val(),
			rzqxx : $("#rzqxx").val(),
			zzzt : $("[name=zzzt]:radio:checked").val(),
			lzrq : $("#lzrq").val(),
			bz : $("#bz").val(),
			zpdzD : $("#zpdzD").val(),
			zpdzX : $("#zpdzX").val(),
			educations : deleteRowid(personnel.education.getAll()),
			languages : deleteRowid(personnel.language.getAll()),
			titles : deleteRowid(personnel.title.getAll()),
			workExperiences : deleteRowid(personnel.workExperience.getAll()),
			posts : deleteRowid(personnel.post.getAll()),
			technicalExperiences : deleteRowid(personnel.technicalExperience.getAll()),
			maintenanceLicenses : deleteRowid(personnel.maintenanceLicense.getAll()),
			typeLicenses : deleteRowid(personnel.typeLicense.getAll()),
			grades : deleteRowid(personnel.grade.getAll()),
			trainings : deleteRowid(personnel.training.getAll()),
			businessAssessments : deleteRowid(personnel.businessAssessment.getAll()),
			scholarships : deleteRowid(personnel.scholarship.getAll()),
			citationRecords : deleteRowid(personnel.citationRecord.getAll()),
			incidentSituations : deleteRowid(personnel.incidentSituation.getAll()),
			unsafeIncidents : deleteRowid(personnel.unsafeIncident.getAll()),
			dishonestBehaviors : deleteRowid(personnel.dishonestBehavior.getAll()),
			attachments : deleteCzsj(personnel.attachment.getBasicAttachments()),
			delAttachments : personnel.attachment.getAllRemoveAttachments()
		};
		return obj;
	}
	
	/**
	 * 删除rowid
	 * @param list
	 */
	function deleteRowid(list){
		for(var i = 0; i < list.length; i++){
			delete list[i].whsj;
			deleteCzsj(list[i].attachments);
		}
		return list;
	}
	
	/**
	 * 删除操作时间
	 * @param list
	 */
	function deleteCzsj(list){
		for(var j = 0; j < list.length; j++){
			list[j].realname = list[j].czsj || list[j].realname;
			delete list[j].czsj;
		}
		return list;
	}
	
	/**
	  * 加载维修人员档案详情数据
	  * @param id
	  */
	 function loadDetail(){
		 if(!$("#personnelId").val()){
			// 权限控制
			maintenanPrivilege();
			 return false;
		 }
	   	 AjaxUtil.ajax({
	   	    url:basePath+"/quality/maintenancepersonnel/loaddetail",
	   	    type: "post",
	   	    contentType:"application/json;charset=utf-8",
	   	    dataType:"json",
	   	    data:JSON.stringify({
	   		    id : $("#personnelId").val()
	   	    }),
	   	    success:function(data){	   	 
	   	    	$("#personnelId").val(data.id);
				$("#rybh").val(data.rybh);
				$("input[name='wbbs'][value='"+data.wbbs+"']").attr('checked',true);
				onchlickradio();
				$("#wxryid").val(data.wxryid);
				$("#xm").val(data.xm);
				$("#szdw").val(data.szdw);
				$("[name=xb][value='"+data.xb+"']").attr("checked","checked"); 
				$("#sr").val((data.sr||"").substr(0,10));
				$("#jg").val(data.jg);
				$("#zzmm").val(data.zzmm);
				$("#xl").val(data.xl);
				$("#sfz").val(data.sfz);
				$("#yzbm").val(data.yzbm);
				$("#lxdh").val(data.lxdh);
				$("#yxdz").val(data.yxdz);
				$("#dz").val(data.dz),
				$("[name=is_jh][value='"+data.isJh+"']").attr("checked","checked"); 
				$("#jtcy").val(data.jtcy);
				$("#cjgzrq").val((data.cjgzrq||"").substr(0,10));
				$("#rzrq").val((data.rzrq||"").substr(0,10));
				$("#rzqxx").val(data.rzqxx);
				$("[name=zzzt][value='"+data.zzzt+"']").attr("checked","checked"); 
				$("#lzrq").val((data.lzrq||"").substr(0,10));
				$("#bz").val(data.bz);
				$("#zpdzD").val(data.zpdzD);
				$("#zpdzX").val(data.zpdzD);
				  	
				$("#sr").datepicker("update");
				$("#cjgzrq").datepicker("update");
				$("#rzrq").datepicker("update");
				$("#lzrq").datepicker("update");
				
				if(data.zzzt == "0"){
					$("#lzrqDiv").show();
				}else{
					$("#lzrqDiv").hide();
					$("#lzrq").val("");
				}
				personnel.updateData(data);
				// 回显表格数据
				fillTableData(data);
				// 权限控制
				 if($("#type").val() != 3){
					 maintenanPrivilege();
				 }else{
					$("#detail_div").find("input,select,textarea").attr("disabled",true);
				 }
				if(data.zpdzD!=null && data.zpdzD!=''){
					var str = data.zpdzD.replaceAll(/\\/g,"&");
					var url = basePath +"/training/faculty/preview/"+str+"/1";
					$(".avatar-view img").attr('src',url);
				}
	        }
        }); 
	 }
	 
	 /**
	  * 日期比较
	  * @param d1
	  * @param d2
	  */
	 function compareDate(d1, d2){
		 return ((new Date(d1.replace(/-/g,"\/"))) - (new Date(d2.replace(/-/g,"\/"))));
	 }
	 
	 function exportExcel(){
	 		var id = $("#personnelId").val();
	 		if(id){
	 			var dprtcode = $("#dprtcode").val();
	 			window.open(basePath+"/quality/maintenancepersonnel/personnelArchives.xls?dprtcode="+ dprtcode + "&id="+ encodeURIComponent(id));
	 		}else{
	 			AlertUtil.showMessage("请先选择一个维修人员档案!");
	 		}
	 		
	 	}
	