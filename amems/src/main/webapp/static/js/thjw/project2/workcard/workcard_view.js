$(document).ready(function(){
	Navigation(menuCode, '查看工卡', 'View', 'GC-7-2', '刘兵', '2017-08-01', '刘兵', '2017-08-01');//加载导航栏
	work_card_view.init();
});

/**
 * 工卡查看
 */
var work_card_view = {
	id : "work_card_view",
	oldId : '',
	param: {
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(){
		var this_ = this;
		var id = $("#id").val();
		this_.selectById(id,function(obj){
			this_.param.dprtcode = obj.dprtcode;
			//初始化信息
			this_.initInfo(obj);
			this_.resizeVersionWidth();
		});
	},
	initInfo : function(obj){//初始化信息
		this.initBody(obj);
	},
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史版本列表
		return work_card_history_alert_Modal.getHistoryBbList($("#id").val());
	},
	resizeVersionWidth : function(){
		$("#maintenance_version_list div[name='jkxm']").width($("#maintenance_version_jkxm").outerWidth());
		$("#maintenance_version_list div[name='sj']").width($("#maintenance_version_sj").outerWidth());
		$("#maintenance_version_list div[name='zq']").width($("#maintenance_version_zq").outerWidth());
		$("#maintenance_version_list div[name='rc']").width($("#maintenance_version_rc").outerWidth()-10);
	},
	selectById : function(id,obj){//通过id获取数据
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/workcard/selectById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	},
	/**
	 * 初始化页面中只读/失效/显示/隐藏
	 */
	initBody : function(obj){
		this.returnViewData(obj);
		this.setReadOnlyFailure();
		this.initInclude(obj,false);
	},
	/**
	 * 设置只读/失效
	 */
	setReadOnlyFailure : function(){
		//设置input只读
		$("#gkh", $("#"+this.id)).attr("readonly","readonly");
		$("#bb", $("#"+this.id)).attr("readonly","readonly");
		$("#wxxm", $("#"+this.id)).attr("readonly","readonly");
		$("#rwdh", $("#"+this.id)).attr("readonly","readonly");
		$("#gzdh", $("#"+this.id)).attr("readonly","readonly");
		$("#kzh", $("#"+this.id)).attr("readonly","readonly");
		$("#cmph", $("#"+this.id)).attr("readonly","readonly");
		$("#gzbt", $("#"+this.id)).attr("readonly","readonly");
		$("#jhgsRs", $("#"+this.id)).attr("readonly","readonly");
		$("#jhgsXss", $("#"+this.id)).attr("readonly","readonly");
		$("#yjwj", $("#"+this.id)).attr("readonly","readonly");
		$("#tbsysm", $("#"+this.id)).attr("readonly","readonly");
		$("#gzgs", $("#"+this.id)).attr("readonly","readonly");
		$("#bz", $("#"+this.id)).attr("readonly","readonly");
		//设置select失效
		$("#jx", $("#"+this.id)).attr("disabled","true");
		$("#gzlx_e", $("#"+this.id)).attr("disabled","true");
		$("#gklx_e", $("#"+this.id)).attr("disabled","true");
		$("#zy_e", $("#"+this.id)).attr("disabled","true");
		$("#gzz_e", $("#"+this.id)).attr("disabled","true");
		//设置checkbox失效
		$("#isBj", $("#"+this.id)).attr("disabled","true");
		$("#worktype", $("#"+this.id)).attr("disabled","true");
		$("#workCardBox", $("#"+this.id)).attr("disabled","true");
		$("#workContentBox", $("#"+this.id)).attr("disabled","true");
	},
	/**
	 * 初始化引入数据
	 */
	initInclude : function(obj,colOptionhead){
		var this_ = this;
		//维修项目
		var wxxmbh = obj.wxxmbh;
		var wxxm = obj.wxxmbh;
		var rwms = '';
		var wxxmid = '';
		if(obj.maintenanceProject != null){
			wxxmbh = obj.maintenanceProject.rwh;
			wxxm = formatUndefine(obj.maintenanceProject.rwh) + " " +formatUndefine(obj.maintenanceProject.bb);
			rwms = obj.maintenanceProject.rwms;
			wxxmid = obj.maintenanceProject.id;
		}
		this_.loadMaintenanceRel(wxxmid);
		
		$("#wxxmbh", $("#"+this_.id)).val(wxxmbh);
		$("#wxxm", $("#"+this_.id)).val(wxxm);
		$("#rwms", $("#"+this_.id)).val(rwms);
		//流程信息
		introduce_process_info_class.show({  
			status:5,//1,编写,2审核,3批准，4审核驳回,5批准驳回
			prepared:StringUtil.escapeStr(obj.zdr?obj.zdr.displayName:''),//编写人
			preparedDate:StringUtil.escapeStr(obj.whsj),//编写日期
			checkedOpinion:StringUtil.escapeStr(obj.shyj),//审核意见
			checkedby:StringUtil.escapeStr(obj.shr?obj.shr.displayName:''),//审核人
			checkedDate:StringUtil.escapeStr(obj.shsj),//审核日期
			checkedResult:obj.shjl,//审核结论
			approvedOpinion:StringUtil.escapeStr(obj.pfyj),//批准意见
			approvedBy:StringUtil.escapeStr(obj.pfr?obj.pfr.displayName:''),//批准人
			approvedDate:StringUtil.escapeStr(obj.pfsj),//批准日期
			approvedResult : obj.pfjl,// 批准结论
		});
		
		var workCardAttachment = obj.workCardAttachment;
		if(workCardAttachment != null){
			 var t = "";
			 t += "<a title='"+(StringUtil.escapeStr(workCardAttachment.wbwjm)+"."+workCardAttachment.hzm)+"' href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+workCardAttachment.id+"')>"+(StringUtil.escapeStr(workCardAttachment.wbwjm)+"."+workCardAttachment.hzm)+"</a>";
			 $("#work_card_attachments", $("#"+this_.id)).html(t);
		}
		
		var workContentAttachment = obj.workContentAttachment;
		if(workContentAttachment != null){
			 var t = "";
			 t += "<a title='"+(StringUtil.escapeStr(workContentAttachment.wbwjm)+"."+workContentAttachment.hzm)+"' href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+workContentAttachment.id+"')>"+(StringUtil.escapeStr(workContentAttachment.wbwjm)+"."+workContentAttachment.hzm)+"</a>";
			 $("#work_content_attachments", $("#"+this_.id)).html(t);
		}
		$("#gznrfjid", $("#"+this_.id)).val(obj.gznrfjid);
		
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:obj.id,
			fileHead : colOptionhead,
			colOptionhead : colOptionhead,
			fileType:"card"
		});//显示附件
		
		evaluation_modal.init({
			parentId : this_.id,// 第一层模态框id
			isShowed : colOptionhead,// 是否显示选择评估单的操作列
			zlxl : 8,//工卡
			dprtcode : this_.param.dprtcode,// 组织机构
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
			changeCss : true,
			isShowAll : false,
			zlid : obj.id
		});
		//初始化参考文件
		ReferenceUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			changeCss:true,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化相关工卡
		WorkCardUtil.init({
			djid:obj.id,//关联单据id
			gkh:obj.gkh,//关联单据编号
			jx : $("#jx", $("#"+this_.id)).val(),// 机型
			parentWinId : this_.id,//父窗口id
			colOptionhead : colOptionhead,
			changeCss : true,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
		//初始化器材清单
		Equipment_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工具设备
		Tools_list_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,//父窗口id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,//true:编辑,false:查看
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工作内容
		WorkContentUtil.init({
			djid:obj.id,//关联单据id
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		//初始化工种工时
		work_hour_edit.init({
			djid:obj.id,//关联单据id
			parentWinId : this_.id,
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : colOptionhead,
			dprtcode:this_.param.dprtcode//默认登录人当前机构代码
		});
		
	},
	returnViewData : function(obj){
		var this_ = this;
		$("#gkh", $("#"+this_.id)).val(obj.gkh);
		$("#bb", $("#"+this_.id)).html(obj.bb);
		$("#rwdh", $("#"+this_.id)).val(obj.rwdh);
		$("#gzdh", $("#"+this_.id)).val(obj.gzdh);
		$("#kzh", $("#"+this_.id)).val(obj.kzh);
		$("#cmph", $("#"+this_.id)).val(obj.cmph);
		$("#gzbt", $("#"+this_.id)).val(obj.gzbt);
		$("#yjwj", $("#"+this_.id)).val(obj.yjwj);
		$("#tbsysm", $("#"+this_.id)).val(obj.tbsysm);
		$("#gzgs", $("#"+this_.id)).val(obj.gzgs);
		$("#bz", $("#"+this_.id)).val(obj.bz);
		$("#jhgsRs", $("#"+this_.id)).val(obj.jhgsRs);
		$("#jhgsXss", $("#"+this_.id)).val(obj.jhgsXss);
		$("#zt", $("#"+this_.id)).val(DicAndEnumUtil.getEnumName('workCardStatusEnum', obj.zt));
		
		if(obj.fBbid != null && obj.fBbid != ''){
			$("#bbViewHistoryDiv", $("#"+this.id)).show();
			this_.oldId = obj.paramsMap.oldId;
			$("#old_bb_a", $("#"+this.id)).html(obj.paramsMap.oldBb);
			this.initWebuiPopover();
		}else{
			$("#bb_view", $("#"+this.id)).val(obj.bb);
			$("#bbNoViewHistoryDiv", $("#"+this.id)).show();
		}
		
		if(obj.jx != null){
			$("#jx", $("#"+this_.id)).val(obj.jx);
		}
		
		if(obj.gzlx != null){
			$("#gzlx_e", $("#"+this_.id)).val(obj.gzlx);
		}
		
		if(obj.gklx != null){
			$("#gklx_e", $("#"+this_.id)).val(obj.gklx);
		}
		
		if(obj.zy != null){
			$("#zy_e", $("#"+this_.id)).val(obj.zy);
		}
		
		//工作组回显
		if(obj.workGroup != null){
			var gzzName = StringUtil.escapeStr(obj.workGroup.gzzdm) + " " + StringUtil.escapeStr(obj.workGroup.gzzmc);
			$("#gzz_e", $("#"+this_.id)).val(gzzName);
		}
		
		if(obj.gzzid == "-"){
			$("#gzz_e", $("#"+this_.id)).val("N/A");
		}
		
		if(obj.isBj == 1){
			$("#isBj", $("#"+this_.id)).attr("checked","true");
		}else{
			$("#isBj", $("#"+this_.id)).removeAttr("checked");
		}
		if(obj.worktype == 1){
			$("#worktype", $("#"+this_.id)).attr("checked","true");
		}else{
			$("#worktype", $("#"+this_.id)).removeAttr("checked");
		}
		//章节号回显
		if(obj.fixChapter != null){
			var wczjhName = StringUtil.escapeStr(obj.fixChapter.zjh) + " " + StringUtil.escapeStr(obj.fixChapter.ywms);
			$("#zjh_name_e", $("#"+this_.id)).val(wczjhName);
		}
		this_.loadCoverPlate(obj.coverPlateList);
		
		this_.loadApplicableUnit(obj.applicableUnitList);
		
		//设置总工时
		this_.sumTotal();
	},
	/**
	 * 适用单位回显
	 */
	loadApplicableUnit : function(list){
		var this_ = this;
		var sydwStr = '';
		if(list != null && list.length > 0){
			$.each(list,function(index,row){
				sydwStr += StringUtil.escapeStr(row.sydw) + ",";
			});
		}
		if(sydwStr != ''){
			sydwStr = sydwStr.substring(0,sydwStr.length - 1);
		}
		$("#esydwdiv", $("#"+this_.id)).html(sydwStr);
	},
	/**
	 * 区域/接近回显
	 */
	loadCoverPlate : function(coverPlateList){
		var this_ = this;
		var str = '';
		var stationStr = '';
		var qyStr = '';
		if(coverPlateList != null && coverPlateList.length > 0){
			$.each(coverPlateList,function(index,row){
				if(row.lx == 1){
					qyStr += StringUtil.escapeStr(row.gbh) + ",";
				}
				if(row.lx == 2){
					str += StringUtil.escapeStr(row.coverPlateInformation?row.coverPlateInformation.gbbh:'') + " " +StringUtil.escapeStr(row.coverPlateInformation?row.coverPlateInformation.gbmc:'') + ",";
				}
				if(row.lx == 3){
					stationStr += formatUndefine(row.gbh) + ",";
				}
			});
		}
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		if(qyStr != ''){
			qyStr = qyStr.substring(0,qyStr.length - 1);
		}
		if(stationStr != ''){
			stationStr = stationStr.substring(0,stationStr.length - 1);
		}
//		$('#eqydiv',$("#"+this_.id)).html(qyStr);
		$('#eqydiv',$("#"+this_.id)).val(qyStr);
		$("#jj_e", $("#"+this_.id)).html(str);
//		$("#fjzw_div_view", $("#"+this_.id)).html(stationStr);
		$("#fjzw_div_view", $("#"+this_.id)).val(stationStr);
	},
	
	buildMonitorItem:function(list){
		var this_ = this;
		var htmlContent = '';
		if(list!=null){
			$.each(list,function(i,row){
				 	if (i == 1) {
				 		htmlContent = htmlContent + "<div class='monitor_rc_td'>";
					}
				 	
				    var scz = (row.scz||"") + this_.convertUnit(row.jklbh, row.scz, row.dwScz);
				    var zqz = (row.zqz||"") + this_.convertUnit(row.jklbh, row.zqz, row.dwZqz);
				    
				 	/*var rz = "-" + row.ydzQ + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.ydzQdw) + "/+" 
				 				+ row.ydzH + MonitorUnitUtil.getMonitorUnit(row.jklbh, row.ydzHdw);*/
				 	var rz = "-" + row.ydzQ + "/+" + row.ydzH + this_.convertUnit(row.jklbh, row.ydzQ, row.ydzHdw);
				    htmlContent += "<div>";
				    htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='jkxm'>"+MonitorUnitUtil.getMonitorName(row.jklbh)+"</div>"; 
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='sj'>"+StringUtil.escapeStr(scz)+"</div>";
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='zq'>"+StringUtil.escapeStr(zqz)+"</div>";
					htmlContent += "<div class='pull-left text-center' style='min-height:10px;' name='rc'>"+StringUtil.escapeStr(rz)+"</div>";
					if (i != 0) {
						htmlContent += "<br>";

					}
					if (i != 0 && i == list.length - 1) {
						htmlContent += "</div>";
					}
			});
		}
		return htmlContent;
   },
	/**
	 * 加载维修项目相关信息
	 */
	loadMaintenanceRel : function(id){
		var this_ = this;
		if(null != id && '' != id){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/maintenanceproject/queryMonitorItemModel",
				type:"post",
				data:{id:id},
				dataType:"json",
				success:function(data){
					$("#maintenance_version_list", $("#"+this_.id)).empty();
					if(data != null){
						this_.loadMonitorItemModel(data);
						this_.loadMonitor(data);
					}
				}
			});
		}else{
			$(".wxxm_syx", $("#"+this_.id)).hide();
			$("#maintenance_version_list", $("#"+this_.id)).empty();
			$("#maintenance_version_list", $("#"+this_.id)).html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 加载维修项目相关信息
	 */
	loadMonitor:function(data){
		var this_ = this;
		var htmlContent = '';
		var wxxmlx = data.wxxmlx;
		if(wxxmlx == 2 || wxxmlx == 3){
			$("#maintenance_version_bj").show();
			if(data.projectMaterialList.length > 0){
				$.each(data.projectMaterialList,function(j,mon){
					htmlContent +="<tr>"
					htmlContent +="<td style='text-align:center;vertical-align:middle;' name='bj'>"+mon.bjh+"</td>"
					htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(mon.projectMonitors, "jklbh"))+"</td>"
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
			}
		}else{
			$("#maintenance_version_bj").hide();
			if(data.projectMonitors.length > 0){
				htmlContent +="<tr>"
				htmlContent +="<td colspan='4'>"+this_.buildMonitorItem(MonitorUnitUtil.sort(data.projectMonitors, "jklbh"))+"</td>"
				htmlContent +="</tr>"
			}else{
				htmlContent = "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
			}
		}
		$("#maintenance_version_list", $("#"+this_.id)).html(htmlContent);
	},
	/**
	 * 加载监控项目适用性
	 */
	loadMonitorItemModel : function(project){
		var this_ = this;
		var syx = '';
		if(project.syx == "-" && null != project.projectModelList && project.projectModelList.length > 0){
			$.each(project.projectModelList,function(j,m){
				syx += m.fjzch + ",";
			});
			if(syx != ''){
				syx = syx.substring(0, syx.length - 1);
			}
		}
		if(project.syx == "00000"){
			syx = "ALL";
		}
		if(!project.syx || project.syx == "null"){
			syx = "N/A";
		}
		$("#syx_div_view", $("#"+this_.id)).html(syx);
	},
	/**
	 * 监控类型单位转换
	 * @param jklbh
	 * @param value
	 * @param unit
	 */
	convertUnit : function(jklbh, value, unit){
		if(value != null && value != "" && value != undefined){
			return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
		}else{
			return "";
		}
	},
	view : function(){
		window.open(basePath+"/project2/workcard/view/" + this.oldId);
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	//计算总数
	sumTotal : function(){
		var total = 0;
		var jhgsRs = $("#jhgsRs",$("#"+this.id)).val();
		var jhgsXss = $("#jhgsXss",$("#"+this.id)).val();
		total = jhgsRs*jhgsXss;
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#bzgs",$("#"+this.id)).html(total);
	}
}
function customResizeHeight(bodyHeight, tableHeight){
	work_card_view.resizeVersionWidth();
}