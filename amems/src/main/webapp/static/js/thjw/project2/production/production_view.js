
	$(function(){
		Navigation(menuCode,'生产指令查看','Production Order View');
		
		production_detail_modal.show({
			id : $("#id").val(),
			type : "view",
			dprtcode : userJgdm
		});
	});