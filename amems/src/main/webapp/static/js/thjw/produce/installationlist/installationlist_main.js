
	$(function(){
		// 页面初始化
		installationlist.init();
	});
	
	var installationlist = {
		
		tableViewId : "installation_table_view",
		
		treeViewId : "installation_tree_view",
			
		// 页面初始化
		init : function(){
			
			var this_ = this;
			// 初始化导航栏
			this_.initNavigation();
			// 初始化控件
			this_.initWidget();
			// 初始化数据字典和枚举
			this_.initDicAndEnum();
			// 初始化飞机注册号
			this_.initAcReg();
			// 初始化日志
			this_.initLog();
			// 初始化通用页面
			this_.common = new InstallationlistCommon({
				parentId : this_.treeViewId,
				init_width_des : "col-lg-5 col-sm-5 col-xs-5",
				init_width_con : "col-lg-7 col-sm-7 col-xs-7",
			});
			// 重新加载table数据
			installationlist_table.reload();
		},
		
		// 获取组织机构
		getDprtcode : function(){
			return userJgdm;
		},
		
		// 获取飞机注册号
		getFjzch : function(){
			return $.trim($("#fjzch").val());
		},
		
		// 获取MSN
		getMSN : function(){
			return $.trim($("#fjzch option:selected").attr("xlh"));
		},
		
		// 获取机型
		getACType : function(){
			return $.trim($("#fjzch option:selected").attr("jx"));
		},
		
		// 获取发动机数量
		getEngCount : function(){
			return parseInt($("#fjzch option:selected").attr("fdjsl"));
		},
		
		// 获取状态
		getStatus : function(){
			return $.trim($("#zt").val());
		},
		
		// 获取关键字
		getKeyword : function(){
			return $.trim($("#keyword").val());
		},
		
		// 初始化导航栏
		initNavigation : function(){
			Navigation(menuCode, '', '', 'GC-1-3', '韩武', '2017-09-11', '韩武', '2017-09-11');
		},
		
		// 初始化控件
		initWidget : function(){
			// 初始化日期控件
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			
			// 初始化输入框内容限制控件
			$(".time-masked").mask("99:99");
			
			// 生成多选下拉框
			$('#kzlx').multiselect({
				buttonClass: 'btn btn-default',
			    buttonWidth: '100%',
			    numberDisplayed:100,
			    buttonContainer: '<div class="btn-group" style="width:90px;" />',
			    nonSelectedText:'全部 ',
			    allSelectedText:'全部 ',
			    includeSelectAllOption: true,
			    selectAllText: '选择全部'
			});
		},
		
		// 初始化数据字典和枚举
		initDicAndEnum : function(){
			var this_ = this;
			
			$("#wz").append("<option value='' selected='selected' >显示全部</option>");
			DicAndEnumUtil.registerEnum("installedPositionEnum", "wz");
			
			$("#zjjlx").append("<option value='' selected='selected' >显示全部</option>");
			DicAndEnumUtil.registerDic("52", "zjjlx", this_.getDprtcode());
		},
		
		// 初始化飞机注册号
		initAcReg : function(){
			
			var this_ = this;
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this_.getDprtcode()});
		 	var planeRegOption = '';
		 	if(planeDatas != null && planeDatas.length >0){
		 		$.each(planeDatas, function(i, planeData){
	 				planeRegOption 
	 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' " +
	 							"jx='"+StringUtil.escapeStr(planeData.FJJX)+"' "+
	 							"xlh='"+StringUtil.escapeStr(planeData.XLH)+"' "+
	 							"fdjsl='"+StringUtil.escapeStr(planeData.FDJSL)+"'>"
	 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
	 					+ "</option>";
		 		});
		 	}else{
		 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
		 	}
		 	$("#fjzch").html(planeRegOption);
		},
		
		// 切换状态
		switchStatus : function(){
			
			var this_ = this;
			if(this_.getStatus() == "current"){
				$("#contains_ineffective_div").css("visibility","visible");
			}else{
				$("#contains_ineffective_div").css("visibility","hidden");
			}
			this_.reload();
		},
		
		// 初始化日志
		initLog : function(){
			try {
				logModal.init({code:'B_S2_001'});
			} catch (e) {
			}
		},
		
		// 切换显示
		toggleView : function(){
			if($("#installation_table_view").is(":visible")){
				$("#installation_tree_view").show();
				$("#installation_table_view").hide();
				$("#zt").attr("disabled", "disabled");
				$("#zt").val("current");
				installationlist_tree.init();
			}else{
				$("#installation_table_view").show();
				$("#installation_tree_view").hide();
				$("#zt").removeAttr("disabled");
				installationlist_table.reload();
			}
			App.resizeHeight();
		},
		
		// 新增/修改装机清单数据
		modify : function(id){
			if($("#installation_table_view").is(":visible")){
				installationlist_modify_win.show({
					dataId : id,
					chinaHead : id ? "修改" : "新增",
					englishHead : id ? "Edit" : "Add",
				});
			}else{
				installationlist_tree.modify();
			}
			
		},
		
		// 查看装机清单数据（编辑区）
		viewCurrent : function(id){
			window.open(basePath + "/aircraftinfo/installationlist/view?id=" + id + "&type=edit");
		},
		
		// 查看装机清单数据（生效区）
		viewHistory : function(id){
			window.open(basePath + "/aircraftinfo/installationlist/view?id=" + id);
		},
		
		// 重新加载数据
		reload : function(){
			if($("#installation_table_view").is(":visible")){
				installationlist_table.reload();
			}else{
				installationlist_tree.init();
			}
		},
		
		// 保存
		save : function(obj, callback){
			if(obj){
				if(obj.weakMsg){
					AlertUtil.showConfirmMessage(obj.weakMsg, {callback: function(){
						delete obj.weakMsg;
						doSave();
					}});
				}else{
					doSave();
				}
			}
			
			function doSave(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/aircraftinfo/installationlist/save",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(obj),
					dataType:"json",
					success:function(data){
						if(callback && typeof(callback) == "function"){
							callback(data);
						}
					}
				});
			}
		},
		
		// 删除
		del : function(id){
			var this_ = this;
			
			AlertUtil.showConfirmMessage("将删除该部件以及其所有子部件（如果有），是否确定删除？", {callback: function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/aircraftinfo/installationlist/delete",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify({
						id : id
					}),
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("删除成功！");
						this_.reload();
					}
				});
			}});
		},
		
		effect : function(){
			
			var this_ = this;
			AlertUtil.showConfirmMessage("是否确定生效？", {callback: function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/aircraftinfo/installationlist/effect",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify({
						fjzch : this_.getFjzch(),
						dprtcode : this_.getDprtcode()
					}),
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("生效成功！");
						this_.reload();
					}
				});
			}});
		},
		
		// 下载文件
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		
		//显示导入模态框
		showImportModal : function(){
			var this_ = this;
			if(StringUtil.escapeStr(this_.getFjzch()) == ""){
				AlertUtil.showMessage("请选择飞机注册号！");
			}else{
				 // 初始化导入
			    importModal.init({
				    importUrl:"/aircraftinfo/installationlist/excelImport?dprtcode="+this_.getDprtcode()+"&fjjx="+this_.getACType()+"&fjzch="+this_.getFjzch(),
				    downloadUrl:"/common/templetDownfile/1", //装机清单
					callback: function(data){
						// 重新加载table数据
						this_.reload();
						$("#ImportModal").modal("hide");
					}
				});
				importModal.show();
			}
		}
	};
	
	// 自定义页面高度
	function customResizeHeight(bodyHeight, tableHeight){
		var panelHeadingHeight = $("#installation_tree_view_left .panel-heading").outerHeight()||0;
		var leftHeight = tableHeight - panelHeadingHeight - 30;
		var rightHeight = tableHeight - panelHeadingHeight - 10;
		$("#installation_tree").attr('style', 'height:' + leftHeight + 'px !important;overflow-x: auto;');
		$("#installation_tree_view_detail").attr('style', 'height:' + rightHeight + 'px !important;overflow-x: auto;');
	}
	
	