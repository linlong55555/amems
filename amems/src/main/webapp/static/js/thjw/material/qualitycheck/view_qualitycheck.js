$(function(){
	Navigation(menuCode,"查看航材检验","Veiw Check Material");
	
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	var jldwTransfor=$("#jldw").val();
	$("#jldw").val(jldwTransfor);
	var jyjg=$("#jyjg").val();
	if(jyjg==2||jyjg==3){
		$("#jgsm_div").show();
	}else{
		$("#jgsm_div").hide();
	}
});
/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
	

