<%@ page contentType="text/html; charset=utf-8" language="java" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航材工具需求清单</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<script>
		/* alert(window.openner); */
		/* var gdIdStr = window.openner.document.getElementById("gdIdStr"); */
		var type = '${param.type}';
	</script>
</head>
<body class="page-header-fixed">
		<!-------导航Start--->
		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<!-- BEGIN CONTENT -->
	<div class="page-content" id="material_tool_require_view" >
		<div class="panel panel-primary ">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body panel-body-new">
				
				<div id="m_task_info" style="display: none;">
					<%@ include file="/WEB-INF/views/common/produce/material_tool_task_info.jsp" %> <!-- 航材工具任务信息 -->
				</div>
				<div id="m_task_workpackage_info" style="display: none;">
					<%@ include file="/WEB-INF/views/common/produce/material_tool_task_info_workpackage.jsp" %> <!-- 工包航材工具任务信息 -->
				</div>
				<div id="m_task_workorder_info" style="display: none;">
					<%@ include file="/WEB-INF/views/common/produce/material_tool_task_info_workorder.jsp" %> <!-- 工单航材工具任务信息 -->
				</div>
				<div class="clearfix"></div>	
				
				<div id="m_tool_detail" >
					<%@ include file="/WEB-INF/views/common/produce/material_tool_detail_list.jsp" %> <!-- 航材工具清单列表信息 -->
				</div>
																								
			</div>
		</div>
	</div>		
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/thjw/common/monitor/monitor_unit.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/material_tool_require_detail_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/inventory_distribution_details_view.jsp"%><!-------库存分布详情 -------->
	<%@ include file="/WEB-INF/views/open_win/equivalent_substitution_view.jsp"%><!-------等效替代 -------->
	<%@ include file="/WEB-INF/views/open_win/task_info_list_view.jsp"%><!-------任务信息 -------->
</body>
</html>