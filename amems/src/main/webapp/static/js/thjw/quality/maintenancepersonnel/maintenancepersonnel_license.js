



	/**
	 * 新增维修执照
	 */
	function addMaintenanceLicense(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#maintenance_license_modal input").val("");
		$("#maintenance_license_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#maintenance_license_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("maintenance_license_modal");
	}
	
	/**
	 * 修改维修执照
	 */
	function editMaintenanceLicense(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.maintenanceLicense.get(rowid);
		$("#maintenance_license_modal_bfdw").val(obj.bfdw);
		$("#maintenance_license_modal_zy").val(obj.zy);
		$("#maintenance_license_modal_zjbh").val(obj.zjbh);
		$("#maintenance_license_modal_bfrq").val(obj.bfrq);
		$("#maintenance_license_modal_yxq_ks").val(obj.yxqKs);
		$("#maintenance_license_modal_yxq_js").val(obj.yxqJs);
		$(".date-picker").datepicker("update");
		loadAttachments("#maintenance_license_modal", rowid);
		ModalUtil.showModal("maintenance_license_modal");
	}
	
	/**
	 * 确定编辑维修执照
	 */
	function confirmMaintenanceLicense(){
		if(!validateMaintenanceLicense()){
			return false;
		}
		$("#maintenance_license_list .non-choice").remove();
		var maintenanceLicense = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				bfdw : $("#maintenance_license_modal_bfdw").val(),
				zy : $("#maintenance_license_modal_zy").val(),
				zjbh : $("#maintenance_license_modal_zjbh").val(),
				bfrq : $("#maintenance_license_modal_bfrq").val(),
				yxqKs : $("#maintenance_license_modal_yxq_ks").val(),
				yxqJs : $("#maintenance_license_modal_yxq_js").val(),
				attachments : getAttachments("7")
		};
		wxzz=[];//维修执照
		modifyMaintenanceLicenseRow(maintenanceLicense);
		personnel.maintenanceLicense.merge(maintenanceLicense);
		$("#maintenance_license_modal").modal("hide");
	}
	
	/**
	 * 验证维修执照
	 */
	function validateMaintenanceLicense(){
		var bfdw = $("#maintenance_license_modal_bfdw").val();
		if(!bfdw){
			AlertUtil.showErrorMessage("请输入颁发单位！");
			return false;
		}
		var zjbh = $("#maintenance_license_modal_zjbh").val();
		if(!zjbh){
			AlertUtil.showErrorMessage("请输入执照号！");
			return false;
		}
		var bfrq = $("#maintenance_license_modal_bfrq").val();
		if(!bfrq){
			AlertUtil.showErrorMessage("请输入颁发日期！");
			return false;
		}
		var yxqKs = $("#maintenance_license_modal_yxq_ks").val();
		var yxqJs = $("#maintenance_license_modal_yxq_js").val();
		if(!yxqJs){
			AlertUtil.showErrorMessage("请输入有效期截止！");
			return false;
		}
		if(yxqKs && compareDate(yxqKs, yxqJs) > 0){
			AlertUtil.showErrorMessage("有效期截止不能小于有效期开始！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-维修执照
	 */
	function modifyMaintenanceLicenseRow(obj){
		var timestr = "";
		if(obj.yxqKs){
			timestr = obj.yxqKs + "~" + obj.yxqJs;
		}else{
			if(obj.yxqJs){
				timestr = "有效期至" + obj.yxqJs;
			}
		}
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#maintenance_license_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editMaintenanceLicense(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteMaintenanceLicense(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bfdw)+"' name='bfdw'>"+StringUtil.escapeStr(obj.bfdw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zy)+"' name='zy'>"+StringUtil.escapeStr(obj.zy)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zjbh)+"' name='zjbh'>"+StringUtil.escapeStr(obj.zjbh)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bfrq)+"' name='bfrq'>"+StringUtil.escapeStr(obj.bfrq)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='time'>"+StringUtil.escapeStr(timestr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"wxzz")+"</td>",
			       "<input type='hidden' name='yxqKs' value='"+(obj.yxqKs||"")+"'>",
			       "<input type='hidden' name='yxqJs' value='"+(obj.yxqJs||"")+"'>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='time']").text(timestr);
			row.find("[name='time']").attr("title",timestr);
			row.find("[name='bfdw']").text(obj.bfdw);
			row.find("[name='bfdw']").attr("title",obj.bfdw);
			row.find("[name='zy']").text(obj.zy);
			row.find("[name='zy']").attr("title",obj.zy);
			row.find("[name='zjbh']").text(obj.zjbh);
			row.find("[name='zjbh']").attr("title",obj.zjbh);
			row.find("[name='bfrq']").text(obj.bfrq);
			row.find("[name='bfrq']").attr("title",obj.bfrq);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"wxzz"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='yxqKs']").val(obj.ksrq||"");
			row.find("[name='yxqJs']").val(obj.jsrq||"");
		}
		
	}
	
	/**
	 * 删除行-维修执照
	 */
	function deleteMaintenanceLicense(obj){
		var row = $(obj).parent().parent();
		personnel.maintenanceLicense.remove(row.attr("id"));
		row.remove();
		if($("#maintenance_license_list>tr").length == 0){
			var ths = $("#maintenance_license_table>thead>tr>th:visible").length;
			$("#maintenance_license_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 新增机型执照
	 */
	function addTypeLicense(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#type_license_modal input").val("");
		$("#type_license_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#type_license_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("type_license_modal");
	}
	
	/**
	 * 修改机型执照
	 */
	function editTypeLicense(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.typeLicense.get(rowid);
		$("#type_license_modal_fjjx").val(obj.fjjx);
		$("#type_license_modal_bfdw").val(obj.bfdw);
		$("#type_license_modal_zy").val(obj.zy);
		$("#type_license_modal_whjb").val(obj.sqdj);
		$("#type_license_modal_bfrq").val(obj.bfrq);
		$("#type_license_modal_yxq_ks").val(obj.yxqKs);
		$("#type_license_modal_yxq_js").val(obj.yxqJs);
		$(".date-picker").datepicker("update");
		loadAttachments("#type_license_modal", rowid);
		ModalUtil.showModal("type_license_modal");
	}
	
	/**
	 * 确定编辑机型执照
	 */
	function confirmTypeLicense(){
		if(!validateTypeLicense()){
			return false;
		}
		$("#type_license_list .non-choice").remove();
		var typeLicense = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				fjjx : $("#type_license_modal_fjjx").val(),
				bfdw : $("#type_license_modal_bfdw").val(),
				zy : $("#type_license_modal_zy").val(),
				sqdj : $("#type_license_modal_whjb").val(),
				bfrq : $("#type_license_modal_bfrq").val(),
				yxqKs : $("#type_license_modal_yxq_ks").val(),
				yxqJs : $("#type_license_modal_yxq_js").val(),
				attachments : getAttachments("8")
		};
		jxzz=[];//机型执照
		modifyTypeLicenseRow(typeLicense);
		personnel.typeLicense.merge(typeLicense);
		$("#type_license_modal").modal("hide");
	}
	
	/**
	 * 验证机型执照
	 */
	function validateTypeLicense(){
		var fjjx = $("#type_license_modal_fjjx").val();
		if(!fjjx){
			AlertUtil.showErrorMessage("请输入机型！");
			return false;
		}
		if(fjjx && !/^[\x00-\xff]*$/.test(fjjx)){
			AlertUtil.showErrorMessage("机型不能包含中文！");
			return false;
		}
		var bfdw = $("#type_license_modal_bfdw").val();
		if(!bfdw){
			AlertUtil.showErrorMessage("请输入颁发单位！");
			return false;
		}
		var yxqKs = $("#type_license_modal_yxq_ks").val();
		var yxqJs = $("#type_license_modal_yxq_js").val();
		if(yxqKs && compareDate(yxqKs, yxqJs) > 0){
			AlertUtil.showErrorMessage("有效期截止不能小于有效期开始！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-机型执照
	 */
	function modifyTypeLicenseRow(obj){
		var timestr = "";
		if(obj.yxqKs){
			timestr = (obj.yxqKs || '') + "~" + (obj.yxqJs || '');
		}else{
			if(obj.yxqJs){
				timestr = "有效期至" + obj.yxqJs;
			}
		}
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#type_license_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editTypeLicense(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteTypeLicense(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bfdw)+"' name='bfdw'>"+StringUtil.escapeStr(obj.bfdw)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.fjjx)+"' name='fjjx'>"+StringUtil.escapeStr(obj.fjjx)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.zy)+"' name='zy'>"+StringUtil.escapeStr(obj.zy)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sqdj)+"' name='sqdj'>"+StringUtil.escapeStr(obj.sqdj)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bfrq)+"' name='bfrq'>"+StringUtil.escapeStr(obj.bfrq)+"</td>",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(timestr)+"' name='time'>"+StringUtil.escapeStr(timestr)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"jxzz")+"</td>",
			       "<input type='hidden' name='yxqKs' value='"+(obj.yxqKs||"")+"'>",
			       "<input type='hidden' name='yxqJs' value='"+(obj.yxqJs||"")+"'>",
			       "<input type='hidden' name='id' value='"+(obj.id||"")+"'>",
			       "</tr>"
			       ].join(""));
		}
		// 修改
		else{
			var row = $("#"+obj.rowid);
			row.find("[name='time']").text(timestr);
			row.find("[name='time']").attr("title",timestr);
			row.find("[name='bfdw']").text(obj.bfdw);
			row.find("[name='bfdw']").attr("title",obj.bfdw);
			row.find("[name='fjjx']").text(obj.fjjx);
			row.find("[name='fjjx']").attr("title",obj.fjjx);
			row.find("[name='zy']").text(obj.zy);
			row.find("[name='zy']").attr("title",obj.zy);
			row.find("[name='sqdj']").text(obj.sqdj);
			row.find("[name='sqdj']").attr("title",obj.sqdj);
			row.find("[name='bfrq']").text(obj.bfrq);
			row.find("[name='bfrq']").attr("title",obj.bfrq);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"jxzz"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
			
			row.find("[name='yxqKs']").val(obj.ksrq||"");
			row.find("[name='yxqJs']").val(obj.jsrq||"");
		}
		
	}
	
	/**
	 * 删除行-机型执照
	 */
	function deleteTypeLicense(obj){
		var row = $(obj).parent().parent();
		personnel.typeLicense.remove(row.attr("id"));
		row.remove();
		if($("#type_license_list>tr").length == 0){
			var ths = $("#type_license_table>thead>tr>th:visible").length;
			$("#type_license_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}