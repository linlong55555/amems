
	$(function(){
		Navigation(menuCode, '装机清单查看', 'View InstallationList', 'GC-1-3-2', '韩武', '2017-09-11', '韩武', '2017-09-11');//加载导航栏
		installationlist_view.init();
	});
	
	var installationlist_view = {
			
		id : "installation_view",
		
		// 页面初始化
		init : function(){
			this.disableAll();
			
			this.initData();
		},
		
		// 禁用所有输入框
		disableAll : function(){
			$(".panel-body input,textarea").attr("disabled","disabled");
			$(".panel-body select,:checkbox").attr("disabled","disabled");
			$(".readonly-style").removeClass("readonly-style");
			$("[name='common_fjzw_btn']").remove();
			$("[name='common_fjzw']").attr("disabled","disabled").css({"border-right":"1px solid #ccc","background-color":"#f3f3f3"});
			$(".panel-body .input-group-addon.btn.btn-default,.identifying").remove();
		},
		
		// 初始化数据
		initData : function(){
			var this_ = this;
			this_.common = new InstallationlistCommon({
				parentId : this_.id,
				cer_width : "col-lg-8 col-md-8 col-md-8 col-sm-7 col-xs-6",
				dataId : $("#zjqdid").val(),
				ope_type : 3,
				data_type : $("#dataType").val() || "effect",
				container : "body",
				callback : function(){
					$(".panel-body input,textarea").attr("disabled","disabled");
					$(".readonly-style").removeClass("readonly-style");
				}
			});
			
		},
		
	};
	
	