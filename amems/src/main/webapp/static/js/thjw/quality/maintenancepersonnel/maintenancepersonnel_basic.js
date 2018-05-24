
	$(function(){
		// 数据字典
		DicAndEnumUtil.registerDic('2','xl',$("#dprtcode").val());
		DicAndEnumUtil.registerDic('10','zzmm',$("#dprtcode").val());
		DicAndEnumUtil.registerDic('11','basic_language_modal_wysp',$("#dprtcode").val());
	});
	
	/**
	 * 切换显示离职日期
	 * @param zt
	 */
	function toggleLzrq(zt){
		if(zt == "0" && $("#type").val() != "3"){
			$("#lzrqDiv").show();
		}else{
			$("#lzrqDiv").hide();
			$("#lzrq").val("");
		}
	}
	
	/**
	 * 选择维修人员
	 */
	function chooseWxry(){
		//调用用户选择弹窗
		userModal.show({
			selected:$("#wxryid").val(),//原值，在弹窗中默认勾选
			page:"maintenancepersonnel",
			callback: function(data){//回调函数
				var flag=validtion4RyidExist(data.id,$("#dprtcode").val());
				if(flag){
					$("#wxryid").val(data.id);
					$("#xm").val(data.realname);
					$("#szdw").val(data.dprt_bm?data.dprt_bm.dprtname:"");
					$("[name=xb][value='"+(data.sex || "1")+"']").attr("checked","checked"); 
					$("#basic_form").data('bootstrapValidator').resetForm(false);
				}
			},
			dprtcode:$("#dprtcode").val()
		})
	}
	
	function validtion4RyidExist(id,dprtcode){
		var searchParam={
				wxryid:id,
				dprtcode:dprtcode,
				paramsMap:{
					notId:$("#personnelId").val()
				}
		}
		var flag = true;
		AjaxUtil.ajax({
			url : basePath
					+ "/quality/maintenancepersonnel/validtion4RyidExist",
			type : "post",
			data :JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async:false,
			success : function(data) {
				if(data>0){
					AlertUtil.showErrorMessage("该维修人员档案已存在！");
					flag = false;
				}
			}
		});
		return flag;
	}
	
	/**
	 * 新增教育经历
	 */
	function addBasicEducation(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#basic_education_modal input").val("");
		$(".date-picker").datepicker("update");
		$("#basic_education_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("basic_education_modal");
	}
	
	/**
	 * 修改教育经历
	 */
	function editBasicEducation(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.education.get(rowid);
		$("#basic_education_modal_ksrq").val(obj.ksrq);
		$("#basic_education_modal_jsrq").val(obj.jsrq);
		$("#basic_education_modal_jyzy").val(obj.jyzy);
		$("#basic_education_modal_byxx").val(obj.byxx);
		$("#basic_education_modal_xz").val(obj.xz);
		$(".date-picker").datepicker("update");
		loadAttachments("#basic_education_modal", rowid);
		ModalUtil.showModal("basic_education_modal");
	}
	
	/**
	 * 确定编辑教育经历
	 */
	function confirmBasicEducation(){
		if(!validateBasicEducation()){
			return false;
		}
		$("#basic_education_list .non-choice").remove();
		var education = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				ksrq : $("#basic_education_modal_ksrq").val(),
				jsrq : $("#basic_education_modal_jsrq").val(),
				jyzy : $("#basic_education_modal_jyzy").val(),
				byxx : $("#basic_education_modal_byxx").val(),
				xz : $("#basic_education_modal_xz").val(),
				attachments : getAttachments("1")
		};
		jyjl=[];//教育经历
		modifyBasicEducationRow(education);
		personnel.education.merge(education);
		$("#basic_education_modal").modal("hide");
	}
	
	/**
	 * 验证教育经历
	 */
	function validateBasicEducation(){
		var ksrq = $("#basic_education_modal_ksrq").val();
		if(!ksrq){
			AlertUtil.showErrorMessage("请输入开始时间！");
			return false;
		}
		var jsrq = $("#basic_education_modal_jsrq").val();
		if(jsrq && compareDate(ksrq, jsrq) > 0){
			AlertUtil.showErrorMessage("结束日期不能小于开始时间！");
			return false;
		}
		var byxx = $("#basic_education_modal_byxx").val();
		if(!byxx){
			AlertUtil.showErrorMessage("请输入毕业学校！");
			return false;
		}
		var jyzy = $("#basic_education_modal_jyzy").val();
		if(!jyzy){
			AlertUtil.showErrorMessage("请输专业！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-教育经历
	 */
	function modifyBasicEducationRow(obj){
		var timestr = obj.ksrq + "至" + (obj.jsrq||"今");
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#basic_education_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editBasicEducation(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteBasicEducation(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='time'>"+StringUtil.escapeStr(timestr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.byxx)+"' name='byxx'>"+StringUtil.escapeStr(obj.byxx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jyzy)+"' name='jyzy'>"+StringUtil.escapeStr(obj.jyzy)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.xz)+"' name='xz'>"+StringUtil.escapeStr(obj.xz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"jyjl")+"</td>",
			       "<input type='hidden' name='ksrq' value='"+(obj.ksrq||"")+"'>",
			       "<input type='hidden' name='jsrq' value='"+(obj.jsrq||"")+"'>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='time']").text(timestr);
			row.find("[name='time']").attr("title",timestr);
			row.find("[name='byxx']").text(obj.byxx);
			row.find("[name='byxx']").attr("title",obj.byxx);
			row.find("[name='jyzy']").text(obj.jyzy);
			row.find("[name='jyzy']").attr("title",obj.jyzy);
			row.find("[name='xz']").text(obj.xz);
			row.find("[name='xz']").attr("title",obj.xz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"jyjl"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='ksrq']").val(obj.ksrq||"");
			row.find("[name='jsrq']").val(obj.jsrq||"");
		}
		
	}
	
	/**
	 * 删除行-教育经历
	 */
	function deleteBasicEducation(obj){
		var row = $(obj).parent().parent();
		personnel.education.remove(row.attr("id"));
		row.remove();
		if($("#basic_education_list>tr").length == 0){
			var ths = $("#basic_education_table>thead>tr>th:visible").length;
			$("#basic_education_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	/**
	 * 新增外语水平
	 */
	function addBasicLanguage(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#basic_language_modal input").val("");
		$(".date-picker").datepicker("update");
		$("#basic_language_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("basic_language_modal");
		
	}
	
	/**
	 * 修改外语水平
	 */
	function editBasicLanguage(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.language.get(rowid);
		$("#basic_language_modal_yz").val(obj.yz);
		$("#basic_language_modal_wysp").val(obj.wysp);
		$(".date-picker").datepicker("update");
		loadAttachments("#basic_language_modal", rowid);
		ModalUtil.showModal("basic_language_modal");
	}
	
	/**
	 * 确定编辑外语水平
	 */
	function confirmBasicLanguage(){
		if(!validateBasicLanguage()){
			return false;
		}
		$("#basic_foreign_language_list .non-choice").remove();
		var language = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				yz : $("#basic_language_modal_yz").val(),
				wysp : $("#basic_language_modal_wysp").val(),
				attachments : getAttachments("2")
		};
		wysp=[];//外语水平
		modifyBasicLanguageRow(language);
		personnel.language.merge(language);
		$("#basic_language_modal").modal("hide");
	}
	
	/**
	 * 验证外语水平
	 */
	function validateBasicLanguage(){
		var yz = $("#basic_language_modal_yz").val();
		if(!yz){
			AlertUtil.showErrorMessage("请输入语种！");
			return false;
		}
		var wysp = $("#basic_language_modal_wysp").val();
		if(!wysp){
			AlertUtil.showErrorMessage("请输入外语水平！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-外语水平
	 */
	function modifyBasicLanguageRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#basic_foreign_language_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editBasicLanguage(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteBasicLanguage(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
	    		   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.yz)+"' name='yz'>"+StringUtil.escapeStr(obj.yz)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.wysp)+"' name='wysp'>"+StringUtil.escapeStr(obj.wysp)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"wysp")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='yz']").text(obj.yz);
			row.find("[name='yz']").attr("title",obj.yz);
			row.find("[name='wysp']").text(obj.wysp);
			row.find("[name='wysp']").attr("title",obj.wysp);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"wysp"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-外语水平
	 */
	function deleteBasicLanguage(obj){
		var row = $(obj).parent().parent();
		personnel.language.remove(row.attr("id"));
		row.remove();
		if($("#basic_foreign_language_list>tr").length == 0){
			var ths = $("#basic_education_table>thead>tr>th:visible").length;
			$("#basic_foreign_language_table").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	/**
	 * 新增聘任职称
	 */
	function addBasicTitle(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#basic_title_modal input").val("");
		$("#basic_title_modal textarea").val("");
		AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		$(".date-picker").datepicker("update");
		$("#basic_title_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("basic_title_modal");
	}
	
	/**
	 * 修改聘任职称
	 */
	function editBasicTitle(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.title.get(rowid);
		AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		$("#basic_title_modal_zc").val(obj.zc);
		$("#basic_title_modal_sqdw").val(obj.sqdw);
		$("#basic_title_modal_prrq").val(obj.prrq);
		$("#basic_title_modal_prqx").val(obj.prqx);
		$("#basic_title_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#basic_title_modal", rowid);
		ModalUtil.showModal("basic_title_modal");
	}
	
	/**
	 * 确定编辑聘任职称
	 */
	function confirmBasicTitle(){
		if(!validateBasicTitle()){
			return false;
		}
		$("#basic_title_list .non-choice").remove();
		var title = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				zc : $("#basic_title_modal_zc").val(),
				sqdw : $("#basic_title_modal_sqdw").val(),
				prrq : $("#basic_title_modal_prrq").val(),
				prqx : $("#basic_title_modal_prqx").val(),
				bz : $("#basic_title_modal_bz").val(),
				attachments : getAttachments("3")
		};
		przc=[];//聘任职称
		modifyBasicTitleRow(title);
		personnel.title.merge(title);
		$("#basic_title_modal").modal("hide");
	}
	
	/**
	 * 验证聘任职称
	 */
	function validateBasicTitle(){
		var zc = $("#basic_title_modal_zc").val();
		if(!zc){
			AlertUtil.showErrorMessage("请输入职称！");
			return false;
		}
		var sqdw = $("#basic_title_modal_sqdw").val();
		if(!sqdw){
			AlertUtil.showErrorMessage("请输入授权单位！");
			return false;
		}
		var prrq = $("#basic_title_modal_prrq").val();
		if(!prrq){
			AlertUtil.showErrorMessage("请输入聘任日期！");
			return false;
		}
		var prqx = $("#basic_title_modal_prqx").val();
		if(prqx && compareDate(prrq, prqx) > 0){
			AlertUtil.showErrorMessage("聘任期限不能小于聘任日期！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-聘任职称
	 */
	function modifyBasicTitleRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#basic_title_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editBasicTitle(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteBasicTitle(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
	    		   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zc)+"' name='zc'>"+StringUtil.escapeStr(obj.zc)+"</td>",
	    		   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sqdw)+"' name='sqdw'>"+StringUtil.escapeStr(obj.sqdw)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.prrq)+"' name='prrq'>"+StringUtil.escapeStr(obj.prrq)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.prqx)+"' name='prqx'>"+StringUtil.escapeStr(obj.prqx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"przc")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='zc']").text(obj.zc);
			row.find("[name='zc']").attr("title",obj.zc);
			row.find("[name='sqdw']").text(obj.sqdw);
			row.find("[name='sqdw']").attr("title",obj.sqdw);
			row.find("[name='prrq']").text(obj.prrq);
			row.find("[name='prrq']").attr("title",obj.prrq);
			row.find("[name='prqx']").text(obj.prqx);
			row.find("[name='prqx']").attr("title",obj.prqx);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"przc"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-聘任职称
	 */
	function deleteBasicTitle(obj){
		var row = $(obj).parent().parent();
		personnel.title.remove(row.attr("id"));
		row.remove();
		if($("#basic_title_list>tr").length == 0){
			var ths = $("#basic_title_table>thead>tr>th:visible").length;
			$("#basic_title_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	/**
	 * 新增工作履历
	 */
	function addBasicWorkExperience(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#basic_work_experience_modal input").val("");
		$("#basic_work_experience_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#basic_work_experience_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("basic_work_experience_modal");
	}
	
	/**
	 * 修改工作履历
	 */
	function editBasicWorkExperience(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.workExperience.get(rowid);
		$("#basic_work_experience_modal_ksrq").val(obj.ksrq);
		$("#basic_work_experience_modal_jsrq").val(obj.jsrq);
		$("#basic_work_experience_modal_szdw").val(obj.szdw);
		$("#basic_work_experience_modal_zw").val(obj.zw);
		$("#basic_work_experience_modal_gznr").val(obj.gznr);
		$(".date-picker").datepicker("update");
		loadAttachments("#basic_work_experience_modal", rowid);
		ModalUtil.showModal("basic_work_experience_modal");
	}
	
	/**
	 * 确定编辑工作履历
	 */
	function confirmBasicWorkExperience(){
		if(!validateBasicWorkExperience()){
			return false;
		}
		$("#basic_work_experience_list .non-choice").remove();
		var title = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				ksrq : $("#basic_work_experience_modal_ksrq").val(),
				jsrq : $("#basic_work_experience_modal_jsrq").val(),
				szdw : $("#basic_work_experience_modal_szdw").val(),
				zw : $("#basic_work_experience_modal_zw").val(),
				gznr : $("#basic_work_experience_modal_gznr").val(),
				attachments : getAttachments("4")
		};
		gzll=[];//工作履历
		modifyBasicWorkExperienceRow(title);
		personnel.workExperience.merge(title);
		$("#basic_work_experience_modal").modal("hide");
	}
	
	/**
	 * 验证工作履历
	 */
	function validateBasicWorkExperience(){
		var ksrq = $("#basic_work_experience_modal_ksrq").val();
		if(!ksrq){
			AlertUtil.showErrorMessage("请输入开始时间！");
			return false;
		}
		var jsrq = $("#basic_work_experience_modal_jsrq").val();
		if(jsrq && compareDate(ksrq, jsrq) > 0){
			AlertUtil.showErrorMessage("结束日期不能小于开始时间！");
			return false;
		}
		var szdw = $("#basic_work_experience_modal_szdw").val();
		if(!szdw){
			AlertUtil.showErrorMessage("请输入工作单位/部门！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-工作履历
	 */
	function modifyBasicWorkExperienceRow(obj){
		var timestr = obj.ksrq + "至" + (obj.jsrq||"今");
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#basic_work_experience_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editBasicWorkExperience(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteBasicWorkExperience(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
	    		   "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='time'>"+StringUtil.escapeStr(timestr)+"</td>",
	    		   "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.szdw)+"' name='szdw'>"+StringUtil.escapeStr(obj.szdw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zw)+"' name='zw'>"+StringUtil.escapeStr(obj.zw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gznr)+"' name='gznr'>"+StringUtil.escapeStr(obj.gznr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"gzll")+"</td>",
			       "<input type='hidden' name='ksrq' value='"+(obj.ksrq||"")+"'>",
			       "<input type='hidden' name='jsrq' value='"+(obj.jsrq||"")+"'>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='time']").text(timestr);
			row.find("[name='time']").attr("title",timestr);
			row.find("[name='szdw']").text(obj.szdw);
			row.find("[name='szdw']").attr("title",obj.szdw);
			row.find("[name='zw']").text(obj.zw);
			row.find("[name='zw']").attr("title",obj.zw);
			row.find("[name='gznr']").text(obj.gznr);
			row.find("[name='gznr']").attr("title",obj.gznr);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"gzll"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='ksrq']").val(obj.ksrq||"");
			row.find("[name='jsrq']").val(obj.jsrq||"");
		}
		
	}
	
	/**
	 * 删除行-工作履历
	 */
	function deleteBasicWorkExperience(obj){
		var row = $(obj).parent().parent();
		personnel.workExperience.remove(row.attr("id"));
		row.remove();
		if($("#basic_work_experience_list>tr").length == 0){
			var ths = $("#basic_work_experience_table>thead>tr>th:visible").length;
			$("#basic_work_experience_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	/**
	 * 选择头像模态框
	 */
	function showChoosePhoto(){
		ModalUtil.showModal("avatar-modal");
	}
	
	/**
	 * 回显表格数据
	 * @param data
	 */
	var jyjl=[];//教育经历
	var wysp=[];//外语水平
	var przc=[];//聘任职称
	var gzll=[];//工作履历
	var gwzw=[];//岗位职务
	var jsll=[];//技术履历
	var wxzz=[];//维修执照
	var jxzz=[];//机型执照
	var wxjssjjl=[];//维修技术升级记录
	var pxjl=[]//培训记录;
	var ywkhjl=[];//业务考核记录
	var xscj=[];//学术成就
	var jjjl=[];//嘉奖记录
	var sgzhqk=[];//事故征候情况
	var baqsj=[];//负有责任的不安全事件
	var bcxxw=[];//不诚信行为
	function fillTableData(data){
		 jyjl=[];//教育经历
		 wysp=[];//外语水平
		 przc=[];//聘任职称
		 gzll=[];//工作履历
		 gwzw=[];//岗位职务
		 jsll=[];//技术履历
		 wxzz=[];//维修执照
		 jxzz=[];//机型执照
		 wxjssjjl=[];//维修技术升级记录
		 pxjl=[]//培训记录;
		 ywkhjl=[];//业务考核记录
		 xscj=[];//学术成就
		 jjjl=[];//嘉奖记录
		 sgzhqk=[];//事故征候情况
		 baqsj=[];//负有责任的不安全事件
		 bcxxw=[];//不诚信行为
		var len=($("#type").val()==3)?1:0;
		$("#basic_education_list").empty();
		if(data.educations.length > 0){
			for(var i = 0; i < data.educations.length; i++){
				setAttachmentType(data.educations[i].attachments, "1");
				modifyBasicEducationRow(data.educations[i]);
			}
		}else{
			var ths = $("#basic_education_table>thead>tr>th").length - len;
			$("#basic_education_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#basic_foreign_language_list").empty();
		if(data.languages.length > 0){
			for(var i = 0; i < data.languages.length; i++){
				setAttachmentType(data.languages[i].attachments, "2");
				modifyBasicLanguageRow(data.languages[i]);
			}
		}else{
			var ths = $("#basic_foreign_language_table>thead>tr>th").length- len;
			$("#basic_foreign_language_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#basic_title_list").empty();
		if(data.titles.length > 0){
			for(var i = 0; i < data.titles.length; i++){
				setAttachmentType(data.titles[i].attachments, "3");
				modifyBasicTitleRow(data.titles[i]);
			}
		}else{
			var ths = $("#basic_title_table>thead>tr>th").length- len;
			$("#basic_title_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#basic_work_experience_list").empty();
		if(data.workExperiences.length > 0){
			for(var i = 0; i < data.workExperiences.length; i++){
				setAttachmentType(data.workExperiences[i].attachments, "4");
				modifyBasicWorkExperienceRow(data.workExperiences[i]);
			}
		}else{
			var ths = $("#basic_work_experience_table>thead>tr>th").length- len;
			$("#basic_work_experience_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#post_post_list").empty();
		if(data.posts.length > 0){
			for(var i = 0; i < data.posts.length; i++){
				setAttachmentType(data.posts[i].attachments, "5");
				modifyPostPostRow(data.posts[i]);
			}
		}else{
			var ths = $("#post_post_table>thead>tr>th").length- len;
			$("#post_post_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#post_technical_list").empty();
		if(data.technicalExperiences.length > 0){
			for(var i = 0; i < data.technicalExperiences.length; i++){
				setAttachmentType(data.technicalExperiences[i].attachments, "6");
				modifyPostTechnicalExperienceRow(data.technicalExperiences[i]);
			}
		}else{
			var ths = $("#post_technical_table>thead>tr>th").length- len;
			$("#post_technical_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#maintenance_license_list").empty();
		if(data.maintenanceLicenses.length > 0){
			for(var i = 0; i < data.maintenanceLicenses.length; i++){
				setAttachmentType(data.maintenanceLicenses[i].attachments, "7");
				modifyMaintenanceLicenseRow(data.maintenanceLicenses[i]);
			}
		}else{
			var ths = $("#maintenance_license_table>thead>tr>th").length- len;
			$("#maintenance_license_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#type_license_list").empty();
		if(data.typeLicenses.length > 0){
			for(var i = 0; i < data.typeLicenses.length; i++){
				setAttachmentType(data.typeLicenses[i].attachments, "8");
				modifyTypeLicenseRow(data.typeLicenses[i]);
			}
		}else{
			var ths = $("#type_license_table>thead>tr>th").length- len;
			$("#type_license_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#maintenance_level_list").empty();
		if(data.grades.length > 0){
			for(var i = 0; i < data.grades.length; i++){
				setAttachmentType(data.grades[i].attachments, "9");
				modifyMaintenanceLevelRow(data.grades[i]);
			}
		}else{
			var ths = $("#maintenance_level_table>thead>tr>th").length- len;
			$("#maintenance_level_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#training_records_list").empty();
		if(data.trainings.length > 0){
			for(var i = 0; i < data.trainings.length; i++){
				setAttachmentType(data.trainings[i].attachments, "10");
				modifyTrainingRecordsRow(data.trainings[i]);
			}
		}else{
			var ths = $("#training_records_table>thead>tr>th").length- len;
			$("#training_records_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#award_business_list").empty();
		if(data.businessAssessments.length > 0){
			for(var i = 0; i < data.businessAssessments.length; i++){
				setAttachmentType(data.businessAssessments[i].attachments, "11");
				modifyAwardBusinessRow(data.businessAssessments[i]);
			}
		}else{
			var ths = $("#award_business_table>thead>tr>th").length- len;
			$("#award_business_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#award_scholarship_list").empty();
		if(data.scholarships.length > 0){
			for(var i = 0; i < data.scholarships.length; i++){
				setAttachmentType(data.scholarships[i].attachments, "12");
				modifyAwardScholarshipRow(data.scholarships[i]);
			}
		}else{
			var ths = $("#award_scholarship_table>thead>tr>th").length- len;
			$("#award_scholarship_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#award_citation_list").empty();
		if(data.citationRecords.length > 0){
			for(var i = 0; i < data.citationRecords.length; i++){
				setAttachmentType(data.citationRecords[i].attachments, "13");
				modifyAwardCitationRow(data.citationRecords[i]);
			}
		}else{
			var ths = $("#award_citation_table>thead>tr>th").length- len;
			$("#award_citation_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#award_incident_list").empty();
		if(data.incidentSituations.length > 0){
			for(var i = 0; i < data.incidentSituations.length; i++){
				setAttachmentType(data.incidentSituations[i].attachments, "14");
				modifyAwardIncidentRow(data.incidentSituations[i]);
			}
		}else{
			var ths = $("#award_incident_table>thead>tr>th").length- len;
			$("#award_incident_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		$("#reputation_unsafe_list").empty();
		if(data.unsafeIncidents.length > 0){
			for(var i = 0; i < data.unsafeIncidents.length; i++){
				setAttachmentType(data.unsafeIncidents[i].attachments, "15");
				modifyReputationUnsafeRow(data.unsafeIncidents[i]);
			}
		}else{
			var ths = $("#reputation_unsafe_table>thead>tr>th").length- len;
			$("#reputation_unsafe_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		$("#reputation_dishonest_list").empty();
		if(data.dishonestBehaviors.length > 0){
			for(var i = 0; i < data.dishonestBehaviors.length; i++){
				setAttachmentType(data.dishonestBehaviors[i].attachments, "16");
				modifyReputationDishonestRow(data.dishonestBehaviors[i]);
			}
		}else{
			var ths = $("#reputation_dishonest_table>thead>tr>th").length- len;
			$("#reputation_dishonest_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
		
		setAttachmentType(data.attachments, "17");
		
		function setAttachmentType(list, type){
			for(var i = 0; i < list.length; i++){
				list[i].type = type;
			}
		}
	}
	
	(function (factory) {
		  if (typeof define === 'function' && define.amd) {
		    // AMD. Register as anonymous module.
		    define(['jquery'], factory);
		  } else if (typeof exports === 'object') {
		    // Node / CommonJS
		    factory(require('jquery'));
		  } else {
		    // Browser globals.
		    factory(jQuery);
		  }
		})(function ($) {

		  'use strict';

		  var console = window.console || { log: function () {} };

		  function CropAvatar($element) {
		    this.$container = $element;

		    this.$avatarView = this.$container.find('.avatar-view');
		    this.$avatar = this.$avatarView.find('img');
		    this.$avatarModal = this.$container.find('#avatar-modal');
		    this.$loading = this.$container.find('.loading');

		    this.$avatarForm = this.$avatarModal.find('.avatar-form');
		    this.$avatarUpload = this.$avatarForm.find('.avatar-upload');
		    this.$avatarSrc = this.$avatarForm.find('.avatar-src');
		    this.$avatarData = this.$avatarForm.find('.avatar-data');
		    this.$avatarInput = this.$avatarForm.find('.avatar-input');
		    this.$avatarSave = this.$avatarForm.find('.avatar-save');
		    this.$avatarBtns = this.$avatarForm.find('.avatar-btns');

		    this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper');
		    this.$avatarPreview = this.$avatarModal.find('.avatar-preview');

		    this.init();
		  }

		  CropAvatar.prototype = {
		    constructor: CropAvatar,

		    support: {
		      fileList: !!$('<input type="file">').prop('files'),
		      blobURLs: !!window.URL && URL.createObjectURL,
		      formData: !!window.FormData
		    },

		    init: function () {
		      this.support.datauri = this.support.fileList && this.support.blobURLs;

		      if (!this.support.formData) {
		        this.initIframe();
		      }

		      this.initTooltip();
		      this.initModal();
		      this.addListener();
		    },

		    addListener: function () {
		      //this.$avatarView.on('click', $.proxy(this.click, this));
		      this.$avatarInput.on('change', $.proxy(this.change, this));
		      this.$avatarForm.on('submit', $.proxy(this.submit, this));
		      this.$avatarBtns.on('click', $.proxy(this.rotate, this));
		    },

		    initTooltip: function () {
		      this.$avatarView.tooltip({
		        placement: 'bottom'
		      });
		    },

		    initModal: function () {
		      this.$avatarModal.modal({
		        show: false
		      });
		    },

		    initPreview: function () {
		      var url = this.$avatar.attr('src');

		      this.$avatarPreview.html('<img src="' + url + '">');
		    },

		    initIframe: function () {
		      var target = 'upload-iframe-' + (new Date()).getTime();
		      var $iframe = $('<iframe>').attr({
		            name: target,
		            src: ''
		          });
		      var _this = this;

		      // Ready ifrmae
		      $iframe.one('load', function () {

		        // respond response
		        $iframe.on('load', function () {
		          var data;

		          try {
		            data = $(this).contents().find('body').text();
		          } catch (e) {
		            console.log(e.message);
		          }

		          if (data) {
		            try {
		              data = $.parseJSON(data);
		            } catch (e) {
		              console.log(e.message);
		            }

		            _this.submitDone(data);
		          } else {
		            _this.submitFail('Image upload failed!');
		          }

		          _this.submitEnd();

		        });
		      });

		      this.$iframe = $iframe;
		      this.$avatarForm.attr('target', target).after($iframe.hide());
		    },

		    click: function () {
		      this.$avatarModal.modal('show');
		      this.initPreview();
		    },

		    change: function () {
		      var files;
		      var file;

		      if (this.support.datauri) {
		        files = this.$avatarInput.prop('files');

		        if (files.length > 0) {
		          file = files[0];

		          if (this.isImageFile(file)) {
		            if (this.url) {
		              URL.revokeObjectURL(this.url); // Revoke the old one
		            }

		            this.url = URL.createObjectURL(file);
		            this.startCropper();
		          }
		        }
		      } else {
		        file = this.$avatarInput.val();

		        if (this.isImageFile(file)) {
		          this.syncUpload();
		        }
		      }
		    },

		    submit: function () {
		      if (!this.$avatarSrc.val() && !this.$avatarInput.val()) {
		        return false;
		      }

		      if (this.support.formData) {
		        this.ajaxUpload();
		    	this.$avatar.attr('src', this.$avatarWrapper.find("img.cropper-hidden").attr('src'));
		    	this.$avatarModal.modal('hide');
		        return false;
		      }
		    },

		    rotate: function (e) {
		      var data;

		      if (this.active) {
		        data = $(e.target).data();

		        if (data.method) {
		          this.$img.cropper(data.method, data.option);
		        }
		      }
		    },

		    isImageFile: function (file) {
		      if (file.type) {
		        return /^image\/\w+$/.test(file.type);
		      } else {
		        return /\.(jpg|jpeg|png|gif)$/.test(file);
		      }
		    },

		    startCropper: function () {
		      var _this = this;

		      if (this.active) {
		        this.$img.cropper('replace', this.url);
		      } else {
		        this.$img = $('<img src="' + this.url + '">');
		        this.$avatarWrapper.empty().html(this.$img);
		        this.$img.cropper({
		          aspectRatio: 1,
		          preview: this.$avatarPreview.selector,
		          crop: function (e) {
		            var json = [
		                  '{"x":' + e.x,
		                  '"y":' + e.y,
		                  '"height":' + e.height,
		                  '"width":' + e.width,
		                  '"rotate":' + e.rotate + '}'
		                ].join();

		            _this.$avatarData.val(json);
		          }
		        });

		        this.active = true;
		      }

		      this.$avatarModal.one('hidden.bs.modal', function () {
		        _this.$avatarPreview.empty();
		        _this.stopCropper();
		      });
		    },

		    stopCropper: function () {
		      if (this.active) {
		        this.$img.cropper('destroy');
		        this.$img.remove();
		        this.active = false;
		      }
		    },

		    ajaxUpload: function () {
				var _this = this;
				//获取裁剪后的画板转换成二进制传输
				var dataURL = this.$img.cropper("getCroppedCanvas");
				var imgurl = dataURL.toDataURL("image/jpeg",1); 
				var blob = dataURLToBlob(imgurl);
					var url = basePath + "/training/faculty/uploadPic";
					var data = new FormData();
					//获取文件后缀
					var input = _this.$avatarInput.val();
					//构造图片名
					var fileName = new Date().getTime()+".jpg";
					data.append('myfile',blob,fileName);
					var json = _this.$avatarData.val();
					data.append('json',json);
					$.ajax(url, {
						type : 'post',
						data : data,
						dataType : 'json',
						processData : false,
						contentType : false,
						
						beforeSend : function() {
							_this.submitStart();
						},
						
						success : function(data) {
							_this.submitDone(data);
						},
						
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							_this.submitFail(textStatus || errorThrown);
						},
						
						complete : function() {
							_this.submitEnd();
						}
					});
		    },

		    syncUpload: function () {
		      this.$avatarSave.click();
		    },

		    submitStart: function () {
		      this.$loading.fadeIn();
		    },

		    submitDone: function (data) {

				if ($.isPlainObject(data) && data.state === 200) {
					if (data.row) {
						var result = data.row;
						console.info(result);
						$("#zpdzD").val(result.zpdzD);
						$("#zpdzX").val(result.zpdzX);
						var str = result.zpdzD.replaceAll(/\\/g,"&");
						var url = basePath +"/training/faculty/preview/"+str+"/1";
						this.url = url;

						if (this.support.datauri || this.uploaded) {
							this.uploaded = false;
							this.cropDone();
						} else {
							this.uploaded = true;
							this.$avatarSrc.val(this.url);
							this.startCropper();
						}

						this.$avatarInput.val('');
					} else if (data.message) {
						this.alert(data.message);
					}
				} else {
					this.alert('Failed to response');
				}
		    },

		    submitFail: function (msg) {
		      this.alert(msg);
		    },

		    submitEnd: function () {
		      this.$loading.fadeOut();
		    },

		    cropDone: function () {
		      this.$avatarForm.get(0).reset();
		      this.$avatar.attr('src', this.url);
		      this.stopCropper();
		      this.$avatarModal.modal('hide');
		    },

		    alert: function (msg) {
		      var $alert = [
		            '<div class="alert alert-danger avatar-alert alert-dismissable">',
		              '<button type="button" class="close" data-dismiss="alert">&times;</button>',
		              msg,
		            '</div>'
		          ].join('');

		      this.$avatarUpload.after($alert);
		    }
		  };

		  $(function () {
		    return new CropAvatar($('#basic_div'));
		  });

		});
	function dataURLToBlob(dataurl){
		var arr = dataurl.split(',');
		var mime = arr[0].match(/:(.*?);/)[1];
		var bstr = atob(arr[1]);
		var n = bstr.length;
		var u8arr = new Uint8Array(n);
		while(n--){
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([u8arr], {type:mime});
	}

	
	//绑定内外部选择按钮，显示与隐藏搜索按钮
	function onchlickradio(){
		var value= $("input[name='wbbs']:checked").val();
		if(value=='1'){
			$("#xm").attr("ondblclick","onchlickradio()();");
			$(".colse-div").addClass("readonly-style");  			//去掉文本框为白的的样式
			$("#xm").attr("readonly",true);
			$(".required").show();
			$("#wxryid").val('');
			$("#xm").val('');
		}else{
			$("#xm").attr("ondblclick","");
			$("#xm").attr("readonly",false);
			$(".colse-div").removeClass("readonly-style");  			//去掉文本框为白的的样式
			$(".required").hide();
			$("#wxryid").val('');
			$("#xm").val('');
		}
	}