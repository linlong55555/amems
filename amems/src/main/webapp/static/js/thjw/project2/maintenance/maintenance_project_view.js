	$(function(){
		 Navigation(menuCode,'','','韩武','2017-08-01','韩武','2017-08-01');
		 // 初始化时间控件
		 maintenancePlanView.initDateWidget();
		 maintenancePlanView.loadWxfa();
	 });
	
	var maintenancePlanView={
			wxfa:{},
			initDateWidget:function(){
				$('.datepicker').datepicker({
					 autoclose: true,
					 clearBtn:true
				}).on('hide', function(e) {
			    });
			},
			// 显示/隐藏维修方案
			toggleMaintenance:function(obj){
				var i = $(obj);
				if(i.hasClass("icon-caret-left")){
					i.removeClass("icon-caret-left").addClass("icon-caret-right");
					$("#left_div").hide();
					$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
				}else{
					i.removeClass("icon-caret-right").addClass("icon-caret-left");
					$("#left_div").show();
					$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
				}
				maintenanceItemList.authHeight();
			},
			// 加载维修方案数据
			loadWxfa:function(){
				var this_ = this;
				AjaxUtil.ajax({
					type: "post",
			 		url:basePath+"/project2/maintenancescheme/detail",
			 		contentType:"application/json;charset=utf-8",
			 		dataType:"json",
			 		data:JSON.stringify({
			 			id : $("#wxfaid").val()
			 		}),
			 		success:function(data){
	 					this_.wxfa = data;
	 					maintenanceItemList.reload();
	 					maintenanceInfo.load();
			 		}
			    });
			}
	}
	
