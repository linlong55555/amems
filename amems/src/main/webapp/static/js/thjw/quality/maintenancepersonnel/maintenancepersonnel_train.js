
	var course;

	$(function(){
		// 初始化时间控件
		initTrainingDateWidget();
		// 初始化数据字典
		initDic();
		// 初始化验证
		initTrainingValidation();
		$('#training_records_modal').on('shown.bs.modal', function (e) {
			$("#training_form").data('bootstrapValidator').resetForm(false);
		});

		 $('input:radio[name="training_records_modal_fxbs"]').click(function(){
				if($(this).val()==2){
					if(fxpxsxmark==null||fxpxsxmark==""){
						$('select#training_records_modal_pxxs option:first').attr('selected','true');
					}else{
						$("#training_records_modal_pxxs").val(fxpxsxmark);
					}
					if(fxksxsmark==null||fxksxsmark==""){
						 $('select#training_records_modal_ksxs option:first').attr('selected','true');
					}else{
						$("#training_records_modal_ksxs").val(fxksxsmark);
					}
					
				}else if($(this).val()==1){
					if(cxpxxsmark==null||cxpxxsmark==""){
						$('select#training_records_modal_pxxs option:first').attr('selected','true');
					}else{
						$("#training_records_modal_pxxs").val(cxpxxsmark);
					}
					 if(cxksxsmark==null||cxksxsmark==""){
						 $('select#training_records_modal_ksxs option:first').attr('selected','true');
					 }else{
						 $("#training_records_modal_ksxs").val(cxksxsmark);
					 }
					 
				}
			})
	});


	/**
	 * 新增培训记录
	 */
	function addTrainingRecords(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#training_records_modal input:not(:radio)").val("");
		$("#training_records_modal textarea").val("");
		$("#training_records_modal_kcmc").removeAttr("disabled");
		$("#training_records_modal_fjjx").removeAttr("disabled");
		$("#training_records_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		/*$("#train_div #fjdivmark").removeClass().addClass('col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0');*/
		/*$("#train_div #wjsmmark").css("width","8.3%");
		$("#train_div #fjlbmark").css("width","9.5%");*/
		$(".date-picker").datepicker("update");
		$("#train_div #fjlbmark").removeClass().addClass('col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0');
		$("#train_div #fjlistmark").removeClass().addClass('col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8');
		ModalUtil.showModal("training_records_modal");
	}
	
	/**
	 * 修改培训记录
	 */
	function editTrainingRecords(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.training.get(rowid);
		course = obj.course;
	    $("#training_records_modal_kcid").val(obj.kcid);
	    $("#training_records_modal_kcbm").val(obj.kcbm);
	    $("#training_records_modal_kcmc").val(obj.kcmc);
	    $("#training_records_modal_fjjx").val(obj.fjjx);
	    $("#training_records_modal_zy").val(obj.zy);
	    $("#training_records_modal_jsxm").val(obj.jsxm);
	    $("#training_records_modal_jsid").val(obj.jsid);
	    $("#training_records_modal_sjKsrq").val((obj.sjKsrq||"").substring(0,10));
	    $("#training_records_modal_sjKssj").val(obj.sjKssj);
	    $("#training_records_modal_sjJsrq").val((obj.sjJsrq||"").substring(0,10));
	    $("#training_records_modal_sjJssj").val(obj.sjJssj);
	    $("#training_records_modal_sjks").val(obj.sjks);
	    $("#training_records_modal_pxxs").val(obj.pxxs);
	    $("#training_records_modal_ksxs").val(obj.ksxs);
	    $("#training_records_modal_pxlb").val(obj.pxlb);
	    $("#training_records_modal_pxgh").val(obj.pxgh);
	    $("#training_records_modal_kcdd").val(obj.kcdd);
	    $("#training_records_modal_cql").val(obj.cql);
	    $("#training_records_modal_cj").val(obj.cj);
	    $("#training_records_modal_zs").val(obj.zs);
	    $("#training_records_modal_xcpxrq").val((obj.xcpxrq||"").substring(0,10));
	    $("#training_records_modal_bz").val(obj.bz);
	    
	    $("#training_records_modal input").attr("disabled",false);
		$("#training_records_modal select").attr("disabled",false);
		$("#training_records_modal textarea").attr("disabled",false);
		if(obj.kcid){
	    	$("input[name='kcmc']").attr({"disabled":"disabled"});
	    	$("#training_records_modal_fjjx").attr({"disabled":"disabled"});
	    }
	    if(obj.kcid!=null&&obj.fxbs==1){
	    	$("input[name='training_records_modal_fxbs']").attr({"disabled":"disabled"});
	    	$("input:radio[name='training_records_modal_fxbs'][value='"+obj.fxbs+"']").attr("checked",true); 
	    }else{
	    	$("input[name='training_records_modal_fxbs").removeAttr("disabled");//将按钮可用
	    	$("input:radio[name='training_records_modal_fxbs'][value='"+obj.fxbs+"']").attr("checked",true); 
	    }
	     
		$("input:radio[name='training_records_modal_khjg'][value='"+obj.khjg+"']").attr("checked",true); 
		/*$("#train_div #fjdivmark").removeClass().addClass('col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 ');*/
		/*$("#train_div #wjsmmark").css("width","8.3%");
		$("#train_div #fjlbmark").css("width","9.5%");*/
		$("#train_div #fjlbmark").removeClass().addClass('col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0');
		$("#train_div #fjlistmark").removeClass().addClass('col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8');
		$(".datetimepicker").datepicker("update");
		loadAttachments("#training_records_modal", rowid);
		ModalUtil.showModal("training_records_modal");
	}
	
	/**
	 * 确定编辑培训记录
	 */
	function confirmTrainingRecords(){
		startWait($("#training_records_modal"));
		if(!validateTrainingRecords()){
			finishWait($("#training_records_modal"));
			return false;
		}
		$("#training_records_list .non-choice").remove();
		var training = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				kcid : $("#training_records_modal_kcid").val(),
				kcbm : $("#training_records_modal_kcbm").val(),
				kcmc : $("#training_records_modal_kcmc").val(),
				fjjx : $("#training_records_modal_fjjx").val(),
				zy : $("#training_records_modal_zy").val(),
				jsxm : $("#training_records_modal_jsxm").val(),
				jsid : $("#training_records_modal_jsid").val(),
				sjKsrq : ($("#training_records_modal_sjKsrq").val()||"").substring(0,10),
				sjKssj : $("#training_records_modal_sjKssj").val(),
				sjJsrq : ($("#training_records_modal_sjJsrq").val()||"").substring(0,10),
				sjJssj : $("#training_records_modal_sjJssj").val(),
				sjks : $("#training_records_modal_sjks").val(),
				pxxs : $("#training_records_modal_pxxs").val(),
				ksxs : $("#training_records_modal_ksxs").val(),
				pxlb : $("#training_records_modal_pxlb").val(),
				pxgh : $("#training_records_modal_pxgh").val(),
				kcdd : $("#training_records_modal_kcdd").val(),
				cql : $("#training_records_modal_cql").val(),
				cj : $("#training_records_modal_cj").val(),
				zs : $("#training_records_modal_zs").val(),
				xcpxrq : ($("#training_records_modal_xcpxrq").val().substring(0,10)||""),
				bz : $("#training_records_modal_bz").val(),
				fxbs : $("[name='training_records_modal_fxbs']:radio:checked").val(),
				khjg : $("[name='training_records_modal_khjg']:radio:checked").val(),
				attachments : getAttachments("10"),
				course : course
		};
		pxjl=[];
		modifyTrainingRecordsRow(training);
		personnel.training.merge(training);
		$("#training_records_modal").modal("hide");
		finishWait($("#training_records_modal"));
	}
	
	/**
	 * 验证培训记录
	 */
	function validateTrainingRecords(){
		$('#training_form').data('bootstrapValidator').validate();
		if(!$('#training_form').data('bootstrapValidator').isValid()){
			return false;
		}	
		if($("#training_records_modal_xcpxrq").val()!=""){
	 		var oDate1 = new Date($("#training_records_modal_xcpxrq").val());
	 	    var oDate2 = new Date($("#training_records_modal_sjKsrq").val());
	 		
	 		 if(oDate1.getTime() < oDate2.getTime()){
	 			AlertUtil.showMessage('下次培训日期必须大于课程开始日期!');
	 			return false;
	 	    }
	 	}
		var ksrq = $("#training_records_modal_sjKsrq").val();
		var jsrq = $("#training_records_modal_sjJsrq").val();
		
		if(jsrq && compareDate(ksrq, jsrq) > 0){
			AlertUtil.showErrorMessage("结束日期不能小于开始时间！");
			return false;
		}
		if(jsrq && compareDate(ksrq, jsrq) == 0){
			var kssj = $("#training_records_modal_sjKssj").val()||"00:00";
			var jssj = $("#training_records_modal_sjJssj").val()||"00:00";
			if(parseInt(kssj.split(":")[0]) > parseInt(jssj.split(":")[0]) ||
					(parseInt(kssj.split(":")[0]) == parseInt(jssj.split(":")[0]) &&
							parseInt(kssj.split(":")[1]) > parseInt(jssj.split(":")[1]))){
				AlertUtil.showErrorMessage("结束日期不能小于开始时间！");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 新增/修改-培训记录
	 */
	function modifyTrainingRecordsRow(obj){
		var timestr = (obj.sjKsrq||"");
		if(obj.sjJsrq && obj.sjKsrq != obj.sjJsrq){
			timestr += ("至" + obj.sjJsrq);
		}
		var khjgstr = "";
		if(obj.khjg == 1){
			khjgstr = "通过";
		}else if(obj.khjg == 0){
			khjgstr = "未通过";
		}
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#training_records_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3 && !obj.pxjhid)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editTrainingRecords(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3 && !obj.pxjhid)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteTrainingRecords(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pxlb)+"' name='pxlb'>"+StringUtil.escapeStr(obj.pxlb)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.fjjx)+"' name='fjjx'>"+StringUtil.escapeStr(obj.fjjx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zy)+"' name='zy'>"+StringUtil.escapeStr(obj.zy)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='timestr'>"+StringUtil.escapeStr(timestr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pxgh)+"' name='pxgh'>"+StringUtil.escapeStr(obj.pxgh)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.kcdd)+"' name='kcdd'>"+StringUtil.escapeStr(obj.kcdd)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.kcmc)+"' name='kcmc'>"+StringUtil.escapeStr(obj.kcmc)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jsxm)+"' name='jsxm'>"+StringUtil.escapeStr(obj.jsxm)+"</td>",
			       "<td style='text-align: right;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjks)+"' name='sjks'>"+StringUtil.escapeStr(obj.sjks)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pxxs)+"' name='pxxs'>"+StringUtil.escapeStr(obj.pxxs)+"</td>",
			       "<td style='text-align: right;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cj)+"' name='cj'>"+StringUtil.escapeStr(obj.cj)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(khjgstr)+"' name='khjgstr'>"+StringUtil.escapeStr(khjgstr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zs)+"' name='zs'>"+StringUtil.escapeStr(obj.zs)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"pxjl")+"</td>",
			       "<input type='hidden' name='sjKsrq' value='"+(obj.sjKsrq||"")+"'>",
			       "<input type='hidden' name='sjKssj' value='"+(obj.sjKssj||"")+"'>",
			       "<input type='hidden' name='sjJsrq' value='"+(obj.sjJsrq||"")+"'>",
			       "<input type='hidden' name='sjJssj' value='"+(obj.sjJssj||"")+"'>",
			       "<input type='hidden' name='fxbs' value='"+(obj.fxbs||"")+"'>",
			       "<input type='hidden' name='khjg' value='"+(obj.khjg||"")+"'>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='timestr']").text(timestr);
			row.find("[name='timestr']").attr("title",timestr);
			row.find("[name='gwzw']").text(obj.gwzw);
			row.find("[name='gwzw']").attr("title",obj.gwzw);
			row.find("[name='pxlb']").text(obj.pxlb||"");
			row.find("[name='pxlb']").attr("title",obj.pxlb||"");
			row.find("[name='fjjx']").text(obj.fjjx);
			row.find("[name='fjjx']").attr("title",obj.fjjx);
			row.find("[name='zy']").text(obj.zy);
			row.find("[name='zy']").attr("title",obj.zy);
			row.find("[name='pxgh']").text(obj.pxgh);
			row.find("[name='pxgh']").attr("title",obj.pxgh);
			row.find("[name='kcdd']").text(obj.kcdd);
			row.find("[name='kcdd']").attr("title",obj.kcdd);
			row.find("[name='kcmc']").text(obj.kcmc);
			row.find("[name='kcmc']").attr("title",obj.kcmc);
			row.find("[name='jsxm']").text(obj.jsxm);
			row.find("[name='sjks']").text(obj.sjks);
			row.find("[name='sjks']").attr("title",obj.sjks);
			row.find("[name='pxxs']").text(obj.pxxs||"");
			row.find("[name='pxxs']").attr("title",obj.pxxs||"");
			row.find("[name='cj']").text(obj.cj);
			row.find("[name='cj']").attr("title",obj.cj);
			row.find("[name='khjgstr']").text(khjgstr);
			row.find("[name='khjgstr']").attr("title",khjgstr);
			row.find("[name='zs']").text(obj.zs);
			row.find("[name='zs']").attr("title",obj.zs);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"pxjl"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='sjKsrq']").val(obj.sjKsrq||"");
			row.find("[name='sjKssj']").val(obj.sjKssj||"");
			row.find("[name='sjJsrq']").val(obj.sjJsrq||"");
			row.find("[name='sjJssj']").val(obj.sjJssj||"");
			row.find("[name='fxbs']").val(obj.fxbs||"");
			row.find("[name='khjg']").val(obj.khjg||"");
		}
		
	}
	
	/**
	 * 删除行-培训记录
	 */
	function deleteTrainingRecords(obj){
		var row = $(obj).parent().parent();
		personnel.training.remove(row.attr("id"));
		row.remove();
		if($("#training_records_list>tr").length == 0){
			var ths = $("#training_records_table>thead>tr>th:visible").length;
			$("#training_records_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 打开课程列表对话框
	 */
	 var fxpxsxmark="";//复训培训形式
	 var fxksxsmark="";//复训考试形式
	 var cxpxxsmark="";//初训培训形式
	 var cxksxsmark="";//初训考试形式
	function openCourseWin() {
		$("#training_records_modal_jsid").val('');
		$("#training_records_modal_jsxm").val('');
		
		
	 	open_win_course_modal.show({
	 		selected : $("#training_records_modal_kcid").val(),// 原值，在弹窗中默认勾选
	 		dprtcode:$("#dprtcode").val(),//机构代码
	 		callback : function(data) {// 回调函数
	 			if (data != null && data.id != null) {
	 				course = data;
	 				$("#training_records_modal_kcid").val(formatUndefine(data.id));
	 				$("#training_records_modal_kcbm").val(formatUndefine(data.kcbh));
	 				$("#training_records_modal_kcmc").val(formatUndefine(data.kcmc));
	 				$("#training_records_modal_fjjx").val(formatUndefine(data.fjjx));
	 				
	 				$('#training_form').data('bootstrapValidator')  
		 		      .updateStatus('kcbm', 'NOT_VALIDATED',null)  
		 		      .validateField('kcbm');  
	 				$('#training_form').data('bootstrapValidator')  
	 				.updateStatus('kcmc', 'NOT_VALIDATED',null)  
	 				.validateField('kcmc');  
	 				
	 				if(data.isFx==1){
	 					 $("input[name='training_records_modal_fxbs']").get(0).checked=true;
	 					 $("input[name='training_records_modal_fxbs").removeAttr("disabled");//将按钮可用
	 				}else{
	 					 $("input[name='training_records_modal_fxbs']").get(0).checked=true;
	 					 $("input[name='training_records_modal_fxbs']").attr("disabled","disabled");
	 				}
	 				 fxpxsxmark=data.fxpxxs;
	 				 fxksxsmark=data.fxksxs;
	 				 cxpxxsmark=data.pxxs;
	 				 cxksxsmark=data.ksxs;
	 				 checkFxbs();
	 				 calcNextTrainingDate();
	 			}
	 		}
	 	})
	}
	
	
	/**
	 * 选中复训
	 */
	function checkFxbs(){
	 	if(course.id != null){
	 		selectkcid(course.id);
	 	}
	}
	
	/**
	 * 根据课程id查询课程信息得到复训周期值+周期单位
	 * @param id
	 */
	function selectkcid(id){
	 	AjaxUtil.ajax({
	 		async: false,
	 		url:basePath+"/training/course/selectById",
	 		type:"post",
	 		data:{id:id},
	 		dataType:"json",
	 		success:function(data){
	 			if(data !=null){
	 				$("input[name='kcmc']").attr({"disabled":"disabled"});
	 				$("#training_records_modal_fjjx").attr({"disabled":"disabled"});
	 				if(data.fxbs==2){
			 			$("#training_records_modal_pxxs").val(data.fxpxxs);
			 			$("#training_records_modal_ksxs").val(data.fxksxs);
	 				}else{
	 					$("#training_records_modal_pxxs").val(data.pxxs);
			 			$("#training_records_modal_ksxs").val(data.ksxs);
	 				}
	 			}
	 		}
	 	});
	}
	
	/**
	 * 课程代码改变事件
	 * @returns
	 */
	function onKcbmChanged(){
		$("#training_records_modal_kcid").val("");
		$('input:radio[name="training_records_modal_fxbs"]').prop("disabled",false);//将按钮可用
		$("input[name='kcmc").removeAttr("disabled");//将课程名称可用
		$("#training_records_modal_fjjx").removeAttr("disabled");//将机型可用
		fxpxsxmark="";//复训培训形式
		fxksxsmark="";//复训考试形式
		cxpxxsmark="";//初训培训形式
		cxksxsmark="";//初训考试形式
	}
	
	
	/**
	 * 初始化验证
	 */
	function initTrainingValidation(){
		$("#training_records_modal_cql").keyup(function(){
			backspace($(this),/^[0-9]+(\.?[0-9]{0,2})?$/);
		});
		$('#training_form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	kcbm: {
		            validators: {
	                    regexp: {
	                    	regexp: /^[\x00-\xff]{0,50}$/,
	                        message: '不能输入中文且长度最多不超过50个字符'
	                    }
		            }
	        	},
	        	kcmc: {
		            validators: {
		            	notEmpty: {
		                    message: '课程名称不能为空'
		                }
		            }
	        	},
	        	fjjx: {
	                validators: {
	                	regexp: {
	                        regexp: /^[\x00-\xff]*$/,
	                        message: '机型不能含有中文'
	                    }
	                }
	            },
	        	sjKsrq: {
		            validators: {
		            	notEmpty: {
		                    message: '课程开始日期不能为空'
		                }
		            }
	        	},
//	        	pxgh: {
//		            validators: {
//		            	notEmpty: {
//		                    message: '培训机构不能为空'
//		                }
//		            }
//	        	},
	        	cql: {
		            validators: {
		        	  regexp: {
		              	regexp: /^(\d{1,2}(\.\d{1,2})?|100)$/,
		                  message: '只能输入0.00-100'
		              }
		            }
	        	}
	        }
	    });
	}
	
	//只能输入数字
//	function clearNoNumber(obj){
//	     //先把非数字的都替换掉，除了数字
//	     obj.value = obj.value.replace(/[^\d]/g,"");
//	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
//	  		obj.value = 0;
//	  	 }
//	}

	//只能输入数字和小数,保留两位
	function clearNoNumTwo(obj){
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
	}
	
	//只能输入数字和小数,保留一位
	function clearNoNumOne(obj){
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
	    	 if(str[1].length > 1){
	    		 obj.value = str[0] +"." + str[1].substring(0,1);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	}
	
	
	/**
	 * 初始化时间控件
	 */
	function initTrainingDateWidget(){
		$("#training_records_modal_sjKsrq").datepicker({
			format:'yyyy-mm-dd',
			autoclose : true,
			clearBtn : true
		 });
		
		 $("#training_records_modal_sjKssj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,
				step:10,			 
				datepicker:false
		 });
		 
		 $("#training_records_modal_sjJsrq").datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
		 });
		 
		 $("#training_records_modal_sjJssj").datetimepicker({
				lang:"ch", 
				format:'H:i'	,
				step:10,			 
				datepicker:false
		 });
		 
		 $('#training_records_modal_xcpxrq').datepicker({
			 format:'yyyy-mm-dd',
			 autoclose : true,
			 clearBtn : true,
			 autoclose: true,
			 clearBtn:true
		 }).on('hide', function(e) {
			  $('#training_form').data('bootstrapValidator')  
		  .updateStatus('xcpxrq', 'NOT_VALIDATED',null)  
		  .validateField('xcpxrq');  
		 });
		
		 $('#training_records_modal_sjKsrq').datepicker({
			 autoclose: true,
			 clearBtn:true
		 }).on('hide', function(e) {
			  $('#training_form').data('bootstrapValidator')  
		  .updateStatus('sjKsrq', 'NOT_VALIDATED',null)  
		  .validateField('sjKsrq');  
		 });
	}
	
	/**
	 * 初始化数据字典
	 */
	function initDic(){
	 	$("#training_records_modal_pxlb").empty();
	 	DicAndEnumUtil.registerDic('34','training_records_modal_pxlb',$("#dprtcode").val());
	 	$("#training_records_modal_pxxs").empty();
	 	DicAndEnumUtil.registerDic('32','training_records_modal_pxxs',$("#dprtcode").val());
	 	$("#training_records_modal_ksxs").empty();
	 	DicAndEnumUtil.registerDic('33','training_records_modal_ksxs',$("#dprtcode").val());
	}
	
	$("#alertModaladdupdate").on("hidden.bs.modal", function() {
		 fxpxsxmark="";//复训培训形式
		 fxksxsmark="";//复训考试形式
		 cxpxxsmark="";//初训培训形式
		 cxksxsmark="";//初训考试形式
	});
	
	//打开调整列表对话框
	 function openLecturerWin() {
	 	var sqksrq=$("#training_records_modal_sjKsrq").val();
	 	var kcid=$("#training_records_modal_kcid").val();
	 	if(sqksrq==''||kcid==''){
	 		AlertUtil.showErrorMessage("课程代码和计划开始日期不能为空!");
	 		return false;
	 	}
	 	var param = {};
	 	param.sqksrq=sqksrq;
	 	param.kcid=kcid;
	 	param.selected=$("#training_records_modal_jsid").val();
	 	param.callback = function(data){
	 		$("#training_records_modal_jsxm").val(data.xm);
	 		$("#training_records_modal_jsid").val(data.id);
	 	},
	 	lecturer_user.show(param);
	 }
	
	
	/**
	 * 不满足正则则回退
	 * @param obj
	 * @param reg
	 */
	function backspace(obj, reg){
		var value = obj.val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(value);
		$('#training_form').data('bootstrapValidator')  
		  .updateStatus(obj.attr("name"), 'NOT_VALIDATED',null)  
		  .validateField(obj.attr("name"));  
	}
	
	/**
	 * 计算下次培训日期
	 */
	function calcNextTrainingDate(){
		 $("#training_records_modal_jsid").val('');
		 $("#training_records_modal_jsxm").val('');
		if(course && course.isFx == 1 && course.zqz && course.zqdw){
			var ksrq = $("#training_records_modal_sjKsrq").val();
			if(ksrq){
				$("#training_records_modal_xcpxrq").val(addDate(ksrq, course.zqz, course.zqdw));
			}
		}
		
		function addDate(date, num, unit){ 
			var d=new Date(date); 
			if(unit == 1){
				d.setDate(d.getDate()+num); 
			}else if(unit == 2){
				d.setMonth(d.getMonth()+num); 
			}else if(unit == 3){
				d.setFullYear(d.getFullYear()+num); 
			}
			var month=d.getMonth()+1; 
			var day = d.getDate(); 
			if(month<10){ 
			month = "0"+month; 
			} 
			if(day<10){ 
			day = "0"+day; 
			} 
			var val = d.getFullYear()+"-"+month+"-"+day; 
			return val; 
		}
	}