$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	outfield_main.init();
	transit_main.init();
	
});

function customResizeHeight(bodyHeight, tableHeight){
	 var tabHead=$(".tab-only ul.nav-tabs ").outerHeight()||0;
	 var tabRowHeight=$(".tab-only .tab-content .row-height:visible").outerHeight()||0;
	 var paginationHeight=$(".tab-only .page-height:visible").outerHeight()||0;
	 var tabTableHeight=bodyHeight-tabHead-tabRowHeight-paginationHeight-20;
	 $(".tab-only .tab-content .tab-pane:visible").find("table").eq(0).parent("div").attr("style","height:"+tabTableHeight+"px;overflow:auto;");	
}

//下载
installationlist = {
		 downloadfile:function(id) {
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		}
}
