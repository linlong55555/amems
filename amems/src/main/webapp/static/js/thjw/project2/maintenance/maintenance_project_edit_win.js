/**
 * 维修项目弹窗
 */
open_win_maintenance_project_modal = {
	id:'open_win_maintenance_project_modal',
	chinaHeadId:'chinaHead',
	englishHeadId:'englishHead',
	planeRegOption : '',
	zoneOption : '',
	materialCount : 0,
	projectCount : 0,
	param: {
		chinaHead:"新增",
		englishHead:"Add", //已经选择的
		win_type : 1,//1:维修项目,2:定检包
		ope_type : 1,//1:新增,2:修改,3:改版
		isEdit : true,
		jx : '',
		wxfabh : '',
		editData : {},
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this_.param, param);
		}
		this_.initInfo();
	    ModalUtil.showModal(this_.id);
		$("#"+this_.id).on('shown.bs.modal', function (e) {
			AlertUtil.hideModalFooterMessage();
			$('#maintenance_project_form').data('bootstrapValidator').resetForm(false); 
			this_.resizeVersionWidth();
		});
	},
	initInfo : function(){
		var this_ = this;
		this_.initHead();
		this_.loadBasicData(this_.param.editData);
		this_.ininWinType();
		this_.initPlaneData();
		this_.initSelect();
		this_.initHistoryList();
		this_.initValidation();
		if(this_.param.win_type == 1){
			this_.initDic(this_.param.dprtcode);
			this_.initZoneInformation(function(){
				this_.multiselect(this_.param.editData);
				this_.loadProjectData(this_.param.editData);
//				this_.initFixedPackage(function(){
//					
//				});
			});
		}else{
			this_.multiselect(this_.param.editData);
		}
		this_.initOperateType();
		if(this.param.isEdit){
			this.loadAttachmentEdit(this.param.editData.id, true, true);
		}else{
			this.loadAttachmentEdit(this.param.editData.id, false, false);
		}
	},
	initOperateType:function(){
		var this_ = this;
		if(this_.param.ope_type == 1){
			$("[name='lastBbSpan']").hide();
			$("#wordCard_div").hide();
			$("input[name='ewxxmlx']").removeAttr("disabled","disabled");
			$("#erwh").removeAttr("disabled","disabled");
		}else if(this_.param.ope_type == 2){
			$("[name='lastBbSpan']").hide();
			$("input[name='ewxxmlx']").attr("disabled","disabled");
			$("#erwh").attr("disabled","disabled");
			
//			var fBbid = this_.param.editData.fBbid;
//			// 当前版本新增的维修项目可以更改维修项目类型
//			if(fBbid){
//				$("input[name='ewxxmlx']").attr("disabled","disabled");
//			}else{
//				$("input[name='ewxxmlx']").removeAttr("disabled","disabled");
//			}
			
			$("input[name='ewxxmlx']").removeAttr("disabled","disabled");
		}else if(this_.param.ope_type == 3){
			$("[name='lastBbSpan']").show();
			$("#wordCard_div").show();
//			$("input[name='ewxxmlx']").attr("disabled","disabled");
			$("#erwh").attr("disabled","disabled");
			
			$("input[name='ewxxmlx']").removeAttr("disabled","disabled");
		}
		
		if((this.param.win_type == 1 && this_.param.ope_type == 2)
				|| (this.param.win_type == 1 && this_.param.ope_type == 3)){
			$("#ssdjb_div").show();
		}else{
			$("#ssdjb_div").hide();
		}
	},
	// 初始化下拉框
	initSelect:function(){
		var wxxmdlid = this.param.editData.wxxmdlid;
		open_win_maintenance_class.buildSelect("edlbh",this.param.jx,this.param.dprtcode,wxxmdlid);
	},
	// 计算监控项目版本的宽度
	resizeVersionWidth:function(){
		$("#component_monitor_list div[name='jkxm']").width($("#maintenance_version_jkxm_modal").outerWidth());
		$("#component_monitor_list div[name='sj']").width($("#maintenance_version_sj_modal").outerWidth());
		$("#component_monitor_list div[name='zq']").width($("#maintenance_version_zq_modal").outerWidth());
		$("#component_monitor_list div[name='rc']").width($("#maintenance_version_rc_modal").outerWidth()-37);
	},
	initHead : function(){
		$("#"+this.chinaHeadId,$("#"+this.id)).html(this.param.chinaHead);
		$("#"+this.englishHeadId,$("#"+this.id)).html(this.param.englishHead);
	},
	ininWinType : function(){
		if(this.param.win_type == 1){
			$(".djbRadio",$("#"+this.id)).hide();
			$(".wxxmRadio",$("#"+this.id)).show();
			$(".wxxmDiv",$("#"+this.id)).show();
			$("#monitor_frame_package").hide();
			$("#qdeo_div").show();
			this.switchMonitorType();
			
		}else{
			$(".djbRadio",$("#"+this.id)).show();
			$(".wxxmRadio",$("#"+this.id)).hide();
			$(".wxxmDiv",$("#"+this.id)).hide();
			$("#monitor_frame_package").show();
			$("#monitor_frame_plane").hide();
			$("#component_monitor_panel").hide();
			$("#qdeo_div").hide();
		}
		$("#associateCnHead").text(this.param.editData.associateCnHead);
		$("#associateEngHead").text(this.param.editData.associateEngHead);
		$("#associateColCnHead").text(this.param.editData.associateColCnHead);
		$("#associateColEngHead").text(this.param.editData.associateColEngHead);
	},
	initFixedPackage : function(obj){
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = this_.param.dprtcode;
		searchParam.jx = this_.param.jx;
		searchParam.wxfabh = this_.param.wxfabh;
		startWait();
		AjaxUtil.ajax({
			async: false,
			url: basePath+"/project2/maintenanceproject/queryFixedPackageByWxfn",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				$('#essdjb',$("#"+this_.id)).empty();
				if(data != null && data.length > 0){
					var str = '';
					str += "<option value='' ></option>";
					$.each(data,function(i,row){
						var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.rwh)+"</option>";
						str += tempOption;
					});
					$('#essdjb',$("#"+this_.id)).html(str);
				}
				if(typeof(obj)=="function"){
					obj(); 
				}
		    }
		}); 
	},
	// 初始化区域下拉框
	initZoneInformation : function(obj){
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = this_.param.dprtcode;
		searchParam.jx = this_.param.jx;
		searchParam.lx = 1;
		startWait();
		AjaxUtil.ajax({
			async: false,
			url: basePath+"/basic/zone/zoneList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var zoneOption = '';
				if(data != null && data.length > 0){
					$.each(data,function(i,row){
						var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.sz)+"</option>";
						zoneOption += tempOption;
					});
				}
				this_.zoneOption = zoneOption;
				if(typeof(obj)=="function"){
					obj(); 
				}
		    }
		}); 
	},
	initPlaneData : function(){
		var this_ = this;
		var data = [];
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/aircraftinfo/selectByFjjx",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify({
				fjjx : this.param.jx,
				dprtcode : this_.param.dprtcode
			}),
			dataType:"json",
			success:function(pd){
				if(pd != null){
					data = pd;
				}
			}
		});
		var planeRegOption = '';
		planeRegOption += "<option value='00000' >ALL</option>";
			if(data.length >0){
				$.each(data,function(i,planeData){
					var tempOption = "<option value='"+StringUtil.escapeStr(planeData.fjzch)+"' >"+StringUtil.escapeStr(planeData.fjzch)+"</option>";
					planeRegOption += tempOption;
				});
		  	}
			planeRegOption += "<option value='null' >N/A</option>";
		this_.planeRegOption = planeRegOption;
	},
	initDic : function(dprtcode){
		$("#egzlx", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('17','egzlx',dprtcode);
		$("#exmlx", $("#"+this.id)).empty();
		DicAndEnumUtil.registerDic('18','exmlx',dprtcode);
	},
	multiselect : function(obj){
		var this_ = this;
		//生成多选下拉框区域
		/*$('#eqydiv',$("#"+this.id)).empty();
		$('#eqydiv',$("#"+this.id)).html("<select class='form-control' multiple='multiple' id='eqy'></select>");
		$("#eqy").html(this.zoneOption);
		$('#eqy',$("#"+this.id)).multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    buttonContainer: '<div class="btn-group" style="width:100%;" />',
		    numberDisplayed:99,
		    nonSelectedText:'请选择 Choose',
		    allSelectedText:'显示全部 All',
		    includeSelectAllOption: true,
		    selectAllText: '选择全部 Select All',
		    onChange:function(element,checked){
	  	    }
		});*/
		
		//生成多选下拉框(飞机注册号)
		$('#esyxdiv_plane',$("#"+this.id)).empty();
		$('#esyxdiv_plane',$("#"+this.id)).html("<select class='form-control' multiple='multiple' id='esyx_plane'></select>");
		$("#esyx_plane",$("#"+this.id)).append(this.planeRegOption);
		$('#esyx_plane',$("#"+this.id)).multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    buttonContainer: '<div class="btn-group" style="min-width:26.4%;" />',
		    numberDisplayed:99,
		    includeSelectAllOption: false,
		    onChange:function(element,checked){
		    	if(element.val() == "00000"){
		    	    $('option', $('#esyx_plane')).each(function(element) {
		    	    	$('#esyx_plane').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#esyx_plane').multiselect('select', '00000');
		    	}else if(element.val() == "null"){
		    	    $('option', $('#esyx_plane')).each(function(element) {
	    	    		$('#esyx_plane').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#esyx_plane').multiselect('select', 'null');
		    	}else{
		    		if(checked){
		    			 $('#esyx_plane').multiselect('deselect', 'null');
		    			 $('#esyx_plane').multiselect('deselect', '00000');
		    		}
		    	}
	  	    }
	    });
		$('#esyx_plane').multiselect('select', '00000');
		
		//生成多选下拉框(飞机注册号)
		$('#esyxdiv_component',$("#"+this.id)).empty();
		$('#esyxdiv_component',$("#"+this.id)).html("<select class='form-control' multiple='multiple' id='esyx_component' ></select>");
		$("#esyx_component",$("#"+this.id)).append(this.planeRegOption);
		$('#esyx_component',$("#"+this.id)).multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    buttonContainer: '<div class="btn-group" style="min-width:26.4%;" />',
		    numberDisplayed:99,
		    includeSelectAllOption: false,
		    onChange:function(element,checked){
		    	if(element.val() == "00000"){
		    	    $('option', $('#esyx_component')).each(function(element) {
		    	    	$('#esyx_component').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#esyx_component').multiselect('select', '00000');
		    	}else if(element.val() == "null"){
		    	    $('option', $('#esyx_component')).each(function(element) {
	    	    		$('#esyx_component').multiselect('deselect', $(this).val());
		    	     });
		    	    $('#esyx_component').multiselect('select', 'null');
		    	}else{
		    		if(checked){
		    			 $('#esyx_component').multiselect('deselect', 'null');
		    			 $('#esyx_component').multiselect('deselect', '00000');
		    		}
		    	}
	  	    }
	    });
		$('#esyx_component').multiselect('select', '00000');
		
		if(obj.id){
			var projectModelList = obj.projectModelList;
			var fjzchList = [];
			if(obj.syx == "00000"){
				fjzchList.push("00000");
			}else if(!obj.syx){
				fjzchList.push("null");
			}else if(projectModelList && projectModelList.length > 0){
				$.each(projectModelList, function(i, row){
					fjzchList.push(row.fjzch);
				});
			}
			if(obj.wxxmlx == 1){
				$('#esyx_plane').multiselect('deselect', '00000');
				$('#esyx_plane',$("#"+this_.id)).multiselect('select', fjzchList);
			}else if(obj.wxxmlx == 2 || obj.wxxmlx == 3){
				$('#esyx_component').multiselect('deselect', '00000');
				$('#esyx_component',$("#"+this_.id)).multiselect('select', fjzchList);
			}
		}
	},
	getInitData : function(dprtcode){
		this.initDic(dprtcode);
		var r = {};
		r.gzlx = $.trim($("#egzlx", $("#"+this.id)).val());
		r.xmlx = $.trim($("#exmlx", $("#"+this.id)).val());
		return r;
	},
	loadBasicData : function(obj){
		var this_ = this;
		$("#eid",$("#"+this.id)).val(obj.id);
		$("#ebb",$("#"+this.id)).val(obj.wxfaBb);
		$("#ecmph",$("#"+this.id)).val(obj.cmph);
		$("#ezjh",$("#"+this.id)).val(obj.zjh);
		var wczjhName = obj.fixChapter ? (formatUndefine(obj.fixChapter.zjh) + " " +formatUndefine(obj.fixChapter.ywms)) : "";
		$("#ezjhName",$("#"+this.id)).val(wczjhName);
		$("#erwh",$("#"+this.id)).val(obj.rwh);
		$("#eckh",$("#"+this.id)).val(obj.ckh);
		$("#eckwj",$("#"+this.id)).val(obj.ckwj);
		$("#erwms",$("#"+this.id)).val(obj.rwms);
		this_.calRwmsCount();
		$("#ebz",$("#"+this.id)).val(obj.bz);
		$("#bbSpan",$("#"+this.id)).html("R"+obj.wxfaBb);
		$("#egkbh",$("#"+this.id)).val(obj.gkbh);
		$("input:radio[name='ewxxmlx'][value='"+obj.wxxmlx+"']",$("#"+this.id)).attr("checked",true); 
		$("input:radio[name='ezt'][value='"+obj.yxbs+"']",$("#"+this.id)).attr("checked",true); 
		this.loadRelationData(obj.projectList);
		$("#monitor_frame_component_jsgs", $("#"+this.id)).empty();
		DicAndEnumUtil.registerEnum("computationalFormulaEnum", 'monitor_frame_component_jsgs');
		if(this_.param.win_type == 1){
			var monitorUtil = new MonitorUtil({
				id:'monitor_frame_plane',
				title_cn : "适用性及监控项设置",
				title_eng : "Applicability And Monitor Setting",
				isEdit : this_.param.isEdit,
				showPlane : true,
				validateCount : false,
				planes : this_.planeRegOption,
				obj : obj.projectMonitors
			});
			monitorUtil.show();
			this.monitorObj = monitorUtil;
			$("#monitor_frame_plane input[name='isHdwz']").removeAttr("checked");
			$("#component_monitor_panel input[name='isHdwz']").removeAttr("checked");
			$("#monitor_frame_plane_jgms,#monitor_frame_component_jgms").val("");
			if(obj.wxxmlx == 1){
				$("#monitor_frame_plane_jsgs",$("#"+this.id)).val(obj.jsgs||1);
				$("#monitor_frame_plane_wz",$("#"+this.id)).val(obj.wz||21);
				if(obj.isHdwz == 1){
					$("#monitor_frame_plane input[name='isHdwz']").attr("checked",'true');//选中
				}
				$("#monitor_frame_plane_jgms",$("#"+this.id)).val(obj.jgms||"");
			}else if(obj.wxxmlx == 2 || obj.wxxmlx == 3){
				$("#monitor_frame_component_jsgs",$("#"+this.id)).val(obj.jsgs||1);
				if(obj.isHdwz == 1){
					$("#component_monitor_panel input[name='isHdwz']").attr("checked",'true');//选中
				}
				$("#monitor_frame_component_jgms",$("#"+this.id)).val(obj.jgms||"");
			}
		}
		if(this_.param.win_type == 2){
			var monitorUtil = new MonitorUtil({
				id:'monitor_frame_package',
				isEdit : this_.param.isEdit,
				validateCount : true,
				obj : obj.projectMonitors
			});
			monitorUtil.show();
			this.monitorObj = monitorUtil;
			$("#monitor_frame_package_jsgs",$("#"+this.id)).val(obj.jsgs||1);
			if(!obj.wz){
				$("#monitor_frame_package_wz",$("#"+this.id)).val(21);
			}else{
				$("#monitor_frame_package_wz",$("#"+this.id)).val(obj.wz||21);
			}
			$("#monitor_frame_package input[name='isHdwz']").removeAttr("checked");
			if(obj.isHdwz == 1){
				$("#monitor_frame_package input[name='isHdwz']").attr("checked",'true');//选中
			}
		}
	},
	loadProjectData : function(obj){
		var this_ = this;
		$("#efjzw",$("#"+this_.id)).val(obj.fjzw);
		$("#egzlx",$("#"+this_.id)).val(obj.gzlx);
		$("#exmlx",$("#"+this_.id)).val(obj.xmlx);
		
		$("#ejhgsRs",$("#"+this_.id)).val(obj.jhgsRs);
		$("#ejhgsXss",$("#"+this_.id)).val(obj.jhgsXss);
		this.sumTotal();
		
		$("#egkid",$("#"+this_.id)).val(obj.gkid);
		$("#egkname",$("#"+this_.id)).val(obj.gkname);
		
		if(obj.eo){
			$("#eeoid",$("#"+this_.id)).val(obj.eo.id);
			$("#eeobh",$("#"+this_.id)).val(obj.eo.eobh);
			$("#eo_title_div").show();
			$("#eo_title",$("#"+this_.id)).html("<a href='javascript:void(0);' onclick='open_win_maintenance_project_modal.viewEO(\""+obj.eo.id+"\")'>"+obj.eo.eozt+"</a>");
		}else{
			$("#eeoid",$("#"+this_.id)).val('');
			$("#eeobh",$("#"+this_.id)).val('');
			$("#eo_title_div").hide();
			$("#eo_title",$("#"+this_.id)).html('');
		}
		
		// 回显接近（盖板）/区域/站位
		$("#jj_e").text("");
		$("#efjzw").val("");
		$('#eqy').val("");
		this_.accessList = [];
		this_.accessIdList = [];
		this_.positionList = [];
		this_.positionIdList = [];
		if(obj.coverPlateList != null && obj.coverPlateList.length > 0){
			
			var zoneList = [];
			var accessList = [];
			var accessIdList = [];
			var positionList = [];
			var positionIdList = [];
			var zoneStr = "";
			var accessStr = "";
			var stationStr = "";
			
			$.each(obj.coverPlateList, function(i, cover){
				if(cover.lx == 1){
					zoneList.push(cover.gbid);
					zoneStr += StringUtil.escapeStr(cover.gbh) + ",";
				}
				if(cover.lx == 2){
					accessIdList.push(cover.gbid);
					if(cover.coverPlateInformation){
						accessList.push({
							id : cover.gbid,
							gbmc : cover.coverPlateInformation.gbmc,
							gbbh : cover.coverPlateInformation.gbbh
						});
						accessStr += formatUndefine(cover.coverPlateInformation.gbbh) + " " +formatUndefine(cover.coverPlateInformation.gbmc) + ",";
					}
				}
				if(cover.lx == 3){
					positionList.push({
						id : cover.gbid,
						sz : cover.gbh
					});
					positionIdList.push(cover.gbid);
					stationStr += formatUndefine(cover.gbh) + ",";
				}
			});
			// 回显区域
			//$('#eqy',$("#"+this_.id)).multiselect('select', zoneList);
			if(zoneStr != ''){
				zoneStr = zoneStr.substring(0, zoneStr.length - 1);
			}
			$('#eqy',$("#"+this_.id)).val(zoneStr);
			
			// 回显接近
			this_.accessList = accessList;
			this_.accessIdList = accessIdList;
			if(accessStr != ''){
				accessStr = accessStr.substring(0,accessStr.length - 1);
			}
			$("#jj_e", $("#"+this_.id)).html(accessStr);
			// 回显飞机站位
			this_.positionList = positionList;
			this_.positionIdList = positionIdList;
			if(stationStr != ''){
				stationStr = stationStr.substring(0,stationStr.length - 1);
			}
			$("#efjzw", $("#"+this_.id)).val(stationStr);
		}
		
		if(obj.syx != null){
			$('#esyx',$("#"+this_.id)).multiselect('select', obj.syx);
		}
		
		$("#eisBj",$("#"+this_.id)).removeAttr("checked");
		if(obj.isBj == 1){
			$("#eisBj",$("#"+this_.id)).attr("checked",'true');//选中
		}
		$("#eali",$("#"+this_.id)).removeAttr("checked");
		if(obj.ali == 1){
			$("#eali",$("#"+this_.id)).attr("checked",'true');//选中
		}
		this.loadMaterialData(obj.projectMaterialList);
	},
	loadMaterialData : function(data){
		var this_ = this;
		$("#component_monitor_list",$("#"+this_.id)).empty();
		this_.materialCount = 0;
		if(data != null && data.length > 0){
			$.each(data,function(index,row){
				row.rowid = Math.uuid().toLowerCase();
				this_.materialCount++;
				this_.addMonitorRow(row);
			});
		} else {
			this_.setMonitorNoData();
		}
		this_.projectMaterialList = data;
	},
	loadRelationData : function(data){
		var this_ = this;
		$("#maintenance_project_list",$("#"+this_.id)).empty();
		$("#ssdjb_div input").val("");
		this_.projectCount = 0;
		if(data != null && data.length > 0){
			$.each(data,function(index,row){
				if(row.wxxmlx == 4){
					$("#ssdjb_div input").val(row.rwh + " R" + row.bb);
				}else{
					this_.projectCount++;
					this_.addProjectRow(row);
				}
			});
		}  
		if(this_.projectCount == 0){
			this_.setProjectNoData();
		}
	},
	loadAttachmentEdit : function(id,colOptionhead,fileHead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			attachHead : true,
			djid:formatUndefine(id),
			fileType:"reference",
			chinaHead:"参考文件附件",
			englishHead:"Reference Attachment",
	 		colOptionhead : colOptionhead,
			fileHead : fileHead,
		});//显示附件
	},
	openChapterWin : function(){//打开章节号弹窗
		var this_ = this;
		var zjh = $.trim($("#ezjh",$("#"+this_.id)).val());
		var dprtcode = this_.param.dprtcode;
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:dprtcode, //机构代码
			parentWinId:this_.id,
			callback: function(data){//回调函数
				if(data != null && data.zjh != null){
					var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
					$("#ezjh",$("#"+this_.id)).val(data.zjh);
					$("#ezjhName",$("#"+this_.id)).val(wczjhName);
				}else{
					$("#ezjh",$("#"+this_.id)).val('');
					$("#ezjhName",$("#"+this_.id)).val('');
				}
				$('#maintenance_project_form').data('bootstrapValidator')  
				     .updateStatus('ezjh', 'NOT_VALIDATED',null)  
				     .validateField('ezjh');  
			}
		});
	},
	// 打开工卡弹窗
	openCardWin : function(){
		var this_ = this;
		var gkid = $.trim($("#egkid",$("#"+this_.id)).val());
		var dprtcode = this_.param.dprtcode;
		open_win_work_card.show({
			selected:gkid,//原值，在弹窗中默认勾选
			jx:this_.param.jx,
			dprtcode:dprtcode, //机构代码
			parentWinId:this_.id,
			callback: function(data){//回调函数
				if(data != null && data.id != null){
					var gkname = StringUtil.escapeStr(data.gdbh) + " R" +StringUtil.escapeStr(data.bb) + " " +StringUtil.escapeStr(data.gzzt);
					$("#egkid",$("#"+this_.id)).val(data.id);
					$("#egkbh",$("#"+this_.id)).val(data.gdbh);
					$("#egkname",$("#"+this_.id)).val(gkname);
				}else{
					$("#egkid",$("#"+this_.id)).val('');
					$("#egkbh",$("#"+this_.id)).val('');
					$("#egkname",$("#"+this_.id)).val('');
				}
			}
		});
	},
	// 打开EO弹窗
	openEOWin : function(){
		var this_ = this;
		var eoid = $("#eeoid",$("#"+this_.id)).val();
		var dprtcode = this_.param.dprtcode;
		open_win_eo.show({
			id:eoid,
			selected:eoid,//原值，在弹窗中默认勾选
			jx:this_.param.jx,
			wxxmid:this_.param.editData.id,
			dprtcode:dprtcode, //机构代码
			parentWinId:this_.id,
			callback: function(data){//回调函数
				if(data != null && data.id != null){
					$("#eeoid",$("#"+this_.id)).val(data.id);
					$("#eeobh",$("#"+this_.id)).val(data.eobh);
					$("#eo_title_div").show();
					$("#eo_title",$("#"+this_.id)).html("<a href='javascript:void(0);' onclick='open_win_maintenance_project_modal.viewEO(\""+data.id+"\")'>"+data.eozt+"</a>");
				}else{
					$("#eeoid",$("#"+this_.id)).val('');
					$("#eeobh",$("#"+this_.id)).val('');
					$("#eo_title_div").hide();
					$("#eo_title",$("#"+this_.id)).html('');
				}
			}
		});
	},
	openStationWin : function(){//打开飞机站位弹窗
		var this_ = this;
		PositionModal.show({
			selected:[],//原值，在弹窗中默认勾选
			dprtcode:this_.param.dprtcode,
			modalHeadCN : '飞机站位列表',
			modalHeadENG : 'A/C Type Position List',
			parentid:this_.id,
			jx:this_.param.jx,
			lx:3,
			callback: function(data){//回调函数
				var str = '';
				this_.positionIdList = [];
				this_.positionList = [];
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						this_.positionIdList.push(row.id);
						this_.positionList.push(row);
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#efjzw", $("#"+this_.id)).val(str);
			}
		});
	},
	// 打开区域弹窗
	openZoneWin : function(){
		var this_ = this;
		PositionModal.show({
			selected:[],
			dprtcode:this_.param.dprtcode,
			modalHeadCN : '区域列表',
			modalHeadENG : 'Zone List',
			parentid:this_.id,
			jx:this_.param.jx,
			lx:1,
			callback: function(data){//回调函数
				var str = '';
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						str += formatUndefine(row.sz) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#eqy", $("#"+this_.id)).val(str);
			}
		});
	},
	openAccessWin : function(){
		var this_ = this;
		var jx = this_.param.jx;
		if(jx == null && jx == ''){
			AlertUtil.showErrorMessage("请选择机型!");
			return false;
		}
		open_win_access.show({
			multi : true,
			parentWinId : this_.id,
			jx:jx,
			accessIdList : this_.accessIdList,//回显
			dprtcode:this_.param.dprtcode,
			callback: function(data){//回调函数
				var str = '';
				this_.accessIdList = [];
				this_.accessList = [];
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						this_.accessIdList.push(row.id);
						this_.accessList.push(row);
						str += formatUndefine(row.gbbh) + " " +formatUndefine(row.gbmc) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#jj_e", $("#"+this_.id)).html(str);
			}
		},true)
	},
	
	openProjectWinAdd : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var existsIdList = [];
		$("#maintenance_project_list",$("#"+this_.id)).find("tr").each(function(){
			var index=$(this).index(); //当前行
			var rwh =$.trim($("#maintenance_project_list input[name='rwh']").eq(index).val());
			if(rwh){
				existsIdList.push(rwh);
			}
		});
		if(this_.param.editData.rwh){
			existsIdList.push(this_.param.editData.rwh);
		}
		open_win_maintenance_project.show({
			multi : true,
			loaded : false,
			id : this_.param.editData.id,
			jx : this_.param.jx,
			wxfabh : this_.param.wxfabh,
			wxxmlx : $("input:radio[name='ewxxmlx']:checked",$("#"+this_.id)).val(),
			dprtcode:dprtcode,
			parentWinId:this_.id,
			existsIdList : existsIdList,//过滤已经选择的
			callback: function(data){//回调函数
				if(data != null && data.length > 0){
					//先移除暂无数据一行
					var len = $("#maintenance_project_list",$("#"+this_.id)).length;
					if (len == 1) {
						if($("#maintenance_project_list",$("#"+this_.id)).find("td").length == 1){
							$("#maintenance_project_list",$("#"+this_.id)).empty();
						}
					}
					$.each(data,function(index,row){
						this_.projectCount++;
						this_.addProjectRow(row);
					});
				}
			}
		},true);
		
	},
	addProjectRow : function(obj){
		var this_ = this;
		var syxstr = open_win_maintenance_project.buildFjzch(obj);
		var tr = "";
		tr += "<tr>";
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<button class='line6 line6-btn' onclick='"+this_.id+".removeProjectCol(this)'>";
	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	    tr += "</button>";
		tr += "</td>";
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.projectCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='rwh' value='"+StringUtil.escapeStr(obj.rwh)+"'/>";
		tr += "</td>";
		tr += "<td title='R"+StringUtil.escapeStr(obj.bb)+"' style='text-align:center;vertical-align:middle;'>R"+StringUtil.escapeStr(obj.bb)+"</td>";  
		tr += "<td title='"+StringUtil.escapeStr(obj.rwh)+"' style='text-align:left;vertical-align:middle;'><a herf='javascript:void(0);' onclick='open_win_maintenance_project_modal.viewProject(\""+obj.id+"\")' class='cursor-pointer'>"+StringUtil.escapeStr(obj.rwh)+"</a></td>";  
		tr += "<td title='"+StringUtil.escapeStr(obj.ckh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ckh)+"</td>";  
		tr += "<td title='"+StringUtil.escapeStr(syxstr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(syxstr)+"</td>";  
		tr += "<td title='"+StringUtil.escapeStr(obj.ckwj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ckwj)+"</td>";  
		if(obj.jhgsRs!=null && obj.jhgsRs!="" && obj.jhgsXss!=null && obj.jhgsXss!=""){
			var total = obj.jhgsRs * obj.jhgsXss;
			if(String(total).indexOf(".") >= 0){
				total = total.toFixed(2);
			}
			var jhvalue=obj.jhgsRs+"人x"+obj.jhgsXss+"时="+total+"时";
			tr += "<td style='text-align:left;vertical-align:middle;' title='"+jhvalue+"'>"+jhvalue+"</td>";  
		}else{
			tr += "<td style='text-align:left;vertical-align:middle;'></td>"; 
		}
		tr += "</tr>";
	
		$("#maintenance_project_list",$("#"+this_.id)).append(tr);
	},
	removeProjectCol : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resProjectxh();
				this_.projectCount--;
			}});
			
		} else {
			$(obj).parent().parent().remove();
			this_.resProjectxh();
			this_.projectCount--;
		}
	},
	resProjectxh : function(){
		var this_ = this;
		var len = $("#maintenance_project_list",$("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#maintenance_project_list",$("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setProjectNoData();
		}
	},
	setProjectNoData : function(){
		if(this.param.isEdit){
			$("#maintenance_project_list",$("#"+this.id)).append("<tr style='height:35px;'><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#maintenance_project_list",$("#"+this.id)).append("<tr style='height:35px;'><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	getProjectParam : function(){
		var this_ = this;
		var param = [];
		var len = $("#maintenance_project_list",$("#"+this_.id)).find("tr").length;
		if (len == 1) {
			if($("#maintenance_project_list",$("#"+this_.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			$("#maintenance_project_list",$("#"+this_.id)).find("tr").each(function(){
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid =$("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
				var rwh =$("input[name='rwh']").eq(index).val();
				infos.id = hiddenid;
				infos.jx  = this_.param.jx;
				infos.wxxmbh = rwh;
				param.push(infos);
			});
		}
		return param;
	},
	sumTotal : function(){
		var total = 0;
		var jhgsRs = $("#ejhgsRs",$("#"+this.id)).val()||"0";
		var jhgsXss = $("#ejhgsXss",$("#"+this.id)).val()||"0";
		total = parseFloat(jhgsRs) * parseFloat(jhgsXss);
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#ebzgs",$("#"+this.id)).text(total);
	},
	clearNoNumber : function(obj){
		 //先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	    this.sumTotal();
	},
	clearNoNumTwo : function(obj){
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
	    this.sumTotal();
	},
	
	// 初始化验证
	initValidation : function(){
		var this_ = this;
		$('#maintenance_project_form').bootstrapValidator({
	        message: '数据不合法',
	        excluded: [':disabled'],
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	ezjh: {
	                validators: {
	                    notEmpty: {
	                        message: 'ATA章节号不能为空'
	                    }
	                }
	            },
	            edlbh: {
	                validators: {
	                	notEmpty: {
	                        message: '维修项目大类不能为空'
	                    }
	                }
	            },
	            erwh: {
	                validators: {
	                	notEmpty: {
	                        message: '任务号不能为空'
	                    },
	                    regexp: {
	                    	regexp: /^[\x00-\xff]*$/,
	                        message: '不能输入中文'
	                    }
	                }
	            },
	            erwms: {
	                validators: {
	                	notEmpty: {
	                        message: '任务描述不能为空'
	                    }
	                }
	            },
	            ecmph: {
	                validators: {
	                	regexp: {
	                    	regexp: /^[\x00-\xff]*$/,
	                        message: '不能输入中文'
	                    }
	                }
	            },
	        }
	    });
	},
	validateData : function(){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		$('#maintenance_project_form').data('bootstrapValidator').validate();
		if(!$('#maintenance_project_form').data('bootstrapValidator').isValid()){
			AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
			return false;
		}
		var zjh = $.trim($("#ezjh",$("#"+this.id)).val());
		var wxxmdlid = $.trim($("#edlbh",$("#"+this.id)).val());
		var rwh = $.trim($("#erwh",$("#"+this.id)).val());
		var rwms = $.trim($("#erwms",$("#"+this.id)).val());
		var wxxmlx = $("input:radio[name='ewxxmlx']:checked",$("#"+this.id)).val();
		
		var rs = $.trim($("#ejhgsRs",$("#"+this.id)).val());
		var xss = $.trim($("#ejhgsXss",$("#"+this.id)).val());
		var eqy = $.trim($("#eqy",$("#"+this_.id)).val());
		var efjzw = $.trim($("#efjzw",$("#"+this_.id)).val());
		
		if(rs && !/^[0-9]+$/.test(rs)){
			AlertUtil.showModalFooterMessage("计划工时的人数应为正整数！");
			return false;
		}
		if(xss && !/^[0-9]+((\.)[0-9]?[0-9]?)?$/.test(xss)){
			AlertUtil.showModalFooterMessage("计划工时的小时数数应为正数，最多两位小数！");
			return false;
		}
		
		var rwmsCount = this_.calRwmsCount();
		if(rwmsCount > 4000){
			AlertUtil.showModalFooterMessage("任务描述的最大长度为4000！");
			return false;
		}
		
		if(eqy != null && eqy != ''){
			if(!new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,4000}$").test(eqy)){  
				$("#eqy",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("区域只能输入长度不超过4000个字符的英文、数字、英文特殊字符!");
				return false;
			}
		}
		
		if(efjzw != null && efjzw != ''){
			if(!new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,4000}$").test(efjzw)){  
				$("#efjzw",$("#"+this.id)).focus();
				AlertUtil.showModalFooterMessage("飞机站位只能输入长度不超过4000个字符的英文、数字、英文特殊字符!");
				return false;
			}
		}
		
		if(wxxmlx == 2 || wxxmlx == 3){
			
			var len = $("#component_monitor_list td",$("#"+this_.id)).length;
			if (len <= 1) {
				AlertUtil.showModalFooterMessage("请设置部件监控项!");
				return false;
			}
			
		}else {
			var msg = this_.monitorObj.validateMonitorItem();
			if (msg){
				AlertUtil.showModalFooterMessage(msg);
				return false;
			}
		}
		
		return true;
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	save: function(){
		var this_ = this;
		
		startWait($("#"+this_.id));
		if(!this_.validateData()){
			finishWait($("#"+this_.id));
			return false;
		}
		var data = {};
		data.scheme = {
			id : this_.param.editData.wxfaid
		};
		data.id = this_.param.editData.id;
		data.wxxmlx = $("input:radio[name='ewxxmlx']:checked",$("#"+this_.id)).val();
		data.wxfabh = this_.param.wxfabh;
		data.jx = this_.param.jx;
		data.dprtcode = this_.param.dprtcode;
		data.bb = $.trim($("#ebb",$("#"+this_.id)).val());
		data.zjh = $.trim($("#ezjh",$("#"+this_.id)).val());
		data.wxxmdlid = $.trim($("#edlbh",$("#"+this_.id)).val());
		data.cmph = $.trim($("#ecmph",$("#"+this_.id)).val());
		data.yxbs = $("input:radio[name='ezt']:checked",$("#"+this_.id)).val();
		data.rwh = $.trim($("#erwh",$("#"+this_.id)).val());
		data.ckh = $.trim($("#eckh",$("#"+this_.id)).val());
		data.ckwj = $.trim($("#eckwj",$("#"+this_.id)).val());
		data.rwms = $.trim($("#erwms",$("#"+this_.id)).val());
		data.bz = $.trim($("#ebz",$("#"+this_.id)).val());
		
		if(data.wxxmlx != 4){
			data.gzlx = $.trim($("#egzlx",$("#"+this_.id)).val());
			data.xmlx = $.trim($("#exmlx",$("#"+this_.id)).val());
			data.jhgsRs = $.trim($("#ejhgsRs",$("#"+this_.id)).val());
			data.jhgsXss = $.trim($("#ejhgsXss",$("#"+this_.id)).val());
			data.eobh = $.trim($("#eeobh",$("#"+this_.id)).val());
			data.isBj = $("#eisBj",$("#"+this_.id)).is(":checked")?1:0;
			data.ali = $("#eali",$("#"+this_.id)).is(":checked")?1:0;
			
			//设置区域
			var coverPlateList = [];
			var zone = {};
			zone.gbid = '';
			zone.gbh = $.trim($("#eqy",$("#"+this_.id)).val());
			zone.xc = 1;
			zone.lx = 1;
			coverPlateList.push(zone);
			/*var qyStr = $.trim($("#eqy",$("#"+this_.id)).val());
			var coverPlateList = [];
			if(qyStr != null && qyStr.length > 0){
				var qyArr = qyStr.split(",");
				for(var i = 0 ; i < qyArr.length ; i++){
					if('multiselect-all' != qyArr[i]){
						var coverPlate = {};
						coverPlate.gbid = qyArr[i];
						coverPlate.gbh = $("#eqy",$("#"+this_.id)).find("option[value='"+qyArr[i]+"']").text();
						coverPlate.xc = i;
						coverPlate.lx = 1;
						coverPlateList.push(coverPlate);
					}
				}
			}*/
			
			//设置接近
			$(this_.accessList).each(function(i) {
				var coverPlate = {};
				coverPlate.gbid = this.id;
				coverPlate.gbh = this.gbbh;
				coverPlate.xc = i + 1;
				coverPlate.lx = 2;
				coverPlateList.push(coverPlate);
			});
			
			//设置飞机站位
			var position = {};
			position.gbid = '';
			position.gbh = $.trim($("#efjzw",$("#"+this_.id)).val());
			position.xc = 1;
			position.lx = 3;
			coverPlateList.push(position);
			/*$(this_.positionList).each(function(i) {
				var coverPlate = {};
				coverPlate.gbid = this.id;;
				coverPlate.gbh = this.sz;
				coverPlate.xc = i + 1;
				coverPlate.lx = 3;
				coverPlateList.push(coverPlate);
			});*/
			data.coverPlateList = coverPlateList;
			var fjzchStr;
			if(data.wxxmlx == 1){
				fjzchStr = $.trim($("#esyx_plane",$("#"+this_.id)).val());
			}else if(data.wxxmlx == 2 || data.wxxmlx == 3){
				fjzchStr = $.trim($("#esyx_component",$("#"+this_.id)).val());
			}
			if(fjzchStr != null && fjzchStr.length > 0){
				var syx = '-';
				var projectModelList = [];
				var fjzchArr = fjzchStr.split(",");
				for(var i = 0 ; i < fjzchArr.length ; i++){
					if("00000" == fjzchArr[i]){
						syx = '00000';
						break;
					}
					if("null" == fjzchArr[i]){
						syx = '';
						break;
					}
					if('multiselect-all' != fjzchArr[i]){
						var projectModel = {};
						projectModel.fjzch = fjzchArr[i];
						projectModel.xc = i + 1;
						projectModelList.push(projectModel);
					}
				}
				if(syx == '-' && projectModelList.length > 0){
					data.projectModelList = projectModelList;
				}
				data.syx = syx;
			}
		}else{
			data.syx = "00000";
		}
		data.projectRelationshipList = this_.getProjectParam();
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		data.attachments = attachmentsObj.getAttachments();
		
		if(data.wxxmlx == 1 || data.wxxmlx == 4){
			data.projectMonitors = this_.monitorObj.getMonitorSettingParam();
		}
		
		if(data.wxxmlx == 2 || data.wxxmlx == 3){
			var projectMaterialList = this_.projectMaterialList;
			$.each(projectMaterialList,function(i){
				this.xc = i + 1;
			});
			data.projectMaterialList = projectMaterialList;
		}
		
		if(data.wxxmlx == 1){
			data.jsgs = $.trim($("#monitor_frame_plane_jsgs",$("#"+this_.id)).val());
			data.wz = $.trim($("#monitor_frame_plane_wz:visible",$("#"+this_.id)).val());
			data.isHdwz = $("#monitor_frame_plane input[name='isHdwz']").is(":checked")?1:0;
			data.jgms = $.trim($("#monitor_frame_plane_jgms",$("#"+this_.id)).val());
			
		}
		if(data.wxxmlx == 2 || data.wxxmlx == 3){
			data.jsgs = $.trim($("#monitor_frame_component_jsgs",$("#"+this_.id)).val());
			data.isHdwz = $("#component_monitor_panel input[name='isHdwz']").is(":checked")?1:0;
			data.jgms = $.trim($("#monitor_frame_component_jgms",$("#"+this_.id)).val());
		}
		if(data.wxxmlx == 4){
			data.jsgs = $.trim($("#monitor_frame_package_jsgs",$("#"+this_.id)).val());
			data.wz = $.trim($("#monitor_frame_package_wz:visible",$("#"+this_.id)).val());
			data.isHdwz = $("#monitor_frame_package input[name='isHdwz']").is(":checked")?1:0;
			data.jgms = $.trim($("#monitor_frame_plane_jgms",$("#"+this_.id)).val());
		}
		
		if(this_.param.callback && typeof(this_.param.callback) == "function"){
			this_.param.callback(data);
		}
	},
	switchMonitorType : function(type){
		type = type || $("[name=ewxxmlx]:radio:checked").val();
		if(type == "1"){
			$("#component_monitor_panel").hide();
			$("#monitor_frame_plane").show();
		}else{
			$("#monitor_frame_plane").hide();
			$("#component_monitor_panel").show();
		}
	},
	
	// 新增部件监控项弹框
	showComponentMonitorModal:function(rowid){
		var this_ = this;
		var materialList = this_.projectMaterialList || [];
		var obj;
		if(rowid){
			for (var i = 0; i < materialList.length; i++) {
				if(materialList[i].rowid == rowid){
					obj = materialList[i];
				}
			}
		}
		var monitorUtil = new MonitorUtil({
			id:'monitor_modal_component',
			isEdit : this.param.isEdit,
			showFormula : false,
			showComponent : true,
			validateCount : true,
			type : "modal",
			obj : obj,
			width : "col-lg-10",
			dprtcode : this.param.dprtcode,
			callback : function(){
				this_.saveMonitorSetting();
			}
		});
		monitorUtil.show();
		this_.monitorObj = monitorUtil;
	},
	
	// 加载版本历史
	initHistoryList : function(){
		var this_ = this;
		var wxxmid = this_.param.editData.id;
		if(wxxmid){
			MaintenanceProjectHistory.getHistoryData(wxxmid, 2, function(list){
				WebuiPopoverUtil.initWebuiPopover('attachment-view3',"#"+this_.id,function(data){
					return MaintenanceProjectHistory.buildHistoryHtml(list, 2);
				});
				
				var wxxm;
				var bb = this_.param.editData.bb;
				if(list && list.length > 0){
					$.each(list, function(i, obj){
						if(parseFloat(obj.bb) == parseFloat(bb)){
							wxxm = obj;
							return false;
						}
					});
				}
				
				if(wxxm){
					$("#lastBbData",$("#"+this_.id)).html('<a href="javascript:void(0);" onclick="'+this_.id+'.viewProject(\''+list[0].id+'\')"> R'+list[0].bb+'</a>');
				}
			});
		}
	},
	
	// 保存部件监控设置
	saveMonitorSetting : function(){
		var this_ = this;
		
		var obj = this_.monitorObj.getComponent();
		this_.addMonitorRow(obj);
		
		var materialList = this_.projectMaterialList || [];
		
		var isInsert = true;
		
		for (var i = 0; i < materialList.length; i++) {
			if(materialList[i].rowid == obj.rowid){
				materialList[i] = obj;
				isInsert = false;
			}
		}
		if(isInsert){
			materialList.push(obj);
		}
		this_.projectMaterialList = materialList;
		this_.resizeVersionWidth();
	},
	
	// 添加行-监控设置
	addMonitorRow : function(obj){
		var this_ = this;
		var tr = [  '<tr rowid="'+obj.rowid+'">',
						'<td style="text-align:center;">',
							'<button class="line6 line6-btn" onclick="open_win_maintenance_project_modal.showComponentMonitorModal(\''+obj.rowid+'\')" title="修改 Modify">',
								'<i class="icon-edit cursor-pointer color-blue cursor-pointer"></i>',
							'</button>',
			          		'<button class="line6 line6-btn margin-left-5" onclick="open_win_maintenance_project_modal.removeMonitorRow(this)" title="删除 Delete">',
			          			'<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>',
			          		'</button>',
			          	'</td>',
			          	'<td style="text-align:cneter;"><span name="orderNumber">'+this_.materialCount+'</span></td>',
						'<td style="text-align:center;" name="bjh">'+StringUtil.escapeStr(obj.bjh)+'</td>',
						'<td style="text-align:left;">'+StringUtil.escapeStr(obj.cj)+'</td>',
						'<td colspan="4">'+maintenanceItemList.buildMonitorItem(MonitorUnitUtil.sort(obj.projectMonitors, "jklbh"), true, 'detail_rc_td')+'</td>',
						'<input type="hidden" name="monitor_component_id" value="'+StringUtil.escapeStr(obj.id)+'">',
				    '</tr>' ].join("");
		
		$("#component_monitor_list .no-data",$("#"+this_.id)).remove();
		
		var row = $("tr[rowid='"+obj.rowid+"']",$("#"+this_.id));
		if(row.length > 0){
			row.replaceWith(tr);
		}else{
			$("#component_monitor_list",$("#"+this_.id)).append(tr);
		}
		this_.resMonitorxh();
	},
	
	// 删除行-监控设置
	removeMonitorRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='monitor_component_id']").val();
		var rowid = $(obj).parents("tr").attr("rowid");
		if (id) {
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				var materialList = this_.projectMaterialList || [];
				for (var i = 0; i < materialList.length; i++) {
					if(materialList[i].rowid == rowid){
						materialList.splice(i, 1);
					}
				}
				$(obj).parent().parent().remove();
				this_.resMonitorxh();
			}});
			
		} else {
			var materialList = this_.projectMaterialList || [];
			for (var i = 0; i < materialList.length; i++) {
				if(materialList[i].rowid == rowid){
					materialList.splice(i, 1);
				}
			}
			$(obj).parent().parent().remove();
			this_.resMonitorxh();
		}
	},
	
	// 重新排列序号-监控设置
	resMonitorxh : function(){
		var this_ = this;
		var len = $("#component_monitor_list",$("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#component_monitor_list",$("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setMonitorNoData();
		}
	},
	setMonitorNoData : function(){
		if(this.param.isEdit){
			$("#component_monitor_list",$("#"+this.id)).append("<tr class='no-data'><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#component_monitor_list",$("#"+this.id)).append("<tr class='no-data'><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	// 维修项目查看差异
	viewProjectDifference:function(id){
		window.open(basePath + "/project2/maintenanceproject/different?id=" + id);
	},
	// 维修方案查看差异
	viewSchemeDifference:function(id){
		window.open(basePath + "/project2/maintenancescheme/different?id=" + id);
	},
	// 维修项目查看
	viewProject:function(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	},
	// 维修方案查看
	viewScheme:function(id){
		window.open(basePath + "/project2/maintenancescheme/view?id=" + id);
	},
	// EO查看
	viewEO:function(id){
		window.open(basePath + "/project2/eo/view?id=" + id);
	},
	// 计算任务描述长度
	calRwmsCount:function(){
		var rwms = $("#erwms",$("#"+this.id)).val();
		var count = rwms.replace(/[^\x00-\xff]/g,"012").length;
		$("#rwms_count",$("#"+this.id)).text(count);
		
		if(count > 4000){
			$("#rwms_des",$("#"+this.id)).addClass("text-danger");
		}else{
			$("#rwms_des",$("#"+this.id)).removeClass("text-danger");
		}
		return count;
	},
};