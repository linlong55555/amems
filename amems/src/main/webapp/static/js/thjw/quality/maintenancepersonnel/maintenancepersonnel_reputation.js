




	
	/**
	 * 新增负有责任的不安全事件
	 */
	function addReputationUnsafe(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#reputation_unsafe_modal input").val("");
		$("#reputation_unsafe_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#reputation_unsafe_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("reputation_unsafe_modal");
	}
	
	/**
	 * 修改负有责任的不安全事件
	 */
	function editReputationUnsafe(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.unsafeIncident.get(rowid);
		$("#reputation_unsafe_modal_rq").val(obj.rq);
		$("#reputation_unsafe_modal_dd").val(obj.dd);
		$("#reputation_unsafe_modal_sjry").val(obj.sjry);
		$("#reputation_unsafe_modal_kf").val(obj.kf);
		$("#reputation_unsafe_modal_sjjg").val(obj.sjjg);
		$("#reputation_unsafe_modal_hg").val(obj.hg);
		$("#reputation_unsafe_modal_dcjl").val(obj.dcjl);
		$("#reputation_unsafe_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#reputation_unsafe_modal", rowid);
		ModalUtil.showModal("reputation_unsafe_modal");
	}
	
	/**
	 * 确定编辑负有责任的不安全事件
	 */
	function confirmReputationUnsafe(){
		if(!validateReputationUnsafe()){
			return false;
		}
		$("#reputation_unsafe_list .non-choice").remove();
		var unsafeIncident = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				rq : $("#reputation_unsafe_modal_rq").val(),
				dd : $("#reputation_unsafe_modal_dd").val(),
				sjry : $("#reputation_unsafe_modal_sjry").val(),
				kf : $("#reputation_unsafe_modal_kf").val(),
				sjjg : $("#reputation_unsafe_modal_sjjg").val(),
				hg : $("#reputation_unsafe_modal_hg").val(),
				dcjl : $("#reputation_unsafe_modal_dcjl").val(),
				bz : $("#reputation_unsafe_modal_bz").val(),
				attachments : getAttachments("15")
		};
		baqsj=[];//负有责任的不安全事件
		modifyReputationUnsafeRow(unsafeIncident);
		personnel.unsafeIncident.merge(unsafeIncident);
		$("#reputation_unsafe_modal").modal("hide");
	}
	
	/**
	 * 验证负有责任的不安全事件
	 */
	function validateReputationUnsafe(){
		var rq = $("#reputation_unsafe_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var dcjl = $("#reputation_unsafe_modal_dcjl").val();
		if(!dcjl){
			AlertUtil.showErrorMessage("请输入调查结论！");
			return false;
		}
		var kf = $("#reputation_unsafe_modal_kf").val();
		var reg=/^\d*(\.\d{1,2})?$/;
		if(kf && !reg.test(kf)){
			AlertUtil.showErrorMessage("扣分只能输入正数！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-负有责任的不安全事件
	 */
	function modifyReputationUnsafeRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#reputation_unsafe_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editReputationUnsafe(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteReputationUnsafe(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dd)+"' name='dd'>"+StringUtil.escapeStr(obj.dd)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjry)+"' name='sjry'>"+StringUtil.escapeStr(obj.sjry)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjjg)+"' name='sjjg'>"+StringUtil.escapeStr(obj.sjjg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.hg)+"' name='hg'>"+StringUtil.escapeStr(obj.hg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dcjl)+"' name='dcjl'>"+StringUtil.escapeStr(obj.dcjl)+"</td>",
			       "<td style='text-align: right;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.kf)+"' name='kf'>"+StringUtil.escapeStr(obj.kf)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"baqsj")+"</td>",
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
			row.find("[name='sjry']").text(obj.sjry);
			row.find("[name='sjry']").attr("title",obj.sjry);
			row.find("[name='sjjg']").text(obj.sjjg);
			row.find("[name='sjjg']").attr("title",obj.sjjg);
			row.find("[name='hg']").text(obj.hg);
			row.find("[name='hg']").attr("title",obj.hg);
			row.find("[name='dcjl']").text(obj.dcjl);
			row.find("[name='dcjl']").attr("title",obj.dcjl);
			row.find("[name='kf']").text(obj.kf);
			row.find("[name='kf']").attr("title",obj.kf);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"baqsj"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-负有责任的不安全事件
	 */
	function deleteReputationUnsafe(obj){
		var row = $(obj).parent().parent();
		personnel.unsafeIncident.remove(row.attr("id"));
		row.remove();
		if($("#reputation_unsafe_list>tr").length == 0){
			var ths = $("#reputation_unsafe_table>thead>tr>th:visible").length;
			$("#reputation_unsafe_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 新增不诚信行为
	 */
	function addReputationDishonest(){
		$("#current_id").val("");
		$("#current_rowid").val("");
		$("#reputation_dishonest_modal input").val("");
		$("#reputation_dishonest_modal textarea").val("");
		$(".date-picker").datepicker("update");
		$("#reputation_dishonest_modal [name='filelist']").html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		ModalUtil.showModal("reputation_dishonest_modal");
	}
	
	/**
	 * 修改不诚信行为
	 */
	function editReputationDishonest(id, rowid){
		$("#current_id").val(id);
		$("#current_rowid").val(rowid);
		var obj = personnel.dishonestBehavior.get(rowid);
		$("#reputation_dishonest_modal_rq").val(obj.rq);
		$("#reputation_dishonest_modal_dd").val(obj.dd);
		$("#reputation_dishonest_modal_sjry").val(obj.sjry);
		$("#reputation_dishonest_modal_kf").val(obj.kf);
		$("#reputation_dishonest_modal_sjjg").val(obj.sjjg);
		$("#reputation_dishonest_modal_hg").val(obj.hg);
		$("#reputation_dishonest_modal_dcjl").val(obj.dcjl);
		$("#reputation_dishonest_modal_bz").val(obj.bz);
		$(".date-picker").datepicker("update");
		loadAttachments("#reputation_dishonest_modal", rowid);
		ModalUtil.showModal("reputation_dishonest_modal");
	}
	
	/**
	 * 确定编辑不诚信行为
	 */
	function confirmReputationDishonest(){
		if(!validateReputationDishonest()){
			return false;
		}
		$("#reputation_dishonest_list .non-choice").remove();
		var dishonestBehavior = {
				id : $("#current_id").val(),
				rowid : $("#current_rowid").val(),
				rq : $("#reputation_dishonest_modal_rq").val(),
				dd : $("#reputation_dishonest_modal_dd").val(),
				sjry : $("#reputation_dishonest_modal_sjry").val(),
				kf : $("#reputation_dishonest_modal_kf").val(),
				sjjg : $("#reputation_dishonest_modal_sjjg").val(),
				hg : $("#reputation_dishonest_modal_hg").val(),
				dcjl : $("#reputation_dishonest_modal_dcjl").val(),
				bz : $("#reputation_dishonest_modal_bz").val(),
				attachments : getAttachments("16")
		};
		bcxxw=[];//不诚信行为
		modifyReputationDishonestRow(dishonestBehavior);
		personnel.dishonestBehavior.merge(dishonestBehavior);
		$("#reputation_dishonest_modal").modal("hide");
	}
	
	/**
	 * 验证不诚信行为
	 */
	function validateReputationDishonest(){
		var rq = $("#reputation_dishonest_modal_rq").val();
		if(!rq){
			AlertUtil.showErrorMessage("请输入日期！");
			return false;
		}
		var dcjl = $("#reputation_dishonest_modal_dcjl").val();
		if(!dcjl){
			AlertUtil.showErrorMessage("请输入调查结论！");
			return false;
		}
		var kf = $("#reputation_dishonest_modal_kf").val();
		var reg=/^\d*(\.\d{1,2})?$/;
		if(kf && !reg.test(kf)){
			AlertUtil.showErrorMessage("扣分只能输入正数！");
			return false;
		}
		return true;
	}
	
	/**
	 * 新增/修改-不诚信行为
	 */
	function modifyReputationDishonestRow(obj){
		// 新增
		if(!obj.rowid || $("#"+obj.rowid).length == 0){
			obj.rowid = Math.uuid().toLowerCase();
			$("#reputation_dishonest_list").append(["<tr id='"+(obj.rowid||obj.id||"")+"'>",
			       ($("#type").val()!=3)?"<td style='text-align: center;vertical-align:middle;'>":"",
				   ($("#type").val()!=3)?"<button class='line6' title='修改 Edit' style='padding:0px 6px;' onclick='editReputationDishonest(\""+(obj.id||"")+"\",\""+(obj.rowid||"")+"\")'><i class='fa glyphicon glyphicon-edit color-blue cursor-pointer'></i></button>&nbsp;&nbsp;":"",
				   ($("#type").val()!=3)?"<button class='line6' title='删除 Delete' style='padding:0px 6px;' onclick='deleteReputationDishonest(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button>":"",	   
			       ($("#type").val()!=3)?"</td>":"",
			       "<td style='text-align: center;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.rq)+"' name='rq'>"+StringUtil.escapeStr(obj.rq)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dd)+"' name='dd'>"+StringUtil.escapeStr(obj.dd)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjry)+"' name='sjry'>"+StringUtil.escapeStr(obj.sjry)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.sjjg)+"' name='sjjg'>"+StringUtil.escapeStr(obj.sjjg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.hg)+"' name='hg'>"+StringUtil.escapeStr(obj.hg)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.dcjl)+"' name='dcjl'>"+StringUtil.escapeStr(obj.dcjl)+"</td>",
			       "<td style='text-align: right;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.kf)+"' name='kf'>"+StringUtil.escapeStr(obj.kf)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;' title='"+StringUtil.escapeStr(obj.bz)+"' name='bz'>"+StringUtil.escapeStr(obj.bz)+"</td>",
			       "<td style='text-align: left;vertical-align:middle;position: relative;background-clip: padding-box;' title='"+buildAttachmentTitle(obj.attachments)+"' name='attachment'>"+buildAttachmentContent(obj.attachments,"bcxxw")+"</td>",
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
			row.find("[name='sjry']").text(obj.sjry);
			row.find("[name='sjry']").attr("title",obj.sjry);
			row.find("[name='sjjg']").text(obj.sjjg);
			row.find("[name='sjjg']").attr("title",obj.sjjg);
			row.find("[name='hg']").text(obj.hg);
			row.find("[name='hg']").attr("title",obj.hg);
			row.find("[name='dcjl']").text(obj.dcjl);
			row.find("[name='dcjl']").attr("title",obj.dcjl);
			row.find("[name='kf']").text(obj.kf);
			row.find("[name='kf']").attr("title",obj.kf);
			row.find("[name='bz']").text(obj.bz);
			row.find("[name='bz']").attr("title",obj.bz);
			row.find("[name='attachment']").html(buildAttachmentContent(obj.attachments,"bcxxw"));
			row.find("[name='attachment']").attr("title",buildAttachmentTitle(obj.attachments));
		}
		
	}
	
	/**
	 * 删除行-不诚信行为
	 */
	function deleteReputationDishonest(obj){
		var row = $(obj).parent().parent();
		personnel.dishonestBehavior.remove(row.attr("id"));
		row.remove();
		if($("#reputation_dishonest_list>tr").length == 0){
			var ths = $("#reputation_dishonest_table>thead>tr>th:visible").length;
			$("#reputation_dishonest_list").append("<tr class='non-choice'><td class='text-center' colspan=\""+ths+"\">暂无数据 No data.</td></tr>");
		}
	}