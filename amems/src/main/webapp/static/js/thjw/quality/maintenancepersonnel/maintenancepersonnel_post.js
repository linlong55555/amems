




	/**
	 * 新增岗位/职务
	 */
	function addPostPost(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#post_post_modal input").val("");
		$("#post_post_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#post_post_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("post_post_modal");
	}
	
	/**
	 * 修改岗位/职务
	 */
	function editPostPost(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.post.get(rowid);
		$("#post_post_modal_ksrq").val(obj.ksrq);
		$("#post_post_modal_jsrq").val(obj.jsrq);
		$("#post_post_modal_gwzw").val(obj.gwzw);
		$("#post_post_modal_dwbm").val(obj.dwbm);
		$("#post_post_modal_gzfw").val(obj.gzfw);
		$("#post_post_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#post_post_modal", rowid);
		ModalUtil.showModal("post_post_modal");
	}
	
	/**
	 * 确定编辑岗位/职务
	 */
	function confirmPostPost(){
		if(!validatePostPost()){
			return false;
		}
		$("#post_post_list .non-choice").remove();
		var post = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				ksrq : $("#post_post_modal_ksrq").val(),
				jsrq : $("#post_post_modal_jsrq").val(),
				gwzw : $("#post_post_modal_gwzw").val(),
				dwbm : $("#post_post_modal_dwbm").val(),
				gzfw : $("#post_post_modal_gzfw").val(),
				bz : $("#post_post_modal_bz").val(),
				attachments : getAttachments("5")
		};
		gwzw=[];//岗位职务
		modifyPostPostRow(post);
		personnel.post.merge(post);
		$("#post_post_modal").modal("hide");
	}
	
	/**
	 * 验证岗位/职务
	 */
	function validatePostPost(){
		var ksrq = $("#post_post_modal_ksrq").val();
		if(!ksrq){
			AlertUtil.showErrorMessage("请输入开始时间！");
			return false;
		}
		var jsrq = $("#post_post_modal_jsrq").val();
		if(jsrq && compareDate(ksrq, jsrq) > 0){
			AlertUtil.showErrorMessage("结束日期不能小于开始时间！");
			return false;
		}
		var gwzw = $("#post_post_modal_gwzw").val();
		if(!gwzw){
			AlertUtil.showErrorMessage("请输入岗位/职务！");
			return false;
		}
		var dwbm = $("#post_post_modal_dwbm").val();
		if(!dwbm){
			AlertUtil.showErrorMessage("请输入工作单位/部门！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-岗位/职务
	 */
	function modifyPostPostRow(obj){
		var timestr = obj.ksrq + "至" + (obj.jsrq||"今");
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#post_post_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editPostPost(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deletePostPost(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='time'>"+StringUtil.escapeStr(timestr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gwzw)+"' name='gwzw'>"+StringUtil.escapeStr(obj.gwzw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dwbm)+"' name='dwbm'>"+StringUtil.escapeStr(obj.dwbm)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gzfw)+"' name='gzfw'>"+StringUtil.escapeStr(obj.gzfw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"gwzw")+"</td>",
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
			row.find("[name='gwzw']").text(obj.gwzw);
			row.find("[name='gwzw']").attr("title",obj.gwzw);
			row.find("[name='gzfw']").text(obj.gzfw);
			row.find("[name='gzfw']").attr("title",obj.gzfw);
			row.find("[name='dwbm']").text(obj.dwbm);
			row.find("[name='dwbm']").attr("title",obj.dwbm);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"gwzw"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='ksrq']").val(obj.ksrq||"");
			row.find("[name='jsrq']").val(obj.jsrq||"");
		}
		
	}
	
	/**
	 * 删除行-岗位/职务
	 */
	function deletePostPost(obj){
		var row = $(obj).parent().parent();
		personnel.post.remove(row.attr("id"));
		row.remove();
		if($("#post_post_list>tr").length == 0){
			var ths = $("#post_post_table>thead>tr>th:visible").length;
			$("#post_post_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	

	
	
	
	
	
	
	/**
	 * 新增技术履历
	 */
	function addPostTechnicalExperience(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#post_technical_experience_modal input").val("");
		$("#post_technical_experience_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#post_technical_experience_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("post_technical_experience_modal");
	}
	
	/**
	 * 修改技术履历
	 */
	function editPostTechnicalExperience(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.technicalExperience.get(rowid);
		$("#post_technical_experience_modal_rq").val(obj.rq);
		$("#post_technical_experience_modal_jx").val(obj.jx);
		$("#post_technical_experience_modal_dwbm").val(obj.dwbm);
		$("#post_technical_experience_modal_zw").val(obj.zw);
		$("#post_technical_experience_modal_gzfw").val(obj.gzfw);
		$("#post_technical_experience_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#post_technical_experience_modal", rowid);
		ModalUtil.showModal("post_technical_experience_modal");
	}
	
	/**
	 * 确定编辑技术履历
	 */
	function confirmPostTechnicalExperience(){
		if(!validatePostTechnicalExperience()){
			return false;
		}
		$("#post_technical_list .non-choice").remove();
		var post = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				rq : $("#post_technical_experience_modal_rq").val(),
				jx : $("#post_technical_experience_modal_jx").val(),
				dwbm : $("#post_technical_experience_modal_dwbm").val(),
				zw : $("#post_technical_experience_modal_zw").val(),
				gzfw : $("#post_technical_experience_modal_gzfw").val(),
				bz : $("#post_technical_experience_modal_bz").val(),
				attachments : getAttachments("6")
		};
		jsll=[];//技术履历
		modifyPostTechnicalExperienceRow(post);
		personnel.technicalExperience.merge(post);
		$("#post_technical_experience_modal").modal("hide");
	}
	
	/**
	 * 验证技术履历
	 */
	function validatePostTechnicalExperience(){
		var rq = $("#post_technical_experience_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var jx = $("#post_technical_experience_modal_jx").val();
		if(jx && !/^[\x00-\xff]*$/.test(jx)){
			AlertUtil.showErrorMessage("机型不能包含中文！");
			return false;
		}
		var dwbm = $("#post_technical_experience_modal_dwbm").val();
		if(!dwbm){
			AlertUtil.showErrorMessage("请输入工作单位/部门！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-技术履历
	 */
	function modifyPostTechnicalExperienceRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#post_technical_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editPostTechnicalExperience(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deletePostTechnicalExperience(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jx)+"' name='jx'>"+StringUtil.escapeStr(obj.jx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dwbm)+"' name='dwbm'>"+StringUtil.escapeStr(obj.dwbm)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zw)+"' name='zw'>"+StringUtil.escapeStr(obj.zw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.gzfw)+"' name='gzfw'>"+StringUtil.escapeStr(obj.gzfw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"jsll")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='rq']").text(obj.rq);
			row.find("[name='rq']").attr("title",obj.rq);
			row.find("[name='jx']").text(obj.jx);
			row.find("[name='jx']").attr("title",obj.jx);
			row.find("[name='dwbm']").text(obj.dwbm);
			row.find("[name='dwbm']").attr("title",obj.dwbm);
			row.find("[name='zw']").text(obj.zw);
			row.find("[name='zw']").attr("title",obj.zw);
			row.find("[name='gzfw']").text(obj.gzfw);
			row.find("[name='gzfw']").attr("title",obj.gzfw);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"jsll"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-技术履历
	 */
	function deletePostTechnicalExperience(obj){
		var row = $(obj).parent().parent();
		personnel.technicalExperience.remove(row.attr("id"));
		row.remove();
		if($("#post_technical_list>tr").length == 0){
			var ths = $("#post_technical_table>thead>tr>th:visible").length;
			$("#post_technical_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	