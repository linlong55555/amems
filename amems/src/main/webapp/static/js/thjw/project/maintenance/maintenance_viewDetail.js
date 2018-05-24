
$(function() {
	Navigation(menuCode,"查看维修方案","View");
});

//返回前页面
function back(){
	 window.location.href =basePath+"/project/maintenance/main?pageParam="+pageParam;
}
