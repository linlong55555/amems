$(function(){
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	$('.page-content select').attr('disabled',true); 
	
	var len1 = $("#xlhfilelist").find("tr").length;
	if (len1 < 1) {
		$("#xlhfilelist").append("<tr><td colspan='4'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len2 = $("#filelist").find("tr").length;
	if (len2 < 1) {
		$("#filelist").append("<tr><td colspan='4'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	
	var sjlx = $("#sjlx").val();
	if(sjlx == 1){
		Navigation(menuCode, '查看外形缺损', 'View', ' ', '孙霁', '2017-08-25', '孙霁', '2017-08-25');//加载导航栏
	}else{
		Navigation(menuCode, '查看结构修理', 'View', ' ', '孙霁', '2017-08-25', '孙霁', '2017-08-25');//加载导航栏
	}
	
	var isXlxjc=$("input:radio[name='is_xlxjc']:checked").val();
	if(isXlxjc==1){
		$("#lxjcjg-div").show();
	}else{
		$("#lxjcjg-div").hide();
	}
});

//下载
function openAttachmentWinEdit(obj){
	var id = $(obj).attr("key");
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

