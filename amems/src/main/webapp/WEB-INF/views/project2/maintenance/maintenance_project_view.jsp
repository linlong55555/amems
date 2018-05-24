<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="type" value="3" />
	<input type="hidden" id="pageType" value="5" />
	<input type="hidden" id="wxfaid" value="${param.id}"/>
	<input type="hidden" id="ajustHeight" value="90" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
  

	<div class="page-content" >
	<div class='col-xs-12 ' style='background:white;padding-left:0px;padding-right:0px;padding-top:0px;'>
	<div class='col-sm-3 col-xs-12' style='padding-left:0px;padding-right:10px;' id="left_div">
     <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_info.jsp"%>
    </div>
    <div class='col-sm-9 col-xs-12 padding-right-0 padding-left-0' id="right_div">
    	<div style="position:absolute;left:-8px;" id="maintenance_toggle_div">
			<i id="maintenance_toggle_btn" class="icon-caret-left cursor-pointer" style="font-size: 22px;" onclick="maintenancePlanView.toggleMaintenance(this)"></i>
		</div>
    <%@ include file="/WEB-INF/views/project2/maintenance/maintenance_item_list.jsp"%>
    </div>
    <div class='clearfix'></div>
    
    </div>	
	</div>
	
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/maintenance/maintenance_project_view.js"></script>
</body>
</html>
