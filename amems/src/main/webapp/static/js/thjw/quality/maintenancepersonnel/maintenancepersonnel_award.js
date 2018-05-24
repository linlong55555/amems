



	/**
	 * 新增业务考核记录
	 */
	function addAwardBusiness(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#award_business_modal input").val("");
		$("#award_business_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#award_business_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("award_business_modal");
	}
	
	/**
	 * 修改业务考核记录
	 */
	function editAwardBusiness(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.businessAssessment.get(rowid);
		$("#award_business_modal_rq").val(obj.rq);
		$("#award_business_modal_jx").val(obj.jx);
		$("#award_business_modal_zy").val(obj.zy);
		$("#award_business_modal_cj").val(obj.cj);
		$("#award_business_modal_khr").val(obj.khr);
		$("#award_business_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#award_business_modal", rowid);
		ModalUtil.showModal("award_business_modal");
	}
	
	/**
	 * 确定编辑业务考核记录
	 */
	function confirmAwardBusiness(){
		if(!validateAwardBusiness()){
			return false;
		}
		$("#award_business_list .non-choice").remove();
		var businessAssessment = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				rq : $("#award_business_modal_rq").val(),
				jx : $("#award_business_modal_jx").val(),
				zy : $("#award_business_modal_zy").val(),
				cj : $("#award_business_modal_cj").val(),
				khr : $("#award_business_modal_khr").val(),
				bz : $("#award_business_modal_bz").val(),
				attachments : getAttachments("11")
		};
		ywkhjl=[];//业务考核记录
		modifyAwardBusinessRow(businessAssessment);
		personnel.businessAssessment.merge(businessAssessment);
		$("#award_business_modal").modal("hide");
	}
	
	/**
	 * 验证业务考核记录
	 */
	function validateAwardBusiness(){
		var rq = $("#award_business_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var jx = $("#award_business_modal_jx").val();
		if(!jx){
			AlertUtil.showErrorMessage("请输入机型！");
			return false;
		}
		if(jx && !/^[\x00-\xff]*$/.test(jx)){
			AlertUtil.showErrorMessage("机型不能包含中文！");
			return false;
		}
		var cj = $("#award_business_modal_cj").val();
		if(!cj){
			AlertUtil.showErrorMessage("请输入成绩！");
			return false;
		}
		var khr = $("#award_business_modal_khr").val();
		if(!khr){
			AlertUtil.showErrorMessage("请输入考核人！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-业务考核记录
	 */
	function modifyAwardBusinessRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#award_business_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editAwardBusiness(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteAwardBusiness(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jx)+"' name='jx'>"+StringUtil.escapeStr(obj.jx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zy)+"' name='zy'>"+StringUtil.escapeStr(obj.zy)+"</td>",
			       "<td style='text-align: right;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cj)+"' name='cj'>"+StringUtil.escapeStr(obj.cj)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.khr)+"' name='khr'>"+StringUtil.escapeStr(obj.khr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"ywkhjl")+"</td>",
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
			row.find("[name='zy']").text(obj.zy);
			row.find("[name='zy']").attr("title",obj.zy);
			row.find("[name='cj']").text(obj.cj);
			row.find("[name='cj']").attr("title",obj.cj);
			row.find("[name='khr']").text(obj.khr);
			row.find("[name='khr']").attr("title",obj.khr);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"ywkhjl"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-业务考核记录
	 */
	function deleteAwardBusiness(obj){
		var row = $(obj).parent().parent();
		personnel.businessAssessment.remove(row.attr("id"));
		row.remove();
		if($("#award_business_list>tr").length == 0){
			var ths = $("#award_business_table>thead>tr>th:visible").length;
			$("#award_business_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 新增学术成就
	 */
	function addAwardScholarship(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#award_scholarship_modal input").val("");
		$("#award_scholarship_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#award_scholarship_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("award_scholarship_modal");
	}
	
	/**
	 * 修改学术成就
	 */
	function editAwardScholarship(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.scholarship.get(rowid);
		$("#award_scholarship_modal_jcrq").val(obj.jcrq);
		$("#award_scholarship_modal_cljg").val(obj.cljg);
		$("#award_scholarship_modal_sm").val(obj.sm);
		$(".date-picker").datepicker("update");
		loadAttachments("#award_scholarship_modal", rowid);
		ModalUtil.showModal("award_scholarship_modal");
	}
	
	/**
	 * 确定编辑学术成就
	 */
	function confirmAwardScholarship(){
		if(!validateAwardScholarship()){
			return false;
		}
		$("#award_scholarship_list .non-choice").remove();
		var scholarship = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				jcrq : $("#award_scholarship_modal_jcrq").val(),
				cljg : $("#award_scholarship_modal_cljg").val(),
				sm : $("#award_scholarship_modal_sm").val(),
				attachments : getAttachments("12")
		};
		xscj=[];//学术成就
		modifyAwardScholarshipRow(scholarship);
		personnel.scholarship.merge(scholarship);
		$("#award_scholarship_modal").modal("hide");
	}
	
	/**
	 * 验证学术成就
	 */
	function validateAwardScholarship(){
		var jcrq = $("#award_scholarship_modal_jcrq").val();
		if(!jcrq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var cljg = $("#award_scholarship_modal_cljg").val();
		if(!cljg){
			AlertUtil.showErrorMessage("请输入成就项目！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-学术成就
	 */
	function modifyAwardScholarshipRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			console.info(Math.uuid().toLowerCase()+"******************************");
			$("#award_scholarship_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editAwardScholarship(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteAwardScholarship(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cljg)+"' name='cljg'>"+StringUtil.escapeStr(obj.cljg)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jcrq)+"' name='jcrq'>"+StringUtil.escapeStr(obj.jcrq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sm)+"' name='sm'>"+StringUtil.escapeStr(obj.sm)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"xscj")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='jcrq']").text(obj.jcrq);
			row.find("[name='jcrq']").attr("title",obj.jcrq);
			row.find("[name='cljg']").text(obj.cljg);
			row.find("[name='cljg']").attr("title",obj.cljg);
			row.find("[name='sm']").text(obj.sm);
			row.find("[name='sm']").attr("title",obj.sm);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"xscj"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-学术成就
	 */
	function deleteAwardScholarship(obj){
		var row = $(obj).parent().parent();
		personnel.scholarship.remove(row.attr("id"));
		row.remove();
		if($("#award_scholarship_list>tr").length == 0){
			var ths = $("#award_scholarship_table>thead>tr>th:visible").length;
			$("#award_scholarship_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 新增嘉奖记录
	 */
	function addAwardCitation(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#award_citation_modal input").val("");
		$("#award_citation_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#award_citation_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("award_citation_modal");
	}
	
	/**
	 * 修改嘉奖记录
	 */
	function editAwardCitation(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.citationRecord.get(rowid);
		$("#award_citation_modal_jcrq").val(obj.jcrq);
		$("#award_citation_modal_cljg").val(obj.cljg);
		$("#award_citation_modal_sm").val(obj.sm);
		$(".date-picker").datepicker("update");
		loadAttachments("#award_citation_modal", rowid);
		ModalUtil.showModal("award_citation_modal");
	}
	
	/**
	 * 确定编辑嘉奖记录
	 */
	function confirmAwardCitation(){
		if(!validateAwardCitation()){
			return false;
		}
		$("#award_citation_list .non-choice").remove();
		var citationRecord = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				jcrq : $("#award_citation_modal_jcrq").val(),
				cljg : $("#award_citation_modal_cljg").val(),
				sm : $("#award_citation_modal_sm").val(),
				attachments : getAttachments("13")
		};
		jjjl=[];//嘉奖记录
		modifyAwardCitationRow(citationRecord);
		personnel.citationRecord.merge(citationRecord);
		$("#award_citation_modal").modal("hide");
	}
	
	/**
	 * 验证嘉奖记录
	 */
	function validateAwardCitation(){
		var jcrq = $("#award_citation_modal_jcrq").val();
		if(!jcrq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var cljg = $("#award_citation_modal_cljg").val();
		if(!cljg){
			AlertUtil.showErrorMessage("请输入事件！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-嘉奖记录
	 */
	function modifyAwardCitationRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#award_citation_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editAwardCitation(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteAwardCitation(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.cljg)+"' name='cljg'>"+StringUtil.escapeStr(obj.cljg)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jcrq)+"' name='jcrq'>"+StringUtil.escapeStr(obj.jcrq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sm)+"' name='sm'>"+StringUtil.escapeStr(obj.sm)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"jjjl")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='jcrq']").text(obj.jcrq);
			row.find("[name='jcrq']").attr("title",obj.jcrq);
			row.find("[name='cljg']").text(obj.cljg);
			row.find("[name='cljg']").attr("title",obj.cljg);
			row.find("[name='sm']").text(obj.sm);
			row.find("[name='sm']").attr("title",obj.sm);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"jjjl"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-嘉奖记录
	 */
	function deleteAwardCitation(obj){
		var row = $(obj).parent().parent();
		personnel.citationRecord.remove(row.attr("id"));
		row.remove();
		if($("#award_citation_list>tr").length == 0){
			var ths = $("#award_citation_table>thead>tr>th:visible").length;
			$("#award_citation_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	/**
	 * 新增事故征候情况
	 */
	function addAwardIncident(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#award_incident_modal input").val("");
		$("#award_incident_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#award_incident_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("award_incident_modal");
	}
	
	/**
	 * 修改事故征候情况
	 */
	function editAwardIncident(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.incidentSituation.get(rowid);
		$("#award_incident_modal_rq").val(obj.rq);
		$("#award_incident_modal_dd").val(obj.dd);
		$("#award_incident_modal_sjjg").val(obj.sjjg);
		$("#award_incident_modal_hg").val(obj.hg);
		$("#award_incident_modal_dcjl").val(obj.dcjl);
		$(".date-picker").datepicker("update");
		loadAttachments("#award_incident_modal", rowid);
		ModalUtil.showModal("award_incident_modal");
	}
	
	/**
	 * 确定编辑事故征候情况
	 */
	function confirmAwardIncident(){
		if(!validateAwardIncident()){
			return false;
		}
		$("#award_incident_list .non-choice").remove();
		var incidentSituation = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				rq : $("#award_incident_modal_rq").val(),
				dd : $("#award_incident_modal_dd").val(),
				sjjg : $("#award_incident_modal_sjjg").val(),
				hg : $("#award_incident_modal_hg").val(),
				dcjl : $("#award_incident_modal_dcjl").val(),
				attachments : getAttachments("14")
		};
		sgzhqk=[];//事故征候情况
		modifyAwardIncidentRow(incidentSituation);
		personnel.incidentSituation.merge(incidentSituation);
		$("#award_incident_modal").modal("hide");
	}
	
	/**
	 * 验证事故征候情况
	 */
	function validateAwardIncident(){
		var rq = $("#award_incident_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var sjjg = $("#award_incident_modal_sjjg").val();
		if(!sjjg){
			AlertUtil.showErrorMessage("请输入事故征候！");
			return false;
		}
		var dcjl = $("#award_incident_modal_dcjl").val();
		if(!dcjl){
			AlertUtil.showErrorMessage("请输入处分情况！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-事故征候情况
	 */
	function modifyAwardIncidentRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#award_incident_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editAwardIncident(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteAwardIncident(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dd)+"' name='dd'>"+StringUtil.escapeStr(obj.dd)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjjg)+"' name='sjjg'>"+StringUtil.escapeStr(obj.sjjg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.hg)+"' name='hg'>"+StringUtil.escapeStr(obj.hg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dcjl)+"' name='dcjl'>"+StringUtil.escapeStr(obj.dcjl)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"sgzhqk")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='rq']").text(obj.rq);
			row.find("[name='rq']").attr("title",obj.rq);
			row.find("[name='dd']").text(obj.dd);
			row.find("[name='dd']").attr("title",obj.dd);
			row.find("[name='sjjg']").text(obj.sjjg);
			row.find("[name='sjjg']").attr("title",obj.sjjg);
			row.find("[name='hg']").text(obj.hg);
			row.find("[name='hg']").attr("title",obj.hg);
			row.find("[name='dcjl']").text(obj.dcjl);
			row.find("[name='dcjl']").attr("title",obj.dcjl);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"sgzhqk"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-事故征候情况
	 */
	function deleteAwardIncident(obj){
		var row = $(obj).parent().parent();
		personnel.incidentSituation.remove(row.attr("id"));
		row.remove();
		if($("#award_incident_list>tr").length == 0){
			var ths = $("#award_incident_table>thead>tr>th:visible").length;
			$("#award_incident_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}