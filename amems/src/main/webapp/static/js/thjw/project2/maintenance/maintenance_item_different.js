
	$(function(){
		Navigation(menuCode,'查看差异','View Difference','韩武','2017-08-01','韩武','2017-08-01');
		
		// 初始化版本下拉框
		initHistorySelect();
	});
	
	/**
	 * 初始化版本下拉框
	 */
	function initHistorySelect(){
		var wxxmid = $("#wxxmid").val();
		AjaxUtil.ajax({
			url:basePath+"/project2/maintenanceproject/versionlist",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify({
				id : wxxmid
			}),
			dataType:"json",
			success:function(data){
				var targetIndex = 0;
				$.each(data, function(i, obj){
					$("#current_bb_list").append("<option value='"+obj.id+"'>R"+obj.bb+"</option>");
					$("#previous_bb_list").append("<option value='"+obj.id+"'>R"+obj.bb+"</option>");
					if(wxxmid == obj.id){
						targetIndex = i;
					}
				});
				$("#current_bb_list").val(wxxmid);
				$.each(data, function(i, obj){
					if(i == targetIndex || i == targetIndex + 1){
						$("#previous_bb_list").val(obj.id);
					}
				});
				loadDifference();
			}
		});
	}

	
	/**
	 * 加载差异
	 */
	function loadDifference(){
		var currentId = $("#current_bb_list").val();
		var previousId = $("#previous_bb_list").val();
		loadWxxm(currentId, previousId);
	}
