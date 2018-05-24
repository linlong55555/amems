




	/**
	 * 新增维修技术等级升级记录
	 */
	function addMaintenanceLevel(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#maintenance_level_modal input").val("");
		$("#maintenance_level_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#maintenance_level_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("maintenance_level_modal");
	}
	
	/**
	 * 修改维修技术等级升级记录
	 */
	function editMaintenanceLevel(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.grade.get(rowid);
		$("#maintenance_level_modal_dj").val(obj.dj);
		$("#maintenance_level_modal_rq").val(obj.rq);
		$("#maintenance_level_modal_zy").val(obj.zy);
		$("#maintenance_level_modal_jx").val(obj.jx);
		$("#maintenance_level_modal_khcj").val(obj.khcj);
		$("#maintenance_level_modal_pzr").val(obj.pzr);
		$("#maintenance_level_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#maintenance_level_modal", rowid);
		ModalUtil.showModal("maintenance_level_modal");
	}
	
	/**
	 * 确定编维修技术等级升级记录
	 */
	function confirmMaintenanceLevel(){
		if(!validateMaintenanceLevel()){
			return false;
		}
		$("#maintenance_level_list .non-choice").remove();
		var grade = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				dj : $("#maintenance_level_modal_dj").val(),
				rq : $("#maintenance_level_modal_rq").val(),
				zy : $("#maintenance_level_modal_zy").val(),
				jx : $("#maintenance_level_modal_jx").val(),
				khcj : $("#maintenance_level_modal_khcj").val(),
				pzr : $("#maintenance_level_modal_pzr").val(),
				bz : $("#maintenance_level_modal_bz").val(),
				attachments : getAttachments("9")
		};
		wxjssjjl=[];//维修技术升级记录
		modifyMaintenanceLevelRow(grade);
		personnel.grade.merge(grade);
		$("#maintenance_level_modal").modal("hide");
	}
	
	/**
	 * 验证维修技术等级升级记录
	 */
	function validateMaintenanceLevel(){
		var dj = $("#maintenance_level_modal_dj").val();
		if(!dj){
			AlertUtil.showErrorMessage("请输入级别！");
			return false;
		}
		var rq = $("#maintenance_level_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var jx = $("#maintenance_level_modal_jx").val();
		if(jx && !/^[\x00-\xff]*$/.test(jx)){
			AlertUtil.showErrorMessage("机型不能包含中文！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-维修技术等级升级记录
	 */
	function modifyMaintenanceLevelRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#maintenance_level_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editMaintenanceLevel(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteMaintenanceLevel(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dj)+"' name='dj'>"+StringUtil.escapeStr(obj.dj)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zy)+"' name='zy'>"+StringUtil.escapeStr(obj.zy)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.jx)+"' name='jx'>"+StringUtil.escapeStr(obj.jx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.khcj)+"' name='khcj'>"+StringUtil.escapeStr(obj.khcj)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.pzr)+"' name='pzr'>"+StringUtil.escapeStr(obj.pzr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"wxjssjjl")+"</td>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='dj']").text(obj.dj);
			row.find("[name='dj']").attr("title",obj.dj);
			row.find("[name='rq']").text(obj.rq);
			row.find("[name='rq']").attr("title",obj.rq);
			row.find("[name='zy']").text(obj.zy);
			row.find("[name='zy']").attr("title",obj.zy);
			row.find("[name='jx']").text(obj.jx);
			row.find("[name='jx']").attr("title",obj.jx);
			row.find("[name='khcj']").text(obj.khcj);
			row.find("[name='khcj']").attr("title",obj.khcj);
			row.find("[name='pzr']").text(obj.pzr);
			row.find("[name='pzr']").attr("title",obj.pzr);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"wxjssjjl"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
		}
		
	}
	
	/**
	 * 删除行-维修技术等级升级记录
	 */
	function deleteMaintenanceLevel(obj){
		var row = $(obj).parent().parent();
		personnel.grade.remove(row.attr("id"));
		row.remove();
		if($("#maintenance_level_list>tr").length == 0){
			var ths = $("#maintenance_level_table>thead>tr>th:visible").length;
			$("#maintenance_level_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}