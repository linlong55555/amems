$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
		});
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		 //收缩效果
		$('#certificate_info').on('show.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#certificate_info').on('hide.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
		
		$('#resume_info').on('show.bs.collapse', function () {
			$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#resume_info').on('hide.bs.collapse', function () {
			$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
	})
	function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
    }
    function showInsideOpen(){
    	$("#inside_open_alert").modal("show");
    }