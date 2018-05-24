
	$(function(){
		
		/**
		 * 弹出窗验证销毁
		 */
		$("#installation_modify_modal").on("hidden.bs.modal", function() {
			$("#installation_modify_modal form").data('bootstrapValidator').destroy();
			$('#installation_modify_modal form').data('bootstrapValidator', null);
		});
		
		$("#installation_modify_modal").on("shown.bs.modal", function() {
			AlertUtil.hideModalFooterMessage();
		});

	});

	
	var installationlist_modify_win = {
		
		id : "installation_modify_modal",
		
		param : {
			dataId : "",
			chinaHead : "新增",
			englishHead : "Add",
		},
		
		show : function(param){
			
			var this_ = this;
			this_.param = param;
			this_.init();
			$("#"+this_.id).modal("show");
		},
		
		// 页面初始化
		init : function(){
			
			var this_ = this;
			
			$("#chinaHead").text(this_.param.chinaHead);
			$("#englishHead").text(this_.param.englishHead);
			
			this_.common = new InstallationlistCommon({
				parentId : this_.id,
				container : "#"+this_.id,
				cer_width : "col-lg-8 col-md-8 col-md-8 col-sm-7 col-xs-6",
				dataId : this_.param.dataId,
			});
			
		},
		
		// 保存
		save : function(){
			var this_ = this;
			var obj = this_.common.getInstallation();
			installationlist.save(obj, function(data){
				finishWait($("#"+this_.id));
				AlertUtil.showMessage("保存成功！");
				$("#"+this_.id).modal("hide");
				installationlist.reload();
			});
		}
		
	};
	
	